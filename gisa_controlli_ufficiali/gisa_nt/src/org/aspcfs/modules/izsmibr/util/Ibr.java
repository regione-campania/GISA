/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.xerces.xs.XSModel;
import org.aspcfs.modules.izsmibr.base.CampioneMolluschi;
import org.aspcfs.modules.izsmibr.base.DsESITOIBRIUS;
import org.aspcfs.modules.izsmibr.base.DsESITOIBRIUS.PARAMETERSLIST;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Ibr {
	
	
	
public static CampioneMolluschi getObjectFromRow(Row row) throws Exception {  

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try
		{
			//21 è il numero di colonne massime contenute nel file di esempio, se si cambia il tracciato, va modificato il numero
			for(int j=row.getFirstCellNum();j<21;j++)
			{
				if(row.getCell(j).getCellType()==Cell.CELL_TYPE_NUMERIC && row.getCell(j)==null)
						throw new Exception("Valori null nella riga. Tutti i campi sono obbligatori.");
				else if((row.getCell(j).getCellType()==Cell.CELL_TYPE_STRING || row.getCell(j).getCellType()==Cell.CELL_TYPE_BLANK) && (row.getCell(j)==null || row.getCell(j).getStringCellValue().equals("")))
					throw new Exception("Valori null nella riga. Tutti i campi sono obbligatori.");
			}
			
		CampioneMolluschi ca = new CampioneMolluschi();
		String pianoCodice = "" ;
		if(row.getCell(0).getCellType()==Cell.CELL_TYPE_NUMERIC)
			pianoCodice =row.getCell(0).getNumericCellValue()+"";
		else
		{
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			pianoCodice =row.getCell(0).getStringCellValue();
		}
		ca.setPianoCodice(pianoCodice);
		
		String numeroSchedaPrelievo = "" ;
		if(row.getCell(1).getCellType()==Cell.CELL_TYPE_NUMERIC)
			numeroSchedaPrelievo =row.getCell(1).getNumericCellValue()+"";
		else
		{
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			numeroSchedaPrelievo =row.getCell(1).getStringCellValue();
		}
		ca.setNumeroSchedaPrelievo(numeroSchedaPrelievo);
		
			

		String DATAPREL = "" ;
		if(row.getCell(2).getCellType()==Cell.CELL_TYPE_NUMERIC)
			DATAPREL =row.getCell(2).getNumericCellValue()+"";
		else
		{
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			DATAPREL =row.getCell(2).getStringCellValue();
			sdf.parse(DATAPREL);
		}
			
		ca.setDataPrel(DATAPREL);
		
		String luogoPrelievoCodice = "" ;
		if(row.getCell(3).getCellType()==Cell.CELL_TYPE_NUMERIC)
			luogoPrelievoCodice =row.getCell(3).getNumericCellValue()+"";
		else
		{
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			luogoPrelievoCodice =row.getCell(3).getStringCellValue();
		}
			
		
		ca.setLuogoPrelievoCodice(luogoPrelievoCodice);
			
		
		String metodoCampionamentoCodice = "" ;
		if(row.getCell(4).getCellType()==Cell.CELL_TYPE_NUMERIC)
			metodoCampionamentoCodice =row.getCell(4).getNumericCellValue()+"";
		else
		{
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			metodoCampionamentoCodice =row.getCell(4).getStringCellValue();
		}
		ca.setMetodoCampionamentoCodice(metodoCampionamentoCodice);
		
		String motivoCodice = "" ;
		if(row.getCell(5).getCellType()==Cell.CELL_TYPE_NUMERIC)
			motivoCodice =row.getCell(5).getNumericCellValue()+"";
		else
		{
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			motivoCodice =row.getCell(5).getStringCellValue();
		}
		ca.setMotivoCodice(motivoCodice);

		
		String prelNome= "" ;
		if(row.getCell(6).getCellType()==Cell.CELL_TYPE_NUMERIC)
			prelNome =row.getCell(6).getNumericCellValue()+"";
		else
		{
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			prelNome =row.getCell(6).getStringCellValue();
		}
		ca.setPrelNome(prelNome);
		
		String prelCognome = "" ;
		if(row.getCell(7).getCellType()==Cell.CELL_TYPE_NUMERIC)
			prelCognome =row.getCell(7).getNumericCellValue()+"";
		else
		{
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			prelCognome =row.getCell(7).getStringCellValue();
		}
		
		ca.setPrelCognome(prelCognome);
		
		
	
		/*ci sarebbe il nome e cognome prima del codice fiscale*/
		
		String prelCodFiscale = "" ;
		if(row.getCell(8).getCellType()==Cell.CELL_TYPE_NUMERIC)
			prelCodFiscale = NumberToTextConverter.toText(row.getCell(8).getNumericCellValue());
			
		else
		{
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			prelCodFiscale =row.getCell(8).getStringCellValue();
		}
		
		ca.setPrelCodFiscale(prelCodFiscale);
			

			
			String sitoCodice = "" ;
			if(row.getCell(9).getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
				Double d = row.getCell(9).getNumericCellValue();
				int value = d.intValue();
				sitoCodice =value+"";
			}
			else
			{
				row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				sitoCodice =row.getCell(9).getStringCellValue();
			}
			ca.setSitoCodice(sitoCodice);
			
			String comuneCodiceIstatParziale = "" ;
			if(row.getCell(10).getCellType()==Cell.CELL_TYPE_NUMERIC )
			{
				Double d = row.getCell(10).getNumericCellValue();
				int value = d.intValue();
				comuneCodiceIstatParziale =value+"";
			}
			else
			{
				row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
				comuneCodiceIstatParziale =row.getCell(10).getStringCellValue();
			}
			ca.setComuneCodiceIstatParziale(comuneCodiceIstatParziale);
			
			
			String siglaProvincia = "" ;				
			if(row.getCell(11).getCellType()==Cell.CELL_TYPE_NUMERIC)
				siglaProvincia =row.getCell(11).getNumericCellValue()+"";
			else
			{
				row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
				siglaProvincia =row.getCell(11).getStringCellValue();
			}
			ca.setSiglaProvincia(siglaProvincia);

			
			
			String laboratorioCodice = "" ;				
			if(row.getCell(12).getCellType()==Cell.CELL_TYPE_NUMERIC)
				laboratorioCodice =row.getCell(12).getNumericCellValue()+"";
			else
			{
				row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
				laboratorioCodice =row.getCell(12).getStringCellValue();
			}
			ca.setLaboratorioCodice(laboratorioCodice);
			
			Double latitudine =0.0 ;				
			if(row.getCell(13).getCellType()==Cell.CELL_TYPE_NUMERIC)
				latitudine =row.getCell(13).getNumericCellValue();
			ca.setLatitudine(latitudine+"");
			
			
			Double longitudine =0.0 ;				
			if(row.getCell(14).getCellType()==Cell.CELL_TYPE_NUMERIC)
				longitudine =row.getCell(14).getNumericCellValue();
		ca.setLongitudine(longitudine+"");
			
			
		String codiceContaminante = "" ;				
		if(row.getCell(15).getCellType()==Cell.CELL_TYPE_NUMERIC)
			codiceContaminante =row.getCell(15).getNumericCellValue()+"";
		else
		{
			row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
			codiceContaminante =row.getCell(15).getStringCellValue();
		}
		ca.setCodiceContaminante(codiceContaminante);
		
		String numeroSchedaPrelievoCampione = "" ;				
		if(row.getCell(16).getCellType()==Cell.CELL_TYPE_NUMERIC)
			numeroSchedaPrelievoCampione =row.getCell(16).getNumericCellValue()+"";
		else
		{
			row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
			numeroSchedaPrelievoCampione =row.getCell(16).getStringCellValue();
		}
			
		
		
		String progreivoCampione = "" ;				
		if(row.getCell(17).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
			Double d = row.getCell(17).getNumericCellValue();
			int value = d.intValue();
			progreivoCampione =value+"";
		}
		else
		{
			row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
			progreivoCampione =row.getCell(17).getStringCellValue();
		}
		ca.setProgressivoCampione(progreivoCampione);
		
		String foodexCodice = "" ;				
		if(row.getCell(18).getCellType()==Cell.CELL_TYPE_NUMERIC)
			foodexCodice =row.getCell(18).getNumericCellValue()+"";
		else
		{
			row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
			foodexCodice =row.getCell(18).getStringCellValue();
		}
		ca.setFoodexCodice(foodexCodice);
		
		String ProfFondale = "" ;				
		if(row.getCell(19).getCellType()==Cell.CELL_TYPE_NUMERIC)
			ProfFondale =row.getCell(19).getNumericCellValue()+"";
		else
		{
			row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
			ProfFondale =row.getCell(19).getStringCellValue();
		}
		ca.setProfFondale(ProfFondale);
		
		String classificazioneDellaZonaDiMareCe8542004 = "" ;				
		if(row.getCell(20).getCellType()==Cell.CELL_TYPE_NUMERIC)
			classificazioneDellaZonaDiMareCe8542004 =row.getCell(20).getNumericCellValue()+"";
		else
		{
			row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
			classificazioneDellaZonaDiMareCe8542004 =row.getCell(20).getStringCellValue();
		}
		ca.setClassificazioneDellaZonaDiMareCe8542004(classificazioneDellaZonaDiMareCe8542004);
		
		return ca;
		}
		catch(Exception e)
		{
			throw e ;
		}
		
	}


	public static DsESITOIBRIUS getObjectFromText(String record) throws Exception {  

		
		try
		{
		
		DsESITOIBRIUS obj =null;
//		String record ="I0301                 2015  140639059BN066VCRGRG77B14A783C0121IT0419900568342411201502122015NEGATIVO                              ";
								
		String ibr_param1_op 				= record.substring(0, 1).trim();
		String ibr_param2_codist 			=  record.substring(1, 3).trim();
		String ibr_param3_codsedediagn 		=  record.substring(3, 5).trim();
		String ibr_param4_codiceprel 		=  record.substring(5, 22).trim();
		String ibr_param5_annoaccet 		=  record.substring(22, 26).trim();
		String ibr_param6_numaccett 		=  record.substring(26, 34).trim();
		String ibr_param7_codiceazeinda 	=  record.substring(34, 42).trim();
		String ibr_param8_idfiscaleprop 	=  record.substring(42, 58).trim();
		String ibr_param9_specieallev 		=  record.substring(58, 62).trim();
		String ibr_param10_codicecapo 		=  record.substring(62, 76).trim();
		String ibr_param11_dataprelievo 	=  record.substring(76, 84).trim();
		String ibr_param12_dataesito 		=  record.substring(84, 92).trim();
		
		String ibr_param13_esitoqualitativo = "" ;
		String ibr_param14_anno_campagna    = record.substring(92, 96).trim();
		try
		{
			ibr_param13_esitoqualitativo = record.substring(96, 107).trim();
		}catch( Exception e)
		{
			ibr_param13_esitoqualitativo = record.substring(96, record.length()).trim();

		}


		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String dtEsito=null;
		if (ibr_param12_dataesito!=null &&!"".equals(ibr_param12_dataesito))
		{
			
			dtEsito = sdf2.format(sdf.parse(ibr_param12_dataesito));
		}
		
		String dtPrelievo=null;
		if (ibr_param11_dataprelievo!=null &&!"".equals(ibr_param11_dataprelievo))
		{
			
			dtPrelievo = sdf2.format(sdf.parse(ibr_param11_dataprelievo));
		}
		
		

		ObjectFactory test = new ObjectFactory();
		obj = test.createDsESITOIBRIUS();
		PARAMETERSLIST p = new PARAMETERSLIST();
		p.setPANNOACCETTAZIONE( new BigDecimal(ibr_param5_annoaccet));
		p.setPCODICEAZIENDA(ibr_param7_codiceazeinda);
		p.setPCODICECAPO(ibr_param10_codicecapo);
		p.setPCODICEISTITUTO(ibr_param2_codist);
		p.setPCODICEPRELIEVO(ibr_param4_codiceprel);
		p.setPCODICESEDEDIAGNOSTICA(ibr_param3_codsedediagn);		
		p.setPDATAESITO(dtEsito);
		p.setPDATAPRELIEVO(dtPrelievo);
		p.setPESITOQUALITATIVO(ibr_param13_esitoqualitativo);
		p.setPIDFISCALEPROPRIETARIO(ibr_param8_idfiscaleprop);
		p.setPNUMEROACCETTAZIONE(ibr_param6_numaccett);
		p.setPSPECIEALLEVATA(ibr_param9_specieallev);
		p.setPEIBRID(new BigDecimal(0));
		p.setPANNOCAMPAGNA( new BigDecimal(ibr_param14_anno_campagna));
		
		obj.getPARAMETERSLIST().add(p);
		return obj;
		}
		catch(Exception e)
		{
			throw e ;
		}
		
	}
	
	
	
	public static String jaxbObjectToXML(DsESITOIBRIUS emp) throws JAXBException {

        try {
            JAXBContext context = JAXBContext.newInstance(DsESITOIBRIUS.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            //for pretty-print XML in JAXB
           // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            // Write to System.out for debugging
            // m.marshal(emp, System.out);

            // Write to File
            m.marshal(emp,  sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e ;
        }
        
    }
	



}
