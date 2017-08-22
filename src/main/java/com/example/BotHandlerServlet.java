package com.example;
import java.io.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;



// [START example]
@SuppressWarnings("serial")
public class BotHandlerServlet extends HttpServlet {

	private AIDataService aiDataService;
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    /*PrintWriter out = resp.getWriter();
    out.println("Hello Servlet!!");*/
    
	String sessionId = req.getParameter("sessionId");
	try{
	
	AIConfiguration aiConfig = new AIConfiguration("c17ce92704f14b0f85181127e2f0e6b6");
	aiDataService = new AIDataService(aiConfig);
	AIResponse aiResponse = request(req.getParameter("query"), sessionId);
	resp.setContentType("text/plain");
    resp.getWriter().append(aiResponse.getResult().getFulfillment().getSpeech());
	}
	
	catch(Exception e){
	
	}
  }
  
  public AIResponse request(AIRequest aiRequest, HttpSession session)
      throws AIServiceException {
    return request(aiRequest,
        (session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
	}
  
  public AIResponse request(String query, HttpSession session) throws AIServiceException {
    return request(new AIRequest(query),
        (session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
	}
  
  public AIResponse request(AIRequest aiRequest, AIServiceContext serviceContext)
      throws AIServiceException {
    return aiDataService.request(aiRequest, serviceContext);
	}
	
	public AIResponse request(String query, String sessionId) throws AIServiceException {
    return request(new AIRequest(query),
        (sessionId != null) ? AIServiceContextBuilder.buildFromSessionId(sessionId) : null);
	}
  
}