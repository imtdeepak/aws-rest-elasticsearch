package com.deepak.amazonaws.lambda.elasticsearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.deepak.amazonaws.lambda.elasticsearch.model.ERequest;

public class ElasticFunctionHandler implements RequestHandler<ERequest, String> {
	private static String ES_INDEX_NAME = "plan-sponsor";

	@Override
	public String handleRequest(ERequest input, Context context) {
		context.getLogger().log("Input: " + input);
		StringBuffer result = null;

		try {

//        	String url = "http://localhost:9200/plan-sponsor/_search?q=SPONS_DFE_MAIL_US_STATE:NY";
//			RestClient restClient = getRestClient();
			String esURL = System.getenv("ES_URL");
			Integer port = Integer.valueOf(System.getenv("ES_PORT"));
			String scheme = System.getenv("ES_PROTOCOL");
			context.getLogger().log("esURL="+esURL);
			context.getLogger().log("port="+port);
			context.getLogger().log("scheme="+scheme);
			
			RestClientBuilder builder = RestClient.builder(new HttpHost(esURL, port, scheme));
			builder.setFailureListener(new RestClient.FailureListener() {
				@Override
				public void onFailure(Node node) {

					System.out.println(node.getName() + " is down ");

				}
			});
			RestClient restClient = builder.build();
			context.getLogger().log(restClient.toString());
			StringBuffer queryURL = new StringBuffer("/" + ES_INDEX_NAME + "/_search?q=");
			
			Map<String, String> queryData=  new HashMap();
			
			if (input.getPlanName() != null) {
				queryData.put("PLAN_NAME",input.getPlanName());
				queryURL.append("PLAN_NAME:").append(input.getPlanName());
			}
			if (input.getSponsorName() != null) {
				queryData.put("SPONSOR_DFE_NAME",input.getSponsorName());
				queryURL.append("SPONSOR_DFE_NAME:").append(input.getSponsorName());
			}
			if (input.getSponsorState() != null) {
				queryData.put("SPONS_DFE_MAIL_US_STATE",input.getSponsorState());
				queryURL.append("SPONS_DFE_MAIL_US_STATE:").append(input.getSponsorState());
			}
			
//			QueryStringBuilder qbuilder = new QueryStringBuilder();
//		    for (Entry<String, String> pair : data.entrySet()) {
//		        builder.addQueryParameter(pair.getKey(), pair.getValue());
//		    }
//		    return builder.encode("UTF-8");
			Request request = new Request("GET", queryURL.toString());
			Response response = restClient.performRequest(request);
			context.getLogger().log("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result);

		} catch (Exception excptn) {
			context.getLogger().log("Exception =" + excptn);
		}
		return result.toString();
	}

//	private RestClient getRestClient() {
//
//		String esURL = System.getenv("ES_URL");
//		Integer port = Integer.getInteger(System.getenv("ES_PORT"));
//		String scheme = System.getenv("ES_PROTOCOL");
//
//		RestClientBuilder builder = RestClient.builder(new HttpHost(esURL, port, scheme));
//		builder.setFailureListener(new RestClient.FailureListener() {
//			@Override
//			public void onFailure(Node node) {
//
//				System.out.println(node.getName() + " is down ");
//
//			}
//		});
//		RestClient restClient = builder.build();
//		return restClient;
//	}
}
