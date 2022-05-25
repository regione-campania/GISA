/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.io.Serializable;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.utils.DatabaseUtils;

/**
 * @author Io
 *
 */
public class RichiestaContributi implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5936083472641345775L;
	private int id;	
	private int inserito_da;
	private int asl;	
	private String descrizioneAsl;
	private java.sql.Timestamp data_richiesta;
	private java.sql.Timestamp data_from; 	
	private java.sql.Timestamp data_to;	
	private int approvato_da;
	private java.sql.Timestamp data_approvazione;
	private int respinto_da;
	private java.sql.Timestamp data_respinta;
	private String tipo_richiesta;
	private Integer protocollo= null;
	private int numero_decreto =-1;
	private Timestamp data_decreto;
	
	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}
	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public java.sql.Timestamp getData_from() {
		return data_from;
	}
	public void setData_from(java.sql.Timestamp data_from) {
		this.data_from = data_from;
	}
	public java.sql.Timestamp getData_to() {
		return data_to;
	}
	public void setData_to(java.sql.Timestamp data_to) {
		this.data_to = data_to;
	}
	public int getInserito_da() {
		return inserito_da;
	}
	public void setInserito_da(int inserito_da) {
		this.inserito_da = inserito_da;
	}
	public int getAsl() {
		return asl;
	}
	public void setAsl(int asl) {
		this.asl = asl;
	}
	public java.sql.Timestamp getData_richiesta() {
		return data_richiesta;
	}
	public void setData_richiesta(java.sql.Timestamp data_richiesta) {
		this.data_richiesta = data_richiesta;
	}
	
	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}
	
	public String getTipo_richiesta(){
		return tipo_richiesta;
	}
	
	public int getApprovato_da() {
		return approvato_da;
	}
	public void setApprovato_da(int approvato_da) {
		this.approvato_da = approvato_da;
	}
	public java.sql.Timestamp getData_approvazione() {
		return data_approvazione;
	}
	public void setData_approvazione(java.sql.Timestamp data_approvazione) {
		this.data_approvazione = data_approvazione;
	}
	public int getRespinto_da() {
		return respinto_da;
	}
	public void setRespinto_da(int respinto_da) {
		this.respinto_da = respinto_da;
	}
	public java.sql.Timestamp getData_respinta() {
		return data_respinta;
	}
	public void setData_respinta(java.sql.Timestamp data_respinta) {
		this.data_respinta = data_respinta;
	}
	
	public boolean insert (Connection db)throws SQLException {
		String sql="";
		try {
		
		
			sql="INSERT INTO contributi_richieste(inserito_da, asl, data_from, data_to, approvato_da, data_approvazione, respinto_da, data_respinta,tipologia)" +
			"	   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
									
			PreparedStatement ps = db.prepareStatement(sql.toString());
						
			
			ps.setInt(1, inserito_da);
			ps.setInt(2, asl);				
			ps.setTimestamp(3, data_from);
			ps.setTimestamp(4, data_to);
			ps.setInt(5, approvato_da);
			ps.setTimestamp(6, data_approvazione);
			ps.setInt(7, respinto_da);
			ps.setTimestamp(8, data_respinta);
			ps.setString(9,tipo_richiesta);
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
		}

	return true;
	}
	
	
	private int getIdRichiesta(Connection db)throws SQLException {
    
		int id=-1;
		
		//Ottengo il valore dell'id 
        StringBuffer sql2= new StringBuffer();
        sql2.append("SELECT nextval ('contributi_richieste_id_seq')");
        PreparedStatement pst2 = db.prepareStatement(sql2.toString());
        ResultSet rs = pst2.executeQuery();
        while (rs.next()) {
        	id = rs.getInt("nextval");
        }
        pst2.execute();
		pst2.close();
		
		return id;
    }
	
	public boolean saveRichiesta (Connection db, RichiestaContributi rc, List<Animale> listaCani)throws SQLException {
		boolean ok=true;			
		String sql="";
		String sqlListaCani="";
		try {
			if (listaCani.size()>0){
			
			sql="INSERT INTO contributi_richieste( inserito_da, asl, approvato_da, data_approvazione, respinto_da, data_respinta, numero_protocollo)" +
			"	   VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = db.prepareStatement(sql.toString());
			
			ps.setInt(1, rc.getInserito_da());
			ps.setInt(2, rc.getAsl());				
			ps.setInt(3, rc.getApprovato_da());
			ps.setTimestamp(4, rc.getData_approvazione());
			ps.setInt(5, rc.getRespinto_da());
			ps.setTimestamp(6, rc.getData_respinta());
			ps.setInt(7, rc.getProtocollo());
		
			ps.executeUpdate();
			
			id = DatabaseUtils.getCurrVal(db, "contributi_richieste_id_seq", id);
							
			ps.close();
					
			
			
			sqlListaCani="INSERT INTO contributi_lista_animali" +
					"(id_animale, microchip, id_richiesta_contributi,tipologia, comune_cattura,comune_proprietario,numero_protocollo, " +
					"data_sterilizzazione, asl,tipo_animale,proprietario,comune_colonia, id_detentore) "+
			"	   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//	PreparedStatement psListaCani = db.prepareStatement(sqlListaCani.toString());
			PreparedStatement psListaCani = null;
			
			Animale c = null;
			for (int i=0;i<listaCani.size();i++) {
			psListaCani = db.prepareStatement(sqlListaCani.toString());
			c=listaCani.get(i);
			
			System.out.println("Microchip " + c.getMicrochip());
		
			psListaCani.setInt(1, c.getIdAnimale());
			psListaCani.setString(2, c.getMicrochip());
			psListaCani.setInt(3, id);
			
			if (c.isFlagCattura()){
				psListaCani.setString(4, "Catturato");
			}else{
				psListaCani.setString(4, "Padronale");
			}
			
			if (c.getIdSpecie() == Cane.idSpecie){
				Cane thisCane = new Cane (db, c.getIdAnimale());
				psListaCani.setInt(5, thisCane.getIdComuneCattura());
			}else{
				psListaCani.setInt(5, -1);
			}

			
			//recupero comune proprietario
			//Stabilimento stab = (Stabilimento) c.getProprietario().getListaStabilimenti().get(0);
			//LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			int id_comune_proprietario = c.getIdComuneProprietario();
			if ((id_comune_proprietario > -1)){
				psListaCani.setInt(6,id_comune_proprietario);
			}
			else
			{
				psListaCani.setInt(6,-1);	
			}

			psListaCani.setInt(7,rc.getProtocollo() );
			psListaCani.setTimestamp(8, c.getDataSterilizzazione());
			psListaCani.setInt(9, c.getIdAslRiferimento());
			psListaCani.setInt(10, c.getIdSpecie());
			psListaCani.setInt(11, c.getIdProprietario());
			
			//TODO COLONIA
			int id_comune_colonia = -1;
			LineaProduttiva lp = null;
			if (c.getIdSpecie() == Gatto.idSpecie){
				Operatore detentore = new Operatore();
				/*detentore.queryRecordOperatorebyIdLineaProduttiva(db, c.getIdDetentore());
				lp = new LineaProduttiva();
				if (detentore != null && detentore.getIdOperatore() >0){
					Stabilimento stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
					lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
					if (lp.getIdAttivita() == 7){
						//colonia
						id_comune_colonia	= stab.getSedeOperativa().getComune();
					}
				}*/
				
				id_comune_colonia = detentore.getComuneColonia(db, c.getIdDetentore());
//				lp = new LineaProduttiva();
//				Gatto gatto = new Gatto(db, c.getIdAnimale());
//				//recupero detentore per vedere se è colonia
//				if (gatto.getDetentore() != null && gatto.getDetentore().getIdOperatore() > 0){
//				Stabilimento stab = (Stabilimento) gatto.getDetentore().getListaStabilimenti().get(0);
//				
//				lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
//				if (lp.getIdAttivita() == 7){
//					//colonia
//					id_comune_colonia	= stab.getSedeOperativa().getComune();
//				}
//				
//				}
//				
			}else if (c.getIdSpecie() == Cane.idSpecie){
				lp = new LineaProduttiva();
				//Cane cane = new Cane(db, c.getIdAnimale());
//				if (cane.getDetentore() != null && cane.getDetentore().getIdOperatore() > 0){
//					Stabilimento stab = (Stabilimento) cane.getDetentore().getListaStabilimenti().get(0);
//					
//					lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
//				}
			
			
			}
			psListaCani.setInt(12, id_comune_colonia);
			psListaCani.setInt(13, c.getIdDetentore());
/*			if (c.getComune_colonia()!=null){
				psListaCani.setString(12, c.getComune_colonia());
			}
			else{
				psListaCani.setString(12,"");
				
			}*/
		
			psListaCani.executeUpdate();
			
			}
			ok=true;
			}		
			else ok= false;
		} catch (Exception e) {
			db.rollback();
			
			throw new SQLException(e.getMessage());
		} finally {
		}

	return ok;
	}
	public void setProtocollo(Integer protocollo) {
		this.protocollo = protocollo;
	}
	public Integer getProtocollo() {
		return protocollo;
	}
	
	public static String formatData(Timestamp timestamp)
	{
		return (timestamp == null) ? ("") : (sdf.format( timestamp ));
	}

	static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );

	public void setNumeroDecreto(int i) {
		// TODO Auto-generated method stub
		this.numero_decreto= i;
	}
	 
	public int getNumeroDecreto() {
		return this.numero_decreto;
	}
	public void setDataDecreto(int int1) {
		// TODO Auto-generated method stub
		
	}
	
	public java.sql.Timestamp getData_Decreto() {
		return data_decreto;
	}
	public void setDataDecreto(java.sql.Timestamp data_decreto) {
		this.data_decreto = data_decreto;
	}

}
