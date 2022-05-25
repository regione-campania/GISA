/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.gestionecu.base.Componente;
import org.aspcfs.modules.gestionecu.base.Motivo;
import org.aspcfs.modules.gestionecu.base.Qualifica;
import org.aspcfs.modules.gestionecu.base.Struttura;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneCUUtil extends CFSModule{
	
	public String executeCommandCaricaPerContoDi(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	  {
		
		if (!hasPermission(context, "gestionenuovacu-add")) {
		      return ("PermissionError");
		}
		int idAsl = -1;
		int idUtente = -1;
		
		try {idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));} catch (Exception e) {}
		try {idUtente = Integer.parseInt(context.getRequest().getParameter("idUtente"));} catch (Exception e) {}

		Connection db = null;

		try 
		{
		db = this.getConnection(context);

		//Aggiunta per conto di
		/*
	 	ArrayList<OiaNodo> nodi = new ArrayList<OiaNodo>();

			db = this.getConnection(context);
			UserBean user=(UserBean)context.getSession().getAttribute("User");
			int idAslnodo = idAsl > 0 ? idAsl : user.getSiteId() ;
			HashMap<Integer,ArrayList<OiaNodo>> strutture = (HashMap<Integer,ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
			if(idAslnodo>0)
			{
				ArrayList<OiaNodo> nodiTemp = strutture.get(idAslnodo);
				if (nodiTemp != null)
					nodi = nodiTemp;
			}
			else
			{
				Iterator<Integer> itK = strutture.keySet().iterator();
				while (itK.hasNext())
				{
					int k = itK.next();
					if (k!=8) { //NON CARICO I NODI REGIONALI
					ArrayList<OiaNodo> nodiTemp = strutture.get(k);
					for(OiaNodo nodotemp : nodiTemp)
					{
						nodi.add(nodi.size(), nodotemp);
					}
					}
				}
			}
			
			if (user.getSiteId()<=0){
				ArrayList<OiaNodo> nodiTemp = strutture.get(8);
				for(OiaNodo nodotemp : nodiTemp)
				{
					nodi.add(nodi.size(), nodotemp);
				}			
				}
			
			LookupList SiteIdList = new LookupList();
			SiteIdList.setTable("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			
			context.getRequest().setAttribute("StrutturaAsl", nodi);
			*/
		
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno = calCorrente.get(Calendar.YEAR);
		
			ArrayList<Struttura> listaStrutture = new ArrayList<Struttura>();
			listaStrutture = Struttura.buildLista(db, anno, idAsl, idUtente);
			context.getRequest().setAttribute("ListaStrutture", listaStrutture);
			
		} 
		
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		return "AddPerContoDiOK";
	  }

	public static ArrayList<Motivo> loadListaMotivi(Connection db, int riferimentoId, String riferimentoIdNomeTab, int anno, int tecnicaId){
		//Aggiunta motivi controllo

		String tecnicaFiltro = "{}";
		if (tecnicaId>0){
			if (tecnicaId==3) {
				tecnicaFiltro = "{ATTIVITA-AUDIT}";
			}	else if (tecnicaId==4) {
				tecnicaFiltro = "{PIANO, ATTIVITA-ISPEZIONE}";
			}	else if (tecnicaId==5) {
				tecnicaFiltro = "{ATTIVITA-SORVEGLIANZA}";
			}
		}

		ArrayList<Motivo> listaMotivi = new ArrayList<Motivo>();
		listaMotivi = Motivo.buildLista(db, riferimentoId, riferimentoIdNomeTab, anno, tecnicaFiltro);
		return listaMotivi;
}
	
	public static ArrayList<Qualifica> loadQualifiche(Connection db, int userId){
		ArrayList<Qualifica> listaQualifiche = Qualifica.buildList(db, userId);
		return listaQualifiche;
	}
	
	
	public String executeCommandCaricaComponenti(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}
		
		int idQualifica = -1;

		try {idQualifica = Integer.parseInt(context.getRequest().getParameter("idQualifica"));} catch (Exception e) {}
		if (idQualifica == -1)
			try {idQualifica = Integer.parseInt((String) context.getRequest().getAttribute("idQualifica"));} catch (Exception e) {}

		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno = calCorrente.get(Calendar.YEAR);

			ArrayList<Componente> listaComponenti = Componente.buildList(db, idQualifica, anno, "");
			context.getRequest().setAttribute("ListaComponenti", listaComponenti);
			
			Qualifica qualifica = new Qualifica(db, idQualifica);
			context.getRequest().setAttribute("Qualifica", qualifica);

		} 

		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		return "AddComponentiOK";
	}
	
		
	
}
