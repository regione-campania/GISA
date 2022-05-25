/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.decessi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
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
		setSegnalibroDocumentazione("sinantropi_decesso");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);			
		
		int idSinantropo  = interoFromRequest("idSinantropo");
		
		Sinantropo s = (Sinantropo)persistence.find(Sinantropo.class, idSinantropo);
		BeanUtils.populate(s, req.getParameterMap());	
		s.setModified(new Date());		
		s.setModifiedBy(utente);
			
		/* Gestione Causa Morte*/
		int idCM = interoFromRequest("causaMorte");
		
	/*	ArrayList<LookupCMI> lcmiList = (ArrayList<LookupCMI>) persistence.createCriteria( LookupCMI.class )
		.list(); */
		
		ArrayList<LookupCMI> lcmiList = (ArrayList<LookupCMI>)req.getServletContext().getAttribute("listCMI");
		
		LookupCMI lcmi = null;
		
		Iterator lcmiIterator = lcmiList.iterator();
		
		Set<LookupCMI> setCMI = new HashSet<LookupCMI>(0);
		while(lcmiIterator.hasNext()) {			
			lcmi = (LookupCMI) lcmiIterator.next();			
			if (lcmi.getId() == idCM) 
				s.setLookupCMI(lcmi);				
		}
		
		
		try {
			
			validaBeanRedirect( s, "sinantropi.Detail.us?idSinantropo="+idSinantropo);
			
			persistence.update(s);
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
			logger.error("Cannot update Data Decesso" + e.getMessage());
			throw e;		
		}
		
		
		setMessaggio("Modica Decesso Sinantropo avvenuta con successo");
		redirectTo( "sinantropi.Detail.us?idSinantropo="+idSinantropo);	
					
	}
}



