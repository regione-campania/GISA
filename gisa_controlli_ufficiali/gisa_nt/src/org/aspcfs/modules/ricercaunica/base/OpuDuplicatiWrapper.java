/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricercaunica.base;

import java.util.ArrayList;

import org.aspcfs.modules.opu.base.Operatore;

public class OpuDuplicatiWrapper {

	private ArrayList<Operatore> operatoriTrovati;
	
	public OpuDuplicatiWrapper(ArrayList<Operatore> trovati)
	{
		this.setOperatoriTrovati(trovati);
	}

	
	public ArrayList<Operatore> getOperatoriTrovati() {
		return operatoriTrovati;
	}

	public void setOperatoriTrovati(ArrayList<Operatore> operatoriTrovati) {
		this.operatoriTrovati = operatoriTrovati;
	}
}
