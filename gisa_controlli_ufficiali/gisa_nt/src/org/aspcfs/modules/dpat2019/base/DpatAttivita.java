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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DpatAttivita extends GenericBean {

	private static final long serialVersionUID = 8172469522264990994L;
	
	private int id;
	private int id_piano;
	private double ui;
	private String description;
	private Boolean enabled;
	private Boolean uiCalcolabile;
	private String codiceAlias ;
	private String alias ;
	private String tipoAttivita ;
	private int asl ;
	private String codiceEsame ;
	private String tipoInserimento ;
	
	private int idSezione ;
	private String descrizioneSezione ; 
	private ArrayList<DpatIndicatore> elencoIndicatori = new ArrayList<DpatIndicatore>();
	private int codiceInterno ;
	private int ordinamento;
	private Timestamp dataScadenza ;
private int stato ;
	private String descrcizionePiano ;
	private int codicePiano ;
	
	private int codiceInternoUnivoco ;
	
	private int id_ ;
	
	private ArrayList<DpatIndicatore> storiaAttivita = new ArrayList<DpatIndicatore>();
 
	
	public ArrayList<DpatIndicatore> getStoriaAttivita() {
		return storiaAttivita;
	}
	public void setStoriaAttivita(ArrayList<DpatIndicatore> storiaAttivita) {
		this.storiaAttivita = storiaAttivita;
	}
	public String getCodiceAlias() {
		return codiceAlias;
	}
	public void setCodiceAlias(String codiceAlias) {
		this.codiceAlias = codiceAlias;
	}
	public String getDescrcizionePiano() {
		return descrcizionePiano;
	}
	public void setDescrcizionePiano(String descrcizionePiano) {
		this.descrcizionePiano = descrcizionePiano;
	}
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
	public int getCodicePiano() {
		return codicePiano;
	}
	public void setCodicePiano(int codicePiano) {
		this.codicePiano = codicePiano;
	}
	public Timestamp getDataScadenza() {
	return dataScadenza;
}
public void setDataScadenza(Timestamp dataScadenza) {
	this.dataScadenza = dataScadenza;
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



	public DpatAttivita(){}
	
	
	
	public int getCodiceInterno() {
		return codiceInterno;
	}



	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}



	public int getIdSezione() {
		return idSezione;
	}



	public void setIdSezione(int idSezione) {
		this.idSezione = idSezione;
	}



	public String getDescrizioneSezione() {
		return descrizioneSezione;
	}



	public void setDescrizioneSezione(String descrizioneSezione) {
		this.descrizioneSezione = descrizioneSezione;
	}



	public String getTipoInserimento() {
		return tipoInserimento;
	}


	public void setTipoInserimento(String tipoInserimento) {
		this.tipoInserimento = tipoInserimento;
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
	public int getAsl() {
		return asl;
	}
	public void setAsl(int asl) {
		this.asl = asl;
	}
	public String getCodiceEsame() {
		return codiceEsame;
	}
	public void setCodiceEsame(String codiceEsame) {
		this.codiceEsame = codiceEsame;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_piano() {
		return id_piano;
	}
	public void setId_piano(int id_piano) {
		this.id_piano = id_piano;
	}
	public double getUi() {
		return ui;
	}
	public void setUi(double ui) {
		this.ui = ui;
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
	public ArrayList<DpatIndicatore> getElencoIndicatori() {
		return elencoIndicatori;
	}
	public void setElencoIndicatori(ArrayList<DpatIndicatore> elencoIndicatori) {
		this.elencoIndicatori = elencoIndicatori;
	}
	
	public void buildlistIndicatori(Connection db,int id,int statoConfigurazione,int anno)
	{
		try
		{
			String sql = "" ;
			if (statoConfigurazione==2)
			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias, ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_indicatore where stato in (0,2) and anno="+anno+" and id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			
			else
				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias,ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_indicatore where stato in (1) and disabilitato = false and anno="+anno+"  and id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatIndicatore ind = new DpatIndicatore();
				ind.setId(rs.getInt("id"));
				ind.setDescription(rs.getString("description"));
				ind.setId_attivita(rs.getInt("id_attivita"));
				ind.setEnabled(rs.getBoolean("enabled"));
				ind.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				ind.setLevel(rs.getInt("level"));
				ind.setNote(rs.getString("note"));
				ind.setAlias(rs.getString("alias"));
				ind.setTipoAttivita(rs.getString("tipo_attivita"));
				ind.setCodiceEsame(rs.getString("codice_esame"));
				ind.setCodiceAlias(rs.getString("codice_alias"));
				
				if(rs.getObject("ui_calcolabile")==null){
					ind.setUiCalcolabile(false);
				}
				else{
					ind.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				}
				this.elencoIndicatori.add(ind);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void buildlistIndicatori(Connection db,int id,int statoConfigurazione)
	{
		try
		{
			String sql = "" ;
			if (statoConfigurazione==2)
			    sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_indicatore where stato in (0,2) and  id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			
			else
				sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_indicatore where stato in (1) and disabilitato = false  and id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatIndicatore ind = new DpatIndicatore();
				ind.setId(rs.getInt("id"));
				ind.setDescription(rs.getString("description"));
				ind.setId_attivita(rs.getInt("id_attivita"));
				ind.setEnabled(rs.getBoolean("enabled"));
				ind.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				ind.setLevel(rs.getInt("level"));
				ind.setNote(rs.getString("note"));
				ind.setAlias(rs.getString("alias"));
				ind.setTipoAttivita(rs.getString("tipo_attivita"));
				ind.setCodiceEsame(rs.getString("codice_esame"));
				
				if(rs.getObject("ui_calcolabile")==null){
					ind.setUiCalcolabile(false);
				}
				else{
					ind.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				}
				this.elencoIndicatori.add(ind);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void buildlistIndicatoriConfiguratore(Connection db,int id,int anno)
	{
		try
		{
			String sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select codice_alias,ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno,id_ from dpat_indicatore where disabilitato=false and anno="+anno+" and id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatIndicatore ind = new DpatIndicatore();
				ind.setId(rs.getInt("id"));
				ind.setId_(rs.getInt("id_"));
				ind.setDescription(rs.getString("description"));
				ind.setId_attivita(rs.getInt("id_attivita"));
				ind.setEnabled(rs.getBoolean("enabled"));
				ind.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				ind.setLevel(rs.getInt("level"));
				ind.setNote(rs.getString("note"));
				ind.setCodiceAlias(rs.getString("codice_alias"));
				ind.setAlias(rs.getString("alias"));
				ind.setTipoAttivita(rs.getString("tipo_attivita"));
				ind.setCodiceEsame(rs.getString("codice_esame"));
				if(rs.getObject("ui_calcolabile")==null){
					ind.setUiCalcolabile(false);
				}
				else{
					ind.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				}
				
				
				ind.setCoefficiente(new DpatCoefficiente(db,ind.getId()));
				this.elencoIndicatori.add(ind);
				
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void buildlistIndicatoriConfiguratore(Connection db,int id)
	{
		try
		{
			String sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select ordinamento, id, description, id_attivita, enabled, obiettivo_in_cu, ui_calcolabile, level, note,alias,tipo_attivita,codice_esame,data_scadenza,codice_interno from dpat_indicatore where disabilitato=false  and id_attivita="+id+")b order by  codice_interno, data_scadenza)a order by ordinamento ";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatIndicatore ind = new DpatIndicatore();
				ind.setId(rs.getInt("id"));
				ind.setDescription(rs.getString("description"));
				ind.setId_attivita(rs.getInt("id_attivita"));
				ind.setEnabled(rs.getBoolean("enabled"));
				ind.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				ind.setLevel(rs.getInt("level"));
				ind.setNote(rs.getString("note"));
				ind.setAlias(rs.getString("alias"));
				ind.setTipoAttivita(rs.getString("tipo_attivita"));
				ind.setCodiceEsame(rs.getString("codice_esame"));
				if(rs.getObject("ui_calcolabile")==null){
					ind.setUiCalcolabile(false);
				}
				else{
					ind.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				}
				
				
				ind.setCoefficiente(new DpatCoefficiente(db,ind.getId()));
				this.elencoIndicatori.add(ind);
				
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void buildListStoriaAttivita(Connection db,int codiceInterno)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		String sql = "select distinct dpat_attivita.codice_alias, s.anno,s.description as sezione,p.alias as aliaspiano,p.description as piano ,dpat_attivita.description as attivita, dpat_attivita.data_scadenza"+
" from  dpat_attivita "+ 
"join dpat_piano p on p.id_=dpat_attivita.id_piano_ and dpat_attivita.anno = p.anno  "+
"join dpat_codici_attivita cc on cc.codice_interno_univoco_attivita = dpat_attivita.codice_interno_univoco "+
"join dpat_sezione s on s.anno = p.anno and s.id  =p.id_sezione  "+
"where cc.codice_interno_univoco_attivita = ? order by s.anno,data_scadenza  ";
		
		try
		{
		pst=db.prepareStatement(sql);
		pst.setInt(1, this.codiceInternoUnivoco);
		rs=pst.executeQuery();
			while (rs.next())
			{
				DpatIndicatore ind = new DpatIndicatore();
				ind.setDescrizioneSezione(rs.getString("sezione"));
				ind.setAliasPiano(rs.getString("aliaspiano"));
				ind.setDescrcizionePiano(rs.getString("piano"));
				ind.setDescrizioneAttivita(rs.getString("attivita"));ind.setAnnoRiferimento(rs.getInt("anno"));
				ind.setDataScadenza(rs.getTimestamp("data_scadenza"));
				ind.setCodiceAlias(rs.getString("codice_alias"));
				this.getStoriaAttivita().add(ind);

			}
		}
		catch(SQLException e)
		{

		}
	}
	
	public DpatAttivita(Connection db,int id,int anno)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select distinct dpat_attivita.*,sez.description as desrizioneSezione,sez.id as sezione_id from dpat_attivita join dpat_piano on dpat_attivita.anno =dpat_piano.anno and  dpat_attivita.id_piano = dpat_piano.id join dpat_sezione sez on sez.anno=dpat_piano.anno and sez.id = dpat_piano.id_sezione where dpat_attivita.id = ? and dpat_attivita.anno="+anno+" and dpat_attivita.data_scadenza is null and dpat_piano.data_scadenza is null ";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setId_(rs.getInt("id_"));
				this.setDescription(rs.getString("description"));
				this.setId_piano(rs.getInt("id_piano"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setUi(rs.getInt("ui"));
				this.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceEsame(rs.getString("codice_esame"));
				this.setIdSezione(rs.getInt("sezione_id"));
				this.setDescrizioneSezione(rs.getString("desrizioneSezione"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setStato(rs.getInt("stato"));
				this.setDataScadenza(rs.getTimestamp("data_scadenza"));
				this.setCodicePiano(rs.getInt("codice_piano"));
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));
				this.buildlistIndicatoriConfiguratore(db, id,anno);
				this.setCodiceAlias(rs.getString("codice_alias"));
				buildListStoriaAttivita(db, anno);
				
			}
				
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public DpatAttivita(Connection db,int id)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select distinct dpat_attivita.*,sez.description as desrizioneSezione,sez.id as sezione_id from dpat_attivita join dpat_piano on dpat_attivita.anno =dpat_piano.anno and  dpat_attivita.id_piano = dpat_piano.id join dpat_sezione sez on sez.anno=dpat_piano.anno and sez.id = dpat_piano.id_sezione where dpat_attivita.id = ? and dpat_attivita.data_scadenza is null ";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setDescription(rs.getString("description"));
				this.setId_piano(rs.getInt("id_piano"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setUi(rs.getInt("ui"));
				this.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceEsame(rs.getString("codice_esame"));
				this.setIdSezione(rs.getInt("sezione_id"));
				this.setDescrizioneSezione(rs.getString("desrizioneSezione"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setStato(rs.getInt("stato"));
				this.setDataScadenza(rs.getTimestamp("data_scadenza"));
				this.setCodicePiano(rs.getInt("codice_piano"));
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));
				this.buildlistIndicatoriConfiguratore(db, id);
				
				
			}
				
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public void disabilitaAttivita(Connection db,String dataScadenza,int anno) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		String sql = 
		 "update dpat_attivita_ set data_scadenza =? from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano and  dpat_attivita_.codice_interno_univoco =? and anno = "+anno+" and dpat_attivita_.data_scadenza is null; "
		+"update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.codice_interno_univoco =? and anno = "+anno+" and dpat_piano_.data_scadenza is null;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, timeDataScadenza);
		pst.setInt(4, this.getId_piano());
		pst.execute();
		
		
	}
	
	public void disabilitaAttivita(Connection db,String dataScadenza) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		String sql = "update dpat_attivita_ set data_scadenza =? from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano and  dpat_attivita_.codice_interno_univoco =?  and dpat_attivita_.data_scadenza is null; update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.codice_interno_univoco =? and dpat_piano_.data_scadenza is null;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, timeDataScadenza);
		pst.setInt(4, this.getId_piano());
		pst.execute();
		
		
	}
	
	
	public void updateDataScadenzaRecordOld(Connection db,String dataScadenza,boolean flagDisabilita,int anno) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		
		
		String sql = "update dpat_attivita_ set data_scadenza =? from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano and s.anno="+anno+" and dpat_attivita_.codice_interno_univoco =? and dpat_attivita_.data_scadenza =?;update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.id_sezione=s.id and dpat_piano_.codice_interno =? and dpat_piano_.data_scadenza =? and s.anno="+anno+";";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, this.getDataScadenza() );
		pst.setTimestamp(4, timeDataScadenza);

		
		pst.setInt(5, this.getId_piano());
		pst.setTimestamp(6, this.dataScadenza);
		pst.execute();

		if (flagDisabilita==true)
		{
		 sql = "update dpat_attivita_ set data_scadenza =?  from dpat_piano_ p join dpat_sezione_ s on s.id = p.id_sezione where p.id = dpat_attivita_.id_piano and codice_interno =? and dpat_attivita_.data_scadenza is null and s.anno="+anno+";update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where s.id =dpat_piano_.id_sezione and  codice_interno =? and dpat_piano_.data_scadenza is null and s.anno="+anno+";";
		 pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, timeDataScadenza);

		
		pst.setInt(4, this.getId_piano());
		pst.execute();
		}
		
		
	}
	
	public void updateDataScadenzaRecordOld(Connection db,String dataScadenza,boolean flagDisabilita) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		
		
		String sql = "update dpat_attivita_ set data_scadenza =? from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano  and dpat_attivita_.codice_interno_univoco =? and dpat_attivita_.data_scadenza =?;update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where dpat_piano_.id_sezione=s.id  and dpat_piano_.id_sezione=s.id and dpat_piano_.codice_interno =? and dpat_piano_.data_scadenza =? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, this.getDataScadenza() );
		pst.setTimestamp(4, timeDataScadenza);

		
		pst.setInt(5, this.getId_piano());
		pst.setTimestamp(6, this.dataScadenza);
		pst.execute();

		if (flagDisabilita==true)
		{
		 sql = "update dpat_attivita_ set data_scadenza =?  from dpat_piano_ p join dpat_sezione_ s on s.id = p.id_sezione where p.id = dpat_attivita_.id_piano and codice_interno =? and dpat_attivita_.data_scadenza is null ;update dpat_piano_ set data_scadenza =? from dpat_sezione_ s where s.id =dpat_piano_.id_sezione and  codice_interno =? and dpat_piano_.data_scadenza is null ;";
		 pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, timeDataScadenza);

		
		pst.setInt(4, this.getId_piano());
		pst.execute();
		}
		
		
	}
	
	
	public void updateRecordDaAttivate(Connection db,int anno) throws ParseException, SQLException
	{
		
		String descrizionePiano ="";
		if (tipoAttivita.contains("ATTIVITA"))
			descrizionePiano = "ATTIVITA";
		else
			descrizionePiano ="PIANO";
		
		
		
		
		String sql = "update dpat_attivita_ set description=?,tipo_attivita=?,ui_calcolabile=?,codice_esame=?,alias=? from dpat_piano_ p join dpat_sezione_ s on s.id = p.id_sezione where  p.id =dpat_attivita_.id_piano  and codice_interno =? and data_scadenza is null and s.anno="+anno+";"
				+ "update dpat_piano_ set  alias =?,tipo_attivita=? ,description = ? from dpat_sezione_ s  where s.id = dpat_piano_.id_sezione and codice_interno =? and data_scadenza is null and s.anno="+anno+";";
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setString(1, description);
		pst.setString(2, this.getTipoAttivita());
		pst.setBoolean(3, uiCalcolabile);
		pst.setString(4, this.getCodiceEsame());
		pst.setString(5, this.getAlias());
		pst.setInt(6, this.getId());
		pst.setString(7, this.getAlias());
		pst.setString(8, this.getTipoAttivita());
		pst.setString(9, descrizionePiano + this.getAlias());

		
		pst.setInt(10, this.getId());

		pst.execute();
		
		
	}
	
	public void updateRecordDaAttivate(Connection db) throws ParseException, SQLException
	{
		
		String descrizionePiano ="";
		if (tipoAttivita.contains("ATTIVITA"))
			descrizionePiano = "ATTIVITA";
		else
			descrizionePiano ="PIANO";
		
		
		
		
		String sql = "update dpat_attivita_ set description=?,tipo_attivita=?,ui_calcolabile=?,codice_esame=?,alias=? from dpat_piano_ p join dpat_sezione_ s on s.id = p.id_sezione where  p.id =dpat_attivita_.id_piano  and codice_interno =? and data_scadenza is null ;"
				+ "update dpat_piano_ set  alias =?,tipo_attivita=? ,description = ? from dpat_sezione_ s  where s.id = dpat_piano_.id_sezione and codice_interno =? and data_scadenza is null;";
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setString(1, description);
		pst.setString(2, this.getTipoAttivita());
		pst.setBoolean(3, uiCalcolabile);
		pst.setString(4, this.getCodiceEsame());
		pst.setString(5, this.getAlias());
		pst.setInt(6, this.getId());
		pst.setString(7, this.getAlias());
		pst.setString(8, this.getTipoAttivita());
		pst.setString(9, descrizionePiano + this.getAlias());

		
		pst.setInt(10, this.getId());

		pst.execute();
		
		
	}
	
	
	
	
	public boolean insert(Connection db,DpatAttivita attivitaRiferimento,int anno)
	{
		int i = 0 ;
		
	
		
		
		try
		{
			
			
			if(attivitaRiferimento.getTipoInserimento()!=null)
			{
			switch(attivitaRiferimento.getTipoInserimento())
			{


			case "up" :
			{
				
				ordinamento = attivitaRiferimento.getOrdinamento();
					PreparedStatement pst2 = db.prepareStatement("update dpat_attivita_ set ordinamento=(ordinamento+1) where   (ordinamento>? or ordinamento=?)");
					pst2.setInt(1, attivitaRiferimento.getOrdinamento());
					pst2.setInt(2, attivitaRiferimento.getOrdinamento());
					pst2.execute();
				

				
				break ;
			}
			case "down" :
			{
				 ordinamento=attivitaRiferimento.getOrdinamento()+1;
					PreparedStatement pst2 = db.prepareStatement("update dpat_attivita_ set ordinamento=(ordinamento+1) where  (ordinamento>? )");
					pst2.setInt(1, attivitaRiferimento.getOrdinamento());
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
	
	
	public boolean update(Connection db,int id_att, int anno)
	{
		int i = 0 ;
		
		
		
		
//		String insert = "Update dpat_attivita_ set id_piano = ? ,description = ? ,ui = ? ,enabled = ? ,ui_calcolabile = ? ,alias = ? ,tipo_attivita = ? ,codice_esame = ? ,codice_interno = ? ,ordinamento = ? ,stato = ? ,codice_piano = ? ,codice_interno_univoco = ? "
//				+ " from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano and  dpat_attivita_.codice_interno_univoco =? and anno = "+anno+" and dpat_attivita_.data_scadenza is null" ;
		String insert = "Update dpat_attivita_ set description = ? ,alias = ? ,codice_esame = ? ,stato = ? , codice_interno_univoco = ? "
				+ " from dpat_piano_ p join dpat_sezione_ s on s.id= p.id_sezione  where  p.id = dpat_attivita_.id_piano and  dpat_attivita_.codice_interno_univoco =? and anno = "+anno+" and dpat_attivita_.data_scadenza is null" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(insert);
			
			
			//int code = DatabaseUtils.getNextInt(db, "dpat_attivita_", "id", 1);
			this.id = id_att ;
			//pst.setInt(++i, id_piano) ;
			pst.setString(++i, description) ;
			//pst.setDouble(++i, ui);
			//pst.setBoolean(++i, true) ;
			//pst.setBoolean(++i, true) ; 
			pst.setString(++i, alias);
			//pst.setString(++i, tipoAttivita);
			pst.setString(++i, codiceEsame);
			//if (codiceInterno>0)
			//	pst.setInt(++i, codiceInterno) ;
			//else
			//{
			//	codiceInterno=id ;
			//	pst.setInt(++i, id) ;
			//	
			//}
			//pst.setInt(++i, ordinamento) ;
			pst.setInt(++i, stato);
			//pst.setInt(++i, codicePiano);
			if (codiceInternoUnivoco>0)
				pst.setInt(++i, codiceInternoUnivoco) ;
			else
			{
				codiceInternoUnivoco=id ;
				pst.setInt(++i, id) ;
				
			}
			pst.setInt(++i, id_att);

			pst.execute();

			
			String sel = "select * from dpat_codici_attivita where codice_interno_univoco_attivita = ? and codice ilike ? and data_scadenza is null";
			pst = db.prepareStatement(sel);
			pst.setInt(1, codiceInternoUnivoco);
			pst.setString(2, codiceAlias);
			ResultSet rs = pst.executeQuery();
			
			if (!rs.next())
			{
			String insertCodice = "update  dpat_codici_attivita set data_scadenza = current_date where codice_interno_univoco_attivita =? and data_scadenza is null ;INSERT INTO dpat_codici_attivita (codice_interno_univoco_attivita,codice) values (?,?)";
			pst = db.prepareStatement(insertCodice);
			pst.setInt(1, codiceInternoUnivoco);
			pst.setInt(2, codiceInternoUnivoco);
			pst.setString(3, codiceAlias);
			pst.execute();
			}
	
			if(stato!=1)
			{
				pst = db.prepareStatement("select * from refresh_motivi_cu(?)");
				pst.setInt(1, anno);
				pst.execute();
			}


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true ;
	}

	

	public boolean insert(Connection db,int anno)
	{
		int i = 0 ;
		
		
		
		
		String insert = "insert into dpat_attivita(id,id_piano,description,ui,enabled,ui_calcolabile,alias,tipo_attivita,codice_esame,codice_interno,ordinamento,stato,codice_piano,codice_interno_univoco) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(insert);
			
			
			int code = DatabaseUtils.getNextInt(db, "dpat_attivita_", "id", 1);
			this.id = code ;
			pst.setInt(++i, code);
			pst.setInt(++i, id_piano) ;
			pst.setString(++i, description) ;
			pst.setDouble(++i, ui);
			pst.setBoolean(++i, true) ;
			pst.setBoolean(++i, true) ; 
			pst.setString(++i, alias);
			pst.setString(++i, tipoAttivita);
			pst.setString(++i, codiceEsame);
			if (codiceInterno>0)
				pst.setInt(++i, codiceInterno) ;
			else
			{
				codiceInterno=id ;
				pst.setInt(++i, id) ;
				
			}
			pst.setInt(++i, ordinamento) ;
			pst.setInt(++i, stato);
			pst.setInt(++i, codicePiano);
			if (codiceInternoUnivoco>0)
				pst.setInt(++i, codiceInternoUnivoco) ;
			else
			{
				codiceInternoUnivoco=id ;
				pst.setInt(++i, id) ;
				
			}
			pst.execute();

			
			String sel = "select * from dpat_codici_attivita where codice_interno_univoco_attivita = ? and codice ilike ? and data_scadenza is null";
			pst = db.prepareStatement(sel);
			pst.setInt(1, codiceInternoUnivoco);
			pst.setString(2, codiceAlias);
			ResultSet rs = pst.executeQuery();
			
			if (!rs.next())
			{
			String insertCodice = "update  dpat_codici_attivita set data_scadenza = current_date where codice_interno_univoco_attivita =? and data_scadenza is null ;INSERT INTO dpat_codici_attivita (codice_interno_univoco_attivita,codice) values (?,?)";
			pst = db.prepareStatement(insertCodice);
			pst.setInt(1, codiceInternoUnivoco);
			pst.setInt(2, codiceInternoUnivoco);
			pst.setString(3, codiceAlias);
			pst.execute();
			}
			
			if(stato!=1)
			{
				pst = db.prepareStatement("select * from refresh_motivi_cu(?)");
				pst.setInt(1, anno);
				pst.execute();
			}


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true ;
	}

	
	public Boolean getUiCalcolabile() {
		return uiCalcolabile;
	}
	public void setUiCalcolabile(Boolean uiCalcolabile) {
		this.uiCalcolabile = uiCalcolabile;
	}

}
