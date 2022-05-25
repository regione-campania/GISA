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
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DpatPiano extends GenericBean {

	private static final long serialVersionUID = -6654452174052738127L;

	private int id;
	private int id_sezione;
	private String description;
	private Boolean enabled;
	private ArrayList<DpatAttivita> elencoAttivita = new ArrayList<DpatAttivita>();
	
	private String alias ;
	private String tipoAttivita ;
	private int codiceInterno ;
	private int ordinamento ;
	private int stato ;
	private int codiceInternoUnivoco ;

	private int id_ ;
	
	
	
	
	public int getId_() {
		return id_;
	}
	public void setId_(int id_) {
		this.id_ = id_;
	}
	public int getCodiceInternoUnivoco() {
		return codiceInternoUnivoco;
	}
	public void setCodiceInternoUnivoco(int codiceInternoUnivoco) {
		this.codiceInternoUnivoco = codiceInternoUnivoco;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public int getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(int ordinamento) {
		this.ordinamento = ordinamento;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_sezione() {
		return id_sezione;
	}
	public void setId_sezione(int id_sezione) {
		this.id_sezione = id_sezione;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public ArrayList<DpatAttivita> getElencoAttivita() {
		return elencoAttivita;
	}
	public void setElencoAttivita(ArrayList<DpatAttivita> elencoAttivita) {
		this.elencoAttivita = elencoAttivita;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean insert(Connection db,DpatAttivita attivitaRiferimento,int anno)
	{
		int i = 0 ;
		
	
		
		
		try
		{
			DpatPiano pianoRif = new DpatPiano(db,attivitaRiferimento.getId_piano());
			
			
			if(attivitaRiferimento.getTipoInserimento()!=null)
			{
			switch(attivitaRiferimento.getTipoInserimento())
			{


			case "up" :
			{
				
					PreparedStatement pst2 = db.prepareStatement("update dpat_piano_ set ordinamento=(ordinamento+1) where   (ordinamento>? or ordinamento=?)");
					pst2.setInt(1, pianoRif.getOrdinamento());
					pst2.setInt(2, pianoRif.getOrdinamento());
					pst2.execute();
				

				
				break ;
			}
			case "down" :
			{
				 ordinamento=pianoRif.getOrdinamento()+1;
					PreparedStatement pst2 = db.prepareStatement("update dpat_piano_ set ordinamento=(ordinamento+1) where  (ordinamento>? )");
					pst2.setInt(1, pianoRif.getOrdinamento());
					pst2.execute();
				
				break ;
			}

			}
			}
			
			this.insert(db,anno);

		


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true ;
	}
	
	
	
	public void update(Connection db,int anno,int id_piano) throws SQLException
	{
		
		int i = 0 ;
//		String inserPiano = "Update dpat_piano_ set id_sezione = ? , description  = ? , enabled  = ? , alias = ? ,tipo_attivita = ? ,codice_interno = ? ,ordinamento = ? ,stato = ? ,codice_interno_univoco = ? "
//				+ " from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.codice_interno_univoco =? and anno = "+anno+" and dpat_piano_.data_scadenza is null";
		String inserPiano = "Update dpat_piano_ set description  = ? , alias = ? ,codice_interno = ? ,stato = ? ,codice_interno_univoco = ? "
		+ " from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.codice_interno_univoco =? and anno = "+anno+" and dpat_piano_.data_scadenza is null";

		PreparedStatement pst = db.prepareStatement(inserPiano);
		//pst.setInt(++i, id_sezione);
		pst.setString(++i, description);
		//pst.setBoolean(++i, enabled);
		pst.setString(++i, alias);

		//pst.setString(++i, tipoAttivita);
		if (codiceInterno>0)
			pst.setInt(++i, codiceInterno);
		else
		{
			codiceInterno=id ;
			pst.setInt(++i, id);
		}
		//pst.setInt(++i, ordinamento);
		pst.setInt(++i, stato);
		
		if (codiceInternoUnivoco>0)
			pst.setInt(++i, codiceInternoUnivoco);
		else
		{
			codiceInternoUnivoco=id ;
			pst.setInt(++i, id);
		}

		pst.setInt(++i, id_piano);
		
		pst.execute();
		
		
		
		
		if(stato!=1)
		{
			pst = db.prepareStatement("select * from refresh_motivi_cu(?)");
			pst.setInt(1, anno);
			pst.execute();
		}

	}

	
	
	
	public void insert(Connection db,int anno) throws SQLException
	{
		
		int i = 0 ;
		String inserPiano = "insert into dpat_piano ( id,id_sezione , description , enabled , alias,tipo_attivita,codice_interno,ordinamento,stato,codice_interno_univoco) values (?,?,?,?,?,?,?,?,?,?)";
		this.id=DatabaseUtils.getNextInt(db, "dpat_piano_", "id", 1);
		PreparedStatement pst = db.prepareStatement(inserPiano);
		pst.setInt(++i, id);
		pst.setInt(++i, id_sezione);
		pst.setString(++i, description);
		pst.setBoolean(++i, enabled);
		pst.setString(++i, alias);

		pst.setString(++i, tipoAttivita);
		if (codiceInterno>0)
			pst.setInt(++i, codiceInterno);
		else
		{
			codiceInterno=id ;
			pst.setInt(++i, id);
		}
		pst.setInt(++i, ordinamento);
		pst.setInt(++i, stato);
		
		if (codiceInternoUnivoco>0)
			pst.setInt(++i, codiceInternoUnivoco);
		else
		{
			codiceInternoUnivoco=id ;
			pst.setInt(++i, id);
		}


		pst.execute();
		
		
		
		
		if(stato!=1)
		{
			pst = db.prepareStatement("select * from refresh_motivi_cu(?)");
			pst.setInt(1, anno);
			pst.execute();
		}

	}
	public void buildlistAttivita(Connection db,int id,int statoConfigurazione,int anno)
	{
		try
		{
			String sql ="" ;
//			if(statoConfigurazione==2)
//			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_assegnato, ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (0,2) and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
//			else
//				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_assegnato,ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (1) and disabilitato = false  and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
//			
			if(statoConfigurazione==2)
			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias, ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (0,2) and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			else
				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias,ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (1) and disabilitato = false  and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatAttivita att = new DpatAttivita();
				att.setId(rs.getInt("id"));
				att.setDescription(rs.getString("description"));
				att.setId_piano(rs.getInt("id_piano"));
				att.setEnabled(rs.getBoolean("enabled"));
				att.setUi(rs.getInt("ui"));
//				att.setCodiceAssegnato(rs.getString("codice_assegnato"));
				att.setCodiceAlias(rs.getString("codice_alias"));
				att.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				att.setAlias(rs.getString("alias"));
				att.setTipoAttivita(rs.getString("tipo_attivita"));
				att.setCodiceEsame(rs.getString("codice_esame"));
				this.elencoAttivita.add(att);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	/*da cancellare appena si rilascia la modifica sul codice univoco*/
	public void buildlistAttivita(Connection db,int id,int statoConfigurazione)
	{
		try
		{
			String sql ="" ;
//			if(statoConfigurazione==2)
//			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_assegnato, ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (0,2) and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
//			else
//				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_assegnato,ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (1) and disabilitato = false  and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
//			
			if(statoConfigurazione==2)
			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select  ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (0,2)  and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			else
				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where stato in (1) and disabilitato = false   and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatAttivita att = new DpatAttivita();
				att.setId(rs.getInt("id"));
				att.setDescription(rs.getString("description"));
				att.setId_piano(rs.getInt("id_piano"));
				att.setEnabled(rs.getBoolean("enabled"));
				att.setUi(rs.getInt("ui"));
//				att.setCodiceAssegnato(rs.getString("codice_assegnato"));
				att.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				att.setAlias(rs.getString("alias"));
				att.setTipoAttivita(rs.getString("tipo_attivita"));
				att.setCodiceEsame(rs.getString("codice_esame"));
				this.elencoAttivita.add(att);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*da elimiare appena si rilasciano le modifiche al dpat*/
	public void buildlistAttivitaConfiguratore(Connection db,int id)
	{
		try
		{
			String sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias ,id_,ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where disabilitato = false and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatAttivita att = new DpatAttivita();
				att.setId(rs.getInt("id"));
				att.setId_(rs.getInt("id_"));
				att.setDescription(rs.getString("description"));
				att.setId_piano(rs.getInt("id_piano"));
				att.setEnabled(rs.getBoolean("enabled"));
				att.setCodiceAlias(rs.getString("codice_alias"));
				att.setUi(rs.getInt("ui"));
				att.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				att.setAlias(rs.getString("alias"));
				att.setTipoAttivita(rs.getString("tipo_attivita"));
				att.setCodiceEsame(rs.getString("codice_esame"));
				this.elencoAttivita.add(att);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void buildlistAttivitaConfiguratore(Connection db,int id,int anno)
	{
		try
		{
			String sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias ,id_,ordinamento,id, description, id_piano, enabled, ui, ui_calcolabile,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_attivita where disabilitato = false and anno = "+anno+" and id_piano="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatAttivita att = new DpatAttivita();
				att.setId(rs.getInt("id"));
				att.setId_(rs.getInt("id_"));
				att.setDescription(rs.getString("description"));
				att.setId_piano(rs.getInt("id_piano"));
				att.setEnabled(rs.getBoolean("enabled"));
				att.setCodiceAlias(rs.getString("codice_alias"));
				att.setUi(rs.getInt("ui"));
				att.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				att.setAlias(rs.getString("alias"));
				att.setTipoAttivita(rs.getString("tipo_attivita"));
				att.setCodiceEsame(rs.getString("codice_esame"));
				this.elencoAttivita.add(att);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public int getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	
	public DpatPiano(){}
	
	public DpatPiano(Connection db,int id)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select dpat_piano.*,dpat_sezione.description as desrizioneSezione,dpat_sezione.id as sezione_id from dpat_piano join dpat_sezione on  dpat_piano.id_sezione = dpat_sezione.id  where dpat_piano.id = ? ";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setDescription(rs.getString("description"));
				this.setId_sezione(rs.getInt("id_sezione"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setStato(rs.getInt("stato"));
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));

			}
				
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public DpatPiano(Connection db,int id,int anno)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select dpat_piano.*,dpat_sezione.description as desrizioneSezione,dpat_sezione.id as sezione_id from dpat_piano join dpat_sezione on  dpat_piano.id_sezione = dpat_sezione.id  where dpat_piano.id = ? and dpat_piano.anno =? and dpat_piano.disabilitato = false";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			pst.setInt(2, anno);
			rs = pst.executeQuery();
			if (rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setDescription(rs.getString("description"));
				this.setId_sezione(rs.getInt("id_sezione"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setStato(rs.getInt("stato"));
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));

			}
				
		}
		catch(SQLException e)
		{
			
		}
	}
	
}
