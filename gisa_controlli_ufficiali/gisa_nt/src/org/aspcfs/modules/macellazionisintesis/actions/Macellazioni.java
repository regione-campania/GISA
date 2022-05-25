/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionisintesis.actions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazionisintesis.base.LogCancellazioneCapiPartite;
import org.aspcfs.modules.macellazioninewsintesis.base.Tipo;
import org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo;
import org.aspcfs.modules.macellazionisintesis.base.Campione;
import org.aspcfs.modules.macellazionisintesis.base.Capo;
import org.aspcfs.modules.macellazionisintesis.base.CapoAjax;
import org.aspcfs.modules.macellazionisintesis.base.CapoLog;
import org.aspcfs.modules.macellazionisintesis.base.CapoLogDao;
import org.aspcfs.modules.macellazionisintesis.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazionisintesis.base.NonConformita;
import org.aspcfs.modules.macellazionisintesis.base.Organi;
import org.aspcfs.modules.macellazionisintesis.base.PatologiaRilevata;
import org.aspcfs.modules.macellazionisintesis.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazionisintesis.base.RegistroTumoriRemoteUtil;
import org.aspcfs.modules.macellazionisintesis.base.RichiestaIstopatologico;
import org.aspcfs.modules.macellazionisintesis.base.Tampone;
import org.aspcfs.modules.macellazionisintesis.base.TipoRicerca;
import org.aspcfs.modules.macellazionisintesis.utils.MacelliUtil;
import org.aspcfs.modules.sintesis.base.SintesisStabilimento;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.NumberFilterMatcher;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.editor.NumberCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webutils.FileDownload;

public final class Macellazioni extends CFSModule
{
	private static boolean enabledIstopatologico = false;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private static SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
	Logger logger = Logger.getLogger("MainLogger");
	
	ConfigTipo configTipo = null;
	
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandList( context );
	}
	
	
	public String executeCommandSearch( ActionContext context )
	{
		
		if (context.getParameter("matricola")!=null && ! "".equals(context.getParameter("matricola")))
		{
			
			
			Connection db = null;
			try
			{
				db		= this.getConnection( context );
				Capo c	= Capo.loadByMatricola( context.getParameter( "matricola" ), db );
				
				if (c!=null && c.getId()>0)
				{
					context.getRequest().setAttribute("altId",c.getId_macello()+"" );
				SintesisStabilimento org = new SintesisStabilimento( db, c.getId_macello(), true );

					caricaLookup(context, false);
				context.getRequest().setAttribute( "Capo", c );
				context.getRequest().setAttribute( "OrgDetails", org );

				
				
				return "SearchMatricolaResultOK" ;
				}

			}
			catch (Exception e1)
			{
				context.getRequest().setAttribute("Error", e1);
				e1.printStackTrace();
			} 
			finally
			{
				this.freeConnection(context, db);
			}
			
		}
		
		return "SearchMatricolaOK" ;
	}

	public String executeCommandToStampeModuli( ActionContext context ) throws NumberFormatException
	{

		if (!hasPermission(context, "stabilimenti-stabilimenti-stampe-moduli-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		
		int idMacello = Integer.parseInt(context.getRequest().getParameter("altId"));
		try {
			db = this.getConnection(context);

			HashMap<String, ArrayList<String>> stampe_date = new HashMap<String, ArrayList<String>>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Iterator<Object> key = org.aspcfs.modules.macellazionisintesis.actions.ApplicationProperties.getApplicationProperties().keySet().iterator();
			while (key.hasNext())
			{
				String kiave = (String) key.next();
				if(kiave.startsWith("GET_DATE"))
				{
					String select1 = org.aspcfs.modules.macellazionisintesis.actions.ApplicationProperties.getProperty(kiave);
					PreparedStatement stat1 = db.prepareStatement(select1);
					stat1.setInt( 1,  idMacello);
					ResultSet res1 = stat1.executeQuery();
					ArrayList<String> listadate= new ArrayList<String>();
					
					while(res1.next()){
						Timestamp thisdata = res1.getTimestamp(1);
						if(thisdata!=null)
						{
							listadate.add(sdf.format(new Date(thisdata.getTime())));
						}
					}
					stampe_date.put(kiave, listadate);
					
				}
			}
			context.getRequest().setAttribute( "DateStampa", stampe_date );


			
			SintesisStabilimento macello = new SintesisStabilimento( db, Integer.parseInt( context.getParameter( "altId" ) ), true );
			context.getRequest().setAttribute( "OrgDetails", macello );
			context.getRequest().setAttribute("altId", context.getParameter("altId"));

		} catch (SQLException ex){
 			ex.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		


		return "ToStampeModuliOK";
	}
	
	public String executeCommandListTipi( ActionContext context )
	{
		String			ret		= "ListTipiOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection		db		= null;

		try
		{
			db = this.getConnection( context );

			SintesisStabilimento org = new SintesisStabilimento( db, Integer.parseInt(context.getRequest().getParameter("altId")), true );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			ArrayList<Tipo> tipi = new ArrayList<Tipo>();
		
			tipi = Tipo.loadAll( db );
			context.getRequest().setAttribute( "tipi", tipi );
			
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		return ret;
	}

	public String executeCommandToLiberoConsumo( ActionContext context )
	{
		String ret = "ToLiberoConsumoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			Capo c	= loadCapo( context );
			context.getSession().setAttribute( "Capo", c );
			Tampone 			tampone 			= Tampone.load			( c.getId()+"", db );
			context.getRequest().setAttribute("Tampone", tampone);
			caricaLookup( context );
			
			String vpmData = stringa	( "vpmData", context );
			context.getRequest().setAttribute( "vpmData", vpmData );
			String mavamData = stringa	( "mavamData", context );
			context.getRequest().setAttribute( "mavamData", mavamData );
			String cdSedutaMacellazione = stringa	( "cdSedutaMacellazione", context );
			context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
				context.getRequest().setAttribute( "sessioneCorrente", vpmData + "-" + cdSedutaMacellazione );
			else
				context.getRequest().setAttribute( "sessioneCorrente", mavamData + "-" + cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
				context.getRequest().setAttribute( "comboSessioniMacellazione", vpmData + "-" + cdSedutaMacellazione  );
			else
				context.getRequest().setAttribute( "comboSessioniMacellazione", mavamData + "-" + cdSedutaMacellazione  );
			
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		return ret;
	}

	public String executeCommandDetails( ActionContext context )
	{
		String ret = "DetailsOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			Capo c	= Capo.load( context.getParameter( "id" ), db );
			SintesisStabilimento org = new SintesisStabilimento ( db, c.getId_macello(), true );

			ArrayList<NonConformita>		ncsVAM				= NonConformita.load	( c.getId(), db );
			ArrayList<Campione> 			campioni 			= Campione.load			( c.getId(), db );
			Tampone 			tampone 			= Tampone.load			( c, db );
			ArrayList<Organi>   			organi		 		= Organi.loadByOrgani	( c.getId(), db );
			TreeMap<Integer, ArrayList<Organi>> organiNew = new TreeMap<Integer, ArrayList<Organi>>();
			for(Organi o : organi){
				if(organiNew.containsKey(o.getLcso_organo())){
					organiNew.get(o.getLcso_organo()).add(o);
				}
				else{
					ArrayList<Organi> organiList = new ArrayList<Organi>();
					organiList.add(o);
					organiNew.put(o.getLcso_organo(), organiList);
				}
			}

			ArrayList<PatologiaRilevata>	patologieRilevate	= PatologiaRilevata.load( c.getId(), db );
			ArrayList<ProvvedimentiCASL>	provvedimenti		= ProvvedimentiCASL.load( c.getId(), db );
			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NC		= Casl_Non_Conformita_Rilevata.load( c.getId(), db );

			context.getRequest().setAttribute( "Capo", c );
			context.getRequest().setAttribute( "OrgDetails", org );

			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "Tampone", tampone );

			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );

			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
			context.getRequest().setAttribute( "casl_NCRilevate", casl_NC );
			
			boolean sessionePregressa = false;
			sessionePregressa = MacelliUtil.isSessionePregressa(c.getId_macello(), c.getVpm_data(), c.getCd_seduta_macellazione(), db);
			if (sessionePregressa)
				context.getRequest().setAttribute("sessionePregressa", "SESSIONE PREGRESSA");

		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		caricaLookup(context, false);

		return ret;
	}

	public class AvailableDroplistFilterEditor extends DroplistFilterEditor {
		@Override
		protected List<Option> getOptions()  {
			List<Option> options = new ArrayList<Option>();
			options.add(new Option("Incompleto","Incompleto"));
			options.add(new Option("OK", "OK"));
			return options;
		}
	}

	public class AvailableFilterMatcher implements FilterMatcher {
		public boolean evaluate(Object itemValue, String filterValue) {

			String item = String.valueOf(itemValue);
			String filter = String.valueOf(filterValue);

			if ((filter.equals("Incompleto") && item.equals("Incompleto")) ||
					(filter.equals("OK") && item.equals("OK"))) {
				return true;
			}

			return false;
		}
	}
	/*d.zanfardino
	 * Aggiunta di una seduta di macellazione per il giorno scelto
	*/
	public String executeCommandAddSedutaMacellazione( ActionContext context) {
		Connection	db = null;

		try
		{
			db = this.getConnection( context );
			String id = (String) context.getRequest().getParameter("altId");
			int altId = new Integer(id);
			String sessioneMacellazioneString = context.getParameter("comboSessioniMacellazione");
			
			Timestamp data = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				data = new Timestamp( sdf.parse( sessioneMacellazioneString ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			}
			
			// SELEZIONO L'ULTIMA SEDUTA
			String selectMax = "SELECT MAX(numero) from m_capi_sedute where id_macello = ?  AND data = ?";
			PreparedStatement stat = db.prepareStatement( selectMax );
			stat.setInt( 1, altId );
			stat.setTimestamp( 2, data );
			
			ResultSet rs = stat.executeQuery();
			int maxSedutaMacellazione = 0;
			while (rs.next()) {
	            maxSedutaMacellazione = rs.getInt(1);
			}
			
			if (maxSedutaMacellazione>=3) {
				String sessioneCorrente =  sessioneMacellazioneString  + "-" + (maxSedutaMacellazione);
				context.getRequest().setAttribute( "sessioneCorrente", sessioneCorrente );
				
				context.getRequest().setAttribute( "messaggio", "Impossibile aggiungere una seduta a questa data. Numero massimo di sedute consentite raggiunto." );
				context.getRequest().setAttribute("comboSessioniMacellazione", sessioneCorrente);
				
			} else {
				// SELEZIONO SE E' SEDUTA PREGRESSA
				String selectPregressa = "SELECT seduta_pregressa from m_capi_sedute where id_macello = ?  AND data = ? and numero = 1";
				PreparedStatement stat2 = db.prepareStatement( selectPregressa );
				stat2.setInt( 1, altId );
				stat2.setTimestamp( 2, data );
				
				ResultSet rs2 = stat2.executeQuery();
				boolean sedutaPregressa = false;
				while (rs2.next()) {
					sedutaPregressa = rs2.getBoolean(1);
				}
				
				//System.out.println("Attuale Seduta di Macellazione: "+maxSedutaMacellazione);
				
				String queryInsert = "insert into m_capi_sedute(numero,id_macello,data, seduta_pregressa) values(?,?,?, ?)";
				stat = db.prepareStatement( queryInsert );
				stat.setInt( 1, maxSedutaMacellazione+1 );
				stat.setInt(2, altId );
				stat.setTimestamp( 3, data );
				stat.setBoolean(4,  sedutaPregressa);
				stat.execute();
				
				String queryUpdateTamponi = "UPDATE m_vpm_tamponi SET sessione_macellazione = ? " +
				"WHERE id_macello = ? AND data_macellazione = ? AND (sessione_macellazione =0 OR sessione_macellazione <=0)";
				stat = db.prepareStatement( queryUpdateTamponi );
				stat.setInt( 1, maxSedutaMacellazione+1 );
				stat.setInt(2, altId );
				stat.setTimestamp( 3, data );
				stat.execute();
				
				String sessioneCorrente =  sessioneMacellazioneString  + "-" + (maxSedutaMacellazione+1);
				context.getRequest().setAttribute( "sessioneCorrente", sessioneCorrente );
				
				context.getRequest().setAttribute( "messaggio", "Sessione " + sessioneCorrente + " aggiunta" );
				context.getRequest().setAttribute("comboSessioniMacellazione", sessioneCorrente);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return executeCommandList( context );
	
	}
	
	
	public String executeCommandAddSedutaMacellazioneNuovaData( ActionContext context) {
		Connection	db = null;

		try
		{
			db = this.getConnection( context );
			String id = (String) context.getRequest().getParameter("altId");
			int altId = new Integer(id);
			String dataNuovaSessioneString = context.getParameter("dataNuovaSessione");
			
			String sedutaPregressaString = (String) context.getRequest().getParameter("seduta_pregressa");
			boolean sedutaPregressa = false;
			String sessioneCorrentePregressa="";
			if (sedutaPregressaString!=null && sedutaPregressaString.equals("on")){
				sedutaPregressa = true;
				sessioneCorrentePregressa=" PREGRESSA ";
			}
			
			Timestamp data = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				data = new Timestamp( sdf.parse( dataNuovaSessioneString ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			}
			
			
			
			String queryInsert = "insert into m_capi_sedute(id_macello,data,seduta_pregressa,numero) values(?,?,?,1) ";
			PreparedStatement stat = db.prepareStatement( queryInsert );
			stat.setInt(1, altId );
			stat.setTimestamp( 2, data );
			stat.setBoolean( 3, sedutaPregressa );
			stat.execute();
			
			String sessioneCorrente =  dataNuovaSessioneString  + "-1";
			context.getRequest().setAttribute( "sessioneCorrente", sessioneCorrente );
			context.getRequest().setAttribute( "sessioneCorrente", sessioneCorrentePregressa );
			context.getRequest().setAttribute("comboSessioniMacellazione", sessioneCorrente);
			
			context.getRequest().setAttribute( "messaggio", "Sessione " + sessioneCorrente + " aggiunta" );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return executeCommandList( context );
	
	}
	
	
	public String executeCommandList( ActionContext context )
	{
		
		int altId = -1;
		int tipoInt = -1;
		
		String altIdString = context.getParameter("altId");
		String tipoString = context.getParameter("tipo"); 	
		
		if (altIdString==null)
			altIdString = (String) context.getRequest().getAttribute("altId");
		if (tipoString==null)
			tipoString = (String) context.getRequest().getAttribute("tipo");
		
		try {tipoInt= Integer.parseInt(tipoString);} catch (Exception e){}
		try {altId= Integer.parseInt(altIdString);} catch (Exception e){}
		
		
		if (tipoInt>-1){
			final int tipo = tipoInt;
			context.getSession().setAttribute("configTipo", new ConfigTipo(tipo));
		}
		
		String			ret		= "HomeOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection		db		= null;
		

		try
		{
			ArrayList<Capo> capi = new ArrayList<Capo>();
			db = this.getConnection( context );
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			String sessioneMacellazioneString = null;
			sessioneMacellazioneString = (String)context.getRequest().getAttribute("comboSessioniMacellazione");
			if(sessioneMacellazioneString==null || sessioneMacellazioneString.equals("") || sessioneMacellazioneString.equals("null"))
				sessioneMacellazioneString = context.getParameter("comboSessioniMacellazione");
			
			ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneUltimaSessPopolata(altIdString, db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );
			
			//Recupero sessioni di macellazioni per riempire la combo
			ArrayList<String> listaSessioniMacellazione = Capo.loadSessioniMacellazioneByStabilimento( altIdString , db );
			context.getRequest().setAttribute( "listaSessioniMacellazione", listaSessioniMacellazione );
			
			//Lista completa per controlli su pregresso
			ArrayList<String> listaSessioniMacellazioneCompleta = Capo.loadSessioniMacellazioneByStabilimentoCompleta( altIdString, db );
			context.getRequest().setAttribute( "listaSessioniMacellazioneCompleta", listaSessioniMacellazioneCompleta );
			
			if((listaSessioniMacellazione!=null && !listaSessioniMacellazione.isEmpty()) && (sessioneMacellazioneString==null || sessioneMacellazioneString.equals("") || sessioneMacellazioneString.equals("null")))
				sessioneMacellazioneString = listaSessioniMacellazione.get(0);
				
			if(sessioneMacellazioneString != null && !sessioneMacellazioneString.equals(""))
			{
				if(!sessioneMacellazioneString.equals("-1"))
				{
					Timestamp dataMacellazioneTimestamp = new Timestamp( sdf.parse(sessioneMacellazioneString.split("-")[0]).getTime() );
					Integer numSessioneMacellazione = 0;
					if(sessioneMacellazioneString.split("-").length>1)
						numSessioneMacellazione = Integer.parseInt(sessioneMacellazioneString.split("-")[1]);
					capi = Capo.loadByStabilimentoPerSessioneMacellazione( altIdString,dataMacellazioneTimestamp, numSessioneMacellazione, db );
				}
				else{
					capi = Capo.loadByStabilimentoCapiNonMacellati( altIdString, db );
					
				}
			}
			else
			{
				if (altIdString!=null)
					capi = Capo.loadByStabilimento( altIdString, db );
				else
					if(context.getRequest().getAttribute("AltId")!=null)
						capi = Capo.loadByStabilimento( context.getRequest().getAttribute("AltId")+"", db );
			}

 
			TableFacade tf = TableFacadeFactory.createTableFacade( "7", context.getRequest() );

			tf.addFilterMatcher(new MatcherKey(Integer.class, "progressivo_macellazione"), new NumberFilterMatcher("##############00000000000000"));
			tf.addFilterMatcher(new MatcherKey(Integer.class, "cd_categoria_rischio"), new NumberFilterMatcher("##############00000000000000"));
			
			tf.addFilterMatcher(new MatcherKey(Timestamp.class, "vpm_data"), new DateFilterMatcher("dd/MM/yyyy"));

			tf.setItems( capi );
			//tf.setColumnProperties( "cd_matricola", "stato_macellazione", "cd_codice_azienda", "cd_data_nascita", "vpm_data", "progressivo_macellazione", "entered"  );
			if (enabledIstopatologico){
				tf.setColumnProperties( "cd_matricola", "stato_macellazione", "cd_codice_azienda_provenienza", "cd_data_nascita","progressivo_macellazione", "cd_categoria_rischio","vpm_data", "articolo17", "clona"
					, "istopatologico" 
					);
			}
			else{
				tf.setColumnProperties( "cd_matricola", "stato_macellazione", "cd_codice_azienda_provenienza", "cd_data_nascita","progressivo_macellazione", "cd_categoria_rischio","vpm_data", "articolo17", "clona"
						//, "istopatologico" 
						);
			}
			tf.setStateAttr("restore");


			tf.getTable().getRow().getColumn( "stato_macellazione" ).setTitle( "Stato Macellazione" );
			tf.getTable().getRow().getColumn( "cd_matricola" ).setTitle( "Matricola" );
			tf.getTable().getRow().getColumn( "cd_codice_azienda_provenienza" ).setTitle( "Codice Azienda" );
			tf.getTable().getRow().getColumn( "cd_data_nascita" ).setTitle( "Data Nascita" );
			tf.getTable().getRow().getColumn( "cd_categoria_rischio" ).setTitle( "Categoria Rischio" );
			tf.getTable().getRow().getColumn( "vpm_data" ).setTitle( "Data Macellazione" );
			tf.getTable().getRow().getColumn( "progressivo_macellazione" ).setTitle( "Progressivo Macellazione" );
			tf.getTable().getRow().getColumn( "articolo17" ).setTitle( "Stampato Art. 17" );
			if (enabledIstopatologico){
				tf.getTable().getRow().getColumn( "istopatologico" ).setTitle( "Richiesta istopatologico" );
			}
			//tf.getTable().getRow().getColumn( "entered" ).setTitle( "Data Inserimento" );


			
			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				tf.render();
				return null;
			}
			else
			{
				HtmlColumn cg;

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("stato_macellazione");
				cg.setFilterable( true );		
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());


				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String colore	= ((Capo)item).color;
								
								String id	= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								String idImportString	= (String) (new HtmlCellEditor()).getValue(item, "id_import", rowCount);
								int idImport = -1;
								if (idImportString!=null && !idImportString.equals("") && !idImportString.equals("null")){
									try { idImport = Integer.parseInt(idImportString);} catch (Exception e ){}
								}
								
								
								String dataMac	= (String) (new HtmlCellEditor()).getValue(item, "vpm_data", rowCount);
								
								String vaiLiberoConsumo ="";
							
								if (temp.toLowerCase().contains("incompleto") && idImport >0 )
									vaiLiberoConsumo ="<input type=\"button\" value=\"PROCESSA LIBERO CONSUMO\" onClick=\" liberoConsumoVeloce('"+id+"', '"+dataMac+"'); \"/>";
								
								return "<div style=\"background-color:" + colore + "\">" + temp + "</div>" + vaiLiberoConsumo;
							}
						}

				);

				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("articolo17");
				cg.setFilterable( false );		
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String toReturn = "";
								String art17	= (String) (new HtmlCellEditor()).getValue(item, "articolo17", rowCount);
								
								if(art17.equals("true"))
									return "<div style=\"background-color:#00FF00 \">" + "SI" + "</div> ";
								else
									return "<div style=\"background-color:red \">" + "NO" + "</div> ";
								
							}
						});
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cd_matricola");
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
								String colore	= ((Capo)item).color;

								temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
								//return "<div style=\"background-color:" + colore + "\"><a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
								return "<a href=\"MacellazioniSintesis.do?command=Details&id=" + id + "&altId=" + id_mac + "\">" + temp + "</a>";
								//return temp;


							}
						}

				);

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("clona");
				cg.setFilterable( false );	
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
								String dataMac	= (String) (new HtmlCellEditor()).getValue(item, "vpm_data", rowCount);
								String seduta	= (String) (new HtmlCellEditor()).getValue(item, "cd_seduta_macellazione", rowCount);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
								Date dataMac2 = null;
								try
								{
									dataMac2 = sdf.parse(dataMac);
								}
								catch(Exception e)
								{
									
								}
								
								String dataMac3	= (String) (new HtmlCellEditor()).getValue(item, "mavam_data", rowCount);
								Date dataMac4 = null;
								try
								{
									dataMac4 = sdf.parse(dataMac3);
								}
								catch(Exception e)
								{
									
								}
								String mavamData = "";
								if(dataMac4!=null)
									mavamData =  sdf2.format(dataMac4);
								String vpmData =  "";
								if(dataMac2!=null)
									vpmData =  sdf2.format(dataMac2);
								//return "<div style=\"background-color:" + colore + "\"><a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
								return "<center><a href=\"MacellazioniSintesis.do?command=NuovoCapo&cdSedutaMacellazione=" + seduta + "&mavamData=" + mavamData + "&vpmData=" + vpmData + "&id=" + id + "&altId=" + id_mac + "&clona=si \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Clona capo\" /></a></center>";
								//return temp;


							}
						}

				);
				
				if (enabledIstopatologico){
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("istopatologico"); //COMMENTARE/DECOMMENTARE DA QUA
				cg.setFilterable( false );	
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
							
								
							String toReturn = "";
								
							if (hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))	{
							
							String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
							String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
							String istopatologico = (String) (new HtmlCellEditor()).getValue(item, "istopatologico_richiesta", rowCount);
							String istopatologico_id = (String) (new HtmlCellEditor()).getValue(item, "istopatologico_id", rowCount);
							boolean istopatologicoEseguito = new Boolean(istopatologico).booleanValue();
							
						//    ArrayList<Organi>   			organi		 		= Organi.loadByOrgani	(Integer.parseInt(id), configTipo );
							
							Connection db = null;
							try {
								db = GestoreConnessioni.getConnection();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							ArrayList<Organi>   			organiTumorali		 		= Capo.checkOrganiTumorali(db, Integer.parseInt(id))	;
						    RichiestaIstopatologico richiesteIstopatologico = new RichiestaIstopatologico();
						    if (istopatologico_id != null && !("").equals(istopatologico_id)){
								try {
									richiesteIstopatologico =	RichiestaIstopatologico.load(Integer.valueOf(istopatologico_id), null);
					
							if (organiTumorali != null && organiTumorali.size() > 0)	{
								//Iterator i = organi.iterator();
								
//								while (i.hasNext()){
//									Organi thisOrgano = (Organi) i.next();
									
									if (!istopatologicoEseguito){
								//return "<div style=\"background-color:" + colore + "\"><a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
										toReturn += "<center><a href=\"MacellazioniSintesis.do?command=RichiestaIstopatologico&id=" + id + "&altId=" + id_mac + " \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Richiedi istopatologico\" /></a></center>";
									
									}
									else if (istopatologicoEseguito) {
										toReturn += "<div>" + "<a href=\"GestioneDocumenti.do?command=GeneraPDFInvioCampioniIstopatologico&idIstopatologico=" + istopatologico_id + " \">Stampa Modello Invio Campioni</a>" + "</div> ";
									if  (richiesteIstopatologico.getIdEsito() > 0 )
										toReturn += "<div>" + "<a onclick=\"window.open('Macellazioni.do?command=VisualizzaEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Visualizza esito</a>" + "</div> ";
									 if (richiesteIstopatologico.getIdEsito() <= 0)									
										toReturn += "<div>" + "<a onclick=\"window.open('Macellazioni.do?command=PrepareInserisciEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Inserisci esito</a>" + "</div> ";
									
									}
							//	}
								if (("").equals(toReturn) )
								toReturn  = "NON PREVISTO";

							}else toReturn  = "NON PREVISTO";
						//	}
						//}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							GestoreConnessioni.freeConnection(db);
						}
							 }
							
							}
							return toReturn;	}}

				);//A QUA
				}
				//cg = (HtmlColumn) tf.getTable().getRow().getColumn("cd_codice_azienda");
				//cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cd_data_nascita");
				cg.setFilterable( false );
				cg.getCellRenderer().setCellEditor(new DateCellEditor("dd/MM/yyyy") );


				cg = (HtmlColumn) tf.getTable().getRow().getColumn("vpm_data");
				cg.setFilterable( true );
				cg.getCellRenderer().setCellEditor( 
						new DateCellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
								String stato	= (String) (new HtmlCellEditor()).getValue(item, "stato_macellazione", rowCount);
								String dataMac	= (String) (new HtmlCellEditor()).getValue(item, "vpm_data", rowCount);
								Date dataMac2 = null;
								try
								{
									dataMac2 = sdf.parse(dataMac);
								}
								catch(Exception e)
								{
									
								}
								String vpmData =  "";
								if(dataMac2!=null)
									vpmData =  sdf2.format(dataMac2);
								if(stato.equals("Incompleto: Inseriti solo i dati sul controllo documentale"))
									return "";
								else
									return vpmData;
							}


						}
				);


				cg = (HtmlColumn) tf.getTable().getRow().getColumn("progressivo_macellazione");
				cg.getCellRenderer().setCellEditor( 
						new NumberCellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String temp = (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								return "-1".equalsIgnoreCase(temp) ? ("") : (temp);
							}


						}
				);


				/*
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("vpm_data");
				cg.setFilterable( true );				
				cg.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				Timestamp tm = ((Capo) item).getVpm_data();
		        				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        				return sdf.format(tm);
		        			}
		        		}
		        	);		
				 */		

			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );

			//Recupero date di macellazione per riempire la combo
			/*ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneByStabilimento( context.getParameter( "orgId" ), db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );*/

			String dataSessione = "";
			String numeroSessione = "";
			if(sessioneMacellazioneString != null && !sessioneMacellazioneString.equals("") && !sessioneMacellazioneString.equals("-1") && sessioneMacellazioneString.split("-").length>1)
			{
				context.getRequest().setAttribute( "sessioneCorrente", sessioneMacellazioneString );
				context.getRequest().setAttribute( "comboSessioniMacellazione", sessioneMacellazioneString );
				String sessioni[] = sessioneMacellazioneString.split("-");
				dataSessione = sessioni[0];
				numeroSessione = sessioni[1];
			}
			else if(sessioneMacellazioneString != null && sessioneMacellazioneString.equals("-1") || sessioneMacellazioneString.split("-").length<=1)
			{
				context.getRequest().setAttribute( "comboSessioniMacellazione", "-1" );
				if(sessioneMacellazioneString!=null && !sessioneMacellazioneString.equals("") && !sessioneMacellazioneString.equals("-1"))
					context.getRequest().setAttribute( "comboSessioniMacellazioneData", sessioneMacellazioneString );
			}
			else if(listaSessioniMacellazione!=null && !listaSessioniMacellazione.isEmpty()){
				context.getRequest().setAttribute( "sessioneCorrente", listaSessioniMacellazione.get(0));
				String sessioni[] = listaSessioniMacellazione.get(0).split("-");
				dataSessione = sessioni[0];
				numeroSessione = sessioni[1];
			}
			
			
			Timestamp data = null;
			int numeroSeduta = -1;
			try {altId=Integer.parseInt(altIdString);} catch(Exception e){};
			try {
				Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataSessione);
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    String parsedDate = formatter.format(initDate);
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    java.util.Date date = sdf2.parse(parsedDate);
			    data = new java.sql.Timestamp(date.getTime());

				} catch(Exception e){};
			try {numeroSeduta=Integer.parseInt(numeroSessione);} catch(Exception e){};
			boolean sessionePregressa = false;
			if (altId>0 && numeroSeduta>0 && data!=null)
				sessionePregressa = MacelliUtil.isSessionePregressa(altId, data, numeroSeduta, db);
			if (sessionePregressa)
				context.getRequest().setAttribute("sessionePregressa", "SESSIONE PREGRESSA");
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		
		}

		caricaLookup( context );
		
		String messaggioImport = (String) context.getRequest().getAttribute("messaggioImport");
		context.getRequest().setAttribute("messaggioImport", messaggioImport);

		return ret;
	}


	public String executeCommandNuovoCapo( ActionContext context )
	{
		String		ret	= "NuovoCapoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		
		String vpmData = context.getParameter( "vpmData" );
		if(vpmData==null || vpmData.equals("") || vpmData.equals("null"))
			vpmData = (String)context.getRequest().getAttribute("vpmData");
		context.getRequest().setAttribute( "vpmData", vpmData);
		String mavamData = context.getParameter( "mavamData" );
		if(mavamData==null || mavamData.equals("") || mavamData.equals("null"))
			mavamData = (String)context.getRequest().getAttribute("mavamData");
		context.getRequest().setAttribute( "mavamData", mavamData);
		String cdSedutaMacellazione = context.getParameter( "cdSedutaMacellazione" );
		context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
		try
		{
			if( context.getRequest().getParameter("clona") != null && context.getRequest().getParameter("clona").equals("si") ){


				db		= this.getConnection( context );
				String idCapo = context.getParameter( "id" );
				
				if(idCapo == null && context.getRequest().getAttribute("idCapo") != null){
					idCapo = context.getRequest().getAttribute("idCapo").toString();
				}
				if (idCapo!=null)
				{
				Capo c	= Capo.load( idCapo, db );
			 	/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
				context.getRequest().setAttribute( "Speditore", speditore );
				if(speditore.getAddressList().size() > 0){
					OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
					context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
				}*/

				Capo capoClonato = new Capo();
				capoClonato.setCd_mod4(c.getCd_mod4());
				capoClonato.setCd_data_mod4(c.getCd_data_mod4());
				capoClonato.setCd_codice_azienda_provenienza(c.getCd_codice_azienda_provenienza());
				capoClonato.setCd_codice_azienda(c.getCd_codice_azienda());

				capoClonato.setCd_maschio(null);
				capoClonato.setCd_info_azienda_provenienza(c.getCd_info_azienda_provenienza());
				capoClonato.setCd_specie(c.getCd_specie());
				capoClonato.setCd_categoria_bovina(c.getCd_categoria_bovina());
				capoClonato.setCd_categoria_bufalina(c.getCd_categoria_bufalina());
				
				//new Clona per Flusso 173-rq1
				capoClonato.setCd_id_razza(c.getCd_id_razza());
				capoClonato.setCd_categoria_rischio(c.getCd_categoria_rischio());
				capoClonato.setCd_vincolo_sanitario(c.isCd_vincolo_sanitario());
				capoClonato.setCd_vincolo_sanitario_motivo(c.getCd_vincolo_sanitario_motivo());
				capoClonato.setCd_vincolo_sanitario_piano(c.isCd_vincolo_sanitario_piano());
				capoClonato.setCd_piano_risanamento(c.getCd_piano_risanamento());
				capoClonato.setCd_qualifica_brc(c.getCd_qualifica_brc());
				capoClonato.setCd_qualifica_tbc(c.getCd_qualifica_tbc());
				capoClonato.setCd_qualifica_sanitaria_brc(c.isCd_qualifica_sanitaria_brc());
				capoClonato.setCd_qualifica_sanitaria_tbc(c.isCd_qualifica_sanitaria_tbc());
				capoClonato.setCd_macellazione_differita(c.getCd_macellazione_differita());
				capoClonato.setCd_bse(c.getCd_bse());
				capoClonato.setBse_data_prelievo(c.getBse_data_prelievo());
				capoClonato.setBse_data_ricezione_esito(c.getBse_data_ricezione_esito());
				capoClonato.setBse_esito(c.getBse_esito());
				capoClonato.setBse_note(c.getBse_note());
				capoClonato.setCd_note(c.getCd_note());
				
				capoClonato.setCd_tipo_mezzo_trasporto(c.getCd_tipo_mezzo_trasporto());
				capoClonato.setCd_targa_mezzo_trasporto(c.getCd_targa_mezzo_trasporto());
				capoClonato.setCd_trasporto_superiore8ore(c.isCd_trasporto_superiore8ore());
				//					capoClonato.setCd_speditore(c.getCd_speditore());
				//					capoClonato.setCd_codice_speditore(c.getCd_codice_speditore());
				//capoClonato.setCd_id_speditore(c.getCd_id_speditore());
				capoClonato.setCd_seduta_macellazione(c.getCd_seduta_macellazione());
				capoClonato.setCd_veterinario_1(c.getCd_veterinario_1());
				capoClonato.setCd_veterinario_2(c.getCd_veterinario_2());
				capoClonato.setCd_veterinario_3(c.getCd_veterinario_3());
				capoClonato.setCd_data_arrivo_macello(c.getCd_data_arrivo_macello());
				capoClonato.setVpm_veterinario(c.getVpm_veterinario());
				capoClonato.setVpm_veterinario_2(c.getVpm_veterinario_2());
				capoClonato.setVpm_veterinario_3(c.getVpm_veterinario_3());
				context.getRequest().setAttribute( "Capo", capoClonato );
				}
			}
			
			if (db==null)
				db		= this.getConnection( context );
			
			String altIdString = context.getRequest().getParameter( "altId" );
			int altId = -1;
			Timestamp data = null;
			int numeroSeduta = -1;
			try {altId=Integer.parseInt(altIdString);} catch(Exception e){};
			try {
				Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(vpmData);
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    String parsedDate = formatter.format(initDate);
			    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    java.util.Date date = sdf2.parse(parsedDate);
			    data = new java.sql.Timestamp(date.getTime());

				} catch(Exception e){};
			try {numeroSeduta=Integer.parseInt(cdSedutaMacellazione);} catch(Exception e){};
			boolean sessionePregressa = false;
			if (altId>0 && numeroSeduta>0 && data!=null)
				sessionePregressa = MacelliUtil.isSessionePregressa(altId, data, numeroSeduta, db);
			if (sessionePregressa)
				context.getRequest().setAttribute("sessionePregressa", "SESSIONE PREGRESSA");
		
		}
		catch (Exception e)
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		getConfigTipo(context);
		//caricaLookup( context );
		if (configTipo.getIdTipo()==1)
			caricaLookupCapo(context);
		else
			caricaLookup( context );
		

		return ret;
	}

	public String executeCommandModificaCapo( ActionContext context )
	{
		String		ret	= "ModificaCapoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			String id = context.getParameter( "id" );
			if(id == null || id.equals("")){
				id = context.getRequest().getAttribute("id").toString();
			}
			Capo c	= Capo.load( id, db );
			SintesisStabilimento org = new SintesisStabilimento( db, c.getId_macello(), true );
			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
			if(speditore.getAddressList().size() > 0){
				OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
				context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
			}*/
			
			String vpmData = stringa	( "vpmData", context );
			context.getRequest().setAttribute( "vpmData", vpmData );
			String mavamData = stringa	( "mavamData", context );
			context.getRequest().setAttribute( "mavamData", mavamData );
			String cdSedutaMacellazione = stringa	( "cdSedutaMacellazione", context );
			context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
				context.getRequest().setAttribute( "sessioneCorrente", vpmData + "-" + cdSedutaMacellazione );
			else
				context.getRequest().setAttribute( "sessioneCorrente", mavamData + "-" + cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
				context.getRequest().setAttribute( "comboSessioniMacellazione", vpmData + "-" + cdSedutaMacellazione  );
			else
				context.getRequest().setAttribute( "comboSessioniMacellazione", mavamData + "-" + cdSedutaMacellazione  );
			
			ArrayList<NonConformita>		ncsVAM					= NonConformita.load	( c.getId(), db );
			ArrayList<Campione> 			campioni 				= Campione.load			( c.getId(), db );
			Tampone			tampone 				= Tampone.load			( c, db );
			ArrayList<Organi>   			organi		 			= Organi.loadByOrgani	( c.getId(), db );
			TreeMap<Integer, ArrayList<Organi>> organiNew = new TreeMap<Integer, ArrayList<Organi>>();
			for(Organi o : organi){
				if(organiNew.containsKey(o.getLcso_organo())){
					organiNew.get(o.getLcso_organo()).add(o);
				}
				else{
					ArrayList<Organi> organiList = new ArrayList<Organi>();
					organiList.add(o);
					organiNew.put(o.getLcso_organo(), organiList);
				}
			}
			ArrayList<PatologiaRilevata>	patologieRilevate		= PatologiaRilevata.load( c.getId(), db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( c.getId(), db );

			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( c.getId(), db );

			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = capoLogDao.select(db, c.getCd_matricola());

			context.getRequest().setAttribute( "Capo", c );
			context.getRequest().setAttribute( "CapoLog", capoLog );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );
			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Tampone", tampone );

			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
			
			boolean sessionePregressa = false;
			sessionePregressa = MacelliUtil.isSessionePregressa(c.getId_macello(), c.getVpm_data(), c.getCd_seduta_macellazione(), db);
			if (sessionePregressa)
				context.getRequest().setAttribute("sessionePregressa", "SESSIONE PREGRESSA");


		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute( "Update", new Boolean( true ) );

		//caricaLookup( context );
		getConfigTipo(context);
		//caricaLookup( context );
		if (configTipo.getIdTipo()==1)
			caricaLookupCapo(context);
		else
			caricaLookup( context );
		


		return ret;
	}

	public String executeCommandSave( ActionContext context )
	{ 
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		boolean clona = ( context.getRequest().getParameter("clona") != null && context.getRequest().getParameter("clona").equals("si") );

		Capo c = null;
		Connection db = null;
		try
		{
			db = this.getConnection( context );
			c = loadCapo( context );
			
			String vpmData = stringa	( "vpm_data", context );
			if(context.getParameter("vpmData")!=null && !context.getParameter("vpmData").equals("") && !context.getParameter("vpmData").equals("null"))
				vpmData = stringa("vpmData", context);
			context.getRequest().setAttribute( "vpmData", vpmData );
			String mavamData = stringa	( "mavam_data", context );
			if(context.getParameter("mavamData")!=null && !context.getParameter("mavamData").equals("") && !context.getParameter("mavamData").equals("null"))
				mavamData = stringa("mavamData", context);
			context.getRequest().setAttribute( "mavamData", mavamData );
			String cdSedutaMacellazione = stringa	( "cd_seduta_macellazione", context );
			if(context.getParameter("cdSedutaMacellazione")!=null && !context.getParameter("cdSedutaMacellazione").equals("") && !context.getParameter("cdSedutaMacellazione").equals("null"))
				cdSedutaMacellazione = stringa("cdSedutaMacellazione", context);
			context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
			{
				context.getRequest().setAttribute( "sessioneCorrente", vpmData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", vpmData + "-" + cdSedutaMacellazione  );
			}
			else
			{
				context.getRequest().setAttribute( "sessioneCorrente", mavamData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", mavamData + "-" + cdSedutaMacellazione  );
			}
			
			if(context.getParameter("vpmData")!=null && !context.getParameter("vpmData").equals("") && !context.getParameter("vpmData").equals("null"))
				c.setVpm_data(data("vpmData", context));
			if(context.getParameter("mavamData")!=null && !context.getParameter("mavamData").equals("") && !context.getParameter("mavamData").equals("null"))
				c.setMavam_data(data("mavamData", context));
			if(context.getParameter("cdSedutaMacellazione")!=null && !context.getParameter("cdSedutaMacellazione").equals("") && !context.getParameter("cdSedutaMacellazione").equals("null"))
				c.setCd_seduta_macellazione(intero("cdSedutaMacellazione", context));
			
			//Controllo Matricola
			if(c.getCd_matricola()!=null && !c.getCd_matricola().equals("")){
				AjaxCalls ac = new AjaxCalls();
				CapoAjax  ca = ac.isCapoEsistenteSintesis(c.getCd_matricola());
				if( ca.isEsistente() ){
					this.freeConnection(context, db);
					context.getRequest().setAttribute("idCapo", c.getId());
					context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Matricola gia' esistente!" );
					return executeCommandNuovoCapo( context );
				}
			}
			if(PopolaCombo.verificaStampaMod10(c.getId_macello(), context.getParameter("vpm_data"), 0)==true)
			{
				context.getRequest().setAttribute("idCapo", c.getId());
				context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
				return executeCommandNuovoCapo( context );
			}
				

			//Controllo Progressivo di Macellazione
			if(c.getVpm_data()!=null && c.getStato_macellazione()!=null && c.getStato_macellazione().equals("Incompleto: Inseriti solo i dati sul controllo documentale")){
				AjaxCalls ac = new AjaxCalls();
				if(!ac.controlloProgressivoMacellazioneBovini(c.getCd_seduta_macellazione(),c.getId_macello(), new SimpleDateFormat("dd/MM/yyyy").format( new Date(c.getVpm_data().getTime() ) ), c.getProgressivo_macellazione(), c.getCd_matricola())){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Progressivo Macellazione gia' esistente!" );
					return executeCommandNuovoCapo( context );
				}
			}
			ArrayList<Campione> cmps = loadCampioni( context, c );

			c.setEntered_by( this.getUserId(context) );
			
			
			c = c.store( db, cmps );


			//Inizio Log del capo in m_capi_log


//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());

			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());


			
			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
			

			/*if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}*/

			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();


			String[] patologieRilevate = context.getRequest().getParameterValues( "vpm_patologie_rilevate" );
			patologieRilevate = (patologieRilevate == null) ? (new String[0]) : (patologieRilevate);
			for( String index: patologieRilevate )
			{
				if( !"-1".equals( index ) )
				{
					PatologiaRilevata pr = new PatologiaRilevata();
					pr.setId_capo		( c.getId() );
					pr.setId_patologia	( Integer.parseInt( index ) );
					pr.setEntered		( c.getEntered() );
					pr.setModified		( c.getEntered() );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}

			// Inserito da Alberto
			String[] casl_NCRilevate = context.getRequest().getParameterValues( "casl_NC_rilevate" );
			casl_NCRilevate = (casl_NCRilevate == null) ? (new String[0]) : (casl_NCRilevate);
			for( String index: casl_NCRilevate )
			{
				if( !"-1".equals( index ) )
				{
					Casl_Non_Conformita_Rilevata nc = new Casl_Non_Conformita_Rilevata();
					nc.setId_capo				( c.getId() );
					nc.setId_casl_non_conformita( Integer.parseInt( index ));
					nc.setEntered				( c.getEntered() );
					nc.setModified				( c.getEntered() );
					nc.setEntered_by			( getUserId(context) );
					nc.setModified_by			( getUserId(context) );
					nc.store( db );
				}
			}

			ArrayList<NonConformita> ncs = loadNC( context, c );
			for( NonConformita nc: ncs )
			{
				nc.setEntered		( c.getEntered() );
				nc.setModified		( c.getEntered() );
				nc.setEntered_by	( getUserId(context) );
				nc.setModified_by	( getUserId(context) );
				nc.store			( db );
			}

			// Inserito da Alberto
			String[] casl_provvedimenti_selezionati = context.getRequest().getParameterValues( "casl_provvedimenti_selezionati" );
			casl_provvedimenti_selezionati = (casl_provvedimenti_selezionati == null) ? (new String[0]) : (casl_provvedimenti_selezionati);
			for( String index: casl_provvedimenti_selezionati )
			{
				if( !"-1".equals( index ) )
				{
					ProvvedimentiCASL pr = new ProvvedimentiCASL();
					pr.setId_capo				( c.getId() );
					pr.setId_provvedimento		( Integer.parseInt( index ));
					pr.setEntered				( c.getEntered() );
					pr.setModified				( c.getEntered() );
					pr.setEntered_by			( getUserId(context) );
					pr.setModified_by			( getUserId(context) );
					pr.store( db );
				}
			}

			for( Campione camp: cmps )
			{
				camp.setId_capo		( c.getId() );
				camp.setEntered		( c.getEntered() );
				camp.setModified	( c.getEntered() );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db );
			}
			Tampone tampone = loadTampone( context, c );
			
			tampone.setEntered		( c.getEntered() );
			tampone.setId_macello(c.getId_macello());
			tampone.setSessione_macellazione(c.getCd_seduta_macellazione());
			tampone.setData_macellazione(c.getVpm_data());
			tampone.setModified	( c.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db ,context);
				
			}
			tampone.associa_tampone_capo(c, db);
			}
			
			ArrayList<Organi> organi = loadOrgani( context, c );
			for( Organi org: organi )
			{
				org.setEntered		( c.getEntered() );
				org.setModified		( c.getEntered() );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
			}

			c.storico_vam_non_conformita		= NonConformita.load	( c.getId(), db );
			c.storico_vpm_campioni				= Campione.load			( c.getId(), db );
			c.storico_lcso_organi				= Organi.loadByOrgani	( c.getId(), db );
			c.storico_vpm_patologie_rilevate	= PatologiaRilevata.load( c.getId(), db );

			//c.storico x provvedimenti
			//c.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c.getId(), db );

	
			context.getRequest().setAttribute( "messaggio", "Capo aggiunto" );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
			if(e1.getMessage().contains("m_evita_matricole_duplicate")){
				context.getRequest().setAttribute( "messaggio", "Errore: Matricola gia' esistente" );
			}
			else{
				context.getRequest().setAttribute( "messaggio", "Errore: " + e1.getMessage() );
			}
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		//		if( "ok".equalsIgnoreCase( context.getParameter( "clone" ) ) )
		if( clona )
		{
			Capo capoClonato = new Capo();
			capoClonato.setCd_mod4(c.getCd_mod4());
			capoClonato.setCd_data_mod4(c.getCd_data_mod4());
			//			capoClonato.setCd_speditore(c.getCd_speditore());
			//			capoClonato.setCd_codice_speditore(c.getCd_codice_speditore());
			
			capoClonato.setCd_targa_mezzo_trasporto(c.getCd_targa_mezzo_trasporto());
			capoClonato.setCd_tipo_mezzo_trasporto(c.getCd_tipo_mezzo_trasporto());
			capoClonato.setCd_codice_azienda_provenienza(c.getCd_codice_azienda_provenienza());
			capoClonato.setCd_trasporto_superiore8ore(c.isCd_trasporto_superiore8ore());
			
			capoClonato.setCd_id_speditore(c.getCd_id_speditore());
			capoClonato.setCd_veterinario_1(c.getCd_veterinario_1());
			capoClonato.setCd_veterinario_2(c.getCd_veterinario_2());
			capoClonato.setCd_veterinario_3(c.getCd_veterinario_3());
			capoClonato.setCd_data_arrivo_macello(c.getCd_data_arrivo_macello());
			capoClonato.setVpm_veterinario(c.getVpm_veterinario());
			capoClonato.setVpm_veterinario_2(c.getVpm_veterinario_2());
			capoClonato.setVpm_veterinario_3(c.getVpm_veterinario_3());
			context.getRequest().setAttribute( "idCapo", c.getId() );
			context.getRequest().setAttribute( "Capo", capoClonato );
			ret = executeCommandNuovoCapo( context );
		}
		else
		{
			context.getRequest().setAttribute("AltId", c.getId_macello());
			ret = executeCommandList( context );
		}

		return ret;
	}

	public String executeCommandUpdate( ActionContext context )
	{
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );

			Capo vecchioCapo = Capo.load( context.getParameter( "id_capo" ), db );
			ArrayList<PatologiaRilevata>	vecchiePatologie		= PatologiaRilevata.load( vecchioCapo.getId(), db );
			ArrayList<Casl_Non_Conformita_Rilevata>	vecchieCaslNC	= Casl_Non_Conformita_Rilevata.load( vecchioCapo.getId(), db );
			ArrayList<ProvvedimentiCASL>	vecchiProvvedimenti		= ProvvedimentiCASL.load( vecchioCapo.getId(), db );
			ArrayList<NonConformita>		vecchieNcs				= NonConformita.load( vecchioCapo.getId(), db );
			ArrayList<Campione>				vecchiCampioni			= Campione.load( vecchioCapo.getId(), db );
			ArrayList<Organi>				vecchiOrgani			= Organi.loadByOrgani( vecchioCapo.getId(), db );
			vecchioCapo.storico_lcso_organi				= vecchiOrgani;
			vecchioCapo.storico_vam_non_conformita		= vecchieNcs;
			vecchioCapo.storico_vpm_campioni			= vecchiCampioni;
			vecchioCapo.storico_vpm_patologie_rilevate	= vecchiePatologie;
			//vecchioCapo.storico_vpm_non_conformita_rilevate	= vecchieCaslNC;

			Capo c	= loadCapo( context );
			
			String vpmData = stringa	( "vpm_data", context );
			if(context.getParameter("vpmData")!=null && !context.getParameter("vpmData").equals("") && !context.getParameter("vpmData").equals("null"))
				vpmData = stringa("vpmData", context);
			context.getRequest().setAttribute( "vpmData", vpmData );
			String mavamData = stringa	( "mavam_data", context );
			if(context.getParameter("mavamData")!=null && !context.getParameter("mavamData").equals("") && !context.getParameter("mavamData").equals("null"))
				mavamData = stringa("mavamData", context);
			context.getRequest().setAttribute( "mavamData", mavamData );
			String cdSedutaMacellazione = stringa	( "cd_seduta_macellazione", context );
			if(context.getParameter("cdSedutaMacellazione")!=null && !context.getParameter("cdSedutaMacellazione").equals("") && !context.getParameter("cdSedutaMacellazione").equals("null"))
				cdSedutaMacellazione = stringa("cdSedutaMacellazione", context);
			context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
			{
				context.getRequest().setAttribute( "sessioneCorrente", vpmData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", vpmData + "-" + cdSedutaMacellazione  );
			}
			else
			{
				context.getRequest().setAttribute( "sessioneCorrente", mavamData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", mavamData + "-" + cdSedutaMacellazione  );
			}
			
			if(context.getParameter("vpmData")!=null && !context.getParameter("vpmData").equals("") && !context.getParameter("vpmData").equals("null"))
				c.setVpm_data(data("vpmData", context));
			if(context.getParameter("mavamData")!=null && !context.getParameter("mavamData").equals("") && !context.getParameter("mavamData").equals("null"))
				c.setMavam_data(data("mavamData", context));
			if(context.getParameter("cdSedutaMacellazione")!=null && !context.getParameter("cdSedutaMacellazione").equals("") && !context.getParameter("cdSedutaMacellazione").equals("null"))
				c.setCd_seduta_macellazione(intero("cdSedutaMacellazione", context));
			
			//Controllo Progressivo di Macellazione
			if(c.getVpm_data()!=null && c.getStato_macellazione()!=null && c.getStato_macellazione().equals("Incompleto: Inseriti solo i dati sul controllo documentale")){
				AjaxCalls ac = new AjaxCalls();
				if(!ac.controlloProgressivoMacellazioneBovini(c.getCd_seduta_macellazione(),c.getId_macello(), new SimpleDateFormat("dd/MM/yyyy").format( new Date(c.getVpm_data().getTime() ) ), c.getProgressivo_macellazione(), vecchioCapo.getCd_matricola())){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", "Capo NON modificato. Progressivo Macellazione gia' esistente!" );
					context.getRequest().setAttribute("id", vecchioCapo.getId());
					return executeCommandModificaCapo( context );
				}
			}
			if(PopolaCombo.verificaStampaMod10(c.getId_macello(), context.getParameter("vpm_data"), 0)==true)
			{
				context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
				return executeCommandNuovoCapo( context );
			}

			c.setId			( vecchioCapo.getId() );
			c.setEntered_by	( vecchioCapo.getEntered_by() );
			c.setEntered	( vecchioCapo.getEntered() );
			c.setModified	( new Timestamp( System.currentTimeMillis() ) );
			c.setIstopatologico_data_richiesta(vecchioCapo.getIstopatologico_data_richiesta());
			c.setIstopatologico_id(vecchioCapo.getIstopatologico_id());
			c.setIstopatologico_richiesta(vecchioCapo.isIstopatologico_richiesta());
			ArrayList<Campione>	campioni		= loadCampioni( context, c );
			c.update		( db , campioni );
			

			
			
			
						Tampone tampone = loadTampone( context, c );
						
						tampone.setEntered		( c.getEntered() );
						tampone.setId_macello(c.getId_macello());
						tampone.setSessione_macellazione(c.getCd_seduta_macellazione());

						tampone.setData_macellazione(c.getVpm_data());
						tampone.setModified	( c.getEntered() );
						tampone.setEntered_by	( getUserId(context) );
						tampone.setModified_by	( getUserId(context) );
						if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
						{
						if(tampone.getId()<= 0)
						{
							tampone.store			( db,context);
							
							
						}
						else
						{
							tampone.updateTampone(db);
						}
						tampone.cancella_tampone_capo(c, db);
						tampone.associa_tampone_capo(c, db);
						
						}
						else
						{
							tampone.cancella_tampone_capo(c, db);
						}


			//Inizio Log del capo in m_capi_log
			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;

			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}*/

			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();

//			capoLog.setAslSpeditore(speditore != null ? speditore.getSiteId() : 0);
			capoLog.setCodiceAziendaNascita(c.getCd_codice_azienda());
//			capoLog.setComuneSpeditore(speditoreAddress != null ? speditoreAddress.getCity() : "");
			capoLog.setDataNascita(c.getCd_data_nascita());
			capoLog.setEnteredBy(c.getEntered_by());
			capoLog.setIdMacello(c.getId_macello());
			capoLog.setInBdn(context.getRequest().getParameter("capo_in_bdn") != null && context.getRequest().getParameter("capo_in_bdn").equals("si"));
			capoLog.setMatricola(c.getCd_matricola());
			capoLog.setModifiedBy(c.getModified_by());
			capoLog.setRazza(c.getCd_id_razza());
			//capoLog.setSesso(c.isCd_maschio() ? "M" : "F");
			capoLog.setSesso(Boolean.TRUE.equals(c.isCd_maschio()) ? "M" : Boolean.FALSE.equals(c.isCd_maschio()) ? "F" : null==c.isCd_maschio() ? "" : "" );
			capoLog.setSpecie(c.getCd_specie());
			capoLog.setTrashedDate(c.getTrashed_date());
			

			if(capoLog.isInBdn()){

				try{
					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}

			capoLogDao.log(db, capoLog);
			//Fine Log del capo in m_capi_log


			String[] patologie = context.getRequest().getParameterValues( "vpm_patologie_rilevate" );
			patologie = (patologie == null) ? (new String[0]) : (patologie);
			//eliminare le patologie non piu selezionate ed aggiorno le altre
			for( PatologiaRilevata vp: vecchiePatologie )
			{
				boolean cancellare = true;
				for( String pat: patologie )
				{
					if( vp.getId_patologia() == Integer.parseInt( pat ) )
					{
						cancellare = false;

						//update delle vecchie patologie
						vp.setModified		( c.getModified() );
						vp.setModified_by	( getUserId(context) );
						vp.update( db );
					}

					if( cancellare ) //cancellazione delle patologie rimosse
					{
						PatologiaRilevata.delete( vp.getId(), getUserId(context), db );
					}
				}
			}
			//inserisco le nuove patologie
			for( String pat: patologie )
			{
				boolean inserire = true;
				for( PatologiaRilevata vp: vecchiePatologie )
				{
					if( vp.getId_patologia() == Integer.parseInt( pat ) )
					{
						inserire = false;
					}
				}

				if( !"-1".equals( pat ) && inserire )
				{
					PatologiaRilevata pr = new PatologiaRilevata();
					pr.setId_capo		( c.getId() );
					pr.setId_patologia	( Integer.parseInt( pat ) );
					pr.setEntered		( c.getEntered() );
					pr.setModified		( c.getEntered() );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}


			// Inserito da Alberto
			String[] casl_non_conformita = context.getRequest().getParameterValues( "casl_NC_rilevate" );
			casl_non_conformita = (casl_non_conformita == null) ? (new String[0]) : (casl_non_conformita);
			//eliminare le casl_non_conformita non piu selezionate ed aggiorno le altre
			for( Casl_Non_Conformita_Rilevata vp: vecchieCaslNC )
			{
				boolean cancellare = true;
				for( String pat: casl_non_conformita )
				{
					if( vp.getId_casl_non_conformita() == Integer.parseInt( pat ) )
					{
						cancellare = false;

						//update delle vecchie casl_non_conformita
						vp.setModified		( c.getModified() );
						vp.setModified_by	( getUserId(context) );
						vp.update( db );
					}

					if( cancellare ) //cancellazione delle casl_non_conformita rimosse
					{
						Casl_Non_Conformita_Rilevata.delete( vp.getId(), getUserId(context), db );
					}
				}
			}
			//inserisco le nuove casl_non_conformita
			for( String pat: casl_non_conformita )
			{
				boolean inserire = true;
				for( Casl_Non_Conformita_Rilevata vp: vecchieCaslNC )
				{
					if( vp.getId_casl_non_conformita() == Integer.parseInt( pat ) )
					{
						inserire = false;
					}
				}

				if( !"-1".equals( pat ) && inserire )
				{
					Casl_Non_Conformita_Rilevata pr = new Casl_Non_Conformita_Rilevata();
					pr.setId_capo				( c.getId() );
					pr.setId_casl_non_conformita( Integer.parseInt( pat ) );
					pr.setEntered				( c.getEntered() );
					pr.setModified				( c.getEntered() );
					pr.setEntered_by			( getUserId(context) );
					pr.setModified_by			( getUserId(context) );
					pr.store( db );
				}
			}	      	

			String[] provvedimenti = context.getRequest().getParameterValues( "casl_provvedimenti_selezionati" );
			provvedimenti = (provvedimenti == null) ? (new String[0]) : (provvedimenti);
			//eliminare le provvedimenti non piu selezionate ed aggiorno le altre
			for( ProvvedimentiCASL vp: vecchiProvvedimenti )
			{
				boolean cancellare = true;
				for( String pro: provvedimenti )
				{
					if( vp.getId_provvedimento() == Integer.parseInt( pro ) )
					{
						cancellare = false;

						//update delle vecchie provvedimenti
						vp.setModified		( c.getModified() );
						vp.setModified_by	( getUserId(context) );
						vp.update( db );
					}

					if( cancellare ) //cancellazione delle provvedimenti rimosse
					{
						ProvvedimentiCASL.delete( vp.getId(), getUserId(context), db );
					}
				}
			}
			//inserisco le nuove provvedimenti
			for( String pro: provvedimenti )
			{
				boolean inserire = true;
				for( ProvvedimentiCASL vp: vecchiProvvedimenti )
				{
					if( vp.getId_provvedimento() == Integer.parseInt( pro ) )
					{
						inserire = false;
					}
				}

				if( !"-1".equals( pro ) && inserire )
				{
					ProvvedimentiCASL pr = new ProvvedimentiCASL();
					pr.setId_capo			( c.getId() );
					pr.setId_provvedimento	( Integer.parseInt( pro ) );
					pr.setEntered			( c.getEntered() );
					pr.setModified			( c.getEntered() );
					pr.setEntered_by		( getUserId(context) );
					pr.setModified_by		( getUserId(context) );
					pr.store( db );
				}
			}

			ArrayList<NonConformita> ncs = loadNC( context, c );
			//eliminare le non conformita' non piu selezionate ed aggiornare le altre
			for( NonConformita ncv: vecchieNcs )
			{
				boolean cancellare = true;
				for( NonConformita nc: ncs )
				{
					if( ncv.getId() == nc.getId() )
					{
						cancellare = false;

						//update delle vecchie non conformita'
						nc.setEntered_by	( ncv.getEntered_by() );
						nc.setEntered		( ncv.getEntered() );
						nc.setModified		( c.getModified() );
						nc.setModified_by	( getUserId(context) );
						nc.update			( db );
					}
				}

				if( cancellare ) //cancellazione delle non conformita' rimosse
				{
					NonConformita.delete( ncv.getId(), getUserId(context), db );
				}
			}
			//salvataggio delle nuove non conformita'
			for( NonConformita nc: ncs )
			{
				if( nc.getId() <= 0 )
				{
					nc.setModified		( c.getEntered() );
					nc.setModified_by	( getUserId(context) );
					nc.setEntered		( c.getEntered() );
					nc.setEntered_by	( getUserId(context) );
					nc.store			( db );
				}
			}


			//eliminare li campioni non piu selezionati ed aggiornare gli altri
			for( Campione campv: vecchiCampioni )
			{
				boolean cancellare = true;
				for( Campione camp: campioni )
				{
					if( campv.getId() == camp.getId() )
					{
						cancellare = false;

						//update dei vecchi campioni
						camp.setEntered_by	( campv.getEntered_by() );
						camp.setEntered		( campv.getEntered() );
						camp.setModified	( c.getModified() );
						camp.setModified_by	( getUserId(context) );
						camp.update( db );
					}
				}

				if( cancellare ) //cancellazione dei campioni rimossi
				{
					Campione.delete( campv.getId(), getUserId(context), db );
				}
			}
			//salvataggio dei nuovi campioni
			for( Campione camp: campioni )
			{
				if( camp.getId() <= 0 )
				{
					camp.setEntered		( c.getEntered() );
					camp.setModified	( c.getEntered() );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db );
				}
			}
			

			ArrayList<Organi>	organi			= loadOrgani( context, c );
			//eliminare gli organi non + selezionati ed aggiornare gli altri
			for( Organi orgv: vecchiOrgani )
			{
				//				boolean cancellare = true;
				//				for( Organi org: organi )
				//				{
				//					if( orgv.getId() == org.getId() )
				//					{
				//						cancellare = false;
				//						
				//						//update dei vecchi organi
				//						org.setEntered_by	( orgv.getEntered_by() );
				//						org.setEntered		( orgv.getEntered() );
				//						org.setModified		( c.getModified() );
				//						org.setModified_by	( getUserId(context) );
				//						org.update( db );
				//					}
				//				}

				//				if( cancellare ) //cancellazione degli organi rimossi
				//				{
				Organi.delete( orgv.getId(), getUserId(context), db );
				//				}
			}
			//salvataggio dei nuovi organi
			for( Organi org: organi )
			{

				//				if( org.getId() <= 0 )
				//				{
				org.setEntered		( c.getEntered() );
				org.setModified		( c.getEntered() );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
				//				}
			}

			Capo nuovoCapo = Capo.load( c.getId() + "", db );

			nuovoCapo.storico_vam_non_conformita			= NonConformita.load	( nuovoCapo.getId(), db );
			nuovoCapo.storico_vpm_campioni					= Campione.load			( nuovoCapo.getId(), db );
			nuovoCapo.storico_lcso_organi					= Organi.loadByOrgani	( nuovoCapo.getId(), db );
			nuovoCapo.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( nuovoCapo.getId(), db );
			//nuovoCapo.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( nuovoCapo.getId(), db );
			//nuovoCapo.storico x provvedimenti casl
			context.getRequest().setAttribute( "messaggio", "Capo aggiornato" );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			context.getRequest().setAttribute( "messaggio", "Errore: " + e1.getMessage() );
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		if( "ok".equalsIgnoreCase( context.getParameter( "clone" ) ) )
		{
			ret = executeCommandNuovoCapo( context );
		}
		else
		{
			ret = executeCommandList( context );
		}

		return ret;
	}

	public String executeCommandPrintBRCRilevazioneMacelli(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("altId");
			int altid = new Integer(id);
			SintesisStabilimento org = new SintesisStabilimento(db, altid, true);
			HashMap map = new HashMap();
			map.put("altid", altid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("BRC_rilevazione_macello_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}


	public String executeCommandPrintTBCRilevazioneMacelli(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("altId");
			int altid = new Integer(id);
			SintesisStabilimento org = new SintesisStabilimento(db, altid, true);
			HashMap map = new HashMap();
			map.put("altid", altid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("TBC_rilevazione_macello_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}

	/*Aggiunta metodo per richiamare jsp relativa al modello idatidosi.*/
	public String executeCommandToModelloIdatidosi(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;

		try
		{
			db = this.getConnection( context );
			SintesisStabilimento org = new SintesisStabilimento( db, Integer.parseInt( context.getParameter( "altId" ) ), true );
			context.getRequest().setAttribute( "OrgDetails", org );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		return "ToModelloIdatidosiOK";



	}

	
	public String executeCommandPrintDisinfezioneMezziTrasporto(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("altId");
			int altid = new Integer(id);
			SintesisStabilimento org = new SintesisStabilimento(db, altid, true);
			HashMap map = new HashMap();
			map.put("altid", altid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("Disinfezione_mezzi_di_trasporto_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}


	public String executeCommandPrintInvioCampioniTBC(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("capoId");
			int capoid = new Integer(id);
			//Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("capoid", capoid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("Invio_campioni_TBC_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}


	public String executeCommandPrintModelloLBE(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("capoId");
			int capoid = new Integer(id);
			//Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("capoid", capoid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("Modello_LBE_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}


	public String executeCommandPrintModello1033TBC(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("capoId");
			int capoid = new Integer(id);
			//Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("capoid", capoid);
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("Modello_10_33_TBC_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}



	public String executeCommandSaveToLiberoConsumo( ActionContext context )
	{
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{ 
			return ("PermissionError");
		}

		Connection db = null;
		try  
		{
			db		= this.getConnection( context );

			Capo c_old = (Capo) context.getSession().getAttribute( "Capo" );
			Capo c	= loadCapo( context );
			
			String vpmData = stringa	( "vpm_data", context );
			context.getRequest().setAttribute( "vpmData", vpmData );
			String mavamData = stringa	( "mavam_data", context );
			context.getRequest().setAttribute( "mavamData", vpmData );
			String cdSedutaMacellazione = stringa	( "cd_seduta_macellazione", context );
			context.getRequest().setAttribute( "cdSedutaMacellazione", cdSedutaMacellazione );
			if(vpmData!=null && !vpmData.equals("") && !vpmData.equals("null"))
			{
				context.getRequest().setAttribute( "sessioneCorrente", vpmData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", vpmData + "-" + cdSedutaMacellazione  );
			}
			else
			{
				context.getRequest().setAttribute( "sessioneCorrente", mavamData + "-" + cdSedutaMacellazione );
				context.getRequest().setAttribute( "comboSessioniMacellazione", mavamData + "-" + cdSedutaMacellazione  );
			}
			
			//Controllo Matricola
			if(c.getCd_matricola()!=null && !c.getCd_matricola().equals("")){
				AjaxCalls ac = new AjaxCalls();
				CapoAjax  ca = ac.isCapoEsistenteSintesis(c.getCd_matricola());
				if( ca.isEsistente() ){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Matricola gia' esistente!" );
					return executeCommandNuovoCapo( context );
				}
			}
			//Controllo Progressivo di Macellazione
			if(c.getVpm_data()!=null && c.getStato_macellazione()!=null && c.getStato_macellazione().equals("Incompleto: Inseriti solo i dati sul controllo documentale")){
				AjaxCalls ac = new AjaxCalls();
				if(!ac.controlloProgressivoMacellazioneBovini(c.getCd_seduta_macellazione(),c.getId_macello(), new SimpleDateFormat("dd/MM/yyyy").format( new Date(c.getVpm_data().getTime() ) ), c.getProgressivo_macellazione(), c_old.getCd_matricola())){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Progressivo Macellazione gia' esistente!" );
					return executeCommandNuovoCapo( context );
				}
			}
			if(PopolaCombo.verificaStampaMod10(c.getId_macello(), context.getParameter("vpm_data"), 0)==true)
			{
				context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
				return executeCommandNuovoCapo( context );
			}


			c_old.setVam_data( c.getVam_data() );
			//			c_old.setVam_favorevole( c.isVam_favorevole() );

			c_old.setMac_progressivo( c.getMac_progressivo() );
			c_old.setProgressivo_macellazione( c.getProgressivo_macellazione() );
			c_old.setMac_tipo( c.getMac_tipo() );
			c_old.setVpm_data( c.getVpm_data() );
			c_old.setVpm_esito( c.getVpm_esito() );
			c_old.setVpm_veterinario( c.getVpm_veterinario() );
			c_old.setVpm_veterinario_2( c.getVpm_veterinario_2() );
			c_old.setVpm_veterinario_3( c.getVpm_veterinario_3() );
			c_old.setVpm_note( c.getVpm_note() );
			c_old.setDestinatario_1_id(c.getDestinatario_1_id());
			c_old.setDestinatario_1_in_regione(c.isDestinatario_1_in_regione());
			c_old.setDestinatario_1_nome(c.getDestinatario_1_nome());
			c_old.setDestinatario_2_id(c.getDestinatario_2_id());
			c_old.setDestinatario_2_in_regione(c.isDestinatario_2_in_regione());
			c_old.setDestinatario_2_nome(c.getDestinatario_2_nome());
			
			
			c_old.setDestinatario_3_id(c.getDestinatario_3_id());
			c_old.setDestinatario_3_in_regione(c.isDestinatario_3_in_regione());
			c_old.setDestinatario_3_nome(c.getDestinatario_3_nome());
			c_old.setDestinatario_4_id(c.getDestinatario_4_id());
			c_old.setDestinatario_4_in_regione(c.isDestinatario_4_in_regione());
			c_old.setDestinatario_4_nome(c.getDestinatario_4_nome());

			c_old.setEntered_by( this.getUserId(context) );

			c_old = c_old.store( db, null );


			//Inizio Log del capo in m_capi_log
//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c_old.getCd_id_speditore());
//			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
//
//			if(speditore.getAddressList().size() > 0){
//				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
//			}

			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();

//			capoLog.setAslSpeditore(speditore != null ? speditore.getSiteId() : 0);
			capoLog.setCodiceAziendaNascita(c_old.getCd_codice_azienda());
//			capoLog.setComuneSpeditore(speditoreAddress != null ? speditoreAddress.getCity() : "");
			capoLog.setDataNascita(c_old.getCd_data_nascita());
			capoLog.setEnteredBy(c_old.getEntered_by());
			capoLog.setIdMacello(c_old.getId_macello());
			capoLog.setInBdn(context.getRequest().getParameter("capo_in_bdn") != null && context.getRequest().getParameter("capo_in_bdn").equals("si"));
			capoLog.setMatricola(c_old.getCd_matricola());
			capoLog.setModifiedBy(c_old.getModified_by());
			capoLog.setRazza(c_old.getCd_id_razza());
			//capoLog.setSesso(c_old.isCd_maschio() ? "M" : "F");
			capoLog.setSesso(Boolean.TRUE.equals(c_old.isCd_maschio()) ? "M" : Boolean.FALSE.equals(c_old.isCd_maschio()) ? "F" : null==c_old.isCd_maschio() ? "" : "" );

			capoLog.setSpecie(c_old.getCd_specie());

			if(capoLog.isInBdn()){

//				try{
//					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
//						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
//					}
//				}
//				catch(Exception e){
//					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
//				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
//				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}

			capoLogDao.log(db, capoLog);
			//Fine Log del capo in m_capi_log


			ArrayList<Campione> cmps = loadCampioni( context, c_old );
			for( Campione camp: cmps )
			{
				camp.setEntered		( c_old.getEntered() );
				camp.setModified	( c_old.getEntered() );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db );
			}
			
			
Tampone tampone = loadTampone( context,c_old );
			
			tampone.setEntered		( c_old.getEntered() );
			tampone.setId_macello(c_old.getId_macello());
			tampone.setSessione_macellazione(c_old.getCd_seduta_macellazione());

			tampone.setData_macellazione(c_old.getVpm_data());
			tampone.setModified	( c_old.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db ,context);
				
			}
			tampone.associa_tampone_capo(c_old, db);
			}

			c_old.storico_vam_non_conformita			= NonConformita.load	( c_old.getId(), db );
			c_old.storico_vpm_campioni					= Campione.load			( c_old.getId(), db );
			c_old.storico_lcso_organi					= Organi.loadByOrgani	( c_old.getId(), db );
			c_old.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( c_old.getId(), db );
			//c_old.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c_old.getId(), db );
		
			context.getRequest().setAttribute( "messaggio", "Capo aggiunto" );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			context.getRequest().setAttribute( "messaggio", "Errore: " + e1.getMessage() );
			
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		if( "ok".equalsIgnoreCase( context.getParameter( "clone" ) ) )
		{
			ret = executeCommandNuovoCapo( context );
		}
		else
		{
			ret = executeCommandList( context );
		}

		return ret;
	}



	public String executeCommandUpdateToLiberoConsumo( ActionContext context )
	{
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );

			Capo vecchioCapo = Capo.load( context.getParameter( "id_capo" ), db );
			Capo c_old = (Capo) context.getSession().getAttribute( "Capo" );
			Capo c	= loadCapo( context );

			//Controllo Progressivo di Macellazione
			if(c.getVpm_data()!=null && c.getStato_macellazione()!=null && c.getStato_macellazione().equals("Incompleto: Inseriti solo i dati sul controllo documentale")){
				AjaxCalls ac = new AjaxCalls();
				if(!ac.controlloProgressivoMacellazioneBovini(c.getCd_seduta_macellazione(), c.getId_macello(), new SimpleDateFormat("dd/MM/yyyy").format( new Date(c.getVpm_data().getTime() ) ), c.getProgressivo_macellazione(), c_old.getCd_matricola())){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", "Capo NON modificato. Progressivo Macellazione gia' esistente!" );
					context.getRequest().setAttribute("id", vecchioCapo.getId());
					return executeCommandModificaCapo( context );
				}
			}
			if(PopolaCombo.verificaStampaMod10(c.getId_macello(), context.getParameter("vpm_data"), 0)==true)
			{
				context.getRequest().setAttribute( "messaggio", "Capo NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
				return executeCommandNuovoCapo( context );
			}

			c_old.setId(vecchioCapo.getId());
			c_old.setVam_data( c.getVam_data() );
			//			c_old.setVam_favorevole( c.isVam_favorevole() );

			c_old.setMac_progressivo( c.getMac_progressivo() );
			c_old.setProgressivo_macellazione( c.getProgressivo_macellazione() );
			c_old.setMac_tipo( c.getMac_tipo() );
			c_old.setVpm_data( c.getVpm_data() );
			c_old.setVpm_esito( c.getVpm_esito() );
			c_old.setVpm_veterinario( c.getVpm_veterinario() );
			c_old.setVpm_veterinario_2( c.getVpm_veterinario_2() );
			c_old.setVpm_veterinario_3( c.getVpm_veterinario_3() );
			c_old.setVpm_note( c.getVpm_note() );
			c_old.setDestinatario_1_id(c.getDestinatario_1_id());
			c_old.setDestinatario_1_in_regione(c.isDestinatario_1_in_regione());
			c_old.setDestinatario_1_nome(c.getDestinatario_1_nome());
			c_old.setDestinatario_2_id(c.getDestinatario_2_id());
			c_old.setDestinatario_2_in_regione(c.isDestinatario_2_in_regione());
			c_old.setDestinatario_2_nome(c.getDestinatario_2_nome());
			
			
			c_old.setDestinatario_3_id(c.getDestinatario_3_id());
			c_old.setDestinatario_3_in_regione(c.isDestinatario_3_in_regione());
			c_old.setDestinatario_3_nome(c.getDestinatario_3_nome());
			c_old.setDestinatario_4_id(c.getDestinatario_4_id());
			c_old.setDestinatario_4_in_regione(c.isDestinatario_4_in_regione());
			c_old.setDestinatario_4_nome(c.getDestinatario_4_nome());

			c_old.setEntered	( vecchioCapo.getEntered() );
			c_old.setModified	( new Timestamp( System.currentTimeMillis() ) );
			c_old.setEntered_by( this.getUserId(context) );

			//c_old = c_old.store( db, null );

			c_old.update( db, null );


			//Inizio Log del capo in m_capi_log
//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c_old.getCd_id_speditore());
//			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
//
//			if(speditore.getAddressList().size() > 0){
//				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
//			}

			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();

//			capoLog.setAslSpeditore(speditore != null ? speditore.getSiteId() : 0);
			capoLog.setCodiceAziendaNascita(c_old.getCd_codice_azienda());
//			capoLog.setComuneSpeditore(speditoreAddress != null ? speditoreAddress.getCity() : "");
			capoLog.setDataNascita(c_old.getCd_data_nascita());
			capoLog.setEnteredBy(c_old.getEntered_by());
			capoLog.setIdMacello(c_old.getId_macello());
			capoLog.setInBdn(context.getRequest().getParameter("capo_in_bdn") != null && context.getRequest().getParameter("capo_in_bdn").equals("si"));
			capoLog.setMatricola(c_old.getCd_matricola());
			capoLog.setModifiedBy(c_old.getModified_by());
			capoLog.setRazza(c_old.getCd_id_razza());
			//capoLog.setSesso(c_old.isCd_maschio() ? "M" : "F");
			capoLog.setSesso(Boolean.TRUE.equals(c_old.isCd_maschio()) ? "M" : Boolean.FALSE.equals(c_old.isCd_maschio()) ? "F" : null==c_old.isCd_maschio() ? "" : "" );

			capoLog.setSpecie(c_old.getCd_specie());
			capoLog.setTrashedDate(c_old.getTrashed_date());

			if(capoLog.isInBdn()){

//				try{
//					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
//						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
//					}
//				}
//				catch(Exception e){
//					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
//				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
//				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}

			capoLogDao.log(db, capoLog);
			//Fine Log del capo in m_capi_log


			//			ArrayList<Campione> cmps = loadCampioni( context, c_old );
			//			for( Campione camp: cmps )
			//			{
			//				camp.setEntered		( c_old.getEntered() );
			//				camp.setModified	( c_old.getEntered() );
			//				camp.setEntered_by	( getUserId(context) );
			//				camp.setModified_by	( getUserId(context) );
			//				camp.store			( db );
			//			}

			ArrayList<Campione> campioni = loadCampioni( context, c_old );
			ArrayList<Campione>	vecchiCampioni = Campione.load( vecchioCapo.getId(), db );

			//eliminare li campioni non piu selezionati ed aggiornare gli altri
			for( Campione campv: vecchiCampioni )
			{
				boolean cancellare = true;
				for( Campione camp: campioni )
				{
					if( campv.getId() == camp.getId() )
					{
						cancellare = false;

						//update dei vecchi campioni
						camp.setEntered_by	( campv.getEntered_by() );
						camp.setEntered		( campv.getEntered() );
						camp.setModified	( c.getModified() );
						camp.setModified_by	( getUserId(context) );
						camp.update( db );
					}
				}

				if( cancellare ) //cancellazione dei campioni rimossi
				{
					Campione.delete( campv.getId(), getUserId(context), db );
				}
			}
			//salvataggio dei nuovi campioni
			for( Campione camp: campioni )
			{
				if( camp.getId() <= 0 )
				{
					camp.setEntered		( c.getEntered() );
					camp.setModified	( c.getEntered() );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db );
				}
			}
			

			Tampone tampone = loadTampone( context, c );
			
			tampone.setEntered		( c.getEntered() );
			tampone.setId_macello(c.getId_macello());
			tampone.setSessione_macellazione(c.getCd_seduta_macellazione());

			tampone.setData_macellazione(c.getVpm_data());
			tampone.setModified	( c.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db,context );
				
				
			}
			else
			{
				tampone.updateTampone(db);
			}
			tampone.cancella_tampone_capo(c, db);
			tampone.associa_tampone_capo(c, db);
			
			}
			else
			{
				tampone.cancella_tampone_capo(c, db);
			}



			c_old.storico_vam_non_conformita			= NonConformita.load	( c_old.getId(), db );
			c_old.storico_vpm_campioni					= Campione.load			( c_old.getId(), db );
			c_old.storico_lcso_organi					= Organi.loadByOrgani	( c_old.getId(), db );
			c_old.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( c_old.getId(), db );
			//c_old.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c_old.getId(), db );
	
			context.getRequest().setAttribute( "messaggio", "Capo modificato" );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			context.getRequest().setAttribute( "messaggio", "Errore: " + e1.getMessage() );
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		if( "ok".equalsIgnoreCase( context.getParameter( "clone" ) ) )
		{
			ret = executeCommandNuovoCapo( context );
		}
		else
		{
			ret = executeCommandList( context );
		}

		return ret;
	}



	public String executeCommandDelete( ActionContext context )
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-delete"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );

			Capo c	= Capo.load( context.getParameter( "id" ), db );
			c.storico_vam_non_conformita			= NonConformita.load	( c.getId(), db );
			c.storico_vpm_campioni					= Campione.load			( c.getId(), db );
			c.storico_lcso_organi					= Organi.loadByOrgani	( c.getId(), db );
			c.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( c.getId(), db );
			//c.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c.getId(), db );
			//c.storico x non provvedimenti

			Capo.delete( c.getId(), getUserId(context), db );

			
			context.getRequest().setAttribute( "messaggio", "Capo eliminato" );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			context.getRequest().setAttribute( "messaggio", "Errore: " + e1.getMessage() );
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		return executeCommandList(context);
	}


	private ArrayList<NonConformita> loadNC(ActionContext context, Capo c)
	{
		ArrayList<NonConformita> ret = new ArrayList<NonConformita>();

		ArrayList<Parameter> tipi			= ParameterUtils.list( context.getRequest(), "nc_tipo_" );
		ArrayList<Parameter> note			= ParameterUtils.list( context.getRequest(), "nc_note_" );
		ArrayList<Parameter> ids			= ParameterUtils.list( context.getRequest(), "nc_id_" );

		for(int i = 0; i < tipi.size(); i++)
		{
			Parameter tipo = tipi.get( i );
			if( !"-1".equalsIgnoreCase( tipo.getValore() ) )
			{
				NonConformita temp = new NonConformita();

				temp.setId_tipo	( Integer.parseInt( tipo.getValore() ) );
				temp.setId_capo	( c.getId() );
				temp.setNote	( note.get( i ).getValore() );
				temp.setId		( Integer.parseInt( ids.get( i ).getValore() ) );

				ret.add( temp );
			}

		}

		return ret;
	}

	private ArrayList<Campione> loadCampioni(ActionContext context, Capo c)
	{
		ArrayList<Campione> ret = new ArrayList<Campione>();

		ArrayList<Parameter> matrici		= ParameterUtils.list( context.getRequest(), "cmp_matrice_" );
		ArrayList<Parameter> tipi			= ParameterUtils.list( context.getRequest(), "cmp_tipo_" );
		ArrayList<Parameter> motivi			= ParameterUtils.list( context.getRequest(), "cmp_id_motivo_" );
		ArrayList<Parameter> esiti			= ParameterUtils.list( context.getRequest(), "cmp_id_esito_" );
		ArrayList<Parameter> molecole		= ParameterUtils.list( context.getRequest(), "cmp_molecole_" );
		ArrayList<Parameter> note			= ParameterUtils.list( context.getRequest(), "cmp_note_" );
		ArrayList<Parameter> ids			= ParameterUtils.list( context.getRequest(), "cmp_identifier_" );

		ArrayList<Parameter> date_ricezione	= ParameterUtils.list( context.getRequest(), "cmp_data_ricezione_esito_" );

		for(int i = 0; i < matrici.size(); i++)
		{
			Parameter tipo = matrici.get( i );
			if( !"-1".equalsIgnoreCase( tipo.getValore() ) )
			{
				Campione temp = new Campione();

				temp.setId_capo			( c.getId() );
				temp.setMatrice			( Integer.parseInt (matrici.get( i ).getValore() ));
				temp.setId_tipo_analisi	( Integer.parseInt( tipi.get( i ).getValore() ) );
				temp.setId_molecole		( Integer.parseInt( molecole.get( i ).getValore() ) );
				temp.setId_motivo		( Integer.parseInt( motivi.get( i ).getValore() ) );
				temp.setId_esito		( Integer.parseInt( esiti.get( i ).getValore() ) );
				temp.setNote			( note.get( i ).getValore() );
				temp.setId				( Integer.parseInt( ids.get( i ).getValore() ) );

				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String data = date_ricezione.get( i ).getValore();
					if(data != null && !data.equals("")){
						Date s = sdf.parse( data );

						Timestamp t = new Timestamp(s.getTime());

						temp.setData_ricezione_esito ( t )  ;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}



				ret.add( temp );
			}

		}

		return ret;
	}
	
	
	private Tampone loadTampone(ActionContext context, Capo c)
	{
		Tampone ret = new Tampone();

	

		if (context.getRequest().getParameter("id_tampone") != null)
		{
		ret.setId(Integer.parseInt (context.getRequest().getParameter("id_tampone") ));
		ret.setPiano_monitoraggio(Integer.parseInt(context.getParameter("piano_monitoraggio")));
		ret.setId_tipo_carcassa	( Integer.parseInt (context.getRequest().getParameter("id_tipo_carcassa") ));
		
		String[] ricerca = context.getRequest().getParameterValues("id_tipo_ricerca");
		if (ricerca!=null)
		{
		for (int i = 0 ; i < ricerca.length ; i++)
		{
			TipoRicerca r = new TipoRicerca();
			r.setId(Integer.parseInt(ricerca[i]));
			ret.getTipo_ricerca().add(r);
		}
		}
		if(context.getRequest().getParameter("metodo").equals("si"))  
			ret.setDistruttivo(true); 
		else
			ret.setDistruttivo(false);
		}

				return ret;

	
	}

	private ArrayList<Organi> loadOrgani(ActionContext context, Capo c )
	{
		ArrayList<Organi> ret = new ArrayList<Organi>();


		ArrayList<Parameter> patologia	= ParameterUtils.list( context.getRequest(), "lcso_patologia_" );
		//ArrayList<Parameter> stadio		= ParameterUtils.list( context.getRequest(), "lcso_stadio_" );
		ArrayList<Parameter> organo		= ParameterUtils.list( context.getRequest(), "lcso_organo_" );
		ArrayList<Parameter> ids		= ParameterUtils.list( context.getRequest(), "lcso_id_" );

		ArrayList<Parameter> lesione_milza				= ParameterUtils.list( context.getRequest(), "lesione_milza_" );
		ArrayList<Parameter> lesione_cuore				= ParameterUtils.list( context.getRequest(), "lesione_cuore_" );
		ArrayList<Parameter> lesione_polmoni			= ParameterUtils.list( context.getRequest(), "lesione_polmoni_" );
		ArrayList<Parameter> lesione_visceri			= ParameterUtils.list( context.getRequest(), "lesione_visceri_" );
		ArrayList<Parameter> lesione_fegato				= ParameterUtils.list( context.getRequest(), "lesione_fegato_" );
		ArrayList<Parameter> lesione_rene				= ParameterUtils.list( context.getRequest(), "lesione_rene_" );
		ArrayList<Parameter> lesione_mammella			= ParameterUtils.list( context.getRequest(), "lesione_mammella_" );
		ArrayList<Parameter> lesione_apparato_genitale	= ParameterUtils.list( context.getRequest(), "lesione_apparato_genitale_" );
		ArrayList<Parameter> lesione_stomaco			= ParameterUtils.list( context.getRequest(), "lesione_stomaco_" );
		ArrayList<Parameter> lesione_intestino			= ParameterUtils.list( context.getRequest(), "lesione_intestino_" );
		ArrayList<Parameter> lesione_osteomuscolari		= ParameterUtils.list( context.getRequest(), "lesione_osteomuscolari_" );

		ArrayList<Parameter> lesione_generici		= ParameterUtils.list( context.getRequest(), "lesione_generici_" );
		ArrayList<Parameter> lesione_altro		= ParameterUtils.list( context.getRequest(), "lesione_altro_" );

		ArrayList<Integer> idPatologie = null;

		for(int i = 0; i < organo.size(); i++)
		{
			Parameter tipo = organo.get( i );


			if( !"-1".equalsIgnoreCase( tipo.getValore() ) )
			{
				//				int max_id = -1;
				idPatologie = new ArrayList<Integer>();

				for( String val : lesione_milza.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_cuore.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_polmoni.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}
				
				for( String val : lesione_visceri.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_fegato.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_rene.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_mammella.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_apparato_genitale.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_stomaco.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_intestino.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_osteomuscolari.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_generici.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_altro.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				Organi temp = null;
				for(int idPatologia : idPatologie){
					temp = new Organi();

					temp.setId_capo			( c.getId() );
					//temp.setLcso_patologia	( Integer.parseInt( patologia.get( i ).getValore() ) );
					temp.setLcso_patologia	( idPatologia );

					String patologiaAltro = context.getRequest().getParameter("lcso_patologiaaltro_" + i);
					if(patologiaAltro != null && !patologiaAltro.equals("")){
						temp.setLcso_patologia_altro(patologiaAltro);
					}
					//giuseppe

					//temp.setLcso_stadio		( Integer.parseInt( stadio.get( i ).getValore() ) );
					temp.setLcso_organo		( Integer.parseInt( organo.get( i ).getValore() ) );
					temp.setId				( Integer.parseInt( ids.get( i ).getValore() ) );

					ret.add( temp );
				}

			}

		}

		return ret;
	}
	
	public ArrayList<Organi> loadOrganiRichiestaIstopatologico(ActionContext context, Connection db){
		ArrayList<Organi> ret = new ArrayList<Organi>();


		ArrayList<Parameter> topografia	= ParameterUtils.list( context.getRequest(), "istopatologico_topografia_" );
		//ArrayList<Parameter> stadio		= ParameterUtils.list( context.getRequest(), "lcso_stadio_" );
		ArrayList<Parameter> interessamento_altri_organi		= ParameterUtils.list( context.getRequest(), "istopatologico_interessamento_altri_organi_" );
		ArrayList<Parameter> id_organi		= ParameterUtils.list( context.getRequest(), "organi_id_" );

		
		for(int i = 0; i < id_organi.size(); i++)
		{
			Parameter idOrgano = (Parameter) id_organi.get(i);
			int idOrganoInt = Integer.parseInt(idOrgano.getValore().toString());


			if( idOrganoInt > 0 )
			{
				Organi thisOrgani = Organi.loadOrgano(idOrganoInt, db);
				thisOrgani.setIstopatologico_interessamento_altri_organi(context.getRequest().getParameter("istopatologico_interessamento_altri_organi_"+idOrganoInt));
				thisOrgani.setIstopatologico_topografia(context.getRequest().getParameter("istopatologico_topografia_"+idOrganoInt));
				ret.add(thisOrgani);
				
				
			}

		}

		return ret;
	}

	private Capo loadCapo(ActionContext context)
	{
		Capo c = new Capo();

		c.setAbb_data			( data		( "abb_data", context ) );
		c.setAbb_veterinario	( stringa	( "abb_veterinario", context ) );
		c.setAbb_veterinario_2	( stringa	( "abb_veterinario_2", context ) );
		c.setAbb_veterinario_3	( stringa	( "abb_veterinario_3", context ) );
		c.setAbb_motivo			( stringa	( "abb_motivo", context ) );
		c.setAbb_dist_carcassa	( booleano	( "abb_dist_carcassa", context ) );
		c.setArticolo17( booleano	( "articolo17", context ) );
		c.setCod_asl_azienda_prov(stringa	( "cod_asl_azienda_prov", context ));
		
		c.setRag_soc_azienda_prov(stringa	( "rag_soc_azienda_provenienza", context ));
		c.setDenominazione_asl_azienda_prov(stringa	( "denominazione_asl_azienda_prov", context ));
		c.setId_asl_azienda_prov(stringa	( "id_asl_azienda_prov", context ));
		
		c.setCasl_data						( data		( "casl_data", context ) );
		c.setCasl_info_richiesta			( stringa	( "casl_info_richiesta", context ) );
		c.setCasl_note_prevvedimento		( stringa	( "casl_note_prevvedimento", context ) );
		//		c.setCasl_motivo					( intero	( "casl_motivo", context ) );
		c.setCd_codice_azienda_provenienza(stringa("cd_codice_azienda_provenienza", context ));

		/*f( c.getCasl_data() != null )
		{*/
		c.setCasl_to_asl_origine			( booleano( "casl_to_asl_origine", context ) );
		c.setCasl_to_azienda_origine		( booleano( "casl_to_azienda_origine", context ) );
		c.setCasl_to_proprietario_animale	( booleano( "casl_to_proprietario_animale", context ) );
		c.setCasl_to_proprietario_macello	( booleano( "casl_to_proprietario_macello", context ) );
		c.setCd_categoria_rischio			(intero("cd_categoria_rischio",context) );
		c.setCasl_to_pif					( booleano( "casl_to_pif", context ) );
		c.setCasl_to_uvac					( booleano( "casl_to_uvac", context ) );
		c.setCasl_to_regione				( booleano( "casl_to_regione", context ) );
		c.setCasl_to_altro					( booleano( "casl_to_altro", context ) );
		if(c.isCasl_to_altro()){
			c.setCasl_to_altro_testo			( stringa( "casl_to_altro_testo", context ) );
		}
		else{
			c.setCasl_to_altro_testo("");
		}
		//}

		c.setCd_speditore				( stringa	( "cd_speditore", context ) );
		c.setCd_codice_speditore		( stringa	( "cd_codice_speditore", context ) );
		c.setCd_id_speditore			( intero	( "cd_id_speditore", context ) );
		c.setCd_codice_azienda			( stringa	( "cd_codice_azienda", context ) );
		c.setCd_data_nascita			( data		( "cd_data_nascita", context ) );
		c.setCd_id_razza				( intero	( "cd_id_razza", context ) );
		c.setCd_razza_altro				( stringa	( "cd_razza_altro", context ) );
		c.setCd_info_catena_alimentare	( booleano	( "cd_info_catena_alimentare", context ) );
		c.setCd_macellazione_differita	( intero	( "cd_macellazione_differita", context ) );
		c.setCd_maschio					( BooleanoPrimitive	( "cd_maschio", context ) );
		c.setCd_matricola				( stringa	( "cd_matricola", context ) );
		c.setCd_prov_stato_comunitario	( booleano	( "cd_prov_stato_comunitario", context ) );
		c.setCd_provenienza_stato		( intero	( "cd_provenienza_stato", context ) );
		c.setCd_provenienza_regione		( intero	( "cd_provenienza_regione", context ) );
		c.setCd_provenienza_comune		( stringa	( "cd_provenienza_comune", context ) );
		c.setCd_specie					( intero	( "cd_specie", context ) );
		c.setCd_categoria_bovina        ( intero	( "cd_categoria_bovina", context ));
		c.setCd_categoria_bufalina      ( intero	( "cd_categoria_bufalina", context ));
		c.setCd_asl						( intero	( "cd_asl", context ) );
		c.setCd_vincolo_sanitario		( booleano	( "cd_vincolo_sanitario", context ) );
		c.setCd_vincolo_sanitario_motivo( stringa	( "cd_vincolo_sanitario_motivo", context ) );
		//new Flusso 173 rq.5
		c.setCd_vincolo_sanitario_piano ( booleano	( "cd_vincolo_sanitario_piano", context ) );
		c.setCd_piano_risanamento       ( intero	( "cd_piano_risanamento", context ) );
		
		//new Flusso 173 rq 6
		c.setCd_qualifica_sanitaria_brc ( booleano	( "cd_qualifica_sanitaria_brc", context ) );
		c.setCd_qualifica_sanitaria_tbc ( booleano	( "cd_qualifica_sanitaria_tbc", context ) );
		c.setCd_qualifica_brc           ( intero	( "cd_qualifica_brc", context ) );
		c.setCd_qualifica_tbc           ( intero	( "cd_qualifica_tbc", context ) );
		
		c.setCd_mod4					( stringa	( "cd_mod4", context ) );
		c.setCd_data_mod4				( data		( "cd_data_mod4" , context ) );
		c.setCd_data_arrivo_macello		( data		( "cd_data_arrivo_macello", context ) );
		c.setCd_seduta_macellazione		( intero	( "cd_seduta_macellazione", context ) );
		c.setMvam_destinazione_carcassa( stringa      ("mvam_destinazione_carcassa",context));
		//
		c.setCd_data_arrivo_macello_flag_dichiarata(booleano ("cd_data_arrivo_macello_flag_dichiarata", context));
		c.setCd_bse						( intero	( "cd_bse", context ) );
		c.setCd_veterinario_1			( stringa	( "cd_veterinario_1", context ) );
		c.setCd_veterinario_2			( stringa	( "cd_veterinario_2", context ) );
		c.setCd_veterinario_3			( stringa	( "cd_veterinario_3", context ) );
		c.setCd_note					( stringa	( "cd_note", context ) );
		c.setSolo_cd					( booleano	( "solo_cd", context ) );
		c.setManca_BSE_Nmesi			( booleano	( "manca_BSE_Nmesi", context ) );
		c.setCd_tipo_mezzo_trasporto	( stringa	( "cd_tipo_mezzo_trasporto", context ) );
		c.setCd_targa_mezzo_trasporto	( stringa	( "cd_targa_mezzo_trasporto", context ) );
		c.setCd_trasporto_superiore8ore	( booleano	( "cd_trasporto_superiore8ore", context ) );
		c.setCd_info_azienda_provenienza(stringa ("cd_info_azienda_provenienza",context));
		c.setBse_data_prelievo			( data		( "bse_data_prelievo", context ) );
		c.setBse_data_ricezione_esito	( data		( "bse_data_ricezione_esito", context ) );
		c.setBse_esito					( stringa	( "bse_esito", context ) );
		c.setBse_note					( stringa	( "bse_note", context ) );

		c.setId			( intero( "id", context ) );
		c.setId_macello	( intero( "id_macello", context ) );

		c.setLcpr_data_prevista_liber	( data		( "lcpr_data_prevista_liber", context ) );
		c.setLcpr_data_effettiva_liber	( data		( "lcpr_data_effettiva_liber", context ) );

		c.setLcso_data		( data		( "lcso_data", context ) );

		c.setMac_progressivo	( intero	( "mac_progressivo", context ) );
		c.setProgressivo_macellazione	( intero	( "progressivo_macellazione", context ) );
		c.setMac_tipo			( intero	( "mac_tipo", context ) );

		c.setMavam_data		( data		( "mavam_data", context ) );
		c.setMavam_luogo	( intero	( "mavam_luogo", context ) );
		c.setMavam_motivo	( stringa	( "mavam_motivo", context ) );
		c.setMavam_note		( stringa	( "mavam_note", context ) );
		c.setMavam_descrizione_luogo_verifica(stringa	( "mavam_descrizione_luogo_verifica", context ) );
		c.setMavam_impianto_termodistruzione( stringa( "mavam_impianto_termodistruzione", context ) );
		c.setMavam_to_asl_origine			( booleano( "mavam_to_asl_origine", context ) );
		c.setMavam_to_azienda_origine		( booleano( "mavam_to_azienda_origine", context ) );
		c.setMavam_to_proprietario_animale	( booleano( "mavam_to_proprietario_animale", context ) );
		c.setMavam_to_proprietario_macello	( booleano( "mavam_to_proprietario_macello", context ) );

		c.setMavam_to_pif					( booleano( "mavam_to_pif", context ) );
		c.setMavam_to_uvac					( booleano( "mavam_to_uvac", context ) );
		c.setMavam_to_regione				( booleano( "mavam_to_regione", context ) );
		c.setMavam_to_altro					( booleano( "mavam_to_altro", context ) );
		if(c.isMavam_to_altro()){
			c.setMavam_to_altro_testo			( stringa( "mavam_to_altro_testo", context ) );
		}
		else{
			c.setMavam_to_altro_testo("");
		}

		c.setRca_data( data		( "rca_data", context ) );
		c.setRca_note( stringa	( "rca_note", context ) );

		c.setSeqa_data			( data		( "seqa_data", context ) );
		c.setSeqa_data_sblocco	( data		( "seqa_data_sblocco", context ) );
		c.setSeqa_destinazione_allo_sblocco( intero( "seqa_destinazione_allo_sblocco", context ) );

		c.setVam_data			( data		( "vam_data", context ) );
		c.setVam_provvedimenti	( intero	( "vam_provvedimenti", context ) );
		c.setVam_provvedimenti_note	( stringa	( "vam_provvedimenti_note", context ) );
		c.setVam_esito			( stringa	( "vam_esito", context ) );
		c.setVam_to_asl_origine			( booleano( "vam_to_asl_origine", context ) );
		c.setVam_to_azienda_origine		( booleano( "vam_to_azienda_origine", context ) );
		c.setVam_to_proprietario_animale	( booleano( "vam_to_proprietario_animale", context ) );
		c.setVam_to_proprietario_macello	( booleano( "vam_to_proprietario_macello", context ) );

		c.setVam_to_pif					( booleano( "vam_to_pif", context ) );
		c.setVam_to_uvac					( booleano( "vam_to_uvac", context ) );
		c.setVam_to_regione				( booleano( "vam_to_regione", context ) );
		c.setVam_to_altro					( booleano( "vam_to_altro", context ) );
		if(c.isVam_to_altro()){
			c.setVam_to_altro_testo			( stringa( "vam_to_altro_testo", context ) );
		}
		else{
			c.setVam_to_altro_testo("");
		}


		c.setVpm_causa_patologia		( stringa	( "vpm_causa_patologia", context ) );
		c.setVpm_data					( data		( "vpm_data", context ) );
		c.setVpm_esito					( intero	( "vpm_esito", context ) );
		c.setVpm_data_esito				( data		( "vpm_data_esito", context ) );		// Inserito da Alberto Campanile
		c.setVpm_note					( stringa	( "vpm_note", context ) );
		c.setVpm_veterinario			( stringa	( "vpm_veterinario", context ) );
		c.setVpm_veterinario_2			( stringa	( "vpm_veterinario_2", context ) );
		c.setVpm_veterinario_3			( stringa	( "vpm_veterinario_3", context ) );

		c.setDestinatario_1_id			( intero	( "destinatario_1_id", context ) );
		c.setDestinatario_1_in_regione	( booleano	( "destinatario_1_in_regione", context ) );
		c.setDestinatario_1_nome		( stringa	( "destinatario_1_nome", context ) );
		c.setDestinatario_2_id			( intero	( "destinatario_2_id", context ) );
		c.setDestinatario_2_in_regione	( booleano	( "destinatario_2_in_regione", context ) );
		c.setDestinatario_2_nome		( stringa	( "destinatario_2_nome", context ) );
		
		
		c.setDestinatario_3_id			( intero	( "destinatario_3_id", context ) );
		c.setDestinatario_3_in_regione	( booleano	( "destinatario_3_in_regione", context ) );
		c.setDestinatario_3_nome		( stringa	( "destinatario_3_nome", context ) );
		c.setDestinatario_4_id			( intero	( "destinatario_4_id", context ) );
		c.setDestinatario_4_in_regione	( booleano	( "destinatario_4_in_regione", context ) );
		c.setDestinatario_4_nome		( stringa	( "destinatario_4_nome", context ) );

		if( c.getDestinatario_1_id() <= 0 && c.getDestinatario_2_id() > 0 )
		{
			c.setDestinatario_1_id( c.getDestinatario_2_id() );
			c.setDestinatario_1_in_regione( c.isDestinatario_2_in_regione() );
			c.setDestinatario_1_nome( c.getDestinatario_2_nome() );
			c.setDestinatario_2_id( -1 );
			c.setDestinatario_2_in_regione( true );
			c.setDestinatario_2_nome( "" );
			
			
		}

		c.setModified_by( this.getUserId( context ) );
		
		setCategoria(c);

		return c;
	}


	private void setCategoria(Capo c)
	{
		
		c.getCd_data_nascita();
		
		
		if (c.getCd_specie()==1 )
		{
			if (c.getMavam_data()!= null && ! "".equals(c.getMavam_data())  && c.getMavam_luogo()>0 && ! "".equals(c.getMavam_luogo()))
			{
				c.setCd_categoria_rischio(5);
			}
			else
			{
				if (c.getCd_macellazione_differita()>0)
				{
					// se il capo ha oltre 48 mesi di eta' categoria 3
				}
				else
				{
					// se 2.	[macellazione in emergenza] -  sopra 48 mesi d eta' 2
				}
			}
			

		}
		
	}
	private boolean booleano(String param, ActionContext context)
	{
		String temp = context.getParameter( param );
		return 
		"true"	.equalsIgnoreCase( temp ) 
		|| "ok"		.equalsIgnoreCase( temp ) 
		|| "si"		.equalsIgnoreCase( temp ) 	
		|| "yes"	.equalsIgnoreCase( temp )
		|| "on"		.equalsIgnoreCase( temp );
	}

	private int intero(String param, ActionContext context)
	{
		int ret = -1;
		String temp = context.getParameter( param );
		try
		{
			ret = Integer.parseInt( temp );
		}
		catch (Exception e)
		{
			return -1 ;
		}
		return ret;
	}


	private String stringa(String param, ActionContext context)
	{
		String temp = context.getParameter( param );
		return (temp == null) ? (temp) : (temp.trim());
	}

	private Timestamp data(String param, ActionContext context)
	{
		try
		{
			return new Timestamp( sdf.parse( context.getParameter( param ) ).getTime() );
		}
		catch (Exception e)
		{
			return null;
		}
	}


	private void caricaLookup(ActionContext context)
	{
		caricaLookup(context, true);
	}

	private void caricaLookup(ActionContext context, boolean select)
	{
		Hashtable<String, String> lu = new Hashtable<String, String>();
		lu.put( "Nazioni",					"m_lookup_nazioni" );
		lu.put( "Regioni",					"m_lookup_regioni" );
		lu.put( "Razze",					"razze_bovini" );
		lu.put( "Specie",					"m_lookup_specie" );
		lu.put( "CategorieBovine",			"m_lookup_specie_categorie_bovine" );
		lu.put( "CategorieBufaline",		"m_lookup_specie_categorie_bufaline" );
		lu.put( "PianiRisanamento",			"m_lookup_piani_risanamento" );
		lu.put( "TipiMacellazione",			"m_lookup_tipi_macellazione" );
		lu.put( "Patologie",				"m_lookup_patologie" );
		lu.put( "PatologieOrgani",			"m_lookup_patologie_organi" );
		lu.put( "TipiEsame",				"m_lookup_tipi_esame" );
		lu.put( "Azioni",					"m_lookup_azioni" );
		lu.put( "Stadi",					"m_lookup_stadi" );
		lu.put( "Organi",					"m_lookup_organi" );
		lu.put( "TipiAnalisi",				"m_lookup_tipo_analisi" );
		lu.put( "TipiNonConformita",		"m_lookup_tipi_non_conformita" );
		lu.put( "Veterinari",				"veterinari_view" );
		lu.put( "EsitiVpm", 				"m_lookup_esiti_vpm" );
		lu.put( "Matrici", 					"m_lookup_matrici" );
		lu.put( "ASL",						"lookup_site_id" );
		lu.put( "LuoghiVerifica",			"m_lookup_luoghi_verifica" );
		lu.put( "Molecole",					"m_lookup_molecole" );
		lu.put( "MotiviASL",				"m_lookup_motivi_comunicazioni_asl" );
		lu.put( "ProvvedimentiVAM",			"m_lookup_provvedimenti_vam" );
		lu.put( "look_ProvvedimentiCASL",	"m_lookup_provvedimenti_casl" );
		lu.put( "BseList",					"m_lookup_bse" );
		lu.put( "EsitiCampioni",			"m_lookup_campioni_esiti" );
		lu.put( "MotiviCampioni",			"m_lookup_campioni_motivi" );
		lu.put( "MolecolePNR",  			"m_lookup_molecole_pnr" );
		lu.put( "MolecoleBatteriologico",	"m_lookup_molecole_batteriologico" );
		lu.put( "MolecoleParassitologico", 	"m_lookup_molecole_parassitologico" );

		lu.put( "lookup_lesione_milza", 	"m_lookup_lesione_milza" );
		lu.put( "lookup_lesione_cuore", 	"m_lookup_lesione_cuore" );
		lu.put( "lookup_lesione_polmoni", 	"m_lookup_lesione_polmoni" );
		lu.put( "lookup_lesione_visceri", 	"m_lookup_lesione_visceri" );
		lu.put( "lookup_lesione_fegato", 	"m_lookup_lesione_fegato" );
		lu.put( "lookup_lesione_rene", 		"m_lookup_lesione_rene" );
		lu.put( "lookup_lesione_mammella",	"m_lookup_lesione_mammella" );
		lu.put( "lookup_lesione_stomaco", 	"m_lookup_lesione_stomaco" );
		lu.put( "lookup_lesione_intestino", "m_lookup_lesione_intestino" );
		lu.put( "lookup_lesione_apparato_genitale",	"m_lookup_lesione_apparato_genitale" );
		lu.put( "lookup_lesione_osteomuscolari", 	"m_lookup_lesione_osteomuscolari" );
		lu.put( "lookup_lesione_generici", 	"m_lookup_lesione_generici" );
		lu.put( "lookup_lesione_altro", 	"m_lookup_lesione_altro" );
		
		lu.put( "MatriciTamponi", 	"lookup_tamponi" );
		lu.put( "AnalisiTamponi", 	"lookup_ricerca_tamponi_macelli" );
		lu.put( "PianiMonitoraggio", 	"lookup_piano_monitoraggio" );
		
		//lookup istopatologico
		lu.put( "listaDestinatariRichiestaIstopatologico", 	"lookup_istopatologico_clinica_destinataria" );
		lu.put( "lookup_alimentazione", 	"lookup_alimentazione" );
		lu.put( "lookup_habitat", 	"lookup_habitat" );
		lu.put( "lookup_esame_istopatologico_tipo_diagnosi", 	"lookup_esame_istopatologico_tipo_diagnosi" );
		lu.put( "lookup_associazione_classificazione_tabella_lookup", 	"lookup_associazione_classificazione_tabella_lookup" );
		lu.put("lookup_who_umana", "lookup_esame_istopatologico_who_umana");
		



		Enumeration<String> e = lu.keys();
		Connection db = null;
		SintesisStabilimento org = null;

		try
		{
			db = this.getConnection( context );
			while( e.hasMoreElements() )
			{
				String key		= e.nextElement();
				String value	= lu.get( key );
				LookupList list	= new LookupList( db, value );
				if(select)
				{
					list.addItem( -1, " -- SELEZIONA -- " );
				}



                if (("lookup_associazione_classificazione_tabella_lookup").equals(value)){
                	list.setJsEvent("onChange = \"javascript:caricaClassificazioneLesione(this);\"");
                }
                
                if (("lookup_esame_istopatologico_tipo_diagnosi").equals(value)){
                	list.setJsEvent("onChange = \"javascript:specificaEsito(this);\"");
                }
                
                
                
				context.getRequest().setAttribute( key, list );
			}

			LookupList vets = (LookupList) context.getRequest().getAttribute( "Veterinari" );
			if( this.getUserSiteId(context) > 0 )
			{
				vets.removeItemfromLookup( db, "veterinari_view", " asl <> " + this.getUserSiteId(context) );
			}

			String tempaltId = context.getRequest().getParameter("altId");
			if(tempaltId == null)
			{
				tempaltId = (String) context.getRequest().getAttribute("altId");
			}
			
			
			if (tempaltId == null){
				tempaltId = (String) context.getRequest().getAttribute("AltId");
			}
			int tempid = Integer.parseInt(tempaltId);
			org = new SintesisStabilimento( db, tempid, true );
			context.getRequest().setAttribute( "OrgDetails", org );

			// Creazione Lookup per tipologia non conformita' divisa in due gruppi
			SystemStatus thisSystem = this.getSystemStatus(context);
			LookupList llTipoNonConformita = new LookupList();
			llTipoNonConformita.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_normale"));
			llTipoNonConformita.addGroup("Sospetta o accertata presenza malattie infettive");
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_malattie_infettive"));
			context.getRequest().setAttribute("TipiNonConformita_Gruppo", llTipoNonConformita);

			UserBean user=(UserBean)context.getSession().getAttribute("User");
			HashMap<String,ArrayList<Contact>> listaUtentiAttiviV =ControlliUfficialiUtil.getUtentiAttiviperaslVeterinari(db, user.getSiteId());
			context.getRequest().setAttribute("listaVeterinari", listaUtentiAttiviV);

		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}

	private void caricaLookupCapo(ActionContext context)
	{
		caricaLookupCapo(context, true);
	}

	private void caricaLookupCapo(ActionContext context, boolean select)
	{
		Hashtable<String, String> lu = new Hashtable<String, String>();
		lu.put( "Nazioni",					"m_lookup_nazioni" );
		lu.put( "Regioni",					"m_lookup_regioni" );
		lu.put( "Razze",					"razze_bovini" );
		lu.put( "Specie",					"m_lookup_specie" );
		lu.put( "CategorieBovine",			"m_lookup_specie_categorie_bovine" );
		lu.put( "CategorieBufaline",		"m_lookup_specie_categorie_bufaline" );
		lu.put( "PianiRisanamento",			"m_lookup_piani_risanamento" );
		lu.put( "TipiMacellazione",			"m_lookup_tipi_macellazione" );
		lu.put( "Patologie",				"m_lookup_patologie" );
		lu.put( "PatologieOrgani",			"m_lookup_patologie_organi" );
		lu.put( "TipiEsame",				"m_lookup_tipi_esame" );
		lu.put( "Azioni",					"m_lookup_azioni" );
		lu.put( "Stadi",					"m_lookup_stadi" );
		lu.put( "Organi",					"m_lookup_organi" );
		lu.put( "TipiAnalisi",				"m_lookup_tipo_analisi" );
		lu.put( "TipiNonConformita",		"m_lookup_tipi_non_conformita" );
		lu.put( "Veterinari",				"veterinari_view" );
		lu.put( "EsitiVpm", 				"m_lookup_esiti_vpm" );
		lu.put( "Matrici", 					"m_lookup_matrici" );
		lu.put( "ASL",						"lookup_site_id" );
		lu.put( "LuoghiVerifica",			"m_lookup_luoghi_verifica" );
		lu.put( "Molecole",					"m_lookup_molecole" );
		lu.put( "MotiviASL",				"m_lookup_motivi_comunicazioni_asl" );
		lu.put( "ProvvedimentiVAM",			"m_lookup_provvedimenti_vam" );
		lu.put( "look_ProvvedimentiCASL",	"m_lookup_provvedimenti_casl" );
		lu.put( "BseList",					"m_lookup_bse" );
		lu.put( "EsitiCampioni",			"m_lookup_campioni_esiti" );
		lu.put( "MotiviCampioni",			"m_lookup_campioni_motivi" );
		lu.put( "MolecolePNR",  			"m_lookup_molecole_pnr" );
		lu.put( "MolecoleBatteriologico",	"m_lookup_molecole_batteriologico" );
		lu.put( "MolecoleParassitologico", 	"m_lookup_molecole_parassitologico" );

		lu.put( "lookup_lesione_milza", 	"m_lookup_lesione_milza" );
		lu.put( "lookup_lesione_cuore", 	"m_lookup_lesione_cuore" );
		lu.put( "lookup_lesione_polmoni", 	"m_lookup_lesione_polmoni" );
		lu.put( "lookup_lesione_visceri", 	"m_lookup_lesione_visceri" );
		lu.put( "lookup_lesione_fegato", 	"m_lookup_lesione_fegato" );
		lu.put( "lookup_lesione_rene", 		"m_lookup_lesione_rene" );
		lu.put( "lookup_lesione_mammella",	"m_lookup_lesione_mammella" );
		lu.put( "lookup_lesione_stomaco", 	"m_lookup_lesione_stomaco" );
		lu.put( "lookup_lesione_intestino", "m_lookup_lesione_intestino" );
		lu.put( "lookup_lesione_apparato_genitale",	"m_lookup_lesione_apparato_genitale" );
		lu.put( "lookup_lesione_osteomuscolari", 	"m_lookup_lesione_osteomuscolari" );
		lu.put( "lookup_lesione_generici", 	"m_lookup_lesione_generici" );
		lu.put( "lookup_lesione_altro", 	"m_lookup_lesione_altro" );
		
		lu.put( "MatriciTamponi", 	"lookup_tamponi" );
		lu.put( "AnalisiTamponi", 	"lookup_ricerca_tamponi_macelli" );
		lu.put( "PianiMonitoraggio", 	"lookup_piano_monitoraggio" );



		Enumeration<String> e = lu.keys();
		Connection db = null;
		SintesisStabilimento org = null;

		try
		{
			db = this.getConnection( context );
			while( e.hasMoreElements() )
			{
				String key		= e.nextElement();
				String value	= lu.get( key );
				LookupList list	= new LookupList( db, value );
				if(select)
				{
					list.addItem( -1, " -- SELEZIONA -- " );
				}
				if (key.equals("Specie")){
					list.removeElementByValue("Ovini");
					list.removeElementByValue("Caprini");
				}




				context.getRequest().setAttribute( key, list );
			}

			LookupList vets = (LookupList) context.getRequest().getAttribute( "Veterinari" );
			if( this.getUserSiteId(context) > 0 )
			{
				vets.removeItemfromLookup( db, "veterinari_view", " asl <> " + this.getUserSiteId(context) );
			}

			String tempaltId = context.getRequest().getParameter("altId");
			if(tempaltId == null)
			{
				tempaltId = (String) context.getRequest().getAttribute("altId");
			}
			int tempid = Integer.parseInt(tempaltId);
			org = new SintesisStabilimento( db, tempid, true );
			context.getRequest().setAttribute( "OrgDetails", org );

			// Creazione Lookup per tipologia non conformita' divisa in due gruppi
			SystemStatus thisSystem = this.getSystemStatus(context);
			LookupList llTipoNonConformita = new LookupList();
			llTipoNonConformita.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_normale"));
			llTipoNonConformita.addGroup("Sospetta o accertata presenza malattie infettive");
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_malattie_infettive"));
			context.getRequest().setAttribute("TipiNonConformita_Gruppo", llTipoNonConformita);

			UserBean user=(UserBean)context.getSession().getAttribute("User");
			HashMap<String,ArrayList<Contact>> listaUtentiAttiviV =ControlliUfficialiUtil.getUtentiAttiviperaslVeterinari(db, user.getSiteId());
			context.getRequest().setAttribute("listaVeterinari", listaUtentiAttiviV);

		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}

	

	private void getConfigTipo(ActionContext context)
	{
		if(context.getSession().getAttribute("configTipo")!=null){
			Object o = context.getSession().getAttribute("configTipo");
			
			try {
				configTipo = (ConfigTipo) context.getSession().getAttribute("configTipo");
				}
			catch (Exception e){
				
				try {
					org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo configTipo2 = (org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo) context.getSession().getAttribute("configTipo");
					final int tipo = configTipo2.getIdTipo();
					configTipo = new ConfigTipo(tipo);
					context.getSession().setAttribute("configTipo", configTipo);
					}
				catch (Exception e2){
					org.aspcfs.modules.macellazioninew.utils.ConfigTipo configTipo3 = (org.aspcfs.modules.macellazioninew.utils.ConfigTipo) context.getSession().getAttribute("configTipo");
					final int tipo = configTipo3.getIdTipo();
					configTipo = new ConfigTipo(tipo);
					context.getSession().setAttribute("configTipo", configTipo);
				}
				
				
				
			}
	}
	}

	
	public String executeCommandRichiestaIstopatologico(ActionContext context){

		String ret = "RichiestaIstopatologicoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			Capo c	= Capo.load( context.getParameter( "id" ), db );
			SintesisStabilimento org = new SintesisStabilimento( db, c.getId_macello(), true );

			
			ArrayList<Organi>   			organiTumorali	 		= Capo.checkOrganiTumorali(db, c.getId());

			context.getRequest().setAttribute( "Capo", c );
			context.getRequest().setAttribute( "OrgDetails", org );
			context.getRequest().setAttribute("altId",c.getId_macello()+"" );

			context.getRequest().setAttribute( "OrganiList", organiTumorali );


		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		caricaLookup(context, false);

		return ret;
	
		
	}
	
	
	public String executeCommandSaveIstopatologico(ActionContext context){

		String ret = "SaveIstopatologicoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		
		Capo capo = new Capo();
		RichiestaIstopatologico thisRichiesta = new RichiestaIstopatologico();
		
		try
		{		
			db		= this.getConnection( context );
			capo = Capo.load(context.getRequest().getParameter("idCapo"), db);
			thisRichiesta = (RichiestaIstopatologico) context.getRequest().getAttribute(
				"RichiestaIstopatologico");
			thisRichiesta.setUtenteInserimento(getUserId(context));
			thisRichiesta.setUtenteModifica(getUserId(context));
			ArrayList<Organi> organi = loadOrganiRichiestaIstopatologico(context, db);
			thisRichiesta.setLista_organi_richiesta(organi);
			thisRichiesta.store(db,context);
			
			
			//Aggiornamento informazioni istopatologico sul capo
			capo.setIstopatologico_richiesta(true);
			capo.setIstopatologico_data_richiesta(thisRichiesta.getDataInserimento());
			capo.setIstopatologico_id(thisRichiesta.getId());
			
			ArrayList<Campione> campioni = Campione.load			( capo.getId(), db );
			
	
			
			capo.update(db, campioni);


		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		//caricaLookup(context, false);
		
		String dataMacellazione = (DateUtils.timestamp2string(capo.getVpm_data()));
		//	sdf.format(new Date(richiestaIstopatologico.getCapo().getVpm_data()));%>
		dataMacellazione = dataMacellazione + "-"+ capo.getCd_seduta_macellazione();


		context.getRequest().setAttribute("altId", capo.getId_macello()+"");
		context.getRequest().setAttribute("idIstopatologico", thisRichiesta.getId());
		context.getRequest().setAttribute("comboSessioniMacellazione", dataMacellazione);
		return executeCommandList( context );
	
		
	}
	
	
	public String executeCommandRichiestaIstopatologicoStampaModelloInvioCampioni(ActionContext context){
		
		String ret = "ModelloInvioCampioniOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		SintesisStabilimento org = null;
		
		try{
			
			
			String idIstopatologico = (String) context.getRequest().getParameter("idIstopatologico");
			int idIstopatologicoInt = -1;
			
			
			if (idIstopatologico == null || ("").equals(idIstopatologico)){
				idIstopatologico = (String) context.getRequest().getAttribute("idIstopatologico");
			}
			
			if (idIstopatologico != null && !("").equals(idIstopatologico)){
				idIstopatologicoInt = Integer.parseInt(idIstopatologico);
			}
			
			if ( idIstopatologicoInt > 0 ){
				db		= this.getConnection( context );
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new SintesisStabilimento( db, istopatologico.getCapo().getId_macello(), true );
			}
			
			
		}catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("altId", istopatologico.getCapo().getId_macello()+"" );
		caricaLookup(context, false);
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);
		
		return ret;
		
	}
	
	
	public String executeCommandPrepareInserisciEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "PrepareEsitoRichiestaIstopatologicoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		SintesisStabilimento org = null;
		
		try
		{			
			String idIstopatologico = (String) context.getRequest().getParameter("idIstopatologico");
			int idIstopatologicoInt = -1;
			
			
			if (idIstopatologico == null || ("").equals(idIstopatologico)){
				idIstopatologico = (String) context.getRequest().getAttribute("idIstopatologico");
			}
			
			if (idIstopatologico != null && !("").equals(idIstopatologico)){
				idIstopatologicoInt = Integer.parseInt(idIstopatologico);
			}
			
			if ( idIstopatologicoInt > 0 ){
				db		= this.getConnection( context );
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new SintesisStabilimento( db, istopatologico.getCapo().getId_macello(), true );
			}
			
		
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("altId", istopatologico.getCapo().getId_macello() + "" );
		caricaLookup(context, false);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

		return ret;
	
		
	}
	
	
	public String executeCommandSaveEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "SaveEsitoRichiestaIstopatologicoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		SintesisStabilimento org = null;
		
		try
		{			
			String idIstopatologico = (String) context.getRequest().getParameter("idIstopatologico");
			int idIstopatologicoInt = -1;
			
			
			if (idIstopatologico == null || ("").equals(idIstopatologico)){
				idIstopatologico = (String) context.getRequest().getAttribute("idIstopatologico");
			}
			
			if (idIstopatologico != null && !("").equals(idIstopatologico)){
				idIstopatologicoInt = Integer.parseInt(idIstopatologico);
			}
			
			if ( idIstopatologicoInt > 0 ){
				db		= this.getConnection( context );
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
			}
			
			
			istopatologico.caricaEsitoIstopatologico(context);
			
			istopatologico.update(db);
			
			
			//AGGIORNAMENTO REGISTRO TUMORI
			if (istopatologico.isFlagEsitoTumorale())
				RegistroTumoriRemoteUtil.aggiuntiEsitoTumorale(istopatologico.getIdentificativoRichiesta(), db);
			
			
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new SintesisStabilimento ( db, istopatologico.getCapo().getId_macello(), true );
			}
			
		
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("altId", istopatologico.getCapo().getId_macello()+"" );

		caricaLookup(context, false);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

		return ret;
	
		
	}
	
	public String executeCommandVisualizzaEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "VisualizzaEsitoRichiestaIstopatologicoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		SintesisStabilimento org = null;
		
		try
		{			
			String idIstopatologico = (String) context.getRequest().getParameter("idIstopatologico");
			int idIstopatologicoInt = -1;
			
			
			if (idIstopatologico == null || ("").equals(idIstopatologico)){
				idIstopatologico = (String) context.getRequest().getAttribute("idIstopatologico");
			}
			
			if (idIstopatologico != null && !("").equals(idIstopatologico)){
				idIstopatologicoInt = Integer.parseInt(idIstopatologico);
			}
			
			if ( idIstopatologicoInt > 0 ){
				db		= this.getConnection( context );
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new SintesisStabilimento( db, istopatologico.getCapo().getId_macello(), true );
			}
			
		
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("altId", istopatologico.getCapo().getId_macello() + "" );
		caricaLookup(context, false);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

		return ret;
	
		
	}
		
	
	public String executeCommandPrepareCancellazioneCapo(ActionContext context){
		
		String idCapo =context.getRequest().getParameter("idCapo");
		
		Connection db = null;
		
		String ret = "";
		String error="";
		Capo c = new Capo();
		
		try {
			db = this.getConnection( context );
			
			c = Capo.load(idCapo, db);
			
		
			
			if (c.isCancellabile()){
				ret = "prepareCancellazioneCapoOK";
			}
			else {
				error = "Articolo 17 stampato.";
				context.getRequest().setAttribute("Error", error);
				ret = "cancellazioneCapoError";

			}
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Capo", c);
		return ret;
	}

public String executeCommandCancellaCapo(ActionContext context){
		String idCapo =context.getRequest().getParameter("idCapo");
		String note = context.getRequest().getParameter("note");
		Connection db = null;
		Capo c = new Capo();
		try {
			db = this.getConnection( context );
			c = Capo.load(idCapo, db);
			int userId =  getUserId(context);
			
			java.util.Date date= new java.util.Date();
			Timestamp current_timestamp = new Timestamp(date.getTime());
			 
			c.setDeleted_by(userId);
			c.setNote_cancellazione(note);
			c.setTrashed_date(current_timestamp);
			c.cancella(db);
			
			LogCancellazioneCapiPartite log = new LogCancellazioneCapiPartite();
			log.setIdCapo(c.getId());
			log.setDataOperazione(current_timestamp);
			log.setIdUtente(userId);
			log.setMatricola(c.getCd_matricola());
			log.setNote(note);
			log.setTipoOperazione("CANCELLAZIONE");
			log.insert(db);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Capo", c);
		return "cancellazioneCapoOK";
	}


public String executeCommandToLiberoConsumoMassivo(ActionContext context){
	
	if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-liberoconsumomassivo-add"))
	{
		return ("PermissionError");
	}
	
	int altId = -1;
	int tipoInt = -1;
	
	String altIdString = context.getParameter("altId");
	String tipoString = context.getParameter("tipo"); 	
	
	if (altIdString==null)
		altIdString = (String) context.getRequest().getAttribute("altId");
	if (tipoString==null)
		tipoString = (String) context.getRequest().getAttribute("tipo");
	
	try {tipoInt= Integer.parseInt(tipoString);} catch (Exception e){}
	try {altId= Integer.parseInt(altIdString);} catch (Exception e){}
	
	
	if (tipoInt>-1){
		final int tipo = tipoInt;
		context.getSession().setAttribute("configTipo", new ConfigTipo(tipo));
	}
	
	String			ret		= "toLiberoConsumoMassivoOK";
	ArrayList<Capo> capi = new ArrayList<Capo>();

	Connection		db		= null;
	SintesisStabilimento org = null;

	try
	{
		db = this.getConnection( context );
		org = new SintesisStabilimento( db, altId, true);
		capi = Capo.loadByStabilimentoCapiNonMacellati( altIdString, db );
			
	}
	catch (Exception e1)
	{
		context.getRequest().setAttribute("Error", e1);
		e1.printStackTrace();
	} 
	finally
	{
		this.freeConnection(context, db);
	
	}

	caricaLookup( context );
	context.getRequest().setAttribute( "OrgDetails", org );
	context.getRequest().setAttribute("listaCapi", capi);

	return ret;
}

public String executeCommandLiberoConsumoMassivo(ActionContext context){
	
	int altId = Integer.parseInt(context.getRequest().getParameter("id_macello"));
	int sizeLista = Integer.parseInt(context.getRequest().getParameter("sizeLista"));
	
	int destinatario_1_id = intero( "destinatario_1_id", context );
	boolean destinatario_1_in_regione = booleano( "destinatario_1_in_regione", context );
	String destinatario_1_nome = stringa( "destinatario_1_nome", context );
	
	int destinatario_2_id = intero( "destinatario_2_id", context );
	boolean destinatario_2_in_regione = booleano( "destinatario_2_in_regione", context );
	String destinatario_2_nome = stringa( "destinatario_2_nome", context );
	
	int destinatario_3_id = intero( "destinatario_3_id", context );
	boolean destinatario_3_in_regione = booleano( "destinatario_3_in_regione", context );
	String destinatario_3_nome = stringa( "destinatario_3_nome", context );
	
	int destinatario_4_id = intero( "destinatario_4_id", context );
	boolean destinatario_4_in_regione = booleano( "destinatario_4_in_regione", context );
	String destinatario_4_nome = stringa( "destinatario_4_nome", context );
	
	Timestamp dataOperazione = new Timestamp( System.currentTimeMillis());
		
	String messaggio ="";
	
	Connection db = null;
	
	for (int i = 0; i< sizeLista; i++){
		boolean macellabile = true;
		String cb = context.getRequest().getParameter("cb_"+i);
		if (cb!=null && cb.equals("on")){
			int idCapo = Integer.parseInt(context.getRequest().getParameter("id_"+i));
			
			try {
				db = this.getConnection(context);
				Capo c = new Capo();
				c = Capo.load(String.valueOf(idCapo), db);
			
				if (c.getVpm_data()==null){
					macellabile = false;
					messaggio+="<font color=\"red\">Il capo "+c.getCd_matricola()+" non risulta assegnato a nessuna sessione.</font>";
				}
				
				if (!c.getStato_macellazione().toLowerCase().contains("incompleto")){
					macellabile = false;
					messaggio+="<font color=\"red\">Il capo "+c.getCd_matricola()+" non risulta in uno stato macellabile.</font>";
				}
				
				if (macellabile){
				c.setStato_macellazione("OK.");
				c.setVpm_esito(1);
				
				c.setDestinatario_1_id(destinatario_1_id);
				c.setDestinatario_2_id(destinatario_2_id);
				c.setDestinatario_3_id(destinatario_3_id);
				c.setDestinatario_4_id(destinatario_4_id);
				
				c.setDestinatario_1_in_regione(destinatario_1_in_regione);
				c.setDestinatario_2_in_regione(destinatario_2_in_regione);
				c.setDestinatario_3_in_regione(destinatario_3_in_regione);
				c.setDestinatario_4_in_regione(destinatario_4_in_regione);
				
				c.setDestinatario_1_nome(destinatario_1_nome);
				c.setDestinatario_2_nome(destinatario_2_nome);
				c.setDestinatario_3_nome(destinatario_3_nome);
				c.setDestinatario_4_nome(destinatario_4_nome);
			
				c.setModified_by(getUserId(context));
				c.setModified(dataOperazione);
				c.setVpm_note("Macellazione avvenuta tramite libero consumo massivo in data "+new SimpleDateFormat("dd/MM/yyyy").format(dataOperazione));
				c.setSolo_cd(false);
				c.aggiornaLiberoConsumo(db);
				messaggio += "<b><font color=\"green\">Capo "+c.getCd_matricola()+" correttamente macellato in libero consumo alla data "+ new SimpleDateFormat("dd/MM/yyyy").format(c.getVpm_data())+".</font></b><br/>";
				}
				
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally {
				this.freeConnection(context, db);
			}
			
		}
		
	}
	
	context.getRequest().setAttribute("messaggioLiberoConsumo", messaggio);
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	context.getRequest().setAttribute("tipo", "1");
	context.getRequest().setAttribute("comboSessioniMacellazione", "-1");
	return executeCommandList(context);

}

public String executeCommandStoricoLiberoConsumoMassivo(ActionContext context){
	
	Connection db = null;
	ArrayList<String> listaStorico = new ArrayList<String>();
	
			try {
				db = this.getConnection(context);
				
				PreparedStatement pst = db.prepareStatement("select log.*, c.cd_matricola, c.id_macello from m_capi_libero_consumo_massivo_log log left join m_capi c on log.id_capo = c.id order by log.data_operazione desc");
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					int idCapo = rs.getInt("id_capo");
					int idUtente = rs.getInt("id_utente");
					String dataOperazione = rs.getString("data_operazione");
					String note = rs.getString("note");
					String matricola = rs.getString("cd_matricola");
					int idMacello = rs.getInt("id_macello");

					
					String riga = idCapo+";;"+idUtente+";;"+dataOperazione+";;"+note+";;"+matricola+";;"+idMacello;
					listaStorico.add(riga);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally {
				this.freeConnection(context, db);
			}
	
			context.getRequest().setAttribute("listaStorico", listaStorico);
			return "StoricoLiberoConsumoMassivoOK";

}

public String executeCommandLogCancellazioneCapiPartite(ActionContext context){
	
	Connection db = null;
	
	ArrayList<LogCancellazioneCapiPartite> listaCapiPartiteCancellati = new ArrayList<LogCancellazioneCapiPartite>();
	
	try {
		db = this.getConnection( context );
		listaCapiPartiteCancellati = LogCancellazioneCapiPartite.loadCapiPartiteCancellati(db);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("listaCapiPartiteCancellati", listaCapiPartiteCancellati);

	return "logCancellazioneCapiPartiteOK";
}

private Boolean BooleanoPrimitive(String param, ActionContext context)
{
    String temp = context.getParameter( param );
    
    if ("null".equals(temp))
        return null;
    
    return
    "true"    .equalsIgnoreCase( temp )
    || "ok"        .equalsIgnoreCase( temp )
    || "si"        .equalsIgnoreCase( temp )     
    || "yes"    .equalsIgnoreCase( temp )
    || "on"        .equalsIgnoreCase( temp );
}

}
