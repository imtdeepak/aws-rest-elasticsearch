package com.personalcapital.investing.service;

import org.elasticsearch.search.SearchHits;

public interface InvestmentPlanService {
	
	public SearchHits getPlanResponse(String planName, String sponsorName, String sponsorState);

}
