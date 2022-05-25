/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedesupplementari.base.tipo_scheda_lista_checkbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.ComponenteSegreteria;
import org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.Iscritto;
import org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.SedeLezioni;

import com.darkhorseventures.framework.actions.ActionContext;

public class DatiGenerici {
	
	private int idIstanza = -1;
	private ArrayList<Checkbox> listaCheckbox = new ArrayList<Checkbox>();
	
	public DatiGenerici(){
		
	}
	
	public DatiGenerici(Connection db, int idIstanza){
		
	}
	
	private void buildRecord (ResultSet rs) throws SQLException{}

	public void buildDaRequest(ActionContext context) {
		listaCheckbox = Checkbox.buildDaRequest(context);
	}

	public void insert(Connection db, int idIstanza) throws SQLException {
		for (int j = 0; j<listaCheckbox.size();j++){
			Checkbox cb = (Checkbox) listaCheckbox.get(j);
			cb.insert(db, idIstanza);
		}
	}

	public void queryRecord(Connection db, String numScheda, int idIstanza) throws SQLException {
		listaCheckbox = Checkbox.queryRecord(db, numScheda, idIstanza);
	}

	public int getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}

	public ArrayList<Checkbox> getListaCheckbox() {
		return listaCheckbox;
	}

	public void setListaCheckbox(ArrayList<Checkbox> listaCheckbox) {
		this.listaCheckbox = listaCheckbox;
	}
	
	
	



}
