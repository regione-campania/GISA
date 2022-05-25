/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Riunione extends GenericBean{
	
	
	public static final int STATO_BOZZA =1 ;
	
	public static final int STATO_IN_APPROVAZIONE =2 ;
	
	public static final int STATO_COMPLETO =3 ;
	
	public static final int STATO_CHIUSO_NON_MODIFICABILE =5 ;
	

	private int id ;
	private String titolo ;
	private String descrizioneBreve ;
	private Timestamp data ;
	private String luogo ;
	private ArrayList<String> listaPartecipanti = new ArrayList<String>();
	private ArrayList<ReferenteApprovazione> listaReferenti = new ArrayList<ReferenteApprovazione>();
	private ArrayList<Rilascio> listaRilasci = new ArrayList<Rilascio>();
	private int stato ;
	
	private int enteredby ; 
	private  Timestamp entered;
	
	private String contesto ;
	
	
	
	public String getContesto() {
		return contesto;
	}
	public void setContesto(String contesto) {
		this.contesto = contesto;
	}
	public ArrayList<ReferenteApprovazione> getListaReferenti() {
		return listaReferenti;
	}
	public void setListaReferenti(ArrayList<ReferenteApprovazione> listaReferenti) {
		this.listaReferenti = listaReferenti;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}
	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public void setData(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data!=null && !"".equals(data))
		{
			this.data = new Timestamp(sdf.parse(data).getTime());
		}
		
	}
	
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public ArrayList<String> getListaPartecipanti() {
		return listaPartecipanti;
	}
	public void setListaPartecipanti(ArrayList<String> listaPartecipanti) {
		this.listaPartecipanti = listaPartecipanti;
	}
	
	public void setListaPartecipanti(String[] listaPartecipanti) {
		for (int i = 0 ; i  < listaPartecipanti.length ; i++)
		{
		this.listaPartecipanti.add(listaPartecipanti[i]);
		}
	}


	public void insert(Connection db)
	{

		
		 
		String insert = "INSERT INTO riunioni (id,titolo,descrizione_breve,data,luogo,entered,enteredby,stato,contesto) values ( ?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
		PreparedStatement pst = null ;
		try
		{
			this.id = DatabaseUtils.getNextSeq(db, "riunioni_id_seq");
			
			int i = 0 ;
			pst = db.prepareStatement(insert);
			
			pst.setInt(++i, id);
			pst.setString(++i, titolo);
			pst.setString(++i, descrizioneBreve);
			pst.setTimestamp(++i, data);
			pst.setString(++i, luogo);
			pst.setInt(++i, enteredby);
			pst.setInt(++i, stato);
			pst.setString(++i, contesto);
			pst.execute();
			
			
			String insertRelazione = "INSERT INTO riunioni_partecipanti (id_riunione,nominativo,entered,enteredby ) values(?,?,current_timestamp,?)";
			for (String partecipante : listaPartecipanti) 
			{
				pst = db.prepareStatement(insertRelazione);
				pst.setInt(1, this.getId());
				pst.setString(2, partecipante);
				pst.setInt(3, enteredby);
				pst.execute();
			}
			
			String insertReferente= "INSERT INTO referenti_approvazione_riunioni (id_riunione,id_referente,entered,enteredby,modified,modifiedby,stato ) values(?,?,current_timestamp,?,current_timestamp,?,?)";
			for (ReferenteApprovazione referente : listaReferenti) 
			{
				pst = db.prepareStatement(insertReferente);
				pst.setInt(1, this.getId());
				pst.setInt(2, referente.getReferente().getId());
				pst.setInt(3, enteredby);
				pst.setInt(4, enteredby);
				pst.setInt(5, stato);
				pst.execute();
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	
	public void chiusuraUfficio(Connection db, int closedby )
	{

		
		 
		String update = "UPDATE riunioni set stato = ? ,closed_by = ?,closed=current_timestamp where id = ?";
		PreparedStatement pst = null ;
		try
		{
			
			int i = 0 ;
			pst = db.prepareStatement(update);
			
			pst.setInt(++i, STATO_CHIUSO_NON_MODIFICABILE);
			pst.setInt(++i, closedby);
			pst.setInt(++i, id);
			pst.execute();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	
	public void queryRecord(Connection db,int id) 
	{
		String select ="select * from riunioni where id =?" ;
		
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				loadResultSet(rs);
				
				String selectPartecipante = "select * from riunioni_partecipanti where id_riunione =?";
				pst = db.prepareStatement(selectPartecipante);
				pst.setInt(1, this.getId());
				ResultSet rs1 = pst.executeQuery();
				while(rs1.next())
				{
					listaPartecipanti.add(rs1.getString("nominativo"));
				}
				
				buildListaReferenti(db);
				buildListaRilasci(db);
				
			
			}
				
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	public void loadResultSet (ResultSet rs) throws SQLException
	{
		
		try
		{
			id =rs.getInt("id");
			titolo = rs.getString("titolo");
			descrizioneBreve=rs.getString("descrizione_breve");
			data=rs.getTimestamp("data");
			luogo = rs.getString("luogo");
			stato = rs.getInt("stato");
			contesto=rs.getString("contesto");
			
		}
		catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	public void cambiaStatoReferente(Connection db,int userIdReferente,int idStatoCambiato)
	{
		ReferenteApprovazione ref = null ;
		for(int i = 0 ; i < this.getListaReferenti().size(); i++)
		{
			if (this.getListaReferenti().get(i).getReferente().getUserId()==userIdReferente )
			{
				ref = (ReferenteApprovazione) this.getListaReferenti().get(i) ;
				break ;
			}
		}
	
	}

	public void ricalcolaStato(Connection db )
	{
		try
		{
			
			int nuovoStato = STATO_COMPLETO ;
			int numBozze = 0 ;
			int numApprovazioni = 0 ;
			for (ReferenteApprovazione approvazione : listaReferenti)
			{
				
				if (approvazione.getStato()==STATO_BOZZA)
				{
					numBozze ++ ;
				}
				else
				{
					numApprovazioni++ ;
				}
				
			}
			
			if (numBozze==0 && numApprovazioni==listaReferenti.size())
				nuovoStato = STATO_COMPLETO ;
			else
			{
				if (numBozze>0 && numApprovazioni>0 )
				{
					nuovoStato = STATO_IN_APPROVAZIONE;
				}
			}
			
			
			PreparedStatement pst = db.prepareStatement("update riunioni set stato = "+nuovoStato+" where id="+id);
			pst.execute();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

	public void buildListaReferenti(Connection db) throws SQLException{
		String selectReferenti= "select ref.user_id_access,ref.id as id_referente,ref.nominativo as nominativo_referente,ref.trashed_date as trashed_date_ref,ra.* from referenti_approvazione_riunioni ra join referenti_riunioni ref on ref.id = ra.id_referente where id_riunione =?";
		PreparedStatement pst = db.prepareStatement(selectReferenti);
		pst.setInt(1, this.getId());
		ResultSet rs1 = pst.executeQuery();
		while(rs1.next())
		{
			Referente ref = new Referente();
			ref.loadResultSet(rs1);
			
			ReferenteApprovazione approvazione = new ReferenteApprovazione();
			approvazione.loadResultSet(rs1);
			approvazione.setReferente(ref);
			
			this.listaReferenti.add(approvazione);
			
		}
	}
	public ArrayList<Rilascio> getListaRilasci() {
		return listaRilasci;
	}
	public void setListaRilasci(ArrayList<Rilascio> listaRilasci) {
		this.listaRilasci = listaRilasci;
	}
	
	public void buildListaRilasci(Connection db) throws SQLException{
		String selectRilasci= "select rilasci.id, oggetto, data, note_note,note_modulo, note_funzione, note_id_contesto, entered, enteredby  from rilasci left join rilasci_riunioni rr on rilasci.id = rr.id_rilascio where rr.id_riunione =? and rilasci.trashed_date is null and rr.trashed_date is null order by rilasci.data desc";
		PreparedStatement pst = db.prepareStatement(selectRilasci);
		pst.setInt(1, this.getId());
		ResultSet rs1 = pst.executeQuery();
		while(rs1.next())
		{
			Rilascio ril = new Rilascio();
			ril.loadResultSet(rs1);
			this.listaRilasci.add(ril);
			
		}
	}
}
