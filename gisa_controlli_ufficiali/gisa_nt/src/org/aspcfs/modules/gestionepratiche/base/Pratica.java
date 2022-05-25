/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionepratiche.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Pratica {
	
	private int id_pratica;
	private String numeroPratica;
	private int idTipologiaPratica;
	private Timestamp dataOperazione;
	private Timestamp dataInserimentoPratica;
	private int idStabilimento;
	private int altId;
	private String numeroRegistrazione;
	private String ragioneSociale;
	private String partitaIva;
	private String indirizzo;
	private int idUtente;
	private int statoPratica;
	private String comuneRichiedente;
	private int siteIdStab;
	private int idComuneRichiedente;
	private String dataOperazioneString;
	
	private String notaPratica;
	private int id_causale_pratica;
	private String desc_causale_pratica; 
	private String desc_tipo_operazione;

	public String getDataOperazioneString() {
		return dataOperazioneString;
	}

	public void setDataOperazioneString(String dataOperazioneString) {
		this.dataOperazioneString = dataOperazioneString;
	}

	public Pratica() {
		// TODO Auto-generated constructor stub
	}
	
	public Pratica(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public void buildRecord(ResultSet rs) throws SQLException{
		id_pratica = rs.getInt("id_pratica");
		numeroPratica = rs.getString("numero_pratica");
		idTipologiaPratica = rs.getInt("id_tipologia_pratica");
		dataOperazione = rs.getTimestamp("data_operazione");
		dataInserimentoPratica = rs.getTimestamp("data_inserimento_pratica");
		idStabilimento = rs.getInt("id_stabilimento");
		altId = rs.getInt("alt_id");
		numeroRegistrazione = rs.getString("numero_registrazione");
		ragioneSociale = rs.getString("ragione_sociale");
		partitaIva = rs.getString("partita_iva");
		indirizzo = rs.getString("indirizzo");
		idUtente = rs.getInt("id_utente"); 
		statoPratica = rs.getInt("stato_pratica"); 
		comuneRichiedente = rs.getString("comune_richiedente");
		siteIdStab = rs.getInt("site_id_stab");
		idComuneRichiedente = rs.getInt("id_comune_richiedente");
	}
	
	
	public static ArrayList<Pratica> getListaPratiche(Connection db, int altId) throws SQLException {
			ArrayList<Pratica> lista = new  ArrayList<Pratica>();
		 		
				PreparedStatement pst = db.prepareStatement("select * from get_lista_pratiche_suap(?)");
				pst.setInt(1, altId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					Pratica pra = new Pratica(rs);
					lista.add(pra);		
				}
			
			return lista;
	}
	
	public static ArrayList<Pratica> getListaPraticheApicoltura(Connection db, int altId) throws SQLException {
		ArrayList<Pratica> lista = new  ArrayList<Pratica>();
	 		
			PreparedStatement pst = db.prepareStatement("select * from get_lista_pratiche_suap_apicoltura(?)");
			pst.setInt(1, altId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				Pratica pra = new Pratica(rs);
				lista.add(pra);		
			}
		
		return lista;
}
	
	public Pratica getDettaglioPratica(Connection db, int id_pratica_in) throws SQLException {
		
			Pratica pratica_output = new Pratica();
	 		
			PreparedStatement pst = db.prepareStatement("select * from get_dettaglio_pratica_gins(?)");
			pst.setInt(1,id_pratica_in);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				pratica_output = new Pratica(rs);
				pratica_output.setId_causale_pratica(rs.getInt("id_causale_pratica"));
				pratica_output.setNotaPratica(rs.getString("note_pratica"));
				pratica_output.setDesc_causale_pratica(rs.getString("desc_causale_pratica"));
				pratica_output.setDesc_tipo_operazione(rs.getString("desc_tipo_operazione"));
			}
		
		return pratica_output;
	}
	
	public static ArrayList<Pratica> getListaPraticheSearch(Connection db, String numeroPratica, 
				int comune, String data_pec, int tipo_pratica, 
				String site_id_user, int numero_pagina, int size_pagina) throws SQLException {
		
			ArrayList<Pratica> lista = new  ArrayList<Pratica>();
	 		String sqlquery = "select * from get_lista_pratiche_amministrative(?,?,?,?,?)"
					+ " order by data_operazione desc  LIMIT " + 
					size_pagina + " OFFSET " + size_pagina * (numero_pagina-1);
			PreparedStatement pst = db.prepareStatement(sqlquery);
			pst.setInt(1, tipo_pratica);	
			pst.setInt(2, comune);
			pst.setString(3, site_id_user);
			pst.setString(4, data_pec);
			pst.setString(5, numeroPratica);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				Pratica pra = new Pratica(rs);
				lista.add(pra);		
			}
		
		return lista;
	}
	
	public static ArrayList<Pratica> getListaPraticheSearchApi(Connection db, String numeroPratica, 
			int comune, String data_pec, int tipo_pratica, 
			String site_id_user, int numero_pagina, int size_pagina) throws SQLException {
	
		ArrayList<Pratica> lista = new  ArrayList<Pratica>();
 		String sqlquery = "select * from get_lista_pratiche_amministrative_api(?,?,?,?,?)"
				+ " order by data_operazione desc  LIMIT " + 
				size_pagina + " OFFSET " + size_pagina * (numero_pagina-1);
		PreparedStatement pst = db.prepareStatement(sqlquery);
		pst.setInt(1, tipo_pratica);	
		pst.setInt(2, comune);
		pst.setString(3, site_id_user);
		pst.setString(4, data_pec);
		pst.setString(5, numeroPratica);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Pratica pra = new Pratica(rs);
			lista.add(pra);		
		}
	
		return lista;
	}
	
	public static int getNumeroPraticheTotSearch(Connection db, String numeroPratica, int comune, String data_pec, int tipo_pratica, String site_id_user) throws SQLException {
		
		int totale_pratiche = 0;
	 		
		PreparedStatement pst = db.prepareStatement("select count(*)::integer num_pratiche_trovate from get_lista_pratiche_amministrative(?,?,?,?,?)");
			pst.setInt(1, tipo_pratica);	
			pst.setInt(2, comune);
			pst.setString(3, site_id_user);
			pst.setString(4, data_pec);
			pst.setString(5, numeroPratica);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				totale_pratiche = rs.getInt("num_pratiche_trovate");	
			}
		
		return totale_pratiche;
	}
	
	public static int getNumeroPraticheTotSearchApi(Connection db, String numeroPratica, int comune, String data_pec, int tipo_pratica, String site_id_user) throws SQLException {
		
		int totale_pratiche = 0;
	 		
		PreparedStatement pst = db.prepareStatement("select count(*)::integer num_pratiche_trovate from get_lista_pratiche_amministrative_api(?,?,?,?,?)");
			pst.setInt(1, tipo_pratica);	
			pst.setInt(2, comune);
			pst.setString(3, site_id_user);
			pst.setString(4, data_pec);
			pst.setString(5, numeroPratica);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				totale_pratiche = rs.getInt("num_pratiche_trovate");	
			}
		
		return totale_pratiche;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public Timestamp getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Timestamp dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public int getIdTipologiaPratica() { 
		return idTipologiaPratica;
	}

	public void setIdTipologiaPratica(int idTipologiaPratica) {
		this.idTipologiaPratica = idTipologiaPratica;
	}
	

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getStatoPratica() {
		return statoPratica;
	}

	public void setStatoPratica(int statoPratica) {
		this.statoPratica = statoPratica;
	}

	public String getComuneRichiedente() {
		return comuneRichiedente;
	}

	public void setComuneRichiedente(String comuneRichiedente) {
		this.comuneRichiedente = comuneRichiedente;
	}

	public int getSiteIdStab() {
		return siteIdStab;
	}

	public void setSiteIdStab(int siteIdStab) {
		this.siteIdStab = siteIdStab;
	}

	public int getIdComuneRichiedente() {
		return idComuneRichiedente;
	}

	public void setIdComuneRichiedente(int idComuneRichiedente) {
		this.idComuneRichiedente = idComuneRichiedente;
	}

	public void insertPratica(Connection db, int userid) throws SQLException {
		// TODO Auto-generated method stub
		 String sql = "select * from public.inserisci_pratica(?,?,?,?,?,?,?)";
	    	PreparedStatement st = db.prepareStatement(sql);
	        st.setInt(1,userid );
	        st.setString(2, this.dataOperazioneString);
	        st.setInt(3, this.idComuneRichiedente);
	        st.setInt(4, this.idTipologiaPratica);
	        st.setString(5, numeroPratica);
	        st.setInt(6, 1);
	        st.setString(7, null); 
	        
	        System.out.println(st);
	        
	        ResultSet rs = st.executeQuery();
	        
	        while(rs.next()){
	        	numeroPratica = rs.getString("inserisci_pratica");
	        }
	}
	
	public void insertPraticaApi(Connection db, int userid) throws SQLException {
		// TODO Auto-generated method stub
		 String sql = "select * from public.inserisci_pratica(?,?,?,?,?,?,?,?)";
	    	PreparedStatement st = db.prepareStatement(sql);
	        st.setInt(1,userid );
	        st.setString(2, this.dataOperazioneString);
	        st.setInt(3, this.idComuneRichiedente);
	        st.setInt(4, this.idTipologiaPratica);
	        st.setString(5, numeroPratica);
	        st.setInt(6, 1);
	        st.setString(7, null);
	        st.setBoolean(8, true);
	        
	        System.out.println(st);
	        
	        ResultSet rs = st.executeQuery();
	        
	        while(rs.next()){
	        	numeroPratica = rs.getString("inserisci_pratica");
	        }
	}
	

	public int contaPratiche(Connection db, int idStab) throws SQLException {
		// TODO Auto-generated method stub
		int totale = 0;
		String sql = "select count(*) as numero_pratiche "
				+ "from pratiche_gins pg left join rel_eventi_pratiche rep on pg.id = rep.id_pratica "
				+ "left join eventi_su_osa eo on rep.id_evento = eo.id "
				+ "where eo.id_stabilimento = ? and pg.id_causale in (1,2,3) and rep.trashed_date is null and pg.apicoltura is not true";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idStab);
		
		//System.out.println(pst);
		ResultSet rs = pst.executeQuery();
    	while (rs.next()) {
    		 totale = rs.getInt("numero_pratiche"); //perche' era String?
    	}
    	return totale;
	}

	public int getId_pratica() {
		return id_pratica;
	}

	public void setId_pratica(int id_pratica) {
		this.id_pratica = id_pratica;
	}

	public void EliminaPratica(Connection db, int id_prat, int user_id) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "update pratiche_gins set trashedby = ? , trashed_date = now() where id = ?;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, user_id);
		pst.setInt(2, id_prat);
		
		ResultSet rs = pst.executeQuery();
    	
	}

	public Timestamp getDataInserimentoPratica() {
		return dataInserimentoPratica;
	}

	public void setDataInserimentoPratica(Timestamp dataInserimentoPratica) {
		this.dataInserimentoPratica = dataInserimentoPratica;
	}

	public String getNotaPratica() {
		return notaPratica;
	}

	public void setNotaPratica(String notaPratica) {
		this.notaPratica = notaPratica;
	}

	public int getId_causale_pratica() {
		return id_causale_pratica;
	}

	public void setId_causale_pratica(int id_causale_pratica) {
		this.id_causale_pratica = id_causale_pratica;
	}

	public String getDesc_causale_pratica() {
		return desc_causale_pratica;
	}

	public void setDesc_causale_pratica(String desc_causale_pratica) {
		this.desc_causale_pratica = desc_causale_pratica;
	}

	public String getDesc_tipo_operazione() {
		return desc_tipo_operazione;
	}

	public void setDesc_tipo_operazione(String desc_tipo_operazione) {
		this.desc_tipo_operazione = desc_tipo_operazione;
	}
	
}
