/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.beans.GenericBean;


public class LineaProduttivaFlag extends GenericBean {
	private static final long serialVersionUID = 1L;



	public LineaProduttivaFlag() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	private String codiceUnivoco;
	private boolean mobile;
	private boolean fisso;
	private boolean apicoltura;
	private boolean registrabili;
	private boolean riconoscibili;
	private boolean sintesis;
	private boolean bdu;
	private boolean vam;
	private boolean noScia;
	private boolean categorizzabili;
	private int compatibilita_noscia;
	private int rev;
	
	
	public LineaProduttivaFlag(ResultSet rs) throws SQLException {
		  codiceUnivoco = rs.getString("codice");
		  mobile = rs.getBoolean("mobile");
		  fisso = rs.getBoolean("fisso");
		  apicoltura = rs.getBoolean("apicoltura");
		  registrabili = rs.getBoolean("registrabili");
		  riconoscibili = rs.getBoolean("riconoscibili");
		  sintesis = rs.getBoolean("sintesis");
		  bdu = rs.getBoolean("bdu");
		  vam = rs.getBoolean("vam");
		  noScia = rs.getBoolean("no_scia");	
		  categorizzabili = rs.getBoolean("categorizzabili");	
		  compatibilita_noscia = rs.getInt("compatibilita_noscia");
		  rev = rs.getInt("rev");
		  }


	public String getCodiceUnivoco() {
		return codiceUnivoco;
	}


	public void setCodiceUnivoco(String codiceUnivoco) {
		this.codiceUnivoco = codiceUnivoco;
	}


	public boolean isMobile() {
		return mobile;
	}


	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}


	public boolean isFisso() {
		return fisso;
	}


	public void setFisso(boolean fisso) {
		this.fisso = fisso;
	}


	public boolean isApicoltura() {
		return apicoltura;
	}


	public void setApicoltura(boolean apicoltura) {
		this.apicoltura = apicoltura;
	}


	public boolean isRegistrabili() {
		return registrabili;
	}


	public void setRegistrabili(boolean registrabili) {
		this.registrabili = registrabili;
	}


	public boolean isRiconoscibili() {
		return riconoscibili;
	}


	public void setRiconoscibili(boolean riconoscibili) {
		this.riconoscibili = riconoscibili;
	}


	public boolean isSintesis() {
		return sintesis;
	}


	public void setSintesis(boolean sintesis) {
		this.sintesis = sintesis;
	}


	public boolean isBdu() {
		return bdu;
	}


	public void setBdu(boolean bdu) {
		this.bdu = bdu;
	}


	public boolean isVam() {
		return vam;
	}


	public void setVam(boolean vam) {
		this.vam = vam;
	}


	public boolean isNoScia() {
		return noScia;
	}


	public void setNoScia(boolean noScia) {
		this.noScia = noScia;
	}


	public boolean isCategorizzabili() {
		return categorizzabili;
	}


	public void setCategorizzabili(boolean categorizzabili) {
		this.categorizzabili = categorizzabili;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getCompatibilita_noscia() {
		return compatibilita_noscia;
	}


	public void setCompatibilita_noscia(int compatibilita_noscia) {
		this.compatibilita_noscia = compatibilita_noscia;
	}


	public int getRev() {
		return rev;
	}


	public void setRev(int rev) {
		this.rev = rev;
	}

	
	
	
}
