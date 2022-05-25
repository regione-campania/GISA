/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per autorizzazioneTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="autorizzazioneTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceLingua" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grspeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grspeDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grspeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="risoluzione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizzazioneTO", propOrder = {
    "codiceLingua",
    "grspeCodice",
    "grspeDescrizione",
    "grspeId",
    "risoluzione",
    "ruolo"
})
public class AutorizzazioneTO {

    protected String codiceLingua;
    protected String grspeCodice;
    protected String grspeDescrizione;
    protected String grspeId;
    protected String risoluzione;
    protected String ruolo;

    /**
     * Recupera il valore della proprieta codiceLingua.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceLingua() {
        return codiceLingua;
    }

    /**
     * Imposta il valore della proprieta codiceLingua.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceLingua(String value) {
        this.codiceLingua = value;
    }

    /**
     * Recupera il valore della proprieta grspeCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrspeCodice() {
        return grspeCodice;
    }

    /**
     * Imposta il valore della proprieta grspeCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrspeCodice(String value) {
        this.grspeCodice = value;
    }

    /**
     * Recupera il valore della proprieta grspeDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrspeDescrizione() {
        return grspeDescrizione;
    }

    /**
     * Imposta il valore della proprieta grspeDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrspeDescrizione(String value) {
        this.grspeDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta grspeId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrspeId() {
        return grspeId;
    }

    /**
     * Imposta il valore della proprieta grspeId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrspeId(String value) {
        this.grspeId = value;
    }

    /**
     * Recupera il valore della proprieta risoluzione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRisoluzione() {
        return risoluzione;
    }

    /**
     * Imposta il valore della proprieta risoluzione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRisoluzione(String value) {
        this.risoluzione = value;
    }

    /**
     * Recupera il valore della proprieta ruolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il valore della proprieta ruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuolo(String value) {
        this.ruolo = value;
    }

}
