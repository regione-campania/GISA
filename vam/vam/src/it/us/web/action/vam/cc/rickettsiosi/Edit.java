/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.rickettsiosi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.Rickettsiosi;
import it.us.web.bean.vam.lookup.LookupEhrlichiosiEsiti;
import it.us.web.bean.vam.lookup.LookupRickettsiosiEsiti;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
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
		
		final Logger logger = LoggerFactory.getLogger(Edit.class);
		
		int id = interoFromRequest("idRickettsiosi");		
		
		//Recupero Bean Rickettsiosi
		Rickettsiosi r = (Rickettsiosi) persistence.find(Rickettsiosi.class, id);
			
		BeanUtils.populate(r, req.getParameterMap());
		
		r.setModified(new Date());
		r.setModifiedBy(utente);
		
		/* Gestione Esito */
		int idEsito = interoFromRequest("esito");
				
		ArrayList<LookupRickettsiosiEsiti> leeList = (ArrayList<LookupRickettsiosiEsiti>) persistence.createCriteria( LookupRickettsiosiEsiti.class )
		.list();		
		
		LookupRickettsiosiEsiti lee = null;		
		Iterator leeIterator = leeList.iterator();
		
		while(leeIterator.hasNext()) {			
			lee = (LookupRickettsiosiEsiti) leeIterator.next();			
			if (lee.getId() == idEsito) 
				r.setLre(lee);	
		}
				
		validaBean( r,  new ToEdit()  );
		
		try 
		{
			persistence.update(r);
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
			logger.error("Cannot edit Rickettsiosi" + ex.getMessage());
			throw ex;		
		}
	
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(r);
		}
		
		setMessaggio("Modica della Rickettsiosi eseguita");
		redirectTo( "vam.cc.rickettsiosi.List.us" );	
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Rickettsiosi";
	}
}


