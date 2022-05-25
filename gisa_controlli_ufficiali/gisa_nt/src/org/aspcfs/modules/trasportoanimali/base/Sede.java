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

import org.aspcfs.utils.DatabaseUtils;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;
import com.daffodilwoods.daffodildb.server.sql99.expression.expressionprimary.result;


public class Sede implements Serializable {

	private String comune;
	private String indirizzo;
	private String cap;
	private String provincia;
	private String latitudine;
	private String longitudine;
	private String stato;
	private int id;
	
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}




	
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	
	public static Map<String, Sede> getPresidentsByUniqueIds(int orgid,Connection db){
		Map<String, Sede> m=new HashMap<String, Sede>();
		
		
		try{
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement("select * from organization_sediveicoli where org_id=?");
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				
				String comune=rs.getString("comune");
				String indirizzo=rs.getString("indirizzo");
				String provincia=rs.getString("provincia");
				String latitudine=rs.getString("latitudine");
				String longitudine=rs.getString("longitudine");
				String cap=rs.getString("cap");
				String stato=rs.getString("stato");
				
				Integer id=rs.getInt("id");
				
				Sede dist=new Sede(comune,indirizzo,provincia,cap,stato);
				m.put(id.toString(), dist);
				
			}
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return m;
	}
	
	private int org_id=-1;
	
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public static void deleteSede(int id,int org_id,Connection db){
		
		try{
			String del="update organization_sediveicoli set elimina=? where id=?";
			Date d=new Date(System.currentTimeMillis());
			java.sql.Date dsql=new java.sql.Date(d.getTime());
			java.sql.PreparedStatement pst=db.prepareStatement(del);
			pst.setDate(1, dsql);
			
			pst.setInt(2, id);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void updateSede(Connection db, int org_id){
		
		try{
			
			
String sql="update organization_sediveicoli set id=?, comune=?, provincia=?,indirizzo=?, cap=?, latitudine=?, longitudine=? , stato=? where id=? and org_id=?";
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
			pst.setInt(1, id);
				pst.setString(2, comune);
				pst.setString(3, provincia);
				pst.setString(4, indirizzo);
				pst.setString(5, cap);
				pst.setString(6, latitudine);
				pst.setString(7, longitudine);
				pst.setString(8, stato);
				pst.setInt(9, id);
				pst.setInt(10, org_id);
			
				pst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	public boolean checkIfExist(Connection db,int id,int org_id) {
		
		try{
			
			String sql="select * from organization_sediveicoli where id=? and org_id=?";
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setInt(1, id);
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
	
	
	
	
	
	
	
	
	private String elimina="";
	public String getElimina() {
		return elimina;
	}
	public void setElimina(String elimina) {
		this.elimina = elimina;
	}
	public Sede (String comune,String indirizzo,String provincia,String cap,String stato){
		//this.setId(id);
		this.setComune(comune);
		this.setProvincia(provincia);
		this.setIndirizzo(indirizzo);
		
		this.setCap(cap);
		this.setStato(stato);
		
		
		
		
		
	}
	
	
public void update(Connection db,int org_id ) {
		
		try{
		
			String sql="UPDATE  organization_sediveicoli SET ID=?,COMUNE=?,PROVINCIA=?,INDIRIZZO=?,CAP=?,LATITUDINE=?,LONGITUDINE=?,STATO=? WHERE  ID=?";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, comune);
			pst.setString(3,provincia );
			pst.setString(4, indirizzo);
			pst.setString(5, cap);
			pst.setString(6, latitudine);
			pst.setString(7, longitudine);
			pst.setString(8, stato);
			pst.setInt(9, org_id);	
			pst.setInt(10, id);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	

	
	public void insert(Connection db,int org_id ) {
		
		try{
		
			String sql="INSERT INTO organization_sediveicoli (ID,COMUNE,INDIRIZZO,PROVINCIA,CAP,STATO,ORG_ID) VALUES "+
			" (?,?,?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			
			id=DatabaseUtils.getNextSeqTipo(db, "organization_sedi_id_seq");
			pst.setInt(1, id);
			pst.setString(2, comune);
			pst.setString(3, indirizzo);
			pst.setString(4,provincia );
			pst.setString(5, cap);
			pst.setString(6, stato);
			pst.setInt(7, org_id);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	
}
