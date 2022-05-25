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

import org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.SedeLezioni;

import com.darkhorseventures.framework.actions.ActionContext;

public class Checkbox {
	
	private int idIstanza = -1;

	private String intestazione = null;
	private int code = -1;
	private String descrizione = null;
	private boolean checked = false;
	
	public Checkbox() {
		// TODO Auto-generated constructor stub
	}
	
	public Checkbox(ResultSet rs) throws SQLException {
		this.idIstanza = rs.getInt("id_istanza");
		this.intestazione = rs.getString("intestazione");
		this.code = rs.getInt("code");
		this.descrizione = rs.getString("descrizione");
		this.checked = rs.getBoolean("checked");
	}

	
	public static ArrayList<Checkbox> buildDaRequest(ActionContext context) {
		ArrayList<Checkbox> lista = new  ArrayList<Checkbox>();
		for (int i = 0; i<=100; i++){
			if (context.getRequest().getParameter("cb_"+i)!=null) {
				Checkbox elem = new Checkbox();
				elem.setCode(Integer.parseInt(context.getRequest().getParameter("cb_"+i+"_code")));
				elem.setChecked(true);
				lista.add(elem);
		}
		}
		return lista;
	}

	public void insert(Connection db, int idIstanza) throws SQLException {

		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.insert_checkbox(?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, code);
		pst.setBoolean(++i, checked);
		pst.executeQuery();				
	
	}
	
	public static ArrayList<Checkbox> queryRecord(Connection db, String numScheda, int idIstanza) throws SQLException {
		ArrayList<Checkbox> lista = new  ArrayList<Checkbox>();
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_lista_checkbox(?, ?)");
		pst.setString(1, numScheda);
		pst.setInt(2, idIstanza);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Checkbox cb = new Checkbox(rs);
			lista.add(cb);
		}
		return lista;
	
	}

	public int getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

}
