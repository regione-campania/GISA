/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

public class SuapMasterListLineaAttivita {
	
	private int id ; 
	private int idAggregazione ; 
	private String codiceProdottoSpecie ; 
	private String lineaAttivita ;
	private String tipo ;
	private String schedaSupplementare ;
	private String note ;
	private String mappingAteco ;
	private String codiceUnivoco ;
	private String codiceNazionaleRichiesto ; 
	private String chiInseriscePratica ;
	private String ateco1;
	private String ateco2;
	private String ateco3;
	private String ateco4;
	private String ateco5;
	private String ateco6;
	private String ateco7;
	private String ateco8;
	private String ateco9;
	private String ateco10;
	private String ateco11;
	private String ateco12;
	private String ateco13;
	private String ateco14;
	private String ateco15;
	private String ateco16;
	private String ateco17;
	private String ateco18;
	
	
	public SuapMasterListLineaAttivita(ResultSet rs)
	{
		buildRecord(rs);
	}
	
	public void buildRecord(ResultSet rs)
	{
		try
		{
			this.id 						=	rs.getInt("id");
			this.idAggregazione 			=	rs.getInt("id_aggregazione");
			this.codiceProdottoSpecie 		=	rs.getString("codice_prodotto_specie");
			this.lineaAttivita				=	rs.getString("linea_attivita");
			this.tipo		 				=	rs.getString("tipo");
			this.schedaSupplementare		=	rs.getString("scheda_supplementare");
			this.note						=	rs.getString("note");
			this.mappingAteco				=	rs.getString("mapping_ateco");
			this.codiceUnivoco				=	rs.getString("codice_univoco");
			this.codiceNazionaleRichiesto	=	rs.getString("codice_nazionale_richiesto");
			this.chiInseriscePratica		=	rs.getString("chi_inserisce_pratica");
			this.ateco1						=	rs.getString("ateco1");
			this.ateco2						=	rs.getString("ateco2");
			this.ateco3						=	rs.getString("ateco3");
			this.ateco4						=	rs.getString("ateco4");
			this.ateco5						=	rs.getString("ateco5");
			this.ateco6						=	rs.getString("ateco6");
			this.ateco7						=	rs.getString("ateco7");
			this.ateco8						=	rs.getString("ateco8");
			this.ateco9						=	rs.getString("ateco9");
			this.ateco10					=	rs.getString("ateco10");
			this.ateco11					=	rs.getString("ateco11");
			this.ateco12					=	rs.getString("ateco12");
			this.ateco13					=	rs.getString("ateco13");
			this.ateco14					=	rs.getString("ateco14");
			this.ateco15					=	rs.getString("ateco15");
			this.ateco16					=	rs.getString("ateco16");
			this.ateco17					=	rs.getString("ateco17");
			this.ateco18					=	rs.getString("ateco18");



		}
		catch(SQLException e)
		{
			System.out.println("##ERRORE COSTRUZIONE BEAN LINEA ATTIVITA "+e.getMessage());
		}
	}



	public SuapMasterListLineaAttivita(Connection db, int idLinea) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from " +MasterListImportUtil.TAB_LINEA_ATTIVITA+" where id = ?");
		pst.setInt(1, idLinea);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdAggregazione() {
		return idAggregazione;
	}



	public void setIdAggregazione(int idAggregazione) {
		this.idAggregazione = idAggregazione;
	}



	public String getCodiceProdottoSpecie() {
		return codiceProdottoSpecie;
	}



	public void setCodiceProdottoSpecie(String codiceProdottoSpecie) {
		this.codiceProdottoSpecie = codiceProdottoSpecie;
	}



	public String getLineaAttivita() {
		return lineaAttivita;
	}



	public void setLineaAttivita(String lineaAttivita) {
		this.lineaAttivita = lineaAttivita;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getSchedaSupplementare() {
		return schedaSupplementare;
	}



	public void setSchedaSupplementare(String schedaSupplementare) {
		this.schedaSupplementare = schedaSupplementare;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public String getMappingAteco() {
		return mappingAteco;
	}



	public void setMappingAteco(String mappingAteco) {
		this.mappingAteco = mappingAteco;
	}



	public String getCodiceUnivoco() {
		return codiceUnivoco;
	}



	public void setCodiceUnivoco(String codiceUnivoco) {
		this.codiceUnivoco = codiceUnivoco;
	}



	public String getCodiceNazionaleRichiesto() {
		return codiceNazionaleRichiesto;
	}



	public void setCodiceNazionaleRichiesto(String codiceNazionaleRichiesto) {
		this.codiceNazionaleRichiesto = codiceNazionaleRichiesto;
	}



	public String getChiInseriscePratica() {
		return chiInseriscePratica;
	}



	public void setChiInseriscePratica(String chiInseriscePratica) {
		this.chiInseriscePratica = chiInseriscePratica;
	}



	public String getAteco1() {
		return ateco1;
	}



	public void setAteco1(String ateco1) {
		this.ateco1 = ateco1;
	}



	public String getAteco2() {
		return ateco2;
	}



	public void setAteco2(String ateco2) {
		this.ateco2 = ateco2;
	}



	public String getAteco3() {
		return ateco3;
	}



	public void setAteco3(String ateco3) {
		this.ateco3 = ateco3;
	}



	public String getAteco4() {
		return ateco4;
	}



	public void setAteco4(String ateco4) {
		this.ateco4 = ateco4;
	}



	public String getAteco5() {
		return ateco5;
	}



	public void setAteco5(String ateco5) {
		this.ateco5 = ateco5;
	}



	public String getAteco6() {
		return ateco6;
	}



	public void setAteco6(String ateco6) {
		this.ateco6 = ateco6;
	}



	public String getAteco7() {
		return ateco7;
	}



	public void setAteco7(String ateco7) {
		this.ateco7 = ateco7;
	}



	public String getAteco8() {
		return ateco8;
	}



	public void setAteco8(String ateco8) {
		this.ateco8 = ateco8;
	}



	public String getAteco9() {
		return ateco9;
	}



	public void setAteco9(String ateco9) {
		this.ateco9 = ateco9;
	}



	public String getAteco10() {
		return ateco10;
	}



	public void setAteco10(String ateco10) {
		this.ateco10 = ateco10;
	}



	public String getAteco11() {
		return ateco11;
	}



	public void setAteco11(String ateco11) {
		this.ateco11 = ateco11;
	}



	public String getAteco12() {
		return ateco12;
	}



	public void setAteco12(String ateco12) {
		this.ateco12 = ateco12;
	}



	public String getAteco13() {
		return ateco13;
	}



	public void setAteco13(String ateco13) {
		this.ateco13 = ateco13;
	}



	public String getAteco14() {
		return ateco14;
	}



	public void setAteco14(String ateco14) {
		this.ateco14 = ateco14;
	}



	public String getAteco15() {
		return ateco15;
	}



	public void setAteco15(String ateco15) {
		this.ateco15 = ateco15;
	}



	public String getAteco16() {
		return ateco16;
	}



	public void setAteco16(String ateco16) {
		this.ateco16 = ateco16;
	}



	public String getAteco17() {
		return ateco17;
	}



	public void setAteco17(String ateco17) {
		this.ateco17 = ateco17;
	}



	public String getAteco18() {
		return ateco18;
	}



	public void setAteco18(String ateco18) {
		this.ateco18 = ateco18;
	}

	public boolean checkFlags(Connection db, String json_flags) throws SQLException {
		
		boolean esito = false;
		String sql = "";
		
		sql = "select * from check_flags(?, ?::json)";
		PreparedStatement pst = db.prepareStatement(sql);
		int i = 0;
		pst.setInt(++i, this.id);
		pst.setString(++i, json_flags);
		ResultSet rs= pst.executeQuery();
		System.out.println("query check flags " + pst);
		if(rs.next())
			{
				esito = rs.getBoolean(1);		 
			}
		
		return esito;
	}
	
	
	

}
