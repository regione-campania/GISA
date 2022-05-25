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


public class Personale implements Serializable {

	private String nome;
	private String cognome;
	private String cf;
	private String mansione;
	private int org_id=-1;
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCf() {
		return cf;
	}
	public String getMansione() {
		return mansione;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public void setMansione(String mansione) {
		this.mansione = mansione;
	}
	
	public static void deletePersona(String cf,int org_id,Connection db){
		
		try{
			String del="delete from  organization_personale where cf=? and org_id="+org_id;
			
			java.sql.PreparedStatement pst=db.prepareStatement(del);
			
			
			pst.setString(1, cf);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static Map<String, Personale> getPresidentsByUniqueIds(int orgid,Connection db){
		Map<String, Personale> m=new HashMap<String, Personale>();
		
		
		try{
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement("select * from organization_personale where org_id=?");
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				String cf=rs.getString("cf");
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				String mansione=rs.getString("mansione");
								
				Personale dist=new Personale(cf,nome, cognome,mansione);
				m.put(cf, dist);
				
			}
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return m;
	}
	
	
	
	public void updatePersonale(Connection db, int org_id){
		
		try{
						
String sql="update organization_personale set cf= ?,nome=? , cognome=? , mansione=? where id=? and org_id=?";
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, cf);
				pst.setString(2, nome);
				pst.setString(3, cognome);
				pst.setString(4, mansione);
				pst.setString(5, cf);
				pst.setInt(6, org_id);
			
				pst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	public boolean checkIfExist(Connection db,String cf,int org_id) {
		
		try{
			
			String sql="select * from organization_personale where cf=? and org_id=?";
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, cf);
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
	
	
	
	
	
	
	
	
	
	public Personale (String cf,String nome , String cognome, String mansione){
	
		this.setCf(cf);
		this.setNome(nome);
		this.setCognome(cognome);
		this.setMansione(mansione);
				
	}
	
	
public void update(Connection db,int org_id ) {
		
		try{
		
			String sql="UPDATE  organization_personale SET NOME=?, COGNOME=?, MANSONE = ? WHERE  cf=?";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			
			pst.setString(1,nome);
			pst.setString(2,cognome );
			pst.setString(3,mansione);
			pst.setString(4,cf );
			
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	

	
	public boolean insert(Connection db,int org_id ) {
		
		try{
		
			
	String verifica="select * from organization_personale where cf=? and org_id=?";
			String sql="INSERT INTO organization_personale (cf,nome,cognome, mansione,org_id) VALUES "+
			" (?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			java.sql.PreparedStatement pst2= db.prepareStatement(verifica);
			pst2.setString(1, cf);
			pst2.setInt(2, org_id);
			ResultSet rs2=pst2.executeQuery();
			if(!rs2.next()){		
			pst.setString(1, cf);
			pst.setString(2, nome);
			pst.setString(3, cognome);
			pst.setString(4, mansione);
			pst.setInt(5, org_id);
			
			
			pst.execute();
		return true;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		return false;
		
	}
	
}
