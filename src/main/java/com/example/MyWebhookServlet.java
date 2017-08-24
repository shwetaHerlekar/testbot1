package com.example;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ai.api.model.Fulfillment;
import ai.api.web.AIWebhookServlet;

// [START example]
@SuppressWarnings("serial")
public class MyWebhookServlet extends AIWebhookServlet  {

	
@Override
protected void doWebhook(AIWebhookRequest input, Fulfillment output) {
	// TODO Auto-generated method stub
	System.out.println(input.getResult().getAction());
	output.setSpeech(input.getResult().getAction());
	
}

}