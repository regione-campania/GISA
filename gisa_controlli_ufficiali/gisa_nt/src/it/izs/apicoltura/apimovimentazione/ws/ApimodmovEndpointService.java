/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apimovimentazione.ws;

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
@WebServiceClient(name = "ApimodmovEndpointService", targetNamespace = "http://ws.apimovimentazione.apicoltura.izs.it/", wsdlLocation = ConfiguratoreServizi.END_POINT+"ws/apicoltura/apimovimentazione/modello?wsdl")
public class ApimodmovEndpointService
    extends Service
{

    private final static URL APIMODMOVENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException APIMODMOVENDPOINTSERVICE_EXCEPTION;
    private final static QName APIMODMOVENDPOINTSERVICE_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "ApimodmovEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
        	if(ApplicationProperties.getProperty("recupero_wsdl_locale").equals("true"))
        		url = new URL(ApplicationProperties.getProperty("END_POINT_BDAPI")+ ApplicationProperties.getProperty("END_POINT_BDAPI_LOCALE_MOVIMENTAZIONE"));
        	else
        		url =  new URL(ApplicationProperties.getProperty("END_POINT_BDAPI")+ "ws/apicoltura/apimovimentazione/modello?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        APIMODMOVENDPOINTSERVICE_WSDL_LOCATION = url;
        APIMODMOVENDPOINTSERVICE_EXCEPTION = e;
    }

    public ApimodmovEndpointService() {
        super(__getWsdlLocation(), APIMODMOVENDPOINTSERVICE_QNAME);
    }

    public ApimodmovEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), APIMODMOVENDPOINTSERVICE_QNAME, features);
    }

    public ApimodmovEndpointService(URL wsdlLocation) {
        super(wsdlLocation, APIMODMOVENDPOINTSERVICE_QNAME);
    }

    public ApimodmovEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, APIMODMOVENDPOINTSERVICE_QNAME, features);
    }

    public ApimodmovEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ApimodmovEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ApimodmovEndpoint
     */
    @WebEndpoint(name = "ApimodmovEndpointPort")
    public ApimodmovEndpoint getApimodmovEndpointPort() {
        return super.getPort(new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "ApimodmovEndpointPort"), ApimodmovEndpoint.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ApimodmovEndpoint
     */
    @WebEndpoint(name = "ApimodmovEndpointPort")
    public ApimodmovEndpoint getApimodmovEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "ApimodmovEndpointPort"), ApimodmovEndpoint.class, features);
    }

    private static URL __getWsdlLocation() {
        if (APIMODMOVENDPOINTSERVICE_EXCEPTION!= null) {
            throw APIMODMOVENDPOINTSERVICE_EXCEPTION;
        }
        System.out.println("##API WSDL LOCATION -->"+APIMODMOVENDPOINTSERVICE_WSDL_LOCATION);
        return APIMODMOVENDPOINTSERVICE_WSDL_LOCATION;
    }

}
