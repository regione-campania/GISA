/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DatiMobile extends GenericBean {

	
	/*Campi utili per l'import*/
	private String piva ;
	private String ragioneSociale ;
	private String  tipoVeicoloDescr ;
	private String  cfRappresentante ; 
	private String  nomeRappresentante ;
	private String cognomeRappresentante ;
	private String sedeLegale ;
	private int idTipoVeicoloOpu;
	

	private int id = -1;
	private String targa="";
	private String carta ="";
	private int tipo = -1;
	private int orgId ;
	private String label= "";

	
	public int getIdTipoVeicoloOpu() {
		return idTipoVeicoloOpu;
	}

	public void setIdTipoVeicoloOpu(int idTipoVeicoloOpu) {
		this.idTipoVeicoloOpu = idTipoVeicoloOpu;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getTipoVeicoloDescr() {
		return tipoVeicoloDescr;
	}

	public void setTipoVeicoloDescr(String tipoVeicoloDescr) {
		this.tipoVeicoloDescr = tipoVeicoloDescr;
	}

	public String getCfRappresentante() {
		return cfRappresentante;
	}

	public void setCfRappresentante(String cfRappresentante) {
		this.cfRappresentante = cfRappresentante;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public void setTipo(String tipo) {
		try {
			this.tipo = Integer.parseInt(tipo);
			}
		catch (Exception e){
		}
		
	}

	public DatiMobile(){

	}
	
	public void queryRecord(Connection db , int id) throws SQLException
	{
		String sql = "select * from opu_stabilimento_mobile where id = ? and enabled";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.buildRecord(rs);
	}

	
	public DatiMobile(Connection db, int id) throws SQLException{
		queryRecord(db, id);
	}
	
	public DatiMobile(ResultSet rs) throws SQLException{
		buildRecord(rs);
	}
	
	private void buildRecord(ResultSet rs){
		try {
			this.id = rs.getInt("id");
			this.targa = rs.getString("targa");
			this.tipo = rs.getInt("tipo");
			this.carta = rs.getString("carta");
			try { this.label = rs.getString("label"); } catch(SQLException e){}
			

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public DatiMobile(ActionContext context, int i){
	setTarga(context.getRequest().getParameter("mobile_targa"+i));
	setTipo(context.getRequest().getParameter("mobile_tipoautoveicolo"+i));
	setCarta(context.getRequest().getParameter("mobile_carta"+i));
	}

	public void insert(Connection db) throws SQLException
	{
		String sql = "insert into opu_stabilimento_mobile (targa, tipo, carta, id_stabilimento) values (?, ?, ?, ?)";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, this.getTarga());
		pst.setInt(2, this.getTipo());
		pst.setString(3, this.getCarta());
		pst.setInt(4, this.getIdStabilimento());
		
		if (this.getTarga()!=null && !this.getTarga().equals("null") && this.getCarta()!=null && !this.getCarta().equals("null") && this.getTipo()>-1) 
			 pst.execute();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	
}



