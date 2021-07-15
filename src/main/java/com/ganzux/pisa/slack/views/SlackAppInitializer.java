package com.ganzux.pisa.slack.views;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SlackAppInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackAppInitializer.class.getCanonicalName());

    @Value("${slack.token}")
    String SLACK_TOKEN;

    @Value("${slack.secret}")
    String SLACK_SECRET;

    @Bean
    public App initSlackApp(AppConfig config) {
        App app = new App(config);
        return app;
    }

    @Bean
    public AppConfig loadAppConfig() {
        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken(SLACK_TOKEN);
        config.setSigningSecret(SLACK_SECRET);
        return config;
    }

}