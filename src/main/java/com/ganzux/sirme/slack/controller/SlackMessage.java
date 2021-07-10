package com.ganzux.sirme.slack.controller;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * @author Alvaro Alcedo Moreno - aalcedo
 * @version 1.0
 * @since 2021-07-10
 */
@Controller
public class SlackMessage {

    @Value("${slack.token}")
    String SLACK_TOKEN;

    public void sendMesssage() throws Exception {
        Slack slack = Slack.getInstance();
         // Initialize an API Methods client with the given token
        MethodsClient methods = slack.methods(SLACK_TOKEN);

        // Build a request object
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#general") // Use a channel ID `C1234567` is preferrable
                .text(":wave: Hi from a bot written in Java!")
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = methods.chatPostMessage(request);

        System.out.println("res" + response.toString());
    }

}
