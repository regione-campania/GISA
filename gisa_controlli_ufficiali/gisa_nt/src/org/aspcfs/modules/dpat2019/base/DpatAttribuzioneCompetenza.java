/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;

public class DpatAttribuzioneCompetenza extends GenericBean {

	private static final long serialVersionUID = -3792227254436924892L;

	private int id;
	private int anno;
	private int id_asl;
	private int entered_by;
	private Timestamp entered;
	private int modified_by;
	private Timestamp modified;
	private ArrayList<DpatSezione> elencoSezioni = new ArrayList<DpatSezione>();
	private ArrayList<OiaNodo> elencoStrutture = new ArrayList<OiaNodo>();

	private boolean completo;
	
	private int idArea ;
	
	
	private OiaNodo strutturaAmbito ;
	
	
	public OiaNodo getStrutturaAmbito() {
		return strutturaAmbito;
	}
	public void setStrutturaAmbito(OiaNodo strutturaAmbito) {
		this.strutturaAmbito = strutturaAmbito;
	}
	public int getIdArea() {
		return idArea;
	}
	public void setIdArea(int idArea) {
		this.idArea = idArea;
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

	public ArrayList<DpatSezione> getElencoSezioni() {
		return elencoSezioni;
	}
	public void setElencoSezioni(ArrayList<DpatSezione> elencoSezioni) {
		this.elencoSezioni = elencoSezioni;
	}





	public void builRecord(int id,Connection db,SystemStatus system,int idArea){
		try
		{
			String sql = "select * from dpat where id="+id;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				this.setId(id);
				this.setAnno(rs.getInt("anno"));
				this.setIdAsl(rs.getInt("id_asl"));
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.idArea =idArea;
				this.strutturaAmbito=new OiaNodo(db,idArea);
				this.setCompleto(rs.getBoolean("completo"));
				this.buildElencoStrutture(db,rs.getInt("id_asl"),system,idArea);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}




	public ArrayList<OiaNodo> getElencoStrutture() {
		return elencoStrutture;
	}
	public void setElencoStrutture(ArrayList<OiaNodo> elencoStrutture) {
		this.elencoStrutture = elencoStrutture;
	}
	public boolean isCompleto() {
		return completo;
	}
	
	
	public void setCompleto(boolean completo) {
		this.completo = completo;
	}
	
	
	
	
	public void buildElencoStrutture(Connection db,int idAsl,SystemStatus system,int idArea){  //ESEMPIO id_padre:id_figlio:id_figlio;id_padre:id_figlio:id_figlio
		
		 
		DpatStrumentoCalcolo dsc = null;
		try {
			dsc = new DpatStrumentoCalcolo(db,idAsl,this.getAnno(), system,-1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<DpatStruttura> elencoStruttureComplesse = new ArrayList<DpatStruttura>();
		String storico="";
		
		String sql =" select * from (select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( select distinct o.data_scadenza, codice_interno_fk,id as id_struttura ,id_padre, descrizione as descrizione , id_asl,o.livello ,o.pathid||';'||o.id as pathid,o.tipologia_struttura " +
				" from dpat_strutture_asl o " +
				 " where " +
				 " o.id_asl=? and o.anno=? and (case when ? >0 then o.id_padre=?  and tipologia_struttura in (39) else 1=1 and  tipologia_struttura in (13,14) end) and ( (stato = 1 and disabilitato= false) or stato=2)  order by pathid||';'||o.id ) a ORDER BY a.codice_interno_fk, a.data_scadenza) aa order by aa.pathid ";		
		PreparedStatement pst;
		
		
		
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, this.getAnno());
			pst.setInt(3, idArea);
			pst.setInt(4, idArea);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				
				DpatStruttura ds = new DpatStruttura();
				ds.setId(rs.getInt("id_struttura"));
				ds.setDescrizione_lunga(rs.getString("descrizione"));
				ds.setTipologia_struttura(rs.getInt("tipologia_struttura"));
				ds.setId_asl(rs.getInt("id_asl"));
				ds.setId_padre(rs.getInt("id_padre"));
				ds.setCodiceInternoFK(rs.getInt("codice_interno_fk"));
				
				String sql2 = "select distinct id_indicatore,competenza_attribuita "+
						"from  dpat "+
						//"join dpat_competenze_struttura_indicatore strutt_indic on strutt_indic.id_dpat = dpat2019.id " +
						"join dpat_competenze_struttura_indicatore strutt_indic on strutt_indic.id_dpat = dpat2019.id " +
						"join dpat_strutture_asl n on n.codice_interno_fk = strutt_indic.id_struttura " +
						" where dpat2019.id = ? and n.id_asl = ? and n.n_livello>=2 and id_struttura = ?";
				pst = db.prepareStatement(sql2);
				pst.setInt(1, this.getId());
				pst.setInt(2, idAsl);
				pst.setInt(3, ds.getCodiceInternoFK());
				
				ResultSet rs2 = pst.executeQuery();
				while (rs2.next())
				{
					int idIndicatore = rs2.getInt("id_indicatore");
					boolean flag = rs2.getBoolean("competenza_attribuita");
					
						ds.getCompetenzeIndicatori().put(idIndicatore, flag);
				}
				
				
				
				elencoStrutture.add(ds);
				storico = storico+ds.getId();
			}
			

			
			
			
			this.setElencoStrutture(elencoStrutture);
//			sql = "update dpat set strutture='"+storico+"' where id="+this.getId();
//			pst = db.prepareStatement(sql);
//			pst.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
//	}
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
	
}
