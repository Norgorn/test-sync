package ru.norgorn.moneySync.web.controllers;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.skyscreamer.jsonassert.JSONAssert;

import ru.norgorn.moneySync.web.requestHandler.impl.PlayerMoneyDTO;

public class MoneyControllerJsonHelperTest {

	@Test
	public void dtoToJson_fullData_ExpectedOK(){
		PlayerMoneyDTO dto = new PlayerMoneyDTO("ru", 1l);

		String json = MoneyControllerJsonHelper.dtoToJson(dto);

		JSONAssert.assertEquals("{\"money\":1,\"country\":\"ru\"}", json, false);
	}

	@Test
	public void jsonToDTO_SimpleData_ExpectedOK() throws IOException{
		PlayerMoneyDTO dto = new PlayerMoneyDTO("ru", 1l);
		
		PlayerMoneyDTO actual = MoneyControllerJsonHelper.jsonToDTO(
				"{\"money\":1,\"country\":\"ru\",\"dummy\":123}");

		assertTrue(new ReflectionEquals(actual).matches(dto));
	}
	
	@Test(expected=IOException.class)
	public void jsonToDTO_SimpleData_ExpectedErrorSameKeyMoney() throws IOException{
		MoneyControllerJsonHelper.jsonToDTO(
				"{\"money\":1,\"money\":2,\"country\":\"ru\",\"dummy\":123}");
	}
	
	@Test(expected=IOException.class)
	public void jsonToDTO_SimpleData_ExpectedErrorSameKeyCountry() throws IOException{
		MoneyControllerJsonHelper.jsonToDTO(
				"{\"money\":1,\"country\":\"ru\",\"country\":\"ru\",\"dummy\":123}");
	}
	
	@Test
	public void jsonToDTO_NullField_ExpectedOK() throws IOException{
		PlayerMoneyDTO dto = new PlayerMoneyDTO(null, 1l);
		
		PlayerMoneyDTO actual = MoneyControllerJsonHelper.jsonToDTO(
				"{\"money\":1,\"dummy\":123}");

		assertTrue(new ReflectionEquals(actual).matches(dto));
	}
	
	@Test(expected=NumberFormatException.class)
	public void jsonToDTO_WrongTypeMoney_ExpectedErrorSameKeyCountry() throws IOException{
		MoneyControllerJsonHelper.jsonToDTO(
				"{\"money\":\"abc\",\"country\":\"ru\"}");
	}
}
