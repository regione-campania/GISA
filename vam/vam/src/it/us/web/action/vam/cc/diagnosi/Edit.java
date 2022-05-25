/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.DiagnosiEffettuate;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("diagnosi");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);

		int idDiagnosi  = interoFromRequest("idDiagnosi");		
		
		Date dataDiagnosi = DateUtils.parseDateUtil(req.getParameter("dataDiagnosi"));	
		
		//Recupero Bean Diagnosi
		Diagnosi d  = (Diagnosi) persistence.find(Diagnosi.class, idDiagnosi);
		
		BeanUtils.populate(d, req.getParameterMap());		
		d.setModified(new Date());
		d.setModifiedBy(utente);
		d.setDiagnosi(stringaFromRequest("diagnosi"));
		d.setTipoDiagnosi(stringaFromRequest("tipoDiagnosi"));
		
		try {
			persistence.update(d);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(d);
			}
			
			
			if (!d.getDiagnosiEffettuate().isEmpty()) {
				
				Set<DiagnosiEffettuate> diagnosiEffettuate = d.getDiagnosiEffettuate();
				Iterator deList = diagnosiEffettuate.iterator();
				DiagnosiEffettuate de;
				
				while(deList.hasNext()) {
					de =  (DiagnosiEffettuate) deList.next();
					persistence.delete(de);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(de);
					}
				}
				persistence.commit();		
			
			}
			
			
			ArrayList<LookupDiagnosi> listaDiagnosi = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
			.addOrder( Order.asc( "level" ) )
			.list();
			
			LookupDiagnosi diagnosiCorrente;
			DiagnosiEffettuate de = null;
			
			for(int i=0; i<listaDiagnosi.size();i++) {
				
				diagnosiCorrente = (LookupDiagnosi) listaDiagnosi.get(i);
				
				if (req.getParameter("op_"+diagnosiCorrente.getId())!=null) {
					
					de = new DiagnosiEffettuate();
					
					de.setDiagnosi(d);
					de.setListaDiagnosi(diagnosiCorrente);
					
					
					String tipoDiagnosi = "";
					
					if (stringaFromRequest("tipoDiagnosi_"+diagnosiCorrente.getId()) != null)
						tipoDiagnosi = req.getParameter("tipoDiagnosi_"+diagnosiCorrente.getId());
										
					
					if (tipoDiagnosi.equalsIgnoreCase("Provata")) {
						de.setProvata(true);
					}
					else {
						de.setProvata(false);
					}
									
					persistence.insert(de);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(de);
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
			logger.error("Cannot save Diagnosi" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Modica della diagnosi eseguita");
		redirectTo( "vam.cc.diagnosi.List.us");	
					
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Diagnosi";
	}
}


