/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToApprovazioneCriuv extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "TRASFERIMENTI", "CRIUV", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		int idTr = interoFromRequest( "id" );
		Trasferimento trasferimento = (Trasferimento) persistence.find( Trasferimento.class, idTr );
		
		
		int idClinicaFiltrare = -1;
		//Se la cc del destinatario è paerta allora è una riconsegna(l'approvazione della riconsegna sarà eliminata a breve)
		if(trasferimento.getCartellaClinicaDestinatario()!=null)
			idClinicaFiltrare = trasferimento.getClinicaDestinazione().getId();
		else
			idClinicaFiltrare  = trasferimento.getClinicaOrigine().getId();
		
		ArrayList<Clinica> cliniche = (ArrayList<Clinica>) persistence.createCriteria( Clinica.class )
			.add( Restrictions.ne( "id", idClinicaFiltrare ))
			.createAlias("lookupAsl", "asl")
			.addOrder( Order.asc( "asl.description" ) )
			.addOrder( Order.asc( "nome" ) )
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
			.list();
		
		req.setAttribute( "cliniche", cliniche );		
		req.setAttribute( "trasferimento", trasferimento );
		
		gotoPage( "/jsp/vam/cc/trasferimenti/approvazioneCriuv.jsp" );
	}

}
