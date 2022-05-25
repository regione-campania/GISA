/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import java.sql.Connection;

import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Utente;
import it.us.web.exceptions.AuthorizationException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action
{	
	public void execute() throws Exception;
	public void can() throws AuthorizationException;
	public void startup( HttpServletRequest req, HttpServletResponse res, ServletContext context );
	public void setConnectionDb( Connection db );
	public void logOperazioniUtente( Utente utente, String operazione, String descrizioneOperazione, String ip ) throws Exception;
	public String getDescrizione();

}
