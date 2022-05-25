/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * GetSoccidari.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class GetSoccidari  implements java.io.Serializable {
    private java.lang.String p_azienda_codice;

    private java.lang.String p_allev_idfiscale;

    private java.lang.String p_spe_codice;

    private java.lang.String p_storico;

    public GetSoccidari() {
    }

    public GetSoccidari(
           java.lang.String p_azienda_codice,
           java.lang.String p_allev_idfiscale,
           java.lang.String p_spe_codice,
           java.lang.String p_storico) {
           this.p_azienda_codice = p_azienda_codice;
           this.p_allev_idfiscale = p_allev_idfiscale;
           this.p_spe_codice = p_spe_codice;
           this.p_storico = p_storico;
    }


    /**
     * Gets the p_azienda_codice value for this GetSoccidari.
     * 
     * @return p_azienda_codice
     */
    public java.lang.String getP_azienda_codice() {
        return p_azienda_codice;
    }


    /**
     * Sets the p_azienda_codice value for this GetSoccidari.
     * 
     * @param p_azienda_codice
     */
    public void setP_azienda_codice(java.lang.String p_azienda_codice) {
        this.p_azienda_codice = p_azienda_codice;
    }


    /**
     * Gets the p_allev_idfiscale value for this GetSoccidari.
     * 
     * @return p_allev_idfiscale
     */
    public java.lang.String getP_allev_idfiscale() {
        return p_allev_idfiscale;
    }


    /**
     * Sets the p_allev_idfiscale value for this GetSoccidari.
     * 
     * @param p_allev_idfiscale
     */
    public void setP_allev_idfiscale(java.lang.String p_allev_idfiscale) {
        this.p_allev_idfiscale = p_allev_idfiscale;
    }


    /**
     * Gets the p_spe_codice value for this GetSoccidari.
     * 
     * @return p_spe_codice
     */
    public java.lang.String getP_spe_codice() {
        return p_spe_codice;
    }


    /**
     * Sets the p_spe_codice value for this GetSoccidari.
     * 
     * @param p_spe_codice
     */
    public void setP_spe_codice(java.lang.String p_spe_codice) {
        this.p_spe_codice = p_spe_codice;
    }


    /**
     * Gets the p_storico value for this GetSoccidari.
     * 
     * @return p_storico
     */
    public java.lang.String getP_storico() {
        return p_storico;
    }


    /**
     * Sets the p_storico value for this GetSoccidari.
     * 
     * @param p_storico
     */
    public void setP_storico(java.lang.String p_storico) {
        this.p_storico = p_storico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSoccidari)) return false;
        GetSoccidari other = (GetSoccidari) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.p_azienda_codice==null && other.getP_azienda_codice()==null) || 
             (this.p_azienda_codice!=null &&
              this.p_azienda_codice.equals(other.getP_azienda_codice()))) &&
            ((this.p_allev_idfiscale==null && other.getP_allev_idfiscale()==null) || 
             (this.p_allev_idfiscale!=null &&
              this.p_allev_idfiscale.equals(other.getP_allev_idfiscale()))) &&
            ((this.p_spe_codice==null && other.getP_spe_codice()==null) || 
             (this.p_spe_codice!=null &&
              this.p_spe_codice.equals(other.getP_spe_codice()))) &&
            ((this.p_storico==null && other.getP_storico()==null) || 
             (this.p_storico!=null &&
              this.p_storico.equals(other.getP_storico())));
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
        if (getP_azienda_codice() != null) {
            _hashCode += getP_azienda_codice().hashCode();
        }
        if (getP_allev_idfiscale() != null) {
            _hashCode += getP_allev_idfiscale().hashCode();
        }
        if (getP_spe_codice() != null) {
            _hashCode += getP_spe_codice().hashCode();
        }
        if (getP_storico() != null) {
            _hashCode += getP_storico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSoccidari.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetSoccidari"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_azienda_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_allev_idfiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_spe_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_storico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"));
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
