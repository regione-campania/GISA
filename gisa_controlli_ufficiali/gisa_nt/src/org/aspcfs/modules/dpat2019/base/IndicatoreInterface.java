/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONObject;

import com.darkhorseventures.framework.beans.GenericBean;


public abstract class IndicatoreInterface extends GenericBean{

	Long oid;
	String descrizione;
	Integer anno;
	Integer ordine;
	Integer codiceRaggruppamento;
	Timestamp scadenza;
	Long oidPianoAttivita;
	String codiceEsame;
	String codiceAlias;
	String tipoAttivita;
	String codiceAsl;
	Integer codiceInternoIndicatore;
	Integer tipoItemDpat;
	String descrSezMadre; 
	String aliasIndicatore;
	Integer codiceInternoPianiGestioneCu;
	String codiceInternoAttivitaGestioneCu;
	String codiceInternoUnivocoTipoAttivitaGestioneCu;
	String codiceAliasIndicatore;
	Integer stato;
	String descrPianoAttivitaPadre;
	String codiceAliasPadre;
	Integer idPianoAttivita;
	String tipoInserimento;
	Boolean rendic_per_campioni;
	
	public Boolean getRendic_per_campioni() {
		return rendic_per_campioni;
	}

	public void setRendic_per_campioni(Boolean rendic_per_campioni) {
		this.rendic_per_campioni = rendic_per_campioni;
	}

	public String getDescrSezMadre()
	{
		return descrSezMadre;
	}

	public void setDescrSezMadre(String descr)
	{
		this.descrSezMadre = descr;
	}

	public String getDescrPianoAttivitaPadre( )
	{
		return this.descrPianoAttivitaPadre;
	}
	public void setDescrPianoAttivitaPadre(String descr)
	{
		this.descrPianoAttivitaPadre = descr;
	}
	
	public String getCodiceAliasPadre( )
	{
		return this.codiceAliasPadre;
	}
	public void setCodiceAliasPadre(String codiceAliasPadre)
	{
		this.codiceAliasPadre = codiceAliasPadre;
	}
	
	public Integer getIdPianoAttivita( )
	{
		return this.idPianoAttivita;
	}
	public void setIdPianoAttivita(Integer idPianoAttivita)
	{
		this.idPianoAttivita = idPianoAttivita;
	}

	public Integer getCodiceInternoIndicatore() {
		return codiceInternoIndicatore;
	}
	public void setCodiceInternoIndicatore(Integer codiceInternoIndicatore) {
		this.codiceInternoIndicatore = codiceInternoIndicatore;
	}
	public Integer getTipoItemDpat() {
		return tipoItemDpat;
	}
	public void setTipoItemDpat(Integer tipoItemDpat) {
		this.tipoItemDpat = tipoItemDpat;
	}
	public String getAliasIndicatore() {
		return aliasIndicatore;
	}
	public void setAliasIndicatore(String aliasIndicatore) {
		this.aliasIndicatore = aliasIndicatore;
	}
	public Integer getCodiceInternoPianiGestioneCu() {
		return codiceInternoPianiGestioneCu;
	}
	public void setCodiceInternoPianiGestioneCu(Integer codiceInternoPianiGestioneCu) {
		this.codiceInternoPianiGestioneCu = codiceInternoPianiGestioneCu;
	}
	public String getCodiceInternoAttivitaGestioneCu() {
		return codiceInternoAttivitaGestioneCu;
	}
	public void setCodiceInternoAttivitaGestioneCu(String codiceInternoAttivitaGestioneCu) {
		this.codiceInternoAttivitaGestioneCu = codiceInternoAttivitaGestioneCu;
	}
	public String getCodiceInternoUnivocoTipoAttivitaGestioneCu() {
		return codiceInternoUnivocoTipoAttivitaGestioneCu;
	}
	public void setCodiceInternoUnivocoTipoAttivitaGestioneCu(String codiceInternoUnivocoTipoAttivitaGestioneCu) {
		this.codiceInternoUnivocoTipoAttivitaGestioneCu = codiceInternoUnivocoTipoAttivitaGestioneCu;
	}
	public String getCodiceAliasIndicatore() {
		return codiceAliasIndicatore;
	}
	public void setCodiceAliasIndicatore(String codiceAliasIndicatore) {
		this.codiceAliasIndicatore = codiceAliasIndicatore;
	}


	public String getTipoInserimento() {
		return tipoInserimento;
	}
	public void setTipoInserimento(String tipoInserimento) {
		this.tipoInserimento = tipoInserimento;
	}


	public String getCodiceAsl() {
		return codiceAsl;
	}
	public void setCodiceAsl(String codiceAsl) {
		this.codiceAsl = codiceAsl;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public String getCodiceAlias() {
		return codiceAlias;
	}
	public void setCodiceAlias(String codiceAlias) {
		this.codiceAlias = codiceAlias;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getOidPianoAttivita() {
		return oidPianoAttivita;
	}
	public void setOidPianoAttivita(Long oidPianoAttivita) {
		this.oidPianoAttivita = oidPianoAttivita;
	}

	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public Integer getOrdine() {
		return ordine;
	}
	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}
	public Integer getCodiceRaggruppamento() {
		return codiceRaggruppamento;
	}
	public void setCodiceRaggruppamento(Integer codiceRaggruppamento) {
		this.codiceRaggruppamento = codiceRaggruppamento;
	}
	public Timestamp getScadenza() {
		return scadenza;
	}
	public void setScadenza(Timestamp scadenza) {
		this.scadenza = scadenza;
	}
	public String getCodiceEsame() {
		return codiceEsame;
	}
	public void setCodiceEsame(String codiceEsame) {
		this.codiceEsame = codiceEsame;
	}
	public void setStato(int stato) {
		this.stato = stato;

	}
	public Integer getStato()
	{
		return stato;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	public JSONObject getJsonObj()
	{
		JSONObject toRet = new JSONObject();
		toRet.put("id", getOid()+"");
		toRet.put("descrizione", getDescrizione()+"");
		toRet.put("anno", getAnno()+"");
		toRet.put("ordine", getOrdine()+"");
		toRet.put("codiceRaggruppamento", getCodiceRaggruppamento()+"");
		toRet.put("scadenza", getScadenza()+"");
		toRet.put("oid_piano_attivita", getOidPianoAttivita()+"");
		toRet.put("codice_esame", getCodiceEsame()+"");
		toRet.put("codice_alias", getCodiceAlias()+"");
		toRet.put("tipo_attivita", getTipoAttivita()+"");
		toRet.put("codice_asl", getCodiceAsl()+"");
		toRet.put("stato", getStato());
		 
		return toRet;
	}
	
	public abstract IndicatoreInterface buildByOid(Connection conn, Integer oid,boolean nonscaduti);
	public abstract IndicatoreInterface build(Connection conn, ResultSet rs) throws Exception;
	public abstract ArrayList<? extends IndicatoreInterface> buildList(Connection conn, ResultSet rs) throws Exception;
	public abstract ArrayList<? extends IndicatoreInterface> searchVersioniPerCodiceRaggruppamento(Connection conn,Integer codiceRaggruppamento,int anno ) throws Exception;
	public abstract Integer insertDummyChildPerPianoAttivitaScelto(Connection db, Integer anno, Long pianoAttivitaPadre,Integer statoInput, Integer userId) throws Exception;
	public abstract Integer insertDummyChildPerUltimoPianoAttivitaInserito(Connection db,Integer anno,Integer userId) throws Exception;
	public abstract Integer insertBeforeOrAfter(Connection db, IndicatoreInterface toInsert, String where,Integer statoInput,String statoIncrementaOrdine, int idPianoAttivita, int anno, int ordinamento,Integer userId) throws Exception;
	public abstract void incrementaOrdiniPerPianoAttivita(Connection conn, String condition, Integer oidPianoAttivita, Integer anno,String stato,Integer userId);
	public abstract void disabilitaByOid(Connection conn, int id,int anno, String stato, String statoFigli, String statoSezione, Integer userId ) throws Exception ;
	public abstract void disabilitaByOid(Connection conn, int id,int anno,Integer userId ) throws Exception;
	public abstract Integer update(IndicatoreInterface newValue, IndicatoreInterface indicatoreRiferimento, Connection db, String stato, String statoFigli, String statoSezione,Integer statoInput,String statoIncrementaOrdine, boolean congelato,Integer userId) throws Exception ;
	public abstract int deleteDummyBrother(Connection db, IndicatoreInterface indicatoreRiferimento,String stato) throws Exception;
	public abstract IndicatoreInterface searchLastChildOf(Connection db, Integer oidPianoAttivita,boolean nonscaduti ) throws Exception;
	public abstract Integer spostaCompetenzeFromTo(Long oidFrom, Long oidTo,Connection conn) throws Exception ;
	public abstract int aggiornaCodiceAliasIndicatorePerTutteVersioni(Connection db, IndicatoreInterface bean, Integer userId) throws Exception;
	public abstract void deleteByOid(Connection conn, int id) throws Exception;
}
