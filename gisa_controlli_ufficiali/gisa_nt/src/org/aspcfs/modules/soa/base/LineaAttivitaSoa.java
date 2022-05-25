/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.soa.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;


public class LineaAttivitaSoa {

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private String impianto ;
	private String categoria ;
	public String getImpianto() {
		return impianto;
	}
	public void setImpianto(String impianto) {
		this.impianto = impianto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
//	public static  ArrayList<String> load_linea_attivita_per_cu(String idCu, Connection db )
//	{
//
//		ArrayList<String>		ret		= new ArrayList<String>();
//		PreparedStatement	stat	= null;
//		ResultSet			res		= null;
//		String temp = null ;
//		if( (idCu != null) && (idCu.trim().length() > 0) )
//		{
//			try
//			{
//				int iid = Integer.parseInt( idCu );
//							
//				String sql = "select * from linee_attivita_controlli_ufficiali_stab_soa where id_controllo_ufficiale =?";
//				stat	= db.prepareStatement( sql );
//				
//				stat.setInt( 1, iid );
//				res		= stat.executeQuery();
//				while( res.next() )
//				{
//					temp = res.getString("linea_attivita_stabilimenti_soa");
//					ret.add(temp);
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			finally
//			{
//				try
//				{
//					if( res != null )
//					{
//						res.close();
//						res = null;
//					}
//					
//					if( stat != null )
//					{
//						stat.close();
//						stat = null;
//					}
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		return ret;
//	
//	}
//	
	
}
