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
package org.aspcfs.modules.molluschibivalvi.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class Organization extends GenericBean {


	public static final int TIPOLOGIA_ZONA_DI_PRODUZIONE_ABUSIVA = 4 ;
	public static final int TIPOLOGIA_ZONA_DI_STABULAZIONE = 3 ;
	public static final int TIPOLOGIA_ZONA_IN_CONCESSIONE = 2 ;
	public static final int TIPOLOGIA_SPECCHIO_ACQUEO = 5 ;
	public static final int TIPOLOGIA_BANCO_NATURALE = 1 ;
	

	public static final int CLASSE_A = 1;
	public static final int CLASSE_B = 2;
	public static final int CLASSE_C = 3;
	public static final int CLASSE_STABULAZIONE = 4;
	
public static final int STATO_CLASSIFICAZIONE_ATTIVO = 0;
public static final int STATO_CLASSIFICAZIONE_IN_SCADENZA = 1;
public static final int STATO_CLASSIFICAZIONE_SCADUTO = 2;
public static final int STATO_CLASSIFICAZIONE_SOSPESO = 3;
public static final int STATO_CLASSIFICAZIONE_REVOCATO = 4;
	

public static final int ID_ML_MOLLUSCHI = 322;

	private static final long serialVersionUID = 4320567602597719160L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	private int accountSize = -1;

	
	private int provvedimentiRestrittivi ;
	private Timestamp trashedDate = null;
	private int categoriaRischio=-1;
	private int id = -1						;
	private int orgId ;
	private String name = ""						;
	private int siteId = -1						;
	private java.sql.Timestamp entered = null		;
	private java.sql.Timestamp modified = null	;
	private int enteredBy 						;
	private int modifiedBy 						;
	private int tipologia 						;
	private int tipoMolluschi 					;
	private int tipoMolluschiOld 					;

	private int classe 							;
	private Timestamp dataClassificazione 		;
	private Timestamp dataFineClassificazione 	;
	private Timestamp dataProvvedimento			;
	private Timestamp dataProvvedimentoSospensione			;
	private int idUltimoProvvedimento ;
	private String provvedimento 				;	
	private String numClassificazione			 ;
	private boolean sottopostaDeclassamento 		;
	private int declassataIn 						;
	private String decretoClassificazione 		;
	private String ipEntered ;
	private String ipModified ;
	private OrganizationAddressList addressList = new OrganizationAddressList();
	private String cun ;
	private String stato ;
	
	private String numeroDecretoSospensioneRevoca;
	private Timestamp dataProvvedimentoSospensioneRevoca;
	/**
	 * CAMPI PER IMPIANTI ABUSIVI
	 */

	private String nome 					;
	private String cognome 				;
	private String note 					;
	private Vector comuni=new Vector()	;
	private java.util.Date prossimoControllo= null;
	private Timestamp dataRifiuto ;
	private int idMotivazioneRifiuto ;
	private String tipoProvvedimento ;
	private HistoryClassificazioneList listaDecreti = new HistoryClassificazioneList();
	private int oldClasse ;
	ConcessioniList listaConcessionari = new ConcessioniList();

	public HashMap<Integer, String> tipoMolluschiInZone = new HashMap<Integer, String>();
	public HashMap<Integer, String> tipoMotiviInZone = new HashMap<Integer, String>();

	private boolean tagliaNonCommerciale = false;
	private Timestamp dataTagliaNonCommerciale = null;
	
	private int statoClassificazione = -1;
	private Timestamp dataRevoca = null;
	private Timestamp dataSospensione = null;
	
	private int idMasterListSuap = -1;
	private String descrizioneMasterListSuap = "";
	
	public int getStatoClassificazione() {
		return statoClassificazione;
	}




	public void setStatoClassificazione(int statoClassificazione) {
		this.statoClassificazione = statoClassificazione;
	}




	public Timestamp getDataRevoca() {
		return dataRevoca;
	}




	public void setDataRevoca(Timestamp dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	public void setDataRevoca(String dataRevoca) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataRevoca!=null && ! "".equals(dataRevoca))
			this.dataRevoca = new Timestamp(sdf.parse(dataRevoca).getTime());
	}


	public Timestamp getDataSospensione() {
		return dataSospensione;
	}




	public void setDataSospensione(Timestamp dataSospensione) {
		this.dataSospensione = dataSospensione;
	}

	public void setDataSospensione(String dataSospensione) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataSospensione!=null && ! "".equals(dataSospensione))
			this.dataSospensione = new Timestamp(sdf.parse(dataSospensione).getTime());
	}


	public int getProvvedimentiRestrittivi() {
		return provvedimentiRestrittivi;
	}




	public void setProvvedimentiRestrittivi(int provvedimentiRestrittivi) {
		this.provvedimentiRestrittivi = provvedimentiRestrittivi;
	}




	public int getOldClasse() {
		return oldClasse;
	}




	public void setOldClasse(int oldClasse) {
		this.oldClasse = oldClasse;
	}




	public HistoryClassificazioneList getListaDecreti() {
		return listaDecreti;
	}




	public void setListaDecreti(HistoryClassificazioneList listaDecreti) {
		this.listaDecreti = listaDecreti;
	}




	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}




	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}




	public Timestamp getDataRifiuto() {
		return dataRifiuto;
	}




	public void setDataRifiuto(Timestamp dataRifiuto) {
		this.dataRifiuto = dataRifiuto;
	}




	public int getIdMotivazioneRifiuto() {
		return idMotivazioneRifiuto;
	}




	public void setIdMotivazioneRifiuto(int idMotivazioneRifiuto) {
		this.idMotivazioneRifiuto = idMotivazioneRifiuto;
	}




	public int getTipoMolluschiOld() {
		return tipoMolluschiOld;
	}




	public void setTipoMolluschiOld(int tipoMolluschiOld) {
		this.tipoMolluschiOld = tipoMolluschiOld;
	}




	public String getStato() {
		setStato();
		return stato;
	}




	public String getCun() {
		return cun;
	}




	public void setCun(String cun) {
		this.cun = cun;
	}




	/**
	 * valori possibili : 
	 * CLASSIFICATO
	 * IN SCADENZA : SE LA DATA FINE CLASSIFICAZIONE e' NEI PROSSIMI 3 MESI
	 * SCADUTA SE LA DATA FINE CLASSIFICAZIONE e' TRASCORSA
	 * DA CLASSIFICARE SE NON E STATA MAI CLASSIFICATA
	 * @param stato
	 */
	public void setStato() {
		if(tipoMolluschi==4)
		{
			stato = "NON CLASSIFICATO";
		}
		else
			if (dataFineClassificazione!=null)
			{
				Timestamp current = new Timestamp(System.currentTimeMillis());
				if(dataFineClassificazione.before(current))
				{
					stato = "SCADUTO" ;
				}
				else
				{
					current.setMonth(current.getMonth()+3);
					if (current.after(dataFineClassificazione))
					{
						stato = "IN SCADENZA" ;
					}
					else
					{
						
							stato = "CLASSIFICATO" ;
					}

				}

			}
			else
			{
				if(provvedimentiRestrittivi == 5 || provvedimentiRestrittivi == 7 || provvedimentiRestrittivi == 6 || provvedimentiRestrittivi == 8 )
					stato = "CLASSIFICATO" ;
				else
					if(provvedimentiRestrittivi ==13 )
						stato = "PRATICA RIFIUTATA" ;
					else
					this.stato = "DA CLASSIFICARE";
			}

	}


	public java.util.Date getProssimoControllo() {
		return prossimoControllo;
	}


	public HashMap<Integer, String> getTipoMolluschiInZone() {
		return tipoMolluschiInZone;
	}


	public void setTipoMolluschiInZone(HashMap<Integer, String> tipoMolluschiInZone) {
		this.tipoMolluschiInZone = tipoMolluschiInZone;
	}

	public HashMap<Integer, String> getTipoMotiviInZone() {
		return tipoMotiviInZone;
	}


	public void setTipoMotiviInZone(HashMap<Integer, String> tipoMotiviInZone) {
		this.tipoMotiviInZone = tipoMotiviInZone;
	}

	public void setProssimoControllo(java.util.Date prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}


	public int getCategoriaRischio() {
		return categoriaRischio;
	}


	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}


	public Timestamp getTrashedDate() {
		return trashedDate;
	}


	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}


	public int getAccountSize() {
		return accountSize;
	}


	public void setAccountSize(int accountSize) {
		this.accountSize = accountSize;
	}

	public void setAccountSize(String tmp) {
		this.accountSize = Integer.parseInt(tmp);
	}

	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}


	public ConcessioniList getListaConcessionari() {
		return listaConcessionari;
	}
	public boolean isTrashed() {
		return (trashedDate != null);
	}


	public void setListaConcessionari(ConcessioniList listaConcessionari) {
		this.listaConcessionari = listaConcessionari;
	}


	public Timestamp getDataFineClassificazione() {
		return dataFineClassificazione;
	}


	public void setDataFineClassificazione(Timestamp dataFineClassificazione) {
		this.dataFineClassificazione = dataFineClassificazione;
	}

	public void setDataFineClassificazione(String dataFineClassificazione) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataFineClassificazione!=null && ! "".equals(dataFineClassificazione))
			this.dataFineClassificazione = new Timestamp(sdf.parse(dataFineClassificazione).getTime());
	}


	public Timestamp getDataProvvedimento() {
		return dataProvvedimento;
	}


	public void setDataProvvedimentoSospensione(Timestamp dataProvvedimento) {
		this.dataProvvedimentoSospensione = dataProvvedimento;
	}

	public void setDataProvvedimentoSospensione(String dataProvvedimento) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataProvvedimento!=null && ! "".equals(dataProvvedimento))
			this.dataProvvedimentoSospensione = new Timestamp(sdf.parse(dataProvvedimento).getTime());
	}


	public Timestamp getDataProvvedimentoSospensione() {
		return dataProvvedimentoSospensione;
	}


	public void setDataProvvedimento(Timestamp dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}

	public void setDataProvvedimento(String dataProvvedimento) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataProvvedimento!=null && ! "".equals(dataProvvedimento))
			this.dataProvvedimento = new Timestamp(sdf.parse(dataProvvedimento).getTime());
	}


	public String getProvvedimento() {
		return provvedimento;
	}


	public void setProvvedimento(String provvedimento) {
		this.provvedimento = provvedimento;
	}


	public String getNumClassificazione() {
		return numClassificazione;
	}


	public void setNumClassificazione(String numClassificazione) {
		this.numClassificazione = numClassificazione;
	}


	public String getIpEntered() {
		return ipEntered;
	}


	public void setIpEntered(String ipEntered) {
		this.ipEntered = ipEntered;
	}


	public String getIpModified() {
		return ipModified;
	}


	public void setIpModified(String ipModified) {
		this.ipModified = ipModified;
	}


	public OrganizationAddressList getAddressList() {
		return addressList;
	}


	public void setAddressList(OrganizationAddressList addressList) {
		this.addressList = addressList;
	}


	public static Logger getLog() {
		return log;
	}


	public static void setLog(Logger log) {
		Organization.log = log;
	}


	public int getId() {
		return id;
	}


	public void setId(int orgId) {
		this.id = orgId;
	}

	public void setId(String orgId) {
		this.id = Integer.parseInt(orgId);
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSiteId() {
		return siteId;
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = Integer.parseInt(siteId);
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}


	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}


	public java.sql.Timestamp getModified() {
		return modified;
	}


	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}


	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public int getTipologia() {
		return tipologia;
	}


	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}


	public int getTipoMolluschi() {
		return tipoMolluschi;
	}


	public void setTipoMolluschi(int tipoMolluschi) {
		this.tipoMolluschi = tipoMolluschi;
	}

	public void setTipoMolluschi(String tipoMolluschi) {
		this.tipoMolluschi =Integer.parseInt( tipoMolluschi );
	}

	public int getClasse() {
		return classe;
	}


	public void setClasse(int classe) {
		this.classe = classe;
	}

	public void setClasse(String classe) {
		this.classe = Integer.parseInt(classe);
	}


	public Timestamp getDataClassificazione() {
		return dataClassificazione;
	}


	public void setDataClassificazione(Timestamp dataClassificazione) {
		this.dataClassificazione = dataClassificazione;
	}
	public void setDataClassificazione(String dataClassificazione) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataClassificazione!=null && ! "".equals(dataClassificazione))
			this.dataClassificazione = new Timestamp(sdf.parse(dataClassificazione).getTime());
	}



	public boolean isSottopostaDeclassamento() {
		return sottopostaDeclassamento;
	}


	public void setSottopostaDeclassamento(boolean sottopostaDeclassamento) {
		this.sottopostaDeclassamento = sottopostaDeclassamento;
	}


	public int getDeclassataIn() {
		return declassataIn;
	}


	public void setDeclassataIn(int declassataIn) {
		this.declassataIn = declassataIn;
	}


	public String getDecretoClassificazione() {
		return decretoClassificazione;
	}


	public void setDecretoClassificazione(String decretoClassificazione) {
		this.decretoClassificazione = decretoClassificazione;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Vector getComuni() {
		return comuni;
	}


	public void setComuni(Vector comuni) {
		this.comuni = comuni;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Organization() { }


	public Organization(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	public Organization(Connection db, int org_id) throws SQLException 
	{
		if (org_id == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.*" +
						"FROM organization o " +
				"WHERE o.org_id = ? " );
		pst.setInt(1, org_id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		addressList.setOrgId(this.getId());
		addressList.buildList(db);
		if(this.tipoMolluschi==2)
		{
			listaConcessionari.setIdZona(this.id);
			listaConcessionari.buildList(db);

		}

		pst = db.prepareStatement("select * from tipo_molluschi where id_molluschi = ? ");
		pst.setInt(1, this.id);
		rs = pst.executeQuery();
		while (rs.next())
		{
			tipoMolluschiInZone.put(rs.getInt("id_matrice"), rs.getString("cammino"));
		}
		
//		pst = db.prepareStatement("select m.motivo_id, lca.description as motivo_nome from molluschi_motivi m left join lookup_classi_acque lca on lca.code = m.motivo_id where m.org_id = ? and m.enabled");
//		pst.setInt(1, this.id);
//		rs = pst.executeQuery();
//		while (rs.next())
//		{
//			tipoMotiviInZone.put(rs.getInt("motivo_id"), rs.getString("motivo_nome"));
//		}
		setTipoMotivi(db);
		setDescrizioneMasterListSuap(getDescrizioneLineaAttivita(db, idMasterListSuap));
	}

	public Organization(Connection db, int org_id,boolean flag) throws SQLException 
	{
		if (org_id == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.*" +
						"FROM organization o " +
				"WHERE o.org_id = ? " );
		pst.setInt(1, org_id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		addressList.setOrgId(this.getId());
		addressList.buildList(db);

	}



	public boolean insert(Connection db,ActionContext  context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try
		{

			id =  DatabaseUtils.getNextSeq(db,context, "organization","org_id");


			String insert = "INSERT INTO ORGANIZATION (org_id,name,site_id,enteredby,modifiedby,entered," +
					"modified,ip_entered,ip_modified,namelast,namefirst,notes,classificazione," +
					"data_classificazione,tipologia_acque,data_fine_classificazione, stato_classificazione, " +
					"date2,note1,numaut,tipologia,cun, id_master_list_suap) " +
					" values (?,?,?,?,?,current_date,current_date,?,?,?,?,?,?,?,?,?, ?,?,?,?,201,?, ?)" ;
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setInt(++i,id);
			pst.setString(++i,name);
			pst.setInt(++i,siteId);
			pst.setInt(++i,enteredBy);
			pst.setInt(++i,modifiedBy);
			pst.setString(++i,ipEntered);
			pst.setString(++i,ipModified);
			pst.setString(++i,nome);
			pst.setString(++i,cognome);
			pst.setString(++i,note);
			pst.setInt(++i,classe);
			pst.setTimestamp(++i,dataClassificazione);
			pst.setInt(++i, tipoMolluschi);
			pst.setTimestamp(++i,dataFineClassificazione); 	
			pst.setInt(++i,statoClassificazione);
			pst.setTimestamp(++i,dataProvvedimento); 	
			pst.setString(++i,provvedimento);
			pst.setString(++i,numClassificazione);
			pst.setString(++i, cun);
			pst.setInt(++i,idMasterListSuap);

			pst.execute();
			Iterator iaddress = addressList.iterator();
			while (iaddress.hasNext()) 
			{
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) 
				{
					thisAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy(),context);
				}
			}
			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();
			}


			if(doCommit)
				db.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			if (doCommit) 
			{
				db.rollback();
			}
		}
		finally
		{

		}

		return true ;
	}


	public int update(Connection db,ActionContext context) throws SQLException {
		int i = -1;
		boolean doCommit = false;
		try 
		{
			if (doCommit = db.getAutoCommit()) 
			{
				db.setAutoCommit(false);
			}
			
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) 
			{
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				if((thisAddress.getCity()!=null)) 
				{
					thisAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy(),context);
				}
			}
			this.update(db,true);
//			if (tipoMolluschiInZone.size()>0)
//			{
//			PreparedStatement pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
//			pst.setInt(1, this.id);
//			pst.execute();
//
//			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
//			pst.setInt(1, id);
//			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
//			while (itTipoMoll.hasNext())
//			{
//				int kiave = itTipoMoll.next();
//				String cammino = tipoMolluschiInZone.get(kiave);
//				pst.setInt(2, kiave);
//				pst.setString(3,cammino);
//				pst.execute();
//			}
//			}
			if (doCommit) 
			{
				db.commit();
			}
		} 
		catch (SQLException e) 
		{
			
			if (doCommit) 
			{
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} 
		finally 
		{
			if (doCommit) 
			{
				db.setAutoCommit(true);
			}
		}
		return i;
	}




	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @param  override       Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */
	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		int i = 0 ;
		StringBuffer sql = new StringBuffer();
		sql.append(	"UPDATE organization " +
				"SET modifiedby=?,modified =? "); 
		
		if(classe>0)
			sql.append(",classificazione = ? " );
		if (name!=null)
			sql.append(", name = ? ");
		if (dataClassificazione!=null)
			sql.append(", data_classificazione = ? ");
		if (cognome!=null)
			sql.append(", namelast = ? ");
		if (nome!=null)
			sql.append(", namefirst = ? ");
		if (note!=null)
			sql.append(", notes = ? ");
		if (dataFineClassificazione!=null)
			sql.append(", data_fine_classificazione = ? ");
		if (dataProvvedimento!=null)
			sql.append(", date2 = ? ");
		if (provvedimento!=null)
			sql.append(", note1 = ? ");
		if (numClassificazione!=null)
			sql.append(", numaut = ? ");

		sql.append(" where org_id = ?" );


		pst = db.prepareStatement(sql.toString());

		pst.setInt(++i, modifiedBy) 	;
		pst.setTimestamp(++i, modified) ;
		if(classe>0)
			pst.setInt(++i, classe);		
		
		if (name!=null)
			pst.setString(++i, name) 					;
		if (dataClassificazione!=null)
			pst.setTimestamp(++i, dataClassificazione) 	;
		if (cognome!=null)
			pst.setString(++i, cognome) 	;
		if (nome!=null)
			pst.setString(++i, nome) 		;
		if (note!=null)
			pst.setString(++i, note) 		;
		if (dataFineClassificazione!=null)
			pst.setTimestamp(++i, dataFineClassificazione) ;
		if (dataProvvedimento!=null)
			pst.setTimestamp(++i, dataProvvedimento) ;
		if (provvedimento!=null)
			pst.setString(++i, provvedimento) 	;
		if (numClassificazione!=null)
			pst.setString(++i, numClassificazione) 	;

		pst.setInt(++i, id)			;
		pst.execute();
		return resultCount;
	}

	public int updateClassificazione(Connection db, boolean override,ActionContext context)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		try
		{
			db.setAutoCommit(false);
			int i = 0 ;
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);
			StringBuffer sql = new StringBuffer();
			/*CLASSIFICAZIONE IN ORGANIZATION*/
			
				sql.append(	"UPDATE organization " +
						"SET  classificazione = ?,data_classificazione =?,modifiedby=?,modified =?, " +
						"data_fine_classificazione = ?," +
						"date2 = ?, note1 = ?,numaut = ?,tipologia_acque=? where org_id = ?" );

				pst = db.prepareStatement(sql.toString());
				pst.setInt(++i, classe) 					;
				pst.setTimestamp(++i, dataClassificazione) 	;
				pst.setInt(++i, modifiedBy) 	;
				pst.setTimestamp(++i, modified) ;
				if (provvedimentiRestrittivi !=5 && provvedimentiRestrittivi != 6 && provvedimentiRestrittivi !=8)
					pst.setTimestamp(++i, dataFineClassificazione);
				else
					pst.setTimestamp(++i, null);
				pst.setTimestamp(++i, dataFineClassificazione) ;


//				pst.setTimestamp(++i, dataProvvedimento) ;

				pst.setString(++i, provvedimento) 	;
				pst.setString(++i, numClassificazione) 	;
				pst.setInt(++i, tipoMolluschi);
				pst.setInt(++i, id)			;
				pst.execute();
			
			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");



			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby) values (?,?,?,?,?,?,?,?,current_date,?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);


			if (provvedimentiRestrittivi !=5 && provvedimentiRestrittivi != 6 && provvedimentiRestrittivi !=8)
				pst.setTimestamp(3, dataFineClassificazione);
			else
				pst.setTimestamp(3, null);

			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, classe);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return resultCount;
	}

	public int riattivazione(Connection db, boolean override,ActionContext context)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		try
		{
			db.setAutoCommit(false);
			int i = 0 ;

			if(dataClassificazione != null && !dataClassificazione.equals("")){
				dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
				dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);	
			}
			StringBuffer sql = new StringBuffer();
		

			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id_zona");

			String insert = "update  decreto_classificazione_molluschi set data_riattivazione = ? where id = ?";
			pst = db.prepareStatement(insert);
			pst.setTimestamp(1, dataProvvedimento);
			pst.setInt(2, idUltimoProvvedimento);

			pst.execute() ;

			
			
			 sql = new StringBuffer();
				/*CLASSIFICAZIONE IN ORGANIZATION*/
				sql.append(	"UPDATE organization " +
						"SET  modifiedby=?,modified =?,id_provvedimento_decreto_classificazione=?,provvedimenti_restrittivi =0 " +
						" where org_id = ?" );
			
			pst = db.prepareStatement(sql.toString());
//			pst.setInt(++i, idUltimoProvvedimento);
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setInt(++i, idHistory)			;

			pst.setInt(++i, id)			;
			pst.execute();

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}






		return resultCount;
	}

	public int updateClassificazioneProvvedimento(Connection db, boolean override,ActionContext context)  {
		int resultCount = 0;
		PreparedStatement pst = null;
	
		
		try
		{
			db.setAutoCommit(false);
			int i = 0 ;

			if(dataClassificazione != null && !dataClassificazione.equals("")){
				dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
				dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);	
			}
			StringBuffer sql = new StringBuffer();
		

			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");

			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby,tipo_provvedimento,old_classe_zona) values (?,?,?,?,?,?,?,?,current_date,?,'provvedimento',?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			pst.setTimestamp(3, dataFineClassificazione);





			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, provvedimentiRestrittivi);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);
			pst.setInt(10, oldClasse);
		

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}
			
			
			 sql = new StringBuffer();
				/*CLASSIFICAZIONE IN ORGANIZATION*/
				sql.append(	"UPDATE organization " +
						"SET  modifiedby=?,modified =?,id_provvedimento_decreto_classificazione=?,provvedimenti_restrittivi = ?"
//						+ ", date2 = ? " +
			+			" where org_id = ?" );
			
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setInt(++i, idHistory)			;
			pst.setInt(++i, provvedimentiRestrittivi)			;
//			pst.setTimestamp(++i, dataProvvedimento)	;

			pst.setInt(++i, id)			;
			pst.execute();

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}






		return resultCount;
	}

	
	public int updateGestioneSanitaria(Connection db, boolean override,ActionContext context, ArrayList<Integer> motiviDaAggiungere)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		String motivi = "";
		for (int i =0; i<motiviDaAggiungere.size();i++)
				motivi+=motiviDaAggiungere.get(i)+",";	
		
		
		try
		{
			db.setAutoCommit(false);
			int i = 0 ;

			if(dataClassificazione != null && !dataClassificazione.equals("")){
				dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
				dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);	
			}
			StringBuffer sql = new StringBuffer();
		

			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");

			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby,tipo_provvedimento,old_classe_zona, motivi) values (?,?,?,?,?,?,?,?,current_date,?,'provvedimento',?, ?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			pst.setTimestamp(3, dataFineClassificazione);





			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, provvedimentiRestrittivi);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);
			pst.setInt(10, oldClasse);
			pst.setString(11, motivi );

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}
			
			
			 sql = new StringBuffer();
				/*CLASSIFICAZIONE IN ORGANIZATION*/
				sql.append(	"UPDATE organization " +
						"SET  modifiedby=?,modified =?,id_provvedimento_decreto_classificazione=?,provvedimenti_restrittivi = ?"
						+ ", date2 = ? " 
			+			" where org_id = ?" );
			
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setInt(++i, idHistory)			;
			pst.setInt(++i, provvedimentiRestrittivi)			;
			pst.setTimestamp(++i, dataProvvedimento)	;

			pst.setInt(++i, id)			;
			pst.execute();

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}






		return resultCount;
	}

	
	public int updateGestioneClassificazione(Connection db, boolean override,ActionContext context, int addressId)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		try
		{
			db.setAutoCommit(false);
			int i = 0 ;

			if(dataClassificazione != null && !dataClassificazione.equals("")){
				dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
				dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);	
			}
			StringBuffer sql = new StringBuffer();
		

			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");

			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby,tipo_provvedimento,old_classe_zona) values (?,?,?,?,?,?,?,?,current_date,?,'provvedimento',?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			pst.setTimestamp(3, dataFineClassificazione);


			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, provvedimentiRestrittivi);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);
			pst.setInt(10, oldClasse);

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}
			
			gestioneCoordinate (addressId, db, context);
			
				
			 sql = new StringBuffer();
				/*CLASSIFICAZIONE IN ORGANIZATION*/
				sql.append(	"UPDATE organization " +
						"SET  modifiedby=?,modified =?,id_provvedimento_decreto_classificazione=?,provvedimenti_restrittivi = ?, stato_classificazione = ?, data_sospensione = ?, data_revoca = ? "
//						+ ", date2 = ? " +
			+			" where org_id = ?" );
			
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setInt(++i, idHistory)			;
			pst.setInt(++i, provvedimentiRestrittivi)			;
			pst.setInt(++i, statoClassificazione)			;
			pst.setTimestamp(++i, dataSospensione)			;
			pst.setTimestamp(++i, dataRevoca)			;
//			pst.setTimestamp(++i, dataProvvedimento)	;

			pst.setInt(++i, id)			;
			pst.execute();

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}






		return resultCount;
	}

	public int sospendiConcessione(Connection db, boolean override,ActionContext context)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		try
		{
			db.setAutoCommit(false);
			int i = 0 ;
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);
			StringBuffer sql = new StringBuffer();
			/*CLASSIFICAZIONE IN ORGANIZATION*/
			sql.append(	"UPDATE organization " +
					"SET  classificazione = ?,data_classificazione =?,modifiedby=?,modified =?, " +
					"data_fine_classificazione = ?," +
					"date2 = ?,note1 = ?,numaut = ?,tipologia_acque=? where org_id = ?" );
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, classe) 					;
			pst.setTimestamp(++i, dataClassificazione) 	;
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setTimestamp(++i, dataFineClassificazione) ;
			pst.setTimestamp(++i, dataProvvedimento) ;
			pst.setString(++i, provvedimento) 	;
			pst.setString(++i, numClassificazione) 	;
			pst.setInt(++i, tipoMolluschi);
			pst.setInt(++i, id)			;
			pst.execute();

			/*CLASSIFICAZIONE IN HISTORY*/


			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id_zona");

			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,classe,tipo_zona_produzione,id_zona,id,entered,enteredby) values (?,?,?,?,?,?,?,current_date,?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);
			pst.setTimestamp(3, dataFineClassificazione);
			pst.setInt(4, classe);
			pst.setInt(5, tipoMolluschi) ;
			pst.setInt(6, this.id);
			pst.setInt(7, idHistory);
			pst.setInt(8, enteredBy);

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}






		return resultCount;
	}



	

	public int getIdUltimoProvvedimento() {
		return idUltimoProvvedimento;
	}




	public void setIdUltimoProvvedimento(int idUltimoProvvedimento) {
		this.idUltimoProvvedimento = idUltimoProvvedimento;
	}




	protected void buildRecord(ResultSet rs) throws SQLException {
		
		idUltimoProvvedimento = rs.getInt("id_provvedimento_decreto_classificazione");

		id 		= 	rs.getInt("org_id");
		orgId = id ;
		name			=	rs.getString("name");
		siteId		=	rs.getInt("site_id");
		nome 			=	rs.getString("namefirst");
		cognome		=	rs.getString("namelast");
		note 			=	rs.getString("notes");
		modified		=	rs.getTimestamp("modified");
		modifiedBy	=	rs.getInt("modifiedby");
		entered 		= 	rs.getTimestamp("entered");
		enteredBy		=	rs.getInt("enteredby");
		classe 		=	rs.getInt("classificazione");
		tipologia 	= 	rs.getInt("tipologia");
		dataClassificazione	= 	rs.getTimestamp("data_classificazione");
		tipoMolluschi			= 	rs.getInt("tipologia_acque");
		ipEntered			 	= 	rs.getString("ip_entered");
		ipModified 			= 	rs.getString("ip_modified");
		cun = rs.getString("cun");
		dataProvvedimento	= 	rs.getTimestamp("date2");
		provvedimento = rs.getString("note1");
		numClassificazione = rs.getString("numaut");
		dataFineClassificazione = rs.getTimestamp("data_fine_classificazione");
		prossimoControllo = rs.getTimestamp("prossimo_controllo");
		categoriaRischio=rs.getInt("categoria_rischio");
		dataRifiuto = rs.getTimestamp("molluschi_data_rifiuto");
		idMotivazioneRifiuto= rs.getInt("molluschi_motivazione_rifiuto");
		provvedimentiRestrittivi = rs.getInt("provvedimenti_restrittivi");
		tagliaNonCommerciale = rs.getBoolean("taglia_non_commerciale");
		dataTagliaNonCommerciale = rs.getTimestamp("taglia_non_commerciale_data_modifica");
		statoClassificazione = rs.getInt("stato_classificazione");
		dataRevoca = rs.getTimestamp("data_revoca");
		dataSospensione = rs.getTimestamp("data_sospensione");
		
		numeroDecretoSospensioneRevoca = rs.getString("numero_decreto_sospensione_revoca");
		dataProvvedimentoSospensioneRevoca = rs.getTimestamp("data_provvedimento_sospensione_revoca");

		idMasterListSuap = rs.getInt("id_master_list_suap");

	}

	public void setRequestItems(ActionContext context) {
		addressList = new OrganizationAddressList(context.getRequest());

	}	  
	public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int organizationSiteId = -1;
		String sqlSelect =
				"SELECT site_id " +
						"FROM organization " +
						"WHERE org_id = ? ";
		int i = 0;
		pst = db.prepareStatement(sqlSelect);
		pst.setInt(++i, tmpOrgId);
		rs = pst.executeQuery();
		if (rs.next()) {
			organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
		}
		rs.close();
		pst.close();
		return organizationSiteId;
	}

	public void setComuni (Connection db, int codeUser) throws SQLException 
	{
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		if(codeUser>0)
			sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ") order by comune ");
		else
			sql.append("select comune from comuni order by comune ");
		st = db.createStatement();
		rs = st.executeQuery(sql.toString());

		while (rs.next()) {
			comuni.add(rs.getString("comune"));
		}
		rs.close();
		st.close();

	}

	public void updateDeclassamento(Connection db, String esito , int classe , Timestamp dataDeclassamento,int orgId)
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("update organization set classificazione = "+classe + 
					", data_fine_classificazione = '"+dataDeclassamento+"' where org_id = "+orgId);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}

	//R.M...manca la condizione
	public int checkCodiceSIN(Connection db, String code) throws SQLException{

		int count = 0;
		String select = "select count(*) as conta from molluschi_sin where enabled and  = ? ";
		PreparedStatement ps = db.prepareStatement(select);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			count = rs.getInt("conta");
		}

		return count;

	}

	public void  gestisciMotiviSospensione(Connection db, ArrayList<Integer> motiviDaAggiungere, ArrayList<Integer> motiviDaCancellare, int userId) throws SQLException{
		if (motiviDaAggiungere.size()>0){
			for (int i=0; i<motiviDaAggiungere.size(); i++){
				int motivo = motiviDaAggiungere.get(i);
				String insert = "insert into molluschi_motivi (motivo_id, org_id, enteredby) values (?,?,?);";
				PreparedStatement pst = db.prepareStatement(insert);
				pst.setInt(1, motivo);
				pst.setInt(2, id);
				pst.setInt(3, userId);
				pst.execute() ;
			}
		}
		if (motiviDaCancellare.size()>0){
			for (int i=0; i<motiviDaCancellare.size(); i++){
				int motivo = motiviDaCancellare.get(i);
				String update = "update molluschi_motivi set enabled = false, modifiedby = ?, modified = now() where motivo_id = ? and org_id = ? and enabled;";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, userId);
				pst.setInt(2, motivo);
				pst.setInt(3, id);
				pst.execute() ;
			}
		}
	}
	
	public void  svuotaMotivi(Connection db, int userId) throws SQLException{
				String update = "update molluschi_motivi set enabled = false, modifiedby = ?, modified = now() where org_id = ? and enabled;";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, userId);
				pst.setInt(2, id);
				pst.execute() ;
			}




	public boolean isTagliaNonCommerciale() {
		return tagliaNonCommerciale;
	}




	public void setTagliaNonCommerciale(boolean tagliaNonCommerciale) {
		this.tagliaNonCommerciale = tagliaNonCommerciale;
	}




	public Timestamp getDataTagliaNonCommerciale() {
		return dataTagliaNonCommerciale;
	}




	public void setDataTagliaNonCommerciale(Timestamp dataTagliaNonCommerciale) {
		this.dataTagliaNonCommerciale = dataTagliaNonCommerciale;
	}
	
	public void setTipoMotivi(Connection db) throws SQLException{
		PreparedStatement pst = db.prepareStatement("select m.motivo_id, lca.description as motivo_nome from molluschi_motivi m left join lookup_classi_acque lca on lca.code = m.motivo_id where m.org_id = ? and m.enabled");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			tipoMotiviInZone.put(rs.getInt("motivo_id"), rs.getString("motivo_nome"));
		}
	}

	
	private void gestioneCoordinate(int addressId, Connection db, ActionContext context) throws SQLException{
		
		String[] alfabeto = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N", "O"};
	
		for (int k = 1; k<=10; k++){
			String id = context.getParameter("poligonoId"+k);
			//String addressId = context.getParameter("poligonoAddressId"+k);
			String lat = context.getParameter("poligonoLatitude"+k);
			String lon = context.getParameter("poligonoLongitude"+k);
			
			int idInt = -1;
			try { idInt = Integer.parseInt(id);	} catch (Exception e){};
			CoordinateMolluschiBivalvi co = new CoordinateMolluschiBivalvi(db, idInt);
			if (co.getAddressId()>0){
				co.setLatitude(lat);
				co.setLongitude(lon);
				co.update(db);
			}
			else{
				co.setLatitude(lat);
				co.setLongitude(lon);
				co.setTipologia(1);
				co.setAddressId(addressId);
				co.setIdentificativo(alfabeto[k]);
				co.store(db);
			}
			
		}
		
		
		for (int j = 1; j<=10; j++){
			String id = context.getParameter("prelievoId"+j);
			//String addressId = context.getParameter("prelievoAddressId"+j);
			String lat = context.getParameter("prelievoLatitude"+j);
			String lon = context.getParameter("prelievoLongitude"+j);
			
			int idInt = -1;
			try { idInt = Integer.parseInt(id);	} catch (Exception e){};
			CoordinateMolluschiBivalvi co = new CoordinateMolluschiBivalvi(db, idInt);
			if (co.getAddressId()>0){
				co.setLatitude(lat);
				co.setLongitude(lon);
				co.update(db);
			}
			else{
				co.setLatitude(lat);
				co.setLongitude(lon);
				co.setTipologia(2);
				co.setAddressId(addressId);
				co.setIdentificativo(alfabeto[j]);
				co.store(db);
			}
			
		}
		
		
		
		
	}
	
	
	public int updateNewRiclassificazione(Connection db, boolean override,ActionContext context, int addressId)  {
		int resultCount = 0;
		PreparedStatement pst = null;
	try
		{
			db.setAutoCommit(false);
			int i = 0 ;
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);
			StringBuffer sql = new StringBuffer();
			/*CLASSIFICAZIONE IN ORGANIZATION*/
			
				sql.append(	"UPDATE organization " +
						"SET  classificazione = ?,data_classificazione =?,modifiedby=?,modified =?, " +
						"data_fine_classificazione = ?, stato_classificazione = ? , " +
						//"date2 = ?, "
						 "note1 = ?,numaut = ?,tipologia_acque=?, cun = ? where org_id = ?" );

				pst = db.prepareStatement(sql.toString());
				pst.setInt(++i, classe) 					;
				pst.setTimestamp(++i, dataClassificazione) 	;
				pst.setInt(++i, modifiedBy) 	;
				pst.setTimestamp(++i, modified) ;
				if (provvedimentiRestrittivi !=5 && provvedimentiRestrittivi != 6 && provvedimentiRestrittivi !=8)
					pst.setTimestamp(++i, dataFineClassificazione);
				else
					pst.setTimestamp(++i, null);
				//pst.setTimestamp(++i, dataFineClassificazione) ;
				pst.setInt(++i, statoClassificazione) 	;

//				pst.setTimestamp(++i, dataProvvedimento) ;

				pst.setString(++i, provvedimento) 	;
				pst.setString(++i, numClassificazione) 	;
				pst.setInt(++i, tipoMolluschi);
				pst.setString(++i, cun);
				pst.setInt(++i, id)			;
				pst.execute();

				//coordinate
				gestioneCoordinate (addressId, db, context);

				
			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");



			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby) values (?,?,?,?,?,?,?,?,current_date,?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
			dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);


			if (provvedimentiRestrittivi !=5 && provvedimentiRestrittivi != 6 && provvedimentiRestrittivi !=8)
				pst.setTimestamp(3, dataFineClassificazione);
			else
				pst.setTimestamp(3, null);

			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, classe);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return resultCount;
	}

	
	public int updateNewRevocaSospensione(Connection db, boolean override,ActionContext context)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		try
		{
			db.setAutoCommit(false);
			int i = 0 ;

			if(dataClassificazione != null && !dataClassificazione.equals("")){
				dataFineClassificazione = new Timestamp(dataClassificazione.getTime());
				dataFineClassificazione.setYear(dataFineClassificazione.getYear()+3);	
			}
			StringBuffer sql = new StringBuffer();
		

			/*CLASSIFICAZIONE IN HISTORY*/

			int idHistory =  DatabaseUtils.getNextSeq(db,context, "decreto_classificazione_molluschi","id");

			String insert = "insert into decreto_classificazione_molluschi (num_decreto,data_classificazione,data_fine_classificazione,data_provvedimento,classe,tipo_zona_produzione,id_zona,id,entered,enteredby,tipo_provvedimento,old_classe_zona) values (?,?,?,?,?,?,?,?,current_date,?,'provvedimento',?)";
			pst = db.prepareStatement(insert);
			pst.setString(1, numClassificazione);
			pst.setTimestamp(2, dataClassificazione);
			pst.setTimestamp(3, dataFineClassificazione);


			pst.setTimestamp(4, dataProvvedimento);

			pst.setInt(5, provvedimentiRestrittivi);
			pst.setInt(6, tipoMolluschi) ;
			pst.setInt(7, this.id);
			pst.setInt(8, idHistory);
			pst.setInt(9, enteredBy);
			pst.setInt(10, oldClasse);

			pst.execute() ;

			/*CLASSIFICAZIONE IN HISTORY MOLLUSCHI E ORGANIZATION*/
			pst = db.prepareStatement("delete from tipo_molluschi where id_molluschi = ?");
			pst.setInt(1, this.id);
			pst.execute();

			String insMoll = "insert into tipo_molluschi_decreti_classificazione (id_decreto_classificazione_molluschi,id_matrice,cammino) values (?,?,?)";
			PreparedStatement pst2 = db.prepareStatement(insMoll);

			pst=db.prepareStatement("insert into tipo_molluschi (id_molluschi,id_matrice,cammino) values (?,?,?) ");
			pst.setInt(1, id);
			pst2.setInt(1, idHistory) ;
			Iterator<Integer> itTipoMoll =  tipoMolluschiInZone.keySet().iterator();
			while (itTipoMoll.hasNext())
			{
				int kiave = itTipoMoll.next();
				String cammino = tipoMolluschiInZone.get(kiave);
				pst.setInt(2, kiave);
				pst.setString(3,cammino);
				pst.execute();

				pst2.setInt(2, kiave);
				pst2.setString(3,cammino);
				pst2.execute() ;
			}
			
			 sql = new StringBuffer();
				/*CLASSIFICAZIONE IN ORGANIZATION*/
				sql.append(	"UPDATE organization " +
						"SET  modifiedby=?,modified =?,id_provvedimento_decreto_classificazione=?,provvedimenti_restrittivi = ?, stato_classificazione = ?, data_sospensione = ?, data_revoca = ? "
						+ ", numero_decreto_sospensione_revoca = ?, data_provvedimento_sospensione_revoca = ? " 
			+			" where org_id = ?" );
			
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, modifiedBy) 	;
			pst.setTimestamp(++i, modified) ;
			pst.setInt(++i, idHistory)			;
			pst.setInt(++i, provvedimentiRestrittivi)			;
			pst.setInt(++i, statoClassificazione)			;
			pst.setTimestamp(++i, dataSospensione)			;
			pst.setTimestamp(++i, dataRevoca)			;
			
			pst.setString(++i, numeroDecretoSospensioneRevoca)			;
			pst.setTimestamp(++i, dataProvvedimentoSospensioneRevoca)			;
//			pst.setTimestamp(++i, dataProvvedimento)	;

			pst.setInt(++i, id)			;
			pst.execute();

			db.commit();
			db.setAutoCommit(true);
		}
		catch(SQLException e)
		{

			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	return resultCount;
	}
	
	public int updateNomeLocalita(Connection db, String nome, int userId)  {
		int resultCount = 0;
		PreparedStatement pst = null;

		String note = "; Nome localita modificato da utente "+userId+" da "+name+" a "+nome;
		String sql = "update organization set name = ?, modified = now(), modifiedby = ?, note_hd = concat(note_hd, ?) where org_id = ?";
		try
		{
			
			pst = db.prepareStatement(sql);
			int i = 0;
			pst.setString(++i, nome);
			pst.setInt(++i, userId);
			pst.setString(++i, note);
			pst.setInt(++i, orgId);
			pst.executeUpdate();
			
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();

			}
	return resultCount;
	}




	public String getNumeroDecretoSospensioneRevoca() {
		return numeroDecretoSospensioneRevoca;
	}




	public void setNumeroDecretoSospensioneRevoca(String numeroDecretoSospensioneRevoca) {
		this.numeroDecretoSospensioneRevoca = numeroDecretoSospensioneRevoca;
	}




	public Timestamp getDataProvvedimentoSospensioneRevoca() {
		return dataProvvedimentoSospensioneRevoca;
	}




	public void setDataProvvedimentoSospensioneRevoca(Timestamp dataProvvedimentoSospensioneRevoca) {
		this.dataProvvedimentoSospensioneRevoca = dataProvvedimentoSospensioneRevoca;
	}
	public void setDataProvvedimentoSospensioneRevoca(String dataProvvedimentoSospensioneRevoca) {
		this.dataProvvedimentoSospensioneRevoca = DateUtils.parseDateStringNew(dataProvvedimentoSospensioneRevoca, "dd/MM/yyyy");
	}




	public int getIdMasterListSuap() {
		return idMasterListSuap;
	}




	public void setIdMasterListSuap(int idMasterListSuap) {
		this.idMasterListSuap = idMasterListSuap;
	}




	public String getDescrizioneMasterListSuap() {
		return descrizioneMasterListSuap;
	}




	public void setDescrizioneMasterListSuap(String descrizioneMasterListSuap) {
		this.descrizioneMasterListSuap = descrizioneMasterListSuap;
	}
	
	public String getDescrizioneLineaAttivita(Connection db, int idLinea) throws SQLException{
		String desc = "";
		PreparedStatement pst = db.prepareStatement("select path_descrizione from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita  = "+idLinea);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			desc = rs.getString("path_descrizione");
		return desc;
	}
	
}

