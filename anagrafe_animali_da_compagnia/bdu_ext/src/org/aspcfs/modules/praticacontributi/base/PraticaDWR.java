/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.praticacontributi.base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PraticaDWR {
	
	private int id 									= -1; //identificativo della pratica - chiave univoca
	private String oggettoPratica 		   			= null;//descrizione della pratica
	
	private int totale_cani_catturati	   			= 0; // numero totale di cani catturati (proprietario sindaco- detentore canile) che possono partecipare al pagamento del contributo per la pratica in questione
	private int totale_cani_padronali	   			= 0; // numero totale di cani padronali che possono partecipare al pagamento del contributo per la pratica in questione

	private int totale_cani_maschi	   			= 0; // numero totale di cani catturati (proprietario sindaco- detentore canile) che possono partecipare al pagamento del contributo per la pratica in questione
	private int totale_cani_femmina	   			= 0;
	
	private int totale_gatti_catturati	   			= 0; // numero totale di gatti catturati (proprietario sindaco- detentore centro di sterilizzazione) che possono partecipare al pagamento del contributo per la pratica in questione
	private int totale_gatti_padronali	   			= 0; // numero totale di gatti padronali che possono partecipare al pagamento del contributo per la pratica in questione
	
	private int cani_restanti_padronali				= 0; //valore corrente del numero di cani padronali che possono ancora accedere alla pratica. Ogni volta che viene inserito un cane per tale pratica il valore viene decrementato con limite minimo pari a zero. Inizialmente e' settato con il numero totale di cani padronali
	private int gatti_restanti_padronali			= 0; //valore corrente del numero di gatti padronali che possono ancora accedere alla pratica. Ogni volta che viene inserito un gatto per tale pratica il valore viene decrementato con limite minimo pari a zero. Inizialmente e' settato con il numero totale di gatti padronali
	private int cani_restanti_catturati				= 0; //valore corrente del numero di cani catturati che possono ancora accedere alla pratica. Ogni volta che viene inserito un cane per tale pratica il valore viene decrementato con limite minimo pari a zero. Inizialmente e' settato con il numero totale di cani catturati
	private int gatti_restanti_catturati			= 0; //valore corrente del numero di gatti catturati che possono ancora accedere alla pratica. Ogni volta che viene inserito un gatto per tale pratica il valore viene decrementato con limite minimo pari a zero. Inizialmente e' settato con il numero totale di gatti catturati
	
	private int cani_restanti_maschi				= 0;
	private int cani_restanti_femmina				= 0;
	
	private int id_asl_pratica			   			= -1; //codice identificativo dell'asl
	private Timestamp data_decreto 		   			= null;//data di emissione del decreto per la pratica
	private String data_decreto_stringa= "";
	
	private int numero_decreto_pratica     			= 0; //numero del decreto della pratica
	  
	private Timestamp data_inizio_sterilizzazione  	= null; //data inizio pagamento di contributo per le sterilizzazioni
	private String data_inizio_sterilizzazione_stringa = "";
	
	private Timestamp data_fine_sterilizzazione	   	= null; //data fine pagamento di contributo per le sterilizzazioni
	private String data_fine_sterilizzazione_stringa= "";

	private ArrayList<String> elenco_comuni         = null;
	private ArrayList<String> elenco_canili         = null;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	public int getTotale_cani_maschi() {
		return totale_cani_maschi;
	}

	public void setTotale_cani_maschi(int totale_cani_maschi) {
		this.totale_cani_maschi = totale_cani_maschi;
	}
	
	public int getTotale_cani_femmina() {
		return totale_cani_femmina;
	}

	public void setTotale_cani_femmina(int totale_cani_femmina) {
		this.totale_cani_femmina = totale_cani_femmina;
	}
	
	
	

	public int getTotale_cani_catturati() {
		return totale_cani_catturati;
	}

	public void setTotale_cani_catturati(int totale_cani_catturati) {
		this.totale_cani_catturati = totale_cani_catturati;
	}

	public int getTotale_cani_padronali() {
		return totale_cani_padronali;
	}

	public void setTotale_cani_padronali(int totale_cani_padronali) {
		this.totale_cani_padronali = totale_cani_padronali;
	}

	public int getId_asl_pratica() {
		return id_asl_pratica;
	}

	public void setId_asl_pratica(int id_asl_pratica) {
		this.id_asl_pratica = id_asl_pratica;
	}
	
	public void setCani_restanti_maschi(int cani_restanti_maschi) {
		this.cani_restanti_maschi = cani_restanti_maschi;
	}

	public int getCani_restanti_maschi() {
		return cani_restanti_maschi;
	}
	
	public void setCani_restanti_femmina(int cani_restanti_femmina) {
		this.cani_restanti_femmina = cani_restanti_femmina;
	}

	public int getCani_restanti_femmina() {
		return cani_restanti_femmina;
	}

	public void setCani_restanti_padronali(int cani_restanti_padronali) {
		this.cani_restanti_padronali = cani_restanti_padronali;
	}

	public int getCani_restanti_padronali() {
		return cani_restanti_padronali;
	}

	public void setGatti_restanti_padronali(int gatti_restanti_padronali) {
		this.gatti_restanti_padronali = gatti_restanti_padronali;
	}

	public int getGatti_restanti_padronali() {
		return gatti_restanti_padronali;
	}

	public void setCani_restanti_catturati(int cani_restanti_catturati) {
		this.cani_restanti_catturati = cani_restanti_catturati;
	}

	public int getCani_restanti_catturati() {
		return cani_restanti_catturati;
	}

	public void setGatti_restanti_catturati(int gatti_restanti_catturati) {
		this.gatti_restanti_catturati = gatti_restanti_catturati;
	}

	public int getGatti_restanti_catturati() {
		return gatti_restanti_catturati;
	}

	public void setData_inizio_sterilizzazione(	Timestamp data_inizio_sterilizzazione) {
		this.data_inizio_sterilizzazione = data_inizio_sterilizzazione;
		setData_inizio_sterilizzazione_stringa(data_inizio_sterilizzazione);
	}

	public Timestamp getData_inizio_sterilizzazione() {
		return data_inizio_sterilizzazione;
	}

	private void setData_inizio_sterilizzazione_stringa(Timestamp data_inizio_sterilizzazione) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_inizio_sterilizzazione!=null){
			data_inizio_sterilizzazione_stringa=sdf.format(data_inizio_sterilizzazione);
		}
	}

	public String getData_inizio_sterilizzazione_stringa() {
		return data_inizio_sterilizzazione_stringa ;
	}
	
	public void setData_fine_sterilizzazione(Timestamp data_fine_sterilizzazione) {
		this.data_fine_sterilizzazione = data_fine_sterilizzazione;
		setData_fine_sterilizzazione_stringa(data_fine_sterilizzazione);
	}

	public Timestamp getData_fine_sterilizzazione() {
		return data_fine_sterilizzazione;
	}

	private void setData_fine_sterilizzazione_stringa(Timestamp data_fine_sterilizzazione) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_fine_sterilizzazione!=null){
			data_fine_sterilizzazione_stringa=sdf.format(data_fine_sterilizzazione);
		}
	}

	public String getData_fine_sterilizzazione_stringa() {
		return data_fine_sterilizzazione_stringa;
	}

	
	public void setData_decreto(Timestamp data_decreto2) {
		this.data_decreto = data_decreto2;
		setData_decreto_stringa(data_decreto);	
	}

	private void setData_decreto_stringa(Timestamp data_decreto2) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_decreto2!=null){
			data_decreto_stringa=sdf.format(data_decreto2);
		}
	}

	public String getData_decreto_stringa() {
		return data_decreto_stringa;
	}


	public Timestamp getData_decreto() {
		return data_decreto;
	}

	public void setTotale_gatti_catturati(int totale_gatti_catturati) {
		this.totale_gatti_catturati = totale_gatti_catturati;
	}

	public int getTotale_gatti_catturati() {
		return totale_gatti_catturati;
	}

	public void setTotale_gatti_padronali(int totale_gatti_padronali) {
		this.totale_gatti_padronali = totale_gatti_padronali;
	}

	public int getTotale_gatti_padronali() {
		return totale_gatti_padronali;
	}

	public void setElenco_comuni(ArrayList<String> elenco_comuni) {
		this.elenco_comuni = elenco_comuni;
	}

	public void setElenco_canili(ArrayList<String> elenco_canili) {
		this.elenco_canili = elenco_canili;
	}
	
	public ArrayList<String> getElencoCanili() {
		return elenco_canili;
	}
	
	public ArrayList<String> getElenco_comuni() {
		return elenco_comuni;
	}

	public void setOggettoPratica(String oggettoPratica) {
		this.oggettoPratica = oggettoPratica;
	}

	public String getOggettoPratica() {
		return oggettoPratica;
	}

	

	public void setNumero_decreto_pratica(int numero_decreto_pratica) {
		this.numero_decreto_pratica = numero_decreto_pratica;
	
	}

	public int getNumero_decreto_pratica() {
		return numero_decreto_pratica;
	}
	
	

}
