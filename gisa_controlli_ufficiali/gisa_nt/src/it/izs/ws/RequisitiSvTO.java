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
 * <p>Classe Java per requisitiSvTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="requisitiSvTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clsvId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clsvdettId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="quantita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="svirrCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="svirrDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="svirrId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="svirrOrdine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requisitiSvTO", propOrder = {
    "clsvId",
    "clsvdettId",
    "quantita",
    "svirrCodice",
    "svirrDescrizione",
    "svirrId",
    "svirrOrdine"
})
@XmlSeeAlso({
    RequisitiSvWsTO.class
})
public class RequisitiSvTO {

    protected Integer clsvId;
    protected Integer clsvdettId;
    protected Integer quantita;
    protected String svirrCodice;
    protected String svirrDescrizione;
    protected Integer svirrId;
    protected Integer svirrOrdine;

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
     * Recupera il valore della proprieta clsvdettId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClsvdettId() {
        return clsvdettId;
    }

    /**
     * Imposta il valore della proprieta clsvdettId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClsvdettId(Integer value) {
        this.clsvdettId = value;
    }

    /**
     * Recupera il valore della proprieta quantita.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQuantita() {
        return quantita;
    }

    /**
     * Imposta il valore della proprieta quantita.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQuantita(Integer value) {
        this.quantita = value;
    }

    /**
     * Recupera il valore della proprieta svirrCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvirrCodice() {
        return svirrCodice;
    }

    /**
     * Imposta il valore della proprieta svirrCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvirrCodice(String value) {
        this.svirrCodice = value;
    }

    /**
     * Recupera il valore della proprieta svirrDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvirrDescrizione() {
        return svirrDescrizione;
    }

    /**
     * Imposta il valore della proprieta svirrDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvirrDescrizione(String value) {
        this.svirrDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta svirrId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSvirrId() {
        return svirrId;
    }

    /**
     * Imposta il valore della proprieta svirrId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSvirrId(Integer value) {
        this.svirrId = value;
    }

    /**
     * Recupera il valore della proprieta svirrOrdine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSvirrOrdine() {
        return svirrOrdine;
    }

    /**
     * Imposta il valore della proprieta svirrOrdine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSvirrOrdine(Integer value) {
        this.svirrOrdine = value;
    }

}
