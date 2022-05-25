/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAviCensimenti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAviCensimenti  implements java.io.Serializable {
    private java.lang.String p_allev_id_fiscale;

    private java.lang.String p_azienda_codice;

    private java.lang.String p_specie_codice;

    private java.lang.String p_detent_id_fiscale;

    private java.lang.String p_tipo_prod;

    private java.lang.String p_orientamento;

    private java.lang.String p_tipo_allev;

    public FindAviCensimenti() {
    }

    public FindAviCensimenti(
           java.lang.String p_allev_id_fiscale,
           java.lang.String p_azienda_codice,
           java.lang.String p_specie_codice,
           java.lang.String p_detent_id_fiscale,
           java.lang.String p_tipo_prod,
           java.lang.String p_orientamento,
           java.lang.String p_tipo_allev) {
           this.p_allev_id_fiscale = p_allev_id_fiscale;
           this.p_azienda_codice = p_azienda_codice;
           this.p_specie_codice = p_specie_codice;
           this.p_detent_id_fiscale = p_detent_id_fiscale;
           this.p_tipo_prod = p_tipo_prod;
           this.p_orientamento = p_orientamento;
           this.p_tipo_allev = p_tipo_allev;
    }


    /**
     * Gets the p_allev_id_fiscale value for this FindAviCensimenti.
     * 
     * @return p_allev_id_fiscale
     */
    public java.lang.String getP_allev_id_fiscale() {
        return p_allev_id_fiscale;
    }


    /**
     * Sets the p_allev_id_fiscale value for this FindAviCensimenti.
     * 
     * @param p_allev_id_fiscale
     */
    public void setP_allev_id_fiscale(java.lang.String p_allev_id_fiscale) {
        this.p_allev_id_fiscale = p_allev_id_fiscale;
    }


    /**
     * Gets the p_azienda_codice value for this FindAviCensimenti.
     * 
     * @return p_azienda_codice
     */
    public java.lang.String getP_azienda_codice() {
        return p_azienda_codice;
    }


    /**
     * Sets the p_azienda_codice value for this FindAviCensimenti.
     * 
     * @param p_azienda_codice
     */
    public void setP_azienda_codice(java.lang.String p_azienda_codice) {
        this.p_azienda_codice = p_azienda_codice;
    }


    /**
     * Gets the p_specie_codice value for this FindAviCensimenti.
     * 
     * @return p_specie_codice
     */
    public java.lang.String getP_specie_codice() {
        return p_specie_codice;
    }


    /**
     * Sets the p_specie_codice value for this FindAviCensimenti.
     * 
     * @param p_specie_codice
     */
    public void setP_specie_codice(java.lang.String p_specie_codice) {
        this.p_specie_codice = p_specie_codice;
    }


    /**
     * Gets the p_detent_id_fiscale value for this FindAviCensimenti.
     * 
     * @return p_detent_id_fiscale
     */
    public java.lang.String getP_detent_id_fiscale() {
        return p_detent_id_fiscale;
    }


    /**
     * Sets the p_detent_id_fiscale value for this FindAviCensimenti.
     * 
     * @param p_detent_id_fiscale
     */
    public void setP_detent_id_fiscale(java.lang.String p_detent_id_fiscale) {
        this.p_detent_id_fiscale = p_detent_id_fiscale;
    }


    /**
     * Gets the p_tipo_prod value for this FindAviCensimenti.
     * 
     * @return p_tipo_prod
     */
    public java.lang.String getP_tipo_prod() {
        return p_tipo_prod;
    }


    /**
     * Sets the p_tipo_prod value for this FindAviCensimenti.
     * 
     * @param p_tipo_prod
     */
    public void setP_tipo_prod(java.lang.String p_tipo_prod) {
        this.p_tipo_prod = p_tipo_prod;
    }


    /**
     * Gets the p_orientamento value for this FindAviCensimenti.
     * 
     * @return p_orientamento
     */
    public java.lang.String getP_orientamento() {
        return p_orientamento;
    }


    /**
     * Sets the p_orientamento value for this FindAviCensimenti.
     * 
     * @param p_orientamento
     */
    public void setP_orientamento(java.lang.String p_orientamento) {
        this.p_orientamento = p_orientamento;
    }


    /**
     * Gets the p_tipo_allev value for this FindAviCensimenti.
     * 
     * @return p_tipo_allev
     */
    public java.lang.String getP_tipo_allev() {
        return p_tipo_allev;
    }


    /**
     * Sets the p_tipo_allev value for this FindAviCensimenti.
     * 
     * @param p_tipo_allev
     */
    public void setP_tipo_allev(java.lang.String p_tipo_allev) {
        this.p_tipo_allev = p_tipo_allev;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAviCensimenti)) return false;
        FindAviCensimenti other = (FindAviCensimenti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.p_allev_id_fiscale==null && other.getP_allev_id_fiscale()==null) || 
             (this.p_allev_id_fiscale!=null &&
              this.p_allev_id_fiscale.equals(other.getP_allev_id_fiscale()))) &&
            ((this.p_azienda_codice==null && other.getP_azienda_codice()==null) || 
             (this.p_azienda_codice!=null &&
              this.p_azienda_codice.equals(other.getP_azienda_codice()))) &&
            ((this.p_specie_codice==null && other.getP_specie_codice()==null) || 
             (this.p_specie_codice!=null &&
              this.p_specie_codice.equals(other.getP_specie_codice()))) &&
            ((this.p_detent_id_fiscale==null && other.getP_detent_id_fiscale()==null) || 
             (this.p_detent_id_fiscale!=null &&
              this.p_detent_id_fiscale.equals(other.getP_detent_id_fiscale()))) &&
            ((this.p_tipo_prod==null && other.getP_tipo_prod()==null) || 
             (this.p_tipo_prod!=null &&
              this.p_tipo_prod.equals(other.getP_tipo_prod()))) &&
            ((this.p_orientamento==null && other.getP_orientamento()==null) || 
             (this.p_orientamento!=null &&
              this.p_orientamento.equals(other.getP_orientamento()))) &&
            ((this.p_tipo_allev==null && other.getP_tipo_allev()==null) || 
             (this.p_tipo_allev!=null &&
              this.p_tipo_allev.equals(other.getP_tipo_allev())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getP_allev_id_fiscale() != null) {
            _hashCode += getP_allev_id_fiscale().hashCode();
        }
        if (getP_azienda_codice() != null) {
            _hashCode += getP_azienda_codice().hashCode();
        }
        if (getP_specie_codice() != null) {
            _hashCode += getP_specie_codice().hashCode();
        }
        if (getP_detent_id_fiscale() != null) {
            _hashCode += getP_detent_id_fiscale().hashCode();
        }
        if (getP_tipo_prod() != null) {
            _hashCode += getP_tipo_prod().hashCode();
        }
        if (getP_orientamento() != null) {
            _hashCode += getP_orientamento().hashCode();
        }
        if (getP_tipo_allev() != null) {
            _hashCode += getP_tipo_allev().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAviCensimenti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAviCensimenti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_allev_id_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_azienda_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_specie_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_detent_id_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_detent_id_fiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_tipo_prod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_orientamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_tipo_allev");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
