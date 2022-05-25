/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioninewopu.base.Azione;
import org.aspcfs.modules.macellazioninewopu.base.Campione;
import org.aspcfs.modules.macellazioninewopu.base.Capo;
import org.aspcfs.modules.macellazioninewopu.base.Condizione;
import org.aspcfs.modules.macellazioninewopu.base.Esito;
import org.aspcfs.modules.macellazioninewopu.base.GenericBean;
import org.aspcfs.modules.macellazioninewopu.base.Partita;
import org.aspcfs.modules.macellazioninewopu.base.PartitaSeduta;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class MacelliUtil
{
	public static final String green = "#00FF00";
	public static final String orange = "#FFA500";
	
	public static int nextNumeroVerbaleSequestro( Capo capo, Connection db )
		throws SQLException
	{
		int ret = 1;
		
		String sql = "select coalesce( max(  to_number( nullif( split_part(seqa_num_verbale, '-', 2), '' ), '999999999999') ), '0') as num from m_capi where id_macello = ?";
		PreparedStatement stat = db.prepareStatement( sql );
		stat.setInt( 1, capo.getId_macello() );
		ResultSet res = stat.executeQuery();
		
		if( res.next() )
		{
			ret = ( res.getInt( "num" ) + 1 );
		}
		
		return ret;
	}
	
//	public static void setNextNumeroVerbaleSequestro( Capo capo, Connection db )
//		throws SQLException
//	{
//		if( 	(capo.getSeqa_data() != null) && 
//				( (capo.getSeqa_num_verbale() == null) || (capo.getSeqa_num_verbale().trim().length() == 0) ) )
//		{
//			capo.setSeqa_num_verbale( "SEQ" + capo.getId_macello() + "-" + nextNumeroVerbaleSequestro( capo, db ) );
//		}
//	}
	
	public static int nextNumeroMacellazione( GenericBean capoGeneric, Connection db )
		throws SQLException
	{
		int ret = 1;
		
		String sql = "select coalesce( max( mac_progressivo ), '0') as num from m_capi where id_macello = ?";
		PreparedStatement stat = db.prepareStatement( sql );
		stat.setInt( 1, capoGeneric.getId_macello() );
		ResultSet res = stat.executeQuery();
		
		if( res.next() )
		{
			ret = ( res.getInt( "num" ) + 1 );
		}
		
		return ret;
	}
	
	public static int nextNumeroMacellazione( Partita partita, Connection db )
			throws SQLException
		{
			int ret = 1;
			
			String sql = "select coalesce( max( mac_progressivo ), '0') as num from m_partite where id_macello = ?";
			PreparedStatement stat = db.prepareStatement( sql );
			stat.setInt( 1, partita.getId_macello() );
			ResultSet res = stat.executeQuery();
			
			if( res.next() )
			{
				ret = ( res.getInt( "num" ) + 1 );
			}
			
			return ret;
		}
	
	public static void setNextNumeroMacellazione( GenericBean capoGeneric, Connection db )
		throws SQLException
	{
		if( 	(capoGeneric.getMac_progressivo() <= 0) && ( capoGeneric.getVpm_data() != null || capoGeneric.getMac_tipo() > 0 ) )
		{
			capoGeneric.setMac_progressivo( nextNumeroMacellazione( capoGeneric, db ) );
		}
	}
	
	public static void setNextNumeroMacellazione( Partita partita, Connection db ) throws SQLException
		{
			if( 	(partita.getMac_progressivo() <= 0) &&
					( partita.getVpm_data() != null || partita.getMac_tipo() > 0 ) )
			{
				partita.setMac_progressivo( nextNumeroMacellazione( partita, db ) );
			}
		}
	
	public static String getRecordColor( GenericBean capoGeneric, Connection db, ConfigTipo configTipo )
	{
		String ret = green;
		
		//Controlla che sia stato compilato il solo controllo documentale
		if(capoGeneric.isSolo_cd()){
			return "yellow";
		}
		
		if(capoGeneric.isManca_BSE_Nmesi()){
			return "orange";
		}
		
		//Controlla se ci sono campioni senza esito
		ArrayList<Campione> cs = Campione.load( capoGeneric.getId(), configTipo,db );
		for( Campione c: cs )
		{
//			if( c.getEsito() == null || c.getEsito().trim().length() == 0 )
//			{
//				ret = orange;
//			}
			if( c.getId_motivo() != 5 && c.getId_esito() <= 0  )
			{
				ret = orange;
			}
		}
		
		//Controlla se e' stato inserito almeno un destinatario
		if( "".equals(capoGeneric.getDestinatario_1_nome()) &&  "".equals(capoGeneric.getDestinatario_2_nome()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
		{
			ret = orange;
		}
		
		//Controlla se e' stato inserito l'esito del Test BSE
		if( capoGeneric.getBse_data_prelievo() != null && "-1".equals(capoGeneric.getBse_esito()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
		{
			ret = orange;
		}
		
		//In VAM se il provvedimento adottato e' "Isolamento" allora il capo 
		//resta evidenziato in arancione fino alla macellazione o distruzione
		if( capoGeneric.getVam_provvedimenti() == 5 )
		{
			ret = orange;
		}
		
		//In VPM se esito e' "Libero Consumo Previo Risanamento" il capo
		//resta evidenziato in arancione fino alla valorizzazione del campo "data effettiva di liberalizzazione"
		if( capoGeneric.getVpm_esito() == 4 && capoGeneric.getLcpr_data_effettiva_liber() == null )
		{
			ret = orange;
		}
		
		return ret;
	}
	
	public static String getRecordColorSeduta( PartitaSeduta seduta, Connection db, ConfigTipo configTipo )
	{
		String ret = green;
		
		if(!seduta.getStato_macellazione().toLowerCase().contains("OK".toLowerCase()))
		{
			ret = "orange";
		}
		
		return ret;
	}
	
	public static String getRecordColor( Partita partita, Connection db, ConfigTipo configTipo )
	{
		String ret = green;
		
		//Controlla che sia stato compilato il solo controllo documentale
		if(partita.isSolo_cd()){
			return "yellow";
		}
		
		if(partita.isManca_BSE_Nmesi()){
			return "orange";
		}
		
		//Controlla se ci sono campioni senza esito
		ArrayList<Campione> cs = Campione.load( partita.getId(), configTipo,db );
		for( Campione c: cs )
		{
//			if( c.getEsito() == null || c.getEsito().trim().length() == 0 )
//			{
//				ret = orange;
//			}
			if( c.getId_motivo() != 5 && c.getId_esito() <= 0  )
			{
				ret = orange;
			}
		}
		
		//Controlla se e' stato inserito almeno un destinatario
		if( "".equals(partita.getDestinatario_1_nome()) &&  "".equals(partita.getDestinatario_2_nome()) )// ( partita.getBse_esito() == null || partita.getBse_esito().length() == 0 ) )
		{
			ret = orange;
		}
		
		//Controlla se e' stato inserito l'esito del Test BSE
		if( partita.getBse_data_prelievo() != null && "-1".equals(partita.getBse_esito()) )// ( partita.getBse_esito() == null || partita.getBse_esito().length() == 0 ) )
		{
			ret = orange;
		}
		
		//In VAM se il provvedimento adottato e' "Isolamento" allora il partita 
		//resta evidenziato in arancione fino alla macellazione o distruzione
		if( partita.getVam_provvedimenti() == 5 )
		{
			ret = orange;
		}
		
		//In VPM se esito e' "Libero Consumo Previo Risanamento" il partita
		//resta evidenziato in arancione fino alla valorizzazione del campo "data effettiva di liberalizzazione"
		ArrayList<Esito> esiti = Esito.load( partita.getId(), configTipo, db );
		if( esiti.contains(Esito.load(4, configTipo, db)) && partita.getLcpr_data_effettiva_liber() == null )
		{
			ret = orange;
		}
		
		return ret;
	}
	
	public static String getStatoMacellazione( GenericBean capoGeneric, Connection db, ArrayList<Campione> cmps )
	{
		String ret = "OK";
		
		//Controlla che sia stato compilato il solo controllo documentale
		if(capoGeneric.isSolo_cd()){
			ret = "Incompleto: Inseriti solo i dati sul controllo documentale";
			
			if( capoGeneric.getBse_data_prelievo() != null && "-1".equals(capoGeneric.getBse_esito()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
			{
				ret+=", Mancanza esito del Test BSE";
			}
			
			return ret;
			
		}
		
		if(capoGeneric.isManca_BSE_Nmesi()){
			
			if (ret.equals("OK") ) {
				ret = "Incompleto: In attesa di test BSE";
			} else {
				ret+=", In attesa di test BSE";
			}
			
		}
		
		//Controlla se ci sono campioni senza esito
		//ArrayList<Campione> cs = Campione.load( capo.getId(), db );
		if(cmps != null){
			for( Campione c: cmps )
			{
				if( c.getId_motivo() != 5 && c.getId_esito() <= 0  )
				{
					if (ret.equals("OK") ) {
						ret = "Incompleto: Presenti campioni senza esito";
					}
				}
			}
		}
		
		//Controlla se e' stato inserito almeno un destinatario
		if( "".equals(capoGeneric.getDestinatario_1_nome()) &&  "".equals(capoGeneric.getDestinatario_2_nome()) && capoGeneric.getMavam_data()==null )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: Mancanza Destinatari Carni";
			} else {
				ret+=", Mancanza Destinatari Carni";
			}
		}
		else
		{
			if( "".equals(capoGeneric.getDestinatario_1_nome()) &&  "".equals(capoGeneric.getDestinatario_2_nome()) && capoGeneric.getMavam_data()!=null )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
			{
				
				if (ret.equals("OK") ) {
					ret = "OK-Non Macellato : Morto Prima della Macellazione";
				} else {
					ret+=", Non Macellato : Morto Prima della Macellazione";
				}
				
			}
			
		}
		
		//Controlla se e' stato inserito l'esito del Test BSE
		if( capoGeneric.getBse_data_prelievo() != null && "-1".equals(capoGeneric.getBse_esito()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: Mancanza esito del Test BSE";
			} else {
				ret+=", Mancanza esito del Test BSE";
			}
		}
		
		//In VAM se il provvedimento adottato e' "Isolamento" allora il capo 
		//resta evidenziato in arancione fino alla macellazione o distruzione
		if( capoGeneric.getVam_provvedimenti() == 5 )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: In visita AM il provvedimento adottato e' \"Isolamento\"";
			} else {
				ret+=", In visita AM il provvedimento adottato e' \"Isolamento\"";
			}
		}
		
		//In VPM se esito e' "Libero Consumo Previo Risanamento" il capo
		//resta evidenziato in arancione fino alla valorizzazione del campo "data effettiva di liberalizzazione"
		if( capoGeneric.getVpm_esito() == 4 && capoGeneric.getLcpr_data_effettiva_liber() == null )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: In visita PM valorizzare il campo \"Data effettiva di liberalizzazione\"";
			} else {
				ret+=", In visita PM valorizzare il campo \"Data effettiva di liberalizzazione\"";
			}
		}
		
		ret+=".";
		
		return ret;
	}
	
	public static String getStatoMacellazioneSeduta( PartitaSeduta seduta, Partita partita, Connection db, ArrayList<Campione> cmps )
	{
		String ret = "OK";
		
		//Controlla che sia stato compilato il solo controllo documentale
		if(seduta.isSolo_cd()){
			ret = "Incompleto: Inseriti solo i dati sul controllo documentale";
			
			if( partita.getBse_data_prelievo() != null && "-1".equals(partita.getBse_esito()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
			{
				ret+=", Mancanza esito del Test TSE";
			}
			
			return ret;
			
		}
		
		if(partita.isManca_BSE_Nmesi()){
			
			if (ret.equals("OK") ) {
				ret = "Incompleto: In attesa di test BSE";
			} else {
				ret+=", In attesa di test BSE";
			}
			
		}
		
		//Controlla se ci sono campioni senza esito
		//ArrayList<Campione> cs = Campione.load( capo.getId(), db );
		if(cmps != null){
			for( Campione c: cmps )
			{
				if( c.getId_motivo() != 5 && c.getId_esito() <= 0  )
				{
					if (ret.equals("OK") ) {
						ret = "Incompleto: Presenti campioni senza esito";
					}
				}
			}
		}
		
		//Controlla se e' stato inserito almeno un destinatario
		if( seduta.getVpm_data()==null)
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: Mancanza Data Macellazione";
			} else {
				ret+=", Mancanza Data Macellazione";
			}
		}
		
		//Controlla se e' stato inserito l'esito del Test BSE
		if( partita.getBse_data_prelievo() != null && "-1".equals(partita.getBse_esito()) )// ( capo.getBse_esito() == null || capo.getBse_esito().length() == 0 ) )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: Mancanza esito del Test BSE";
			} else {
				ret+=", Mancanza esito del Test BSE";
			}
		}
		
		//In VAM se il provvedimento adottato e' "Isolamento" allora il capo 
		//resta evidenziato in arancione fino alla macellazione o distruzione
		if( seduta.getVam_provvedimenti() == 5 )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: In visita AM il provvedimento adottato e' \"Isolamento\"";
			} else {
				ret+=", In visita AM il provvedimento adottato e' \"Isolamento\"";
			}
		}
		
		//In VPM se esito e' "Libero Consumo Previo Risanamento" il capo
		//resta evidenziato in arancione fino alla valorizzazione del campo "data effettiva di liberalizzazione"
		if( seduta.getVpm_esito() == 4 && seduta.getLcpr_data_effettiva_liber() == null )
		{
			if (ret.equals("OK") ) {
				ret = "Incompleto: In visita PM valorizzare il campo \"Data effettiva di liberalizzazione\"";
			} else {
				ret+=", In visita PM valorizzare il campo \"Data effettiva di liberalizzazione\"";
			}
		}
		
		ret+=".";
		
		return ret;
	}
	
	public static int getStato( Capo capo, Connection db )	
	{
		int ret = -1;

		//carico elenco stati
		//dall'ultimo al primo
		//-carico tutte le condizioni
		//-verifico tutte le condizioni
		
		try
		{
			LookupList stati = new LookupList( db, "m_stati_lookup" );
			int i = stati.getSelectSize();
			for( int j = (i - 1); (j >= 0) && (ret < 0); j-- )
			{
				LookupElement el = (LookupElement) stati.get( j );
				if( el.isEnabled() )
				{
					int id_stato = el.getCode();
					Vector<Condizione> cond = Condizione.load( id_stato, db );
					boolean ok = true;
					for( int k = 0; (k < cond.size()) && ok; k++ )
					{
						ok = verifyCondition( capo, cond.elementAt( k ), db );
					}
					
					if( ok )
					{
						ret = id_stato;
					}
				}
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	private static boolean verifyCondition( Capo capo, Condizione cond, Connection db )
	{
		boolean ret = false;
		
		String sql = "SELECT 'TEST CONDITION' FROM " + cond.getTabelle() + " WHERE " + cond.getCampi() + " " + cond.getCondizioni();
		
		try
		{
			PreparedStatement stat = db.prepareStatement( sql.replaceAll( "{id_capo}", capo.getId() + "" ) );
			ResultSet res = stat.executeQuery();
			if( res.next() )
			{
				ret = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}


	public static boolean canPerform( ActionContext context, Capo capo, String action  )
	{
		boolean ret = false;

		ret = verifyPermission( context, action );
		ConnectionElement ce		= null;
		ConnectionPool sqlDriver	= null;
		Connection db				= null;
		
		if( ret )
		{
			Azione az = null;
			try
			{
				ce				= (ConnectionElement) context.getSession().getAttribute( "ConnectionElement" );
				sqlDriver		= (ConnectionPool) context.getServletContext().getAttribute( "ConnectionPool" );
				db				= sqlDriver.getConnection(ce,context);
				int	id_stato	= getStato( capo, db );
				az				= Azione.load( action, id_stato, db );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				sqlDriver.free(db,context);
			}
			
			ret = (az == null) ? (false) : (true);
		}
		
		return ret;
	}


	private static boolean verifyPermission(ActionContext context, String action)
	{
		boolean ret = false;
		
		UserBean utente = (UserBean) context.getSession().getAttribute("User");
		
		if (utente != null)
		{
			ConnectionElement ce = utente.getConnectionElement();
			SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
				"SystemStatus")).get(ce.getUrl());
			ret = systemStatus.hasPermission( utente.getRoleId(), action );
		}
		
		return ret;
	}
	
	/**
	 * Metodo che dai file della bdb (capi_bovini_asl....) ricava un file di insert per la tabella m_capi_bdn
	 * @param sourceFile
	 * @param destFile
	 */
//	public static void getInsertFile( String sourceFile, String destFile )
//	{
//		
//		try
//		{
//			FileOutputStream fos = new FileOutputStream( destFile );
//			BufferedReader br = new BufferedReader( new FileReader(sourceFile) );
//			
//			Colonna[] c = 
//			{
//					new Colonna( 4,  "codice_usl" ),
//					new Colonna( 2,  "codice_distretto" ),
//					new Colonna( 13, "id_allevamento" ),
//					new Colonna( 8,  "data_estrazione_dati" ),
//					new Colonna( 1,  "inserimento_o_variazione" ),
//					new Colonna( 13, "codice_interno" ),
//					new Colonna( 14, "codice_identificativo_capo" ),
//					new Colonna( 8,  "codice_azienda" ),
//					new Colonna( 16, "identificativo_fiscale_allevamento" ),
//					new Colonna( 4,  "specie_allevata" ),
//					new Colonna( 1,  "flag_inseminazione" ),
//					new Colonna( 14, "codice_marchio_madre" ),
//					new Colonna( 14, "codice_assegnato_in_precedenza" ),
//					new Colonna( 16, "codice_elettronico" ),
//					new Colonna( 4,  "razza_capo" ),
//					new Colonna( 1,  "sesso_capo" ),
//					new Colonna( 8,  "data_nascita_capo" ),
//					new Colonna( 8,  "data_ingresso_stalla" ),
//					new Colonna( 8,  "data_applicazione_marca" ),
//					new Colonna( 8,  "data_iscrizione_anagrafe" ),
//					new Colonna( 1,  "origine_animale" ),
//					new Colonna( 2,  "paese_provenienza" ),
//					new Colonna( 3,  "codice_libro_genealogico" ),
//					new Colonna( 8,  "data_ultimo_aggiornamento" ),
//					new Colonna( 21, "numero_certificato_sanitario" ),
//					new Colonna( 20, "numero_riferimento_locale" ),
//					new Colonna( 10, "filler" )
//			};
//			
//			CsvLunghezzaFissa clf = new CsvLunghezzaFissa( c );
//			SimpleDateFormat sdf1 = new SimpleDateFormat( "ddMMyyyy" );
//			SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
//			
//			String temp = br.readLine();
//			while( temp != null && temp.trim().length() > 0 )
//			{
//				clf.setRiga( temp );
//				/*
//				 * 
//				 * 	matricola VARCHAR( 64 ),
//					codice_azienda VARCHAR( 64 ),
//					data_nascita TIMESTAMP,
//					razza VARCHAR( 64 ),
//					maschio BOOLEAN,
//					specie
//				 */
//				
//				String matricola		= clf.get( "codice_identificativo_capo" );
//				String codice_azienda	= clf.get( "codice_azienda" );
//				String data_nascita		= clf.get( "data_nascita_capo" );
//				String razza			= clf.get( "razza_capo" );
//				String sesso			= clf.get( "sesso_capo" );
//				String specie			= clf.get( "specie_allevata" );
//				String maschio = ("M".equalsIgnoreCase(sesso)) ? ("TRUE") : ("FALSE");
//				
//				String sql = "INSERT INTO m_capi_bdn( matricola, codice_azienda, data_nascita, razza, maschio, specie )" +
//						"VALUES( '" + matricola + "', '" + codice_azienda + "', '" + sdf2.format( sdf1.parse(data_nascita) ) + "', '" +
//						getCodiceRazza( razza ) + "', " + maschio + ", '" + getCodiceSpecie( specie ) + "' );\n";
//				
//				
//				fos.write( sql.getBytes() );
//				
//				temp = br.readLine();
//			}
//			
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args)
	{
		String fileIn = "D:\\file_DATI\\allevamenti\\CAPI_BOVINI_ASLR113_2008_11_17\\CAPI_BOVINI_ASLR113_2008_11_17.lst";
		String fileOut = "C:\\out_capi_bdn.csv";
		
		startupRazze();
		startuoSpecie();
		//getInsertFile( fileIn, fileOut );
		
	}
	
	private static int getCodiceRazza( String text_code )
	{
		int ret = -1;
		
		if( text_code != null )
		{
			ret = razze.get( text_code.trim() );
		}
		
		return ret;
	}
	
	private static int getCodiceSpecie( String text_code )
	{
		int ret = -1;
		
		if( text_code != null )
		{
			ret = specie.get( text_code.trim() );
		}
		
		return ret;
	}
	
	private static void startuoSpecie()
	{
		specie.put( "0121", 1 );
		specie.put( "0122", 2 );
		specie.put( "0124", 3 );
		specie.put( "0126", 4 );
		specie.put( "0129", 5 );
		specie.put( "0125", 6 );
		specie.put( "0128", 7 );
		specie.put( "0130", 8 );
		specie.put( "0132", 9 );
		specie.put( "0133", 10 );
		specie.put( "0134", 11 );
		specie.put( "0135", 12 );
		specie.put( "0131", 13 );
		specie.put( "0136", 14 );
		specie.put( "0137", 15 );
		specie.put( "0138", 16 );
		specie.put( "0139", 17 );
		specie.put( "0140", 18 );
		specie.put( "0141", 19 );
		specie.put( "0142", 20 );
		specie.put( "0143", 21 );
		specie.put( "0146", 22 );
		specie.put( "0170", 23 );
		specie.put( "0148", 24 );
		specie.put( "0149", 25 );
		specie.put( "0147", 26 );
		specie.put( "0150", 27 );
		specie.put( "0151", 28 );
		specie.put( "0152", 29 );
		specie.put( "0160", 30 );
		specie.put( "0144", 31 );

	}
	
	private static void startupRazze()
	{
		razze.put( "ABO", 1 );
		razze.put( "AGE", 2 );
		razze.put( "ALT", 3 );
		razze.put( "LPG", 4 );
		razze.put( "LTM", 5 );
		razze.put( "ANG", 6 );
		razze.put( "AAG", 7 );
		razze.put( "APN", 8 );
		razze.put( "ARM", 9 );
		razze.put( "ATR", 10 );
		razze.put( "ABC", 11 );
		razze.put( "AVI", 12 );
		razze.put( "AWS", 13 );
		razze.put( "AYR", 14 );
		razze.put( "BGN", 15 );
		razze.put( "BRB", 16 );
		razze.put( "BZD", 17 );
		razze.put( "BRE", 18 );
		razze.put( "BGW", 19 );
		razze.put( "BGS", 20 );
		razze.put( "BRH", 21 );
		razze.put( "BVP", 22 );
		razze.put( "BLS", 23 );
		razze.put( "BSN", 24 );
		razze.put( "BKO", 25 );
		razze.put( "BAQ", 26 );
		razze.put( "BBL", 27 );
		razze.put( "BLT", 28 );
		razze.put( "BRM", 29 );
		razze.put( "BRN", 30 );
		razze.put( "BRT", 31 );
		razze.put( "BRZ", 32 );
		razze.put( "BRG", 33 );
		razze.put( "BRO", 34 );
		razze.put( "BSW", 35 );
		razze.put( "BRN", 36 );
		razze.put( "BAA", 37 );
		razze.put( "BNP", 38 );
		razze.put( "BFL", 39 );
		razze.put( "BUR", 40 );
		razze.put( "CAB", 41 );
		razze.put( "CAL", 42 );
		razze.put( "CMG", 43 );
		razze.put( "CMR", 44 );
		razze.put( "CHL", 45 );
		razze.put( "CHT", 46 );
		razze.put( "CIA", 47 );
		razze.put( "CVN", 48 );
		razze.put( "CIN", 49 );
		razze.put( "CLF", 50 );
		razze.put( "CMS", 51 );
		razze.put( "CRN", 52 );
		razze.put( "CRG", 53 );
		razze.put( "CZP", 54 );
		razze.put( "DRW", 55 );
		razze.put( "DRT", 56 );
		razze.put( "DLG", 57 );
		razze.put( "DEV", 58 );
		razze.put( "DXT", 59 );
		razze.put( "BNV", 60 );
		razze.put( "DRP", 61 );
		razze.put( "DRH", 62 );
		razze.put( "FBN", 63 );
		razze.put( "FNR", 64 );
		razze.put( "FNN", 65 );
		razze.put( "FRB", 66 );
		razze.put( "FRS", 67 );
		razze.put( "FRN", 68 );
		razze.put( "HST", 69 );
		razze.put( "BTF", 70 );
		razze.put( "FFR", 71 );
		razze.put( "FIT", 72 );
		razze.put( "NZF", 73 );
		razze.put( "FHL", 74 );
		razze.put( "FRB", 75 );
		razze.put( "GLW", 76 );
		razze.put( "GRS", 77 );
		razze.put( "GAF", 78 );
		razze.put( "GRG", 79 );
		razze.put( "GDP", 80 );
		razze.put( "GRL", 81 );
		razze.put( "GRA", 82 );
		razze.put( "GRD", 83 );
		razze.put( "GCN", 84 );
		razze.put( "GUS", 85 );
		razze.put( "HEF", 86 );
		razze.put( "HER", 87 );
		razze.put( "HLA", 88 );
		razze.put( "ICD", 89 );
		razze.put( "LDF", 90 );
		razze.put( "ITS", 91 );
		razze.put( "JES", 92 );
		razze.put( "KRL", 93 );
		razze.put( "KBL", 94 );
		razze.put( "KRH", 95 );
		razze.put( "LCN", 96 );
		razze.put( "LMN", 97 );
		razze.put( "LAN", 98 );
		razze.put( "LTD", 99 );
		razze.put( "LCS", 100 );
		razze.put( "LNK", 101 );
		razze.put( "MRR", 102 );
		razze.put( "MSS", 103 );
		razze.put( "MTS", 104 );
		razze.put( "MRN", 105 );
		razze.put( "MRZ", 106 );
		razze.put( "MRA", 107 );
		razze.put( "MTT", 108 );
		razze.put( "NND", 109 );
		razze.put( "NST", 110 );
		razze.put( "NTC", 111 );
		razze.put( "PGL", 112 );
		razze.put( "PCC", 113 );
		razze.put( "POL", 114 );
		razze.put( "PNZ", 115 );
		razze.put( "PLZ", 116 );
		razze.put( "PMR", 117 );
		razze.put( "PST", 118 );
		razze.put( "RMV", 119 );
		razze.put( "CAS", 120 );
	}
	
	private static Hashtable<String, Integer> razze		= new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> specie	= new Hashtable<String, Integer>();
	
}
