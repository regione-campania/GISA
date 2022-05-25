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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SoggettoFisico {

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	 
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDescComuneNascita() {
		return descComuneNascita;
	}
	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Timestamp getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getDescSex() {
		return descSex;
	}
	public void setDescSex(String descSex) {
		this.descSex = descSex;
	}
	public void setIndirizzi(List<Indirizzo> inds){this.indirizzi = inds;}
	public List<Indirizzo> getIndirizzi() {return this.indirizzi;}
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	
	
	
	private Integer id;
	private String nome;
	private String cognome;
	private String descComuneNascita;
	private String codiceFiscale;
	private Timestamp dataNascita;
	private String descSex;
	private List<Indirizzo> indirizzi;
	private Timestamp dataInserimento;
	private String telefono;
	private String pec;
	
	
	
	
	public SoggettoFisico(){}
	public SoggettoFisico(ResultSet rs , Connection conn)
	{
		try {setId(rs.getInt("id"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setNome(rs.getString("nome"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setCognome(rs.getString("cognome"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setDescComuneNascita(rs.getString("comune_nascita"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setCodiceFiscale(rs.getString("codice_fiscale"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setDataNascita(rs.getTimestamp("data_nascita"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setDescSex(rs.getString("sesso"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setDataInserimento(rs.getTimestamp("data_inserimento"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setPec(rs.getString("email"));} catch(Exception ex){System.out.println("Campo non presente");}
		try {setTelefono(rs.getString("telefono"));} catch(Exception ex){System.out.println("Campo non presente");}
		/*provo a risolvere indirizzi */
		try{
			
			List<Indirizzo> indirizzi = Indirizzo.getIndirizziSoggettoFisico(getId(), conn);
			setIndirizzi(indirizzi);
			
		} catch(Exception ex) {ex.printStackTrace(); }
		
	}
	
	
	
	
	
	/*ritorna tutti soggetti fisici */
	public static ArrayList<SoggettoFisico> getAllSoggetti(Connection conn) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<SoggettoFisico> toRet = new ArrayList<SoggettoFisico>();
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_soggetto_fisico()");
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new SoggettoFisico(rs,conn));
			}
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	
	/*ritorna soggetto fisico con un dato oid */
	public static  SoggettoFisico  getByOid(Connection conn, Integer oid) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_soggetto_fisico(?)");
			pst.setInt(1, oid);
			rs = pst.executeQuery();
			
			if(!rs.next())
				return null;
			
			return new SoggettoFisico(rs,conn);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	
	/*ottiene i soggetti data l'impresa l'impresa
	 * anche se in realta' dovrebbe essere sempre e solo uno il sogg per l'impresa
	 */
	public static List<SoggettoFisico> getSoggettiDaImpresa(int idImpresa,   Connection conn) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<SoggettoFisico> toRet = new ArrayList<SoggettoFisico>();
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_rel_impresa_soggetto_fisico(?)");
			pst.setInt(1, idImpresa);
			rs = pst.executeQuery();
			while(rs.next())
			{
				int idSogg = rs.getInt("id_soggetto_fisico");
				SoggettoFisico sogg = SoggettoFisico.getByOid(conn, idSogg);
				toRet.add(sogg);
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		return toRet;
	}
	
	
	/*ritorna i soggetti  
	 * ricercati usando nome e/o cognome e/o codice fiscale 
	 * alcuni di questi campi in input possono essere null
	 */
	public static ArrayList<SoggettoFisico> getByDatiAnag(String nome, String cognome, String cf, Connection conn) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<SoggettoFisico> toRet = new ArrayList<SoggettoFisico>();
		
		/*i campi in input possono essere null, ma in caso in cui siano vuoti li setto a null per 
		 * correttezza di funzionamento della query
		 */
		nome =  nome != null && nome.trim().length( ) > 0 ? nome : null;
		cognome =  cognome != null && cognome.trim().length( ) > 0 ? cognome : null;
		cf =  cf != null && cf.trim().length( ) > 0 ? cf : null;
		
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_soggetto_fisico(NULL,NULL,?,?,NULL,?)");
			Utilities.setNullable(java.sql.Types.VARCHAR, 1, cognome, pst); 
			Utilities.setNullable(java.sql.Types.VARCHAR, 2, nome, pst); 
			Utilities.setNullable(java.sql.Types.VARCHAR, 3, cf, pst);
			
			rs = pst.executeQuery();
			 
			while(rs.next())
			{
				toRet.add(new SoggettoFisico(rs,conn));
			}
			 
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		return toRet;
		
		
	}
	
	
	/*controlla se esiste gia' un soggetto fisico e in caso lo ritorna, altrimenti ritorna null
	 * 
	 * L'esistenza di un soggetto fisico e' data tramite il match col codice fiscale
	 */
	public static SoggettoFisico getIfAlreadyExists(String cf, Connection db) throws Exception
	{
		
		SoggettoFisico toRet = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			 pst = db.prepareStatement("select * from public_functions.anagrafica_cerca_soggetto_fisico(NULL,NULL,NULL,NULL,NULL,?)");
			 pst.setString(1, cf);
			 rs = pst.executeQuery();
			 if(rs.next()) /*abbiamo trovato un match */
			 {
				 Integer idFound = rs.getInt(1);
				 toRet = SoggettoFisico.getByOid(db, idFound);
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
	
		return toRet;
		
	}
	
	 
	
}
