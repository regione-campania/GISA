/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAllev_Tipologie_Prod_STR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAllev_Tipologie_Prod_STR  implements java.io.Serializable {
    private java.lang.String p_azienda_codice;

    private java.lang.String p_allev_id_fiscale;

    private java.lang.String p_specie_codice;

    private java.lang.String p_cod_tipo_tipologia_prod;

    public FindAllev_Tipologie_Prod_STR() {
    }

    public FindAllev_Tipologie_Prod_STR(
           java.lang.String p_azienda_codice,
           java.lang.String p_allev_id_fiscale,
           java.lang.String p_specie_codice,
           java.lang.String p_cod_tipo_tipologia_prod) {
           this.p_azienda_codice = p_azienda_codice;
           this.p_allev_id_fiscale = p_allev_id_fiscale;
           this.p_specie_codice = p_specie_codice;
           this.p_cod_tipo_tipologia_prod = p_cod_tipo_tipologia_prod;
    }


    /**
     * Gets the p_azienda_codice value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @return p_azienda_codice
     */
    public java.lang.String getP_azienda_codice() {
        return p_azienda_codice;
    }


    /**
     * Sets the p_azienda_codice value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @param p_azienda_codice
     */
    public void setP_azienda_codice(java.lang.String p_azienda_codice) {
        this.p_azienda_codice = p_azienda_codice;
    }


    /**
     * Gets the p_allev_id_fiscale value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @return p_allev_id_fiscale
     */
    public java.lang.String getP_allev_id_fiscale() {
        return p_allev_id_fiscale;
    }


    /**
     * Sets the p_allev_id_fiscale value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @param p_allev_id_fiscale
     */
    public void setP_allev_id_fiscale(java.lang.String p_allev_id_fiscale) {
        this.p_allev_id_fiscale = p_allev_id_fiscale;
    }


    /**
     * Gets the p_specie_codice value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @return p_specie_codice
     */
    public java.lang.String getP_specie_codice() {
        return p_specie_codice;
    }


    /**
     * Sets the p_specie_codice value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @param p_specie_codice
     */
    public void setP_specie_codice(java.lang.String p_specie_codice) {
        this.p_specie_codice = p_specie_codice;
    }


    /**
     * Gets the p_cod_tipo_tipologia_prod value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @return p_cod_tipo_tipologia_prod
     */
    public java.lang.String getP_cod_tipo_tipologia_prod() {
        return p_cod_tipo_tipologia_prod;
    }


    /**
     * Sets the p_cod_tipo_tipologia_prod value for this FindAllev_Tipologie_Prod_STR.
     * 
     * @param p_cod_tipo_tipologia_prod
     */
    public void setP_cod_tipo_tipologia_prod(java.lang.String p_cod_tipo_tipologia_prod) {
        this.p_cod_tipo_tipologia_prod = p_cod_tipo_tipologia_prod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAllev_Tipologie_Prod_STR)) return false;
        FindAllev_Tipologie_Prod_STR other = (FindAllev_Tipologie_Prod_STR) obj;
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
            ((this.p_allev_id_fiscale==null && other.getP_allev_id_fiscale()==null) || 
             (this.p_allev_id_fiscale!=null &&
              this.p_allev_id_fiscale.equals(other.getP_allev_id_fiscale()))) &&
            ((this.p_specie_codice==null && other.getP_specie_codice()==null) || 
             (this.p_specie_codice!=null &&
              this.p_specie_codice.equals(other.getP_specie_codice()))) &&
            ((this.p_cod_tipo_tipologia_prod==null && other.getP_cod_tipo_tipologia_prod()==null) || 
             (this.p_cod_tipo_tipologia_prod!=null &&
              this.p_cod_tipo_tipologia_prod.equals(other.getP_cod_tipo_tipologia_prod())));
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
        if (getP_allev_id_fiscale() != null) {
            _hashCode += getP_allev_id_fiscale().hashCode();
        }
        if (getP_specie_codice() != null) {
            _hashCode += getP_specie_codice().hashCode();
        }
        if (getP_cod_tipo_tipologia_prod() != null) {
            _hashCode += getP_cod_tipo_tipologia_prod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAllev_Tipologie_Prod_STR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_Prod_STR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_azienda_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_allev_id_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_specie_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_cod_tipo_tipologia_prod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_tipo_tipologia_prod"));
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
