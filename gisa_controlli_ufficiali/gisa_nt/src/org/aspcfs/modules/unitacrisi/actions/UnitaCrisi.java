/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.unitacrisi.actions;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.unitacrisi.base.UnitaCrisiBean;
import org.aspcfs.utils.web.LookupList;
import com.darkhorseventures.framework.actions.ActionContext;

public class UnitaCrisi extends CFSModule {
	
	
	static
	{
		
	}
	
	public String executeCommandDefault(ActionContext context) {
		return executeCommandLista( context );
	}
	
	public String executeCommandLista(ActionContext context) {
		Connection db = null;
		ArrayList<UnitaCrisiBean> lista_unita = null;
		
	    try {
	    	db = this.getConnection(context);
	    	lista_unita = UnitaCrisiBean.load_tutte(db);
	    	context.getRequest().setAttribute("lista_unita", lista_unita);
	    	
	    	LookupList lookup_ambito_unita_di_crisi = new LookupList(db, "lookup_ambito_unita_di_crisi");
	    	context.getRequest().setAttribute("lookup_ambito_unita_di_crisi", lookup_ambito_unita_di_crisi);
		    	
		} catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      e.printStackTrace();
		      return ("SystemError");
		} finally {
		      this.freeConnection(context, db);
		}	
		
		
		return ("UCListaOK");
	}
	
	public String executeCommandNuovo(ActionContext context) {
		 Connection db = null;
		 SystemStatus systemStatus  = this.getSystemStatus(context);
		 
		    
		    try {
		    	db = this.getConnection(context);
		    	String id_ambito = context.getRequest().getParameter("id_ambito");
		    	context.getRequest().setAttribute("id_ambito", id_ambito);

		    	LookupList lookup_ambito_unita_di_crisi = new LookupList(db, "lookup_ambito_unita_di_crisi");
		    	String ambito_stringa = lookup_ambito_unita_di_crisi.getValueFromId(id_ambito);
		    	context.getRequest().setAttribute("ambito_stringa", ambito_stringa);
			    
			    
		    } catch (Exception e) {
			      context.getRequest().setAttribute("Error", e);
			      e.printStackTrace();
			      return ("SystemError");
			} finally {
			      this.freeConnection(context, db);
			}
		
		return ("NuovoUCPopUpOK");
	}
	
	public String executeCommandInserisci(ActionContext context) {
		Connection db = null;
		UnitaCrisiBean oUnitaCrisi = new UnitaCrisiBean();
		 
		String sId_ambito 			= context.getRequest().getParameter("id_ambito");
		String sResponsabile		= context.getRequest().getParameter("responsabile");
		String sMail				= context.getRequest().getParameter("mail");
		String sTelefono			= context.getRequest().getParameter("telefono");
		String sFax					= context.getRequest().getParameter("fax");
		
		String sMessaggioOperazione = "";
		boolean bOperazioneOK = false;
		
		try {
			db = getConnection(context);
			
			int id_ambito=-1;
			if ( sId_ambito != null)		id_ambito = Integer.parseInt(sId_ambito);
			oUnitaCrisi.setId_ambito(id_ambito);
			
			oUnitaCrisi.setResponsabile(sResponsabile);
			oUnitaCrisi.setMail(sMail);
			oUnitaCrisi.setTelefono(sTelefono);
			oUnitaCrisi.setFax(sFax);
						
			oUnitaCrisi.setEntered_by( this.getUserId(context) );
			oUnitaCrisi.setModified_by( this.getUserId(context) );
			UnitaCrisiBean nuovoNodoCreato = oUnitaCrisi.store(db);
			
			sMessaggioOperazione = "Operazione di inserimento eseguita con successo...";
			bOperazioneOK		 = true;
			
		} catch (SQLException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} finally {
		      this.freeConnection(context, db);
		      context.getRequest().setAttribute("sMessaggioOperazione", sMessaggioOperazione);
		      context.getRequest().setAttribute("bOperazioneOK", bOperazioneOK);
	    }
		
		return ("InsertPopUpOK");
	}

	public String executeCommandUpdate(ActionContext context) {
		Connection db = null;
		String sId = context.getRequest().getParameter("id");
		UnitaCrisiBean oUnitaCrisi = null;
		 
		String sId_ambito 			= context.getRequest().getParameter("id_ambito");
		String sResponsabile		= context.getRequest().getParameter("responsabile");
		String sMail				= context.getRequest().getParameter("mail");
		String sTelefono			= context.getRequest().getParameter("telefono");
		String sFax					= context.getRequest().getParameter("fax");
		
		String sMessaggioOperazione = "";
		boolean bOperazioneOK = false;
		
		try {
			db = getConnection(context);
			oUnitaCrisi = UnitaCrisiBean.load(sId, db);
			
			int id_ambito=-1;
			if ( sId_ambito != null)		id_ambito = Integer.parseInt(sId_ambito);
			oUnitaCrisi.setId_ambito(id_ambito);
			
			oUnitaCrisi.setResponsabile(sResponsabile);
			oUnitaCrisi.setMail(sMail);
			oUnitaCrisi.setTelefono(sTelefono);
			oUnitaCrisi.setFax(sFax);
			
			oUnitaCrisi.setModified_by( this.getUserId(context) );
			oUnitaCrisi.update(db);
			
			sMessaggioOperazione = "Operazione di aggiornamento eseguita con successo...";
			bOperazioneOK		 = true;
			
		} catch (SQLException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			sMessaggioOperazione = "Operazione di inserimento fallita...";
			bOperazioneOK		 = false;
			e.printStackTrace();
		} finally {
			context.getRequest().setAttribute("sMessaggioOperazione", sMessaggioOperazione);
			context.getRequest().setAttribute("bOperazioneOK", bOperazioneOK);
		    this.freeConnection(context, db);
	    }
		
		return ("UpdatePopUpOK");
	}
	
	public String executeCommandCancella(ActionContext context) {
		 Connection db = null;
		 
		 try {
			 db = getConnection(context);
			 String sId = context.getRequest().getParameter("id");
			 UnitaCrisiBean.delete( Integer.parseInt(sId), getUserId(context), db);
		 } catch (Exception e) {
			 context.getRequest().setAttribute("Error", e);
			 return ("SystemError");
		 } finally {
		     this.freeConnection(context, db);
		 }
		 return executeCommandLista( context );
	}
	
	public String executeCommandModifica(ActionContext context) {
		Connection db = null;
		String sId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		try {
			db = getConnection(context);

			UnitaCrisiBean oUnitaCrisi = UnitaCrisiBean.load(sId, db);
			context.getRequest().setAttribute( "oUnitaCrisi", oUnitaCrisi );
	    	
	    	LookupList lookup_ambito_unita_di_crisi = new LookupList(db, "lookup_ambito_unita_di_crisi");
	    	String ambito_stringa = lookup_ambito_unita_di_crisi.getValueFromId(oUnitaCrisi.getId_ambito());
	    	context.getRequest().setAttribute("ambito_stringa", ambito_stringa);
		    
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		      this.freeConnection(context, db);
	    }
		
		return ("UCModificaOK");
	}

		
}
