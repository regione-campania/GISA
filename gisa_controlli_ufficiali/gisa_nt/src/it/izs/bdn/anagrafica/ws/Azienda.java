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
 * <p>Classe Java per azienda complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="azienda">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="aziendaId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtApertura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtChiusura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="distrettoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distrettoDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comProSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foglioCatastale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="particella" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="holderIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="holderCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nordGb" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="estGb" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="fusoGb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xUtm" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="yUtm" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="fusoUtm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "azienda", propOrder = {
    "aziendaId",
    "codice",
    "longitudine",
    "latitudine",
    "localita",
    "indirizzo",
    "dtApertura",
    "dtChiusura",
    "distrettoCodice",
    "distrettoDenominazione",
    "aslCodice",
    "aslDenominazione",
    "comIstat",
    "comProSigla",
    "comCap",
    "comDescrizione",
    "foglioCatastale",
    "particella",
    "sezione",
    "subalterno",
    "holderIdFiscale",
    "holderCognNome",
    "nordGb",
    "estGb",
    "fusoGb",
    "xUtm",
    "yUtm",
    "fusoUtm",
    "note"
})
public class Azienda
    extends Entity
{

    protected Integer aziendaId;
    protected String codice;
    protected String longitudine;
    protected String latitudine;
    protected String localita;
    protected String indirizzo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtApertura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtChiusura;
    protected String distrettoCodice;
    protected String distrettoDenominazione;
    protected String aslCodice;
    protected String aslDenominazione;
    protected String comIstat;
    protected String comProSigla;
    protected String comCap;
    protected String comDescrizione;
    protected String foglioCatastale;
    protected String particella;
    protected String sezione;
    protected String subalterno;
    protected String holderIdFiscale;
    protected String holderCognNome;
    protected Double nordGb;
    protected Double estGb;
    protected String fusoGb;
    protected Double xUtm;
    protected Double yUtm;
    protected String fusoUtm;
    protected String note;

    /**
     * Recupera il valore della proprieta aziendaId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAziendaId() {
        return aziendaId;
    }

    /**
     * Imposta il valore della proprieta aziendaId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAziendaId(Integer value) {
        this.aziendaId = value;
    }

    /**
     * Recupera il valore della proprieta codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprieta codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprieta longitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitudine() {
        return longitudine;
    }

    /**
     * Imposta il valore della proprieta longitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitudine(String value) {
        this.longitudine = value;
    }

    /**
     * Recupera il valore della proprieta latitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitudine() {
        return latitudine;
    }

    /**
     * Imposta il valore della proprieta latitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitudine(String value) {
        this.latitudine = value;
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
     * Recupera il valore della proprieta distrettoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrettoCodice() {
        return distrettoCodice;
    }

    /**
     * Imposta il valore della proprieta distrettoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrettoCodice(String value) {
        this.distrettoCodice = value;
    }

    /**
     * Recupera il valore della proprieta distrettoDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrettoDenominazione() {
        return distrettoDenominazione;
    }

    /**
     * Imposta il valore della proprieta distrettoDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrettoDenominazione(String value) {
        this.distrettoDenominazione = value;
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
     * Recupera il valore della proprieta comCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCap() {
        return comCap;
    }

    /**
     * Imposta il valore della proprieta comCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCap(String value) {
        this.comCap = value;
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
     * Recupera il valore della proprieta foglioCatastale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoglioCatastale() {
        return foglioCatastale;
    }

    /**
     * Imposta il valore della proprieta foglioCatastale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoglioCatastale(String value) {
        this.foglioCatastale = value;
    }

    /**
     * Recupera il valore della proprieta particella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Imposta il valore della proprieta particella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticella(String value) {
        this.particella = value;
    }

    /**
     * Recupera il valore della proprieta sezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Imposta il valore della proprieta sezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Recupera il valore della proprieta subalterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubalterno() {
        return subalterno;
    }

    /**
     * Imposta il valore della proprieta subalterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
    }

    /**
     * Recupera il valore della proprieta holderIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolderIdFiscale() {
        return holderIdFiscale;
    }

    /**
     * Imposta il valore della proprieta holderIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolderIdFiscale(String value) {
        this.holderIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta holderCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolderCognNome() {
        return holderCognNome;
    }

    /**
     * Imposta il valore della proprieta holderCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolderCognNome(String value) {
        this.holderCognNome = value;
    }

    /**
     * Recupera il valore della proprieta nordGb.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNordGb() {
        return nordGb;
    }

    /**
     * Imposta il valore della proprieta nordGb.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNordGb(Double value) {
        this.nordGb = value;
    }

    /**
     * Recupera il valore della proprieta estGb.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEstGb() {
        return estGb;
    }

    /**
     * Imposta il valore della proprieta estGb.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEstGb(Double value) {
        this.estGb = value;
    }

    /**
     * Recupera il valore della proprieta fusoGb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFusoGb() {
        return fusoGb;
    }

    /**
     * Imposta il valore della proprieta fusoGb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFusoGb(String value) {
        this.fusoGb = value;
    }

    /**
     * Recupera il valore della proprieta xUtm.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXUtm() {
        return xUtm;
    }

    /**
     * Imposta il valore della proprieta xUtm.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXUtm(Double value) {
        this.xUtm = value;
    }

    /**
     * Recupera il valore della proprieta yUtm.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYUtm() {
        return yUtm;
    }

    /**
     * Imposta il valore della proprieta yUtm.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYUtm(Double value) {
        this.yUtm = value;
    }

    /**
     * Recupera il valore della proprieta fusoUtm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFusoUtm() {
        return fusoUtm;
    }

    /**
     * Imposta il valore della proprieta fusoUtm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFusoUtm(String value) {
        this.fusoUtm = value;
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
