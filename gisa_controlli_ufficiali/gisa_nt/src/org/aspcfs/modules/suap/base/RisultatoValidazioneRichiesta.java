/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.util.HashMap;

import com.darkhorseventures.framework.beans.GenericBean;

public class RisultatoValidazioneRichiesta extends GenericBean {
	
	private int idRisultato ;
	private String descrizioneErrore ;
	private HashMap<Integer, Stabilimento> listaAnagraficheCandidate = new HashMap<Integer,Stabilimento>();
	private int idStabilimentoTrovato =-1 ;
	private boolean altEseguitaRicercaGlobale;
	
	private String color;
	
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isAltEseguitaRicercaGlobale() {
		return altEseguitaRicercaGlobale;
	}
	public void setAltEseguitaRicercaGlobale(boolean altEseguitaRicercaGlobale) {
		this.altEseguitaRicercaGlobale = altEseguitaRicercaGlobale;
	}
	public int getIdRisultato() {
		return idRisultato;
	}
	public void setIdRisultato(int idRisultato) {
		this.idRisultato = idRisultato;
	}
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}
	public HashMap<Integer, Stabilimento> getListaAnagraficheCandidate() {
		return listaAnagraficheCandidate;
	}
	public void setListaAnagraficheCandidate(HashMap<Integer, Stabilimento> listaAnagraficheCandidate) {
		this.listaAnagraficheCandidate = listaAnagraficheCandidate;
	}
	public int getIdStabilimentoTrovato() {
		return idStabilimentoTrovato;
	}
	public void setIdStabilimentoTrovato(int idStabilimentoTrovato) {
		this.idStabilimentoTrovato = idStabilimentoTrovato;
	}
	
	

}
