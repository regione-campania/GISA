/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import it.us.web.action.GenericAction;
import it.us.web.bean.test.Password;
import it.us.web.bean.test.Utente;
import it.us.web.dao.GenericDAO;
import it.us.web.exceptions.AuthorizationException;

public class MyTest extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		SessionFactory sessionFactory = GenericDAO.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int utente = (Integer)session.save( new Utente() );
		Password p = new Password();
		p.setUtente( (Utente)session.load(Utente.class, utente) );
		session.save( p );
		tx.commit();
		session.close();
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
