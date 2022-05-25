/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sequestri.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.daffodilwoods.daffodildb.server.sql99.dcl.sqlconnectionstatement.connectionname;
import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.defaultclause;
import com.darkhorseventures.framework.actions.ActionContext;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8279615793517067789L;
	private boolean sequestro=false;
	private boolean soa ;
	private int tipo_nc ;


	public int getTipo_nc() {
		return tipo_nc;
	}
	public void setTipo_nc(int tipo_nc) {
		this.tipo_nc = tipo_nc;
	}

	public boolean isSoa() {
		return soa;
	}
	public void setSoa(boolean soa) {
		this.soa = soa;
	}
	private boolean farmacosorveglanza = false ;

	public boolean isFarmacosorveglianza() {
		return farmacosorveglanza;
	}
	public void setFarmacosorveglianza(boolean farmacosorveglanza) {
		this.farmacosorveglanza = farmacosorveglanza;
	}
	public boolean isSequestro() {
		return sequestro;
	}
	public void setSequestro(boolean sequestro) {
		this.sequestro = sequestro;
	}
	private String descrizionEsito="";
	private String descrizione="";
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String valutazione="";
	
	public String getValutazione() {
		return valutazione;
	}
	public void setValutazione(String val) {
		this.valutazione = val;
	}
	
	public Ticket()
	{
	
	}
	private String identificativonc="";

	public String getIdentificativonc() {
		return identificativonc;
	}
	public void setIdentificativonc(String identificativonc) {
		this.identificativonc = identificativonc;
	}
	private int id_nonconformita=-1;
	public int getId_nonconformita() {
		return id_nonconformita;
	}
	public void setId_nonconformita(int id_nonconformita) {
		this.id_nonconformita = id_nonconformita;
	}
	
	public void setId_nonconformita(String id_nonconformita) {
		this.id_nonconformita = Integer.parseInt(id_nonconformita);
	}
	
	protected int sequestroDi=-1;
	
	protected int tipologiaSequestro = -1;
	
	public int getTipologiaSequestro() {
		return tipologiaSequestro;
	}
	public void setTipologiaSequestro(int tipologiaSequestro) {
		this.tipologiaSequestro = tipologiaSequestro;
	}
	public int getSequestroDi() {
		return sequestroDi;
	}
	public void setSequestroDi(int sequestroDi) {
		this.sequestroDi = sequestroDi;
	}

	protected int esitoSequestro=-1;
	public int getEsitoSequestro() {
		return esitoSequestro;
	}
	public int getCodiceArticolo() {
		return codiceArticolo;
	}
	public void setCodiceArticolo(int codiceArticolo) {
		this.codiceArticolo = codiceArticolo;
	}
	public void setEsitoSequestro(int esitoSequestro) {
		this.esitoSequestro = esitoSequestro;
	}
	
	public void setEsitoSequestro(String esitoSequestro) {
		if (esitoSequestro!= null && !esitoSequestro.equals(""))
		this.esitoSequestro = Integer.parseInt(esitoSequestro);
	}

	private String noteSequestrodi="";

	public String getNoteSequestrodi() {
		return noteSequestrodi;
	}
	public void setNoteSequestrodi(String noteSequestrodi) {
		this.noteSequestrodi = noteSequestrodi;
	}
	protected String sequestroDiDescrizione="";
	protected String notaSequestro="";
	protected HashMap<Integer, String> oggettiSequestrati=new HashMap<Integer, String>();
	protected HashMap<Integer, String> azioniNonConformi=new HashMap<Integer, String>();
	protected int codiceArticolo=-1;
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int provvedimenti = -1;
	protected int sequestriAmministrative = -1;
	protected int sequestriPenali = -1;
	protected int sequestri = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";

	//aggiunto da d.dauria per gestire i sequestri
	private boolean tipoSequestro = false;
	private boolean tipoSequestroDue = false;
	private boolean tipoSequestroTre = false;
	private boolean tipoSequestroQuattro = false; 
	private String testoAppoggio = "";
	private int punteggio = 0;
	private double quantita = 0;
	private String idControlloUfficiale = null;
	private String identificativo = null;

	public void setQuantita(String temp)
	{
		this.quantita = Double.parseDouble(temp);
	}
	public void setQuantita(double temp)
	{
		this.quantita = temp;
	}
	public double getQuantita() {
		return quantita;
	}
	public void setPunteggio(String temp)
	{
		this.punteggio = Integer.parseInt(temp);
	}
	public void setPunteggio(int temp)
	{
		this.punteggio = temp;
	}
	public int getPunteggio() {
		return punteggio;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}


	public boolean getTipoSequestro() {
		return tipoSequestro;
	}






	public void setTipoSequestro(boolean tipoSequestro) {
		this.tipoSequestro = tipoSequestro;
	}

	public void setTipoSequestro(String temp) {
		this.tipoSequestro = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroDue() {
		return tipoSequestroDue;
	}

	public void setTipoSequestroDue(boolean tipoSequestroDue) {
		this.tipoSequestroDue = tipoSequestroDue;
	}

	public void setTipoSequestroDue(String temp) {
		this.tipoSequestroDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroTre() {
		return tipoSequestroTre;
	}

	public void setTipoSequestroTre(boolean tipoSequestroTre) {
		this.tipoSequestroTre = tipoSequestroTre;
	}

	public void setTipoSequestroTre(String temp) {
		this.tipoSequestroTre = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroQuattro() {
		return tipoSequestroQuattro;
	}

	public void setTipoSequestroQuattro(boolean tipoSequestroQuattro) {
		this.tipoSequestroQuattro = tipoSequestroQuattro;
	}

	public void setTipoSequestroQuattro(String temp) {
		this.tipoSequestroQuattro = DatabaseUtils.parseBoolean(temp);
	}



	public String getPippo() {
		return pippo;
	}

	public void setPippo(String pippo) {
		this.pippo = pippo;

	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;

	}

	public int getProvvedimenti() {
		return provvedimenti;
	}

	public void setProvvedimenti(String provvedimenti) {
		try {
			this.provvedimenti = Integer.parseInt(provvedimenti);
		} catch (Exception e) {
			this.provvedimenti = -1;
		}
	}

	public void setSequestriAmministrative(String sequestriAmministrative) {
		try {
			this.sequestriAmministrative = Integer.parseInt(sequestriAmministrative);
		} catch (Exception e) {
			this.sequestriAmministrative = -1;
		}
	}

	public void setSequestriPenali(String sequestriPenali) {
		try {
			this.sequestriPenali = Integer.parseInt(sequestriPenali);
		} catch (Exception e) {
			this.sequestriPenali = -1;
		}
	}

	public void setSequestri(String sequestri) {
		try {
			this.sequestri = Integer.parseInt(sequestri);
		} catch (Exception e) {
			this.sequestri = -1;
		}
	}

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;

	}

	public int getSequestriAmministrative() {
		return sequestriAmministrative;
	}

	public void setSequestriAmministrative(int sequestriAmministrative) {
		this.sequestriAmministrative = sequestriAmministrative;
	}

	public int getSequestriPenali() {
		return sequestriPenali;
	}

	public void setSequestrPenali(int sequestriPenali) {
		this.sequestriPenali = sequestriPenali;
	}

	public int getSequestri() {
		return sequestri;
	}

	public void setSequestri(int sequestri) {
		this.sequestri = sequestri;
	}


	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	public Ticket(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public Ticket(Connection db, int id) throws SQLException {
		queryRecord(db, id);
	}

	public Ticket(Connection db, int id,boolean soa) throws SQLException {
		this.soa = soa;
		queryRecord(db, id);
	}

	public String getDescrizione1() {
		return descrizione1;
	}

	public void setDescrizione1(String descrizione1) {
		this.descrizione1 = descrizione1;
	}

	public String getDescrizione2() {
		return descrizione2;
	}

	public void setDescrizione2(String descrizione2) {
		this.descrizione2 = descrizione2;
	}
	public String getDescrizione3() {
		return descrizione3;
	}

	public void setDescrizione3(String descrizione3) {
		this.descrizione3 = descrizione3;
	}



	private void setDescrizioneSequestroDi(Connection db,int idseq ) throws SQLException{

		String sql="select description from lookup_sequestri_amministrative where code="+idseq;
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			sequestroDiDescrizione=rs.getString("description");

		}

	}


	private void setDescrizioneSequestroDiSoa(Connection db,int idseq ) throws SQLException{

		String sql="select description from lookup_sequestri_amministrative_soa where code="+idseq;
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			sequestroDiDescrizione=rs.getString("description");

		}

	}


	public void setDescrizioneEsito(Connection db,int code) throws SQLException{

		String sql="select description from lookup_esiti_sequestri where code="+code;
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			descrizionEsito=rs.getString("description");

		}

	}


	public String getDescrizionEsito() {
		return descrizionEsito;
	}
	public void setDescrizionEsito(String descrizionEsito) {
		this.descrizionEsito = descrizionEsito;
	}


	
	private int idTicketNonConformita=-1;


	public int getIdTicketNonConformita() {
		return idTicketNonConformita;
	}

	private int idControlloUfficialeTicket=-1;

	






	public int getIdControlloUfficialeTicket() {
		return idControlloUfficialeTicket;
	}

	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}

		String sql =   "SELECT t.*, cu.ticketid as id_cu,esiti.description as descrizione_esito,nonconformita.tipologia as tipologia_nonconformita, " +

		"o.site_id AS orgsiteid,o.tipologia as tipo_operatore,cu.chiusura_attesa_esito " +
		"FROM ticket t "
		+ "left join ticket nonconformita on nonconformita.ticketid=t.id_nonconformita " +
		"LEFT JOIN lookup_esiti_sequestri esiti ON (t.esito_sequestri = esiti.code) " ;
		sql +=   "LEFT JOIN organization o ON (t.org_id = o.org_id) " ;
		sql +="LEFT JOIN ticket cu ON (t.id_controllo_ufficiale = cu.id_controllo_ufficiale  and cu.tipologia = 3) " +
		
		"WHERE t.ticketid = ? AND t.tipologia = 9";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			//this.setDescrizioneEsito(db, esitoSequestro);
			//this.setIdTicketNConformita(db);
			//this.setIdControlloUfficialeTicket(db);
			super.controlloBloccoCu(db, Integer.parseInt(this.idControlloUfficiale));
		}
		
		
		//this.setOggettiSequestrati(db);
		if (soa == false)
		{
			setDescrizioneSequestroDi(db,sequestroDi);
		}
		else
		{
			setDescrizioneSequestroDiSoa(db,sequestroDi);
		}
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		
		
	}

	public String getSequestroDiDescrizione() {
		return sequestroDiDescrizione;
	}
	public void setSequestroDiDescrizione(String sequestroDiDescrizione) {
		this.sequestroDiDescrizione = sequestroDiDescrizione;
	}
	public HashMap<Integer, String> getOggettiSequestrati() {
		return oggettiSequestrati;
	}
	public void setOggettiSequestrati(HashMap<Integer, String> oggettiSequestrati) {
		this.oggettiSequestrati = oggettiSequestrati;
	}
	public HashMap<Integer, String> getAzioniNonConformi() {
		return azioniNonConformi;
	}
	public void setAzioniNonConformi(HashMap<Integer, String> azioniNonConformi) {
		this.azioniNonConformi = azioniNonConformi;
	}



	private String getQueryOggettiSequestrati(){

		String sql="";
		switch (sequestroDi){

		case 1:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_stabilimento.description from "+
			" tipisequestri,lookup_sequestri_amministrative_stabilimento where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_stabilimento.code and "+
			" tipisequestri.idticket=?";
			break;

		}

		case 2:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_attrezzature.description from "+
			" tipisequestri,lookup_sequestri_amministrative_attrezzature where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_attrezzature.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		case 3:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_locali.description from "+
			" tipisequestri,lookup_sequestri_amministrative_locali where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_locali.code and "+
			" tipisequestri.idticket=?";
			break;	
		}
		case 4:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_locali_attrezzature.description from "+
			" tipisequestri,lookup_sequestri_amministrative_locali_attrezzature where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_locali_attrezzature.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		case 5:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_animali.description from "+
			" tipisequestri,lookup_sequestri_amministrative_animali where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_animali.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		case 6:{

			sql="select  tipisequestri.*, lookup_sequestri_amministrative_alimentiorigineanimale.description from "+
			" tipisequestri,lookup_sequestri_amministrative_alimentiorigineanimale where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_alimentiorigineanimale.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		case 7:{
			sql="select  tipisequestri.*, lookup_sequestri_amministrative_alimentioriginevegetale.description from "+
			" tipisequestri,lookup_sequestri_amministrative_alimentioriginevegetale where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_alimentioriginevegetale.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		case 8:{

			sql="select  tipisequestri.*, lookup_sequestri_amministrative_vegetale_animale.description from "+
			" tipisequestri,lookup_sequestri_amministrative_vegetale_animale where tipisequestri.idtiposequestro=lookup_sequestri_amministrative_vegetale_animale.code and "+
			" tipisequestri.idticket=?";
			break;
		}
		default: {
			break;
		}


		}
		return sql;		

	}


	public void setOggettiSequestrati(Connection db)throws SQLException{



		PreparedStatement pst=db.prepareStatement(this.getQueryOggettiSequestrati());
		pst.setInt(1, id);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			int idOggesttoSequestrato=rst.getInt("idtiposequestro");
			String description=rst.getString("description");
			oggettiSequestrati.put(idOggesttoSequestrato, description);


		}

	}


	public void setAzioniNonConformi(Connection db)throws SQLException{

		String sql="select  oggettisequestrati_azioninonconformi.*, lookup_sequestri_penali.description from "+
		" oggettisequestrati_azioninonconformi,lookup_sequestri_penali where oggettisequestrati_azioninonconformi.azione_nonconforme=lookup_sequestri_penali.code and "+
		" oggettisequestrati_azioninonconformi.idticket=?";

		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			int azionenonConforme=rst.getInt("azione_nonconforme");
			String description=rst.getString("description");
			azioniNonConformi.put(azionenonConforme, description);


		}

	}


	public void insertOggettiSequestratiOAzionenonConforme(Connection db,String[] oggettiSequestrati) throws SQLException{


		String sql="Insert into tipisequestri (idticket,idtiposequestro) values(?,?)";
		PreparedStatement pst=db.prepareStatement(sql);

		if(oggettiSequestrati!=null){
			for(int i=0;i<oggettiSequestrati.length;i++){

				int oggettoKey=Integer.parseInt(oggettiSequestrati[i]);
				pst.setInt(1, id);
				pst.setInt(2, oggettoKey);
				pst.execute();
			}
		}









	}


	public void updateOggettiSequestratiOAzionenonConforme(Connection db,String[] oggettiSequestrati) throws SQLException{


		String sql="update  tipisequestri set idtiposequestro=? where idticket=?";
		String del="delete from tipisequestri where idticket=?";

		PreparedStatement pstDel=db.prepareStatement(del);
		pstDel.setInt(1, id);
		pstDel.execute();


		this.insertOggettiSequestratiOAzionenonConforme(db, oggettiSequestrati);



	}

   

	public synchronized boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {

				db.setAutoCommit(false);
			}
		
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			
			sql.append(
					"INSERT INTO ticket (contact_id, problem, pri_code, " +
					"department_code, cat_code, scode,  link_contract_id, " +
					"link_asset_id, expectation, product_id, customer_product_id, " +
					"key_count, status_id, trashed_date, user_group_id, cause_id, " +
					"resolution_id, defect_id, escalation_level, resolvable, " +
			"resolvedby, resolvedby_department_code, state_id, site_id ,ip_entered,ip_modified,flag_posticipato,flag_campione_non_conforme, ");
			
			

			if (tipo_nc != -1)
			{
				sql.append("tipo_nc,");
			}


			if (idControlloUfficiale != null)
			{
				sql.append("id_controllo_ufficiale,");
			}
			if (altId>0){sql.append("alt_id,");}
			if (farmacosorveglanza == false)
			{
				sql.append("org_id,id_stabilimento,id_apiario, ");
			}
			else
			{
				sql.append("id_farmacia,");
			}
			
				sql.append("ticketid, ");
			
			sql.append("notesequestridi ,");
			if (quantita >= 0) {
				sql.append("quantita, ");
			}
			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}
			if(esitoSequestro!=-1){
				sql.append("esito_sequestri, ");

			}

			if(!solution.equals("")){
				sql.append("solution, ");

			}

			if(descrizione!=null){
				sql.append("descrizioneesitosequestro, ");

			}

			if(valutazione != null){
				sql.append("nc_gravi_valutazione, ");

			}

			if(estimatedResolutionDate!=null){
				sql.append("est_resolution_date , ");
			}

			if(codiceArticolo!=-1){
				sql.append("codice_articolo , ");

			}

			if(tipologiaSequestro!= -1){
				sql.append("tipologiaSequestro , ");
			}

			if(sequestroDi!=-1){
				//sequestroNota
				sql.append("sequestrodi , ");
			}

			sql.append("sequestrotrasporti , ");

			sql.append("punteggio,");

			sql.append("identificativonc ,");


			sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
			"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali, sequestri, descrizione1, descrizione2, descrizione3  ");

			sql.append(" , tipo_sequestro");
			sql.append(" , tipo_sequestro_due");
			sql.append(" , tipo_sequestro_tre");
			sql.append(" , tipo_sequestro_quattro");
			sql.append(", id_nonconformita");

			sql.append(")");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?, ");
			sql.append("?, ?, ?, ");
			if (tipo_nc != -1)
			{
				sql.append("?,");
			}


			if (idControlloUfficiale != null)
			{
				sql.append("?,");
			}
			if (altId>0){sql.append("?,");}
			if (farmacosorveglanza == false)
			{
				sql.append("?,?,?, ");
			}
			else
			{
				sql.append("?,");
			}
			sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");


			sql.append("?,");
			if (quantita >= 0) {
				sql.append("?, ");
			}
			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}
			if(esitoSequestro!=-1){
				sql.append("?, ");
			}

			if(!solution.equals("")){
				sql.append("?, ");

			}

			if(descrizione!=null){
				sql.append("?, ");


			}
			
			if(valutazione !=null){
				sql.append("?, ");


			}
			
			if(estimatedResolutionDate!=null){
				sql.append("? , ");
			}

			if(codiceArticolo!=-1){
				sql.append("? , ");

			}

			if(tipologiaSequestro!= -1){
				sql.append("? , ");
			}
			
			if(sequestroDi!=-1){
				//sequestroNota
				sql.append("? , ");
			}
			sql.append("? , ");
			sql.append("? , ");
			sql.append("? , ");

			sql.append("?, ?, ?, ?, " +
			"9, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "); //ho aggiunto 2 punti interrogativi
			String asl = null;
			  if(siteId==201){
					asl = "AV";	
				}else if(siteId == 202){
					asl = "BN";
				}else if(siteId ==203){
					asl = "CE";
				}else if(siteId ==204){
					asl = "NA1";
				}else if(siteId == 205){
					asl = "NA2Nord";
				}else if(siteId == 206){
					asl = "NA3Sud";
				}else if(siteId ==207){
					asl = "SA";
				}
				else{
					if(siteId ==16){
						asl = "FuoriRegione";
					}
				
					
				}
			sql.append(", ?");
			// if (punteggio != -1) {

			//   }
			sql.append(") RETURNING ticketid ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);


	        pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, assetId);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, statusId);
			DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
	        pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, causeId);
			DatabaseUtils.setInt(pst, ++i, resolutionId);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

	        pst.setNull(++i, java.sql.Types.BOOLEAN);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			pst.setString(++i, super.getIpEntered());
		    pst.setString(++i, super.getIpModified());
		    
		    pst.setBoolean(++i, super.isFlag_posticipato());
		      pst.setBoolean(++i, super.isFlag_campione_non_conforme());
			if (tipo_nc != -1)
			{
				DatabaseUtils.setInt(pst, ++i, tipo_nc);
			}


			if (idControlloUfficiale != null)
			{
				pst.setString(++i, idControlloUfficiale);
			}

			if (altId>0){ DatabaseUtils.setInt(pst, ++i, altId);}
			if (farmacosorveglanza == false)
			{
				DatabaseUtils.setInt(pst, ++i, orgId);
				DatabaseUtils.setInt(pst, ++i, idStabilimento);
				DatabaseUtils.setInt(pst, ++i, idApiario);


			}
			else
			{
				DatabaseUtils.setInt(pst, ++i, orgId);
			}
			
			pst.setString(++i, noteSequestrodi);
			if (quantita >= 0) {
				pst.setDouble(++i, quantita);
			}
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			} 
			if (esitoSequestro!=-1) {
				pst.setInt(++i, esitoSequestro);
			} 

			if(!solution.equals("")){
				pst.setString(++i,solution);

			}

			if(descrizione!=null){
				pst.setString(++i,descrizione);


			}

			if(valutazione != null){
				pst.setString(++i,valutazione);


			}

			if(estimatedResolutionDate!=null){
				pst.setTimestamp(++i, estimatedResolutionDate);
			}


			if(codiceArticolo!= -1){

				pst.setInt(++i , codiceArticolo);

			}

			if(tipologiaSequestro!= -1){
				pst.setInt(++i, tipologiaSequestro);
			}
			
			if(sequestroDi!=-1){
				//sequestroNota
				pst.setInt(++i , sequestroDi);
			}
			pst.setBoolean(++i, sequestro);
			pst.setInt(++i, 25);
			pst.setString(++i, identificativonc);

			pst.setString( ++i, this.getTipo_richiesta() );

			pst.setString( ++i, this.getPippo() );

			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setInt(pst, ++i, provvedimenti);
			DatabaseUtils.setInt(pst, ++i, sequestriAmministrative);
			DatabaseUtils.setInt(pst, ++i, sequestriPenali);
			DatabaseUtils.setInt(pst, ++i, sequestri);
			pst.setString( ++i, this.getDescrizione1() );
			pst.setString( ++i, this.getDescrizione2() );
			pst.setString( ++i, this.getDescrizione3() );
			pst.setBoolean(++i , this.getTipoSequestro());
			pst.setBoolean(++i , this.getTipoSequestroDue());
			pst.setBoolean(++i , this.getTipoSequestroTre());
			pst.setBoolean(++i , this.getTipoSequestroQuattro());
			pst.setInt(++i, id_nonconformita);
			// if (punteggio != -1) {

			// }
			/* pezzo aggiunto da d.dauria 
		      pst.setTimestamp(++i,new Timestamp( System.currentTimeMillis() ));
		      pst.setString(++i, "x");
		      this.setCloseIt(true);
			 */

			ResultSet rs = pst.executeQuery();
			if (rs.next())
				this.id =rs.getInt("ticketid");
			pst.close();

			db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
			//OrganizationHistory orgHisory = new OrganizationHistory();
			/*
		      TicketLog thisEntry = new TicketLog();
				thisEntry.setEnteredBy(this.getModifiedBy());
				thisEntry.setDepartmentCode(this.getDepartmentCode());
				thisEntry.setAssignedTo(this.getAssignedTo());
				thisEntry.setPriorityCode(this.getPriorityCode());
				thisEntry.setSeverityCode(this.getSeverityCode());
				thisEntry.setEscalationCode(this.getEscalationLevel());
				thisEntry.setEntryText(this.getComment());
				thisEntry.setTicketId(this.getId());
				thisEntry.setStateId(this.getStateId());
				thisEntry.process(db, this.getId(), this.getEnteredBy(), this
						.getModifiedBy());
			 */
			//orgHisory.setOrgId( this.getOrgId() );
			//orgHisory.setMessage( "Aperta richiesta n. " + this.getId() );
			//orgHisory.insert( db );

			//Update the rest of the fields
			this.update(db, true);
			
			if (commit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		//ticket table
		this.setId(rs.getInt("ticketid"));

		  try
		    {
			    super.setTipologiaNonConformita(rs.getInt("tipologia_nonconformita"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		    
		 altId = DatabaseUtils.getInt(rs, "alt_id");
		if (farmacosorveglanza == false)
		{
			orgId = DatabaseUtils.getInt(rs, "org_id");
			idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
			idApiario = DatabaseUtils.getInt(rs, "id_apiario");
		}
		else
		{
			orgId = DatabaseUtils.getInt(rs, "id_farmacia");
		}
		   try
		    {
		    	super.setIdDistributore(rs.getInt("id_distributore"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		id_nonconformita = rs.getInt("id_nonconformita");
		idTicketNonConformita = id_nonconformita ;
		idControlloUfficialeTicket = rs.getInt("id_cu");
		contactId = DatabaseUtils.getInt(rs, "contact_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		
		tipologia_operatore = rs.getInt("tipo_operatore");
		if (idStabilimento>0)
			   tipologia_operatore = Ticket.TIPO_OPU;
		chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		 tipo_nc = rs.getInt("tipo_nc") ;
		sequestro=rs.getBoolean("sequestrotrasporti");
		descrizione=rs.getString("descrizioneesitosequestro");
		valutazione = rs.getString("nc_gravi_valutazione");
		identificativonc=rs.getString("identificativonc");
		noteSequestrodi=rs.getString("notesequestridi");
		quantita = rs.getDouble("quantita");
		tipologiaSequestro = rs.getInt("tipologiaSequestro");
		sequestroDi=rs.getInt("sequestrodi");
		esitoSequestro=rs.getInt("esito_sequestri");
		descrizionEsito = rs.getString("descrizione_esito");
		notaSequestro=rs.getString("notesequestro");
		codiceArticolo=rs.getInt("codice_articolo");
		solution = rs.getString("solution");
		location = rs.getString("location");
		assignedDate = rs.getTimestamp("assigned_date");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		cause = rs.getString("cause");
		estimatedResolutionDateTimeZone = rs.getString(
		"est_resolution_date_timezone");
		trashedDate = rs.getTimestamp("trashed_date");
		causeId = DatabaseUtils.getInt(rs, "cause_id");
		resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipo_richiesta = rs.getString( "tipo_richiesta" );
		pippo = rs.getString( "custom_data" );
		tipologia = rs.getInt( "tipologia" );
		provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		sequestriAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		sequestriPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		sequestri = DatabaseUtils.getInt(rs, "sequestri");

		descrizione1 = rs.getString( "descrizione1" );
		descrizione2 = rs.getString( "descrizione2" );
		descrizione3 = rs.getString( "descrizione3" );

		tipoSequestro = rs.getBoolean("tipo_sequestro");
		tipoSequestroDue = rs.getBoolean("tipo_sequestro_due");
		tipoSequestroTre = rs.getBoolean("tipo_sequestro_tre");
		tipoSequestroQuattro = rs.getBoolean("tipo_sequestro_quattro");
		idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		identificativo = rs.getString("identificativo");
		punteggio = rs.getInt("punteggio");

		//organization table

		orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");

		setPermission();
	}

	public String getNotaSequestro() {
		return notaSequestro;
	}
	public void setNotaSequestro(String notaSequestro) {
		this.notaSequestro = notaSequestro;
	}
	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE ticket " +
				"SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
				"pri_code = ?, scode = ?, " +
				"cat_code = ?, assigned_to = ?, " +
				"subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
				"source_code = ?, contact_id = ?, problem = ?, " +
		"status_id = ?, trashed_date = ?, site_id = ? , ");
		if (!override) {
			sql.append(
					"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		}
		

		if (tipologiaSequestro != -1) {
			sql.append(" tipologiaSequestro = ?, ");
		}
				
		if (sequestroDi != -1) {
			sql.append(" sequestrodi = ?, ");
		}
		sql.append(" sequestrotrasporti = ?, ");

		if (quantita >= 0) {
			sql.append(" quantita = ?, ");
		}
		sql.append("notesequestridi=?, ");



		if(esitoSequestro!=-1){
			sql.append("esito_sequestri=?,");

		}





		if(codiceArticolo!=-1){
			sql.append("codice_articolo=?, ");

		}

		if(notaSequestro!=null){
			//sequestroNota
			sql.append("notesequestro=?, ");

		}

		if(descrizione!=null){
			//sequestroNota
			sql.append("descrizioneesitosequestro=?, ");
		}
		
		if(valutazione != null){
			//sequestroNota
			sql.append("nc_gravi_valutazione = ?, ");
		}


		sql.append(
				"solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
				"est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
				"cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
				"user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +

				//"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sequestri_amministrative = ?, sequestri_penali = ?, sequestri = ?, descrizione1 = ?, descrizione2 = ?, descrizione3 = ? , tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

				"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, tipo_richiesta = ?, " +
				"provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, " +
				"descrizione2 = ?, descrizione3 = ?, tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ?  " +

		"WHERE ticketid = ? ");
		/* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		int i = 0;
		pst = db.prepareStatement(sql.toString());
        pst.setNull(++i, java.sql.Types.INTEGER);
		DatabaseUtils.setInt(pst, ++i, this.getAssetId());
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);

		DatabaseUtils.setInt(pst, ++i, this.getContactId());
		pst.setString(++i, this.getProblem());
		DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		if (!override) {
			pst.setInt(++i, this.getModifiedBy());
		}
		
		if (tipologiaSequestro != -1) {
			pst.setInt(++i, tipologiaSequestro);
		}
		
		if (sequestroDi != -1) {
			pst.setInt(++i, sequestroDi);
		}

		pst.setBoolean(++i, sequestro);
		if (quantita >= 0) {
			pst.setDouble(++i, quantita);
		}

		pst.setString(++i, noteSequestrodi);
		if(esitoSequestro!=-1){
			pst.setInt(++i, esitoSequestro);

		}



		if(codiceArticolo!=-1){
			pst.setInt(++i, codiceArticolo);

		}

		if(notaSequestro!=null){
			//sequestroNota
			pst.setString(++i, notaSequestro);

		}

		if(descrizione!=null){
			//sequestroNota
			pst.setString(++i, descrizione);
		}

		if(valutazione != null){
			//sequestroNota
			pst.setString(++i, valutazione);
		}

		pst.setString(++i, this.getSolution());

		if( pippo != null )
		{
			pst.setString( ++i, pippo );
		}
		else
		{
			pst.setNull( i++, Types.VARCHAR );
		}
		pst.setString(++i, location);
		DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		pst.setString(++i, this.assignedDateTimeZone);
		DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		pst.setString(++i, estimatedResolutionDateTimeZone);
		DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
		pst.setString(++i, this.resolutionDateTimeZone);
		pst.setString(++i, cause);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);

		DatabaseUtils.setInt(pst, ++i, causeId);
		DatabaseUtils.setInt(pst, ++i, resolutionId);
        pst.setNull(++i, java.sql.Types.INTEGER);
		DatabaseUtils.setInt(pst, ++i, this.getStateId());
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.BOOLEAN);
        pst.setNull(++i, java.sql.Types.INTEGER);
        pst.setNull(++i, java.sql.Types.INTEGER);

		pst.setString( ++i, tipo_richiesta );
		DatabaseUtils.setInt(pst, ++i, provvedimenti);
		DatabaseUtils.setInt(pst, ++i, sequestriAmministrative);
		DatabaseUtils.setInt(pst, ++i, sequestriPenali);
		DatabaseUtils.setInt(pst, ++i, sequestri);

		pst.setString(++i, descrizione1);
		pst.setString(++i, descrizione2);
		pst.setString(++i, descrizione3);

		pst.setBoolean(++i, this.getTipoSequestro());
		pst.setBoolean(++i, this.getTipoSequestroDue());
		pst.setBoolean(++i, this.getTipoSequestroTre());
		pst.setBoolean(++i, this.getTipoSequestroQuattro());

		pst.setInt(++i, id);
		/*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		
		resultCount = pst.executeUpdate();
		pst.close();



	
	
		return resultCount;
	}

	public int chiudi(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);

			

				PreparedStatement pst = null;
				String sql =
					"UPDATE ticket " +
					"SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
							db) + ", modifiedby = ? " +
							"WHERE ticketid = ? ";
				int i = 0;
				pst = db.prepareStatement(sql);
				pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
				pst.setInt(++i, this.getModifiedBy());
				pst.setInt(++i, this.getId());
				resultCount = pst.executeUpdate();
				pst.close();

				this.setClosed((java.sql.Timestamp) null);

				//Update the ticket log
					db.commit();
			
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	//controllo di


	public boolean delete(Connection db, String baseFilePath)
	throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			// delete any related action list items
			logicdelete(db, baseFilePath);
			if (commit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	public int reopen(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql = "UPDATE ticket " + "SET closed = ?, modified = "
			+ DatabaseUtils.getCurrentTimestamp(db)
			+ ", modifiedby = ? " + "WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			// Update the ticket log
						db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	public int update(Connection db) throws SQLException {
		int i = -1;
		try {
			db.setAutoCommit(false);
			i = this.update(db, false);
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return i;
	}

	public String getTestoAppoggio() {
		return testoAppoggio;
	}

	public void setTestoAppoggio(String testoAppoggio) {
		this.testoAppoggio = testoAppoggio;
	}

	/*metodo di chiusura */



}

