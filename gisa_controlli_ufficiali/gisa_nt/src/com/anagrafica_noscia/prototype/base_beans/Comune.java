/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.base_beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Comune {

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodeComune() {
		return codeComune;
	}
	public void setCodeComune(String codeComune) {
		this.codeComune = codeComune;
	}
	public String getCodRegione() {
		return codRegione;
	}
	public void setCodRegione(String codRegione) {
		this.codRegione = codRegione;
	}
	public String getCodProvincia() {
		return codProvincia;
	}
	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIstat() {
		return istat;
	}
	public void setIstat(String istat) {
		this.istat = istat;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	private Integer id;
	private String codeComune;
	private String codRegione;
	private String codProvincia;
	private String nome;
	private String istat;
	private String cap;
	
	
	
	public Comune() { }
	
	public Comune(ResultSet rs)
	{
		try{setId(rs.getInt("id"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setCodeComune(rs.getString("cod_comune"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setCodRegione(rs.getString("cod_regione"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setCodProvincia(rs.getString("cod_provincia"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setNome(rs.getString("nome").toUpperCase());}catch(Exception ex){System.out.println("Campo non presente");}
		try{setIstat(rs.getString("istat"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setCap(rs.getString("cap"));}catch(Exception ex){System.out.println("Campo non presente");}
	}
	
	
	
	public static ArrayList<Comune> getAllComuni(Connection cn) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Comune> toRet = new ArrayList<Comune>();
		
		try
		{
			pst = cn.prepareStatement("select * from comuni1");
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new Comune(rs));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
		return toRet;
	}
	
	
	
	public static ArrayList<Comune> getAllComuniCampani(Connection cn) throws Exception
	{
		ArrayList<Comune> allComuni = getAllComuni (cn);
		ArrayList<Comune> campani = new ArrayList<Comune>();
		for(Comune com : allComuni)
		{
			if(com.getCodRegione().equals("15")) /*regione campania */
			{
				campani.add(com);
			}
		}
		
		return campani;
	}
	
	
	
	public static  Comune getById(Connection cn, Integer id) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		 
		
		try
		{
			pst = cn.prepareStatement("select * from comuni1 where id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next( )== false)
				return null;
			
			return new Comune(rs);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	 
	}
	
	public static  Comune getByDesc(Connection cn, String descComune) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		 
		
		try
		{
			pst = cn.prepareStatement("select * from comuni1 where nome ilike ?");
			pst.setString(1, descComune);
			rs = pst.executeQuery();
			if(rs.next( )== false)
				return null;
			
			return new Comune(rs);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	 
	}
	
	/*ritorna [id asl, desc asl] */
	public static Object[] getIdDescAslFromIDComune(Connection conn, Integer idCom) 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Object[] toRet = null;
		
		try
		{
			pst = conn.prepareStatement("select asl.description, asl.code from lookup_site_id asl join comuni1 com on com.codiceistatasl = asl.code::text "
					+ " where com.id = ?");
			pst.setInt(1, idCom);
			rs = pst.executeQuery();
			rs.next();
			Integer codeAsl = rs.getInt("code");
			String descAsl = rs.getString("description");
			toRet = new Object[]{codeAsl,descAsl};
		}
		catch(Exception ex)
		{
			 System.out.println("Per il comune passato, non e' stato possibile individuare asl campana. Assegno fuori regione");
			 return new Object[]{16,"fuori regione"};
			
			
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
		
		return toRet;
	}
}
