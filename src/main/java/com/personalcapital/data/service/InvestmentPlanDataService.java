package com.personalcapital.data.service;

import org.json.simple.JSONObject;

public interface InvestmentPlanDataService {

	JSONObject getPlanResponse(String index, String planName, String sponsorName, String sponsorState);

}
