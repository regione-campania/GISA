/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.util.Hashtable;

public class CsvLunghezzaFissa
{

	public static class Colonna
	{
		private int		lunghezza		= -1;
		private int		indiceIniziale	= -1;
		private String	nome			= null;
		
		public Colonna(int lunghezza, String nome)
		{
			this.lunghezza	= lunghezza;
			this.nome		= nome;
		}
		
		public int getLunghezza() {
			return lunghezza;
		}
		public int getIndiceFinale() {
			return lunghezza + indiceIniziale - 1;
		}
		public void setLunghezza(int lunghezza) {
			this.lunghezza = lunghezza;
		}
		public String getNome() {
			return nome;
		}
		public int getIndiceIniziale() {
			return indiceIniziale;
		}

		public void setIndiceIniziale(int indiceIniziale) {
			this.indiceIniziale = indiceIniziale;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
	}
	
	
	private Hashtable<String, Colonna>	ht		= null;
	private String						riga	= null;
	
	public CsvLunghezzaFissa( Colonna[] colonne )
	{
		ht				= new Hashtable<String, Colonna>();
		
		int index = 0;
		for( Colonna c: colonne )
		{
			c.setIndiceIniziale( index );
			ht.put( c.getNome(), c );
			index += c.getLunghezza();
		}
	}
	
	public void setRiga( String riga )
	{
		this.riga = riga;
	}
	
	public String get( String colonna )
	{
		String ret = null;
		
		Colonna c = ht.get( colonna );
		
		if( (c != null) && (riga != null) && ( riga.length() > c.getIndiceFinale() ) )
		{
			ret = riga.substring( c.getIndiceIniziale(), c.getIndiceFinale() + 1 );
		}
		
		return ret;
	}

}
