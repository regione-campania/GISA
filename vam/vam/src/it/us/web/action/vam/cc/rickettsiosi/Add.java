/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.rickettsiosi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.Rickettsiosi;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupEhrlichiosiEsiti;
import it.us.web.bean.vam.lookup.LookupRickettsiosiEsiti;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("rickettsiosi");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
				
		Rickettsiosi r = new Rickettsiosi();

		BeanUtils.populate(r, req.getParameterMap());		
			
		
		r.setEntered(new Date());		
		r.setEnteredBy(utente);
		r.setModified(new Date());
		r.setModifiedBy(utente);
					
		int idEsito = interoFromRequest("esito");
				
		//RegistrazioniCaninaResponse	res	= CaninaRemoteUtil.eseguiRickettsiosi(cc.getAccettazione().getAnimale(), stringaFromRequest( "dataRickettsiosi" ), idEsito, utente);	
				
				
		
		/* Gestione Esito */
		ArrayList<LookupRickettsiosiEsiti> leeList = (ArrayList<LookupRickettsiosiEsiti>) persistence.createCriteria( LookupRickettsiosiEsiti.class )
		.list();		
		
		LookupRickettsiosiEsiti lee = null;		
		Iterator leeIterator = leeList.iterator();
		
		while(leeIterator.hasNext()) {			
			lee = (LookupRickettsiosiEsiti) leeIterator.next();			
			if (lee.getId() == idEsito) 
				r.setLre(lee);	
		}
		
		r.setCartellaClinica((CartellaClinica)persistence.find (CartellaClinica.class, (Integer)session.getAttribute("idCc")));
				
		validaBean( r , new ToAdd()  );

		try {
			persistence.insert(r);
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
			persistence.commit();
		}
		catch (RuntimeException ex)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}
			logger.error("Cannot save Rickettsiosi" + ex.getMessage());
			throw ex;		
		}
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(r);
		}
		
		setMessaggio("Rickettsiosi aggiunta con successo");								
		redirectTo("vam.cc.rickettsiosi.List.us");
			
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Rickettsiosi";
	}
}

