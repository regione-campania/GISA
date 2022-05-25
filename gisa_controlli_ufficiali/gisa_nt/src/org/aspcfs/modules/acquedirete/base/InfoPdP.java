/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class InfoPdP extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private int id_controllo ;
	private int org_id_pdp ;
	private String temperatura ;
	private String cloro ;
	private String ore  ;
	private boolean prot_routine ;
	private boolean prot_verifica ;
	private boolean prot_replica_micro;
	private boolean prot_replica_chim;
	private String altro ;
	private int id_campione ;
	
	private boolean prot_radioattivita;
	private boolean prot_ricerca_fitosanitari;

	
	private Organization orgdDetails ;
	
	public Organization getOrgdDetails() {
		return orgdDetails;
	}
	public void setOrgdDetails(Organization orgdDetails) {
		this.orgdDetails = orgdDetails;
	}
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_controllo() {
		return id_controllo;
	}
	public void setId_controllo(int id_controllo) {
		this.id_controllo = id_controllo;
	}
	public int getOrg_id_pdp() {
		return org_id_pdp;
	}
	public void setOrg_id_pdp(int org_id_pdp) {
		this.org_id_pdp = org_id_pdp;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getCloro() {
		return cloro;
	}
	public void setCloro(String cloro) {
		this.cloro = cloro;
	}
	public String getOre() {
		return ore;
	}
	public void setOre(String ore) {
		this.ore = ore;
	}
	public boolean isProt_routine() {
		return prot_routine;
	}
	public void setProt_routine(boolean prot_routine) {
		this.prot_routine = prot_routine;
	}
	public boolean isProt_verifica() {
		return prot_verifica;
	}
	public void setProt_verifica(boolean prot_verifica) {
		this.prot_verifica = prot_verifica;
	}
	public boolean isProt_replica_micro() {
		return prot_replica_micro;
	}
	public void setProt_replica_micro(boolean prot_replica_micro) {
		this.prot_replica_micro = prot_replica_micro;
	}
	public boolean isProt_replica_chim() {
		return prot_replica_chim;
	}
	public void setProt_replica_chim(boolean prot_replica_chim) {
		this.prot_replica_chim = prot_replica_chim;
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
	}
	
	public boolean isProt_radioattivita() {
		return prot_radioattivita;
	}
	public void setProt_radioattivita(boolean prot_radioattivita) {
		this.prot_radioattivita = prot_radioattivita;
	}

	public boolean isProt_ricerca_fitosanitari() {
		return prot_ricerca_fitosanitari;
	}
	public void setProt_ricerca_fitosanitari(boolean prot_ricerca_fitosanitari) {
		this.prot_ricerca_fitosanitari = prot_ricerca_fitosanitari;
	}

	
	public int getId_campione() {
		return id_campione;
	}
	public void setId_campione(int id_campione) {
		this.id_campione = id_campione;
	}
	public InfoPdP(){}
	public InfoPdP(ResultSet rs) throws SQLException
	{
		this.buildRecord(rs);
		
	}
	
	public String getDescrizioneProtocolloSelezionato()
	{
		String prot = "" ;
		if (prot_routine == true){
			prot = "PROTOCOLLO DI ROUTINE" ;
		}
		
		if (prot_verifica == true){
			prot = "PROTOCOLLO DI VERIFICA" ;
			}
		
		if (prot_replica_micro == true){
		prot = "PROTOCOLLO DI REPLICA (MICROBIOLOGICO)" ;
		}
		
		if (prot_replica_chim == true){
			prot = "PROTOCOLLO DI REPLICA (CHIMICO)" ;
			}
		
		return prot ;
	}
	
	public boolean isProtocolloSelezionato(int i)
	{
		if (i == 0)
			return prot_routine;
		
		if (i == 1)
			return prot_verifica;
		
		if (i == 2)
			return prot_replica_micro;
		
		if (i == 3)
			return prot_replica_chim;
		
		if (i == 4)
			return prot_radioattivita;
		
		if (i == 5)
			return prot_ricerca_fitosanitari;
			
		return false;
	}
	
	public String getDescrizioneProtocollo(int i)
	{
		String prot = "" ;
		if (i == 0){
			prot = "PROTOCOLLO DI ROUTINE" ;
		}
		
		if (i == 1){
			prot = "PROTOCOLLO DI VERIFICA" ;
			}
		
		if (i == 2){
		prot = "PROTOCOLLO DI REPLICA (MICROBIOLOGICO)" ;
		}
		
		 if (i == 3){
				prot = "PROTOCOLLO DI REPLICA (CHIMICO)" ;
				}
	
		 if (i == 4){
				prot = "PROTOCOLLO DI RADIOATTIVITA'" ;
				}
		 
		if (i == 5){
				prot = "PROTOCOLLO DI RICERCA FITOSANITARI" ;
				}
			
		return prot ;
	}
	
	public void buildRecord(ResultSet rs) throws SQLException
	{
		 id =rs.getInt("id");
		 id_controllo =rs.getInt("id_controllo");
		 org_id_pdp = rs.getInt("org_id_pdp") ;
		 temperatura =rs.getString("temperatura");
		 cloro =rs.getString("cloro");
		 ore  =rs.getString("ore");
		prot_routine =rs.getBoolean("prot_routine");
		prot_verifica  =rs.getBoolean("prot_verifica");
		prot_replica_micro =rs.getBoolean("prot_replica_micro");
		prot_replica_chim =rs.getBoolean("prot_replica_chim");
		altro = rs.getString("altro");
		id_campione = rs.getInt("id_campione");
		
		prot_radioattivita =rs.getBoolean("prot_radioattivita");
		prot_ricerca_fitosanitari =rs.getBoolean("prot_ricerca_fitosanitari");

	}
	
	public static InfoPdP InfoPdPCampione(Connection db,int id_campione) throws SQLException {
		InfoPdP pdp = null ;
		ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	   
	    
	    sqlSelect.append("SELECT * from controlli_punti_di_prelievo_acque_rete where 1=1 and enabled = true and id_campione = ? ");

	   
	   
	   PreparedStatement pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	  pst.setInt(1, id_campione);
	  
	    
	    rs = DatabaseUtils.executeQuery(db, pst);
	    if (rs.next())
	    {
	    	pdp = new InfoPdP();
	    	pdp.buildRecord(rs);
	    	
	    }
	    return pdp ;
	  }
  
	  
	public void insert(Connection db)
	{
		int i = 0 ;
		String insert = "insert into controlli_punti_di_prelievo_acque_rete (id_controllo ,id_campione,org_id_pdp ,temperatura  ,cloro  ,ore ,prot_routine  ,prot_verifica ,prot_replica_micro  ,prot_replica_chim ,altro, prot_radioattivita, prot_ricerca_fitosanitari)" +
				"values" +
				"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{ 
		PreparedStatement pst = db.prepareStatement(insert);
		pst.setInt(++i,id_controllo );
		pst.setInt(++i, id_campione);
		pst.setInt(++i, org_id_pdp);
		pst.setString(++i, temperatura);
		pst.setString(++i, cloro);
		pst.setString(++i, ore);
		pst.setBoolean(++i, prot_routine);
		pst.setBoolean(++i, prot_verifica);
		pst.setBoolean(++i, prot_replica_micro);
		pst.setBoolean(++i, prot_replica_chim);
		pst.setString(++i, altro);

		pst.setBoolean(++i, prot_radioattivita);
		pst.setBoolean(++i, prot_ricerca_fitosanitari);
		
		pst.execute();
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setIdCampionePrecedente(Connection db, int idControllo) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select id_campione from controlli_punti_di_prelievo_acque_rete where id_controllo = ? and org_id_pdp = ? and id_campione > 0 limit 1");
		pst.setInt(1, idControllo);
		pst.setInt(2, org_id_pdp);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.id_campione = rs.getInt("id_campione");
		}

	
}
