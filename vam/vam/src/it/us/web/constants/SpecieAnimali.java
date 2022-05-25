/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

/**
 * Qui vengono mappati gli id delle specie animale
 * 
 * Tutte le anomalie (classe e non interfaccia, nomi minuscoli, getter) servono per aggirare le limitazioni
 * dell'expression language e permetterne l'accesso anche da jsp
 */
public class SpecieAnimali
{
	private static final SpecieAnimali instance = new SpecieAnimali();
	
	public static final int cane		= 1;
	public static final int gatto		= 2;
	public static final int sinantropo	= 3;
	
	private SpecieAnimali()
	{
		
	}
	
	public static SpecieAnimali getInstance()
	{
		return instance;
	}

	public int getCane() {
		return cane;
	}

	public int getGatto() {
		return gatto;
	}

	public int getSinantropo() {
		return sinantropo;
	}

}
