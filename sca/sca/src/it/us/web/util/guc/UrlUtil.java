/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class UrlUtil
{
	public static String getUrlResponse( String urlString )
	{
		String ret = null;
		
		try
		{
			URL					url = new URL( urlString );
			URLConnection		uc = url.openConnection();
			uc.setConnectTimeout(3000);
			uc.setDoOutput( true );
			OutputStreamWriter	wr = new OutputStreamWriter( uc.getOutputStream() );
			wr.flush();
			wr.close();
			BufferedReader	br		= new BufferedReader( new InputStreamReader( uc.getInputStream() ) );
			StringBuffer	sb		= new StringBuffer();
			String			temp	= null;
			while( (temp = br.readLine()) != null )
			{
				sb.append( temp );
			}
			br.close();
			
			ret = sb.toString();
		}
		catch ( Exception e ){
			e.printStackTrace();
		}
		
		return ret;
	}
}
