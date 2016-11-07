package ru.norgorn.moneySync.web.controllers;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.norgorn.moneySync.persistence.entity.Money;
import ru.norgorn.moneySync.service.MoneySyncService;
import spark.Request;

@Controller
public class MoneyStatsController {

	MoneySyncService moneyService;
	
	@Autowired
	public MoneyStatsController(MoneySyncService moneyService){
		this.moneyService = moneyService;
		
		setupControllers();
	}

	private void setupControllers() {
		get("/money/stats/:top", (request, response) -> {
			return getTopPlayersPerAllCountries(request);
		});
	}

	public String getTopPlayersPerAllCountries(Request request) {
		int top = readAndValidateTop(request);
		
		Map<String,List<Money>> moneyStats = moneyService.getTopPerAllCountries(top);
		return MoneyControllerJsonHelper.statsToJson(moneyStats);
	}

	public int readAndValidateTop(Request request) {
		int top=0;
		
		try {
			String topStr = request.params(":top");
			if( topStr == null || topStr.trim().length() == 0)
				halt(MoneyController.HTTP_BAD_REQUEST);
			top = Integer.parseInt(topStr);
		} catch (NumberFormatException e) {
			halt(MoneyController.HTTP_BAD_REQUEST);
		}
		return top;
	}
}
