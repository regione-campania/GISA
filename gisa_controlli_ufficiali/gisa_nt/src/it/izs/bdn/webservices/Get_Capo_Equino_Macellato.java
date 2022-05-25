/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Get_Capo_Equino_Macellato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class Get_Capo_Equino_Macellato  implements java.io.Serializable {
    private java.lang.String p_codice_elettronico;

    private java.lang.String p_passaporto;

    private java.lang.String p_codice_ueln;

    public Get_Capo_Equino_Macellato() {
    }

    public Get_Capo_Equino_Macellato(
           java.lang.String p_codice_elettronico,
           java.lang.String p_passaporto,
           java.lang.String p_codice_ueln) {
           this.p_codice_elettronico = p_codice_elettronico;
           this.p_passaporto = p_passaporto;
           this.p_codice_ueln = p_codice_ueln;
    }


    /**
     * Gets the p_codice_elettronico value for this Get_Capo_Equino_Macellato.
     * 
     * @return p_codice_elettronico
     */
    public java.lang.String getP_codice_elettronico() {
        return p_codice_elettronico;
    }


    /**
     * Sets the p_codice_elettronico value for this Get_Capo_Equino_Macellato.
     * 
     * @param p_codice_elettronico
     */
    public void setP_codice_elettronico(java.lang.String p_codice_elettronico) {
        this.p_codice_elettronico = p_codice_elettronico;
    }


    /**
     * Gets the p_passaporto value for this Get_Capo_Equino_Macellato.
     * 
     * @return p_passaporto
     */
    public java.lang.String getP_passaporto() {
        return p_passaporto;
    }


    /**
     * Sets the p_passaporto value for this Get_Capo_Equino_Macellato.
     * 
     * @param p_passaporto
     */
    public void setP_passaporto(java.lang.String p_passaporto) {
        this.p_passaporto = p_passaporto;
    }


    /**
     * Gets the p_codice_ueln value for this Get_Capo_Equino_Macellato.
     * 
     * @return p_codice_ueln
     */
    public java.lang.String getP_codice_ueln() {
        return p_codice_ueln;
    }


    /**
     * Sets the p_codice_ueln value for this Get_Capo_Equino_Macellato.
     * 
     * @param p_codice_ueln
     */
    public void setP_codice_ueln(java.lang.String p_codice_ueln) {
        this.p_codice_ueln = p_codice_ueln;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Get_Capo_Equino_Macellato)) return false;
        Get_Capo_Equino_Macellato other = (Get_Capo_Equino_Macellato) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.p_codice_elettronico==null && other.getP_codice_elettronico()==null) || 
             (this.p_codice_elettronico!=null &&
              this.p_codice_elettronico.equals(other.getP_codice_elettronico()))) &&
            ((this.p_passaporto==null && other.getP_passaporto()==null) || 
             (this.p_passaporto!=null &&
              this.p_passaporto.equals(other.getP_passaporto()))) &&
            ((this.p_codice_ueln==null && other.getP_codice_ueln()==null) || 
             (this.p_codice_ueln!=null &&
              this.p_codice_ueln.equals(other.getP_codice_ueln())));
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
        if (getP_codice_elettronico() != null) {
            _hashCode += getP_codice_elettronico().hashCode();
        }
        if (getP_passaporto() != null) {
            _hashCode += getP_passaporto().hashCode();
        }
        if (getP_codice_ueln() != null) {
            _hashCode += getP_codice_ueln().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_Capo_Equino_Macellato.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Equino_Macellato"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_codice_elettronico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_passaporto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_codice_ueln");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"));
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
