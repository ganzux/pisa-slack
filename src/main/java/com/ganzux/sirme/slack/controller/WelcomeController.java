package com.ganzux.sirme.slack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alvaro Alcedo Moreno - aalcedo
 * @version 1.0
 * @since 2021-07-10
 */
@RestController
public class WelcomeController {

    StringBuilder sb = new StringBuilder("Hello World");

    @GetMapping("/")
    public String welcome() {
        sb.append("!");
        return sb.toString();
    }

}
