/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.utils.CsvLunghezzaFissa.Colonna;

public class CapiAllevamentiUtil {
	
	private static void startuoSpecie()
	{
		specie.put( "0121", 1 );
		specie.put( "0122", 2 );
		specie.put( "0124", 3 );
		specie.put( "0126", 4 );
		specie.put( "0129", 5 );
		specie.put( "0125", 6 );
		specie.put( "0128", 7 );
		specie.put( "0130", 8 );
		specie.put( "0132", 9 );
		specie.put( "0133", 10 );
		specie.put( "0134", 11 );
		specie.put( "0135", 12 );
		specie.put( "0131", 13 );
		specie.put( "0136", 14 );
		specie.put( "0137", 15 );
		specie.put( "0138", 16 );
		specie.put( "0139", 17 );
		specie.put( "0140", 18 );
		specie.put( "0141", 19 );
		specie.put( "0142", 20 );
		specie.put( "0143", 21 );
		specie.put( "0146", 22 );
		specie.put( "0170", 23 );
		specie.put( "0148", 24 );
		specie.put( "0149", 25 );
		specie.put( "0147", 26 );
		specie.put( "0150", 27 );
		specie.put( "0151", 28 );
		specie.put( "0152", 29 );
		specie.put( "0160", 30 );
		specie.put( "0144", 31 );

	}
	
	
	
	private static String getDescrizioneSpecie(String specie_allevata)
	{
		String specieAllev ="";
		if(specie_allevata.equals("0121")){
			specieAllev = "BOVINI";
		}else if(specie_allevata.equals("0129")){
			specieAllev = "BUFALINI";
		}else if(specie_allevata.equals("0124")){
			specieAllev = "OVINI";
		}else if(specie_allevata.equals("0125")){
			specieAllev = "CAPRINI";
		}else if(specie_allevata.equals("0122")){
			specieAllev = "SUINI";
		}else if(specie_allevata.equals("0126")){
			specieAllev = "CAVALLI";
		}else if(specie_allevata.equals("0147")){
			specieAllev = "MULI";
		}else if(specie_allevata.equals("0148")){
			specieAllev = "BARDOTTI";
		}else if(specie_allevata.equals("0128")){
			specieAllev = "CONIGLI";
		}else if(specie_allevata.equals("0130")){
			specieAllev = "API";
		}else if(specie_allevata.equals("0132")){
			specieAllev = "TACCHINI";
		}else if(specie_allevata.equals("0133")){ 
			specieAllev = "PERNICI";
		}else if(specie_allevata.equals("0134")){
			specieAllev = "QUAGLIE";
		}else if(specie_allevata.equals("0135")){
			specieAllev = "STARNE";
		}else if(specie_allevata.equals("0131")){
			specieAllev = "GALLUS";
		}else if(specie_allevata.equals("0136")){
			specieAllev = "PICCIONI";
		}else if(specie_allevata.equals("0137")){
			specieAllev = "OCHE";
		}else if(specie_allevata.equals("0138")){
			specieAllev = "FARAONE";
		}else if(specie_allevata.equals("0139")){
			specieAllev = "FAGIANI";
		}else if(specie_allevata.equals("0144")){
			specieAllev = "VOLATILI PER RICHIAMI VIVI";
		}else if(specie_allevata.equals("0140")){
			specieAllev = "STRUZZI";
		}else if(specie_allevata.equals("0141")){
			specieAllev = "ANATRE";
		}else if(specie_allevata.equals("0142")){
			specieAllev = "COLOMBE";
		}else if(specie_allevata.equals("0143")){
			specieAllev = "EMU";
		}else if(specie_allevata.equals("0146")){
			specieAllev = "AVICOLI MISTI";
		}else if(specie_allevata.equals("0150")){
			specieAllev = "ERMELLINI";
		}else if(specie_allevata.equals("0151")){
			specieAllev = "RANE";
		}else if(specie_allevata.equals("0152")){
			specieAllev = "LUMACHE";
		}else if(specie_allevata.equals("0160")){
			specieAllev = "PESCI";
		}
		
		return specieAllev;
	}
	
	
	private static void startupRazze()
	{
		

		razze.put( "ABO", 1 );
		razze.put( "AGE", 2 );
		razze.put( "ALT", 3 );
		razze.put( "LPG", 4 );
		razze.put( "LTM", 5 );
		razze.put( "ANG", 6 );
		razze.put( "AAG", 7 );
		razze.put( "APN", 8 );
		razze.put( "ARM", 9 );
		razze.put( "ATR", 10 );
		razze.put( "ABC", 11 );
		razze.put( "AVI", 12 );
		razze.put( "AWS", 13 );
		razze.put( "AYR", 14 );
		razze.put( "BGN", 15 );
		razze.put( "BRB", 16 );
		razze.put( "BZD", 17 );
		razze.put( "BRE", 18 );
		razze.put( "BGW", 19 );
		razze.put( "BGS", 20 );
		razze.put( "BRH", 21 );
		razze.put( "BVP", 22 );
		razze.put( "BLS", 23 );
		razze.put( "BSN", 24 );
		razze.put( "BKO", 25 );
		razze.put( "BAQ", 26 );
		razze.put( "BBL", 27 );
		razze.put( "BLT", 28 );
		razze.put( "BRM", 29 );
		razze.put( "BRN", 30 );
		razze.put( "BRT", 31 );
		razze.put( "BRZ", 32 );
		razze.put( "BRG", 33 );
		razze.put( "BRO", 34 );
		razze.put( "BSW", 35 );
		razze.put( "BRN", 36 );
		razze.put( "BAA", 37 );
		razze.put( "BNP", 38 );
		razze.put( "BFL", 39 );
		razze.put( "BUR", 40 );
		razze.put( "CAB", 41 );
		razze.put( "CAL", 42 );
		razze.put( "CMG", 43 );
		razze.put( "CMR", 44 );
		razze.put( "CHL", 45 );
		razze.put( "CHT", 46 );
		razze.put( "CIA", 47 );
		razze.put( "CVN", 48 );
		razze.put( "CIN", 49 );
		razze.put( "CLF", 50 );
		razze.put( "CMS", 51 );
		razze.put( "CRN", 52 );
		razze.put( "CRG", 53 );
		razze.put( "CZP", 54 );
		razze.put( "DRW", 55 );
		razze.put( "DRT", 56 );
		razze.put( "DLG", 57 );
		razze.put( "DEV", 58 );
		razze.put( "DXT", 59 );
		razze.put( "BNV", 60 );
		razze.put( "DRP", 61 );
		razze.put( "DRH", 62 );
		razze.put( "FBN", 63 );
		razze.put( "FNR", 64 );
		razze.put( "FNN", 65 );
		razze.put( "FRB", 66 );
		razze.put( "FRS", 67 );
		razze.put( "FRN", 68 );
		razze.put( "HST", 69 );
		razze.put( "BTF", 70 );
		razze.put( "FFR", 71 );
		razze.put( "FIT", 72 );
		razze.put( "NZF", 73 );
		razze.put( "FHL", 74 );
		razze.put( "FRB", 75 );
		razze.put( "GLW", 76 );
		razze.put( "GRS", 77 );
		razze.put( "GAF", 78 );
		razze.put( "GRG", 79 );
		razze.put( "GDP", 80 );
		razze.put( "GRL", 81 );
		razze.put( "GRA", 82 );
		razze.put( "GRD", 83 );
		razze.put( "GCN", 84 );
		razze.put( "GUS", 85 );
		razze.put( "HEF", 86 );
		razze.put( "HER", 87 );
		razze.put( "HLA", 88 );
		razze.put( "ICD", 89 );
		razze.put( "LDF", 90 );
		razze.put( "ITS", 91 );
		razze.put( "JES", 92 );
		razze.put( "KRL", 93 );
		razze.put( "KBL", 94 );
		razze.put( "KRH", 95 );
		razze.put( "LCN", 96 );
		razze.put( "LMN", 97 );
		razze.put( "LAN", 98 );
		razze.put( "LTD", 99 );
		razze.put( "LCS", 100 );
		razze.put( "LNK", 101 );
		razze.put( "MRR", 102 );
		razze.put( "MSS", 103 );
		razze.put( "MTS", 104 );
		razze.put( "MRN", 105 );
		razze.put( "MRZ", 106 );
		razze.put( "MRA", 107 );
		razze.put( "MTT", 108 );
		razze.put( "NND", 109 );
		razze.put( "NST", 110 );
		razze.put( "NTC", 111 );
		razze.put( "PGL", 112 );
		razze.put( "PCC", 113 );
		razze.put( "POL", 114 );
		razze.put( "PNZ", 115 );
		razze.put( "PLZ", 116 );
		razze.put( "PMR", 117 );
		razze.put( "PST", 118 );
		razze.put( "RMV", 119 );
		razze.put( "CAS", 120 );
		razze.put( "LHO", 121 );
		razze.put( "LKV", 122 );
		razze.put( "LMS", 123 );
		razze.put( "MAJ", 124 );
		razze.put( "MAL", 125 );
		razze.put( "MCG", 126 );
		razze.put( "MDC", 127 );
		razze.put( "MDS", 128 );
		razze.put( "MRB", 129 );
		razze.put( "MRY", 130 );
		razze.put( "MSH", 131 );
		razze.put( "MTL", 132 );
		razze.put( "MWF", 133 );
		razze.put( "NMD", 134 );
		razze.put( "NRD", 135 );
		razze.put( "PDL", 136 );
		razze.put( "PDC", 137 );
		razze.put( "PGR", 138 );
		razze.put( "PIS", 139 );
		razze.put( "PMT", 140 );
		razze.put( "PNR", 141 );
		razze.put( "POH", 142 );
		razze.put( "PON", 143 );
		razze.put( "PRP", 144 );
		razze.put( "PRS", 145 );
		razze.put( "FRD", 146 );
		razze.put( "PRO", 147 );
		razze.put( "PRT", 148 );
		razze.put( "RBG", 149 );
		razze.put( "REN", 150 );
		razze.put( "RGG", 151 );
		razze.put( "RMG", 152 );
		razze.put( "SAL", 153 );
		razze.put( "SCL", 154 );
		razze.put( "MBL", 155 );
		razze.put( "SLO", 156 );
		razze.put( "PRF", 157 );
		razze.put( "SIM", 158 );
		razze.put( "SPT", 159 );
		razze.put( "BSA", 160 );
		razze.put( "SRB", 161 );
		razze.put( "SRD", 162 );
		razze.put( "SRW", 163 );
		razze.put( "TRT", 164 );
		razze.put( "TAR", 165 );
		razze.put( "UKM", 166 );
		razze.put( "UNK", 167 );
		razze.put( "VPN", 168 );
		razze.put( "VPR", 169 );
		razze.put( "OTT", 170 );
		razze.put( "TOR", 171 );
		razze.put( "VAR", 172 );
		razze.put( "VTO", 173 );
		razze.put( "WAG", 174 );
		razze.put( "WEB", 175 );
		razze.put( "YAK", 176 );
		razze.put( "ZEB", 177 );
		razze.put( "ZZZ", 178 );
		razze.put( "BFM", 179 );
		razze.put( "BFN", 180 );
			
	}
	
	private static String getData() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");
		String rit = sdf.format( gc.getTime() ).replace(":", "_");
		return ( rit );
	}
	
	
	private static boolean verifivaEsistenzaCapo(Connection db , String matricola , String codice_azienda)
	{
		PreparedStatement pst 	= null ;
		ResultSet rs 			= null ;
		try {
			
			pst = db.prepareStatement(" select * from m_capi_bdn where matricola = ? and codice_azienda = ? ");
			pst.setString(1, matricola);
			pst.setString(2, codice_azienda);
			rs = pst.executeQuery();
			if(rs.next())
			{
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	private static String getInsertFile( BufferedReader br,String filePath , Connection db) throws FileNotFoundException
	{
		
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( filePath + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( filePath + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( filePath + data + "_rpg.txt" ) ;
		
		PreparedStatement pst = null;
		try
		{
			
			
			
			Colonna[] c = 
			{
					new Colonna( 4,  "codice_usl" ),
					new Colonna( 2,  "codice_distretto" ),
					new Colonna( 13, "id_allevamento" ),
					new Colonna( 8,  "data_estrazione_dati" ),
					new Colonna( 1,  "inserimento_o_variazione" ),
					new Colonna( 13, "codice_interno" ),
					new Colonna( 14, "codice_identificativo_capo" ),
					new Colonna( 8,  "codice_azienda" ),
					new Colonna( 16, "identificativo_fiscale_allevamento" ),
					new Colonna( 4,  "specie_allevata" ),
					new Colonna( 1,  "flag_inseminazione" ),
					new Colonna( 14, "codice_marchio_madre" ),
					new Colonna( 14, "codice_assegnato_in_precedenza" ),
					new Colonna( 16, "codice_elettronico" ),
					new Colonna( 4,  "razza_capo" ),
					new Colonna( 1,  "sesso_capo" ),
					new Colonna( 8,  "data_nascita_capo" ),
					new Colonna( 8,  "data_ingresso_stalla" ),
					new Colonna( 8,  "data_applicazione_marca" ),
					new Colonna( 8,  "data_iscrizione_anagrafe" ),
					new Colonna( 1,  "origine_animale" ),
					new Colonna( 2,  "paese_provenienza" ),
					new Colonna( 3,  "codice_libro_genealogico" ),
					new Colonna( 8,  "data_ultimo_aggiornamento" ),
					new Colonna( 21, "numero_certificato_sanitario" ),
					new Colonna( 20, "numero_riferimento_locale" ),
					new Colonna( 10, "filler" )
			};
			
			CsvLunghezzaFissa clf = new CsvLunghezzaFissa( c );
			SimpleDateFormat sdf1 = new SimpleDateFormat( "ddMMyyyy" );
			SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
			
			String temp = br.readLine();
			while( temp != null && temp.trim().length() > 0 )
			{
				clf.setRiga( temp );
				/*
				 * 
				 * 	matricola VARCHAR( 64 ),
					codice_azienda VARCHAR( 64 ),
					data_nascita TIMESTAMP,
					razza VARCHAR( 64 ),
					maschio BOOLEAN,
					specie
				 */
				
				String matricola		= clf.get( "codice_identificativo_capo" );
				String codice_azienda	= clf.get( "codice_azienda" );
				String data_nascita		= clf.get( "data_nascita_capo" );
				String razza			= clf.get( "razza_capo" );
				String sesso			= clf.get( "sesso_capo" );
				String specie			= clf.get( "specie_allevata" );
				String maschio = ("M".equalsIgnoreCase(sesso)) ? ("TRUE") : ("FALSE");
				String inserimentoVariazione = clf.get("inserimento_o_variazione");
				String specieAllevata = clf.get("specie_allevata");
				
				if(inserimentoVariazione.equalsIgnoreCase("i"))
				{
				
				int idAzienda = Organization.prelevaOrgIdCapi(db, codice_azienda,getDescrizioneSpecie(specieAllevata));
				
				
				
				if(verifivaEsistenzaCapo(db, matricola, codice_azienda)==true)
				{
					logErr.write( ("ERRORE --> Il Capo che si sta tentando di inserire gia e presente in banca dati . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda + "\n").getBytes());	
					nrRigheErr++;
				}
				
				else
				{
					
					if(codice_azienda.trim().equals("") )
					{
						logErr.write( ("ERRORE --> Il Capo Non Puo essere inserito. Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Motivo Codice Azienda non Valiorizzato . "+ "\n").getBytes());	
						nrRigheErr++;
					}
					else
					{
						if(idAzienda==-1)
						{
							logErr.write( ("ERRORE --> Il Capo Non Puo essere inserito. Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Motivo Allevamento non Trovato in Gisa . "+ "\n").getBytes());	
							nrRigheErr++;
						}
						else
						{
						
						
							String sql = "INSERT INTO m_capi_bdn( matricola, codice_azienda, data_nascita, razza, maschio, specie )" +
								"VALUES( '" + matricola + "', '" + codice_azienda + "', '" + sdf2.format( sdf1.parse(data_nascita) ) + "', '" +
								getCodiceRazza( razza ) + "', " + maschio + ", '" + getCodiceSpecie( specie ) + "' );\n";
					
						pst=db.prepareStatement(sql);
						pst.execute();
					
						Integer codiceRazza = getCodiceRazza( razza );
						Integer codiceSpecie = getCodiceSpecie( specie );
						
						if(codiceRazza == -1 && codiceSpecie == -1 )
						{
							logOk.write( ("WARNING --> Il Capo e Stato inserito . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la razza : "+razza+", E nessuna corrispondenza per la specie : "+specie+ "\n").getBytes());	
							nrRigheOk++;
						}
						else{
						
						if(codiceRazza == -1)
						{
							logOk.write( ("WARNING --> Il Capo e Stato inserito . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la razza : "+razza+". Razza non identificata."+ "\n").getBytes());	
							nrRigheOk++;
						}else
						{
						if(codiceSpecie == -1)
						{
							logOk.write( ("WARNING --> Il Capo e Stato inserito . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la specie : "+specie+". Specie non identificata."+ "\n").getBytes());	
							nrRigheOk++;
						}
						
						}
						}
						
						if(codiceRazza!=-1 && codiceSpecie!= -1)
							{
						
						logOk.write( ("OK --> Il Capo e Stato inserito Correttamente . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda + "\n").getBytes());	
						nrRigheOk++;
							}
							}
						
					}
					
					}
				}
				else
				{
					if(inserimentoVariazione.equalsIgnoreCase("v"))
					{
						if(verifivaEsistenzaCapo(db, matricola, codice_azienda)==false)
						{
							logErr.write( ("ERRORE --> Il Capo che si sta tentando di Modificare non e presente in banca dati . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda + "\n").getBytes());	
							nrRigheErr++;
						}
						else
						{
							if(codice_azienda.trim().equals("") )
							{
								logErr.write( ("ERRORE --> Il Capo Non Puo essere Modificato. Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Motivo Codice Azienda non Valiorizzato . "+ "\n").getBytes());	
								nrRigheErr++;
							}
							else
							{
							
							
								String sql = "UPDATE m_capi_bdn SET  matricola='" + matricola + "',codice_azienda = '" + codice_azienda + "', data_nascita ='" + sdf2.format( sdf1.parse(data_nascita) ) + "', razza ='" +
									getCodiceRazza( razza ) + "', maschio=" + maschio + ", specie='" + getCodiceSpecie( specie ) + "'";
								
						
							pst=db.prepareStatement(sql);
							pst.execute();
						
							Integer codiceRazza = getCodiceRazza( razza );
							Integer codiceSpecie = getCodiceSpecie( specie );
							
							if(codiceRazza == -1 && codiceSpecie == -1 )
							{
								logOk.write( ("WARNING --> Il Capo e Stato Modificato . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la razza : "+razza+", E nessuna corrispondenza per la specie : "+specie+ "\n").getBytes());	
								nrRigheOk++;
							}
							else{
							
							if(codiceRazza == -1)
							{
								logOk.write( ("WARNING --> Il Capo e Stato Modificato . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la razza : "+razza+". Razza non identificata."+ "\n").getBytes());	
								nrRigheOk++;
							}else
							{
							if(codiceSpecie == -1)
							{
								logOk.write( ("WARNING --> Il Capo e Stato Modificato . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda +". Attenzione non e stata trovata nessuna corrispondenza per la specie : "+specie+". Specie non identificata."+ "\n").getBytes());	
								nrRigheOk++;
							}
							
							}
							}
							
							if(codiceRazza!=-1 && codiceSpecie!= -1)
								{
							
							logOk.write( ("OK --> Il Capo e Stato Modificato Correttamente . Matricola capo : "+matricola + " Codice azienda : "+codice_azienda + "\n").getBytes());	
							nrRigheOk++;
								}
							
						}
						
					}
				}
				}
			
				
				temp = br.readLine();
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	private static int getCodiceRazza( String text_code )
	{
		int ret = -1;
		
		if( text_code != null && razze.containsKey(text_code.trim()) )
		{
			ret = razze.get( text_code.trim() );
		}
		
		return ret;
	}
	
	private static int getCodiceSpecie( String text_code )
	{
		int ret = -1;
		
		if( text_code != null && specie.containsKey(text_code.trim()) )
		{
			ret = specie.get( text_code.trim() );
		}
		
		return ret;
	}
	
	public static String leggiCampiCapiAllevamenti(BufferedReader br,String filePath, Connection db) throws FileNotFoundException
	{
		
		startupRazze();
		startuoSpecie();
		return getInsertFile(br, filePath, db);
		
	
		
	}
	
	static int nrRigheOk 		= 0;
	static int nrRigheErr 		= 0;
	private static Hashtable<String, Integer> razze		= new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> specie	= new Hashtable<String, Integer>();

}
