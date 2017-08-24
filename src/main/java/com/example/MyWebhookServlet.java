package com.example;

import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.JsonElement;
import ai.api.model.AIOutputContext;
import ai.api.model.Fulfillment;
import ai.api.web.AIWebhookServlet;

// [START example]
@SuppressWarnings("serial")
public class MyWebhookServlet extends AIWebhookServlet  { 

	//https://ai-ml-eychat.appspot.com/webhook  
	@Override
	protected void doWebhook(AIWebhookRequest input, Fulfillment output) {


		// TODO Auto-generated method stub
		String action = input.getResult().getAction();
		HashMap<String, JsonElement> parameter = input.getResult().getParameters();

		switch (action) {
		case "overtime":
		case "overtime_federal":
		case "overtime_federal_more_yes":
		case "overtime_states":
		case "overtime_states_no":
		case "compliance_expert_yes":
			output.setSpeech(parameter.toString());
			break;
		case "query":
			String topic = parameter.get("topics").toString().replaceAll("^\"|\"$", "");
			String law_scope = parameter.get("law_scope").toString().replaceAll("^\"|\"$", "");
			output  = getQueryResponse(topic,law_scope.toUpperCase() , output );
			break;
		case "state_laws": 

			break;

		default:
			break;
		}
		//output.setSpeech(input.getResult().toString());

	}

	protected Fulfillment getQueryResponse(String topic , String law_scope , Fulfillment output){
		ServletContext conetxt = getServletContext();

		String pathToDd = conetxt.getRealPath("/WEB-INF/db.txt");

		JSONParser parser = new JSONParser();
		Object obj = null;
		String response = "No Response!!!" ;
		if (law_scope.equals("FEDERAL")) {
			try {
				obj = parser.parse(new FileReader(pathToDd));
				JSONObject jsonObject = (JSONObject) obj;
				JSONObject obj1 = (JSONObject)	jsonObject.get(topic.toUpperCase().trim());
				response = "This is what I found about federal law on" + topic+ ". \n" ;
				response += obj1.get(law_scope).toString();
				output.setDisplayText(response + "\n\nDoes this help?\n :obYes:cb :obNo:cb");
				output.setSpeech(response + "\n \n This is what I found. Does it help ?");
				AIOutputContext contextOut = new AIOutputContext();
				//webhook_res["contextOut"].append({"name":"complaince_expert", "lifespan":2,"parameters":{ "topic": topic} })
				HashMap<String, JsonElement> outParameters = new HashMap<String , JsonElement>();
				JsonElement contextOutParameter=(JsonElement) parser.parse(topic);				
				outParameters.put("topic",contextOutParameter );
				contextOut.setLifespan(2);
				contextOut.setName("complaince_expert");
				contextOut.setParameters(outParameters);
				output.setContextOut();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return output ;
	}
}