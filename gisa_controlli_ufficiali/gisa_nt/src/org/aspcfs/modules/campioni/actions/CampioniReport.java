/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.itextpdf.text.pdf.Barcode128;

import org.aspcf.modules.controlliufficiali.action.AccountVigilanza;
import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.modules.campioni.base.CampioniHtmlFields;
import org.aspcfs.modules.campioni.base.ModuliCampioniHtmlFields;
import org.aspcfs.modules.campioni.base.ModuloCampione;
import org.aspcfs.modules.campioni.base.Pnaa;
import org.aspcfs.modules.campioni.base.Sin;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.modules.global_search.base.OrganizationListView;
import org.aspcfs.modules.vigilanza.base.NucleoIspettivo;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import java.util.logging.Logger;

import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

public class CampioniReport extends CFSModule{
	
	Logger logger = Logger.getLogger("MainLogger");
	
	
	public String executeCommandViewSchedaSIN(ActionContext context) {
		
		Connection db = null;
  		Logger logger = Logger.getLogger("MainLogger");
  		String tipoSin = context.getRequest().getParameter("tipo");
  		try {
  			db = this.getConnection(context);
  			
  			String orgId = context.getRequest().getParameter("orgId");
  			String id_controllo = context.getParameter("idControllo");
  			String url = context.getRequest().getParameter("url");
  			  			
  			Filtro f = new Filtro();
  			Sin sin = new Sin();	
  			
  			f.setIdControllo(Integer.parseInt(id_controllo));
  			f.setOrgId(Integer.parseInt(orgId));
  			
  			//Controllo se il documento e' gia' stato salvato sul db
  			StringBuffer sqlSin = new StringBuffer();
  			PreparedStatement pstSin = null;
  			ResultSet rsSin = null;
  			// determino l'insieme delle colonne
			sqlSin.append("SELECT * FROM scheda_campioni_sin where a12 in (select identificativo from ticket where ticketid = ? and trashed_date is null)");
			pstSin = db.prepareStatement(sqlSin.toString());
			pstSin.setInt(1, Integer.parseInt(id_controllo));
			rsSin = pstSin.executeQuery();
			String def="false";
			if (rsSin.next())//scheda sin esistente
				def="true";
			context.getRequest().setAttribute("definitivo", def);
					
  			ResultSet rs = f.queryRecord_operatori_sin(db); 
  			// campione
  			org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();
  			campione.queryRecord(db, Integer.parseInt(id_controllo));
  			
  			
	        String redirect = "";			
	  		logger.info("GESTIONE CU CENTRALIZZATA PER OPERATORE:" + url);
	  			
	  		String ind 		= "";
	  		String comune 	= "";
	  		String prelevatore = "";
	  		String nucleo_prelevatore = "" ;
	  		String idComponentiNucleo = "";
	  		String codice_fiscale = "";
	  				
	  		// allevamento
	  		/*org.aspcfs.modules.allevamenti.base.Organization allevamento = 
	  				new org.aspcfs.modules.allevamenti.base.Organization(db, Integer.parseInt(orgId));
	  			// controllo ufficiale
	  		* */
	  			
	  		org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
	  		cu.queryRecord(db, Integer.parseInt(campione.getIdControlloUfficiale()));
	  			
	  			// determino il tipo di allevamento UOVA o LATTE
	  		int tipo = 1;
	  		if(tipoSin.equalsIgnoreCase("latte")){
	  				tipo = CampioniUtil.TIPO_LATTE;
	  		} else if (tipoSin.equalsIgnoreCase("uova")){
	  				tipo = CampioniUtil.TIPO_UOVA;
	  		}
	  			
	  		ArrayList<NucleoIspettivo> listaNucleo = cu.getNucleoasList();
	  		if((listaNucleo!=null)) {
	  			for (NucleoIspettivo nucleo : listaNucleo)
	  			{
	  				prelevatore += nucleo.getComponente()+ "," ;
	  				nucleo_prelevatore+= nucleo.getNucleo() + ","; 
	  				idComponentiNucleo += nucleo.getUserId() + ",";
	  	
	  			}
	  		}
	  			
	  		ArrayList<String> cfs = cu.getCFList(context,idComponentiNucleo,listaNucleo.size(),db);
	  			if((cfs.size() > 0)) {
	  				for (String cf : cfs)
	  					codice_fiscale += cf;
	  				
	  		    }
	  		
	  		context.getRequest().setAttribute("max",cfs.size());
	  		LookupList aslList = new LookupList(db, "lookup_site_id");
	  		
			String operatore = "";
			String sel_punti_wm = " select * from view_operatori_sin where punti_sb_wm != 'N.D' and tipologia in (3,5) and asl_rif = ?";
			PreparedStatement pst_puti_wm = db.prepareStatement(sel_punti_wm);
			pst_puti_wm.setInt(1, campione.getSiteId());
			ResultSet rs_punti_wm = pst_puti_wm.executeQuery();
		    while (rs_punti_wm.next()) {
		    	operatore += rs_punti_wm.getString("punti_sb_wm")+ "OPERATORE" ;
		    }
		    
        	context.getRequest().setAttribute("op_punti_sb_wm", operatore);
	  		
	  		while (rs.next()){
	  			ind = rs.getString("indirizzo");
	  			comune = rs.getString("comune");
	  			if(comune.equals("") && ind.equals("")){
	  				//Aggiorna eventualmente indirizzo inserito a mano.
	  				String comUtente = (String) context.getRequest().getParameter("a7");
	  				String indUtente = (String) context.getRequest().getParameter("a6");
	  				if(comUtente != null && indUtente != null){
	  						
		  				if(!comUtente.equals("null") && !indUtente.equals("null")){
		  					String update = "update organization_address set city = ?, addrline1 = ? where org_id = ? ";
		  	  				PreparedStatement ps = db.prepareStatement(update);
		  	  				ps.setString(1, comUtente);
		  	  				ps.setString(2, indUtente);
		  	  				ps.setInt(3, Integer.parseInt(orgId));
		  	  				ps.execute();
		  				}
		  				comune = comUtente;
		  				ind = indUtente;
	  				}
	  					
	  			}
	  			
	  			sin.setA9(rs.getString("ragione_sociale"));// Ragione sociale
	  			sin.setA5(rs.getString("num_reg"));// Codice Azienda
	  			if(!"".equals(operatore))
	  			{
	  				sin.setA11_1("");// Latitudine
			  		sin.setA11_2("");// Longitudine
	  			}
	  			else
	  			{
	  			sin.setA11_1(rs.getString("latitudine"));// Latitudine
		  		sin.setA11_2(rs.getString("longitudine"));// Longitudine
	  			}
	  		}
	  		
	  		//Costruzione della lista degli operatori Punti di sbarco e Mercati Ittici
	  		
		    
	  		
	  		/*Iterator iaddress = allevamento.getAddressList().iterator();
	  		if (iaddress.hasNext()) {
	  			OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
	  			ind = pnaa.getStreetAddressLine1();
	  			comune = pnaa.getCity();
	  			if(comune.equals("") && ind.equals("")){
	  				//Aggiorna eventualmente indirizzo inserito a mano.
	  				String comUtente = (String) context.getRequest().getParameter("a7");
	  				String indUtente = (String) context.getRequest().getParameter("a6");
	  				if(comUtente != null && indUtente != null){
	  						
		  				if(!comUtente.equals("null") && !indUtente.equals("null")){
		  					String update = "update organization_address set city = ?, addrline1 = ? where org_id = ? ";
		  	  				PreparedStatement ps = db.prepareStatement(update);
		  	  				ps.setString(1, comUtente);
		  	  				ps.setString(2, indUtente);
		  	  				ps.setInt(3, Integer.parseInt(orgId));
		  	  				ps.execute();
		  				}
		  				comune = comUtente;
		  				ind = indUtente;
	  				}
	  					
	  			}
	  				/*if(pnaa.getLatitude() != 0.0)
	  					lat = String.valueOf(pnaa.getLatitude());
	  				if(pnaa.getA11_2() != 0.0)
	  					lon = String.valueOf(pnaa.getA11_2());
	  				*/
	  		//}
	  			
	  			
	  		sin.setA2(prelevatore);// Prelevatore
	  		sin.setA2_1(codice_fiscale);
	  		sin.setA2_2(operatore);
	  		sin.setA3(aslList.getValueFromId(campione.getSiteId())); // ASL di appartnenza
	  		sin.setA6(ind);// Indirizzo luogo prelievo
	  		sin.setA7(comune);// Comune luogo prelievo
	  		sin.setA8("Italia");// Nazione luogo prelievo 
	  		sin.setA10(DateUtils.dataToString(campione.getAssignedDate()));// Data prelievo
	  		sin.setA12(campione.getIdentificativo());// Codice campione
	  		//sin.setA13("1");// n. aliquote campione
	  		sin.setA14(CampioniUtil.checkEsistenzaSchedaSin(db, campione.getIdentificativo()));// codice prelievo (barcode)
	  		sin.setA15(campione.getLocation());	// n. verbale	
	  		sin.setData(DateUtils.dataToString(new java.util.Date())); //data odierna
			sin.setOrgId(orgId);
			sin.setIdControllo(id_controllo);
			sin.setTipo(tipoSin);
			sin.setUrl(url);
				
	  		// se la scheda non esiste nel db genero un nuovo numero 
	  		// progressivo e riempio i campi
	  		if( sin.getA14() == null || sin.getA14().equals("")){
			  		sin.setA14(CampioniUtil.generaNumeroSchedaSin(tipo, db));
	  		}
	  			
	  		String checkp = context.getRequest().getParameter("prelev");
	  		sin.setA2_check(checkp);
	  			
	  		String checkop = context.getRequest().getParameter("a2_2_check");
	  		sin.setA2_2_check(checkop);
	  		
	  		CampioniUtil.riempiSchedaHTML(campione.getIdentificativo(),sin, db);
	  		
	  		if ("".equals(operatore))
	  			sin.setA6(ind);// Indirizzo luogo prelievo
	  		
	  		
	  		redirect= cu.getURlDettaglio()+"Campioni.do?command=TicketDetails&id="
					+ id_controllo + "&orgId=" + orgId;
	  		context.getRequest().setAttribute("redirect", redirect);
	  		context.getRequest().setAttribute("SinDetails", sin);
				
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			context.getRequest().setAttribute("Error", e);
	  			return ("SystemError");
	  		} finally {
	  			this.freeConnection(context, db);
	  		}
	
	  		if(tipoSin.equals("latteA")) {
	  			
	  			return "ViewAOK";
	  		}
	  		else if(tipoSin.equals("latteC1")) {
	  			return "ViewC1OK";
	  		}
	  		else if(tipoSin.equals("latteC2")) {
	  			return "ViewC2OK";
	  		}
	  		else if(tipoSin.equals("latteC")) {
	  			return "ViewCOK";
	  		}
	  		else if(tipoSin.equals("pesci")){
	  			return "ViewDOK";
	  		} else {
	  			return "ViewBOK";
	  		}
			
			
	}
	
	
	
	
  	
  	
  
  	
  	public String executeCommandSalvaOperatoriSin(ActionContext context) {

		Connection db = null;
		PreparedStatement pst = null;
		String sql = null;	
		String size = context.getRequest().getParameter("size");
		
		try {
			
			db = this.getConnection(context);	
			for (int i=1; i<=Integer.parseInt(size); i++){
				
				String visibilita = context.getRequest().getParameter("sin_"+i);
				String org_id = context.getRequest().getParameter("org_id_"+i);
				if(visibilita.equals("si")){
					sql = "update organization set note1 = ? where org_id = ? ";
					pst = db.prepareStatement(sql);
					pst.setString(1, "SIN2013");
					pst.setInt(2, Integer.parseInt(org_id));
					pst.executeUpdate();
				}else {
					sql = "update organization set note1 = null where org_id = ? ";
					pst = db.prepareStatement(sql);
					pst.setInt(1, Integer.parseInt(org_id));
					pst.executeUpdate();
				}
				
			}
			
			context.getRequest().setAttribute("redirect", "Operazione di salvataggio e' avvenuta con successo!");
			
			
  		
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			this.freeConnection(context, db);
		}
		
		return this.executeCommandViewOperatoriSin(context);
  	}
  	
  	public String executeCommandViewOperatoriSin(ActionContext context) {
		
		if (!hasPermission(context, "operatorisin-view")) {
		      return ("PermissionError");
		}
		    
		Connection db = null;
  		Logger logger = Logger.getLogger("MainLogger");
  		
  		try {
  			
  			db = this.getConnection(context);
  			//Sfrutto la action di GlobalSearch
  			OrganizationListView organizationList = new OrganizationListView();
  			//Display list of accounts if user chooses not to list contacts

  			organizationList.buildListSIN(db);
  			
  			 context.getSession().setAttribute("OrgList", organizationList);
  	  } catch (SQLException e) {
  	      //Go through the SystemError process
  	      context.getRequest().setAttribute("Error", e);
  	      return ("SystemError");
  	    } finally {
  	      this.freeConnection(context, db);
  	    }
  	    
		return "ListSINOK";

  	}
  	
  	public String executeCommandViewSchedaPNAA(ActionContext context) {
		
		Connection db = null;
  		Logger logger = Logger.getLogger("MainLogger");
  		try {
  			
  			db = this.getConnection(context);	
  			String orgId = context.getRequest().getParameter("orgId");
  			String idCampione = context.getParameter("idCampione");
  			String url = context.getRequest().getParameter("url");
  			String idCU = context.getRequest().getParameter("idCU");
  		    String redirect = "";		
  			
  		    Filtro f = new Filtro();
  			f.setIdControllo(Integer.parseInt(idCampione));
  			f.setId_controllo_ufficiale(idCU);
  			f.setOrgId(Integer.parseInt(orgId));
  			 //Dati operatore PNAA	
  			Pnaa pnaa = new Pnaa();	
  		    pnaa.setOrgId(orgId);
			pnaa.setIdCampione(Integer.parseInt(idCampione));
			pnaa.setIdControlloUfficiale(Integer.parseInt(idCU));
	  		pnaa.setData(DateUtils.dataToString(new java.util.Date())); //data odierna
	  		pnaa.setUrl(url);
	  		pnaa.queryRecord(db, Integer.parseInt(idCampione));
	  		//Barcodes
	  		ModCampioni barcodes = new ModCampioni();
			barcodes.setTipoModulo("19");//PNAA 19	 
			barcodes.getBarcodes(db,19, orgId, Integer.parseInt(idCampione));
			barcodes.setBarcodeMotivazione(pnaa.getBarcode_motivazione());
			barcodes.setBarcodeOSA(pnaa.getBarcode_numero_registrazione());
			barcodes.setMotivazione(pnaa.getMotivazione());
			barcodes.setUo(pnaa.getPerContoDi());
			
			String comUtente = (String) context.getRequest().getParameter("a9");
  			String indUtente = (String) context.getRequest().getParameter("a8");
  			pnaa.queryRecordIndirizzo(db,comUtente,indUtente);	
  			
  			if (!pnaa.getA11_1().equals("0") && !pnaa.getA11_2().equals("0")) {

				String spatial_coords[] = null;
				spatial_coords = this.converToWgs84UTM33NInverter(pnaa.getA11_1(),pnaa.getA11_2(), db);
				
				if (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(
								',', '.')) > 41.503754) {
					AjaxCalls ajaxCall = new AjaxCalls();
					String[] coordinate = ajaxCall.getCoordinate(
							pnaa.getA8(),pnaa.getA9(), pnaa.getA10(),
							pnaa.getCap(), ""
									+ pnaa.getA11_1(), ""
									+ pnaa.getA11_2(), "");
					pnaa.setA11_1(coordinate[1]);
					pnaa.setA11_2(coordinate[0]);
				} else {
					pnaa.setA11_1(spatial_coords[0]);
					pnaa.setA11_2(spatial_coords[1]);
				}
			}
  		    context.getRequest().setAttribute("bozza", pnaa.getBozza());
			String numScheda = CampioniUtil.checkEsistenzaSchedaPnaa(db, pnaa.getA0());

	  		// se la scheda non esiste nel db genero un nuovo numero 
	  		// progressivo e riempio i campi
	  		if( numScheda == null || numScheda.equals("")){
			 		numScheda = CampioniUtil.generaNumeroSchedaPNAA(db, pnaa.getB1());
	  		}
	  			
	  		pnaa.setNumScheda(numScheda);
	  		CampioniUtil.riempiSchedaHTMLRsitrutturato(Integer.parseInt(idCampione),pnaa, db);
	  		
	  		LookupList specie = new LookupList(db, "lookup_specie_pnaa");
		      context.getRequest().setAttribute("SpecieCategoria", specie);
		      
		    LookupList prod = new LookupList(db, "lookup_prodotti_pnaa");
		    context.getRequest().setAttribute("StatoProdotti", prod);
	  		
	  		redirect= pnaa.getUrl()+"Campioni.do?command=TicketDetails&id="
					+ idCampione + "&orgId=" + orgId;
	  		context.getRequest().setAttribute("redirect", redirect);
  		    context.getRequest().setAttribute("OrgCampione", barcodes);
	  		context.getRequest().setAttribute("PnaaDetails", pnaa);
				
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
				  for (int i = 0; i < 200; i++) {
					  valoriScelti.add("");
					}
			  		context.getRequest().setAttribute("valoriScelti", valoriScelti);
			  		context.getRequest().setAttribute("definitivoDocumentale", "false");}
		  
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			context.getRequest().setAttribute("Error", e);
	  			return ("SystemError");
	  		} finally {
	  			this.freeConnection(context, db);
	  		}
	
	  		return "PnaaOK";
			
			
	}
  	

public String executeCommandViewSchedaPNAA2(ActionContext context) {
		
		Connection db = null;
  		Logger logger = Logger.getLogger("MainLogger");
  		try {
  			
  			db = this.getConnection(context);	
  			String orgId = context.getRequest().getParameter("orgId");
  			String idCampione = context.getParameter("idCampione");
  			if (idCampione == null || idCampione.equals("null"))
  				idCampione = context.getParameter("ticketId");
  			String url = context.getRequest().getParameter("url");
  			String idCU = context.getRequest().getParameter("idCU");
  		    String redirect = "";		
  		    
  		    Ticket cu = new Ticket(db, Integer.parseInt(idCU));
  		    
  		    if (cu.getAltId()>0)
  		    	orgId = String.valueOf(cu.getAltId());
  		    else if (cu.getIdApiario()>0)
  		    	orgId = String.valueOf(cu.getIdApiario());
  		     
  			 //Dati operatore PNAA	
  			Pnaa pnaa = new Pnaa();	
  		    pnaa.setOrgId(orgId);
			pnaa.setIdCampione(Integer.parseInt(idCampione));
			pnaa.setIdControlloUfficiale(Integer.parseInt(idCU));
	  		pnaa.setData(DateUtils.dataToString(new java.util.Date())); //data odierna
	  		pnaa.setUrl(url);
	  		pnaa.queryRecordRistrutturato(db, Integer.parseInt(idCampione));
	  		
	  		if ((pnaa.getA11_1()!=null && pnaa.getA11_2()!=null) && (!pnaa.getA11_1().equals("0") && !pnaa.getA11_2().equals("0"))) {

				String spatial_coords[] = null;
				spatial_coords = this.converToWgs84UTM33NInverter(pnaa.getA11_1(),pnaa.getA11_2(), db);
				
//				if (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(
//								',', '.')) > 41.503754) {
//					AjaxCalls ajaxCall = new AjaxCalls();
//					String[] coordinate = ajaxCall.getCoordinate(
//							pnaa.getA8(),pnaa.getA9(), pnaa.getA10(),
//							pnaa.getCap(), ""
//									+ pnaa.getA11_1(), ""
//									+ pnaa.getA11_2(), "");
//					pnaa.setA11_1(coordinate[1]);
//					pnaa.setA11_2(coordinate[0]);
//				} else {
					pnaa.setA11_1(spatial_coords[0]);
					pnaa.setA11_2(spatial_coords[1]);
				//}
			}
	  		
	  		
	  		
	  		//Barcodes
	  		ModCampioni barcodes = new ModCampioni();
			barcodes.setTipoModulo("19");//PNAA 19	 
			barcodes.getBarcodes(db,19, orgId, Integer.parseInt(idCampione));
			barcodes.setBarcodeMotivazione(pnaa.getBarcode_motivazione());
			barcodes.setBarcodeOSA(pnaa.getBarcode_numero_registrazione());
			barcodes.setMotivazione(pnaa.getMotivazione());
			barcodes.setUo(pnaa.getPerContoDi());
		
  		    context.getRequest().setAttribute("bozza", pnaa.getBozza());
			String numScheda = CampioniUtil.checkEsistenzaSchedaPnaa(db, pnaa.getA0());

	  		// se la scheda non esiste nel db genero un nuovo numero 
	  		// progressivo e riempio i campi
	  		
	  		pnaa.setNumScheda(numScheda);
	  		CampioniUtil.riempiSchedaHTMLRsitrutturato(Integer.parseInt(idCampione),pnaa, db);
	  		
	  		LookupList specie = new LookupList(db, "lookup_specie_pnaa");
		      context.getRequest().setAttribute("SpecieCategoria", specie);
		      
		    LookupList prod = new LookupList(db, "lookup_prodotti_pnaa");
		    context.getRequest().setAttribute("StatoProdotti", prod);
		    
			LookupList specieVegetali = new LookupList(db, "lookup_specie_vegetale_pnaa ");
		      context.getRequest().setAttribute("specieVegetali", specieVegetali);
	  		
	  		redirect= pnaa.getUrl()+"Campioni.do?command=TicketDetails&id="
					+ idCampione + "&orgId=" + orgId;
	  		context.getRequest().setAttribute("redirect", redirect);
  		    context.getRequest().setAttribute("OrgCampione", barcodes);
  		    
  		    AccountVigilanza.setComponentiNucleoPnaa(pnaa, Integer.parseInt(idCU), db);
	  		context.getRequest().setAttribute("PnaaDetails", pnaa);
	  		context.getRequest().setAttribute("Anno", pnaa.getAnno());
				
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
				  for (int i = 0; i < 200; i++) {
					  valoriScelti.add("");
					}
			  		context.getRequest().setAttribute("valoriScelti", valoriScelti);
			  		context.getRequest().setAttribute("definitivoDocumentale", "false");}
		  
			  if (idCU.equals("-1"))
				  return "PrenotaCampioniHTMLOK";
	  		
			  if (!controllaEsistenzaSchedaPNAA(db, Integer.parseInt(idCampione))){ 
				  context.getRequest().setAttribute("error", "<font size=\"5px\"><b>Errore! <br/><br/>Completare la scheda PNAA prima di aprire il verbale</b></font>");
		  		return ("documentaleError");
			  }
				  
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			context.getRequest().setAttribute("Error", e);
	  			return ("SystemError");
	  		} finally {
	  			this.freeConnection(context, db);
	  		}
	
  		 
  		 
	  		return "HTMLOK";
			
			
	}

public String executeCommandModificaSchedaPNAA2(ActionContext context) {
	
	Connection db = null;
		Logger logger = Logger.getLogger("MainLogger");
		try {
			
			db = this.getConnection(context);	
			String idCampione = context.getParameter("idCampione");
			if (idCampione == null || idCampione.equals("null"))
				idCampione = context.getParameter("ticketId");
			
		//Dati finestra modale
  		//ArrayList<CampioniHtmlFields> campi = new ArrayList<CampioniHtmlFields>();
  		LinkedHashMap<String, ArrayList<CampioniHtmlFields>> campi = new LinkedHashMap<String, ArrayList<CampioniHtmlFields>>();
  		String query="";
  		if (controllaEsistenzaSchedaPNAA(db, Integer.parseInt(idCampione))){
  		
  		 query = "select campi.*, pnaa.valore_campione from campioni_html_fields campi "+ 
  						" LEFT JOIN campioni_fields_value pnaa on pnaa.id_campioni_html_fields = campi.id " +
  						" LEFT JOIN ticket t on t.ticketid = pnaa.id_campione "+
  						" join lookup_piano_monitoraggio piano on piano.code=t.motivazione_piano_campione  "+
  						" where codice_interno_piano_monitoraggio = piano.codice_interno::text " +
  						" and pnaa.id_campione = ? and pnaa.enabled = true and campi.enabled_campo = true "+
  						" order by ordine_campo asc";
  		}
  		else {
  			 query = "select campi.*, '' as valore_campione from campioni_html_fields campi "+
  						" where enabled_campo and campi.codice_interno_piano_monitoraggio::integer in (select distinct p.codice_interno  from ticket join lookup_piano_monitoraggio p on p.code=ticket.motivazione_piano_campione where ticketid = ? ) "+
  						" order by ordine_campo asc ;";
		}
  		
  		PreparedStatement pst = db.prepareStatement(query);
  		pst.setInt(1, Integer.parseInt(idCampione));
  		ResultSet rs = pst.executeQuery();
  		while (rs.next())
  		{
  			CampioniHtmlFields campo = new CampioniHtmlFields();
  			campo.buildRecord(db, rs);
  			
  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
  				ArrayList<CampioniHtmlFields> campoArray = new ArrayList<CampioniHtmlFields>(); //Crea array vuoto
  				campoArray.add(campo); //Aggiungi campo
  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
  			}
  			else
  				campi.get(campo.getNome_campo()).add(campo);
  		}
  		pst.close();
  		
  		CampioniHtmlFields c = new CampioniHtmlFields();
  		LinkedHashMap<String, String> ris = c.costruisciHtmlDaHashMap(db, campi);
  		context.getRequest().setAttribute("campiHash", ris);
  	
		}catch (Exception e) {
  			e.printStackTrace();
  			context.getRequest().setAttribute("Error", e);
  			return ("SystemError");
  		} finally {
  			this.freeConnection(context, db);
  		}

		if (context.getRequest().getParameter("messaggioOk")!=null && !context.getRequest().getParameter("messaggioOk").equals("null"))
			context.getRequest().setAttribute("messaggioOk", context.getRequest().getParameter("messaggioOk"));
  		return "ModificaPNAAOk";
}

public String executeCommandUpdatePNAA2(ActionContext context) {

	int idCampione = Integer.parseInt(context.getRequest().getParameter("idCampione"));
	String idCU = context.getRequest().getParameter("idCU");
	String orgId = 	context.getRequest().getParameter("orgId");
	String url = context.getRequest().getParameter("url");
	String tipo = context.getRequest().getParameter("tipo");
	Connection db = null;
	try {
		db = this.getConnection(context);	
		CampioniHtmlFields newPnaa = new CampioniHtmlFields();
		newPnaa.gestisciUpdate(context, db, idCampione);
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	context.getRequest().setAttribute("idCampione", String.valueOf(idCampione));
	context.getRequest().setAttribute("idCU", idCU);
	context.getRequest().setAttribute("orgId", orgId);
	context.getRequest().setAttribute("url", url);
	context.getRequest().setAttribute("tipo", tipo);
	context.getRequest().setAttribute("messaggioOk", "Modifica dati PNAA avvenuta con successo.");
	return executeCommandModificaSchedaPNAA2(context);
}

public String executeCommandModificaSchedaModulo(ActionContext context) {
	
	Connection db = null;
		Logger logger = Logger.getLogger("MainLogger");
		try {
			
			db = this.getConnection(context);	
			String idCampione = context.getParameter("idCampione");
			if (idCampione == null || idCampione.equals("null"))
				idCampione = context.getParameter("ticketId");
			String tipoModulo = context.getParameter("tipoModulo");
			
		//Dati finestra modale
  		//ArrayList<CampioniHtmlFields> campi = new ArrayList<CampioniHtmlFields>();
  		LinkedHashMap<String, ArrayList<ModuliCampioniHtmlFields>> campi = new LinkedHashMap<String, ArrayList<ModuliCampioniHtmlFields>>();
  		
//  		String query = "select campi.*, mod.valore_campione from moduli_campioni_html_fields campi "+ 
//  						" LEFT JOIN moduli_campioni_fields_value mod on mod.id_moduli_campioni_html_fields = campi.id " +
//  						" LEFT JOIN ticket t on t.ticketid = mod.id_campione "+
//  						" where tipo_analita = ? " +
//  						" and (mod.id_campione = ? or mod.id_campione is null) and (enabled = true or enabled is null) "+
//  						" order by ordine_campo asc";
  		
  		String query = "select campi.*, mod.valore_campione "+
" from moduli_campioni_html_fields campi "+  
"LEFT JOIN moduli_campioni_fields_value mod on mod.id_moduli_campioni_html_fields = campi.id and mod.id_campione = ? "+
"LEFT JOIN ticket t on t.ticketid = mod.id_campione "+ 
 " where tipo_modulo = ? and campi.enabled_campo "+  
" and (enabled = true or enabled is null) "+ 
 " order by ordine_campo asc ";

  		PreparedStatement pst = db.prepareStatement(query);
  		pst.setInt(2, Integer.parseInt(tipoModulo)); //1
  		pst.setInt(1, Integer.parseInt(idCampione)); //2
  		ResultSet rs = pst.executeQuery();
  		while (rs.next())
  		{
  			ModuliCampioniHtmlFields campo = new ModuliCampioniHtmlFields();
  			campo.buildRecord(db, rs);
  			
  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
  				ArrayList<ModuliCampioniHtmlFields> campoArray = new ArrayList<ModuliCampioniHtmlFields>(); //Crea array vuoto
  				campoArray.add(campo); //Aggiungi campo
  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
  			}
  			else
  				campi.get(campo.getNome_campo()).add(campo);
  		}
  		pst.close();
  		
  		ModuliCampioniHtmlFields c = new ModuliCampioniHtmlFields();
  		LinkedHashMap<String, String> ris = c.costruisciHtmlDaHashMap(db, campi);
  		context.getRequest().setAttribute("campiHash", ris);
  	
		}catch (Exception e) {
  			e.printStackTrace();
  			context.getRequest().setAttribute("Error", e);
  			return ("SystemError");
  		} finally {
  			this.freeConnection(context, db);
  		}

		if (context.getRequest().getParameter("messaggioOk")!=null && !context.getRequest().getParameter("messaggioOk").equals("null"))
			context.getRequest().setAttribute("messaggioOk", context.getRequest().getParameter("messaggioOk"));
  		return "ModificaModuloOk";
}

public String executeCommandUpdateSchedaModulo(ActionContext context) {

	int idCampione = Integer.parseInt(context.getRequest().getParameter("idCampione"));
	String orgId = 	context.getRequest().getParameter("orgId");
	int tipoModulo = Integer.parseInt(context.getRequest().getParameter("tipoModulo"));
	
	Connection db = null;
	try {
		db = this.getConnection(context);	
		ModuliCampioniHtmlFields newModulo = new ModuliCampioniHtmlFields();
		newModulo.gestisciUpdate(context, db, idCampione, tipoModulo);
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	context.getRequest().setAttribute("idCampione", String.valueOf(idCampione));
	context.getRequest().setAttribute("orgId", orgId);
	context.getRequest().setAttribute("tipo", tipoModulo);
	context.getRequest().setAttribute("messaggioOk", "Modifica dati modulo avvenuta con successo.");
	return executeCommandModificaSchedaModulo(context);
}

public static boolean controllaEsistenzaSchedaPNAA (Connection db, int idCampione){
    
    StringBuffer sqlSelect = new StringBuffer();
    PreparedStatement pst = null;
    boolean esito = false;
               
    sqlSelect.append("SELECT * from campioni_fields_value where id_campione = ? " +
                    "and enabled = true");
    try {
            pst = db.prepareStatement(sqlSelect.toString());
    
    pst.setInt(1, idCampione);
    ResultSet rs = pst.executeQuery();
    
  if (rs.next())
          esito = true;
  
    } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
   return esito;
}
	
}
