package com.personalcapital.investing.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.elasticsearch.search.SearchHits;
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
	private SearchHits mockResponse;

	@BeforeClass
	public static void createInput() throws IOException {
		input = new ERequest();
		input.setPlanName("NYCHSRO/MEDREVIEW, INC 401(K) PLAN");
		input.setSponsorState("NY");

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

		SearchHits output = handler.handleRequest(input, ctx);
//		System.out.println(output.totalHits);

		Assert.assertEquals(output,mockResponse);
	}
}
