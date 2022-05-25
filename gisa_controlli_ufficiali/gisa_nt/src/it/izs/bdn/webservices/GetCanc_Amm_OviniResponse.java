/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetCanc_Amm_OviniResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetCanc_Amm_OviniResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_OviniResult;

    public GetCanc_Amm_OviniResponse() {
    }

    public GetCanc_Amm_OviniResponse(
           it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_OviniResult) {
           this.getCanc_Amm_OviniResult = getCanc_Amm_OviniResult;
    }


    /**
     * Gets the getCanc_Amm_OviniResult value for this GetCanc_Amm_OviniResponse.
     * 
     * @return getCanc_Amm_OviniResult
     */
    public it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getGetCanc_Amm_OviniResult() {
        return getCanc_Amm_OviniResult;
    }


    /**
     * Sets the getCanc_Amm_OviniResult value for this GetCanc_Amm_OviniResponse.
     * 
     * @param getCanc_Amm_OviniResult
     */
    public void setGetCanc_Amm_OviniResult(it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_OviniResult) {
        this.getCanc_Amm_OviniResult = getCanc_Amm_OviniResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCanc_Amm_OviniResponse)) return false;
        GetCanc_Amm_OviniResponse other = (GetCanc_Amm_OviniResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCanc_Amm_OviniResult==null && other.getGetCanc_Amm_OviniResult()==null) || 
             (this.getCanc_Amm_OviniResult!=null &&
              this.getCanc_Amm_OviniResult.equals(other.getGetCanc_Amm_OviniResult())));
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
        if (getGetCanc_Amm_OviniResult() != null) {
            _hashCode += getGetCanc_Amm_OviniResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCanc_Amm_OviniResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCanc_Amm_OviniResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCanc_Amm_OviniResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCanc_Amm_OviniResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCanc_Amm_OviniResponse>getCanc_Amm_OviniResult"));
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
