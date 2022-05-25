/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.accounts.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;



public class Provincia implements Serializable{
	
	private int codice ;
	private String descrizione ;
	
	  private String value = null; 	
	  private String label = null;
	  private int idProvincia = -1;
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = new Integer(idProvincia);
	}
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public ArrayList<Provincia> getProvince (Connection db ,String nomeStart,String inRegione,String estero)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select code,description from lookup_province where 1=1 and description ilike ?  ";
		
		if(estero!=null && estero.equalsIgnoreCase("no"))
		{
			sql += " and cod_nazione is not null " ;
		}
		
		
		if(inRegione!=null && inRegione.equalsIgnoreCase("si"))
		{
			sql += " and id_regione = "+ComuniAnagrafica.CODICE_REGIONE_CAMPANIA ;
		} else if(inRegione!=null && inRegione.equalsIgnoreCase("no")) {
			sql += " and id_regione <> " + ComuniAnagrafica.CODICE_REGIONE_CAMPANIA;
		}
		sql += " limit 30" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, "%"+nomeStart+"%");
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				int codice = rs.getInt(1);
				String descrizione = rs.getString("description");
				Provincia p = new Provincia();
				p.setCodice(codice);
				p.setDescrizione(descrizione);
				listaProvince.add(p);
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listaProvince ;
		
		
	}
	
	
	public Provincia getProvinciaAsl (Connection db ,int idAsl)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select p.code as codice_provincia, p.description from lookup_province p left join lookup_asl_rif a on (p.code = a.id_provincia) where a.code = ?  ";
	//	Provincia p = null;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				int codice = rs.getInt("codice_provincia");
				String descrizione = rs.getString("description");
				
				this.setCodice(codice);
				this.setDescrizione(descrizione);
				//listaProvince.add(p);
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return this ;
		
		
	}
	
	//Funzione per popolamento aree di testo, per ora usate in registrazione di modifica residenza prop/det
	
	public ArrayList<Provincia> getProvincePerCampoTesto (Connection db ,String nomeStart,int idRegione)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select code,description from lookup_province where 1=1 and description ilike ? and id_regione = ?  ";

		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, "%"+nomeStart+"%");
			pst.setInt(2, idRegione);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
			      value = rs.getString(2);
			      label = rs.getString(2); //Si pu� modificare
			      idProvincia = rs.getInt(1);
				
				String descrizione = rs.getString("description");
				Provincia p = new Provincia();
				p.setValue(value);
				p.setLabel(label);
				p.setIdProvincia(idProvincia);
				listaProvince.add(p);
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listaProvince ;
		
		
	}
	
	public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0 ; i <campi.length; i++)
		{
			String nomeCampo = campi[i].getName();
			
			for (int j=0; j<metodi.length; j++ )
			{
				if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
				{
					
					map.put(nomeCampo, metodi[j].invoke(this));
				}
				
			}
			
		}
		
		return map ;
		
	}
	
	
	public  ArrayList<Provincia> getProvinceByIdRegione(Connection db, int idRegione){
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		String query = "select code, description from lookup_province where id_regione = ?";
		try{
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, idRegione);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				
				int codice = rs.getInt("code");
				String descrizione = rs.getString("description");
				Provincia p = new Provincia();
				p.setCodice(codice);
				p.setDescrizione(descrizione);
				
				listaProvince.add(p);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			
		}
		return listaProvince;
	}

}
