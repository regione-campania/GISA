/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.util;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class UtilExc {

	XSSFSheet foglio;
	HashMap<Integer, HashMap<Integer,RegioneMerged>> regioniMerged = new HashMap<Integer,HashMap<Integer,RegioneMerged>>(); //la I chiave � l'indice riga dove inizia, la II la col
	
	public UtilExc(XSSFSheet sheet)
	{
		
		this.foglio = sheet;
		
		for(int i=0;i<sheet.getNumMergedRegions();i++)
		{
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			
			HashMap<Integer,RegioneMerged> mergsRiga = null;
			
			if(!regioniMerged.containsKey(new Integer(firstRow)))
			{
				regioniMerged.put(new Integer(firstRow), new HashMap<Integer,RegioneMerged>());
			}
			
			mergsRiga = regioniMerged.get(new Integer(firstRow));
			
			String cellRefAltoASx = new CellReference(firstRow,firstColumn).formatAsString();
			RegioneMerged mergR = new RegioneMerged(firstRow,lastRow,firstColumn,lastColumn, cellRefAltoASx);
			mergsRiga.put(new Integer(firstColumn),mergR);
		}
		
	}
	
	
	
	public String getCellValueAncheSeInRegione(Cell cell)
	{
		return getCellValueAncheSeInRegione(cell.getRowIndex(),cell.getColumnIndex());
	}
	
	
	public String getCellValueAncheSeInRegione(CellReference ref)
	{
		return getCellValueAncheSeInRegione(ref.getRow(),ref.getCol());
	}
	
	
	public String getCellValueAncheSeInRegione(int r,int c)
	{
		String toRet;
		
		//controllo che se la cella si trova in una regione, il suo valore � quello della regione che lo contiene
		RegioneMerged regioneCella = isCellInRegion(r,c);
		if(regioneCella == null)
		{ //non si trova in nessuna merged region
			toRet = foglio.getRow(r).getCell(c)+"";
		}
		else
		{
			//il valore della cella � il valore della cella in alto a sinistra della regione
			XSSFCell rootCell = foglio.getRow(regioneCella.start_row).getCell(regioneCella.start_col);
			toRet = rootCell.toString();
		}
		
		
		return toRet;
	}
	
	
	
	public int[] getBoundsRegioneAppartenenza(CellReference ref)
	{
		return getBoundsRegioneAppartenenza(ref.getRow(),ref.getCol());
	}
	
	public int[] getBoundsRegioneAppartenenza(Cell cella)
	{
		return getBoundsRegioneAppartenenza(cella.getRowIndex(),cella.getColumnIndex());
	}
	
	//ritorna array [startriga,endriga,startcol,endcol] della regione di appartenenza (stessi indici se � unitaria)
	public int[] getBoundsRegioneAppartenenza(int r,int c)
	{
		RegioneMerged regione = isCellInRegion(r,c);
		if(regione == null)
		{
			return new int[]{r,r,c,c};
		}
		else
		{
			return new int[]{regione.start_row,regione.end_row,regione.start_col,regione.end_col};
		}
	}
	
	
	
	public RegioneMerged isCellInRegion(int r,int c)
	{
		int rigaRegione = r;
		int colRegione = c;
		
		RegioneMerged toRet = null;
		
		//cerco la riga d'inizio pi� alta come indice e pi� vicina che contiene una regione che racchiude la colonna
		while(rigaRegione >= 0)
		{
			
			if(!regioniMerged.containsKey(new Integer(rigaRegione)))
			{
				rigaRegione--;
				continue;
			}
			
			HashMap<Integer,RegioneMerged> regsRiga = regioniMerged.get(rigaRegione);
			colRegione = c;
			//la regione � quella sulla riga che contiene la colonna
			while(colRegione >=  0  )
			{
				if(!regsRiga.containsKey(new Integer(colRegione)))
				{
					colRegione--;
					continue;
				}
				RegioneMerged t =  regsRiga.get(new Integer(colRegione));
				if(r <= t.end_row && c <= t.end_col )
				{
						return t; //TROVATA
				}
				else
				{
					colRegione--;
				}
				
			}
			if(colRegione < 0)
			{
				rigaRegione--;
				continue;
			}

		}
		
		return toRet;
		
	}
	
	
	public void createCellIfNull() //NON LO TRASCRIVE SU DISCO PERO'
	{
		for(int i=0;i<foglio.getLastRowNum();i++)
		{
			XSSFRow r = foglio.getRow(i);
			for(int j=0;j<r.getLastCellNum();j++)
			{
				if(r.getCell(j,Row.RETURN_BLANK_AS_NULL) == null)
				{
					r.createCell(j);
				}
			}
		}
	}
	
	
	
	public XSSFSheet getSheet()
	{
		return this.foglio;
	}
	
	
}
