/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.microchip.actions;

import java.sql.Connection;
import java.util.Date;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.log.base.LogBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.microchip.base.MicrochipImportValidate;
import org.aspcfs.modules.microchip.base.MicrochipList;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;



import com.darkhorseventures.framework.actions.ActionContext;


public final class Microchip extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
     return executeCommandSearch(context);
  }	
  
  
  public String executeCommandSearch(ActionContext context) {
	
	  if (!(hasPermission(context, "microchip-view"))) {
	    return ("PermissionError");
	  }
	  	
	  SystemStatus systemStatus = this.getSystemStatus(context);
	  Connection db = null;
	  try {
	    db = getConnection(context);
	    
	   //LOG PER I VETERINARI PRIVATI
	   if ( getUserRole(context) == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP")) ){
	   	  LogBean lb = new LogBean();
	   	  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Ricerca MC", context, db);
	   }
	   
	   //LOG PER UTENTI UNINA
	   if ( getUserRole(context) ==  Integer.valueOf(ApplicationProperties.getProperty("UNINA")) ){
		   	  LogBean lb = new LogBean();
		   	  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Ricerca MC, utente UNINA", context, db);
		   }
	      
	    LookupList aslList = new LookupList(db, "lookup_asl_rif");
	    aslList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
	    context.getRequest().setAttribute("aslRifList", aslList);
	    
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    
	    if ( getUserRole(context) == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || 
	    		getUserRole(context) ==  Integer.valueOf(ApplicationProperties.getProperty("UNINA"))   ) { //Ruolo Veterinario
	    	context.getRequest().setAttribute("codiceFiscale", 
	    			(getUserRole(context) == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) ? thisUser.getContact().getCodiceFiscale() : "UNINA");
	    	return ("SearchVetOK");
	    }
	
	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  return ("SearchOK");
  }
  
  public String executeCommandList(ActionContext context) {
	  
    MicrochipList mcList = new MicrochipList();
    String asl = context.getParameter("aslRif");
    String mc  = context.getParameter("microchip");
    String cf  = context.getParameter("codiceFiscale");
    int totMc			= 0;
    int totAss			= 0;
    int totAssMc		= 0;
    int totAssTat		= 0;
    int totAssFelina	= 0;
    int totFuso			= 0;
    int totAssFuretti   = 0;
    
    //Prepare pagedListInfo
	PagedListInfo microchipListInfo = this.getPagedListInfo(context, "MicrochipListInfo");
	microchipListInfo.setLink("Microchip.do?command=List&aslRif="+(asl != null ? asl :"")+"&microchip="+(mc != null ? mc :"")+"&codiceFiscale=" +(cf !=null ? cf : ""));

	Connection db = null;
	
	try {
	  db = this.getConnection(context);
	  mcList.setPagedListInfo(microchipListInfo);

	  if (asl!=null) {
		  if ( !"-1".equals(asl) ) {
			  if ( asl.length() == 16 ) { //Verififico se è un CF ..caso VP
				  mcList.setCodiceFiscale(asl);
			  }else if(("UNINA").equals(asl)){
				  mcList.setCodiceFiscale(asl);
			  }
				  else{
			  
				  mcList.setAslRif(Integer.parseInt(asl));
			  }
			  totMc   		= org.aspcfs.modules.microchip.base.Microchip.getTotaliAsl(db, asl);
			  totAssMc 		= org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslMc(db, asl,Cane.idSpecie);
			  totAssTat		= org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslTat(db, asl,Cane.idSpecie);
			  totAss        = totAssMc + totAssTat;
			  totAssFelina  = org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslMc(db, asl,Gatto.idSpecie);
			  totAssFuretti = org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslMc(db, asl,Furetto.idSpecie);
			  totFuso = org.aspcfs.modules.microchip.base.Microchip.getFuoriUso(db, asl);
			  context.getRequest().setAttribute("mcTotali", totMc);
			  context.getRequest().setAttribute("mcAssegnati", totAss);
			  context.getRequest().setAttribute("mcAssegnatiFelina", totAssFelina);
			  context.getRequest().setAttribute("mcAssegnatiFuretti", totAssFuretti);
			  context.getRequest().setAttribute("mcNonAssegnati", totMc-totAss-totAssFelina-totAssFuretti-totFuso);
			  context.getRequest().setAttribute("mcFuoriUso", totFuso );
			  context.getRequest().setAttribute("statistiche", "true");
		  }
	  }
	  
	  if (mc != null && !"".equals(mc)) {
		mcList.setMicrochip(mc);
	  }
	  
	  if ( cf != null && !"".equals(cf) ){
		  mcList.setCodiceFiscale(cf);
		  
		  totMc   = org.aspcfs.modules.microchip.base.Microchip.getTotaliAsl(db, cf);
//		  totAss  = org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAsl(db, cf);
		  totAssMc 		= org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslMc(db, cf,org.aspcfs.modules.microchip.base.Microchip.id_specie_cane);
		  totAssTat		= org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslTat(db, cf,org.aspcfs.modules.microchip.base.Microchip.id_specie_cane);
		  totAss        = totAssMc + totAssTat;
		  totAssFelina  = org.aspcfs.modules.microchip.base.Microchip.getAssegnatiAslMc(db, cf,org.aspcfs.modules.microchip.base.Microchip.id_specie_gatto);
		  totFuso = org.aspcfs.modules.microchip.base.Microchip.getFuoriUso(db, cf);
		  context.getRequest().setAttribute("mcTotali", totMc);
		  context.getRequest().setAttribute("mcAssegnati", totAss);
		  context.getRequest().setAttribute("mcNonAssegnati", totMc-totAss-totAssFelina-totFuso);
		  context.getRequest().setAttribute("mcAssegnatiFelina", totAssFelina);
		  context.getRequest().setAttribute("mcFuoriUso", totFuso );
		  context.getRequest().setAttribute("statistiche", "true");
	  }
	  
	  //LOG PER I VETERINARI PRIVATI
      if ( getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))){
    	  LogBean lb = new LogBean();
    	  lb.store( getUserAsl(context), getUserId(context), 2,"Ricerca MC:"+mc, context, db);
      }
	  
      LookupList lookupSpecie = new LookupList(db,"lookup_specie");
      context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
	  //set the searchcriteria
	  microchipListInfo.setSearchCriteria(mcList, context);
	  mcList.buildList(db);
	  context.getRequest().setAttribute("microchipList", mcList);
	  
	  if ( getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) ){ //RUOLO VETERINARIO PRIVATO
			context.getRequest().setAttribute("veterinario", true);
		}
	  
    } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
    } finally {
	    this.freeConnection(context, db);
    }
	
    return ("ListOK");
  }
  
  public String executeCommandScarico(ActionContext context) {
		
	  if (!(hasPermission(context, "microchip-view"))) {
	    return ("PermissionError");
	  }

	  Connection db = null;
	  
	  try {
		  
	    db = getConnection(context);
	    
	   //LOG PER I VETERINARI PRIVATI
	   if ( getUserRole(context) == 24 ){
	   	  LogBean lb = new LogBean();
	   	  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Scarico MC", context, db);
	   }
	   
	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  return ("ScaricoOK");
  }
  
  
  public String executeCommandElimina(ActionContext context) {
		
	  if (!(hasPermission(context, "elimina-view"))) {
	    return ("PermissionError");
	  }

	  Connection db = null;
	  
	  try {
		  
	    db = getConnection(context);
	    
	   //LOG PER I VETERINARI PRIVATI
	   if ( getUserRole(context) == 24 ){
	   	  LogBean lb = new LogBean();
	   	  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Elimina MC", context, db);
	   }
	   
	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  return ("EliminaOK");
  }
  
  public String executeCommandCarico(ActionContext context) {
		
	  if (!(hasPermission(context, "microchip-view"))) {
	    return ("PermissionError");
	  }

	  Connection db = null;
	  
	  try {
		  
	    db = getConnection(context);
	    
	   //LOG PER I VETERINARI PRIVATI
	   if ( getUserRole(context) == 24 ){
	   	  LogBean lb = new LogBean();
	   	  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Carico MC", context, db);
	   }
	   
	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  
	  context.getRequest().setAttribute("dataOggi", new Date());
	  return ("CaricoOK");
  }
  
  public String executeCommandEseguiScarico(ActionContext context) {
	  if (!(hasPermission(context, "microchip-view"))) {
	    return ("PermissionError");
	  }
	  
	  boolean    checkMC    = false;
	  boolean    isNotAssigned = false;
	  String     mc         = null;
	  String     rif        = null;
	  Connection db         = null;
	  
	  try {
		
		db = getConnection(context);
		  
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		
		//LOG PER I VETERINARI PRIVATI
	    if ( getUserRole(context) == 24 )
	    {
	    	
		  LogBean lb = new LogBean();
		  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Scarico MC", context, db);
		  
		  rif = thisUser.getContact().getCodiceFiscale();
		  
		}
	    else if ( getUserRole(context) == 37 )
	    {
		  rif = "UNINA";
		}
	    else{
		
		  rif = org.aspcfs.modules.microchip.base.Microchip.getAslMC(  getUserAsl(context) );
		  
		}
	    
	    mc = context.getParameter("microchip");
	    
	    checkMC       = org.aspcfs.modules.microchip.base.Microchip.checkMyMc(db, mc, rif);
	    isNotAssigned = org.aspcfs.modules.microchip.base.Microchip.isAssigned(db, mc);
	    
	    if ( checkMC && isNotAssigned) 
	    {
	    	org.aspcfs.modules.microchip.base.Microchip.eseguiScaricoMc(db, mc);
	    	context.getRequest().setAttribute("ok", "Scarico MC Eseguito con Successo!");
	    } 
	    else 
	    {
	    	context.getRequest().setAttribute("errore", "Impossibile Eseguire lo Scarico!");
	    }

	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  return ("ScaricoOK");
  }
  
  
  
  public String executeCommandEseguiElimina(ActionContext context) {
	  if (!(hasPermission(context, "elimina-view"))) {
	    return ("PermissionError");
	  }
	  
	 // boolean    checkMC    = false;
	  boolean    isNotAssigned = false;
	  String     mc          = null;
	  //String     rif         = null;
	  String     motivazione = null;
	  Connection db          = null;
	  
	  try {
		
		db = getConnection(context);
		  
	    mc = context.getParameter("microchip");
	    motivazione = context.getParameter("motivazione");
	    
	    isNotAssigned = org.aspcfs.modules.microchip.base.Microchip.isAssigned(db, mc);
	    
	    if (isNotAssigned) 
	    {
	    	org.aspcfs.modules.microchip.base.Microchip.eseguiEliminaMc(db, mc, motivazione);
	    	context.getRequest().setAttribute("ok", "Elimiba MC Eseguito con Successo!");
	    } 
	    else 
	    {
	    	context.getRequest().setAttribute("errore", "Impossibile Eseguire Elimina MC! Il MC è assegnato ad un animale!");
	    }

	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  return ("EliminaOK");
  }
  
  public String executeCommandEseguiCarico(ActionContext context) {
	  if (!(hasPermission(context, "microchip-view"))) {
	    return ("PermissionError");
	  }
	  
	  org.aspcfs.modules.microchip.base.Microchip m = null;
	  boolean    lengthOk        = false;
	  boolean    isNotAssigned   = false;
	  boolean    isNotTatAssigned   = false;
	  boolean    isNotduplicated = false;
	  String     mc              = null;
	  String     cf              = null;
	  Connection db              = null;
	  
	  try {
		
		db = getConnection(context);
		//db.setAutoCommit(false);
		  
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		
		m = new org.aspcfs.modules.microchip.base.Microchip();
		
		//LOG PER I VETERINARI PRIVATI
	    if ( getUserRole(context) == 24 )
	    {
		  LogBean lb = new LogBean();
		  lb.store( getUserAsl(context), getUserId(context), 2,"Maschera Carico MC", context, db);
		  cf = thisUser.getContact().getCodiceFiscale();
		}else{
			 cf = org.aspcfs.modules.microchip.base.Microchip.getAslMC(  getUserAsl(context) );
		}
	    
	   
	    mc = context.getParameter("microchip");
	    
	    m.setAsl( cf );
	    m.setMicrochip( mc );
	    m.setEnteredBy ( getUserId(context) );
	    m.setModifiedBy( getUserId(context) );
	    m.setStatusId( 6 );
	    
	    if ( mc.length() == 15 && mc.indexOf(" ")<0) { //&& m.checkNumber(mc) ){
	    	lengthOk = true;
	    }
	    
	    isNotAssigned   = org.aspcfs.modules.microchip.base.Microchip.isAssigned(db, mc);
	    isNotTatAssigned   = org.aspcfs.modules.microchip.base.Microchip.isTatuaggioAssigned(db, mc);
	    isNotduplicated = org.aspcfs.modules.microchip.base.Microchip.verifyDuplicate(db, mc);
	    
	    if ( isNotAssigned && lengthOk && isNotduplicated && isNotTatAssigned) 
	    {
	    	m.insert(db);
	    	
	    	if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
	    		String[] esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),m.getMicrochip(), "giacenza");
				context.getRequest().setAttribute("errore", esitoInvioSinaaf[1]);
				context.getRequest().setAttribute("ok", "Carico MC Eseguito con Successo!" + ((esitoInvioSinaaf[0]==null)?(""):(esitoInvioSinaaf[0])));
			}
			
	    		    } 
	    else 
	    {
	    	context.getRequest().setAttribute("errore", "Impossibile Eseguire il Carico!");
	    }

	  } catch (Exception e) {
	    context.getRequest().setAttribute("Error", e);
	    return ("SystemError");
	  } finally {
	    this.freeConnection(context, db);
	  }
	
	  context.getRequest().setAttribute("dataOggi", new Date());
	  
	  return ("CaricoOK");
  }

  
}
