/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Rendicontazione extends GenericBean{
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idCapitolo;

	private int punteggioA;
	private int punteggioB;
	private int punteggioC;
	private int punteggioTotale;
	
	private String descCap;
	 

	public int getIdCapitolo() {
		return idCapitolo;
	}

	public void setidCapitolo(int idCap) {
		this.idCapitolo = idCap;
	}

	public int getPunteggioA() {
		return punteggioA;
	}

	public void setPunteggioA(int punteggioA) {
		this.punteggioA = punteggioA;
	}

	public int getPunteggioB() {
		return punteggioB;
	}

	public void setPunteggioB(int punteggioB) {
		this.punteggioB = punteggioB;
	}

	public int getPunteggioC() {
		return punteggioC;
	}

	public void setPunteggioC(int punteggioC) {
		this.punteggioC = punteggioC;
	}

	public int getPunteggioTotale() {
		return punteggioTotale;
	}

	public void setPunteggioTotale(int punteggio) {
		this.punteggioTotale = punteggio;
	}
	
	public String getDescCap() {
		return descCap;
	}

	public void setDescCap(String descCap) {
		this.descCap = descCap;
	}



	public Rendicontazione(){ }
		
	
	public static ArrayList<Rendicontazione> buildList(Connection db, String anno, String idSpecie) throws SQLException {
		// TODO Auto-generated method stub
		
		ArrayList<Rendicontazione> rendicontazioneList = new ArrayList<Rendicontazione>();
		PreparedStatement pst = db.prepareStatement(
        "SELECT * FROM get_rendicontazione_chk_bns_animale("+anno+","+idSpecie+")");
		
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Rendicontazione thisRisposta = new Rendicontazione(rs);
			rendicontazioneList.add(thisRisposta);
		}
		
		return rendicontazioneList;
	}
	  
	protected  void buildRecord(ResultSet rs) throws SQLException {
		 
		 //chk_bns_mod_ist table
		 idCapitolo = rs.getInt("idcapitolo");
		 
		 descCap = rs.getString("capitolo");
		 punteggioA = rs.getInt("punteggioa");
		 punteggioB = rs.getInt("punteggiob");
		 punteggioC = rs.getInt("punteggioc");
		 punteggioTotale = rs.getInt("totaleirr");
		 
		    
	 }
	  
	 public Rendicontazione(ResultSet rs) throws SQLException {
		    buildRecord(rs);
	 }
	
}
