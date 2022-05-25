/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.directwebremoting.extend.LoginRequiredException;


public class ControlliUfficiali {
	
	
	
	/**
	 * 	METODO UTILIZZATO SUL SUBMIT DI AGGIUNTA DI UN CONTROLLO UFFICIALE
	 * 	SOLO SE DI TIPO ISPEZIONE ALLARME RAPIDO
	 * 	RITORNA 1 
	 * 	SE 	
	 * 		
	 * 		ESISTE GIA UN CONTROLLO DI IESPEZIONE SISTEMA ALLARME RAPIDO
	 * 		CON AZIONE ADOTTATA : ELEVATA SANZIONE E ARTICOLO QUELLO CHE SI STA CERCANDO DI INSERIRE
	 * 	
	 * ALTRIMENTI ZERO
	 * 	
	 * @return
	 */
	
	public static int controlloSistemaAllarmeRapido( int orgId , int articoloSelezionato )
	{
		Logger logger = Logger.getLogger("MainLogger");
		int trovato = 0 		;
		Connection db = null	;
		PreparedStatement pst = null	;
		ResultSet rs = null				;
		try
		{
			db = GestoreConnessioni.getConnection();
			if (db!=null)
			{
			String cerca =  "select ticketid " + 
							"from ticket t , tipocontrolloufficialeimprese tcu , " +
							"salvataggio_azioni_adottate aza,lookup_azioni_adottate laza " +
							"where 	" +
							"t.tipologia = 3 and " +
							"tcu.idcontrollo = t.ticketid and " +
							"tcu.tipoispezione =7 and " +
							"t.provvedimenti_prescrittivi=4	 and " +
							"aza.id_controllo_ufficiale = t.ticketid and " +
							"aza.id_azione_adottata = laza.code and " +
							"laza.code =  3	and	" +
							"t.org_id = ? and	" +
							"t.trashed_date is null and " +
							"t.articolo_azioni =? " ;

			pst = db.prepareStatement(cerca);
			pst.setInt(1, orgId);
			pst.setInt(2, articoloSelezionato);
			
			rs	= pst.executeQuery();
			
			int count = 0;
			while(rs.next())
			{
				count ++;
				int tt = rs.getInt("ticketid");
				
			}
			if(count>1)
			{
				trovato = 1 ;
			}
			}

		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch(SQLException e )
		{
			e.printStackTrace()	;
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return trovato 	;
	}
		
	

}
