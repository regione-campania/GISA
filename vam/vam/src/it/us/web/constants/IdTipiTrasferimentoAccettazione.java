/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

public class IdTipiTrasferimentoAccettazione
{
	private static final IdTipiTrasferimentoAccettazione instance = new IdTipiTrasferimentoAccettazione();
	
	public static final int proprietaIntraAsl		= 1;
	public static final int proprietaExtraAsl		= 2;
	public static final int proprietaFuoriRegione	= 3;
	public static final int residenza				= 4;
	
	private IdTipiTrasferimentoAccettazione()
	{
		
	}
	
	public static IdTipiTrasferimentoAccettazione getInstance()
	{
		return instance;
	}

	public int getProprietaIntraAsl() {
		return proprietaIntraAsl;
	}

	public int getProprietaExtraAsl() {
		return proprietaExtraAsl;
	}

	public int getProprietaFuoriRegione() {
		return proprietaFuoriRegione;
	}

	public int getResidenza() {
		return residenza;
	}
}
