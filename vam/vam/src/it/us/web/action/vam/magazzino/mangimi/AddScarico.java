/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.mangimi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.MagazzinoMangimi;
import it.us.web.bean.vam.ScaricoFarmaco;
import it.us.web.bean.vam.ScaricoMangime;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddScarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "MANGIMI", "EDIT" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoMangimi");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddScarico.class);
					
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				*/
		/* ***************************************** */
		/* ***************************************** */
		MagazzinoMangimi mMangimi = (MagazzinoMangimi) persistence.find(MagazzinoMangimi.class, interoFromRequest("idMangime"));
		
		float quantitaDaScaricare = floatFromRequest("quantitaDaScaricare");
		
		if ( (mMangimi.getQuantita() - quantitaDaScaricare) < 0f) {
			setErrore("La quantità residua deve essere maggiore o uguale a zero");
			goToAction( new Detail() );
		}
		
		mMangimi.setQuantita(mMangimi.getQuantita() - quantitaDaScaricare );
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE SCARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		ScaricoMangime sMangime = new ScaricoMangime();
		
		sMangime.setDataScarico(new Date());
		sMangime.setEntered(new Date());		
		sMangime.setEnteredBy(utente);
		sMangime.setModified(new Date());		
		sMangime.setModifiedBy(utente);
		
		sMangime.setQuantita(quantitaDaScaricare);		
		sMangime.setMagazzinoMangime(mMangimi);
		
		
		validaBean( mMangimi, new Detail()  );
		validaBean( sMangime, new Detail()  );
		
		try {				
			
			persistence.update(mMangimi);				
			persistence.insert(sMangime);
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
		redirectTo("vam.magazzino.mangimi.Detail.us");
	}

}




