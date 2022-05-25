/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.sinantropi;

import it.us.web.bean.BUtente;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.lookup.LookupComuni;

import java.util.Date;
import java.util.HashSet;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "sinantropo_detenzioni", schema = "public")
public class Detenzioni implements java.io.Serializable {

	private int id;	
		
	private Date dataDetenzioneDa;
	private Date dataDetenzioneA;
	private LookupComuni comuneDetenzione;
	private String luogoDetenzione;
	
	private String detentorePrivatoNome;
	private String detentorePrivatoCognome;
	private String detentorePrivatoCodiceFiscale;
	private LookupTipiDocumento lookupTipologiaDocumento;
	private String detentorePrivatoNumeroDocumento;
	private String detentorePrivatoEmail;
	private String detentorePrivatoTelefono;	
	private LookupDetentori lookupDetentori;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private Catture catture;
	
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
	public BUtente getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")	
	public BUtente getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_detenzione_da", length = 29)
	public Date getDataDetenzioneDa() {
		return dataDetenzioneDa;
	}

	public void setDataDetenzioneDa(Date dataDetenzioneDa) {
		this.dataDetenzioneDa = dataDetenzioneDa;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_detenzione_a", length = 29)
	public Date getDataDetenzioneA() {
		return dataDetenzioneA;
	}

	public void setDataDetenzioneA(Date dataDetenzioneA) {
		this.dataDetenzioneA = dataDetenzioneA;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune_detenzione")
	public LookupComuni getComuneDetenzione() {
		return comuneDetenzione;
	}

	public void setComuneDetenzione(LookupComuni comuneDetenzione) {
		this.comuneDetenzione = comuneDetenzione;
	}

	@Column(name ="luogo_detenzione")
	public String getLuogoDetenzione() {
		return luogoDetenzione;
	}

	public void setLuogoDetenzione(String luogoDetenzione) {
		this.luogoDetenzione = luogoDetenzione;
	}
	
	@Column(name = "detentorePrivato_nome", length = 64)
	@Length(max = 64)
	@NotNull
	public String getDetentorePrivatoNome() {
		return this.detentorePrivatoNome;
	}

	public void setDetentorePrivatoNome(String detentorePrivatoNome) {
		this.detentorePrivatoNome = detentorePrivatoNome;
	}

	@Column(name = "detentorePrivato_cognome", length = 64)
	@Length(max = 64)
	@NotNull
	public String getDetentorePrivatoCognome() {
		return this.detentorePrivatoCognome;
	}

	public void setDetentorePrivatoCognome(String detentorePrivatoCognome) {
		this.detentorePrivatoCognome = detentorePrivatoCognome;
	}

	@Column(name = "detentorePrivato_codice_fiscale", length = 16)
	@Length(max = 16)
	@NotNull
	public String getDetentorePrivatoCodiceFiscale() {
		return this.detentorePrivatoCodiceFiscale;
	}

	public void setDetentorePrivatoCodiceFiscale(String detentorePrivatoCodiceFiscale) {
		this.detentorePrivatoCodiceFiscale = detentorePrivatoCodiceFiscale;
	}

	@Column(name = "detentorePrivato_numero_documento", length = 64)
	@Length(max = 64)
	public String getDetentorePrivatoNumeroDocumento() {
		return this.detentorePrivatoNumeroDocumento;
	}

	public void setDetentorePrivatoNumeroDocumento(String detentorePrivatoNumeroDocumento) {
		this.detentorePrivatoNumeroDocumento = detentorePrivatoNumeroDocumento;
	}

		
	
	@Column(name = "detentorePrivato_email", length = 64)
	@Length(max = 64)
	public String getDetentorePrivatoEmail() {
		return detentorePrivatoEmail;
	}

	public void setDetentorePrivatoEmail(String detentorePrivatoEmail) {
		this.detentorePrivatoEmail = detentorePrivatoEmail;
	}

	@Column(name = "detentorePrivato_telefono", length = 64)
	@Length(max = 64)
	public String getDetentorePrivatoTelefono() {
		return detentorePrivatoTelefono;
	}

	public void setDetentorePrivatoTelefono(String detentorePrivatoTelefono) {
		this.detentorePrivatoTelefono = detentorePrivatoTelefono;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detentorePrivato_tipologia_documento")
	public LookupTipiDocumento getLookupTipologiaDocumento() {
		return lookupTipologiaDocumento;
	}

	public void setLookupTipologiaDocumento(
			LookupTipiDocumento lookupTipologiaDocumento) {
		this.lookupTipologiaDocumento = lookupTipologiaDocumento;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipologia_detentore")
	public LookupDetentori getLookupDetentori() {
		return lookupDetentori;
	}

	public void setLookupDetentori(LookupDetentori lookupDetentori) {
		this.lookupDetentori = lookupDetentori;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catture")
	public Catture getCatture() {
		return catture;
	}

	public void setCatture(Catture catture) {
		this.catture = catture;
	}
	
	
	
	

}
