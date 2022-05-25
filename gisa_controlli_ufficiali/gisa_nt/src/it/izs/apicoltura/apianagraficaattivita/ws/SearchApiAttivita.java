/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaattivita.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per searchApiAttivita complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="searchApiAttivita">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApiAttivitaSearch" type="{http://ws.apianagrafica.apicoltura.izs.it/}apiatt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchApiAttivita", propOrder = {
    "apiAttivitaSearch"
})
public class SearchApiAttivita {

    @XmlElement(name = "ApiAttivitaSearch", required = true)
    protected Apiatt apiAttivitaSearch;

    /**
     * Recupera il valore della proprieta apiAttivitaSearch.
     * 
     * @return
     *     possible object is
     *     {@link Apiatt }
     *     
     */
    public Apiatt getApiAttivitaSearch() {
        return apiAttivitaSearch;
    }

    /**
     * Imposta il valore della proprieta apiAttivitaSearch.
     * 
     * @param value
     *     allowed object is
     *     {@link Apiatt }
     *     
     */
    public void setApiAttivitaSearch(Apiatt value) {
        this.apiAttivitaSearch = value;
    }

}
