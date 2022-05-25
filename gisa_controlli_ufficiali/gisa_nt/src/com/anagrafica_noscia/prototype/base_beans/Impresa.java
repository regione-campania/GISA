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

public class Impresa {
	
	
	
	public LookupPair getLookupPairTipoImpresaSocieta()
	{
		return this.lookupPairTipoImpresaSocieta;
	}
	public void setLookupPairTipoImpresaSocieta(LookupPair lk)
	{
		this.lookupPairTipoImpresaSocieta = lk;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public Integer getIdTipoImpresaSocieta() {
		return idTipoImpresaSocieta;
	}

	public void setIdTipoImpresaSocieta(Integer idTipoImpresaSocieta) {
		this.idTipoImpresaSocieta = idTipoImpresaSocieta;
	}

	public Timestamp getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	public String getCodFiscale() {
		return codFiscale;
	}
	
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	public void setIndirizzi(List<Indirizzo> inds){this.indirizzi = inds;}
	public List<Indirizzo> getIndirizzi() {return this.indirizzi;}
	
	public void setLegaliRappresentanti(List<SoggettoFisico> legs){this.legaliRappresentanti = legs;}
	public List<SoggettoFisico> getLegaliRappresentanti() {return this.legaliRappresentanti;}
	
	public List<Stabilimento> getStabilimenti() {
		return stabilimenti;
	}
	public void setStabilimenti(List<Stabilimento> stabilimenti) {
		this.stabilimenti = stabilimenti;
	}
	public Timestamp getDataArrivoPec() {
		return dataArrivoPec;
	}
	public void setDataArrivoPec(Timestamp dataArrivoPec) {
		this.dataArrivoPec = dataArrivoPec;
	}
	 
	
	
	private Integer id;
	private String ragioneSociale;
	private String piva;
	private String codFiscale;
	private Integer idTipoImpresaSocieta;
	

	private Timestamp dataInserimento;
	private Timestamp dataScadenza;

	private Timestamp dataArrivoPec;

	private Timestamp dataCancellazione;
	private LookupPair lookupPairTipoImpresaSocieta;
	private List<Indirizzo> indirizzi;
	private List<SoggettoFisico> legaliRappresentanti;
	private List<Stabilimento> stabilimenti;
	
	
	public Impresa(){}
	
	public Impresa(ResultSet rs , Connection db )
	{
		try{setId(rs.getInt("id"));} catch(Exception ex){System.out.println("Campo non presente");}
		try{setRagioneSociale(rs.getString("ragione_sociale"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setPiva(rs.getString("piva"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{ setCodFiscale(rs.getString("codfisc")); } catch(Exception ex){System.out.println("Campo non presente");}
		try{setIdTipoImpresaSocieta(rs.getInt("id_tipo_impresa_societa"));}catch(Exception ex){System.out.println("Campo non presente");}
		try{setDataInserimento(rs.getTimestamp("data_inserimento"));} catch(Exception ex){System.out.println("Campo non presente");}
		try{setDataArrivoPec(rs.getTimestamp("data_arrivo_pec"));} catch(Exception ex){System.out.println("Campo non presente");}
		 
//		try{setDataScadenza(rs.getTimestamp("data_scadenza"));}catch(Exception ex){ex.printStackTrace();}
//		try{setDataCancellazione(rs.getTimestamp("data_cancellazione"));}catch(Exception ex){ex.printStackTrace();}
		
		/*risolvo le lookup col tipo pair */
		try
		{
			LookupPair tipImpresaSocieta = LookupPair.buildByCode(db, "LOOKUP_TIPO_IMPRESA_SOC", "code","description" , rs.getInt("id_tipo_impresa_societa"),null);
			setLookupPairTipoImpresaSocieta(tipImpresaSocieta);
			
		}catch(Exception ex){System.out.println("Campo non presente");}
		
		/*provo a risolvere indirizzi */
		try{
			
			List<Indirizzo> indirizzi = Indirizzo.getIndirizziImpresa(getId(), db);
			setIndirizzi(indirizzi);
			
		} catch(Exception ex) {System.out.println("Campo non presente"); }
		
		/*provo a risolvere i soggetti fisici impresa (legali rapp ) */
		try
		{
			List<SoggettoFisico> legaliRapp = SoggettoFisico.getSoggettiDaImpresa(getId(), db);
			setLegaliRappresentanti(legaliRapp);
		}
		catch(Exception ex){System.out.println("Campo non presente");}
		
		/*provo a risolvere gli stabilimenti associati all'impresa */
		try
		{
			List<Stabilimento> stab = Stabilimento.getStabilimentiDaImpresa(getId(), db);
			setStabilimenti(stab);
			
		}catch(Exception ex){System.out.println("Campo non presente");}
	}
	
	
	
	
	
	public static ArrayList<Impresa> getAllImprese(Connection conn) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Impresa> toRet = new ArrayList<Impresa>();
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_impresa()");
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new Impresa(rs,conn));
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
	
	public static  Impresa  getByOid(Connection conn, Integer oid) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_impresa(?)");
			pst.setInt(1, oid);
			rs = pst.executeQuery();
			
			if(!rs.next())
				return null;
			
			return new Impresa(rs,conn);
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
	
	
	
	
	
	/*ritorna Lista delle imprese associate ad uno stabilimento, 
	 * anche se in realta' teoricamente e' sempre una soltanto l'impresa stabilimento
	 */
	public static ArrayList<Impresa> getImpreseDaStabilimento(Integer idStabilimento, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Impresa> imprese = new ArrayList<Impresa>();
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_rel_impresa_stabilimento(NULL,?,NULL,NULL)");
			pst.setInt(1, idStabilimento);
			rs = pst.executeQuery();
			while(rs.next())
			{
				int idImpresa = rs.getInt("id_impresa");
				Impresa imp = Impresa.getByOid(conn, idImpresa);
				imprese.add(imp);
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
		
		return imprese;
	}
	
	
	
	
	
	/*ritorna un'impresa se gia' esiste, altrimenti ritorna null
	 * Un'impresa esiste gia' se esiste con quel
	 * cf,piva,ragionesociale e legale rappresentante
	 */
	public static Impresa getIfAlreadyExists(String ragioneSocialeImpresa, String pivaImpresa, String cfImpresa,String cfLegale, Connection db) throws Exception
	{
		
		PreparedStatement pst = null;
		Impresa toRet = null;
		ResultSet rs = null;
		try
		{
			/*controlliamo se gia' esiste il soggetto fisico impresa (legale rappresentante) */
			SoggettoFisico soggFound = SoggettoFisico.getIfAlreadyExists(cfLegale, db);
			
			/*se non esiste soggetto fisico (legale rappresentante) allora anche se tutti gli altri dati dell'impresa gia' esistono, 
			 * la consideriamo semanticamente un'impresa differente, quindi il metodo ritorna null poiche' indica che non l'ha trovata 
			 */
			 
			if(soggFound != null)
			 
			{
				pst = db.prepareStatement("select * from public_functions.anagrafica_cerca_impresa(NULL,?,?,?)");
				
				pst.setString(1, ragioneSocialeImpresa);
				pst.setString(2, cfImpresa);
				pst.setString(3, pivaImpresa);
				
				
				rs = pst.executeQuery();
				if(rs.next()) /* -> abbiamo trovato un match */
				{
					Integer idFound = rs.getInt(1);
					
					/*devo vedere se inoltre il rapp legale e l'impresa sono collegati */
					try{pst.close();} catch(Exception ex){}
					try{rs.close();} catch(Exception ex){}
					
					pst = db.prepareStatement("select * from select * from anagrafica_cerca_rel_impresa_soggetto_fisico(?,?)");
					pst.setInt(1, idFound);
					pst.setInt(2, soggFound.getId());
					rs = pst.executeQuery();
					if(rs.next())
						toRet = Impresa.getByOid(db, idFound);
					
				}
			}
			
			
		}
		catch(Exception ex)
		{
			throw ex;
			
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
		}
	
		return toRet;
	}
	
	
	
	/*ritorna le imprese  
	 * ricercate usando partita iva e/o codice fiscale e/o ragione sociale 
	 * alcuni di questi campi in input possono essere null
	 */
	public static ArrayList<Impresa> getByDatiAnag(String codiceFiscale, String ragioneSociale, String pIva, Connection conn) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Impresa> toRet = new ArrayList<Impresa>();
		
		/*i campi in input possono essere null, ma in caso in cui siano vuoti li setto a null per 
		 * correttezza di funzionamento della query
		 */
		codiceFiscale = codiceFiscale != null && codiceFiscale.trim().length( )> 0 ? codiceFiscale : null;
		ragioneSociale = ragioneSociale != null && ragioneSociale.trim().length( )> 0 ? ragioneSociale : null;
		pIva = pIva != null && pIva.trim().length( )> 0 ? pIva : null;
		
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_impresa(NULL,?,?,?)");
			Utilities.setNullable(java.sql.Types.VARCHAR, 1, ragioneSociale, pst); 
			Utilities.setNullable(java.sql.Types.VARCHAR, 2, codiceFiscale, pst); 
			Utilities.setNullable(java.sql.Types.VARCHAR, 3, pIva, pst);
			
			rs = pst.executeQuery();
			 
			while(rs.next())
			{
				toRet.add(new Impresa(rs,conn));
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
	
	 
}
