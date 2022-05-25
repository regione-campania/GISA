/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.modules.sintesis.base.SintesisProdotto;

public class SuapMasterListSchedaAggiuntiva {


	
	private int id = -1;
	private String nome = null;
	private int idScheda = -1;
	private String titScheda = null;
	private boolean testoAggiuntivo = false;
	private Timestamp insertDate = null;
	private Timestamp disabledDate = null;
	
	private int idRelazione = -1;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdScheda() {
		return idScheda;
	}
	public void setIdScheda(int idScheda) {
		this.idScheda = idScheda;
	}
	
	public String getTitScheda() {
		return titScheda;
	}
	public void setTitScheda(String titScheda) {
		this.titScheda = titScheda;
	}
	public boolean isTestoAggiuntivo() {
		return testoAggiuntivo;
	}
	public void setTestoAggiuntivo(boolean testoAggiuntivo) {
		this.testoAggiuntivo = testoAggiuntivo;
	}
	
	public Timestamp getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
	public Timestamp getDisabledDate() {
		return disabledDate;
	}
	public void setDisabledDate(Timestamp disabledDate) {
		this.disabledDate = disabledDate;
	}
	public int getIdRelazione() {
		return idRelazione;
	}
	public void setIdRelazione(int idRelazione) {
		this.idRelazione = idRelazione;
	}
	public SuapMasterListSchedaAggiuntiva (ResultSet rs) throws SQLException{
		buildRecord(rs);
	}
	
	public SuapMasterListSchedaAggiuntiva() {
		// TODO Auto-generated constructor stub
	}
	public void buildRecord(ResultSet rs) throws SQLException{
		id = rs.getInt("id");
		idScheda = rs.getInt("id_scheda");
		titScheda = rs.getString("tit_scheda");
		nome = rs.getString("nome");
		testoAggiuntivo = rs.getBoolean("testo_aggiuntivo");
		insertDate = rs.getTimestamp("insert_date");
		disabledDate = rs.getTimestamp("disabled_date");
	}
	
	
	public static ArrayList<SuapMasterListSchedaAggiuntiva> getElencoSchedeAggiuntive(Connection db, int idRelazione,
			SuapMasterListLineaAttivita linea) throws SQLException {
			ArrayList<SuapMasterListSchedaAggiuntiva> lista = new  ArrayList<SuapMasterListSchedaAggiuntiva>();
		 
			if (linea.getSchedaSupplementare()!=null && !("").equals(linea.getSchedaSupplementare())){
			String schede[] = linea.getSchedaSupplementare().split(",");
			
			for (int i = 0; i<schede.length; i++){
				
				Float idSchedaFloat = Float.parseFloat(schede[i]);
				int idScheda = Math.round(idSchedaFloat);
				
				PreparedStatement pst = db.prepareStatement("select * from "+ MasterListImportUtil.TAB_SCHEDE_AGGIUNTIVE +" where id_scheda = ? and disabled_date is null order by nome asc");
				pst.setInt(1, idScheda);
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					SuapMasterListSchedaAggiuntiva scheda = new SuapMasterListSchedaAggiuntiva(rs);
					scheda.setIdRelazione(idRelazione);
					lista.add(scheda);		
				}
			}
			}
			return lista;
	}

}
