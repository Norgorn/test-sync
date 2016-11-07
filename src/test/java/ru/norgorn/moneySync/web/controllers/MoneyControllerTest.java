package ru.norgorn.moneySync.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;

import ru.norgorn.moneySync.persistence.MoneyRepository;
import ru.norgorn.moneySync.persistence.entity.Money;
import ru.norgorn.moneySync.service.MoneySyncServiceImpl;
import spark.HaltException;
import spark.Request;
import spark.Response;

public class MoneyControllerTest {
	
	MoneyRepository repository = mock(MoneyRepository.class);
	
	MoneyController sut = new MoneyController( new MoneySyncServiceImpl(repository));
	
	Request request;
	Response response = mock(Response.class);
	String responseJson;
	
	int status;
	
	@Before
	@SuppressWarnings("rawtypes")
	public void prepare(){
		reset(repository);
		when(repository.findOne("a")).thenReturn(new Money("a", "ru", 120));
		
		status = 0;
		doAnswer(new Answer() {
			  public Object answer(InvocationOnMock invocation) {
			      Object[] args = invocation.getArguments();
			      status = (Integer) args[0];
			      return null;
			  }}
		)
		.when(response).status(anyInt());
	}

	@Test
	public void getPlayersMoney_ExpectedOK() throws IOException{
		
		givenRequestWithUid("a");
		
		responseJson = sut.getPlayersMoney(request);
		
		thenReceiveValidResult();
	}
	
	@Test
	public void getPlayersMoney_ExpectedNotFound() throws IOException{
		
		givenRequestWithUid("b");
		
		try {
			
			responseJson = sut.getPlayersMoney(request);
			
			fail("Expected HaltException");
		} catch (HaltException e) {
			assertEquals("Expected NotFound code", 404, e.statusCode());
		}
	}
	
	@Test
	public void addPlayersMoney_ExpectedOk() throws IOException{
		
		givenRequestWithUid_And_ValidData("b");
		
		sut.addPlayersMoney(request, response);
		
		thenSaveToRepository();
	}
	
	@Test
	public void addPlayersMoney_ExpectedBadRequest() throws IOException{
		
		givenRequestWithUid_And_IncompleteData("b");
		
		try {
			sut.addPlayersMoney(request, response);
		} catch (HaltException e) {
			status = e.statusCode();
		}
		
		thenStopRequestProcessing();
	}
	
	
	
	public void givenRequestWithUid(String uid) {
		request = mock(Request.class);
		when(request.params(":uid")).thenReturn(uid);
	}
	
	public void givenRequestWithUid_And_ValidData(String uid) {
		givenRequestWithUid(uid);
		
		when(request.body()).thenReturn("{\"money\":1,\"country\":\"ru\"}");
	}
	
	public void givenRequestWithUid_And_IncompleteData(String uid) {
		givenRequestWithUid(uid);
		
		when(request.body()).thenReturn("{\"country\":\"ru\"}");
	}

	
	
	public void thenReceiveValidResult() {
		JSONAssert.assertEquals("{\"money\":120,\"country\":\"ru\"}", responseJson, false);
	}
	
	public void thenSaveToRepository() {
		assertEquals("Expected HTTP OK", 200, status);
		verify(repository).saveAndFlush(any(Money.class));
	}
	
	public void thenStopRequestProcessing() {
		assertEquals("Expected HTTP BAD REQUEST", 400, status);
		verifyZeroInteractions(repository);
	}
}
