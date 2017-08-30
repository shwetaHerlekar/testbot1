package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertData
 */
public class InsertData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String JDBC_DRIVER = "com.mysql.jdbc.GoogleDriver";  
	static String DB_URL = "";
	static String USER = "root";
	static String PASS = "root";
	static Connection conn = null;
	static Statement stmt = null;   
	public int law_id=0;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertData() {
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
			
			Class.forName(JDBC_DRIVER);
			DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url");

			//System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String path = InsertData.class.getResource("/sample_data.xlsx").getPath();
			FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            
            String[] headers = new String[55];
            String[] cRow = new String[55];
            boolean firstRow = true ;
            int index = 0;
           
            while (iterator.hasNext()) {
            	
            	index = 0;
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                	
                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    	
                        //System.out.print(currentCell.getStringCellValue() + "--");
                        if(firstRow){
                        	headers[index] = currentCell.getStringCellValue();
                        	//System.out.println(headers[index]);
                        	index++;
                        }
                        else{
                        	cRow[index] = currentCell.getStringCellValue();
                        	index++;
                        }
                     
                        
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    	
                        //System.out.print(currentCell.getNumericCellValue() + "--");
                        if(firstRow){
                            headers[index] = currentCell.getStringCellValue();
                            index++;
                        }
                        else{
                        	cRow[index] = currentCell.getStringCellValue();
                        	index++;
                        }
                    }
                }
                if(!firstRow){
             	   insertTopic(conn,cRow[0]);
             	   insertSubTopic(conn, cRow[1], cRow[0], out);
             	   insertState(conn, headers, "US", out);
             	   insertLawDesc(conn, headers, cRow, out);
                }
               firstRow = false;
               //System.out.println(cRow[0]);
               
            }
            
            /*for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}
            System.out.println("colsize : "+headers.length);*/
			
		} catch (Exception e) {
			out.print("exception!!");	
			System.out.println("exception!!");
			e.printStackTrace();
		}
	  out.println("Good Bye!!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void insertTopic(Connection conn, String topic) throws SQLException {
		stmt = conn.createStatement();
		int t = stmt.executeUpdate("insert into Topics(topic_name) Values('"+topic+"')");
	}
	
	public void insertSubTopic(Connection conn, String subtopic, String topic,PrintWriter out) throws SQLException {
		stmt = conn.createStatement();
		int topic_id = getTopicId(conn, topic, out);
		//out.println(subtopic);
		int t = stmt.executeUpdate("insert into SubTopics(sub_topic_name,topic_id) Values('"+subtopic+"','"+topic_id+"')");
	}
	
	public int getTopicId(Connection conn, String topic, PrintWriter out) throws SQLException{
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select topic_id from Topics where topic_name='"+topic+"';");
		int id=-1;
		while(rs.next()){
	         //Retrieve by column name
	         id  = rs.getInt("topic_id");
	         //out.println(id);
	         return id;
	      }
		return id;
	}
	
	public void insertState(Connection conn, String[] headers, String country,PrintWriter out) throws SQLException {
		stmt = conn.createStatement();
		out.println("inside state");
		//int topic_id = getTopicId(conn, country, out);
		
		for (int i = 5; i < headers.length; i++) {
			out.println(headers[i]);
			int t1 = 1;
			int t = stmt.executeUpdate("insert into State(state_name,country_id) Values('"+headers[i]+"','"+t1+"')");
		}
		
		
	}
	
	public void insertLawDesc(Connection conn, String[] headers, String[] curRow,PrintWriter out) throws SQLException {
		out.println("inside law desc");
		for (int i = 4; i < curRow.length; i++) {
			
			out.println(curRow[i]);
			out.println(law_id);
			law_id++;
			//insertQuestion(conn, curRow[2], law_id, out);
			if(i==4)
			{
				int id = 1;
				int id1= getTopicId(conn, curRow[0], out);
				stmt = conn.createStatement();
				int t = stmt.executeUpdate("insert into Law_Description(law_description,country_id,topic_id) Values('"+curRow[4]+"','"+id+"','"+id1+"')");
			}
			else
			{
				int id = 1;
				int id1 = getstateId(conn, headers[i], out);
				int id2 = getTopicId(conn, curRow[i], out);
				stmt = conn.createStatement();
				int t = stmt.executeUpdate("insert into Law_Description(law_description,state_id,country_id,topic_id) Values('"+curRow[4]+"','"+id1+"','"+id+"','"+id2+"')");
			}
		}
		
	}
	
	public int getstateId(Connection conn, String state, PrintWriter out) throws SQLException{
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select state_id from State where state_name='"+state+"';");
		int id=-1;
		while(rs.next()){
	         //Retrieve by column name
	         id  = rs.getInt("state_id");
	         //out.println(id);
	         return id;
	      }
		return id;
	}
	
	public void insertQuestion(Connection conn, String question, int law_id1,PrintWriter out) throws SQLException {
		stmt = conn.createStatement();
		//int topic_id = getTopicId(conn, country, out);
		stmt = conn.createStatement();
		out.println(question);
		int uid = 1;
		int t = stmt.executeUpdate("insert into QuestionsMgnt(possible_questions,law_desc_id,questions_type,User_id) Values('"+question+"','"+law_id1+"','SYSTEM','"+uid+"')");	
	}
}