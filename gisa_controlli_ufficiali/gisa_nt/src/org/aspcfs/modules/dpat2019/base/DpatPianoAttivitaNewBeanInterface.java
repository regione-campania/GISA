/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.beans.GenericBean;

public abstract class DpatPianoAttivitaNewBeanInterface <T extends DpatIndicatoreNewBeanAbstract > extends GenericBean implements Cloneable
{
	private static final long serialVersionUID = 1L;

	Long oid;
	String descrizione;
	Integer anno;
	Integer ordine;
	Integer codiceRaggruppamento;
	Timestamp scadenza;
	Long oidSezione;
	String codiceEsame;
	String codiceAlias;
	String tipoAttivita;
	String codiceAsl;
	String tipoInserimento;
	Integer codiceInternoPiano;
	String descrSezMadre; /*per comodita' di visualizzazione nello storico (sezione>pianoattivita) */
	Integer codiceInternoAttivita;
	String aliasPiano;
	String aliasAttivita;
	String codiceAliasAttivita;
	Integer stato;
	ArrayList<T> indicatoriFigli = new ArrayList<T>();

	public String getDescrSezMadre()
	{
		return descrSezMadre;
	}

	public void setDescrSezMadre(String descr)
	{
		this.descrSezMadre = descr;
	}

	public Integer getCodiceInternoPiano() {
		return codiceInternoPiano;
	}
	public void setCodiceInternoPiano(Integer codiceInternoPiano) {
		this.codiceInternoPiano = codiceInternoPiano;
	}
	public Integer getCodiceInternoAttivita() {
		return codiceInternoAttivita;
	}
	public void setCodiceInternoAttivita(Integer codiceInternoAtativita) {
		this.codiceInternoAttivita = codiceInternoAtativita;
	}
	public String getAliasPiano() {
		return aliasPiano;
	}
	public void setAliasPiano(String aliasPiano) {
		this.aliasPiano = aliasPiano;
	}
	public String getAliasAttivita() {
		return aliasAttivita;
	}
	public void setAliasAttivita(String aliasAttivita) {
		this.aliasAttivita = aliasAttivita;
	}
	public String getCodiceAliasAttivita() {
		return codiceAliasAttivita;
	}
	public void setCodiceAliasAttivita(String codiceAliasAttivita) {
		this.codiceAliasAttivita = codiceAliasAttivita;
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

	public ArrayList<T> getIndicatoriFigli() {
		return indicatoriFigli;
	}
	public void setIndicatoriFigli(ArrayList<T> indicatoriFigli) {
		this.indicatoriFigli = indicatoriFigli;
	}
	public Long getOidSezione() {
		return oidSezione;
	}
	public void setOidSezione(Long oidSezione) {
		this.oidSezione = oidSezione;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
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
	
	public void setStato(Integer stato) {
		this.stato = stato;
	}
	public Integer getStato()
	{
		return this.stato;
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
		toRet.put("oid_sezione", getOidSezione()+"");
		toRet.put("codice_esame", getCodiceEsame()+"");
		toRet.put("codice_alias", getCodiceAlias()+"");
		toRet.put("tipo_attivita", getTipoAttivita()+"");
		toRet.put("codice_asl", getCodiceAsl()+"");
		toRet.put("codice_interno_piano", getCodiceInternoPiano()+"");
		toRet.put("codice_interno_attivita", getCodiceInternoAttivita()+"");
		toRet.put("alias_piano", getAliasPiano());
		toRet.put("alias_attivita", getAliasAttivita());
		toRet.put("codice_alias_attivita", getCodiceAliasAttivita());
		toRet.put("stato", getStato()+"");
		 
		
		JSONArray figli = new JSONArray();
		for(T bean : indicatoriFigli)
		{
			figli.put(bean.getJsonObj());
		}

		toRet.put("figli", figli);
		
		return toRet;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public abstract ArrayList<? extends DpatPianoAttivitaNewBeanInterface<T>> buildList(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds) throws Exception;
	public abstract ArrayList<? extends DpatPianoAttivitaNewBeanInterface<T>> searchVersioniPerCodiceRaggruppamento(Connection conn, Integer codRaggruppamento, boolean withChilds, int anno) throws Exception;
	public abstract DpatPianoAttivitaNewBeanInterface<T> buildByOid(Connection conn, Integer oid, boolean nonscaduti, boolean withChilds) throws Exception;
	public abstract DpatPianoAttivitaNewBeanInterface<T> build(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds) throws Exception;
	public abstract DpatPianoAttivitaNewBeanInterface<T> searchLastChildOf(Connection db, Integer oidSezione,boolean nonscaduti) throws Exception ;
	
}
