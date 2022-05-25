/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.sinsa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per updateAccettazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="updateAccettazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroScheda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroAccettazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataAccettazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateAccettazione", propOrder = {
    "numeroScheda",
    "numeroAccettazione",
    "dataAccettazione"
})
public class UpdateAccettazione {

    protected String numeroScheda;
    protected String numeroAccettazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccettazione;

    /**
     * Recupera il valore della proprieta numeroScheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroScheda() {
        return numeroScheda;
    }

    /**
     * Imposta il valore della proprieta numeroScheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroScheda(String value) {
        this.numeroScheda = value;
    }

    /**
     * Recupera il valore della proprieta numeroAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAccettazione() {
        return numeroAccettazione;
    }

    /**
     * Imposta il valore della proprieta numeroAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAccettazione(String value) {
        this.numeroAccettazione = value;
    }

    /**
     * Recupera il valore della proprieta dataAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAccettazione() {
        return dataAccettazione;
    }

    /**
     * Imposta il valore della proprieta dataAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAccettazione(XMLGregorianCalendar value) {
        this.dataAccettazione = value;
    }

}
