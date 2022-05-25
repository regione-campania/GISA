/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosticaImmagini.rx;


import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Rx;
import it.us.web.dao.GuiViewDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class Edit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("rx");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Edit.class);


		int idRx  = interoFromRequest("idRx");
		
		Rx rx  = (Rx) persistence.find(Rx.class, idRx);
	
		BeanUtils.populate(rx, req.getParameterMap());				
		rx.setModified(new Date());		
		rx.setModifiedBy(utente);
		rx.setCartellaClinica( cc );			
			
		validaBean( rx , new ToEdit() );
			
		try {
			persistence.update(rx);	
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(rx);
			}
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
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
			logger.error("Cannot edit RX" + e.getMessage());
			throw e;	
		}
		
		setMessaggio("RX modificato");
		redirectTo("vam.cc.diagnosticaImmagini.rx.List.us");
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Rx";
	}
}
