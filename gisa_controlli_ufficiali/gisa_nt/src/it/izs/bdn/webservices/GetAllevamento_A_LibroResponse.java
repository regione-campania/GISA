/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetAllevamento_A_LibroResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetAllevamento_A_LibroResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_LibroResult;

    public GetAllevamento_A_LibroResponse() {
    }

    public GetAllevamento_A_LibroResponse(
           it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_LibroResult) {
           this.getAllevamento_A_LibroResult = getAllevamento_A_LibroResult;
    }


    /**
     * Gets the getAllevamento_A_LibroResult value for this GetAllevamento_A_LibroResponse.
     * 
     * @return getAllevamento_A_LibroResult
     */
    public it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getGetAllevamento_A_LibroResult() {
        return getAllevamento_A_LibroResult;
    }


    /**
     * Sets the getAllevamento_A_LibroResult value for this GetAllevamento_A_LibroResponse.
     * 
     * @param getAllevamento_A_LibroResult
     */
    public void setGetAllevamento_A_LibroResult(it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_LibroResult) {
        this.getAllevamento_A_LibroResult = getAllevamento_A_LibroResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAllevamento_A_LibroResponse)) return false;
        GetAllevamento_A_LibroResponse other = (GetAllevamento_A_LibroResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getAllevamento_A_LibroResult==null && other.getGetAllevamento_A_LibroResult()==null) || 
             (this.getAllevamento_A_LibroResult!=null &&
              this.getAllevamento_A_LibroResult.equals(other.getGetAllevamento_A_LibroResult())));
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
        if (getGetAllevamento_A_LibroResult() != null) {
            _hashCode += getGetAllevamento_A_LibroResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAllevamento_A_LibroResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetAllevamento_A_LibroResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAllevamento_A_LibroResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetAllevamento_A_LibroResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetAllevamento_A_LibroResponse>GetAllevamento_A_LibroResult"));
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
