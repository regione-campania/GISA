/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;


//classe che costruisce un albero dato un foglio excel
/*HP utilizzo:
 * -i nodi sono le REGIONI di celle 
 * -il livello dell'albero cresce spostandosi di colonna (come se l'albero fosse orizzontalizzato, la radice � la col 0 etc)
 * -il riferimento alla cella in un nodo � quello della cella in alto a sinistra della regione
 * -NEL CASO IN CUI SI ABBIA UNA REGIONE (NODO) PIU' LUNGA (CHE COPRE UN NUMERO MAGGIORE DI RIGHE) RISPETTO ALLA REGIONE PADRE
 * 		ALLORA VIENE SPLITTATA SECONDO LA GRANDEZZA DELLA REGIONE PADRE
 * - il file excel deve essere pretrattato, inserendo tutte le celle che risultano null tramite l'utility
 * -occorre sapere a priori qual � l'ultima colonna
 * -si usa il flag radiceFantasma per indicare che il nodo non esiste realmente come regione/cella in excel, ma � la radice fittizia usata per considerare
 *      tutte le regioni della colonna 0esima come sue figlie
 *      
 *
 */

public class Nodo {

	XSSFSheet sheet;
	ArrayList<Nodo> figli = new ArrayList<Nodo>();
	int[] boundsRegione; //[startrow,endrow,startcol,endcol]
	int[] boundsRegionePadre; //usati per sovraframmentare la regione di questo nodo, nel caso spiegato sopra
	XSSFCell cellaStart;
	UtilExc util;
	String stringValue;
	int lastColIndex;
	private int lvl;
	Connection db;
	boolean perRiconoscibili;
	
	
	public Nodo(boolean isGhostRoot,UtilExc util,XSSFSheet sheet,int[] boundsRegionePadre, XSSFCell cellaStart,int lastColumnIndex,Connection db,boolean perRiconoscibili)
	{
		this.util = util;
		this.cellaStart = cellaStart;
		this.boundsRegionePadre = boundsRegionePadre;
		this.sheet = sheet;
		this.lastColIndex = lastColumnIndex;
		this.db = db;
		this.perRiconoscibili = perRiconoscibili;
		stringValue = cellaStart != null ? cellaStart.toString() : "";
		
		if(isGhostRoot)
		{
			boundsRegione = boundsRegionePadre;
		}
		else if(cellaStart == null) 
		{
			boundsRegione = new int[]{boundsRegionePadre[0], boundsRegionePadre[0], boundsRegionePadre[2]+1,boundsRegionePadre[3]+1};
		}
		else
		{
			boundsRegione = util.getBoundsRegioneAppartenenza(cellaStart);
			//controllo se � il caso di sovraframmentare
			if(boundsRegione[1] > boundsRegionePadre[1]) //allora la regione attuale esce fuori da quella del padre, e non va bene
			{
				
				boundsRegione[1] = boundsRegionePadre[1]; //limite sup regioen padre
			}
			if(boundsRegione[0] < boundsRegionePadre[0])
			{
				boundsRegione[0] = boundsRegionePadre[0]; //limite inf della regione padre
			}
			
		}
		
		this.lvl = boundsRegione[2];
		
		
		
		String refA = new CellReference(boundsRegione[0],boundsRegione[2]).formatAsString();
//		System.out.println(refA+" "+boundsRegione[0]+" "+boundsRegione[1]+" "+boundsRegione[2]+" "+boundsRegione[3]+" :"+stringValue);
		
		
		
		//risolvo i figli (cio� tutte le distinte regioni contenute nelle righe appartenenti al range della regione attuale
		//ma dalla prima colonna successiva all'ultima della regione attuale)
		//a patto che il foglio non sia terminato
		int colDeiFigli = boundsRegione[3]+1;
		
		if(colDeiFigli > lastColumnIndex)
		{
			return;
		}
		
		HashMap<String,Boolean> temp = new HashMap<String,Boolean>();  //NB: piu' iR diversi potrebbero appartenere alla stessa regione e quindi quella regione va presa una sola volta comunque
																		//uso questa struttura di appoggio dove le chiavi sono i rif della regione (riferimento cella in alto a sx)
		if(stringValue.equals("Commercio ambulante "))
		{
			System.out.println("");
		}
		
		for(int iR = boundsRegione[0]; iR<= boundsRegione[1];iR++) 
		{							
			XSSFCell cellaRappresentativaRegione = null;
			
			RegioneMerged regioneDelFiglio = util.isCellInRegion(iR, colDeiFigli);
			 
			if(regioneDelFiglio == null) //allora � cella unitaria (oppure la cella figlia non esiste)
			{ 
				temp.put(new CellReference(iR,colDeiFigli).formatAsString(),true); //salvo come riferimento della regione (in realt� � cella unitaria) proprio quello della cella
				cellaRappresentativaRegione = sheet.getRow(iR).getCell(colDeiFigli);
			}
			else if(temp.containsKey(regioneDelFiglio.cellRef)) //allora la cella singola vagliata (che appartiene ad una regione) ha la regioen di appartenenza che gi� era stata considerata
			{
				continue;
			}
			else
			{
				temp.put(regioneDelFiglio.cellRef, true);
				cellaRappresentativaRegione = sheet.getRow(regioneDelFiglio.start_row).getCell(regioneDelFiglio.start_col);
				
				
			}
			//lancio ricorsivamente nodo verso questa regione figlia individuata
			
			figli.add(new Nodo(false,util,sheet,this.boundsRegione,cellaRappresentativaRegione,lastColumnIndex,db, perRiconoscibili));
		}
		
	}
	
	public void rappresentaSottoAlbero()
	{
		
		String ref = new CellReference(boundsRegione[0],boundsRegione[2]).formatAsString();
		
		
		System.out.print("\n");
		for(int k = 0;k<boundsRegione[2];k++)
		{
			System.out.print("--");
		}
		
		System.out.print("ref: "+ref+", lvl: "+boundsRegione[2]+" > "+stringValue);
		
		for(Nodo figlio : figli)
		{
			figlio.rappresentaSottoAlbero();
		}
		
	}
	
	public int getLvlNodo()
	{
		//il livello � l'indice colonna della regione
		return boundsRegione[2];
	}
	
	
	 
	
	public void eseguiQuery(HashMap<String,String> campiDiMioPadreDaCuiDipendo,HashMap<String,Boolean> nodiDaSaltare) throws Exception
	{
		
		if(cellaStart != null && nodiDaSaltare.containsKey(this.cellaStart.getReference().toUpperCase()) )
			return;
		
		HashMap<String,String> campiPerFigli = new HashMap<String,String>();
		if(lvl >=0 && figli != null && figli.size() > 0)
		{
			String oldConcat = null;
			
			switch(lvl)
			{/*
				case MasterListImportUtil.COL_NORMA:
					campiPerFigli.put("idNorma",DBUtilML.getIdNorma(db,this.stringValue)+"");
					break;
				case MasterListImportUtil.COL_MACROAREE:
					
					//inserisco entry in master list suap per questa macroarea
					
					campiDiMioPadreDaCuiDipendo.put("lvl","1"); //serve sapere il livello
					campiDiMioPadreDaCuiDipendo.put("concatDesc", this.stringValue);
					int idMacroarea = DBUtilML.inserisciInMasterListSuap(db, campiDiMioPadreDaCuiDipendo); //questo viene usato anche come id del nodo di root per le successive entry
					campiPerFigli.put("idRadiceMasterListSuap",idMacroarea+"");
					//per la chiamata verso i figli...
					campiPerFigli.put("concatDesc", this.stringValue); //la concatenazione al livello macroarea ha un solo valore
					campiPerFigli.put("idPadre",idMacroarea+"");
					break;
				case MasterListImportUtil.COL_CODICESEZIONE:
					
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					String idUltimoAggiornamentoMacro = campiDiMioPadreDaCuiDipendo.get("idPadre");
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","cod_macroarea","id = "+idUltimoAggiornamentoMacro, this.stringValue,String.class); 
					campiPerFigli.put("codMacro",this.stringValue);
					break;
				case MasterListImportUtil.COL_AGGREGAZIONI:
					
					campiDiMioPadreDaCuiDipendo.put("lvl","2");
					oldConcat = campiDiMioPadreDaCuiDipendo.get("concatDesc");
					campiDiMioPadreDaCuiDipendo.put("concatDesc", oldConcat+"->"+this.stringValue);
					int aggreg = DBUtilML.inserisciInMasterListSuap(db, campiDiMioPadreDaCuiDipendo);
					campiPerFigli.put("idPadre",aggreg+"");
					campiPerFigli.put("concatDesc", oldConcat+"->"+this.stringValue);
					break;
					
				case MasterListImportUtil.COL_CODICEATTIVITA:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					
					
					String idUltimoInserimentoAggregazione = campiDiMioPadreDaCuiDipendo.get("idPadre");
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","cod_aggregazione","id = "+idUltimoInserimentoAggregazione, this.stringValue,String.class); 
					campiPerFigli.put("codAggr",this.stringValue);
					break;
				case MasterListImportUtil.COL_LINEAATTIVITA:
					campiDiMioPadreDaCuiDipendo.put("lvl","3");
					oldConcat = campiDiMioPadreDaCuiDipendo.get("concatDesc");
					campiDiMioPadreDaCuiDipendo.put("concatDesc", oldConcat+"->"+this.stringValue);
					int attivita = DBUtilML.inserisciInMasterListSuap(db, campiDiMioPadreDaCuiDipendo);
					campiPerFigli.put("idPadre",attivita+"");
					campiPerFigli.put("concatDesc", oldConcat+"->"+this.stringValue);
					campiPerFigli.put("idFogliaLineaAttivita",attivita+""); //questa viene usata per l'aggancio dall'attivita specifica
					
					break;
				case MasterListImportUtil.COL_CODICENORMA:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					String idUltimoInserimentoAttivita = campiDiMioPadreDaCuiDipendo.get("idPadre");
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","codicenorma","id = "+idUltimoInserimentoAttivita, this.stringValue,String.class); 
					campiPerFigli.put("codNorma",this.stringValue);
					break;
				
				case MasterListImportUtil.COL_CODICEUNIVOCO:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","codiceunivoco","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("codUnivoco",this.stringValue);
					break;
				case MasterListImportUtil.COL_CHIINSERISCELAPRATICA:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","chiinserisce","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("chiInserisce",this.stringValue);
					break;	
				case MasterListImportUtil.COL_SCHEDASUPPLEMENTARE:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","schedasupplementare","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("schedaSupplementare",this.stringValue);
					break;		
					
					case MasterListImportUtil.COL_TIPO:
					
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","tipo","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("tipo",this.stringValue);
					break;		
					case MasterListImportUtil.COL_NOTE:
						
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","note","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("note",this.stringValue);
					break;		
					case MasterListImportUtil.COL_MAPPINGATECO:
						
					if(stringValue == null || stringValue.equals(""))
					{
						stringValue = "-1";
					}
					DBUtilML.aggiornaCampoPer(db,"master_list_suap6","mappingateco","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
					campiPerFigli.put("mappingAteco",this.stringValue);
					break;		
															
					case MasterListImportUtil.COL_ATECO1:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}
						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco1","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco1",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO2:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco2","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco2",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO3:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco3","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco3",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO4:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco4","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco4",this.stringValue);
						break;			
					case MasterListImportUtil.COL_ATECO5:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco5","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco5",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO6:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco6","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco6",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO7:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco7","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco7",this.stringValue);
						break;						
					case MasterListImportUtil.COL_ATECO8:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco8","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco8",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO9:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco9","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco9",this.stringValue);
						break;			
					case MasterListImportUtil.COL_ATECO10:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco10","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco10",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO11:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco11","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco11",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO12:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco12","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco12",this.stringValue);
						break;					
					case MasterListImportUtil.COL_ATECO13:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco13","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco13",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO14:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco14","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco14",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO15:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco15","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco15",this.stringValue);
						break;
					case MasterListImportUtil.COL_ATECO16:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco17","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco17",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO17:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco17","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco17",this.stringValue);
						break;		
					case MasterListImportUtil.COL_ATECO18:
						
						if(stringValue == null || stringValue.equals(""))
						{
							stringValue = "-1";
						}

						DBUtilML.aggiornaCampoPer(db,"master_list_suap6","ateco18","id = "+ campiDiMioPadreDaCuiDipendo.get("idPadre"), this.stringValue,String.class); 
						campiPerFigli.put("ateco18",this.stringValue);
						break;					
								
					
					
					
					
				//da qui l'inserimento � fatto su un'altra tabella (quella dei campi estesi)
				//ATTENZIONE CHE DA QUI IN POI I CAMPI POSSONO ESSERE VUOTI
				case MasterListImportUtil.COL_ATTIVITASPECIFICA:
					campiDiMioPadreDaCuiDipendo.put("lvl","1");
					campiDiMioPadreDaCuiDipendo.remove("idPadre"); //altrimenti userebbe in maniera erronea...
					campiDiMioPadreDaCuiDipendo.put("concatDesc",this.stringValue);
					campiDiMioPadreDaCuiDipendo.put("attivitaSpecifica",this.stringValue);
					int idLineaDiAttivitaMLDaAgganciare = Integer.parseInt(campiDiMioPadreDaCuiDipendo.get("idFogliaLineaAttivita"));
					campiDiMioPadreDaCuiDipendo.put("idLineaAttivitaFogliaMLDiProvenienza",idLineaDiAttivitaMLDaAgganciare+"");
					int idAttivitaSpecifica = DBUtilML.inserisciInCampiEstesi(db, campiDiMioPadreDaCuiDipendo); //questo viene salvato come radice per i campi estesi
					campiPerFigli.put("radiceAlberoCampiEstesi",idAttivitaSpecifica+"");
//					DBUtilML.agganciaCampoEstesoToMasterListSuap(db,idLineaDiAttivitaMLDaAgganciare,idAttivitaSpecifica);
					campiPerFigli.put("idPadre",idAttivitaSpecifica+"");
					campiPerFigli.put("concatDesc",  this.stringValue);
					break;
//				//i codici attivita specifica non ci interessano
				case MasterListImportUtil.COL_CARATTERIZZAZIONESPECIFICA:
					campiDiMioPadreDaCuiDipendo.put("lvl","2");
					oldConcat = campiDiMioPadreDaCuiDipendo.get("concatDesc");
					campiDiMioPadreDaCuiDipendo.put("concatDesc", oldConcat+"->"+this.stringValue);
					campiDiMioPadreDaCuiDipendo.put("caratterizzazioneSpecifica",this.stringValue);
					int idCaratterizzaz = DBUtilML.inserisciInCampiEstesi(db, campiDiMioPadreDaCuiDipendo);
					campiPerFigli.put("idPadre",idCaratterizzaz+"");
					campiPerFigli.put("concatDesc", oldConcat+"->"+this.stringValue);
					break;
//				//i codici caratterizzazione non ci interessano
				case MasterListImportUtil.COL_DETTAGLIOSPECIALIZZAZIONEPRODUTTIVA:
					campiDiMioPadreDaCuiDipendo.put("lvl","3");
					oldConcat = campiDiMioPadreDaCuiDipendo.get("concatDesc");
					campiDiMioPadreDaCuiDipendo.put("concatDesc", oldConcat+"->"+this.stringValue);
					campiDiMioPadreDaCuiDipendo.put("dettaglioSpecializzazione",this.stringValue);
					int idDettaglio = DBUtilML.inserisciInCampiEstesi(db,campiDiMioPadreDaCuiDipendo);
					campiPerFigli.put("idPadre",idDettaglio+"");
					campiPerFigli.put("concatDesc", oldConcat+"->"+this.stringValue);
					break;
				case MasterListImportUtil.COL_NOTEINFORMATIVE: //le inserisco solo sui dettagli
					String idUltimoInserimentoDettaglio = campiDiMioPadreDaCuiDipendo.get("idPadre");
					DBUtilML.aggiornaCampoPer(db,"campi_estesi_masterlistsuap51","note_informative","id = "+idUltimoInserimentoDettaglio, this.stringValue,String.class); 
					break;
				//da qui l'inserimento � fatto su un'altra tabella (quelle dei documenti)
				case MasterListImportUtil.COL_DOCUMENTIDALLEGARE:
					idUltimoInserimentoDettaglio = campiDiMioPadreDaCuiDipendo.get("idPadre"); //questo � l'id a cui agganciare il documento
					//cio� l'id dell'ultimo nodo dettaglio
					campiDiMioPadreDaCuiDipendo.put("codiciDocumenti", this.stringValue);
					Integer[] idsCodiciDocumento = DBUtilML.ottieniIdGruppoPerCodiciGruppi(db,campiDiMioPadreDaCuiDipendo);
					if(idsCodiciDocumento != null)
					{ //se � null ci sono stati errori o non ci sono documenti allegati
						DBUtilML.inserisciInCampiPerDocumentiAllegati(db,Integer.parseInt(idUltimoInserimentoDettaglio), idsCodiciDocumento);
					}
					break;
				//qui prendiamo info sui flussi (chi inserisce/chi valida/codice nazionale)
				//che vanno direttamente sulle master list (sulle foglie) e non sui campi estesi
				//posso saltare colonne chi inserisce e chi valida, visto che prendo info direttamente dalla descrizione del codice nazionale
				case MasterListImportUtil.COL_CODICENAZIONALERICHIESTO:
					campiDiMioPadreDaCuiDipendo.put("descrCodiceNazionale", this.stringValue);
					DBUtilML.inserisciInformazioniFlusso(db,campiDiMioPadreDaCuiDipendo, perRiconoscibili);
					break;
				
					
//					
			*/		
			}
			
			for(String k : campiDiMioPadreDaCuiDipendo.keySet())
			{
				if(campiPerFigli.containsKey(k))
					continue; //perch� vuol dire che avevamo gi� inserito un valore piu' aggiornato
				
				campiPerFigli.put(k,campiDiMioPadreDaCuiDipendo.get(k));
			}
			
			
		}
		for(Nodo figlio : figli)
		{
			figlio.eseguiQuery(campiPerFigli,nodiDaSaltare);
		}
		
	}
	
	

	
	
	
}
