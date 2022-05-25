/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindSuiCensimentiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindSuiCensimentiResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimentiResult;

    public FindSuiCensimentiResponse() {
    }

    public FindSuiCensimentiResponse(
           it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimentiResult) {
           this.findSuiCensimentiResult = findSuiCensimentiResult;
    }


    /**
     * Gets the findSuiCensimentiResult value for this FindSuiCensimentiResponse.
     * 
     * @return findSuiCensimentiResult
     */
    public it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult getFindSuiCensimentiResult() {
        return findSuiCensimentiResult;
    }


    /**
     * Sets the findSuiCensimentiResult value for this FindSuiCensimentiResponse.
     * 
     * @param findSuiCensimentiResult
     */
    public void setFindSuiCensimentiResult(it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimentiResult) {
        this.findSuiCensimentiResult = findSuiCensimentiResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindSuiCensimentiResponse)) return false;
        FindSuiCensimentiResponse other = (FindSuiCensimentiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.findSuiCensimentiResult==null && other.getFindSuiCensimentiResult()==null) || 
             (this.findSuiCensimentiResult!=null &&
              this.findSuiCensimentiResult.equals(other.getFindSuiCensimentiResult())));
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
        if (getFindSuiCensimentiResult() != null) {
            _hashCode += getFindSuiCensimentiResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindSuiCensimentiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiCensimentiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findSuiCensimentiResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiCensimentiResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindSuiCensimentiResponse>FindSuiCensimentiResult"));
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
