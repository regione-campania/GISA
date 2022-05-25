/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.checklist.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Organization {
  
	private static String select = 	" Select org_id , account_size , " +
									" categoria_rischio, categoria_precedente, " +
									" name , tipologia,site_id from organization where org_id = ? ";
	
	private int 		org_id					;
	private String 		ragioneSociale			;
	private int 		accountSize				;
	private int 		categoriaRischio		;
	private int 		categoriaPrecedente		;
	private int 		tipologia 				;
	private int 		idAsl					;
	public static final int TIPO_IMPRESE 			= 1 		;
	public static final int TIPO_ALLEVAMENTI 		= 2 		;
	public static final int TIPO_STABILIMENTI 		= 3 		;
	public static final int TIPO_OPERATORI_FR 		= 22 		;
	public static final int TIPO_PERATORI_PRIV		= 13 		;
	public static final int TIPO_FARMACOSORVEGLIANZA= 151 		;
	public static final int TIPO_ABUSIVI			= 4 		;
	public static final int TIPO_SOA				= 97 		;
	public static final int TIPO_OSM				= 800 		;
	public static final int TIPO_OSM_REG				= 801 		;
	public static final int TIPO_TRASPORTI			= 9 		;
	public static final int TIPO_CANILI			= 10 		;
	public static final int TIPO_CANIPADRONALI			= 255 		;
	public static final int TIPO_PUNTI_DI_SBARCO			= 5 		;
	public static final int TIPO_IMBARCAZIONE			= 17 		;
	
	public Organization(int idStabilimento)
	{
		this.org_id = idStabilimento;
	}
	private String url ;
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public int getAccountSize() {
		return accountSize;
	}
	public void setAccountSize(int accountSize) {
		this.accountSize = accountSize;
	}
	public int getCategoriaRischio() {
		return categoriaRischio;
	}
	public void setCategoria_rischio(int categoria_rischio) {
		this.categoriaRischio = categoria_rischio;
	}
	public int getCategoriaPrecedente() {
		return categoriaPrecedente;
	}
	public void setCategoria_precedente(int categoria_precedente) {
		this.categoriaPrecedente = categoria_precedente;
	}
	public int getTipologia() {
		return tipologia;
	}
	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public Organization(Connection db, int orgId)
	{
		try
		{
			PreparedStatement pst = db.prepareStatement(select)	;
			pst.setInt(1, orgId);
			ResultSet rs = pst.executeQuery()	;
			if( rs.next() )
			{
				setCampi ( rs ) 				;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void setCampi(ResultSet rs) throws SQLException
	{
		
			org_id			 		= rs.getInt( "org_id" )					;
			ragioneSociale 			= rs.getString( "name" )				;
			categoriaPrecedente 	= rs.getInt( "categoria_precedente" )	;
			categoriaRischio 		= rs.getInt( "categoria_rischio" )		;
			tipologia 				= rs.getInt( "tipologia" )				;
			accountSize				= rs.getInt( "account_size" ) 			;
			idAsl					= rs.getInt( "site_id" ) 				;
					
	}
	
	public void updateCategoriaRischio(Connection db, int categoriaR,Timestamp prossimoControllo, int orgid,int idCu) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_rischio = ? , prossimo_controllo = ?  where org_id = ?");
		pst.setInt(1, categoriaR);
		pst.setTimestamp(2, prossimoControllo);
		pst.setInt(3, orgid);
		pst.execute();
		
	}
	
	
public void updateCategoriaRischioOpu(Connection db, int categoriaR,Timestamp prossimoControllo, int orgid,int idCu) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE opu_stabilimento set categoria_rischio = ? , data_prossimo_controllo = ? where id = ?");
		pst.setInt(1, categoriaR);
		pst.setTimestamp(2, prossimoControllo);
		pst.setInt(3, orgid);
		pst.execute();
		
	}


public void updateCategoriaRischioApiari(Connection db, int categoriaR,Timestamp prossimoControllo, int orgid,int idCu) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("UPDATE apicoltura_apiari set categoria_rischio = ? , data_prossimo_controllo = ? where id = ?");
	pst.setInt(1, categoriaR);
	pst.setTimestamp(2, prossimoControllo);
	pst.setInt(3, orgid);
	pst.execute();
	
}

public void updateCategoriaRischioSuap(Connection db, int categoriaR,Timestamp prossimoControllo, int altid) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("UPDATE suap_ric_scia_stabilimento set categoria_rischio = ? , data_prossimo_controllo = ?  where alt_id = ?");
	pst.setInt(1, categoriaR);
	pst.setTimestamp(2, prossimoControllo);
	pst.setInt(3, altid);
	pst.execute();
	
}
	
public void updateCategoriaRischioOperatoreUnico(Connection db, int categoriaR,Timestamp prossimoControllo, int orgid) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE opu_stabilimento set categoria_rischio = ? , prossimo_controllo = ?  where id = ?");
		pst.setInt(1, categoriaR);
		pst.setTimestamp(2, prossimoControllo);
		pst.setInt(3, orgid);
		pst.execute();
		
	}

public void updateCategoriaRischioSintesis(Connection db, int categoriaR,Timestamp prossimoControllo, int altid, int idCu) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("UPDATE sintesis_stabilimento set categoria_rischio = ? , data_prossimo_controllo = ? where alt_id = ?");
	pst.setInt(1, categoriaR);
	pst.setTimestamp(2, prossimoControllo);
	pst.setInt(3, altid);
	pst.execute();
	
	
}
	

	public void updateCategoriaPrecedente(Connection db, int categoriaR, int orgid) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_precedente = ? where org_id = ?");
		pst.setInt(1, categoriaR);
		pst.setInt(2, orgid);
		pst.execute();
		
	}
	
	public void updateLivelloRischioSintesis(Connection db, int livello, int altid) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE sintesis_stabilimento set livello_rischio = ?  where alt_id = ?");
		pst.setInt(1, livello);
		pst.setInt(2, altid);
		pst.execute();
		
		
	}
	public void updateCategoriaRischioAnagrafica(Connection db, int categoriaR, Timestamp prossimoControllo, int altId) throws SQLException {
		
		PreparedStatement pst=db.prepareStatement("UPDATE opu_stabilimento set categoria_rischio = ? , data_prossimo_controllo = ?  where alt_id = ?");
		pst.setInt(1, categoriaR);
		pst.setTimestamp(2, prossimoControllo);
		pst.setInt(3, altId);
		pst.execute();
				
	}
	
	
	
}
