/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.anagrafica;

import java.util.ArrayList;
import java.util.List;

import com.anagrafica_noscia.prototype.base_beans.Impresa;
import com.anagrafica_noscia.prototype.base_beans.RelazioneStabilimentoLineaProduttiva;
import com.anagrafica_noscia.prototype.base_beans.SoggettoFisico;
import com.anagrafica_noscia.prototype.base_beans.Stabilimento;



/*classe che rappresenta un'istanza di entry di anagrafica
 * e che raggruppa tutte le entita' coinvolte (stabilimento, legale etc...)
 */
public abstract class AnagraficaBase {
	
	
	private Impresa impresa;
	private  List<SoggettoFisico> legaliRappresentanti; /*anche se al max sara' 1 */
	private  List<Stabilimento> stabilimenti; /*anche se al max sara' 1 */
	
	public Impresa getImpresa() {
		return impresa;
	}
	public void setImpresa(Impresa impresa) {
		this.impresa = impresa;
	}
	public  List<SoggettoFisico> getLegaliRappresentanti() {
		return legaliRappresentanti;
	}
	public void setLegaliRappresentanti(List<SoggettoFisico> legaliRappresentanti) {
		this.legaliRappresentanti = legaliRappresentanti;
	}
	public  List<Stabilimento> getStabilimenti() {
		return stabilimenti;
	}
	public void setStabilimenti(List<Stabilimento> stabilimenti) {
		this.stabilimenti = stabilimenti;
	}
}
