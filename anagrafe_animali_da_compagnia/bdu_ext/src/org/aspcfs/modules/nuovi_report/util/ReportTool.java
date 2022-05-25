/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.modules.nuovi_report.base.Stats;
import java.util.logging.Logger;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class ReportTool
{
	private transient static Logger logger = Logger.getLogger("MainLogger");
	public static float[] reportAnagrafati(ActionContext context,
			ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ----------------- REPORT ANAGRAFATI -----------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		int			ms_tatoo	= integer ( context, "reportDetails" );
		int			vivo		= integer ( context, "vivo" );
		boolean		fuoriReg	= booleano( context, "regione" );
		
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, vivo, fuori_regione FROM elenco_anagrafati WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl = " + asl + " " ) : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		sql += ( (ms_tatoo == 4) ? ( " AND ( microchip IS NULL OR trim( microchip ) = '' ) " ) : ("") );
		sql += ( (ms_tatoo == 5) ? ( " AND ( microchip IS NOT NULL AND trim( microchip ) <> '' ) " ) : ("") );
		sql += ( (vivo == 1)     ? ( " AND (NOT vivo) " )       : ("") );
		sql += ( (vivo == 2)     ? ( " AND vivo " )           : ("") );
		sql += ( (fuoriReg)      ? ( " AND fuori_regione " ) : ("") );
		
		sql += " ORDER BY asl, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();

		float[] ret = loadElenco( res, elenco, header, false, false, true, true, false, false, false );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}

	//temporaneo da eliminare prova di ristrutturazione
	public static float[] reportAnagrafatiRev2(ActionContext context,
			ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db,ArrayList<Stats> risultati2, ArrayList<Stats> risultati3) throws SQLException
	{
		logger.info( "[CANINA] - ----------------- REPORT ANAGRAFATI REVISIONE 2-----------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		int			ms_tatoo	= integer ( context, "reportDetails" );
		int			vivo		= integer ( context, "vivo" );	
		
		String sql = "SELECT microchip, tatuaggio, vivo, asl_cane, id_asl_cane, data_inserimento_software ," +
			"data_registrazione_software, tipo_operatore, j.description as asl_op from elenco_anagrafati_rev2_prova " +
			"LEFT JOIN lookup_asl_rif j ON j.code=asl_definitiva where true and tipo_operatore!=''  ";
	
		
		//filtro sull'asl dell'operatore
		sql += ( (asl > 0)       ? ( " AND asl_definitiva = " + asl + " " ) : ("") );
		
		//filtro sulla data di inserimento nel software
		sql += ( (dal != null)   ? ( " AND data_inserimento_software >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data_inserimento_software <= '" + al + "' " )   : ("") );
	
		//filtro sul microchip o sul tatuaggio
		sql += ( (ms_tatoo == 4) ? ( " AND ( microchip IS NULL OR trim( microchip ) = '' ) " ) : ("") );
		sql += ( (ms_tatoo == 5) ? ( " AND ( microchip IS NOT NULL AND trim( microchip ) <> '' ) " ) : ("") );
	
		
		//filtro sullo stato del cane vivo o deceduto
		sql += ( (vivo == 1)     ? ( " AND (NOT vivo) " )       : ("") );
		sql += ( (vivo == 2)     ? ( " AND vivo " )           : ("") );
		
		//filtro sulla data di registrazione
		//sql += ( (dal != null)   ? ( " AND data_registrazione_software >= '" + dal + "' " )  : ("") );
		//sql += ( (al != null)    ? ( " AND data_registrazione_software <= '" + al + "' " )   : ("") );
		

		sql += " ORDER BY tipo_operatore,asl_op, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();

		
		float[] ret = loadElencoNuovo( res, elenco, header, true, true );
		String  riepilogo ="SELECT COUNT(tipo_operatore) as totale_cani_anagrafati, tipo_operatore FROM ( " + sql + " )x GROUP BY tipo_operatore";
		
		
		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati2( res, risultati );
		
		String  riepilogo2 ="SELECT asl_cane, count (asl_cane) as tot FROM ( " + sql + " )x  where tipo_operatore='Veterinari LP' GROUP BY " +
		" asl_cane,tipo_operatore ORDER BY ASL_CANE";
		
		stat	= db.prepareStatement( riepilogo2 );
		res		= stat.executeQuery();
		loadRisultati3( res, risultati2 );
		
		String riepilogo3 = "SELECT asl_cane, count (asl_cane) as tot FROM ( " + sql + " )x  where tipo_operatore='Veterinari ASL' GROUP BY " +
		" asl_cane,tipo_operatore ORDER BY ASL_CANE";
		
		stat	= db.prepareStatement( riepilogo3 );
		res		= stat.executeQuery();
		loadRisultati4( res, risultati3 );
		
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
		
	}
//fine di prova
	
	/**
	 * 
	 * @param res
	 * @param elenco
	 * @param header
	 * @param comune
	 * @param com_catt
	 * @param vivo
	 * @param fuori_reg
	 * @param sanzione
	 * @param contr_reg
	 * @param data_catt
	 * @return
	 * @throws SQLException
	 */
	private static float[] loadElenco(ResultSet res, ArrayList<Stats> elenco, Stats header,
			boolean comune, boolean com_catt, boolean vivo, 
			boolean fuori_reg, boolean sanzione, boolean contr_reg, boolean data_catt) throws SQLException
	{
		ArrayList<Float> ret = new ArrayList<Float>();
		
		while( res.next() )
		{
			Stats temp = new Stats();
			temp.setAsl( res.getString( "asl" ) );
			temp.setIdAsl( res.getInt( "id_asl" ) );
			if( comune )
			{
				temp.add( res.getString( "comune" ) );
			}
			if( com_catt )
			{
				temp.add( res.getString( "comune_cattura" ) );
			}
			temp.add( res.getString( "microchip" ) );
			temp.add( res.getString( "tatuaggio" ) );
			temp.add( formatData( res.getTimestamp( "data" ) ) );
			if( vivo )
			{
				temp.add( (res.getBoolean( "vivo" )) ? ( "SI" ) : ( "NO" ) );
			}
			if( fuori_reg )
			{
				temp.add( (res.getBoolean( "fuori_regione" )) ? ( "SI" ) : ( "NO" ) );
			}
			if( sanzione )
			{
				temp.add( (res.getBoolean( "sanzione" )) ? ( "SI: " + res.getInt( "importo_sanzione" ) ) : ( "NO" ) );
			}
			if( contr_reg )
			{
				temp.add( (res.getBoolean( "contr_regionale" )) ? ( "SI" ) : ( "NO" ) );
			}
			if( data_catt )
			{
				temp.add( formatData( res.getTimestamp( "data_cattura" ) ) );
			}
			
			elenco.add( temp );
		}
		
		header.add( "ASL" );
		ret.add( 1.5f );
		
		if( comune )
		{
			header.add( "COMUNE" );
			ret.add( 2.7f );
		}
		
		if( com_catt )
		{
			header.add( "COMUNE CATTURA" );
			ret.add( 2.7f );
		}
		
		header.add( "MICROCHIP" );
		ret.add( 1.7f );
		
		header.add( "TATUAGGIO" );
		ret.add( 1.7f );
		
		header.add( "DATA" );
		ret.add( 1.1f );
		
		if( vivo )
		{
			header.add( "VIVO" );
			ret.add( .5f );
		}
		if( fuori_reg )
		{
			header.add( "FUORI REG." );
			ret.add( .5f );
		}
		if( sanzione )
		{
			header.add( "SANZIONE" );
			ret.add( 1.1f );
		}
		if( contr_reg )
		{
			header.add( "CONTR." );
			ret.add( .8f );
		}
		if( data_catt )
		{
			header.add( "DATA CATTURA" );
			ret.add( 1.1f );
		}
		
		
		float[] fa = new float[ ret.size() ];
		for( int i = 0; i < ret.size(); i++ )
		{
			fa[i] = ret.get( i ).floatValue();
		}
		
		return fa;
	}
	
	private static float[] loadElencoVet( ResultSet res, ArrayList<Stats> elenco, Stats header, int type ) throws SQLException
	{
		ArrayList<Float> ret = new ArrayList<Float>();
		
		while( res.next() )
		{
			Stats temp = new Stats();
			temp.setAsl( res.getString( "asl_vet" ) );
			temp.setIdAsl( res.getInt( "id_asl_vet" ) );
			temp.add( res.getString( "cognome_vet" ) + " " + res.getString( "nome_vet" ) );
			temp.add( res.getString( "microchip" ) );
			if( type == 1 )
			{
				temp.add( res.getString( "asl" ) );
				temp.add( formatData( res.getTimestamp( "data" ) ) );
			}
			else if( type == 2 )
			{
				temp.add( res.getString( "asl_cane" ) );
				temp.add( formatData( res.getTimestamp( "data_ster" ) ) );
				temp.add( (res.getBoolean( "contr_regionale" )) ? ("SI") : ("NO") );
			}
			
			
			elenco.add( temp );
		}
		
		header.add( "ASL VET." );
		ret.add( 1.2f );

		header.add( "VETERINARIO" );
		ret.add( 1.8f );
		
		header.add( "MICROCHIP" );
		ret.add( 1.8f );

		header.add( (type == 1) ? ("ASL INS." ) : ("ASL CANE") );
		ret.add( 1.2f );
		
		header.add( "DATA" );
		ret.add( 1.1f );
		
		if( type == 2 )
		{
			header.add( "CONTR." );
			ret.add( .8f );
		}
		
		float[] fa = new float[ ret.size() ];
		for( int i = 0; i < ret.size(); i++ )
		{
			fa[i] = ret.get( i ).floatValue();
		}
		
		return fa;
	}

	private static String formatData(Timestamp timestamp)
	{
		return (timestamp == null) ? ("") : (sdf.format( timestamp ));
	}

	private static void loadRisultati(ResultSet res, ArrayList<Stats> risultati) throws SQLException
	{

		Stats temp = new Stats();
		temp.add( "ASL" );
		temp.add( "CANI" );
		risultati.add( temp );
		
		int righe;
		int tot = 0;
		for( righe = 0; res.next(); righe++ )
		{
			int cani = res.getInt( "cani" );
			tot += cani;
			temp = new Stats();
			temp.setAsl( res.getString( "asl" ) );
			temp.setIdAsl( res.getInt( "id_asl" ) );
			temp.add( temp.getAsl() + "  " );
			temp.add( cani + "  " );
			risultati.add( temp );
		}
		
		if( righe > 1 )
		{
			temp = new Stats();
			temp.add( "TOTALE  " );
			temp.add( tot + "  " );
			risultati.add( temp );
		}
		
		if( righe == 0)
		{
			risultati.remove( 0 );
			temp = new Stats();
			temp.add( "NESSUN CANE" );
			risultati.add( temp );
		}
	}

	private static float[] loadElencoNuovo(ResultSet res, ArrayList<Stats> elenco, Stats header,
			boolean vivo,boolean fuori_reg) throws SQLException
	{
		ArrayList<Float> ret = new ArrayList<Float>();
		
		while( res.next() )
		{
			Stats temp = new Stats();
			temp.setAsl( res.getString( "asl_cane" ) );
			temp.setIdAsl( res.getInt( "id_asl_cane" ) );
			
			temp.add( res.getString( "microchip" ) );
			temp.add( res.getString( "tatuaggio" ) );
		
		//	temp.add( formatData( res.getTimestamp( "data" ) ) );
			if( vivo )
			{
				temp.add( (res.getBoolean( "vivo" )) ? ( "SI" ) : ( "NO" ) );
			}
			
			temp.add( formatData( res.getTimestamp( "data_inserimento_software" ) ) );
			temp.add( formatData( res.getTimestamp( "data_registrazione_software" ) ) );
			temp.add( res.getString("tipo_operatore"));
			temp.add( res.getString("asl_op"));
			elenco.add( temp );
		}
		
		header.add( "ASL CANE" );
		ret.add( 1.7f );
		
		header.add( "MICROCHIP" );
		ret.add( 2.1f );
		
		header.add( "TATUAGGIO" );
		ret.add( 1.7f );
		
		if( vivo )
		{
			header.add( "VIVO" );
			ret.add( .8f );
		}
		header.add( "INS. SF" );
		ret.add( 1.6f );
		header.add( "DATA REG." );
		ret.add( 1.6f );
		header.add( "TIPO OP." );
		ret.add( 2.0f );
		header.add( "ASL OP." );
		ret.add( 1.5f );

		float[] fa = new float[ ret.size() ];
		for( int i = 0; i < ret.size(); i++ )
		{
			fa[i] = ret.get( i ).floatValue();
		}
		
		return fa;
	}
	
	public static float[] reportCatturati(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ------------------- REPORT CATTURE -------------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		String		comune		= context.getParameter( "prova" );
		
		String sql = "SELECT asl, comune_cattura, microchip, tatuaggio, data, id_asl FROM elenco_catture WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl = " + asl + " " ) : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		sql += ( (asl > 0 && !"--Nessuno--".equalsIgnoreCase(comune)) ? (" AND comune_cattura ilike '" + comune.replaceAll( "'", "''") + "' ") : ("") );
		
		sql += " ORDER BY asl, comune_cattura, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, false, true, false, false, false, false, false );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}

	public static float[] reportCessioni(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ------------------ REPORT CESSIONI ------------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl FROM elenco_cessioni WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl = " + asl + " " ) : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		
		sql += " ORDER BY asl, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, false, false, false, false, false, false, false );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}

	public static float[] reportRestituiti(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ------------------ REPORT RESTITUITI -----------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		String		comune		= context.getParameter( "prova" );
		
		String sql = "SELECT asl, comune, microchip, tatuaggio, data, id_asl FROM elenco_restituiti WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl = " + asl + " " ) : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		sql += ( (asl > 0 && !"--Nessuno--".equalsIgnoreCase(comune)) ? (" AND comune ilike '" + comune.replaceAll( "'", "''") + "' ") : ("") );
		
		sql += " ORDER BY asl, comune, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, true, false, false, false, false, false, false );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}

	public static float[] reportSmarriti(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ------------------ REPORT SMARRITI ------------------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		int			sanzione	= integer ( context, "sanzione" );
		String		comune		= context.getParameter( "prova" );
		
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, sanzione, importo_sanzione, comune FROM elenco_smarriti WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl = " + asl + " " )   : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		sql += ( (sanzione == 1) ? ( " AND (NOT sanzione) " )         : ("") );
		sql += ( (sanzione == 2) ? ( " AND sanzione " )               : ("") );
		sql += ( (asl > 0 && !"--Nessuno--".equalsIgnoreCase(comune)) ? (" AND comune ilike '" + comune.replaceAll( "'", "''") + "' ") : ("") );
		
		sql += " ORDER BY asl, comune, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, true, false, false, false, true, false, false );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}

	/*public static float[] reportSterilizzati(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "---------------- REPORT STERILIZZATI ----------------" );
		
		int			asl			= asl    (context);
		Timestamp	dal			= data   ( context, "date_start" );
		Timestamp	al			= data   ( context, "date_end" );
		int			cattura		= integer( context, "reportCattura" );
		int			contributo	= integer( context, "reportContributo" );
		int			stato		= integer( context, "reportType" );
		
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, cattura, data_cattura, contr_regionale, data_reimmissione, in_canile, data_adozione FROM elenco_sterilizzati WHERE TRUE ";
		
		sql += ( (asl > 0)         ? ( " AND id_asl = " + asl + " " )          : ("") );
		sql += ( (dal != null)     ? ( " AND data >= '" + dal + "' " )         : ("") );
		sql += ( (al != null)      ? ( " AND data <= '" + al + "' " )          : ("") );
		sql += ( (cattura == 6)    ? ( " AND cattura " )                       : ("") );
		sql += ( (cattura == 7)    ? ( " AND (NOT cattura) " )                 : ("") );
		sql += ( (contributo == 4) ? ( " AND contr_regionale " )               : ("") );
		sql += ( (contributo == 5) ? ( " AND (NOT contr_regionale) " )         : ("") );
		sql += ( (stato == 1)      ? ( " AND data_reimmissione IS NOT NULL " ) : ("") );
		sql += ( (stato == 2)      ? ( " AND in_canile " )                     : ("") );
		sql += ( (stato == 3)      ? ( " AND data_adozione IS NOT NULL " )     : ("") );
		
		sql += " ORDER BY asl, data_cattura, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, false, false, false, false, false, true, true );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}
	*/
	public static float[] reportSterilizzati(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ---------------- REPORT STERILIZZATI ----------------" );
		
		int			asl			= asl    (context);
		Timestamp	dal			= data   ( context, "date_start" );
		Timestamp	al			= data   ( context, "date_end" );
		int			cattura		= integer( context, "reportCattura" );
		int			contributo	= integer( context, "reportContributo" );
		int			stato		= integer( context, "reportType" );
		
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, comune,cattura, data_cattura, contr_regionale, data_reimmissione, in_canile, data_adozione FROM elenco_sterilizzati2 WHERE TRUE ";
		
		sql += ( (asl > 0)         ? ( " AND id_asl = " + asl + " " )          : ("") );
		sql += ( (dal != null)     ? ( " AND data >= '" + dal + "' " )         : ("") );
		sql += ( (al != null)      ? ( " AND data <= '" + al + "' " )          : ("") );
		sql += ( (cattura == 6)    ? ( " AND cattura " )                       : ("") );
		sql += ( (cattura == 7)    ? ( " AND (NOT cattura) " )                 : ("") );
		sql += ( (contributo == 4) ? ( " AND contr_regionale " )               : ("") );
		sql += ( (contributo == 5) ? ( " AND (NOT contr_regionale) " )         : ("") );
		sql += ( (stato == 1)      ? ( " AND data_reimmissione IS NOT NULL " ) : ("") );
		sql += ( (stato == 2)      ? ( " AND in_canile " )                     : ("") );
		sql += ( (stato == 3)      ? ( " AND data_adozione IS NOT NULL " )     : ("") );
		
		sql += " ORDER BY asl, data_cattura, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, true, false, false, false, false, true, true );
		
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}
	
	public static float[] reportAnagrafatiVet(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ------------ REPORT ANAGRAFATI VETERINARI -----------" );
		
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		boolean		fuoriReg	= booleano( context, "regione" );
		
		String sql = "SELECT asl, cognome_vet, nome_vet, " +
				"case when ( microchip is null or trim(microchip) = '' ) then tatuaggio else microchip end as microchip, " +
				"data, id_asl, fuori_regione, " +
				"id_asl_vet, asl_vet " +
				"FROM elenco_anagrafati_veterinari WHERE TRUE ";
		
		sql += ( (asl > 0)       ? ( " AND id_asl_vet = " + asl + " " ) : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )    : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )     : ("") );
		sql += ( (fuoriReg)      ? ( " AND id_asl = 14 " )              : ("") );
		
		sql += " ORDER BY asl_vet, cognome_vet, nome_vet, asl, data, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();

		float[] ret = loadElencoVet( res, elenco, header, 1 );
		
		String riepilogo = "SELECT x.asl_vet as asl, x.id_asl_vet as id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl_vet, id_asl_vet ORDER BY asl_vet";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}
	
	public static float[] reportSterilizzatiVet(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "[CANINA] - ----------- REPORT STERILIZZATI VETERINARI ----------" );
		
		int			asl			= asl    (context);
		Timestamp	dal			= data   ( context, "date_start" );
		Timestamp	al			= data   ( context, "date_end" );
		int			contributo	= integer( context, "reportContributo" );
		
		String sql = "SELECT asl_cane, cognome_vet, nome_vet, " +
				"case when ( microchip is null or trim(microchip) = '' ) then tatuaggio else microchip end as microchip, " +
				"data_ster, id_asl_cane, " +
				"contr_regionale, id_asl_vet, asl_vet " +
				"FROM elenco_sterilizzati_veterinari WHERE TRUE ";
		
		sql += ( (asl > 0)         ? ( " AND id_asl_vet = " + asl + " " )      : ("") );
		sql += ( (dal != null)     ? ( " AND data_ster >= '" + dal + "' " )    : ("") );
		sql += ( (al != null)      ? ( " AND data_ster <= '" + al + "' " )     : ("") );
		sql += ( (contributo == 4) ? ( " AND contr_regionale " )               : ("") );
		sql += ( (contributo == 5) ? ( " AND (NOT contr_regionale) " )         : ("") );
		
		sql += " ORDER BY asl_vet, cognome_vet, nome_vet, asl_cane, data_ster, microchip, tatuaggio";

		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElencoVet( res, elenco, header, 2 );
		
		String riepilogo = "SELECT x.asl_vet as asl, x.id_asl_vet as id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl_vet, id_asl_vet ORDER BY asl_vet";

		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		
		logger.info( "-----------------------------------------------------" );
		
		return ret;
	}
	private static void loadRisultati2(ResultSet res, ArrayList<Stats> risultati) throws SQLException
	{

		Stats temp = new Stats();
		temp.add( "TIPO OPERATORE" ); //ASL
		temp.add( "TOTALE CANI ANAGRAFATI" );//CANI
		risultati.add( temp );
		
		int righe;
		int tot = 0;
		for( righe = 0; res.next(); righe++ )
		{
			String tipologia = res.getString("tipo_operatore");
			int cani = res.getInt( "totale_cani_anagrafati" ); //cani
			tot += cani;
			temp = new Stats();
			temp.add(tipologia);
			temp.add( cani + "  " );
			risultati.add( temp );
		}
		
		if( righe > 1 )
		{
			temp = new Stats();
			temp.add( "TOTALE  " );
			temp.add( tot + "  " );
			risultati.add( temp );
		}
		
		if( righe == 0)
		{
			risultati.remove( 0 );
			temp = new Stats();
			temp.add( "NESSUN CANE" );
			risultati.add( temp );
		}
	}

	private static void loadRisultati3(ResultSet res, ArrayList<Stats> risultati) throws SQLException
	{

		Stats temp = new Stats();
		temp.add( "ASL" ); //ASL
		temp.add( "TOTALE CANI ANAGRAFATI DAI VET LP" );//CANI
		risultati.add( temp );
		
		int righe;
		int tot = 0;
		for( righe = 0; res.next(); righe++ )
		{
			String tipologia = res.getString("asl_cane");
			int cani = res.getInt( "tot" ); //cani
			tot += cani;
			temp = new Stats();
			temp.add(tipologia);
			temp.add( cani + "  " );
			risultati.add( temp );
		}
		
		if( righe > 1 )
		{
			temp = new Stats();
			temp.add( "TOTALE  " );
			temp.add( tot + "  " );
			risultati.add( temp );
		}
		
		if( righe == 0)
		{
			risultati.remove( 0 );
			temp = new Stats();
			temp.add( " " );
			temp.add("Nessun cane anagrafato dagli LP");
			risultati.add( temp );
		}
	}
	
	
	private static void loadRisultati4(ResultSet res, ArrayList<Stats> risultati) throws SQLException
	{
		if (res !=null){
		Stats temp = new Stats();
		temp.add( "ASL" ); //ASL
		temp.add( "TOTALE CANI ANAGRAFATI DAI VET ASL" );//CANI
		risultati.add( temp );
		
		int righe;
		int tot = 0;
		for( righe = 0; res.next(); righe++ )
		{
			String tipologia = res.getString("asl_cane");
			int cani = res.getInt( "tot" ); //cani
			tot += cani;
			temp = new Stats();
			temp.add(tipologia);
			temp.add( cani + "  " );
			risultati.add( temp );
		}
		
		if( righe > 1 )
		{
			temp = new Stats();
			temp.add( "TOTALE  " );
			temp.add( tot + "  " );
			risultati.add( temp );
		}
		
		if( righe == 0)
		{
			risultati.remove( 0 );
			temp = new Stats();
			temp.add("");
			temp.add( "Nessun cane anagrafato dai VET ASL" );
			risultati.add( temp );
		}
		}
	}
	
	/*public static float[] reportAnagrafati_vet(ActionContext context,
			ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "----------------- REPORT ANAGRAFATI VETERINARI-----------------" );
		int asl= 1;
		//int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		boolean		fuoriReg	= booleano( context, "regione" );
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, vivo, fuori_regione FROM elenco_anagrafati WHERE TRUE ";
		sql += ( (asl > 0)         ? ( " AND id_asl = " + asl + " " )          : ("") );
		sql += ( (dal != null)   ? ( " AND data >= '" + dal + "' " )  : ("") );
		sql += ( (al != null)    ? ( " AND data <= '" + al + "' " )   : ("") );
		sql += ( (fuoriReg)      ? ( " AND fuori_regione " ) : ("") );
		sql += " ORDER BY asl, microchip, tatuaggio";
		
		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		
		float[] ret = loadElenco( res, elenco, header, false, false, true, true, false, false, false );
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";
		
		stat	= db.prepareStatement( riepilogo );
		
		res		= stat.executeQuery();
		loadRisultati( res, risultati );

		logger.info( "-----------------------------------------------------" );

		return ret;
	}
	*/
	
	/*public static float[] reportSterilizzati_vet(ActionContext context, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, Connection db) throws SQLException
	{
		logger.info( "---------------- REPORT STERILIZZATI VETERINARI ----------------" );
		int asl= 1;
		Timestamp	dal			= data   ( context, "date_start" );
		Timestamp	al			= data   ( context, "date_end" );
		int			contributo	= integer( context, "reportContributo" );
		String sql = "SELECT asl, microchip, tatuaggio, data, id_asl, contr_regionale, data_reimmissione, in_canile, data_adozione FROM elenco_sterilizzati WHERE TRUE ";
		sql += ( (asl > 0)         ? ( " AND id_asl = " + asl + " " )          : ("") );
		sql += ( (dal != null)     ? ( " AND data >= '" + dal + "' " )         : ("") );
		sql += ( (al != null)      ? ( " AND data <= '" + al + "' " )          : ("") );
		sql += ( (contributo == 4) ? ( " AND contr_regionale " )               : ("") );
		sql += ( (contributo == 5) ? ( " AND (NOT contr_regionale) " )         : ("") );
		sql += " ORDER BY asl, microchip, tatuaggio";
		
		PreparedStatement 	stat	= db.prepareStatement( sql );
		ResultSet			res		= stat.executeQuery();
		float[] ret = loadElenco( res, elenco, header, false, false, false, false, false, true, false );
		String riepilogo = "SELECT x.asl, x.id_asl, count(*) AS cani FROM ( " + sql + " )x GROUP BY asl, id_asl ORDER BY asl";

		
		stat	= db.prepareStatement( riepilogo );
		res		= stat.executeQuery();
		loadRisultati( res, risultati );
		logger.info( "-----------------------------------------------------" );

		return ret;
	}
	*/
	
	private static boolean booleano(ActionContext context, String string)
	{
		String temp = context.getParameter( string );
		return 
		   "true"	.equalsIgnoreCase( temp ) 
		|| "ok"		.equalsIgnoreCase( temp ) 
		|| "si"		.equalsIgnoreCase( temp ) 	
		|| "yes"	.equalsIgnoreCase( temp )
		|| "on"		.equalsIgnoreCase( temp );
	}

	private static int integer(ActionContext context, String string)
	{
		int ret = -1;
		try
		{
			ret = Integer.parseInt( context.getParameter( string ) );
		}
		catch ( Exception e )
		{
			ret = -1;
		}
		return ret;
	}

	private static Timestamp data(ActionContext context, String string)
	{
		Timestamp ret = null;
		
		try
		{
			String data = context.getParameter( string );
			if( data != null && data.trim().length() > 0 )
			{
				ret = new Timestamp( sdf.parse( data ).getTime() );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		return ret;
	}

	private static int asl(ActionContext context)
	{
		return integer( context, "aslRif" );
	}

	public static void load(ActionContext context, ArrayList<String> filtri, Connection db )
	{
		int			asl			= asl     (context);
		Timestamp	dal			= data    ( context, "date_start" );
		Timestamp	al			= data    ( context, "date_end" );
		int			ms_tatoo	= integer ( context, "reportDetails" );
		int			vivo		= integer ( context, "vivo" );
		boolean		fuoriReg	= booleano( context, "regione" );
		String		comune		= context.getParameter( "prova" );
		int			cattura		= integer( context, "reportCattura" );
		int			contributo	= integer( context, "reportContributo" );
		int			stato		= integer( context, "reportType" );
		int			sanzione	= integer ( context, "sanzione" );
		if( asl > 0 )
		{
			try
			{
				LookupList aslList = new LookupList(db, "lookup_asl_rif");
				filtri.add( "ASL " + aslList.getSelectedValue( asl ) );
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		if(asl > 0 && comune != null && !"--Nessuno--".equalsIgnoreCase(comune))
		{
			filtri.add( "COMUNE DI " + comune );
		}
		
		if( dal != null )
		{
			filtri.add( "DAL " + sdf.format( dal ) );
		}
		
		if( al != null )
		{
			filtri.add( "AL " + sdf.format( al ) );
		}
		
		if( ms_tatoo == 4 )
		{
			filtri.add( "CON SOLO TATUAGGIO" );
		}
		
		if( ms_tatoo == 5 )
		{
			filtri.add( "CON MICROCHIP" );
		}
		
		if( vivo == 1 )
		{
			filtri.add( "DECEDUTI" );
		}
		
		if( vivo == 2 )
		{
			filtri.add( "VIVI" );
		}

		if( cattura == 6 )
		{
			filtri.add( "CATTURATI" );
		}
		
		if( cattura == 7 )
		{
			filtri.add( "NON CATTURATI" );
		}
		
		if( contributo == 4 )
		{
			filtri.add( "CON CONTRIBUTO REGIONALE" );
		}
		
		if( contributo == 5 )
		{
			filtri.add( "SENZA CONTRIBUTO REGIONALE" );
		}
		
		if( stato == 1 )
		{
			filtri.add( "REIMMESSI" );
		}
		
		if( stato == 2 )
		{
			filtri.add( "IN CANILE" );
		}
		
		if( stato == 3 )
		{
			filtri.add( "ADOTTATI" );
		}

		if( sanzione == 1 )
		{
			filtri.add( "SENZA SANZIONE" );
		}
		
		if( sanzione == 2 )
		{
			filtri.add( "CON SANZIONE" );
		}
		
		if( fuoriReg )
		{
			filtri.add( "FUORI REGIONE" );
		}
		
		if( filtri.size() == 0 )
		{
			filtri.add( "NESSUN FILTRO" );
		}
		
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	static SimpleDateFormat sdfSQL = new SimpleDateFormat( "yyyy-MM-dd" );
	
}
