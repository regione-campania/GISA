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

public class ComponenteNucleoIspettivo extends GenericBean {

	private int id = -1;
	private int idControlloUfficiale = -1;
	private int idQualifica = -1;
	private String nomeComponente = null;
	private int idComponente =-1;
	private boolean enabled = true;

	public int getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(int idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(int idQualifica) {
		this.idQualifica = idQualifica;
	}

	public String getNomeComponente() {
		return nomeComponente;
	}

	public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	}

	public void setDatiComponente(Connection db) throws SQLException {
		
		User utente = new User(db, idComponente);
		Contact contatto = null;
		if (utente.getContactId()>=10000000)
			contatto = new Contact(db, utente.getContactId(), true);
		else
			contatto = new Contact(db, utente.getContactId());
		String nome = contatto.getNameFirst() + " " + contatto.getNameLast();
		this.nomeComponente = nome;
		this.idQualifica = utente.getRoleId();
	}
	
	public int getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(int idComponente) {
		this.idComponente = idComponente;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	public ComponenteNucleoIspettivo(Connection db, int id )
	{
		try
		{
			String select = "select * from cu_nucleo where id = ?";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()){
				buildRecord(rs);
				setDatiComponente(db);
			}
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}

	public ComponenteNucleoIspettivo(Connection db, ResultSet rs )
	{
		try {
			buildRecord(rs);
			setDatiComponente(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ComponenteNucleoIspettivo() {
		// TODO Auto-generated constructor stub
	}

	public void costruisci(Connection db)
	{
		try
		{
			String select = "select * from cu_nucleo where id_controllo_ufficiale = ? and id_componente = ? and enabled";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, idControlloUfficiale);
			pst.setInt(2, idComponente);
			rs = pst.executeQuery();
			if (rs.next())
				buildRecord(rs);
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}
	public void insert(Connection db )
	{
		try
		{
			this.id = DatabaseUtils.getNextSeq(db, "cu_nucleo_id_seq");
			String insert = "insert into cu_nucleo(id, id_controllo_ufficiale, id_componente) values (?, ?, ?)";
			PreparedStatement pst = null ;
			pst = db.prepareStatement(insert);

			pst.setInt(1, id);
			pst.setInt(2, idControlloUfficiale);
			pst.setInt(3, idComponente);
			pst.execute();
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}

	public void buildRecord(ResultSet rs) throws SQLException
	{
		id =rs.getInt("id");
		idControlloUfficiale =rs.getInt("id_controllo_ufficiale");
		idQualifica  = -1;
		nomeComponente  ="";
		idComponente =rs.getInt("id_componente");
		enabled =rs.getBoolean("enabled");
		
		
	}
	public void update(Connection db )
	{
		try
		{
			String update = "update cu_nucleo set id_componente = ? where id = ?";
			PreparedStatement pst = null ;
			pst = db.prepareStatement(update);

			pst.setInt(1, idComponente);
			pst.setInt(2, id);

			pst.executeUpdate();
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}
	
	public void rimuoviComponenti(Connection db, ArrayList<ComponenteNucleoIspettivo> nucleiNuovi) throws SQLException{
		
		ArrayList<Integer> idNucleiInseriti = new ArrayList<Integer>();
		for (int i = 0; i<nucleiNuovi.size();i++){
			ComponenteNucleoIspettivo comp = nucleiNuovi.get(i);
			idNucleiInseriti.add(comp.getId());
		}
		
		String select = "select * from cu_nucleo where id_controllo_ufficiale = ? and enabled";
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setInt(1, idControlloUfficiale);
		rs = pst.executeQuery();
		while (rs.next())
			{
			int id = rs.getInt("id");
			ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo(db, id);
			if (!idNucleiInseriti.contains(comp.getId()))
				comp.disabilita(db);
			}
		
	}
	public void disabilita(Connection db )
	{
		try
		{
			String update = "update cu_nucleo set enabled = false where id = ?";
			PreparedStatement pst = null ;
			pst = db.prepareStatement(update);

			pst.setInt(1, id);
			pst.executeUpdate();
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}

	}
	
	public static ArrayList<ComponenteNucleoIspettivo> buildList(Connection db, int idControllo )
	{
		ArrayList<ComponenteNucleoIspettivo> nuclei = new ArrayList<ComponenteNucleoIspettivo>();
		try
		{
			String select = "select * from cu_nucleo where id_controllo_ufficiale = ? and enabled order by id asc";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, idControllo);
			rs = pst.executeQuery();
			while (rs.next()){
				ComponenteNucleoIspettivo componente = new ComponenteNucleoIspettivo(db, rs);
				nuclei.add(componente);
			}
	}
		catch(SQLException e)
		{	e.printStackTrace();
		}
		return nuclei;
	}
	
}
