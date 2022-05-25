/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.SysoLogger;

public class GestoreComunicazioniVam {

	public GestoreComunicazioniVam() {

	}





public void aggiornaIdStabilimentoGisaBdu(int idStabGisa,int idRelStablpBdu) throws SQLException {}
	
	
public static String cessazioneAutomaticaVam(int idCanileBdu, Timestamp dataCessazione, String noteCessazione) throws SQLException {
	String output = "";
	Connection connectionVam =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.cessazione_clinica(-1,?,?,?);";
		
		try {
			connectionVam =GestoreConnessioni.getConnectionVam();
			pst=connectionVam.prepareStatement(select);
			int i = 0;
			pst.setInt(++i, idCanileBdu);
			pst.setTimestamp(++i, dataCessazione);
			pst.setString(++i, noteCessazione);
			System.out.println("CESSAZIONE AUTOMATICA SU VAM ---> "+pst.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				output = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionVam.close();
		}
		return output;	
	}





public String inserisciNuovaClinicaVam(int stabId, String nome, String nomeBreve, String asl, String comune,
		String indirizzo, String email, String telefono, String noteHd) throws SQLException {
	String output = "";
	Connection connectionVam =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.inserimento_clinica(?,?,?, ?, ?, ?, ?, ?, ?);";
		
		try {
			connectionVam =GestoreConnessioni.getConnectionVam();
			pst=connectionVam.prepareStatement(select);
			int i = 0;
			pst.setInt(++i, stabId);
			pst.setString(++i, nome);
			pst.setString(++i, nomeBreve);
			pst.setString(++i, asl);
			pst.setString(++i, comune);
			pst.setString(++i, indirizzo);
			pst.setString(++i, email);
			pst.setString(++i, telefono);
			pst.setString(++i, noteHd);
			System.out.println("INSERIMENTO SU VAM ---> "+pst.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				output = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionVam.close();
		}
		return output;	
	}





public void variazioneOperatoreVamSciaNoCessazione(int idStabilimentoTrovato, Stabilimento richiesta, int idUtente) throws SQLException {

	String output = "";
	Connection connectionVam =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.suap_variazione_titolarita(?,?,?);";
		
		try {
			connectionVam =GestoreConnessioni.getConnectionVam();
			pst=connectionVam.prepareStatement(select);
			int i = 0;
			pst.setString(++i, richiesta.getOperatore().getRagioneSociale());
			pst.setInt(++i, idUtente);
			pst.setInt(++i, idStabilimentoTrovato);
			System.out.println("VARIAZIONE SU VAM ---> "+pst.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				output = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionVam.close();
		}
	
}

}
