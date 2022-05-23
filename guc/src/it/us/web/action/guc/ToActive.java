/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.util.HashMap;
import java.util.List;
import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.AslDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToActive extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		int id = interoFromRequest("id");
		Utente u = null;
		List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
		//List<String> comuniList = AslDAO.getComuni(db);//persistence.createCriteria(Asl.class).add(Restrictions.ge("id", 201)).addOrder(Order.asc("nome")).list();
		//req.setAttribute("comuniList", comuniList);
		if( utentiList.size() > 0 ){
			u = utentiList.get(0);
			req.setAttribute("UserRecord", u);
			
			List<Asl> aslList = AslDAO.getAsl(db);//persistence.createCriteria(Asl.class).add(Restrictions.ge("id", 201)).addOrder(Order.asc("nome")).list();
			req.setAttribute("aslList", aslList);

			//Costruisci tutti ruoli...potrebbe non servire?
			//Boolean flag = costruisciListaRuoli();
			Boolean flag = costruisciListaRuoli();
			if (flag==false){
				//costruisciListaCliniche();
				//costruisciListaCaniliBDU();
				
				//GISA NON e'' ANCORA SUPPORTATO
				//costruisciListaStruttureGisa();
				
				//costruisciListaImportatori();
				
				//HashMap<String, Integer> HashProvince = costruisciListaProvince();
				//req.setAttribute("HashProvince", HashProvince);
				
				gotoPage( "/jsp/guc/edit.jsp" );
			} else {
				setErrore("Impossibile procedere. Lista Ruoli non disponibile");
				goToAction(new Home());
			}
		}
		else{
			setErrore("Utente con id " + id + " non trovato");
			goToAction(new Home());
		}	
	}
}
