/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DbUtil;

import com.darkhorseventures.framework.actions.ActionContext;

public class RegistrazioniWKF {
	
	private int idStato = -1;
	private int idRegistrazione = -1;
	private String descrizioneRegistrazione = "";
	private int idProssimoStato = -1;
	private String descrizioneProssimoStato = "";
	private String title = "";
	private boolean flagPossibilitaRegistazioni = true;
	private int idSpecie = -1;
	private boolean onlyHd = false;
	
	private boolean flagHasSecondoMicrochip = false;
	
	private boolean flagHasPassaporto = false;
	private boolean flagHasRitrovamentoNonDenunciato = false;
	private boolean flagHasDetenzioneInCanileDopoRitrovamento = false;
	private boolean flagPrelievoDnaEffetuato = false;
	private boolean flagAllontanamentoEffettuato = false;

	//INCLUDI REGISTRAZIONI ONLY_HD NELLE POSSIBILI REGISTRAZIONI
	private boolean flagIncludiHd = false;
	
	//RECUPERA SOLO REGISTRAZIONI EFFETTUABILI FUORI ASL
	private boolean checkSoloRegistrazioniEffettuabiliFuoriAsl = false;
	private boolean checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl = false;
	private boolean flagIsUtenteInCaricoPerFuoriDominio = false;
	private boolean flagIsAslProprietariaConAnimaleFuoriDominioAsl = false;
	private boolean flagIsAslDiversaConAnimaleFuoriDominioAsl = false;
	private boolean flagIsCatturaUltimaRegistrazioneFuoriDominioAsl = false;
	private boolean flagEscludiTrasferimentoCanileFuoriDominioAls = false;
	/**
	DA SPECIFICHE SOLO SE SI TRATTA D CATTURA FUORI ASL POSSO ABILITARE REIMMISSIONE E TRASF. CANILE.
	SE SI TRATTA DI RITROVAMENTO NN DENUNCIATO O RITROVAMENTO NO X CANI RANDAGI. PER CANI PADRONALI DEVE ESSERCI
	**/
		
	
	
	
	public final static String mapping_specie_registrazione_table = "mapping_registrazioni_specie_animale";
	
	public final static int start = 1;
	public final static int privato = 2;
	public final static int randagio = 3;
	public final static int privato_furto = 4;
	public final static int privato_ceduto = 5;
	public final static int privato_fuori_regione = 6;
	public final static int privato_deceduto = 7;
	public final static int privato_sterilizzato = 8;
	/**
	 * @return the idStato
	 */
	public int getIdStato() {
		return idStato;
	}
	/**
	 * @param idStato the idStato to set
	 */
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	/**
	 * @return the idRegistrazione
	 */
	public int getIdRegistrazione() {
		return idRegistrazione;
	}
	/**
	 * @param idRegistrazione the idRegistrazione to set
	 */
	public void setIdRegistrazione(int idRegistrazione) {
		this.idRegistrazione = idRegistrazione;
	}
	/**
	 * @return the idProssimoStato
	 */
	public int getIdProssimoStato() {
		return idProssimoStato;
	}
	/**
	 * @param idProssimoStato the idProssimoStato to set
	 */
	public void setIdProssimoStato(int idProssimoStato) {
		this.idProssimoStato = idProssimoStato;
	}
		    

	
	public boolean isFlagHasDetenzioneInCanileDopoRitrovamento() {
		return flagHasDetenzioneInCanileDopoRitrovamento;
	}
	public void setFlagHasDetenzioneInCanileDopoRitrovamento(
			boolean flagHasDetenzioneInCanileDopoRitrovamento) {
		this.flagHasDetenzioneInCanileDopoRitrovamento = flagHasDetenzioneInCanileDopoRitrovamento;
	}
	/**
	 * @return the descrizioneRegistrazione
	 */
	public String getDescrizioneRegistrazione() {
		return descrizioneRegistrazione;
	}
	/**
	 * @param descrizioneRegistrazione the descrizioneRegistrazione to set
	 */
	public void setDescrizioneRegistrazione(String descrizioneRegistrazione) {
		this.descrizioneRegistrazione = descrizioneRegistrazione;
	}
	
	
	
	/**
	 * @return the descrizioneProssimoStaro
	 */
	public String getDescrizioneProssimoStato() {
		return descrizioneProssimoStato;
	}
	/**
	 * @param descrizioneProssimoStaro the descrizioneProssimoStaro to set
	 */
	public void setDescrizioneProssimoStato(String descrizioneProssimoStaro) {
		this.descrizioneProssimoStato = descrizioneProssimoStaro;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	public boolean isFlagPossibilitaRegistazioni() {
		return flagPossibilitaRegistazioni;
	}
	public void setFlagPossibilitaRegistazioni(boolean flagPossibilitaRegistazioni) {
		this.flagPossibilitaRegistazioni = flagPossibilitaRegistazioni;
	}
	
	
	
	public boolean isFlagHasSecondoMicrochip() {
		return flagHasSecondoMicrochip;
	}
	public void setFlagHasSecondoMicrochip(boolean flagHasSecondoMicrochip) {
		this.flagHasSecondoMicrochip = flagHasSecondoMicrochip;
	}
	
	public boolean isFlagHasRitrovamentoNonDenunciato() {
		return flagHasRitrovamentoNonDenunciato;
	}
	public void setFlagHasRitrovamentoNonDenunciato(boolean flagHasRitrovamentoNonDenunciato) {
		this.flagHasRitrovamentoNonDenunciato = flagHasRitrovamentoNonDenunciato;
	}
	
	
	
	
	public boolean isFlagHasPassaporto() {
		return flagHasPassaporto;
	}
	public void setFlagHasPassaporto(boolean flagHasPassaporto) {
		this.flagHasPassaporto = flagHasPassaporto;
	}
	public int getIdSpecie() {
		return idSpecie;
	}
	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}	
	
	public boolean isFlagPrelievoDnaEffetuato() {
		return flagPrelievoDnaEffetuato;
	}
	public void setFlagPrelievoDnaEffetuato(boolean flagPrelievoDnaEffetuato) {
		this.flagPrelievoDnaEffetuato = flagPrelievoDnaEffetuato;
	}
	
	public boolean isFlagAllontanamentoEffettuato() {
		return flagAllontanamentoEffettuato;
	}
	public void setFlagAllontanamentoEffettuato(boolean flagAllontanamentoEffettuato) {
		this.flagAllontanamentoEffettuato = flagAllontanamentoEffettuato;
	}
	
	public boolean isFlagIncludiHd() {
		return flagIncludiHd;
	}
	public void setFlagIncludiHd(boolean flagIncludiHd) {
		this.flagIncludiHd = flagIncludiHd;
	}
	
	

	public boolean isCheckSoloRegistrazioniEffettuabiliFuoriAsl() {
		return checkSoloRegistrazioniEffettuabiliFuoriAsl;
	}
	
	
	public void setCheckSoloRegistrazioniEffettuabiliFuoriAsl(
			boolean checkSoloRegistrazioniEffettuabiliFuoriAsl) {
		this.checkSoloRegistrazioniEffettuabiliFuoriAsl = checkSoloRegistrazioniEffettuabiliFuoriAsl;
	}
	
	
	
	public boolean isCheckSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl() {
		return checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl;
	}
	public void setCheckSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl(
			boolean checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl) {
		this.checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl = checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl;
	}
	
	
	public boolean isFlagIsUtenteInCaricoPerFuoriDominio() {
		return flagIsUtenteInCaricoPerFuoriDominio;
	}
	public void setFlagIsUtenteInCaricoPerFuoriDominio(
			boolean flagIsUtenteInCaricoPerFuoriDominio) {
		this.flagIsUtenteInCaricoPerFuoriDominio = flagIsUtenteInCaricoPerFuoriDominio;
	}
	
	
	
	public boolean isFlagIsAslProprietariaConAnimaleFuoriDominioAsl() {
		return flagIsAslProprietariaConAnimaleFuoriDominioAsl;
	}
	public void setFlagIsAslProprietariaConAnimaleFuoriDominioAsl(
			boolean flagIsAslProprietariaConAnimaleFuoriDominioAsl) {
		this.flagIsAslProprietariaConAnimaleFuoriDominioAsl = flagIsAslProprietariaConAnimaleFuoriDominioAsl;
	}
	
	
	
	public boolean isFlagIsAslDiversaConAnimaleFuoriDominioAsl() {
		return flagIsAslDiversaConAnimaleFuoriDominioAsl;
	}
	public void setFlagIsAslDiversaConAnimaleFuoriDominioAsl(
			boolean flagIsAslDiversaConAnimaleFuoriDominioAsl) {
		this.flagIsAslDiversaConAnimaleFuoriDominioAsl = flagIsAslDiversaConAnimaleFuoriDominioAsl;
	}
	
	
	
	
	public boolean isFlagIsCatturaUltimaRegistrazioneFuoriDominioAsl() {
		return flagIsCatturaUltimaRegistrazioneFuoriDominioAsl;
	}
	public void setFlagIsCatturaUltimaRegistrazioneFuoriDominioAsl(
			boolean flagIsCatturaUltimaRegistrazioneFuoriDominioAsl) {
		this.flagIsCatturaUltimaRegistrazioneFuoriDominioAsl = flagIsCatturaUltimaRegistrazioneFuoriDominioAsl;
	}
	
	
	public boolean isFlagEscludiTrasferimentoCanileFuoriDominioAls() {
		return flagEscludiTrasferimentoCanileFuoriDominioAls;
	}
	public void setFlagEscludiTrasferimentoCanileFuoriDominioAls(
			boolean flagEscludiTrasferimentoCanileFuoriDominioAls) {
		this.flagEscludiTrasferimentoCanileFuoriDominioAls = flagEscludiTrasferimentoCanileFuoriDominioAls;
	}
	public RegistrazioniWKF(){
		
	}
	
	public RegistrazioniWKF(int idTipologiaRegistrazione, int idStato, Connection db) throws SQLException{
		
		StringBuffer sqlSelect = new StringBuffer( "Select wk.* from registrazioni_wk wk " +
				" where ((wk.id_stato = ? and wk.id_registrazione = ?) or (wk.id_stato = -1 and wk.id_registrazione = ?)) ");
		

		PreparedStatement pst = db.prepareStatement(sqlSelect.toString());
		pst.setInt(1, idStato);
		pst.setInt(2, idTipologiaRegistrazione);
		pst.setInt(3, idTipologiaRegistrazione);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()){
	    	this.setIdRegistrazione(rs.getInt("id_registrazione"));
	    	this.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
	    	//r_wkf.setDescrizioneRegistrazione(rs.getString("description"));
	    	this.setOnlyHd(rs.getBoolean("only_hd"));
			
		}
		
		
		
	}
	
	
	
	public boolean isOnlyHd() {
		return onlyHd;
	}
	public void setOnlyHd(boolean onlyHd) {
		this.onlyHd = onlyHd;
	}
	public  RegistrazioniWKF getProssimoStatoDaStatoPrecedenteERegistrazione(Connection con) throws SQLException{
		
		
		StringBuffer select = new StringBuffer("Select wk.*, s.* from registrazioni_wk wk inner join lookup_tipologia_stato as s " +
				"on (wk.id_prossimo_stato = s.code) ");
		
		
/*		if (idSpecie > -1){
			select.append(" left join " + mapping_specie_registrazione_table + " as mapping on (wk.id = mapping.id_registrazione where mapping.id_specie = ?) ");
		}*/
		
		
		select.append(" where ((wk.id_stato = ? and id_registrazione = ?) or (wk.id_stato = -1 and id_registrazione = ?))");
		

		
		
		PreparedStatement pst = con.prepareStatement(select.toString());
		int i = 0;
		
/*		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}
		*/
		pst.setInt(++i, this.idStato);
		pst.setInt(++i, this.idRegistrazione);
		pst.setInt(++i, this.idRegistrazione);
		

		
		ResultSet rs = DatabaseUtils.executeQuery(con, pst);

		if (rs.next()){
			
			
			this.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
			this.setDescrizioneProssimoStato(rs.getString("description"));
			
			
		}
		
		rs.close();
		if (pst != null) {
		  pst.close();
		}
		
		return this;
	}
	
public  RegistrazioniWKF getPrecedenteStatoDaStatoAttualeERegistrazione(Connection con) throws SQLException{
		
		
		StringBuffer select = new StringBuffer("Select wk.*, s.* from registrazioni_wk_revert wk inner join lookup_tipologia_stato as s " +
				"on (wk.id_prossimo_stato = s.code) ");
		
		
/*		if (idSpecie > -1){
			select.append(" left join " + mapping_specie_registrazione_table + " as mapping on (wk.id = mapping.id_registrazione where mapping.id_specie = ?) ");
		}*/
		
		
		select.append(" where ((wk.id_stato = ? and id_registrazione = ?) or (wk.id_stato = -1 and id_registrazione = ?))");
		

		
		
		PreparedStatement pst = con.prepareStatement(select.toString());
		int i = 0;
		
/*		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}
		*/
		pst.setInt(++i, this.idStato);
		pst.setInt(++i, this.idRegistrazione);
		pst.setInt(++i, this.idRegistrazione);
		

		
		ResultSet rs = DatabaseUtils.executeQuery(con, pst);

		if (rs.next()){
			
			
			this.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
			this.setDescrizioneProssimoStato(rs.getString("description"));
			
			
		}
		
		rs.close();
		if (pst != null) {
		  pst.close();
		}
		
		return this;
	}
	
	
	public void checkPossibilitaRegistrazioni(Connection con){
		ArrayList<RegistrazioniWKF> registrazioni = new ArrayList<RegistrazioniWKF>();
		try {
			registrazioni = this.getRegistrazioniDaStato(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (registrazioni.isEmpty()){
			this.flagPossibilitaRegistazioni = false;
		}
	}
	
	public ArrayList getRegistrazioni(Connection con) throws SQLException
	{
		ArrayList<RegistrazioniWKF> registrazioni = new ArrayList<RegistrazioniWKF>();
		
		StringBuffer select = new StringBuffer("Select wk.*, l.* from registrazioni_wk wk inner join lookup_tipologia_registrazione as l " +
				"on (wk.id_registrazione = l.code)");
		
		if (idSpecie > -1){
			select.append(" and l.code in (select id_registrazione from  " + mapping_specie_registrazione_table +" where id_specie = ?) ");
		}
		
		PreparedStatement pst = con.prepareStatement(select.toString());
		
		if (idSpecie > -1) {
			pst.setInt(1, idSpecie);
		}
		
	    ResultSet rs = DatabaseUtils.executeQuery(con, pst);
		
	    while (rs.next()){
	    	RegistrazioniWKF r_wkf = new RegistrazioniWKF();
	    	r_wkf.setIdRegistrazione(rs.getInt("id_registrazione"));
	    	//r_wkf.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
	    	r_wkf.setDescrizioneRegistrazione(rs.getString("description"));
	    	registrazioni.add(r_wkf);
	    }
		
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	    
		return registrazioni;
		
		
		
	}
	
	
	public ArrayList getRegistrazioniSpecie(Connection con) throws SQLException
	{
		ArrayList<RegistrazioniWKF> registrazioni = new ArrayList<RegistrazioniWKF>();
		
		StringBuffer select = new StringBuffer("Select distinct(l.code) as id_registrazione, l.description from lookup_tipologia_registrazione as l " +
				"left join mapping_registrazioni_specie_animale m on (m.id_registrazione = l.code) ");
		
		if (idSpecie > -1){
			select.append("  where m.id_specie = ? ");
		}
		
		PreparedStatement pst = con.prepareStatement(select.toString());
		
		if (idSpecie > -1) {
			pst.setInt(1, idSpecie);
		}
		
	    ResultSet rs = DatabaseUtils.executeQuery(con, pst);
		
	    while (rs.next()){
	    	RegistrazioniWKF r_wkf = new RegistrazioniWKF();
	    	r_wkf.setIdRegistrazione(rs.getInt("id_registrazione"));
	    	//r_wkf.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
	    	r_wkf.setDescrizioneRegistrazione(rs.getString("description"));
	    	registrazioni.add(r_wkf);
	    }
		
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	    
		return registrazioni;
		
		
		
	}
	
	
public ArrayList<RegistrazioniWKF> getRegistrazioniDaStato(Connection con) throws SQLException{
		
		ArrayList<RegistrazioniWKF> registrazioni = new ArrayList<RegistrazioniWKF>();
		
		StringBuffer sqlSelect = new StringBuffer( "Select wk.*, l.* from registrazioni_wk wk " +
				"inner join lookup_tipologia_registrazione as l " +
		"on (wk.id_registrazione = l.code) ");
		

		
		
		sqlSelect.append("  where  (id_stato = ? or id_stato = -1)");
		
		
		if (idSpecie > -1){
			sqlSelect.append(" and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = ?) ");
		}
		
		if (flagHasSecondoMicrochip){
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		
		if (flagHasPassaporto){
			sqlSelect.append(" and l.code not in (?) ");
		}else{
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (flagHasRitrovamentoNonDenunciato || flagHasDetenzioneInCanileDopoRitrovamento){
			sqlSelect.append(" and l.code not in (?) ");
		}
		if (!flagHasRitrovamentoNonDenunciato && !flagHasDetenzioneInCanileDopoRitrovamento){
			sqlSelect.append(" and l.code not in (?) and l.code not in (?) ");
		}

		
		
		if (flagPrelievoDnaEffetuato){
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (flagAllontanamentoEffettuato){
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (flagIncludiHd){
			sqlSelect.append(" and (only_hd = true or only_hd = false )");
		}else{
			sqlSelect.append(" and only_hd = false");
		}
		
		if (checkSoloRegistrazioniEffettuabiliFuoriAsl && (!checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl || !flagIsUtenteInCaricoPerFuoriDominio) ){
			sqlSelect.append(" and l.fuori_asl = true");
		}
		
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagIsUtenteInCaricoPerFuoriDominio){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true");
		}

		
		else if (flagIsAslProprietariaConAnimaleFuoriDominioAsl){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
		}else if (flagIsAslDiversaConAnimaleFuoriDominioAsl){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_diversa = true");
		}
		//SE NN SI TRATTA DI CATTURA FUORI ASL ELIMINO REIMMISSIONE E TRASF CANILE COME RICHIESTO
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagEscludiTrasferimentoCanileFuoriDominioAls){
			sqlSelect.append(" and l.code not in (?) ");
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (idStato == 81 ){
			sqlSelect.append(" and l.code not in (?) ");
		}
//		else if(!checkSoloRegistrazioniEffettuabiliFuoriAsl && !checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && !flagIsUtenteInCaricoPerFuoriDominio){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
//		}
//		}else if (!checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = false");
//		}
		
		sqlSelect.append(" order by l.description ");
		
		PreparedStatement pst = con.prepareStatement(sqlSelect.toString());
		
		int i = 0;
		
	    pst.setInt(++i, this.idStato);
		
		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}
		
		if (flagHasSecondoMicrochip){
			pst.setInt(++i, EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip); /**
																						*
																							Se l'animale ha già un secondo
																							microchip non devo aggiungere la registrazione
																							di secondo microchip*/
			}
		
		
		if (flagHasPassaporto){
			pst.setInt(++i, EventoRilascioPassaporto.idTipologiaDB); /**
			*
				Se l'animale ha già un passaporto
				 non devo aggiungere la registrazione
				di rilascio passaporto*/
		}else{
			pst.setInt(++i, EventoRilascioPassaporto.idTipologiaRinnovoDB); /**
			*
				Se l'animale non ha già un passaporto
				 non devo aggiungere la registrazione
				di rinnovo passaporto*/
		}
		
		if (flagHasRitrovamentoNonDenunciato || flagHasDetenzioneInCanileDopoRitrovamento ){
			pst.setInt(++i, EventoRitrovamentoNonDenunciato.idTipologiaDB);
		}
		if (!flagHasRitrovamentoNonDenunciato && !flagHasDetenzioneInCanileDopoRitrovamento){
			pst.setInt(++i, EventoRestituzioneAProprietario.idTipologiaDB);
			pst.setInt(++i, EventoRestituzioneAProprietario.idTipologiaRestituzioneACanileDB);
		}
		
		
		if (flagPrelievoDnaEffetuato){
			pst.setInt(++i, EventoPrelievoDNA.idTipologiaDB);
		}
		
		if (flagAllontanamentoEffettuato){
			pst.setInt(++i, EventoAllontanamento.idTipologiaDB);
		}
		
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagEscludiTrasferimentoCanileFuoriDominioAls){
			pst.setInt(++i, EventoReimmissione.idTipologiaDB);
			pst.setInt(++i, EventoTrasferimentoCanile.idTipologiaDB);
		}
		
		if (idStato == 81 ){//Se bloccato escludi evento di Blocco
			pst.setInt(++i, EventoBloccoAnimale.idTipologiaDB);
		}
	    
		System.out.println("Query verifica registrazioni possibili: " + pst);

	    ResultSet rs = DatabaseUtils.executeQuery(con, pst);
		
	    while (rs.next()){
	    	RegistrazioniWKF r_wkf = new RegistrazioniWKF();
	    	r_wkf.setIdRegistrazione(rs.getInt("id_registrazione"));
	    	r_wkf.setIdProssimoStato(rs.getInt("id_prossimo_stato"));
	    	r_wkf.setDescrizioneRegistrazione(rs.getString("description"));
	    	r_wkf.setOnlyHd(rs.getBoolean("only_hd"));
	    	if(rs.getString("title")==null)
	    		r_wkf.setTitle("");
	    	else
	    		r_wkf.setTitle(rs.getString("title"));
	    	registrazioni.add(r_wkf);
	    }
		
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	    
		return registrazioni;
		
		
		
	}
	
	
	public ArrayList<Integer> getRegistrazioniCodeDaStato(Connection con) throws SQLException{
		
		ArrayList<Integer> registrazioni = new ArrayList<Integer>();
		
		StringBuffer sqlSelect = new StringBuffer( "Select wk.*, l.* from registrazioni_wk wk " +
				"inner join lookup_tipologia_registrazione as l " +
		"on (wk.id_registrazione = l.code) ");
		

		
		
		sqlSelect.append("  where (id_stato = ? or id_stato = -1)");
		
		
		if (idSpecie > -1){
			sqlSelect.append(" and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = ?) ");
		}
		
		if (flagHasSecondoMicrochip){
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		
		if (flagHasPassaporto){
			sqlSelect.append(" and l.code not in (?) ");
		}else{
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (flagHasRitrovamentoNonDenunciato || flagHasDetenzioneInCanileDopoRitrovamento){
			sqlSelect.append(" and l.code not in (?) ");
		}
		if (!flagHasRitrovamentoNonDenunciato && !flagHasDetenzioneInCanileDopoRitrovamento){
			sqlSelect.append(" and l.code not in (?) and l.code not in (?) ");
		}

		
		
		if (flagPrelievoDnaEffetuato){
			sqlSelect.append(" and l.code not in (?) ");
		}
		

		if (flagAllontanamentoEffettuato){
			sqlSelect.append(" and l.code not in (?) ");
		}
		
		if (flagIncludiHd){
			sqlSelect.append(" and (only_hd = true or only_hd = false )");
		}else{
			sqlSelect.append(" and only_hd = false");
		}
		
		if (checkSoloRegistrazioniEffettuabiliFuoriAsl && (!checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl || !flagIsUtenteInCaricoPerFuoriDominio) ){
			sqlSelect.append(" and l.fuori_asl = true");
		}
		
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagIsUtenteInCaricoPerFuoriDominio){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true");
		}
		
		else if (flagIsAslProprietariaConAnimaleFuoriDominioAsl){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
		}else if (flagIsAslDiversaConAnimaleFuoriDominioAsl){
			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_diversa = true");
		}
		
		
		//SE NN SI TRATTA DI CATTURA FUORI ASL ELIMINO REIMMISSIONE E TRASF CANILE COME RICHIESTO
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagEscludiTrasferimentoCanileFuoriDominioAls){
			sqlSelect.append(" and l.code not in (?) ");
			sqlSelect.append(" and l.code not in (?) ");
		}
		
//		else if (!checkSoloRegistrazioniEffettuabiliFuoriAsl && checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && !flagIsUtenteInCaricoPerFuoriDominio){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
//		} 
//		else if(!checkSoloRegistrazioniEffettuabiliFuoriAsl && !checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && !flagIsUtenteInCaricoPerFuoriDominio){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
//		}
//		
//		if (checkSoloRegistrazioniEffettuabiliFuoriAsl && (!checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl || !flagIsUtenteInCaricoPerFuoriDominio)){
//			sqlSelect.append(" and l.fuori_asl = true");
//		}
//		
//		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagIsUtenteInCaricoPerFuoriDominio){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true");
//		}else if (!checkSoloRegistrazioniEffettuabiliFuoriAsl && checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && !flagIsUtenteInCaricoPerFuoriDominio){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true");
//		}else if (!checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl){
//			sqlSelect.append(" and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = false");
//		}
		
		PreparedStatement pst = con.prepareStatement(sqlSelect.toString());
		
		int i = 0;
		
	    pst.setInt(++i, this.idStato);
		
		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}
		
		if (flagHasSecondoMicrochip){
			pst.setInt(++i, EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip); /**
																						*
																							Se l'animale ha già un secondo
																							microchip non devo aggiungere la registrazione
																							di secondo microchip*/
			}
		
		
		if (flagHasPassaporto){
			pst.setInt(++i, EventoRilascioPassaporto.idTipologiaDB); /**
			*
				Se l'animale ha già un passaporto
				 non devo aggiungere la registrazione
				di rilascio passaporto*/
		}else{
			pst.setInt(++i, EventoRilascioPassaporto.idTipologiaRinnovoDB); /**
			*
				Se l'animale non ha già un passaporto
				 non devo aggiungere la registrazione
				di rinnovo passaporto*/
		}
		
		if (flagHasRitrovamentoNonDenunciato || flagHasDetenzioneInCanileDopoRitrovamento ){
			pst.setInt(++i, EventoRitrovamentoNonDenunciato.idTipologiaDB);
		}
		if (!flagHasRitrovamentoNonDenunciato && !flagHasDetenzioneInCanileDopoRitrovamento){
			pst.setInt(++i, EventoRestituzioneAProprietario.idTipologiaDB);
			pst.setInt(++i, EventoRestituzioneAProprietario.idTipologiaRestituzioneACanileDB);
		}
		
		
		
		if (flagPrelievoDnaEffetuato){
			pst.setInt(++i, EventoPrelievoDNA.idTipologiaDB); /**
			*
				Se l'animale ha già una registrazione di prelievo DNA
				 non devo aggiungere la registrazione
				di prelievo DNA*/
		}
		
		if (flagAllontanamentoEffettuato){
			pst.setInt(++i, EventoAllontanamento.idTipologiaDB); /**
			*
				Se l'animale ha già una registrazione di allontanamento
				 non devo aggiungere la registrazione
				di allontanamento*/
		}
		
		if (checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl && flagEscludiTrasferimentoCanileFuoriDominioAls){
			pst.setInt(++i, EventoReimmissione.idTipologiaDB);
			pst.setInt(++i, EventoTrasferimentoCanile.idTipologiaDB);
		}

	    

	    ResultSet rs = DatabaseUtils.executeQuery(con, pst);
		
	    while (rs.next()){

	    	registrazioni.add(rs.getInt("id_registrazione"));
	    }
		
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	    
		return registrazioni;
		
		
		
	}
	
	
	public void buildWkfDati(ActionContext context, Animale thisAnimale, boolean hasPermissionRegPregresse, UserBean user, boolean isAslAnimale,  Connection db) throws SQLException{
		
		this.setIdStato(thisAnimale.getStato());
		this.setIdSpecie(thisAnimale.getIdSpecie());
		this.setFlagIncludiHd(hasPermissionRegPregresse);
		this.setCheckSoloRegistrazioniEffettuabiliFuoriAsl((!isAslAnimale && !thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl()));
		//SE HO UN ANIMALE LA CUI ULTIMA OPERAZIONE E' STATA ESEGUITA DA FUORI ASL E L'UTENTE CORRENTE E' DI QUELLA ASL ALLORA ABILITO SOLO LE POSSIBILI REGISTRAZIONI
		this.setCheckSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl(thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl());
		this.setFlagIsUtenteInCaricoPerFuoriDominio(thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() > 0 && thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() == user.getSiteId());
		this.setFlagIsAslProprietariaConAnimaleFuoriDominioAsl(thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl() && thisAnimale.getIdAslRiferimento() == user.getSiteId());
		this.setFlagIsAslDiversaConAnimaleFuoriDominioAsl(thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl() && 
				thisAnimale.getIdAslRiferimento() != user.getSiteId() && thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() != user.getSiteId());
		

		if (thisAnimale.getTatuaggio() != null
				&& !("").equals(thisAnimale.getTatuaggio())) {
			this.setFlagHasSecondoMicrochip(true);
		}

		if (thisAnimale.getNumeroPassaporto() != null
				&& !("").equals(thisAnimale.getNumeroPassaporto())) {
			this.setFlagHasPassaporto(true);
		}

		if (thisAnimale.cercaRitrovamentoNonDenunciatoAperto(db) > 0) {
			this.setFlagHasRitrovamentoNonDenunciato(true);
		}
		
		if (thisAnimale.isFlagDetenutoInCanileDopoRitrovamento()){
			this.setFlagHasDetenzioneInCanileDopoRitrovamento(true); //il cane è stato ritrovato e affidato a un canile, devo poter restituire a prop
		}
		
		if (thisAnimale.getIdSpecie() == Cane.idSpecie && thisAnimale.getFlagPrelievoDnaEffettuato(db) == true){
			this.setFlagPrelievoDnaEffetuato(true);
			
		}
		
		if (thisAnimale.getFlagAllontanamentoEffettuato(db) == true){
			this.setFlagAllontanamentoEffettuato(true);
			
		}
		if (thisAnimale.getIdTipologiaUltimaOperazioneFuoriDominioAsl() == EventoCattura.idTipologiaDBRicattura && thisAnimale.isFlagCattura()){
			this.setFlagIsCatturaUltimaRegistrazioneFuoriDominioAsl(true);
		}
		
		if (thisAnimale.getIdTipologiaUltimaOperazioneFuoriDominioAsl() ==  EventoCattura.idTipologiaDBRicattura && thisAnimale.isFlagCattura() ){
			this.setFlagEscludiTrasferimentoCanileFuoriDominioAls(true);
		}
		
		
	}
}
