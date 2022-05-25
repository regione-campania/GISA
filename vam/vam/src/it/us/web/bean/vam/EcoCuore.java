/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.EcoCuoreEsito;
import it.us.web.bean.vam.lookup.LookupEcoCuoreDiagnosi;
import it.us.web.util.bean.NotNullDependencies;
import it.us.web.util.bean.MinLengthDependencies;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "eco_cuore", schema = "public")
@Where( clause = "trashed_date is null" )
@MinLengthDependencies.List({
	@MinLengthDependencies(dependence = "difettoInterventricolare" ,attributo = "flussoDifettoInterventricolare", 	minLength = 5, message = "Scegliere flusso del difetto interventricolare"),
	@MinLengthDependencies(dependence = "difettoInteratriale" ,		attributo = "flussoDifettoInteratriale", 		minLength = 5, message = "Scegliere flusso del difetto interatriale")
})
@NotNullDependencies(listField="lvotVelocitaOndaE;lvotVelocitaOndaA", allNull=true, message = "Valorizzare sia velocità Onda E che velocità Onda A")

public class EcoCuore implements java.io.Serializable, EsameInterface
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
	private Set<EcoCuoreEsito> ecoCuoreEsitos = new HashSet<EcoCuoreEsito>(0);
	private String unitaMisuraBMode;
	private String unitaMisuraDoppler;
	private Boolean neoformazioneBaseCardiaca;
	private Boolean neoformazioneAuricolaDx;
	private Boolean difettoInterventricolare;
	private String flussoDifettoInterventricolare;
	private Boolean difettoInteratriale;
	private String flussoDifettoInteratriale;
	private Boolean dottoArteriosoPervio;
	private Boolean versamentoPericardico;
	private Boolean versamentoToracico;
	private Float IVSd;
	private Float IVSs;
	private Float LVIDd;
	private Float LVIDs;
	private Float LVWd;
	private Float LVWs;
	private Float laAo;
	private Float LA;
	private Float FS;
	private Float EF;
	private String aortaFlusso;
	private Float aortaVelocitaMax;
	private Float aortaGradiente;
	private String aortaStenosi1;
	private String aortaStenosi2;
	private String aortaInsufficienza;
	private String polmonareFlusso;
	private Float polmonareVelocitaMax; 
	private Float polmonareGradiente;
	private String polmonareStenosi;
	private String polmonareInsufficienza;
	private String lvotTipo;
	private Float lvotVelocitaMax;
	private Float lvotGradiente;
	private Boolean lvotFlussoLaminare;
	private Float lvotVelocitaOndaE;
	private Float lvotVelocitaOndaA;
	private Float lvotRapportoEA;
	private Float lvotVelocitaMaxRigurgito;
	private Float lvotMitraleGradiente;
	private String lvotStenosi;
	private String lvotInsufficienza;
	private String rvotTipo;
	private Float rvotVelocitaMax;
	private Float rvotGradiente;
	private Boolean rvotFlussoLaminare;
	private Float rvotVelocitaMaxRigurgito;
	private Float rvotTricuspideGradiente;
	private String rvotStenosi;
	private String rvotInsufficienza;
	private Set<LookupEcoCuoreDiagnosi> lookupEcoCuoreDiagnosis;
	
	
	

	public EcoCuore()
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
		return enteredBy;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ecoCuore")
	public Set<EcoCuoreEsito> getEcoCuoreEsitos() {
		return this.ecoCuoreEsitos;
	}

	public void setEcoCuoreEsitos(Set<EcoCuoreEsito> ecoCuoreEsitos) {
		this.ecoCuoreEsitos = ecoCuoreEsitos;
	}
	
	@Column(name = "unita_misura_bmode")
	@Length(min = 2, max = 2,message="Selezionare un valore")
	public String getUnitaMisuraBMode() {
		return unitaMisuraBMode;
	}

	public void setUnitaMisuraBMode(String unitaMisuraBMode) {
		this.unitaMisuraBMode = unitaMisuraBMode;
	}
	
	@Column(name = "unita_misura_doppler")
	@Length(min = 3, max = 4,message = "Selezionare un valore")
	public String getUnitaMisuraDoppler() {
		return unitaMisuraDoppler;
	}

	public void setUnitaMisuraDoppler(String unitaMisuraDoppler) {
		this.unitaMisuraDoppler = unitaMisuraDoppler;
	}
	
	@Column(name = "neoformazione_base_cardiaca")
	public Boolean getNeoformazioneBaseCardiaca() {
		return this.neoformazioneBaseCardiaca;
	}

	public void setNeoformazioneBaseCardiaca(Boolean neoformazioneBaseCardiaca) {
		this.neoformazioneBaseCardiaca = neoformazioneBaseCardiaca;
	}
	
	@Column(name = "neoformazione_auricola_dx")
	public Boolean getNeoformazioneAuricolaDx() {
		return this.neoformazioneAuricolaDx;
	}

	public void setNeoformazioneAuricolaDx(Boolean neoformazioneAuricolaDx) {
		this.neoformazioneAuricolaDx = neoformazioneAuricolaDx;
	}

	
	@Column(name = "difetto_interventricolare")
	public Boolean getDifettoInterventricolare() {
		return this.difettoInterventricolare;
	}

	public void setDifettoInterventricolare(Boolean difettoInterventricolare) {
		this.difettoInterventricolare = difettoInterventricolare;
	}
	
	
	@Column(name = "flusso_difetto_interventricolare")
	@Length(max = 5)
	public String getFlussoDifettoInterventricolare() {
		return flussoDifettoInterventricolare;
	}

	public void setFlussoDifettoInterventricolare(
			String flussoDifettoInterventricolare) {
		this.flussoDifettoInterventricolare = flussoDifettoInterventricolare;
	}

	@Column(name = "difetto_interatriale")
	public Boolean getDifettoInteratriale() {
		return difettoInteratriale;
	}

	public void setDifettoInteratriale(Boolean difettoInteratriale) {
		this.difettoInteratriale = difettoInteratriale;
	}

	@Column(name = "flusso_difetto_interatriale")
	@Length(max = 5)
	public String getFlussoDifettoInteratriale() {
		return flussoDifettoInteratriale;
	}

	public void setFlussoDifettoInteratriale(String flussoDifettoInteratriale) {
		this.flussoDifettoInteratriale = flussoDifettoInteratriale;
	}

	@Column(name = "dotto_arterioso_pervio")
	public Boolean getDottoArteriosoPervio() {
		return dottoArteriosoPervio;
	}

	public void setDottoArteriosoPervio(Boolean dottoArteriosoPervio) {
		this.dottoArteriosoPervio = dottoArteriosoPervio;
	}

	@Column(name = "versamento_pericardico")
	public Boolean getVersamentoPericardico() {
		return versamentoPericardico;
	}

	public void setVersamentoPericardico(Boolean versamentoPericardico) {
		this.versamentoPericardico = versamentoPericardico;
	}

	@Column(name = "versamento_toracico")
	public Boolean getVersamentoToracico() {
		return versamentoToracico;
	}

	public void setVersamentoToracico(Boolean versamentoToracico) {
		this.versamentoToracico = versamentoToracico;
	}
	
	@Column(name = "ivsd")
	public Float getIVSd() {
		return IVSd;
	}

	public void setIVSd(Float iVSd) {
		IVSd = iVSd;
	}

	@Column(name = "ivss")
	public Float getIVSs() {
		return IVSs;
	}

	public void setIVSs(Float iVSs) {
		IVSs = iVSs;
	}

	@Column(name = "lvidd")
	public Float getLVIDd() {
		return LVIDd;
	}

	public void setLVIDd(Float lVIDd) {
		LVIDd = lVIDd;
	}

	@Column(name = "lvids")
	public Float getLVIDs() {
		return LVIDs;
	}

	public void setLVIDs(Float lVIDs) {
		LVIDs = lVIDs;
	}

	@Column(name = "lvwd")
	public Float getLVWd() {
		return LVWd;
	}

	public void setLVWd(Float lVWd) {
		LVWd = lVWd;
	}

	@Column(name = "lvws")
	public Float getLVWs() {
		return LVWs;
	}

	public void setLVWs(Float lVWs) {
		LVWs = lVWs;
	}

	@Column(name = "la_ao")
	public Float getLaAo() {
		return this.laAo;
	}

	public void setLaAo(Float laAo) {
		this.laAo = laAo;
	}

	@Column(name = "la")
	public Float getLA() {
		return LA;
	}

	public void setLA(Float lA) {
		LA = lA;
	}

	@Column(name = "fs")
	public Float getFS() {
		return FS;
	}

	public void setFS(Float fS) {
		FS = fS;
	}

	@Column(name = "ef")
	public Float getEF() {
		return EF;
	}

	public void setEF(Float eF) {
		EF = eF;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "eco_cuore_diagnosi", 
			joinColumns =        { @JoinColumn(name = "id_eco_cuore", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_diagnosi", nullable = false, updatable = false) })
	public Set<LookupEcoCuoreDiagnosi> getLookupEcoCuoreDiagnosis() {
		return lookupEcoCuoreDiagnosis;
	}

	public void setLookupEcoCuoreDiagnosis(Set<LookupEcoCuoreDiagnosi> lookupEcoCuoreDiagnosis) {
		this.lookupEcoCuoreDiagnosis = lookupEcoCuoreDiagnosis;
	}
	
	
	@Column(name = "aorta_flusso")
	@Length(max = 32)
	public String getAortaFlusso() {
		return aortaFlusso;
	}

	public void setAortaFlusso(String aortaFlusso) {
		this.aortaFlusso = aortaFlusso;
	}

	@Column(name = "aorta_velocita_max")
	public Float getAortaVelocitaMax() {
		return aortaVelocitaMax;
	}

	public void setAortaVelocitaMax(Float aortaVelocitaMax) {
		this.aortaVelocitaMax = aortaVelocitaMax;
	}
	
	@Column(name = "aorta_gradiente")
	public Float getAortaGradiente() {
		return aortaGradiente;
	}

	public void setAortaGradiente(Float aortaGradiente) {
		this.aortaGradiente = aortaGradiente;
	}

	@Column(name = "aorta_stenosi1")
	@Length(max = 2)
	public String getAortaStenosi1() {
		return aortaStenosi1;
	}

	public void setAortaStenosi1(String aortaStenosi1) {
		this.aortaStenosi1 = aortaStenosi1;
	}
	
	@Column(name = "aorta_stenosi2")
	@Length(max = 1)
	public String getAortaStenosi2() {
		return aortaStenosi2;
	}

	public void setAortaStenosi2(String aortaStenosi2) {
		this.aortaStenosi2 = aortaStenosi2;
	}

	@Column(name = "aorta_insufficienza")
	@Length(max = 1)
	public String getAortaInsufficienza() {
		return aortaInsufficienza;
	}

	public void setAortaInsufficienza(String aortaInsufficienza) {
		this.aortaInsufficienza = aortaInsufficienza;
	}

	@Column(name = "polmonare_flusso")
	@Length(max = 1)
	public String getPolmonareFlusso() {
		return polmonareFlusso;
	}

	public void setPolmonareFlusso(String polmonareFlusso) {
		this.polmonareFlusso = polmonareFlusso;
	}

	@Column(name = "polmonare_velocita_max")
	public Float getPolmonareVelocitaMax() {
		return polmonareVelocitaMax;
	}

	public void setPolmonareVelocitaMax(Float polmonareVelocitaMax) {
		this.polmonareVelocitaMax = polmonareVelocitaMax;
	}

	@Column(name = "polmonare_gradiente")
	public Float getPolmonareGradiente() {
		return polmonareGradiente;
	}

	public void setPolmonareGradiente(Float polmonareGradiente) {
		this.polmonareGradiente = polmonareGradiente;
	}

	@Column(name = "polmonare_stenosi")
	@Length(max = 1)
	public String getPolmonareStenosi() {
		return polmonareStenosi;
	}

	public void setPolmonareStenosi(String polmonareStenosi) {
		this.polmonareStenosi = polmonareStenosi;
	}

	@Column(name = "polmonare_insufficienza")
	@Length(max = 1)
	public String getPolmonareInsufficienza() {
		return polmonareInsufficienza;
	}

	public void setPolmonareInsufficienza(String polmonareInsufficienza) {
		this.polmonareInsufficienza = polmonareInsufficienza;
	}

	@Column(name = "lvot_tipo")
	@Length(max = 1)
	public String getLvotTipo() {
		return lvotTipo;
	}

	public void setLvotTipo(String lvotTipo) {
		this.lvotTipo = lvotTipo;
	}

	@Column(name = "lvot_velocita_max")
	public Float getLvotVelocitaMax() {
		return lvotVelocitaMax;
	}

	public void setLvotVelocitaMax(Float lvotVelocitaMax) {
		this.lvotVelocitaMax = lvotVelocitaMax;
	}

	@Column(name = "lvot_gradiente")
	public Float getLvotGradiente() {
		return lvotGradiente;
	}

	public void setLvotGradiente(Float lvotGradiente) {
		this.lvotGradiente = lvotGradiente;
	}

	@Column(name = "lvot_flusso_laminare")
	public Boolean getLvotFlussoLaminare() {
		return lvotFlussoLaminare;
	}

	public void setLvotFlussoLaminare(Boolean lvotFlussoLaminare) {
		this.lvotFlussoLaminare = lvotFlussoLaminare;
	}

	@Column(name = "lvot_velocita_onda_e")
	public Float getLvotVelocitaOndaE() {
		return lvotVelocitaOndaE;
	}

	public void setLvotVelocitaOndaE(Float lvotVelocitaOndaE) {
		this.lvotVelocitaOndaE = lvotVelocitaOndaE;
	}

	@Column(name = "lvot_velocita_onda_a")
	public Float getLvotVelocitaOndaA() {
		return lvotVelocitaOndaA;
	}

	public void setLvotVelocitaOndaA(Float lvotVelocitaOndaA) {
		this.lvotVelocitaOndaA = lvotVelocitaOndaA;
	}

	@Column(name = "rapporto_ea")
	public Float getLvotRapportoEA() {
		return lvotRapportoEA;
	}

	public void setLvotRapportoEA(Float lvotRapportoEA) {
		this.lvotRapportoEA = lvotRapportoEA;
	}

	@Column(name = "lvot_velocita_max_rigurgito")
	public Float getLvotVelocitaMaxRigurgito() {
		return lvotVelocitaMaxRigurgito;
	}

	public void setLvotVelocitaMaxRigurgito(Float lvotVelocitaMaxRigurgito) {
		this.lvotVelocitaMaxRigurgito = lvotVelocitaMaxRigurgito;
	}

	@Column(name = "lvot_mitrale_gradiente")
	public Float getLvotMitraleGradiente() {
		return lvotMitraleGradiente;
	}

	public void setLvotMitraleGradiente(Float lvotMitraleGradiente) {
		this.lvotMitraleGradiente = lvotMitraleGradiente;
	}

	@Column(name = "lvot_stenosi")
	@Length(max  = 1 )
	public String getLvotStenosi() {
		return lvotStenosi;
	}

	public void setLvotStenosi(String lvotStenosi) {
		this.lvotStenosi = lvotStenosi;
	}

	@Column(name = "lvot_insufficienza")
	@Length(max  = 1 )
	public String getLvotInsufficienza() {
		return lvotInsufficienza;
	}

	public void setLvotInsufficienza(String lvotInsufficienza) {
		this.lvotInsufficienza = lvotInsufficienza;
	}

	@Column(name = "rvot_tipo")
	@Length(max  = 1 )
	public String getRvotTipo() {
		return rvotTipo;
	}

	public void setRvotTipo(String rvotTipo) {
		this.rvotTipo = rvotTipo;
	}

	@Column(name = "rvot_velocita_max")
	public Float getRvotVelocitaMax() {
		return rvotVelocitaMax;
	}

	public void setRvotVelocitaMax(Float rvotVelocitaMax) {
		this.rvotVelocitaMax = rvotVelocitaMax;
	}

	@Column(name = "rvot_gradiente")
	public Float getRvotGradiente() {
		return rvotGradiente;
	}

	public void setRvotGradiente(Float rvotGradiente) {
		this.rvotGradiente = rvotGradiente;
	}

	@Column(name = "rvot_flusso_laminare")
	public Boolean getRvotFlussoLaminare() {
		return rvotFlussoLaminare;
	}

	public void setRvotFlussoLaminare(Boolean rvotFlussoLaminare) {
		this.rvotFlussoLaminare = rvotFlussoLaminare;
	}

	@Column(name = "rvot_velocita_max_rigurgito")
	public Float getRvotVelocitaMaxRigurgito() {
		return rvotVelocitaMaxRigurgito;
	}

	public void setRvotVelocitaMaxRigurgito(Float rvotVelocitaMaxRigurgito) {
		this.rvotVelocitaMaxRigurgito = rvotVelocitaMaxRigurgito;
	}

	@Column(name = "rvot_tricuspide_gradiente")
	public Float getRvotTricuspideGradiente() {
		return rvotTricuspideGradiente;
	}

	public void setRvotTricuspideGradiente(Float rvotTricuspideGradiente) {
		this.rvotTricuspideGradiente = rvotTricuspideGradiente;
	}

	@Column(name = "rvot_stenosi")
	@Length(max  = 1 )
	public String getRvotStenosi() {
		return rvotStenosi;
	}

	public void setRvotStenosi(String rvotStenosi) {
		this.rvotStenosi = rvotStenosi;
	}

	@Column(name = "rvot_insufficienza")
	@Length(max  = 1 )
	public String getRvotInsufficienza() {
		return rvotInsufficienza;
	}

	public void setRvotInsufficienza(String rvotInsufficienza) {
		this.rvotInsufficienza = rvotInsufficienza;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Eco Cuore";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare eco cuore";
	}

}
