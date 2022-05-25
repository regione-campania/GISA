/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetCancellazione_Amministrativa_STRResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetCancellazione_Amministrativa_STRResponse  implements java.io.Serializable {
    private java.lang.String getCancellazione_Amministrativa_STRResult;

    public GetCancellazione_Amministrativa_STRResponse() {
    }

    public GetCancellazione_Amministrativa_STRResponse(
           java.lang.String getCancellazione_Amministrativa_STRResult) {
           this.getCancellazione_Amministrativa_STRResult = getCancellazione_Amministrativa_STRResult;
    }


    /**
     * Gets the getCancellazione_Amministrativa_STRResult value for this GetCancellazione_Amministrativa_STRResponse.
     * 
     * @return getCancellazione_Amministrativa_STRResult
     */
    public java.lang.String getGetCancellazione_Amministrativa_STRResult() {
        return getCancellazione_Amministrativa_STRResult;
    }


    /**
     * Sets the getCancellazione_Amministrativa_STRResult value for this GetCancellazione_Amministrativa_STRResponse.
     * 
     * @param getCancellazione_Amministrativa_STRResult
     */
    public void setGetCancellazione_Amministrativa_STRResult(java.lang.String getCancellazione_Amministrativa_STRResult) {
        this.getCancellazione_Amministrativa_STRResult = getCancellazione_Amministrativa_STRResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCancellazione_Amministrativa_STRResponse)) return false;
        GetCancellazione_Amministrativa_STRResponse other = (GetCancellazione_Amministrativa_STRResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCancellazione_Amministrativa_STRResult==null && other.getGetCancellazione_Amministrativa_STRResult()==null) || 
             (this.getCancellazione_Amministrativa_STRResult!=null &&
              this.getCancellazione_Amministrativa_STRResult.equals(other.getGetCancellazione_Amministrativa_STRResult())));
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
        if (getGetCancellazione_Amministrativa_STRResult() != null) {
            _hashCode += getGetCancellazione_Amministrativa_STRResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCancellazione_Amministrativa_STRResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCancellazione_Amministrativa_STRResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCancellazione_Amministrativa_STRResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCancellazione_Amministrativa_STRResult"));
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
