package ru.norgorn.moneySync.web.controllers;

import static spark.Spark.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger("Controller_Errors_Junction");
	
	public ExceptionHandler(){
		setupControllers();
	}
	
	public void setupControllers(){
		
		exception(IOException.class, (e, request, response) -> {
			logger.error("Bad request: " + request.toString(), e);
		    response.status(MoneyController.HTTP_BAD_REQUEST);
		    response.body("");
		});
		
		exception(Exception.class, (e, request, response) -> {
			logger.error("Internal error while processing request: " + request.toString(), e);
			response.status(500);
		    response.body("");
		});
	}
}
