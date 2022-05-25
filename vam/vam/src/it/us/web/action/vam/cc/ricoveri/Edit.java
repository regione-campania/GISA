/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.ricoveri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.hibernate.HibernateException;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.StrutturaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.Screenshot;

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
		setSegnalibroDocumentazione("ricoveri");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(Edit.class);
			
		int idStrutturaClinica = interoFromRequest("idStrutturaClinica");
		
		//Recupero Bean CartellaClinica
		StrutturaClinica sc = (StrutturaClinica) persistence.find (StrutturaClinica.class, idStrutturaClinica); 
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataRicovero 	= req.getParameter("ricoveroData");		
		Date ricoveroData 		= format.parse(dataRicovero);	
				
		cc.setRicoveroData				(ricoveroData);
		cc.setRicoveroBox				(req.getParameter("ricoveroBox"));
		cc.setRicoveroMotivo			(req.getParameter("ricoveroMotivo"));
		cc.setRicoveroSintomatologia	(req.getParameter("ricoveroSintomatologia"));
		cc.setRicoveroNote				(req.getParameter("ricoveroNote"));
		cc.setStrutturaClinica			(sc);
		cc.setModified					( new Date() );
		cc.setModifiedBy				( utente );
				
		try {
			persistence.update(cc);
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
			logger.error("Cannot Edit Ricovero" + e.getMessage());
			throw e;		
		}
		
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(cc);
		}
		
		setMessaggio("Ricovero inserito/modificato con successo");
		redirectTo( "vam.cc.ricoveri.Detail.us" );	
					
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Ricovero";
	}
	
	
}

