/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.servlet;

import java.io.IOException;
import java.util.List;

import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.util.properties.Application;

public class SettaAmbiente {

	private String ambiente;
	private String dbMode;
	
	public void SettaAmbiente(){}
	
	public void check() throws IOException{
		
		// CONTROLLA SE IL SISTEMA E' MASTER O SLAVE
		Persistence persistence  = PersistenceFactory.getPersistence();
		List<String> result = persistence.createSQLQuery("show transaction_read_only").list();

		for( String a: result )
		{
			if (a.equals("on")){  //DB READONLY
				setDbMode("slave");
				System.out.println("DB READ ONLY MODE");
			}
			else {				  //DB READ/WRITE
				setDbMode("master");
				System.out.println("DB READ/WRITE MODE");	
			}
		}
		
		//RICARICA IL FILE DI PROPERTIES ADATTO 
		Application.reloadProperties(Application.get("ambiente")); 
		
		setAmbiente(Application.get("ambiente"));
		
		PersistenceFactory.closePersistence( persistence, true );
		System.out.println("Caricato file di properties per : "+Application.get("ambiente"));
	}
	
	public String getAmbiente() {
		return ambiente;
	}
	
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getDbMode() {
		return dbMode;
	}

	public void setDbMode(String dbMode) {
		this.dbMode = dbMode;
	}
	
}
