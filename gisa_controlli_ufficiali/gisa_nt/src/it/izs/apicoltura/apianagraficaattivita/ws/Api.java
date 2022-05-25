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
 * <p>Classe Java per api complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="api">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apianagrafica.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apiId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiattDtCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numAlveari" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSciami" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="comIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtApertura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtChiusura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apimodallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apimodallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classificazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apisotspeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiAslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apisotspeDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "api", propOrder = {
    "apiId",
    "apiattDtCessazione",
    "apiattAziendaCodice",
    "progressivo",
    "detenIdFiscale",
    "detenCognNome",
    "numAlveari",
    "numSciami",
    "comIstat",
    "comProSigla",
    "comDescrizione",
    "localita",
    "apiAslCodice",
    "indirizzo",
    "latitudine",
    "longitudine",
    "cap",
    "dtApertura",
    "dtChiusura",
    "apimodallCodice",
    "apimodallDescrizione",
    "classificazione",
    "apisotspeCodice",
    "apisotspeDescrizione"
})
public class Api
    extends Entity
{

    protected Integer apiId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apiattDtCessazione;
    protected String apiattAziendaCodice;
    protected Integer progressivo;
    protected String detenIdFiscale;
    protected String detenCognNome;
    protected Integer numAlveari;
    protected Integer numSciami;
    protected String comIstat;
    protected String comProSigla;
    protected String comDescrizione;
    protected String localita;
    protected String apiAslCodice;
    protected String indirizzo;
    protected Double latitudine;
    protected Double longitudine;
    protected String cap;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtApertura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtChiusura;
    protected String apimodallCodice;
    protected String apimodallDescrizione;
    protected String classificazione;
    protected String apisotspeCodice;
    protected String apisotspeDescrizione;

    /**
     * Recupera il valore della proprieta apiId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiId() {
        return apiId;
    }

    /**
     * Imposta il valore della proprieta apiId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiId(Integer value) {
        this.apiId = value;
    }

    /**
     * Recupera il valore della proprieta apiattDtCessazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApiattDtCessazione() {
        return apiattDtCessazione;
    }

    /**
     * Imposta il valore della proprieta apiattDtCessazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApiattDtCessazione(XMLGregorianCalendar value) {
        this.apiattDtCessazione = value;
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
     * Recupera il valore della proprieta progressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProgressivo() {
        return progressivo;
    }

    /**
     * Imposta il valore della proprieta progressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProgressivo(Integer value) {
        this.progressivo = value;
    }

    /**
     * Recupera il valore della proprieta detenIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetenIdFiscale() {
        return detenIdFiscale;
    }

    /**
     * Imposta il valore della proprieta detenIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetenIdFiscale(String value) {
        this.detenIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta detenCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetenCognNome() {
        return detenCognNome;
    }

    /**
     * Imposta il valore della proprieta detenCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetenCognNome(String value) {
        this.detenCognNome = value;
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
    
    
    
    
    public String getApiAslCodice() {
        return apiAslCodice;
    }

    public void setApiAslCodice(String value) {
        this.apiAslCodice = value;
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
     * Recupera il valore della proprieta dtApertura.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtApertura() {
        return dtApertura;
    }

    /**
     * Imposta il valore della proprieta dtApertura.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtApertura(XMLGregorianCalendar value) {
        this.dtApertura = value;
    }

    /**
     * Recupera il valore della proprieta dtChiusura.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtChiusura() {
        return dtChiusura;
    }

    /**
     * Imposta il valore della proprieta dtChiusura.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtChiusura(XMLGregorianCalendar value) {
        this.dtChiusura = value;
    }

    /**
     * Recupera il valore della proprieta apimodallCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimodallCodice() {
        return apimodallCodice;
    }

    /**
     * Imposta il valore della proprieta apimodallCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimodallCodice(String value) {
        this.apimodallCodice = value;
    }

    /**
     * Recupera il valore della proprieta apimodallDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimodallDescrizione() {
        return apimodallDescrizione;
    }

    /**
     * Imposta il valore della proprieta apimodallDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimodallDescrizione(String value) {
        this.apimodallDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta classificazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificazione() {
        return classificazione;
    }

    /**
     * Imposta il valore della proprieta classificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificazione(String value) {
        this.classificazione = value;
    }

    /**
     * Recupera il valore della proprieta apisotspeCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApisotspeCodice() {
        return apisotspeCodice;
    }

    /**
     * Imposta il valore della proprieta apisotspeCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApisotspeCodice(String value) {
        this.apisotspeCodice = value;
    }

    /**
     * Recupera il valore della proprieta apisotspeDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApisotspeDescrizione() {
        return apisotspeDescrizione;
    }

    /**
     * Imposta il valore della proprieta apisotspeDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApisotspeDescrizione(String value) {
        this.apisotspeDescrizione = value;
    }

}
