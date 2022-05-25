/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_operazioni_accettazione", schema = "public")
@Where( clause = "enabled" )
public class LookupOperazioniAccettazione implements java.io.Serializable
{
	private static final long serialVersionUID = -7225928276157167124L;
	
	private int id;
	private String description;
	private boolean inbdr;
	private boolean obbligoCc;
	private boolean canina;
	private boolean felina;
	private boolean sinantropi;
	private Integer level;
	private Boolean enabled;
	private Boolean covid;
	private Boolean enabledInPage;
	private Boolean hiddenInPage;
	private Boolean approfondimenti;
	private Boolean approfondimentoDiagnosticoMedicina;
	private Boolean richiestaPrelieviMalattieInfettive;
	private Boolean altaSpecialitaChirurgica;
	private Boolean diagnosticaStrumentale;
	private Boolean effettuabileFuoriAsl;
	//Indica se l'operazione è abilitata per animali fuori asl e morti
	private Boolean effettuabileFuoriAslMorto;
	private Boolean effettuabileDaMorto;
	private Boolean effettuabileDaVivo;
	private Boolean sceltaAsl;
	private Boolean intraFuoriAsl;
	private Boolean versoAssocCanili;
	private Boolean enabledDefault;
	private Integer idBdr;
	private Set<LookupOperazioniAccettazioneCondizionate> operazioniCondizionate = new HashSet<LookupOperazioniAccettazioneCondizionate>(0);
	private Set<LookupOperazioniAccettazioneCondizionate> operazioniCondizionanti = new HashSet<LookupOperazioniAccettazioneCondizionate>(0);
//	private Set<Accettazione> accettaziones = new HashSet<Accettazione>(0);

	public LookupOperazioniAccettazione()
	{
		
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Motivazioni/Operazioni Richieste";
	}
	
	@Override
	public String toString()
	{
		return description;
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

	@Column(name = "description", length = 64)
	@Length(max = 64)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "inbdr", nullable = false)
	@NotNull
	public boolean isInbdr() {
		return this.inbdr;
	}

	public void setInbdr(boolean inbdr) {
		this.inbdr = inbdr;
	}
	
	@Column(name = "obbligo_cc", nullable = false)
	@NotNull
	public boolean isObbligoCc() {
		return this.obbligoCc;
	}

	public void setObbligoCc(boolean obbligoCc) {
		this.obbligoCc = obbligoCc;
	}

	@Column(name = "canina", nullable = false)
	@NotNull
	public boolean isCanina() {
		return this.canina;
	}

	public void setCanina(boolean canina) {
		this.canina = canina;
	}

	@Column(name = "felina", nullable = false)
	@NotNull
	public boolean isFelina() {
		return this.felina;
	}

	public void setFelina(boolean felina) {
		this.felina = felina;
	}

	@Column(name = "sinantropi", nullable = false)
	@NotNull
	public boolean isSinantropi() {
		return this.sinantropi;
	}

	public void setSinantropi(boolean sinantropi) {
		this.sinantropi = sinantropi;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name = "covid")
	public Boolean getCovid() {
		return this.covid;
	}

	public void setCovid(Boolean covid) {
		this.covid = covid;
	}
	
	@Column(name = "enabled_in_page")
	public Boolean getEnabledInPage() {
		return this.enabledInPage;
	}

	public void setEnabledInPage(Boolean enabledInPage) {
		this.enabledInPage = enabledInPage;
	}
	
	@Column(name = "hidden_in_page")
	public Boolean getHiddenInPage() {
		return this.hiddenInPage;
	}

	public void setHiddenInPage(Boolean hiddenInPage) {
		this.hiddenInPage = hiddenInPage;
	}

	
	@Column(name = "enabled_default")
	public Boolean getEnabledDefault() {
		return this.enabledDefault;
	}

	public void setEnabledDefault(Boolean enabledDefault) {
		this.enabledDefault = enabledDefault;
	}
	
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "accettazione_operazionirichieste", schema = "public", joinColumns = { @JoinColumn(name = "operazione_richiesta", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "accettazione", nullable = false, updatable = false) })
//	public Set<Accettazione> getAccettaziones() {
//		return this.accettaziones;
//	}
//
//	public void setAccettaziones(Set<Accettazione> accettaziones) {
//		this.accettaziones = accettaziones;
//	}

	public Boolean getApprofondimenti() {
		return approfondimenti;
	}

	public void setApprofondimenti(Boolean approfondimenti) {
		this.approfondimenti = approfondimenti;
	}
	
	@Column(name = "approfondimento_diagnostico_medicina")
	public Boolean getApprofondimentoDiagnosticoMedicina() {
		return approfondimentoDiagnosticoMedicina;
	}

	public void setApprofondimentoDiagnosticoMedicina(Boolean appDiaMedicina) {
		this.approfondimentoDiagnosticoMedicina = appDiaMedicina;
	}

	@Column(name = "richiesta_prelievi_malattie_infettive")
	public Boolean getRichiestaPrelieviMalattieInfettive() {
		return richiestaPrelieviMalattieInfettive;
	}

	public void setRichiestaPrelieviMalattieInfettive(
			Boolean richiestaPrelieviMalattieInfettive) {
		this.richiestaPrelieviMalattieInfettive = richiestaPrelieviMalattieInfettive;
	}


	@Column(name = "alta_specialita_chirurgica")
	public Boolean getAltaSpecialitaChirurgica() {
		return altaSpecialitaChirurgica;
	}

	public void setAltaSpecialitaChirurgica(Boolean altaSpecialitaChirurgica) {
		this.altaSpecialitaChirurgica = altaSpecialitaChirurgica;
	}

	@Column(name = "diagnostica_strumentale")
	public Boolean getDiagnosticaStrumentale() {
		return diagnosticaStrumentale;
	}

	public void setDiagnosticaStrumentale(Boolean diagnosticaStrumentale) {
		this.diagnosticaStrumentale = diagnosticaStrumentale;
	}
	
	@Column(name="effettuabile_fuori_asl")
	public Boolean getEffettuabileFuoriAsl() {
		return effettuabileFuoriAsl;
	}

	public void setEffettuabileFuoriAsl(Boolean effettuabileFuoriAsl) {
		this.effettuabileFuoriAsl = effettuabileFuoriAsl;
	}
	
	@Column(name="effettuabile_fuori_asl_morto")
	public Boolean getEffettuabileFuoriAslMorto() {
		return effettuabileFuoriAslMorto;
	}

	public void setEffettuabileFuoriAslMorto(Boolean effettuabileFuoriAslMorto) {
		this.effettuabileFuoriAslMorto = effettuabileFuoriAslMorto;
	}

	@Column(name="effettuabile_da_morto")
	public Boolean getEffettuabileDaMorto() {
		return effettuabileDaMorto;
	}

	public void setEffettuabileDaMorto(Boolean effettuabileDaMorto) {
		this.effettuabileDaMorto = effettuabileDaMorto;
	}

	@Column(name="scelta_asl")
	public Boolean getSceltaAsl() {
		return sceltaAsl;
	}

	public void setSceltaAsl(Boolean sceltaAsl) {
		this.sceltaAsl = sceltaAsl;
	}
	
	@Column(name="intra_fuori_asl")
	public Boolean getIntraFuoriAsl() {
		return intraFuoriAsl;
	}

	public void setIntraFuoriAsl(Boolean intraFuoriAsl) {
		this.intraFuoriAsl = intraFuoriAsl;
	}
	
	@Column(name="verso_assoc_canili")
	public Boolean getVersoAssocCanili() {
		return versoAssocCanili;
	}

	public void setVersoAssocCanili(Boolean versoAssocCanili) {
		this.versoAssocCanili = versoAssocCanili;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operazioneCondizionante")
	public Set<LookupOperazioniAccettazioneCondizionate> getOperazioniCondizionate() {
		try{
		return this.operazioniCondizionate;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void setOperazioniCondizionate(Set<LookupOperazioniAccettazioneCondizionate> operazioniCondizioanate) {
		this.operazioniCondizionate = operazioniCondizioanate;
	}
	
	@Transient
	public ArrayList<String> getOperazioniCondizionateArray()
	{
		ArrayList<String> toReturn = new ArrayList<String>();
		for(LookupOperazioniAccettazioneCondizionate temp:getOperazioniCondizionate())
		{
			toReturn.add(temp.getOperazioneCondizionata().getId()+"@"+temp.getOperazioneDaFare());
		}
		return toReturn;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operazioneCondizionata")
	public Set<LookupOperazioniAccettazioneCondizionate> getOperazioniCondizionanti() {
		return this.operazioniCondizionanti;
	}

	public void setOperazioniCondizionanti(Set<LookupOperazioniAccettazioneCondizionate> operazioniCondizioananti) {
		this.operazioniCondizionanti = operazioniCondizioananti;
	}

	@Column(name="effettuabile_da_vivo")
	public Boolean getEffettuabileDaVivo() {
		return effettuabileDaVivo;
	}

	public void setEffettuabileDaVivo(Boolean effettuabileDaVivo) {
		this.effettuabileDaVivo = effettuabileDaVivo;
	}
	
	@Column(name = "id_bdr")
	public Integer getIdBdr() {
		return this.idBdr;
	}

	public void setIdBdr(Integer idBdr) {
		this.idBdr = idBdr;
	}
}
