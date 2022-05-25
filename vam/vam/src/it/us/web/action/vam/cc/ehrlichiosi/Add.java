/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.ehrlichiosi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.lookup.LookupAritmie;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupEhrlichiosiEsiti;
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
		setSegnalibroDocumentazione("ehrlichiosi");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);

					
		Ehrlichiosi e = new Ehrlichiosi();

		BeanUtils.populate(e, req.getParameterMap());				
				
		e.setEntered(new Date());		
		e.setEnteredBy(utente);
		e.setModified(new Date());
		e.setModifiedBy(utente);
			
		int idEsito = interoFromRequest("esito");
				
		//RegistrazioniCaninaResponse	res	= CaninaRemoteUtil.eseguiEhrlichiosi(cc.getAccettazione().getAnimale(), stringaFromRequest( "dataEhrlichiosi" ), idEsito, utente);	
		
		
		
			/* Gestione Esito */
			ArrayList<LookupEhrlichiosiEsiti> leeList = (ArrayList<LookupEhrlichiosiEsiti>) persistence.createCriteria( LookupEhrlichiosiEsiti.class )
			.list();		
			
			LookupEhrlichiosiEsiti lee = null;		
			Iterator leeIterator = leeList.iterator();		
			
			while(leeIterator.hasNext()) {			
				lee = (LookupEhrlichiosiEsiti) leeIterator.next();			
				if (lee.getId() == idEsito) 
					e.setLee(lee);	
			}
			
			e.setCartellaClinica(cc);			
			
			validaBean( e , new ToAdd());
				
			try {
				persistence.insert(e);
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(e);
				}
				cc.setModified( e.getModified() );
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
				logger.error("Cannot save Ehrlichiosi" + ex.getMessage());
				throw ex;		
			}
						
			
			setMessaggio("Ehrlichiosi aggiunta con successo");				
			redirectTo("vam.cc.ehrlichiosi.List.us");
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Ehrlichiosi";
	}
}
