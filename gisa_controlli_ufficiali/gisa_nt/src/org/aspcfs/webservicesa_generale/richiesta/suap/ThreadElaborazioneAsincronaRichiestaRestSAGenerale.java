/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class ThreadElaborazioneAsincronaRichiestaRestSAGenerale extends Thread {

	ServletContext context;
	HashMap<String, String> parametriInputsPerSciaComeSuXml;
	ArrayList<String> lineeToInsert; 
	int idUser;
//	File[] filesToSend ;
	HashMap<String,File> filesToSend; //le chiavi sono i nomi allegato gruppo file (es Allegato A, Allegato B....)
	String appName;
	Logger logger = Logger.getLogger(ThreadElaborazioneAsincronaRichiestaRestSAGenerale.class);
	File[] foldsToDelete;
	RestControllerSAGenerale callback;
	boolean isOpGlobale;
	private ConnettoreAGisaGenerale connettoreAGisa;
	private ConnettoreAlDocumentaleGenerale connettoreAlDocumentale;
	
	public ThreadElaborazioneAsincronaRichiestaRestSAGenerale()
	{}

	public ThreadElaborazioneAsincronaRichiestaRestSAGenerale(ConnettoreAGisaGenerale connettoreAGisa,RestControllerSAGenerale callback,ServletContext context, HashMap<String, String> parametriInputsPerSciaComeSuXml,
			ArrayList<String> lineeToInsert, ConnettoreAlDocumentaleGenerale connettoreDocu, HashMap<String,File> allegati,int idUser,String appName,boolean isOpGlobale) throws SQLException {
			
			this.context = context;
			this.idUser = idUser;
			this.lineeToInsert = lineeToInsert;
			this.parametriInputsPerSciaComeSuXml = parametriInputsPerSciaComeSuXml;
			this.connettoreAlDocumentale = connettoreDocu;
			filesToSend = allegati;
			this.appName = appName;
			this.foldsToDelete = foldsToDelete;
			this.callback = callback;
			this.isOpGlobale = isOpGlobale;
			this.connettoreAGisa = connettoreAGisa;
	}
	
	@Override
	public void run() 
	{
		 
		 logger.info("THREAD SERVIZ REST > *INIZIO* ELABORAZIONE PER USER-ID "+idUser);
		 
		 int[] risultati = elaboraInserimentoSciaConXml(connettoreAGisa,parametriInputsPerSciaComeSuXml, lineeToInsert, idUser,isOpGlobale);
		 int idImpresa = risultati[0];
		 int idNuovoStab = risultati[1];
		 int idStabPerDocumentale = risultati[2];
		 
		 logger.info("THREAD SERVIZ REST > *FINE* ELABORAZIONE PER USER-ID "+idUser);
		 
		 if(idImpresa > 0)
		 {
			 logger.info("THREAD SERVIZ REST > ELABORAZIONE COMPLETATA CON SUCCESSO, ID RICHIESTA: "+idImpresa);
			 logger.info("THREAD SERVIZ REST > INIZIO INVIO ALLEGATI ASSOCIATI AD ALTID: "+idStabPerDocumentale);
			 boolean res = elaboraAllegati(connettoreAlDocumentale,filesToSend,idStabPerDocumentale,idUser,appName);
			 if(res)
			 {
				 logger.info("THREAD SERVIZ REST > INVIO ALLEGATI EFFETTUATO CON SUCCESSO ");
			 }
			 else
			 {
				 logger.info("THREAD SERVIZ REST > ALMENO UN ALLEGATO NON E' STATO INVIATO CON SUCCESSO ");
			 }
			 
		 }
		 else
		 {
			 logger.info("THREAD SERVIZ REST > ELABORAZIONE FALLITA: ");
		 }
		 
		 //avviso il controller che ho terminato (questo avviera il rilascio risorse rimanenti aperte, files etc)
		 callback.callbackTerminazioneThreadElaborazioneRichiesta();
		 
		  
		
		
	}
	
private boolean elaboraAllegati(ConnettoreAlDocumentaleGenerale connett,HashMap<String,File> allegatiToSend,int altStab,int idUser,String appName)
{
	
	 
	
	boolean res = false;
	try
	{
		res = connett.inviaAllegati(allegatiToSend, altStab,idUser,appName);
		return res;
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		return false;
	}
	
}

//il primo e id nuovoo op
//il secondo id nuovo stab
//il terzo e l'id del nuovo stab pero' per il documentale
private int[] elaboraInserimentoSciaConXml(ConnettoreAGisaGenerale connettoreAGisa, HashMap<String, String> parametriInputsPerSciaComeSuXml, ArrayList<String> lineeToIns,int idUser,boolean isOperazioneGlobale)   {
		
		Connection db = null;
		PreparedStatement pst = null;
		
		ResultSet rs =null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			String[] t =  connettoreAGisa.richiediInserimentoInGisa(parametriInputsPerSciaComeSuXml,lineeToIns,idUser,isOperazioneGlobale);
			int[] toRet = new int[3];
			toRet[0] = Integer.parseInt(t[0]);
			toRet[1] = Integer.parseInt(t[1]);
			toRet[2] = Integer.parseInt(t[2]);
			return toRet;
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("THREAD SERVIZ REST > TENTATIVO DI INSERIMENTO RICHIESTA *FALLITO*");
			return new int[]{-1,-1};
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{pst.close();}catch(Exception ex){}
			try{GestoreConnessioni.freeConnection(db);}catch(Exception ex){}
		}
		
	}

/*
public boolean cancellaFile(File f)
{
	boolean cancellato = true;
	try
	{
		cancellato = f.delete();
	}
	catch(Exception ex){ cancellato = false;}
	
	if(cancellato)
	{
		 logger.info("THREAD SERVIZ REST > ? CANCELLATO ".replace("?", f.getName()));
	}
	else
	{
		 logger.info("THREAD SERVIZ REST > ? NON CANCELLATO ".replace("?", f.getName()));
	}
	return cancellato;
}*/

}
