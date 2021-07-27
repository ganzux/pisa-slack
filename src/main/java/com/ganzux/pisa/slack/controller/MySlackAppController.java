package com.ganzux.pisa.slack.controller;

import com.ganzux.pisa.slack.exceptions.MessageException;
import com.ganzux.pisa.slack.persistance.service.ProjectService;
import com.ganzux.pisa.slack.persistance.service.TimesheetService;
import com.ganzux.pisa.slack.util.Constants;
import com.ganzux.pisa.slack.views.HomePage;
import com.ganzux.pisa.slack.views.ViewsHelper;
import com.google.gson.internal.LinkedTreeMap;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;

import javax.servlet.annotation.WebServlet;

import com.slack.api.model.view.ViewState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



@WebServlet(name = "MySlackAppController",
        urlPatterns = {"/slack/events"})
public class MySlackAppController extends SlackAppServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySlackAppController.class);

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

        // HOME PAGE
        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {

            ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                .userId(payload.getEvent().getUser())
                .view(homePage.loadMainView(payload))
            );

            return ctx.ack();
        });


        // SAVE
        app.blockAction(Constants.VIEW_TS_INPUT_SAVE, (req, ctx) -> {

            try {
                String[] formData = recoverValuesTimesheet(req, ctx);
                timesheetService.save(formData[0], formData[1], formData[2], formData[3], formData[4], formData[5]);
            } catch (MessageException e) {
                return viewsHelper.openAckModal(ctx,"Are you sure you have all the data?", true);
            } catch (Exception e) {
                return viewsHelper.openAckModal(ctx,"Oh, oh...", true);
            }

            return viewsHelper.openAckModal(ctx,"Well Done!", false);
        });

        // LAST TIMESHEET
        app.blockAction(Constants.VIEW_MODAL_LAST_TIMESHEET, (req, ctx) -> {
            return viewsHelper.openMoreInfoModal(ctx, true);
        });

        // CURRENT TIMESHEET
        app.blockAction(Constants.VIEW_MODAL_THIS_TIMESHEET, (req, ctx) -> {
            return viewsHelper.openMoreInfoModal(ctx, false);
        });

        // SELECT PROJECT
        app.blockAction(Constants.VIEW_TS_INPUT_PROJECT, (req, ctx) -> {
            // Do something where
            return ctx.ack();
        });

        // Close info modal
        app.viewClosed("info-modal", (req, ctx) -> {
            // refresh main screen
            return ctx.ack();
        });



    }

    protected String[] recoverValuesTimesheet(BlockActionRequest req, ActionContext ctx) throws MessageException {
        try {
            LinkedTreeMap projectMap = (LinkedTreeMap) req.getPayload().getView().getState().getValues().get(Constants.VIEW_BLOCK_INPUT_PROJECT);
            LinkedTreeMap minutesMap = (LinkedTreeMap) req.getPayload().getView().getState().getValues().get(Constants.VIEW_BLOCK_INPUT_MINUTES);
            LinkedTreeMap commentsMap = (LinkedTreeMap) req.getPayload().getView().getState().getValues().get(Constants.VIEW_BLOCK_INPUT_COMMENTS);
            LinkedTreeMap dateFromMap = (LinkedTreeMap) req.getPayload().getView().getState().getValues().get(Constants.VIEW_BLOCK_INPUT_DATE_FROM);
            LinkedTreeMap dateToMap = (LinkedTreeMap) req.getPayload().getView().getState().getValues().get(Constants.VIEW_BLOCK_INPUT_DATE_TO);

            String projectId = ((ViewState.Value) projectMap.get(Constants.VIEW_TS_INPUT_PROJECT)).getSelectedOption().getValue();
            String minutes = ((ViewState.Value) minutesMap.get(Constants.VIEW_TS_INPUT_MINUTES)).getValue();
            String comments = ((ViewState.Value) commentsMap.get(Constants.VIEW_TS_INPUT_COMMENTS)).getValue();
            String dateFrom = ((ViewState.Value) dateFromMap.get(Constants.VIEW_TS_INPUT_DATE_FROM)).getSelectedDate();
            String dateTo = ((ViewState.Value) dateToMap.get(Constants.VIEW_TS_INPUT_DATE_TO)).getSelectedDate();
            String userId = ctx.getRequestUserId();

            // TODO Change for a DTO
            return new String[]{userId, projectId, minutes, comments, dateFrom, dateTo};
        } catch (Exception e) {
            LOGGER.error("Error recovering data from main screen {}", e.getLocalizedMessage(), e);
            throw new MessageException("Error recovering data from main screen");
        }
    }


}
