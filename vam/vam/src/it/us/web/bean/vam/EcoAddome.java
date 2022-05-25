/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.util.bean.NotNullDependencies;

import java.util.Date;
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
@Table(name = "eco_addome", schema = "public")
@Where( clause = "trashed_date is null" )
@NotNullDependencies(allNull = true, listField = "gravidanza1;gravidanza2;gravidanza3;gravidanza4", message = "Valorizzare tutti i valori della dimensione camera ovulare intrauterina")
public class EcoAddome implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = 5528525804289839766L;
	
	private int id;
	private CartellaClinica cartellaClinica;
	private Date dataRichiesta;
	private Date dataEsito;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;	
	private Set<EcoAddomeEsito> ecoAddomeEsitos;
	private Float gravidanza1;
	private Float gravidanza2;
	private Float gravidanza3;
	private Float gravidanza4;

	public EcoAddome()
	{
		
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_richiesta", length = 13)
	@NotNull(message = "Inserire un valore")
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_esito", length = 13)
	public Date getDataEsito() {
		return this.dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull	
	public BUtente getEnteredBy() {
		return this.enteredBy;
	}
	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtente getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ecoAddome")
	public Set<EcoAddomeEsito> getEcoAddomeEsitos() {
		return ecoAddomeEsitos;
	}

	public void setEcoAddomeEsitos(Set<EcoAddomeEsito> ecoAddomeEsitos) {
		this.ecoAddomeEsitos = ecoAddomeEsitos;
	}
	
	@Column(name = "gravidanza1")
	public Float getGravidanza1() {
		return gravidanza1;
	}

	public void setGravidanza1(Float gravidanza1) {
		this.gravidanza1 = gravidanza1;
	}

	@Column(name = "gravidanza2")
	public Float getGravidanza2() {
		return gravidanza2;
	}

	public void setGravidanza2(Float gravidanza2) {
		this.gravidanza2 = gravidanza2;
	}

	@Column(name = "gravidanza3")
	public Float getGravidanza3() {
		return gravidanza3;
	}

	
	public void setGravidanza3(Float gravidanza3) {
		this.gravidanza3 = gravidanza3;
	}

	@Column(name = "gravidanza4")
	public Float getGravidanza4() {
		return gravidanza4;
	}

	public void setGravidanza4(Float gravidanza4) {
		this.gravidanza4 = gravidanza4;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Eco Addome";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare eco addome";
	}
	
	

}
