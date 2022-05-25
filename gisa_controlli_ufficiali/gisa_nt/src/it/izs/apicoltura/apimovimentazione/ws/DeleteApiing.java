/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apimovimentazione.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per deleteApiing complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="deleteApiing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApiingTO" type="{http://ws.apimovimentazione.apicoltura.izs.it/}apiing"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteApiing", propOrder = {
    "apiingTO"
})
public class DeleteApiing {

    @XmlElement(name = "ApiingTO", required = true)
    protected Apiing apiingTO;

    /**
     * Recupera il valore della proprieta apiingTO.
     * 
     * @return
     *     possible object is
     *     {@link Apiing }
     *     
     */
    public Apiing getApiingTO() {
        return apiingTO;
    }

    /**
     * Imposta il valore della proprieta apiingTO.
     * 
     * @param value
     *     allowed object is
     *     {@link Apiing }
     *     
     */
    public void setApiingTO(Apiing value) {
        this.apiingTO = value;
    }

}
