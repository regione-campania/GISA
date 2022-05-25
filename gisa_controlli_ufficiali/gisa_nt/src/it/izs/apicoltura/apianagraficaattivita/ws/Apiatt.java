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
 * <p>Classe Java per apiatt complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apiatt">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apianagrafica.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apiattId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regSlDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comSlIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comSlDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comSlProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comSlCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localitaSl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoSl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apitipattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apitipattDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTelFisso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTelMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apiatt", propOrder = {
    "apiattId",
    "denominazione",
    "aziendaCodice",
    "aslCodice",
    "aslDenominazione",
    "regSlDescrizione",
    "propIdFiscale",
    "propCognNome",
    "comSlIstat",
    "comSlDescrizione",
    "comSlProSigla",
    "comSlCap",
    "localitaSl",
    "indirizzoSl",
    "apitipattCodice",
    "apitipattDescrizione",
    "numTelFisso",
    "numTelMobile",
    "fax",
    "email",
    "dtInizioAttivita",
    "dtCessazione",
    "note"
})
public class Apiatt
    extends Entity
{

    protected Integer apiattId;
    protected String denominazione;
    protected String aziendaCodice;
    protected String aslCodice;
    protected String aslDenominazione;
    protected String regSlDescrizione;
    protected String propIdFiscale;
    protected String propCognNome;
    protected String comSlIstat;
    protected String comSlDescrizione;
    protected String comSlProSigla;
    protected String comSlCap;
    protected String localitaSl;
    protected String indirizzoSl;
    protected String apitipattCodice;
    protected String apitipattDescrizione;
    protected String numTelFisso;
    protected String numTelMobile;
    protected String fax;
    protected String email;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioAttivita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtCessazione;
    protected String note;

    /**
     * Recupera il valore della proprieta apiattId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiattId() {
        return apiattId;
    }

    /**
     * Imposta il valore della proprieta apiattId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiattId(Integer value) {
        this.apiattId = value;
    }

    /**
     * Recupera il valore della proprieta denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprieta denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprieta aziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaCodice() {
        return aziendaCodice;
    }

    /**
     * Imposta il valore della proprieta aziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaCodice(String value) {
        this.aziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta aslCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAslCodice() {
        return aslCodice;
    }

    /**
     * Imposta il valore della proprieta aslCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAslCodice(String value) {
        this.aslCodice = value;
    }

    /**
     * Recupera il valore della proprieta aslDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAslDenominazione() {
        return aslDenominazione;
    }

    /**
     * Imposta il valore della proprieta aslDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAslDenominazione(String value) {
        this.aslDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta regSlDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegSlDescrizione() {
        return regSlDescrizione;
    }

    /**
     * Imposta il valore della proprieta regSlDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegSlDescrizione(String value) {
        this.regSlDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta propIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropIdFiscale() {
        return propIdFiscale;
    }

    /**
     * Imposta il valore della proprieta propIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropIdFiscale(String value) {
        this.propIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta propCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropCognNome() {
        return propCognNome;
    }

    /**
     * Imposta il valore della proprieta propCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropCognNome(String value) {
        this.propCognNome = value;
    }

    /**
     * Recupera il valore della proprieta comSlIstat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComSlIstat() {
        return comSlIstat;
    }

    /**
     * Imposta il valore della proprieta comSlIstat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComSlIstat(String value) {
        this.comSlIstat = value;
    }

    /**
     * Recupera il valore della proprieta comSlDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComSlDescrizione() {
        return comSlDescrizione;
    }

    /**
     * Imposta il valore della proprieta comSlDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComSlDescrizione(String value) {
        this.comSlDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta comSlProSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComSlProSigla() {
        return comSlProSigla;
    }

    /**
     * Imposta il valore della proprieta comSlProSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComSlProSigla(String value) {
        this.comSlProSigla = value;
    }

    /**
     * Recupera il valore della proprieta comSlCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComSlCap() {
        return comSlCap;
    }

    /**
     * Imposta il valore della proprieta comSlCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComSlCap(String value) {
        this.comSlCap = value;
    }

    /**
     * Recupera il valore della proprieta localitaSl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaSl() {
        return localitaSl;
    }

    /**
     * Imposta il valore della proprieta localitaSl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaSl(String value) {
        this.localitaSl = value;
    }

    /**
     * Recupera il valore della proprieta indirizzoSl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoSl() {
        return indirizzoSl;
    }

    /**
     * Imposta il valore della proprieta indirizzoSl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoSl(String value) {
        this.indirizzoSl = value;
    }

    /**
     * Recupera il valore della proprieta apitipattCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApitipattCodice() {
        return apitipattCodice;
    }

    /**
     * Imposta il valore della proprieta apitipattCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApitipattCodice(String value) {
        this.apitipattCodice = value;
    }

    /**
     * Recupera il valore della proprieta apitipattDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApitipattDescrizione() {
        return apitipattDescrizione;
    }

    /**
     * Imposta il valore della proprieta apitipattDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApitipattDescrizione(String value) {
        this.apitipattDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta numTelFisso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelFisso() {
        return numTelFisso;
    }

    /**
     * Imposta il valore della proprieta numTelFisso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelFisso(String value) {
        this.numTelFisso = value;
    }

    /**
     * Recupera il valore della proprieta numTelMobile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelMobile() {
        return numTelMobile;
    }

    /**
     * Imposta il valore della proprieta numTelMobile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelMobile(String value) {
        this.numTelMobile = value;
    }

    /**
     * Recupera il valore della proprieta fax.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Imposta il valore della proprieta fax.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Recupera il valore della proprieta email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della proprieta email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprieta dtInizioAttivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtInizioAttivita() {
        return dtInizioAttivita;
    }

    /**
     * Imposta il valore della proprieta dtInizioAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtInizioAttivita(XMLGregorianCalendar value) {
        this.dtInizioAttivita = value;

    }

    /**
     * Recupera il valore della proprieta dtCessazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtCessazione() {
        return dtCessazione;
    }

    /**
     * Imposta il valore della proprieta dtCessazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtCessazione(XMLGregorianCalendar value) {
        this.dtCessazione = value;
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

}
