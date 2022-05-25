/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.bloccocu.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class EventoBloccoCu extends GenericBean {

	private int id ;
	private Timestamp data_evento ;

	private Timestamp data_blocco ;
	private Timestamp data_sblocco ;
	private boolean sblocco ;
	private Timestamp entered ; 
	private Timestamp modified ;
	private int enteredby ;
	private int modifiedby ;
	private String note ;
	private int quarter ;
	private String data_bloccoString;
	private String data_sbloccoString;
	
	private String tipoBlocco ;
	
	

	public String getTipoBlocco() {
		return tipoBlocco;
	}


	public void setTipoBlocco(String tipoBlocco) {
		this.tipoBlocco = tipoBlocco;
	}


	public int getId() {
		return id;
	}
	
	
	public int getQuarter() {
		return quarter;
	}


	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}
	public Timestamp getData_blocco() {
		return data_blocco;
	}

	public String getData_bloccoString() {
		if (data_blocco !=null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_bloccoString = sdf.format(this.data_blocco);
		}
		
		return data_bloccoString;
	}

	public String getData_sbloccoString() {
		if (data_sblocco !=null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_sbloccoString = sdf.format(this.data_sblocco);
		}
		
		return data_sbloccoString;
	}

	public Timestamp getData_sblocco() {
		return data_sblocco;
	}
	public void setData_sblocco(Timestamp data_sblocco) {
		this.data_sblocco = data_sblocco;
	}

	public void setData_sblocco(String data_sblocco) throws ParseException {
		if (data_sblocco !=null && ! "".equalsIgnoreCase(data_sblocco) )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_sblocco = new Timestamp(sdf.parse(data_sblocco).getTime());
		}

	}

	public void setData_blocco(Timestamp data_blocco) {
		this.data_blocco = data_blocco;
	}
	public void setData_blocco(String data_blocco) throws ParseException {
		if (data_blocco !=null && ! "".equalsIgnoreCase(data_blocco) )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_blocco = new Timestamp(sdf.parse(data_blocco).getTime());
		}

	}


	public boolean isSblocco() {
		return sblocco;
	}
	public void setSblocco(boolean sblocco) {
		this.sblocco = sblocco;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}



	public void insert_blocco (Connection db,ActionContext context) throws SQLException
	{
		
	    int id =  DatabaseUtils.getNextSeq(db, context,"evento_blocco_controlli","id");

		String insert = "insert into evento_blocco_controlli (data_blocco,data_sblocco,note ,enteredby,entered,id,tipo_blocco) values (?,?,?,?,current_timestamp,?,?)";
		PreparedStatement pst = db.prepareStatement(insert);
		pst.setTimestamp(1, data_blocco);
		pst.setTimestamp(2, data_sblocco);
		pst.setString(3, note);
		pst.setInt(4,enteredby);
		pst.setInt(5,id);
		pst.setString(6, tipoBlocco);
		pst.execute();


		String insert2 = "insert into evento_blocco (data_blocco,data_sblocco,id_evento_blocco_controlli,tipo_blocco) values (?,?,?,?)";
		PreparedStatement pst2 = db.prepareStatement(insert2);
		pst2.setTimestamp(1, data_blocco);
		pst2.setTimestamp(2, data_sblocco);
		pst2.setInt(3,id);
		pst2.setString(4, tipoBlocco);
		pst2.execute();
	}

	public void insert_sblocco (Connection db) throws SQLException
	{
		String insert = "update  evento_blocco_controlli set sblocco = ?,modified=current_timestamp,modifiedby=? where id = ? ";
		PreparedStatement pst = db.prepareStatement(insert);
		pst.setBoolean(1, true);
		pst.setInt(2,modifiedby);
		pst.setInt(3,id);
		pst.execute();

		String insert2 = "delete from evento_blocco where id_evento_blocco_controlli = ? ";
		PreparedStatement pst2 = db.prepareStatement(insert2);
		pst2.setInt(1,id);
		pst2.execute();



	}


	public void queryRecord (Connection db) throws SQLException
	{
		String sel ="select evento_blocco_controlli.* from evento_blocco_controlli join evento_blocco on evento_blocco_controlli.id = evento_blocco.id_evento_blocco_controlli ";
		PreparedStatement pst = db.prepareStatement(sel);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
		{
			this.buildRecord(rs);
		}
		quarter =getQuarter(new Date (System.currentTimeMillis()));


	}

	

	private void buildRecord (ResultSet rs)throws SQLException
	{
		id = rs.getInt("id"); 
		data_blocco =rs.getTimestamp("data_blocco");
		sblocco =rs.getBoolean("sblocco");
		entered =rs.getTimestamp("entered");
		modified =rs.getTimestamp("modified");
		enteredby = rs.getInt("enteredby"); 
		modifiedby =rs.getInt("modifiedby");
		note = rs.getString("note") ;
		data_sblocco = rs.getTimestamp("data_sblocco");
		tipoBlocco=rs.getString("tipo_blocco");
	}


	
	/**
	 * prendeininput una data e ritorna l'indice deltrimestre
	 * precedente in cuirientra la data.
	 * ESEMPIO input 05/07/2014 -> 3 trimestre
	 * La funziona ritorna indice 2 => secondo trimestre
	 *  
	 * @param date
	 * @return
	 */
	private  int getQuarter(java.util.Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int[] months = { 3, 6, 9, 12 };
		int count = 0;
		do {
			calendar.set(year, months[count++], 1);
			java.util.Date tempDt = calendar.getTime();
			if(date.compareTo(tempDt)<=0)
			{
				quarter = count ;
			
				return count;
			}
		} while(count<=months.length);
		return 0;
	} 


	public void setDataBloccoSblocco(Date dataInput) throws ParseException
	{

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(dataInput);

		int quarter = getQuarter(dataInput);
		String currentQuarterBeginDate = "";
		String currentQuarterEndDate = "";
		String fromDt="";
		String toDt="";
		switch (quarter)
		{
		case 1 : 
		{
			currentQuarterBeginDate = "01/01/"+calendar.get(Calendar.YEAR);
			currentQuarterEndDate = "31/03/"+calendar.get(Calendar.YEAR);
			break ;
		}
		case 2 : {
			currentQuarterBeginDate = "01/01/"+calendar.get(Calendar.YEAR);
			currentQuarterEndDate = "30/06/"+calendar.get(Calendar.YEAR);
			break ;
		}
		case 3 :{
			currentQuarterBeginDate = "01/01/"+calendar.get(Calendar.YEAR);
			currentQuarterEndDate = "30/09/"+calendar.get(Calendar.YEAR);
			break ;
		}
		case 4:
		{
			currentQuarterBeginDate = "01/01/"+calendar.get(Calendar.YEAR);
			currentQuarterEndDate = "30/09/"+calendar.get(Calendar.YEAR);
			break ;
		}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		data_blocco = new Timestamp(sdf.parse(currentQuarterBeginDate).getTime());
		data_sblocco = new Timestamp(sdf.parse(currentQuarterEndDate).getTime());
		
	}

}
