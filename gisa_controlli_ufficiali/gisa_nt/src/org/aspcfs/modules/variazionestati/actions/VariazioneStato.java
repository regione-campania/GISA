/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.variazionestati.actions;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.variazionestati.base.LineaVariazione;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class VariazioneStato extends CFSModule{

	Logger logger = Logger.getLogger(VariazioneStato.class);





	public String executeCommandAdd(ActionContext context)
	{
		
		if (!(hasPermission(context, "variazione_stato_stabilimento-add"))) {
			return ("PermissionError");
		}
		
		String ret = "";
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		
			int id = -1;
			int tipologia =-1;
			
			String idString = context.getRequest().getParameter("id");
			if (idString==null)
				idString = (String) context.getRequest().getAttribute("id");
			id = Integer.parseInt(idString);
			
			String tipologiaString = context.getRequest().getParameter("tipologia");
			if (tipologiaString==null)
				tipologiaString = (String) context.getRequest().getAttribute("tipologia");
			tipologia = Integer.parseInt(tipologiaString);
			
			String esitoVariazioneStatoLinee = (String) context.getRequest().getAttribute("esitoVariazioneStatoLinee");
			context.getRequest().setAttribute("esitoVariazioneStatoLinee", esitoVariazioneStatoLinee);

			RicercaOpu ric = new RicercaOpu();
			ric.setTipologia(tipologia);
			ric.setRiferimentoId(id);
			
			 if (tipologia==999){
				ric.setRiferimentoIdNomeCol("id_stabilimento");	
				ret = "addVariazioneOpuOK";
			}
			
			ric.setListaLineeProduttiveConControllo(db);	
			ArrayList<LineaProduttiva> listaLineeOriginali = ric.getListaControlliPerLinea();
			
			ArrayList<LineaVariazione> listaLineeVariazione = new ArrayList<LineaVariazione>();
			
			for (int i = 0; i<listaLineeOriginali.size();i++){
				LineaProduttiva lineaOriginale = (LineaProduttiva) listaLineeOriginali.get(i);
				System.out.println("************** VARIAZIONE STATO ID NORMA: "+lineaOriginale.getIdNorma()+" **************");
				if (lineaOriginale.getIdNorma()== LineaProduttiva.NORMA_STABILIMENTI_852){
				
				LineaVariazione linea = new LineaVariazione();
				linea.setLinea(lineaOriginale);
				
				linea.setListaOperazioni(db);
				linea.setListaCU(ric.getListaControlliPerLinea(db, lineaOriginale.getId_rel_stab_lp()));
				linea.setOperazioneOrigine(db);
				listaLineeVariazione.add(linea);
				}
				
			}
		
			context.getRequest().setAttribute("listaLinee",listaLineeVariazione);

			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			context.getRequest().setAttribute("ListaStati", ListaStati);
			
			LookupList ListaOperazioni = new LookupList(db,"lookup_variazione_stato_operazioni");
			context.getRequest().setAttribute("ListaOperazioni", ListaOperazioni);
				
			context.getRequest().setAttribute("id", idString);
			context.getRequest().setAttribute("tipologia", tipologiaString);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return ret;
	}
	
	public String executeCommandInsert(ActionContext context)
	{
		if (!(hasPermission(context, "variazione_stato_stabilimento-add"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		String esitoVariazioneStatoLinee = "";

		int id = -1;
		int tipologia =-1;
		String idString = context.getRequest().getParameter("id");
		if (idString==null)
			idString = (String) context.getRequest().getAttribute("id");
		id = Integer.parseInt(idString);
		String tipologiaString = context.getRequest().getParameter("tipologia");
		if (tipologiaString==null)
			tipologiaString = (String) context.getRequest().getAttribute("tipologia");
		tipologia = Integer.parseInt(tipologiaString);

		
		try
		{
			db = this.getConnection(context);
			
			int i = 0;
			
			while (context.getRequest().getParameter("linea_"+i)!=null){
				String idRel = context.getRequest().getParameter("linea_"+i);
				String idOperazione = context.getRequest().getParameter("operazione_"+i);
				String idCu = context.getRequest().getParameter("idCu_"+i);
				String dataVariazione = context.getRequest().getParameter("dataVariazione_"+i);
				String idStato = context.getRequest().getParameter("idStato_"+i);

				LineaVariazione linea = new LineaVariazione();
				linea.setIdRelStabLp(idRel);
				linea.setIdOperazione(idOperazione);
				linea.setDataVariazione(dataVariazione);
				linea.setIdCu(idCu);
				linea.setIdUtente(getUserId(context));
				linea.setIdStato(idStato);
				
				 if (tipologia==999){
					linea.setIdStabilimento(id);
				}
				
				if (linea.controlloCoerenzaDate(db))
					linea.aggiornaStato(db);
				else
					esitoVariazioneStatoLinee+= linea.getErrore();
			i++;
			}	
			
			if (esitoVariazioneStatoLinee.equals(""))
				esitoVariazioneStatoLinee= "Variazione eseguita con successo.";
				
				context.getRequest().setAttribute("esitoVariazioneStatoLinee", esitoVariazioneStatoLinee);
			
				Ticket tic = new Ticket();
				tic.setTipologia_operatore(tipologia);
				context.getRequest().setAttribute("TicketDetails", tic);
				context.getRequest().setAttribute("id", idString);
				
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "InsertOK";
	}
	
}
