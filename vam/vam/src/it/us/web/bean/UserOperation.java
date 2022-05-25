/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserOperation {
	private int id;
	private int user_id;
	private String username;
	private String ip;
	private String url;
	private String parameter;
	private Timestamp data;
	private Boolean automatico = false;
	private String userBrowser;
	private String action;
	
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
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public void insert(Connection db, Boolean automatico) throws Exception{
		String insertStoricoOperazioniUtenti = "INSERT INTO vam_storico_operazioni_utenti(user_id, username, ip, data, path, parametri, automatico,browser,action) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)"; 
			
			PreparedStatement pst = db.prepareStatement(insertStoricoOperazioniUtenti);
			pst.setInt(1, this.getUser_id());
			pst.setString(2, this.getUsername());
			pst.setString(3, this.getIp());
			pst.setTimestamp(4, this.getData());
			pst.setString(5, this.getUrl());
			pst.setString(6, this.getParameter());
			pst.setBoolean(7, automatico);
			pst.setString(8, this.getUserBrowser());
			pst.setString(9, this.getAction());
			pst.executeUpdate(); 
			pst.close();
	}
	
	private String createFilter (String sql, String username,String dateStart,String dateEnd){
		if (username!=null && !username.equalsIgnoreCase("")){
			sql = sql + " and username ilike ?";
		}
		if (dateStart!=null && !dateStart.equalsIgnoreCase("")){
			sql = sql + " and data::date >= ? ";
		}
		if (dateEnd!=null && !dateEnd.equalsIgnoreCase("")){
			sql = sql + " and data::date <= ? ";
		}
		sql = sql + " order by data desc";
		return sql;
	}
	
	private PreparedStatement prepareFilter (PreparedStatement pst,String username,String dateStart,String dateEnd) throws SQLException, ParseException{
		int i = 0;
		if (username!=null && !username.equalsIgnoreCase("")){
			pst.setString(++i, username);
		}
		if (dateStart!=null && !dateStart.equalsIgnoreCase("")){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(dateStart);
			Timestamp t = new Timestamp(date.getTime());
			pst.setTimestamp(++i, t);
		}
		if (dateEnd!=null && !dateEnd.equalsIgnoreCase("")){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(dateEnd);
			Timestamp t = new Timestamp(date.getTime());
			String time = "23:59:59";
	    	t = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(date).concat(time));
			pst.setTimestamp(++i, t);
		}
		return pst;
	}
	
	public ArrayList<UserOperation> buildList (Connection db, String username, String dateStart, String dateEnd) throws SQLException, ParseException{
		ArrayList<UserOperation> op_list = new ArrayList<UserOperation>();
		String sql = "select id,data,username,path from vam_storico_operazioni_utenti_view where 1=1";
		PreparedStatement pst = null;
		sql = createFilter(sql,username,dateStart,dateEnd);
		pst = db.prepareStatement(sql);
		pst = prepareFilter(pst,username,dateStart,dateEnd);
		
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			UserOperation op = new UserOperation();
			op.setId(rs.getInt("id"));
			op.setData(rs.getTimestamp("data"));
			op.setUsername(rs.getString("username"));
			op.setUrl(rs.getString("path"));
			op_list.add(op);
		}
		return op_list;
	}
	
	
	public UserOperation buildRecord (Connection db, int idOp){
		UserOperation o = new UserOperation();
		String sql = "select * from vam_storico_operazioni_utenti_view where id = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			 pst = db.prepareStatement(sql);
			 pst.setInt(1, idOp);
			 rs = pst.executeQuery();
			 while (rs.next()){
				o.setData(rs.getTimestamp("data"));
				o.setId(rs.getInt("id"));
				o.setIp(rs.getString("ip"));
				o.setParameter(rs.getString("parametri"));
				o.setUrl(rs.getString("path"));
				o.setUser_id(rs.getInt("user_id"));
				o.setUserBrowser(rs.getString("browser"));
				o.setUsername(rs.getString("username"));
				o.setAction(rs.getString("action"));
				o.setAutomatico(rs.getBoolean("automatico"));
			 }
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return o;
	}
	
	
	public static UserOperation lastOperation(int user_id, Connection db) throws Exception
	{
		String selectStoricoOperazioniUtenti = "SELECT * FROM vam_storico_operazioni_utenti_view where user_id = ? order by data desc limit 1 "; 
		UserOperation uo = null;
		
		try
		{
			
			PreparedStatement pst = db.prepareStatement(selectStoricoOperazioniUtenti);
			pst.setInt(1, user_id);
			ResultSet res = pst.executeQuery();
			if(res.next())
			{
				uo = new UserOperation();
				uo.setData(res.getTimestamp("data"));
				uo.setId(res.getInt("id"));
				uo.setIp(res.getString("ip"));
				uo.setParameter(res.getString("parametri"));
				uo.setUrl(res.getString("path"));
				uo.setUser_id(res.getInt("user_id"));
				uo.setUserBrowser(res.getString("browser"));
				uo.setUsername(res.getString("username"));
				uo.setAction(res.getString("action"));
			}
			
			pst.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return uo;
	}
}
