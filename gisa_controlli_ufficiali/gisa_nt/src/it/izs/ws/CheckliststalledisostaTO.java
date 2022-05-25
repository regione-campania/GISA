/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per checkliststalledisostaTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checkliststalledisostaTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altroRappresentante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clssId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataScadPrescrizioni" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataVerificaSs" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="enteAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagLattazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPrescrizioneEsitoSs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagRappresentante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numAnimaliMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numLattazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="prescrizioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requisitiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkliststalledisostaTO", propOrder = {
    "altroRappresentante",
    "caId",
    "clssId",
    "dataAutorizzazione",
    "dataScadPrescrizioni",
    "dataVerificaSs",
    "enteAutorizzazione",
    "flagLattazione",
    "flagPrescrizioneEsitoSs",
    "flagRappresentante",
    "numAnimaliMax",
    "numAutorizzazione",
    "numLattazione",
    "prescrizioni",
    "requisitiXml",
    "secondoControllore"
})
@XmlSeeAlso({
    CheckliststalledisostaWsTO.class
})
public class CheckliststalledisostaTO {

    protected String altroRappresentante;
    protected Integer caId;
    protected Integer clssId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAutorizzazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadPrescrizioni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerificaSs;
    protected String enteAutorizzazione;
    protected String flagLattazione;
    protected String flagPrescrizioneEsitoSs;
    protected String flagRappresentante;
    protected Integer numAnimaliMax;
    protected String numAutorizzazione;
    protected Integer numLattazione;
    protected String prescrizioni;
    protected String requisitiXml;
    protected String secondoControllore;

    /**
     * Recupera il valore della proprieta altroRappresentante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltroRappresentante() {
        return altroRappresentante;
    }

    /**
     * Imposta il valore della proprieta altroRappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltroRappresentante(String value) {
        this.altroRappresentante = value;
    }

    /**
     * Recupera il valore della proprieta caId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCaId() {
        return caId;
    }

    /**
     * Imposta il valore della proprieta caId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCaId(Integer value) {
        this.caId = value;
    }

    /**
     * Recupera il valore della proprieta clssId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClssId() {
        return clssId;
    }

    /**
     * Imposta il valore della proprieta clssId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClssId(Integer value) {
        this.clssId = value;
    }

    /**
     * Recupera il valore della proprieta dataAutorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAutorizzazione() {
        return dataAutorizzazione;
    }

    /**
     * Imposta il valore della proprieta dataAutorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAutorizzazione(XMLGregorianCalendar value) {
        this.dataAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprieta dataScadPrescrizioni.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadPrescrizioni() {
        return dataScadPrescrizioni;
    }

    /**
     * Imposta il valore della proprieta dataScadPrescrizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadPrescrizioni(XMLGregorianCalendar value) {
        this.dataScadPrescrizioni = value;
    }

    /**
     * Recupera il valore della proprieta dataVerificaSs.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerificaSs() {
        return dataVerificaSs;
    }

    /**
     * Imposta il valore della proprieta dataVerificaSs.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerificaSs(XMLGregorianCalendar value) {
        this.dataVerificaSs = value;
    }

    /**
     * Recupera il valore della proprieta enteAutorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteAutorizzazione() {
        return enteAutorizzazione;
    }

    /**
     * Imposta il valore della proprieta enteAutorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteAutorizzazione(String value) {
        this.enteAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprieta flagLattazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagLattazione() {
        return flagLattazione;
    }

    /**
     * Imposta il valore della proprieta flagLattazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagLattazione(String value) {
        this.flagLattazione = value;
    }

    /**
     * Recupera il valore della proprieta flagPrescrizioneEsitoSs.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPrescrizioneEsitoSs() {
        return flagPrescrizioneEsitoSs;
    }

    /**
     * Imposta il valore della proprieta flagPrescrizioneEsitoSs.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPrescrizioneEsitoSs(String value) {
        this.flagPrescrizioneEsitoSs = value;
    }

    /**
     * Recupera il valore della proprieta flagRappresentante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRappresentante() {
        return flagRappresentante;
    }

    /**
     * Imposta il valore della proprieta flagRappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRappresentante(String value) {
        this.flagRappresentante = value;
    }

    /**
     * Recupera il valore della proprieta numAnimaliMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAnimaliMax() {
        return numAnimaliMax;
    }

    /**
     * Imposta il valore della proprieta numAnimaliMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAnimaliMax(Integer value) {
        this.numAnimaliMax = value;
    }

    /**
     * Recupera il valore della proprieta numAutorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAutorizzazione() {
        return numAutorizzazione;
    }

    /**
     * Imposta il valore della proprieta numAutorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAutorizzazione(String value) {
        this.numAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprieta numLattazione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumLattazione() {
        return numLattazione;
    }

    /**
     * Imposta il valore della proprieta numLattazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumLattazione(Integer value) {
        this.numLattazione = value;
    }

    /**
     * Recupera il valore della proprieta prescrizioni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrescrizioni() {
        return prescrizioni;
    }

    /**
     * Imposta il valore della proprieta prescrizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrescrizioni(String value) {
        this.prescrizioni = value;
    }

    /**
     * Recupera il valore della proprieta requisitiXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequisitiXml() {
        return requisitiXml;
    }

    /**
     * Imposta il valore della proprieta requisitiXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequisitiXml(String value) {
        this.requisitiXml = value;
    }

    /**
     * Recupera il valore della proprieta secondoControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondoControllore() {
        return secondoControllore;
    }

    /**
     * Imposta il valore della proprieta secondoControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondoControllore(String value) {
        this.secondoControllore = value;
    }

}
