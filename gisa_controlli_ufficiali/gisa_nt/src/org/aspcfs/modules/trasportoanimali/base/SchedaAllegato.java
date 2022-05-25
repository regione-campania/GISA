/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.trasportoanimali.base;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class SchedaAllegato extends GenericBean {

	private String ragioneSociale;
	private String accountNumber;
	private String partitaIva;
	private String indirizzo;
	private String comune;
	private String provincia;
	private String telefono;
	private String email;
	private String fax;
	private String cap;
	private String nazione;
	private String sede;
	private String cittaSede;
	private String stateSede;
	private String d1;
	private String d2;
	private String d3;
	private String banca;
	private String mezzo;
	private String targa;
	private Timestamp date1;
	private String asl;
	private String codiceFiscaleRappresentante;
	private String note;
	
	
	
	
	
	
	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getCittaSede() {
		return cittaSede;
	}

	public void setCittaSede(String cittaSede) {
		this.cittaSede = cittaSede;
	}

	public String getStateSede() {
		return stateSede;
	}

	public void setStateSede(String stateSede) {
		this.stateSede = stateSede;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}

	public String getMezzo() {
		return mezzo;
	}

	public void setMezzo(String mezzo) {
		this.mezzo = mezzo;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Timestamp getDate1() {
		return date1;
	}

	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}

	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public SchedaAllegato()  {
			
	}
	
	public SchedaAllegato(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
		
	}
	
	public void buildRecord(ResultSet rs) throws SQLException {

		try {
			rs.findColumn("name");
			this.ragioneSociale = rs.getString("name");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("codice_fiscale_rappresentante");
			this.codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("d1");
			this.d1 = rs.getString("d1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("d2");
			this.d2 = rs.getString("d2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("d3");
			this.d3 = rs.getString("d3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("sede");
			this.sede = rs.getString("sede");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("cittasede");
			this.cittaSede = rs.getString("cittasede");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("statesede");
			this.stateSede = rs.getString("statesede");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("mezzo");
			this.mezzo = rs.getString("mezzo");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("targa");
			this.targa = rs.getString("targa");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("asl");
			this.asl = rs.getString("asl");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("date1");
			this.date1 = rs.getTimestamp("date1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("account_number");
			this.accountNumber = rs.getString("account_number");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("partita_iva");
			this.partitaIva = rs.getString("partita_iva");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		
		try {
			rs.findColumn("telefono_rappresentante");
			this.telefono = rs.getString("telefono_rappresentante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("fax");
			this.fax = rs.getString("fax");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("email_rappresentante");
			this.email = rs.getString("email_rappresentante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("city");
			this.comune = rs.getString("city");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("postalcode");
			this.cap = rs.getString("postalcode");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("country");
			this.nazione = rs.getString("country");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("state");
			this.provincia = rs.getString("state");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("addrline1");
			this.indirizzo  = rs.getString("addrline1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("notes");
			this.note  = rs.getString("notes");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	
}
