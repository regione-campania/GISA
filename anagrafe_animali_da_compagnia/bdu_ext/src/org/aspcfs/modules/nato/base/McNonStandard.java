/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nato.base;


import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;


import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class McNonStandard implements Serializable{

	/**
	 * 
	 */
	private transient static Logger logger = Logger.getLogger("MainLogger");
	private static final long serialVersionUID = -8376981311926754554L;
	static SimpleDateFormat from	= new SimpleDateFormat( "dd/MM/yyyy" );
	static SimpleDateFormat to		= new SimpleDateFormat( "yyyy-MM-dd" );
	
	private String serial_number;
	private int razza=1;
	private int mantello=1;
	private int taglia=1;
	private java.sql.Date data_di_nascita;
	private java.sql.Date data_esame1;
	private String passaporto;
	private String nome_proprietario;
	private String cognome_proprietario;
	private String codice_fiscale;
	private String indirizzo;
	private String tipo_esame1;
	private int esito_esame1=-1;
	/**
	 * 
	 */
	private String comune;
	private String notes;
	private String data_esame_str1;

	public McNonStandard(String serial_number, int razza, int mantello,
			int taglia, java.sql.Date data_di_nascita, String passaporto,
			String nome_proprietario, String cognome_proprietario,
			String codice_fiscale, String indirizzo, String comune,
			String notes, String tipo_esame1, int esito_esame1, java.sql.Date data_esame1) {
		super();
		this.serial_number = serial_number;
		this.razza = razza;
		this.mantello = mantello;
		this.taglia = taglia;
		this.data_di_nascita = data_di_nascita;
		this.passaporto = passaporto;
		this.nome_proprietario = nome_proprietario;
		this.cognome_proprietario = cognome_proprietario;
		this.codice_fiscale = codice_fiscale;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.notes = notes;
		this.tipo_esame1 = tipo_esame1;
		this.esito_esame1 = esito_esame1;
		this.data_esame1 = data_esame1;
	}

	public static SimpleDateFormat getFrom() {
		return from;
	}

	public McNonStandard()
	{
	
	}
	public static void setFrom(SimpleDateFormat from) {
		McNonStandard.from = from;
	}

	public static SimpleDateFormat getTo() {
		return to;
	}

	public static void setTo(SimpleDateFormat to) {
		McNonStandard.to = to;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public java.sql.Date getData_di_nascita() {
		return data_di_nascita;
	}

	public void setData_di_nascita(java.sql.Date data_di_nascita) {
		this.data_di_nascita = data_di_nascita;
	}

	public String getNome_proprietario() {
		return nome_proprietario;
	}

	public void setNome_proprietario(String nome_proprietario) {
		this.nome_proprietario = nome_proprietario;
	}

	public String getCognome_proprietario() {
		return cognome_proprietario;
	}

	public void setCognome_proprietario(String cognome_proprietario) {
		this.cognome_proprietario = cognome_proprietario;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTipo_esame1() {
		return tipo_esame1;
	}

	public void setTipo_esame1(String tipo_esame1) {
		this.tipo_esame1 = tipo_esame1;
	}

	public int getEsito_esame1() {
		return esito_esame1;
	}

	public void setEsito_esame1(int esito_esame1) {
		this.esito_esame1 = esito_esame1;
	}

	public Date getData_esame1() {
		return data_esame1;
	}
	
	public void setData_esame1(java.sql.Date data_esame1) {
		this.data_esame1 = data_esame1;
	}
	public void setData_esame1(String data_esame1) {
		this.data_esame_str1 = data_esame1;
	}
	
	public String getData_esame_str1() {
		return this.data_esame_str1 ;
	}
	
	public int getRazza() {
		return razza;
	}

	public int getMantello() {
		return mantello;
	}

	public int getTaglia() {
		return taglia;
	}

	public String getPassaporto() {
		return passaporto;
	}
	public void setPassaporto1(String passaporto) {
	
		this.passaporto=passaporto;
	}
	

	public McNonStandard (String serial_number,int razza,int mantello, int taglia,	Date data_di_nascita,String passaporto,String nome_proprietario,
String cognome_proprietario,String codice_fiscale,String indirizzo,String comune,String notes,String tipo_esame1,int esito_esame1,
java.sql.Date data_esame1)
	{
		
		this.setNotes(notes);
		this.setDataEs1(data_esame1);
		this.setIndirizzo(indirizzo);
		this.setComune(comune);
		this.setRazza(razza);
		this.setMantello(mantello);
		this.setTaglia(taglia);
		this.setDataNascita(data_di_nascita);
		this.setPassaporto(passaporto);
		this.setMicrochip(serial_number);
		this.setNome_Proprietario(nome_proprietario);
		this.setCognome_Proprietario(cognome_proprietario);
		this.setCF(codice_fiscale);
		this.setTipoEs1(tipo_esame1);
		this.setEsito1(esito_esame1);
		
	}
	
		private void setNome_Proprietario(String nome_proprietario) {
		// TODO Auto-generated method stub
		this.nome_proprietario=nome_proprietario;
		
	}

	private void setCognome_Proprietario(String cognome_proprietario) {
		// TODO Auto-generated method stub
		this.cognome_proprietario=cognome_proprietario;
	}

	public void setPassaporto(String passaporto) {
		// TODO Auto-generated method stub
		this.passaporto=passaporto;
		
	}

	private void setMicrochip(String serial_number) {
		// TODO Auto-generated method stub
		this.serial_number=serial_number;
		
	}

	private void setCF(String codice_fiscale) {
		// TODO Auto-generated method stub
		this.codice_fiscale=codice_fiscale;
	}

	private void setTipoEs1(String tipo_esame1) {
		// TODO Auto-generated method stub
		this.tipo_esame1=tipo_esame1;
		
	}

	private void setEsito1(int esito_esame1) {
		// TODO Auto-generated method stub
		this.esito_esame1=esito_esame1;
		
	}
	private void setDataNascita(Date data_di_nascita) {
		// TODO Auto-generated method stub
		this.data_di_nascita=(java.sql.Date) data_di_nascita;
		
	}

	public void setTaglia(int taglia) {
		// TODO Auto-generated method stub
		this.taglia=taglia;
		
	}

	public void setMantello(int mantello) {
		// TODO Auto-generated method stub
		this.mantello=mantello;
	}

	public void setRazza(int razza) {
		// TODO Auto-generated method stub
		this.razza=razza;
		
	}

	public void setDataEs1(Date data_esame1) {
		// TODO Auto-generated method stub
		this.data_esame1 =(java.sql.Date) data_esame1;
	}
	
	public Date getDataEs1() {
		return data_esame1;
	}
	
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	private static String data(String data) throws ParseException {
		return to.format( from.parse( data ) );
	}

	public void insert(Connection db){
		try{
			int  id = DatabaseUtils.getNextSeq(db, "mc_id_seq");
if(serial_number!=null && !serial_number.equals(""))
	
{
		String sql="INSERT INTO mc_non_standard(id, serial_number, razza, mantello, taglia, data_di_nascita," +
		"passaporto, nome_proprietario, cognome_proprietario, codice_fiscale," +
		" indirizzo, comune, tipo_esame1, data_esame1, esito_esame1, notes)" +
		"	   VALUES "+"("+id+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
java.sql.PreparedStatement pst= db.prepareStatement(sql);

			 	pst.setString(1,serial_number);	
			 	pst.setInt(2, razza);
			 	pst.setInt(3, mantello);
			  	pst.setInt(4,taglia);
			 	pst.setDate(5,data_di_nascita);
			 	pst.setString(6,passaporto);
			 	pst.setString(7,nome_proprietario);
			 	pst.setString(8,cognome_proprietario);
			 	pst.setString(9,codice_fiscale);
			 	pst.setString(10,indirizzo);
			 	pst.setString(11,comune);
			 	pst.setString(12,tipo_esame1);
			 	pst.setDate(13,(java.sql.Date) data_esame1);
			 	pst.setInt(14,esito_esame1);
			 	pst.setString(15,notes);
				pst.execute();
		}
		}
		catch(Exception e){
	 		  logger.severe("[CANINA] - EXCEPTION nel metodo insert della classe McNonstandard");
			e.printStackTrace();
		}
	}

	
}
