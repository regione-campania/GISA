/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoInvii;
import org.aspcfs.modules.izsmibr.util.ObjectFactory;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class InvioMassivoDistributori extends GenericBean {
	private int id ;
	private String data;
	private int inviato_da ;
	private int stab_id ;
	private int id_rel_stab_lp ;
	private String esito ; 
	private String md5;
	
	
	
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getInviato_da() {
		return inviato_da;
	}
	public void setInviato_da(int inviato_da) {
		this.inviato_da = inviato_da;
	}
	
	
	
	public int getStab_id() {
		return stab_id;
	}
	public void setStab_id(int stab_id) {
		this.stab_id = stab_id;
	}
	public int getId_rel_stab_lp() {
		return id_rel_stab_lp;
	}
	public void setId_rel_stab_lp(int id_rel_stab_lp) {
		this.id_rel_stab_lp = id_rel_stab_lp;
	}
	public void insert(Connection db) throws SQLException
	{
		
		try
		{
			
			this.id = DatabaseUtils.getNextInt(db, "import_distributori_opu", "id", 1);
			if(id==0)
				id=1;
			PreparedStatement pst = db.prepareStatement("INSERT INTO import_distributori_opu (id,data,user_id,stab_id,id_rel_stab_lp,md5) values (?,?,?,?,?,?)");
			pst.setInt(1, id);
			pst.setString(2, data);
			pst.setInt(3, inviato_da);
			pst.setInt(4, stab_id);
			pst.setInt(5, id_rel_stab_lp);
			pst.setString(6, md5);
			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	public void getImportFormd5(Connection db) throws SQLException
	{
		
		try
		{
			
			
			PreparedStatement pst = db.prepareStatement("select * from import_distributori_opu where md5 ilike ?");
			pst.setString(1, md5);
			ResultSet rs  =pst.executeQuery();
			if(rs.next())
				buildRecord(rs);
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	
	public void aggiornaEsito(Connection db) throws SQLException
	{
		
		try
		{
			
			
			PreparedStatement pst = db.prepareStatement("UPDATE import_distributori_opu SET esito=? where id =?");
			
			pst.setString(1, esito);
			pst.setInt(2, id);
			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	public void insertRecordImportatoKo(Connection db,Distrubutore dist,String errore) throws SQLException
	{
		
		try
		{
			
			
			PreparedStatement pst = db.prepareStatement("INSERT INTO import_distributori_automatici_ko (id_import_distributori_opu,matricola,data,comune,asl,ubicazione,descrizione_errore)values(?,?,?,?,?,?,?)");
			pst.setInt(1, id);
			pst.setString(2, dist.getMatricola());
			pst.setString(3, dist.getDataInst());
			pst.setString(4, dist.getComune());
			pst.setString(5, dist.getAsl());
			pst.setString(6, dist.getUbicazione());
			pst.setString(7, errore);

			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	public ArrayList<Distrubutore> getAllRecordsKo(Connection db) throws ParseException
	{
		ArrayList<Distrubutore> listaRecordInviati = new ArrayList<Distrubutore>();
		
		try
		{
			ObjectFactory createObj = new ObjectFactory();
			PreparedStatement pst = db.prepareStatement("select * from import_distributori_automatici_ko where id_import_distributori_opu=?");
			pst.setInt(1, this.id);
			ResultSet rs = pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (rs.next())
			{
				
				Distrubutore recordGisa = new Distrubutore();
				recordGisa.setMatricola(rs.getString("matricola"));
				recordGisa.setDataInst(rs.getString("data"));
				recordGisa.setComune(rs.getString("comune"));
				recordGisa.setAsl(rs.getString("asl"));
				recordGisa.setUbicazione(rs.getString("ubicazione"));
				recordGisa.setDescrizioneErrore(rs.getString("descrizione_errore"));
				
				
				listaRecordInviati.add(recordGisa);
				
			}
		}
		catch(SQLException e)
		{
			
		}
		return listaRecordInviati ;
	}
	
	public ArrayList<InvioMassivoDistributori> getListaImportDistributori(Connection db,ActionContext context) throws SQLException
	{
		ArrayList<InvioMassivoDistributori> listaInvii = new ArrayList<InvioMassivoDistributori>();
		
		try
		{
		PreparedStatement pst = db.prepareStatement("SELECT * FROM import_distributori_opu where id_rel_stab_lp =?  order by data desc");
		pst.setInt(1, id_rel_stab_lp);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			InvioMassivoDistributori invio = new InvioMassivoDistributori();
			invio.buildRecord(rs);
			
			listaInvii.add(invio);
			
		}
		
		}catch(SQLException e)
		{
			throw e ;
		}
		
		return listaInvii;
	}
	
	public void queryRecord(Connection db,int id) throws SQLException
	{
		
		try
		{
			
			PreparedStatement pst = db.prepareStatement("SELECT * FROM import_distributori_opu WHERE id =?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				buildRecord(rs);
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		data = rs.getString("data");
		inviato_da = rs.getInt("user_id");
		stab_id =rs.getInt("stab_id");
		id_rel_stab_lp =rs.getInt("id_rel_stab_lp");
		esito = rs.getString("esito");
		md5 =rs.getString("md5");
	}

}
