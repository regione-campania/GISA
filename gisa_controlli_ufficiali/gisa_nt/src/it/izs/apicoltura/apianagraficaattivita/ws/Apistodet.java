/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaattivita.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

 
/**
 * <p>Classe Java per apistodet complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apistodet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apianagrafica.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apistodetId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detenCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtAssegnazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtRevoca" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiApiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiApiattDtCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apiApiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apistodet", propOrder = {
    "apistodetId",
    "detenIdFiscale",
    "detenCognNome",
    "dtAssegnazione",
    "dtRevoca",
    "apiProgressivo",
    "apiApiattAziendaCodice",
    "apiApiattDtCessazione",
    "apiApiattDenominazione"
})
public class Apistodet
    extends Entity
{

    protected Integer apistodetId;
    protected String detenIdFiscale;
    protected String detenCognNome;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtAssegnazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtRevoca;
    protected Integer apiProgressivo;
    protected String apiApiattAziendaCodice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar apiApiattDtCessazione;
    protected String apiApiattDenominazione;

    /**
     * Recupera il valore della proprieta apistodetId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApistodetId() {
        return apistodetId;
    }

    /**
     * Imposta il valore della proprieta apistodetId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApistodetId(Integer value) {
        this.apistodetId = value;
    }

    /**
     * Recupera il valore della proprieta detenIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetenIdFiscale() {
        return detenIdFiscale;
    }

    /**
     * Imposta il valore della proprieta detenIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetenIdFiscale(String value) {
        this.detenIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta detenCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetenCognNome() {
        return detenCognNome;
    }

    /**
     * Imposta il valore della proprieta detenCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetenCognNome(String value) {
        this.detenCognNome = value;
    }

    /**
     * Recupera il valore della proprieta dtAssegnazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtAssegnazione() {
        return dtAssegnazione;
    }

    /**
     * Imposta il valore della proprieta dtAssegnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtAssegnazione(XMLGregorianCalendar value) {
        this.dtAssegnazione = value;
    }

    /**
     * Recupera il valore della proprieta dtRevoca.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtRevoca() {
        return dtRevoca;
    }

    /**
     * Imposta il valore della proprieta dtRevoca.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtRevoca(XMLGregorianCalendar value) {
        this.dtRevoca = value;
    }

    /**
     * Recupera il valore della proprieta apiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiProgressivo() {
        return apiProgressivo;
    }

    /**
     * Imposta il valore della proprieta apiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiProgressivo(Integer value) {
        this.apiProgressivo = value;
    }

    /**
     * Recupera il valore della proprieta apiApiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiApiattAziendaCodice() {
        return apiApiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta apiApiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiApiattAziendaCodice(String value) {
        this.apiApiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta apiApiattDtCessazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApiApiattDtCessazione() {
        return apiApiattDtCessazione;
    }

    /**
     * Imposta il valore della proprieta apiApiattDtCessazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApiApiattDtCessazione(XMLGregorianCalendar value) {
        this.apiApiattDtCessazione = value;
    }

    /**
     * Recupera il valore della proprieta apiApiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiApiattDenominazione() {
        return apiApiattDenominazione;
    }

    /**
     * Imposta il valore della proprieta apiApiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiApiattDenominazione(String value) {
        this.apiApiattDenominazione = value;
    }

}
