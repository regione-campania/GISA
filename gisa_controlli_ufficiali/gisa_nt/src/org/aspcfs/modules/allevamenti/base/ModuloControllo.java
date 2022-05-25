/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;

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

public class ModuloControllo {
	
	private int idCampione = -1;
	private int specie = -1;

	private String	data_ultima_ristrutturazione	;
	private String	num_totale_capannoni	;
	private String	num_totale_capannoni_attivi	;
	private String	capannone_num_1	;
	private String	cap_massima_1	;
	private String	animali_presenti_1	;
	private String	num_totale_box_1	;
	private String	num_attivi_box_1	;
	private String	ispezionato_1	;
	private String	capannone_num_2	;
	private String	cap_massima_2	;
	private String	animali_presenti_2	;
	private String	num_totale_box_2	;
	private String	num_attivi_box_2	;
	private String	ispezionato_2	;
	private String	capannone_num_3	;
	private String	cap_massima_3	;
	private String	animali_presenti_3	;
	private String	num_totale_box_3	;
	private String	num_attivi_box_3	;
	private String	ispezionato_3	;
	private String	num_totale_animali	;
	private String	categorie_eta	;
	private String	cap_massima	;
	private String	veterinario_aziendale	;
	private String	num_totale_vitelli	;
	private String	num_vitelli_inf_8	;
	private String	cap_massima_vitelli	;
	private String	tipologia_allevamento	;
	private String	num_uova_anno	;
	private String	selezione_imballaggio	;
	private String	selezione_imballaggio_destinazione	;
	private String	capannone_num_4	;
	private String	cap_massima_4	;
	private String	animali_presenti_4	;
	private String	ispezionato_4	;
	private String	capannone_num_5	;
	private String	cap_massima_5	;
	private String	animali_presenti_5	;
	private String	ispezionato_5	;
	private String	muta	;
	private String	tipologia_allevamwento	;
	private String	tecnica_produttiva	;
	private String	categoria_presenti_1	;
	private String	categoria_presenti_2	;
	private String	categoria_presenti_3	;
	private String	num_totale_box_4	;
	private String	categoria_presenti_4	;
	private String	num_totale_box_5	;
	private String	categoria_presenti_5	;
	private String	artificiale	;
	private String	responsabile_piano	;
	


	public int getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(int idCampione) {
		this.idCampione = idCampione;
	}

	public int getSpecie() {
		return specie;
	}

	public void setSpecie(int specie) {
		this.specie = specie;
	}

	public String getData_ultima_ristrutturazione() {
		return data_ultima_ristrutturazione;
	}

	public void setData_ultima_ristrutturazione(String data_ultima_ristrutturazione) {
		this.data_ultima_ristrutturazione = data_ultima_ristrutturazione;
	}

	public String getNum_totale_capannoni() {
		return num_totale_capannoni;
	}

	public void setNum_totale_capannoni(String num_totale_capannoni) {
		this.num_totale_capannoni = num_totale_capannoni;
	}

	public String getNum_totale_capannoni_attivi() {
		return num_totale_capannoni_attivi;
	}

	public void setNum_totale_capannoni_attivi(String num_totale_capannoni_attivi) {
		this.num_totale_capannoni_attivi = num_totale_capannoni_attivi;
	}

	public String getCapannone_num_1() {
		return capannone_num_1;
	}

	public void setCapannone_num_1(String capannone_num_1) {
		this.capannone_num_1 = capannone_num_1;
	}

	public String getCap_massima_1() {
		return cap_massima_1;
	}

	public void setCap_massima_1(String cap_massima_1) {
		this.cap_massima_1 = cap_massima_1;
	}

	public String getAnimali_presenti_1() {
		return animali_presenti_1;
	}

	public void setAnimali_presenti_1(String animali_presenti_1) {
		this.animali_presenti_1 = animali_presenti_1;
	}

	public String getNum_totale_box_1() {
		return num_totale_box_1;
	}

	public void setNum_totale_box_1(String num_totale_box_1) {
		this.num_totale_box_1 = num_totale_box_1;
	}

	public String getNum_attivi_box_1() {
		return num_attivi_box_1;
	}

	public void setNum_attivi_box_1(String num_attivi_box_1) {
		this.num_attivi_box_1 = num_attivi_box_1;
	}

	public String getIspezionato_1() {
		return ispezionato_1;
	}

	public void setIspezionato_1(String ispezionato_1) {
		this.ispezionato_1 = ispezionato_1;
	}

	public String getCapannone_num_2() {
		return capannone_num_2;
	}

	public void setCapannone_num_2(String capannone_num_2) {
		this.capannone_num_2 = capannone_num_2;
	}

	public String getCap_massima_2() {
		return cap_massima_2;
	}

	public void setCap_massima_2(String cap_massima_2) {
		this.cap_massima_2 = cap_massima_2;
	}

	public String getAnimali_presenti_2() {
		return animali_presenti_2;
	}

	public void setAnimali_presenti_2(String animali_presenti_2) {
		this.animali_presenti_2 = animali_presenti_2;
	}

	public String getNum_totale_box_2() {
		return num_totale_box_2;
	}

	public void setNum_totale_box_2(String num_totale_box_2) {
		this.num_totale_box_2 = num_totale_box_2;
	}

	public String getNum_attivi_box_2() {
		return num_attivi_box_2;
	}

	public void setNum_attivi_box_2(String num_attivi_box_2) {
		this.num_attivi_box_2 = num_attivi_box_2;
	}

	public String getIspezionato_2() {
		return ispezionato_2;
	}

	public void setIspezionato_2(String ispezionato_2) {
		this.ispezionato_2 = ispezionato_2;
	}

	public String getCapannone_num_3() {
		return capannone_num_3;
	}

	public void setCapannone_num_3(String capannone_num_3) {
		this.capannone_num_3 = capannone_num_3;
	}

	public String getCap_massima_3() {
		return cap_massima_3;
	}

	public void setCap_massima_3(String cap_massima_3) {
		this.cap_massima_3 = cap_massima_3;
	}

	public String getAnimali_presenti_3() {
		return animali_presenti_3;
	}

	public void setAnimali_presenti_3(String animali_presenti_3) {
		this.animali_presenti_3 = animali_presenti_3;
	}

	public String getNum_totale_box_3() {
		return num_totale_box_3;
	}

	public void setNum_totale_box_3(String num_totale_box_3) {
		this.num_totale_box_3 = num_totale_box_3;
	}

	public String getNum_attivi_box_3() {
		return num_attivi_box_3;
	}

	public void setNum_attivi_box_3(String num_attivi_box_3) {
		this.num_attivi_box_3 = num_attivi_box_3;
	}

	public String getIspezionato_3() {
		return ispezionato_3;
	}

	public void setIspezionato_3(String ispezionato_3) {
		this.ispezionato_3 = ispezionato_3;
	}

	public String getNum_totale_animali() {
		return num_totale_animali;
	}

	public void setNum_totale_animali(String num_totale_animali) {
		this.num_totale_animali = num_totale_animali;
	}

	public String getCategorie_eta() {
		return categorie_eta;
	}

	public void setCategorie_eta(String categorie_eta) {
		this.categorie_eta = categorie_eta;
	}

	public String getCap_massima() {
		return cap_massima;
	}

	public void setCap_massima(String cap_massima) {
		this.cap_massima = cap_massima;
	}

	public String getVeterinario_aziendale() {
		return veterinario_aziendale;
	}

	public void setVeterinario_aziendale(String veterinario_aziendale) {
		this.veterinario_aziendale = veterinario_aziendale;
	}

	public String getNum_totale_vitelli() {
		return num_totale_vitelli;
	}

	public void setNum_totale_vitelli(String num_totale_vitelli) {
		this.num_totale_vitelli = num_totale_vitelli;
	}

	public String getNum_vitelli_inf_8() {
		return num_vitelli_inf_8;
	}

	public void setNum_vitelli_inf_8(String num_vitelli_inf_8) {
		this.num_vitelli_inf_8 = num_vitelli_inf_8;
	}

	public String getCap_massima_vitelli() {
		return cap_massima_vitelli;
	}

	public void setCap_massima_vitelli(String cap_massima_vitelli) {
		this.cap_massima_vitelli = cap_massima_vitelli;
	}

	public String getTipologia_allevamento() {
		return tipologia_allevamento;
	}

	public void setTipologia_allevamento(String tipologia_allevamento) {
		this.tipologia_allevamento = tipologia_allevamento;
	}

	public String getNum_uova_anno() {
		return num_uova_anno;
	}

	public void setNum_uova_anno(String num_uova_anno) {
		this.num_uova_anno = num_uova_anno;
	}

	public String getSelezione_imballaggio() {
		return selezione_imballaggio;
	}

	public void setSelezione_imballaggio(String selezione_imballaggio) {
		this.selezione_imballaggio = selezione_imballaggio;
	}

	public String getSelezione_imballaggio_destinazione() {
		return selezione_imballaggio_destinazione;
	}

	public void setSelezione_imballaggio_destinazione(String selezione_imballaggio_destinazione) {
		this.selezione_imballaggio_destinazione = selezione_imballaggio_destinazione;
	}

	public String getCapannone_num_4() {
		return capannone_num_4;
	}

	public void setCapannone_num_4(String capannone_num_4) {
		this.capannone_num_4 = capannone_num_4;
	}

	public String getCap_massima_4() {
		return cap_massima_4;
	}

	public void setCap_massima_4(String cap_massima_4) {
		this.cap_massima_4 = cap_massima_4;
	}

	public String getAnimali_presenti_4() {
		return animali_presenti_4;
	}

	public void setAnimali_presenti_4(String animali_presenti_4) {
		this.animali_presenti_4 = animali_presenti_4;
	}

	public String getIspezionato_4() {
		return ispezionato_4;
	}

	public void setIspezionato_4(String ispezionato_4) {
		this.ispezionato_4 = ispezionato_4;
	}

	public String getCapannone_num_5() {
		return capannone_num_5;
	}

	public void setCapannone_num_5(String capannone_num_5) {
		this.capannone_num_5 = capannone_num_5;
	}

	public String getCap_massima_5() {
		return cap_massima_5;
	}

	public void setCap_massima_5(String cap_massima_5) {
		this.cap_massima_5 = cap_massima_5;
	}

	public String getAnimali_presenti_5() {
		return animali_presenti_5;
	}

	public void setAnimali_presenti_5(String animali_presenti_5) {
		this.animali_presenti_5 = animali_presenti_5;
	}

	public String getIspezionato_5() {
		return ispezionato_5;
	}

	public void setIspezionato_5(String ispezionato_5) {
		this.ispezionato_5 = ispezionato_5;
	}

	public String getMuta() {
		return muta;
	}

	public void setMuta(String muta) {
		this.muta = muta;
	}

	public String getTipologia_allevamwento() {
		return tipologia_allevamwento;
	}

	public void setTipologia_allevamwento(String tipologia_allevamwento) {
		this.tipologia_allevamwento = tipologia_allevamwento;
	}

	public String getTecnica_produttiva() {
		return tecnica_produttiva;
	}

	public void setTecnica_produttiva(String tecnica_produttiva) {
		this.tecnica_produttiva = tecnica_produttiva;
	}

	public String getCategoria_presenti_1() {
		return categoria_presenti_1;
	}

	public void setCategoria_presenti_1(String categoria_presenti_1) {
		this.categoria_presenti_1 = categoria_presenti_1;
	}

	public String getCategoria_presenti_2() {
		return categoria_presenti_2;
	}

	public void setCategoria_presenti_2(String categoria_presenti_2) {
		this.categoria_presenti_2 = categoria_presenti_2;
	}

	public String getCategoria_presenti_3() {
		return categoria_presenti_3;
	}

	public void setCategoria_presenti_3(String categoria_presenti_3) {
		this.categoria_presenti_3 = categoria_presenti_3;
	}

	public String getNum_totale_box_4() {
		return num_totale_box_4;
	}

	public void setNum_totale_box_4(String num_totale_box_4) {
		this.num_totale_box_4 = num_totale_box_4;
	}

	public String getCategoria_presenti_4() {
		return categoria_presenti_4;
	}

	public void setCategoria_presenti_4(String categoria_presenti_4) {
		this.categoria_presenti_4 = categoria_presenti_4;
	}

	public String getNum_totale_box_5() {
		return num_totale_box_5;
	}

	public void setNum_totale_box_5(String num_totale_box_5) {
		this.num_totale_box_5 = num_totale_box_5;
	}

	public String getCategoria_presenti_5() {
		return categoria_presenti_5;
	}

	public void setCategoria_presenti_5(String categoria_presenti_5) {
		this.categoria_presenti_5 = categoria_presenti_5;
	}

	public String getArtificiale() {
		return artificiale;
	}

	public void setArtificiale(String artificiale) {
		this.artificiale = artificiale;
	}

	public String getResponsabile_piano() {
		return responsabile_piano;
	}

	public void setResponsabile_piano(String responsabile_piano) {
		this.responsabile_piano = responsabile_piano;
	}



	//kiave = nomeCampo
	// value = hashmap<String ,String>
	//kiave = valorecampo
	HashMap<String,HashMap<String ,String>> listaCampiModulo = new HashMap<String, HashMap<String ,String>>();
	
	
	public HashMap<String, HashMap<String,String>> getListaCampiModulo() {
		return listaCampiModulo;
	}

	public void setListaCampiModulo(HashMap<String, HashMap<String,String>> listaCampiModulo) {
		this.listaCampiModulo = listaCampiModulo;
	}

	

	public ModuloControllo() throws SQLException {
	}

	

	
	

	
}