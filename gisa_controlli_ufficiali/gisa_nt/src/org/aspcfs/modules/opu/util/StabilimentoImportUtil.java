/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Stabilimento;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StabilimentoImportUtil {


	public static void cancellaOrganization(Connection db, int orgId){
		System.out.println("Cancello logicamente la vecchia organization ("+orgId+")");
		String sql = "update organization set import_opu = true, trashed_date = now(), notes = ?  where org_id =? ;"
				+ " select * from refresh_anagrafica(?, 'org');";
		PreparedStatement pst2;
		try {
			pst2 = db.prepareStatement(sql);
			pst2.setString(1, "Operatore importato in data "+new Timestamp(System.currentTimeMillis()));
			pst2.setInt(2, orgId);
			pst2.setInt(3, orgId);
			pst2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void importaOperatoriMercatoIttico(Connection db, int orgId,int idStabilimento){

		String sql = "INSERT INTO operatori_associati_mercato_ittico(id_mercato_ittico, id_operatore, tipo_operatore, entered_by,contenitore_mercato_ittico) "
				+ "(select ?,id_operatore, tipo_operatore, entered_by,'opu' from operatori_associati_mercato_ittico where id_mercato_ittico=? and contenitore_mercato_ittico ilike 'organization');";
		PreparedStatement pst2;
		try {
			pst2 = db.prepareStatement(sql);
			pst2.setInt(1, idStabilimento);
			pst2.setInt(2, orgId);
			pst2.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void importaMacellazioni (Connection db, Organization OrgImport, Stabilimento newStabilimento) throws SQLException{
		System.out.println("Importo le macellazioni da"+OrgImport.getOrgId()+" a "+newStabilimento.getAltId());

		if (newStabilimento.getAltId()>0) {
		PreparedStatement pstCapi = db.prepareStatement("update m_capi set id_macello = ?, old_id_macello = ? where id_macello = ?");
		pstCapi.setInt(1, newStabilimento.getAltId());
		pstCapi.setInt(2, OrgImport.getOrgId());
		pstCapi.setInt(3, OrgImport.getOrgId());
		pstCapi.execute();
		
		PreparedStatement pstCapiSedute = db.prepareStatement("update m_capi_sedute set id_macello = ?, old_id_macello = ? where id_macello = ?");
		pstCapiSedute.setInt(1, newStabilimento.getAltId());
		pstCapiSedute.setInt(2, OrgImport.getOrgId());
		pstCapiSedute.setInt(3, OrgImport.getOrgId());
		pstCapiSedute.execute();
		
		PreparedStatement pstTamponi = db.prepareStatement("update m_vpm_tamponi set id_macello = ?, old_id_macello = ? where id_macello = ?");
		pstTamponi.setInt(1, newStabilimento.getAltId());
		pstTamponi.setInt(2, OrgImport.getOrgId());
		pstTamponi.setInt(3, OrgImport.getOrgId());
		pstTamponi.execute();
		
		PreparedStatement pstPartite = db.prepareStatement(" update m_partite set id_macello = ?, old_id_macello = ? where id_macello = ?");
		pstPartite.setInt(1, newStabilimento.getAltId());
		pstPartite.setInt(2, OrgImport.getOrgId());
		pstPartite.setInt(3, OrgImport.getOrgId());
		pstPartite.execute();
		
		PreparedStatement pstPartiteSedute = db.prepareStatement("update m_partite_sedute set id_macello = ?, old_id_macello = ? where id_macello = ?");
		pstPartiteSedute.setInt(1, newStabilimento.getAltId());
		pstPartiteSedute.setInt(2, OrgImport.getOrgId());
		pstPartiteSedute.setInt(3, OrgImport.getOrgId());
		pstPartiteSedute.execute();
			
		}
	
	}



	public static void completaDati(Connection db,Integer orgId, LineaProduttivaList lineel) {
		 
		PreparedStatement pst = null;
		 
		
		try
		{
			pst = db.prepareStatement("select * from public_functions.completa_dati(?,?)");
			pst.setInt(1, orgId);
			for(Object lineaO : lineel)
			{
				LineaProduttiva lineaLL = (LineaProduttiva) lineaO;
				Integer idRelStabLP = lineaLL.getId();
				pst.setInt(2, idRelStabLP);
				pst.executeQuery();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	
	public static void stampaLog(String text){
		System.out.println(text);
		 
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
	    String data = sdf.format(cal.getTime());
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("log_sintesis.txt", true));
		
		bufferedWriter.write(data+" "+text);
		bufferedWriter.write(System.lineSeparator());
		
		bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
