/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.ricercaunica.base;


import com.darkhorseventures.framework.beans.GenericBean;











import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.omg.CORBA.DATA_CONVERSION;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class RicercaOpu extends GenericBean {
										 	
	//static final long serialVersionUID =-6915867402685037717L;

  /**
	 * 
	 */
	private static final long serialVersionUID = -566967668465298257L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;
  
  private int idNorma;
  private String ragioneSociale = "";
  private String comune = null;
  private String codiceFiscaleSoggettoFisico = null;
  private String comuneSedeProduttiva = null;
  private String partitaIva =null;
  private String codiceFiscale =null;
  private String indirizzoSedeProduttiva =null;
  private String asl = null;
  private int tipologia = -1;
  private int orgId;
  
  private Timestamp data_inizio_attivita;
  private Timestamp data_fine_attivita;
 private String norma ;
  private int riferimentoId = -1;
  private String riferimentoIdNome = "";
  private String tipo_impresa = "";
  private String note = "";
  private String numeroRegistrazione ="";
  
  //Adding field
  private String n_linea="";
  
  private String numAut = "";
  private String provSedeProduttiva = "";
  private String capSedeProduttiva = "";
  private String indirizzoSedeLegale = "";
  private String comuneSedeLegale = "";
  private String provSedeLegale = "";
  private String capSedeLegale = "";
private String tipoAttivita ;
private String targa ;
private String matricola ;

private String color ;
private int tipoRicerca ;
private int numCu ;
private String msgConvergenza ;
private int esitoConvergenza ;




public String getMsgConvergenza() {
	return msgConvergenza;
}


public void setMsgConvergenza(String msgConvergenza) {
	this.msgConvergenza = msgConvergenza;
}


public int getEsitoConvergenza() {
	return esitoConvergenza;
}


public void setEsitoConvergenza(int esitoConvergenza) {
	this.esitoConvergenza = esitoConvergenza;
}


private ArrayList<Integer> tipologiaAnagraficheVersoCuiConvergere = new ArrayList<Integer>();



  
  public ArrayList<Integer> getTipologiaAnagraficheVersoCuiConvergere() {
	return tipologiaAnagraficheVersoCuiConvergere;
}


public void setTipologiaAnagraficheVersoCuiConvergere(ArrayList<Integer> tipologiaAnagraficheVersoCuiConvergere) {
	this.tipologiaAnagraficheVersoCuiConvergere = tipologiaAnagraficheVersoCuiConvergere;
}


public String getN_linea() {
	return n_linea;
}


public void setN_linea(String n_linea) {
	this.n_linea = n_linea;
}


public void setTipologieVersoCuiConvergere(Connection db)
{
	
	String sql = "select * from dbi_get_tipo_convergenza(?)";
	try
	{
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, tipologia);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			this.tipologiaAnagraficheVersoCuiConvergere.add(rs.getInt(1));
		}
		
	}
	catch(SQLException e)
	{
		
	}
	
}

public int getNumCu() {
	return numCu;
}


public void setNumCu(int numCu) {
	this.numCu = numCu;
}


public String getColor() {
	return color;
}


public void setColor(String color) {
	this.color = color;
}


public String getTarga() {
	return targa;
}


public void setTarga(String targa) {
	this.targa = targa;
}


public String getTipoAttivita() {
	return tipoAttivita;
}


public void setTipoAttivita(String tipoAttivita) {
	this.tipoAttivita = tipoAttivita;
}


public int getIdNorma() {
	return idNorma;
}


public void setIdNorma(int idNorma) {
	this.idNorma = idNorma;
}


public int getOrgId() {
	return orgId;
}


public String getNorma() {
	return norma;
}


public void setNorma(String norma) {
	this.norma = norma;
}


public void setOrgId(int orgId) {
	this.orgId = orgId;
}


public Timestamp getData_inizio_attivita() {
	return data_inizio_attivita;
}


public void setData_inizio_attivita(Timestamp data_inizio_attivita) {
	this.data_inizio_attivita = data_inizio_attivita;
}


public Timestamp getData_fine_attivita() {
	return data_fine_attivita;
}


public void setData_fine_attivita(Timestamp data_fine_attivita) {
	this.data_fine_attivita = data_fine_attivita;
}


public String getProvSedeProduttiva() {
	return provSedeProduttiva;
}


public void setProvSedeProduttiva(String provSedeProduttiva) {
	this.provSedeProduttiva = provSedeProduttiva;
}


public String getCapSedeProduttiva() {
	return capSedeProduttiva;
}


public void setCapSedeProduttiva(String capSedeProduttiva) {
	this.capSedeProduttiva = capSedeProduttiva;
}


public String getIndirizzoSedeLegale() {
	return indirizzoSedeLegale;
}


public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
	this.indirizzoSedeLegale = indirizzoSedeLegale;
}


public String getComuneSedeLegale() {
	return comuneSedeLegale;
}


public void setComuneSedeLegale(String comuneSedeLegale) {
	this.comuneSedeLegale = comuneSedeLegale;
}


public String getProvSedeLegale() {
	return provSedeLegale;
}


public void setProvSedeLegale(String provSedeLegale) {
	this.provSedeLegale = provSedeLegale;
}


public String getCapSedeLegale() {
	return capSedeLegale;
}


public void setCapSedeLegale(String capSedeLegale) {
	this.capSedeLegale = capSedeLegale;
}


private String stato =null;
  private String statoImpresa =null;
  private String attivita =null;
  private String macroarea =null;
  private String aggregazione =null;

  private int categoriaRischio ;
  private Timestamp dataProssimoControllo ;
  private int numeroControlli ;
 
  private HashMap<Integer, ArrayList<LineeAttivita>> listaCandatiPerLineaVecchiaAnagrafica = new HashMap<Integer, ArrayList<LineeAttivita>>();
  private String n_Regold;
  public ArrayList<LineaProduttiva> getListaControlliPerLinea() {
	return listaControlliPerLinea;
	
}
  


public HashMap<Integer, ArrayList<LineeAttivita>> getListaCandatiPerLineaVecchiaAnagrafica() {
	return listaCandatiPerLineaVecchiaAnagrafica;
}


public void setListaCandatiPerLineaVecchiaAnagrafica(
		HashMap<Integer, ArrayList<LineeAttivita>> listaCandatiPerLineaVecchiaAnagrafica) {
	this.listaCandatiPerLineaVecchiaAnagrafica = listaCandatiPerLineaVecchiaAnagrafica;
}


public void setListaControlliPerLinea(ArrayList<LineaProduttiva> listaControlliPerLinea) {
	this.listaControlliPerLinea = listaControlliPerLinea;
}


public String getN_Regold() {
	return n_Regold;
}


public void setN_Regold(String n_Regold) {
	this.n_Regold = n_Regold;
}


public String getMacroarea() {
	return macroarea;
}


public void setMacroarea(String macroarea) {
	this.macroarea = macroarea;
}


public String getAggregazione() {
	return aggregazione;
}


public void setAggregazione(String aggregazione) {
	this.aggregazione = aggregazione;
}


private Timestamp dataUltimoControlloSorveglianza; 
  private String riferimentoIdNomeCol;
  private String riferimentoIdNomeTab;
  
  private ArrayList<LineaProduttiva> listaControlliPerLinea = new ArrayList<LineaProduttiva>();
private int id_tipo_attivita ;


  
  
  public int getId_tipo_attivita() {
	return id_tipo_attivita;
}


public void setId_tipo_attivita(int id_tipo_attivita) {
	this.id_tipo_attivita = id_tipo_attivita;
}


public int getTipoRicerca() {
	return tipoRicerca;
}


public void setTipoRicerca(int tipoRicerca) {
	this.tipoRicerca = tipoRicerca;
}


public String getRiferimentoIdNomeCol() {
	return riferimentoIdNomeCol;
}


public void setRiferimentoIdNomeCol(String riferimentoIdNomeCol) {
	this.riferimentoIdNomeCol = riferimentoIdNomeCol;
}


public String getRiferimentoIdNomeTab() {
	return riferimentoIdNomeTab;
}


public void setRiferimentoIdNomeTab(String riferimentoIdNomeTab) {
	this.riferimentoIdNomeTab = riferimentoIdNomeTab;
}


public int getCategoriaRischio() {
	return categoriaRischio;
}


public void setCategoriaRischio(int categoriaRischio) {
	this.categoriaRischio = categoriaRischio;
}


public Timestamp getDataProssimoControllo() {
	return dataProssimoControllo;
}


public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
	this.dataProssimoControllo = dataProssimoControllo;
}


public int getNumeroControlli() {
	return numeroControlli;
}


public void setNumeroControlli(int numeroControlli) {
	this.numeroControlli = numeroControlli;
}


public String getNumeroRegistrazione() {
	return numeroRegistrazione;
}


public void setNumeroRegistrazione(String numeroRegistrazione) {
	this.numeroRegistrazione = numeroRegistrazione;
}


public String getNumAut() {
	return numAut;
}


public void setNumAut(String numAut) {
	this.numAut = numAut;
}


public String getRagioneSociale() {
	return ragioneSociale;
}


public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}


public String getPartitaIva() {
	return partitaIva;
}


public void setPartitaIva(String partitaIva) {
	this.partitaIva = partitaIva;
}



public String getRagionseSociale() {
	return ragioneSociale;
}


public void setRagionseSociale(String ragionseSociale) {
	this.ragioneSociale = ragionseSociale;
}


public String getComune() {
	return comune;
}


public void setComune(String comune) {
	this.comune = comune;
}


public String getCodiceFiscaleSoggettoFisico() {
	return codiceFiscaleSoggettoFisico;
}


public void setCodiceFiscaleSoggettoFisico(String codiceFiscaleSoggettoFisico) {
	this.codiceFiscaleSoggettoFisico = codiceFiscaleSoggettoFisico;
}


public RicercaOpu() { }


public String getAsl() {
	return asl;
}


public void setAsl(String asl) {
	this.asl = asl;
}


public int getTipologia() {
	return tipologia;
}


public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}


public int getRiferimentoId() {
	return riferimentoId;
}


public void setRiferimentoId(int riferimentoId) {
	this.riferimentoId = riferimentoId;
}


public String getRiferimentoIdNome() {
	return riferimentoIdNome;
}


public void setRiferimentoIdNome(String riferimentoIdNome) {
	this.riferimentoIdNome = riferimentoIdNome;
}


public String getComuneSedeProduttiva() {
	return comuneSedeProduttiva;
}


public void setComuneSedeProduttiva(String comuneSedeProduttiva) {
	this.comuneSedeProduttiva = comuneSedeProduttiva;
}


public String getIndirizzoSedeProduttiva() {
	return indirizzoSedeProduttiva;
}


public void setIndirizzoSedeProduttiva(String indirizzoSedeProduttiva) {
	this.indirizzoSedeProduttiva = indirizzoSedeProduttiva;
}


public String getStato() {
	return stato;
}


public void setStato(String stato) {
	this.stato = stato;
}


public String getStatoImpresa() {
	return statoImpresa;
}


public void setStatoImpresa(String statoImpresa) {
	this.statoImpresa = statoImpresa;
}


public String getAttivita() {
	return attivita;
}


public void setAttivita(String attivita) {
	this.attivita = attivita;
}

  

private void setNumCu(Connection db) throws SQLException
{
	String sel = "select count(*) from ticket where trashed_date is null and tipologia = 3 and "+this.getRiferimentoIdNomeCol()+" = "+this.getRiferimentoId();
	PreparedStatement pst = db.prepareStatement(sel);
	pst.setInt(1, this.getRiferimentoId());
	ResultSet rs = pst.executeQuery();
	if(rs.next())
	{
		numCu=rs.getInt(1);
	}
}
/**
 * METODO VALIDO SOLO PER  IL NUOVO OPERATORE
 * @param db
 * @throws SQLException
 */
public void setListaLineeProduttiveConControllo(Connection db) throws SQLException
{
	
	
	if(tipologia==999)
	{
	String sel = "select count(idcontrollo) as numero_cu_out,id_Rel_stab_lp_out,id_linea_master_list_out,enabled_out,descrizione_out, id_norma_out, id_stato_out  "
			+ "from dbi_get_controlli_ufficiali_su_linee_produttive(?)"
			+ "group by id_Rel_stab_lp_out,id_linea_master_list_out,enabled_out,descrizione_out, id_norma_out, id_stato_out";
	PreparedStatement pst = db.prepareStatement(sel);
	pst.setInt(1, this.getRiferimentoId());
	ResultSet rs = pst.executeQuery();
	while(rs.next())
	{
		LineaProduttiva linea = new LineaProduttiva();
		linea.setId_rel_stab_lp(rs.getInt("id_rel_stab_lp_out"));
		linea.setId(rs.getInt("id_linea_master_list_out"));
		linea.setNumeroControlliUfficialiEseguiti(rs.getInt("numero_cu_out"));
		linea.setEnabled(rs.getBoolean("enabled_out"));
		linea.setDescrizione_linea_attivita(rs.getString("descrizione_out"));
		linea.setIdNorma(rs.getInt("id_norma_out"));
		linea.setStato(rs.getInt("id_stato_out"));
		this.getListaControlliPerLinea().add(linea);
	}
	}
	
	else
	{
		if(tipologia==1)
		{
			String sel = "select count(idcontrollo) as numero_cu_out,id_Rel_stab_lp_out,id_linea_master_list_out,enabled_out,descrizione_out, id_norma_out, id_stato_out "
					+ "from dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(?) "
					+ "group by id_Rel_stab_lp_out,id_linea_master_list_out,enabled_out,descrizione_out, id_norma_out, id_stato_out";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, this.getRiferimentoId());
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				LineaProduttiva linea = new LineaProduttiva();
				linea.setId_rel_stab_lp(rs.getInt("id_rel_stab_lp_out"));
				linea.setId(rs.getInt("id_linea_master_list_out"));
				linea.setNumeroControlliUfficialiEseguiti(rs.getInt("numero_cu_out"));
				linea.setEnabled(rs.getBoolean("enabled_out"));
				linea.setDescrizione_linea_attivita(rs.getString("descrizione_out"));
				linea.setIdNorma(rs.getInt("id_norma_out"));
				linea.setStato(rs.getInt("id_stato_out"));
				this.getListaControlliPerLinea().add(linea);
				load_candiati_per_vecchia_anagrafica(this.getRiferimentoId(), linea.getId_rel_stab_lp(), db);
			}
			
			
		}
	}
	
	setUltimaSorveglianza( db);
}


public void setNumeroControlli(Connection db) throws SQLException
{
	
	String sel = "select count(*) from ticket where trashed_date is null and tipologia = 3 and "+riferimentoIdNomeCol+" ="+riferimentoId;
	PreparedStatement pst = db.prepareStatement(sel);
	ResultSet rs = pst.executeQuery();
	if(rs.next())
		numeroControlli = rs.getInt(1);
	
	setUltimaSorveglianza( db);
}


public void setUltimaSorveglianza(Connection db) throws SQLException
{
	
	String sel = "select max(assigned_date)as data_controllo,ticketid,categoria_rischio,ticket.data_prossimo_controllo,isaggiornata_categoria,punteggio ,audit.id,ticket.id_stabilimento " +
"from ticket " +
"left join audit on audit.id_controllo= ticket.ticketid||'' and audit.trashed_date is null " +
"where ticket."+riferimentoIdNomeCol+" = "+riferimentoId +" and ticket.trashed_date is null and ticket.provvedimenti_prescrittivi =5 " +
"group by ticketid,categoria_rischio,ticket.data_prossimo_controllo,categoria_rischio,ticket.data_prossimo_controllo,audit.id,ticket.id_stabilimento " +
"order by max(assigned_date) desc limit 1";
	PreparedStatement pst = db.prepareStatement(sel);
	ResultSet rs = pst.executeQuery();
	if(rs.next())
		dataUltimoControlloSorveglianza = rs.getTimestamp("data_controllo");
}


public Timestamp getDataUltimoControlloSorveglianza() {
	return dataUltimoControlloSorveglianza;
}


public void setDataUltimoControlloSorveglianza(Timestamp dataUltimoControlloSorveglianza) {
	this.dataUltimoControlloSorveglianza = dataUltimoControlloSorveglianza;
}
  

public RicercaOpu(Connection db, int rifId,String rifNomeColonna, String archiviato) throws SQLException {
	
	  
    PreparedStatement pst = db.prepareStatement(
        "select * from global_arch_view where riferimento_id = ? and riferimento_id_nome_col = ?");
    pst.setInt(1, rifId);
    pst.setString(2, rifNomeColonna);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
    	setFieldRecord(rs);
    	setNumeroControlli(db);
    	setListaLineeProduttiveConControllo(db);
    	setTipologieVersoCuiConvergere(db);
    	
    }
    rs.close();
    pst.close();
    
}



public RicercaOpu(Connection db, int rifId,String rifNomeColonna) throws SQLException {
	
	  
   
    PreparedStatement pst = db.prepareStatement(
        "select * from ricerche_anagrafiche_old_materializzata where riferimento_id = ? and riferimento_id_nome_col = ?");
    pst.setInt(1, rifId);
    pst.setString(2, rifNomeColonna);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
    	setFieldRecord(rs);
    	setNumeroControlli(db);
    	setListaLineeProduttiveConControllo(db);
    	setTipologieVersoCuiConvergere(db);
    	
    }
    rs.close();
    pst.close();
    
}

public RicercaOpu(Connection db, int rifId,String rifNomeTab, boolean nomeTab) throws SQLException {
		  
	   
    PreparedStatement pst = db.prepareStatement(
        "select * from ricerche_anagrafiche_old_materializzata where riferimento_id = ? and riferimento_id_nome_tab = ?");
    pst.setInt(1, rifId);
    pst.setString(2, rifNomeTab);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
    	setFieldRecord(rs);
    //	setNumeroControlli(db);
    //	setListaLineeProduttiveConControllo(db);
    //	setTipologieVersoCuiConvergere(db);
    	
    }
    rs.close();
    pst.close();
    
}

public RicercaOpu(Connection db, int rifId,int tipologia) throws SQLException {
	
	PreparedStatement pst = db.prepareStatement("select * from ricerche_anagrafiche_old_materializzata where riferimento_id = ? and tipologia_operatore = ?");
    pst.setInt(1, rifId);
    pst.setInt(2, tipologia);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
    	setFieldRecord(rs);
    	setNumeroControlli(db);
    	setListaLineeProduttiveConControllo(db);
    	setTipologieVersoCuiConvergere(db);
    	
    }
    rs.close();
    pst.close();
    
}


public RicercaOpu(Connection db, int org_id) throws SQLException {
	
	  
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
      //  "select * from public_functions.dbi_get_stab_archiviati() where org_id = ?");
    		  "select * from ricerche_anagrafiche_old_materializzata where tipo_ricerca_anagrafica = 3 and riferimento_id = ?");
    pst.setInt(1, org_id);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
}


public void setFieldRecord(ResultSet rs) throws SQLException {
	  
	
	this.setIdNorma(rs.getInt("id_norma"));
	this.setRagionseSociale(rs.getString("ragione_sociale"));
	this.setPartitaIva(rs.getString("partita_iva"));
	this.setCodiceFiscale(rs.getString("codice_fiscale"));
	this.setAsl(rs.getString("asl"));
	this.setTipologia(rs.getInt("tipologia_operatore"));
	this.setRiferimentoId(rs.getInt("riferimento_id"));
	this.setRiferimentoIdNome(rs.getString("riferimento_id_nome"));
	this.setNorma(rs.getString("norma"));
	this.setNumeroRegistrazione(rs.getString("n_reg"));
	this.setNumAut(rs.getString("num_riconoscimento"));
	
	this.setStato(rs.getString("stato"));
//	this.setStatoImpresa(rs.getString("stato_impresa"));
	this.setAttivita(rs.getString("attivita"));
	
	this.setIndirizzoSedeProduttiva(rs.getString("indirizzo") + ", "+rs.getString("comune")  + ", "+rs.getString("provincia_stab"));
    try
    {
    	this.setCategoriaRischio(rs.getInt("categoria_rischio"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
        this.setN_Regold(rs.getString("n_reg_old"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
        this.setIdAsl(rs.getInt("asl_rif"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    try
    {
        this.setComuneSedeProduttiva(rs.getString("comune"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
        this.setComuneSedeLegale(rs.getString("comune_leg"));
    }
    catch(Exception e)
    {
    	
    }
	
    
    
    try
    {
    	this.setMacroarea(rs.getString("macroarea"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    try
    {
    	this.setMacroarea(rs.getString("aggregazione"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setMacroarea(rs.getString("attivita"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
    	this.setId_tipo_attivita(rs.getInt("tipo_attivita"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setDataProssimoControllo(rs.getTimestamp("prossimo_controllo"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setRiferimentoIdNomeCol(rs.getString("riferimento_id_nome_col"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
    	this.setRiferimentoIdNomeTab(rs.getString("riferimento_id_nome_tab"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setTipoAttivita(rs.getString("tipo_attivita_descrizione"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setTarga(rs.getString("targa"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setMatricola(rs.getString("matricola"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setTipoRicerca(rs.getInt("tipo_ricerca_anagrafica"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setColor(rs.getString("color"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	this.setCodiceFiscaleSoggettoFisico(rs.getString("codice_fiscale_rappresentante"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    
    
  }


public  void load_candiati_per_vecchia_anagrafica( int org_id,int idLineaVecchiaAnagrafica, Connection db)
{

	
	
		
	String sqlCandidati = "	select * from proponi_candidati_mapping(?)  where (ranking,idvecchialineaoriginale_out) in "+ 
						"("+
						"SELECT max(ranking),idvecchialineaoriginale_out FROM proponi_candidati_mapping(?)"
						+ " where idvecchialineaoriginale_out=? "+
						"group by idvecchialineaoriginale_out "+
						")";
	HashMap<Integer,Integer> maxRanksPerMapping = new HashMap<Integer,Integer>();
	
	try
	{
		//parte ottenimento candidati 
		try
		{
			PreparedStatement stat = db.prepareStatement(sqlCandidati); //ottengo tutti i candidati ordinati per ranking, e scelgo solo (per ogni linea vecchia) quello / i (piu' di uno possono essercene) col rank massimo
			stat.setInt(1, org_id);
			stat.setInt(2, org_id);
			stat.setInt(3, idLineaVecchiaAnagrafica);
			ResultSet res = stat.executeQuery();
			while(res.next())
			{
				
				
				LineeAttivita candidato = new LineeAttivita();
				
				candidato.setAggregazione(res.getString(6));
				candidato.setAttivita(res.getString(8));
				candidato.setMacroarea(res.getString(4));
				candidato.setId(res.getInt(2)); //id linea nuova
				candidato.setIdAggregazione(res.getInt(5));
				candidato.setIdAttivita(res.getInt(7));
				candidato.setIdMacroarea(res.getInt(3));
				candidato.setRanking(res.getInt(10));
				candidato.setNorma(res.getInt(9));
				candidato.setIdLineaVecchiaOriginale(res.getInt(11));
				
				if(this.listaCandatiPerLineaVecchiaAnagrafica.get(idLineaVecchiaAnagrafica)==null)
				{
					ArrayList<LineeAttivita> listaCandiatiPerVecchiaLinea = new ArrayList<LineeAttivita>();
					listaCandiatiPerVecchiaLinea.add(candidato);
					this.listaCandatiPerLineaVecchiaAnagrafica.put(idLineaVecchiaAnagrafica, listaCandiatiPerVecchiaLinea);
					
				}
				else
				{
					ArrayList<LineeAttivita> listaCandiatiPerVecchiaLinea = this.listaCandatiPerLineaVecchiaAnagrafica.get(idLineaVecchiaAnagrafica);
					listaCandiatiPerVecchiaLinea.add(candidato);
					this.listaCandatiPerLineaVecchiaAnagrafica.put(idLineaVecchiaAnagrafica, listaCandiatiPerVecchiaLinea);
				}
				
				
				
			}
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
		 
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

	


}

protected void buildRecord(ResultSet rs) throws SQLException {
	
    //organization table
	orgId = rs.getInt("org_id");
	partitaIva = rs.getString("partita_iva");
	codiceFiscale = rs.getString("codice_fiscale");
	asl = rs.getString("asl");
	numeroRegistrazione = rs.getString("n_reg");
	n_linea = rs.getString("n_linea");
	tipo_impresa = rs.getString("tipo_impresa");
	ragioneSociale = rs.getString("ragione_sociale");
	statoImpresa = rs.getString("stato_impresa");
	data_inizio_attivita =  rs.getTimestamp("data_inizio_attivita");
	data_fine_attivita =  rs.getTimestamp("data_fine_attivita");
	note =  rs.getString("note");
	categoriaRischio =  rs.getInt("categoria_rischio");
	indirizzoSedeProduttiva =  rs.getString("indirizzo");
	comuneSedeProduttiva =  rs.getString("comune");
	provSedeProduttiva =  rs.getString("provincia_stabilimento");
	capSedeProduttiva =  rs.getString("cap_stabilimento");
	indirizzoSedeLegale =  rs.getString("indirizzo_legale");
	comuneSedeLegale =  rs.getString("comune_legale");
	provSedeLegale =  rs.getString("prov_legale");
	capSedeLegale =  rs.getString("cap_legale");
	
	try
	{
		tipoAttivita= rs.getString("tipo_attivita_descrizione");
		targa = rs.getString("targa");
		matricola = rs.getString("matricola");

	}
	catch(SQLException e)
	{
		
	}
	
 
}

public ArrayList<Integer> getListaControlliPerLinea(Connection db, int idRel) throws SQLException
{
	ArrayList<Integer> listaCu = new ArrayList<Integer>();
	
	if(tipologia==999)
	{
	String sel = "select idcontrollo "
			+ "from dbi_get_controlli_ufficiali_su_linee_produttive(?)"
			+ " where id_Rel_stab_lp_out = ? ";
	PreparedStatement pst = db.prepareStatement(sel);
	pst.setInt(1, this.getRiferimentoId());
	pst.setInt(2, idRel);
	ResultSet rs = pst.executeQuery();
	while(rs.next())
	{
		if (rs.getInt(1)>0)
			listaCu.add(rs.getInt(1));
	}
	}
	
	else
	{
		if(tipologia==1)
		{
			String sel = "select idcontrollo  "
					+ "from dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(?) "
					+ " where id_Rel_stab_lp_out = ? ";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, this.getRiferimentoId());
			pst.setInt(2, idRel);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				if (rs.getInt(1)>0)
					listaCu.add(rs.getInt(1));
			}
			
			
		}
	}
	
	return listaCu;
}


public String getCodiceFiscale() {
	return codiceFiscale;
}


public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
}


public String getMatricola() {
	return matricola;
}


public void setMatricola(String matricola) {
	this.matricola = matricola;
}


}

