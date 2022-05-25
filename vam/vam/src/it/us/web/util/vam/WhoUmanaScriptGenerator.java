/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class WhoUmanaScriptGenerator
{

	public static void main(String[] args)
	{
		try
		{
			FileReader			fr	= new FileReader( "c:\\whoUmana.csv" );
			BufferedReader		br	= new BufferedReader( fr );
			FileOutputStream	fos	= new FileOutputStream( "c:\\whoUmana.sql" );
			
			String	lastPcode	= "xxx";
			int		lastPid		= -1;
			int		id			= 0;
			
			for( String temp = br.readLine(); temp != null; temp = br.readLine() )
			{
				String sql = "";
				
				String[] splitz = temp.split( ";" );
				String pText = splitz[0].replaceAll( "\"", "" ).trim().replaceAll( "'", "''" );
				String pCode = splitz[1].replaceAll( "\"", "" ).trim().replaceAll( "'", "''" );
				String fText = splitz[2].replaceAll( "\"", "" ).trim().replaceAll( "'", "''" );
				String fCode = splitz[3].replaceAll( "\"", "" ).trim().replaceAll( "'", "''" );
				
				
				
				if( !lastPcode.equalsIgnoreCase( pCode ) ) //nuovo padre
				{
					sql = "INSERT INTO lookup_esame_istopatologico_who_umana (id, codice, description, enabled, level, padre) VALUES (";
					sql += ( ++id + ", '" );
					sql += ( pCode + "', '" + pText + "', true, " + id*10 + ", NULL );\n" );
					lastPid		= id;
					lastPcode	= pCode;

					fos.write( sql.getBytes() );
				}

				sql = "INSERT INTO lookup_esame_istopatologico_who_umana (id, codice, description, enabled, level, padre) VALUES (";
				sql += ( ++id + ", '" );
				sql += ( fCode + "', '" + fText + "', true, " + id*10 + ", " + lastPid + " );\n" );
				
				fos.write( sql.getBytes() );
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
