/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.magazzino.farmaci;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CaricoFarmaco;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.MagazzinoFarmaciUtil;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCarico extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "MAGAZZINO", "FARMACI", "ADD" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("magazzinoFarmaci");
	}

	public void execute() throws Exception
	{
					
		final Logger logger = LoggerFactory.getLogger(AddCarico.class);
				
		MagazzinoFarmaci mf = null;
		
		LookupFarmaci lf 
		= (LookupFarmaci) persistence.find (LookupFarmaci.class, interoFromRequest("farmaco"));
		

		LookupTipiFarmaco ltf 
		= (LookupTipiFarmaco) persistence.find (LookupTipiFarmaco.class, interoFromRequest("tipoFarmaco"));
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE MAGAZZINO 				 */
		/* ***************************************** */
		/* ***************************************** */						
		mf = MagazzinoFarmaciUtil.checkFarmaco(persistence, utente.getClinica().getId(), lf.getId(), ltf.getId()); 
		
		//Flag per discriminare se, nella clinica in questione, il farmaco è nuovo o è già presente
		boolean isNew = true;
		float quantita = floatFromRequest("numeroConfezioni") * floatFromRequest("quantitaElemento");
		
		// Se il farmaco già c'è, aggiorno solo la quantità
		if (mf != null) {
			float nuovaQuantita = mf.getQuantita() + quantita;
			
			mf.setModified(new Date());		
			mf.setModifiedBy(utente);
			mf.setQuantita(nuovaQuantita);
			
			isNew = false;
		}
		else {
			mf = new MagazzinoFarmaci();			
			mf.setClinica(utente.getClinica());				
			mf.setEntered(new Date());		
			mf.setEnteredBy(utente);
			mf.setModified(new Date());		
			mf.setModifiedBy(utente);
			mf.setQuantita(quantita);
		}
		
		mf.setLookupFarmaci(lf);
		mf.setLookupTipiFarmaco(ltf);
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE CARICO 				*/
		/* ***************************************** */
		/* ***************************************** */
		CaricoFarmaco cf = new CaricoFarmaco();
		
		cf.setDataScadenza(dataFromRequest("dataScadenza"));
		cf.setEntered(new Date());		
		cf.setEnteredBy(utente);
		cf.setModified(new Date());		
		cf.setModifiedBy(utente);
		cf.setNumeroConfezioni(interoFromRequest("numeroConfezioni"));
		cf.setQuantitaUnitaria(floatFromRequest("quantitaElemento"));
		
		cf.setMf(mf);
						
		
		validaBean( mf, new Detail()  );
		validaBean( cf, new Detail()  );
			
		try {
			
			if (isNew = true)
				persistence.insert(mf);
			else
				persistence.update(mf);
			
			persistence.insert(cf);
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




