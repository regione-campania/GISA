/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.chirurgie.tipoIntervento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.TipoIntervento;
import it.us.web.bean.vam.lookup.LookupAritmie;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupTipologiaAltroInterventoChirurgico;
import it.us.web.dao.GuiViewDAO;

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
		setSegnalibroDocumentazione("tipoIntervento");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Add.class);
		
		TipoIntervento tipoIntervento = new TipoIntervento();
		
		BeanUtils.populate(tipoIntervento, req.getParameterMap());				
		tipoIntervento.setEntered(new Date());		
		tipoIntervento.setEnteredBy(utente);
		tipoIntervento.setCartellaClinica( cc );	
		
		String operatoriString = req.getParameter("idOp");
		String[] operatori = operatoriString.split(",");
		Set<SuperUtente> operatoriHash = new HashSet<SuperUtente>();
		for(int i=0;i<operatori.length;i++)
		{
			int			idOperatore = Integer.parseInt(operatori[i]);
			SuperUtente		user					= (SuperUtente) persistence.find( SuperUtente.class, idOperatore);
			operatoriHash.add(user);
		}
		tipoIntervento.setOperatori(operatoriHash);
		
		Set<LookupTipologiaAltroInterventoChirurgico> hsTipologie = new HashSet<LookupTipologiaAltroInterventoChirurgico>();
		String[] tipologie = req.getParameterValues("tipologia");
		
		if(tipologie!=null)
		{
			for(int j=0;j<tipologie.length;j++)
			{
				ArrayList<LookupTipologiaAltroInterventoChirurgico> tipologia = (ArrayList<LookupTipologiaAltroInterventoChirurgico>) persistence.createCriteria( LookupTipologiaAltroInterventoChirurgico.class )
						.add( Restrictions.eq( "id", Integer.parseInt(tipologie[j])) ).list();
			
				if (!tipologia.isEmpty())
					hsTipologie.add(tipologia.get(0));
			}
		}
		
		
		tipoIntervento.setTipologie(hsTipologie);
		
		validaBean( tipoIntervento , new ToAdd()  );
			
		try {
			persistence.insert(tipoIntervento);		
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(tipoIntervento);
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
			logger.error("Cannot save Tipo Intervento" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Tipo Intervento aggiunto");
		redirectTo("vam.cc.chirurgie.tipoIntervento.List.us");
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Altro intervento";
	}
}
