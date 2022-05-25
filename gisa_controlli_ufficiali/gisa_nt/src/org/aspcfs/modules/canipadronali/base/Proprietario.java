/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.utils.IndirizzoProprietario;

public class Proprietario extends org.aspcfs.utils.Proprietario {

	private int idProprietario ;
	private int org_id_canina ;
	private String tipo ;
	private int punteggioTotale;
	private int orgId ; 
	ArrayList<Cane> listaCani = new ArrayList<Cane>();
	private int enteredBy;
	private int modifiedBy;
	private int siteId 			;
	private int categoriaRischio ;
	private int accountSize ;
	private String name 		;
	private int tipologia = 255 ;
	
	
	
	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public int getAccountSize() {
		return accountSize;
	}

	public void setAccountSize(int accountSize) {
		this.accountSize = accountSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public ArrayList<Cane> getListaCani() {
		return listaCani;
	}

	public void setListaCani(ArrayList<Cane> listaCani) {
		this.listaCani = listaCani;
	}

	public int getPunteggioTotale() {
		return punteggioTotale;
	}

	public void setPunteggioTotale(int punteggioTotale) {
		this.punteggioTotale = punteggioTotale;
	}

	public int getOrg_id_canina() {
		return org_id_canina;
	}

	public void setOrg_id_canina(int org_id_canina) {
		this.org_id_canina = org_id_canina;
	}

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
	public String toString()
	{
		
		
		return this.getNome()+" "+this.getCognome()+" "+this.getCodiceFiscale()+" "+this.getDocumentoIdentita() ;
	}
	
	
	public void buildRecord(ResultSet rs , Proprietario p)
	{
		try 
		{
			
				p.setRagioneSociale(rs.getString("name"));
				p.setNome(rs.getString("nome_rappresentante"));
				p.setCognome(rs.getString("cognome_rappresentante"));
				p.setDataNascita(rs.getDate("data_nascita_rappresentante"));
				p.setLuogoNascita(rs.getString("luogo_nascita_rappresentante"));
				p.setCodiceFiscale(rs.getString("codice_fiscale_rappresentante"));
				p.setPunteggioTotale(rs.getInt("punteggio"));
				p.setTipologia(255);
				//IndirizzoProprietario indirizzo = getIndirizzoProprietario(p.getIdProprietario()) ;
				
				
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
