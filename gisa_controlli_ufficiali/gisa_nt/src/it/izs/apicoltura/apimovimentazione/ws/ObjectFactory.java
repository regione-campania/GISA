/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apimovimentazione.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.izs.apicoltura.apimovimentazione.ws package. 
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

    private final static QName _SearchApimodmovByPkResponse_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "searchApimodmovByPkResponse");
    private final static QName _SearchApimodmovByPk_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "searchApimodmovByPk");
    private final static QName _DeleteApimodmov_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "deleteApimodmov");
    private final static QName _InsertApimodmov_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "insertApimodmov");
    private final static QName _DeleteApimodmovResponse_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "deleteApimodmovResponse");
    private final static QName _UpdateApimodmovResponse_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "updateApimodmovResponse");
    private final static QName _SOAPAutenticazioneWS_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "SOAPAutenticazioneWS");
    private final static QName _SearchApimodmov_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "searchApimodmov");
    private final static QName _UpdateApimodmov_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "updateApimodmov");
    private final static QName _InsertApimodmovResponse_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "insertApimodmovResponse");
    private final static QName _SearchApimodmovResponse_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "searchApimodmovResponse");
    private final static QName _BusinessFault_QNAME = new QName("http://ws.apimovimentazione.apicoltura.izs.it/", "BusinessFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.izs.apicoltura.apimovimentazione.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Apimodmov }
     * 
     */
    public Apimodmov createApimodmov() {
        return new Apimodmov();
    }

    /**
     * Create an instance of {@link SOAPAutenticazioneWS }
     * 
     */
    public SOAPAutenticazioneWS createSOAPAutenticazioneWS() {
        return new SOAPAutenticazioneWS();
    }

    /**
     * Create an instance of {@link SearchApimodmov }
     * 
     */
    public SearchApimodmov createSearchApimodmov() {
        return new SearchApimodmov();
    }

    /**
     * Create an instance of {@link InsertApimodmovResponse }
     * 
     */
    public InsertApimodmovResponse createInsertApimodmovResponse() {
        return new InsertApimodmovResponse();
    }

    /**
     * Create an instance of {@link SearchApimodmovResponse }
     * 
     */
    public SearchApimodmovResponse createSearchApimodmovResponse() {
        return new SearchApimodmovResponse();
    }

    /**
     * Create an instance of {@link UpdateApimodmov }
     * 
     */
    public UpdateApimodmov createUpdateApimodmov() {
        return new UpdateApimodmov();
    }

    /**
     * Create an instance of {@link BusinessWsException }
     * 
     */
    public BusinessWsException createBusinessWsException() {
        return new BusinessWsException();
    }

    /**
     * Create an instance of {@link SearchApimodmovByPk }
     * 
     */
    public SearchApimodmovByPk createSearchApimodmovByPk() {
        return new SearchApimodmovByPk();
    }

    /**
     * Create an instance of {@link SearchApimodmovByPkResponse }
     * 
     */
    public SearchApimodmovByPkResponse createSearchApimodmovByPkResponse() {
        return new SearchApimodmovByPkResponse();
    }

    /**
     * Create an instance of {@link DeleteApimodmov }
     * 
     */
    public DeleteApimodmov createDeleteApimodmov() {
        return new DeleteApimodmov();
    }

    /**
     * Create an instance of {@link InsertApimodmov }
     * 
     */
    public InsertApimodmov createInsertApimodmov() {
        return new InsertApimodmov();
    }

    /**
     * Create an instance of {@link DeleteApimodmovResponse }
     * 
     */
    public DeleteApimodmovResponse createDeleteApimodmovResponse() {
        return new DeleteApimodmovResponse();
    }

    /**
     * Create an instance of {@link UpdateApimodmovResponse }
     * 
     */
    public UpdateApimodmovResponse createUpdateApimodmovResponse() {
        return new UpdateApimodmovResponse();
    }

    /**
     * Create an instance of {@link Apidetmod }
     * 
     */
    public Apidetmod createApidetmod() {
        return new Apidetmod();
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
     * Create an instance of {@link Apimodmov.ListaDettaglioModello }
     * 
     */
    public Apimodmov.ListaDettaglioModello createApimodmovListaDettaglioModello() {
        return new Apimodmov.ListaDettaglioModello();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApimodmovByPkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "searchApimodmovByPkResponse")
    public JAXBElement<SearchApimodmovByPkResponse> createSearchApimodmovByPkResponse(SearchApimodmovByPkResponse value) {
        return new JAXBElement<SearchApimodmovByPkResponse>(_SearchApimodmovByPkResponse_QNAME, SearchApimodmovByPkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApimodmovByPk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "searchApimodmovByPk")
    public JAXBElement<SearchApimodmovByPk> createSearchApimodmovByPk(SearchApimodmovByPk value) {
        return new JAXBElement<SearchApimodmovByPk>(_SearchApimodmovByPk_QNAME, SearchApimodmovByPk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApimodmov }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "deleteApimodmov")
    public JAXBElement<DeleteApimodmov> createDeleteApimodmov(DeleteApimodmov value) {
        return new JAXBElement<DeleteApimodmov>(_DeleteApimodmov_QNAME, DeleteApimodmov.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApimodmov }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "insertApimodmov")
    public JAXBElement<InsertApimodmov> createInsertApimodmov(InsertApimodmov value) {
        return new JAXBElement<InsertApimodmov>(_InsertApimodmov_QNAME, InsertApimodmov.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApimodmovResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "deleteApimodmovResponse")
    public JAXBElement<DeleteApimodmovResponse> createDeleteApimodmovResponse(DeleteApimodmovResponse value) {
        return new JAXBElement<DeleteApimodmovResponse>(_DeleteApimodmovResponse_QNAME, DeleteApimodmovResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApimodmovResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "updateApimodmovResponse")
    public JAXBElement<UpdateApimodmovResponse> createUpdateApimodmovResponse(UpdateApimodmovResponse value) {
        return new JAXBElement<UpdateApimodmovResponse>(_UpdateApimodmovResponse_QNAME, UpdateApimodmovResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPAutenticazioneWS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "SOAPAutenticazioneWS")
    public JAXBElement<SOAPAutenticazioneWS> createSOAPAutenticazioneWS(SOAPAutenticazioneWS value) {
        return new JAXBElement<SOAPAutenticazioneWS>(_SOAPAutenticazioneWS_QNAME, SOAPAutenticazioneWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApimodmov }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "searchApimodmov")
    public JAXBElement<SearchApimodmov> createSearchApimodmov(SearchApimodmov value) {
        return new JAXBElement<SearchApimodmov>(_SearchApimodmov_QNAME, SearchApimodmov.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApimodmov }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "updateApimodmov")
    public JAXBElement<UpdateApimodmov> createUpdateApimodmov(UpdateApimodmov value) {
        return new JAXBElement<UpdateApimodmov>(_UpdateApimodmov_QNAME, UpdateApimodmov.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApimodmovResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "insertApimodmovResponse")
    public JAXBElement<InsertApimodmovResponse> createInsertApimodmovResponse(InsertApimodmovResponse value) {
        return new JAXBElement<InsertApimodmovResponse>(_InsertApimodmovResponse_QNAME, InsertApimodmovResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApimodmovResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "searchApimodmovResponse")
    public JAXBElement<SearchApimodmovResponse> createSearchApimodmovResponse(SearchApimodmovResponse value) {
        return new JAXBElement<SearchApimodmovResponse>(_SearchApimodmovResponse_QNAME, SearchApimodmovResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apimovimentazione.apicoltura.izs.it/", name = "BusinessFault")
    public JAXBElement<BusinessWsException> createBusinessFault(BusinessWsException value) {
        return new JAXBElement<BusinessWsException>(_BusinessFault_QNAME, BusinessWsException.class, null, value);
    }

}
