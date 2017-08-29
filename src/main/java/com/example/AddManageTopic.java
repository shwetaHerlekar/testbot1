package com.example;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CORBA.portable.OutputStream;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class AddManageTopic
 */
public class AddManageTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddManageTopic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print("Hello get!!");	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//PrintWriter out = response.getWriter();
		//out.print("Hello post");	
		
		URL url = new URL("https://api.api.ai/v1/intents?v=20150910");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer 36e0d78753284c17ada1711f2dc6fc25");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		//conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		
		JSONObject obj = new JSONObject();
		obj.put("name", "union membership");
		obj.put("auto", new JSONArray());
		obj.put("contexts", new JSONArray());
		obj.put("templates", new JSONArray());
		obj.put("userSays", new JSONArray());
		obj.put("responses", new JSONArray());
		obj.put("priority", new JSONArray());
		
		OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
	    osw.write(obj.toString());
	    osw.flush();
	    osw.close();
	    int responseCode = conn.getResponseCode();
	}
	
	@SuppressWarnings("unchecked")
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		URL url = new URL("https://api.api.ai/v1/intents/613de225-65b2-4fa8-9965-c14ae7673826?v=2");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Authorization", "Bearer c17ce92704f14b0f85181127e2f0e6b6");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		
		
		JSONObject obj = new JSONObject();
		obj.put("name", "union membership");
		obj.put("auto", new JSONArray());
		obj.put("contexts", new JSONArray());
		obj.put("templates", new JSONArray());
		obj.put("userSays", new JSONArray());
		obj.put("responses", new JSONArray());
		obj.put("priority", new JSONArray());
		
		OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
	    osw.write(obj.toString());
	    osw.flush();
	    osw.close();
	    int responseCode = conn.getResponseCode();
	    System.out.println(responseCode);
	}

}
