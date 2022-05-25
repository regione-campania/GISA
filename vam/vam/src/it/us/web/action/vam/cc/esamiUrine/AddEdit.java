/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiUrine;


import java.util.Date;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameUrine;
import it.us.web.bean.vam.lookup.LookupEsameUrineColore;
import it.us.web.bean.vam.lookup.LookupEsameUrinePresenze;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;

public class AddEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("urine");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(AddEdit.class);
		
		boolean edit = booleanoFromRequest( "edit" );
		
		EsameUrine esame = null;
		
		if(!edit)
		{
			esame = new EsameUrine();
		}
		else
		{
			esame = (EsameUrine) persistence.find( EsameUrine.class, interoFromRequest("idEu") );
		}
		
		BeanUtils.populate( esame, req.getParameterMap() );
		
		esame.setGlucosio( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "glucosio_id" ) ) );
		esame.setBilirubina( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "bilirubina_id" ) ) );
		esame.setCorpiChetonici( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "corpi_chetonici_id" ) ) );
		esame.setEmoglobina( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "emoglobina_id" ) ) );
		esame.setNitriti( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "nitriti_id" ) ) );
		esame.setSangue( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "sangue_id" ) ) );
		esame.setBatteri( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "batteri_id" ) ) );
		esame.setCilindri( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "cilindri_id" ) ) );
		
		esame.setColore( (LookupEsameUrineColore) persistence.find( LookupEsameUrineColore.class, interoFromRequest( "colore_id" )) );
		esame.setCelluleEpiteliali( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "cellule_epiteliali_id" ) ) );
		esame.setCristalli( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "cristalli_id" ) ) );
		esame.setEritrociti( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "eritrociti_id" ) ) );
		esame.setLeucociti( (LookupEsameUrinePresenze) persistence.find( LookupEsameUrinePresenze.class, interoFromRequest( "leucociti_id" ) ) );

		esame.setModified( new Date() );
		esame.setModifiedBy( utente );
		
		esame.setCartellaClinica( cc );			
			
		try
		{
			if(!edit)
			{
				esame.setEntered( esame.getModified() );
				esame.setEnteredBy( utente );
				persistence.insert( esame );
				setMessaggio("Esame delle urine aggiunto");
			}
			else
			{
				persistence.update( esame );
				setMessaggio("Esame delle urine modificato");
			}
			cc.setModified( esame.getModified() );
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
			logger.error("Cannot save Esame Urine" + e.getMessage());
			throw e;
		}
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(esame);
		}
		
		redirectTo("vam.cc.esamiUrine.Detail.us?id=" + esame.getId());
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica Esame Urine";
	}
}
