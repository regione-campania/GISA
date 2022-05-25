/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.admin.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class UserOperation extends GenericBean {
	private int id;
	private int user_id;
	private String username;
	private String ip;
	private String url;
	private String parameter;
	private Timestamp data;
	private Boolean automatico = false;
	private String userBrowser;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public Boolean getAutomatico() {
		return automatico;
	}
	public void setAutomatico(Boolean automatico) {
		this.automatico = automatico;
	}
	public String getUserBrowser() {
		return userBrowser;
	}
	public void setUserBrowser(String userBrowser) {
		this.userBrowser = userBrowser;
	}
	     
	public void insert(Connection db, Boolean automatico, String suff) throws Exception{
		if (suff==null || (suff!=null && suff.equals("null"))){
			suff="";
		} 
		   
		String insertStoricoOperazioniUtenti = "INSERT INTO gisa"+suff+"_storico_operazioni_utenti(user_id, username, ip, data, path, parametri, automatico,browser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 
			
			PreparedStatement pst = db.prepareStatement(insertStoricoOperazioniUtenti);
			pst.setInt(1, this.getUser_id());
			pst.setString(2, this.getUsername());
			pst.setString(3, this.getIp());
			pst.setTimestamp(4, this.getData());
			pst.setString(5, this.getUrl());
			pst.setString(6, this.getParameter());
			pst.setBoolean(7, automatico);
			pst.setString(8, this.getUserBrowser());
			pst.executeUpdate(); 
			pst.close();
	}	
	
	public void buildRecord(ResultSet rs , UserOperation o) {
		try {
			o.setId(rs.getInt("id"));
			o.setUser_id(rs.getInt("user_id"));
			o.setUsername(rs.getString("username"));
			o.setIp(rs.getString("ip"));
			o.setData(rs.getTimestamp("data"));
			o.setUrl(rs.getString("path"));
			o.setAutomatico(rs.getBoolean("automatico"));
			o.setParameter(rs.getString("parametri"));	
			o.setUserBrowser(rs.getString("browser"));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void buildRecord(String idOp,Connection db,String suff) {
		try {
			String sql = "select * from gisa"+suff+"_storico_operazioni_utenti_view where id="+idOp;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				this.buildRecord(rs, this);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
