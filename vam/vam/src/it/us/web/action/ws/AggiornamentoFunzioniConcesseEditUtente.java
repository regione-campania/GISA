/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ws;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.properties.Application;

public class AggiornamentoFunzioniConcesseEditUtente extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void execute() throws Exception
	{
		System.out.println("######## RELOAD UTENTI ########");
		PrintWriter pw = res.getWriter();  
		
		String username = stringaFromRequest("username");
		
		System.out.println("RELOAD UTENTI - USERNAME: " + username);
		if(username==null)
		{
			username="";
			System.out.println("RELOAD UTENTI - USERNAME IS NULL: SI SETTA A STRINGA VUOTA " );
		}
		System.out.println("RELOAD UTENTI - ESEGUO QUERY PER TROVARE GLI UTENTI: select id from utenti_super where trashed_date is null and (username = '"+username+"' or '"+username+"' = '' )" );
		List<Integer> idSU = persistence.createSQLQuery("select id from utenti_super where trashed_date is null and (username = '"+username+"' or '"+username+"' = '' )").list();
		System.out.println("RELOAD UTENTI - NUMERO DI UTENTI TROVATI: " + ((idSU==null)?("0"):(idSU.size())));
		
		if (idSU != null && !idSU.isEmpty())
		{
			int i=0;
			System.out.println("RELOAD UTENTI - INIZIA L'ITERAZIONE DI TUTTI GLI UTENTI TROVATI ");
			while(i<idSU.size())
			{
				int idSuperutente = idSU.get(i);
				System.out.println("RELOAD UTENTI - ELABORAZIONE UTENTE CON ID: " + idSuperutente);
				
			//	int idSuperutente = interoFromRequest("idSuperutente");
				SuperUtente su = (SuperUtente)persistence.find(SuperUtente.class, idSuperutente);
				System.out.println("RELOAD UTENTI - ELABORAZIONE UTENTE CON ID: " + idSuperutente + ". CARICATO BEAN SUPERUTENTE.");
				
				HashMap<Integer, HashMap<String, String>> funzioniConcesse = (HashMap<Integer, HashMap<String, String>>)context.getAttribute("funzioniConcesse");
				HashMap<Integer, String> ruoliUtenti = (HashMap<Integer, String>)context.getAttribute("ruoliUtenti");
				Iterator<BUtente> utenti = (Iterator<BUtente>)su.getUtenti().listIterator();
				
				System.out.println("RELOAD UTENTI - ELABORAZIONE UTENTE CON ID: " + idSuperutente + ". CARICATI funzioniConcesse, ruoliUtenti e utenti collegati tabella utenti_super_ .");
				
				while(utenti.hasNext())
				{
					HashMap<String, String> funzioniConcesseUtente 	= new HashMap<String, String>();
					String ruoloUtente = "";
					BUtente tempU 			= utenti.next();
					List<String> result = persistence.createSQLQuery( 
							"select cap.subject_name as funzione " +
							"from capability_permission cap_per, capability cap, category_secureobject cat_sec " +
							"where cap_per.permissions_name = 'w' and " +
							"cap_per.capabilities_id = cap.id and " +
							"cap.category_name = cat_sec.categories_name and " +
							"cat_sec.secureobjects_name = '" + tempU.getId() + "'"
					).list();
					
					for( String funzione: result )
					{
						if (!context.getAttribute("dbMode").equals("slave")){
							funzioniConcesseUtente.put(funzione, "true");
						}
						else if (!funzione.contains("ADD") && !funzione.contains("EDIT") && !funzione.contains("DELETE")) {
							funzioniConcesseUtente.put(funzione, "true");
						}
					}
					ruoloUtente = tempU.getRuoloByTalos();
					
					funzioniConcesse.remove(tempU.getSuperutente().getId());
					funzioniConcesse.put(tempU.getSuperutente().getId(), funzioniConcesseUtente);
					
					ruoliUtenti.remove(tempU.getSuperutente().getId());
					ruoliUtenti.put(tempU.getSuperutente().getId(), ruoloUtente);
					
					break;
				}
				
				context.setAttribute( "funzioniConcesse", funzioniConcesse);
				context.setAttribute( "ruoliUtenti", ruoliUtenti);
				
				System.out.println("RELOAD UTENTI - ELABORAZIONE UTENTE CON ID: " + idSuperutente + ". SETTATI I NUOVI VALORI DI funzioniConcesse e ruoliUtenti");
				
		
				HashMap<String, HttpSession> utentiList = (HashMap<String, HttpSession>)req.getServletContext().getAttribute("utenti");
				if (utentiList!=null && !utentiList.isEmpty()){
					HttpSession userSession = (HttpSession)utentiList.get(username);
					if (userSession!=null)
						userSession.invalidate(); 
				}
				
				i++;
			}
			
			pw.write("OK");
			pw.flush();
		} else {
			/*invalido sessione per cambio username - ho concatenato i due username con ;-; es. old_username;-;new_username*/
			HashMap<String, HttpSession> utentiList = (HashMap<String, HttpSession>)req.getServletContext().getAttribute("utenti");
			if (utentiList!=null && !utentiList.isEmpty()){
		    	String split[] = username.split(";-;");
		    	if (split.length==2){
					HttpSession userSession = (HttpSession)utentiList.get(split[0]);
					if (userSession!=null){
						userSession.invalidate();
					}
					utentiList.remove(split[0]);
		    	}
			}
			pw.write("OK");
			pw.flush();
		}
		
	}

}










