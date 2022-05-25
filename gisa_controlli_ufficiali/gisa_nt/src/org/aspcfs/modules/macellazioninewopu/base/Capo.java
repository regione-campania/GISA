/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninewopu.utils.MacelliUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;


public class Capo extends GenericBean
{
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private static SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
	private static Logger logger = Logger.getLogger("MainLogger");
	
	private static final long serialVersionUID = 8313006891554941893L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private static final int RAZZA_BOVINA   = 1;
	private static final int RAZZA_BUFALINA = 5;
	
	private String		cd_matricola;
	private int			cd_specie;
	private int			cd_categoria_bovina;
	private int			cd_categoria_bufalina;
	private Timestamp	cd_data_nascita;
	private boolean		cd_maschio;
	private int			cd_id_razza;
	private String		cd_razza_altro;
	private int			cd_bse;

	public String color = "#FFA500";
		
	private String rag_soc_azienda_prov ;
	private String denominazione_asl_azienda_prov ;
	private String id_asl_azienda_prov ;
	private String cod_asl_azienda_prov ;
	
	
	
	public String getCod_asl_azienda_prov() {
		return cod_asl_azienda_prov;
	}

	public void setCod_asl_azienda_prov(String cod_asl_azienda_prov) {
		this.cod_asl_azienda_prov = cod_asl_azienda_prov;
	}

	public String getRag_soc_azienda_prov() {
		return rag_soc_azienda_prov;
	}

	public void setRag_soc_azienda_prov(String rag_soc_azienda_prov) {
		this.rag_soc_azienda_prov = rag_soc_azienda_prov;
	}

	public String getDenominazione_asl_azienda_prov() {
		return denominazione_asl_azienda_prov;
	}

	public void setDenominazione_asl_azienda_prov(String denominazione_asl_azienda_prov) {
		this.denominazione_asl_azienda_prov = denominazione_asl_azienda_prov;
	}

	public String getId_asl_azienda_prov() {
		return id_asl_azienda_prov;
	}

	public void setId_asl_azienda_prov(String id_asl_azienda_prov) {
		this.id_asl_azienda_prov = id_asl_azienda_prov;
	}

	public Timestamp getDataSessioneMacellazione(){
		
		return this.getCd_data_arrivo_macello();
		
	}
	
	public String getCd_matricola() {
		return cd_matricola;
	}
	public void setCd_matricola(String cd_matricola) {
		this.cd_matricola = cd_matricola;
	}
	public int getCd_specie() {
		return cd_specie;
	}
	public void setCd_specie(int cd_specie) {
		this.cd_specie = cd_specie;
	}
	
	public int getCd_categoria_bovina() {
		return cd_categoria_bovina;
	}
	public void setCd_categoria_bovina(int cd_categoria_bovina) {
		this.cd_categoria_bovina = cd_categoria_bovina;
	}
	public int getCd_categoria_bufalina() {
		return cd_categoria_bufalina;
	}
	public void setCd_categoria_bufalina(int cd_categoria_bufalina) {
		this.cd_categoria_bufalina = cd_categoria_bufalina;
	}
	
	public Timestamp getCd_data_nascita() {
		return cd_data_nascita;
	}
	public void setCd_data_nascita(Timestamp cd_data_nascita) {
		this.cd_data_nascita = cd_data_nascita;
	}
	public boolean isCd_maschio() {
		return cd_maschio;
	}
	public void setCd_maschio(boolean cd_maschio) {
		this.cd_maschio = cd_maschio;
	}
	public int getCd_id_razza() {
		return cd_id_razza;
	}
	public void setCd_id_razza(int cd_id_razza) {
		this.cd_id_razza = cd_id_razza;
	}
	public String getCd_razza_altro() {
		return cd_razza_altro;
	}
	public void setCd_razza_altro(String cd_razza_altro) {
		this.cd_razza_altro = cd_razza_altro;
	}
	
	public boolean isBufalino(){
		boolean ret=false;
		
		if (cd_specie==RAZZA_BUFALINA)
			ret=true; 
		
		return ret;
		
	}
	
	public boolean isBovino(){
		boolean ret=false;
		
		if (cd_specie==RAZZA_BOVINA)
			ret=true; 
		
		return ret;
	}
	
	public int getCd_bse() {
		return cd_bse;
	}
	public void setCd_bse(int cd_bse) {
		this.cd_bse = cd_bse;
	}
	
	
	
	
	public void setCategoria()
	{
		if (getCd_specie()==1 )
		{
			if (super.getMavam_data()!= null && ! "".equals(super.getMavam_data())  && super.getMavam_luogo()>0 && ! "".equals(super.getMavam_luogo()))
			{
				setCd_categoria_rischio(5);
			}
			else
			{
				if (getCd_macellazione_differita()>0)
				{
					// se il capo ha oltre 48 mesi di eta' categoria 3
				}
				else
				{
					// se 2.	[macellazione in emergenza] -  sopra 48 mesi d eta' 2
				}
			}
			

		}
		
	}
	
	private static void logga(ConfigTipo configTipo, ActionContext context, Connection db,GenericBean b) throws Exception
	{
			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();
	
			capoLog.setCodiceAziendaNascita(b.getCd_codice_azienda());
			capoLog.setDataNascita(((Capo)b).getCd_data_nascita());
			capoLog.setEnteredBy(b.getEntered_by());
			capoLog.setIdMacello(b.getId_macello());
			capoLog.setInBdn(context.getRequest().getParameter("capo_in_bdn") != null && context.getRequest().getParameter("capo_in_bdn").equals("si"));
			capoLog.setMatricola(((Capo)b).getCd_matricola());
			capoLog.setModifiedBy(((Capo)b).getModified_by());
			capoLog.setRazza(((Capo)b).getCd_id_razza());
			capoLog.setSesso(((Capo)b).isCd_maschio() ? "M" : "F");
			capoLog.setSpecie(((Capo)b).getCd_specie());
			capoLog.setTrashedDate(b.getTrashed_date());
			
			if(capoLog.isInBdn()){

				try{
					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}

			capoLogDao.log(db, capoLog);
	}
	
	
	
	public String costruisciQueryCapoEsistente(ConfigTipo configTipo, Integer numOvini, Integer numCaprini)
	{
		return configTipo.getQueryEsistenzaCapo();
	}
	
	public PreparedStatement costruisciStatementPartitaEsistente(PreparedStatement stat, String identificativo,String codiceAzienda) throws SQLException
	{
		stat.setString( 1, identificativo );
		return stat;
	}

}
