package com.ganzux.mahris.slack.views;

import com.ganzux.mahris.slack.persistance.dto.UserDto;
import com.ganzux.mahris.slack.persistance.service.ProjectService;
import com.ganzux.mahris.slack.persistance.service.TimesheetService;
import com.ganzux.mahris.slack.persistance.service.UserService;
import com.ganzux.mahris.slack.util.TimeUtil;
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
                                .accessory(button(b -> b.text(plainText(pt -> pt.text("See last week report"))).value("last_timesheets").actionId("modalLastTs"))
                                )
                        ),
                        section(section -> section
                                .text(markdownText(generateThisWeekSummary(user.getUserId())))
                                .accessory(button(b -> b.text(plainText(pt -> pt.text("See this week report"))).value("this_timesheets").actionId("modalThisTs"))
                                )
                        ),

                        divider(),

                        section(section -> section
                                .blockId("project-block")
                                .text(markdownText("Select the Project you have been working in"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("project-selection-action")
                                        .placeholder(plainText("Select a project"))
                                        .options(asOptions(viewsHelper.generateProjectList()
                                        ))
                                ))
                        ),

                        input(section -> section
                                .blockId("minutes-blockF")
                                .label(plainText("Date worked From"))
                                .element(datePicker(d -> d
                                        .actionId("date-worked-from")
                                       // .initialDate(timeUtil.weekInit())
                                ))
                        ),

                        input(section -> section
                                .blockId("minutes-blockT")
                                .label(plainText(pt -> pt.text(("Date worked To"))))
                                .element(datePicker(d -> d
                                        .actionId("date-worked-to")
                                        //.initialDate(timeUtil.weekEnd())
                                ))
                        ),

                        input(input -> input
                                .blockId("minutes-block")
                                .element(plainTextInput(pti -> pti
                                        .actionId("minutes-input-action")
                                        .multiline(false)
                                        .maxLength(5)
                                        .minLength(1)
                                        .initialValue("480")
                                ))
                                .label(plainText(pt -> pt.text("Minutes invested in the project").emoji(true)))
                        ),

                        input(input -> input
                                .blockId("comments-block")
                                .element(plainTextInput(pti -> pti
                                        .actionId("comments-input-action")
                                        .multiline(true)
                                        .maxLength(300)
                                        .minLength(0)
                                ))
                                .label(plainText(pt -> pt.text("Comments").emoji(true)))
                        ),

                        divider(),
                        actions(actions -> actions
                                .elements(asElements(
                                        button(b -> b.text(plainText(pt -> pt.text("Add Timesheet"))).value("button1").actionId("copyLastTs"))
                                ))
                        )
                ))
        );

        return appHomeView;
    }

    protected String generateHeader() {
        return ":disguised_face: *Multi Agent HR Information System* at your service! :tada:";
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
