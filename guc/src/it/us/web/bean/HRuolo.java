/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;



/**
 * Questo bean serve solo a far creare la tabella permessi_ruoli ad hibernate
 */

//@Entity
//@Table(name = "permessi_ruoli", schema = "public")
public class HRuolo
{
	
	String ruolo;
	String descrizione;
	int id;
	
	public String getDescrizione()
	{
		return descrizione;
	}
	
	public void setDescrizione(String descrizione)
	{
		this.descrizione = ( descrizione == null )?( "" ):( descrizione );
	}

	//@Column(name = "nome", unique = true)
	public String getRuolo() {
		return ruolo;
	}

	//@Id
	//@GeneratedValue( strategy = GenerationType.IDENTITY )
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
}
