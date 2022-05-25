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
 * <p>Classe Java per avidetatt complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="avidetatt">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="avidetattId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoDelegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="uniproDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproSpeallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproSpeallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproPropIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproPropCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproTipattCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproTipattDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oriproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oriproDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallCodiceNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallDescrizioneNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproCodiceNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproDescrizioneNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="noteDetatt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avidettCapacita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="avidettCapacitaInc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="elencoCodSpecie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "avidetatt", propOrder = {
    "avidetattId",
    "tipoDelegato",
    "uniproId",
    "uniproDenominazione",
    "uniproAziendaCodice",
    "uniproSpeallCodice",
    "uniproSpeallDescrizione",
    "uniproPropIdFiscale",
    "uniproPropCognNome",
    "uniproTipattCodice",
    "uniproTipattDescrizione",
    "oriproCodice",
    "oriproDescrizione",
    "modallCodice",
    "modallDescrizione",
    "modallCodiceNew",
    "modallDescrizioneNew",
    "avitipproCodice",
    "avitipproDescrizione",
    "avitipproCodiceNew",
    "avitipproDescrizioneNew",
    "detenIdFiscale",
    "detenCognNome",
    "vetIdFiscale",
    "vetCognNome",
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
    "noteDetatt",
    "avidettCapacita",
    "avidettCapacitaInc",
    "elencoCodSpecie"
})
public class Avidetatt
    extends Entity
{

    protected Integer avidetattId;
    protected String tipoDelegato;
    protected Integer uniproId;
    protected String uniproDenominazione;
    protected String uniproAziendaCodice;
    protected String uniproSpeallCodice;
    protected String uniproSpeallDescrizione;
    protected String uniproPropIdFiscale;
    protected String uniproPropCognNome;
    protected String uniproTipattCodice;
    protected String uniproTipattDescrizione;
    protected String oriproCodice;
    protected String oriproDescrizione;
    protected String modallCodice;
    protected String modallDescrizione;
    protected String modallCodiceNew;
    protected String modallDescrizioneNew;
    protected String avitipproCodice;
    protected String avitipproDescrizione;
    protected String avitipproCodiceNew;
    protected String avitipproDescrizioneNew;
    protected String detenIdFiscale;
    protected String detenCognNome;
    protected String vetIdFiscale;
    protected String vetCognNome;
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
    protected String noteDetatt;
    protected Integer avidettCapacita;
    protected Integer avidettCapacitaInc;
    protected String elencoCodSpecie;

    /**
     * Recupera il valore della proprieta' avidetattId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvidetattId() {
        return avidetattId;
    }

    /**
     * Imposta il valore della proprieta' avidetattId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvidetattId(Integer value) {
        this.avidetattId = value;
    }

    /**
     * Recupera il valore della proprieta' tipoDelegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDelegato() {
        return tipoDelegato;
    }

    /**
     * Imposta il valore della proprieta' tipoDelegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDelegato(String value) {
        this.tipoDelegato = value;
    }

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
     * Recupera il valore della proprieta' uniproDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproDenominazione() {
        return uniproDenominazione;
    }

    /**
     * Imposta il valore della proprieta' uniproDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproDenominazione(String value) {
        this.uniproDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta' uniproAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproAziendaCodice() {
        return uniproAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta' uniproAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproAziendaCodice(String value) {
        this.uniproAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta' uniproSpeallCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproSpeallCodice() {
        return uniproSpeallCodice;
    }

    /**
     * Imposta il valore della proprieta' uniproSpeallCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproSpeallCodice(String value) {
        this.uniproSpeallCodice = value;
    }

    /**
     * Recupera il valore della proprieta' uniproSpeallDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproSpeallDescrizione() {
        return uniproSpeallDescrizione;
    }

    /**
     * Imposta il valore della proprieta' uniproSpeallDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproSpeallDescrizione(String value) {
        this.uniproSpeallDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta' uniproPropIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproPropIdFiscale() {
        return uniproPropIdFiscale;
    }

    /**
     * Imposta il valore della proprieta' uniproPropIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproPropIdFiscale(String value) {
        this.uniproPropIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta' uniproPropCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproPropCognNome() {
        return uniproPropCognNome;
    }

    /**
     * Imposta il valore della proprieta' uniproPropCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproPropCognNome(String value) {
        this.uniproPropCognNome = value;
    }

    /**
     * Recupera il valore della proprieta' uniproTipattCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproTipattCodice() {
        return uniproTipattCodice;
    }

    /**
     * Imposta il valore della proprieta' uniproTipattCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproTipattCodice(String value) {
        this.uniproTipattCodice = value;
    }

    /**
     * Recupera il valore della proprieta' uniproTipattDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniproTipattDescrizione() {
        return uniproTipattDescrizione;
    }

    /**
     * Imposta il valore della proprieta' uniproTipattDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniproTipattDescrizione(String value) {
        this.uniproTipattDescrizione = value;
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
     * Recupera il valore della proprieta' oriproDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriproDescrizione() {
        return oriproDescrizione;
    }

    /**
     * Imposta il valore della proprieta' oriproDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriproDescrizione(String value) {
        this.oriproDescrizione = value;
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
     * Recupera il valore della proprieta' modallDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModallDescrizione() {
        return modallDescrizione;
    }

    /**
     * Imposta il valore della proprieta' modallDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModallDescrizione(String value) {
        this.modallDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta' modallCodiceNew.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModallCodiceNew() {
        return modallCodiceNew;
    }

    /**
     * Imposta il valore della proprieta' modallCodiceNew.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModallCodiceNew(String value) {
        this.modallCodiceNew = value;
    }

    /**
     * Recupera il valore della proprieta' modallDescrizioneNew.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModallDescrizioneNew() {
        return modallDescrizioneNew;
    }

    /**
     * Imposta il valore della proprieta' modallDescrizioneNew.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModallDescrizioneNew(String value) {
        this.modallDescrizioneNew = value;
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
     * Recupera il valore della proprieta' avitipproDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvitipproDescrizione() {
        return avitipproDescrizione;
    }

    /**
     * Imposta il valore della proprieta' avitipproDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvitipproDescrizione(String value) {
        this.avitipproDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta' avitipproCodiceNew.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvitipproCodiceNew() {
        return avitipproCodiceNew;
    }

    /**
     * Imposta il valore della proprieta' avitipproCodiceNew.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvitipproCodiceNew(String value) {
        this.avitipproCodiceNew = value;
    }

    /**
     * Recupera il valore della proprieta' avitipproDescrizioneNew.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvitipproDescrizioneNew() {
        return avitipproDescrizioneNew;
    }

    /**
     * Imposta il valore della proprieta' avitipproDescrizioneNew.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvitipproDescrizioneNew(String value) {
        this.avitipproDescrizioneNew = value;
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
     * Recupera il valore della proprieta' detenCognNome.
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
     * Imposta il valore della proprieta' detenCognNome.
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
     * Recupera il valore della proprieta' vetCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetCognNome() {
        return vetCognNome;
    }

    /**
     * Imposta il valore della proprieta' vetCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetCognNome(String value) {
        this.vetCognNome = value;
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

}
