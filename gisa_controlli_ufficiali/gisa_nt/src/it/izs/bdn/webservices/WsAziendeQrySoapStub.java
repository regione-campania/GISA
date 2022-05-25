/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsAziendeQrySoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public class WsAziendeQrySoapStub extends org.apache.axis.client.Stub implements it.izs.bdn.webservices.WsAziendeQrySoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[43];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
        _initOperationDesc5();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAzienda");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAziendaResponse>getAziendaResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAziendaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAzienda_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAzienda_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPersona");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_persona_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getPersonaResponse>getPersonaResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getPersonaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPersona_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_persona_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getPersona_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetInfoSanitarie");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_malattia_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_dt_rilevazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetInfoSanitarieResponse>GetInfoSanitarieResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetInfoSanitarieResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetInfoSanitarie_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_malattia_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_dt_rilevazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetInfoSanitarie_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSoccidari");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetSoccidariResponse>GetSoccidariResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetSoccidariResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSoccidari_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetSoccidari_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoAllevamentoResponse>getInfoAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoAzienda");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoAziendaResponse>getInfoAziendaResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAziendaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoAzienda_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAzienda_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamentoResponse>getAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento_Latte");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamento_LatteResponse>getAllevamento_LatteResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_LatteResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento_Latte_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_Latte_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindOviTipologieProduttive");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindOviTipologieProduttiveResponse>FindOviTipologieProduttiveResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviTipologieProduttiveResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindOviTipologieProduttive_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviTipologieProduttive_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindSuiTipologieProduttive");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindSuiTipologieProduttiveResponse>FindSuiTipologieProduttiveResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiTipologieProduttiveResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindSuiTipologieProduttive_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiTipologieProduttive_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindOviCensimenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindOviCensimentiResponse>FindOviCensimentiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviCensimentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindOviCensimenti_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviCensimenti_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindSuiCensimenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindSuiCensimentiResponse>FindSuiCensimentiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiCensimentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindSuiCensimenti_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiCensimenti_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAviCensimenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_detent_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAviCensimentiResponse>FindAviCensimentiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAviCensimentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAviCensimenti_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_detent_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAviCensimenti_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento_AVI");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_prop_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_det_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamento_AVIResponse>getAllevamento_AVIResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_AVIResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllevamento_AVI_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_prop_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_det_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_AVI_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findAllevamento_AVI");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_prop_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_det_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_orientamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_tipo_allev"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>findAllevamento_AVIResponse>findAllevamento_AVIResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "findAllevamento_AVIResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_ListaAllevamenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_ListaAllevamentiResponse>Get_ListaAllevamentiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_ListaAllevamentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_ListaAllevamenti_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_ListaAllevamenti_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAllevamento_A_Libro");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetAllevamento_A_LibroResponse>GetAllevamento_A_LibroResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetAllevamento_A_LibroResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAllevamento_A_Libro_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetAllevamento_A_Libro_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllevamentoNelRaggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_latitudine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_longitudine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_raggio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllevamentoNelRaggioResponse>FindAllevamentoNelRaggioResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoNelRaggioResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllevamentoNelRaggio_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_latitudine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_longitudine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_raggio"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoNelRaggio_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_denominazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllevamentoResponse>FindAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllev_Tipologie_Prod");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_tipo_tipologia_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllev_Tipologie_ProdResponse>FindAllev_Tipologie_ProdResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllev_Tipologie_ProdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllev_Tipologie_Prod_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_tipo_tipologia_prod"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllev_Tipologie_Prod_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_denominazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_specie_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Estrazione_Allevamenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "strDelega"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Estrazione_AllevamentiResponse>Estrazione_AllevamentiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Estrazione_AllevamentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Estrazione_Allevamenti_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "strDelega"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Estrazione_Allevamenti_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindControlli_Allevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_dt_controllo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindControlli_Allevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindControlli_Allevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id_fiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_dt_controllo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindControlli_AllevamentoResponse>FindControlli_AllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindControlli_AllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[42] = oper;

    }

    public WsAziendeQrySoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WsAziendeQrySoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WsAziendeQrySoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Estrazione_AllevamentiResponse>Estrazione_AllevamentiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllev_Tipologie_ProdResponse>FindAllev_Tipologie_ProdResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>findAllevamento_AVIResponse>findAllevamento_AVIResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllevamentoNelRaggioResponse>FindAllevamentoNelRaggioResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAllevamentoResponse>FindAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindAviCensimentiResponse>FindAviCensimentiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindControlli_AllevamentoResponse>FindControlli_AllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindOviCensimentiResponse>FindOviCensimentiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindOviTipologieProduttiveResponse>FindOviTipologieProduttiveResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindSuiCensimentiResponse>FindSuiCensimentiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindSuiTipologieProduttiveResponse>FindSuiTipologieProduttiveResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_ListaAllevamentiResponse>Get_ListaAllevamentiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetAllevamento_A_LibroResponse>GetAllevamento_A_LibroResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamento_AVIResponse>getAllevamento_AVIResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamento_LatteResponse>getAllevamento_LatteResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAllevamentoResponse>getAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getAziendaResponse>getAziendaResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoAllevamentoResponse>getInfoAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoAziendaResponse>getInfoAziendaResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetInfoSanitarieResponse>GetInfoSanitarieResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getPersonaResponse>getPersonaResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>GetSoccidariResponse>GetSoccidariResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Estrazione_Allevamenti");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Estrazione_Allevamenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Estrazione_Allevamenti_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Estrazione_Allevamenti_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Estrazione_Allevamenti_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Estrazione_Allevamenti_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Estrazione_AllevamentiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Estrazione_AllevamentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_Prod");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllev_Tipologie_Prod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_Prod_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllev_Tipologie_Prod_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_Prod_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllev_Tipologie_Prod_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllev_Tipologie_ProdResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">findAllevamento_AVI");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento_AVI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">findAllevamento_AVIResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento_AVIResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggio");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoNelRaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggio_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoNelRaggio_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggio_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoNelRaggio_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoNelRaggioResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoNelRaggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAviCensimenti");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAviCensimenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAviCensimenti_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAviCensimenti_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAviCensimenti_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAviCensimenti_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindAviCensimentiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindAviCensimentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindControlli_Allevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindControlli_Allevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindControlli_Allevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindControlli_Allevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindControlli_Allevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindControlli_Allevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindControlli_AllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindControlli_AllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviCensimenti");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviCensimenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviCensimenti_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviCensimenti_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviCensimenti_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviCensimenti_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviCensimentiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviCensimentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviTipologieProduttive");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviTipologieProduttive.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviTipologieProduttive_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviTipologieProduttive_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviTipologieProduttive_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviTipologieProduttive_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindOviTipologieProduttiveResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindOviTipologieProduttiveResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiCensimenti");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiCensimenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiCensimenti_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiCensimenti_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiCensimenti_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiCensimenti_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiCensimentiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiCensimentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiTipologieProduttive");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiTipologieProduttive.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiTipologieProduttive_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiTipologieProduttive_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiTipologieProduttive_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiTipologieProduttive_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindSuiTipologieProduttiveResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindSuiTipologieProduttiveResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_ListaAllevamenti");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_ListaAllevamenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_ListaAllevamenti_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_ListaAllevamenti_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_ListaAllevamenti_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_ListaAllevamenti_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_ListaAllevamentiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_ListaAllevamentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetAllevamento_A_Libro");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_A_Libro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetAllevamento_A_Libro_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_A_Libro_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetAllevamento_A_Libro_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_A_Libro_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetAllevamento_A_LibroResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_A_LibroResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_AVI");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_AVI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_AVI_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_AVI_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_AVI_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_AVI_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_AVIResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_AVIResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_Latte");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_Latte.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_Latte_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_Latte_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_Latte_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_Latte_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_LatteResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_LatteResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAzienda_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAzienda_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getAzienda_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetAzienda_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAzienda");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAzienda.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAzienda_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAzienda_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAzienda_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAzienda_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoAziendaResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoAziendaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarie");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoSanitarie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarie_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoSanitarie_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarie_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoSanitarie_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetInfoSanitarieResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoSanitarieResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getPersona");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetPersona.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getPersona_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetPersona_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getPersona_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetPersona_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getPersonaResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetPersonaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetSoccidari");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetSoccidari.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetSoccidari_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetSoccidari_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetSoccidari_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetSoccidari_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">GetSoccidariResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetSoccidariResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "SOAPAutenticazione");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.SOAPAutenticazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult getAzienda(java.lang.String p_azienda_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAzienda");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAzienda"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getAzienda_STR(java.lang.String p_azienda_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAzienda_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAzienda_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult getPersona(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getPersona");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getPersona"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_persona_idfiscale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getPersona_STR(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getPersona_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getPersona_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_persona_idfiscale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarie(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetInfoSanitarie");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetInfoSanitarie"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_malattia_codice, p_dt_rilevazione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getInfoSanitarie_STR(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetInfoSanitarie_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetInfoSanitarie_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_malattia_codice, p_dt_rilevazione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult getSoccidari(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetSoccidari");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetSoccidari"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getSoccidari_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetSoccidari_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetSoccidari_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult getInfoAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getInfoAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAllevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult getInfoAzienda(java.lang.String p_azienda_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoAzienda");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAzienda"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getInfoAzienda_STR(java.lang.String p_azienda_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoAzienda_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoAzienda_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult getAllevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult getAllevamento_Latte(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento_Latte");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_Latte"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getAllevamento_Latte_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento_Latte_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_Latte_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult findOviTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindOviTipologieProduttive");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviTipologieProduttive"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findOviTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindOviTipologieProduttive_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviTipologieProduttive_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult findSuiTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindSuiTipologieProduttive");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiTipologieProduttive"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findSuiTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindSuiTipologieProduttive_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiTipologieProduttive_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult findOviCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindOviCensimenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviCensimenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findOviCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindOviCensimenti_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindOviCensimenti_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindSuiCensimenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiCensimenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findSuiCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindSuiCensimenti_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindSuiCensimenti_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult findAviCensimenti(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAviCensimenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAviCensimenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id_fiscale, p_azienda_codice, p_specie_codice, p_detent_id_fiscale, p_tipo_prod, p_orientamento, p_tipo_allev});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findAviCensimenti_STR(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAviCensimenti_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAviCensimenti_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id_fiscale, p_azienda_codice, p_specie_codice, p_detent_id_fiscale, p_tipo_prod, p_orientamento, p_tipo_allev});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento_AVI");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_AVI"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getAllevamento_AVI_STR(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getAllevamento_AVI_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getAllevamento_AVI_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult findAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/findAllevamento_AVI");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "findAllevamento_AVI"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult get_ListaAllevamenti(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_ListaAllevamenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_ListaAllevamenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_ListaAllevamenti_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_ListaAllevamenti_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_ListaAllevamenti_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_Libro(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetAllevamento_A_Libro");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetAllevamento_A_Libro"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getAllevamento_A_Libro_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/GetAllevamento_A_Libro_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "GetAllevamento_A_Libro_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult findAllevamentoNelRaggio(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllevamentoNelRaggio");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoNelRaggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_specie_codice, p_latitudine, p_longitudine, p_raggio});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findAllevamentoNelRaggio_STR(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllevamentoNelRaggio_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamentoNelRaggio_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_specie_codice, p_latitudine, p_longitudine, p_raggio});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult findAllevamento(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_denominazione, p_specie_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_Prod(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllev_Tipologie_Prod");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllev_Tipologie_Prod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_id_fiscale, p_specie_codice, p_cod_tipo_tipologia_prod});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findAllev_Tipologie_Prod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllev_Tipologie_Prod_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllev_Tipologie_Prod_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_id_fiscale, p_specie_codice, p_cod_tipo_tipologia_prod});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindAllevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_denominazione, p_specie_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult estrazione_Allevamenti(java.lang.String strDelega) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Estrazione_Allevamenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Estrazione_Allevamenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strDelega});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String estrazione_Allevamenti_STR(java.lang.String strDelega) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Estrazione_Allevamenti_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Estrazione_Allevamenti_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {strDelega});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findControlli_Allevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindControlli_Allevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindControlli_Allevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_id_fiscale, p_spe_codice, p_dt_controllo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult findControlli_Allevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindControlli_Allevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindControlli_Allevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_id_fiscale, p_spe_codice, p_dt_controllo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
