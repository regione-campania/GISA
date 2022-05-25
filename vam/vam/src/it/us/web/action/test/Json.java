/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test;

import it.us.web.action.GenericAction;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONObject;
import it.us.web.util.properties.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class Json extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		URL					url = new URL( Application.get( "CANINA_GETINFOCANE_URL" ) + "985120021339767" );
		URLConnection		uc = url.openConnection();
		uc.setDoOutput( true );
		OutputStreamWriter	wr = new OutputStreamWriter(uc.getOutputStream());
		wr.flush();
		wr.close();
		BufferedReader br = new BufferedReader( new InputStreamReader( uc.getInputStream() ) );
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while( (temp = br.readLine()) != null )
		{
			sb.append( temp );
		}
		br.close(); 
		
		JSONObject json = new JSONObject( sb.toString() );
		JSONObject bean = (JSONObject)( (JSONObject)json.get( "ns:getInfoCaneResponse" ) ).get( "ns:return" );
		bean.remove( "@xmlns" );
		System.out.println( bean );
		System.out.println( bean.toString()
						.replaceAll( "ax24:", "" )
						.replaceAll( "\"\\$\":", "" )
						.replaceAll( ":\\{", ":" )
						.replaceAll( "\\},", "," )
						.replaceAll( "\"@xsi:nil\":\"true\"", "null" ) );
//		JsonObject j = new JsonObject();
		Gson g = new Gson( );
//		System.out.println( sb.toString() );
		Cane cane = g.fromJson( 
					bean.toString()
						.replaceAll( "ax24:", "" )
						.replaceAll( "\"\\$\":", "" )
						.replaceAll( ":\\{", ":" )
						.replaceAll( "\\},", "," )
						.replaceAll( "\"@xsi:nil\":\"true\"", "null" ),
					Cane.class );
		
		System.out.println( cane );
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
