/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsAnagraficaCapoQrySoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.SOAPHeaderElement;


public class WsAnagraficaCapoQrySoapStub extends org.apache.axis.client.Stub implements it.izs.bdn.webservices.WsAnagraficaCapoQrySoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[49];
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
        oper.setName("getCapoTAG");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_TAG"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoTAGResponse>getCapoTAGResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoTAGResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoResponse>getCapoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoResponseGetCapoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapo_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapo_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoTAG_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_TAG"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoTAG_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoOvino");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoOvinoResponse>getCapoOvinoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoOvino_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvino_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoALibro");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoALibroResponse>getCapoALibroResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoALibroResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoALibro_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoALibro_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoMacellato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoMacellatoResponse>getCapoMacellatoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoMacellatoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoMacellato_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoMacellato_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoOvinoMacellato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoOvinoMacellatoResponse>getCapoOvinoMacellatoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoMacellatoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapoOvinoMacellato_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoMacellato_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_AllevamentoResponse>Get_Capi_AllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_AllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento_Periodo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_dal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_al"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_Allevamento_PeriodoResponse>Get_Capi_Allevamento_PeriodoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_PeriodoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento_Periodo_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_dal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_al"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Periodo_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapiOviniAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapiOviniAllevamentoResponse>getCapiOviniAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapiOviniAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_storico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento_Cod");
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
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_Allevamento_CodResponse>Get_Capi_Allevamento_CodResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_CodResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Get_Capi_Allevamento_Cod_STR");
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
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_dal"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data_al"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Cod_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapiOviniAllevamento_Cod");
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
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapiOviniAllevamento_CodResponse>getCapiOviniAllevamento_CodResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_CodResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCapiOviniAllevamento_Cod_STR");
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
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_cod_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_Cod_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoCapiAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoCapiAllevamentoResponse>getInfoCapiAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoCapiAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoCapiOviniAllevamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoCapiOviniAllevamentoResponse>getInfoCapiOviniAllevamentoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiOviniAllevamentoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getInfoCapiOviniAllevamento_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiOviniAllevamento_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMadri");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getMadriResponse>getMadriResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetMadriResponseGetMadriResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getMadriResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMadri_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_data"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getMadri_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDecesso");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getDecessoResponse>getDecessoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecessoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDecesso_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecesso_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDecessoOvino");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getDecessoOvinoResponse>getDecessoOvinoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecessoOvinoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDecessoOvino_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_azienda_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_allev_idfiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_spe_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecessoOvino_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFurto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getFurtoResponse>getFurtoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurtoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFurto_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurto_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFurtoOvino");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getFurtoOvinoResponse>getFurtoOvinoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurtoOvinoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFurtoOvino_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_capo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurtoOvino_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindCapoMacellato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindCapoMacellatoResponse>FindCapoMacellatoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapoMacellatoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindCapoMacellato_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapoMacellato_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindCapiOviniMacellati");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindCapiOviniMacellatiResponse>FindCapiOviniMacellatiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapiOviniMacellatiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindCapiOviniMacellati_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapiOviniMacellati_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCancellazione_Amministrativa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCancellazione_AmministrativaResponse>getCancellazione_AmministrativaResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCancellazione_AmministrativaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCancellazione_Amministrativa_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCancellazione_Amministrativa_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Capo_Sentinella");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_ident_nome"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>get_Capo_SentinellaResponse>get_Capo_SentinellaResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_SentinellaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[42] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Capo_Sentinella_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_ident_nome"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Sentinella_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[43] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Capo_Equino_Macellato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>get_Capo_Equino_MacellatoResponse>get_Capo_Equino_MacellatoResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Equino_MacellatoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[44] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Capo_Equino_Macellato_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Equino_Macellato_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[45] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("find_Capi_Equini_Macellati");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>find_Capi_Equini_MacellatiResponse>find_Capi_Equini_MacellatiResult"));
        oper.setReturnClass(it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "find_Capi_Equini_MacellatiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[46] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("find_Capi_Equini_Macellati_STR");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_elettronico"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_passaporto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_codice_ueln"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "find_Capi_Equini_Macellati_STRResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[47] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCanc_Amm_Ovini");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "p_capo_codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCanc_Amm_OviniResponse>getCanc_Amm_OviniResult"));
        oper.setReturnClass(it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCanc_Amm_OviniResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[48] = oper;

    }

    public WsAnagraficaCapoQrySoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WsAnagraficaCapoQrySoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WsAnagraficaCapoQrySoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>find_Capi_Equini_MacellatiResponse>find_Capi_Equini_MacellatiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindCapiOviniMacellatiResponse>FindCapiOviniMacellatiResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>FindCapoMacellatoResponse>FindCapoMacellatoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_Allevamento_CodResponse>Get_Capi_Allevamento_CodResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_Allevamento_PeriodoResponse>Get_Capi_Allevamento_PeriodoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>Get_Capi_AllevamentoResponse>Get_Capi_AllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>get_Capo_Equino_MacellatoResponse>get_Capo_Equino_MacellatoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>get_Capo_SentinellaResponse>get_Capo_SentinellaResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCanc_Amm_OviniResponse>getCanc_Amm_OviniResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCancellazione_AmministrativaResponse>getCancellazione_AmministrativaResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapiOviniAllevamento_CodResponse>getCapiOviniAllevamento_CodResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapiOviniAllevamentoResponse>getCapiOviniAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoALibroResponse>getCapoALibroResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoMacellatoResponse>getCapoMacellatoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoOvinoMacellatoResponse>getCapoOvinoMacellatoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoOvinoResponse>getCapoOvinoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoResponse>getCapoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoResponseGetCapoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getCapoTAGResponse>getCapoTAGResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getDecessoOvinoResponse>getDecessoOvinoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getDecessoResponse>getDecessoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getFurtoOvinoResponse>getFurtoOvinoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getFurtoResponse>getFurtoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoCapiAllevamentoResponse>getInfoCapiAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getInfoCapiOviniAllevamentoResponse>getInfoCapiOviniAllevamentoResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">>getMadriResponse>getMadriResult");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetMadriResponseGetMadriResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">find_Capi_Equini_Macellati");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Find_Capi_Equini_Macellati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">find_Capi_Equini_Macellati_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Find_Capi_Equini_Macellati_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">find_Capi_Equini_Macellati_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Find_Capi_Equini_Macellati_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">find_Capi_Equini_MacellatiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapiOviniMacellati");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapiOviniMacellati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapiOviniMacellati_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapiOviniMacellati_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapiOviniMacellati_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapiOviniMacellati_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapiOviniMacellatiResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapiOviniMacellatiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapoMacellato");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapoMacellato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapoMacellato_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapoMacellato_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapoMacellato_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapoMacellato_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">FindCapoMacellatoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.FindCapoMacellatoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Cod");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Cod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Cod_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Cod_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Cod_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Cod_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_CodResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Periodo");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Periodo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Periodo_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Periodo_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_Periodo_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_Periodo_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_PeriodoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_Allevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_Allevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">Get_Capi_AllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capi_AllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Equino_Macellato");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Equino_Macellato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Equino_Macellato_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Equino_Macellato_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Equino_Macellato_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Equino_Macellato_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Equino_MacellatoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Sentinella");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Sentinella.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Sentinella_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Sentinella_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_Sentinella_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_Sentinella_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">get_Capo_SentinellaResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.Get_Capo_SentinellaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCanc_Amm_Ovini");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCanc_Amm_Ovini.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCanc_Amm_OviniResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCanc_Amm_OviniResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCancellazione_Amministrativa");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCancellazione_Amministrativa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCancellazione_Amministrativa_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCancellazione_Amministrativa_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCancellazione_Amministrativa_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCancellazione_Amministrativa_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCancellazione_AmministrativaResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCancellazione_AmministrativaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_Cod");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_Cod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_Cod_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_Cod_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_Cod_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_Cod_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_CodResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapiOviniAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapiOviniAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapo");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapo_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapo_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapo_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapo_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoALibro");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoALibro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoALibro_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoALibro_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoALibro_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoALibro_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoALibroResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoALibroResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoMacellato");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoMacellato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoMacellato_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoMacellato_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoMacellato_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoMacellato_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoMacellatoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoMacellatoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvino");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvino.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvino_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvino_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvino_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvino_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoMacellato");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoMacellato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoMacellato_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoMacellato_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoMacellato_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoMacellato_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoMacellatoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoMacellatoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoOvinoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoOvinoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoTAG_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoTAG_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getCapoTAG_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetCapoTAG_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecesso");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecesso_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecesso_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecesso_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecesso_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecessoOvino");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoOvino.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecessoOvino_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoOvino_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecessoOvino_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoOvino_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecessoOvinoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoOvinoResponse.class;
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
            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getDecessoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetDecessoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurto");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurto_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurto_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurto_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurto_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurtoOvino");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoOvino.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurtoOvino_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoOvino_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurtoOvino_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoOvino_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurtoOvinoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoOvinoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getFurtoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetFurtoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiOviniAllevamento");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiOviniAllevamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiOviniAllevamento_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiOviniAllevamento_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiOviniAllevamento_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiOviniAllevamento_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getInfoCapiOviniAllevamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getMadri");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetMadri.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getMadri_STR");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetMadri_STR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getMadri_STRResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetMadri_STRResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", ">getMadriResponse");
            cachedSerQNames.add(qName);
            cls = it.izs.bdn.webservices.GetMadriResponse.class;
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

    public it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult getCapoTAG(java.lang.String p_TAG) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoTAG");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoTAG"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_TAG});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.izs.bdn.webservices.GetCapoResponseGetCapoResult getCapo(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapo"));
        
//        String userNameSTR = "izsna_006";
//        String passWordSTR = "na.izs34";
//
//        javax.xml.namespace.QName qName = null;
//        //qName = new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Header", "web");    
//        qName = new javax.xml.namespace.QName("soapenv:Header");
//        
//        SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(qName);
//
//        try {
//        	soapHeaderElement.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
//        	soapHeaderElement.addNamespaceDeclaration("web", "http://bdr.izs.it/webservices");
//        	soapHeaderElement.setMustUnderstand(true);
//        	soapHeaderElement.setActor(null);  
//        	SOAPElement usernameTokenSOAPElement = soapHeaderElement.addChildElement("web:SOAPAutenticazione", "web", "http://bdr.izs.it/webservices");
//        	SOAPElement userNameSOAPElement = usernameTokenSOAPElement.addChildElement("web:Username", "web", "http://bdr.izs.it/webservices");
//        	userNameSOAPElement.addTextNode(userNameSTR);
//        	SOAPElement passwordSOAPElement = usernameTokenSOAPElement.addChildElement("web:Password", "web", "http://bdr.izs.it/webservices");
//        	passwordSOAPElement.addTextNode(passWordSTR);
//        } catch (SOAPException e1) {
//        	e1.printStackTrace();
//        }
//
//        setHeader(soapHeaderElement);
        
        
//        //SOAPHeaderElement wsseSecurity = new SOAPHeaderElement(new QName(null,null)); // "http://bdr.izs.it/webservices/getCapo","SOAPAutenticazione")); //, "wsse"));
//		String nullString = null;
//		SOAPHeaderElement wsseSecurity = new SOAPHeaderElement();
//        MessageElement usernameToken = new MessageElement(nullString, "web:SOAPAutenticazione");
//        MessageElement username = new MessageElement(nullString, "web:Username");
//        MessageElement password = new MessageElement(nullString, "web:Password");
//        try {
//			username.setObjectValue(userNameSTR);
//			usernameToken.addChild(username);
//			password.setObjectValue(passWordSTR);			//myProps.getProperty("password"));
//			usernameToken.addChild(password);
//			wsseSecurity.addChild(usernameToken);
//		} catch (SOAPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}			//myProps.getProperty("username"));
//
//        setHeader(wsseSecurity);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoResponseGetCapoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoResponseGetCapoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoResponseGetCapoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapo_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapo_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapo_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public java.lang.String getCapoTAG_STR(java.lang.String p_TAG) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoTAG_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoTAG_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_TAG});

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

    public it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult getCapoOvino(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoOvino");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvino"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapoOvino_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoOvino_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvino_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult getCapoALibro(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoALibro");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoALibro"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapoALibro_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoALibro_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoALibro_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult getCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoMacellato");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoMacellato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoMacellato_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoMacellato_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoOvinoMacellato");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoMacellato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapoOvinoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapoOvinoMacellato_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapoOvinoMacellato_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult get_Capi_Allevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_storico, p_cod_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_Capi_Allevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_storico, p_cod_capo});

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

    public it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult get_Capi_Allevamento_Periodo(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento_Periodo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Periodo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_cod_capo, p_data_dal, p_data_al});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_Capi_Allevamento_Periodo_STR(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento_Periodo_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Periodo_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_cod_capo, p_data_dal, p_data_al});

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

    public it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult getCapiOviniAllevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapiOviniAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_storico, p_cod_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapiOviniAllevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapiOviniAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_storico, p_cod_capo});

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

    public it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento_Cod");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Cod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_Capi_Allevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/Get_Capi_Allevamento_Cod_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "Get_Capi_Allevamento_Cod_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo, p_data_dal, p_data_al});

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

    public it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapiOviniAllevamento_Cod");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_Cod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCapiOviniAllevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCapiOviniAllevamento_Cod_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCapiOviniAllevamento_Cod_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo});

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

    public it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult getInfoCapiAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoCapiAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getInfoCapiAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoCapiAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiAllevamento_STR"));

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

    public it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult getInfoCapiOviniAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoCapiOviniAllevamento");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiOviniAllevamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getInfoCapiOviniAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getInfoCapiOviniAllevamento_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getInfoCapiOviniAllevamento_STR"));

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

    public it.izs.bdn.webservices.GetMadriResponseGetMadriResult getMadri(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getMadri");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getMadri"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_data});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetMadriResponseGetMadriResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetMadriResponseGetMadriResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetMadriResponseGetMadriResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getMadri_STR(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getMadri_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getMadri_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_allev_id, p_data});

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

    public it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult getDecesso(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getDecesso");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecesso"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getDecesso_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getDecesso_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecesso_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo});

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

    public it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult getDecessoOvino(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getDecessoOvino");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecessoOvino"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getDecessoOvino_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getDecessoOvino_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getDecessoOvino_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo});

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

    public it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult getFurto(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getFurto");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getFurto_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getFurto_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurto_STR"));

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

    public it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult getFurtoOvino(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getFurtoOvino");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurtoOvino"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_capo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getFurtoOvino_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getFurtoOvino_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getFurtoOvino_STR"));

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

    public it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult findCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindCapoMacellato");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapoMacellato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindCapoMacellato_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapoMacellato_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult findCapiOviniMacellati(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindCapiOviniMacellati");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapiOviniMacellati"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String findCapiOviniMacellati_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/FindCapiOviniMacellati_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "FindCapiOviniMacellati_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult getCancellazione_Amministrativa(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCancellazione_Amministrativa");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCancellazione_Amministrativa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCancellazione_Amministrativa_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCancellazione_Amministrativa_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCancellazione_Amministrativa_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

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

    public it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult get_Capo_Sentinella(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/get_Capo_Sentinella");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Sentinella"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_ident_nome, p_passaporto, p_codice_ueln});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_Capo_Sentinella_STR(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[43]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/get_Capo_Sentinella_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Sentinella_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_ident_nome, p_passaporto, p_codice_ueln});

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

    public it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult get_Capo_Equino_Macellato(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[44]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/get_Capo_Equino_Macellato");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Equino_Macellato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_passaporto, p_codice_ueln});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_Capo_Equino_Macellato_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[45]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/get_Capo_Equino_Macellato_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "get_Capo_Equino_Macellato_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_passaporto, p_codice_ueln});

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

    public it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_Macellati(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[46]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/find_Capi_Equini_Macellati");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "find_Capi_Equini_Macellati"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_passaporto, p_codice_ueln});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String find_Capi_Equini_Macellati_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[47]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/find_Capi_Equini_Macellati_STR");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "find_Capi_Equini_Macellati_STR"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_codice_elettronico, p_passaporto, p_codice_ueln});

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

    public it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_Ovini(java.lang.String p_capo_codice) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[48]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://bdr.izs.it/webservices/getCanc_Amm_Ovini");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "getCanc_Amm_Ovini"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {p_capo_codice});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult) org.apache.axis.utils.JavaUtils.convert(_resp, it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
