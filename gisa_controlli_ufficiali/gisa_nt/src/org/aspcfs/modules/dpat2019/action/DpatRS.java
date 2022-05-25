/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.action;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.dpat2019.base.DpatAnagraficaNominativo;
import org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentali;
import org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentaliStruttura;
import org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentaliStruttureList;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloStruttureList;
import org.aspcfs.modules.dpat2019.base.DpatStruttura;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneDocumentiDPAT;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class DpatRS extends CFSModule {

  

	public String executeCommandAddModify(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		DpatRisorseStrumentali dpatRS =  null ;
		int annoCorrente =  -1 ;
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
			
			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			
			String area = context.getParameter("combo_area");
			int idArea = -1 ;
			
			
			if (area!=null && !"".equals(area))
			{
				try
				{
					idArea = Integer.parseInt(area);
				}
				catch(Exception e)
				{
					
				}
			}
			
			dpatRS = new DpatRisorseStrumentali(db,idAsl,annoCorrente,idArea);
			if (dpatRS.getId()<=0)
			{ 	
				dpatRS.setIdAsl(idAsl);
				dpatRS.setAnno(annoCorrente);
				dpatRS.setEntered(current_date);
				dpatRS.setModified(current_date);
				dpatRS.setEnteredby(utente.getUserId());
				dpatRS.setModifiedby(utente.getUserId());
				
				dpatRS.insertIstanzaDpatRS(db,context);
				dpatRS.setIdArea(idArea);
				
				dpatRS = new DpatRisorseStrumentali(db,idAsl,annoCorrente,idArea);
				if (dpatRS.getListaStrutture().size()==0)
				{
					dpatRS.insertDpatRS(db,context,idArea);
					dpatRS.getListaStrutture().setIdAreaSel(idArea);
					dpatRS.getListaStrutture().buildList(db, dpatRS.getId(), dpatRS.getIdAsl(), dpatRS.getAnno());
				}
				
			} 
			LookupList AttrezzatureCampionamenti = new LookupList(db,"lookup_attrezzature_campionamenti");
			AttrezzatureCampionamenti.addItem(-1, "Seleziona Voce") ;
			context.getRequest().setAttribute("AttrezzatureCampionamenti", AttrezzatureCampionamenti);
			context.getRequest().setAttribute("DpatRS", dpatRS);
			context.getRequest().setAttribute("edit", context.getParameter("edit"));

			
			int idSDC = -1;
			PreparedStatement pst2 = db.prepareStatement("select id from dpat_strumento_calcolo where id_asl="+dpatRS.getIdAsl()+" and anno="+dpatRS.getAnno());
			ResultSet rs2 = pst2.executeQuery();
			while (rs2.next()){
				idSDC = rs2.getInt(1);
			}
			context.getRequest().setAttribute("idSDC", idSDC);
			pst2.close();
			rs2.close();
			
			
			
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		context.getRequest().setAttribute("anno", annoCorrente+"");
		if (getUserSiteId(context)>0 && (dpatRS.isCompleto()==false && dpatRS.getStrutturaAmbito().getStato_all2() ==0)    && hasPermission(context, "dpat-edit") && dpatRS.getAnno()==annoCorrente){
			return ("DpatSDCAddModifyOK"); /*VISUALIZZAZIONE UTENTE ASL*/
		}
		else {
			return executeCommandDetail(context); /*VISUALIZZAZIONE UTENTE SENZA ASL*/
		}
		}
	
	
	public String executeCommandRiapriRisorseStrumentali(ActionContext context){
		
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		DpatRisorseStrumentali dpatRS =  null ;
		int annoCorrente =  -1 ;
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
			
			
			String area = context.getParameter("combo_area");
			int idArea = -1 ;
			
			
			if (area!=null && !"".equals(area))
			{
				try
				{
					idArea = Integer.parseInt(area);
				}
				catch(Exception e)
				{
					
				}
			}
			
			dpatRS = new DpatRisorseStrumentali(db,idAsl,annoCorrente,idArea);
			//Riapri
			
			if (idArea<=0)
			dpatRS.setCompleto(false);
			else
			{
				dpatRS.getStrutturaAmbito().setStato_all2(0);
			}
			
			LookupList AttrezzatureCampionamenti = new LookupList(db,"lookup_attrezzature_campionamenti");
			AttrezzatureCampionamenti.addItem(-1, "Seleziona Voce") ;
			context.getRequest().setAttribute("AttrezzatureCampionamenti", AttrezzatureCampionamenti);
			context.getRequest().setAttribute("DpatRS", dpatRS);
			context.getRequest().setAttribute("edit", context.getParameter("edit"));

			
			int idSDC = -1;
			PreparedStatement pst2 = db.prepareStatement("select id from dpat_strumento_calcolo where id_asl="+dpatRS.getIdAsl()+" and anno="+dpatRS.getAnno());
			ResultSet rs2 = pst2.executeQuery();
			while (rs2.next()){
				idSDC = rs2.getInt(1);
			}
			context.getRequest().setAttribute("idSDC", idSDC);
			pst2.close();
			rs2.close();
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		if (getUserSiteId(context)>0 && dpatRS.isCompleto()==false  && hasPermission(context, "dpat-edit") && dpatRS.getAnno()==annoCorrente){
			return ("DpatSDCAddModifyOK"); /*VISUALIZZAZIONE UTENTE ASL*/
		}
		else if(dpatRS.isCompleto()==false && hasPermission(context, "riapri-allegato2-view") ){
			return ("DpatSDCAddModifyOK"); /*VISUALIZZAZIONE UTENTE CHE PUO' RIAPRIRE IL FOGLIO*/
		}
		else {
			return executeCommandDetail(context); /*VISUALIZZAZIONE UTENTE SENZA ASL*/
		}
	}
	
	
	public String executeCommandDetail(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			int annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
			
			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			String area = context.getParameter("combo_area");
			int idArea = -1 ;
			
			
			if (area!=null && !"".equals(area))
			{
				try
				{
					idArea = Integer.parseInt(area);
				}
				catch(Exception e)
				{
					
				}
			}
			
			
			DpatRisorseStrumentali dpatRS = new DpatRisorseStrumentali(db,idAsl,annoCorrente,idArea);
			
			LookupList AttrezzatureCampionamenti = new LookupList(db,"lookup_attrezzature_campionamenti");
			AttrezzatureCampionamenti.addItem(-1, "Seleziona Voce") ;
			context.getRequest().setAttribute("AttrezzatureCampionamenti", AttrezzatureCampionamenti);

			context.getRequest().setAttribute("DpatRS", dpatRS);
			context.getRequest().setAttribute("edit", context.getParameter("edit"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		
		// SERVER DOCUMENTALE
		String layout = context.getRequest().getParameter("layout");
		if (layout!=null && layout.equals("style"))
			return ("DpatRSDetailStyleOK");
		//SERVER DOCUMENTALE	
		
		return ("DpatRSDetailOK");
	}
	
	
	public String executeCommandSalvaDefinitivo(ActionContext context){
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

		String id = (String)context.getRequest().getParameter("id");
		String idAsl = (String)context.getRequest().getParameter("idAsl");
		context.getRequest().setAttribute("idAsl", idAsl);
		Connection db = null;
		try { 
			db = this.getConnection(context);

			
			String area = context.getParameter("combo_area");
			int idArea = -1 ;
			
			
			if (area!=null && !"".equals(area))
			{
				try
				{
					idArea = Integer.parseInt(area);
				}
				catch(Exception e)
				{
					
				}
			}
			
			context.getRequest().setAttribute("comboArea", idArea);
			if (idArea<=0)
			{
			PreparedStatement pst = db.prepareStatement("update dpat_risorse_strumentali set completo=true, modified=now(), modifiedby="+utente.getUserId()+" where id="+id);
			pst.executeUpdate();
			}
			else
			{
				PreparedStatement pst = db.prepareStatement("update strutture_asl set stato_all2=2 where id="+idArea);
				pst.executeUpdate();
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}	
		return "DpatSDCAddNominativoOK";
	}
	
	
	
	
	
	
	
	
	public String executeCommandGeneraExcel(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			int annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
			
			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			String area = context.getParameter("combo_area");
			int idArea = -1 ;
			
			
			if (area!=null && !"".equals(area))
			{
				try
				{
					idArea = Integer.parseInt(area);
				}
				catch(Exception e)
				{
					
				}
			}
			
			
			DpatRisorseStrumentali dpatRS = new DpatRisorseStrumentali(db,idAsl,annoCorrente,idArea);
			
			LookupList AttrezzatureCampionamenti = new LookupList(db,"lookup_attrezzature_campionamenti");
			AttrezzatureCampionamenti.addItem(-1, "Seleziona Voce") ;
			 	
			
			
			 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("STRUTTURA DI APPARTENENZA");
			HSSFCell cel1 = row.createCell(1);
			cel1.setCellValue("N. AUTO DI SERVIZIO (le auto utilizzate da piu UU.OO. vanno conteggiate una sola volta ed assegnate ad una sola U.O.)");
			
			HSSFCell cel2 = row.createCell(2);
			cel2.setCellValue("ATTREZZATURE PER CAMPIONAMENTI (lasciare visibile solo la risposta voluta)");
			HSSFCell cel3 = row.createCell(3);
			cel3.setCellValue("N. PERSONAL COMPUTER SENZA ADSL");
			HSSFCell cel4 = row.createCell(4);
			cel4.setCellValue("N. PERSONAL COMPUTER CON ADSL");
			HSSFCell cel5 = row.createCell(5);
			cel5.setCellValue("N. NOTEBOOK NON CONNESSI AD INTERNET");
			HSSFCell cel6 = row.createCell(6);
			cel6.setCellValue("N. NOTEBOOK CONNESSI AD INTERNET");
			HSSFCell cel7 = row.createCell(7);
			cel7.setCellValue("N. STAMPANT");
			HSSFCell cel8 = row.createCell(8);
			cel8.setCellValue("QUANTITA' DI TELEFONI");
			HSSFCell cel9 = row.createCell(9);
			cel9.setCellValue("N. TERMOMETRI TARATI");
			
			HSSFCell cel10 = row.createCell(10);
			cel10.setCellValue("DESCRIZIONE");
			
			HSSFCell cel11 = row.createCell(11);
			cel11.setCellValue("QUANTITA");
			
			 	
			
			
			DpatRisorseStrumentaliStruttureList listStrutture =  dpatRS.getListaStrutture();
			
			int indice = 0 ;
			for (int i = 0 ; i < listStrutture.size(); i++)
			{
				
				indice = i+1 ;
				DpatRisorseStrumentaliStruttura struttura = (DpatRisorseStrumentaliStruttura)listStrutture.get(i);
				
				
				
					HSSFRow row_ = sheet.createRow(indice);
					HSSFCell cell_0 = row_.createCell(0);
					cell_0.setCellValue(struttura.getDescrizioneStruttura());
					HSSFCell cel_1 = row_.createCell(1);
					cel_1.setCellValue(struttura.getNumAuto());
					
					
					HSSFCell cel_2 = row_.createCell(2);
					cel_2.setCellValue(AttrezzatureCampionamenti.getSelectedValue(struttura.getIdAttrezzatureCampionamenti()) );
					HSSFCell cel_3 = row_.createCell(3);
					cel_3.setCellValue(struttura.getNumComputerSenzaAdsl() );
					HSSFCell cel_4 = row_.createCell(4);
					cel_4.setCellValue(struttura.getNumComputerConAdsl() );
					HSSFCell cel_5 = row_.createCell(5);
					cel_5.setCellValue(struttura.getNumNotebookNonConnessi() );
					HSSFCell cel_6 = row_.createCell(6);
					cel_6.setCellValue(struttura.getNumNotebookConnessi() );
					HSSFCell cel_7 = row_.createCell(7);
					cel_7.setCellValue(struttura.getNumStampanti() );
					HSSFCell cel_8 = row_.createCell(8);
					cel_8.setCellValue(struttura.getNumTelefoni()  );
					HSSFCell cel_9 = row_.createCell(9);
					cel_9.setCellValue(struttura.getNumTermometriTarati());
					
					HSSFCell cel_10 = row_.createCell(10);
					cel_10.setCellValue(struttura.getAltro_descrizione());
					
					HSSFCell cel_11 = row_.createCell(11);
					cel_11.setCellValue(struttura.getQuantitaAltro());
					
				
			}
			
			// write it as an excel attachment
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			context.getResponse().setContentType("application/vnd.ms-excel");
			context.getResponse().setContentLength(outArray.length);
			context.getResponse().setHeader("Expires:", "0"); // eliminates browser caching
			context.getResponse().setHeader("Content-Disposition", "attachment; filename=MODELLO_2_"+dpatRS.getStrutturaAmbito().getDescrizione_lunga()+".xls");
			java.io.OutputStream outStream = context.getResponse().getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			
			
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		
		
		
		return ("-none-");
	}



}
