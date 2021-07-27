package com.ganzux.pisa.slack.views;

import com.ganzux.pisa.slack.persistance.dto.UserDto;
import com.ganzux.pisa.slack.persistance.service.ProjectService;
import com.ganzux.pisa.slack.persistance.service.TimesheetService;
import com.ganzux.pisa.slack.persistance.service.UserService;
import com.ganzux.pisa.slack.util.Constants;
import com.ganzux.pisa.slack.util.TimeUtil;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.slack.api.model.block.Blocks.actions;
import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.asOptions;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static com.slack.api.model.block.element.BlockElements.datePicker;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.block.element.BlockElements.staticSelect;
import static com.slack.api.model.view.Views.view;

@Component
public class HomePage {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TimesheetService timesheetService;

    @Autowired
    ViewsHelper viewsHelper;

    @Autowired
    TimeUtil timeUtil;

    public View loadMainView(EventsApiPayload<AppHomeOpenedEvent> appHomeOpenedEvent){

        UserDto user = userService.get(appHomeOpenedEvent.getEvent().getUser());


        View appHomeView = view(view -> view
            .type("home")
                .blocks(asBlocks(
                        section(section -> section
                                .text(markdownText(generateHeader()))
                            ),

                        divider(),

                        section(section -> section
                                .text(markdownText(generateLastWeekSummary(user.getUserId())))
                                .accessory(button(b -> b.text(plainText(pt -> pt.text("See last week report"))).value("last_timesheets").actionId(Constants.VIEW_MODAL_LAST_TIMESHEET))
                                )
                        ),
                        section(section -> section
                                .text(markdownText(generateThisWeekSummary(user.getUserId())))
                                .accessory(button(b -> b.text(plainText(pt -> pt.text("See this week report"))).value("this_timesheets").actionId(Constants.VIEW_MODAL_THIS_TIMESHEET))
                                )
                        ),

                        divider(),

                        section(section -> section
                                .blockId(Constants.VIEW_BLOCK_INPUT_PROJECT)
                                .text(markdownText("Select the Project you have been working in"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId(Constants.VIEW_TS_INPUT_PROJECT)
                                        .placeholder(plainText("Select a project"))
                                        .options(asOptions(viewsHelper.generateProjectList()
                                        ))
                                ))
                        ),

                        input(section -> section
                                .blockId(Constants.VIEW_BLOCK_INPUT_DATE_FROM)
                                .label(plainText("Date worked From"))
                                .element(datePicker(d -> d
                                        .actionId(Constants.VIEW_TS_INPUT_DATE_FROM)
                                       // .initialDate(timeUtil.weekInit())
                                ))
                        ),

                        input(section -> section
                                .blockId(Constants.VIEW_BLOCK_INPUT_DATE_TO)
                                .label(plainText(pt -> pt.text(("Date worked To"))))
                                .element(datePicker(d -> d
                                        .actionId(Constants.VIEW_TS_INPUT_DATE_TO)
                                        //.initialDate(timeUtil.weekEnd())
                                ))
                        ),

                        input(input -> input
                                .blockId(Constants.VIEW_BLOCK_INPUT_MINUTES)
                                .element(plainTextInput(pti -> pti
                                        .actionId(Constants.VIEW_TS_INPUT_MINUTES)
                                        .multiline(false)
                                        .maxLength(5)
                                        .minLength(1)
                                        .initialValue("480")
                                ))
                                .label(plainText(pt -> pt.text("Minutes invested in the project").emoji(true)))
                        ),

                        input(input -> input
                                .blockId(Constants.VIEW_BLOCK_INPUT_COMMENTS)
                                .element(plainTextInput(pti -> pti
                                        .actionId(Constants.VIEW_TS_INPUT_COMMENTS)
                                        .multiline(true)
                                        .maxLength(300)
                                        .minLength(0)
                                ))
                                .label(plainText(pt -> pt.text("Comments").emoji(true)))
                        ),

                        divider(),
                        actions(actions -> actions
                                .elements(asElements(
                                        button(b -> b.text(plainText(pt -> pt.text("Add Timesheet"))).value("button1").actionId(Constants.VIEW_TS_INPUT_SAVE))
                                ))
                        )
                ))
        );

        return appHomeView;
    }

    protected String generateHeader() {
        return ":disguised_face: *Personnel Integration Slack Application* at your service! :tada:";
    }

    protected String generateLastWeekSummary(String userId) {

        StringBuilder sb = new StringBuilder();

        long hoursWorkedLastWeek = timeUtil.calculateHoursWork(viewsHelper.getLastWeekTimesheets(userId));

        sb.append("Last week you worked ").append(hoursWorkedLastWeek).append(" hours ");
        sb.append(viewsHelper.generateEmojiForWorkingHours(hoursWorkedLastWeek));

        return sb.toString();
    }

    protected String generateThisWeekSummary(String userId) {

        StringBuilder sb = new StringBuilder();

        long hoursWorkedThistWeek = timeUtil.calculateHoursWork(viewsHelper.getThisWeekTimesheets(userId));

        sb.append("This week you've worked ").append(hoursWorkedThistWeek).append(" hours ");
        sb.append(viewsHelper.generateEmojiForWorkingHours(hoursWorkedThistWeek));

        return sb.toString();
    }

}
