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
 * <p>Classe Java per checklistsostanzevietateTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistsostanzevietateTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clsvId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataIrregolarita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataScadPrescrizioni" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataVerifica" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="esitoVerifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagNotificaOp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagStatoProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoSanzione" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="prescrizioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requisitiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistsostanzevietateTO", propOrder = {
    "caId",
    "clsvId",
    "dataIrregolarita",
    "dataScadPrescrizioni",
    "dataVerifica",
    "esitoVerifica",
    "flagNotificaOp",
    "flagStatoProcedimento",
    "importoSanzione",
    "prescrizioni",
    "requisitiXml",
    "secondoControllore",
    "tsCodice",
    "tsDescrizione",
    "tsId"
})
@XmlSeeAlso({
    ChecklistsostanzevietateWsTO.class
})
public class ChecklistsostanzevietateTO {

    protected Integer caId;
    protected Integer clsvId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIrregolarita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadPrescrizioni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerifica;
    protected String esitoVerifica;
    protected String flagNotificaOp;
    protected String flagStatoProcedimento;
    protected Double importoSanzione;
    protected String prescrizioni;
    protected String requisitiXml;
    protected String secondoControllore;
    protected String tsCodice;
    protected String tsDescrizione;
    protected Integer tsId;

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
     * Recupera il valore della proprieta clsvId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClsvId() {
        return clsvId;
    }

    /**
     * Imposta il valore della proprieta clsvId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClsvId(Integer value) {
        this.clsvId = value;
    }

    /**
     * Recupera il valore della proprieta dataIrregolarita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIrregolarita() {
        return dataIrregolarita;
    }

    /**
     * Imposta il valore della proprieta dataIrregolarita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIrregolarita(XMLGregorianCalendar value) {
        this.dataIrregolarita = value;
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
     * Recupera il valore della proprieta dataVerifica.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerifica() {
        return dataVerifica;
    }

    /**
     * Imposta il valore della proprieta dataVerifica.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerifica(XMLGregorianCalendar value) {
        this.dataVerifica = value;
    }

    /**
     * Recupera il valore della proprieta esitoVerifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoVerifica() {
        return esitoVerifica;
    }

    /**
     * Imposta il valore della proprieta esitoVerifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoVerifica(String value) {
        this.esitoVerifica = value;
    }

    /**
     * Recupera il valore della proprieta flagNotificaOp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagNotificaOp() {
        return flagNotificaOp;
    }

    /**
     * Imposta il valore della proprieta flagNotificaOp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagNotificaOp(String value) {
        this.flagNotificaOp = value;
    }

    /**
     * Recupera il valore della proprieta flagStatoProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagStatoProcedimento() {
        return flagStatoProcedimento;
    }

    /**
     * Imposta il valore della proprieta flagStatoProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagStatoProcedimento(String value) {
        this.flagStatoProcedimento = value;
    }

    /**
     * Recupera il valore della proprieta importoSanzione.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getImportoSanzione() {
        return importoSanzione;
    }

    /**
     * Imposta il valore della proprieta importoSanzione.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setImportoSanzione(Double value) {
        this.importoSanzione = value;
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

    /**
     * Recupera il valore della proprieta tsCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsCodice() {
        return tsCodice;
    }

    /**
     * Imposta il valore della proprieta tsCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsCodice(String value) {
        this.tsCodice = value;
    }

    /**
     * Recupera il valore della proprieta tsDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsDescrizione() {
        return tsDescrizione;
    }

    /**
     * Imposta il valore della proprieta tsDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsDescrizione(String value) {
        this.tsDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta tsId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTsId() {
        return tsId;
    }

    /**
     * Imposta il valore della proprieta tsId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTsId(Integer value) {
        this.tsId = value;
    }

}
