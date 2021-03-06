/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.anagrafica.ws;

import it.izs.apicoltura.apianagraficaattivita.ws.ConfiguratoreServizi;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import org.aspcfs.modules.util.imports.ApplicationProperties;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ApistoproEndpointService", targetNamespace = "http://ws.apianagrafica.apicoltura.izs.it/", wsdlLocation = ConfiguratoreServizi.END_POINT+"ws/apicoltura/apianagrafica/storicoproprietario?wsdl")
public class ApistoproEndpointService
    extends Service
{

    private final static URL ApistoproENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException ApistoproENDPOINTSERVICE_EXCEPTION;
    private final static QName ApistoproENDPOINTSERVICE_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApistoproEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(ApplicationProperties.getProperty("END_POINT_BDAPI")+"ws/apicoltura/apianagrafica/storicoproprietario?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ApistoproENDPOINTSERVICE_WSDL_LOCATION = url;
        ApistoproENDPOINTSERVICE_EXCEPTION = e;
    }

    public ApistoproEndpointService() {
        super(__getWsdlLocation(), ApistoproENDPOINTSERVICE_QNAME);
    }

    public ApistoproEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ApistoproENDPOINTSERVICE_QNAME, features);
    }

    public ApistoproEndpointService(URL wsdlLocation) {
        super(wsdlLocation, ApistoproENDPOINTSERVICE_QNAME);
    }

    public ApistoproEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ApistoproENDPOINTSERVICE_QNAME, features);
    }

    public ApistoproEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ApistoproEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PersoneEndpoint
     */
    @WebEndpoint(name = "ApistoproEndpointPort")
    public ApistoproEndpoint getApistoproEndpointPort() {
        return super.getPort(new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApistoproEndpointPort"), ApistoproEndpoint.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PersoneEndpoint
     */
    @WebEndpoint(name = "ApistoproEndpointPort")
    public ApistoproEndpoint getApistoproEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApistoproEndpointPort"), ApistoproEndpoint.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ApistoproENDPOINTSERVICE_EXCEPTION!= null) {
            throw ApistoproENDPOINTSERVICE_EXCEPTION;
        }
        System.out.println("##API WSDL LOCATION -->"+ApistoproENDPOINTSERVICE_WSDL_LOCATION);
        return ApistoproENDPOINTSERVICE_WSDL_LOCATION;
    }

}
