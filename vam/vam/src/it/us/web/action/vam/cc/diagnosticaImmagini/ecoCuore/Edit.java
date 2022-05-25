/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosticaImmagini.ecoCuore;


import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EcoCuore;
import it.us.web.bean.vam.EcoCuoreEsito;
import it.us.web.bean.vam.lookup.LookupEcoCuoreAnomalia;
import it.us.web.bean.vam.lookup.LookupEcoCuoreDiagnosi;
import it.us.web.bean.vam.lookup.LookupEcoCuoreTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class Edit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecoCuore");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Edit.class);

		int idEcoCuore  = interoFromRequest("idEcoCuore");
		
		//Recupero Bean Eco-Cuore
		EcoCuore ecoCuore  = (EcoCuore) persistence.find(EcoCuore.class, idEcoCuore);
		
		Iterator<EcoCuoreEsito> iter = ecoCuore.getEcoCuoreEsitos().iterator();
		while( iter.hasNext() )
		{
			EcoCuoreEsito e = iter.next();
			persistence.delete(e);		
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(e);
			}
		}
		
		
		BeanUtils.populate(ecoCuore, req.getParameterMap());				
		
		
		//GESTIONE ESITI ESAME B-MODE
		//Inizializzo la lista di Esiti
		Set<EcoCuoreEsito> esiti = new HashSet<EcoCuoreEsito>();
		
		//Itero i tipi
		Iterator<LookupEcoCuoreTipo> tipi = (Iterator<LookupEcoCuoreTipo>) persistence.findAll(LookupEcoCuoreTipo.class).listIterator();
		while(tipi.hasNext())
		{
			LookupEcoCuoreTipo tipo = tipi.next();
			
			//Se l'esito per quel tipo è Normale
			if(stringaFromRequest("_"+tipo.getId()).equals("Normale"))
			{
				EcoCuoreEsito esito = new EcoCuoreEsito();
				esito.setEcoCuore(ecoCuore);
				esito.setEnabled(true);
				esito.setLookupEcoCuoreTipo(tipo);
				esito.setNormale(true);
				esiti.add(esito);
			}
			//Se l'esito per quel tipo è Anormale
			else if(stringaFromRequest("_"+tipo.getId()).equals("Anormale"))
			{
				//Itero le anomalie possibili su quel tipo
				Iterator<LookupEcoCuoreAnomalia> anomalie = (Iterator<LookupEcoCuoreAnomalia>) persistence.createCriteria( LookupEcoCuoreAnomalia.class )
				.add( Restrictions.eq( "lookupEcoCuoreTipo.id",  tipo.getId() )).list().iterator();
				while(anomalie.hasNext())
				{
					LookupEcoCuoreAnomalia anomalia = anomalie.next();
					if(stringaFromRequest("anomalia_"+anomalia.getId())!=null)
					{
						EcoCuoreEsito esito = new EcoCuoreEsito();
						esito.setEcoCuore(ecoCuore);
						esito.setEnabled(true);
						esito.setLookupEcoCuoreTipo(tipo);
						esito.setNormale(false);
						esito.setLookupEcoCuoreAnomalia(anomalia);
						esiti.add(esito);
					}
				}
				
				
			}
		}
		ecoCuore.setEcoCuoreEsitos(esiti);
		//FINE GESTIONE ESITI ESAME B-MODE
		
		
		//Gestione Lookup Diagnosi
		Set<LookupEcoCuoreDiagnosi> diagnosi = objectList(LookupEcoCuoreDiagnosi.class, "diagnosi_");
		
		
		//Controllo che sia stata selezionata almeno una diagnosi
		if(!diagnosi.isEmpty())
		{
			ecoCuore.setLookupEcoCuoreDiagnosis(diagnosi);
		}
		//Fine Gestione Lookup Diagnosi
		
		
		
		ecoCuore.setModified(new Date());		
		ecoCuore.setModifiedBy(utente);
		ecoCuore.setCartellaClinica( cc );			
			
		validaBean( ecoCuore , new ToEdit() );
			
		try {
			persistence.update(ecoCuore);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(ecoCuore);
			}
			
			iter = esiti.iterator();
			while( iter.hasNext() )
			{
				persistence.insert(iter.next());		
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(iter.next());
				}
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
			logger.error("Cannot edit Eco-Cuore" + e.getMessage());
			throw e;	
		}
		
		setMessaggio("Eco-Cuore modificato");
		redirectTo("vam.cc.diagnosticaImmagini.ecoCuore.List.us");
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Eco-cuore";
	}
}
