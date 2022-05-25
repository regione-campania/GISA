/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per controlliallevamentiWsSearchTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="controlliallevamentiWsSearchTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allevId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dtControllo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagVitelli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controlliallevamentiWsSearchTO", propOrder = {
    "allevId",
    "dtControllo",
    "flagVitelli"
})
public class ControlliallevamentiWsSearchTO {

    protected Integer allevId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtControllo;
    protected String flagVitelli;

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

}
