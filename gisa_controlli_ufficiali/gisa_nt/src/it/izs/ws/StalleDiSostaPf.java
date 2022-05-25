/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per stalleDiSostaPf complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="stalleDiSostaPf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="checkliststalledisosta" type="{http://ws.izs.it}checkliststalledisostaWsTO" minOccurs="0"/>
 *         &lt;element name="controlloAllevamento" type="{http://ws.izs.it}controlliallevamentiWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stalleDiSostaPf", propOrder = {
    "checkliststalledisosta",
    "controlloAllevamento"
})
public class StalleDiSostaPf {

    protected CheckliststalledisostaWsTO checkliststalledisosta;
    protected ControlliallevamentiWS controlloAllevamento;

    /**
     * Recupera il valore della proprieta checkliststalledisosta.
     * 
     * @return
     *     possible object is
     *     {@link CheckliststalledisostaWsTO }
     *     
     */
    public CheckliststalledisostaWsTO getCheckliststalledisosta() {
        return checkliststalledisosta;
    }

    /**
     * Imposta il valore della proprieta checkliststalledisosta.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckliststalledisostaWsTO }
     *     
     */
    public void setCheckliststalledisosta(CheckliststalledisostaWsTO value) {
        this.checkliststalledisosta = value;
    }

    /**
     * Recupera il valore della proprieta controlloAllevamento.
     * 
     * @return
     *     possible object is
     *     {@link ControlliallevamentiWS }
     *     
     */
    public ControlliallevamentiWS getControlloAllevamento() {
        return controlloAllevamento;
    }

    /**
     * Imposta il valore della proprieta controlloAllevamento.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlliallevamentiWS }
     *     
     */
    public void setControlloAllevamento(ControlliallevamentiWS value) {
        this.controlloAllevamento = value;
    }

}
