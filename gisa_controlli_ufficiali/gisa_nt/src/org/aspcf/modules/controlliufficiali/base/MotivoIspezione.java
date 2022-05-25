/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.aspcfs.modules.vigilanza.base.Ticket;

import com.darkhorseventures.framework.actions.ActionContext;


public class MotivoIspezione {
	
	boolean pianoMonitoraggio = false;
	boolean sorveglianza;
	boolean sistemaAllarmeRapido = false;
	boolean siAltro = false;
	boolean verificaRisoluzioneNcPrec = false;
	boolean tossinfezione;
	boolean no = false;
		
	String descrizione;
	String dataInsorgenzaSintomi;
	String sospetto;
	String allarmeRapido;
	String piano1;
	String piano2;
	String pianoHtml;
	String attivitaHtml;
	String soggettiCoinvolti;
	String ricoverati;
	String alimentiSospetti;
	
	
	int n_capi = 0;
	int idControllo;
	
	private HashMap<Integer, String> tipoIspezione;
	private HashMap<Integer, String> listaSpecieTrasportata;
	
	ArrayList<String> tipoIsp;
	
	public String getPiano1() {
		return piano1;
	}

	public void setPiano1(String piano1) {
		this.piano1 = piano1;
	}

	public String getPiano2() {
		return piano2;
	}

	public void setPiano2(String piano2) {
		this.piano2 = piano2;
	}

	public String getPianoHtml() {
		return pianoHtml;
	}

	public void setPianoHtml(String pianoHtml) {
		this.pianoHtml = pianoHtml;
	}
	
	public boolean isSorveglianza() {
		return sorveglianza;
	}

	public void setSorveglianza(boolean sorveglianza) {
		this.sorveglianza = sorveglianza;
	}

	public boolean isSistemaAllarmeRapido() {
		return sistemaAllarmeRapido;
	}

	public void setSistemaAllarmeRapido(boolean sistemaAllarmeRapido) {
		this.sistemaAllarmeRapido = sistemaAllarmeRapido;
	}

	public boolean isSiAltro() {
		return siAltro;
	}

	public void setSiAltro(boolean siAltro) {
		this.siAltro = siAltro;
	}

	public boolean isNo() {
		return no;
	}

	public void setNo(boolean no) {
		this.no = no;
	}
	public boolean isTossinfezione() {
		return tossinfezione;
	}

	public void setTossinfezione(boolean tossinfezione) {
		this.tossinfezione = tossinfezione;
	}

	public String getSoggettiCoinvolti() {
		return soggettiCoinvolti;
	}

	public void setSoggettiCoinvolti(String soggettiCoinvolti) {
		this.soggettiCoinvolti = soggettiCoinvolti;
	}

	public String getRicoverati() {
		return ricoverati;
	}

	public void setRicoverati(String ricoverati) {
		this.ricoverati = ricoverati;
	}

	public String getAlimentiSospetti() {
		return alimentiSospetti;
	}

	public void setAlimentiSospetti(String alimentiSospetti) {
		this.alimentiSospetti = alimentiSospetti;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDataInsorgenzaSintomi() {
		return dataInsorgenzaSintomi;
	}

	public void setDataInsorgenzaSintomi(String dataInsorgenzaSintomi) {
		this.dataInsorgenzaSintomi = dataInsorgenzaSintomi;
	}

	public String getSospetto() {
		return sospetto;
	}

	public void setSospetto(String sospetto) {
		this.sospetto = sospetto;
	}

	public boolean isPianoMonitoraggio() {
		return pianoMonitoraggio;
	}
	
	public boolean isVerificaRisoluzioneNcPrec() {
		return verificaRisoluzioneNcPrec;
	}

	public void setVerificaRisoluzioneNcPrec(boolean verificaRisoluzioneNcPrec) {
		this.verificaRisoluzioneNcPrec = verificaRisoluzioneNcPrec;
	}

	public String getAllarmeRapido() {
		return allarmeRapido;
	}

	public void setAllarmeRapido(String allarmeRapido) {
		this.allarmeRapido = allarmeRapido;
	}
	
	public void setPianoMonitoraggio(boolean piano) {
		// TODO Auto-generated method stub
		this.pianoMonitoraggio = piano;
	}
	
	private void setTipoIspezione(HashMap<Integer, String> tipoIspezione) {
		// TODO Auto-generated method stub
		this.tipoIspezione = tipoIspezione;
	}

	private void setListaSpecieTrasportata(
			HashMap<Integer, String> listaSpecieTrasportata) {
		// TODO Auto-generated method stub
		this.listaSpecieTrasportata = listaSpecieTrasportata;
	}
	
	private void setIdControllo(int idControllo) {
		// TODO Auto-generated method stub
		this.idControllo = idControllo;
	}
	
	public int getIdControllo() {
		return idControllo;
	}
	
	private void setNcapi(int capo) {
		// TODO Auto-generated method stub
		this.n_capi = capo;
	}
	
	public int getNCapi() {
		return n_capi;
	}

	public MotivoIspezione() {}

	public MotivoIspezione(Connection db, int idControllo) {
		
		try {
			 
			this.setIdControllo(idControllo);
			Ticket cu = new Ticket(db, idControllo);
			HashMap<Integer, String> tipoIspezione = cu.getTipoIspezione();
			HashMap<Integer, String> listaSpecieTrasportata = cu.getListaAnimali_Ispezioni(); //mai funzionato!?
			
		    //tipoIsp = cu.getTipoIspezioneCodiceInterno();
			tipoIsp = cu.getTipoIspezioneCodiceInternoUnivoco();
			System.out.println("codice_interno:" + tipoIsp);
			
			this.setTipoIspezione(tipoIspezione);
			this.setListaSpecieTrasportata(listaSpecieTrasportata);
			
			if (cu.getTipoCampione()== 5) {
				this.setSorveglianza(true);
			}
			
			// Era on
			if (tipoIspezione.containsKey(2) || tipoIsp.contains("2a")) {
				this.setPianoMonitoraggio(true);
				String pianoScelto = "";
				String pianoSceltoHtml = "";
				// Specificare piano di monitoraggio
				/*Iterator<Integer> kiave = cu.getLisaElementipianoMonitoraggio_ispezioni().keySet().iterator();
				String pianoScelto = "";
				while (kiave.hasNext()) {
					pianoScelto += cu.getLisaElementipianoMonitoraggio_ispezioni().get(kiave.next())+"-";
				}
				if(pianoScelto.length() > 120){
					this.setPiano1(pianoScelto.substring(0,121));
					this.setPiano2(pianoScelto.substring(121));
				}
				else {
					this.setPiano1(pianoScelto);
				}*/
				ArrayList<Piano> piani = cu.getPianoMonitoraggio();
				for (Piano p : piani)
				{
					pianoScelto += " - " +p.getDescrizione();
					pianoSceltoHtml += " - " + p.getDescrizione() + "</br>";

				}
				
				this.setPiano1(pianoScelto);
				this.setPianoHtml(pianoSceltoHtml);
					
				
				
			}
			
			String attivitaDescr = "";
			Set list  = tipoIspezione.keySet();
			Iterator iter = list.iterator();
			while(iter.hasNext()) {
			     Integer key = (Integer) iter.next();
			     String value = (String) tipoIspezione.get(key);
			     if (key!=89) //escludo l'attivita' "Piano di monitoraggio"
			    	 attivitaDescr = attivitaDescr + value + "<br/>";
			}
			this.setAttivitaHtml(attivitaDescr);
			
			if (tipoIspezione.containsKey(7) ||  tipoIsp.contains("7a")) {
				this.setSistemaAllarmeRapido(true);
				this.setAllarmeRapido(cu.getCodiceAllerta());
			}
			if (tipoIspezione.containsKey(8) ||  tipoIsp.contains("8a")) {
				this.setVerificaRisoluzioneNcPrec(true);
				//form.setField("verifica_risoluzione_nc_prec", "Yes");
				// System.out.println("Valore di isAzione: "+cu.isAzione());
				if (cu.isAzione()) {
					this.setSiAltro(true);
					this.setDescrizione(cu.getAzione_descrizione());
				}
				else{
					this.setDescrizione("");
					
				}
			}
			
			/*if (tipoIspezione.containsKey(16)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				this.setTossinfezione(true);
				this.setSoggettiCoinvolti(cu.getSoggettiCoinvolti());
				this.setRicoverati(cu.getRicoverati());
				this.setAlimentiSospetti(cu.getAlimentiSospetti());
			
				String dataSintomi = sdf.format(new Date(cu.getDataSintomi()
						.getTime()));
				String dataPasto = sdf.format(new Date(cu.getDataPasto()
						.getTime()));
				if (dataSintomi != null) {
					//form.setField("data_insorgenza_sintomi", dataSintomi);
					this.setDataInsorgenzaSintomi(dataSintomi);
				}
				if (dataPasto != null) {
					//form.setField("sospetto", dataPasto);
					this.setSospetto(dataPasto);
				}
				//form.setField("no", "Yes");
				//this.setNo(false);

			}*/
			
			//for ( int key : listaSpecieTrasportata.keySet() ){
			for ( int key=1;key<=27;key++ ){
				
				if(key == 1 && cu.getNum_specie1()>0) {
					n_capi += cu.getNum_specie1();
				}
				else if(key == 2 && cu.getNum_specie2()>0) {
					n_capi += cu.getNum_specie2();
				}
				else if(key == 4 && cu.getNum_specie3()>0) {
					n_capi += cu.getNum_specie3();
				}
				else if(key == 6 && cu.getNum_specie4()>0) {
					n_capi += cu.getNum_specie4();
				}
				else if(key == 10 && cu.getNum_specie5()>0) {
					n_capi += cu.getNum_specie5();
				}
				else if(key == 11 && cu.getNum_specie6()>0) {
					n_capi += cu.getNum_specie6();
				}
				else if(key == 12 && cu.getNum_specie7()>0) {
					n_capi += cu.getNum_specie7();
				}
				else if(key == 13 && cu.getNum_specie8()>0) {
					n_capi += cu.getNum_specie8();
				}
				else if(key == 14 && cu.getNum_specie9()>0) {
					n_capi += cu.getNum_specie9();
				}
				else if(key == 15 && cu.getNum_specie10()>0) {
					n_capi += cu.getNum_specie10();
				}
				else if(key == 16 && cu.getNum_specie11()>0) {
					n_capi += cu.getNum_specie11();
				}
				else if(key == 18 && cu.getNum_specie12()>0) {
					n_capi += cu.getNum_specie12();
				}
				else if(key == 19 && cu.getNum_specie13()>0) {
					n_capi += cu.getNum_specie13();
				}
				else if(key == 20 && cu.getNum_specie14()>0) {
					n_capi += cu.getNum_specie14();
				}
				else if(key == 21 && cu.getNum_specie15()>0) {
					n_capi += cu.getNum_specie15();
				}
				else if(key == 22 && cu.getNum_specie22()>0) {
					n_capi += cu.getNum_specie22();
				}
				else if(key == 23 && cu.getNum_specie23()>0) {
					n_capi += cu.getNum_specie23();
				}
				else if(key == 24 && cu.getNum_specie24()>0) {
					n_capi += cu.getNum_specie24();
				}
				else if(key == 25 && cu.getNum_specie25()>0) {
					n_capi += cu.getNum_specie25();
				}
				else if(key == 26 && cu.getNum_specie26()>0) {
					n_capi += cu.getNum_specie26();
				}
			}
			this.setNcapi(n_capi);
			
			//db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public boolean isChecked(int code){
		
		boolean checked = false;
		
		if(tipoIspezione.containsKey(code) || tipoIsp.contains("code")) {
			checked = true;	
		}
		else {
			checked = false;
		}
		return checked;
	}
	
public boolean isChecked(String codiceInternoUnivoco){
		
		boolean checked = false;
		
		if(tipoIsp.contains(codiceInternoUnivoco)) {
			checked = true;	
		}
		else {
			checked = false;
		}
		return checked;
	}
	
	
	public boolean isSpecieChecked(int code) {
		
		boolean checked = false;
		
	    for ( int key : listaSpecieTrasportata.keySet() ){
			if(key == code){
				checked = true;
				break;
			}						
			
		}
		
		return checked;	
		
			
	}

	public String getAttivitaHtml() {
		return attivitaHtml;
	}

	public void setAttivitaHtml(String attivitaHtml) {
		this.attivitaHtml = attivitaHtml;
	}
	
	
}
