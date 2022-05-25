/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;
import org.aspcfs.utils.DatabaseUtils;
import org.exolab.castor.util.Iterator;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DpatStrumentoCalcolo extends GenericBean {
	
	private int id ;
	private int anno ;
	private int idAsl ;
	private Timestamp entered;
	private Timestamp modified;
	private int enteredby ;
	private int modifiedby ;
	private boolean isCompleto;
	private boolean propagato;
	private static final int CODICE_INTERNO_UBA= 1;
	private OiaNodo strutturaAmbito ;

	private int stato ;
		private int idStrutturaAreaSelezionata;
		
private int coefficienteUba;
	


	public int getStato() {
	return stato;
}
public void setStato(int stato) {
	this.stato = stato;
}
	public int getIdStrutturaAreaSelezionata() {
			return idStrutturaAreaSelezionata;
		}
		public void setIdStrutturaAreaSelezionata(int idStrutturaAreaSelezionata) {
			this.idStrutturaAreaSelezionata = idStrutturaAreaSelezionata;
		}
	private DpatStrumentoCalcoloStruttureList listaStrutture = new DpatStrumentoCalcoloStruttureList() ;
	
	
	public DpatStrumentoCalcoloStruttureList getListaStrutture() {
		return listaStrutture;
	}
	public void setListaStrutture(DpatStrumentoCalcoloStruttureList listaStrutture) {
		this.listaStrutture = listaStrutture;
	}
	
	 
	public boolean isPropagato() {
		return propagato;
	}
	public void setPropagato(boolean propagato) {
		this.propagato = propagato;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	public void builRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		idAsl = rs.getInt("id_asl");
		anno = rs.getInt("anno");
		entered = rs.getTimestamp("entered");
		modified = rs.getTimestamp("modified");
		enteredby = rs.getInt("enteredby");
		modifiedby = rs.getInt("modifiedby");
		isCompleto = (rs.getBoolean("completo"));
		stato =rs.getInt("stato");
		try
		{
			propagato = rs.getBoolean("propagato");
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public DpatStrumentoCalcolo (Connection db,int idAsl,int anno,boolean flag,int idArea) throws SQLException
	{
		ResultSet rs = this.queryRecord(db,idAsl,anno);
		if (rs.next())
		{
			this.builRecord(rs);
			this.setIdStrutturaAreaSelezionata(idArea);
			strutturaAmbito = new OiaNodo(db, idArea);
		}
		
	}
	
	public DpatStrumentoCalcolo (Connection db,int idAsl,int anno,SystemStatus system,int idAreaSelezionata) throws SQLException
	{
		ResultSet rs = this.queryRecord(db,idAsl,anno);
		if (rs.next())
		{
			this.builRecord(rs);
			setCoefficienteUbaFromdb(db);
			this.listaStrutture.setIdAreaSelezionata(idAreaSelezionata);
			this.listaStrutture.buildList(db, this.id,system);
			this.setIdStrutturaAreaSelezionata(idAreaSelezionata);
			if (idAreaSelezionata>0)
				strutturaAmbito = new OiaNodo(db, idAreaSelezionata);
			else
				strutturaAmbito = new OiaNodo();
				
			
		}
		
	} 
	
	
	
	public OiaNodo getStrutturaAmbito() {
		return strutturaAmbito;
	}
	public void setStrutturaAmbito(OiaNodo strutturaAmbito) {
		this.strutturaAmbito = strutturaAmbito;
	}
	public int getCoefficienteUba() {
		return coefficienteUba;
	}
	public void setCoefficienteUba(int coefficienteUba) {
		this.coefficienteUba = coefficienteUba;
	}
	public void setCoefficienteUbaFromdb(Connection db)
	{
		
		try
		{
			String sel = "select valore from parametri_configurabili_data_fine_validita where codice_interno=?";
			PreparedStatement pst =db.prepareStatement(sel);
			pst.setInt(1, CODICE_INTERNO_UBA);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				coefficienteUba=rs.getInt(1);
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	public DpatStrumentoCalcolo () throws SQLException
	{
		
	} 
	
	public DpatStrumentoCalcolo (Connection db , int id,SystemStatus system) throws SQLException
	{
		ResultSet rs = this.queryRecord(db,id);
		if (rs.next())
		{
			this.builRecord(rs);
			this.listaStrutture.buildList(db, this.id,system);
		}
	} 
		
	public DpatStrumentoCalcolo (ResultSet rs) throws SQLException
	{
		this.builRecord(rs);
		
	}
	
	public void congelaStrumentoCalcolo(Connection db, int idUtente) throws SQLException
	{
//		
		
		
		
				
		
		java.util.Iterator itStr = listaStrutture.iterator();
		while (itStr.hasNext())
		{
			OiaNodo struttura = (OiaNodo)itStr.next();
			struttura.congelaStruttura(db, idUtente);
			
			java.util.Iterator nominativi = struttura.getListaNominativi().iterator();
			
			while (nominativi.hasNext())
			{
				DpatStrumentoCalcoloNominativi nom = (DpatStrumentoCalcoloNominativi) nominativi.next();
				nom.congelaRisorsa(db);
			}
					
			
		}
		
		
		boolean allStruttureCongelate = true ;
		
		String verificaSDCStruttue = "select * from dpat_strutture_asl where stato = 0 and id_strumento_calcolo = ? and tipologia_struttura in (13,14) and disabilitato=false";
		PreparedStatement pst = db.prepareStatement(verificaSDCStruttue);
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			allStruttureCongelate  = false ;
		
		
		if (allStruttureCongelate)
		{
			
			 String update = "update dpat_strumento_calcolo set stato = 2 where id = ?" ;
		PreparedStatement pst1 = null ;
		pst1=  db.prepareStatement(update);
		pst1.setInt(1, this.getId());
		pst1.execute();
		}
		
	}
	
	public ResultSet queryRecord(Connection db,int idAsl,int anno)
	{
		PreparedStatement pst =  null ;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_strumento_calcolo where id_asl = ? and anno = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, anno);
			rs = pst.executeQuery() ;
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return rs ;
	}
	
	
	
	
	
	public ResultSet queryRecord(Connection db,int id)
	{
		PreparedStatement pst =  null ;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_strumento_calcolo where id = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery() ;
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return rs ;
	}
	
	public ResultSet queryList(Connection db,int idAsl)
	{
		PreparedStatement pst =  null ;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_strumento_calcolo where id_asl = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			rs = pst.executeQuery() ;
			
			 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return rs ;
	}
	
	
	public ArrayList<DpatStrumentoCalcolo> getList(Connection db) throws SQLException
	{ 
		ArrayList<DpatStrumentoCalcolo> lista = new ArrayList<DpatStrumentoCalcolo>();
		ResultSet rs = queryList(db, idAsl);
		while (rs.next()){
			DpatStrumentoCalcolo dpatSC = new DpatStrumentoCalcolo(rs);
			lista.add(dpatSC);
		}
		return lista ;
	}
	
	public void inserDpatStrumentoCalcolo(Connection db,ActionContext context) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
		
		String insert = "INSERT INTO dpat_strumento_calcolo(anno, id_asl, entered, modified, enteredby, modifiedby, id, riaperto) VALUES (?, ?, ?, ?, ?, ?, ?,false)";
		PreparedStatement pst = db.prepareStatement(insert);
		
		
	     id = DatabaseUtils.getNextSeq(db,context, "dpat_strumento_calcolo","id");

		int i = 0 ;
		pst.setInt(++i, anno);
		pst.setInt(++i, idAsl);
		pst.setTimestamp(++i, entered);
		pst.setTimestamp(++i, modified);
		pst.setInt(++i, enteredby);
		pst.setInt(++i, modifiedby);
		pst.setInt(++i, id);
		pst.execute();
		
		
		
		String inserStrutture = "INSERT INTO strutture_asl( id_padre, id_asl, descrizione_lunga, n_livello, entered,entered_by, modified, modified_by, trashed_date, tipologia_struttura,"+ 
            " comune, enabled, obsoleto, confermato, id_strumento_calcolo, "+
            " codice_interno_fk, nome, id_utente,mail, indirizzo, delegato, descrizione_comune, id_oia_nodo_temp,"+ 
            " data_scadenza, stato, anno,descrizione_area_struttura_complessa ) ";
		 inserStrutture +=" (select id_padre, id_asl, descrizione_struttura, n_livello, current_Timestamp,?, current_Timestamp, ?, null, tipologia_struttura, null, true, false, true, ?, "+
       " codice_interno_fk, null ,null,null, null, null, null, null,"+ 
       " data_scadenza, ?, ?,descrizione_area_struttura_complessa"+
       " from ("+
       " select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( "+
       " select oia_nodo.*  from dpat_strutture_asl oia_nodo join organization o on o.site_id = oia_nodo.id_asl and o.tipologia = 6  "+
       " LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_nodo.tipologia_struttura = tipooia.code  "+
       " where  oia_nodo.anno = ? and oia_nodo.id_Asl=? and oia_nodo.tipologia_struttura = 13  and disabilitato = false order by oia_nodo.pathid||';'||oia_nodo.id "+
       " )a ORDER BY a.codice_interno_fk, a.data_scadenza 	) aa order by aa.pathid||';'||aa.id )";
		
		pst = db.prepareStatement(inserStrutture);
		pst.setInt(1, this.enteredby);
		pst.setInt(2, this.modifiedby);
		pst.setInt(3, this.id);
		pst.setInt(4, 1);
		pst.setInt(5, anno);
		pst.setInt(6, anno-1);
		pst.setInt(7, idAsl);
		
		pst.execute();
		
		db.commit();
		
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			
		}
		finally 
		{
			db.setAutoCommit(true);
		}
		
		
	}
	
	
	
	public void inserDpatStrumentoCalcoloConfig(Connection db,ActionContext context) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
		
		String insert = "INSERT INTO dpat_strumento_calcolo_temp(anno, id_asl, entered, modified, enteredby, modifiedby, id, riaperto) VALUES (?, ?, ?, ?, ?, ?, ?,false)";
		PreparedStatement pst = db.prepareStatement(insert);
		
	     id = DatabaseUtils.getNextSeq(db,context, "dpat_strumento_calcolo_temp","id");

		
		int i = 0 ;
		pst.setInt(++i, anno);
		pst.setInt(++i, idAsl);
		pst.setTimestamp(++i, entered);
		pst.setTimestamp(++i, modified);
		pst.setInt(++i, enteredby);
		pst.setInt(++i, modifiedby);
		pst.setInt(++i, id);
		pst.execute();
		
		
		
		String sql = "insert into oia_nodo_temp ( id_padre, id_asl, nome, descrizione_lunga, n_livello, id_utente,entered, entered_by, modified, modified_by, trashed_date, tipologia_struttura, mail, comune, indirizzo, delegato, descrizione_comune, enabled,obsoleto, confermato,id_oia_nodo) " +
		"(select   id_padre, id_asl, nome, descrizione_lunga, n_livello, id_utente,entered, entered_by, modified, modified_by, trashed_date, tipologia_struttura,mail, comune, indirizzo, delegato, descrizione_comune, enabled,obsoleto, confermato,id from oia_nodo " + 
		"where id_asl = ? and obsoleto=false and confermato=true and enabled and n_livello=2 and trashed_date is null)";
		 
		 pst = db.prepareStatement(sql);
		 pst.setInt(1, idAsl);
		 pst.execute();
		
		sql = "insert into oia_nodo_temp ( id_padre, id_asl, nome, descrizione_lunga, n_livello, id_utente, entered, entered_by, modified, modified_by, trashed_date, tipologia_struttura, mail, comune, indirizzo, delegato, descrizione_comune, enabled, obsoleto, confermato,id_oia_nodo) "+
		"(select   t.id, n.id_asl, n.nome, n.descrizione_lunga, n.n_livello, n.id_utente, n.entered, n.entered_by, n.modified, n.modified_by, n.trashed_date, n.tipologia_struttura, n.mail, n.comune, n.indirizzo, n.delegato, n.descrizione_comune, n.enabled, n.obsoleto, n.confermato,n.id from oia_nodo n join oia_nodo_temp t on n.id_padre=t.id_oia_nodo "+  
		"where n.id_asl = ? and n.obsoleto=false and n.confermato=true and n.enabled and n.n_livello=3 and n.trashed_date is null)";
		
		 pst = db.prepareStatement(sql);

		pst.setInt(1, idAsl);
		 pst.execute();


		sql = "insert into dpat_strumento_calcolo_strutture_temp(id_strumento_calcolo,id_struttura,trashed_date,id_dpat_sc_strutture) "+
		"(" +
		"select ?,n.id,scs.trashed_date,scs.id "+
		"from dpat_strumento_calcolo_strutture scs "+
		"join dpat_strumento_calcolo sc on sc.id = id_strumento_calcolo "+
		"join oia_nodo_temp n on n.id_oia_nodo =id_struttura "+
		 "where  sc.id_asl=? and sc.anno=? and scs.trashed_date is null)";

		 pst = db.prepareStatement(sql);
		 pst.setInt(1, this.id);
			pst.setInt(2, idAsl);
			pst.setInt(3, anno-1);

			 pst.execute();

		sql = "insert into dpat_strumento_calcolo_nominativi_temp (id_anagrafica_nominativo,id_lookup_qualifica,,id_dpat_strumento_calcolo_strutture,trashed_date) "+
		"( "+
		"select id_anagrafica_nominativo,id_lookup_qualifica,scn.trashed_date "+
		"from dpat_strumento_calcolo_nominativi scn "+
		"join dpat_strumento_calcolo_strutture scs on scn.id_dpat_strumento_calcolo_strutture=scs.id "+
		"join dpat_strumento_calcolo_strutture_temp scstemp on scstemp.id_dpat_sc_strutture=scs.id "+
		"join dpat_strumento_calcolo sc on sc.id = scs.id_strumento_calcolo "+
		 "where  sc.id_asl=? and sc.anno=? and scs.trashed_date is null and scn.trashed_date is null "+
		")";
		 pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, anno-1);

			 pst.execute();
		
		db.commit();
		
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			
		}
		finally 
		{
			db.setAutoCommit(true);
		}
		
		
	}
	public boolean isCompleto() {
		return isCompleto;
	}
	public void setCompleto(boolean isCompleto) {
		this.isCompleto = isCompleto;
	}	
	
	
	
}
