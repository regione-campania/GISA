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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.mycfs.base.Mail;
import org.directwebremoting.WebContextFactory;

import com.darkhorseventures.framework.actions.ActionContext;

public class DwrCustomSatisfaction {

	public static Boolean insertCustomSatisfaction(String data_operazione,String username,String soddisfatto,String descrizione_problema,String operazione_eseguita,String longTimeIni , String longTimeEnd  ){
		Connection db = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			db = GestoreConnessioni.getConnection();


			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd yy:mm:ss");
			long datOp = Long.parseLong(data_operazione);
			Timestamp timeOp = new Timestamp(datOp);

			long sedondi =  TimeUnit.MILLISECONDS.toSeconds( (Long.parseLong(longTimeEnd) - Long.parseLong(longTimeIni)));    


			String action = "" ;
			String command = "" ;
			if (operazione_eseguita!=null && !operazione_eseguita.equals(""))
			{
				action = operazione_eseguita.split(";")[0];
				command = operazione_eseguita.split(";")[1];
			}

			String insert = "insert into  customer_satisfaction (data_operazione,username,soddisfatto,descrizione_problema,action_eseguita,operazione_eseguita,time_esecuzione_secondi) values (?,?,?,?,?,?,?)" ;
			stat = db.prepareStatement(insert);
			stat.setTimestamp(1, timeOp);
			stat.setString(2, username);
			if("si".equalsIgnoreCase(soddisfatto))
				stat.setBoolean(3,true);
			else
				stat.setBoolean(3,false);

			stat.setString(4, descrizione_problema);
			stat.setString(5, action);
			stat.setString(6, command);
			stat.setLong(7, sedondi);

			stat.execute();




			HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
			if("no".equalsIgnoreCase(soddisfatto))
			{
				String email = ApplicationProperties.getProperty("HD_LEVEL_1_EMAIL_ADDRESS");
				Mail mail = new Mail();	
				mail.setHost(getPref(req, "MAILSERVER"));
				mail.setFrom(getPref(req, "EMAILADDRESS"));
				mail.setUser(getPref(req, "EMAILADDRESS"));
				mail.setRispondiA(email);
				mail.setPass(getPref(req, "MAILPASSWORD"));
				mail.setPort(Integer.parseInt(getPref(req, "PORTSERVER")));
				mail.setTo(email);
				mail.setSogg("[#!CUSTOMERSATISFACTION-BDU-KO]");
				mail.setTesto("["+new Date()+"] L'utente "+username+" non è soddisfatto del servizio ["+action+  " - "+command+"], indicando il seguente problema : "+descrizione_problema.trim()+"[Tempo di esecuzione dal submit a rendering :]"+sedondi+"sec.");
				mail.sendMail();
			}

		}
		catch(SQLException e)
		{
			System.out.println("Errore Salvataggio Customer Satisfaction");
			return false;
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return true ; 
	}

	
	

	public static long calcolaTempoEsecuzioneCustomSatisfaction(String longTimeIni , String longTimeEnd  ){
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd yy:mm:ss");
		long sedondi =  TimeUnit.MILLISECONDS.toSeconds( (Long.parseLong(longTimeEnd) - Long.parseLong(longTimeIni)));    
		
	
		
	return sedondi ; 
}
	
	protected static String getPref(HttpServletRequest context, String param) {
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
				"applicationPrefs");
		if (prefs != null) {
			return prefs.get(param);
		} else {
			return null;
		}
	}
}
