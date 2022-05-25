/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.controller;

	import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

	public interface TreeDAO {
		
		public void createTree(Connection db,String nomeTabella,String tabella,String descrizione) throws SQLException;
		public ArrayList<String> readLookup(Connection db) ;
		public Tree dettaglioTree(String nomeTabella ,String idColonna,String padreColonna,String descrzioneColonna,String livello ,String nodo,String campoEnabled,String colonnaSelezione, Connection db) ;
		public String aggiungiLivello(Connection db,String nomeTabella,int idPadre,int livello);
		public ArrayList<Tree> list(Connection db) throws SQLException ;
		public void salvaLivello(Connection db,String nomeAlbero,String combo , int idPadre,int livello,String[] valori) throws SQLException;

	}