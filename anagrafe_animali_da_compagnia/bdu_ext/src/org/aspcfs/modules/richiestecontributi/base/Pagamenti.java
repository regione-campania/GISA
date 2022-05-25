/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Stabilimento;


public class Pagamenti {

	
	public boolean saveRichiestaOK (Connection db, List<Animale> listaCani, int idDettaglio, int userId , int n_protocollo)throws SQLException, ParseException {
		
		
		String sqlListaUnivocita="";
		String sqlUpdateRichiesta="";
		String sqlChiusuraPratica="";
		//try {
			Date d=new Date();			    
		    Timestamp dataRevContributo = new Timestamp(d.getTime());
		    
			sqlListaUnivocita="INSERT INTO contributi_lista_univocita( id_animale,microchip, id_richiesta_contributi," +
					"data_approvazione, tipologia, comune_cattura,comune_proprietario, asl, data_sterilizzazione, comune_colonia,tipo_animale, id_richiesta_contributi_pratica_separata  ) "+
			"	   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ?, ? )";								
			PreparedStatement psListaCani = db.prepareStatement(sqlListaUnivocita.toString());
							
			Animale c;
			for (int i=0;i<listaCani.size();i++) {
			c=listaCani.get(i);
			
			psListaCani.setInt(1, c.getIdAnimale());
			
			psListaCani.setString(2, c.getMicrochip());
			
			psListaCani.setInt(3, idDettaglio);
			
			psListaCani.setTimestamp(4,dataRevContributo );
			
			if (c.isFlagCattura()){
				psListaCani.setString(5, "Catturato");
			}else{
				psListaCani.setString(5, "Padronale");
			}
			
			if (c.getIdSpecie() == Cane.idSpecie){
				Cane thisCane = new Cane (db, c.getIdAnimale());
				psListaCani.setInt(6,thisCane.getIdComuneCattura());
			}
			else
				psListaCani.setInt(6,-1);
			
			
			//recupero comune proprietario
		//	Stabilimento stab = (Stabilimento) c.getProprietario().getListaStabilimenti().get(0);
		//	LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		//	int id_comune_proprietario = stab.getSedeOperativa().getComune();
			int id_comune_proprietario = c.getIdComuneProprietario();
			if ((id_comune_proprietario > -1)){
				psListaCani.setInt(7,id_comune_proprietario);
			}
			else
			{
				psListaCani.setInt(7,-1);	
			}
			
			
			psListaCani.setInt(8,c.getIdAslRiferimento());
			
			psListaCani.setTimestamp(9,c.getDataSterilizzazione());
			
			
			//TODO AGGIUSTARE COLONIA
			
			int idComuneColonia = -1;
			
			if (c.getIdSpecie() == Gatto.idSpecie)
				idComuneColonia = c.getIdComuneDetentore();
			
			psListaCani.setInt(10,idComuneColonia);
			
			psListaCani.setInt(11, c.getIdSpecie());
			
			psListaCani.setInt(12, -1);
			
			psListaCani.executeUpdate();
			
			}
						
			
						
			/* UPDATE TABELLA CONTRIBUTI*/
			
			//Date d=new Date();			    
		    //Timestamp dataRevContributo = new Timestamp(d.getTime());
		    sqlUpdateRichiesta="UPDATE contributi_richieste SET approvato_da='" + userId + "', data_approvazione='" + dataRevContributo + "' where id='" + idDettaglio + "'";
			
		    PreparedStatement psRevContributi = db.prepareStatement(sqlUpdateRichiesta.toString());
			
		    psRevContributi.execute();
		   

		    sqlChiusuraPratica="UPDATE pratiche_contributi set data_chiusura_pratica = now()  where id = ? ";
		    
		    PreparedStatement psChiusuraPratica = db.prepareStatement(sqlChiusuraPratica.toString());
		    psChiusuraPratica.setInt(1,n_protocollo );
		    psChiusuraPratica.execute();

						    
			
//		} catch (SQLException e) {
//			db.rollback();
//			//throw new SQLException(e.getMessage());
//			return false;
//		} finally {
//			//db.setAutoCommit(true);
//		}

	return true;
	}
	
	
	
	public boolean saveRichiestaFailed (Connection db, int idDettaglio, int userId, int num_protocollo)throws SQLException, ParseException {
		
		
		
		String sqlUpdateRichiesta="";
		String sqlUpdateChiusuraPratica="";
		
		try {		
		
			PreparedStatement psChiusuraPratica = null;
			
			/* UPDATE TABELLA CONTRIBUTI*/
			
			Date d=new Date();			    
		    Timestamp dataRevContributo = new Timestamp(d.getTime());
		    sqlUpdateRichiesta="UPDATE contributi_richieste SET respinto_da='" + userId + "', data_respinta='" + dataRevContributo + "' where id='" + idDettaglio + "'";
		    
		    PreparedStatement psRevContributi = db.prepareStatement(sqlUpdateRichiesta.toString());
		    psRevContributi.execute();
		    
		    sqlUpdateRichiesta="UPDATE pratica_contributi SET data_chiusura = "+dataRevContributo + "where id = "+num_protocollo;
		    	    
		    psChiusuraPratica = db.prepareStatement(sqlUpdateChiusuraPratica);			
		    psChiusuraPratica.execute();
			

		} catch (SQLException e) {
			db.rollback();
			//throw new SQLException(e.getMessage());
			return false;
		} finally {
		}

	return true;
	}
	
public boolean saveRespingiInMOdifica (Connection db, int idDettaglio, int userId)throws SQLException, ParseException {
		
		
		
		String sqlUpdateRichiesta="";
		try {		
		
			PreparedStatement psChiusuraPratica = null;
			
			/* UPDATE TABELLA CONTRIBUTI*/
			
			Date d=new Date();			    
		    Timestamp dataRevContributo = new Timestamp(d.getTime());
		    sqlUpdateRichiesta="UPDATE contributi_richieste SET respinto_da='" + userId + "', in_modifica = true " + ", data_respinta='" + dataRevContributo + "' where id='" + idDettaglio + "'";
		    
		    PreparedStatement psRevContributi = db.prepareStatement(sqlUpdateRichiesta.toString());
		    psRevContributi.execute();
		    

		} catch (SQLException e) {
			db.rollback();
			//throw new SQLException(e.getMessage());
			return false;
		} finally {
		}

	return true;
	}
	
	
	
}
