/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.catture;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Sinantropo;
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
		setSegnalibroDocumentazione("sinantropi_cattura");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);			
		
		int idCattura 	 = interoFromRequest("idCattura");
		int idSinantropo = interoFromRequest("idSinantropo");
		
		Catture c = (Catture) persistence.find(Catture.class, idCattura);
		Sinantropo s = c.getSinantropo();
		BeanUtils.populate(c, req.getParameterMap());	
		c.setModified(new Date());		
		c.setModifiedBy(utente);
		
		
		String provinciaCattura = stringaFromRequest("provinciaCattura");
		int comuneCattura = 0; 
		
		if (provinciaCattura.equals("BN"))		
			comuneCattura = interoFromRequest("comuneCatturaBN");
		else if (provinciaCattura.equals("NA"))	
			comuneCattura = interoFromRequest("comuneCatturaNA");
		else if (provinciaCattura.equals("SA"))	
			comuneCattura = interoFromRequest("comuneCatturaSA");
		else if (provinciaCattura.equals("CE"))	
			comuneCattura = interoFromRequest("comuneCatturaCE");
		else if (provinciaCattura.equals("AV"))	
			comuneCattura = interoFromRequest("comuneCatturaAV");
				
		
/*		ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list();*/
		ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>)req.getServletContext().getAttribute("listComuni");

		
		LookupComuni lc = null;
		
		Iterator listComuniIterator = listComuni.iterator();
		
		Set<Catture> setCatture = new HashSet<Catture>(0);
		while(listComuniIterator.hasNext()) {			
			lc = (LookupComuni) listComuniIterator.next();			
			if (lc.getId() == comuneCattura) 
				c.setComuneCattura(lc);
		}
		c.setSinantropo(s);
		setCatture.add(c);
		s.setCattureis(setCatture);
		
		
		
		
		try {
			
			validaBean( c, new List()  );
			
			persistence.update(c);
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
			logger.error("Cannot update Cattura" + e.getMessage());
			throw e;		
		}
		
		
		setMessaggio("Modica Rinvenimento avvenuta con successo");
		redirectTo( "sinantropi.catture.List.us?idSinantropo="+idSinantropo);	
					
	}
}


