/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.vigilanza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Qualifica extends GenericBean {

	private int code = -1;
	private String nome = null;
	private boolean inDpat = false;
	private boolean inNucleoIspettivo = false;
	private boolean viewLista = false;


	
	
	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public boolean isInDpat() {
		return inDpat;
	}


	public void setInDpat(boolean inDpat) {
		this.inDpat = inDpat;
	}


	public boolean isInNucleoIspettivo() {
		return inNucleoIspettivo;
	}


	public void setInNucleoIspettivo(boolean inNucleoIspettivo) {
		this.inNucleoIspettivo = inNucleoIspettivo;
	}


	public boolean isViewLista() {
		return viewLista;
	}


	public void setViewLista(boolean viewLista) {
		this.viewLista = viewLista;
	}


	public Qualifica(Connection db, int id )
	{
		try
		{
			String select = "select * from lookup_qualifiche where code = ?";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()){
				buildRecord(rs);
			}
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}


	public void buildRecord(ResultSet rs) throws SQLException
	{
		code =rs.getInt("code");
		nome =rs.getString("description");
		inDpat =rs.getBoolean("in_dpat");
		inNucleoIspettivo =rs.getBoolean("in_nucleo_ispettivo");
		viewLista =rs.getBoolean("view_lista_utenti_nucleo_ispettivo");
	}
	
}
