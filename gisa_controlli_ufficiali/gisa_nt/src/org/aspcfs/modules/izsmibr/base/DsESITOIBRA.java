/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//
// Questo file e stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra persa durante la ricompilazione dello schema di origine. 
// Generato il: 2016.02.11 alle 04:21:30 PM CET 
//


package org.aspcfs.modules.izsmibr.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="PARAMETERS_LIST">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="EIBR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="ISTITUTO_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="CODICE_ISTITUTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CODICE_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DENOM_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CODICE_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ANNO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="NUMERO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ALLEV_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="CODICE_AZIENDA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ID_FISCALE_PROPRIETARIO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="SPECIE_ALLEVATA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CAPO_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="CODICE_CAPO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DATA_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="DATA_ESITO" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="ESITO_QUALITATIVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parameterslist"
})
@XmlRootElement(name = "dsESITO_IBR_A")
public class DsESITOIBRA {

    @XmlElement(name = "PARAMETERS_LIST")
    protected List<DsESITOIBRA.PARAMETERSLIST> parameterslist;

    /**
     * Gets the value of the parameterslist property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterslist property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPARAMETERSLIST().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DsESITOIBRA.PARAMETERSLIST }
     * 
     * 
     */
    public List<DsESITOIBRA.PARAMETERSLIST> getPARAMETERSLIST() {
        if (parameterslist == null) {
            parameterslist = new ArrayList<DsESITOIBRA.PARAMETERSLIST>();
        }
        return this.parameterslist;
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
     *       &lt;all>
     *         &lt;element name="EIBR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="ISTITUTO_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="CODICE_ISTITUTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CODICE_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DENOM_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CODICE_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ANNO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="NUMERO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ALLEV_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="CODICE_AZIENDA" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ID_FISCALE_PROPRIETARIO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="SPECIE_ALLEVATA" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CAPO_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="CODICE_CAPO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DATA_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="DATA_ESITO" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="ESITO_QUALITATIVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public  class PARAMETERSLIST {

        @XmlElement(name = "EIBR_ID", required = true)
        protected BigDecimal eibrid;
        @XmlElement(name = "ISTITUTO_ID", required = true)
        protected BigDecimal istitutoid;
        @XmlElement(name = "CODICE_ISTITUTO", required = true)
        protected String codiceistituto;
        @XmlElement(name = "CODICE_SEDE_DIAGNOSTICA", required = true)
        protected String codicesedediagnostica;
        @XmlElement(name = "DENOM_SEDE_DIAGNOSTICA", required = true)
        protected String denomsedediagnostica;
        @XmlElement(name = "CODICE_PRELIEVO", required = true)
        protected String codiceprelievo;
        @XmlElement(name = "ANNO_ACCETTAZIONE", required = true)
        protected BigDecimal annoaccettazione;
        @XmlElement(name = "NUMERO_ACCETTAZIONE", required = true)
        protected String numeroaccettazione;
        @XmlElement(name = "ALLEV_ID", required = true)
        protected BigDecimal allevid;
        @XmlElement(name = "CODICE_AZIENDA", required = true)
        protected String codiceazienda;
        @XmlElement(name = "ID_FISCALE_PROPRIETARIO", required = true)
        protected String idfiscaleproprietario;
        @XmlElement(name = "SPECIE_ALLEVATA", required = true)
        protected String specieallevata;
        @XmlElement(name = "CAPO_ID", required = true)
        protected BigDecimal capoid;
        @XmlElement(name = "CODICE_CAPO", required = true)
        protected String codicecapo;
        @XmlElement(name = "DATA_PRELIEVO", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataprelievo;
        @XmlElement(name = "DATA_ESITO", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataesito;
        @XmlElement(name = "ESITO_QUALITATIVO", required = true)
        protected String esitoqualitativo;

        /**
         * Recupera il valore della proprieta eibrid.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getEIBRID() {
            return eibrid;
        }

        /**
         * Imposta il valore della proprieta eibrid.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setEIBRID(BigDecimal value) {
            this.eibrid = value;
        }

        /**
         * Recupera il valore della proprieta istitutoid.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getISTITUTOID() {
            return istitutoid;
        }

        /**
         * Imposta il valore della proprieta istitutoid.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setISTITUTOID(BigDecimal value) {
            this.istitutoid = value;
        }

        /**
         * Recupera il valore della proprieta codiceistituto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODICEISTITUTO() {
            return codiceistituto;
        }

        /**
         * Imposta il valore della proprieta codiceistituto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODICEISTITUTO(String value) {
            this.codiceistituto = value;
        }

        /**
         * Recupera il valore della proprieta codicesedediagnostica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODICESEDEDIAGNOSTICA() {
            return codicesedediagnostica;
        }

        /**
         * Imposta il valore della proprieta codicesedediagnostica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODICESEDEDIAGNOSTICA(String value) {
            this.codicesedediagnostica = value;
        }

        /**
         * Recupera il valore della proprieta denomsedediagnostica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDENOMSEDEDIAGNOSTICA() {
            return denomsedediagnostica;
        }

        /**
         * Imposta il valore della proprieta denomsedediagnostica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDENOMSEDEDIAGNOSTICA(String value) {
            this.denomsedediagnostica = value;
        }

        /**
         * Recupera il valore della proprieta codiceprelievo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODICEPRELIEVO() {
            return codiceprelievo;
        }

        /**
         * Imposta il valore della proprieta codiceprelievo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODICEPRELIEVO(String value) {
            this.codiceprelievo = value;
        }

        /**
         * Recupera il valore della proprieta annoaccettazione.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getANNOACCETTAZIONE() {
            return annoaccettazione;
        }

        /**
         * Imposta il valore della proprieta annoaccettazione.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setANNOACCETTAZIONE(BigDecimal value) {
            this.annoaccettazione = value;
        }

        /**
         * Recupera il valore della proprieta numeroaccettazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMEROACCETTAZIONE() {
            return numeroaccettazione;
        }

        /**
         * Imposta il valore della proprieta numeroaccettazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMEROACCETTAZIONE(String value) {
            this.numeroaccettazione = value;
        }

        /**
         * Recupera il valore della proprieta allevid.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getALLEVID() {
            return allevid;
        }

        /**
         * Imposta il valore della proprieta allevid.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setALLEVID(BigDecimal value) {
            this.allevid = value;
        }

        /**
         * Recupera il valore della proprieta codiceazienda.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODICEAZIENDA() {
            return codiceazienda;
        }

        /**
         * Imposta il valore della proprieta codiceazienda.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODICEAZIENDA(String value) {
            this.codiceazienda = value;
        }

        /**
         * Recupera il valore della proprieta idfiscaleproprietario.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIDFISCALEPROPRIETARIO() {
            return idfiscaleproprietario;
        }

        /**
         * Imposta il valore della proprieta idfiscaleproprietario.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIDFISCALEPROPRIETARIO(String value) {
            this.idfiscaleproprietario = value;
        }

        /**
         * Recupera il valore della proprieta specieallevata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSPECIEALLEVATA() {
            return specieallevata;
        }

        /**
         * Imposta il valore della proprieta specieallevata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSPECIEALLEVATA(String value) {
            this.specieallevata = value;
        }

        /**
         * Recupera il valore della proprieta capoid.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getCAPOID() {
            return capoid;
        }

        /**
         * Imposta il valore della proprieta capoid.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setCAPOID(BigDecimal value) {
            this.capoid = value;
        }

        /**
         * Recupera il valore della proprieta codicecapo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODICECAPO() {
            return codicecapo;
        }

        /**
         * Imposta il valore della proprieta codicecapo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODICECAPO(String value) {
            this.codicecapo = value;
        }

        /**
         * Recupera il valore della proprieta dataprelievo.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDATAPRELIEVO() {
            return dataprelievo;
        }

        /**
         * Imposta il valore della proprieta dataprelievo.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDATAPRELIEVO(XMLGregorianCalendar value) {
            this.dataprelievo = value;
        }

        /**
         * Recupera il valore della proprieta dataesito.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDATAESITO() {
            return dataesito;
        }

        /**
         * Imposta il valore della proprieta dataesito.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDATAESITO(XMLGregorianCalendar value) {
            this.dataesito = value;
        }

        /**
         * Recupera il valore della proprieta esitoqualitativo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getESITOQUALITATIVO() {
            return esitoqualitativo;
        }

        /**
         * Imposta il valore della proprieta esitoqualitativo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setESITOQUALITATIVO(String value) {
            this.esitoqualitativo = value;
        }

    }

}
