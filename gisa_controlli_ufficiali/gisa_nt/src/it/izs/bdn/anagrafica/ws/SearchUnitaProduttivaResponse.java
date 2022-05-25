/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per searchUnitaProduttivaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="searchUnitaProduttivaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UnitaProduttivaTO" type="{http://ws.anagrafica.bdn.izs.it/}unipro" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchUnitaProduttivaResponse", propOrder = {
    "unitaProduttivaTO"
})
public class SearchUnitaProduttivaResponse {

    @XmlElement(name = "UnitaProduttivaTO")
    protected List<Unipro> unitaProduttivaTO;

    /**
     * Gets the value of the unitaProduttivaTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitaProduttivaTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitaProduttivaTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Unipro }
     * 
     * 
     */
    public List<Unipro> getUnitaProduttivaTO() {
        if (unitaProduttivaTO == null) {
            unitaProduttivaTO = new ArrayList<Unipro>();
        }
        return this.unitaProduttivaTO;
    }

}
