/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.detenzioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_detenzione");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);			
		
		int idSinantropo  = interoFromRequest("idSinantropo");
		int idDetenzione  = interoFromRequest("idDetenzione");
		
		Detenzioni d = (Detenzioni) persistence.find(Detenzioni.class, idDetenzione);
		Catture c = d.getCatture();
		
		BeanUtils.populate(d, req.getParameterMap());	
		d.setModified(new Date());		
		d.setModifiedBy(utente);
		
		
		String provinciaDetenzione = stringaFromRequest("provinciaDetenzione");
		int comuneDetenzione = 0; 
		
		if (provinciaDetenzione.equals("BN"))		
			comuneDetenzione = interoFromRequest("comuneDetenzioneBN");
		else if (provinciaDetenzione.equals("NA"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneNA");
		else if (provinciaDetenzione.equals("SA"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneSA");
		else if (provinciaDetenzione.equals("CE"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneCE");
		else if (provinciaDetenzione.equals("AV"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneAV");
				
		
/*		ArrayList<LookupComuni> listComuni2 = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list(); */
		ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>)req.getServletContext().getAttribute("listComuni");

		
		LookupComuni lc2 = null;
		
		Iterator listComuniIterator2 = listComuni.iterator();
		
		Set<Detenzioni> setDetenzioni = new HashSet<Detenzioni>(0);
		while(listComuniIterator2.hasNext()) {			
			lc2 = (LookupComuni) listComuniIterator2.next();			
			if (lc2.getId() == comuneDetenzione) 
				d.setComuneDetenzione(lc2);
		}
		d.setCatture(c);
		setDetenzioni.add(d);
		c.setDetenzionis(setDetenzioni);

		
		/* Gestione Tipologia di detentore */		
		int idDetentore = interoFromRequest("tipologiaDetentore");
		
		ArrayList<LookupDetentori> ldList = (ArrayList<LookupDetentori>) persistence.createCriteria( LookupDetentori.class )
		.list();		
		
		LookupDetentori ld = null;
		
		Iterator ldIterator = ldList.iterator();
		
		//Set<LookupTipiDocumento> setTD = new HashSet<LookupTipiDocumento>(0);
		while(ldIterator.hasNext()) {			
			ld = (LookupDetentori) ldIterator.next();			
			if (ld.getId() == idDetentore) 				
				d.setLookupDetentori(ld);
		}
		
		
		/* Gestione Tipologie documento del privato detentore */
		int idTD = interoFromRequest("tipologiaDocumento");
		
		ArrayList<LookupTipiDocumento> ltdList = (ArrayList<LookupTipiDocumento>) persistence.createCriteria( LookupTipiDocumento.class )
		.list();		
		
		LookupTipiDocumento ltd = null;
		
		Iterator ltdIterator = ltdList.iterator();
		
		Set<LookupTipiDocumento> setTD = new HashSet<LookupTipiDocumento>(0);
		while(ltdIterator.hasNext()) {			
			ltd = (LookupTipiDocumento) ltdIterator.next();			
			if (ltd.getId() == idTD) 				
				d.setLookupTipologiaDocumento(ltd);
		}
				
		
		try {
			
			validaBeanRedirect( d, "sinantropi.Detail.us?idSinantropo="+idSinantropo );
			
			persistence.update(d);
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
			logger.error("Cannot update Detenzione" + e.getMessage());
			throw e;		
		}
		
		
		setMessaggio("Modica Detenzione Sinantropo avvenuta con successo");
		redirectTo( "sinantropi.Detail.us?idSinantropo="+idSinantropo);	
					
	}
}



