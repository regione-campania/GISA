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
 * <p>Classe Java per apiing complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apiing">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apimovimentazione.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apiingId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apidetmodId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apimodmovDtModello" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apimodmovNumModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provApiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provApiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provApiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dtIngresso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numAlveari" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numPacchiDapi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSciami" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numApiRegine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="listaAlveari" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apimotingCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apimotingDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtModello" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apiing", propOrder = {
    "apiingId",
    "apidetmodId",
    "apiattDenominazione",
    "apiattAziendaCodice",
    "apiProgressivo",
    "apimodmovDtModello",
    "apimodmovNumModello",
    "provApiattDenominazione",
    "provApiattAziendaCodice",
    "provApiProgressivo",
    "dtIngresso",
    "numAlveari",
    "numPacchiDapi",
    "numSciami",
    "numApiRegine",
    "listaAlveari",
    "apimotingCodice",
    "apimotingDescrizione",
    "numModello",
    "dtModello"
})
public class Apiing
    extends Entity
{

    protected Integer apiingId;
    protected Integer apidetmodId;
    protected String apiattDenominazione;
    protected String apiattAziendaCodice;
    protected Integer apiProgressivo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apimodmovDtModello;
    protected String apimodmovNumModello;
    protected String provApiattDenominazione;
    protected String provApiattAziendaCodice;
    protected Integer provApiProgressivo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtIngresso;
    protected Integer numAlveari;
    protected Integer numPacchiDapi;
    protected Integer numSciami;
    protected Integer numApiRegine;
    protected String listaAlveari;
    protected String apimotingCodice;
    protected String apimotingDescrizione;
    protected String numModello;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtModello;

    /**
     * Recupera il valore della proprieta apiingId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiingId() {
        return apiingId;
    }

    /**
     * Imposta il valore della proprieta apiingId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiingId(Integer value) {
        this.apiingId = value;
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
     * Recupera il valore della proprieta apiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiattDenominazione() {
        return apiattDenominazione;
    }

    /**
     * Imposta il valore della proprieta apiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiattDenominazione(String value) {
        this.apiattDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta apiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiattAziendaCodice() {
        return apiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta apiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiattAziendaCodice(String value) {
        this.apiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta apiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiProgressivo() {
        return apiProgressivo;
    }

    /**
     * Imposta il valore della proprieta apiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiProgressivo(Integer value) {
        this.apiProgressivo = value;
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

    /**
     * Recupera il valore della proprieta provApiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvApiattDenominazione() {
        return provApiattDenominazione;
    }

    /**
     * Imposta il valore della proprieta provApiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvApiattDenominazione(String value) {
        this.provApiattDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta provApiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvApiattAziendaCodice() {
        return provApiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta provApiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvApiattAziendaCodice(String value) {
        this.provApiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta provApiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProvApiProgressivo() {
        return provApiProgressivo;
    }

    /**
     * Imposta il valore della proprieta provApiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProvApiProgressivo(Integer value) {
        this.provApiProgressivo = value;
    }

    /**
     * Recupera il valore della proprieta dtIngresso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtIngresso() {
        return dtIngresso;
    }

    /**
     * Imposta il valore della proprieta dtIngresso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtIngresso(XMLGregorianCalendar value) {
        this.dtIngresso = value;
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
     * Recupera il valore della proprieta apimotingCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimotingCodice() {
        return apimotingCodice;
    }

    /**
     * Imposta il valore della proprieta apimotingCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimotingCodice(String value) {
        this.apimotingCodice = value;
    }

    /**
     * Recupera il valore della proprieta apimotingDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimotingDescrizione() {
        return apimotingDescrizione;
    }

    /**
     * Imposta il valore della proprieta apimotingDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimotingDescrizione(String value) {
        this.apimotingDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta numModello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumModello() {
        return numModello;
    }

    /**
     * Imposta il valore della proprieta numModello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumModello(String value) {
        this.numModello = value;
    }

    /**
     * Recupera il valore della proprieta dtModello.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtModello() {
        return dtModello;
    }

    /**
     * Imposta il valore della proprieta dtModello.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtModello(XMLGregorianCalendar value) {
        this.dtModello = value;
    }

}
