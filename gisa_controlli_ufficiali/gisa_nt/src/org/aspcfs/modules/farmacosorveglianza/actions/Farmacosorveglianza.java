/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.farmacosorveglianza.actions;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.modules.farmacosorveglianza.base.Organization;
import org.aspcfs.modules.farmacosorveglianza.base.OrganizationList;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import java.sql.PreparedStatement;

import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.farmacosorveglianza.base.*;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.base.CustomFieldRecord;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.DependencyList;


import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Farmacosorveglianza extends CFSModule {

  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

	  if (context.getRequest().getParameter("comando")!= null)
	  {
		  try {
			return executeCommandAllegatoI(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
		  return executeCommandDashboardPre(context);
  }
  
  
  public String executeCommandDashboardScelta(ActionContext context) {

		// Controllo per i permessi dell'utente
	    if (!hasPermission(context, "farmacosorveglianza-home-view")) {
	   
	        return ("PermissionError");
	      
	    }
	    
	    return ("DashboardSceltaOK");
	  }
  
  
  public String executeCommandSelectAllegatoI(ActionContext context) throws SQLException
  {
	  
	  Connection db = null;
  
	  try
	  {
	   db = this.getConnection(context);
	  LookupList siteList = new LookupList(db, "lookup_site_id");
	  context.getRequest().setAttribute("Asl", siteList);
	  LookupList lanno = new LookupList(db, "lookup_anno");
	  context.getRequest().setAttribute("Anno", lanno);
	  
	  }
	  catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
    	  this.freeConnection(context, db);
	  }
	  
	  //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	  return "SelectAllegatoIOK";
  }
  
  public String executeCommandSelectAllegatoII(ActionContext context) throws SQLException
  {
	  
	  Connection db = null;
  
	  try
	  {
	   db = this.getConnection(context);
	  LookupList siteList = new LookupList(db, "lookup_site_id");
	  context.getRequest().setAttribute("Asl", siteList);
	  LookupList lanno = new LookupList(db, "lookup_anno");
	  context.getRequest().setAttribute("Anno", lanno);
	  }
	  catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
    	  this.freeConnection(context, db);
	  }
	  
	  //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	  return "SelectAllegatoIIOK";
  }
  
  
	
	public String executeCommandStampaAllegatoII(ActionContext context) 
	  {
		  
		  String anno = context.getRequest().getParameter("anno");
		  String asl = context.getRequest().getParameter("asl");
		  Connection db = null ;
		 
	     try
	     {
	    	 int row = 2 ;
	    	 	db = this.getConnection(context);
	    	 		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    			WritableWorkbook wb = Workbook.createWorkbook(bos);
		    		WritableSheet sheet = wb.createSheet("AllegatoII", 0);
		    		sheet.setColumnView(0, 90);
		    		sheet.setColumnView(2, 10);
		    		
		    		context.getRequest().setAttribute("idAsl", asl);
		    		context.getRequest().setAttribute("anno", anno);
		    		 HashMap<Integer, String> aslMap = new HashMap<Integer, String>();
		    	    	 LookupList siteList = new LookupList(db, "lookup_site_id");
		    	    	
		    	    	 String selectAsl = "select code , short_description from lookup_site_id where enabled = true ";
		    	      	 PreparedStatement pst2 = db.prepareStatement(selectAsl);
		    	      	
		    	      	 ResultSet rs2 = pst2.executeQuery();
		    	      	 while (rs2.next())
		    	      	 {
		    	      		 aslMap.put(rs2.getInt("code"), rs2.getString("short_description"));
		    	      	 }
		    	    	 
		    	      	context.getRequest().setAttribute("aslList", siteList);
		    	    	
		    	    	 context.getRequest().setAttribute("aslListMap", aslMap);
		    	      	  
		    	      	LookupList righeAllegato = new LookupList(db, "lookup_allegatoi_righe");
		    	    	  context.getRequest().setAttribute("righeAllegato", righeAllegato);
		    	    	 if (asl == null || "-1".equals(asl))
		    	    	 {
		    	    		 	String sql = "select  code as idasl, asl.description as descrasl,f.* from farmacosorveglianza_allegatoii f " +
		    					"right join lookup_site_id asl on (f.id_asl = asl.code and anno = "+anno+")" +
		    					" where code != 16 and asl.enabled=true  order by code";
		    			PreparedStatement pst = db.prepareStatement(sql);
		    			ResultSet rs = pst.executeQuery();
		    			
		    			HashMap<Integer,RigaAllegatoII> collezione = new HashMap<Integer, RigaAllegatoII>();
		    			 RigaAllegatoII riga = new RigaAllegatoII();
		    			while (rs.next())
		    			{
		    				 riga.setAnimali_reddito_art_4_5(riga.getAnimali_reddito_art_4_5()+ rs.getInt("animali_reddito_art_4_5"));
		    				 riga.setAnimali_reddito_art_11(riga.getAnimali_reddito_art_11()+ rs.getInt("animali_reddito_art_11"));
		    				 riga.setAnimali_reddito_tot_a(riga.getAnimali_reddito_tot_a() + rs.getInt("animali_reddito_tot_a"));
		    				 riga.setMangimi_medicati_prod_intermedi_art_3_4( riga.getMangimi_medicati_prod_intermedi_art_3_4() + rs.getInt("mangimi_medicati_prod_intermedi_art_3_4"));
		    				 riga.setMangimi_medicati_prod_intermedi_art_16(riga.getMangimi_medicati_prod_intermedi_art_16() + rs.getInt("mangimi_medicati_prod_intermedi_art_16"));
		    				 riga.setMangimi_medicati_prod_intermedi_tot_b( riga.getMangimi_medicati_prod_intermedi_tot_b() + rs.getInt("mangimi_medicati_prod_intermedi_tot_b"));
		    				 riga.setScorte_vet_art_84(riga.getScorte_vet_art_84() + rs.getInt("scorte_vet_art_84"));
		    				 riga.setScorte_vet_tot_c(riga.getScorte_vet_tot_c() + rs.getInt("scorte_vet_tot_c"));
		    				 riga.setScorte_allev_da_reddito(riga.getScorte_allev_da_reddito() +  rs.getInt("scorte_allev_da_reddito"));
		    				 riga.setScorte_allev_da_compagnia(riga.getScorte_allev_da_compagnia() + rs.getInt("scorte_allev_da_compagnia"));
		    				 riga.setScorte_allev_da_ippodromi(riga.getScorte_allev_da_ippodromi() + rs.getInt("scorte_allev_da_ippodromi"));
		    				 riga.setScorte_allev_tot_d(riga.getScorte_allev_tot_d() + rs.getInt("scorte_allev_tot_d")); 
		    				 riga.setBovina(riga.getBovina()+rs.getInt("bovina"));
		    				 riga.setBufalina(riga.getBufalina()+rs.getInt("bufalina"));
		    				 riga.setSuina(riga.getSuina()+rs.getInt("suina"));
		    				 riga.setAvicola(riga.getAvicola()+rs.getInt("avicola"));
		    				 riga.setOvicaprina(riga.getOvicaprina()+rs.getInt("ovicaprina"));
		    				 riga.setCunicola(riga.getCunicola()+rs.getInt("cunicola"));
		    				 riga.setEquina(riga.getEquina()+rs.getInt("equina"));
		    				 riga.setAcquicoltura(riga.getAcquicoltura()+rs.getInt("acquicoltura"));
		    				 
		    				 riga.setPrescrizioneAcquicoltura(riga.getPrescrizioneAcquicoltura()+rs.getInt("prescrizioneacquicoltura"));
	    	    			 riga.setPrescrizioneApiario(riga.getPrescrizioneApiario()+rs.getInt("prescrizioneapiario"));
	    	    			 riga.setPrescrizioneAvicola(riga.getPrescrizioneAvicola() + rs.getInt("prescrizioneavicola"));
	    	    			 riga.setPrescrizioneBovina(riga.getPrescrizioneBovina() + rs.getInt("prescrizionebovina"));
	    	    			 riga.setPrescrizioneBufalina(riga.getPrescrizioneBufalina() + rs.getInt("prescrizionebufalina"));
	    	    			 riga.setPrescrizioneCunicola(riga.getPrescrizioneCunicola() + rs.getInt("prescrizionecunicola"));
	    	    			 riga.setPrescrizioneEquina(riga.getPrescrizioneEquina() + rs.getInt("prescrizioneequina"));
	    	    			 riga.setPrescrizioneOvicaprina(riga.getPrescrizioneOvicaprina() + rs.getInt("prescrizioneovicaprina"));
	    	    			 riga.setPrescrizioneSuina(riga.getPrescrizioneSuina() + rs.getInt("prescrizionesuina"));
	    	    			 
	    	    			 riga.setMediaAcquicoltura(riga.getMediaAcquicoltura() + rs.getFloat("mediaacquicoltura"));
	    	    			 riga.setMediaApiario(riga.getMediaApiario() + rs.getFloat("mediaapiario"));
	    	    			 riga.setMediaAvicola(riga.getMediaAvicola() + rs.getFloat("mediaavicola"));
	    	    			 riga.setMediaBovina(riga.getMediaBovina() + rs.getFloat("mediabovina"));
	    	    			 riga.setMediaBufalina(riga.getMediaBufalina() + rs.getFloat("mediabufalina"));
	    	    			 riga.setMediaCunicola(riga.getMediaCunicola() + rs.getFloat("mediacunicola"));
	    	    			 riga.setMediaEquina(riga.getMediaEquina() + rs.getFloat("mediaequina"));
	    	    			 riga.setMediaOvicaprina(riga.getMediaOvicaprina() + rs.getFloat("mediaovicaprina"));
	    	    			 riga.setMediaSuina(riga.getMediaSuina() + rs.getFloat("mediasuina"));
		    				 
		    				 
		    				 
		    				 
		    				 
		    				 riga.setApiario(riga.getApiario()+rs.getInt("apiario"));
		    				 riga.setAnno(Integer.parseInt(anno));
		    				 riga.setIdAsl(rs.getInt("idasl"));
		    				 riga.setDescrizioneAsl(rs.getString("descrasl"));
		    				 
		    				 
		    				 
		    			
		    			}
		    			WritableFont headerFont1 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
		    			WritableCellFormat headerFormat1 = new WritableCellFormat(headerFont1);
			    		WritableFont headerFont2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			    		headerFont2.setUnderlineStyle(UnderlineStyle.SINGLE);
			    		WritableCellFormat headerFormat2 = new WritableCellFormat(headerFont2);
			    		WritableFont headerFont3 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			    		WritableCellFormat headerFormat3 = new WritableCellFormat(headerFont3);
			    		WritableFont headerFont4 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			    		WritableCellFormat headerFormat4 = new WritableCellFormat(headerFont4);
			    		headerFormat4.setBackground(Colour.LIGHT_ORANGE);
			    		sheet.addCell( new Label(0,3,"REGIONE CAMPANIA ANNO "+anno,headerFormat1));
			    		sheet.addCell( new Label(0,5,"TOTALE PRESCRIZIONI PERVENUTE:",headerFormat2) );
			    		
			    		sheet.addCell( new Label(0,6,"1)Per Animali da Reddito:",headerFormat2) );
			    		sheet.addCell( new Label(0,7,"di cui per uso in deroga:",headerFormat3) );
			    		sheet.addCell( new Label(0,8,"D.lgs 158/2006 (artt. 4 e 5):    ",headerFormat3) );
			    		sheet.addCell( new Label(0,9,"D.lgs 193/2006 (art. 11)",headerFormat3) );
			    		sheet.addCell( new Label(1,8,""+riga.getAnimali_reddito_art_4_5() ,headerFormat4) );
			    		sheet.addCell( new Label(1,9,""+riga.getAnimali_reddito_art_11(),headerFormat4) );
			    		sheet.addCell( new Label(2,6,"a) Totale: n. ",headerFormat3) );
			    		sheet.addCell( new Label(3,6,""+riga.getAnimali_reddito_tot_a(),headerFormat4) );
			    		
			    		sheet.addCell( new Label(0,10,"2)Per Manbgimi Medicati e Prodotti Intermedi",headerFormat2) );
			    		sheet.addCell( new Label(0,11,"di cui per uso in deroga:",headerFormat3) );
			    		sheet.addCell( new Label(0,12,"D.lgs 90/93 (art. 3 c. 4)",headerFormat3) );
			    		sheet.addCell( new Label(0,13,"D.M. 16/11/93 (art. 16c.1)  ",headerFormat3) );
			    		sheet.addCell( new Label(1,12,""+riga.getMangimi_medicati_prod_intermedi_art_3_4() ,headerFormat4) );
			    		sheet.addCell( new Label(1,13,""+riga.getMangimi_medicati_prod_intermedi_art_16(),headerFormat4) );
			    		sheet.addCell( new Label(2,10,"b) Totale: n. ",headerFormat3) );
			    		sheet.addCell( new Label(3,10,""+riga.getMangimi_medicati_prod_intermedi_tot_b(),headerFormat4) );
			    		
			    		
			    		sheet.addCell( new Label(0,14,"3)Per Scorte Proprie del Veterinario (ambulatori, cliniche e attivita' zooiatrica)",headerFormat2) );
			    		sheet.addCell( new Label(0,15,"di cui per scorte farmaci uso umano",headerFormat3) );
			    		sheet.addCell( new Label(0,16,"D.lgs 193/2006 (art. 84 comma 7) ",headerFormat3) );
			    		sheet.addCell( new Label(1,16,""+riga.getScorte_vet_art_84() ,headerFormat4) );
			    		sheet.addCell( new Label(2,14,"c) Totale: n. ",headerFormat3));
			    		sheet.addCell( new Label(3,14,""+riga.getScorte_vet_tot_c(),headerFormat4) );
			    		
			    		sheet.addCell( new Label(0,17,"4)Per Scorte di Impianto di allevamento e custodia di animali",headerFormat2) );
			    		sheet.addCell( new Label(0,18,"di cui",headerFormat3) );
			    		sheet.addCell( new Label(0,19,"da reddito",headerFormat3) );
			    		sheet.addCell( new Label(0,20,"da compagnia",headerFormat3) );
			    		sheet.addCell( new Label(0,21,"ippodromi, maneggi, scuderie",headerFormat3) );
			    		sheet.addCell( new Label(1,19,""+riga.getScorte_allev_da_reddito() ,headerFormat4) );
			    		sheet.addCell( new Label(1,20,""+riga.getScorte_allev_da_compagnia() ,headerFormat4));
			    		sheet.addCell( new Label(1,21,""+riga.getScorte_allev_da_ippodromi(),headerFormat4) );
			    		sheet.addCell( new Label(2,17,"d) Totale: n. ",headerFormat3));
			    		sheet.addCell( new Label(3,17,""+riga.getScorte_allev_tot_d(),headerFormat4) );
			    		
			    		sheet.addCell( new Label(0,22,"Totale generale (a+b+c+d)",headerFormat3));
			    		sheet.addCell( new Label(2,22,"N.",headerFormat3));
			    		sheet.addCell( new Label(3,22,""+(riga.getAnimali_reddito_tot_a() + riga.getMangimi_medicati_prod_intermedi_tot_b() +  riga.getScorte_vet_tot_c() + riga.getScorte_allev_tot_d()),headerFormat4) );
			    		
			    		WritableFont headerFont5 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			    		WritableCellFormat headerFormat5 = new WritableCellFormat(headerFont5);
			    		
			    		sheet.mergeCells(4, 2, 7, 2);
			    		sheet.addCell( new Label(5,3,"ALLEGATO II:",headerFormat5));
			    		sheet.mergeCells(4, 3, 7, 3);
			    		sheet.addCell( new Label(5,4,"SCHEDA PRESCRIZIONI FARMACO VETERINARIO",headerFormat5));
			    		
			    		sheet.mergeCells(5, 5, 6, 5);
			    		sheet.addCell( new Label(5,5,"INDICATORI DI FARMACOSORVEGLIANZA",headerFormat2));
			    		sheet.mergeCells(5, 6, 6,6);
			    		sheet.setColumnView(5, 28);
			    		sheet.addCell( new Label(5,6,"n. medio prescrizioni/anno per allevamento:",headerFormat5));
			    		
			    		sheet.addCell( new Label(5,7,"Bovina",headerFormat5));
			    		sheet.addCell( new Label(6,7,""+riga.getBovina(),headerFormat5));
			    		sheet.addCell( new Label(7,7,""+riga.getPrescrizioneBovina(),headerFormat5));
			    		sheet.addCell( new Label(8,7,""+riga.getMediaBovina(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,8,"Suina",headerFormat5));
			    		sheet.addCell( new Label(6,8,""+riga.getSuina(),headerFormat5));
			    		sheet.addCell( new Label(7,8,""+riga.getPrescrizioneSuina(),headerFormat5));
			    		sheet.addCell( new Label(8,8,""+riga.getMediaSuina(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,9,"Avicola",headerFormat5));
			    		sheet.addCell( new Label(6,9,""+riga.getAvicola(),headerFormat5));
			    		sheet.addCell( new Label(7,9,""+riga.getPrescrizioneAvicola(),headerFormat5));
			    		sheet.addCell( new Label(8,9,""+riga.getMediaAvicola(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,10,"Ovicaprina",headerFormat5));
			    		sheet.addCell( new Label(6,10,""+riga.getOvicaprina(),headerFormat5));
			    		sheet.addCell( new Label(7,10,""+riga.getPrescrizioneOvicaprina(),headerFormat5));
			    		sheet.addCell( new Label(8,10,""+riga.getMediaOvicaprina(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,11,"Cunicola",headerFormat5));
			    		sheet.addCell( new Label(6,11,""+riga.getCunicola(),headerFormat5));
			    		sheet.addCell( new Label(7,11,""+riga.getPrescrizioneCunicola(),headerFormat5));
			    		sheet.addCell( new Label(8,11,""+riga.getMediaCunicola(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,12,"Equina",headerFormat5));
			    		sheet.addCell( new Label(6,12,""+riga.getEquina(),headerFormat5));
			    		sheet.addCell( new Label(7,12,""+riga.getPrescrizioneEquina(),headerFormat5));
			    		sheet.addCell( new Label(8,12,""+riga.getMediaEquina(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,13,"Acquicoltura",headerFormat5));
			    		sheet.addCell( new Label(6,13,""+riga.getAcquicoltura(),headerFormat5));
			    		sheet.addCell( new Label(7,13,""+riga.getPrescrizioneAcquicoltura(),headerFormat5));
			    		sheet.addCell( new Label(8,13,""+riga.getMediaAcquicoltura(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,14,"Apiario",headerFormat5));
			    		sheet.addCell( new Label(6,14,""+riga.getApiario(),headerFormat5));
			    		sheet.addCell( new Label(7,14,""+riga.getPrescrizioneApiario(),headerFormat5));
			    		sheet.addCell( new Label(8,14,""+riga.getMediaApiario(),headerFormat5));
			    		
			    		sheet.addCell( new Label(5,15,"Bufalina",headerFormat5));
			    		sheet.addCell( new Label(6,15,""+riga.getBufalina(),headerFormat5));
			    		sheet.addCell( new Label(7,15,""+riga.getPrescrizioneBufalina(),headerFormat5));
			    		sheet.addCell( new Label(8,15,""+riga.getMediaBufalina(),headerFormat5));

			    		sheet.addCell( new Label(5,16,"* dal numero di allevamenti conteggiati vanno esclusi gli allevamenti per autoconsumo"));
			    		
			    		
		    	    	 }
		    	    	 else
		    	    	 {
		    	    	 
		    	    	
		    	    		 String sql_verigica = "select * from farmacosorveglianza_allegatoii where anno = "+anno + " and id_asl = "+asl;
		    	    		 PreparedStatement pst_ver = db.prepareStatement(sql_verigica);
		    	    		 ResultSet rs_ver = pst_ver.executeQuery();
		    	    		 UserBean user = (UserBean) context.getSession().getAttribute("User");
		    	    		 
		    	    		 String sql = "select  asl.description as descrasl,f.* from farmacosorveglianza_allegatoii f " +
		    	    		 " join lookup_site_id asl on (f.id_asl = asl.code)" +
		    	    		 "where id_asl = "+asl+" and anno = "+anno ;
		    	    		 PreparedStatement pst = db.prepareStatement(sql);
		    	    		 ResultSet rs = pst.executeQuery();
		    	    		 Collection<RigaAllegatoII> coll=	new ArrayList<RigaAllegatoII>();
		    	    		 Date dataUltimaModifica = null;
		    	    		 String modificatoDa = "";
		    	    		 RigaAllegatoII riga = new RigaAllegatoII();
		    	    		 while (rs.next())
		    	    		 {
		    	    			 riga.setId(rs.getInt("id"));
		    	    			 riga.setAnno(Integer.parseInt(anno));
		    	    			 riga.setIdAsl(Integer.parseInt(asl));
		    	    			 riga.setDescrizioneAsl(rs.getString("descrasl"));
		    	    			 riga.setAnimali_reddito_art_4_5(rs.getInt("animali_reddito_art_4_5"));
		    	    			 riga.setAnimali_reddito_art_11(rs.getInt("animali_reddito_art_11"));
		    	    			 riga.setAnimali_reddito_tot_a(rs.getInt("animali_reddito_tot_a"));
		    	    			 riga.setMangimi_medicati_prod_intermedi_art_3_4(rs.getInt("mangimi_medicati_prod_intermedi_art_3_4"));
		    	    			 riga.setMangimi_medicati_prod_intermedi_art_16(rs.getInt("mangimi_medicati_prod_intermedi_art_16"));
		    	    			 riga.setMangimi_medicati_prod_intermedi_tot_b(rs.getInt("mangimi_medicati_prod_intermedi_tot_b"));
		    	    			 riga.setScorte_vet_art_84(rs.getInt("scorte_vet_art_84"));
		    	    			 riga.setScorte_vet_tot_c(rs.getInt("scorte_vet_tot_c"));
		    	    			 riga.setScorte_allev_da_reddito(rs.getInt("scorte_allev_da_reddito"));
		    	    			 riga.setScorte_allev_da_compagnia(rs.getInt("scorte_allev_da_compagnia"));
		    	    			 riga.setScorte_allev_da_ippodromi(rs.getInt("scorte_allev_da_ippodromi"));
		    	    			 riga.setScorte_allev_tot_d(rs.getInt("scorte_allev_tot_d"));
		    	    			 riga.setBovina(rs.getInt("bovina"));
		    	    			 riga.setBufalina(rs.getInt("bufalina"));
		    	    			 riga.setSuina(rs.getInt("suina"));
		    	    			 riga.setAvicola(rs.getInt("avicola"));
		    	    			 riga.setOvicaprina(rs.getInt("ovicaprina"));
		    	    			 riga.setCunicola(rs.getInt("cunicola"));
		    	    			 riga.setEquina(rs.getInt("equina"));
		    	    			 riga.setAcquicoltura(rs.getInt("acquicoltura"));
		    	    	    	 riga.setApiario(rs.getInt("apiario"));		 
		    	    			 riga.setPrescrizioneAcquicoltura(rs.getInt("prescrizioneacquicoltura"));
		    	    			 riga.setPrescrizioneApiario(rs.getInt("prescrizioneapiario"));
		    	    			 riga.setPrescrizioneAvicola(rs.getInt("prescrizioneavicola"));
		    	    			 riga.setPrescrizioneBovina(rs.getInt("prescrizionebovina"));
		    	    			 riga.setPrescrizioneBufalina(rs.getInt("prescrizionebufalina"));
		    	    			 riga.setPrescrizioneCunicola(rs.getInt("prescrizionecunicola"));
		    	    			 riga.setPrescrizioneEquina(rs.getInt("prescrizioneequina"));
		    	    			 riga.setPrescrizioneOvicaprina(rs.getInt("prescrizioneovicaprina"));
		    	    			 riga.setPrescrizioneSuina(rs.getInt("prescrizionesuina"));
		    	    			 riga.setMediaAcquicoltura(rs.getFloat("mediaacquicoltura"));
		    	    			 riga.setMediaApiario(rs.getFloat("mediaapiario"));
		    	    			 riga.setMediaAvicola(rs.getFloat("mediaavicola"));
		    	    			 riga.setMediaBovina(rs.getFloat("mediabovina"));
		    	    			 riga.setMediaBufalina(rs.getFloat("mediabufalina"));
		    	    			 riga.setMediaCunicola(rs.getFloat("mediacunicola"));
		    	    			 riga.setMediaEquina(rs.getFloat("mediaequina"));
		    	    			 riga.setMediaOvicaprina(rs.getFloat("mediaovicaprina"));
		    	    			 riga.setMediaSuina(rs.getFloat("mediasuina"));
		    	    			 	 
		    	    		 }
		    	    		 WritableFont headerFont1 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
				    			WritableCellFormat headerFormat1 = new WritableCellFormat(headerFont1);
					    		WritableFont headerFont2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
					    		headerFont2.setUnderlineStyle(UnderlineStyle.SINGLE);
					    		WritableCellFormat headerFormat2 = new WritableCellFormat(headerFont2);
					    		WritableFont headerFont3 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
					    		WritableCellFormat headerFormat3 = new WritableCellFormat(headerFont3);
					    		WritableFont headerFont4 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
					    		WritableCellFormat headerFormat4 = new WritableCellFormat(headerFont4);
					    		headerFormat4.setBackground(Colour.LIGHT_ORANGE);
					    		sheet.addCell( new Label(0,3,"ASL "+siteList.getValueFromId(asl)+" ANNO "+anno,headerFormat1));
					    		sheet.addCell( new Label(0,5,"TOTALE PRESCRIZIONI PERVENUTE:",headerFormat2) );
					    		
					    		sheet.addCell( new Label(0,6,"1)Per Animali da Reddito:",headerFormat2) );
					    		sheet.addCell( new Label(0,7,"di cui per uso in deroga:",headerFormat3) );
					    		sheet.addCell( new Label(0,8,"D.lgs 158/2006 (artt. 4 e 5):    ",headerFormat3) );
					    		sheet.addCell( new Label(0,9,"D.lgs 193/2006 (art. 11)",headerFormat3) );
					    		sheet.addCell( new Label(1,8,""+riga.getAnimali_reddito_art_4_5() ,headerFormat4) );
					    		sheet.addCell( new Label(1,9,""+riga.getAnimali_reddito_art_11(),headerFormat4) );
					    		sheet.addCell( new Label(2,6,"a) Totale: n. ",headerFormat3) );
					    		sheet.addCell( new Label(3,6,""+riga.getAnimali_reddito_tot_a(),headerFormat4) );
					    		
					    		sheet.addCell( new Label(0,10,"2)Per Manbgimi Medicati e Prodotti Intermedi",headerFormat2) );
					    		sheet.addCell( new Label(0,11,"di cui per uso in deroga:",headerFormat3) );
					    		sheet.addCell( new Label(0,12,"D.lgs 90/93 (art. 3 c. 4)",headerFormat3) );
					    		sheet.addCell( new Label(0,13,"D.M. 16/11/93 (art. 16c.1)  ",headerFormat3) );
					    		sheet.addCell( new Label(1,12,""+riga.getMangimi_medicati_prod_intermedi_art_3_4() ,headerFormat4) );
					    		sheet.addCell( new Label(1,13,""+riga.getMangimi_medicati_prod_intermedi_art_16(),headerFormat4) );
					    		sheet.addCell( new Label(2,10,"b) Totale: n. ",headerFormat3) );
					    		sheet.addCell( new Label(3,10,""+riga.getMangimi_medicati_prod_intermedi_tot_b(),headerFormat4) );
					    		
					    		
					    		sheet.addCell( new Label(0,14,"3)Per Scorte Proprie del Veterinario (ambulatori, cliniche e attivita' zooiatrica)",headerFormat2) );
					    		sheet.addCell( new Label(0,15,"di cui per scorte farmaci uso umano",headerFormat3) );
					    		sheet.addCell( new Label(0,16,"D.lgs 193/2006 (art. 84 comma 7) ",headerFormat3) );
					    		sheet.addCell( new Label(1,16,""+riga.getScorte_vet_art_84() ,headerFormat4) );
					    		sheet.addCell( new Label(2,14,"c) Totale: n. ",headerFormat3));
					    		sheet.addCell( new Label(3,14,""+riga.getScorte_vet_tot_c(),headerFormat4) );
					    		
					    		sheet.addCell( new Label(0,17,"4)Per Scorte di Impianto di allevamento e custodia di animali",headerFormat2) );
					    		sheet.addCell( new Label(0,18,"di cui",headerFormat3) );
					    		sheet.addCell( new Label(0,19,"da reddito",headerFormat3) );
					    		sheet.addCell( new Label(0,20,"da compagnia",headerFormat3) );
					    		sheet.addCell( new Label(0,21,"ippodromi, maneggi, scuderie",headerFormat3) );
					    		sheet.addCell( new Label(1,19,""+riga.getScorte_allev_da_reddito() ,headerFormat4) );
					    		sheet.addCell( new Label(1,20,""+riga.getScorte_allev_da_compagnia() ,headerFormat4));
					    		sheet.addCell( new Label(1,21,""+riga.getScorte_allev_da_ippodromi(),headerFormat4) );
					    		sheet.addCell( new Label(2,17,"d) Totale: n. ",headerFormat3));
					    		sheet.addCell( new Label(3,17,""+riga.getScorte_allev_tot_d(),headerFormat4) );
					    		
					    		sheet.addCell( new Label(0,22,"Totale generale (a+b+c+d)",headerFormat3));
					    		sheet.addCell( new Label(2,22,"N.",headerFormat3));
					    		sheet.addCell( new Label(3,22,""+(riga.getAnimali_reddito_tot_a() + riga.getMangimi_medicati_prod_intermedi_tot_b() +  riga.getScorte_vet_tot_c() + riga.getScorte_allev_tot_d()),headerFormat4) );
					    		
					    		WritableFont headerFont5 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
					    		WritableCellFormat headerFormat5 = new WritableCellFormat(headerFont5);
					    		
					    		sheet.mergeCells(4, 2, 7, 2);
					    		sheet.addCell( new Label(5,3,"ALLEGATO II:",headerFormat5));
					    		sheet.mergeCells(4, 3, 7, 3);
					    		sheet.addCell( new Label(5,4,"SCHEDA PRESCRIZIONI FARMACO VETERINARIO",headerFormat5));
					    		
					    		sheet.mergeCells(5, 5, 6, 5);
					    		sheet.addCell( new Label(5,5,"INDICATORI DI FARMACOSORVEGLIANZA",headerFormat2));
					    		sheet.mergeCells(5, 6, 6,6);
					    		sheet.setColumnView(5, 28);
					    		sheet.addCell( new Label(5,6,"n. medio prescrizioni/anno per allevamento:",headerFormat5));
					    		
					    		sheet.addCell( new Label(5,7,"Bovina",headerFormat5));
					    		sheet.addCell( new Label(6,7,""+riga.getBovina(),headerFormat5));
					    		sheet.addCell( new Label(7,7,""+riga.getPrescrizioneBovina(),headerFormat5));
					    		sheet.addCell( new Label(8,7,""+riga.getMediaBovina(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,8,"Suina",headerFormat5));
					    		sheet.addCell( new Label(6,8,""+riga.getSuina(),headerFormat5));
					    		sheet.addCell( new Label(7,8,""+riga.getPrescrizioneSuina(),headerFormat5));
					    		sheet.addCell( new Label(8,8,""+riga.getMediaSuina(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,9,"Avicola",headerFormat5));
					    		sheet.addCell( new Label(6,9,""+riga.getAvicola(),headerFormat5));
					    		sheet.addCell( new Label(7,9,""+riga.getPrescrizioneAvicola(),headerFormat5));
					    		sheet.addCell( new Label(8,9,""+riga.getMediaAvicola(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,10,"Ovicaprina",headerFormat5));
					    		sheet.addCell( new Label(6,10,""+riga.getOvicaprina(),headerFormat5));
					    		sheet.addCell( new Label(7,10,""+riga.getPrescrizioneOvicaprina(),headerFormat5));
					    		sheet.addCell( new Label(8,10,""+riga.getMediaOvicaprina(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,11,"Cunicola",headerFormat5));
					    		sheet.addCell( new Label(6,11,""+riga.getCunicola(),headerFormat5));
					    		sheet.addCell( new Label(7,11,""+riga.getPrescrizioneCunicola(),headerFormat5));
					    		sheet.addCell( new Label(8,11,""+riga.getMediaCunicola(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,12,"Equina",headerFormat5));
					    		sheet.addCell( new Label(6,12,""+riga.getEquina(),headerFormat5));
					    		sheet.addCell( new Label(7,12,""+riga.getPrescrizioneEquina(),headerFormat5));
					    		sheet.addCell( new Label(8,12,""+riga.getMediaEquina(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,13,"Acquicoltura",headerFormat5));
					    		sheet.addCell( new Label(6,13,""+riga.getAcquicoltura(),headerFormat5));
					    		sheet.addCell( new Label(7,13,""+riga.getPrescrizioneAcquicoltura(),headerFormat5));
					    		sheet.addCell( new Label(8,13,""+riga.getMediaAcquicoltura(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,14,"Apiario",headerFormat5));
					    		sheet.addCell( new Label(6,14,""+riga.getApiario(),headerFormat5));
					    		sheet.addCell( new Label(7,14,""+riga.getPrescrizioneApiario(),headerFormat5));
					    		sheet.addCell( new Label(8,14,""+riga.getMediaApiario(),headerFormat5));

					    		sheet.addCell( new Label(5,15,"Bufalina",headerFormat5));
					    		sheet.addCell( new Label(6,15,""+riga.getBufalina(),headerFormat5));
					    		sheet.addCell( new Label(7,15,""+riga.getPrescrizioneBufalina(),headerFormat5));
					    		sheet.addCell( new Label(8,15,""+riga.getMediaBufalina(),headerFormat5));
					    		
					    		sheet.addCell( new Label(5,16,"* dal numero di allevamenti conteggiati vanno esclusi gli allevamenti per autoconsumo"));
					    		
		    		  
		    	    		 context.getRequest().setAttribute("RigaAllegatoII", riga);

		    	  	    	
		    	    	 }
		    		
		    		
		    		wb.write();
		    		wb.close();
	    		context.getResponse().setContentType( "application/xls" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"AllegatoII.xls" +   "\";" ); 
				context.getResponse().getOutputStream().write(bos.toByteArray());
	      	 
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
	     finally
	     {this.freeConnection(context, db);
	     }
		return "-none-";
	     
	  }
  
  public String executeCommandStampaAllegatoI(ActionContext context) 
  {
	  
	  String anno = context.getRequest().getParameter("anno");
	  String asl = context.getRequest().getParameter("asl");
	  Connection db = null ;
	  String annoSel = "" ;
	 
     try
     { 
    	 String fileName = "";
    	 db = this.getConnection(context);
    	  
    	 LookupList listanno = new LookupList(db, "lookup_anno");

    	 annoSel= listanno.getSelectedValue(Integer.parseInt(anno));
    	 LookupList siteList = new LookupList(db, "lookup_site_id");
      	 context.getRequest().setAttribute("aslList", siteList);
      	 String selectAsl = "select code , short_description from lookup_site_id where enabled = true ";
      	 PreparedStatement pst2 = db.prepareStatement(selectAsl);
      	 HashMap<Integer, String> aslMap = new HashMap<Integer, String>();
      	 ResultSet rs2 = pst2.executeQuery();
      	 while (rs2.next()) 
      	 {
      		 aslMap.put(rs2.getInt("code"), rs2.getString("short_description"));
      	 }  
      	 
      	 LookupList righeAllegato = new LookupList(db, "lookup_allegatoi_righe");
    	  context.getRequest().setAttribute("righeAllegato", righeAllegato);
    	 if (asl == null)
    	 {
    		 fileName = "AllegatiI_Regione.xls";
    		 String sql = "select  row.description ,row.code,row.level,sum(f.n_operatori) as n_operatori ,sum(f.n_ispezioni_effettuate) as n_ispezioni_effettuate ,sum(f.violazioni_amministrative) as violazioni_amministrative,"+
"sum(denunce_aut_giusiziarie) as denunce_aut_giudiziarie , sum(f.sequestri_amministrativi) as sequestri_amministrativi,sum(f.sequestri_giudiziari) as sequestri_giudiziari,"+
"sum(f.non_conformita_Campionamento) as non_conformita_Campionamento ,sum(f.n_operatori_piu_uno_controlli) as n_operatori_piu_uno_controlli,"+
"sum(f.n_operatori_piu_due_controlli) as n_operatori_piu_due_controlli "+
"from farmacosorveglianza_allegatoi f "+
"join lookup_allegatoi_righe row on (f.id_riga_allegatoi = row.code and row.enabled) join lookup_site_id asl on (f.id_asl = asl.code) "+
"where anno =  "+anno+
"group by row.description ,row.code,row.level "+
"order by "+
"row.level,row.code";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		Collection<RigaAllegatoI> coll=	new ArrayList<RigaAllegatoI>();
		HashMap<Integer,ArrayList<RigaAllegatoI>> collezione = new HashMap<Integer, ArrayList<RigaAllegatoI>>();
		for (int i = 0 ; i <righeAllegato.size() ; i++)
		{
			LookupElement el = (LookupElement)righeAllegato.get(i);
			collezione.put(el.getLevel(), new ArrayList<RigaAllegatoI>()) ;
		}
		
		Date dataUltimaModifica = null;
		String modificatoDa = "";
		while (rs.next())
		{
			
			int level = rs.getInt("level");
			RigaAllegatoI riga = new RigaAllegatoI();
			riga.setId(rs.getInt("code"));
			riga.setAnno(Integer.parseInt(anno)); 
			riga.setDescrizioneRiga(rs.getString("description"));
			riga.setN_operatori(rs.getInt("n_operatori"));
			riga.setN_ispezioni_effettuate(rs.getInt("n_ispezioni_effettuate"));
			riga.setN_operatori_piu_due_controlli(rs.getInt("n_operatori_piu_due_controlli"));
			riga.setN_operatori_piu_uno_controlli(rs.getInt("n_operatori_piu_uno_controlli"));
			riga.setNon_conformita_campionamento(rs.getInt("non_conformita_campionamento"));
			riga.setDenunce_aut_giusiziarie(rs.getInt("denunce_aut_giudiziarie"));
			riga.setSequestri_amministrativi(rs.getInt("sequestri_amministrativi"));
			riga.setSequestri_giudiziari(rs.getInt("sequestri_giudiziari"));
			riga.setViolazioni_amministrative(rs.getInt("violazioni_amministrative"));
			
			
			ArrayList<RigaAllegatoI> lista = collezione.get(level);
			lista.add(riga);
			collezione.put(level,lista);
		}
		context.getRequest().setAttribute("AllegatoI", collezione);
		HttpServletResponse res = context.getResponse();
    	res.setContentType( "application/xls" );
    	res.setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	
		ServletOutputStream out = context.getResponse().getOutputStream();
		  
		out.print("<table align='left'>");
		out.print("<tr style= 'background-color: rgb(227, 227, 227)'>");
		out.print("<td colspan='118'>");
		out.print("<table align = 'left' >");
		out.print("<tr><td colspan='118' align = 'left'>");
		out.print("<h2>Allegato I</h2> <br></td></tr>");
		out.print("<tr><td colspan='118' align = 'left'>");
		out.print("<h3>Scheda Rilevazione Dati Attivita' di Farmacosorveglianza </h3>");
		out.print("</td></tr>");
		out.print("<tr><td><b>REGIONE	:</b></td><td colspan='117' align = 'left'>CAMPANIA</td></tr>");
		out.print("<tr><td><b>ANNO	:</b></td><td colspan='117' align = 'left'>"+annoSel+"</td></tr>");
		out.print("<tr><td><b>ENTE	:</b></td><td colspan='117' align = 'left'>REGIONE CAMPANIA</td></tr></table></td></tr >");
		out.print("<tr>");
		out.print("<td colspan='2'>");
				//COSTRUZIONE TABELLA PER TUTTE LE ASL  
		out.print("<table border ='4' >"+ 
		"<tr style= 'background-color: #729FCF'>"+
		"<td colspan='2'>ASL o PROVINCIA;</td>"+
		"<td><center><b>N. Operatori</b></center></td>"+
		"<td><center><b>N. Ispezioni Effettuate</b></center></td>"+
		"<td><center><b>(*)(**)Violazioni Amministrative</b></center></td>"+
		"<td><center><b>(*)(**)Denunce Autorita' Giudiziaria</b></center></td>"+
		"<td><center><b>(*)(****)Sequestri Amministrativi</b></center></td>"+
		"<td><center><b>(*)(****)Sequestri Giudiziari</b></center></td>"+
		"<td><center><b>(***)Non Conformita a seguito di campionamento</b></center></td>"+
		"<td><center><b>N. Operatori sottoposti a piu di un controllo</b></center></td>"+
		"<td><center><b>N. Operatori sottoposti a piu di due controlli</b></center></td>" +
		    
	 	  
		"</tr>");      
		
		// STAMPA DATI TABELLONE 
		Iterator<Integer> itKeyLevel = collezione.keySet().iterator();
		  
		while(itKeyLevel.hasNext())
		{    
			int levelCurr = itKeyLevel.next();
			ArrayList<RigaAllegatoI> l = collezione.get(levelCurr);
			int count = 0;
			for(RigaAllegatoI rr : l)
			{
				count = count + 1;
			out.print("<tr>");  
			if(l.size()==2 && count==1)
				out.print("<td rowspan='2'>"+rr.getDescrizioneRiga().split("-")[0]+"</td><td>"+rr.getDescrizioneRiga().split("-")[1]+"</td>");
			else   
			{    
				if(l.size()==2 &&  count==2)
					out.print("<td>"+rr.getDescrizioneRiga().split("-")[1]+"</td>");
				else
					out.print("<td colspan='2'><center>"+rr.getDescrizioneRiga()+"</center></td>");
				 
			}
			out.print("<td>"+rr.getN_operatori()+"</td>");
			out.print("<td>"+rr.getN_ispezioni_effettuate()+"</td>");
			out.print("<td>"+rr.getViolazioni_amministrative()+"</td>");
			out.print("<td>"+rr.getDenunce_aut_giusiziarie()+"</td>");
			out.print("<td>"+rr.getSequestri_amministrativi()+"</td>");
			out.print("<td>"+rr.getSequestri_giudiziari()+"</td>");
			out.print("<td>"+rr.getNon_conformita_campionamento()+"</td>");
			out.print("<td>"+rr.getN_operatori_piu_uno_controlli()+"</td>");
			out.print("<td>"+rr.getN_operatori_piu_due_controlli()+"</td>");
			out.print("</tr>");

			}
		}
				
		 
				
				
				
			out.print("</table>"+
			"</td>"+
			"</tr></table>");

    	 }
    	 else
    	 {
    		 
    	String sql =	"select  asl.description as descrasl,f.*,row.description from farmacosorveglianza_allegatoi f " +
						"join lookup_allegatoi_righe row on (f.id_riga_allegatoi = row.code) join lookup_site_id asl on (f.id_asl = asl.code)" +				
						"where id_asl = "+asl+" and anno = "+anno+ " order by id_riga_allegatoi";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<RigaAllegatoI> coll=	new ArrayList<RigaAllegatoI>();
		Date dataUltimaModifica = null;
		String modificatoDa = "";
		while (rs.next())
		{
			RigaAllegatoI riga = new RigaAllegatoI();
			riga.setId(rs.getInt("id"));
			riga.setAnno(Integer.parseInt(anno));
			riga.setIdAsl(Integer.parseInt(asl));
			riga.setDescrizioneAsl(rs.getString("descrasl"));
			riga.setDescrizioneRiga(rs.getString("description"));
			riga.setN_operatori(rs.getInt("n_operatori"));
			riga.setN_ispezioni_effettuate(rs.getInt("n_ispezioni_effettuate"));
			riga.setN_operatori_piu_due_controlli(rs.getInt("n_operatori_piu_due_controlli"));
			riga.setN_operatori_piu_uno_controlli(rs.getInt("n_operatori_piu_uno_controlli"));
			riga.setNon_conformita_campionamento(rs.getInt("non_conformita_campionamento"));
			riga.setDenunce_aut_giusiziarie(rs.getInt("denunce_aut_giusiziarie"));
			riga.setSequestri_amministrativi(rs.getInt("sequestri_amministrativi"));
			riga.setSequestri_giudiziari(rs.getInt("sequestri_giudiziari"));
			riga.setViolazioni_amministrative(rs.getInt("violazioni_amministrative"));
			
			
			coll.add(riga);
			
		}
		fileName = "AllegatoI_"+siteList.getSelectedValue(Integer.parseInt(asl))+".xls";
		HttpServletResponse res = context.getResponse();
    	res.setContentType( "application/xls" );
    	res.setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	
		ServletOutputStream out = context.getResponse().getOutputStream();
		
		out.print("<table align='left'>");
		out.print("<tr style= 'background-color: rgb(227, 227, 227)'>");
		out.print("<td colspan='118'>");
		out.print("<table align = 'left' >");
		out.print("<tr><td colspan='10' align = 'left'>");
		out.print("<h2>Allegato I</h2> <br></td></tr>");
		out.print("<tr><td colspan='10' align = 'left'>");
		out.print("<h3>Scheda Rilevazione Dati Attivita' di Farmacosorveglianza </h3>");
		out.print("</td></tr>");
		out.print("<tr><td><b>ASL	:</b></td><td colspan='9' align = 'left'>"+siteList.getSelectedValue(Integer.parseInt(asl))+"</td></tr>");
		out.print("<tr><td><b>ANNO	:</b></td><td colspan='9' align = 'left'>"+annoSel+"</td></tr>");
		out.print("<tr><td><b>ENTE	:</b></td><td colspan='9' align = 'left'></td></tr></table></td></tr >");
		out.print("<tr>");
		out.print("<td colspan='2'>");
				//COSTRUZIONE TABELLA PER TUTTE LE ASL 
		out.print("<table border ='4' >"+
		"<tr style= 'background-color: #729FCF'>"+
		"<td>&nbsp;</td>"+
		"<td ><center><b>N. Operatori</b></center></td>"+
		"<td  ><center><b>N. Ispezioni</b></center></td>"+
		"<td  ><center><b>N. Violazioni Amm.</b></center></td>"+
		"<td  ><center><b>N. Denunce Giudiz.</b></center></td>"+
		"<td  ><center><b>N. Sequestri Amm.</b></center></td>"+
		"<td  ><center><b>N. Sequestri Giud.</b></center></td>"+
		"<td  ><center><b>N. Non Conformita Camp.</b></center></td>"+
		"<td  ><center><b>N. Oper. (Cont.>1)</b></center></td>"+
		"<td  ><center><b>N. Oper. (Cont.>2)</b></center></td>"+
		"</tr>");
		
			
			// STAMPA DATI TABELLINA 
			
			for (int i = 0 ; i<coll.size(); i++) // scorro le righe prese dalla lookup allegatoi_righe
			{
				RigaAllegatoI rigaAllegato =coll.get(i);
				out.print("<tr>"+
				"<td>"+rigaAllegato.getDescrizioneRiga()+"</td>"+
				"<td>"+rigaAllegato.getN_operatori()+"</td>"+
				"<td>"+rigaAllegato.getN_ispezioni_effettuate()+"</td>"+
				"<td>"+rigaAllegato.getViolazioni_amministrative()+"</td>"+
				"<td>"+rigaAllegato.getDenunce_aut_giusiziarie()+"</td>"+
				"<td>"+rigaAllegato.getSequestri_amministrativi()+"</td>"+
				"<td>"+rigaAllegato.getSequestri_giudiziari()+"</td>"+
				"<td>"+rigaAllegato.getNon_conformita_campionamento()+"</td>"+
				"<td>"+rigaAllegato.getN_operatori_piu_uno_controlli()+"</td>"+
				"<td>"+rigaAllegato.getN_operatori_piu_due_controlli()+"</td>");
				
				out.print("</tr>");	
			}
			out.print("</table>"+
			"</td>"+
			"</tr></table>");
    	 }
  
     }
     catch(Exception e)
     {
    	 e.printStackTrace();
     }
     finally
     {
    	 this.freeConnection(context, db);
     }
	return "-none-";
}
  private String validateColumn(WorksheetColumn colonna, String changedValue) {
	  	String nomeColonna=colonna.getProperty();
		String errore="";
		String changedValue1 = colonna.getChangedValue();    

			try
			{
				Integer.parseInt(changedValue1);
			
			}catch(Exception e)
			{
				colonna.setError("Tipo Dato Non Valido"); 
				errore="Inserire un numero";
			}
			if(errore.equals(""))
				colonna.removeError();
	return errore;
	

}
  
  
  public String executeCommandChiudiAllegatoI(ActionContext context) throws SQLException
  {
	  
	  String anno = context.getRequest().getParameter("anno");
	  context.getRequest().setAttribute("anno", anno);
	  
	  String asl = context.getRequest().getParameter("asl");
	  context.getRequest().setAttribute("asl", asl);
	  Connection db = null ;
	     try
	     {
	    	  db = this.getConnection(context);
	    	  PreparedStatement pst = db.prepareStatement("update farmacosorveglianza_allegatoi set stato = 1 where id_asl ="+asl+" and anno = "+anno);
	    	  pst.execute();
	     }
	     catch (Exception e) {
		e.printStackTrace();
		}
	     finally{
	    	 freeConnection(context, db);
	     }
	     
	  return executeCommandAllegatoI(context);
  }
  public String executeCommandAllegatoI(ActionContext context) throws SQLException
  {
	  
	  String anno = context.getRequest().getParameter("anno");
	  if (anno==null)
	  {
		  anno = (String)context.getRequest().getAttribute("anno");
	  }
	  
	  String asl = context.getRequest().getParameter("asl");
	  if (asl==null)
	  {
		  asl = (String)context.getRequest().getAttribute("asl");
	  }
	  
	context.getRequest().setAttribute("idAsl", asl);
	 HashMap<Integer, String> aslMap = new HashMap<Integer, String>();
	  Connection db = null ;
	  db = this.getConnection(context);
     try
     {
    	 
    	 LookupList listanno = new LookupList(db, "lookup_anno");

    		context.getRequest().setAttribute("anno", anno);

    		
    		
    	 LookupList siteList = new LookupList(db, "lookup_site_id");
    	
    	 String selectAsl = "select code , short_description from lookup_site_id where enabled = true ";
      	 PreparedStatement pst2 = db.prepareStatement(selectAsl);
      	
      	 ResultSet rs2 = pst2.executeQuery();
      	 while (rs2.next())
      	 {
      		 aslMap.put(rs2.getInt("code"), rs2.getString("short_description"));
      	 }
    	 
      	context.getRequest().setAttribute("aslList", siteList);
    	
    	 context.getRequest().setAttribute("aslListMap", aslMap);
      	  
      
    	 if (asl == null)
    	 {  
    		 String sql = "select  id_asl, asl.description as descrasl,f.*,row.description from farmacosorveglianza_allegatoi f " +
				"join lookup_allegatoi_righe row on (f.id_riga_allegatoi = row.code) join lookup_site_id asl on (f.id_asl = asl.code)" +
				
				"where anno = "+anno+ " and asl.enabled=true order by row.level,id_riga_allegatoi";
		PreparedStatement pst = db.prepareStatement(sql);
		
		ResultSet rs = pst.executeQuery();
		Collection<RigaAllegatoI> coll=	new ArrayList<RigaAllegatoI>();
		HashMap<Integer,ArrayList<RigaAllegatoI>> collezione = new HashMap<Integer, ArrayList<RigaAllegatoI>>();
		for (int i = 201 ; i <=207 ; i++)
		{
			collezione.put(i, new ArrayList<RigaAllegatoI>()) ;
		}
		
		Date dataUltimaModifica = null;
		String modificatoDa = "";
		int count = 0 ; 
		while (rs.next())
		{
			count ++ ;
			RigaAllegatoI riga = new RigaAllegatoI();
			riga.setId(rs.getInt("id"));
			riga.setAnno(Integer.parseInt(anno));
			riga.setIdAsl(rs.getInt("id_asl"));
			riga.setDescrizioneAsl(rs.getString("descrasl"));
			riga.setDescrizioneRiga(rs.getString("description"));
			riga.setN_operatori(rs.getInt("n_operatori"));
			riga.setN_ispezioni_effettuate(rs.getInt("n_ispezioni_effettuate"));
			riga.setN_operatori_piu_due_controlli(rs.getInt("n_operatori_piu_due_controlli"));
			riga.setN_operatori_piu_uno_controlli(rs.getInt("n_operatori_piu_uno_controlli"));
			riga.setNon_conformita_campionamento(rs.getInt("non_conformita_campionamento"));
			riga.setDenunce_aut_giusiziarie(rs.getInt("denunce_aut_giusiziarie"));
			riga.setSequestri_amministrativi(rs.getInt("sequestri_amministrativi"));
			riga.setSequestri_giudiziari(rs.getInt("sequestri_giudiziari"));
			riga.setViolazioni_amministrative(rs.getInt("violazioni_amministrative"));
			
			riga.setStato(rs.getInt("stato"));
			ArrayList<RigaAllegatoI> lista = collezione.get(riga.getIdAsl());
			lista.add(riga);
			collezione.put(riga.getIdAsl(),lista);
		}
		
		if (count<=0)
		{
			LookupList righeAllegato = new LookupList(db, "lookup_allegatoi_righe");
			context.getRequest().setAttribute("righeAllegato", righeAllegato);
		}
		else
		{
			
			String subquery = "select id_riga_allegatoi from farmacosorveglianza_allegatoi where 1=1 and anno = "+anno +  ((asl != null) ? "id_asl = "+asl : "") ;  
			String sql1 = "(select * from lookup_allegatoi_righe where code in ("+subquery+")) ";
			LookupList righeAllegato = new LookupList(db, sql1);
			context.getRequest().setAttribute("righeAllegato", righeAllegato);
		}
		
  	  
		context.getRequest().setAttribute("AllegatoI", collezione);
    		 
    	 }
    	 else
    	 {
    	 
    	
	  String sql_verigica = "select * from farmacosorveglianza_allegatoi where anno = "+anno + " and id_asl = "+asl+" order by id_riga_allegatoi";
	  PreparedStatement pst_ver = db.prepareStatement(sql_verigica);
	  ResultSet rs_ver = pst_ver.executeQuery();
	  UserBean user = (UserBean) context.getSession().getAttribute("User");
	  	Integer stato_db = 0 ;
	  if (rs_ver.next()==false)
	  {
		  Timestamp curr = new Timestamp (System.currentTimeMillis());
		
		  String sql_insert = "insert into farmacosorveglianza_allegatoi (id,id_asl,anno,id_riga_allegatoi,modified,modifiedby) select nextval('farmacosorveglianza_allegatoi_seq_id'),"+asl+","+anno+",code,'"+curr+"',"+user.getUserId()+" from lookup_allegatoi_righe where lookup_allegatoi_righe.enabled = true order by level";
		  PreparedStatement pst_insert = db.prepareStatement(sql_insert);
		  
		  pst_insert.execute();
	  }
	  else
	  {
		  stato_db = rs_ver.getInt("stato");
	  }
	  if (stato_db==null)
		  stato_db=0;
	  

  	    	RowSetDynaClass	rsdc			= null;
  	    	TableFacade tf = TableFacadeFactory.createTableFacade( "15", context.getRequest() );
//  	    	tf.setMaxRows(20);
  			if (user.getSiteId()!=-1)
  			{
  				
  				if (hasPermission(context, "farmacosorveglianza-farmacosorveglianza-allegatoI-edit")) 
  				{
  					if(stato_db == 0)
  					{
  					tf.setEditable(true);
  					}
  					else
  					{
  						tf.setEditable(false);
  					}
  				}
  			}
  			else
  			{
  				tf.setEditable(false);
  			}
  		if(stato_db==0)
  		{
  		if (user.getSiteId() !=-1   && hasPermission(context, "farmacosorveglianza-farmacosorveglianza-allegatoI-edit"))
  		{
  			Worksheet worksheet = tf.getWorksheet();
  			
  			//String query="update distributori_automatici set ";
  			if (worksheet.isSaving() || worksheet.hasChanges() && stato_db == 0)
			{ 
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);
				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update farmacosorveglianza_allegatoi set ";
					valoriAggiornati=new HashMap<String, String>();
					UniqueProperty u =new UniqueProperty("id",s);

					WorksheetRow row= worksheet.getRow(u);

					Collection<WorksheetColumn> columns = row.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						if (nomeColonna.equals("n_operatori") || nomeColonna.equals("n_ispezioni_effettuate") || 
								nomeColonna.equals("violazioni_amministrative") || nomeColonna.equals("denunce_aut_giusiziarie") || 
								nomeColonna.equals("sequestri_amministrativi") || nomeColonna.equals("sequestri_giudiziari") ||
								nomeColonna.equals("non_conformita_campionamento") || nomeColonna.equals("n_operatori_piu_uno_controlli") ||
								nomeColonna.equals("n_operatori_piu_due_controlli") )
						{
							validateColumn(colonna, changedValue);
							if (colonna.hasError()) {  
								context.getRequest().setAttribute("errore", colonna.getError());
								
								continue;    
								}
							if(!colonna.hasError()){

								valoriAggiornati.put(nomeColonna, changedValue);

							}
							
						}
							
						}
						
					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;
					
					int tipo=-1;
					int flag=0;
					while(it.hasNext()){
						if(c!=0){
							query+=",";
						}
						
						String kiaveNomeColonna=it.next();
						String valore=valoriAggiornati.get(kiaveNomeColonna);

						if(kiaveNomeColonna.equals("n_operatori") || kiaveNomeColonna.equals("n_ispezioni_effettuate") ||
								kiaveNomeColonna.equals("violazioni_amministrative") || kiaveNomeColonna.equals("denunce_aut_giusiziarie") || 
								kiaveNomeColonna.equals("sequestri_amministrativi") || kiaveNomeColonna.equals("sequestri_giudiziari") ||
								kiaveNomeColonna.equals("non_conformita_campionamento") || kiaveNomeColonna.equals("n_operatori_piu_uno_controlli") ||
								kiaveNomeColonna.equals("n_operatori_piu_due_controlli")){

							

							query=query+""+kiaveNomeColonna+"="+valore+" ";
						}
							c++;

					}	
					Timestamp curr = new Timestamp(System.currentTimeMillis());
					query=query+",modified ='"+curr+"' , modifiedby ="+user.getUserId()+" where "+uniquePropertyName+"="+s;
					if(c!=0 && stato_db==0){
						
					PreparedStatement pst=db.prepareStatement(query);
					pst.execute();
					pst.close();
					
						
					}
				}
			}
  		}
  		}	
  			String sql = "select  asl.description as descrasl,f.*,row.description from farmacosorveglianza_allegatoi f " +
  					"join lookup_allegatoi_righe row on (f.id_riga_allegatoi = row.code) join lookup_site_id asl on (f.id_asl = asl.code)" +
  					
  					"where id_asl = "+asl+" and anno = "+anno+ " order by id_riga_allegatoi";
  			PreparedStatement pst = db.prepareStatement(sql);
  			ResultSet rs = pst.executeQuery();
  			Collection<RigaAllegatoI> coll=	new ArrayList<RigaAllegatoI>();
  			Date dataUltimaModifica = null;
  			String modificatoDa = "";
  			while (rs.next())
  			{
  				RigaAllegatoI riga = new RigaAllegatoI();
  				riga.setId(rs.getInt("id"));
  				riga.setAnno(Integer.parseInt(anno));
  				riga.setIdAsl(Integer.parseInt(asl));
  				riga.setDescrizioneAsl(rs.getString("descrasl"));
  				riga.setDescrizioneRiga(rs.getString("description"));
  				riga.setN_operatori(rs.getInt("n_operatori"));
  				riga.setN_ispezioni_effettuate(rs.getInt("n_ispezioni_effettuate"));
  				riga.setN_operatori_piu_due_controlli(rs.getInt("n_operatori_piu_due_controlli"));
  				riga.setN_operatori_piu_uno_controlli(rs.getInt("n_operatori_piu_uno_controlli"));
  				riga.setNon_conformita_campionamento(rs.getInt("non_conformita_campionamento"));
  				riga.setDenunce_aut_giusiziarie(rs.getInt("denunce_aut_giusiziarie"));
  				riga.setSequestri_amministrativi(rs.getInt("sequestri_amministrativi"));
  				riga.setSequestri_giudiziari(rs.getInt("sequestri_giudiziari"));
  				
  				riga.setViolazioni_amministrative(rs.getInt("violazioni_amministrative"));
  				coll.add(riga);
  				
  			}
  			tf.setItems(coll );
  			tf.setColumnProperties("id",
  					"descrizioneRiga", "n_operatori","n_ispezioni_effettuate",
  					"violazioni_amministrative","denunce_aut_giusiziarie","sequestri_amministrativi",
  					"sequestri_giudiziari","non_conformita_campionamento","n_operatori_piu_uno_controlli",
  					"n_operatori_piu_due_controlli"
  			);
  			
  			tf.setMaxRowsIncrements(38);
  			tf.setMaxRows(38);
  			tf.setStateAttr("restore");
  		
  			HtmlRow row = (HtmlRow) tf.getTable().getRow();
  	        row.setUniqueProperty("id"); // the unique worksheet properties to identify the row
  	        context.getRequest().setAttribute("stato", stato_db);
  	        
  	        
  	        tf.getTable().getRow().getColumn( "id" ).setTitle( "&nbsp;" );
  			tf.getTable().getRow().getColumn( "descrizioneRiga" ).setTitle( "&nbsp;" );
  			tf.getTable().getRow().getColumn( "n_operatori" ).setTitle( "N. Operatori" );
  			tf.getTable().getRow().getColumn( "n_ispezioni_effettuate" ).setTitle( "N. Ispezioni" );
  			tf.getTable().getRow().getColumn( "violazioni_amministrative" ).setTitle( "N. Violazioni Amm." );
  			tf.getTable().getRow().getColumn( "denunce_aut_giusiziarie" ).setTitle( "N. Denunce Giudiz." );
  			tf.getTable().getRow().getColumn( "sequestri_amministrativi" ).setTitle( "N. Sequestri Amm." );
  			tf.getTable().getRow().getColumn( "sequestri_giudiziari" ).setTitle( "N. Sequestri Giud." );
  			tf.getTable().getRow().getColumn( "non_conformita_campionamento" ).setTitle( "N. Non Conformita Camp." );
  			tf.getTable().getRow().getColumn( "n_operatori_piu_uno_controlli" ).setTitle( "N. Oper. (Cont.>1)" );
  			tf.getTable().getRow().getColumn( "n_operatori_piu_due_controlli" ).setTitle( "N. Oper. (Cont.>2)" );
  			
  			Limit limit = tf.getLimit();
  			if(! limit.isExported() )
  			{
  				
  				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneRiga");
  				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
  				cg.setEditable(false);
  				cg.setFilterable( false );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("id");
  				cg.setFilterable( false );
  		
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
  		        				
  		        				
  		        				return "<label style = 'display:none'>"+iddef+"</label>";
  		        			}
  		        		}
  		        
  		        	);
  				 
  				cg.setEditable(false);
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("n_operatori");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "n_operatori", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				 
  				cg.setFilterable( false );
  				cg.setWidth("2");
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("n_ispezioni_effettuate");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "n_ispezioni_effettuate", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("violazioni_amministrative");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "violazioni_amministrative", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("denunce_aut_giusiziarie");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "denunce_aut_giusiziarie", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("sequestri_amministrativi");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "sequestri_amministrativi", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("sequestri_giudiziari");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "sequestri_giudiziari", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("non_conformita_campionamento");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "non_conformita_campionamento", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("n_operatori_piu_uno_controlli");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "n_operatori_piu_uno_controlli", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("n_operatori_piu_due_controlli");
  				cg.getCellRenderer().setCellEditor( 
  		        		new CellEditor()
  		        		{	
  		        			public Object getValue(Object item, String property, int rowCount)
  		        			{
  		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "n_operatori_piu_due_controlli", rowCount);
  		        				
  		        				
  		        				return "<center>"+iddef+"</center>";
  		        			}
  		        		}
  		        
  		        	);
  				cg.setFilterable( false );
  				
  				
  				
  			}
  			 
  			if (stato_db == 0)
  			{
  			ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
			item2.setTooltip( "Salva" );
		
			tf.getToolbar().addToolbarItem( item2 );
  			}
  			 
  			String tabella = tf.render();
  			context.getRequest().setAttribute( "tabella", tabella );
  			context.getRequest().setAttribute( "tf", tf );
    	 }
     }
     catch(Exception e )
     {
    	 e.printStackTrace();
     }
     finally
     {
    	 this.freeConnection(context, db);
     }
	  return "AllegatoIOK" ;
  }
  
  
  public String executeCommandAllegatoII(ActionContext context) throws SQLException
  {
	  
	  String anno = context.getRequest().getParameter("anno");
	  
	  String asl = context.getRequest().getParameter("asl");
	context.getRequest().setAttribute("idAsl", asl);
	context.getRequest().setAttribute("anno", anno);
	 HashMap<Integer, String> aslMap = new HashMap<Integer, String>();
	  Connection db = null ;
	  db = this.getConnection(context);
     try
     {
    	 LookupList siteList = new LookupList(db, "lookup_site_id");
    	
    	 String selectAsl = "select code , short_description from lookup_site_id where enabled = true ";
      	 PreparedStatement pst2 = db.prepareStatement(selectAsl);
      	 ResultSet rs2 = pst2.executeQuery();
      	 
      	 while (rs2.next())
      	 {
      		 aslMap.put(rs2.getInt("code"), rs2.getString("short_description"));
      	 }
    	 
      	context.getRequest().setAttribute("aslList", siteList);
    	
    	 context.getRequest().setAttribute("aslListMap", aslMap);
      	  
    	 
      	LookupList righeAllegato = new LookupList(db, "lookup_allegatoi_righe");
    	  context.getRequest().setAttribute("righeAllegato", righeAllegato);
    	 if (asl == null)
    	 {
    		 	String sql = "select  code as idasl, asl.description as descrasl,f.* from farmacosorveglianza_allegatoii f " +
				"right join lookup_site_id asl on (f.id_asl = asl.code and anno = "+anno+")" +
				" where code != 16 and asl.enabled=true order by code";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		
		HashMap<Integer,RigaAllegatoII> collezione = new HashMap<Integer, RigaAllegatoII>();
		
		while (rs.next())
		{
			 RigaAllegatoII riga = new RigaAllegatoII();
			 riga.setAnimali_reddito_art_4_5(rs.getInt("animali_reddito_art_4_5"));
			 riga.setAnimali_reddito_art_11(rs.getInt("animali_reddito_art_11"));
			 riga.setAnimali_reddito_tot_a(rs.getInt("animali_reddito_tot_a"));
			 riga.setMangimi_medicati_prod_intermedi_art_3_4(rs.getInt("mangimi_medicati_prod_intermedi_art_3_4"));
			 riga.setMangimi_medicati_prod_intermedi_art_16(rs.getInt("mangimi_medicati_prod_intermedi_art_16"));
			 riga.setMangimi_medicati_prod_intermedi_tot_b(rs.getInt("mangimi_medicati_prod_intermedi_tot_b"));
			 riga.setScorte_vet_art_84(rs.getInt("scorte_vet_art_84"));
			 riga.setScorte_vet_tot_c(rs.getInt("scorte_vet_tot_c"));
			 riga.setScorte_allev_da_reddito(rs.getInt("scorte_allev_da_reddito"));
			 riga.setScorte_allev_da_compagnia(rs.getInt("scorte_allev_da_compagnia"));
			 riga.setScorte_allev_da_ippodromi(rs.getInt("scorte_allev_da_ippodromi"));
			 riga.setScorte_allev_tot_d(rs.getInt("scorte_allev_tot_d")); 
			 riga.setBovina(rs.getInt("bovina"));
			 riga.setBufalina(rs.getInt("bufalina"));
			 riga.setSuina(rs.getInt("suina"));
			 riga.setAvicola(rs.getInt("avicola"));
			 riga.setOvicaprina(rs.getInt("ovicaprina"));
			 riga.setCunicola(rs.getInt("cunicola"));
			 riga.setEquina(rs.getInt("equina"));
			 riga.setAcquicoltura(rs.getInt("acquicoltura"));
			 riga.setApiario(rs.getInt("apiario"));
			 
			 riga.setPrescrizioneAcquicoltura(rs.getInt("prescrizioneacquicoltura"));
			 riga.setPrescrizioneApiario(rs.getInt("prescrizioneapiario"));
			 riga.setPrescrizioneAvicola(rs.getInt("prescrizioneavicola"));
			 riga.setPrescrizioneBovina(rs.getInt("prescrizionebovina"));
			 riga.setPrescrizioneBufalina(rs.getInt("prescrizionebufalina"));
			 riga.setPrescrizioneCunicola(rs.getInt("prescrizionecunicola"));
			 riga.setPrescrizioneEquina(rs.getInt("prescrizioneequina"));
			 riga.setPrescrizioneOvicaprina(rs.getInt("prescrizioneovicaprina"));
			 riga.setPrescrizioneSuina(rs.getInt("prescrizionesuina"));
			 
			 riga.setMediaAcquicoltura(rs.getFloat("mediaacquicoltura"));
			 riga.setMediaApiario(rs.getFloat("mediaapiario"));
			 riga.setMediaAvicola(rs.getFloat("mediaavicola"));
			 riga.setMediaBovina(rs.getFloat("mediabovina"));
			 riga.setMediaBufalina(rs.getFloat("mediabufalina"));
			 riga.setMediaCunicola(rs.getFloat("mediacunicola"));
			 riga.setMediaEquina(rs.getFloat("mediaequina"));
			 riga.setMediaOvicaprina(rs.getFloat("mediaovicaprina"));
			 riga.setMediaSuina(rs.getFloat("mediasuina"));
			 
			 riga.setStato(rs.getInt("stato"));
			 riga.setAnno(Integer.parseInt(anno));
			 riga.setIdAsl(rs.getInt("idasl"));
			 riga.setDescrizioneAsl(rs.getString("descrasl"));
			collezione.put(riga.getIdAsl(),riga);
		}
		context.getRequest().setAttribute("ListaAllegatoII", collezione);
    		 
    	 }
    	 else
    	 {
    	 
    	
    		 String sql_verigica = "select * from farmacosorveglianza_allegatoii where anno = "+anno + " and id_asl = "+asl;
    		 PreparedStatement pst_ver = db.prepareStatement(sql_verigica);
    		 ResultSet rs_ver = pst_ver.executeQuery();
    		 UserBean user = (UserBean) context.getSession().getAttribute("User");
    		
    		 /**
    		 * 	ALLA SELEZIONE DELL'ANNO VIENE CREATO UN NUOVO RECORD PER L'ALLEGATO I PER L'ANNO SELEZIONATO 
    		 * 	E L'ASL A CUI L'UTENTE APPARTIENE
    		 */
    		 int stato_db = 0 ;
    		 if (rs_ver.next()==false)
    		 {
    			 Timestamp curr = new Timestamp (System.currentTimeMillis());
    			 String sql_insert = "insert into farmacosorveglianza_allegatoii (id,id_asl,anno,modified,modifiedby,stato) values (nextval('farmacosorveglianza_allegatoii_seq_id'),"+asl+","+anno+",'"+curr+"',"+user.getUserId()+",0)";
    			 PreparedStatement pst_insert = db.prepareStatement(sql_insert);
    			 pst_insert.execute();
    		 }
    		 else
    		 {
    			 stato_db = rs_ver.getInt("stato");
    		 }
    		String stato = context.getRequest().getParameter("stato");
    		 
    		 /**
     		 * 	SE SI STA TENTANDO DI AGGIORNARE I DATI VIENE EFFETTUATO UN UPDATE SULLA RIGA INSERITA PRECEDENTEMENTE 
     		 */
    		 
    		 if (context.getRequest().getParameter("button")!=null && stato_db==0)
    		 {
    			 RigaAllegatoII riga = new RigaAllegatoII();
    			 riga.setAnimali_reddito_art_4_5(Integer.parseInt(context.getRequest().getParameter("animali_reddito_art_4_5")));
    			 riga.setAnimali_reddito_art_11(Integer.parseInt(context.getRequest().getParameter("animali_reddito_art_11")));
    			 riga.setAnimali_reddito_tot_a(Integer.parseInt(context.getRequest().getParameter("animali_reddito_tot_a")));
    			 riga.setMangimi_medicati_prod_intermedi_art_3_4(Integer.parseInt(context.getRequest().getParameter("mangimi_medicati_prod_intermedi_art_3_4")));
    			 riga.setMangimi_medicati_prod_intermedi_art_16(Integer.parseInt(context.getRequest().getParameter("mangimi_medicati_prod_intermedi_art_16")));
    			 riga.setMangimi_medicati_prod_intermedi_tot_b(Integer.parseInt(context.getRequest().getParameter("mangimi_medicati_prod_intermedi_tot_b")));
    			 riga.setScorte_vet_art_84(Integer.parseInt(context.getRequest().getParameter("scorte_vet_art_84")));
    			 riga.setScorte_vet_tot_c(Integer.parseInt(context.getRequest().getParameter("scorte_vet_tot_c")));
    			 riga.setScorte_allev_da_reddito(Integer.parseInt(context.getRequest().getParameter("scorte_allev_da_reddito")));
    			 riga.setScorte_allev_da_compagnia(Integer.parseInt(context.getRequest().getParameter("scorte_allev_da_compagnia")));
    			 riga.setScorte_allev_da_ippodromi(Integer.parseInt(context.getRequest().getParameter("scorte_allev_da_ippodromi")));
    			 riga.setScorte_allev_tot_d(Integer.parseInt(context.getRequest().getParameter("scorte_allev_tot_d"))); 
    			 riga.setStato(Integer.parseInt(stato));
    		
    			 riga.setBovina(Integer.parseInt(context.getRequest().getParameter("bovina")));
    			 riga.setBufalina(Integer.parseInt(context.getRequest().getParameter("bufalina")));
    			 riga.setSuina(Integer.parseInt(context.getRequest().getParameter("suina")));
    			 riga.setAvicola(Integer.parseInt(context.getRequest().getParameter("avicola")));
    			 riga.setOvicaprina(Integer.parseInt(context.getRequest().getParameter("ovicaprina")));
    			 riga.setCunicola(Integer.parseInt(context.getRequest().getParameter("cunicola")));
    			 riga.setEquina(Integer.parseInt(context.getRequest().getParameter("equina")));
    			 riga.setAcquicoltura(Integer.parseInt(context.getRequest().getParameter("acquicoltura")));
    			 riga.setApiario(Integer.parseInt(context.getRequest().getParameter("apiario")));
    			 
    			 riga.setPrescrizioneBovina(Integer.parseInt(context.getRequest().getParameter("prescrizione_bovina")));
    			 riga.setPrescrizioneBufalina(Integer.parseInt(context.getRequest().getParameter("prescrizione_bufalina")));
    			 riga.setPrescrizioneSuina(Integer.parseInt(context.getRequest().getParameter("prescrizione_suina")));
    			 riga.setPrescrizioneAvicola(Integer.parseInt(context.getRequest().getParameter("prescrizione_avicola")));
    			 riga.setPrescrizioneOvicaprina(Integer.parseInt(context.getRequest().getParameter("prescrizione_ovicaprina")));
    			 riga.setPrescrizioneCunicola(Integer.parseInt(context.getRequest().getParameter("prescrizione_cunicola")));
    			 riga.setPrescrizioneEquina(Integer.parseInt(context.getRequest().getParameter("prescrizione_equina")));
    			 riga.setPrescrizioneAcquicoltura(Integer.parseInt(context.getRequest().getParameter("prescrizione_acquicoltura")));
    			 riga.setPrescrizioneApiario(Integer.parseInt(context.getRequest().getParameter("prescrizione_apiario")));
    			 
    			 riga.setMediaBovina(Float.parseFloat(context.getRequest().getParameter("media_bovina")));
    			 riga.setMediaBufalina(Float.parseFloat(context.getRequest().getParameter("media_bufalina")));
    			 riga.setMediaSuina(Float.parseFloat(context.getRequest().getParameter("media_suina")));
    			 riga.setMediaAvicola(Float.parseFloat(context.getRequest().getParameter("media_avicola")));
    			 riga.setMediaOvicaprina(Float.parseFloat(context.getRequest().getParameter("media_ovicaprina")));
    			 riga.setMediaCunicola(Float.parseFloat(context.getRequest().getParameter("media_cunicola")));
    			 riga.setMediaEquina(Float.parseFloat(context.getRequest().getParameter("media_equina")));
    			 riga.setMediaAcquicoltura(Float.parseFloat(context.getRequest().getParameter("media_acquicoltura")));
    			 riga.setMediaApiario(Float.parseFloat(context.getRequest().getParameter("media_apiario")));
    			 
    			 
    			 riga.setAnno(Integer.parseInt(anno));
    			 riga.setModifiedby(user.getUserId());
    			 riga.setModified(new Timestamp((new Date(System.currentTimeMillis()).getTime())));
    			 riga.setIdAsl(Integer.parseInt(asl));
    			 riga.update(db);
    			 context.getRequest().setAttribute("salvataggio", "ok");
    			 
    		 }
    		 
    		 
    		 
    		 String sql = "select  asl.description as descrasl,f.* from farmacosorveglianza_allegatoii f " +
    		 " join lookup_site_id asl on (f.id_asl = asl.code)" +
    		 "where id_asl = "+asl+" and anno = "+anno + " and asl.enabled=true order by code" ;
    		 PreparedStatement pst = db.prepareStatement(sql);
    		 ResultSet rs = pst.executeQuery();
    		 Collection<RigaAllegatoII> coll=	new ArrayList<RigaAllegatoII>();
    		 Date dataUltimaModifica = null;
    		 String modificatoDa = "";
    		 RigaAllegatoII riga = new RigaAllegatoII();
    		 while (rs.next())
    		 {
    			 
    			 riga.setId(rs.getInt("id"));
    			 riga.setAnno(Integer.parseInt(anno));
    			 riga.setIdAsl(Integer.parseInt(asl));
    			 riga.setDescrizioneAsl(rs.getString("descrasl"));
    			 riga.setAnimali_reddito_art_4_5(rs.getInt("animali_reddito_art_4_5"));
    			 riga.setAnimali_reddito_art_11(rs.getInt("animali_reddito_art_11"));
    			 riga.setAnimali_reddito_tot_a(rs.getInt("animali_reddito_tot_a"));
    			 riga.setMangimi_medicati_prod_intermedi_art_3_4(rs.getInt("mangimi_medicati_prod_intermedi_art_3_4"));
    			 riga.setMangimi_medicati_prod_intermedi_art_16(rs.getInt("mangimi_medicati_prod_intermedi_art_16"));
    			 riga.setMangimi_medicati_prod_intermedi_tot_b(rs.getInt("mangimi_medicati_prod_intermedi_tot_b"));
    			 riga.setScorte_vet_art_84(rs.getInt("scorte_vet_art_84"));
    			 riga.setScorte_vet_tot_c(rs.getInt("scorte_vet_tot_c"));
    			 riga.setScorte_allev_da_reddito(rs.getInt("scorte_allev_da_reddito"));
    			 riga.setScorte_allev_da_compagnia(rs.getInt("scorte_allev_da_compagnia"));
    			 riga.setScorte_allev_da_ippodromi(rs.getInt("scorte_allev_da_ippodromi"));
    			 riga.setScorte_allev_tot_d(rs.getInt("scorte_allev_tot_d"));
    			 riga.setBovina(rs.getInt("bovina"));
    			 riga.setBufalina(rs.getInt("bufalina"));
    			 riga.setSuina(rs.getInt("suina"));
    			 riga.setAvicola(rs.getInt("avicola"));
    			 riga.setOvicaprina(rs.getInt("ovicaprina"));
    			 riga.setStato(rs.getInt("stato"));
    			 riga.setCunicola(rs.getInt("cunicola"));
    			 riga.setEquina(rs.getInt("equina"));
    			 riga.setAcquicoltura(rs.getInt("acquicoltura"));
    			 riga.setApiario(rs.getInt("apiario"));
    			 
    			 riga.setPrescrizioneAcquicoltura(rs.getInt("prescrizioneacquicoltura"));
    			 riga.setPrescrizioneApiario(rs.getInt("prescrizioneapiario"));
    			 riga.setPrescrizioneAvicola(rs.getInt("prescrizioneavicola"));
    			 riga.setPrescrizioneBovina(rs.getInt("prescrizionebovina"));
    			 riga.setPrescrizioneBufalina(rs.getInt("prescrizionebufalina"));
    			 riga.setPrescrizioneCunicola(rs.getInt("prescrizionecunicola"));
    			 riga.setPrescrizioneEquina(rs.getInt("prescrizioneequina"));
    			 riga.setPrescrizioneOvicaprina(rs.getInt("prescrizioneovicaprina"));
    			 riga.setPrescrizioneSuina(rs.getInt("prescrizionesuina"));
    			 
    			 riga.setMediaAcquicoltura(rs.getFloat("mediaacquicoltura"));
    			 riga.setMediaApiario(rs.getFloat("mediaapiario"));
    			 riga.setMediaAvicola(rs.getFloat("mediaavicola"));
    			 riga.setMediaBovina(rs.getFloat("mediabovina"));
    			 riga.setMediaBufalina(rs.getFloat("mediabufalina"));
    			 riga.setMediaCunicola(rs.getFloat("mediacunicola"));
    			 riga.setMediaEquina(rs.getFloat("mediaequina"));
    			 riga.setMediaOvicaprina(rs.getFloat("mediaovicaprina"));
    			 riga.setMediaSuina(rs.getFloat("mediasuina"));
    			 
    			 
    			 
    			
    		 }
	  
    		 context.getRequest().setAttribute("RigaAllegatoII", riga);

  	    	
    	 }
     }
     catch(Exception e )
     {
    	 e.printStackTrace();
     }
     finally
     {
    	 this.freeConnection(context, db);
     }
	  return "AllegatoIIOK" ;
  }
  

  /**
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-reports-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "account-reports"));
      String filePath1 = this.getPath(context, "account-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath1);
      }
      String filePath2 = this.getPath(context, "account-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      java.io.File fileToDelete2 = new java.io.File(filePath2);
      if (!fileToDelete2.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath2);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }


  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
 
  
  public String executeCommandSearchFormFcie(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view"))) {
	      return ("PermissionError");
	    }

	    //Bypass search form for portal users
	    if (isPortalUser(context)) {
	      return (executeCommandSearchFcie(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "--Tutti--");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	       
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c
					.buildList(db, user.getSiteId());

			LookupList comuniList = new LookupList(listaComuni);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      
	      
	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(
	          context, "SearchOrgListInfoFcie");
	      orgListInfo.setCurrentLetter("");
	      orgListInfo.setCurrentOffset(0);
	      
	      
	      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
	        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
	      }
	      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
	        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
	      }
	      Organization newOrg = (Organization) context.getFormBean();
	      //ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      
	      
	      
	      if (newOrg==null)
	      {
	    	  newOrg = new Organization();
	      }
	     
	      //newOrg.setComuni2(db);
	      //if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);

	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchFcieOK");
	  }
  
  public String executeCommandSearchFormFar(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-farmaci-view"))) {
	      return ("PermissionError");
	    }

	    //Bypass search form for portal users
	    if (isPortalUser(context)) {
	      return (executeCommandSearchFar(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1, "--Tutti--");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      


	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(
	          context, "SearchOrgListInfoFcie");
	      orgListInfo.setCurrentLetter("");
	      orgListInfo.setCurrentOffset(0);
	      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
	        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
	      }
	      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
	        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
	      }
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchFarOK");
	  }

  public String executeCommandSearchFormVet(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-view"))) {
	      return ("PermissionError");
	    }

	    //Bypass search form for portal users
	    if (isPortalUser(context)) {
	      return (executeCommandSearchVet(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "--Tutti--");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);

	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(
	          context, "SearchOrgListInfoFcie");
	      orgListInfo.setCurrentLetter("");
	      orgListInfo.setCurrentOffset(0);
	      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
	        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
	      }
	      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
	        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
	      }
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchVetOK");
	  }
  
  public String executeCommandSearchFormPre(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-view"))) {
	      return ("PermissionError");
	    }

	    //Bypass search form for portal users
	    if (isPortalUser(context)) {
	      return (executeCommandSearchVet(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    VeterinariList organizationList2 = new VeterinariList();

	    Connection db = null;
	    try {
	      db = getConnection(context);
	      
	      PreparedStatement pst4 = db.prepareStatement(
	        "SELECT * FROM veterinari2");
	      	      
	      organizationList2.buildList(db, pst4);
	      context.getRequest().setAttribute("VeterinariList", organizationList2);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "--Tutti--");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);

	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(
	          context, "SearchOrgListInfoFcie");
	      orgListInfo.setCurrentLetter("");
	      orgListInfo.setCurrentOffset(0);
	      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
	        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
	      }
	      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
	        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
	      }
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchPreOK");
	  }

  /**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  
  
  public String executeCommandAddFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-far-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      
	      Farmaci newOrg = (Farmaci) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      
	      
	      if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    //if a different module reuses this action then do a explicit return
	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

	    return getReturn(context, "AddFar");
	  }

  public String executeCommandAddVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	     
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      Veterinari newOrg = (Veterinari) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      
	      
	      if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    //if a different module reuses this action then do a explicit return
	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

	    return getReturn(context, "AddVet");
	  }
  
  public String executeCommandAddPre(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    OrganizationList organizationList = new OrganizationList();
	    VeterinariList organizationList2 = new VeterinariList();
	    FarmaciList organizationList3 = new FarmaciList();
	    PrescrizioniList organizationList4 = new PrescrizioniList();
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	  
		 
	      //context.getSession().setAttribute("previousSearchType", "accounts");
	      PreparedStatement pst = db.prepareStatement(
	        "SELECT * FROM farmacie");
	      PreparedStatement pst2 = db.prepareStatement(
	        "SELECT * FROM veterinari2");
	      PreparedStatement pst3 = db.prepareStatement(
	        "SELECT * FROM farmaci2");
	      PreparedStatement pst4 = db.prepareStatement(
	        "SELECT * FROM organization WHERE tipologia = 2");
	      	      
	      organizationList.buildList(db, pst);
	      organizationList2.buildList(db, pst2);
	      organizationList3.buildList(db, pst3);
	      //organizationList4.buildList(db, pst4);
	      
	      context.getRequest().setAttribute("FarmacieList", organizationList);
	      context.getRequest().setAttribute("VeterinariList", organizationList2);
	      context.getRequest().setAttribute("FarmaciList", organizationList3);
	      context.getRequest().setAttribute("OrganizationList", organizationList4);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      Prescrizioni newOrg = (Prescrizioni) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    //if a different module reuses this action then do a explicit return
	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

	    return getReturn(context, "AddPre");
	  }

  
  public String executeCommandAddFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      
	      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
	      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoLocale", TipoLocale);
	      
	      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
	      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

	      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
	      
	      Organization newOrg = (Organization) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      if(newOrg == null)
	      {
	    	  newOrg = new Organization();
	      }
	      
	      newOrg.setComuni2(db);
	      //if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      //}
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    //if a different module reuses this action then do a explicit return
	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

	    return getReturn(context, "AddFcie");
	  }
  
  
 



  public String executeCommandDetailsVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Veterinari newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("idVeterinario");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("idVeterinario");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }
	      newOrg = new Veterinari(db, tempid);
	      //check whether or not the owner is an active User
	     
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action1 = context.getRequest().getParameter("action1");
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsVetOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "DetailsVet");
	    }
	  }
  
  public String executeCommandDetailsPre(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Prescrizioni newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("idPrescrizione");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("idPrescrizione");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }
	      newOrg = new Prescrizioni(db, tempid);
	      //check whether or not the owner is an active User
	     
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsPreOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "DetailsPre");
	    }
	  }
  
  public String executeCommandDetailsFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-far-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Farmaci newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("idFarmaco");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("idFarmaco");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }
	      newOrg = new Farmaci(db, tempid);
	      //check whether or not the owner is an active User
	      
	      //Caricamento Diffide
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getIdFarmaco(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getIdFarmaco(),null));
	   
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsFarOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "DetailsFar");
	    }
	  }
  
  
  
  
  public String executeCommandDetailsFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("idFarmacia");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("idFarmacia");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	    
	      newOrg = new Organization(db, tempid);
	      
	      //check whether or not the owner is an active User
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	      
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	       
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsFcieOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "DetailsFcie");
	    }
	  }
  
  
  public String executeCommandCessazioneAttivita(ActionContext context) throws ParseException
	{
		 
	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
		String dataFineString = context.getParameter("dataCessazioneAttivita");
		Timestamp dataFine = null;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
		String note = context.getParameter("noteCessazione");
		int userId = getUserId(context);
	  
		Connection db = null;
		try
		{
			db=super.getConnection(context);
			Organization org = new Organization(db,orgId);
			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
			context.getRequest().setAttribute("OrgDetails", org);
		}
		catch(SQLException e)
		{
			
		}
			finally{
				super.freeConnection(context, db);
			}
		return "InsertFcieOK";
	}
  
  public String executeCommandSospensioneAttivita(ActionContext context) throws ParseException
 	{
 		 
 	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
 		String dataFineString = context.getParameter("dataSospensioneAttivita");
 		Timestamp dataFine = null;	
 		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
 		String note = context.getParameter("noteSospensione");
 		int userId = getUserId(context);
 	  
 		Connection db = null;
 		try
 		{
 			db=super.getConnection(context);
 			Organization org = new Organization(db,orgId);
 			AccountsUtil.sospensioneAttivita(db, orgId, dataFine, note, userId);
 			context.getRequest().setAttribute("OrgDetails", org);
 		}
 		catch(SQLException e)
 		{
 			
 		}
 			finally{
 				super.freeConnection(context, db);
 			}
 		return "InsertFcieOK";
 	}
  
  public String executeCommandDetails(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("idFarmacia");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("idFarmacia");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	    
	      newOrg = new Organization(db, tempid);
	      //check whether or not the owner is an active User
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	      
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	       
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsFcieOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "DetailsFcie");
	    }
	  }
  
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboardPre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-view")) {
      if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
        return ("PermissionError");
      }
      //Bypass dashboard and search form for portal users
      if (isPortalUser(context)) {
        return (executeCommandSearchPre(context));
      }
      return (executeCommandSearchFormPre(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");
    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
        "AccountsCalendarInfo");
    if (calendarInfo == null) {
      calendarInfo = new CalendarBean(
          this.getUser(context, this.getUserId(context)).getLocale());
      calendarInfo.addAlertType(
          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
      calendarInfo.setCalendarDetailsView("Accounts");
      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
    }

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.
    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList newUserList = thisRec.getFullChildList(
        shortChildList, new UserList());

    newUserList.setMyId(getUserId(context));
    newUserList.setMyValue(
        thisUser.getUserRecord().getContact().getNameLastFirst());
    newUserList.setIncludeMe(true);

    newUserList.setJsEvent(
        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
        "userId", getUserId(context));
    userListSelect.addAttribute("id", "userId");
    context.getRequest().setAttribute("Return", "Accounts");
    context.getRequest().setAttribute("NewUserList", userListSelect);
    return ("DashboardPreOK");
  }
  
  public String executeCommandDashboard(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	      if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearchPre(context));
	      }
	      return (executeCommandSearchFormPre(context));
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	    if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    //this is how we get the multiple-level heirarchy...recursive function.
	    User thisRec = thisUser.getUserRecord();

	    UserList shortChildList = thisRec.getShortChildList();
	    UserList newUserList = thisRec.getFullChildList(
	        shortChildList, new UserList());

	    newUserList.setMyId(getUserId(context));
	    newUserList.setMyValue(
	        thisUser.getUserRecord().getContact().getNameLastFirst());
	    newUserList.setIncludeMe(true);

	    newUserList.setJsEvent(
	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
	        "userId", getUserId(context));
	    userListSelect.addAttribute("id", "userId");
	    context.getRequest().setAttribute("Return", "Accounts");
	    context.getRequest().setAttribute("NewUserList", userListSelect);
	    return ("DashboardOK");
	  }
  
  public String executeCommandDashboardFcie(ActionContext context) {
	    if (!hasPermission(context, "accounts-dashboard-view")) {
	      if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearchFcie(context));
	      }
	      return (executeCommandSearchFormFcie(context));
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	    if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    //this is how we get the multiple-level heirarchy...recursive function.
	    User thisRec = thisUser.getUserRecord();

	    UserList shortChildList = thisRec.getShortChildList();
	    UserList newUserList = thisRec.getFullChildList(
	        shortChildList, new UserList());

	    newUserList.setMyId(getUserId(context));
	    newUserList.setMyValue(
	        thisUser.getUserRecord().getContact().getNameLastFirst());
	    newUserList.setIncludeMe(true);

	    newUserList.setJsEvent(
	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
	        "userId", getUserId(context));
	    userListSelect.addAttribute("id", "userId");
	    context.getRequest().setAttribute("Return", "Accounts");
	    context.getRequest().setAttribute("NewUserList", userListSelect);
	    return ("DashboardOK");
	  }
  
  public String executeCommandDashboardFar(ActionContext context) {
	    if (!hasPermission(context, "accounts-dashboard-view")) {
	      if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-far-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearchFar(context));
	      }
	      return (executeCommandSearchFormFar(context));
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	    if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    //this is how we get the multiple-level heirarchy...recursive function.
	    User thisRec = thisUser.getUserRecord();

	    UserList shortChildList = thisRec.getShortChildList();
	    UserList newUserList = thisRec.getFullChildList(
	        shortChildList, new UserList());

	    newUserList.setMyId(getUserId(context));
	    newUserList.setMyValue(
	        thisUser.getUserRecord().getContact().getNameLastFirst());
	    newUserList.setIncludeMe(true);

	    newUserList.setJsEvent(
	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
	        "userId", getUserId(context));
	    userListSelect.addAttribute("id", "userId");
	    context.getRequest().setAttribute("Return", "Accounts");
	    context.getRequest().setAttribute("NewUserList", userListSelect);
	    return ("DashboardOK");
	  }

  public String executeCommandDashboardVet(ActionContext context) {
	    if (!hasPermission(context, "accounts-dashboard-view")) {
	      if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearchVet(context));
	      }
	      return (executeCommandSearchFormVet(context));
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	    if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    //this is how we get the multiple-level heirarchy...recursive function.
	    User thisRec = thisUser.getUserRecord();

	    UserList shortChildList = thisRec.getShortChildList();
	    UserList newUserList = thisRec.getFullChildList(
	        shortChildList, new UserList());

	    newUserList.setMyId(getUserId(context));
	    newUserList.setMyValue(
	        thisUser.getUserRecord().getContact().getNameLastFirst());
	    newUserList.setIncludeMe(true);

	    newUserList.setJsEvent(
	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
	        "userId", getUserId(context));
	    userListSelect.addAttribute("id", "userId");
	    context.getRequest().setAttribute("Return", "Accounts");
	    context.getRequest().setAttribute("NewUserList", userListSelect);
	    return ("DashboardOK");
	  }
  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearchPre(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-view")) {
	      return ("PermissionError");
	    }

	    String source = (String) context.getRequest().getParameter("source");
	    PrescrizioniList organizationList = new PrescrizioniList();
	    
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfoFcie");
	    searchListInfo.setLink("FarmacosorveglianzaPre.do?command=SearchPre");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "--Tutti--");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	      if (isPortalUser(context)) {
	        organizationList.setOrgSiteId(this.getUserSiteId(context));
	        source = "searchForm";
	      }
	      //return if no criteria is selected
	      if ((searchListInfo.getListView() == null || "".equals(
	          searchListInfo.getListView())) && !"searchForm".equals(source)) {
	        return "ListPreOK";
	      }
	      
	      //Display list of accounts if user chooses not to list contacts
	      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
	        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
	          this.deletePagedListInfo(context, "SearchOrgListInfoFcie");
	          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfoFcie");
	          searchListInfo.setLink("FarmacosorveglianzaPre.do?command=SearchPre");
	        }
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        organizationList.setMinerOnly(false);
	        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
	        organizationList.setDataPrescrizione1(context.getRequest().getParameter("searchAccountName1"));
	       	organizationList.setDataPrescrizione2(context.getRequest().getParameter("searchAccountName2"));
	       	if((context.getRequest().getParameter("searchAccountNumber")==null)){
	       		organizationList.setIdVeterinario("-1");   	
	       	}else{
	        organizationList.setIdVeterinario(context.getRequest().getParameter("searchAccountNumber"));
	       	}
	       	if((context.getRequest().getParameter("searchcodeOrgSiteId")==null)){
	       		organizationList.setSiteId("-1");   	
	       	}else{
	        organizationList.setSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
	       	}
	        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
	        
	        searchListInfo.setSearchCriteria(organizationList, context);
	        //fetching criterea for account source (my accounts or all accounts)
	        if ("my".equals(searchListInfo.getListView())) {
	          organizationList.setOwnerId(this.getUserId(context));
	        }
	        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	        } else if (organizationList.getOrgSiteId() == -1) {
	          organizationList.setIncludeOrganizationWithoutSite(true);
	        }
	        //fetching criterea for account status (active, disabled or any)
	        int enabled = searchListInfo.getFilterKey("listFilter2");
	        organizationList.setIncludeEnabled(enabled);
	        //If the user is a portal user, fetching only the
	        //the organization that he access to
	        //(i.e., the organization for which he is an account contact
	        if (isPortalUser(context)) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	          
	        }
	        organizationList.buildList(db);
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        return "ListPreOK";
	      } else {}
	    } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }return "" ;
	  }
  
  
  
  public String executeCommandSearchFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-view")) {
	      return ("PermissionError");
	    }

	    /*if(!context.getCommand().equals("SearchFcie")){
	    	this.deletePagedListInfo(context, "SearchOrgListInfoFcie");
	    }*/
	    
	    String source = (String) context.getRequest().getParameter("source");
	    OrganizationList organizationList = new OrganizationList();
	    
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfoFcie");
	    searchListInfo.setLink("Farmacosorveglianza.do?command=SearchFcie");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      Organization newOrg = new Organization();
	      newOrg.setComuni2(db);
	            
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	      if (isPortalUser(context)) {
	        organizationList.setOrgSiteId(this.getUserSiteId(context));
	        source = "searchForm";
	      }
	      //return if no criteria is selected
	      if ((searchListInfo.getListView() == null || "".equals(
	          searchListInfo.getListView())) && !"searchForm".equals(source)) {
	        return "ListFcieOK";
	      }
	      
	      //Display list of accounts if user chooses not to list contacts
	      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
	        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
	          this.deletePagedListInfo(context, "SearchOrgListInfoFcie");
	          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfoFcie");
	          searchListInfo.setLink("Farmacosorveglianza.do?command=SearchFcie");
	        }
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        organizationList.setMinerOnly(false);
	        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
	        organizationList.setRagioneSociale(context.getRequest().getParameter("searchAccountName"));
	        organizationList.setCitta(context.getRequest().getParameter("searchAccountName2"));
	        if((context.getRequest().getParameter("searchcodeOrgSiteId")==null)){
	       		organizationList.setSiteId("-1");   	
	       	}else{
	        organizationList.setSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
	       	}
	        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
	        
	        searchListInfo.setSearchCriteria(organizationList, context);
	        //fetching criterea for account source (my accounts or all accounts)
	        if ("my".equals(searchListInfo.getListView())) {
	          organizationList.setOwnerId(this.getUserId(context));
	        }
	        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	        } else if (organizationList.getOrgSiteId() == -1) {
	          organizationList.setIncludeOrganizationWithoutSite(true);
	        }
	        //fetching criterea for account status (active, disabled or any)
	        int enabled = searchListInfo.getFilterKey("listFilter2");
	        organizationList.setIncludeEnabled(enabled);
	        //If the user is a portal user, fetching only the
	        //the organization that he access to
	        //(i.e., the organization for which he is an account contact
	        if (isPortalUser(context)) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	          
	        }
	        organizationList.buildList(db);
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        return "ListFcieOK";
	      } else {}
	    } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }return "" ;
	  }
  

  public String executeCommandSearchVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-view")) {
	      return ("PermissionError");
	    }

	    String source = (String) context.getRequest().getParameter("source");
	    VeterinariList organizationList = new VeterinariList();
	    
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfoFcie");
	    searchListInfo.setLink("FarmacosorveglianzaVet.do?command=SearchVet");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	      if (isPortalUser(context)) {
		        organizationList.setOrgSiteId(this.getUserSiteId(context));
		        source = "searchForm";
		      }
		      //return if no criteria is selected
		      if ((searchListInfo.getListView() == null || "".equals(
		          searchListInfo.getListView())) && !"searchForm".equals(source)) {
		        return "ListVetOK";
		      }
		      
		      //Display list of accounts if user chooses not to list contacts
		      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
		        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
		          this.deletePagedListInfo(context, "SearchOrgListInfoFcie");
		          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfoFcie");
		          searchListInfo.setLink("FarmacosorveglianzaVet.do?command=SearchVet");
		        }
		        //Build the organization list
		        organizationList.setPagedListInfo(searchListInfo);
		        organizationList.setMinerOnly(false);
		        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
		        organizationList.setCognome(context.getRequest().getParameter("searchAccountName"));
		        organizationList.setCitta(context.getRequest().getParameter("searchAccountNumber"));
		        
		        if((context.getRequest().getParameter("searchcodeOrgSiteId")==null)||(context.getRequest().getParameter("searchcodeOrgSiteId").equals("-1"))){
		       		organizationList.setSiteId("-1");   	
		       	}else{
		       		organizationList.setSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
		       	}
		        
		        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
		        
		        searchListInfo.setSearchCriteria(organizationList, context);
		        //fetching criterea for account source (my accounts or all accounts)
		        if ("my".equals(searchListInfo.getListView())) {
		          organizationList.setOwnerId(this.getUserId(context));
		        }
		        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
		          organizationList.setOrgSiteId(this.getUserSiteId(context));
		          organizationList.setIncludeOrganizationWithoutSite(false);
		        } else if (organizationList.getOrgSiteId() == -1) {
		          organizationList.setIncludeOrganizationWithoutSite(true);
		        }
		        //fetching criterea for account status (active, disabled or any)
		        int enabled = searchListInfo.getFilterKey("listFilter2");
		        organizationList.setIncludeEnabled(enabled);
		        //If the user is a portal user, fetching only the
		        //the organization that he access to
		        //(i.e., the organization for which he is an account contact
		        if (isPortalUser(context)) {
		          organizationList.setOrgSiteId(this.getUserSiteId(context));
		          organizationList.setIncludeOrganizationWithoutSite(false);
		          
		        }
		        organizationList.buildList(db);
		        context.getRequest().setAttribute("OrgList", organizationList);
		        context.getSession().setAttribute("previousSearchType", "accounts");
		        
		        return "ListVetOK";
		      } else {}
	    } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }return "" ;
	  }
  
  public String executeCommandSearchFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-far-view")) {
	      return ("PermissionError");
	    }

	    String source = (String) context.getRequest().getParameter("source");
	    FarmaciList organizationList = new FarmaciList();
	    
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfoFcie");
	    searchListInfo.setLink("FarmacosorveglianzaFar.do?command=SearchFar");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {

		      db = this.getConnection(context);
		      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
		      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
		      
		      LookupList idConfezione = new LookupList(db, "lookup_confezione");
		      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("idConfezione", idConfezione);
		      
		      if (isPortalUser(context)) {
		        organizationList.setOrgSiteId(this.getUserSiteId(context));
		        source = "searchForm";
		      }
		      //return if no criteria is selected
		      if ((searchListInfo.getListView() == null || "".equals(
		          searchListInfo.getListView())) && !"searchForm".equals(source)) {
		        return "ListFarOK";
		      }
		      
		      //Display list of accounts if user chooses not to list contacts
		      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
		        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
		          this.deletePagedListInfo(context, "SearchOrgListInfoFcie");
		          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfoFcie");
		          searchListInfo.setLink("FarmacosorveglianzaFar.do?command=SearchFar");
		        }
		        //Build the organization list
		        organizationList.setPagedListInfo(searchListInfo);
		        organizationList.setMinerOnly(false);
		        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
		        organizationList.setNomeCommerciale(context.getRequest().getParameter("searchAccountName"));
		        if((context.getRequest().getParameter("searchcodeOrgSiteId")==null)||(context.getRequest().getParameter("searchcodeOrgSiteId").equals("-1"))){
		       		organizationList.setIdConfezione("-1");   	
		       	}else{
		       		organizationList.setIdConfezione(context.getRequest().getParameter("searchcodeOrgSiteId"));
		       	}
		        
		        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
		        
 		        searchListInfo.setSearchCriteria(organizationList, context);
		        //fetching criterea for account source (my accounts or all accounts)
		        if ("my".equals(searchListInfo.getListView())) {
		          organizationList.setOwnerId(this.getUserId(context));
		        }
		        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
		          organizationList.setOrgSiteId(this.getUserSiteId(context));
		          organizationList.setIncludeOrganizationWithoutSite(false);
		        } else if (organizationList.getOrgSiteId() == -1) {
		          organizationList.setIncludeOrganizationWithoutSite(true);
		        }
		        //fetching criterea for account status (active, disabled or any)
		        int enabled = searchListInfo.getFilterKey("listFilter2");
		        organizationList.setIncludeEnabled(enabled);
		        //If the user is a portal user, fetching only the
		        //the organization that he access to
		        //(i.e., the organization for which he is an account contact
		        if (isPortalUser(context)) {
		          organizationList.setOrgSiteId(this.getUserSiteId(context));
		          organizationList.setIncludeOrganizationWithoutSite(false);
		          
		        }
		        organizationList.buildList(db);
		        context.getRequest().setAttribute("OrgList", organizationList);
		        context.getSession().setAttribute("previousSearchType", "accounts");
		        
		        return "ListFarOK";
		      } else {}
	    } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }return "" ;
	  }


  
  public String executeCommandInsertFcie(ActionContext context) throws SQLException {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Organization insertedOrg = null;

	    
	    Organization newOrg = (Organization) context.getFormBean();
	   
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setName(context.getRequest().getParameter("ragioneSociale"));
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newOrg.setIp_entered(ip);
	    newOrg.setIp_modified(ip);
	    //if(context.getRequest().getParameter("dataRicDettaglio")!=null)
	    newOrg.setDataRicDettaglio(context.getRequest().getParameter("dataRicDettaglio"));
	    //if(context.getRequest().getParameter("dataRicIngrosso")!=null)
		newOrg.setDataRicIngrosso(context.getRequest().getParameter("dataRicIngrosso"));
	     try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      newOrg.setComuni2(db);
	      newOrg.setRequestItems(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      if (this.getUserSiteId(context) != -1) {
	        // Set the site Id of the account to be equal to the site Id of the user
	        // for a new account
	        if (newOrg.getId() == -1) {
	          newOrg.setSiteId(this.getUserSiteId(context));
	        } else {
	          // Check whether the user has access to update the organization
	          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	            return ("PermissionError");
	          }
	        }
	      }
	      
	      newOrg.setEntered(new Timestamp (new Date(System.currentTimeMillis()).getTime()));

	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	     
	      newOrg.setEnteredBy(user.getUserId());
	      newOrg.setModifiedBy(user.getUserId());

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Organization(db, newOrg.getOrgId());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        addRecentItem(context, newOrg);
	        
	         
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Farmacia Insert ok");
	    if (recordInserted) {
	      String target = context.getRequest().getParameter("target");
	      if (context.getRequest().getParameter("popup") != null) {
	        return ("ClosePopupOK");
	      }
	      if (target != null && "add_contact".equals(target)) {
	        return ("InsertAndAddContactOK");
	      } else {
	        return ("InsertFcieOK");
	      }
	    }
	    return (executeCommandAddFcie(context));
	  }
  
  public String executeCommandInsertFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-far-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Farmaci insertedOrg = null;

	    Farmaci newOrg = (Farmaci) context.getFormBean();
	    
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    SystemStatus systemStatus = this.getSystemStatus(context);

	    try {
	      db = this.getConnection(context);
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      
	      if (this.getUserSiteId(context) != -1) {
	        // Set the site Id of the account to be equal to the site Id of the user
	        // for a new account
	        if (newOrg.getId() == -1) {
	          //newOrg.setSiteId(this.getUserSiteId(context));
	        } else {
	          // Check whether the user has access to update the organization
	          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	            return ("PermissionError");
	          }
	        }
	      }

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Farmaci(db, newOrg.getIdFarmaco());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        addRecentItem(context, newOrg);
	        
	           
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Farmacia Insert ok");
	    if (recordInserted) {
	      String target = context.getRequest().getParameter("target");
	      if (context.getRequest().getParameter("popup") != null) {
	        return ("ClosePopupOK");
	      }
	      if (target != null && "add_contact".equals(target)) {
	        return ("InsertAndAddContactOK");
	      } else {
	        return ("InsertFarOK");
	      }
	    }
	    return (executeCommandAddFcie(context));
	  }
  
  public String executeCommandInsertVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-vet-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;

	    boolean recordInserted = false;
	    boolean isValid = false;
	    Veterinari insertedOrg = null;

	    Veterinari newOrg = (Veterinari) context.getFormBean();
	    
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    SystemStatus systemStatus = this.getSystemStatus(context);

	    try {
	      db = this.getConnection(context);
	
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      if (this.getUserSiteId(context) != -1) {
	        // Set the site Id of the account to be equal to the site Id of the user
	        // for a new account
	        if (newOrg.getId() == -1) {
	          //newOrg.setSiteId(this.getUserSiteId(context));
	        } else {
	          // Check whether the user has access to update the organization
	          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	            return ("PermissionError");
	          }
	        }
	      }

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Veterinari(db, newOrg.getIdVeterinario());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        addRecentItem(context, newOrg);
	        
	        
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Veterinario Insert ok");
	    if (recordInserted) {
	      String target = context.getRequest().getParameter("target");
	      if (context.getRequest().getParameter("popup") != null) {
	        return ("ClosePopupOK");
	      }
	      if (target != null && "add_contact".equals(target)) {
	        return ("InsertAndAddContactOK");
	      } else {
	        return ("InsertVetOK");
	      }
	    }
	    return (executeCommandAddFcie(context));
	  }

  public String executeCommandInsertPre(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-pre-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;

	    boolean recordInserted = false;
	    boolean isValid = false;
	    Prescrizioni insertedOrg = null;

	    Prescrizioni newOrg = (Prescrizioni) context.getFormBean();
	    
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    SystemStatus systemStatus = this.getSystemStatus(context);

	    try {
	      db = this.getConnection(context);
	
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      if (this.getUserSiteId(context) != -1) {
	        // Set the site Id of the account to be equal to the site Id of the user
	        // for a new account
	        if (newOrg.getId() == -1) {
	          //newOrg.setSiteId(this.getUserSiteId(context));
	        } else {
	          // Check whether the user has access to update the organization
	          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	            return ("PermissionError");
	          }
	        }
	      }

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Prescrizioni(db, newOrg.getIdPrescrizione());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        addRecentItem(context, newOrg);
	        Integer idPre = insertedOrg.getIdPrescrizione();
	        String PrescrizioneID = idPre.toString();
	        
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Prescrizione Insert ok");
	    if (recordInserted) {
	      String target = context.getRequest().getParameter("target");
	      if (context.getRequest().getParameter("popup") != null) {
	        return ("ClosePopupOK");
	      }
	      if (target != null && "add_contact".equals(target)) {
	        return ("InsertAndAddContactOK");
	      } else {
	        return ("InsertPreOK");
	      }
	    }
	    return (executeCommandAddPre(context));
	  }

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdatePre2(ActionContext context) {
    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Prescrizioni newOrg = (Prescrizioni) context.getFormBean();
    Prescrizioni oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
      
 
      oldOrg = new Prescrizioni(db, newOrg.getIdPrescrizione());
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        resultCount = newOrg.update(db);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
            "individual")) {
         
        }
        //update all contacts which are associated with this organization

        }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    if (resultCount == -1 || !isValid) {
      return (executeCommandModifyPre(context));
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandSearchPre(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("dashboard")) {
        return (executeCommandDashboardPre(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("Calendar")) {
        if (context.getRequest().getParameter("popup") != null) {
          return ("PopupCloseOK");
        }
      } else {
        return ("UpdatePreOK");
      }
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
    return ("UpdatePreOK");
  }
  
  public String executeCommandUpdateVet(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    Veterinari newOrg = (Veterinari) context.getFormBean();
	    Veterinari oldOrg = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setEnteredBy(getUserId(context));
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	      oldOrg = new Veterinari(db, newOrg.getIdVeterinario());
	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        resultCount = newOrg.update(db);
	      //  String prova = context.getRequest().getParameter("address1state");
	       // newOrg.setState(prova);
	      }
	      if (resultCount == 1) {
	        processUpdateHook(context, oldOrg, newOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	     }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Modify Account");
	    if (resultCount == -1 || !isValid) {
	      return (executeCommandModifyVet(context));
	    } else if (resultCount == 1) {
	      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("list")) {
	        return (executeCommandSearchVet(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("dashboard")) {
	        return (executeCommandDashboard(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("Calendar")) {
	        if (context.getRequest().getParameter("popup") != null) {
	          return ("PopupCloseOK");
	        }
	      } else {
	        return ("UpdateVetOK");
	      }
	    } else {
	      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	      return ("UserError");
	    }
	    return ("UpdateVetOK");
	  }

  public String executeCommandUpdateFar(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    Farmaci newOrg = (Farmaci) context.getFormBean();
	    Farmaci oldOrg = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setEnteredBy(getUserId(context));
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	      
	      oldOrg = new Farmaci(db, newOrg.getIdFarmaco());
	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        resultCount = newOrg.update(db);
	      //  String prova = context.getRequest().getParameter("address1state");
	       // newOrg.setState(prova);
	      }
	      if (resultCount == 1) {
	        processUpdateHook(context, oldOrg, newOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	       }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Modify Account");
	    if (resultCount == -1 || !isValid) {
	      return (executeCommandModifyVet(context));
	    } else if (resultCount == 1) {
	      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("list")) {
	        return (executeCommandSearchVet(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("dashboard")) {
	        return (executeCommandDashboard(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("Calendar")) {
	        if (context.getRequest().getParameter("popup") != null) {
	          return ("PopupCloseOK");
	        }
	      } else {
	        return ("UpdateFarOK");
	      }
	    } else {
	      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	      return ("UserError");
	    }
	    return ("UpdateFarOK");
	  }
  
  public String executeCommandUpdatePre(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    Prescrizioni newOrg = (Prescrizioni) context.getFormBean();
	    Prescrizioni oldOrg = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setEnteredBy(getUserId(context));
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	      
	      oldOrg = new Prescrizioni(db, newOrg.getIdPrescrizione());
	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        resultCount = newOrg.update(db);
	      //  String prova = context.getRequest().getParameter("address1state");
	       // newOrg.setState(prova);
	      }
	      if (resultCount == 1) {
	        processUpdateHook(context, oldOrg, newOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	       }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Modify Account");
	    if (resultCount == -1 || !isValid) {
	      return (executeCommandModifyVet(context));
	    } else if (resultCount == 1) {
	      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("list")) {
	        return (executeCommandSearchVet(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("dashboard")) {
	        return (executeCommandDashboard(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("Calendar")) {
	        if (context.getRequest().getParameter("popup") != null) {
	          return ("PopupCloseOK");
	        }
	      } else {
	        return ("UpdatePreOK");
	      }
	    } else {
	      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	      return ("UserError");
	    }
	    return ("UpdatePreOK");
	  }
  
  public String executeCommandUpdateFcie(ActionContext context) {
	    if (!(hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    Organization newOrg = (Organization) context.getFormBean();
	    Organization oldOrg = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    newOrg.setModifiedBy(getUserId(context));
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newOrg.setIp_entered(ip);
	    newOrg.setIp_modified(ip);
	    newOrg.setEnteredBy(getUserId(context));
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	      
	      oldOrg = new Organization(db, newOrg.getIdFarmacia());
	      newOrg.setRequestItems(context);
	    

	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	   
	     
	      newOrg.setModifiedBy(user.getUserId());
	      
	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        resultCount = newOrg.update(db,context);
	      //  String prova = context.getRequest().getParameter("address1state");
	       // newOrg.setState(prova);
	      }
	      if (resultCount == 1) {
	        processUpdateHook(context, oldOrg, newOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	        //update all contacts which are associated with this organization
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      errorMessage.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Modify Account");
	    if (resultCount == -1 || !isValid) {
	      return (executeCommandModifyVet(context));
	    } else if (resultCount == 1) {
	      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("list")) {
	        return (executeCommandSearchVet(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("dashboard")) {
	        return (executeCommandDashboard(context));
	      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
	          "return").equals("Calendar")) {
	        if (context.getRequest().getParameter("popup") != null) {
	          return ("PopupCloseOK");
	        }
	      } else {
	        return ("UpdateFcieOK");
	      }
	    } else {
	      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	      return ("UserError");
	    }
	    return ("UpdateFcieOK");
	  }
  
  
  /**
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeletePre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Prescrizioni thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Prescrizioni(
          db, Integer.parseInt(context.getRequest().getParameter("idPrescrizione")));
      //check permission to record
      
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          // NOTE: these may have different options later
          
          OpportunityHeaderList opportunityList = new OpportunityHeaderList();
          opportunityList.setOrgId(thisOrganization.getOrgId());
          opportunityList.buildList(db);
          opportunityList.invalidateUserData(context, db);
          
          recordDeleted = thisOrganization.delete(
              db, context, getDbNamePath(context));
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          recordDeleted = thisOrganization.disable(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        
        context.getRequest().setAttribute(
            "refreshUrl", "FarmacosorveglianzaPre.do?command=SearchPre");
        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
            context.getRequest().getParameter("return"))) {
          return executeCommandSearchPre(context);
        }
        return "DeletePreOK";
      } else {
        processErrors(context, thisOrganization.getErrors());
        return (executeCommandSearchPre(context));
      }
    } else {
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.accountDeletion"));
      context.getRequest().setAttribute(
          "refreshUrl", "FarmacosorveglianzaPre.do?command=SearchPre");
      return ("DeleteError");
    }
  }
  
  public String executeCommandDeleteFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    boolean recordDeleted = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Farmaci(
	          db, Integer.parseInt(context.getRequest().getParameter("idFarmacia")));
	      //check permission to record
	      
	      if (context.getRequest().getParameter("action") != null) {
	        if (((String) context.getRequest().getParameter("action")).equals(
	            "delete")) {
	          // NOTE: these may have different options later
	          
	         recordDeleted = thisOrganization.delete(
	              db, context, getDbNamePath(context));
	        } else if (((String) context.getRequest().getParameter("action")).equals(
	            "disable")) {
	          recordDeleted = thisOrganization.disable(db);
	        }
	      }
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (errorMessage == null) {
	      if (recordDeleted) {
	        
	        context.getRequest().setAttribute(
	            "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
	            context.getRequest().getParameter("return"))) {
	          return executeCommandSearchFcie(context);
	        }
	        return "DeleteFcieOK";
	      } else {
	        processErrors(context, thisOrganization.getErrors());
	        return (executeCommandSearchFcie(context));
	      }
	    } else {
	      context.getRequest().setAttribute(
	          "actionError", systemStatus.getLabel(
	          "object.validation.actionError.accountDeletion"));
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	      return ("DeleteError");
	    }
	  }

  
  public String executeCommandDeleteFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    boolean recordDeleted = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Farmaci(
	          db, Integer.parseInt(context.getRequest().getParameter("idFarmaco")));
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisOrganization.getIdFarmaco())) {
	        return ("PermissionError");
	      }
	      if (context.getRequest().getParameter("action") != null) {
	        if (((String) context.getRequest().getParameter("action")).equals(
	            "delete")) {
	          // NOTE: these may have different options later
	          
	          recordDeleted = thisOrganization.delete(
	              db, context, getDbNamePath(context));
	        } else if (((String) context.getRequest().getParameter("action")).equals(
	            "disable")) {
	          recordDeleted = thisOrganization.disable(db);
	        }
	      }
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (errorMessage == null) {
	      if (recordDeleted) {
	        
	        context.getRequest().setAttribute(
	            "refreshUrl", "Farmacosorveglianza.do?command=SearchFar");
	        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
	            context.getRequest().getParameter("return"))) {
	          return executeCommandSearchFar(context);
	        }
	        return "DeleteFarOK";
	      } else {
	        processErrors(context, thisOrganization.getErrors());
	        return (executeCommandSearchFar(context));
	      }
	    } else {
	      context.getRequest().setAttribute(
	          "actionError", systemStatus.getLabel(
	          "object.validation.actionError.accountDeletion"));
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFar");
	      return ("DeleteError");
	    }
	  }

  
  public String executeCommandDeleteVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    boolean recordDeleted = false;
	    Veterinari thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Veterinari(
	          db, Integer.parseInt(context.getRequest().getParameter("idVeterinario")));
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisOrganization.getIdVeterinario())) {
	        return ("PermissionError");
	      }
	      if (context.getRequest().getParameter("action") != null) {
	        if (((String) context.getRequest().getParameter("action")).equals(
	            "delete")) {
	          // NOTE: these may have different options later
	         
	          recordDeleted = thisOrganization.delete(
	              db, context, getDbNamePath(context));
	        } else if (((String) context.getRequest().getParameter("action")).equals(
	            "disable")) {
	          recordDeleted = thisOrganization.disable(db);
	        }
	      }
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (errorMessage == null) {
	      if (recordDeleted) {
	        
	        context.getRequest().setAttribute(
	            "refreshUrl", "Farmacosorveglianza.do?command=SearchVet");
	        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
	            context.getRequest().getParameter("return"))) {
	          return executeCommandSearchVet(context);
	        }
	        return "DeleteVetOK";
	      } else {
	        processErrors(context, thisOrganization.getErrors());
	        return (executeCommandSearchVet(context));
	      }
	    } else {
	      context.getRequest().setAttribute(
	          "actionError", systemStatus.getLabel(
	          "object.validation.actionError.accountDeletion"));
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchVet");
	      return ("DeleteError");
	    }
	  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrashPre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Prescrizioni thisOrganization = null;
    Prescrizioni oldOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String orgId = context.getRequest().getParameter("idPrescrizione");
      //check permission to record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      thisOrganization = new Prescrizioni(db, Integer.parseInt(orgId));
      oldOrganization = new Prescrizioni(db, Integer.parseInt(orgId));
      // NOTE: these may have different options later
      
      this.invalidateUserData(context, this.getUserId(context));
     
      if (recordUpdated) {
        
        
      
      }
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "Farmacosorveglianza.do?command=SearchPre");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Farmacosorveglianza.do?command=SearchPre");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearchPre(context);
      }
      return "DeletePreOK";
    } else {
      return (executeCommandSearchPre(context));
    }
  }
  
  public String executeCommandTrashFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Organization thisOrganization = null;
	    Organization oldOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idFarmacia");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Organization(db, Integer.parseInt(orgId));
	      oldOrganization = new Organization(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      recordUpdated = thisOrganization.updateStatus(
	              db, context, true, this.getUserId(context));
	          this.invalidateUserData(context, this.getUserId(context));
	          if (recordUpdated) {
	            
	           
	            
	          }
	        } catch (Exception e) {
	        	 
	          context.getRequest().setAttribute(
	              "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	          context.getRequest().setAttribute("Error", e);
	          return ("SystemError");
	        } finally {
	          this.freeConnection(context, db);
	        }
	        addModuleBean(context, "Accounts", "Delete Account");
	        if (recordUpdated) {
	        	context.getRequest().setAttribute(
	      	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	      	      if ("list".equals(context.getRequest().getParameter("return"))) {
	      	        return executeCommandSearchFcie(context);
	      	      }
	      	      return "DeleteFcieOK";
	      	    } else {
	      	      return (executeCommandSearchFcie(context));
	      	    }
	      	  }
	     

  
  public String executeCommandTrashFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Farmaci thisOrganization = null;
	    Farmaci oldOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idFarmaco");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Farmaci(db, Integer.parseInt(orgId));
	      oldOrganization = new Farmaci(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	      if (recordUpdated) {
	        
	        
	  
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFar");
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFar");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchFar(context);
	      }
	      return "DeleteFarOK";
	    } else {
	      return (executeCommandSearchFar(context));
	    }
	  }


  public String executeCommandTrashVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Veterinari thisOrganization = null;
	    Veterinari oldOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idVeterinario");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Veterinari(db, Integer.parseInt(orgId));
	      oldOrganization = new Veterinari(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	      if (recordUpdated) {
	        
	        
	   
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchVet");
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchVet");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchVet(context);
	      }
	      return "DeleteVetOK";
	    } else {
	      return (executeCommandSearchVet(context));
	    }
	  }

  
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestorePre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Prescrizioni thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String orgId = context.getRequest().getParameter("idPrescrizione");
      //check permission to record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      thisOrganization = new Prescrizioni(db, Integer.parseInt(orgId));
      // NOTE: these may have different options later
      
      this.invalidateUserData(context, this.getUserId(context));
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Farmacosorveglianza.do?command=SearchPre");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearchPre(context);
      }
      return this.executeCommandDetailsPre(context);
    } else {
      return (executeCommandSearchPre(context));
    }
  }

  public String executeCommandRestoreVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Veterinari thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idVeterinario");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Veterinari(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchVet");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchVet(context);
	      }
	      return this.executeCommandDetailsVet(context);
	    } else {
	      return (executeCommandSearchVet(context));
	    }
	  }
  
  public String executeCommandRestoreFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idFarmacia");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Farmaci(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=Search");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchFcie(context);
	      }
	      return this.executeCommandDetailsFcie(context);
	    } else {
	      return (executeCommandSearchFcie(context));
	    }
	  }
  
  public String executeCommandRestoreFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("idFarmaco");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Farmaci(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFar");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchFar(context);
	      }
	      return this.executeCommandDetailsFar(context);
	    } else {
	      return (executeCommandSearchFar(context));
	    }
	  }

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnablePre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    Prescrizioni thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Prescrizioni(
          db, Integer.parseInt(context.getRequest().getParameter("idPrescrizione")));
      recordEnabled = thisOrganization.enable(db);
      if (!recordEnabled) {
        this.validateObject(context, db, thisOrganization);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    return (executeCommandSearchPre(context));
  }
  
  public String executeCommandEnableVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    boolean recordEnabled = false;
	    Veterinari thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Veterinari(
	          db, Integer.parseInt(context.getRequest().getParameter("idVeterinario")));
	      recordEnabled = thisOrganization.enable(db);
	      if (!recordEnabled) {
	        this.validateObject(context, db, thisOrganization);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    return (executeCommandSearchVet(context));
	  }

  public String executeCommandEnableFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    boolean recordEnabled = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Farmaci(
	          db, Integer.parseInt(context.getRequest().getParameter("idFarmaco")));
	      recordEnabled = thisOrganization.enable(db);
	      if (!recordEnabled) {
	        this.validateObject(context, db, thisOrganization);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    return (executeCommandSearchFar(context));
	  }
  
  public String executeCommandEnableFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    boolean recordEnabled = false;
	    Farmaci thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Farmaci(
	          db, Integer.parseInt(context.getRequest().getParameter("idFarmacia")));
	      recordEnabled = thisOrganization.enable(db);
	      if (!recordEnabled) {
	        this.validateObject(context, db, thisOrganization);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    return (executeCommandSearchFcie(context));
	  }

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDeletePre(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    Prescrizioni thisOrg = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }
    try {
      db = this.getConnection(context);
      thisOrg = new Prescrizioni(db, Integer.parseInt(id));
      //check permission to record
      
      
      
      
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      htmlDialog.addButton(
          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Farmacosorveglianza.do?command=TrashPre&action=delete&idPrescrizione=" + thisOrg.getIdPrescrizione() + "&forceDelete=true" + "'");
     
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeletePreOK");
  }
  
  public String executeCommandConfirmDeleteFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Organization thisOrg = null;
	    HtmlDialog htmlDialog = new HtmlDialog();
	    String id = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    if (context.getRequest().getParameter("id") != null) {
	      id = context.getRequest().getParameter("id");
	    }
	    try {
	      db = this.getConnection(context);
	      thisOrg = new Organization(db, Integer.parseInt(id));
	      //check permission to record
	      
	      
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Farmacosorveglianza.do?command=TrashFcie&action=delete&idFarmacia=" + thisOrg.getOrgId() + "&forceDelete=true" + "'");
	      
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getSession().setAttribute("Dialog", htmlDialog);
	    return ("ConfirmDeleteFcieOK");
	  }

  public String executeCommandConfirmDeleteFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Farmaci thisOrg = null;
	    HtmlDialog htmlDialog = new HtmlDialog();
	    String id = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    if (context.getRequest().getParameter("id") != null) {
	      id = context.getRequest().getParameter("id");
	    }
	    try {
	      db = this.getConnection(context);
	      thisOrg = new Farmaci(db, Integer.parseInt(id));
	      //check permission to record
	      
	      
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Farmacosorveglianza.do?command=TrashFar&action=delete&idFarmaco=" + thisOrg.getIdFarmaco() + "&forceDelete=true" + "'");
	      
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getSession().setAttribute("Dialog", htmlDialog);
	    return ("ConfirmDeleteFarOK");
	  }

  public String executeCommandConfirmDeleteVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Veterinari thisOrg = null;
	    HtmlDialog htmlDialog = new HtmlDialog();
	    String id = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    if (context.getRequest().getParameter("id") != null) {
	      id = context.getRequest().getParameter("id");
	    }
	    try {
	      db = this.getConnection(context);
	      thisOrg = new Veterinari(db, Integer.parseInt(id));
	      //check permission to record
	      
	      
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Farmacosorveglianza.do?command=TrashVet&action=delete&idVeterinario=" + thisOrg.getIdVeterinario() + "&forceDelete=true" + "'");
	      
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getSession().setAttribute("Dialog", htmlDialog);
	    return ("ConfirmDeleteVetOK");
	  }



  /**
   *  Modify: Displays the form used for modifying the information of the
   *  currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandModifyPre2(ActionContext context) {
    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("idPrescrizione");
    context.getRequest().setAttribute("idPrescrizione", orgid);
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Prescrizioni newOrg = null;
    try {
      db = this.getConnection(context);
      newOrg = (Prescrizioni) context.getFormBean();
     
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute("systemStatus", systemStatus);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      

      //inserito da Francesco
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      

    

      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Account Modify");
    context.getRequest().setAttribute("OrgDetails", newOrg);
    if (context.getRequest().getParameter("popup") != null) {
      return ("PopupModifyOK");
    } else {
      return ("ModifyPreOK");
    }
  }
  
  public String executeCommandModifyFcie(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    String orgid = context.getRequest().getParameter("idFarmacia");
	    context.getRequest().setAttribute("idFarmacia", orgid);
	    int tempid = Integer.parseInt(orgid);
	    Connection db = null;
	    Organization newOrg = null;
	    try {
	      db = this.getConnection(context);
	    
	        newOrg = new Organization(db, tempid);
	       
	      
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      newOrg.setComuni2(db);
		   
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      CountrySelect countrySelect = new CountrySelect(systemStatus);
	      context.getRequest().setAttribute("CountrySelect", countrySelect);
	      context.getRequest().setAttribute("systemStatus", systemStatus);

	      /*UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
	      context.getRequest().setAttribute("UserList", userList);*/
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("OrgDetails", newOrg);
	    if (context.getRequest().getParameter("popup") != null) {
	      return ("PopupModifyOK");
	    } else {
	      return ("ModifyFcieOK");
	    }
	  }
  
  public String executeCommandModifyPre(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    String orgid = context.getRequest().getParameter("idPrescrizione");
	    context.getRequest().setAttribute("idPrescrizione", orgid);
	    int tempid = Integer.parseInt(orgid);
	    OrganizationList organizationList = new OrganizationList();
	    VeterinariList organizationList2 = new VeterinariList();
	    FarmaciList organizationList3 = new FarmaciList();
	    PrescrizioniList organizationList4 = new PrescrizioniList();
	    
	    Connection db = null;
	    Prescrizioni newOrg = null;
	    try {
	      db = this.getConnection(context);
	      newOrg = (Prescrizioni) context.getFormBean();
	      if (newOrg.getId() == -1) {
	        newOrg = new Prescrizioni(db, tempid);
	       
	      } else {
	        
	      }
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      
	      PreparedStatement pst = db.prepareStatement(
	        "SELECT * FROM farmacie");
	      PreparedStatement pst2 = db.prepareStatement(
	        "SELECT * FROM veterinari2");
	      PreparedStatement pst3 = db.prepareStatement(
	        "SELECT * FROM farmaci2");
	      PreparedStatement pst4 = db.prepareStatement(
	        "SELECT * FROM organization WHERE tipologia = 2");
	      	      
	      organizationList.buildList(db, pst);
	      organizationList2.buildList(db, pst2);
	      organizationList3.buildList(db, pst3);
	    //  organizationList4.buildList(db, pst4);
	      
	      context.getRequest().setAttribute("FarmacieList", organizationList);
	      context.getRequest().setAttribute("VeterinariList", organizationList2);
	      context.getRequest().setAttribute("FarmaciList", organizationList3);
	      context.getRequest().setAttribute("OrganizationList", organizationList4);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      CountrySelect countrySelect = new CountrySelect(systemStatus);
	      context.getRequest().setAttribute("CountrySelect", countrySelect);
	      context.getRequest().setAttribute("systemStatus", systemStatus);

	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("OrgDetails", newOrg);
	    if (context.getRequest().getParameter("popup") != null) {
	      return ("PopupModifyOK");
	    } else {
	      return ("ModifyPreOK");
	    }
	  }
  
  public String executeCommandModifyFar(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    String orgid = context.getRequest().getParameter("idFarmaco");
	    context.getRequest().setAttribute("idFarmaco", orgid);
	    int tempid = Integer.parseInt(orgid);
	    Connection db = null;
	    Farmaci newOrg = null;
	    try {
	      db = this.getConnection(context);
	      newOrg = (Farmaci) context.getFormBean();
	      if (newOrg.getId() == -1) {
	        newOrg = new Farmaci(db, tempid);
	       
	      } else {
	        
	      }
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      LookupList idTiposomministrazione = new LookupList(db, "lookup_tipo_somministrazione");
	      idTiposomministrazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idTiposomministrazione", idTiposomministrazione);
	      
	      LookupList idConfezione = new LookupList(db, "lookup_confezione");
	      idConfezione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("idConfezione", idConfezione);
	      
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      CountrySelect countrySelect = new CountrySelect(systemStatus);
	      context.getRequest().setAttribute("CountrySelect", countrySelect);
	      context.getRequest().setAttribute("systemStatus", systemStatus);

	      /*UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
	      context.getRequest().setAttribute("UserList", userList);*/
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("OrgDetails", newOrg);
	    if (context.getRequest().getParameter("popup") != null) {
	      return ("PopupModifyOK");
	    } else {
	      return ("ModifyFarOK");
	    }
	  }
  
  public String executeCommandModifyVet(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-edit")) {
	      return ("PermissionError");
	    }
	    String orgid = context.getRequest().getParameter("idVeterinario");
	    context.getRequest().setAttribute("idVeterinario", orgid);
	    int tempid = Integer.parseInt(orgid);
	    Connection db = null;
	    Veterinari newOrg = null;
	    try {
	      db = this.getConnection(context);
	      newOrg = (Veterinari) context.getFormBean();
	      if (newOrg.getId() == -1) {
	        newOrg = new Veterinari(db, tempid);
	       
	      } else {
	        
	      }
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      LookupList tipologiaId = new LookupList(db, "lookup_tipologia_veterinario");
	      tipologiaId.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipologiaList", tipologiaId);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      CountrySelect countrySelect = new CountrySelect(systemStatus);
	      context.getRequest().setAttribute("CountrySelect", countrySelect);
	      context.getRequest().setAttribute("systemStatus", systemStatus);

	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("OrgDetails", newOrg);
	    if (context.getRequest().getParameter("popup") != null) {
	      return ("PopupModifyOK");
	    } else {
	      return ("ModifyVetOK");
	    }
	  }
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "RptListInfo");
    this.deletePagedListInfo(context, "OpportunityPagedInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AutoGuideAccountInfo");
    this.deletePagedListInfo(context, "RevenueListInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
    this.deletePagedListInfo(context, "ServiceContractListInfo");
    this.deletePagedListInfo(context, "AssetListInfo");
    this.deletePagedListInfo(context, "AccountProjectInfo");
    this.deletePagedListInfo(context, "orgHistoryListInfo");
  }


  public String executeCommandPrintReport(ActionContext context) {
	    if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		try {
		  db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("id");
		  HashMap map = new HashMap();
		  map.put("orgid", new Integer(id));
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
		    if(filename.equals("modelloB.xml"))
		    	fileDownload.setDisplayName("ModelloB_DIA_" + id + ".pdf");
		    else if (filename.equals("modelloC.xml"))
		    	fileDownload.setDisplayName("ModelloC_DIA_" + id + ".pdf");
		    else
		    	fileDownload.setDisplayName("DettaglioOSA_" + id + ".pdf");
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
  
  public String executeCommandTrash(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Connection db = null;
	    try {
	        db = this.getConnection(context);
	        int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	        String note = context.getRequest().getParameter("note");

	        //check permission to record
	        if (!isRecordAccessPermitted(context, db, orgId)) {
	          return ("PermissionError");
	        }
	       
	        // NOTE: these may have different options later
	        recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
	      
	   
	      } catch (Exception e) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=SearchFcie");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearchFcie(context);
	      }
	      return "DeleteOK";
	    } else {
	      return (executeCommandSearchFcie(context));
	    }
	  }

public String executeCommandConfirmDelete(ActionContext context) {
	    if (!hasPermission(context, "farmacosorveglianza-farmacosorveglianza-delete")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    HtmlDialog htmlDialog = new HtmlDialog();
	    int orgId = -1;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    if (context.getRequest().getParameter("id") != null) {
	    	orgId = Integer.parseInt(context.getRequest().getParameter("id"));
	    	}
	    try {
	        db = this.getConnection(context);
	        //check permission to record
	        if (!AccountsUtil.isCancellabile(db, orgId)){
	      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
	            htmlDialog.addMessage("<br/>");
	            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
	        }
	        else {
	        htmlDialog.addMessage("<form action=\"Farmacosorveglianza.do?command=Trash&auto-populate=true\" method=\"post\">");
	        htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
	        htmlDialog.addMessage("<br/>");
	        htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
	        htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
	        htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
	        htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
	        htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
	        htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+orgId+"\"/>");
	        htmlDialog.addMessage("</form>");
	        }
	      } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getSession().setAttribute("Dialog", htmlDialog);
	    return ("ConfirmDeleteOK");
	  }
	 
}

  
  
  
