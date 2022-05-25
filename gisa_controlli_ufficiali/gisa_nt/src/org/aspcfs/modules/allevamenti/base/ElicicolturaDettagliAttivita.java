/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;

public class ElicicolturaDettagliAttivita {

	private String dtUltimaModifica;
    private String elidetattId;
    private String specieId;
    private String specieCodice;
    private String specieDescrizione;
    private String oriproId;
    private String oriproCodice;
    private String oriproDescrizione;
    private String modallId;
    private String modallCodice;
    private String modallDescrizione;
    private String propId;
    private String propIdFiscale;
    private String propCognNome;
    private String dtInizioAttivita;
    private String dtFineAttivita;
    private String note;
    private String dDtInserimentoBdn;
    private String aslCodice;
    private String tipattCodice; 
	
    private ArrayList<ElicicolturaAttivita> attivita = new ArrayList<ElicicolturaAttivita>();

	public ElicicolturaDettagliAttivita(JSONObject explrObject) {
		
		try {
			
			this.dtUltimaModifica = explrObject.getString("dtUltimaModifica");
		    this.elidetattId = explrObject.getString("elidetattId");
		    this.specieId = explrObject.getString("specieId");
		    this.specieCodice = explrObject.getString("specieCodice");
		    this.specieDescrizione = explrObject.getString("specieDescrizione");
		    this.oriproId = explrObject.getString("oriproId");
		    this.oriproCodice = explrObject.getString("oriproCodice");
		    this.oriproDescrizione = explrObject.getString("oriproDescrizione");
		    this.modallId = explrObject.getString("modallId");
		    this.modallCodice = explrObject.getString("modallCodice");
		    this.modallDescrizione = explrObject.getString("modallDescrizione");
		    this.propId = explrObject.getString("propId");
		    this.propIdFiscale = explrObject.getString("propIdFiscale");
		    this.propCognNome = explrObject.getString("propCognNome");
		    this.dtInizioAttivita = explrObject.getString("dtInizioAttivita");
		    this.dtFineAttivita = explrObject.getString("dtFineAttivita");
		    this.note = explrObject.getString("note");
		    this.dDtInserimentoBdn = explrObject.getString("dDtInserimentoBdn");
		    this.aslCodice = explrObject.getString("aslCodice");
		    this.tipattCodice = explrObject.getString("tipattCodice"); 
		    
		    JSONArray jsonArray = new JSONArray(explrObject.getString("attivita"));
		
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject explrObject2 = jsonArray.getJSONObject(i);
				ElicicolturaAttivita att = new ElicicolturaAttivita(explrObject2);
				attivita.add(att);
				}
			
		} catch (NoSuchElementException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    }

	public String getDtUltimaModifica() {
		return dtUltimaModifica;
	}

	public void setDtUltimaModifica(String dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}

	public String getElidetattId() {
		return elidetattId;
	}

	public void setElidetattId(String elidetattId) {
		this.elidetattId = elidetattId;
	}

	public String getSpecieId() {
		return specieId;
	}

	public void setSpecieId(String specieId) {
		this.specieId = specieId;
	}

	public String getSpecieCodice() {
		return specieCodice;
	}

	public void setSpecieCodice(String specieCodice) {
		this.specieCodice = specieCodice;
	}

	public String getSpecieDescrizione() {
		return specieDescrizione;
	}

	public void setSpecieDescrizione(String specieDescrizione) {
		this.specieDescrizione = specieDescrizione;
	}

	public String getOriproId() {
		return oriproId;
	}

	public void setOriproId(String oriproId) {
		this.oriproId = oriproId;
	}

	public String getOriproCodice() {
		return oriproCodice;
	}

	public void setOriproCodice(String oriproCodice) {
		this.oriproCodice = oriproCodice;
	}

	public String getOriproDescrizione() {
		return oriproDescrizione;
	}

	public void setOriproDescrizione(String oriproDescrizione) {
		this.oriproDescrizione = oriproDescrizione;
	}

	public String getModallId() {
		return modallId;
	}

	public void setModallId(String modallId) {
		this.modallId = modallId;
	}

	public String getModallCodice() {
		return modallCodice;
	}

	public void setModallCodice(String modallCodice) {
		this.modallCodice = modallCodice;
	}

	public String getModallDescrizione() {
		return modallDescrizione;
	}

	public void setModallDescrizione(String modallDescrizione) {
		this.modallDescrizione = modallDescrizione;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public String getPropIdFiscale() {
		return propIdFiscale;
	}

	public void setPropIdFiscale(String propIdFiscale) {
		this.propIdFiscale = propIdFiscale;
	}

	public String getPropCognNome() {
		return propCognNome;
	}

	public void setPropCognNome(String propCognNome) {
		this.propCognNome = propCognNome;
	}

	public String getDtInizioAttivita() {
		return dtInizioAttivita;
	}

	public void setDtInizioAttivita(String dtInizioAttivita) {
		this.dtInizioAttivita = dtInizioAttivita;
	}

	public String getDtFineAttivita() {
		return dtFineAttivita;
	}

	public void setDtFineAttivita(String dtFineAttivita) {
		this.dtFineAttivita = dtFineAttivita;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getdDtInserimentoBdn() {
		return dDtInserimentoBdn;
	}

	public void setdDtInserimentoBdn(String dDtInserimentoBdn) {
		this.dDtInserimentoBdn = dDtInserimentoBdn;
	}

	public String getAslCodice() {
		return aslCodice;
	}

	public void setAslCodice(String aslCodice) {
		this.aslCodice = aslCodice;
	}

	public String getTipattCodice() {
		return tipattCodice;
	}

	public void setTipattCodice(String tipattCodice) {
		this.tipattCodice = tipattCodice;
	}

	public ArrayList<ElicicolturaAttivita> getAttivita() {
		return attivita;
	}

	public void setAttivita(ArrayList<ElicicolturaAttivita> attivita) {
		this.attivita = attivita;
	}

	
	
}
