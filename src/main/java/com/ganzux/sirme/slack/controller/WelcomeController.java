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

    @GetMapping("/")
    public String welcome() {
        return "Hello World!";
    }

}
