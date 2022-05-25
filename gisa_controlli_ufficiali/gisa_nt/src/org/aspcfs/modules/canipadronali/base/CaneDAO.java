/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.base;

import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public interface CaneDAO {
	
	//commento al 214
	public CaneList searchCaneByMC( String mc,String cf_prop,String reg , int siteId,PagedListInfo searchListInfo, ActionContext context,String id_canina, String id_gisa, boolean cani_canile) 	;
	public int inserisciCane( Proprietario proprietario,UserBean user ) throws SQLException 																;
	public Proprietario dettaglioProprietario( int orgId , int idControllo ) 											;
	

}
