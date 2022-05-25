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
import java.util.ArrayList;

public class LivelloAggiuntivo {
	
	private int id ; 
	private int idPadre ; 
	private String nome ; 
	private boolean checked ;
	private int idIstanza = -1;
	private LivelloAggiuntivo livelloFiglio ;
	private String codiceLinea ;
	private String descrizioneLinea;
	
	
	
	public LivelloAggiuntivo(ResultSet rs)
	{
		try
		{
			this.id 				=	rs.getInt("id");
			this.idPadre 		=	rs.getInt("id_padre");
			this.nome 	=	rs.getString("valore");
			this.checked		=	rs.getBoolean("checked");
			this.idIstanza 				=	rs.getInt("id_istanza");

		}
		catch(SQLException e)
		{
			System.out.println("##ERRORE COSTRUZIONE BEAN LIVELLO AGGIUNTIVO "+e.getMessage());
		}
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdPadre() {
		return idPadre;
	}



	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public boolean isChecked() {
		return checked;
	}



	public void setChecked(boolean checked) {
		this.checked = checked;
	}



	public LivelloAggiuntivo getLivelloFiglio() {
		return livelloFiglio;
	}



	public void setLivelloFiglio(LivelloAggiuntivo livelloFiglio) {
		this.livelloFiglio = livelloFiglio;
	}



	public static ArrayList<LivelloAggiuntivo> getElencoLivelliAggiuntivi(Connection db, int idRelStabLp,
			String codice, String descrizione_linea_attivita) throws SQLException {
		ArrayList<LivelloAggiuntivo> listaLivelli = new ArrayList<LivelloAggiuntivo>();
		
		String sql = "select l.*, v.checked, v.id_istanza from master_list_configuratore_livelli_aggiuntivi l left join master_list_configuratore_livelli_aggiuntivi_values v on l.id = v.id_configuratore_livelli_aggiuntivi and v.id_istanza = ? where l.codice_univoco ilike ? order by id_padre asc";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idRelStabLp);
		pst.setString(2, codice);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()){
			LivelloAggiuntivo livello = new LivelloAggiuntivo(rs);
			
			if (livello.getIdIstanza()<=0)
				livello.setIdIstanza(idRelStabLp);
			livello.setCodiceLinea(codice);
			livello.setDescrizioneLinea(descrizione_linea_attivita);
			listaLivelli.add(livello);
		}
		
		ArrayList<LivelloAggiuntivo> alberoLivelli = new ArrayList<LivelloAggiuntivo>();
		
		for (int i = 0; i<listaLivelli.size();i++){
			LivelloAggiuntivo livello = (LivelloAggiuntivo) listaLivelli.get(i);
			if (livello.getIdPadre()>0){
				LivelloAggiuntivo livelloPadre = trovaLivelloPadre(alberoLivelli, livello.getIdPadre());
				if (livelloPadre!=null)
					livelloPadre.setLivelloFiglio(livello);
			}
			else
				alberoLivelli.add(livello);
			
		}
		
		return alberoLivelli;
	}



	public String getCodiceLinea() {
		return codiceLinea;
	}



	public void setCodiceLinea(String codiceLinea) {
		this.codiceLinea = codiceLinea;
	}



	public String getDescrizioneLinea() {
		return descrizioneLinea;
	}



	public void setDescrizioneLinea(String descrizioneLinea) {
		this.descrizioneLinea = descrizioneLinea;
	}



	private static LivelloAggiuntivo trovaLivelloPadre(ArrayList<LivelloAggiuntivo> alberoLivelli, int idPadre) {

		for (int i = 0; i< alberoLivelli.size();i++){
			LivelloAggiuntivo livello = (LivelloAggiuntivo) alberoLivelli.get(i);
			if (livello.getId() == idPadre)
				return livello;
			else if (livello.getLivelloFiglio()!=null){
				LivelloAggiuntivo livelloFiglio = livello.getLivelloFiglio();
				if (livelloFiglio.getId() == idPadre)
					return livelloFiglio;
				else if (livelloFiglio.getLivelloFiglio()!=null){
					LivelloAggiuntivo livelloFiglio2 = livelloFiglio.getLivelloFiglio();
					if (livelloFiglio2.getId() == idPadre)
						return livelloFiglio2;
					else if (livelloFiglio2.getLivelloFiglio()!=null){
						LivelloAggiuntivo livelloFiglio3 = livelloFiglio2.getLivelloFiglio();
						if (livelloFiglio3.getId() == idPadre)
							return livelloFiglio3;
				}
			}
			
		}
		}
		return null;
	}



	public int getIdIstanza() {
		return idIstanza;
	}



	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}



	public static void gestisciUpdate(Connection db, int idLivello, int idIstanza, boolean checked) throws SQLException {
		String select = "select * from master_list_configuratore_livelli_aggiuntivi_values where id_configuratore_livelli_aggiuntivi = ? and id_istanza = ?";
		PreparedStatement pst = db.prepareStatement(select);
		pst.setInt(1, idLivello);
		pst.setInt(2, idIstanza);

		ResultSet rs = pst.executeQuery();

		if (rs.next()){
		int id = rs.getInt("id");
		String update = "update master_list_configuratore_livelli_aggiuntivi_values set checked = ? where id = ?";
		PreparedStatement pstUpdate = db.prepareStatement(update);
		pstUpdate.setBoolean(1, checked);
		pstUpdate.setInt(2, id);
		pstUpdate.executeUpdate();
		}

		else {
		String insert= "insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi , id_istanza, checked) values (?, ?, ?)";
		PreparedStatement pstInsert = db.prepareStatement(insert);
		pstInsert.setInt(1, idLivello);
		pstInsert.setInt(2, idIstanza);
		pstInsert.setBoolean(3, checked);
		pstInsert.executeUpdate();

		}
		
	}



	
	
	

}
