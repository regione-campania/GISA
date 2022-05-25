/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per checklistallevamentiTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistallevamentiTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allevId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="allevIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="critCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="critDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="critId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataVerifica" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="detCapiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detClaXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtControllo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagAziendaBdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEsitoBa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEsitoIr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagIncongruenzeRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPrescrIntenzione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPrescrizioneEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagRegStalla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagRegStallaConforme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagTipoRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagVitelli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteDetentore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCapiPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCapiPresentiCensimento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCapiRegistro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCla" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="numIncongruenzeRegistro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numMancataNotifica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numMovRegistro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="prescrNumGgRegCapi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="prescrNumGgRegDoc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="primoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAbbattimentoCapi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sanzAltro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sanzAltroDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAmministrativa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sanzBloccoMov" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sanzSequestroCapi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="secondoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistallevamentiTO", propOrder = {
    "allevId",
    "allevIdFiscale",
    "aziendaCodice",
    "caId",
    "claId",
    "critCodice",
    "critDescrizione",
    "critId",
    "dataVerifica",
    "detCapiXml",
    "detClaXml",
    "dtControllo",
    "flagAziendaBdn",
    "flagEsitoBa",
    "flagEsitoIr",
    "flagIncongruenzeRegistro",
    "flagPrescrIntenzione",
    "flagPrescrizioneEsito",
    "flagRegStalla",
    "flagRegStallaConforme",
    "flagTipoRegistro",
    "flagVitelli",
    "noteControllore",
    "noteDetentore",
    "numCapiPresenti",
    "numCapiPresentiCensimento",
    "numCapiRegistro",
    "numCla",
    "numIncongruenzeRegistro",
    "numMancataNotifica",
    "numMovRegistro",
    "prescrNumGgRegCapi",
    "prescrNumGgRegDoc",
    "primoControllore",
    "sanzAbbattimentoCapi",
    "sanzAltro",
    "sanzAltroDesc",
    "sanzAmministrativa",
    "sanzBloccoMov",
    "sanzSequestroCapi",
    "secondoControllore",
    "speCodice"
})
@XmlSeeAlso({
    ChecklistallevamentiWsTO.class
})
public class ChecklistallevamentiTO {

    protected Integer allevId;
    protected String allevIdFiscale;
    protected String aziendaCodice;
    protected Integer caId;
    protected Integer claId;
    protected String critCodice;
    protected String critDescrizione;
    protected Integer critId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerifica;
    protected String detCapiXml;
    protected String detClaXml;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtControllo;
    protected String flagAziendaBdn;
    protected String flagEsitoBa;
    protected String flagEsitoIr;
    protected String flagIncongruenzeRegistro;
    protected String flagPrescrIntenzione;
    protected String flagPrescrizioneEsito;
    protected String flagRegStalla;
    protected String flagRegStallaConforme;
    protected String flagTipoRegistro;
    protected String flagVitelli;
    protected String noteControllore;
    protected String noteDetentore;
    protected Integer numCapiPresenti;
    protected Integer numCapiPresentiCensimento;
    protected Integer numCapiRegistro;
    protected Long numCla;
    protected Integer numIncongruenzeRegistro;
    protected Integer numMancataNotifica;
    protected Integer numMovRegistro;
    protected Integer prescrNumGgRegCapi;
    protected Integer prescrNumGgRegDoc;
    protected String primoControllore;
    protected Integer sanzAbbattimentoCapi;
    protected Integer sanzAltro;
    protected String sanzAltroDesc;
    protected Integer sanzAmministrativa;
    protected Integer sanzBloccoMov;
    protected Integer sanzSequestroCapi;
    protected String secondoControllore;
    protected String speCodice;

    /**
     * Recupera il valore della proprieta allevId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAllevId() {
        return allevId;
    }

    /**
     * Imposta il valore della proprieta allevId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAllevId(Integer value) {
        this.allevId = value;
    }

    /**
     * Recupera il valore della proprieta allevIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllevIdFiscale() {
        return allevIdFiscale;
    }

    /**
     * Imposta il valore della proprieta allevIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllevIdFiscale(String value) {
        this.allevIdFiscale = value;
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
     * Recupera il valore della proprieta caId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCaId() {
        return caId;
    }

    /**
     * Imposta il valore della proprieta caId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCaId(Integer value) {
        this.caId = value;
    }

    /**
     * Recupera il valore della proprieta claId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaId() {
        return claId;
    }

    /**
     * Imposta il valore della proprieta claId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaId(Integer value) {
        this.claId = value;
    }

    /**
     * Recupera il valore della proprieta critCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCritCodice() {
        return critCodice;
    }

    /**
     * Imposta il valore della proprieta critCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCritCodice(String value) {
        this.critCodice = value;
    }

    /**
     * Recupera il valore della proprieta critDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCritDescrizione() {
        return critDescrizione;
    }

    /**
     * Imposta il valore della proprieta critDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCritDescrizione(String value) {
        this.critDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta critId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCritId() {
        return critId;
    }

    /**
     * Imposta il valore della proprieta critId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCritId(Integer value) {
        this.critId = value;
    }

    /**
     * Recupera il valore della proprieta dataVerifica.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerifica() {
        return dataVerifica;
    }

    /**
     * Imposta il valore della proprieta dataVerifica.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerifica(XMLGregorianCalendar value) {
        this.dataVerifica = value;
    }

    /**
     * Recupera il valore della proprieta detCapiXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetCapiXml() {
        return detCapiXml;
    }

    /**
     * Imposta il valore della proprieta detCapiXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetCapiXml(String value) {
        this.detCapiXml = value;
    }

    /**
     * Recupera il valore della proprieta detClaXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetClaXml() {
        return detClaXml;
    }

    /**
     * Imposta il valore della proprieta detClaXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetClaXml(String value) {
        this.detClaXml = value;
    }

    /**
     * Recupera il valore della proprieta dtControllo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtControllo() {
        return dtControllo;
    }

    /**
     * Imposta il valore della proprieta dtControllo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtControllo(XMLGregorianCalendar value) {
        this.dtControllo = value;
    }

    /**
     * Recupera il valore della proprieta flagAziendaBdn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagAziendaBdn() {
        return flagAziendaBdn;
    }

    /**
     * Imposta il valore della proprieta flagAziendaBdn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagAziendaBdn(String value) {
        this.flagAziendaBdn = value;
    }

    /**
     * Recupera il valore della proprieta flagEsitoBa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEsitoBa() {
        return flagEsitoBa;
    }

    /**
     * Imposta il valore della proprieta flagEsitoBa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEsitoBa(String value) {
        this.flagEsitoBa = value;
    }

    /**
     * Recupera il valore della proprieta flagEsitoIr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEsitoIr() {
        return flagEsitoIr;
    }

    /**
     * Imposta il valore della proprieta flagEsitoIr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEsitoIr(String value) {
        this.flagEsitoIr = value;
    }

    /**
     * Recupera il valore della proprieta flagIncongruenzeRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagIncongruenzeRegistro() {
        return flagIncongruenzeRegistro;
    }

    /**
     * Imposta il valore della proprieta flagIncongruenzeRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagIncongruenzeRegistro(String value) {
        this.flagIncongruenzeRegistro = value;
    }

    /**
     * Recupera il valore della proprieta flagPrescrIntenzione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPrescrIntenzione() {
        return flagPrescrIntenzione;
    }

    /**
     * Imposta il valore della proprieta flagPrescrIntenzione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPrescrIntenzione(String value) {
        this.flagPrescrIntenzione = value;
    }

    /**
     * Recupera il valore della proprieta flagPrescrizioneEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPrescrizioneEsito() {
        return flagPrescrizioneEsito;
    }

    /**
     * Imposta il valore della proprieta flagPrescrizioneEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPrescrizioneEsito(String value) {
        this.flagPrescrizioneEsito = value;
    }

    /**
     * Recupera il valore della proprieta flagRegStalla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRegStalla() {
        return flagRegStalla;
    }

    /**
     * Imposta il valore della proprieta flagRegStalla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRegStalla(String value) {
        this.flagRegStalla = value;
    }

    /**
     * Recupera il valore della proprieta flagRegStallaConforme.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRegStallaConforme() {
        return flagRegStallaConforme;
    }

    /**
     * Imposta il valore della proprieta flagRegStallaConforme.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRegStallaConforme(String value) {
        this.flagRegStallaConforme = value;
    }

    /**
     * Recupera il valore della proprieta flagTipoRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagTipoRegistro() {
        return flagTipoRegistro;
    }

    /**
     * Imposta il valore della proprieta flagTipoRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagTipoRegistro(String value) {
        this.flagTipoRegistro = value;
    }

    /**
     * Recupera il valore della proprieta flagVitelli.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagVitelli() {
        return flagVitelli;
    }

    /**
     * Imposta il valore della proprieta flagVitelli.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagVitelli(String value) {
        this.flagVitelli = value;
    }

    /**
     * Recupera il valore della proprieta noteControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteControllore() {
        return noteControllore;
    }

    /**
     * Imposta il valore della proprieta noteControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteControllore(String value) {
        this.noteControllore = value;
    }

    /**
     * Recupera il valore della proprieta noteDetentore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteDetentore() {
        return noteDetentore;
    }

    /**
     * Imposta il valore della proprieta noteDetentore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteDetentore(String value) {
        this.noteDetentore = value;
    }

    /**
     * Recupera il valore della proprieta numCapiPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCapiPresenti() {
        return numCapiPresenti;
    }

    /**
     * Imposta il valore della proprieta numCapiPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapiPresenti(Integer value) {
        this.numCapiPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numCapiPresentiCensimento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCapiPresentiCensimento() {
        return numCapiPresentiCensimento;
    }

    /**
     * Imposta il valore della proprieta numCapiPresentiCensimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapiPresentiCensimento(Integer value) {
        this.numCapiPresentiCensimento = value;
    }

    /**
     * Recupera il valore della proprieta numCapiRegistro.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCapiRegistro() {
        return numCapiRegistro;
    }

    /**
     * Imposta il valore della proprieta numCapiRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapiRegistro(Integer value) {
        this.numCapiRegistro = value;
    }

    /**
     * Recupera il valore della proprieta numCla.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumCla() {
        return numCla;
    }

    /**
     * Imposta il valore della proprieta numCla.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumCla(Long value) {
        this.numCla = value;
    }

    /**
     * Recupera il valore della proprieta numIncongruenzeRegistro.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumIncongruenzeRegistro() {
        return numIncongruenzeRegistro;
    }

    /**
     * Imposta il valore della proprieta numIncongruenzeRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumIncongruenzeRegistro(Integer value) {
        this.numIncongruenzeRegistro = value;
    }

    /**
     * Recupera il valore della proprieta numMancataNotifica.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumMancataNotifica() {
        return numMancataNotifica;
    }

    /**
     * Imposta il valore della proprieta numMancataNotifica.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumMancataNotifica(Integer value) {
        this.numMancataNotifica = value;
    }

    /**
     * Recupera il valore della proprieta numMovRegistro.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumMovRegistro() {
        return numMovRegistro;
    }

    /**
     * Imposta il valore della proprieta numMovRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumMovRegistro(Integer value) {
        this.numMovRegistro = value;
    }

    /**
     * Recupera il valore della proprieta prescrNumGgRegCapi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrescrNumGgRegCapi() {
        return prescrNumGgRegCapi;
    }

    /**
     * Imposta il valore della proprieta prescrNumGgRegCapi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrescrNumGgRegCapi(Integer value) {
        this.prescrNumGgRegCapi = value;
    }

    /**
     * Recupera il valore della proprieta prescrNumGgRegDoc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrescrNumGgRegDoc() {
        return prescrNumGgRegDoc;
    }

    /**
     * Imposta il valore della proprieta prescrNumGgRegDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrescrNumGgRegDoc(Integer value) {
        this.prescrNumGgRegDoc = value;
    }

    /**
     * Recupera il valore della proprieta primoControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimoControllore() {
        return primoControllore;
    }

    /**
     * Imposta il valore della proprieta primoControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimoControllore(String value) {
        this.primoControllore = value;
    }

    /**
     * Recupera il valore della proprieta sanzAbbattimentoCapi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSanzAbbattimentoCapi() {
        return sanzAbbattimentoCapi;
    }

    /**
     * Imposta il valore della proprieta sanzAbbattimentoCapi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSanzAbbattimentoCapi(Integer value) {
        this.sanzAbbattimentoCapi = value;
    }

    /**
     * Recupera il valore della proprieta sanzAltro.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSanzAltro() {
        return sanzAltro;
    }

    /**
     * Imposta il valore della proprieta sanzAltro.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSanzAltro(Integer value) {
        this.sanzAltro = value;
    }

    /**
     * Recupera il valore della proprieta sanzAltroDesc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSanzAltroDesc() {
        return sanzAltroDesc;
    }

    /**
     * Imposta il valore della proprieta sanzAltroDesc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSanzAltroDesc(String value) {
        this.sanzAltroDesc = value;
    }

    /**
     * Recupera il valore della proprieta sanzAmministrativa.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSanzAmministrativa() {
        return sanzAmministrativa;
    }

    /**
     * Imposta il valore della proprieta sanzAmministrativa.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSanzAmministrativa(Integer value) {
        this.sanzAmministrativa = value;
    }

    /**
     * Recupera il valore della proprieta sanzBloccoMov.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSanzBloccoMov() {
        return sanzBloccoMov;
    }

    /**
     * Imposta il valore della proprieta sanzBloccoMov.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSanzBloccoMov(Integer value) {
        this.sanzBloccoMov = value;
    }

    /**
     * Recupera il valore della proprieta sanzSequestroCapi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSanzSequestroCapi() {
        return sanzSequestroCapi;
    }

    /**
     * Imposta il valore della proprieta sanzSequestroCapi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSanzSequestroCapi(Integer value) {
        this.sanzSequestroCapi = value;
    }

    /**
     * Recupera il valore della proprieta secondoControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondoControllore() {
        return secondoControllore;
    }

    /**
     * Imposta il valore della proprieta secondoControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondoControllore(String value) {
        this.secondoControllore = value;
    }

    /**
     * Recupera il valore della proprieta speCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeCodice() {
        return speCodice;
    }

    /**
     * Imposta il valore della proprieta speCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeCodice(String value) {
        this.speCodice = value;
    }

}
