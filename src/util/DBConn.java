package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBConn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getConn();
	}
	
	public static Connection getConn() {

		Connection cn = null;

		// run Application
		Properties p = new PropertiesUtil().getProperties();
		String url = p.getProperty("url");
		String id = p.getProperty("username");
		String password = p.getProperty("password");
		String driver = p.getProperty("driver");
		try {
			Class.forName(driver);
			cn = DriverManager.getConnection(url,id,password);
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		
//		//JNDI
//		Context ctx;
//		try {
//			ctx = new InitialContext();
//			DataSource ds = (DataSource) ctx.lookup("java:comp/env/AI_Bartender");
//			cn = ds.getConnection();
//			System.out.println("connection:"+cn.getCatalog()+"<br>");
//		} catch (NamingException|SQLException e) {
//			System.out.println("Error: "+e.getMessage());
//		}
		
		return cn;
	}
}
