/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Stabilimento;

public class SintesisOperatoreMercato {


	
	private int id = -1;
	private int idStabilimentoSintesisMercato = -1;
	private int idRelazioneSintesisMercato = -1;
	private int riferimentoIdOperatore = -1;
	private String riferimentoIdNomeTabOperatore = null;
	private int numBox = -1;
	private String identificativo = null;
	private Timestamp entered = null;
	private int enteredBy = -1;
	private Timestamp dataCessazione = null;
	private int cessatoBy = -1;
	private Timestamp trashedDate = null;
	private int trashedBy = -1;
	private String noteHd = null;
	
	private Stabilimento opuStabilimento = null;
	private Organization orgStabilimento = null;
	private String ragioneSocialeOperatore = null;
	
	public SintesisOperatoreMercato() {
		// TODO Auto-generated constructor stub
	}
	
	public SintesisOperatoreMercato(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public SintesisOperatoreMercato(Connection db, int id) throws SQLException, IndirizzoNotFoundException {
		PreparedStatement pst = db.prepareStatement("select * from sintesis_operatori_mercato where id = ? and trashed_date is null");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			buildRecord(rs);
			setStabilimento(db);
		}
	}

	public void buildRecord(ResultSet rs) throws SQLException{
		id = rs.getInt("id");
		idStabilimentoSintesisMercato = rs.getInt("id_stabilimento_sintesis_mercato");
		idRelazioneSintesisMercato = rs.getInt("id_relazione_sintesis_mercato");
		riferimentoIdOperatore = rs.getInt("riferimento_id_operatore");
		riferimentoIdNomeTabOperatore = rs.getString("riferimento_id_nome_tab_operatore");
		numBox = rs.getInt("num_box");
		identificativo = rs.getString("identificativo");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		dataCessazione = rs.getTimestamp("data_cessazione");
		cessatoBy = rs.getInt("cessatoby");
		trashedDate = rs.getTimestamp("trashed_date");
		trashedBy = rs.getInt("trashedby");
		noteHd = rs.getString("note_hd");
	}
	
	
	public static ArrayList<SintesisOperatoreMercato> getElencoOperatori(Connection db, int idRelazione) throws SQLException, IndirizzoNotFoundException {
			ArrayList<SintesisOperatoreMercato> lista = new  ArrayList<SintesisOperatoreMercato>();
		 		
				PreparedStatement pst = db.prepareStatement("select * from sintesis_operatori_mercato where id_relazione_sintesis_mercato = ? and trashed_date is null and data_cessazione is null order by num_box asc");
				pst.setInt(1, idRelazione); 
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(rs);
					operatore.setStabilimento(db);
					lista.add(operatore);		
				}
			
			return lista;
	}
	
	public static ArrayList<SintesisOperatoreMercato> getStoricoOperatori(Connection db, int idRelazione, int numBox) throws SQLException, IndirizzoNotFoundException {
		ArrayList<SintesisOperatoreMercato> lista = new  ArrayList<SintesisOperatoreMercato>();
	 		
			PreparedStatement pst = db.prepareStatement("select * from sintesis_operatori_mercato where id_relazione_sintesis_mercato = ? and num_box = ? and (data_cessazione is not null or trashed_date is not null) order by identificativo desc");
			pst.setInt(1, idRelazione); 
			pst.setInt(2, numBox); 
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(rs);
				operatore.setStabilimento(db);
				lista.add(operatore);		
			}
		
		return lista;
}

	
	public void insert(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_operatori_mercato (id_relazione_sintesis_mercato, id_stabilimento_sintesis_mercato, riferimento_id_operatore, riferimento_id_nome_tab_operatore, num_box, identificativo , enteredby) values (?, ?, ?, ?, ?, ?, ?)");
		pst.setInt(1, idRelazioneSintesisMercato);
		pst.setInt(2, idStabilimentoSintesisMercato);
		pst.setInt(3, riferimentoIdOperatore);
		pst.setString(4, riferimentoIdNomeTabOperatore);
		pst.setInt(5, numBox);
		pst.setString(6, identificativo);
		pst.setInt(7, idUtente);
		pst.executeUpdate();			
	}

	public void delete(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_operatori_mercato set trashed_date = now(), trashedby = ? where id = ?");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);
		pst.executeUpdate();	
		
		PreparedStatement pst2 = db.prepareStatement("update opu_stabilimento set trashed_date = now(), trashed_by = ? where id in (select riferimento_id_operatore from sintesis_operatori_mercato where id = ? and riferimento_id_nome_tab_operatore = 'opu_stabilimento')");
		pst2.setInt(1, idUtente);
		pst2.setInt(2, id);
		pst2.executeUpdate();	
		
		PreparedStatement pst3 = db.prepareStatement("select * from refresh_anagrafica((select riferimento_id_operatore from sintesis_operatori_mercato where id = ? ), 'opu')");
		pst3.setInt(1, id);
		pst3.executeQuery();	 
	}
	
	public void cessazione(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_operatori_mercato set data_cessazione = now(), cessatoby = ? where id = ?");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);
		pst.executeUpdate();		
		
		PreparedStatement pst2 = db.prepareStatement("update opu_stabilimento set stato=4 where id in (select riferimento_id_operatore from sintesis_operatori_mercato where id = ? and riferimento_id_nome_tab_operatore = 'opu_stabilimento')");
		pst2.setInt(1, id);
		pst2.executeUpdate();	
		
		PreparedStatement pst3 = db.prepareStatement("update opu_relazione_stabilimento_linee_produttive set stato=4 where id_stabilimento in (select riferimento_id_operatore from sintesis_operatori_mercato where id = ? and riferimento_id_nome_tab_operatore = 'opu_stabilimento')");
		pst3.setInt(1, id);
		pst3.executeUpdate();
		
		PreparedStatement pst4 = db.prepareStatement("select * from refresh_anagrafica((select riferimento_id_operatore from sintesis_operatori_mercato where id = ? ), 'opu')");
		pst4.setInt(1, id);
		pst4.executeQuery();	
	}

	
	public void setIdentificativo(Connection db) throws SQLException {
		String res = "";
		PreparedStatement pst = db.prepareStatement("select * from genera_identificativo_operatore_mercato_sintesis(?, ?);");
		pst.setInt(1, idRelazioneSintesisMercato);
		pst.setInt(2, numBox);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			res = rs.getString(1);
		this.identificativo = res;
	}
	
	public void setStabilimento(Connection db) throws SQLException, IndirizzoNotFoundException {
		if (riferimentoIdOperatore>0 && riferimentoIdNomeTabOperatore!=null) {
			if (riferimentoIdNomeTabOperatore.equals("opu_stabilimento")){
				Stabilimento stab = new Stabilimento (db, riferimentoIdOperatore);
				this.opuStabilimento = stab;
			}
			else if (riferimentoIdNomeTabOperatore.equals("organization")){
				Organization org = new Organization (db, riferimentoIdOperatore);
				this.orgStabilimento = org;
		}
	}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdStabilimentoSintesisMercato() {
		return idStabilimentoSintesisMercato;
	}

	public void setIdStabilimentoSintesisMercato(int idStabilimentoSintesisMercato) {
		this.idStabilimentoSintesisMercato = idStabilimentoSintesisMercato;
	}

	public int getIdRelazioneSintesisMercato() {
		return idRelazioneSintesisMercato;
	}

	public void setIdRelazioneSintesisMercato(int idRelazioneSintesisMercato) {
		this.idRelazioneSintesisMercato = idRelazioneSintesisMercato;
	}

	public int getRiferimentoIdOperatore() {
		return riferimentoIdOperatore;
	}

	public void setRiferimentoIdOperatore(int riferimentoIdOperatore) {
		this.riferimentoIdOperatore = riferimentoIdOperatore;
	}

	public String getRiferimentoIdNomeTabOperatore() {
		return riferimentoIdNomeTabOperatore;
	}

	public void setRiferimentoIdNomeTabOperatore(String riferimentoIdNomeTabOperatore) {
		this.riferimentoIdNomeTabOperatore = riferimentoIdNomeTabOperatore;
	}

	public int getNumBox() {
		return numBox;
	}

	public void setNumBox(int numBox) {
		this.numBox = numBox;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Timestamp getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public int getCessatoBy() {
		return cessatoBy;
	}

	public void setCessatoBy(int cessatoBy) {
		this.cessatoBy = cessatoBy;
	}

	public Timestamp getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}

	public int getTrashedBy() {
		return trashedBy;
	}

	public void setTrashedBy(int trashedBy) {
		this.trashedBy = trashedBy;
	}

	public String getNoteHd() {
		return noteHd;
	}

	public void setNoteHd(String noteHd) {
		this.noteHd = noteHd;
	}

	public Stabilimento getOpuStabilimento() {
		return opuStabilimento;
	}

	public void setOpuStabilimento(Stabilimento opuStabilimento) {
		this.opuStabilimento = opuStabilimento;
	}

	public Organization getOrgStabilimento() {
		return orgStabilimento;
	}

	public void setOrgStabilimento(Organization orgStabilimento) {
		this.orgStabilimento = orgStabilimento;
	}

	public String getRagioneSocialeOperatore() {
		if (opuStabilimento!=null)
			return opuStabilimento.getOperatore().getRagioneSociale();
		if (orgStabilimento!=null)
			return orgStabilimento.getName();
		return "";
	}

	public static ArrayList<SintesisOperatoreMercato> load_operatori_mercato_per_cu(int idControlloUfficiale, Connection db) throws SQLException, IndirizzoNotFoundException {
		ArrayList<SintesisOperatoreMercato> lista = new  ArrayList<SintesisOperatoreMercato>();
 		
		PreparedStatement pst = db.prepareStatement("select * from sintesis_operatori_mercato where id in (select id_operatore_mercato from controlli_ufficiali_operatori_mercato where id_controllo_ufficiale = ? and trashed_date is null) and trashed_date is null order by num_box asc");
		pst.setInt(1, idControlloUfficiale); 
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(rs);
			operatore.setStabilimento(db);
			lista.add(operatore);		
		}
	
	return lista; 
	}

	

}
