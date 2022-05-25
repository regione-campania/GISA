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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.SoggettoFisico;

public class Stabilimento {
	private int id ;
	private int idAsl;
	private String codiceAzienda;
	private Indirizzo sedeOperativa ;
	private Timestamp dataAperturaAzienda;
	private Timestamp dataChiusuraAzienda;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public Indirizzo getSedeOperativa() {
		return sedeOperativa;
	}
	public void setSedeOperativa(Indirizzo sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}
	public Timestamp getDataAperturaAzienda() {
		return dataAperturaAzienda;
	}
	public void setDataAperturaAzienda(Timestamp dataAperturaAzienda) {
		this.dataAperturaAzienda = dataAperturaAzienda;
	}
	public void setDataAperturaAzienda(String dataAperturaAzienda) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		this.dataAperturaAzienda = new Timestamp(sdf.parse(dataAperturaAzienda).getTime());
	}
	
	public void setDataChiusuraAzienda(String dataChiusuraAzienda) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dataChiusuraAzienda!=null && !"".equals(dataChiusuraAzienda) && !"null".equalsIgnoreCase(dataChiusuraAzienda))
			this.dataChiusuraAzienda = new Timestamp(sdf.parse(dataChiusuraAzienda).getTime());
	}
	
	public Timestamp getDataChiusuraAzienda() {
		return dataChiusuraAzienda;
	}
	public void setDataChiusuraAzienda(Timestamp dataChiusuraAzienda) {
		this.dataChiusuraAzienda = dataChiusuraAzienda;
	}
	
	
	public void insert(Connection db,int idUtente)
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			
			/*INSERT INDIRIZZO AZIENDA : INPUT: indrizzo_azienda,comuni1.nome,cap_azienda */
			String insertIndirizzoAzienda = "select inserisci_opu_indirizzo_allev(?,?,?)";
			int idIndirizzoAzienda =-1;
			pst=db.prepareStatement(insertIndirizzoAzienda);
			pst.setString(1,this.getSedeOperativa().getVia());
			pst.setString(2,this.getSedeOperativa().getComuneTesto());
			pst.setString(3,this.getSedeOperativa().getCap());
			rs = pst.executeQuery();
			if(rs.next())
			{
				idIndirizzoAzienda = rs.getInt(1);
			}
			
			/*INSERT  AZIENDA : INPUT: account_number,site_id,id_indirizzo_azienda,dat_apertura_azienda::timestamp,data_chius_azienda::timestamp,291 */
			
			String inserAzienda = "select  inserisci_opu_allevamenti_azienda(?,?,?,?::timestamp,?::timestamp,291)";
			int idAzienda=-1;
			pst=db.prepareStatement(inserAzienda);
			pst.setString(1,this.getCodiceAzienda());
			pst.setInt(2,getIdAsl());
			pst.setInt(3,idIndirizzoAzienda);
			pst.setTimestamp(4, this.getDataAperturaAzienda());
			pst.setTimestamp(5, this.getDataChiusuraAzienda());
			rs = pst.executeQuery();
			if(rs.next())
			{
				idAzienda = rs.getInt(1);
				this.setId(idAzienda);
			}


		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public void queryRecord(Connection db,int altId) throws SQLException
	{
		String sql = "select * from opu_allevamenti_stabilimento where id =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, altId);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			
			this.setIdAsl(rs.getInt("id_asl"));
			this.setCodiceAzienda(rs.getString("codice_azienda"));
			this.setDataAperturaAzienda(rs.getTimestamp("data_apertuta_azienda"));
			this.setDataChiusuraAzienda(rs.getTimestamp("data_chiusura_azienda"));
			this.setSedeOperativa(new Indirizzo(db, rs.getInt("id_indirizzo_azienda")));
			
			
			
		}
		
	}
	
}
