/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.allevamenti.base.OrganizationAddress;
import org.aspcfs.modules.allevamenti.base.OrganizationAddressList;
import org.aspcfs.modules.allevamenti.base.OrganizationEmailAddress;
import org.aspcfs.modules.allevamenti.base.OrganizationEmailAddressList;
import org.aspcfs.modules.allevamenti.base.OrganizationPhoneNumber;
import org.aspcfs.modules.allevamenti.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.modules.stabilimenti.base.OrganizationList;
import org.aspcfs.modules.stabilimenti.base.SottoAttivita;
import org.aspcfs.modules.trasportoanimali.base.Personale;
import org.aspcfs.modules.trasportoanimali.base.Sede;
import org.aspcfs.modules.trasportoanimali.base.Veicolo;


//import org.aspcfs.modules.soa.base.OrganizationList;
//import org.aspcfs.modules.soa.base.SottoAttivita;




import com.darkhorseventures.framework.actions.ActionContext;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

public class LeggiFile {


	private static Logger log 	= Logger.getLogger(org.aspcfs.utils.LeggiFile.class);
	static int nrRigheOk 		= 0;
	static int nrRigheErr 		= 0;
	static String DIVISORIO 	= null;
	static String SEPARATORE 	= null;
	static int ASL				= -1;
	static int CODICE_AZ		= -1;
	static int DENOMINAZIONE	= -1;
	static int SPECIE_ALLEVATA	= -1;
	static int DATA_INIZIO		= -1;
	static int DATA_FINE		= -1;
	static int COMUNE			= -1;
	static int nrRigheOksa 		= 0;
	static int nrRigheOkstabAggiorn 		= 0;
	static int nrRigheOksaAgg 		= 0;



	public static String leggiCampiDistributori(ActionContext context, Connection db, File brr, String path, int userId,int org_id )
			throws IOException, SQLException, ParseException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		brr.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));

		POIFSFileSystem fs	= new POIFSFileSystem(new FileInputStream(brr));
		HSSFWorkbook wb		= new HSSFWorkbook(fs); 	
		HSSFSheet sheet		= wb.getSheetAt((short)0); //Fog
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for( int row_index=1; row_index <= sheet.getLastRowNum(); row_index++ )
		{

			org.aspcfs.modules.stabilimenti.base.Organization organization = new org.aspcfs.modules.stabilimenti.base.Organization();

			HSSFRow row = sheet.getRow(row_index);
			if(row != null)
			{

				java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());

				HSSFCell cell_1 = row.getCell((short)0); // matricola
				HSSFCell cell_2 = row.getCell((short)1); // data installazione
				HSSFCell cell_3 = row.getCell((short)2); // comune
				HSSFCell cell_4 = row.getCell((short)3); // indirizzo
				HSSFCell cell_5 = row.getCell((short)4); // provincia
				HSSFCell cell_6 = row.getCell((short)5); // cap
				HSSFCell cell_7 = row.getCell((short)6); // Ubicazione
				HSSFCell cell_8 = row.getCell((short)7); // Alimenti Distribuuiti
				HSSFCell cell_9 = row.getCell((short)8); // Note
				if(cell_1 != null || cell_2 != null || cell_3 != null || cell_4 != null || cell_5!=null)
				{

					Date data1				= null;
					String matricola 		= "";
					String dataInstall		= "";
					String comune 			= "" ;
					String indirizzo 		= "" ;
					String provincia 		= "" ;
					String cap 				= "" ;
					String ubicazione 		= "" ;
					int alimentoDistribuito = -1;
					String note 			= "" ;

					if(cell_1.getCellType() == 1)
						matricola 			= cell_1.getStringCellValue();
					else
						matricola = "" + cell_1.getNumericCellValue();
					if (cell_2.getCellType()==cell_2.CELL_TYPE_STRING)
					{
						dataInstall			= "" + cell_2.getStringCellValue();
					}
					else
					{
						data1			=  cell_2.getDateCellValue();
						dataInstall = null;
					}
					comune				= cell_3.getStringCellValue();
					indirizzo			= cell_4.getStringCellValue();
					provincia			= cell_5.getStringCellValue();

					if (cell_6.getCellType()==cell_6.CELL_TYPE_NUMERIC)
					{
						cap					= "" + cell_6.getNumericCellValue();
					}
					else
					{
						if (cell_6.getCellType()==cell_6.CELL_TYPE_STRING)
						{
							cap					= "" + cell_6.getStringCellValue();
						}
					}


					ubicazione			= cell_7.getStringCellValue();
					alimentoDistribuito	= (int) cell_8.getNumericCellValue();
					if (cell_9!=null)
					{
						if (cell_9.getCellType()==cell_9.CELL_TYPE_NUMERIC)
						{
							note				= ""+cell_9.getNumericCellValue();
						}
						else
						{
							if (cell_9.getCellType()==cell_9.CELL_TYPE_STRING)
							{
								note				= cell_9.getStringCellValue();
							}
						}

					}


					if(dataInstall != null)
						data1=sdf.parse(dataInstall);

					Distrubutore distributore=new Distrubutore(matricola,comune,indirizzo,""+cap,provincia,"","",note,data1,alimentoDistribuito,ubicazione);

					boolean inserito = false;
					boolean aggiornato = false;

					if(matricola==null || matricola.equals(""))
					{
						logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MANCA IL CAMPO MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
						nrRigheOk++;
						nrRigheErr++;
						log.info("Distributore NON inserito.MANCA CAMPO MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");

					}
					else{
						int id=distributore.checkIfExist(db, matricola, org_id);
						if(id!=-1){

							distributore.updateDistributore(db, org_id,id);
							aggiornato=true;
						}else{

							distributore.insert(db, org_id);
							inserito=true;

						}

						if( inserito == true )
						{

							logOk.write( ("OK --> DISTRIBUTORE INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
							nrRigheOk++;
							log.info("Distributore inserito.matricola : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
						}
						else if( aggiornato )
						{
							logErr.write( ("ERRORE --> DISTRIBUTORE GIa ESISTENTE -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
							nrRigheErr++;
							logOk.write( ("OK --> DISTRIBUTORE AGGIORNATO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	

							log.info("dISTRIBUTORE aggiornato.matricola : " +  matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
						}
						else
						{

							logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
							nrRigheErr++;
							log.info("Distributore NON inserito.MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
						}

					}
				}}

		}

		/*BufferedReader br = new BufferedReader(new FileReader(brr));
	String stringRead = br.readLine( );

	while(( stringRead != null )&&(stringRead.trim().length() > 0))
	{
		String[] valori=stringRead.split(";");

	if(valori !=null && valori.length!=0 ){
		String matricola=valori[0];



		if(!matricola.equalsIgnoreCase("Matricola")){
			Date data1=sdf.parse(valori[1]);
			String comune=valori[2];
			String provincia=valori[3];
			String indirizzo=valori[4];
			int cap=Integer.parseInt(valori[5]);
			String ubicazione=valori[6];
			String alimenti=valori[7];
			String note=valori[8];


		int alimentii=-1;
		if(alimenti!=null)
			alimentii=Integer.parseInt(alimenti);

		String sql="select description from lookup_tipo_distributore where description=?";
		java.sql.PreparedStatement pst=(java.sql.PreparedStatement)db.prepareStatement(sql);
		pst.setString(1, alimenti);
		ResultSet rs=pst.executeQuery();

		if(rs.next()){
			alimentii=rs.getInt(1);
		}


		Distrubutore distributore=new Distrubutore(matricola,comune,indirizzo,""+cap,provincia,"","",note,data1,alimentii,ubicazione);


		boolean inserito = false;
		boolean aggiornato = false;

		int id=distributore.checkIfExist(db, matricola, org_id);
		if(id!=-1){

			distributore.updateDistributore(db, org_id,id);
			aggiornato=true;
		}else{

			distributore.insert(db, org_id);
			inserito=true;

		}

			if( inserito == true )
		{
			//System.out.println("Stabilimento Inserito correttamente");
			logOk.write( ("OK --> DISTRIBUTORE INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("Distributore inserito.matricola : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}
		else if( aggiornato )
		{
			//System.out.println("Stabilimento Aggiornato correttamente");
			logOk.write( ("OK --> DISTRIBUTORE AGGIORNATO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("dISTRIBUTORE aggiornato.matricola : " +  matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}
		else
		{

			logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("Distributore NON inserito.MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}




}}
		stringRead = br.readLine( );	}*/

		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;

		return data;

	}


	public static String leggiCampiDistributoriCSV(ActionContext context, Connection db, File brr, String path, int userId,int org_id )
			throws IOException, SQLException, ParseException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		brr.renameTo(new File(context.getServletContext().getRealPath("WEB-INF")+File.separatorChar+"temp.xls"));

		TextConvertXlsToCsv.convert(brr,context.getServletContext().getRealPath("WEB-INF") );
		//TextConvertXlsToCsv.convert(brr.getPath(),context.getServletContext().getRealPath("templatexls"));

		File f = new File(context.getServletContext().getRealPath("WEB-INF")+File.separatorChar+"input.csv");

		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = br.readLine() ;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		while (line!= null && !line.equals(""))
		{
			String [] param = line.split(";" );
			Date data1				= null;
			String matricola 		= "";
			String dataInstall		= "";
			String comune 			= "" ;
			String indirizzo 		= "" ;
			String provincia 		= "" ;
			String cap 				= "" ;
			String ubicazione 		= "" ;
			int alimentoDistribuito = -1;
			String note 			= "" ;


			matricola = param[0] ;

			dataInstall = param[1];

			comune				= param [2];
			indirizzo			= param [3];
			provincia			= param [4];
			cap					= "" + param [5];;



			ubicazione			= param [6];
			alimentoDistribuito	= Integer.parseInt(param[7]);

			if (param.length == 9)
				note				= ""+param[8];



			if(dataInstall != null && ! dataInstall.equals(""))
				data1=sdf.parse(dataInstall);

			Distrubutore distributore=new Distrubutore(matricola,comune,indirizzo,""+cap,provincia,"","",note,data1,alimentoDistribuito,ubicazione);

			boolean inserito = false;
			boolean aggiornato = false;

			if(matricola==null || matricola.equals(""))
			{
				logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MANCA IL CAMPO MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
				nrRigheOk++;
				nrRigheErr++;
				log.info("Distributore NON inserito.MANCA CAMPO MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");

			}
			else{
				int id=distributore.checkIfExist(db, matricola, org_id);
				if(id!=-1){

					distributore.updateDistributore(db, org_id,id);
					aggiornato=true;
				}else{

					distributore.insert(db, org_id);
					inserito=true;

				}

				if( inserito == true )
				{

					logOk.write( ("OK --> DISTRIBUTORE INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheOk++;
					log.info("Distributore inserito.matricola : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
				}
				else if( aggiornato )
				{
					logErr.write( ("ERRORE --> DISTRIBUTORE GIa ESISTENTE -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheErr++;
					logOk.write( ("OK --> DISTRIBUTORE AGGIORNATO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	

					log.info("dISTRIBUTORE aggiornato.matricola : " +  matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
				}
				else
				{

					logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheErr++;
					log.info("Distributore NON inserito.MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
				}
			}


			line = br.readLine() ;
		}
		f.delete();	
		/*BufferedReader br = new BufferedReader(new FileReader(brr));
	String stringRead = br.readLine( );

	while(( stringRead != null )&&(stringRead.trim().length() > 0))
	{
		String[] valori=stringRead.split(";");

	if(valori !=null && valori.length!=0 ){
		String matricola=valori[0];



		if(!matricola.equalsIgnoreCase("Matricola")){
			Date data1=sdf.parse(valori[1]);
			String comune=valori[2];
			String provincia=valori[3];
			String indirizzo=valori[4];
			int cap=Integer.parseInt(valori[5]);
			String ubicazione=valori[6];
			String alimenti=valori[7];
			String note=valori[8];


		int alimentii=-1;
		if(alimenti!=null)
			alimentii=Integer.parseInt(alimenti);

		String sql="select description from lookup_tipo_distributore where description=?";
		java.sql.PreparedStatement pst=(java.sql.PreparedStatement)db.prepareStatement(sql);
		pst.setString(1, alimenti);
		ResultSet rs=pst.executeQuery();

		if(rs.next()){
			alimentii=rs.getInt(1);
		}


		Distrubutore distributore=new Distrubutore(matricola,comune,indirizzo,""+cap,provincia,"","",note,data1,alimentii,ubicazione);


		boolean inserito = false;
		boolean aggiornato = false;

		int id=distributore.checkIfExist(db, matricola, org_id);
		if(id!=-1){

			distributore.updateDistributore(db, org_id,id);
			aggiornato=true;
		}else{

			distributore.insert(db, org_id);
			inserito=true;

		}

			if( inserito == true )
		{
			//System.out.println("Stabilimento Inserito correttamente");
			logOk.write( ("OK --> DISTRIBUTORE INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("Distributore inserito.matricola : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}
		else if( aggiornato )
		{
			//System.out.println("Stabilimento Aggiornato correttamente");
			logOk.write( ("OK --> DISTRIBUTORE AGGIORNATO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("dISTRIBUTORE aggiornato.matricola : " +  matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}
		else
		{

			logErr.write( ("ERRORE --> DISTRIBUTORE NON INSERITO -->MATRICOLA : " + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
			nrRigheOk++;
			log.info("Distributore NON inserito.MATRICOLA : "  + matricola + " COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n");
		}




}}
		stringRead = br.readLine( );	}*/

		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;

		return data;

	}






	public static String calcolaMd5(File f) throws IOException{

		String md5 = "";
		byte[] buffer = new byte[(int) f.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(f);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}


		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return md5 ;
	}


	public static void leggiCampiDistributoriOpuCSV(ActionContext context, Connection db, File brr, int userId,int lda_macroarea, int lda_rel_stab, int stabId ,InvioMassivoDistributori invioMassivo,int idLinea)
			throws IOException, SQLException, ParseException, InvalidFileException
	{
		String data						= getData();





		if( brr.getName().toLowerCase().endsWith("txt") ||  brr.getName().toLowerCase().endsWith("csv"))

		{

			String md5File = calcolaMd5(brr);

			invioMassivo.setMd5(md5File);
			invioMassivo.getImportFormd5(db);

			if(invioMassivo.getId()>0)
			{
				invioMassivo.setId(-1);
				throw new InvalidFileException("File gia caricato nel sistema in data :"+invioMassivo.getData());

			}
			else
			{
				
				invioMassivo.insert(db);


				BufferedReader br = new BufferedReader(new FileReader(brr));
				String line = br.readLine() ;


				LineeMobiliHtmlFields gestoreInserimento = new LineeMobiliHtmlFields();
				int indice = 0 ; 
				while (line!= null && !line.equals(""))
				{
					if(indice>0)
					{
						String [] param = line.split(";" );

						String matricola 		= "";
						String dataInstall		= "";
						String comune 			= "" ;
						String indirizzo 		= "" ;
						String cap 				= "" ;
						String ubicazione 		= "" ;
						int alimentoDistribuito = -1;


						Distrubutore distributore = null ;
						try
						{



							try
							{
								matricola = param[0] ;
								dataInstall = param[1];
								ubicazione			= param [2];
								indirizzo			= param [3];
								comune				= param [4];
								cap					= "" + param [5];
							}
							catch(ArrayIndexOutOfBoundsException e)
							{
								distributore=new Distrubutore(matricola,dataInstall ,ubicazione,indirizzo,comune,""+cap,alimentoDistribuito);
								throw new InvalidFieldException("Il Tracciato Record del File non e' valido");
							}



							distributore=new Distrubutore(matricola,dataInstall ,ubicazione,indirizzo,comune,""+cap,alimentoDistribuito);
							distributore.setIdLineaAttivita(idLinea);
							distributore.controllavalidataCampi(db);

							HashMap<String, String> listaValori = new HashMap<String, String>();
							listaValori.put("alimento_distribuito", ""+distributore.getDescrizioneTipoAlimenti());
							listaValori.put("asl_distributore", distributore.getAsl());
							listaValori.put("cap", distributore.getCap());
							listaValori.put("comune", distributore.getComune());
							listaValori.put("data_installazione", distributore.getDataInst());
							listaValori.put("indirizzo", distributore.getIndirizzo());
							listaValori.put("matricola", distributore.getMatricola());
							listaValori.put("provincia", distributore.getProvincia());
							listaValori.put("ubicazione", distributore.getUbicazione());

							distributore.setIdStabilimento(stabId);
							String matricolaOut =distributore.controllaEsistenzaDistributoreInNuovaAnagrafica(db);
							if(!matricolaOut.equalsIgnoreCase("") ){

								gestoreInserimento.aggiornaDettaglioMobileDaImort(db, lda_macroarea, lda_rel_stab, stabId, listaValori, distributore.getId(), userId);
								invioMassivo.insertRecordImportatoKo(db, distributore, "OK-AGGIORNATO");

							}else{

								gestoreInserimento.insertDettaglioMobileDaImort(db, lda_macroarea, lda_rel_stab, stabId, listaValori, userId);
								invioMassivo.insertRecordImportatoKo(db, distributore, "OK-INSERITO");

							}

						}
						catch(InvalidFieldException e)
						{

							invioMassivo.insertRecordImportatoKo(db, distributore, e.getMessage());
							invioMassivo.setEsito("Warning - ci sono Record KO");
							invioMassivo.aggiornaEsito(db);
						}



					}
					line = br.readLine() ;
					indice ++ ;
				}

				if(invioMassivo.getEsito()== null || "".equals(invioMassivo.getEsito()))
				{
					invioMassivo.setEsito("OK");
					invioMassivo.aggiornaEsito(db);

				}

			}
		}
		else{
			throw new InvalidFileException("Tipo di File non Valido");
		}


	}





	public static String leggiCampiSedi(ActionContext context, Connection db, File brr, String path, int userId,int org_id )
			throws IOException, SQLException, ParseException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		brr.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));

		BufferedReader br = new BufferedReader(new FileReader(brr));
		String stringRead = br.readLine( );
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		while(( stringRead != null )&&(stringRead.trim().length() > 0))
		{
			String[] valori=stringRead.split(";");

			String comune=valori[0];
			if(!comune.equals("citta")){

				String indirizzo=valori[1];
				String provincia=valori[2];
				int cap=Integer.parseInt(valori[3]);
				String stato=valori[4];

				Sede sede=new Sede(comune,indirizzo,provincia,""+cap,stato);

				boolean inserito = false;
				boolean aggiornato = false;

				sede.insert(db, org_id);
				inserito=true;
				if( inserito == true )
				{
					//System.out.println("Stabilimento Inserito correttamente");
					logOk.write( ("OK --> SEDE INSERITO -->COMUNE : " +  comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheOk++;
					log.info("Sede inserito.comune : "  + comune + " INDIRIZZO : " + indirizzo + "\n");
				}
				else if( aggiornato )
				{
					//System.out.println("Stabilimento Aggiornato correttamente");
					logOk.write( ("OK --> SEDE AGGIORNATO -->COMUNE : " +  comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheOk++;
					log.info("Sede aggiornato.comune : " +  comune + " INDIRIZZO : " + indirizzo + "\n");
				}
				else
				{

					logErr.write( ("ERRORE --> SEDE NON INSERITO -->COMUNE : " + comune + " INDIRIZZO : " + indirizzo + "\n").getBytes());	
					nrRigheOk++;
					log.info("Sede NON inserito.COMUNE : "  + comune + " INDIRIZZO : " + indirizzo + "\n");
				}
			}
			stringRead = br.readLine( );	

		}

		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;

		return data;

	}

	public static String leggiCampiPersonale(ActionContext context, Connection db, File brr, String path, int userId,int org_id )
			throws IOException, SQLException, ParseException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		brr.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));

		BufferedReader br = new BufferedReader(new FileReader(brr));
		String stringRead = br.readLine( );
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		while(( stringRead != null )&&(stringRead.trim().length() > 0))
		{
			String[] valori=stringRead.split(";");

			String cf=valori[0];
			String nome=valori[1];
			String cognome=valori[2];
			String mansione=valori[3];

			Personale sede=new Personale(cf,nome,cognome,mansione);

			boolean inserito = false;
			boolean aggiornato = false;


			if(sede.checkIfExist(db, cf, org_id)==true){

				sede.updatePersonale(db, org_id);
				aggiornato=true;
			}else{

				sede.insert(db, org_id);
				inserito=true;

			}
			if( inserito == true )
			{
				//System.out.println("Stabilimento Inserito correttamente");
				logOk.write( ("OK --> PERSONALE INSERITO -->NOME : " +  nome + " COGNOME : " + cognome + "\n").getBytes());	
				nrRigheOk++;
				log.info("PERSONALE inserito.comune : NOME : " +  nome + " COGNOME : " + cognome + "\n");
			}
			else if( aggiornato )
			{
				//System.out.println("Stabilimento Aggiornato correttamente");
				logOk.write( ("OK --> PERSONALE AGGIORNATO -->NOME : " +  nome + " COGNOME : " + cognome + "\n").getBytes());	
				nrRigheOk++;
				log.info("PERSONALE aggiornato NOME : " +  nome + " COGNOME : " + cognome + "\n");
			}
			else
			{

				logErr.write( ("ERRORE --> PERSONALE NON INSERITO -->NOME : " +  nome + " COGNOME : " + cognome + "\n").getBytes());	
				nrRigheOk++;
				log.info("PERSONALE NON inserito NOME : " +  nome + " COGNOME : " + cognome + "\n");
			}

			stringRead = br.readLine( );	

		}


		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;

		return data;

	}


	public static String leggiCampiVeicoli(ActionContext context, Connection db, File brr, String path, int userId,int org_id )
			throws IOException, SQLException, ParseException
	{

		String data						= getData();


		try{

			FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
			FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
			FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

			//brr.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));

			BufferedReader br = new BufferedReader(new FileReader(brr));
			String stringRead = br.readLine( );
			//SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			while(( stringRead != null )&&(stringRead.trim().length() > 0))
			{
				String[] valori=stringRead.split(";");

				if(valori!=null || valori.length==2)
				{
					String descrizione=valori[0];

					if(valori[1]!=null){
						String targa=valori[1];


						Veicolo veicoli=new Veicolo(org_id,descrizione,targa);


						boolean inserito = false;
						boolean aggiornato = false;


						if(veicoli.checkIfExist(db, targa, org_id)==true){

							veicoli.updateVeicoli(db, org_id);
							aggiornato=true;
						}else{

							veicoli.insert(db, org_id);
							inserito=true;

						}


						if( inserito == true )
						{
							//System.out.println("Stabilimento Inserito correttamente");
							logOk.write( ("OK --> VEICOLO INSERITO -->TARGA : " + targa + " DESCRIZIONE : " + descrizione+"\n").getBytes());	
							nrRigheOk++;
							log.info("VEICOLO inserito.targa : "  + targa + " DESCRIZIONE : " + descrizione+"\n");
						}
						else if( aggiornato )
						{
							//System.out.println("Stabilimento Aggiornato correttamente");
							logOk.write( ("OK --> VEICOLO AGGIORNATO -->TARGA : " + targa + " DESCRIZIONE : " + descrizione + "\n").getBytes());	
							nrRigheOk++;
							log.info("VEICOLO aggiornato.targa : " +  targa + " DESCRIZIONE : " + descrizione + "\n");
						}
						else
						{

							logErr.write( ("ERRORE --> VEICOLO NON INSERITO -->TARGA : " + targa + " DESCRIZIONE : " + descrizione +"\n").getBytes());	
							nrRigheOk++;
							log.info("VEICOLO NON inserito.TARGA : "  + targa + " DESCRIZIONE : " + descrizione + "\n");
						}

					}else{
						return "errore";
					}

				}else{
					return "errore";

				}




				stringRead = br.readLine( );	

			}


			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

			nrRigheErr = 0;
			nrRigheOk  = 0;


		}catch(Exception e){
			e.printStackTrace();
			return "errore";
		}

		return data;

	}

	public static String leggiCampiOsm(ActionContext context, Connection db, File br, String path, int userId )
			throws IOException, SQLException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		br.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));
		POIFSFileSystem fs	= new POIFSFileSystem(new FileInputStream(br));
		HSSFWorkbook wb		= new HSSFWorkbook(fs); 	
		HSSFSheet sheet		= wb.getSheetAt((short)0); //Fog

		for( int row_index=1; row_index <= sheet.getLastRowNum(); row_index++ )
		{

			org.aspcfs.modules.osm.base.Organization organization = new org.aspcfs.modules.osm.base.Organization();

			HSSFRow row = sheet.getRow(row_index);
			if(row != null)
			{

				java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());


				boolean flagErrore=false;
				boolean flagWarning=false;

				HSSFCell cell_1 = row.getCell((short)0);
				HSSFCell cell_2 = row.getCell((short)1);
				HSSFCell cell_3 = row.getCell((short)2);
				HSSFCell cell_4 = row.getCell((short)3);
				HSSFCell cell_5 = row.getCell((short)4);
				HSSFCell cell_6 = row.getCell((short)5);
				HSSFCell cell_7 = row.getCell((short)6);
				HSSFCell cell_8 = row.getCell((short)7);
				HSSFCell cell_9 = row.getCell((short)8);
				HSSFCell cell_10 = row.getCell((short)9);
				HSSFCell cell_11 = row.getCell((short)10);
				HSSFCell cell_12 = row.getCell((short)11);
				HSSFCell cell_13 = row.getCell((short)12);
				HSSFCell cell_14 = row.getCell((short)13);
				HSSFCell cell_15 = row.getCell((short)14);
				HSSFCell cell_16 = row.getCell((short)15);
				HSSFCell cell_17 = row.getCell((short)16);
				HSSFCell cell_18 = row.getCell((short)17);
				HSSFCell cell_19 = row.getCell((short)18);
				HSSFCell cell_20 = row.getCell((short)19);
				HSSFCell cell_21 = row.getCell((short)20);
				HSSFCell cell_22 = row.getCell((short)21);
				HSSFCell cell_23 = row.getCell((short)22);


				int progressivoStabilimento = (int)cell_1.getNumericCellValue();

				int approval = 0;
				String approvaAsString = "";


				if(cell_3!=null){
					String ragioneSociale=cell_3.getStringCellValue();

					if( cell_2.getCellType() == 0 )
						approvaAsString = "" + (int)cell_2.getNumericCellValue();
					else
						approvaAsString = cell_2.getStringCellValue();




					String inmdirizzo			= cell_4.getStringCellValue();
					String ragioneSocialePrec	= cell_5.getStringCellValue();
					String pIva					= "";

					if( cell_6.getCellType() == 0 )
						pIva = "" + (int) cell_6.getNumericCellValue();
					else
						pIva = cell_6.getStringCellValue();


					String cf							= "";

					if( cell_7.getCellType() == 0 )
						cf = "" + (int) cell_7.getNumericCellValue();
					else
						cf = cell_7.getStringCellValue();

					String comune						= cell_8.getStringCellValue();
					String siglaProvincia				= cell_9.getStringCellValue();
					//int codiceRagione					= (int) cell_10.getNumericCellValue();   			//NON SERVE
					Integer codiceServizioVeterinario	= (int) cell_11.getNumericCellValue();

					int site_id = -1;

					if(codiceServizioVeterinario!=null)
					{
						switch(codiceServizioVeterinario)
						{
						case 15 :
							site_id=1;
							break;
						case 35:
							site_id=2;
							break;
						case 55 :
							site_id=3;
							break;
						case 74 :
							site_id=4;
							break;
						case 93 :
							site_id=5;
							break;
						case 171:
							site_id=11;
							break;
						case 182 :
							site_id=12;
							break;
						case 190 :
							site_id=13;
							break;
						case 110 :
							site_id=6;
							break;
						case 123 :
							site_id=7;
							break;
						case 136 :
							site_id=8;
							break;
						case 149 :
							site_id=9;
							break;
						case 160 :
							site_id=10;
							break;
						default :
							site_id=-1;
						}
					}


					if(site_id==-1){

						String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune.replaceAll("'", "''")+"'";
						PreparedStatement pst=db.prepareStatement(sql);
						ResultSet rsResultSet=pst.executeQuery();
						if(rsResultSet.next())
							site_id=rsResultSet.getInt(1);
					}



					int statoStabilimento			= (int)cell_12.getNumericCellValue();				//NON SERVE
					String descrizioneStatoAttivita	= cell_13.getStringCellValue();
					//String statoAttivita			= cell_18.getStringCellValue();

					int statoLab = -1;



					if(statoStabilimento==0 || statoStabilimento==1 || statoStabilimento==2 || statoStabilimento==3)
						statoLab=statoStabilimento;






					int codiceSezione		= (int)cell_14.getNumericCellValue();
					String categoria		= "";
					String attivita			= cell_15.getStringCellValue();
					Integer codiceImpianto	= (int)cell_16.getNumericCellValue();
					//String progressivoAttivita=cell_17.getStringCellValue();

					Date dataInizioAttivita = null;


					if(cell_19.getCellType()!=cell_19.CELL_TYPE_STRING)
						dataInizioAttivita=cell_19.getDateCellValue();

					Date dataFineAttivita = null;
					if(cell_20.getCellType()!=cell_20.CELL_TYPE_STRING)
						dataFineAttivita=cell_20.getDateCellValue();



					String tipoAutorizzazione = cell_21.getStringCellValue();

					String descrTipoAutorizzazione = "";
					if(tipoAutorizzazione.equalsIgnoreCase("D"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE DEFINITA";
					}
					else if(tipoAutorizzazione.equalsIgnoreCase("C"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE";
					}

					String ritiReligiosi	= cell_22.getStringCellValue();
					Integer imballata		= (int)cell_23.getNumericCellValue();


					if(approvaAsString!=null && ( approvaAsString.equals("-") || approvaAsString.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> STABILIMENTO  NON INSERITO PER MANCANZA DEL CAMPO APPROVAL NUMBER (CAMPO USATO COME CHIAVE DI RICERCA). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(codiceImpianto!=null && ( codiceImpianto.equals("-") || codiceImpianto.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> STABILIMENTO  NON INSERITO PER MANCANZA DEL CAMPO CODICE IMPIANTO (CAMPO USATO COME CHIAVE DI RICERCA PER SOTTOATTIVITa). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}












					if(flagErrore==false){

						org.aspcfs.modules.osm.base.Organization old_organization = org.aspcfs.modules.osm.base.Organization.load(approvaAsString, db );

						organization = new org.aspcfs.modules.osm.base.Organization();
						organization.setSiteId (site_id);
						organization.setCategoria(categoria);

						if(approvaAsString != null)
						{
							if( !approvaAsString.equals("-") )
								organization.setNumAut(approvaAsString);
							else
								organization.setNumAut("");
						}

						if(ragioneSociale != null)
						{
							if(!ragioneSociale.equals("-"))
								organization.setName(ragioneSociale);
							else
								organization.setName("");
						}

						if(ragioneSocialePrec != null)
						{
							if(!ragioneSocialePrec.equals("-"))
								organization.setBanca(ragioneSocialePrec);
							else
								organization.setBanca("");
						}

						if(pIva != null)
						{
							if(!pIva.equals("-"))
								organization.setPartitaIva(pIva);
							else
								organization.setPartitaIva("");
						}

						if(cf != null)
						{
							if(!cf.equals("-"))
								organization.setCodiceFiscale(cf);
							else
								organization.setCodiceFiscale("");
						}

						//LOOKUP
						organization.setStatoLab(statoLab);

						if(attivita != null)
						{
							if(!attivita.equals("-"))
							{
								organization.setImpianto(organization.getIdImpianto(attivita, db) + "");
							}
							else
								organization.setImpianto("");
						}

						if(codiceImpianto != null)
						{
							if(!codiceImpianto.equals("-"))
								organization.setCodiceImpianto(codiceImpianto + "");
							else
								organization.setCodiceImpianto("");
						}

						if(tipoAutorizzazione != null)
						{
							if(!tipoAutorizzazione.equals("-"))
								organization.setTipoAutorizzazzione(descrTipoAutorizzazione);
							else
								organization.setTipoAutorizzazzione("");
						}

						if(ritiReligiosi != null)
						{
							if(!ritiReligiosi.equals("-"))
								organization.setRitiReligiosi(ritiReligiosi);
							else
								organization.setRitiReligiosi("");
						}

						if(imballata != null)
						{
							if(!imballata.equals("-"))
								organization.setImballata(imballata);
							else
								organization.setImballata(-1);
						}

						organization.setOwner(userId);
						organization.setEnteredBy(userId);

						if(dataInizioAttivita!=null)
							organization.setDate2(new Timestamp(dataInizioAttivita.getTime()));

						if(dataFineAttivita!=null)
							organization.setContractEndDate(new Timestamp(dataFineAttivita.getTime()));

						org.aspcfs.modules.osm.base.OrganizationAddress ind = new org.aspcfs.modules.osm.base.OrganizationAddress();
						ind.setCity(comune);
						ind.setStreetAddressLine1(inmdirizzo);
						ind.setState(siglaProvincia);
						org.aspcfs.modules.osm.base.OrganizationAddressList address = new org.aspcfs.modules.osm.base.OrganizationAddressList();



						int orgid=-1;
						if(old_organization!=null)
							orgid=old_organization.getOrgId();



						ind.setId(address.getIdfromaddress(db, inmdirizzo, comune, siglaProvincia,orgid));

						ind.setType(5);
						address.add(ind);
						organization.setAddressList(address);
						int idImpianto = organization.getIdImpianto(db,attivita);
						organization.setImpianto(idImpianto);// accetta intero
						//organization.setCodiceImpianto("");// Stringa

						if( approvaAsString.equals("-") && statoLab == 3 )
						{
							approvaAsString = " ";
							organization.setNumAut(approvaAsString);
						}

						try
						{

							//int statoLabA = organization.checkIfExistsUpload(db,organization.getNumAut(),categoria,idImpianto);

							boolean inserito = false;
							boolean aggiornato = false;






							if( old_organization != null )
							{


								organization.setModifiedBy( userId );
								organization.setOrgId( old_organization.getOrgId() );
								organization.setModified( old_organization.getModified() );

								organization.update(db,context);
								aggiornato = true;
							}
							else
							{
								if(ragioneSociale==null || ragioneSociale.equals(""))
									organization.setName("-");

								inserito = organization.insert(db,context);
							}

							if( inserito == true )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}



								if(flagWarning==true){
									logOk.write( ("WARNING --> STABILIMENTO INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info ("WARNING --> STABILIMENTO INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

								}else{
									logOk.write( ("SUCCESS --> STABILIMENTO INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info("SUCCESS --> STABILIMENTO INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}

							}
							else if( aggiornato )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}


								if(flagWarning==true){
									logOk.write( ("WARNING --> STABILIMENTO AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("WARNING --> STABILIMENTO AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

								}else{
									logOk.write( ("SUCCESS --> STABILIMENTO AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("SUCCESS --> STABILIMENTO AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}
							}
							else
							{

								logErr.write( ("Stabilimento NON inserito. (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
								nrRigheErr++;
								log.info("Stabilimento NON inserito. (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");
							}

						}
						catch (SQLException e)
						{
							System.out.println("non inserito, andato in catch");
							logErr.write( ("ERRORE NON CONFORME (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());
							nrRigheErr++;
							log.warn("si e verificato un errore nell'inserimento/aggiornamento dello stabilimento APPROVAL_NUMBER : "+approvaAsString+" RAGIONE SOCIALE : "+ragioneSociale+" impianto : "+attivita);
							e.printStackTrace();
						}


						organization = org.aspcfs.modules.osm.base.Organization.load( approvaAsString, db );




						if( organization != null )
						{
							Timestamp now = new Timestamp( System.currentTimeMillis() );

							SottoAttivita sa = new SottoAttivita();
							sa.setAttivita					( attivita );
							sa.setCodice_impianto			( codiceImpianto );
							sa.setCodice_sezione			( codiceSezione );
							//sa.setData_fine_attivitaString	( dataFineAttivita );
							if(dataInizioAttivita!=null)
								sa.setData_inizio_attivita(new Timestamp( dataInizioAttivita.getTime()) );
							if(dataFineAttivita!=null)
								sa.setData_fine_attivita(new Timestamp(dataFineAttivita.getTime()));
							sa.setDescrizione_stato_attivita( descrizioneStatoAttivita );
							sa.setEnabled					( true );
							sa.setEntered_by				( userId );
							sa.setEntered					( now );
							sa.setId_stabilimento			( organization.getOrgId() );
							sa.setImballata					( imballata );
							sa.setModified					( now );
							sa.setModified_by				( userId );
							sa.setRiti_religiosi			( "S".equalsIgnoreCase( ritiReligiosi ) );
							sa.setStato_attivita			( statoLab );
							sa.setTipo_autorizzazione		( "D".equalsIgnoreCase( tipoAutorizzazione ) ? (1) : (2) );

							try
							{



								if(attivita!=null && (attivita.equals("") || attivita.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO ATTIVITA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}

								if(codiceImpianto!=null && (codiceImpianto.equals("") || codiceImpianto.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO CODICE IMPIANTO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}





								if(flagErrore==false){

									if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(comune!=null && (comune.equals("") || comune.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}


									if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}



									if(dataInizioAttivita==null ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(site_id==-1 ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}
									if(statoLab==-1 ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}

									if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}




									SottoAttivita old = sa.alreadyExist( db );
									if( old != null )
									{
										sa.setId		( old.getId() );
										sa.setEntered	( old.getEntered() );
										sa.setEntered_by( old.getEntered_by() );

										sa.update( db );

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}
									else
									{

										sa.store( db,context );

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}}

							}
							catch (Exception e)
							{
								System.out.println( "errore generico" );
								e.printStackTrace();
							}
						}
						else
						{
							System.out.println( "errore generico" );
						}
					}}
			}
		}


		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;


		try{


			String sql="select * from organization where tipologia=800 and to_date(entered::text,'yyyy-MM-dd') < current_date and to_date(modified::text,'yyyy-MM-dd') < current_date";
			java.sql.PreparedStatement pst=  db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			String sql2="update organization set stato_lab=1 where org_id=? and tipologia=800";

			String sql3="update stabilimenti_sottoattivita set stato_attivita=1,descrizione_stato_attivita='revocata' where id_stabilimento=?";
			java.sql.PreparedStatement pst3=  db.prepareStatement(sql3);
			java.sql.PreparedStatement pst2=db.prepareStatement(sql2);


			java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());

			int count=0;
			while(rs.next()){

				int org_id=rs.getInt("org_id");
				java.sql.Date dataAggiornamento=rs.getDate("modified");
				String approval_number=rs.getString("numaut");
				String ragioneSociale=rs.getString("name");
				pst2.setInt(1, org_id);
				pst2.execute();
				count++;
				logOk.write( ("REVOCA --> STABILIMENTO REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " +dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+ "\n").getBytes());	

				logErr.write( ("REVOCA --> STABILIMENTO REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " + dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+"\n").getBytes());	



				log.info("Stabilimento Revocato Perche non Presente nel File Sintesi Ragione Sociale : " + ragioneSociale +"\n");

				pst3.setInt(1, org_id);
				pst3.execute();


			}

			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg+"\n Stabilimenti revocati : "+count ).getBytes() );


		}catch(Exception e){
			System.out.println("Errore nell'aggiornamento degli stabilimenti presenti in banca dati");
			e.printStackTrace();
		}


		return data;

	}	




	public static String leggiCampiStabilimenti(ActionContext context, Connection db, File br, String path, int userId )
			throws IOException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{



		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		br.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));
		POIFSFileSystem fs	= new POIFSFileSystem(new FileInputStream(br));
		HSSFWorkbook wb		= new HSSFWorkbook(fs); 	
		HSSFSheet sheet		= wb.getSheetAt((short)0); //Fog

		for( int row_index=1; row_index <= sheet.getLastRowNum(); row_index++ )
		{


			org.aspcfs.modules.stabilimenti.base.Organization organization = new org.aspcfs.modules.stabilimenti.base.Organization();

			HSSFRow row = sheet.getRow(row_index);
			if(row != null)
			{

				java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());


				boolean flagErrore=false;
				boolean flagWarning=false;

				HSSFCell cell_1 = row.getCell((short)0);
				HSSFCell cell_2 = row.getCell((short)1);
				HSSFCell cell_3 = row.getCell((short)2);
				HSSFCell cell_4 = row.getCell((short)3);
				HSSFCell cell_5 = row.getCell((short)4);
				HSSFCell cell_6 = row.getCell((short)5);
				HSSFCell cell_7 = row.getCell((short)6);
				HSSFCell cell_8 = row.getCell((short)7);
				HSSFCell cell_9 = row.getCell((short)8);
				HSSFCell cell_10 = row.getCell((short)9);
				HSSFCell cell_11 = row.getCell((short)10);
				HSSFCell cell_12 = row.getCell((short)11);
				HSSFCell cell_13 = row.getCell((short)12);
				HSSFCell cell_14 = row.getCell((short)13);
				HSSFCell cell_15 = row.getCell((short)14);
				HSSFCell cell_16 = row.getCell((short)15);
				HSSFCell cell_17 = row.getCell((short)16);
				HSSFCell cell_18 = row.getCell((short)17);
				HSSFCell cell_19 = row.getCell((short)18);
				HSSFCell cell_20 = row.getCell((short)19);
				HSSFCell cell_21 = row.getCell((short)20);
				HSSFCell cell_22 = row.getCell((short)21);
				HSSFCell cell_23 = row.getCell((short)22);


				int progressivoStabilimento = (int)cell_1.getNumericCellValue();

				int approval = 0;
				String approvaAsString = "";


				if(cell_3!=null){
					String ragioneSociale=cell_3.getStringCellValue();

					if( cell_2.getCellType() == 0 )
						approvaAsString = "" + (int)cell_2.getNumericCellValue();
					else
						approvaAsString = cell_2.getStringCellValue();




					String inmdirizzo			= cell_4.getStringCellValue();
					String ragioneSocialePrec	= cell_5.getStringCellValue();
					String pIva					= "";

					if( cell_6.getCellType() == 0 )
						pIva = "" + (int) cell_6.getNumericCellValue();
					else
						pIva = cell_6.getStringCellValue();


					String cf							= "";

					if( cell_7.getCellType() == 0 )
						cf = "" + (int) cell_7.getNumericCellValue();
					else
						cf = cell_7.getStringCellValue();

					String comune						= cell_8.getStringCellValue();
					String siglaProvincia				= cell_9.getStringCellValue();
					//int codiceRagione					= (int) cell_10.getNumericCellValue();   			//NON SERVE
					Integer codiceServizioVeterinario	= (int) cell_11.getNumericCellValue();

					int site_id = -1;

					if(codiceServizioVeterinario!=null)
					{
						switch(codiceServizioVeterinario)
						{
						case 15 :
							site_id=1;
							break;
						case 35:
							site_id=2;
							break;
						case 55 :
							site_id=3;
							break;
						case 74 :
							site_id=4;
							break;
						case 93 :
							site_id=5;
							break;
						case 171:
							site_id=11;
							break;
						case 182 :
							site_id=12;
							break;
						case 190 :
							site_id=13;
							break;
						case 110 :
							site_id=6;
							break;
						case 123 :
							site_id=7;
							break;
						case 136 :
							site_id=8;
							break;
						case 149 :
							site_id=9;
							break;
						case 160 :
							site_id=10;
							break;
						default :
							site_id=-1;
						}
					}


					if(site_id==-1){

						String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune.replaceAll("'", "''")+"'";
						PreparedStatement pst=db.prepareStatement(sql);
						ResultSet rsResultSet=pst.executeQuery();
						if(rsResultSet.next())
							site_id=rsResultSet.getInt(1);
					}




					int statoStabilimento			= (int)cell_12.getNumericCellValue();				//NON SERVE
					String descrizioneStatoAttivita	= cell_13.getStringCellValue();
					//String statoAttivita			= cell_18.getStringCellValue();

					int statoLab = -1;



					if(statoStabilimento==0 || statoStabilimento==1 || statoStabilimento==2 || statoStabilimento==3)
						statoLab=statoStabilimento;






					int codiceSezione		= (int)cell_14.getNumericCellValue();
					String categoria		= "";
					String attivita			= cell_15.getStringCellValue();
					Integer codiceImpianto	= (int)cell_16.getNumericCellValue();
					//String progressivoAttivita=cell_17.getStringCellValue();

					Date dataInizioAttivita = null;


					if(cell_19.getCellType()!=cell_19.CELL_TYPE_STRING)
						dataInizioAttivita=cell_19.getDateCellValue();

					Date dataFineAttivita = null;
					if(cell_20.getCellType()!=cell_20.CELL_TYPE_STRING)
						dataFineAttivita=cell_20.getDateCellValue();



					String tipoAutorizzazione = cell_21.getStringCellValue();

					String descrTipoAutorizzazione = "";
					if(tipoAutorizzazione.equalsIgnoreCase("D"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE DEFINITA";
					}
					else if(tipoAutorizzazione.equalsIgnoreCase("C"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE";
					}

					String ritiReligiosi	= cell_22.getStringCellValue();
					Integer imballata		= (int)cell_23.getNumericCellValue();


					if(approvaAsString!=null && ( approvaAsString.equals("-") || approvaAsString.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> STABILIMENTO  NON INSERITO PER MANCANZA DEL CAMPO APPROVAL NUMBER (CAMPO USATO COME CHIAVE DI RICERCA). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(codiceImpianto!=null && ( codiceImpianto.equals("-") || codiceImpianto.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> STABILIMENTO  NON INSERITO PER MANCANZA DEL CAMPO CODICE IMPIANTO (CAMPO USATO COME CHIAVE DI RICERCA PER SOTTOATTIVITa). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}


					System.out.print("Ho letto i dati.");
					System.out.println("connessione chiusa ? "+db.isClosed());








					if(flagErrore==false){

						org.aspcfs.modules.stabilimenti.base.Organization old_organization = org.aspcfs.modules.stabilimenti.base.Organization.load(approvaAsString, db,"organization" );

						organization = new org.aspcfs.modules.stabilimenti.base.Organization();
						organization.setSiteId (site_id);
						organization.setCategoria(categoria);

						if(approvaAsString != null)
						{
							if( !approvaAsString.equals("-") )
								organization.setNumAut(approvaAsString);
							else
								organization.setNumAut("");
						}

						if(ragioneSociale != null)
						{
							if(!ragioneSociale.equals("-"))
								organization.setName(ragioneSociale);
							else
								organization.setName("");
						}

						if(ragioneSocialePrec != null)
						{
							if(!ragioneSocialePrec.equals("-"))
								organization.setBanca(ragioneSocialePrec);
							else
								organization.setBanca("");
						}

						if(pIva != null)
						{
							if(!pIva.equals("-"))
								organization.setPartitaIva(pIva);
							else
								organization.setPartitaIva("");
						}

						if(cf != null)
						{
							if(!cf.equals("-"))
								organization.setCodiceFiscale(cf);
							else
								organization.setCodiceFiscale("");
						}

						//LOOKUP
						organization.setStatoLab(statoLab);

						if(attivita != null)
						{
							if(!attivita.equals("-"))
							{
								organization.setImpianto(organization.getIdImpianto(attivita, db) + "");
							}
							else
								organization.setImpianto("");
						}

						if(codiceImpianto != null)
						{
							if(!codiceImpianto.equals("-"))
								organization.setCodiceImpianto(codiceImpianto + "");
							else
								organization.setCodiceImpianto("");
						}

						if(tipoAutorizzazione != null)
						{
							if(!tipoAutorizzazione.equals("-"))
								organization.setTipoAutorizzazzione(descrTipoAutorizzazione);
							else
								organization.setTipoAutorizzazzione("");
						}

						if(ritiReligiosi != null)
						{
							if(!ritiReligiosi.equals("-"))
								organization.setRitiReligiosi(ritiReligiosi);
							else
								organization.setRitiReligiosi("");
						}

						if(imballata != null)
						{
							if(!imballata.equals("-"))
								organization.setImballata(imballata);
							else
								organization.setImballata(-1);
						}

						organization.setOwner(userId);
						organization.setEnteredBy(userId);

						if(dataInizioAttivita!=null)
							organization.setDate2(new Timestamp(dataInizioAttivita.getTime()));

						if(dataFineAttivita!=null)
							organization.setContractEndDate(new Timestamp(dataFineAttivita.getTime()));

						org.aspcfs.modules.stabilimenti.base.OrganizationAddress ind = new org.aspcfs.modules.stabilimenti.base.OrganizationAddress();
						ind.setCity(comune);
						ind.setStreetAddressLine1(inmdirizzo);
						ind.setState(siglaProvincia);
						org.aspcfs.modules.stabilimenti.base.OrganizationAddressList address = new org.aspcfs.modules.stabilimenti.base.OrganizationAddressList();



						int orgid=-1;
						if(old_organization!=null)
							orgid=old_organization.getOrgId();



						ind.setId(address.getIdfromaddress(db, inmdirizzo, comune, siglaProvincia,orgid));

						ind.setType(5);
						address.add(ind);
						organization.setAddressList(address);
						int idImpianto = organization.getIdImpianto(db,attivita);
						organization.setImpianto(idImpianto);// accetta intero
						//organization.setCodiceImpianto("");// Stringa

						if( approvaAsString.equals("-") && statoLab == 3 )
						{
							approvaAsString = " ";
							organization.setNumAut(approvaAsString);
						}

						try
						{

							//int statoLabA = organization.checkIfExistsUpload(db,organization.getNumAut(),categoria,idImpianto);

							boolean inserito = false;
							boolean aggiornato = false;






							if( old_organization != null )
							{


								organization.setModifiedBy( userId );
								organization.setOrgId( old_organization.getOrgId() );
								organization.setModified( old_organization.getModified() );

								organization.update(db,context);
								aggiornato = true;
							}
							else
							{
								if(ragioneSociale==null || ragioneSociale.equals(""))
									organization.setName("-");
								System.out.print("Sto prima della insert.");
								inserito = organization.insert(db,context);
								System.out.print("Sto dopo la insert");
							}

							if( inserito == true )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}



								if(flagWarning==true){
									logOk.write( ("WARNING --> STABILIMENTO INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info ("WARNING --> STABILIMENTO INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

								}else{
									logOk.write( ("SUCCESS --> STABILIMENTO INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info("SUCCESS --> STABILIMENTO INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}

							}
							else if( aggiornato )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}


								if(flagWarning==true){
									logOk.write( ("WARNING --> STABILIMENTO AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("WARNING --> STABILIMENTO AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

								}else{
									logOk.write( ("SUCCESS --> STABILIMENTO AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("SUCCESS --> STABILIMENTO AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}
							}
							else
							{

								logErr.write( ("Stabilimento NON inserito. (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
								nrRigheErr++;
								log.info("Stabilimento NON inserito. (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");
							}

						}
						catch (SQLException e)
						{
							System.out.println("non inserito, andato in catch");
							logErr.write( ("ERRORE NON CONFORME (PROGRESSIVO SISTEMA STABILIMENTO) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());
							nrRigheErr++;
							log.warn("si e verificato un errore nell'inserimento/aggiornamento dello stabilimento APPROVAL_NUMBER : "+approvaAsString+" RAGIONE SOCIALE : "+ragioneSociale+" impianto : "+attivita);
							e.printStackTrace();
						}


						organization = org.aspcfs.modules.stabilimenti.base.Organization.load( approvaAsString, db,"organization" );




						if( organization != null )
						{
							Timestamp now = new Timestamp( System.currentTimeMillis() );

							SottoAttivita sa = new SottoAttivita();
							sa.setAttivita					( attivita );
							sa.setCodice_impianto			( codiceImpianto );
							sa.setCodice_sezione			( codiceSezione );
							//sa.setData_fine_attivitaString	( dataFineAttivita );
							if(dataInizioAttivita!=null)
								sa.setData_inizio_attivita(new Timestamp( dataInizioAttivita.getTime()) );
							if(dataFineAttivita!=null)
								sa.setData_fine_attivita(new Timestamp(dataFineAttivita.getTime()));
							sa.setDescrizione_stato_attivita( descrizioneStatoAttivita );
							sa.setEnabled					( true );
							sa.setEntered_by				( userId );
							sa.setEntered					( now );
							sa.setId_stabilimento			( organization.getOrgId() );
							sa.setImballata					( imballata );
							sa.setModified					( now );
							sa.setModified_by				( userId );
							sa.setRiti_religiosi			( "S".equalsIgnoreCase( ritiReligiosi ) );
							sa.setStato_attivita			( statoLab );
							sa.setTipo_autorizzazione		( "D".equalsIgnoreCase( tipoAutorizzazione ) ? (1) : (2) );

							try
							{



								if(attivita!=null && (attivita.equals("") || attivita.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO ATTIVITA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}

								if(codiceImpianto!=null && (codiceImpianto.equals("") || codiceImpianto.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO CODICE IMPIANTO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}





								if(flagErrore==false){

									if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(comune!=null && (comune.equals("") || comune.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}


									if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}



									if(dataInizioAttivita==null ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(site_id==-1 ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}
									if(statoLab==-1 ){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}

									if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

										logErr.write( ("WARNING --> STABILIMENTO  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}




									SottoAttivita old = sa.alreadyExist( db );
									if( old != null )
									{
										sa.setId		( old.getId() );
										sa.setEntered	( old.getEntered() );
										sa.setEntered_by( old.getEntered_by() );

										sa.update( db );

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}
									else
									{

										sa.store( db,context );

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}}

							}
							catch (Exception e)
							{
								System.out.println( "errore generico" );
								e.printStackTrace();
							}
						}
						else
						{
							System.out.println( "errore generico" );
						}
					}}
				System.out.println("Inserito o Aggiornato lo stabilimenti con progressivo stabilimento "+progressivoStabilimento);

			}

		}



		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;

		System.out.println("Fine dell'inserimento dei dati presenti nel FILE SINTESI");

		try{


			String sql="select * from organization where tipologia=3 and to_date(entered::text,'yyyy-MM-dd') < current_date and to_date(modified::text,'yyyy-MM-dd') < current_date";
			java.sql.PreparedStatement pst=  db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			String sql2="update organization set stato_lab=1 where org_id=? and tipologia=3";

			String sql3="update stabilimenti_sottoattivita set stato_attivita=1,descrizione_stato_attivita='revocata' where id_stabilimento=?";
			java.sql.PreparedStatement pst3=  db.prepareStatement(sql3);
			java.sql.PreparedStatement pst2=db.prepareStatement(sql2);


			java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());

			System.out.println("Inizio controllo per gli stabilimenti non presenti nel file di SINTESI con update Per Revoca");
			int count=0;
			while(rs.next()){

				int org_id=rs.getInt("org_id");
				java.sql.Date dataAggiornamento=rs.getDate("modified");
				String approval_number=rs.getString("numaut");
				String ragioneSociale=rs.getString("name");
				pst2.setInt(1, org_id);
				pst2.execute();
				count++;
				logOk.write( ("REVOCA --> STABILIMENTO REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " +dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+ "\n").getBytes());	

				logErr.write( ("REVOCA --> STABILIMENTO REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " + dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+"\n").getBytes());	


				System.out.println("Revoca dello stavilimento con account number "+approval_number);

				log.info("Stabilimento Revocato Perche non Presente nel File Sintesi Ragione Sociale : " + ragioneSociale +"\n");

				pst3.setInt(1, org_id);
				pst3.execute();


			}

			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg+"\n Stabilimenti revocati : "+count ).getBytes() );


		}catch(Exception e){
			System.out.println("Errore nell'aggiornamento degli stabilimenti presenti in banca dati");
			e.printStackTrace();
		}


		return data;

	}



	public static String leggiCampi(ActionContext context, Connection db, BufferedReader br, String path, String specieA, int userId ) throws IOException{

		String data = getData();
		boolean recordDeleted = false;
		FileOutputStream logOk =  new FileOutputStream( path+data+"_ok.txt" ) ;
		FileOutputStream logErr =  new FileOutputStream( path+data+"_err.txt" ) ;
		FileOutputStream logRiepilogo =  new FileOutputStream( path+data+"_rpg.txt" ) ;
		String codiceasl = null;
		String codiceAzienda = null;
		String nome_asl = null;
		nrRigheErr = 0;
		nrRigheOk = 0;
		try {

			//initializeProperties();
			String stringRead = br.readLine( );
			while(( stringRead != null )&&(stringRead.trim().length() > 0))
			{
				int asl = -1;

				codiceasl = stringRead.substring(1, 4);
				if(codiceasl.equals("101")){
					asl = 1;
					nome_asl = "Avellino1";

				}else if(codiceasl.equals("102")){
					asl = 2;
					nome_asl = "Avellino2";

				}else if(codiceasl.equals("103")){
					asl = 3;
					nome_asl = "Benevento1";
				}
				else if(codiceasl.equals("104")){
					asl = 4;
					nome_asl = "Caserta1";
				}
				else if(codiceasl.equals("105")){
					asl = 5;
					nome_asl = "Caserta2";
				}
				else if(codiceasl.equals("106")){
					asl = 6;
					nome_asl = "Napoli1";
				}
				else if(codiceasl.equals("107")){
					asl = 7;
					nome_asl = "Napoli2";
				}
				else if(codiceasl.equals("108")){
					asl = 8;
					nome_asl = "Napoli3";
				}
				else if(codiceasl.equals("109")){
					asl = 9;
					nome_asl = "Napoli4";
				}
				else if(codiceasl.equals("110")){
					asl = 10;
					nome_asl = "Napoli5";
				}else if(codiceasl.equals("111")){
					asl = 11;
					nome_asl = "Salerno1";
				}
				else if(codiceasl.equals("112")){
					asl = 12;
					nome_asl = "Salerno2";
				}
				else if(codiceasl.equals("113")){
					asl = 13;
					nome_asl = "Salerno3";
				}
				String insVar = stringRead.substring(27, 28);
				String denominazione = null;
				String specie_allevata = null;
				String dataInizio = null;
				String ggI = null;
				String mmI = null;
				String aaaaI = null;
				Timestamp data_inizio = null;
				Timestamp data_fine = null;
				String dataFine = null;
				String ggF = null;
				String mmF = null;
				String aaaaF = null;
				String tipo_struttura = null;
				String or_prod = null;
				String p_iva = null;
				String indirizzo = null;
				String citta = null;
				String provincia = null;
				String telefono = null;
				String mail = null;
				String cap = null;




				if(specieA.equals( "2" )){

					codiceAzienda = stringRead.substring(41, 49);
					denominazione = stringRead.substring(97, 257);
					specie_allevata = stringRead.substring(65, 69);
					or_prod = stringRead.substring(96, 97);
					dataInizio = stringRead.substring(461, 469);
					p_iva = stringRead.substring(49, 65);
					indirizzo = stringRead.substring(257, 307) + stringRead.substring(312, 362);
					if(indirizzo.equals("                                                                                                    "))
					{
						indirizzo = "";
					}
					citta = Organization.prelevaComune(db, stringRead.substring(362, 365));
					provincia = stringRead.substring(365, 367);
					cap = stringRead.substring(307, 312);
					telefono = stringRead.substring(367, 382);
					mail = stringRead.substring(382, 412);
					tipo_struttura = stringRead.substring(85, 87);
					if(tipo_struttura.equals("AL")){
						tipo_struttura = "ALLEVAMENTO";
						if(or_prod.equals("M")){
							or_prod = "MISTO";
						}
						else if(or_prod.equals("C")){
							or_prod = "CARNE";
						}else if(or_prod.equals("L")){
							or_prod = "LATTE";
						}
					}else if(tipo_struttura.equals("CG")){
						tipo_struttura = "CENTRO MATERIALE GENETICO";
						if(or_prod.equals("S")){
							or_prod = "CENTRO RACCOLTA SPERMA";
						}
						else if(or_prod.equals("E")){
							or_prod = "GRUPPO RACCOLTA EMBRIONI";
						}
						else if(or_prod.equals("M")){
							or_prod = "CENTRO MAGAZZINAGGIO";
						}else if(or_prod.equals("C")){
							or_prod = "CENTRO GENETICO";
						}
						else if(or_prod.equals("Z")){
							or_prod = "CENTRO GENETICO E QUARANTENA";
						}
						else if(or_prod.equals("Q")){
							or_prod = "CENTRO QUARANTENA";
						}
					}else if(tipo_struttura.equals("CR")){
						tipo_struttura = "CENTRO RACCOLTA";
						if(or_prod.equals("T")){
							or_prod = "CENTRO RACCOLTA";
						}
					}else if(tipo_struttura.equals("PS")){
						tipo_struttura = "PUNTO DI SOSTA";
						if(or_prod.equals("P")){
							or_prod = "PUNTO RACCOLTA";
						}
					}else if(tipo_struttura.equals("SS")){
						tipo_struttura = "STALLA DI SOSTA";
						if(or_prod.equals("A")){
							or_prod = "DA ALLEVAMENTO";
						}
						else if(or_prod.equals("M")){
							or_prod = "DA MACELLO";
						}
						else if(or_prod.equals("U")){
							or_prod = "DA ALLEVAMENTO/MACELLO";
						}
					}else if(tipo_struttura.equals("ST")){
						tipo_struttura = "STABULARIO";
						if(or_prod.equals("ST")){
							or_prod = "STABULARIO";
						}
					}
					ggI = dataInizio.substring(0,2);
					mmI = dataInizio.substring(2,4);
					aaaaI = dataInizio.substring(4,8);
					data_inizio = DatabaseUtils.parseDateToTimestamp(ggI+"/"+mmI+"/"+aaaaI);
					dataFine = stringRead.substring(469, 477);
					ggF = dataInizio.substring(0,2);
					mmF = dataInizio.substring(2,4);
					aaaaF = dataInizio.substring(4,8);
					data_fine = DatabaseUtils.parseDateToTimestamp(ggF+"/"+mmF+"/"+aaaaF);

				}else if(specieA.equals( "6" )){

					codiceAzienda = stringRead.substring(58, 67);
					denominazione = "";
					specie_allevata = stringRead.substring(83, 87);
					p_iva = stringRead.substring(67, 83);

				}
				else {	
					codiceAzienda = stringRead.substring(54, 62);
					denominazione = stringRead.substring(90, 250);
					specie_allevata = stringRead.substring(78, 82);
					p_iva = stringRead.substring(62, 78);
					indirizzo = stringRead.substring(250, 300) + stringRead.substring(305, 355);
					if(indirizzo.equals("                                                                                                    "))
					{
						indirizzo = "";
					}
					citta = Organization.prelevaComune(db, stringRead.substring(362, 365));
					provincia = stringRead.substring(358, 360);
					cap = stringRead.substring(300, 305);
					telefono = stringRead.substring(361, 375);
					mail = stringRead.substring(375, 405);
					or_prod = stringRead.substring(406, 407);
					tipo_struttura = stringRead.substring(82, 84);
					if(tipo_struttura.equals("AL")){
						tipo_struttura = "ALLEVAMENTO";
						if(or_prod.equals("M")){
							or_prod = "MISTO";
						}
						else if(or_prod.equals("C")){
							or_prod = "CARNE";
						}
						else if(or_prod.equals("L")){
							or_prod = "LATTE";
						}
					}else if(tipo_struttura.equals("CG")){
						tipo_struttura = "CENTRO MATERIALE GENETICO";
						if(or_prod.equals("S")){
							or_prod = "CENTRO RACCOLTA SPERMA";
						}
						else if(or_prod.equals("E")){
							or_prod = "GRUPPO RACCOLTA EMBRIONI";
						}
						else if(or_prod.equals("M")){
							or_prod = "CENTRO MAGAZZINAGGIO";
						}else if(or_prod.equals("C")){
							or_prod = "CENTRO GENETICO";
						}
						else if(or_prod.equals("Z")){
							or_prod = "CENTRO GENETICO E QUARANTENA";
						}
						else if(or_prod.equals("Q")){
							or_prod = "CENTRO QUARANTENA";
						}
					}else if(tipo_struttura.equals("CR")){
						tipo_struttura = "CENTRO RACCOLTA";
						if(or_prod.equals("T")){
							or_prod = "CENTRO RACCOLTA";
						}
					}else if(tipo_struttura.equals("PS")){
						tipo_struttura = "PUNTO DI SOSTA";
						if(or_prod.equals("P")){
							or_prod = "PUNTO RACCOLTA";
						}
					}else if(tipo_struttura.equals("SS")){
						tipo_struttura = "STALLA DI SOSTA";
						if(or_prod.equals("A")){
							or_prod = "DA ALLEVAMENTO";
						}
						else if(or_prod.equals("M")){
							or_prod = "DA MACELLO";
						}
						else if(or_prod.equals("U")){
							or_prod = "DA ALLEVAMENTO/MACELLO";
						}
					}else if(tipo_struttura.equals("ST")){
						tipo_struttura = "STABULARIO";
						if(or_prod.equals("ST")){
							or_prod = "STABULARIO";
						}
					}
					dataInizio = stringRead.substring(461, 469);
					ggI = dataInizio.substring(0,2);
					mmI = dataInizio.substring(2,4);
					aaaaI = dataInizio.substring(4,8);
					data_inizio = DatabaseUtils.parseDateToTimestamp(ggI+"/"+mmI+"/"+aaaaI);
					dataFine = stringRead.substring(469, 477);
					ggF = dataFine.substring(0,2);
					mmF = dataFine.substring(2,4);
					aaaaF = dataFine.substring(4,8);
					data_fine = DatabaseUtils.parseDateToTimestamp(ggF+"/"+mmF+"/"+aaaaF);

				}

				String specieAllev = null;
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

				/*String dataInizio = stringRead.substring(461, 469);
				String ggI = dataInizio.substring(0,2);
				String mmI = dataInizio.substring(2,4);
				String aaaaI = dataInizio.substring(4,8);
				Timestamp data_inizio = DatabaseUtils.parseDateToTimestamp(ggI+"/"+mmI+"/"+aaaaI);
				String dataFine = stringRead.substring(469, 477);
				String ggF = dataInizio.substring(0,2);
				String mmF = dataInizio.substring(2,4);
				String aaaaF = dataInizio.substring(4,8);
				Timestamp data_fine = DatabaseUtils.parseDateToTimestamp(ggF+"/"+mmF+"/"+aaaaF);
				 */

				Organization thisAllev = null;
				thisAllev = checkPreProcess( insVar, nome_asl, codiceAzienda, denominazione,specieAllev,data_inizio,db,data_fine, logErr ); 



				if ( insVar.equals("I") ){

					if(thisAllev==null)
					{
						thisAllev  = new Organization();
						OrganizationAddressList address = new OrganizationAddressList();
						OrganizationAddress ind = new OrganizationAddress();
						ind.setType(1);
						ind.setStreetAddressLine1(indirizzo);
						address.add(ind);
						thisAllev.setSpecieA(Integer.parseInt(specie_allevata));
						thisAllev.setTipologiaStrutt(tipo_struttura);
						if(p_iva != null  && (p_iva.length()==11 || p_iva.length()==16)){
							thisAllev.setPartitaIva(p_iva);
						}else
						{logErr.write( ("ERRORE --> RECORD NON CONFORME!! PARTITA IVA O CODICE SBAGLIATO-->"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+"\n").getBytes() );
						nrRigheErr++;}
						thisAllev.setOrientamentoProd(or_prod);
						thisAllev.setAddressList(address);
						thisAllev.setCity(citta);
						thisAllev.setState(provincia);
						thisAllev.setPostalCode(cap);
						OrganizationPhoneNumberList phone = new OrganizationPhoneNumberList();
						OrganizationPhoneNumber num = new OrganizationPhoneNumber();
						num.setPrimaryNumber(telefono);
						phone.add(num);
						/*if(telefono.equals("              ")){
						telefono = "";
						phone.add(telefono);
					}
					else{

					phone.add(telefono);
					thisAllev.setPhoneNumberList(phone);
					}*/
						OrganizationEmailAddressList email = new OrganizationEmailAddressList();
						OrganizationEmailAddress em = new OrganizationEmailAddress();
						em.setEmail(mail);
						email.add(em);
						/*if(mail.equals("                              "))
					{
						mail = "";
					}
					email.add(mail);*/
						thisAllev.setEmailAddressList(email);
						thisAllev.setEnteredBy(userId);
						thisAllev.setModifiedBy(userId);
						thisAllev.setOwner(userId);
						thisAllev.setSiteId(asl);
						thisAllev.setName(denominazione.trim());
						thisAllev.setSpecieAllev(specieAllev);
						thisAllev.setAccountNumber(codiceAzienda);
						thisAllev.setDate1(data_inizio);
						thisAllev.setDate2(data_fine);

						thisAllev.insertUpload(db,context);
						/*context.getRequest().setAttribute("allevamento", thisAllev);
					Accounts accounts = new Accounts();

					accounts.executeCommandInsert(context);*/

						boolean flag = false;
						if(asl == -1 )
						{
							flag = true;
							logOk.write( ("WARNING --> ALLEVAMENTO INSERITO MA MANCA IL CAMPO ASL -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
							nrRigheOk++;
						}
						else
						{
							if(denominazione.equals(""))
							{
								flag = true;
								logOk.write( ("WARNING --> ALLEVAMENTO INSERITO MA MANCA IL CAMPO DENOMINAZIONE -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
								nrRigheOk++;
							}
							else
							{
								if (codiceAzienda.equals(""))
								{
									flag = true;
									logOk.write( ("WARNING --> ALLEVAMENTO INSERITO MA MANCA IL CAMPO CODICE AZIENDA -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
									nrRigheOk++;
								}
								else
								{
									if(specie_allevata.equals(""))
									{
										flag = true;
										logOk.write( ("WARNING --> ALLEVAMENTO INSERITO MA MANCA IL CAMPO SPECIE ALLEV. -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
										nrRigheOk++;
									}
									else
									{
										if(indirizzo.equals(""))
										{
											flag = true;
											logOk.write( ("WARNING --> ALLEVAMENTO INSERITO MA MANCA IL CAMPO INDIRIZZO -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
											nrRigheOk++;
										}
									}
								}
							}
						}


						//LOG RECORD INSERITO
						if(flag == false)
						{
							logOk.write( ("OK --> ALLEVAMENTO INSERITO -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione.trim()+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
							nrRigheOk++;
						}
					}
					else
					{
						logErr.write( ("ERRORE --> RECORD ESISTENTE"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+"\n").getBytes() );
						nrRigheErr++;
					}

				}
				else 
					if(insVar.equals("V")){
						if(thisAllev != null){
							thisAllev.setSpecieA(Integer.parseInt(specie_allevata));
							if(tipo_struttura != null){
								thisAllev.setTipologiaStrutt(tipo_struttura);
							}
							if(p_iva != null  && (p_iva.length()==11 || p_iva.length()==16)){
								thisAllev.setPartitaIva(p_iva);
							}else{
								logErr.write( ("ERRORE --> RECORD NON CONFORME!! PARTITA IVA O CODICE SBAGLIATO-->"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+"\n").getBytes() );
								nrRigheErr++;
							}
							thisAllev.setEnteredBy(userId);
							thisAllev.setModifiedBy(userId);
							thisAllev.setOwner(userId);
							thisAllev.setSiteId(asl);
							if(denominazione != null){
								thisAllev.setName(denominazione.trim());
							}
							if(or_prod != null){
								thisAllev.setOrientamentoProd(or_prod);
							}
							if(specieAllev != null){
								thisAllev.setSpecieAllev(specieAllev);
							}
							thisAllev.setAccountNumber(codiceAzienda);
							thisAllev.setDate1(data_inizio);
							if(data_fine != null){
								thisAllev.setDate2(data_fine);
							}
							if(indirizzo != null){
								OrganizationAddressList address = new OrganizationAddressList();
								OrganizationAddress ind = new OrganizationAddress();
								ind.setType(1);
								ind.setStreetAddressLine1(indirizzo);
								address.add(ind);

							}		
							thisAllev.setCity(citta);
							thisAllev.setState(provincia);
							thisAllev.setPostalCode(cap);
							if(telefono != null){
								OrganizationPhoneNumberList phone = new OrganizationPhoneNumberList();
								OrganizationPhoneNumber num = new OrganizationPhoneNumber();
								num.setPrimaryNumber(telefono);
								phone.add(num);
							}
							if(mail != null){
								OrganizationEmailAddressList email = new OrganizationEmailAddressList();
								OrganizationEmailAddress em = new OrganizationEmailAddress();
								em.setEmail(mail);
								email.add(em);
							}
							thisAllev.update(db,context);

							boolean flag = false;
							if(asl == -1 )
							{
								flag = true;
								logOk.write( ("WARNING --> ALLEVAMENTO MODIFICATO MA MANCA IL CAMPO ASL -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
								nrRigheOk++;
							}
							else
							{
								if(denominazione.equals(""))
								{
									flag = true;
									logOk.write( ("WARNING --> ALLEVAMENTO MODIFICATO MA MANCA IL CAMPO DENOMINAZIONE -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
									nrRigheOk++;
								}
								else
								{
									if (codiceAzienda.equals(""))
									{
										flag = true;
										logOk.write( ("WARNING --> ALLEVAMENTO MODIFICATO MA MANCA IL CAMPO CODICE AZIENDA -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
										nrRigheOk++;
									}
									else
									{
										if(specie_allevata.equals(""))
										{
											flag = true;
											logOk.write( ("WARNING --> ALLEVAMENTO MODIFICATO MA MANCA IL CAMPO SPECIE ALLEV. -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
											nrRigheOk++;
										}
										else
										{
											if(indirizzo.equals(""))
											{
												flag = true;
												logOk.write( ("WARNING --> ALLEVAMENTO MODIFICATO MA MANCA IL CAMPO INDIRIZZO -->"+"ASL:"+nome_asl+" CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
												nrRigheOk++;
											}
										}
									}
								}
							}


							//LOG RECORD INSERITO
							if(flag == false)
								logOk.write( ("OK --> ALLEVAMENTO MODIFICATO -->"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
							nrRigheOk++;
						}else {
							logErr.write( ("ERRORE --> ALLEVAMENTO MODIFICATO NON ESISTENTE --> "+"ASL:"+nome_asl+" .CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
							nrRigheErr++;
						}

					}else if(insVar.equals("A")){
						if(thisAllev != null){
							recordDeleted = thisAllev.disable(db);
							logOk.write( ("OK --> ALLEVAMENTO ANNULLATO -->"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+" Denominazione:"+denominazione+" Operazione:"+insVar+" SpecieAllevata:"+specie_allevata+" DataInizioAttivita:"+data_inizio+" DataFineAttivita:"+data_fine+"\n").getBytes() );
							nrRigheOk++;

						}

					}

				stringRead = br.readLine( );
			}
			//LOG DI RIEPILOGO

			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n"+"Record ERR: "+nrRigheErr ).getBytes() );

			br.close();

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			logErr.write( ("ERRORE --> RECORD NON CONFORME!! -->"+"ASL:"+nome_asl+"CodiceAzienda:"+codiceAzienda+"\n").getBytes() );
			nrRigheErr++;
		} 

		return data;
	}


	/*private static void updateControlli(Connection db, String codice,
			String data_prelievo, String data_accettazione, String data_esito,
			Asset thisAsset, int esito_finale, Integer i) throws SQLException {
		if ( i == 5 ) {
			thisAsset.updateControlliCane(db);
		}else{
			try {
				thisAsset.updateEsitiDate(i, db, thisAsset, codice, data_prelievo, data_accettazione, data_esito, esito_finale);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}*/



	private static Organization checkPreProcess(String insVar, String asl, String codiceAz, String denominazione, String specieAllevata, Timestamp dataInizio, Connection db, Timestamp dataFine, FileOutputStream logErr ) throws SQLException, IOException {
		String dataIn = null;
		if(dataInizio!= null){
			dataIn= dataInizio.toString();
		}
		String dataFn = null;
		if(dataFine!= null){
			dataFn = dataFine.toString();
		}
		int CodAllev = -1;
		Organization allevamento = null;
		boolean check = true;

		//Verifica Formato Data
		if(dataIn!= null){
			if ( parseDate(dataIn)==null) {
				check = false;
				logErr.write( ("ERRORE --> FORMATO DATA INIZIO ATTIVITA' NON VALIDO --> "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n" ).getBytes() );
				nrRigheErr++;
			}
		}

		if(dataFn!=null){
			if(dataFine != null){
				if( parseDate(dataFn)==null){
					check = false;
					logErr.write( ("ERRORE --> FORMATO DATA FINE ATTIVITA' NON VALIDO --> "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n" ).getBytes() );
					nrRigheErr++;
				}}
		}


		//Verifica Esistenza dell'allevamento
		if(insVar.equals("V")){

			if ( check ) {
				int i = Organization.prelevaOrgId(db, codiceAz,  denominazione);
				if ( i ==  -1 ){
					check = false;
					logErr.write( ("ERRORE --> VARIAZIONE AD ALLEVAMENTO NON PRESENTE --> "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n" ).getBytes() );
					nrRigheErr++;
				}}
		}else if(insVar.equals("A")){
			if ( check ) {
				int i = Organization.prelevaOrgId(db, codiceAz,  denominazione);
				if ( i ==  -1 ){
					check = false;
					logErr.write( ("ERRORE --> ANNULLAMENTO ALLEVAMENTO NON PRESENTE --> "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n" ).getBytes() );
					nrRigheErr++;
				}
			}
		}
		/*else if(insVar.equals("I")){
			if ( check ) {
				int i = Organization.prelevaOrgId(db, codiceAz);
				if ( i !=  -1 ){
					check = false;
					logErr.write( ("ERRORE --> INSERIMENTO ALLEVAMENTO GIA' PRESENTE --> "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n" ).getBytes() );
					nrRigheErr++;
				}
		}
		}*/
		//Verifica Data Fine
		if(insVar.equals("V")){
			if ( check )
			{
				allevamento  = new Organization(db, Organization.prelevaOrgId(db, codiceAz,  denominazione));

				if ( dataFine != null) {
					if ( dataInizio.after(dataFine) ){
						check = false;
						logErr.write( ("ERRORE --> DATA FINE ATTIVITA' ANTECEDENTE A DATA INIZIO ATTIVITA': ** "+"ASL:"+asl+" CodiceAzienda:"+codiceAz+" Denominazione:"+denominazione+" DataInizioAttivita:"+dataInizio+" DataFineAttivita:"+dataFine+"\n").getBytes() );
						nrRigheErr++;
					}
				}
			}}
		if ( check ) {
			return allevamento;
		} else {
			return null;
		}
	} 

	/*private static void initializeProperties() {
		Select s = new Select();

		MC = Integer.parseInt( s.get("MC") );
		DATA_PRELIEVO = Integer.parseInt( s.get("DATA_PRELIEVO") );
		DATA_ACCETTAZIONE = Integer.parseInt( s.get("DATA_ACCETTAZIONE") );
		DATA_ESITO = Integer.parseInt( s.get("DATA_ESITO") );
		RISULTATO = Integer.parseInt( s.get("RISULTATO") );
		ESITO = Integer.parseInt( s.get("ESITO") );
	}*/

	/*private static void inserisciTicket(Connection db, Asset thisAsset, String dataCorrente)
			throws SQLException {
		Ticket newTic = new Ticket();
		newTic.setEnteredBy(0);
		newTic.setModifiedBy(0);
		newTic.setOrgId(thisAsset.getOrgId());
		newTic.setProblem("Esito Controllo Leishmaniosi");
		newTic.setAssignedDate( DatabaseUtils.parseDateToTimestamp( dataCorrente ) );
		newTic.setAssetId(thisAsset.getId());
		newTic.setClosed( DatabaseUtils.parseDateToTimestamp( dataCorrente )  );
		newTic.setSolution("Esito Controllo Leishmaniosi");
		newTic.insert(db);
	}*/


	public static String getData() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");
		String rit = sdf.format( gc.getTime() ).replace(":", "_");
		return ( rit );
	}


	/*
	private static String getDataTicket() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String rit = sdf.format( gc.getTime() );
		return ( rit );
	}*/


	private static boolean checkDate(Timestamp data) {
		DateFormat df = new SimpleDateFormat ("yyyy-mm-gg");
		try {
			df.parse (data.toString());
		} catch (ParseException e) {
			return false;
		}return true;

	}

	private static Timestamp parseDate( String data )
	{
		Timestamp ret = null;
		DateFormat sdf = new SimpleDateFormat ("yyyyMMdd");
		try
		{
			if(data!=null)
				ret = new Timestamp( sdf.parse( data ).getTime() );

		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return ret;
	}

	public static void deleteSottoAttivita(Connection db)
	{

		PreparedStatement pst = null ;
		try
		{

			String sql = "delete from stabilimenti_sottoattivita_soa ";
			pst = db.prepareStatement(sql);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}


	public static String leggiCampiSoa(ActionContext context, Connection db, File br, String path, int userId )
			throws IOException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{

		HashMap<Integer, Boolean> controlliAggiornamenti = new HashMap<Integer, Boolean>();

		String data						= getData();

		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		//br.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));
		//POIFSFileSystem fs	= new POIFSFileSystem(new FileInputStream(br));
		//HSSFWorkbook wb		= new HSSFWorkbook(fs); 	
		//HSSFSheet sheet		= wb.getSheetAt((short)0); //Fog

		//for( int row_index=1; row_index <= sheet.getLastRowNum(); row_index++ )
		//{
		int numeroRighe =1;

		deleteSottoAttivita(db);

		BufferedReader brr = new BufferedReader(new FileReader(br));
		String stringRead = brr.readLine( );
		int riga = 0;
		while(( stringRead != null )&&(stringRead.trim().length() > 0))
		{

			String [] rigaAttuale = stringRead.split(";");
			org.aspcfs.modules.soa.base.Organization organization = new org.aspcfs.modules.soa.base.Organization();

			//HSSFRow row = sheet.getRow(row_index);
			//		if(row != null)
			//		{

			java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());


			boolean flagErrore=false;
			boolean flagWarning=false;
			if(riga!=0)
			{
				//			HSSFCell cell_1 = row.getCell((short)0);
				//			HSSFCell cell_2 = row.getCell((short)1);
				//			HSSFCell cell_3 = row.getCell((short)2);
				//			HSSFCell cell_4 = row.getCell((short)3);
				//			HSSFCell cell_5 = row.getCell((short)4);
				//			HSSFCell cell_6 = row.getCell((short)5);
				//			HSSFCell cell_7 = row.getCell((short)6);
				//			HSSFCell cell_8 = row.getCell((short)7);
				//			HSSFCell cell_9 = row.getCell((short)8);
				//			HSSFCell cell_10 = row.getCell((short)9);
				//			HSSFCell cell_11 = row.getCell((short)10);
				//			HSSFCell cell_12 = row.getCell((short)11);
				//			HSSFCell cell_13 = row.getCell((short)12);
				//			HSSFCell cell_14 = row.getCell((short)13);
				//			HSSFCell cell_15 = row.getCell((short)14);
				//			HSSFCell cell_16 = row.getCell((short)15);
				//			HSSFCell cell_17 = row.getCell((short)16);
				//			HSSFCell cell_18 = row.getCell((short)17);
				//			HSSFCell cell_19 = row.getCell((short)18);
				//			HSSFCell cell_20 = row.getCell((short)19);
				//			HSSFCell cell_21 = row.getCell((short)20);
				//			HSSFCell cell_22 = row.getCell((short)21);
				//			HSSFCell cell_23 = row.getCell((short)22);


				//			int progressivoStabilimento = (int)cell_1.getNumericCellValue();
				//			
				//			int approval = 0;
				//			String approvaAsString = "";
				//			
				//			
				//			if(cell_3!=null){
				//				String ragioneSociale=cell_3.getStringCellValue();
				//
				//			if( cell_2.getCellType() == 0 )
				//				approvaAsString = "" + (int)cell_2.getNumericCellValue();
				//			else
				//				approvaAsString = cell_2.getStringCellValue();
				//		
				//		
				//		
				//			
				//			String inmdirizzo			= cell_4.getStringCellValue();
				//			String ragioneSocialePrec	= cell_5.getStringCellValue();
				//			String pIva					= "";
				//			
				//			if( cell_6.getCellType() == 0 )
				//				pIva = "" + (int) cell_6.getNumericCellValue();
				//			else
				//				pIva = cell_6.getStringCellValue();
				//			
				//
				//			String cf							= "";
				//			
				//			if( cell_7.getCellType() == 0 )
				//				cf = "" + (int) cell_7.getNumericCellValue();
				//			else
				//				cf = cell_7.getStringCellValue();
				//
				//			String comune						= cell_8.getStringCellValue();
				//			String siglaProvincia				= cell_9.getStringCellValue();
				//			//int codiceRagione					= (int) cell_10.getNumericCellValue();   			//NON SERVE
				//			Integer codiceServizioVeterinario	= (int) cell_11.getNumericCellValue();
				//		
				//			int site_id = -1;
				//	
				//			if(codiceServizioVeterinario!=null)
				//			{
				//				switch(codiceServizioVeterinario)
				//				{
				//				case 15 :
				//					site_id=1;
				//					break;
				//				case 35:
				//					site_id=2;
				//					break;
				//				case 55 :
				//					site_id=3;
				//					break;
				//				case 74 :
				//					site_id=4;
				//					break;
				//				case 93 :
				//					site_id=5;
				//					break;
				//				case 171:
				//					site_id=11;
				//					break;
				//				case 182 :
				//					site_id=12;
				//					break;
				//				case 190 :
				//					site_id=13;
				//					break;
				//				case 110 :
				//					site_id=6;
				//					break;
				//				case 123 :
				//					site_id=7;
				//					break;
				//				case 136 :
				//					site_id=8;
				//					break;
				//				case 149 :
				//					site_id=9;
				//					break;
				//				case 160 :
				//					site_id=10;
				//					break;
				//				default :
				//					site_id=-1;
				//				}
				//			}

				int progressivoStabilimento = Integer.parseInt(rigaAttuale[0].trim());

				System.out.println("Sto Processando la riga del file di sintesi con progressivostabilimento = "+progressivoStabilimento);
				int approval = 0;
				String approvaAsString = "";


				if(rigaAttuale[2]!=null && ! rigaAttuale[2].equals(""))
				{

					String ragioneSociale=rigaAttuale[2];
					/*if( cell_2.getCellType() == 0 )
				{
					approvaAsString = "" + (int)cell_2.getNumericCellValue();
				}
				else
				{
					approvaAsString = cell_2.getStringCellValue();
				}
					 */

					approvaAsString = rigaAttuale[1];



					String inmdirizzo			= rigaAttuale[3];//cell_4.getStringCellValue();
					String ragioneSocialePrec	= rigaAttuale[4];//cell_5.getStringCellValue();
					String pIva					= "";


					//System.out.println(cell_6.getNumericCellValue());
					/*if( cell_6.getCellType() == 0 )
					 ppIva = ""+ Double.toString(cell_6.getNumericCellValue()) ;
				else
					pIva = cell_6.getStringCellValue();
					 */
					pIva = rigaAttuale[5];

					String cf							= "";

					/*if( cell_7.getCellType() == 0 )
					cf = "" + (int) cell_7.getNumericCellValue();
				else
					cf = cell_7.getStringCellValue();
					 */
					cf = rigaAttuale[6];
					String comune						= rigaAttuale[7];//cell_8.getStringCellValue();
					String siglaProvincia				= rigaAttuale[8];//cell_9.getStringCellValue();
					//int codiceRagione					= (int) cell_10.getNumericCellValue();   			//NON SERVE
					//Integer codiceServizioVeterinario	= (int) cell_11.getNumericCellValue();

					int codiceRagione =-1;
					if(!rigaAttuale[9].trim().equals(""))
						codiceRagione					= Integer.parseInt(rigaAttuale[9].trim());
					Integer codiceServizioVeterinario	= -1;
					if(!rigaAttuale[10].trim().equals(""))
						codiceServizioVeterinario	= Integer.parseInt(rigaAttuale[10].trim());
					int site_id = -1;

					/**
					 * Recupero del codice di asl di riferimento a partire dal campo codice servizio veterinario
					 */
					if(comune.equalsIgnoreCase("acerra") || comune.equalsIgnoreCase("casalnuovo di napoli"))
					{
						site_id = 8;
					}
					else
					{
						if(comune.equalsIgnoreCase("portici"))
						{
							site_id = 6;
						}


						else
						{

							if(codiceServizioVeterinario!=null)
							{
								switch(codiceServizioVeterinario)
								{
								case 15 :
									site_id=1;
									break;
								case 35:
									site_id=2;
									break;
								case 55 :
									site_id=3;
									break;
								case 74 :
									site_id=4;
									break;
								case 93 :
									site_id=5;
									break;
								case 171:
									site_id=11;
									break;
								case 182 :
									site_id=12;
									break;
								case 190 :
									site_id=13;
									break;
								case 110 :
									site_id=6;
									break;
								case 123 :
									site_id=7;
									break;
								case 136 :
									site_id=8;
									break;
								case 149 :
									site_id=9;
									break;
								case 160 :
									site_id=10;
									break;
								default :
									site_id=-1;
								}
							}
						}
					}


					if(site_id==-1){

						String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune.replaceAll("'", "''")+"'";
						PreparedStatement pst=db.prepareStatement(sql);
						ResultSet rsResultSet=pst.executeQuery();
						if(rsResultSet.next())
							site_id=rsResultSet.getInt(1);
					}



					Integer statoStabilimento = null;
					if(!rigaAttuale[11].trim().equals("") && !rigaAttuale[11].trim().equals("-"))
						statoStabilimento			= Integer.parseInt(rigaAttuale[11].trim());//(int)cell_12.getNumericCellValue();
					String descrizioneStatoAttivita	= rigaAttuale[12].trim();//cell_13.getStringCellValue();
					//String statoAttivita			= cell_18.getStringCellValue();

					int statoLab = -1;

					if(statoStabilimento==0 || statoStabilimento==1 || statoStabilimento==2 || statoStabilimento==3)
					{
						statoLab=statoStabilimento;
					}




					int codiceSezione		= -1;
					if(!rigaAttuale[13].trim().equals(""))
						codiceSezione		= Integer.parseInt(rigaAttuale[13].trim());//(int)cell_14.getNumericCellValue();
					String categoria		= "";
					String attivita			= rigaAttuale[14];//cell_15.getStringCellValue();
					Integer codiceImpianto	= -1;
					if(!rigaAttuale[15].trim().equals(""))

						codiceImpianto	=Integer.parseInt(rigaAttuale[16].trim());// (int)cell_16.getNumericCellValue();
					//String progressivoAttivita=cell_17.getStringCellValue();

					Date dataInizioAttivita = null;


					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					//if(cell_19.getCellType()!=cell_19.CELL_TYPE_STRING)

					if(!rigaAttuale[19].equals("") && !rigaAttuale[19].equals("-"))
						try {
							dataInizioAttivita=sdf.parse(rigaAttuale[19]);
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}//cell_19.getDateCellValue();

					Date dataFineAttivita = null;
					//if(cell_20.getCellType()!=cell_20.CELL_TYPE_STRING)
					if(!rigaAttuale[19].equals("") && !rigaAttuale[19].equals("-"))
						try {
							dataFineAttivita=sdf.parse(rigaAttuale[19]);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	//cell_20.getDateCellValue();




					String tipoAutorizzazione =rigaAttuale[20];// cell_21.getStringCellValue();

					String descrTipoAutorizzazione = "";
					if(tipoAutorizzazione.equalsIgnoreCase("D"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE DEFINITA";
					}
					else if(tipoAutorizzazione.equalsIgnoreCase("C"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE";
					}

					String ritiReligiosi	= rigaAttuale[21];// cell_22.getStringCellValue();
					Integer imballata = -1;
					if(rigaAttuale.length>=24)
					{
						if( rigaAttuale[23]!=null && !rigaAttuale[23].trim().equals(""))
							imballata		= Integer.parseInt(rigaAttuale[23].trim());//(int)cell_23.getNumericCellValue();
					}



					if(approvaAsString!=null && ( approvaAsString.equals("-") || approvaAsString.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> SOA  NON INSERITO PER MANCANZA DEL CAMPO APPROVAL NUMBER (CAMPO USATO COME CHIAVE DI RICERCA). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(codiceImpianto!=null && ( codiceImpianto.equals("-") || codiceImpianto.equals("")) )
					{
						logErr.write( ("ERRORE BLOCCANTE  --> SOA  NON INSERITO PER MANCANZA DEL CAMPO CODICE IMPIANTO (CAMPO USATO COME CHIAVE DI RICERCA PER SOTTOATTIVITa). -->" + "PROGRESSIVO SISTEMA STABILIMENTO NEL FILE : " + progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + " \n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}


					if(flagErrore==false){

						org.aspcfs.modules.soa.base.Organization old_organization = org.aspcfs.modules.soa.base.Organization.loadbyProgressivo(progressivoStabilimento, db );

						organization = new org.aspcfs.modules.soa.base.Organization();
						organization.setSiteId (site_id);
						organization.setCategoria(categoria);

						if(approvaAsString != null)
						{
							if( !approvaAsString.equals("-") )
								organization.setNumAut(approvaAsString);
							else
								organization.setNumAut("");
						}

						if(ragioneSociale != null)
						{
							if(!ragioneSociale.equals("-"))
								organization.setName(ragioneSociale);
							else
								organization.setName("");
						}

						if(ragioneSocialePrec != null)
						{
							if(!ragioneSocialePrec.equals("-"))
								organization.setBanca(ragioneSocialePrec);
							else
								organization.setBanca("");
						}

						if(pIva != null)
						{
							if(!pIva.equals("-"))
								organization.setPartitaIva(pIva);
							else
								organization.setPartitaIva("");
						}

						if(cf != null)
						{
							if(!cf.equals("-"))
								organization.setCodiceFiscale(cf);
							else
								organization.setCodiceFiscale("");
						}

						//LOOKUP
						organization.setStatoLab(statoLab);

						if(attivita != null)
						{
							if(!attivita.equals("-"))
							{
								organization.setImpianto(organization.getIdImpianto(attivita, db) + "");
							}
							else
								organization.setImpianto("");
						}

						if(codiceImpianto != null)
						{
							if(!codiceImpianto.equals("-"))
								organization.setCodiceImpianto(codiceImpianto + "");
							else
								organization.setCodiceImpianto("");
						}

						if(tipoAutorizzazione != null)
						{
							if(!tipoAutorizzazione.equals("-"))
								organization.setTipoAutorizzazzione(descrTipoAutorizzazione);
							else
								organization.setTipoAutorizzazzione("");
						}

						if(ritiReligiosi != null)
						{
							if(!ritiReligiosi.equals("-"))
								organization.setRitiReligiosi(ritiReligiosi);
							else
								organization.setRitiReligiosi("");
						}

						if(imballata != null)
						{
							if(!imballata.equals("-"))
								organization.setImballata(imballata);
							else
								organization.setImballata(-1);
						}

						organization.setOwner(userId);
						organization.setEnteredBy(userId);

						if(dataInizioAttivita!=null)
							organization.setDate2(new Timestamp(dataInizioAttivita.getTime()));

						if(dataFineAttivita!=null)
							organization.setContractEndDate(new Timestamp(dataFineAttivita.getTime()));

						org.aspcfs.modules.soa.base.OrganizationAddress ind = new org.aspcfs.modules.soa.base.OrganizationAddress();
						ind.setCity(comune);
						ind.setStreetAddressLine1(inmdirizzo);
						ind.setState(siglaProvincia);
						org.aspcfs.modules.soa.base.OrganizationAddressList address = new org.aspcfs.modules.soa.base.OrganizationAddressList();



						int orgid=-1;
						if(old_organization!=null)
							orgid=old_organization.getOrgId();



						ind.setId(address.getIdfromaddress(db, inmdirizzo, comune, siglaProvincia,orgid));

						ind.setType(5);
						address.add(ind);
						organization.setAddressList(address);
						int idImpianto = organization.getIdImpianto(db,attivita);
						organization.setImpianto(idImpianto);// accetta intero
						//organization.setCodiceImpianto("");// Stringa

						if( approvaAsString.equals("-") && statoLab == 3 )
						{
							approvaAsString = " ";
							organization.setNumAut(approvaAsString);
						}

						try
						{

							//int statoLabA = organization.checkIfExistsUpload(db,organization.getNumAut(),categoria,idImpianto);

							boolean inserito = false;
							boolean aggiornato = false;	
							organization.setProgressivo_soa(progressivoStabilimento);

							if( old_organization != null )
							{

								if(controlliAggiornamenti.get(progressivoStabilimento)==null)
								{
									organization.setModifiedBy( userId );
									organization.setOrgId( old_organization.getOrgId() );
									organization.setModified( old_organization.getModified() );

									organization.update(db,context);
									aggiornato = true;
									controlliAggiornamenti.put(progressivoStabilimento, aggiornato);
								}
							}
							else
							{


								if(ragioneSociale==null || ragioneSociale.equals(""))
									organization.setName("-");

								inserito = organization.insert(db,context);
							}

							if( inserito == true )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}



								if(flagWarning==true){
									logOk.write( ("WARNING --> SOA INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info ("WARNING --> SOA INSERITO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

								}else{
									logOk.write( ("SUCCESS --> SOA INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOk++;
									log.info("SUCCESS --> SOA INSERITO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}

							}
							else if( aggiornato )
							{



								if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(comune!=null && (comune.equals("") || comune.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}


								if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}



								if(dataInizioAttivita==null ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}

								if(site_id==-1 ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;
								}
								if(statoLab==-1 ){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}

								if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

									logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
									nrRigheErr++;
									flagWarning=true;

								}


								if(flagWarning==true){
									logOk.write( ("WARNING --> SOA AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("WARNING --> SOA AGGIORNATO CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

								}else{
									logOk.write( ("SUCCESS --> SOA AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("SUCCESS --> SOA AGGIORNATO CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


								}
							}
							else
							{

								logErr.write( ("SOA NON inserito. (PROGRESSIVO SISTEMA SOA) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
								nrRigheErr++;
								log.info("SOA NON inserito. (PROGRESSIVO SISTEMA SOA) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");
							}

						}
						catch (SQLException e)
						{
							System.out.println("non inserito, andato in catch");
							logErr.write( ("ERRORE NON CONFORME (PROGRESSIVO SISTEMA SOA) : " +progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());
							nrRigheErr++;
							log.warn("si e verificato un errore nell'inserimento/aggiornamento dello stabilimento APPROVAL_NUMBER : "+approvaAsString+" RAGIONE SOCIALE : "+ragioneSociale+" impianto : "+attivita);
							e.printStackTrace();
						}


						organization = org.aspcfs.modules.soa.base.Organization.loadbyProgressivo(progressivoStabilimento,db );




						if( organization != null )
						{
							Timestamp now = new Timestamp( System.currentTimeMillis() );

							SottoAttivita sa = new SottoAttivita();
							sa.setAttivita					( attivita );
							sa.setCodice_impianto			( codiceImpianto );
							sa.setCodice_sezione			( codiceSezione );
							//sa.setData_fine_attivitaString	( dataFineAttivita );
							if(dataInizioAttivita!=null)
								sa.setData_inizio_attivita(new Timestamp( dataInizioAttivita.getTime()) );
							if(dataFineAttivita!=null)
								sa.setData_fine_attivita(new Timestamp(dataFineAttivita.getTime()));
							sa.setDescrizione_stato_attivita( descrizioneStatoAttivita );
							sa.setEnabled					( true );
							sa.setEntered_by				( userId );
							sa.setEntered					( now );
							sa.setId_stabilimento			( organization.getOrgId() );
							sa.setImballata					( imballata );
							sa.setModified					( now );
							sa.setModified_by				( userId );
							sa.setRiti_religiosi			( "S".equalsIgnoreCase( ritiReligiosi ) );
							sa.setStato_attivita			( statoLab );
							sa.setTipo_autorizzazione		( "D".equalsIgnoreCase( tipoAutorizzazione ) ? (1) : (2) );

							try
							{



								if(attivita!=null && (attivita.equals("") || attivita.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO ATTIVITA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}

								if(codiceImpianto!=null && (codiceImpianto.equals("") || codiceImpianto.equals("-"))){

									logErr.write( ("BLOCCANTE --> SOTTOATTIVITA SCARTATA PER MANCANZA DEL CAMPO CODICE IMPIANTO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
									nrRigheErr++;
									flagErrore=true;
								}





								if(flagErrore==false){

									if(ragioneSocialePrec!=null && (ragioneSocialePrec.equals("") || ragioneSocialePrec.equals("-"))){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE PRECEDENTE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO INDIRIZZO. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()  + "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(comune!=null && (comune.equals("") || comune.equals("-"))){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO COMUNE. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}


									if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO PROVINCIA. (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}



									if(dataInizioAttivita==null ){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA INIZIO ATTIVITA (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}

									if(site_id==-1 ){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160).  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;
									}
									if(statoLab==-1 ){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO STATO STBILIMENTO  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}

									if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

										logErr.write( ("WARNING --> SOA  INSERITO MA MANCA IL CAMPO RAGIONE SOCIALE  (PROGRESSIVO SISTEMA STABILIMENTO) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString() +  "\n").getBytes() );
										nrRigheErr++;
										flagWarning=true;

									}




									SottoAttivita old = sa.alreadyExist( db );
									if( old != null )
									{
										sa.setId		( old.getId() );
										sa.setEntered	( old.getEntered() );
										sa.setEntered_by( old.getEntered_by() );

										sa.update( db );

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("WARNING --> SOTTOATTIVITA AGGIORNATA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : ) "+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOksaAgg++;
											log.info("SUCCESS --> SOTTOATTIVITA AGGIORNATA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}
									else
									{

										sa.store( db ,context);

										if(flagWarning==true){
											logOk.write( ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info ("WARNING --> SOTTOATTIVITA INSERITA CON WARNING (CONTROLLA IL FILE DEGLI ERRORI PER PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");	

										}else{
											logOk.write( ("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n").getBytes());	
											nrRigheOk++;
											log.info("SUCCESS --> SOTTOATTIVITA INSERITA CORRETTAMENTE (PROGRESSIVO SISTEMA STABILIMENTO : )"+progressivoStabilimento+" DATA IMPORT :"+dataAggiornament.toString()+ "\n");


										}


									}}

							}
							catch (Exception e)
							{
								System.out.println( "errore generico" );
								e.printStackTrace();
							}
						}
						else
						{
							System.out.println( "errore generico" );
						}
					}}
			}
		}


		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;


		try{


			String sql="select * from organization where tipologia=97 and to_date(entered::text,'yyyy-MM-dd') < current_date and to_date(modified::text,'yyyy-MM-dd') < current_date";
			java.sql.PreparedStatement pst=  db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			String sql2="update organization set stato_lab=1 where org_id=? and tipologia=97";

			String sql3="update soa_sottoattivita set stato_attivita=1,descrizione_stato_attivita='revocata' where id_soa=?";
			java.sql.PreparedStatement pst3=  db.prepareStatement(sql3);
			java.sql.PreparedStatement pst2=db.prepareStatement(sql2);


			java.util.Date dataAggiornament=new java.util.Date(System.currentTimeMillis());

			int count=0;
			while(rs.next()){

				int org_id=rs.getInt("org_id");
				java.sql.Date dataAggiornamento=rs.getDate("modified");
				String approval_number=rs.getString("numaut");
				String ragioneSociale=rs.getString("name");
				pst2.setInt(1, org_id);
				pst2.execute();
				count++;
				logOk.write( ("REVOCA --> SOA REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA SOA) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " +dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+ "\n").getBytes());	

				logErr.write( ("REVOCA --> SOA REVOCATO INSIEME A LE SUE SOTTOATTIVITA (PROGRESSIVO SISTEMA SOA) : "+approval_number+" DATA ULTIMO AGGIORNAMENTO : " + dataAggiornamento+" . OPERAZIONE EFFTUATA IN DATA :"+dataAggiornament.toString()+"\n").getBytes());	



				log.info("Stabilimento Revocato Perche non Presente nel File Sintesi Ragione Sociale : " + ragioneSociale +"\n");

				pst3.setInt(1, org_id);
				pst3.execute();


			}

			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n Stabilimenti Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg+"\n Stabilimenti revocati : "+count ).getBytes() );


		}catch(Exception e){
			System.out.println("Errore nell'aggiornamento degli stabilimenti presenti in banca dati");

		}


		return data;

	}

	public static String leggiCampiSoaVecchia(ActionContext context, Connection db, File br, String path, int userId )
			throws IOException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String data						= getData();
		FileOutputStream logOk			=  new FileOutputStream( path + data + "_ok.txt" ) ;
		FileOutputStream logErr			=  new FileOutputStream( path + data + "_err.txt" ) ;
		FileOutputStream logRiepilogo	=  new FileOutputStream( path + data + "_rpg.txt" ) ;

		br.renameTo(new File(context.getServletContext().getRealPath("templatexls")+File.separatorChar+"temp.xls"));
		POIFSFileSystem fs	= new POIFSFileSystem(new FileInputStream(br));
		HSSFWorkbook wb		= new HSSFWorkbook(fs); 	
		HSSFSheet sheet		= wb.getSheetAt((short)0); //Fog

		for( int row_index=1; row_index <= sheet.getLastRowNum(); row_index++ )
		{

			org.aspcfs.modules.soa.base.Organization organization = new org.aspcfs.modules.soa.base.Organization();

			HSSFRow row = sheet.getRow(row_index);
			if(row != null)
			{
				boolean flagErrore=false;

				HSSFCell cell_1 = row.getCell((short)0);
				HSSFCell cell_2 = row.getCell((short)1);
				HSSFCell cell_3 = row.getCell((short)2);
				HSSFCell cell_4 = row.getCell((short)3);
				HSSFCell cell_5 = row.getCell((short)4);
				HSSFCell cell_6 = row.getCell((short)5);
				HSSFCell cell_7 = row.getCell((short)6);
				HSSFCell cell_8 = row.getCell((short)7);
				HSSFCell cell_9 = row.getCell((short)8);
				HSSFCell cell_10 = row.getCell((short)9);
				HSSFCell cell_11 = row.getCell((short)10);
				HSSFCell cell_12 = row.getCell((short)11);
				HSSFCell cell_13 = row.getCell((short)12);
				HSSFCell cell_14 = row.getCell((short)13);
				HSSFCell cell_15 = row.getCell((short)14);
				HSSFCell cell_16 = row.getCell((short)15);
				HSSFCell cell_17 = row.getCell((short)16);
				HSSFCell cell_18 = row.getCell((short)17);
				HSSFCell cell_19 = row.getCell((short)18);
				HSSFCell cell_20 = row.getCell((short)19);
				HSSFCell cell_21 = row.getCell((short)20);
				HSSFCell cell_22 = row.getCell((short)21);
				HSSFCell cell_23 = row.getCell((short)22);


				//int progressivoStabilimento = (int)cell_1.getNumericCellValue();

				int approval = 0;
				String approvaAsString = "";


				if(cell_3!=null){
					String ragioneSociale=cell_3.getStringCellValue();

					if( cell_2.getCellType() == 0 )
						approvaAsString = "" + (int)cell_2.getNumericCellValue();
					else
						approvaAsString = cell_2.getStringCellValue();




					String inmdirizzo			= cell_4.getStringCellValue();
					String ragioneSocialePrec	= cell_5.getStringCellValue();
					String pIva					= "";

					if( cell_6.getCellType() == 0 )
						pIva = "" + (int) cell_6.getNumericCellValue();
					else
						pIva = cell_6.getStringCellValue();


					String cf							= "";

					if( cell_7.getCellType() == 0 )
						cf = "" + (int) cell_7.getNumericCellValue();
					else
						cf = cell_7.getStringCellValue();

					String comune						= cell_8.getStringCellValue();
					String siglaProvincia				= cell_9.getStringCellValue();
					//int codiceRagione					= (int) cell_10.getNumericCellValue();   			//NON SERVE
					Integer codiceServizioVeterinario	= (int) cell_11.getNumericCellValue();

					int site_id = -1;

					if(codiceServizioVeterinario!=null)
					{
						switch(codiceServizioVeterinario)
						{
						case 15 :
							site_id=1;
							break;
						case 35:
							site_id=2;
							break;
						case 55 :
							site_id=3;
							break;
						case 74 :
							site_id=4;
							break;
						case 93 :
							site_id=5;
							break;
						case 171:
							site_id=11;
							break;
						case 182 :
							site_id=12;
							break;
						case 190 :
							site_id=13;
							break;
						case 110 :
							site_id=6;
							break;
						case 123 :
							site_id=7;
							break;
						case 136 :
							site_id=8;
							break;
						case 149 :
							site_id=9;
							break;
						case 160 :
							site_id=10;
							break;
						default :
							site_id=-1;
						}
					}


					if(site_id==-1){




						String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune.replaceAll("'", "''")+"'";
						PreparedStatement pst=db.prepareStatement(sql);
						ResultSet rsResultSet=pst.executeQuery();
						if(rsResultSet.next())
							site_id=rsResultSet.getInt(1);
					}



					int statoStabilimento			= (int)cell_12.getNumericCellValue();				//NON SERVE
					String descrizioneStatoAttivita	= cell_13.getStringCellValue();
					//String statoAttivita			= cell_18.getStringCellValue();

					int statoLab = -1;



					if(statoStabilimento==0 || statoStabilimento==1 || statoStabilimento==2 || statoStabilimento==3)
						statoLab=statoStabilimento;






					int codiceSezione		= (int)cell_14.getNumericCellValue();
					String categoria		= "";
					String attivita			= cell_15.getStringCellValue();
					Integer codiceImpianto	= (int)cell_16.getNumericCellValue();
					//String progressivoAttivita=cell_17.getStringCellValue();

					Date dataInizioAttivita = null;


					if(cell_19.getCellType()!=cell_19.CELL_TYPE_STRING)
						dataInizioAttivita=cell_19.getDateCellValue();

					Date dataFineAttivita = null;
					if(cell_20.getCellType()!=cell_20.CELL_TYPE_STRING)
						dataFineAttivita=cell_20.getDateCellValue();



					String tipoAutorizzazione = cell_21.getStringCellValue();

					String descrTipoAutorizzazione = "";
					if(tipoAutorizzazione.equalsIgnoreCase("D"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE DEFINITA";
					}
					else if(tipoAutorizzazione.equalsIgnoreCase("C"))
					{
						descrTipoAutorizzazione = "DECRETO DI AUTORIZZAZZIONE";
					}

					String ritiReligiosi	= cell_22.getStringCellValue();
					Integer imballata		= (int)cell_23.getNumericCellValue();


					if(approvaAsString!=null && ( approvaAsString.equals("-") || approvaAsString.equals("")) )
					{
						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO APPROVAL NUMBER. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(ragioneSociale!=null && (ragioneSociale.equals("") || ragioneSociale.equals("-"))){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO RAGIONE SOCIALE. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(inmdirizzo!=null && (inmdirizzo.equals("") || inmdirizzo.equals("-"))){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO INDIRIZZO. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(comune!=null && (comune.equals("") || comune.equals("-"))){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO COMUNE. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}


					if(siglaProvincia!=null && (siglaProvincia.equals("") || siglaProvincia.equals("-"))){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO PROVINCIA. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}


					/*if(pIva!=null && (pIva.equals("") || pIva.equals("-"))){

			logErr.write( ("ERRORE --> STABILIMENTO  NON INSERITO PER MANCANZA DEL CAMPO PARTITA IVA. -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
			nrRigheErr++;
			flagErrore=true;
		}*/

					if(dataInizioAttivita==null ){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO DATA INIZIO ATTIVITA -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}

					if(attivita!=null && (attivita.equals("") || attivita.equals("-")) ){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO DATA TIPOLOGIA ATTIVITA SVOLTA DALLO STABILIMENTO -->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;	
					}

					if(site_id==-1 ){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO CODICE SERVIZIO VETERINATIO O PER NON CORRISPONDENZA COL RANGE SEGUENTE (15,35,55,74,39,171,182,190,110,123,136,149,160)-->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}
					if(statoLab==-1 ){

						logErr.write( ("ERRORE --> SOA  NON INSERITO PER MANCANZA DEL CAMPO STATO STBILIMENTO-->" + "APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes() );
						nrRigheErr++;
						flagErrore=true;
					}







					if(flagErrore==false)
					{

						if(ragioneSociale!=null){
							org.aspcfs.modules.soa.base.Organization old_organization = org.aspcfs.modules.soa.base.Organization.load( approvaAsString,db );

							organization = new org.aspcfs.modules.soa.base.Organization();
							organization.setSiteId (site_id);
							organization.setCategoria(categoria);

							if(approvaAsString != null)
							{
								if( !approvaAsString.equals("-") )
									organization.setNumAut(approvaAsString);
								else
									organization.setNumAut("");
							}

							if(ragioneSociale != null)
							{
								if(!ragioneSociale.equals("-"))
									organization.setName(ragioneSociale);
								else
									organization.setName("");
							}

							if(ragioneSocialePrec != null)
							{
								if(!ragioneSocialePrec.equals("-"))
									organization.setBanca(ragioneSocialePrec);
								else
									organization.setBanca("");
							}

							if(pIva != null)
							{
								if(!pIva.equals("-"))
									organization.setPartitaIva(pIva);
								else
									organization.setPartitaIva("");
							}

							if(cf != null)
							{
								if(!cf.equals("-"))
									organization.setCodiceFiscale(cf);
								else
									organization.setCodiceFiscale("");
							}

							//LOOKUP
							organization.setStatoLab(statoLab);

							if(attivita != null)
							{
								if(!attivita.equals("-"))
								{
									organization.setImpianto(organization.getIdImpianto(attivita, db) + "");
								}
								else
									organization.setImpianto("");
							}

							if(codiceImpianto != null)
							{
								if(!codiceImpianto.equals("-"))
									organization.setCodiceImpianto(codiceImpianto + "");
								else
									organization.setCodiceImpianto("");
							}

							if(tipoAutorizzazione != null)
							{
								if(!tipoAutorizzazione.equals("-"))
									organization.setTipoAutorizzazzione(descrTipoAutorizzazione);
								else
									organization.setTipoAutorizzazzione("");
							}

							if(ritiReligiosi != null)
							{
								if(!ritiReligiosi.equals("-"))
									organization.setRitiReligiosi(ritiReligiosi);
								else
									organization.setRitiReligiosi("");
							}

							if(imballata != null)
							{
								if(!imballata.equals("-"))
									organization.setImballata(imballata);
								else
									organization.setImballata(-1);
							}

							organization.setOwner(userId);
							organization.setEnteredBy(userId);

							if(dataInizioAttivita!=null)
								organization.setDate2(new Timestamp(dataInizioAttivita.getTime()));

							if(dataFineAttivita!=null)
								organization.setContractEndDate(new Timestamp(dataFineAttivita.getTime()));


							org.aspcfs.modules.soa.base.OrganizationAddress ind = new org.aspcfs.modules.soa.base.OrganizationAddress();
							ind.setCity(comune);
							ind.setStreetAddressLine1(inmdirizzo);
							ind.setState(siglaProvincia);
							org.aspcfs.modules.soa.base.OrganizationAddressList address = new org.aspcfs.modules.soa.base.OrganizationAddressList();



							int orgid=-1;
							if(old_organization!=null)
								orgid=old_organization.getOrgId();



							ind.setId(address.getIdfromaddress(db, inmdirizzo, comune, siglaProvincia,orgid));

							ind.setType(5);
							address.add(ind);
							organization.setAddressList(address);
							int idImpianto = organization.getIdImpianto(db,attivita);
							organization.setImpianto(idImpianto);// accetta intero
							//organization.setCodiceImpianto("");// Stringa

							if( approvaAsString.equals("-") && statoLab == 3 )
							{
								approvaAsString = " ";
								organization.setNumAut(approvaAsString);
							}

							try
							{

								//int statoLabA = organization.checkIfExistsUpload(db,organization.getNumAut(),categoria,idImpianto);

								boolean inserito = false;
								boolean aggiornato = false;

								if( old_organization != null )
								{
									organization.setModifiedBy( userId );
									organization.setOrgId( old_organization.getOrgId() );
									organization.setModified( old_organization.getModified() );

									organization.update(db,context);
									aggiornato = true;
								}
								else
								{
									if(ragioneSociale!=null)
										inserito = organization.insert(db,context);
								}

								if( inserito == true )
								{
									System.out.println("Stabilimento Inserito correttamente");
									logOk.write( ("OK --> SOA INSERITO -->APPROVAL_NUMBER : " + approvaAsString + " CATEGORIA : " + categoria + " impianto : " + attivita + "\n").getBytes());	
									nrRigheOk++;
									log.info("Stabilimento inserito.APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n");
								}
								else if( aggiornato )
								{
									System.out.println("Stabilimento Aggiornato correttamente");
									logOk.write( ("OK --> SOA AGGIORNATO -->APPROVAL_NUMBER : " + approvaAsString + " CATEGORIA : " + categoria + " impianto : " + attivita + "\n").getBytes());	
									nrRigheOkstabAggiorn++;
									log.info("Stabilimento aggiornato.APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n");
								}
								else
								{

									logErr.write( ("ERRORE --> SOA NON INSERITO -->APPROVAL_NUMBER : " + approvaAsString + " RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes());	
									nrRigheErr++;
									log.info("Stabilimento NON inserito.APPROVAL_NUMBER : " + approvaAsString + "  RAGIONE SOCIALE : " + ragioneSociale + " impianto : " + attivita + "\n");
								}

							}
							catch (SQLException e)
							{
								System.out.println("non inserito, andato in catch");
								logErr.write( ("ERRORE NON CONFORME --> SOA NON INSERITO -->APPROVAL_NUMBER : "+approvaAsString+" RAGIONE SOCIALE : "+ragioneSociale+" impianto : "+attivita+"\n").getBytes());
								nrRigheErr++;
								log.warn("si e verificato un errore nell'inserimento/aggiornamento dello stabilimento APPROVAL_NUMBER : "+approvaAsString+" RAGIONE SOCIALE : "+ragioneSociale+" impianto : "+attivita);
								e.printStackTrace();
							}


							organization = org.aspcfs.modules.soa.base.Organization.load( approvaAsString,db );




							if( organization != null )
							{
								Timestamp now = new Timestamp( System.currentTimeMillis() );

								org.aspcfs.modules.soa.base.SottoAttivita sa = new org.aspcfs.modules.soa.base.SottoAttivita();
								sa.setAttivita					( attivita );
								sa.setCodice_impianto			( codiceImpianto );
								sa.setCodice_sezione			( codiceSezione );
								//sa.setData_fine_attivitaString	( dataFineAttivita );
								if(dataInizioAttivita!=null)
									sa.setData_inizio_attivita(new Timestamp( dataInizioAttivita.getTime()) );
								if(dataFineAttivita!=null)
									sa.setData_fine_attivita(new Timestamp(dataFineAttivita.getTime()));
								sa.setDescrizione_stato_attivita( descrizioneStatoAttivita );
								sa.setEnabled					( true );
								sa.setEntered_by				( userId );
								sa.setEntered					( now );
								sa.setId_soa			( organization.getOrgId() );
								sa.setImballata					( imballata );
								sa.setModified					( now );
								sa.setModified_by				( userId );
								sa.setRiti_religiosi			( "S".equalsIgnoreCase( ritiReligiosi ) );
								sa.setStato_attivita			( statoLab );
								sa.setTipo_autorizzazione		( "D".equalsIgnoreCase( tipoAutorizzazione ) ? (1) : (2) );

								try
								{
									org.aspcfs.modules.soa.base.SottoAttivita old = sa.alreadyExist( db );
									if( old != null )
									{
										sa.setId		( old.getId() );
										sa.setEntered	( old.getEntered() );
										sa.setEntered_by( old.getEntered_by() );

										sa.update( db );

										System.out.println("Attivita Aggiornata Correttamente");
										logOk.write( ("OK --> ATTIVITA' AGGIORNATA -->APPROVAL_NUMBER : " + approvaAsString + " ragioneSociale : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes());	
										nrRigheOksaAgg++;
										log.info("Attivita aggiornata.APPROVAL_NUMBER : " + approvaAsString + " ragioneSociale : " + ragioneSociale + " impianto : " + attivita + "\n");
									}
									else
									{
										sa.store( db,context );

										System.out.println("Inserito correttamente");
										logOk.write( ("OK --> ATTIVITA' INSERITA -->APPROVAL_NUMBER : " + approvaAsString + " ragioneSociale : " + ragioneSociale + " impianto : " + attivita + "\n").getBytes());	
										nrRigheOksa++;
										log.info("Attivita inserita.APPROVAL_NUMBER : " + approvaAsString + " ragioneSociale : " + ragioneSociale + " impianto : " + attivita + "\n");
									}

								}


								catch (Exception e)
								{
									System.out.println( "errore generico" );
									e.printStackTrace();
								}
							}
							else
							{
								System.out.println( "errore generico" );
							}
						}}
				}}
		}


		logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n  Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg ).getBytes() );

		nrRigheErr = 0;
		nrRigheOk  = 0;


		try{


			String sql="select * from organization where tipologia=97 and to_date(entered::text,'yyyy-MM-dd') < current_date and to_date(modified::text,'yyyy-MM-dd') < current_date";
			java.sql.PreparedStatement pst=  db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			String sql2="update organization set stato_lab=1 where org_id=? and tipologia=97";

			String sql3="update soa_sottoattivita set stato_attivita=1,descrizione_stato_attivita='revocata' where id_soa=?";
			java.sql.PreparedStatement pst3=  db.prepareStatement(sql3);
			java.sql.PreparedStatement pst2=db.prepareStatement(sql2);

			int count=0;
			while(rs.next()){

				int org_id=rs.getInt("org_id");
				String ragioneSociale=rs.getString("name");
				pst2.setInt(1, org_id);
				pst2.execute();
				count++;
				logOk.write( ("OK --> SOA REVOCATO -->RAGIONE SOCIALE : "+ragioneSociale  + "\n").getBytes());	

				log.info("Stabilimento Revocato Perche non Presente nel File Sintesi Ragione Sociale : " + ragioneSociale +"\n");

				pst3.setInt(1, org_id);
				pst3.execute();


			}

			logRiepilogo.write( ("Record Totali: "+ (nrRigheErr+nrRigheOk) +"\n"+"Record OK: "+nrRigheOk+"\n  Aggiornati "+nrRigheOkstabAggiorn+"\n"+"Record ERR: "+nrRigheErr+"\n Record SottoAttivita "+nrRigheOksa+"\n SottoAttivita Aggiornati "+nrRigheOksaAgg+"\n  revocati : "+count ).getBytes() );


		}catch(Exception e){
			System.out.println("Errore nell'aggiornamento degli  presenti in banca dati");

		}


		return data;}

}
