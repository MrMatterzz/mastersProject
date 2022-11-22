package com.PTO;


import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	
	public static java.sql.Date convertStringIntoSqlDate(String dateIn) {
		
		if (dateIn == null || dateIn.isEmpty()	|| dateIn.equals("null") || dateIn.length() == 0) {
			return null;
	
		}
		
		try {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");          
		   java.util.Date dateInUtil = (java.util.Date) format.parse(dateIn);		
           java.sql.Date dateOut = new java.sql.Date(dateInUtil.getTime());
           
           return dateOut;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
