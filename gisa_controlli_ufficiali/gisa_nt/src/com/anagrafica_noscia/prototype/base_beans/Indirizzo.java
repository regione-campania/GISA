/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.base_beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Indirizzo {

	public static final int INDIRIZZO_STAB = 0;
	public static final int INDIRIZZO_SOGGFIS = 1;
	public static final int INDIRIZZO_IMPRESA = 2;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescVia() {
		return descVia;
	}
	public void setDescVia(String descVia) {
		this.descVia = descVia;
	}
	public Integer getIdToponimo() {
		return idToponimo;
	}
	public void setIdToponimo(Integer idToponimo) {
		this.idToponimo = idToponimo;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Integer getIdComune() {
		return idComune;
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public void setNazione(String naz){this.nazione = naz;}
	public String getNazione(){return this.nazione;}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getDescToponimo()
	{
		return this.descToponimo;
	}
	public void setDescToponimo(String descToponimo) {

		this.descToponimo = descToponimo;
		
	}
	private Integer id;
	private String descVia;
	private Integer idToponimo;
	private String descToponimo;
	private String civico;
	private String cap;
	private Integer idComune;
	private String descComune;

	private String nazione;
	private Double lat;
	private Double lon;
	private Timestamp dataInserimento;
	
	
	
	public Indirizzo(){}
	public Indirizzo(ResultSet rs, Connection conn){
		
		try{setId(rs.getInt("id"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setDescVia(rs.getString("via"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setIdToponimo(rs.getInt("toponimo"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{
			String descToponimo = LookupPair.buildByCode(conn, "lookup_toponimi", "code", "description", getIdToponimo(), "enabled=true").getDesc();
			setDescToponimo(descToponimo);} catch(Exception ex){System.out.println("Campo non presente");}
		try{setCivico(rs.getString("civico"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setCap(rs.getString("cap"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setIdComune(rs.getInt("comune"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setLat(rs.getDouble("latitudine"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setLon(rs.getDouble("longitudine"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setNazione(rs.getString("nazione")); } catch(Exception ex){System.out.println("Campo non presente");}
		try{ /*provo a risolvere desc comune da id */
			String descComune = Comune.getById(conn,getIdComune()).getNome();
			setDescComune(descComune);
		}
		catch(Exception ex)
		{
			
		}
		try{
			setDataInserimento(rs.getTimestamp("data_inserimento"));
		}catch(Exception ex){System.out.println("Campo non presente");}
	}
	
	
	
	
	/*Ritrova INDIRIZZO DATO OID*/
	public static Indirizzo getByOid(Connection conn,Integer id) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		Indirizzo toRet = null;
		
		try
		{
			
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_opu_indirizzo(?) ");
			pst.setInt(1,id);
			pst.execute();
			rs = pst.getResultSet();
			
			if(!rs.next())
				return null;
			
			toRet = new Indirizzo(rs,conn);
			return toRet;
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	}
	
 
 
	
	/*metodo di servizio usato per ottenere gli indirizzi dalla cross table per stabilimento/impresa/sogg fisico */
	private static ArrayList<Indirizzo> getIndirizziEntita(Integer tipoEntita, Integer idEntita, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Indirizzo> inds = new ArrayList<Indirizzo>();
		
		try
		{
			String nomeDbi = null;
			switch(tipoEntita)
			{
				case INDIRIZZO_IMPRESA: nomeDbi = "public_functions.anagrafica_cerca_rel_impresa_opu_indirizzo"; break;
				case INDIRIZZO_SOGGFIS : nomeDbi = "public_functions.anagrafica_cerca_rel_soggetto_fisico_opu_indirizzo"; break;
				case INDIRIZZO_STAB : nomeDbi = "public_functions.anagrafica_cerca_rel_stabilimento_opu_indirizzo"; break;
				default : throw new Exception("TIPO ENTITA RICERCATO PER INDIRIZZO NON RICONOSCIUTO COME VALIDO");
			}
			pst = conn.prepareStatement("select * from "+nomeDbi+"(?)");
			pst.setInt(1, idEntita);
			rs = pst.executeQuery();
			while(rs.next())
			{
				int idFoundInd = rs.getInt("id_indirizzo");
				inds.add(Indirizzo.getByOid(conn, idFoundInd));
			}
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
		
		return inds;
	}
	
	
	
	
	/*da la lista di indirizzi per un dato stabilimento*/
	public static List<Indirizzo> getIndirizziStabilimento(Integer idStab, Connection conn) throws Exception
	{
		 return getIndirizziEntita(INDIRIZZO_STAB,idStab,conn);
	}
	
	/*da la lista indirizzi per un dato sogg fisico */
	public static List<Indirizzo> getIndirizziSoggettoFisico(Integer idSogg, Connection conn) throws Exception
	{
		 return getIndirizziEntita(INDIRIZZO_SOGGFIS,idSogg,conn);
	}
	
	/*da la lista indirizzi per una data impresa */
	public static List<Indirizzo> getIndirizziImpresa(Integer idImpr, Connection conn) throws Exception
	{
		return getIndirizziEntita(INDIRIZZO_IMPRESA,idImpr,conn);
	}
	
	
	
	
}
