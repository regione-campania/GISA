/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.altriprovvedimenti.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;


public class ElementoOsservazione implements Serializable {

	private static final long serialVersionUID = 7088794923408011309L;
	public static final int NC_FORMALI			=	1;
	public static final int NC_SIGNIFICATIVE	=	2;
	public static final int NC_GRAVI			=	3;
	
	private int id ;
	private int id_ticket			;
	private int progressivo_nc		;
	private int tipologia_nc		;
	private String note				;
	
	LookupList listaOsservazioni = new LookupList() ;
	LookupList listaOggettiAudit= new LookupList() ;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LookupList getListaOsservazioni() {
		return listaOsservazioni;
	}
	public void setListaOsservazioni(LookupList listaOsservazioni) {
		this.listaOsservazioni = listaOsservazioni;
	}
	public LookupList getListaOggettiAudit() {
		return listaOggettiAudit;
	}
	public void setListaOggettiAudit(LookupList listaOggettiAudit) {
		this.listaOggettiAudit = listaOggettiAudit;
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

	
	
	public ElementoOsservazione ( )
	{
		
	}
	
	public ElementoOsservazione (int id_ticket,int progressivo_nc,int tipologia,String note)
	{
		this.setId_ticket(id_ticket)			;
		this.setProgressivo_nc(progressivo_nc)	;
		this.setTipologia_nc(tipologia)			;
		this.setNote(note)						;
	}
	
	public void buildListOsservazioni(Connection db )
	{
		
		try
		{
			PreparedStatement pst = db.prepareStatement("select id_lookup_osservazioni ,lo.description " +
					"from salvataggio_osservazioni_lista t1 join lookup_osservazioni lo on lo.code = t1.id_lookup_osservazioni " +
					"where t1.id_salvataggio_osservazioni = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				LookupElement el = new LookupElement();
				el.setCode(rs.getInt("id_lookup_osservazioni"));
				el.setDescription(rs.getString("description"));
				listaOsservazioni.add(el);
			}
		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
	}
	
	public void buildListggettoAudit(Connection db)
	{
		
		try
		{
			PreparedStatement pst = db.prepareStatement("select id_lookup_oggetto_audit ,lo.description " +
					" from salvataggio_osservazioni_oggetto_audit t1 join lookup_oggetto_audit lo on lo.code = t1.id_lookup_oggetto_audit " +
					" where t1.id_salvataggio_osservazioni = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				LookupElement el = new LookupElement();
				el.setCode(rs.getInt("id_lookup_oggetto_audit"));
				el.setDescription(rs.getString("description"));
				listaOggettiAudit.add(el);
			}
		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
