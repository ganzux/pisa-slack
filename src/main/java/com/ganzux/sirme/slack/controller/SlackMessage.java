package com.ganzux.sirme.slack.controller;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * @author Alvaro Alcedo Moreno - aalcedo
 * @version 1.0
 * @since 2021-07-10
 */
@Controller
public class SlackMessage {

    @Autowired
    App slackApp;

    public void rootMessage(String originalAddress) throws Exception {

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#general")
                .text(":thinking_face: Hey! " + originalAddress + " is looking under the table!")
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = slackApp.client().chatPostMessage(request);

        System.out.println(response);
    }

}
