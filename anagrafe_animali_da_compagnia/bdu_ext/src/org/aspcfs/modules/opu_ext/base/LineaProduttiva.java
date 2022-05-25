/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;


public class LineaProduttiva extends GenericBean {
	
	
	
	public final static int idAttivitaCanile = 4;  // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneCanile = 5; // da opu_relazione_attivita_produttive_aggregazioni
	
	public final static int idAggregazioneSindaco = 3; // da opu_relazione_attivita_produttive_aggregazioni
	public final static int idAttivitaSindaco = 2; // da opu_lookup_attivita_linee_produttive_aggregazioni
	
	
	public final static int idAttivitaOperatoreCommerciale = 5; // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int IdAggregazioneOperatoreCommerciale = 6;// da opu_relazione_attivita_produttive_aggregazioni
	
	public final static int idAttivitaImportatore = 3; // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneImportatore = 4; // da opu_relazione_attivita_produttive_aggregazioni
	
	
	
	public final static int idAttivitaColonia = 7; // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneColonia = 9; // da opu_relazione_attivita_produttive_aggregazioni
	
	
	public final static int idAttivitaSindacoFR = 6; // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneSindacoFR = 7; // da opu_relazione_attivita_produttive_aggregazioni
	
	
	public final static int idAttivitaPrivato = 1; // da opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazionePrivato = 1; // da opu_relazione_attivita_produttive_aggregazioni

	public LineaProduttiva() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String categoria 	;
	private String attivita 	;
	private String macrocategoria ;
	private int idMacroarea ;
	private int idCategoria 	;
	private int idAttivita 		;
	private int id ;
	private Timestamp dataInizio ;
	private Timestamp dataFine ;
	private int stato ;
	private String autorizzazione ;
	private int idRelazioneAttivita;
	 
	public int getIdRelazioneAttivita() {
		return idRelazioneAttivita;
	}
	public void setIdRelazioneAttivita(int idRelazioneAttivita) {
		this.idRelazioneAttivita = idRelazioneAttivita;
	}
	
	public void setIdRelazioneAttivita(String idRelazioneAttivita) {
		this.idRelazioneAttivita = new Integer(idRelazioneAttivita).intValue();
	}
	
	public String getAutorizzazione() {
		return autorizzazione;
	}
	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}
	public Timestamp getDataInizio() {
		
		return dataInizio;
	}
	
	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public void setDataInizio(String dataInizio) throws ParseException {
		if (dataInizio != null && ! dataInizio.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizio = new Timestamp(sdf.parse(dataInizio).getTime());

		}

	}

	public Timestamp getDataFine() {
		
		return dataFine;
	}
	
	
	
	public String getDataFineasString() {
		String dataFine = "" ;
		if (this.dataFine != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFine = sdf.format(new Date(this.dataFine.getTime()));

		}
		return dataFine;
	}
	
	public String getDataInizioasString() {
		String dataInizio = "" ;
		if (this.dataInizio != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataInizio = sdf.format(new Date(this.dataInizio.getTime()));

		}
		return dataInizio;
	}
	
	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public void setDataFine(String dataFine) throws ParseException {
		if (dataFine != null && ! dataFine.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFine = new Timestamp(sdf.parse(dataFine).getTime());

		}

	}
	
	
	
	public String getMacrocategoria() {
		return macrocategoria;
	}
	public void setMacrocategoria(String macrocategoria) {
		this.macrocategoria = macrocategoria;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public void setStato(String stato) {
		this.stato = new Integer(stato).intValue();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = new Integer(id).intValue();
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = new Integer(idCategoria).intValue();
	}
	public int getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}
	public void setIdAttivita(String idAttivita) {
		this.idAttivita = new Integer(idAttivita).intValue();
	}
	
	public int getIdMacroarea() {
		return idMacroarea;
	}
	public void setIdMacroarea(int idMacroarea) {
		this.idMacroarea = idMacroarea;
	}
	public void setIdMacroarea(String idMacroarea) {
		this.idMacroarea = new Integer(idMacroarea).intValue();
	}
	public LineaProduttiva(Connection db,int id)
	{
		queryRecord(db, id);
	}
	public void queryRecord(Connection db, int id )
	{
		String sql = 	"select distinct rslp.autorizzazione, opu_relazione_attivita_produttive_aggregazioni.id as id,opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria, " +
		"opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita " + 
		"from opu_lookup_attivita_linee_produttive_aggregazioni " + 
		"join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione " +
		"join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione " +
		"join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive " +
		"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id " +

		"where opu_relazione_attivita_produttive_aggregazioni.id = ? and rslp.trashed_date is null " +
		"order by opu_lookup_attivita_linee_produttive_aggregazioni.code" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			this.idRelazioneAttivita = id;
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}

	}
	
	public void queryRecordStabilimento(Connection db, int id )
	{
		String sql = 	"select rslp.id_stabilimento,rslp.data_inizio,rslp.data_fine,rslp.stato,opu_relazione_attivita_produttive_aggregazioni.id,opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria, " +
		"opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita " + 
		"from opu_lookup_attivita_linee_produttive_aggregazioni " + 
		"join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione " +
		"join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione " +
		"join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive " +
		"left opu_join relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id " +
		"where opu_lookup_attivita_linee_produttive_aggregazioni.code = ?"  +

		
		"order by lookup_attivita_linee_produttive_aggregazioni.code" ;

		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				buildRecordStabilimento(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}

	}

	public void buildRecord(ResultSet rs) throws SQLException
	{
		try
		{
			this.id = rs.getInt("id");
			this.macrocategoria = rs.getString("macro");
			this.idMacroarea = rs.getInt("id_macrocategoria");
			this.idAttivita = rs.getInt("id_attivita");
			this.idCategoria = rs.getInt("id_categoria");
			this.categoria = rs.getString("aggregazione");
			this.attivita = rs.getString("attivita");
			//this.autorizzazione = rs.getString("autorizzazione");
			
			
		}
		catch(SQLException e)
		{
			throw (e) ;
		}
	}
	public void buildRecordStabilimento(ResultSet rs) throws SQLException
	{
		try
		{
			//this.id = rs.getInt("id_relazione_attivita");
			this.id = rs.getInt("id");
			this.macrocategoria = rs.getString("macro");
			this.idMacroarea = rs.getInt("id_macrocategoria");
			this.idAttivita = rs.getInt("id_attivita");
			this.idCategoria = rs.getInt("id_categoria");
			this.categoria = rs.getString("aggregazione");
			this.attivita = rs.getString("attivita");
			this.dataInizio = rs.getTimestamp("data_inizio");
			this.setDataFine(rs.getTimestamp("data_fine"));
			this.stato = rs.getInt("stato");
			this.autorizzazione = rs.getString("autorizzazione");
			this.idRelazioneAttivita=rs.getInt("id_relazione_attivita");

		}
		catch(SQLException e)
		{
			throw (e) ;
		}
	}
	
	public LineaProduttiva(ResultSet rs) throws SQLException
	{
		buildRecord(rs);
	}
	
	public LineaProduttiva(ResultSet rs,boolean f) throws SQLException
	{
		buildRecordStabilimento(rs);
	}


	


}
