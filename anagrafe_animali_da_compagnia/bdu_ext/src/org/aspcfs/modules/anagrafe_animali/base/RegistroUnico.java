/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Logger;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class RegistroUnico 
{
	Logger logger = Logger.getLogger("MainLogger");

	private int idAnimale;

	private String microchip;
	private String tatuaggio;
	private int idProprietario;
	private int idEvento;
	private int idAslProprietario;
	private Timestamp dataInserimentoRegistrazione;
	private Timestamp dataRegistrazione;
	private String aslProprietario;
	private double valutazioneDehasse;
	private String tipologia;
	private String proprietario;
	private int idTipologiaRegistrazione;
	private int idTipologiaEvento;
	private String evento;
	private String[] veterinari;
	private String idCu;
	private String misureRestrittive ;
	private String misureRiabilitative;
	private String misureFormative;
	private int anno;
	private PagedListInfo pagedListInfo = null;
	
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	
	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}
	
	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	public String getTatuaggio() {
		return tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
	
	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public int getIdAslProprietario() {
		return idAslProprietario;
	}

	public void setIdAslProprietario(int idAslProprietario) {
		this.idAslProprietario = idAslProprietario;
	}

	public String getAslProprietario() {
		return aslProprietario;
	}

	public void setAslProprietario(String aslProprietario) {
		this.aslProprietario = aslProprietario;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public Timestamp getDataInserimentoRegistrazione() {
		return dataInserimentoRegistrazione;
	}

	public void setDataInserimentoRegistrazione(Timestamp dataInserimentoRegistrazione) {
		this.dataInserimentoRegistrazione = dataInserimentoRegistrazione;
	}

	public Timestamp getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public double getValutazioneDehasse() {
		return valutazioneDehasse;
	}

	public void setValutazioneDehasse(double valutazioneDehasse) {
		this.valutazioneDehasse = valutazioneDehasse;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String[] getVeterinari() {
		return veterinari;
	}

	public void setVeterinari(String[] veterinari) {
		this.veterinari = veterinari;
	}

	public String getIdCu() {
		return idCu;
	}

	public void setIdCu(String idCu) {
		this.idCu = idCu;
	}

	public String getMisureFormative() {
		return misureFormative;
	}

	public void setMisureFormative(String misureFormative) {
		this.misureFormative = misureFormative;
	}
	
	public String getMisureRiabilitative() {
		return misureRiabilitative;
	}

	public void setMisureRiabilitative(String misureRiabilitative) {
		this.misureRiabilitative = misureRiabilitative;
	}
	
	public String getMisureRestrittive() {
		return misureRestrittive;
	}

	public void setMisureRestrittive(String misureRestrittive) {
		this.misureRestrittive = misureRestrittive;
	}
	
	
	public int getIdTipologiaRegistrazione() {
		return idTipologiaRegistrazione;
	}

	public void setIdTipologiaRegistrazione(int idTipologiaRegistrazione) {
		this.idTipologiaRegistrazione = idTipologiaRegistrazione;
	}
	
	public int getIdTipologiaEvento() {
		return idTipologiaEvento;
	}

	public void setIdTipologiaEvento(int idTipologiaEvento) {
		this.idTipologiaEvento = idTipologiaEvento;
	}

	
	
	
	public RegistroUnico(Connection db, int idEvento) throws SQLException 
	{
		PreparedStatement pst = db.prepareStatement("select * from registro_unico_cani_rischio_elevato_aggressivita where id_evento = ?");
		pst.setInt(1, idEvento);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst); 
		 if (rs.next())
		 {
			 buildRecord(rs, db);
			 buildVeterinari(db, this.getIdTipologiaEvento());
		 }
	}

	public RegistroUnico(){}

	private void buildRecord(ResultSet rs, Connection db) throws SQLException 
	{
		this.anno =  rs.getInt("anno");
		this.aslProprietario =  rs.getString("asl_proprietario");
		this.dataInserimentoRegistrazione =  rs.getTimestamp("data_inserimento_registrazione");
		this.dataRegistrazione =  rs.getTimestamp("data_registrazione");
		this.evento =  rs.getString("evento");
		this.idAnimale =  rs.getInt("id_animale");
		this.idAslProprietario =  rs.getInt("id_asl_proprietario");
		this.idCu =  rs.getString("id_cu");
		this.idEvento =  rs.getInt("id_evento");
		this.idProprietario =  rs.getInt("id_proprietario");
		this.idTipologiaRegistrazione =  rs.getInt("id_tipologia_registrazione");
		this.idTipologiaEvento =  rs.getInt("id_tipologia_evento");
		this.microchip =  rs.getString("microchip");
		this.misureRestrittive =  rs.getString("misure_restrittive");
		this.misureRiabilitative =  rs.getString("misure_riabilitative");
		this.misureFormative =  rs.getString("misure_formative");
		this.proprietario = rs.getString("proprietario");
		this.tatuaggio =  rs.getString("anno");
		this.tipologia =  rs.getString("tipologia");
		this.valutazioneDehasse =  rs.getDouble("valutazione_dehasse");
	}
	
	 public PreparedStatement prepareList(Connection db, int anno, int asl) throws SQLException {
		    ResultSet rs = null;
		    int items = -1;
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlSelect = new StringBuffer();
		    sqlCount.append(" select count(*) AS recordcount from get_registro_unico_cani_aggressori(?,?) ");
		    
		    if (pagedListInfo != null) 
		    {
		      PreparedStatement pst = db.prepareStatement(sqlCount.toString() );
		      pagedListInfo.doManualOffset(db, pst);
		      
		      pst.setInt(1, anno);
			  pst.setInt(2, asl);
		      rs = pst.executeQuery();
		      if (rs.next()) 
		      {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		      if (!pagedListInfo.getCurrentLetter().equals("")) 
		      {
		        pst = db.prepareStatement(sqlCount.toString());
		        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
		        if (pagedListInfo != null) 
		        {
		          pagedListInfo.doManualOffset(db, pst);
		        }
		        pst.setInt(1, anno);
				pst.setInt(2, asl);
		        rs = pst.executeQuery();
		        
		        if (rs.next()) 
		        {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }

		    } 
		    if (pagedListInfo != null) 
		    {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } 
		    else 
		    {
		      sqlSelect.append("SELECT ");
		    }
		    sqlSelect.append(" * from get_registro_unico_cani_aggressori(?,?) ");
		    
		    
		    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() );
		    return pst;
		  }
	
	
	
	
	
	public RegistroUnicoList getRegistri(Connection db, int anno, int asl) throws SQLException 
	{
		RegistroUnicoList registri = new RegistroUnicoList();
		//PreparedStatement pst = db.prepareStatement(" select * from get_registro_unico_cani_aggressori(?,?) ");
		PreparedStatement pst = prepareList(db, anno, asl);
		
		pst = db.prepareStatement(" select * from get_registro_unico_cani_aggressori(?,?) offset " +  pagedListInfo.getCurrentOffset() + " limit " + pagedListInfo.getItemsPerPage());
		pst.setInt(1, anno);
		pst.setInt(2, asl);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst );  
		 
		while (rs.next())
		{
			RegistroUnico reg = new RegistroUnico();
			reg.buildRecord(rs, db);
			registri.add(reg);
		}
		
		return registri;
			 
	}
	
	
	public static Integer getMinAnno(Connection db) throws SQLException 
	{
		Integer anno = null;
		PreparedStatement pst = db.prepareStatement("Select min(anno) as anno from registro_unico_cani_rischio_elevato_aggressivita");
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);

		if(rs.next())
			anno = rs.getInt("anno");

		rs.close();
		pst.close();
		
		return anno;
		
}
	
	
	public void buildVeterinari(Connection db, int idTipologiaEvento) throws SQLException 
	{
		String tabella = "evento_morsicatura_veterinari";
		if(idTipologiaEvento==68)
			tabella = "evento_aggressione_veterinari";
		
		PreparedStatement pst = db.prepareStatement("Select count(*) as conta from " + tabella +  " where id_evento = ?");
		pst.setInt(1, idEvento);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);

		if(rs.next())
			veterinari = new String[rs.getInt("conta")];

		pst = db.prepareStatement("Select * from " + tabella +  " where id_evento = ?");
		pst.setInt(1, idEvento);
		rs = DatabaseUtils.executeQuery(db, pst);
				
		int j=0;
		while(rs.next()) 
		{
			veterinari[j] = rs.getString("id_veterinario");
			j++;
		}


		rs.close();
		pst.close();
		
}
	
	
	public String getVeterinariEstesi()
	{
		String toReturn = "";
		Connection db = null;
		try
		{

		Thread t = Thread.currentThread();
		db = GestoreConnessioni.getConnection();
		
		
		
		int j=0;
		while(j<this.getVeterinari().length)		
		{
			User user = new User();
			user.setBuildContact(true);
			
			if(j>0)
				toReturn+="<br/>";
			
			if (Integer.parseInt(this.getVeterinari()[j])>-1)
				user.buildRecord(db, Integer.parseInt(this.getVeterinari()[j]));
			else
				return "";
			toReturn += user.getContact().getNameFull() ;
			j++;
		}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return toReturn;
	}
}


 


	

