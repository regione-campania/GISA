/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali_ext.base;

import com.darkhorseventures.framework.beans.GenericBean;

public class AnimaleType extends GenericBean{

	private int idSpecie ;
	private String tipoClasseAnimale ;
	public int getIdSpecie() {
		return idSpecie;
	}
	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}
	
	public void setIdSpecie(String idSpecie) {
		if (idSpecie != null && ! "".equals(idSpecie))
		this.idSpecie = Integer.parseInt(idSpecie);
	}
	public String getTipoClasseAnimale() {
		return tipoClasseAnimale;
	}
	public void setTipoClasseAnimale(String tipoClasseAnimale) {
		this.tipoClasseAnimale = tipoClasseAnimale;
	}
	
	
}
