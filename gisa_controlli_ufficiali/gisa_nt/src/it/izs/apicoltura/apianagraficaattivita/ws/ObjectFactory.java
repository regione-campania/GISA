/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaattivita.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.izs.apicoltura.apianagraficaattivita.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteApiAttivita_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "deleteApiAttivita");
    private final static QName _UpdateApiAttivita_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "updateApiAttivita");
    private final static QName _BusinessFault_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "BusinessFault");
    private final static QName _DeleteApiAttivitaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "deleteApiAttivitaResponse");
    private final static QName _SearchApiAttivita_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAttivita");
    private final static QName _SearchApiAttivitaByPk_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAttivitaByPk");
    private final static QName _SearchApiAttivitaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAttivitaResponse");
    private final static QName _SOAPAutenticazioneWS_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "SOAPAutenticazioneWS");
    private final static QName _InsertApiAttivita_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "insertApiAttivita");
    private final static QName _InsertApiAttivitaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "insertApiAttivitaResponse");
    private final static QName _UpdateApiAttivitaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "updateApiAttivitaResponse");
    private final static QName _SearchApiAttivitaByPkResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAttivitaByPkResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.izs.apicoltura.apianagraficaattivita.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchApiAttivitaResponse }
     * 
     */
    public SearchApiAttivitaResponse createSearchApiAttivitaResponse() {
        return new SearchApiAttivitaResponse();
    }

    /**
     * Create an instance of {@link SOAPAutenticazioneWS }
     * 
     */
    public SOAPAutenticazioneWS createSOAPAutenticazioneWS() {
        return new SOAPAutenticazioneWS();
    }

    /**
     * Create an instance of {@link DeleteApiAttivita }
     * 
     */
    public DeleteApiAttivita createDeleteApiAttivita() {
        return new DeleteApiAttivita();
    }

    /**
     * Create an instance of {@link UpdateApiAttivita }
     * 
     */
    public UpdateApiAttivita createUpdateApiAttivita() {
        return new UpdateApiAttivita();
    }

    /**
     * Create an instance of {@link BusinessWsException }
     * 
     */
    public BusinessWsException createBusinessWsException() {
        return new BusinessWsException();
    }

    /**
     * Create an instance of {@link DeleteApiAttivitaResponse }
     * 
     */
    public DeleteApiAttivitaResponse createDeleteApiAttivitaResponse() {
        return new DeleteApiAttivitaResponse();
    }

    /**
     * Create an instance of {@link SearchApiAttivita }
     * 
     */
    public SearchApiAttivita createSearchApiAttivita() {
        return new SearchApiAttivita();
    }

    /**
     * Create an instance of {@link SearchApiAttivitaByPk }
     * 
     */
    public SearchApiAttivitaByPk createSearchApiAttivitaByPk() {
        return new SearchApiAttivitaByPk();
    }

    /**
     * Create an instance of {@link InsertApiAttivitaResponse }
     * 
     */
    public InsertApiAttivitaResponse createInsertApiAttivitaResponse() {
        return new InsertApiAttivitaResponse();
    }

    /**
     * Create an instance of {@link UpdateApiAttivitaResponse }
     * 
     */
    public UpdateApiAttivitaResponse createUpdateApiAttivitaResponse() {
        return new UpdateApiAttivitaResponse();
    }

    /**
     * Create an instance of {@link SearchApiAttivitaByPkResponse }
     * 
     */
    public SearchApiAttivitaByPkResponse createSearchApiAttivitaByPkResponse() {
        return new SearchApiAttivitaByPkResponse();
    }

    /**
     * Create an instance of {@link InsertApiAttivita }
     * 
     */
    public InsertApiAttivita createInsertApiAttivita() {
        return new InsertApiAttivita();
    }

    /**
     * Create an instance of {@link Apiatt }
     * 
     */
    public Apiatt createApiatt() {
        return new Apiatt();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link FieldError }
     * 
     */
    public FieldError createFieldError() {
        return new FieldError();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApiAttivita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "deleteApiAttivita")
    public JAXBElement<DeleteApiAttivita> createDeleteApiAttivita(DeleteApiAttivita value) {
        return new JAXBElement<DeleteApiAttivita>(_DeleteApiAttivita_QNAME, DeleteApiAttivita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApiAttivita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "updateApiAttivita")
    public JAXBElement<UpdateApiAttivita> createUpdateApiAttivita(UpdateApiAttivita value) {
        return new JAXBElement<UpdateApiAttivita>(_UpdateApiAttivita_QNAME, UpdateApiAttivita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "BusinessFault")
    public JAXBElement<BusinessWsException> createBusinessFault(BusinessWsException value) {
        return new JAXBElement<BusinessWsException>(_BusinessFault_QNAME, BusinessWsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApiAttivitaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "deleteApiAttivitaResponse")
    public JAXBElement<DeleteApiAttivitaResponse> createDeleteApiAttivitaResponse(DeleteApiAttivitaResponse value) {
        return new JAXBElement<DeleteApiAttivitaResponse>(_DeleteApiAttivitaResponse_QNAME, DeleteApiAttivitaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAttivita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAttivita")
    public JAXBElement<SearchApiAttivita> createSearchApiAttivita(SearchApiAttivita value) {
        return new JAXBElement<SearchApiAttivita>(_SearchApiAttivita_QNAME, SearchApiAttivita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAttivitaByPk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAttivitaByPk")
    public JAXBElement<SearchApiAttivitaByPk> createSearchApiAttivitaByPk(SearchApiAttivitaByPk value) {
        return new JAXBElement<SearchApiAttivitaByPk>(_SearchApiAttivitaByPk_QNAME, SearchApiAttivitaByPk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAttivitaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAttivitaResponse")
    public JAXBElement<SearchApiAttivitaResponse> createSearchApiAttivitaResponse(SearchApiAttivitaResponse value) {
        return new JAXBElement<SearchApiAttivitaResponse>(_SearchApiAttivitaResponse_QNAME, SearchApiAttivitaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPAutenticazioneWS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "SOAPAutenticazioneWS")
    public JAXBElement<SOAPAutenticazioneWS> createSOAPAutenticazioneWS(SOAPAutenticazioneWS value) {
        return new JAXBElement<SOAPAutenticazioneWS>(_SOAPAutenticazioneWS_QNAME, SOAPAutenticazioneWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApiAttivita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "insertApiAttivita")
    public JAXBElement<InsertApiAttivita> createInsertApiAttivita(InsertApiAttivita value) {
        return new JAXBElement<InsertApiAttivita>(_InsertApiAttivita_QNAME, InsertApiAttivita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApiAttivitaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "insertApiAttivitaResponse")
    public JAXBElement<InsertApiAttivitaResponse> createInsertApiAttivitaResponse(InsertApiAttivitaResponse value) {
        return new JAXBElement<InsertApiAttivitaResponse>(_InsertApiAttivitaResponse_QNAME, InsertApiAttivitaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApiAttivitaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "updateApiAttivitaResponse")
    public JAXBElement<UpdateApiAttivitaResponse> createUpdateApiAttivitaResponse(UpdateApiAttivitaResponse value) {
        return new JAXBElement<UpdateApiAttivitaResponse>(_UpdateApiAttivitaResponse_QNAME, UpdateApiAttivitaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAttivitaByPkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAttivitaByPkResponse")
    public JAXBElement<SearchApiAttivitaByPkResponse> createSearchApiAttivitaByPkResponse(SearchApiAttivitaByPkResponse value) {
        return new JAXBElement<SearchApiAttivitaByPkResponse>(_SearchApiAttivitaByPkResponse_QNAME, SearchApiAttivitaByPkResponse.class, null, value);
    }

}
