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
 * <p>Classe Java per insertUnitaProduttiva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="insertUnitaProduttiva">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UnitaProduttivaTO" type="{http://ws.anagrafica.bdn.izs.it/}unipro" minOccurs="0"/>
 *         &lt;element name="DettaglioAttivitaTO" type="{http://ws.anagrafica.bdn.izs.it/}avidetatt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insertUnitaProduttiva", propOrder = {
    "unitaProduttivaTO",
    "dettaglioAttivitaTO"
})
public class InsertUnitaProduttiva {

    @XmlElement(name = "UnitaProduttivaTO")
    protected Unipro unitaProduttivaTO;
    @XmlElement(name = "DettaglioAttivitaTO")
    protected Avidetatt dettaglioAttivitaTO;

    /**
     * Recupera il valore della proprieta unitaProduttivaTO.
     * 
     * @return
     *     possible object is
     *     {@link Unipro }
     *     
     */
    public Unipro getUnitaProduttivaTO() {
        return unitaProduttivaTO;
    }

    /**
     * Imposta il valore della proprieta unitaProduttivaTO.
     * 
     * @param value
     *     allowed object is
     *     {@link Unipro }
     *     
     */
    public void setUnitaProduttivaTO(Unipro value) {
        this.unitaProduttivaTO = value;
    }

    /**
     * Recupera il valore della proprieta dettaglioAttivitaTO.
     * 
     * @return
     *     possible object is
     *     {@link Avidetatt }
     *     
     */
    public Avidetatt getDettaglioAttivitaTO() {
        return dettaglioAttivitaTO;
    }

    /**
     * Imposta il valore della proprieta dettaglioAttivitaTO.
     * 
     * @param value
     *     allowed object is
     *     {@link Avidetatt }
     *     
     */
    public void setDettaglioAttivitaTO(Avidetatt value) {
        this.dettaglioAttivitaTO = value;
    }

}
