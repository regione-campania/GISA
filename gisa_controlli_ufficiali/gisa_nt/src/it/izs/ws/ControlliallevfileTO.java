/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per controlliallevfileTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="controlliallevfileTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allevId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="blobFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cafId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dtControllo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagVitelli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoControlloGen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controlliallevfileTO", propOrder = {
    "allevId",
    "blobFile",
    "caId",
    "cafId",
    "dtControllo",
    "flagVitelli",
    "nomeFile",
    "tipoControlloGen"
})
public class ControlliallevfileTO {

    protected Integer allevId;
    protected byte[] blobFile;
    protected Integer caId;
    protected Integer cafId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtControllo;
    protected String flagVitelli;
    protected String nomeFile;
    protected String tipoControlloGen;

    /**
     * Recupera il valore della proprieta allevId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAllevId() {
        return allevId;
    }

    /**
     * Imposta il valore della proprieta allevId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAllevId(Integer value) {
        this.allevId = value;
    }

    /**
     * Recupera il valore della proprieta blobFile.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBlobFile() {
        return blobFile;
    }

    /**
     * Imposta il valore della proprieta blobFile.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBlobFile(byte[] value) {
        this.blobFile = value;
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
     * Recupera il valore della proprieta cafId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCafId() {
        return cafId;
    }

    /**
     * Imposta il valore della proprieta cafId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCafId(Integer value) {
        this.cafId = value;
    }

    /**
     * Recupera il valore della proprieta dtControllo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtControllo() {
        return dtControllo;
    }

    /**
     * Imposta il valore della proprieta dtControllo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtControllo(XMLGregorianCalendar value) {
        this.dtControllo = value;
    }

    /**
     * Recupera il valore della proprieta flagVitelli.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagVitelli() {
        return flagVitelli;
    }

    /**
     * Imposta il valore della proprieta flagVitelli.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagVitelli(String value) {
        this.flagVitelli = value;
    }

    /**
     * Recupera il valore della proprieta nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprieta nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprieta tipoControlloGen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoControlloGen() {
        return tipoControlloGen;
    }

    /**
     * Imposta il valore della proprieta tipoControlloGen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoControlloGen(String value) {
        this.tipoControlloGen = value;
    }

}
