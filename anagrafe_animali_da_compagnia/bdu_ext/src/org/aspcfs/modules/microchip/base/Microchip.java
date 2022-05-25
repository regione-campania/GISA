/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.microchip.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class Microchip extends GenericBean {
	/**
	 * 
	 */
	
	public static  final int id_specie_cane = 1 ;
	public static final int id_specie_gatto = 2 ;
	private static final long serialVersionUID = -360340615919400448L;
	private int id = -1;
	private String microchip = null;
	private String asl = null;
	private int aslRif = -1;
	private int statusId = -1;
	private int importId = -1;
	private int enteredBy = -1;
	private int modifiedBy = -1;
	private boolean assegnato = false;
	private boolean assegnato_felina = false;
	private Timestamp trashed_date= null;
	private boolean abilitato = true;
	private Timestamp dataCaricamento= null;
	private int idImport = -1;

	
	
	/*NUOVI CAMPI*/
	
	private int idSpecie ;
	private int idAnimale ;
	private boolean flagSecondoMicrochip ;
	
	
	
	public boolean isFlagSecondoMicrochip() {
		return flagSecondoMicrochip;
	}

	public void setFlagSecondoMicrochip(boolean flagSecondoMicrochip) {
		this.flagSecondoMicrochip = flagSecondoMicrochip;
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public boolean isAssegnato_felina() {
		return assegnato_felina;
	}

	public void setAssegnato_felina(boolean assegnato_felina) {
		this.assegnato_felina = assegnato_felina;
	}

	public Microchip() {
	}

	public Microchip(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	
	//commentato assegnato a felina
	public boolean insert(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();

		try {

			sql.append("INSERT INTO microchips ( microchip, asl, status_id, assegnato_felina, " ); 
			//sql.append("INSERT INTO microchips ( microchip, asl, status_id,  " );
			if ( importId > -1 ){
				sql.append( " import_id, ");
			}
			
			sql.append(" enteredby, modifiedby, data_caricamento ) VALUES ( ? , ? , ? , ? ,");
			
			if ( importId > -1 ) {
				sql.append( " ?, " );
			}
					
			sql.append(" ?,?, now() )");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, microchip);
			pst.setString(++i, asl);
			pst.setInt(++i, statusId);
			pst.setBoolean( ++i, assegnato_felina );
			
			if ( importId > -1) {
				pst.setInt(++i, importId);
			}
			
			pst.setInt(++i, enteredBy);
			pst.setInt(++i, modifiedBy);
			pst.execute();
			pst.close();

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
		}

		return true;
	}

	public static boolean verifyDuplicate(Connection db, String mc) throws SQLException {
		String microchip = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT m.microchip FROM microchips m WHERE m.microchip = ? and trashed_date is null ");
		pst.setString(1, mc);
		rs = pst.executeQuery();

		if (rs.next()) {
			microchip = rs.getString("microchip");
		}
		rs.close();
		pst.close();

		if (microchip != null){
			return false;
		}else{
			return true;
		}
	}

	
	public static int getTotaliAsl(Connection db, String asl) throws SQLException {
		String a = null;
		int tot = 0;
		if ( asl.length() == 16 || ("UNINA").equals(asl) ) 
		{
			a = asl;
		}else{
			a = getAslMC(Integer.parseInt(asl));
		}
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT COUNT(*) as totale FROM microchips WHERE asl = ? AND trashed_date IS NULL ");
		pst.setString(1, a);
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt("totale");
		}

		rs.close();
		pst.close();

		return tot;
	}
	
	public static int getAssegnatiAslMc(Connection db, String asl,int idSpecie) throws SQLException {
		String a = null;
		int tot = 0;
		if ( asl.length() == 16 || ("UNINA").equals(asl)) 
		{
			a = asl;
		}else{
			a = getAslMC(Integer.parseInt(asl));
		}
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		pst = db.prepareStatement("select * from get_numero_microchip_assegnati(?,?)");
		pst.setInt(1, idSpecie);
		pst.setString(2, a);
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt(1);
		}

		rs.close();
		pst.close();

		return tot;
	}

	public static int getAssegnatiAslTat(Connection db, String asl , int idSpecie) throws SQLException {
		String a = null;
		int tot = 0;
		if ( asl.length() == 16 || ("UNINA").equals(asl) ) 
		{
			a = asl;
		}else{
			a = getAslMC(Integer.parseInt(asl));
		}
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		pst = db.prepareStatement("select * from get_numero_tatuaggi_assegnati(?,?)");
		pst.setInt(1, idSpecie);
		pst.setString(2, a);
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt(1);
		}

		rs.close();
		pst.close();

		return tot;
	}

//	public static int getAssegnatiAsl(Connection db, String asl) throws SQLException {
//		String a = null;
//		int tot = 0;
//		if ( asl.length() == 16 ) 
//		{
//			a = asl;
//		}else{
//			a = getAslMC(Integer.parseInt(asl));
//		}
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		
//		
//		pst = db.prepareStatement("Select sum (assegnati) as assegnati from (SELECT COUNT(*) as assegnati FROM animale a, microchips m WHERE ( a.microchip = m.microchip ) AND m.asl = ? AND m.trashed_date IS NULL and m.enabled=true and a.trashed_date is null " +
//				"union SELECT COUNT(*) as assegnati FROM animale a, microchips m WHERE ( a.tatuaggio = m.microchip ) AND m.asl = ? AND m.trashed_date IS NULL and m.enabled=true and a.trashed_date is null )assegnati ");
//		
//		//pst = db.prepareStatement("SELECT COUNT(*) as assegnati FROM asset a, microchips m WHERE ( a.serial_number = m.microchip ) AND m.asl = ? AND m.trashed_date IS NULL and m.enabled=true and a.trashed_date is null");
//		pst.setString(1, a);
//		pst.setString(2, a);
//		rs = pst.executeQuery();
//
//		if (rs.next()) {
//			tot = rs.getInt("assegnati");
//		}
//
//		rs.close();
//		pst.close();
//
//		return tot;
//	}
	

	
	public static int getFuoriUso(Connection db, String asl) throws SQLException {
		String a = null;
		int tot  = 0;
		if ( asl.length() == 16 || ("UNINA").equals(asl) ) 
		{
			a = asl;
		}else{
			a = getAslMC(Integer.parseInt(asl));
		}
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT COUNT(*) as fuori_uso FROM microchips m WHERE m.asl = ? AND m.enabled = false AND m.trashed_date IS NULL ");
		pst.setString(1, a);
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt("fuori_uso");
		}

		rs.close();
		pst.close();

		return tot;
	}
	
	public static boolean checkMyMc(Connection db, String mc, String rif) throws SQLException {
		boolean rit = false;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT microchip as my_mc FROM microchips m WHERE m.microchip = ? AND m.asl = ? AND trashed_date IS NULL");
		pst.setString(1, mc);
		pst.setString(2, rif);
		rs = pst.executeQuery();

		if (rs.next()) {
			rit = true;
		}

		rs.close();
		pst.close();
		
		return rit;
	}
	
	public static boolean isAssigned(Connection db, String mc) throws SQLException {
		boolean rit = true;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT * FROM animale a WHERE a.microchip = ? and trashed_date is null and data_cancellazione is null");
		pst.setString(1, mc);
		rs = pst.executeQuery();

		if (rs.next()) {
			rit = false;
			
		}

		rs.close();
		pst.close();
		return rit;
	}
	public static boolean isTatuaggioAssigned(Connection db, String mc) throws SQLException {
		boolean rit = true;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT * FROM animale a WHERE a.tatuaggio = ? and trashed_date is null and data_cancellazione is null");
		pst.setString(1, mc);
		rs = pst.executeQuery();

		if (rs.next()) {
			rit = false;
		}

		rs.close();
		pst.close();
		
		return rit;
	}
	
	public static int eseguiScaricoMc(Connection db, String mc ) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE microchips SET enabled = false WHERE microchip = ?");
		pst.setString(1, mc);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}
	
	
	public static int eseguiEliminaMc(Connection db, String mc, String note ) throws SQLException {
		int resultCount = 0;
		
		java.util.Date date= new java.util.Date();
		Timestamp current_timestamp = new Timestamp(date.getTime());
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE microchips SET trashed_date = ?, note = ?  WHERE microchip = ?");
		pst.setTimestamp(1, current_timestamp);
		pst.setString(2, note);
		pst.setString(3, mc);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}
	
	
	public static int assegnaFelina(Connection db, String mc ) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE microchips SET assegnato_felina = true WHERE microchip = ?");
		pst.setString(1, mc);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}

	//usiamo false per indicare che è assegnato a canina
	public static int assegnaCanina(Connection db, String mc ) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE microchips SET assegnato_felina = false WHERE microchip = ?");
		pst.setString(1, mc);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}

	public static int eseguiCaricoMc(Connection db, String mc ) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE microchips SET enabled = false WHERE microchip = ?");
		pst.setString(1, mc);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		//microchips table
//		id = rs.getInt("id");
		microchip = rs.getString("microchip");
		asl = rs.getString("asl");
		//String serialNumber = rs.getString("assegnato");
		//assegnato = (serialNumber == null || "".equals(serialNumber)) ? false : true;
		//assegnato_felina = rs.getBoolean( "assegnato_felina" );
		//Timestamp trashed_date= rs.getTimestamp("trashed_date");
        trashed_date= rs.getTimestamp("trashed_date");
		abilitato= rs.getBoolean("enabled");
		idSpecie = rs.getInt("id_specie");
		idAnimale = rs.getInt("id_animale");
		flagSecondoMicrochip = rs.getBoolean("flag_secondo_microchip");
		enteredBy = rs.getInt("enteredby");
		idImport = rs.getInt("import_id");
        dataCaricamento= rs.getTimestamp("data_caricamento");


	}
	
	
	public static String getAslMC(int code) {
		switch ( code ) {
		case 201:
			return "AV";
		case 202:
			return "BN";
		case 203:
			return "CE";
		case 204:
			return "NA1C";
		case 205:
			return "NA2N";
		case 206:
			return "NA3S";
		case 207:
			return "SA";
		default:
			return "";
		}
	}	
	public boolean checkNumber(String mc){
		try {
			Integer.parseInt(mc.trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int getAslRif() {
		return aslRif;
	}
	public void setAslRif(int aslRif) {
		this.aslRif = aslRif;
	}
	public void setAslRif(String aslRif) {
		this.aslRif = Integer.parseInt(aslRif);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getImportId() {
		return importId;
	}
	public void setImportId(int importId) {
		this.importId = importId;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getMicrochip() {
		return microchip;
	}
	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
	public String getAsl() {
		return asl;
	}
	public void setAsl(String asl) {
		this.asl = asl;
	}
	

	//da gestire
	public static boolean isInBD(Connection db, String mc, String rif) throws SQLException
	{
		boolean ret = false;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//pst = db.prepareStatement("SELECT * FROM microchips m WHERE NOT m.assegnato_felina and m.microchip = ? AND asl = ?");

		pst = db.prepareStatement("SELECT * FROM microchips m WHERE  (m.assegnato_felina=false or m.assegnato_felina is null) and m.microchip = ? AND asl = ? ");
		
		pst.setString(1, mc);
		pst.setString(2, rif);
		rs = pst.executeQuery();

		if (rs.next()) {
			ret = true;
		}
		rs.close();
		pst.close();
		
		return ret;
	}

	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public Timestamp getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	
	public void setInformazioniImport(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT entered FROM import where import_id = ?");
		pst.setInt(1, this.idImport);
		rs = pst.executeQuery();

		if (rs.next()) {
			this.dataCaricamento = rs.getTimestamp("entered");
		}
		rs.close();
		pst.close();
	
	}

	public int getIdImport() {
		return idImport;
	}

	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}

}
