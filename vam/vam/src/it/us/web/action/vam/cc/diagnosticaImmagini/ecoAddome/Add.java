/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosticaImmagini.ecoAddome;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EcoAddome;
import it.us.web.bean.vam.EcoAddomeEsito;
import it.us.web.bean.vam.lookup.LookupEcoAddomeReferti;
import it.us.web.bean.vam.lookup.LookupEcoAddomeTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecoAddome");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);

		EcoAddome ecoAddome = new EcoAddome();
		
		BeanUtils.populate(ecoAddome, req.getParameterMap());				
		
		//GESTIONE ESITI
		//Inizializzo la lista di Esiti
		Set<EcoAddomeEsito> esiti = new HashSet<EcoAddomeEsito>();
		
		//Itero i tipi
		Iterator<LookupEcoAddomeTipo> tipi = (Iterator<LookupEcoAddomeTipo>) persistence.findAll(LookupEcoAddomeTipo.class).listIterator();
		while(tipi.hasNext())
		{
			LookupEcoAddomeTipo tipo = tipi.next();
			
			//Se l'esito per quel tipo è Normale
			if(stringaFromRequest("_"+tipo.getId()).equals("Normale"))
			{
				//Itero i referti normali possibili su quel tipo
				Iterator<LookupEcoAddomeReferti> referti = (Iterator<LookupEcoAddomeReferti>) persistence.createCriteria( LookupEcoAddomeReferti.class )
				.add( Restrictions.eq( "lookupEcoAddomeTipo.id",  tipo.getId() ))
				.add( Restrictions.eq( "tipo",  "N")).list().iterator();
				Set<LookupEcoAddomeReferti> refertiToAdd = new HashSet<LookupEcoAddomeReferti>();
				while(referti.hasNext())
				{
					LookupEcoAddomeReferti referto = referti.next();
					if(stringaFromRequest("refertoNormale_"+referto.getId())!=null)
					{
						refertiToAdd.add(referto);
					}
				}
				EcoAddomeEsito esito = new EcoAddomeEsito();
				esito.setEcoAddome(ecoAddome);
				esito.setLookupEcoAddomeReferti(refertiToAdd);
				esito.setNormale(true);
				esito.setTipo(tipo);
				esiti.add(esito);
			}
			//Se l'esito per quel tipo è Anormale
			else if(stringaFromRequest("_"+tipo.getId()).equals("Anormale"))
			{
				//Itero i referti anormali possibili su quel tipo
				Iterator<LookupEcoAddomeReferti> referti = (Iterator<LookupEcoAddomeReferti>) persistence.createCriteria( LookupEcoAddomeReferti.class )
				.add( Restrictions.eq( "lookupEcoAddomeTipo.id",  tipo.getId() ))
				.add( Restrictions.eq( "tipo",  "A")).list().iterator();
				Set<LookupEcoAddomeReferti> refertiToAdd = new HashSet<LookupEcoAddomeReferti>();
				while(referti.hasNext())
				{
					LookupEcoAddomeReferti referto = referti.next();
					if(stringaFromRequest("refertoAnormale_"+referto.getId())!=null)
					{
						refertiToAdd.add(referto);
					}
				}
				EcoAddomeEsito esito = new EcoAddomeEsito();
				esito.setEcoAddome(ecoAddome);
				esito.setLookupEcoAddomeReferti(refertiToAdd);
				esito.setNormale(false);
				esito.setTipo(tipo);
				esiti.add(esito);
			}
		}
		//FINE GESTIONE ESITI
		
		
		ecoAddome.setEntered(new Date());		
		ecoAddome.setEnteredBy(utente);
		ecoAddome.setCartellaClinica( cc );			
			
		validaBean( ecoAddome , new ToAdd() );

		try {
			persistence.insert(ecoAddome);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(ecoAddome);
			}
			
			Iterator<EcoAddomeEsito> iter = esiti.iterator();
			while( iter.hasNext() )
			{
				EcoAddomeEsito e = iter.next();
				persistence.insert(e);		
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(e);
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
			logger.error("Cannot save Eco-Addome" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Eco-Addome aggiunto");
		redirectTo("vam.cc.diagnosticaImmagini.ecoAddome.List.us");
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Eco-addome";
	}
}
