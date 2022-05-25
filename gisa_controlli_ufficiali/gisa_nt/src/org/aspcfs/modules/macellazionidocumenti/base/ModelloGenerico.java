/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionidocumenti.base;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.aspcfs.modules.macellazioni.base.Art17;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazioni.base.ChiaveModuliMacelli;
import org.aspcfs.modules.macellazioni.base.NonConformita;
import org.aspcfs.modules.macellazioni.base.Organi;
import org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazioni.base.StampeModuli;
import org.aspcfs.modules.macellazioni.base.StampeModuliDao;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.speditori.base.Organization;
import org.aspcfs.utils.DateUtils;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.beans.GenericBean;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;

public class ModelloGenerico  extends GenericBean {
	
	//private String tipo = null; 
	private int idMacello = -1; 
	private Timestamp data = null;
	private int tipoModulo = -1;
	private int progressivo = -1;
	private int oldProgressivo = -1;
	private int id;
	private int hashCode;
	private String matricolaCapo;
	private int idSpeditore;
	private String malattiaCapo;
	private int aslMacello = -1;
	private ModelloGenerico mod2 = null;
	
	
	private ArrayList<Capo> listaCapi; 
	private ArrayList<Capo> listaCapi_marchi;
	private TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC;
	private TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti;
	private ArrayList<Capo> listaCapiBrucellosi;
	private ArrayList<Capo> listaCapiTubercolosi;
	private ArrayList<Capo> listaCapiGravidi ;
	private TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreTBC;
	private TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiTBC;
	private TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC;
	private TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiBRC;
	private ArrayList<Capo> listaCapi_1033_tbc;
	private TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani;
	private ArrayList<Capo> listaCapi_ante_mortem;
	private TreeMap<Integer, ArrayList<NonConformita>> hashCapiNCAnteMortem;
	private ArrayList<Capo> listaCapiMortiAnte;
	private TreeMap<Integer, Capo> hashCapoLEB ;
	private TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreLEB ;
	private ArrayList<Capo> listaCapiTrasportati;
	private ArrayList<ModelloGenerico> listaModelli = new  ArrayList<ModelloGenerico>();
	private HashMap <Integer, Integer> hashCategorieBovine = new  HashMap<Integer, Integer>();
	private HashMap <Integer, Integer> hashCategorieBufaline = new  HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> hashCategoriaNumeroInfettiBovini = new  HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> hashCategoriaNumeroInfettiBufalini = new  HashMap<Integer, Integer>();
	
	public ModelloGenerico getMod2() { 
		return mod2;
	}

	public void setMod2(ModelloGenerico mod2) {
		this.mod2 = mod2;
	}

	public int getAnnoDataModulo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return Integer.parseInt( sdf.format(this.getData()));
	}
	public int getGiornoDataModulo(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return Integer.parseInt( sdf.format(this.getData()));
	}
	public String getMeseFromDataModulo(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String[] mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
		int mese = Integer.parseInt( sdf.format(this.getData()));
		return mesi[mese-1];
	}
	
	public HashMap<Integer, Integer> getHashCategoriaNumeroInfettiBovini() {
		return hashCategoriaNumeroInfettiBovini;
	}

	public void setHashCategoriaNumeroInfettiBovini(
			HashMap<Integer, Integer> hashCategoriaNumeroInfettiBovini) {
		this.hashCategoriaNumeroInfettiBovini = hashCategoriaNumeroInfettiBovini;
	}

	public HashMap<Integer, Integer> getHashCategoriaNumeroInfettiBufalini() {
		return hashCategoriaNumeroInfettiBufalini;
	}

	public void setHashCategoriaNumeroInfettiBufalini(
			HashMap<Integer, Integer> hashCategoriaNumeroInfettiBufalini) {
		this.hashCategoriaNumeroInfettiBufalini = hashCategoriaNumeroInfettiBufalini;
	}

	public int getAslMacello() {
		return aslMacello;
	}
	public void setAslMacello(int aslMacello) {
		this.aslMacello = aslMacello;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	public String getMatricolaCapo() {
		return matricolaCapo;
	}
	public void setMatricolaCapo(String matricolaCapo) {
		this.matricolaCapo = matricolaCapo;
	}
	public int getIdSpeditore() {
		return idSpeditore;
	}
	public void setIdSpeditore(int idSpeditore) {
		this.idSpeditore = idSpeditore;
	}
	public String getMalattiaCapo() {
		return malattiaCapo;
	}
	public void setMalattiaCapo(String malattiaCapo) {
		this.malattiaCapo = malattiaCapo;
	}
	public int getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}
	public int getOldProgressivo() {
		return oldProgressivo;
	}
	public void setOldProgressivo(int oldProgressivo) {
		this.oldProgressivo = oldProgressivo;
	}
	public ArrayList<Capo> getListaCapi() {
		return listaCapi;
	}
	public void setListaCapi(ArrayList<Capo> listaCapi) {
		this.listaCapi = listaCapi;
	}
	public ArrayList<Capo> getListaCapi_marchi() {
		return listaCapi_marchi;
	}
	public void setListaCapi_marchi(ArrayList<Capo> listaCapi_marchi) {
		this.listaCapi_marchi = listaCapi_marchi;
	}
	public TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> getHashCapiNC() {
		return hashCapiNC;
	}
	public void setHashCapiNC(
			TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC) {
		this.hashCapiNC = hashCapiNC;
	}
	public TreeMap<Integer, ArrayList<ProvvedimentiCASL>> getHashCapiProvvedimenti() {
		return hashCapiProvvedimenti;
	}
	public void setHashCapiProvvedimenti(
			TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti) {
		this.hashCapiProvvedimenti = hashCapiProvvedimenti;
	}
	public ArrayList<Capo> getListaCapiBrucellosi() {
		return listaCapiBrucellosi;
	}
	public void setListaCapiBrucellosi(ArrayList<Capo> listaCapiBrucellosi) {
		this.listaCapiBrucellosi = listaCapiBrucellosi;
	}
	public ArrayList<Capo> getListaCapiTubercolosi() {
		return listaCapiTubercolosi;
	}
	public void setListaCapiTubercolosi(ArrayList<Capo> listaCapiTubercolosi) {
		this.listaCapiTubercolosi = listaCapiTubercolosi;
	}
	public ArrayList<Capo> getListaCapiGravidi() {
		return listaCapiGravidi;
	}
	public void setListaCapiGravidi(ArrayList<Capo> listaCapiGravidi) {
		this.listaCapiGravidi = listaCapiGravidi;
	}
	public TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> getHashSpeditoreTBC() {
		return hashSpeditoreTBC;
	}
	public void setHashSpeditoreTBC(
			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreTBC) {
		this.hashSpeditoreTBC = hashSpeditoreTBC;
	}
	public TreeMap<Integer, ArrayList<Capo>> getHashSpeditoreCapiTBC() {
		return hashSpeditoreCapiTBC;
	}
	public void setHashSpeditoreCapiTBC(
			TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiTBC) {
		this.hashSpeditoreCapiTBC = hashSpeditoreCapiTBC;
	}
	public TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> getHashSpeditoreBRC() {
		return hashSpeditoreBRC;
	}
	public void setHashSpeditoreBRC(
			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC) {
		this.hashSpeditoreBRC = hashSpeditoreBRC;
	}
	public TreeMap<Integer, ArrayList<Capo>> getHashSpeditoreCapiBRC() {
		return hashSpeditoreCapiBRC;
	}
	public void setHashSpeditoreCapiBRC(
			TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiBRC) {
		this.hashSpeditoreCapiBRC = hashSpeditoreCapiBRC;
	}
	public ArrayList<Capo> getListaCapi_1033_tbc() {
		return listaCapi_1033_tbc;
	}
	public void setListaCapi_1033_tbc(ArrayList<Capo> listaCapi_1033_tbc) {
		this.listaCapi_1033_tbc = listaCapi_1033_tbc;
	}
	public TreeMap<Integer, ArrayList<Organi>> getHashCapiOrgani() {
		return hashCapiOrgani;
	}
	public void setHashCapiOrgani(TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani) {
		this.hashCapiOrgani = hashCapiOrgani;
	}
	public ArrayList<Capo> getListaCapi_ante_mortem() {
		return listaCapi_ante_mortem;
	}
	public void setListaCapi_ante_mortem(ArrayList<Capo> listaCapi_ante_mortem) {
		this.listaCapi_ante_mortem = listaCapi_ante_mortem;
	}
	public TreeMap<Integer, ArrayList<NonConformita>> getHashCapiNCAnteMortem() {
		return hashCapiNCAnteMortem;
	}
	public void setHashCapiNCAnteMortem(
			TreeMap<Integer, ArrayList<NonConformita>> hashCapiNCAnteMortem) {
		this.hashCapiNCAnteMortem = hashCapiNCAnteMortem;
	}
	public ArrayList<Capo> getListaCapiMortiAnte() {
		return listaCapiMortiAnte;
	}
	public void setListaCapiMortiAnte(ArrayList<Capo> listaCapiMortiAnte) {
		this.listaCapiMortiAnte = listaCapiMortiAnte;
	}
	public TreeMap<Integer, Capo> getHashCapoLEB() {
		return hashCapoLEB;
	}
	public void setHashCapoLEB(TreeMap<Integer, Capo> hashCapoLEB) {
		this.hashCapoLEB = hashCapoLEB;
	}
	public TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> getHashSpeditoreLEB() {
		return hashSpeditoreLEB;
	}
	public void setHashSpeditoreLEB(
			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreLEB) {
		this.hashSpeditoreLEB = hashSpeditoreLEB;
	}
	public ArrayList<Capo> getListaCapiTrasportati() {
		return listaCapiTrasportati;
	}
	public void setListaCapiTrasportati(ArrayList<Capo> listaCapiTrasportati) {
		this.listaCapiTrasportati = listaCapiTrasportati;
	}
	public int getTipoModulo() {
		return tipoModulo;
	}
	public void setTipoModulo(String tipoModulo) {
		if (tipoModulo!=null && !tipoModulo.equals("") && !tipoModulo.equals("null"))
		this.tipoModulo = Integer.parseInt(tipoModulo);
	}
	public void setTipoModulo(int tipoModulo) {
		this.tipoModulo = tipoModulo;
	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public void setIdMacello(String idMacello) {
		if (idMacello!=null && !idMacello.equals("null") && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}
	
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public void setData(String data) {
		this.data = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	
	public ArrayList<ModelloGenerico> getListaModelli() {
		return listaModelli;
	}
	public void setListaModelli(ArrayList<ModelloGenerico> listaModelli) {
		this.listaModelli = listaModelli;
	}
	
	public HashMap<Integer, Integer> getHashCategorieBovine() {
		return hashCategorieBovine;
	}

	public void setHashCategorieBovine(HashMap<Integer, Integer> hashCategorieBovine) {
		this.hashCategorieBovine = hashCategorieBovine;
	}

	public HashMap<Integer, Integer> getHashCategorieBufaline() {
		return hashCategorieBufaline;
	}

	public void setHashCategorieBufaline(
			HashMap<Integer, Integer> hashCategorieBufaline) {
		this.hashCategorieBufaline = hashCategorieBufaline;
	}

	public void popola(Connection db) throws Exception{
		
		switch (tipoModulo){
		case 1:	
			String select1 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_IDATIDOSI");
			PreparedStatement stat1 = db.prepareStatement(select1);
			stat1.setInt( 1, idMacello );
			stat1.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat1.toString());
			ResultSet res1 = stat1.executeQuery();
			ArrayList<Capo> listaCapi = new ArrayList<Capo>();
			Capo capo = null;
			while(res1.next()){
				capo = Capo.load(res1.getString("id"), db);
				listaCapi.add(capo);
			}
			setListaCapi(listaCapi);
			gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
			break;
		case 2:
			String select2 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_MODELLO_MARCHI");
			PreparedStatement stat2 = db.prepareStatement( select2 );
			stat2.setInt( 1, idMacello);
			stat2.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat2.toString());
			ResultSet res2 = stat2.executeQuery();

			ArrayList<Capo> listaCapi_marchi = new ArrayList<Capo>();
			TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC = new TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>>();
			TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti = new TreeMap<Integer, ArrayList<ProvvedimentiCASL>>();
			Capo capo_macello = null;
			while(res2.next()){
				capo_macello = Capo.load(res2.getString("id"), db);
				listaCapi_marchi.add(capo_macello);
				hashCapiNC.put(capo_macello.getId(), Casl_Non_Conformita_Rilevata.load(capo_macello.getId(), db));
				hashCapiProvvedimenti.put(capo_macello.getId(), ProvvedimentiCASL.load(capo_macello.getId(), db));
			}
			setListaCapi_marchi(listaCapi_marchi);
			setHashCapiNC(hashCapiNC);
			setHashCapiProvvedimenti(hashCapiProvvedimenti);
			gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		break;
		case 3:  
			String select3 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_INFETTI");
			PreparedStatement stat3 = db.prepareStatement( select3 );
			stat3.setInt( 1, idMacello );
			stat3.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat3.toString());
			ResultSet res3 = stat3.executeQuery();

			ArrayList<Capo> listaCapiBrucellosi = new ArrayList<Capo>();
			ArrayList<Capo> listaCapiTubercolosi = new ArrayList<Capo>();
			Capo capo_infetto = null;
			while(res3.next()){
				capo_infetto = Capo.load(res3.getString("id"), db);
				if(capo_infetto.getCd_macellazione_differita() == 1){
					listaCapiBrucellosi.add(capo_infetto);
				}
				else if(capo_infetto.getCd_macellazione_differita() == 2){
					listaCapiTubercolosi.add(capo_infetto);
				}
			}
			setListaCapiBrucellosi(listaCapiBrucellosi);
			setListaCapiTubercolosi(listaCapiTubercolosi);
			
			if( listaCapiTubercolosi.size() > 0 && listaCapiBrucellosi.size()==0){
				this.setMalattiaCapo("TUBERCOLOSI");
				gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
			}
			else if( listaCapiTubercolosi.size() == 0 && listaCapiBrucellosi.size()>0){
				this.setMalattiaCapo("BRUCELLOSI");
				gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
			}
				
			else if( listaCapiTubercolosi.size() > 0 && listaCapiBrucellosi.size()>0)
				{ 	
				this.setMalattiaCapo("TUBERCOLOSI");
				gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
				mod2 = (ModelloGenerico) BeanUtils.cloneBean(this);
				mod2.setMalattiaCapo("BRUCELLOSI");
				mod2.gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
				}

		break;
		case 4: 
			String select4 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_GRAVIDI");
			PreparedStatement stat4 = db.prepareStatement( select4 );
			stat4.setInt( 1, idMacello );
			stat4.setTimestamp( 2, data );
			ResultSet res4 = stat4.executeQuery();
			System.out.println("Query modello macelli: "+stat4.toString());
			ArrayList<Capo> listaCapiGravidi = new ArrayList<Capo>();
			Capo capo_gravido = null;

			while(res4.next()){
				capo_gravido = Capo.load(res4.getString("id"), db);
				listaCapiGravidi.add(capo_gravido);
			}
			setListaCapiGravidi(listaCapiGravidi);
			gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
			break;
		case 5: 
			String select5 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_TBC_RILEVAZIONE_MACELLO");
			PreparedStatement stat5 = db.prepareStatement( select5 );
			stat5.setInt( 1, idMacello);
			stat5.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat5.toString());
			ResultSet res5 = stat5.executeQuery();

			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreTBC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
			TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiTBC = new TreeMap<Integer, ArrayList<Capo>>();
			Capo capo_tbc = null;
			//listaCapi = new ArrayList<Capo>();
//			inizializzaHashCategorieBovine();
//			inizializzaHashCategorieBufaline();
			
			while(res5.next()){
				capo_tbc = Capo.load(res5.getString("id"), db);
				//listaCapi.add(capo_tbc);
				//aggiornaHashCategorie(capo_tbc);
				
				if(capo_tbc.getCd_id_speditore() < 0) 
					capo_tbc.setCd_id_speditore(capo_tbc.getId_macello());
				
				if(capo_tbc.getCd_id_speditore() > -1) {
				
					if(!hashSpeditoreCapiTBC.containsKey(capo_tbc.getCd_id_speditore())){
						hashSpeditoreCapiTBC.put(capo_tbc.getCd_id_speditore(), new ArrayList<Capo>());
					}
					hashSpeditoreCapiTBC.get(capo_tbc.getCd_id_speditore()).add(capo_tbc);
					if(!hashSpeditoreTBC.containsKey(capo_tbc.getCd_id_speditore())){
						hashSpeditoreTBC.put(capo_tbc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_tbc.getCd_id_speditore()) );
					}
				}
//				if (h==0){ //Prima salvo tutto nel bean vero e proprio
//					this.setMatricolaCapo(capo_tbc.getCd_matricola());
//					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
//				}
//				// Poi inserisco tutto nella lista di modelli, compreso il primo
//					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
//					listaModelli.get(h).setMatricolaCapo(capo_tbc.getCd_matricola());
//					listaModelli.get(h).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
//				h++;
	}
			int h = 0;
			for(Entry<Integer, Organization> speditoreList : hashSpeditoreTBC.entrySet()) {
				listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
				  Integer orgIdSpeditore = speditoreList.getKey();
				  listaModelli.get(h).idSpeditore = orgIdSpeditore;
				  //Organization speditore = speditoreList.getValue();
				  
				  HashMap <Integer, Integer> hashCategorieBovineRet = listaModelli.get(h).inizializzaHashCategorieBovine();
				  HashMap <Integer, Integer> hashCategorieBufalineRet = listaModelli.get(h).inizializzaHashCategorieBufaline();
				  HashMap <Integer, Integer> hashCategoriaNumeroInfettiBoviniRet = listaModelli.get(h).inizializzaHashCategorieInfettiBovini();
				  HashMap <Integer, Integer> hashCategoriaNumeroInfettiBufaliniRet = listaModelli.get(h).inizializzaHashCategorieInfettiBufalini();
				   
				  listaModelli.get(h).hashCategorieBovine = hashCategorieBovineRet;
				  listaModelli.get(h).hashCategorieBufaline = hashCategorieBufalineRet;
				  listaModelli.get(h).hashCategoriaNumeroInfettiBovini = hashCategoriaNumeroInfettiBoviniRet;
				  listaModelli.get(h).hashCategoriaNumeroInfettiBufalini = hashCategoriaNumeroInfettiBufaliniRet;
				  
				  ArrayList <Capo> capoList = null;
				  capoList = hashSpeditoreCapiTBC.get(orgIdSpeditore);
				  for (int i = 0; i<capoList.size();i++){
					  Capo capoElement = capoList.get(i);
					  listaModelli.get(h).aggiornaHashCategorie(capoElement);
					  listaModelli.get(h).aggiornaHashCategorieInfetti(capoElement);
				 }
				  if (h==0){ //Prima salvo tutto nel bean vero e proprio
						this.setMatricolaCapo(capo_tbc.getCd_matricola());
						gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
					}
					// Poi inserisco tutto nella lista di modelli, compreso il primo
						//listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
						listaModelli.get(h).setMatricolaCapo(capo_tbc.getCd_matricola());
					//	listaModelli.get(h).setHashCategorieBovine(hashCategorieBovine);
					//	listaModelli.get(h).setHashCategorieBufaline(hashCategorieBufaline);
						listaModelli.get(h).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
					h++;
				}
					
			setHashSpeditoreCapiTBC(hashSpeditoreCapiTBC);
			setHashSpeditoreTBC(hashSpeditoreTBC);
		//	setListaCapi(listaCapi);
			//setHashCategorieBovine(hashCategorieBovine);
			//setHashCategorieBufaline(hashCategorieBufaline);
			//gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			
			break;
			
		case 6: 
			String select6 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_BRC_RILEVAZIONE_MACELLO");
			PreparedStatement stat6 = db.prepareStatement( select6 );
			stat6.setInt( 1, idMacello );
			stat6.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat6.toString());
			ResultSet res6 = stat6.executeQuery();
			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
			TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiBRC = new TreeMap<Integer, ArrayList<Capo>>();
			Capo capo_brc = null;
		
			//listaCapi = new ArrayList<Capo>();
//			inizializzaHashCategorieBovine();
//			inizializzaHashCategorieBufaline();
			
			while(res6.next()){
				capo_brc = Capo.load(res6.getString("id"), db);
				//listaCapi.add(capo_tbc);
				//aggiornaHashCategorie(capo_tbc);
				
				if(capo_brc.getCd_id_speditore() < 0) 
					capo_brc.setCd_id_speditore(capo_brc.getId_macello());
				
				if(capo_brc.getCd_id_speditore() > -1) {
				
					if(!hashSpeditoreCapiBRC.containsKey(capo_brc.getCd_id_speditore())){
						hashSpeditoreCapiBRC.put(capo_brc.getCd_id_speditore(), new ArrayList<Capo>());
					}
					hashSpeditoreCapiBRC.get(capo_brc.getCd_id_speditore()).add(capo_brc);
					if(!hashSpeditoreBRC.containsKey(capo_brc.getCd_id_speditore())){
						hashSpeditoreBRC.put(capo_brc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_brc.getCd_id_speditore()) );
					}
				}
//				if (h==0){ //Prima salvo tutto nel bean vero e proprio
//					this.setMatricolaCapo(capo_tbc.getCd_matricola());
//					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
//				}
//				// Poi inserisco tutto nella lista di modelli, compreso il primo
//					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
//					listaModelli.get(h).setMatricolaCapo(capo_tbc.getCd_matricola());
//					listaModelli.get(h).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
//				h++;
	}
			int x = 0;
			for(Entry<Integer, Organization> speditoreList : hashSpeditoreBRC.entrySet()) {
				listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
				  Integer orgIdSpeditore = speditoreList.getKey();
				  listaModelli.get(x).idSpeditore = orgIdSpeditore;
				  //Organization speditore = speditoreList.getValue();
				  
				  HashMap <Integer, Integer> hashCategorieBovineRet = listaModelli.get(x).inizializzaHashCategorieBovine();
				  HashMap <Integer, Integer> hashCategorieBufalineRet = listaModelli.get(x).inizializzaHashCategorieBufaline();
				 
				  listaModelli.get(x).hashCategorieBovine = hashCategorieBovineRet;
				  listaModelli.get(x).hashCategorieBufaline = hashCategorieBufalineRet;
				  
				  ArrayList <Capo> capoList = null;
				  capoList = hashSpeditoreCapiBRC.get(orgIdSpeditore);
				  for (int i = 0; i<capoList.size();i++){
					  Capo capoElement = capoList.get(i);
					  listaModelli.get(x).aggiornaHashCategorie(capoElement);
					 }
				  if (x==0){ //Prima salvo tutto nel bean vero e proprio
						this.setMatricolaCapo(capo_brc.getCd_matricola());
						gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
					}
					// Poi inserisco tutto nella lista di modelli, compreso il primo
						//listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
						listaModelli.get(x).setMatricolaCapo(capo_brc.getCd_matricola());
					//	listaModelli.get(h).setHashCategorieBovine(hashCategorieBovine);
					//	listaModelli.get(h).setHashCategorieBufaline(hashCategorieBufaline);
						listaModelli.get(x).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
						listaModelli.get(x).listaCapi = capoList;
					x++;
				}
					
			setHashSpeditoreCapiBRC(hashSpeditoreCapiBRC);
			setHashSpeditoreBRC(hashSpeditoreBRC);
		//	setListaCapi(listaCapi);
			//setHashCategorieBovine(hashCategorieBovine);
			//setHashCategorieBufaline(hashCategorieBufaline);
			//gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			
			break;
			
//		case 6: 
//			String select6 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_BRC_RILEVAZIONE_MACELLO");
//			PreparedStatement stat6 = db.prepareStatement( select6 );
//			stat6.setInt( 1, orgId );
//			stat6.setTimestamp( 2, data );
//			ResultSet res6 = stat6.executeQuery();
//
//			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
//			TreeMap<Integer, ArrayList<Capo>> hashSpeditoreCapiBRC = new TreeMap<Integer, ArrayList<Capo>>();
//			Capo capo_brc = null;
//			listaCapi = new ArrayList<Capo>();
//			this.hashCategorieBovine = inizializzaHashCategorieBovine();
//			this.hashCategorieBufaline = inizializzaHashCategorieBufaline();
//			while(res6.next()){
//				capo_brc = Capo.load(res6.getString("id"), db);
//				listaCapi.add(capo_brc);
//				aggiornaHashCategorie(capo_brc);
//				if(capo_brc.getCd_id_speditore() > -1){
//					if(!hashSpeditoreCapiBRC.containsKey(capo_brc.getCd_id_speditore())){
//						hashSpeditoreCapiBRC.put(capo_brc.getCd_id_speditore(), new ArrayList<Capo>());
//					}
//					hashSpeditoreCapiBRC.get(capo_brc.getCd_id_speditore()).add(capo_brc);
//					if(!hashSpeditoreBRC.containsKey(capo_brc.getCd_id_speditore())){
//						hashSpeditoreBRC.put(capo_brc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_brc.getCd_id_speditore()) );
//					}
//				}
//				
//			}
//			setListaCapi(listaCapi);
//			setHashSpeditoreCapiBRC(hashSpeditoreCapiBRC);
//			setHashSpeditoreBRC(hashSpeditoreBRC);
//			setHashCategorieBovine(hashCategorieBovine);
//			setHashCategorieBufaline(hashCategorieBufaline);
//			gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
//			break;
		case 7:
			String select7 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_1033_TBC");
			PreparedStatement stat7 = db.prepareStatement( select7 );
			stat7.setInt( 1, idMacello );
			stat7.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat7.toString());
			ResultSet res7 = stat7.executeQuery();

			ArrayList<Capo> listaCapi_1033_tbc = new ArrayList<Capo>();
			TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani = new TreeMap<Integer, ArrayList<Organi>>();
			Capo capo_1033_tbc = null;
			int q = 0;
			while(res7.next()){
				
				capo_1033_tbc = Capo.load(res7.getString("id"), db);
				listaCapi_1033_tbc.add(capo_1033_tbc);
				hashCapiOrgani.put(capo_1033_tbc.getId(), Organi.loadByOrgani(capo_1033_tbc.getId(), db));
				if (q==0){ //Prima salvo tutto nel bean vero e proprio
					this.setMatricolaCapo(capo_1033_tbc.getCd_matricola());
					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				}
				// Poi inserisco tutto nella lista di modelli, compreso il primo
					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
					listaModelli.get(q).setMatricolaCapo(capo_1033_tbc.getCd_matricola());
					listaModelli.get(q).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				q++;
			}
			setListaCapi_1033_tbc(listaCapi_1033_tbc);
			setHashCapiOrgani(hashCapiOrgani);
			break;
		case 8: 
			//document.add(instanceImg);
			String select8 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_EVIDENZE_VISITA_ANTE_MORTEM");
			PreparedStatement stat8 = db.prepareStatement( select8 );
			stat8.setInt( 1, idMacello );
			stat8.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat8.toString());
			ResultSet res8 = stat8.executeQuery();

			/*Sicuramente va cambiata la query rispetto al modulo 2---*/
			ArrayList<Capo> listaCapi_ante_mortem = new ArrayList<Capo>();
			TreeMap<Integer, ArrayList<NonConformita>> hashCapiNCAnteMortem = new TreeMap<Integer, ArrayList<NonConformita>>();
			Capo capo_visita_ante_mortem = null;
			int j = 0;
			while(res8.next()){
				capo_visita_ante_mortem = Capo.load(res8.getString("id"), db);
				listaCapi_ante_mortem.add(capo_visita_ante_mortem);
				hashCapiNCAnteMortem.put(capo_visita_ante_mortem.getId(), NonConformita.load(capo_visita_ante_mortem.getId(), db));
				if (j==0){ //Prima salvo tutto nel bean vero e proprio
					this.setMatricolaCapo(capo_visita_ante_mortem.getCd_matricola());
					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				}
				// Poi inserisco tutto nella lista di modelli, compreso il primo
					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
					listaModelli.get(j).setMatricolaCapo(capo_visita_ante_mortem.getCd_matricola());
					listaModelli.get(j).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				j++;
			}
			setListaCapi_ante_mortem(listaCapi_ante_mortem);
			setHashCapiNCAnteMortem(hashCapiNCAnteMortem);
			break;
		case 9: 
			//document.add(instanceImg);
			String select9 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_MORTE_ANTE_MACELLAZIONE");
			PreparedStatement stat9 = db.prepareStatement( select9 );
			stat9.setInt( 1, idMacello );
			stat9.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat9.toString());
			ResultSet res9 = stat9.executeQuery();

			ArrayList<Capo> listaCapiMortiAnte = new ArrayList<Capo>();
			Capo capo_ante_macellazione = null;
			int i = 0;
			while(res9.next()){
				capo_ante_macellazione = Capo.load(res9.getString("id"), db);
				listaCapiMortiAnte.add(capo_ante_macellazione);
				
				if (i==0){ //Prima salvo tutto nel bean vero e proprio
					this.setMatricolaCapo(capo_ante_macellazione.getCd_matricola());
					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				}
				// Poi inserisco tutto nella lista di modelli, compreso il primo
					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
					listaModelli.get(i).setMatricolaCapo(capo_ante_macellazione.getCd_matricola());
					listaModelli.get(i).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				
				i++;
			}
			setListaCapiMortiAnte(listaCapiMortiAnte);
			
		break;
		case 10:
			String select10 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_ANIMALI_LEB");
			PreparedStatement stat10 = db.prepareStatement( select10 );
			stat10.setInt( 1, idMacello );
			stat10.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat10.toString());
			ResultSet res10 = stat10.executeQuery();

			TreeMap<Integer, Capo> hashCapoLEB = new TreeMap<Integer, Capo>();
			TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreLEB = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();

			Capo capo_leb = null;
			int k = 0;
			while(res10.next()){
				capo_leb = Capo.load(res10.getString("id"), db);
				
				if(capo_leb.getCd_id_speditore() < 0) 
					capo_leb.setCd_id_speditore(capo_leb.getId_macello());
				
				if (k==0){ //Prima salvo tutto nel bean vero e proprio
					this.setMatricolaCapo(capo_leb.getCd_matricola());
					hashCapoLEB.put(capo_leb.getId(), capo_leb );
					hashSpeditoreLEB.put(capo_leb.getId(), new org.aspcfs.modules.speditori.base.Organization(db,capo_leb.getCd_id_speditore()) );
					gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				}
				// Poi inserisco tutto nella lista di modelli, compreso il primo
					listaModelli.add( (ModelloGenerico) BeanUtils.cloneBean(this));
					listaModelli.get(k).setMatricolaCapo(capo_leb.getCd_matricola());
					listaModelli.get(k).gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
				
				k++;	
			} 
			setHashCapoLEB(hashCapoLEB);
			setHashSpeditoreLEB(hashSpeditoreLEB);
			break;
		case 11:
			String select11 = org.aspcfs.modules.macellazioni.actions.ApplicationProperties.getProperty("GET_TRASPORTI_ANIMALI_INFETTI");
			PreparedStatement stat11 = db.prepareStatement( select11 );
			stat11.setInt( 1, idMacello );
			stat11.setTimestamp( 2, data );
			System.out.println("Query modello macelli: "+stat11.toString());
			ResultSet res11 = stat11.executeQuery();

			ArrayList<Capo> listaCapiTrasportati = new ArrayList<Capo>();
			Capo capo_trasp = null;

			while(res11.next()){
				capo_trasp = Capo.load(res11.getString("id"), db);
				listaCapiTrasportati.add(capo_trasp);

			}
			setListaCapiTrasportati(listaCapiTrasportati);
			gestioneProgressivoModulo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		break;
		default : System.out.println("Default");
		}
		
}

	
	
	private String toDateasString(Timestamp time)
	{
		  String toRet = "";
		  try
		  { 
			  if (time != null){
			  	java.sql.Date d = new java.sql.Date(time.getTime());
			  
			  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  	toRet=sdf.format(d);
		  }
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return toRet;
	}
	
	private void gestioneProgressivoModulo(Connection db, ChiaveModuliMacelli chiave) throws Exception{

		String daRicercare = "test";
		
		 java.util.Date date= new java.util.Date();
		 daRicercare = date.toString();
		
		//Gestione progressivo
		this.setProgressivo(0);
		this.setOldProgressivo(0);
		this.select(db, chiave);

		
		//se il progressivo e' zero, significa che NON esiste la stampa di quel modulo per cui va inserita in banca dati
		if(getProgressivo() == 0){
			this.setHashCode(daRicercare.hashCode());
			this.insert(db);
			this.select(db,chiave);
		}
		//se il progressivo e' diverso da zero, significa che esiste la stampa di quel modulo per cui va controllato 
		//se ci sono modifiche con la versione precedente (mediante hashcode)
		else{
			if(getHashCode() != daRicercare.hashCode()){
				this.setHashCode(daRicercare.hashCode());
				this.setOldProgressivo(this.getProgressivo());
				this.update(db);
				this.select(db,chiave);
			}
			}
		}
	
	
		public void select(Connection db, ChiaveModuliMacelli chiave) throws Exception{
			
			String select = "";
			PreparedStatement stat = null;
			ResultSet rs = null;
			
			switch (chiave) {
			case TIPO_DATA_MACELLO:
				
				select = "select * " +
						 "from stampe_moduli_macelli " +
						 "where tipo_modulo = ? and data_modulo = ? and id_macello = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, this.getTipoModulo() );
				stat.setTimestamp( 2, this.getData() );
				stat.setInt( 3, this.getIdMacello() );
				
				break;
			case TIPO_DATA_MACELLO_MATRICOLA:
				
				select = "select * " +
						 "from stampe_moduli_macelli " +
						 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and matricola_capo = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, this.getTipoModulo() );
				stat.setTimestamp( 2, this.getData() );
				stat.setInt( 3, this.getIdMacello() );
				stat.setString( 4, this.getMatricolaCapo() );
				
				break;
			case TIPO_DATA_MACELLO_SPEDITORE:
				
				select = "select * " +
				 		 "from stampe_moduli_macelli " +
				 		 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and id_speditore = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, this.getTipoModulo() );
				stat.setTimestamp( 2, this.getData() );
				stat.setInt( 3, this.getIdMacello() );
				stat.setInt( 4, this.getIdSpeditore() );
				
				break;
			case TIPO_DATA_MACELLO_MALATTIA:
				
				select = "select * " +
				 		 "from stampe_moduli_macelli " +
				 		 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and malattia_capo = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, this.getTipoModulo() );
				stat.setTimestamp( 2, this.getData() );
				stat.setInt( 3, this.getIdMacello() );
				stat.setString( 4, this.getMalattiaCapo() );
				
				break;
			default:
				throw new Exception("Chiave per moduli macelli non prevista");
			}
			
			
			rs = stat.executeQuery();
			if(rs.next()){
				this.setProgressivo(rs.getInt("progressivo"));
				this.setOldProgressivo(rs.getInt("old_progressivo"));
				this.setMatricolaCapo(rs.getString("matricola_capo"));
				this.setIdSpeditore(rs.getInt("id_speditore"));
				this.setMalattiaCapo(rs.getString("malattia_capo"));
				this.setHashCode(rs.getInt("hash_code"));
				if (rs.getInt("asl_macello")>0)
					this.setAslMacello(rs.getInt("asl_macello"));
			}
			
			
			
		}
		
public void insert(Connection db) throws Exception{
			
			int maxProgressivo = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			String select = "select coalesce( max( progressivo ), 0 ) " +
							"from stampe_moduli_macelli " +
							"where asl_macello = ? and date_part('year',data_modulo) = ?";
			PreparedStatement stat = db.prepareStatement(select);
			stat.setInt(1, this.getAslMacello() );
			stat.setInt(2,Integer.parseInt( sdf.format(this.getData()) ));
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				maxProgressivo = rs.getInt(1);
			}
			
			String insert = "insert into stampe_moduli_macelli"+
							"(tipo_modulo, data_modulo, asl_macello, id_macello, progressivo, matricola_capo, id_speditore, malattia_capo, old_progressivo, hash_code ) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stat = db.prepareStatement(insert);
			stat.setInt		 ( 1, this.getTipoModulo() );
			stat.setTimestamp( 2, this.getData() );
			stat.setInt		 ( 3, this.getAslMacello() );
			stat.setInt		 ( 4, this.getIdMacello() );
			stat.setInt		 ( 5, maxProgressivo + 1 );
			stat.setString	 ( 6, this.getMatricolaCapo() );
			stat.setInt		 ( 7, this.getIdSpeditore() );
			stat.setString	 ( 8, this.getMalattiaCapo() );
			stat.setInt		 ( 9, this.getOldProgressivo() );
			stat.setInt		 ( 10, this.getHashCode() );
			stat.executeUpdate();
			
		}
		
		
		public void update(Connection db) throws Exception{
			
			int maxProgressivo = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			String select = "select coalesce( max( progressivo ), 0 ) " +
							"from stampe_moduli_macelli " +
							"where asl_macello = ? and date_part('year',data_modulo) = ?";
			PreparedStatement stat = db.prepareStatement(select);
			stat.setInt(1, this.getAslMacello() );
			stat.setInt(2,Integer.parseInt( sdf.format(this.getData()) ));
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				maxProgressivo = rs.getInt(1);
			}
			
			String update = "update stampe_moduli_macelli "+
							"set tipo_modulo = ?, data_modulo = ?, asl_macello = ?, id_macello = ?, " +
							"progressivo = ?, matricola_capo = ?, id_speditore = ?, malattia_capo = ?, old_progressivo = ?, hash_code = ? " +
							"where id = ?";
			stat = db.prepareStatement(update);
			stat.setInt		 ( 1, this.getTipoModulo() );
			stat.setTimestamp( 2, this.getData() );
			stat.setInt		 ( 3, this.getAslMacello() );
			stat.setInt		 ( 4, this.getIdMacello() );
			stat.setInt		 ( 5, maxProgressivo + 1 );
			stat.setString	 ( 6, this.getMatricolaCapo() );
			stat.setInt		 ( 7, this.getIdSpeditore() );
			stat.setString	 ( 8, this.getMalattiaCapo() );
			stat.setInt		 ( 9, this.getOldProgressivo() );
			stat.setInt		 ( 10, this.getHashCode() );
			stat.setInt		 ( 11, this.getId() );
			stat.executeUpdate();
			
		}
		
		private HashMap <Integer, Integer> inizializzaHashCategorieBovine(){
			HashMap <Integer, Integer> hashCategorieBovine = new HashMap <Integer, Integer>();
			hashCategorieBovine.put(-1,0);
			hashCategorieBovine.put(1,0);
			hashCategorieBovine.put(2,0);
			hashCategorieBovine.put(4,0);
			hashCategorieBovine.put(5,0);
			hashCategorieBovine.put(6,0);
			hashCategorieBovine.put(7,0);
			return hashCategorieBovine;
			}
		private HashMap <Integer, Integer> inizializzaHashCategorieBufaline(){
			HashMap <Integer, Integer> hashCategorieBufaline = new HashMap <Integer, Integer>();
			hashCategorieBufaline.put(-1,0);
			hashCategorieBufaline.put(1,0);
			hashCategorieBufaline.put(2,0);
			hashCategorieBufaline.put(3,0);
			hashCategorieBufaline.put(4,0);
			hashCategorieBufaline.put(5,0);
			hashCategorieBufaline.put(6,0);
			return hashCategorieBufaline;
		}
		
		private void aggiornaHashCategorie(Capo capo){
			int categoriaBovina = capo.getCd_categoria_bovina();
			int categoriaBufalina = capo.getCd_categoria_bufalina();
						
			if (capo.isBovino()){
				int valAttuale = hashCategorieBovine.get(categoriaBovina);
				valAttuale++;
				hashCategorieBovine.put(categoriaBovina, valAttuale);
			}
			else if (capo.isBufalino()){
				int valAttuale = hashCategorieBufaline.get(categoriaBufalina);
				valAttuale++;
				hashCategorieBufaline.put(categoriaBufalina, valAttuale);
			}
			
			
		}
		
		private HashMap <Integer, Integer> inizializzaHashCategorieInfettiBovini(){
			HashMap <Integer, Integer> hashCategoriaNumeroInfettiBovini = new HashMap <Integer, Integer>();
			hashCategoriaNumeroInfettiBovini.put(-1,0);
			hashCategoriaNumeroInfettiBovini.put(1,0);
			hashCategoriaNumeroInfettiBovini.put(2,0);
			hashCategoriaNumeroInfettiBovini.put(4,0);
			hashCategoriaNumeroInfettiBovini.put(5,0);
			hashCategoriaNumeroInfettiBovini.put(6,0);
			hashCategoriaNumeroInfettiBovini.put(7,0);
			return hashCategoriaNumeroInfettiBovini;
			}
		private HashMap <Integer, Integer> inizializzaHashCategorieInfettiBufalini(){
			HashMap <Integer, Integer> hashCategoriaNumeroInfettiBufalini = new HashMap <Integer, Integer>();
			hashCategoriaNumeroInfettiBufalini.put(-1,0);
			hashCategoriaNumeroInfettiBufalini.put(1,0);
			hashCategoriaNumeroInfettiBufalini.put(2,0);
			hashCategoriaNumeroInfettiBufalini.put(3,0);
			hashCategoriaNumeroInfettiBufalini.put(4,0);
			hashCategoriaNumeroInfettiBufalini.put(5,0);
			hashCategoriaNumeroInfettiBufalini.put(6,0);
			return hashCategoriaNumeroInfettiBufalini;
		}
		
	
		private void aggiornaHashCategorieInfetti(Capo capo){
			int categoriaBovina = capo.getCd_categoria_bovina();
			int categoriaBufalina = capo.getCd_categoria_bufalina();
			if (capo.getCd_macellazione_differita()==2){
			if (capo.isBovino()){
				int valAttuale = hashCategoriaNumeroInfettiBovini.get(categoriaBovina);
				valAttuale++;
				hashCategoriaNumeroInfettiBovini.put(categoriaBovina, valAttuale);
			}
			else if (capo.isBufalino()){
				int valAttuale = hashCategoriaNumeroInfettiBufalini.get(categoriaBufalina);
				valAttuale++;
				hashCategoriaNumeroInfettiBufalini.put(categoriaBufalina, valAttuale);
			}
		}
		}
}