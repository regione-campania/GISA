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
import java.util.HashMap;
import java.util.Iterator;

import com.darkhorseventures.framework.beans.GenericBean;

public class DpatRisorseStrumentaliStruttura extends GenericBean {

	private int id ;
	private int idRisorsaStrumentale ;
	private int idStruttura ;
	private String descrizioneStruttura ;
	private int numAuto ;
	private int idAttrezzatureCampionamenti ;
	private int numComputerSenzaAdsl ;
	private int numComputerConAdsl ; 
	private int numNotebookNonConnessi ;
	private int numNotebookConnessi ;
	private int numTelefoni ;
	private int numTermometriTarati ;
	private int numStampanti ;
	private String altro_descrizione ;
	private int quantitaAltro ;
	private String flagSianVeterinari ;
	private String descrizioneStrutturaLunga ;
	private int codiceInternoFK;


	
	
	public int getCodiceInternoFK() {
		return codiceInternoFK;
	}
	public void setCodiceInternoFK(int codiceInternoFK) {
		this.codiceInternoFK = codiceInternoFK;
	}
	public String getDescrizioneStrutturaLunga() {
		return descrizioneStrutturaLunga;
	}
	public void setDescrizioneStrutturaLunga(String descrizioneStrutturaLunga) {
		this.descrizioneStrutturaLunga = descrizioneStrutturaLunga;
	}
	public String getFlagSianVeterinari() {
		return flagSianVeterinari;
	}
	public void setFlagSianVeterinari(String flagSianVeterinari) {
		this.flagSianVeterinari = flagSianVeterinari;
	}
	public String getDescrizioneStruttura() {
		return descrizioneStruttura;
	}
	public void setDescrizioneStruttura(String descrizioneStruttura) {
		this.descrizioneStruttura = descrizioneStruttura;
	}

	public int getIdRisorsaStrumentale() {
		return idRisorsaStrumentale;
	}
	public void setIdRisorsaStrumentale(int idRisorsaStrumentale) {
		this.idRisorsaStrumentale = idRisorsaStrumentale;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 

	public int getIdStruttura() {
		return idStruttura;
	}
	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}

	public int getNumAuto() {
		return numAuto;
	}
	public void setNumAuto(int numAuto) {
		this.numAuto = numAuto;
	}
	public int getIdAttrezzatureCampionamenti() {
		return idAttrezzatureCampionamenti;
	}
	public void setIdAttrezzatureCampionamenti(int idAttrezzatureCampionamenti) {
		this.idAttrezzatureCampionamenti = idAttrezzatureCampionamenti;
	}
	public int getNumComputerSenzaAdsl() {
		return numComputerSenzaAdsl;
	}
	public void setNumComputerSenzaAdsl(int numComputerSenzaAdsl) {
		this.numComputerSenzaAdsl = numComputerSenzaAdsl;
	}
	public int getNumComputerConAdsl() {
		return numComputerConAdsl;
	}
	public void setNumComputerConAdsl(int numComputerConAdsl) {
		this.numComputerConAdsl = numComputerConAdsl;
	}
	public int getNumNotebookNonConnessi() {
		return numNotebookNonConnessi;
	}
	public void setNumNotebookNonConnessi(int numNotebookNonConnessi) {
		this.numNotebookNonConnessi = numNotebookNonConnessi;
	}
	public int getNumNotebookConnessi() {
		return numNotebookConnessi;
	}
	public void setNumNotebookConnessi(int numNotebookConnessi) {
		this.numNotebookConnessi = numNotebookConnessi;
	}
	public int getNumTelefoni() {
		return numTelefoni;
	}
	public void setNumTelefoni(int numTelefoni) {
		this.numTelefoni = numTelefoni;
	}
	public int getNumTermometriTarati() {
		return numTermometriTarati;
	}
	public void setNumTermometriTarati(int numTermometriTarati) {
		this.numTermometriTarati = numTermometriTarati;
	}
	public int getNumStampanti() {
		return numStampanti;
	}
	public void setNumStampanti(int numStampanti) {
		this.numStampanti = numStampanti;
	}
	public String getAltro_descrizione() {
		return altro_descrizione;
	}
	public void setAltro_descrizione(String altro_descrizione) {
		this.altro_descrizione = altro_descrizione;
	}
	public int getQuantitaAltro() {
		return quantitaAltro;
	}
	public void setQuantitaAltro(int quantitaAltro) {
		this.quantitaAltro = quantitaAltro;
	}
	public void builRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		idRisorsaStrumentale = rs.getInt("id_risorse_strumentali");
		idStruttura = rs.getInt("id_struttura");
		numAuto = rs.getInt("num_auto");
		idAttrezzatureCampionamenti = rs.getInt("id_attrezzature_campionamenti");
		numComputerSenzaAdsl = rs.getInt("num_computer_senza_adsl");
		numComputerConAdsl = rs.getInt("num_computer_con_adsl");
		numNotebookNonConnessi = rs.getInt("num_notebook_non_connessi");
		numNotebookConnessi = rs.getInt("num_notebook_connessi");
		numTelefoni = rs.getInt("num_telefoni");
		numTermometriTarati = rs.getInt("num_termometri_tarati");
		numStampanti= rs.getInt("num_stampanti");
		altro_descrizione = rs.getString("altro_descrizione");
		quantitaAltro= rs.getInt("quantita_altro");
		this.setDescrizioneStruttura(rs.getString("descrizione_struttura"));
		flagSianVeterinari = rs.getString("flag_sian_veterinari");
		descrizioneStrutturaLunga=rs.getString("descrizione_lunga");
		
		codiceInternoFK = rs.getInt("codice_interno_fk");
		
		
		
	}
	public DpatRisorseStrumentaliStruttura ()
	{
	}
	public DpatRisorseStrumentaliStruttura (Connection db,int id) throws SQLException
	{
		ResultSet rs = this.queryRecord(db, id);
		if (rs.next())
		{	this.builRecord(rs);

		}

	}
	public ResultSet queryRecord(Connection db,int id)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{ 
			String sql ="SELECT id_risorse_strumentali, id_struttura, v1.stato, v1.id, v2.descrizione_lunga as descrizione_struttura " +
					" FROM dpat_risorse_strumentali_strutture v1 join oia_nodo v2 on v1.id_struttura = v2.id " +
					"where v1.id =? ;";
			pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs ;
	}

	public DpatRisorseStrumentaliStruttura (ResultSet rs) throws SQLException
	{
		this.builRecord(rs);
	}


	public void update(Connection db,int userid)
	{
		PreparedStatement pst = null ;
		
		try
		{ 
			String sql ="UPDATE dpat_risorse_strumentali_strutture " +
					"SET num_auto=?, id_attrezzature_campionamenti=?, " + 
					"num_computer_senza_adsl=?, num_computer_con_adsl=?, num_notebook_non_connessi=?,  " +
					" num_notebook_connessi=?, num_telefoni=?, num_termometri_tarati=?,  " +
					" num_stampanti=?, altro_descrizione=?, quantita_altro=? ,modified=current_timestamp,modifiedby="+userid +
					"WHERE id = ? " ;
			pst = db.prepareStatement(sql);
			pst.setInt(1, numAuto);
			pst.setInt(2, idAttrezzatureCampionamenti);
			pst.setInt(3, numComputerSenzaAdsl);
			pst.setInt(4, numComputerConAdsl);
			pst.setInt(5, numNotebookNonConnessi);
			pst.setInt(6, numNotebookConnessi);
			pst.setInt(7, numTelefoni);
			pst.setInt(8, numTermometriTarati);
			pst.setInt(9, numStampanti);
			pst.setString(10, altro_descrizione);
			pst.setInt(11, quantitaAltro);
			pst.setInt(12, id);
			pst.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}




}
