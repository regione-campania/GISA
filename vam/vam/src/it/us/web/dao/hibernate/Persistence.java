/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Persistence
{
	protected Session session = null;
	protected Transaction transaction = null;
	
	protected Persistence( Session session ) 
	{
		this.session = session;
		transaction = session.beginTransaction();
	}
	
	public Object insert(Object bean) throws Exception
	{
		return this.session.save( bean );
	}
	
	public void update(Object bean) throws Exception
	{
		this.session.update( bean );
	}
	
	public void delete(Object bean) throws Exception
	{
		this.session.delete( bean );
	}
	
	@SuppressWarnings("unchecked")
	public Object find(Class clazz, Long id) throws Exception
	{
		Object ret = this.session.load( clazz, id );
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Object find(Class clazz, int id) throws Exception
	{
		return this.session.load( clazz, id );
	}
	
	@SuppressWarnings("unchecked")
	public List findAll(Class clazz) throws Exception
	{
		return session.createQuery( "from " + clazz.getName() ).list();
	}
	
	public Query getNamedQuery( String namedQuery )
	{
		return session.getNamedQuery( namedQuery );
	}
	
	public Query createQuery( String query )
	{
		return session.createQuery( query );
	}
	
	public Query createSQLQuery( String query )
	{
		return session.createSQLQuery( query );
	}
	
	public Query createSQLQuery( String query, Class clazz  )
	{
		return session.createSQLQuery( query ).addEntity(clazz);
	}
	
	public void commit()
	{
		transaction.commit();
		transaction = session.beginTransaction();
	}
	
	public void rollBack()
	{
		transaction.rollback();
		transaction = session.beginTransaction();
	}
	
	@SuppressWarnings("unchecked")
	public Criteria createCriteria( Class clazz )
	{
		return session.createCriteria( clazz );
	}
	
	protected void destroy( boolean commit )
	{
		if( (session != null) && (transaction != null) )
		{
			try
			{
				if( commit )
				{
					transaction.commit();
				}
				else
				{
					transaction.rollback();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					session.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			transaction	= null;
			session		= null;
		}
	}

	protected Session getSession()
	{
		return session;
	}

	protected Transaction getTransaction()
	{
		return transaction;
	}

}
