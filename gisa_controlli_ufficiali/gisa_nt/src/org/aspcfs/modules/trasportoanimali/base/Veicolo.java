/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.trasportoanimali.base;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import oracle.net.TNSAddress.Description;

import org.aspcfs.utils.DatabaseUtils;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;
import com.daffodilwoods.daffodildb.server.sql99.expression.expressionprimary.result;

public class Veicolo implements Serializable {

	private boolean accepted;
	public boolean getAccepted(){
		return this.accepted;
	}
	public void setAccepted(boolean accepted){
		this.accepted = accepted;
	}
	
	private String descrizione;
	private String targa;
	
	public String getDescrizione() {
		return descrizione;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	private  boolean lunghi_viaggi;
	
	public void setLunghi_Viaggi(boolean valore){
		this.lunghi_viaggi = valore;
	}
	public boolean getLunghi_Viaggi(){
		return this.lunghi_viaggi;
	}
	
	
	
	
	
	
	
	
	
public static void deleteVeicolo(String targa,int org_id,Connection db){
		
		try{
			String del="update organization_autoveicoli set elimina=? where targa=?";
			Date d=new Date(System.currentTimeMillis());
			java.sql.Date dsql=new java.sql.Date(d.getTime());
			java.sql.PreparedStatement pst=db.prepareStatement(del);
			pst.setDate(1, dsql);
			
			pst.setString(2, targa);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static Map<String, Veicolo> getPresidentsByUniqueIds(int orgid,Connection db){
		Map<String, Veicolo> m=new HashMap<String, Veicolo>();
		
		
		try{
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement("select * from organization_autoveicoli where org_id=?");
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				int org_id = rs.getInt("org_id"); 
				String descrizione=rs.getString("descrizione");
				String targa=rs.getString("targa");
				boolean lunghi_viaggi = rs.getBoolean("lunghi_viaggi");
				boolean accepted = rs.getBoolean("accepted");
								
				Veicolo dist=new Veicolo(org_id,descrizione, targa, lunghi_viaggi,accepted);
				m.put(targa, dist);
				
			}
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return m;
	}
	
	
	
	public void updateVeicoli(Connection db, int org_id){
		
		try{
						
String sql="update organization_autoveicoli set descrizione=? , targa=?,lunghi_viaggi=?, accepted=?,elimina = null where targa=? and org_id=?";
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, descrizione);
				pst.setString(2, targa);
				pst.setBoolean(3, lunghi_viaggi);
				pst.setBoolean(4, accepted);
				pst.setString(5, targa);
				pst.setInt(6, org_id);
			
				pst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	public boolean checkIfExist(Connection db,String matricola,int org_id) {
		
		try{
			
			String sql="select * from organization_autoveicoli where targa=? and org_id=?";
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, matricola);
				pst.setInt(2, org_id);
				ResultSet rs=pst.executeQuery();
				
				if(rs.next()){
					return true;
				}
			
		}catch(Exception e){
		
			e.printStackTrace();
			
		}
		return false;
		
	}
	
	
	
	
	
	private int org_id=-1;
	
	
	
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public Veicolo (int org_id, String des , String targa, boolean lunghi_viaggi,boolean accepted){
	
		this.setOrg_id(org_id);
		this.setDescrizione(des);
		this.setTarga(targa);
		this.setLunghi_Viaggi(lunghi_viaggi);
		this.setAccepted(accepted);
				
	}
	
	public Veicolo (int org_id, String des , String targa){
		
		this.setOrg_id(org_id);
		this.setDescrizione(des);
		this.setTarga(targa);
				
	}
public void update(Connection db,int org_id ) {
		
		try{
		
			String sql="UPDATE  organization_autoveicoli SET DESCRIZIONE=?, lunghi_viaggi = ?, accepted=? WHERE  TARGA=?";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			
			pst.setString(1,descrizione);
			pst.setBoolean(2, lunghi_viaggi);
			pst.setBoolean(3, accepted);
			pst.setString(4,targa );
			
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	

	
	public void insert(Connection db,int org_id ) {
		
		try{
		
			String sql="INSERT INTO organization_autoveicoli (org_id,descrizione,targa,lunghi_viaggi,accepted) VALUES "+
			" (?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			pst.setInt(1, org_id);	
			pst.setString(2, descrizione);
			pst.setString(3, targa);
			pst.setBoolean(4, lunghi_viaggi);
			pst.setBoolean(5, accepted);
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	
}
