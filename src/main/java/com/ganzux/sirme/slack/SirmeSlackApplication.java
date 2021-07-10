package com.ganzux.sirme.slack;

import com.ganzux.sirme.slack.controller.WelcomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SirmeSlackApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SirmeSlackApplication.class.getCanonicalName());

	public static void main(String[] args) {
		LOGGER.info("Initializing SpringBoot app...");
		SpringApplication.run(SirmeSlackApplication.class, args);
	}

}
