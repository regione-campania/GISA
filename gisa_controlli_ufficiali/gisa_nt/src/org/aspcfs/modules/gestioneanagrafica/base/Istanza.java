/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.gestioneml.actions.GestioneMasterList;
import org.aspcfs.utils.DatabaseUtils;


public class Istanza 
{
	private Integer id=null;
	private Integer idStabilimento=null;
	private Integer idImpresa=null;
	private Integer idAttivita=null;
	private Integer tipoCarattere=null;
	private String cun=null;
	private Timestamp dataInizio=null;
	private Timestamp dataFine=null;
	private String pathCompleto=null;
	private String numero_registrazione=null;
	private Integer idStato=null;
	private Integer idMacroarea=null;
	private Integer idAggregazione=null;
	private String telefono=null;
	private String fax=null;
	private Integer tipoAttivita=null;
	private String codiceUnivoco=null;
	
	public final static int STATO_ATTIVO= 1;
	public final static int STATO_REVOCATO= 2;
	public final static int STATO_SOSPESO= 3;
	public final static int STATO_IN_DOMANDA= 4;
	public final static int STATO_CESSATO= 5;
	public final static int STATO_RICONOSCIUTO_CONDIZIONATO= 6;
	public final static int STATO_RESPINTA= 7;
	public final static int STATO_REGISTRAZIONE_NON_ANCORA_DISPONIBILE= 8;
	public final static int STATO_AUTORIZZATA= 9;
	public final static int STATO_CANCELLATA= 10;
	public final static int STATO_CESSATO_E_SINCRONIZZATO= 11;
	public final static int STATO_IN_ITINERE= 12;
	public final static int STATO_IN_RICHIESTA= 13;
	public final static int STATO_IN_SCADENZA= 14;
	public final static int STATO_N_D= 15;
	public final static int STATO_REVOCATA= 16;
	public final static int STATO_RINNOVO= 17;
	public final static int STATO_SCADUTO= 18;
	public final static int STATO_SOSPESA= 19;
	public final static int STATO_CESSATO_E_ARCHIVIATO= 20;
	public final static int STATO_ATTIVO_E_ARCHIVIATO= 21;
	public final static int STATO_SOSPESO_E_ARCHIVIATO= 22;

	
	
	
	
	public Integer getIdAttivita() {
		return idAttivita;
	}


	public void setIdAttivita(Integer idAttivita) {
		this.idAttivita = idAttivita;
	}


	public Integer getIdMacroarea() {
		return idMacroarea;
	}


	public void setIdMacroarea(Integer idMacroarea) {
		this.idMacroarea = idMacroarea;
	}


	public Integer getIdAggregazione() {
		return idAggregazione;
	}


	public void setIdAggregazione(Integer idAggregazione) {
		this.idAggregazione = idAggregazione;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIdStabilimento() {
		return idStabilimento;
	}


	public void setIdStabilimento(Integer idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public Integer getTipoCarattere() {
		return tipoCarattere;
	}


	public void setTipoCarattere(Integer tipoCarattere) {
		this.tipoCarattere = tipoCarattere;
	}


	public String getCun() {
		return cun;
	}


	public void setCun(String cun) {
		this.cun = cun;
	}


	public Timestamp getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = DatabaseUtils.parseDateToTimestamp(dataInizio);
	}

	public Timestamp getDataFine() {
		return dataFine;
	}


	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = DatabaseUtils.parseDateToTimestamp(dataFine);
	}
	
	
	public Integer getTipoAttivita() {
		return tipoAttivita;
	}


	public void setTipoAttivita(Integer tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}


	public String getCodiceUnivoco() {
		return codiceUnivoco;
	}


	public void setCodiceUnivoco(String codiceUnivoco) {
		this.codiceUnivoco = codiceUnivoco;
	}


	public Istanza()
	{
	}

	public Istanza (Integer idStabilimento, String  idLinea, String  dataInizio, String  dataFine, String  tipoCarattere, String  cun, String numRegistrazione){
		
		this.idStabilimento = idStabilimento;
		this.idAttivita = Integer.parseInt(idLinea);
		this.tipoCarattere = Integer.parseInt(tipoCarattere);
		setDataInizio(dataInizio);
		setDataFine(dataFine);
		this.cun = cun;
		this.numero_registrazione = numRegistrazione;
		
	}
	
	public void buildRecord(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.idAttivita = (Integer) rs.getObject("id_attivita");
		try{
			rs.findColumn("id_stabilimento");
			this.idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		} catch (SQLException e) {
			;
		}
		
		try{
			rs.findColumn("id_impresa");
			this.idImpresa = DatabaseUtils.getInt(rs, "id_impresa");
		} catch (SQLException e) {
			;
		}
		this.cun = rs.getString("cun");
		this.dataInizio = rs.getTimestamp("data_inizio_attivita");
		this.dataFine = rs.getTimestamp("data_fine_attivita");
		this.tipoCarattere = rs.getInt("id_carattere");
		this.numero_registrazione = rs.getString("numero_registrazione");
		this.idStato = rs.getInt("id_stato");
		this.idMacroarea = (Integer) rs.getObject("id_macroarea");
		this.idAggregazione = (Integer)  rs.getObject("id_aggregazione");
		try{
			rs.findColumn("telefono");
			this.telefono = rs.getString("telefono");
		} catch (SQLException e) {
			;
		}
		try{
			rs.findColumn("fax");
			this.fax = rs.getString("fax");
		} catch (SQLException e) {
			;
		}
		this.tipoAttivita= (Integer) rs.getObject("id_tipo_attivita");
		this.codiceUnivoco = rs.getString("codice_univoco");

	}
	
	public Integer insert( Connection conn, int idUtente) throws SQLException 
	{
		String sql = " select * from anagrafica.anagrafica_inserisci_rel_stabilimento_linea_attivita( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		int i = 0;
		st.setObject(++i, idMacroarea);
		st.setObject(++i, idAggregazione);
		st.setInt(++i, idAttivita);
		st.setInt(++i, idStabilimento);
		st.setObject(++i, numero_registrazione);
		st.setString(++i, cun);
		st.setObject(++i, telefono);
		st.setObject(++i, fax);
		st.setObject(++i, 0);
		st.setObject(++i, tipoAttivita);
		st.setTimestamp(++i, dataInizio);
		st.setTimestamp(++i, dataFine);
		st.setInt(++i, idUtente);
		st.setInt(++i, tipoCarattere);
		
		ResultSet rs = st.executeQuery();
		if(rs.next())
			id=rs.getInt(1);
		return id;
	}
	
	public void setPathCompleto(Connection db) throws SQLException {
		String lineaAttivitaMasterList = GestioneMasterList.getPathCompleto(db, idAttivita);
		this.pathCompleto = lineaAttivitaMasterList;
	}


	public String getNumero_registrazione() {
		return numero_registrazione;
	}


	public void setNumero_registrazione(String numero_registrazione) {
		this.numero_registrazione = numero_registrazione;
	}


	public String getPathCompleto() {
		return pathCompleto;
	}


	public void setPathCompleto(String pathCompleto) {
		this.pathCompleto = pathCompleto;
	}


	public Integer getIdStato() {
		return idStato;
	}


	public void setIdStato(Integer idStato) {
		this.idStato = idStato;
	}
	
	public void update( Connection conn, int idUtente) throws SQLException 
	{
				    
			    
		String sql = " select * from anagrafica.anagrafica_modifica_rel_stabilimento_linea_attivita( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		int i = 0;
		st.setObject(++i, idMacroarea);
		st.setObject(++i, idAggregazione);
		st.setInt(++i, idAttivita);
		st.setInt(++i, idStabilimento);
		st.setObject(++i, numero_registrazione);
		st.setString(++i, cun);
		st.setObject(++i, telefono);
		st.setObject(++i, fax);
		st.setObject(++i, idUtente);
		st.setObject(++i, idStato);
		st.setBoolean(++i, true);
		st.setBoolean(++i, false);
		st.setInt(++i, tipoCarattere);
		st.setString(++i, null);
		st.setString(++i, codiceUnivoco);
		st.setInt(++i, tipoAttivita);
		st.setTimestamp(++i, dataInizio);
		st.setTimestamp(++i, dataFine);
		
		ResultSet rs = st.executeQuery();
	}


	public Integer getIdImpresa() {
		return idImpresa;
	}


	public void setIdImpresa(Integer idImpresa) {
		this.idImpresa = idImpresa;
	}
}
