/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test;

import it.us.web.action.GenericAction;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.test.Organization;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONML;
import it.us.web.util.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewJson extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		Organization oo = new Organization();
		
		oo.setId( 2 );
		oo.setDataNascita( new Timestamp(System.currentTimeMillis()) );
		oo.setIdAsl( "adfgsdgf" );
		
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gb.setPrettyPrinting().setDateFormat( "yyyy-MM-dd HH:mm:ss" ).create();
		Gson gsonx = gb.create();
		System.out.println( gsonx.toJson( oo ) );
		
		String json = "{\r\n" + 
				"	\"organization\":{\r\n" + 
				"		\"#\":\"\",\r\n" + 
				"		\"id\":{\"#\":\"274558\"},\r\n" + 
				"		\"idAsl\":{\"#\":\"3\"},\r\n" + 
				"		\"aslString\":{\"#\":\"Napoli 1\"},\r\n" + 
				"		\"tipoProprietarioDetentore\":{\"#\":\"Operatore Commerciale\"},\r\n" + 
				"		\"regioneSociale\":{\"#\":\"MONDO ANIMALE\"},\r\n" + 
				"		\"codiceFiscale\":{\"#\":\"SPSGNN80H30F839K\"},\r\n" + 
				"		\"isUomo\":{\"#\":\"true\"},\r\n" + 
				"		\"nome\":{\"#\":\"GIOVANNI\"},\r\n" + 
				"		\"cognome\":{\"#\":\"ESPOSITO\"},\r\n" + 
				"		\"dataNascita\":{\"#\":\"1980-06-30 00:00:00.0\"},\r\n" + 
				"		\"luogoNascita\":{\"#\":\"NAPOLI\"},\r\n" + 
				"		\"documentoIdentita\":{\"#\":\"\"},\r\n" + 
				"		\"listaIndirizzi\":{\"#\":\"\",	\r\n" + 
				"			\"indirizzo\":[\r\n" + 
				"							{\"#\":\"\",\r\n" + 
				"							\"citta\":{\"#\":\"NAPOLI, NA\"},\r\n" + 
				"							\"nazione\":{\"#\":\"ITALY\"},\r\n" + 
				"							\"via\":{\"#\":\"CORSO GARIBALDI370\\/371\"},\r\n" + 
				"							\"tipologia\":{\"#\":\"Residenza\"},\r\n" + 
				"							\"cap\":{\"#\":\"\"}\r\n" + 
				"							},\r\n" + 
				"								\r\n" + 
				"							{	\"#\":\"\",\r\n" + 
				"								\"citta\":{\"#\":\"NAPOLI, NA\"},\r\n" + 
				"								\"nazione\":{\"#\":\"ITALY\"},\r\n" + 
				"								\"via\":{\"#\":\"CORSO GARIBALDI370\\/371\"},\r\n" + 
				"								\"tipologia\":{\"#\":\"Attivit? Commerciale\"},\r\n" + 
				"								\"cap\":{\"#\":\"\"}\r\n" + 
				"							}\r\n" + 
				"						]\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"} ";
		
		String json2 = "[\"organization\",[\"id\",274558],[\"idAsl\",3],[\"aslString\",\"Napoli 1\"],[\"tipoProprietarioDetentore\",\"Operatore Commerciale\"],[\"regioneSociale\",\"MONDO ANIMALE\"],[\"codiceFiscale\",\"SPSGNN80H30F839K\"],[\"isUomo\",true],[\"nome\",\"GIOVANNI\"],[\"cognome\",\"ESPOSITO\"],[\"dataNascita\",\"1980-06-30 00:00:00.0\"],[\"luogoNascita\",\"NAPOLI\"],[\"documentoIdentita\"],[\"listaIndirizzi\",[\"indirizzo\",[\"citta\",\"NAPOLI, NA\"],[\"nazione\",\"ITALY\"],[\"via\",\"CORSO GARIBALDI370/371\"],[\"tipologia\",\"Residenza\"],[\"cap\"]],[\"indirizzo\",[\"citta\",\"NAPOLI, NA\"],[\"nazione\",\"ITALY\"],[\"via\",\"CORSO GARIBALDI370/371\"],[\"tipologia\",\"Attivit? Commerciale\"],[\"cap\"]]]] ";
		
		String json3 = "{\"organization\":{\"id\":274558,\"idAsl\":3,\"aslString\":\"Napoli 1\",\"tipoProprietarioDetentore\":\"Operatore Commerciale\",\"regioneSociale\":\"MONDO ANIMALE\",\"codiceFiscale\":\"SPSGNN80H30F839K\",\"isUomo\":true,\"nome\":\"GIOVANNI\",\"cognome\":\"ESPOSITO\",\"dataNascita\":\"1980-06-30 00:00:00.0\",\"luogoNascita\":\"NAPOLI\",\"documentoIdentita\":null,\"listaIndirizzi\":{\"indirizzo\":[{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Residenza\",\"cap\":null},{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Attivit? Commerciale\",\"cap\":null}]}}} ";
		
		String json4 = "{\"id\":274558,\"idAsl\":3,\"aslString\":\"Napoli 1\",\"tipoProprietarioDetentore\":\"Operatore Commerciale\",\"regioneSociale\":\"MONDO ANIMALE\",\"codiceFiscale\":\"SPSGNN80H30F839K\",\"isUomo\":true,\"nome\":\"GIOVANNI\",\"cognome\":\"ESPOSITO\",\"dataNascita\":\"1980-06-30 00:00:00.0\",\"luogoNascita\":\"NAPOLI\",\"documentoIdentita\":null,\"listaIndirizzi\":{\"indirizzo\":[{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Residenza\",\"cap\":null},{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Attivit? Commerciale\",\"cap\":null}]}}";
		
		String jsonOK = "{\"id\":274558,\r\n" + 
				"\"idAsl\":3,\r\n" + 
				"\"aslString\":\"Napoli 1\",\r\n" + 
				"\"tipoProprietarioDetentore\":\"Operatore Commerciale\",\r\n" + 
				"\"regioneSociale\":\"MONDO ANIMALE\",\r\n" + 
				"\"codiceFiscale\":\"SPSGNN80H30F839K\",\r\n" + 
				"\"isUomo\":true,\r\n" + 
				"\"nome\":\"GIOVANNI\",\r\n" + 
				"\"cognome\":\"ESPOSITO\",\r\n" + 
				"\"dataNascita\":\"1980-06-30 00:00:00.0\",\r\n" + 
				"\"luogoNascita\":\"NAPOLI\",\r\n" + 
				"\"documentoIdentita\":null,\r\n" + 
				"\"listaIndirizzi\":\r\n" + 
				"	{\"indirizzo\":[\r\n" + 
				"		{\"citta\":\"NAPOLI, NA\",\r\n" + 
				"		\"nazione\":\"ITALY\",\r\n" + 
				"		\"via\":\"CORSO GARIBALDI370\\/371\",\r\n" + 
				"		\"tipologia\":\"Residenza\",\r\n" + 
				"		\"cap\":null},\r\n" + 
				"		{\"citta\":\"NAPOLI, NA\",\r\n" + 
				"		\"nazione\":\"ITALY\",\r\n" + 
				"		\"via\":\"CORSO GARIBALDI370\\/371\",\r\n" + 
				"		\"tipologia\":\"Attivit? Commerciale\",\r\n" + 
				"		\"cap\":null}]}} ";
		
		String jsonOOKK = "{\"id\":274558,\"idAsl\":3,\"aslString\":\"Napoli 1\"," +
				"\"tipoProprietarioDetentore\":\"Operatore Commerciale\",\"ragioneSociale\":\"MONDO ANIMALE\"," +
				"\"codiceFiscale\":\"SPSGNN80H30F839K\",\"isUomo\":true,\"nome\":\"GIOVANNI\",\"cognome\":\"ESPOSITO\"," +
				"\"dataNascita\":\"1980-06-30 00:00:00.0\",\"luogoNascita\":\"NAPOLI\",\"documentoIdentita\":null," +
				"\"listaIndirizzi\":{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Residenza\",\"cap\":null,\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Attivit? Commerciale\",\"cap\":null}} ";
		
		String jsonOK3 = "{\"id\":274558,\"idAsl\":3,\"aslString\":\"Napoli 1\",\"tipoProprietarioDetentore\":\"Operatore Commerciale\"," +
				"\"ragioneSociale\":\"MONDO ANIMALE\",\"codiceFiscale\":\"SPSGNN80H30F839K\",\"isUomo\":true," +
				"\"nome\":\"GIOVANNI\",\"cognome\":\"ESPOSITO\",\"dataNascita\":\"1980-06-30 00:00:00.0\"," +
				"\"luogoNascita\":\"NAPOLI\",\"documentoIdentita\":null,\"listaIndirizzi\":[{\"citta\":\"NAPOLI, NA\"," +
				"\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Residenza\",\"cap\":null}," +
				"{\"citta\":\"NAPOLI, NA\",\"nazione\":\"ITALY\",\"via\":\"CORSO GARIBALDI370\\/371\",\"tipologia\":\"Attivit? Commerciale\",\"cap\":null}]} ";
		
		String jsonOK4 = "{\r\n" + 
				"    \"id\":249724,\r\n" + 
				"    \"idAsl\":3,\r\n" + 
				"    \"aslString\":\"Napoli 1\",\r\n" + 
				"    \"tipoProprietarioDetentore\":\"Privato\",\r\n" + 
				"    \"ragioneSociale\":\"ABATANGELO, GIOVANNI\",\r\n" + 
				"    \"codiceFiscale\":\"BTNGNN63H24F839Q\",\r\n" + 
				"    \"isUomo\":true,\r\n" + 
				"    \"nome\":\"GIOVANNI\",\r\n" + 
				"    \"cognome\":\"ABATANGELO\",\r\n" + 
				"    \"dataNascita\":\"1963-06-24 00:00:00.0\",\r\n" + 
				"    \"luogoNascita\":\"NAPOLI\",\r\n" + 
				"    \"documentoIdentita\":\"C.I. AN 2578039  17\\/05\\/2006\",\r\n" + 
				"    \"listaIndirizzi\":[{\r\n" + 
				"        \"citta\":\"NAPOLI, NA\",\r\n" + 
				"        \"nazione\":\"ITALY\",\r\n" + 
				"        \"via\":\"VIA RAFFAELE TESTA 4 IS I\",\r\n" + 
				"        \"tipologia\":\"Residenza\",\r\n" + 
				"        \"cap\":80100\r\n" + 
				"    }]\r\n" + 
				"}";
		
		Gson gson = new Gson();
		Organization o = gsonx.fromJson( jsonOK4, Organization.class );
		
//		JSONObject jo = JSONML.toJSONObject( json );
//		JSONObject jo = new JSONObject( json );
//		System.out.println( jo );
//		System.out.println( jo.get( "organization" ) );
//		System.out.println( jo.getJSONObject( "organization" ).getString( "codiceFiscale" ) );
//		System.out.println( jo.getJSONObject( "organization" ).getJSONObject( "codiceFiscale" ).getString( "#" ) );
		
//		URL					url = new URL( "http://62.149.222.66:8280/service/2/servizi_canina/getInfoCane?mc=985120021339767&response=application/json/badgerfish" );
//		URLConnection		uc = url.openConnection();
//		uc.setDoOutput( true );
//		OutputStreamWriter	wr = new OutputStreamWriter(uc.getOutputStream());
//		wr.flush();
//		wr.close();
//		BufferedReader br = new BufferedReader( new InputStreamReader( uc.getInputStream() ) );
//		StringBuffer sb = new StringBuffer();
//		String temp = null;
//		while( (temp = br.readLine()) != null )
//		{
//			sb.append( temp );
//		}
//		br.close(); 
//		
//		JSONObject json = new JSONObject( sb.toString() );
//		JSONObject bean = (JSONObject)( (JSONObject)json.get( "ns:getInfoCaneResponse" ) ).get( "ns:return" );
//		bean.remove( "@xmlns" );
//		System.out.println( bean );
//		
////		JsonObject j = new JsonObject();
//		Gson g = new Gson( );
////		System.out.println( sb.toString() );
//		Cane cane = g.fromJson( 
//					bean.toString()
//						.replaceAll( "ax24:", "" )
//						.replaceAll( "\"\\$\":", "" )
//						.replaceAll( ":\\{", ":" )
//						.replaceAll( "\\},", "," )
//						.replaceAll( "\"@xsi:nil\":\"true\"", "null" ),
//					Cane.class );
		
		System.out.println( o.getDataNascita() );
		System.out.println( o.getId() );
		System.out.println( o.getIdAsl() );
		System.out.println( o.getListaIndirizzi().length );
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
