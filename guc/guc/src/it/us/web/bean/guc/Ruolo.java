/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;


public class Ruolo implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private int id;
	private Utente utente;
	private String endpoint;
	private Integer ruoloInteger;
	private String ruoloString;
	private String note;
	private HashMap<String,String> extOpt;
	
	private boolean selected = false;
	private String nomeRuolo = null;
	private String descrizioneRuolo = null;
	private int ruoloDaCopiare = -1;
	private boolean inDpat = false;
	private boolean inAccess = false;
	private boolean inNucleo = false;
	private boolean listaNucleo = false;
	
	public String getNomeRuolo() {
		return nomeRuolo;
	}


	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}


	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}


	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}


	public boolean isInDpat() {
		return inDpat;
	}


	public void setInDpat(boolean inDpat) {
		this.inDpat = inDpat;
	}


	public boolean isInAccess() {
		return inAccess;
	}


	public void setInAccess(boolean inAccess) {
		this.inAccess = inAccess;
	}


	public boolean isInNucleo() {
		return inNucleo;
	}


	public void setInNucleo(boolean inNucleo) {
		this.inNucleo = inNucleo;
	}


	public boolean isListaNucleo() {
		return listaNucleo;
	}


	public void setListaNucleo(boolean listaNucleo) {
		this.listaNucleo = listaNucleo;
	}


	public boolean isQualifica() {
		return qualifica;
	}


	public void setQualifica(boolean qualifica) {
		this.qualifica = qualifica;
	}


	private boolean qualifica = false;
	
	
	public Ruolo() {
		setRuoloInteger(-1);
		setRuoloString("N.D.");
		setNote("");
		setExtOpt(new HashMap<String,String>());
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public Integer getRuoloInteger() {
		return ruoloInteger;
	}
	public void setRuoloInteger(Integer ruoloInteger) {
		this.ruoloInteger = ruoloInteger;
	}
	
	
	public String getRuoloString() {
		return ruoloString;
	}
	public void setRuoloString(String ruoloString) {
		this.ruoloString = ruoloString;
	}

	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString(){
		return getEndpoint()+";;;"+getRuoloInteger();
	}


	public HashMap<String,String> getExtOpt() {
		return extOpt;
	}


	public void setExtOpt(HashMap<String,String> extOpt) {
		this.extOpt = extOpt;
	} 
	
	public void buildFromRequest(HttpServletRequest req, int indice){
		selected = (req.getParameter("ruolo_"+indice)!=null && req.getParameter("ruolo_"+indice).equals("on")) ? true : false;
		nomeRuolo = req.getParameter("nomeRuolo");
		descrizioneRuolo = req.getParameter("descrizioneRuolo");
		setRuoloDaCopiare(req.getParameter("ruoloDaCopiare_"+indice)); 
		inAccess = (req.getParameter("inAccess_"+indice)!=null && req.getParameter("inAccess_"+indice).equals("on")) ? true : false; 
		inDpat = (req.getParameter("inDpat_"+indice)!=null && req.getParameter("inDpat_"+indice).equals("on")) ? true : false;
		inNucleo = (req.getParameter("inNucleo_"+indice)!=null && req.getParameter("inNucleo_"+indice).equals("on")) ? true : false;
		listaNucleo =(req.getParameter("nucleoLista_"+indice)!=null && req.getParameter("nucleoLista_"+indice).equals("on")) ? true : false;
		qualifica = (req.getParameter("isQualifica_"+indice)!=null && req.getParameter("isQualifica_"+indice).equals("on")) ? true : false;
		
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public int getRuoloDaCopiare() {
		return ruoloDaCopiare;
	}


	public void setRuoloDaCopiare(int ruoloDaCopiare) {
		this.ruoloDaCopiare = ruoloDaCopiare;
	}
	
	public void setRuoloDaCopiare(String ruoloDaCopiare) {
		try {this.ruoloDaCopiare = Integer.parseInt(ruoloDaCopiare);} catch (Exception e) {}
	}
	
}
