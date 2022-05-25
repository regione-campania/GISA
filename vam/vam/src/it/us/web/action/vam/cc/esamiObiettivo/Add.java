/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiObiettivo;


import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("esameObiettivo");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
						
		Date dateEsameObiettivo = DateUtils.parseDateUtil(req.getParameter("dataEsameObiettivo"));	
				
		int set 	 = interoFromRequest("numeroElementi");
		String esito = null;

		try {
			
			/* Scorro la lista di tutti gli elementi e per ognuno controllo l'esito */
			for (int i=1; i<set; i++) {				
								
				EsameObiettivo eo = new EsameObiettivo();
								
				eo.setDataEsameObiettivo(dateEsameObiettivo);
				eo.setCartellaClinica(cc);		
				eo.setEntered(new Date());		
				eo.setEnteredBy(utente);
				eo.setModified(new Date());		
				eo.setModifiedBy(utente);
				
				ArrayList<LookupEsameObiettivoTipo> lookupEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
				.add( Restrictions.eq( "description", req.getParameter("descrizione_"+i) ) ).list();
								
				eo.setLookupEsameObiettivoTipo(lookupEsameObiettivoTipo.get(0));
								
				esito = req.getParameter("_"+i);
				
				if (esito.equalsIgnoreCase("Normale")) {		
					
					eo.setNormale(true);					
					persistence.insert(eo);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eo);
					}
				}
				
				else if (esito.equals("Anormale")) {					
					
					eo.setNormale(false);						
					persistence.insert(eo);	
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eo);
					}
										
					Iterator<LookupEsameObiettivoEsito> setEOEsito = objectList(LookupEsameObiettivoEsito.class, "op_").iterator();
					
					while(setEOEsito.hasNext()) {
						
						EsameObiettivoEsito eoe = new EsameObiettivoEsito();
						eoe.setEntered(new Date());
						eoe.setEnteredBy(utente);
						eoe.setModified(new Date());
						eoe.setModifiedBy(utente);
						eoe.setDataEsameObiettivo(dateEsameObiettivo);							
					
						LookupEsameObiettivoEsito leoe = setEOEsito.next();						
												
						if (leoe.getLookupEsameObiettivoTipo().getId() == lookupEsameObiettivoTipo.get(0).getId()) {	
							eoe.setLookupEsameObiettivoEsito(leoe);							
							eoe.setEsameObiettivo(eo);	
							persistence.insert(eoe);	
							if(cc.getDataChiusura()!=null)
							{
								beanModificati.add(eoe);
							}
							
						}							
										
					}				
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
			logger.error("Cannot save Esame Obiettivo" + e.getMessage());
			throw e;		
		}
			
		setMessaggio("Esame Obiettivo inserito");
		redirectTo("vam.cc.diagnosi.List.us");
		//gotoPage("/jsp/vam/cc/esamiSangue/add.jsp");
		
		
			
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Esame Obiettivo";
	}
}
