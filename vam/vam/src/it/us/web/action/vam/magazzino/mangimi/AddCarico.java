/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.mangimi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CaricoFarmaco;
import it.us.web.bean.vam.CaricoMangime;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.MagazzinoMangimi;
import it.us.web.bean.vam.lookup.LookupEtaAnimale;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupMangimi;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.bean.vam.lookup.LookupTipoAnimale;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.MagazzinoFarmaciUtil;
import it.us.web.util.vam.MagazzinoMangimiUtil;

import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "MANGIMI", "ADD" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoMangimi");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddCarico.class);
				
		MagazzinoMangimi magazzinoMangimi = null;
		
		LookupMangimi lMangimi = (LookupMangimi) persistence.find (LookupMangimi.class, interoFromRequest("mangime"));
		

		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				 */
		/* ***************************************** */
		/* ***************************************** */						
		magazzinoMangimi = MagazzinoMangimiUtil.checkMangime(persistence, utente.getClinica().getId(), interoFromRequest("tipoAnimale"), interoFromRequest("etaAnimale"), lMangimi.getId()); 
		
		//Flag per discriminare se, nella clinica in questione, il mangime è nuovo o è già presente
		boolean isNew = true;
		float quantita = floatFromRequest("numeroConfezioni");
		
		// Se il mangime già c'è, aggiorno solo la quantità
		if (magazzinoMangimi != null) 
		{
			float nuovaQuantita = magazzinoMangimi.getQuantita() + quantita;
			
			magazzinoMangimi.setModified(new Date());		
			magazzinoMangimi.setModifiedBy(utente);
			magazzinoMangimi.setQuantita(nuovaQuantita);
			
			isNew = false;
		}
		else 
		{
			magazzinoMangimi = new MagazzinoMangimi();			
			magazzinoMangimi.setClinica(utente.getClinica());				
			magazzinoMangimi.setEntered(new Date());		
			magazzinoMangimi.setEnteredBy(utente);
			magazzinoMangimi.setModified(new Date());		
			magazzinoMangimi.setModifiedBy(utente);
			magazzinoMangimi.setQuantita(quantita);
			magazzinoMangimi.setEtaAnimale((LookupEtaAnimale)persistence.find(LookupEtaAnimale.class, interoFromRequest("etaAnimale")));
			magazzinoMangimi.setTipoAnimale((LookupTipoAnimale)persistence.find(LookupTipoAnimale.class, interoFromRequest("tipoAnimale")));
		}
		
		magazzinoMangimi.setLookupMangimi(lMangimi);
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE CARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		CaricoMangime caricoMangimi = new CaricoMangime();
		
		caricoMangimi.setDataScadenza(dataFromRequest("dataScadenza"));
		caricoMangimi.setEntered(new Date());		
		caricoMangimi.setEnteredBy(utente);
		caricoMangimi.setModified(new Date());		
		caricoMangimi.setModifiedBy(utente);
		caricoMangimi.setNumeroConfezioni(interoFromRequest("numeroConfezioni"));
		
		caricoMangimi.setMagazzinoMangimi(magazzinoMangimi);
						
		
		validaBean( magazzinoMangimi, new Detail()  );
		validaBean( caricoMangimi, new Detail()  );
		
		try {
			
			if (isNew = true)
				persistence.insert(magazzinoMangimi);
			else
				persistence.update(magazzinoMangimi);
			
			persistence.insert(caricoMangimi);
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




