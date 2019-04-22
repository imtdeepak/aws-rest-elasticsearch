package com.personalcapital.data.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.personalcapital.data.service.InvestmentPlanDataService;

public class InvestmentPlanElasticSearchDataService implements InvestmentPlanDataService {
	private JSONParser parser = new JSONParser();
	
	@Override
	public JSONObject getPlanResponse(String index, String planName, String sponsorName, String sponsorState) {
		RestClient restClient = getRestClient();
		String queryURL = new String("/" + index + "/_search?q=");
		Response elasticSearchResponse = null;
		StringBuffer entityBody = new StringBuffer();
		JSONObject jsonResponse = null;
		
		try {
			if (planName != null) {
				queryURL+= "PLAN_NAME:"+URLEncoder.encode(planName, "UTF-8");
			}
			if (sponsorName != null) {
				queryURL+="SPONSOR_DFE_NAME:"+URLEncoder.encode(sponsorName, "UTF-8");
			}
			if (sponsorState != null) {
				queryURL+="SPONS_DFE_MAIL_US_STATE:"+URLEncoder.encode(sponsorState, "UTF-8");
			}
			
			Request request = new Request("GET", queryURL);
			
			System.out.println("Query URL=" + queryURL);
			
			elasticSearchResponse = restClient.performRequest(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(elasticSearchResponse.getEntity().getContent()));

			
			String line = "";
			while ((line = rd.readLine()) != null) {
				entityBody.append(line);
			}
			jsonResponse = (JSONObject) parser.parse(entityBody.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonResponse;
	}
	
	private RestClient getRestClient() {

		String esURL = System.getenv("ES_URL");
		Integer port = Integer.valueOf(System.getenv("ES_PORT"));
		String scheme = System.getenv("ES_PROTOCOL");

		RestClientBuilder builder = RestClient.builder(new HttpHost(esURL, port, scheme));
		builder.setFailureListener(new RestClient.FailureListener() {
			@Override
			public void onFailure(Node node) {

				System.out.println(node.getName() + " is down ");

			}
		});
		RestClient restClient = builder.build();
		return restClient;
	}

}
