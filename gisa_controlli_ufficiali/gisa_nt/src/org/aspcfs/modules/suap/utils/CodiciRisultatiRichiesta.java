/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.utils;

public class CodiciRisultatiRichiesta {
	
	public static final int INSERIMENTO_NUOVO_OPERATORE_OK_NON_ESISTEVA_INSERITONUOVO =1;
	public static final int INSERIMENTO_NUOVO_OPERATORE_OK_ESISTEVA_CONVERSO =2;
	public static final int INSERIMENTO_NUOVO_OPERATORE_OK_ESISTEVA_INSERITONUOVO =3;
	public static final int INSERIMENTO_NUOVO_OPERATORE_KO_STABILIMENTO_PREESISTENTE_FATTO_NULLA = 4;
	public static final int TENTATO_INSERIMENTO_CON_REFRESH =99;
	public static final int TIPO_DI_OPERAZIONE_NONSUPPORTATA = 100;
	public static final int TIPO_ERRORE_GENERICO = -1;
	//RISERVARE IL 7 POICHE' E' UTILIZZATO DA UN ALTRA ENUM
	
	public static final int VALIDATA_LINEA_PARZIALE = 7;

	
}
