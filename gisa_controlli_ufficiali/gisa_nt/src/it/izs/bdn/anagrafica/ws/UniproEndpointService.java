/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "UniproEndpointService", targetNamespace = "http://ws.anagrafica.bdn.izs.it/", wsdlLocation = "https://ws.izs.it/j6_bdn/ws/anagrafica/unitaproduttiva?wsdl")
public class UniproEndpointService
    extends Service
{

    private final static URL UNIPROENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException UNIPROENDPOINTSERVICE_EXCEPTION;
    private final static QName UNIPROENDPOINTSERVICE_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "UniproEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://ws.izs.it/j6_bdn/ws/anagrafica/unitaproduttiva?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        UNIPROENDPOINTSERVICE_WSDL_LOCATION = url;
        UNIPROENDPOINTSERVICE_EXCEPTION = e;
    }

    public UniproEndpointService() {
        super(__getWsdlLocation(), UNIPROENDPOINTSERVICE_QNAME);
    }

    public UniproEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), UNIPROENDPOINTSERVICE_QNAME, features);
    }

    public UniproEndpointService(URL wsdlLocation) {
        super(wsdlLocation, UNIPROENDPOINTSERVICE_QNAME);
    }

    public UniproEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, UNIPROENDPOINTSERVICE_QNAME, features);
    }

    public UniproEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UniproEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns UniproEndpoint
     */
    @WebEndpoint(name = "UniproEndpointPort")
    public UniproEndpoint getUniproEndpointPort() {
        return super.getPort(new QName("http://ws.anagrafica.bdn.izs.it/", "UniproEndpointPort"), UniproEndpoint.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UniproEndpoint
     */
    @WebEndpoint(name = "UniproEndpointPort")
    public UniproEndpoint getUniproEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.anagrafica.bdn.izs.it/", "UniproEndpointPort"), UniproEndpoint.class, features);
    }

    private static URL __getWsdlLocation() {
        if (UNIPROENDPOINTSERVICE_EXCEPTION!= null) {
            throw UNIPROENDPOINTSERVICE_EXCEPTION;
        }
        return UNIPROENDPOINTSERVICE_WSDL_LOCATION;
    }

}
