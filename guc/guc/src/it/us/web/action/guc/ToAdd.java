/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.guc.Asl;
import it.us.web.dao.AslDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class ToAdd extends GenericAction
{
	
	Logger logger = Logger.getLogger(ToAdd.class);

	@Override
	public void can() throws AuthorizationException 
	{
		try
		{
		isLogged();
		}
		catch(AuthorizationException e)
		{
			logger.info("Non Autenticato");
			throw e ;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		/* lista endpoint */
		ArrayList<EndPoint> endPoints = EndPoint.getListaEndPoint(db, false);
		req.setAttribute("listaEndPoints", endPoints);
		
		List<Asl> aslList = AslDAO.getAsl(db);//persistence.createCriteria(Asl.class).add(Restrictions.ge("id", 201)).addOrder(Order.asc("nome")).list();
		req.setAttribute("aslList", aslList);
		
		List<String> comuniList = AslDAO.getComuni(db);//persistence.createCriteria(Asl.class).add(Restrictions.ge("id", 201)).addOrder(Order.asc("nome")).list();
		req.setAttribute("comuniList", comuniList);
		
		try
		{
		costruisciListaRuoli();
		}
		catch(Exception ee)
		{
			logger.error("Errore Costrizione Ruoli");
		}
		
		costruisciListaCliniche();
		costruisciListaCaniliBDU();
		
		//GISA NON e'' ANCORA SUPPORTATO
		//costruisciListaStruttureGisa();
		
		costruisciListaGestori();
		costruisciListaComuni();
		costruisciListaImportatori();
		
		HashMap<String, Integer> HashProvince = costruisciListaProvince();
		req.setAttribute("HashProvince", HashProvince);

		gotoPage( "/jsp/guc/add.jsp" );
		
	}
	
	

}
