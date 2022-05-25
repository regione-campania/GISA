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
 * <p>Classe Java per avidetattUk complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="avidetattUk">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avidetattId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="oriproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oriproDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avitipproDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagFaseProduttiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniproId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="propIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speallCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speallDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtFineAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "avidetattUk", propOrder = {
    "aziendaCodice",
    "avidetattId",
    "oriproCodice",
    "oriproDescrizione",
    "modallCodice",
    "modallDescrizione",
    "avitipproCodice",
    "avitipproDescrizione",
    "flagFaseProduttiva",
    "detenIdFiscale",
    "detenCognNome",
    "uniproId",
    "propIdFiscale",
    "propCognNome",
    "speallCodice",
    "speallDescrizione",
    "dtInizioAttivita",
    "dtFineAttivita"
})
public class AvidetattUk
    extends Entity
{

    protected String aziendaCodice;
    protected Integer avidetattId;
    protected String oriproCodice;
    protected String oriproDescrizione;
    protected String modallCodice;
    protected String modallDescrizione;
    protected String avitipproCodice;
    protected String avitipproDescrizione;
    protected String flagFaseProduttiva;
    protected String detenIdFiscale;
    protected String detenCognNome;
    protected Integer uniproId;
    protected String propIdFiscale;
    protected String propCognNome;
    protected String speallCodice;
    protected String speallDescrizione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtInizioAttivita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtFineAttivita;

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
     * Recupera il valore della proprieta avidetattId.
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
     * Imposta il valore della proprieta avidetattId.
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
     * Recupera il valore della proprieta oriproCodice.
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
     * Imposta il valore della proprieta oriproCodice.
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
     * Recupera il valore della proprieta oriproDescrizione.
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
     * Imposta il valore della proprieta oriproDescrizione.
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
     * Recupera il valore della proprieta modallCodice.
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
     * Imposta il valore della proprieta modallCodice.
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
     * Recupera il valore della proprieta modallDescrizione.
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
     * Imposta il valore della proprieta modallDescrizione.
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
     * Recupera il valore della proprieta avitipproCodice.
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
     * Imposta il valore della proprieta avitipproCodice.
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
     * Recupera il valore della proprieta avitipproDescrizione.
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
     * Imposta il valore della proprieta avitipproDescrizione.
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
     * Recupera il valore della proprieta flagFaseProduttiva.
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
     * Imposta il valore della proprieta flagFaseProduttiva.
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

}
