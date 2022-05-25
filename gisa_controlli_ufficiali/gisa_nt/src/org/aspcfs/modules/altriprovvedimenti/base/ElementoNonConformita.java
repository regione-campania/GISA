/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.altriprovvedimenti.base;

import java.io.Serializable;
import java.util.HashMap;


public class ElementoNonConformita implements Serializable {

	private static final long serialVersionUID = 7088794923408011309L;
	public static final int NC_FORMALI			=	1;
	public static final int NC_SIGNIFICATIVE	=	2;
	public static final int NC_GRAVI			=	3;
	
	private int id_ticket			;
	private int progressivo_nc		;
	private int tipologia_nc		;
	private String note				;
	private int id_nc 				;
	private String descrizione_nc	;
	private int id_linea_nc 		;
	private int id_nc_benessere_macellazione;
	private int id_nc_benessere_trasporto;
	private int id_operatore_mercato;

	private HashMap<Integer,String> lista_nc = new HashMap<Integer, String>();
	
	
	public int getId_nc() {
		return id_nc;
	}
	public void setId_nc(int id_nc) {
		this.id_nc = id_nc;
	}
	public String getDescrizione_nc() {
		return descrizione_nc;
	}
	public void setDescrizione_nc(String descrizione_nc) {
		this.descrizione_nc = descrizione_nc;
	}
	public int getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}
	public int getProgressivo_nc() {
		return progressivo_nc;
	}
	public void setProgressivo_nc(int progressivo_nc) {
		this.progressivo_nc = progressivo_nc;
	}
	public int getTipologia_nc() {
		return tipologia_nc;
	}
	public void setTipologia_nc(int tipologia_nc) {
		this.tipologia_nc = tipologia_nc;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public HashMap<Integer, String> getLista_nc() {
		return lista_nc;
	}
	public void setLista_nc(HashMap<Integer, String> lista_nc) {
		this.lista_nc = lista_nc;
	}
	
	
	public ElementoNonConformita ( )
	{
		
	}
	
	public ElementoNonConformita (int id_ticket,int progressivo_nc,int tipologia,String note, int idLineaNc)
	{
		this.setId_ticket(id_ticket)			;
		this.setProgressivo_nc(progressivo_nc)	;
		this.setTipologia_nc(tipologia)			;
		this.setNote(note)						;
		this.setId_linea_nc(idLineaNc)			;
	}
	public ElementoNonConformita (int id_ticket,int progressivo_nc,int tipologia)
	{
		this.setId_ticket(id_ticket)			;
		this.setProgressivo_nc(progressivo_nc)	;
		this.setTipologia_nc(tipologia)			;

	}
	public int getId_linea_nc() {
		return id_linea_nc;
	}
	public void setId_linea_nc(int id_linea_nc) {
		this.id_linea_nc = id_linea_nc;
	}
	
	public int getId_nc_benessere_macellazione() {
		return id_nc_benessere_macellazione;
	}
	public void setId_nc_benessere_macellazione(int id_nc_benessere_macellazione) {
		this.id_nc_benessere_macellazione = id_nc_benessere_macellazione;
	}
	public int getId_nc_benessere_trasporto() {
		return id_nc_benessere_trasporto;
	}
	public void setId_nc_benessere_trasporto(int id_nc_benessere_trasporto) {
		this.id_nc_benessere_trasporto = id_nc_benessere_trasporto;
	}
	public int getId_operatore_mercato() {
		return id_operatore_mercato;
	}
	public void setId_operatore_mercato(int id_operatore_mercato) {
		this.id_operatore_mercato = id_operatore_mercato;
	}
	
}
