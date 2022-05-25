/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per unipro complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="unipro">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="uniproId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipattDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capacita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capacitaInc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="superficie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCapannoni" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numTelMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTelFisso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagAlternanzaSpecie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagDestPulcini" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtFineAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "unipro", propOrder = {
    "uniproId",
    "aziendaCodice",
    "speallCodice",
    "speallDescrizione",
    "propIdFiscale",
    "propCognNome",
    "tipattCodice",
    "tipattDescrizione",
    "denominazione",
    "capacita",
    "capacitaInc",
    "superficie",
    "numCapannoni",
    "numTelMobile",
    "numTelFisso",
    "email",
    "flagAlternanzaSpecie",
    "flagDestPulcini",
    "dtInizioAttivita",
    "dtFineAttivita",
    "note"
})
public class Unipro
    extends Entity
{

    protected Integer uniproId;
    protected String aziendaCodice;
    protected String speallCodice;
    protected String speallDescrizione;
    protected String propIdFiscale;
    protected String propCognNome;
    protected String tipattCodice;
    protected String tipattDescrizione;
    protected String denominazione;
    protected Integer capacita;
    protected Integer capacitaInc;
    protected Integer superficie;
    protected Integer numCapannoni;
    protected String numTelMobile;
    protected String numTelFisso;
    protected String email;
    protected String flagAlternanzaSpecie;
    protected String flagDestPulcini;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioAttivita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtFineAttivita;
    protected String note;

    /**
     * Recupera il valore della proprieta uniproId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUniproId() {
        return uniproId;
    }

    /**
     * Imposta il valore della proprieta uniproId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUniproId(Integer value) {
        this.uniproId = value;
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
     * Recupera il valore della proprieta speallCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeallCodice() {
        return speallCodice;
    }

    /**
     * Imposta il valore della proprieta speallCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeallCodice(String value) {
        this.speallCodice = value;
    }

    /**
     * Recupera il valore della proprieta speallDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeallDescrizione() {
        return speallDescrizione;
    }

    /**
     * Imposta il valore della proprieta speallDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeallDescrizione(String value) {
        this.speallDescrizione = value;
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
     * Recupera il valore della proprieta tipattCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipattCodice() {
        return tipattCodice;
    }

    /**
     * Imposta il valore della proprieta tipattCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipattCodice(String value) {
        this.tipattCodice = value;
    }

    /**
     * Recupera il valore della proprieta tipattDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipattDescrizione() {
        return tipattDescrizione;
    }

    /**
     * Imposta il valore della proprieta tipattDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipattDescrizione(String value) {
        this.tipattDescrizione = value;
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
     * Recupera il valore della proprieta capacita.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacita() {
        return capacita;
    }

    /**
     * Imposta il valore della proprieta capacita.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacita(Integer value) {
        this.capacita = value;
    }

    /**
     * Recupera il valore della proprieta capacitaInc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacitaInc() {
        return capacitaInc;
    }

    /**
     * Imposta il valore della proprieta capacitaInc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacitaInc(Integer value) {
        this.capacitaInc = value;
    }

    /**
     * Recupera il valore della proprieta superficie.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSuperficie() {
        return superficie;
    }

    /**
     * Imposta il valore della proprieta superficie.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSuperficie(Integer value) {
        this.superficie = value;
    }

    /**
     * Recupera il valore della proprieta numCapannoni.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCapannoni() {
        return numCapannoni;
    }

    /**
     * Imposta il valore della proprieta numCapannoni.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapannoni(Integer value) {
        this.numCapannoni = value;
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
     * Recupera il valore della proprieta flagAlternanzaSpecie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagAlternanzaSpecie() {
        return flagAlternanzaSpecie;
    }

    /**
     * Imposta il valore della proprieta flagAlternanzaSpecie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagAlternanzaSpecie(String value) {
        this.flagAlternanzaSpecie = value;
    }

    /**
     * Recupera il valore della proprieta flagDestPulcini.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagDestPulcini() {
        return flagDestPulcini;
    }

    /**
     * Imposta il valore della proprieta flagDestPulcini.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagDestPulcini(String value) {
        this.flagDestPulcini = value;
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
     * Recupera il valore della proprieta dtFineAttivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtFineAttivita() {
        return dtFineAttivita;
    }

    /**
     * Imposta il valore della proprieta dtFineAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtFineAttivita(XMLGregorianCalendar value) {
        this.dtFineAttivita = value;
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
