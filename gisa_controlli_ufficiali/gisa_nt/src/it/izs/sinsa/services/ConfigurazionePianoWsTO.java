/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.sinsa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per configurazionePianoWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="configurazionePianoWsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="campioneDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoriaDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foodex2Codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mangimeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="materialeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduloCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduloDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pianoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specieCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configurazionePianoWsTO", propOrder = {
    "campioneDescrizione",
    "categoria",
    "categoriaDescrizione",
    "foodex2Codice",
    "mangimeCodice",
    "materialeCodice",
    "moduloCodice",
    "moduloDescrizione",
    "pianoCodice",
    "specieCodice"
})
public class ConfigurazionePianoWsTO {

    protected String campioneDescrizione;
    protected String categoria;
    protected String categoriaDescrizione;
    protected String foodex2Codice;
    protected String mangimeCodice;
    protected String materialeCodice;
    protected String moduloCodice;
    protected String moduloDescrizione;
    protected String pianoCodice;
    protected String specieCodice;

    /**
     * Recupera il valore della proprieta campioneDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampioneDescrizione() {
        return campioneDescrizione;
    }

    /**
     * Imposta il valore della proprieta campioneDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampioneDescrizione(String value) {
        this.campioneDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta categoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della proprieta categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

    /**
     * Recupera il valore della proprieta categoriaDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    /**
     * Imposta il valore della proprieta categoriaDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaDescrizione(String value) {
        this.categoriaDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta foodex2Codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoodex2Codice() {
        return foodex2Codice;
    }

    /**
     * Imposta il valore della proprieta foodex2Codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoodex2Codice(String value) {
        this.foodex2Codice = value;
    }

    /**
     * Recupera il valore della proprieta mangimeCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMangimeCodice() {
        return mangimeCodice;
    }

    /**
     * Imposta il valore della proprieta mangimeCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMangimeCodice(String value) {
        this.mangimeCodice = value;
    }

    /**
     * Recupera il valore della proprieta materialeCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialeCodice() {
        return materialeCodice;
    }

    /**
     * Imposta il valore della proprieta materialeCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialeCodice(String value) {
        this.materialeCodice = value;
    }

    /**
     * Recupera il valore della proprieta moduloCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuloCodice() {
        return moduloCodice;
    }

    /**
     * Imposta il valore della proprieta moduloCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuloCodice(String value) {
        this.moduloCodice = value;
    }

    /**
     * Recupera il valore della proprieta moduloDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuloDescrizione() {
        return moduloDescrizione;
    }

    /**
     * Imposta il valore della proprieta moduloDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuloDescrizione(String value) {
        this.moduloDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta pianoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPianoCodice() {
        return pianoCodice;
    }

    /**
     * Imposta il valore della proprieta pianoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPianoCodice(String value) {
        this.pianoCodice = value;
    }

    /**
     * Recupera il valore della proprieta specieCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecieCodice() {
        return specieCodice;
    }

    /**
     * Imposta il valore della proprieta specieCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecieCodice(String value) {
        this.specieCodice = value;
    }

}
