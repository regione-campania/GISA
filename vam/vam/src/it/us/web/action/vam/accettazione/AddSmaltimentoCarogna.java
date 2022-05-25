/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Date;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddSmaltimentoCarogna extends GenericAction  implements Specie
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("dimissioni");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void execute() throws Exception
	{		
		
		final Logger logger = LoggerFactory.getLogger(Add.class);
		
		int idAccettazione = interoFromRequest("idAccettazione");
		Accettazione accettazione 			 = (Accettazione) persistence.find (Accettazione.class, idAccettazione);
		Animale animale 			 = accettazione.getAnimale();

		animale.setDataSmaltimentoCarogna(dataFromRequest("dataSmaltimentoCarogna"));
		animale.setDdt(stringaFromRequest("ddt"));
		animale.setDittaAutorizzata(stringaFromRequest("dittaAutorizzata"));

		validaBean( animale,  new it.us.web.action.vam.cc.Detail() );
			
		try {
			persistence.update(animale);
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
			logger.error("Cannot update Smaltimento Carogna" + e.getMessage());
			throw e;		
		}
		setMessaggio("Trasporto spoglie registrato con successo");
					
		redirectTo("vam.accettazione.Detail.us?id="+idAccettazione);
	}

}



