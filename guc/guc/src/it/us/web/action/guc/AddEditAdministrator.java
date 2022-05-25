/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.PasswordHash;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddEditAdministrator extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );
	
	@Override
	public void can() throws AuthorizationException
	{
			isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		String op = stringaFromRequest("operation");
		int id = interoFromRequest("idUtente");
		BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
		
		HashMap<String, String> query=new HashMap<String, String>();
		BUtente u  = new BUtente();
		BeanUtils.populate(u, req.getParameterMap());

		//Controllo univocita'' dell'username su utenti
		ArrayList<BUtente> utentiList = (ArrayList<BUtente>) UtenteDAO.getUtenteByUserame(db, u.getUsername());
		
		if (op.equals("add") && id==-1){  //ADD UTENTE
			if(utentiList.size() > 0){
				setErrore("Username gia'' esistente");
				req.setAttribute("UserRecord", u);
				goToAction(new ToAddEditAdministrator());
			}
			java.util.Date date= new java.util.Date();
			u.setEntered(new Timestamp(date.getTime()));
			u.setEnteredBy(utente.getId());
			u.setModified(new Timestamp(date.getTime()));
			u.setModifiedBy(utente.getId());
			u.setPassword(PasswordHash.encrypt(u.getPassword()));

			UtenteDAO.insert(db, u);
			LogUtenteDAO.loggaUtente(db, u, "Inserimento",BBButente);
			
			utentiList = (ArrayList<BUtente>) UtenteDAO.getUtenteByUserame(db, u.getUsername());
			if(utentiList.size() > 0){
				u.setId(utentiList.get(0).getId());
			}
			if (u.getId()>0)
				setMessaggio("Utente Amministratore inserito con successo");
			else
				setErrore("Errore durante l'inserimento");
		}
		else if (op.equals("edit") && id!=-1){  //EDIT UTENTE
			if (utentiList.size() > 0 && utentiList.get(0).getId()!=id){
				setErrore("Username gia'' esistente");
				req.setAttribute("UserRecord", u);
				goToAction(new ToAddEditAdministrator());
			}
			
			//utente pre-modifica
			BUtente utentePre = (BUtente) UtenteDAO.getUtenteBId(db, id);
			java.util.Date date = new java.util.Date(); 				
			
			u.setModified(new Timestamp(date.getTime()));
			u.setModifiedBy(utente.getId());
			u.setEnteredBy(utentePre.getEnteredBy());
			u.setEntered(utentePre.getEntered());

			if(u.isNewPassword())
				u.setPassword(PasswordHash.encrypt( stringaFromRequest("password1") ));
			else 
				u.setPassword(utentePre.getPassword());
			u.setId(id);
				
			UtenteDAO.update(db, u);
			LogUtenteDAO.loggaUtente(db, u, "Modifica", BBButente);
				
			if (u.getId()>0)
				setMessaggio("Utente Amministratore aggiornato con successo");
			else
				setErrore("Errore durante l'aggiornamento");	
		req.setAttribute("UserRecord", u);
		redirectTo("guc.DetailAdministrator.us?id=" + u.getId());	
		}
	}
}

