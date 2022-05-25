/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;










import java.util.regex.Pattern;

import javafx.scene.input.DataFormat;
import net.sf.asterisk.fastagi.command.SetCallerIdCommand;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONObject;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.newvaluescorrelationname;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class OperatorePerDuplicati extends GenericBean {



	
	protected boolean flagFuoriRegione = false ;
	
	protected String telefono1;
	protected String telefono2;
	protected String email;
	protected String fax;
	protected String note;
	protected int modifiedBy = -1;
	protected int enteredBy = -1;
	protected String ipEnteredBy;
	protected String ipModifiedBy;
	protected java.sql.Timestamp dataInizio;
	protected java.sql.Timestamp entered;
	protected java.sql.Timestamp modified;
	protected boolean flagRicCe ;
	protected String numRicCe ;
	private Indirizzo sedeLegaleImpresa = new Indirizzo();
	protected SoggettoFisico rappLegale;
	protected SoggettoFisicoList storicoSoggettoFisico = new SoggettoFisicoList();
	protected SedeList listaSediOperatore = new SedeList();
	protected StabilimentoList listaStabilimenti = new StabilimentoList();
	protected OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
	protected String action ;
	protected int tipo_impresa	;
	protected int tipo_societa ;
	protected Integer idUtente;
	protected String codFiscale;
	protected String partitaIva;
	protected int idOperatore;
	protected String ragioneSociale;
	protected String domicilioDigitale ;
	protected String codiceInternoImpresa;
	private Integer impresaIdTipoImpresa;
	private Integer ImpresaIdTipoSocieta;
	private String nomeRappSedeLegale;
	private String cognomeRappSedeLegale;
	private String cfRappSedeLegale;
	private Date dataNascitaRappSedeLegale;
	private String sessoRappSedeLegale;
	private String comuneNascitaRappSedeLegale;
	private String siglaProvSoggFisico;
	private String comuneResidenza;
	private String indirizzoRappSedeLegale;
	private String toponimoResidenza;
	private Integer idToponimoResidenza;
	private String civicoResidenza;
	private String siglaProvLegale;
	private String comuneSedeLegale;
	private String indirizzoSedeLegale;
	private String civicoSedeLegale;
	private String toponimoSedeLegale;
	private Integer idToponimoSedeLegale;
	private String siglaProvOperativa;
	private String comuneStab;
	private String indirizzoStab;
	private String civicoSedeStab;
	private String toponimoSedeStab;
	private Integer idToponimoStab;
	private Integer stabIdAttivita;
	private Integer stabIdCarattere;
	private Date dataInizioAttivita;
	private Date dataFineAttivita;
	private String tipoImpresa;
	private String tipoSocieta;
	ArrayList<Integer> arrayImprese = new ArrayList<Integer>();
//	private String[] campiConcatenati; //array per concatenare nome, cognome, sesso, cf
	
	
	

	public HashMap<String,String[]> getValuesPerForm()
	{
		HashMap<String,String[]> toRet = new HashMap<String,String[]>();
		//i campi che prevedono tipo con associato un id tipo, vengono accoppiati nell'array di stringhe
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
		
		toRet.put("partitaIva",new String[]{partitaIva});
		toRet.put("idOperatore",new String[]{idOperatore+""});
		toRet.put("ragioneSociale",new String[]{ragioneSociale});
		toRet.put("domicilioDigitale",new String[]{domicilioDigitale});
		toRet.put("codiceInternoImpresa",new String[]{codiceInternoImpresa});
		
//		toRet.put("tipoImpresa",tipoImpresa);
		toRet.put("tipoImpresa",new String[]{tipoImpresa,impresaIdTipoImpresa+""});
//		toRet.put("tipoSocieta",tipoSocieta);
		toRet.put("tipoSocieta",new String[]{tipoSocieta,ImpresaIdTipoSocieta+""});
		toRet.put("nomeRappSedeLegale",new String[]{nomeRappSedeLegale});
		toRet.put("cognomeRappSedeLegale",new String[]{cognomeRappSedeLegale});
		toRet.put("cfRappSedeLegale",new String[]{cfRappSedeLegale});
		
		if(dataNascitaRappSedeLegale != null)
			toRet.put("dataNascitaRappSedeLegale", new String[]{formatter.format(dataNascitaRappSedeLegale)});
		
		toRet.put("sessoRappSedeLegale",new String[]{sessoRappSedeLegale});
		toRet.put("comuneNascitaRappSedeLegale",new String[]{comuneNascitaRappSedeLegale});
		toRet.put("siglaProvSoggFisico",new String[]{siglaProvSoggFisico});
		toRet.put("nazioneResidenza", new String[]{this.nazioneResidenza});
		toRet.put("capResidenza", new String[]{this.capResidenza});
		toRet.put("comuneResidenza",new String[]{comuneResidenza});
		toRet.put("indirizzoRappSedeLegale",new String[]{indirizzoRappSedeLegale});
		if (idToponimoResidenza>0)
			toRet.put("toponimoResidenza",new String[]{toponimoResidenza,idToponimoResidenza+""});
		else
		{
			toRet.put("toponimoResidenza",new String[]{"",-1+""});
		}
		toRet.put("civicoResidenza",new String[]{civicoResidenza+""});
		toRet.put("siglaProvLegale",new String[]{siglaProvLegale});
		toRet.put("nazioneSedeLegale", new String[]{this.nazioneSedeLegale});
		toRet.put("capSedeLegale", new String[]{this.capSedeLegale});
		toRet.put("comuneSedeLegale",new String[]{comuneSedeLegale});
		toRet.put("indirizzoSedeLegale",new String[]{indirizzoSedeLegale});
		toRet.put("civicoSedeLegale",new String[]{civicoSedeLegale});

		if (idToponimoSedeLegale>0)
			toRet.put("toponimoSedeLegale",new String[]{toponimoSedeLegale,idToponimoSedeLegale+""});
		else
		{
			toRet.put("toponimoSedeLegale",new String[]{"",-1+""});
		}
		toRet.put("siglaProvOperativa",new String[]{siglaProvOperativa});
		toRet.put("capSedeOperativa",  new String[]{capSedeOperativa});
		toRet.put("nazioneSedeOperativa", new String[]{nazioneSedeOperativa});
		toRet.put("comuneStab",new String[]{comuneStab});
		toRet.put("indirizzoStab",new String[]{indirizzoStab});
		toRet.put("civicoSedeStab",new String[]{civicoSedeStab});
		if (idToponimoStab>0)
			toRet.put("toponimoSedeStab",new String[]{toponimoSedeStab,idToponimoStab+""});
		else
			toRet.put("toponimoSedeLegale",new String[]{"",-1+""});
		toRet.put("stabIdAttivita",new String[]{this.descrTipoAttivita,stabIdAttivita+""});
		toRet.put("stabIdCarattere",new String[]{this.descrTipoCarattere,stabIdCarattere+""});
		if(dataInizioAttivita != null)
			toRet.put("dataInizioAttivita",new String[]{formatter.format(dataInizioAttivita)});
		
		if(dataFineAttivita != null)
			toRet.put("dataFineAttivita",new String[]{formatter.format(dataFineAttivita)});
		
		
		toRet.put("codFiscale",new String[]{codFiscale});
		toRet.put("campiConcatenati", new String[]{nomeRappSedeLegale,cognomeRappSedeLegale,sessoRappSedeLegale,codFiscale});
		
		return toRet;
	}
	
	public String getTipoImpresa() {
		return tipoImpresa;
	}

	public void setTipoImpresa(String tipoImpresa) {
		if(tipoImpresa!=null)
		this.tipoImpresa = tipoImpresa;
		else this.tipoImpresa ="" ;
	}

	public String getTipoSocieta() {
		return tipoSocieta;
	}

	public void setTipoSocieta(String tipoSocieta) {
		if(tipoSocieta!=null)
		this.tipoSocieta = tipoSocieta;
		else tipoSocieta = "";
	}

	
	
	
	
	
	private SoggettoFisicoPerOpuDuplicati rappLegalePerDup;
	private SedePerOpuDuplicati sedePerDup;

	private String descrTipoAttivita;

	private String descrTipoCarattere;

	private ArrayList<Integer> arrayImpianti;

	private String capSedeLegale;

	private String nazioneSedeLegale;

	private String capResidenza;

	private String nazioneResidenza;

	private String capSedeOperativa;

	private String nazioneSedeOperativa;
	
	
	
	public Indirizzo getSedeLegaleImpresa() {
		
		
		return sedeLegaleImpresa;
	}

	public void setSedeLegaleImpresa(Indirizzo sedeLegaleImpresa) {
		this.sedeLegaleImpresa = sedeLegaleImpresa;
	}

	public int getTipo_impresa() {
		return tipo_impresa;
	}

	public void setTipo_impresa(int tipo_impresa) {
		this.tipo_impresa = tipo_impresa;
	}

	public int getTipo_societa() {
		return tipo_societa;
	}

	public void setTipo_societa(int tipo_societa) {
		this.tipo_societa = tipo_societa;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public OperatorePerDuplicati(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public OperatorePerDuplicati() {
	}
	
	public void setCapSedeLegale(String string) {
		this.capSedeLegale = string;
		
	}

	public void setNazioneSedeLegale(String string) {
		this.nazioneSedeLegale = string;
		
	}

	public void setCapResidenza(String string) {
		this.capResidenza = string;
	}
	
	public String getCapResidenza(){return capResidenza;}
	public String getNazioneResidenza() {return nazioneResidenza;}
	public String getCapSedeLegale(){return capSedeLegale;}
	public String getNazioneSedeLegale(){return nazioneSedeLegale;}
	
	public void setCapSedeOperativa(String s){this.capSedeOperativa = s;}
	public String getCapSedeOperativa() { return capSedeOperativa;} 
	public void setNazioneSedeOperativa(String s){this.nazioneSedeOperativa = s;}
	public String getNazioneSedeOperativa() { return nazioneSedeOperativa;} 

	private void setNazioneResidenza(String string) {

		this.nazioneResidenza = string;
	}

	
	public SoggettoFisicoList getStoricoSoggettoFisico() {
		return storicoSoggettoFisico;
	}

	public void setStoricoSoggettoFisico(SoggettoFisicoList storicoSoggettoFisico) {
		this.storicoSoggettoFisico = storicoSoggettoFisico;
	}

	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}

	public void setDomicilioDigitale(String domicilioDigitale) {
		if (domicilioDigitale!=null)
		this.domicilioDigitale = domicilioDigitale;else this.domicilioDigitale ="";
			
	}

	public boolean isFlagRicCe() {
		return flagRicCe;
	}

	public void setFlagRicCe(boolean flagRicCe) {
		this.flagRicCe = flagRicCe;
	}

	public String getNumRicCe() {
		return numRicCe;
	}

	public void setNumRicCe(String numRicCe) {
		this.numRicCe = numRicCe;
	}

	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}

	public StabilimentoList getListaStabilimenti() {
		return listaStabilimenti;
	}

	public void setListaStabilimenti(StabilimentoList listaStabilimenti) {
		this.listaStabilimenti = listaStabilimenti;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
		this.listaSediOperatore.setIdOperatore(idOperatore);
		// this.listaIter.setIdOperatore(idOperatore);
	}

	public void setIdOperatore(String id) {
		this.idOperatore = new Integer(id).intValue();
		this.listaSediOperatore.setIdOperatore(new Integer(id).intValue());
		// this.listaIter.setIdOperatore(new Integer(id).intValue());
	}

	public String getRagioneSociale() {
		return (ragioneSociale !=null ? ragioneSociale.trim() :"");
	}

	public void setRagioneSociale(String ragioneSociale) {
		if (ragioneSociale!=null)
		this.ragioneSociale = ragioneSociale;
		else this.ragioneSociale = "";
	}

	public String getPartitaIva() {
		return (partitaIva !=null ? partitaIva.trim() :"");
	}

	public SedeList getListaSediOperatore() {
		return listaSediOperatore;
	}

	public void setListaSediOperatore(SedeList listaSediOperatore) {
		this.listaSediOperatore = listaSediOperatore;
	}

	public void setPartitaIva(String partitaIva) {
		if (partitaIva!=null)
		this.partitaIva = partitaIva;
		else this.partitaIva = "";
	}

	public String getCodFiscale() {
		return (codFiscale !=null ? codFiscale.trim() :"");
		
	}

	public void setCodFiscale(String codFiscale) {
		if(codFiscale!=null)
		this.codFiscale = codFiscale;
		else this.codFiscale = "";
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}



	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getIpEnteredBy() {
		return ipEnteredBy;
	}

	public void setIpEnteredBy(String ipEnteredBy) {
		this.ipEnteredBy = ipEnteredBy;
	}

	public String getIpModifiedBy() {
		return ipModifiedBy;
	}

	public void setIpModifiedBy(String ipModifiedBy) {
		this.ipModifiedBy = ipModifiedBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public SoggettoFisico getRappLegale() {
		return rappLegale;
	}

	public void setRappLegale(SoggettoFisico rappLegale) {
		this.rappLegale = new SoggettoFisico();
		this.rappLegale = rappLegale;
	}

	/*
	 * public Indirizzo getSedeLegale() { return sedeLegale; }
	 * 
	 * 
	 * public void setSedeLegale(Indirizzo sedeLegale) { this.sedeLegale = new
	 * Indirizzo(); this.sedeLegale = sedeLegale; }
	 */

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}



	public OrganizationPhoneNumberList getPhoneNumberList() {
		return phoneNumberList;
	}

	public void setPhoneNumberList(OrganizationPhoneNumberList phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public OrganizationEmailAddressList getEmailAddressList() {
		return emailAddressList;
	}

	public void setEmailAddressList(
			OrganizationEmailAddressList emailAddressList) {
		this.emailAddressList = emailAddressList;
	}

	
	

	public java.sql.Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(java.sql.Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public Integer getImpresaIdTipoImpresa() {
		return impresaIdTipoImpresa;
	}

	public void setImpresaIdTipoImpresa(Integer impresaIdTipoImpresa) {
		this.impresaIdTipoImpresa = impresaIdTipoImpresa;
	}

	public Integer getImpresaIdTipoSocieta() {
		return ImpresaIdTipoSocieta;
	}

	public void setImpresaIdTipoSocieta(Integer impresaIdTipoSocieta) {
		ImpresaIdTipoSocieta = impresaIdTipoSocieta;
	}

	public String getNomeRappSedeLegale() {
		
		return nomeRappSedeLegale;
	}

	public void setNomeRappSedeLegale(String nomeRappSedeLegale) {
		if (nomeRappSedeLegale!=null)
			this.nomeRappSedeLegale = nomeRappSedeLegale;
		else
			this.nomeRappSedeLegale = "";
	}

	public String getCognomeRappSedeLegale() {
		return cognomeRappSedeLegale;
	}

	public void setCognomeRappSedeLegale(String cognomeRappSedeLegale) {
		if (cognomeRappSedeLegale!=null)
		this.cognomeRappSedeLegale = cognomeRappSedeLegale;
		else
			this.cognomeRappSedeLegale = "";
	}

	public String getCfRappSedeLegale() {
		return cfRappSedeLegale;
	}

	public void setCfRappSedeLegale(String cfRappSedeLegale) {
		if (cfRappSedeLegale!=null)
			this.cfRappSedeLegale = cfRappSedeLegale;
		else
			this.cfRappSedeLegale="";
	}

	public Date getDataNascitaRappSedeLegale() {
		return dataNascitaRappSedeLegale;
	}

	public void setDataNascitaRappSedeLegale(Date dataNascitaRappSedeLegale) {
		
		this.dataNascitaRappSedeLegale = dataNascitaRappSedeLegale;
	}

	public String getSessoRappSedeLegale() {
		return sessoRappSedeLegale;
	}

	public void setSessoRappSedeLegale(String sessoRappSedeLegale) {
		if (sessoRappSedeLegale!=null)
		this.sessoRappSedeLegale = sessoRappSedeLegale;
		else
			this.sessoRappSedeLegale="";
	}

	public String getComuneNascitaRappSedeLegale() {
		
		return comuneNascitaRappSedeLegale;
	}

	public void setComuneNascitaRappSedeLegale(String comuneNascitaRappSedeLegale) {
		if (comuneNascitaRappSedeLegale!=null)
		this.comuneNascitaRappSedeLegale = comuneNascitaRappSedeLegale;
		else
			this.comuneNascitaRappSedeLegale ="";
	}

	public String getSiglaProvSoggFisico() {
		return siglaProvSoggFisico;
	}

	public void setSiglaProvSoggFisico(String siglaProvSoggFisico) {
		if (siglaProvSoggFisico!=null)
		this.siglaProvSoggFisico = siglaProvSoggFisico;
		else
			this.siglaProvSoggFisico ="";
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		if (comuneResidenza!=null)
		this.comuneResidenza = comuneResidenza;
		else
			this.comuneResidenza ="";
	}

	public String getIndirizzoRappSedeLegale() {
		return indirizzoRappSedeLegale;
	}

	public void setIndirizzoRappSedeLegale(String indirizzoRappSedeLegale) {
		if (indirizzoRappSedeLegale!=null)
		this.indirizzoRappSedeLegale = indirizzoRappSedeLegale;
		else
			this.indirizzoRappSedeLegale ="";
	}

	public String getToponimoResidenza() {
		return toponimoResidenza;
	}

	public void setToponimoResidenza(String toponimoResidenza) {
		if (toponimoResidenza!=null)
		this.toponimoResidenza = toponimoResidenza;
		else this.toponimoResidenza ="";
	}

	public Integer getIdToponimoResidenza() {
		return idToponimoResidenza;
	}

	public void setIdToponimoResidenza(Integer idToponimoResidenza) {
		this.idToponimoResidenza = idToponimoResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		if (civicoResidenza!=null)
		this.civicoResidenza = civicoResidenza;
		else
			this.civicoResidenza = "";
	}

	public String getSiglaProvLegale() {
		return siglaProvLegale;
	}

	public void setSiglaProvLegale(String siglaProvLegale) {
		if (siglaProvLegale!=null)
		this.siglaProvLegale = siglaProvLegale;
		else
			this.siglaProvLegale ="";
	}

	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}

	public void setComuneSedeLegale(String comuneSedeLegale) {
		if(comuneSedeLegale!=null)
		this.comuneSedeLegale = comuneSedeLegale;
		else
			this.comuneSedeLegale = "";
	}

	public String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}

	public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
		if(indirizzoSedeLegale!=null)
		this.indirizzoSedeLegale = indirizzoSedeLegale;
		else
		this.indirizzoSedeLegale ="";
	}

	public String getCivicoSedeLegale() {
		return civicoSedeLegale;
	}

	public void setCivicoSedeLegale(String civicoSedeLegale) {
		if(civicoSedeLegale!=null)
		this.civicoSedeLegale = civicoSedeLegale;
		else
			this.civicoSedeLegale = "";
	}

	public String getToponimoSedeLegale() {
		return toponimoSedeLegale;
	}

	public void setToponimoSedeLegale(String toponimoSedeLegale) {
		if (toponimoSedeLegale!=null)
		this.toponimoSedeLegale = toponimoSedeLegale;
		else
			this.toponimoSedeLegale ="";
	}

	public Integer getIdToponimoSedeLegale() {
		return idToponimoSedeLegale;
	}

	public void setIdToponimoSedeLegale(Integer idToponimoSedeLegale) {
		this.idToponimoSedeLegale = idToponimoSedeLegale;
	}

	public String getSiglaProvOperativa() {
		return siglaProvOperativa;
	}

	public void setSiglaProvOperativa(String siglaProvOperativa) {
		
		this.siglaProvOperativa = siglaProvOperativa;
	}

	public String getComuneStab() {
		return comuneStab;
	}

	public void setComuneStab(String comuneStab) {
		this.comuneStab = comuneStab;
	}

	public String getIndirizzoStab() {
		return indirizzoStab;
	}

	public void setIndirizzoStab(String indirizzoStab) {
		this.indirizzoStab = indirizzoStab;
	}

	public String getCivicoSedeStab() {
		return civicoSedeStab;
	}

	public void setCivicoSedeStab(String civicoSedeStab) {
		this.civicoSedeStab = civicoSedeStab;
	}

	public String getToponimoSedeStab() {
		return toponimoSedeStab;
	}

	public void setToponimoSedeStab(String toponimoSedeStab) {
		this.toponimoSedeStab = toponimoSedeStab;
	}

	public Integer getIdToponimoStab() {
		return idToponimoStab;
	}

	public void setIdToponimoStab(Integer idToponimoStab) {
		this.idToponimoStab = idToponimoStab;
	}

	public Integer getStabIdAttivita() {
		return stabIdAttivita;
	}

	public void setStabIdAttivita(Integer stabIdAttivita) {
		this.stabIdAttivita = stabIdAttivita;
	}

	public ArrayList<Integer> getArrayImprese() {
		return arrayImprese;
	}

	public void setArrayImprese(ArrayList<Integer> arrayImprese) {
		this.arrayImprese = arrayImprese;
	}
	
	
	public ArrayList<Integer> getArrayImpianti()
	{
		return this.arrayImpianti;
	}
	
	public void setArrayImpianti(ArrayList<Integer> idImpianti) {
		this.arrayImpianti = idImpianti;
		
	}
	
	public Integer getStabIdCarattere() {
		return stabIdCarattere;
	}

	public void setStabIdCarattere(Integer stabIdCarattere) {
		this.stabIdCarattere = stabIdCarattere;
	}

	public Date getDataInizioAttivita() {
		return dataInizioAttivita;
	}

	public void setDataInizioAttivita(Date dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
	}

	public Date getDataFineATtivita() {
		return dataFineAttivita;
	}

	public void setDataFineAttivita(Date dataFineATtivita) {
		this.dataFineAttivita = dataFineATtivita;
	}

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	
	public String getCodiceInternoImpresa() {
		return codiceInternoImpresa;
	}

	public void setCodiceInternoImpresa(String codiceInternoImpresa) {
		this.codiceInternoImpresa = codiceInternoImpresa;
	}

	public boolean insert(Connection db,ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			idOperatore = DatabaseUtils.getNextSeq(db, "opu_operatore_id_seq");
			sql.append("INSERT INTO opu_operatore (");

			sql.append("tipo_impresa,tipo_societa,ragione_sociale, codice_fiscale_impresa, "
					+ "partita_iva");

			if (idOperatore > -1) {
				sql.append(",id,codice_interno_impresa ");
			}

			if (note!= null && ! note.equals("")) {
				sql.append(", note ");
			}
			
			if (enteredBy > -1) {
				sql.append(", enteredby");
			}

			if (modifiedBy > -1) {
				sql.append(", modifiedby");
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				sql.append(", ipenteredby");
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				sql.append(", ipmodifiedby");
			}

			sql.append(", entered, modified,domicilio_digitale,flag_ric_ce,num_ric_ce,id_indirizzo");

			sql.append(")");

			sql.append("VALUES (?,?,?,?,?,?,?,?");

			if (idOperatore > -1) {
				sql.append(",?,? ");
			}
			if (note!= null && ! note.equals("")) {
				sql.append(",? ");
			}

			if (enteredBy > -1) {
				sql.append(",?");
			}

			if (modifiedBy > -1) {
				sql.append(",?");
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				sql.append(",?");
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				sql.append(",?");
			}

			sql.append(", ?, ? , ?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, tipo_impresa);
			pst.setInt(++i, tipo_societa);
			pst.setString(++i, this.getRagioneSociale());
			pst.setString(++i, this.getCodFiscale());
			pst.setString(++i, this.getPartitaIva());
			if (idOperatore > -1) {
				pst.setInt(++i, idOperatore);
				if(codiceInternoImpresa!=null)
					pst.setString(++i, ""+codiceInternoImpresa);
				else
					pst.setString(++i, ""+idOperatore);
			}
			if (note!= null && ! note.equals("")) {
				pst.setString(++i, this.note);
			}
			if (enteredBy > -1) {
				pst.setInt(++i, this.enteredBy);
			}

			if (modifiedBy > -1) {
				pst.setInt(++i, this.modifiedBy);
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				pst.setString(++i, this.ipEnteredBy);
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				pst.setString(++i, this.ipModifiedBy);
			}

			pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
			pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
			
			pst.setString(++i, domicilioDigitale);
			pst.setBoolean(++i, flagRicCe);
			pst.setString(++i, numRicCe);
			
			Indirizzo sedeLegale =  new Indirizzo();
			SedeList listaInd = this.getListaSediOperatore();
			Iterator<Indirizzo> it = listaInd.iterator();
			if (it.hasNext()) {

				sedeLegale = it.next();
				
				
			}
			pst.setInt(++i, sedeLegale.getIdIndirizzo());
			
			

			pst.execute();
			pst.close();

		
			if (this.getRappLegale() != null)
				this.aggiungiRelazioneSoggettoFisico(db, 1,context);

			

			

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;

	}

	public void queryRecordOperatore(Connection db, int idOperatore)
	throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}
		
		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		buildRappresentante(db);

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);
		if(listaSediOperatore.size()>0 && this.tipo_impresa!=1 )
			sedeLegaleImpresa= (Indirizzo)listaSediOperatore.get(0);
		else
			if (rappLegale!=null)
			sedeLegaleImpresa = rappLegale.getIndirizzo();

		listaStabilimenti.setIdOperatore(idOperatore);
		
		listaStabilimenti.setFlag_dia(false);
		listaStabilimenti.buildList(db);

		
		
		rs.close();
		pst.close();
		
		
	}
	

	public void queryRecordOperatoreEsclusaSedeProduttiva(Connection db, int idOperatore)
	throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
			sedeLegaleImpresa = new Indirizzo(db,rs.getInt("id_indirizzo"));
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		
		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}
	
	
	
	public void queryRecordOperatoreStorico(Connection db, int idOperatore)
			throws SQLException, IndirizzoNotFoundException {

				if (idOperatore == -1) {
					throw new SQLException("Invalid Account");
				}

				PreparedStatement pst = db
				.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.id = ?");
				pst.setInt(1, idOperatore);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					this.buildRecord(rs);
				}

				if (idOperatore == -1) {
					throw new SQLException(Constants.NOT_FOUND_ERROR);
				}

				listaSediOperatore.setIdOperatore(idOperatore);
				listaSediOperatore.setOnlyActive(1);
				listaSediOperatore.buildListSediOperatoreStorico(db);

				/*listaStabilimenti.setIdOperatore(idOperatore);
				listaStabilimenti.setFlag_dia(false);
				listaStabilimenti.buildList(db);*/

				rs.close();
				pst.close();
				storicoSoggettoFisico.setIdOperatore(idOperatore);
				storicoSoggettoFisico.buildListStorico(db);
					
				rs.close();
				pst.close();
			}
	
	public void updateSoggettoFisico(Connection db,ActionContext context) throws SQLException
	{
		if (this.getRappLegale() != null)
			this.aggiungiRelazioneSoggettoFisico(db, 1,context);
	}
	
	public void queryRecordOperatorePartitaIva(Connection db, String pIva)
	throws SQLException, IndirizzoNotFoundException {

		

		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.partita_iva ilike ?");
		pst.setString(1, pIva);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);


		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}


	

	public void queryRecordOperatorebyCodiceFiscale(Connection db,
			String codiceFiscale) throws SQLException, IllegalAccessException, InstantiationException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		int idLineaProduttiva = -1;

		PreparedStatement pst = db
		.prepareStatement("Select o.*, o.id as idOperatore, r.id as idlinea "
				+ "from opu_operatore o  "
				+ "join opu_stabilimento s on s.id_operatore = o.id "
				+ "join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
				+ "where o.codice_fiscale = ?");
		pst.setString(1, codiceFiscale);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
			idLineaProduttiva = rs.getInt("idlinea");
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);

		listaStabilimenti.setIdOperatore(idOperatore);
		listaStabilimenti.buildStabilimento(db, idLineaProduttiva);

		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}

	public void setRequestItems(ActionContext context) {
		phoneNumberList = new OrganizationPhoneNumberList(context);
		emailAddressList = new OrganizationEmailAddressList(context.getRequest());
		rappLegale = new SoggettoFisico(context.getRequest());
	}


   
    
	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setIdOperatore(rs.getInt("idOperatore"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.tipo_impresa=rs.getInt("tipo_impresa");
		this.tipo_societa= rs.getInt("tipo_societa");
		
		this.setEnteredBy(rs.getInt("enteredby"));
		this.setModifiedBy(rs.getInt("modifiedby"));
		this.setEntered(rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));
		this.setDomicilioDigitale(rs.getString("domicilio_digitale"));
		
		

	}  
	
	public void buildRecordPerDuplicatiOpu(ResultSet rs, Connection db) throws SQLException, ParseException
	{
		
		//popolo per info impresa
		this.setIdOperatore(rs.getInt("id"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setNote(rs.getString("note"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setEnteredBy(rs.getInt("enteredby"));
		this.setModifiedBy(rs.getInt("modifiedby"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		this.setEntered( rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));
		
		this.tipo_impresa=rs.getInt("tipo_impresa");
		this.tipo_societa= rs.getInt("tipo_societa");
		this.setCodiceInternoImpresa(rs.getString("codice_interno_impresa"));
		
		this.setDomicilioDigitale(rs.getString("domicilio_digitale"));
		
		//popolo info legale
		PreparedStatement pst0 = null;
		ResultSet rs0 = null;
		pst0 = db.prepareStatement("select c.nome,c.cognome,c.codice_fiscale,d.via,d.comune_testo from opu_operatore a join opu_rel_operatore_soggetto_fisico b"+
									" on a.id = b.id_operatore join opu_soggetto_fisico c on b.id_soggetto_fisico = c.id "+
									"join opu_indirizzo d on a.id_indirizzo = d.id where a.id = ?");
		pst0.setInt(1, rs.getInt("id"));
		System.out.println(pst0);
		rs0 = pst0.executeQuery();
		rs0.next();
		
		SoggettoFisicoPerOpuDuplicati sogg = new SoggettoFisicoPerOpuDuplicati();
		sogg.setNome(rs0.getString("nome"));
		sogg.setCognome(rs0.getString("cognome"));
		sogg.setCodiceFiscale(rs0.getString("codice_fiscale"));
		this.setRappLegalePerDup(sogg);
		
		//per sede legale
		SedePerOpuDuplicati sede = new SedePerOpuDuplicati();
		sede.setVia(rs0.getString("via"));
		sede.setComune(rs0.getString("comune_testo"));
		this.setSedePerDup(sede);
		
		//per dati stabilimento
		//TODO
		
		
	}



	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame,ActionContext context)
	throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "opu_rel_operatore_soggetto_fisico_id_seq");

			sql.append("update opu_rel_operatore_soggetto_fisico set enabled = false where id_operatore =?; INSERT INTO opu_rel_operatore_soggetto_fisico (");

			if (idRelazione > -1)
				sql.append("id,");

			sql
			.append("id_operatore, id_soggetto_fisico, tipo_soggetto_fisico, data_inizio, stato_ruolo,enabled");

			sql.append(")");

			sql.append(" VALUES ( ");
			
			if (idRelazione > -1) {
				sql.append("?,");
			}
			
			sql.append(" ?, ?, ?, ?, ?,true ");
			
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idOperatore);
			if (idRelazione > -1) {
				pst.setInt(++i, idRelazione);
			}

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, tipoLegame);
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, 1);

			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean aggiornaRelazioneSoggettoFisico(Connection db, int tipoLegame,ActionContext context)
	throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
			.prepareStatement("Update opu_rel_operatore_soggetto_fisico set stato_ruolo = 2, data_fine = ? where "
					+ "stato_ruolo = 1 and "
					+ "id_operatore = ? "
					+ "and tipo_soggetto_fisico = ? ");

			int i = 0;
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, tipoLegame);
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiRelazioneSoggettoFisico(db, 1,context);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}


	
	
	
	
	
	public boolean modificaSedeLegale(Connection db, Indirizzo indirizzo)
			throws SQLException {

				StringBuffer sql = new StringBuffer();
				try {
					
					sql.append("UPDATE opu_operatore SET id_indirizzo = ? WHERE id = ? ");
					int i = 0;
				
					PreparedStatement pst = db.prepareStatement(sql.toString());
					pst.setInt(++i, indirizzo.getIdIndirizzo());
					pst.setInt(++i, idOperatore);
					pst.execute();
					pst.close();

				} catch (SQLException e) {

					throw new SQLException(e.getMessage());
				} finally {

				}

				return true;

			}
	
	public Indirizzo getSedeLegale() {
		Indirizzo sedeLegale = null;

		SedeList listaInd = this.getListaSediOperatore();
		Iterator<Indirizzo> it = listaInd.iterator();
		while (it.hasNext()) {

			Indirizzo temp = it.next();
			if (temp.getTipologiaSede() == 1) {
				sedeLegale = temp;
			}
		}

		return sedeLegale;
	}

	public Indirizzo getSedeOperativa() {
		Indirizzo sedeOperativa = null;

		SedeList listaInd = this.getListaSediOperatore();
		Iterator<Indirizzo> it = listaInd.iterator();
		while (it.hasNext()) {

			Indirizzo temp = it.next();
			if (temp.getTipologiaSede() == 2) {
				sedeOperativa = temp;
			}
		}

		return sedeOperativa;
	}

//	public boolean aggiornaRelazioneSede(Connection db, Indirizzo oldIndirizzo,
//			Indirizzo newIndirizzo,ActionContext context) throws SQLException {
//
//		StringBuffer sql = new StringBuffer();
//		try {
//
//			Calendar calendar = Calendar.getInstance();
//			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
//					.getTime().getTime());
//
//			// Disabilito vecchia relazione
//
//			PreparedStatement pst = db
//			.prepareStatement("Update opu_relazione_operatore_sede set stato_sede = 2, data_fine = ? where "
//					+ "stato_sede = 1 and "
//					+ "id_operatore = ? "
//					+ "and tipologia_sede = ? ");
//
//			int i = 0;
//			pst.setTimestamp(++i, currentTimestamp);
//			pst.setInt(++i, idOperatore);
//			pst.setInt(++i, oldIndirizzo.getTipologiaSede());
//			int resultCount = pst.executeUpdate();
//			pst.close();
//
//			this.aggiungiSede(db, newIndirizzo,context);
//
//		} catch (SQLException e) {
//
//			throw new SQLException(e.getMessage());
//		} finally {
//
//		}
//
//		return true;
//	}

	public boolean esegui_voltura(Connection db)
	{

		PreparedStatement pst = null ;
		int i = 0 ;
		try
		{
			pst = db.prepareStatement("update opu_operatore set ragione_sociale = ? , partita_iva = ? where id = ?;update opu_rel_operatore_soggetto_fisico set id_soggetto_fisico = ? where id = ?");
			pst.setString(++i, ragioneSociale);
			pst.setString(++i, partitaIva);
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, this.getIdOperatore());
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false ;
		}


		return true ;
	}
	protected void buildRappresentanteLegale(ResultSet rs) {

		try {
			rappLegale = new SoggettoFisico(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// TODO da eliminare
	public void buildRappresentante(Connection db) {
		SoggettoFisico rapp = new SoggettoFisico();

		StringBuffer sql = new StringBuffer();
		try {

			StringBuffer sqlSelect = new StringBuffer("");
			sqlSelect
			.append("SELECT distinct max(storico.id) as id_soggetto_storico ,i.civico,comnasc.id as  id_comune_nascita," +
					"o.nome,o.cognome," +
					"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id as id_soggetto ,i.toponimo," +
					"i.id,i.comune,i.comune_testo,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
					"lp.description as descrizione_provincia   " 
					+ "FROM opu_soggetto_fisico o "+
					"left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico " 
					+ "left join comuni1 comnasc on (comnasc.nome ilike o.comune_nascita) " 
					+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
					+ "left join comuni1 on (comuni1.id = i.comune) "
					+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
					+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
					+ "join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) and os.enabled "
					+ "join opu_operatore op on (os.id_operatore = op.id)  where os.id_operatore = ? and stato_ruolo = 1"+
					"group by o.nome,o.cognome," +
					"o.codice_fiscale,comnasc.id,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,i.civico,i.toponimo,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id  ," +
					"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome ," +
			"lp.description,i.comune_testo " );

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());

			int i = 0;
			pst.setInt(++i, idOperatore);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRappresentanteLegale(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

		}

	}

	public boolean checkEsistenzaOperatore(Connection db) {
		boolean exist = false;
		String query = "select *  from opu_operatore o " 
			+ "where o.partita_iva = ? ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.idOperatore = rs.getInt("id");
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exist;
	}
	
	public List<Operatore> checkEsistenzaOperatoreSuap(Connection db) throws IndirizzoNotFoundException {
		
		String query = "select distinct on (id) id,o.id as idOperatore   from opu_operatore o " 
			+ "where (1=1 ";
		
		
		boolean trovata_partita_iva=false;
		boolean solo_ragSociale=true;
		
		if(partitaIva!= null && ! "".equals(partitaIva)){
			query+=" and o.partita_iva  ilike ?  ";
			trovata_partita_iva=true;
			solo_ragSociale=false;
		}
			
		 if(codFiscale!= null && ! "".equals(codFiscale)){
			 solo_ragSociale=false;
			 if(trovata_partita_iva)
				 query+=" or o.codice_fiscale_impresa ilike ?  ";
			 else
				 query+=" and o.codice_fiscale_impresa ilike ?  ";
		 }
		
		 if (solo_ragSociale){
				if(ragioneSociale!= null && ! "".equals(ragioneSociale))
					query+=" and o.ragione_sociale  ilike ?  ";
		 }
		 
		 query+=") and trashed_date is null ";
		
		Operatore operatore = null;
		List<Operatore> listaOp = new ArrayList<Operatore>();
		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			
			if(partitaIva!= null && ! "".equals(partitaIva))
				pst.setString(++i, this.partitaIva);
				
			 if(codFiscale!= null && ! "".equals(codFiscale))
				 pst.setString(++i, this.codFiscale);
			 
			 if(solo_ragSociale){
				 if(ragioneSociale!= null && ! "".equals(ragioneSociale))
					 pst.setString(++i, this.ragioneSociale);	
			 }
			 
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				operatore = new Operatore();
				operatore.queryRecordOperatore(db,rs.getInt("idOperatore"));
				listaOp.add(operatore);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaOp;
	}


	public int getIdLineaProduttivaIfExists(Connection db,
			int idLineaTipologiaLineaProduttiva) {
		int idRelazioneStabLineaProd = -1;
		String query = "select rel.* from opu_operatore o left join opu_stabilimento s on (o.id = s.id_operatore) "
			+ "left join opu_relazione_stabilimento_linee_produttive rel on (s.id = rel.id_stabilimento) "
			+ "where o.partita_iva = ? and rel.id_linea_produttiva = ?";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);
			pst.setInt(++i, idLineaTipologiaLineaProduttiva);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				idRelazioneStabLineaProd = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idRelazioneStabLineaProd;
	}

	public int compareTo(Operatore otherOpertore) {
	       
			
			String denominazione = otherOpertore.getRagioneSociale();
			String pIva= otherOpertore.getPartitaIva();
			String codFiscale=otherOpertore.getCodFiscale();
			
			SoggettoFisico legaleRapp = otherOpertore.getRappLegale();
			Indirizzo indirizzo = otherOpertore.getSedeLegale();
			
			if (
					this.getRagioneSociale().equalsIgnoreCase(denominazione) &&
					this.getPartitaIva().equalsIgnoreCase(pIva )&&
					this.getCodFiscale().equalsIgnoreCase(codFiscale) && 
					this.getRappLegale().compareTo(legaleRapp)==0 &&
					this.getSedeLegale().compareTo(indirizzo)==0
					)
				return 0;
			return 1 ;
			

	    }

	public SoggettoFisicoPerOpuDuplicati getRappLegalePerDup() {
		return rappLegalePerDup;
	}

	public void setRappLegalePerDup(SoggettoFisicoPerOpuDuplicati rappLegalePerDup) {
		this.rappLegalePerDup = rappLegalePerDup;
	}

	public SedePerOpuDuplicati getSedePerDup() {
		return sedePerDup;
	}

	public void setSedePerDup(SedePerOpuDuplicati sedePerDup) {
		this.sedePerDup = sedePerDup;
	}
	

	public void buildRecordPerDuplicatiV2(ResultSet rs) throws SQLException {

		
		this.setIdStabilimento(rs.getInt("id_stabilimento_out"));
		this.setIdOperatore(rs.getInt("id_opu_operatore_out"));
		this.setImpresaIdTipoImpresa(rs.getInt("impresa_id_tipo_impresa_out"));
		this.setTipoImpresa( rs.getString("tipo_impresa_out") );
		this.setImpresaIdTipoSocieta(rs.getInt("impresa_id_tipo_societa_out"));
		this.setTipoSocieta(rs.getString("tipo_societa_out"));
		this.setRagioneSociale(rs.getString("ragione_sociale_out"));
		this.setPartitaIva(rs.getString("partita_iva_out"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa_out"));
		this.setDomicilioDigitale(rs.getString("domicilio_digitale_out"));
		this.setNomeRappSedeLegale(rs.getString("nome_rapp_sede_legale_out"));
		this.setCognomeRappSedeLegale(rs.getString("cognome_rapp_sede_legale_out"));
		this.setCfRappSedeLegale(rs.getString("cf_rapp_sede_legale_out"));
		Timestamp ts = rs.getTimestamp("data_inizio_attivita_out");
		if(null != ts)
		{
			this.setDataNascitaRappSedeLegale( new Date(ts.getTime()) );
		}
		
		this.setSessoRappSedeLegale(rs.getString("sesso_out"));
		this.setComuneNascitaRappSedeLegale(rs.getString("comune_nascita_rapp_sede_legale_out"));
		this.setSiglaProvSoggFisico(rs.getString("sigla_prov_soggfisico_out"));
		this.setComuneResidenza(rs.getString("comune_residenza_out"));
		this.setIndirizzoRappSedeLegale(rs.getString("indirizzo_rapp_sede_legale_out"));
		this.setToponimoResidenza(rs.getString("toponimo_residenza_out"));
		this.setIdToponimoResidenza(rs.getInt("id_toponimo_residenza_out"));
		this.setCivicoResidenza(rs.getString("civico_residenza_out"));
		this.setCivicoSedeLegale(rs.getString("civico_sede_legale_out"));
		this.setSiglaProvLegale(rs.getString("sigla_prov_legale_out"));
		this.setComuneSedeLegale(rs.getString("comune_sede_legale_out"));
		this.setIndirizzoSedeLegale(rs.getString("indirizzo_sede_legale_out"));
		this.setToponimoSedeLegale(rs.getString("toponimo_sede_legale_out"));
		this.setIdToponimoSedeLegale(rs.getInt("id_toponimo_sede_legale_out"));
		this.setSiglaProvOperativa(rs.getString("sigla_prov_operativa_out"));
		this.setComuneStab(rs.getString("comune_stab_out"));
		this.setIndirizzoStab(rs.getString("indirizzo_stab_out"));
		this.setCivicoSedeStab(rs.getString("civico_sede_stab_out"));
		this.setToponimoSedeStab(rs.getString("toponimo_sede_stab_out"));
		this.setIdToponimoStab(rs.getInt("id_toponimo_stab_out"));
		this.setStabIdAttivita(rs.getInt("stab_id_attivita_out"));
		this.setStabIdCarattere(rs.getInt("stab_id_carattere_out"));
		
		this.setNazioneResidenza(rs.getString("nazione_residenza"));
		this.setCapResidenza(rs.getString("cap_residenza"));
		this.setNazioneSedeLegale(rs.getString("nazione_sede_legale"));
		this.setCapSedeLegale(rs.getString("cap_sede_legale"));
		
		this.setCapSedeOperativa(rs.getString("cap_stab"));
		this.setNazioneSedeOperativa(rs.getString("nazione_stab"));
		
		ts = rs.getTimestamp("data_inizio_attivita_out");
		if(ts != null)
		{
			this.setDataInizioAttivita(new Date(ts.getTime()));
		}
		
		ts = rs.getTimestamp("data_fine_attivita_out");
		if(ts != null)
		{
			this.setDataInizioAttivita(new Date(ts.getTime()));	
		}
		
		
		
		
		
		
	}

	

	public void costruisciDaJsonFormConvergenzaImpresa(JSONObject jsonRepr) throws NoSuchElementException, ParseException {
		
//		for(Iterator<String> it=jsonRepr.keys(); it.hasNext();)
//		{
//			String chiave = it.next();
//			System.out.println(chiave+" "+jsonRepr.getString(chiave));
//		}
		
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		this.setImpresaIdTipoImpresa( mioParseIntDaJson(jsonRepr,"idTipoImpresa") );
		this.setImpresaIdTipoSocieta( mioParseIntDaJson(jsonRepr,"idTipoSocieta"));
		this.setRagioneSociale(eliminaCharEncodeDaJson(jsonRepr,"ragioneSociale"));
		setPartitaIva( eliminaCharEncodeDaJson(jsonRepr,"partitaIva") );
		this.setCodFiscale(eliminaCharEncodeDaJson(jsonRepr,"codFiscale"));
		setDomicilioDigitale(eliminaCharEncodeDaJson(jsonRepr,"domicilioDigitale"));

		String concatenazioneDaSplittare = eliminaCharEncodeDaJson(jsonRepr, "concatenazionePerRappLegale");
		String[] tokens = concatenazioneDaSplittare.split("|");
		setNomeRappSedeLegale(!tokens[0].equals("-") ? tokens[0] : null ); //se il token e' -, rappresenta valore nullo
		setCognomeRappSedeLegale(!tokens[1].equals("-") ? tokens[1] : null);
		setSessoRappSedeLegale(!tokens[2].equals("-") ? tokens[2] : null);
		setCfRappSedeLegale(!tokens[3].equals("-") ? tokens[3] : null);
		
		
		
		
		setDataNascitaRappSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"dataNascitaRappSedeLegale")  != null ? dateFormat.parse(  eliminaCharEncodeDaJson(jsonRepr,"dataNascitaRappSedeLegale") ) : null );
		setComuneNascitaRappSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"comuneNascitaRappSedeLegale"));
		setSiglaProvSoggFisico(eliminaCharEncodeDaJson(jsonRepr,"siglaProvSoggFisico"));
		setComuneResidenza(eliminaCharEncodeDaJson(jsonRepr,"comuneResidenza"));
		setIndirizzoRappSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"indirizzoRappSedeLegale"));
		setIdToponimoResidenza(mioParseIntDaJson(jsonRepr,"idToponimoResidenza"));
		setCivicoResidenza(eliminaCharEncodeDaJson(jsonRepr,"civicoResidenza"));
		setCapResidenza(eliminaCharEncodeDaJson(jsonRepr,"capResidenza"));
		setNazioneResidenza(eliminaCharEncodeDaJson(jsonRepr,"nazioneResidenza"));
		setSiglaProvLegale(eliminaCharEncodeDaJson(jsonRepr,"siglaProvLegale"));
		setComuneSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"comuneSedeLegale"));
		setIndirizzoSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"indirizzoSedeLegale"));
		setCivicoSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"civicoSedeLegale"));
		setCapSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"capSedeLegale"));
		setNazioneSedeLegale(eliminaCharEncodeDaJson(jsonRepr,"nazioneSedeLegale"));
		setIdToponimoSedeLegale(mioParseIntDaJson(jsonRepr,"idToponimoSedeLegale"));
		setIdUtente(mioParseIntDaJson(jsonRepr,"idUtente"));
		
		JSONObject idOperatoriJson = jsonRepr.getJSONObject("idOperatori");
		ArrayList<Integer> idOperatoriScelti = new ArrayList<Integer>();
		for(Iterator<String> it = idOperatoriJson.keys(); it.hasNext();)
		{
			idOperatoriScelti.add(Integer.parseInt(it.next()));
		}
		setArrayImprese(idOperatoriScelti);
		
//		setArrayImprese();
		
//		for(Iterator<String> it=jsonRepr.keys(); it.hasNext();)
//		{	
//			
//			System.out.println(it.next()+" "+((String)jsonRepr.get(it.next()) ).replaceAll("$", " ") );
//		}
		
	}
	
	
	
	public void costruisciDaJsonFormConvergenzaImpianti(JSONObject jsonRepr) throws ParseException {
		
		boolean debugPrint = true;
		
		
		this.setStabIdAttivita( mioParseIntDaJson(jsonRepr,"idTipoAttivita") );
		this.setStabIdCarattere( mioParseIntDaJson(jsonRepr,"idTipoCarattere"));
		this.setSiglaProvOperativa(eliminaCharEncodeDaJson(jsonRepr,"siglaProvincia"));
		setComuneStab( eliminaCharEncodeDaJson(jsonRepr,"comune") );
		setIdToponimoStab(mioParseIntDaJson(jsonRepr,"idToponimo"));
		
		setIndirizzoStab(eliminaCharEncodeDaJson(jsonRepr, "indirizzo"));
		setCivicoSedeStab(eliminaCharEncodeDaJson(jsonRepr, "civico"));
		setIdUtente(mioParseIntDaJson(jsonRepr,"idUtente"));
		
		
		setDataInizioAttivita( parseStringJsonPerTimestamp(jsonRepr, "dataInizioAttivita")  );
		setDataInizioAttivita( parseStringJsonPerTimestamp(jsonRepr, "dataFineAttivita")  );
		setIdOperatore(mioParseIntDaJson(jsonRepr,"idOperatore"));
		
		setCapSedeOperativa(eliminaCharEncodeDaJson(jsonRepr,"capSedeOperativa"));
		setNazioneSedeOperativa(eliminaCharEncodeDaJson(jsonRepr,"nazioneSedeOperativa"));
		
		JSONObject idImpiantiJson = jsonRepr.getJSONObject("idImpianti");
		ArrayList<Integer> idImpianti = new ArrayList<Integer>();
		for(Iterator<String> it = idImpiantiJson.keys(); it.hasNext();)
		{
			idImpianti.add(Integer.parseInt(it.next()));
		}
		setArrayImpianti(idImpianti);
		
		
		if(debugPrint)
		{
			System.out.println("INIZIO DEBUG VALORI RICEVUTI JSON PER CONVERGENZA IMPIANTI--------------");
			System.out.println("id Attivita "+getStabIdAttivita());
			System.out.println("id Carattere "+getStabIdCarattere());
			System.out.println("sigla prov "+getSiglaProvOperativa());
			System.out.println("comune stab "+getComuneStab());
			System.out.println("id toponimostab "+getIdToponimoStab());
			System.out.println("indirizzo "+getIndirizzoStab());
			System.out.println("civico "+getCivicoSedeStab());
			System.out.println("id Utente "+getIdUtente());
			System.out.println("data inizio attivita "+getDataInizio());
			System.out.println("data fine "+getDataFineATtivita());
			System.out.println("id Operat "+getIdOperatore());
			System.out.println("lista ID IMPIANTI SELEZIONATI");
			for(Integer id : idImpianti)
			{
				System.out.println("id "+id);
			}
			System.out.println("----------------------------------------------------------------");
		}
		
		
	}
	
	
	
	
	

	private String eliminaCharEncodeDaJson(JSONObject jsonRepr,String propToGet)
	{
		try
		{
			String toRet = jsonRepr.getString(propToGet);
			
			if(toRet.indexOf("$") != -1)
			{
				toRet = toRet.replaceAll(Pattern.quote("$"), " ");
			}
			
			return toRet;
		}
		catch(Exception ex)
		{
			System.out.println("ATTENZIONE PROPRIETA "+propToGet+" NON SETTATA");
			return null;
		}
	}
	
	private Timestamp parseStringJsonPerTimestamp(JSONObject jsonRepr, String propToGet)
	{
		Timestamp toRet = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		try
		{
			String dataStr = eliminaCharEncodeDaJson(jsonRepr, propToGet);
			if(dataStr == null) //data non settata proprio
				toRet = null;
			else
				toRet = new Timestamp(dateFormat.parse(dataStr).getTime());
				
		}
		catch(Exception ex)
		{
			System.out.println("ATTENZIONE DATA  "+propToGet+" NON PARSABILE");
			toRet = null;
		}
		
		return toRet;
	}
	
	private Integer mioParseIntDaJson(JSONObject jsonRepr,String propToGet)
	{
		try
		{
			String toParse = jsonRepr.getString(propToGet);
			Integer t = Integer.parseInt(toParse);
			return t;
		}
		catch(Exception ex) //entra qui se la proprieta non e' settata o se e' settata ma non e' possibile farne parsing
		{
			System.out.println("ATTENZIONE -> PARSING INTEGER INVALIDO (NON SETTATO O INVALIDO) PER PROPRIETA "+propToGet);
			return null;
		}
	}

	public void setDescrTipoAttivita(String string) {
		// TODO Auto-generated method stub
		this.descrTipoAttivita = string;
	}

	public String getDescrTipoAttivita()
	{
		return this.descrTipoAttivita;
	}
	
	public void setDescrTipoCarattere(String string)
	{
		this.descrTipoCarattere = string;
	}
	public String getDescrTipoCarattere()
	{
		return this.descrTipoCarattere;
	}

	

	
//	public HashMap<String, Object> getHashmapOperatore() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
//	{
//
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		Field[] campi = this.getClass().getDeclaredFields();
//		Method[] metodi = this.getClass().getMethods();
//		for (int i = 0 ; i <campi.length; i++)
//		{
//			String nomeCampo = campi[i].getName();
//
//			
//			
//			
//			
//			
//			
//			if ( ! nomeCampo.equalsIgnoreCase("domicilioDigitale") && ! nomeCampo.equalsIgnoreCase("rappLegale") && ! nomeCampo.equalsIgnoreCase("storicoSoggettoFisico") && ! nomeCampo.equalsIgnoreCase("listaSediOperatore") 
//					&& ! nomeCampo.equalsIgnoreCase("listaStabilimenti")  && ! nomeCampo.equalsIgnoreCase("phoneNumberList") 
//					&& ! nomeCampo.equalsIgnoreCase("emailAddressList"))
//			{
//				for (int j=0; j<metodi.length; j++ )
//				{
//
//					if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
//					{
//						
//						map.put(nomeCampo,new String (""+metodi[j].invoke(this)));
//						
//						
//
//					}
//				}
//
//			}
//
//		}
//		
//		if (rappLegale!=null)
//		{
//			
//			JSONObject rapp = new JSONObject(rappLegale.getHashmapSoggettoFisico());
//			map.put("rapplegale", rapp);
//		}
//			
//		if(getSedeLegale()!=null)
//		{
//			
//			JSONObject sedeleg = new JSONObject(getSedeLegale().getHashmapIndirizzo());
//				map.put("sedelegale",sedeleg );
//		
//
//
//		}
//
//
//
//		return map ;
//
//	}




}
