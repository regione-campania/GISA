/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.exolab.castor.jdo.engine.SQLTypes;

import in.co.daffodil.db.jdbc.SQLType;


/*MODIFICA : ora il gestore ed i suoi dati anagrafici sono separati nel modello dati e quindi nel bean
 * poiche' la relazione puo' essere 1:n
 */
public class AnagraficaGestore {
	
	
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Integer getIdComune() {
		return idComune;
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}
	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}
	public Integer getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(Integer idAsl) {
		this.idAsl = idAsl;
	}
	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}
	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdGestore() {
		return idGestore;
	}
	public void setIdGestore(Integer idGestore) {
		this.idGestore = idGestore;
	}
	public void setNominativoRappLegale(String nominativoRappLegale) {
		this.nominativoRappLegale = nominativoRappLegale;
		
	}
	public String getNominativoRappLegale()
	{
		return this.nominativoRappLegale;
	}
	private Integer id;
	private Integer idGestore;
	private String indirizzo;
	private Integer idComune;
	private String provincia;
	private String telefono;
	private String nominativoRappLegale;
	private String domicilioDigitale;
	private Integer idAsl;
	private String descrizioneAsl;
	private String descrizioneComune;
	
	
	public AnagraficaGestore(){}
	
	
	public static AnagraficaGestore build(ResultSet rs, Connection conn)
	{
		AnagraficaGestore toRet = new AnagraficaGestore();
		
		try{ toRet.setIndirizzo(rs.getString("indirizzo")); }catch(Exception ex){}
		try{toRet.setIdComune(rs.getInt("idcomune"));}catch(Exception ex){}
		try{toRet.setId(rs.getInt("id"));}catch(Exception ex){}
		try{toRet.setIdGestore(rs.getInt("id_gestore"));}catch(Exception ex){}
		try{toRet.setIdAsl(rs.getInt("idasl"));}catch(Exception ex){}
		try{toRet.setProvincia(rs.getString("provincia"));}catch(Exception ex){}
		try{toRet.setTelefono(rs.getString("telefono"));}catch(Exception ex){}
		try{toRet.setNominativoRappLegale(rs.getString("nominativo_rapplegale"));}catch(Exception ex){}
		try{toRet.setDomicilioDigitale(rs.getString("domicilio_digitale"));}catch(Exception ex){}
		try
		{
			String descAsl = ControlloInterno.getDescFromLookupCode(conn, "lookup_site_id", "code", "description", toRet.getIdAsl(),"enabled = true");
			toRet.setDescrizioneAsl(descAsl);
		} catch(Exception ex){}
		
		try {
			String descComune = ControlloInterno.getDescFromLookupCode(conn, "comuni1", "id", "nome", toRet.getIdComune(), "");
			toRet.setDescrizioneComune(descComune);
		}
		  catch(Exception ex){}
		return toRet;
		 
	}
	
	public static ArrayList<AnagraficaGestore> getAnagraficheDiGestore(Integer idGestore, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<AnagraficaGestore> anags = new ArrayList<AnagraficaGestore>();
		try
		{
			pst = conn.prepareStatement("select * from gestori_acque_anag where id_gestore = ?");
			pst.setInt(1,idGestore);
			rs = pst.executeQuery();
			while(rs.next())
			{
				anags.add(AnagraficaGestore.build(rs, conn));
			}
			return anags;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{pst.close();}catch(Exception ex){}
		}
	}
	
	public void insert(Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("insert into gestori_acque_anag(id_gestore,idasl,idcomune,provincia,telefono,domicilio_digitale,indirizzo) values(?,?,?,?,?,?,?) returning id");
			
			
			
			 
			 int u = 0;
			 pst.setInt(++u,getIdGestore());
			 
			if(getIdAsl() != null)
			{
				pst.setInt(++u, getIdAsl());
			}
			else 
			{
				pst.setNull(++u, java.sql.Types.INTEGER);
			}
			
			if(getIdComune() != null)
			{
				pst.setInt(++u,getIdComune());
			}
			else
			{
				pst.setInt(++u, java.sql.Types.INTEGER);
			}
			
			pst.setString(++u, getProvincia());
			pst.setString(++u, getTelefono()); 
			pst.setString(++u, getDomicilioDigitale());
			pst.setString(++u, getIndirizzo());  
			
			pst.execute();
			rs = pst.getResultSet();
			rs.next();
			setId(rs.getInt(1));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
	}
	
	
}
