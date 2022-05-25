/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.terapieDegenza;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.TerapiaAssegnata;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupModalitaSomministrazioneFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.bean.vam.lookup.LookupVieSomministrazioneFarmaci;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddAssegnazione extends GenericAction 
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("terapia");
	}
	
	@Override
	public void execute() throws Exception
	{		
		
		final Logger logger = LoggerFactory.getLogger(AddAssegnazione.class);
					
		TerapiaDegenza td = (TerapiaDegenza) persistence.find(TerapiaDegenza.class, interoFromRequest("idTerapiaDegenza"));
		
		TerapiaAssegnata ta = new TerapiaAssegnata();
		
		BeanUtils.populate(ta, req.getParameterMap());
		
		MagazzinoFarmaci mf = (MagazzinoFarmaci) persistence.find(MagazzinoFarmaci.class, interoFromRequest("idFarmaco"));
		ta.setMagazzinoFarmaci(mf);
		
		LookupModalitaSomministrazioneFarmaci lmsf 
			= (LookupModalitaSomministrazioneFarmaci) persistence.find(LookupModalitaSomministrazioneFarmaci.class, interoFromRequest("modalitaSomministrazione"));
		ta.setLmsf(lmsf);
		
		LookupVieSomministrazioneFarmaci lvsf 
			= (LookupVieSomministrazioneFarmaci) persistence.find(LookupVieSomministrazioneFarmaci.class, interoFromRequest("vieSomministrazione"));
		ta.setLvsf(lvsf);
		
		
		
		ta.setEntered(new Date());		
		ta.setEnteredBy(utente);
		ta.setModified(new Date());		
		ta.setModifiedBy(utente);
		ta.setTerapiaDegenza(td);
		ta.setData(new Date());
		
		
		validaBean( ta ,  new List()  );
		
		try {
			persistence.insert(ta);
			persistence.update(td);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(ta);
				beanModificati.add(td);
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
			logger.error("Cannot save Assegnazione" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Assegnazione farmaco effettuata con successo");
		redirectTo("vam.cc.terapieDegenza.Detail.us?idTerapiaDegenza="+td.getId());
	
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Assegnazione Terapia";
	}

}



