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
 * <p>Classe Java per checklistallevamentiWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistallevamentiWsTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.izs.it}checklistallevamentiTO">
 *       &lt;sequence>
 *         &lt;element name="capiAnomali1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomali2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomali3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomali4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomaliePassaporto1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomaliePassaporto2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomaliePassaporto3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAnomaliePassaporto4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAssentiBdn1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAssentiBdn2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAssentiBdn3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiAssentiBdn4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiControllati1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiControllati2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiControllati3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiControllati4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent20071" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent20072" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent20073" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent20074" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiIrregIdent4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiMicrochipIlleg1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiMicrochipIlleg2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiMicrochipIlleg3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiMicrochipIlleg4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiNonIdent1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiNonIdent2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiNonIdent3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiNonIdent4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPresenti1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPresenti2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPresenti3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPresenti4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPriviPassaporto1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPriviPassaporto2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPriviPassaporto3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="capiPriviPassaporto4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cldId1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cldId2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cldId3" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cldId4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clspeCodice1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clspeCodice2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clspeCodice3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clspeCodice4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistallevamentiWsTO", propOrder = {
    "capiAnomali1",
    "capiAnomali2",
    "capiAnomali3",
    "capiAnomali4",
    "capiAnomaliePassaporto1",
    "capiAnomaliePassaporto2",
    "capiAnomaliePassaporto3",
    "capiAnomaliePassaporto4",
    "capiAssentiBdn1",
    "capiAssentiBdn2",
    "capiAssentiBdn3",
    "capiAssentiBdn4",
    "capiControllati1",
    "capiControllati2",
    "capiControllati3",
    "capiControllati4",
    "capiIrregIdent1",
    "capiIrregIdent2",
    "capiIrregIdent20071",
    "capiIrregIdent20072",
    "capiIrregIdent20073",
    "capiIrregIdent20074",
    "capiIrregIdent3",
    "capiIrregIdent4",
    "capiMicrochipIlleg1",
    "capiMicrochipIlleg2",
    "capiMicrochipIlleg3",
    "capiMicrochipIlleg4",
    "capiNonIdent1",
    "capiNonIdent2",
    "capiNonIdent3",
    "capiNonIdent4",
    "capiPresenti1",
    "capiPresenti2",
    "capiPresenti3",
    "capiPresenti4",
    "capiPriviPassaporto1",
    "capiPriviPassaporto2",
    "capiPriviPassaporto3",
    "capiPriviPassaporto4",
    "cldId1",
    "cldId2",
    "cldId3",
    "cldId4",
    "clspeCodice1",
    "clspeCodice2",
    "clspeCodice3",
    "clspeCodice4"
})
public class ChecklistallevamentiWsTO
    extends ChecklistallevamentiTO
{

    protected Integer capiAnomali1;
    protected Integer capiAnomali2;
    protected Integer capiAnomali3;
    protected Integer capiAnomali4;
    protected Integer capiAnomaliePassaporto1;
    protected Integer capiAnomaliePassaporto2;
    protected Integer capiAnomaliePassaporto3;
    protected Integer capiAnomaliePassaporto4;
    protected Integer capiAssentiBdn1;
    protected Integer capiAssentiBdn2;
    protected Integer capiAssentiBdn3;
    protected Integer capiAssentiBdn4;
    protected Integer capiControllati1;
    protected Integer capiControllati2;
    protected Integer capiControllati3;
    protected Integer capiControllati4;
    protected Integer capiIrregIdent1;
    protected Integer capiIrregIdent2;
    protected Integer capiIrregIdent20071;
    protected Integer capiIrregIdent20072;
    protected Integer capiIrregIdent20073;
    protected Integer capiIrregIdent20074;
    protected Integer capiIrregIdent3;
    protected Integer capiIrregIdent4;
    protected Integer capiMicrochipIlleg1;
    protected Integer capiMicrochipIlleg2;
    protected Integer capiMicrochipIlleg3;
    protected Integer capiMicrochipIlleg4;
    protected Integer capiNonIdent1;
    protected Integer capiNonIdent2;
    protected Integer capiNonIdent3;
    protected Integer capiNonIdent4;
    protected Integer capiPresenti1;
    protected Integer capiPresenti2;
    protected Integer capiPresenti3;
    protected Integer capiPresenti4;
    protected Integer capiPriviPassaporto1;
    protected Integer capiPriviPassaporto2;
    protected Integer capiPriviPassaporto3;
    protected Integer capiPriviPassaporto4;
    protected Integer cldId1;
    protected Integer cldId2;
    protected Integer cldId3;
    protected Integer cldId4;
    protected String clspeCodice1;
    protected String clspeCodice2;
    protected String clspeCodice3;
    protected String clspeCodice4;

    /**
     * Recupera il valore della proprieta capiAnomali1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomali1() {
        return capiAnomali1;
    }

    /**
     * Imposta il valore della proprieta capiAnomali1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomali1(Integer value) {
        this.capiAnomali1 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomali2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomali2() {
        return capiAnomali2;
    }

    /**
     * Imposta il valore della proprieta capiAnomali2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomali2(Integer value) {
        this.capiAnomali2 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomali3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomali3() {
        return capiAnomali3;
    }

    /**
     * Imposta il valore della proprieta capiAnomali3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomali3(Integer value) {
        this.capiAnomali3 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomali4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomali4() {
        return capiAnomali4;
    }

    /**
     * Imposta il valore della proprieta capiAnomali4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomali4(Integer value) {
        this.capiAnomali4 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomaliePassaporto1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomaliePassaporto1() {
        return capiAnomaliePassaporto1;
    }

    /**
     * Imposta il valore della proprieta capiAnomaliePassaporto1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomaliePassaporto1(Integer value) {
        this.capiAnomaliePassaporto1 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomaliePassaporto2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomaliePassaporto2() {
        return capiAnomaliePassaporto2;
    }

    /**
     * Imposta il valore della proprieta capiAnomaliePassaporto2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomaliePassaporto2(Integer value) {
        this.capiAnomaliePassaporto2 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomaliePassaporto3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomaliePassaporto3() {
        return capiAnomaliePassaporto3;
    }

    /**
     * Imposta il valore della proprieta capiAnomaliePassaporto3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomaliePassaporto3(Integer value) {
        this.capiAnomaliePassaporto3 = value;
    }

    /**
     * Recupera il valore della proprieta capiAnomaliePassaporto4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAnomaliePassaporto4() {
        return capiAnomaliePassaporto4;
    }

    /**
     * Imposta il valore della proprieta capiAnomaliePassaporto4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAnomaliePassaporto4(Integer value) {
        this.capiAnomaliePassaporto4 = value;
    }

    /**
     * Recupera il valore della proprieta capiAssentiBdn1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAssentiBdn1() {
        return capiAssentiBdn1;
    }

    /**
     * Imposta il valore della proprieta capiAssentiBdn1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAssentiBdn1(Integer value) {
        this.capiAssentiBdn1 = value;
    }

    /**
     * Recupera il valore della proprieta capiAssentiBdn2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAssentiBdn2() {
        return capiAssentiBdn2;
    }

    /**
     * Imposta il valore della proprieta capiAssentiBdn2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAssentiBdn2(Integer value) {
        this.capiAssentiBdn2 = value;
    }

    /**
     * Recupera il valore della proprieta capiAssentiBdn3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAssentiBdn3() {
        return capiAssentiBdn3;
    }

    /**
     * Imposta il valore della proprieta capiAssentiBdn3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAssentiBdn3(Integer value) {
        this.capiAssentiBdn3 = value;
    }

    /**
     * Recupera il valore della proprieta capiAssentiBdn4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiAssentiBdn4() {
        return capiAssentiBdn4;
    }

    /**
     * Imposta il valore della proprieta capiAssentiBdn4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiAssentiBdn4(Integer value) {
        this.capiAssentiBdn4 = value;
    }

    /**
     * Recupera il valore della proprieta capiControllati1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiControllati1() {
        return capiControllati1;
    }

    /**
     * Imposta il valore della proprieta capiControllati1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiControllati1(Integer value) {
        this.capiControllati1 = value;
    }

    /**
     * Recupera il valore della proprieta capiControllati2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiControllati2() {
        return capiControllati2;
    }

    /**
     * Imposta il valore della proprieta capiControllati2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiControllati2(Integer value) {
        this.capiControllati2 = value;
    }

    /**
     * Recupera il valore della proprieta capiControllati3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiControllati3() {
        return capiControllati3;
    }

    /**
     * Imposta il valore della proprieta capiControllati3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiControllati3(Integer value) {
        this.capiControllati3 = value;
    }

    /**
     * Recupera il valore della proprieta capiControllati4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiControllati4() {
        return capiControllati4;
    }

    /**
     * Imposta il valore della proprieta capiControllati4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiControllati4(Integer value) {
        this.capiControllati4 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent1() {
        return capiIrregIdent1;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent1(Integer value) {
        this.capiIrregIdent1 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent2() {
        return capiIrregIdent2;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent2(Integer value) {
        this.capiIrregIdent2 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent20071.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent20071() {
        return capiIrregIdent20071;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent20071.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent20071(Integer value) {
        this.capiIrregIdent20071 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent20072.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent20072() {
        return capiIrregIdent20072;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent20072.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent20072(Integer value) {
        this.capiIrregIdent20072 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent20073.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent20073() {
        return capiIrregIdent20073;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent20073.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent20073(Integer value) {
        this.capiIrregIdent20073 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent20074.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent20074() {
        return capiIrregIdent20074;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent20074.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent20074(Integer value) {
        this.capiIrregIdent20074 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent3() {
        return capiIrregIdent3;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent3(Integer value) {
        this.capiIrregIdent3 = value;
    }

    /**
     * Recupera il valore della proprieta capiIrregIdent4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiIrregIdent4() {
        return capiIrregIdent4;
    }

    /**
     * Imposta il valore della proprieta capiIrregIdent4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiIrregIdent4(Integer value) {
        this.capiIrregIdent4 = value;
    }

    /**
     * Recupera il valore della proprieta capiMicrochipIlleg1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiMicrochipIlleg1() {
        return capiMicrochipIlleg1;
    }

    /**
     * Imposta il valore della proprieta capiMicrochipIlleg1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiMicrochipIlleg1(Integer value) {
        this.capiMicrochipIlleg1 = value;
    }

    /**
     * Recupera il valore della proprieta capiMicrochipIlleg2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiMicrochipIlleg2() {
        return capiMicrochipIlleg2;
    }

    /**
     * Imposta il valore della proprieta capiMicrochipIlleg2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiMicrochipIlleg2(Integer value) {
        this.capiMicrochipIlleg2 = value;
    }

    /**
     * Recupera il valore della proprieta capiMicrochipIlleg3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiMicrochipIlleg3() {
        return capiMicrochipIlleg3;
    }

    /**
     * Imposta il valore della proprieta capiMicrochipIlleg3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiMicrochipIlleg3(Integer value) {
        this.capiMicrochipIlleg3 = value;
    }

    /**
     * Recupera il valore della proprieta capiMicrochipIlleg4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiMicrochipIlleg4() {
        return capiMicrochipIlleg4;
    }

    /**
     * Imposta il valore della proprieta capiMicrochipIlleg4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiMicrochipIlleg4(Integer value) {
        this.capiMicrochipIlleg4 = value;
    }

    /**
     * Recupera il valore della proprieta capiNonIdent1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiNonIdent1() {
        return capiNonIdent1;
    }

    /**
     * Imposta il valore della proprieta capiNonIdent1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiNonIdent1(Integer value) {
        this.capiNonIdent1 = value;
    }

    /**
     * Recupera il valore della proprieta capiNonIdent2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiNonIdent2() {
        return capiNonIdent2;
    }

    /**
     * Imposta il valore della proprieta capiNonIdent2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiNonIdent2(Integer value) {
        this.capiNonIdent2 = value;
    }

    /**
     * Recupera il valore della proprieta capiNonIdent3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiNonIdent3() {
        return capiNonIdent3;
    }

    /**
     * Imposta il valore della proprieta capiNonIdent3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiNonIdent3(Integer value) {
        this.capiNonIdent3 = value;
    }

    /**
     * Recupera il valore della proprieta capiNonIdent4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiNonIdent4() {
        return capiNonIdent4;
    }

    /**
     * Imposta il valore della proprieta capiNonIdent4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiNonIdent4(Integer value) {
        this.capiNonIdent4 = value;
    }

    /**
     * Recupera il valore della proprieta capiPresenti1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPresenti1() {
        return capiPresenti1;
    }

    /**
     * Imposta il valore della proprieta capiPresenti1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPresenti1(Integer value) {
        this.capiPresenti1 = value;
    }

    /**
     * Recupera il valore della proprieta capiPresenti2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPresenti2() {
        return capiPresenti2;
    }

    /**
     * Imposta il valore della proprieta capiPresenti2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPresenti2(Integer value) {
        this.capiPresenti2 = value;
    }

    /**
     * Recupera il valore della proprieta capiPresenti3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPresenti3() {
        return capiPresenti3;
    }

    /**
     * Imposta il valore della proprieta capiPresenti3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPresenti3(Integer value) {
        this.capiPresenti3 = value;
    }

    /**
     * Recupera il valore della proprieta capiPresenti4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPresenti4() {
        return capiPresenti4;
    }

    /**
     * Imposta il valore della proprieta capiPresenti4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPresenti4(Integer value) {
        this.capiPresenti4 = value;
    }

    /**
     * Recupera il valore della proprieta capiPriviPassaporto1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPriviPassaporto1() {
        return capiPriviPassaporto1;
    }

    /**
     * Imposta il valore della proprieta capiPriviPassaporto1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPriviPassaporto1(Integer value) {
        this.capiPriviPassaporto1 = value;
    }

    /**
     * Recupera il valore della proprieta capiPriviPassaporto2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPriviPassaporto2() {
        return capiPriviPassaporto2;
    }

    /**
     * Imposta il valore della proprieta capiPriviPassaporto2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPriviPassaporto2(Integer value) {
        this.capiPriviPassaporto2 = value;
    }

    /**
     * Recupera il valore della proprieta capiPriviPassaporto3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPriviPassaporto3() {
        return capiPriviPassaporto3;
    }

    /**
     * Imposta il valore della proprieta capiPriviPassaporto3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPriviPassaporto3(Integer value) {
        this.capiPriviPassaporto3 = value;
    }

    /**
     * Recupera il valore della proprieta capiPriviPassaporto4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapiPriviPassaporto4() {
        return capiPriviPassaporto4;
    }

    /**
     * Imposta il valore della proprieta capiPriviPassaporto4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapiPriviPassaporto4(Integer value) {
        this.capiPriviPassaporto4 = value;
    }

    /**
     * Recupera il valore della proprieta cldId1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCldId1() {
        return cldId1;
    }

    /**
     * Imposta il valore della proprieta cldId1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCldId1(Integer value) {
        this.cldId1 = value;
    }

    /**
     * Recupera il valore della proprieta cldId2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCldId2() {
        return cldId2;
    }

    /**
     * Imposta il valore della proprieta cldId2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCldId2(Integer value) {
        this.cldId2 = value;
    }

    /**
     * Recupera il valore della proprieta cldId3.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCldId3() {
        return cldId3;
    }

    /**
     * Imposta il valore della proprieta cldId3.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCldId3(Integer value) {
        this.cldId3 = value;
    }

    /**
     * Recupera il valore della proprieta cldId4.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCldId4() {
        return cldId4;
    }

    /**
     * Imposta il valore della proprieta cldId4.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCldId4(Integer value) {
        this.cldId4 = value;
    }

    /**
     * Recupera il valore della proprieta clspeCodice1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClspeCodice1() {
        return clspeCodice1;
    }

    /**
     * Imposta il valore della proprieta clspeCodice1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClspeCodice1(String value) {
        this.clspeCodice1 = value;
    }

    /**
     * Recupera il valore della proprieta clspeCodice2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClspeCodice2() {
        return clspeCodice2;
    }

    /**
     * Imposta il valore della proprieta clspeCodice2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClspeCodice2(String value) {
        this.clspeCodice2 = value;
    }

    /**
     * Recupera il valore della proprieta clspeCodice3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClspeCodice3() {
        return clspeCodice3;
    }

    /**
     * Imposta il valore della proprieta clspeCodice3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClspeCodice3(String value) {
        this.clspeCodice3 = value;
    }

    /**
     * Recupera il valore della proprieta clspeCodice4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClspeCodice4() {
        return clspeCodice4;
    }

    /**
     * Imposta il valore della proprieta clspeCodice4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClspeCodice4(String value) {
        this.clspeCodice4 = value;
    }

}
