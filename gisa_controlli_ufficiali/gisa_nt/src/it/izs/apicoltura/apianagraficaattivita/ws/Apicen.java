/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaattivita.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

 

/**
 * <p>Classe Java per apicen complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apicen">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apianagrafica.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apicenId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiApiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiApiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiApiattDtCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtCensimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numAlveari" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSciami" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apicen", propOrder = {
    "apicenId",
    "apiProgressivo",
    "apiApiattDenominazione",
    "apiApiattAziendaCodice",
    "apiApiattDtCessazione",
    "dtCensimento",
    "numAlveari",
    "numSciami",
    "flagCensUfficiale"
})
public class Apicen
    extends Entity
{

    protected Integer apicenId;
    protected Integer apiProgressivo;
    protected String apiApiattDenominazione;
    protected String apiApiattAziendaCodice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apiApiattDtCessazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtCensimento;
    protected Integer numAlveari;
    protected Integer numSciami;
    protected String flagCensUfficiale;

    /**
     * Recupera il valore della proprieta apicenId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApicenId() {
        return apicenId;
    }

    /**
     * Imposta il valore della proprieta apicenId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApicenId(Integer value) {
        this.apicenId = value;
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
     * Recupera il valore della proprieta apiApiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiApiattDenominazione() {
        return apiApiattDenominazione;
    }

    /**
     * Imposta il valore della proprieta apiApiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiApiattDenominazione(String value) {
        this.apiApiattDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta apiApiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiApiattAziendaCodice() {
        return apiApiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta apiApiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiApiattAziendaCodice(String value) {
        this.apiApiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta apiApiattDtCessazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApiApiattDtCessazione() {
        return apiApiattDtCessazione;
    }

    /**
     * Imposta il valore della proprieta apiApiattDtCessazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApiApiattDtCessazione(XMLGregorianCalendar value) {
        this.apiApiattDtCessazione = value;
    }

    /**
     * Recupera il valore della proprieta dtCensimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtCensimento() {
        return dtCensimento;
    }

    /**
     * Imposta il valore della proprieta dtCensimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtCensimento(XMLGregorianCalendar value) {
        this.dtCensimento = value;
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
    
    
    
    public String getFlagCensUfficiale() {
        return flagCensUfficiale;
    }

    public void setFlagCensUfficiale(String flagCensUfficiale) {
        this.flagCensUfficiale = flagCensUfficiale;
    }

}
