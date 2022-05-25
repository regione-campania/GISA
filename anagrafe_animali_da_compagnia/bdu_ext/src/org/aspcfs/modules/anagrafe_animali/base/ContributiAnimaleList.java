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
import java.util.Vector;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class ContributiAnimaleList extends Vector implements SyncableList{
	
	
	private int idPraticaContributi = -1;
	private int idAsl = -1;
	
	
	  protected java.sql.Timestamp lastAnchor = null;
	  protected java.sql.Timestamp nextAnchor = null;
	  protected int syncType = Constants.NO_SYNC;
	  protected PagedListInfo pagedListInfo = null;

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public int getIdPraticaContributi() {
		return idPraticaContributi;
	}

	public void setIdPraticaContributi(int idPraticaContributi) {
		this.idPraticaContributi = idPraticaContributi;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	
	//Flusso 251
	  public void buildListCaniNonPagati(Connection db, Integer idComune) throws SQLException, IndirizzoNotFoundException {
		    PreparedStatement pst = null;

		    //Flusso 251
		    ResultSet rs = queryListNonPagati(db, pst, idComune);
		    while (rs.next()) {
		    	
		    	
		    	Animale thisAnimale = new Animale();
		    	thisAnimale.setIdSpecie(rs.getInt("id_specie"));
//		    	
//		    	if (rs.getInt("id_specie") == Cane.idSpecie){
//		    		 thisAnimale = new Cane(db, rs.getInt("id_animale"));
//		    	}else if(rs.getInt("id_specie") == Gatto.idSpecie){
//		    		 thisAnimale = new Gatto(db, rs.getInt("id_animale"));
//		    	}
//		    	 
		    	thisAnimale.setIdAnimale(rs.getInt("id_animale"));
		    	thisAnimale.setMicrochip(rs.getString("microchip"));
		    	thisAnimale.setNomeCognomeProprietario(rs.getString("proprietario"));
		    	thisAnimale.setNomeCognomeDetentore(rs.getString("detentore"));
		    	//recupero i dati del cane al momento dell'inserimento nella pratica
		    	thisAnimale.setIdProprietario(rs.getInt("id_proprietario"));
		    	thisAnimale.setIdDetentore(rs.getInt("id_detentore"));
		    	thisAnimale.setIdAslRiferimento(rs.getInt("asl"));
		    	thisAnimale.setIdComuneProprietario(rs.getInt("id_comune_proprietario"));
		    	thisAnimale.setIdComuneDetentore(rs.getInt("id_comune_detentore"));
		    	thisAnimale.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
		    	thisAnimale.setIdComuneCattura(rs.getInt("id_comune_cattura"));
		    	
		    	if (rs.getString("tipologia").equals("Catturato")){
		    		//Se era catturato al momento dell'inserimento nella pratica
		    		thisAnimale.setFlagCattura(true);
		    		}
		      
			      //Costruisco proprietario e detentore
//			      Operatore proprietario = null;
//			      Operatore detentore = null;
//			      
//			      if (rs.getInt("id_proprietario") > -1)
//			      {
//			    	   proprietario = new Operatore();
//			    	   proprietario.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_proprietario"));
//			      }
//			      
//			      if (rs.getInt("id_detentore") > -1)
//			      {
//			    	   detentore = new Operatore();
//			    	   detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
//			      }
			      
			     
			     // thisAnimale.setProprietario(proprietario);
			    //  thisAnimale.setDetentore(detentore);
			      
			      this.add(thisAnimale);
			      
/*			      
			      if (thisAnimale.getIdSpecie() == Cane.idSpecie){
			    	  Cane a = new Cane(db, thisAnimale.getIdAnimale());
			    	   detentore = new Operatore();
			    	  detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
			    	  a.setDetentore(detentore);
			    	  a.setIdDetentore(rs.getInt("id_detentore"));
			    	  this.add(a);
			      }		
			      
			      if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
			    	  Gatto a = new Gatto(db, thisAnimale.getIdAnimale());
			    	   detentore = new Operatore();
			    	  detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
			    	  a.setDetentore(detentore);
			    	  a.setIdDetentore(rs.getInt("id_detentore"));
			    	  this.add(a);
			      }
			      */
			      
			      //thisAnimale.setDetentore(detentore);
			      //  this.add(thisAnimale);
			     
			    }
		 
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		 //   buildResources(db);
		  }
	  
	  
	  public void buildListCaniInviatiPerApprovazione(Connection db) throws SQLException, IndirizzoNotFoundException {
		    PreparedStatement pst = null;
		    try{
		    ResultSet rs = queryListInviati(db, pst);
		    while (rs.next()) {
		    	Animale thisAnimale = new Animale();
		    	
		    	
		    	thisAnimale.setIdSpecie(rs.getInt("id_specie"));

		    	 
		    	thisAnimale.setIdAnimale(rs.getInt("id_animale"));
		    	thisAnimale.setMicrochip(rs.getString("microchip"));
		    	thisAnimale.setIdProprietario(rs.getInt("proprietario"));
		    	thisAnimale.setIdDetentore(rs.getInt("id_detentore"));
		    	thisAnimale.setNomeCognomeProprietario(db);
		    	thisAnimale.setNomeCognomeDetentore(db);
	    	
		    	thisAnimale.setIdAslRiferimento(rs.getInt("asl"));
		    	thisAnimale.setIdComuneProprietario(rs.getInt("comune_proprietario"));
		    	thisAnimale.setIdComuneDetentore(rs.getInt("comune_colonia"));
		    	thisAnimale.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
		    	thisAnimale.setIdComuneCattura(rs.getInt("comune_cattura"));
	
		    	
		    	if (rs.getString("tipologia").equals("Catturato")){
		    		//Se era catturato al momento dell'inserimento nella pratica
		    		thisAnimale.setFlagCattura(true);
		    		}

		    	  thisAnimale.setContributoPagato(db, idPraticaContributi);
		    	  this.add(thisAnimale);
		    }
		 
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
	}catch (Exception e) {
	e.printStackTrace();
	}
		 //   buildResources(db);
		  }
	  public ResultSet queryListPagati(Connection db, PreparedStatement pst) throws SQLException {
		  
		    ResultSet rs = null;
		    int items = -1;
		    int i = 0;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records

		    sqlCount.append(
		    		"select * from contributi_lista_univocita where id_richiesta_contributi = ?"  );
		 

		     
		   
		    
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString());
		     
		      
		    pst.setInt(++i, idPraticaContributi);
//		    pst.setInt(++i, idAsl);
		      
		      
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		    }

/*		      //Determine the offset, based on the filter, for the first record to show
		      if (!pagedListInfo.getCurrentLetter().equals("")) {
		        pst = db.prepareStatement(
		            sqlCount.toString() +
		            sqlFilter.toString() +
		            "AND " + DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
		        items = prepareFilter(pst);
		        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
		        rs = pst.executeQuery();
		        if (rs.next()) {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }

		      //Determine column to sort by
		      pagedListInfo.setColumnToSortBy("a.nome");
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		            
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		    } else {
		      sqlOrder.append("ORDER BY a.nome ");
		    }*/

		    //Need to build a base SQL statement for returning records
//		    if (pagedListInfo != null) {
//		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
//		    } else {
//		      sqlSelect.append("SELECT ");
//		    }
		     
		    
		     sqlSelect.append( "select * from contributi_lista_univocita where id_richiesta_contributi = 151");
		
	//	ResultSet res = stat.executeQuery();
		
		    	 	
		    	 	
		  
		      
		  
		    pst = db.prepareStatement(
		        sqlSelect.toString());

//		    pst.setInt(++i, idPraticaContributi);
//		    pst.setInt(++i, idAsl);
		   
		    
		    
		   
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    
		    
		    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    
		  
		    return rs;
}
	  
	  //Flusso 251
	  public ResultSet queryListNonPagati(Connection db, PreparedStatement pst, Integer idComune) throws SQLException {
		  
		    ResultSet rs = null;
		    int items = -1;
		    int i = 0;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records

		    sqlCount.append(
		    		"Select count(distinct e.* ) from estrazione_animali_contributi e " +
					"where id_progetto_di_sterilizzazione_richiesto = ?  and " +
							"(asl = ? )"  );
		 
		    //Flusso 251
		    if(idComune!=null && idComune>0)
		    {
		    	 sqlCount.append(" and comune = ? ");
		    }
		    
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString());
		     
		      
		    pst.setInt(++i, idPraticaContributi);
		    pst.setInt(++i, idAsl);
		    
		    //Flusso 251
		    if(idComune!=null && idComune>0)
		    {
		    	pst.setInt(++i, idComune);
		    }
		    
		      
		      
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		    }

/*		      //Determine the offset, based on the filter, for the first record to show
		      if (!pagedListInfo.getCurrentLetter().equals("")) {
		        pst = db.prepareStatement(
		            sqlCount.toString() +
		            sqlFilter.toString() +
		            "AND " + DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
		        items = prepareFilter(pst);
		        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
		        rs = pst.executeQuery();
		        if (rs.next()) {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }

		      //Determine column to sort by
		      pagedListInfo.setColumnToSortBy("a.nome");
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		            
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		    } else {
		      sqlOrder.append("ORDER BY a.nome ");
		    }*/

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		     
		    
		    sqlSelect.append(" *	from estrazione_animali_contributi e " +
		    		//"left join animale on (e.id_animale = animale.id) left join opu_operatori_denormalizzati o on " +
		    	//	"(animale.id_proprietario = o.id_rel_stab_lp) " +
		    	//	"left join opu_operatori_denormalizzati d on " +
		    	//	"(animale.id_detentore = o.id_rel_stab_lp)" +
					"where id_progetto_di_sterilizzazione_richiesto = ?  and " +
							"(asl = ? )");
		    	 	
		    //Flusso 251
		    if(idComune!=null && idComune>0)
		    {
		    	sqlSelect.append(" and comune = ? ");
		    }	 	
		  
		      
		  
		    pst = db.prepareStatement(
		        sqlSelect.toString());

		    pst.setInt(++i, idPraticaContributi);
		    pst.setInt(++i, idAsl);
		   
		    //Flusso 251
		    if(idComune!=null && idComune>0)
		    {
		    	pst.setInt(++i, idComune);
		    }
		    
		    
		   
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    
		    
		    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    
		  
		    return rs;
}
	  
	  
	  
	  public ResultSet queryListInviati(Connection db, PreparedStatement pst) throws SQLException {
		  
		    ResultSet rs = null;
		    int items = -1;
		    int i = 0;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records

		    sqlCount.append("Select distinct id_animale, microchip ,proprietario,clc.tipologia,clc.comune_cattura, data_sterilizzazione,numero_protocollo,asl " +
					", l.description,comune_proprietario,tipo_animale as id_specie,comune_colonia, id_detentore, op.ragione_sociale as nomeproprietario, " +
					"op_d.ragione_sociale as nomedetentore from  contributi_lista_animali clc " +
					"left join lookup_asl_rif l on l.code= clc.asl " +
					"left join opu_operatori_denormalizzati op on (clc.proprietario = op.id_rel_stab_lp)" +
					"left join opu_operatori_denormalizzati op_d on (clc.id_detentore = op_d.id_rel_stab_lp)"+
					"  where clc.id_richiesta_contributi = ? order by tipo_animale,microchip " );
		 

		     
		   
		    
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString());
		     
		      
		    pst.setInt(++i, idPraticaContributi);
		  
		      
		      
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		    }

/*		      //Determine the offset, based on the filter, for the first record to show
		      if (!pagedListInfo.getCurrentLetter().equals("")) {
		        pst = db.prepareStatement(
		            sqlCount.toString() +
		            sqlFilter.toString() +
		            "AND " + DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
		        items = prepareFilter(pst);
		        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
		        rs = pst.executeQuery();
		        if (rs.next()) {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }

		      //Determine column to sort by
		      pagedListInfo.setColumnToSortBy("a.nome");
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		            
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		    } else {
		      sqlOrder.append("ORDER BY a.nome ");
		    }*/

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		     
		    
//		    sqlSelect.append("distinct id_animale, microchip ,proprietario,clc.tipologia,clc.comune_cattura, data_sterilizzazione,numero_protocollo,asl " +
//					", l.description,comune_proprietario,tipo_animale as id_specie,comune_colonia, id_detentore, op.ragione_sociale as nomeproprietario, op_d.ragione_sociale as nomedetentore from  " +
//					"contributi_lista_animali clc " +
//					"left join lookup_asl_rif l on l.code= clc.asl " +
//					"left join opu_operatori_denormalizzati op on (clc.proprietario = op.id_rel_stab_lp)" +
//					"left join opu_operatori_denormalizzati op_d on (clc.id_detentore = op.id_rel_stab_lp)" +
//					" where clc.id_richiesta_contributi = ? order by tipo_animale,microchip ");
		    	 	
		    sqlSelect.append("distinct id_animale, microchip ,proprietario,clc.tipologia,clc.comune_cattura, " +
		    		"data_sterilizzazione,numero_protocollo,asl " +
					", l.description,comune_proprietario,tipo_animale as id_specie," +
					"comune_colonia, id_detentore " +
				//	", op.ragione_sociale as nomeproprietario, " +
					//"op_d.ragione_sociale as nomedetentore "+
					"from  " +
					"contributi_lista_animali clc " +
					"left join lookup_asl_rif l on l.code= clc.asl " +
					//"left join opu_operatori_denormalizzati op on (clc.proprietario = op.id_rel_stab_lp)" +
				//	"left join opu_operatori_denormalizzati op_d on (clc.id_detentore = op.id_rel_stab_lp)" +
					" where clc.id_richiesta_contributi = ? order by tipo_animale,microchip ");
		    	 	
		  
		      
		  
		    pst = db.prepareStatement(
		        sqlSelect.toString());

		    pst.setInt(++i, idPraticaContributi);
		  //  pst.setInt(++i, idAsl);
		    
		    
		   
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    
		    
		    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    
		  
		    return rs;
}
	  
		
	  public void buildListCaniPagati(Connection db) throws SQLException, IndirizzoNotFoundException {
		    PreparedStatement pst = null;

		    ResultSet rs = queryListPagati(db, pst);
		    while (rs.next()) {
		    	
		    	
		    	Animale thisAnimale = new Animale();
		    	thisAnimale.setIdSpecie(rs.getInt("tipo_animale"));
//		    	
//		    	if (rs.getInt("id_specie") == Cane.idSpecie){
//		    		 thisAnimale = new Cane(db, rs.getInt("id_animale"));
//		    	}else if(rs.getInt("id_specie") == Gatto.idSpecie){
//		    		 thisAnimale = new Gatto(db, rs.getInt("id_animale"));
//		    	}
//		    	 
		    	thisAnimale.setIdAnimale(rs.getInt("id_animale"));
		    	thisAnimale.setMicrochip(rs.getString("microchip"));
		    //	thisAnimale.setNomeCognomeProprietario(rs.getString("proprietario"));
		    //	thisAnimale.setNomeCognomeDetentore(rs.getString("detentore"));
		    	//recupero i dati del cane al momento dell'inserimento nella pratica
		    	//thisAnimale.setIdProprietario(rs.getInt("id_proprietario"));
		    	thisAnimale.setIdAslRiferimento(rs.getInt("asl"));
		    	thisAnimale.setIdComuneProprietario(rs.getInt("comune_proprietario"));
		    	thisAnimale.setIdComuneDetentore(rs.getInt("comune_colonia"));
		    	thisAnimale.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
		    	thisAnimale.setIdComuneCattura(rs.getInt("comune_cattura"));
		    	
		    	if (rs.getString("tipologia") == "Catturato"){
		    		//Se era catturato al momento dell'inserimento nella pratica
		    		thisAnimale.setFlagCattura(true);
		    		}
		      
			      //Costruisco proprietario e detentore
//			      Operatore proprietario = null;
//			      Operatore detentore = null;
//			      
//			      if (rs.getInt("id_proprietario") > -1)
//			      {
//			    	   proprietario = new Operatore();
//			    	   proprietario.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_proprietario"));
//			      }
//			      
//			      if (rs.getInt("id_detentore") > -1)
//			      {
//			    	   detentore = new Operatore();
//			    	   detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
//			      }
			      
			     
			     // thisAnimale.setProprietario(proprietario);
			    //  thisAnimale.setDetentore(detentore);
			      
			      this.add(thisAnimale);
			      
/*			      
			      if (thisAnimale.getIdSpecie() == Cane.idSpecie){
			    	  Cane a = new Cane(db, thisAnimale.getIdAnimale());
			    	   detentore = new Operatore();
			    	  detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
			    	  a.setDetentore(detentore);
			    	  a.setIdDetentore(rs.getInt("id_detentore"));
			    	  this.add(a);
			      }		
			      
			      if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
			    	  Gatto a = new Gatto(db, thisAnimale.getIdAnimale());
			    	   detentore = new Operatore();
			    	  detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
			    	  a.setDetentore(detentore);
			    	  a.setIdDetentore(rs.getInt("id_detentore"));
			    	  this.add(a);
			      }
			      */
			      
			      //thisAnimale.setDetentore(detentore);
			      //  this.add(thisAnimale);
			     
			    }
		 
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		 //   buildResources(db);
		  }
	  
}
