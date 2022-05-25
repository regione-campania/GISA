/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
 
package it.izs.apicoltura.apianagraficaattivita.ws;

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
@WebServiceClient(name = "ApiubiEndpointService", targetNamespace = "http://ws.apianagrafica.apicoltura.izs.it/", wsdlLocation = ConfiguratoreServizi.END_POINT+"ws/apicoltura/apianagrafica/storicoubicazione?wsdl")
public class ApiubiEndpointService
    extends Service
{

    private final static URL APIUBIENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException APIUBIENDPOINTSERVICE_EXCEPTION;
    private final static QName APIUBIENDPOINTSERVICE_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApiubiEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
        	if(ApplicationProperties.getProperty("recupero_wsdl_locale").equals("true"))
        		url = new URL(ApplicationProperties.getProperty("END_POINT_BDAPI")+ ApplicationProperties.getProperty("END_POINT_BDAPI_LOCALE_UBICAZIONE"));
        	else
        		url =  new URL(ApplicationProperties.getProperty("END_POINT_BDAPI")+"ws/apicoltura/apianagrafica/storicoubicazione?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        APIUBIENDPOINTSERVICE_WSDL_LOCATION = url;
        APIUBIENDPOINTSERVICE_EXCEPTION = e;
    }

    public ApiubiEndpointService() {
        super(__getWsdlLocation(), APIUBIENDPOINTSERVICE_QNAME);
    }

    public ApiubiEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), APIUBIENDPOINTSERVICE_QNAME, features);
    }

    public ApiubiEndpointService(URL wsdlLocation) {
        super(wsdlLocation, APIUBIENDPOINTSERVICE_QNAME);
    }

    public ApiubiEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, APIUBIENDPOINTSERVICE_QNAME, features);
    }

    public ApiubiEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ApiubiEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ApiubiEndpoint
     */
    @WebEndpoint(name = "ApiubiEndpointPort")
    public ApiubiEndpoint getApiubiEndpointPort() {
        return super.getPort(new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApiubiEndpointPort"), ApiubiEndpoint.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ApiubiEndpoint
     */
    @WebEndpoint(name = "ApiubiEndpointPort")
    public ApiubiEndpoint getApiubiEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.apianagrafica.apicoltura.izs.it/", "ApiubiEndpointPort"), ApiubiEndpoint.class, features);
    }

    private static URL __getWsdlLocation() {
        if (APIUBIENDPOINTSERVICE_EXCEPTION!= null) {
            throw APIUBIENDPOINTSERVICE_EXCEPTION;
        }
        System.out.println("##API WSDL LOCATION -->"+APIUBIENDPOINTSERVICE_WSDL_LOCATION);
        return APIUBIENDPOINTSERVICE_WSDL_LOCATION;
    }

}
