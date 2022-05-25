/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class GestoreComunicazioniGisa {

	public GestoreComunicazioniGisa() {

	}
	
	/*****
	 * FUNZIONE PER L'INSERIMENTO IN GISA DELL'OPERATORE COMMERCIALE (E IMPORTATORE) INSERITO IN BDU
	 *  
	 * Input lo stabilimento inserito
	 *  Si collega al DB gisa e attraverso la funzione DBI inserisce 
	 *  l'operatore commerciale o l'importatore nel sistema GISA
	 *  Nel file build.properties (configurazione di centric) settare i seguenti parametri:
	 *  GISADB.HOST
		GISADB.NAME
		GISADB.USERNAME
		GISADB.PWD
	 * 
	 ******/

	public void inserisciOperatoreCommercialeInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		try {
			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
//		
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
		
			inserted
					.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);
		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from inserisci_operatore_commerciale(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?,?::timestamp , ?, ?::timestamp, ?, crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision))";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lpInserita.getId());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setString(++i, inserted.getRagioneSociale());
		pst.setTimestamp(++i, inserted.getEntered());
		pst.setTimestamp(++i, inserted.getModified());
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setString(++i, rappLegale.getComuneNascita());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10
		pst.setString(++i, inserted.getPartitaIva());

		// Data datachiusuracommerciale
		pst.setTimestamp(++i, null);

		// Autorizzazione
		pst.setString(++i, " -- ");

		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue((sedeLegale.getProvincia()))); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long

		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
	//	printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());

		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
				
		}
	}
	
	



	
	/*****
	 * FUNZIONE PER L'INSERIMENTO IN GISA DEI CANILI INSERITI IN BDU
	 *  
	 * Input lo stabilimento inserito
	 *  Si collega al DB gisa e attraverso la funzione DBI inserisce 
	 *  il canile nel sistema GISA
	 *  Nel file build.properties (configurazione di centric) settare i seguenti parametri:
	 *  GISADB.HOST
		GISADB.NAME
		GISADB.USERNAME
		GISADB.PWD
	 * 
	 ******/
	
	public void inserisciCanileInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		CanileInformazioni infoCanile = (CanileInformazioni)lpInserita;
		try {
			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
//		
		
			inserted
					.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);

		connectionGisa = GestoreConnessioni.getConnectionGisa();
		String insert = "select * from inserisci_operatore_canile(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?,?::timestamp , ?, ?::timestamp, ?, ?::timestamp, ?, ?, ?,  crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), ?, ?)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lpInserita.getId());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setString(++i, inserted.getRagioneSociale());
		pst.setTimestamp(++i, inserted.getEntered());
		pst.setTimestamp(++i, inserted.getModified());
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setString(++i, rappLegale.getComuneNascita());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10
		
		//Documento identita (testo)
		pst.setString(++i, (rappLegale.getDocumentoIdentita() != null) ? rappLegale.getDocumentoIdentita() : "" );
		
		//Data ricezione autorizzazione (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataInizio());
		//Partita iva (Testo)
		pst.setString(++i, inserted.getPartitaIva());
		//Data chiusura canile  (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataFine());
		//Abusivo (Booleano)
		pst.setBoolean(++i, infoCanile.isAbusivo()); //parametro da aggiungere
		//Centro sterilizzazione (Booleano)
		pst.setBoolean(++i, infoCanile.isCentroSterilizzazione()); //da inserire
		//Autorizzazione (Testo)
		pst.setString(++i,infoCanile.getAutorizzazione()); //Da inserire
		


		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

		//Sede legale (Indirizzo)
		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeLegale.getProvincia())); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long

		
		//Sede operativa  (Indirizzo)
		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i,  province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
		//Num telefono legale (Testo)
		pst.setString(++i, rappLegale.getTelefono1()); //Collegato al soggetto, va bene??
		
		//Num telefono operativo (Testo)
		pst.setString(++i, rappLegale.getTelefono2()); //Collegato al soggetto, va bene??
		if (System.getProperty("DEBUG") != null) 
			System.out.println(pst.toString());
		
	//	printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());
		
		pst.execute();

	//	connectionGisa.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
				
		}
	}
	
	

	/*****
	 * FUNZIONE PER L'INSERIMENTO IN GISA DELLE COLONIE INSERITE IN BDU
	 *  
	 * Input lo stabilimento inserito
	 *  Si collega al DB gisa e attraverso la funzione DBI inserisce 
	 *  la colonia nel sistema GISA
	 *  Nel file build.properties (configurazione di centric) settare i seguenti parametri:
	 *  GISADB.HOST
		GISADB.NAME
		GISADB.USERNAME
		GISADB.PWD
	 * 
	 ******/
	
	public void inserisciColoniaInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		ColoniaInformazioni infoColonia = (ColoniaInformazioni)lpInserita;
		try {
//			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
//		
		
			inserted
					.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);
		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from inserisci_operatore_colonia(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?, ?::timestamp, ?, ?, crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), ?, ?)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lpInserita.getId());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setString(++i, infoColonia.getNrProtocollo());
		pst.setTimestamp(++i, inserted.getEntered());
		pst.setTimestamp(++i, inserted.getModified());
		
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setString(++i, rappLegale.getComuneNascita());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10		
		//Documento identita (testo)
		pst.setString(++i, rappLegale.getDocumentoIdentita());
		//Partita iva (Testo)
		pst.setString(++i, inserted.getPartitaIva());
		
		/*//Data ricezione autorizzazione (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataInizio());

		//Data chiusura canile  (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataFine());
		//Abusivo (Booleano)
		pst.setBoolean(++i, infoCanile.isAbusivo()); //parametro da aggiungere
		//Centro sterilizzazione (Booleano)
		pst.setBoolean(++i, infoCanile.isCentroSterilizzazione()); //da inserire
		//Autorizzazione (Testo)
		pst.setString(++i,infoCanile.getAutorizzazione()); //Da inserire
*/		


		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

/*		//Sede legale (Indirizzo)
		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeLegale.getProvincia())); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long
*/
		
		//Sede operativa  (Indirizzo)
		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i,  province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
		//Num telefono legale (Testo)
		pst.setString(++i, rappLegale.getTelefono1()); //Collegato al soggetto, va bene??
		
		//Num telefono operativo (Testo)
		pst.setString(++i, rappLegale.getTelefono2()); //Collegato al soggetto, va bene??
		if (System.getProperty("DEBUG") != null) 
			System.out.println(pst.toString());
		
	//	printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());
		
		
		pst.execute();

	//	connectionGisa.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
				
		}
	}
	
	
	
	public void aggiornaCanileInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		CanileInformazioni infoCanile = (CanileInformazioni)lpInserita;
		try {
			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
		
		
		
		inserted.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);
		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		//String insert = "select * from inserisci_operatore_canile(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?,?::timestamp , ?, ?::timestamp, ?, ?::timestamp, ?, ?, ?,  crea_indirizzo(?,?,?,?,?,?), crea_indirizzo(?,?,?,?,?,?), ?, ?)";
		
		String update = "select * from aggiorna_operatore_canile(?, ?, ?, ?::timestamp, ?, ?, ?, ?::timestamp, ?,?,?,?,?::timestamp, ?::timestamp, ?::boolean, ?::boolean, ?, ?, crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), ?::timestamp)";

		int i = 0;
		pst = connectionGisa.prepareStatement(update);
		pst.setInt(++i, lpInserita.getId());
		pst.setString(++i, inserted.getRagioneSociale());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setTimestamp(++i, inserted.getModified());
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10
	
		//Partita iva (Testo)
		pst.setString(++i, inserted.getPartitaIva());
		
		pst.setString(++i, rappLegale.getComuneNascita());
		
		
		pst.setString(++i, rappLegale.getDocumentoIdentita());
		
		pst.setString(++i, infoCanile.getAutorizzazione()); //AUTORIZZAZIONE???
		pst.setTimestamp(++i, null); //DATA CANCELLAZIONE???
		pst.setTimestamp(++i, infoCanile.getDataAutorizzazione() ); //DATA AUTORIZZAZIONE???
		
	
		//Abusivo (Booleano)
		pst.setBoolean(++i,  infoCanile.isAbusivo()); //ABUSIVO???
		pst.setBoolean(++i,  infoCanile.isCentroSterilizzazione()); //CENTRO STERILIZZAZIONE ???
		
		pst.setString(++i, rappLegale.getTelefono1()); //TEL LEGALE ???
		pst.setString(++i, rappLegale.getTelefono2());  //TEL OPERATIVO ???


		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

		//Sede legale (Indirizzo)
		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeLegale.getProvincia())); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long
	
		
		//Sede operativa  (Indirizzo)
		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i,  province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
		pst.setTimestamp(++i, lpInserita.getDataFine()); // DATA CHIUSURA???
		if (System.getProperty("DEBUG") != null) 
			System.out.println(pst.toString());
		
//		printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());
		
		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		//	DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);	
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	/*****
	 * FUNZIONE PER L?AGGIORNAMENTO IN GISA DELLE COLONIE MODIFICATE IN BDU
	 *  
	 * Input lo stabilimento inserito
	 *  Si collega al DB gisa e attraverso la funzione DBI aggiorna 
	 *  la colonia nel sistema GISA
	 *  Nel file build.properties (configurazione di centric) settare i seguenti parametri:
	 *  GISADB.HOST
		GISADB.NAME
		GISADB.USERNAME
		GISADB.PWD
	 * 
	 ******/
	
	public void aggiornaColoniaInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		ColoniaInformazioni infoColonia = (ColoniaInformazioni)lpInserita;
		try {
			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
		
		
			inserted
					.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		//SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);
		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from aggiorna_operatore_colonia(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?, ?::timestamp, ?, ?, crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), ?, ?)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lpInserita.getId());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setString(++i, infoColonia.getNrProtocollo());
		pst.setTimestamp(++i, inserted.getEntered());
		pst.setTimestamp(++i, inserted.getModified());
		
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setString(++i, rappLegale.getComuneNascita());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10		
		//Documento identita (testo)
		pst.setString(++i, rappLegale.getDocumentoIdentita());
		//Partita iva (Testo)
		pst.setString(++i, inserted.getPartitaIva());
		
		/*//Data ricezione autorizzazione (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataInizio());

		//Data chiusura canile  (timestamp)
		pst.setTimestamp(++i, lpInserita.getDataFine());
		//Abusivo (Booleano)
		pst.setBoolean(++i, infoCanile.isAbusivo()); //parametro da aggiungere
		//Centro sterilizzazione (Booleano)
		pst.setBoolean(++i, infoCanile.isCentroSterilizzazione()); //da inserire
		//Autorizzazione (Testo)
		pst.setString(++i,infoCanile.getAutorizzazione()); //Da inserire
*/		


		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

/*		//Sede legale (Indirizzo)
		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeLegale.getProvincia())); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long
*/
		
		//Sede operativa  (Indirizzo)
		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i,  province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
		//Num telefono legale (Testo)
		pst.setString(++i, rappLegale.getTelefono1()); //Collegato al soggetto, va bene??
		
		//Num telefono operativo (Testo)
		pst.setString(++i, rappLegale.getTelefono2()); //Collegato al soggetto, va bene??
		if (System.getProperty("DEBUG") != null) 
			System.out.println(pst.toString());
		
//		printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());
		
		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);			
			
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	public void aggiornaOperatoreCommercialeInGisa(Connection db, Stabilimento stabilimentoInserito, ApplicationPrefs prefs) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		Operatore inserted = new Operatore();
		LineaProduttiva lpInserita =	((LineaProduttiva)stabilimentoInserito.getListaLineeProduttive().get(0));
		try {
			
//		String dbName = prefs.get("GISADB.NAME");
//		String username = prefs.get("GISADB.USERNAME");
//		String pwd = prefs.get("GISADB.PWD");
//		String host = prefs.get("GISADB.HOST");
//		
//		String SERVER_GISA = InetAddress.getByName(host)
//		.getHostAddress();
		
			inserted
					.queryRecordOperatorebyIdLineaProduttiva(db, lpInserita.getId());
		SoggettoFisico rappLegale = inserted.getRappLegale();
		SoggettoFisico rappOperativo = stabilimentoInserito.getRappLegale();
//		connectionGisa = DbUtil.getConnection(dbName, username,
//				pwd, SERVER_GISA);
		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from aggiorna_operatore_commerciale(?, ?, ?, ?::timestamp, ?::timestamp, ?, ?, ?, ?,?::timestamp , ?, ?::timestamp, ?, crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision), crea_indirizzo(?::text,?::text,?::text,?::text,?::double precision,?::double precision))";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lpInserita.getId());
		pst.setInt(++i, stabilimentoInserito.getIdAsl());
		pst.setString(++i, inserted.getRagioneSociale());
		pst.setTimestamp(++i, inserted.getEntered());
		pst.setTimestamp(++i, inserted.getModified());
		pst.setString(++i, rappLegale.getNome());
		pst.setString(++i, rappLegale.getCognome());
		pst.setString(++i, rappLegale.getCodFiscale());
		pst.setString(++i, rappLegale.getComuneNascita());
		pst.setTimestamp(++i, rappLegale.getDataNascita()); //10
		pst.setString(++i, inserted.getPartitaIva());

		// Data datachiusuracommerciale
		pst.setTimestamp(++i, null);

		// Autorizzazione
		pst.setString(++i, " -- ");

		ComuniAnagrafica c = new ComuniAnagrafica();
		// Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, -1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		
		LookupList province = new LookupList(db, "lookup_province");

		Indirizzo sedeLegale = inserted.getSedeLegale();

		pst.setString(++i, comuniList.getSelectedValue(sedeLegale.getComune())); // comune
		pst.setString(++i, sedeLegale.getVia()); // via
		pst.setString(++i, sedeLegale.getCap()); // cap
		pst.setString(++i, province.getSelectedValue((sedeLegale.getProvincia()))); // provincia
		pst.setDouble(++i, sedeLegale.getLatitudine()); // lat
		pst.setDouble(++i, sedeLegale.getLongitudine()); // long

		Indirizzo sedeOperativa = stabilimentoInserito.getSedeOperativa();

		pst.setString(++i, comuniList.getSelectedValue(sedeOperativa
				.getComune())); // comune
		pst.setString(++i, sedeOperativa.getVia()); // via
		pst.setString(++i, sedeOperativa.getCap()); // cap
		pst.setString(++i, province.getSelectedValue(sedeOperativa.getProvincia())); // provincia
		pst.setDouble(++i, sedeOperativa.getLatitudine()); // lat
		pst.setDouble(++i, sedeOperativa.getLongitudine()); // long
		
//		printInformazioniConnessioneGisa(dbName, host, username, pwd, pst.toString());

		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	
	public void chiudiOperatoreCommercialeInGisa(Connection db, LineaProduttiva lp) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		
		try {
			

		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from chiudi_operatore_commerciale(?, ?::timestamp)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lp.getId());
		pst.setTimestamp(++i, lp.getDataFine());


		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	public void apriOperatoreCommercialeInGisa(Connection db, LineaProduttiva lp) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;

		try {
			

		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from apri_operatore_commerciale(?)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lp.getId());
	//	pst.setTimestamp(++i, lpInserita.getDataFine());


		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	
	public void chiudiCanileInGisa(Connection db, LineaProduttiva lp) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		
		try {
			

		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from chiudi_canile(?, ?::timestamp)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lp.getId());
		pst.setTimestamp(++i, lp.getDataFine());


		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	public void apriCanileleInGisa(Connection db, LineaProduttiva lp) {
		
		Connection connectionGisa = null;
		PreparedStatement pst = null;
		
		try {
			

		
		connectionGisa = GestoreConnessioni.getConnectionGisa();

		String insert = "select * from apri_canile(?)";

		int i = 0;
		pst = connectionGisa.prepareStatement(insert);
		pst.setInt(++i, lp.getId());
	//	pst.setTimestamp(++i, lpInserita.getDataFine());


		pst.execute();

		//connectionGisa.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			
			//DbUtil.chiudiConnessioneJDBC(null, pst, connectionGisa);
			GestoreConnessioni.freeConnectionGisa(connectionGisa);
		}
	}
	
	
	
	
	
	
	private static void printInformazioniConnessioneGisa(String dbname, String host, String user, String pwd, String query){
		
		System.out.println("Aggiornamento GISA --- DBHOST: " +host + " DBNAME: " + dbname + " UserDB: " + user + " PWD: " + pwd);
		System.out.println("Aggiornamento GISA --- QUERY ESECUZIONE: " +query);
		
	}
}
