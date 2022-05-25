/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.hibernate;

import java.util.Properties;

import org.hibernate.Session;

public class PersistenceFactory extends Persistence
{

	protected PersistenceFactory(Session session)
	{
		super(session);
	}
	
	public static Persistence getPersistence()
	{
		return new Persistence( HibernateFactory.openSession() );
	}
	
	public static void closePersistence( Persistence persistence, boolean commit )
	{
		if( persistence != null )
		{
			persistence.destroy( commit );
		}
	}
	
	public static Persistence getPersistenceCanina()
	{
		return new Persistence( HibernateFactory.openSessionCanina() );
	}
	
	public static Persistence getPersistenceFelina()
	{
		return new Persistence( HibernateFactory.openSessionFelina() );
	}
	
	public static Persistence getPersistenceDocumentale()
	{
		return new Persistence( HibernateFactory.openSessionDocumentale() );
	}
}
