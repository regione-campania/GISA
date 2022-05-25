/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

import org.aspcfs.modules.noscia.LineaAttivita;
import org.aspcfs.modules.noscia.Metadato;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Stabilimento;

public class OggettoPerStorico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int altId;
	private String descrizione_asl_print;
	private String tipo_attivita_stabilimento;
	private LinkedHashMap<String, String> valoriAnagrafica;
	private LinkedHashMap<String, String> valoriLinee;
	private LinkedHashMap<String, String> valoriExtra;
	private LinkedHashMap<String, String> valoriEstesi;
	private String codiceLinea;
	private List<Metadato> lineaattivita;

	public OggettoPerStorico(){}
	
	public OggettoPerStorico(int altId, Connection db) throws IndirizzoNotFoundException{

		this.altId = altId;
	    
	    try{
	        
	    	Stabilimento s = null;
	    	s = new Stabilimento(db, altId, true);
	    	
	    	s.getDatiByAltId(db, altId);
	    	this.descrizione_asl_print = s.getAsl();
	    	this.tipo_attivita_stabilimento = s.getTipoAttivitaDesc();
	    	
	    	LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
	        valoriAnagrafica = s.getValoriAnagrafica(db,altId);
	        this.valoriAnagrafica = valoriAnagrafica;
	        
	        LinkedHashMap<String, String> valoriLinee = new LinkedHashMap<String, String>();
	        valoriLinee = s.getValoriAnagraficaLinee(db, altId);	        
	        this.valoriLinee = valoriLinee;

	        LinkedHashMap<String, String> valoriExtra = new LinkedHashMap<String, String>();
	        valoriExtra = s.getValoriAnagraficaExtra(db,altId);
	        this.valoriExtra = valoriExtra;
	        
	        LinkedHashMap<String, String> valoriEstesi = new LinkedHashMap<String, String>();
	        valoriEstesi = s.getValoriAnagraficaEstesi(db,altId);
	        this.valoriEstesi = valoriEstesi;

	        String codice = "TEST-SCIA";

	        String desc_linea = "";
	        //CONTROLLO SE QUELLA LINEA E NO SCIA
        	LineaProduttiva lin = (LineaProduttiva) s.getListaLineeProduttive().get(0);
        	if(lin.getFlags().isNoScia())
        	{
        		codice = lin.getCodice();
        		desc_linea = lin.getNorma() + "-> " + lin.getDescrizione_linea_attivita();
        	} else {
        		codice = s.RecuperaTemplateOsaDaLinea(db, lin);
        		if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 1){
        			codice = "SCIA-FISSO";
        		} else if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 2){
        			codice = "SCIA-MOBILE";
        		}
        	}

	        this.codiceLinea = codice;

	        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        this.lineaattivita = listaElementi;
	        
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	public OggettoPerStorico(int altId, String descrizione_asl_print, String tipo_attivita_stabilimento,
							LinkedHashMap<String,String> valoriAnagrafica, LinkedHashMap<String, String> valoriLinee,
							LinkedHashMap<String, String> valoriExtra, String codiceLinea, List<Metadato> lineaattivita){
		
		this.setAltId(altId);
		this.setCodiceLinea(codiceLinea);
		this.setDescrizione_asl_print(descrizione_asl_print);
		this.setTipo_attivita_stabilimento(tipo_attivita_stabilimento);
		this.setValoriAnagrafica(valoriAnagrafica);
		this.setValoriLinee(valoriLinee);
		this.setValoriExtra(valoriExtra);
		this.setLineaattivita(lineaattivita);
	}
	
	public byte[] oggettoToStream(){
		
		byte[] stream = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
			stream = baos.toByteArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stream;
		
	}
	
	public void streamToOggetto(byte[] stream){
		
		OggettoPerStorico output = new OggettoPerStorico();
		
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(stream);
			ObjectInputStream ois = new ObjectInputStream(bais);
			output = (OggettoPerStorico) ois.readObject();
			this.setAltId(output.getAltId());
			this.setCodiceLinea(output.getCodiceLinea());
			this.setDescrizione_asl_print(output.getDescrizione_asl_print());
			this.setTipo_attivita_stabilimento(output.getTipo_attivita_stabilimento());
			this.setValoriAnagrafica(output.getValoriAnagrafica());
			this.setValoriLinee(output.getValoriLinee());
			this.setValoriExtra(output.getValoriExtra());
			this.setValoriEstesi(output.getValoriEstesi());
			this.setLineaattivita(output.getLineaattivita());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void salvaStoricoEvento(Connection db, int stabId, OggettoPerStorico stab_pre_modifica) throws SQLException, IndirizzoNotFoundException{
		
		//recupero altId OSA
		String sql = "select alt_id from opu_stabilimento where id = ?";
		PreparedStatement st = db.prepareStatement(sql);
	    st.setInt(1, stabId);
	    ResultSet rs = st.executeQuery();
	    int altId = -1;
		while (rs.next()){
			altId = rs.getInt("alt_id");
		}
		OggettoPerStorico stab_post_modifica = new OggettoPerStorico(altId, db);
		
		//recupero ultimo evento sull OSA 
		sql = "select id from eventi_su_osa where alt_id = ? order by entered desc limit 1";
		st = db.prepareStatement(sql);
		st.setInt(1, altId);
		rs = st.executeQuery();
		int id_evento = -1;
		while (rs.next()){
			id_evento = rs.getInt("id");
		}
		
		//salvo lo stato dell OSA prima e dopo l evento effettuato
		sql = "update eventi_su_osa set pre_evento = ?, post_evento = ? where id =? ";
		st = db.prepareStatement(sql);
		if(stab_pre_modifica == null){
			st.setBytes(1, stab_post_modifica.oggettoToStream());
		} else {
			st.setBytes(1, stab_pre_modifica.oggettoToStream());
		}
		st.setBytes(2, stab_post_modifica.oggettoToStream());
		st.setInt(3, id_evento);
		st.execute();
		
	}
	
	
	
	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

	public String getDescrizione_asl_print() {
		return descrizione_asl_print;
	}

	public void setDescrizione_asl_print(String descrizione_asl_print) {
		this.descrizione_asl_print = descrizione_asl_print;
	}

	public String getTipo_attivita_stabilimento() {
		return tipo_attivita_stabilimento;
	}

	public void setTipo_attivita_stabilimento(String tipo_attivita_stabilimento) {
		this.tipo_attivita_stabilimento = tipo_attivita_stabilimento;
	}

	public LinkedHashMap<String, String> getValoriAnagrafica() {
		return valoriAnagrafica;
	}

	public void setValoriAnagrafica(LinkedHashMap<String, String> valoriAnagrafica) {
		this.valoriAnagrafica = valoriAnagrafica;
	}

	public LinkedHashMap<String, String> getValoriLinee() {
		return valoriLinee;
	}

	public void setValoriLinee(LinkedHashMap<String, String> valoriLinee) {
		this.valoriLinee = valoriLinee;
	}

	public LinkedHashMap<String, String> getValoriExtra() {
		return valoriExtra;
	}

	public void setValoriExtra(LinkedHashMap<String, String> valoriExtra) {
		this.valoriExtra = valoriExtra;
	}

	public String getCodiceLinea() {
		return codiceLinea;
	}

	public void setCodiceLinea(String codiceLinea) {
		this.codiceLinea = codiceLinea;
	}

	public List<Metadato> getLineaattivita() {
		return lineaattivita;
	}

	public void setLineaattivita(List<Metadato> lineaattivita) {
		this.lineaattivita = lineaattivita;
	}

	public LinkedHashMap<String, String> getValoriEstesi() {
		return valoriEstesi;
	}

	public void setValoriEstesi(LinkedHashMap<String, String> valoriEstesi) {
		this.valoriEstesi = valoriEstesi;
	}
	
	

}
