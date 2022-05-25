/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

/**
 * Qui vengono mappati gli id delle operazioni in bdr
 * 
 * Tutte le anomalie (classe e non interfaccia, nomi minuscoli, getter) servono per aggirare le limitazioni
 * dell'expression language e permetterne l'accesso anche da jsp
 */
public class IdOperazioniBdr
{
	private static final IdOperazioniBdr instance = new IdOperazioniBdr();
	
	public static final int iscrizione		= 1;
	public static final int adozione		= 2;
	public static final int decesso			= 3;
	public static final int trasferimento	= 4;
	public static final int smarrimento		= 5;
	public static final int ritrovamento	= 6;
	public static final int sterilizzazione	= 9;
	public static final int ritrovamentoSmarrNonDenunciato	= 52;
	public static final int passaporto		= 7;
	public static final int ricattura       = 51;
	
	public static final int esameAutoptico		= 12;
	public static final int smaltimentoCarogna	= 13;
	public static final int furto       		= 50;
	public static final int ricoveroInCanile			= 47;
	public static final int incompatibilitaAmbientale	= 48;
	public static final int altro						= 49;
	public static final int reimmissione				= 56;
	public static final int prelievoDna 				= 57;
	public static final int rinnovoPassaporto 			= 58;
	public static final int trasfCanile					= 59;
	public static final int ritornoProprietario      	= 60;
	public static final int prelievoLeishmania 			= 61;
	public static final int ritornoAslOrigine			= 62;
	public static final int ritornoCanileOrigine		= 63;
	
	private IdOperazioniBdr()
	{
		
	}
	
	public static IdOperazioniBdr getInstance()
	{
		return instance;
	}

	public int getIscrizione() {
		return iscrizione;
	}
	public int getAdozione() {
		return adozione;
	}
	public int getFurto() {
		return furto;
	}
	public int getDecesso() {
		return decesso;
	}
	public int getTrasferimento() {
		return trasferimento;
	}
	public int getSmarrimento() {
		return smarrimento;
	}
	public int getRitrovamento() {
		return ritrovamento;
	}
	public int getSterilizzazione() {
		return sterilizzazione;
	}
	public int getRitrovamentoSmarrNonDenunciato() {
		return ritrovamentoSmarrNonDenunciato;
	}
	public int getPassaporto() {
		return passaporto;
	}
	public int getEsameAutoptico() {
		return esameAutoptico;
	}
	public int getSmaltimentoCarogna() {
		return smaltimentoCarogna;
	}
	public int getRicoveroInCanile() {
		return ricoveroInCanile;
	}
	public int getIncompatibilitaAmbientale() {
		return incompatibilitaAmbientale;
	}
	
	public int getPrelievoDna() {
		return prelievoDna;
	}
	
	public int getPrelievoLeishmania() {
		return prelievoLeishmania;
	}

	public int getRinnovoPassaporto() {
		return rinnovoPassaporto;
	}
	
	public int getAltro() {
		return altro;
	}
	
	public int getTrasfCanile() {
		return trasfCanile;
	}
	
	public int getRitornoProprietario() {
		return ritornoProprietario;
	}

	public static int getRitornocanileorigine() {
		return ritornoCanileOrigine;
	}
	
	
	
}
