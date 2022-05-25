/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;

import org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBeanAbstract;

public class DpatAttribuzioneCompetenzeNewBean extends DpatAttribuzioneCompetenzeNewBeanAbstract
{

	@Override
	public void buildlistSezioni(Connection db, int anno,boolean configli) {
		this.elencoSezioni = new DpatWrapperSezioniBean(anno, db, true, configli);		
	}
	
	

}
