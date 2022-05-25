/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LineaVariazione {
	
	private int id_rel_stab_lp;
	private int id_stato_lp;
	private String desc_stato_lp;
	private String desc_linea;
	private ArrayList<Integer> lista_cu;
	private String desc_stato_secondo_livello;
	private int id_stato_secondo_livello;
	private int id_stabilimento;
	private String DataUltimaVariazione;

	public LineaVariazione(int id_rel_stab_lp, int id_stato_lp, String desc_linea, int id_stabilimento){
		this.id_rel_stab_lp = id_rel_stab_lp;
		this.id_stato_lp = id_stato_lp;
		this.desc_linea = desc_linea;
		this.id_stabilimento = id_stabilimento;
		this.lista_cu = new ArrayList<Integer>();
	}
	
	public int getId_rel_stab_lp() {
		return id_rel_stab_lp;
	}

	public void setId_rel_stab_lp(int id_rel_stab_lp) {
		this.id_rel_stab_lp = id_rel_stab_lp;
	}

	public int getId_stato_lp() {
		return id_stato_lp;
	}

	public void setId_stato_lp(int id_stato_lp) {
		this.id_stato_lp = id_stato_lp;
	}

	public String getDesc_linea() {
		return desc_linea;
	}

	public void setDesc_linea(String desc_linea) {
		this.desc_linea = desc_linea;
	}

	public ArrayList<Integer> getLista_cu() {
		return lista_cu;
	}

	public void setLista_cu(Connection db) throws SQLException {
		
		String sql = "select idcontrollo from dbi_get_controlli_ufficiali_su_linee_produttive(?) where id_Rel_stab_lp_out = ? and idcontrollo is not null";
		
		PreparedStatement st = db.prepareStatement(sql);
        st.setInt(1, this.id_stabilimento);
        st.setInt(2, this.id_rel_stab_lp);
        System.out.println(st);

        ResultSet rs = st.executeQuery();
        int id_cu = -1;
  		while (rs.next()){
  			id_cu = rs.getInt("idcontrollo");
  			this.lista_cu.add(id_cu);
  		}
	}

	public String getDesc_stato_secondo_livello() {
		return desc_stato_secondo_livello;
	}

	public void setDesc_stato_secondo_livello(String desc_stato_secondo_livello) {
		this.desc_stato_secondo_livello = desc_stato_secondo_livello;
	}

	public int getId_stato_secondo_livello() {
		return id_stato_secondo_livello;
	}

	public void setId_stato_secondo_livello(Connection db) throws SQLException {
		String sql = "select lv.code::integer as code, lv.description::text as description "
		+ "from variazione_stato_operazioni_storico so join lookup_variazione_stato_operazioni lv on so.id_operazione = lv.code "
		+ "where so.id_stabilimento = ? "
		+ "and so.id_rel_stab_lp = ? "
		+ "and so.data_variazione is not null order by so.data desc limit 1";
		
		PreparedStatement st = db.prepareStatement(sql);
        st.setInt(1, this.id_stabilimento);
        st.setInt(2, this.id_rel_stab_lp);
        System.out.println(st);

        ResultSet rs = st.executeQuery();
        int code_stato = -1;
        String desc_stato = "";
  		while (rs.next()){
  			code_stato = rs.getInt("code");
  			desc_stato = rs.getString("description");
  		}
		this.desc_stato_secondo_livello = desc_stato;
		this.id_stato_secondo_livello = code_stato;
		
	}

	public String getDesc_stato_lp() {
		return desc_stato_lp;
	}

	public void setDesc_stato_lp(String desc_stato_lp) {
		this.desc_stato_lp = desc_stato_lp;
	}

	public void setDataUltimaVariazione(Connection db) throws SQLException {
		String sql = "select to_char(so.data_variazione, 'dd/MM/yyyy') as data_variazione "
				+ "from variazione_stato_operazioni_storico so "
				+ "where so.id_stabilimento = ? "
				+ "and so.id_rel_stab_lp = ? "
				+ "and so.data_variazione is not null order by so.data desc limit 1";
				
				PreparedStatement st = db.prepareStatement(sql);
		        st.setInt(1, this.id_stabilimento);
		        st.setInt(2, this.id_rel_stab_lp);
		        System.out.println(st);

		        ResultSet rs = st.executeQuery();
		        String data_variazione = "";
		  		while (rs.next()){
		  			data_variazione = rs.getString("data_variazione");
		  		}
				this.DataUltimaVariazione = data_variazione;
	}

	public String getDataUltimaVariazione() {
		return DataUltimaVariazione;
	}

	public void setDataUltimaVariazione(String dataUltimaVariazione) {
		DataUltimaVariazione = dataUltimaVariazione;
	}

}
