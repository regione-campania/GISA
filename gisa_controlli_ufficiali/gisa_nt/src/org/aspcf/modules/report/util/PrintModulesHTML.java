/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;



import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.aspcf.modules.checklist_benessere.base.Capitolo;
import org.aspcf.modules.checklist_benessere.base.ChecklistIstanza;
import org.aspcf.modules.checklist_benessere.base.ChecklistIstanzaCGO;
import org.aspcf.modules.checklist_benessere.base.ChecklistIstanzaCGO_2018;
import org.aspcf.modules.controlliufficiali.action.AccountVigilanza;
import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.controlliufficiali.base.ModTamponi;
import org.aspcf.modules.controlliufficiali.base.ModTamponiMAcellazione;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcf.modules.controlliufficiali.base.Ticket.TipoOperatori;
import org.aspcfs.modules.acquedirete.base.InfoPdPList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.allevamenti.actions.CheckListAllevamenti;
import org.aspcfs.modules.allevamenti.base.ModuloControllo;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.campioni.actions.Campioni;
import org.aspcfs.modules.campioni.actions.CampioniReport;
import org.aspcfs.modules.campioni.base.ModuloCampione;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.nonconformita.base.TicketList;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;



public class PrintModulesHTML extends CFSModule{

	
	public ResultSet getFieldOperatore(Connection db, Filtro f, String orgId) throws SQLException{
		
		 ResultSet rs = null;
			
		  //setto l'orgid dell'operatore
		  f.setOrgId(Integer.parseInt(orgId));
		  rs = f.queryRecord(db);
		  
		return rs;
	}
	
	
	public ResultSet getFieldOperatoreMacello(Connection db, Filtro f, int idMacello, String dataMacellazione){
		
		  ResultSet rs = null;
		
		  try 
		  {
			  //setto l'orgid dell'operatore
			  
			  //setto l'id del controllo PADRE
			
			  
			  
			  rs = f.queryRecord_operatori_macello(db,idMacello,dataMacellazione);
			 
			  
			
		
			  
	  }catch ( Exception errorMessage ) {
			errorMessage.printStackTrace();
	  }
	  
	  return rs;
	}
	
	public ResultSet getFieldOperatoreMacelloOpu(Connection db, Filtro f, int idMacello, String dataMacellazione){
		
		  ResultSet rs = null;
		
		  try 
		  {
			  //setto l'orgid dell'operatore
			  
			  //setto l'id del controllo PADRE
			
			  
			  
			  rs = f.queryRecord_operatori_macello_opu(db,idMacello,dataMacellazione);
			 
			  
			
		
			  
	  }catch ( Exception errorMessage ) {
			errorMessage.printStackTrace();
	  }
	  
	  return rs;
	}
	
	public ResultSet getFieldOperatoreMacelloSintesis(Connection db, Filtro f, int idMacello, String dataMacellazione){
		
		  ResultSet rs = null;
		
		  try 
		  {
			  //setto l'orgid dell'operatore
			  
			  //setto l'id del controllo PADRE
			
			  
			  
			  rs = f.queryRecord_operatori_macello_sintesis(db,idMacello,dataMacellazione);
			 
			  
			
		
			  
	  }catch ( Exception errorMessage ) {
			errorMessage.printStackTrace();
	  }
	  
	  return rs;
	}
	
	public ResultSet getFieldOperatore(String url,Connection db, Filtro f, String idCU,String orgId,String ticketId){
		
		  ResultSet rs = null;
		
		  try 
		  {
			  //setto l'orgid dell'operatore

			  f.setOrgId(Integer.parseInt(orgId));
			  //setto l'id del controllo PADRE
			  f.setIdControllo(Integer.parseInt(idCU));
			  //setto l'id del campione
			  f.setIdCampione(Integer.parseInt(ticketId));
			  
			  boolean mercatoIttico = f.isOperatoreMercatoIttico(db);
			  if(url != null){
					TipoOperatori to = TipoOperatori.valueOf(url);
			  switch (to) {
				case Account : { rs = f.queryRecord_controlli(db); break ;
				} 
				case Allevamenti: { rs = f.queryRecord_allevamento(db); break ;
				} 
				
				case Stabilimenti: {
					if(mercatoIttico){
						rs = f.queryRecord_operatori_mercatoIttico(db);
						break;
					}
					else {
						rs = f.queryRecord_controlli_stabilimenti(db); 
						break ;
					}
					
				}	
				case ApicolturaApiari : { rs = f.queryRecord_controlli_api(db); break ;} 
				case OperatoriMercatoIttico: { rs = f.queryRecord_operatori_mercatoIttico(db); break ;
				}
				case OperatoriCommerciali: { rs = f.queryRecord_operatori_commerciali(db); break ;
				}
				case OperatoriFuoriRegione: { rs = f.queryRecord_controlli_op_fuori_asl(db); break ;
				} 
				case Operatoriprivati: {	rs = f.queryRecord_controlli_privati(db); break ;
				}  
				case Farmacie: { rs = f.queryRecord_controlli_farmacosorveglianza(db); break ;
				}
				case Parafarmacie: { rs = f.queryRecord_controlli_farmacosorveglianza(db); break ;
				} 
				case Abusivismi: { rs = f.queryRecord_controlli_abusivi(db); break ;
				} 
				case Soa: { rs = f.queryRecord_controlli_soa(db); break ;
				} 
				case Osm: { rs = f.queryRecord_controlli_osm(db); break ;
				} 
				case AziendeAgricoleOpuStab: { rs = f.queryRecord_controlli_opu(db); break ;
				}
				case AziendeAgricole: { rs = f.queryRecord_controlli_aziendeagricole(db); break; 
				}
				case AcqueRete: { rs = f.queryRecord_controlli_acquerete(db); break; 
				}
				case RiproduzioneAnimale: {rs = f.queryRecord_controlli_riproduzioneanimale(db); break;
				}
				case OsmRegistrati:{ rs = f.queryRecord_controlli_osm(db); break ;
				} 
				case Trasporti: { rs = f.queryRecord_controlli_trasporto(db); break ;
				} 
				case Canili: { rs = f.queryRecord_controlli_canile(db); break ;
				} 
				case LabHaccp: { rs = f.queryRecord_controlli_lab_haccp(db); break ;
				} 
				case CaniPadronali: { rs = f.queryRecord_controlli_cani_padronali(db); break ;
				} 
				case OSAnimali: { rs = f.queryRecord_controlli_operatori_sperimentazione_animale(db); break ;
				} 
				case OpnonAltrove: { rs = f.queryRecord_controlli_cani_padronali(db); break ;
				} 
				case MolluschiBivalvi : { rs = f.queryRecord_controlliMolluschiBivalvi(db); break ;
				} 
				case Colonie : { rs = f.queryRecord_controlli_canile(db); break ;
				}
				case Imbarcazioni: { rs = f.queryRecord_controlli_imbarcazione(db); 
				}
				case OpuStab: { rs = f.queryRecord_controlli_opu(db); break ;
				}
				case GisaSuapStab: { rs = f.queryRecord_controlli_richieste(db); break ;
				}
				case StabilimentoSintesisAction: { rs = f.queryRecord_controlli_sintesis(db); break ;
				}
				case GestioneAnagrafica: { rs = f.queryRecord_controlli_anagrafica(db); break ;
				}
				case ZoneControllo: { rs = f.queryRecord_controlli_zone_controllo(db); break;
				}
				case PuntiSbarco: { rs = f.queryRecord_controlli_punti_di_sbarco(db); break;
				}
				default: System.out.println("Tipo Operatore non previsto!");
				}
			  }
			  
			
		
			  
	  }catch ( Exception errorMessage ) {
			errorMessage.printStackTrace();
	  }
	  
	  return rs;
	}
	
	public String executeCommandViewStampa(ActionContext context){
		String orgId = context.getRequest().getParameter("orgId");
		String tipoVerbale = (String) context.getRequest().getParameter("tipo");
		String ticketId = (String) context.getRequest().getParameter("ticketId");
		String motivazione_campione = context.getRequest().getParameter("motivazione_campione");
		String motivazione_piano_campione = context.getRequest().getParameter("motivazione_piano_campione");
		
		ArrayList<String> ElencoAnaliti = new ArrayList<String>();
		String sizeA = (String) context.getRequest().getParameter("sizeA");
		for(int i=0;i<Integer.parseInt(sizeA);i++) {
			ElencoAnaliti.add(context.getRequest().getParameter("IdA_"+i));
		}

		ArrayList<String> ElencoMatrici = new ArrayList<String>();
		String sizeM = (String) context.getRequest().getParameter("sizeM");
		for(int i=0;i<Integer.parseInt(sizeM);i++) {
			ElencoMatrici.add(context.getRequest().getParameter("IdM_"+i));
		}
		
		context.getRequest().setAttribute("tipo",tipoVerbale);
		context.getRequest().setAttribute("ticketId", ticketId);
		String barcodeId = context.getRequest().getParameter("barcodeId");
		Filtro f = new Filtro();
		Connection db = null;
		try {
			db = this.getConnection(context);
			ResultSet rs = this.getFieldOperatore(db, f, orgId);
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs, "operatore");
			org.setOrgId(Integer.parseInt(orgId));
			String barCodePrelievo = "";			
			context.getRequest().setAttribute("OrgOperatore", org);
			ModCampioni campione = new ModCampioni();
			
			campione.setTipoModulo(tipoVerbale);
			
			//MOTIVAZIONE
			if (motivazione_campione.equals("2") && Integer.parseInt(motivazione_piano_campione)>0){
				campione.retrieveCode(db, Integer.parseInt(motivazione_piano_campione));
			}
			else {
				campione.retrieveCode(db, Integer.parseInt(motivazione_campione));
			}
			
			//BARCODE OSA
			if(org.getApproval_number() != null && !org.getApproval_number().equals("")){
				campione.setBarcodeOSA(org.getApproval_number());
			}
			else {
				campione.setBarcodeOSA(org.getN_reg());
			}	
			
			//BARCODE VERBALE
			if(tipoVerbale != null){
			//	barCodePrelievo = campione.generaCodice(db, Integer.parseInt(tipoVerbale), Integer.parseInt(orgId),0);
				campione.setBarcodePrelievo(barcodeId);
			} 
				
			//BARCODE MATRICE e ANALITI
			ArrayList<String> path_analiti = new ArrayList<String>();
			ArrayList<String> path_matrice = new ArrayList<String>();
			String descrizione="";
			
			for (int i=0;i<Integer.parseInt(sizeA);i++){
				path_analiti = campione.getPathAndCodesFromId(db,Integer.parseInt(ElencoAnaliti.get(i)),"analiti","analiti_id");
				if(path_analiti.size() > 0){
					descrizione += path_analiti.get(0)+"  ---------  ";
				}
			}
			campione.setAnaliti(descrizione);
			
			descrizione="";
			for (int i=0;i<Integer.parseInt(sizeM);i++){
				path_matrice = campione.getPathAndCodesFromId(db,Integer.parseInt(ElencoMatrici.get(i)),"matrici","matrice_id");
				if(path_matrice.size() > 0){
					descrizione += path_matrice.get(0)+"  ---------  ";
				}
				if( path_matrice.get(1)!= null && !path_matrice.get(1).equals("")){
					campione.setCodiciMatrice(path_matrice.get(1));
					campione.setMatrice(descrizione.split("  ---------  ")[0]);
				}
			}
			
			
			//Barcode della matrice
//			executeCommandViewMatriciAnalitiPrenota(context,db,campione);		
			context.getRequest().setAttribute("OrgCampione", campione);
	    	context.getRequest().setAttribute("tipo",tipoVerbale);

		}catch(SQLException ex){
			
			ex.printStackTrace();
		}finally{
			this.freeConnection(context, db);
		}
		return "HTMLOK2";
	}
	
public String executeCommandViewModulesFromPrenotaCampione(ActionContext context){
		
		String orgId = context.getRequest().getParameter("orgId");
		String tipoVerbale = (String) context.getRequest().getParameter("tipo");
		String tipoAction = (String) context.getRequest().getParameter("tipoAction");
		String motivazione_campione = context.getRequest().getParameter("motivazione_campione");
		String motivazione_piano_campione = context.getRequest().getParameter("motivazione_piano_campione");
		
	    Connection db = null;
		try{
			db = this.getConnection(context);
			org.aspcfs.modules.stampa_verbale_prelievo.base.Organization org = new org.aspcfs.modules.stampa_verbale_prelievo.base.Organization();
			ResultSet rs = org.queryGetOsa(db, Integer.parseInt(orgId));
		    
		    while (rs.next()){
		    	org.setName(rs.getString("name"));
		    	org.setSiteId(rs.getInt("site_id"));
		    	org.setOrgId(rs.getInt("org_id"));
		    }
			context.getRequest().setAttribute("OrgDetails", org);
		}catch(SQLException s){
	    	s.printStackTrace();
	    }finally {
  		  	this.freeConnection(context, db);
	  	}
	    
		String barcodeId = context.getRequest().getParameter("barcodeId");
		context.getRequest().setAttribute("tipo",tipoVerbale);

		if(tipoAction != null && tipoAction.equals("prenota")){
				return this.executeCommandPrenotaCampione(context);
		}
		else return "PrenotazioneOK";	 
	}
	
	

	public String executeCommandPrenota(ActionContext context) {
		   
	    String orgId = context.getRequest().getParameter("orgId");
	    String tipo = context.getRequest().getParameter("tipo");
	    Connection db = null;
	    try {
	    
	    	db = this.getConnection(context);
	    	
	    	LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
	        quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
	        context.getRequest().setAttribute("Motivazione", quesiti_diagn);
	        
	    	
	    	LookupList siteList = new LookupList(db, "lookup_site_id");
	        siteList.addItem(-1, "-- SELEZIONA VOCE --");
	        siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	        context.getRequest().setAttribute("SiteIdList", siteList);
	    	
	        org.aspcfs.modules.stampa_verbale_prelievo.base.Organization org = new org.aspcfs.modules.stampa_verbale_prelievo.base.Organization();
		    ResultSet rs = org.queryGetOsa(db, Integer.parseInt(orgId));
		    
		    while (rs.next()){
		    	org.setName(rs.getString("name"));
		    	org.setSiteId(rs.getInt("site_id"));
		    	org.setOrgId(rs.getInt("org_id"));
		    }
		    
		    if(orgId != null && !orgId.equals("")){
		    	context.getRequest().setAttribute("orgId",orgId);
		    }
	    	context.getRequest().setAttribute("tipo",tipo);
		    context.getRequest().setAttribute("OrgDetails", org);
	    }catch(SQLException s){
	    	s.printStackTrace();
	    }finally {
  		  	this.freeConnection(context, db);
	  	  }   
	    return ("PrenotaOK");
	}
	
	public String executeCommandPrenotaCampione(ActionContext context){
		
		   String orgId = context.getRequest().getParameter("orgId");

		   String tipo = context.getRequest().getParameter("tipo");
		   
		   org.aspcfs.modules.stampa_verbale_prelievo.base.Organization org = (org.aspcfs.modules.stampa_verbale_prelievo.base.Organization) context.getRequest().getAttribute("OrgDetails");
		   context.getRequest().setAttribute("OrgDetails", org);

		   org.aspcfs.modules.campioni.actions.Campioni c= new Campioni();
		   Connection db = null ;
		   try {   
			   db = this.getConnection(context);
			c.executeCommandInsert(context,db);
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		   finally
		   {
			   this.freeConnection(context, db);
		   }
		   context.getRequest().setAttribute("orgId",orgId);
		   context.getRequest().setAttribute("tipo",tipo);		   
		   return "PrenotazioneOK";			  
	 }
	
	public void executeCommandViewMatriciAnalitiPrenota(ActionContext context, Connection db, ModCampioni c) throws NumberFormatException, SQLException{
		   
		   ArrayList<String> path_analiti = new ArrayList<String>();
		   ArrayList<String> path_matrice = new ArrayList<String>();
		   String descrizione = "";
								    
		   if(context.getRequest().getParameter("size")!=null)
		   {
			int size = Integer.parseInt(context.getRequest().getParameter("size"));
			for (int i = 1 ; i<=size; i++)
			{
				if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
				{
					path_analiti = c.getPathAndCodesFromId(db,Integer.parseInt(context.getRequest().getParameter("analitiId_"+i)),"analiti","analiti_id");
					if(path_analiti.size() > 0){
						 descrizione += path_analiti.get(0)+"  ---------  ";
					}
				}

			}
			c.setAnaliti(descrizione);
		 }
		   
		if(context.getRequest().getParameter("size1")!=null)
		{
			int size = Integer.parseInt(context.getRequest().getParameter("size1"));
			for (int i = 1 ; i<=size; i++)
			{
				if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
				{
					path_matrice = c.getPathAndCodesFromId(db, Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i)),"matrici","matrice_id");
					if(path_matrice.size() > 0){
						c.setMatrice(path_matrice.get(0));
					}
					if( path_matrice.get(1)!= null && !path_matrice.get(1).equals("")){
						c.setCodiciMatrice(path_matrice.get(1));
					}
				}

			}
		}
		   
  }
	
	public String executeCommandViewModello10Macelli(ActionContext context){
		   
		 
        String redirect = "";
        String url = context.getRequest().getParameter("url"); // Stabilimenti
        String orgId = context.getRequest().getParameter("orgId");
        String sessioneMacellazione = context.getParameter("sessione_macellazione");
		 String tipoMod = (String) context.getRequest().getParameter("tipo"); //10.1
		 Connection db = null;
	     String idCU = "999";
	     context.getRequest().getAttribute("tipo");
	     String dataPostMortem = context.getRequest().getParameter("comboDateMacellazione");
	     
		 Filtro f = new Filtro();
		 String returnValue  = "";
		 ResultSet rs = null;
		 try{
			  db = this.getConnection(context);  
			  rs = this.getFieldOperatoreMacello(db, f, Integer.parseInt(orgId), dataPostMortem);
		
			  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
			  org.setOrgId(Integer.parseInt(orgId));			  			  
			  context.getRequest().setAttribute("OrgOperatore", org);
			  context.getRequest().setAttribute("orgId",orgId);		
				  redirect = url +"MacellazioneDocumenti.do?command=ToMod10&orgId="+orgId;
				 
				  ResultSet rs_tamponi = f.queryRecord_tamponin_sedute_macellazione(db,dataPostMortem,sessioneMacellazione,orgId);
				  ModTamponiMAcellazione tampone = new ModTamponiMAcellazione(rs_tamponi,db);
				  tampone.setTipoModulo(tipoMod);					 				 
				  tampone.getFieldsModuli(db, orgId, Integer.parseInt(idCU), org);
				  //nel caso in cui si tratta di un tampone per macelli l'idCU e' 999
				  String barcode = tampone.getBarcode(db, orgId, Integer.parseInt(idCU), tampone.getId());
				  tampone.setBarcodePrelievo(barcode);
				  context.getRequest().setAttribute("OrgTamponeMacelli", tampone);
				  context.getRequest().setAttribute("redirect", redirect);
				  
				  try{
					  
					  if (sessioneMacellazione == null || "".equals(sessioneMacellazione))
						  sessioneMacellazione = "0";
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("insert into m_mod_10 (id_macello,data_macellazione,sessione_macellazione,data_generazione,generato_da) values (?,?,?,current_timestamp,?);" +
					  		"update m_capi set modello10=true where id_macello =? and cd_seduta_macellazione=? and to_char(vpm_data, 'dd/MM/yyyy') = ?;"); 
					  pst.setInt(1, Integer.parseInt(orgId));
					  pst.setTimestamp(2, t);
					  pst.setInt(3, Integer.parseInt(sessioneMacellazione));
					  pst.setInt(4, getUserId(context));
					  pst.setInt(5, Integer.parseInt(orgId));
					  pst.setInt(6, Integer.parseInt(sessioneMacellazione));
					  pst.setString(7, dataPostMortem);
					  pst.execute();
				  
				  }catch(SQLException e)
				  {
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("update m_mod_10 set data_generazione = current_timestamp , generato_da = ? where id_macello = ? and data_macellazione = ? " +
					  		" and sessione_macellazione =?"); 
					  pst.setInt(1, getUserId(context));
					  pst.setInt(2, Integer.parseInt(orgId));
					  pst.setTimestamp(3, t);
					  pst.setInt(4, Integer.parseInt(sessioneMacellazione));
					  pst.execute();
					  
				  }
				  
				  returnValue = "HTMLTamponiMacelliOK"; 
				   
			
				//Server documentale: memorizza i values nell'html
				  if (context.getRequest().getParameter("documentale")!=null){
					  String listavalori = context.getRequest().getParameter("listavalori");
						String[] splitValori = listavalori.split(";;;", -1);
						ArrayList<String> valoriScelti = new ArrayList<String>();
					
						for (int i=0;i<splitValori.length;i++)
							valoriScelti.add(splitValori[i]);
						context.getRequest().setAttribute("valoriScelti", valoriScelti);
						context.getRequest().setAttribute("definitivoDocumentale", "true");
						  }
				  else{
					  ArrayList<String> valoriScelti = new ArrayList<String>();
					  for (int i = 0; i < 100; i++) {
						  valoriScelti.add("");
						}
				  		context.getRequest().setAttribute("valoriScelti", valoriScelti);}
			  
		  }catch (Exception e) {
			e.printStackTrace();
		  }finally {
	  		  	this.freeConnection(context, db);
	  	  }

 	 return returnValue;
 } 
	
	public String executeCommandViewModello10MacelliOpu(ActionContext context){
		   
		 
        String redirect = "";
        String url = context.getRequest().getParameter("url"); // Stabilimenti
        String altId = context.getRequest().getParameter("altId");
        String sessioneMacellazione = context.getParameter("sessione_macellazione");
		 String tipoMod = (String) context.getRequest().getParameter("tipo"); //10.1
		 Connection db = null;
	     String idCU = "999";
	     context.getRequest().getAttribute("tipo");
	     String dataPostMortem = context.getRequest().getParameter("comboDateMacellazione");
	     
		 Filtro f = new Filtro();
		 String returnValue  = "";
		 ResultSet rs = null;
		 try{
			  db = this.getConnection(context);  
			  rs = this.getFieldOperatoreMacelloOpu(db, f, Integer.parseInt(altId), dataPostMortem);
		
			  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
			  org.setAltId(Integer.parseInt(altId));			  			  
			  context.getRequest().setAttribute("OrgOperatore", org);
			  context.getRequest().setAttribute("altId",altId);		
				  redirect = url +"MacellazioneDocumentiOpu.do?command=ToMod10&altId="+altId;
				 
				  ResultSet rs_tamponi = f.queryRecord_tamponin_sedute_macellazione(db,dataPostMortem,sessioneMacellazione,altId);
				  ModTamponiMAcellazione tampone = new ModTamponiMAcellazione(rs_tamponi,db);
				  tampone.setTipoModulo(tipoMod);					 				 
				  tampone.getFieldsModuli(db, altId, Integer.parseInt(idCU), org);
				  //nel caso in cui si tratta di un tampone per macelli l'idCU e' 999
				  String barcode = tampone.getBarcode(db, altId, Integer.parseInt(idCU), tampone.getId());
				  tampone.setBarcodePrelievo(barcode);
				  context.getRequest().setAttribute("OrgTamponeMacelli", tampone);
				  context.getRequest().setAttribute("redirect", redirect);
				  
				  try{
					  
					  if (sessioneMacellazione == null || "".equals(sessioneMacellazione))
						  sessioneMacellazione = "0";
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("insert into m_mod_10 (id_macello,data_macellazione,sessione_macellazione,data_generazione,generato_da) values (?,?,?,current_timestamp,?);" +
					  		"update m_capi set modello10=true where id_macello =? and cd_seduta_macellazione=? and to_char(vpm_data, 'dd/MM/yyyy') = ?;"); 
					  pst.setInt(1, Integer.parseInt(altId));
					  pst.setTimestamp(2, t);
					  pst.setInt(3, Integer.parseInt(sessioneMacellazione));
					  pst.setInt(4, getUserId(context));
					  pst.setInt(5, Integer.parseInt(altId));
					  pst.setInt(6, Integer.parseInt(sessioneMacellazione));
					  pst.setString(7, dataPostMortem);
					  pst.execute();
				  
				  }catch(SQLException e)
				  {
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("update m_mod_10 set data_generazione = current_timestamp , generato_da = ? where id_macello = ? and data_macellazione = ? " +
					  		" and sessione_macellazione =?"); 
					  pst.setInt(1, getUserId(context));
					  pst.setInt(2, Integer.parseInt(altId));
					  pst.setTimestamp(3, t);
					  pst.setInt(4, Integer.parseInt(sessioneMacellazione));
					  pst.execute();
					  
				  }
				  
				  returnValue = "HTMLTamponiMacelliOpuOK"; 
				   
			
				//Server documentale: memorizza i values nell'html
				  if (context.getRequest().getParameter("documentale")!=null){
					  String listavalori = context.getRequest().getParameter("listavalori");
						String[] splitValori = listavalori.split(";;;", -1);
						ArrayList<String> valoriScelti = new ArrayList<String>();
					
						for (int i=0;i<splitValori.length;i++)
							valoriScelti.add(splitValori[i]);
						context.getRequest().setAttribute("valoriScelti", valoriScelti);
						context.getRequest().setAttribute("definitivoDocumentale", "true");
						  }
				  else{
					  ArrayList<String> valoriScelti = new ArrayList<String>();
					  for (int i = 0; i < 100; i++) {
						  valoriScelti.add("");
						}
				  		context.getRequest().setAttribute("valoriScelti", valoriScelti);}
			  
		  }catch (Exception e) {
			e.printStackTrace();
		  }finally {
	  		  	this.freeConnection(context, db);
	  	  }

 	 return returnValue;
 } 
	
	public String executeCommandViewModello10MacelliSintesis(ActionContext context){
		   
		 
        String redirect = "";
        String url = context.getRequest().getParameter("url"); // Stabilimenti
        String altId = context.getRequest().getParameter("altId");
        String sessioneMacellazione = context.getParameter("sessione_macellazione");
		 String tipoMod = (String) context.getRequest().getParameter("tipo"); //10.1
		 Connection db = null;
	     String idCU = "999";
	     context.getRequest().getAttribute("tipo");
	     String dataPostMortem = context.getRequest().getParameter("comboDateMacellazione");
	     
		 Filtro f = new Filtro();
		 String returnValue  = "";
		 ResultSet rs = null;
		 try{
			  db = this.getConnection(context);  
			  rs = this.getFieldOperatoreMacelloSintesis(db, f, Integer.parseInt(altId), dataPostMortem);
		
			  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
			  org.setAltId(Integer.parseInt(altId));			  			  
			  context.getRequest().setAttribute("OrgOperatore", org);
			  context.getRequest().setAttribute("altId",altId);		
				  redirect = url +"MacellazioneDocumentiSintesis.do?command=ToMod10&altId="+altId;
				 
				  ResultSet rs_tamponi = f.queryRecord_tamponin_sedute_macellazione(db,dataPostMortem,sessioneMacellazione,altId);
				  ModTamponiMAcellazione tampone = new ModTamponiMAcellazione(rs_tamponi,db);
				  tampone.setTipoModulo(tipoMod);					 				 
				  tampone.getFieldsModuli(db, altId, Integer.parseInt(idCU), org);
				  //nel caso in cui si tratta di un tampone per macelli l'idCU e' 999
				  String barcode = tampone.getBarcode(db, altId, Integer.parseInt(idCU), tampone.getId());
				  tampone.setBarcodePrelievo(barcode);
				  context.getRequest().setAttribute("OrgTamponeMacelli", tampone);
				  context.getRequest().setAttribute("redirect", redirect);
				  
				  try{
					  
					  if (sessioneMacellazione == null || "".equals(sessioneMacellazione))
						  sessioneMacellazione = "0";
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("insert into m_mod_10 (id_macello,data_macellazione,sessione_macellazione,data_generazione,generato_da) values (?,?,?,current_timestamp,?);" +
					  		"update m_capi set modello10=true where id_macello =? and cd_seduta_macellazione=? and to_char(vpm_data, 'dd/MM/yyyy') = ?;"); 
					  pst.setInt(1, Integer.parseInt(altId));
					  pst.setTimestamp(2, t);
					  pst.setInt(3, Integer.parseInt(sessioneMacellazione));
					  pst.setInt(4, getUserId(context));
					  pst.setInt(5, Integer.parseInt(altId));
					  pst.setInt(6, Integer.parseInt(sessioneMacellazione));
					  pst.setString(7, dataPostMortem);
					  pst.execute();
				  
				  }catch(SQLException e)
				  {
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					  Timestamp t = new Timestamp(sdf.parse(dataPostMortem).getTime());
					  PreparedStatement pst = db.prepareStatement("update m_mod_10 set data_generazione = current_timestamp , generato_da = ? where id_macello = ? and data_macellazione = ? " +
					  		" and sessione_macellazione =?"); 
					  pst.setInt(1, getUserId(context));
					  pst.setInt(2, Integer.parseInt(altId));
					  pst.setTimestamp(3, t);
					  pst.setInt(4, Integer.parseInt(sessioneMacellazione));
					  pst.execute();
					  
				  }
				  
				  returnValue = "HTMLTamponiMacelliSintesisOK"; 
				   
			
				//Server documentale: memorizza i values nell'html
				  if (context.getRequest().getParameter("documentale")!=null){
					  String listavalori = context.getRequest().getParameter("listavalori");
						String[] splitValori = listavalori.split(";;;", -1);
						ArrayList<String> valoriScelti = new ArrayList<String>();
					
						for (int i=0;i<splitValori.length;i++)
							valoriScelti.add(splitValori[i]);
						context.getRequest().setAttribute("valoriScelti", valoriScelti);
						context.getRequest().setAttribute("definitivoDocumentale", "true");
						  }
				  else{
					  ArrayList<String> valoriScelti = new ArrayList<String>();
					  for (int i = 0; i < 100; i++) {
						  valoriScelti.add("");
						}
				  		context.getRequest().setAttribute("valoriScelti", valoriScelti);}
			  
		  }catch (Exception e) {
			e.printStackTrace();
		  }finally {
	  		  	this.freeConnection(context, db);
	  	  }

 	 return returnValue;
 } 
	
	
   public String executeCommandViewModules(ActionContext context){
	   
	   
	         String redirect = "";
	         String url = context.getRequest().getParameter("url");
	         String orgId = context.getRequest().getParameter("orgId");
		     String ticketId = context.getRequest().getParameter("ticketId");
			 String tipoMod = (String) context.getRequest().getParameter("tipo");
			 
			 if (tipoMod.equals("9"))
				 return executeCommandViewModAcque(context);
			 if (tipoMod.equals("19")){
				 CampioniReport cr = new CampioniReport();
				 return cr.executeCommandViewSchedaPNAA2(context);
			 }
			 
			 Connection db = null;
		     String idCU = context.getRequest().getParameter("idCU");
		     context.getRequest().getAttribute("tipo");
			 Filtro f = new Filtro();
			 String returnValue  = "";
			 ResultSet rs = null;
			
			 try{
				 
				  db = this.getConnection(context);  
				  rs = this.getFieldOperatore(url, db, f, idCU, orgId, ticketId);
				  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
				
				  //NUOVA GESTIONE ATTRIBUTI ESTESI
				  try {
					  popolaModulo(context, db, Integer.parseInt(ticketId), Integer.parseInt(tipoMod));
				  }
				  catch (Exception e){
					  e.printStackTrace();
				  }
				  
				  if(url.equals("MolluschiBivalvi") && !(idCU.equals("-1"))){
					  org.setClasse(rs.getString("classificazione"));
			    	  org.setCodiceAcqua(rs.getInt("codice_acqua")); 
			    	  org.setOrgId(Integer.parseInt(orgId));
			    	  int sizeListaConcessionari = org.listConcessionari(db);
			    	org.buildListSpecieMolluschi(db);
			    	  context.getRequest().setAttribute("sizeLista", sizeListaConcessionari);
			    	 org.aspcfs.modules.campioni.base.Ticket ticket = new org.aspcfs.modules.campioni.base.Ticket(db,Integer.parseInt(ticketId));
			    	//ticket.buildCoordinateMolluschi(db);
			    	  context.getRequest().setAttribute("ticketDetails", ticket);
				  }
				  			  
				  AccountVigilanza.setComponentiNucleo(org, Integer.parseInt(idCU), db);
				  
				  context.getRequest().setAttribute("OrgOperatore", org);
				  context.getRequest().setAttribute("orgId",orgId);		
				  
				  if(tipoMod.equals("6") || tipoMod.equals("10")) {
					  
					  redirect = url +"Tamponi.do?command=TicketDetails&id="+ ticketId + "&orgId=" + orgId;
					 
					  ResultSet rs_tamponi = f.queryRecord_tamponi(db);
					  ModTamponi tampone = new ModTamponi(rs_tamponi);
					  tampone.setTipoModulo(tipoMod);					 				 
					  tampone.getFieldsModuli(db, orgId, Integer.parseInt(idCU), org);
					  tampone.getBarcodes(db, Integer.parseInt(tipoMod), orgId, Integer.parseInt(ticketId));
					  context.getRequest().setAttribute("OrgTampone", tampone);
					  context.getRequest().setAttribute("redirect", redirect);
					  returnValue = "HTMLTamponiOK";
					  
				  } else {
					  
					  redirect = url +"Campioni.do?command=TicketDetails&id="+ ticketId + "&orgId=" + orgId;
					  ResultSet rs_campione = f.queryRecord_campioni(db);
					  ModCampioni campione = new ModCampioni(rs_campione);
					  campione.setTipoModulo(tipoMod);	 
					  campione.getFieldsModuli(db, orgId, Integer.parseInt(idCU), org);
					  campione.getBarcodes(db, Integer.parseInt(tipoMod), orgId, Integer.parseInt(ticketId));
					  if(!idCU.equals("-1")){
						  
						  f.getPerContoDi(db, rs_campione);
					  }
					  campione.setServizio(f.getServizio());
					  campione.setUo(f.getUo());
					  if(tipoMod.equals("1") ){
						 // String barcodeMolluschi =  campione.generaCodiceMolluschi(db, campione.getBarcodePrelievo(), Integer.parseInt(ticketId), Integer.parseInt(orgId), idCU);
						  campione.setBarcodeMolluschi(campione.getBarcodePrelievo());
						  campione.setBarcodeMolluschiNew(campione.getBarcodePrelievoNew());
					  }
					  org.aspcfs.modules.campioni.base.Ticket ticket = new org.aspcfs.modules.campioni.base.Ticket(db,Integer.parseInt(ticketId));
					  //campione.setBarcodePrelievo(ticket.getLocation());
					  campione.setBarcodePrelievo(campione.getBarcodePrelievo());
					  campione.setBarcodePrelievoNew(campione.getBarcodePrelievoNew());
					  
					  // CASO IN CUI LA PRATICA E' STATA CHIUSA. IL CAMPIONE DEVE POTER ESSERE STAMPABILE (APPARE IL BOTTONE GENERA PDF)
					  if (campione.getData_chiusura_campione()!=null)
						  context.getRequest().setAttribute("definitivo", "true");
					  
					  //Caso in cui sono nel cavaliere Molluschi bivalvi ed ho 1 campione--> 2 verbali (1 per i molluschi e 1 tra battereologico/chimico 
					  // a seconda dell'analita...)
					 /* if(tipoMod.equals("1") ){
							  String barcodeMolluschi =  campione.generaCodiceMolluschi(db, campione.getBarcodePrelievo(), Integer.parseInt(ticketId), Integer.parseInt(orgId), idCU);
							  campione.setBarcodeMolluschi(campione.getBarcodePrelievo());
					  }*/
					
					  
					  
					  if (idCU.equals("-1")){
						  rs = this.getFieldOperatore(db, f, orgId);
						  org = new org.aspcf.modules.controlliufficiali.base.Organization(rs, "operatore");
						  org.setOrgId(Integer.parseInt(orgId));
						  context.getRequest().setAttribute("OrgOperatore", org);
						  
						  //BARCODE MOTIVAZIONE
						  if (ticket.getMotivazione_campione()==2 && ticket.getMotivazione_piano_campione()>0){
							  campione.retrieveCode(db, ticket.getMotivazione_piano_campione());
						  }
							  
						  else 
							  campione.retrieveCode(db, ticket.getMotivazione_campione());
						  
						  //BARCODE OSA
						  if(org.getApproval_number() != null && !org.getApproval_number().equals(""))
							  campione.setBarcodeOSA(org.getApproval_number());
						  else
							  campione.setBarcodeOSA(org.getN_reg());
					  }					  
					  
					  
					  //MOLLUSCHI
					  if(org.getClasse() != null && !org.getClasse().equals("")){
						  if(org.getClasse().equals("CLASSE A")){
							  campione.setBarcodeMotivazione("PRGMA");
						  }else if(org.getClasse().equals("CLASSE B")){
							  campione.setBarcodeMotivazione("PRGMB");
						  }else if(org.getClasse().equals("CLASSE C")){
							  campione.setBarcodeMotivazione("PRGMC");
						  }
					  }
					  context.getRequest().setAttribute("OrgCampione", campione);
					  context.getRequest().setAttribute("redirect", redirect);
					  returnValue =  "HTMLOK";
					  
				 	  if (idCU.equals("-1"))
						  returnValue =  "PrenotaCampioniHTMLOK";
				 
				  }
				  
				  //Server documentale: memorizza i values nell'html
				  if (context.getRequest().getParameter("documentale")!=null){
					  String listavalori = context.getRequest().getParameter("listavalori");
						String[] splitValori = listavalori.split(";;;", -1);
						ArrayList<String> valoriScelti = new ArrayList<String>();
					
						for (int i=0;i<splitValori.length;i++)
							valoriScelti.add(splitValori[i]);
						context.getRequest().setAttribute("valoriScelti", valoriScelti);
						context.getRequest().setAttribute("definitivoDocumentale", "true");
									  }
				  else{
					  ArrayList<String> valoriScelti = new ArrayList<String>();
					  for (int i = 0; i < 100; i++) {
						  valoriScelti.add("");
						}
				  		context.getRequest().setAttribute("valoriScelti", valoriScelti);}
				 				  
			  }catch (Exception e) {
				e.printStackTrace();
			  }finally {
		  		  	this.freeConnection(context, db);
		  	  }
			 
			
			
			

	  	 return returnValue;
	  } 
   
   public String executeCommandViewModAcque(ActionContext context){
	   
		  
       String redirect = "";
       String url = context.getRequest().getParameter("url");
       String orgId = context.getRequest().getParameter("orgId");
	     String ticketId = context.getRequest().getParameter("ticketId");
		 String tipoMod = (String) context.getRequest().getParameter("tipo");
		 
		 Connection db = null;
	     String idCU = context.getRequest().getParameter("idCU");
	     context.getRequest().getAttribute("tipo");
		 Filtro f = new Filtro();
		 String returnValue  = "";
		 ResultSet rs = null;
		
		 try{
			 
			  db = this.getConnection(context);  
			  rs = this.getFieldOperatore(url, db, f, idCU, orgId, ticketId);
			  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
			
			  AccountVigilanza.setComponentiNucleo(org, Integer.parseInt(idCU), db);	  
			  context.getRequest().setAttribute("OrgOperatore", org);
			  context.getRequest().setAttribute("orgId",orgId);		
			  
			   redirect = url +"Campioni.do?command=TicketDetails&id="+ ticketId + "&orgId=" + orgId;
				  ResultSet rs_campione = f.queryRecord_campioni(db);
				  ModCampioni campione = new ModCampioni(rs_campione);
				  campione.setTipoModulo(tipoMod);	 
				  campione.getFieldsModuli(db, orgId, Integer.parseInt(idCU), org);
				  campione.getBarcodes(db, Integer.parseInt(tipoMod), orgId, Integer.parseInt(ticketId)); //DECOMMENTARE PER SBLOCCARE ACQUE PREACCETTAZIONE 
				  if(!idCU.equals("-1")){
					  f.getPerContoDi(db, rs_campione);
				  }
				  campione.setServizio(f.getServizio());
				  campione.setUo(f.getUo());
				
				  org.aspcfs.modules.campioni.base.Ticket ticket = new org.aspcfs.modules.campioni.base.Ticket(db,Integer.parseInt(ticketId));
				  campione.setBarcodePrelievo(ticket.getLocation());
				// campione.setBarcodePrelievo(campione.getBarcodePrelievo());
				  
				  // CASO IN CUI LA PRATICA E' STATA CHIUSA. IL CAMPIONE DEVE POTER ESSERE STAMPABILE (APPARE IL BOTTONE GENERA PDF)
				  if (campione.getData_chiusura_campione()!=null)
					  context.getRequest().setAttribute("definitivo", "true");
				  
				  //Caso in cui sono nel cavaliere Molluschi bivalvi ed ho 1 campione--> 2 verbali (1 per i molluschi e 1 tra battereologico/chimico 
				  // a seconda dell'analita...)
				 /* if(tipoMod.equals("1") ){
						  String barcodeMolluschi =  campione.generaCodiceMolluschi(db, campione.getBarcodePrelievo(), Integer.parseInt(ticketId), Integer.parseInt(orgId), idCU);
						  campione.setBarcodeMolluschi(campione.getBarcodePrelievo());
				  }*/
				
				  
				  
				  if (idCU.equals("-1")){
					  rs = this.getFieldOperatore(db, f, orgId);
					  org = new org.aspcf.modules.controlliufficiali.base.Organization(rs, "operatore");
					  org.setOrgId(Integer.parseInt(orgId));
					  context.getRequest().setAttribute("OrgOperatore", org);
					  
					  //BARCODE MOTIVAZIONE
					  if (ticket.getMotivazione_campione()==2 && ticket.getMotivazione_piano_campione()>0){
						  campione.retrieveCode(db, ticket.getMotivazione_piano_campione());
					  }
						  
					  else 
						  campione.retrieveCode(db, ticket.getMotivazione_campione());
					  
					  //BARCODE OSA
					  if(org.getApproval_number() != null && !org.getApproval_number().equals(""))
						  campione.setBarcodeOSA(org.getApproval_number());
					  else
						  campione.setBarcodeOSA(org.getN_reg());
				  }					  
				  
				  
			
				  context.getRequest().setAttribute("OrgCampione", campione);
				  context.getRequest().setAttribute("redirect", redirect);
				  returnValue =  "HTMLOK";
			  
			  
			  //Server documentale: memorizza i values nell'html
			  if (context.getRequest().getParameter("documentale")!=null){
				  String listavalori = context.getRequest().getParameter("listavalori");
					String[] splitValori = listavalori.split(";;;", -1);
					ArrayList<String> valoriScelti = new ArrayList<String>();
				
					for (int i=0;i<splitValori.length;i++)
						valoriScelti.add(splitValori[i]);
					context.getRequest().setAttribute("valoriScelti", valoriScelti);
					context.getRequest().setAttribute("definitivoDocumentale", "true");
								  }
			  else{
				  ArrayList<String> valoriScelti = new ArrayList<String>();
				  for (int i = 0; i < 100; i++) {
					  valoriScelti.add("");
					}
			  		context.getRequest().setAttribute("valoriScelti", valoriScelti);}
			
			
					 
					 InfoPdPList list = new InfoPdPList();
						list.setId_controllo(Integer.parseInt(idCU));
						try {
							list.buildList(db);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						context.getRequest().setAttribute("PdpList", list);
					 
					 return "ModAcqueOK";
				
			  
		  }catch (Exception e) {
			e.printStackTrace();
		  }finally {
	  		  	this.freeConnection(context, db);
	  	  }
		 
	 return returnValue;
} 

public ResultSet getCoordsByOrgId(Connection db, String orgId, Organization org) {
	// TODO Auto-generated method stub
	   ResultSet rs = null;
	   try{
		 
		   String query = "select * from organization_address where org_id = ? and address_type in (5,6,7)";
		   PreparedStatement pst = db.prepareStatement(query);
		   pst.setInt(1, Integer.parseInt(orgId));
		   rs = pst.executeQuery();
		   double latitudine = 0.0;
		   double longitudine = 0.0;
		   if(rs.next()){
			   
			   latitudine = rs.getDouble("latitude");
			   longitudine = rs.getDouble("longitude");
			   String spatial_coords[] = null;
			   spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(longitudine), Double.toString(latitudine), db);
			   if (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(
						',', '.')) > 41.503754) {
				   
				   AjaxCalls ajaxCall = new AjaxCalls();
				   String[] coordinate = ajaxCall.getCoordinate("","","","",Double.toString(latitudine), Double.toString(longitudine), "");
				   org.setLatitudine(coordinate[1]);
				   org.setLongitudine(coordinate[0]);
				   
				} else {
					org.setLatitudine(spatial_coords[0]);
					org.setLongitudine(spatial_coords[1]);
				}
					   
		   }
		   
	   } catch(SQLException sl) {
		   sl.printStackTrace();
	   }
	   
	return rs;
}
   
   public ResultSet getVerbalePnaa(Connection db, String idCampione) {
		// TODO Auto-generated method stub
		   ResultSet rs = null;
		   
		   try{
			 
			   String query = "select * from verbale_prelievo_pnaa where id_campione = ? ";
			   PreparedStatement pst = db.prepareStatement(query);
			   pst.setInt(1, Integer.parseInt(idCampione));
			   rs = pst.executeQuery();
		   } catch(SQLException se){
			   se.printStackTrace();
		   }
		   
		return rs;
	}



public String executeCommandGenerateBarcode(ActionContext context){
	           
		 Connection db = null;
		 
		 try{
			  db = this.getConnection(context);  
			  String barCodePrelievo = "";
			  String tipoVerbale = (String) context.getRequest().getParameter("tipo");
			 // String tipoVerbale = (String) context.getSession().getAttribute("tipoModulo");
			  org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			  context.getRequest().setAttribute("OrgOperatore", org);
			  ModCampioni campione = new ModCampioni();
			  campione.setTipoModulo(tipoVerbale);	
			  barCodePrelievo = campione.generaCodice(db, Integer.parseInt(tipoVerbale));
			  campione.setBarcodePrelievo(barCodePrelievo);
			  context.getRequest().setAttribute("OrgCampione", campione);
		 }catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
		  }
		 finally
		 {
			 this.freeConnection(context, db);
		 }
		  return "HTMLOK";
   	} 

   	
   public String executeCommandAddSchedaAllegato(ActionContext context){
	   
	   String url = context.getRequest().getParameter("url");
        String orgId = context.getRequest().getParameter("orgId");
        String idStabilimento = context.getRequest().getParameter("idStabilimento");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   int numAllegato = -1;
	   int versioneChecklist = -1;
	   int specieChecklist = -1;
	   Connection db = null;
	   String redirectTo = "";
	   String redirectToB11 = "";

	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
	
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   
		// Se siamo nella nuova gestione classyfarm, salta tutto e vai direttamente a quella
		   if (versioneChecklist == 6)
			   return executeCommandAddSchedaAllegato_Ver6(context);
		   
		// Se siamo nella nuova gestione 2019, salta tutto e vai direttamente a quella
		   if (versioneChecklist == 5)
			   return executeCommandAddSchedaAllegato_Ver5(context);
		   
		// Se siamo nella nuova gestione 2018, salta tutto e vai direttamente a quella
		   if (numAllegato != 20 && versioneChecklist == 4)
			   return executeCommandAddSchedaAllegato_Ver4(context);
		   
		// Se siamo nella nuova gestione 2018 classyfarm, salta tutto e vai direttamente a quella
		   if (numAllegato != 20 && versioneChecklist == 3)
			   return executeCommandAddSchedaAllegato_Ver3(context);
		   
		   
		   if(numAllegato!= 20 && (versioneChecklist == 2 || versioneChecklist == 3 || versioneChecklist == 4) ){ //NUOVE
				   boolean esito = false;
				   try {
						 esito = popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
						}
						catch (Exception e){
							e.printStackTrace();
						}
						 if (esito==false){
							 context.getRequest().setAttribute("error", "Errore. Per completare la lista di riscontro selezionata, inserire prima i dati del frontespizio cliccando sul bottone COMPILA FRONTESPIZIO.");
							 return "popupError";
						 }
			      }
		 
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		   
		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   
		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(idStabilimento);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		   
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("AzFields", fields);

		   //Verificare che la scheda non sia stata gia' inserita!
		   
		   ChecklistIstanza chk = new ChecklistIstanza(db,Integer.parseInt(ticketId), specieChecklist);
		   ArrayList<Capitolo> capitoli = Capitolo.buildRecordCapitoli(db,specieChecklist, versioneChecklist);
		   int numCapitoli = 0;
		   //Gestire qui la specie 
		   boolean check_scheda_esistente = chk.verificaScheda(db,Integer.parseInt(ticketId), specieChecklist);
		   ArrayList<Integer> domandeCapitoliGiaRisposte = new ArrayList<Integer>();
		   
		   //se esiste vai al dettaglio della scheda ma carica il set di domande a cui hai risposto!
		   if(check_scheda_esistente){
			   numCapitoli = this.getNumCapitoliGiaRisposte(db,Integer.parseInt(ticketId), specieChecklist);
			   domandeCapitoliGiaRisposte = this.getDomandePerCapitoloGiaRisposte(db,Integer.parseInt(ticketId), specieChecklist);
			   
			   
			   
			   //SE NON E' UNA BOZZA CALCOLA I TOTALI E METTILI SU REQUEST
			   if (!chk.isBozza()&&specieChecklist!=-1){
				   int totA=0, totB=0,totC=0,tot=0;

					String select = "select * from get_punteggio_totale_chk_bns_animale(?)";
					PreparedStatement pst = db.prepareStatement(select);
					pst.setInt(1,Integer.parseInt(ticketId));
					ResultSet rs = pst.executeQuery();
					
					if (rs.next()){ //se trovo risultati, controlla
						totA=rs.getInt("totalea");
						totB=rs.getInt("totaleb");
						totC=rs.getInt("totalec");
					}
					tot = totA+totB+totC;
				   
				   context.getRequest().setAttribute("totale_punteggio_A", String.valueOf(totA));
				   context.getRequest().setAttribute("totale_punteggio_B", String.valueOf(totB));
				   context.getRequest().setAttribute("totale_punteggio_C", String.valueOf(totC));
				   context.getRequest().setAttribute("totale_punteggio", String.valueOf(tot));
			   }
			   
			   visualizzaCampiCgo(chk, versioneChecklist, numAllegato, context, db);
			   
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   context.getRequest().setAttribute("domandePerCapitolo", domandeCapitoliGiaRisposte);
			   context.getRequest().setAttribute("numCapitoli", Integer.toString(numCapitoli));
			   context.getRequest().setAttribute("bozza", Boolean.toString(chk.isBozza()));
			   
			   
			   redirectTo = "InsertAllegatoOK";
			   redirectToB11 = "InsertAllegatoB11OK";

		   }
		   //Altrimenti caricala ex novo
		   else {
			 //Caricamento dei Tipi di Irregolarita': Requisito - Definizione dei requisiti
			   numCapitoli = capitoli.size();
			   String idAllegato = Integer.toString(Capitolo.getIdAllegato(db,specieChecklist, versioneChecklist));
			   TicketList listConf = new TicketList();
			   
			   if (orgId!=null && !orgId.equals(""))
				   listConf.setOrgId(orgId);
			   else if (idStabilimento!=null && !idStabilimento.equals(""))
				   listConf.setIdStabilimento(Integer.parseInt(idStabilimento));
			  
			   listConf.buildListControlli(db, orgId,ticketId);

			   Iterator nonConfIterator = listConf.iterator();
			   String esito = "";
			   if(nonConfIterator.hasNext()) {

				   esito = "NonFavorevole";
			   }else {
				   esito = "Favorevole";
			   }
			   
			   context.getRequest().setAttribute("esito", esito);
			   context.getRequest().setAttribute("CapitoliList",capitoli);
			   context.getRequest().setAttribute("idAlleg",idAllegato);
			   context.getRequest().setAttribute("numCapitoli", Integer.toString(numCapitoli));
			   redirectTo =  "ViewAllegatoOK";
			   redirectToB11 = "ViewAllegatoB11OK";


		   }
		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
	   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	   //if (numAllegato.equals("15")) 
	   if (numAllegato==20 )
		   return redirectToB11;
	   else
		   return redirectTo;
	   
	   
	  
	   
	   
   }
   
public String executeCommandAddSchedaAllegato_Ver3(ActionContext context){
	   
	   String url = context.getRequest().getParameter("url");
        String orgId = context.getRequest().getParameter("orgId");
        String idStabilimento = context.getRequest().getParameter("idStabilimento");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   String descrizioneAltreSpecie = context.getRequest().getParameter("descrizioneAltreSpecie");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   int numAllegato = -1;
	   int versioneChecklist = -1;
	   int specieChecklist = -1;
	   int codAllegato = -1;
	   int idChkBnsModIst = -1;
	   Connection db = null;
	   String redirectTo = "";
	   
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
	
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];
		   
		   if (descrizioneAltreSpecie==null && specieChecklist==-2){
			   switch (specie) {
			   case 139 : descrizioneAltreSpecie="FAGIANI";break;
			   case 125 : descrizioneAltreSpecie="CAPRINI";break;
			   case 146 : descrizioneAltreSpecie="AVICOLI MISTI";break;
			   case 128 : descrizioneAltreSpecie="CONIGLI";break;
			   case 129 : descrizioneAltreSpecie="BUFALINI";break;
			   case 121 : descrizioneAltreSpecie="BOVINI";break;
			   case 126 : descrizioneAltreSpecie="CAVALLI";break;
			   case 124 : descrizioneAltreSpecie="OVINI";break;
			   case 134 : descrizioneAltreSpecie="QUAGLIE";break;
			   case 160 : descrizioneAltreSpecie="PESCI";break;
			   }
		   }
		   context.getRequest().setAttribute("descrizioneAltreSpecie", descrizioneAltreSpecie);

		 //cerco checklist esistente
		   
		   org.aspcf.modules.checklist_benessere.base.v3.ChecklistIstanza_Generica chk = new org.aspcf.modules.checklist_benessere.base.v3.ChecklistIstanza_Generica(db,Integer.parseInt(ticketId), codAllegato);
		   idChkBnsModIst = chk.getIdChkBnsModIst();
		   context.getRequest().setAttribute("ChecklistIstanza", chk);
		   
		   if (numAllegato == 1){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_AltreSpecie_OK";
		   }
		   else if (numAllegato == 5){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Broiler_OK";
		   }
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   ArrayList<org.aspcf.modules.checklist_benessere.base.v3.Domanda> domande = new ArrayList<org.aspcf.modules.checklist_benessere.base.v3.Domanda>();
		   PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v3(?, ?, ?, ?)");
		   pst.setInt(1, specieChecklist);
		   pst.setInt(2, numAllegato);
		   pst.setInt(3, versioneChecklist);
		   pst.setInt(4, controllo.getId());
		   ResultSet rs = pst.executeQuery();
		   while (rs.next()){
			   org.aspcf.modules.checklist_benessere.base.v3.Domanda domanda = new org.aspcf.modules.checklist_benessere.base.v3.Domanda(db, rs, idChkBnsModIst); 
			   domande.add(domanda);
		   }
		   context.getRequest().setAttribute("DomandeList",domande);

		   
		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		   
		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   
		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(idStabilimento);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		   
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("AzFields", fields);

		  
			 //Caricamento dei Tipi di Irregolarita': Requisito - Definizione dei requisiti
			   String idAllegato = Integer.toString(Capitolo.getIdAllegato(db,specieChecklist, versioneChecklist));
			   TicketList listConf = new TicketList();
			   
			   if (orgId!=null && !orgId.equals(""))
				   listConf.setOrgId(orgId);
			   else if (idStabilimento!=null && !idStabilimento.equals(""))
				   listConf.setIdStabilimento(Integer.parseInt(idStabilimento));
			  
			   listConf.buildListControlli(db, orgId,ticketId);

			   Iterator nonConfIterator = listConf.iterator();
			   String esito = "";
			   if(nonConfIterator.hasNext()) {

				   esito = "NonFavorevole";
			   }else {
				   esito = "Favorevole";
			   }
			   
			   context.getRequest().setAttribute("esito", esito);
			   context.getRequest().setAttribute("idAlleg",idAllegato);
			   context.getRequest().setAttribute("codice_specie",String.valueOf(specieChecklist));
			   context.getRequest().setAttribute("specie",String.valueOf(specie));

		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
	   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	   //if (numAllegato.equals("15")) 
	    return redirectTo;
	   
	   
	  
	   
	   
   }
   
public String executeCommandAddSchedaAllegato_Ver4(ActionContext context){
	   
	   String url = context.getRequest().getParameter("url");
        String orgId = context.getRequest().getParameter("orgId");
        String idStabilimento = context.getRequest().getParameter("idStabilimento");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   String descrizioneAltreSpecie = context.getRequest().getParameter("descrizioneAltreSpecie");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   int numAllegato = -1;
	   int versioneChecklist = -1;
	   int specieChecklist = -1;
	   int codAllegato = -1;
	   int idChkBnsModIst = -1;
	   Connection db = null;
	   String redirectTo = "";
	   
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
	
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];
		   
		   if (descrizioneAltreSpecie==null && specieChecklist==-2){
			   switch (specie) {
			   case 139 : descrizioneAltreSpecie="FAGIANI";break;
			   case 125 : descrizioneAltreSpecie="CAPRINI";break;
			   case 146 : descrizioneAltreSpecie="AVICOLI MISTI";break;
			   case 128 : descrizioneAltreSpecie="CONIGLI";break;
			   case 129 : descrizioneAltreSpecie="BUFALINI";break;
			   case 121 : descrizioneAltreSpecie="BOVINI";break;
			   case 126 : descrizioneAltreSpecie="CAVALLI";break;
			   case 124 : descrizioneAltreSpecie="OVINI";break;
			   case 134 : descrizioneAltreSpecie="QUAGLIE";break;
			   case 160 : descrizioneAltreSpecie="PESCI";break;
			   }
		   }
		   context.getRequest().setAttribute("descrizioneAltreSpecie", descrizioneAltreSpecie);

		 //cerco checklist esistente
		   
		   org.aspcf.modules.checklist_benessere.base.v4.ChecklistIstanza_Generica chk = new org.aspcf.modules.checklist_benessere.base.v4.ChecklistIstanza_Generica(db,Integer.parseInt(ticketId), codAllegato);
		   idChkBnsModIst = chk.getIdChkBnsModIst();
		   context.getRequest().setAttribute("ChecklistIstanza", chk);
		   
		   if (numAllegato == 1){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_AltreSpecie_OK";
		   }
		   else if (numAllegato == 2){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Galline_OK";
		   }
		   else if (numAllegato == 4){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Vitelli_OK";
		   }
		   else if (numAllegato == 5){
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Broiler_OK";
		   }
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   ArrayList<org.aspcf.modules.checklist_benessere.base.v4.Domanda> domande = new ArrayList<org.aspcf.modules.checklist_benessere.base.v4.Domanda>();
		   PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v4(?, ?, ?, ?)");
		   pst.setInt(1, specieChecklist);
		   pst.setInt(2, numAllegato);
		   pst.setInt(3, versioneChecklist);
		   pst.setInt(4, controllo.getId());
		   ResultSet rs = pst.executeQuery();
		   while (rs.next()){
			   org.aspcf.modules.checklist_benessere.base.v4.Domanda domanda = new org.aspcf.modules.checklist_benessere.base.v4.Domanda(db, rs, idChkBnsModIst); 
			   domande.add(domanda);
		   }
		   context.getRequest().setAttribute("DomandeList",domande);

		   
		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		   
		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   
		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(idStabilimento);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		   
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("AzFields", fields);

		  
			 //Caricamento dei Tipi di Irregolarita': Requisito - Definizione dei requisiti
			   String idAllegato = Integer.toString(Capitolo.getIdAllegato(db,specieChecklist, versioneChecklist));
			   TicketList listConf = new TicketList();
			   
			   if (orgId!=null && !orgId.equals(""))
				   listConf.setOrgId(orgId);
			   else if (idStabilimento!=null && !idStabilimento.equals(""))
				   listConf.setIdStabilimento(Integer.parseInt(idStabilimento));
			  
			   listConf.buildListControlli(db, orgId,ticketId);

			   Iterator nonConfIterator = listConf.iterator();
			   String esito = "";
			   if(nonConfIterator.hasNext()) {

				   esito = "NonFavorevole";
			   }else {
				   esito = "Favorevole";
			   }
			   
			   context.getRequest().setAttribute("esito", esito);
			   context.getRequest().setAttribute("idAlleg",idAllegato);
			   context.getRequest().setAttribute("codice_specie",String.valueOf(specieChecklist));
			   context.getRequest().setAttribute("specie",String.valueOf(specie));

		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
	   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	   //if (numAllegato.equals("15")) 
	    return redirectTo;
	   
	   
	  
	   
	   
   }
   
public String executeCommandAddSchedaAllegato_Ver6(ActionContext context){
	   
	   String url = context.getRequest().getParameter("url");
     String orgId = context.getRequest().getParameter("orgId");
     String idStabilimento = context.getRequest().getParameter("idStabilimento");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   int numAllegato = -1;
	   int versioneChecklist = -1;
	   int specieChecklist = -1;
	   int codAllegato = -1;
	   int idChkBnsModIst = -1;
	   Connection db = null;
	   String redirectTo = "";

	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
	
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];
		   
		   if (numAllegato == 7){ //ovicaprini
			   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Ovicaprini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Ovicaprini(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Ovicaprini_OK";
		   }
		   
		   if (numAllegato == 20){ //suini
			   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Suini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Suini(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Suini_OK";
		   }
		   
		   if (numAllegato == 6){ //bovini bufalini
			   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_BoviniBufalini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_BoviniBufalini(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_BoviniBufalini_OK";
		   }
		   
		   if (numAllegato == 30){ //vitelli
			   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Vitelli chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Vitelli(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Vitelli_OK";
		   }
		   
		   if (numAllegato == 10){ //galline
			   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Galline chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Galline(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Galline_OK";
		   }
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   ArrayList<org.aspcf.modules.checklist_benessere.base.v6.Domanda> domande = new ArrayList<org.aspcf.modules.checklist_benessere.base.v6.Domanda>();
		   PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v6(?, ?, ?, ?)");
		   pst.setInt(1, specieChecklist);
		   pst.setInt(2, numAllegato);
		   pst.setInt(3, versioneChecklist);
		   pst.setInt(4, controllo.getId());
		   ResultSet rs = pst.executeQuery();
		   while (rs.next()){
			   org.aspcf.modules.checklist_benessere.base.v6.Domanda domanda = new org.aspcf.modules.checklist_benessere.base.v6.Domanda(db, rs, idChkBnsModIst); 
			   domande.add(domanda);
		   }
		   context.getRequest().setAttribute("DomandeList",domande);

		   
		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		   
		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   
		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(idStabilimento);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		   
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("AzFields", fields);

		  
			 //Caricamento dei Tipi di Irregolarita': Requisito - Definizione dei requisiti
			   String idAllegato = Integer.toString(Capitolo.getIdAllegato(db,specieChecklist, versioneChecklist));
			   TicketList listConf = new TicketList();
			   
			   if (orgId!=null && !orgId.equals(""))
				   listConf.setOrgId(orgId);
			   else if (idStabilimento!=null && !idStabilimento.equals(""))
				   listConf.setIdStabilimento(Integer.parseInt(idStabilimento));
			  
			   listConf.buildListControlli(db, orgId,ticketId);

			   Iterator nonConfIterator = listConf.iterator();
			   String esito = "";
			   if(nonConfIterator.hasNext()) {

				   esito = "NonFavorevole";
			   }else {
				   esito = "Favorevole";
			   }
			   
			   context.getRequest().setAttribute("esito", esito);
			   context.getRequest().setAttribute("idAlleg",idAllegato);
			   context.getRequest().setAttribute("codice_specie",String.valueOf(specieChecklist));
		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
	   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	   //if (numAllegato.equals("15")) 
	    return redirectTo;
	   
	   
	  
	   
	   
}

    public String executeCommandAddSchedaAllegato_Ver5(ActionContext context){
	   
	   String url = context.getRequest().getParameter("url");
        String orgId = context.getRequest().getParameter("orgId");
        String idStabilimento = context.getRequest().getParameter("idStabilimento");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   int numAllegato = -1;
	   int versioneChecklist = -1;
	   int specieChecklist = -1;
	   int codAllegato = -1;
	   int idChkBnsModIst = -1;
	   Connection db = null;
	   String redirectTo = "";

	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
	
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];
		   
		   //cerco checklist esistente
		   if (numAllegato == 20){ //suini
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Suini chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Suini(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Suini_OK";
		   }
		   
		   if (numAllegato == 6){ //bovini bufalini
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_BoviniBufalini chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_BoviniBufalini(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_BoviniBufalini_OK";
		   }
		   
		   if (numAllegato == 30){ //vitelli
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Vitelli chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Vitelli(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Vitelli_OK";
		   }
		   
		   if (numAllegato == 10){ //galline
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Galline chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Galline(db,Integer.parseInt(ticketId), codAllegato);
			   idChkBnsModIst = chk.getIdChkBnsModIst();
			   context.getRequest().setAttribute("ChecklistIstanza", chk);
			   redirectTo =  "ViewAllegato_Ver"+versioneChecklist+"_Galline_OK";
		   }
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   ArrayList<org.aspcf.modules.checklist_benessere.base.v5.Domanda> domande = new ArrayList<org.aspcf.modules.checklist_benessere.base.v5.Domanda>();
		   PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v5(?, ?, ?, ?)");
		   pst.setInt(1, specieChecklist);
		   pst.setInt(2, numAllegato);
		   pst.setInt(3, versioneChecklist);
		   pst.setInt(4, controllo.getId());
		   ResultSet rs = pst.executeQuery();
		   while (rs.next()){
			   org.aspcf.modules.checklist_benessere.base.v5.Domanda domanda = new org.aspcf.modules.checklist_benessere.base.v5.Domanda(db, rs, idChkBnsModIst); 
			   domande.add(domanda);
		   }
		   context.getRequest().setAttribute("DomandeList",domande);

		   
		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		   
		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   
		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(idStabilimento);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		   
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("AzFields", fields);

		  
			 //Caricamento dei Tipi di Irregolarita': Requisito - Definizione dei requisiti
			   String idAllegato = Integer.toString(Capitolo.getIdAllegato(db,specieChecklist, versioneChecklist));
			   TicketList listConf = new TicketList();
			   
			   if (orgId!=null && !orgId.equals(""))
				   listConf.setOrgId(orgId);
			   else if (idStabilimento!=null && !idStabilimento.equals(""))
				   listConf.setIdStabilimento(Integer.parseInt(idStabilimento));
			  
			   listConf.buildListControlli(db, orgId,ticketId);

			   Iterator nonConfIterator = listConf.iterator();
			   String esito = "";
			   if(nonConfIterator.hasNext()) {

				   esito = "NonFavorevole";
			   }else {
				   esito = "Favorevole";
			   }
			   
			   context.getRequest().setAttribute("esito", esito);
			   context.getRequest().setAttribute("idAlleg",idAllegato);
			   context.getRequest().setAttribute("codice_specie",String.valueOf(specieChecklist));
		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
	   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	   //if (numAllegato.equals("15")) 
	    return redirectTo;
	   
	   
	  
	   
	   
   }
   
   
//public boolean getDataControllo(Connection db, String ticketId) {
//	// TODO Auto-generated method stub
//	 String sql = "select assigned_date as data_controllo from ticket where ticketid = ? ";
//	 boolean nuova_gestione = false;
//	 Timestamp timestamp = Timestamp.valueOf("2014-07-01 00:00:00.0");
//
//	 PreparedStatement pst;
//	try {
//		pst = db.prepareStatement(sql.toString());
//		pst.setInt(1,Integer.parseInt(ticketId));
//		 ResultSet rs = null;
//		 rs = pst.executeQuery();
//		 while (rs.next()){
//			 
//			   if(rs.getTimestamp("data_controllo").after(timestamp)){
//				   nuova_gestione = true;
//			   }
//		 }
//		 pst.close();
//		 rs.close();
//		 
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	return nuova_gestione;
//	 	
//}
//
//public boolean getDataControlloCgo(Connection db, String ticketId) {
//	// TODO Auto-generated method stub
//	 String sql = "select assigned_date as data_controllo from ticket where ticketid = ? ";
//	 boolean gestione_cgo = false;
//	 Timestamp timestamp = Timestamp.valueOf("2017-05-10 00:00:00.0");
//
//	 PreparedStatement pst;
//	try {
//		pst = db.prepareStatement(sql.toString());
//		pst.setInt(1,Integer.parseInt(ticketId));
//		 ResultSet rs = null;
//		 rs = pst.executeQuery();
//		 while (rs.next()){
//			 
//			   if(rs.getTimestamp("data_controllo").after(timestamp)){
//				   gestione_cgo = true;
//			   }
//		 }
//		 pst.close();
//		 rs.close();
//		 
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	return gestione_cgo;
//	 	
//}

public int[] getVersioneChecklist(Connection db, String ticketId, int specie) {
	// TODO Auto-generated method stub
	 String sql = "select * from get_versione_checklist(?, ?)";
	 int versione = -1;
	 int numAllegato = -1;
	 int codiceSpecie = -1;
	 int codAllegato = -1;
	 PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
		pst.setInt(1,Integer.parseInt(ticketId));
		pst.setInt(2,specie);

		 ResultSet rs = null;
		 rs = pst.executeQuery();
		 while (rs.next()){
			 versione = rs.getInt("versione");
			 numAllegato = rs.getInt("num_allegato");
			 codiceSpecie = rs.getInt("codice_specie");
			 codAllegato = rs.getInt("code_allegato");
		 }
		 pst.close();
		 rs.close();
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 int info[] = {versione, numAllegato, codiceSpecie, codAllegato};
	 return info;
}

public String executeCommandInsertChecklistBenessere(ActionContext context){
	   
	   //String url = context.getRequest().getParameter("url");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   String orgId = context.getRequest().getParameter("orgId");
	   String stabId = context.getRequest().getParameter("stabId");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   String bozza =  context.getRequest().getParameter("bozza");
	   int versioneChecklist = -1;
	   int numAllegato = -1;
	   int specieChecklist = -1;

	  
	   Connection db = null;
//	   boolean nuova_gestione = false;
//	   boolean gestione_cgo = false;
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
		   
//		   nuova_gestione = this.getDataControllo(db, ticketId);
//		   
//		   gestione_cgo = this.getDataControlloCgo(db, ticketId);
//		   context.getRequest().setAttribute("gestione_cgo", String.valueOf(gestione_cgo));

		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   
		   if (versioneChecklist == 6)
			   return executeCommandInsertChecklistBenessere_Ver6(context);
		   
		   if (versioneChecklist == 5)
			   return executeCommandInsertChecklistBenessere_Ver5(context);
		  
		   if (numAllegato!=20 && versioneChecklist == 4)
			   return executeCommandInsertChecklistBenessere_Ver4(context);
		   
		   if (numAllegato!=20 && versioneChecklist == 3)
			   return executeCommandInsertChecklistBenessere_Ver3(context);
		   
		   if(versioneChecklist == 2){ //NUOVE
			
			   try {
					 popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
					}
					catch (Exception e){
						e.printStackTrace();
					}
			   
			   //Per le specie Vitelli Allegato IV...sn vitelli...
			  /* if(specie == 1211) {
				   numAllegato = "4";
			   }
			   //galline ovaiole
			   else if(specie ==131) {
				   numAllegato = "2";
			   }
			   //suini
			   else if(specie == 122 ){
				   numAllegato = "3";
			   }
			   else if(specie == -1 ) {
				   numAllegato = "15";
				  }
			   else if(specie == 1461 ) {
				   numAllegato = "5";
		   }
			   //altre specie
			   else {
//				   if (specie==1461){ 
//					   context.getRequest().setAttribute("AltreSpecie", "POLLI DA CARNE");
//				   }
				   numAllegato = "1";
				   specie = -2;
			   }*/
		   }
		   else if (versioneChecklist == 1){ //vecchie
			   //Per le Galline Ovaiole allegato I
			 /*  if(specie == 131){
				   numAllegato = "1";
			   }
			   //Per le specie SUINI Allegato II
			   else if(specie == 122) {
				   numAllegato = "2";
			   } 
			   
			   //Per le specie Vitelli Allegato III...sn bovini...
			   else if(specie == 1211 ) {
				   numAllegato = "3";
			   }
			   
			   else if(specie == 1461 ) {
				   numAllegato = "5";
			   }
			   else {
				   if(specie == -1 ) {
					   numAllegato = "15";
				   }
				   else
				   numAllegato = "4";
			   }*/
		   }//fine vecchia gestione
		   else if (versioneChecklist == 3){ //NUOVE CON CGO
			   //NUOVE
				
			   try {
					 popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
					}
					catch (Exception e){
						e.printStackTrace();
					}
			   
			   //Per le specie Vitelli Allegato IV...sn vitelli...
		/*	   if(specie == 1211) {
				   numAllegato = "4";
			   }
			   //galline ovaiole
			   else if(specie ==131) {
				   numAllegato = "2";
			   }
			   //suini
			   else if(specie == 122 ){
				   numAllegato = "3";
			   }
			   else if(specie == -1 ) {
				   numAllegato = "15";
				  }
			   else if(specie == 1461 ) {
				   numAllegato = "5";
		   }
			   //altre specie
			   else {
//				   if (specie==1461){ 
//					   context.getRequest().setAttribute("AltreSpecie", "POLLI DA CARNE");
//				   }
				   numAllegato = "1";
				   specie = -2;
			   }
		   */
		   }
		   
		   else if(versioneChecklist == 4){ //CGO 2018
			 	   try {
						popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
						}
						catch (Exception e){
							e.printStackTrace();
						}
	   }
		   
		   boolean recordInserted = false;
		   boolean isValid = false;
		
		   UserBean user = (UserBean) context.getSession().getAttribute("User");
		   ChecklistIstanza chk = (ChecklistIstanza) context.getFormBean();
		   chk.setEnteredBy(getUserId(context));
		   chk.setModifiedBy(getUserId(context));
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		   context.getRequest().setAttribute("Ticket",controllo);
		   
		   /*Gestioneparametri mod B11*/
		   if (specieChecklist == -1)
			   chk.setParametriModB11FromRequest(context.getRequest());
		   
		   if(bozza != null && !bozza.equals("")){
			   if(bozza.equals("true")){
				   chk.setBozza(true);
			   }
			   else {
				   chk.setBozza(false);
			   }
		   }
		   
		   context.getRequest().setAttribute("Aggiornato", "OK");
		   //Costruisci la lista delle risposte a partire dall'istanza
		   chk.setRisposteFromRequest(context.getRequest(), db, versioneChecklist, specieChecklist);
		   //Get domande per capitolo
			ArrayList<Integer> domandeCapitoli = new ArrayList<Integer>();
			domandeCapitoli = this.getDomandePerCapitolo(db, specieChecklist, versioneChecklist);
		    int numCapitoli = this.getNumCapitoli(db, specieChecklist, versioneChecklist);
		    
			org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

			   int orgIdInt = -1;
			   int stabIdInt = -1;
			   
			   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
			   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}

			   if (orgIdInt>0)
				   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
			   else if (stabIdInt>0)
				   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
			   
			context.getRequest().setAttribute("Allevamento",allevamento);
			
			  AziendeZootFields fields = new AziendeZootFields();
			   fields.queryRecord(db, Integer.parseInt(ticketId));
			   context.getRequest().setAttribute("AzFields", fields);
			
		   try {
			isValid = this.validateObject(context, db, chk);
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   
		   if (isValid) {
				recordInserted = chk.insert(db,context);
				
				gestioneCampiCgo(chk, versioneChecklist, numAllegato, context, db);
				
//				if(bozza != null && bozza.equals("true")){
//					context.getRequest().setAttribute("chiudi", "si");
//				}
//				else {
//					context.getRequest().setAttribute("chiudi", "no");
//				}
				context.getRequest().setAttribute("chiudi", "si");	
	
		   }
		   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("domandePerCapitolo", domandeCapitoli);
		   context.getRequest().setAttribute("numCapitoli", Integer.toString(numCapitoli));
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	   if (numAllegato==15) 
		   return "InsertAllegatoB11OK";
	   
		   return "InsertAllegatoOK";
		  
	   
   }

public String executeCommandInsertChecklistBenessere_Ver3(ActionContext context){
	   
	   //String url = context.getRequest().getParameter("url");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   String orgId = context.getRequest().getParameter("orgId");
	   String stabId = context.getRequest().getParameter("stabId");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   String descrizioneAltreSpecie = context.getRequest().getParameter("descrizioneAltreSpecie");
	   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
	   int versioneChecklist = -1;
	   int numAllegato = -1; 
	   int specieChecklist = -1;
	   int codAllegato = -1;

	   Connection db = null;
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
	       context.getRequest().setAttribute("Ticket",controllo);
		   
	       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   int ticketIdInt = -1;

		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}
		   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];

		   boolean recordInserted = false;
		   boolean isValid = false;
		
		   UserBean user = (UserBean) context.getSession().getAttribute("User");
		   
		 
		   org.aspcf.modules.checklist_benessere.base.v3.ChecklistIstanza_Generica chk = new org.aspcf.modules.checklist_benessere.base.v3.ChecklistIstanza_Generica();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   context.getRequest().setAttribute("descrizioneAltreSpecie", descrizioneAltreSpecie);
		   

	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	  
	   return executeCommandAddSchedaAllegato_Ver3(context);
}

public String executeCommandInsertChecklistBenessere_Ver4(ActionContext context){
	   
	   //String url = context.getRequest().getParameter("url");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   String orgId = context.getRequest().getParameter("orgId");
	   String stabId = context.getRequest().getParameter("stabId");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   String descrizioneAltreSpecie = context.getRequest().getParameter("descrizioneAltreSpecie");
	   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
	   int versioneChecklist = -1;
	   int numAllegato = -1; 
	   int specieChecklist = -1;
	   int codAllegato = -1;

	   Connection db = null;
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
	       context.getRequest().setAttribute("Ticket",controllo);
		   
	       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   int ticketIdInt = -1;

		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}
		   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];

		   boolean recordInserted = false;
		   boolean isValid = false;
		
		   UserBean user = (UserBean) context.getSession().getAttribute("User");
		   
		 
		   org.aspcf.modules.checklist_benessere.base.v4.ChecklistIstanza_Generica chk = new org.aspcf.modules.checklist_benessere.base.v4.ChecklistIstanza_Generica();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   context.getRequest().setAttribute("descrizioneAltreSpecie", descrizioneAltreSpecie);
		   
 
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	  
	   return executeCommandAddSchedaAllegato_Ver4(context);
}
   
public String executeCommandInsertChecklistBenessere_Ver5(ActionContext context){
	   
	   //String url = context.getRequest().getParameter("url");
	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	   String orgId = context.getRequest().getParameter("orgId");
	   String stabId = context.getRequest().getParameter("stabId");
	   String ticketId = context.getRequest().getParameter("idControllo");
	   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
	   int versioneChecklist = -1;
	   int numAllegato = -1; 
	   int specieChecklist = -1;
	   int codAllegato = -1;

	   Connection db = null;
	   int infoChecklist [] = {-1, -1, -1, -1};
	   
	   try {
		   
		   db = this.getConnection(context);
		   
		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
	       context.getRequest().setAttribute("Ticket",controllo);
		   
	       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

		   int orgIdInt = -1;
		   int stabIdInt = -1;
		   int ticketIdInt = -1;

		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}
		   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

		   if (orgIdInt>0)
			   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
		   else if (stabIdInt>0)
			   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
		   
		   context.getRequest().setAttribute("Allevamento",allevamento);
		
		   AziendeZootFields fields = new AziendeZootFields();
		   fields.queryRecord(db, Integer.parseInt(ticketId));
		   
		   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
		   
		   versioneChecklist = infoChecklist[0];
		   numAllegato = infoChecklist[1];
		   specieChecklist = infoChecklist[2];
		   codAllegato = infoChecklist[3];

		   boolean recordInserted = false;
		   boolean isValid = false;
		
		   UserBean user = (UserBean) context.getSession().getAttribute("User");
		   
		   if (numAllegato==20){ //suini
			   
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Suini chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Suini();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   }
		   
		   if (numAllegato==6){ //bovini bufalini
			   
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_BoviniBufalini chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_BoviniBufalini();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   }
		   
		   if (numAllegato==30){ //vitelli
			   
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Vitelli chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Vitelli();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   }
		   
		   if (numAllegato==10){ //galline
			   
			   org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Galline chk = new org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Galline();
			   chk.setOrgId(orgIdInt);
			   chk.setNumAllegato(numAllegato);
			   chk.setCodAllegato(codAllegato);
			   chk.setIdSpecie(specie);
			   chk.setIdVersione(versioneChecklist);
			   chk.setIdCu(ticketIdInt);
			   chk.setBozza(bozza);
			   chk.setEnteredBy(getUserId(context));
			   chk.recuperaDaForm(context, db);
			   chk.upsert(db);
		
			   
		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
		   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		   }
		   
   
	   }catch(SQLException sql){
		   sql.printStackTrace();
	   }finally{
		   this.freeConnection(context, db);
	   }
	  
	   return executeCommandAddSchedaAllegato_Ver5(context);
}



	public String executeCommandUpdateChecklistBenessere(ActionContext context)throws SQLException {
	   
	
		Connection db = null;
		int resultCount = -1;

		String orgId = context.getRequest().getParameter("orgId");
		String stabId = context.getRequest().getParameter("stabId");
		String ticketId = context.getRequest().getParameter("idControllo");
		String bozza = context.getRequest().getParameter("bozza");
		int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
		ChecklistIstanza newChk = (ChecklistIstanza) context.getFormBean();
		ChecklistIstanza oldChk = null;
		
		 if(bozza != null && !bozza.equals("")){
			   if(bozza.equals("true")){
				   newChk.setBozza(true);
			   }
			   else {
				   newChk.setBozza(false);
			   }
		   }
		
		newChk.setModifiedBy(getUserId(context));
		newChk.setEnteredBy(getUserId(context));
		   if (specie == -1)
			   newChk.setParametriModB11FromRequest(context.getRequest());
		
		 int numAllegato = -1;
		 int versioneChecklist = -1;
		 int specieChecklist = -1;
		 
//		 boolean nuova_gestione = false;
//		 boolean gestione_cgo = false;
//		  
		   int infoChecklist [] = {-1, -1, -1, -1};
		 
		//Get domande per capitolo
		   
		   context.getRequest().setAttribute("Aggiornato", "OK");
		
		try {
			
			db = this.getConnection(context);
			   
			   
//			   nuova_gestione = this.getDataControllo(db, ticketId);
//			   
//			   gestione_cgo = this.getDataControlloCgo(db, ticketId);
//			   context.getRequest().setAttribute("gestione_cgo", String.valueOf(gestione_cgo));

			   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
			   
			   versioneChecklist = infoChecklist[0];
			   numAllegato = infoChecklist[1];
			   specieChecklist = infoChecklist[2];
			 
			 if(versioneChecklist == 2){ //NUOVE
				
				 try {
				 popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
				 
				   //Per le specie Vitelli Allegato IV...sn vitelli...
				  /* if(specie == 1211) {
					   numAllegato = "4";
				   }
				   //galline ovaiole
				   else if(specie ==131) {
					   numAllegato = "2";
				   }
				   //suini
				   else if(specie == 122 ){
					   numAllegato = "3";
				   }
				   else if(specie == 1461 ) {
					   numAllegato = "5";
			   }
				   //altre specie
				   else {
					   if(specie == -1 ) {
						   numAllegato = "15";
					   }
					   else{
					   numAllegato = "1";
					   specie = -2;
					   }
				   }
			 */  }
			   else  if(versioneChecklist == 1){ //VECCHIE
				   //Per le Galline Ovaiole allegato I
				/*   if(specie == 131){
					   numAllegato = "1";
				   }
				   //Per le specie SUINI Allegato II
				   else if(specie == 122) {
					   numAllegato = "2";
				   } 
				   
				   //Per le specie Vitelli Allegato III...sn bovini...
				   else if(specie == 1211 ) {
					   numAllegato = "3";
				   }
				   
				   else if(specie == 1461 ) {
					   numAllegato = "5";
				   }
				   else {
					   if(specie == -1 ) {
						   numAllegato = "15";
					   }
					   else
					   numAllegato = "4";
				   }*/
			   }//fine vecchia gestione
			   else if (versioneChecklist == 3) { //NUOVE CON CGO
				   //NUOVE
					
					 try {
					 popolaListaRiscontro(context, db, Integer.parseInt(ticketId), specieChecklist);
					}
					catch (Exception e){
						e.printStackTrace();
					}
					
					 
					   //Per le specie Vitelli Allegato IV...sn vitelli...
			/*		   if(specie == 1211) {
						   numAllegato = "4";
					   }
					   //galline ovaiole
					   else if(specie ==131) {
						   numAllegato = "2";
					   }
					   //suini
					   else if(specie == 122 ){
						   numAllegato = "3";
					   }
					   else if(specie == 1461 ) {
						   numAllegato = "5";
				   }
					   //altre specie
					   else {
						   if(specie == -1 ) {
							   numAllegato = "15";
						   }
						   else{
						   numAllegato = "1";
						   specie = -2;
						   }
					   }
				  */ 
			   }
			 
			 
			//Get domande per capitolo
			ArrayList<Integer> domandeCapitoli = new ArrayList<Integer>();
			domandeCapitoli = this.getDomandePerCapitoloGiaRisposte(db, Integer.parseInt(ticketId), specieChecklist);
			int numCapitoli = this.getNumCapitoliGiaRisposte(db, Integer.parseInt(ticketId), specieChecklist);
			newChk.setRisposteFromRequestInseriti(context.getRequest(), db, Integer.parseInt(ticketId), specieChecklist);
			//newChk.setIdAlleg(numAllegato);
			oldChk = new ChecklistIstanza(db, Integer.parseInt(ticketId), specieChecklist);
			
		    resultCount = newChk.update(db);
		    
			gestioneCampiCgo(oldChk, versioneChecklist, numAllegato, context, db);

//		    if(bozza != null && bozza.equals("true")){
//		    	context.getRequest().setAttribute("chiudi", "no");
//		    }else {
//		    	context.getRequest().setAttribute("chiudi", "si");
//		    }
		    context.getRequest().setAttribute("chiudi", "si"); 

			if (resultCount == 1) {
				processUpdateHook(context, oldChk, newChk);
				
			}
			
			org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
			
			 
			   int orgIdInt = -1;
			   int stabIdInt = -1;
			   
			   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
			   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}

			   if (orgIdInt>0)
				   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
			   else if (stabIdInt>0)
				   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
				
			  AziendeZootFields fields = new AziendeZootFields();
			   fields.queryRecord(db, Integer.parseInt(ticketId));
			   context.getRequest().setAttribute("AzFields", fields);
			   
			context.getRequest().setAttribute("Allevamento",allevamento);
			context.getRequest().setAttribute("ChecklistIstanza",newChk);
			context.getRequest().setAttribute("domandePerCapitolo", domandeCapitoli);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			context.getRequest().setAttribute("numCapitoli", Integer.toString(numCapitoli));
			context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));

	} catch (Exception errorMessage) {

		context.getRequest().setAttribute("Error", errorMessage);
		errorMessage.printStackTrace();
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	
	//addModuleBean(context, "View Accounts", "Update Checklist");
		if (specieChecklist != -1)
		return "UpdateAllegatoOK";
		return "UpdateAllegatoB11OK";
	}
		
	public String executeCommandInsertChecklistBenessere_Ver6(ActionContext context){
		   
		   //String url = context.getRequest().getParameter("url");
		   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
		   String orgId = context.getRequest().getParameter("orgId");
		   String stabId = context.getRequest().getParameter("stabId");
		   String ticketId = context.getRequest().getParameter("idControllo");
		   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
		   int versioneChecklist = -1;
		   int numAllegato = -1; 
		   int specieChecklist = -1;
		   int codAllegato = -1;

		   Connection db = null;
		   int infoChecklist [] = {-1, -1, -1, -1};
		   
		   try {
			   
			   db = this.getConnection(context);
			   
			   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		       context.getRequest().setAttribute("Ticket",controllo);
			   
		       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

			   int orgIdInt = -1;
			   int stabIdInt = -1;
			   int ticketIdInt = -1;

			   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
			   try {stabIdInt = Integer.parseInt(stabId);} catch (Exception e) {}
			   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

			   if (orgIdInt>0)
				   allevamento = this.getDatiAllevamento(db,allevamento,orgIdInt,Integer.parseInt(ticketId));
			   else if (stabIdInt>0)
				   allevamento = this.getDatiAllevamentoOpu(db,allevamento,stabIdInt,Integer.parseInt(ticketId));
			   
			   context.getRequest().setAttribute("Allevamento",allevamento);
			
			   AziendeZootFields fields = new AziendeZootFields();
			   fields.queryRecord(db, Integer.parseInt(ticketId));
			   
			   infoChecklist = this.getVersioneChecklist(db, ticketId, specie);
			   
			   versioneChecklist = infoChecklist[0];
			   numAllegato = infoChecklist[1];
			   specieChecklist = infoChecklist[2];
			   codAllegato = infoChecklist[3];

			   boolean recordInserted = false;
			   boolean isValid = false;
			
			   UserBean user = (UserBean) context.getSession().getAttribute("User");
			   
			   if (numAllegato==7){ //ovicaprini
				   
				   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Ovicaprini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Ovicaprini();
				   chk.setOrgId(orgIdInt);
				   chk.setNumAllegato(numAllegato);
				   chk.setCodAllegato(codAllegato);
				   chk.setIdSpecie(specie);
				   chk.setIdVersione(versioneChecklist);
				   chk.setIdCu(ticketIdInt);
				   chk.setBozza(bozza);
				   chk.setEnteredBy(getUserId(context));
				   chk.recuperaDaForm(context, db);
				   chk.upsert(db);
			
				   
			   context.getRequest().setAttribute("ChecklistIstanza",chk);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
			   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
			   }
			   
			   if (numAllegato==20){ //suini
				   
				   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Suini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Suini();
				   chk.setOrgId(orgIdInt);
				   chk.setNumAllegato(numAllegato);
				   chk.setCodAllegato(codAllegato);
				   chk.setIdSpecie(specie);
				   chk.setIdVersione(versioneChecklist);
				   chk.setIdCu(ticketIdInt);
				   chk.setBozza(bozza);
				   chk.setEnteredBy(getUserId(context));
				   chk.recuperaDaForm(context, db);
				   chk.upsert(db);
			
				   
			   context.getRequest().setAttribute("ChecklistIstanza",chk);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
			   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
			   }
			   
			   if (numAllegato==6){ //bovini bufalini
				   
				   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_BoviniBufalini chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_BoviniBufalini();
				   chk.setOrgId(orgIdInt);
				   chk.setNumAllegato(numAllegato);
				   chk.setCodAllegato(codAllegato);
				   chk.setIdSpecie(specie);
				   chk.setIdVersione(versioneChecklist);
				   chk.setIdCu(ticketIdInt);
				   chk.setBozza(bozza);
				   chk.setEnteredBy(getUserId(context));
				   chk.recuperaDaForm(context, db);
				   chk.upsert(db);
			
				   
			   context.getRequest().setAttribute("ChecklistIstanza",chk);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
			   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
			   }
			   
			   if (numAllegato==30){ //vitelli
				   
				   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Vitelli chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Vitelli();
				   chk.setOrgId(orgIdInt);
				   chk.setNumAllegato(numAllegato);
				   chk.setCodAllegato(codAllegato);
				   chk.setIdSpecie(specie);
				   chk.setIdVersione(versioneChecklist);
				   chk.setIdCu(ticketIdInt);
				   chk.setBozza(bozza);
				   chk.setEnteredBy(getUserId(context));
				   chk.recuperaDaForm(context, db);
				   chk.upsert(db);
			
				   
			   context.getRequest().setAttribute("ChecklistIstanza",chk);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
			   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
			   }
			   
			   if (numAllegato==10){ //galline
				   
				   org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Galline chk = new org.aspcf.modules.checklist_benessere.base.v6.ChecklistIstanza_Galline();
				   chk.setOrgId(orgIdInt);
				   chk.setNumAllegato(numAllegato);
				   chk.setCodAllegato(codAllegato);
				   chk.setIdSpecie(specie);
				   chk.setIdVersione(versioneChecklist);
				   chk.setIdCu(ticketIdInt);
				   chk.setBozza(bozza);
				   chk.setEnteredBy(getUserId(context));
				   chk.recuperaDaForm(context, db);
				   chk.upsert(db);
			
				   
			   context.getRequest().setAttribute("ChecklistIstanza",chk);
			   context.getRequest().setAttribute("numAllegato",String.valueOf(numAllegato));
			   context.getRequest().setAttribute("versioneChecklist", String.valueOf(versioneChecklist));
			   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
			   }
	   
		   }catch(SQLException sql){
			   sql.printStackTrace();
		   }finally{
			   this.freeConnection(context, db);
		   }
		  
		   return executeCommandAddSchedaAllegato_Ver6(context);
	}



	


   public String executeCommandPrintEtichette(ActionContext context){
		
		 String selectedValue = (String) context.getRequest().getParameter("selectMod");
		 String barcodeAnalita = null;
		 context.getRequest().setAttribute("value",selectedValue);
		 String reportDir = getWebInfPath(context, "template_report");
		 String numVerbale = (String)context.getRequest().getParameter("num_verbale");
		 String motivo = (String) context.getRequest().getParameter("quesiti_diagnostici_sigla");
		 
		 ArrayList<String> analiti = new ArrayList<String>(); 
		 ManagePdfBase pdfBase =  new ManagePdfBase();  
		 java.sql.Connection db = null;
		 try{
			 db = this.getConnection(context);
			 
			 if(Integer.parseInt(selectedValue) == 5)
			 {
				 if(context.getRequest().getParameter("size")!=null)
				 {
						int size = Integer.parseInt(context.getRequest().getParameter("size"));
						for (int i = 1 ; i<=size; i++)
						{
							if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
							{
							int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
							barcodeAnalita = pdfBase.retrieveCode(db, idFoglia, "analita");
							analiti.add(barcodeAnalita);	
							}
							
						}
					
				  }
			 }
			 
			 if(analiti.size() > 0){
				 context.getResponse().setContentType( "application/zip" );
				 context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + "Analiti.zip" + "\";" ); 
				 ZipOutputStream out = new ZipOutputStream(context.getResponse().getOutputStream()); 
				 
				 for(int j=0; j < analiti.size(); j++){
					 
				 
					 byte[] buf = new byte[1024]; 
					 // Create the ZIP file  
				
					 // Compress the files 
					 BufferedImage img = pdfBase.createBarcodeImage(analiti.get(j));
				 
					 File outputfile = new File(reportDir+"/"+analiti.get(j)+".png");
					 ImageIO.write(img, "png", outputfile); 
					 FileInputStream fis = new FileInputStream(outputfile);
			     
					 BufferedInputStream in = new BufferedInputStream(fis);
					 out.putNextEntry(new ZipEntry(outputfile.getName())); //"image.jpg"
					 // Transfer bytes from the file to the ZIP file 
					 int len; 
					 while ((len = in.read(buf)) > 0) { 
						 out.write(buf, 0, len); 
					 } 
				    // Complete the entry 	 
					 in.close(); 
				
				 } 
				 out.close();
			 }
			
			 if(Integer.parseInt(selectedValue) == 4)
			 {
			 
				   String barcodeMatrice = "";
					if(context.getRequest().getParameter("size1")!=null)
					{
						int size = Integer.parseInt(context.getRequest().getParameter("size1"));
						for (int i = 1 ; i<=size; i++)
						{
							if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
							{
								
								barcodeMatrice = pdfBase.retrieveCode(db,Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i)),"matrice");
							}

						}
						

						BufferedImage img = pdfBase.createBarcodeImage(barcodeMatrice);
					    File outputfile = new File(reportDir+"/"+barcodeMatrice+".png");
						ImageIO.write(img, "png", outputfile);
					    FileInputStream fis = new FileInputStream(outputfile);
						BufferedInputStream is = new BufferedInputStream(fis);
						OutputStream out = context.getResponse().getOutputStream(); 
						context.getResponse().setContentType("application/octet-stream");
						context.getResponse().setHeader("Content-Disposition","attachment;filename="+barcodeMatrice+".png");
						byte[] buf = new byte[4 * 1024]; // 4K buffer
						int bytesRead;
						while ((bytesRead = is.read(buf)) != -1) {
							   	out.write(buf, 0, bytesRead);
						}
						
						is.close();
						out.close(); 
						fis.close();  
						
					}
					   
			 }
			  
			  String barcode = null;
			  if(Integer.parseInt(selectedValue) == 1)
			  {
				    /*Genera barcode in base al testo inserito nella textarea*/
					if(numVerbale!=null && !numVerbale.equals("") ){
							barcode = numVerbale.trim(); 
					}
			  }
			  if(Integer.parseInt(selectedValue) == 2){
				  ModCampioni campione = new ModCampioni();
				  campione.retrieveCode(db, Integer.parseInt(motivo));
				  barcode = campione.getMotivazione();
			  }
			  
			  
			   BufferedImage img = pdfBase.createBarcodeImage(barcode);
			   File outputfile = new File(reportDir+"/"+barcode+".png");
			   ImageIO.write(img, "png", outputfile);
			   FileInputStream fis = new FileInputStream(outputfile);
			   BufferedInputStream is = new BufferedInputStream(new FileInputStream(outputfile));
			   OutputStream out = context.getResponse().getOutputStream(); 
			   context.getResponse().setContentType("application/octet-stream");
			   context.getResponse().setHeader("Content-Disposition","attachment;filename="+barcode+".png");
			   byte[] buf = new byte[4 * 1024]; // 4K buffer
			   int bytesRead;
			    while ((bytesRead = is.read(buf)) != -1) {
						out.write(buf, 0, bytesRead);
				}
				is.close();
				out.close(); 
				fis.close();
				  
		 
		 }catch(SQLException s){
			 s.printStackTrace();
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }finally {
	  		  	this.freeConnection(context, db);
	  	  }	
		 
		 return ("-none-");
		  
	 }
	
   public String executeCommandPrintSelectedEtichette(ActionContext context) {
		
		Connection db =  null;
		try {
			
			db = this.getConnection(context);
			LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
			quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
		} catch(SQLException s){
			s.printStackTrace();
		} 
		finally
		 {
			   this.freeConnection(context, db);
		 }
	    return ("PrintEtichetteOK");
	}
   
   public ArrayList<Integer> getDomandePerCapitolo(Connection db, int specie, int versione) throws SQLException{
	   	
	   ArrayList<Capitolo> capitoli = Capitolo.buildRecordCapitoli(db,specie, versione);
	   Iterator<Capitolo> capIt = capitoli.iterator();
	   ArrayList<Integer> domandeCapitoli = new ArrayList<Integer>();
	
	   while(capIt.hasNext()) {
		   Capitolo c = capIt.next();
		   domandeCapitoli .add(c.getDomandePerCapitolo());
	   }
	   
	   return domandeCapitoli;
   }
   
   public ArrayList<Integer> getDomandePerCapitoloGiaRisposte(Connection db, int idCU, int specie) throws SQLException{
	   
	   ArrayList<Integer> domandeCapitoli = new ArrayList<Integer>();
	   String sql = "select max(iddom) as numDom from chk_bns_risposte  r " +
	   				"left join chk_bns_mod_ist ist on r.idmodist = ist.id "+
	   				"left join lookup_chk_bns_mod mod on mod.code = ist.id_alleg " +
	   				"where ist.idcu = ? and mod.codice_specie = ? and ist.trashed_date is null " +
	   				"group by idcap " +
		   			"order by idcap ";
		   
	   PreparedStatement pst = db.prepareStatement(sql.toString());
	   pst.setInt(1,idCU);
	   pst.setInt(2,specie);
	   ResultSet rs = null;
	   rs = pst.executeQuery();
	   while (rs.next()){
		   domandeCapitoli.add(rs.getInt("numDom"));
	   }
	   pst.close();
	   rs.close();
	   
	   
	   	return domandeCapitoli;
   }
   
   public int getNumCapitoliGiaRisposte(Connection db, int idCU, int codice_specie) throws SQLException{
	   	
	   int numCapitoli = 0;
	   
	   String sql = "select max(idcap) as numCapitoli from chk_bns_risposte risp " +
	   		" left join chk_bns_mod_ist ist on ist.id = risp.idmodist " +
	   		" left join lookup_chk_bns_mod mod on mod.code = ist.id_alleg " +
	   		" where ist.idcu = ? and mod.codice_specie = ? and ist.trashed_date is null ";
	   PreparedStatement pst = db.prepareStatement(sql.toString());
	   pst.setInt(1,idCU);
	   pst.setInt(2, codice_specie);
	   ResultSet rs = null;
	   rs = pst.executeQuery();
	   while (rs.next()){
		   numCapitoli = rs.getInt("numCapitoli");
	   }
	   pst.close();
	   rs.close();
	   
	   return numCapitoli;
   }
   
   public int getNumCapitoli(Connection db, int specie, int versione) throws SQLException{
	   	
	   ArrayList<Capitolo> capitoli = Capitolo.buildRecordCapitoli(db,specie, versione);
	   int numCapitoli = capitoli.size();
	   
	   return numCapitoli; 
   }
   
   public Organization getDatiAllevamento(Connection db, Organization allevamento, int orgId, int ticketId) throws SQLException{
	   
	  
	   
	   Filtro f = new Filtro();
	   f.setOrgId(orgId);
	   f.setIdCampione(ticketId);
	   
	   ResultSet rs = f.queryRecord_chk_bns_allevamento(db); 
	   allevamento = new Organization(rs,"allevamento");
	   
	   if (allevamento.getData_referto() != null) {
			allevamento.setAnnoReferto(allevamento.getData_referto().substring(0, 4));
			allevamento.setGiornoReferto(allevamento.getData_referto().substring(8, 11));
			allevamento.setMeseReferto(allevamento.getMeseFromData(allevamento.getData_referto()));		
		}
	   
	   
	   //Utilizzo il riferimento alla nascita per comporre la data di inizio attivita'
	   if (allevamento.getData_inizio_attivita() != null) {
			allevamento.setAnnoNascita((allevamento.getData_inizio_attivita().substring(0, 4)));
			allevamento.setGiornoNascita((allevamento.getData_inizio_attivita().substring(8, 11)));
			allevamento.setMeseNascita((allevamento.getMeseFromData(allevamento.getData_inizio_attivita())));		
		}
	   
	   return allevamento;

	  
	   
   }
   
public Organization getDatiAllevamentoOpu(Connection db, Organization allevamento, int idStabilimento, int ticketId) throws SQLException{
	   
	  
	   
	   Filtro f = new Filtro();
	   f.setIdStabilimento(idStabilimento);
	   f.setIdCampione(ticketId);
	   
	   ResultSet rs = f.queryRecord_chk_bns_allevamento_opu(db); 
	   allevamento = new Organization(rs,"allevamento");
	   
	   if (allevamento.getData_referto() != null) {
			allevamento.setAnnoReferto(allevamento.getData_referto().substring(0, 4));
			allevamento.setGiornoReferto(allevamento.getData_referto().substring(8, 11));
			allevamento.setMeseReferto(allevamento.getMeseFromData(allevamento.getData_referto()));		
		}
	   
	   
	   //Utilizzo il riferimento alla nascita per comporre la data di inizio attivita'
	   if (allevamento.getData_inizio_attivita() != null) {
			allevamento.setAnnoNascita((allevamento.getData_inizio_attivita().substring(0, 4)));
			allevamento.setGiornoNascita((allevamento.getData_inizio_attivita().substring(8, 11)));
			allevamento.setMeseNascita((allevamento.getMeseFromData(allevamento.getData_inizio_attivita())));		
		}
	   
	   return allevamento;

	  
	   
   }

public Organization getDatiAllevamentoBiosicurezza(Connection db, Organization allevamento, int orgId) throws SQLException{
	   
	  
	   
	   Filtro f = new Filtro();
	   f.setOrgId(orgId);
	   
	   ResultSet rs = f.queryRecord_biosicurezza_allevamento(db); 
	   allevamento = new Organization(rs,"allevamento");
	   
	   return allevamento;

	  
	   
}

public Organization getDatiAllevamentoFarmacosorveglianza(Connection db, Organization allevamento, int orgId) throws SQLException{
	   
	  
	   
	   Filtro f = new Filtro();
	   f.setOrgId(orgId);
	   
	   ResultSet rs = f.queryRecord_farmacosorveglianza_allevamento(db); 
	   allevamento = new Organization(rs,"allevamento");
	   
	   return allevamento;

	  
	   
}
    
   public String executeCommandSalvaModello2(ActionContext context) {
		String listavalori = context.getRequest().getParameter("listavalori");
		String[] splitValori = listavalori.split(";;;");
		ArrayList<String> valoriScelti = new ArrayList<String>();
		context.getRequest().setAttribute("listavalori", listavalori);
		for (int i=1;i<splitValori.length;i=i+2)
			valoriScelti.add(splitValori[i]);
	
		
		return executeCommandViewModules(context);
	}
	
	   private void popolaModulo (ActionContext context, Connection db, int idCampione, int tipoModulo) throws SQLException {
			//Dati operatore PNAA	
			ModuloCampione modulo = new ModuloCampione();	
			modulo.setTipoModulo(tipoModulo);
			modulo.setIdCampione(idCampione);
			CampioniUtil.riempiSchedaModuloHTMLRsitrutturato(idCampione,modulo, db);
			if (modulo.getListaCampiModulo().size()==0)
				CampioniUtil.riempiSchedaModuloVUOTOHTMLRsitrutturato(tipoModulo,modulo, db);
	  		context.getRequest().setAttribute("Modulo", modulo);		
	}
    
	   private boolean popolaListaRiscontro (ActionContext context, Connection db, int idCampione, int specie) throws SQLException {
			//Dati operatore PNAA	
			ModuloControllo modulo = new ModuloControllo();	
			modulo.setSpecie(specie);
			modulo.setIdCampione(idCampione);
			CheckListAllevamenti.riempiListaRiscontroHTMLRsitrutturato(idCampione, specie, modulo, db);
	  		context.getRequest().setAttribute("Modulo", modulo);
	  		if (modulo.getListaCampiModulo().size()>0)
	  			return true;
	  		return false;
	}
	   
	   
	    private void gestioneCampiCgo(ChecklistIstanza chk, int versioneChecklist, int numAllegato,  ActionContext context, Connection db) throws SQLException {
	    	// TODO Auto-generated method stub
	    	
	    	if (numAllegato== 20 && versioneChecklist == 3){
	    		
	    		ChecklistIstanzaCGO chkCgo = new ChecklistIstanzaCGO(context);
	    		chkCgo.setIdChkBnsModIst(chk.getId());
	    		chkCgo.upsert(db);
	    		context.getRequest().setAttribute("ChecklistIstanzaCGO", chkCgo);
	    	}
	    	
	    	if (numAllegato== 20 && versioneChecklist == 4){
	    		
	    		ChecklistIstanzaCGO_2018 chkCgo = new ChecklistIstanzaCGO_2018(context);
	    		chkCgo.setIdChkBnsModIst(chk.getId());
	    		chkCgo.upsert(db);
	    		context.getRequest().setAttribute("ChecklistIstanzaCGO_2018", chkCgo);
	    	}
	    	
	      }
	    private void visualizzaCampiCgo(ChecklistIstanza chk, int versioneChecklist, int numAllegato, ActionContext context, Connection db) throws SQLException {
	    	// TODO Auto-generated method stub
	    	
	    	if (numAllegato== 20 && versioneChecklist == 3){
	    		ChecklistIstanzaCGO chkCgo = new ChecklistIstanzaCGO(db, chk.getId());
	    		context.getRequest().setAttribute("ChecklistIstanzaCGO", chkCgo);
	    	}
	    	if (numAllegato== 20 && versioneChecklist == 4){
	    		ChecklistIstanzaCGO_2018 chkCgo = new ChecklistIstanzaCGO_2018(db, chk.getId());
	    		context.getRequest().setAttribute("ChecklistIstanzaCGO_2018", chkCgo);
	    	}
	      }
	    
	    public String executeCommandSchedaBiosicurezza(ActionContext context){
	 	   
	       String orgId = context.getRequest().getParameter("orgId");
	 	   String ticketId = context.getRequest().getParameter("idControllo");
	 	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	 	   Connection db = null;
	 	    
	 	   try {
	 		   
	 		   db = this.getConnection(context);
	 	   
	 		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
	 		   context.getRequest().setAttribute("Ticket",controllo);
	 		   
	 		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
	 		   
	 		   int orgIdInt = -1;
	 		   
	 		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}

	 		   if (orgIdInt>0)
	 			   allevamento = this.getDatiAllevamentoBiosicurezza(db,allevamento,orgIdInt);
	 		   
	 		   context.getRequest().setAttribute("Allevamento",allevamento);
	 		   
	 		  org.aspcf.modules.checklist_biosicurezza.base.ChecklistIstanza chk = new org.aspcf.modules.checklist_biosicurezza.base.ChecklistIstanza(db, Integer.parseInt(ticketId), specie);
		 	  context.getRequest().setAttribute("Checklist",chk);

	 			   
	 	   }catch(SQLException sql){
	 		   sql.printStackTrace();
	 	   }finally{
	 		   this.freeConnection(context, db);
	 	   }
	 	  
	 	   return "ViewAllegato_Bio_Suini_OK";
	 	   
	    }
	    
	    public String executeCommandInsertChecklistBiosicurezza(ActionContext context){
	 	   
	 	   //String url = context.getRequest().getParameter("url");
	 	   int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
	 	   String orgId = context.getRequest().getParameter("orgId");
	 	   String ticketId = context.getRequest().getParameter("idControllo");
	 	   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
	 	   
	 	   Connection db = null;
	 	   
	 	   try {
	 		   
	 		   db = this.getConnection(context);
	 		   
	 		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
	 	       context.getRequest().setAttribute("Ticket",controllo);
	 		   
	 	       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

	 		   int orgIdInt = -1;
	 		   int ticketIdInt = -1;

	 		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
	 		   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

	 		   if (orgIdInt>0)
	 			   allevamento = this.getDatiAllevamentoBiosicurezza(db,allevamento,orgIdInt);
	 		    
	 		   context.getRequest().setAttribute("Allevamento",allevamento);
	 		
	 		   boolean recordInserted = false;
	 		   boolean isValid = false;
	 		
	 		   UserBean user = (UserBean) context.getSession().getAttribute("User");
	 		   
		 		  org.aspcf.modules.checklist_biosicurezza.base.ChecklistIstanza chk = new org.aspcf.modules.checklist_biosicurezza.base.ChecklistIstanza(db, Integer.parseInt(ticketId), specie);
    			  chk.setBozza(bozza);
    			  chk.setOrgId(orgIdInt);
    			  chk.setIdCU(Integer.parseInt(ticketId));
	 			  chk.setEnteredBy(getUserId(context));
	 			  chk.recuperaDaForm(context, db);
	 			  chk.upsert(db);
	 		
	 			   
	 		   context.getRequest().setAttribute("ChecklistIstanza",chk);
	 		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
	 		
	    
	 	   }catch(SQLException sql){
	 		   sql.printStackTrace();
	 	   }finally{
	 		   this.freeConnection(context, db);
	 	   }
	 	  
	 	   return executeCommandSchedaBiosicurezza(context);
	 }
	    
	    public String executeCommandSchedaFarmacosorveglianza(ActionContext context){
		 	   
		       String orgId = context.getRequest().getParameter("orgId");
		 	   String ticketId = context.getRequest().getParameter("idControllo");
		 	   Connection db = null;
		 	    
		 	   try {
		 		   
		 		   db = this.getConnection(context);
		 	   
		 		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		 		   context.getRequest().setAttribute("Ticket",controllo);
		 		   
		 		   org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;
		 		   
		 		   int orgIdInt = -1;
		 		   
		 		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}

		 		   if (orgIdInt>0)
		 			   allevamento = this.getDatiAllevamentoFarmacosorveglianza(db,allevamento,orgIdInt);
		 		   
		 		   context.getRequest().setAttribute("Allevamento",allevamento);
		 		   
		 		  org.aspcf.modules.checklist_farmacosorveglianza.base.ChecklistIstanza chk = new org.aspcf.modules.checklist_farmacosorveglianza.base.ChecklistIstanza(db, Integer.parseInt(ticketId));
			 	  context.getRequest().setAttribute("Checklist",chk);

		 			   
		 	   }catch(SQLException sql){
		 		   sql.printStackTrace();
		 	   }finally{
		 		   this.freeConnection(context, db);
		 	   }
		 	  
		 	   return "ViewAllegato_Farmacosorveglianza_OK";
		 	   
		    }
	    
	    public String executeCommandInsertChecklistFarmacosorveglianza(ActionContext context){
		 	   
		 	   String orgId = context.getRequest().getParameter("orgId");
		 	   String ticketId = context.getRequest().getParameter("idControllo");
		 	   boolean bozza =  Boolean.parseBoolean(context.getRequest().getParameter("bozza"));
		 	   
		 	   Connection db = null;
		 	   
		 	   try {
		 		   
		 		   db = this.getConnection(context);
		 		   
		 		   org.aspcfs.modules.vigilanza.base.Ticket controllo = new org.aspcfs.modules.vigilanza.base.Ticket (db, Integer.parseInt(ticketId));
		 	       context.getRequest().setAttribute("Ticket",controllo);
		 		   
		 	       org.aspcf.modules.controlliufficiali.base.Organization allevamento = null;

		 		   int orgIdInt = -1;
		 		   int ticketIdInt = -1;

		 		   try {orgIdInt = Integer.parseInt(orgId);} catch (Exception e) {}
		 		   try {ticketIdInt = Integer.parseInt(ticketId);} catch (Exception e) {}

		 		   org.aspcf.modules.checklist_farmacosorveglianza.base.ChecklistIstanza chk = new org.aspcf.modules.checklist_farmacosorveglianza.base.ChecklistIstanza(db, Integer.parseInt(ticketId));
		 		   chk.setBozza(bozza);
		 		   chk.setOrgId(orgIdInt);
		 		   chk.setIdControllo(Integer.parseInt(ticketId));
		 		   chk.setEnteredBy(getUserId(context));
		 		   chk.recuperaDaForm(context, db);
		 		   chk.upsert(db);
		 				 			   
		 		   context.getRequest().setAttribute("ChecklistIstanza",chk);
		 		   context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		 		
		    
		 	   }catch(SQLException sql){
		 		   sql.printStackTrace();
		 	   }finally{
		 		   this.freeConnection(context, db);
		 	   }
		 	  
		 	   return executeCommandSchedaFarmacosorveglianza(context);
		 }
	    
}
