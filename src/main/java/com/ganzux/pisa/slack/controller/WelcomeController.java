package com.ganzux.pisa.slack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alvaro Alcedo Moreno - aalcedo
 * @version 1.0
 * @since 2021-07-10
 */
@RestController
public class WelcomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class.getCanonicalName());
    private static StringBuilder sb = new StringBuilder("Hello World");

    @Autowired
    SlackMessage scheduledController;

    @GetMapping("/")
    public String welcome(HttpServletRequest request) throws Exception {
        sb.append("!");

        LOGGER.info(sb.toString());

        scheduledController.rootMessage(request.getRemoteAddr());

        return sb.toString();
    }

}
