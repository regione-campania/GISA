/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

public class Dpat extends GenericBean {

	private static final long serialVersionUID = -3792227254436924892L;

	private int id;
	private int anno;
	private int id_asl;
	private int entered_by;
	private Timestamp entered;
	private int modified_by;
	private Timestamp modified;
	private Boolean enabled;
	private ArrayList<DpatSezione> elencoSezioni = new ArrayList<DpatSezione>();
	private String strutture;
	private ArrayList<DpatStruttura> elencoStrutture = new ArrayList<DpatStruttura>();
	private boolean completo;
	private ArrayList<DpatSezione> elencoSezioniSplitted = new ArrayList<DpatSezione>();
	
	private boolean congelato;
	
	
	
	public void setCongelato(boolean congelato) {
		this.congelato = congelato;
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
	public int getId_asl() {
		return id_asl;
	}
	public void setId_asl(int id_asl) {
		this.id_asl = id_asl;
	}
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public ArrayList<DpatSezione> getElencoSezioni() {
		return elencoSezioni;
	}
	public void setElencoSezioni(ArrayList<DpatSezione> elencoSezioni) {
		this.elencoSezioni = elencoSezioni;
	}

		
	public ArrayList<DpatSezione> getElencoSezioniSplitted() {
		return elencoSezioniSplitted;
	}
	public void setElencoSezioniSplitted(
			ArrayList<DpatSezione> elencoSezioniSplitted) {
		this.elencoSezioniSplitted = elencoSezioniSplitted;
	}
	
	
	private int idAreaSelezionata =-1 ;
	
	
	public int getIdAreaSelezionata() {
		return idAreaSelezionata;
	}
	public void setIdAreaSelezionata(int idAreaSelezionata) {
		this.idAreaSelezionata = idAreaSelezionata;
	}
	public void buildlistSezioni(Connection db, int anno)
	{
		try
		{
			String sql = "select dpat_sezione.id, dpat_sezione.description, dpat_sezione.anno, dpat_sezione.enabled, dpat_sezione.color,dpat_sezione.codice_interno from dpat_sezione join dpat_istanza ist on dpat_sezione.id_dpat_istanza = ist.id where ist.anno="+anno+" and ist.trashed_date is null and dpat_sezione.enabled=true order by dpat_sezione.description";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatSezione sez = new DpatSezione();
				sez.setId(rs.getInt("id"));
				sez.setDescription(rs.getString("description"));
				sez.setAnno(rs.getInt("anno"));
				sez.setEnabled(rs.getBoolean("enabled"));
				sez.setBgColor((rs.getString("color")));
				sez.setCodiceInterno(rs.getInt("codice_interno"));
				this.elencoSezioni.add(sez);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void builRecord(int id,Connection db,SystemStatus system,int idAreaSelezionata){
		try
		{
			DpatStrumentoCalcolo dsc = null;
			//dsc = new DpatStrumentoCalcolo(db,idAsl,this.getAnno());
			
			String sql = "select * from dpat where id="+id;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				this.setId(id);
				this.setAnno(rs.getInt("anno"));
				this.setIdAsl(rs.getInt("id_asl"));
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.setEntered_by(rs.getInt("entered_by"));
				this.setModified_by(rs.getInt("modified_by"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setStrutture(rs.getString("strutture"));
				this.setCompleto(rs.getBoolean("completo"));
				
				
					this.buildElencoStrutture(db,rs.getInt("id_asl"),system,idAreaSelezionata);
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	public void builRecordSdc(int id,Connection db,SystemStatus system){
		try
		{
			DpatStrumentoCalcolo dsc = null;
			//dsc = new DpatStrumentoCalcolo(db,idAsl,this.getAnno());
			
			String sql = "select * from dpat where id="+id;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				this.setId(id);
				this.setAnno(rs.getInt("anno"));
				this.setIdAsl(rs.getInt("id_asl"));
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.setEntered_by(rs.getInt("entered_by"));
				this.setModified_by(rs.getInt("modified_by"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setStrutture(rs.getString("strutture"));
				this.setCompleto(rs.getBoolean("completo"));
				
				dsc = new DpatStrumentoCalcolo(db,this.getIdAsl(),this.getAnno(),system,-1);
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void builRecordAslAnno(int idAsl, int anno,Connection db){
		try
		{
			DpatStrumentoCalcolo dsc = null;
			//dsc = new DpatStrumentoCalcolo(db,idAsl,this.getAnno());
			
			String sql = "select * from dpat where id_asl="+idAsl+" and anno= "+anno;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				this.setId(rs.getInt("id"));
				this.setAnno(rs.getInt("anno"));
				this.setIdAsl(rs.getInt("id_asl"));
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.setEntered_by(rs.getInt("entered_by"));
				this.setModified_by(rs.getInt("modified_by"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setStrutture(rs.getString("strutture"));
				this.setCompleto(rs.getBoolean("completo"));
				this.setCongelato(rs.getBoolean("congelato"));
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public String getStrutture() {
		return strutture;
	}
	public void setStrutture(String strutture) {
		this.strutture = strutture;
	}
	public ArrayList<DpatStruttura> getElencoStrutture() {
		return elencoStrutture;
	}
	public void setElencoStrutture(ArrayList<DpatStruttura> elencoStrutture) {
		this.elencoStrutture = elencoStrutture;
	}
	
	public Dpat (Connection db , int idAsl , int anno,SystemStatus system,int idAreaSelezionata)
	{
		String sql = "select * from dpat where id_asl=" + idAsl + " and anno="+ anno;		
		
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
			this.setId(id);
			this.setAnno(rs.getInt("anno"));
			this.setIdAsl(rs.getInt("id_asl"));
			this.setEntered(rs.getTimestamp("entered"));
			this.setModified(rs.getTimestamp("modified"));
			this.setEntered_by(rs.getInt("entered_by"));
			this.setModified_by(rs.getInt("modified_by"));
			this.setEnabled(rs.getBoolean("enabled"));
			this.setStrutture(rs.getString("strutture"));
			this.setCompleto(rs.getBoolean("completo"));
			
			this.buildElencoStrutture(db,rs.getInt("id_asl"),system,idAreaSelezionata);
//			dsc = new DpatStrumentoCalcolo(db,this.getIdAsl(),this.getAnno(),system);
//			if (dsc.isCompleto()==true){
//				this.buildElencoStrutture(db,rs.getInt("id_asl"),system);
//			}
		}
		}
		catch(SQLException e)
		{
			System.out.println("Errore nella costruzione del dpat  "+e.getMessage());
		}
	
		
	}
	
	public Dpat ()
	{
		
	}
	
	public boolean isCongelato ()
	{
		return congelato ;
	}
	
	public boolean isCongelato (Connection db,int idAsl , int anno) throws SQLException
	{
		
		PreparedStatement pst = db.prepareStatement("select congelato from dpat where id_asl = ? and anno = ? ");
		pst.setInt(1, idAsl);
		pst.setInt(2, anno);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			congelato = rs.getBoolean(1);
			
		
		return congelato ;
	}
	
	public boolean verificaEsistenzaDpat(Connection db , int anno )
	{
		try
		{
		ResultSet rs = db.prepareStatement("select id from dpat_istanza where anno = "+anno+" and trashed_date is null").executeQuery();
		if(rs.next())
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
		
	}
	public void buildElencoStrutture(Connection db,int idAsl,SystemStatus system,int idAreaSelezionata){  //ESEMPIO id_padre:id_figlio:id_figlio;id_padre:id_figlio:id_figlio
		
	 
//			DpatStrumentoCalcolo dsc = null;
//			try {
//				dsc = new DpatStrumentoCalcolo(db,idAsl,this.getAnno(), system);
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
			ArrayList<DpatStruttura> elencoStruttureComplesse = new ArrayList<DpatStruttura>();
			String storico="";
			
			String sql ="select * from (select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from "
					+ "( select distinct o.data_scadenza,codice_interno_fk,"
					+ " id as id_struttura ,id_padre, descrizione as descrizione , "
					+ "id_asl,o.livello ,o.pathid||';'||o.id as pathid,o.tipologia_struttura " +
					" from dpat_strutture_asl o " +
					 " where " +
					 " o.id_asl=? and o.anno=? and ( (stato = 1 and disabilitato= false) or stato=2)  and case when ?::int >0 then o.codice_interno_fk=? else 1=1 end  order by pathid||';'||o.id)a ORDER BY a.codice_interno_fk, a.data_scadenza) aa order by aa.pathid";
			PreparedStatement pst;
			try {
				pst = db.prepareStatement(sql);
				pst.setInt(1, idAsl);
				pst.setInt(2, this.getAnno());
				pst.setInt(3, idAreaSelezionata);
				pst.setInt(4, idAreaSelezionata);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					DpatStruttura ds = new DpatStruttura();
					ds.setId(rs.getInt("id_struttura"));
					ds.setDescrizione_lunga(rs.getString("descrizione"));
					ds.setTipologia_struttura(rs.getInt("tipologia_struttura"));
					ds.setId_asl(rs.getInt("id_asl"));
					ds.setId_padre(rs.getInt("id_padre"));
					ds.setCodiceInternoFK(rs.getInt("codice_interno_fk"));
					elencoStrutture.add(ds);
					storico = storico+ds.getId();
				}
				

				
				
				
				this.setElencoStrutture(elencoStrutture);
//				sql = "update dpat set strutture='"+storico+"' where id="+this.getId();
//				pst = db.prepareStatement(sql);
//				pst.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
//		}
	}
	
	public void modificaElencoStrutture(){
	}
	public boolean isCompleto() {
		return completo;
	}
	public void setCompleto(boolean completo) {
		this.completo = completo;
	}
	
	
	private boolean propagaSezioniConfiguratoreDpat(Connection db,int anno,int iddpatIstanza) throws SQLException
	{
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			
			
			String insert = "insert into dpat_sezione (description,color,anno,code_lookup_sezioni_piani_monitoraggio,enabled,id_dpat_istanza) "+
			"("+
			"select distinct sez.description,sez.color,? as anno,sez.code,true,? "+
			"from lookup_piano_monitoraggio_configuratore piani "+
			"join lookup_sezioni_piani_monitoraggio sez on piani.id_sezione=sez.code "+
			"where piani.enabled and sez.enabled "+
			"order by sez.code "+
			") ";
			pst=db.prepareStatement(insert);
			pst.setInt(1, anno);
			pst.setInt(2, iddpatIstanza);
			pst.execute();
			
			
			//PROPAGAZIONE PER ALL. 6
			/*insert = "insert into dpat_attribuzione_competenze_sezione (description,color,anno,enabled,code_lookup_sezioni_piani_monitoraggio) "+
			"("+
			"select distinct sez.description,sez.color,? as anno,true,sez.code "+
			"from lookup_piano_monitoraggio_configuratore piani "+
			"join lookup_sezioni_piani_monitoraggio sez on piani.id_sezione=sez.code "+
			"where piani.enabled and sez.enabled and sez.description not ilike 'sezione e' "+
			") ";
			pst=db.prepareStatement(insert);
			pst.setInt(1, anno);
			pst.execute();
		*/
		}
		catch(SQLException e)
		{
			
			throw e ;
			
		}
		return true ;
	}
	private boolean propagaPianiConfiguratoreDpat(Connection db,int anno) throws SQLException
	{
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			
			String insert = "insert into dpat_piano(id_sezione,description,enabled,code_lookup_piano_monitoraggio_padre) " +
					"(" +
					" select id_sezione,description,enabled,idpiano" +
					" from " +
					"( " +
					" select distinct dpat_sezione.id as id_sezione ,alias as description,true as enabled,ordinamento,config.id_sezione as configsezione,config.code as idpiano " +
					" from lookup_piano_monitoraggio_configuratore config " +
					" join dpat_sezione on config.id_sezione = dpat_sezione.code_lookup_sezioni_piani_monitoraggio " +
					" where config.id_padre<=0 and config.enabled " +
					"order by config.id_sezione,ordinamento " +
					")piani " +
					") " ;
					pst=db.prepareStatement(insert);
					pst.execute();
					
					
			//PROPAGAZIONE PER ALL. 6		
			/*insert = "insert into dpat_attribuzione_competenze_piani(id_sezione,descrizione,enabled,code_lookup_piano_monitoraggio_padre) "+  
					 "( "+
					"		 select id_sezione,description,enabled,idpiano "+ 
					"		 from   "+
					"		(  "+
					"		 select distinct dpat_attribuzione_competenze_sezione.id as id_sezione ,alias as description,true as enabled,ordinamento,config.id_sezione as configsezione,config.code as idpiano "+  
					"		 from lookup_piano_monitoraggio_configuratore config  "+
					"		 join dpat_attribuzione_competenze_sezione on config.id_sezione = dpat_attribuzione_competenze_sezione.code_lookup_sezioni_piani_monitoraggio "+  
					"		 where config.id_padre<=0 and config.enabled "+
					"		order by config.id_sezione,ordinamento  "+
					"		)piani  "+
					"		)  ";
					pst=db.prepareStatement(insert);
					pst.execute();*/
		}
		catch(SQLException e)
		{
			
			throw e ;
		}
		return true ;
	}
	private boolean propagaAttivitaConfiguratoreDpat(Connection db,int anno) throws SQLException
	{
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			pst=db.prepareStatement("");
			String insert = "insert into dpat_attivita (id_piano,ui,description,enabled,ui_calcolabile) "+
					"( "+
					"select id_piano,ui,description,enabled,ui_calcolabile "+
					"from "+
					"( "+
					"select distinct config.code, p.id as id_piano ,0 as ui,config.description as description,true as enabled,false as ui_calcolabile,ordinamento,config.id_sezione as configsezione "+
					" from lookup_piano_monitoraggio_configuratore config "+
					"join dpat_sezione on config.id_sezione = dpat_sezione.code_lookup_sezioni_piani_monitoraggio "+
					"join dpat_piano p on p.id_sezione = dpat_sezione.id and config.code = p.code_lookup_piano_monitoraggio_padre "+
					" where config.id_padre<=0 and config.enabled and dpat_sezione.anno=? and config.enabled and p.enabled  "+
					" order by config.id_sezione,ordinamento "+
					")attivita "+
					"); ";
					pst=db.prepareStatement(insert);
					pst.setInt(1, anno);
					pst.execute();
					
				
			//PROPAGAZIONE PER ALL. 6		
			/*insert = "insert into dpat_attribuzione_competenze_attivita (id_dpat_attribuzione_competenze_piani,descrizione,enabled) "+
					"( "+
					"		select id_dpat_attribuzione_competenze_piani,descrizione,enabled "+
					"		from "+
					"		( "+
					"		select distinct config.code, p.id as id_dpat_attribuzione_competenze_piani, config.description as descrizione,true as enabled,ordinamento,config.id_sezione as configsezione "+ 
					"				from lookup_piano_monitoraggio_configuratore config "+
					"			join dpat_attribuzione_competenze_sezione on config.id_sezione = dpat_attribuzione_competenze_sezione.code_lookup_sezioni_piani_monitoraggio "+ 
					"				join dpat_attribuzione_competenze_piani p on p.id_sezione = dpat_attribuzione_competenze_sezione.id and config.code = p.code_lookup_piano_monitoraggio_padre "+ 
					"				where config.id_padre<=0 and config.enabled and dpat_attribuzione_competenze_sezione.anno=? and config.enabled and p.enabled  "+
					"				order by config.id_sezione,ordinamento "+
					"		)attivita "+
					"	) ";
					pst=db.prepareStatement(insert);
					pst.setInt(1, anno);
					pst.execute();*/
		}
		catch(SQLException e)
		{
			
			throw e ;
		}
		return true ;
	}
	private boolean propagaIndicatoriConfiguratoreDpat(Connection db,int anno) throws SQLException
	{
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			pst=db.prepareStatement("");
			String insert = "insert into dpat_indicatore (id_attivita,description,obiettivo_in_cu,enabled,ui_calcolabile,level,code_lookup_piano_monitoraggio_figli,note) "+
					"( "+
					"select id_attivita,description,0,true,true,0,idpiano,notepiano "+
					"from "+
					"( "+
					"select distinct att.id as id_attivita,config_ind.description,config.id_sezione,config.ordinamento,config_ind.ordinamento_figli,config_ind.code as idpiano, config_ind.note as notepiano "+
					" from lookup_piano_monitoraggio_configuratore config "+
					"join lookup_piano_monitoraggio_configuratore config_ind on config_ind.id_padre=config.code "+
					"join dpat_sezione on config.id_sezione = dpat_sezione.code_lookup_sezioni_piani_monitoraggio "+
					"join dpat_piano p on p.id_sezione = dpat_sezione.id and config.code = p.code_lookup_piano_monitoraggio_padre "+
					"join dpat_attivita att on att.id_piano = p.id "+
					" where config.id_padre<=0 and config_ind.enabled=true and config.enabled and dpat_sezione.anno=? and config.enabled and p.enabled "+
					"order by config.id_sezione,ordinamento "+
					")indicatori"+
					");";
					pst=db.prepareStatement(insert);
					pst.setInt(1, anno);
					pst.execute();
					
					
			//PROPAGAZIONE PER ALL. 6		
			/*insert = "insert into dpat_attribuzione_competenze_indicatori (id_dpat_attribuzione_competenze_attivita,descrizione,enabled,note) "+
					"( "+
					"		select id_dpat_attribuzione_competenze_attivita,descrizione,true, note "+
					"		from "+
					"		( "+
					"			select distinct att.id as id_dpat_attribuzione_competenze_attivita, config_ind.description as descrizione, config.id_sezione,config.ordinamento,config_ind.ordinamento_figli,config_ind.code as idpiano, config_ind.note as note "+ 
					"				from lookup_piano_monitoraggio_configuratore config "+
					"				join lookup_piano_monitoraggio_configuratore config_ind on config_ind.id_padre=config.code "+ 
					"				join dpat_attribuzione_competenze_sezione on config.id_sezione = dpat_attribuzione_competenze_sezione.code_lookup_sezioni_piani_monitoraggio "+ 
					"				join dpat_attribuzione_competenze_piani p on p.id_sezione = dpat_attribuzione_competenze_sezione.id and config.code = p.code_lookup_piano_monitoraggio_padre "+ 
					"				join dpat_attribuzione_competenze_attivita att on att.id_dpat_attribuzione_competenze_piani = p.id "+
					"				where config.id_padre<=0 and config_ind.enabled=true and config.enabled and dpat_attribuzione_competenze_sezione.anno=? and config.enabled and p.enabled "+ 
					"				order by config.id_sezione,ordinamento "+
					"		)indicatori "+
					"	) ";

					pst=db.prepareStatement(insert);
					pst.setInt(1, anno);
					pst.execute();*/
		}
		catch(SQLException e)
		{
			
			throw e ;
		}
		return true ;
	}
	
	
	
	public ArrayList<String> getCodiciInterniReadonly(Connection db,String tipo)
	{
		ArrayList<String> codiciInterniReadonly = new ArrayList<String>();
		try
		{
			String sql = "select * from dpat_lista_codice_interno_readonly where tipo_attivita = ? and " + 
		                 "                                                       (now() between data_blocco and data_riattivazione or " + 
		                 "                                                       (now() > data_blocco and data_riattivazione is null) or " + 
					     "                                                       (now() < data_riattivazione and data_blocco is null) or " +
		                 "                                                       (data_blocco is null and data_riattivazione is null) )";
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, tipo);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
				codiciInterniReadonly.add(rs.getString(1).toLowerCase());
			
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return codiciInterniReadonly;
	}
	
	
}
