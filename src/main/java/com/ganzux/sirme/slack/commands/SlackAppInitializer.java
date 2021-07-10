package com.ganzux.sirme.slack.commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

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

    @Bean
    public AppConfig loadAppConfig() {

        AppConfig config = new AppConfig();

        config.setSingleTeamBotToken(SLACK_TOKEN);
        config.setClientId("2224011135479.2262426042608");
        config.setClientSecret("e10464249db633b8c935056260eb507c");

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


        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#general") // Use a channel ID `C1234567` is preferrable
                .text(":wave: Hi there from a bot written in Java!")
                .token(SLACK_TOKEN)
                .build();

        LOGGER.info("chatPostMessage");
        try {
            app.client().chatPostMessage(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("chatPostMessage done");
        return app;
    }

}