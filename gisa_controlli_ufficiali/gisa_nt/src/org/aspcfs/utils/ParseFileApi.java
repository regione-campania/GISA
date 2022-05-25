/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.util.imports.DbUtil;

public class ParseFileApi {
	
	
	private void verificaAzienda(String codiceFiscale,String codiceAziendaNazionale,PrintWriter pw) throws SQLException
	{
		
		Connection db = GestoreConnessioni.getConnection();
		String sql = "select * from apicoltura_imprese where codice_azienda ilike ? and codice_fiscale_impresa ilike ? ";
		try
		{
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, codiceAziendaNazionale);
		pst.setString(2, codiceFiscale);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
		{
			pw.println("[OK] - IMPRESA CON CODICE "+codiceAziendaNazionale+" PROPRIETARIO "+codiceFiscale+" PRESENTE IN BANCA DATI");
		}
		else
		{
			pw.println("[KO] - IMPRESA CON CODICE "+codiceAziendaNazionale+" PROPRIETARIO "+codiceFiscale+" NON PRESENTE IN BANCA DATI");

		}
		pw.flush();
		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
	}
	
	
	private void verificaSoggettoFisico(String codiceFiscale,PrintWriter pw) throws SQLException
	{
		Connection db = GestoreConnessioni.getConnection();
		String sql = "select * from opu_soggetto_fisico where codice_fiscale ilike ? ";
		try
		{
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, codiceFiscale);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
		{
			pw.println("[OK] - SOGGETTO FISICO PRESENTE CON CF "+codiceFiscale+" NOMINATIVO "+rs.getString("nome")+ " "+ rs.getString("cognome") +" PRESENTE IN BANCA DATI");
		}
		else
		{
			pw.println("[KO] - SOGGETTO FISICO NON PRESENTE  CF "+codiceFiscale);

		}
		pw.flush();
		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
	
	}
	
	
	public void parseFileApiFuoriRegione(String pathFileCsv,Connection db) throws IOException, SQLException
	{
		
		String insert = "INSERT INTO apicoltura_richieste_bdn("+
		           " cf_richiedente, denominazione_ric, codice_azienda, denominazione_attivita, "+
		             "cf_proprietario, nominativo_prop, progressivo, cf_detentore, "+
		          "   nominativo_detentore, num_alveari, num_sciami, indirizo, cap, "+
		           " localita, data_apertura, data_chiusura, istat_comune, descr_comune, "+
		            " prov_sigla, codice_asl, longitudine, latitudine, modalitaallevdescr, "+
		          "   classificazione, sottospecie)"+
		   "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = db.prepareStatement(insert);
		File f = new File(pathFileCsv);
		if (f.exists())
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null ;
			
			while ((line=br.readLine())!=null)
			{
				String[] row = line.split(";");
				String cfRichiedente = row[0];
				String denominazioneRichiedente = row[1];
				String codiceAzienda =  row[2];
				String denominazioneAttivita = row[3];
				String cfProprietario = row[4];	
				String nominativoPropr =  row[5];	
				String progr = row[6];
				String cfDetentore =  row[7];
				String nominativoDet = row[8];
				String numAlveari = row[9];
				String numSciami = row[10];
				String indirizzo = row[11];
				String cap = row[12];
				String localita = row[13];
				String dataApertura = row[14];
				String dataChiusura = row[15];
				String istatComune = row [16];
				String descrComune = row[17];
				String provSigla = row[18] ;
				String codiceAsl = row [19];
				String longitudine = row[21];
				String latitudine = row[22];
				String modalitaAllevDescr = row[23] ;
				String classificazione = row[24];
				String sottoSpecie = row[25];
				
				int i =0 ;
				pst.setString(++i,cfRichiedente );
				pst.setString(++i, denominazioneRichiedente);
				pst.setString(++i, codiceAzienda);
				pst.setString(++i, denominazioneAttivita);
				pst.setString(++i, cfProprietario);
				pst.setString(++i, nominativoPropr);
				pst.setString(++i, progr);
				pst.setString(++i, cfDetentore);
				pst.setString(++i, nominativoDet);
				pst.setString(++i, numAlveari);
				pst.setString(++i, numSciami);
				pst.setString(++i,indirizzo );
				pst.setString(++i,cap );
				pst.setString(++i, localita);
				pst.setString(++i, dataApertura);
				pst.setString(++i, dataChiusura);
				pst.setString(++i, istatComune);
				pst.setString(++i,descrComune );
				pst.setString(++i, provSigla);
				pst.setString(++i, codiceAsl);
				pst.setString(++i,longitudine );
				pst.setString(++i,latitudine );
				pst.setString(++i, modalitaAllevDescr);
				pst.setString(++i, classificazione);
				pst.setString(++i, sottoSpecie);
				pst.execute();
				
				
				
			}
			
		}
		
		
	}

}
