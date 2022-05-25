/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsPianoIBRLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.aspcfs.modules.izsmibr.service;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.sun.glass.ui.Application;

public class WsPianoIBRLocator extends org.apache.axis.client.Service implements org.aspcfs.modules.izsmibr.service.WsPianoIBR {

/**
 * Registraziopne e gestione degli esiti dei campionamenti per i controlli
 * IBR sui capi bovini
 */

    public WsPianoIBRLocator() {
    }


    public WsPianoIBRLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WsPianoIBRLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for wsPianoIBRSoap
    private java.lang.String wsPianoIBRSoap_address = ApplicationProperties.getProperty("END_POINT_IBR_BDN");

    public java.lang.String getwsPianoIBRSoapAddress() {
        return wsPianoIBRSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wsPianoIBRSoapWSDDServiceName = "wsPianoIBRSoap";

    public java.lang.String getwsPianoIBRSoapWSDDServiceName() {
        return wsPianoIBRSoapWSDDServiceName;
    }

    public void setwsPianoIBRSoapWSDDServiceName(java.lang.String name) {
        wsPianoIBRSoapWSDDServiceName = name;
    }

    public org.aspcfs.modules.izsmibr.service.WsPianoIBRSoap getwsPianoIBRSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wsPianoIBRSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwsPianoIBRSoap(endpoint);
    }

    public org.aspcfs.modules.izsmibr.service.WsPianoIBRSoap getwsPianoIBRSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.aspcfs.modules.izsmibr.service.WsPianoIBRSoapStub _stub = new org.aspcfs.modules.izsmibr.service.WsPianoIBRSoapStub(portAddress, this);
            _stub.setPortName(getwsPianoIBRSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwsPianoIBRSoapEndpointAddress(java.lang.String address) {
        wsPianoIBRSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.aspcfs.modules.izsmibr.service.WsPianoIBRSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.aspcfs.modules.izsmibr.service.WsPianoIBRSoapStub _stub = new org.aspcfs.modules.izsmibr.service.WsPianoIBRSoapStub(new java.net.URL(wsPianoIBRSoap_address), this);
                _stub.setPortName(getwsPianoIBRSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("wsPianoIBRSoap".equals(inputPortName)) {
            return getwsPianoIBRSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "wsPianoIBR");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "wsPianoIBRSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("wsPianoIBRSoap".equals(portName)) {
            setwsPianoIBRSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
