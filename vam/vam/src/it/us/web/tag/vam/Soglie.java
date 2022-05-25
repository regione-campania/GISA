/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tag.vam;

import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.lookup.LookupSoglieEsami;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.tag.GenericTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Restrictions;

public class Soglie extends GenericTag
{
	private static final long serialVersionUID = 1L;
	
	private Serializable animale;
	private Serializable esame;
	
	private Animale animaleBean;
	private Hashtable<String, LookupSoglieEsami> soglie = new Hashtable<String, LookupSoglieEsami>();
	private String esameLabel;
	
	@SuppressWarnings("unchecked")
	public int doStartTag()
	{
		if( animale != null )
		{
			animaleBean = (Animale) animale;
			esameLabel = esame.getClass().getName().split( "[_$$_]" )[0];//getAnnotation( Table.class ).name();
			Persistence persistence = null;
			
			try
			{
				persistence = PersistenceFactory.getPersistence();
				
				ArrayList<LookupSoglieEsami> ls = (ArrayList<LookupSoglieEsami>) persistence.createCriteria( LookupSoglieEsami.class )
												.add( Restrictions.eq( "esame", esameLabel ) )
												.add( Restrictions.eq( "specie", animaleBean.getLookupSpecie() ) ).list();
				
				for( LookupSoglieEsami soglia: ls )
				{
					soglie.put( soglia.getProprieta(), soglia );
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				PersistenceFactory.closePersistence( persistence, true );
			}
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
		
	public int doEndTag()
	{
		return EVAL_BODY_INCLUDE;
	}


	public Serializable getAnimale() {
		return animale;
	}


	public void setAnimale(Serializable animale) {
		this.animale = animale;
	}


	public Serializable getEsame() {
		return esame;
	}


	public void setEsame(Serializable esame) {
		this.esame = esame;
	}


	public Animale getAnimaleBean() {
		return animaleBean;
	}


	public void setAnimaleBean(Animale animaleBean) {
		this.animaleBean = animaleBean;
	}


	public Hashtable<String, LookupSoglieEsami> getSoglie() {
		return soglie;
	}


	public void setSoglie(Hashtable<String, LookupSoglieEsami> soglie) {
		this.soglie = soglie;
	}


	public String getEsameLabel() {
		return esameLabel;
	}

	public void setEsameLabel(String esameLabel) {
		this.esameLabel = esameLabel;
	}
	
	public LookupSoglieEsami getSoglia( String proprieta )
	{
		return soglie.get( proprieta );
	}
	
	public Float getValue( String proprieta )
	{
		Float ret = null;
		
		try
		{
			ret = (Float) PropertyUtils.getProperty( esame, proprieta );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}

	public void insertLookupSoglia(String proprieta)
	{
		Persistence persistence = null;

		try
		{
			LookupSoglieEsami ls = new LookupSoglieEsami();
			ls.setEnabled( true );
			ls.setEsame( esameLabel );
			ls.setProprieta( proprieta );
			ls.setSpecie( animaleBean.getLookupSpecie() );
			ls.setMin( 1f );
			ls.setMax( 2f );
			
			persistence = PersistenceFactory.getPersistence();
			persistence.insert( ls );
			persistence.commit();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			PersistenceFactory.closePersistence( persistence, true );
		}		
	}
	
}