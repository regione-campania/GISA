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
	String codProvincia = null;
	
	  private String value = null; 	
	  private String label = null;
	  private int idProvincia = -1;
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCodProvincia() {
		return codProvincia;
	}
	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
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
	
	
	public  Provincia getProvinciaBySigla(Connection db, String sigla){
		Provincia p = null;
		String query = "select * from lookup_province where cod_provincia = ? or description like ?";
		try
		{
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, sigla);
			pst.setString(2, sigla);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next())
			{
				p = new Provincia();
				int codice = rs.getInt("code");
				String descrizione = rs.getString("description");
				p.setCodice(codice);
				p.setDescrizione(descrizione);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}finally{
			
		}
		return p;
	}
	
	public  Provincia getProvinciaByComune(Connection db, String comune)
	{
		Provincia p = null;
		String query = "select p.* from lookup_province p , comuni1 c where upper(c.nome) = upper(?) and c.cod_provincia::integer = p.code and p.enabled is true ";
		try
		{
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, comune);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next())
			{
				p = new Provincia();
				int codice = rs.getInt("code");
				String descrizione = rs.getString("description");
				String codProvincia = rs.getString("cod_provincia");
				p.setCodice(codice);
				p.setDescrizione(descrizione);
				p.setCodProvincia(codProvincia);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}finally{
			
		}
		return p;
	}
	

	//Funzione per popolamento aree di testo, per ora usate in registrazione di modifica residenza prop/det
	
	public ArrayList<Provincia> getProvincePerCampoTesto (Connection db ,String nomeStart,int idRegione)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select code,description,cod_provincia from lookup_province where 1=1 and description ilike ? and (? = -1 or id_regione = ? )  ";

		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, "%"+nomeStart+"%");
			pst.setInt(2, idRegione);
			pst.setInt(3, idRegione);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
			      value = rs.getString(2);
			      label = rs.getString(2); //Si puo modificare
			      idProvincia = rs.getInt(1);
			      codProvincia = rs.getString(3);
				
				String descrizione = rs.getString("description");
				Provincia p = new Provincia();
				p.setValue(value);
				p.setLabel(label);
				p.setIdProvincia(idProvincia);
				p.setCodProvincia(codProvincia);
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
		
		String sql = "select p.code as codice_provincia, p.description from lookup_province p  join lookup_site_id a on (p.code = a.id_provincia) where a.code = ?  ";
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
	
	
	
	public Integer getId(Connection db ,String codProvincia)
	{
		Integer id = null;
		
		String sql = "select p.code from lookup_province p where p.cod_provincia = ?  ";
		
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, codProvincia);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				id = rs.getInt("code");
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return id;
	}
	
	public ArrayList<Provincia> getProvince (Connection db ,String nomeStart,String inRegione,String cod_nazione)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select code,description from lookup_province where 1=1  and description ilike ?  ";
		if (cod_nazione!=null && !"undefined".equals(cod_nazione))
			sql +="and cod_nazione="+cod_nazione+" ";
		if(inRegione!=null && inRegione.equalsIgnoreCase("si"))
		{
			sql += " and id_regione = "+ComuniAnagrafica.CODICE_REGIONE_CAMPANIA ;
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
				p.setIdProvincia(codice);
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
	
	public ArrayList<Provincia> getProvince (Connection db ,String nomeStart,String inRegione,String cod_nazione,int idAsl)
	{
		ArrayList<Provincia> listaProvince =new ArrayList<Provincia>();
		
		String sql = "select distinct code,description  from lookup_province lp left join comuni1 c on c.cod_provincia::int = lp.code  where 1=1  and description ilike ?  ";
		if(idAsl>0)
		{
			sql+=" and codiceistatasl::int="+idAsl+" ";
		}
		if (cod_nazione!=null && !"undefined".equals(cod_nazione))
			sql +="and lp.cod_nazione="+cod_nazione+" ";
		if(inRegione!=null && inRegione.equalsIgnoreCase("si"))
		{
			sql += " and id_regione = "+ComuniAnagrafica.CODICE_REGIONE_CAMPANIA ;
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
				p.setIdProvincia(codice);
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

}