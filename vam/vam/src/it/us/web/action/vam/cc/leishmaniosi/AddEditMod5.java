/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.leishmaniosi;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.HibernateException;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddEditMod5 extends GenericAction {

	
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
		
		final Logger logger = LoggerFactory.getLogger(AddEditMod5.class);
			
		//Recupero Bean CartellaClinica
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data 		= dataFromRequest("dataMod5");
		String num 		= stringaFromRequest("numMod5");
				
		cc.setDataMod5(data);
		cc.setNumMod5(num);
		cc.setModified					( new Date() );
		cc.setModifiedBy				( utente );
				
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
			logger.error("Cannot Edit Mod 5" + e.getMessage());
			throw e;		
		}
		
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(cc);
		}
		
		setMessaggio("Mod. 5/A inserito/modificato con successo");
		redirectTo( "vam.cc.leishmaniosi.List.us" );	
					
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Mod.5 Leishmaniosi";
	}
	
	
}

