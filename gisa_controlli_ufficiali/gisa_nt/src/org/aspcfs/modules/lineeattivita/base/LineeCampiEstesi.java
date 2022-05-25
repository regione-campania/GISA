/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.lineeattivita.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class LineeCampiEstesi {

private int id;
private int idValore;
private int id_rel_stab_lp;
private String nome_campo;
private String tipo_campo;
private String label_campo;
private String valore_campo;
private boolean modificabile;
private boolean visibile;
private int indice;

private String tabellaLookup;

private String path_descrizione ="";
private int idUtenteInserimento = -1;
private Timestamp dataInserimento = null;


LinkedHashMap<Integer, LineeCampiEstesi> arrayCampi = new LinkedHashMap<Integer, LineeCampiEstesi>();

public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getId_rel_stab_lp() {
	return id_rel_stab_lp;
}


public void setId_rel_stab_lp(int id_rel_stab_lp) {
	this.id_rel_stab_lp = id_rel_stab_lp;
}


public String getNome_campo() {
	return nome_campo;
}


public void setNome_campo(String nome_campo) {
	this.nome_campo = nome_campo;
}


public String getTipo_campo() {
	return tipo_campo;
}


public void setTipo_campo(String tipo_campo) {
	this.tipo_campo = tipo_campo;
}


public String getLabel_campo() {
	return label_campo;
}


public void setLabel_campo(String label_campo) {
	this.label_campo = label_campo;
}


public String getValore_campo() {
	return valore_campo;
}


public void setValore_campo(String valore_campo) {
	this.valore_campo = valore_campo;
}


public boolean isModificabile() {
	return modificabile;
}


public void setModificabile(boolean modificabile) {
	this.modificabile = modificabile;
}


public boolean isVisibile() {
	return visibile;
}


public void setVisibile(boolean visibile) {
	this.visibile = visibile;
}

public LineeCampiEstesi (Connection db, int idRelStabLp) throws SQLException{

	PreparedStatement pst = db.prepareStatement("Select * from opu_campi_estesi_lda where id_rel_stab_lp = ? and visibile");
	pst.setInt(1, idRelStabLp);
	ResultSet rs = pst.executeQuery();
	while (rs.next()) {
		buildRecord(rs);
		arrayCampi.put(Integer.valueOf(this.getId()), this);
	}
	rs.close();
	pst.close();
	

}

public LineeCampiEstesi() throws SQLException{
	
}


public void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	  this.id_rel_stab_lp=rs.getInt("id_rel_stab_lp");
	  this.nome_campo=  rs.getString("nome_campo");
	  this.tipo_campo=  rs.getString("tipo_campo");
	  this.label_campo=  rs.getString("label_campo");
	  this.valore_campo=  rs.getString("valore_campo");
	  this.modificabile=  rs.getBoolean("modificabile");
	  this.visibile =  rs.getBoolean("visibile");
	  this.path_descrizione= rs.getString("path_descrizione");
	  this.tabellaLookup= rs.getString("tabella_lookup");
	  this.idUtenteInserimento = rs.getInt("id_utente_inserimento");
	  this.dataInserimento = rs.getTimestamp("data_inserimento");
	  this.setIdValore(rs.getInt("id_valore"));
	  try { this.indice = rs.getInt("indice"); } catch (Exception e) {}

	
}


private String generaHtml(Connection db) throws SQLException{
	
	if (tipo_campo.equalsIgnoreCase("text")){
		String text = "<input type=\"text\" id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\""+valore_campo+"\"/>";
		 return  text;
		}
	if (tipo_campo.equalsIgnoreCase("hidden")){
		String text = "<input type=\"hidden\" id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\""+valore_campo+"\"/>";
		 return  text;
		}
	if (tipo_campo.equalsIgnoreCase("data")){
		String text = "<input type=\"text\" readonly id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\""+valore_campo+"\"/>";
		String data = "<a href=\"#\" onClick=\"cal19.select2(document.forms[0]."+nome_campo+",\'anchor19\',\'dd/MM/yyyy\'); return false;\" NAME=\"anchor19\" ID=\"anchor19\"><img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>";
		return text+data;
		 
	}
	return "";
}


public String getPath_descrizione() {
	return path_descrizione;
}


public void setPath_descrizione(String path_descrizione) {
	this.path_descrizione = path_descrizione;
}


public void gestisciUpdate(String nuovoValore, int idValore, int idUtente, Connection db){
	
	
	if (this.isModificabile()){
		if (nuovoValore==null && this.getTipo_campo().equals("checkbox"))
			nuovoValore = "false";
		else if (nuovoValore!=null && this.getTipo_campo().equals("checkbox"))
			nuovoValore= "true";
		
		if (!checkValues(nuovoValore, this.getValore_campo())){
			PreparedStatement pst;
			try {
				
				String sqlSelect ="select * from linee_mobili_fields_value where id_linee_mobili_html_fields = ? and (id_opu_rel_stab_linea = ? or id_rel_stab_linea = ? ) and id= ? and enabled";
				
				pst = db.prepareStatement(sqlSelect);
				pst.setInt(1, this.getId());
				pst.setInt(2, this.getId_rel_stab_lp());
				pst.setInt(3, this.getId_rel_stab_lp());
				pst.setInt(4, idValore);
 
				System.out.println("Query select campi estesi LDA: "+pst.toString());
				ResultSet rsSelect = pst.executeQuery();
				
				int idRelStabLp = -1;
				int idOpuRelStabLp = -1;
				while (rsSelect.next()){
					if (rsSelect.getInt("id_rel_stab_linea")>0)
						idRelStabLp = rsSelect.getInt("id_rel_stab_linea");
					if (rsSelect.getInt("id_opu_rel_stab_linea")>0)
						idOpuRelStabLp = rsSelect.getInt("id_opu_rel_stab_linea");
				}
				
				if (idRelStabLp>0 || idOpuRelStabLp>0){
					
					String sqlUpdate = "update linee_mobili_fields_value set enabled = false where id_linee_mobili_html_fields = ? and id = ? and enabled ";
					
					if (idRelStabLp>0)
						sqlUpdate+=" and id_rel_stab_linea = ? ";
					if (idOpuRelStabLp>0)
						sqlUpdate+=" and id_opu_rel_stab_linea = ? ";
					
					PreparedStatement pstUpdate = db.prepareStatement(sqlUpdate);
					int i = 0;
					//pstUpdate.setString(++i, nuovoValore);
					pstUpdate.setInt(++i, this.getId());
					pstUpdate.setInt(++i, idValore);

					if (idRelStabLp>0)
						pstUpdate.setInt(++i, idRelStabLp);
					if (idOpuRelStabLp>0)
						pstUpdate.setInt(++i, idOpuRelStabLp);
					
					System.out.println("Query disable campi estesi LDA: "+pstUpdate.toString());
					if ((idRelStabLp>0 && idRelStabLp == this.getId_rel_stab_lp()) || (idOpuRelStabLp>0 && idOpuRelStabLp == this.getId_rel_stab_lp()))
						pstUpdate.executeUpdate();
				}
				
					String sqlInsert = "insert into linee_mobili_fields_value (id_linee_mobili_html_fields, id_opu_rel_stab_linea, valore_campo, id_utente_inserimento, data_inserimento, indice) values (?, ?,  ?, ?, now(), ?)";

					PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
					pstInsert.setInt(1, this.getId());
					pstInsert.setInt(2, this.getId_rel_stab_lp());
					pstInsert.setString(3, nuovoValore);
					pstInsert.setInt(4, idUtente);
					pstInsert.setInt(5, indice);
					System.out.println("Query insert campi estesi LDA: "+pstInsert.toString());
					pstInsert.executeUpdate();
				
		
			pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}


private boolean checkValues(String nuovoValore, String valore_campo2) {

	if (nuovoValore!=null){
		if (nuovoValore.equals("") || nuovoValore.equals("null"))
			nuovoValore="";
	}
		
	if (valore_campo2!=null){
		if (valore_campo2.equals("") || valore_campo2.equals("null"))
			valore_campo2="";
	}
	
	if (nuovoValore==null && valore_campo2 == null)
		return true;
	if (!nuovoValore.equals(valore_campo2))
		return false;
	
	return true;
}


public String getTabellaLookup() {
	return tabellaLookup;
}


public void setTabellaLookup(String tabella_lookup) {
	this.tabellaLookup = tabella_lookup;
}


public int getIdUtenteInserimento() {
	return idUtenteInserimento;
}


public void setIdUtenteInserimento(int idUtenteInserimento) {
	this.idUtenteInserimento = idUtenteInserimento;
}


public Timestamp getDataInserimento() {
	return dataInserimento;
}


public void setDataInserimento(Timestamp dataInserimento) {
	this.dataInserimento = dataInserimento;
}


public int getIdValore() {
	return idValore;
}


public void setIdValore(int idValore) {
	this.idValore = idValore;
}


public int getIndice() {
	return indice;
}


public void setIndice(int indice) {
	this.indice = indice;
}

}
