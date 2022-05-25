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

public class Stabilimento {
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	public Integer getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(Integer idAsl) {
		this.idAsl = idAsl;
	}
	public void setIndirizzi(List<Indirizzo> inds){this.indirizzi = inds;}
	
	public List<Indirizzo> getIndirizzi() {return this.indirizzi;}
	
	public List<RelazioneStabilimentoLineaProduttiva> getLineeProds() {
		return lineeProds;
	}
	public void setLineeProds(List<RelazioneStabilimentoLineaProduttiva> lineeProds) {
		this.lineeProds = lineeProds;
	}
	public Integer getIdStato() {
		return idStato;
	}
	public void setIdStato(Integer idStato) {
		this.idStato = idStato;
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	private Integer id;
	private String denominazione;
	private Timestamp dataScadenza;
	private Timestamp dataCancellazione;
	private Integer idAsl;
	private Integer idStato;
	private String stato; /*desc di id stato da lookup_stati */
	private Timestamp dataInserimento;

	


	private List<Indirizzo> indirizzi;
	private List<RelazioneStabilimentoLineaProduttiva> lineeProds;
	

	
	
	public Stabilimento(){}
	public Stabilimento(ResultSet rs,Connection conn, boolean individuaLineeProduttive)
	{
		try{setId(rs.getInt("id"));}catch(Exception ex){System.out.println("campo non presente");}
		try{setDenominazione(rs.getString("denominazione"));}catch(Exception ex){System.out.println("campo non presente");}
//		try{setDataScadenza(rs.getTimestamp("data_scadenza"));}catch(Exception ex){System.out.println("campo non presente");}
//		try{setDataCancellazione(rs.getTimestamp("data_cancellazione"));}catch(Exception ex){System.out.println("campo non presente");}
		try{setDataInserimento(rs.getTimestamp("data_inserimento"));}catch(Exception ex){System.out.println("campo non presente");}
		try{setIdAsl(rs.getInt("id_asl"));} catch(Exception ex){System.out.println("campo non presente");}
		try{setIdStato(rs.getInt("id_stato"));} catch(Exception ex){System.out.println("campo non presente");}
		try{ /*da id stato usando lookup_stati */
			String descStato = null;
			descStato = LookupPair.buildByCode(conn, "lookup_stati", "code", "description", getIdStato(), "enabled = true").getDesc();
			setStato(descStato);
		}
		catch(Exception ex){ System.out.println("campo non presente");}
		/*provo a risolvere indirizzi */
		try{
			
			List<Indirizzo> indirizzi = Indirizzo.getIndirizziStabilimento(getId(), conn);
			setIndirizzi(indirizzi);
			
		} catch(Exception ex) {System.out.println("campo non presente"); }
		
		
		
		/*provo a risolvere le istanze di linee produttive per lo stabilimento */
		try
		{
			if(individuaLineeProduttive)
			{
				List<RelazioneStabilimentoLineaProduttiva> prods = RelazioneStabilimentoLineaProduttiva.getAllRelazioniPerStabilimento(getId(), conn);
				setLineeProds(prods);
			}
			
		}
		catch(Exception ex){System.out.println("campo non presente");}
		
	}
	
	
	
	
	
	/*Da tutti gli stabilimenti */
	public static ArrayList<Stabilimento> getAllStabilimenti(Connection conn) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Stabilimento> toRet = new ArrayList<Stabilimento>();
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_stabilimento()");
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new Stabilimento(rs,conn,true));
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
	
	
	
	/*Da lo stabilimento per un dato id */
	public static  Stabilimento  getByOid(Connection conn, Integer oid, boolean individuaLineeProduttive) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_stabilimento(?)");
			pst.setInt(1, oid);
			rs = pst.executeQuery();
			
			if(!rs.next())
				return null;
			
			return new Stabilimento(rs,conn,individuaLineeProduttive);
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
	
	
	 
	public static Stabilimento getIfAlreadyExists(String cf, Connection db) throws Exception
	{
		
		Stabilimento toRet = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			throw new Exception("METODO NON IMPLEMENTATO");
			
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
	
//		return toRet;
		
	}
	
	
	
	
	
	/*ritorna Lista degli stabilimenti associati all'impresa, 
	 * anche se in realta' teoricamente e' sempre uno
	 */
	public static ArrayList<Stabilimento> getStabilimentiDaImpresa(Integer idImpresa, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Stabilimento> stabs = new ArrayList<Stabilimento>();
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_rel_impresa_stabilimento(?)");
			pst.setInt(1, idImpresa);
			rs = pst.executeQuery();
			while(rs.next())
			{
				int idStab = rs.getInt("id_stabilimento");
				Stabilimento stab = Stabilimento.getByOid(conn, idStab,true);
				stabs.add(stab);
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
		
		return stabs;
	}
	
	
	/*ritorna gli stabilimenti  
	 * ricercati usando tipo attivita (fissa-mobile)  e/o desc comune
	 * alcuni di questi campi in input possono essere null
	 */
	public static ArrayList<Stabilimento> getByDatiAnag(int idTipoAttivita,String descComune,  Connection conn) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Stabilimento> toRet = new ArrayList<Stabilimento>();
		
		/*i campi in input possono essere null, ma in caso in cui siano vuoti li setto a null per 
		 * correttezza di funzionamento della query
		 */
		descComune = descComune != null && descComune.trim().length( ) > 0 ? descComune.toLowerCase() : null;
		
		try
		{
			/*Non esiste una dbi specifica quindi ..*/
			ArrayList<Stabilimento> allStabs = getAllStabilimenti(conn);
			/*scorro su questi stabilimenti e li filtro uno ad uno */
			for(Stabilimento stab : allStabs)
			{
				/*scorro sulle linee produttive, e vedo se fanno match con l'id tipo attivita , se e' scelta
				 */
				boolean matchTrovatoPerTipoAttivita = false; 
				boolean matchTrovatoPerComuneIndirizzo = false;
				
				
				/*se si passa -1 allora non si vuole filtrare su 
				tipo attivita' quindi si considera come se avesse matchato */
				if(idTipoAttivita == -1)
				{
					matchTrovatoPerTipoAttivita = true;
				}
				else
				{
					for(RelazioneStabilimentoLineaProduttiva lineaProd : stab.getLineeProds())
					{
						
						if(lineaProd.getLineaAttivita().getIdLookupTipoAttivita() == idTipoAttivita)
						{
							matchTrovatoPerTipoAttivita = true;
							break;
						}
					}
				}
				
				
				/*proseguo analisi con questo stabilimento solo se e' stato fatto match per tipo di attivita' (se richiesta) */
				if(matchTrovatoPerTipoAttivita)
				{
					if(descComune != null) /*se e' richiesta restrizione sulla desc comune dell'indirizzo stab */
					{
						/*scorro sugli indirizzi dello stabilimento, e controllo il comune */
						for(Indirizzo ind : stab.getIndirizzi())
						{
							try
							{
							
								String descComuneStab = Comune.getById(conn,  ind.getIdComune() ).getNome().toLowerCase();
								if(descComune.equals(descComuneStab)) /*lo stabilimento ha tra gli indirizzi, uno che e' per il comune scelto */
								{
									matchTrovatoPerComuneIndirizzo = true; 
									break;
								}
							}
							catch(Exception ex)
							{
								System.out.println("Impossibile matchare un comune ! Continuo lo stesso ");
								continue;
							}
						}
					}
					else /*in tal caso non e' richiesta restrizione sul comune */
					{
						matchTrovatoPerComuneIndirizzo = true; 
					}
					
				}
				
				/*lo stabilimento soddisfa entrambi i match */
				if(matchTrovatoPerTipoAttivita && matchTrovatoPerComuneIndirizzo)
				{
					toRet.add(stab);
				}
				
				
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
