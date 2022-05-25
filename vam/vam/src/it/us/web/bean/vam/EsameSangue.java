/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "esame_sangue", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameSangue implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = 231246511650594951L;
	
	private int id;
	private CartellaClinica cartellaClinica;
	private String note;
	private Date dataRichiesta;
	private Date dataEsito;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	/* Sezione emetochimici*/
	private Float ematochimiciBun;
	private Float ematochimiciBilirubinaTotale;
	private Float ematochimiciCreatinina;
	private Float ematochimiciGlucosio;
	private Float ematochimiciColesterolo;
	private Float ematochimiciProteineTotali;
	private Float ematochimiciAlbumine;
	private Float ematochimiciGlobuline;
	private Float ematochimiciRapportoAG;
	
	/* Sezione elettroliti*/
	private Float elettrolitiCalcio;
	private Float elettrolitiCalcioCorretto;
	private Float elettrolitiFosforo;
	private Float elettrolitiSodio;
	private Float elettrolitiPotassio;
	private Float elettrolitiRapportoNaK;
	private Float elettrolitiCloro;
	private Float elettrolitiRapportoCaP;
	private Float elettrolitiProdottoCaP;
	private Float elettrolitiCloroCorretto;
	private Float elettrolitiFe;
	private Float elettrolitiMagnesio;
	
	/* Sezione emogas arterioso*/
	private Float emogasArteriosoPh;
	private Float emogasArteriosoTco2;
	private Float emogasArteriosoHco3;
	private Float emogasArteriosoPco2;
	private Float emogasArteriosoPo2;
	private Float emogasArteriosoAnionGap;
	
	/* Sezione emogas venoso*/
	private Float emogasVenosoPh;
	private Float emogasVenosoTco2;
	private Float emogasVenosoHco3;
	private Float emogasVenosoPco2;
	private Float emogasVenosoPo2;
	private Float emogasVenosoAnionGap;
	
	/* Sezione enzimatici*/
	private Float enzimaticiAst;
	private Float enzimaticiAlt;
	private Float enzimaticiAlp;
	private Float enzimaticiGgt;
	private Float enzimaticiCpk;
	private Float enzimaticiLdh;
	private Float enzimaticiAmilasi;
	private Float enzimaticiLipasi;
	private Float enzimaticiFruttosamina;
	private Float enzimaticiTrigliceridi;
	
	/* Sezione elettroforesi*/
	private Float elettroforesiAlbumine;
	private Float elettroforesiAlfa1;
	private Float elettroforesiAlfa2;
	private Float elettroforesiBeta;
	private Float elettroforesiGamma;
	private Float elettroforesiGlobuline;
	private Float elettroforesiRapportoAG;
	
	/* Sezione acidi biliari*/
	private Float acidiBiliari;
	private Float acidiBiliariPostp;
	
	

	public EsameSangue()
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
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

	@Column(name = "note", length = 64)
	@Length(max = 64)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_richiesta", length = 29)
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito", length = 29)
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
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	
	
	@Column(name = "ematochimici_bun")
	public Float getEmatochimiciBun() {
		return ematochimiciBun;
	}

	public void setEmatochimiciBun(Float ematochimiciBun) {
		this.ematochimiciBun = ematochimiciBun;
	}
	
	@Column(name = "ematochimici_bilirubina_totale")
	public Float getEmatochimiciBilirubinaTotale() {
		return ematochimiciBilirubinaTotale;
	}

	public void setEmatochimiciBilirubinaTotale(Float ematochimiciBilirubinaTotale) {
		this.ematochimiciBilirubinaTotale = ematochimiciBilirubinaTotale;
	}
	
	@Column(name = "ematochimici_creatinina")
	public Float getEmatochimiciCreatinina() {
		return ematochimiciCreatinina;
	}

	public void setEmatochimiciCreatinina(Float ematochimiciCreatinina) {
		this.ematochimiciCreatinina = ematochimiciCreatinina;
	}
	
	@Column(name = "ematochimici_glucosio")
	public Float getEmatochimiciGlucosio() {
		return ematochimiciGlucosio;
	}

	public void setEmatochimiciGlucosio(Float ematochimiciGlucosio) {
		this.ematochimiciGlucosio = ematochimiciGlucosio;
	}

	@Column(name = "ematochimici_colesterolo")
	public Float getEmatochimiciColesterolo() {
		return ematochimiciColesterolo;
	}

	public void setEmatochimiciColesterolo(Float ematochimiciColesterolo) {
		this.ematochimiciColesterolo = ematochimiciColesterolo;
	}

	@Column(name = "ematochimici_proteine_totali")
	public Float getEmatochimiciProteineTotali() {
		return ematochimiciProteineTotali;
	}

	public void setEmatochimiciProteineTotali(Float ematochimiciProteineTotali) {
		this.ematochimiciProteineTotali = ematochimiciProteineTotali;
	}

	@Column(name = "ematochimici_albumine")
	public Float getEmatochimiciAlbumine() {
		return ematochimiciAlbumine;
	}

	public void setEmatochimiciAlbumine(Float ematochimiciAlbumine) {
		this.ematochimiciAlbumine = ematochimiciAlbumine;
	}

	@Column(name = "ematochimici_globuline")
	public Float getEmatochimiciGlobuline() {
		return ematochimiciGlobuline;
	}

	public void setEmatochimiciGlobuline(Float ematochimiciGlobuline) {
		this.ematochimiciGlobuline = ematochimiciGlobuline;
	}

	@Column(name = "ematochimici_rapporto_a_g")
	public Float getEmatochimiciRapportoAG() {
		return ematochimiciRapportoAG;
	}

	public void setEmatochimiciRapportoAG(Float ematochimiciRapportoAG) {
		this.ematochimiciRapportoAG = ematochimiciRapportoAG;
	}

	@Column(name = "elettroliti_calcio")
	public Float getElettrolitiCalcio() {
		return elettrolitiCalcio;
	}

	public void setElettrolitiCalcio(Float elettrolitiCalcio) {
		this.elettrolitiCalcio = elettrolitiCalcio;
	}

	@Column(name = "elettroliti_calcio_corretto")
	public Float getElettrolitiCalcioCorretto() {
		return elettrolitiCalcioCorretto;
	}

	public void setElettrolitiCalcioCorretto(Float elettrolitiCalcioCorretto) {
		this.elettrolitiCalcioCorretto = elettrolitiCalcioCorretto;
	}

	@Column(name = "elettroliti_fosforo")
	public Float getElettrolitiFosforo() {
		return elettrolitiFosforo;
	}

	public void setElettrolitiFosforo(Float elettrolitiFosforo) {
		this.elettrolitiFosforo = elettrolitiFosforo;
	}

	@Column(name = "elettroliti_sodio")
	public Float getElettrolitiSodio() {
		return elettrolitiSodio;
	}

	public void setElettrolitiSodio(Float elettrolitiSodio) {
		this.elettrolitiSodio = elettrolitiSodio;
	}

	@Column(name = "elettroliti_potassio")
	public Float getElettrolitiPotassio() {
		return elettrolitiPotassio;
	}

	public void setElettrolitiPotassio(Float elettrolitiPotassio) {
		this.elettrolitiPotassio = elettrolitiPotassio;
	}

	@Column(name = "elettroliti_rapporto_na_k")
	public Float getElettrolitiRapportoNaK() {
		return elettrolitiRapportoNaK;
	}

	public void setElettrolitiRapportoNaK(Float elettrolitiRapportoNaK) {
		this.elettrolitiRapportoNaK = elettrolitiRapportoNaK;
	}

	@Column(name = "elettroliti_cloro")
	public Float getElettrolitiCloro() {
		return elettrolitiCloro;
	}

	public void setElettrolitiCloro(Float elettrolitiCloro) {
		this.elettrolitiCloro = elettrolitiCloro;
	}

	@Column(name = "elettroliti_rapporto_ca_p")
	public Float getElettrolitiRapportoCaP() {
		return elettrolitiRapportoCaP;
	}

	public void setElettrolitiRapportoCaP(Float elettrolitiRapportoCaP) {
		this.elettrolitiRapportoCaP = elettrolitiRapportoCaP;
	}

	@Column(name = "elettroliti_prodotto_ca_p")
	public Float getElettrolitiProdottoCaP() {
		return elettrolitiProdottoCaP;
	}

	public void setElettrolitiProdottoCaP(Float elettrolitiProdottoCaP) {
		this.elettrolitiProdottoCaP = elettrolitiProdottoCaP;
	}

	@Column(name = "elettroliti_cloro_corretto")
	public Float getElettrolitiCloroCorretto() {
		return elettrolitiCloroCorretto;
	}

	public void setElettrolitiCloroCorretto(Float elettrolitiCloroCorretto) {
		this.elettrolitiCloroCorretto = elettrolitiCloroCorretto;
	}

	@Column(name = "elettroliti_fe")
	public Float getElettrolitiFe() {
		return elettrolitiFe;
	}

	public void setElettrolitiFe(Float elettrolitiFe) {
		this.elettrolitiFe = elettrolitiFe;
	}

	@Column(name = "elettroliti_magnesio")
	public Float getElettrolitiMagnesio() {
		return elettrolitiMagnesio;
	}

	public void setElettrolitiMagnesio(Float elettrolitiMagnesio) {
		this.elettrolitiMagnesio = elettrolitiMagnesio;
	}

	@Column(name = "emogas_arterioso_ph")
	public Float getEmogasArteriosoPh() {
		return emogasArteriosoPh;
	}

	public void setEmogasArteriosoPh(Float emogasArteriosoPh) {
		this.emogasArteriosoPh = emogasArteriosoPh;
	}

	@Column(name = "emogas_arterioso_t_co2")
	public Float getEmogasArteriosoTco2() {
		return emogasArteriosoTco2;
	}

	public void setEmogasArteriosoTco2(Float emogasArteriosoTco2) {
		this.emogasArteriosoTco2 = emogasArteriosoTco2;
	}

	@Column(name = "emogas_arterioso_h_co3")
	public Float getEmogasArteriosoHco3() {
		return emogasArteriosoHco3;
	}

	public void setEmogasArteriosoHco3(Float emogasArteriosoHco3) {
		this.emogasArteriosoHco3 = emogasArteriosoHco3;
	}

	@Column(name = "emogas_arterioso_p_co2")
	public Float getEmogasArteriosoPco2() {
		return emogasArteriosoPco2;
	}

	public void setEmogasArteriosoPco2(Float emogasArteriosoPco2) {
		this.emogasArteriosoPco2 = emogasArteriosoPco2;
	}

	@Column(name = "emogas_arterioso_p_o2")
	public Float getEmogasArteriosoPo2() {
		return emogasArteriosoPo2;
	}

	public void setEmogasArteriosoPo2(Float emogasArteriosoPo2) {
		this.emogasArteriosoPo2 = emogasArteriosoPo2;
	}

	@Column(name = "emogas_arterioso_anion_gap")
	public Float getEmogasArteriosoAnionGap() {
		return emogasArteriosoAnionGap;
	}

	public void setEmogasArteriosoAnionGap(Float emogasArteriosoAnionGap) {
		this.emogasArteriosoAnionGap = emogasArteriosoAnionGap;
	}

	@Column(name = "emogas_venoso_ph")
	public Float getEmogasVenosoPh() {
		return emogasVenosoPh;
	}

	public void setEmogasVenosoPh(Float emogasVenosoPh) {
		this.emogasVenosoPh = emogasVenosoPh;
	}

	@Column(name = "emogas_venoso_t_co2")
	public Float getEmogasVenosoTco2() {
		return emogasVenosoTco2;
	}

	public void setEmogasVenosoTco2(Float emogasVenosoTco2) {
		this.emogasVenosoTco2 = emogasVenosoTco2;
	}

	@Column(name = "emogas_venoso_h_co3")
	public Float getEmogasVenosoHco3() {
		return emogasVenosoHco3;
	}

	public void setEmogasVenosoHco3(Float emogasVenosoHco3) {
		this.emogasVenosoHco3 = emogasVenosoHco3;
	}

	@Column(name = "emogas_venoso_p_co2")
	public Float getEmogasVenosoPco2() {
		return emogasVenosoPco2;
	}

	public void setEmogasVenosoPco2(Float emogasVenosoPco2) {
		this.emogasVenosoPco2 = emogasVenosoPco2;
	}

	@Column(name = "emogas_venoso_p_o2")
	public Float getEmogasVenosoPo2() {
		return emogasVenosoPo2;
	}

	public void setEmogasVenosoPo2(Float emogasVenosoPo2) {
		this.emogasVenosoPo2 = emogasVenosoPo2;
	}

	@Column(name = "emogas_venoso_anion_gap")
	public Float getEmogasVenosoAnionGap() {
		return emogasVenosoAnionGap;
	}

	public void setEmogasVenosoAnionGap(Float emogasVenosoAnionGap) {
		this.emogasVenosoAnionGap = emogasVenosoAnionGap;
	}

	@Column(name = "enzimatici_ast")
	public Float getEnzimaticiAst() {
		return enzimaticiAst;
	}

	public void setEnzimaticiAst(Float enzimaticiAst) {
		this.enzimaticiAst = enzimaticiAst;
	}

	@Column(name = "enzimatici_alt")
	public Float getEnzimaticiAlt() {
		return enzimaticiAlt;
	}

	public void setEnzimaticiAlt(Float enzimaticiAlt) {
		this.enzimaticiAlt = enzimaticiAlt;
	}

	@Column(name = "enzimatici_alp")
	public Float getEnzimaticiAlp() {
		return enzimaticiAlp;
	}

	public void setEnzimaticiAlp(Float enzimaticiAlp) {
		this.enzimaticiAlp = enzimaticiAlp;
	}

	@Column(name = "enzimatici_ggt")
	public Float getEnzimaticiGgt() {
		return enzimaticiGgt;
	}

	public void setEnzimaticiGgt(Float enzimaticiGgt) {
		this.enzimaticiGgt = enzimaticiGgt;
	}

	@Column(name = "enzimatici_cpk")
	public Float getEnzimaticiCpk() {
		return enzimaticiCpk;
	}

	public void setEnzimaticiCpk(Float enzimaticiCpk) {
		this.enzimaticiCpk = enzimaticiCpk;
	}

	@Column(name = "enzimatici_ldh")
	public Float getEnzimaticiLdh() {
		return enzimaticiLdh;
	}

	public void setEnzimaticiLdh(Float enzimaticiLdh) {
		this.enzimaticiLdh = enzimaticiLdh;
	}

	@Column(name = "enzimatici_amilasi")
	public Float getEnzimaticiAmilasi() {
		return enzimaticiAmilasi;
	}

	public void setEnzimaticiAmilasi(Float enzimaticiAmilasi) {
		this.enzimaticiAmilasi = enzimaticiAmilasi;
	}

	@Column(name = "enzimatici_lipasi")
	public Float getEnzimaticiLipasi() {
		return enzimaticiLipasi;
	}

	public void setEnzimaticiLipasi(Float enzimaticiLipasi) {
		this.enzimaticiLipasi = enzimaticiLipasi;
	}

	@Column(name = "enzimatici_fruttosamina")
	public Float getEnzimaticiFruttosamina() {
		return enzimaticiFruttosamina;
	}

	public void setEnzimaticiFruttosamina(Float enzimaticiFruttosamina) {
		this.enzimaticiFruttosamina = enzimaticiFruttosamina;
	}

	@Column(name = "enzimatici_trigliceridi")
	public Float getEnzimaticiTrigliceridi() {
		return enzimaticiTrigliceridi;
	}

	public void setEnzimaticiTrigliceridi(Float enzimaticiTrigliceridi) {
		this.enzimaticiTrigliceridi = enzimaticiTrigliceridi;
	}

	@Column(name = "elettroforesi_albumine")
	public Float getElettroforesiAlbumine() {
		return elettroforesiAlbumine;
	}

	public void setElettroforesiAlbumine(Float elettroforesiAlbumine) {
		this.elettroforesiAlbumine = elettroforesiAlbumine;
	}

	@Column(name = "elettroforesi_alfa1")
	public Float getElettroforesiAlfa1() {
		return elettroforesiAlfa1;
	}

	public void setElettroforesiAlfa1(Float elettroforesiAlfa1) {
		this.elettroforesiAlfa1 = elettroforesiAlfa1;
	}

	@Column(name = "elettroforesi_alfa2")
	public Float getElettroforesiAlfa2() {
		return elettroforesiAlfa2;
	}

	public void setElettroforesiAlfa2(Float elettroforesiAlfa2) {
		this.elettroforesiAlfa2 = elettroforesiAlfa2;
	}

	@Column(name = "elettroforesi_beta")
	public Float getElettroforesiBeta() {
		return elettroforesiBeta;
	}

	public void setElettroforesiBeta(Float elettroforesiBeta) {
		this.elettroforesiBeta = elettroforesiBeta;
	}

	@Column(name = "elettroforesi_gamma")
	public Float getElettroforesiGamma() {
		return elettroforesiGamma;
	}

	public void setElettroforesiGamma(Float elettroforesiGamma) {
		this.elettroforesiGamma = elettroforesiGamma;
	}

	@Column(name = "elettroforesi_globuline")
	public Float getElettroforesiGlobuline() {
		return elettroforesiGlobuline;
	}

	public void setElettroforesiGlobuline(Float elettroforesiGlobuline) {
		this.elettroforesiGlobuline = elettroforesiGlobuline;
	}

	@Column(name = "elettroforesi_rapporto_a_g")
	public Float getElettroforesiRapportoAG() {
		return elettroforesiRapportoAG;
	}

	public void setElettroforesiRapportoAG(Float elettroforesiRapportoAG) {
		this.elettroforesiRapportoAG = elettroforesiRapportoAG;
	}

	@Column(name = "acidi_biliari")
	public Float getAcidiBiliari() {
		return acidiBiliari;
	}

	public void setAcidiBiliari(Float acidiBiliari) {
		this.acidiBiliari = acidiBiliari;
	}

	@Column(name = "acidi_biliari_postp")
	public Float getAcidiBiliariPostp() {
		return acidiBiliariPostp;
	}

	public void setAcidiBiliariPostp(Float acidiBiliariPostp) {
		this.acidiBiliariPostp = acidiBiliariPostp;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Sangue";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare Esame Sangue";
	}
	
	

}
