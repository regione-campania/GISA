/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionepratiche.actions;



import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiGins;
import org.aspcfs.modules.gestioneanagrafica.base.Comune;
import org.aspcfs.modules.gestionepratiche.base.Pratica;
import org.aspcfs.modules.gestionepratiche.base.Richiesta;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;



import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestionePraticaAction extends CFSModule {
	
	public String executeCommandHomeGins(ActionContext context){
		
		Connection db = null ;
		
		try	
		{
			db = this.getConnection(context);
			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setInRegione(true);
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
		}
		catch(SQLException e)
		{
			
		}finally{
			this.freeConnection(context, db);
		}
		
		return "HomeGins";
	}

	
	public String executeCommandCreaPratica(ActionContext context) throws IndirizzoNotFoundException{
		
		int altId = -1;
		int stabId = -1;
		String tipo_operazione_pratica = "-1";
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		
		try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		
		try {tipo_operazione_pratica = context.getRequest().getParameter("tipo_operazione_pratica");} catch (Exception e){}
		if(tipo_operazione_pratica == null){
			tipo_operazione_pratica = "-1";
		}
		
		Connection db = null;  
	    
	    try{
	        db = this.getConnection(context);
	        org.aspcfs.modules.opu.base.Stabilimento stab = null;
	        
	        if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
	        
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
			if(!tipo_operazione_pratica.equalsIgnoreCase("-1")){
				context.getRequest().setAttribute("tipo_operazione_pratica", tipo_operazione_pratica);
			} else {
				context.getRequest().setAttribute("tipo_operazione_pratica", tipo_operazione_pratica);
			}
			context.getRequest().setAttribute("listRichieste", richiesta.getRichieste());
            
            if(stabId != -1 || altId != -1){
	            Comune c = new Comune();
	            if(stab.getTipoAttivita() == 1){
	            	c = new Comune(stab.getSedeOperativa().getComune());
	            	c.getNomeById(db, stab.getSedeOperativa().getComune());		            
	            } else {
	            	c = new Comune(stab.getOperatore().getSedeLegale().getComune());
	            	c.getNomeById(db, stab.getOperatore().getSedeLegale().getComune());
	            } 
	            context.getRequest().setAttribute("comuneTesto", c.getNome());
	            context.getRequest().setAttribute("comuneOsaId", c.getId() );
	            
            }
           
	     
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
		
		return "CreaPratica";
	}
	
	public String executeCommandInserisciPratica(ActionContext context) throws IndirizzoNotFoundException, IOException{
		
		int altId = -1;
		int stabId = -1;
		String numeroPratica = "";
		int tipoPratica = -1;
		String dataPratica = "";
		int comune_richiedente = -1;
		String idAggiuntaPratica = "";
		String codici_allegati = "";
		String tipo_output = "1";  //1 salva e vai a gestione anagrafica - 2 salva e inserisci nuova - 3 salva e torna alla lista
		
		Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
		String chiave_campo;
		for (String key: parameterMap.keySet()){
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("header_"))
			{
				codici_allegati += chiave_campo.replaceFirst("header_", "") + ";;";
			}
			
		}
		System.out.println(codici_allegati);
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		
		try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		
		try {tipoPratica =  Integer.parseInt(context.getRequest().getParameter("idTipologiaPratica"));} catch (Exception e){}
		context.getRequest().setAttribute("tipoPratica", String.valueOf(tipoPratica));
		
		try {dataPratica = context.getRequest().getParameter("data_richiesta");} catch (Exception e){}
		context.getRequest().setAttribute("dataPratica", dataPratica);
		
		try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
		
		try {comune_richiedente = Integer.parseInt(context.getRequest().getParameter("comune_richiedente"));} catch (Exception e){}
		context.getRequest().setAttribute("comunePratica", String.valueOf(comune_richiedente));
		
		try {idAggiuntaPratica = context.getRequest().getParameter("idAggiuntaPratica");} catch (Exception e){}
		
		Connection db = null;
		    
		    try{
		        db = this.getConnection(context);
		        Pratica pratica = new Pratica();
		        pratica.setDataOperazioneString(dataPratica);//da confermare
		        pratica.setIdComuneRichiedente(comune_richiedente);
		        pratica.setIdTipologiaPratica(tipoPratica);
		        pratica.setNumeroPratica(numeroPratica);
		        pratica.insertPratica(db, getUserId(context));
		        
		        numeroPratica = pratica.getNumeroPratica();
		        //passo numero pratica al documentale
		        GestioneAllegatiGins.aggiornaNumeroPratica(getUserId(context), idAggiuntaPratica, numeroPratica, codici_allegati, String.valueOf(comune_richiedente));
		        
		    }catch (SQLException e) {
		    	e.printStackTrace();
		    } finally {
		        this.freeConnection(context, db);
		    }
		
		context.getRequest().setAttribute("numeroPratica", numeroPratica);
		
		try {tipo_output = context.getRequest().getParameter("tipo_output");} catch (Exception e){}
		context.getRequest().setAttribute("tipo_output", tipo_output);
		context.getRequest().setAttribute("flagApicoltura", "0");
		
		return "InserisciPraticaOk";
	}

public String executeCommandCompletaPratica(ActionContext context) throws IndirizzoNotFoundException{
	
	int altId = -1;
	int stabId = -1;
	String numeroPratica = "";
	int tipoPratica = -1;
	String dataPratica = "";
	int comunePratica = -1;
	String apicoltura = "";
	
	try {altId = Integer.parseInt(context.getParameter("altId").toString());} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getParameter("stabId").toString());} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	try {tipoPratica =  Integer.parseInt(context.getParameter("tipoPratica").toString());} catch (Exception e){}
	context.getRequest().setAttribute("tipoPratica", String.valueOf(tipoPratica));
	
	try {dataPratica = context.getParameter("dataPratica").toString();} catch (Exception e){}
	context.getRequest().setAttribute("dataPratica", dataPratica);
	
	try {numeroPratica = context.getParameter("numeroPratica").toString();} catch (Exception e){}
	context.getRequest().setAttribute("numeroPratica", numeroPratica);
	
	try {comunePratica =  Integer.parseInt(context.getParameter("comunePratica").toString());} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	 try{ apicoltura = context.getRequest().getParameter("apicoltura");} catch (Exception e){}
     if(apicoltura == null){
     	apicoltura = "";
     }
     context.getRequest().setAttribute("flagApicoltura", apicoltura);
     context.getRequest().setAttribute("tipo_output", "1");
	
	return "InserisciPraticaOk";
}

	public String executeCommandSearchFormPratica(ActionContext context) throws IndirizzoNotFoundException{
			
			Connection db = null;  
		    
		    try{
		        db = this.getConnection(context);
		        Richiesta ric = new Richiesta();
		        ric.getTipoRichiesta(db);
		        context.getRequest().setAttribute("listRichieste", ric.getRichieste());
				
		    }catch (SQLException e) {
		    	e.printStackTrace();
		    } finally {
		        this.freeConnection(context, db);
		    }
		    
			return "SearchFormPratica";
	}
	
	public String executeCommandSearchFormPraticaApi(ActionContext context) throws IndirizzoNotFoundException{
	    
		return "SearchFormPraticaApi";
	}
	
	
	
	public String executeCommandSearchPraticheAmministrative(ActionContext context) {
		
		String numero_pratica = null;
		int cod_comune = -1;
		String data_pec = null;
		int tipo_pratica = -1;
		int size_pagina = 10;
		int numero_pagina = 1;
		
		try {numero_pratica = context.getRequest().getParameter("numero_pratica");} catch (Exception e){}
		
		try {cod_comune =  Integer.parseInt(context.getRequest().getParameter("comune_richiedente"));} catch (Exception e){}
		
		try {data_pec = context.getRequest().getParameter("data_richiesta");} catch (Exception e){}
		
		try {tipo_pratica =  Integer.parseInt(context.getRequest().getParameter("idTipologiaPratica"));} catch (Exception e){}
		
		try {size_pagina =  Integer.parseInt(context.getRequest().getParameter("size_pagina"));} catch (Exception e){}
		try {numero_pagina =  Integer.parseInt(context.getRequest().getParameter("numero_pagina"));} catch (Exception e){}
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			int numero_pratiche_totali = Pratica.getNumeroPraticheTotSearch(db, numero_pratica, cod_comune,
					data_pec, tipo_pratica, String.valueOf(getUserSiteId(context)));
			int numero_pagine_totali = (int) Math.ceil((double)numero_pratiche_totali/(double) size_pagina);
			context.getRequest().setAttribute("NumeroPratichePagine", String.valueOf(numero_pagine_totali));
			context.getRequest().setAttribute("SizePagine", String.valueOf(size_pagina));
			
			context.getRequest().setAttribute("numeroPratica", numero_pratica);
			context.getRequest().setAttribute("codComune", String.valueOf(cod_comune));
			context.getRequest().setAttribute("dataPec", data_pec);
			context.getRequest().setAttribute("tipoPratica", String.valueOf(tipo_pratica));
			
			if(numero_pagina > numero_pagine_totali){
				numero_pagina = numero_pagine_totali;
			}
			if(numero_pagina <= 0){
				numero_pagina = 1;
			}
			
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPraticheSearch(db, numero_pratica, cod_comune,
							data_pec, tipo_pratica, String.valueOf(getUserSiteId(context)),
							numero_pagina, size_pagina);
			context.getRequest().setAttribute("ListaPratiche", listaPratiche);
			context.getRequest().setAttribute("NumeroPagina", String.valueOf(numero_pagina));
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
            context.getRequest().setAttribute("ListaTipoRichieste", richiesta.getRichieste());
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ListaPraticheAmministrativeOK";
	}

	public String executeCommandSearchPraticheAmministrativeApi(ActionContext context) {
		
		String numero_pratica = null;
		int cod_comune = -1;
		String data_pec = null;
		int tipo_pratica = -1;
		int size_pagina = 10;
		int numero_pagina = 1;
		
		try {numero_pratica = context.getRequest().getParameter("numero_pratica");} catch (Exception e){}
		
		try {cod_comune =  Integer.parseInt(context.getRequest().getParameter("comune_richiedente"));} catch (Exception e){}
		
		try {data_pec = context.getRequest().getParameter("data_richiesta");} catch (Exception e){}
		
		try {tipo_pratica =  Integer.parseInt(context.getRequest().getParameter("idTipologiaPratica"));} catch (Exception e){}
		
		try {size_pagina =  Integer.parseInt(context.getRequest().getParameter("size_pagina"));} catch (Exception e){}
		try {numero_pagina =  Integer.parseInt(context.getRequest().getParameter("numero_pagina"));} catch (Exception e){}
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			int numero_pratiche_totali = Pratica.getNumeroPraticheTotSearchApi(db, numero_pratica, cod_comune,
					data_pec, tipo_pratica, String.valueOf(getUserSiteId(context)));
			int numero_pagine_totali = (int) Math.ceil((double)numero_pratiche_totali/(double) size_pagina);
			context.getRequest().setAttribute("NumeroPratichePagine", String.valueOf(numero_pagine_totali));
			context.getRequest().setAttribute("SizePagine", String.valueOf(size_pagina));
			
			context.getRequest().setAttribute("numeroPratica", numero_pratica);
			context.getRequest().setAttribute("codComune", String.valueOf(cod_comune));
			context.getRequest().setAttribute("dataPec", data_pec);
			context.getRequest().setAttribute("tipoPratica", String.valueOf(tipo_pratica));
			
			if(numero_pagina > numero_pagine_totali){
				numero_pagina = numero_pagine_totali;
			}
			if(numero_pagina <= 0){
				numero_pagina = 1;
			}
			
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPraticheSearchApi(db, numero_pratica, cod_comune,
							data_pec, tipo_pratica, String.valueOf(getUserSiteId(context)),
							numero_pagina, size_pagina);
			context.getRequest().setAttribute("ListaPratiche", listaPratiche);
			context.getRequest().setAttribute("NumeroPagina", String.valueOf(numero_pagina));
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
            context.getRequest().setAttribute("ListaTipoRichieste", richiesta.getRichieste());
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ListaPraticheAmministrativeApiOK";
	}
		
	
	
	public String executeCommandListaPraticheStabilimenti(ActionContext context) {
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPratiche(db, -1);
			context.getRequest().setAttribute("ListaPratiche", listaPratiche);
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
            context.getRequest().setAttribute("ListaTipoRichieste", richiesta.getRichieste());
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ListOK"; 
	}

	public String executeCommandStoricoPratiche(ActionContext context) throws SQLException, IndirizzoNotFoundException {
		
		Connection db = null;
		
		int altId = -1;
		int stabId = -1;
		
		try {altId = (Integer) context.getRequest().getAttribute("altId");} catch (Exception e){}
		if (altId==-1)
			try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		
		try {stabId = (Integer) context.getRequest().getAttribute("stabId");} catch (Exception e){}
		if (stabId==-1)
			try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	  	
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.opu.base.Stabilimento stab = null;
			
			if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
			
		    context.getRequest().setAttribute("StabilimentoDettaglio", stab);
		     
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPratiche(db, stab.getAltId());
			context.getRequest().setAttribute("ListaPratiche", listaPratiche);
	
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
	        context.getRequest().setAttribute("ListaTipoRichieste", richiesta.getRichieste());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
	
		return "StoricoPraticheOK"; 
	}
	
	public String executeCommandEliminaPratica(ActionContext context) {
		
		Connection db = null;
		
		int id_pratica = -1;

		try {id_pratica = Integer.parseInt(context.getRequest().getParameter("id_pratica"));} catch (Exception e){}
		
		try {
			db = this.getConnection(context);
			
			Pratica pratica = new Pratica();
			pratica.EliminaPratica(db, id_pratica, getUserId(context));
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("tipo_output", 3); 
		
		return "InserisciPraticaOk";
	}
	
	public String executeCommandVisualizzaDettaglioPratica(ActionContext context) {
		
		Connection db = null;
		
		int id_pratica = -1;

		try {id_pratica = Integer.parseInt(context.getRequest().getParameter("id_pratica"));} catch (Exception e){}
		Pratica pratica = new Pratica();
		
		try {
			db = this.getConnection(context);

			pratica = pratica.getDettaglioPratica(db, id_pratica);
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("dettaglio_pratica", pratica); 
		
		return "DettaglioPraticaPopupOk";
	}
	
	public String executeCommandListaPraticheApicoltura(ActionContext context) {
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPraticheApicoltura(db, -1);
			context.getRequest().setAttribute("ListaPratiche", listaPratiche);
			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
            context.getRequest().setAttribute("ListaTipoRichieste", richiesta.getRichieste());
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ListaPraticheApicolturaOK"; 
	}
	
	public String executeCommandCreaPraticaApicoltura(ActionContext context){
				
		Connection db = null;  
	    
	    try{
	        db = this.getConnection(context);

			Richiesta richiesta = new Richiesta();
			richiesta.getTipoRichiesta(db);
			context.getRequest().setAttribute("listRichieste", richiesta.getRichieste());           
	     
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
		
		return "CreaPraticaApicoltura";
	}
	
	public String executeCommandInserisciPraticaApicoltura(ActionContext context) throws IndirizzoNotFoundException, IOException{
		
		String numeroPratica = "";
		int tipoPratica = -1;
		String dataPratica = "";
		int comune_richiedente = -1;
		String idAggiuntaPratica = "";
		String codici_allegati = "";
		String tipo_output = "1";  //1 salva e vai a gestione anagrafica - 2 salva e inserisci nuova - 3 salva e torna alla lista
		
		Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
		String chiave_campo;
		for (String key: parameterMap.keySet()){
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("header_"))
			{
				codici_allegati += chiave_campo.replaceFirst("header_", "") + ";;";
			}
			
		}
		System.out.println(codici_allegati);
		
		try {tipoPratica =  Integer.parseInt(context.getRequest().getParameter("idTipologiaPratica"));} catch (Exception e){}
		context.getRequest().setAttribute("tipoPratica", String.valueOf(tipoPratica));
		
		try {dataPratica = context.getRequest().getParameter("data_richiesta");} catch (Exception e){}
		context.getRequest().setAttribute("dataPratica", dataPratica);
		
		try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
		
		try {comune_richiedente = Integer.parseInt(context.getRequest().getParameter("comune_richiedente"));} catch (Exception e){}
		context.getRequest().setAttribute("comunePratica", String.valueOf(comune_richiedente));
		
		try {idAggiuntaPratica = context.getRequest().getParameter("idAggiuntaPratica");} catch (Exception e){}
		
		Connection db = null;
		    
		    try{
		        db = this.getConnection(context);
		        Pratica pratica = new Pratica();
		        pratica.setDataOperazioneString(dataPratica);//da confermare
		        pratica.setIdComuneRichiedente(comune_richiedente);
		        pratica.setIdTipologiaPratica(tipoPratica);
		        pratica.setNumeroPratica(numeroPratica);
		        pratica.insertPraticaApi(db, getUserId(context));
		        
		        numeroPratica = pratica.getNumeroPratica();
		        //passo numero pratica al documentale
		        GestioneAllegatiGins.aggiornaNumeroPratica(getUserId(context), idAggiuntaPratica, numeroPratica, codici_allegati, String.valueOf(comune_richiedente));
		        
		    }catch (SQLException e) {
		    	e.printStackTrace();
		    } finally {
		        this.freeConnection(context, db);
		    }
		
		context.getRequest().setAttribute("numeroPratica", numeroPratica);
		
		try {tipo_output = context.getRequest().getParameter("tipo_output");} catch (Exception e){}
		context.getRequest().setAttribute("tipo_output", tipo_output);
		
		context.getRequest().setAttribute("altId", "");
		context.getRequest().setAttribute("stabId", "");
		context.getRequest().setAttribute("flagApicoltura", "1");
		
		return "InserisciPraticaOk";
	}
	
}


