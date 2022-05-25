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
 * <p>Classe Java per campionePrelevatoWsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="campionePrelevatoWsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altreInformazioni" type="{http://sinsa.izs.it/services}prelievoAltreInformazioniWs" minOccurs="0"/>
 *         &lt;element name="altreInformazioniXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="campioneId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="confezionamentoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foodexCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idAltreInformazioni" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mangimeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="materialeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numAliquote" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numUnitaCampionarie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="progressivoCampione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="provenienzaAltraStruttura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaSNCProvCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaSNCStatoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaSpiNumRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaSpiOrienProduttivoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaSpiTipoAllevamentoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenienzaTipoImpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specieCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="temperaturaAccettazione" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="trattamentoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campionePrelevatoWsTO", propOrder = {
    "altreInformazioni",
    "altreInformazioniXml",
    "campioneId",
    "confezionamentoCodice",
    "foodexCodice",
    "idAltreInformazioni",
    "mangimeCodice",
    "materialeCodice",
    "numAliquote",
    "numUnitaCampionarie",
    "progressivoCampione",
    "provenienzaAltraStruttura",
    "provenienzaSNCProvCodice",
    "provenienzaSNCStatoCodice",
    "provenienzaSpiNumRegistrazione",
    "provenienzaSpiOrienProduttivoCodice",
    "provenienzaSpiTipoAllevamentoCodice",
    "provenienzaTipoImpresa",
    "specieCodice",
    "temperaturaAccettazione",
    "trattamentoCodice"
})
public class CampionePrelevatoWsTO {

    protected PrelievoAltreInformazioniWs altreInformazioni;
    protected String altreInformazioniXml;
    protected Integer campioneId;
    protected String confezionamentoCodice;
    protected String foodexCodice;
    protected Integer idAltreInformazioni;
    protected String mangimeCodice;
    protected String materialeCodice;
    protected Integer numAliquote;
    protected Integer numUnitaCampionarie;
    protected Integer progressivoCampione;
    protected String provenienzaAltraStruttura;
    protected String provenienzaSNCProvCodice;
    protected String provenienzaSNCStatoCodice;
    protected String provenienzaSpiNumRegistrazione;
    protected String provenienzaSpiOrienProduttivoCodice;
    protected String provenienzaSpiTipoAllevamentoCodice;
    protected String provenienzaTipoImpresa;
    protected String specieCodice;
    protected Float temperaturaAccettazione;
    protected String trattamentoCodice;

    /**
     * Recupera il valore della proprieta altreInformazioni.
     * 
     * @return
     *     possible object is
     *     {@link PrelievoAltreInformazioniWs }
     *     
     */
    public PrelievoAltreInformazioniWs getAltreInformazioni() {
        return altreInformazioni;
    }

    /**
     * Imposta il valore della proprieta altreInformazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link PrelievoAltreInformazioniWs }
     *     
     */
    public void setAltreInformazioni(PrelievoAltreInformazioniWs value) {
        this.altreInformazioni = value;
    }

    /**
     * Recupera il valore della proprieta altreInformazioniXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInformazioniXml() {
        return altreInformazioniXml;
    }

    /**
     * Imposta il valore della proprieta altreInformazioniXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInformazioniXml(String value) {
        this.altreInformazioniXml = value;
    }

    /**
     * Recupera il valore della proprieta campioneId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCampioneId() {
        return campioneId;
    }

    /**
     * Imposta il valore della proprieta campioneId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCampioneId(Integer value) {
        this.campioneId = value;
    }

    /**
     * Recupera il valore della proprieta confezionamentoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfezionamentoCodice() {
        return confezionamentoCodice;
    }

    /**
     * Imposta il valore della proprieta confezionamentoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfezionamentoCodice(String value) {
        this.confezionamentoCodice = value;
    }

    /**
     * Recupera il valore della proprieta foodexCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoodexCodice() {
        return foodexCodice;
    }

    /**
     * Imposta il valore della proprieta foodexCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoodexCodice(String value) {
        this.foodexCodice = value;
    }

    /**
     * Recupera il valore della proprieta idAltreInformazioni.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdAltreInformazioni() {
        return idAltreInformazioni;
    }

    /**
     * Imposta il valore della proprieta idAltreInformazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdAltreInformazioni(Integer value) {
        this.idAltreInformazioni = value;
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
     * Recupera il valore della proprieta numAliquote.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAliquote() {
        return numAliquote;
    }

    /**
     * Imposta il valore della proprieta numAliquote.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAliquote(Integer value) {
        this.numAliquote = value;
    }

    /**
     * Recupera il valore della proprieta numUnitaCampionarie.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumUnitaCampionarie() {
        return numUnitaCampionarie;
    }

    /**
     * Imposta il valore della proprieta numUnitaCampionarie.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumUnitaCampionarie(Integer value) {
        this.numUnitaCampionarie = value;
    }

    /**
     * Recupera il valore della proprieta progressivoCampione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProgressivoCampione() {
        return progressivoCampione;
    }

    /**
     * Imposta il valore della proprieta progressivoCampione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProgressivoCampione(Integer value) {
        this.progressivoCampione = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaAltraStruttura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaAltraStruttura() {
        return provenienzaAltraStruttura;
    }

    /**
     * Imposta il valore della proprieta provenienzaAltraStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaAltraStruttura(String value) {
        this.provenienzaAltraStruttura = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaSNCProvCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaSNCProvCodice() {
        return provenienzaSNCProvCodice;
    }

    /**
     * Imposta il valore della proprieta provenienzaSNCProvCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaSNCProvCodice(String value) {
        this.provenienzaSNCProvCodice = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaSNCStatoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaSNCStatoCodice() {
        return provenienzaSNCStatoCodice;
    }

    /**
     * Imposta il valore della proprieta provenienzaSNCStatoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaSNCStatoCodice(String value) {
        this.provenienzaSNCStatoCodice = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaSpiNumRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaSpiNumRegistrazione() {
        return provenienzaSpiNumRegistrazione;
    }

    /**
     * Imposta il valore della proprieta provenienzaSpiNumRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaSpiNumRegistrazione(String value) {
        this.provenienzaSpiNumRegistrazione = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaSpiOrienProduttivoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaSpiOrienProduttivoCodice() {
        return provenienzaSpiOrienProduttivoCodice;
    }

    /**
     * Imposta il valore della proprieta provenienzaSpiOrienProduttivoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaSpiOrienProduttivoCodice(String value) {
        this.provenienzaSpiOrienProduttivoCodice = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaSpiTipoAllevamentoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaSpiTipoAllevamentoCodice() {
        return provenienzaSpiTipoAllevamentoCodice;
    }

    /**
     * Imposta il valore della proprieta provenienzaSpiTipoAllevamentoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaSpiTipoAllevamentoCodice(String value) {
        this.provenienzaSpiTipoAllevamentoCodice = value;
    }

    /**
     * Recupera il valore della proprieta provenienzaTipoImpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenienzaTipoImpresa() {
        return provenienzaTipoImpresa;
    }

    /**
     * Imposta il valore della proprieta provenienzaTipoImpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenienzaTipoImpresa(String value) {
        this.provenienzaTipoImpresa = value;
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

    /**
     * Recupera il valore della proprieta temperaturaAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTemperaturaAccettazione() {
        return temperaturaAccettazione;
    }

    /**
     * Imposta il valore della proprieta temperaturaAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTemperaturaAccettazione(Float value) {
        this.temperaturaAccettazione = value;
    }

    /**
     * Recupera il valore della proprieta trattamentoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrattamentoCodice() {
        return trattamentoCodice;
    }

    /**
     * Imposta il valore della proprieta trattamentoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrattamentoCodice(String value) {
        this.trattamentoCodice = value;
    }

}
