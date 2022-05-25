/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.reimmissioni;

import it.us.web.action.GenericAction;
import it.us.web.action.sinantropi.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Reimmissioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_reimmissione");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
					
		int idCattura = interoFromRequest("idCattura");
		
		Catture c = (Catture) persistence.find(Catture.class, idCattura);
		Sinantropo sinantropo = c.getSinantropo();
		
		/*GESTIONE CODICE ISPRA*/
		String codiceIspra = stringaFromRequest("codiceIspra");
		if(codiceIspra!=null && !codiceIspra.equals("") && !SinantropoUtil.checkUniquenessCodiceIspra(persistence, sinantropo, codiceIspra))
		{
			setErrore( "Codice ISPRA già presente in BDR" );
			goToAction( new it.us.web.action.sinantropi.reimmissioni.ToAdd() );
		}
		else
		{
			sinantropo.setCodiceIspra(stringaFromRequest("codiceIspra"));
		}
		
		
		/*GESTIONE RE-IMMISSIONE*/
		Reimmissioni r = new Reimmissioni();
		BeanUtils.populate(r, req.getParameterMap());	
		r.setEntered(new Date());		
		r.setEnteredBy(utente);
		r.setModified(new Date());		
		r.setModifiedBy(utente);
		
		
		String provinciaReimmissione = stringaFromRequest("provinciaReimmissione");
		int comuneReimmissione = 0; 
		
		if (provinciaReimmissione.equals("BN"))		
			comuneReimmissione = interoFromRequest("comuneReimmissioneBN");
		else if (provinciaReimmissione.equals("NA"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneNA");
		else if (provinciaReimmissione.equals("SA"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneSA");
		else if (provinciaReimmissione.equals("CE"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneCE");
		else if (provinciaReimmissione.equals("AV"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneAV");
				
		
/*		ArrayList<LookupComuni> listComuni3 = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list();*/
		ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>)req.getServletContext().getAttribute("listComuni");

		
		LookupComuni lc3 = null;
		
		Iterator listComuniIterator3 = listComuni.iterator();
		
		Set<Reimmissioni> setReimmissioni = new HashSet<Reimmissioni>(0);
		while(listComuniIterator3.hasNext()) {			
			lc3 = (LookupComuni) listComuniIterator3.next();			
			if (lc3.getId() == comuneReimmissione) 
				r.setComuneReimmissione(lc3);
		}
		r.setCatture(c);	
			
		
		try {			
			
			validaBean( r, new ToAdd() );			
			
			//Inserimento re-immissione
			if (dataFromRequest("dataReimmissione")!=null && !stringaFromRequest("provinciaReimmissione").equals("X")){
				c.getSinantropo().setLastOperation("RILASCIO");
				persistence.insert(r);
				c.setReimmissioni(r);
				persistence.update(c);
										
			}						
			persistence.update(sinantropo);
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
			logger.error("Cannot update Sinantropo with Cattura" + e.getMessage());
			throw e;		
		}
		
		
		setMessaggio( "Registrazione Rilascio effettuata" );		
		redirectTo  ( "sinantropi.catture.List.us?idSinantropo="+c.getSinantropo().getId());
			
	}	
}




