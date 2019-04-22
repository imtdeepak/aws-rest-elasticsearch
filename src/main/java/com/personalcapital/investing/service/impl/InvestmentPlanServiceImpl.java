package com.personalcapital.investing.service.impl;

import org.elasticsearch.search.SearchHits;

import com.personalcapital.data.service.InvestmentPlanDataService;
import com.personalcapital.data.service.impl.InvestmentPlanElasticSearchDataService;
import com.personalcapital.investing.service.InvestmentPlanService;

public class InvestmentPlanServiceImpl implements InvestmentPlanService {
	private static String ES_INDEX_NAME = "plan-sponsor";
	
	private InvestmentPlanDataService investmentPlanDataService = new InvestmentPlanElasticSearchDataService();
	
	@Override
	public SearchHits getPlanResponse(String planName, String sponsorName, String sponsorState) {
		SearchHits investmentPlans=investmentPlanDataService.getPlanResponse(ES_INDEX_NAME, planName,sponsorName,sponsorState);
		return investmentPlans;
	}
	
	

}
