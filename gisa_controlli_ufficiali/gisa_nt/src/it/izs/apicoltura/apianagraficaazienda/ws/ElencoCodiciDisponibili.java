/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaazienda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per elencoCodiciDisponibili complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="elencoCodiciDisponibili">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comIstat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comProSigla" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suffisso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoCodiciDisponibili", propOrder = {
    "comIstat",
    "comProSigla",
    "suffisso"
})
public class ElencoCodiciDisponibili {

    @XmlElement(required = true)
    protected String comIstat;
    @XmlElement(required = true)
    protected String comProSigla;
    @XmlElement(required = true)
    protected String suffisso;

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
     * Recupera il valore della proprieta suffisso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffisso() {
        return suffisso;
    }

    /**
     * Imposta il valore della proprieta suffisso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffisso(String value) {
        this.suffisso = value;
    }

}
