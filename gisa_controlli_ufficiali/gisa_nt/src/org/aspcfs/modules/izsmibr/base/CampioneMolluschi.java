/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.base;

import it.izs.bdn.action.utilsXML;
import it.izs.sinsa.services.AutenticazioneTO;
import it.izs.sinsa.services.AutorizzazioneTO;
import it.izs.sinsa.services.BusinessException_Exception;
import it.izs.sinsa.services.CampionePrelevatoWsTO;
import it.izs.sinsa.services.ContaminanteWsTO;
import it.izs.sinsa.services.Exception_Exception;
import it.izs.sinsa.services.InsertPrelievo;
import it.izs.sinsa.services.InsertPrelievoResponse;
import it.izs.sinsa.services.IzsWsException_Exception;
import it.izs.sinsa.services.PrelevatoreWsTO;
import it.izs.sinsa.services.Prelievi;
import it.izs.sinsa.services.PrelieviService;
import it.izs.sinsa.services.PrelievoAltreInformazioniWs;
import it.izs.sinsa.services.PrelievoWsTO;
import it.izs.sinsa.services.SOAPException_Exception;
import it.izs.sinsa.services.PrelievoAltreInformazioniWs.Informazioni;
import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.izsmibr.actions.EsitoInvioMoll;

public class CampioneMolluschi {

	private String esito_invio ;
	private String tracciato_record_richiesta ;
	private String tracciato_record_risposta ;
	private Timestamp data_invio_bdn ;
	private String errore ;
	
	private String pianoCodice;
	private String numeroSchedaPrelievo;
	private String dataPrel ; 
	private String luogoPrelievoCodice ;
	private String metodoCampionamentoCodice;
	private String motivoCodice;
	private String prelNome ;
	private String prelCognome ;
	private String prelCodFiscale ;
	private String sitoCodice;
	private String comuneCodiceIstatParziale;
	private String siglaProvincia;
	private String laboratorioCodice;
	private String latitudine;
	private String longitudine;
	private String codiceContaminante;
	private String progressivoCampione;
	private String foodexCodice;
	private String ProfFondale;
	private String classificazioneDellaZonaDiMareCe8542004;
	
	public String getFoodexCodice() {
		return foodexCodice;
	}
	public void setFoodexCodice(String foodexCodice) {
		this.foodexCodice = foodexCodice;
	}
	public String getEsito_invio() {
		return esito_invio;
	}
	public void setEsito_invio(String esito_invio) {
		this.esito_invio = esito_invio;
	}
	public String getTracciato_record_richiesta() {
		return tracciato_record_richiesta;
	}
	public void setTracciato_record_richiesta(String tracciato_record_richiesta) {
		this.tracciato_record_richiesta = tracciato_record_richiesta;
	}
	public String getTracciato_record_risposta() {
		return tracciato_record_risposta;
	}
	public void setTracciato_record_risposta(String tracciato_record_risposta) {
		this.tracciato_record_risposta = tracciato_record_risposta;
	}
	public Timestamp getData_invio_bdn() {
		return data_invio_bdn;
	}
	public void setData_invio_bdn(Timestamp data_invio_bdn) {
		this.data_invio_bdn = data_invio_bdn;
	}
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public String getPianoCodice() {
		return pianoCodice;
	}
	public void setPianoCodice(String pianoCodice) {
		this.pianoCodice = pianoCodice;
	}
	public String getNumeroSchedaPrelievo() {
		return numeroSchedaPrelievo;
	}
	public void setNumeroSchedaPrelievo(String numeroSchedaPrelievo) {
		this.numeroSchedaPrelievo = numeroSchedaPrelievo;
	}
	public String getDataPrel() {
		return dataPrel;
	}
	public void setDataPrel(String dataPrel) {
		this.dataPrel = dataPrel;
	}
	public String getLuogoPrelievoCodice() {
		return luogoPrelievoCodice;
	}
	public void setLuogoPrelievoCodice(String luogoPrelievoCodice) {
		this.luogoPrelievoCodice = luogoPrelievoCodice;
	}
	public String getMetodoCampionamentoCodice() {
		return metodoCampionamentoCodice;
	}
	public void setMetodoCampionamentoCodice(String metodoCampionamentoCodice) {
		this.metodoCampionamentoCodice = metodoCampionamentoCodice;
	}
	public String getMotivoCodice() {
		return motivoCodice;
	}
	public void setMotivoCodice(String motivoCodice) {
		this.motivoCodice = motivoCodice;
	}
	public String getPrelNome() {
		return prelNome;
	}
	public void setPrelNome(String prelNome) {
		this.prelNome = prelNome;
	}
	public String getPrelCognome() {
		return prelCognome;
	}
	public void setPrelCognome(String prelCognome) {
		this.prelCognome = prelCognome;
	}
	public String getPrelCodFiscale() {
		return prelCodFiscale;
	}
	public void setPrelCodFiscale(String prelCodFiscale) {
		this.prelCodFiscale = prelCodFiscale;
	}
	public String getSitoCodice() {
		return sitoCodice;
	}
	public void setSitoCodice(String sitoCodice) {
		this.sitoCodice = sitoCodice;
	}
	public String getComuneCodiceIstatParziale() {
		return comuneCodiceIstatParziale;
	}
	public void setComuneCodiceIstatParziale(String comuneCodiceIstatParziale) {
		this.comuneCodiceIstatParziale = comuneCodiceIstatParziale;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getLaboratorioCodice() {
		return laboratorioCodice;
	}
	public void setLaboratorioCodice(String laboratorioCodice) {
		this.laboratorioCodice = laboratorioCodice;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public String getCodiceContaminante() {
		return codiceContaminante;
	}
	public void setCodiceContaminante(String codiceContaminante) {
		this.codiceContaminante = codiceContaminante;
	}
	public String getProgressivoCampione() {
		return progressivoCampione;
	}
	public void setProgressivoCampione(String progressivoCampione) {
		this.progressivoCampione = progressivoCampione;
	}
	public String getProfFondale() {
		return ProfFondale;
	}
	public void setProfFondale(String profFondale) {
		ProfFondale = profFondale;
	}
	public String getClassificazioneDellaZonaDiMareCe8542004() {
		return classificazioneDellaZonaDiMareCe8542004;
	}
	public void setClassificazioneDellaZonaDiMareCe8542004(String classificazioneDellaZonaDiMareCe8542004) {
		this.classificazioneDellaZonaDiMareCe8542004 = classificazioneDellaZonaDiMareCe8542004;
	}
	
	
	 public void insert(Connection db,int idInvioMassivo) throws SQLException
	    {
	    	
	    	try
	    	{
	    		
	    	
	    		
	    		PreparedStatement pst = db.prepareStatement("INSERT INTO import_ca_molluschi("+
            "id_invio_massivo_molluschi, pianocodice, numeroschedaprelievo, "+
            "dataprel, luogoprelievocodice, metodocampionamentocodice, motivocodice, "+
            "prelnome, prelcognome, prelcodfiscale, sitocodice, comunecodiceistatparziale, "+
            "siglaprovincia, laboratoriocodice, latitudine, longitudine, codicecontaminante, "+
            "progressivocampione, proffondale, classificazionedellazonadimarece8542004, "+
            " tracciato_record_richiesta, tracciato_record_risposta, "+
            "data_invio_bdn, errore, esito_invio,foodexcodice)"+
    "VALUES ( ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, ?, ?, "+
            "?, ?, ?, ?, ?, "+
            " ?, ?, "+
            "?, ?, ?,?,?, "+
            "current_timestamp, ?, ?,?);");
	    		int i = 0 ;
	    		pst.setInt(++i, idInvioMassivo);
	    		pst.setString(++i, getPianoCodice());
	    		pst.setString(++i, getNumeroSchedaPrelievo());
	    		pst.setString(++i, getDataPrel());
	    		pst.setString(++i, getLuogoPrelievoCodice());
	    		pst.setString(++i, getMetodoCampionamentoCodice());
	    		pst.setString(++i, getMotivoCodice());
	    		pst.setString(++i, getPrelNome());
	    		pst.setString(++i, getPrelCognome());
	    		pst.setString(++i, getPrelCodFiscale());
	    		pst.setString (++i, getSitoCodice());
	    		pst.setString(++i, getComuneCodiceIstatParziale());
	    		pst.setString(++i, getSiglaProvincia());
	    		pst.setString(++i, getLaboratorioCodice());
	    		pst.setString(++i, getLatitudine());
	    		pst.setString(++i, getLongitudine());
	    		pst.setString(++i, getCodiceContaminante());	    		
	    		pst.setString(++i, getProgressivoCampione());
	    		pst.setString(++i, getProfFondale());
	    		pst.setString(++i, getClassificazioneDellaZonaDiMareCe8542004());
	    		
	    		
	    		pst.setString(++i, this.getTracciato_record_richiesta());
	    		pst.setString(++i, this.getTracciato_record_risposta());
	    		pst.setString(++i, this.getErrore());
	    		pst.setString(++i, getEsito_invio());
	    		pst.setString(++i, getFoodexCodice());



	    		pst.execute();
	    		
	    	}
	    	catch(SQLException e)
	    	{
	    		throw e ;
	    	}
	    }

	
	public void sendToBdn() throws ParseException, DatatypeConfigurationException
	{
		PrelieviService service = new PrelieviService();
		Prelievi prelievi = service.getPrelieviPort();
		
		AutenticazioneTO autenticazione = new AutenticazioneTO();
		autenticazione.setUsername("izsna_006");
		autenticazione.setPassword("na.izs34");
		
		AutorizzazioneTO autorizzione = new AutorizzazioneTO();
		autorizzione.setRuoloCodice("SVREG");
		autorizzione.setRuoloValoreCodice("150");
		PrelievoWsTO prelievoTo = new PrelievoWsTO();
		
		prelievoTo.setPianoCodice(pianoCodice);
		prelievoTo.setNumeroScheda(numeroSchedaPrelievo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(sdf.parse(getDataPrel()));
		XMLGregorianCalendar dtcontrollo = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		prelievoTo.setDataPrelievo(dtcontrollo);

		prelievoTo.setLaboratorioCodice(laboratorioCodice);
		prelievoTo.setLuogoPrelievoCodice(luogoPrelievoCodice);
		prelievoTo.setMetodoCampionamentoCodice(metodoCampionamentoCodice);
		prelievoTo.setMotivoCodice(motivoCodice);
		prelievoTo.setSitoCodice(sitoCodice);
		prelievoTo.setSitoComCodice(comuneCodiceIstatParziale);
		prelievoTo.setSitoProSigla(siglaProvincia);
		prelievoTo.setSitoLatitudine(Double.parseDouble(latitudine));
		prelievoTo.setSitoLongitudine(Double.parseDouble(longitudine));
		
		
		PrelevatoreWsTO prel = new PrelevatoreWsTO();
		prel.setPrelCodFiscale(prelCodFiscale.toUpperCase());
		
		prelievoTo.getPrelevatori().add(prel);
		
		ContaminanteWsTO contaminanteWsTO = new ContaminanteWsTO();
		contaminanteWsTO.setContaminanteCodice(codiceContaminante);
		prelievoTo.getContaminanti().add(contaminanteWsTO);
		
		CampionePrelevatoWsTO campione = new CampionePrelevatoWsTO();
		campione.setProgressivoCampione(Integer.parseInt(progressivoCampione));
		campione.setFoodexCodice(foodexCodice);
		
		
		PrelievoAltreInformazioniWs al = new PrelievoAltreInformazioniWs();
		Informazioni in = new Informazioni();
		it.izs.sinsa.services.PrelievoAltreInformazioniWs.Informazioni.Entry e = new it.izs.sinsa.services.PrelievoAltreInformazioniWs.Informazioni.Entry();
		e.setKey("profFondale");
		e.setValue(ProfFondale);
		in.getEntry().add(e);
		al.setInformazioni(in);
		campione.setAltreInformazioni(al);
		
		
		prelievoTo.getCampioniPrelevati().add(campione);
		
		InsertPrelievo p = new InsertPrelievo();
		p.setPrelievo(prelievoTo);
		
	
		
		try {
		InsertPrelievoResponse risposta = prelievi.insertPrelievo(p, autenticazione, autorizzione);
		
		risposta.getReturn();
		
		esito_invio="OK";
		errore="";
		
		
		
		} catch (BusinessException_Exception e1) {
			// TODO Auto-generated catch block
			EsitoInvioMoll esito = new EsitoInvioMoll();
			esito_invio= "KO";
			errore = e1.getMessage();
			errore=errore.replace("Client received SOAP Fault from server:", "").replace("Please see the server log to find more detail regarding exact cause of the failure", "");
			
			
		} catch (Exception_Exception e2) {
			// TODO Auto-generated catch block
			
			esito_invio= "KO";
			errore = e2.getMessage();
			errore=errore.replace("Client received SOAP Fault from server:", "").replace("Please see the server log to find more detail regarding exact cause of the failure", "");

			
		} catch (IzsWsException_Exception e3) {
			
			esito_invio= "KO";
			errore = e3.getMessage();
			errore=errore.replace("Client received SOAP Fault from server:", "").replace("Please see the server log to find more detail regarding exact cause of the failure", "");

			
		} catch (SOAPException_Exception e4) {
			// TODO Auto-generated catch block
			esito_invio= "KO";
			errore = e4.getMessage();
			errore=errore.replace("Client received SOAP Fault from server:", "").replace("Please see the server log to find more detail regarding exact cause of the failure", "");

		
			
		}
		catch (Exception e5) {
			// TODO Auto-generated catch block
			esito_invio= "KO";
			errore = e5.getMessage();
			errore=errore.replace("Client received SOAP Fault from server:", "").replace("Please see the server log to find more detail regarding exact cause of the failure", "");

			
		}
	}
	
	
	
	public void sendToBdn(Connection db, int userId) 
	{
		WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_PRELIEVO_MOLLUSCHI,WsPost.AZIONE_INSERT);
		
		HashMap<String, Object> campiInput = new HashMap<>();
		
		String xmlCampioniPrelevati = 
		"<altreInformazioni> " +
	    "	<informazioni> " +
	    "  		<entry> " +
	    "    		<key>profFondale</key> " +
	    "    		<value>"  + ((ProfFondale==null)?(""):(ProfFondale)) + "</value> " +
	    "   	</entry> " +
	    "	</informazioni> " +
	    "</altreInformazioni> " +
	    "<foodexCodice>"  + ((foodexCodice==null)?(""):(foodexCodice)) + "</foodexCodice> " +
	    "<progressivoCampione>"  + ((progressivoCampione==null)?(""):(progressivoCampione)) + "</progressivoCampione>";
		
		campiInput.put("campioniPrelevati",xmlCampioniPrelevati);
				
		String xmlContaminanti = "<contaminanteCodice>"  + ((codiceContaminante==null)?(""):(codiceContaminante)) + "</contaminanteCodice>";
	    campiInput.put("contaminanti",xmlContaminanti);	
		
		campiInput.put("dataPrelievo",getDataPrel()+"T00:00:00.000+02:00");
		campiInput.put("laboratorioCodice",laboratorioCodice);
		campiInput.put("luogoPrelievoCodice",luogoPrelievoCodice);
		campiInput.put("metodoCampionamentoCodice",metodoCampionamentoCodice);
		campiInput.put("motivoCodice",motivoCodice);
		campiInput.put("numeroScheda",numeroSchedaPrelievo);
		campiInput.put("pianoCodice",pianoCodice);
		
		String xmlPrelevatori = "<prelCodFiscale>"  + ((prelCodFiscale.toUpperCase()==null)?(""):(prelCodFiscale.toUpperCase())) + "</prelCodFiscale>";
	    campiInput.put("prelevatori",xmlPrelevatori);
		
		campiInput.put("sitoCodice",sitoCodice);
		campiInput.put("sitoComCodice",comuneCodiceIstatParziale);
		campiInput.put("sitoLatitudine",Double.parseDouble(latitudine));
		campiInput.put("sitoLongitudine",Double.parseDouble(longitudine));
		campiInput.put("sitoProSigla",siglaProvincia);
		
		wsPost.setCampiInput(campiInput);
		wsPost.costruisciEnvelope(db,"1");
		String response = wsPost.post(db, userId);
		String prelievoId = utilsXML.getValoreNodoXML(response,"prelievoId");
		
		if(prelievoId==null || prelievoId.equals(""))
		{
			String messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
			esito_invio= "KO";
			errore = "Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) ;
		}
		
		
	}
}
