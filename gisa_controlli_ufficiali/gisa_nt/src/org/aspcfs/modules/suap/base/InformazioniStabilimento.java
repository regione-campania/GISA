/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.darkhorseventures.framework.actions.ActionContext;

public class InformazioniStabilimento implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TIPO_ATTIVITA_FISSA = 1 ;
	public static final int TIPO_ATTIVITA_MOBILE = 2 ;
	public static final int TIPO_ATTIVITA_DISTRIBUTORI = 3 ;
	
	private int id ;
	private int id_opu_rel_stab_lp ;
	public int getId_opu_rel_stab_lp() {
		return id_opu_rel_stab_lp;
	}
	public void setId_opu_rel_stab_lp(int id_opu_rel_stab_lp) {
		this.id_opu_rel_stab_lp = id_opu_rel_stab_lp;
	}
	private String codice_interno ;
	private String codice_registrazione ;
	private boolean flag_vendita_canili ;
	private Timestamp dataPresentazioneScia ;
	private Timestamp dataInizioCarattere ;
	private Timestamp datafineCarattere ;
	private int tipoAttivita ;
	private int carattere ;
	private int servizioCompetente ;
	private String domicilioDigitale ;
	
	

	public InformazioniStabilimento ()
	{}
	public InformazioniStabilimento (Connection db , int idStabilimento)
	{
		try
		{
			PreparedStatement pst = null ;
			pst = db.prepareStatement("select * from opu_informazioni_852 where id_opu_rel_stab_lp = ? ");
			pst.setInt(1, idStabilimento);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				buildRecord(rs);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
 
	}
	
	public InformazioniStabilimento (ActionContext context) throws ParseException
	{
		
		
		int idNorma = -1 ;
		if ("AziendeAgricoleOpu".equalsIgnoreCase(context.getAction().getActionName()))
			idNorma = LineaProduttiva.NORMA_AZIENDE_AGRICOLE;
		else
			if ("Stabilimenti852".equalsIgnoreCase(context.getAction().getActionName()))
				idNorma = LineaProduttiva.NORMA_STABILIMENTI_852;
		
		if (idNorma==1)
		{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
		this.setCodice_interno(context.getParameter("codice_interno"));
		if (context.getParameter("flag_vendita")!= null)
			this.setFlag_vendita_canili(true);
		if (context.getParameter("dataPresentazioneScia")!= null && !"".equals(context.getParameter("dataPresentazioneScia")))
		{
			this.setDataPresentazioneScia(new Timestamp(sdf.parse(context.getParameter("dataPresentazioneScia")).getTime()));
		}
		
		
		if (context.getParameter("dateI")!= null && !"".equals(context.getParameter("dateI")))
		{
			this.setDataInizioCarattere(new Timestamp(sdf.parse(context.getParameter("dateI")).getTime()));
		}
		
		if (context.getParameter("dateF")!= null && !"".equals(context.getParameter("dateF")))
		{
			this.setDatafineCarattere(new Timestamp(sdf.parse(context.getParameter("dateF")).getTime()));
		}
		if (context.getParameter("tipo_attivita")!= null)
			this.setTipoAttivita(Integer.parseInt(context.getParameter("tipo_attivita")));
		if (context.getParameter("carattere") != null)
		this.setCarattere(Integer.parseInt(context.getParameter("carattere")));
		if (context.getParameter("servizio_competente") != null)
		this.setServizioCompetente(Integer.parseInt(context.getParameter("servizio_competente")));
		this.setDomicilioDigitale(context.getParameter("domicilio_digitale"));
		}

	}
	
	
	

	
	public Timestamp getDataInizioCarattere() {
		return dataInizioCarattere;
	}
	public void setDataInizioCarattere(Timestamp dataInizioCarattere) {
		this.dataInizioCarattere = dataInizioCarattere;
	}
	public Timestamp getDatafineCarattere() {
		return datafineCarattere;
	}
	public void setDatafineCarattere(Timestamp datafineCarattere) {
		this.datafineCarattere = datafineCarattere;
	}
	public int getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(int tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public int getCarattere() {
		return carattere;
	}
	public void setCarattere(int carattere) {
		this.carattere = carattere;
	}
	public int getServizioCompetente() {
		return servizioCompetente;
	}
	public void setServizioCompetente(int servizioCompetente) {
		this.servizioCompetente = servizioCompetente;
	}
	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}
	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}
	public Timestamp getDataPresentazioneScia() {
		return dataPresentazioneScia;
	}

	public void setDataPresentazioneScia(Timestamp dataPresentazioneScia) {
		this.dataPresentazioneScia = dataPresentazioneScia;
	}
	public void setDataPresentazioneScia(String dataPresentazioneScia) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataPresentazioneScia != null && ! "".equals(dataPresentazioneScia))
		{
			this.dataPresentazioneScia =  new Timestamp(sdf.parse(dataPresentazioneScia).getTime());
		}
		
	}
	
	public String getDataPresentazioneSciaString() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = "" ;
		if (dataPresentazioneScia != null && ! "".equals(dataPresentazioneScia))
		{
			data =  sdf.format(new Date(dataPresentazioneScia.getTime()));
		}
		return data ;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id != null && !"".equals(id))
			this.id = Integer.parseInt(id);
	}


	
	public String getCodice_interno() {
		return codice_interno;
	}
	public void setCodice_interno(String codice_interno) {
		this.codice_interno = codice_interno;
	}
	public String getCodice_registrazione() {
		return codice_registrazione;
	}
	public void setCodice_registrazione(String codice_registrazione) {
		this.codice_registrazione = codice_registrazione;
	}

	public boolean isFlag_vendita_canili() {
		return flag_vendita_canili;
	}
	public void setFlag_vendita_canili(boolean flag_vendita_canili) {
		this.flag_vendita_canili = flag_vendita_canili;
	}

	public void insert (Connection db)
	{

		String sql = "INSERT INTO opu_informazioni_852 (id_opu_rel_stab_lp,codice_registrazione,codice_interno," +
		"flag_vendita_canili,data_presentazione_scia,tipo_attivita,carattere,servizio_competente,data_inizio_temporanea,data_fine_temporanea,domicilio_digitale)" +
		" values (?,?,?,?,?,?,?,?,?,?,?)"	;
		try
		{
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(++i,id_opu_rel_stab_lp );
			pst.setString(++i,codice_registrazione );
			pst.setString(++i,codice_interno );
			pst.setBoolean(++i,flag_vendita_canili );
			pst.setTimestamp(++i, dataPresentazioneScia);
			pst.setInt(++i,tipoAttivita );
			pst.setInt(++i,carattere );
			pst.setInt(++i,servizioCompetente );
			pst.setTimestamp(++i, dataInizioCarattere);
			pst.setTimestamp(++i, datafineCarattere);
			pst.setString(++i, domicilioDigitale);
			pst.execute();
		
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void buildRecord(ResultSet rs) throws SQLException
	{

		id = rs.getInt("id");
		id_opu_rel_stab_lp = rs.getInt("id_opu_rel_stab_lp");
		codice_interno  = rs.getString("codice_interno");
		codice_registrazione = rs.getString("codice_registrazione");
		flag_vendita_canili = rs.getBoolean("flag_vendita_canili");
		this.setDataPresentazioneScia(rs.getTimestamp("data_presentazione_scia"));
		tipoAttivita = rs.getInt("tipo_attivita");
		carattere = rs.getInt("carattere");
		servizioCompetente = rs.getInt("servizio_competente");
		domicilioDigitale = rs.getString("domicilio_digitale"); 
		dataInizioCarattere = rs.getTimestamp("data_inizio_temporanea");
		datafineCarattere = rs.getTimestamp("data_fine_temporanea"); 
 
	}
 
}
