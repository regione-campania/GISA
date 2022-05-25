/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.farmaci;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.ScaricoFarmaco;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddScarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "FARMACI", "EDIT" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoFarmaci");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddScarico.class);
					
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				*/
		/* ***************************************** */
		/* ***************************************** */
		MagazzinoFarmaci mf = (MagazzinoFarmaci) persistence.find(MagazzinoFarmaci.class, interoFromRequest("idFarmaco"));
		
		float quantitaDaScaricare = floatFromRequest("quantitaDaScaricare");
		
		float quantitaConvertita = quantitaDaScaricare * mf.getLookupTipiFarmaco().getConversioneScarico();
		
		if ( (mf.getQuantita() - quantitaConvertita) < 0f) {
			setErrore("La quantità residua deve essere > 0");
			goToAction( new Detail() );
		}
		
		mf.setQuantita(mf.getQuantita() - quantitaConvertita );
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE SCARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		ScaricoFarmaco sf = new ScaricoFarmaco();
		
		sf.setDataScarico(new Date());
		sf.setEntered(new Date());		
		sf.setEnteredBy(utente);
		sf.setModified(new Date());		
		sf.setModifiedBy(utente);
		
		sf.setQuantita(quantitaDaScaricare);		
		sf.setMf(mf);
		
		
		validaBean( mf, new Detail()  );
		validaBean( sf, new Detail()  );
			
		try {				
			
			persistence.update(mf);				
			persistence.insert(sf);
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
			logger.error("Cannot save Magazzino" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Magazzino aggiornato con successo");
		redirectTo("vam.magazzino.farmaci.Detail.us");
	
	}

}




