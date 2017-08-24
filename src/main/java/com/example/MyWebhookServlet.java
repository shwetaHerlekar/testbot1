package com.example;

import java.io.*;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ai.api.model.Fulfillment;
import ai.api.web.AIWebhookServlet;

// [START example]
@SuppressWarnings("serial")
public class MyWebhookServlet extends AIWebhookServlet  {

	
@Override
protected void doWebhook(AIWebhookRequest input, Fulfillment output) {
	// TODO Auto-generated method stub
	System.out.println(input.getResult().getAction());
	String action = input.getResult().getAction();
	HashMap<String, JsonElement> parameter = input.getResult().getParameters();
	switch (action) {
	case "overtime":
	case "overtime_federal":
	case "overtime_federal_more_yes":
	case "overtime_states":
	case "overtime_states_no":
	case "compliance_expert_yes":
	case "query":
	case "state_laws": 
		output.setSpeech("from Webhook");
		break;

	default:
		break;
	}
	//output.setSpeech(input.getResult().toString());
	
}

}