package com.ganzux.sirme.slack.commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;
import com.slack.api.model.event.AppHomeOpenedEvent;
import static com.slack.api.model.block.element.BlockElements.*;
/**
 * @author Alvaro Alcedo Moreno - aalcedo
 * @version 1.0
 * @since 2021-07-03
 */
@Configuration
public class SlackAppInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackAppInitializer.class.getCanonicalName());

    @Value("${slack.token}")
    String SLACK_TOKEN;

    @Value("${slack.secret}")
    String SLACK_SECRET;

    @Bean
    public AppConfig loadAppConfig() {

        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken(SLACK_TOKEN);
        config.setSigningSecret(SLACK_SECRET);
        return config;
    }

    @Bean
    public App initSlackApp(AppConfig config) throws Exception {

        LOGGER.info("Initializing Slack App commands");

        App app = new App(config);
        app.command("/hello", (req, ctx) -> {

            LOGGER.info("hello ?", req.getRequestBodyAsString());

            return ctx.ack(r -> r.text("Thanks!"));
        });

        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            View appHomeView = view(view -> view
                .type("home")
                .blocks(asBlocks(
                    section(section -> section.text(markdownText(mt -> mt.text("*Welcome to your _App's Home_* :tada:")))),
                    divider(),
                    section(section -> section.text(markdownText(mt -> mt.text("This button won't do much for now but you can set up a listener for it using the `actions()` method and passing its unique `action_id`. See an example on <https://slack.dev/java-slack-sdk/guides/interactive-components|slack.dev/java-slack-sdk>.")))),
                    actions(actions -> actions
                        .elements(asElements(
                            button(b -> b.text(plainText(pt -> pt.text("Click me!"))).value("button1").actionId("button_1"))
                        ))
                    )
                ))
            );

            ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                    .userId(payload.getEvent().getUser())
                    .view(appHomeView)
            );

            return ctx.ack();
        });




        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#general")
                .text(":dizzy: SIRME App started! Feel free to ask me")
                .token(SLACK_TOKEN)
                .build();

        app.client().chatPostMessage(request);

        return app;
    }

}