package com.aurora.quicklinksservices.daos;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//import au.com.bytecode.opencsv.CSVReader;

public class ExceltoDB2 {

	public static void main(String[] args) throws IOException, NamingException,
			SQLException {
		int appid = 241;
//
//		String jndiName = "jdbc/PTLDB2Z";
//
//		Properties prop = new Properties();
//
//		prop.put(Context.INITIAL_CONTEXT_FACTORY,
//				"com.ibm.websphere.naming.WsnInitialContextFactory");
//
//		prop.put(Context.PROVIDER_URL, "iiop://localhost:10035");
//
//		InitialContext init = new InitialContext(prop);
//
//		DataSource source = (DataSource) init.lookup(jndiName);
//
//		System.out.println("DataSource!!!!!!" + source);
//
//	c	Connection con = source.getConnection("S05WTAH", "u39f$p41");
//
//		System.out.println("Connection----->" + con);
//
//		CSVReader reader = new CSVReader(new FileReader(
//				"C:\\Users\\N28355\\Desktop\\testnew.csv"));
//		int i=241;
//        while (i<=251) {
//        	 i++;
//			Statement stmt = con.createStatement();
//			StringBuffer sql = new StringBuffer();
//			sql.append("INSERT INTO S05DTDB.tpt2e_app_role (PT2E_ROLE_CD, PT2E_APPID, PT2E_SEQ_NO)  VALUES('EMP','");
//			sql.append(i + "',");
//			sql.append(0);
//			sql.append(")");
//			stmt.executeUpdate(sql.toString());
//			System.out.println(sql);
//			
//        }


	}
}
