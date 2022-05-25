/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.izs.bdn.anagrafica.ws package. 
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

    private final static QName _UpdatePersone_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "updatePersone");
    private final static QName _SOAPAutenticazioneWS_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "SOAPAutenticazioneWS");
    private final static QName _SearchPersoneResponse_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "searchPersoneResponse");
    private final static QName _BusinessFault_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "BusinessFault");
    private final static QName _SearchPersoneByPkResponse_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "searchPersoneByPkResponse");
    private final static QName _InsertPersoneResponse_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "insertPersoneResponse");
    private final static QName _InsertPersone_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "insertPersone");
    private final static QName _SearchPersoneByPk_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "searchPersoneByPk");
    private final static QName _UpdatePersoneResponse_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "updatePersoneResponse");
    private final static QName _DeletePersoneResponse_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "deletePersoneResponse");
    private final static QName _DeletePersone_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "deletePersone");
    private final static QName _SearchPersone_QNAME = new QName("http://ws.anagrafica.bdn.izs.it/", "searchPersone");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.izs.bdn.anagrafica.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchPersoneResponse }
     * 
     */
    public SearchPersoneResponse createSearchPersoneResponse() {
        return new SearchPersoneResponse();
    }

    /**
     * Create an instance of {@link UpdatePersone }
     * 
     */
    public UpdatePersone createUpdatePersone() {
        return new UpdatePersone();
    }

    /**
     * Create an instance of {@link SOAPAutenticazioneWS }
     * 
     */
    public SOAPAutenticazioneWS createSOAPAutenticazioneWS() {
        return new SOAPAutenticazioneWS();
    }

    /**
     * Create an instance of {@link InsertPersoneResponse }
     * 
     */
    public InsertPersoneResponse createInsertPersoneResponse() {
        return new InsertPersoneResponse();
    }

    /**
     * Create an instance of {@link InsertPersone }
     * 
     */
    public InsertPersone createInsertPersone() {
        return new InsertPersone();
    }

    /**
     * Create an instance of {@link BusinessWsException }
     * 
     */
    public BusinessWsException createBusinessWsException() {
        return new BusinessWsException();
    }

    /**
     * Create an instance of {@link SearchPersoneByPkResponse }
     * 
     */
    public SearchPersoneByPkResponse createSearchPersoneByPkResponse() {
        return new SearchPersoneByPkResponse();
    }

    /**
     * Create an instance of {@link SearchPersoneByPk }
     * 
     */
    public SearchPersoneByPk createSearchPersoneByPk() {
        return new SearchPersoneByPk();
    }

    /**
     * Create an instance of {@link UpdatePersoneResponse }
     * 
     */
    public UpdatePersoneResponse createUpdatePersoneResponse() {
        return new UpdatePersoneResponse();
    }

    /**
     * Create an instance of {@link DeletePersoneResponse }
     * 
     */
    public DeletePersoneResponse createDeletePersoneResponse() {
        return new DeletePersoneResponse();
    }

    /**
     * Create an instance of {@link DeletePersone }
     * 
     */
    public DeletePersone createDeletePersone() {
        return new DeletePersone();
    }

    /**
     * Create an instance of {@link SearchPersone }
     * 
     */
    public SearchPersone createSearchPersone() {
        return new SearchPersone();
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
     * Create an instance of {@link Persone }
     * 
     */
    public Persone createPersone() {
        return new Persone();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "updatePersone")
    public JAXBElement<UpdatePersone> createUpdatePersone(UpdatePersone value) {
        return new JAXBElement<UpdatePersone>(_UpdatePersone_QNAME, UpdatePersone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPAutenticazioneWS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "SOAPAutenticazioneWS")
    public JAXBElement<SOAPAutenticazioneWS> createSOAPAutenticazioneWS(SOAPAutenticazioneWS value) {
        return new JAXBElement<SOAPAutenticazioneWS>(_SOAPAutenticazioneWS_QNAME, SOAPAutenticazioneWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "searchPersoneResponse")
    public JAXBElement<SearchPersoneResponse> createSearchPersoneResponse(SearchPersoneResponse value) {
        return new JAXBElement<SearchPersoneResponse>(_SearchPersoneResponse_QNAME, SearchPersoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "BusinessFault")
    public JAXBElement<BusinessWsException> createBusinessFault(BusinessWsException value) {
        return new JAXBElement<BusinessWsException>(_BusinessFault_QNAME, BusinessWsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersoneByPkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "searchPersoneByPkResponse")
    public JAXBElement<SearchPersoneByPkResponse> createSearchPersoneByPkResponse(SearchPersoneByPkResponse value) {
        return new JAXBElement<SearchPersoneByPkResponse>(_SearchPersoneByPkResponse_QNAME, SearchPersoneByPkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertPersoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "insertPersoneResponse")
    public JAXBElement<InsertPersoneResponse> createInsertPersoneResponse(InsertPersoneResponse value) {
        return new JAXBElement<InsertPersoneResponse>(_InsertPersoneResponse_QNAME, InsertPersoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertPersone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "insertPersone")
    public JAXBElement<InsertPersone> createInsertPersone(InsertPersone value) {
        return new JAXBElement<InsertPersone>(_InsertPersone_QNAME, InsertPersone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersoneByPk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "searchPersoneByPk")
    public JAXBElement<SearchPersoneByPk> createSearchPersoneByPk(SearchPersoneByPk value) {
        return new JAXBElement<SearchPersoneByPk>(_SearchPersoneByPk_QNAME, SearchPersoneByPk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "updatePersoneResponse")
    public JAXBElement<UpdatePersoneResponse> createUpdatePersoneResponse(UpdatePersoneResponse value) {
        return new JAXBElement<UpdatePersoneResponse>(_UpdatePersoneResponse_QNAME, UpdatePersoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "deletePersoneResponse")
    public JAXBElement<DeletePersoneResponse> createDeletePersoneResponse(DeletePersoneResponse value) {
        return new JAXBElement<DeletePersoneResponse>(_DeletePersoneResponse_QNAME, DeletePersoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "deletePersone")
    public JAXBElement<DeletePersone> createDeletePersone(DeletePersone value) {
        return new JAXBElement<DeletePersone>(_DeletePersone_QNAME, DeletePersone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.anagrafica.bdn.izs.it/", name = "searchPersone")
    public JAXBElement<SearchPersone> createSearchPersone(SearchPersone value) {
        return new JAXBElement<SearchPersone>(_SearchPersone_QNAME, SearchPersone.class, null, value);
    }

}
