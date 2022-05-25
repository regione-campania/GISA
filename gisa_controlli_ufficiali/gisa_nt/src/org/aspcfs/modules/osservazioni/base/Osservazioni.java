/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.osservazioni.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import oracle.net.TNSAddress.Description;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.modules.nonconformita.base.ElementoOsservazione;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Osservazioni extends org.aspcfs.modules.troubletickets.base.Ticket
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -357873719517180996L;
	private String paddedControlloUfficiale="";
	private boolean soa = false;
	private String ncFormaliValutazioni;
	private String ncSignificativeValutazioni;
	private String ncGraviValutazioni;
	private int tipoControllo ;
	protected ArrayList<ElementoOsservazione> non_conformita_gravi			=	new ArrayList<ElementoOsservazione>();
	protected ArrayList<ElementoOsservazione> non_conformita_formali		=	new ArrayList<ElementoOsservazione>();
	protected ArrayList<ElementoOsservazione> non_conformita_significative	=	new ArrayList<ElementoOsservazione>();
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int provvedimenti = -1;
	protected int nonconformitaAmministrative = -1;
	protected int nonconformitaPenali = -1;
	protected int nonconformita = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";
	private String idControlloUfficiale = null;
	private String identificativo = null;
	private boolean farmacosorveglianza = false;
	private boolean tipoNonConformitaDue = false;
	private boolean tipoNonConformitaTre = false;
	private boolean tipoNonConformitaQuattro = false; 
	private int nucleoIspettivo = -1;
	private int nucleoIspettivoDue = -1;
	private int nucleoIspettivoTre = -1;
	private String componenteNucleo = null;
	private String componenteNucleoDue = null;
	private String componenteNucleoTre = null;
	private String testoAppoggio = "";
	private String testoAlimentoComposto = null;
	private int nucleoIspettivoQuattro = -1;
	private int nucleoIspettivoCinque = -1;
	private int nucleoIspettivoSei = -1;
	private int nucleoIspettivoSette = -1;
	private int nucleoIspettivoOtto = -1;
	private int nucleoIspettivoNove = -1;
	private int nucleoIspettivoDieci = -1;
	private String componenteNucleoQuattro = null;
	private String componenteNucleoCinque = null;
	private String componenteNucleoSei = null;
	private String componenteNucleoSette = null;
	private String componenteNucleoOtto = null;
	private String componenteNucleoNove = null;
	private String componenteNucleoDieci = null;
	private String nonConformitaFormali="";
	private String nonConformitaGravi="";
	private String nonConformitaSignificative="";
	private String puntiFormali=null;
	private String puntiSignificativi=null;
	private String puntiGravi=null;
	private int punteggio=0;
	private String sottoAttivita ;

	private String note_altro = "";
	private boolean resolvable;

	
	public String getNote_altro() {
		return note_altro;
	}
	public void setNote_altro(String note_altro) {
		this.note_altro = note_altro;
	}
	public boolean isResolvable() {
		return resolvable;
	}
	public void setResolvable(boolean resolvable) {
		this.resolvable = resolvable;
	}
	public String getSottoAttivita() {
		return sottoAttivita;
	}
	public void setSottoAttivita(String sottoAttivita) {
		this.sottoAttivita = sottoAttivita;
	}
	public Osservazioni ()
	{

	}



	public int getTipoControllo() {
		return tipoControllo;
	}


	public void setTipoControllo(int tipoControllo) {
		this.tipoControllo = tipoControllo;
	}


	public String getNcFormaliValutazioni() {
		return ncFormaliValutazioni;
	}


	public void setNcFormaliValutazioni(String ncFormaliValutazioni) {
		this.ncFormaliValutazioni = ncFormaliValutazioni;
	}


	public String getNcSignificativeValutazioni() {
		return ncSignificativeValutazioni;
	}


	public void setNcGraviValutazioni(String ncGravi) {
		this.ncGraviValutazioni = ncGravi;
	}

	public String getNcGraviValutazioni() {
		return ncGraviValutazioni;
	}

	public void setNcSignificativeValutazioni(String ncSignificativeValutazioni) {
		this.ncSignificativeValutazioni = ncSignificativeValutazioni;
	}

	public boolean isSoa() {
		return soa;
	}


	public void setSoa(boolean soa) {
		this.soa = soa;
	}

	public ArrayList<ElementoOsservazione> getNon_conformita_formali() {
		return non_conformita_formali;
	}

	public ArrayList<ElementoOsservazione> getNon_conformita_significative() {
		return non_conformita_significative;
	}



	public ArrayList<ElementoOsservazione> getNon_conformita_gravi() {
		return non_conformita_gravi;
	}


	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}


	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}
	//aggiunto da d.dauria per gestire i nonconformita
	private boolean tipoNonConformita = false;
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
	public int getNucleoIspettivoQuattro() {
		return nucleoIspettivoQuattro;
	}

	public void setNucleoIspettivoQuattro(int nucleoIspettivoQuattro) {
		this.nucleoIspettivoQuattro = nucleoIspettivoQuattro;
	}

	public int getNucleoIspettivoCinque() {
		return nucleoIspettivoCinque;
	}

	public void setNucleoIspettivoCinque(int nucleoIspettivoCinque) {
		this.nucleoIspettivoCinque = nucleoIspettivoCinque;
	}

	public int getNucleoIspettivoSei() {
		return nucleoIspettivoSei;
	}

	public void setNucleoIspettivoSei(int nucleoIspettivoSei) {
		this.nucleoIspettivoSei = nucleoIspettivoSei;
	}

	public int getNucleoIspettivoSette() {
		return nucleoIspettivoSette;
	}

	public void setNucleoIspettivoSette(int nucleoIspettivoSette) {
		this.nucleoIspettivoSette = nucleoIspettivoSette;
	}

	public int getNucleoIspettivoOtto() {
		return nucleoIspettivoOtto;
	}

	public void setNucleoIspettivoOtto(int nucleoIspettivoOtto) {
		this.nucleoIspettivoOtto = nucleoIspettivoOtto;
	}

	public int getNucleoIspettivoNove() {
		return nucleoIspettivoNove;
	}

	public void setNucleoIspettivoNove(int nucleoIspettivoNove) {
		this.nucleoIspettivoNove = nucleoIspettivoNove;
	}

	public int getNucleoIspettivoDieci() {
		return nucleoIspettivoDieci;
	}

	public void setNucleoIspettivoDieci(int nucleoIspettivoDieci) {
		this.nucleoIspettivoDieci = nucleoIspettivoDieci;
	}

	public String getComponenteNucleoQuattro() {
		return componenteNucleoQuattro;
	}

	public void setComponenteNucleoQuattro(String componenteNucleoQuattro) {
		this.componenteNucleoQuattro = componenteNucleoQuattro;
	}

	public String getComponenteNucleoCinque() {
		return componenteNucleoCinque;
	}

	public void setComponenteNucleoCinque(String componenteNucleoCinque) {
		this.componenteNucleoCinque = componenteNucleoCinque;
	}

	public String getComponenteNucleoSei() {
		return componenteNucleoSei;
	}

	public void setComponenteNucleoSei(String componenteNucleoSei) {
		this.componenteNucleoSei = componenteNucleoSei;
	}

	public String getComponenteNucleoSette() {
		return componenteNucleoSette;
	}

	public void setComponenteNucleoSette(String componenteNucleoSette) {
		this.componenteNucleoSette = componenteNucleoSette;
	}

	public String getComponenteNucleoOtto() {
		return componenteNucleoOtto;
	}

	public void setComponenteNucleoOtto(String componenteNucleoOtto) {
		this.componenteNucleoOtto = componenteNucleoOtto;
	}

	public String getComponenteNucleoNove() {
		return componenteNucleoNove;
	}

	public void setComponenteNucleoNove(String componenteNucleoNove) {
		this.componenteNucleoNove = componenteNucleoNove;
	}

	public String getComponenteNucleoDieci() {
		return componenteNucleoDieci;
	}

	public void setComponenteNucleoDieci(String componenteNucleoDieci) {
		this.componenteNucleoDieci = componenteNucleoDieci;
	}


	public String getNonConformitaFormali() {
		return nonConformitaFormali;
	}

	public void setNonConformitaFormali(String ncFormali) {
		nonConformitaFormali=ncFormali;

	}

	public String getNonConformitaGravi() {
		return nonConformitaGravi;
	}

	public void setNonConformitaGravi(String nonConformitaGravi) {
		this.nonConformitaGravi = nonConformitaGravi;
	}

	public String getNonConformitaSignificative() {
		return nonConformitaSignificative;
	}

	public void setNonConformitaSignificative(String nonConformitaSignificative) {
		this.nonConformitaSignificative = nonConformitaSignificative;
	}

	public String getPuntiFormali() {
		return puntiFormali;
	}

	public void setPuntiFormali(String puntiFormali) {
		this.puntiFormali = puntiFormali;
	}

	public String getPuntiSignificativi() {
		return puntiSignificativi;
	}

	public void setPuntiSignificativi(String puntiSignificativi) {
		this.puntiSignificativi = puntiSignificativi;
	}

	public String getPuntiGravi() {
		return puntiGravi;
	}

	public void setPuntiGravi(String puntiGravi) {
		this.puntiGravi = puntiGravi;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public void setNucleoIspettivoQuattro(String temp)
	{
		this.nucleoIspettivoQuattro = Integer.parseInt(temp);
	}
	public void setPunteggio(String temp)
	{
		if(temp!=null){
			if(!temp.equals(""))
				this.punteggio = Integer.parseInt(temp);
		}
	}
	public void setNucleoIspettivoCinque(String temp)
	{
		this.nucleoIspettivoCinque = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoSei(String temp)
	{
		this.nucleoIspettivoSei = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoSette(String temp)
	{
		this.nucleoIspettivoSette = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoOtto(String temp)
	{
		this.nucleoIspettivoOtto = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoNove(String temp)
	{
		this.nucleoIspettivoNove = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoDieci(String temp)
	{
		this.nucleoIspettivoDieci = Integer.parseInt(temp);
	}


	public void setNucleoIspettivo(String temp)
	{
		this.nucleoIspettivo = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoDue(String temp)
	{
		this.nucleoIspettivoDue = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoTre(String temp)
	{
		this.nucleoIspettivoTre = Integer.parseInt(temp);
	}

	public int getNucleoIspettivo() {
		return nucleoIspettivo;
	}



	public void setNucleoIspettivo(int nucleoIspettivo) {
		this.nucleoIspettivo = nucleoIspettivo;
	}



	public int getNucleoIspettivoDue() {
		return nucleoIspettivoDue;
	}

	public void setNucleoIspettivoDue(int nucleoIspettivoDue) {
		this.nucleoIspettivoDue = nucleoIspettivoDue;
	}

	public int getNucleoIspettivoTre() {
		return nucleoIspettivoTre;
	}

	public void setNucleoIspettivoTre(int nucleoIspettivoTre) {
		this.nucleoIspettivoTre = nucleoIspettivoTre;
	}

	public String getComponenteNucleo() {
		return componenteNucleo;
	}

	public void setComponenteNucleo(String componenteNucleo) {
		this.componenteNucleo = componenteNucleo;
	}

	public String getComponenteNucleoDue() {
		return componenteNucleoDue;
	}

	public void setComponenteNucleoDue(String componenteNucleoDue) {
		this.componenteNucleoDue = componenteNucleoDue;
	}

	public String getComponenteNucleoTre() {
		return componenteNucleoTre;
	}

	public void setComponenteNucleoTre(String componenteNucleoTre) {
		this.componenteNucleoTre = componenteNucleoTre;
	}

	public boolean getTipoNonConformita() {
		return tipoNonConformita;
	}

	public void setTipoNonConformita(boolean tipoNonConformita) {
		this.tipoNonConformita = tipoNonConformita;
	}

	public void setTipoNonConformita(String temp) {
		this.tipoNonConformita = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoNonConformitaDue() {
		return tipoNonConformitaDue;
	}

	public void setTipoNonConformitaDue(boolean tipoNonConformitaDue) {
		this.tipoNonConformitaDue = tipoNonConformitaDue;
	}

	public void setTipoNonConformitaDue(String temp) {
		this.tipoNonConformitaDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoNonConformitaTre() {
		return tipoNonConformitaTre;
	}

	public void setTipoNonConformitaTre(boolean tipoNonConformitaTre) {
		this.tipoNonConformitaTre = tipoNonConformitaTre;
	}

	public void setTipoNonConformitaTre(String temp) {
		this.tipoNonConformitaTre = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoNonConformitaQuattro() {
		return tipoNonConformitaQuattro;
	}

	public void setTipoNonConformitaQuattro(boolean tipoNonConformitaQuattro) {
		this.tipoNonConformitaQuattro = tipoNonConformitaQuattro;
	}

	public void setTipoNonConformitaQuattro(String temp) {
		this.tipoNonConformitaQuattro = DatabaseUtils.parseBoolean(temp);
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

	public void setNonConformitaAmministrative(String nonconformitaAmministrative) {
		try {
			this.nonconformitaAmministrative = Integer.parseInt(nonconformitaAmministrative);
		} catch (Exception e) {
			this.nonconformitaAmministrative = -1;
		}
	}

	public void setNonConformitaPenali(String nonconformitaPenali) {
		try {
			this.nonconformitaPenali = Integer.parseInt(nonconformitaPenali);
		} catch (Exception e) {
			this.nonconformitaPenali = -1;
		}
	}

	public void setNonConformita(String nonconformita) {
		try {
			this.nonconformita = Integer.parseInt(nonconformita);
		} catch (Exception e) {
			this.nonconformita = -1;
		}
	}

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;
	}

	public int getNonConformitaAmministrative() {
		return nonconformitaAmministrative;
	}

	public void setNonConformitaAmministrative(int nonconformitaAmministrative) {
		this.nonconformitaAmministrative = nonconformitaAmministrative;
	}

	public int getNonConformitaPenali() {
		return nonconformitaPenali;
	}

	public void setSequestrPenali(int nonconformitaPenali) {
		this.nonconformitaPenali = nonconformitaPenali;
	}

	public int getNonConformita() {
		return nonconformita;
	}

	public void setNonConformita(int nonconformita) {
		this.nonconformita = nonconformita;
	}


	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}


	public Osservazioni(ResultSet rs , Connection db) throws SQLException {
		buildRecord(rs);


		this.set_NonConformitaFormali(db);
		this.setNon_conformita_gravi(db);
		this.setNon_conformita_significative(db);
	}
	
	public Osservazioni(ResultSet rs) throws SQLException {
		buildRecord(rs);


		
	}

	public Osservazioni(Connection db, int id,boolean soa,boolean flag) throws SQLException {
		this.soa = soa;
		queryRecord(db, id);
	}

	public Osservazioni(Connection db, int id) throws SQLException {
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

	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}

		String sql =   "SELECT t.*, " +
		"o.site_id AS orgsiteid," +
		"o.tipologia as tipologia_operatore, " +

		"a.serial_number AS serialnumber, " +
		"a.manufacturer_code AS assetmanufacturercode, " +
		"a.vendor_code AS assetvendorcode, " +
		"a.model_version AS modelversion, " +
		"a.location AS assetlocation, " +
		"a.onsite_service_model AS assetonsiteservicemodel " +

		"FROM ticket t ";
		sql += "LEFT JOIN organization o ON (t.org_id = o.org_id) ";
		sql +=

			"LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +

			"WHERE t.ticketid = ? AND t.tipologia = 16";
		PreparedStatement pst = db.prepareStatement(sql);

		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db,Integer.parseInt(this.idControlloUfficiale));
			tipoControllo = cu.getTipoCampione();

			this.set_NonConformitaFormali(db);
			this.setNon_conformita_gravi(db);
			this.setNon_conformita_significative(db);

		}
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}


	}



	public void delTipiOsservazione(Connection db) throws SQLException
	{
		String delAll = "delete from salvataggio_osservazioni_lista where id_salvataggio_osservazioni in (select id from salvataggio_osservazioni where idticket=?);" +
		"delete from salvataggio_osservazioni_oggetto_audit where id_salvataggio_osservazioni in (select id from salvataggio_osservazioni where idticket=?);"+
		"delete from salvataggio_osservazioni where idticket=?" ;

		PreparedStatement pst = db.prepareStatement(delAll);
		pst.setInt(1, id);
		pst.setInt(2, id);
		pst.setInt(3, id);
		pst.execute();	
	}

	public boolean insertTipiNonConformita(ActionContext context,Connection db,String[] oggettoAudit,String[] listaOsservazioni , int tipologia,String note  ) throws SQLException {



		if (oggettoAudit!=null && oggettoAudit.length>0)
		{



			String insert_note="insert into salvataggio_osservazioni (id,idticket,tipologia,note) values (?,?,?,?)";
			PreparedStatement pst_note =	db.prepareStatement(insert_note) ;

			int idNc = DatabaseUtils.getNextSeqInt(db, context,"salvataggio_osservazioni","id");
			pst_note.setInt(1, idNc);
			pst_note.setInt(2, id);
			pst_note.setInt(3, tipologia);
			pst_note.setString(4, note);
			pst_note.execute();





			pst_note =	db.prepareStatement("insert into salvataggio_osservazioni_lista (id_salvataggio_osservazioni,id_lookup_osservazioni) values (?,?)") ;
			for (int i = 0 ; i<listaOsservazioni.length;i++)
			{
				if (Integer.parseInt(listaOsservazioni[i]) >0)
				{
				pst_note.setInt(1, idNc);
				pst_note.setInt(2, Integer.parseInt(listaOsservazioni[i]));
				pst_note.execute();
				}
			}


			pst_note =	db.prepareStatement("insert into salvataggio_osservazioni_oggetto_audit (id_salvataggio_osservazioni,id_lookup_oggetto_audit) values (?,?)") ;
			for (int i = 0 ; i<oggettoAudit.length;i++)
			{
				if (Integer.parseInt(oggettoAudit[i]) >0)
				{
				pst_note.setInt(1, idNc);
				pst_note.setInt(2, Integer.parseInt(oggettoAudit[i]));
				pst_note.execute();
			}
			}

			pst_note.close();


		}






		return true;
	}

	public boolean updateControllo(Connection db, int ticketId) throws SQLException {
		String insert="UPDATE ticket SET ncrilevate = TRUE WHERE ticketId = ?";
		PreparedStatement pst=db.prepareStatement(insert);
		pst.setInt(1, ticketId);
		pst.execute();
		pst.close();
		return true;
	}

	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}

			id = DatabaseUtils.getNextSeq(db, context,"ticket","ticketid");
			sql.append(
					"INSERT INTO ticket (contact_id, problem, pri_code, " +
					"department_code, cat_code, scode,  link_contract_id, " +
					"link_asset_id, expectation, product_id, customer_product_id, " +
					"key_count, status_id, trashed_date, user_group_id, cause_id, " +
					"resolution_id, defect_id, escalation_level, resolvable, " +
			"resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified, ");
			if (farmacosorveglianza == true)
			{
				sql.append("id_farmacia, ");
			}
			else
			{
				sql.append("org_id, ");
			}

			sql.append("nc_formali_valutazione,nc_significative_valutazione, nc_gravi_valutazione, ");



			if (id > -1) {
				sql.append("ticketid, ");
			}
			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}
			if ( nucleoIspettivo > -1) {
				sql.append("nucleo_ispettivo,");
			}
			if ( nucleoIspettivoDue > -1) {
				sql.append(" nucleo_ispettivo_due,");
			}
			if ( nucleoIspettivoTre > -1) {
				sql.append(" nucleo_ispettivo_tre,");
			}
			if (componenteNucleo != null) {
				sql.append(" componente_nucleo,");
			}
			if (componenteNucleoDue != null) {
				sql.append(" componente_nucleo_due,");
			}
			if (componenteNucleoTre != null) {
				sql.append(" componente_nucleo_tre,");
			}
			if (testoAlimentoComposto != null) {
				sql.append("testo_alimento_composto,");
			}
			if ( nucleoIspettivoQuattro > -1) {
				sql.append(" nucleo_ispettivo_quattro,");
			}
			if ( nucleoIspettivoCinque > -1) {
				sql.append(" nucleo_ispettivo_cinque,");
			}
			if ( nucleoIspettivoSei > -1) {
				sql.append("nucleo_ispettivo_sei,");
			}
			if ( nucleoIspettivoSette > -1) {
				sql.append(" nucleo_ispettivo_sette,");
			}
			if ( nucleoIspettivoOtto > -1) {
				sql.append(" nucleo_ispettivo_otto,");
			}
			if ( nucleoIspettivoNove > -1) {
				sql.append(" nucleo_ispettivo_nove,");
			}
			if ( nucleoIspettivoDieci > -1) {
				sql.append(" nucleo_ispettivo_dieci,");
			}
			
			
			if (componenteNucleoQuattro != null) {
				sql.append(" componente_nucleo_quattro,");
			}
			if (componenteNucleoCinque != null) {
				sql.append(" componente_nucleo_cinque,");
			}
			if (componenteNucleoSei != null) {
				sql.append(" componente_nucleo_sei,");
			}
			if (componenteNucleoSette != null) {
				sql.append("componente_nucleo_sette,");
			}
			if (componenteNucleoOtto != null) {
				sql.append(" componente_nucleo_otto,");
			}
			if (componenteNucleoNove != null) {
				sql.append(" componente_nucleo_nove,");
			}
			if (componenteNucleoDieci != null) {
				sql.append(" componente_nucleo_dieci,");
			}
			
			
			
			if (note_altro != null) {
				sql.append(" note_altro,");
			}
			
			if (resolutionDate != null) {
				sql.append(" resolution_date,");
			}
			
			if (nonConformitaFormali != null) {
				sql.append(" nonconformitaformali,");
			}


			if (nonConformitaSignificative != null) {
				sql.append(" nonconformitasignificative,");
			}


			if (nonConformitaGravi != null) {
				sql.append(" nonconformitagravi,");
			}


			if (puntiFormali != null) {
				sql.append(" puntiformali,");
			}


			if (puntiSignificativi != null) {
				sql.append(" puntisignificativi,");
			}


			if (puntiGravi != null) {
				sql.append(" puntigravi,");
			}
			sql.append(" id_controllo_ufficiale, identificativo,");
			if (punteggio != 0) {
				sql.append(" punteggio,");
			}


			sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
			"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali, sequestri, descrizione1, descrizione2, descrizione3  ");

			sql.append(" , tipo_sequestro");
			sql.append(" , tipo_sequestro_due");
			sql.append(" , tipo_sequestro_tre");
			sql.append(" , tipo_sequestro_quattro");
			sql.append(")");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
			sql.append("?, ?, ?, ");

			if (farmacosorveglianza == true)
			{
				sql.append("?,");
			}
			else
			{
				sql.append("?,");
			}
			sql.append("?,?,?,"); //per valutazioni gravi ho aggiunto un ?
			if (id > -1) {
				sql.append("?,");
			}
			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}
			if (nucleoIspettivo > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoDue > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoTre > -1) {
				sql.append("?,");
			}
			if (componenteNucleo != null) {
				sql.append("?, ");
			}
			if (componenteNucleoDue != null) {
				sql.append("?, ");
			}
			if (componenteNucleoTre != null) {
				sql.append("?, ");
			}
			if (testoAlimentoComposto != null) {
				sql.append("?,");
			}
			if (nucleoIspettivoQuattro > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoCinque > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoSei > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoSette > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoOtto > -1) {
				sql.append("?,");
			}
			if (nucleoIspettivoNove > -1) {
				sql.append(", ?");
			}
			if (nucleoIspettivoDieci > -1) {
				sql.append("?,");
			}
			if (componenteNucleoQuattro != null) {
				sql.append("?, ");
			}
			if (componenteNucleoCinque != null) {
				sql.append("?, ");
			}
			if (componenteNucleoSei != null) {
				sql.append("?, ");
			}
			if (componenteNucleoSette != null) {
				sql.append("?, ");
			}
			if (componenteNucleoOtto != null) {
				sql.append("?, ");
			}
			if (componenteNucleoNove != null) {
				sql.append("?, ");
			}
			if (componenteNucleoDieci != null) {
				sql.append("?, ");
			}

			
			if (note_altro != null) {
				sql.append(" ?,");
			}

			if (resolutionDate != null) {
				sql.append(" ?,");
			}
			
			if (nonConformitaFormali != null) {
				sql.append(" ?,");
			}


			if (nonConformitaSignificative != null) {
				sql.append(" ?,");
			}


			if (nonConformitaGravi != null) {
				sql.append(" ?,");
			}


			if (puntiFormali != null) {
				sql.append(" ? ,");
			}


			if (puntiSignificativi != null) {
				sql.append(" ?,");
			}


			if (puntiGravi != null) {
				sql.append(" ?,");
			}
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
				else
					if(siteId ==14){
						asl = "REGIONE";
					}


			}
			sql.append(" ?, '"+asl+idControlloUfficiale+"' || trim(to_char( " + id + ", '000000' )), ");

			if (punteggio != 0) {
				sql.append(" ?,");
			}

			sql.append("?, ?, ?, ?, " +
			"16, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "); //ho aggiunto 2 punti interrogativi

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

			pst.setBoolean(++i, this.resolvable);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			pst.setString(++i, super.getIpEntered());
			pst.setString(++i, super.getIpModified());

			if (farmacosorveglianza == true)
			{
				pst.setInt(++i, orgId);
			}
			else
			{
				pst.setInt(++i, orgId);
			}
			pst.setString(++i, ncFormaliValutazioni);
			pst.setString(++i, ncSignificativeValutazioni);
			pst.setString(++i, ncGraviValutazioni);
			if (id > -1) {
				pst.setInt(++i, id);
			}
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			} 
			if(nucleoIspettivo > -1)
				pst.setInt(++i , nucleoIspettivo);
			if(nucleoIspettivoDue > -1)
				pst.setInt(++i , nucleoIspettivoDue);
			if(nucleoIspettivoTre > -1)
				pst.setInt(++i , nucleoIspettivoTre);
			if (componenteNucleo != null) {
				pst.setString(++i, componenteNucleo);
			}
			if (componenteNucleoDue != null) {
				pst.setString(++i, componenteNucleoDue);
			}
			if (componenteNucleoTre != null) {
				pst.setString(++i, componenteNucleoTre);
			}     
			if (testoAlimentoComposto != null) {
				pst.setString(++i, testoAlimentoComposto);
			}  
			if(nucleoIspettivoQuattro > -1)
				pst.setInt(++i , nucleoIspettivoQuattro);
			if(nucleoIspettivoCinque > -1)
				pst.setInt(++i , nucleoIspettivoCinque);     
			if(nucleoIspettivoSei > -1)
				pst.setInt(++i , nucleoIspettivoSei);
			if(nucleoIspettivoSette > -1)
				pst.setInt(++i , nucleoIspettivoSette);
			if(nucleoIspettivoOtto > -1)
				pst.setInt(++i , nucleoIspettivoOtto);
			if(nucleoIspettivoNove > -1)
				pst.setInt(++i , nucleoIspettivoNove);
			if(nucleoIspettivoDieci > -1)
				pst.setInt(++i , nucleoIspettivoDieci);
			if (componenteNucleoQuattro != null) {
				pst.setString(++i, componenteNucleoQuattro);
			}
			if (componenteNucleoCinque != null) {
				pst.setString(++i, componenteNucleoCinque);
			}
			if (componenteNucleoSei != null) {
				pst.setString(++i, componenteNucleoSei);
			}
			if (componenteNucleoSette != null) {
				pst.setString(++i, componenteNucleoSette);
			}
			if (componenteNucleoOtto != null) {
				pst.setString(++i, componenteNucleoOtto);
			}
			if (componenteNucleoNove != null) {
				pst.setString(++i, componenteNucleoNove);
			}
			if (componenteNucleoDieci != null) {
				pst.setString(++i, componenteNucleoDieci);
			}
		
			
			if (note_altro != null) {
				pst.setString(++i, note_altro);
			}
			
			if (resolutionDate != null) {
				pst.setTimestamp(++i, resolutionDate);
			}
			
			if (nonConformitaFormali != null) {
				pst.setString(++i, nonConformitaFormali);
			}


			if (nonConformitaSignificative != null) {
				pst.setString(++i,nonConformitaSignificative);
			}


			if (nonConformitaGravi != null) {
				pst.setString(++i, nonConformitaGravi);
			}


			if (puntiFormali != null) {
				pst.setString(++i, puntiFormali);
			}


			if (puntiSignificativi != null) {
				pst.setString(++i, puntiSignificativi);
			}


			if (puntiGravi != null) {
				pst.setString(++i, puntiGravi);
			}
			pst.setString(++i, idControlloUfficiale);
			if (punteggio != 0) {
				pst.setInt(++i, punteggio);
			}



			/**
			 * 
			 */
			 pst.setString( ++i, this.getTipo_richiesta() );

			pst.setString( ++i, this.getPippo() );

			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setInt(pst, ++i, provvedimenti);
			DatabaseUtils.setInt(pst, ++i, nonconformitaAmministrative);
			DatabaseUtils.setInt(pst, ++i, nonconformitaPenali);
			DatabaseUtils.setInt(pst, ++i, nonconformita);
			pst.setString( ++i, this.getDescrizione1() );
			pst.setString( ++i, this.getDescrizione2() );
			pst.setString( ++i, this.getDescrizione3() );
			pst.setBoolean(++i , this.getTipoNonConformita());
			pst.setBoolean(++i , this.getTipoNonConformitaDue());
			pst.setBoolean(++i , this.getTipoNonConformitaTre());
			pst.setBoolean(++i , this.getTipoNonConformitaQuattro());

			/* pezzo aggiunto da d.dauria 
		      pst.setTimestamp(++i,new Timestamp( System.currentTimeMillis() ));
		      pst.setString(++i, "x");
		      this.setCloseIt(true);
			 */
			pst.execute();
			pst.close();
	//Update the rest of the fields
			
			//????chiedere a peppe 
			//this.update(db, true);

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

		orgId = DatabaseUtils.getInt(rs, "org_id");


		ncFormaliValutazioni = rs.getString("nc_formali_valutazione");
		ncSignificativeValutazioni =  rs.getString("nc_significative_valutazione");
		ncGraviValutazioni = rs.getString("nc_gravi_valutazione");
		contactId = DatabaseUtils.getInt(rs, "contact_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");

		tipoControllo = rs.getInt("tipo_controllo");
		chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");

		solution = rs.getString("solution");
		location = rs.getString("location");
		assignedDate = rs.getTimestamp("assigned_date");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		
		resolutionDate = rs.getTimestamp("resolution_date");
		resolvable = rs.getBoolean("resolvable");
		cause = rs.getString("cause");
		assetId = DatabaseUtils.getInt(rs, "link_asset_id");

		estimatedResolutionDateTimeZone = rs.getString(
		"est_resolution_date_timezone");
		assignedDateTimeZone = rs.getString("assigned_date_timezone");
		resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		statusId = DatabaseUtils.getInt(rs, "status_id");
		trashedDate = rs.getTimestamp("trashed_date");
        note_altro = rs.getString("note_altro");
		causeId = DatabaseUtils.getInt(rs, "cause_id");
		resolutionId = DatabaseUtils.getInt(rs, "resolution_id");

		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipo_richiesta = rs.getString( "tipo_richiesta" );
		pippo = rs.getString( "custom_data" );
		tipologia = rs.getInt( "tipologia" );
		provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		nonconformitaAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		nonconformitaPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		nonconformita = DatabaseUtils.getInt(rs, "sequestri");

		descrizione1 = rs.getString( "descrizione1" );
		descrizione2 = rs.getString( "descrizione2" );
		descrizione3 = rs.getString( "descrizione3" );

		tipoNonConformita = rs.getBoolean("tipo_sequestro");
		tipoNonConformitaDue = rs.getBoolean("tipo_sequestro_due");
		tipoNonConformitaTre = rs.getBoolean("tipo_sequestro_tre");
		tipoNonConformitaQuattro = rs.getBoolean("tipo_sequestro_quattro");


		nucleoIspettivo = DatabaseUtils.getInt(rs, "nucleo_ispettivo");
		nucleoIspettivoDue = DatabaseUtils.getInt(rs, "nucleo_ispettivo_due");
		nucleoIspettivoTre = DatabaseUtils.getInt(rs, "nucleo_ispettivo_tre");
		componenteNucleo = rs.getString("componente_nucleo");
		componenteNucleoDue = rs.getString("componente_nucleo_due");
		componenteNucleoTre = rs.getString("componente_nucleo_tre");
		testoAlimentoComposto = rs.getString("testo_alimento_composto");
		nucleoIspettivoQuattro = DatabaseUtils.getInt(rs, "nucleo_ispettivo_quattro");
		nucleoIspettivoCinque = DatabaseUtils.getInt(rs, "nucleo_ispettivo_cinque");
		nucleoIspettivoSei = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sei");
		nucleoIspettivoSette = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sette");
		nucleoIspettivoOtto = DatabaseUtils.getInt(rs, "nucleo_ispettivo_otto");
		nucleoIspettivoNove = DatabaseUtils.getInt(rs, "nucleo_ispettivo_nove");
		nucleoIspettivoDieci = DatabaseUtils.getInt(rs, "nucleo_ispettivo_dieci");
		componenteNucleoQuattro = rs.getString("componente_nucleo_quattro");
		componenteNucleoCinque = rs.getString("componente_nucleo_cinque");
		componenteNucleoSei = rs.getString("componente_nucleo_sei");
		componenteNucleoSette = rs.getString("componente_nucleo_sette");
		componenteNucleoOtto = rs.getString("componente_nucleo_otto");
		componenteNucleoNove = rs.getString("componente_nucleo_nove");
		componenteNucleoDieci = rs.getString("componente_nucleo_dieci");

		nonConformitaFormali = rs.getString("nonconformitaformali");
		nonConformitaSignificative=rs.getString("nonconformitasignificative");
		nonConformitaGravi=rs.getString("nonconformitagravi");
		puntiFormali=rs.getString("puntiformali");
		puntiSignificativi=rs.getString("puntisignificativi");
		puntiGravi=rs.getString("puntigravi");
		idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		identificativo = rs.getString("identificativo");
		punteggio=rs.getInt("punteggio");

	

		tipologia_operatore = rs.getInt("tipologia_operatore") ;
		//Calculations

	}


	public void delAllNc(Connection db) throws SQLException
	{
		String del = "delete from salvataggio_osservazioni where idticket = ?";

		PreparedStatement pst_del =	db.prepareStatement(del) ;
		pst_del.setInt(1, id);
		pst_del.execute();


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
				"status_id = ?, trashed_date = ?, site_id = ? ,ip_modified='"+super.getIpModified()+"', ");
		if (!override) {
			sql.append(
					"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		}



		if (nonConformitaFormali != null) {
			sql.append(" nonconformitaformali= ?, ");
		}


		if (nonConformitaSignificative != null) {
			sql.append(" nonconformitasignificative= ?, ");
		}

		sql.append("nc_formali_valutazione = ?,nc_significative_valutazione=?, nc_gravi_valutazione=?,");


		if (nonConformitaGravi != null) {
			sql.append(" nonconformitagravi= ?, ");
		}


		if (puntiFormali != null) {
			sql.append(" puntiformali= ?, ");
		}


		if (puntiSignificativi != null) {
			sql.append(" puntisignificativi= ?, ");
		}


		if (puntiGravi != null) {
			sql.append(" puntigravi= ?, ");
		}

		if (punteggio != 0) {
			sql.append(" punteggio= ? , ");
		}


	
		sql.append(
				"solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
				"est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
				"cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
				"user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +

				//"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, nonconformita_amministrative = ?, nonconformita_penali = ?, nonconformita = ?, descrizione1 = ?, descrizione2 = ?, descrizione3 = ? , tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

				"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, tipo_richiesta = ?, " +
				"provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, " +
				"descrizione2 = ?, descrizione3 = ?, note_altro = ?, tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? ," +
				"nucleo_ispettivo = ?, nucleo_ispettivo_due = ?, nucleo_ispettivo_tre = ?, componente_nucleo = ?, componente_nucleo_due = ?, componente_nucleo_tre = ?, testo_alimento_composto = ?, nucleo_ispettivo_quattro = ?, nucleo_ispettivo_cinque = ?, nucleo_ispettivo_sei = ?, nucleo_ispettivo_sette = ?, nucleo_ispettivo_otto = ?, nucleo_ispettivo_nove = ?, nucleo_ispettivo_dieci = ?,"+
				"componente_nucleo_quattro = ?, componente_nucleo_cinque = ?, componente_nucleo_sei = ?, componente_nucleo_sette = ?, componente_nucleo_otto = ?, componente_nucleo_nove = ?, componente_nucleo_dieci = ?"+

		"WHERE ticketid = ? ");
		/* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);

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


		if (nonConformitaFormali != null) {
			pst.setString(++i, nonConformitaFormali);
		}


		if (nonConformitaSignificative != null) {
			pst.setString(++i,nonConformitaSignificative);
		}
		pst.setString(++i,ncFormaliValutazioni);
		pst.setString(++i,ncSignificativeValutazioni);
		pst.setString(++i,ncGraviValutazioni);

		if (nonConformitaGravi != null) {
			pst.setString(++i, nonConformitaGravi);
		}


		if (puntiFormali != null) {
			pst.setString(++i, puntiFormali);
		}


		if (puntiSignificativi != null) {
			pst.setString(++i, puntiSignificativi);
		}


		if (puntiGravi != null) {
			pst.setString(++i, puntiGravi);
		}

		if (punteggio != 0) {
			pst.setInt(++i, punteggio);
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
		pst.setBoolean(++i, this.resolvable);

		pst.setNull(++i, java.sql.Types.INTEGER);

		pst.setNull(++i, java.sql.Types.INTEGER);

		pst.setString( ++i, tipo_richiesta );
		DatabaseUtils.setInt(pst, ++i, provvedimenti);
		DatabaseUtils.setInt(pst, ++i, nonconformitaAmministrative);
		DatabaseUtils.setInt(pst, ++i, nonconformitaPenali);
		DatabaseUtils.setInt(pst, ++i, nonconformita);

		pst.setString(++i, descrizione1);
		pst.setString(++i, descrizione2);
		pst.setString(++i, descrizione3);
		pst.setString(++i, this.note_altro);

		pst.setBoolean(++i, this.getTipoNonConformita());
		pst.setBoolean(++i, this.getTipoNonConformitaDue());
		pst.setBoolean(++i, this.getTipoNonConformitaTre());
		pst.setBoolean(++i, this.getTipoNonConformitaQuattro());

		pst.setInt(++i, nucleoIspettivo);
		pst.setInt(++i, nucleoIspettivoDue);
		pst.setInt(++i, nucleoIspettivoTre);
		pst.setString(++i, componenteNucleo);
		pst.setString(++i, componenteNucleoDue);
		pst.setString(++i, componenteNucleoTre);
		pst.setString(++i, testoAlimentoComposto);
		pst.setInt(++i, nucleoIspettivoQuattro);
		pst.setInt(++i, nucleoIspettivoCinque);
		pst.setInt(++i, nucleoIspettivoSei);
		pst.setInt(++i, nucleoIspettivoSette);
		pst.setInt(++i, nucleoIspettivoOtto);
		pst.setInt(++i, nucleoIspettivoNove);
		pst.setInt(++i, nucleoIspettivoDieci);
		pst.setString(++i, componenteNucleoQuattro);
		pst.setString(++i, componenteNucleoCinque);
		pst.setString(++i, componenteNucleoSei);
		pst.setString(++i, componenteNucleoSette);
		pst.setString(++i, componenteNucleoOtto);
		pst.setString(++i, componenteNucleoNove);
		pst.setString(++i, componenteNucleoDieci);

		pst.setInt(++i, id);
		/*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		    resultCount = pst.executeUpdate();
		    pst.close();

		    //controllo di



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


	public void updateNonConformita(Connection db,String[] nc_formali,String[] nc_significative,String[] nc_gravi ) throws SQLException{
		String sql="delete from salvataggio_nonconformita where idticket="+id;
		PreparedStatement pst=db.prepareStatement(sql);
		pst.execute();
		//this.insertNonConformita(db, nc_formali, nc_significative, nc_gravi);


	}

	private static void aggiornaPunteggio1(Connection db, int idNononformita) throws SQLException
	{

		String selselectIdCu = "select id_controllo_ufficiale from ticket where ticketid = ?";
		String update = "update ticket set punteggio = (select sum (punteggio) from ticket where id_controllo_ufficiale= ? and tipologia not in (3)) where ticketid = ?";
		PreparedStatement pst = db.prepareStatement(selselectIdCu);
		pst.setInt(1, idNononformita);
		ResultSet rs = pst.executeQuery();
		String idCU = "";
		if(rs.next())
		{
			idCU = rs.getString(1);
		}
		String padd = idCU;
		int id_cu = -1;
		while(idCU.startsWith("0"))
		{
			idCU = idCU.substring(1);
		}
		id_cu = Integer.parseInt(padd);
		pst =db.prepareStatement(update);

		pst.setString(1, padd);
		pst.setInt(2, id_cu);
		pst.execute();
	}

	public int update(Connection db) throws SQLException {
		int i = -1;
		try {
			db.setAutoCommit(false);
			i = this.update(db, false);
			aggiornaPunteggio(db, this.getId());

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

	public void set_NonConformitaFormali(Connection db) throws SQLException 
	{
		for (int i = 0 ; i < non_conformita_formali.size(); i++)
			non_conformita_formali.remove(non_conformita_formali.get(i));
		String sql="";


		sql = "select id,idticket,tipologia,note " +
		"from salvataggio_osservazioni " +
		"where idticket = ? and tipologia = ? order by tipologia";




		PreparedStatement pst_note = db.prepareStatement(sql);
		pst_note.setInt(1, id);
		pst_note.setInt(2, ElementoNonConformita.NC_FORMALI);
		ResultSet rs_note = pst_note.executeQuery();
		int i = 1 ;
		if ( rs_note.next() )
		{
			int ticketid = rs_note.getInt("idticket")				;
			int tipo = rs_note.getInt("tipologia")	;				;
			String note = rs_note.getString("note");
			ElementoOsservazione new_nc = new ElementoOsservazione(ticketid,i,tipo,note);
			new_nc.setId(rs_note.getInt("id"));
			new_nc.buildListggettoAudit(db);
			new_nc.buildListOsservazioni(db);
			non_conformita_formali.add(new_nc);
			puntiFormali = ""+new_nc.getListaOggettiAudit().size();
			i++;
		}




	}



	public void setNon_conformita_significative(Connection db) throws SQLException 
	{

		for (int i = 0 ; i < non_conformita_significative.size(); i++)
			non_conformita_significative.remove(non_conformita_significative.get(i));
		String sql="";


		sql = "select id,idticket,tipologia,note " +
		"from salvataggio_osservazioni " +
		"where idticket = ? and tipologia = ? order by tipologia";



		PreparedStatement pst_note = db.prepareStatement(sql);
		pst_note.setInt(1, id);
		pst_note.setInt(2, ElementoNonConformita.NC_SIGNIFICATIVE);
		ResultSet rs_note = pst_note.executeQuery();
		int i = 1 ;
		if ( rs_note.next() )
		{
			int ticketid = rs_note.getInt("idticket")				;
			int tipo = rs_note.getInt("tipologia")	;				;
			String note = rs_note.getString("note");
			ElementoOsservazione new_nc = new ElementoOsservazione(ticketid,i,tipo,note);
			new_nc.setId(rs_note.getInt("id"));
			new_nc.buildListggettoAudit(db);
			new_nc.buildListOsservazioni(db);
			non_conformita_significative.add(new_nc);
			puntiSignificativi = ""+new_nc.getListaOggettiAudit().size()*7;
			i++;
		}





	}




	public void setNon_conformita_gravi(Connection db) throws SQLException 
	{
		for (int i = 0 ; i < non_conformita_gravi.size(); i++)
			non_conformita_gravi.remove(non_conformita_gravi.get(i));
		String sql="";



		sql = "select id,idticket,tipologia,note " +
		"from salvataggio_osservazioni " +
		"where idticket = ? and tipologia = ? order by tipologia";


		PreparedStatement pst_note = db.prepareStatement(sql);
		pst_note.setInt(1, id);
		pst_note.setInt(2, ElementoNonConformita.NC_GRAVI);
		ResultSet rs_note = pst_note.executeQuery();
		int i = 1 ;
		if ( rs_note.next() )
		{
			int ticketid = rs_note.getInt("idticket")				;
			int tipo = rs_note.getInt("tipologia")	;				;
			String note = rs_note.getString("note");
			ElementoOsservazione new_nc = new ElementoOsservazione(ticketid,i,tipo,note);
			new_nc.setId(rs_note.getInt("id"));
			new_nc.buildListggettoAudit(db);
			new_nc.buildListOsservazioni(db);
			non_conformita_gravi.add(new_nc);
			puntiGravi = ""+new_nc.getListaOggettiAudit().size()*25;
			i++;
		}



	}






}

