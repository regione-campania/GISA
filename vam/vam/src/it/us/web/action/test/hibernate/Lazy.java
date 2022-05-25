/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test.hibernate;

import it.us.web.action.GenericAction;
import it.us.web.bean.test.Utente;
import it.us.web.exceptions.AuthorizationException;

public class Lazy extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
//		Utente test = new Utente();
//		
//		test.setUsername( "paperino" );
//		
//		Password pwd = new Password();
//		pwd.setUsername( "aaaaa" );
//		HashSet<Password> pwds = new HashSet<Password>();
//		pwds.add( pwd );
//		pwds.add( new Password() );
//		
//		persistence.insert( test );
//		
//		for( Password pass: pwds )
//		{
//			pass.setUtente( test );
//			persistence.insert( pass );
//		}
//		
//		persistence.commit();
		
		Utente utente = (Utente) persistence.getNamedQuery("testUtente").list().get( 0 );//find( Utente.class, 1 );
		persistence.commit();
//		GenericHibernateDAO gh = new GenericHibernateDAO();
//		gh.startTransaction();
//		Utente utente = (Utente)gh.find( Utente.class, 1801l );
//		gh.commitTransaction();
//		
		req.setAttribute( "utentex", utente );
		gotoPage( "/jsp/test/lazy.jsp" );
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
