/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiSangue;


import java.util.Date;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameSangue;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sangue");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Add.class);
		
		EsameSangue esameSangue = new EsameSangue();
		BeanUtils.populate(esameSangue, req.getParameterMap());				
		
		esameSangue.setEntered(new Date());		
		esameSangue.setEnteredBy(utente);
				
		esameSangue.setCartellaClinica(cc);			
			
		try {
			persistence.insert(esameSangue);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(esameSangue);
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
			logger.error("Cannot save Esame Sangue" + e.getMessage());
			throw e;
		}
		
		
		
		setMessaggio("Esame del sange aggiunto");
		redirectTo("vam.cc.esamiSangue.List.us");
		//gotoPage("/jsp/vam/cc/esamiSangue/add.jsp");
		
		
			
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Esame Sangue";
	}
}
