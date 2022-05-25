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

public class DpatIndicatore extends GenericBean {

	private static final long serialVersionUID = -3712067931478757670L;

	
	private int id_ ;
	private int id;
	private int id_attivita;
	private String description;
	private int obiettivo_in_ui;
	private int carico_in_ui;
	private Boolean enabled;
	private DpatCoefficiente coefficiente;
	private Boolean uiCalcolabile;
	private int level;
	private String note = "";
	private String alias ;
	private String tipoAttivita ;
	private int asl ;
	private String codiceEsame ;
	private String descrizioneAttivita;
	private String tipoInserimento ;
	private String tipoAttivitaPiano ;
	private int codiceInterno ;
	private int codiceInternoUnivoco ;
	
	private int codiceInternoPianoCU ;
	private String codiceInternoAttivitaCU;
	private String codiceInternoUnivocoAttivitaCU ;
	private String codiceAlias ; 
	private Timestamp dataScadenza ;
	private int ordinamento;
	
private int stato ;
	private int idSezione ;
	private int codiceAttivita ;
	private int idAttivita_ ;
	private String descrizioneSezione ;
	private int idPiano ;
	private String descrcizionePiano ;
	
	private int annoRiferimento ;
	private String aliasPiano ;
	
	private ArrayList<DpatIndicatore> storiaIndicatore = new ArrayList<DpatIndicatore>();

	
	public ArrayList<DpatIndicatore> getStoriaIndicatore() {
		return storiaIndicatore;
	}
	public void setStoriaIndicatore(ArrayList<DpatIndicatore> storiaIndicatore) {
		this.storiaIndicatore = storiaIndicatore;
	}
	public int getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	public String getAliasPiano() {
		return aliasPiano;
	}
	public void setAliasPiano(String aliasPiano) {
		this.aliasPiano = aliasPiano;
	}
	public String getCodiceAlias() {
		return codiceAlias;
	}
	public void setCodiceAlias(String codiceAlias) {
		this.codiceAlias = codiceAlias;
	}
	public int getIdPiano() {
		return idPiano;
	}
	public void setIdPiano(int idPiano) {
		this.idPiano = idPiano;
	}
	public String getDescrcizionePiano() {
		return descrcizionePiano;
	}
	public void setDescrcizionePiano(String descrcizionePiano) {
		this.descrcizionePiano = descrcizionePiano;
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
	public int getIdAttivita_() {
		return idAttivita_;
	}
	public void setIdAttivita_(int idAttivita_) {
		this.idAttivita_ = idAttivita_;
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
	public int getCodiceInternoPianoCU() {
		return codiceInternoPianoCU;
	}
	public void setCodiceInternoPianoCU(int codiceInternoPianoCU) {
		this.codiceInternoPianoCU = codiceInternoPianoCU;
	}
	public String getCodiceInternoAttivitaCU() {
		return codiceInternoAttivitaCU;
	}
	public void setCodiceInternoAttivitaCU(String codiceInternoAttivitaCU) {
		this.codiceInternoAttivitaCU = codiceInternoAttivitaCU;
	}
	public String getCodiceInternoUnivocoAttivitaCU() {
		return codiceInternoUnivocoAttivitaCU;
	}
	public void setCodiceInternoUnivocoAttivitaCU(String codiceInternoUnivocoAttivitaCU) {
		this.codiceInternoUnivocoAttivitaCU = codiceInternoUnivocoAttivitaCU;
	}
	public int getCodiceAttivita() {
		return codiceAttivita;
	}
	public void setCodiceAttivita(int codiceAttivita) {
		this.codiceAttivita = codiceAttivita;
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

	
	public int getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getTipoAttivitaPiano() {
		return tipoAttivitaPiano;
	}
	public void setTipoAttivitaPiano(String tipoAttivitaPiano) {
		this.tipoAttivitaPiano = tipoAttivitaPiano;
	}
	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}
	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
	}
	private ArrayList<DpatIndicatore> elencoIndicatori = new ArrayList<DpatIndicatore>();
	
	
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
	public int getId_attivita() {
		return id_attivita;
	}
	public void setId_attivita(int id_attivita) {
		this.id_attivita = id_attivita;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getObiettivo_in_ui() {
		return obiettivo_in_ui;
	}
	public void setObiettivo_in_cu(int obiettivo_in_ui) {
		this.obiettivo_in_ui = obiettivo_in_ui;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public DpatCoefficiente getCoefficiente() {
		return coefficiente;
	}
	public void setCoefficiente(DpatCoefficiente coefficiente) {
		this.coefficiente = coefficiente;
	}
	public int getCarico_in_ui() {
		return carico_in_ui;
	}
	public void setCarico_in_ui(int carico_in_ui) {
		this.carico_in_ui = carico_in_ui;
	}
	public Boolean getUiCalcolabile() {
		return uiCalcolabile;
	}
	public void setUiCalcolabile(Boolean uiCalcolabile) {
		this.uiCalcolabile = uiCalcolabile;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getTipoInserimento() {
		return tipoInserimento;
	}


	public void setTipoInserimento(String tipoInserimento) {
		this.tipoInserimento = tipoInserimento;
	}
	public DpatIndicatore()
	{
		
	}
	
	
	
	
	
	public boolean insert(Connection db,DpatIndicatore indicatoreRiferimento,int anno)
	{
		int i = 0 ;
		
	
		
		
		try
		{
			
			
			if(indicatoreRiferimento.getTipoInserimento()!=null)
			{
			switch(indicatoreRiferimento.getTipoInserimento())
			{


			case "up" :
			{
				
					PreparedStatement pst2 = db.prepareStatement("update dpat_indicatore_ set ordinamento=(ordinamento+1) where   (ordinamento>? or ordinamento=?)");
					pst2.setInt(1, indicatoreRiferimento.getOrdinamento());
					pst2.setInt(2, indicatoreRiferimento.getOrdinamento());
					pst2.execute();
				

				
				break ;
			}
			case "down" :
			{
				 ordinamento=indicatoreRiferimento.getOrdinamento()+1;
					PreparedStatement pst2 = db.prepareStatement("update dpat_indicatore_ set ordinamento=(ordinamento+1) where  (ordinamento>? )");
					pst2.setInt(1, indicatoreRiferimento.getOrdinamento());
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
	
	public boolean insert(Connection db,int anno)
	{
		int i = 0 ;
		
		
		
		
		String insert = "insert into dpat_indicatore(id,id_attivita,description,obiettivo_in_cu,ui_calcolabile,level,alias,tipo_attivita,codice_esame,enabled,codice_interno,ordinamento,stato,codice_attivita,codice_interno_piani_gestione_cu,codice_interno_attivita_gestione_cu,codice_interno_univoco_tipo_attivita_gestione_cu,codice_interno_univoco) values (?,?,?,?,?,?,?,?,?,true,?,?,?,?,?,?,?,?)" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(insert);
			
			
			int code = DatabaseUtils.getNextInt(db, "dpat_indicatore_", "id", 1);
			pst.setInt(++i, code);
			pst.setInt(++i, id_attivita) ;
			pst.setString(++i, description) ;
			pst.setInt(++i, obiettivo_in_ui);
			pst.setBoolean(++i, uiCalcolabile);
			pst.setInt(++i, level);
			pst.setString(++i, alias) ;
			pst.setString(++i, tipoAttivita) ;
			pst.setString(++i, codiceEsame) ;

			if(codiceInterno>0)
				pst.setInt(++i, codiceInterno);
			else
			{
				codiceInterno = code ;
				pst.setInt(++i, code);
				
			}
			pst.setInt(++i, ordinamento);
			pst.setInt(++i, stato);
			pst.setInt(++i, codiceAttivita);
			pst.setInt(++i, codiceInternoPianoCU);
			pst.setString(++i, codiceInternoAttivitaCU) ;
			pst.setString(++i, codiceInternoUnivocoAttivitaCU) ;
			if(codiceInternoUnivoco>0)
				pst.setInt(++i, codiceInternoUnivoco);
			else
			{
				codiceInternoUnivoco = code ;
				pst.setInt(++i, code);
				
			}
			
			pst.execute();
			
			
			String sel = "select * from dpat_codici_indicatore where codice_interno_univoco_indicatore = ? and codice ilike ? and data_scadenza is null";
			pst = db.prepareStatement(sel);
			pst.setInt(1, codiceInternoUnivoco);
			pst.setString(2, codiceAlias);
			ResultSet rs = pst.executeQuery();
			if (!rs.next())
			{
				
				
			insert = "update  dpat_codici_indicatore set data_scadenza = current_date where codice_interno_univoco_indicatore =? and data_scadenza is null ;insert into dpat_codici_indicatore( codice_interno_univoco_indicatore,codice) values ( ?,?)";
			pst = db.prepareStatement(insert);
			pst.setInt(1,codiceInternoUnivoco);
			pst.setInt(2,codiceInternoUnivoco);
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
	
	public DpatIndicatore(Connection db,int id,int anno)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select p.id as id_piano , p.description as descrizione_piano,s.id as id_sezione,dpat_indicatore.*,s.description as descrizione_sezione,dpat_attivita.description as desrizioneAttivita,dpat_attivita.tipo_attivita as tipo_att_piano from dpat_indicatore join dpat_attivita on dpat_indicatore.anno =dpat_attivita.anno and  dpat_attivita.id = dpat_indicatore.id_attivita join dpat_piano p on p.id = dpat_Attivita.id_piano and dpat_Attivita.anno = p.anno join dpat_sezione s on s.id = p.id_sezione and s.anno=p.anno where dpat_indicatore.id = ? and dpat_indicatore.anno="+anno+" and dpat_indicatore.disabilitato=false and dpat_attivita.disabilitato=false and p.data_scadenza is null ";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
			{
				
				this.setIdPiano(rs.getInt("id_piano"));
				this.setDescrcizionePiano(rs.getString("descrizione_piano"));
				this.setIdSezione(rs.getInt("id_sezione"));
				this.setDescrizioneSezione(rs.getString("descrizione_sezione"));
				this.setId(rs.getInt("id"));
				this.setDescription(rs.getString("description"));
				this.setId_attivita(rs.getInt("id_attivita"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				this.setLevel(rs.getInt("level"));
				this.setNote(rs.getString("note"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceAlias(rs.getString("codice_alias"));
				this.setCodiceEsame(rs.getString("codice_esame"));
				if(rs.getObject("ui_calcolabile")==null){
					this.setUiCalcolabile(false);
				}
				else{
					this.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
			}
				this.setDescrizioneAttivita(rs.getString("desrizioneAttivita"));
				this.setTipoAttivitaPiano(rs.getString("tipo_att_piano"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setDataScadenza(rs.getTimestamp("data_scadenza"));
				this.setCodiceAttivita(rs.getInt("codice_attivita"));
				this.coefficiente = new DpatCoefficiente(db,this.getCodiceInterno());
				
				this.setCodiceInternoPianoCU(rs.getInt("codice_interno_piani_gestione_cu"));
				this.setCodiceInternoAttivitaCU(rs.getString("codice_interno_attivita_gestione_cu"));
				this.setCodiceInternoUnivocoAttivitaCU(rs.getString("codice_interno_univoco_tipo_attivita_gestione_cu"));
				
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));
				
				
				
				
				try
				{
					idAttivita_ = rs.getInt("id_attivita_");
				}
				catch(Exception e)
				{
					
					
				}
				
				buildListStoriaIndicatore(db, anno);
				
		}}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public DpatIndicatore(Connection db,int id)
	{
		try
		{
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			String sl = "select p.id as id_piano , p.description as descrizione_piano,s.id as id_sezione,dpat_indicatore.*,s.description as descrizione_sezione,dpat_attivita.description as desrizioneAttivita,dpat_attivita.tipo_attivita as tipo_att_piano from dpat_indicatore join dpat_attivita on dpat_indicatore.anno =dpat_attivita.anno and  dpat_attivita.id = dpat_indicatore.id_attivita join dpat_piano p on p.id = dpat_Attivita.id_piano and dpat_Attivita.anno = p.anno join dpat_sezione s on s.id = p.id_sezione and s.anno=p.anno where dpat_indicatore.id = ?  and dpat_indicatore.disabilitato=false and dpat_attivita.disabilitato=false ";
			pst=db.prepareStatement(sl);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
			{
				
				this.setIdPiano(rs.getInt("id_piano"));
				this.setDescrcizionePiano(rs.getString("descrizione_piano"));
				this.setIdSezione(rs.getInt("id_sezione"));
				this.setDescrizioneSezione(rs.getString("descrizione_sezione"));
				this.setId(rs.getInt("id"));
				this.setDescription(rs.getString("description"));
				this.setId_attivita(rs.getInt("id_attivita"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setObiettivo_in_cu(rs.getInt("obiettivo_in_cu"));
				this.setLevel(rs.getInt("level"));
				this.setNote(rs.getString("note"));
				this.setAlias(rs.getString("alias"));
				this.setTipoAttivita(rs.getString("tipo_attivita"));
				this.setCodiceEsame(rs.getString("codice_esame"));
				if(rs.getObject("ui_calcolabile")==null){
					this.setUiCalcolabile(false);
				}
				else{
					this.setUiCalcolabile(rs.getBoolean("ui_calcolabile"));
			}
				this.setDescrizioneAttivita(rs.getString("desrizioneAttivita"));
				this.setTipoAttivitaPiano(rs.getString("tipo_att_piano"));
				this.setCodiceInterno(rs.getInt("codice_interno"));
				this.setOrdinamento(rs.getInt("ordinamento"));
				this.setDataScadenza(rs.getTimestamp("data_scadenza"));
				this.setCodiceAttivita(rs.getInt("codice_attivita"));
				this.coefficiente = new DpatCoefficiente(db,this.getCodiceInterno());
				
				this.setCodiceInternoPianoCU(rs.getInt("codice_interno_piani_gestione_cu"));
				this.setCodiceInternoAttivitaCU(rs.getString("codice_interno_attivita_gestione_cu"));
				this.setCodiceInternoUnivocoAttivitaCU(rs.getString("codice_interno_univoco_tipo_attivita_gestione_cu"));
				
				this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));
				
				
				
				
				try
				{
					idAttivita_ = rs.getInt("id_attivita_");
				}
				catch(Exception e)
				       
				{
					
				}
				
		}}
		catch(SQLException e)
		{
			
		}
	}
	
	
	
	public void updateDataScadenzaRecordOld(Connection db,String dataScadenza,boolean flagDisabilita) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		
		
		String sql = "update dpat_indicatore set data_scadenza =? where id =? and data_scadenza = ?;update dpat_coefficiente set data_scadenza = ? where id_indicatore= ? and data_scadenza = ? ;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(3, this.getDataScadenza() );
		pst.setTimestamp(4, timeDataScadenza);

		
		pst.setInt(5, this.getId());
		pst.setTimestamp(6, this.dataScadenza);
		pst.execute();

		if (flagDisabilita==true)
		{
		 sql = "update dpat_indicatore set data_scadenza =? where id =? and data_scadenza is null ;update dpat_coefficiente set data_scadenza = ? where id_indicatore= ? and data_scadenza  is null ;";
		 pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		pst.setTimestamp(4, timeDataScadenza);

		
		pst.setInt(5, this.getId());
		pst.execute();
		}
		
		
	}
	
	public void updateRecordDaAttivate(Connection db,int anno) throws ParseException, SQLException
	{
		
	
		
		
		
		String sql = "update dpat_indicatore_ set description =?,obiettivo_in_cu =?,ui_calcolabile =?,alias =?,tipo_attivita =?,codice_esame =? "
				+ " from dpat_attivita_ a join dpat_piano_ p on p.id = a.id_piano join dpat_sezione_ s on s.id = p.id_sezione "
				+ "where a.id =dpat_indicatore_.id_attivita and  codice_interno =? and data_scadenza is null and s.anno="+anno+";";
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setString(1, description);
		pst.setInt(2, obiettivo_in_ui);
		pst.setBoolean(3, uiCalcolabile);
		pst.setString(4, this.getAlias());
		
		pst.setString(5, this.getTipoAttivita());
		pst.setString(6, this.getCodiceEsame());
		
		pst.setInt(7, this.getId());

		

		pst.execute();
		
		
	}
	
	public void updateRecordDaAttivate(Connection db) throws ParseException, SQLException
	{
		
	
		
		
		
		String sql = "update dpat_indicatore_ set description =?,obiettivo_in_cu =?,ui_calcolabile =?,alias =?,tipo_attivita =?,codice_esame =? "
				+ " from dpat_attivita_ a join dpat_piano_ p on p.id = a.id_piano join dpat_sezione_ s on s.id = p.id_sezione "
				+ "where a.id =dpat_indicatore_.id_attivita and  codice_interno =? and data_scadenza is null ;";
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setString(1, description);
		pst.setInt(2, obiettivo_in_ui);
		pst.setBoolean(3, uiCalcolabile);
		pst.setString(4, this.getAlias());
		
		pst.setString(5, this.getTipoAttivita());
		pst.setString(6, this.getCodiceEsame());
		
		pst.setInt(7, this.getId());

		

		pst.execute();
		
		
	}
	
	
	public void disabilitaIndicatore(Connection db,String dataScadenza,int anno) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		String sql = "update dpat_indicatore_ set data_scadenza =? from dpat_attivita_ a join dpat_piano_ p on p.id = a.id_piano join dpat_sezione_ s on s.id = p.id_sezione where dpat_indicatore_.id_attivita=a.id and dpat_indicatore_.codice_interno_univoco =? and s.anno="+anno+";update dpat_coefficiente_ set data_scadenza = ? where id_indicatore= ?;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		
		pst.setTimestamp(3, timeDataScadenza);
		pst.setInt(4, this.getId());
		pst.execute();
		
		
	}
	
	public void disabilitaIndicatore(Connection db,String dataScadenza) throws ParseException, SQLException
	{
		Timestamp timeDataScadenza = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		timeDataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
		
		String sql = "update dpat_indicatore_ set data_scadenza =? from dpat_attivita_ a join dpat_piano_ p on p.id = a.id_piano join dpat_sezione_ s on s.id = p.id_sezione where dpat_indicatore_.id_attivita=a.id and dpat_indicatore_.codice_interno_univoco =? ;update dpat_coefficiente_ set data_scadenza = ? where id_indicatore= ?;";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, timeDataScadenza);
		pst.setInt(2, this.getId());
		
		pst.setTimestamp(3, timeDataScadenza);
		pst.setInt(4, this.getId());
		pst.execute();
		
		
	}
	
	public void buildListStoriaIndicatore(Connection db,int codiceInterno)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		String sql = "select distinct ind.codice_alias,s.anno,s.description as sezione,p.alias as aliaspiano,p.description as piano ,dpat_attivita.description as attivita,ind.alias as aliasindicatore,ind.description as indicatore,ind.data_scadenza "+
"from  dpat_indicatore ind "
+ "join dpat_attivita on dpat_attivita.id_ = ind.id_attivita_ and dpat_attivita.anno = ind.anno "+ 
"join dpat_piano p on p.id_=dpat_attivita.id_piano_ and dpat_attivita.anno = p.anno  "+
"join dpat_codici_indicatore cc on cc.codice_interno_univoco_indicatore = ind.codice_interno_univoco "+
"join dpat_sezione s on s.anno = p.anno and s.id  =p.id_sezione  "+
"where cc.codice_interno_univoco_indicatore = ? order by s.anno,data_scadenza  ";
		
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
				ind.setDescrizioneAttivita(rs.getString("attivita"));
				ind.setAlias(rs.getString("aliasindicatore"));
				ind.setDescription(rs.getString("indicatore"));
				ind.setAnnoRiferimento(rs.getInt("anno"));
				ind.setDataScadenza(rs.getTimestamp("data_scadenza"));
				ind.setCodiceAlias(rs.getString("codice_alias"));
				this.getStoriaIndicatore().add(ind);

			}
		}
		catch(SQLException e)
		{

		}
	}
	
	
	
}
