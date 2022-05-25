/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.base;







import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;




import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegistroTumoriRemoteUtil {
	

	
	
	public static void aggiuntiEsitoTumorale( String identificativoIstopatologico, Connection con ) throws Exception
	{

		


			
			
			ArrayList parameters = new ArrayList();
			PreparedStatement st = null;
			ResultSet rs = null;
			//parameters.add(identificativo);
			try {
				

					String query = "select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')";
					st = con.prepareStatement(query);
					rs = st.executeQuery();
	
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		
	}
		
	}
