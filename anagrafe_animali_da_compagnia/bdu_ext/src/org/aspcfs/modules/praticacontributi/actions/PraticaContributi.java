/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.praticacontributi.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.aspcfs.controller.SystemStatus;


import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.praticacontributi.base.PraticaDWR;
import org.aspcfs.modules.praticacontributi.base.PraticaList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class PraticaContributi extends CFSModule{

	
	public String executeCommandDefault(ActionContext context) {
		String search = (String)context.getSession().getAttribute("startSearch"); 
		if ( search  != null )
		{
			if (Boolean.parseBoolean(search)) {
				cleanSession(context);
			}
		}
		return (executeCommandSearchForm(context));
	}

	public String executeCommandDashboard(ActionContext context) {
		Connection db = null;
		
		
		try{
			//connessione al db
			db = getConnection(context);
			 buildFormElements(context, db);
		}catch(Exception e){
		   // logger.severe("[CANINA] - EXCEPTION nella action executeCommandDashboard della classe PraticaContributi");
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally{
			this.freeConnection(context, db);
		}
		
		return ("PraticaSearchFormOK");
		
	}
	
	
	public String executeCommandElencoComuni(ActionContext context) 
	{
	 	if (!hasPermission(context, "pratica-contributi-view")) 
	 	{
		    	context.getRequest().setAttribute("dettagli_problema", "PermissionError");
		        return ("PermissionError");
	    }	
	 	
		Connection db = null;
			
		String idStr = context.getRequest().getParameter("id");
		if (idStr!=null)
		{
	    try 
	    {
	    	db = this.getConnection(context);
	    	Pratica thisAsset = new Pratica();
			thisAsset.setBuildCompleteParentList(true);
			
			thisAsset.queryRecord(db, Integer.parseInt(idStr));
			context.getRequest().setAttribute("pratica", thisAsset);
	    }
	    catch (Exception errorMessage) 
	    {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("dettagli_problema", errorMessage.toString());
			context.getRequest().setAttribute("Error", errorMessage);
		} 
	    finally 
	    {
			this.freeConnection(context, db);
		}
	}
		return ("PraticaElencoComuniOK");
	}


	private void cleanSession(ActionContext context) {
		
		context.getSession().removeAttribute("dataDecreto");
		context.getSession().removeAttribute("dataInizioSterilizzazione");
		context.getSession().removeAttribute("dataFineSterilizzazione");
		context.getSession().removeAttribute("searchcodenumeroDecreto");
		context.getSession().removeAttribute("aslRif");
		context.getSession().removeAttribute("aslPraticaRif");
		context.getSession().removeAttribute("comuneScelto");
		

	}
	
	//inserimento di una nuova pratica di contributi
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "pratica-contributi-add")) {
			return ("PermissionError");
		}
		
		Connection		db		= null;
		Pratica thisOrg = (Pratica) context.getFormBean();
			
		try {
			  //connessione al db
			  db = getConnection(context);
			  
			  if ((String) context.getRequest().getParameter("idTipologiaPratica") != null)
				  thisOrg.setIdTipologiaPratica((String) context.getRequest().getParameter("idTipologiaPratica"));
				
			  
			  buildFormElements(context, db);
			  context.getRequest().setAttribute("pratica", thisOrg);
			  String currentDate = getCurrentDateAsString(context);
			  context.getRequest().setAttribute("currentDate", currentDate);
			  
			  LookupList veterinariPrivati = new LookupList(db, "elenco_veterinari_lp_attivi");
		      veterinariPrivati.addItem(-1, "--Seleziona--");
		      veterinariPrivati.setMultiple(true);
		      veterinariPrivati.setSelectSize(10);
			  context.getRequest().setAttribute("veterinariPrivatiList", veterinariPrivati);
			  
		}
		catch (Exception e) {
			  //An error occurred, go to generic error message page
			  e.printStackTrace();
			  context.getRequest().setAttribute("Error", e);
			//  logger.severe("[CANINA] - Si è verificata una eccezione mell'inserimento di una pratica ");
			  return ("SystemError");
		}
		finally
		{
		      this.freeConnection(context, db);
			  System.gc();
		}
//		return getReturn(context, "AddOk");
		return "AddOk";
		
	}
	
	
	
	public void buildFormElements(ActionContext context, Connection db) throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);
		
		LookupList aslList = new LookupList(db, "lookup_asl_rif");
	    aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
	    context.getRequest().setAttribute("aslRifList", aslList);

	    
	    ArrayList<String> comuneList=new ArrayList<String>();
	    Hashtable<String, ArrayList<String>> hashtable = new Hashtable<String,ArrayList<String>>();
	    String[] x=new String[7];

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    x[0]="Napoli 1 Centro";
	    x[1]="Napoli 2 Nord";
	    x[2]="Napoli 3 Sud";
	    x[3]="Avellino";
	    x[4]="Caserta";
	    x[5]="Benevento";
	    x[6]="Salerno";
	
	    
	    if ( (String)context.getRequest().getParameter("idTipologiaPratica") != null && new Integer((String)context.getRequest().getParameter("idTipologiaPratica")) == 1){
	    String qry2="select nome from comuni1 join lookup_asl_rif on (codiceistatasl=codiceistat) where code=? and cod_regione = '15' order by nome ";
	    if(thisUser.getSiteId()==0){
	 	   for(int i=201;i<=207;i++){
	 		   		ArrayList<String> tmp=new ArrayList<String>();
	 	    		PreparedStatement pst2=db.prepareStatement(qry2);
	 	      		pst2.setInt(1,i);
	 	      		ResultSet rs2=pst2.executeQuery();
	 	      			tmp.add("Scegli");
	 	      		while(rs2.next()){
//	 	      				tmp.add("'"+rs2.getString("comune").replaceAll("'", "")+"'");
	 	      				tmp.add("\""+rs2.getString("nome")+"\"");
	 	      		}
	 	      		String tempo="[";
	 	      		for(String cc:tmp){
	 	      				tempo=tempo+cc+",";
	     		
	 	      		}
	 	      		tempo=tempo.substring(0, tempo.length()-1)+"]";
	 	      		hashtable.put(""+i,tmp);
	 	  }
	     }
	    else{
	 	   if(thisUser.getSiteId()>0){
	 		   
	 		  ArrayList<String> tmp2=new ArrayList<String>();
	 		   PreparedStatement pst2=db.prepareStatement(qry2);
	       		pst2.setInt(1,thisUser.getSiteId());
	       		ResultSet rs2=pst2.executeQuery();
	       		int i=0;
	       		tmp2.add("Scegli");
	       		while(rs2.next()){
	 				//tmp2.add("'"+rs2.getString("comune").replaceAll("'", "")+"'");
	       			tmp2.add("\""+rs2.getString("nome")+"\"");
	 				i++;
	 			}
	       		context.getRequest().setAttribute("lista", tmp2);
	       		
	     	   }
	 	
	    }
	    }
	     context.getRequest().setAttribute("hashtable", hashtable);
	}
	
	public void buildFormElements2(ActionContext context, Connection db) throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    ArrayList<PraticaDWR> elencoPratiche =null;
	    String query="Select id, " +
			 "       asl, " +
			 "       descrizione, " +
			 "       numero_totale_cani_catturati , " +
			 "  	 numero_totale_gatti_catturati , " +
			 "		 data_inizio_sterilizzazione," +
			 "		 data_fine_sterilizzazione,			  " +
			 "		 numero_totale_cani_padronali ," +
			 "       numero_restante_cani_catturati, "+
			 "       numero_restante_cani_padronali, "+
			 "		 numero_totale_gatti_padronali " +
			 "       FROM pratiche_contributi " +
			 "       where asl= ?";
		
	    PreparedStatement pst=db.prepareStatement(query);
  		pst.setInt(1,thisUser.getSiteId());
  		ResultSet rs=pst.executeQuery();
  		PraticaDWR pratica_tmp= null;
  		while(rs.next()){
  			pratica_tmp= new PraticaDWR();
			pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
			pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
			pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
			pratica_tmp.setId(rs.getInt("id"));
			int i=pratica_tmp.getId();
			pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
			pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
			pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
			pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
			elencoPratiche.add(pratica_tmp);
			
			String query2="Select comune from pratiche_contributi_comuni where id_pratica=?";
			PreparedStatement st2 = db.prepareStatement( query2 );
			st2.setInt(1, i);
			ResultSet rs2 = st2.executeQuery();
			ArrayList<String> comuni= new ArrayList<String>();
			while(rs2.next()){
				comuni.add(rs2.getString("comune"));
			}
			pratica_tmp.setElenco_comuni(comuni);
			
  		}
  		context.getRequest().setAttribute("elenco_pratiche", elencoPratiche);
	}
	
	
	//inserimento di una nuova pratica per il pagamento dei contributi di sterilizzazione
	public String executeCommandSave(ActionContext context) {
		//controllo sui permessi di inserimento
		if (!hasPermission(context, "pratica-contributi-add")) {
			context.getRequest().setAttribute("dettagli_problema", "PermissionError");
			return ("PermissionError");
		}
	
		Connection db = null;
		boolean inserted = false;
		String checkSaveClone = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		//recupero del bean della pratica
		//Pratica thisOrg = new Pratica();
		Pratica thisOrg = (Pratica) context.getFormBean();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		 
		try{
			//connessione al db
			db = this.getConnection(context);
			//recupero dell'asl			
			
			if(thisOrg.getIdTipologiaPratica() != Pratica.idPraticaLP)
				thisOrg.setIdAslPratica(Integer.parseInt(context.getRequest().getParameter("aslRif")));
			
		 	
			//recupero dell'elenco di comuni che sono coinvolti per la pratica 
			
			//Con l'aggiunta dell'inserimento pratiche per canili da maschera questo array conterrà eventualmente la lista dei canili
			 String[] comuni =context.getRequest().getParameterValues("comune");
			 String[] vet =context.getRequest().getParameterValues("veterinari");
			 
			 
			 if(thisOrg.getIdTipologiaPratica() == Pratica.idPraticaLP)
				 thisOrg.setVeterinari(vet);
			 
			 if (thisOrg.getIdTipologiaPratica() == Pratica.idPraticaComune || thisOrg.getIdTipologiaPratica() == Pratica.idPraticaLP){
				 thisOrg.setComuni(comuni);
			 }			 
			 else{
				 thisOrg.setCanili(comuni);
			 }
			 
			 if (thisOrg.getIdTipologiaPratica() != Pratica.idPraticaLP)
			 {
				 thisOrg.setCaniRestantiCatturati(Integer.parseInt(context.getParameter("totaleCaniCatturati")));
				 thisOrg.setCaniRestantiPadronali(Integer.parseInt(context.getParameter("totaleCaniPadronali")));
				 thisOrg.setGattiRestantiCatturati(Integer.parseInt(context.getParameter("totaleGattiCatturati")));
				 thisOrg.setGattiRestantiPadronali(Integer.parseInt(context.getParameter("totaleGattiPadronali")));
			 }
			 else
			 {
				 thisOrg.setCaniRestantiMaschi(Integer.parseInt(context.getParameter("totaleCaniMaschi")));
				 thisOrg.setCaniRestantiFemmina(Integer.parseInt(context.getParameter("totaleCaniFemmina")));
			 }
			 thisOrg.setEnteredBy(this.getUserId(context));
			 thisOrg.setModifiedBy(this.getUserId(context));
			 
			 thisOrg.setDataDecreto((String)context.getParameter("dataDecreto"));
			 thisOrg.setDataInizioSterilizzazione((String)context.getParameter("dataInizioSterilizzazione"));
			 thisOrg.setDataFineSterilizzazione((String)context.getParameter("dataFineSterilizzazione"));
				
			
			 inserted =thisOrg.insert(db);
			 
			// thisOrg = new Pratica(db, "" + thisOrg.getId());
			 thisOrg = new Pratica(db, thisOrg.getId());
			 context.getRequest().setAttribute("pratica", thisOrg);
		    
			//thisOrg.setDescrizione_asl_pratica(context.getParameter("aslRif"));
		}catch(Exception errorMessage){
		//	logger.severe("[CANINA] - EXCEPTION nella action executeCommandSave della classe PraticaContributi");
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
		finally{
			this.freeConnection(context, db);	
		}
		if (inserted ){
	//		context.getRequest().setAttribute("id", thisOrg.getId());
		}
		return  "InsertOK";
		//executeCommandDetails(context);
	}

	public String executeCommandDetails(ActionContext context) {

 	if (!hasPermission(context, "pratica-contributi-view")) {
	    	context.getRequest().setAttribute("dettagli_problema", "PermissionError");
	        return ("PermissionError");
	    }		
		Connection db = null;

			
			String idStr = context.getRequest().getParameter("id");
			if (idStr!=null){
			/*Object obj = context.getRequest().getAttribute("id");
			if (obj != null) {
				idStr = obj.toString();
			}*/
			//BeanSaver newSavedBean = null;
		    try {
	
		    	db = this.getConnection(context);
		    	Pratica thisAsset = new Pratica();
				thisAsset.setBuildCompleteParentList(true);
			
				
				thisAsset.queryRecord(db, Integer.parseInt(idStr));
				context.getRequest().setAttribute("pratica", thisAsset);
				
				
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni =  c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				
			//	getReturn(context, "Details");
		    }catch (Exception errorMessage) {
				//An error occurred, go to generic error message page
				errorMessage.printStackTrace();
				context.getRequest().setAttribute("dettagli_problema", errorMessage.toString());
				context.getRequest().setAttribute("Error", errorMessage);
			} finally {
				//Always free the database connection
				this.freeConnection(context, db);
			}
		}
		return getReturn(context, "Details");
//			return "DetailsOK";
	}	
	
	//Genera il form per la ricerca di una pratica dei contributi
	public String executeCommandSearchForm(ActionContext context) {
		Connection db = null;
		
		try{
			//connessione al db
			db = getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			if (thisUser.getRoleId()==22){ //AMMINISTRAOTRE REGIONE
			 	  context.getRequest().setAttribute("AmministratoreRegione", thisUser);
		    }
			
			 String search = (String)context.getSession().getAttribute("startSearch"); 
				if ( search  != null )
				{
					if (Boolean.parseBoolean(search)) {
						cleanSession(context);
					}
				}
			 buildFormElements(context, db);
			
		}
		catch(Exception e){
		//	logger.severe("[CANINA] - EXCEPTION nella action executeCommandSearchForm della classe PraticaContributi");
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		
		}
		finally{
			this.freeConnection(context, db);
		}
		
		if(context.getParameter("lp")!=null)
			return ("PraticaSearchFormLPOK");
		else
			return ("PraticaSearchFormOK");
		
	}
	
	
	//Genera la lista degli impianti trovati tramite la funzione di ricerca
	public String executeCommandSearchList(ActionContext context) {
		
		  if (!hasPermission(context, "pratica-contributi-view")) {
	        return ("PermissionError");
	      }
		  
		  String parentId = context.getRequest().getParameter("parentId");
		  Pratica  thisPratica= (Pratica) context.getFormBean();
	      PraticaList praticaList = new PraticaList();
	  			  
		  UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	      
	      
	      
	      //Prepare pagedListInfo
	      PagedListInfo praticaListInfo = this.getPagedListInfo(context, "PraticaListInfo");
	      praticaListInfo.setLink("PraticaContributi.do?command=SearchList&parentId="+(parentId != null?parentId:"") + "&onlyLP=" + (context.getParameter("onlyLP") != null?context.getParameter("onlyLP"):"")  + "&tipoProgetto=" + (context.getParameter("tipoProgetto") != null?context.getParameter("tipoProgetto"):""));
	      Connection db = null;
	  
	      try {
		        db = this.getConnection(context);
		    
		        praticaList.setPagedListInfo(praticaListInfo);
		        
		        
		        if (parentId != null && !"".equals(parentId.trim()) && !"-1".equals(parentId.trim())) {
		        	praticaList.setParentId(parentId);
		        	Pratica parent = new Pratica();
			          parent.setBuildCompleteParentList(true);
			          parent.setIncludeMe(true);
			          parent.queryRecord(db, Integer.parseInt(parentId));
			          context.getRequest().setAttribute("parent", parent);
			        }
		
		    setPraticaCriteria(context, thisPratica, praticaList, db); 	
			   
		    
		    
		  //Filtro aggiunto 
	        String asl = context.getParameter("aslRif");
	        
	        String aslReg = context.getParameter("aslRifFReg");
        	if ( !("0").equals(aslReg) && aslReg!=null && !"".equals(aslReg)){
        		asl = aslReg;
        	}
        	
	        if (asl!=null && !"".equals(asl)) {
	        	int aslRif = Integer.parseInt(asl);
	        	if (aslRif > 0) {
	        		praticaList.setAslRif(aslRif);
	        	}
	        	context.getSession().setAttribute("aslPraticaRif", asl);
	        } 
	        String aslR = (String) context.getSession().getAttribute("aslPraticaRif");
	        if ( (aslR!=null && !"".equals(aslR)) && (asl==null || "".equals(asl)) ) {
	        	int aslRif = Integer.parseInt(aslR);
	        	if (aslRif > 0) {
	        		praticaList.setAslRif(aslRif);
	        	}
	        }
	        
	        //Flusso 251: modifiche del 03/08 - INIZIO
	        if(context.getParameter("tipoProgetto")!=null && !context.getParameter("tipoProgetto").equals("") && !context.getParameter("tipoProgetto").equals("null"))
	        {
	        	int tipoProgetto = Integer.parseInt(context.getParameter("tipoProgetto"));
	        	praticaList.setIdTipologiaPratica(tipoProgetto);
	        }
	        
	      //Flusso 251: modifiche del 03/08 - FINE
	        
	        
	        //Flusso 251: rollback
	        if(false)
	        //if(context.getParameter("onlyLP")!=null && !context.getParameter("onlyLP").equals("") && !context.getParameter("onlyLP").equals("null"))
	        {
	        	praticaList.setAslRif(-1);
	        	praticaList.setIdTipologiaPratica(Pratica.idPraticaLP);
	        }
	        	
	        //set the searchcriteria
	    	praticaListInfo.setSearchCriteria(praticaList, context);
	    	praticaList.buildList(db);
			buildFormElements(context, db);
		    context.getRequest().setAttribute("praticaList", praticaList);
	    	/*String decr= context.getParameter("numeroDecretoPratica");
	       context.getSession().setAttribute("numeroDecretoPratica", decr);
   
	       String dataDecr= context.getParameter("dataDecreto");
	       context.getSession().setAttribute("dataDecreto", dataDecr);

	       String dataInizio= context.getParameter("dataInizioSterilizzazione");
	       context.getSession().setAttribute("dataInizioSterilizzazione", dataInizio);
	       
	       String dataFine= context.getParameter("dataFineSterilizzazione");
	       context.getSession().setAttribute("dataFineSterilizzazione", dataFine);
	       
	       String comune= context.getParameter("comuneScelto");
	       context.getSession().setAttribute("comuneScelto", comune);
	       */
	 
	        
	    
		   
	      }
	      	catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      
	      
	      //Flusso 251: modifiche del 03/08 - INIZIO
	      
	      if(context.getParameter("tipoProgetto")!=null && !context.getParameter("tipoProgetto").equals("") && !context.getParameter("tipoProgetto").equals("null"))
	      {
	    	  
	    	  int tipoProgetto = Integer.parseInt(context.getParameter("tipoProgetto"));
	    	  if(tipoProgetto==Pratica.idPraticaComune)
	    		  return getReturn(context, "PraticaSearchListLP");
	    	  else
	    		  return getReturn(context, "PraticaSearchList");
	    	  
	      }
	      else
	    	  return getReturn(context, "PraticaSearchList");
	      //Flusso 251: modifiche del 03/08 - FINE
	      
	      
	      
	      
	      
	    	  
	      
	}
	
	private void setPraticaCriteria(ActionContext context, Pratica praticaForm,
			PraticaList praticaList, Connection db) throws SQLException {
		context.getSession().setAttribute("startSearch", "true");
		
		//recupera i dati dal form 
		if (praticaForm.getNumeroDecretoPratica()>0   )
		{
			praticaList.setNumeroDecretoPratica(praticaForm.getNumeroDecretoPratica());
			context.getSession().setAttribute("numeroDecretoPratica", praticaForm.getNumeroDecretoPratica());
		}
		else {
			if ((String) context.getSession().getAttribute("numeroDecretoPratica") != null){
				praticaList.setNumeroDecretoPratica((String) context.getSession().getAttribute("numeroDecretoPratica"));
			}
		}
		
		if (praticaForm.getDataDecreto()!= null   )
		{
			praticaList.setdataDecretoPratica(praticaForm.getDataDecreto());
			context.getSession().setAttribute("dataDecreto", praticaForm.getDataDecretoFormattata());
		}
		else {
			if ((String) context.getSession().getAttribute("dataDecreto") != null  && (!"".equals(context.getSession().getAttribute("dataDecreto")))){
				praticaList.setdataDecretoPratica((String) context.getSession().getAttribute("dataDecreto"));
			}
		}
		if (praticaForm.getDataInizioSterilizzazione()!= null   ){
			praticaList.setDataInizio(praticaForm.getDataInizioSterilizzazione());
			context.getSession().setAttribute("dataInizioSterilizzazione", praticaForm.getDataInizioSterilizzazioneStringa());
			
		}
		else{
			if ((String) context.getSession().getAttribute("dataInizioSterilizzazione") != null && (!"".equals(context.getSession().getAttribute("dataInizioSterilizzazione"))) ){
					praticaList.setDataInizio((String) context.getSession().getAttribute("dataInizioSterilizzazione"));
			}
		
		}
		if (praticaForm.getDataFineSterilizzazione()!= null   ){
			praticaList.setDataFine(praticaForm.getDataFineSterilizzazione());
			context.getSession().setAttribute("dataFineSterilizzazione", praticaForm.getDataFineSterilizzazioneStringa());
		
		}
		else {
			if ((String) context.getSession().getAttribute("dataFineSterilizzazione") != null && (!"".equals(context.getSession().getAttribute("dataFineSterilizzazione")))){
				praticaList.setDataFine((String) context.getSession().getAttribute("dataFineSterilizzazione"));
			}
		}
		
		if (praticaForm.getComuneScelto()!=null ){
			praticaList.setComune(praticaForm.getComuneScelto());
			context.getSession().setAttribute("comuneScelto",praticaForm.getComuneScelto() );
		}else{
			if ((String) context.getSession().getAttribute("comuneScelto") != null && (!"".equals(context.getSession().getAttribute("dataFineSterilizzazione")))){
				praticaList.setComune((String) context.getSession().getAttribute("comuneScelto"));
			}
		}
		
		if (praticaForm.getStatoP()!=-1 ){
			//
			praticaList.setStatoP(praticaForm.getStatoP());
			context.getSession().setAttribute("",praticaForm.getData_chiusura_pratica() );
		}
		
	}
	
	public String executeCommandList(ActionContext context) {
		if (!hasPermission(context, "pratica-contributi-view")) {
	        return ("PermissionError");
	     }
	     
	    String parentId = context.getRequest().getParameter("parentId");
	    PraticaList praticaList = new PraticaList();
	
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    
	  //Prepare pagedListInfo
	      PagedListInfo praticaListInfo = this.getPagedListInfo(context, "PraticaListInfo");
	      praticaListInfo.setLink("PraticaContributi.do?command=List&parentId="+(parentId != null?parentId:""));
	      Connection db = null;
	      
	      try {
		        db = this.getConnection(context);
		        
		        praticaList.setPagedListInfo(praticaListInfo);
		        if (parentId != null && !"".equals(parentId.trim()) && !"-1".equals(parentId.trim())) {
		        	praticaList.setParentId(parentId);
		          Pratica parent = new Pratica();
		          parent.setBuildCompleteParentList(true);
		          parent.setIncludeMe(true);
		          parent.queryRecord(db, Integer.parseInt(parentId));
		          context.getRequest().setAttribute("parent", parent);
		        }
		        
		        //Setto i filtri di ricerca per L'Anagrafe Canina
		        setCriteriaPratica(context, praticaList);
		        
			    //set the searchcriteria
		        praticaListInfo.setSearchCriteria(praticaList, context);
		        
		        praticaList.buildList(db);
		        buildFormElements(context, db);
		        context.getRequest().setAttribute("praticaList", praticaList);

	      }catch(Exception e){
	    //	  logger.severe("[CANINA] - EXCEPTION nella action executeCommandList della classe PraticaContributi");
	    	  context.getRequest().setAttribute("Error", e);
	    	  return ("SystemError");
	      } 
	      	finally {
	        this.freeConnection(context, db);
	      }
	      return getReturn(context, "PraticaList");
	}
	
private void setCriteriaPratica(ActionContext context, PraticaList praticaList) {
	//Filtro aggiunto per la selezione Propretari/Detentore
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date parsedDate;
	
	String asl = (String) context.getSession().getAttribute("aslPraticaRif");
	if (asl!=null && !"".equals(asl)) {
		int aslRif = Integer.parseInt(asl);
		if (aslRif > 0) {
			praticaList.setAslRif(aslRif);
		}
	}

	
	
	
	String numeroDecreto =  (String) context.getSession().getAttribute("numeroDecretoPratica");
	
	if (numeroDecreto!=null && !"".equals(numeroDecreto)){
		int numeroDecretoPratica= Integer.parseInt(numeroDecreto);
		if (numeroDecretoPratica > 0) {
			praticaList.setNumeroDecretoPratica(numeroDecretoPratica);
		}
	}

	String dataDecreto =  (String) context.getSession().getAttribute("dataDecreto");
	if (dataDecreto!=null && !"".equals(dataDecreto)){
		
		try {
			//parsedDate = dateFormat.parse(dataDecreto);
			//java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			//praticaList.setdataDecretoPratica(timestamp);
			praticaList.setdataDecretoPratica(dataDecreto);

		//} catch (ParseException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	logger.severe("[CANINA] - ParseException su data decreto");
		}

				
	}

	String dataInizio =  (String) context.getSession().getAttribute("dataInizioSterilizzazione");
	if (dataInizio!=null && !"".equals(dataInizio)){
		
		try {
//			parsedDate = dateFormat.parse(dataInizio);
//			java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
//			praticaList.setDataInizio(timestamp);
			praticaList.setDataInizio(dataInizio);
		//} catch (ParseException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	logger.severe("[CANINA] - ParseException su data inizio sterilizzazione");
		}

				
	}
	
	String dataFine =  (String) context.getSession().getAttribute("dataFineSterilizzazione");
	if (dataFine!=null && !"".equals(dataFine)){
		
		try {
	//		parsedDate = dateFormat.parse(dataFine);
	//		java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	//		praticaList.setDataFine(timestamp);
			praticaList.setDataFine(dataFine);
		//} catch (ParseException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	logger.severe("[CANINA] - ParseException su data fine sterilizzazione");
		}

				
	}
	String comune =  (String) context.getSession().getAttribute("comuneScelto");
	if (comune!=null && !("").equals("comune")){
		praticaList.setComune(comune);
	}
	
}

	//action per la chiusura di un progetto di sterilizzazione
	public String executeCommandChiudi(ActionContext context) {
	boolean updateOk =false;
	 Connection db = null;
	 try{
	Pratica  thisPratica= (Pratica) context.getFormBean();
    
	db = this.getConnection(context);
		if (context.getRequest().getParameter("id")!=null && (!context.getRequest().getParameter("id").equals("0"))){
			int id=Integer.parseInt(context.getRequest().getParameter("id"));
			updateOk=thisPratica.settaChiusura(db,id);
		
		}
		
	 }
	 catch(Exception e){
		 e.printStackTrace();
		 context.getRequest().setAttribute("Error", e);
         return ("SystemError");
	 }finally{
		 this.freeConnection(context, db);
	 }
	 if (updateOk==true){
		 return (executeCommandDetails(context));
	 }
	 else
			return (executeCommandList(context));
		
	}

	//action per la chiusura di un progetto di sterilizzazione
	public String executeCommandProroga(ActionContext context) {
	boolean updateOk =false;
	 Connection db = null;
	 try{
		 
		 
		 Pratica  thisPratica= (Pratica) context.getFormBean();
		 db = this.getConnection(context);
		 String dataProroga= context.getRequest().getParameter("dataProroga");
		Timestamp date_new= DatabaseUtils.parseDateToTimestamp(dataProroga);
		 if (context.getRequest().getParameter("id")!=null && (!context.getRequest().getParameter("id").equals("0"))){
			int id=Integer.parseInt(context.getRequest().getParameter("id"));
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			  
			 if ((dataProroga!=null) && !(dataProroga.equals(""))){
				 updateOk=thisPratica.settaProroga(db,thisUser.getUserId(),id,date_new);
			 }
		
		}
		
	 }
	 catch(Exception e){
		 e.printStackTrace();
		 context.getRequest().setAttribute("Error", e);
	     return ("SystemError");
	 }finally{
		 this.freeConnection(context, db);
	 }
	 if (updateOk==true){
		 return (executeCommandDetails(context));
	 }
	 else
			return (executeCommandList(context));
		
	}
	    

}
	

