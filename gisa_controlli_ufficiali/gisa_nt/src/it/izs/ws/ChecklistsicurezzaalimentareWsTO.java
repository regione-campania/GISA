/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per checklistsicurezzaalimentareWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistsicurezzaalimentareWsTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.izs.it}checklistsicurezzaalimentareTO">
 *       &lt;sequence>
 *         &lt;element name="requisiti" type="{http://ws.izs.it}requisitiSaWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistsicurezzaalimentareWsTO", propOrder = {
    "requisiti"
})
public class ChecklistsicurezzaalimentareWsTO
    extends ChecklistsicurezzaalimentareTO
{

    @XmlElement(nillable = true)
    protected List<RequisitiSaWsTO> requisiti;

    /**
     * Gets the value of the requisiti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requisiti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequisiti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequisitiSaWsTO }
     * 
     * 
     */
    public List<RequisitiSaWsTO> getRequisiti() {
        if (requisiti == null) {
            requisiti = new ArrayList<RequisitiSaWsTO>();
        }
        return this.requisiti;
    }

}
