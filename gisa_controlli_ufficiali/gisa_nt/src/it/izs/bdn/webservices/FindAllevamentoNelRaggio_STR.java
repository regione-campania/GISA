/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAllevamentoNelRaggio_STR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAllevamentoNelRaggio_STR  implements java.io.Serializable {
    private java.lang.String p_specie_codice;

    private java.lang.String p_latitudine;

    private java.lang.String p_longitudine;

    private java.lang.String p_raggio;

    public FindAllevamentoNelRaggio_STR() {
    }

    public FindAllevamentoNelRaggio_STR(
           java.lang.String p_specie_codice,
           java.lang.String p_latitudine,
           java.lang.String p_longitudine,
           java.lang.String p_raggio) {
           this.p_specie_codice = p_specie_codice;
           this.p_latitudine = p_latitudine;
           this.p_longitudine = p_longitudine;
           this.p_raggio = p_raggio;
    }


    /**
     * Gets the p_specie_codice value for this FindAllevamentoNelRaggio_STR.
     * 
     * @return p_specie_codice
     */
    public java.lang.String getP_specie_codice() {
        return p_specie_codice;
    }


    /**
     * Sets the p_specie_codice value for this FindAllevamentoNelRaggio_STR.
     * 
     * @param p_specie_codice
     */
    public void setP_specie_codice(java.lang.String p_specie_codice) {
        this.p_specie_codice = p_specie_codice;
    }


    /**
     * Gets the p_latitudine value for this FindAllevamentoNelRaggio_STR.
     * 
     * @return p_latitudine
     */
    public java.lang.String getP_latitudine() {
        return p_latitudine;
    }


    /**
     * Sets the p_latitudine value for this FindAllevamentoNelRaggio_STR.
     * 
     * @param p_latitudine
     */
    public void setP_latitudine(java.lang.String p_latitudine) {
        this.p_latitudine = p_latitudine;
    }


    /**
     * Gets the p_longitudine value for this FindAllevamentoNelRaggio_STR.
     * 
     * @return p_longitudine
     */
    public java.lang.String getP_longitudine() {
        return p_longitudine;
    }


    /**
     * Sets the p_longitudine value for this FindAllevamentoNelRaggio_STR.
     * 
     * @param p_longitudine
     */
    public void setP_longitudine(java.lang.String p_longitudine) {
        this.p_longitudine = p_longitudine;
    }


    /**
     * Gets the p_raggio value for this FindAllevamentoNelRaggio_STR.
     * 
     * @return p_raggio
     */
    public java.lang.String getP_raggio() {
        return p_raggio;
    }


    /**
     * Sets the p_raggio value for this FindAllevamentoNelRaggio_STR.
     * 
     * @param p_raggio
     */
    public void setP_raggio(java.lang.String p_raggio) {
        this.p_raggio = p_raggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAllevamentoNelRaggio_STR)) return false;
        FindAllevamentoNelRaggio_STR other = (FindAllevamentoNelRaggio_STR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.p_specie_codice==null && other.getP_specie_codice()==null) || 
             (this.p_specie_codice!=null &&
              this.p_specie_codice.equals(other.getP_specie_codice()))) &&
            ((this.p_latitudine==null && other.getP_latitudine()==null) || 
             (this.p_latitudine!=null &&
              this.p_latitudine.equals(other.getP_latitudine()))) &&
            ((this.p_longitudine==null && other.getP_longitudine()==null) || 
             (this.p_longitudine!=null &&
              this.p_longitudine.equals(other.getP_longitudine()))) &&
            ((this.p_raggio==null && other.getP_raggio()==null) || 
             (this.p_raggio!=null &&
              this.p_raggio.equals(other.getP_raggio())));
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
        if (getP_specie_codice() != null) {
            _hashCode += getP_specie_codice().hashCode();
        }
        if (getP_latitudine() != null) {
            _hashCode += getP_latitudine().hashCode();
        }
        if (getP_longitudine() != null) {
            _hashCode += getP_longitudine().hashCode();
        }
        if (getP_raggio() != null) {
            _hashCode += getP_raggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAllevamentoNelRaggio_STR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggio_STR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_specie_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_latitudine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_latitudine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_longitudine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_longitudine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_raggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_raggio"));
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
