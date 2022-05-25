/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.articoliSanitari;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CaricoArticoloSanitario;
import it.us.web.bean.vam.CaricoFarmaco;
import it.us.web.bean.vam.MagazzinoArticoliSanitari;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.lookup.LookupArticoliSanitari;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.MagazzinoArticoliSanitariUtil;
import it.us.web.util.vam.MagazzinoFarmaciUtil;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "AS", "ADD" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoAS");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddCarico.class);
				
		MagazzinoArticoliSanitari magazzinoArticoliSanitari = null;
		
		LookupArticoliSanitari lArticoliSanitari 
		= (LookupArticoliSanitari) persistence.find (LookupArticoliSanitari.class, interoFromRequest("articoloSanitario"));
		

		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				 */
		/* ***************************************** */
		/* ***************************************** */						
		magazzinoArticoliSanitari = MagazzinoArticoliSanitariUtil.checkArticoloSanitario(persistence, utente.getClinica().getId(), lArticoliSanitari.getId()); 
		
		//Flag per discriminare se, nella clinica in questione, l'articolo è nuovo o è già presente
		boolean isNew = true;
		float quantita = floatFromRequest("numeroConfezioni");
		
		// Se l'articolo già c'è, aggiorno solo la quantità
		if (magazzinoArticoliSanitari != null) {
			float nuovaQuantita = magazzinoArticoliSanitari.getQuantita() + quantita;
			
			magazzinoArticoliSanitari.setModified(new Date());		
			magazzinoArticoliSanitari.setModifiedBy(utente);
			magazzinoArticoliSanitari.setQuantita(nuovaQuantita);
			
			isNew = false;
		}
		else {
			magazzinoArticoliSanitari = new MagazzinoArticoliSanitari();			
			magazzinoArticoliSanitari.setClinica(utente.getClinica());				
			magazzinoArticoliSanitari.setEntered(new Date());		
			magazzinoArticoliSanitari.setEnteredBy(utente);
			magazzinoArticoliSanitari.setModified(new Date());		
			magazzinoArticoliSanitari.setModifiedBy(utente);
			magazzinoArticoliSanitari.setQuantita(quantita);
		}
		
		magazzinoArticoliSanitari.setLookupArticoliSanitari(lArticoliSanitari);
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE CARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		CaricoArticoloSanitario cArticoloSantario = new CaricoArticoloSanitario();
		
		cArticoloSantario.setDataScadenza(dataFromRequest("dataScadenza"));
		cArticoloSantario.setEntered(new Date());		
		cArticoloSantario.setEnteredBy(utente);
		cArticoloSantario.setModified(new Date());		
		cArticoloSantario.setModifiedBy(utente);
		cArticoloSantario.setNumeroConfezioni(interoFromRequest("numeroConfezioni"));
		
		cArticoloSantario.setMagazzinoArticoliSanitari(magazzinoArticoliSanitari);
						
		
		validaBean( magazzinoArticoliSanitari, new Detail()  );
		validaBean( cArticoloSantario , new Detail() );
		
		try {
			
			if (isNew)
				persistence.insert(magazzinoArticoliSanitari);
			else
				persistence.update(magazzinoArticoliSanitari);
			
			persistence.insert(cArticoloSantario);
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




