/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Find_Capi_Equini_MacellatiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class Find_Capi_Equini_MacellatiResponse  implements java.io.Serializable {
    private it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_MacellatiResult;

    public Find_Capi_Equini_MacellatiResponse() {
    }

    public Find_Capi_Equini_MacellatiResponse(
           it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_MacellatiResult) {
           this.find_Capi_Equini_MacellatiResult = find_Capi_Equini_MacellatiResult;
    }


    /**
     * Gets the find_Capi_Equini_MacellatiResult value for this Find_Capi_Equini_MacellatiResponse.
     * 
     * @return find_Capi_Equini_MacellatiResult
     */
    public it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult getFind_Capi_Equini_MacellatiResult() {
        return find_Capi_Equini_MacellatiResult;
    }


    /**
     * Sets the find_Capi_Equini_MacellatiResult value for this Find_Capi_Equini_MacellatiResponse.
     * 
     * @param find_Capi_Equini_MacellatiResult
     */
    public void setFind_Capi_Equini_MacellatiResult(it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_MacellatiResult) {
        this.find_Capi_Equini_MacellatiResult = find_Capi_Equini_MacellatiResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Find_Capi_Equini_MacellatiResponse)) return false;
        Find_Capi_Equini_MacellatiResponse other = (Find_Capi_Equini_MacellatiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.find_Capi_Equini_MacellatiResult==null && other.getFind_Capi_Equini_MacellatiResult()==null) || 
             (this.find_Capi_Equini_MacellatiResult!=null &&
              this.find_Capi_Equini_MacellatiResult.equals(other.getFind_Capi_Equini_MacellatiResult())));
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
        if (getFind_Capi_Equini_MacellatiResult() != null) {
            _hashCode += getFind_Capi_Equini_MacellatiResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Find_Capi_Equini_MacellatiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">find_Capi_Equini_MacellatiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("find_Capi_Equini_MacellatiResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "find_Capi_Equini_MacellatiResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>find_Capi_Equini_MacellatiResponse>find_Capi_Equini_MacellatiResult"));
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
