/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.parafarmacie.base;

public class RigaAllegatoI {
	private int id ;
	private int idAsl ;
	private String descrizioneAsl;
	private int anno ;
	
	private int violazioni_amministrative;
	private int denunce_aut_giusiziarie;
	private int sequestri_amministrativi;
	private int sequestri_giudiziari; 
	private int non_conformita_campionamento;
	private int n_operatori_piu_uno_controlli;
	private int n_operatori_piu_due_controlli;
		
	private int n_operatori;
	private int n_ispezioni_effettuate;
	private int riga;
	private String descrizioneRiga;
	private int stato;
	
	
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRiga() {
		return riga;
	}
	public void setRiga(int riga) {
		this.riga = riga;
	}
	public String getDescrizioneRiga() {
		return descrizioneRiga;
	}
	public void setDescrizioneRiga(String descrizioneRiga) {
		this.descrizioneRiga = descrizioneRiga;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAl) {
		this.idAsl = idAl;
	}
	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}
	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getN_operatori() {
		return n_operatori;
	}
	public void setN_operatori(int n_operatori) {
		this.n_operatori = n_operatori;
	}
	public int getN_ispezioni_effettuate() {
		return n_ispezioni_effettuate;
	}
	public void setN_ispezioni_effettuate(int n_ispezioni_effettuate) {
		this.n_ispezioni_effettuate = n_ispezioni_effettuate;
	}
	public int getViolazioni_amministrative() {
		return violazioni_amministrative;
	}
	public void setViolazioni_amministrative(int violazioni_amministrative) {
		this.violazioni_amministrative = violazioni_amministrative;
	}
	public int getDenunce_aut_giusiziarie() {
		return denunce_aut_giusiziarie;
	}
	public void setDenunce_aut_giusiziarie(int denunce_aut_giusiziarie) {
		this.denunce_aut_giusiziarie = denunce_aut_giusiziarie;
	}
	public int getSequestri_amministrativi() {
		return sequestri_amministrativi;
	}
	public void setSequestri_amministrativi(int sequestri_amministrativi) {
		this.sequestri_amministrativi = sequestri_amministrativi;
	}
	public int getSequestri_giudiziari() {
		return sequestri_giudiziari;
	}
	public void setSequestri_giudiziari(int sequestri_giudiziari) {
		this.sequestri_giudiziari = sequestri_giudiziari;
	}
	public int getNon_conformita_campionamento() {
		return non_conformita_campionamento;
	}
	public void setNon_conformita_campionamento(int non_conformita_campionamento) {
		this.non_conformita_campionamento = non_conformita_campionamento;
	}
	public int getN_operatori_piu_uno_controlli() {
		return n_operatori_piu_uno_controlli;
	}
	public void setN_operatori_piu_uno_controlli(int n_operatori_piu_uno_controlli) {
		this.n_operatori_piu_uno_controlli = n_operatori_piu_uno_controlli;
	}
	public int getN_operatori_piu_due_controlli() {
		return n_operatori_piu_due_controlli;
	}
	public void setN_operatori_piu_due_controlli(int n_operatori_piu_due_controlli) {
		this.n_operatori_piu_due_controlli = n_operatori_piu_due_controlli;
	}
	
	
}
