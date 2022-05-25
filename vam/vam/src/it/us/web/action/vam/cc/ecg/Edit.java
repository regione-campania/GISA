/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.ecg;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.lookup.LookupAritmie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;

public class Edit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecg");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Edit.class);

		int idEcg  = interoFromRequest("idEcg");
		String diagnosi = stringaFromRequest("diagnosi");
		
		//Recupero Bean ECG
		Ecg ecg  = (Ecg) persistence.find(Ecg.class, idEcg);
		
		BeanUtils.populate(ecg, req.getParameterMap());
		
		Set<LookupAritmie> aritmie = objectList(LookupAritmie.class, "aritmia_");
		Iterator<LookupAritmie> aritmieIterator = objectList(LookupAritmie.class, "aritmia_").iterator();
		
		//Controllo che sia stata selezionata un'aritmia: in tal caso diagnosi = P(positiva)
		if(diagnosi==null && aritmieIterator.hasNext())
		{
			ecg.setDiagnosi("P");
			ecg.setAritmie(aritmie);
		}
		else if(diagnosi==null && !aritmieIterator.hasNext())
		{
			ecg.setDiagnosi(null);
			ecg.setAritmie(null);
		}
		else
			ecg.setDiagnosi("N");
		
		ecg.setModified(new Date());
		ecg.setModifiedBy(utente);
		
		validaBean( ecg , new ToEdit() );
		
		try 
		{
			persistence.update(ecg);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(ecg);
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
			logger.error("Cannot edit ECG" + e.getMessage());
			throw e;		
		}
					
		
		setMessaggio("Modica dell'ECG eseguita");
		redirectTo( "vam.cc.ecg.List.us" );	
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Ecg";
	}
}


