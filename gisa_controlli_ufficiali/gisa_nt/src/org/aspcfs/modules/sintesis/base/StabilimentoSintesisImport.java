/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.apps.transfer.DataImportHandler;
import org.aspcfs.modules.sintesis.util.StabilimentoSintesisUtil;
import org.aspcfs.utils.DateUtils;

public class StabilimentoSintesisImport {
	
	
	public static final int IMPORT_DA_VALIDARE = 0;
	public static final int IMPORT_VALIDATO = 1;
	public static final int IMPORT_RIFIUTATO = 2;
	
	public static final int CHECK_INSERISCI_STABILIMENTO_INSERISCI_LINEA = 1;
	public static final int CHECK_AGGIORNA_STABILIMENTO_INSERISCI_LINEA = 2;
	public static final int CHECK_AGGIORNA_STABILIMENTO_AGGIORNA_LINEA= 3;
	public static final int CHECK_LINEA_NON_MAPPATA = 4;
	public static final int CHECK_IGNORA = 5;
	public static final int CHECK_WORKFLOW_NON_RISPETTATO = 6;

	
	private String statoSedeOperativa= null;
	private String approvalNumber = null;
	private String denominazioneSedeOperativa = null;
	private String ragioneSocialeImpresa = null;
	private String partitaIva = null;
	private String codiceFiscale = null;
	private String indirizzo = null;
	private String comune = null;
	private String siglaProvincia = null;
	private String provincia = null;
	private String regione	= null;
	private String codUfficioVeterinario = null;
	private String ufficioVeterinario = null;
	private String attivita = null;
	private String statoAttivita = null;
	private String descrizioneSezione	= null;
	private String dataInizioAttivita = null;
	private String dataFineAttivita = null;
	private String tipoAutorizzazione = null;
	private String imballaggio = null;
	private String paesiAbilitatiExport = null;
	private String remark = null;
	private String species = null;
	private String informazioniAggiuntive = null;
	
	private int idImport = -1;
	private int statoImport = -1;
	private int id = -1;
	
	private Timestamp dataProcess = null;
	private int idUtenteProcess = -1;
	
	private Timestamp dataSintesis = null;
	
	private String md5;
	
	/* dati complementari opu*/
	int opuIdLineaProduttivaMasterList = -1;
	
	int opuTipoImpresa = -1;
	int opuTipoSocieta = -1;
	String opuDomicilioDigitale = null;
	
	int opuNazioneSedeLegale = -1;
	int opuProvinciaSedeLegale = -1;
	int opuComuneSedeLegale = -1;
	int opuToponimoSedeLegale = -1;
	String opuDescrizioneProvinciaSedeLegale = "";
	String opuDescrizioneComuneSedeLegale = "";
	String opuDescrizioneToponimoSedeLegale = "";
	
	String opuViaSedeLegale = null;
	String opuCivicoSedeLegale = null;
	String opuCapSedeLegale = null;
	
	String opuNomeRappresentante = null;
	String opuCognomeRappresentante = null;
	String opuSessoRappresentante = null;
	Timestamp opuDataNascitaRappresentante = null;
	int opuNazioneNascitaRappresentante = -1;
	String opuComuneNascitaRappresentante = null;
	String opuCodiceFiscaleRappresentante = null;
	
	int opuNazioneResidenzaRappresentante = -1;
	int opuProvinciaResidenzaRappresentante = -1;
	int opuComuneResidenzaRappresentante = -1;
	int opuToponimoResidenzaRappresentante = -1;
	String opuDescrizioneProvinciaResidenzaRappresentante = "";
	String opuDescrizioneComuneResidenzaRappresentante = "";
	String opuDescrizioneToponimoResidenzaRappresentante = "";
	String opuViaResidenzaRappresentante = null;
	String opuCivicoResidenzaRappresentante = null;
	String opuCapResidenzaRappresentante = null;
	
	String opuDomicilioDigitaleRappresentante = null;
	
	String cap = null;
	double latitudine = 0;
	double longitudine = 0;
	
	int opuIdOperatore = -1;
	
	int riferimentoOrgId = -1;

	SintesisOperatore opuOperatore  = new SintesisOperatore();

	public int getOpuIdOperatore() {
		return opuIdOperatore;
	}
	public void setOpuIdOperatore(int opuIdOperatore) {
		this.opuIdOperatore = opuIdOperatore;
	}
	
	public String getStatoSedeOperativa() {
		return statoSedeOperativa;
	}
	public void setStatoSedeOperativa(String statoSedeOperativa) {
		this.statoSedeOperativa = fixString(statoSedeOperativa);
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = fixString(approvalNumber);
	}
	public String getDenominazioneSedeOperativa() {
		return denominazioneSedeOperativa;
	}
	public void setDenominazioneSedeOperativa(String denominazioneSedeOperativa) {
		this.denominazioneSedeOperativa = fixString(denominazioneSedeOperativa);
	}
	public String getRagioneSocialeImpresa() {
		return ragioneSocialeImpresa;
	}
	public void setRagioneSocialeImpresa(String ragioneSocialeImpresa) {
		this.ragioneSocialeImpresa = fixString(ragioneSocialeImpresa);
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = fixString(partitaIva);
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = fixString(codiceFiscale);
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = fixString(indirizzo);
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = fixString(comune);
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = fixString(siglaProvincia);
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = fixString(provincia);
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = fixString(regione);
	}
	public String getCodUfficioVeterinario() {
		return codUfficioVeterinario;
	}
	public void setCodUfficioVeterinario(String codUfficioVeterinario) {
		this.codUfficioVeterinario = fixString(codUfficioVeterinario);
	}
	public String getUfficioVeterinario() {
		return ufficioVeterinario;
	}
	public void setUfficioVeterinario(String ufficioVeterinario) {
		this.ufficioVeterinario = fixString(ufficioVeterinario);
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = fixString(attivita);
	}
	public String getStatoAttivita() {
		return statoAttivita;
	}
	public void setStatoAttivita(String statoAttivita) {
		this.statoAttivita = fixString(statoAttivita);
	}
	public String getDescrizioneSezione() {
		return descrizioneSezione;
	}
	public void setDescrizioneSezione(String descrizioneSezione) {
		this.descrizioneSezione = fixString(descrizioneSezione);
	}
	public String getDataInizioAttivita() {
		return dataInizioAttivita;
	}
	public void setDataInizioAttivita(String dataInizioAttivita) {
		this.dataInizioAttivita = fixString(dataInizioAttivita);
	}
	public String getDataFineAttivita() {
		return dataFineAttivita;
	}
	public void setDataFineAttivita(String dataFineAttivita) {
		this.dataFineAttivita = fixString(dataFineAttivita);
	}
	public String getTipoAutorizzazione() {
		return tipoAutorizzazione;
	}
	public void setTipoAutorizzazione(String tipoAutorizzazione) {
		this.tipoAutorizzazione = fixString(tipoAutorizzazione);
	}
	public String getImballaggio() {
		return imballaggio;
	}
	public void setImballaggio(String imballaggio) {
		this.imballaggio = fixString(imballaggio);
	}
	public String getPaesiAbilitatiExport() {
		return paesiAbilitatiExport;
	}
	public void setPaesiAbilitatiExport(String paesiAbilitatiExport) {
		this.paesiAbilitatiExport = fixString(paesiAbilitatiExport);
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = fixString(remark);
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = fixString(species);
	}
	public int getIdImport() {
		return idImport;
	}
	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}
	public String getInformazioniAggiuntive() {
		return informazioniAggiuntive;
	}
	public void setInformazioniAggiuntive(String informazioniAggiuntive) {
		this.informazioniAggiuntive = fixString(informazioniAggiuntive);
	}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																							

	
	public int getStatoImport() {
		return statoImport;
	}
	public void setStatoImport(int statoImport) {
		this.statoImport = statoImport;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	
	
	public int getOpuIdLineaProduttivaMasterList() {
		return opuIdLineaProduttivaMasterList;
	}
	public void setOpuIdLineaProduttivaMasterList(int opuIdLineaProduttivaMasterList) {
		this.opuIdLineaProduttivaMasterList = opuIdLineaProduttivaMasterList;
	}
	public int getOpuTipoImpresa() {
		return opuTipoImpresa;
	}
	public void setOpuTipoImpresa(int opuTipoImpresa) {
		this.opuTipoImpresa = opuTipoImpresa;
	}
	public void setOpuTipoImpresa(String opuTipoImpresa) {
		try { this.opuTipoImpresa = Integer.parseInt(opuTipoImpresa);} catch (Exception e){};
	}
	public int getOpuTipoSocieta() {
		return opuTipoSocieta;
	}
	public void setOpuTipoSocieta(int opuTipoSocieta) {
		this.opuTipoSocieta = opuTipoSocieta;
	}
	public void setOpuTipoSocieta(String opuTipoSocieta) {
		try { this.opuTipoSocieta = Integer.parseInt(opuTipoSocieta);} catch (Exception e){};
	}
	public String getOpuDomicilioDigitale() {
		return opuDomicilioDigitale;
	}
	public void setOpuDomicilioDigitale(String opuDomicilioDigitale) {
		this.opuDomicilioDigitale = opuDomicilioDigitale;
	}
	
	public String getOpuNomeRappresentante() {
		return opuNomeRappresentante;
	}
	public void setOpuNomeRappresentante(String opuNomeRappresentante) {
		this.opuNomeRappresentante = opuNomeRappresentante;
	}
	public String getOpuCognomeRappresentante() {
		return opuCognomeRappresentante;
	}
	public void setOpuCognomeRappresentante(String opuCognomeRappresentante) {
		this.opuCognomeRappresentante = opuCognomeRappresentante;
	}
	public String getOpuSessoRappresentante() {
		return opuSessoRappresentante;
	}
	public void setOpuSessoRappresentante(String opuSessoRappresentante) {
		this.opuSessoRappresentante = opuSessoRappresentante;
	}
	public Timestamp getOpuDataNascitaRappresentante() {
		return opuDataNascitaRappresentante;
	}
	public void setOpuDataNascitaRappresentante(Timestamp opuDataNascitaRappresentante) {
		this.opuDataNascitaRappresentante = opuDataNascitaRappresentante;
	}
	public void setOpuDataNascitaRappresentante(String opuDataNascitaRappresentante) {
		this.opuDataNascitaRappresentante = DateUtils.parseDateStringNew(opuDataNascitaRappresentante, "dd/MM/yyyy");
	}
	public int getOpuNazioneNascitaRappresentante() {
		return opuNazioneNascitaRappresentante;
	}
	public void setOpuNazioneNascitaRappresentante(int opuNazioneNascitaRappresentante) {
		this.opuNazioneNascitaRappresentante = opuNazioneNascitaRappresentante;
	}
	public void setOpuNazioneNascitaRappresentante(String opuNazioneNascitaRappresentante) {
		try { this.opuNazioneNascitaRappresentante = Integer.parseInt(opuNazioneNascitaRappresentante);} catch (Exception e){};
	}
	public String getOpuComuneNascitaRappresentante() {
		return opuComuneNascitaRappresentante;
	}
	public void setOpuComuneNascitaRappresentante(String opuComuneNascitaRappresentante) {
		this.opuComuneNascitaRappresentante = opuComuneNascitaRappresentante;
	}

	public String getOpuCodiceFiscaleRappresentante() {
		return opuCodiceFiscaleRappresentante;
	}
	public void setOpuCodiceFiscaleRappresentante(String opuCodiceFiscaleRappresentante) {
		this.opuCodiceFiscaleRappresentante = opuCodiceFiscaleRappresentante;
	}
	public int getOpuNazioneResidenzaRappresentante() {
		return opuNazioneResidenzaRappresentante;
	}
	public void setOpuNazioneResidenzaRappresentante(int opuNazioneResidenzaRappresentante) {
		this.opuNazioneResidenzaRappresentante = opuNazioneResidenzaRappresentante;
	}
	public void setOpuNazioneResidenzaRappresentante(String opuNazioneResidenzaRappresentante) {
		try { this.opuNazioneResidenzaRappresentante = Integer.parseInt(opuNazioneResidenzaRappresentante);} catch (Exception e){};
	}
	public int getOpuProvinciaResidenzaRappresentante() {
		return opuProvinciaResidenzaRappresentante;
	}
	public void setOpuProvinciaResidenzaRappresentante(int opuProvinciaResidenzaRappresentante) {
		this.opuProvinciaResidenzaRappresentante = opuProvinciaResidenzaRappresentante;
	}
	public void setOpuProvinciaResidenzaRappresentante(String opuProvinciaResidenzaRappresentante) {
		try { this.opuProvinciaResidenzaRappresentante = Integer.parseInt(opuProvinciaResidenzaRappresentante);} catch (Exception e){};
	}
	public int getOpuComuneResidenzaRappresentante() {
		return opuComuneResidenzaRappresentante;
	}
	public void setOpuComuneResidenzaRappresentante(int opuComuneResidenzaRappresentante) {
		this.opuComuneResidenzaRappresentante = opuComuneResidenzaRappresentante;
	}
	public void setOpuComuneResidenzaRappresentante(String opuComuneResidenzaRappresentante) {
		try { this.opuComuneResidenzaRappresentante = Integer.parseInt(opuComuneResidenzaRappresentante);} catch (Exception e){};
	}
	public int getOpuToponimoResidenzaRappresentante() {
		return opuToponimoResidenzaRappresentante;
	}
	public void setOpuToponimoResidenzaRappresentante(int opuToponimoResidenzaRappresentante) {
		this.opuToponimoResidenzaRappresentante = opuToponimoResidenzaRappresentante;
	}
	public void setOpuToponimoResidenzaRappresentante(String opuToponimoResidenzaRappresentante) {
		try { this.opuToponimoResidenzaRappresentante = Integer.parseInt(opuToponimoResidenzaRappresentante);} catch (Exception e){};
	}
	public String getOpuViaResidenzaRappresentante() {
		return opuViaResidenzaRappresentante;
	}
	public void setOpuViaResidenzaRappresentante(String opuViaResidenzaRappresentante) {
		this.opuViaResidenzaRappresentante = opuViaResidenzaRappresentante;
	}
	public String getOpuCivicoResidenzaRappresentante() {
		return opuCivicoResidenzaRappresentante;
	}
	public void setOpuCivicoResidenzaRappresentante(String opuCivicoResidenzaRappresentante) {
		this.opuCivicoResidenzaRappresentante = opuCivicoResidenzaRappresentante;
	}
	public String getOpuCapResidenzaRappresentante() {
		return opuCapResidenzaRappresentante;
	}
	public void setOpuCapResidenzaRappresentante(String opuCapResidenzaRappresentante) {
		this.opuCapResidenzaRappresentante = opuCapResidenzaRappresentante;
	}
	public String getOpuDomicilioDigitaleRappresentante() {
		return opuDomicilioDigitaleRappresentante;
	}
	public void setOpuDomicilioDigitaleRappresentante(String opuDomicilioDigitaleRappresentante) {
		this.opuDomicilioDigitaleRappresentante = opuDomicilioDigitaleRappresentante;
	}
	public int getOpuNazioneSedeLegale() {
		return opuNazioneSedeLegale;
	}
	public void setOpuNazioneSedeLegale(int opuNazioneSedeLegale) {
		this.opuNazioneSedeLegale = opuNazioneSedeLegale;
	}
	
	public void setOpuNazioneSedeLegale(String opuNazioneSedeLegale) {
		try {this.opuNazioneSedeLegale = Integer.parseInt(opuNazioneSedeLegale);} catch(Exception e){};
	}
	
	public int getOpuProvinciaSedeLegale() {
		return opuProvinciaSedeLegale;
	}
	public void setOpuProvinciaSedeLegale(int opuProvinciaSedeLegale) {
		this.opuProvinciaSedeLegale = opuProvinciaSedeLegale;
	}
	
	public void setOpuProvinciaSedeLegale(String opuProvinciaSedeLegale) {
		try {this.opuProvinciaSedeLegale = Integer.parseInt(opuProvinciaSedeLegale);} catch(Exception e){};
	}
	
	public int getOpuComuneSedeLegale() {
		return opuComuneSedeLegale;
	}
	public void setOpuComuneSedeLegale(int opuComuneSedeLegale) {
		this.opuComuneSedeLegale = opuComuneSedeLegale;
	}
	public void setOpuComuneSedeLegale(String opuComuneSedeLegale) {
		try {this.opuComuneSedeLegale = Integer.parseInt(opuComuneSedeLegale);} catch(Exception e){};
	}
	public int getOpuToponimoSedeLegale() {
		return opuToponimoSedeLegale;
	}
	public void setOpuToponimoSedeLegale(int opuToponimoSedeLegale) {
		this.opuToponimoSedeLegale = opuToponimoSedeLegale;
	}
	public void setOpuToponimoSedeLegale(String opuToponimoSedeLegale) {
		try {this.opuToponimoSedeLegale = Integer.parseInt(opuToponimoSedeLegale);} catch(Exception e){};
	}
	public String getOpuViaSedeLegale() {
		return opuViaSedeLegale;
	}
	public void setOpuViaSedeLegale(String opuViaSedeLegale) {
		this.opuViaSedeLegale = opuViaSedeLegale;
	}
	public String getOpuCivicoSedeLegale() {
		return opuCivicoSedeLegale;
	}
	public void setOpuCivicoSedeLegale(String opuCivicoSedeLegale) {
		this.opuCivicoSedeLegale = opuCivicoSedeLegale;
	}
	public String getOpuCapSedeLegale() {
		return opuCapSedeLegale;
	}
	public void setOpuCapSedeLegale(String opuCapSedeLegale) {
		this.opuCapSedeLegale = opuCapSedeLegale;
	}
	public SintesisOperatore getOpuOperatore() {
		return opuOperatore;
	}
	public void setOpuOperatore(SintesisOperatore opuOperatore) {
		this.opuOperatore = opuOperatore;
	}
	public Timestamp getDataProcess() {
		return dataProcess;
	}
	public void setDataProcess(Timestamp dataProcess) {
		this.dataProcess = dataProcess;
	}
	public int getIdUtenteProcess() {
		return idUtenteProcess;
	}
	public void setIdUtenteProcess(int idUtenteProcess) {
		this.idUtenteProcess = idUtenteProcess;
	}
	public String getOpuDescrizioneProvinciaSedeLegale() {
		return opuDescrizioneProvinciaSedeLegale;
	}
	public void setOpuDescrizioneProvinciaSedeLegale(String opuDescrizioneProvinciaSedeLegale) {
		this.opuDescrizioneProvinciaSedeLegale = opuDescrizioneProvinciaSedeLegale;
	}
	public String getOpuDescrizioneComuneSedeLegale() {
		return opuDescrizioneComuneSedeLegale;
	}
	public void setOpuDescrizioneComuneSedeLegale(String opuDescrizioneComuneSedeLegale) {
		this.opuDescrizioneComuneSedeLegale = opuDescrizioneComuneSedeLegale;
	}
	public String getOpuDescrizioneToponimoSedeLegale() {
		return opuDescrizioneToponimoSedeLegale;
	}
	public void setOpuDescrizioneToponimoSedeLegale(String opuDescrizioneToponimoSedeLegale) {
		this.opuDescrizioneToponimoSedeLegale = opuDescrizioneToponimoSedeLegale;
	}
	public String getOpuDescrizioneProvinciaResidenzaRappresentante() {
		return opuDescrizioneProvinciaResidenzaRappresentante;
	}
	public void setOpuDescrizioneProvinciaResidenzaRappresentante(String opuDescrizioneProvinciaResidenzaRappresentante) {
		this.opuDescrizioneProvinciaResidenzaRappresentante = opuDescrizioneProvinciaResidenzaRappresentante;
	}
	public String getOpuDescrizioneComuneResidenzaRappresentante() {
		return opuDescrizioneComuneResidenzaRappresentante;
	}
	public void setOpuDescrizioneComuneResidenzaRappresentante(String opuDescrizioneComuneResidenzaRappresentante) {
		this.opuDescrizioneComuneResidenzaRappresentante = opuDescrizioneComuneResidenzaRappresentante;
	}
	public String getOpuDescrizioneToponimoResidenzaRappresentante() {
		return opuDescrizioneToponimoResidenzaRappresentante;
	}
	public void setOpuDescrizioneToponimoResidenzaRappresentante(String opuDescrizioneToponimoResidenzaRappresentante) {
		this.opuDescrizioneToponimoResidenzaRappresentante = opuDescrizioneToponimoResidenzaRappresentante;
	}
	public StabilimentoSintesisImport (Row nextRow){
		 Iterator<Cell> cellIterator = nextRow.cellIterator();
      	
		 int i = 0;
         while (cellIterator.hasNext()) {
        	 Cell cell = cellIterator.next();
        	 switch(i){
        	 case 0: setStatoSedeOperativa(cell.getStringCellValue()); break;
        	 case 1: setApprovalNumber(cell.getStringCellValue()); break;
        	 case 2: setDenominazioneSedeOperativa(cell.getStringCellValue()); break;
        	 case 3: setRagioneSocialeImpresa(cell.getStringCellValue()); break;
        	 case 4: setPartitaIva(cell.getStringCellValue()); break;
        	 case 5: setCodiceFiscale(cell.getStringCellValue()); break;
        	 case 6: setIndirizzo(cell.getStringCellValue()); break;
        	 case 7: setComune(cell.getStringCellValue()); break;
        	 case 8: setSiglaProvincia(cell.getStringCellValue()); break;
        	 case 9: setProvincia(cell.getStringCellValue()); break;
        	 case 10: setRegione(cell.getStringCellValue()); break;
        	 case 11: setCodUfficioVeterinario(cell.getStringCellValue()); break;
        	 case 12: setUfficioVeterinario(cell.getStringCellValue()); break;
        	 case 13: setAttivita(cell.getStringCellValue()); break;
        	 case 14: setStatoAttivita(cell.getStringCellValue()); break;
        	 case 15: setDescrizioneSezione(cell.getStringCellValue()); break;
        	 case 16: setDataInizioAttivita(cell.getStringCellValue()); break;
        	 case 17: setDataFineAttivita(cell.getStringCellValue()); break;
        	 case 18: setTipoAutorizzazione(cell.getStringCellValue()); break;
        	 case 19: setImballaggio(cell.getStringCellValue()); break;
        	 case 20: setPaesiAbilitatiExport(cell.getStringCellValue()); break;
        	 case 21: setRemark(cell.getStringCellValue()); break;
        	 case 22: setSpecies(cell.getStringCellValue()); break;	 
        	 case 23: setInformazioniAggiuntive(cell.getStringCellValue()); break;	 
        	 }
             i++; 
           
          
         }
	}
	
	public void insert(Connection db) throws SQLException{
		
		PreparedStatement pst = db.prepareStatement("INSERT INTO public.sintesis_stabilimenti_import("
				+ "stato_sede_operativa, approval_number, denominazione_sede_operativa,  "
				+ "ragione_sociale_impresa, partita_iva, codice_fiscale, indirizzo, comune, "
				+ "sigla_provincia, provincia, regione, cod_ufficio_veterinario,   "
				+ "ufficio_veterinario, attivita, stato_attivita, descrizione_sezione,  data_inizio_attivita, data_fine_attivita, "
				+ "tipo_autorizzazione,  imballaggio, paesi_abilitati_export, remark, species, informazioni_aggiuntive, opu_id_linea_produttiva_master_list, opu_id_operatore,  riferimento_org_id, md5, id_import) "
				+ "VALUES (?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?,   ?, ?, ?,   ?, ?, ?, ?,?, ?,?,  ?, ?)");

				int i = 0;
				
				pst.setString(++i, statoSedeOperativa);
				pst.setString(++i, approvalNumber);
				pst.setString(++i, denominazioneSedeOperativa);
				pst.setString(++i, ragioneSocialeImpresa);
				pst.setString(++i, partitaIva);
				pst.setString(++i, codiceFiscale);
				pst.setString(++i, indirizzo);
				pst.setString(++i, comune);
				pst.setString(++i, siglaProvincia);
				pst.setString(++i, provincia);
				pst.setString(++i, regione);
				pst.setString(++i, codUfficioVeterinario);
				pst.setString(++i, ufficioVeterinario);
				pst.setString(++i, attivita);
				pst.setString(++i, statoAttivita);
				pst.setString(++i, descrizioneSezione);
				pst.setString(++i, dataInizioAttivita);
				pst.setString(++i, dataFineAttivita);
				pst.setString(++i, tipoAutorizzazione);
				pst.setString(++i, imballaggio);
				pst.setString(++i, paesiAbilitatiExport);
				pst.setString(++i, remark);
				pst.setString(++i, species);
				pst.setString(++i, informazioniAggiuntive);
				pst.setInt(++i, opuIdLineaProduttivaMasterList);
				pst.setInt(++i, opuIdOperatore);
				pst.setInt(++i, riferimentoOrgId);
				pst.setString(++i, md5);
				pst.setInt(++i, idImport);
				pst.execute();
	}

	
	public StabilimentoSintesisImport(){
	}
	
	public StabilimentoSintesisImport(ResultSet rs) throws SQLException{
		buildRecord(rs);
	}
	
	
	public StabilimentoSintesisImport(Connection db, int id) throws SQLException{
		
		PreparedStatement pst = db.prepareStatement("select * from sintesis_stabilimenti_import where id = ?");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setOpuOperatore(db);
		}
	}
	
	private void setOpuOperatore(Connection db) throws SQLException {
		if (this.opuIdOperatore>0){
			SintesisOperatore op = new SintesisOperatore(db, this.opuIdOperatore);
			this.opuOperatore = op;
		}
		
	}
	private void buildRecord(ResultSet rs) throws SQLException{
		
		statoSedeOperativa=  rs.getString("stato_sede_operativa");
		approvalNumber =  rs.getString("approval_number");
		denominazioneSedeOperativa =  rs.getString("denominazione_sede_operativa");
		ragioneSocialeImpresa =  rs.getString("ragione_sociale_impresa");
		partitaIva =  rs.getString("partita_iva");
		codiceFiscale =  rs.getString("codice_fiscale");
		indirizzo = rs.getString("indirizzo");
		comune = rs.getString("comune");
		siglaProvincia = rs.getString("sigla_provincia");
		provincia = rs.getString("provincia");
		regione	= rs.getString("regione");
		codUfficioVeterinario = rs.getString("cod_ufficio_veterinario");
		ufficioVeterinario = rs.getString("ufficio_veterinario");
		attivita = rs.getString("attivita");
		statoAttivita = rs.getString("stato_attivita");
		descrizioneSezione	= rs.getString("descrizione_sezione");
		dataInizioAttivita = rs.getString("data_inizio_attivita");
		dataFineAttivita = rs.getString("data_fine_attivita");
		tipoAutorizzazione = rs.getString("tipo_autorizzazione");
		imballaggio = rs.getString("imballaggio");
		paesiAbilitatiExport = rs.getString("paesi_abilitati_export");
		remark = rs.getString("remark");
		species = rs.getString("species");
		informazioniAggiuntive = rs.getString("informazioni_aggiuntive");
		idImport = rs.getInt("id_import");
		statoImport = rs.getInt("stato_import");
		id = rs.getInt("id");

		dataProcess=  rs.getTimestamp("data_process");
		idUtenteProcess =  rs.getInt("id_utente_process");
		
		try{ 
			if(rs.findColumn("data_sintesis")>0)
				dataSintesis = rs.getTimestamp("data_sintesis");
		} catch (SQLException e){}
		
		md5 = rs.getString("md5");

		//dati complementari opu
		opuIdLineaProduttivaMasterList = rs.getInt("opu_id_linea_produttiva_master_list");
		opuIdOperatore = rs.getInt("opu_id_operatore");

		opuTipoImpresa = rs.getInt("opu_tipo_impresa");
		opuTipoSocieta = rs.getInt("opu_tipo_societa");
		opuDomicilioDigitale = rs.getString("opu_domicilio_digitale");
		opuNomeRappresentante = rs.getString("opu_nome_rappresentante");
		opuCognomeRappresentante = rs.getString("opu_cognome_rappresentante");
		opuSessoRappresentante = rs.getString("opu_sesso_rappresentante");
		opuDataNascitaRappresentante = rs.getTimestamp("opu_data_nascita_rappresentante");
		opuNazioneNascitaRappresentante = rs.getInt("opu_nazione_nascita_rappresentante");
		opuComuneNascitaRappresentante = rs.getString("opu_comune_nascita_rappresentante");
		opuCodiceFiscaleRappresentante = rs.getString("opu_codice_fiscale_rappresentante");
		opuNazioneResidenzaRappresentante = rs.getInt("opu_nazione_residenza_rappresentante");
		opuProvinciaResidenzaRappresentante = rs.getInt("opu_provincia_residenza_rappresentante");
		opuComuneResidenzaRappresentante = rs.getInt("opu_comune_residenza_rappresentante");
		opuToponimoResidenzaRappresentante = rs.getInt("opu_toponimo_residenza_rappresentante");
		
		opuDescrizioneProvinciaResidenzaRappresentante = rs.getString("opu_descrizione_provincia_residenza_rappresentante");
		opuDescrizioneComuneResidenzaRappresentante = rs.getString("opu_descrizione_comune_residenza_rappresentante");
		opuDescrizioneToponimoResidenzaRappresentante = rs.getString("opu_descrizione_toponimo_residenza_rappresentante");
		
		opuViaResidenzaRappresentante = rs.getString("opu_via_residenza_rappresentante");
		opuCivicoResidenzaRappresentante = rs.getString("opu_civico_residenza_rappresentante");
		opuCapResidenzaRappresentante = rs.getString("opu_cap_residenza_rappresentante");
		opuDomicilioDigitaleRappresentante = rs.getString("opu_domicilio_digitale_rappresentante");

		opuNazioneSedeLegale = rs.getInt("opu_nazione_sede_legale");
		opuProvinciaSedeLegale = rs.getInt("opu_provincia_sede_legale");
		opuComuneSedeLegale = rs.getInt("opu_comune_sede_legale");
		opuToponimoSedeLegale = rs.getInt("opu_toponimo_sede_legale");
		
		opuDescrizioneProvinciaSedeLegale = rs.getString("opu_descrizione_provincia_sede_legale");
		opuDescrizioneComuneSedeLegale = rs.getString("opu_descrizione_comune_sede_legale");
		opuDescrizioneToponimoSedeLegale = rs.getString("opu_descrizione_toponimo_sede_legale");
		
		opuViaSedeLegale = rs.getString("opu_via_sede_legale");
		opuCivicoSedeLegale = rs.getString("opu_civico_sede_legale");
		opuCapSedeLegale = rs.getString("opu_cap_sede_legale");
		
		latitudine = rs.getDouble("latitudine");
		longitudine = rs.getDouble("longitudine");
		cap = rs.getString("cap");
		riferimentoOrgId = rs.getInt("riferimento_org_id");
		
	}
	public void aggiornaLineaProduttivaMasterList(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set opu_id_linea_produttiva_master_list = ? where id = ?");
		pst.setInt(1, opuIdLineaProduttivaMasterList);
		pst.setInt(2, id);
		pst.executeUpdate();
		
	}
	public void completaDati(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set opu_tipo_impresa = ?, opu_tipo_societa = ?, opu_domicilio_digitale = ?, "
				+ " opu_nome_rappresentante = ?, opu_cognome_rappresentante = ?,  opu_sesso_rappresentante = ?, opu_data_nascita_rappresentante = ?, "
				+ "opu_nazione_nascita_rappresentante = ?,  opu_comune_nascita_rappresentante = ?, opu_codice_fiscale_rappresentante = ?, "
				+ "opu_nazione_residenza_rappresentante = ?, opu_provincia_residenza_rappresentante = ?, opu_comune_residenza_rappresentante = ?, opu_toponimo_residenza_rappresentante = ?, opu_via_residenza_rappresentante = ?, "
				+ "opu_civico_residenza_rappresentante = ?, opu_cap_residenza_rappresentante = ?, opu_domicilio_digitale_rappresentante = ?,  "
				+ "opu_nazione_sede_legale = ?, opu_provincia_sede_legale = ?, opu_comune_sede_legale = ?, opu_toponimo_sede_legale = ?, opu_via_sede_legale = ?, "
				+ "opu_civico_sede_legale = ?, opu_cap_sede_legale = ? , latitudine = ?, longitudine = ?, cap = ?,  "
				+ "opu_descrizione_provincia_residenza_rappresentante = ?, opu_descrizione_comune_residenza_rappresentante= ?, opu_descrizione_toponimo_residenza_rappresentante = ?, "
				+ "opu_descrizione_provincia_sede_legale = ?, opu_descrizione_comune_sede_legale = ?, opu_descrizione_toponimo_sede_legale = ? "
				+ "where id = ?");
		
		int i = 0;
		pst.setInt(++i, opuTipoImpresa);
		pst.setInt(++i, opuTipoSocieta);
		pst.setString(++i, opuDomicilioDigitale);
		pst.setString(++i, opuNomeRappresentante);
		pst.setString(++i, opuCognomeRappresentante);
		pst.setString(++i, opuSessoRappresentante);
		pst.setTimestamp(++i, opuDataNascitaRappresentante);
		pst.setInt(++i, opuNazioneNascitaRappresentante);
		pst.setString(++i, opuComuneNascitaRappresentante);
		pst.setString(++i, opuCodiceFiscaleRappresentante);
		pst.setInt(++i, opuNazioneResidenzaRappresentante);
		pst.setInt(++i, opuProvinciaResidenzaRappresentante);
		pst.setInt(++i, opuComuneResidenzaRappresentante);
		pst.setInt(++i, opuToponimoResidenzaRappresentante);
		pst.setString(++i, opuViaResidenzaRappresentante);
		pst.setString(++i, opuCivicoResidenzaRappresentante);
		pst.setString(++i, opuCapResidenzaRappresentante);
		pst.setString(++i, opuDomicilioDigitaleRappresentante);
		pst.setInt(++i, opuNazioneSedeLegale);
		pst.setInt(++i, opuProvinciaSedeLegale);
		pst.setInt(++i, opuComuneSedeLegale);
		pst.setInt(++i, opuToponimoSedeLegale);
		pst.setString(++i, opuViaSedeLegale);
		pst.setString(++i, opuCivicoSedeLegale);
		pst.setString(++i, opuCapSedeLegale);
		pst.setDouble(++i, latitudine);
		pst.setDouble(++i, longitudine);
		pst.setString(++i, cap);

		pst.setString(++i, opuDescrizioneProvinciaResidenzaRappresentante);
		pst.setString(++i, opuDescrizioneComuneResidenzaRappresentante);
		pst.setString(++i, opuDescrizioneToponimoResidenzaRappresentante);
		
		pst.setString(++i, opuDescrizioneProvinciaSedeLegale);
		pst.setString(++i, opuDescrizioneComuneSedeLegale);
		pst.setString(++i, opuDescrizioneToponimoSedeLegale);
		
		pst.setInt(++i, id);
		pst.executeUpdate();
	}
	
	public void propagaCompletaDati(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set opu_tipo_impresa = ?, opu_tipo_societa = ?, opu_domicilio_digitale = ?, "
				+ " opu_nome_rappresentante = ?, opu_cognome_rappresentante = ?,  opu_sesso_rappresentante = ?, opu_data_nascita_rappresentante = ?, "
				+ "opu_nazione_nascita_rappresentante = ?,  opu_comune_nascita_rappresentante = ?, opu_codice_fiscale_rappresentante = ?, "
				+ "opu_nazione_residenza_rappresentante = ?, opu_provincia_residenza_rappresentante = ?, opu_comune_residenza_rappresentante = ?, opu_toponimo_residenza_rappresentante = ?, opu_via_residenza_rappresentante = ?, "
				+ "opu_civico_residenza_rappresentante = ?, opu_cap_residenza_rappresentante = ?, opu_domicilio_digitale_rappresentante = ?,  "
				+ "opu_nazione_sede_legale = ?, opu_provincia_sede_legale = ?, opu_comune_sede_legale = ?, opu_toponimo_sede_legale = ?, opu_via_sede_legale = ?, "
				+ "opu_civico_sede_legale = ?, opu_cap_sede_legale = ? , latitudine = ?, longitudine = ?, cap = ?,   "
				+ "opu_descrizione_provincia_residenza_rappresentante = ?, opu_descrizione_comune_residenza_rappresentante= ?, opu_descrizione_toponimo_residenza_rappresentante = ?, "
				+ "opu_descrizione_provincia_sede_legale = ?, opu_descrizione_comune_sede_legale = ?, opu_descrizione_toponimo_sede_legale = ? "
				+ "where stato_import = 0 and id <> ? and partita_iva = ? and codice_fiscale = ?  and (length(partita_iva)>4 or length(codice_fiscale)>4) ");
		
		int i = 0;
		pst.setInt(++i, opuTipoImpresa);
		pst.setInt(++i, opuTipoSocieta);
		pst.setString(++i, opuDomicilioDigitale);
		pst.setString(++i, opuNomeRappresentante);
		pst.setString(++i, opuCognomeRappresentante);
		pst.setString(++i, opuSessoRappresentante);
		pst.setTimestamp(++i, opuDataNascitaRappresentante);
		pst.setInt(++i, opuNazioneNascitaRappresentante);
		pst.setString(++i, opuComuneNascitaRappresentante);
		pst.setString(++i, opuCodiceFiscaleRappresentante);
		pst.setInt(++i, opuNazioneResidenzaRappresentante);
		pst.setInt(++i, opuProvinciaResidenzaRappresentante);
		pst.setInt(++i, opuComuneResidenzaRappresentante);
		pst.setInt(++i, opuToponimoResidenzaRappresentante);
		pst.setString(++i, opuViaResidenzaRappresentante);
		pst.setString(++i, opuCivicoResidenzaRappresentante);
		pst.setString(++i, opuCapResidenzaRappresentante);
		pst.setString(++i, opuDomicilioDigitaleRappresentante);
		pst.setInt(++i, opuNazioneSedeLegale);
		pst.setInt(++i, opuProvinciaSedeLegale);
		pst.setInt(++i, opuComuneSedeLegale);
		pst.setInt(++i, opuToponimoSedeLegale);
		pst.setString(++i, opuViaSedeLegale);
		pst.setString(++i, opuCivicoSedeLegale);
		pst.setString(++i, opuCapSedeLegale);
		pst.setDouble(++i, latitudine);
		pst.setDouble(++i, longitudine);
		pst.setString(++i, cap);

		pst.setString(++i, opuDescrizioneProvinciaResidenzaRappresentante);
		pst.setString(++i, opuDescrizioneComuneResidenzaRappresentante);
		pst.setString(++i, opuDescrizioneToponimoResidenzaRappresentante);
		
		pst.setString(++i, opuDescrizioneProvinciaSedeLegale);
		pst.setString(++i, opuDescrizioneComuneSedeLegale);
		pst.setString(++i, opuDescrizioneToponimoSedeLegale);
		
		pst.setInt(++i, id);
		pst.setString(++i, partitaIva);
		pst.setString(++i, codiceFiscale);

		pst.executeUpdate();
		
	}
	public void codificaLinea(Connection db) throws SQLException {

		int idLinea = StabilimentoSintesisUtil.codificaLinea(db, attivita, descrizioneSezione);
		this.opuIdLineaProduttivaMasterList = idLinea;
		
	}
	
	public void codificaOperatore(Connection db) throws SQLException {
		int idOperatore = -1;
		String sqlSelect = "select id from sintesis_operatore where ragione_sociale ilike ? and partita_iva ilike ? and codice_fiscale_impresa ilike ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, ragioneSocialeImpresa);
		pstSelect.setString(2, partitaIva);
		pstSelect.setString(3, codiceFiscale);

		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idOperatore = rsSelect.getInt("id");
		this.opuIdOperatore = idOperatore;
	}
	public void chiudi(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set stato_import = ?, data_process = now(), id_utente_process = ? where id = ?");
		pst.setInt(1, IMPORT_VALIDATO);
		pst.setInt(2, idUtenteProcess);
		pst.setInt(3, id);
		pst.executeUpdate();
		
	}
	public void aggiornaOpuIdOperatore(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set opu_id_operatore = ? where id = ?");
		pst.setInt(1, opuIdOperatore);
		pst.setInt(2, id);
		pst.executeUpdate();
		
		
	}
	public void aggiornaRiferimentoOrgId(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set riferimento_org_id = ? where id = ?");
		pst.setInt(1, riferimentoOrgId);
		pst.setInt(2, id);
		pst.executeUpdate();
		
		
	}
	public void rifiuta(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_stabilimenti_import set stato_import = ?,  data_process = now(), id_utente_process = ? where id = ?");
		pst.setInt(1, IMPORT_RIFIUTATO);
		pst.setInt(2, idUtenteProcess);
		pst.setInt(3, id);
		pst.executeUpdate();
	}
	
	public void setMd5() throws NoSuchAlgorithmException, IOException{
		
		String total = statoSedeOperativa+approvalNumber+denominazioneSedeOperativa+ragioneSocialeImpresa+partitaIva+codiceFiscale+indirizzo+comune+siglaProvincia+provincia+
				regione+codUfficioVeterinario+attivita+statoAttivita+descrizioneSezione+dataInizioAttivita+dataFineAttivita+tipoAutorizzazione+imballaggio;
				//+paesiAbilitatiExport+remark+species+informazioniAggiuntive;
	
		this.md5 = calcolaMd5(total);
	 
	}
	
	public String calcolaMd5(String string) throws IOException, NoSuchAlgorithmException{
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(string.getBytes(Charset.forName("UTF8")));
		byte[] resultByte = messageDigest.digest();
		String result = new String(Hex.encodeHex(resultByte));
		return result;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public int getRiferimentoOrgId() {
		return riferimentoOrgId;
	}
	public void setRiferimentoOrgId(int riferimentoOrgId) {
		this.riferimentoOrgId = riferimentoOrgId;
	}
	public boolean md5Presente(Connection db) throws SQLException {
		boolean presente = false;
		
		PreparedStatement pst = db.prepareStatement("select * from sintesis_stabilimenti_import where md5 = ?");
		pst.setString(1, md5);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			presente = true;
		return presente;
	}
	public void aggiornaDatiDaGisaSintesis(Connection db, SintesisOperatore op) throws SQLException {

		this.opuTipoImpresa = op.getTipoImpresa();
		this.opuTipoSocieta = op.getTipoSocieta();
		this.opuDomicilioDigitale = op.getDomicilioDigitale();
		
		if (op.getRappLegale()!=null){
			this.opuNomeRappresentante = op.getRappLegale().getNome();
			this.opuCognomeRappresentante = op.getRappLegale().getCognome();
			this.opuSessoRappresentante= op.getRappLegale().getSesso();
			this.opuDataNascitaRappresentante= op.getRappLegale().getDataNascita();
			this.opuNazioneNascitaRappresentante= op.getRappLegale().getNazioneNascita();
			this.opuComuneNascitaRappresentante= op.getRappLegale().getComuneNascita();
			this.opuCodiceFiscaleRappresentante= op.getRappLegale().getCodiceFiscale();
			this.opuDomicilioDigitaleRappresentante= op.getRappLegale().getDomicilioDigitale();
			
			if (op.getRappLegale().getIndirizzo()!=null){
				this.opuNazioneResidenzaRappresentante= op.getRappLegale().getIndirizzo().getNazione();
				this.opuProvinciaResidenzaRappresentante= op.getRappLegale().getIndirizzo().getIdProvincia();
				this.opuComuneResidenzaRappresentante= op.getRappLegale().getIndirizzo().getComune();
				this.opuToponimoResidenzaRappresentante = op.getRappLegale().getIndirizzo().getToponimo();
				this.opuViaResidenzaRappresentante = op.getRappLegale().getIndirizzo().getVia();
				this.opuCivicoResidenzaRappresentante= op.getRappLegale().getIndirizzo().getCivico();
				this.opuCapResidenzaRappresentante= op.getRappLegale().getIndirizzo().getCap();
				this.opuDescrizioneProvinciaResidenzaRappresentante=op.getRappLegale().getIndirizzo().getDescrizione_provincia();
				this.opuDescrizioneComuneResidenzaRappresentante= op.getRappLegale().getIndirizzo().getDescrizioneComune();
				this.opuDescrizioneToponimoResidenzaRappresentante= op.getRappLegale().getIndirizzo().getDescrizioneToponimo();
			}
		}
		
		if (op.getSedeLegale()!=null){
			this.opuNazioneSedeLegale= op.getSedeLegale().getNazione();
			this.opuProvinciaSedeLegale= op.getSedeLegale().getIdProvincia();
			this.opuComuneSedeLegale= op.getSedeLegale().getComune();
			this.opuToponimoSedeLegale= op.getSedeLegale().getToponimo();
			this.opuViaSedeLegale= op.getSedeLegale().getVia();
			this.opuCivicoSedeLegale= op.getSedeLegale().getCivico();
			this.opuCapSedeLegale= op.getSedeLegale().getCap();
			this.opuDescrizioneProvinciaSedeLegale= op.getSedeLegale().getDescrizione_provincia();
			this.opuDescrizioneComuneSedeLegale= op.getSedeLegale().getDescrizioneComune();
			this.opuDescrizioneToponimoSedeLegale= op.getSedeLegale().getDescrizioneToponimo();
		}
		
		
		
		this.completaDati(db);
		
		
	}
	
	public void aggiornaDatiDaGisaOrganization(Connection db, Organization org) throws SQLException {

		this.opuTipoImpresa = -1;
		this.opuTipoSocieta = -1;
		this.opuDomicilioDigitale = org.getEmailRappresentante();
		
			this.opuNomeRappresentante = org.getNomeRappresentante();
			this.opuCognomeRappresentante = org.getCognomeRappresentante();
			this.opuSessoRappresentante= "M";
			setOpuDataNascitaRappresentante(org.getDataNascitaRappresentante());
			this.opuNazioneNascitaRappresentante= 106;
			this.opuComuneNascitaRappresentante= org.getLuogoNascitaRappresentante();
			this.opuCodiceFiscaleRappresentante= org.getCodiceFiscaleRappresentante();
			this.opuDomicilioDigitaleRappresentante= org.getEmailRappresentante();
			
				this.opuNazioneResidenzaRappresentante=106;
				this.opuProvinciaResidenzaRappresentante= -1;
				this.opuComuneResidenzaRappresentante= -1;
				this.opuToponimoResidenzaRappresentante =100;
				this.opuViaResidenzaRappresentante = "";
				this.opuCivicoResidenzaRappresentante= "";
				this.opuCapResidenzaRappresentante= "";
				this.opuDescrizioneProvinciaResidenzaRappresentante= org.getProv_legale_rapp();
				this.opuDescrizioneComuneResidenzaRappresentante= org.getCity_legale_rapp();
				this.opuDescrizioneToponimoResidenzaRappresentante= "VIA";
		
				
				
				
				Address sedeLegale = org.getAddress("1");
				this.opuNazioneSedeLegale= 106;
				this.opuProvinciaSedeLegale= -1;
				this.opuComuneSedeLegale= -1;
				this.opuToponimoSedeLegale= 100;
				this.opuViaSedeLegale= sedeLegale.getStreetAddressLine1();
				this.opuCivicoSedeLegale= "";
				this.opuCapSedeLegale= sedeLegale.getZip();
				this.opuDescrizioneProvinciaSedeLegale= sedeLegale.getState();
				this.opuDescrizioneComuneSedeLegale= sedeLegale.getCity();
				this.opuDescrizioneToponimoSedeLegale= "VIA";
		
		
		
				this.completaDati(db);
		
		
	}
	
//	public void recuperaDatiDaOrganization(Connection db){
//		PreparedStatement pst;
//		try {
//			pst = db.prepareStatement("select riferimento_id from ricerche_anagrafiche_old_materializzata where btrim(num_riconoscimento)  ilike btrim(?) and ragione_sociale ilike ? "
//					+ "and btrim(partita_iva) ilike ? and asl_rif::text = ? and id_norma in (5,6) and riferimento_id_nome ilike ?");
//		
//		int i = 0;
//		pst.setString(++i,  approvalNumber);
//		pst.setString(++i, ragioneSocialeImpresa);
//		pst.setString(++i, partitaIva);
//		pst.setString(++i, codUfficioVeterinario);
//		pst.setString(++i, "orgId");
//		ResultSet rs = pst.executeQuery();
//		if (rs.next()){
//			riferimentoOrgId = rs.getInt("riferimento_id");
//		}
//		
//		if (riferimentoOrgId>0){
//			aggiornaRiferimentoOrgId(db);
//			Organization org = new Organization(db, riferimentoOrgId);
//			aggiornaDatiDaGisaOrganization(db, org);
//		}
//		
//		
//		
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
	
	public void recuperaDatiDaSintesis(Connection db){
	
		try {
			SintesisOperatore op = new SintesisOperatore();
		op.setRagioneSociale(ragioneSocialeImpresa);
		op.setPartitaIva(partitaIva);
		op.setCodiceFiscaleImpresa(codiceFiscale);
		op.buildDaCampi(db);
		if (op.getIdOperatore()>0){
			opuIdOperatore = op.getIdOperatore();
			aggiornaOpuIdOperatore(db);
			aggiornaDatiDaGisaSintesis(db, op);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public Object[] processaRichiesta(Connection db, int userId, boolean ignoraFlussoStati) throws SQLException{

		String msg = "";
		int codiceUscita = -1;
		SintesisRelazioneLineaProduttiva relEsistente = null;
		SintesisRelazioneLineaProduttiva rel = null;
		SintesisStabilimento stabEsistente = null;
		Object[] outputValidazione = new Object[]{codiceUscita,msg,relEsistente,rel, stabEsistente};
		
		LogImport log = null;
		log = new LogImport(db, getIdImport());
		
		stabEsistente = new SintesisStabilimento(db, approvalNumber);
		StabilimentoImportUtil.stampaLog("[SINTESIS] stabEsistente id: "+stabEsistente.getIdStabilimento()+".");

					//genera oggetto operatore
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto operatore.");
			SintesisOperatore op = new SintesisOperatore();
			op.setRagioneSociale(getRagioneSocialeImpresa());
			op.setPartitaIva(getPartitaIva());
			op.setCodiceFiscaleImpresa(getCodiceFiscale());
			op.setTipoImpresa(getOpuTipoImpresa());
			op.setTipoSocieta(getOpuTipoSocieta());
			op.setDomicilioDigitale(getOpuDomicilioDigitale());
			
				//genera oggetto indirizzo legale
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto indirizzo sede legale.");
			SintesisIndirizzo indLegale = new SintesisIndirizzo();
			indLegale.setToponimo(getOpuToponimoSedeLegale());
			indLegale.setCivico(getOpuCivicoSedeLegale());
			indLegale.setVia(getOpuViaSedeLegale());
			indLegale.setComune(getOpuComuneSedeLegale());
			indLegale.setIdProvincia(getOpuProvinciaSedeLegale());
			indLegale.setCap(getOpuCapSedeLegale());
			
			//genera oggetto soggetto fisico
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto rappresentante legale.");
			SintesisSoggettoFisico sogg = new SintesisSoggettoFisico();
			sogg.setNome(getOpuNomeRappresentante());
			sogg.setCognome(getOpuCognomeRappresentante());
			sogg.setComuneNascita(getOpuComuneNascitaRappresentante());
			sogg.setNazioneNascita(getOpuNazioneNascitaRappresentante());
			sogg.setSesso(getOpuSessoRappresentante());
			sogg.setDataNascita(getOpuDataNascitaRappresentante());
			sogg.setCodiceFiscale(getOpuCodiceFiscaleRappresentante());
			sogg.setDomicilioDigitale(getOpuDomicilioDigitaleRappresentante());

			
			//genera oggetto indirizzo residenza
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto indirizzo residenza.");
			SintesisIndirizzo indResidenza = new SintesisIndirizzo();
			indResidenza.setToponimo(getOpuToponimoResidenzaRappresentante());
			indResidenza.setCivico(getOpuCivicoResidenzaRappresentante());
			indResidenza.setVia(getOpuViaResidenzaRappresentante());
			indResidenza.setComune(getOpuComuneResidenzaRappresentante());
			indResidenza.setIdProvincia(getOpuProvinciaResidenzaRappresentante());
			indResidenza.setCap(getOpuCapResidenzaRappresentante());
			
			
					//genera oggetto indirizzo
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto indirizzo.");
			SintesisIndirizzo ind = new SintesisIndirizzo();
			ind.setVia(getIndirizzo());
			ind.setDescrizioneComune(getComune());
			ind.setDescrizione_provincia(getProvincia());
			ind.setIdAsl(getCodUfficioVeterinario());
			ind.setLatitudine(latitudine);
			ind.setLongitudine(longitudine);
			ind.setCap(cap);
			ind.codificaCampi(db);
			
					//genera oggetto stabilimento
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto stabilimento.");
			SintesisStabilimento stab = new SintesisStabilimento();
			stab.setOperatore(op);
			stab.setIndirizzo(ind);
			stab.setApprovalNumber(getApprovalNumber());
			stab.setDenominazione(getDenominazioneSedeOperativa());
			stab.setSintesisDescrizioneStatoSedeOperativa(getStatoSedeOperativa());
			stab.setRiferimentoOrgId(getRiferimentoOrgId());
			stab.codificaCampi(db);

					//genera oggetto linea
			StabilimentoImportUtil.stampaLog("[SINTESIS] Costruisco oggetto linea produttiva.");
			rel = new SintesisRelazioneLineaProduttiva();
			rel.setIdLineaMasterList(getOpuIdLineaProduttivaMasterList());
			rel.setStabilimento(stab);
			rel.setSpecies(getSpecies());
			rel.setRemark(getRemark());
			rel.setTipoAutorizzazione(getTipoAutorizzazione());
			rel.setImballaggio(getImballaggio());
			rel.setPaesiAbilitatiExport(getPaesiAbilitatiExport());
			rel.setSintesisAttivita(getAttivita());
			rel.setSintesisDescrizioneSezione(getDescrizioneSezione());
			rel.setSintesisDescrizioneStato(getStatoAttivita());
			rel.setDataInizio(getDataInizioAttivita());
			rel.setDataFine(getDataFineAttivita());
			rel.codificaCampi(db);
			
			relEsistente = new SintesisRelazioneLineaProduttiva(db, rel.getStabilimento().getApprovalNumber(), rel.getIdLineaMasterList());
			relEsistente.setPathCompleto(db);
			StabilimentoImportUtil.stampaLog("[SINTESIS] relEsistente id: "+relEsistente.getIdRelazione()+" ; linea: "+relEsistente.getPathCompleto());

			codiceUscita = controllaValiditaDati(rel, relEsistente, ignoraFlussoStati, db);
			
			StabilimentoImportUtil.stampaLog("[SINTESIS] Codice uscita: "+codiceUscita);
			
			if (codiceUscita == CHECK_IGNORA){
				//non fare nulla
				StabilimentoImportUtil.stampaLog("[SINTESIS] IGNORATO");

				msg+=  textHtml("[DUPLICATO] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> NON PROCESSATO PERCHE' GIA' ESISTENTE.", true);
			}
			else if (codiceUscita == CHECK_LINEA_NON_MAPPATA){
				//non fare nulla.
				StabilimentoImportUtil.stampaLog("[SINTESIS] IGNORATO PER LINEA NON MAPPATA");
				msg+=  textHtml("[LINEA NON MAPPATA] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> NON PROCESSATO PERCHE' LINEA NON MAPPATA.", true);
			}
			else if (codiceUscita == CHECK_WORKFLOW_NON_RISPETTATO){
				//non fare nulla
				StabilimentoImportUtil.stampaLog("[SINTESIS] IGNORATO PER WORKFLOW ERRATO");
				msg+=  textHtml("[ERRORE FLUSSO STATI] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> NON PROCESSATO PERCHE' NON RISPETTANTE IL FLUSSO CORRETTO DEGLI STATI (STAB/ATTIVITA').", true); 
			}
			else if (codiceUscita == CHECK_INSERISCI_STABILIMENTO_INSERISCI_LINEA){
				StabilimentoImportUtil.stampaLog("[SINTESIS] INIZIO INSERIMENTO");
				msg+=  textHtml("[AGGIUNTA STABILIMENTO E AGGIUNTA LINEA] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> INSERITO NEL SISTEMA.", false); 

				SintesisOperatore sintOp = new SintesisOperatore(db, getOpuIdOperatore());
				if (sintOp.getIdOperatore()<=0){
					StabilimentoImportUtil.stampaLog("[SINTESIS] INSERIMENTO OPERATORE "+op.getIdOperatore());

					indResidenza.insertIndirizzo(db);
					StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITO INDIRIZZO RESIDENZA "+indResidenza.getIdIndirizzo());
					
					sogg.setIdIndirizzo(indResidenza.getIdIndirizzo());
					sogg.insertSoggetto(db);
					StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITO RAPP LEGALE "+sogg.getId());
								
					indLegale.insertIndirizzo(db);
					StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITO INDIRIZZO SEDE LEGALE "+indLegale.getIdIndirizzo());
					
					op.setIdIndirizzo(indLegale.getIdIndirizzo());
					op.setIdRappLegale(sogg.getId());
		
					op.setEnteredby(userId);
					op.setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
					op.insertOperatore(db);
				}
				else{
					StabilimentoImportUtil.stampaLog("[SINTESIS] RECUPERATO OPERATORE "+getOpuIdOperatore());
					op.setIdOperatore(sintOp.getIdOperatore());
				}
				
				ind.insertIndirizzo(db);
				StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITO INDIRIZZO "+ind.getIdIndirizzo());
		
				stab.setIdOperatore(op.getIdOperatore());
				stab.setIdIndirizzo(ind.getIdIndirizzo());
				stab.setIdAsl(ind.getIdAsl());
					
				stab.setEnteredby(userId);
				stab.setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
				stab.insertStabilimento(db);
				StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITO STABILIMENTO "+stab.getIdStabilimento());
				SintesisStorico.salvaStoricoStabilimento(db, getId(), getIdImport(), userId, null, stab);

				rel.setIdStabilimento(stab.getIdStabilimento());
				rel.setEnteredby(userId);
				rel.setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
				rel.insertRelazione(db);
				StabilimentoImportUtil.stampaLog("[SINTESIS] INSERITA RELAZIONE "+rel.getIdRelazione());
				SintesisStorico.salvaStoricoRelazione(db, getId(), getIdImport(), userId, null, rel);
		
				setIdUtenteProcess(userId);
				chiudi(db);
				StabilimentoImportUtil.stampaLog("[SINTESIS] CHIUSURA IMPORT RICHIESTA "+getId());
				RicercheAnagraficheTab.insertSintesis(db, stab.getIdStabilimento());

			}
			else if (codiceUscita == CHECK_AGGIORNA_STABILIMENTO_AGGIORNA_LINEA){
				msg+=  textHtml("[AGGIORNATO STABILIMENTO E AGGIORNATA LINEA] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> AGGIORNATO.", false);

					rel.getStabilimento().setIdStabilimento(relEsistente.getIdStabilimento());
					rel.getStabilimento().getOperatore().setIdOperatore(relEsistente.getStabilimento().getOperatore().getIdOperatore());
					rel.getStabilimento().getIndirizzo().setIdIndirizzo(relEsistente.getStabilimento().getIndirizzo().getIdIndirizzo());
					rel.setIdRelazione(relEsistente.getIdRelazione());
					
					
					if (rel.getStabilimento().getOperatore().isDiverso(db, relEsistente.getStabilimento().getOperatore())){
						rel.getStabilimento().getOperatore().setEnteredby(userId);
						rel.getStabilimento().getOperatore().setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
						rel.getStabilimento().getOperatore().insertOperatore(db);
						rel.getStabilimento().setIdOperatore(rel.getStabilimento().getOperatore().getIdOperatore());
					}
					else
						rel.getStabilimento().setIdOperatore(relEsistente.getStabilimento().getIdOperatore());
					
					if (rel.getStabilimento().getIndirizzo().isDiverso(db,  relEsistente.getStabilimento().getIndirizzo())){
						rel.getStabilimento().getIndirizzo().insertIndirizzo(db);
						rel.getStabilimento().setIdIndirizzo(rel.getStabilimento().getIndirizzo().getIdIndirizzo());
					}
					else
						rel.getStabilimento().setIdIndirizzo(relEsistente.getStabilimento().getIndirizzo().getIdIndirizzo());
					
					if (rel.getStabilimento().isDiverso(db, relEsistente.getStabilimento())){
						SintesisStorico.salvaStoricoStabilimento(db, getId(), getIdImport(), userId, relEsistente.getStabilimento(), rel.getStabilimento());
						rel.getStabilimento().setModifiedby(userId);
						rel.getStabilimento().setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
						rel.getStabilimento().aggiornaStabilimento(db);
					}
					
						SintesisStorico.salvaStoricoRelazione(db, getId(), getIdImport(),userId, relEsistente, rel);
						rel.setModifiedby(userId);
						rel.setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
						rel.aggiornaRelazione(db);
					
				
			
				setIdUtenteProcess(userId);
				chiudi(db);
				RicercheAnagraficheTab.insertSintesis(db, stab.getIdStabilimento());

			}
			else if (codiceUscita == CHECK_AGGIORNA_STABILIMENTO_INSERISCI_LINEA){
				msg+= textHtml("[AGGIORNATO STABILIMENTO E INSERITA LINEA] Stabilimento: "+rel.getStabilimento().getDenominazione()+ "<br/> ( "+ rel.getStabilimento().getApprovalNumber() +") / Linea: "+rel.getSintesisDescrizioneSezione() + " -> " + rel.getSintesisAttivita() + " <br/> AGGIUNTA LINEA PRODUTTIVA.", false); 

				rel.getStabilimento().setIdStabilimento(stabEsistente.getIdStabilimento());
				rel.getStabilimento().getOperatore().setIdOperatore(stabEsistente.getOperatore().getIdOperatore());
				rel.getStabilimento().getIndirizzo().setIdIndirizzo(stabEsistente.getIndirizzo().getIdIndirizzo());
			
				if (rel.getStabilimento().getOperatore().isDiverso(db, stabEsistente.getOperatore())){
					rel.getStabilimento().getOperatore().setEnteredby(userId);
					rel.getStabilimento().getOperatore().setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
					rel.getStabilimento().getOperatore().insertOperatore(db);
					rel.getStabilimento().setIdOperatore(rel.getStabilimento().getOperatore().getIdOperatore());
				}
				else
					rel.getStabilimento().setIdOperatore(stabEsistente.getIdOperatore());
				
				if (rel.getStabilimento().getIndirizzo().isDiverso(db,  stabEsistente.getIndirizzo())){
					rel.getStabilimento().getIndirizzo().insertIndirizzo(db);
					rel.getStabilimento().setIdIndirizzo(rel.getStabilimento().getIndirizzo().getIdIndirizzo());

				}
				else
					rel.getStabilimento().setIdIndirizzo(stabEsistente.getIndirizzo().getIdIndirizzo());
				
				if (rel.getStabilimento().isDiverso(db, stabEsistente)){
					SintesisStorico.salvaStoricoStabilimento(db, getId(), getIdImport(), userId, stabEsistente, rel.getStabilimento());
					rel.getStabilimento().setModifiedby(userId);
					rel.getStabilimento().setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
					rel.getStabilimento().aggiornaStabilimento(db);
				}
				
				rel.setIdStabilimento(stabEsistente.getIdStabilimento());
				rel.setDataUltimoAggiornamentoSintesis(log.getDataDocumentoSintesis());
				rel.insertRelazione(db);

				SintesisRelazioneLineaProduttiva relEsistenteNew = new SintesisRelazioneLineaProduttiva();
				SintesisStorico.salvaStoricoRelazione(db, getId(), getIdImport(), userId, relEsistenteNew, rel);
				setIdUtenteProcess(userId);
				chiudi(db);
				RicercheAnagraficheTab.insertSintesis(db, stab.getIdStabilimento());
	}
			
		
			
			log.setErrore(msg);
			log.update(db);
		
			outputValidazione= 	new Object[]{codiceUscita,msg,relEsistente,rel, stabEsistente};

			return outputValidazione;
		
	}
	
	
	private int controllaValiditaDati(SintesisRelazioneLineaProduttiva rel, SintesisRelazioneLineaProduttiva relEsistente, boolean ignoraFlussoStati, Connection db) throws SQLException{
		
		
		SintesisStabilimento stabEsistente = null;
		stabEsistente = new SintesisStabilimento(db, rel.getStabilimento().getApprovalNumber());

		
		// Se la linea non ha trovato mapping
		if (rel.getIdLineaMasterList()<=0)
			return CHECK_LINEA_NON_MAPPATA;
		
		// Se lo stabilimento non esiste
		else if (stabEsistente.getIdStabilimento()<=0) {
			if (!ignoraFlussoStati && !rel.rispettaWorkFlow(db, relEsistente, stabEsistente))
				 return CHECK_WORKFLOW_NON_RISPETTATO;
			else
				return CHECK_INSERISCI_STABILIMENTO_INSERISCI_LINEA;
		}
		
		// Se lo stabilimento esiste
		else if (stabEsistente.getIdStabilimento()>0) {
			
			// Se non ha la linea
			if (relEsistente.getIdRelazione()<=0){
				if (!ignoraFlussoStati && !rel.rispettaWorkFlow(db, relEsistente, stabEsistente))
					 return CHECK_WORKFLOW_NON_RISPETTATO;
				else
					return CHECK_AGGIORNA_STABILIMENTO_INSERISCI_LINEA;
			}
			
			// Se ha la linea
			else if (relEsistente.getIdRelazione()>0 && rel.sonoDiversi(rel, relEsistente)){
				if (!ignoraFlussoStati && !rel.rispettaWorkFlow(db, relEsistente, stabEsistente))
					 return CHECK_WORKFLOW_NON_RISPETTATO;
				else
					return CHECK_AGGIORNA_STABILIMENTO_AGGIORNA_LINEA;
			}
			
		}
		
		return CHECK_IGNORA;

	}
	
	private String textHtml(String text, boolean error){
		String html="";
		
		int inizio = text.indexOf("[");
		int fine = text.indexOf("]");
		
		html = "<b>"+text.substring(inizio, fine)+"</b>"+ text.substring(fine, text.length());
		
		if (error)
			return "<font color=\"red\">"+html+"</font><br/>";
		else
			return "<font color=\"green\">"+html+"</font><br/>";
		
	}
	
	
	private String fixString(String text){
		if (text!=null)
			return text.trim();
		else
			return "";
	}
	public boolean isApprovalTroppoLungo() {
		if (approvalNumber.length()>=20)
			return true;
		return false;
	}
	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public void setLatitudine(String latitudine) {
		try {this.latitudine = Double.parseDouble(latitudine);} catch (Exception e) {}
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public void setLongitudine(String longitudine) {
		try {this.longitudine = Double.parseDouble(longitudine);} catch (Exception e) {}
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Timestamp getDataSintesis() {
		return dataSintesis;
	}
	public void setDataSintesis(Timestamp dataSintesis) {
		this.dataSintesis = dataSintesis;
	}
	
	
	
}
