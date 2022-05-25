/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sanzioni.base;

import java.sql.Connection;
import java.sql.Timestamp;

public class RegistroOrdinanza {

	int id  ; 
	int id_sanzione ;
	Timestamp data_protocollo_entrata_pv ;
	String fi_assegnatario ;
	boolean effettuato_sequestro ; 
	String num_sequestro  ;
	boolean presentata_opposizione_sequestro ;
	boolean presentazione_accolta ;
	boolean presentati_scritti_difensivi ;
	boolean presentata_richiesta_riduzione ;
	boolean presentata_richiesta_audizione ;
	Timestamp data_audizione ;
	boolean argomentazioni_accolte ;
	Double importo_sanzione_ingiunta ;
	Timestamp data_emissione_mod4d_5d ;
	double giorni_lavorazione_pratica ;
	String note ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_sanzione() {
		return id_sanzione;
	}
	public void setId_sanzione(int id_sanzione) {
		this.id_sanzione = id_sanzione;
	}
	public Timestamp getData_protocollo_entrata_pv() {
		return data_protocollo_entrata_pv;
	}
	public void setData_protocollo_entrata_pv(Timestamp data_protocollo_entrata_pv) {
		this.data_protocollo_entrata_pv = data_protocollo_entrata_pv;
	}
	public String getFi_assegnatario() {
		return fi_assegnatario;
	}
	public void setFi_assegnatario(String fi_assegnatario) {
		this.fi_assegnatario = fi_assegnatario;
	}
	public boolean isEffettuato_sequestro() {
		return effettuato_sequestro;
	}
	public void setEffettuato_sequestro(boolean effettuato_sequestro) {
		this.effettuato_sequestro = effettuato_sequestro;
	}
	public String getNum_sequestro() {
		return num_sequestro;
	}
	public void setNum_sequestro(String num_sequestro) {
		this.num_sequestro = num_sequestro;
	}
	public boolean isPresentata_opposizione_sequestro() {
		return presentata_opposizione_sequestro;
	}
	public void setPresentata_opposizione_sequestro(
			boolean presentata_opposizione_sequestro) {
		this.presentata_opposizione_sequestro = presentata_opposizione_sequestro;
	}
	public boolean isPresentazione_accolta() {
		return presentazione_accolta;
	}
	public void setPresentazione_accolta(boolean presentazione_accolta) {
		this.presentazione_accolta = presentazione_accolta;
	}
	public boolean isPresentati_scritti_difensivi() {
		return presentati_scritti_difensivi;
	}
	public void setPresentati_scritti_difensivi(boolean presentati_scritti_difensivi) {
		this.presentati_scritti_difensivi = presentati_scritti_difensivi;
	}
	public boolean isPresentata_richiesta_riduzione() {
		return presentata_richiesta_riduzione;
	}
	public void setPresentata_richiesta_riduzione(
			boolean presentata_richiesta_riduzione) {
		this.presentata_richiesta_riduzione = presentata_richiesta_riduzione;
	}
	public boolean isPresentata_richiesta_audizione() {
		return presentata_richiesta_audizione;
	}
	public void setPresentata_richiesta_audizione(
			boolean presentata_richiesta_audizione) {
		this.presentata_richiesta_audizione = presentata_richiesta_audizione;
	}
	public Timestamp getData_audizione() {
		return data_audizione;
	}
	public void setData_audizione(Timestamp data_audizione) {
		this.data_audizione = data_audizione;
	}
	public boolean isArgomentazioni_accolte() {
		return argomentazioni_accolte;
	}
	public void setArgomentazioni_accolte(boolean argomentazioni_accolte) {
		this.argomentazioni_accolte = argomentazioni_accolte;
	}
	public Double getImporto_sanzione_ingiunta() {
		return importo_sanzione_ingiunta;
	}
	public void setImporto_sanzione_ingiunta(Double importo_sanzione_ingiunta) {
		this.importo_sanzione_ingiunta = importo_sanzione_ingiunta;
	}
	public Timestamp getData_emissione_mod4d_5d() {
		return data_emissione_mod4d_5d;
	}
	public void setData_emissione_mod4d_5d(Timestamp data_emissione_mod4d_5d) {
		this.data_emissione_mod4d_5d = data_emissione_mod4d_5d;
	}
	public double getGiorni_lavorazione_pratica() {
		return giorni_lavorazione_pratica;
	}
	public void setGiorni_lavorazione_pratica(double giorni_lavorazione_pratica) {
		this.giorni_lavorazione_pratica = giorni_lavorazione_pratica;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

	
	
}
