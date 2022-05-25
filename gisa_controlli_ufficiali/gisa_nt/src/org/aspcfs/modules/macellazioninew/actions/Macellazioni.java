/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninew.actions;
import it.izs.bdn.bean.CapoParzialeBean;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import org.apache.commons.beanutils.BeanUtils;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiMacelli;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioninew.base.Campione;
import org.aspcfs.modules.macellazioninew.base.Capo;
import org.aspcfs.modules.macellazioni.base.CapoAjax;
import org.aspcfs.modules.macellazioni.base.LogCancellazioneCapiPartite;
import org.aspcfs.modules.macellazioni.base.RegistroTumoriRemoteUtil;
import org.aspcfs.modules.macellazioninew.base.CategoriaRischio;
import org.aspcfs.modules.macellazioninew.base.RichiestaIstopatologico;
import org.aspcfs.modules.macellazioninew.base.BeanAjax;
import org.aspcfs.modules.macellazioninew.base.CapoLog;
import org.aspcfs.modules.macellazioninew.base.CapoLogDao;
import org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazioninew.base.ChiaveModuliMacelli;
import org.aspcfs.modules.macellazioninew.base.Esito;
import org.aspcfs.modules.macellazioninew.base.GenericBean;
import org.aspcfs.modules.macellazioninew.base.NonConformita;
import org.aspcfs.modules.macellazioninew.base.Organi;
import org.aspcfs.modules.macellazioninew.base.Partita;
import org.aspcfs.modules.macellazioninew.base.PartitaAjax;
import org.aspcfs.modules.macellazioninew.base.PartitaLog;
import org.aspcfs.modules.macellazioninew.base.PartitaLogDao;
import org.aspcfs.modules.macellazioninew.base.PartitaSeduta;
import org.aspcfs.modules.macellazioninew.base.PatologiaRilevata;
import org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazioninew.base.StampeModuli;
import org.aspcfs.modules.macellazioninew.base.StampeModuliDao;
import org.aspcfs.modules.macellazioninew.base.Tampone;
import org.aspcfs.modules.macellazioninew.base.Tipo;
import org.aspcfs.modules.macellazioninew.base.TipoRicerca;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninew.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninew.utils.ReflectionUtil;
import org.aspcfs.modules.speditori.base.OrganizationAddress;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.vigilanza.base.RuoliUtentiParser;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;
import org.bouncycastle.jce.provider.JDKKeyFactory.RSA;
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

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.returnsclause;
import com.darkhorseventures.framework.actions.ActionContext;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.zeroio.webutils.FileDownload;

public class Macellazioni extends CFSModule
{
	
	private static boolean enabledIstopatologico = false;

	
	GenericBean b, b_old, vecchioBean, nuovoBean = null;
	ArrayList<GenericBean> beans = null;
	PartitaSeduta p_seduta;
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
		getConfigTipo(context);
		if (context.getParameter("matricola")!=null && ! "".equals(context.getParameter("matricola")))
		{
			getConfigTipo(context);
			
			Connection db = null;
			try
			{
				db		= this.getConnection( context );
				b	    = GenericBean.loadByIdentificativo( context.getParameter( "matricola" ), db, configTipo );
			
				context.getRequest().setAttribute("orgId",b.getId_macello()+"" );
				Organization org = new Organization( db, b.getId_macello() );

				caricaLookup(context, false,configTipo);
				context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
				context.getRequest().setAttribute( "OrgDetails", org );
				return "SearchMatricolaResultOK" ;
				

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

	public String executeCommandToStampeModuli( ActionContext context )
	{
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-stampe-moduli-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		
		int idMacello = Integer.parseInt(context.getRequest().getParameter("orgId"));
		try {
			db = this.getConnection(context);

			HashMap<String, ArrayList<String>> stampe_date = new HashMap<String, ArrayList<String>>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Iterator<Object> key = org.aspcfs.modules.macellazioninew.actions.ApplicationProperties.getApplicationProperties().keySet().iterator();
			while (key.hasNext())
			{
				String kiave = (String) key.next();
				if(kiave.startsWith("GET_DATE"))
				{
					String select1 = org.aspcfs.modules.macellazioninew.actions.ApplicationProperties.getProperty(kiave);
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


			
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );
			context.getRequest().setAttribute("orgId", context.getParameter("orgId"));

		} catch (SQLException ex){
 			ex.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		


		return "ToStampeModuliOK";
	}


	public String executeCommandToLiberoConsumo( ActionContext context )
	{
		getConfigTipo(context);
		
		String ret = "ToLiberoConsumoOK";
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			b = loadBean(context, configTipo);
			
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
			String codiceAzienda = (String)ReflectionUtil.invocaMetodo(o, "getCd_codice_azienda_provenienza", null);
			Integer numOvini = null;
			Integer numCaprini = null;
			try
			{
				numOvini = (Integer)ReflectionUtil.invocaMetodo(o, "getCd_num_capi_ovini", null);
				numCaprini = (Integer)ReflectionUtil.invocaMetodo(o, "getCd_num_capi_caprini", null);
			}
			catch(NoSuchMethodException ex)
			{
				numOvini = 0;
				numCaprini = 0;
			}
			if(identificativo!=null && !identificativo.equals(""))
			{
				AjaxCalls ac = new AjaxCalls();
				//Se viene dalla pagina di modifica partita
				BeanAjax  ca = null;
				if(context.getRequest().getParameter("fromModifica")!=null && context.getRequest().getParameter("fromModifica").equals("si"))
					ca = ac.isCapoEsistenteUpdate(b.getId(),identificativo,b.getCd_codice_azienda_provenienza(),((Partita)b).getCd_num_capi_ovini(),((Partita)b).getCd_num_capi_caprini());
				else 
					ca = this.isCapoEsistente(identificativo, codiceAzienda, numOvini, numCaprini, configTipo);
				
				if( ca.isEsistente() ){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoNonInserito() );
					return executeCommandNuovoCapo( context );
				}
			}
			
			
			if (configTipo.hasSeduteMacellazione())
			{
				Iterator<PartitaSeduta> sedute = Partita.loadSeduteSmart(db, b.getId(), configTipo).iterator();
				int numCapiOviniSedute = 0;
				int numCapiCapriniSedute = 0;
				int numCapiOviniDestPartita_AltreSedute = 0;
				int numCapiCapriniDestPartita_AltreSedute = 0;
				int numCapiOviniMavamPartita_AltreSedute = 0;
				int numCapiCapriniMavamPartita_AltreSedute = 0;
				while(sedute.hasNext())
				{
					PartitaSeduta temp = sedute.next();
					Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
					int numCapiOviniEsitiSeduta = 0;
					int numCapiCapriniEsitiSeduta = 0;
					while(esitiSeduta.hasNext())
					{
						Esito esitoTemp = esitiSeduta.next();
						numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
						numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
					}
					numCapiOviniSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
					numCapiCapriniSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
					int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
					int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
					numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
					numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
					int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
					int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
					numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
					numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
				}
				context.getRequest().setAttribute( "numCapiOviniSedute", numCapiOviniSedute+"" );
				int numCapiOviniDestPartita = (((Partita)b)).getVam_num_capi_ovini();
				int numCapiCapriniDestPartita = (((Partita)b)).getVam_num_capi_caprini();
				numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestPartita;
				numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestPartita;
				
				context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
				context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );
				
				int numCapiOviniMavamPartita = (((Partita)b)).getMavam_num_capi_ovini();
				int numCapiCapriniMavamPartita = (((Partita)b)).getMavam_num_capi_caprini();
				numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamPartita;
				numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamPartita;
				
				context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
				context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			}
			
			
			Tampone 			tampone 			= null;
			ArrayList<Campione> 			campioni 			= null;
			
			
			if(configTipo.hasMoreDestinatari())
			{
				Object numDestinatariSelezionati = ReflectionUtil.invocaMetodo(b, "getNumDestinatariSelezionati", new Class[]{}, new Object[]{});
				context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
			}
			
			context.getSession().setAttribute( configTipo.getNomeBeanJsp(), b);
			
			Integer idOggetto = b.getId();
			tampone  = Tampone.load  ( idOggetto, configTipo, db );
			campioni = Campione.load ( idOggetto, configTipo, db);
			
			context.getRequest().setAttribute("Tampone", tampone);
			context.getRequest().setAttribute("Campioni", campioni);
			
			caricaLookup( context, configTipo );
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
	
	
	public BeanAjax isCapoEsistente( String identificativo, String codiceAzienda, Integer numOvini, Integer numCaprini, ConfigTipo configTipo ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		BeanAjax ret = (BeanAjax)ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBeanAjax());
		ReflectionUtil.invocaMetodo(ret, configTipo.getMetodoSetIdentificativo(), new Class[]{String.class}, new Object[]{identificativo});
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		
		try{
			conn = GestoreConnessioni.getConnection();

			if (conn!=null)
			{
				Object o = ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBean());
				String query = (String)ReflectionUtil.invocaMetodo(o, configTipo.getMetodoCostruisciQueryEsistenzaCapo(), new Class[]{ConfigTipo.class, Integer.class,Integer.class}, new Object[]{configTipo,numOvini,numCaprini});
				stat = conn.prepareStatement( query );
				stat = (PreparedStatement)ReflectionUtil.invocaMetodo(o, configTipo.getMetodoCostruisciStatementEsistenzaCapo(), new Class[]{PreparedStatement.class, String.class,String.class}, new Object[]{stat,identificativo,codiceAzienda});
			
				res = stat.executeQuery();
			
				if( res.next() )
				{
					ret.setEsistente(true);
				}
			}
			
		}
		catch (SQLException sqle){
			logger.severe("Eccezione nel metodo per determinare se " + configTipo.getDescrizioneCampoIdentificativoTabella() + " " + identificativo + " e' stata gia' inserita o meno in banca dati");
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
	public String executeCommandToLiberoConsumoSeduta( ActionContext context )
	{
		getConfigTipo(context);
		
		String ret = "ToLiberoConsumoSedutaOK";
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			b = GenericBean.load(context.getParameter("idPartita"), db, configTipo);
			
			Tampone 			tampone 			= null;
			ArrayList<Campione> 			campioni 			= null;
			
			
			if(configTipo.hasMoreDestinatari())
			{
				//Object numDestinatariSelezionati = ReflectionUtil.invocaMetodo(b, "getNumDestinatariSelezionati", new Class[]{}, new Object[]{});
				context.getRequest().setAttribute( "righeDestDefault",  2);
			}
			
			
			context.getSession().setAttribute( configTipo.getNomeBeanJsp(), b);
			
			Integer idOggetto = b.getId();
			tampone  = Tampone.load  ( idOggetto, configTipo, db );
			campioni = Campione.load ( idOggetto, configTipo, db);
			
			Iterator<PartitaSeduta> sedute = Partita.loadSeduteSmart(db, idOggetto, configTipo).iterator();
			int numCapiOviniPartita_AltreSedute = 0;
			int numCapiCapriniPartita_AltreSedute = 0;
			int numCapiOviniDestPartita_AltreSedute = 0;
			int numCapiCapriniDestPartita_AltreSedute = 0;
			int numCapiOviniMavamPartita_AltreSedute = 0;
			int numCapiCapriniMavamPartita_AltreSedute = 0;
			while(sedute.hasNext())
			{
				PartitaSeduta temp = sedute.next();
				Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
				int numCapiOviniEsitiSeduta = 0;
				int numCapiCapriniEsitiSeduta = 0;
				while(esitiSeduta.hasNext())
				{
					Esito esitoTemp = esitiSeduta.next();
					numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
					numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
				}
				numCapiOviniPartita_AltreSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
				numCapiCapriniPartita_AltreSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
				int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
				int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
				numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
				numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
				int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
				int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
				numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
				numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
			}
			Iterator<Esito> esitiPartita = Esito.load(((Partita)b).getId(), configTipo, db).iterator();
			int numCapiOviniEsitiPartita = 0;
			int numCapiCapriniEsitiPartita = 0;
			while(esitiPartita.hasNext())
			{
				Esito esitoTemp = esitiPartita.next();
				numCapiOviniEsitiPartita = esitoTemp.getNum_capi_ovini();
				numCapiCapriniEsitiPartita = esitoTemp.getNum_capi_caprini();
			}
			numCapiOviniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_ovini()+((Partita)b).getCasl_num_capi_ovini()+numCapiOviniEsitiPartita;
			numCapiCapriniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_caprini()+((Partita)b).getCasl_num_capi_caprini()+numCapiCapriniEsitiPartita;
			
			context.getRequest().setAttribute( "numCapiOviniPartita_AltreSedute", numCapiOviniPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniPartita_AltreSedute", numCapiCapriniPartita_AltreSedute+"" );
			
			int numCapiOviniDestPartita = ((Partita)b).getVam_num_capi_ovini();
			int numCapiCapriniDestPartita = ((Partita)b).getVam_num_capi_caprini();
			numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestPartita;
			numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestPartita;
			
			context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );
			
			int numCapiOviniMavamPartita = (((Partita)b)).getMavam_num_capi_ovini();
			int numCapiCapriniMavamPartita = (((Partita)b)).getMavam_num_capi_caprini();
			numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamPartita;
			numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamPartita;
			
			
			context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			
			context.getRequest().setAttribute("Tampone", tampone);
			context.getRequest().setAttribute("Campioni", campioni);
			
			context.getRequest().setAttribute( "updateLiberoConsumo", new Boolean( false ) );
			caricaLookup( context, configTipo );
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
		
		getConfigTipo(context);

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			
			//Capo.load(context.getParameter("id"), db);
			b = GenericBean.load(context.getParameter("id"), db, configTipo);
			
			Organization org = new Organization( db, Integer.parseInt(context.getParameter("orgId")) );
			
			if(configTipo.getSezioneSpeditore())
			{
				Integer idSpeditore = b.getCd_id_speditore();
				if( idSpeditore > 0 )
				{
					org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, idSpeditore );
					if(speditore.getAddressList().size() > 0)
					{
						OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
						context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
					}
					context.getRequest().setAttribute( "Speditore", speditore );		
				}
			}
			
			//id = c.getId();
			int id = b.getId();

			ArrayList<NonConformita>		ncsVAM				= NonConformita.load	( id, configTipo, db );
			ArrayList<Campione> 			campioni 			= Campione.load			( id, configTipo, db );
			Tampone 						tampone 			= Tampone.load			( id, configTipo, db );
			ArrayList<Organi>   			organi		 		= Organi.loadByOrgani	( id, configTipo, db );
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

			ArrayList<PatologiaRilevata>	patologieRilevate	= PatologiaRilevata.load( id, configTipo,db );
			ArrayList<Esito> esiti = null;
			if(configTipo.getEsitiMultipli())
				esiti = Esito.load( id, configTipo,db );
			ArrayList<ProvvedimentiCASL>	provvedimenti		= ProvvedimentiCASL.load( id, configTipo, db );
			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NC		= Casl_Non_Conformita_Rilevata.load( id, configTipo, db );

			
			ArrayList<CategoriaRischio> categorie = CategoriaRischio.load( id, configTipo,db );
			
			
			//context.getRequest().setAttribute( "Capo", c );
			
			
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
			
			if(configTipo.hasSeduteMacellazione())
			{
				String stato = (String)ReflectionUtil.invocaMetodo(o, "getStampatoArt17", new Class[]{Connection.class,ConfigTipo.class}, new Object[]{db, configTipo});
				if(stato.equals("SI"))
					context.getRequest().setAttribute( "stampatoArt17",  true);
				else
					context.getRequest().setAttribute( "stampatoArt17",  false);
			}
			
			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			
			context.getRequest().setAttribute( "OrgDetails", org );

			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "Tampone", tampone );

			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "Esiti", esiti );
			context.getRequest().setAttribute( "Categorie", categorie );
			
			Iterator<Esito> esitiIter = esiti.iterator();
			ArrayList<Integer> esitiId = new ArrayList<Integer>();
			while(esitiIter.hasNext())
			{
				esitiId.add(esitiIter.next().getId_esito());
			}
			context.getRequest().setAttribute( "EsitiId", esitiId );
			
			
			
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
			context.getRequest().setAttribute( "casl_NCRilevate", casl_NC );

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

		caricaLookup(context, false, configTipo);

		return ret;
	}
	
	public String executeCommandDetailsSeduta( ActionContext context )
	{
		String ret = "DetailsSedutaOK";
		
		getConfigTipo(context);

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			p_seduta = PartitaSeduta.load(context.getParameter("id"), db, configTipo);
			Organization org = new Organization( db, Integer.parseInt(context.getParameter("orgId")) );
			
			Integer id = null;
			id = p_seduta.getId();
			
			ArrayList<NonConformita>		ncsVAM				= PartitaSeduta.loadNonConformita( id, db );
			ArrayList<Campione> 			campioni 			= PartitaSeduta.loadCampioni	 ( id, db );
			Tampone 						tampone 			= PartitaSeduta.loadTampone  	 ( id, db );
			ArrayList<Organi>   			organi		 		= PartitaSeduta.loadOrgani   	 ( id, db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( p_seduta.getId_partita(), configTipo, db );
			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( p_seduta.getId_partita(), configTipo, db );
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

			ArrayList<PatologiaRilevata>	patologieRilevate	= PartitaSeduta.loadPatologieRilevate( id, db );
			ArrayList<Esito> 							esiti   = PartitaSeduta.loadEsito            ( id, db );
			ArrayList<CategoriaRischio> 							categorie   = CategoriaRischio.load(p_seduta.getId_partita(), configTipo, db);

			
			p_seduta.popolaCampiByPartita(db,configTipo);
			
			context.getRequest().setAttribute( "Partita", p_seduta );
			context.getRequest().setAttribute( "OrgDetails", org );

			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "Tampone", tampone );

			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "Esiti", esiti );
			context.getRequest().setAttribute( "Categorie", categorie );
			
			Iterator<Esito> esitiIter = esiti.iterator();
			ArrayList<Integer> esitiId = new ArrayList<Integer>();
			while(esitiIter.hasNext())
			{
				esitiId.add(esitiIter.next().getId_esito());
			}
			context.getRequest().setAttribute( "EsitiId", esitiId );
			
			
			
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
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

		caricaLookup(context, false, configTipo);

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
		getConfigTipo(context);
		try
		{
			db = this.getConnection( context );
			String id = (String) context.getRequest().getParameter("orgId");
			int orgId = new Integer(id);
			String dataMacellazioneString = context.getParameter("comboDateMacellazione");
			Timestamp data = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				data = new Timestamp( sdf.parse( dataMacellazioneString ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			}
			
			// SELEZIONO L'ULTIMA SEDUTA 
			String selectMax = "SELECT MAX(cd_seduta_macellazione) from m_capi where id_macello = ?  AND (vpm_data = ? or (vpm_data is null and mavam_data = ?)) ";
			PreparedStatement stat = db.prepareStatement( selectMax );
			stat.setInt( 1, orgId );
			stat.setTimestamp( 2, data );
			
			ResultSet rs = stat.executeQuery();
			int maxSedutaMacellazione = 0;
			while (rs.next()) {
	            maxSedutaMacellazione = rs.getInt(1);
			}
			//System.out.println("Attuale Seduta di Macellazione: "+maxSedutaMacellazione);
			
			String queryUpdate = "UPDATE m_capi SET cd_seduta_macellazione = ? " +
					"WHERE id_macello = ? AND vpm_data = ? AND (cd_seduta_macellazione =0 OR cd_seduta_macellazione <=0)";
			stat = db.prepareStatement( queryUpdate );
			stat.setInt( 1, maxSedutaMacellazione+1 );
			stat.setInt(2, orgId );
			stat.setTimestamp( 3, data );
			stat.execute();
			
			String queryUpdateTamponi = "UPDATE m_vpm_tamponi SET sessione_macellazione = ? " +
			"WHERE id_macello = ? AND data_macellazione = ? AND (sessione_macellazione =0 OR sessione_macellazione <=0)";
	stat = db.prepareStatement( queryUpdateTamponi );
	stat.setInt( 1, maxSedutaMacellazione+1 );
	stat.setInt(2, orgId );
	stat.setTimestamp( 3, data );
	stat.execute();
			
			context.getRequest().setAttribute( "messaggio", "Seduta di macellazione aggiunta" );
			
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
		 if(context.getParameter("tipo")!=null && !context.getParameter("tipo").equals("") && !context.getParameter("tipo").equals("null"))
         {
                 final int tipo = Integer.parseInt(context.getParameter("tipo"));
                 context.getSession().setAttribute("configTipo", new ConfigTipo(tipo));
         }
         getConfigTipo(context);

         String                  ret             = "HomeOK";

         if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
         {
                 return ("PermissionError");
         }

         Connection              db              = null;

         try
         {
//               ArrayList<Capo> capi       = new ArrayList<Capo>();
//               ArrayList<Partita> partite = new ArrayList<Partita>();
                 db = this.getConnection( context );
                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                 String dataMacellazioneString = context.getParameter("comboDateMacellazione");

                 TableFacade tf = TableFacadeFactory.createTableFacade( configTipo.getNumeroColonneList(), context.getRequest() );


                 if(dataMacellazioneString != null && !dataMacellazioneString.equals(""))
                 {
                         if(!dataMacellazioneString.equals("-1"))
                         {
                                 Timestamp dataMacellazioneTimestamp = new Timestamp( sdf.parse(dataMacellazioneString).getTime() );
                                 //capi = Capo.loadByStabilimentoPerDataMacellazione( context.getParameter( "orgId" ),dataMacellazioneTimestamp, db );
                                 beans = GenericBean.loadByStabilimentoPerDataMacellazione( context.getParameter( "orgId" ),dataMacellazioneTimestamp, db, configTipo );
                         }
                         else
                         {
                                 //capi = Capo.loadByStabilimentoCapiNonMacellati( context.getParameter( "orgId" ), db );
                                 beans = GenericBean.loadByStabilimentoCapiNonMacellati( context.getParameter( "orgId" ), db, configTipo );
                         }
                 }
                 else
                 {
                         if (context.getParameter("orgId")!= null)
                         {
                                 //capi = Capo.loadByStabilimento( context.getParameter( "orgId" ), db );
                                 beans = GenericBean.loadByStabilimento( context.getParameter( "orgId" ), db, configTipo );
                         }
                         else if(context.getRequest().getAttribute("OrgId")!=null)
                         {
                                 //capi = Capo.loadByStabilimento( context.getRequest().getAttribute("OrgId")+"", db);
                                 beans = GenericBean.loadByStabilimento(context.getRequest().getAttribute("OrgId")+"", db, configTipo );
                         }
                 }

                 tf.addFilterMatcher(new MatcherKey(Integer.class, "progressivo_macellazione"), new NumberFilterMatcher("###,##0.00"));
                 tf.addFilterMatcher(new MatcherKey(Timestamp.class, "vpm_data"), new DateFilterMatcher("dd/MM/yyyy"));

                 //tf.setItems(Collection<Object>)ReflectionUtil.getVariabile(this, configTipo.getNomeVariabileGlobaleActionMacellazioniArray()) );
                 tf.setItems( beans);
                 tf.setColumnProperties( configTipo.getColonneLista() );
                 tf.setStateAttr("restore");

                 String[] colonne = configTipo.getColonneLista();
                 String[] descrizione = configTipo.getDescrizioneColonneLista();
                 int i=0;
                 while(i<colonne.length)
                 {
                         tf.getTable().getRow().getColumn( colonne[i] ).setTitle( descrizione[i] );
                         i++;
                 }

                 Limit limit = tf.getLimit();
                 if( limit.isExported() )
                 {
                         tf.render();
                         return null;
                 }
                 else
                 {
                         HtmlColumn cg;

                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("capi_ovini_macellati");
                         cg.setFilterable( false );
                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {
                                                         String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);

                                                         int capiOviniMacellati = 0;
                                                         Partita pTemp = null;
                                                         Connection db2 = null;
                                                         try
                                                         {
                                                                 db2 = GestoreConnessioni.getConnection();

                                                                 pTemp = (Partita)Partita.load(id, db2, configTipo);
                                                                 
                                                                 int numCapiOviniEsitiSedutaDaSottrarre = 0;
                                             					 int numCapiOviniEsitiPartitaDaSottrarre = 0;

                                                                 Iterator<PartitaSeduta> sedute = Partita.loadSeduteWithoutPartita(db2,Integer.parseInt(id) , configTipo).iterator();
                                                                 while(sedute.hasNext())
                                                                 {
                                                                         PartitaSeduta temp = sedute.next();
                                                                         capiOviniMacellati+=temp.getVam_num_capi_ovini();
                                                                         capiOviniMacellati+=temp.getMavam_num_capi_ovini();
                                                                         
                                                                         Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db2).iterator();
                                                     					 while(esitiSeduta.hasNext())
                                                     					 {
                                                     						 Esito esitoTemp = esitiSeduta.next();
                                                     						 if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
                                                     						 {
                                                     							numCapiOviniEsitiSedutaDaSottrarre += esitoTemp.getNum_capi_ovini();
                                                     						 }
                                                     					 }
                                                     					 
                                                                 }

                                                                 capiOviniMacellati+=(pTemp.getVam_num_capi_ovini()-numCapiOviniEsitiSedutaDaSottrarre);
                                                                 capiOviniMacellati+=(pTemp.getMavam_num_capi_ovini());

                                                                 Iterator<Esito> esitiPartita = Esito.load(pTemp.getId(), configTipo, db2).iterator();
                                             					 while(esitiPartita.hasNext())
                                             					 {
                                             						 Esito esitoTemp = esitiPartita.next();
                                             						 if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
                                             						 {
                                             							numCapiOviniEsitiPartitaDaSottrarre += esitoTemp.getNum_capi_ovini();
                                             						 }
                                             					 }
                                             					 
                                             					 capiOviniMacellati-=numCapiOviniEsitiPartitaDaSottrarre;
                                             					 
                                                                 db2.close();
                                                         }
                                                         catch (Exception e)
                                                         {
                                                                 try
                                                                 {
                                                                         db2.close();
                                                                 }
                                                                 catch(Exception ex)
                                                                 {
                                                                         ex.printStackTrace();
                                                                 }
                                                                 e.printStackTrace();
                                                         }
                                                         finally
                                                         {
                                                                 if(db2!=null)
                                                                 {
                                                                         try
                                                                         {
                                                                                 db2.close();
                                                                         }
                                                                         catch(Exception ex)
                                                                         {
                                                                                 ex.printStackTrace();
                                                                         }
                                                                 }
                                                         }


                                                         return capiOviniMacellati;
                                                 }
                                         }

                         );

                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("capi_caprini_macellati");
                         cg.setFilterable( false );
                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {
                                                         String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);

                                                         int capiCapriniMacellati = 0;
                                                         Partita pTemp = null;
                                                         Connection db2 = null;
                                                         try
                                                         {
                                                                 db2 = GestoreConnessioni.getConnection();

                                                                 pTemp = (Partita)Partita.load(id, db2, configTipo);

                                             					 int numCapiCapriniEsitiSedutaDaSottrarre = 0;
                                            					 int numCapiCapriniEsitiPartitaDaSottrarre = 0;
                                            					 
                                                                 Iterator<PartitaSeduta> sedute = Partita.loadSeduteWithoutPartita(db2,Integer.parseInt(id) , configTipo).iterator();
                                                                 while(sedute.hasNext())
                                                                 {
                                                                         PartitaSeduta temp = sedute.next();
                                                                         capiCapriniMacellati+=temp.getVam_num_capi_caprini();
                                                                         capiCapriniMacellati+=temp.getMavam_num_capi_caprini();
                                                                         
                                                                         Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db2).iterator();
                                                     					 while(esitiSeduta.hasNext())
                                                     					 {
                                                     						 Esito esitoTemp = esitiSeduta.next();
                                                     						 if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
                                                     						 {
                                                     							numCapiCapriniEsitiSedutaDaSottrarre += esitoTemp.getNum_capi_caprini();
                                                     						 }
                                                     					 }
                                                                 }

                                                                 capiCapriniMacellati+=pTemp.getVam_num_capi_caprini();
                                                                 capiCapriniMacellati+=pTemp.getMavam_num_capi_caprini();
                                                                 
                                                                 Iterator<Esito> esitiPartita = Esito.load(pTemp.getId(), configTipo, db2).iterator();
                                             					 while(esitiPartita.hasNext())
                                             					 {
                                             						 Esito esitoTemp = esitiPartita.next();
                                             						 if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
                                             						 {
                                             							numCapiCapriniEsitiPartitaDaSottrarre += esitoTemp.getNum_capi_caprini();
                                             						 }
                                             					 }
                                             					 
                                             					 capiCapriniMacellati-=numCapiCapriniEsitiPartitaDaSottrarre;

                                                                 db2.close();
                                                         }
                                                         catch (Exception e)
                                                         {
                                                                 try
                                                                 {
                                                                         db2.close();
                                                                 }
                                                                 catch(Exception ex)
                                                                 {
                                                                         ex.printStackTrace();
                                                                 }
                                                                 e.printStackTrace();
                                                         }
                                                         finally
                                                         {
                                                                 if(db2!=null)
                                                                 {
                                                                         try
                                                                         {
                                                                                 db2.close();
                                                                         }
                                                                         catch(Exception ex)
                                                                         {
                                                                                 ex.printStackTrace();
                                                                         }
                                                                 }
                                                         }


                                                         return capiCapriniMacellati;
                                                 }
                                         }

                         );


                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("stato_macellazione");
                         cg.setFilterable( true );
                         cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {
                                                         String stato  = "";
                                                         String colore = ((GenericBean)item).color;

                                                         String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);

                                                         int numCapiOviniDestMavamPartitaSedute = 0;
                                                         int numCapiCapriniDestMavamPartitaSedute = 0;
                                                         Partita pTemp = null;
                                                         Connection db2 = null;
                                                         try
                                                         {
                                                                 db2 = GestoreConnessioni.getConnection();

                                                                 pTemp = (Partita)Partita.load(id, db2, configTipo);


                                                                 Iterator<PartitaSeduta> sedute = Partita.loadSeduteWithoutPartita(db2,Integer.parseInt(id) , configTipo).iterator();
                                                                 while(sedute.hasNext())
                                                                 {
                                                                         PartitaSeduta temp = sedute.next();
                                                                         numCapiOviniDestMavamPartitaSedute+=temp.getVam_num_capi_ovini();
                                                                         numCapiCapriniDestMavamPartitaSedute+=temp.getVam_num_capi_caprini();
                                                                         numCapiOviniDestMavamPartitaSedute+=temp.getMavam_num_capi_ovini();
                                                                         numCapiCapriniDestMavamPartitaSedute+=temp.getMavam_num_capi_caprini();
                                                                 }

                                                                 numCapiOviniDestMavamPartitaSedute+=pTemp.getVam_num_capi_ovini();
                                                                 numCapiCapriniDestMavamPartitaSedute+=pTemp.getVam_num_capi_caprini();
                                                                 numCapiOviniDestMavamPartitaSedute+=pTemp.getMavam_num_capi_ovini();
                                                                 numCapiCapriniDestMavamPartitaSedute+=pTemp.getMavam_num_capi_caprini();

                                                                 if((numCapiOviniDestMavamPartitaSedute==0 || pTemp.getCd_num_capi_ovini() <0)  && (numCapiCapriniDestMavamPartitaSedute==0 || pTemp.getCd_num_capi_caprini() <0))
                                                                 {
                                                                         stato = "Non Macellata";
                                                                         colore = "yellow";
                                                                 }
                                                                 else if(numCapiOviniDestMavamPartitaSedute<pTemp.getCd_num_capi_ovini() ||
                                                                    numCapiCapriniDestMavamPartitaSedute<pTemp.getCd_num_capi_caprini())
                                                                 {
                                                                         stato = "Parzialmente Macellata";
                                                                         colore = MacelliUtil.orange;
                                                                 }
                                                                 else
                                                                 {
                                                                         stato = "Completamente Macellata";
                                                                         colore = MacelliUtil.green;
                                                                 }
                                                                 String stato2 = pTemp.getStato_macellazione();
                                                                 if(stato2.contains("Incompleto"))
                                                                 {
                                                                         stato += ". " + stato2;
                                                                         if(colore.equals(MacelliUtil.green))
                                                                                 colore = "orange";
                                                                 }

                                                                 db2.close();
                                                         }
                                                         catch (Exception e)
                                                         {
                                                                 try
                                                                 {
                                                                         db2.close();
                                                                 }
                                                                 catch(Exception ex)
                                                                 {
                                                                         ex.printStackTrace();
                                                                 }
                                                                 e.printStackTrace();
                                                         }
                                                         finally
                                                         {
                                                                 if(db2!=null)
                                                                 {
                                                                         try
                                                                         {
                                                                                 db2.close();
                                                                         }
                                                                         catch(Exception ex)
                                                                         {
                                                                                 ex.printStackTrace();
                                                                         }
                                                                 }
                                                         }


                                                         return "<div style=\"background-color:" + colore + "\">" + stato + "</div>";
                                                 }
                                         }

                         );

                         cg = (HtmlColumn) tf.getTable().getRow().getColumn(configTipo.getNomeCampoIdentificativoTabella());

                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {
                                                	 Partita pTemp = null;
                                                	  Connection db2 = null;
                                                	  String categoriaPartita = "";
                                                	  String temp             = (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
                                                      String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
                                                      String id_mac   = (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
                                                      String colore = ((GenericBean)item).color;
                                                	  try
                                                      {
                                                	  db2 = GestoreConnessioni.getConnection();
                                                	   pTemp = (Partita)Partita.load(id, db2, configTipo);
                                                	   
                                                         temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
                                                         categoriaPartita = "OVICAPRINI";
                                                         if (pTemp.isSpecie_suina())
                                                        	 categoriaPartita ="SUINI";
                                                         //return "<div style=\"background-color:" + colore + "\"><a href=\"macellazioninew.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
                                                         db2.close();
                                                      }
                                                      catch (Exception e)
                                                      {
                                                              try
                                                              {
                                                                      db2.close();
                                                              }
                                                              catch(Exception ex)
                                                              {
                                                                      ex.printStackTrace();
                                                              }
                                                              e.printStackTrace();
                                                      }
                                                      finally
                                                      {
                                                              if(db2!=null)
                                                              {
                                                                      try
                                                                      {
                                                                              db2.close();
                                                                      }
                                                                      catch(Exception ex)
                                                                      {
                                                                              ex.printStackTrace();
                                                                      }
                                                              }
                                                      }
                                                         return categoriaPartita+"<br/>"+"<a href=\"MacellazioniNew.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a>";
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
                                                         String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
                                                         String id_mac   = (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);

                                                         //return "<div style=\"background-color:" + colore + "\"><a href=\"macellazioninew.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
                                                         return "<center><a href=\"MacellazioniNew.do?command=NuovoCapo&id=" + id + "&orgId=" + id_mac + "&clona=si \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Clona capo\" /></a></center>";
                                                         //return temp;


                                                 }
                                         }

                         );
                         if (enabledIstopatologico){
                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("richiesta_istopatologico"); //DECOMMENTARE DA QUA
                         cg.setFilterable( false );
                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {


                                                 String toReturn = "";

                                                 if (hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))    {

                                                 String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
                                                 String id_mac   = (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
                                                 String istopatologico = (String) (new HtmlCellEditor()).getValue(item, "istopatologico_richiesta", rowCount);
                                                 String istopatologico_id = (String) (new HtmlCellEditor()).getValue(item, "istopatologico_id", rowCount);
                                                 boolean istopatologicoEseguito = new Boolean(istopatologico).booleanValue();

                                         //    ArrayList<Organi>                         organi                          = Organi.loadByOrgani   (Integer.parseInt(id), configTipo );

                                                 Connection db = null;
                                                 try {
                                                         db = GestoreConnessioni.getConnection();
                                                 } catch (SQLException e) {
                                                         // TODO Auto-generated catch block
                                                         e.printStackTrace();
                                                 }

                                                 ArrayList<Organi>                       organiTumorali                          = GenericBean.checkOrganiTumorali(db, Integer.parseInt(id), configTipo) ;
                                             RichiestaIstopatologico richiesteIstopatologico = new RichiestaIstopatologico();
                                             if (istopatologico_id != null && !("").equals(istopatologico_id))
                                                         try {
                                                                 richiesteIstopatologico =       RichiestaIstopatologico.load(Integer.valueOf(istopatologico_id), null, configTipo);


////                                     if (configTipo.getEsitiMultipli()){
////
////                                             Connection db = null;
////                                             try {
////                                                     db = GestoreConnessioni.getConnection();
////                                             } catch (SQLException e) {
////                                                     // TODO Auto-generated catch block
////                                                     e.printStackTrace();
////                                             }
////
////
////                                             Iterator<PartitaSeduta> sedute = Partita.loadSeduteSmart(db, b.getId(), configTipo).iterator();
////                                             int k = 0;
////                                             while(sedute.hasNext())
////                                             {
////                                                     if (k <= 0){
////                                                     PartitaSeduta temp = sedute.next();
////                                                     organi = temp.loadOrgani(temp.getId(), db);
////
////                                                     if (organi != null && organi.size() > 0)        {
////                                                             Iterator i = organi.iterator();
////
////                                                             while (i.hasNext()){
////                                                                     Organi thisOrgano = (Organi) i.next();
////
////                                                                     if (!istopatologicoEseguito && thisOrgano.getLcso_patologia() == 2 && k <= 0){
////                                                             //return "<div style=\"background-color:" + colore + "\"><a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
////                                                                             toReturn += "<center><a href=\"MacellazioniNew.do?command=RichiestaIstopatologico&id=" + id + "&orgId=" + id_mac + " \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Richiedi istopatologico\" /></a></center>";
////                                                                             k++;
////                                                                     }
////                                                                     else if (istopatologicoEseguito && k <= 0) {
////                                                                             toReturn += "<div>" + "<a href=\"GestioneDocumenti.do?command=GeneraPDFInvioCampioniIstopatologico&idIstopatologico=" + istopatologico_id + " \">Stampa Modello Invio Campioni</a>" + "</div> ";
////                                                                     if  (richiesteIstopatologico.getIdEsito() > 0 )
////                                                                             toReturn += "<div>" + "<a onclick=\"window.open('MacellazioniNew.do?command=VisualizzaEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Visualizza esito</a>" + "</div> ";
////                                                                      if (richiesteIstopatologico.getIdEsito() <= 0)
////                                                                             toReturn += "<div>" + "<a onclick=\"window.open('MacellazioniNew.do?command=PrepareInserisciEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Inserisci esito</a>" + "</div> ";
////                                                                      k++;
////                                                                     }
////                                                             }
////                                                             if (("").equals(toReturn) )
////                                                             toReturn  = "NON PREVISTO";
////
////                                                     }else toReturn  = "NON PREVISTO";
//////                                                   Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
//////                                                   {
//////                                                           Esito esitoTemp = esitiSeduta.next();
//////
//////                                                   }
////                                                     }
////                                             }
////
////                                     }else{
                                                 if (organiTumorali != null && organiTumorali.size() > 0)        {
//                                                       //Iterator i = organi.iterator();

////                                                     while (i.hasNext()){
////                                                             Organi thisOrgano = (Organi) i.next();

                                                                 if (!istopatologicoEseguito){
                                                         //return "<div style=\"background-color:" + colore + "\"><a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
                                                                         toReturn += "<center><a href=\"MacellazioniNew.do?command=RichiestaIstopatologico&id=" + id + "&orgId=" + id_mac + " \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Richiedi istopatologico\" /></a></center>";

                                                                 }
                                                                 else if (istopatologicoEseguito) {
                                                                         toReturn += "<div>" + "<a href=\"GestioneDocumenti.do?command=GeneraPDFInvioCampioniIstopatologicoNew&idIstopatologico=" + istopatologico_id + " \">Stampa Modello Invio Campioni</a>" + "</div> ";
                                                                 if  (richiesteIstopatologico.getIdEsito() > 0 )
                                                                         toReturn += "<div>" + "<a onclick=\"window.open('MacellazioniNew.do?command=VisualizzaEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Visualizza esito</a>" + "</div> ";
                                                                  if (richiesteIstopatologico.getIdEsito() <= 0)
                                                                         toReturn += "<div>" + "<a onclick=\"window.open('MacellazioniNew.do?command=PrepareInserisciEsitoRichiestaIstopatologico&idIstopatologico=" + istopatologico_id + "&popup=true', 'titolo', 'width=600, height=600, resizable, status, scrollbars=1, location');\" href=\"#\" + \">Inserisci esito</a>" + "</div> ";

                                                                 }
                                                 //      }
                                                         if (("").equals(toReturn) )
                                                         toReturn  = "NON PREVISTO";

                                                 }else toReturn  = "NON PREVISTO";
                                         //      }
//                                       //}
                                         } catch (Exception e) {
                                                 // TODO Auto-generated catch block
                                                 e.printStackTrace();
                                         }finally{
                                                 GestoreConnessioni.freeConnection(db);
                                         }
                                                 }//FINE PERMISSION
                                                 return toReturn;

                                                 }
                                         }

                         );//A QUA
                         }
                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("articolo17");
                         cg.setFilterable( false );
                         cg.getCellRenderer().setCellEditor(
                                         new CellEditor()
                                         {
                                                 public Object getValue(Object item, String property, int rowCount)
                                                 {
                                                         String toReturn = "";
                                                         String art17    = (String) (new HtmlCellEditor()).getValue(item, "articolo17", rowCount);
                                                         String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);

                                                         Partita pTemp = null;
                                                         String colore = "";
                                                         String stato = "";

                                                         Connection db2 = null;
                                                         try
                                                         {
                                                                 db2 = GestoreConnessioni.getConnection();

                                                                 pTemp = (Partita)Partita.load(id, db2, configTipo);

                                                                 stato  = pTemp.getStampatoArt17(db2,configTipo);
                                                                 colore = pTemp.getColorStampatoArt17(db2,configTipo,stato);

                                                         }
                                                         catch (Exception e)
                                                         {
                                                                 try
                                                                 {
                                                                         db2.close();
                                                                 }
                                                                 catch(Exception ex)
                                                                 {
                                                                         ex.printStackTrace();
                                                                 }
                                                                 e.printStackTrace();
                                                         }
                                                         finally
                                                         {
                                                                 if(db2!=null)
                                                                 {
                                                                         try
                                                                         {
                                                                                 db2.close();
                                                                         }
                                                                         catch(Exception ex)
                                                                         {
                                                                                 ex.printStackTrace();
                                                                         }
                                                                 }
                                                         }

                                                         return "<div style=\"background-color:" + colore + " \">" + stato + "</div> ";
                                                 }
                                         });

                         //cg = (HtmlColumn) tf.getTable().getRow().getColumn("cd_codice_azienda");
                         //cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

                         if(trova(colonne, "cd_data_nascita"))
                         {
                                 cg = (HtmlColumn) tf.getTable().getRow().getColumn("cd_data_nascita");
                                 cg.setFilterable( false );
                                 cg.getCellRenderer().setCellEditor(new DateCellEditor("dd/MM/yyyy") );
                         }

                         if(trova(colonne, "vpm_data"))
                         {
                         cg = (HtmlColumn) tf.getTable().getRow().getColumn("vpm_data");
                         cg.setFilterable( true );
                         cg.getCellRenderer().setCellEditor(new DateCellEditor("dd/MM/yyyy") );
                         }

                         if(trova(colonne, "progressivo_macellazione"))
                         {
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
                         }

                         if(trova(colonne, "chiusa"))
                         {
                                 cg = (HtmlColumn) tf.getTable().getRow().getColumn("chiusa");
                                 cg.setFilterable( false );
                                 cg.getCellRenderer().setCellEditor(
                                                 new CellEditor()
                                                 {
                                                         public Object getValue(Object item, String property, int rowCount)
                                                         {
                                                                 String chiusa   = (String) (new HtmlCellEditor()).getValue(item, "chiusa", rowCount);
                                                                 if(chiusa.equals("true"))
                                                                         return "Chiusa";
                                                                 else
                                                                         return "Aperta";
                                                         }
                                                 }

                                 );
                         }
                         if(trova(colonne, "operazioni"))
                         {
                                 cg = (HtmlColumn) tf.getTable().getRow().getColumn("operazioni");
                                 cg.setFilterable( false );
                                 cg.getCellRenderer().setCellEditor(
                                                 new CellEditor()
                                                 {
                                                         public Object getValue(Object item, String property, int rowCount)
                                                         {
                                                                 String chiusa   = (String) (new HtmlCellEditor()).getValue(item, "chiusa", rowCount);
                                                                 String id               = (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
                                                                 String id_mac   = (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);

                                                                 int numCapiOviniDestMavamPartitaSedute = 0;
                                                                 int numCapiCapriniDestMavamPartitaSedute = 0;
                                                                 Partita pTemp = null;
                                                                 Connection db2 = null;
                                                                 try
                                                                 {
                                                                         db2 = GestoreConnessioni.getConnection();

                                                                         pTemp = (Partita)Partita.load(id, db2, configTipo);


                                                                         Iterator<PartitaSeduta> sedute = Partita.loadSeduteWithoutPartita(db2,Integer.parseInt(id) , configTipo).iterator();
                                                                         while(sedute.hasNext())
                                                                         {
                                                                                 PartitaSeduta temp = sedute.next();
                                                                                 numCapiOviniDestMavamPartitaSedute+=temp.getVam_num_capi_ovini();
                                                                                 numCapiCapriniDestMavamPartitaSedute+=temp.getVam_num_capi_caprini();
                                                                                 numCapiOviniDestMavamPartitaSedute+=temp.getMavam_num_capi_ovini();
                                                                                 numCapiCapriniDestMavamPartitaSedute+=temp.getMavam_num_capi_caprini();
                                                                         }

                                                                         numCapiOviniDestMavamPartitaSedute+=pTemp.getVam_num_capi_ovini();
                                                                         numCapiCapriniDestMavamPartitaSedute+=pTemp.getVam_num_capi_caprini();
                                                                         numCapiOviniDestMavamPartitaSedute+=pTemp.getMavam_num_capi_ovini();
                                                                         numCapiCapriniDestMavamPartitaSedute+=pTemp.getMavam_num_capi_caprini();

                                                                         db2.close();
                                                                 }
                                                                 catch (Exception e)
                                                                 {
                                                                         try
                                                                         {
                                                                                 db2.close();
                                                                         }
                                                                         catch(Exception ex)
                                                                         {
                                                                                 ex.printStackTrace();
                                                                         }
                                                                         e.printStackTrace();
                                                                 }
                                                                 finally
                                                                 {
                                                                         if(db2!=null)
                                                                         {
                                                                                 try
                                                                                 {
                                                                                         db2.close();
                                                                                 }
                                                                                 catch(Exception ex)
                                                                                 {
                                                                                         ex.printStackTrace();
                                                                                 }
                                                                         }
                                                                 }

                                                                 if(chiusa.equals("true"))
                                                                         return "<a href=\"MacellazioniNew.do?command=ListSedute&id=" + id + "&orgId=" + id_mac + "\">Lista Sedute</a> <a href=\"javascript:toMostraMotivazioni("+id+","+id_mac+");\">Motivazioni</a>";
                                                                 else
                                                                 {
                                                                         if(numCapiOviniDestMavamPartitaSedute<pTemp.getCd_num_capi_ovini() || numCapiCapriniDestMavamPartitaSedute<pTemp.getCd_num_capi_caprini())
                                                                                 return "<a href=\"MacellazioniNew.do?command=ToRiprendiPartita&id=" + id + "&orgId=" + id_mac + "\">Aggiungi Seduta</a> <a href=\"MacellazioniNew.do?command=ListSedute&id=" + id + "&orgId=" + id_mac + "\">Lista Sedute</a> <a href=\"javascript:toChiudiPartita("+id+","+id_mac+");\">Chiudi</a>";
                                                                         else
                                                                                 return "<a href=\"#\" onclick=\"alert('Impossibile inserire altre sedute di macellazione: tutti i capi risultano gia' macellati.')\" >Aggiungi Seduta</a> <a href=\"MacellazioniNew.do?command=ListSedute&id=" + id + "&orgId=" + id_mac + "\">Lista Sedute</a> <a href=\"javascript:toChiudiPartita("+id+","+id_mac+");\">Chiudi</a>";

                                                                 }
                                                         }
                                                 }

                                 );
                         }


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
                 context.getRequest().setAttribute( "listaDateMacellazione", getDateMacellazione(configTipo, db, context) );



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

         caricaLookup( context, configTipo );

         return ret;
		
	}
	
	public String executeCommandListSedute( ActionContext context )
	{
		getConfigTipo(context);
		
		String			ret		= "ListSeduteOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection		db		= null;

		try
		{
			ArrayList<PartitaSeduta> partite = new ArrayList<PartitaSeduta>();
			db = this.getConnection( context );
			
			TableFacade tf = null;
			tf = TableFacadeFactory.createTableFacade( "6", context.getRequest() );
		
			b = GenericBean.load(context.getParameter("id"), db, configTipo);
			
			getConfigTipo(context);
			
			partite = Partita.loadSeduteMinimale( db, b.getId(), configTipo );
			
			tf.addFilterMatcher(new MatcherKey(Timestamp.class, "vpm_data"), new DateFilterMatcher("dd/MM/yyyy"));

			tf.setItems( partite );
			tf.setColumnProperties( "numero", "stato_macellazione", "cd_codice_azienda_provenienza", "ovini_morti_am","caprini_morti_am","ovini_macellati","caprini_macellati", "vpm_data", "articolo17" );
			tf.setStateAttr("restore");

			tf.getTable().getRow().getColumn( "stato_macellazione" ).setTitle( "Stato Macellazione" );
			tf.getTable().getRow().getColumn( "numero" ).setTitle( "Numero" );
			tf.getTable().getRow().getColumn( "cd_codice_azienda_provenienza" ).setTitle( "Codice Azienda" );
			tf.getTable().getRow().getColumn( "ovini_morti_am" ).setTitle( "Ovini/Cinghiali Totali" );
			tf.getTable().getRow().getColumn( "caprini_morti_am" ).setTitle( "Caprini/Suini Totali" );
			tf.getTable().getRow().getColumn( "ovini_macellati" ).setTitle( "Ovini/Cinghiali Macellati" );
			tf.getTable().getRow().getColumn( "caprini_macellati" ).setTitle( "Caprini/Suini Macellati" );
			tf.getTable().getRow().getColumn( "vpm_data" ).setTitle( "Data Macellazione" );
			tf.getTable().getRow().getColumn( "articolo17" ).setTitle( "Stampato Art. 17" );

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
								String colore = ((PartitaSeduta)item).color;
								return "<div style=\"background-color:" + colore + "\">" + temp + "</div>";
							}
						}

				);

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("ovini_macellati");
				cg.setFilterable( false );		
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								
								int capiOviniMacellati = 0;
								PartitaSeduta pTemp = null;
								Connection db2 = null;
								try 
								{
									db2 = GestoreConnessioni.getConnection();
									
									pTemp = (PartitaSeduta)PartitaSeduta.load(id, db2, configTipo);
									
									capiOviniMacellati+=pTemp.getVam_num_capi_ovini();
									
									
									Iterator<Esito> esitiSeduta = pTemp.loadEsito(pTemp.getId(), db2).iterator();
									
        							int numCapiOviniEsitiSedutaDaSottrarre = 0;
	            					
        							while(esitiSeduta.hasNext())
	            					{
	            						Esito esitoTemp = esitiSeduta.next();
	            						if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
	            						{
	            							numCapiOviniEsitiSedutaDaSottrarre = esitoTemp.getNum_capi_ovini();
	            						}
	            					}
        							
        							capiOviniMacellati-=numCapiOviniEsitiSedutaDaSottrarre;
        							if (capiOviniMacellati<0)
        								capiOviniMacellati=0;
									
									db2.close();
								} 
								catch (Exception e) 
								{
									try
									{
										db2.close();
									}
									catch(Exception ex)
									{
										ex.printStackTrace();
									}
									e.printStackTrace();
								}
								finally
								{
									if(db2!=null)
									{
										try
										{
											db2.close();
										}
										catch(Exception ex)
										{
											ex.printStackTrace();
										}
									}
								}
								
								
								return capiOviniMacellati;
							}
						}

				);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("caprini_macellati");
				cg.setFilterable(false);
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								
								int capiCapriniMacellati = 0;
								PartitaSeduta pTemp = null;
								Connection db2 = null;
								try 
								{
									db2 = GestoreConnessioni.getConnection();
									
									pTemp = (PartitaSeduta)PartitaSeduta.load(id, db2, configTipo);
									
									capiCapriniMacellati+=pTemp.getVam_num_capi_caprini();
									
									
									Iterator<Esito> esitiSeduta = pTemp.loadEsito(pTemp.getId(), db2).iterator();
									
        							int numCapiCapriniEsitiSedutaDaSottrarre = 0;
	            					
        							while(esitiSeduta.hasNext())
	            					{
	            						Esito esitoTemp = esitiSeduta.next();
	            						if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
	            						{
	            							numCapiCapriniEsitiSedutaDaSottrarre = esitoTemp.getNum_capi_caprini();
	            						}
	            					}
        							
        							capiCapriniMacellati-=numCapiCapriniEsitiSedutaDaSottrarre;
        							if (capiCapriniMacellati<0)
        								capiCapriniMacellati=0;
									
									
									db2.close();
								} 
								catch (Exception e) 
								{
									try
									{
										db2.close();
									}
									catch(Exception ex)
									{
										ex.printStackTrace();
									}
									e.printStackTrace();
								}
								finally
								{
									if(db2!=null)
									{
										try
										{
											db2.close();
										}
										catch(Exception ex)
										{
											ex.printStackTrace();
										}
									}
								}
								
								
								return capiCapriniMacellati;
							}
						}

				);
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("ovini_morti_am");
				cg.setFilterable( false );		
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								
								int capiOviniMacellati = 0;
								PartitaSeduta pTemp = null;
								Connection db2 = null;
								try 
								{
									db2 = GestoreConnessioni.getConnection();
									
									pTemp = (PartitaSeduta)PartitaSeduta.load(id, db2, configTipo);
									
									capiOviniMacellati+=pTemp.getMavam_num_capi_ovini()+pTemp.getVam_num_capi_ovini();
									
									Iterator<Esito> esitiSeduta = pTemp.loadEsito(pTemp.getId(), db2).iterator();
									
        							int numCapiOviniEsitiSedutaDaSottrarre = 0;
	            					
        							while(esitiSeduta.hasNext())
	            					{
	            						Esito esitoTemp = esitiSeduta.next();
	            						if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
	            						{
	            							numCapiOviniEsitiSedutaDaSottrarre = esitoTemp.getNum_capi_ovini();
	            						}
	            					}
        							
        							//capiOviniMacellati-=numCapiOviniEsitiSedutaDaSottrarre;
        							if (capiOviniMacellati<0)
        								capiOviniMacellati=0;
        							
									db2.close();
								} 
								catch (Exception e) 
								{
									try
									{
										db2.close();
									}
									catch(Exception ex)
									{
										ex.printStackTrace();
									}
									e.printStackTrace();
								}
								finally
								{
									if(db2!=null)
									{
										try
										{
											db2.close();
										}
										catch(Exception ex)
										{
											ex.printStackTrace();
										}
									}
								}
								
								
								return capiOviniMacellati;
							}
						}

				);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("caprini_morti_am");
				cg.setFilterable(false);
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								
								int capiCapriniMacellati = 0;
								PartitaSeduta pTemp = null;
								Connection db2 = null;
								try 
								{
									db2 = GestoreConnessioni.getConnection();
									
									pTemp = (PartitaSeduta)PartitaSeduta.load(id, db2, configTipo);
									
									capiCapriniMacellati+=pTemp.getMavam_num_capi_caprini()+pTemp.getVam_num_capi_caprini();
									
									Iterator<Esito> esitiSeduta = pTemp.loadEsito(pTemp.getId(), db2).iterator();
									
        							int numCapiCapriniEsitiSedutaDaSottrarre = 0;
	            					
        							while(esitiSeduta.hasNext())
	            					{
	            						Esito esitoTemp = esitiSeduta.next();
	            						if(esitoTemp.getId_esito()== 2 || esitoTemp.getId_esito()==5)
	            						{
	            							numCapiCapriniEsitiSedutaDaSottrarre = esitoTemp.getNum_capi_caprini();
	            						}
	            					}
        							
        						//	capiCapriniMacellati-=numCapiCapriniEsitiSedutaDaSottrarre;
        							if (capiCapriniMacellati<0)
        								capiCapriniMacellati=0;
        							
									db2.close();
								} 
								catch (Exception e) 
								{
									try
									{
										db2.close();
									}
									catch(Exception ex)
									{
										ex.printStackTrace();
									}
									e.printStackTrace();
								}
								finally
								{
									if(db2!=null)
									{
										try
										{
											db2.close();
										}
										catch(Exception ex)
										{
											ex.printStackTrace();
										}
									}
								}
								
								
								return capiCapriniMacellati;
							}
						}

				);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("numero");
				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id		= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
								String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
								String categoriaPartita ="";
								temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
								
								
								PartitaSeduta pTemp = null;
								Connection db2 = null;
								try {
								db2 = GestoreConnessioni.getConnection();
								pTemp = (PartitaSeduta)PartitaSeduta.load(id, db2, configTipo);
								
								 categoriaPartita = "OVICAPRINI";
                                 if (pTemp.isSpecie_suina())
                                	 categoriaPartita ="SUINI";
                                 //return "<div style=\"background-color:" + colore + "\"><a href=\"macellazioninew.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
                                 db2.close();
                              }
                              catch (Exception e)
                              {
                                      try
                                      {
                                              db2.close();
                                      }
                                      catch(Exception ex)
                                      {
                                              ex.printStackTrace();
                                      }
                                      e.printStackTrace();
                              }
                              finally
                              {
                                      if(db2!=null)
                                      {
                                              try
                                              {
                                                      db2.close();
                                              }
                                              catch(Exception ex)
                                              {
                                                      ex.printStackTrace();
                                              }
                                      }
                              }
								//return "<div style=\"background-color:" + colore + "\"><a href=\"macellazioninew.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a></div>";
								return categoriaPartita+"<br/>"+ "<a href=\"MacellazioniNew.do?command=DetailsSeduta&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a>";
								//return temp;


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
				

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("vpm_data");
				cg.setFilterable( true );
				cg.getCellRenderer().setCellEditor(new DateCellEditor("dd/MM/yyyy") );

			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );

			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			context.getRequest().setAttribute( "partite", partite );
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

		caricaLookup( context, configTipo );
		
		return ret;
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
			ArrayList<Tipo> tipi = new ArrayList<Tipo>();
			db = this.getConnection( context );
			tipi = Tipo.loadAll( db );
			context.getRequest().setAttribute( "tipi", tipi );
			
			Organization org = new Organization( db, Integer.parseInt(context.getRequest().getParameter("orgId")) );
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

		return ret;
	}

	public String executeCommandNuovoCapo( ActionContext context )
	{
		getConfigTipo(context);
		
		String		ret	= "NuovoCapoOK";

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			
			int codiceUnivoco = GenericBean.nextId(db, configTipo);
			context.getRequest().setAttribute( "codiceUnivoco", codiceUnivoco+"" );
			Date d = new Date();
			context.getRequest().setAttribute( "anno", 1900 + d.getYear() + "" );
			int asl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			String aslString ="";
			
			//Gestisco casi in cui l'utente non ha ASL
			if (asl<0) 
				aslString = "0";
			else
				aslString = String.valueOf(asl);
			
			//Padding a tre cifre
			while (aslString.length()<3){
				aslString = "0"+aslString;
			}
			
			context.getRequest().setAttribute( "asl", aslString);
			
			context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", "0" );
			context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", "0" );
			context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", "0" );
			context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", "0" );
			
			if( context.getRequest().getParameter("clona") != null && context.getRequest().getParameter("clona").equals("si") ){
	
				String idCapo = context.getParameter( "id" );
				if((idCapo == null || idCapo.equals("0")) && context.getRequest().getAttribute("idCapo") != null){
					idCapo = context.getRequest().getAttribute("idCapo").toString();
				}
				
				if (idCapo!=null)
				{
					b = GenericBean.load(idCapo, db, configTipo);
					
					if (configTipo.hasSeduteMacellazione())
					{
						Iterator<PartitaSeduta> sedute = Partita.loadSeduteSmart(db, b.getId(), configTipo).iterator();
						int numCapiOviniSedute = 0;
						int numCapiCapriniSedute = 0;
						while(sedute.hasNext())
						{
							PartitaSeduta temp = sedute.next();
							Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
							int numCapiOviniEsitiSeduta = 0;
							int numCapiCapriniEsitiSeduta = 0;
							while(esitiSeduta.hasNext())
							{
								Esito esitoTemp = esitiSeduta.next();
								numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
								numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
							}
							numCapiOviniSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
							numCapiCapriniSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
						}
						context.getRequest().setAttribute( "numCapiOviniSedute", numCapiOviniSedute+"" );
						context.getRequest().setAttribute( "numCapiCapriniSedute", numCapiCapriniSedute+"" );
					}
					
				/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
				context.getRequest().setAttribute( "Speditore", speditore );
				if(speditore.getAddressList().size() > 0){
					OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
					context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
				}*/

				GenericBean capoClonato =  (GenericBean)ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBean());
				capoClonato.setCd_mod4(b.getCd_mod4());
				capoClonato.setCd_data_mod4(b.getCd_data_mod4());
				capoClonato.setCd_codice_azienda_provenienza(b.getCd_codice_azienda_provenienza());
				capoClonato.setCd_tipo_mezzo_trasporto(b.getCd_tipo_mezzo_trasporto());
				capoClonato.setCd_targa_mezzo_trasporto(b.getCd_targa_mezzo_trasporto());
				capoClonato.setCd_trasporto_superiore8ore(b.isCd_trasporto_superiore8ore());
				capoClonato.setCd_veterinario_1(b.getCd_veterinario_1());
				capoClonato.setCd_veterinario_2(b.getCd_veterinario_2());
				capoClonato.setCd_veterinario_3(b.getCd_veterinario_3());
				capoClonato.setCd_data_arrivo_macello(b.getCd_data_arrivo_macello());
				capoClonato.setVpm_veterinario(b.getVpm_veterinario());
				capoClonato.setVpm_veterinario_2(b.getVpm_veterinario_2());
				capoClonato.setVpm_veterinario_3(b.getVpm_veterinario_3());
				
				Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(capoClonato);
				
				if(configTipo.hasMoreDestinatari())
				{
					Object numDestinatariSelezionati = ReflectionUtil.invocaMetodo(o, "getNumDestinatariSelezionati", new Class[]{}, new Object[]{});
					context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
				}
				context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), o );
				}
			}

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

		caricaLookup( context, configTipo );

		return ret;
	}
	
	public String executeCommandRiprendiPartita( ActionContext context )
	{ 
		getConfigTipo(context);
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db = this.getConnection( context );
			
			p_seduta = (PartitaSeduta)loadSeduta(context);
			
			//Controllo stampa del modello 10
			String ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,false))!=null)
				return ret2;
			
			//Controllo progressivo di Macellazione
//			ret2 = null;
//			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, false, null, null))!=null)
//				return ret2;
			
			ArrayList<Campione> cmps = loadCampioniSeduta(context, configTipo, p_seduta);
			
			p_seduta.setEntered_by( this.getUserId(context) );
			
			
			//Generazione numerazione univoca automatica
			int codiceUnivoco = PartitaSeduta.nextId(db, configTipo);
			Date d = new Date();
			String anno = 1900 + d.getYear() + "" ;
			int asl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			String aslString ="";
			
			//Gestisco casi in cui l'utente non ha ASL
			if (asl<0) 
				aslString = "0";
			else
				aslString = String.valueOf(asl);
			
			//Padding a tre cifre
			while (aslString.length()<3){
				aslString = "0"+aslString;
			}
			String numero = aslString + codiceUnivoco + anno;
			p_seduta.setNumero(numero);
			p_seduta = p_seduta.store( db, cmps, configTipo );
			
			int enteredBy = this.getUserId(context);
			int modifiedBy = this.getUserId(context);
			Timestamp entered = null;
			Timestamp modified = null;
			Integer idSeduta = null;
			Integer idMacello = null;
			Integer sedutaMacellazione = null;
			Timestamp dataMacellazione = null;
			idSeduta = p_seduta.getId();
			entered = p_seduta.getEntered();
			modified = p_seduta.getModified();
			idMacello = p_seduta.getId_macello();
			sedutaMacellazione = p_seduta.getCd_seduta_macellazione();
			dataMacellazione = p_seduta.getVpm_data();
			
			context.getRequest().setAttribute("id", idSeduta);

			String[] patologieRilevate = context.getRequest().getParameterValues( "vpm_patologie_rilevate" );
			patologieRilevate = (patologieRilevate == null) ? (new String[0]) : (patologieRilevate);
			for( String index: patologieRilevate )
			{
				if( !"-1".equals( index ) )
				{
					PatologiaRilevata pr = new PatologiaRilevata();
					pr.setId_seduta		( idSeduta );
					pr.setId_patologia	( Integer.parseInt( index ) );
					pr.setEntered		( entered);
					pr.setModified		( entered );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}
			
			if(p_seduta.getVam_esito()!=null && p_seduta.getVam_esito().toLowerCase().contains("non favorevole".toLowerCase()))
			{
				ArrayList<NonConformita> ncs = loadNC( context );
				for( NonConformita nc: ncs )
				{
					nc.setId_seduta(p_seduta.getId());
					nc.setEntered		( entered );
					nc.setModified		( entered );
					nc.setEntered_by	( getUserId(context) );
					nc.setModified_by	( getUserId(context) );
					nc.store			( db );
				}
			}
			
			/*String[] casl_NCRilevate = context.getRequest().getParameterValues( "casl_NC_rilevate" );
			casl_NCRilevate = (casl_NCRilevate == null) ? (new String[0]) : (casl_NCRilevate);
			for( String index: casl_NCRilevate )
			{
				if( !"-1".equals( index ) )
				{
					Casl_Non_Conformita_Rilevata nc = new Casl_Non_Conformita_Rilevata();
					nc.setId_seduta(p_seduta.getId());
					nc.setId_casl_non_conformita( Integer.parseInt( index ));
					nc.setEntered				( entered);
					nc.setModified				( entered );
					nc.setEntered_by			( getUserId(context) );
					nc.setModified_by			( getUserId(context) );
					nc.store( db );
				}
			}*/
			
			String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
			esiti = (esiti == null) ? (new String[0]) : (esiti);
			for( String index: esiti )
			{
				if( !"-1".equals( index ) )
				{
					Esito e = new Esito();
					e.setId_seduta		( idSeduta );
					e.setId_esito   	( Integer.parseInt( index ) );
					e.setEntered		( entered);
					e.setModified		( entered );
					e.setEntered_by	( getUserId(context) );
					e.setModified_by	( getUserId(context) );
					e.setNum_capi_ovini(intero("num_capi_ovini_esito_"+index, context));
					e.setNum_capi_caprini(intero("num_capi_caprini_esito_"+index, context));
					e.store( db );
				}
			}
			
			

			// Inserito da Alberto

			for( Campione camp: cmps )
			{
				camp.setId_seduta	( idSeduta );
				camp.setEntered		( entered );
				camp.setModified	( entered );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db, configTipo );
			}
			Tampone tampone = loadTampone( context );
			
			tampone.setEntered		( entered );
			tampone.setId_macello(idMacello);
			tampone.setSessione_macellazione(sedutaMacellazione);
			tampone.setData_macellazione(dataMacellazione);
			tampone.setModified	( entered );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db,context );
				
			}
			
			tampone.associa_tampone_seduta(p_seduta, db);
			}
			
			ArrayList<Organi> organi = loadOrgani( context );
			for( Organi org: organi )
			{
				org.setId_seduta(p_seduta.getId());
				org.setEntered		( entered );
				org.setModified		( entered );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
			}

			//c.storico x provvedimenti
			//c.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c.getId(), db );

			context.getRequest().setAttribute( "messaggio", "Seduta di macellazione numero "+ p_seduta.getNumero() + " aggiunta" );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
			if(e1.getMessage().contains("m_evita_matricole_duplicate")){
				context.getRequest().setAttribute( "messaggio", "Errore: Matricola gia' esistente" );
			}
			else if(e1.getMessage().contains("m_evita_partite_duplicate")){
				context.getRequest().setAttribute( "messaggio", "Errore: Partita gia' esistente per questo macello e specie" );
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
		clona(configTipo,context);
		ret = executeCommandList( context );
		return ret;
	}
	
	public String executeCommandToRiprendiPartita( ActionContext context )
	{
		String		ret	= "ToRiprendiPartitaOK";
		
		getConfigTipo(context);

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
			
			b = GenericBean.load(id, db, configTipo);
			
			Organization org = new Organization( db, b.getId_macello() );
			
			Integer idOggetto = b.getId();
			ArrayList<Esito> esiti = Esito.load( Integer.parseInt(id), configTipo,db );
			
			
			ArrayList sedList = Partita.loadSeduteWhitoutPartita(db, idOggetto, configTipo);
			Iterator<PartitaSeduta> sedute = sedList.iterator();
			int numCapiOviniPartita_AltreSedute = 0;
			int numCapiCapriniPartita_AltreSedute = 0;
			while(sedute.hasNext())
			{
				PartitaSeduta temp = sedute.next();
				Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
				int numCapiOviniEsitiSeduta = 0;
				int numCapiCapriniEsitiSeduta = 0;
				while(esitiSeduta.hasNext())
				{
					Esito esitoTemp = esitiSeduta.next();
					numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
					numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
				}
				numCapiOviniPartita_AltreSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
				numCapiCapriniPartita_AltreSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
			}
			Iterator<Esito> esitiPartita = Esito.load(((Partita)b).getId(), configTipo, db).iterator();
			int numCapiOviniEsitiPartita = 0;
			int numCapiCapriniEsitiPartita = 0;
			while(esitiPartita.hasNext())
			{
				Esito esitoTemp = esitiPartita.next();
				numCapiOviniEsitiPartita = esitoTemp.getNum_capi_ovini();
				numCapiCapriniEsitiPartita = esitoTemp.getNum_capi_caprini();
			}
			numCapiOviniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_ovini()+((Partita)b).getCasl_num_capi_ovini()+numCapiOviniEsitiPartita;
			numCapiCapriniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_caprini()+((Partita)b).getCasl_num_capi_caprini()+numCapiCapriniEsitiPartita;
			
			context.getRequest().setAttribute( "numCapiOviniPartita_AltreSedute", numCapiOviniPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniPartita_AltreSedute", numCapiCapriniPartita_AltreSedute+"" );
			
			int numCapiOviniDestPartita_AltreSedute = 0;
			int numCapiCapriniDestPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute2 = sedList.iterator();
			while(sedute2.hasNext())
			{
				PartitaSeduta temp = sedute2.next();
				int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
				int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
				numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
				numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
			}
			int numCapiOviniDestPartita = ((Partita)b).getVam_num_capi_ovini();
			int numCapiCapriniDestPartita = ((Partita)b).getVam_num_capi_caprini();
			numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestPartita;
			numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestPartita;
			context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );
			
			
			int numCapiOviniMavamPartita_AltreSedute = 0;
			int numCapiCapriniMavamPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute3 = sedList.iterator();
			while(sedute3.hasNext())
			{
				PartitaSeduta temp = sedute3.next();
				int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
				int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
				numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
				numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
			}
			int numCapiOviniMavamPartita = (((Partita)b)).getMavam_num_capi_ovini();
			int numCapiCapriniMavamPartita = (((Partita)b)).getMavam_num_capi_caprini();
			numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamPartita;
			numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamPartita;
			context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			
			if(configTipo.hasMoreDestinatari())
			{
				Object numDestinatariSelezionati = ReflectionUtil.invocaMetodo(b, "getNumDestinatariSelezionati", new Class[]{}, new Object[]{});
				context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
			}
			
			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			
			ArrayList<NonConformita>		ncsVAM					= NonConformita.load	( idOggetto, configTipo,db );
			ArrayList<CategoriaRischio>		categorie					= CategoriaRischio.load	( idOggetto, configTipo,db );
			ArrayList<Campione> 			campioni 				= Campione.load			( idOggetto, configTipo,db );
			Tampone			tampone 				= Tampone.load			( idOggetto, configTipo,db );
			TreeMap<Integer, ArrayList<Organi>> organiNew = new TreeMap<Integer, ArrayList<Organi>>();
			ArrayList<PatologiaRilevata>	patologieRilevate		= PatologiaRilevata.load( idOggetto, configTipo, db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( idOggetto, configTipo, db );

			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( idOggetto, configTipo, db );


			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );
			context.getRequest().setAttribute( "Categorie", categorie );
			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Tampone", tampone );
			context.getRequest().setAttribute( "Esiti", esiti );

			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );

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

		context.getRequest().setAttribute( "Update", new Boolean( false ) );

		caricaLookup( context, configTipo );

		return ret;
	}
	
	public String executeCommandToChiudiPartita( ActionContext context )
	{
		getConfigTipo(context);
		String		ret	= "ToChiudiPartitaOK";
		
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
			
			context.getRequest().setAttribute("id", id);
			
			b = GenericBean.load(id, db, configTipo);
			
			Organization org = null;
			org = new Organization( db, b.getId_macello() );
			
			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );


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

		caricaLookup( context, configTipo );

		return ret;
	}
	
	public String executeCommandToMostraMotivazioni( ActionContext context )
	{
		getConfigTipo(context);
		String		ret	= "ToMostraMotivazioniOK";
		
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
			
			context.getRequest().setAttribute("id", id);
			
			b = GenericBean.load(id, db, configTipo);
			
			Organization org = null;
			org = new Organization( db, b.getId_macello() );
			
			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );


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

		caricaLookup( context, configTipo );

		return ret;
	}

	public String executeCommandModificaCapo( ActionContext context )
	{
		getConfigTipo(context);
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
			
			int codiceUnivoco = GenericBean.nextId(db, configTipo);
			context.getRequest().setAttribute( "codiceUnivoco", codiceUnivoco+"" );
			Date d = new Date();
			context.getRequest().setAttribute( "anno", 1900 + d.getYear() + "" );
			int asl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			String aslString ="";
			
			//Gestisco casi in cui l'utente non ha ASL
			if (asl<0) 
				aslString = "0";
			else
				aslString = String.valueOf(asl);
			
			//Padding a tre cifre
			while (aslString.length()<3){
				aslString = "0"+aslString;
			}
			
			context.getRequest().setAttribute( "asl", aslString);
			
			//loadOggetto(id, tipo, db);
			b = GenericBean.load(id, db, configTipo);
			
			Organization org = null;
			org = new Organization( db, b.getId_macello() );
			
			if(configTipo.hasSeduteMacellazione())
			{
				ArrayList sedList = Partita.loadSeduteWhitoutPartita(db, b.getId(), configTipo);
				Iterator<PartitaSeduta> sedute = sedList.iterator();
				int numCapiOviniSedute = 0;
				int numCapiCapriniSedute = 0;
				while(sedute.hasNext())
				{
					PartitaSeduta temp = sedute.next();
					Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
					int numCapiOviniEsitiSeduta = 0;
					int numCapiCapriniEsitiSeduta = 0;
					while(esitiSeduta.hasNext())
					{
						Esito esitoTemp = esitiSeduta.next();
						numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
						numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
					}
					numCapiOviniSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
					numCapiCapriniSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
				}
				context.getRequest().setAttribute( "numCapiOviniSedute", numCapiOviniSedute+"" );
				context.getRequest().setAttribute( "numCapiCapriniSedute", numCapiCapriniSedute+"" );
				
				int numCapiOviniDestPartita_AltreSedute = 0;
				int numCapiCapriniDestPartita_AltreSedute = 0;
				Iterator<PartitaSeduta> sedute2 = sedList.iterator();
				while(sedute2.hasNext())
				{
					PartitaSeduta temp = sedute2.next();
					int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
					int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
					numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
					numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
				}
				context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
				context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );

				
				int numCapiOviniMavamPartita_AltreSedute = 0;
				int numCapiCapriniMavamPartita_AltreSedute = 0;
				Iterator<PartitaSeduta> sedute3 = sedList.iterator();
				while(sedute3.hasNext())
				{
					PartitaSeduta temp = sedute3.next();
					int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
					int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
					numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
					numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
				}
				context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
				context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			}
			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
			if(speditore.getAddressList().size() > 0){
				OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
				context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
			}*/
			
			Integer idOggetto = b.getId();
			ArrayList<Esito> esiti = null;
			if(configTipo.getEsitiMultipli())
				esiti = Esito.load( Integer.parseInt(id), configTipo,db );
			ArrayList<NonConformita>		ncsVAM					= NonConformita.load	( idOggetto, configTipo,db );
			ArrayList<CategoriaRischio>		categorie					= CategoriaRischio.load	( idOggetto, configTipo,db );
			ArrayList<Campione> 			campioni 				= Campione.load			( idOggetto, configTipo,db );
			Tampone			tampone 				= Tampone.load			( idOggetto, configTipo,db );
			ArrayList<Organi>   			organi		 			= Organi.loadByOrgani	( idOggetto, configTipo,db );
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
			ArrayList<PatologiaRilevata>	patologieRilevate		= PatologiaRilevata.load( idOggetto, configTipo, db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( idOggetto, configTipo, db );

			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( idOggetto, configTipo, db );

			CapoLogDao capoLogDao = null;
			PartitaLogDao partitaLogDao = null; 
			PartitaLog partitaLog = null;
			CapoLog capoLog = null;
			capoLogDao = CapoLogDao.getInstance();
			
			b_old = (GenericBean) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			vecchioBean	= loadBean(context, configTipo);
			
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(vecchioBean);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null,null);
			capoLog = capoLogDao.select(db, identificativo);
			
			if(configTipo.hasMoreDestinatari())
			{
				Object numDestinatariSelezionati = ReflectionUtil.invocaMetodo(b, "getNumDestinatariSelezionati", new Class[]{}, new Object[]{});
				context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
			}
			
			context.getRequest().setAttribute( configTipo.getNomeBeanJsp(), b );
			context.getRequest().setAttribute( "CapoLog", capoLog );
			context.getRequest().setAttribute( "PartitaLog", partitaLog );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );
			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Categorie", categorie );
			context.getRequest().setAttribute( "Tampone", tampone );
			context.getRequest().setAttribute( "Esiti", esiti );
			
			Iterator<Esito> esitiIter = esiti.iterator();
			ArrayList<Integer> esitiId = new ArrayList<Integer>();
			while(esitiIter.hasNext())
			{
				esitiId.add(esitiIter.next().getId_esito());
			}
			context.getRequest().setAttribute( "EsitiId", esitiId );
			
			Iterator<CategoriaRischio> categorieIter = categorie.iterator();
			ArrayList<Integer> categorieId = new ArrayList<Integer>();
			while(categorieIter.hasNext())
			{
				categorieId.add(categorieIter.next().getId_categoria());
			}
			context.getRequest().setAttribute( "CategorieId", categorieId );

			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );

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

		caricaLookup( context, configTipo );

		return ret;
	}
	
	public String executeCommandModificaSedutaCampioniSenzaEsito( ActionContext context )
	{
		getConfigTipo(context);
		String		ret	= "ModificaSedutaCampioniSenzaEsitoOK";
		
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
			
			PartitaSeduta.load(id, db, configTipo);
			
			Organization org = new Organization( db, p_seduta.getId_macello() );
			/*etIdorg.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
			if(speditore.getAddressList().size() > 0){
				OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
				context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
			}*/
			
			Integer idOggetto = p_seduta.getId();
			p_seduta = PartitaSeduta.load(idOggetto+"", db, configTipo);
			Partita p = (Partita)Partita.load(p_seduta.getId_partita()+"", db, configTipo);
			
			ArrayList sedList = Partita.loadSeduteWhitoutPartita(db, p_seduta.getId_partita(), configTipo);
			Iterator<PartitaSeduta> sedute = sedList.iterator();
			int numCapiOviniPartita_AltreSedute = 0;
			int numCapiCapriniPartita_AltreSedute = 0;
			while(sedute.hasNext())
			{
				PartitaSeduta temp = sedute.next();
				if(temp.getId()!=p_seduta.getId())
				{
					Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
					int numCapiOviniEsitiSeduta = 0;
					int numCapiCapriniEsitiSeduta = 0;
					while(esitiSeduta.hasNext())
					{
						Esito esitoTemp = esitiSeduta.next();
						numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
						numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
					}
					numCapiCapriniPartita_AltreSedute+=temp.getMavam_num_capi_caprini()+numCapiCapriniEsitiSeduta;
					numCapiOviniPartita_AltreSedute+=temp.getMavam_num_capi_ovini()+numCapiOviniEsitiSeduta;
				}
			}
			
			int numCapiOviniDestPartita_AltreSedute = 0;
			int numCapiCapriniDestPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute2 = sedList.iterator();
			while(sedute2.hasNext())
			{
				PartitaSeduta temp = sedute2.next();
				if(temp.getId()!=p_seduta.getId())
				{
					int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
					int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
					numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
					numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
				}
			}
			int numCapiOviniDestPartita = ((Partita)b).getVam_num_capi_ovini();
			int numCapiCapriniDestPartita = ((Partita)b).getVam_num_capi_caprini();
			numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestPartita;
			numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestPartita;
			context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );
			
			int numCapiOviniMavamPartita_AltreSedute = 0;
			int numCapiCapriniMavamPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute3 = sedList.iterator();
			while(sedute3.hasNext())
			{
				PartitaSeduta temp = sedute3.next();
				if(temp.getId()!=p_seduta.getId())
				{
					int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
					int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
					numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
					numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
				}
			}
			int numCapiCapriniMavamPartita = (((Partita)b)).getMavam_num_capi_caprini();
			int numCapiOviniMavamPartita = (((Partita)b)).getMavam_num_capi_ovini();
			numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamPartita;
			numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamPartita;
			context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			
			Partita temp = (Partita)GenericBean.load(p_seduta.getId_partita()+"", db,configTipo);
			Iterator<Esito> esitiPartita = Esito.load(((Partita)b).getId(), configTipo, db).iterator();
			int numCapiOviniEsitiPartita = 0;
			int numCapiCapriniEsitiPartita = 0;
			while(esitiPartita.hasNext())
			{
				Esito esitoTemp = esitiPartita.next();
				numCapiOviniEsitiPartita = esitoTemp.getNum_capi_ovini();
				numCapiCapriniEsitiPartita = esitoTemp.getNum_capi_caprini();
			}
			numCapiOviniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_ovini()+((Partita)b).getCasl_num_capi_ovini()+numCapiOviniEsitiPartita;
			numCapiCapriniPartita_AltreSedute+=((Partita)b).getMavam_num_capi_caprini()+((Partita)b).getCasl_num_capi_caprini()+numCapiCapriniEsitiPartita;
			
			context.getRequest().setAttribute( "numCapiOviniPartita_AltreSedute", numCapiOviniPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniPartita_AltreSedute", numCapiCapriniPartita_AltreSedute+"" );
			
			
			ArrayList<Esito> 				esiti 					= PartitaSeduta.loadEsito( idOggetto,db );
			ArrayList<NonConformita>		ncsVAM					= PartitaSeduta.loadNonConformita	( idOggetto,db );
			ArrayList<CategoriaRischio>		categorie					= CategoriaRischio.load(p_seduta.getId_partita(), configTipo, db);
			ArrayList<Campione> 			campioni 				= PartitaSeduta.loadCampioni			( idOggetto,db );
			Tampone			tampone 				= PartitaSeduta.loadTampone			( idOggetto,db );
			ArrayList<Organi>   			organi		 			= PartitaSeduta.loadOrgani	( idOggetto,db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( p_seduta.getId_partita(), configTipo, db );
			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( p_seduta.getId_partita(), configTipo, db );
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
			ArrayList<PatologiaRilevata>	patologieRilevate		= PartitaSeduta.loadPatologieRilevate( idOggetto, db );

			CapoLogDao capoLogDao = null;
			PartitaLogDao partitaLogDao = null; 
			PartitaLog partitaLog = null;
			CapoLog capoLog = null;
			partitaLogDao = PartitaLogDao.getInstance();
			
			b_old = (GenericBean) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			vecchioBean	= loadBean(context, configTipo);
			
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(vecchioBean);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
			partitaLog = partitaLogDao.select(db, identificativo);
			
			p_seduta.popolaCampiByPartita(db,configTipo);
			
			if(configTipo.hasMoreDestinatari())
			{
				Object numDestinatariSelezionati = p_seduta.getNumDestinatariSelezionati();
				context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
			}
			
			context.getRequest().setAttribute( "Partita", p_seduta );
			context.getRequest().setAttribute( "PartitaPrincipale", p );
			context.getRequest().setAttribute( "CapoLog", capoLog );
			context.getRequest().setAttribute( "PartitaLog", partitaLog );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );
			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Tampone", tampone );
			context.getRequest().setAttribute( "Esiti", esiti );
			context.getRequest().setAttribute( "Categorie", categorie );
			
			Iterator<Esito> esitiIter = esiti.iterator();
			ArrayList<Integer> esitiId = new ArrayList<Integer>();
			while(esitiIter.hasNext())
			{
				esitiId.add(esitiIter.next().getId_esito());
			}
			context.getRequest().setAttribute( "EsitiId", esitiId );

			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
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

		caricaLookup( context, configTipo );

		return ret;
	}
	
	public String executeCommandModificaSeduta( ActionContext context )
	{
		getConfigTipo(context);
		String		ret	= "ModificaSedutaOK";
		
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
			
			PartitaSeduta.load(id, db, configTipo);
			
			Organization org = new Organization( db, p_seduta.getId_macello() );
			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
			if(speditore.getAddressList().size() > 0){
				OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
				context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
			}*/
			
			Integer idOggetto = p_seduta.getId();
			p_seduta = PartitaSeduta.load(idOggetto+"", db, configTipo);
			Partita p = (Partita)Partita.load(p_seduta.getId_partita()+"", db, configTipo);
			
			ArrayList sedList = Partita.loadSeduteWhitoutPartita(db, p_seduta.getId_partita(), configTipo);
			Iterator<PartitaSeduta> sedute = sedList.iterator();
			int numCapiOviniPartita_AltreSedute = 0;
			int numCapiCapriniPartita_AltreSedute = 0;
			while(sedute.hasNext())
			{
				PartitaSeduta temp = sedute.next();
				if(temp.getId()!=p_seduta.getId())
				{
					Iterator<Esito> esitiSeduta = temp.loadEsito(temp.getId(), db).iterator();
					int numCapiOviniEsitiSeduta = 0;
					int numCapiCapriniEsitiSeduta = 0;
					while(esitiSeduta.hasNext())
					{
						Esito esitoTemp = esitiSeduta.next();
						numCapiOviniEsitiSeduta = esitoTemp.getNum_capi_ovini();
						numCapiCapriniEsitiSeduta = esitoTemp.getNum_capi_caprini();
					}
					numCapiCapriniPartita_AltreSedute+=temp.getMavam_num_capi_caprini()+temp.getVam_num_capi_caprini();
					numCapiOviniPartita_AltreSedute+=temp.getMavam_num_capi_ovini()+temp.getVam_num_capi_ovini();
				}
			}
			
			int numCapiOviniDestPartita_AltreSedute = 0;
			int numCapiCapriniDestPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute2 = sedList.iterator();
			while(sedute2.hasNext())
			{
				PartitaSeduta temp = sedute2.next();
				if(temp.getId()!=p_seduta.getId())
				{
					int numCapiOviniDestSeduta = temp.getVam_num_capi_ovini();
					int numCapiCapriniDestSeduta = temp.getVam_num_capi_caprini();
					numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestSeduta;
					numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestSeduta;
				}
			}
			int numCapiOviniDestPartita = ((Partita)b).getVam_num_capi_ovini();
			int numCapiCapriniDestPartita = ((Partita)b).getVam_num_capi_caprini();
			numCapiOviniDestPartita_AltreSedute+=numCapiOviniDestPartita;
			numCapiCapriniDestPartita_AltreSedute+=numCapiCapriniDestPartita;
			context.getRequest().setAttribute( "numCapiOviniDestPartita_AltreSedute", numCapiOviniDestPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniDestPartita_AltreSedute", numCapiCapriniDestPartita_AltreSedute+"" );
			
			int numCapiOviniMavamPartita_AltreSedute = 0;
			int numCapiCapriniMavamPartita_AltreSedute = 0;
			Iterator<PartitaSeduta> sedute3 = sedList.iterator();
			while(sedute3.hasNext())
			{
				PartitaSeduta temp = sedute3.next();
				if(temp.getId()!=p_seduta.getId())
				{
					int numCapiOviniMavamSeduta = temp.getMavam_num_capi_ovini();
					int numCapiCapriniMavamSeduta = temp.getMavam_num_capi_caprini();
					numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamSeduta;
					numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamSeduta;
				}
			}
			int numCapiCapriniMavamPartita = (((Partita)b)).getMavam_num_capi_caprini();
			int numCapiOviniMavamPartita = (((Partita)b)).getMavam_num_capi_ovini();
			numCapiOviniMavamPartita_AltreSedute+=numCapiOviniMavamPartita;
			numCapiCapriniMavamPartita_AltreSedute+=numCapiCapriniMavamPartita;
			context.getRequest().setAttribute( "numCapiOviniMavamPartita_AltreSedute", numCapiOviniMavamPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniMavamPartita_AltreSedute", numCapiCapriniMavamPartita_AltreSedute+"" );
			
			Partita temp = (Partita)GenericBean.load(p_seduta.getId_partita()+"", db,configTipo);
			Iterator<Esito> esitiPartita = Esito.load(((Partita)b).getId(), configTipo, db).iterator();
			int numCapiOviniEsitiPartita = 0;
			int numCapiCapriniEsitiPartita = 0;
			while(esitiPartita.hasNext())
			{
				Esito esitoTemp = esitiPartita.next();
				numCapiOviniEsitiPartita = esitoTemp.getNum_capi_ovini();
				numCapiCapriniEsitiPartita = esitoTemp.getNum_capi_caprini();
			}
			numCapiOviniPartita_AltreSedute+=((Partita)b).getVam_num_capi_ovini()+((Partita)b).getMavam_num_capi_ovini()+((Partita)b).getCasl_num_capi_ovini();
			numCapiCapriniPartita_AltreSedute+=((Partita)b).getVam_num_capi_caprini()+((Partita)b).getMavam_num_capi_caprini()+((Partita)b).getCasl_num_capi_caprini();
			
			context.getRequest().setAttribute( "numCapiOviniPartita_AltreSedute", numCapiOviniPartita_AltreSedute+"" );
			context.getRequest().setAttribute( "numCapiCapriniPartita_AltreSedute", numCapiCapriniPartita_AltreSedute+"" );
			
			
			ArrayList<Esito> 				esiti 					= PartitaSeduta.loadEsito( idOggetto,db );
			ArrayList<NonConformita>		ncsVAM					= PartitaSeduta.loadNonConformita	( idOggetto,db );
			ArrayList<CategoriaRischio>		categorie					= CategoriaRischio.load(p_seduta.getId_partita(), configTipo, db);
			ArrayList<Campione> 			campioni 				= PartitaSeduta.loadCampioni			( idOggetto,db );
			Tampone			tampone 				= PartitaSeduta.loadTampone			( idOggetto,db );
			ArrayList<Organi>   			organi		 			= PartitaSeduta.loadOrgani	( idOggetto,db );
			ArrayList<ProvvedimentiCASL>	provvedimenti			= ProvvedimentiCASL.load( p_seduta.getId_partita(), configTipo, db );
			ArrayList<Casl_Non_Conformita_Rilevata>	casl_NCRilevate	= Casl_Non_Conformita_Rilevata.load( p_seduta.getId_partita(), configTipo, db );
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
			ArrayList<PatologiaRilevata>	patologieRilevate		= PartitaSeduta.loadPatologieRilevate( idOggetto, db );

			CapoLogDao capoLogDao = null;
			PartitaLogDao partitaLogDao = null; 
			PartitaLog partitaLog = null;
			CapoLog capoLog = null;
			partitaLogDao = PartitaLogDao.getInstance();
			
			b_old = (GenericBean) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			vecchioBean	= loadBean(context, configTipo);
			
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(vecchioBean);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
			partitaLog = partitaLogDao.select(db, identificativo);
			
			p_seduta.popolaCampiByPartita(db,configTipo);
			
			if(configTipo.hasMoreDestinatari())
			{
				Object numDestinatariSelezionati = p_seduta.getNumDestinatariSelezionati();
				context.getRequest().setAttribute( "righeDestDefault",  numDestinatariSelezionati);
			}
			
			context.getRequest().setAttribute( "Partita", p_seduta );
			context.getRequest().setAttribute( "PartitaPrincipale", p );
			context.getRequest().setAttribute( "CapoLog", capoLog );
			context.getRequest().setAttribute( "PartitaLog", partitaLog );
			context.getRequest().setAttribute( "OrgDetails", org );
			//context.getRequest().setAttribute( "Speditore", speditore );
			context.getRequest().setAttribute( "NCVAM", ncsVAM );
			context.getRequest().setAttribute( "Categorie", categorie );
			context.getRequest().setAttribute( "Tampone", tampone );
			context.getRequest().setAttribute( "Esiti", esiti );
			
			Iterator<Esito> esitiIter = esiti.iterator();
			ArrayList<Integer> esitiId = new ArrayList<Integer>();
			while(esitiIter.hasNext())
			{
				esitiId.add(esitiIter.next().getId_esito());
			}
			context.getRequest().setAttribute( "EsitiId", esitiId );

			context.getRequest().setAttribute( "casl_NCRilevate", casl_NCRilevate );
			context.getRequest().setAttribute( "Campioni", campioni );
			context.getRequest().setAttribute( "OrganiList", organi );
			context.getRequest().setAttribute( "OrganiListNew", organiNew );
			context.getRequest().setAttribute( "PatologieRilevate", patologieRilevate );
			context.getRequest().setAttribute( "casl_Provvedimenti_effettuati", provvedimenti );
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

		caricaLookup( context, configTipo );

		return ret;
	}

	public String executeCommandSave( ActionContext context )
	{ 
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		boolean clona = ( context.getRequest().getParameter("clona") != null && context.getRequest().getParameter("clona").equals("si") );

		Connection db = null;
		try
		{
			db = this.getConnection( context );
			
			b = loadBean(context, configTipo);
			
			//Verifica univocita' dell'identificato dell'oggetto in questione: matricola per i capi, partita per le partite, ...
			String ret2 = null;
			String identificativo = (String)ReflectionUtil.invocaMetodo(b, configTipo.getNomeMetodoIdentificativo(), null);
			if((ret2 = controlloUnivocita(configTipo, context, db, identificativo, b))!=null)
				return ret2;
			
			//Controllo stampa del modello 10
			ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,false))!=null)
				return ret2;
			
			//Controllo progressivo di Macellazione
			ret2 = null;
			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, false, null, null))!=null)
				return ret2;
			
			ArrayList<Campione> cmps = loadCampioni(context, configTipo,b);

			
				b.setEntered_by( this.getUserId(context) );
				b = b.store( db, cmps, configTipo );
		
			int enteredBy = this.getUserId(context);
			int modifiedBy = this.getUserId(context);
			Timestamp entered = null;
			Timestamp modified = null;
			Integer idCapo = null;
			Integer idMacello = null;
			Integer sedutaMacellazione = null;
			Timestamp dataMacellazione = null;
			idCapo = b.getId();
			entered = b.getEntered();
			modified = b.getModified();
			idMacello = b.getId_macello();
			sedutaMacellazione = b.getCd_seduta_macellazione();
			dataMacellazione = b.getVpm_data();
			
			context.getRequest().setAttribute("id", idCapo);

			//Inizio Log del capo in m_capi_log


//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());

			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());


			
			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
			

			/*if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}*/


			String[] patologieRilevate = context.getRequest().getParameterValues( "vpm_patologie_rilevate" );
			patologieRilevate = (patologieRilevate == null) ? (new String[0]) : (patologieRilevate);
			for( String index: patologieRilevate )
			{
				if( !"-1".equals( index ) )
				{
					PatologiaRilevata pr = new PatologiaRilevata();
					ReflectionUtil.invocaMetodo(pr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					pr.setId_patologia	( Integer.parseInt( index ) );
					pr.setEntered		( entered);
					pr.setModified		( entered );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}
			
			String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
			esiti = (esiti == null) ? (new String[0]) : (esiti);
			for( String index: esiti )
			{
				if( !"-1".equals( index ) )
				{
					Esito e = new Esito();
					e.setId_partita		( idCapo );
					e.setId_esito   	( Integer.parseInt( index ) );
					e.setEntered		( entered);
					e.setModified		( entered );
					e.setEntered_by	( getUserId(context) );
					e.setModified_by	( getUserId(context) );
					e.setNum_capi_ovini   ( intero("num_capi_ovini_esito_"+index, context));
					e.setNum_capi_caprini ( intero("num_capi_caprini_esito_"+index, context));
					e.store( db );
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
					ReflectionUtil.invocaMetodo(nc, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					nc.setId_casl_non_conformita( Integer.parseInt( index ));
					nc.setEntered				( entered);
					nc.setModified				( entered );
					nc.setEntered_by			( getUserId(context) );
					nc.setModified_by			( getUserId(context) );
					nc.store( db );
				}
			}

			ArrayList<NonConformita> ncs = loadNC( context );
			for( NonConformita nc: ncs )
			{
				nc.setEntered		( entered );
				nc.setModified		( entered );
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
					ReflectionUtil.invocaMetodo(pr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					pr.setId_provvedimento		( Integer.parseInt( index ));
					pr.setEntered				( entered );
					pr.setModified				( entered );
					pr.setEntered_by			(  getUserId(context) );
					pr.setModified_by			( getUserId(context) );
					pr.store( db );
				}
			}
			
			String[] categorie_selezionate = context.getRequest().getParameterValues( "categorie_selezionate" );
			categorie_selezionate = (categorie_selezionate == null) ? (new String[0]) : (categorie_selezionate);
			for( String index: categorie_selezionate )
			{
				if( !"-1".equals( index ) )
				{
					CategoriaRischio c = new CategoriaRischio();
					ReflectionUtil.invocaMetodo(c, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					c.setId_categoria		( Integer.parseInt( index ));
					c.setEntered				( entered );
					c.setModified				( entered );
					c.setEntered_by			(  getUserId(context) );
					c.setModified_by			( getUserId(context) );
					c.store( db );
				}
			}

			for( Campione camp: cmps )
			{
				ReflectionUtil.invocaMetodo(camp, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
				camp.setEntered		( entered );
				camp.setModified	( entered );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db, configTipo );
			}
			Tampone tampone = loadTampone( context );
			
			tampone.setEntered		( entered );
			tampone.setId_macello(idMacello);
			tampone.setSessione_macellazione(sedutaMacellazione);
			tampone.setData_macellazione(dataMacellazione);
			tampone.setModified	( entered );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db ,context);
				
			}
			
			tampone.associa_tampone_capo(b, db, configTipo);
			}
			
			ArrayList<Organi> organi = loadOrgani( context);
			for( Organi org: organi )
			{
				ReflectionUtil.invocaMetodo(org, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[]{b.getId()});
				org.setEntered		( entered );
				org.setModified		( entered );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
			}

			settaStorico(configTipo,db);
				
			//c.storico x provvedimenti
			//c.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( c.getId(), db );

			context.getRequest().setAttribute( "messaggio",  configTipo.getMessaggioCapoAggiunto() );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
			if(e1.getMessage().contains("m_evita_matricole_duplicate")){
				context.getRequest().setAttribute( "messaggio", "Errore: Matricola gia' esistente" );
			}
			else if(e1.getMessage().contains("m_evita_partite_duplicate")){
				context.getRequest().setAttribute( "messaggio", "Errore: Partita gia' esistente per questo macello e specie" );
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
			clona(configTipo,context);
			ret = executeCommandNuovoCapo( context );
		}
		else
		{
			context.getRequest().setAttribute("OrgId", b.getId_macello());
			ret = executeCommandList( context );
		}

		return ret;
	}

	public String executeCommandUpdate( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			GenericBean vecchioCapo = GenericBean.load( context.getParameter( "id" ), db, configTipo );
			Integer idVecchioCapo = vecchioCapo.getId();

			ArrayList<PatologiaRilevata>	vecchiePatologie		= PatologiaRilevata.load( idVecchioCapo, configTipo,db );
			ArrayList<Esito>	vecchiEsiti		                    = Esito.load( idVecchioCapo, configTipo,db );
			ArrayList<Casl_Non_Conformita_Rilevata>	vecchieCaslNC	= Casl_Non_Conformita_Rilevata.load( idVecchioCapo, configTipo,db );
			ArrayList<ProvvedimentiCASL>	vecchiProvvedimenti		= ProvvedimentiCASL.load( idVecchioCapo, configTipo,db );
			ArrayList<CategoriaRischio>	vecchieCategorie		= CategoriaRischio.load( idVecchioCapo, configTipo,db );
			ArrayList<NonConformita>		vecchieNcs				= NonConformita.load( idVecchioCapo, configTipo,db );
			ArrayList<Campione>				vecchiCampioni			= Campione.load( idVecchioCapo, configTipo,db );
			ArrayList<Organi>				vecchiOrgani			= Organi.loadByOrgani( idVecchioCapo, configTipo,db );
			
			vecchioCapo.storico_lcso_organi				= vecchiOrgani;
			vecchioCapo.storico_vam_non_conformita		= vecchieNcs;
			vecchioCapo.storico_vpm_campioni			= vecchiCampioni;
			vecchioCapo.storico_vpm_patologie_rilevate	= vecchiePatologie;
			
			b = loadBean(context, configTipo);

			//Verifica univocita' dell'identificato dell'oggetto in questione: matricola per i capi, partita per le partite, ...
			String ret2 = null;
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
			if(configTipo.controlloUnivocitaUpdate())
			{
				if((ret2 = controlloUnivocitaUpdate(configTipo, context, db, identificativo))!=null)
					return ret2;
			}
			ret2 = null;
			//Controllo progressivo di Macellazione
			ret2 = null;
			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, true,vecchioBean,identificativo))!=null)
				return ret2;
			
			
			//Controllo stampa del modello 10
			ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,true))!=null)
				return ret2;

			
			Integer enteredBy = null;
			Integer modifiedBy = null;
			Timestamp entered = null;
			Timestamp modified = null;
			Integer idCapo = null;
			Integer idMacello = null;
			Integer sedutaMacellazione = null;
			Timestamp dataMacellazione = null;
			idCapo = b.getId();
			entered =  b.getEntered() ;
			modified = new Timestamp( System.currentTimeMillis() ) ;
			enteredBy = b.getEntered_by();
			idMacello = b.getId_macello();
			sedutaMacellazione = b.getCd_seduta_macellazione();
			dataMacellazione = b.getVpm_data();
			
			ArrayList<Campione>	campioni		= loadCampioni( context, configTipo,b );
			
			b.update		( db , campioni, configTipo );
			
			Tampone tampone = loadTampone( context );
			
			tampone.setEntered		(entered);
			tampone.setId_macello(idMacello);
			tampone.setSessione_macellazione(sedutaMacellazione);

			tampone.setData_macellazione(dataMacellazione);
			tampone.setModified	( entered );
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
			tampone.cancella_tampone_capo(b, db, configTipo);
			tampone.associa_tampone_capo( b, db, configTipo);
			
			}
			else
			{
				tampone.cancella_tampone_capo(b, db, configTipo);
			}


			//Inizio Log del capo in m_capi_log
			/*org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;

			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}*/

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
						vp.setModified		( modified );
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
					ReflectionUtil.invocaMetodo(pr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					pr.setId_patologia	( Integer.parseInt( pat ) );
					pr.setEntered		( entered );
					pr.setModified		( entered );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}
			
			if(configTipo.getEsitiMultipli())
			{
				String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
				esiti = (esiti == null) ? (new String[0]) : (esiti);
				//eliminare le patologie non piu selezionate ed aggiorno le altre
				for( Esito e: vecchiEsiti)
				{
					boolean cancellare = true;
					for( String es: esiti )
					{
						if( e.getId_esito() == Integer.parseInt( es ) )
						{
							cancellare = false;

							//update delle vecchie patologie
							e.setModified		( modified );
							e.setModified_by	( getUserId(context) );
							e.setNum_capi_ovini   ( intero("num_capi_ovini_esito_"+es, context));
							e.setNum_capi_caprini ( intero("num_capi_caprini_esito_"+es, context));
							e.update( db );
						}

						if( cancellare ) //cancellazione delle patologie rimosse
						{
							Esito.delete( e.getId(), getUserId(context), db );
						}
					}
				}
				//inserisco le nuove patologie
				for( String es: esiti )
				{
					boolean inserire = true;
					for( Esito e: vecchiEsiti)
					{
						if( e.getId_esito() == Integer.parseInt( es ) )
						{
							inserire = false;
						}
					}

					if( !"-1".equals( es ) && inserire )
					{
						Esito e = new Esito();
						e.setId_partita		( idCapo );
						e.setId_esito   	( Integer.parseInt( es ) );
						e.setEntered		( entered );
						e.setModified		( entered );
						e.setEntered_by	    ( getUserId(context) );
						e.setModified_by	( getUserId(context) );
						e.setNum_capi_ovini   ( intero("num_capi_ovini_esito_"+es, context));
						e.setNum_capi_caprini ( intero("num_capi_caprini_esito_"+es, context));
						e.store( db );
					}
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
						vp.setModified		( modified );
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
					ReflectionUtil.invocaMetodo(pr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					pr.setId_casl_non_conformita( Integer.parseInt( pat ) );
					pr.setEntered				( entered );
					pr.setModified				( entered );
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
						vp.setModified		( modified );
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
					ReflectionUtil.invocaMetodo(pr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					pr.setId_provvedimento	( Integer.parseInt( pro ) );
					pr.setEntered			( entered );
					pr.setModified			( entered );
					pr.setEntered_by		( getUserId(context) );
					pr.setModified_by		( getUserId(context) );
					pr.store( db );
				}
			}
			
			
			String[] categorie = context.getRequest().getParameterValues( "categorie_selezionate" );
			categorie = (categorie == null) ? (new String[0]) : (categorie);
			for( CategoriaRischio vc: vecchieCategorie )
			{
				boolean cancellare = true;
				for( String cat: categorie )
				{
					if( vc.getId_categoria() == Integer.parseInt( cat ) )
					{
						cancellare = false;

						//update delle vecchie provvedimenti
						vc.setModified		( modified );
						vc.setModified_by	( getUserId(context) );
						vc.update( db );
					}

					if( cancellare ) //cancellazione delle provvedimenti rimosse
					{
						CategoriaRischio.delete( vc.getId(), getUserId(context), db );
					}
				}
			}
			for( String cat: categorie )
			{
				boolean inserire = true;
				for( CategoriaRischio vc: vecchieCategorie )
				{
					if( vc.getId_categoria() == Integer.parseInt( cat ) )
					{
						inserire = false;
					}
				}

				if( !"-1".equals( cat ) && inserire )
				{
					CategoriaRischio cr = new CategoriaRischio();
					ReflectionUtil.invocaMetodo(cr, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {idCapo});
					cr.setId_categoria	( Integer.parseInt( cat ) );
					cr.setEntered			( entered );
					cr.setModified			( entered );
					cr.setEntered_by		( getUserId(context) );
					cr.setModified_by		( getUserId(context) );
					cr.store( db );
				}
			}

			ArrayList<NonConformita> ncs = loadNC( context);
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
						nc.setModified		( modified );
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
					nc.setModified		( entered );
					nc.setModified_by	( getUserId(context) );
					nc.setEntered		( entered );
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
						camp.setModified	( modified );
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
					camp.setEntered		( entered );
					camp.setModified	( entered );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			

			ArrayList<Organi>	organi			= loadOrgani(context);
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
				ReflectionUtil.invocaMetodo(org, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[]{b.getId()});
				org.setEntered		( entered );
				org.setModified		( entered );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
				//				}
			}

			GenericBean nuovoCapo = null;
			
			nuovoCapo = GenericBean.load( b.getId() + "", db, configTipo );
			
			settaStorico(configTipo, db);

			//nuovoCapo.storico_vpm_non_conformita_rilevate	= PatologiaRilevata.load( nuovoCapo.getId(), db );
			//nuovoCapo.storico x provvedimenti casl
			context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiornato() );
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
	
	
	public String executeCommandUpdateEsitoCampioni( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			GenericBean vecchioCapo = GenericBean.load( context.getParameter( "id" ), db, configTipo );
			Integer idVecchioCapo = vecchioCapo.getId();
			
			ArrayList<Campione>				vecchiCampioni			= Campione.load( idVecchioCapo, configTipo,db );
			
			vecchioCapo.storico_vpm_campioni			= vecchiCampioni;
			
			//Verifica univocita' dell'identificato dell'oggetto in questione: matricola per i capi, partita per le partite, ...
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
			
			Timestamp modified = null;
			modified = new Timestamp( System.currentTimeMillis() ) ;
			
			ArrayList<Campione>	campioni		= loadCampioni( context, configTipo,b );

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
						camp.setModified	( modified );
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
					camp.setEntered		( modified );
					camp.setModified	( modified );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			
			vecchioCapo.setStato_macellazione(((Partita)vecchioCapo).getStatoMacellazione(db, configTipo));
			vecchioCapo.update(db, campioni, configTipo);

			settaStorico(configTipo, db);

			context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiornato() );
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
	
	public String executeCommandUpdateSedutaEsitoCampioni( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			PartitaSeduta p_seduta = PartitaSeduta.load( context.getParameter( "id" ), db, configTipo );
			Integer idVecchioCapo = p_seduta.getId();
			
			
			ArrayList<Campione>				vecchiCampioni			= PartitaSeduta.loadCampioni(idVecchioCapo, db);
			
			p_seduta.storico_vpm_campioni			= vecchiCampioni;
			
			Timestamp modified = null;
			modified = new Timestamp( System.currentTimeMillis() ) ;
			
			ArrayList<Campione>	campioni		= loadCampioni( context, configTipo,b );

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
						camp.setId_seduta(idVecchioCapo);
						camp.setEntered_by	( campv.getEntered_by() );
						camp.setEntered		( campv.getEntered() );
						camp.setModified	( modified );
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
					camp.setId_seduta(idVecchioCapo);
					camp.setEntered		( modified );
					camp.setModified	( modified );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			
			GenericBean partita = Partita.load(p_seduta.getId_partita()+"", db, configTipo);
			p_seduta.setStato_macellazione(MacelliUtil.getStatoMacellazioneSeduta(p_seduta, (Partita)partita, db, campioni ));
			p_seduta.update(db, campioni, configTipo);

			settaStorico(configTipo, db);

			context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiornato() );
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
	
	public String executeCommandUpdateSeduta( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			p_seduta = PartitaSeduta.load( context.getParameter( "id" ), db, configTipo );
			Integer idSeduta = p_seduta.getId();

			ArrayList<PatologiaRilevata>	vecchiePatologie		= PartitaSeduta.loadPatologieRilevate(idSeduta, db);
			ArrayList<Esito>	vecchiEsiti		                    = PartitaSeduta.loadEsito( idSeduta, db );
			ArrayList<Organi>				vecchiOrgani			= PartitaSeduta.loadOrgani(idSeduta, db);
			ArrayList<NonConformita>				ncsVam			= PartitaSeduta.loadNonConformita(idSeduta, db);
			
			p_seduta.storico_lcso_organi				= vecchiOrgani;
			p_seduta.storico_vpm_patologie_rilevate	= vecchiePatologie;
			
			PartitaSeduta p = (PartitaSeduta)loadSeduta(context);

			String ret2 = null;
			
			Integer enteredBy = null;
			Integer modifiedBy = null;
			Timestamp entered = null;
			Timestamp modified = null;
			Integer idCapo = null;
			Integer idMacello = null;
			Integer sedutaMacellazione = null;
			Timestamp dataMacellazione = null;
			idCapo = p.getId();
			entered =  p.getEntered() ;
			modified = new Timestamp( System.currentTimeMillis() ) ;
			enteredBy = p.getEntered_by();
			idMacello = p.getId_macello();
			sedutaMacellazione = p.getCd_seduta_macellazione();
			dataMacellazione = p.getVpm_data();
			
			p.setNumero(p_seduta.getNumero());
			
			ArrayList<Campione> 			campioni 			= loadCampioni( context, configTipo,b );
			Tampone tampone = loadTampone( context );
			
			tampone.setEntered		(entered);
			tampone.setId_macello(idMacello);
			tampone.setSessione_macellazione(sedutaMacellazione);

			tampone.setData_macellazione(dataMacellazione);
			tampone.setModified	( entered );
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
			tampone.cancella_tampone_seduta(p, db, configTipo);
			tampone.associa_tampone_seduta( p, db, configTipo);
			
			}
			else
			{
				tampone.cancella_tampone_seduta(p, db, configTipo);
			}


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
						vp.setModified		( modified );
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
					pr.setId_seduta(p_seduta.getId());
					pr.setId_patologia	( Integer.parseInt( pat ) );
					pr.setEntered		( entered );
					pr.setModified		( entered );
					pr.setEntered_by	( getUserId(context) );
					pr.setModified_by	( getUserId(context) );
					pr.store( db );
				}
			}
			
			ArrayList<NonConformita> ncs = loadNCSedute( context);
			//eliminare le non conformita' non piu selezionate ed aggiornare le altre
			for( NonConformita ncv: ncsVam )
			{
				boolean cancellare = true;
				if(p.getVam_esito().toLowerCase().contains("non favorevole".toLowerCase()))
				{
					for( NonConformita nc: ncs )
					{
						if( ncv.getId() == nc.getId() )
						{
							cancellare = false;
	
							//update delle vecchie non conformita'
							nc.setEntered_by	( ncv.getEntered_by() );
							nc.setEntered		( ncv.getEntered() );
							nc.setModified		( modified );
							nc.setModified_by	( getUserId(context) );
							nc.update			( db );
						}
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
				if( nc.getId() <= 0 && p.getVam_esito().toLowerCase().contains("non favorevole".toLowerCase()))
				{
					nc.setId_seduta(p_seduta.getId());
					nc.setModified		( entered );
					nc.setModified_by	( getUserId(context) );
					nc.setEntered		( entered );
					nc.setEntered_by	( getUserId(context) );
					nc.store			( db );
				}
			}
			
			
			if(configTipo.getEsitiMultipli())
			{
				String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
				esiti = (esiti == null) ? (new String[0]) : (esiti);
				//eliminare le patologie non piu selezionate ed aggiorno le altre
				for( Esito e: vecchiEsiti)
				{
					boolean cancellare = true;
					for( String es: esiti )
					{
						if( e.getId_esito() == Integer.parseInt( es ) )
						{
							cancellare = false;

							//update delle vecchie patologie
							e.setModified		( modified );
							e.setModified_by	( getUserId(context) );
							e.setNum_capi_ovini   ( intero("num_capi_ovini_esito_"+es, context));
							e.setNum_capi_caprini ( intero("num_capi_caprini_esito_"+es, context));
							e.update( db );
						}

						if( cancellare ) //cancellazione delle patologie rimosse
						{
							Esito.delete( e.getId(), getUserId(context), db );
						}
					}
				}
				//inserisco le nuove patologie
				for( String es: esiti )
				{
					boolean inserire = true;
					for( Esito e: vecchiEsiti)
					{
						if( e.getId_esito() == Integer.parseInt( es ) )
						{
							inserire = false;
						}
					}

					if( !"-1".equals( es ) && inserire )
					{
						Esito e = new Esito();
						e.setId_seduta		( p_seduta.getId());
						e.setId_esito   	( Integer.parseInt( es ) );
						e.setEntered		( entered );
						e.setModified		( entered );
						e.setEntered_by	    ( getUserId(context) );
						e.setModified_by	( getUserId(context) );
						e.setNum_capi_ovini   ( intero("num_capi_ovini_esito_"+es, context));
						e.setNum_capi_caprini ( intero("num_capi_caprini_esito_"+es, context));
						e.store( db );
					}
				}
			}

			ArrayList<Organi>	organi			= loadOrgani(context);
			//eliminare gli organi non + selezionati ed aggiornare gli altri
			for( Organi orgv: vecchiOrgani )
			{
				Organi.delete( orgv.getId(), getUserId(context), db );
			}
			//salvataggio dei nuovi organi
			for( Organi org: organi )
			{
				org.setId_seduta(p_seduta.getId());
				org.setEntered		( entered );
				org.setModified		( entered );
				org.setEntered_by	( getUserId(context) );
				org.setModified_by	( getUserId(context) );
				org.store			( db );
			}

			settaStorico(configTipo, db);
			
			ArrayList<Campione>				vecchiCampioni			= PartitaSeduta.loadCampioni(idSeduta, db);

			for( Campione campv: vecchiCampioni )
			{
				boolean cancellare = true;
				for( Campione camp: campioni )
				{
					if( campv.getId() == camp.getId() )
					{
						cancellare = false;

						//update dei vecchi campioni
						camp.setId_seduta(idCapo);
						camp.setEntered_by	( campv.getEntered_by() );
						camp.setEntered		( campv.getEntered() );
						camp.setModified	( modified );
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
					camp.setId_seduta(idCapo);
					camp.setEntered		( entered );
					camp.setModified	( entered );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			
			p.update(db, campioni, configTipo);

			context.getRequest().setAttribute( "messaggio", "Seduta di macellazione modificata" );
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
	
	public String executeCommandSaveToLiberoConsumo( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{ 
			return ("PermissionError");
		}

		Connection db = null;
		try  
		{
			db		= this.getConnection( context );

			b_old = (GenericBean) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			b	= loadBean(context, configTipo);
			
			//Controllo Matricola
			//c.getCd_matricola();
			Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
			String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
			String codiceAzienda = (String)ReflectionUtil.invocaMetodo(o, "getCd_codice_azienda_provenienza", null);
			Integer numOvini = null;
			Integer numCaprini = null;
			try
			{
				numOvini = (Integer)ReflectionUtil.invocaMetodo(o, "getCd_num_capi_ovini", null);
				numCaprini = (Integer)ReflectionUtil.invocaMetodo(o, "getCd_num_capi_caprini", null);
			}
			catch(NoSuchMethodException ex)
			{
				numOvini = 0;
				numCaprini = 0;
			}
			if(identificativo!=null && !identificativo.equals(""))
			{
				AjaxCalls ac = new AjaxCalls();
				BeanAjax  ca = this.isCapoEsistente(identificativo, codiceAzienda, numOvini, numCaprini, configTipo);
				if( ca.isEsistente() ){
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoNonInserito() );
					return executeCommandNuovoCapo( context );
				}
			}
			
			String ret2 = null;
			//Controllo progressivo di Macellazione
			ret2 = null;
			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, false, null, identificativo))!=null)
				return ret2;
			
			//Controllo stampa del modello 10
			ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,true))!=null)
				return ret2;


				b_old.setVam_data( b.getVam_data() );
				b_old.setMac_progressivo( b.getMac_progressivo() );
				b_old.setProgressivo_macellazione( b.getProgressivo_macellazione() );
				b_old.setMac_tipo( b.getMac_tipo() );
				b_old.setVpm_data( b.getVpm_data() );
				b_old.setVpm_esito( b.getVpm_esito() );
				b_old.setVpm_veterinario( b.getVpm_veterinario() );
				b_old.setVpm_veterinario_2( b.getVpm_veterinario_2() );
				b_old.setVpm_veterinario_3( b.getVpm_veterinario_3() );
				b_old.setVpm_note( b.getVpm_note() );
				b_old.setDestinatario_1_id(b.getDestinatario_1_id());
				b_old.setDestinatario_1_in_regione(b.isDestinatario_1_in_regione());
				b_old.setDestinatario_1_nome(b.getDestinatario_1_nome());
				b_old.setDestinatario_2_id(b.getDestinatario_2_id());
				b_old.setDestinatario_2_in_regione(b.isDestinatario_2_in_regione());
				b_old.setDestinatario_2_nome(b.getDestinatario_2_nome());
				b_old.setDestinatario_3_id(b.getDestinatario_3_id());
				b_old.setDestinatario_3_in_regione(b.isDestinatario_3_in_regione());
				b_old.setDestinatario_3_nome(b.getDestinatario_3_nome());
				b_old.setDestinatario_4_id(b.getDestinatario_4_id());
				b_old.setDestinatario_4_in_regione(b.isDestinatario_4_in_regione());
				b_old.setDestinatario_4_nome(b.getDestinatario_4_nome());
				b_old.setVam_provvedimenti(b.getVam_provvedimenti());
				
				
				if(configTipo.hasNumCapiVam())
				{
					Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
					Object oToSet = ReflectionUtil.invocaMetodo(o2, "getVam_num_capi_ovini", null, null);
					ReflectionUtil.invocaMetodo(b_old, "setVam_num_capi_ovini", new Class[]{Integer.class}, new Object[]{oToSet});
					o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b);
					oToSet = ReflectionUtil.invocaMetodo(o2, "getVam_num_capi_caprini", null, null);
					ReflectionUtil.invocaMetodo(b_old, "setVam_num_capi_caprini", new Class[]{Integer.class}, new Object[]{oToSet});
				}
				
				if(configTipo.hasMoreDestinatari())
				{
					Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
					int i=5;
					while(i<=configTipo.getNumDestinatari())
					{
						Integer id = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_id", null, null);
						ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_id", new Class[]{Integer.class}, new Object[]{id});
						Boolean in_regione = (Boolean)ReflectionUtil.invocaMetodo(b, "isDestinatario_"+i+"_in_regione", null, null);
						ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_in_regione", new Class[]{Boolean.class}, new Object[]{in_regione});
						String nome = (String)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_nome", null, null);
						ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_nome", new Class[]{String.class}, new Object[]{nome});
						i++;
					}
				}
				if(configTipo.hasDestinatariNumCapi())
				{
					Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
					int i=1;
					while(i<=configTipo.getNumDestinatari())
					{
						Integer num_capi_ovini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_ovini", null, null);
						ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_ovini", new Class[]{Integer.class}, new Object[]{num_capi_ovini});
						Integer num_capi_caprini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_caprini", null, null);
						ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_caprini", new Class[]{Integer.class}, new Object[]{num_capi_caprini});
						i++;
					}
				}
				b_old.setEntered_by( this.getUserId(context) );
	
				b_old = b_old.store( db, null,configTipo );

			//Inizio Log del capo in m_capi_log
//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c_old.getCd_id_speditore());
//			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
//
//			if(speditore.getAddressList().size() > 0){
//				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
//			}

			
		//	ReflectionUtil.invocaMetodo(configTipo.getPackageAction()+configTipo.getNomeAction(), "loggaPerLiberoConsumo", new Object[]{configTipo, context, db});

			//Fine Log del capo in m_capi_log

				
			if(configTipo.getEsitiMultipli())
			{
				String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
				esiti = (esiti == null) ? (new String[0]) : (esiti);
				for( String index: esiti )
				{
					if( !"-1".equals( index ) )
					{
						Esito e = new Esito();
						e.setId_partita		( b_old.getId() );
						e.setId_esito   	( Integer.parseInt( index ) );
						e.setEntered		( b_old.getEntered());
						e.setModified		( b_old.getEntered() );
						e.setEntered_by	(     getUserId(context) );
						e.setModified_by	( getUserId(context) );
						e.store( db );
					}
				}
			}

			ArrayList<Campione> cmps = loadCampioni(context, configTipo, b_old);
			for( Campione camp: cmps )
			{
				camp.setEntered		( b_old.getEntered() );
				camp.setModified	( b_old.getEntered() );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db, configTipo );
			}
			
			b_old.setStato_macellazione(MacelliUtil.getStatoMacellazione(b_old, db, cmps));
			b_old.update(db, cmps, configTipo);
			
			
			Tampone tampone = loadTampone( context );
			tampone.setEntered		( b_old.getEntered() );
			tampone.setId_macello(b_old.getId_macello());
			tampone.setSessione_macellazione(b_old.getCd_seduta_macellazione());

			tampone.setData_macellazione(b_old.getVpm_data());
			tampone.setModified	( b_old.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db,context);
				
			}
			tampone.associa_tampone_capo(b_old, db, configTipo);
			}
				

			b_old.storico_vam_non_conformita			= NonConformita.load	( b_old.getId(), configTipo,db );
			b_old.storico_vpm_campioni					= Campione.load			( b_old.getId(), configTipo,db );
			b_old.storico_lcso_organi					= Organi.loadByOrgani	( b_old.getId(), configTipo,db );
			b_old.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( b_old.getId(), configTipo,db );
			
			context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiunto() );
			
			
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
	
	public String executeCommandSaveToLiberoConsumoSeduta( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{ 
			return ("PermissionError");
		}

		Connection db = null;
		try  
		{
			db		= this.getConnection( context );

			b	= loadBean(context,configTipo);
			
			p_seduta = (PartitaSeduta)loadSeduta(context);
			p_seduta.popolaCampiByPartita(db, configTipo);
			
			//Controllo stampa del modello 10
			String ret2 = null;
			if((ret2 = controlloStampaMod10Seduta(configTipo, context,true))!=null)
				return ret2;

			p_seduta.setEntered_by( this.getUserId(context) );
			
			//Generazione numerazione univoca automatica
			int codiceUnivoco = PartitaSeduta.nextId(db, configTipo);
			Date d = new Date();
			String anno = 1900 + d.getYear() + "" ;
			int asl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			String aslString ="";
			
			ArrayList<Campione> cmps = loadCampioniSeduta(context, configTipo, p_seduta);
			
			//Gestisco casi in cui l'utente non ha ASL
			if (asl<0) 
				aslString = "0";
			else
				aslString = String.valueOf(asl);
			
			//Padding a tre cifre
			while (aslString.length()<3){
				aslString = "0"+aslString;
			}
			String numero = aslString + codiceUnivoco + anno;
			p_seduta.setNumero(numero);
			p_seduta = p_seduta.store( db, cmps,configTipo );

			String[] esiti = context.getRequest().getParameterValues( "vpm_esito" );
			esiti = (esiti == null) ? (new String[0]) : (esiti);
			for( String index: esiti )
			{
				if( !"-1".equals( index ) )
				{
					Esito e = new Esito();
					e.setId_seduta      ( p_seduta.getId() );
					e.setId_esito   	( Integer.parseInt( index ) );
					e.setEntered		( p_seduta.getEntered());
					e.setModified		( p_seduta.getEntered() );
					e.setEntered_by	    ( getUserId(context) );
					e.setModified_by	( getUserId(context) );
					e.store( db );
				}
			}
			
			cmps = loadCampioniSeduta(context, configTipo, p_seduta);
			for( Campione camp: cmps )
			{
				camp.setEntered		( p_seduta.getEntered() );
				camp.setModified	( p_seduta.getEntered() );
				camp.setEntered_by	( getUserId(context) );
				camp.setModified_by	( getUserId(context) );
				camp.store			( db, configTipo );
			}
			
			Tampone tampone = loadTampone( context );
			tampone.setEntered		( p_seduta.getEntered() );
			tampone.setId_macello(p_seduta.getId_macello());
			tampone.setSessione_macellazione(p_seduta.getCd_seduta_macellazione());

			tampone.setData_macellazione(p_seduta.getVpm_data());
			tampone.setModified	( p_seduta.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db,context );
				
			}
			tampone.associa_tampone_seduta(p_seduta, db, configTipo);
			}
				

			p_seduta.storico_vam_non_conformita			= NonConformita.load	( p_seduta.getId(), configTipo,db );
			p_seduta.storico_vpm_campioni					= Campione.load			( p_seduta.getId(), configTipo,db );
			p_seduta.storico_lcso_organi					= Organi.loadByOrgani	( p_seduta.getId(), configTipo,db );
			p_seduta.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( p_seduta.getId(), configTipo,db );
			
			context.getRequest().setAttribute( "messaggio", "Seduta di macellazione numero " + p_seduta.getNumero() + " aggiunta" );
			
			
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
			//ret = executeCommandNuovoCapo( context );
			ret = executeCommandToRiprendiPartita(context);
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

		getConfigTipo(context);
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );

			//vecchioBean = Capo.load( context.getParameter( "id" ), db, configTipo );
			//b_old = (Capo) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			
			b_old = (GenericBean) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			vecchioBean	= loadBean(context, configTipo);
			
			b	= loadBean( context, configTipo );

			String ret2 = null;
			//Controllo progressivo di Macellazione
			ret2 = null;
			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, false, null, null))!=null)
				return ret2;
			
			//Controllo stampa del modello 10
			ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,true))!=null)
				return ret2;

			b_old.setId(vecchioBean.getId());
			b_old.setVam_data( b.getVam_data() );
			b_old.setMac_progressivo( b.getMac_progressivo() );
			b_old.setProgressivo_macellazione( b.getProgressivo_macellazione() );
			b_old.setMac_tipo( b.getMac_tipo() );
			b_old.setVpm_data( b.getVpm_data() );
			b_old.setVpm_esito( b.getVpm_esito() );
			b_old.setVpm_veterinario( b.getVpm_veterinario() );
			b_old.setVpm_veterinario_2( b.getVpm_veterinario_2() );
			b_old.setVpm_veterinario_3( b.getVpm_veterinario_3() );
			b_old.setVpm_note( b.getVpm_note() );
			b_old.setDestinatario_1_id(b.getDestinatario_1_id());
			b_old.setDestinatario_1_in_regione(b.isDestinatario_1_in_regione());
			b_old.setDestinatario_1_nome(b.getDestinatario_1_nome());
			b_old.setDestinatario_2_id(b.getDestinatario_2_id());
			b_old.setDestinatario_2_in_regione(b.isDestinatario_2_in_regione());
			b_old.setDestinatario_2_nome(b.getDestinatario_2_nome());
			
			
			b_old.setDestinatario_3_id(b.getDestinatario_3_id());
			b_old.setDestinatario_3_in_regione(b.isDestinatario_3_in_regione());
			b_old.setDestinatario_3_nome(b.getDestinatario_3_nome());
			b_old.setDestinatario_4_id(b.getDestinatario_4_id());
			b_old.setDestinatario_4_in_regione(b.isDestinatario_4_in_regione());
			b_old.setDestinatario_4_nome(b.getDestinatario_4_nome());
			
			if(configTipo.hasMoreDestinatari())
			{
				Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
				int i=5;
				while(i<=configTipo.getNumDestinatari())
				{
					Integer id = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_id", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_id", new Class[]{Integer.class}, new Object[]{id});
					Boolean in_regione = (Boolean)ReflectionUtil.invocaMetodo(b, "isDestinatario_"+i+"_in_regione", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_in_regione", new Class[]{Boolean.class}, new Object[]{in_regione});
					String nome = (String)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_nome", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_nome", new Class[]{String.class}, new Object[]{nome});
					i++;
				}
			}
			
			if(configTipo.hasDestinatariNumCapi())
			{
				Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
				int i=1;
				while(i<=configTipo.getNumDestinatari())
				{
					Integer num_capi_ovini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_ovini", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_ovini", new Class[]{Integer.class}, new Object[]{num_capi_ovini});
					Integer num_capi_caprini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_caprini", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_caprini", new Class[]{Integer.class}, new Object[]{num_capi_caprini});
					i++;
				}
			}

			b_old.setEntered	( vecchioBean.getEntered() );
			b_old.setModified	( new Timestamp( System.currentTimeMillis() ) );
			b_old.setEntered_by( this.getUserId(context) );

			//c_old = c_old.store( db, null );

			b_old.update( db, null, configTipo );

			//Inizio Log del capo in m_capi_log
//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c_old.getCd_id_speditore());
//			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
//
//			if(speditore.getAddressList().size() > 0){
//				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
//			}
			
			
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

			ArrayList<Campione> campioni = loadCampioni(context, configTipo, b);
			
			ArrayList<Campione>	vecchiCampioni = null;
			vecchiCampioni = Campione.load( vecchioBean.getId(), configTipo,db );
			
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
						camp.setModified	( b.getModified() );
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
					camp.setEntered		( b.getEntered() );
					camp.setModified	( b.getEntered() );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			

			Tampone tampone = loadTampone( context);
			
			tampone.setEntered		( b.getEntered() );
			tampone.setId_macello(b.getId_macello());
			tampone.setSessione_macellazione(b.getCd_seduta_macellazione());

			tampone.setData_macellazione(b.getVpm_data());
			tampone.setModified	( b.getEntered() );
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
			tampone.cancella_tampone_capo(b, db, configTipo);
			tampone.associa_tampone_capo( b, db, configTipo);
			
			}
			else
			{
				tampone.cancella_tampone_capo(b, db, configTipo);
			}

			
				b_old.storico_vam_non_conformita			= NonConformita.load	( b_old.getId(), configTipo,db );
				b_old.storico_vpm_campioni					= Campione.load			( b_old.getId(), configTipo,db );
				b_old.storico_lcso_organi					= Organi.loadByOrgani	( b_old.getId(), configTipo,db );
				b_old.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( b_old.getId(), configTipo,db );
				context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiornato() );
			
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
	
	public String executeCommandChiusuraPartita( ActionContext context )
	{
		getConfigTipo(context);
		String ret = null;
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-edit"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			
			GenericBean.load(context.getParameter(configTipo.getNomeVariabileIdJsp()), db, configTipo);
			
			Iterator<PartitaSeduta> partite = Partita.loadSedute( db, b.getId(), configTipo ).iterator();

			Integer modifiedBy = null;
			Timestamp modified = null;
			Integer idCapo = null;
			Integer idMacello = null;
			idCapo = b.getId();
			modified = new Timestamp( System.currentTimeMillis() ) ;
			idMacello = b.getId_macello();
			

			Partita nuovaPartita = (Partita)Partita.load(context.getParameter(configTipo.getNomeVariabileIdJsp()), db, configTipo);
			nuovaPartita.chiudi(db, context.getParameter("cd_motivazione_chiusura"));
			
			while(partite.hasNext())
			{
				PartitaSeduta partTemp = partite.next();
				partTemp.chiudi(db, context.getParameter("cd_motivazione_chiusura"));
			}
			
			context.getRequest().setAttribute( "messaggio", "Partita chiusa" );
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

		ret = executeCommandList( context );

		return ret;
	}

	public String executeCommandPrintBRCRilevazioneMacelli(ActionContext context) 
	{
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("orgId");
			int orgid = new Integer(id);
			Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("orgid", orgid);
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


	public String executeCommandPrintTBCRilevazioneMacelli(ActionContext context) 
	{
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("orgId");
			int orgid = new Integer(id);
			Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("orgid", orgid);
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
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		Connection db = null;

		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
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

	/*public String executeCommandPrintModelloIdatidosi(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			String data = context.getParameter( "data" );

		  /*db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("orgId");
		  int orgid = new Integer(id);
		  Organization org = new Organization(db, orgid);
		  String data = context.getParameter( "data" );
		  Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToModelloIdatidosiOK";
			}

		  //Verifico la data post mortem prima di procedere
		  String select = "SELECT * FROM m_esercenti_macellazioni WHERE id_macello = ? AND data_macellazione = ?";
		  PreparedStatement stat = db.prepareStatement( select );
		  stat.setInt( 1, org.getOrgId() );
		  stat.setTimestamp( 2, d );
		  ResultSet		res		= stat.executeQuery();
		  RowSetDynaClass	rsdc	= new RowSetDynaClass( res );

		  if( rsdc.getRows().size() == 0 )
		  {
				context.getRequest().setAttribute( "messaggio", "Nessun capo macellato il " + data );
				return "ToModelloIdatidosiOK";
		   }



		  HashMap map = new HashMap();
		  map.put("orgid", org.getOrgId());
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
		    	fileDownload.setDisplayName("Modello_idatidosi_" + org.getOrgId() + ".pdf");
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
	  }*/

	/*public String executeCommandPrintModelloIdatidosi(ActionContext context) throws com.itextpdf.text.DocumentException, IOException {

		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		String reportDir = getWebInfPath(context, "template_report");
		String reportDirImg = getWebInfPath(context, "reports");
		Connection db = null;

		try {

			db = this.getConnection(context);
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );
			String data = context.getParameter( "data" );
			Timestamp d = new Timestamp( sdf.parse( data ).getTime() );

			String qry = ApplicationProperties.getProperty("GET_IDATIDOSI");
			PreparedStatement stat = db.prepareStatement(qry);
			stat.setInt( 1, macello.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet res = stat.executeQuery();

			ArrayList<Capo> listaCapi = new ArrayList<Capo>();
			Capo capo = null;
			while(res.next()){
				capo = Capo.load(res.getString("id"), db);
				listaCapi.add(capo);
			}

			if( listaCapi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con idatidosi macellato il giorno " + data );
					return "ToModelloIdatidosiOK";
			   }

			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			//RM
			int value_logo = macello.getSiteId();
			Image instanceImg = null;
			if (value_logo == -1 || value_logo == 16) {
				instanceImg = Image.getInstance(reportDir
						+ "/images/regionecampania.jpg");
			} else {
				LookupList asl = new LookupList(db, "lookup_site_id");
				instanceImg = Image.getInstance(reportDirImg + "/images/"
						+ asl.getSelectedValue(value_logo) + ".jpg");
			}

			instanceImg.setAbsolutePosition(6.0F,700.0F);
			instanceImg.scalePercent(30.0F);
			document.add(instanceImg);

			this.createPDFIdatidosi(db,document,writer,macello,listaCapi,data);
			//this.createPDFAnimaliGravidi(db,document,writer,macello,listaCapi);

			document.close();
	        FileDownload fileDownload = new FileDownload();
		    fileDownload.setDisplayName("Modello_idatidosi.pdf");
			fileDownload.sendFile(context,out.toByteArray(), "application/pdf");

		}
		catch ( ParseException pe ){
			pe.printStackTrace();
			context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			return "ToModelloIdatidosiOK";
		}
		catch (Exception errorMessage){
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
		}

		return "-none-";

	}




	/*public String executeCommandPrintModelloMarchi(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		String reportDir = getWebInfPath(context, "template_report");
		String reportDirImg = getWebInfPath(context, "reports");
		Connection db = null;

		try {

			db = this.getConnection(context);
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );
			String data = context.getParameter( "data" );
			Timestamp d = new Timestamp( sdf.parse( data ).getTime() );

			//MODIFICARE LA QUERY
			String select = ApplicationProperties.getProperty("GET_MODELLO_MARCHI");
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, macello.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet res = stat.executeQuery();

			ArrayList<Capo> listaCapi = new ArrayList<Capo>();
			HashMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC = new HashMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>>();
			HashMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti = new HashMap<Integer, ArrayList<ProvvedimentiCASL>>();
			Capo capo = null;
			while(res.next()){
				capo = Capo.load(res.getString("id"), db);
				listaCapi.add(capo);
				hashCapiNC.put(capo.getId(), Casl_Non_Conformita_Rilevata.load(capo.getId(), db));
				hashCapiProvvedimenti.put(capo.getId(), ProvvedimentiCASL.load(capo.getId(), db));
			}

			if( listaCapi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con non conformita' arrivato al macello il giorno " + data );
					return "ToModelloIdatidosiOK";
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			//RM
			int value_logo = macello.getSiteId(); 
			Image instanceImg = null;
			if (value_logo == -1 || value_logo == 16) {
				instanceImg = Image.getInstance(reportDir
						+ "/images/regionecampania.jpg");
			} else {
				LookupList asl = new LookupList(db, "lookup_site_id");
				instanceImg = Image.getInstance(reportDirImg + "/images/"
						+ asl.getSelectedValue(value_logo) + ".jpg");
			}

			instanceImg.setAbsolutePosition(6.0F,700.0F);
			instanceImg.scalePercent(30.0F);
			document.add(instanceImg);

			this.createPDFArrivoAlMacello(db, document, writer, macello, listaCapi);

			document.close();
	        FileDownload fileDownload = new FileDownload();
		    fileDownload.setDisplayName("Modello_Arrivo_Al_Macello.pdf");
			fileDownload.sendFile(context,out.toByteArray(), "application/pdf");

		}
		catch ( ParseException pe ){
			pe.printStackTrace();
			context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			return "ToModelloIdatidosiOK";
		}
		catch (Exception errorMessage){
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
		}

		return "-none-";

	}*/

	/*public String executeCommandPrintModelloMarchi(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
		  db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("orgId");
		  int orgid = new Integer(id);
		  Organization org = new Organization(db, orgid);
		  HashMap map = new HashMap();
		  map.put("orgid", orgid);
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
		    	fileDownload.setDisplayName("Modello_marchi_" + id + ".pdf");
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
	  }*/

	/*public String executeCommandPrintMacellazioneAnimaliInfetti(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
		  db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("orgId");
		  int orgid = new Integer(id);
		  Organization org = new Organization(db, orgid);
		  HashMap map = new HashMap();
		  map.put("orgid", orgid);
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
		    	fileDownload.setDisplayName("Macellazione_animali_infetti_" + id + ".pdf");
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
	  }*/

	/*public String executeCommandPrintMacellazioneAnimaliInfetti(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}

		String reportDir = getWebInfPath(context, "template_report");
		String reportDirImg = getWebInfPath(context, "reports");
		Connection db = null;

		try {

			db = this.getConnection(context);
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );
			String data = context.getParameter( "data" );
			Timestamp d = new Timestamp( sdf.parse( data ).getTime() );

			String qry = ApplicationProperties.getProperty("GET_ANIMALI_INFETTI"); 
			PreparedStatement stat = db.prepareStatement( qry );
			stat.setInt( 1, macello.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet res = stat.executeQuery();

			ArrayList<Capo> listaCapiBrucellosi = new ArrayList<Capo>();
			ArrayList<Capo> listaCapiTubercolosi = new ArrayList<Capo>();
			Capo capo = null;
			while(res.next()){
				capo = Capo.load(res.getString("id"), db);
				if(capo.getCd_macellazione_differita() == 1){
					listaCapiBrucellosi.add(capo);
				}
				else if(capo.getCd_macellazione_differita() == 2){
					listaCapiTubercolosi.add(capo);
				}
			}

			if( listaCapiBrucellosi.size() <= 0 && listaCapiTubercolosi.size() <= 0 ){
				context.getRequest().setAttribute( "messaggio", "Nessun capo con idatidosi macellato il giorno " + data );
				return "ToModelloIdatidosiOK";
		   }

			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			//RM
			int value_logo = macello.getSiteId(); //RECUPERARE ID_MACELLO
			Image instanceImg = null;
			if (value_logo == -1 || value_logo == 16) {
				instanceImg = Image.getInstance(reportDir
						+ "/images/regionecampania.jpg");
			} else {
				LookupList asl = new LookupList(db, "lookup_site_id");
				instanceImg = Image.getInstance(reportDirImg + "/images/"
						+ asl.getSelectedValue(value_logo) + ".jpg");
			}

			instanceImg.setAbsolutePosition(6.0F,700.0F);
			instanceImg.scalePercent(30.0F);
			document.add(instanceImg);

			this.createPDFAnimaliInfettiDa(db, document, writer, macello, listaCapiBrucellosi, listaCapiTubercolosi, data);

			document.close();
	        FileDownload fileDownload = new FileDownload();
		    fileDownload.setDisplayName("Modello_Animali_Infetti.pdf");
			fileDownload.sendFile(context,out.toByteArray(), "application/pdf");

		}
		catch ( ParseException pe ){
			pe.printStackTrace();
			context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			return "ToModelloIdatidosiOK";
		}
		catch (Exception errorMessage){
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
		}

		return "-none-";

	}*/


	public String executeCommandPrintDisinfezioneMezziTrasporto(ActionContext context) 
	{
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("orgId");
			int orgid = new Integer(id);
			Organization org = new Organization(db, orgid);
			HashMap map = new HashMap();
			map.put("orgid", orgid);
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


	public String executeCommandPrintInvioCampioniTBC(ActionContext context) 
	{
		getConfigTipo(context);
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


	public String executeCommandPrintModelloLBE(ActionContext context) 
	{
		getConfigTipo(context);
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


	public String executeCommandPrintModello1033TBC(ActionContext context) 
	{
		getConfigTipo(context);
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



	public String executeCommaToLiberoConsumo( ActionContext context )
	{
		String ret = null;

		getConfigTipo(context);
		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		try
		{
			db		= this.getConnection( context );

			vecchioBean = Capo.load( context.getParameter( "id" ), db, configTipo );
			b_old = (Capo) context.getSession().getAttribute( configTipo.getNomeBeanJsp() );
			b	= loadBean( context, configTipo );

			String ret2 = null;
			//Controllo progressivo di Macellazione
			ret2 = null;
			if((ret2 = controlloProgressivoMacellazione(configTipo, context, db, false, null, null))!=null)
				return ret2;
			
			//Controllo stampa del modello 10
			ret2 = null;
			if((ret2 = controlloStampaMod10(configTipo, context,true))!=null)
				return ret2;

			b_old.setId(vecchioBean.getId());
			b_old.setVam_data( b.getVam_data() );
			b_old.setMac_progressivo( b.getMac_progressivo() );
			b_old.setProgressivo_macellazione( b.getProgressivo_macellazione() );
			b_old.setMac_tipo( b.getMac_tipo() );
			b_old.setVpm_data( b.getVpm_data() );
			b_old.setVpm_esito( b.getVpm_esito() );
			b_old.setVpm_veterinario( b.getVpm_veterinario() );
			b_old.setVpm_veterinario_2( b.getVpm_veterinario_2() );
			b_old.setVpm_veterinario_3( b.getVpm_veterinario_3() );
			b_old.setVpm_note( b.getVpm_note() );
			b_old.setDestinatario_1_id(b.getDestinatario_1_id());
			b_old.setDestinatario_1_in_regione(b.isDestinatario_1_in_regione());
			b_old.setDestinatario_1_nome(b.getDestinatario_1_nome());
			b_old.setDestinatario_2_id(b.getDestinatario_2_id());
			b_old.setDestinatario_2_in_regione(b.isDestinatario_2_in_regione());
			b_old.setDestinatario_2_nome(b.getDestinatario_2_nome());
			
			
			b_old.setDestinatario_3_id(b.getDestinatario_3_id());
			b_old.setDestinatario_3_in_regione(b.isDestinatario_3_in_regione());
			b_old.setDestinatario_3_nome(b.getDestinatario_3_nome());
			b_old.setDestinatario_4_id(b.getDestinatario_4_id());
			b_old.setDestinatario_4_in_regione(b.isDestinatario_4_in_regione());
			b_old.setDestinatario_4_nome(b.getDestinatario_4_nome());
			
			if(configTipo.hasMoreDestinatari())
			{
				Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
				int i=5;
				while(i<=configTipo.getNumDestinatari())
				{
					Integer id = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_id", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_id", new Class[]{Integer.class}, new Object[]{id});
					Boolean in_regione = (Boolean)ReflectionUtil.invocaMetodo(b, "isDestinatario_"+i+"_in_regione", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_in_regione", new Class[]{Boolean.class}, new Object[]{in_regione});
					String nome = (String)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_nome", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_nome", new Class[]{String.class}, new Object[]{nome});
					i++;
				}
			}
			
			if(configTipo.hasDestinatariNumCapi())
			{
				Object o2 = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(b_old);
				int i=1;
				while(i<=configTipo.getNumDestinatari())
				{
					Integer num_capi_ovini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_ovini", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_ovini", new Class[]{Integer.class}, new Object[]{num_capi_ovini});
					Integer num_capi_caprini = (Integer)ReflectionUtil.invocaMetodo(b, "getDestinatario_"+i+"_num_capi_caprini", null, null);
					ReflectionUtil.invocaMetodo(o2, "setDestinatario_" + i + "_num_capi_caprini", new Class[]{Integer.class}, new Object[]{num_capi_caprini});
					i++;
				}
			}
			
			b_old.setEntered	( vecchioBean.getEntered() );
			b_old.setModified	( new Timestamp( System.currentTimeMillis() ) );
			b_old.setEntered_by( this.getUserId(context) );

			//c_old = c_old.store( db, null );

			b_old.update( db, null, configTipo );

			//Inizio Log del capo in m_capi_log
//			org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization(db,c_old.getCd_id_speditore());
//			org.aspcfs.modules.speditori.base.OrganizationAddress speditoreAddress = null;
//
//			if(speditore.getAddressList().size() > 0){
//				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
//			}
			
			
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

			ArrayList<Campione> campioni = loadCampioni(context, configTipo, b);
			
			ArrayList<Campione>	vecchiCampioni = null;
			vecchiCampioni = Campione.load( vecchioBean.getId(), configTipo,db );
			
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
						camp.setModified	( b.getModified() );
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
					camp.setEntered		( b.getEntered() );
					camp.setModified	( b.getEntered() );
					camp.setEntered_by	( getUserId(context) );
					camp.setModified_by	( getUserId(context) );
					camp.store			( db, configTipo );
				}
			}
			

			Tampone tampone = loadTampone( context);
			
			tampone.setEntered		( b.getEntered() );
			tampone.setId_macello(b.getId_macello());
			tampone.setSessione_macellazione(b.getCd_seduta_macellazione());

			tampone.setData_macellazione(b.getVpm_data());
			tampone.setModified	( b.getEntered() );
			tampone.setEntered_by	( getUserId(context) );
			tampone.setModified_by	( getUserId(context) );
			if ("on".equalsIgnoreCase(context.getParameter("checkTampone")))
			{
			if(tampone.getId()<= 0)
			{
				tampone.store			( db ,context);
				
				
			}
			else
			{
				tampone.updateTampone(db);
			}
			tampone.cancella_tampone_capo(b, db, configTipo);
			tampone.associa_tampone_capo( b, db, configTipo);
			
			}
			else
			{
				tampone.cancella_tampone_capo(b, db, configTipo);
			}

			
				b_old.storico_vam_non_conformita			= NonConformita.load	( b_old.getId(), configTipo,db );
				b_old.storico_vpm_campioni					= Campione.load			( b_old.getId(), configTipo,db );
				b_old.storico_lcso_organi					= Organi.loadByOrgani	( b_old.getId(), configTipo,db );
				b_old.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( b_old.getId(), configTipo,db );
				context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoAggiornato() );
			
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
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-delete"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		
		getConfigTipo(context);
		
		try
		{
			db		= this.getConnection( context );

			//loadOggetto(context.getParameter("id"), tipo, db);
			b = GenericBean.load(context.getParameter("id"), db, configTipo);
			
			b.storico_vam_non_conformita			= NonConformita.load	( b.getId(), configTipo,db );
			b.storico_vpm_campioni					= Campione.load			( b.getId(), configTipo,db );
			b.storico_lcso_organi					= Organi.loadByOrgani	( b.getId(), configTipo, db );
			b.storico_vpm_patologie_rilevate		= PatologiaRilevata.load( b.getId(), configTipo,db );
			GenericBean.delete( b.getId(), getUserId(context), db, configTipo );
			
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


	private ArrayList<NonConformita> loadNC(ActionContext context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
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
				
				ReflectionUtil.invocaMetodo(temp, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] { b.getId()});
				//temp.setId_capo		( b.getId() );
				temp.setNote	( note.get( i ).getValore() );
				temp.setId		( Integer.parseInt( ids.get( i ).getValore() ) );

				ret.add( temp );
			}

		}

		return ret;
	}
	
	private ArrayList<NonConformita> loadNCSedute(ActionContext context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
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
				
				temp.setId_seduta(p_seduta.getId());
				temp.setNote	( note.get( i ).getValore() );
				temp.setId		( Integer.parseInt( ids.get( i ).getValore() ) );

				ret.add( temp );
			}

		}

		return ret;
	}

	private ArrayList<Campione> loadCampioni(ActionContext context, String tipo2)
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

				temp.setId_capo			( b_old.getId() );
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
	
	private ArrayList<Campione> loadCampioni(ActionContext context, ConfigTipo configTipo, GenericBean c2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
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

				//temp.setId_capo			( c2.getId() );
				ReflectionUtil.invocaMetodo(temp, configTipo.getNomeMetodoRifAltreTabelle(), new Class[]{Integer.class}, new Object[] {c2.getId()});
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
	
	private ArrayList<Campione> loadCampioniSeduta(ActionContext context, ConfigTipo configTipo, PartitaSeduta c2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
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

				temp.setId_seduta		( c2.getId() );
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
	
	
	private Tampone loadTampone(ActionContext context)
	{
		Tampone ret = new Tampone();

	

		if (context.getRequest().getParameter("id_tampone") != null && !context.getRequest().getParameter("id_tampone").equals("") )
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

	private ArrayList<Organi> loadOrgani(ActionContext context )
	{
		ArrayList<Organi> ret = new ArrayList<Organi>();


		ArrayList<Parameter> patologia	= ParameterUtils.list( context.getRequest(), "lcso_patologia_" );
		//ArrayList<Parameter> stadio		= ParameterUtils.list( context.getRequest(), "lcso_stadio_" );
		ArrayList<Parameter> organo		= ParameterUtils.list( context.getRequest(), "lcso_organo_" );
		ArrayList<Parameter> ids		= ParameterUtils.list( context.getRequest(), "lcso_id_" );

		ArrayList<Parameter> lesione_milza				= ParameterUtils.list( context.getRequest(), "lesione_milza_" );
		ArrayList<Parameter> lesione_cuore				= ParameterUtils.list( context.getRequest(), "lesione_cuore_" );
		ArrayList<Parameter> lesione_polmoni			= ParameterUtils.list( context.getRequest(), "lesione_polmoni_" );
		ArrayList<Parameter> lesione_fegato				= ParameterUtils.list( context.getRequest(), "lesione_fegato_" );
		ArrayList<Parameter> lesione_rene				= ParameterUtils.list( context.getRequest(), "lesione_rene_" );
		ArrayList<Parameter> lesione_mammella			= ParameterUtils.list( context.getRequest(), "lesione_mammella_" );
		ArrayList<Parameter> lesione_apparato_genitale	= ParameterUtils.list( context.getRequest(), "lesione_apparato_genitale_" );
		ArrayList<Parameter> lesione_stomaco			= ParameterUtils.list( context.getRequest(), "lesione_stomaco_" );
		ArrayList<Parameter> lesione_intestino			= ParameterUtils.list( context.getRequest(), "lesione_intestino_" );
		ArrayList<Parameter> lesione_osteomuscolari		= ParameterUtils.list( context.getRequest(), "lesione_osteomuscolari_" );
		ArrayList<Parameter> lesione_visceri			= ParameterUtils.list( context.getRequest(), "lesione_visceri_" );

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
					temp.setId_capo			( b.getId() );
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

	private GenericBean loadBean(ActionContext context, ConfigTipo configTipo) throws Exception
	{
		GenericBean bean =  (GenericBean)ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBean());
		Method[] m = bean.getClass().getMethods();
		popolaBean(m,context,bean);
		m = bean.getClass().getSuperclass().getMethods();
		popolaBean(m,context,bean);
		//bean.setCategoria();
		return bean;

	}
	
	public boolean booleano(String param, ActionContext context)
	{
		String temp = context.getParameter( param );
		return 
		"true"	.equalsIgnoreCase( temp ) 
		|| "ok"		.equalsIgnoreCase( temp ) 
		|| "si"		.equalsIgnoreCase( temp ) 	
		|| "yes"	.equalsIgnoreCase( temp )
		|| "on"		.equalsIgnoreCase( temp );
	}

	public int intero(String param, ActionContext context)
	{
		int ret = -1;
		String temp = context.getParameter( param );
		try
		{
			ret = Integer.parseInt( temp );
		}
		catch (Exception e)
		{
			return 0 ;
		}
		return ret;
	}


	public String stringa(String param, ActionContext context)
	{
		String temp = context.getParameter( param );
		return (temp == null) ? (temp) : (temp.trim());
	}

	public Timestamp data(String param, ActionContext context)
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


	private void caricaLookup(ActionContext context,ConfigTipo configTipo)
	{
		caricaLookup(context, true, configTipo);
	}

	private void caricaLookup(ActionContext context, boolean select, ConfigTipo configTipo)
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
		lu.put( "lookup_lesione_visceri", 	"m_lookup_lesione_visceri" );
		lu.put( "lookup_lesione_polmoni", 	"m_lookup_lesione_polmoni" );
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
		Organization org = null;

		try
		{
			db = this.getConnection( context );
			while( e.hasMoreElements() )
			{
				String key		= e.nextElement();
				String value	= lu.get( key );
				LookupList list	= new LookupList( db, value, configTipo );
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

			String temporgId = context.getRequest().getParameter("orgId");
			if(temporgId == null)
			{
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			org = new Organization( db, tempid );
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
	


	public String getMeseFromData(String mese){

		switch (Integer.parseInt(mese)) {
		case 01 : mese = "Gennaio"    ;  break;
		case 02 : mese = "Febbraio"   ;  break;
		case 03 : mese = "Marzo"      ;  break;
		case 04 : mese = "Aprile"     ;  break;
		case 05 : mese = "Maggio"     ;  break;
		case 06 : mese = "Giugno"     ;  break;
		case 07 : mese = "Luglio"     ;  break;
		//case 08 : mese = "Agosto"     ;  break;
		//case 09 : mese = "Settembre"  ;  break;
		case 10 : mese = "Ottobre"    ;  break;
		case 11 : mese = "Novembre"   ;  break;
		case 12 : mese = "Dicembre"   ;  break;
		}
		if (mese.equals("08")){
			mese = "Agosto"; 
		}
		if (mese.equals("09")){
			mese = "Settembre";
		}

		return mese;
	}



	public void mergePDF(ArrayList<ByteArrayOutputStream> outputList, ByteArrayOutputStream out){
		try{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			List<PdfReader> readers = new ArrayList<PdfReader>();
			//int totalPages = 0;
			// Create Readers for the pdfs.
			for(ByteArrayOutputStream output : outputList) {
				PdfReader pdfReader = new PdfReader(output.toByteArray());
				readers.add(pdfReader);
				//totalPages += pdfReader.getNumberOfPages();
			}
			// Create a writer for the outputstream

			//BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			// data

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;

			// Loop through the PDF files and add to the output.
			for(PdfReader pdfReader : readers){

				// Create a new page in the target for each source page.
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					currentPageNumber++;
					document.newPage();
					pageOfCurrentReaderPDF++;
					page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);

					// Code for pagination.
					//					if (true) {
					//						cb.beginText();
					//						cb.setFontAndSize(bf, 9);
					//						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
					//						cb.endText();
					//					}
				}
				pageOfCurrentReaderPDF = 0;
			}

			document.close();

		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			//			try {
			//				if (out != null)
			//				out.close();
			//			} 
			//			catch (IOException ioe) {
			//				ioe.printStackTrace();
			//			}
		}

	}


	private String controlloUnivocita(ConfigTipo configTipo, ActionContext context, Connection db, String identificativo,GenericBean b2 ) throws Exception
	{
		if(identificativo!=null && !identificativo.equals(""))
		{
			AjaxCalls ac = new AjaxCalls();
			String codiceAzienda = (String)ReflectionUtil.invocaMetodo(b2, "getCd_codice_azienda_provenienza", null);
			Integer numOvini = null;
			Integer numCaprini = null;
			try
			{
				numOvini = (Integer)ReflectionUtil.invocaMetodo(b2, "getCd_num_capi_ovini", null);
				numCaprini = (Integer)ReflectionUtil.invocaMetodo(b2, "getCd_num_capi_caprini", null);
			}
			catch(NoSuchMethodException ex)
			{
				numOvini = 0;
				numCaprini = 0;
			}
			
			BeanAjax  ca = this.isCapoEsistente(identificativo, codiceAzienda, numOvini, numCaprini, configTipo);
			if( ca.isEsistente() )
			{
				this.freeConnection(context, db);
				context.getRequest().setAttribute("idCapo", b2.getId());
				context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoNonInserito() );
				return executeCommandNuovoCapo( context );
			}
		}
		return null;
	}
	
	private String controlloUnivocitaUpdate(ConfigTipo configTipo, ActionContext context, Connection db, String identificativo )
	{
		if(identificativo!=null && !identificativo.equals(""))
		{
			AjaxCalls ac = new AjaxCalls();
			PartitaAjax  pa = ac.isCapoEsistenteUpdate(b.getId(),identificativo,b.getCd_codice_azienda_provenienza(),((Partita)b).getCd_num_capi_ovini(),((Partita)b).getCd_num_capi_caprini());
			if( pa.isEsistente() )
			{
				this.freeConnection(context, db);
				context.getRequest().setAttribute(configTipo.getNomeVariabileIdJsp(), b.getId());
				context.getRequest().setAttribute( "messaggio", configTipo.getMessaggioCapoNonModificato() );
				return executeCommandModificaCapo(context);
			}
		}
		
		return null;
	}
	
	private Partita loadSeduta(ActionContext context) throws Exception
	{
		PartitaSeduta p = new PartitaSeduta();
		Method[] m = p.getClass().getMethods();
		popolaBean(m,context,p);
		m = p.getClass().getSuperclass().getMethods();
		popolaBean(m,context,p);
		//p.setCategoria();
		return p;
	}
	
	private String controlloStampaMod10(ConfigTipo configTipo, ActionContext context,boolean update) throws ParseException
	{
		if(PopolaCombo.verificaStampaMod10(b.getId_macello(), context.getParameter("vpm_data"), 0)==true)
		{
			if(!update)
				context.getRequest().setAttribute(configTipo.getNomeVariabileIdJsp(), b.getId());
			context.getRequest().setAttribute( "messaggio", configTipo.getDescrizioneBean() + " NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
			return executeCommandNuovoCapo( context );
		}
		
		return null;
	}
	
	private String controlloStampaMod10Seduta(ConfigTipo configTipo, ActionContext context,boolean update) throws ParseException
	{
		if(PopolaCombo.verificaStampaMod10(b.getId_macello(), context.getParameter("vpm_data"), 0)==true)
		{
			if(!update)
				context.getRequest().setAttribute(configTipo.getNomeVariabileIdJsp(), b.getId());
			context.getRequest().setAttribute( "messaggio", configTipo.getDescrizioneBean() + " NON inserito. Per questa seduta di macellazione e' stato stampato il Mod 10!" );
			return executeCommandToRiprendiPartita( context );
		}
		
		return null;
	}
	
	
	private String controlloProgressivoMacellazione(ConfigTipo configTipo, ActionContext context, Connection db, boolean update,GenericBean vb, String identificativo)
	{
			if(configTipo.hasProgressivoMacellazione() && b.getVpm_data()!=null)
			{
				AjaxCalls ac = new AjaxCalls();
				if(!ac.controlloProgressivoMacellazione(configTipo,b.getId_macello(), new SimpleDateFormat("dd/MM/yyyy").format( new Date(b.getVpm_data().getTime() ) ), b.getProgressivo_macellazione(), identificativo))
				{
					this.freeConnection(context, db);
					context.getRequest().setAttribute( "messaggio", configTipo.getDescrizioneBean()+ " NON inserito. Progressivo Macellazione gia' esistente!" );
					if(update)
						context.getRequest().setAttribute("id", vb.getId());
					return executeCommandNuovoCapo( context );
				}
			}
		
		return null;
	}
	
	private void settaStorico(ConfigTipo configTipo, Connection db)
	{
			b.storico_vam_non_conformita		= NonConformita.load	( b.getId(), configTipo,db );
			b.storico_vpm_campioni				= Campione.load			( b.getId(), configTipo,db );
			b.storico_lcso_organi				= Organi.loadByOrgani	( b.getId(), configTipo, db );
			b.storico_vpm_patologie_rilevate	= PatologiaRilevata.load( b.getId(), configTipo,db );
	}
	
	private void clona(ConfigTipo configTipo,ActionContext context)
	{
		Capo capoClonato = new Capo();
		capoClonato.setCd_mod4(b.getCd_mod4());
		capoClonato.setCd_data_mod4(b.getCd_data_mod4());
		capoClonato.setCd_targa_mezzo_trasporto(b.getCd_targa_mezzo_trasporto());
		capoClonato.setCd_tipo_mezzo_trasporto(b.getCd_tipo_mezzo_trasporto());
		capoClonato.setCd_codice_azienda_provenienza(b.getCd_codice_azienda_provenienza());
		capoClonato.setCd_trasporto_superiore8ore(b.isCd_trasporto_superiore8ore());
		
		capoClonato.setCd_id_speditore(b.getCd_id_speditore());
		capoClonato.setCd_veterinario_1(b.getCd_veterinario_1());
		capoClonato.setCd_veterinario_2(b.getCd_veterinario_2());
		capoClonato.setCd_veterinario_3(b.getCd_veterinario_3());
		capoClonato.setCd_data_arrivo_macello(b.getCd_data_arrivo_macello());
		capoClonato.setVpm_veterinario(b.getVpm_veterinario());
		capoClonato.setVpm_veterinario_2(b.getVpm_veterinario_2());
		capoClonato.setVpm_veterinario_3(b.getVpm_veterinario_3());
		context.getRequest().setAttribute( "idCapo", b.getId() );
		context.getRequest().setAttribute( "Capo", capoClonato );
	}
	
	private ArrayList<String> getDateMacellazione(ConfigTipo configTipo, Connection db, ActionContext context)
	{
		ArrayList<String> listaDateMacellazione = GenericBean.loadDateMacellazioneByStabilimento( context.getParameter( "orgId" ), db, configTipo );
		return listaDateMacellazione;
	}
	
	private void getConfigTipo(ActionContext context)
	{
		if(context.getSession().getAttribute("configTipo")!=null)
			configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
	}
	
	public void gestioneProgressivoModulo(Connection db, StampeModuli stampeModuli, ChiaveModuliMacelli chiave, ByteArrayOutputStream out) throws Exception
	{

		//Gestione progressivo
		stampeModuli.setProgressivo(0);
		stampeModuli.setOldProgressivo(0);
		StampeModuliDao stampeModuliDao = StampeModuliDao.getInstance();
		stampeModuliDao.select(db, stampeModuli, chiave);

		String outToString = new String(out.toByteArray());
		if(outToString.contains("ModDate")){
			outToString = outToString.substring(0, outToString.indexOf("ModDate"));
		}
		//se il progressivo e' zero, significa che NON esiste la stampa di quel modulo per cui va inserita in banca dati
		if(stampeModuli.getProgressivo() == 0){
			stampeModuli.setHashCode(outToString.hashCode());
			stampeModuliDao.insert(db, stampeModuli);
			stampeModuliDao.select(db,stampeModuli,chiave);
		}
		//se il progressivo e' diverso da zero, significa che esiste la stampa di quel modulo per cui va controllato 
		//se ci sono modifiche con la versione precedente (mediante hashcode)
		else{
			if(stampeModuli.getHashCode() != outToString.hashCode()){
				stampeModuli.setHashCode(outToString.hashCode());
				stampeModuli.setOldProgressivo(stampeModuli.getProgressivo());
				stampeModuliDao.update(db, stampeModuli);
				stampeModuliDao.select(db,stampeModuli,chiave);
			}
		}

	}
	
	private boolean trova(String[] lista, String elemento){
		for (int i=0; i<lista.length; i++)
			if (lista[i].equalsIgnoreCase(elemento))
				return true;
		return false;
		
	}
	
	private void popolaBean(Method[] m,ActionContext context,GenericBean bean) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		for( int j = 0; j < m.length; j++ )
	    {
	        String met = m[j].getName();
	        if(m[j].getName().toLowerCase().startsWith("set"))
	        {
	        	Class[] types = m[j].getParameterTypes();
	        	if(types.length>0)
	        	{
	        		Class t = types[0];
	        		String nomeCampo = m[j].getName().substring(3);
        			String inizio = nomeCampo.substring(0, 1).toLowerCase();
        			nomeCampo = inizio + nomeCampo.substring(1);
        			Object[] parametri = null;
        					
	        		if(t.getName().toLowerCase().equals("java.lang.string"))
	        			parametri = new Object[]{ stringa( nomeCampo, context ) };
	        		else if(t.getName().toLowerCase().equals("int") || t.getName().toLowerCase().equals("java.lang.integer"))
	        		{
	        			if(stringa( nomeCampo, context )!=null && stringa( nomeCampo, context ).equals(""))
	        				parametri = new Object[]{ 0 };
	        			else
	        				parametri = new Object[]{ intero( nomeCampo, context ) };
	        		}
	        		else if(t.getName().toLowerCase().equals("java.sql.timestamp"))
	        			parametri = new Object[]{ data( nomeCampo, context ) };
	        		else if(t.getName().toLowerCase().equals("boolean") || t.getName().toLowerCase().equals("java.lang.boolean"))
	        			parametri = new Object[]{ booleano( nomeCampo, context ) };
	        		ReflectionUtil.invocaMetodo(bean, m[j].getName(), types, parametri);
	        	}
	        }
	    }
	}
	
	
	/**
	 * GESTIONE RICHIESTE ISTOPATOLOGICO
	 */
	
	
	public String executeCommandRichiestaIstopatologico(ActionContext context){

		String ret = "RichiestaIstopatologicoOK";

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		try
		{
			
			if(context.getSession().getAttribute("configTipo")!=null)
				configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
		
		
			db		= this.getConnection( context );
			
			b = GenericBean.load(context.getParameter("id"), db, configTipo);
		//	Capo c	= Capo.load( context.getParameter( "id" ), db );
			Organization org = new Organization( db, b.getId_macello() );



		//	ArrayList<Organi>   			organi	 		= Organi.loadByOrgani	( b.getId(), configTipo );
			
			ArrayList<Organi>   			organiTumorali	 		= GenericBean.checkOrganiTumorali(db, b.getId(), configTipo);
			
//			TreeMap<Integer, ArrayList<Organi>> organiNew = new TreeMap<Integer, ArrayList<Organi>>();
//			for(Organi o : organi){
//				if(organiNew.containsKey(o.getLcso_organo())){
//					organiNew.get(o.getLcso_organo()).add(o);
//				}
//				else{
//					ArrayList<Organi> organiList = new ArrayList<Organi>();
//					organiList.add(o);
//					organiNew.put(o.getLcso_organo(), organiList);
//				}
//			}


			context.getRequest().setAttribute( "Capo", b );
			context.getRequest().setAttribute( "OrgDetails", org );
			context.getRequest().setAttribute("orgId",b.getId_macello()+"" );

			context.getRequest().setAttribute( "OrganiList", organiTumorali );
			//context.getRequest().setAttribute( "OrganiListNew", organiNew );


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

		caricaLookup(context, true, configTipo);

		return ret;
	
		
	}
	
	
	public String executeCommandSaveIstopatologico(ActionContext context){

		String ret = "SaveIstopatologicoOK";

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-add"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		
		GenericBean capo = new GenericBean();
		
		
		RichiestaIstopatologico thisRichiesta = new RichiestaIstopatologico();
		
		try
		{		
			if(context.getSession().getAttribute("configTipo")!=null)
				configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
			db		= this.getConnection( context );
			
			capo = Capo.load(( (context.getRequest().getParameter("idCapo")!= null) ? context.getRequest().getParameter("idCapo") :context.getRequest().getParameter("idPartita"))  ,
					db, configTipo);
			thisRichiesta = (RichiestaIstopatologico) context.getRequest().getAttribute(
				"RichiestaIstopatologico");
			thisRichiesta.setUtenteInserimento(getUserId(context));
			thisRichiesta.setUtenteModifica(getUserId(context));
			ArrayList<Organi> organi = loadOrganiRichiestaIstopatologico(context, db);
			thisRichiesta.setLista_organi_richiesta(organi);
			thisRichiesta.store(db,context);
			
			
			//Aggiornamento informazioni istopatologico sul capo o partita
			capo.setIstopatologico_richiesta(true);
			capo.setIstopatologico_data_richiesta(thisRichiesta.getDataInserimento());
			capo.setIstopatologico_id(thisRichiesta.getId());
			
			ArrayList<Campione> campioni = Campione.load( capo.getId(), configTipo, db );
			
			capo.update(db, campioni, configTipo);


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


		context.getRequest().setAttribute("OrgId", capo.getId_macello()+"");
		context.getRequest().setAttribute("idIstopatologico", thisRichiesta.getId());
		return executeCommandList( context );
	
		
	}
	
	
	public String executeCommandPrepareInserisciEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "PrepareEsitoRichiestaIstopatologicoOK";

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-view"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		Organization org = null;
		
		try
		{	
			
			if(context.getSession().getAttribute("configTipo")!=null)
				configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
			
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db, configTipo);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new Organization( db, istopatologico.getCapo().getId_macello() );
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

		context.getRequest().setAttribute("orgId", istopatologico.getCapo().getId_macello() + "" );
		caricaLookup(context, false, configTipo);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

		return ret;
	
		
	}
	
	
	
	public String executeCommandRichiestaIstopatologicoStampaModelloInvioCampioni(ActionContext context){
		
		String ret = "ModelloInvioCampioniOK";
		
		

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-view"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		Organization org = null;
		
		if(context.getSession().getAttribute("configTipo")!=null)
			configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
		
		
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db, configTipo);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new Organization( db, istopatologico.getCapo().getId_macello() );
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
		context.getRequest().setAttribute("orgId", istopatologico.getCapo().getId_macello()+"" );
		caricaLookup(context, false, configTipo);
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);
		
		return ret;
		
	}
	
	
	
	
	public String executeCommandSaveEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "SaveEsitoRichiestaIstopatologicoOK";

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-add"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		Organization org = null;
		
		if(context.getSession().getAttribute("configTipo")!=null)
			configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
		
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db, configTipo);
			}
			
			
			istopatologico.caricaEsitoIstopatologico(context);
			
			istopatologico.update(db);
			
			
			//AGGIORNAMENTO REGISTRO TUMORI
			RegistroTumoriRemoteUtil.aggiuntiEsitoTumorale(istopatologico.getIdentificativoRichiesta(), db);
			
			
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new Organization( db, istopatologico.getCapo().getId_macello() );
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
		
		context.getRequest().setAttribute("orgId", istopatologico.getCapo().getId_macello()+"" );

		caricaLookup(context, false, configTipo);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

		return ret;
	
		
	}
	
	public String executeCommandVisualizzaEsitoRichiestaIstopatologico(ActionContext context){

		String ret = "VisualizzaEsitoRichiestaIstopatologicoOK";

//		if (!hasPermission(context, "stabilimenti-stabilimenti-istopatologico-esito-view"))
//		{
//			return ("PermissionError");
//		}

		Connection db = null;
		RichiestaIstopatologico istopatologico = null;
		Organization org = null;
		
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db, configTipo);
			}
			
			if (istopatologico.getCapo().getId_macello() > 0){
				org = new Organization( db, istopatologico.getCapo().getId_macello() );
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

		context.getRequest().setAttribute("orgId", istopatologico.getCapo().getId_macello() + "" );
		caricaLookup(context, false, configTipo);
		
		context.getRequest().setAttribute( "OrgDetails", org );
		context.getRequest().setAttribute("richiestaIstopatologico", istopatologico);

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
	
	
public String executeCommandPrepareCancellazionePartita(ActionContext context){
	
	String idPartita =context.getRequest().getParameter("idPartita");
	
	Connection db = null;
	
	String ret = "";
	String error="";
	Partita p = new Partita();

	try {
		db = this.getConnection( context );
		p = (Partita) Partita.load(idPartita, db, configTipo);
			
		if (p.isCancellabile(db, configTipo)){
			ret = "prepareCancellazionePartitaOK";
		}
		else {
			error = "Articolo 17 stampato per almeno una delle sedute della partita.";
			context.getRequest().setAttribute("Error", error);
			ret = "cancellazionePartitaError";
	}
		
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("Partita", p);
	return ret;
}
	
public String executeCommandCancellaPartita(ActionContext context) throws Exception{
	String idPartita =context.getRequest().getParameter("idPartita");
	String note = context.getRequest().getParameter("note");
	Connection db = null;
	Partita p = new Partita();
	try {
		db = this.getConnection( context );
		p = (Partita) Partita.load(idPartita, db, configTipo);
		
		int userId =  getUserId(context);

		java.util.Date date= new java.util.Date();
		Timestamp current_timestamp = new Timestamp(date.getTime());
		 
		
		p.setDeleted_by(userId);
		p.setNote_cancellazione(note);	
		p.setTrashed_date(current_timestamp);
		p.cancella(db, p.getId());
	
		LogCancellazioneCapiPartite log = new LogCancellazioneCapiPartite();
		log.setIdPartita(p.getId());
		log.setDataOperazione(current_timestamp);
		log.setIdUtente(userId);
		log.setNumero(p.getCd_partita());
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
	context.getRequest().setAttribute("Partita", p);
	return "cancellazionePartitaOK";
}
}
