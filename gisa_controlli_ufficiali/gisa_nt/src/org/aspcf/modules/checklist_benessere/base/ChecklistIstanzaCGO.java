/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.aspcf.modules.report.util.ApplicationProperties;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;







import com.daffodilwoods.daffodildb.server.sql99.dml.contextuallytypedrowvalueconstructor;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanzaCGO extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanzaCGO() { }
	  
	private int id;
	private int idChkBnsModIst;
	private String note="";
	private boolean cgo4Rispettato = false;
	private boolean cgo9Rispettato = false;
	
	private String puntonote_1_1 = "";
	private String puntonote_1_2 = "";
	private String puntonote_1_3 = "";
	private String puntonote_1_4 = "";
	private String puntonote_2_1 = "";
	private String puntonote_2_2 = "";
	private String puntonote_2_3 = "";
	private String puntonote_2_4 = "";
	private String puntonote_3_1 = "";
	private String puntonote_3_2 = "";
	private String puntonote_3_3 = "";
	private String puntonote_3_4 = "";
	private String puntonote_4_1 = "";
	private String puntonote_4_2 = "";
	private String puntonote_4_3 = "";
	private String puntonote_4_4 = "";
	private String puntonote_5_1 = "";
	private String puntonote_5_2 = "";
	private String puntonote_5_3 = "";
	private String puntonote_5_4 = "";
	private String puntonote_6_1 = "";
	private String puntonote_6_2 = "";
	private String puntonote_6_3 = "";
	private String puntonote_6_4 = "";
	private String puntonote_7_1 = "";
	private String puntonote_7_2 = "";
	private String puntonote_7_3 = "";
	private String puntonote_7_4 = "";
	private String puntonote_8_1 = "";
	private String puntonote_8_2 = "";
	private String puntonote_8_3 = "";
	private String puntonote_8_4 = "";
	private String puntonote_9_1 = "";
	private String puntonote_9_2 = "";
	private String puntonote_9_3 = "";
	private String puntonote_9_4 = "";
	private String puntonote_10_1 = "";
	private String puntonote_10_2 = "";
	private String puntonote_10_3 = "";
	private String puntonote_10_4 = "";

	private boolean sez1Cgo4Interventi = false;
	private String sez1Cgo4InterventiNote = "";
	private String sez1Cgo4InterventiData = "";
	private boolean sez1Cgo9Interventi = false;
	private String sez1Cgo9InterventiNote = "";
	private String sez1Cgo9InterventiData = "";
	
	private boolean sez2Cgo4Interventi = false;
	private String sez2Cgo4InterventiData = "";
	private boolean sez2Cgo9Interventi = false;
	private String sez2Cgo9InterventiData = "";

	
	
	protected void buildRecord(ResultSet rs) throws SQLException {
		 
		 //chk_bns_mod_ist table
		 id = rs.getInt("id");
		 idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");
		 note= rs.getString("note");
		 cgo4Rispettato = rs.getBoolean("cgo4_rispettato");
		 cgo9Rispettato = rs.getBoolean("cgo9_rispettato");
			
		 puntonote_1_1=rs.getString("puntonote_1_1");
		 puntonote_1_2=rs.getString("puntonote_1_2");
		 puntonote_1_3=rs.getString("puntonote_1_3");
		 puntonote_1_4=rs.getString("puntonote_1_4");
		 puntonote_2_1=rs.getString("puntonote_2_1");
		 puntonote_2_2=rs.getString("puntonote_2_2");
		 puntonote_2_3=rs.getString("puntonote_2_3");
		 puntonote_2_4=rs.getString("puntonote_2_4");
		 puntonote_3_1=rs.getString("puntonote_3_1");
		 puntonote_3_2=rs.getString("puntonote_3_2");
		 puntonote_3_3=rs.getString("puntonote_3_3");
		 puntonote_3_4=rs.getString("puntonote_3_4");
		 puntonote_4_1=rs.getString("puntonote_4_1");
		 puntonote_4_2=rs.getString("puntonote_4_2");
		 puntonote_4_3=rs.getString("puntonote_4_3");
		 puntonote_4_4=rs.getString("puntonote_4_4");
		 puntonote_5_1=rs.getString("puntonote_5_1");
		 puntonote_5_2=rs.getString("puntonote_5_2");
		 puntonote_5_3=rs.getString("puntonote_5_3");
		 puntonote_5_4=rs.getString("puntonote_5_4");
		 puntonote_6_1=rs.getString("puntonote_6_1");
		 puntonote_6_2=rs.getString("puntonote_6_2");
		 puntonote_6_3=rs.getString("puntonote_6_3");
		 puntonote_6_4=rs.getString("puntonote_6_4");
		 puntonote_7_1=rs.getString("puntonote_7_1");
		 puntonote_7_2=rs.getString("puntonote_7_2");
		 puntonote_7_3=rs.getString("puntonote_7_3");
		 puntonote_7_4=rs.getString("puntonote_7_4");
		 puntonote_8_1=rs.getString("puntonote_8_1");
		 puntonote_8_2=rs.getString("puntonote_8_2");
		 puntonote_8_3=rs.getString("puntonote_8_3");
		 puntonote_8_4=rs.getString("puntonote_8_4");
		 puntonote_9_1=rs.getString("puntonote_9_1");
		 puntonote_9_2=rs.getString("puntonote_9_2");
		 puntonote_9_3=rs.getString("puntonote_9_3");
		 puntonote_9_4=rs.getString("puntonote_9_4");
		 puntonote_10_1=rs.getString("puntonote_10_1");
		 puntonote_10_2=rs.getString("puntonote_10_2");
		 puntonote_10_3=rs.getString("puntonote_10_3");
		 puntonote_10_4=rs.getString("puntonote_10_4");

		 sez1Cgo4Interventi = rs.getBoolean("sez1_cgo4_interventi");
		 sez1Cgo4InterventiNote = rs.getString("sez1_cgo4_interventi_note");
		 sez1Cgo4InterventiData = rs.getString("sez1_cgo4_interventi_data");
		 sez1Cgo9Interventi = rs.getBoolean("sez1_cgo9_interventi");
		 sez1Cgo9InterventiNote = rs.getString("sez1_cgo9_interventi_note");
		 sez1Cgo9InterventiData = rs.getString("sez1_cgo9_interventi_data");
			
		 sez2Cgo4Interventi = rs.getBoolean("sez2_cgo4_interventi");
		 sez2Cgo4InterventiData = rs.getString("sez2_cgo4_interventi_data");
		 sez2Cgo9Interventi = rs.getBoolean("sez2_cgo9_interventi");
		 sez2Cgo9InterventiData = rs.getString("sez2_cgo9_interventi_data");
			    
	 }
	

	public ChecklistIstanzaCGO(Connection db, int idChkBnsModIst) 
		throws SQLException {
			
			  
		    if (idChkBnsModIst == -1) {
		      throw new SQLException("Invalid ChkIstID");
		    } 
		    PreparedStatement pst = db.prepareStatement(
		        "select * from chk_bns_mod_ist_cgo " +
		        "where id_chk_bns_mod_ist = ? ");

		    pst.setInt(1, idChkBnsModIst);
		    
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		    }
		    
		    rs.close();
		    pst.close();
			  
	}
	  
	
	
	public ChecklistIstanzaCGO(ActionContext context) {

		setNote(context.getRequest().getParameter("cgo_note"));
		setCgo4Rispettato(context.getRequest().getParameter("cgo4rispettato"));
		setCgo9Rispettato(context.getRequest().getParameter("cgo9rispettato"));
		
		setSez1Cgo4Interventi(context.getRequest().getParameter("sez1_cgo4interventi"));
		setSez1Cgo4InterventiNote(context.getRequest().getParameter("sez1_cgo4interventi_note"));
		setSez1Cgo4InterventiData(context.getRequest().getParameter("sez1_cgo4interventi_data"));

		setSez1Cgo9Interventi(context.getRequest().getParameter("sez1_cgo9interventi"));
		setSez1Cgo9InterventiNote(context.getRequest().getParameter("sez1_cgo9interventi_note"));
		setSez1Cgo9InterventiData(context.getRequest().getParameter("sez1_cgo9interventi_data"));
		
		setSez2Cgo4Interventi(context.getRequest().getParameter("sez2_cgo4interventi"));
		setSez2Cgo4InterventiData(context.getRequest().getParameter("sez2_cgo4interventi_data"));

		setSez2Cgo9Interventi(context.getRequest().getParameter("sez2_cgo9interventi"));
		setSez2Cgo9InterventiData(context.getRequest().getParameter("sez2_cgo9interventi_data"));
		
		setPuntonote_1_1(context.getRequest().getParameter("cgo_puntonote_1_1"));
		setPuntonote_1_2(context.getRequest().getParameter("cgo_puntonote_1_2"));
		setPuntonote_1_3(context.getRequest().getParameter("cgo_puntonote_1_3"));
		setPuntonote_1_4(context.getRequest().getParameter("cgo_puntonote_1_4"));
		setPuntonote_2_1(context.getRequest().getParameter("cgo_puntonote_2_1"));
		setPuntonote_2_2(context.getRequest().getParameter("cgo_puntonote_2_2"));
		setPuntonote_2_3(context.getRequest().getParameter("cgo_puntonote_2_3"));
		setPuntonote_2_4(context.getRequest().getParameter("cgo_puntonote_2_4"));
		setPuntonote_3_1(context.getRequest().getParameter("cgo_puntonote_3_1"));
		setPuntonote_3_2(context.getRequest().getParameter("cgo_puntonote_3_2"));
		setPuntonote_3_3(context.getRequest().getParameter("cgo_puntonote_3_3"));
		setPuntonote_3_4(context.getRequest().getParameter("cgo_puntonote_3_4"));
		setPuntonote_4_1(context.getRequest().getParameter("cgo_puntonote_4_1"));
		setPuntonote_4_2(context.getRequest().getParameter("cgo_puntonote_4_2"));
		setPuntonote_4_3(context.getRequest().getParameter("cgo_puntonote_4_3"));
		setPuntonote_4_4(context.getRequest().getParameter("cgo_puntonote_4_4"));
		setPuntonote_5_1(context.getRequest().getParameter("cgo_puntonote_5_1"));
		setPuntonote_5_2(context.getRequest().getParameter("cgo_puntonote_5_2"));
		setPuntonote_5_3(context.getRequest().getParameter("cgo_puntonote_5_3"));
		setPuntonote_5_4(context.getRequest().getParameter("cgo_puntonote_5_4"));
		setPuntonote_6_1(context.getRequest().getParameter("cgo_puntonote_6_1"));
		setPuntonote_6_2(context.getRequest().getParameter("cgo_puntonote_6_2"));
		setPuntonote_6_3(context.getRequest().getParameter("cgo_puntonote_6_3"));
		setPuntonote_6_4(context.getRequest().getParameter("cgo_puntonote_6_4"));
		setPuntonote_7_1(context.getRequest().getParameter("cgo_puntonote_7_1"));
		setPuntonote_7_2(context.getRequest().getParameter("cgo_puntonote_7_2"));
		setPuntonote_7_3(context.getRequest().getParameter("cgo_puntonote_7_3"));
		setPuntonote_7_4(context.getRequest().getParameter("cgo_puntonote_7_4"));
		setPuntonote_8_1(context.getRequest().getParameter("cgo_puntonote_8_1"));
		setPuntonote_8_2(context.getRequest().getParameter("cgo_puntonote_8_2"));
		setPuntonote_8_3(context.getRequest().getParameter("cgo_puntonote_8_3"));
		setPuntonote_8_4(context.getRequest().getParameter("cgo_puntonote_8_4"));
		setPuntonote_9_1(context.getRequest().getParameter("cgo_puntonote_9_1"));
		setPuntonote_9_2(context.getRequest().getParameter("cgo_puntonote_9_2"));
		setPuntonote_9_3(context.getRequest().getParameter("cgo_puntonote_9_3"));
		setPuntonote_9_4(context.getRequest().getParameter("cgo_puntonote_9_4"));
		setPuntonote_10_1(context.getRequest().getParameter("cgo_puntonote_10_1"));
		setPuntonote_10_2(context.getRequest().getParameter("cgo_puntonote_10_2"));
		setPuntonote_10_3(context.getRequest().getParameter("cgo_puntonote_10_3"));
		setPuntonote_10_4(context.getRequest().getParameter("cgo_puntonote_10_4"));

	}



	public boolean upsert(Connection db) throws SQLException {
	    
		int idRecuperato = -1;
		PreparedStatement pst = db.prepareStatement("select id from chk_bns_mod_ist_cgo where id_chk_bns_mod_ist = ?");
		pst.setInt(1, idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			idRecuperato = rs.getInt("id");
		
		if (idRecuperato > 0)
			update(db, idRecuperato);
		else
			insert(db);
	
		return true;
	  }

	
	 public boolean update(Connection db, int idRecuperato) throws SQLException {
				 
		 String sql = "update chk_bns_mod_ist_cgo set note = ?, cgo4_rispettato = ?, cgo9_rispettato = ?, "
		 		+ "sez1_cgo4_interventi = ?, sez1_cgo4_interventi_note = ?, sez1_cgo4_interventi_data = ?, sez1_cgo9_interventi = ?, sez1_cgo9_interventi_note = ?, "
		 		+ "sez1_cgo9_interventi_data = ?, sez2_cgo4_interventi = ?, sez2_cgo4_interventi_data = ?, sez2_cgo9_interventi = ?, sez2_cgo9_interventi_data = ?,  "
		 		+ "puntonote_1_1 = ?, puntonote_1_2 = ?, puntonote_1_3 = ?, puntonote_1_4 = ?, puntonote_2_1 = ?, puntonote_2_2 = ?, puntonote_2_3 = ?, puntonote_2_4 = ?, "
		 		+ "puntonote_3_1 = ?, puntonote_3_2 = ?, puntonote_3_3 = ?, puntonote_3_4 = ?, puntonote_4_1 = ?, puntonote_4_2 = ?, puntonote_4_3 = ?, puntonote_4_4 = ?,"
		 		+ " puntonote_5_1 = ?, puntonote_5_2 = ?, puntonote_5_3 = ?, puntonote_5_4 = ?, puntonote_6_1 = ?, puntonote_6_2 = ?, puntonote_6_3 = ?, puntonote_6_4 = ?,"
		 		+ " puntonote_7_1 = ?, puntonote_7_2 = ?, puntonote_7_3 = ?, puntonote_7_4 = ?, puntonote_8_1 = ?, puntonote_8_2 = ?, puntonote_8_3 = ?, puntonote_8_4 = ?,"
		 		+ " puntonote_9_1 = ?, puntonote_9_2 = ?, puntonote_9_3 = ?, puntonote_9_4 = ?, puntonote_10_1 = ?, puntonote_10_2 = ?, puntonote_10_3 = ?, puntonote_10_4 = ? "
		 		+ "where id = ? ";
	
		 PreparedStatement pst = db.prepareStatement(sql);
		 int i = 0;
		 pst.setString(++i, note);
		 pst.setBoolean(++i, cgo4Rispettato);
		 pst.setBoolean(++i, cgo9Rispettato);
		 pst.setBoolean(++i, sez1Cgo4Interventi);
		 pst.setString(++i, sez1Cgo4InterventiNote);
		 pst.setString(++i, sez1Cgo4InterventiData);
		 pst.setBoolean(++i, sez1Cgo9Interventi);
		 pst.setString(++i, sez1Cgo9InterventiNote);
		 pst.setString(++i, sez1Cgo9InterventiData);
		 pst.setBoolean(++i, sez2Cgo4Interventi);
		 pst.setString(++i, sez2Cgo4InterventiData);
		 pst.setBoolean(++i, sez2Cgo9Interventi);
		 pst.setString(++i, sez2Cgo9InterventiData);
		 pst.setString(++i,puntonote_1_1);
		 pst.setString(++i,puntonote_1_2);
		 pst.setString(++i,puntonote_1_3);
		 pst.setString(++i,puntonote_1_4);
		 pst.setString(++i,puntonote_2_1);
		 pst.setString(++i,puntonote_2_2);
		 pst.setString(++i,puntonote_2_3);
		 pst.setString(++i,puntonote_2_4);
		 pst.setString(++i,puntonote_3_1);
		 pst.setString(++i,puntonote_3_2);
		 pst.setString(++i,puntonote_3_3);
		 pst.setString(++i,puntonote_3_4);
		 pst.setString(++i,puntonote_4_1);
		 pst.setString(++i,puntonote_4_2);
		 pst.setString(++i,puntonote_4_3);
		 pst.setString(++i,puntonote_4_4);
		 pst.setString(++i,puntonote_5_1);
		 pst.setString(++i,puntonote_5_2);
		 pst.setString(++i,puntonote_5_3);
		 pst.setString(++i,puntonote_5_4);
		 pst.setString(++i,puntonote_6_1);
		 pst.setString(++i,puntonote_6_2);
		 pst.setString(++i,puntonote_6_3);
		 pst.setString(++i,puntonote_6_4);
		 pst.setString(++i,puntonote_7_1);
		 pst.setString(++i,puntonote_7_2);
		 pst.setString(++i,puntonote_7_3);
		 pst.setString(++i,puntonote_7_4);
		 pst.setString(++i,puntonote_8_1);
		 pst.setString(++i,puntonote_8_2);
		 pst.setString(++i,puntonote_8_3);
		 pst.setString(++i,puntonote_8_4);
		 pst.setString(++i,puntonote_9_1);
		 pst.setString(++i,puntonote_9_2);
		 pst.setString(++i,puntonote_9_3);
		 pst.setString(++i,puntonote_9_4);
		 pst.setString(++i,puntonote_10_1);
		 pst.setString(++i,puntonote_10_2);
		 pst.setString(++i,puntonote_10_3);
		 pst.setString(++i,puntonote_10_4);
		 pst.setInt(++i, idRecuperato);
		 pst.executeUpdate();
		 return true;
		 
		 
	 
	 }

	 
	 public boolean insert(Connection db) throws SQLException {
		 
		 String sql = "insert into chk_bns_mod_ist_cgo (id_chk_bns_mod_ist, note, cgo4_rispettato, cgo9_rispettato, "
		 		+ "sez1_cgo4_interventi, sez1_cgo4_interventi_note, sez1_cgo4_interventi_data, sez1_cgo9_interventi, sez1_cgo9_interventi_note, "
		 		+ "sez1_cgo9_interventi_data, sez2_cgo4_interventi, sez2_cgo4_interventi_data, sez2_cgo9_interventi, sez2_cgo9_interventi_data,  "
		 		+ "puntonote_1_1, puntonote_1_2, puntonote_1_3, puntonote_1_4, puntonote_2_1, puntonote_2_2, puntonote_2_3, puntonote_2_4, "
		 		+ "puntonote_3_1, puntonote_3_2, puntonote_3_3, puntonote_3_4, puntonote_4_1, puntonote_4_2, puntonote_4_3, puntonote_4_4,"
		 		+ " puntonote_5_1, puntonote_5_2, puntonote_5_3, puntonote_5_4, puntonote_6_1, puntonote_6_2, puntonote_6_3, puntonote_6_4,"
		 		+ " puntonote_7_1, puntonote_7_2, puntonote_7_3, puntonote_7_4, puntonote_8_1, puntonote_8_2, puntonote_8_3, puntonote_8_4,"
		 		+ " puntonote_9_1, puntonote_9_2, puntonote_9_3, puntonote_9_4, puntonote_10_1, puntonote_10_2, puntonote_10_3, puntonote_10_4) values ("
		 		+ "?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?, ?, ?, ?,"
		 		+ "?, ?, ?, ?, ?, ?, ?, ?);";
	
		 PreparedStatement pst = db.prepareStatement(sql);
		 int i = 0;
		 pst.setInt(++i, idChkBnsModIst);
		 pst.setString(++i, note);
		 pst.setBoolean(++i, cgo4Rispettato);
		 pst.setBoolean(++i, cgo9Rispettato);
		 pst.setBoolean(++i, sez1Cgo4Interventi);
		 pst.setString(++i, sez1Cgo4InterventiNote);
		 pst.setString(++i, sez1Cgo4InterventiData);
		 pst.setBoolean(++i, sez1Cgo9Interventi);
		 pst.setString(++i, sez1Cgo9InterventiNote);
		 pst.setString(++i, sez1Cgo9InterventiData);
		 pst.setBoolean(++i, sez2Cgo4Interventi);
		 pst.setString(++i, sez2Cgo4InterventiData);
		 pst.setBoolean(++i, sez2Cgo9Interventi);
		 pst.setString(++i, sez2Cgo9InterventiData);
		 pst.setString(++i,puntonote_1_1);
		 pst.setString(++i,puntonote_1_2);
		 pst.setString(++i,puntonote_1_3);
		 pst.setString(++i,puntonote_1_4);
		 pst.setString(++i,puntonote_2_1);
		 pst.setString(++i,puntonote_2_2);
		 pst.setString(++i,puntonote_2_3);
		 pst.setString(++i,puntonote_2_4);
		 pst.setString(++i,puntonote_3_1);
		 pst.setString(++i,puntonote_3_2);
		 pst.setString(++i,puntonote_3_3);
		 pst.setString(++i,puntonote_3_4);
		 pst.setString(++i,puntonote_4_1);
		 pst.setString(++i,puntonote_4_2);
		 pst.setString(++i,puntonote_4_3);
		 pst.setString(++i,puntonote_4_4);
		 pst.setString(++i,puntonote_5_1);
		 pst.setString(++i,puntonote_5_2);
		 pst.setString(++i,puntonote_5_3);
		 pst.setString(++i,puntonote_5_4);
		 pst.setString(++i,puntonote_6_1);
		 pst.setString(++i,puntonote_6_2);
		 pst.setString(++i,puntonote_6_3);
		 pst.setString(++i,puntonote_6_4);
		 pst.setString(++i,puntonote_7_1);
		 pst.setString(++i,puntonote_7_2);
		 pst.setString(++i,puntonote_7_3);
		 pst.setString(++i,puntonote_7_4);
		 pst.setString(++i,puntonote_8_1);
		 pst.setString(++i,puntonote_8_2);
		 pst.setString(++i,puntonote_8_3);
		 pst.setString(++i,puntonote_8_4);
		 pst.setString(++i,puntonote_9_1);
		 pst.setString(++i,puntonote_9_2);
		 pst.setString(++i,puntonote_9_3);
		 pst.setString(++i,puntonote_9_4);
		 pst.setString(++i,puntonote_10_1);
		 pst.setString(++i,puntonote_10_2);
		 pst.setString(++i,puntonote_10_3);
		 pst.setString(++i,puntonote_10_4);
		 pst.executeUpdate();
		 return true;
		 
		 
	 }


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdChkBnsModIst() {
		return idChkBnsModIst;
	}


	public void setIdChkBnsModIst(int idChkBnsModIst) {
		this.idChkBnsModIst = idChkBnsModIst;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public boolean isCgo4Rispettato() {
		return cgo4Rispettato;
	}


	public void setCgo4Rispettato(boolean cgo4Rispettato) {
		this.cgo4Rispettato = cgo4Rispettato;
	}


	public boolean isCgo9Rispettato() {
		return cgo9Rispettato;
	}


	public void setCgo9Rispettato(boolean cgo9Rispettato) {
		this.cgo9Rispettato = cgo9Rispettato;
	}


	public String getPuntonote_1_1() {
		return puntonote_1_1;
	}


	public void setPuntonote_1_1(String puntonote_1_1) {
		this.puntonote_1_1 = puntonote_1_1;
	}


	public String getPuntonote_1_2() {
		return puntonote_1_2;
	}


	public void setPuntonote_1_2(String puntonote_1_2) {
		this.puntonote_1_2 = puntonote_1_2;
	}


	public String getPuntonote_1_3() {
		return puntonote_1_3;
	}


	public void setPuntonote_1_3(String puntonote_1_3) {
		this.puntonote_1_3 = puntonote_1_3;
	}


	public String getPuntonote_1_4() {
		return puntonote_1_4;
	}


	public void setPuntonote_1_4(String puntonote_1_4) {
		this.puntonote_1_4 = puntonote_1_4;
	}


	public String getPuntonote_2_1() {
		return puntonote_2_1;
	}


	public void setPuntonote_2_1(String puntonote_2_1) {
		this.puntonote_2_1 = puntonote_2_1;
	}


	public String getPuntonote_2_2() {
		return puntonote_2_2;
	}


	public void setPuntonote_2_2(String puntonote_2_2) {
		this.puntonote_2_2 = puntonote_2_2;
	}


	public String getPuntonote_2_3() {
		return puntonote_2_3;
	}


	public void setPuntonote_2_3(String puntonote_2_3) {
		this.puntonote_2_3 = puntonote_2_3;
	}


	public String getPuntonote_2_4() {
		return puntonote_2_4;
	}


	public void setPuntonote_2_4(String puntonote_2_4) {
		this.puntonote_2_4 = puntonote_2_4;
	}


	public String getPuntonote_3_1() {
		return puntonote_3_1;
	}


	public void setPuntonote_3_1(String puntonote_3_1) {
		this.puntonote_3_1 = puntonote_3_1;
	}


	public String getPuntonote_3_2() {
		return puntonote_3_2;
	}


	public void setPuntonote_3_2(String puntonote_3_2) {
		this.puntonote_3_2 = puntonote_3_2;
	}


	public String getPuntonote_3_3() {
		return puntonote_3_3;
	}


	public void setPuntonote_3_3(String puntonote_3_3) {
		this.puntonote_3_3 = puntonote_3_3;
	}


	public String getPuntonote_3_4() {
		return puntonote_3_4;
	}


	public void setPuntonote_3_4(String puntonote_3_4) {
		this.puntonote_3_4 = puntonote_3_4;
	}


	public String getPuntonote_4_1() {
		return puntonote_4_1;
	}


	public void setPuntonote_4_1(String puntonote_4_1) {
		this.puntonote_4_1 = puntonote_4_1;
	}


	public String getPuntonote_4_2() {
		return puntonote_4_2;
	}


	public void setPuntonote_4_2(String puntonote_4_2) {
		this.puntonote_4_2 = puntonote_4_2;
	}


	public String getPuntonote_4_3() {
		return puntonote_4_3;
	}


	public void setPuntonote_4_3(String puntonote_4_3) {
		this.puntonote_4_3 = puntonote_4_3;
	}


	public String getPuntonote_4_4() {
		return puntonote_4_4;
	}


	public void setPuntonote_4_4(String puntonote_4_4) {
		this.puntonote_4_4 = puntonote_4_4;
	}


	public String getPuntonote_5_1() {
		return puntonote_5_1;
	}


	public void setPuntonote_5_1(String puntonote_5_1) {
		this.puntonote_5_1 = puntonote_5_1;
	}


	public String getPuntonote_5_2() {
		return puntonote_5_2;
	}


	public void setPuntonote_5_2(String puntonote_5_2) {
		this.puntonote_5_2 = puntonote_5_2;
	}


	public String getPuntonote_5_3() {
		return puntonote_5_3;
	}


	public void setPuntonote_5_3(String puntonote_5_3) {
		this.puntonote_5_3 = puntonote_5_3;
	}


	public String getPuntonote_5_4() {
		return puntonote_5_4;
	}


	public void setPuntonote_5_4(String puntonote_5_4) {
		this.puntonote_5_4 = puntonote_5_4;
	}


	public String getPuntonote_6_1() {
		return puntonote_6_1;
	}


	public void setPuntonote_6_1(String puntonote_6_1) {
		this.puntonote_6_1 = puntonote_6_1;
	}


	public String getPuntonote_6_2() {
		return puntonote_6_2;
	}


	public void setPuntonote_6_2(String puntonote_6_2) {
		this.puntonote_6_2 = puntonote_6_2;
	}


	public String getPuntonote_6_3() {
		return puntonote_6_3;
	}


	public void setPuntonote_6_3(String puntonote_6_3) {
		this.puntonote_6_3 = puntonote_6_3;
	}


	public String getPuntonote_6_4() {
		return puntonote_6_4;
	}


	public void setPuntonote_6_4(String puntonote_6_4) {
		this.puntonote_6_4 = puntonote_6_4;
	}


	public String getPuntonote_7_1() {
		return puntonote_7_1;
	}


	public void setPuntonote_7_1(String puntonote_7_1) {
		this.puntonote_7_1 = puntonote_7_1;
	}


	public String getPuntonote_7_2() {
		return puntonote_7_2;
	}


	public void setPuntonote_7_2(String puntonote_7_2) {
		this.puntonote_7_2 = puntonote_7_2;
	}


	public String getPuntonote_7_3() {
		return puntonote_7_3;
	}


	public void setPuntonote_7_3(String puntonote_7_3) {
		this.puntonote_7_3 = puntonote_7_3;
	}


	public String getPuntonote_7_4() {
		return puntonote_7_4;
	}


	public void setPuntonote_7_4(String puntonote_7_4) {
		this.puntonote_7_4 = puntonote_7_4;
	}


	public String getPuntonote_8_1() {
		return puntonote_8_1;
	}


	public void setPuntonote_8_1(String puntonote_8_1) {
		this.puntonote_8_1 = puntonote_8_1;
	}


	public String getPuntonote_8_2() {
		return puntonote_8_2;
	}


	public void setPuntonote_8_2(String puntonote_8_2) {
		this.puntonote_8_2 = puntonote_8_2;
	}


	public String getPuntonote_8_3() {
		return puntonote_8_3;
	}


	public void setPuntonote_8_3(String puntonote_8_3) {
		this.puntonote_8_3 = puntonote_8_3;
	}


	public String getPuntonote_8_4() {
		return puntonote_8_4;
	}


	public void setPuntonote_8_4(String puntonote_8_4) {
		this.puntonote_8_4 = puntonote_8_4;
	}


	public String getPuntonote_9_1() {
		return puntonote_9_1;
	}


	public void setPuntonote_9_1(String puntonote_9_1) {
		this.puntonote_9_1 = puntonote_9_1;
	}


	public String getPuntonote_9_2() {
		return puntonote_9_2;
	}


	public void setPuntonote_9_2(String puntonote_9_2) {
		this.puntonote_9_2 = puntonote_9_2;
	}


	public String getPuntonote_9_3() {
		return puntonote_9_3;
	}


	public void setPuntonote_9_3(String puntonote_9_3) {
		this.puntonote_9_3 = puntonote_9_3;
	}


	public String getPuntonote_9_4() {
		return puntonote_9_4;
	}


	public void setPuntonote_9_4(String puntonote_9_4) {
		this.puntonote_9_4 = puntonote_9_4;
	}


	public String getPuntonote_10_1() {
		return puntonote_10_1;
	}


	public void setPuntonote_10_1(String puntonote_10_1) {
		this.puntonote_10_1 = puntonote_10_1;
	}


	public String getPuntonote_10_2() {
		return puntonote_10_2;
	}


	public void setPuntonote_10_2(String puntonote_10_2) {
		this.puntonote_10_2 = puntonote_10_2;
	}


	public String getPuntonote_10_3() {
		return puntonote_10_3;
	}


	public void setPuntonote_10_3(String puntonote_10_3) {
		this.puntonote_10_3 = puntonote_10_3;
	}


	public String getPuntonote_10_4() {
		return puntonote_10_4;
	}


	public void setPuntonote_10_4(String puntonote_10_4) {
		this.puntonote_10_4 = puntonote_10_4;
	}


	public boolean isSez1Cgo4Interventi() {
		return sez1Cgo4Interventi;
	}


	public void setSez1Cgo4Interventi(boolean sez1Cgo4Interventi) {
		this.sez1Cgo4Interventi = sez1Cgo4Interventi;
	}


	public String getSez1Cgo4InterventiNote() {
		return sez1Cgo4InterventiNote;
	}


	public void setSez1Cgo4InterventiNote(String sez1Cgo4InterventiNote) {
		this.sez1Cgo4InterventiNote = sez1Cgo4InterventiNote;
	}


	public String getSez1Cgo4InterventiData() {
		return sez1Cgo4InterventiData;
	}


	public void setSez1Cgo4InterventiData(String sez1Cgo4InterventiData) {
		this.sez1Cgo4InterventiData = sez1Cgo4InterventiData;
	}


	public boolean isSez1Cgo9Interventi() {
		return sez1Cgo9Interventi;
	}


	public void setSez1Cgo9Interventi(boolean sez1Cgo9Interventi) {
		this.sez1Cgo9Interventi = sez1Cgo9Interventi;
	}


	public String getSez1Cgo9InterventiNote() {
		return sez1Cgo9InterventiNote;
	}


	public void setSez1Cgo9InterventiNote(String sez1Cgo9InterventiNote) {
		this.sez1Cgo9InterventiNote = sez1Cgo9InterventiNote;
	}


	public String getSez1Cgo9InterventiData() {
		return sez1Cgo9InterventiData;
	}


	public void setSez1Cgo9InterventiData(String sez1Cgo9InterventiData) {
		this.sez1Cgo9InterventiData = sez1Cgo9InterventiData;
	}


	public boolean isSez2Cgo4Interventi() {
		return sez2Cgo4Interventi;
	}


	public void setSez2Cgo4Interventi(boolean sez2Cgo4Interventi) {
		this.sez2Cgo4Interventi = sez2Cgo4Interventi;
	}


	public String getSez2Cgo4InterventiData() {
		return sez2Cgo4InterventiData;
	}


	public void setSez2Cgo4InterventiData(String sez2Cgo4InterventiData) {
		this.sez2Cgo4InterventiData = sez2Cgo4InterventiData;
	}


	public boolean isSez2Cgo9Interventi() {
		return sez2Cgo9Interventi;
	}


	public void setSez2Cgo9Interventi(boolean sez2Cgo9Interventi) {
		this.sez2Cgo9Interventi = sez2Cgo9Interventi;
	}


	public String getSez2Cgo9InterventiData() {
		return sez2Cgo9InterventiData;
	}


	public void setSez2Cgo9InterventiData(String sez2Cgo9InterventiData) {
		this.sez2Cgo9InterventiData = sez2Cgo9InterventiData;
	}
	

	private void setSez2Cgo9Interventi(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			sez2Cgo9Interventi = true;
		else 
			sez2Cgo9Interventi = false;
	}


	private void setSez2Cgo4Interventi(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			sez2Cgo4Interventi = true;
		else 
			sez2Cgo4Interventi = false;
	}


	private void setSez1Cgo9Interventi(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			sez1Cgo9Interventi = true;
		else 
			sez1Cgo9Interventi = false;
	}


	private void setSez1Cgo4Interventi(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			sez1Cgo4Interventi = true;
		else 
			sez1Cgo4Interventi = false;
	}


	private void setCgo4Rispettato(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			cgo4Rispettato = true;
		else 
			cgo4Rispettato = false;
	}
	private void setCgo9Rispettato(String parameter) {
		if (parameter!=null && parameter.equals("si"))
			cgo9Rispettato = true;
		else 
			cgo9Rispettato = false;
	}
	
	
	    
}
