/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetAllevamento_AVIResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetAllevamento_AVIResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVIResult;

    public GetAllevamento_AVIResponse() {
    }

    public GetAllevamento_AVIResponse(
           it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVIResult) {
           this.getAllevamento_AVIResult = getAllevamento_AVIResult;
    }


    /**
     * Gets the getAllevamento_AVIResult value for this GetAllevamento_AVIResponse.
     * 
     * @return getAllevamento_AVIResult
     */
    public it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getGetAllevamento_AVIResult() {
        return getAllevamento_AVIResult;
    }


    /**
     * Sets the getAllevamento_AVIResult value for this GetAllevamento_AVIResponse.
     * 
     * @param getAllevamento_AVIResult
     */
    public void setGetAllevamento_AVIResult(it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVIResult) {
        this.getAllevamento_AVIResult = getAllevamento_AVIResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAllevamento_AVIResponse)) return false;
        GetAllevamento_AVIResponse other = (GetAllevamento_AVIResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getAllevamento_AVIResult==null && other.getGetAllevamento_AVIResult()==null) || 
             (this.getAllevamento_AVIResult!=null &&
              this.getAllevamento_AVIResult.equals(other.getGetAllevamento_AVIResult())));
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
        if (getGetAllevamento_AVIResult() != null) {
            _hashCode += getGetAllevamento_AVIResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAllevamento_AVIResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_AVIResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAllevamento_AVIResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_AVIResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamento_AVIResponse>getAllevamento_AVIResult"));
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
