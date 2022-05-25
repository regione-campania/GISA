/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.allegatof;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class ListaDistribuzione extends GenericBean {
	
	
	private String distributore = null;
	private Timestamp data = null;
	private String asl = null;
	private int numeroCuEseguiti = 0;
	private double prodottoGiaRitirato = 0;
	private double prodottoAccantonato = 0;
	private double prodottoVincoloSanitario = 0;
	private int prodottoVendutoConsumatore = 0;
	private int prodottoDistribuito = 0;
	private int prodottoDistribuitoAsl = 0;
	private int prodottoDistribuitoRegione = 0;
	private int prodottoDistribuitoNazionale = 0;
	private int prodottoDistribuitoEstero = 0;
	private int prodottoDistribuitoRitiro = 0;
	
	private int operatoriNonComunicazioneRischio = 0;
	private int operatoriNonRicevutoProdotto = 0;
	private int operatoriSoloSedeLegale = 0;
	private int operatoriNonEsistenti = 0;
	private int operatoriNonProcedureRitiro = 0;
	private int operatoriNonProcedureRichiamo = 0;
	
	private String azioniAdottate = null;

	

	public String getDistributore() {
		return distributore;
	}

	public void setDistributore(String distributore) {
		this.distributore = distributore;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public int getNumeroCuEseguiti() {
		return numeroCuEseguiti;
	}

	public void setNumeroCuEseguiti(int numeroCuEseguiti) {
		this.numeroCuEseguiti = numeroCuEseguiti;
	}

	public double getProdottoGiaRitirato() {
		return prodottoGiaRitirato;
	}

	public void setProdottoGiaRitirato(double prodottoGiaRitirato) {
		this.prodottoGiaRitirato = prodottoGiaRitirato;
	}

	public double getProdottoAccantonato() {
		return prodottoAccantonato;
	}

	public void setProdottoAccantonato(double prodottoAccantonato) {
		this.prodottoAccantonato = prodottoAccantonato;
	}

	public double getProdottoVincoloSanitario() {
		return prodottoVincoloSanitario;
	}

	public void setProdottoVincoloSanitario(double prodottoVincoloSanitario) {
		this.prodottoVincoloSanitario = prodottoVincoloSanitario;
	}

	public int getProdottoVendutoConsumatore() {
		return prodottoVendutoConsumatore;
	}

	public void setProdottoVendutoConsumatore(int prodottoVendutoConsumatore) {
		this.prodottoVendutoConsumatore = prodottoVendutoConsumatore;
	}

	public int getProdottoDistribuito() {
		return prodottoDistribuito;
	}

	public void setProdottoDistribuito(int prodottoDistribuito) {
		this.prodottoDistribuito = prodottoDistribuito;
	}

	public int getProdottoDistribuitoAsl() {
		return prodottoDistribuitoAsl;
	}

	public void setProdottoDistribuitoAsl(int prodottoDistribuitoAsl) {
		this.prodottoDistribuitoAsl = prodottoDistribuitoAsl;
	}

	public int getProdottoDistribuitoRegione() {
		return prodottoDistribuitoRegione;
	}

	public void setProdottoDistribuitoRegione(int prodottoDistribuitoRegione) {
		this.prodottoDistribuitoRegione = prodottoDistribuitoRegione;
	}

	public int getProdottoDistribuitoNazionale() {
		return prodottoDistribuitoNazionale;
	}

	public void setProdottoDistribuitoNazionale(int prodottoDistribuitoNazionale) {
		this.prodottoDistribuitoNazionale = prodottoDistribuitoNazionale;
	}

	public int getProdottoDistribuitoEstero() {
		return prodottoDistribuitoEstero;
	}

	public void setProdottoDistribuitoEstero(int prodottoDistribuitoEstero) {
		this.prodottoDistribuitoEstero = prodottoDistribuitoEstero;
	}

	public int getOperatoriNonComunicazioneRischio() {
		return operatoriNonComunicazioneRischio;
	}

	public void setOperatoriNonComunicazioneRischio(int operatoriNonComunicazioneRischio) {
		this.operatoriNonComunicazioneRischio = operatoriNonComunicazioneRischio;
	}

	public int getOperatoriNonRicevutoProdotto() {
		return operatoriNonRicevutoProdotto;
	}

	public void setOperatoriNonRicevutoProdotto(int operatoriNonRicevutoProdotto) {
		this.operatoriNonRicevutoProdotto = operatoriNonRicevutoProdotto;
	}

	public int getOperatoriSoloSedeLegale() {
		return operatoriSoloSedeLegale;
	}

	public void setOperatoriSoloSedeLegale(int operatoriSoloSedeLegale) {
		this.operatoriSoloSedeLegale = operatoriSoloSedeLegale;
	}

	public int getOperatoriNonEsistenti() {
		return operatoriNonEsistenti;
	}

	public void setOperatoriNonEsistenti(int operatoriNonEsistenti) {
		this.operatoriNonEsistenti = operatoriNonEsistenti;
	}

	public int getOperatoriNonProcedureRitiro() {
		return operatoriNonProcedureRitiro;
	}

	public void setOperatoriNonProcedureRitiro(int operatoriNonProcedureRitiro) {
		this.operatoriNonProcedureRitiro = operatoriNonProcedureRitiro;
	}

	public int getOperatoriNonProcedureRichiamo() {
		return operatoriNonProcedureRichiamo;
	}

	public void setOperatoriNonProcedureRichiamo(int operatoriNonProcedureRichiamo) {
		this.operatoriNonProcedureRichiamo = operatoriNonProcedureRichiamo;
	}

	public int getProdottoDistribuitoRitiro() {
		return prodottoDistribuitoRitiro;
	}

	public void setProdottoDistribuitoRitiro(int prodottoDistribuitoRitiro) {
		this.prodottoDistribuitoRitiro = prodottoDistribuitoRitiro;
	}

	public String getAzioniAdottate() {
		return azioniAdottate;
	}

	public void setAzioniAdottate(String azioniAdottate) {
		this.azioniAdottate = azioniAdottate;
	}
	
	
	public ListaDistribuzione(Connection db, int idLista, int idAsl) {
		// TODO Auto-generated constructor stub
		
		String sql = "select * from allerte_f_lista(?, ?, -1)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, idLista);
			ResultSet rs= pst.executeQuery();
		
			if (rs.next()){
				buildRecord(rs);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ListaDistribuzione(Connection db, int idLista, int idAsl, int idAllerta) {
		// TODO Auto-generated constructor stub
		
		String sql = "select * from allerte_f_lista(?, -1, ?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, idAllerta);
			ResultSet rs= pst.executeQuery();
		
			if (rs.next()){
				buildRecord(rs);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void buildRecord(ResultSet rs) throws SQLException{
		
		asl = rs.getString("asl");
		numeroCuEseguiti = rs.getInt("numero_cu_eseguiti");
		operatoriNonProcedureRitiro = rs.getInt("non_eseguite_procedure_ritiro");
		operatoriNonProcedureRichiamo = rs.getInt("non_eseguite_procedure_richiamo");
		prodottoGiaRitirato = rs.getDouble("prodotto_ritirato_fornitore");
		prodottoAccantonato = rs.getDouble("prodotto_attesa_ritiro");
		prodottoVendutoConsumatore = rs.getInt("prodotto_venduto_consumatore");
		operatoriNonRicevutoProdotto = rs.getInt("non_ricevuto_prodotto");
		operatoriSoloSedeLegale = rs.getInt("solo_sede_legale");
		operatoriNonEsistenti = rs.getInt("non_esistente");
		prodottoDistribuito = rs.getInt("prodotto_distribuito");
		prodottoDistribuitoAsl = rs.getInt("prodotto_distribuito_asl");
		prodottoDistribuitoRegione = rs.getInt("prodotto_distribuito_regione");
		prodottoDistribuitoNazionale = rs.getInt("prodotto_distribuito_nazionale");
		prodottoDistribuitoEstero = rs.getInt("prodotto_distribuito_estero");
		prodottoDistribuitoRitiro = rs.getInt("prodotto_distribuito_ritiro");
		azioniAdottate = rs.getString("azioni_adottate");
		prodottoVincoloSanitario = rs.getDouble("prodotto_detenuto");
		distributore = rs.getString("nome_fornitore");
		data = rs.getTimestamp("data_lista");
		operatoriNonComunicazioneRischio = rs.getInt("non_ricevuta_comunicazione_rischio");
		
	}
	
	
}
