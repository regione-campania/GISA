/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StoricoImport {
	
	private int id;
	private int idUtente;
	private String nomeFile;
	private String nomeFileCompleto;
	private String codDocumento;
	private String tipoRichiesta;
	private Timestamp dataImport;
	private int gestoreAcque;
	private String nomeGestoreAcque;
	private String erroreInsert;
	private String erroreParsingFile;
	private Boolean esito;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGestoreAcque() 
	{
		return gestoreAcque;
	}
	public void setGestoreAcque(int gestoreAcque) 
	{
		this.gestoreAcque = gestoreAcque;
	}
	public String getNomeGestoreAcque() 
	{
		return nomeGestoreAcque;
	}
	public void setNomeGestoreAcque(String nomeGestoreAcque) 
	{
		this.nomeGestoreAcque = nomeGestoreAcque;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNomeFileCompleto() {
		return nomeFileCompleto;
	}
	public void setNomeFileCompleto(String nomeFileCompleto) {
		this.nomeFileCompleto = nomeFileCompleto;
	}
	public String getCodDocumento() {
		return codDocumento;
	}
	public void setCodDocumento(String codDocumento) {
		this.codDocumento = codDocumento;
	}
	public String getTipoRichiesta() {
		return tipoRichiesta;
	}
	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}
	
	public Timestamp getDataImport() {
		return dataImport;
	}
	public void setDataImport(Timestamp dataImport) {
		this.dataImport = dataImport;
	}
	
	public String getErroreInsert() {
		return erroreInsert;
	}
	public void setErroreInsert(String erroreInsert) {
		this.erroreInsert = erroreInsert;
	}
	public String getErroreParsingFile() {
		return erroreParsingFile;
	}
	public void setErroreParsingFile(String erroreParsingFile) {
		this.erroreParsingFile = erroreParsingFile;
	}
	
	public ArrayList<StoricoImport> getAll(Connection conn) throws GestoreNotFoundException, Exception
	{
		ArrayList<StoricoImport> storicoImport = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
			
		pst = conn.prepareStatement("select * from gestori_acque_log_import order by data_import desc ");
		rs = pst.executeQuery();
		while(rs.next())
		{
			StoricoImport storicoTemp = new StoricoImport();
			storicoTemp.setId(rs.getInt("id"));
			storicoTemp.setIdUtente(rs.getInt("id_utente"));
			storicoTemp.setDataImport(rs.getTimestamp("data_import"));
			storicoTemp.setNomeFile(rs.getString("nome_file"));
			storicoTemp.setTipoRichiesta(rs.getString("tipo_richiesta"));
			storicoTemp.setNomeFileCompleto(rs.getString("nome_file_completo"));
			storicoTemp.setGestoreAcque(rs.getInt("gestore_acque"));
			storicoTemp.setErroreInsert(rs.getString("errore_insert"));
			storicoTemp.setErroreParsingFile(rs.getString("errore_parsing_file"));
			storicoTemp.setNomeGestoreAcque(new GestoreAcque(storicoTemp.getGestoreAcque(), conn).getNome());
			storicoImport.add(storicoTemp);
		}
		return storicoImport;
	}
	
	public ArrayList<StoricoImport> getStoricoUtente(Connection conn, int idUtente) throws GestoreNotFoundException, Exception
	{
		ArrayList<StoricoImport> storicoImport = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
			
		pst = conn.prepareStatement("select * from gestori_acque_log_import where id_utente = ? order by data_import desc ");
		pst.setInt(1, idUtente);
		rs = pst.executeQuery();
		while(rs.next())
		{
			StoricoImport storicoTemp = new StoricoImport();
			storicoTemp.setId(rs.getInt("id"));
			storicoTemp.setIdUtente(rs.getInt("id_utente"));
			storicoTemp.setDataImport(rs.getTimestamp("data_import"));
			storicoTemp.setNomeFile(rs.getString("nome_file"));
			storicoTemp.setTipoRichiesta(rs.getString("tipo_richiesta"));
			storicoTemp.setNomeFileCompleto(rs.getString("nome_file_completo"));
			storicoTemp.setGestoreAcque(rs.getInt("gestore_acque"));
			storicoTemp.setCodDocumento(rs.getString("cod_documento"));
			storicoTemp.setErroreInsert(rs.getString("errore_insert"));
			storicoTemp.setErroreParsingFile(rs.getString("errore_parsing_file"));
			storicoTemp.setNomeGestoreAcque(new GestoreAcque(storicoTemp.getGestoreAcque(), conn).getNome());
			storicoImport.add(storicoTemp);
		}
		return storicoImport;
	}
	
	public StoricoImport getStorico(Connection conn,int id) throws GestoreNotFoundException, Exception
	{
		StoricoImport storicoTemp = new StoricoImport();
		PreparedStatement pst = null;
		ResultSet rs = null;
			
		pst = conn.prepareStatement("select * from gestori_acque_log_import where id = ? order by data_import desc ");
		pst.setInt(1, id);
		rs = pst.executeQuery();
		while(rs.next())
		{
			storicoTemp.setId(rs.getInt("id"));
			storicoTemp.setIdUtente(rs.getInt("id_utente"));
			storicoTemp.setDataImport(rs.getTimestamp("data_import"));
			storicoTemp.setNomeFile(rs.getString("nome_file"));
			storicoTemp.setTipoRichiesta(rs.getString("tipo_richiesta"));
			storicoTemp.setNomeFileCompleto(rs.getString("nome_file_completo"));
			storicoTemp.setGestoreAcque(rs.getInt("gestore_acque"));
			storicoTemp.setErroreInsert(rs.getString("errore_insert"));
			storicoTemp.setErroreParsingFile(rs.getString("errore_parsing_file"));
			storicoTemp.setNomeGestoreAcque(new GestoreAcque(storicoTemp.getGestoreAcque(), conn).getNome());
		}
		return storicoTemp;
	}
	
	
}
