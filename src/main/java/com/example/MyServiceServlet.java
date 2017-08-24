package com.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import ai.api.AIServiceException;
import ai.api.model.AIResponse;
import ai.api.web.AIServiceServlet;

/*@WebServlet(initParams = {
        @WebInitParam(name = MyServiceServlet.PARAM_API_AI_KEY,
        value = "c52536d801de4975a86024f33e87b511")
    })*/

public class MyServiceServlet extends AIServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getSession());
		try {

			AIResponse aiResponse = request(request.getParameter("message"), request.getParameter("sessionId"));
			response.setContentType("application/json");
			JSONObject obj = new JSONObject();
			obj.put("displayText", aiResponse.getResult().getFulfillment().getSpeech());
			obj.put("speech", aiResponse.getResult().getFulfillment().getSpeech());
			if(aiResponse.getResult().getFulfillment().getDisplayText()!=null)
			{
				obj.put("displayText", aiResponse.getResult().getFulfillment().getDisplayText());
			}	
			PrintWriter out = response.getWriter();
			out.print(obj);	
		} catch (AIServiceException e) {
			System.out.println("Exception accesing API AI");
			e.printStackTrace();
		}
	}
}