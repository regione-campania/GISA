/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;

import it.us.web.bean.guc.Utente;

import java.util.Comparator;


public class ComparatoreRuoli implements Comparator<Utente> {

	String endpoint;
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public int compare(Utente u1, Utente u2) {
			return u1.getHashRuoli().get(endpoint).getRuoloString().compareTo(u2.getHashRuoli().get(endpoint).getRuoloString());
	}

	
	
}
