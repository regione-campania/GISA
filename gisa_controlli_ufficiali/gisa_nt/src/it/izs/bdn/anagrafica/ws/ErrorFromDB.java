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
 * <p>Classe Java per errorFromDB complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="errorFromDB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="utenteMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errorFromDB", propOrder = {
    "codErrore",
    "logMsg",
    "utenteMsg"
})
public class ErrorFromDB {

    protected String codErrore;
    protected String logMsg;
    protected String utenteMsg;

    /**
     * Recupera il valore della proprieta codErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodErrore() {
        return codErrore;
    }

    /**
     * Imposta il valore della proprieta codErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodErrore(String value) {
        this.codErrore = value;
    }

    /**
     * Recupera il valore della proprieta logMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * Imposta il valore della proprieta logMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMsg(String value) {
        this.logMsg = value;
    }

    /**
     * Recupera il valore della proprieta utenteMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtenteMsg() {
        return utenteMsg;
    }

    /**
     * Imposta il valore della proprieta utenteMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtenteMsg(String value) {
        this.utenteMsg = value;
    }

}
