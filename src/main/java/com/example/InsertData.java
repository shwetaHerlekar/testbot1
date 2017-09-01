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

import java.util.logging.Logger;

/**
 * Servlet implementation class InsertData
 */
public class InsertData extends HttpServlet {
	private static final Logger log = Logger.getLogger(InsertData.class.getName());
	private static final long serialVersionUID = 1L;
	static String JDBC_DRIVER = "com.mysql.jdbc.GoogleDriver";  
	static String DB_URL = "";
	static String USER = "root";
	static String PASS = "root";
	//static Connection conn = null;
	//static Statement stmt = null;   
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
			
			/*Class.forName(JDBC_DRIVER);
			DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url");

			//System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);*/
			
			//conn = createDBConnection();
			
			String path = InsertData.class.getResource("/sample_data.xlsx").getPath();
			FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            
            String[] headers = new String[55];
            String[] cRow = new String[55];
            boolean firstRow = true ;
            int index = 0;
            int RowCount = 0;
           
            while (iterator.hasNext()) {
            	
            	log.info("row count :"+RowCount);
            	
            	index = 0;
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                //out.println("inside main while");

                while (cellIterator.hasNext()) {
                	
                	//out.println("inside second while");
                    Cell currentCell = cellIterator.next();
                    
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
             	   //insertTopic(cRow[0]);
             	   //insertSubTopic(cRow[1], cRow[0], out);
             	   
                	if(RowCount<2)
                	{
                		insertLawDesc(headers, cRow, out);
                		RowCount++;
                	}
                	
             	   //insertQuestion(cRow[2], cRow[0], cRow[1], out);
                }
                else
                {
                	//insertState(headers, "US", out);
                	
                }
               firstRow = false;
               
            }
            
			
		} catch (Exception e) {
			out.print("exception!!");
			out.print(e);
			//System.out.println("exception!!");
			//e.printStackTrace();
		}
	  out.println("Good Bye!!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void insertTopic(String topic) throws SQLException, ClassNotFoundException {
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		int t = stmt.executeUpdate("insert into Topics(topic_name) Values('"+topic+"')");
		stmt.close();
		conn1.close();
		
	}
	
	public void insertSubTopic(String subtopic, String topic,PrintWriter out) throws SQLException, ClassNotFoundException {
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		int topic_id = getTopicId(topic, out);
		//out.println(subtopic);
		int t = stmt.executeUpdate("insert into SubTopics(sub_topic_name,topic_id) Values('"+subtopic+"','"+topic_id+"')");
		conn1.close();
		
	}
	
	public int getTopicId(String topic, PrintWriter out) throws SQLException, ClassNotFoundException{
		
		//out.println(topic);
		//log.info(topic);
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		ResultSet rs = stmt.executeQuery("select topic_id from Topics where topic_name='"+topic+"';");
		int id=-1;
		while(rs.next()){
	         //Retrieve by column name
	         id  = rs.getInt("topic_id");
	        //out.println("topic id:"+id);
	        rs.close();
	        stmt.close();
	        conn1.close();
	         return id;
	      }
		return id;
	}
	
	public void insertState(String[] headers, String country,PrintWriter out) throws SQLException, ClassNotFoundException {
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		out.println("inside state");
		
		for (int i = 5; i < headers.length; i++) {
			out.println(headers[i]);
			int t1 = 1;
			int t = stmt.executeUpdate("insert into State(state_name,country_id) Values('"+headers[i]+"','"+t1+"')");
		}
		conn1.close();
		stmt = null;
	}
	
	public void insertLawDesc(String[] headers, String[] curRow,PrintWriter out) throws SQLException, ClassNotFoundException {
		out.println("inside law desc");
		log.info("law_desc_raw_cnt :"+law_id);
		for (int i = 3; i < curRow.length; i++) {
			
			
			//out.println(law_id);
			//law_id++;
			//curRow[i] = curRow[i].replace("'","''");
			curRow[i] = curRow[i].replaceAll("\'", "");
			
			if(i==3)
			{
				log.info("federal");
				log.info("i :"+i);
				int id = 1;
				int id1= getSubTopicId(curRow[1], out);
				Connection conn1 = createDBConnection();
				Statement stmt = conn1.createStatement();
				int t = stmt.executeUpdate("insert into Law_Description(law_description,country_id,sub_topic_id) Values('"+curRow[i]+"','"+id+"','"+id1+"')");
				stmt.close();;
				conn1.close();
				
			}
			else
			{
				int id = 1;
				int id1 = getstateId(headers[i], out);
				int id2 = getSubTopicId(curRow[1], out);
				log.info("i :"+i);
				//log.info("id2:"+id2);
				Connection conn1 = createDBConnection();
				Statement stmt = conn1.createStatement();
				int t = stmt.executeUpdate("insert into Law_Description(law_description,state_id,country_id,sub_topic_id) Values('"+curRow[i]+"','"+id1+"','"+id+"','"+id2+"')");
				stmt.close();
				conn1.close();
				
			}
		}
    }
	
	
	public int getstateId(String state, PrintWriter out) throws SQLException, ClassNotFoundException{
		//out.println(state);
		log.info(state);
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		ResultSet rs = stmt.executeQuery("select state_id from State where state_name='"+state+"';");
		int id=-1;
		while(rs.next()){
	         //Retrieve by column name
	         id  = rs.getInt("state_id");
	         //out.println("subtopicid"+id);
	         rs.close();
		     stmt.close();
	         conn1.close();
	         return id;
	      }
		return id;
	} 
	
	public int getSubTopicId(String subtopic, PrintWriter out) throws SQLException, ClassNotFoundException{
		//out.println(subtopic);
		Connection conn1 = createDBConnection();
		Statement stmt = conn1.createStatement();
		ResultSet rs = stmt.executeQuery("select sub_topic_id from SubTopics where sub_topic_name='"+subtopic+"';");
		int id=-1;
		while(rs.next()){
	         //Retrieve by column name
	         id  = rs.getInt("sub_topic_id");
	         //out.println(id);
	         rs.close();
		 stmt.close();
	         conn1.close();
	         return id;
	      }
		return id;
	}
	
	public void insertQuestion(String question, String topic,String subtopic,PrintWriter out) throws SQLException, ClassNotFoundException {
		question = question.replaceAll("\'", "");
		Connection conn1 = createDBConnection();
		out.println(question);
		int topic_id = getTopicId(topic, out);
		int sub_topic_id = getSubTopicId(subtopic, out);
		Statement stmt = conn1.createStatement();
		int uid = 1;
		String sql = "insert into QuestionsMgnt(possible_questions,questions_type,User_id,topic_id,sub_topic_id) Values('"+question+"','SYSTEM','"+uid+"','"+topic_id+"','"+sub_topic_id+"')";
		//out.println(sql);
		int t = stmt.executeUpdate(sql);	
		conn1.close();
		stmt = null;
	}
	
	public Connection createDBConnection() throws ClassNotFoundException, SQLException{
		Class.forName(JDBC_DRIVER);
		DB_URL = System.getProperty("ae-cloudsql.cloudsql-database-url");
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
}
