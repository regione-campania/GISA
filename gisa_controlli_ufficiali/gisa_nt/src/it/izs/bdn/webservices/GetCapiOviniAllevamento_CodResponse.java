/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetCapiOviniAllevamento_CodResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetCapiOviniAllevamento_CodResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_CodResult;

    public GetCapiOviniAllevamento_CodResponse() {
    }

    public GetCapiOviniAllevamento_CodResponse(
           it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_CodResult) {
           this.getCapiOviniAllevamento_CodResult = getCapiOviniAllevamento_CodResult;
    }


    /**
     * Gets the getCapiOviniAllevamento_CodResult value for this GetCapiOviniAllevamento_CodResponse.
     * 
     * @return getCapiOviniAllevamento_CodResult
     */
    public it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getGetCapiOviniAllevamento_CodResult() {
        return getCapiOviniAllevamento_CodResult;
    }


    /**
     * Sets the getCapiOviniAllevamento_CodResult value for this GetCapiOviniAllevamento_CodResponse.
     * 
     * @param getCapiOviniAllevamento_CodResult
     */
    public void setGetCapiOviniAllevamento_CodResult(it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_CodResult) {
        this.getCapiOviniAllevamento_CodResult = getCapiOviniAllevamento_CodResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCapiOviniAllevamento_CodResponse)) return false;
        GetCapiOviniAllevamento_CodResponse other = (GetCapiOviniAllevamento_CodResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCapiOviniAllevamento_CodResult==null && other.getGetCapiOviniAllevamento_CodResult()==null) || 
             (this.getCapiOviniAllevamento_CodResult!=null &&
              this.getCapiOviniAllevamento_CodResult.equals(other.getGetCapiOviniAllevamento_CodResult())));
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
        if (getGetCapiOviniAllevamento_CodResult() != null) {
            _hashCode += getGetCapiOviniAllevamento_CodResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCapiOviniAllevamento_CodResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_CodResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCapiOviniAllevamento_CodResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_CodResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapiOviniAllevamento_CodResponse>getCapiOviniAllevamento_CodResult"));
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
