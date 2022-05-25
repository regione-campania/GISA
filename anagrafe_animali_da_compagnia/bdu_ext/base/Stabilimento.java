/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

public class Stabilimento extends Operatore {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.Stabilimento.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	    
	    
	
	private Indirizzo sedeOperativa =new Indirizzo();
	private LineaProduttivaList listaLineeProduttive  = new  LineaProduttivaList() ;
	private int idStabilimento ;
	private Timestamp entered;
	private Timestamp modified;
	private int enteredBy ;
	private int modifiedBy ;
	private int idAsl ;
	private boolean flagFuoriRegione = false;
	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}
	
	public void setFlagFuoriRegione(
			String flagInRegione) {
		this.flagFuoriRegione = (("NO").equalsIgnoreCase(flagInRegione));
	}



	private SoggettoFisico rappLegale = new SoggettoFisico();



	public Stabilimento(){
		
		rappLegale = new SoggettoFisico();
    	sedeOperativa = new Indirizzo() ;
		
	}
	
	public Stabilimento(ResultSet rs) throws SQLException{
		this.buildRecordStabilimento(rs);
	}

	public Stabilimento(Connection db, int idOperatore) throws SQLException, IndirizzoNotFoundException{

		super.queryRecordOperatore(db, idOperatore);
		
		
	}
	
	public Stabilimento(Connection db, int idOperatore,int idStabilimento) throws SQLException, IndirizzoNotFoundException{

		super.queryRecordOperatore(db, idOperatore);
		queryRecordStabilimento(db,idStabilimento);
		
		
	}

	public void queryRecordStabilimento(Connection db , int idStabilimento) throws SQLException, IndirizzoNotFoundException
	{
		if (idStabilimento == -1){
			throw new SQLException("Invalid Account");
		}
		
		PreparedStatement pst = db.prepareStatement("Select * from opu_stabilimento o where o.id = ?");
		pst.setInt(1, idStabilimento);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	    	buildRecordStabilimento(rs);
	    	rappLegale = new SoggettoFisico(db,rs.getInt("id_soggetto_fisico"));
	    	sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
	    	listaLineeProduttive.setIdStabilimento(idStabilimento);
		    listaLineeProduttive.buildListStabilimento(db);
	    }

	    if (idStabilimento == -1) {
	      throw new SQLException(Constants.NOT_FOUND_ERROR);
	    }
	    
	    
	    
	    
	    
	    
	    
	    rs.close();
	    pst.close();
	    buildRappresentante(db);
	    rs.close();
	    pst.close();
		
	}

	
	
	public void setSedeOperativa(Sede sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public SoggettoFisico getRappLegale() {
		return rappLegale;
	}

	public void setRappLegale(SoggettoFisico rappLegale) {
		this.rappLegale = rappLegale;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		if (idAsl!= null && !idAsl.equals(""))
			this.idAsl = Integer.parseInt(idAsl);
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
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

	public LineaProduttivaList getListaLineeProduttive() {
		return listaLineeProduttive;
	}

	public void setListaLineeProduttive(LineaProduttivaList listaLineeProduttive) {
		this.listaLineeProduttive = listaLineeProduttive;
	}




	

	public Indirizzo getSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(Indirizzo sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public boolean insert (Connection db, boolean insertLinee) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;


			
			this.idStabilimento =DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq");
			sql.append("INSERT INTO opu_stabilimento (");

			
			if (idAsl > 0) {
				sql.append("id_asl, ");
			}
			
			if (idStabilimento > -1) {
				sql.append("id, ");
			}

			if (enteredBy > -1){
				sql.append("entered_by,");
			}

			if (modifiedBy > -1){
				sql.append("modified_by,");
			}
			if (super.getIdOperatore() > -1){
				sql.append("id_operatore,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("id_soggetto_fisico,");
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("id_indirizzo,");
				
			}
			
			if (flagFuoriRegione){
				sql.append("flag_fuori_regione,");
			}
			
			sql.append("entered,modified)");
			sql.append("VALUES (");

			if (idAsl > 0) {
				sql.append("?, ");
			}
			
			if (idStabilimento > -1) {
				sql.append("?,");
			}

			if (enteredBy > -1){
				sql.append("?,");
			}


			if (modifiedBy > -1){
				sql.append("?,");
			}
			if (super.getIdOperatore() > -1){
				sql.append("?,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("?,");
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("?,");
				
			}
			
			
			if (flagFuoriRegione){
				sql.append("?,");
			}


			sql.append("current_date,current_date)");


			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			if (idAsl > 0) {
				pst.setInt(++i,idAsl);
			}
			
			if (idStabilimento > -1) {
				pst.setInt(++i,idStabilimento);
			}

			if (enteredBy > -1){
				pst.setInt(++i, this.enteredBy);
			}


			if (modifiedBy > -1){
				pst.setInt(++i, this.modifiedBy);
			}
			if (super.getIdOperatore() > -1){
				pst.setInt(++i, super.getIdOperatore());
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				pst.setInt(++i, rappLegale.getIdSoggetto());
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				pst.setInt(++i, sedeOperativa.getIdIndirizzo());
				
			}
			
			if (flagFuoriRegione){
				pst.setBoolean(++i, flagFuoriRegione); // di default è false
			}

			pst.execute();
			pst.close();

//			this.idStabilimento = DatabaseUtils.getCurrVal(db, "stabilimento_id_seq", idStabilimento);

			
			if (insertLinee){
			
			LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
			Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
			while(itLp.hasNext()){

				LineaProduttiva temp = itLp.next();
				this.aggiungiLineaProduttiva(db, temp);
			}
			}
			
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;
	}

	
	public LineaProduttiva aggiungiLineaProduttiva (Connection db , LineaProduttiva lp)
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			int i = 0 ;
			sql.append("INSERT INTO opu_relazione_stabilimento_linee_produttive (id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato) values (?,?,?,?,?) ");
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idStabilimento) ;
			pst.setInt(++i, lp.getIdRelazioneAttivita()); //PRIMA pst.setInt(++i, lp.getId());
			
			pst.setTimestamp(++i, lp.getDataInizio());
			pst.setTimestamp(++i, lp.getDataFine());
			pst.setInt(++i, lp.getStato());

			
			pst.execute();
			
			lp.setId(DatabaseUtils.getCurrVal(db, "opu_relazione_stabilimento_linee_produttive_id_seq",
					1));
			
			//Salvo le informazioni pertinenti la linea operativa
			lp.insert(db);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
			
		}
		return lp ;
		
	}

	
	
  protected void buildRecordStabilimento(ResultSet rs) throws SQLException {
		  
		  this.setIdStabilimento(rs.getInt("id"));
		  this.setEnteredBy(rs.getInt("entered_by"));
		  this.setModifiedBy(rs.getInt("modified_by"));
		  this.setIdOperatore(rs.getInt("id_operatore"));
		  this.setIdAsl(rs.getInt("id_asl"));
		  this.setFlagFuoriRegione(rs.getBoolean("flag_fuori_regione"));
		  sedeOperativa.setIdIndirizzo(rs.getInt("id_indirizzo"));
		  rappLegale.setIdSoggetto(rs.getInt("id_soggetto_fisico"));
		//  buildSede(rs);
		//  buildRappresentanteLegale(rs);

		  
		  
	  }











}
