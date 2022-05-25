/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per searchUnitaProduttiva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="searchUnitaProduttiva">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UnitaProduttivaSearchTO" type="{http://ws.anagrafica.bdn.izs.it/}unipro"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchUnitaProduttiva", propOrder = {
    "unitaProduttivaSearchTO"
})
public class SearchUnitaProduttiva {

    @XmlElement(name = "UnitaProduttivaSearchTO", required = true)
    protected Unipro unitaProduttivaSearchTO;

    /**
     * Recupera il valore della proprieta unitaProduttivaSearchTO.
     * 
     * @return
     *     possible object is
     *     {@link Unipro }
     *     
     */
    public Unipro getUnitaProduttivaSearchTO() {
        return unitaProduttivaSearchTO;
    }

    /**
     * Imposta il valore della proprieta unitaProduttivaSearchTO.
     * 
     * @param value
     *     allowed object is
     *     {@link Unipro }
     *     
     */
    public void setUnitaProduttivaSearchTO(Unipro value) {
        this.unitaProduttivaSearchTO = value;
    }

}
