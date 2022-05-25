/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetCapoOvinoMacellatoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetCapoOvinoMacellatoResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellatoResult;

    public GetCapoOvinoMacellatoResponse() {
    }

    public GetCapoOvinoMacellatoResponse(
           it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellatoResult) {
           this.getCapoOvinoMacellatoResult = getCapoOvinoMacellatoResult;
    }


    /**
     * Gets the getCapoOvinoMacellatoResult value for this GetCapoOvinoMacellatoResponse.
     * 
     * @return getCapoOvinoMacellatoResult
     */
    public it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getGetCapoOvinoMacellatoResult() {
        return getCapoOvinoMacellatoResult;
    }


    /**
     * Sets the getCapoOvinoMacellatoResult value for this GetCapoOvinoMacellatoResponse.
     * 
     * @param getCapoOvinoMacellatoResult
     */
    public void setGetCapoOvinoMacellatoResult(it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellatoResult) {
        this.getCapoOvinoMacellatoResult = getCapoOvinoMacellatoResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCapoOvinoMacellatoResponse)) return false;
        GetCapoOvinoMacellatoResponse other = (GetCapoOvinoMacellatoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCapoOvinoMacellatoResult==null && other.getGetCapoOvinoMacellatoResult()==null) || 
             (this.getCapoOvinoMacellatoResult!=null &&
              this.getCapoOvinoMacellatoResult.equals(other.getGetCapoOvinoMacellatoResult())));
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
        if (getGetCapoOvinoMacellatoResult() != null) {
            _hashCode += getGetCapoOvinoMacellatoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCapoOvinoMacellatoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoMacellatoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCapoOvinoMacellatoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoMacellatoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoOvinoMacellatoResponse>getCapoOvinoMacellatoResult"));
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
