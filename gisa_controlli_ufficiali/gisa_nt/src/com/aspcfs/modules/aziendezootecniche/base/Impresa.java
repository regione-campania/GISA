/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.aspcfs.modules.aziendezootecniche.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.SoggettoFisico;

import com.darkhorseventures.framework.beans.GenericBean;
import com.sun.javafx.beans.IDProperty;

public class Impresa extends GenericBean {

	private int id ;
	private String idFiscaleAllevamento ;
	private String ragioneSociale ;
	private Indirizzo sedeLegale ;
	private SoggettoFisico proprietario;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdFiscaleAllevamento() {
		return idFiscaleAllevamento;
	}
	public void setIdFiscaleAllevamento(String idFiscaleAllevamento) {
		this.idFiscaleAllevamento = idFiscaleAllevamento;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public Indirizzo getSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(Indirizzo sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
	public SoggettoFisico getProprietario() {
		return proprietario;
	}
	public void setProprietario(SoggettoFisico proprietario) {
		this.proprietario = proprietario;
	}
	
	public void insert(Connection db,int idUtente)
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			int idResidenza = -1 ;
		/*INSERT RESIDENZA  - INPUT : indirizzo residenza,comune residenza,cap residenza*/
		String sqlInsertResidenzaProprietario = "select * from  inserisci_opu_indirizzo_allev(?,?,?)";
		pst=db.prepareStatement(sqlInsertResidenzaProprietario);
		pst.setString(1,this.getProprietario().getIndirizzo().getVia());
		pst.setString(2,this.getProprietario().getIndirizzo().getComuneTesto());
		pst.setString(3,this.getProprietario().getIndirizzo().getCap());
		rs = pst.executeQuery();
		if(rs.next())
		{
			idResidenza = rs.getInt(1);
		}
		
		int idSoggettoFisico = -1 ;
		/*INSERT SOGGETTO FISICO . INPUT : nominativo,cf,id_residenza_proprietario */
		String sqlInsertSoggettoFisico = "select * from  inserisci_opu_soggetto_fisico_allev(?,?,?)";
		pst=db.prepareStatement(sqlInsertSoggettoFisico);
		pst.setString(1,this.getProprietario().getNome());
		pst.setString(2,this.getProprietario().getCodFiscale());
		pst.setInt(3,idResidenza);
		rs = pst.executeQuery();
		if(rs.next())
		{
			idSoggettoFisico = rs.getInt(1);
		}
		
		
		/*INSERT SEDE LEGALE . INPUT : indirizzo,comune,cap */
		String sqlInsertSedeLegale = "select * from  inserisci_opu_indirizzo_allev(?,?,?)";
		int idSedeLegale = -1 ;
		pst=db.prepareStatement(sqlInsertSedeLegale);
		pst.setString(1,this.getSedeLegale().getVia());
		pst.setString(2,this.getSedeLegale().getComuneTesto());
		pst.setString(3,this.getSedeLegale().getCap());
		rs = pst.executeQuery();
		if(rs.next())
		{
			idSedeLegale = rs.getInt(1);
		}
		
		
		/*INSERT IMPRESA . INPUT : partita_iva ,name,id_indirizzo_sede_legale ,id_proprietario,291,o.org_id*/
		String sqlInsertImpresa = "select * FROM  inserisci_opu_allevamenti(? ,?,? ,?,?,-1)";
		int idImpresa = -1 ;
		pst=db.prepareStatement(sqlInsertImpresa);
		pst.setString(1,this.getIdFiscaleAllevamento());
		pst.setString(2,this.getRagioneSociale());
		pst.setInt(3,idSedeLegale);
		pst.setInt(4,idSoggettoFisico);
		pst.setInt(5,idUtente);
		rs = pst.executeQuery();
		if(rs.next())
		{
			idImpresa = rs.getInt(1);
			this.setId(idImpresa);
		}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	
		
	}
	

	
	public void queryRecord(Connection db,int id) throws SQLException
	{
		String sql = "select * from opu_allevamenti_impresa where id =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			this.setId(id);
			this.setIdFiscaleAllevamento(rs.getString("id_fiscale_allevamento"));
			this.setRagioneSociale(rs.getString("ragione_sociale"));
			this.setSedeLegale(new Indirizzo(db, rs.getInt("id_indirizzo_legale")));
			this.setProprietario(new SoggettoFisico(db, rs.getInt("id_soggetto_fisico_proprietario")));
			
		}
	}
		
	
}
