/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.util.Date;
import java.util.HashSet;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditGenericInfo extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}

	public void execute() throws Exception
	{
		
			final Logger logger = LoggerFactory.getLogger(EditGenericInfo.class);
					
			
			cc.setPeso(req.getParameter("peso"));
			
			HashSet<LookupHabitat> setH = objectList (LookupHabitat.class, "oph_");
			cc.setLookupHabitats(setH);
			
			HashSet<LookupFerite> setF = objectList (LookupFerite.class, "opf_");
			cc.setLookupFerite(setF);
			
			HashSet<LookupAlimentazioni> setA = objectList (LookupAlimentazioni.class, "opa_");
			cc.setLookupAlimentazionis(setA);
			
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
					
			try {
				persistence.update(cc);
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
				logger.error("Cannot Edit Generic INfo" + e.getMessage());
				throw e;		
			}
			
			
			
			setMessaggio("Informazioni aggiornate con successo");
			redirectTo( "vam.cc.Detail.us" );		
	}
}

