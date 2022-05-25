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
 * <p>Classe Java per requisitiTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="requisitiTO">
 *   &lt;complexContent> 
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cldbId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="irrDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="irrId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="irrOrdine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numIrregolarita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numProvvCatA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numProvvCatB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numProvvCatC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAllegato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/> 
 *         &lt;element name="dettirrId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="flagEsitoDett" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="osservazioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requisitiTO", propOrder = {
    "cldbId",
    "irrDescrizione",
    "irrId",
    "irrOrdine",
    "numIrregolarita",
    "numProvvCatA",
    "numProvvCatB",
    "numProvvCatC",
    "tipoAllegato",
    "dettirrId",
    "flagEsitoDett",
    "osservazioni"
    })
@XmlSeeAlso({
    RequisitiWsTO.class
})
public class RequisitiTO {

    protected Integer cldbId;
    protected String irrDescrizione;
    protected Integer irrId;
    protected Integer irrOrdine;
    protected String numIrregolarita;
    protected String numProvvCatA;
    protected String numProvvCatB;
    protected String numProvvCatC;
    protected Integer tipoAllegato;
    
    protected int dettirrId;
    protected String flagEsitoDett;
    protected String osservazioni;
    
    /**
     * Recupera il valore della proprieta cldbId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCldbId() {
        return cldbId;
    }

    /**
     * Imposta il valore della proprieta cldbId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCldbId(Integer value) {
        this.cldbId = value;
    }

    /**
     * Recupera il valore della proprieta irrDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIrrDescrizione() {
        return irrDescrizione;
    }

    /**
     * Imposta il valore della proprieta irrDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIrrDescrizione(String value) {
        this.irrDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta irrId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIrrId() {
        return irrId;
    }

    /**
     * Imposta il valore della proprieta irrId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIrrId(Integer value) {
        this.irrId = value;
    }

    /**
     * Recupera il valore della proprieta irrOrdine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIrrOrdine() {
        return irrOrdine;
    }

    /**
     * Imposta il valore della proprieta irrOrdine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIrrOrdine(Integer value) {
        this.irrOrdine = value;
    }

    /**
     * Recupera il valore della proprieta numIrregolarita.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumIrregolarita() {
        return numIrregolarita;
    }

    /**
     * Imposta il valore della proprieta numIrregolarita.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumIrregolarita(String value) {
        this.numIrregolarita = value;
    }

    /**
     * Recupera il valore della proprieta numProvvCatA.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumProvvCatA() {
        return numProvvCatA;
    }

    /**
     * Imposta il valore della proprieta numProvvCatA.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumProvvCatA(String value) {
        this.numProvvCatA = value;
    }
  
    /**
     * Recupera il valore della proprieta numProvvCatB.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumProvvCatB() {
        return numProvvCatB;
    }

    /**
     * Imposta il valore della proprieta numProvvCatB.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumProvvCatB(String value) {
        this.numProvvCatB = value;
    }
   
    /**
     * Recupera il valore della proprieta numProvvCatC.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumProvvCatC() {
        return numProvvCatC;
    }

    /**
     * Imposta il valore della proprieta numProvvCatC.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumProvvCatC(String value) {
        this.numProvvCatC = value;
    }
   
    /**
     * Recupera il valore della proprieta tipoAllegato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * Imposta il valore della proprieta tipoAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoAllegato(Integer value) {
        this.tipoAllegato = value;
    }

	public int getDettirrId() {
		return dettirrId;
	}

	public void setDettirrId(int dettirrId) {
		this.dettirrId = dettirrId;
	}

	public String getFlagEsitoDett() {
		return flagEsitoDett;
	}

	public void setFlagEsitoDett(String flagEsitoDett) {
		this.flagEsitoDett = flagEsitoDett;
	}

	public String getOsservazioni() {
		return osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}

}
