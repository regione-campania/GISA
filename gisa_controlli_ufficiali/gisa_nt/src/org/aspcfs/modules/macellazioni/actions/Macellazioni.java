/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.actions;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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






































import javax.naming.NamingException;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiMacelli;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUpload;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Campione;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioni.base.CapoAjax;
import org.aspcfs.modules.macellazioni.base.CapoImport;
import org.aspcfs.modules.macellazioni.base.CapoLog;
import org.aspcfs.modules.macellazioni.base.CapoLogDao;
import org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazioni.base.ChiaveModuliMacelli;
import org.aspcfs.modules.macellazioni.base.ImportLogList;
import org.aspcfs.modules.macellazioni.base.LogCancellazioneCapiPartite;
import org.aspcfs.modules.macellazioni.base.NonConformita;
import org.aspcfs.modules.macellazioni.base.Organi;
import org.aspcfs.modules.macellazioni.base.PatologiaRilevata;
import org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazioni.base.RegistroTumoriRemoteUtil;
import org.aspcfs.modules.macellazioni.base.RichiestaIstopatologico;
import org.aspcfs.modules.macellazioni.base.StampeModuli;
import org.aspcfs.modules.macellazioni.base.StampeModuliDao;
import org.aspcfs.modules.macellazioni.base.Tampone;
import org.aspcfs.modules.macellazioni.base.TipoRicerca;
import org.aspcfs.modules.macellazioni.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninew.base.GenericBean;
import org.aspcfs.modules.macellazioninew.base.Tipo;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.speditori.base.OrganizationAddress;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;
import org.bouncycastle.jce.provider.JDKKeyFactory.RSA;
import org.directwebremoting.webwork.AjaxResult;
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
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.JsonObject;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
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
					context.getRequest().setAttribute("orgId",c.getId_macello()+"" );
				Organization org = new Organization( db, c.getId_macello() );

					caricaLookup(context, false);
				context.getRequest().setAttribute( "Capo", c );
				context.getRequest().setAttribute( "OrgDetails", org );

				context.getRequest().setAttribute("matricola_cercata", context.getParameter("matricola") );
				
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
		
		context.getRequest().setAttribute("matricola_cercata", context.getParameter("matricola") );
		
		return "SearchMatricolaOK" ;
	}

	public String executeCommandToStampeModuli( ActionContext context )
	{

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
			Iterator<Object> key = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getApplicationProperties().keySet().iterator();
			while (key.hasNext())
			{
				String kiave = (String) key.next();
				if(kiave.startsWith("GET_DATE"))
				{
					String select1 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty(kiave);
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


	public String executeCommandStampeModuli( ActionContext context )
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-stampe-moduli-view"))
		{
			return ("PermissionError");
		}

		String reportDir = getWebInfPath(context, "template_report");
		String reportDirImg = getWebInfPath(context, "reports");
		String displayName = "";
		Connection db = null;


		try {

			db = this.getConnection(context);
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );

			HashMap<String, ArrayList<String>> stampe_date = new HashMap<String, ArrayList<String>>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Iterator<Object> key = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getApplicationProperties().keySet().iterator();
			while (key.hasNext())
			{
				String kiave = (String) key.next();
				if(kiave.startsWith("GET_DATE"))
				{
					String select1 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty(kiave);
					PreparedStatement stat1 = db.prepareStatement(select1);
					stat1.setInt( 1,  macello.getOrgId());
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

			
			int tipoModulo = Integer.parseInt(context.getParameter("tipoModulo"));

			String data = context.getParameter( "data" + tipoModulo );
			if(data == null || data.equals("")){
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToStampeModuliOK";
			}
			Timestamp d = new Timestamp( sdf.parse( data ).getTime() );

			//Set info base per StampeModuli
			StampeModuli stampeModuli = new StampeModuli();
			stampeModuli.setTipoModulo(tipoModulo);
			stampeModuli.setDataModulo(d);
			stampeModuli.setAslMacello(macello.getSiteId());
			stampeModuli.setIdMacello(macello.getOrgId());

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

			boolean closeDocument = true;

			switch (tipoModulo){
			case 1:	
				document.add(instanceImg);
				String select1 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_IDATIDOSI");
				PreparedStatement stat1 = db.prepareStatement(select1);
				stat1.setInt( 1, macello.getOrgId() );
				stat1.setTimestamp( 2, d );
				ResultSet res1 = stat1.executeQuery();
				ArrayList<Capo> listaCapi = new ArrayList<Capo>();
				Capo capo = null;
				while(res1.next()){
					capo = Capo.load(res1.getString("id"), db);
					listaCapi.add(capo);
				}

				if( listaCapi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con idatidosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFIdatidosi(db, document, writer, out, macello, listaCapi, data, stampeModuli); 
				displayName = "Modello_idatidosi.pdf";
				break;
			case 2:
				document.add(instanceImg);
				String select2 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_MODELLO_MARCHI");
				PreparedStatement stat2 = db.prepareStatement( select2 );
				stat2.setInt( 1, macello.getOrgId() );
				stat2.setTimestamp( 2, d );
				ResultSet res2 = stat2.executeQuery();

				ArrayList<Capo> listaCapi_marchi = new ArrayList<Capo>();
				TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC = new TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>>();
				TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti = new TreeMap<Integer, ArrayList<ProvvedimentiCASL>>();
				Capo capo_macello = null;
				while(res2.next()){
					capo_macello = Capo.load(res2.getString("id"), db);
					listaCapi_marchi.add(capo_macello);
					hashCapiNC.put(capo_macello.getId(), Casl_Non_Conformita_Rilevata.load(capo_macello.getId(), db));
					hashCapiProvvedimenti.put(capo_macello.getId(), ProvvedimentiCASL.load(capo_macello.getId(), db));
				}

				if( listaCapi_marchi.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFArrivoAlMacello(db, document, writer, out, macello, listaCapi_marchi, hashCapiNC, hashCapiProvvedimenti, data, stampeModuli);
				displayName = "Modello_Arrivo_Al_macello.pdf";

				break;
			case 3:  
				document.add(instanceImg);
				String select3 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_INFETTI");
				PreparedStatement stat3 = db.prepareStatement( select3 );
				stat3.setInt( 1, macello.getOrgId() );
				stat3.setTimestamp( 2, d );
				ResultSet res3 = stat3.executeQuery();

				ArrayList<Capo> listaCapiBrucellosi = new ArrayList<Capo>();
				ArrayList<Capo> listaCapiTubercolosi = new ArrayList<Capo>();
				Capo capo_infetto = null;
				while(res3.next()){
					capo_infetto = Capo.load(res3.getString("id"), db);
					if(capo_infetto.getCd_macellazione_differita() == 1){
						listaCapiBrucellosi.add(capo_infetto);
					}
					else if(capo_infetto.getCd_macellazione_differita() == 2){
						listaCapiTubercolosi.add(capo_infetto);
					}
				}

				if( listaCapiBrucellosi.size() <= 0 && listaCapiTubercolosi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo infetto macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				if( listaCapiBrucellosi.size() > 0){
					this.createPDFAnimaliInfettiDa(db, document, out, listaCapiBrucellosi, macello, data, "BRUCELLOSI", stampeModuli );
				}
				if( listaCapiTubercolosi.size() > 0){
					if(listaCapiBrucellosi.size() > 0){
						ByteArrayOutputStream outNew = new ByteArrayOutputStream(); 
						ByteArrayOutputStream out2 = new ByteArrayOutputStream(); 
						Document document2 = new Document();
						PdfWriter writer2 = PdfWriter.getInstance(document2, out2);
						document2.open();
						document2.add(instanceImg);
						this.createPDFAnimaliInfettiDa(db, document2, out2, listaCapiTubercolosi, macello, data, "TUBERCOLOSI", stampeModuli );

						ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
						outputList.add(out);
						outputList.add(out2);
						mergePDF(outputList, outNew);
						out.reset();
						out.write(outNew.toByteArray());
					}
					else{
						this.createPDFAnimaliInfettiDa(db, document, out, listaCapiTubercolosi, macello, data, "TUBERCOLOSI", stampeModuli );
					}
				}

				displayName = "Modello_Animali_Infetti.pdf";
				break;
			case 4: 
				document.add(instanceImg);
				String select4 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_GRAVIDI");
				PreparedStatement stat4 = db.prepareStatement( select4 );
				stat4.setInt( 1, macello.getOrgId() );
				stat4.setTimestamp( 2, d );
				ResultSet res4 = stat4.executeQuery();

				ArrayList<Capo> listaCapiGravidi = new ArrayList<Capo>();
				Capo capo_gravido = null;

				while(res4.next()){
					capo_gravido = Capo.load(res4.getString("id"), db);
					listaCapiGravidi.add(capo_gravido);
				}

				if( listaCapiGravidi.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo gravido macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFAnimaliGravidi(db, document, writer, out, macello, listaCapiGravidi,data,stampeModuli);	
				displayName = "Modello_Animali_Gravidi.pdf";
				break;
			case 5: 
				String select5 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_TBC_RILEVAZIONE_MACELLO");
				PreparedStatement stat5 = db.prepareStatement( select5 );
				stat5.setInt( 1, macello.getOrgId() );
				stat5.setTimestamp( 2, d );
				ResultSet res5 = stat5.executeQuery();

				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreTBC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
				TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiTBC = new TreeMap<Integer, ArrayList<Capo>>();
				Capo capo_tbc = null;

				while(res5.next()){
					capo_tbc = Capo.load(res5.getString("id"), db);
					if(capo_tbc.getCd_id_speditore() > -1) {
					
						if(!hashSpeditoreCapiTBC.containsKey(capo_tbc.getCd_id_speditore())){
							hashSpeditoreCapiTBC.put(capo_tbc.getCd_id_speditore(), new ArrayList<Capo>());
						}
						hashSpeditoreCapiTBC.get(capo_tbc.getCd_id_speditore()).add(capo_tbc);
						if(!hashSpeditoreTBC.containsKey(capo_tbc.getCd_id_speditore())){
							hashSpeditoreTBC.put(capo_tbc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_tbc.getCd_id_speditore()) );
						}
					}

					

				}

				if( hashSpeditoreCapiTBC.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con piano di risanamento tubercolosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFRilevazioneTBC(context, db, document, writer, out, macello, reportDir, hashSpeditoreTBC,hashSpeditoreCapiTBC,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_TBCRilevazione_Macelli.pdf";
				break;
			case 6: 
				String select6 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_BRC_RILEVAZIONE_MACELLO");
				PreparedStatement stat6 = db.prepareStatement( select6 );
				stat6.setInt( 1, macello.getOrgId() );
				stat6.setTimestamp( 2, d );
				ResultSet res6 = stat6.executeQuery();

				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
				TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiBRC = new TreeMap<Integer, ArrayList<Capo>>();
				Capo capo_brc = null;

				while(res6.next()){
					capo_brc = Capo.load(res6.getString("id"), db);
					if(capo_brc.getCd_id_speditore() > -1){
						if(!hashSpeditoreCapiBRC.containsKey(capo_brc.getCd_id_speditore())){
							hashSpeditoreCapiBRC.put(capo_brc.getCd_id_speditore(), new ArrayList<Capo>());
						}
						hashSpeditoreCapiBRC.get(capo_brc.getCd_id_speditore()).add(capo_brc);
						if(!hashSpeditoreBRC.containsKey(capo_brc.getCd_id_speditore())){
							hashSpeditoreBRC.put(capo_brc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_brc.getCd_id_speditore()) );
						}
					}
					
				}

				if( hashSpeditoreCapiBRC.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con piano di risanamento brucellosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFRilevazioneBRC(db, document, writer, out, macello, reportDir, hashSpeditoreBRC,hashSpeditoreCapiBRC,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_BRCRilevazione_Macelli.pdf";

				break;
			case 7:
				String select7 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_1033_TBC");
				PreparedStatement stat7 = db.prepareStatement( select7 );
				stat7.setInt( 1, macello.getOrgId() );
				stat7.setTimestamp( 2, d );
				ResultSet res7 = stat7.executeQuery();

				ArrayList<Capo> listaCapi_1033_tbc = new ArrayList<Capo>();
				TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani = new TreeMap<Integer, ArrayList<Organi>>();
				Capo capo_1033_tbc = null;

				while(res7.next()){
					capo_1033_tbc = Capo.load(res7.getString("id"), db);
					listaCapi_1033_tbc.add(capo_1033_tbc);
					hashCapiOrgani.put(capo_1033_tbc.getId(), Organi.loadByOrgani(capo_1033_tbc.getId(), db));
				}

				if( listaCapi_1033_tbc.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con organi colpiti da tubercolosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDF1033TBC(db, document, writer, out, macello, reportDir, listaCapi_1033_tbc,hashCapiOrgani,data,stampeModuli);

				closeDocument = false;
				displayName = "Modello_1033.pdf";

				break;
			case 8: 
				//document.add(instanceImg);
				String select8 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_EVIDENZE_VISITA_ANTE_MORTEM");
				PreparedStatement stat8 = db.prepareStatement( select8 );
				stat8.setInt( 1, macello.getOrgId() );
				stat8.setTimestamp( 2, d );
				ResultSet res8 = stat8.executeQuery();

				/*Sicuramente va cambiata la query rispetto al modulo 2---*/
				ArrayList<Capo> listaCapi_ante_mortem = new ArrayList<Capo>();
				TreeMap<Integer, ArrayList<NonConformita>> hashCapiNCAnteMortem = new TreeMap<Integer, ArrayList<NonConformita>>();
				Capo capo_visita_ante_mortem = null;
				while(res8.next()){
					capo_visita_ante_mortem = Capo.load(res8.getString("id"), db);
					//Recuperare non conformita'
					listaCapi_ante_mortem.add(capo_visita_ante_mortem);
					hashCapiNCAnteMortem.put(capo_visita_ante_mortem.getId(), NonConformita.load(capo_visita_ante_mortem.getId(), db));
				}

				if( listaCapi_ante_mortem.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFVisitaAnteMortem(db, document, writer, out, macello, listaCapi_ante_mortem, hashCapiNCAnteMortem, data, instanceImg, stampeModuli);
				closeDocument = false;
				displayName = "Modello_Evidenze_Visita_AnteMortem.pdf";
				break;
			case 9: 
				//document.add(instanceImg);
				String select9 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_MORTE_ANTE_MACELLAZIONE");
				PreparedStatement stat9 = db.prepareStatement( select9 );
				stat9.setInt( 1, macello.getOrgId() );
				stat9.setTimestamp( 2, d );
				ResultSet res9 = stat9.executeQuery();

				ArrayList<Capo> listaCapiMortiAnte = new ArrayList<Capo>();
				Capo capo_ante_macellazione = null;

				while(res9.next()){

					capo_ante_macellazione = Capo.load(res9.getString("id"), db);
					listaCapiMortiAnte.add(capo_ante_macellazione);

				}

				if( listaCapiMortiAnte.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo morto ante macellazione il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFMorteAnteMacellazione(db, document, writer, out, macello, listaCapiMortiAnte, data, instanceImg, stampeModuli);
				closeDocument = false;
				displayName = "Modello_Morte_Antecedente_Macellazione.pdf";
				break;
			case 10:
				String select10 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_LEB");
				PreparedStatement stat10 = db.prepareStatement( select10 );
				stat10.setInt( 1, macello.getOrgId() );
				stat10.setTimestamp( 2, d );
				ResultSet res10 = stat10.executeQuery();

				TreeMap<Integer, Capo> hashCapoLEB = new TreeMap<Integer, Capo>();
				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreLEB = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();

				Capo capo_leb = null;

				while(res10.next()){
					capo_leb = Capo.load(res10.getString("id"), db);
					hashCapoLEB.put(capo_leb.getId(), capo_leb );
					//hashSpeditoreLEB.put(capo_leb.getId(), new org.aspcfs.modules.speditori.base.Organization(db,capo_leb.getCd_id_speditore()) );
				}

				if( hashCapoLEB.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo bovino con piano di risanamento leucosi bovina macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFAnimaliLEB(db, document, writer, out, macello, reportDir, hashCapoLEB,hashSpeditoreLEB,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_LEB.pdf";
				break;
			case 11:
				document.add(instanceImg);
				String select11 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_TRASPORTI_ANIMALI_INFETTI");
				PreparedStatement stat11 = db.prepareStatement( select11 );
				stat11.setInt( 1, macello.getOrgId() );
				stat11.setTimestamp( 2, d );
				ResultSet res11 = stat11.executeQuery();

				ArrayList<Capo> listaCapiTrasportati = new ArrayList<Capo>();
				Capo capo_trasp = null;

				while(res11.next()){
					capo_trasp = Capo.load(res11.getString("id"), db);
					listaCapiTrasportati.add(capo_trasp);

				}

				if( listaCapiTrasportati.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo affetto da tubercolosi o brucellosi arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFTrasportoAnimaliInfetti(db, document, writer, out, macello, listaCapiTrasportati, data, stampeModuli);
				displayName = "Modello_Trasporto_Animali_Infetti.pdf";

				break;
			default : System.out.println("Default");
			}

			/*RM: IN CASO DI LETTURA TEMPLATE IL DOCUMENT.CLOSE NN C VUOLE!!!!*/

			if(closeDocument){
				document.close();
			}

			FileDownload fileDownload = new FileDownload();
			fileDownload.setDisplayName(displayName);
			
			GestioneAllegatiMacelli allegato = new GestioneAllegatiMacelli();
			allegato.setBa(out.toByteArray());
			allegato.setOrg(macello.getOrgId());
			allegato.setTipoDocumento("Macelli_"+tipoModulo);
			return allegato.chiamaServerDocumentale(context);
				
			
			//fileDownload.sendFile(context,out.toByteArray(), "application/pdf");

		}
		catch ( ParseException pe ){
			pe.printStackTrace();
			context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			return "ToStampeModuliOK";
		}
		catch (Exception errorMessage){
			context.getRequest().setAttribute( "messaggio", "Si e' verificato un problema" );
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return "ToStampeModuliOK";
		}
		finally
		{
			this.freeConnection(context, db);
		}


		//return "-none-";
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
			Organization org = new Organization( db, c.getId_macello() );

			if( c.getCd_id_speditore() > 0 ){
				org.aspcfs.modules.speditori.base.Organization speditore = new org.aspcfs.modules.speditori.base.Organization( db, c.getCd_id_speditore() );
				if(speditore.getAddressList().size() > 0){
					OrganizationAddress speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
					context.getRequest().setAttribute( "SpeditoreAddress", speditoreAddress );
				}
				context.getRequest().setAttribute( "Speditore", speditore );
			}

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
			String id = (String) context.getRequest().getParameter("orgId");
			int orgId = new Integer(id);
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
			stat.setInt( 1, orgId );
			stat.setTimestamp( 2, data );
			
			ResultSet rs = stat.executeQuery();
			int maxSedutaMacellazione = 0;
			while (rs.next()) {
	            maxSedutaMacellazione = rs.getInt(1);
			}
			
			// SELEZIONO SE E' SEDUTA PREGRESSA
			String selectPregressa = "SELECT seduta_pregressa from m_capi_sedute where id_macello = ?  AND data = ? and numero = 1";
			PreparedStatement stat2 = db.prepareStatement( selectPregressa );
			stat2.setInt( 1, orgId );
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
			stat.setInt(2, orgId );
			stat.setTimestamp( 3, data );
			stat.setBoolean(4,  sedutaPregressa);
			stat.execute();
			
			String queryUpdateTamponi = "UPDATE m_vpm_tamponi SET sessione_macellazione = ? " +
			"WHERE id_macello = ? AND data_macellazione = ? AND (sessione_macellazione =0 OR sessione_macellazione <=0)";
			stat = db.prepareStatement( queryUpdateTamponi );
			stat.setInt( 1, maxSedutaMacellazione+1 );
			stat.setInt(2, orgId );
			stat.setTimestamp( 3, data );
			stat.execute();
			
			String sessioneCorrente =  sessioneMacellazioneString  + "-" + (maxSedutaMacellazione+1);
			context.getRequest().setAttribute( "sessioneCorrente", sessioneCorrente );
			
			context.getRequest().setAttribute( "messaggio", "Sessione " + sessioneCorrente + " aggiunta" );
			context.getRequest().setAttribute("comboSessioniMacellazione", sessioneCorrente);
			
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
			String id = (String) context.getRequest().getParameter("orgId");
			int orgId = new Integer(id);
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
			stat.setInt(1, orgId );
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
		
		int orgId = -1;
		int tipoInt = -1;
		
		String orgIdString = context.getParameter("orgId");
		String tipoString = context.getParameter("tipo"); 	
		
		if (orgIdString==null)
			orgIdString = (String) context.getRequest().getAttribute("orgId");
		if (tipoString==null)
			tipoString = (String) context.getRequest().getAttribute("tipo");
		
		try {tipoInt= Integer.parseInt(tipoString);} catch (Exception e){}
		try {orgId= Integer.parseInt(orgIdString);} catch (Exception e){}
		
		
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
			
			ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneUltimaSessPopolata(orgIdString, db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );
			
			//Recupero sessioni di macellazioni per riempire la combo
			ArrayList<String> listaSessioniMacellazione = Capo.loadSessioniMacellazioneByStabilimento( orgIdString , db );
			context.getRequest().setAttribute( "listaSessioniMacellazione", listaSessioniMacellazione );
			
			//Lista completa per controlli su pregresso
			ArrayList<String> listaSessioniMacellazioneCompleta = Capo.loadSessioniMacellazioneByStabilimentoCompleta( orgIdString, db );
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
					capi = Capo.loadByStabilimentoPerSessioneMacellazione( orgIdString,dataMacellazioneTimestamp, numSessioneMacellazione, db );
				}
				else{
					capi = Capo.loadByStabilimentoCapiNonMacellati( orgIdString, db );
					
				}
			}
			else
			{
				if (orgIdString!=null)
					capi = Capo.loadByStabilimento( orgIdString, db );
				else
					if(context.getRequest().getAttribute("OrgId")!=null)
						capi = Capo.loadByStabilimento( context.getRequest().getAttribute("OrgId")+"", db );
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
								return "<a href=\"Macellazioni.do?command=Details&id=" + id + "&orgId=" + id_mac + "\">" + temp + "</a>";
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
								return "<center><a href=\"Macellazioni.do?command=NuovoCapo&cdSedutaMacellazione=" + seduta + "&mavamData=" + mavamData + "&vpmData=" + vpmData + "&id=" + id + "&orgId=" + id_mac + "&clona=si \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Clona capo\" /></a></center>";
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
										toReturn += "<center><a href=\"Macellazioni.do?command=RichiestaIstopatologico&id=" + id + "&orgId=" + id_mac + " \"><img src=\"images/icons/clone.png\" height=\"20px\" width=\"20px\" title=\"Richiedi istopatologico\" /></a></center>";
									
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
			else if(sessioneMacellazioneString != null && (sessioneMacellazioneString.equals("-1") || sessioneMacellazioneString.split("-").length<=1))
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
			try {orgId=Integer.parseInt(orgIdString);} catch(Exception e){};
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
			if (orgId>0 && numeroSeduta>0 && data!=null)
				sessionePregressa = MacelliUtil.isSessionePregressa(orgId, data, numeroSeduta, db);
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
				
				capoClonato.setCd_info_azienda_provenienza(c.getCd_info_azienda_provenienza());
				capoClonato.setCd_specie(c.getCd_specie());
				capoClonato.setCd_categoria_bovina(c.getCd_categoria_bovina());
				capoClonato.setCd_categoria_bufalina(c.getCd_categoria_bufalina());
				
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
			
			String orgIdString = context.getRequest().getParameter( "orgId" );
			int orgId = -1;
			Timestamp data = null;
			int numeroSeduta = -1;
			try {orgId=Integer.parseInt(orgIdString);} catch(Exception e){};
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
			if (orgId>0 && numeroSeduta>0 && data!=null)
				sessionePregressa = MacelliUtil.isSessionePregressa(orgId, data, numeroSeduta, db);
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
			Organization org = new Organization( db, c.getId_macello() );
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
				CapoAjax  ca = ac.isCapoEsistente(c.getCd_matricola());
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
			context.getRequest().setAttribute("OrgId", c.getId_macello());
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
			capoLog.setSesso(c.isCd_maschio() ? "M" : "F");
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


	public String executeCommandPrintTBCRilevazioneMacelli(ActionContext context) {
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


	public String executeCommandPrintDisinfezioneMezziTrasporto(ActionContext context) {
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
				CapoAjax  ca = ac.isCapoEsistente(c.getCd_matricola());
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
			capoLog.setSesso(c_old.isCd_maschio() ? "M" : "F");
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
			capoLog.setSesso(c_old.isCd_maschio() ? "M" : "F");
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
		c.setCd_maschio					( booleano	( "cd_maschio", context ) );
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
		Organization org = null;

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

			String temporgId = context.getRequest().getParameter("orgId");
			if(temporgId == null)
			{
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			
			
			if (temporgId == null){
				temporgId = (String) context.getRequest().getAttribute("OrgId");
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
		Organization org = null;

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

	private void createPDFIdatidosi(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
				"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
				"SERVIZIO VETERINARIO\n" +
				"...............................\n" +
				"Unita' Operativa Veterinaria del Distretto ...........\n" +
				"INDIRIZZO .....................................................\n" +
				"TELEFONO .......................... FAX ..........................\n" +
				"MAIL .............................................................", 
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		//			Paragraph p2 = new Paragraph("Prot. " + stampeModuli.getProgressivo() + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p2.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p2);

		Paragraph p3 = new Paragraph("Al Dipartimento di Prevenzione\nServizi Veterinari\ndell' A.S.L. (ASL di origine animale) ..........\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_RIGHT);
		document.add(p3);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
		cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 12.0F);

		cb.showTextAligned(Element.ALIGN_LEFT, "COMUNICAZIONE RISULTATI ISPEZIONE POST MORTEM\n",108F,491F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "(All. I Sez. II Capo I punto 2.a Reg. 854/04)\n",108F,476F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "IDATIDOSI - (O.M. 21/04/1964)",108F,461F, 0);
		cb.endText();

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p5 = new Paragraph("Si comunica che presso il macello " + macello.getName() + " " +
				"sito nel Comune di " + comuneMacello + " riconosciuto con " +
				"n. " + macello.getApprovalNumber() + " alla visita ispettiva post mortem i sottoelencati animali:\n" ,
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));

		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);
		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoCapiIdatidosi = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String razza = "";
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(Capo c : listaCapi){
			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(c.getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");



			ListItem li = new ListItem( "specie " + specie + " , razza " + razza + " con matr. " + c.getCd_matricola() + ", " +
					"proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc, 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiIdatidosi.add(li);

		}
		document.add(elencoCapiIdatidosi);

		document.add( Chunk.NEWLINE );
		Paragraph p7 = new Paragraph( (listaCapi.size() > 0 ? "macellati" : "macellato") + " in data " + dataMacellazione + (listaCapi.size() > 0 ? " sono risultati infestati" : " e' risultato infestato") + " da IDADITOSI.\n" +
				"Tanto si comunica per gli ulteriori accertamenti ed i seguiti di competenza.\n" +
				"La trasmissione del documento ha valore ufficiale non si provvedera' ad inviare lo stesso a mezzo posta (L.n. 412 art.6 del 30/12/1991 e succ. integ.)", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_RIGHT);
		document.add(p8);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}


	private void createPDFArrivoAlMacello(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC, TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti, String dataArrivoMacello, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");
		LookupList listaNC  = new LookupList( db, "m_lookup_motivi_comunicazioni_asl");
		LookupList listaPA  = new LookupList( db, "m_lookup_provvedimenti_casl");

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId())+"\nSERVIZIO VETERINARIO\n" +
				"...............................\n" +
				"Unita' Operativa Veterinaria del Distretto ...........\n" +
				"INDIRIZZO .....................................................\n" +
				"TELEFONO .......................... FAX ..........................\n" +
				"MAIL .............................................................", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("Al................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
		cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 12.0F);

		cb.showTextAligned(Element.ALIGN_LEFT, "Non conformita' rilevate all'arrivo degli animali al macello.\n",108F,527F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "(Macello "+macello.getName()+" con numero CE "+ macello.getApprovalNumber()+" nel comune di "+comuneMacello+")\n",108F,516F, 0);
		cb.endText();

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p5 = new Paragraph("L'anno "+dataArrivoMacello.substring(6,10)+" addi "+dataArrivoMacello.substring(0,2)+" del mese di "+getMeseFromData(dataArrivoMacello.substring(3,5))+", presso il macello "+macello.getName()+
				" sito nel Comune di "+comuneMacello+" riconosciuto con n. "+macello.getApprovalNumber()+" \n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph("e' stato accertato che i capi:",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_CENTER);
		document.add(p6);

		com.itextpdf.text.List elencoCapiArrivoAlMacello = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoCapiNC = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoCapiPA = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String mod4 = "";
		String dataMod4;
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";

		ArrayList<Casl_Non_Conformita_Rilevata> listNC = new ArrayList<Casl_Non_Conformita_Rilevata> ();
		ArrayList<ProvvedimentiCASL> listPA = new ArrayList<ProvvedimentiCASL> ();

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(Capo c : listaCapi){
			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				//dataMod4 =  new Date(c.getCd_data_mod4().getTime()).toString();
				dataMod4 = sdf.format(new Date(c.getCd_data_mod4().getTime()));
			}
			else {
				dataMod4 = "..........................." ;
			}	

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			ListItem li = new ListItem( "specie " + specie + " , con matr. " + c.getCd_matricola() + ", " +
					"proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc +" scortato/i dal relativo passaporto e dal" +
					" mod. 4 n. "+ mod4 +" datato "+ dataMod4,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiArrivoAlMacello.add(li);	

		}
		ListItem nc = null;
		ListItem pa = null;

		for (int key : hashCapiNC.keySet()){
			nc = new ListItem();
			listNC = hashCapiNC.get(key);
			for (int k =0; k< listNC.size(); k++){
				if(k == 0){
					nc.add(listaNC.getSelectedValue(listNC.get(k).getId_casl_non_conformita()));
				}
				else{
					nc.add(" - " + listaNC.getSelectedValue(listNC.get(k).getId_casl_non_conformita()));
				}
			}
			elencoCapiNC.add(nc);
		}



		document.add( Chunk.NEWLINE );

		for (int k : hashCapiProvvedimenti.keySet()){
			pa = new ListItem();
			listPA = hashCapiProvvedimenti.get(k);
			for (int n =0; n< listPA.size(); n++){
				if(n == 0){
					pa.add(listaPA.getSelectedValue(listPA.get(n).getId_provvedimento()));
				}
				else{
					pa.add(" - " + listaPA.getSelectedValue(listPA.get(n).getId_provvedimento()));
				}
			}
			elencoCapiPA.add(pa);
		}


		document.add(elencoCapiArrivoAlMacello);
		document.add( Chunk.NEWLINE );

		Paragraph p7 = new Paragraph("presentano le seguenti non conformita':\n ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add(elencoCapiNC);

		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph("Per tale/i non conformita' sono stati adottati i seguenti provvedimenti: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		document.add( Chunk.NEWLINE );

		document.add(elencoCapiPA);

		document.add( Chunk.NEWLINE );

		Paragraph p9 = new Paragraph("Tanto si comunica per gli ulteriori accertamenti ed i seguiti di competenza.\n" +
				"La trasmissione del documento ha valore ufficiale e non si provvedera' ad inviare lo stesso a mezzo posta (L. n.412 art.6 del 30/12/1991 e succ. integ.).", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p9);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p10 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}

	private void createPDFAnimaliInfettiDa(Connection db, Document document, ByteArrayOutputStream out, ArrayList<Capo> listaCapiBrucellosi, Organization macello, String dataMacellazione, String malattia, StampeModuli stampeModuli) throws Exception {

		LookupList listaAsl = new LookupList(db,"lookup_site_id");
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		//Gestione progressivo
		//int progressivoModulo = 0;
		//		stampeModuli.setMalattiaCapo(malattia);
		//		progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
		//		if(progressivoModulo == 0){
		//			stampeModuli.insert(db);
		//			progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
		//		}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId())+"\nSERVIZIO VETERINARIO ......... \n" +
				"DISTRETTO SANITARIO DI ........................\n" +
				".................................................\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("Al Dipartimento di Prevenzione\n" +
				"Servizio Veterinario\n" +
				"la ASL ..................\n" +
				"Fax: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		Paragraph p3 = new Paragraph("Alla Regione .................\n" +
				"Assessorato Sanita'\n" +
				"Servizio Veterinario\n" +
				"Fax: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_RIGHT);
		document.add(p3);

		Paragraph p4 = new Paragraph("Alla Regione CAMPANIA\n" +
				"Assessorato Sanita'\n" +
				"Servizio Veterinario (da inviare x email)\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_RIGHT);
		document.add(p4);

		Paragraph p5 = new Paragraph("Al Ministero della Salute\n" +
				"Direzione Generale della Sanita'\n" +
				"Animale e del Farmaco Veterinario\n" +
				"Ufficio II\n"+
				"Via Ribotta\n" +
				"ROMA (da inviare x email)\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_RIGHT);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		//		Paragraph p6 = new Paragraph("Prot. " + progressivoModulo + 
		//									 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//									 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//									 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//		p6.setAlignment(Element.ALIGN_LEFT);
		//		document.add(p6);			
		//		
		//		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: Certificazione del ricevimento degli animali infetti e dell'avvenuta macellazione",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		document.add( Chunk.NEWLINE );
		Paragraph p7 = new Paragraph("Si attesta che in data "+dataMacellazione+" i sottoelencati capi della specie:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoCapiInfettiBrucellosi = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoMatricole = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";
		String mod4 = "";
		String dataMod4 = "";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(Capo c : listaCapiBrucellosi){
			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");				
			//			speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//			cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//			comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//			loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				//dataMod4 =  new Date(c.getCd_data_mod4().getTime()).toString();
				dataMod4 = sdf.format(new Date(c.getCd_data_mod4().getTime()));
			}
			else {
				dataMod4 = "..........................." ;
			}

			ListItem li = new ListItem( "specie " + specie + " proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc + " scortato dal" +
					" mod. 4 n. "+ mod4 +" datato "+ dataMod4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);

			ListItem matricole = new ListItem("Matricola "+c.getCd_matricola());
			matricole.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiInfettiBrucellosi.add(li);
			elencoMatricole.add(matricole);
		}

		document.add(elencoCapiInfettiBrucellosi);

		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph(" infetti da "+malattia+" sono giunti in vincolo sanitario presso questo stabilimento di macellazione.\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		Paragraph p9 = new Paragraph("I suddetti animali sono stati sottoposti a macellazione in data "+dataMacellazione+"\n" +
				"Elenco degli animali abbattuti: \n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p9);

		document.add(elencoMatricole);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p10 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		stampeModuli.setMalattiaCapo(malattia);

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}





	private void createPDFAnimaliGravidi(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapiGravidi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");
		LookupList listaAsl = new LookupList(db,"lookup_site_id");

		//Gestione progressivo
		//int progressivoModulo = 0;
		//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			if(progressivoModulo == 0){
		//				stampeModuli.insert(db);
		//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE\nSERVIZIO VETERINARIO "+listaAsl.getValueFromId(macello.getSiteId())+"\n......................................", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("A: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		document.add( Chunk.NEWLINE );

		//			Paragraph p3 = new Paragraph("Prot. " + progressivoModulo + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p3.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p3);
		//			
		//			document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: Macellazione animali gravidi\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		document.add( Chunk.NEWLINE );

		Paragraph p4 = new Paragraph("L'anno "+dataMacellazione.substring(6,10)+" addi "+dataMacellazione.substring(0,2)+" del mese di "+getMeseFromData(dataMacellazione.substring(3,5))+", presso lo Stabilimento di Macellazione "+macello.getName()+" Riconoscimento CE "+macello.getAccountNumber()+"," +
				"il sottoscritto ....................... Veterinario Dirigente della ASL ..............................,\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p4);

		document.add( Chunk.NEWLINE );

		Paragraph p5 = new Paragraph("ha verificato che ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_CENTER);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoMatricole = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoDescrizioneAnimali = new com.itextpdf.text.List(true, 10);

		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";
		String mod4 = "";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(Capo c : listaCapiGravidi){
			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");

			ListItem li = new ListItem( " l'animale  proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc + " scortato dal relativo passaporto e " +
					" Mod. 4 n. "+ mod4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoDescrizioneAnimali.add(li);
			ListItem matricole = new ListItem(c.getCd_matricola());
			elencoMatricole.add(matricole);
		}

		document.add(elencoDescrizioneAnimali);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph("risultano in evidente stato di gravidanza i seguenti animali:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p6);

		document.add( Chunk.NEWLINE );

		document.add(elencoMatricole);

		document.add( Chunk.NEWLINE );

		/*Paragraph p7 = new Paragraph("[PARTE DINAMICA ELENCO ANIMALI GRAVIDI] ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p7.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p7);*/	

		Paragraph p8 = new Paragraph("Tanto si certifica per i doveri di competenza.\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p9 = new Paragraph("Data .......................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_LEFT);
		document.add(p9);

		Paragraph p10 = new Paragraph("IL VETERINARIO DIRIGENTE\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,680F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,665F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}

	private void createPDFVisitaAnteMortem (Connection db, Document doc, PdfWriter wr, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, TreeMap<Integer, ArrayList<NonConformita>> hashCapiNC, String dataArrivoMacello,Image logo, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaNC  = new LookupList( db, "m_lookup_tipi_non_conformita");
		LookupList listaPA  = new LookupList( db, "m_lookup_provvedimenti_vam");
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );

		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream output = null;
		Document document = null;
		PdfWriter writer = null;

		ArrayList<NonConformita> listNC = null;
		ListItem singolaNcItext = null;
		com.itextpdf.text.List elencoNcItext = null;

		String specie = "";
		String razza = "";
		String sesso = "";

		//int i = 0;
		for(Capo c : listaCapi){

			//				++i;
			//				if(i > 1){
			//					document.newPage();
			//					document.add(logo);
			//				}

			output = new ByteArrayOutputStream(); 
			document = new Document();
			writer = PdfWriter.getInstance(document, output);
			document.open();
			document.add(logo);

			
			String comucazione_to = "";
			
				if (c.isCasl_to_asl_origine())
				{
					comucazione_to  += "ASL ORIGINE \n" ;
				}
				if (c.isCasl_to_proprietario_animale())
				{
					comucazione_to += "PROPRIETARIO ANIMALE \n" ;
				}
				if (c.isCasl_to_azienda_origine())
				{
					comucazione_to += "AZIENDA ORIGINE \n" ;
				}
				if (c.isCasl_to_proprietario_macello())
				{
					comucazione_to += "PROPRIETARIO MACELLO \n" ;
				}
				if (c.isCasl_to_pif())
				{
					comucazione_to += "PIF \n" ;
				}
				if (c.isCasl_to_uvac())
				{
					comucazione_to += "UVAC \n" ;
				}
				if (c.isCasl_to_regione())
				{
					comucazione_to += "REGIONE \n" ;
				}
				if (c.isCasl_to_altro())
				{
					comucazione_to += "ALTRO : \n"+c.getCasl_to_altro_testo(); ;
				}
				if(comucazione_to.equals(""))
				{
					comucazione_to = "..................................." ; 
				}
			//c.getCasl_to_altro_testo;
			
				Paragraph pp = new Paragraph("\n\n\n\n\n\n\n\n\n\n\n", 
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

				pp.setAlignment(Element.ALIGN_CENTER);
				document.add(pp);
			Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
					"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
					"SERVIZIO VETERINARIO\n" +
					"...............................\n" +
					"Unita' Operativa Veterinaria del Distretto ...........\n" +
					"INDIRIZZO .....................................................\n" +
					"TELEFONO .......................... FAX ..........................\n" +
					"MAIL .............................................................", 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

//			Paragraph p1 = new Paragraph("Al Sig ................................. proprietario dell'animale .........\n" +
//					"Al Responsabile dello stabilimento ..............................\n" +
//					"All'ASL ............................\n" +
//					"Al PIF e UVAC ............................................\n" +
//					"..................................................\n" +
//					"..................................................\n" +
//					"..................................................\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			
			Paragraph p1 = new Paragraph("Comunicazione a "+comucazione_to ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			
		
			
			p1.setAlignment(Element.ALIGN_RIGHT);
			document.add(p1);

			document.add( Chunk.NEWLINE );

			//				Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
			//											 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
			//											 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
			//											 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			//				p2.setAlignment(Element.ALIGN_LEFT);
			//				document.add(p2);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			Paragraph p_bold = new Paragraph("OGGETTO: Evidenze in fase di visita ante mortem presso lo stabilimento "+macello.getName()+ " con numero CE "+macello.getApprovalNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
			p_bold.setAlignment(Element.ALIGN_LEFT);
			document.add(p_bold);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(c.getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");
			sesso = c.isCd_maschio() ? "M" : "F";

			Paragraph p3 = new Paragraph("Si comunica che in data "+dataArrivoMacello+" il Servizio Veterinario operante presso lo stabilimento in oggetto ha effettuato la visita ante mortem sull'animale di seguito segnalato:\n\n" +
					"specie " + specie + " , razza " + razza + " , sesso " + sesso + " con matr. " + c.getCd_matricola() + "\n\n" + 
					"Dal controllo effettuato e' stata rilevata la/le seguente/i evidenza/e:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p3.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p3);		


			listNC = hashCapiNC.get(c.getId());
			elencoNcItext = new com.itextpdf.text.List(true, 10);

			if(listNC.size() > 0){ //se ci sono non conformita' allora le aggiungiamo al documento
				for (int k = 0; k < listNC.size(); k++){
					singolaNcItext = new ListItem();
					singolaNcItext.add(listaNC.getSelectedValue(listNC.get(k).getId_tipo()));
					elencoNcItext.add(singolaNcItext);
				}
			}
			else{ //altrimenti metto 3 righe vuote nel documento per permettere di scrivere a mano le non conformita'
				for (int k = 0; k < 3; k++){
					singolaNcItext = new ListItem(".........................................");
					elencoNcItext.add(singolaNcItext);
				}
			}

			document.add(elencoNcItext);

			document.add( Chunk.NEWLINE );

			Paragraph p4 = new Paragraph("Per la predetta evidenza e' stato adottato il seguente provvedimento:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p4);

			document.add( Chunk.NEWLINE );

			String lista_PA = listaPA.getSelectedValue(c.getVam_provvedimenti());
			if( lista_PA != null && !lista_PA.equals("")){
				document.add(new Paragraph(lista_PA));
			}
			else {
				document.add(new Paragraph("......................................................."));
			}


			document.add( Chunk.NEWLINE );

			/*INSERIRE LE NOTE!*/
			String note = "";
			note = (c.getVam_provvedimenti_note() != null && !c.getVam_provvedimenti_note().equals("") ? note : "..............................................");;
			document.add(new Paragraph("NOTE: "+note));


			document.add( Chunk.NEWLINE );

			Paragraph p5 = new Paragraph("Data..................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p5.setAlignment(Element.ALIGN_LEFT);
			document.add(p5);

			Paragraph p6 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p6.setAlignment(Element.ALIGN_RIGHT);
			document.add(p6);

			document.close();

			stampeModuli.setMatricolaCapo(c.getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
			Document documentNew = new Document();
			PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			PdfContentByte cbNew = writerNew.getDirectContent();

			// Load existing PDF
			PdfReader readerNew = new PdfReader(output.toByteArray());

			PdfImportedPage page = null;
			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cbNew.addTemplate(page, 0, 0);
				if(j == 1){
					cbNew.beginText();
					cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cbNew.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
					}
					cbNew.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}

		this.mergePDF(outputList, out);

	}

	private void createPDFMorteAnteMacellazione (Connection db, Document doc, PdfWriter wr, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, String dataArrivoMacello, Image logo, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaLuoghiVerifica = new LookupList(db, "m_lookup_luoghi_verifica");

		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream output = null;
		Document document = null;
		PdfWriter writer = null;

		String specie = "";
		String razza = "";
		String sesso = "";
		String luogo = "";

		//int i = 0;
		//int progressivoModulo = 0;
		for(Capo c : listaCapi){

			//Gestione progressivo
			//				stampeModuli.setMatricolaCapo(c.getCd_matricola());
			//				progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
			//				if(progressivoModulo == 0){
			//					stampeModuli.insert(db);
			//					progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
			//				}

			//				++i;
			//				if(i > 1){
			//					document.newPage();
			//					document.add(logo);
			//				}

			output = new ByteArrayOutputStream(); 
			document = new Document();
			writer = PdfWriter.getInstance(document, output);
			document.open();
			document.add(logo);

			Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
					"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
					"SERVIZIO VETERINARIO\n" +
					"...............................\n" +
					"Unita' Operativa Veterinaria del Distretto ...........\n" +
					"INDIRIZZO .....................................................\n" +
					"TELEFONO .......................... FAX ..........................\n" +
					"MAIL .............................................................", 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			Paragraph p1 = new Paragraph("Allo stabilimento della ditta ......................................\n" +
					"Al Sig ................................ proprietario dell'animale .........\n" +
					"Al Responsabile dello stabilimento ..............................\n" +
					"All'ASL ............................\n" +
					"Al PIF e UVAC ....................................\n" +
					"..................................................\n" +
					"..................................................\n" +
					"..................................................\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p1.setAlignment(Element.ALIGN_RIGHT);
			document.add(p1);

			document.add( Chunk.NEWLINE );

			//				Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
			//											 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
			//											 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
			//											 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			//				p2.setAlignment(Element.ALIGN_LEFT);
			//				document.add(p2);
			//				
			//				document.add( Chunk.NEWLINE );
			//				document.add( Chunk.NEWLINE );

			Paragraph p_bold = new Paragraph("OGGETTO: Rilevazione di animale morto in fase antecedente la macellazione (STABILIMENTO "+macello.getName()+" con numero CE "+macello.getApprovalNumber()+")\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
			p_bold.setAlignment(Element.ALIGN_LEFT);
			document.add(p_bold);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );


			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(c.getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");
			sesso = c.isCd_maschio() ? "M" : "F";
			luogo = listaLuoghiVerifica.getSelectedValue(c.getMavam_luogo());
			if(c.getMavam_luogo() == 3){
				String desc_luogo = c.getMavam_descrizione_luogo_verifica();
				desc_luogo = (desc_luogo != null && !desc_luogo.equals("") ? desc_luogo : "...........................");
				luogo = luogo + ": " + desc_luogo;
			}

			Paragraph p3 = new Paragraph("Si comunica che in data "+dataArrivoMacello+" il Servizio Veterinario operante presso lo stabilimento in oggetto da controlli effettuati ha rilevato nel luogo \"" +
					luogo + "\"" +
					" che l'animale di seguito descritto e' morto antecedentemente alla macellazione:\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p3.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p3);

			document.add( Chunk.NEWLINE );

			Paragraph p7 = new Paragraph("specie " + specie + " , razza " + razza + " , sesso " + sesso + " con matr. " + c.getCd_matricola(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p7.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p7);

			document.add( Chunk.NEWLINE );

			String note = c.getMavam_note();
			note = (note != null && !note.equals("") ? note : "....................................\n....................................\n....................................\n");

			Paragraph p4 = new Paragraph("L'animale sopra indicato e' inviato allo stabilimento in indirizzo per la distruzione.\n" +
					"[NOTE]\n" + note,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p4);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			Paragraph p5 = new Paragraph("Data..................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p5.setAlignment(Element.ALIGN_LEFT);
			document.add(p5);

			Paragraph p6 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p6.setAlignment(Element.ALIGN_RIGHT);
			document.add(p6);

			document.close();

			stampeModuli.setMatricolaCapo(c.getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
			Document documentNew = new Document();
			PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			PdfContentByte cbNew = writerNew.getDirectContent();

			// Load existing PDF
			PdfReader readerNew = new PdfReader(output.toByteArray());

			PdfImportedPage page = null;
			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cbNew.addTemplate(page, 0, 0);
				if(j == 1){
					cbNew.beginText();
					cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cbNew.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
					}
					cbNew.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}

		this.mergePDF(outputList, out);

	}

	private void createPDFTrasportoAnimaliInfetti (Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, String dataArrivoMacello, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );

		//Gestione progressivo
		//int progressivoModulo = 0;
		//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			if(progressivoModulo == 0){
		//				stampeModuli.insert(db);
		//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
				"AZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
				"SERVIZIO VETERINARIO ............... \n" +
				"DISTRETTO SANITARIO DI ................\n" +
				"...................................................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		//			Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p2.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p2);
		//			
		//			document.add( Chunk.NEWLINE );
		//			document.add( Chunk.NEWLINE );

		/*[PARAGRAFO PER INDICARE IL MEZZO DI TRASPORTO NEL CONTROLLO DOCUMENTALE]*/

		Paragraph p_bold = new Paragraph("VERBALE DI DISINFEZIONE NEI CASI DI TRASPORTO\n DI ANIMALI INFETTI\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_CENTER);
		document.add(p_bold);


		document.add( Chunk.NEWLINE );

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p3 = new Paragraph("IL SOTTOSCRITTO DOTT. ................................., VETERINARIO DIRIGENTE DELLA A.S.L. ......................... " +
				"IN SERVIZIO c/o L'IMPIANTO DI MACELLAZIONE "+macello.getName()+" SITO NEL COMUNE DI "+comuneMacello+" RICONOSCIUTO CON n. "+macello.getApprovalNumber()+",\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p3);

		document.add( Chunk.NEWLINE );

		Paragraph p4 = new Paragraph("DICHIARA" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_CENTER);
		document.add(p4);

		document.add( Chunk.NEWLINE );

		String tipo = "";
		String targa = "";
		String specie = "";
		String provenienza = "...........................";

		com.itextpdf.text.List elencoCapi = new com.itextpdf.text.List(true, 10);

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;

		for(Capo c : listaCapi){

			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					provenienza = speditoreOrg.getName();
				}
			}

			//provenienza = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "...........................");  
			tipo = ( c.getCd_tipo_mezzo_trasporto() != null && !c.getCd_tipo_mezzo_trasporto().equals("") ? c.getCd_tipo_mezzo_trasporto() : "...........................") ;
			targa = ( c.getCd_targa_mezzo_trasporto() != null && !c.getCd_targa_mezzo_trasporto().equals("") ? c.getCd_targa_mezzo_trasporto() : "...........................") ;
			ListItem li = new ListItem ("L'AUTOMEZZO "+ tipo +" TARGATO "+ targa +" SUL QUALE E' STATO TRASPORTATO L'ANIMALE DELLA SPECIE " +specie+" CON MATRICOLA "+ c.getCd_matricola()+" PROVENIENTE DA AZIENDA INFETTA "+provenienza+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapi.add(li);
		}

		Paragraph p5 = new Paragraph("CHE IN DATA "+ dataArrivoMacello , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		document.add(elencoCapi);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph(" SONO STATI LAVATI E DISINFETTATI COME DA NORMATIVA VIGENTE.\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p6);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p7 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_RIGHT);
		document.add(p7);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataArrivoMacello)),37F,660F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,645F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());


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



	private void mergePDF(ArrayList<ByteArrayOutputStream> outputList, ByteArrayOutputStream out){
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


	private void createPDF1033TBC(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, ArrayList<Capo> listaCapi_1033_tbc, TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaOrgani	= new LookupList( db, "m_lookup_organi" );
		LookupList listaStadi  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : "") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  "" );


		int i;
		for(Capo capo_1033_tbc : listaCapi_1033_tbc){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_1033.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			String specie = listaSpecie.getValueFromId(capo_1033_tbc.getCd_specie());
			String razza = listaRazze.getValueFromId(capo_1033_tbc.getCd_id_razza());

			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields();
			form.setField("ragione_sociale", macello.getName());
			form.setField("asl",listaAsl.getSelectedValue(macello.getSiteId()));
			form.setField("specie",specie);
			form.setField("razza",razza);
			form.setField("matricola",capo_1033_tbc.getCd_matricola());
			form.setField("comune", comuneMacello);
			form.setField("data_macellazione",dataMacellazione);
			form.setField("note",capo_1033_tbc.getCd_note());
			form.setField("veterinario_1",capo_1033_tbc.getCd_veterinario_1());
			form.setField("veterinario_2",capo_1033_tbc.getCd_veterinario_2());
			form.setField("veterinario_3",capo_1033_tbc.getCd_veterinario_3());
			if(capo_1033_tbc.getCd_data_nascita() != null){
				if(capo_1033_tbc.getVpm_data().getTime() - capo_1033_tbc.getCd_data_nascita().getTime() <= 2*365*24*60*60*1000L ){
					form.setField("eta_inf", "Yes");
				}
				else{
					form.setField("eta_sup", "Yes");
				}
			}
			if(capo_1033_tbc.isCd_maschio()){
				form.setField("sesso_m","Yes");
			}
			else {
				form.setField("sesso_f","Yes");
			}

			String organi = "";
			String stadi = "";
			i = 0;
			for(Organi o : hashCapiOrgani.get(capo_1033_tbc.getId())){
				i++;
				if(i == 1){
					organi = organi + "1." + listaOrgani.getSelectedValue(o.getLcso_organo());
					stadi = stadi + "1." + listaStadi.getSelectedValue(o.getLcso_patologia());
				}
				else{
					organi = organi + " - " + i + "." + listaOrgani.getSelectedValue(o.getLcso_organo());
					stadi = stadi + " - " + i + "." + listaStadi.getSelectedValue(o.getLcso_patologia());
				}

			}

			form.setField("organi", organi);
			form.setField("stadi", stadi);
			stamper_1033.setFormFlattening(true);
			stamper_1033.close();


			//Set info aggiuntive per StampeModuli
			stampeModuli.setMatricolaCapo(capo_1033_tbc.getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,600F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,587F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);		

		}

		mergePDF(outputList, out);

	}


	private void createPDFRilevazioneTBC(ActionContext context, Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		//lookup
		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaCategorieBovine	= new LookupList( db, "m_lookup_specie_categorie_bovine" );
		LookupList listaCategorieBufaline	= new LookupList( db, "m_lookup_specie_categorie_bufaline" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni"); 
		LookupList listaPatologieOrgani  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		AcroFields form = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		OrganizationAddress speditoreAddress = null;
		HashMap<String, Integer> hashCategoriaNumeroVisitati = null;
		HashMap<String, Integer> hashCategoriaNumeroInfetti = null;
		TreeMap<Integer, ArrayList<Organi>> hashCapoOrganiTBC =  null; 
		TreeMap<Integer, ArrayList<Campione>> hashCapoCampioni =  null;
		ArrayList<Capo> listaCapi = null;


		//ciclo sugli speditori
		for(int idSpeditore : hashSpeditore.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_TBCRilevazioneMacello.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			//Gestione progressivo
			//			stampeModuli.setIdSpeditore(idSpeditore);
			//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			//			if(progressivoModulo == 0){
			//				stampeModuli.insert(db);
			//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			//			}

			speditore = hashSpeditore.get(idSpeditore);
			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}
			listaCapi = hashSpeditoreCapi.get(idSpeditore);
			hashCapoOrganiTBC = new TreeMap<Integer, ArrayList<Organi>>();
			hashCapoCampioni = new TreeMap<Integer, ArrayList<Campione>>();

			hashCategoriaNumeroVisitati = new HashMap<String, Integer>();
			hashCategoriaNumeroInfetti = new HashMap<String, Integer>();

			//Inizializzazione degli hash
			for(int codeCat : listaCategorieBovine.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_1_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_1_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", 0);

			for(int codeCat : listaCategorieBufaline.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_5_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_5_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", 0);

			//Valorizzazione degli hash
			int num = 0;
			for(Capo c : listaCapi){

				//se bovini
				if( c.getCd_specie() == 1 ){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_1_" + c.getCd_categoria_bovina())){
						num = hashCategoriaNumeroVisitati.get("NUM_1_" + c.getCd_categoria_bovina());
						hashCategoriaNumeroVisitati.put("NUM_1_" + c.getCd_categoria_bovina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_1_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 2){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_1_" + c.getCd_categoria_bovina())){
							num = hashCategoriaNumeroInfetti.get("NUM_1_" + c.getCd_categoria_bovina());
							hashCategoriaNumeroInfetti.put("NUM_1_" + c.getCd_categoria_bovina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_1_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", ++num);
						}
					}
				}
				//se bufalini
				else if(c.getCd_specie() == 5){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_5_" + c.getCd_categoria_bufalina())){
						num = hashCategoriaNumeroVisitati.get("NUM_5_" + c.getCd_categoria_bufalina());
						hashCategoriaNumeroVisitati.put("NUM_5_" + c.getCd_categoria_bufalina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_5_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 2){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_5_" + c.getCd_categoria_bufalina())){
							num = hashCategoriaNumeroInfetti.get("NUM_5_" + c.getCd_categoria_bufalina());
							hashCategoriaNumeroInfetti.put("NUM_5_" + c.getCd_categoria_bufalina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_5_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", ++num);
						}
					}
				}

				if(c.getCd_macellazione_differita() == 2){
					hashCapoOrganiTBC.put(c.getId(), Organi.loadOrganiTBC(c.getId(), db));
					hashCapoCampioni.put(c.getId(), Campione.loadCampioniTBC(c.getId(), db)) ;
				}

			}

			//Set dei campi
			form = stamper_1033.getAcroFields();

			//form.setField("progressivo_modulo", "PROGRESSIVO_DA_SETTARE" + "/" + listaAsl.getValueFromId(macello.getSiteId())+ "/" + sdfYear.format(sdf.parse(dataMacellazione)));
			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx), 
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){ 
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}

			}

			form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));
			//campi relativi al numero e tipo di animali esaminati
			for(String chiave : hashCategoriaNumeroVisitati.keySet()){
				form.setField(chiave, "" + hashCategoriaNumeroVisitati.get(chiave));
				form.setField(chiave + "_BIS", "" + hashCategoriaNumeroVisitati.get(chiave));
			}

			//campi relativi al numero e tipo di animali infetti
			for(String chiave : hashCategoriaNumeroInfetti.keySet()){
				form.setField(chiave + "_INFETTI", "" + hashCategoriaNumeroInfetti.get(chiave));
			}

			//campi relativi agli organi sequestrati e agli organi prelevati(campioni)
			int i = 0;
			for(Capo c : listaCapi){
				//solo i capi infetti
				if(c.getCd_macellazione_differita() == 2){
					i++;
					form.setField("MATRICOLA_" + i, c.getCd_matricola());
					if(c.getCd_data_nascita() != null){
						form.setField("DATA_NASCITA_" + i, sdf.format(c.getCd_data_nascita() ));
					}
					for(Organi o : hashCapoOrganiTBC.get(c.getId())){
						form.setField("ORGANO_" + o.getLcso_organo() + "_" + i , "Yes");
					}
					for(Campione cmp : hashCapoCampioni.get(c.getId())){
						form.setField("CAMPIONE_" + cmp.getMatrice() + "_" + i , "Yes");
					}
				}
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setIdSpeditore(idSpeditore);

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,740F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,727F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}
		//fine ciclo sugli speditori

		mergePDF(outputList, out);

	}


	private void createPDFRilevazioneBRC(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		//lookup
		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaCategorieBovine	= new LookupList( db, "m_lookup_specie_categorie_bovine" );
		LookupList listaCategorieBufaline	= new LookupList( db, "m_lookup_specie_categorie_bufaline" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni"); 
		LookupList listaPatologieOrgani  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		HashMap<String, Integer> hashCategoriaNumeroVisitati = null;
		HashMap<String, Integer> hashCategoriaNumeroInfetti = null;
		TreeMap<Integer, ArrayList<Organi>> hashCapoOrganiBRC =  null; 
		TreeMap<Integer, ArrayList<Campione>> hashCapoCampioni =  null;

		//ciclo sugli speditori
		for(int idSpeditore : hashSpeditore.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_BRCRilevazioneMacello.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			speditore = hashSpeditore.get(idSpeditore);
			OrganizationAddress speditoreAddress = null;
			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}
			ArrayList<Capo> listaCapi = hashSpeditoreCapi.get(idSpeditore);
			hashCapoOrganiBRC = new TreeMap<Integer, ArrayList<Organi>>();
			hashCapoCampioni = new TreeMap<Integer, ArrayList<Campione>>();

			hashCategoriaNumeroVisitati = new HashMap<String, Integer>();
			hashCategoriaNumeroInfetti = new HashMap<String, Integer>();

			//Inizializzazione degli hash
			for(int codeCat : listaCategorieBovine.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_1_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_1_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", 0);

			for(int codeCat : listaCategorieBufaline.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_5_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_5_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", 0);

			//Valorizzazione degli hash
			int num = 0;
			for(Capo c : listaCapi){

				//se bovini
				if( c.getCd_specie() == 1 ){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_1_" + c.getCd_categoria_bovina())){
						num = hashCategoriaNumeroVisitati.get("NUM_1_" + c.getCd_categoria_bovina());
						hashCategoriaNumeroVisitati.put("NUM_1_" + c.getCd_categoria_bovina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_1_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 1){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_1_" + c.getCd_categoria_bovina())){
							num = hashCategoriaNumeroInfetti.get("NUM_1_" + c.getCd_categoria_bovina());
							hashCategoriaNumeroInfetti.put("NUM_1_" + c.getCd_categoria_bovina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_1_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", ++num);
						}
					}
				}
				//se bufalini
				else if(c.getCd_specie() == 5){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_5_" + c.getCd_categoria_bufalina())){
						num = hashCategoriaNumeroVisitati.get("NUM_5_" + c.getCd_categoria_bufalina());
						hashCategoriaNumeroVisitati.put("NUM_5_" + c.getCd_categoria_bufalina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_5_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 1){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_5_" + c.getCd_categoria_bufalina())){
							num = hashCategoriaNumeroInfetti.get("NUM_5_" + c.getCd_categoria_bufalina());
							hashCategoriaNumeroInfetti.put("NUM_5_" + c.getCd_categoria_bufalina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_5_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", ++num);
						}
					}
				}

				if(c.getCd_macellazione_differita() == 1){
					hashCapoOrganiBRC.put(c.getId(), Organi.loadOrganiBRC(c.getId(), db));
					hashCapoCampioni.put(c.getId(), Campione.loadCampioniBRC(c.getId(), db)) ;
				}

			}

			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields();

			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx), 
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){ 
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}

			}

			form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));

			//campi relativi al numero e tipo di animali esaminati
			for(String chiave : hashCategoriaNumeroVisitati.keySet()){
				form.setField(chiave, "" + hashCategoriaNumeroVisitati.get(chiave));
			}

			//campi relativi al numero e tipo di animali infetti
			for(String chiave : hashCategoriaNumeroInfetti.keySet()){
				form.setField(chiave + "_INFETTI", "" + hashCategoriaNumeroInfetti.get(chiave));
			}

			//campi relativi agli organi sequestrati e agli organi prelevati(campioni)
			int i = 0;
			for(Capo c : listaCapi){
				//solo i capi infetti
				if(c.getCd_macellazione_differita() == 1){
					i++;
					form.setField("MATRICOLA_" + i, c.getCd_matricola());
					if(c.getCd_data_nascita() != null){
						form.setField("DATA_NASCITA_" + i, sdf.format(c.getCd_data_nascita() ));
					}
					form.setField("CAT_" + c.getCd_specie() + "_" + c.getCd_categoria_bovina() + "_" + i, "Yes");
					form.setField("CAT_" + c.getCd_specie() + "_" + c.getCd_categoria_bufalina() + "_" + i, "Yes");
					for(Organi o : hashCapoOrganiBRC.get(c.getId())){
						form.setField("ORGANO_" + o.getLcso_organo() + "_" + i , "Yes");
					}
					for(Campione cmp : hashCapoCampioni.get(c.getId())){
						form.setField("CAMPIONE_" + cmp.getMatrice() + "_" + i , "Yes");
					}
				}
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setIdSpeditore(idSpeditore);

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,740F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,727F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);
		}
		//fine ciclo sugli speditori

		mergePDF(outputList, out);

	}


	private void createPDFAnimaliLEB(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, Capo> hashCapo, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		Capo capo = null;
		for(int idCapo : hashCapo.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_LEB.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			capo = hashCapo.get(idCapo);
			speditore = hashSpeditore.get(idCapo);

			OrganizationAddress speditoreAddress = null;
			if(speditore !=null && speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}


			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields(); 
			if (speditore!=null){
			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());
			}

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx),
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}      
			}

			if (speditore!=null)
				form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));

			String razza = listaRazze.getValueFromId(capo.getCd_id_razza());
			String comuneMacello = macello.getCity();
			String provinciaMacello = macello.getState();
			if (speditore!=null)
				form.setField("asl_prov",listaAsl.getSelectedValue(speditore.getSiteId()));
			form.setField("ragione_sociale", macello.getName()+ " C.E: "+macello.getApprovalNumber());
			form.setField("comune_macello", comuneMacello);
			form.setField("prov_macello",provinciaMacello);
			form.setField("matricola",capo.getCd_matricola());

			if(capo.getCd_data_nascita() != null){
				String dataNascita = sdf.format(new Date(capo.getCd_data_nascita().getTime()));
				form.setField("data_nascita",dataNascita);
			}

			if(capo.getCd_specie() == 1){
				form.setField("bovina","Yes");
				form.setField("razza",razza);
			}
			else if(capo.getCd_specie() == 5) {
				form.setField("bufalina","Yes");
				form.setField("razza","N.D");
			}

			if(capo.isCd_maschio()){
				form.setField("sesso_m","Yes");
			}
			else {
				form.setField("sesso_f","Yes");
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setMatricolaCapo(capo.getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,700F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,687F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);	

		}

		mergePDF(outputList, out);

	}

	private void gestioneProgressivoModulo(Connection db, StampeModuli stampeModuli, ChiaveModuliMacelli chiave, ByteArrayOutputStream out) throws Exception{

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

	private void getConfigTipo(ActionContext context)
	{
		if(context.getSession().getAttribute("configTipo")!=null)
			configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
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
			Organization org = new Organization( db, c.getId_macello() );

			
			ArrayList<Organi>   			organiTumorali	 		= Capo.checkOrganiTumorali(db, c.getId());

			context.getRequest().setAttribute( "Capo", c );
			context.getRequest().setAttribute( "OrgDetails", org );
			context.getRequest().setAttribute("orgId",c.getId_macello()+"" );

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


		context.getRequest().setAttribute("orgId", capo.getId_macello()+"");
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
		Organization org = null;
		
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
			}
			
			
			istopatologico.caricaEsitoIstopatologico(context);
			
			istopatologico.update(db);
			
			
			//AGGIORNAMENTO REGISTRO TUMORI
			if (istopatologico.isFlagEsitoTumorale())
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
			istopatologico = RichiestaIstopatologico.load(idIstopatologicoInt, db);
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


public String executeCommandToLiberoConsumoMassivo(ActionContext context){
	
	if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-liberoconsumomassivo-add"))
	{
		return ("PermissionError");
	}
	
	int orgId = -1;
	int tipoInt = -1;
	
	String orgIdString = context.getParameter("orgId");
	String tipoString = context.getParameter("tipo"); 	
	
	if (orgIdString==null)
		orgIdString = (String) context.getRequest().getAttribute("orgId");
	if (tipoString==null)
		tipoString = (String) context.getRequest().getAttribute("tipo");
	
	try {tipoInt= Integer.parseInt(tipoString);} catch (Exception e){}
	try {orgId= Integer.parseInt(orgIdString);} catch (Exception e){}
	
	
	if (tipoInt>-1){
		final int tipo = tipoInt;
		context.getSession().setAttribute("configTipo", new ConfigTipo(tipo));
	}
	
	String			ret		= "toLiberoConsumoMassivoOK";
	ArrayList<Capo> capi = new ArrayList<Capo>();

	Connection		db		= null;
	Organization org = null;

	try
	{
		db = this.getConnection( context );
		org = new Organization( db, orgId);
		capi = Capo.loadByStabilimentoCapiNonMacellati( orgIdString, db );
			
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
	
	int orgId = Integer.parseInt(context.getRequest().getParameter("id_macello"));
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
	context.getRequest().setAttribute("orgId", String.valueOf(orgId));
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

}
