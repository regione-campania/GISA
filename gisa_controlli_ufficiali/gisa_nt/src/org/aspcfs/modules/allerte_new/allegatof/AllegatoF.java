/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.allegatof;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class AllegatoF  extends GenericBean {

	private String asl = null;
	private String codiceAllerta = null;
	private String numeroNotifica = null;
	private String tipoNotifica = null;
	private String rischio = null;
	private String prodotto = null;
	private String denominazioneProdotto = null;
	private String produttore = null;
	private String lotto = null;
	private Timestamp dataScadenza = null;
	private String unitaMisura = null;
	private AllegatoFNote allegatoNote = null;
	private boolean hasListe = false;
	private ArrayList<ListaDistribuzione> liste = new ArrayList<ListaDistribuzione>();
	
	public String getCodiceAllerta() {
		return codiceAllerta;
	}
	public void setCodiceAllerta(String codiceAllerta) {
		this.codiceAllerta = codiceAllerta;
	}
	public String getNumeroNotifica() {
		return numeroNotifica;
	}
	public void setNumeroNotifica(String numeroNotifica) {
		this.numeroNotifica = numeroNotifica;
	}
	public String getTipoNotifica() {
		return tipoNotifica;
	}
	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}
	public String getRischio() {
		return rischio;
	}
	public void setRischio(String rischio) {
		this.rischio = rischio;
	}
	public String getProdotto() {
		return prodotto;
	}
	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}
	public String getDenominazioneProdotto() {
		return denominazioneProdotto;
	}
	public void setDenominazioneProdotto(String denominazioneProdotto) {
		this.denominazioneProdotto = denominazioneProdotto;
	}
	public String getProduttore() {
		return produttore;
	}
	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}
	public String getLotto() {
		return lotto;
	}
	public void setLotto(String lotto) {
		this.lotto = lotto;
	}
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getUnitaMisura() {
		if (unitaMisura==null)
			return "Kg";
		return unitaMisura;
	}
	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	
	public ArrayList<ListaDistribuzione> getListe() {
		return liste;
	}
	public void setListe(ArrayList<ListaDistribuzione> liste) {
		this.liste = liste;
	}
	
	
	public void buildAllegatoF(Connection db, int idAllerta, int idAsl) throws SQLException{
		
		String sqlAllerta = "select * from allerte_f_allerta(?)";
		PreparedStatement pstAllerta = db.prepareStatement(sqlAllerta);
		pstAllerta.setInt(1, idAllerta);
		ResultSet rsAllerta= pstAllerta.executeQuery();
		
		if (rsAllerta.next()){
			buildRecordDatiAllerta(rsAllerta);
			
			allegatoNote = new AllegatoFNote(db, idAllerta, idAsl);
			
			String sqlListe = "select * from allerte_ldd where id_allerta =? order by id asc";
			PreparedStatement pstListe = db.prepareStatement(sqlListe);
			pstListe.setInt(1, idAllerta);
			ResultSet rsListe= pstListe.executeQuery();
			
			int cont=0;
			while (rsListe.next()){
				cont++;
				int idLista = rsListe.getInt("id");
				ListaDistribuzione lista = new ListaDistribuzione(db, idLista, idAsl);
				liste.add(lista);
			}
			if (cont==0){
				ListaDistribuzione lista = new ListaDistribuzione(db, -1, idAsl, idAllerta);
				liste.add(lista);
			}
		}
	}
	private void buildRecordDatiAllerta(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		
		codiceAllerta = rs.getString("codice_allerta");
		tipoNotifica = rs.getString("tipo_notifica");
		numeroNotifica = rs.getString("numero_notifica");
		rischio = rs.getString("azione_non_conforme");
		prodotto = rs.getString("prodotto");
		denominazioneProdotto = rs.getString("denominazione_prodotto");
		produttore = rs.getString("fabbricante_produttore");
		lotto = rs.getString("numero_lotto");
		dataScadenza = rs.getTimestamp("data_scadenza_allerta");
		unitaMisura = rs.getString("unita_misura");
		hasListe = rs.getBoolean("liste");
		
	}
	public String getAsl() {
		return asl;
	}
	public void setAsl(String asl) {
		this.asl = asl;
	}
	public AllegatoFNote getAllegatoNote() {
		return allegatoNote;
	}
	public void setAllegatoNote(AllegatoFNote allegatoNote) {
		this.allegatoNote = allegatoNote;
	}
	public boolean isHasListe() {
		return hasListe;
	}
	public void setHasListe(boolean hasListe) {
		this.hasListe = hasListe;
	}
	
}
