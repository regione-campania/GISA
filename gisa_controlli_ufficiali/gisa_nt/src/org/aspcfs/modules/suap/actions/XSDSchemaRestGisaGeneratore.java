/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.webservicesa_generale.richiesta.suap.XmlParserGenerico;

import com.darkhorseventures.framework.actions.ActionContext;

public class XSDSchemaRestGisaGeneratore {

	
	
	
	public File generaFileXmlSecondoSchema(Connection db,String tmpFolderPath, int[] parametri, String nomeFileXml,File[] fileAllegati, String operazione, Boolean[] isValidOuput) throws Exception
	{
		
		PreparedStatement pst3 = null;
//		PreparedStatement pst4 = null;
		ResultSet rs3 = null;
		FileOutputStream  os = null;
		OutputStreamWriter osw = null;
		FileOutputStream os0 = null;
		PreparedStatement pst5 = null;
		ResultSet rs99 = null;   
		Statement st = null;
		ResultSet rs5 = null;
		PreparedStatement pst11 = null;  
		ResultSet rs11 = null;
		PreparedStatement pst12 = null;
		ResultSet rs12 = null;
		String nomeSchema = null;
		XmlParserGenerico pars = null;
		
		try
		{
			
			//			String tmpFolderPath = "C:/Users/davide/Desktop/temp_fold/";
			File tmpFold = new File(tmpFolderPath);
			tmpFold.mkdir();
			
			String queryperOttenereXml = null;
			String queryPerXmlLinee = null;
			String numeroRegistrazioneRichiesta = null;
			
			if(operazione.equalsIgnoreCase("RICHIESTA")) //inserimento
			{
				//preparo la query per estrarre la parte generale sull'impresa...
				queryperOttenereXml = "select ottieni_xml_inserimentoscia_secondo_schema_impresa(?,?)";
				pst3=db.prepareStatement(queryperOttenereXml);
				int tipoRichiesta = parametri[0];
				int idRichiestaNuovoOp = parametri[1];
				pst3.setInt(1, tipoRichiesta);
				pst3.setInt(2, idRichiestaNuovoOp);
				//preparo la query per estrarre la parte delle linee
				queryPerXmlLinee = "select ottieni_xml_inserimento_scia_secondo_schema_linee(?,?)";
				pst12 = db.prepareStatement(queryPerXmlLinee);
				pst12.setInt(1, tipoRichiesta);
				pst12.setInt(2, idRichiestaNuovoOp);
				nomeSchema = "schema_gisa.xsd";
				
			}
			else if(operazione.equalsIgnoreCase("VALIDAZIONE"))
			{
				
				//preparo la query per estrarre la parte generale sull'impresa...
				queryperOttenereXml = "select ottieni_xml_validazionescia_secondo_schema_impresa(?,?)";
				pst3=db.prepareStatement(queryperOttenereXml);
				int idStab = parametri[0];
				int idRichiestaNuovoOp = parametri[1];
				
				pst3.setInt(1, idStab);
				pst3.setInt(2, idRichiestaNuovoOp);
				
				//ritrovo numero registrazione che va nel protocollo interno
				pst11 = db.prepareStatement("select * from suap_query_validazione_scia_richiesta_perimpresa(?,?)");
				pst11.setInt(1, idStab );
				pst11.setInt(2, idRichiestaNuovoOp);
				rs11 = pst11.executeQuery();
				
				if (rs11.next())
					numeroRegistrazioneRichiesta = rs11.getString("numero_registrazione");
				
				//preparo la query per estrarre la parte delle linee
				queryPerXmlLinee = "select ottieni_xml_validazionescia_secondo_schema_linee(?,?)";
				pst12 = db.prepareStatement(queryPerXmlLinee);
				pst12.setInt(1, idStab);
				pst12.setInt(2, idRichiestaNuovoOp);
				nomeSchema = "schema_gisa_validazione.xsd";
				
			}
			else if (operazione.equalsIgnoreCase("ANAGRAFICA"))
			{
				
				//preparo la query per estrarre la parte generale sull'impresa...
				queryperOttenereXml = "select ottieni_xml_anag_secondo_schema_impresa(?)";
				pst3=db.prepareStatement(queryperOttenereXml);
				int idStab = parametri[0];
				
				pst3.setInt(1, idStab);
				
				//ritrovo numero registrazione che va nel protocollo interno
				pst11 = db.prepareStatement("select * from suap_query_anag_perimpresa(?)");
				pst11.setInt(1, idStab );
				rs11 = pst11.executeQuery();
				if (rs11.next())
					numeroRegistrazioneRichiesta = rs11.getString("numero_registrazione");
				
				//preparo la query per estrarre la parte delle linee
				queryPerXmlLinee = "select ottieni_xml_anag_secondo_schema_linee(?)";
				pst12 = db.prepareStatement(queryPerXmlLinee);
				pst12.setInt(1, idStab);
				nomeSchema = "schema_gisa_validazione.xsd";
			}
			
					
			rs3 = pst3.executeQuery();
			
			String stringaXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
			
			//metto la parte relativa ai dati generali dell'impresa
			if (rs3.next())
				stringaXml += rs3.getString(1); 
			//metto la parte relativa a tutte le linee
			rs12 = pst12.executeQuery();
			if(rs12.next())
			{
				String stringaLinee = rs12.getString(1);
				//MODIFICA CODE
				//devo trasformare il formato ritornato dalla dbi <macroarea>codice descrizione</macroarea> (e lo stesso per aggregazione e attivita 
				//nel formato <macroarea code="codice">descrizione</macroarea>
				String stringaLineeTrasformata = trasformaFormatoEntriesLinee(stringaLinee,"macroarea");
				stringaLineeTrasformata = trasformaFormatoEntriesLinee(stringaLineeTrasformata,"aggregazione");
				stringaLineeTrasformata= trasformaFormatoEntriesLinee(stringaLineeTrasformata,"attivita");
				
				stringaXml = stringaXml.replace("LINEE_QUI", stringaLineeTrasformata);
			}
			else
			{
				stringaXml = stringaXml.replace("LINEE_QUI",""); //se non ha linee non metto nulla
			}
			
			
			
			if( (operazione.equalsIgnoreCase("VALIDAZIONE") || operazione.equalsIgnoreCase("ANAGRAFICA")) && numeroRegistrazioneRichiesta != null )
			{
				stringaXml = stringaXml.replace("NUMERO_REG_QUI", numeroRegistrazioneRichiesta.toLowerCase());
			}
			
			pst12.close();
			rs12.close();
			//REV_ML (prendo la piu' recente)
			pst12 = db.prepareStatement("select rev_ml from revisione_master_list order by data_creazione desc");
			rs12 = pst12.executeQuery(); 
			int revml = 0;
			if( rs12.next() ) revml = rs12.getInt(1);
			stringaXml = stringaXml.replace("REV_ML_QUI",revml+"" );
			
			
			//eseguiamo il mapping tra i valori descrittivi presenti nel db e quelli imposti per i domini dello schema dell xml
			st = db.createStatement();
			rs99 = st.executeQuery("select * from tabella_mapping_descriz_to_xsd");
			while(rs99.next())
			{
				stringaXml = stringaXml.replaceAll(rs99.getString("valore_db"), rs99.getString("valore_xsd"));
			}
			
			File xmlFile = new File(tmpFold.getAbsolutePath()+"/"+nomeFileXml);
			
			URL urlSchemaXsd = new URL("http://srv.gisacampania.it/gisa_nt/"+nomeSchema );
			//l'uriXsdSchema non solo dobbiamo usarlo per il validatore, ma lo mettiamo anche nell'xml 
			stringaXml = stringaXml.replace("LOCATION_SCHEMA_QUI", urlSchemaXsd+"");
			stringaXml = stringaXml.replaceAll("NOME_SCHEMA_QUI", "http:www.gisacampania.it/gisa"); 
			
			//devo prendere tutti i gruppi documenti necessari (ognuno una sola volta) per tutte le linee estratte, e metterle come tag
			//figli del tag <allegati> in coda 
			//quindi prima prendo tutti gli id attivita (uno per linea). Uso una routine apposita, che necessita di prendere il file in input, quindi prima trascrivo una copia
			//dell xml cosi' com'e'
			os0 = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(os0,"UTF-8");
//			os0.write(stringaXml.getBytes());
			osw.write(stringaXml);
			osw.flush();
			try
			{
				os0.close();
			} catch(Exception ex){}
			
			try
			{
				osw.close();
			} catch(Exception ex){}
			
			pars = new XmlParserGenerico(xmlFile);
			//..prima pero' devo normalizzare togliere i comuni dall'ind ede legale (che nella view da cuie'recuperato,e'l'unico indirizzo contenente i comuni)
			try
			{
				
				 
				//il II par sono i campi non voluti
				HashMap<String,String> parametriInputsPerSciaComeSuXml = pars.getAllChildsValuesFor("item", new String[]{"#text","allegati","rev_ml","id_protocollo_origine","lista_linee"} );
				
				
				
				String indRappSedeLegaleToAdjust = parametriInputsPerSciaComeSuXml.get("ind_rapp_sede_legale");
			
				int indLastNum = -1;
				//cerco indice ultimo char prettamente numerico
				for(int z = 0;z<indRappSedeLegaleToAdjust.length(); z++)
				{
					try
					{
						Integer.parseInt(indRappSedeLegaleToAdjust.charAt(z)+"");
					}
					catch(Exception ex) //none'un numero
					{
						continue;
					}
					//trovato numero, aggiorno indice ultimo char numerico 
					indLastNum = z;
					
				}
				//e quindi levo tutto quello che viene dopo (che sono comune e prov che non ci interessano in questo tag)
				indRappSedeLegaleToAdjust = indRappSedeLegaleToAdjust.substring(0,indLastNum+1);
				//e ora sostituisco nell'xml che sto generando
				int startA = stringaXml.indexOf("<ind_rapp_sede_legale>")+"<ind_rapp_sede_legale>".length();
				int endA = stringaXml.indexOf("</ind_rapp_sede_legale>");
				stringaXml = stringaXml.substring(0,startA)+indRappSedeLegaleToAdjust+stringaXml.substring(endA);
				
			}
			catch(Exception ex){ex.printStackTrace();} //se fallisce, non faccio niente e lascio quello estratto dal db
			
			
			
			
			
			//...ora continuo con l'estrazione dei gruppi file allegati richiesti
			HashMap<String,Boolean> nomiGruppiFileRichiesti =  new HashMap<String,Boolean>();
			ArrayList<String> codiciAttivita = null;
			
				//costruisco la lista dei tag figli del tag <allegati> come <nomegruppofile><nomegruppofile_nomeallegato><nomegruppofile>
			String temp = "\n<allegati>\n";
			for(String nomeGruppo : nomiGruppiFileRichiesti.keySet())
			{
				temp += ("<?>"+"?_NOMEALLEGATOQUI"+"</?>").replaceAll("\\?", nomeGruppo.toLowerCase())+"\n";
			}
			temp += "</allegati>\n";

			//li metto in coda
			stringaXml = stringaXml.replace("</item>",temp+"</item>");

			Pattern pat = Pattern.compile(" .*_");
			Matcher m = null;
			//inserisco i titoli dei file
			for(File f : fileAllegati)
			{
				//devo estrarre il nome del gruppo a cui il file trovato e associato
				//NB: se nel documentale ci sono file che non riguardano la linea che e presente adesso nel file xml
				//non vengono inseriti (questo puo' capitare se si tratta di validazione, dove sono presenti anche file che non riguardano
				//la linea che si e validata)
				String nomeFile = f.getName();
				m = pat.matcher(nomeFile);
				if(m.find())
				{
					int startInd = m.start();
					int endInd = m.end();
					String nomeGruppo = nomeFile.substring(startInd+1, endInd-1);
					//sostituisco nel tag <nomegruppo> la parte ?_NOMEALLEGATOQUI
					stringaXml = stringaXml.replace(">"+nomeGruppo.toLowerCase()+"_NOMEALLEGATOQUI", ">"+nomeFile);

				}

			}
				
			
			os = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(os,"UTF-8");
			osw.write(stringaXml);
//			os.write(stringaXml.getBytes()); //scrivo il contenuto estratto dal db sul file
//			osw.close();
//			os.close();
			osw.flush();
			
			//prima di restituirlo provo anche a validarlo
//			String pathXsdSchema = cont.getServletContext().getRealPath("schema_gisa.xsd");
			ArrayList<String[]> campiInvalidi = new ArrayList<String[]>();
			 
			
			System.out.println("DEBUG -> IL FILE GENERATO DALL'XML UTILITY E' DISPONIBILE IN "+xmlFile.getAbsolutePath());
			return xmlFile;
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace(); 
			throw new EccezioneGenerazioneXml(); //poiche la gestiamo fuori
		}
		finally
		{
			
			try{ os.close();} catch(Exception ex){}
			try{os0.close();} catch(Exception ex){}
			try{pst3.close();} catch(Exception ex){}
			try{rs3.close();} catch(Exception ex){}
			try{pst5.close();} catch(Exception ex){}
			try{rs5.close();} catch(Exception ex){}
			try{st.close();} catch(Exception ex){}
			try{rs99.close();} catch(Exception ex){}
			try{rs11.close();}catch(Exception ex){}
			try{pst11.close();}catch(Exception ex){}
			try{pst12.close();}catch(Exception ex){}
			try{rs12.close();}catch(Exception ex){}
			try{osw.close();} catch(Exception ex){}
			
		}
		
		
		
	}
	
	
	

	//nometipoe'macroarea,aggregazione o attivita
	private String trasformaFormatoEntriesLinee(String toTransform, String nomeTipo) {
		
		int iLastOcc = -1;
		//per le macroarea
		while( (iLastOcc = toTransform.indexOf("<?>".replace("?",nomeTipo),iLastOcc+1)) != -1 )
		{
			//estraggo il codice, che si trova subito come prima entry numerica del tag
			int iStart = iLastOcc + ("<?>".replace("?", nomeTipo).length());
			int iEnd = toTransform.indexOf("</?>".replace("?", nomeTipo),iLastOcc);
			String t = toTransform.substring(iStart,iEnd);
			String codice = t.trim().split(" ")[0]; //il primo spazioe'sicuramente il separatore dopo il codice
			String descr = t.trim().substring(t.trim().indexOf(" ")+1);
			toTransform = toTransform.substring(0,iLastOcc) + "<? code=\"$\">".replace("?",nomeTipo).replace("$", codice)
					+descr+ toTransform.substring(iEnd);
		}
		
		return toTransform;
		
		
	}
	
	
	
	
	
	
	
	
	//METODI USATI PER LA PARTICOLARE RAPPRESENTAZIONE 
		
	public File rappresentaTuttiXmlInUnicaAnagrafica(File[] files,File tmpFold) throws Exception
	{
		File toRet = null;
		String unicaAnag = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\n<items><!-- LISTA ANAGRAFICHE -->"
				+ "\n\n\n\n$\n\n</items>";
		FileOutputStream fos = null;
		try
		{
			
			
			for(File singoloXml : files)
			{
				unicaAnag = aggiornaStringaUnicaAnag(singoloXml,unicaAnag);
			}
			
			
			//levo l'ultimo $
			unicaAnag = unicaAnag.replace("$","");
			
			//preparo file anag generale
			File anagGenF = new File(tmpFold.getAbsoluteFile()+"/anagrafica.generale.xml");
			fos = new FileOutputStream(anagGenF);
			fos.write(unicaAnag.getBytes());
			fos.close();
			
			return anagGenF;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{fos.close();}catch(Exception ex){}
		}
		
		
	}
		
		
	
		
			
	private String aggiornaStringaUnicaAnag(File xmlGenerato, String unicaAnag) throws Exception
	{
				
				FileInputStream fis = null;
				Pattern pat = Pattern.compile("<item[^>]*>");
				
				
				try
				{
					fis = new FileInputStream(xmlGenerato);
					byte[] buff0 = new byte[1024];
					int r0 = -1;
					StringBuffer sb = new StringBuffer();
					
					while((r0=fis.read(buff0))>0)
					{
						sb.append(new String(buff0,0,r0));
					}
					
					fis.close();
					
					//estraggo la parte contenuta in item per la singola anagrafica, e la pulisco
					
					String xmlContent = sb.toString();
					
					
		//			int si = xmlContent.indexOf(">");
					Matcher mat = pat.matcher(xmlContent);
					mat.find();
					int si = mat.end();
					int ei = xmlContent.indexOf("</item>");
					xmlContent = "<item><!-- ENTRY ANAGRAFICA -->\n"
							+xmlContent.substring(si+1); //cos� ho tolto gli attributi del tag <item>	
					//ora devo togliere gli allegati
					si = xmlContent.indexOf("<allegati>");
					ei = xmlContent.indexOf("</allegati>");
					xmlContent = xmlContent.substring(0,si)+xmlContent.substring(ei+"</allegati>".length());
					//metto nell'xml unico
					String toRet = unicaAnag.replace("$",xmlContent+"\n$");
					return toRet;
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw ex;
				}
				finally
				{
					try{fis.close();}catch(Exception ex){}
				}
	}
	
		 

	
}
