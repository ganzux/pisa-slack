package com.ganzux.pisa.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class PisaSlackApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(PisaSlackApplication.class.getCanonicalName());

	public static void main(String[] args) {
		LOGGER.info("Initializing SpringBoot app...");
		SpringApplication.run(PisaSlackApplication.class, args);
	}

}
