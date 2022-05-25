/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONArray;

public abstract class DpatWrapperSezioniNewBeanAbstract<T extends DpatSezioneNewBeanInterface>
{
	public int anno;
	public int getAnno(){return this.anno;}
	public ArrayList<T> sezioni = new ArrayList<T>();

	public ArrayList<T> getSezioni(){return this.sezioni;}
	public void setSezioni(ArrayList<T> sezioni) {this.sezioni = sezioni;}

	public JSONArray getJsonArray()
	{
		JSONArray toRet = new JSONArray();
		for(T bean : sezioni)
		{
			toRet.put(bean.getJsonObj());
		}
		return toRet;
	}
	
	public abstract int  getStatoDopoModifica(Connection db,int anno) throws Exception;
	
}
