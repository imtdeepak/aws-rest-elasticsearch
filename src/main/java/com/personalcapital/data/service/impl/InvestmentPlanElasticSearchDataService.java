package com.personalcapital.data.service.impl;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.personalcapital.data.service.InvestmentPlanDataService;

public class InvestmentPlanElasticSearchDataService implements InvestmentPlanDataService {
	
	@Override
	public SearchHits getPlanResponse(String index, String planName, String sponsorName, String sponsorState) {
		
		RestHighLevelClient client = getRestClient();
		
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		
		SearchHits hits = null;
		try {
			if (planName != null) {
				qb.must(QueryBuilders.matchQuery("PLAN_NAME", planName));
				
			}
			if (sponsorName != null) {
				qb.must(QueryBuilders.matchQuery("SPONSOR_DFE_NAME", sponsorName));
			}
			if (sponsorState != null) {
				qb.must(QueryBuilders.matchQuery("SPONS_DFE_MAIL_US_STATE", sponsorState));
			}
			
			SearchRequest searchRequest = new SearchRequest(index); 
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(qb);
			searchRequest.source(searchSourceBuilder);
			System.out.println(qb.toString());
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			hits = searchResponse.getHits();
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hits;
	}
	
	private RestHighLevelClient getRestClient() {

		String esURL = System.getenv("ES_URL");
		Integer port = Integer.valueOf(System.getenv("ES_PORT"));
		String scheme = System.getenv("ES_PROTOCOL");

		RestClientBuilder builder = RestClient.builder(new HttpHost(esURL, port, scheme));
//		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		builder.setFailureListener(new RestClient.FailureListener() {
			@Override
			public void onFailure(Node node) {

				System.out.println(node.getName() + " is down ");

			}
		});
		RestHighLevelClient restHighLevelClient= new RestHighLevelClient(builder);
		return restHighLevelClient;
	}

}
