/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.helpDesk;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ticket;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.bean.vam.lookup.LookupTickets;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.mail.Mailer;
import it.us.web.util.properties.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction {

	
	public void can() throws AuthorizationException
	{

		BGuiView gui = GuiViewDAO.getView( "HD", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("helpDesk");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Add.class);
			
		Ticket t = new Ticket();		
		
		BeanUtils.populate(t, req.getParameterMap());				
		LookupTickets lt = (LookupTickets) persistence.find(LookupTickets.class, Integer.parseInt(req.getParameter("tipologiaTicket")));
			
		t.setLookupTickets(lt);
		t.setEntered(new Date());	
		BUtenteAll ut = UtenteDAO.getUtenteAll(utente.getId());
		t.setEnteredBy( ut);
		t.setAperto(true);	
			
		try 
		{
			persistence.insert(t);
			persistence.commit();
		}
		catch (RuntimeException e)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}
			logger.error("Cannot save Ticket" + e.getMessage());
			throw e;		
		}
			
		String ruolo = ((HashMap<Integer, String>)context.getAttribute("ruoliUtenti")).get(utente.getSuperutente().getId());
		ArrayList<String> mailTo = new ArrayList<String>();
		String mailToAnagrafeCampania = "";
		String oggetto = "";
		String oggettoAnagrafeCampania = "";
		String cc = null;
		if(ruolo.equalsIgnoreCase("Referente ASL"))
		{
			mailTo.add( Application.get("MAIL_DESTINATION_ADDRESS") );
			oggetto = "[VAM] " + t.getLookupTickets().getDescription() + ": segnalazione n. " + t.getId() + " aperta da " + t.getEnteredBy();
		}
		else
		{
			//Se l'utente ha una clinica e Asl associate e l'Asl ha almeno un referente con la mail impostata
			if(utente.getClinica()!=null && utente.getClinica().getLookupAsl()!=null )
			{
				Iterator<BUtente> referenti = utente.getClinica().getLookupAsl().getReferentes().iterator();
				while(referenti.hasNext())
				{
					BUtente tempReferente = referenti.next();
					if(tempReferente.getEmail()!=null && !tempReferente.getEmail().equals("") && !mailTo.contains(tempReferente.getEmail()))
					{
						mailTo.add( tempReferente.getEmail() );
					}
				}
				
			}
			mailToAnagrafeCampania = Application.get("MAIL_DESTINATION_ADDRESS");
			oggetto = "[VAM] " + t.getLookupTickets().getDescription() + ": segnalazione n. " + t.getId() + " aperta da " + t.getEnteredBy();
			oggettoAnagrafeCampania = "[VAM -- In carico al Referente Asl]" + t.getLookupTickets().getDescription() + ": segnalazione n. " + t.getId() + " aperta da " + t.getEnteredBy();
		}
		String testo = t.getDescription() + ". La persona desidera ricevere una risposta al seguente indirizzo di posta elettronica: " + t.getEmail();
		
		Iterator<String> mails = mailTo.iterator();
		while(mails.hasNext())
			Mailer.send( mails.next(), cc, testo, oggetto );
		if(!ruolo.equalsIgnoreCase("Referente Asl") && !ruolo.equals("14"))
			Mailer.send( mailToAnagrafeCampania, cc, testo, oggettoAnagrafeCampania );
		
		setMessaggio("Segnalazione numero "+" "+ t.getId()+ " " + " inserita con successo");		
		
		goToAction(new List());
		
		
			
	}
}

