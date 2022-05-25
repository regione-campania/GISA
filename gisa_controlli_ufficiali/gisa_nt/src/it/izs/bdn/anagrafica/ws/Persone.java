/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per persone complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="persone">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.anagrafica.bdn.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="personeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comIstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proSigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "persone", propOrder = {
    "personeId",
    "idFiscale",
    "cognome",
    "nome",
    "cognNome",
    "indirizzo",
    "cap",
    "localita",
    "telefono",
    "email",
    "stCodice",
    "stNome",
    "comIstat",
    "comDescrizione",
    "proSigla",
    "proNome",
    "aslCodice"
})
public class Persone
    extends Entity
{

    protected Integer personeId;
    protected String idFiscale;
    protected String cognome;
    protected String nome;
    protected String cognNome;
    protected String indirizzo;
    protected String cap;
    protected String localita;
    protected String telefono;
    protected String email;
    protected String stCodice;
    protected String stNome;
    protected String comIstat;
    protected String comDescrizione;
    protected String proSigla;
    protected String proNome;
    protected String aslCodice;

    /**
     * Recupera il valore della proprieta personeId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPersoneId() {
        return personeId;
    }

    /**
     * Imposta il valore della proprieta personeId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPersoneId(Integer value) {
        this.personeId = value;
    }

    /**
     * Recupera il valore della proprieta idFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFiscale() {
        return idFiscale;
    }

    /**
     * Imposta il valore della proprieta idFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFiscale(String value) {
        this.idFiscale = value;
    }

    /**
     * Recupera il valore della proprieta cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprieta cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprieta nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprieta nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprieta cognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognNome() {
        return cognNome;
    }

    /**
     * Imposta il valore della proprieta cognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognNome(String value) {
        this.cognNome = value;
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
     * Recupera il valore della proprieta telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprieta telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
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
     * Recupera il valore della proprieta stCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStCodice() {
        return stCodice;
    }

    /**
     * Imposta il valore della proprieta stCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStCodice(String value) {
        this.stCodice = value;
    }

    /**
     * Recupera il valore della proprieta stNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStNome() {
        return stNome;
    }

    /**
     * Imposta il valore della proprieta stNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStNome(String value) {
        this.stNome = value;
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
     * Recupera il valore della proprieta proSigla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProSigla() {
        return proSigla;
    }

    /**
     * Imposta il valore della proprieta proSigla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProSigla(String value) {
        this.proSigla = value;
    }

    /**
     * Recupera il valore della proprieta proNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProNome() {
        return proNome;
    }

    /**
     * Imposta il valore della proprieta proNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProNome(String value) {
        this.proNome = value;
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

}
