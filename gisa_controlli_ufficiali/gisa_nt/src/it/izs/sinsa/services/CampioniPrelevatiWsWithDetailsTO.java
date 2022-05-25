/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.sinsa.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per campioniPrelevatiWsWithDetailsTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="campioniPrelevatiWsWithDetailsTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altreInformazioni">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="altreInformazioniStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizioneAggiuntiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="materialeCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="materialeDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numUnitaCampionarie" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="progressivoCampione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="provenAllevAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenAllevSpecie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenAltro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenSpiCun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenSpiIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provenSpiRagioneSociale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specieCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specieDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="temperaturaAccettazione" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campioniPrelevatiWsWithDetailsTO", propOrder = {
    "altreInformazioni",
    "altreInformazioniStr",
    "descrizioneAggiuntiva",
    "materialeCodice",
    "materialeDescrizione",
    "numUnitaCampionarie",
    "progressivoCampione",
    "provenAllevAziendaCodice",
    "provenAllevSpecie",
    "provenAltro",
    "provenIdFiscale",
    "provenSpiCun",
    "provenSpiIndirizzo",
    "provenSpiRagioneSociale",
    "specieCodice",
    "specieDescrizione",
    "temperaturaAccettazione"
})
public class CampioniPrelevatiWsWithDetailsTO {

    @XmlElement(required = true)
    protected CampioniPrelevatiWsWithDetailsTO.AltreInformazioni altreInformazioni;
    protected String altreInformazioniStr;
    protected String descrizioneAggiuntiva;
    protected String materialeCodice;
    protected String materialeDescrizione;
    protected Integer numUnitaCampionarie;
    protected Integer progressivoCampione;
    protected String provenAllevAziendaCodice;
    protected String provenAllevSpecie;
    protected String provenAltro;
    protected String provenIdFiscale;
    protected String provenSpiCun;
    protected String provenSpiIndirizzo;
    protected String provenSpiRagioneSociale;
    protected String specieCodice;
    protected String specieDescrizione;
    protected Float temperaturaAccettazione;

    /**
     * Recupera il valore della proprieta altreInformazioni.
     * 
     * @return
     *     possible object is
     *     {@link CampioniPrelevatiWsWithDetailsTO.AltreInformazioni }
     *     
     */
    public CampioniPrelevatiWsWithDetailsTO.AltreInformazioni getAltreInformazioni() {
        return altreInformazioni;
    }

    /**
     * Imposta il valore della proprieta altreInformazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link CampioniPrelevatiWsWithDetailsTO.AltreInformazioni }
     *     
     */
    public void setAltreInformazioni(CampioniPrelevatiWsWithDetailsTO.AltreInformazioni value) {
        this.altreInformazioni = value;
    }

    /**
     * Recupera il valore della proprieta altreInformazioniStr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInformazioniStr() {
        return altreInformazioniStr;
    }

    /**
     * Imposta il valore della proprieta altreInformazioniStr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInformazioniStr(String value) {
        this.altreInformazioniStr = value;
    }

    /**
     * Recupera il valore della proprieta descrizioneAggiuntiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAggiuntiva() {
        return descrizioneAggiuntiva;
    }

    /**
     * Imposta il valore della proprieta descrizioneAggiuntiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAggiuntiva(String value) {
        this.descrizioneAggiuntiva = value;
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
     * Recupera il valore della proprieta materialeDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialeDescrizione() {
        return materialeDescrizione;
    }

    /**
     * Imposta il valore della proprieta materialeDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialeDescrizione(String value) {
        this.materialeDescrizione = value;
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
     * Recupera il valore della proprieta provenAllevAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenAllevAziendaCodice() {
        return provenAllevAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta provenAllevAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenAllevAziendaCodice(String value) {
        this.provenAllevAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta provenAllevSpecie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenAllevSpecie() {
        return provenAllevSpecie;
    }

    /**
     * Imposta il valore della proprieta provenAllevSpecie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenAllevSpecie(String value) {
        this.provenAllevSpecie = value;
    }

    /**
     * Recupera il valore della proprieta provenAltro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenAltro() {
        return provenAltro;
    }

    /**
     * Imposta il valore della proprieta provenAltro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenAltro(String value) {
        this.provenAltro = value;
    }

    /**
     * Recupera il valore della proprieta provenIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenIdFiscale() {
        return provenIdFiscale;
    }

    /**
     * Imposta il valore della proprieta provenIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenIdFiscale(String value) {
        this.provenIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta provenSpiCun.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenSpiCun() {
        return provenSpiCun;
    }

    /**
     * Imposta il valore della proprieta provenSpiCun.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenSpiCun(String value) {
        this.provenSpiCun = value;
    }

    /**
     * Recupera il valore della proprieta provenSpiIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenSpiIndirizzo() {
        return provenSpiIndirizzo;
    }

    /**
     * Imposta il valore della proprieta provenSpiIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenSpiIndirizzo(String value) {
        this.provenSpiIndirizzo = value;
    }

    /**
     * Recupera il valore della proprieta provenSpiRagioneSociale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenSpiRagioneSociale() {
        return provenSpiRagioneSociale;
    }

    /**
     * Imposta il valore della proprieta provenSpiRagioneSociale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenSpiRagioneSociale(String value) {
        this.provenSpiRagioneSociale = value;
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
     * Recupera il valore della proprieta specieDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecieDescrizione() {
        return specieDescrizione;
    }

    /**
     * Imposta il valore della proprieta specieDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecieDescrizione(String value) {
        this.specieDescrizione = value;
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
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class AltreInformazioni {

        protected List<CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry }
         * 
         * 
         */
        public List<CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected String key;
            protected String value;

            /**
             * Recupera il valore della proprieta key.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Imposta il valore della proprieta key.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Recupera il valore della proprieta value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Imposta il valore della proprieta value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}
