/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//
// Questo file e stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra persa durante la ricompilazione dello schema di origine. 
// Generato il: 2016.02.10 alle 03:34:58 PM CET 
//


package org.aspcfs.modules.izsmibr.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
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
 *                   &lt;element name="P_EIBR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="P_CODICE_ISTITUTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="P_CODICE_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="P_CODICE_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="P_ANNO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="P_NUMERO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="P_CODICE_AZIENDA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="P_ID_FISCALE_PROPRIETARIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="P_SPECIE_ALLEVATA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="P_CODICE_CAPO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="P_DATA_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="P_DATA_ESITO" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="P_ESITO_QUALITATIVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {
    "parameterslist"
})
@XmlRootElement(name = "dsESITO_IBR_IUS")

public class DsESITOIBRIUS {

    @XmlElement(name = "PARAMETERS_LIST")
    protected List<DsESITOIBRIUS.PARAMETERSLIST> parameterslist;

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
     * {@link DsESITOIBRIUS.PARAMETERSLIST }
     * 
     * 
     */
    public List<DsESITOIBRIUS.PARAMETERSLIST> getPARAMETERSLIST() {
        if (parameterslist == null) {
            parameterslist = new ArrayList<DsESITOIBRIUS.PARAMETERSLIST>();
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
     *         &lt;element name="P_EIBR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="P_CODICE_ISTITUTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="P_CODICE_SEDE_DIAGNOSTICA" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="P_CODICE_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="P_ANNO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="P_NUMERO_ACCETTAZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="P_CODICE_AZIENDA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="P_ID_FISCALE_PROPRIETARIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="P_SPECIE_ALLEVATA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="P_CODICE_CAPO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="P_DATA_PRELIEVO" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="P_DATA_ESITO" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="P_ESITO_QUALITATIVO" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    public static class PARAMETERSLIST {

        @XmlElement(name = "P_EIBR_ID", required = true)
        protected BigDecimal peibrid;
        @XmlElement(name = "P_CODICE_ISTITUTO", required = true)
        protected String pcodiceistituto;
        @XmlElement(name = "P_CODICE_SEDE_DIAGNOSTICA", required = true)
        protected String pcodicesedediagnostica;
        @XmlElement(name = "P_CODICE_PRELIEVO")
        protected String pcodiceprelievo;
        @XmlElement(name = "P_ANNO_ACCETTAZIONE", required = true)
        protected BigDecimal pannoaccettazione;
        @XmlElement(name = "P_ANNO_CAMPAGNA", required = true)
        protected BigDecimal pannocampagna;
        @XmlElement(name = "P_NUMERO_ACCETTAZIONE", required = true)
        protected String pnumeroaccettazione;
        @XmlElement(name = "P_CODICE_AZIENDA")
        protected String pcodiceazienda;
        @XmlElement(name = "P_ID_FISCALE_PROPRIETARIO")
        protected String pidfiscaleproprietario;
        @XmlElement(name = "P_SPECIE_ALLEVATA")
        protected String pspecieallevata;
        @XmlElement(name = "P_CODICE_CAPO", required = true)
        protected String pcodicecapo;
        @XmlElement(name = "P_DATA_PRELIEVO", required = true)
        @XmlSchemaType(name = "date")
        protected String pdataprelievo;
        @XmlElement(name = "P_DATA_ESITO", required = true)
        @XmlSchemaType(name = "date")
        protected String pdataesito;
        @XmlElement(name = "P_ESITO_QUALITATIVO", required = true)
        protected String pesitoqualitativo;

        /**
         * Recupera il valore della proprieta peibrid.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPEIBRID() {
            return peibrid;
        }

        /**
         * Imposta il valore della proprieta peibrid.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPEIBRID(BigDecimal value) {
            this.peibrid = value;
        }

        /**
         * Recupera il valore della proprieta pcodiceistituto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPCODICEISTITUTO() {
            return pcodiceistituto;
        }

        /**
         * Imposta il valore della proprieta pcodiceistituto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPCODICEISTITUTO(String value) {
            this.pcodiceistituto = value;
        }

        /**
         * Recupera il valore della proprieta pcodicesedediagnostica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPCODICESEDEDIAGNOSTICA() {
            return pcodicesedediagnostica;
        }

        /**
         * Imposta il valore della proprieta pcodicesedediagnostica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPCODICESEDEDIAGNOSTICA(String value) {
            this.pcodicesedediagnostica = value;
        }

        /**
         * Recupera il valore della proprieta pcodiceprelievo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPCODICEPRELIEVO() {
            return pcodiceprelievo;
        }

        /**
         * Imposta il valore della proprieta pcodiceprelievo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPCODICEPRELIEVO(String value) {
            this.pcodiceprelievo = value;
        }

        /**
         * Recupera il valore della proprieta pannoaccettazione.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPANNOACCETTAZIONE() {
            return pannoaccettazione;
        }
        
        public BigDecimal getPANNOCAMPAGNA() {
            return pannocampagna;
        }

        /**
         * Imposta il valore della proprieta pannoaccettazione.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPANNOACCETTAZIONE(BigDecimal value) {
            this.pannoaccettazione = value;
        }
        
        public void setPANNOCAMPAGNA(BigDecimal value) {
            this.pannocampagna = value;
        }


        /**
         * Recupera il valore della proprieta pnumeroaccettazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPNUMEROACCETTAZIONE() {
            return pnumeroaccettazione;
        }

        /**
         * Imposta il valore della proprieta pnumeroaccettazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPNUMEROACCETTAZIONE(String value) {
            this.pnumeroaccettazione = value;
        }

        /**
         * Recupera il valore della proprieta pcodiceazienda.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPCODICEAZIENDA() {
            return pcodiceazienda;
        }

        /**
         * Imposta il valore della proprieta pcodiceazienda.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPCODICEAZIENDA(String value) {
            this.pcodiceazienda = value;
        }

        /**
         * Recupera il valore della proprieta pidfiscaleproprietario.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPIDFISCALEPROPRIETARIO() {
            return pidfiscaleproprietario;
        }

        /**
         * Imposta il valore della proprieta pidfiscaleproprietario.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPIDFISCALEPROPRIETARIO(String value) {
            this.pidfiscaleproprietario = value;
        }

        /**
         * Recupera il valore della proprieta pspecieallevata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPSPECIEALLEVATA() {
            return pspecieallevata;
        }

        /**
         * Imposta il valore della proprieta pspecieallevata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPSPECIEALLEVATA(String value) {
            this.pspecieallevata = value;
        }

        /**
         * Recupera il valore della proprieta pcodicecapo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPCODICECAPO() {
            return pcodicecapo;
        }

        /**
         * Imposta il valore della proprieta pcodicecapo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPCODICECAPO(String value) {
            this.pcodicecapo = value;
        }

        /**
         * Recupera il valore della proprieta pdataprelievo.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getPDATAPRELIEVO() {
        	
            return pdataprelievo;
        }

        /**
         * Imposta il valore della proprieta pdataprelievo.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setPDATAPRELIEVO(String value) {
            this.pdataprelievo = value;
        }

        /**
         * Recupera il valore della proprieta pdataesito.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getPDATAESITO() {
            return pdataesito;
        }

        /**
         * Imposta il valore della proprieta pdataesito.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setPDATAESITO(String value) {
            this.pdataesito = value;
        }

        /**
         * Recupera il valore della proprieta pesitoqualitativo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPESITOQUALITATIVO() {
            return pesitoqualitativo;
        }

        /**
         * Imposta il valore della proprieta pesitoqualitativo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPESITOQUALITATIVO(String value) {
            this.pesitoqualitativo = value;
        }

    }
    
    
    
    
   

}
