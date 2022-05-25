/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.util.properties.Application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import cacheonix.cache.Cache;
import cacheonix.Cacheonix;

public final class DataCaching {

   // Set up the database driver
   //private static final Driver driver;
   static Persistence persistence = null;
  // Class.forName("org.postgresql.Driver");
   static Connection connection = null;
   
   
   
   public DataCaching(Connection conn)
   {
	   connection = conn; 
   }
   
   
//   static {
//
//      try {
//
//    	  if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
//	  		{
//	  			connection = DriverManager.getConnection("jdbc:postgresql://dbServerBdu:5432/canina","postgres", "postgres");
//	  		}
//	  		else
//	  		{
//	  			persistence = PersistenceFactory.getPersistenceCanina();
//	  		}
//    	  
//         //driver = (Driver) driverClass.newInstance();
//      } catch (Exception e) {
//
//         throw new IllegalStateException(e.toString());
//      }
//   }


   public QueryResult execute(final String queryText, final List queryParameters)
           throws IOException, ClassNotFoundException, IllegalAccessException,
           InstantiationException, SQLException 
           {
	   			   			
		     // Get result from cache
	   		/*final Cache queryCache = Cacheonix.getInstance().getCache("query.cache");
			final QueryKey queryKey = new QueryKey(queryText, queryParameters);
		    QueryResult queryResult = (QueryResult) queryCache.get(queryKey);*/
		    QueryResult queryResult = null;
	   
	   
		      if (queryResult == null) {
		         
		         // Not in cache, get the result from the database
		         Connection conn = null;
		         PreparedStatement ps = null;
		         ResultSet rs = null;
		
		         try {
		            //conn = driver.connect("my/connection/URL", new Properties());

		        	ps = connection.prepareStatement(queryText);
		        		

		            
		
		            // Set queryParameters
		            for (int i = 1; i <= queryParameters.size(); i++) {
		               final Object parameter = queryParameters.get(i - 1);
		               ps.setObject(i, parameter);
		            }
		
		            // Execute the statement and retrieve the result
		            //final List rows = new ArrayList();
		            final List rows = new ArrayList();
		            rs = ps.executeQuery();
		            final int columnCount = rs.getMetaData().getColumnCount();
		            //METADATA DEI RISULTATI
		            ResultSetMetaData metaData = rs.getMetaData();
		            
		            while (rs.next()) {
		               //final Object[] row = new Object[columnCount];
		               final HashMap<String,Object> row = new HashMap<String,Object>();
		               for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		                  //row[columnIndex] = rs.getObject(columnIndex);
		            	  String nomeColonna = metaData.getColumnName(columnIndex);
		            	  Object valueColonna =rs.getObject(columnIndex); 
		                  row.put(nomeColonna, valueColonna);
		                  
		               }
		               rows.add(row);
		            }
		
		            // Create query result
		            queryResult = new QueryResult(columnCount, rows);
		
		            // Put the result to cache
		            //queryCache.put(queryKey, queryResult);
		            
		         } finally {
		            
		            // Close result set
		            if (rs != null) {
		               
		               try {
		                  rs.close();
		               } catch (SQLException ignored) {
		               }
		            }
		
		            // Close prepared statement
		            if (ps != null) {
		               
		               try {
		                  ps.close();
		               } catch (SQLException ignored) {
		               }
		            }
		
		            // Close connection
		            if (conn != null) {
		               
		               try {
		                  conn.close();
		               } catch (SQLException ignored) {
		               }
		            }
		         }
		
		      }
		      else {
		    	  //System.out.println("Query in CACHE "+queryKey.getQueryText());
		      }
		
		      return queryResult;
		   }
}