/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.articoliSanitari;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.MagazzinoArticoliSanitari;
import it.us.web.bean.vam.ScaricoArticoloSanitario;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddScarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "AS", "EDIT" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoAS");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddScarico.class);
					
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				*/
		/* ***************************************** */
		/* ***************************************** */
		MagazzinoArticoliSanitari magazzinoArticoliSanitari = (MagazzinoArticoliSanitari) persistence.find(MagazzinoArticoliSanitari.class, interoFromRequest("idArticoloSanitario"));
		
		float quantitaDaScaricare = floatFromRequest("quantitaDaScaricare");
		
		if ( (magazzinoArticoliSanitari.getQuantita() - quantitaDaScaricare) < 0f) 
		{
			setErrore("La quantità residua deve essere maggiore o uguale a zero");
			goToAction( new Detail() );
		}
		
		magazzinoArticoliSanitari.setQuantita(magazzinoArticoliSanitari.getQuantita() - quantitaDaScaricare);
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE SCARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		ScaricoArticoloSanitario scaricoArticoloSanitario = new ScaricoArticoloSanitario();
		
		scaricoArticoloSanitario.setDataScarico(new Date());
		scaricoArticoloSanitario.setEntered(new Date());		
		scaricoArticoloSanitario.setEnteredBy(utente);
		scaricoArticoloSanitario.setModified(new Date());		
		scaricoArticoloSanitario.setModifiedBy(utente);
		
		scaricoArticoloSanitario.setQuantita(quantitaDaScaricare);		
		scaricoArticoloSanitario.setMagazzinoArticoliSanitari(magazzinoArticoliSanitari);
		
		
		validaBean( magazzinoArticoliSanitari, new Detail()  );
		validaBean( scaricoArticoloSanitario, new Detail()  );
		try {				
			
			persistence.update(magazzinoArticoliSanitari);				
			persistence.insert(scaricoArticoloSanitario);
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
		redirectTo("vam.magazzino.articoliSanitari.Detail.us");
	
	}

}




