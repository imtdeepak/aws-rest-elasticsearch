package com.personalcapital.investing.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.lambda.runtime.Context;
import com.personalcapital.investing.dto.ERequest;

public class InvestmentPlanHandlerTest {

	private static ERequest input;

	InvestmentPlanHandler handler = mock(InvestmentPlanHandler.class);
	
	@Mock
	private JSONObject mockResponse;

	@BeforeClass
	public static void createInput() throws IOException {
		input = new ERequest();
		input.setPlanName("plan1");

	}

	private Context createContext() {
		TestContext ctx = new TestContext();
		ctx.setFunctionName("ElasticFunctionHandler.handleRequest");
		return ctx;
	}

	@Test
	public void testElasticFunctionHandler() {
		Context ctx = createContext();
		 
		
		when(handler.handleRequest(input, ctx)).thenReturn(mockResponse);

		JSONObject output = handler.handleRequest(input, ctx);

		Assert.assertEquals(output,mockResponse);
	}
}
