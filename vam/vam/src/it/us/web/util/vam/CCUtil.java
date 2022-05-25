/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.dao.vam.ClinicaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import sun.security.action.GetLongAction;

/**
 * @author Io
 *
 */
public class CCUtil {
	
	
	private static final DecimalFormat decimalFormat = new DecimalFormat( "00000" );
	
	
	/**
	 * L'algoritmo funziona nella seguente maniera:
	 * 1) Prendo l'anno corrente
	 * 2) Prendo l'anno dell'ultima cartella clinica salvata in quella clinica
	 * 3) Se gli anni sono uguali
	 * 	3.1) Prendo il progressivo dell'ultima cartellaClinica salvata in quella Clinica
	 * 	3.2) Se non c'è (1° cartella clinica)
	 * 		3.2.1) Assegno 1
	 * 	3.3) Se c'è
	 * 		3.3.1) Il numero + 1
	 * 4) Se gli anni sono diversi
	 * 	4.1) Assegno 1
	 * @param persistence
	 * @param idClinica
	 * @return
	 * @throws ParseException
	 */
	public static int getSequence (Persistence persistence, int idClinica, int annoApertura) throws ParseException {
		
		//int annoCorrente 	= getCurrentYear();
		CartellaClinica cc	= getLastCC(persistence, idClinica, annoApertura);
		int newProgressivo	= 1;
		
		
		if (cc != null) {
			
			//int annoUltimaCC 	= getLastCCDataStored(cc);	
		
			//if (annoCorrente == annoUltimaCC && cc!=null) {
				newProgressivo = cc.getProgressivo()+1;
			//}
			//else {
				//newProgressivo = 1;
			//}
		}
		else {
			newProgressivo = 1;
		}
		
		return newProgressivo;
	}
	
	public static String assignedNumber (Persistence persistence, int progressivoClinica ,int idClinica, int annoApertura) throws Exception {
		
		Clinica clinica = (Clinica) persistence.find(Clinica.class, idClinica);
		annoApertura+=1900;
		
		return "CC-"+clinica.getNomeBreve()+"-"+annoApertura+"-"+decimalFormat.format(progressivoClinica);
	}
	
	public static String assignedNumber (Connection connection, int progressivoClinica ,int idClinica, int annoApertura) throws Exception {
		
		String nomeBreve = ClinicaDAO.getNomeBreve(idClinica, connection);
		//Clinica clinica = (Clinica) persistence.find(Clinica.class, idClinica);
		annoApertura+=1900;
		
		return "CC-"+nomeBreve+"-"+annoApertura+"-"+decimalFormat.format(progressivoClinica);
	}
	
	private static int getCurrentYear () {
		
		Calendar calendar = new GregorianCalendar();		
		int currentYear   = calendar.get(Calendar.YEAR);
		
		return currentYear;
	}	
	
	private static CartellaClinica getLastCC (Persistence persistence, int idClinica, int anno) {
		
		CartellaClinica cc=null;
		Timestamp dataInizio = new Timestamp(anno, 0,  1,  0, 0,  0,  0);
		Timestamp dataFine =   new Timestamp(anno, 11, 31, 23,59, 59, 999999999);
		
		//Recupero delle cartelle cliniche di un determinata clinica
		ArrayList<CartellaClinica> cartelleCliniche = (ArrayList<CartellaClinica>) persistence.getNamedQuery("GetCCByClinica_IntervalloDate")
																						.setInteger("idClinica", idClinica)
																						.setTimestamp("dataInizio", dataInizio)
																						.setTimestamp("dataFine", dataFine)
																						.list();
		
		
		if (cartelleCliniche.size() == 0)
			return null;
		else
			cc = cartelleCliniche.get(0);	
				
		return cc;		
		
	}
	
	
	private static int getLastCCDataStored (CartellaClinica cc) throws ParseException {
				
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");		
		
		int lastCCYear = Integer.parseInt(simpleDateformat.format(cc.getDataApertura()));
		
		return lastCCYear;
		
	}
	
	public static boolean riconsegnaPossibile (CartellaClinica cc) throws ParseException {
				
		boolean riconsegnaPossibile	= false;
		
		Set<Trasferimento> trasfs = cc.getTrasferimenti();
						
		for( Trasferimento trasf: trasfs )
		{
			if( trasf.getStato().stato == StatoTrasferimento.ACCETTATO_DESTINATARIO )//trasferimento aperto
			{
				riconsegnaPossibile = true;
			}
		}
		
		return riconsegnaPossibile;
		
	}
	
	
public static int getSequence (Connection connection, int idClinica, int annoApertura) throws ParseException, SQLException {
		
		//int annoCorrente 	= getCurrentYear();
		CartellaClinica cc	= getLastCC(connection, idClinica, annoApertura);
		int newProgressivo	= 1;
		
		
		if (cc != null) {
			
			//int annoUltimaCC 	= getLastCCDataStored(cc);	
		
			//if (annoCorrente == annoUltimaCC && cc!=null) {
				newProgressivo = cc.getProgressivo()+1;
			//}
			//else {
				//newProgressivo = 1;
			//}
		}
		else {
			newProgressivo = 1;
		}
		
		return newProgressivo;
	}



		private static CartellaClinica getLastCC (Connection connection, int idClinica, int anno) throws SQLException {
		
		CartellaClinica cc=null;
		Timestamp dataInizio = new Timestamp(anno, 0,  1,  0, 0,  0,  0);
		Timestamp dataFine =   new Timestamp(anno, 11, 31, 23,59, 59, 999999999);
		
		//Recupero delle cartelle cliniche di un determinata clinica
		ArrayList<CartellaClinica> cartelleCliniche = CartellaClinicaDAO.getCcs(connection, idClinica,dataInizio,dataFine);
		
		
		if (cartelleCliniche.size() == 0)
			return null;
		else
			cc = cartelleCliniche.get(0);	
				
		return cc;		
		
	}

	
	
	

}
