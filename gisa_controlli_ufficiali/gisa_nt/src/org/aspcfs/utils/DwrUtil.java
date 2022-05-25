/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.directwebremoting.extend.LoginRequiredException;

public class DwrUtil {




	static Logger logger = Logger.getLogger("MainLogger");


	public static Object [] getValoriComboComuni1Asl (int idAsl)
	{

		Object [] ret	= new Object [2] ; 

		HashMap<Integer, String> valori =new  HashMap<Integer,String>();
		

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= 	"select id,nome from comuni1 c,lookup_site_id asl where c.notused is null and c.codiceistatasl=asl.codiceistat and asl.enabled=true "	;

			if(idAsl!= -1 && idAsl!= -2)
			{
				select+= " and asl.code = ? order by nome ";
			}
			else
			{
				select += " order by nome ";
			}

			pst = db.prepareStatement(select);
			if(idAsl!= -1 && idAsl!= -2)
			{
				pst.setInt(1, idAsl);
			}
			rs = pst.executeQuery();
			int i = 1;

			while ( rs.next() )
			{
				String 	value	= rs.getString("nome")	;
				int id = rs.getInt("id");
				valori.put(id, value);

			}
			Object [] ind	= new Object [valori.size()+1];
			Object [] val	= new Object [valori.size()+1];

			ind [0]	= ""				;
			val [0]	= "                "	;

			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection( db);
		}

		return ret;

	}	
	
	public static Object [] getValoriAsl(int idcomune)
	{
		Object [] ret	= new Object [2] ; 
		HashMap<Integer, String> valori =new  HashMap<Integer,String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= 	"select code, description from lookup_site_id where enabled = true "	;

			if(idcomune>0)
				select+= " and codiceistat IN (select codiceistatasl from comuni1 where notused is null and id = ? ) ";
			
			
			pst = db.prepareStatement(select);
			if(idcomune>0)
				pst.setInt(1, idcomune);
			rs = pst.executeQuery();
			int i = 0;

			while ( rs.next() )
			{
				int 	code 	= rs.getInt("code")				;
				String 	value	= rs.getString("description")	;
				valori.put(code, value);

			}
			Object [] ind	= new Object [valori.size()];
			Object [] val	= new Object [valori.size()];



			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	  
}
