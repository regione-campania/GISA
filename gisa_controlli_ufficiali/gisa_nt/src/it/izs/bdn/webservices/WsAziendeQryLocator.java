/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsAziendeQryLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

import org.aspcfs.modules.util.imports.ApplicationProperties;

public class WsAziendeQryLocator extends org.apache.axis.client.Service implements it.izs.bdn.webservices.WsAziendeQry {

/**
 * Estrazione dati relativi alle aziende.
 */

    public WsAziendeQryLocator() {
    }


    public WsAziendeQryLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WsAziendeQryLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    
    // Use to get a proxy class for wsAziendeQrySoap
    private java.lang.String wsAziendeQrySoap_address = ApplicationProperties.getProperty("END_POINT_AZIENDE_BDN");
    //private java.lang.String wsAziendeQrySoap_address = "http://bdrtest.izs.it/wsBDNInterrogazioni/wsAziendeQry.asmx";

    public java.lang.String getwsAziendeQrySoapAddress() {
        return wsAziendeQrySoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wsAziendeQrySoapWSDDServiceName = "wsAziendeQrySoap";

    public java.lang.String getwsAziendeQrySoapWSDDServiceName() {
        return wsAziendeQrySoapWSDDServiceName;
    }

    public void setwsAziendeQrySoapWSDDServiceName(java.lang.String name) {
        wsAziendeQrySoapWSDDServiceName = name;
    }

    public it.izs.bdn.webservices.WsAziendeQrySoap getwsAziendeQrySoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	System.out.println("WSDL ALLEVAMENTI 2 --> "+wsAziendeQrySoap_address);
            endpoint = new java.net.URL(wsAziendeQrySoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwsAziendeQrySoap(endpoint);
    }

    public it.izs.bdn.webservices.WsAziendeQrySoap getwsAziendeQrySoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.izs.bdn.webservices.WsAziendeQrySoapStub _stub = new it.izs.bdn.webservices.WsAziendeQrySoapStub(portAddress, this);
            _stub.setPortName(getwsAziendeQrySoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwsAziendeQrySoapEndpointAddress(java.lang.String address) {
        wsAziendeQrySoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.izs.bdn.webservices.WsAziendeQrySoap.class.isAssignableFrom(serviceEndpointInterface)) {
                it.izs.bdn.webservices.WsAziendeQrySoapStub _stub = new it.izs.bdn.webservices.WsAziendeQrySoapStub(new java.net.URL(wsAziendeQrySoap_address), this);
                _stub.setPortName(getwsAziendeQrySoapWSDDServiceName());
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
        if ("wsAziendeQrySoap".equals(inputPortName)) {
            return getwsAziendeQrySoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "wsAziendeQry");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bdr.izs.it/webservices", "wsAziendeQrySoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("wsAziendeQrySoap".equals(portName)) {
            setwsAziendeQrySoapEndpointAddress(address);
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
