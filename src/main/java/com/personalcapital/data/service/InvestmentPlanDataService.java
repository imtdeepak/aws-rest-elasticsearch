package com.personalcapital.data.service;

import org.elasticsearch.search.SearchHits;

public interface InvestmentPlanDataService {

	SearchHits getPlanResponse(String index, String planName, String sponsorName, String sponsorState);

}
