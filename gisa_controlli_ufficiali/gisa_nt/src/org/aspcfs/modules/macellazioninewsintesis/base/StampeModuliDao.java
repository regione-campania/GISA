/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class StampeModuliDao {
	
	private static StampeModuliDao instance;
	private StampeModuliDao(){}
	
	public static StampeModuliDao getInstance(){
		if(instance == null){
			instance = new StampeModuliDao();
		}
		return instance;
	}
	
	public void select(Connection db, StampeModuli stampeModuli, ChiaveModuliMacelli chiave) throws Exception{
			
			String select = "";
			PreparedStatement stat = null;
			ResultSet rs = null;
			
			switch (chiave) {
			case TIPO_DATA_MACELLO:
				
				select = "select * " +
						 "from stampe_moduli_macelli " +
						 "where tipo_modulo = ? and data_modulo = ? and id_macello = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, stampeModuli.getTipoModulo() );
				stat.setTimestamp( 2, stampeModuli.getDataModulo() );
				stat.setInt( 3, stampeModuli.getIdMacello() );
				
				break;
			case TIPO_DATA_MACELLO_MATRICOLA:
				
				select = "select * " +
						 "from stampe_moduli_macelli " +
						 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and matricola_capo = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, stampeModuli.getTipoModulo() );
				stat.setTimestamp( 2, stampeModuli.getDataModulo() );
				stat.setInt( 3, stampeModuli.getIdMacello() );
				stat.setString( 4, stampeModuli.getMatricolaCapo() );
				
				break;
			case TIPO_DATA_MACELLO_SPEDITORE:
				
				select = "select * " +
				 		 "from stampe_moduli_macelli " +
				 		 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and id_speditore = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, stampeModuli.getTipoModulo() );
				stat.setTimestamp( 2, stampeModuli.getDataModulo() );
				stat.setInt( 3, stampeModuli.getIdMacello() );
				stat.setInt( 4, stampeModuli.getIdSpeditore() );
				
				break;
			case TIPO_DATA_MACELLO_MALATTIA:
				
				select = "select * " +
				 		 "from stampe_moduli_macelli " +
				 		 "where tipo_modulo = ? and data_modulo = ? and id_macello = ? and malattia_capo = ?";
				stat = db.prepareStatement(select);
				stat.setInt( 1, stampeModuli.getTipoModulo() );
				stat.setTimestamp( 2, stampeModuli.getDataModulo() );
				stat.setInt( 3, stampeModuli.getIdMacello() );
				stat.setString( 4, stampeModuli.getMalattiaCapo() );
				
				break;
			default:
				throw new Exception("Chiave per moduli macelli non prevista");
			}
			
			
			rs = stat.executeQuery();
			if(rs.next()){
				stampeModuli.setId(rs.getInt("id"));
				stampeModuli.setTipoModulo(rs.getInt("tipo_modulo"));
				stampeModuli.setDataModulo(rs.getTimestamp("data_modulo"));
				stampeModuli.setAslMacello(rs.getInt("asl_macello"));
				stampeModuli.setIdMacello(rs.getInt("id_macello"));
				stampeModuli.setProgressivo(rs.getInt("progressivo"));
				stampeModuli.setOldProgressivo(rs.getInt("old_progressivo"));
				stampeModuli.setMatricolaCapo(rs.getString("matricola_capo"));
				stampeModuli.setIdSpeditore(rs.getInt("id_speditore"));
				stampeModuli.setMalattiaCapo(rs.getString("malattia_capo"));
				stampeModuli.setHashCode(rs.getInt("hash_code"));
			}
			
			
			
		}
		
		public void insert(Connection db, StampeModuli stampeModuli) throws Exception{
			
			int maxProgressivo = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			String select = "select coalesce( max( progressivo ), 0 ) " +
							"from stampe_moduli_macelli " +
							"where asl_macello = ? and date_part('year',data_modulo) = ?";
			PreparedStatement stat = db.prepareStatement(select);
			stat.setInt(1, stampeModuli.getAslMacello() );
			stat.setInt(2,Integer.parseInt( sdf.format(stampeModuli.getDataModulo()) ));
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				maxProgressivo = rs.getInt(1);
			}
			
			String insert = "insert into stampe_moduli_macelli"+
							"(tipo_modulo, data_modulo, asl_macello, id_macello, progressivo, matricola_capo, id_speditore, malattia_capo, old_progressivo, hash_code ) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stat = db.prepareStatement(insert);
			stat.setInt		 ( 1, stampeModuli.getTipoModulo() );
			stat.setTimestamp( 2, stampeModuli.getDataModulo() );
			stat.setInt		 ( 3, stampeModuli.getAslMacello() );
			stat.setInt		 ( 4, stampeModuli.getIdMacello() );
			stat.setInt		 ( 5, maxProgressivo + 1 );
			stat.setString	 ( 6, stampeModuli.getMatricolaCapo() );
			stat.setInt		 ( 7, stampeModuli.getIdSpeditore() );
			stat.setString	 ( 8, stampeModuli.getMalattiaCapo() );
			stat.setInt		 ( 9, stampeModuli.getOldProgressivo() );
			stat.setInt		 ( 10, stampeModuli.getHashCode() );
			stat.executeUpdate();
			
		}
		
		
		public void update(Connection db, StampeModuli stampeModuli) throws Exception{
			
			int maxProgressivo = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			String select = "select coalesce( max( progressivo ), 0 ) " +
							"from stampe_moduli_macelli " +
							"where asl_macello = ? and date_part('year',data_modulo) = ?";
			PreparedStatement stat = db.prepareStatement(select);
			stat.setInt(1, stampeModuli.getAslMacello() );
			stat.setInt(2,Integer.parseInt( sdf.format(stampeModuli.getDataModulo()) ));
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				maxProgressivo = rs.getInt(1);
			}
			
			String update = "update stampe_moduli_macelli "+
							"set tipo_modulo = ?, data_modulo = ?, asl_macello = ?, id_macello = ?, " +
							"progressivo = ?, matricola_capo = ?, id_speditore = ?, malattia_capo = ?, old_progressivo = ?, hash_code = ? " +
							"where id = ?";
			stat = db.prepareStatement(update);
			stat.setInt		 ( 1, stampeModuli.getTipoModulo() );
			stat.setTimestamp( 2, stampeModuli.getDataModulo() );
			stat.setInt		 ( 3, stampeModuli.getAslMacello() );
			stat.setInt		 ( 4, stampeModuli.getIdMacello() );
			stat.setInt		 ( 5, maxProgressivo + 1 );
			stat.setString	 ( 6, stampeModuli.getMatricolaCapo() );
			stat.setInt		 ( 7, stampeModuli.getIdSpeditore() );
			stat.setString	 ( 8, stampeModuli.getMalattiaCapo() );
			stat.setInt		 ( 9, stampeModuli.getOldProgressivo() );
			stat.setInt		 ( 10, stampeModuli.getHashCode() );
			stat.setInt		 ( 11, stampeModuli.getId() );
			stat.executeUpdate();
			
		}

}
