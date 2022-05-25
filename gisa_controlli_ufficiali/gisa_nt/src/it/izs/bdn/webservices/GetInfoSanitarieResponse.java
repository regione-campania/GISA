/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetInfoSanitarieResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetInfoSanitarieResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarieResult;

    public GetInfoSanitarieResponse() {
    }

    public GetInfoSanitarieResponse(
           it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarieResult) {
           this.getInfoSanitarieResult = getInfoSanitarieResult;
    }


    /**
     * Gets the getInfoSanitarieResult value for this GetInfoSanitarieResponse.
     * 
     * @return getInfoSanitarieResult
     */
    public it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getGetInfoSanitarieResult() {
        return getInfoSanitarieResult;
    }


    /**
     * Sets the getInfoSanitarieResult value for this GetInfoSanitarieResponse.
     * 
     * @param getInfoSanitarieResult
     */
    public void setGetInfoSanitarieResult(it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarieResult) {
        this.getInfoSanitarieResult = getInfoSanitarieResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetInfoSanitarieResponse)) return false;
        GetInfoSanitarieResponse other = (GetInfoSanitarieResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getInfoSanitarieResult==null && other.getGetInfoSanitarieResult()==null) || 
             (this.getInfoSanitarieResult!=null &&
              this.getInfoSanitarieResult.equals(other.getGetInfoSanitarieResult())));
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
        if (getGetInfoSanitarieResult() != null) {
            _hashCode += getGetInfoSanitarieResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetInfoSanitarieResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarieResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getInfoSanitarieResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetInfoSanitarieResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetInfoSanitarieResponse>GetInfoSanitarieResult"));
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
