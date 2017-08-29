package com.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

/**
 * Servlet implementation class SQLTestServlet
 */
public class SQLTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static String DB_URL = "jdbc:mysql://localhost:3031/students";
	static String USER = "root";
	static String PASS = "root";
	Connection conn = null;
	Statement stmt = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SQLTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("Hello SQL!!");	
		try {
			Class.forName("com.mysql.jdbc.GoogleDriver");
			DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url");

			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			out.println("con done");	
			stmt = conn.createStatement();
			int t = stmt.executeUpdate("insert into sample Values('Benifit', 'Group Insurance')");
			/*while(rs.next()){  
				out.println(rs.getString(1));
				out.println(rs.getInt(2));
			}*/
			conn.close();  
			out.println("good bye!");
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			out.print("exception!!");	
			e.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
