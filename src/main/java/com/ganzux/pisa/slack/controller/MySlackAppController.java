package com.ganzux.pisa.slack.controller;

import com.ganzux.pisa.slack.persistance.service.ProjectService;
import com.ganzux.pisa.slack.persistance.service.TimesheetService;
import com.ganzux.pisa.slack.util.Constants;
import com.ganzux.pisa.slack.views.HomePage;
import com.ganzux.pisa.slack.views.ViewsHelper;
import com.google.gson.internal.LinkedTreeMap;
import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;

import javax.servlet.annotation.WebServlet;

import com.slack.api.model.view.ViewState;
import org.springframework.beans.factory.annotation.Autowired;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

@WebServlet(name = "MySlackAppController",
        urlPatterns = {"/slack/events"})
public class MySlackAppController extends SlackAppServlet {

    @Autowired
    public HomePage homePage;

    @Autowired
    public ViewsHelper viewsHelper;

    @Autowired
    public ProjectService projectService;

    @Autowired
    public TimesheetService timesheetService;

    public MySlackAppController(App app) {
        super(app);

        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {


            ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                .userId(payload.getEvent().getUser())
                .view(homePage.loadMainView(payload))
            );

            return ctx.ack();
        });

        app.blockAction(Constants.VIEW_TS_INPUT_SAVE, (req, ctx) -> {

            LinkedTreeMap projectMap = (LinkedTreeMap)req.getPayload().getView().getState().getValues().get("project-block");
            LinkedTreeMap minutesMap = (LinkedTreeMap)req.getPayload().getView().getState().getValues().get("minutes-block");
            LinkedTreeMap commentsMap = (LinkedTreeMap)req.getPayload().getView().getState().getValues().get("comments-block");
            LinkedTreeMap dateFromMap = (LinkedTreeMap)req.getPayload().getView().getState().getValues().get("minutes-blockF");
            LinkedTreeMap dateToMap = (LinkedTreeMap)req.getPayload().getView().getState().getValues().get("minutes-blockT");

            String projectId = ((ViewState.Value) projectMap.get(Constants.VIEW_TS_INPUT_PROJECT)).getSelectedOption().getValue();
            String minutes = ((ViewState.Value) minutesMap.get(Constants.VIEW_TS_INPUT_MINUTES)).getValue();
            String comments = ((ViewState.Value) commentsMap.get(Constants.VIEW_TS_INPUT_COMMENTS)).getValue();
            String dateFrom = ((ViewState.Value) dateFromMap.get(Constants.VIEW_TS_INPUT_DATE_FROM)).getSelectedDate();
            String dateTo = ((ViewState.Value) dateToMap.get(Constants.VIEW_TS_INPUT_DATE_TO)).getSelectedDate();
            String userId = ctx.getRequestUserId();

            timesheetService.save(userId, projectId, minutes, comments, dateFrom, dateTo);

            return ctx.ack();
        });

        app.blockAction(Constants.VIEW_MODAL_LAST_TIMESHEET, (req, ctx) -> {
            return viewsHelper.openMoreInfoModal(ctx, true);
        });

        app.blockAction(Constants.VIEW_MODAL_THIS_TIMESHEET, (req, ctx) -> {
            return viewsHelper.openMoreInfoModal(ctx, false);
        });

        app.command("/ping", (req, ctx) -> {
            return ctx.ack(asBlocks(
                    section(section -> section.text(markdownText(":wave: pong"))),
                    actions(actions -> actions
                            .elements(asElements(
                                    button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                            ))
                    )
            ));
        });

        app.command("/echo", (req, ctx) -> {
            return ctx.ack(req.getResponseUrl());
        });

        app.blockAction(Constants.VIEW_TS_INPUT_PROJECT, (req, ctx) -> {
            // Do something where
            return ctx.ack();
        });


    }


}
