package com.personalcapital.investing.handler;

import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.personalcapital.investing.dto.ERequest;
import com.personalcapital.investing.service.InvestmentPlanService;
import com.personalcapital.investing.service.impl.InvestmentPlanServiceImpl;

public class InvestmentPlanHandler implements RequestHandler<ERequest, JSONObject> {
	
	private InvestmentPlanService investmentPlanService = new InvestmentPlanServiceImpl();

	@Override
	public JSONObject handleRequest(ERequest input, Context context) {
		
		JSONObject  investmentPlans=investmentPlanService.getPlanResponse(input.getPlanName(), input.getSponsorName(), input.getSponsorState());
		
		return investmentPlans;
		
	}
}
