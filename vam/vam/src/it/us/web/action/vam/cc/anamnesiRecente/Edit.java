/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.anamnesiRecente;

import java.util.Date;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.autopsie.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
	public void can() throws AuthorizationException, Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("anamnesi");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);
			
		BeanUtils.populate(cc, req.getParameterMap());	
		
		cc.setModified(new Date());		
		cc.setModifiedBy(utente);
		
		try {
			
			validaBean( cc , new ToAdd() );
			
			persistence.update(cc);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(cc);
			}
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
			logger.error("Cannot save Anamnesi recente" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Anamnesi recente aggiornata");
		redirectTo("vam.cc.Detail.us");
}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Anamnesi Recente";
	}
	
}

