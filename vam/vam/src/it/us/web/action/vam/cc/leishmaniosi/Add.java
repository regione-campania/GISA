/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.leishmaniosi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.bean.vam.Rickettsiosi;
import it.us.web.bean.vam.lookup.LookupLeishmaniosiEsiti;
import it.us.web.bean.vam.lookup.LookupRickettsiosiEsiti;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
		setSegnalibroDocumentazione("leishmaniosi");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
		
				
		Leishmaniosi l = new Leishmaniosi();

		BeanUtils.populate(l, req.getParameterMap());		
				
		l.setEntered(new Date());		
		l.setEnteredBy(utente);
		l.setModified(new Date());
		l.setModifiedBy(utente);			
		
		int idEsito = interoFromRequest("esito");
				
//		RegistrazioniCaninaResponse	res	= CaninaRemoteUtil.eseguiLeishmaniosi(cc.getAccettazione().getAnimale(), 
//				stringaFromRequest( "dataPrelievoLeishmaniosi" ), 
//				stringaFromRequest( "dataEsitoLeishmaniosi" ), 
//				stringaFromRequest( "ordinanzaSindaco" ), 
//				stringaFromRequest( "dataOrdinanzaSindaco" ), 
//				idEsito, utente);	
				
						
		/* Gestione Esito */
		ArrayList<LookupLeishmaniosiEsiti> lleList = (ArrayList<LookupLeishmaniosiEsiti>) persistence.createCriteria( LookupLeishmaniosiEsiti.class )
		.list();		
		
		LookupLeishmaniosiEsiti lle = null;		
		Iterator lleIterator = lleList.iterator();
		
		while(lleIterator.hasNext()) {			
			lle = (LookupLeishmaniosiEsiti) lleIterator.next();			
			if (lle.getId() == idEsito) 
				l.setLle(lle);	
		}
		
		l.setCartellaClinica((CartellaClinica)persistence.find (CartellaClinica.class, (Integer)session.getAttribute("idCc")));
					
		
		validaBean( l ,  new ToAdd() );
			
		try
		{
			persistence.insert(l);
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
			logger.error("Cannot save Leishmaniosi" + ex.getMessage());
			throw ex;		
		}
				
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(l);
		}
		
		setMessaggio("Leishmaniosi aggiunta con successo");							
		redirectTo("vam.cc.leishmaniosi.List.us");
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Leishmaniosi";
	}
}
