/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupAritmie;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import it.us.web.util.bean.NotNullDependencies;

@Entity
@Table(name = "ecg", schema = "public")
@Where( clause = "trashed_date is null" )
@NotNullDependencies(listField = "intervalloQT;intervalloRR", allNull = true, message = "Valorizzare sia Intervallo QT che Intervallo RR")

public class Ecg implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = 310529298751894357L;
	private int id;
	private CartellaClinica cartellaClinica;
	private Date dataRichiesta;
	private Date dataEsito;
	private String ritmo;
	private Integer frequenza;
	private Integer ampiezzaOndaP;
	private Integer durataOndaP;
	private Integer ampiezzaOndaR;
	private Integer durataPR;
	private Integer durataComplessoQRS;
	private String durataST;
	private Integer durataSTInQuadratini;
	private Integer ampiezzaOndaT;
	private Integer intervalloQT;
	private Integer intervalloRR;
	private String QTCorretto;
	private String asse;
	private String diagnosi;
	private Set<LookupAritmie> aritmie;
	private Date entered;
	private BUtente enteredBy;
	private Date modified;
	private BUtente modifiedBy;
	private Date trashedDate;
	

	public Ecg()
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

	@Temporal(TemporalType.DATE)
	@Column(name = "data_richiesta")
	@NotNull(message="Inserire un valore")
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_esito")
	public Date getDataEsito() {
		return this.dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}

	@Column(name = "ritmo", length = 1)
	@Length(min = 1, max = 1,message="Selezionare un valore")
	public String getRitmo() {
		return this.ritmo;
	}

	public void setRitmo(String ritmo) {
		this.ritmo = ritmo;
	}

	@Column(name = "frequenza")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getFrequenza() {
		return this.frequenza;
	}

	public void setFrequenza(Integer frequenza) {
		this.frequenza = frequenza;
	}

	@Column(name = "asse", length = 64)
	@Length(max = 64)
	public String getAsse() {
		return this.asse;
	}

	public void setAsse(String asse) {
		this.asse = asse;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered")
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date")
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
	
	
	@Column(name = "ampiezza_onda_p")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getAmpiezzaOndaP() {
		return ampiezzaOndaP;
	}

	public void setAmpiezzaOndaP(Integer ampiezzaOndaP) {
		this.ampiezzaOndaP = ampiezzaOndaP;
	}

	@Column(name = "durata_onda_p")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getDurataOndaP() {
		return durataOndaP;
	}

	public void setDurataOndaP(Integer durataOndaP) {
		this.durataOndaP = durataOndaP;
	}
	

	@Column(name = "ampiezza_onda_r")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getAmpiezzaOndaR() {
		return ampiezzaOndaR;
	}

	public void setAmpiezzaOndaR(Integer ampiezzaOndaR) {
		this.ampiezzaOndaR = ampiezzaOndaR;
	}

	@Column(name = "durata_pr")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getDurataPR() {
		return durataPR;
	}

	public void setDurataPR(Integer durataPR) {
		this.durataPR = durataPR;
	}

	@Column(name = "durata_complesso_qrs")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getDurataComplessoQRS() {
		return durataComplessoQRS;
	}

	public void setDurataComplessoQRS(Integer durataComplessoQRS) {
		this.durataComplessoQRS = durataComplessoQRS;
	}

	@Column(name = "durata_st", length = 1)
	@Length(max = 1)
	public String getDurataST() {
		return durataST;
	}

	public void setDurataST(String durataST) {
		this.durataST = durataST;
	}
	
	@Column(name = "durata_st_quadratini")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getDurataSTInQuadratini() {
		return durataSTInQuadratini;
	}

	public void setDurataSTInQuadratini(Integer durataSTInQuadratini) {
		this.durataSTInQuadratini = durataSTInQuadratini;
	}

	@Column(name = "ampiezza_onda_t")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getAmpiezzaOndaT() {
		return ampiezzaOndaT;
	}

	public void setAmpiezzaOndaT(Integer ampiezzaOndaT) {
		this.ampiezzaOndaT = ampiezzaOndaT;
	}

	@Column(name = "intervallo_qt")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getIntervalloQT() {
		return intervalloQT;
	}

	public void setIntervalloQT(Integer intervalloQT) {
		this.intervalloQT = intervalloQT;
	}
	
	@Column(name = "intervallo_rr")
	@Range(min=1,message = "Inserire un valore positivo")
	public Integer getIntervalloRR() {
		return intervalloRR;
	}

	public void setIntervalloRR(Integer intervalloRR) {
		this.intervalloRR = intervalloRR;
	}
	
	@Column(name = "qt_corretto")
	@Length(max = 64,message="Il valore supera 64 caratteri")
	public String getQTCorretto() {
		return QTCorretto;
	}

	public void setQTCorretto(String QTCorretto) {
		this.QTCorretto = QTCorretto;
	}
	
	
	/**
	 * Restituisce l'esito dell'esame:
	 * N:    esito negativo;
	 * P:    esito positivo(in tal caso getAritmia ritorna la disfunzione rilevata)
	 * null: esito non valorizzato 
	 * @return
	 */
	@Column(name = "diagnosi", length = 1)
	@Length(max = 1)
	public String getDiagnosi() {
		return this.diagnosi;
	}

	/**
	 * Setta l'esito dell'esame:
	 * N:    esito negativo;
	 * P:    esito positivo(in tal caso getAritmia ritorna l'aritmia rilevata)
	 * null: esito non valorizzato 
	 * @return
	 */
	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}
	
	/**
	 * Restituisce l'aritmia rilevata dall'esame, se la diagnosi è positiva
	 * @return
	 */
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ecg_aritmie", 
			joinColumns =        { @JoinColumn(name = "id_ecg", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_aritmia", nullable = false, updatable = false) })
	public Set<LookupAritmie> getAritmie() {
		return aritmie;
	}

	/**
	 * Setta le aritmie rilevate dall'esame, se la diagnosi è positiva
	 * @return
	 */
	public void setAritmie(Set<LookupAritmie> aritmie) {
		this.aritmie = aritmie;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Ecg";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare Ecg";
	}
}
