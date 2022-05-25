/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.controller.Tree;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class PianoMonitoraggio extends org.aspcfs.modules.troubletickets.base.Ticket
{
	private int code 				;
	private int tipo 				;
	private String descrizione 		;
	private boolean enabled 		;
	private int asl 				;
	private int enteredby			;
	private int modifiedby			;
	private String descrizione_tipo ;
	private String sezione 			;
	private int asl_pianificazione 	;
	private int cu_pianificati		;
	private int cu_eseguiti 		;
	private int id_padre ;
	private int ordinamentoPadri ;
	private int ordinamentoFigli ; 
	private String tipoInserimento ;
	private String codiceEsame;
	private int level;
	private int idIndicatore ;
	private int codiceInterno ;
	
	private String tipoAttivita ;
	private String alias ;
   
	private double coefficiente ;
	private String note="";
	private String codice ;

	
    
    
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public double getCoefficiente() {
		return coefficiente;
	}
	public void setCoefficiente(double coefficiente) {
		this.coefficiente = coefficiente;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getCodiceEsame() {
		return codiceEsame;
	}
	public void setCodiceEsame(String codiceEsame) {
		this.codiceEsame = codiceEsame;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getIdIndicatore() {
		return idIndicatore;
	}
	public void setIdIndicatore(int idIndicatore) {
		this.idIndicatore = idIndicatore;
	}
	public int getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getTipoInserimento() {
		return tipoInserimento;
	}
	public void setTipoInserimento(String tipoInserimento) {
		this.tipoInserimento = tipoInserimento;
	}
	public int getOrdinamentoPadri() {
		return ordinamentoPadri;
	}
	public void setOrdinamentoPadri(int ordinamentoPadri) {
		this.ordinamentoPadri = ordinamentoPadri;
	}
	public int getOrdinamentoFigli() {
		return ordinamentoFigli;
	}
	public void setOrdinamentoFigli(int ordinamentoFigli) {
		this.ordinamentoFigli = ordinamentoFigli;
	}
	Tree listaMatrici = new Tree();


	public Tree getListaMatrici() {
		return listaMatrici;
	}
	public void setListaMatrici(Tree listaMatrici) {
		this.listaMatrici = listaMatrici;
	}
	private ArrayList<PianoMonitoraggio> lista_sottopiani = new ArrayList<PianoMonitoraggio>();

	public int getAsl_pianificazione() {
		return asl_pianificazione;
	}
	public void setAsl_pianificazione(int asl_pianificazione) {
		this.asl_pianificazione = asl_pianificazione;
	}
	public int getCu_pianificati() {
		return cu_pianificati;
	}
	public void setCu_pianificati(int cu_pianificati) {
		this.cu_pianificati = cu_pianificati;
	}
	public int getCu_eseguiti() {
		return cu_eseguiti;
	}
	public void setCu_eseguiti(int cu_eseguiti) {
		this.cu_eseguiti = cu_eseguiti;
	}
	public String getDescrizione_tipo() {
		return descrizione_tipo;
	}
	public void setDescrizione_tipo(String descrizione_tipo) {
		this.descrizione_tipo = descrizione_tipo;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getAsl() {
		return asl;
	}
	public void setAsl(int asl) {
		this.asl = asl;
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


	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
	public ArrayList<PianoMonitoraggio> getLista_sottopiani() {
		return lista_sottopiani;
	}
	public void setLista_sottopiani(ArrayList<PianoMonitoraggio> lista_sottopiani) {
		this.lista_sottopiani = lista_sottopiani;
	}
	public void delete(Connection db)
	{
		int i = 0 ;
		String insert = "update lookup_piano_monitoraggio set enabled = false,modified=current_timestamp, modifiedby =? where code = ? or id_padre=?  " ;
		try
		{
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(++i, modifiedby) ;
			pst.setInt(++i, code) ;
			pst.setInt(++i, code) ;
			pst.execute();
		}
		catch(SQLException e)
		{

		}

	}
	public void deleteConfiguratore(Connection db)
	{
		int i = 0 ;
		String insert = "update lookup_piano_monitoraggio_configuratore set enabled = false,modified=current_timestamp, modifiedby =? where code = ? or id_padre=?  " ;
		try
		{
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(++i, modifiedby) ;
			pst.setInt(++i, code) ;
			pst.setInt(++i, code) ;
			pst.execute();
		}
		catch(SQLException e)
		{

		}

	}

	public void buildSottopiani(Connection db)
	{
		try
		{
			lista_sottopiani.clear();
			PreparedStatement pst = db.prepareStatement("select t.* ,lspm.description as sezione,lspm.code as codice_sezione  from lookup_piano_monitoraggio t  join lookup_sezioni_piani_monitoraggio lspm on (t.id_Sezione =  lspm.code)  where t.enabled = true and id_padre = "+code+ " order by ordinamento_figli") ;
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				PianoMonitoraggio p = new PianoMonitoraggio(rs);
				lista_sottopiani.add(p);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void buildSottopianiConfiguratore(Connection db)
	{
		try
		{
			lista_sottopiani.clear();
			PreparedStatement pst = db.prepareStatement("select t.* ,lspm.description as sezione,lspm.code as codice_sezione  from lookup_piano_monitoraggio_configuratore t  join lookup_sezioni_piani_monitoraggio lspm on (t.id_Sezione =  lspm.code)  where t.enabled = true and id_padre = "+code+ " order by ordinamento_figli") ;
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				PianoMonitoraggio p = new PianoMonitoraggio(rs);
				lista_sottopiani.add(p);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}



	public void queryRecord(Connection db,int idPiano)
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("select t.* ,lspm.description as sezione,lspm.code as codice_sezione  from lookup_piano_monitoraggio t  join lookup_sezioni_piani_monitoraggio lspm on (t.id_Sezione =  lspm.code)  where t.enabled = true and t.code=  "+idPiano+ " order by code") ;
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				buildRecord(rs);
				buildSottopiani(db);

			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void queryRecordConfiguratore(Connection db,int idPiano)
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("select t.* ,lspm.description as sezione,lspm.code as codice_sezione  from lookup_piano_monitoraggio_configuratore t  join lookup_sezioni_piani_monitoraggio lspm on (t.id_Sezione =  lspm.code)  where t.enabled = true and t.code=  "+idPiano+ " order by code") ;
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				buildRecord(rs);
				buildSottopianiConfiguratore(db);

			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}



	public PianoMonitoraggio(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	public PianoMonitoraggio() throws SQLException {

	}

	public int getId_padre() {
		return id_padre;
	}
	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}

	public void setId_padre(String id_padre) {
		if(id_padre != null && ! "".equals(id_padre))
			this.id_padre = Integer.parseInt(id_padre);
	}
	protected void buildRecord(ResultSet rs) throws SQLException {
		//ticket table
		this.setCode(rs.getInt("code"))								;
		this.setDescrizione(rs.getString("description"))			;
		this.setEnabled(rs.getBoolean("enabled"))					;
		this.setAsl(rs.getInt("site_id"))							;
		this.setId_padre(rs.getInt("id_padre")) ;


		this.setOrdinamentoPadri(rs.getInt("ordinamento"))			;
		this.setOrdinamentoFigli(rs.getInt("ordinamento_figli"))	;
		this.setCodiceInterno(rs.getInt("codice_interno"));	

		try
		{
			this.setCodice(rs.getString("codice"));
		}
		catch(Exception e)
		{
			
		}
		try
		{
			this.setAlias(rs.getString("alias"));
		}
		catch(Exception e)
		{
			
		}
		
		
		
		try
		{
			this.setLevel(rs.getInt("level"));
			this.setCodiceEsame(rs.getString("codice_esame"));
			this.setIdIndicatore(rs.getInt("id_indicatore"));
			this.setTipoAttivita(rs.getString("tipo_attivita"));
			
			this.setCoefficiente(rs.getDouble("coefficiente"));
			
			 
		}catch(Exception e)
		{
			
		}

	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}
