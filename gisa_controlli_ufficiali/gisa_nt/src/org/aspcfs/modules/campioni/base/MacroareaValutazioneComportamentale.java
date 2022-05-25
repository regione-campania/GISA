/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class MacroareaValutazioneComportamentale extends GenericBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private int idCampione ;
	private int statoGenerale ;
	private ArrayList<MacroareaAnomalieValutazione> listaAnomalie = new ArrayList<MacroareaAnomalieValutazione>();
	private int tolleranza ;
	private int adottabilita ;
	private int modifiedBy ;
	
	
	


	public String getAdeguatezza_box() {
		return adeguatezza_box;
	}
	public void setAdeguatezza_box(String adeguatezza_box) {
		this.adeguatezza_box = adeguatezza_box;
	}
	public String getAdeguatezza_sgambamento() {
		return adeguatezza_sgambamento;
	}
	public void setAdeguatezza_sgambamento(String adeguatezza_sgambamento) {
		this.adeguatezza_sgambamento = adeguatezza_sgambamento;
	}
	public String getSegni_di_malessere() {
		return segni_di_malessere;
	}
	public void setSegni_di_malessere(String segni_di_malessere) {
		this.segni_di_malessere = segni_di_malessere;
	}
	public String getAtti_ripetitivi() {
		return atti_ripetitivi;
	}
	public void setAtti_ripetitivi(String atti_ripetitivi) {
		this.atti_ripetitivi = atti_ripetitivi;
	}
	public String getAtti_ripetitivi_campione() {
		return atti_ripetitivi_campione;
	}
	public void setAtti_ripetitivi_campione(String atti_ripetitivi_campione) {
		this.atti_ripetitivi_campione = atti_ripetitivi_campione;
	}
	public String getAggressivita() {
		return aggressivita;
	}
	public void setAggressivita(String aggressivita) {
		this.aggressivita = aggressivita;
	}
	public String getPaura() {
		return paura;
	}
	public void setPaura(String paura) {
		this.paura = paura;
	}
	public String getSocievolezza() {
		return socievolezza;
	}
	public void setSocievolezza(String socievolezza) {
		this.socievolezza = socievolezza;
	}
	public String getGuinzaglio() {
		return guinzaglio;
	}
	public void setGuinzaglio(String guinzaglio) {
		this.guinzaglio = guinzaglio;
	}
	public String getManipolazioni() {
		return manipolazioni;
	}
	public void setManipolazioni(String manipolazioni) {
		this.manipolazioni = manipolazioni;
	}
	public String getPrelievo_ematico() {
		return prelievo_ematico;
	}
	public void setPrelievo_ematico(String prelievo_ematico) {
		this.prelievo_ematico = prelievo_ematico;
	}
	public String getBcs() {
		return bcs;
	}
	public void setBcs(String bcs) {
		this.bcs = bcs;
	}
	public String getPulizia() {
		return pulizia;
	}
	public void setPulizia(String pulizia) {
		this.pulizia = pulizia;
	}
	public String getZoppia() {
		return zoppia;
	}
	public void setZoppia(String zoppia) {
		this.zoppia = zoppia;
	}
	public String getTosse() {
		return tosse;
	}
	public void setTosse(String tosse) {
		this.tosse = tosse;
	}

	String adeguatezza_box ;
	String adeguatezza_sgambamento ;
	String segni_di_malessere  ;
	String atti_ripetitivi  ;
	String atti_ripetitivi_campione  ;
	String aggressivita  ;
	String paura ;
	String socievolezza ;
	String guinzaglio  ;
	String manipolazioni  ;
	String prelievo_ematico  ;
	String bcs ;
	String pulizia ;
	String zoppia ;
	String tosse ;
	
	
	
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setId(String id) {
		if (id != null && ! "".equals(id))
		this.id = Integer.parseInt(id);
	}
	public int getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(int idCampione) {
		this.idCampione = idCampione;
	}
	public void setIdCampione(String idCampione) {
		if (idCampione != null && ! "".equals(idCampione))
			this.idCampione = Integer.parseInt(idCampione);	
		
	}
	
	public int getStatoGenerale() {
		return statoGenerale;
	}
	public void setStatoGenerale(int statoGenerale) {
		this.statoGenerale = statoGenerale;
	}
	
	public void setStatoGenerale(String statoGenerale) {
		if (statoGenerale != null && ! "".equals(statoGenerale))
			this.statoGenerale = Integer.parseInt(statoGenerale);		
		
	}
	
	
	
	public ArrayList<MacroareaAnomalieValutazione> getListaAnomalie() {
		return listaAnomalie;
	}
	public void setListaAnomalie(
			ArrayList<MacroareaAnomalieValutazione> listaAnomalie) {
		this.listaAnomalie = listaAnomalie;
	}
	public int getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(int tolleranza) {
		this.tolleranza = tolleranza;
	}
	public void setTolleranza(String tolleranza) {
		if (tolleranza != null && ! "".equals(tolleranza))
			this.tolleranza = Integer.parseInt(tolleranza);
	}
			
	public int getAdottabilita() {
		return adottabilita;
	}
	public void setAdottabilita(int adottabilita) {
		this.adottabilita = adottabilita;
	}
	public void setAdottabilita(String adottabilita) {
		if (adottabilita != null && ! "".equals(adottabilita))
			this.adottabilita = Integer.parseInt(adottabilita);
	}
	
	public MacroareaValutazioneComportamentale ()
	{
		
	}
	public MacroareaValutazioneComportamentale (Connection db,int idCampione)
	{
		this.queryRecord(db, idCampione);
	}
	public void insert(Connection db,ActionContext context) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
			
		     id =  DatabaseUtils.getNextSeq(db, context,"iuv_campioni_valutazione_comportamentale","id");

			String insert = "insert into iuv_campioni_valutazione_comportamentale (id,id_campione, adeguatezza_box , adeguatezza_sgambamento,"
			+" segni_di_malessere, atti_ripetitivi, atti_ripetitivi_campione, aggressivita, paura, socievolezza, "
			+" guinzaglio, manipolazioni, prelievo_ematico, bcs, pulizia, zoppia, tosse, modified,modifiedby) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_date,?)";
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setInt(++i, id);
			pst.setInt(++i, idCampione);
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("adeguatezza_box")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("adeguatezza_sgambamento")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("segni_di_malessere")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("atti_ripetitivi")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("atti_ripetitivi_campione")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("aggressivita")));
			pst.setString(++i, context.getRequest().getParameter("paura"));
			pst.setString(++i, context.getRequest().getParameter("socievolezza"));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("guinzaglio")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("manipolazioni")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("prelievo_ematico")));
			pst.setString(++i, context.getRequest().getParameter("bcs"));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("pulizia")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("zoppia")));
			pst.setBoolean(++i,Boolean.parseBoolean(""+context.getRequest().getParameter("tosse")));
			pst.setInt(++i, modifiedBy);

			pst.execute();
			
			/*for (MacroareaAnomalieValutazione valutazione : listaAnomalie)
			{
				valutazione.insert(db, id);
			}
			*/
			db.commit();
			db.setAutoCommit(true);

			
			
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
		}
		
	}
	
	public void insert_(Connection db,ActionContext context) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
			
		     id =  DatabaseUtils.getNextSeq(db, context,"iuv_campioni_valutazione_comportamentale","id");

			String insert = "insert into iuv_campioni_valutazione_comportamentale (id,id_campione,id_stato_generale,id_tolleranza,id_adottabilita,modified,modifiedby) values (?,?,?,?,?,current_date,?)";
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setInt(++i, id);
			pst.setInt(++i, idCampione);
			pst.setInt(++i, statoGenerale);
			pst.setInt(++i, tolleranza);
			pst.setInt(++i, adottabilita);
			pst.setInt(++i, modifiedBy);

			pst.execute();
			
			for (MacroareaAnomalieValutazione valutazione : listaAnomalie)
			{
				valutazione.insert(db, id);
			}
			db.commit();
			db.setAutoCommit(true);

			
			
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
		}
		
	}
	
	public void update(Connection db, ActionContext context) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
			String insert = "update iuv_campioni_valutazione_comportamentale set adeguatezza_box = ? , adeguatezza_sgambamento = ? ,"
			+" segni_di_malessere = ? , atti_ripetitivi = ? , atti_ripetitivi_campione = ? , aggressivita = ? , paura = ? , socievolezza = ? , "
			+" guinzaglio = ? , manipolazioni = ? , prelievo_ematico = ? , bcs = ? , pulizia = ? , zoppia = ? , tosse = ?  where id = ? ";
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("adeguatezza_box")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("adeguatezza_sgambamento")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("segni_di_malessere")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("atti_ripetitivi")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("atti_ripetitivi_campione")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("aggressivita")));
			pst.setString(++i, paura);
			pst.setString(++i, socievolezza);
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("guinzaglio")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("manipolazioni")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("prelievo_ematico")));
			pst.setString(++i, bcs);
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("pulizia")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("zoppia")));
			pst.setBoolean(++i,Boolean.parseBoolean(context.getRequest().getParameter("tosse")));
			pst.setInt(++i, id);
			pst.execute();
			
			/*String del = "delete from iuv_campioni_valutazione_comportamentale_anomalie where id_iuv_valutazione = ?";
			pst = db.prepareStatement(del);
			pst.setInt(1, id);
			pst.execute();
			
			for (MacroareaAnomalieValutazione valutazione : listaAnomalie)
			{
				valutazione.update(db, id);
			}*/
			db.commit();
			db.setAutoCommit(true);

			
			
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
		}
		
	}
	
	private void buildRecord(ResultSet rs) throws SQLException
	{
		
		id = rs.getInt("id");
		idCampione = rs.getInt("id_campione");
		adeguatezza_box= ""+""+rs.getBoolean("adeguatezza_box");
		adeguatezza_sgambamento= ""+rs.getBoolean("adeguatezza_sgambamento");
		segni_di_malessere = ""+rs.getBoolean("segni_di_malessere");
		atti_ripetitivi = ""+rs.getBoolean("atti_ripetitivi");
		atti_ripetitivi_campione = ""+rs.getBoolean("atti_ripetitivi_campione");
		aggressivita = ""+rs.getBoolean("aggressivita");
		paura= rs.getString("paura");
		socievolezza= rs.getString("socievolezza");
		guinzaglio = ""+rs.getBoolean("guinzaglio");
		manipolazioni = ""+rs.getBoolean("manipolazioni");
		prelievo_ematico = ""+rs.getBoolean("prelievo_ematico");
		bcs= rs.getString("bcs");
		pulizia= ""+rs.getBoolean("pulizia");
		zoppia= ""+rs.getBoolean("zoppia");
		tosse= ""+rs.getBoolean("tosse");

		
		
	}
	
	private void buildListaAnomalie(Connection db)
	{
		try
		{
			
			//String sel = "select * from iuv_campioni_valutazione_comportamentale_anomalie join lookup_campioni_iuv_anomalie_comportamentali on id_anomalia=code and enabled=true where id_iuv_valutazione = ?";
			String sel = "select * from iuv_campioni_valutazione_comportamentale where id = ?";
			PreparedStatement pst = db.prepareStatement(sel);
			int i = 0 ;
			pst.setInt(++i, id);
			pst.execute();
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
			
				listaAnomalie.add(new MacroareaAnomalieValutazione(rs));
			}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void queryRecord(Connection db,int idCampione)
	{
		
		try
		{
			String sel = "select * from iuv_campioni_valutazione_comportamentale where id_campione = ?";
			PreparedStatement pst = db.prepareStatement(sel);
			int i = 0 ;
			pst.setInt(++i, idCampione);
			pst.execute();
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
			
				this.buildRecord(rs);
			}
			
			//buildListaAnomalie(db);
			
			
			
			
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
		}
		
	}
	

}
