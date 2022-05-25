/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.sinsa.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.izs.sinsa.services package. 
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

    private final static QName _SOAPAutenticazione_QNAME = new QName("http://sinsa.izs.it/services", "SOAPAutenticazione");
    private final static QName _Exception_QNAME = new QName("http://sinsa.izs.it/services", "Exception");
    private final static QName _SearchPrelievoByIdResponse_QNAME = new QName("http://sinsa.izs.it/services", "searchPrelievoByIdResponse");
    private final static QName _GetMotiviPianoResponse_QNAME = new QName("http://sinsa.izs.it/services", "getMotiviPianoResponse");
    private final static QName _SearchPrelievoResponse_QNAME = new QName("http://sinsa.izs.it/services", "searchPrelievoResponse");
    private final static QName _InsertPrelievo_QNAME = new QName("http://sinsa.izs.it/services", "insertPrelievo");
    private final static QName _GetPiani_QNAME = new QName("http://sinsa.izs.it/services", "getPiani");
    private final static QName _GetAltreInformazioniResponse_QNAME = new QName("http://sinsa.izs.it/services", "getAltreInformazioniResponse");
    private final static QName _SearchPrelievoById_QNAME = new QName("http://sinsa.izs.it/services", "searchPrelievoById");
    private final static QName _GetMotiviPiano_QNAME = new QName("http://sinsa.izs.it/services", "getMotiviPiano");
    private final static QName _DownloadSchedaPrelievoFirmata_QNAME = new QName("http://sinsa.izs.it/services", "downloadSchedaPrelievoFirmata");
    private final static QName _UpdateAccettazioneResponse_QNAME = new QName("http://sinsa.izs.it/services", "updateAccettazioneResponse");
    private final static QName _GetConfigurazionePiano_QNAME = new QName("http://sinsa.izs.it/services", "getConfigurazionePiano");
    private final static QName _GetLuoghiPrelievoPianoResponse_QNAME = new QName("http://sinsa.izs.it/services", "getLuoghiPrelievoPianoResponse");
    private final static QName _GetPianiResponse_QNAME = new QName("http://sinsa.izs.it/services", "getPianiResponse");
    private final static QName _IzsWsException_QNAME = new QName("http://sinsa.izs.it/services", "IzsWsException");
    private final static QName _UpdateAccettazione_QNAME = new QName("http://sinsa.izs.it/services", "updateAccettazione");
    private final static QName _PrintSchedaPrelievo_QNAME = new QName("http://sinsa.izs.it/services", "printSchedaPrelievo");
    private final static QName _SearchPrelievo_QNAME = new QName("http://sinsa.izs.it/services", "searchPrelievo");
    private final static QName _SOAPAutorizzazione_QNAME = new QName("http://sinsa.izs.it/services", "SOAPAutorizzazione");
    private final static QName _GetLuoghiPrelievoPiano_QNAME = new QName("http://sinsa.izs.it/services", "getLuoghiPrelievoPiano");
    private final static QName _GetAltreInformazioniXsdResponse_QNAME = new QName("http://sinsa.izs.it/services", "getAltreInformazioniXsdResponse");
    private final static QName _GetConfigurazionePianoResponse_QNAME = new QName("http://sinsa.izs.it/services", "getConfigurazionePianoResponse");
    private final static QName _BusinessException_QNAME = new QName("http://sinsa.izs.it/services", "BusinessException");
    private final static QName _PrintSchedaPrelievoResponse_QNAME = new QName("http://sinsa.izs.it/services", "printSchedaPrelievoResponse");
    private final static QName _SOAPException_QNAME = new QName("http://sinsa.izs.it/services", "SOAPException");
    private final static QName _InsertPrelievoResponse_QNAME = new QName("http://sinsa.izs.it/services", "insertPrelievoResponse");
    private final static QName _UploadSchedaPrelievoFirmata_QNAME = new QName("http://sinsa.izs.it/services", "uploadSchedaPrelievoFirmata");
    private final static QName _GetAltreInformazioniXsd_QNAME = new QName("http://sinsa.izs.it/services", "getAltreInformazioniXsd");
    private final static QName _UploadSchedaPrelievoFirmataResponse_QNAME = new QName("http://sinsa.izs.it/services", "uploadSchedaPrelievoFirmataResponse");
    private final static QName _GetAltreInformazioni_QNAME = new QName("http://sinsa.izs.it/services", "getAltreInformazioni");
    private final static QName _DownloadSchedaPrelievoFirmataResponse_QNAME = new QName("http://sinsa.izs.it/services", "downloadSchedaPrelievoFirmataResponse");
    private final static QName _UploadSchedaPrelievoFirmataFile_QNAME = new QName("", "file");
    private final static QName _DownloadSchedaPrelievoFirmataResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.izs.sinsa.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrelievoAltreInformazioniWs }
     * 
     */
    public PrelievoAltreInformazioniWs createPrelievoAltreInformazioniWs() {
        return new PrelievoAltreInformazioniWs();
    }

    /**
     * Create an instance of {@link PrelievoAltreInformazioniWs.Informazioni }
     * 
     */
    public PrelievoAltreInformazioniWs.Informazioni createPrelievoAltreInformazioniWsInformazioni() {
        return new PrelievoAltreInformazioniWs.Informazioni();
    }

    /**
     * Create an instance of {@link CampioniPrelevatiWsWithDetailsTO }
     * 
     */
    public CampioniPrelevatiWsWithDetailsTO createCampioniPrelevatiWsWithDetailsTO() {
        return new CampioniPrelevatiWsWithDetailsTO();
    }

    /**
     * Create an instance of {@link CampioniPrelevatiWsWithDetailsTO.AltreInformazioni }
     * 
     */
    public CampioniPrelevatiWsWithDetailsTO.AltreInformazioni createCampioniPrelevatiWsWithDetailsTOAltreInformazioni() {
        return new CampioniPrelevatiWsWithDetailsTO.AltreInformazioni();
    }

    /**
     * Create an instance of {@link PrelieviWsWithDetailsTO }
     * 
     */
    public PrelieviWsWithDetailsTO createPrelieviWsWithDetailsTO() {
        return new PrelieviWsWithDetailsTO();
    }

    /**
     * Create an instance of {@link PrelieviWsWithDetailsTO.AltreInformazioni }
     * 
     */
    public PrelieviWsWithDetailsTO.AltreInformazioni createPrelieviWsWithDetailsTOAltreInformazioni() {
        return new PrelieviWsWithDetailsTO.AltreInformazioni();
    }

    /**
     * Create an instance of {@link GetLuoghiPrelievoPianoResponse }
     * 
     */
    public GetLuoghiPrelievoPianoResponse createGetLuoghiPrelievoPianoResponse() {
        return new GetLuoghiPrelievoPianoResponse();
    }

    /**
     * Create an instance of {@link IzsWsException }
     * 
     */
    public IzsWsException createIzsWsException() {
        return new IzsWsException();
    }

    /**
     * Create an instance of {@link GetPianiResponse }
     * 
     */
    public GetPianiResponse createGetPianiResponse() {
        return new GetPianiResponse();
    }

    /**
     * Create an instance of {@link SearchPrelievo }
     * 
     */
    public SearchPrelievo createSearchPrelievo() {
        return new SearchPrelievo();
    }

    /**
     * Create an instance of {@link PrintSchedaPrelievo }
     * 
     */
    public PrintSchedaPrelievo createPrintSchedaPrelievo() {
        return new PrintSchedaPrelievo();
    }

    /**
     * Create an instance of {@link UpdateAccettazione }
     * 
     */
    public UpdateAccettazione createUpdateAccettazione() {
        return new UpdateAccettazione();
    }

    /**
     * Create an instance of {@link GetAltreInformazioniXsdResponse }
     * 
     */
    public GetAltreInformazioniXsdResponse createGetAltreInformazioniXsdResponse() {
        return new GetAltreInformazioniXsdResponse();
    }

    /**
     * Create an instance of {@link GetConfigurazionePianoResponse }
     * 
     */
    public GetConfigurazionePianoResponse createGetConfigurazionePianoResponse() {
        return new GetConfigurazionePianoResponse();
    }

    /**
     * Create an instance of {@link AutorizzazioneTO }
     * 
     */
    public AutorizzazioneTO createAutorizzazioneTO() {
        return new AutorizzazioneTO();
    }

    /**
     * Create an instance of {@link GetLuoghiPrelievoPiano }
     * 
     */
    public GetLuoghiPrelievoPiano createGetLuoghiPrelievoPiano() {
        return new GetLuoghiPrelievoPiano();
    }

    /**
     * Create an instance of {@link PrintSchedaPrelievoResponse }
     * 
     */
    public PrintSchedaPrelievoResponse createPrintSchedaPrelievoResponse() {
        return new PrintSchedaPrelievoResponse();
    }

    /**
     * Create an instance of {@link BusinessException }
     * 
     */
    public BusinessException createBusinessException() {
        return new BusinessException();
    }

    /**
     * Create an instance of {@link GetAltreInformazioniXsd }
     * 
     */
    public GetAltreInformazioniXsd createGetAltreInformazioniXsd() {
        return new GetAltreInformazioniXsd();
    }

    /**
     * Create an instance of {@link UploadSchedaPrelievoFirmata }
     * 
     */
    public UploadSchedaPrelievoFirmata createUploadSchedaPrelievoFirmata() {
        return new UploadSchedaPrelievoFirmata();
    }

    /**
     * Create an instance of {@link SOAPException }
     * 
     */
    public SOAPException createSOAPException() {
        return new SOAPException();
    }

    /**
     * Create an instance of {@link InsertPrelievoResponse }
     * 
     */
    public InsertPrelievoResponse createInsertPrelievoResponse() {
        return new InsertPrelievoResponse();
    }

    /**
     * Create an instance of {@link UploadSchedaPrelievoFirmataResponse }
     * 
     */
    public UploadSchedaPrelievoFirmataResponse createUploadSchedaPrelievoFirmataResponse() {
        return new UploadSchedaPrelievoFirmataResponse();
    }

    /**
     * Create an instance of {@link DownloadSchedaPrelievoFirmataResponse }
     * 
     */
    public DownloadSchedaPrelievoFirmataResponse createDownloadSchedaPrelievoFirmataResponse() {
        return new DownloadSchedaPrelievoFirmataResponse();
    }

    /**
     * Create an instance of {@link GetAltreInformazioni }
     * 
     */
    public GetAltreInformazioni createGetAltreInformazioni() {
        return new GetAltreInformazioni();
    }

    /**
     * Create an instance of {@link AutenticazioneTO }
     * 
     */
    public AutenticazioneTO createAutenticazioneTO() {
        return new AutenticazioneTO();
    }

    /**
     * Create an instance of {@link GetMotiviPianoResponse }
     * 
     */
    public GetMotiviPianoResponse createGetMotiviPianoResponse() {
        return new GetMotiviPianoResponse();
    }

    /**
     * Create an instance of {@link SearchPrelievoResponse }
     * 
     */
    public SearchPrelievoResponse createSearchPrelievoResponse() {
        return new SearchPrelievoResponse();
    }

    /**
     * Create an instance of {@link SearchPrelievoByIdResponse }
     * 
     */
    public SearchPrelievoByIdResponse createSearchPrelievoByIdResponse() {
        return new SearchPrelievoByIdResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link InsertPrelievo }
     * 
     */
    public InsertPrelievo createInsertPrelievo() {
        return new InsertPrelievo();
    }

    /**
     * Create an instance of {@link GetPiani }
     * 
     */
    public GetPiani createGetPiani() {
        return new GetPiani();
    }

    /**
     * Create an instance of {@link DownloadSchedaPrelievoFirmata }
     * 
     */
    public DownloadSchedaPrelievoFirmata createDownloadSchedaPrelievoFirmata() {
        return new DownloadSchedaPrelievoFirmata();
    }

    /**
     * Create an instance of {@link UpdateAccettazioneResponse }
     * 
     */
    public UpdateAccettazioneResponse createUpdateAccettazioneResponse() {
        return new UpdateAccettazioneResponse();
    }

    /**
     * Create an instance of {@link GetMotiviPiano }
     * 
     */
    public GetMotiviPiano createGetMotiviPiano() {
        return new GetMotiviPiano();
    }

    /**
     * Create an instance of {@link GetAltreInformazioniResponse }
     * 
     */
    public GetAltreInformazioniResponse createGetAltreInformazioniResponse() {
        return new GetAltreInformazioniResponse();
    }

    /**
     * Create an instance of {@link SearchPrelievoById }
     * 
     */
    public SearchPrelievoById createSearchPrelievoById() {
        return new SearchPrelievoById();
    }

    /**
     * Create an instance of {@link GetConfigurazionePiano }
     * 
     */
    public GetConfigurazionePiano createGetConfigurazionePiano() {
        return new GetConfigurazionePiano();
    }

    /**
     * Create an instance of {@link ContaminanteWsTO }
     * 
     */
    public ContaminanteWsTO createContaminanteWsTO() {
        return new ContaminanteWsTO();
    }

    /**
     * Create an instance of {@link PrelievoWsTO }
     * 
     */
    public PrelievoWsTO createPrelievoWsTO() {
        return new PrelievoWsTO();
    }

    /**
     * Create an instance of {@link ConfigurazionePianoWsTO }
     * 
     */
    public ConfigurazionePianoWsTO createConfigurazionePianoWsTO() {
        return new ConfigurazionePianoWsTO();
    }

    /**
     * Create an instance of {@link PrelevatoreWsTO }
     * 
     */
    public PrelevatoreWsTO createPrelevatoreWsTO() {
        return new PrelevatoreWsTO();
    }

    /**
     * Create an instance of {@link CampionePrelevatoWsTO }
     * 
     */
    public CampionePrelevatoWsTO createCampionePrelevatoWsTO() {
        return new CampionePrelevatoWsTO();
    }

    /**
     * Create an instance of {@link PrelievoAltreInformazioniWs.Informazioni.Entry }
     * 
     */
    public PrelievoAltreInformazioniWs.Informazioni.Entry createPrelievoAltreInformazioniWsInformazioniEntry() {
        return new PrelievoAltreInformazioniWs.Informazioni.Entry();
    }

    /**
     * Create an instance of {@link CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry }
     * 
     */
    public CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry createCampioniPrelevatiWsWithDetailsTOAltreInformazioniEntry() {
        return new CampioniPrelevatiWsWithDetailsTO.AltreInformazioni.Entry();
    }

    /**
     * Create an instance of {@link PrelieviWsWithDetailsTO.AltreInformazioni.Entry }
     * 
     */
    public PrelieviWsWithDetailsTO.AltreInformazioni.Entry createPrelieviWsWithDetailsTOAltreInformazioniEntry() {
        return new PrelieviWsWithDetailsTO.AltreInformazioni.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticazioneTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "SOAPAutenticazione")
    public JAXBElement<AutenticazioneTO> createSOAPAutenticazione(AutenticazioneTO value) {
        return new JAXBElement<AutenticazioneTO>(_SOAPAutenticazione_QNAME, AutenticazioneTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPrelievoByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "searchPrelievoByIdResponse")
    public JAXBElement<SearchPrelievoByIdResponse> createSearchPrelievoByIdResponse(SearchPrelievoByIdResponse value) {
        return new JAXBElement<SearchPrelievoByIdResponse>(_SearchPrelievoByIdResponse_QNAME, SearchPrelievoByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMotiviPianoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getMotiviPianoResponse")
    public JAXBElement<GetMotiviPianoResponse> createGetMotiviPianoResponse(GetMotiviPianoResponse value) {
        return new JAXBElement<GetMotiviPianoResponse>(_GetMotiviPianoResponse_QNAME, GetMotiviPianoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPrelievoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "searchPrelievoResponse")
    public JAXBElement<SearchPrelievoResponse> createSearchPrelievoResponse(SearchPrelievoResponse value) {
        return new JAXBElement<SearchPrelievoResponse>(_SearchPrelievoResponse_QNAME, SearchPrelievoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertPrelievo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "insertPrelievo")
    public JAXBElement<InsertPrelievo> createInsertPrelievo(InsertPrelievo value) {
        return new JAXBElement<InsertPrelievo>(_InsertPrelievo_QNAME, InsertPrelievo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPiani }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getPiani")
    public JAXBElement<GetPiani> createGetPiani(GetPiani value) {
        return new JAXBElement<GetPiani>(_GetPiani_QNAME, GetPiani.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAltreInformazioniResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getAltreInformazioniResponse")
    public JAXBElement<GetAltreInformazioniResponse> createGetAltreInformazioniResponse(GetAltreInformazioniResponse value) {
        return new JAXBElement<GetAltreInformazioniResponse>(_GetAltreInformazioniResponse_QNAME, GetAltreInformazioniResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPrelievoById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "searchPrelievoById")
    public JAXBElement<SearchPrelievoById> createSearchPrelievoById(SearchPrelievoById value) {
        return new JAXBElement<SearchPrelievoById>(_SearchPrelievoById_QNAME, SearchPrelievoById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMotiviPiano }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getMotiviPiano")
    public JAXBElement<GetMotiviPiano> createGetMotiviPiano(GetMotiviPiano value) {
        return new JAXBElement<GetMotiviPiano>(_GetMotiviPiano_QNAME, GetMotiviPiano.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadSchedaPrelievoFirmata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "downloadSchedaPrelievoFirmata")
    public JAXBElement<DownloadSchedaPrelievoFirmata> createDownloadSchedaPrelievoFirmata(DownloadSchedaPrelievoFirmata value) {
        return new JAXBElement<DownloadSchedaPrelievoFirmata>(_DownloadSchedaPrelievoFirmata_QNAME, DownloadSchedaPrelievoFirmata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAccettazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "updateAccettazioneResponse")
    public JAXBElement<UpdateAccettazioneResponse> createUpdateAccettazioneResponse(UpdateAccettazioneResponse value) {
        return new JAXBElement<UpdateAccettazioneResponse>(_UpdateAccettazioneResponse_QNAME, UpdateAccettazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurazionePiano }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getConfigurazionePiano")
    public JAXBElement<GetConfigurazionePiano> createGetConfigurazionePiano(GetConfigurazionePiano value) {
        return new JAXBElement<GetConfigurazionePiano>(_GetConfigurazionePiano_QNAME, GetConfigurazionePiano.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLuoghiPrelievoPianoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getLuoghiPrelievoPianoResponse")
    public JAXBElement<GetLuoghiPrelievoPianoResponse> createGetLuoghiPrelievoPianoResponse(GetLuoghiPrelievoPianoResponse value) {
        return new JAXBElement<GetLuoghiPrelievoPianoResponse>(_GetLuoghiPrelievoPianoResponse_QNAME, GetLuoghiPrelievoPianoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPianiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getPianiResponse")
    public JAXBElement<GetPianiResponse> createGetPianiResponse(GetPianiResponse value) {
        return new JAXBElement<GetPianiResponse>(_GetPianiResponse_QNAME, GetPianiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IzsWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "IzsWsException")
    public JAXBElement<IzsWsException> createIzsWsException(IzsWsException value) {
        return new JAXBElement<IzsWsException>(_IzsWsException_QNAME, IzsWsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAccettazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "updateAccettazione")
    public JAXBElement<UpdateAccettazione> createUpdateAccettazione(UpdateAccettazione value) {
        return new JAXBElement<UpdateAccettazione>(_UpdateAccettazione_QNAME, UpdateAccettazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintSchedaPrelievo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "printSchedaPrelievo")
    public JAXBElement<PrintSchedaPrelievo> createPrintSchedaPrelievo(PrintSchedaPrelievo value) {
        return new JAXBElement<PrintSchedaPrelievo>(_PrintSchedaPrelievo_QNAME, PrintSchedaPrelievo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPrelievo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "searchPrelievo")
    public JAXBElement<SearchPrelievo> createSearchPrelievo(SearchPrelievo value) {
        return new JAXBElement<SearchPrelievo>(_SearchPrelievo_QNAME, SearchPrelievo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutorizzazioneTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "SOAPAutorizzazione")
    public JAXBElement<AutorizzazioneTO> createSOAPAutorizzazione(AutorizzazioneTO value) {
        return new JAXBElement<AutorizzazioneTO>(_SOAPAutorizzazione_QNAME, AutorizzazioneTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLuoghiPrelievoPiano }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getLuoghiPrelievoPiano")
    public JAXBElement<GetLuoghiPrelievoPiano> createGetLuoghiPrelievoPiano(GetLuoghiPrelievoPiano value) {
        return new JAXBElement<GetLuoghiPrelievoPiano>(_GetLuoghiPrelievoPiano_QNAME, GetLuoghiPrelievoPiano.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAltreInformazioniXsdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getAltreInformazioniXsdResponse")
    public JAXBElement<GetAltreInformazioniXsdResponse> createGetAltreInformazioniXsdResponse(GetAltreInformazioniXsdResponse value) {
        return new JAXBElement<GetAltreInformazioniXsdResponse>(_GetAltreInformazioniXsdResponse_QNAME, GetAltreInformazioniXsdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurazionePianoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getConfigurazionePianoResponse")
    public JAXBElement<GetConfigurazionePianoResponse> createGetConfigurazionePianoResponse(GetConfigurazionePianoResponse value) {
        return new JAXBElement<GetConfigurazionePianoResponse>(_GetConfigurazionePianoResponse_QNAME, GetConfigurazionePianoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "BusinessException")
    public JAXBElement<BusinessException> createBusinessException(BusinessException value) {
        return new JAXBElement<BusinessException>(_BusinessException_QNAME, BusinessException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintSchedaPrelievoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "printSchedaPrelievoResponse")
    public JAXBElement<PrintSchedaPrelievoResponse> createPrintSchedaPrelievoResponse(PrintSchedaPrelievoResponse value) {
        return new JAXBElement<PrintSchedaPrelievoResponse>(_PrintSchedaPrelievoResponse_QNAME, PrintSchedaPrelievoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOAPException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "SOAPException")
    public JAXBElement<SOAPException> createSOAPException(SOAPException value) {
        return new JAXBElement<SOAPException>(_SOAPException_QNAME, SOAPException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertPrelievoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "insertPrelievoResponse")
    public JAXBElement<InsertPrelievoResponse> createInsertPrelievoResponse(InsertPrelievoResponse value) {
        return new JAXBElement<InsertPrelievoResponse>(_InsertPrelievoResponse_QNAME, InsertPrelievoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadSchedaPrelievoFirmata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "uploadSchedaPrelievoFirmata")
    public JAXBElement<UploadSchedaPrelievoFirmata> createUploadSchedaPrelievoFirmata(UploadSchedaPrelievoFirmata value) {
        return new JAXBElement<UploadSchedaPrelievoFirmata>(_UploadSchedaPrelievoFirmata_QNAME, UploadSchedaPrelievoFirmata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAltreInformazioniXsd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getAltreInformazioniXsd")
    public JAXBElement<GetAltreInformazioniXsd> createGetAltreInformazioniXsd(GetAltreInformazioniXsd value) {
        return new JAXBElement<GetAltreInformazioniXsd>(_GetAltreInformazioniXsd_QNAME, GetAltreInformazioniXsd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadSchedaPrelievoFirmataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "uploadSchedaPrelievoFirmataResponse")
    public JAXBElement<UploadSchedaPrelievoFirmataResponse> createUploadSchedaPrelievoFirmataResponse(UploadSchedaPrelievoFirmataResponse value) {
        return new JAXBElement<UploadSchedaPrelievoFirmataResponse>(_UploadSchedaPrelievoFirmataResponse_QNAME, UploadSchedaPrelievoFirmataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAltreInformazioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "getAltreInformazioni")
    public JAXBElement<GetAltreInformazioni> createGetAltreInformazioni(GetAltreInformazioni value) {
        return new JAXBElement<GetAltreInformazioni>(_GetAltreInformazioni_QNAME, GetAltreInformazioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadSchedaPrelievoFirmataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sinsa.izs.it/services", name = "downloadSchedaPrelievoFirmataResponse")
    public JAXBElement<DownloadSchedaPrelievoFirmataResponse> createDownloadSchedaPrelievoFirmataResponse(DownloadSchedaPrelievoFirmataResponse value) {
        return new JAXBElement<DownloadSchedaPrelievoFirmataResponse>(_DownloadSchedaPrelievoFirmataResponse_QNAME, DownloadSchedaPrelievoFirmataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = UploadSchedaPrelievoFirmata.class)
    public JAXBElement<byte[]> createUploadSchedaPrelievoFirmataFile(byte[] value) {
        return new JAXBElement<byte[]>(_UploadSchedaPrelievoFirmataFile_QNAME, byte[].class, UploadSchedaPrelievoFirmata.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadSchedaPrelievoFirmataResponse.class)
    public JAXBElement<byte[]> createDownloadSchedaPrelievoFirmataResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadSchedaPrelievoFirmataResponseReturn_QNAME, byte[].class, DownloadSchedaPrelievoFirmataResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = PrintSchedaPrelievoResponse.class)
    public JAXBElement<byte[]> createPrintSchedaPrelievoResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadSchedaPrelievoFirmataResponseReturn_QNAME, byte[].class, PrintSchedaPrelievoResponse.class, ((byte[]) value));
    }

}
