package com.deepak.amazonaws.lambda.elasticsearch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.deepak.amazonaws.lambda.elasticsearch.model.ERequest;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ElasticFunctionHandlerTest {

	private static ERequest input;

	ElasticFunctionHandler handler = mock(ElasticFunctionHandler.class);

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
		when(handler.handleRequest(input, ctx)).thenReturn("Elastic Search result, " + input.getPlanName());

		String output = handler.handleRequest(input, ctx);

		Assert.assertEquals("Elastic Search result, " + input.getPlanName(), output);
	}
}
