/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.Driver;

/******************************************
 * GESTIONE DEI DB PRESENTI NEI METADATI  *
*******************************************/
public class Db {
	
	//INFO CONNESSIONE DB METADATI
	private String meta_name = ApplicationProperties.getProperty( "DATABASE" );
	private String meta_ip = ApplicationProperties.getProperty( "IP" );
	private String meta_port = ApplicationProperties.getProperty( "PORT" );
	private String meta_user = ApplicationProperties.getProperty( "USERNAME" );
	private String meta_password = ApplicationProperties.getProperty( "PASSWORD" );
	
	private ArrayList<String> lista = new ArrayList<String>(); //LISTA DEI DB

	public ArrayList<String> getLista() {
		return lista;
	}
	
	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
	/****************************************
	 * LISTA DB SPECIFICATI NEI METADATI	*
	 * INPUT NULL							*	
	 * OUTPUT NULL							*
	*****************************************/
	public void buildLista(){
		ArrayList<String> l = new ArrayList<String>();
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);

			String query = "SELECT alias FROM db_info ORDER BY alias";
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);
	
			while(rs.next())
			{ 
				l.add(rs.getString("alias"));
			}	
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if (l.size()>0) 
			setLista(l);
	}

	/********************************
	 * NOME DEL DB SELEZIONATO		*
	 * INPUT STRING ID DATABASE		*
	 * OUTPUT STRING NOME DATABASE	*
	 ********************************/
	public String getDBName(String id){
		String name=null;
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);

			String query = "SELECT nome FROM db_info WHERE id_db="+id+"";
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);
			while (rs.next()){
				name=rs.getString("nome");
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return name;
		
	}
	
	/************************************
	 * INFO DEL DB (es ip,porta,ecc)	*
	 * INPUT STRING NOME_DB O ALIAS_DB, *
	 * 		 STRING CAMPO				*
	 * OUTPUT STRING VALORE CAMPO		*
	 ************************************/
	public String getFieldValue(String db, String f){
		String fieldString=null;
		int fieldInt = 0;
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);

			String query = "SELECT "+f+" FROM db_info WHERE alias='"+db+"'";
			
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);
			
			if(f.equals("id_db")){
				while(rs.next()){ 
					fieldInt = rs.getInt(f);
				}
			}
			else {
				while(rs.next()){ 
					fieldString = rs.getString(f);
				}
			}	
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if (f.equals("id_db"))
			return Integer.toString(fieldInt);
		else 
			return fieldString;
	}
	
	
	public String getAlias (String id_db){
		String query = "SELECT alias FROM db_info WHERE id_db="+id_db;
		String alias = null;
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);
			
			while(rs.next()){ 
				alias = rs.getString(1);
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return alias;
	}
	
	/****************************
	 * QUERY DI UN DB			*
	 * INPUT STRING ID DATABASE	*
	 * 		 STRING TIPO QUERY	*
	 * OUTPUT STRING QUERY		*
	 ****************************/
	public String getQuery(String id_db, String tipo){
		String query=null;
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);

			String q = "SELECT valore FROM query WHERE id_db="+id_db+" and tipo='"+tipo+"'";
			statement = connection.createStatement();		
			rs = statement.executeQuery(q);
			
			while(rs.next()){ 
				query = rs.getString("valore");
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return query;
	}	
}