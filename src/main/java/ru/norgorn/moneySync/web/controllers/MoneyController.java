package ru.norgorn.moneySync.web.controllers;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.norgorn.moneySync.persistence.entity.Money;
import ru.norgorn.moneySync.service.MoneySyncService;
import ru.norgorn.moneySync.web.requestHandler.impl.PlayerMoneyDTO;
import spark.Request;
import spark.Response;

@Controller
public class MoneyController {

	static final int HTTP_BAD_REQUEST = 400;
	static final int HTTP_OK = 200;
	static final int HTTP_NOT_FOUND = 404;
	
	private static final String PATH_PREFIX = "/money";
	
	MoneySyncService moneyService;
	
	@Autowired
	public MoneyController(MoneySyncService moneyService){
		this.moneyService = moneyService;
		
		setupControllers();
	}

	public void setupControllers(){
		get(PATH_PREFIX+"/:uid", (request, response) -> {
			return getPlayersMoney(request);
		});
		
		post(PATH_PREFIX+"/:uid", (request, response) -> {
			return addPlayersMoney(request, response);
		});
	}

	public String getPlayersMoney(Request request) throws IOException {
		String uid = readUidAndValidate(request);
			
		Money value = moneyService.get(uid);
		
		if(value == null)
			halt(HTTP_NOT_FOUND, "No such player");
		
		PlayerMoneyDTO data = new PlayerMoneyDTO(value.getCountry(), value.getMoney());
		
		return MoneyControllerJsonHelper.dtoToJson(data);
	}

	public String addPlayersMoney(Request request, Response response) throws IOException {		
		String uid = readUidAndValidate(request);
		
		PlayerMoneyDTO incommingData = readDataAndValidate(request);
		
		saveMoney(incommingData, uid);
		
		response.status(HTTP_OK);
		return "";
	}

	private String readUidAndValidate(Request request) throws IOException {
		String uid = request.params(":uid");
		if( !isValidUid(uid) )
			throw new IOException("No valid userid found");
		return uid;
	}

	private PlayerMoneyDTO readDataAndValidate(Request request) throws IOException {
		PlayerMoneyDTO incommingData = MoneyControllerJsonHelper.jsonToDTO(request.body());
		if( !isValidDTO(incommingData) )
			halt(HTTP_BAD_REQUEST);
		return incommingData;
	}

	private boolean isValidDTO(PlayerMoneyDTO incommingData){
		return incommingData.getMoney() != null && incommingData.getCountry() != null;
	}

	private boolean isValidUid(String uid) {
		return uid != null && uid.trim().length() > 0;
	}
	
	private void saveMoney(PlayerMoneyDTO incommingData, String uid) {
		moneyService.add(uid,incommingData.getCountry(), incommingData.getMoney());
	}
}
