/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Get_Capi_Allevamento_CodResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class Get_Capi_Allevamento_CodResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_CodResult;

    public Get_Capi_Allevamento_CodResponse() {
    }

    public Get_Capi_Allevamento_CodResponse(
           it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_CodResult) {
           this.get_Capi_Allevamento_CodResult = get_Capi_Allevamento_CodResult;
    }


    /**
     * Gets the get_Capi_Allevamento_CodResult value for this Get_Capi_Allevamento_CodResponse.
     * 
     * @return get_Capi_Allevamento_CodResult
     */
    public it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult getGet_Capi_Allevamento_CodResult() {
        return get_Capi_Allevamento_CodResult;
    }


    /**
     * Sets the get_Capi_Allevamento_CodResult value for this Get_Capi_Allevamento_CodResponse.
     * 
     * @param get_Capi_Allevamento_CodResult
     */
    public void setGet_Capi_Allevamento_CodResult(it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_CodResult) {
        this.get_Capi_Allevamento_CodResult = get_Capi_Allevamento_CodResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Get_Capi_Allevamento_CodResponse)) return false;
        Get_Capi_Allevamento_CodResponse other = (Get_Capi_Allevamento_CodResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.get_Capi_Allevamento_CodResult==null && other.getGet_Capi_Allevamento_CodResult()==null) || 
             (this.get_Capi_Allevamento_CodResult!=null &&
              this.get_Capi_Allevamento_CodResult.equals(other.getGet_Capi_Allevamento_CodResult())));
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
        if (getGet_Capi_Allevamento_CodResult() != null) {
            _hashCode += getGet_Capi_Allevamento_CodResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_Capi_Allevamento_CodResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_CodResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("get_Capi_Allevamento_CodResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_CodResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_Allevamento_CodResponse>Get_Capi_Allevamento_CodResult"));
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
