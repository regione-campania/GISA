/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import it.izs.bdn.bean.CapoParzialeBean;
import it.izs.bdn.bean.InfoAllevamentoBean;
import it.izs.bdn.webservices.WsAziendeQry;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import oracle.sql.DATE;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.macellazioninew.base.BeanAjax;
import org.aspcfs.modules.macellazioni.base.CapoAjax;
import org.aspcfs.modules.macellazioninew.base.PartitaAjax;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninew.utils.ReflectionUtil;
import org.aspcfs.utils.web.LookupList;
import org.directwebremoting.extend.LoginRequiredException;

	
/**
 * @author Gennaro
 */

public class AjaxCalls
{
	Logger logger = Logger.getLogger("MainLogger");
	//
	public AllevamentoAjax getAllevamento( String codice_azienda,String id_fiscale,String cod_specie ){
		
		AllevamentoAjax ret = new AllevamentoAjax();
		
		
		Connection conn = null;
		try
		{
			conn = GestoreConnessioni.getConnection(); 
			if (conn != null)
			{
			InfoAllevamentoBean iab = InfoAllevamentoBean.getInfoAllevamentoBean(codice_azienda, id_fiscale, cod_specie);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			LookupList listaSpeci = new LookupList(conn,"lookup_specie_allevata");
			LookupList listaComuni = new LookupList(conn,"lookup_comuni");
			
			if ( iab.getCod_errore() == 0  && iab.getSpecie_allevata()!=null ) {
				
				ret.setDenominazione( iab.getDenominazione() );
				ret.setCodice_azienda( iab.getCodice_azienda() );
				ret.setCodice_fiscale(iab.getCod_fiscale());
				ret.setIndirizzo(iab.getIndirizzo());
				if(iab.getComune()!=null && !iab.getComune().equals(""))
					ret.setComune(Utility.getComuneFromCodiceIstat(iab.getCodice_azienda(), iab.getComune(), conn));
				else
					ret.setComune(Utility.getComuneFromCodiceAzienda(iab.getCodice_azienda(), conn));
				ret.setNote(iab.getNote());
				ret.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
				ret.setDescrizione_specie(listaSpeci.getSelectedValue(ret.getSpecie_allevata()));
				ret.setNumero_capi(Integer.parseInt(iab.getNum_capi_totali()));
				ret.setCfDetentore(iab.getCod_fiscale_det());
				ret.setCfProprietario(iab.getCod_fiscale());
				ret.setCodiceTipoAllevamento(iab.getCodiceTipoAllevamento());
				ret.setOrientamento_prod(iab.getOrientamento_prod());
				String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
				try{
					ret.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
				}
				catch (Exception e) {
					logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
		 			
				}
				
				if (iab.getData_fine_attivita() != null)
				{
					String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
					try{
						ret.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
					}
					catch (Exception e) {
						logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
						
					}
				}
				ret.setInBDN( true );
				
			}
			else if ( iab.getCod_errore() == 1 ){
				ret.setInBDN( false );
			}
			else{
				logger.severe("Si e verificato un problema nella chiamata ai WS per l'azienda con codice " + codice_azienda + "\n" + 
						      "Errore 1: " + iab.getErrore());
			}
			
			ret.setErrore(iab.getCod_errore());
			}

			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (Exception sqle)
		{
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + codice_azienda);
		
			sqle.printStackTrace();
		}
		finally
		{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
	}
	
	
	
public AllevamentoAjax findAllevamento( String allevId/*,int idUtente*/ ){
		
		AllevamentoAjax ret = new AllevamentoAjax();
		
		
		Connection conn = null;
		try
		{
			conn = GestoreConnessioni.getConnection(); 
			if (conn != null)
			{
			InfoAllevamentoBean iab = InfoAllevamentoBean.getInfoAllevamento(allevId/*,conn,idUtente*/);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			LookupList listaSpeci = new LookupList(conn,"lookup_specie_allevata");
			LookupList listaComuni = new LookupList(conn,"lookup_comuni");
			
			if ( iab.getCod_errore() == 0  && iab.getSpecie_allevata()!=null ) {
				
				ret.setDenominazione( iab.getDenominazione() );
				ret.setCodice_azienda( iab.getCodice_azienda() );
				ret.setCodice_fiscale(iab.getCod_fiscale());
				ret.setIndirizzo(iab.getIndirizzo());
				ret.setComune(Utility.getComuneFromCodiceAzienda(iab.getCodice_azienda(), conn));
				ret.setNote(iab.getNote());
				ret.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
				ret.setDescrizione_specie(listaSpeci.getSelectedValue(ret.getSpecie_allevata()));
				ret.setNumero_capi(Integer.parseInt(iab.getNum_capi_totali()));
				ret.setCfDetentore(iab.getCod_fiscale_det());
				ret.setCfProprietario(iab.getCod_fiscale());
				ret.setCodiceTipoAllevamento(iab.getCodiceTipoAllevamento());
				ret.setFlag_Carne_Latte(iab.getFlag_carne_latte());
				String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
				try{
					ret.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
				}
				catch (Exception e) {
					logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
		 			
				}
				
				if (iab.getData_fine_attivita() != null)
				{
					String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
					try{
						ret.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
					}
					catch (Exception e) {
						logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
						
					}
				}
				ret.setInBDN( true );
				
			}
			else if ( iab.getCod_errore() == 1 ){
				ret.setInBDN( false );
			}
			else{
				logger.severe("Si e verificato un problema nella chiamata ai WS per l'azienda con id " + allevId + "\n" + 
						      "Errore 1: " + iab.getErrore());
			}
			
			ret.setErrore(iab.getCod_errore());
			}

			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (Exception sqle)
		{
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + allevId);
		
			sqle.printStackTrace();
		}
		finally
		{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
	}



	
public AllevamentoAjax findAllevamento( String allevId,int idUtente ){
	
	AllevamentoAjax ret = new AllevamentoAjax();
	
	
	Connection conn = null;
	try
	{
		conn = GestoreConnessioni.getConnection(); 
		if (conn != null)
		{
		InfoAllevamentoBean iab = InfoAllevamentoBean.getInfoAllevamento(allevId,conn,idUtente);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		LookupList listaSpeci = new LookupList(conn,"lookup_specie_allevata");
		LookupList listaComuni = new LookupList(conn,"lookup_comuni");
		
		if (( iab.getCod_errore() == 0 || (iab.getErrore()!=null && !iab.getErrore().equals(""))) && iab.getSpecie_allevata()!=null ) {
			
			ret.setCap( iab.getCap());
			ret.setDenominazione( iab.getDenominazione() );
			ret.setCodice_azienda( iab.getCodice_azienda() );
			ret.setCodice_fiscale(iab.getCod_fiscale());
			ret.setIndirizzo(iab.getIndirizzo());
			ret.setNote(iab.getNote());
			ret.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
			ret.setDescrizione_specie(listaSpeci.getSelectedValue(ret.getSpecie_allevata()));
			if(iab.getNum_capi_totali()!=null)
				ret.setNumero_capi(Integer.parseInt(iab.getNum_capi_totali()));
			ret.setCfDetentore(iab.getCod_fiscale_det());
			ret.setCfProprietario(iab.getCodFiscaleProp());
			ret.setCodiceTipoAllevamento(iab.getCodiceTipoAllevamento());
			ret.setFlag_Carne_Latte(iab.getFlag_carne_latte());
			ret.setOrientamento_prod(iab.getOrientamento_prod());
			ret.setTipologia_strutt(iab.getTipo_produzione());
			ret.setComune(iab.getDescComune());
			
			
			String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
			try{
				ret.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
			}
			catch (Exception e) {
				logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
	 			
			}
			
			if (iab.getData_fine_attivita() != null)
			{
				String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
				try{
					ret.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
				}
				catch (Exception e) {
					logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
					
				}
			}
			ret.setInBDN( true );
			
		}
		else if ( iab.getCod_errore() == 1 ){
			ret.setInBDN( false );
		}
		else{
			logger.severe("Si e verificato un problema nella chiamata ai WS per l'azienda con id " + allevId + "\n" + 
					      "Errore 1: " + iab.getErrore());
		}
		
		ret.setErrore(iab.getCod_errore());
		}

		
	}catch(LoginRequiredException e)
	{
		throw e;
	}
	catch (Exception sqle)
	{
		logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + allevId);
	
		sqle.printStackTrace();
	}
	finally
	{
		if( conn != null )
		{
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}
	}
	
	return ret;
}
	
	
public InfoAllevamentoBean getInfoAllevamento( String codice_azienda,String id_fiscale,String cod_specie,Connection db ){
		
		AllevamentoAjax ret = new AllevamentoAjax();
		
		InfoAllevamentoBean iab =  null ;
		try
		{
		
			iab = InfoAllevamentoBean.getInfoAllevamentoBean(codice_azienda, id_fiscale, cod_specie);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			LookupList listaSpeci = new LookupList(db,"lookup_specie_allevata");
			LookupList listaComuni = new LookupList(db,"lookup_comuni");
			
			if ( iab.getCod_errore() == 0 ) {
				
				ret.setDenominazione( iab.getDenominazione() );
				ret.setCodice_azienda( iab.getCodice_azienda() );
				ret.setCodice_fiscale(iab.getCod_fiscale());
				ret.setIndirizzo(iab.getIndirizzo());
				ret.setComune(Utility.getComuneFromCodiceAzienda(iab.getCodice_azienda(), db));
				ret.setNote(iab.getNote());
				ret.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
				ret.setDescrizione_specie(listaSpeci.getSelectedValue(ret.getSpecie_allevata()));
				
				
				if(iab.getNum_capi_totali()!=null)
					ret.setNumero_capi(Integer.parseInt(iab.getNum_capi_totali()));
				
				if (iab.getData_inizio_attivita() != null)
				{
				String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
				try{
					ret.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
				}
				catch (Exception e) {
					logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
				
				}
				
				}
				
				
				if (iab.getData_fine_attivita() != null)
				{
					String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
					try{
						ret.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
					}
					catch (Exception e) {
						logger.warning("Non e stato possibile recuperare la data di inizio attivita dell azienda ");
						
					}
				}
				ret.setInBDN( true );
				
			}
			else if ( iab.getCod_errore() == 1 ){
				ret.setInBDN( false );
			}
			else{
				logger.severe("Si e verificato un problema nella chiamata ai WS per l'azienda con codice " + codice_azienda + "\n" + 
						      "Errore 1: " + iab.getErrore());
				
			
			}
			
			ret.setErrore(iab.getCod_errore());
		

			
		}
		catch (Exception sqle)
		{
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + codice_azienda);
			
			sqle.printStackTrace();
		}
		
		
		return iab;
	}
	
	
	
	
	
public ArrayList<InfoAllevamentoBean> getAllevamenti( String codice_azienda,String denominazione,String specie ){
		
		
		
	ArrayList<InfoAllevamentoBean> lista = new ArrayList<InfoAllevamentoBean>();
	
		try
		{
				if (specie!=null && "0-1".equals(specie))
					specie = "";
				
			 System.out.println("denominazione : "+denominazione + " specie : "+specie +" codice_azienda : "+codice_azienda);
			 lista = InfoAllevamentoBean.findAllevamenti(codice_azienda,denominazione,specie);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + codice_azienda);
			
			sqle.printStackTrace();
		}
		
		
		return lista;
	}
	



public ArrayList<InfoAllevamentoBean> getAllevamenti( String codice_azienda,String denominazione,String specie , Connection db,int idUtente){
	
	
	
	ArrayList<InfoAllevamentoBean> lista = new ArrayList<InfoAllevamentoBean>();
	
		try
		{
				if (specie!=null && "0-1".equals(specie))
					specie = "";
				
			 System.out.println("denominazione : "+denominazione + " specie : "+specie +" codice_azienda : "+codice_azienda);
			 lista = InfoAllevamentoBean.findAllevamenti(codice_azienda,denominazione,specie,db,idUtente);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + codice_azienda);
			
			sqle.printStackTrace();
		}
		
		
		return lista;
	}
public InfoAllevamentoBean getAllevamentoProvenienza( String codice_azienda,String denominazione,String specie,String tipoAll ){
	
	
	String cod_bovini = "" ;

	String cod_bufalini = "" ;
	
	
	
	
	Connection db = null ;
	ArrayList<InfoAllevamentoBean> lista = new ArrayList<InfoAllevamentoBean>();
	InfoAllevamentoBean infoRet = new InfoAllevamentoBean();
		try
		{
			db = GestoreConnessioni.getConnection();
				if (specie!=null && "0-1".equals(specie))
					specie = "";
				
			 lista = InfoAllevamentoBean.findAllevamenti_old(codice_azienda,denominazione,specie);
			 
			 for (InfoAllevamentoBean info : lista){
				
				 
				 if (info.getData_fine_attivita()==null || ("".equalsIgnoreCase(info.getData_fine_attivita())) || "null".equalsIgnoreCase(info.getData_fine_attivita()))
				 {
					 if (tipoAll.equals("1") && (Integer.parseInt(info.getCodice_specie())== 121 || Integer.parseInt(info.getCodice_specie())== 129) )
					 {
					 info.setCodiceComune(info.getCodice_azienda(), db);
					 return info ;
					 }
					 else
					 {
						 if (tipoAll.equals("2") &&  info.getCodice_specie() !=null && (Integer.parseInt(info.getCodice_specie())== 124|| Integer.parseInt(info.getCodice_specie())== 125) )
						 {
						 info.setCodiceComune(info.getCodice_azienda(), db);
						 return info ;
						 }
						 
					 }
				 }
				 
			 }
			
			
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + codice_azienda);
			
			sqle.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		
		return infoRet;
	}
	/*
	public CapoAjax getCapoOLD( String matricola )
	{
		CapoAjax ret = new CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		//ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		String		sql = "SELECT * FROM m_capi_bdn WHERE upper( matricola ) = upper( ? )";
		
		Connection conn = null;
		
		
//		String driver	= prefs.get("GATEKEEPER.DRIVER");
//		String url		= prefs.get("GATEKEEPER.URL");
//		String user		= prefs.get("GATEKEEPER.USER");
//		String password	= prefs.get("GATEKEEPER.PASSWORD");
		
		try
		{
			
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			
			
			conn = GestoreConnessioni.getConnection();
			
			PreparedStatement stat = conn.prepareStatement( sql );
			stat.setString( 1, matricola.trim() );
			
			ResultSet res = stat.executeQuery();
			
			if( res.next() )
			{
				ret.setCodice_azienda( res.getString( "codice_azienda" ) );
				ret.setData_nascita( DateUtils.getDateAsString(  res.getTimestamp( "data_nascita" ), Locale.ITALY ) );
				ret.setRazza( res.getString( "razza" ) );
				ret.setSesso( res.getBoolean( "maschio" ) );
				ret.setSpecie( res.getString( "specie" ) );
				ret.setInBDN( true );
			}
			else
			{
				ret.setInBDN( false );
			}
			
			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL" );
			stat.setString( 1, matricola.trim() );
			
			res = stat.executeQuery();
			
			if( res.next() )
			{
				ret.setMacellato( true );
			}
			else
			{
				ret.setMacellato( false );
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
	}
	*/
	
	public CapoAjax getCapo( String matricola ){
		
		CapoAjax ret = new CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		//ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet res = null;
		
		/*
		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");
		*/
		try
		{
			/*
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			*/
			
			conn = GestoreConnessioni.getConnection();
			
			if (conn!=null)
			{
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			CapoParzialeBean cpb = CapoParzialeBean.getCapoParzialeBean(ret.getMatricola());
			
			if ( cpb.getCod_errore() == 0 ) {
				
				ret.setAsl( Utility.getIdAslFromIstatAsl( cpb.getIstatAsl(), conn, matricola ) );
				ret.setCodice_azienda( cpb.getCodice_azienda() );
				ret.setComune( Utility.getComuneFromCodiceAzienda( cpb.getCodice_azienda(), conn ) );
				
				String dataNascita = cpb.getData_nascita_capo().substring(0, 10);
				try{
					ret.setData_nascita( sdf1.format( sdf2.parse(dataNascita) ) );
				}
				catch (Exception e) {
					logger.warning("Non e stato possibile recuperare la data di nascita del capo con matricola " + matricola);
				
				}
				
				ret.setRazza( Utility.getIdRazzaFromTextCodeRazza(cpb.getRazza(), conn, matricola) );
				ret.setRegione( Utility.getIdRegioneFromIstatAsl(cpb.getIstatAsl() , conn) );
				ret.setSesso( cpb.getSesso_capo().equalsIgnoreCase("M") );
				ret.setSpecie( Utility.getIdSpecieFromTextCodeSpecie( cpb.getSpecie_capo(), conn, matricola) );
				ret.setInBDN( true );
			}
			else if ( cpb.getCod_errore() == 1 ){
				ret.setInBDN( false );
			}
			else{
				logger.severe("Si e verificato un problema nella chiamata ai WS per il capo con matricola " + ret.getMatricola() + "\n" + 
						      "Errore 1: " + cpb.getErrore1() + "\n Errore 2: " + cpb.getErrore2());
			
			}
			
			ret.setErrore(cpb.getCod_errore());
			
//			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL" );
//			stat.setString( 1, matricola.trim() );
//			
//			res = stat.executeQuery();
//			
//			if( res.next() )
//			{
//				ret.setEsistente( true );
//			}
//			else
//			{
//				ret.setEsistente( false );
//			}
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (Exception sqle)
		{
			logger.severe("Eccezione durante il recupero dei dati dalla BDN per il capo con matricola " + matricola);
		
			sqle.printStackTrace();
		}
		finally
		{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
	}
	
	public String[] getSeduteMacellazione( String data,int orgId){
		
		ArrayList<String> seduteTemp = new ArrayList<String>();
		
		String[] numSeduteMacellazione = null;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		Timestamp d = null;
		DateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			d = new Timestamp( sdf.parse( data ).getTime() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			//stat = conn.prepareStatement("SELECT COUNT(DISTINCT cd_seduta_macellazione) FROM m_capi WHERE id_macello = ? AND vpm_data = ?");
			stat = conn.prepareStatement("SELECT DISTINCT case when cd_seduta_macellazione is null" +
					" then '0' else cd_seduta_macellazione || '' END" +
					" AS sedute FROM m_capi WHERE id_macello = ? AND vpm_data = ?" +
					"ORDER BY case when cd_seduta_macellazione is null " +
					"then '0' else cd_seduta_macellazione || '' END");
			stat.setInt(1, orgId);
			stat.setTimestamp(2, d);
			res = stat.executeQuery();
			
			while ( res.next() ){
				seduteTemp.add( res.getString(1));
			}
			numSeduteMacellazione = new String[seduteTemp.size()];
			
			int i = 0;
			
			for (String seduta:seduteTemp) {
				numSeduteMacellazione[i] = seduta;
				i++;
			}
			}
		}
		catch(SQLException sqle){ 
			sqle.printStackTrace();

		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		

		return numSeduteMacellazione;
	}

//	public BeanAjax isCapoEsistente( String identificativo, String codiceAzienda, Integer numOvini, Integer numCaprini, ConfigTipo configTipo ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
//		
//		BeanAjax ret = (BeanAjax)ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBeanAjax());
//		ReflectionUtil.invocaMetodo(ret, configTipo.getMetodoSetIdentificativo(), new Class[]{String.class}, new Object[]{identificativo});
//		
//		Connection conn = null;
//		PreparedStatement stat = null;
//		ResultSet res = null;
//		
//		try{
//			conn = GestoreConnessioni.getConnection();
//
//			if (conn!=null)
//			{
//				Object o = ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBean());
//				String query = (String)ReflectionUtil.invocaMetodo(o, configTipo.getMetodoCostruisciQueryEsistenzaCapo(), new Class[]{ConfigTipo.class, Integer.class,Integer.class}, new Object[]{configTipo,numOvini,numCaprini});
//				stat = conn.prepareStatement( query );
//				stat = (PreparedStatement)ReflectionUtil.invocaMetodo(o, configTipo.getMetodoCostruisciStatementEsistenzaCapo(), new Class[]{PreparedStatement.class, String.class,String.class}, new Object[]{stat,identificativo,codiceAzienda});
//			
//				res = stat.executeQuery();
//			
//				if( res.next() )
//				{
//					ret.setEsistente(true);
//				}
//			}
//			
//		}
//		catch (SQLException sqle){
//			logger.severe("Eccezione nel metodo per determinare se " + configTipo.getDescrizioneCampoIdentificativoTabella() + " " + identificativo + " e stata gia inserita o meno in banca dati");
//			sqle.printStackTrace();
//		}
//		finally{
//			if( conn != null ){
//				GestoreConnessioni.freeConnection(conn);
//				conn = null;
//			}
//		}
//		
//		return ret;
//		
//	}
	
	public PartitaAjax isCapoEsistenteUpdate(int idPartitaDaModificare, String partita, String codiceAziendaProvenienza, int numOvini, int numCaprini )
	{
		
		PartitaAjax ret = new PartitaAjax();
		ret.setPartita( partita.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
				String select = "SELECT * " +
						  "FROM m_partite " +
						  "WHERE upper( cd_partita ) = upper( ? ) " +
						  	"AND trashed_date IS NULL " +
							"AND cd_codice_azienda_provenienza = ? " +
							"AND id <> ? ";
				
				if(numOvini>0 && numCaprini<=0)
					select += " and cd_num_capi_ovini > 0 ";
				else if(numCaprini>0 && numOvini<=0)
					select += " and cd_num_capi_caprini > 0 "; 
				else if(numCaprini>0 && numOvini>0)
					select += " and (cd_num_capi_ovini > 0  or cd_num_capi_caprini > 0 )"; 
				
			stat = conn.prepareStatement( select );
			stat.setString( 1, partita.trim() );
			stat.setString( 2, codiceAziendaProvenienza );
			stat.setInt( 3, idPartitaDaModificare );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la partita " + partita + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	public org.aspcfs.modules.macellazioninewopu.base.PartitaAjax isCapoEsistenteUpdateOpu(int idPartitaDaModificare, String partita, String codiceAziendaProvenienza, int numOvini, int numCaprini )
	{
		
		org.aspcfs.modules.macellazioninewopu.base.PartitaAjax ret = new org.aspcfs.modules.macellazioninewopu.base.PartitaAjax();
		ret.setPartita( partita.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
				String select = "SELECT * " +
						  "FROM m_partite " +
						  "WHERE upper( cd_partita ) = upper( ? ) " +
						  	"AND trashed_date IS NULL " +
							"AND cd_codice_azienda_provenienza = ? " +
							"AND id <> ? ";
				
				if(numOvini>0 && numCaprini<=0)
					select += " and cd_num_capi_ovini > 0 ";
				else if(numCaprini>0 && numOvini<=0)
					select += " and cd_num_capi_caprini > 0 "; 
				else if(numCaprini>0 && numOvini>0)
					select += " and (cd_num_capi_ovini > 0  or cd_num_capi_caprini > 0 )"; 
				
			stat = conn.prepareStatement( select );
			stat.setString( 1, partita.trim() );
			stat.setString( 2, codiceAziendaProvenienza );
			stat.setInt( 3, idPartitaDaModificare );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la partita " + partita + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	public org.aspcfs.modules.macellazioninewsintesis.base.PartitaAjax isCapoEsistenteUpdateSintesis(int idPartitaDaModificare, String partita, String codiceAziendaProvenienza, int numOvini, int numCaprini )
	{
		
		org.aspcfs.modules.macellazioninewsintesis.base.PartitaAjax ret = new org.aspcfs.modules.macellazioninewsintesis.base.PartitaAjax();
		ret.setPartita( partita.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
				String select = "SELECT * " +
						  "FROM m_partite " +
						  "WHERE upper( cd_partita ) = upper( ? ) " +
						  	"AND trashed_date IS NULL " +
							"AND cd_codice_azienda_provenienza = ? " +
							"AND id <> ? ";
				
				if(numOvini>0 && numCaprini<=0)
					select += " and cd_num_capi_ovini > 0 ";
				else if(numCaprini>0 && numOvini<=0)
					select += " and cd_num_capi_caprini > 0 "; 
				else if(numCaprini>0 && numOvini>0)
					select += " and (cd_num_capi_ovini > 0  or cd_num_capi_caprini > 0 )"; 
				
			stat = conn.prepareStatement( select );
			stat.setString( 1, partita.trim() );
			stat.setString( 2, codiceAziendaProvenienza );
			stat.setInt( 3, idPartitaDaModificare );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la partita " + partita + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	public boolean controlloProgressivoMacellazioneBovini(int numeroSeduta, int idMacello, String dataMacellazione, int progressivoMacellazione, String matricola){
		
		boolean ok = false;
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			String query = "select count(*) as num from m_capi where id_macello = ? and to_char(vpm_data, 'dd/MM/yyyy') = ? and progressivo_macellazione = ? and cd_seduta_macellazione = ? and cd_matricola != ? and trashed_date is null and stato_macellazione != 'Incompleto: Inseriti solo i dati sul controllo documentale'";
			pst = conn.prepareStatement(query);
			pst.setInt(1, idMacello );
			pst.setString(2, dataMacellazione);
			pst.setInt(3, progressivoMacellazione );
			pst.setInt(4, numeroSeduta );
			pst.setString(5, matricola);
			
			rs = pst.executeQuery();
			
			if(rs.next()){
				ok = (rs.getInt("num") == 0);
			}
			
			}
		}
		catch (SQLException sqle)
		{
			logger.severe("Eccezione SQL nel controllo del prograssivo macellazione");
			sqle.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			if(rs != null){
				try {
					rs.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pst != null){
				try {
					pst.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ok;
		
	}
	
public boolean controlloProgressivoMacellazioneNew(ConfigTipo configTipo, int idMacello, String dataMacellazione, int progressivoMacellazione, String matricola){
		
		boolean ok = false;
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			String query = "select count(*) as num from " + configTipo.getNomeTabella() + " where id_macello = ? and to_char(vpm_data, 'dd/MM/yyyy') = ? and progressivo_macellazione = ? and " + configTipo.getNomeCampoIdentificativoTabella() + " != ? and trashed_date is null ";
			pst = conn.prepareStatement(query);
			pst.setInt(1, idMacello );
			pst.setString(2, dataMacellazione);
			pst.setInt(3, progressivoMacellazione );
			pst.setString(4, matricola);
			
			rs = pst.executeQuery();
			
			if(rs.next()){
				ok = (rs.getInt("num") == 0);
			}
			
			}
		}
		catch (SQLException sqle)
		{
			logger.severe("Eccezione SQL nel controllo del prograssivo macellazione");
			sqle.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			if(rs != null){
				try {
					rs.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pst != null){
				try {
					pst.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ok;
		
	}

public boolean controlloProgressivoMacellazione(ConfigTipo configTipo, int idMacello, String dataMacellazione, int progressivoMacellazione, String matricola){
	
	boolean ok = false;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	try
	{
		conn = GestoreConnessioni.getConnection();

		if (conn!=null)
		{
		String query = "select count(*) as num from " + configTipo.getNomeTabella() + " where id_macello = ? and to_char(vpm_data, 'dd/MM/yyyy') = ? and progressivo_macellazione = ? and " + configTipo.getNomeCampoIdentificativoTabella() + " != ? and trashed_date is null ";
		pst = conn.prepareStatement(query);
		pst.setInt(1, idMacello );
		pst.setString(2, dataMacellazione);
		pst.setInt(3, progressivoMacellazione );
		pst.setString(4, matricola);
		
		rs = pst.executeQuery();
		
		if(rs.next()){
			ok = (rs.getInt("num") == 0);
		}
		
		}
	}
	catch (SQLException sqle)
	{
		logger.severe("Eccezione SQL nel controllo del prograssivo macellazione");
		sqle.printStackTrace();
	}catch(LoginRequiredException e)
	{
		throw e;
	}
	finally
	{
		if(rs != null){
			try {
				rs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst != null){
			try {
				pst.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( conn != null )
		{
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}
	}
	
	return ok;
	
}



public boolean controlloProgressivoMacellazioneOpu(org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo configTipo, int idMacello, String dataMacellazione, int progressivoMacellazione, String matricola){
	
	boolean ok = false;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	try
	{
		conn = GestoreConnessioni.getConnection();

		if (conn!=null)
		{
		String query = "select count(*) as num from " + configTipo.getNomeTabella() + " where id_macello = ? and to_char(vpm_data, 'dd/MM/yyyy') = ? and progressivo_macellazione = ? and " + configTipo.getNomeCampoIdentificativoTabella() + " != ? and trashed_date is null ";
		pst = conn.prepareStatement(query);
		pst.setInt(1, idMacello );
		pst.setString(2, dataMacellazione);
		pst.setInt(3, progressivoMacellazione );
		pst.setString(4, matricola);
		
		rs = pst.executeQuery();
		
		if(rs.next()){
			ok = (rs.getInt("num") == 0);
		}
		
		}
	}
	catch (SQLException sqle)
	{
		logger.severe("Eccezione SQL nel controllo del prograssivo macellazione");
		sqle.printStackTrace();
	}
	finally
	{
		if(rs != null){
			try {
				rs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst != null){
			try {
				pst.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( conn != null )
		{
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}
	}
	
	return ok;
	
}

public boolean controlloProgressivoMacellazioneSintesis(org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo configTipo, int idMacello, String dataMacellazione, int progressivoMacellazione, String matricola){
	
	boolean ok = false;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	try
	{
		conn = GestoreConnessioni.getConnection();

		if (conn!=null)
		{
		String query = "select count(*) as num from " + configTipo.getNomeTabella() + " where id_macello = ? and to_char(vpm_data, 'dd/MM/yyyy') = ? and progressivo_macellazione = ? and " + configTipo.getNomeCampoIdentificativoTabella() + " != ? and trashed_date is null ";
		pst = conn.prepareStatement(query);
		pst.setInt(1, idMacello );
		pst.setString(2, dataMacellazione);
		pst.setInt(3, progressivoMacellazione );
		pst.setString(4, matricola);
		
		rs = pst.executeQuery();
		
		if(rs.next()){
			ok = (rs.getInt("num") == 0);
		}
		
		}
	}
	catch (SQLException sqle)
	{
		logger.severe("Eccezione SQL nel controllo del prograssivo macellazione");
		sqle.printStackTrace();
	}
	finally
	{
		if(rs != null){
			try {
				rs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst != null){
			try {
				pst.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( conn != null )
		{
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}
	}
	
	return ok;
	
}
	
	
	public String[] getCoordinate
		( String indirizzo, String citta, String provincia, String cap,
		  String campo_lat, String campo_lon, String campo_flag )
	{
		String[] ret = new String[6];
		
		if( 	((indirizzo == null) || (indirizzo.trim().length() == 0)) &&
				((citta == null) || (citta.trim().length() == 0)) &&
				((provincia == null) || (provincia.trim().length() == 0)) &&
				((cap == null) || (cap.trim().length() == 0)) )
		{
			ret[0] = "";
			ret[1] = "";
			ret[2] = "";
		}
		else
		{
			GeoCoder geo = new GeoCoder();
			String[] temp = geo.getCoords( indirizzo, citta, provincia );
			if( temp[2].equalsIgnoreCase("e") )
			{
				ret[0] = "errore";
				ret[1] = "ricerca";
				ret[2] = temp[2];
			}
			else
			{
				//String[] c = convert2GaussBoaga( temp );
				//String[] c = convert2Wgs84UTM33N(temp);
				
				/*ret[0] = c[0];
				ret[1] = c[1];
				ret[2] = temp[2];*/
				
				if (temp[0].equals("0.0") && temp[1].equals("0.0") ){
				temp = GetCoordinateFromDb(temp, indirizzo, citta, provincia);
				}
					
				
				ret[0] = temp[0];
				ret[1] = temp[1];
				ret[2] = temp[2];
				
				
			}
		}
		
		ret[3] = campo_lat;
		ret[4] = campo_lon;
		ret[5] = campo_flag;
		
		
		return ret;
	}
	
	
	private String[] GetCoordinateFromDb(String[] temp, String indirizzo, String citta, String provincia) {

		String[] res = temp;
		
		Connection conn = null;
		try
		{
			conn = GestoreConnessioni.getConnection(); 
			if (conn != null)
			{
				
				String sql = "select count(latitudine||';'||longitudine), latitudine, longitudine from opu_indirizzo where 1=1 ";
						
						if (!citta.equals(""))
							sql+=" and comune in (select id from comuni1 where nome ilike ?) ";
						
						sql+=" and latitudine > 0 and longitudine > 0 group by latitudine, longitudine order by count(latitudine||';'||longitudine) desc limit 1";

				PreparedStatement pst = conn.prepareStatement(sql);
				int i = 0;
				if (!citta.equals(""))
					pst.setString(++i, "%"+citta+"%");
				System.out.println("GET COORDS ULTIMA SPIAGGIA: Cerco sul db "+pst.toString());
				ResultSet rs = pst.executeQuery();
				if (rs.next()){
					res[1] = rs.getString("latitudine");
					res[0] = rs.getString("longitudine");
				}
				
			}
			
		}catch(Exception e)
		{ 			
			e.printStackTrace();
		}
		finally
		{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		
		return res;
		
	}








	public CapoAjax isCapoEsistente( String matricola ){
		
		CapoAjax ret = new CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL" );
			stat.setString( 1, matricola.trim() );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la matricola " + matricola + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	public CapoAjax isCapoEsistenteUpdateCapo( String matricola, int id ){
		
		CapoAjax ret = new CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL AND id <> ? " );
			stat.setString( 1, matricola.trim() );
			stat.setInt   ( 2, id );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la matricola " + matricola + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
public org.aspcfs.modules.macellazioniopu.base.CapoAjax isCapoEsistenteOpu( String matricola ){
		
	org.aspcfs.modules.macellazioniopu.base.CapoAjax ret = new org.aspcfs.modules.macellazioniopu.base.CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL" );
			stat.setString( 1, matricola.trim() );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la matricola " + matricola + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	public org.aspcfs.modules.macellazioniopu.base.CapoAjax isCapoEsistenteUpdateCapoOpu( String matricola, int id ){
		
		org.aspcfs.modules.macellazioniopu.base.CapoAjax ret = new org.aspcfs.modules.macellazioniopu.base.CapoAjax();
		ret.setMatricola( matricola.trim().toUpperCase() );
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
			stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL AND id <> ? " );
			stat.setString( 1, matricola.trim() );
			stat.setInt   ( 2, id );
			
			res = stat.executeQuery();
			
			if( res.next() ){
				ret.setEsistente(true);
			}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se la matricola " + matricola + " e stata gia inserita o meno in banca dati");
			sqle.printStackTrace();
		}
		finally{
			if( conn != null ){
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return ret;
		
	}
	
	
	public org.aspcfs.modules.macellazionisintesis.base.CapoAjax isCapoEsistenteSintesis( String matricola ){
		
		org.aspcfs.modules.macellazionisintesis.base.CapoAjax ret = new org.aspcfs.modules.macellazionisintesis.base.CapoAjax();
			ret.setMatricola( matricola.trim().toUpperCase() );
			
			Connection conn = null;
			PreparedStatement stat = null;
			ResultSet res = null;
			
			try{
				conn = GestoreConnessioni.getConnection();

				if (conn!=null)
				{
				stat = conn.prepareStatement( "SELECT * FROM m_capi WHERE upper( cd_matricola ) = upper( ? ) AND trashed_date IS NULL" );
				stat.setString( 1, matricola.trim() );
				
				res = stat.executeQuery();
				
				if( res.next() ){
					ret.setEsistente(true);
				}
				}
				
			}
			catch (SQLException sqle){
				logger.severe("Eccezione nel metodo per determinare se la matricola " + matricola + " e stata gia inserita o meno in banca dati");
				sqle.printStackTrace();
			}
			finally{
				if( conn != null ){
					GestoreConnessioni.freeConnection(conn);
					conn = null;
				}
			}
			
			return ret;
			
		}
}
