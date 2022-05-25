/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

/**
 * Qui vengono mappati gli id della sezione "operazioni richieste" dell'accettazione
 * 
 * Tutte le anomalie (classe e non interfaccia, nomi minuscoli, getter) servono per aggirare le limitazioni
 * dell'expression language e permetterne l'accesso anche da jsp
 */
public class IdRichiesteVarie
{
	private static final IdRichiesteVarie instance = new IdRichiesteVarie();
	
	public static final int richiestaPrelievi			= 8;
	public static final int sterilizzazione				= 9;
	public static final int profilassiRabbia			= 10;
	public static final int prontoSoccorso				= 11;
	public static final int esameNecroscopico			= 12;
	public static final int smaltimentoCarogna			= 13;
	public static final int ritorvamentoAltraAsl		= 14;
	public static final int ricoveroInCanile			= 47;
	public static final int incompatibilitaAmbientale	= 48;
	public static final int altro						= 49;
	public static final int attivitaEsterne  			= 53;
	
	private IdRichiesteVarie()
	{
		
	}
	
	public static IdRichiesteVarie getInstance()
	{
		return instance;
	}

	public int getRichiestaPrelievi() {
		return richiestaPrelievi;
	}

	public int getSterilizzazione() {
		return sterilizzazione;
	}

	public int getProfilassiRabbia() {
		return profilassiRabbia;
	}

	public int getProntoSoccorso() {
		return prontoSoccorso;
	}

	public int getEsameNecroscopico() {
		return esameNecroscopico;
	}

	public int getSmaltimentoCarogna() {
		return smaltimentoCarogna;
	}

	public int getRitorvamentoAltraAsl() {
		return ritorvamentoAltraAsl;
	}

	public int getRicoveroInCanile() {
		return ricoveroInCanile;
	}

	public int getIncompatibilitaAmbientale() {
		return incompatibilitaAmbientale;
	}

	public int getAltro() {
		return altro;
	}
	
	public int getAttivitaEsterne() {
		return attivitaEsterne;
	}

}
