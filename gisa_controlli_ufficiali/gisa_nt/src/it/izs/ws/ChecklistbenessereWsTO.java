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
 * <p>Classe Java per checklistbenessereWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistbenessereWsTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.izs.it}checklistbenessereTO">
 *       &lt;sequence>
 *         &lt;element name="requisiti" type="{http://ws.izs.it}requisitiWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="box" type="{http://ws.izs.it}boxWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="capannoni" type="{http://ws.izs.it}capannoniWsTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistbenessereWsTO", propOrder = {
    "requisiti",
    "box",
    "capannoni"
})
public class ChecklistbenessereWsTO
    extends ChecklistbenessereTO
{

    @XmlElement(nillable = true)
    protected List<RequisitiWsTO> requisiti;
    protected List<BoxWsTO> box;
    protected List<CapannoniWsTO> capannoni;

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
     * {@link RequisitiWsTO }
     * 
     * 
     */
    public List<RequisitiWsTO> getRequisiti() {
        if (requisiti == null) {
            requisiti = new ArrayList<RequisitiWsTO>();
        }
        return this.requisiti;
    }
    public List<BoxWsTO> getBox() {
        if (box == null) {
        	box = new ArrayList<BoxWsTO>();
        }
        return this.box;
    }
    public List<CapannoniWsTO> getCapannoni() {
        if (capannoni == null) {
        	capannoni = new ArrayList<CapannoniWsTO>();
        }
        return this.capannoni;
    }
}
