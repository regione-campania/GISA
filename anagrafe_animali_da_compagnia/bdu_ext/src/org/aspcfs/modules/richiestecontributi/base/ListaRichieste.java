/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspcfs.utils.DatabaseUtils;

public class ListaRichieste {
	
	private static Logger log = Logger.getLogger(org.aspcfs.modules.richiestecontributi.base.ListaRichieste.class);
	
	public List<RichiestaContributi> richiestaContributi;
	
	public ListaRichieste() {
		this.richiestaContributi=new ArrayList();
	}

	public List<RichiestaContributi> getRichiestaContributi() {
		return richiestaContributi;
	}

	public void setRichiestaContributi(List<RichiestaContributi> richiestaContributi) {
		this.richiestaContributi = richiestaContributi;
	}
	
	public List<RichiestaContributi> getListaRichiesteContributi(Connection db)throws SQLException {
		ResultSet rs;
		String sql="";
		try {			
			
//			sql= "select id AS id_richiesta, lar.description AS ASL, data_richiesta AS data_richiesta, tipologia, numero_protocollo,data_from,data_to from contributi_richieste cr LEFT JOIN lookup_asl_rif lar ON lar.code=cr.asl where approvato_da=0 AND respinto_da =0 ";
			sql="select cr.id AS id_richiesta, lar.description AS ASL, cr.data_richiesta AS data_richiesta, p.numero_decreto,p.data_decreto, cr.numero_protocollo from contributi_richieste cr left join pratiche_contributi p on cr.numero_protocollo= p.id LEFT JOIN lookup_asl_rif lar ON lar.code=cr.asl where cr.approvato_da=0 AND cr.respinto_da =0";
			PreparedStatement ps = db.prepareStatement(sql.toString());
					
			rs = DatabaseUtils.executeQuery(db, ps);
			
		
			RichiestaContributi rc;
			while (rs.next()) {
				rc=new RichiestaContributi();
							
				rc.setId(rs.getInt("id_richiesta"));
				rc.setDescrizioneAsl(rs.getString("ASL"));
				rc.setData_richiesta(rs.getTimestamp("data_richiesta"));
				//rc.setTipo_richiesta(rs.getString("tipologia"));
				rc.setNumeroDecreto(rs.getInt("numero_decreto"));
				rc.setDataDecreto(rs.getTimestamp("data_decreto"));
				rc.setProtocollo(rs.getInt("numero_protocollo"));
				//rc.setData_from(rs.getTimestamp("data_from"));
				//rc.setData_to(rs.getTimestamp("data_to"));
				richiestaContributi.add(rc);
				
					
			}

			rs.close();
			if (ps != null) {
				ps.close();
			}
			
			

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		this.setRichiestaContributi(richiestaContributi);
		return richiestaContributi;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<RichiestaContributi> getReportContributi(Connection db, boolean approvati)throws SQLException {
		ResultSet rs;
		String sql="";
		try {			
									
			if (approvati==true)
				sql= "select cr.id AS id_richiesta, lar.description AS ASL, data_richiesta AS data_richiesta,numero_protocollo,p.numero_decreto,p.data_decreto from " +
						"contributi_richieste cr left join pratiche_contributi p on p.id=cr.numero_protocollo LEFT JOIN lookup_asl_rif lar ON lar.code=cr.asl where approvato_da!=0 AND data_approvazione is not null and cr.id!=228 ";
			else
				sql= "select cr.id AS id_richiesta, lar.description AS ASL, data_richiesta AS data_richiesta,numero_protocollo,p.numero_decreto,p.data_decreto " +
						"from contributi_richieste cr left join pratiche_contributi p on p.id=cr.numero_protocollo LEFT JOIN lookup_asl_rif lar ON lar.code=cr.asl where respinto_da!=0 AND data_respinta is not null ";
			
			
			PreparedStatement ps = db.prepareStatement(sql.toString());
			
			rs = DatabaseUtils.executeQuery(db, ps);
						
			RichiestaContributi rc;
			while (rs.next()) {
				rc=new RichiestaContributi();
							
				rc.setId(rs.getInt(1));
				rc.setDescrizioneAsl(rs.getString(2));
				rc.setData_richiesta(rs.getTimestamp(3));
				//rc.setTipo_richiesta(rs.getString(4));	
				rc.setProtocollo(rs.getInt(4));
				rc.setNumeroDecreto(rs.getInt(5));
				rc.setDataDecreto(rs.getTimestamp(6));
				richiestaContributi.add(rc);
				
			}

			rs.close();
			if (ps != null) {
				ps.close();
			}
			
			

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		this.setRichiestaContributi(richiestaContributi);
		return richiestaContributi;
	}
	

	
	
	
	

}
