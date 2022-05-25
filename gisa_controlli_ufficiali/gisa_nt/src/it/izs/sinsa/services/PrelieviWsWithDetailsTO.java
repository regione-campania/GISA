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
 * <p>Classe Java per prelieviWsWithDetailsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="prelieviWsWithDetailsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altreInformazioni">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="altreInformazioniStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="campioniPrelevati" type="{http://sinsa.izs.it/services}campioniPrelevatiWsWithDetailsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="comCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtPrelievo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="enteCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enteDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motivoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motivoDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroScheda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroVerbale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pianoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pianoDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelevatoreCodFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelevatoreCognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelevatoreNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prelievoId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="proSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoComCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoComDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitoLatitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sitoLongitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sitoProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sliCodFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sliPIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sliRagioneSociale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spiCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spiIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spiLatitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="spiLocalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spiLongitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="spiNumRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spiNumRiconoscimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prelieviWsWithDetailsTO", propOrder = {
    "altreInformazioni",
    "altreInformazioniStr",
    "aslCodice",
    "aslDescrizione",
    "campioniPrelevati",
    "comCodice",
    "comDescrizione",
    "dtPrelievo",
    "enteCodice",
    "enteDescrizione",
    "motivoCodice",
    "motivoDescrizione",
    "numeroScheda",
    "numeroVerbale",
    "pianoCodice",
    "pianoDescrizione",
    "prelevatoreCodFiscale",
    "prelevatoreCognome",
    "prelevatoreNome",
    "prelievoId",
    "proSigla",
    "sitoComCodice",
    "sitoComDescrizione",
    "sitoLatitudine",
    "sitoLongitudine",
    "sitoProSigla",
    "sliCodFiscale",
    "sliPIva",
    "sliRagioneSociale",
    "spiCap",
    "spiIndirizzo",
    "spiLatitudine",
    "spiLocalita",
    "spiLongitudine",
    "spiNumRegistrazione",
    "spiNumRiconoscimento"
})
public class PrelieviWsWithDetailsTO {

    @XmlElement(required = true)
    protected PrelieviWsWithDetailsTO.AltreInformazioni altreInformazioni;
    protected String altreInformazioniStr;
    protected String aslCodice;
    protected String aslDescrizione;
    @XmlElement(nillable = true)
    protected List<CampioniPrelevatiWsWithDetailsTO> campioniPrelevati;
    protected String comCodice;
    protected String comDescrizione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtPrelievo;
    protected String enteCodice;
    protected String enteDescrizione;
    protected String motivoCodice;
    protected String motivoDescrizione;
    protected String numeroScheda;
    protected String numeroVerbale;
    protected String pianoCodice;
    protected String pianoDescrizione;
    protected String prelevatoreCodFiscale;
    protected String prelevatoreCognome;
    protected String prelevatoreNome;
    protected Integer prelievoId;
    protected String proSigla;
    protected String sitoComCodice;
    protected String sitoComDescrizione;
    protected Double sitoLatitudine;
    protected Double sitoLongitudine;
    protected String sitoProSigla;
    protected String sliCodFiscale;
    protected String sliPIva;
    protected String sliRagioneSociale;
    protected String spiCap;
    protected String spiIndirizzo;
    protected Double spiLatitudine;
    protected String spiLocalita;
    protected Double spiLongitudine;
    protected String spiNumRegistrazione;
    protected String spiNumRiconoscimento;

    /**
     * Recupera il valore della proprieta altreInformazioni.
     * 
     * @return
     *     possible object is
     *     {@link PrelieviWsWithDetailsTO.AltreInformazioni }
     *     
     */
    public PrelieviWsWithDetailsTO.AltreInformazioni getAltreInformazioni() {
        return altreInformazioni;
    }

    /**
     * Imposta il valore della proprieta altreInformazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link PrelieviWsWithDetailsTO.AltreInformazioni }
     *     
     */
    public void setAltreInformazioni(PrelieviWsWithDetailsTO.AltreInformazioni value) {
        this.altreInformazioni = value;
    }

    /**
     * Recupera il valore della proprieta altreInformazioniStr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInformazioniStr() {
        return altreInformazioniStr;
    }

    /**
     * Imposta il valore della proprieta altreInformazioniStr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInformazioniStr(String value) {
        this.altreInformazioniStr = value;
    }

    /**
     * Recupera il valore della proprieta aslCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAslCodice() {
        return aslCodice;
    }

    /**
     * Imposta il valore della proprieta aslCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAslCodice(String value) {
        this.aslCodice = value;
    }

    /**
     * Recupera il valore della proprieta aslDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAslDescrizione() {
        return aslDescrizione;
    }

    /**
     * Imposta il valore della proprieta aslDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAslDescrizione(String value) {
        this.aslDescrizione = value;
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
     * {@link CampioniPrelevatiWsWithDetailsTO }
     * 
     * 
     */
    public List<CampioniPrelevatiWsWithDetailsTO> getCampioniPrelevati() {
        if (campioniPrelevati == null) {
            campioniPrelevati = new ArrayList<CampioniPrelevatiWsWithDetailsTO>();
        }
        return this.campioniPrelevati;
    }

    /**
     * Recupera il valore della proprieta comCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCodice() {
        return comCodice;
    }

    /**
     * Imposta il valore della proprieta comCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCodice(String value) {
        this.comCodice = value;
    }

    /**
     * Recupera il valore della proprieta comDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComDescrizione() {
        return comDescrizione;
    }

    /**
     * Imposta il valore della proprieta comDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComDescrizione(String value) {
        this.comDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta dtPrelievo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtPrelievo() {
        return dtPrelievo;
    }

    /**
     * Imposta il valore della proprieta dtPrelievo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtPrelievo(XMLGregorianCalendar value) {
        this.dtPrelievo = value;
    }

    /**
     * Recupera il valore della proprieta enteCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteCodice() {
        return enteCodice;
    }

    /**
     * Imposta il valore della proprieta enteCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteCodice(String value) {
        this.enteCodice = value;
    }

    /**
     * Recupera il valore della proprieta enteDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteDescrizione() {
        return enteDescrizione;
    }

    /**
     * Imposta il valore della proprieta enteDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteDescrizione(String value) {
        this.enteDescrizione = value;
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
     * Recupera il valore della proprieta motivoDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoDescrizione() {
        return motivoDescrizione;
    }

    /**
     * Imposta il valore della proprieta motivoDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoDescrizione(String value) {
        this.motivoDescrizione = value;
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
     * Recupera il valore della proprieta pianoDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPianoDescrizione() {
        return pianoDescrizione;
    }

    /**
     * Imposta il valore della proprieta pianoDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPianoDescrizione(String value) {
        this.pianoDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta prelevatoreCodFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrelevatoreCodFiscale() {
        return prelevatoreCodFiscale;
    }

    /**
     * Imposta il valore della proprieta prelevatoreCodFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrelevatoreCodFiscale(String value) {
        this.prelevatoreCodFiscale = value;
    }

    /**
     * Recupera il valore della proprieta prelevatoreCognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrelevatoreCognome() {
        return prelevatoreCognome;
    }

    /**
     * Imposta il valore della proprieta prelevatoreCognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrelevatoreCognome(String value) {
        this.prelevatoreCognome = value;
    }

    /**
     * Recupera il valore della proprieta prelevatoreNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrelevatoreNome() {
        return prelevatoreNome;
    }

    /**
     * Imposta il valore della proprieta prelevatoreNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrelevatoreNome(String value) {
        this.prelevatoreNome = value;
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
     * Recupera il valore della proprieta proSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProSigla() {
        return proSigla;
    }

    /**
     * Imposta il valore della proprieta proSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProSigla(String value) {
        this.proSigla = value;
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
     * Recupera il valore della proprieta sitoComDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoComDescrizione() {
        return sitoComDescrizione;
    }

    /**
     * Imposta il valore della proprieta sitoComDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoComDescrizione(String value) {
        this.sitoComDescrizione = value;
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
     * Recupera il valore della proprieta sliCodFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSliCodFiscale() {
        return sliCodFiscale;
    }

    /**
     * Imposta il valore della proprieta sliCodFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSliCodFiscale(String value) {
        this.sliCodFiscale = value;
    }

    /**
     * Recupera il valore della proprieta sliPIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSliPIva() {
        return sliPIva;
    }

    /**
     * Imposta il valore della proprieta sliPIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSliPIva(String value) {
        this.sliPIva = value;
    }

    /**
     * Recupera il valore della proprieta sliRagioneSociale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSliRagioneSociale() {
        return sliRagioneSociale;
    }

    /**
     * Imposta il valore della proprieta sliRagioneSociale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSliRagioneSociale(String value) {
        this.sliRagioneSociale = value;
    }

    /**
     * Recupera il valore della proprieta spiCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpiCap() {
        return spiCap;
    }

    /**
     * Imposta il valore della proprieta spiCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpiCap(String value) {
        this.spiCap = value;
    }

    /**
     * Recupera il valore della proprieta spiIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpiIndirizzo() {
        return spiIndirizzo;
    }

    /**
     * Imposta il valore della proprieta spiIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpiIndirizzo(String value) {
        this.spiIndirizzo = value;
    }

    /**
     * Recupera il valore della proprieta spiLatitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSpiLatitudine() {
        return spiLatitudine;
    }

    /**
     * Imposta il valore della proprieta spiLatitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSpiLatitudine(Double value) {
        this.spiLatitudine = value;
    }

    /**
     * Recupera il valore della proprieta spiLocalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpiLocalita() {
        return spiLocalita;
    }

    /**
     * Imposta il valore della proprieta spiLocalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpiLocalita(String value) {
        this.spiLocalita = value;
    }

    /**
     * Recupera il valore della proprieta spiLongitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSpiLongitudine() {
        return spiLongitudine;
    }

    /**
     * Imposta il valore della proprieta spiLongitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSpiLongitudine(Double value) {
        this.spiLongitudine = value;
    }

    /**
     * Recupera il valore della proprieta spiNumRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpiNumRegistrazione() {
        return spiNumRegistrazione;
    }

    /**
     * Imposta il valore della proprieta spiNumRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpiNumRegistrazione(String value) {
        this.spiNumRegistrazione = value;
    }

    /**
     * Recupera il valore della proprieta spiNumRiconoscimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpiNumRiconoscimento() {
        return spiNumRiconoscimento;
    }

    /**
     * Imposta il valore della proprieta spiNumRiconoscimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpiNumRiconoscimento(String value) {
        this.spiNumRiconoscimento = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class AltreInformazioni {

        protected List<PrelieviWsWithDetailsTO.AltreInformazioni.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PrelieviWsWithDetailsTO.AltreInformazioni.Entry }
         * 
         * 
         */
        public List<PrelieviWsWithDetailsTO.AltreInformazioni.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<PrelieviWsWithDetailsTO.AltreInformazioni.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected String key;
            protected String value;

            /**
             * Recupera il valore della proprieta key.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Imposta il valore della proprieta key.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Recupera il valore della proprieta value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Imposta il valore della proprieta value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}
