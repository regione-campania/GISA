/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per requisitiSsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="requisitiSsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clssId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clssdettId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ordine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ssirrId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requisitiSsTO", propOrder = {
    "clssId",
    "clssdettId",
    "descrizione",
    "flagSN",
    "note",
    "ordine",
    "ssirrId",
    "titolo"
})
@XmlSeeAlso({
    RequisitiSsWsTO.class
})
public class RequisitiSsTO {

    protected Integer clssId;
    protected Integer clssdettId;
    protected String descrizione;
    protected String flagSN;
    protected String note;
    protected Integer ordine;
    protected Integer ssirrId;
    protected String titolo;

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
     * Recupera il valore della proprieta clssdettId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClssdettId() {
        return clssdettId;
    }

    /**
     * Imposta il valore della proprieta clssdettId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClssdettId(Integer value) {
        this.clssdettId = value;
    }

    /**
     * Recupera il valore della proprieta descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprieta descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprieta flagSN.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagSN() {
        return flagSN;
    }

    /**
     * Imposta il valore della proprieta flagSN.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagSN(String value) {
        this.flagSN = value;
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
     * Recupera il valore della proprieta ordine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrdine() {
        return ordine;
    }

    /**
     * Imposta il valore della proprieta ordine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrdine(Integer value) {
        this.ordine = value;
    }

    /**
     * Recupera il valore della proprieta ssirrId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSsirrId() {
        return ssirrId;
    }

    /**
     * Imposta il valore della proprieta ssirrId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSsirrId(Integer value) {
        this.ssirrId = value;
    }

    /**
     * Recupera il valore della proprieta titolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il valore della proprieta titolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

}
