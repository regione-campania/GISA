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
 * <p>Classe Java per avidetattFromAllevfam complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="avidetattFromAllevfam">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="uniproId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="superficie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numTelMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTelFisso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagAlternanzaSpecie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagDestPulcini" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="uniproDtFineAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtValidazioneRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spebdnCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oriproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagLinea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioAttivitaDetatt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtFineAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numCicliAnno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numGruppiAnno" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numUovaInc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="flagTuttoVuoto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagFilieraRurale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagFaseProduttiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCapannoniDetatt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="densitaMaxAllev" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="avidettCapacita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="avidettCapacitaInc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="elencoCodSpecie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteDetatt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "avidetattFromAllevfam", propOrder = {
    "uniproId",
    "aziendaCodice",
    "speallCodice",
    "propIdFiscale",
    "tipattCodice",
    "denominazione",
    "superficie",
    "numTelMobile",
    "numTelFisso",
    "email",
    "flagAlternanzaSpecie",
    "flagDestPulcini",
    "dtInizioAttivita",
    "uniproDtFineAttivita",
    "dtValidazioneRichiesta",
    "note",
    "spebdnCodice",
    "oriproCodice",
    "modallCodice",
    "avitipproCodice",
    "detenIdFiscale",
    "vetIdFiscale",
    "flagLinea",
    "dtInizioAttivitaDetatt",
    "dtFineAttivita",
    "numCicliAnno",
    "numGruppiAnno",
    "numUovaInc",
    "flagTuttoVuoto",
    "flagFilieraRurale",
    "flagFaseProduttiva",
    "numCapannoniDetatt",
    "densitaMaxAllev",
    "avidettCapacita",
    "avidettCapacitaInc",
    "elencoCodSpecie",
    "noteDetatt"
})
public class AvidetattFromAllevfam
    extends Entity
{

    protected Integer uniproId;
    protected String aziendaCodice;
    protected String speallCodice;
    protected String propIdFiscale;
    protected String tipattCodice;
    protected String denominazione;
    protected Integer superficie;
    protected String numTelMobile;
    protected String numTelFisso;
    protected String email;
    protected String flagAlternanzaSpecie;
    protected String flagDestPulcini;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioAttivita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar uniproDtFineAttivita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtValidazioneRichiesta;
    protected String note;
    protected String spebdnCodice;
    protected String oriproCodice;
    protected String modallCodice;
    protected String avitipproCodice;
    protected String detenIdFiscale;
    protected String vetIdFiscale;
    protected String flagLinea;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioAttivitaDetatt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtFineAttivita;
    protected Integer numCicliAnno;
    protected Integer numGruppiAnno;
    protected Integer numUovaInc;
    protected String flagTuttoVuoto;
    protected String flagFilieraRurale;
    protected String flagFaseProduttiva;
    protected Integer numCapannoniDetatt;
    protected Integer densitaMaxAllev;
    protected Integer avidettCapacita;
    protected Integer avidettCapacitaInc;
    protected String elencoCodSpecie;
    protected String noteDetatt;

    /**
     * Recupera il valore della proprieta' uniproId.
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
     * Imposta il valore della proprieta' uniproId.
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
     * Recupera il valore della proprieta' aziendaCodice.
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
     * Imposta il valore della proprieta' aziendaCodice.
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
     * Recupera il valore della proprieta' speallCodice.
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
     * Imposta il valore della proprieta' speallCodice.
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
     * Recupera il valore della proprieta' propIdFiscale.
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
     * Imposta il valore della proprieta' propIdFiscale.
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
     * Recupera il valore della proprieta' tipattCodice.
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
     * Imposta il valore della proprieta' tipattCodice.
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
     * Recupera il valore della proprieta' denominazione.
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
     * Imposta il valore della proprieta' denominazione.
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
     * Recupera il valore della proprieta' superficie.
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
     * Imposta il valore della proprieta' superficie.
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
     * Recupera il valore della proprieta' numTelMobile.
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
     * Imposta il valore della proprieta' numTelMobile.
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
     * Recupera il valore della proprieta' numTelFisso.
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
     * Imposta il valore della proprieta' numTelFisso.
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
     * Recupera il valore della proprieta' email.
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
     * Imposta il valore della proprieta' email.
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
     * Recupera il valore della proprieta' flagAlternanzaSpecie.
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
     * Imposta il valore della proprieta' flagAlternanzaSpecie.
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
     * Recupera il valore della proprieta' flagDestPulcini.
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
     * Imposta il valore della proprieta' flagDestPulcini.
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
     * Recupera il valore della proprieta' dtInizioAttivita.
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
     * Imposta il valore della proprieta' dtInizioAttivita.
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
     * Recupera il valore della proprieta' uniproDtFineAttivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUniproDtFineAttivita() {
        return uniproDtFineAttivita;
    }

    /**
     * Imposta il valore della proprieta' uniproDtFineAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUniproDtFineAttivita(XMLGregorianCalendar value) {
        this.uniproDtFineAttivita = value;
    }

    /**
     * Recupera il valore della proprieta' dtValidazioneRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtValidazioneRichiesta() {
        return dtValidazioneRichiesta;
    }

    /**
     * Imposta il valore della proprieta' dtValidazioneRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtValidazioneRichiesta(XMLGregorianCalendar value) {
        this.dtValidazioneRichiesta = value;
    }

    /**
     * Recupera il valore della proprieta' note.
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
     * Imposta il valore della proprieta' note.
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
     * Recupera il valore della proprieta' spebdnCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpebdnCodice() {
        return spebdnCodice;
    }

    /**
     * Imposta il valore della proprieta' spebdnCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpebdnCodice(String value) {
        this.spebdnCodice = value;
    }

    /**
     * Recupera il valore della proprieta' oriproCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriproCodice() {
        return oriproCodice;
    }

    /**
     * Imposta il valore della proprieta' oriproCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriproCodice(String value) {
        this.oriproCodice = value;
    }

    /**
     * Recupera il valore della proprieta' modallCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModallCodice() {
        return modallCodice;
    }

    /**
     * Imposta il valore della proprieta' modallCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModallCodice(String value) {
        this.modallCodice = value;
    }

    /**
     * Recupera il valore della proprieta' avitipproCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvitipproCodice() {
        return avitipproCodice;
    }

    /**
     * Imposta il valore della proprieta' avitipproCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvitipproCodice(String value) {
        this.avitipproCodice = value;
    }

    /**
     * Recupera il valore della proprieta' detenIdFiscale.
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
     * Imposta il valore della proprieta' detenIdFiscale.
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
     * Recupera il valore della proprieta' vetIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetIdFiscale() {
        return vetIdFiscale;
    }

    /**
     * Imposta il valore della proprieta' vetIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetIdFiscale(String value) {
        this.vetIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta' flagLinea.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagLinea() {
        return flagLinea;
    }

    /**
     * Imposta il valore della proprieta' flagLinea.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagLinea(String value) {
        this.flagLinea = value;
    }

    /**
     * Recupera il valore della proprieta' dtInizioAttivitaDetatt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtInizioAttivitaDetatt() {
        return dtInizioAttivitaDetatt;
    }

    /**
     * Imposta il valore della proprieta' dtInizioAttivitaDetatt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtInizioAttivitaDetatt(XMLGregorianCalendar value) {
        this.dtInizioAttivitaDetatt = value;
    }

    /**
     * Recupera il valore della proprieta' dtFineAttivita.
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
     * Imposta il valore della proprieta' dtFineAttivita.
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
     * Recupera il valore della proprieta' numCicliAnno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCicliAnno() {
        return numCicliAnno;
    }

    /**
     * Imposta il valore della proprieta' numCicliAnno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCicliAnno(Integer value) {
        this.numCicliAnno = value;
    }

    /**
     * Recupera il valore della proprieta' numGruppiAnno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumGruppiAnno() {
        return numGruppiAnno;
    }

    /**
     * Imposta il valore della proprieta' numGruppiAnno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumGruppiAnno(Integer value) {
        this.numGruppiAnno = value;
    }

    /**
     * Recupera il valore della proprieta' numUovaInc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumUovaInc() {
        return numUovaInc;
    }

    /**
     * Imposta il valore della proprieta' numUovaInc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumUovaInc(Integer value) {
        this.numUovaInc = value;
    }

    /**
     * Recupera il valore della proprieta' flagTuttoVuoto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagTuttoVuoto() {
        return flagTuttoVuoto;
    }

    /**
     * Imposta il valore della proprieta' flagTuttoVuoto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagTuttoVuoto(String value) {
        this.flagTuttoVuoto = value;
    }

    /**
     * Recupera il valore della proprieta' flagFilieraRurale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagFilieraRurale() {
        return flagFilieraRurale;
    }

    /**
     * Imposta il valore della proprieta' flagFilieraRurale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagFilieraRurale(String value) {
        this.flagFilieraRurale = value;
    }

    /**
     * Recupera il valore della proprieta' flagFaseProduttiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagFaseProduttiva() {
        return flagFaseProduttiva;
    }

    /**
     * Imposta il valore della proprieta' flagFaseProduttiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagFaseProduttiva(String value) {
        this.flagFaseProduttiva = value;
    }

    /**
     * Recupera il valore della proprieta' numCapannoniDetatt.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCapannoniDetatt() {
        return numCapannoniDetatt;
    }

    /**
     * Imposta il valore della proprieta' numCapannoniDetatt.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapannoniDetatt(Integer value) {
        this.numCapannoniDetatt = value;
    }

    /**
     * Recupera il valore della proprieta' densitaMaxAllev.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDensitaMaxAllev() {
        return densitaMaxAllev;
    }

    /**
     * Imposta il valore della proprieta' densitaMaxAllev.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDensitaMaxAllev(Integer value) {
        this.densitaMaxAllev = value;
    }

    /**
     * Recupera il valore della proprieta' avidettCapacita.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvidettCapacita() {
        return avidettCapacita;
    }

    /**
     * Imposta il valore della proprieta' avidettCapacita.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvidettCapacita(Integer value) {
        this.avidettCapacita = value;
    }

    /**
     * Recupera il valore della proprieta' avidettCapacitaInc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvidettCapacitaInc() {
        return avidettCapacitaInc;
    }

    /**
     * Imposta il valore della proprieta' avidettCapacitaInc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvidettCapacitaInc(Integer value) {
        this.avidettCapacitaInc = value;
    }

    /**
     * Recupera il valore della proprieta' elencoCodSpecie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElencoCodSpecie() {
        return elencoCodSpecie;
    }

    /**
     * Imposta il valore della proprieta' elencoCodSpecie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElencoCodSpecie(String value) {
        this.elencoCodSpecie = value;
    }

    /**
     * Recupera il valore della proprieta' noteDetatt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteDetatt() {
        return noteDetatt;
    }

    /**
     * Imposta il valore della proprieta' noteDetatt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteDetatt(String value) {
        this.noteDetatt = value;
    }

}
