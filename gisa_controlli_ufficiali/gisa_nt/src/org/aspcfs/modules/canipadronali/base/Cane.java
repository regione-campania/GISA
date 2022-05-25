/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.utils.IndirizzoProprietario;
import org.aspcfs.utils.Proprietario;

public class Cane extends  org.aspcfs.utils.Cane {
	
	org.aspcfs.modules.canipadronali.base.Proprietario proprietario ;
	private int enteredby 		;
	private int modifiedby 		;
	private int orgId 			;
	private int idStato 			;
	private int siteId 			;
	private int categoriaRischio ;
	private int accountSize ;
	private String name 		;
	private String taglia ;
	private String mantello ;
	private int tipologia = 255 ;
	private int asset_id_Canina ;
	private String stato ;
	private String dataDecesso ;
	
	
	
	
	
	public String getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(String dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getAsset_id_Canina() {
		return asset_id_Canina;
	}

	public void setAsset_id_Canina(int asset_id_Canina) {
		this.asset_id_Canina = asset_id_Canina;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getMantello() {
		return mantello;
	}

	public void setMantello(String mantello) {
		this.mantello = mantello;
	}

	public String getName() {
		name = this.getNominativoProprietario() ;
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAccountSize() {
		return accountSize;
	}

	public void setAccountSize(String accountSize) {
		this.accountSize = Integer.parseInt(accountSize);
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoraRischio) {
		this.categoriaRischio = categoraRischio;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public org.aspcfs.modules.canipadronali.base.Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(org.aspcfs.modules.canipadronali.base.Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public int getEnteredby() {
		return enteredby;
	}

	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}

	public int getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	
	public void buildRecord(ResultSet rs , org.aspcfs.modules.canipadronali.base.Cane c)
	{
		try 
		{
			
				
				c.setId(rs.getInt("id_cane"));
				c.setMc(rs.getString("mc"));
				c.setRazza(rs.getString("razza"));
				
				c.setSesso(rs.getString("sesso"));
				c.setDettagliAddizionali(rs.getString("dett_add"));
				c.setDataNascita(rs.getDate("data_nascita_cane"));
				c.setIdStato(rs.getInt("id_stato"));
				c.setNominativoProprietario(rs.getString("nominativo"));
				org.aspcfs.modules.canipadronali.base.Proprietario p = new org.aspcfs.modules.canipadronali.base.Proprietario();
				p.setIdProprietario(rs.getInt("org_id"));
				c.setOrgId(p.getIdProprietario());
				p.setIdAsl(rs.getInt("id_asl_prop")) ;
				p.setTipoProprietarioDetentore(rs.getString("tipo_proprietario"));
				c.setSiteId(p.getIdAsl());
				p.setRagioneSociale(c.getNominativoProprietario());
				p.setNome(rs.getString("nome"));
				p.setCognome(rs.getString("cognome"));
				p.setDataNascita(rs.getDate("data_nascita_prop"));
				p.setLuogoNascita("luogo_nascita_prop");
				p.setCodiceFiscale(rs.getString("cf_prop"));
				//IndirizzoProprietario indirizzo = getIndirizzoProprietario(p.getIdProprietario()) ;
				ArrayList<IndirizzoProprietario> lista_indirizzi_prop = new ArrayList<IndirizzoProprietario>() ;
				//lista_indirizzi_prop.add(indirizzo);
				p.setLista_indirizzi(lista_indirizzi_prop) ;
				
				c.setProprietario(p) ;
				
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
