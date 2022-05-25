/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;


@Entity
@Table(name = "cartella_clinica", schema = "public")
@Where(clause = "trashed_date is null")
public class CartellaClinicaList implements java.io.Serializable {
	private static final long serialVersionUID = -2430940309640415238L;
	
	private int id;
	private String numero;
	private FascicoloSanitario fascicoloSanitario;
	private Accettazione accettazione;
	private Date dataApertura;
	private Date dataChiusura;
	private Diagnosi lastDiagnosi = new Diagnosi();
	private Set<Diagnosi> diagnosis = new HashSet<Diagnosi>(0);


	public CartellaClinicaList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "numero")
	@NotNull
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fascicolo_sanitario")
	public FascicoloSanitario getFascicoloSanitario() {
		return this.fascicoloSanitario;
	}

	public void setFascicoloSanitario(FascicoloSanitario fascicoloSanitario) {
		this.fascicoloSanitario = fascicoloSanitario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accettazione")
	@NotNull
	public Accettazione getAccettazione() {
		return this.accettazione;
	}

	public void setAccettazione(Accettazione accettazione) {
		this.accettazione = accettazione;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_apertura", length = 13)
	public Date getDataApertura() {
		return this.dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_chiusura", length = 13)
	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public Diagnosi getLastDiagnosi() {
		Set<Diagnosi> setDiagnosi = this.diagnosis;

		Diagnosi currentDiagnosi;

		if (setDiagnosi.size() > 1) {

			Iterator listaDiagnosi = setDiagnosi.iterator();

			lastDiagnosi = (Diagnosi) listaDiagnosi.next();

			while (listaDiagnosi.hasNext()) {

				currentDiagnosi = (Diagnosi) listaDiagnosi.next();

				if (currentDiagnosi.getDataDiagnosi().compareTo(
						lastDiagnosi.getDataDiagnosi()) == 0
						&& currentDiagnosi.getId() > lastDiagnosi.getId()) {
					lastDiagnosi = currentDiagnosi;
				}
				if (currentDiagnosi.getDataDiagnosi().compareTo(
						lastDiagnosi.getDataDiagnosi()) > 0) {
					lastDiagnosi = currentDiagnosi;
				}

			}
			return lastDiagnosi;
		}

		else if (setDiagnosi.size() == 1) {

			Iterator listaDiagnosi = setDiagnosi.iterator();
			lastDiagnosi = (Diagnosi) listaDiagnosi.next();

			return lastDiagnosi;
		}

		return lastDiagnosi;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	public Set<Diagnosi> getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(Set<Diagnosi> diagnosis) {
		this.diagnosis = diagnosis;
	}

}
