/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class SedutaUnivoca extends GenericBean {

	public static final int idStatoDaMacellare = 1;
	public static final int idStatoMacellazioneInCorso = 2;
	public static final int idStatoMacellato = 3;

	private int id = -1;
	private int idUtenteInserimento;
	private int idMacello = -1;
	private Timestamp data = null;
	private int numeroSeduta = 1;
	private ArrayList<CapoUnivoco> listaCapi = new ArrayList<CapoUnivoco>();
	private HashMap<Integer, Integer> listaCapiNumeri = new HashMap<Integer, Integer>();
	private int idStato = -1;
	private int numeroCapiTotale = 0;
	private int numeroCapiMacellati = 0;
	
	private int giorniAperturaSeduta = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}

	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}

	public int getIdMacello() {
		return idMacello;
	}

	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}

	public void setIdMacello(String idMacello) {
		if (idMacello != null && !idMacello.equals("null") && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public void setData(String data) {
		this.data = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public ArrayList<CapoUnivoco> getListaCapi() {
		return listaCapi;
	}

	public void setListaCapi(ArrayList<CapoUnivoco> listaCapi) {
		this.listaCapi = listaCapi;
	}

	public HashMap<Integer, Integer> getListaCapiNumeri() {
		return listaCapiNumeri;
	}

	public void setListaCapiNumeri(HashMap<Integer, Integer> listaCapiNumeri) {
		this.listaCapiNumeri = listaCapiNumeri;
	}

	public int getNumeroSeduta() {
		return numeroSeduta;
	}

	public void setNumeroSeduta(int numeroSeduta) {
		this.numeroSeduta = numeroSeduta;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public void setIdStato(String idStato) {
		if (idStato != null && !("").equals(idStato))
			this.idStato = Integer.parseInt(idStato);
	}

	public int getNumeroCapiTotale() {
		return numeroCapiTotale;
	}

	public void setNumeroCapiTotale(int numeroCapiTotale) {
		this.numeroCapiTotale = numeroCapiTotale;
	}

	public int getNumeroCapiMacellati() {
		return numeroCapiMacellati;
	}

	public void setNumeroCapiMacellati(int numeroCapiMacellati) {
		this.numeroCapiMacellati = numeroCapiMacellati;
	}
	
	
	

	public int getGiorniAperturaSeduta() {
		return giorniAperturaSeduta;
	}

	public void setGiorniAperturaSeduta(int giorniAperturaSeduta) {
		this.giorniAperturaSeduta = giorniAperturaSeduta;
	}

	public SedutaUnivoca() {

	}

	public SedutaUnivoca(ActionContext context) {
		setIdMacello(context.getRequest().getParameter("idMacello"));
		setData(context.getRequest().getParameter("dataSeduta"));

	}

	public SedutaUnivoca(ResultSet rs) throws SQLException {
		setIdMacello(rs.getInt("id_macello"));
		setData(rs.getTimestamp("data"));
		setId(rs.getInt("id"));
		setIdStato(rs.getInt("stato"));
		setNumeroSeduta(rs.getInt("numero"));
		calcolaGiorniApertura();


	}

	public boolean insert(Connection db) throws SQLException {

		int nextSeq = -1;
		String nexCode = "select nextval('mu_sedute_id_seq')";
		try {
			PreparedStatement pst = db.prepareStatement(nexCode);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				nextSeq = rs.getInt(1);
			}
			if (nextSeq > -1)
				setId(nextSeq);
		} catch (SQLException e) {
			throw e;
		}

		int numSeduta = 1;
		String nexNumero = "select max(numero) from mu_sedute where id_macello = ? and data = ?";
		try {
			PreparedStatement pst = db.prepareStatement(nexNumero);
			pst.setInt(1, idMacello);
			pst.setTimestamp(2, data);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				numSeduta = rs.getInt(1);
			}
			setNumeroSeduta(numSeduta + 1);
		} catch (SQLException e) {
			throw e;
		}

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO mu_sedute (entered, id ");

		if (idUtenteInserimento > 0)
			sql.append(", enteredby");
		if (idMacello > 0)
			sql.append(", id_macello");
		if (data != null)
			sql.append(", data");
		if (numeroSeduta > 0)
			sql.append(", numero");
		if (idStato > 0)
			sql.append(", stato");
		if (numeroCapiTotale > 0)
			sql.append(", numero_capi_totale");
		if (numeroCapiMacellati > 0)
			sql.append(", numero_capi_macellati");

		sql.append(")");

		sql.append(" VALUES (now(), ? ");

		if (idUtenteInserimento > 0)
			sql.append(",?");
		if (idMacello > 0)
			sql.append(", ?");
		if (data != null)
			sql.append(", ?");
		if (numeroSeduta > 0)
			sql.append(", ?");
		if (idStato > 0)
			sql.append(", ?");
		if (numeroCapiTotale > 0)
			sql.append(", numero_capi_totale");
		if (numeroCapiMacellati > 0)
			sql.append(", numero_capi_macellati");

		sql.append(")");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		pst.setInt(++i, id);

		if (idUtenteInserimento > 0)
			pst.setInt(++i, idUtenteInserimento);
		if (idMacello > 0)
			pst.setInt(++i, idMacello);
		if (data != null)
			pst.setTimestamp(++i, data);
		if (numeroSeduta > 0)
			pst.setInt(++i, numeroSeduta);
		if (idStato > 0)
			pst.setInt(++i, idStato);
		if (numeroCapiTotale > 0)
			pst.setInt(++i, numeroCapiTotale);
		if (numeroCapiMacellati > 0)
			pst.setInt(++i, numeroCapiMacellati);

		if (id > 0)
			pst.execute();

		pst.close();
		return true;
	}

	public SedutaUnivoca(Connection db, int idSeduta) {

		String sql = "select * from mu_sedute where id = ?";

		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setInt(1, idSeduta);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRecord(rs, db);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildRecord(ResultSet rs, Connection db) throws SQLException {
		setIdMacello(rs.getInt("id_macello"));
		setData(rs.getTimestamp("data"));
		setId(rs.getInt("id"));
		setNumeroSeduta(rs.getInt("numero"));
		setIdStato(rs.getInt("stato"));
		setNumeroCapiMacellati(rs.getInt("numero_capi_macellati"));
		setNumeroCapiTotale(rs.getInt("numero_capi_totale"));
		// settaListaCapiNumeri(db);
		settaListaCapi(db);
		calcolaGiorniApertura();

	}

	private void settaListaCapi(Connection db) {
		String sql = "select c.*, p.numero from mu_capi c left join mu_partite p on c.id_partita = p.id where c.id_seduta = ? order by specie asc";

		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CapoUnivoco capo = new CapoUnivoco(rs);
				capo.setNumeroPartita(rs.getString("numero"));
				capo.setSpecieCapoNome();
				listaCapi.add(capo);

				int num = 0;
				try {
					num = listaCapiNumeri.get(capo.getSpecieCapo());
				} catch (Exception e) {
				}
				num++;
				listaCapiNumeri.put(capo.getSpecieCapo(), num);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private void settaListaCapiNumeri(Connection db){
	// String sql =
	// "select specie.description as specie, count(id) as numero from mu_capi c left join lookup_specie_mu specie on specie.code = c.specie where c.id_seduta = ? group by specie.description order by specie";
	//
	// PreparedStatement pst;
	// try {
	// pst = db.prepareStatement(sql);
	//
	// pst.setInt(1, id);
	//
	// ResultSet rs = pst.executeQuery();
	// while (rs.next())
	// {
	// String specie = rs.getString("specie");
	// int numero = rs.getInt("numero");
	// listaCapiNumeri.put(specie, numero);
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public Vector ricercaSeduta(Connection db) {
		ResultSet rs = null;
		Vector seduteList = new Vector();
		PreparedStatement pst;
		try {

			String query = "select * from mu_sedute where id>0 ";
			// query = query.substring(0, query.indexOf("order by"));

			if (data != null)
				query += " and data = ? ";

			if (idMacello > 0)
				query += " and id_macello =  ? ";

			query += " order by data DESC, numero DESC ";

			pst = db.prepareStatement(query);
			int i = 0;

			if (data != null)
				pst.setTimestamp(++i, data);

			if (idMacello > 0)
				pst.setInt(++i, idMacello);

			rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				SedutaUnivoca seduta = new SedutaUnivoca(rs);
				seduta.settaListaCapi(db);
				seduteList.add(seduta);
			}
			rs.close();
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seduteList;
	}

	public void updateNumeroCapi(Connection db) {
		String queryToUpdate = "";
		try {
			queryToUpdate = "update mu_sedute set numero_capi_totale = ? where id = ? ";

			PreparedStatement pst = db.prepareStatement(queryToUpdate);
			pst.setInt(1, numeroCapiTotale);
			pst.setInt(2, id);

			pst.execute();
		} catch (Exception e) {

		} finally {

		}
	}

	public void updateNumeroCapiMacellati(Connection db) {
		String queryToUpdate = "";
		try {
			queryToUpdate = "update mu_sedute set numero_capi_macellati = ?, stato = ? where id = ? ";

			PreparedStatement pst = db.prepareStatement(queryToUpdate);
			pst.setInt(1, numeroCapiMacellati);
			pst.setInt(2, idStato);
			pst.setInt(3, id);

			pst.execute();
		} catch (Exception e) {

		} finally {

		}
	}
	
	public String giorniFraDueDate( GregorianCalendar dallaDataGC, GregorianCalendar allaDataGC ) {

		// conversione in millisecondi
		long dallaDataMilliSecondi = dallaDataGC.getTimeInMillis();
		long allaDataMilliSecondi = allaDataGC.getTimeInMillis();

		long millisecondiFraDueDate = allaDataMilliSecondi - dallaDataMilliSecondi;

		// conversione in giorni con la divisione intera
		double giorniFraDueDate_DivInt = millisecondiFraDueDate / 86400000;
		
		// conversione in giorni con la divisione reale
		double giorniFraDueDate_DivReal = millisecondiFraDueDate / 86400000.0;

		// conversione in giorni con arrotondamento della divisione reale
		double giorniFraDueDate_DivRealRound = Math.round( millisecondiFraDueDate / 86400000.0 );

		return giorniFraDueDate_DivInt + "\t" + 
				giorniFraDueDate_DivReal + "\t" + 
				giorniFraDueDate_DivRealRound;
	}
	
	
	private void calcolaGiorniApertura(){
		GregorianCalendar cal=(GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(this.getData());
		GregorianCalendar c=new GregorianCalendar() ;
		giorniAperturaSeduta=  DateUtils.getDaysBetween(cal, c );
	}

}
