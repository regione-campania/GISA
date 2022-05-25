/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.utils;

import java.util.ArrayList;

public class AttivitaProduttive {
	
	private int idAttivita;
	private int nextLivello ;
	private String nomeColonnaId ;
	private String nomeColonnaDescrizione ;
	private String nomeTabella ;
	private String nomeColonnaDipendente ;
	
	private String label ;
	
	private boolean ultimoLivello ;
	private boolean attivitaPrincipale;
	private String nameCombo ;
	private String bgcolor ;
	private String bgcolorPrec ;
	private boolean previstoCodiceNazionale;

	
	
	
	public boolean isPrevistoCodiceNazionale() {
		return previstoCodiceNazionale;
	}

	public void setPrevistoCodiceNazionale(boolean previstoCodiceNazionale) {
		this.previstoCodiceNazionale = previstoCodiceNazionale;
	}

	public String getBgcolorPrec() {
		return bgcolorPrec;
	}

	public void setBgcolorPrec(String bgcolorPrec) {
		this.bgcolorPrec = bgcolorPrec;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public boolean isUltimoLivello() {
		return ultimoLivello;
	}

	public void setUltimoLivello(boolean ultimoLivello) {
		this.ultimoLivello = ultimoLivello;
	}

	public boolean isAttivitaPrincipale() {
		return attivitaPrincipale;
	}

	public void setAttivitaPrincipale(boolean attivitaPrincipale) {
		this.attivitaPrincipale = attivitaPrincipale;
	}

	private ArrayList<Item> listaItem= new  ArrayList<Item>();
	
	public AttivitaProduttive(){}
	
	public int getNextLivello() {
		return nextLivello;
	}

	

	public String getNomeTabella() {
		return nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	public String getNomeColonnaDipendente() {
		return nomeColonnaDipendente;
	}

	public void setNomeColonnaDipendente(String nomeColonnaDipendente) {
		this.nomeColonnaDipendente = nomeColonnaDipendente;
	}

	public void setNextLivello(int nextLivello) {
		this.nextLivello = nextLivello;
	}



	public String getNomeColonnaId() {
		return nomeColonnaId;
	}



	public void setNomeColonnaId(String nomeColonnaId) {
		this.nomeColonnaId = nomeColonnaId;
	}



	public String getNomeColonnaDescrizione() {
		return nomeColonnaDescrizione;
	}



	public void setNomeColonnaDescrizione(String nomeColonnaDescrizione) {
		this.nomeColonnaDescrizione = nomeColonnaDescrizione;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}


	
	


	public ArrayList<Item> getListaItem() {
		return listaItem;
	}

	public void setListaItem(ArrayList<Item> listaItem) {
		this.listaItem = listaItem;
		
	}

	public int getIdAttivita() {
		return idAttivita;
	}

	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}








}
 
	
	

