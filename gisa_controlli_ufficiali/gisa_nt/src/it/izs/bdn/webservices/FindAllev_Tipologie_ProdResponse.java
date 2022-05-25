/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAllev_Tipologie_ProdResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAllev_Tipologie_ProdResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_ProdResult;

    public FindAllev_Tipologie_ProdResponse() {
    }

    public FindAllev_Tipologie_ProdResponse(
           it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_ProdResult) {
           this.findAllev_Tipologie_ProdResult = findAllev_Tipologie_ProdResult;
    }


    /**
     * Gets the findAllev_Tipologie_ProdResult value for this FindAllev_Tipologie_ProdResponse.
     * 
     * @return findAllev_Tipologie_ProdResult
     */
    public it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult getFindAllev_Tipologie_ProdResult() {
        return findAllev_Tipologie_ProdResult;
    }


    /**
     * Sets the findAllev_Tipologie_ProdResult value for this FindAllev_Tipologie_ProdResponse.
     * 
     * @param findAllev_Tipologie_ProdResult
     */
    public void setFindAllev_Tipologie_ProdResult(it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_ProdResult) {
        this.findAllev_Tipologie_ProdResult = findAllev_Tipologie_ProdResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAllev_Tipologie_ProdResponse)) return false;
        FindAllev_Tipologie_ProdResponse other = (FindAllev_Tipologie_ProdResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.findAllev_Tipologie_ProdResult==null && other.getFindAllev_Tipologie_ProdResult()==null) || 
             (this.findAllev_Tipologie_ProdResult!=null &&
              this.findAllev_Tipologie_ProdResult.equals(other.getFindAllev_Tipologie_ProdResult())));
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
        if (getFindAllev_Tipologie_ProdResult() != null) {
            _hashCode += getFindAllev_Tipologie_ProdResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAllev_Tipologie_ProdResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_ProdResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findAllev_Tipologie_ProdResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllev_Tipologie_ProdResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllev_Tipologie_ProdResponse>FindAllev_Tipologie_ProdResult"));
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
