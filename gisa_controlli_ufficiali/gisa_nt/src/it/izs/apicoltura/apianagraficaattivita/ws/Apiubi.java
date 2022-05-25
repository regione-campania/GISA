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
 * <p>Classe Java per apiubi complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apiubi">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apianagrafica.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apiubiId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiApiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtApiApiattCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apiApiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="comDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apiubi", propOrder = {
    "apiubiId",
    "apiProgressivo",
    "apiApiattDenominazione",
    "dtApiApiattCessazione",
    "apiApiattAziendaCodice",
    "indirizzo",
    "cap",
    "localita",
    "dtInizioValidita",
    "dtFineValidita",
    "comDescrizione",
    "comIstat",
    "comProSigla",
    "latitudine",
    "longitudine"
})
public class Apiubi
    extends Entity
{

    protected Integer apiubiId;
    protected Integer apiProgressivo;
    protected String apiApiattDenominazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtApiApiattCessazione;
    protected String apiApiattAziendaCodice;
    protected String indirizzo;
    protected String cap;
    protected String localita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioValidita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtFineValidita;
    protected String comDescrizione;
    protected String comIstat;
    protected String comProSigla;
    protected Double latitudine;
    protected Double longitudine;

    /**
     * Recupera il valore della proprieta apiubiId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiubiId() {
        return apiubiId;
    }

    /**
     * Imposta il valore della proprieta apiubiId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiubiId(Integer value) {
        this.apiubiId = value;
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
     * Recupera il valore della proprieta dtApiApiattCessazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtApiApiattCessazione() {
        return dtApiApiattCessazione;
    }

    /**
     * Imposta il valore della proprieta dtApiApiattCessazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtApiApiattCessazione(XMLGregorianCalendar value) {
        this.dtApiApiattCessazione = value;
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
     * Recupera il valore della proprieta indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprieta indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprieta cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il valore della proprieta cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprieta localita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della proprieta localita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprieta dtInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtInizioValidita() {
        return dtInizioValidita;
    }

    /**
     * Imposta il valore della proprieta dtInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtInizioValidita(XMLGregorianCalendar value) {
        this.dtInizioValidita = value;
    }

    /**
     * Recupera il valore della proprieta dtFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtFineValidita() {
        return dtFineValidita;
    }

    /**
     * Imposta il valore della proprieta dtFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtFineValidita(XMLGregorianCalendar value) {
        this.dtFineValidita = value;
    }

    /**
     * Recupera il valore della proprieta comDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComDescrizione() {
        return comDescrizione;
    }

    /**
     * Imposta il valore della proprieta comDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComDescrizione(String value) {
        this.comDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta comIstat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComIstat() {
        return comIstat;
    }

    /**
     * Imposta il valore della proprieta comIstat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComIstat(String value) {
        this.comIstat = value;
    }

    /**
     * Recupera il valore della proprieta comProSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComProSigla() {
        return comProSigla;
    }

    /**
     * Imposta il valore della proprieta comProSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComProSigla(String value) {
        this.comProSigla = value;
    }

    /**
     * Recupera il valore della proprieta latitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLatitudine() {
        return latitudine;
    }

    /**
     * Imposta il valore della proprieta latitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLatitudine(Double value) {
        this.latitudine = value;
    }

    /**
     * Recupera il valore della proprieta longitudine.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLongitudine() {
        return longitudine;
    }

    /**
     * Imposta il valore della proprieta longitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLongitudine(Double value) {
        this.longitudine = value;
    }

}
