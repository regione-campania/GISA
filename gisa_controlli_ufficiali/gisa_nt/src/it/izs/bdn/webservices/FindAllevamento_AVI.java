/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * FindAllevamento_AVI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class FindAllevamento_AVI  implements java.io.Serializable {
    private java.lang.String p_azienda_codice;

    private java.lang.String p_prop_id_fiscale;

    private java.lang.String p_det_id_fiscale;

    private java.lang.String p_spe_codice;

    private java.lang.String p_tipo_prod;

    private java.lang.String p_orientamento;

    private java.lang.String p_tipo_allev;

    public FindAllevamento_AVI() {
    }

    public FindAllevamento_AVI(
           java.lang.String p_azienda_codice,
           java.lang.String p_prop_id_fiscale,
           java.lang.String p_det_id_fiscale,
           java.lang.String p_spe_codice,
           java.lang.String p_tipo_prod,
           java.lang.String p_orientamento,
           java.lang.String p_tipo_allev) {
           this.p_azienda_codice = p_azienda_codice;
           this.p_prop_id_fiscale = p_prop_id_fiscale;
           this.p_det_id_fiscale = p_det_id_fiscale;
           this.p_spe_codice = p_spe_codice;
           this.p_tipo_prod = p_tipo_prod;
           this.p_orientamento = p_orientamento;
           this.p_tipo_allev = p_tipo_allev;
    }


    /**
     * Gets the p_azienda_codice value for this FindAllevamento_AVI.
     * 
     * @return p_azienda_codice
     */
    public java.lang.String getP_azienda_codice() {
        return p_azienda_codice;
    }


    /**
     * Sets the p_azienda_codice value for this FindAllevamento_AVI.
     * 
     * @param p_azienda_codice
     */
    public void setP_azienda_codice(java.lang.String p_azienda_codice) {
        this.p_azienda_codice = p_azienda_codice;
    }


    /**
     * Gets the p_prop_id_fiscale value for this FindAllevamento_AVI.
     * 
     * @return p_prop_id_fiscale
     */
    public java.lang.String getP_prop_id_fiscale() {
        return p_prop_id_fiscale;
    }


    /**
     * Sets the p_prop_id_fiscale value for this FindAllevamento_AVI.
     * 
     * @param p_prop_id_fiscale
     */
    public void setP_prop_id_fiscale(java.lang.String p_prop_id_fiscale) {
        this.p_prop_id_fiscale = p_prop_id_fiscale;
    }


    /**
     * Gets the p_det_id_fiscale value for this FindAllevamento_AVI.
     * 
     * @return p_det_id_fiscale
     */
    public java.lang.String getP_det_id_fiscale() {
        return p_det_id_fiscale;
    }


    /**
     * Sets the p_det_id_fiscale value for this FindAllevamento_AVI.
     * 
     * @param p_det_id_fiscale
     */
    public void setP_det_id_fiscale(java.lang.String p_det_id_fiscale) {
        this.p_det_id_fiscale = p_det_id_fiscale;
    }


    /**
     * Gets the p_spe_codice value for this FindAllevamento_AVI.
     * 
     * @return p_spe_codice
     */
    public java.lang.String getP_spe_codice() {
        return p_spe_codice;
    }


    /**
     * Sets the p_spe_codice value for this FindAllevamento_AVI.
     * 
     * @param p_spe_codice
     */
    public void setP_spe_codice(java.lang.String p_spe_codice) {
        this.p_spe_codice = p_spe_codice;
    }


    /**
     * Gets the p_tipo_prod value for this FindAllevamento_AVI.
     * 
     * @return p_tipo_prod
     */
    public java.lang.String getP_tipo_prod() {
        return p_tipo_prod;
    }


    /**
     * Sets the p_tipo_prod value for this FindAllevamento_AVI.
     * 
     * @param p_tipo_prod
     */
    public void setP_tipo_prod(java.lang.String p_tipo_prod) {
        this.p_tipo_prod = p_tipo_prod;
    }


    /**
     * Gets the p_orientamento value for this FindAllevamento_AVI.
     * 
     * @return p_orientamento
     */
    public java.lang.String getP_orientamento() {
        return p_orientamento;
    }


    /**
     * Sets the p_orientamento value for this FindAllevamento_AVI.
     * 
     * @param p_orientamento
     */
    public void setP_orientamento(java.lang.String p_orientamento) {
        this.p_orientamento = p_orientamento;
    }


    /**
     * Gets the p_tipo_allev value for this FindAllevamento_AVI.
     * 
     * @return p_tipo_allev
     */
    public java.lang.String getP_tipo_allev() {
        return p_tipo_allev;
    }


    /**
     * Sets the p_tipo_allev value for this FindAllevamento_AVI.
     * 
     * @param p_tipo_allev
     */
    public void setP_tipo_allev(java.lang.String p_tipo_allev) {
        this.p_tipo_allev = p_tipo_allev;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindAllevamento_AVI)) return false;
        FindAllevamento_AVI other = (FindAllevamento_AVI) obj;
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
            ((this.p_prop_id_fiscale==null && other.getP_prop_id_fiscale()==null) || 
             (this.p_prop_id_fiscale!=null &&
              this.p_prop_id_fiscale.equals(other.getP_prop_id_fiscale()))) &&
            ((this.p_det_id_fiscale==null && other.getP_det_id_fiscale()==null) || 
             (this.p_det_id_fiscale!=null &&
              this.p_det_id_fiscale.equals(other.getP_det_id_fiscale()))) &&
            ((this.p_spe_codice==null && other.getP_spe_codice()==null) || 
             (this.p_spe_codice!=null &&
              this.p_spe_codice.equals(other.getP_spe_codice()))) &&
            ((this.p_tipo_prod==null && other.getP_tipo_prod()==null) || 
             (this.p_tipo_prod!=null &&
              this.p_tipo_prod.equals(other.getP_tipo_prod()))) &&
            ((this.p_orientamento==null && other.getP_orientamento()==null) || 
             (this.p_orientamento!=null &&
              this.p_orientamento.equals(other.getP_orientamento()))) &&
            ((this.p_tipo_allev==null && other.getP_tipo_allev()==null) || 
             (this.p_tipo_allev!=null &&
              this.p_tipo_allev.equals(other.getP_tipo_allev())));
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
        if (getP_prop_id_fiscale() != null) {
            _hashCode += getP_prop_id_fiscale().hashCode();
        }
        if (getP_det_id_fiscale() != null) {
            _hashCode += getP_det_id_fiscale().hashCode();
        }
        if (getP_spe_codice() != null) {
            _hashCode += getP_spe_codice().hashCode();
        }
        if (getP_tipo_prod() != null) {
            _hashCode += getP_tipo_prod().hashCode();
        }
        if (getP_orientamento() != null) {
            _hashCode += getP_orientamento().hashCode();
        }
        if (getP_tipo_allev() != null) {
            _hashCode += getP_tipo_allev().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindAllevamento_AVI.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">findAllevamento_AVI"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_azienda_codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_prop_id_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_prop_id_fiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_det_id_fiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_det_id_fiscale"));
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
        elemField.setFieldName("p_tipo_prod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_orientamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("p_tipo_allev");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"));
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
