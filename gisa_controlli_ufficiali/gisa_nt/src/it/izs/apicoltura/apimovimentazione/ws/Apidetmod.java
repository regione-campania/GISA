/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apimovimentazione.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per apidetmod complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apidetmod">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apimovimentazione.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apidetmodId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apimodmovId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apimodmovApiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apimodmovProvApiattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apimodmovDtModello" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apimodmovDtUscita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apimodmovDestApiattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="destApiComIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiComDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numSciami" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numAlveari" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numPacchiDapi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numApiRegine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="listaAlveari" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiLocalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiLatitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="destApiLongitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="apimodmovNumModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dettaglioModello", propOrder = {
		"destApiId",
    "apidetmodId",
    "apimodmovId",
    "apimodmovApiProgressivo",
    "apimodmovProvApiattCodice",
    "apimodmovDtModello",
    "apimodmovDtUscita",
    "apimodmovDestApiattCodice",
    "destApiProgressivo",
    "destApiComIstat",
    "destApiProSigla",
    "destApiComDescrizione",
    "numSciami",
    "numAlveari",
    "numPacchiDapi",
    "numApiRegine",
    "listaAlveari",
    "destApiIndirizzo",
    "destApiLocalita",
    "destApiLatitudine",
    "destApiLongitudine",
    "apimodmovNumModello",
    "apimodmovApiApiattCodice",
    "destApiComProSigla"
})
public class Apidetmod
    extends Entity
{

	 protected Integer destApiId;
	 
    protected Integer apidetmodId;
    protected Integer apimodmovId;
    protected Integer apimodmovApiProgressivo;
    protected String apimodmovProvApiattCodice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apimodmovDtModello;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apimodmovDtUscita;
    protected String apimodmovDestApiattCodice;
    protected Integer destApiProgressivo;
    protected String destApiComIstat;
    protected String destApiProSigla;
    protected String destApiComDescrizione;
    protected Integer numSciami;
    protected Integer numAlveari;
    protected Integer numPacchiDapi;
    protected Integer numApiRegine;
    protected String listaAlveari;
    protected String destApiIndirizzo;
    protected String destApiLocalita;
    protected Double destApiLatitudine;
    protected Double destApiLongitudine;
    protected String apimodmovNumModello;
    protected String apimodmovApiApiattCodice;
    protected String destApiComProSigla;
    
    
    
    
    
    
    public String getDestApiComProSigla() {
		return destApiComProSigla;
	}

	public void setDestApiComProSigla(String destApiComProSigla) {
		this.destApiComProSigla = destApiComProSigla;
	}

	public Integer getDestApiId() {
		return destApiId;
	}

	public void setDestApiId(Integer destApiId) {
		this.destApiId = destApiId;
	}

	/**
     * Recupera il valore della proprieta apidetmodId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApidetmodId() {
        return apidetmodId;
    }

    /**
     * Imposta il valore della proprieta apidetmodId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApidetmodId(Integer value) {
        this.apidetmodId = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApimodmovId() {
        return apimodmovId;
    }

    /**
     * Imposta il valore della proprieta apimodmovId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApimodmovId(Integer value) {
        this.apimodmovId = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovApiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApimodmovApiProgressivo() {
        return apimodmovApiProgressivo;
    }

    /**
     * Imposta il valore della proprieta apimodmovApiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApimodmovApiProgressivo(Integer value) {
        this.apimodmovApiProgressivo = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovProvApiattCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimodmovProvApiattCodice() {
        return apimodmovProvApiattCodice;
    }

    /**
     * Imposta il valore della proprieta apimodmovProvApiattCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimodmovProvApiattCodice(String value) {
        this.apimodmovProvApiattCodice = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovDtModello.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApimodmovDtModello() {
        return apimodmovDtModello;
    }

    /**
     * Imposta il valore della proprieta apimodmovDtModello.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApimodmovDtModello(XMLGregorianCalendar value) {
        this.apimodmovDtModello = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovDtUscita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApimodmovDtUscita() {
        return apimodmovDtUscita;
    }

    /**
     * Imposta il valore della proprieta apimodmovDtUscita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApimodmovDtUscita(XMLGregorianCalendar value) {
        this.apimodmovDtUscita = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovDestApiattCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimodmovDestApiattCodice() {
        return apimodmovDestApiattCodice;
    }

    /**
     * Imposta il valore della proprieta apimodmovDestApiattCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimodmovDestApiattCodice(String value) {
        this.apimodmovDestApiattCodice = value;
    }

    /**
     * Recupera il valore della proprieta destApiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDestApiProgressivo() {
        return destApiProgressivo;
    }

    /**
     * Imposta il valore della proprieta destApiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDestApiProgressivo(Integer value) {
        this.destApiProgressivo = value;
    }

    /**
     * Recupera il valore della proprieta destApiComIstat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiComIstat() {
        return destApiComIstat;
    }

    /**
     * Imposta il valore della proprieta destApiComIstat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiComIstat(String value) {
        this.destApiComIstat = value;
    }

    /**
     * Recupera il valore della proprieta destApiProSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiProSigla() {
        return destApiProSigla;
    }

    /**
     * Imposta il valore della proprieta destApiProSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiProSigla(String value) {
        this.destApiProSigla = value;
    }

    /**
     * Recupera il valore della proprieta destApiComDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiComDescrizione() {
        return destApiComDescrizione;
    }

    /**
     * Imposta il valore della proprieta destApiComDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiComDescrizione(String value) {
        this.destApiComDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta numSciami.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSciami() {
        return numSciami;
    }

    /**
     * Imposta il valore della proprieta numSciami.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSciami(Integer value) {
        this.numSciami = value;
    }

    /**
     * Recupera il valore della proprieta numAlveari.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAlveari() {
        return numAlveari;
    }

    /**
     * Imposta il valore della proprieta numAlveari.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAlveari(Integer value) {
        this.numAlveari = value;
    }

    /**
     * Recupera il valore della proprieta numPacchiDapi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumPacchiDapi() {
        return numPacchiDapi;
    }

    /**
     * Imposta il valore della proprieta numPacchiDapi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumPacchiDapi(Integer value) {
        this.numPacchiDapi = value;
    }

    /**
     * Recupera il valore della proprieta numApiRegine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumApiRegine() {
        return numApiRegine;
    }

    /**
     * Imposta il valore della proprieta numApiRegine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumApiRegine(Integer value) {
        this.numApiRegine = value;
    }

    /**
     * Recupera il valore della proprieta listaAlveari.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListaAlveari() {
        return listaAlveari;
    }

    /**
     * Imposta il valore della proprieta listaAlveari.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListaAlveari(String value) {
        this.listaAlveari = value;
    }

    /**
     * Recupera il valore della proprieta destApiIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiIndirizzo() {
        return destApiIndirizzo;
    }

    /**
     * Imposta il valore della proprieta destApiIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiIndirizzo(String value) {
        this.destApiIndirizzo = value;
    }

    /**
     * Recupera il valore della proprieta destApiLocalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiLocalita() {
        return destApiLocalita;
    }

    /**
     * Imposta il valore della proprieta destApiLocalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiLocalita(String value) {
        this.destApiLocalita = value;
    }

    /**
     * Recupera il valore della proprieta destApiLatitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDestApiLatitudine() {
        return destApiLatitudine;
    }

    /**
     * Imposta il valore della proprieta destApiLatitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDestApiLatitudine(Double value) {
        this.destApiLatitudine = value;
    }

    /**
     * Recupera il valore della proprieta destApiLongitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDestApiLongitudine() {
        return destApiLongitudine;
    }

    /**
     * Imposta il valore della proprieta destApiLongitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDestApiLongitudine(Double value) {
        this.destApiLongitudine = value;
    }

    /**
     * Recupera il valore della proprieta apimodmovNumModello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimodmovNumModello() {
        return apimodmovNumModello;
    }

    /**
     * Imposta il valore della proprieta apimodmovNumModello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimodmovNumModello(String value) {
        this.apimodmovNumModello = value;
    }

	public void setApimodmovApiApiattCodice(String codiceAzienda) 
	{	
		apimodmovApiApiattCodice=codiceAzienda;
	}
	
	public String getApimodmovApiApiattCodice() 
	{
        return apimodmovApiApiattCodice;
    }

}
