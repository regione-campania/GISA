/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

public class Sql
{
	public static final String TABLE_FUNZIONE = "PERMESSI_FUNZIONE";
	public static final String TABLE_SUBFUNZIONE = "PERMESSI_SUBFUNZIONE";
	public static final String TABLE_UTENTI = "UTENTI";
	public static final String TABLE_GUC_UTENTI = "guc_utenti";
	public static final String SEQ_FUNZIONE = "permessi_funzione_id_funzione_seq";
	public static final String SEQ_GUI = "permessi_gui_id_gui_seq";
	public static final String SEQ_SUBFUNZIONE = "permessi_subfunzione_id_subfunzione_seq";
	

	public static final String VIEW_GUI = "PERMESSI_GUI_VIEW";
	
	public static final String GET_USER = 	"SELECT * FROM "  + TABLE_UTENTI 
											+ " WHERE " +TABLE_UTENTI + ".USERNAME = ? "
											+ " AND " + TABLE_UTENTI  + ".TRASHED_DATE IS NULL AND " + TABLE_UTENTI  + ".ENABLED = TRUE ";

	public static final String GET_USER_FROM_ID = "SELECT * FROM "  + TABLE_UTENTI 
		+ " WHERE " +TABLE_UTENTI + ".ID = ? "
		+ " AND " + TABLE_UTENTI  + ".TRASHED_DATE IS NULL  AND " + TABLE_UTENTI  + ".ENABLED = TRUE ";
	
	public static final String GET_ALL_UTENTI = 	"SELECT * FROM "  + TABLE_UTENTI
													+ " WHERE " + TABLE_UTENTI  + ".TRASHED_DATE IS NULL AND " + TABLE_UTENTI  + ".ENABLED = TRUE ";
	
	public static final String GET_ALL_GUI_VIEW = " SELECT * FROM " + VIEW_GUI ;
	
	public static final String GET_GUI_VIEW_FUNZ = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_FUNZIONE = ?";
	
	public static final String GET_GUI_VIEW_FUNZ_BY_ID = " SELECT * FROM " + VIEW_GUI + " WHERE ID_FUNZIONE = ? ORDER BY NOME_SUBFUNZIONE, NOME_GUI";
	
	public static final String GET_GUI_VIEW_FUNZ_ALL = " SELECT * FROM " + VIEW_GUI + " ORDER BY NOME_FUNZIONE, NOME_SUBFUNZIONE, NOME_GUI";
	
	public static final String GET_GUI_VIEW_BY_ID = " SELECT * FROM " + VIEW_GUI + " WHERE ID_GUI = ?";
	
	public static final String GET_GUI_VIEW_SUBFUNZ = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_SUBFUNZIONE = ?";
	
	public static final String GET_GUI_VIEW_GUI = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_GUI = ?";
	
	public static final String GET_GUI_VIEW_FUNZ_SUBFUNZ = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_FUNZIONE = ? AND NOME_SUBFUNZIONE = ?";
	
	public static final String GET_GUI_VIEW_SUBFUNZ_GUI = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_SUBFUNZIONE = ? AND NOME_GUI = ?";
	
	public static final String GET_GUI_VIEW_FUNZ_GUI = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_FUNZIONE = ? AND NOME_GUI = ?";
	
	public static final String GET_GUI_VIEW_FUNZ_SUBFUNZ_GUI = " SELECT * FROM " + VIEW_GUI + " WHERE NOME_FUNZIONE = ? AND NOME_SUBFUNZIONE = ? AND NOME_GUI = ?";
	
	public static final String GET_IDUTENTE = "SELECT ID_UTENTE FROM " + TABLE_UTENTI + " WHERE USERNAME = ? "
												+ " AND " + TABLE_UTENTI  + ".TRASHED_DELETE IS NULL  AND " + TABLE_UTENTI  + ".ENABLED = TRUE "; ;
	
	public static final String GET_RUOLO_BY_NOME="SELECT * FROM PERMESSI_RUOLI WHERE NOME=?";
	
	public static final String GET_RUOLO_BY_ID="SELECT * FROM PERMESSI_RUOLI WHERE ID=?";
	
	public static final String INSERT_RUOLO="INSERT INTO PERMESSI_RUOLI(NOME,DESCRIZIONE) VALUES (?,?)";
	
	public static final String GET_SUBFUNZIONI="SELECT * FROM PERMESSI_SUBFUNZIONE";
	public static final String GET_FUNZIONI="SELECT * FROM PERMESSI_FUNZIONE ORDER BY NOME";
	public static final String GET_GUI="SELECT * FROM PERMESSI_GUI";
	public static final String GET_RUOLI = " SELECT * FROM PERMESSI_RUOLI ORDER BY NOME ";
	
	public static final String GET_RUOLI_BY_ENDPOINT ="select distinct r.ruolo_Integer, r.ruolo_String " +
	"from guc_ruoli r " +
	"where r.endpoint = ? and r.ruolo_Integer > 0 and r.ruolo_String != '' " +
	"order by r.ruolo_String" ;
	
	public static final String GET_UTENTI_FILTERED = "SELECT * FROM "  + TABLE_UTENTI + 
													" WHERE UPPER( NOME ) LIKE UPPER( ? ) " +
													" AND UPPER ( COGNOME ) LIKE UPPER( ? ) " + 
													" ORDER BY UPPER( COGNOME ), UPPER( NOME ), UPPER( USERNAME )";
	
	public static final String DELETE_RUOLO = "DELETE FROM PERMESSI_RUOLI WHERE NOME = ? ";

	public static final String GET_FUNZIONE = "SELECT * FROM " + TABLE_FUNZIONE + " WHERE NOME = ?";
	
	public static final String GET_SUBFUNZIONE = "SELECT * FROM " + TABLE_SUBFUNZIONE + " WHERE ID_FUNZIONE = ? AND NOME = ?";
	

	public static final String INSERT_NEW_FUNZIONE =
			"INSERT INTO " +
				"PERMESSI_FUNZIONE( ID_FUNZIONE, NOME ) " +
				"VALUES( NEXTVAL('" + SEQ_FUNZIONE + "'), ? )";
	
	public static final String INSERT_NEW_SUBFUNZIONE = 
		"INSERT INTO " +
			"PERMESSI_SUBFUNZIONE( ID_SUBFUNZIONE, ID_FUNZIONE, NOME ) " +
			"VALUES( NEXTVAL('" + SEQ_SUBFUNZIONE + "'), ?, ? )";
	
	public static final String INSERT_NEW_GUIOBJECT =
		"INSERT INTO " +
				"PERMESSI_GUI( ID_GUI, ID_SUBFUNZIONE, NOME ) " +
				"VALUES( NEXTVAL('" + SEQ_GUI + "'), ?, ? )";
	
	public static final String USERNAME_EXISTS = "SELECT * FROM " + 
	TABLE_UTENTI + " WHERE USERNAME = ? ";
	//+ " AND " + TABLE_UTENTI  + ".DATE_DELETE IS NULL " ;
	
	
	public static final String PASSWORD_CHECK =   " SELECT * FROM " + TABLE_UTENTI 
	+ " WHERE " + TABLE_UTENTI  + ".USERNAME = ? AND trashed_date IS NULL  AND " + TABLE_UTENTI  + ".ENABLED = TRUE AND (" + TABLE_UTENTI  + ".DATA_SCADENZA > CURRENT_DATE OR " + TABLE_UTENTI  + ".DATA_SCADENZA IS NULL ) and trashed_date is null";

	public static final String CF_CHECK =   " SELECT * FROM " + TABLE_UTENTI 
			+ " WHERE " + TABLE_UTENTI  + ".codice_fiscale ilike  ? AND trashed_date IS NULL  AND " + TABLE_UTENTI  + ".ENABLED = TRUE AND (" + TABLE_UTENTI  + ".DATA_SCADENZA > CURRENT_DATE OR " + TABLE_UTENTI  + ".DATA_SCADENZA IS NULL ) and trashed_date is null";

	
	public static final String PASSWORD_CHECK_ACCESSO_UNICO =   " SELECT * FROM " + TABLE_GUC_UTENTI 
	+ " WHERE " + TABLE_GUC_UTENTI  + ".USERNAME = ? AND " + TABLE_GUC_UTENTI  + ".ENABLED = TRUE";
	
	public static final String GET_ASL="SELECT * FROM ASL ";
	
	public static final String GET_COMUNI="SELECT * FROM comuni ";
	
	public static final String GET_CLINICHE="SELECT * FROM guc_cliniche_vam where id_utente = ? order by id";
	
	public static final String GET_GESTORE="SELECT * FROM public_functions.dbi_get_gestori_acque(?) order by id";
	
	public static final String GET_COMUNE="SELECT * FROM public_functions.dbi_get_comuni(?) order by id";
	
	public static final String GET_STRUTTURE="SELECT * FROM guc_strutture_gisa where id_utente = ? order by id";
	
	public static final String GET_CANILI="SELECT * FROM guc_canili_bdu where id_utente = ? order by id";
	
	public static final String GET_IMPORTATORI="SELECT * FROM guc_importatori where id_utente = ? order by id";
	
	public static final String GET_EXTOPT="SELECT key,val FROM extended_option where user_id = ? and endpoint=?" ;

	public static final String PASSWORD_CHECK_ACCESSO_ADMIN_SPID =   "select * from utenti gu where enabled = true and codice_fiscale in (select codice_fiscale from guc_cf_white_list where codice_fiscale = ? and password = md5(?));";

}
