/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.sinsa.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per prelievoWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="prelievoWsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assistCodFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="campioniPrelevati" type="{http://sinsa.izs.it/services}campionePrelevatoWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contaminanti" type="{http://sinsa.izs.it/services}contaminanteWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contaminantiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataAccettazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFinePrelievo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataInizioIspezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataPrelievo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataVerbale" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ispezioneFollowUp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ispezioneMotivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ispezioneNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ispezioneNumero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ispezioneNumeroRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ispezioneTipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="laboratorioCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="luogoPrelievoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="metodoCampionamentoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motivoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroAccettazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroScheda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroVerbale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pianoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelevatori" type="{http://sinsa.izs.it/services}prelevatoreWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prelevatoriXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelievoId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sitoAslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoComCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoLatitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sitoLongitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sitoProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targaAutomezzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoImpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prelievoWsTO", propOrder = {
    "assistCodFiscale",
    "campioniPrelevati",
    "contaminanti",
    "contaminantiXml",
    "cun",
    "dataAccettazione",
    "dataFinePrelievo",
    "dataInizioIspezione",
    "dataPrelievo",
    "dataVerbale",
    "ispezioneFollowUp",
    "ispezioneMotivo",
    "ispezioneNote",
    "ispezioneNumero",
    "ispezioneNumeroRiferimento",
    "ispezioneTipo",
    "laboratorioCodice",
    "luogoPrelievoCodice",
    "metodoCampionamentoCodice",
    "motivoCodice",
    "note",
    "numeroAccettazione",
    "numeroScheda",
    "numeroVerbale",
    "pianoCodice",
    "prelevatori",
    "prelevatoriXml",
    "prelievoId",
    "sitoAslCodice",
    "sitoCodice",
    "sitoComCodice",
    "sitoLatitudine",
    "sitoLongitudine",
    "sitoProSigla",
    "targaAutomezzo",
    "tipoImpresa"
})
public class PrelievoWsTO {

    protected String assistCodFiscale;
    @XmlElement(nillable = true)
    protected List<CampionePrelevatoWsTO> campioniPrelevati;
    @XmlElement(nillable = true)
    protected List<ContaminanteWsTO> contaminanti;
    protected String contaminantiXml;
    protected String cun;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccettazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePrelievo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioIspezione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPrelievo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerbale;
    protected String ispezioneFollowUp;
    protected String ispezioneMotivo;
    protected String ispezioneNote;
    protected String ispezioneNumero;
    protected String ispezioneNumeroRiferimento;
    protected String ispezioneTipo;
    protected String laboratorioCodice;
    protected String luogoPrelievoCodice;
    protected String metodoCampionamentoCodice;
    protected String motivoCodice;
    protected String note;
    protected String numeroAccettazione;
    protected String numeroScheda;
    protected String numeroVerbale;
    protected String pianoCodice;
    @XmlElement(nillable = true)
    protected List<PrelevatoreWsTO> prelevatori;
    protected String prelevatoriXml;
    protected Integer prelievoId;
    protected String sitoAslCodice;
    protected String sitoCodice;
    protected String sitoComCodice;
    protected Double sitoLatitudine;
    protected Double sitoLongitudine;
    protected String sitoProSigla;
    protected String targaAutomezzo;
    protected String tipoImpresa;

    /**
     * Recupera il valore della proprieta assistCodFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssistCodFiscale() {
        return assistCodFiscale;
    }

    /**
     * Imposta il valore della proprieta assistCodFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssistCodFiscale(String value) {
        this.assistCodFiscale = value;
    }

    /**
     * Gets the value of the campioniPrelevati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the campioniPrelevati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCampioniPrelevati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampionePrelevatoWsTO }
     * 
     * 
     */
    public List<CampionePrelevatoWsTO> getCampioniPrelevati() {
        if (campioniPrelevati == null) {
            campioniPrelevati = new ArrayList<CampionePrelevatoWsTO>();
        }
        return this.campioniPrelevati;
    }

    /**
     * Gets the value of the contaminanti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contaminanti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContaminanti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContaminanteWsTO }
     * 
     * 
     */
    public List<ContaminanteWsTO> getContaminanti() {
        if (contaminanti == null) {
            contaminanti = new ArrayList<ContaminanteWsTO>();
        }
        return this.contaminanti;
    }

    /**
     * Recupera il valore della proprieta contaminantiXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContaminantiXml() {
        return contaminantiXml;
    }

    /**
     * Imposta il valore della proprieta contaminantiXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContaminantiXml(String value) {
        this.contaminantiXml = value;
    }

    /**
     * Recupera il valore della proprieta cun.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCun() {
        return cun;
    }

    /**
     * Imposta il valore della proprieta cun.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCun(String value) {
        this.cun = value;
    }

    /**
     * Recupera il valore della proprieta dataAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAccettazione() {
        return dataAccettazione;
    }

    /**
     * Imposta il valore della proprieta dataAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAccettazione(XMLGregorianCalendar value) {
        this.dataAccettazione = value;
    }

    /**
     * Recupera il valore della proprieta dataFinePrelievo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinePrelievo() {
        return dataFinePrelievo;
    }

    /**
     * Imposta il valore della proprieta dataFinePrelievo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinePrelievo(XMLGregorianCalendar value) {
        this.dataFinePrelievo = value;
    }

    /**
     * Recupera il valore della proprieta dataInizioIspezione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioIspezione() {
        return dataInizioIspezione;
    }

    /**
     * Imposta il valore della proprieta dataInizioIspezione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioIspezione(XMLGregorianCalendar value) {
        this.dataInizioIspezione = value;
    }

    /**
     * Recupera il valore della proprieta dataPrelievo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPrelievo() {
        return dataPrelievo;
    }

    /**
     * Imposta il valore della proprieta dataPrelievo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPrelievo(XMLGregorianCalendar value) {
        this.dataPrelievo = value;
    }

    /**
     * Recupera il valore della proprieta dataVerbale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerbale() {
        return dataVerbale;
    }

    /**
     * Imposta il valore della proprieta dataVerbale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerbale(XMLGregorianCalendar value) {
        this.dataVerbale = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneFollowUp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneFollowUp() {
        return ispezioneFollowUp;
    }

    /**
     * Imposta il valore della proprieta ispezioneFollowUp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneFollowUp(String value) {
        this.ispezioneFollowUp = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneMotivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneMotivo() {
        return ispezioneMotivo;
    }

    /**
     * Imposta il valore della proprieta ispezioneMotivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneMotivo(String value) {
        this.ispezioneMotivo = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneNote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneNote() {
        return ispezioneNote;
    }

    /**
     * Imposta il valore della proprieta ispezioneNote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneNote(String value) {
        this.ispezioneNote = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneNumero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneNumero() {
        return ispezioneNumero;
    }

    /**
     * Imposta il valore della proprieta ispezioneNumero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneNumero(String value) {
        this.ispezioneNumero = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneNumeroRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneNumeroRiferimento() {
        return ispezioneNumeroRiferimento;
    }

    /**
     * Imposta il valore della proprieta ispezioneNumeroRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneNumeroRiferimento(String value) {
        this.ispezioneNumeroRiferimento = value;
    }

    /**
     * Recupera il valore della proprieta ispezioneTipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIspezioneTipo() {
        return ispezioneTipo;
    }

    /**
     * Imposta il valore della proprieta ispezioneTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIspezioneTipo(String value) {
        this.ispezioneTipo = value;
    }

    /**
     * Recupera il valore della proprieta laboratorioCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaboratorioCodice() {
        return laboratorioCodice;
    }

    /**
     * Imposta il valore della proprieta laboratorioCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaboratorioCodice(String value) {
        this.laboratorioCodice = value;
    }

    /**
     * Recupera il valore della proprieta luogoPrelievoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuogoPrelievoCodice() {
        return luogoPrelievoCodice;
    }

    /**
     * Imposta il valore della proprieta luogoPrelievoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuogoPrelievoCodice(String value) {
        this.luogoPrelievoCodice = value;
    }

    /**
     * Recupera il valore della proprieta metodoCampionamentoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetodoCampionamentoCodice() {
        return metodoCampionamentoCodice;
    }

    /**
     * Imposta il valore della proprieta metodoCampionamentoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetodoCampionamentoCodice(String value) {
        this.metodoCampionamentoCodice = value;
    }

    /**
     * Recupera il valore della proprieta motivoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoCodice() {
        return motivoCodice;
    }

    /**
     * Imposta il valore della proprieta motivoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoCodice(String value) {
        this.motivoCodice = value;
    }

    /**
     * Recupera il valore della proprieta note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprieta note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprieta numeroAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAccettazione() {
        return numeroAccettazione;
    }

    /**
     * Imposta il valore della proprieta numeroAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAccettazione(String value) {
        this.numeroAccettazione = value;
    }

    /**
     * Recupera il valore della proprieta numeroScheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroScheda() {
        return numeroScheda;
    }

    /**
     * Imposta il valore della proprieta numeroScheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroScheda(String value) {
        this.numeroScheda = value;
    }

    /**
     * Recupera il valore della proprieta numeroVerbale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroVerbale() {
        return numeroVerbale;
    }

    /**
     * Imposta il valore della proprieta numeroVerbale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroVerbale(String value) {
        this.numeroVerbale = value;
    }

    /**
     * Recupera il valore della proprieta pianoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPianoCodice() {
        return pianoCodice;
    }

    /**
     * Imposta il valore della proprieta pianoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPianoCodice(String value) {
        this.pianoCodice = value;
    }

    /**
     * Gets the value of the prelevatori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prelevatori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrelevatori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrelevatoreWsTO }
     * 
     * 
     */
    public List<PrelevatoreWsTO> getPrelevatori() {
        if (prelevatori == null) {
            prelevatori = new ArrayList<PrelevatoreWsTO>();
        }
        return this.prelevatori;
    }

    /**
     * Recupera il valore della proprieta prelevatoriXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrelevatoriXml() {
        return prelevatoriXml;
    }

    /**
     * Imposta il valore della proprieta prelevatoriXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrelevatoriXml(String value) {
        this.prelevatoriXml = value;
    }

    /**
     * Recupera il valore della proprieta prelievoId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrelievoId() {
        return prelievoId;
    }

    /**
     * Imposta il valore della proprieta prelievoId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrelievoId(Integer value) {
        this.prelievoId = value;
    }

    /**
     * Recupera il valore della proprieta sitoAslCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoAslCodice() {
        return sitoAslCodice;
    }

    /**
     * Imposta il valore della proprieta sitoAslCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoAslCodice(String value) {
        this.sitoAslCodice = value;
    }

    /**
     * Recupera il valore della proprieta sitoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoCodice() {
        return sitoCodice;
    }

    /**
     * Imposta il valore della proprieta sitoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoCodice(String value) {
        this.sitoCodice = value;
    }

    /**
     * Recupera il valore della proprieta sitoComCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoComCodice() {
        return sitoComCodice;
    }

    /**
     * Imposta il valore della proprieta sitoComCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoComCodice(String value) {
        this.sitoComCodice = value;
    }

    /**
     * Recupera il valore della proprieta sitoLatitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSitoLatitudine() {
        return sitoLatitudine;
    }

    /**
     * Imposta il valore della proprieta sitoLatitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSitoLatitudine(Double value) {
        this.sitoLatitudine = value;
    }

    /**
     * Recupera il valore della proprieta sitoLongitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSitoLongitudine() {
        return sitoLongitudine;
    }

    /**
     * Imposta il valore della proprieta sitoLongitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSitoLongitudine(Double value) {
        this.sitoLongitudine = value;
    }

    /**
     * Recupera il valore della proprieta sitoProSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoProSigla() {
        return sitoProSigla;
    }

    /**
     * Imposta il valore della proprieta sitoProSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoProSigla(String value) {
        this.sitoProSigla = value;
    }

    /**
     * Recupera il valore della proprieta targaAutomezzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargaAutomezzo() {
        return targaAutomezzo;
    }

    /**
     * Imposta il valore della proprieta targaAutomezzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargaAutomezzo(String value) {
        this.targaAutomezzo = value;
    }

    /**
     * Recupera il valore della proprieta tipoImpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoImpresa() {
        return tipoImpresa;
    }

    /**
     * Imposta il valore della proprieta tipoImpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoImpresa(String value) {
        this.tipoImpresa = value;
    }

}
