/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetInfoSanitarie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetInfoSanitarie  implements java.io.Serializable {
    private java.lang.String p_azienda_codice;

    private java.lang.String p_malattia_codice;

    private java.lang.String p_dt_rilevazione;

    public GetInfoSanitarie() {
    }

    public GetInfoSanitarie(
           java.lang.String p_azienda_codice,
           java.lang.String p_malattia_codice,
           java.lang.String p_dt_rilevazione) {
           this.p_azienda_codice = p_azienda_codice;
           this.p_malattia_codice = p_malattia_codice;
           this.p_dt_rilevazione = p_dt_rilevazione;
    }


    /**
     * Gets the p_azienda_codice value for this GetInfoSanitarie.
     * 
     * @return p_azienda_codice
     */
    public java.lang.String getP_azienda_codice() {
        return p_azienda_codice;
    }


    /**
     * Sets the p_azienda_codice value for this GetInfoSanitarie.
     * 
     * @param p_azienda_codice
     */
    public void setP_azienda_codice(java.lang.String p_azienda_codice) {
        this.p_azienda_codice = p_azienda_codice;
    }


    /**
     * Gets the p_malattia_codice value for this GetInfoSanitarie.
     * 
     * @return p_malattia_codice
     */
    public java.lang.String getP_malattia_codice() {
        return p_malattia_codice;
    }


    /**
     * Sets the p_malattia_codice value for this GetInfoSanitarie.
     * 
     * @param p_malattia_codice
     */
    public void setP_malattia_codice(java.lang.String p_malattia_codice) {
        this.p_malattia_codice = p_malattia_codice;
    }


    /**
     * Gets the p_dt_rilevazione value for this GetInfoSanitarie.
     * 
     * @return p_dt_rilevazione
     */
    public java.lang.String getP_dt_rilevazione() {
        return p_dt_rilevazione;
    }


    /**
     * Sets the p_dt_rilevazione value for this GetInfoSanitarie.
     * 
     * @param p_dt_rilevazione
     */
    public void setP_dt_rilevazione(java.lang.String p_dt_rilevazione) {
        this.p_dt_rilevazione = p_dt_rilevazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetInfoSanitarie)) return false;
        GetInfoSanitarie other = (GetInfoSanitarie) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.p_azienda_codice==null && other.getP_azienda_codice()==null) || 
             (this.p_azienda_codice!=null &&
              this.p_azienda_codice.equals(other.getP_azienda_codice()))) &&
            ((this.p_malattia_codice==null && other.getP_malattia_codice()==null) || 
             (this.p_malattia_codice!=null &&
              this.p_malattia_codice.equals(other.getP_malattia_codice()))) &&
            ((this.p_dt_rilevazione==null && other.getP_dt_rilevazione()==null) || 
             (this.p_dt_rilevazione!=null &&
              this.p_dt_rilevazione.equals(other.getP_dt_rilevazione())));
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
        if (getP_azienda_codice() != null) {
            _hashCode += getP_azienda_codice().hashCode();
        }
        if (getP_malattia_codice() != null) {
            _hashCode += getP_malattia_codice().hashCode();
        }
        if (getP_dt_rilevazione() != null) {
            _hashCode += getP_dt_rilevazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetInfoSanitarie.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_azienda_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_malattia_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_malattia_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_dt_rilevazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_dt_rilevazione"));
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
