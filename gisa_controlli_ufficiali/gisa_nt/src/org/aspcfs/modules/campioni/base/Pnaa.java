/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.report.util.ApplicationProperties;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.utils.DateUtils;

public class Pnaa {

	private String ente_appartenenza ;
	private String unita_territoriale ;
	private String componenti_nucleo_ispettivo ;
	String anno = "" ;
	String mese = "" ;
	String gg = "" ;
	int codice_piano;
	int idCampione;
	String data_chiusura_campione;
	String materia = "";
	String codice_materia_prima = "";
	String alimenti = "";
	String nome_piano = "";
	String cap = "";
	String a0 = "";
	String a1 = "";
	String a2 = "";
	String a2_norma = "";
	String a3 = "";
	String a3_1 = "";
	String a3_1_1 = "";
	String a3_1_2 = "";
	String a3_1_3 = "";
	String a3_1_4 = "";
	String a3_1_5 = "";
	String a3_1_6 = "";
	String a3_1_7 = "";
	String a3_1_8 = "";
	String a3_1_9 = "";
	String a3_1_10 = "";
	String a3_1_11 = "";
	String a4 = "";
	String a5 = "";
	String a6 = "";
	String a7 = "";
	String a8 = "";
	String a9 = "";
	String a10 = "";
	String a11_1 = "";
	String a11_2 = "";
	String a12 = "";
	String a12b = "";
	String a13 = "";
	String a14 = "";
	String a15 = "";
	String a15b = "";
	String a16 = "";
	String b1 = "";
	String b2 = "";
	String b3 = "";
	String b4 = "";
	String b5 = "";
	String b6 = "";
	String b7 = "";
	String b8 = "";
	String b9 = "";
	String b10 = "";
	String b11 = "";
	String b12 = "";
	String b13 = "";
	String b14 = "";
	String b15 = "";
	String b16 = "";
	String b17 = "";

	String asl_campione = "";
	String numVerbale = "";
	String numScheda = "";
	String annoControllo = "";
	String meseControllo = "";
	String giornoControllo = "";
	String identificativo = "";
	String orgId = "";
	String motivazione = "";
	String barcode_motivazione = "";
	String barcode_numero_registrazione = "";
	String data = ""; // data generazione verbale
	String url = "";
	String componente_nucleo = "";
	String componente_nucleo_due = "";
	String componente_nucleo_tre = "";
	String componente_nucleo_quattro = "";
	String componente_nucleo_cinque = "";
	String componente_nucleo_sei = "";
	String componente_nucleo_sette = "";
	String componente_nucleo_otto = "";
	String componente_nucleo_nove = "";
	String componente_nucleo_dieci = "";
	String bozza = "true";
	String comune = "";
	String indirizzo = "";
	ArrayList<SpecieAnimali> listaSpecieAnimali = new ArrayList<SpecieAnimali>();
	
	String perContoDi ="";
	
	
	//kiave = nomeCampo
	// value = hashmap<String ,String>
	//kiave = valorecampo
	HashMap<String,HashMap<String ,String>> listaCampiPna = new HashMap<String, HashMap<String ,String>>();
	
	
	private int idControlloUfficiale = -1;
	
	
	
	
	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getEnte_appartenenza() {
		return ente_appartenenza;
	}

	public void setEnte_appartenenza(String ente_appartenenza) {
		this.ente_appartenenza = ente_appartenenza;
	}

	public String getUnita_territoriale() {
		return unita_territoriale;
	}

	public void setUnita_territoriale(String unita_territoriale) {
		this.unita_territoriale = unita_territoriale;
	}

	public String getComponenti_nucleo_ispettivo() {
		return componenti_nucleo_ispettivo;
	}

	public void setComponenti_nucleo_ispettivo(String componenti_nucleo_ispettivo) {
		this.componenti_nucleo_ispettivo = componenti_nucleo_ispettivo;
	}

	public HashMap<String, HashMap<String,String>> getListaCampiPna() {
		return listaCampiPna;
	}

	public void setListaCampiPna(HashMap<String, HashMap<String,String>> listaCampiPna) {
		this.listaCampiPna = listaCampiPna;
	}

	public void setPerContoDi(String perContoDi){
		this.perContoDi = perContoDi;
	}
	
	public  String getPerContoDi(){
		return perContoDi;
	}
	
	public  int getIdControlloUfficiale(){
		return idControlloUfficiale;
	}
	
	public void setIdControlloUfficiale(int idControlloUfficiale){
		this.idControlloUfficiale = idControlloUfficiale;
	}

	
	public String getCodice_materia_prima() {
		return codice_materia_prima;
	}

	public void setCodice_materia_prima(String codice_materia_prima) {
		this.codice_materia_prima = codice_materia_prima;
	}

	public String getData_chiusura_campione() {
		return data_chiusura_campione;
	}

	public void setData_chiusura_campione(String data_chiusura_campione) {
		this.data_chiusura_campione = data_chiusura_campione;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public ArrayList<SpecieAnimali> getListaSpecieAnimali() {
		return listaSpecieAnimali;
	}

	public void setListaSpecieAnimali(
			ArrayList<SpecieAnimali> listaSpecieAnimali) {
		this.listaSpecieAnimali = listaSpecieAnimali;
	}

	public String getBarcode_motivazione() {
		return barcode_motivazione;
	}

	public void setBarcode_motivazione(String barcode_motivazione) {
		this.barcode_motivazione = barcode_motivazione;
	}

	public String getBarcode_numero_registrazione() {
		return barcode_numero_registrazione;
	}

	public void setBarcode_numero_registrazione(
			String barcode_numero_registrazione) {
		this.barcode_numero_registrazione = barcode_numero_registrazione;
	}

	public String getNumScheda() {
		return numScheda;
	}

	public void setNumScheda(String numScheda) {
		this.numScheda = numScheda;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getA12b() {
		return a12b;
	}

	public void setA12b(String a12b) {
		this.a12b = a12b;
	}

	public String getA15b() {
		return a15b;
	}

	public void setA15b(String a15b) {
		this.a15b = a15b;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getAlimenti() {
		return alimenti;
	}

	public void setAlimenti(String alimenti) {
		this.alimenti = alimenti;
	}

	public String getA2_norma() {
		return a2_norma;
	}

	public void setA2_norma(String a2_norma) {
		this.a2_norma = a2_norma;
	}

	public String getA3_1_9() {
		return a3_1_9;
	}

	public void setA3_1_9(String a3_1_9) {
		this.a3_1_9 = a3_1_9;
	}

	public String getA3_1_10() {
		return a3_1_10;
	}

	public void setA3_1_10(String a3_1_10) {
		this.a3_1_10 = a3_1_10;
	}

	public String getA3_1_11() {
		return a3_1_11;
	}

	public void setA3_1_11(String a3_1_11) {
		this.a3_1_11 = a3_1_11;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getNumVerbale() {
		return numVerbale;
	}

	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public int getIdControllo() {
		return idCampione;
	}

	public void setIdControllo(int idCampione) {
		this.idCampione = idCampione;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setA0(String a0) {
		this.a0 = a0;
	}

	public String getA4() {
		return a4;
	}

	public String getA3_1() {
		return a3_1;
	}

	public void setA3_1(String a3_1) {
		this.a3_1 = a3_1;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11_1() {
		return a11_1;
	}

	public void setA11_1(String a11_1) {
		this.a11_1 = a11_1;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA13() {
		return a13;
	}

	public void setA13(String a13) {
		this.a13 = a13;
	}

	public String getA14() {
		return a14;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}

	public String getA15() {
		return a15;
	}

	public void setA15(String a15) {
		this.a15 = a15;
	}

	public String getA16() {
		return a16;
	}

	public void setA16(String a16) {
		this.a16 = a16;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public String getB3() {
		return b3;
	}

	public void setB3(String b3) {
		this.b3 = b3;
	}

	public String getB4() {
		return b4;
	}

	public void setB4(String b4) {
		this.b4 = b4;
	}

	public String getB5() {
		return b5;
	}

	public void setB5(String b5) {
		this.b5 = b5;
	}

	public String getB6() {
		return b6;
	}

	public void setB6(String b6) {
		this.b6 = b6;
	}

	public String getB8() {
		return b8;
	}

	public void setB8(String b8) {
		this.b8 = b8;
	}

	public String getB9() {
		return b9;
	}

	public void setB9(String b9) {
		this.b9 = b9;
	}

	public String getB10() {
		return b10;
	}

	public void setB10(String b10) {
		this.b10 = b10;
	}

	public String getB11() {
		return b11;
	}

	public void setB11(String b11) {
		this.b11 = b11;
	}

	public String getB12() {
		return b12;
	}

	public void setB12(String b12) {
		this.b12 = b12;
	}

	public String getB13() {
		return b13;
	}

	public void setB13(String b13) {
		this.b13 = b13;
	}

	public String getB14() {
		return b14;
	}

	public void setB14(String b14) {
		this.b14 = b14;
	}

	public String getB15() {
		return b15;
	}

	public void setB15(String b15) {
		this.b15 = b15;
	}

	public String getB16() {
		return b16;
	}

	public void setB16(String b16) {
		this.b16 = b16;
	}

	public String getB17() {
		return b17;
	}

	public void setB17(String b17) {
		this.b17 = b17;
	}

	public String getA0() {
		return a0;
	}

	public ArrayList<String> getListaProdottiPnaa() {
		return listaProdottiPnaa;
	}

	public void setListaProdottiPnaa(ArrayList<String> listaProdottiPnaa) {
		this.listaProdottiPnaa = listaProdottiPnaa;
	}

	public String getAslCampione() {
		return asl_campione;
	}

	public void setAslCampione(String asl_campione) {
		this.asl_campione = asl_campione;
	}

	public String getAnnoControllo() {
		return annoControllo;
	}

	public void setAnnoControllo(String anno) {
		this.annoControllo = anno;
	}

	public String getMeseControllo() {
		return meseControllo;
	}

	public void setMeseControllo(String giorno) {
		this.meseControllo = giorno;
	}

	public String getGiornoControllo() {
		return giornoControllo;
	}

	public void setGiornoControllo(String mese) {
		this.giornoControllo = mese;
	}

	ArrayList<SpecieAnimali> listaAnimali = new ArrayList<SpecieAnimali>();
	private ArrayList<String> listaProdottiPnaa = new ArrayList<String>();

	public int getCodice_piano() {
		return codice_piano;
	}

	public void setCodice_piano(int codice_piano) {
		this.codice_piano = codice_piano;
	}

	public String getNome_piano() {
		return nome_piano;
	}

	public void setNome_piano(String nome_piano) {
		this.nome_piano = nome_piano;
	}

	public String getB1() {
		return b1;
	}

	public void setB1(String b1) {
		this.b1 = b1;
	}

	public String getB7() {
		return b7;
	}

	public void setB7(String b7) {
		this.b7 = b7;
	}

	public String getA11_2() {
		return a11_2;
	}

	public void setA11_2(String a11_2) {
		this.a11_2 = a11_2;
	}

	public String getA3_1_1() {
		return a3_1_1;
	}

	public void setA3_1_1(String a3_1_1) {
		this.a3_1_1 = a3_1_1;
	}

	public String getA3_1_2() {
		return a3_1_2;
	}

	public void setA3_1_2(String a3_1_2) {
		this.a3_1_2 = a3_1_2;
	}

	public String getA3_1_3() {
		return a3_1_3;
	}

	public void setA3_1_3(String a3_1_3) {
		this.a3_1_3 = a3_1_3;
	}

	public String getA3_1_4() {
		return a3_1_4;
	}

	public void setA3_1_4(String a3_1_4) {
		this.a3_1_4 = a3_1_4;
	}

	public String getA3_1_5() {
		return a3_1_5;
	}

	public void setA3_1_5(String a3_1_5) {
		this.a3_1_5 = a3_1_5;
	}

	public String getA3_1_6() {
		return a3_1_6;
	}

	public void setA3_1_6(String a3_1_6) {
		this.a3_1_6 = a3_1_6;
	}

	public String getA3_1_7() {
		return a3_1_7;
	}

	public void setA3_1_7(String a3_1_7) {
		this.a3_1_7 = a3_1_7;
	}

	public String getA3_1_8() {
		return a3_1_8;
	}

	public void setA3_1_8(String a3_1_8) {
		this.a3_1_8 = a3_1_8;
	}

	public Pnaa() throws SQLException {
	}

	public Pnaa(Connection db, int id) throws SQLException {
		queryRecord(db, id);

	}

	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		// Controllo se il documento e' gia' stato salvato sul db
		PreparedStatement pst = db
				.prepareStatement("SELECT * FROM scheda_campioni_pnaa where a0 in (select identificativo from ticket where ticketid = ? and trashed_date is null)");
		pst.setInt(1, idCampione);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			bozza = "false";
		}

		
		
		ResultSet rs_op = null;
		String qry = ApplicationProperties.getProperty("GET_OP_PNAA");
		PreparedStatement pst_pnaa = db.prepareStatement(qry);
		pst_pnaa.setInt(1, Integer.parseInt(orgId));
		pst_pnaa.setInt(2, idCampione);
		rs_op = pst_pnaa.executeQuery();
		buildRecord(rs_op);
		
		
		buildListSpecieAnimali_Pnaa_Mangime(db);
		buildListProdottiPnaa(db);
		buildPerContoDi(db);

	}
	
	
	
	public void queryRecordRistrutturato(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		// Controllo se il documento e' gia' stato salvato sul db
		PreparedStatement pst = db
				.prepareStatement("SELECT * FROM campioni_fields_value where id_Campione = ?");
		pst.setInt(1, idCampione);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			bozza = "false";
		}
		/*Dati Recuperati*/
		ResultSet rs_op = null;
		String qry = ApplicationProperties.getProperty("GET_OP_PNAA_2");
		PreparedStatement pst_pnaa = db.prepareStatement(qry);
		pst_pnaa.setInt(1, Integer.parseInt(orgId));
		pst_pnaa.setInt(2, idCampione);
		rs_op = pst_pnaa.executeQuery();
		buildRecord2(rs_op);
		
	
		

	}

	
	
	public void buildRecord2(ResultSet rs) {

		String prelevatore = "";

		try {
			if (rs.next()) {

				orgId = ""+ rs.getInt("org_id");
				
				 
				 ente_appartenenza = rs.getString("ente_appartenenza");
				 unita_territoriale = rs.getString("unita_territoriale");
				 anno = ""+rs.getInt("anno");
				 gg = ""+ rs.getInt("giorno");
				mese = "" +rs.getInt("mese");
				componenti_nucleo_ispettivo = rs.getString("componenti_nucleo_ispettivo");
				a1 = rs.getString("a1");
				a3 = rs.getString("a3");
				a4 = rs.getString("a4");
				 a8 = rs.getString("a8");

				 a9 = rs.getString("a9");
				 
				 a10  = rs.getString("a10");
				 a11_1= rs.getString("a11_1");
				 a11_2 = rs.getString("a11_2");
				 a12 = rs.getString("a12");
				 a12b= rs.getString("a12b");
				 a13  = rs.getString("a13");
				 a14= rs.getString("a14");
				 a15 = rs.getString("a15");
				 a15b = rs.getString("a15b");
				 b7= rs.getString("b7");
				 
				 numVerbale = rs.getString("num_verbale");
					motivazione = rs.getString("motivazione");
					barcode_motivazione = rs.getString("barcode_motivazione");
					barcode_numero_registrazione = rs
							.getString("numero_registrazione_osa");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buildRecord(ResultSet rs) {

		String prelevatore = "";

		try {
			if (rs.next()) {

				a8 = rs.getString("indirizzo");
				a9 = rs.getString("comune");
				cap = rs.getString("cap");
				idCampione = rs.getInt("id_campione");
				data_chiusura_campione = rs.getString("data_chiusura_campione");
				a0 = rs.getString("identificativo");
				a1 = rs.getString("a1");
				a3 = rs.getString("a3");
				a1 = rs.getString("a1");
				a10 = rs.getString("provincia");// provincia
				a11_1 = rs.getString("latitudine");// Latitudine
				a11_2 = rs.getString("longitudine");// Longitudine
				a12 = rs.getString("ragione_sociale_o_proprietario");// ragione_sociale/proprietario_animali
				a12b = rs.getString("ragione_sociale");// ragione_sociale
				a13 = rs.getString("titolare");
				a14 = rs.getString("cf_proprietario");// codice
														// fiscale/proprietario
														// animali
				a15 = rs.getString("nome_detentore");// detentore
				a15b = rs.getString("cf_detentore");// cf_detentore
				b1 = rs.getString("b1"); // matrice
				materia = rs.getString("materia_prima");
				codice_materia_prima = rs.getString("foodex2_codice");		
				b7 = rs.getString("b7");// metodo di produzione
				giornoControllo = rs.getString("data_campione").substring(8, 10);
				meseControllo = getMeseFromData(rs.getString("data_campione"));
				annoControllo = "14";
				asl_campione = rs.getString("asl");
				componente_nucleo = rs.getString("componente_nucleo");
				componente_nucleo_due = rs.getString("componente_nucleo_due");
				componente_nucleo_tre = rs.getString("componente_nucleo_tre");
				componente_nucleo_quattro = rs
						.getString("componente_nucleo_quattro");
				componente_nucleo_cinque = rs
						.getString("componente_nucleo_cinque");
				componente_nucleo_sei = rs.getString("componente_nucleo_sei");
				componente_nucleo_sette = rs
						.getString("componente_nucleo_sette");
				componente_nucleo_otto = rs.getString("componente_nucleo_otto");
				componente_nucleo_nove = rs.getString("componente_nucleo_nove");
				componente_nucleo_dieci = rs
						.getString("componente_nucleo_dieci");
				numVerbale = rs.getString("num_verbale");
				motivazione = rs.getString("motivazione");
				barcode_motivazione = rs.getString("barcode_motivazione");
				barcode_numero_registrazione = rs
						.getString("numero_registrazione_osa");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (componente_nucleo != null && !componente_nucleo.equals("")) {
			prelevatore = componente_nucleo + "-";
		}
		if (componente_nucleo_due != null && !componente_nucleo_due.equals("")) {
			prelevatore += componente_nucleo_due + "-";
			if (componente_nucleo_tre != null
					&& !componente_nucleo_tre.equals("")) {
				prelevatore += componente_nucleo_tre + "-";
			}
			if (componente_nucleo_quattro != null
					&& !componente_nucleo_quattro.equals("")) {
				prelevatore += componente_nucleo_quattro + "-";
			}
			if (componente_nucleo_cinque != null
					&& !componente_nucleo_cinque.equals("")) {
				prelevatore += componente_nucleo_cinque + "-";
			}
			if (componente_nucleo_sei != null
					&& !componente_nucleo_sei.equals("")) {
				prelevatore += componente_nucleo_sei + "-";
			}
			if (componente_nucleo_sette != null
					&& !componente_nucleo_sette.equals("")) {
				prelevatore += componente_nucleo_sette + "-";
			}
			if (componente_nucleo_otto != null
					&& !componente_nucleo_otto.equals("")) {
				prelevatore += componente_nucleo_otto + "-";
			}
			if (componente_nucleo_nove != null
					&& !componente_nucleo_nove.equals("")) {
				prelevatore += componente_nucleo_nove + "-";
			}
			if (componente_nucleo_dieci != null
					&& !componente_nucleo_dieci.equals("")) {
				prelevatore += componente_nucleo_dieci + "";
			}
			//componente_nucleo_due = prelevatore;
		} else {
			componente_nucleo_due = "";
		}

		a4 = prelevatore;

	}

	public void queryRecordIndirizzo(Connection db, String comUtente,
			String indUtente) throws SQLException {

		if (comune.equals("") && indirizzo.equals("")) {
			// Aggiorna eventualmente indirizzo inserito a mano.
			if (comUtente != null && indUtente != null) {

				if (!comUtente.equals("null") && !indUtente.equals("null")) {
					String update = "update organization_address set city = ?, addrline1 = ? where org_id = ? ";
					PreparedStatement ps = db.prepareStatement(update);
					ps.setString(1, comUtente);
					ps.setString(2, indUtente);
					ps.setInt(3, Integer.parseInt(orgId));
					ps.execute();
				}

				a8 = indUtente;
				a9 = comUtente;
			}

		}

	}

	public Pnaa(ResultSet rs) throws SQLException {

		while (rs.next()) {

			codice_piano = rs.getInt("codice_piano");
			nome_piano = rs.getString("nome_piano");
			if (rs.getString("a1") != null) {
				a1 = rs.getString("a1");
			}
			if (rs.getString("a3") != null) {
				a3 = rs.getString("a3");
			}
			if (rs.getString("b1") != null) {
				b1 = rs.getString("b1");
			}
			if (rs.getString("b7") != null) {
				b7 = rs.getString("b7");
			}

		}

	}

	public int getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(int idCampione) {
		this.idCampione = idCampione;
	}

	public String getComponente_nucleo() {
		return componente_nucleo;
	}

	public void setComponente_nucleo(String componente_nucleo) {
		this.componente_nucleo = componente_nucleo;
	}

	public String getComponente_nucleo_due() {
		return componente_nucleo_due;
	}

	public void setComponente_nucleo_due(String componente_nucleo_due) {
		this.componente_nucleo_due = componente_nucleo_due;
	}

	public String getComponente_nucleo_tre() {
		return componente_nucleo_tre;
	}

	public void setComponente_nucleo_tre(String componente_nucleo_tre) {
		this.componente_nucleo_tre = componente_nucleo_tre;
	}

	public String getComponente_nucleo_quattro() {
		return componente_nucleo_quattro;
	}

	public void setComponente_nucleo_quattro(String componente_nucleo_quattro) {
		this.componente_nucleo_quattro = componente_nucleo_quattro;
	}

	public String getComponente_nucleo_cinque() {
		return componente_nucleo_cinque;
	}

	public void setComponente_nucleo_cinque(String componente_nucleo_cinque) {
		this.componente_nucleo_cinque = componente_nucleo_cinque;
	}

	public String getComponente_nucleo_sei() {
		return componente_nucleo_sei;
	}

	public void setComponente_nucleo_sei(String componente_nucleo_sei) {
		this.componente_nucleo_sei = componente_nucleo_sei;
	}

	public String getComponente_nucleo_sette() {
		return componente_nucleo_sette;
	}

	public void setComponente_nucleo_sette(String componente_nucleo_sette) {
		this.componente_nucleo_sette = componente_nucleo_sette;
	}

	public String getComponente_nucleo_otto() {
		return componente_nucleo_otto;
	}

	public void setComponente_nucleo_otto(String componente_nucleo_otto) {
		this.componente_nucleo_otto = componente_nucleo_otto;
	}

	public String getComponente_nucleo_nove() {
		return componente_nucleo_nove;
	}

	public void setComponente_nucleo_nove(String componente_nucleo_nove) {
		this.componente_nucleo_nove = componente_nucleo_nove;
	}

	public String getComponente_nucleo_dieci() {
		return componente_nucleo_dieci;
	}

	public void setComponente_nucleo_dieci(String componente_nucleo_dieci) {
		this.componente_nucleo_dieci = componente_nucleo_dieci;
	}

	public String getBozza() {
		return bozza;
	}

	public void setBozza(String bozza) {
		this.bozza = bozza;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getMeseFromData(String data_referto) {
		String mese = data_referto.substring(5, 7);

		switch (Integer.parseInt(mese)) {
		case 01:
			mese = "Gennaio";
			break;
		case 02:
			mese = "Febbraio";
			break;
		case 03:
			mese = "Marzo";
			break;
		case 04:
			mese = "Aprile";
			break;
		case 05:
			mese = "Maggio";
			break;
		case 06:
			mese = "Giugno";
			break;
		case 07:
			mese = "Luglio";
			break;
		// case 08 : mese = "Agosto" ; break;
		// case 09 : mese = "Settembre" ; break;
		case 10:
			mese = "Ottobre";
			break;
		case 11:
			mese = "Novembre";
			break;
		case 12:
			mese = "Dicembre";
			break;
		}
		if (mese.equals("08")) {
			mese = "Agosto";
		}
		if (mese.equals("09")) {
			mese = "Settembre";
		}

		return mese;
	}
	
	
	

	public void buildListSpecieAnimali_Pnaa_Mangime(Connection db)
			throws SQLException {

		/*
		 * PreparedStatement pst1 = db.prepareStatement(
		 * "select specie.description as specie_descrizione ,specie.code as id_specie , "
		 * +
		 * "lcsa.code as categoria_id ,lcsa.description as categoria_descrizione "
		 * + "from campione_specie_animali_pnaa_mangime csa "+
		 * "join lookup_categorie_specie_animali lcsa on csa.id_categoria = lcsa.code "
		 * + "join lookup_specie_pnaa specie on specie.code = csa.id_specie  "+
		 * "where id_campione = ?" );
		 */
		PreparedStatement pst1 = db
				.prepareStatement(" select specie.description as specie_descrizione ,specie.code as id_specie "
						+ " from campione_specie_animali_pnaa_mangime csa "
						+ " join lookup_specie_pnaa specie on specie.code = csa.id_specie "
						+ " where id_campione = ?");

		pst1.setInt(1, this.idCampione);
		ResultSet rs = pst1.executeQuery();

		while (rs.next()) {
			int idSpecie = rs.getInt("id_specie");
			String descrSpecie = rs.getString("specie_descrizione");
			// int idCategoria = rs.getInt("categoria_id");
			// String descrCategoria = rs.getString("categoria_descrizione");
			SpecieAnimali specie = new SpecieAnimali();
			// specie.setIdCategoria(idCategoria);
			specie.setIdSpecie(idSpecie);
			// specie.setDescrizioneCategoria(descrCategoria);
			specie.setDescrizioneSpecie(descrSpecie);

			listaSpecieAnimali.add(specie);

		}

	}

	public void buildListProdottiPnaa(Connection db) throws SQLException {
		PreparedStatement pst1 = db
				.prepareStatement(" select p.description as prodotto_descrizione ,p.code as id_prodotto "
						+ " from campione_prodotti_pnaa cpa "
						+ " join lookup_prodotti_pnaa p on p.code = cpa.id_prodotto "
						+ " where id_campione = ?");

		pst1.setInt(1, this.idCampione);
		ResultSet rs = pst1.executeQuery();

		while (rs.next()) {
			int idProd = rs.getInt("id_prodotto");
			String descrProdotto = rs.getString("prodotto_descrizione");
			listaProdottiPnaa.add(descrProdotto);

		}

	}

	public void buildPerContoDi(Connection db) throws SQLException {
		
		int motivazione=-1;
		PreparedStatement pst = db.prepareStatement ("select motivazione_piano_campione from ticket where ticketid = ? ");
		pst.setInt(1, idCampione);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			motivazione = rs.getInt("motivazione_piano_campione");
		}
		
		PreparedStatement pst1 = db
				.prepareStatement(" select od.descrizione_lunga from tipocontrolloufficialeimprese tcu "+
						" left join oia_nodo od on tcu.id_unita_operativa = od.id "+  
						" left join oia_nodo padre on padre.id = od.id_padre" + 
						" where idcontrollo = ? and id_unita_operativa > 0  and pianomonitoraggio = ?");

		pst1.setInt(1, idControlloUfficiale);
		pst1.setInt(2, motivazione);
		ResultSet rs1 = pst1.executeQuery();

		while (rs1.next()) {
			String perContoDi = rs1.getString("descrizione_lunga");
			setPerContoDi(perContoDi);
		}

	}
}