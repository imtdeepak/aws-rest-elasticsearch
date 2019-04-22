package com.personalcapital.investing.service;

import org.json.simple.JSONObject;

public interface InvestmentPlanService {
	
	public JSONObject getPlanResponse(String planName, String sponsorName, String sponsorState);

}
