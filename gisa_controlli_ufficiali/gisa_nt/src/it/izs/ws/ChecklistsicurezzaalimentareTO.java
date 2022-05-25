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
 * <p>Classe Java per checklistsicurezzaalimentareTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistsicurezzaalimentareTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clsaId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataScadPrescrizioni" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataVerificaSa" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagCopiaCheckList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPrescrizioneEsitoSa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "checklistsicurezzaalimentareTO", propOrder = {
    "caId",
    "clsaId",
    "dataScadPrescrizioni",
    "dataVerificaSa",
    "flagCopiaCheckList",
    "flagPrescrizioneEsitoSa",
    "prescrizioni",
    "requisitiXml",
    "secondoControllore"
})
@XmlSeeAlso({
    ChecklistsicurezzaalimentareWsTO.class
})
public class ChecklistsicurezzaalimentareTO {

    protected Integer caId;
    protected Integer clsaId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadPrescrizioni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerificaSa;
    protected String flagCopiaCheckList;
    protected String flagPrescrizioneEsitoSa;
    protected String prescrizioni;
    protected String requisitiXml;
    protected String secondoControllore;

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
     * Recupera il valore della proprieta clsaId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClsaId() {
        return clsaId;
    }

    /**
     * Imposta il valore della proprieta clsaId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClsaId(Integer value) {
        this.clsaId = value;
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
     * Recupera il valore della proprieta dataVerificaSa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerificaSa() {
        return dataVerificaSa;
    }

    /**
     * Imposta il valore della proprieta dataVerificaSa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerificaSa(XMLGregorianCalendar value) {
        this.dataVerificaSa = value;
    }

    /**
     * Recupera il valore della proprieta flagCopiaCheckList.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCopiaCheckList() {
        return flagCopiaCheckList;
    }

    /**
     * Imposta il valore della proprieta flagCopiaCheckList.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCopiaCheckList(String value) {
        this.flagCopiaCheckList = value;
    }

    /**
     * Recupera il valore della proprieta flagPrescrizioneEsitoSa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPrescrizioneEsitoSa() {
        return flagPrescrizioneEsitoSa;
    }

    /**
     * Imposta il valore della proprieta flagPrescrizioneEsitoSa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPrescrizioneEsitoSa(String value) {
        this.flagPrescrizioneEsitoSa = value;
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
