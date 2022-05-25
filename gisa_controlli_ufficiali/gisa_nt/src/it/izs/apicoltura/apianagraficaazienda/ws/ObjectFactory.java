/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apianagraficaazienda.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.izs.apicoltura.apianagrafica.ws package. 
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

    private final static QName _SearchApiAziendaByPkResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAziendaByPkResponse");
    private final static QName _ElencoCodiciDisponibiliResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "elencoCodiciDisponibiliResponse");
    private final static QName _SearchApiAzienda_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAzienda");
    private final static QName _BusinessFault_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "BusinessFault");
    private final static QName _SOAPAutenticazioneWS_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "SOAPAutenticazioneWS");
    private final static QName _ElencoCodiciAssegnatiResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "elencoCodiciAssegnatiResponse");
    private final static QName _InsertApiAzienda_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "insertApiAzienda");
    private final static QName _ElencoCodiciAssegnati_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "elencoCodiciAssegnati");
    private final static QName _UpdateApiAziendaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "updateApiAziendaResponse");
    private final static QName _ElencoCodiciDisponibili_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "elencoCodiciDisponibili");
    private final static QName _DeleteApiAziendaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "deleteApiAziendaResponse");
    private final static QName _InsertApiAziendaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "insertApiAziendaResponse");
    private final static QName _UpdateApiAzienda_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "updateApiAzienda");
    private final static QName _SearchApiAziendaByPk_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAziendaByPk");
    private final static QName _DeleteApiAzienda_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "deleteApiAzienda");
    private final static QName _SearchApiAziendaResponse_QNAME = new QName("http://ws.apianagrafica.apicoltura.izs.it/", "searchApiAziendaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.izs.apicoltura.apianagrafica.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SOAPAutenticazioneWS }
     * 
     */
    public SOAPAutenticazioneWS createSOAPAutenticazioneWS() {
        return new SOAPAutenticazioneWS();
    }

    /**
     * Create an instance of {@link SearchApiAziendaByPkResponse }
     * 
     */
    public SearchApiAziendaByPkResponse createSearchApiAziendaByPkResponse() {
        return new SearchApiAziendaByPkResponse();
    }

    /**
     * Create an instance of {@link ElencoCodiciDisponibiliResponse }
     * 
     */
    public ElencoCodiciDisponibiliResponse createElencoCodiciDisponibiliResponse() {
        return new ElencoCodiciDisponibiliResponse();
    }

    /**
     * Create an instance of {@link SearchApiAzienda }
     * 
     */
    public SearchApiAzienda createSearchApiAzienda() {
        return new SearchApiAzienda();
    }

    /**
     * Create an instance of {@link BusinessWsException }
     * 
     */
    public BusinessWsException createBusinessWsException() {
        return new BusinessWsException();
    }

    /**
     * Create an instance of {@link ElencoCodiciAssegnatiResponse }
     * 
     */
    public ElencoCodiciAssegnatiResponse createElencoCodiciAssegnatiResponse() {
        return new ElencoCodiciAssegnatiResponse();
    }

    /**
     * Create an instance of {@link InsertApiAzienda }
     * 
     */
    public InsertApiAzienda createInsertApiAzienda() {
        return new InsertApiAzienda();
    }

    /**
     * Create an instance of {@link ElencoCodiciAssegnati }
     * 
     */
    public ElencoCodiciAssegnati createElencoCodiciAssegnati() {
        return new ElencoCodiciAssegnati();
    }

    /**
     * Create an instance of {@link UpdateApiAziendaResponse }
     * 
     */
    public UpdateApiAziendaResponse createUpdateApiAziendaResponse() {
        return new UpdateApiAziendaResponse();
    }

    /**
     * Create an instance of {@link InsertApiAziendaResponse }
     * 
     */
    public InsertApiAziendaResponse createInsertApiAziendaResponse() {
        return new InsertApiAziendaResponse();
    }

    /**
     * Create an instance of {@link UpdateApiAzienda }
     * 
     */
    public UpdateApiAzienda createUpdateApiAzienda() {
        return new UpdateApiAzienda();
    }

    /**
     * Create an instance of {@link SearchApiAziendaByPk }
     * 
     */
    public SearchApiAziendaByPk createSearchApiAziendaByPk() {
        return new SearchApiAziendaByPk();
    }

    /**
     * Create an instance of {@link DeleteApiAzienda }
     * 
     */
    public DeleteApiAzienda createDeleteApiAzienda() {
        return new DeleteApiAzienda();
    }

    /**
     * Create an instance of {@link ElencoCodiciDisponibili }
     * 
     */
    public ElencoCodiciDisponibili createElencoCodiciDisponibili() {
        return new ElencoCodiciDisponibili();
    }

    /**
     * Create an instance of {@link DeleteApiAziendaResponse }
     * 
     */
    public DeleteApiAziendaResponse createDeleteApiAziendaResponse() {
        return new DeleteApiAziendaResponse();
    }

    /**
     * Create an instance of {@link SearchApiAziendaResponse }
     * 
     */
    public SearchApiAziendaResponse createSearchApiAziendaResponse() {
        return new SearchApiAziendaResponse();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link Azienda }
     * 
     */
    public Azienda createAzienda() {
        return new Azienda();
    }

    /**
     * Create an instance of {@link FieldError }
     * 
     */
    public FieldError createFieldError() {
        return new FieldError();
    }

    /**
     * Create an instance of {@link Apiazienda }
     * 
     */
    public Apiazienda createApiazienda() {
        return new Apiazienda();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAziendaByPkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAziendaByPkResponse")
    public JAXBElement<SearchApiAziendaByPkResponse> createSearchApiAziendaByPkResponse(SearchApiAziendaByPkResponse value) {
        return new JAXBElement<SearchApiAziendaByPkResponse>(_SearchApiAziendaByPkResponse_QNAME, SearchApiAziendaByPkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoCodiciDisponibiliResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "elencoCodiciDisponibiliResponse")
    public JAXBElement<ElencoCodiciDisponibiliResponse> createElencoCodiciDisponibiliResponse(ElencoCodiciDisponibiliResponse value) {
        return new JAXBElement<ElencoCodiciDisponibiliResponse>(_ElencoCodiciDisponibiliResponse_QNAME, ElencoCodiciDisponibiliResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAzienda }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAzienda")
    public JAXBElement<SearchApiAzienda> createSearchApiAzienda(SearchApiAzienda value) {
        return new JAXBElement<SearchApiAzienda>(_SearchApiAzienda_QNAME, SearchApiAzienda.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPAutenticazioneWS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "SOAPAutenticazioneWS")
    public JAXBElement<SOAPAutenticazioneWS> createSOAPAutenticazioneWS(SOAPAutenticazioneWS value) {
        return new JAXBElement<SOAPAutenticazioneWS>(_SOAPAutenticazioneWS_QNAME, SOAPAutenticazioneWS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoCodiciAssegnatiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "elencoCodiciAssegnatiResponse")
    public JAXBElement<ElencoCodiciAssegnatiResponse> createElencoCodiciAssegnatiResponse(ElencoCodiciAssegnatiResponse value) {
        return new JAXBElement<ElencoCodiciAssegnatiResponse>(_ElencoCodiciAssegnatiResponse_QNAME, ElencoCodiciAssegnatiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApiAzienda }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "insertApiAzienda")
    public JAXBElement<InsertApiAzienda> createInsertApiAzienda(InsertApiAzienda value) {
        return new JAXBElement<InsertApiAzienda>(_InsertApiAzienda_QNAME, InsertApiAzienda.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoCodiciAssegnati }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "elencoCodiciAssegnati")
    public JAXBElement<ElencoCodiciAssegnati> createElencoCodiciAssegnati(ElencoCodiciAssegnati value) {
        return new JAXBElement<ElencoCodiciAssegnati>(_ElencoCodiciAssegnati_QNAME, ElencoCodiciAssegnati.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApiAziendaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "updateApiAziendaResponse")
    public JAXBElement<UpdateApiAziendaResponse> createUpdateApiAziendaResponse(UpdateApiAziendaResponse value) {
        return new JAXBElement<UpdateApiAziendaResponse>(_UpdateApiAziendaResponse_QNAME, UpdateApiAziendaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoCodiciDisponibili }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "elencoCodiciDisponibili")
    public JAXBElement<ElencoCodiciDisponibili> createElencoCodiciDisponibili(ElencoCodiciDisponibili value) {
        return new JAXBElement<ElencoCodiciDisponibili>(_ElencoCodiciDisponibili_QNAME, ElencoCodiciDisponibili.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApiAziendaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "deleteApiAziendaResponse")
    public JAXBElement<DeleteApiAziendaResponse> createDeleteApiAziendaResponse(DeleteApiAziendaResponse value) {
        return new JAXBElement<DeleteApiAziendaResponse>(_DeleteApiAziendaResponse_QNAME, DeleteApiAziendaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertApiAziendaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "insertApiAziendaResponse")
    public JAXBElement<InsertApiAziendaResponse> createInsertApiAziendaResponse(InsertApiAziendaResponse value) {
        return new JAXBElement<InsertApiAziendaResponse>(_InsertApiAziendaResponse_QNAME, InsertApiAziendaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateApiAzienda }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "updateApiAzienda")
    public JAXBElement<UpdateApiAzienda> createUpdateApiAzienda(UpdateApiAzienda value) {
        return new JAXBElement<UpdateApiAzienda>(_UpdateApiAzienda_QNAME, UpdateApiAzienda.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAziendaByPk }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAziendaByPk")
    public JAXBElement<SearchApiAziendaByPk> createSearchApiAziendaByPk(SearchApiAziendaByPk value) {
        return new JAXBElement<SearchApiAziendaByPk>(_SearchApiAziendaByPk_QNAME, SearchApiAziendaByPk.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteApiAzienda }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "deleteApiAzienda")
    public JAXBElement<DeleteApiAzienda> createDeleteApiAzienda(DeleteApiAzienda value) {
        return new JAXBElement<DeleteApiAzienda>(_DeleteApiAzienda_QNAME, DeleteApiAzienda.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchApiAziendaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.apianagrafica.apicoltura.izs.it/", name = "searchApiAziendaResponse")
    public JAXBElement<SearchApiAziendaResponse> createSearchApiAziendaResponse(SearchApiAziendaResponse value) {
        return new JAXBElement<SearchApiAziendaResponse>(_SearchApiAziendaResponse_QNAME, SearchApiAziendaResponse.class, null, value);
    }

}
