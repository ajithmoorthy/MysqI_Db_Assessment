package com.atmecs.mysqlAssessment.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseReader {
	protected Connection con=null;
	ResultSet rs=null;
	Statement stmt=null;
	public DataBaseReader() {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost/assesmenttwo" , "root", "Ambtech003@230");
		} catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}  
	}
	public String getCellData(String tableName,String columnheader,String uniqueId) throws SQLException {
		String cellData = null;
		stmt=con.createStatement();
		String readQuery="Select "+columnheader+" from "+tableName+" where TestCase_Id="+"'"+uniqueId+"'"+";";
		rs=stmt.executeQuery(readQuery);
		while(rs.next())
		{
		cellData=rs.getString(columnheader).toString();
		}
		return cellData;
	}
}
