/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAllevamentoNelRaggio_STRResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAllevamentoNelRaggio_STRResponse  implements java.io.Serializable {
    private java.lang.String findAllevamentoNelRaggio_STRResult;

    public FindAllevamentoNelRaggio_STRResponse() {
    }

    public FindAllevamentoNelRaggio_STRResponse(
           java.lang.String findAllevamentoNelRaggio_STRResult) {
           this.findAllevamentoNelRaggio_STRResult = findAllevamentoNelRaggio_STRResult;
    }


    /**
     * Gets the findAllevamentoNelRaggio_STRResult value for this FindAllevamentoNelRaggio_STRResponse.
     * 
     * @return findAllevamentoNelRaggio_STRResult
     */
    public java.lang.String getFindAllevamentoNelRaggio_STRResult() {
        return findAllevamentoNelRaggio_STRResult;
    }


    /**
     * Sets the findAllevamentoNelRaggio_STRResult value for this FindAllevamentoNelRaggio_STRResponse.
     * 
     * @param findAllevamentoNelRaggio_STRResult
     */
    public void setFindAllevamentoNelRaggio_STRResult(java.lang.String findAllevamentoNelRaggio_STRResult) {
        this.findAllevamentoNelRaggio_STRResult = findAllevamentoNelRaggio_STRResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAllevamentoNelRaggio_STRResponse)) return false;
        FindAllevamentoNelRaggio_STRResponse other = (FindAllevamentoNelRaggio_STRResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.findAllevamentoNelRaggio_STRResult==null && other.getFindAllevamentoNelRaggio_STRResult()==null) || 
             (this.findAllevamentoNelRaggio_STRResult!=null &&
              this.findAllevamentoNelRaggio_STRResult.equals(other.getFindAllevamentoNelRaggio_STRResult())));
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
        if (getFindAllevamentoNelRaggio_STRResult() != null) {
            _hashCode += getFindAllevamentoNelRaggio_STRResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAllevamentoNelRaggio_STRResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggio_STRResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findAllevamentoNelRaggio_STRResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoNelRaggio_STRResult"));
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
