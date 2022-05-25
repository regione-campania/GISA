/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sinaaf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.CampoModificato;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAllontanamento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.ws.WsPost;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;

public class Sinaaf extends GenericBean {

	private static Logger log = Logger .getLogger(Sinaaf.class);
	
	public Sinaaf()
	{
		
	}
	
	public String[] aggiornamento(Connection db, int userId, String id, String entita, String token, boolean forzaModifica) throws Exception
	{
		String[] toReturn = new String[5];
		WsPost ws = new WsPost(db,id,entita,ApplicationProperties.getProperty("END_POINT_SINAAF"));
		String idSinaaf = ws.idSinaaf;
		String method =  (idSinaaf!=null && !idSinaaf.equals("") && !idSinaaf.equals("null"))?("put"):("post");
		
		if((!ws.sincronizzato || forzaModifica) && ws.propagazioneSinaaf && ws.presenteInGisa==null)
		{
			JsonParser parser = new JsonParser();
		
			if(ws.dipendenze!=null)
			{
				int i=0;
				String[] dipendenze = ws.dipendenze.split(";");
				while(i < dipendenze.length)
				{
					String[] toReturnDipendenze = new Sinaaf().aggiornamento(db, userId, dipendenze[i],  dipendenze[i+1],token,false);
					if(toReturnDipendenze[1]!=null || toReturnDipendenze[4]!=null )
						return toReturnDipendenze;
					i+=2;
				}
			}
			
			if(token==null)
				token = ws.getToken();
	
			String envelope = "";
		    PreparedStatement pst = db.prepareStatement("select * from " + ws.dbiGetEnvelope + "(?)");
		    pst.setString(1, id);
		    ResultSet rs = pst.executeQuery();
		    while (rs.next())
			    envelope = rs.getString(1);
			
		    ws.setWsRequest(envelope);
		    String wsResponse = ws.post(db, userId, MediaType.parse("application/json;charset=UTF-8"), token,id,ws.tabella,method);
	  
		    JsonObject json = (JsonObject) parser.parse(wsResponse);
		    String status = (json.get("status")==null)?(null):(json.get("status").toString());
		    String error = (json.get("error")==null)?(null):(json.get("error").toString());
		    if((status!=null && (status.equals("422") || status.equals("500") || status.equals("403"))) || error!=null)
		    	toReturn[1] = (status==null)?(error):(status)+"";
		    else
		    {
		    	idSinaaf = json.get(ws.nomeCampoIdSinaaf).toString();
			    toReturn[0] = idSinaaf;
			    ws.setIdSinaaf(db, idSinaaf, id);
		    }
		    toReturn[2] = wsResponse;
		    
		    if( ws.wsPostSecondario!=null)
			{
			    pst = db.prepareStatement("select * from " + ws.wsPostSecondario.dbiGetEnvelope + "(?)");
			    pst.setString(1, id);
			    rs = pst.executeQuery();
			    while (rs.next())
				    envelope = rs.getString(1);
				
			    ws.wsPostSecondario.setWsRequest(envelope);
			    wsResponse =  ws.wsPostSecondario.post(db, userId, MediaType.parse("application/json;charset=UTF-8"), token,id, ws.wsPostSecondario.tabella,method);
		  
			    json = (JsonObject) parser.parse(wsResponse);
			    status = (json.get("status")==null)?(null):(json.get("status").toString());
			    error = (json.get("error")==null)?(null):(json.get("error").toString());
			    if((status!=null && (status.equals("422") || status.equals("500") || status.equals("403"))) || error!=null)
			    	toReturn[1] = (status==null)?(error):(status)+"";
			    else
			    {
			    	idSinaaf = json.get(ws.wsPostSecondario.nomeCampoIdSinaaf).toString();
				    toReturn[0] =  idSinaaf;
				    ws.wsPostSecondario.setIdSinaaf(db, idSinaaf, id, "_secondario");
			    }
			    toReturn[2] = wsResponse;
			}
		}
		else if (idSinaaf !=null)
		{
			toReturn[0] = idSinaaf;
		}
		else
		{
			if(!ws.propagazioneSinaaf )
				toReturn[3] = "Propagazione non prevista";
			else
				toReturn[4] = ws.presenteInGisa; 
		}
	    
	    return toReturn;
	}
	
	public String[] get(Connection db, int userId, String id, String entita, String token) throws Exception
	{
		String[] toReturn = new String[5];
		WsPost ws = new WsPost(db,id,entita,ApplicationProperties.getProperty("END_POINT_SINAAF"));
		String idSinaaf = ws.idSinaaf;
		
		if(idSinaaf!=null && !idSinaaf.equals("") && !idSinaaf.equalsIgnoreCase("null"))
		{
			JsonParser parser = new JsonParser();
		
			if(token==null)
				token = ws.getToken();
	
			ws.url+="/"+ws.idSinaaf;
		    String wsResponse = ws.get(db, userId, MediaType.parse("application/json;charset=UTF-8"), token,id,ws.tabella);
	  
		    JsonObject json = (JsonObject) parser.parse(wsResponse);
		    String status = (json.get("status")==null)?(null):(json.get("status").toString());
		    String error = (json.get("error")==null)?(null):(json.get("error").toString());
		    if((status!=null && (status.equals("422") || status.equals("500") || status.equals("403"))) || error!=null)
		    	toReturn[1] = (status==null)?(error):(status)+"";
		    toReturn[2] = wsResponse;
		}
		else
		{
			if(!ws.propagazioneSinaaf )
				toReturn[3] = "Non presente in sinaaf";
		}
	    
	    return toReturn;
	}
	
	
	
	
	public String[] aggiornamento(Connection db, int userId, String id, String entita) throws Exception
	{
		return aggiornamento( db,  userId,  id,  entita, null,true);
	}
	
	public String[] aggiornamento(Connection db, int userId, String id, String entita, boolean forzaModifica) throws Exception
	{
		return aggiornamento( db,  userId,  id,  entita, null,forzaModifica);
	}
	
	public String[] get(Connection db, int userId, String id, String entita) throws Exception
	{
		return get( db,  userId,  id,  entita, null);
	}
	
	public String[] inviaInSinaaf(Connection db, int userId,String idEntita, String entita) throws Exception
	{
		String[] toReturn = new String[2];
		String [] esito = new Sinaaf().aggiornamento(db, userId, idEntita, entita);
		
		if(esito[1]!=null )
		{
			toReturn[1] = "Errore durante la propagazione in Sinaaf: " + esito[2];
			toReturn[1]=toReturn[1].replaceAll("'","");
		}
		else if(esito[4]!=null )
		{
			toReturn[1] = esito[4];
			toReturn[1]=toReturn[1].replaceAll("'","");
		}
		else if(esito[3]!=null )
		{
			toReturn[0] = esito[3];
		}
		else
		{
			toReturn[0] = "Propagazione avvenuta in Sinaaf correttamente";
		}
		
		return toReturn;
	}
	
	public String[] vediInSinaaf(Connection db, int userId,String idEntita, String entita) throws Exception
	{
		String toReturn[] = new String[2];
		String [] esito = new Sinaaf().get(db, userId, idEntita, entita);
		
		if(esito[1]!=null )
		{
			toReturn[0] = "Errore durante la ricerca in Sinaaf: " + esito[2];
			toReturn[0]=toReturn[0].replaceAll("'","");
		}
		else if(esito[3]!=null )
		{
			toReturn[0] = esito[3];
		}
		else
		{
			toReturn[1] = esito[2];
		}
		
		return toReturn;
	}
	
	
	
}
