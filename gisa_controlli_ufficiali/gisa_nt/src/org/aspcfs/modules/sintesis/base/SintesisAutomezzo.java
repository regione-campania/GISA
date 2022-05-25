/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;

public class SintesisAutomezzo {
	
	private int id = -1;
	private int idRelazione = -1;
	private int enteredBy;
	private Timestamp entered;
	private int modifiedBy;
	private Timestamp modified;
	private Timestamp dataDismissione;
	
	private String automezzoMarca;
	private String automezzoTipo;
	private String automezzoTarga;
	
	private int ricoveroIdProvincia;
	private int ricoveroIdComune;
	private int ricoveroIdToponimo;
	private String ricoveroVia;
	private String ricoveroCivico;

	private int detenzioneIdProvincia;
	private int detenzioneIdComune;
	private int detenzioneIdToponimo;
	private String detenzioneVia;
	private String detenzioneCivico;

	private String automezzoCisternaTrasporto;
	
	private String automezzoVeicoloFreschi;
	private String automezzoVeicoloFreschiCat1;
	private String automezzoVeicoloFreschiCat2;
	private String automezzoVeicoloFreschiCat3;
	
	private String automezzoVeicoloDerivati;
	private String automezzoVeicoloDerivatiCat1;
	private String automezzoVeicoloDerivatiCat2;
	private String automezzoVeicoloDerivatiCat3;
	
	private String automezzoContenitore;
	private String automezzoContenitoreDimensioni;

	private String trasportoTemperaturaControllata;
	private String trasportoIsotermico;
	private String trasportoTemperaturaAmbiente;
		
	private String numeroIdentificativo;
	
	
	
	public SintesisAutomezzo() {
		// TODO Auto-generated constructor stub
	}
	
	public SintesisAutomezzo(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public SintesisAutomezzo(Connection db, int id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sintesis_automezzi where id = ? and trashed_date is null");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			buildRecord(rs);
		}
	}

	public void buildRecord(ResultSet rs) throws SQLException{ 
		id = rs.getInt("id");
		idRelazione = rs.getInt("id_sintesis_rel_stab_lp");
		enteredBy = rs.getInt("entered_by");
		entered = rs.getTimestamp("entered");
		modifiedBy = rs.getInt("modified_by");
		modified = rs.getTimestamp("modified");
		dataDismissione = rs.getTimestamp("data_dismissione");
		
		automezzoMarca = rs.getString("automezzo_marca");
		automezzoTipo = rs.getString("automezzo_tipo");
		automezzoTarga = rs.getString("automezzo_targa");
		
		ricoveroIdProvincia = rs.getInt("ricovero_id_provincia");
		ricoveroIdComune = rs.getInt("ricovero_id_comune");
		ricoveroIdToponimo = rs.getInt("ricovero_id_toponimo");
		ricoveroVia = rs.getString("ricovero_via");
		ricoveroCivico = rs.getString("ricovero_civico");
		
		detenzioneIdProvincia = rs.getInt("detenzione_id_provincia");
		detenzioneIdComune = rs.getInt("detenzione_id_comune");
		detenzioneIdToponimo = rs.getInt("detenzione_id_toponimo");
		detenzioneVia = rs.getString("detenzione_via");
		detenzioneCivico = rs.getString("detenzione_civico");
		
		automezzoCisternaTrasporto = rs.getString("automezzo_cisterna_trasporto");
		
		automezzoVeicoloFreschi = rs.getString("automezzo_veicolo_freschi");
		automezzoVeicoloFreschiCat1 = rs.getString("automezzo_veicolo_freschi_cat1");
		automezzoVeicoloFreschiCat2 = rs.getString("automezzo_veicolo_freschi_cat2");
		automezzoVeicoloFreschiCat3 = rs.getString("automezzo_veicolo_freschi_cat3");
		
		automezzoVeicoloDerivati = rs.getString("automezzo_veicolo_derivati");
		automezzoVeicoloDerivatiCat1 = rs.getString("automezzo_veicolo_derivati");
		automezzoVeicoloDerivatiCat2 = rs.getString("automezzo_veicolo_derivati_cat2");
		automezzoVeicoloDerivatiCat3 = rs.getString("automezzo_veicolo_derivati_cat3");
		
		automezzoContenitore = rs.getString("automezzo_contenitore");
		automezzoContenitoreDimensioni = rs.getString("automezzo_contenitore_dimensioni");

		trasportoTemperaturaControllata = rs.getString("trasporto_temperatura_controllata");
		trasportoIsotermico = rs.getString("trasporto_isotermico");
		trasportoTemperaturaAmbiente = rs.getString("trasporto_temperatura_ambiente");
		
		numeroIdentificativo = rs.getString("numero_identificativo");
	}
	
	
	public static ArrayList<SintesisAutomezzo> getElencoAutomezzi(Connection db, int idRelazione) throws SQLException {
			ArrayList<SintesisAutomezzo> lista = new  ArrayList<SintesisAutomezzo>();
		 		
				PreparedStatement pst = db.prepareStatement("select * from sintesis_automezzi where id_sintesis_rel_stab_lp = ? and trashed_date is null and data_dismissione is null order by numero_identificativo asc");
				pst.setInt(1, idRelazione);
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					SintesisAutomezzo automezzo = new SintesisAutomezzo(rs);
					lista.add(automezzo);		
				}
			
			return lista;
	}

	public void insert(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_automezzi ("+
				"id_sintesis_rel_stab_lp, entered_by,"+ 
				"automezzo_marca, automezzo_tipo, automezzo_targa, "+ 
				"ricovero_id_provincia, ricovero_id_comune, ricovero_id_toponimo, ricovero_via, ricovero_civico,"+
				"detenzione_id_provincia, detenzione_id_comune, detenzione_id_toponimo, detenzione_via, detenzione_civico,"+ 
				"automezzo_cisterna_trasporto, automezzo_veicolo_freschi, automezzo_veicolo_freschi_cat1, automezzo_veicolo_freschi_cat2, automezzo_veicolo_freschi_cat3, "+
				"automezzo_veicolo_derivati, automezzo_veicolo_derivati_cat1, automezzo_veicolo_derivati_cat2, automezzo_veicolo_derivati_cat3, "+
				"automezzo_contenitore, automezzo_contenitore_dimensioni, trasporto_temperatura_controllata, trasporto_isotermico, trasporto_temperatura_ambiente, "+
				"numero_identificativo) values ("
				+ "?, ?, "
				+ "?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ " ? ) returning id as id_inserito;");
								
		int i = 0;
		pst.setInt(++i, idRelazione);
		pst.setInt(++i, idUtente);
	
		pst.setString(++i, automezzoMarca);
		pst.setString(++i, automezzoTipo);
		pst.setString(++i, automezzoTarga);

		pst.setInt(++i, ricoveroIdProvincia);
		pst.setInt(++i, ricoveroIdComune);
		pst.setInt(++i, ricoveroIdToponimo);
		pst.setString(++i, ricoveroVia);
		pst.setString(++i, ricoveroCivico);
		
		pst.setInt(++i, detenzioneIdProvincia);
		pst.setInt(++i, detenzioneIdComune);
		pst.setInt(++i, detenzioneIdToponimo);
		pst.setString(++i, detenzioneVia);
		pst.setString(++i, detenzioneCivico);
		
		pst.setString(++i, automezzoCisternaTrasporto);
		pst.setString(++i, automezzoVeicoloFreschi);
		pst.setString(++i, automezzoVeicoloFreschiCat1);
		pst.setString(++i, automezzoVeicoloFreschiCat2);
		pst.setString(++i, automezzoVeicoloFreschiCat3);	
		
		pst.setString(++i, automezzoVeicoloDerivati);	
		pst.setString(++i, automezzoVeicoloDerivatiCat1);	
		pst.setString(++i, automezzoVeicoloDerivatiCat2);	
		pst.setString(++i, automezzoVeicoloDerivatiCat3);	

		pst.setString(++i, automezzoContenitore);	
		pst.setString(++i, automezzoContenitoreDimensioni);
		pst.setString(++i, trasportoTemperaturaControllata);	
		pst.setString(++i, trasportoIsotermico);	
		pst.setString(++i, trasportoTemperaturaAmbiente);	

		pst.setString(++i, numeroIdentificativo);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			this.id = rs.getInt("id_inserito");
	}

	public void update(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_automezzi set "+
				"modified = now(), modified_by= ?, "+ 
				"ricovero_id_provincia= ?,  ricovero_id_comune= ?,  ricovero_id_toponimo= ?,  ricovero_via= ?,  ricovero_civico= ?, "+
				"detenzione_id_provincia= ?,  detenzione_id_comune= ?,  detenzione_id_toponimo= ?,  detenzione_via= ?,  detenzione_civico= ?, "+ 
				"automezzo_cisterna_trasporto= ?,  automezzo_veicolo_freschi= ?,  automezzo_veicolo_freschi_cat1= ?,  automezzo_veicolo_freschi_cat2= ?,  automezzo_veicolo_freschi_cat3= ?, "+
				"automezzo_veicolo_derivati= ?,  automezzo_veicolo_derivati_cat1= ?,  automezzo_veicolo_derivati_cat2 = ?,  automezzo_veicolo_derivati_cat3= ?,  automezzo_contenitore= ?, "+
				"automezzo_contenitore_dimensioni= ?,  trasporto_temperatura_controllata= ?,  trasporto_isotermico = ?,  trasporto_temperatura_ambiente= ? where id = ?;");
		
		int i = 0;
		pst.setInt(++i, idUtente);
		
		pst.setInt(++i, ricoveroIdProvincia);
		pst.setInt(++i, ricoveroIdComune);
		pst.setInt(++i, ricoveroIdToponimo);
		pst.setString(++i, ricoveroVia);
		pst.setString(++i, ricoveroCivico);
		
		pst.setInt(++i, detenzioneIdProvincia);
		pst.setInt(++i, detenzioneIdComune);
		pst.setInt(++i, detenzioneIdToponimo);
		pst.setString(++i, detenzioneVia);
		pst.setString(++i, detenzioneCivico);
		
		pst.setString(++i, automezzoCisternaTrasporto);
		pst.setString(++i, automezzoVeicoloFreschi);
		pst.setString(++i, automezzoVeicoloFreschiCat1);
		pst.setString(++i, automezzoVeicoloFreschiCat2);
		pst.setString(++i, automezzoVeicoloFreschiCat3);	
		
		pst.setString(++i, automezzoVeicoloDerivati);	
		pst.setString(++i, automezzoVeicoloDerivatiCat1);	
		pst.setString(++i, automezzoVeicoloDerivatiCat2);	
		pst.setString(++i, automezzoVeicoloDerivatiCat3);	

		pst.setString(++i, automezzoContenitore);	
		pst.setString(++i, automezzoContenitoreDimensioni);
		pst.setString(++i, trasportoTemperaturaControllata);	
		pst.setString(++i, trasportoIsotermico);	
		pst.setString(++i, trasportoTemperaturaAmbiente);	
		
		pst.setInt(++i, id);
		
		pst.executeUpdate();
		
	}
	
	public void insertStorico(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_automezzi_storico (entered_by, id_sintesis_automezzi, json) values (?, ?, (select row_to_json(sintesis_automezzi) from sintesis_automezzi where id = ?))");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);
		pst.setInt(3, id);
		pst.executeUpdate();
		
	}

	public void delete(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_automezzi set trashed_date = now(), modified = now(), modified_by = ? where id = ?");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);		
		pst.executeUpdate();		
	}
	
	public void dismetti(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_automezzi set data_dismissione = now(), modified = now(), modified_by = ? where id = ?");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);
		pst.executeUpdate();		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(int idRelazione) {
		this.idRelazione = idRelazione;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public String getAutomezzoMarca() {
		return automezzoMarca;
	}

	public void setAutomezzoMarca(String automezzoMarca) {
		this.automezzoMarca = automezzoMarca;
	}

	public String getAutomezzoTipo() {
		return automezzoTipo;
	}

	public void setAutomezzoTipo(String automezzoTipo) {
		this.automezzoTipo = automezzoTipo;
	}

	public String getAutomezzoTarga() {
		return automezzoTarga;
	}

	public void setAutomezzoTarga(String automezzoTarga) {
		this.automezzoTarga = automezzoTarga;
	}
	
	public int getRicoveroIdProvincia() {
		return ricoveroIdProvincia;
	}

	public void setRicoveroIdProvincia(int ricoveroIdProvincia) {
		this.ricoveroIdProvincia = ricoveroIdProvincia;
	}

	public int getRicoveroIdComune() {
		return ricoveroIdComune;
	}

	public void setRicoveroIdComune(int ricoveroIdComune) {
		this.ricoveroIdComune = ricoveroIdComune;
	}

	public int getRicoveroIdToponimo() {
		return ricoveroIdToponimo;
	}

	public void setRicoveroIdToponimo(int ricoveroIdToponimo) {
		this.ricoveroIdToponimo = ricoveroIdToponimo;
	}

	public String getRicoveroVia() {
		return ricoveroVia;
	}

	public void setRicoveroVia(String ricoveroVia) {
		this.ricoveroVia = ricoveroVia;
	}

	public String getRicoveroCivico() {
		return ricoveroCivico;
	}

	public void setRicoveroCivico(String ricoveroCivico) {
		this.ricoveroCivico = ricoveroCivico;
	}

	public int getDetenzioneIdProvincia() {
		return detenzioneIdProvincia;
	}

	public void setDetenzioneIdProvincia(int detenzioneIdProvincia) {
		this.detenzioneIdProvincia = detenzioneIdProvincia;
	}

	public int getDetenzioneIdComune() {
		return detenzioneIdComune;
	}

	public void setDetenzioneIdComune(int detenzioneIdComune) {
		this.detenzioneIdComune = detenzioneIdComune;
	}

	public int getDetenzioneIdToponimo() {
		return detenzioneIdToponimo;
	}

	public void setDetenzioneIdToponimo(int detenzioneIdToponimo) {
		this.detenzioneIdToponimo = detenzioneIdToponimo;
	}

	public String getDetenzioneVia() {
		return detenzioneVia;
	}

	public void setDetenzioneVia(String detenzioneVia) {
		this.detenzioneVia = detenzioneVia;
	}

	public String getDetenzioneCivico() {
		return detenzioneCivico;
	}

	public void setDetenzioneCivico(String detenzioneCivico) {
		this.detenzioneCivico = detenzioneCivico;
	}

	

	public String getNumeroIdentificativo() {
		return numeroIdentificativo;
	}

	public void setNumeroIdentificativo(String numeroIdentificativo) {
		this.numeroIdentificativo = numeroIdentificativo;
	}

	public String getAutomezzoCisternaTrasporto() {
		return automezzoCisternaTrasporto;
	}

	public void setAutomezzoCisternaTrasporto(String automezzoCisternaTrasporto) {
		this.automezzoCisternaTrasporto = automezzoCisternaTrasporto;
	}

	public String getAutomezzoVeicoloFreschi() {
		return automezzoVeicoloFreschi;
	}

	public void setAutomezzoVeicoloFreschi(String automezzoVeicoloFreschi) {
		this.automezzoVeicoloFreschi = automezzoVeicoloFreschi;
	}

	public String getAutomezzoVeicoloFreschiCat1() {
		return automezzoVeicoloFreschiCat1;
	}

	public void setAutomezzoVeicoloFreschiCat1(String automezzoVeicoloFreschiCat1) {
		this.automezzoVeicoloFreschiCat1 = automezzoVeicoloFreschiCat1;
	}

	public String getAutomezzoVeicoloFreschiCat2() {
		return automezzoVeicoloFreschiCat2;
	}

	public void setAutomezzoVeicoloFreschiCat2(String automezzoVeicoloFreschiCat2) {
		this.automezzoVeicoloFreschiCat2 = automezzoVeicoloFreschiCat2;
	}

	public String getAutomezzoVeicoloFreschiCat3() {
		return automezzoVeicoloFreschiCat3;
	}

	public void setAutomezzoVeicoloFreschiCat3(String automezzoVeicoloFreschiCat3) {
		this.automezzoVeicoloFreschiCat3 = automezzoVeicoloFreschiCat3;
	}

	public String getAutomezzoVeicoloDerivati() {
		return automezzoVeicoloDerivati;
	}

	public void setAutomezzoVeicoloDerivati(String automezzoVeicoloDerivati) {
		this.automezzoVeicoloDerivati = automezzoVeicoloDerivati;
	}

	public String getAutomezzoVeicoloDerivatiCat1() {
		return automezzoVeicoloDerivatiCat1;
	}

	public void setAutomezzoVeicoloDerivatiCat1(String automezzoVeicoloDerivatiCat1) {
		this.automezzoVeicoloDerivatiCat1 = automezzoVeicoloDerivatiCat1;
	}

	public String getAutomezzoVeicoloDerivatiCat2() {
		return automezzoVeicoloDerivatiCat2;
	}

	public void setAutomezzoVeicoloDerivatiCat2(String automezzoVeicoloDerivatiCat2) {
		this.automezzoVeicoloDerivatiCat2 = automezzoVeicoloDerivatiCat2;
	}

	public String getAutomezzoVeicoloDerivatiCat3() {
		return automezzoVeicoloDerivatiCat3;
	}

	public void setAutomezzoVeicoloDerivatiCat3(String automezzoVeicoloDerivatiCat3) {
		this.automezzoVeicoloDerivatiCat3 = automezzoVeicoloDerivatiCat3;
	}

	public String getAutomezzoContenitore() {
		return automezzoContenitore;
	}

	public void setAutomezzoContenitore(String automezzoContenitore) {
		this.automezzoContenitore = automezzoContenitore;
	}

	public String getAutomezzoContenitoreDimensioni() {
		return automezzoContenitoreDimensioni;
	}

	public void setAutomezzoContenitoreDimensioni(String automezzoContenitoreDimensioni) {
		this.automezzoContenitoreDimensioni = automezzoContenitoreDimensioni;
	}

	public String getTrasportoTemperaturaControllata() {
		return trasportoTemperaturaControllata;
	}

	public void setTrasportoTemperaturaControllata(String trasportoTemperaturaControllata) {
		this.trasportoTemperaturaControllata = trasportoTemperaturaControllata;
	}

	public String getTrasportoIsotermico() {
		return trasportoIsotermico;
	}

	public void setTrasportoIsotermico(String trasportoIsotermico) {
		this.trasportoIsotermico = trasportoIsotermico;
	}

	public String getTrasportoTemperaturaAmbiente() {
		return trasportoTemperaturaAmbiente;
	}

	public void setTrasportoTemperaturaAmbiente(String trasportoTemperaturaAmbiente) {
		this.trasportoTemperaturaAmbiente = trasportoTemperaturaAmbiente;
	}

	public void buildFromRequest(ActionContext context) {
		
		automezzoMarca = context.getRequest().getParameter("automezzoMarca");
		automezzoTipo = context.getRequest().getParameter("automezzoTipo");
		automezzoTarga = context.getRequest().getParameter("automezzoTarga");
		
		try { ricoveroIdProvincia = Integer.parseInt(context.getRequest().getParameter("ricoveroIdProvincia")); } catch (Exception e) {}
		try { ricoveroIdComune = Integer.parseInt(context.getRequest().getParameter("ricoveroIdComune")); } catch (Exception e) {}
		try { ricoveroIdToponimo = Integer.parseInt(context.getRequest().getParameter("ricoveroIdToponimo")); } catch (Exception e) {}

		ricoveroVia = context.getRequest().getParameter("ricoveroVia");
		ricoveroCivico = context.getRequest().getParameter("ricoveroCivico");
		
		try { detenzioneIdProvincia = Integer.parseInt(context.getRequest().getParameter("detenzioneIdProvincia")); } catch (Exception e) {}
		try { detenzioneIdComune = Integer.parseInt(context.getRequest().getParameter("detenzioneIdComune")); } catch (Exception e) {}
		try { detenzioneIdToponimo = Integer.parseInt(context.getRequest().getParameter("detenzioneIdToponimo")); } catch (Exception e) {}

		detenzioneVia = context.getRequest().getParameter("detenzioneVia");
		detenzioneCivico = context.getRequest().getParameter("detenzioneCivico");
		
		automezzoCisternaTrasporto = context.getRequest().getParameter("automezzoCisternaTrasporto");
		
		automezzoVeicoloFreschi = context.getRequest().getParameter("automezzoVeicoloFreschi");
		automezzoVeicoloFreschiCat1 = context.getRequest().getParameter("automezzoVeicoloFreschiCat1");
		automezzoVeicoloFreschiCat2 = context.getRequest().getParameter("automezzoVeicoloFreschiCat2");
		automezzoVeicoloFreschiCat3 = context.getRequest().getParameter("automezzoVeicoloFreschiCat3");

		automezzoVeicoloDerivati = context.getRequest().getParameter("automezzoVeicoloDerivati");
		automezzoVeicoloDerivatiCat1 = context.getRequest().getParameter("automezzoVeicoloDerivatiCat1");
		automezzoVeicoloDerivatiCat2 = context.getRequest().getParameter("automezzoVeicoloDerivatiCat2");
		automezzoVeicoloDerivatiCat3 = context.getRequest().getParameter("automezzoVeicoloDerivatiCat3");
		
		automezzoContenitore = context.getRequest().getParameter("automezzoContenitore");
		automezzoContenitoreDimensioni = context.getRequest().getParameter("automezzoContenitoreDimensioni");
		
		trasportoTemperaturaControllata = context.getRequest().getParameter("trasportoTemperaturaControllata");
		trasportoIsotermico = context.getRequest().getParameter("trasportoIsotermico");
		trasportoTemperaturaAmbiente = context.getRequest().getParameter("trasportoTemperaturaAmbiente");

		
	}

	public void generaNumeroIdentificativo(Connection db) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from genera_numero_identificativo_scarrabili(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.numeroIdentificativo = rs.getString("genera_numero_identificativo_scarrabili");
	}

	public Timestamp getDataDismissione() {
		return dataDismissione;
	}

	public void setDataDismissione(Timestamp dataDismissione) {
		this.dataDismissione = dataDismissione;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public String getCuAssociati(Connection db) throws SQLException {
		String cu = null;
		PreparedStatement pst = db.prepareStatement("select * from get_cu_associati_targa_sintesis(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			cu = rs.getString("get_cu_associati_targa_sintesis");
		return cu;
	}
	
	

}
