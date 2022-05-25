/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.sinantropi;

import it.us.web.bean.remoteBean.RegistrazioniSinantropi;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.hibernate.Persistence;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class SinantropoUtil {
	
	private static final DecimalFormat decimalFormat = new DecimalFormat( "00000" );
	
	public static String getNumero (Persistence p) throws Exception {
		
		int numero = 1;
		
		int lastId = getLastSinantropo(p);
		
		if (lastId > 0) {			
			numero = lastId + 1;
		}
		else {
			numero = 1;
		}
		
		return "S"+decimalFormat.format(numero);
	}
	
	private static int getLastSinantropo(Persistence p) throws Exception {
		
		Sinantropo s;
		int max = 0;
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
				
		if (sinantropi.size() == 0) {
			return max;
		}
		else {
			for (int i=0 ; i<sinantropi.size(); i++) {
				s = sinantropi.get(i);
				if (s.getId() > max)
					max = s.getId();
			}
			
		}				
		return max;	
		
	}
	
	//Su tutti i Sinantropi viene controllata l'univocità => Utilizzato in fase di aggiunta
	public static boolean checkUniquenessNumeroUfficiale (Persistence p , String numeroUfficiale) throws Exception {
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getNumeroUfficiale()!=null && !s.getNumeroUfficiale().equalsIgnoreCase("") && s.getNumeroUfficiale().equalsIgnoreCase(numeroUfficiale)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	//Su tutti i Sinantropi viene controllata l'univocità => Utilizzato in fase di aggiunta
	public static boolean checkUniquenessMc (Persistence p , String mc) throws Exception {
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getMc()!=null && !s.getMc().equalsIgnoreCase("") && s.getMc().equalsIgnoreCase(mc)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	//Su tutti i Sinantropi viene controllata l'univocità => Utilizzato in fase di aggiunta
	public static boolean checkUniquenessCodiceIspra (Persistence p , String codiceIspra) throws Exception {
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getCodiceIspra()!=null && !s.getCodiceIspra().equalsIgnoreCase("") && s.getCodiceIspra().equalsIgnoreCase(codiceIspra)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	//Su tutti i Sinantropi, tranne quello corrente, viene controllata l'univocità => Utilizzato in fase di editing	
	public static boolean checkUniquenessNumeroUfficiale (Persistence p , Sinantropo sinantropo, String numeroUfficiale) throws Exception {
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		sinantropi.remove(sinantropo);
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getNumeroUfficiale()!=null && s.getNumeroUfficiale().equalsIgnoreCase(numeroUfficiale)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	//Su tutti i Sinantropi, tranne quello corrente, viene controllata l'univocità => Utilizzato in fase di editing	
	public static boolean checkUniquenessMc (Persistence p , Sinantropo sinantropo, String mc) throws Exception {
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		sinantropi.remove(sinantropo);
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getMc()!=null && s.getMc()!=null && s.getMc().equalsIgnoreCase(mc)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	//Su tutti i Sinantropi, tranne quello corrente, viene controllata l'univocità => Utilizzato in fase di editing	
	public static boolean checkUniquenessCodiceIspra (Persistence p , Sinantropo sinantropo, String codiceIspra) throws Exception 
	{
		
		Sinantropo s;
		ArrayList<String> numeriUfficiali = new ArrayList();
		
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>) p.findAll(Sinantropo.class);
		sinantropi.remove(sinantropo);
		for (int i=0 ; i<sinantropi.size(); i++) {
			s = (Sinantropo) sinantropi.get(i);
			if (s.getCodiceIspra()!=null && s.getCodiceIspra()!=null && s.getCodiceIspra().equalsIgnoreCase(codiceIspra)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	public static Sinantropo getSinantropoByNumero (Persistence persistence, String numeroSinantropo) throws ParseException {
						
		Sinantropo s = null;		
		
		//Recupero del Sinantropo per mc
		ArrayList<Sinantropo> sinantropiMc = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByMcGenerale").setString("mc", numeroSinantropo).list();

		//Recupero del Sinantropo per numero Ufficiale assegnato dall'istituo faunistico
		ArrayList<Sinantropo> sinantropiUfficiali = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroUfficialeGenerale").setString("numeroSinantropo", numeroSinantropo).list();
				
		//Recupero del Sinantropo per numero Automatico assegnato dal sistema
		ArrayList<Sinantropo> sinantropiAutomatici = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroAutomaticoGenerale").setString("numeroSinantropo", numeroSinantropo).list();
		
		//Recupero del Sinantropo per codice Ispra
		ArrayList<Sinantropo> sinantropiCodiceIspra = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByCodiceIspraGenerale").setString("numeroSinantropo", numeroSinantropo).list();
				
		
		if (sinantropiUfficiali.size() > 0) {			
			s = (Sinantropo) sinantropiUfficiali.get(0);					
		}		
		else if (sinantropiAutomatici.size() > 0){			
			s = (Sinantropo) sinantropiAutomatici.get(0);								
		}
		else if (sinantropiMc.size() > 0){			
			s = (Sinantropo) sinantropiMc.get(0);								
		}
		else if (sinantropiCodiceIspra.size() > 0){			
			s = (Sinantropo) sinantropiCodiceIspra.get(0);								
		}
		else {
			return null;
		}
		
		return s;
	}
			


	
	
	public static Detenzioni getLastActiveDetentore (Persistence persistence, String numeroSinantropo) throws ParseException {
		
		Detenzioni d = null;
		
		Sinantropo s = null;		
		
		//Recupero del Sinantropo per mc
		ArrayList<Sinantropo> sinantropiMc = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByMcGenerale").setString("mc", numeroSinantropo).list();

		//Recupero del Sinantropo per numero Ufficiale assenato dall'istituo faunistico
		ArrayList<Sinantropo> sinantropiUfficiali = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroUfficialeGenerale").setString("numeroSinantropo", numeroSinantropo).list();
				
		//Recupero del Sinantropo per numero Automatico assenato dal sistema
		ArrayList<Sinantropo> sinantropiAutomatici = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroAutomaticoGenerale").setString("numeroSinantropo", numeroSinantropo).list();
		
		if (sinantropiUfficiali.size() > 0) {			
			s = (Sinantropo) sinantropiUfficiali.get(0);					
		}		
		else if (sinantropiAutomatici.size() > 0){			
			s = (Sinantropo) sinantropiAutomatici.get(0);								
		}
		else if (sinantropiMc.size() > 0){			
			s = (Sinantropo) sinantropiMc.get(0);								
		}
		else {
			return null;
		}
		
		Catture c = getLastCattura(s);
				
		if (c != null) {
			d = getLastDetentore(c);
		}
				
		return d;
	}
	
	public static Catture getLastCattura (Sinantropo s) throws ParseException {
		
		Iterator listaCatture = s.getCattureis().iterator();
		
		Catture c;
		
		Catture lastCattura = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataIniziale 	= "01/01/1800";		
		Date dataCattura 		= format.parse(dataIniziale);	
				
		while (listaCatture.hasNext()) {
			c = (Catture) listaCatture.next();
			if (c.getDataCattura().after(dataCattura)) {
				dataCattura = c.getDataCattura();
				lastCattura = c;
			}
		}
		
		
		return lastCattura;
	}
	
	private static Detenzioni getLastDetentore (Catture c) throws ParseException {
		
		Iterator listaDetenzioni = c.getDetenzionis().iterator();
		
		Detenzioni d;
		Detenzioni lastDetenzione = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataIniziale 	= "01/01/1800";		
		Date dataDetenzione 	= format.parse(dataIniziale);	
				
		while (listaDetenzioni.hasNext()) {
			d = (Detenzioni) listaDetenzioni.next();
			if (d.getDataDetenzioneDa().after(dataDetenzione)) {
				dataDetenzione = d.getDataDetenzioneDa();
				lastDetenzione = d;
			}
		}
		
		if (c.getReimmissioni() == null)
			return lastDetenzione;
		else
			return null;
		
		
	}
	
	
	public static RegistrazioniSinantropi findRegistrazioniEffettuabili (Persistence persistence, String numeroSinantropo) throws ParseException {
		
		RegistrazioniSinantropi rs = new RegistrazioniSinantropi();
		
		Sinantropo s = null;		
		
		//Recupero del Sinantropo per mc
		ArrayList<Sinantropo> sinantropiMc = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByMcGenerale").setString("mc", numeroSinantropo).list();

		//Recupero del Sinantropo per numero Ufficiale assenato dall'istituo faunistico
		ArrayList<Sinantropo> sinantropiUfficiali = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroUfficialeGenerale").setString("numeroSinantropo", numeroSinantropo).list();
				
		//Recupero del Sinantropo per numero Automatico assenato dal sistema
		ArrayList<Sinantropo> sinantropiAutomatici = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroAutomaticoGenerale").setString("numeroSinantropo", numeroSinantropo).list();
		
		if (sinantropiUfficiali.size() > 0) {			
			s = (Sinantropo) sinantropiUfficiali.get(0);					
		}		
		else if (sinantropiAutomatici.size() > 0) {			
			s = (Sinantropo) sinantropiAutomatici.get(0);								
		}
		else if (sinantropiMc.size() > 0) {			
			s = (Sinantropo) sinantropiMc.get(0);								
		}
		else {
			return null;
		}
		
		/* Se l'ultima operazione è stata una re-immissione 
		 * e non c'è decesso => Si può fare una nuova Cattura e registrare un decesso*/
		if (s.getLastOperation().equalsIgnoreCase("RILASCIO") && s.getDataDecesso() == null) {
			
			rs.setDecesso		((Boolean)true);
			rs.setCattura		((Boolean)true);
			rs.setDetentore		((Boolean)false);
			rs.setReimmissione	((Boolean)false);
	
		}
		/* Se esiste una data di decesso => Non si può fare nulla*/
		else if (s.getDataDecesso() != null) {
			rs.setDecesso		((Boolean)false);
			rs.setCattura		((Boolean)false);
			rs.setDetentore		((Boolean)false);
			rs.setReimmissione	((Boolean)false);
		}		
		else {
			Catture c = getLastCattura(s);
						
			/* Se esiste una cattura associa al Sinantropo, non c'è una reimmissione
			 * e non è deceduto => Si può fare Decesso, Detenzione e Reimmissione*/
			if (c != null && c.getReimmissioni() == null && s.getDataDecesso() == null) {
				rs.setDecesso		((Boolean)true);
				rs.setCattura		((Boolean)false);
				rs.setDetentore		((Boolean)true);
				rs.setReimmissione	((Boolean)true);
			}
			/* Se esiste una cattura associa al Sinantropo, c'è una reimmissione
			 * e non è deceduto => Si può fare solo il Decesso*/
			else if (c != null && c.getReimmissioni() != null && s.getDataDecesso() == null) {
				rs.setDecesso		((Boolean)true);
				rs.setCattura		((Boolean)true);
				rs.setDetentore		((Boolean)false);
				rs.setReimmissione	((Boolean)false);
			}
			else {
				rs.setDecesso		((Boolean)false);
				rs.setCattura		((Boolean)false);
				rs.setDetentore		((Boolean)false);
				rs.setReimmissione	((Boolean)false);
			}
			
		}
						
		return rs;
	}
	
	
	
	
	
	
	
		public static RegistrazioniSinantropi findRegistrazioniEffettuabili (Connection connection, String numeroSinantropo) throws ParseException, SQLException {
			
			RegistrazioniSinantropi rs = new RegistrazioniSinantropi();
			
			Sinantropo s = null;		
			
			//Recupero del Sinantropo per mc
			s = SinantropoDAO.getSinantropoByNumero(connection, numeroSinantropo);
			
			
			/*ArrayList<Sinantropo> sinantropiMc = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByMcGenerale").setString("mc", numeroSinantropo).list();
	
			//Recupero del Sinantropo per numero Ufficiale assenato dall'istituo faunistico
			ArrayList<Sinantropo> sinantropiUfficiali = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroUfficialeGenerale").setString("numeroSinantropo", numeroSinantropo).list();
					
			//Recupero del Sinantropo per numero Automatico assenato dal sistema
			ArrayList<Sinantropo> sinantropiAutomatici = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroAutomaticoGenerale").setString("numeroSinantropo", numeroSinantropo).list();
			
			if (sinantropiUfficiali.size() > 0) {			
				s = (Sinantropo) sinantropiUfficiali.get(0);					
			}		
			else if (sinantropiAutomatici.size() > 0) {			
				s = (Sinantropo) sinantropiAutomatici.get(0);								
			}
			else if (sinantropiMc.size() > 0) {			
				s = (Sinantropo) sinantropiMc.get(0);								
			}
			else {
				return null;
			}*/
			
			/* Se l'ultima operazione è stata una re-immissione 
			 * e non c'è decesso => Si può fare una nuova Cattura e registrare un decesso*/
			if(s!=null)
				{	
				if (s.getLastOperation().equalsIgnoreCase("RILASCIO") && s.getDataDecesso() == null) {
					
					rs.setDecesso		((Boolean)true);
					rs.setCattura		((Boolean)true);
					rs.setDetentore		((Boolean)false);
					rs.setReimmissione	((Boolean)false);
			
				}
				/* Se esiste una data di decesso => Non si può fare nulla*/
				else if (s.getDataDecesso() != null) {
					rs.setDecesso		((Boolean)false);
					rs.setCattura		((Boolean)false);
					rs.setDetentore		((Boolean)false);
					rs.setReimmissione	((Boolean)false);
				}		
				else {
					Catture c = getLastCattura(s);
								
					/* Se esiste una cattura associa al Sinantropo, non c'è una reimmissione
					 * e non è deceduto => Si può fare Decesso, Detenzione e Reimmissione*/
					if (c != null && c.getReimmissioni() == null && s.getDataDecesso() == null) {
						rs.setDecesso		((Boolean)true);
						rs.setCattura		((Boolean)false);
						rs.setDetentore		((Boolean)true);
						rs.setReimmissione	((Boolean)true);
					}
					/* Se esiste una cattura associa al Sinantropo, c'è una reimmissione
					 * e non è deceduto => Si può fare solo il Decesso*/
					else if (c != null && c.getReimmissioni() != null && s.getDataDecesso() == null) {
						rs.setDecesso		((Boolean)true);
						rs.setCattura		((Boolean)true);
						rs.setDetentore		((Boolean)false);
						rs.setReimmissione	((Boolean)false);
					}
					else {
						rs.setDecesso		((Boolean)false);
						rs.setCattura		((Boolean)false);
						rs.setDetentore		((Boolean)false);
						rs.setReimmissione	((Boolean)false);
					}
					
				}
		}
						
		return rs;
	}
		
	
	public static RegistrazioniSinantropiResponse getInfoDecesso (Persistence persistence,Animale animale) throws UnsupportedEncodingException, ParseException {
		
		RegistrazioniSinantropiResponse ret = null;
		Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
				
		if (s!=null && s.getLookupCMI() != null && s.getDataDecesso() != null) {
			ret = new RegistrazioniSinantropiResponse();
			ret.setDecessoValue(s.getLookupCMI().getDescription());
			ret.setDataEvento(s.getDataDecesso());
			ret.setDataDecessoPresunta(s.isDataDecessoPresunta());
		}
				
		
		return ret;
	}
	
	public static RegistrazioniSinantropiResponse getInfoDecesso (Persistence persistence,AnimaleNoH animale) throws UnsupportedEncodingException, ParseException {
		
		RegistrazioniSinantropiResponse ret = null;
		Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
				
		if (s!=null && s.getLookupCMI() != null && s.getDataDecesso() != null) {
			ret = new RegistrazioniSinantropiResponse();
			ret.setDecessoValue(s.getLookupCMI().getDescription());
			ret.setDataEvento(s.getDataDecesso());
			ret.setDataDecessoPresunta(s.isDataDecessoPresunta());
		}
				
		
		return ret;
	}
	
public static RegistrazioniSinantropiResponse getInfoDecesso (Connection connection,AnimaleNoH animale) throws UnsupportedEncodingException, ParseException, SQLException {
		
		RegistrazioniSinantropiResponse ret = null;
		Sinantropo s = SinantropoDAO.getSinantropoByNumero(connection, animale.getIdentificativo());
				
		if (s!=null && s.getLookupCMI() != null && s.getDataDecesso() != null) {
			ret = new RegistrazioniSinantropiResponse();
			ret.setDecessoValue(s.getLookupCMI().getDescription());
			ret.setDataEvento(s.getDataDecesso());
			ret.setDataDecessoPresunta(s.isDataDecessoPresunta());
		}
				
		
		return ret;
	}
	
	
	

}

