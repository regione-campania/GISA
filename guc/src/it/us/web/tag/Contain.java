/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tag;

import java.io.Serializable;
import java.util.Collection;

public class Contain extends GenericTag
{
	private static final long serialVersionUID = 1L;
	
	private Collection<Serializable> collection;
	private Serializable item;
	
	
	public int doStartTag()
	{
		int ret = SKIP_BODY;
		
		if( collection != null && collection.contains( item ) )
		{
			ret = EVAL_BODY_INCLUDE;
		}
		
		return ret;
	}
	
		
	public int doEndTag()
	{
		return EVAL_BODY_INCLUDE;
	}


	public Collection<Serializable> getCollection() {
		return collection;
	}


	public void setCollection(Collection<Serializable> collection) {
		this.collection = collection;
	}


	public Serializable getItem() {
		return item;
	}


	public void setItem(Serializable item) {
		this.item = item;
	}

}