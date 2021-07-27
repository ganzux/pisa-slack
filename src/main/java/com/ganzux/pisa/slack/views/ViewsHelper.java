package com.ganzux.pisa.slack.views;

import com.ganzux.pisa.slack.persistance.dto.ProjectDto;
import com.ganzux.pisa.slack.persistance.dto.TimeSheetDto;
import com.ganzux.pisa.slack.persistance.dto.UserDto;
import com.ganzux.pisa.slack.persistance.service.ProjectService;
import com.ganzux.pisa.slack.persistance.service.TimesheetService;
import com.ganzux.pisa.slack.persistance.service.UserService;
import com.ganzux.pisa.slack.util.TimeUtil;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.view.Views.view;
import static com.slack.api.model.view.Views.viewTitle;

@Component
public class ViewsHelper {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    TimeUtil timeUtil;

    @Autowired
    TimesheetService timesheetService;

    @Autowired
    ViewsHelper viewsHelper;

    private static final long MIN_HOURS_WEEK = 38;

    public String generateEmojiForWorkingHours(long workingWeek) {
        if (workingWeek < MIN_HOURS_WEEK) {
            return ":warning:";
        } else {
            return ":clap::skin-tone-3:";
        }
    }

    public List<TimeSheetDto> getLastWeekTimesheets(String userId) {
        UserDto user = userService.get(userId);

        // Calculate beginning and end of current week
        LocalDate now = LocalDate.now();
        LocalDate thisWeekInit = timeUtil.weekInit(now);

        LocalDate lastWeekInit = timeUtil.weekInit(thisWeekInit.minusDays(7));
        LocalDate lastWeekEnd = timeUtil.weekEnd(lastWeekInit);

        List<TimeSheetDto> lastTimeSheets = timesheetService.getLasts(user.getUserId(), lastWeekInit, lastWeekEnd);

        return lastTimeSheets;
    }

    public List<TimeSheetDto> getThisWeekTimesheets(String userId) {
        // Connected user
        UserDto user = userService.get(userId);

        // Calculate beginning and end of current week
        LocalDate now = LocalDate.now();
        LocalDate thisWeekInit = timeUtil.weekInit(now);
        LocalDate thisWeekEnd = timeUtil.weekEnd(now);

        List<TimeSheetDto> thisTimeSheets = timesheetService.getLasts(user.getUserId(), thisWeekInit, thisWeekEnd);

        return thisTimeSheets;
    }

    public OptionObject[] generateProjectList(){

        List<ProjectDto> projectDtos = projectService.findAll();
        projectDtos.stream().forEach(projectDto -> new OptionObject());

        OptionObject[] projects = new OptionObject[projectDtos.size()];
        int i = 0;
        for (ProjectDto projectDto : projectDtos) {
            projects[i] = new OptionObject();
            projects[i].setText(plainText(pt -> pt.text(projectDto.getProjectName()).emoji(true)));
            projects[i].setValue(String.valueOf(projectDto.getId()));
            i++;
        }

        return projects;
    }

    public View buildModalLastSpreadSheets(String userId, boolean lastWeek) {

        StringBuilder sb = new StringBuilder();
        if (lastWeek) {
            sb.append("Last");
        } else {
            sb.append("This");
        }
        sb.append(" week Timesheet");

        return view(view -> view
                .callbackId("see-last-timesheet" + lastWeek)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text(sb.toString()).emoji(true)))

                .blocks(
                        asBlocks(
                                getLastTimeSheetsFormatted(userId, lastWeek)
                        )
                )
        );
    }

    public View buildModalWithMessage(String message, boolean error) {

        StringBuilder sb = new StringBuilder();
        sb.append(message);

        return view(view -> view
                .callbackId("info-modal")
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text(error? "Error!" : "Operation result").emoji(true)))

                .blocks(
                        asBlocks(section(section -> section.text(markdownText(message))))
                )
        );
    }

    public Response openMoreInfoModal(ActionContext ctx, boolean lastWeek) throws IOException, SlackApiException {
        ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(buildModalLastSpreadSheets(ctx.getRequestUserId(), lastWeek)));
        if (viewsOpenRes.isOk()) {
            return ctx.ack();
        }
        else {
            return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
        }
    }

    public Response openAckModal(ActionContext ctx, String text, boolean isError) throws IOException, SlackApiException {
        ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(buildModalWithMessage(text, isError)));
        if (viewsOpenRes.isOk()) {
            return ctx.ack();
        }
        else {
            return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
        }
    }

    public Response openMoreInfoModal(SlashCommandContext ctx, boolean lastWeek) throws IOException, SlackApiException {
        ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(buildModalLastSpreadSheets(ctx.getRequestUserId(), lastWeek)));
        if (viewsOpenRes.isOk()) {
            return ctx.ack();
        }
        else {
            return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
        }
    }

    public LayoutBlock[] getLastTimeSheetsFormatted(String userId, boolean lastWeek) {

        List<TimeSheetDto> lastWeekTs;

        if (lastWeek) {
            lastWeekTs = viewsHelper.getLastWeekTimesheets(userId);
        } else {
            lastWeekTs = viewsHelper.getThisWeekTimesheets(userId);
        }

        LayoutBlock[] layoutBlocks = new LayoutBlock[lastWeekTs.size()];

        int i = 0;
        for(TimeSheetDto timeSheetDto : lastWeekTs) {
            layoutBlocks[i++] = section(section -> section.text(markdownText(generateWeekShort(timeSheetDto))));
        }
        return  layoutBlocks;
    }

    public String generateWeekShort(TimeSheetDto timeSheetDto) {

        String weekData = String.format("Project: *%s*\nHours: %s (%s to %s)\nComments: %s",
                timeSheetDto.getProject().getProjectName(),
                (double)timeSheetDto.getDuration() / 60,
                timeUtil.prettyDate(timeSheetDto.getTimeFrom()),
                timeUtil.prettyDate(timeSheetDto.getTimeTo()),
                null == timeSheetDto.getComments() ? "No comments" : timeSheetDto.getComments()
                );

        return weekData;
    }

    public void cleanHomeScreen() {

    }
}
