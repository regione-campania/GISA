/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionidocumenti.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.aspcfs.modules.macellazioni.base.Art17;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioninew.base.Partita;
import org.aspcfs.modules.macellazioninew.base.PartitaSeduta;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninew.utils.ReflectionUtil;
import org.aspcfs.utils.DateUtils;
import org.postgresql.util.PSQLException;
import org.aspcfs.modules.macellazioninew.base.GenericBean;

public class Articolo17  extends GenericBean {
	
	//private String tipo = null; 
	private int idMacello = -1; 
	private int esercente = -999; 
	private Timestamp data = null;
	private String nomeEsercente = "";
	private String proprietario = "";
	private int anno=-1;
	private int idSeduta = -1;
	private int idPartita = -1;
	private String cdPartita="";
	private int progressivoMacellazione = -1;
	private LinkedHashMap<String, String[]> listaElementi = new LinkedHashMap<String, String[]>();
	private int tipoComboMacelli =-1;
//	private ArrayList<Capo> listaCapi = null;
//	private ArrayList<Partita> listaPartite = null;
	private ArrayList<GenericBean> listaGB = new  ArrayList<GenericBean> ();
	private int idUtente = -1;
	private ConfigTipo configTipo = null;
	
	
	
	public String getCdPartita() {
		return cdPartita;
	}

	public void setCdPartita(String cdPartita) {
		this.cdPartita = cdPartita;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeEsercente() {
		return nomeEsercente;
	}
	
	public void setNomeEsercente(String nomeEsercente) {
		if (nomeEsercente!=null){
			nomeEsercente = nomeEsercente.replace("u38", "&");
			this.nomeEsercente = nomeEsercente;
		}
	}
//	public ArrayList<Capo> getListaCapi() {
//		return listaCapi;
//	}
//	public void setListaCapi(ArrayList<Capo> listaCapi) {
//		this.listaCapi = listaCapi;
//	}
//	public String getTipo() {
//		return tipo;
//	}
//	public void setTipo(String tipo) {
//		this.tipo = tipo;
//	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public void setIdMacello(String idMacello) {
		if (idMacello!=null && !idMacello.equals("null") && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}
	
	public int getIdSeduta() {
		return idSeduta;
	}
	public void setIdSeduta(int idSeduta) {
		this.idSeduta = idSeduta;
	}
	public void setIdSeduta(String idSeduta) {
		if (idSeduta!=null && !idSeduta.equals("null") && !idSeduta.equals(""))
			this.idSeduta = Integer.parseInt(idSeduta);
	}
	
	public int getIdPartita() {
		return idPartita;
	}
	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}
	public void setIdPartita(String idPartita) {
		if (idPartita!=null && !idPartita.equals("null") && !idPartita.equals(""))
			this.idPartita = Integer.parseInt(idPartita);
	}
	
	
	public int getEsercente() {
		return esercente;
	}
	public void setEsercente(int esercente) {
		this.esercente = esercente;
	}
	public void setEsercente(String esercente) {
		if (esercente!=null && !esercente.equals("null") && !esercente.equals(""))
			this.esercente = Integer.parseInt(esercente);
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
	
	
	public LinkedHashMap<String, String[]> getListaElementi() {
		return listaElementi;
	}

	
	public void popolaDaModulo(Connection db, ConfigTipo configTipo) throws Exception{
		this.configTipo = configTipo;
		tipoComboMacelli = configTipo.getIdTipo();
		PreparedStatement stat = null;
		String select = "";
		String suffissoVista = "";
		suffissoVista = configTipo.getSuffissoTabelle();
		
		
		
		try {
		if (idSeduta>0) 
			suffissoVista+="_sedute";
		
		select = "SELECT * ";
		
		if(tipoComboMacelli==2)
		{
			if (configTipo.hasDestinatarioCarni())
			{
		select += " , CASE  WHEN destinatario_1_id = ? THEN destinatario_1_num_capi_ovini "
				+ "               WHEN destinatario_2_id = ? THEN destinatario_2_num_capi_ovini "
				+ "               WHEN destinatario_3_id = ? THEN destinatario_3_num_capi_ovini "
				+ "               WHEN destinatario_4_id = ? THEN destinatario_4_num_capi_ovini "
				+ "               WHEN destinatario_5_id = ? THEN destinatario_5_num_capi_ovini "
				+ "               WHEN destinatario_6_id = ? THEN destinatario_6_num_capi_ovini "
				+ "               WHEN destinatario_7_id = ? THEN destinatario_7_num_capi_ovini "
				+ "               WHEN destinatario_8_id = ? THEN destinatario_8_num_capi_ovini "
				+ "               WHEN destinatario_9_id = ? THEN destinatario_9_num_capi_ovini "
				+ "               WHEN destinatario_10_id = ? THEN destinatario_10_num_capi_ovini "
				+ "               WHEN destinatario_11_id = ? THEN destinatario_11_num_capi_ovini "
				+ "               WHEN destinatario_12_id = ? THEN destinatario_12_num_capi_ovini "
				+ "               WHEN destinatario_13_id = ? THEN destinatario_13_num_capi_ovini "
				+ "               WHEN destinatario_14_id = ? THEN destinatario_14_num_capi_ovini "
				+ "               WHEN destinatario_15_id = ? THEN destinatario_15_num_capi_ovini "
				+ "               WHEN destinatario_16_id = ? THEN destinatario_16_num_capi_ovini "
				+ "               WHEN destinatario_17_id = ? THEN destinatario_17_num_capi_ovini "
				+ "               WHEN destinatario_18_id = ? THEN destinatario_18_num_capi_ovini "
				+ "               WHEN destinatario_19_id = ? THEN destinatario_19_num_capi_ovini "
				+ "               WHEN destinatario_20_id = ? THEN destinatario_20_num_capi_ovini "
				+ "			END as num_capi_ovini_macellati,"
				+ "                 CASE  WHEN destinatario_1_id = ? THEN destinatario_1_num_capi_caprini "
						+ "               WHEN destinatario_2_id = ? THEN destinatario_2_num_capi_caprini "
						+ "               WHEN destinatario_3_id = ? THEN destinatario_3_num_capi_caprini "
						+ "               WHEN destinatario_4_id = ? THEN destinatario_4_num_capi_caprini "
						+ "               WHEN destinatario_5_id = ? THEN destinatario_5_num_capi_caprini "
						+ "               WHEN destinatario_6_id = ? THEN destinatario_6_num_capi_caprini "
						+ "               WHEN destinatario_7_id = ? THEN destinatario_7_num_capi_caprini "
						+ "               WHEN destinatario_8_id = ? THEN destinatario_8_num_capi_caprini "
						+ "               WHEN destinatario_9_id = ? THEN destinatario_9_num_capi_caprini "
						+ "               WHEN destinatario_10_id = ? THEN destinatario_10_num_capi_caprini "
						+ "               WHEN destinatario_11_id = ? THEN destinatario_11_num_capi_caprini "
						+ "               WHEN destinatario_12_id = ? THEN destinatario_12_num_capi_caprini "
						+ "               WHEN destinatario_13_id = ? THEN destinatario_13_num_capi_caprini "
						+ "               WHEN destinatario_14_id = ? THEN destinatario_14_num_capi_caprini "
						+ "               WHEN destinatario_15_id = ? THEN destinatario_15_num_capi_caprini "
						+ "               WHEN destinatario_16_id = ? THEN destinatario_16_num_capi_caprini "
						+ "               WHEN destinatario_17_id = ? THEN destinatario_17_num_capi_caprini "
						+ "               WHEN destinatario_18_id = ? THEN destinatario_18_num_capi_caprini "
						+ "               WHEN destinatario_19_id = ? THEN destinatario_19_num_capi_caprini "
						+ "               WHEN destinatario_20_id = ? THEN destinatario_20_num_capi_caprini "
						+ "			END as num_capi_caprini_macellati ";
				}
		}
			select += "   FROM m_modulo_art17"+ suffissoVista + " WHERE ";
		
		if (idSeduta>0)
			select = select + " id_seduta = ? AND ";
		else if (tipoComboMacelli==2 && idPartita>0)
			select = select + " id_partita = ? AND ";
		
		select = select + " data_macellazione = ? AND id_macello = ? ";
		
		if(tipoComboMacelli==2)
		{
		if (configTipo.hasDestinatarioCarni()){
			if(esercente == -999)
				select = select + " AND (destinatario_1_nome = ? or destinatario_2_nome = ? or destinatario_3_nome = ? or destinatario_4_nome = ? or destinatario_5_nome = ? or destinatario_6_nome = ? or destinatario_7_nome = ? or destinatario_8_nome = ? or destinatario_9_nome = ? or destinatario_10_nome = ? or destinatario_11_nome = ? or destinatario_12_nome = ? or destinatario_13_nome = ? or destinatario_14_nome = ? or destinatario_15_nome = ? or destinatario_16_nome = ? or destinatario_17_nome = ? or destinatario_18_nome = ? or destinatario_19_nome = ? or destinatario_20_nome = ?)";
			else
				select = select + " AND (destinatario_1_id = ? or destinatario_2_id = ? or destinatario_3_id = ? or destinatario_4_id = ? or destinatario_5_id = ? or destinatario_6_id = ? or destinatario_7_id = ? or destinatario_8_id = ? or destinatario_9_id = ? or destinatario_10_id = ? or destinatario_11_id = ? or destinatario_12_id = ? or destinatario_13_id = ? or destinatario_14_id = ? or destinatario_15_id = ? or destinatario_16_id = ? or destinatario_17_id = ? or destinatario_18_id = ? or destinatario_19_id = ? or destinatario_20_id = ?) ";
		}
		}
		else if(tipoComboMacelli==1)
		{
			if (configTipo.hasDestinatarioCarni()){
				if(esercente == -999)
					select = select + " AND esercente = ? ";
				else
					select = select + " AND id_esercente = ?";
			}
		}
		
		if(tipoComboMacelli==2)
		{
			select += " union SELECT * ";
			
			if(tipoComboMacelli==2)
			{
				if (configTipo.hasDestinatarioCarni())
				{
			select += " , CASE  WHEN destinatario_1_id = ? THEN destinatario_1_num_capi_ovini "
					+ "               WHEN destinatario_2_id = ? THEN destinatario_2_num_capi_ovini "
					+ "               WHEN destinatario_3_id = ? THEN destinatario_3_num_capi_ovini "
					+ "               WHEN destinatario_4_id = ? THEN destinatario_4_num_capi_ovini "
					+ "               WHEN destinatario_5_id = ? THEN destinatario_5_num_capi_ovini "
					+ "               WHEN destinatario_6_id = ? THEN destinatario_6_num_capi_ovini "
					+ "               WHEN destinatario_7_id = ? THEN destinatario_7_num_capi_ovini "
					+ "               WHEN destinatario_8_id = ? THEN destinatario_8_num_capi_ovini "
					+ "               WHEN destinatario_9_id = ? THEN destinatario_9_num_capi_ovini "
					+ "               WHEN destinatario_10_id = ? THEN destinatario_10_num_capi_ovini "
					+ "               WHEN destinatario_11_id = ? THEN destinatario_11_num_capi_ovini "
					+ "               WHEN destinatario_12_id = ? THEN destinatario_12_num_capi_ovini "
					+ "               WHEN destinatario_13_id = ? THEN destinatario_13_num_capi_ovini "
					+ "               WHEN destinatario_14_id = ? THEN destinatario_14_num_capi_ovini "
					+ "               WHEN destinatario_15_id = ? THEN destinatario_15_num_capi_ovini "
					+ "               WHEN destinatario_16_id = ? THEN destinatario_16_num_capi_ovini "
					+ "               WHEN destinatario_17_id = ? THEN destinatario_17_num_capi_ovini "
					+ "               WHEN destinatario_18_id = ? THEN destinatario_18_num_capi_ovini "
					+ "               WHEN destinatario_19_id = ? THEN destinatario_19_num_capi_ovini "
					+ "               WHEN destinatario_20_id = ? THEN destinatario_20_num_capi_ovini "
					+ "			END as num_capi_ovini_macellati,"
					+ "                 CASE  WHEN destinatario_1_id = ? THEN destinatario_1_num_capi_caprini "
							+ "               WHEN destinatario_2_id = ? THEN destinatario_2_num_capi_caprini "
							+ "               WHEN destinatario_3_id = ? THEN destinatario_3_num_capi_caprini "
							+ "               WHEN destinatario_4_id = ? THEN destinatario_4_num_capi_caprini "
							+ "               WHEN destinatario_5_id = ? THEN destinatario_5_num_capi_caprini "
							+ "               WHEN destinatario_6_id = ? THEN destinatario_6_num_capi_caprini "
							+ "               WHEN destinatario_7_id = ? THEN destinatario_7_num_capi_caprini "
							+ "               WHEN destinatario_8_id = ? THEN destinatario_8_num_capi_caprini "
							+ "               WHEN destinatario_9_id = ? THEN destinatario_9_num_capi_caprini "
							+ "               WHEN destinatario_10_id = ? THEN destinatario_10_num_capi_caprini "
							+ "               WHEN destinatario_11_id = ? THEN destinatario_11_num_capi_caprini "
							+ "               WHEN destinatario_12_id = ? THEN destinatario_12_num_capi_caprini "
							+ "               WHEN destinatario_13_id = ? THEN destinatario_13_num_capi_caprini "
							+ "               WHEN destinatario_14_id = ? THEN destinatario_14_num_capi_caprini "
							+ "               WHEN destinatario_15_id = ? THEN destinatario_15_num_capi_caprini "
							+ "               WHEN destinatario_16_id = ? THEN destinatario_16_num_capi_caprini "
							+ "               WHEN destinatario_17_id = ? THEN destinatario_17_num_capi_caprini "
							+ "               WHEN destinatario_18_id = ? THEN destinatario_18_num_capi_caprini "
							+ "               WHEN destinatario_19_id = ? THEN destinatario_19_num_capi_caprini "
							+ "               WHEN destinatario_20_id = ? THEN destinatario_20_num_capi_caprini "
							+ "			END as num_capi_caprini_macellati ";
					}
			}
			
			
			select += " FROM m_modulo_art17"+ suffissoVista + "_sedute WHERE ";
			
			select = select + " data_macellazione = ? AND id_macello = ? ";
			
			if (configTipo.hasDestinatarioCarni())
			{
				if(esercente == -999)
					select = select + " AND (destinatario_1_nome = ? or destinatario_2_nome = ? or destinatario_3_nome = ? or destinatario_4_nome = ? or destinatario_5_nome = ? or destinatario_6_nome = ? or destinatario_7_nome = ? or destinatario_8_nome = ? or destinatario_9_nome = ? or destinatario_10_nome = ? or destinatario_11_nome = ? or destinatario_12_nome = ? or destinatario_13_nome = ? or destinatario_14_nome = ? or destinatario_15_nome = ? or destinatario_16_nome = ? or destinatario_17_nome = ? or destinatario_18_nome = ? or destinatario_19_nome = ? or destinatario_20_nome = ?)";
				else
					select = select + " AND (destinatario_1_id = ? or destinatario_2_id = ? or destinatario_3_id = ? or destinatario_4_id = ? or destinatario_5_id = ? or destinatario_6_id = ? or destinatario_7_id = ? or destinatario_8_id = ? or destinatario_9_id = ? or destinatario_10_id = ? or destinatario_11_id = ? or destinatario_12_id = ? or destinatario_13_id = ? or destinatario_14_id = ? or destinatario_15_id = ? or destinatario_16_id = ? or destinatario_17_id = ? or destinatario_18_id = ? or destinatario_19_id = ? or destinatario_20_id = ?) ";
			}
		}
		
		stat = db.prepareStatement( select );
		
		int i = 0;
		
		
		if(tipoComboMacelli==2)
		{
		if (configTipo.hasDestinatarioCarni()){
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
		}
		}
		
		
		if (idSeduta>0)
			stat.setInt(++i,  idSeduta);
		else if (tipoComboMacelli==2 && idPartita>0)
			stat.setInt(++i,  idPartita);
		
		stat.setTimestamp(++i, data);
		stat.setInt(++i, idMacello);
		
		if(tipoComboMacelli==2)
		{
		if (configTipo.hasDestinatarioCarni()){
			if(esercente == -999)
			{
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
				stat.setString(++i, nomeEsercente);
			}
			else
			{
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
			}
		}
		}
		else if(tipoComboMacelli==1)
		{
			if (configTipo.hasDestinatarioCarni()){
				if(esercente == -999)
					stat.setString(++i, nomeEsercente);
				else
					stat.setInt(++i, esercente);
			}
		}
			
		if(tipoComboMacelli==2)
		{
		if (configTipo.hasDestinatarioCarni()){
			
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
				stat.setInt(++i, esercente);
		}
		}
		
		if(tipoComboMacelli==2)
		{
			stat.setTimestamp(++i, data);
			stat.setInt(++i, idMacello);
			
			if (configTipo.hasDestinatarioCarni())
			{
				if(esercente == -999)
				{
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
					stat.setString(++i, nomeEsercente);
				}
				else
				{
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
					stat.setInt(++i, esercente);
				}
			}
		}
		System.out.println("Query Articolo17: "+stat.toString());
		ResultSet res = stat.executeQuery();
		buildRecord(db, res, esercente,nomeEsercente);
		
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public void popolaDaArt17(Connection db){
		
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
		stat	= db.prepareStatement( 
				"SELECT * FROM m_art17 WHERE " +
				"id_macello = ? and " +
				"id_esercente = ? and " + (esercente == -999 ? "nome_esercente = ? and " : "") +
				"data_modello = ? and " +
				"trashed_date IS NULL" );
		int i = 0;
		stat.setInt( ++i, idMacello );
		stat.setInt( ++i, esercente );
		if(esercente == -999){
			stat.setString( ++i, nomeEsercente );
		}
		stat.setTimestamp( ++i, data );
		res = stat.executeQuery();
		
		if( res.next() )
		{
			buildRecord2(res);
		}	
		else
		{
			store(db);
			//popolaDaArt17(db);//buildRecord2(res);
		}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	private void buildRecord(Connection db, ResultSet rs, int orgId, String nomeOrgId) throws Exception{
		try {
			int i = 0;
			
			if (tipoComboMacelli==1) {
			while (rs.next()){
				String mac_progressivo = "", matricola ="", sesso ="", num_mezzene="", data_nascita="", specie="", categoria="", mod4="", esito="",veterinari="",veterinari_1 ="",veterinari_2 ="",veterinari_3 ="";
				
				mac_progressivo = rs.getString("mac_progressivo");
				matricola = rs.getString( "matricola" ); 
				num_mezzene= rs.getString( "num_mezzene" );
				data_nascita= rs.getString( "data_nascita" );
				specie = rs.getString( "specie" );
				categoria = rs.getString( "categoria" );
				sesso=rs.getString( "sesso" );
				mod4= rs.getString( "mod4" );
				esito=rs.getString( "esito" );
				veterinari_1=rs.getString( "veterinari_1" );
				veterinari_2=rs.getString( "veterinari_2" );
				veterinari_3=rs.getString( "veterinari_3" );
				veterinari_1=(veterinari_1!=null && !veterinari_1.equals("null")?(veterinari_1):(""));
				veterinari_2=(veterinari_2!=null && !veterinari_2.equals("null")?(veterinari_2):(""));
				veterinari_3=(veterinari_3!=null && !veterinari_3.equals("null")?(veterinari_3):(""));
				veterinari=veterinari+veterinari_1;
				if(!veterinari.equals("") && !veterinari_2.equals(""))
					veterinari=veterinari+"<br/>";
				veterinari=veterinari+veterinari_2;
				if(!veterinari.equals("") && !veterinari_3.equals(""))
					veterinari=veterinari+"<br/>";
				veterinari=veterinari+veterinari_3;
				
				String[] valori = {mac_progressivo, matricola, num_mezzene, data_nascita, specie, categoria, sesso, mod4,veterinari, esito};
				listaElementi.put(String.valueOf(i), valori);
				//Capo c = Capo.loadByMatricola(matricola, db);
				//listaCapi.add(c);
				GenericBean gb = GenericBean.loadByIdentificativo(matricola, db, configTipo);
				listaGB.add(gb);
				i++;
			}
			}
			else if (tipoComboMacelli==2) 
			{
				if(idSeduta>0)
				{
					while (rs.next())
					{
						
						
						String mac_progressivo="", veterinari="",veterinari_1 ="",veterinari_2 ="",veterinari_3 ="",partita ="", numcapiovini ="", numcapicaprini="", categoria="", mod4="", esito="", seduta ="", categoria_macellazione="";
						mac_progressivo = rs.getString( "mac_progressivo" );
						partita = rs.getString( "partita" );
						categoria_macellazione = (rs.getBoolean( "specie_suina" )) ? "SUINI" : "OVICAPRINI";
						seduta = rs.getString( "seduta" );
						
						
						PartitaSeduta p = PartitaSeduta.load(String.valueOf(idSeduta), db, configTipo);
						Partita p2 = (Partita) Partita.load(String.valueOf(p.getId_partita()), db, configTipo);
						this.setProprietario(p2.getProprietario());
						if( (orgId > 0 && orgId==p.getDestinatario_1_id()) || (orgId == -999 && orgId==p.getDestinatario_1_id() && nomeOrgId.equals(p.getDestinatario_1_nome())))
						{
							this.setEsercente(p.getDestinatario_1_id());
							this.setNomeEsercente(p.getDestinatario_1_nome());
							numcapiovini =  rs.getString( "destinatario_1_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_1_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_2_id()) || (orgId == -999 && orgId==p.getDestinatario_2_id() && nomeOrgId.equals(p.getDestinatario_2_nome())))
						{
							this.setEsercente(p.getDestinatario_2_id());
							this.setNomeEsercente(p.getDestinatario_2_nome());
							numcapiovini =  rs.getString( "destinatario_2_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_2_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_3_id()) || (orgId == -999 && orgId==p.getDestinatario_3_id() && nomeOrgId.equals(p.getDestinatario_3_nome())))
						{
							this.setEsercente(p.getDestinatario_3_id());
							this.setNomeEsercente(p.getDestinatario_3_nome());
							numcapiovini =  rs.getString( "destinatario_3_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_3_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_4_id()) || (orgId == -999 && orgId==p.getDestinatario_4_id() && nomeOrgId.equals(p.getDestinatario_4_nome())))
						{
							this.setEsercente(p.getDestinatario_4_id());
							this.setNomeEsercente(p.getDestinatario_4_nome());
							numcapiovini =  rs.getString( "destinatario_4_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_4_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_5_id()) || (orgId == -999 && orgId==p.getDestinatario_5_id() && nomeOrgId.equals(p.getDestinatario_5_nome())))
						{
							this.setEsercente(p.getDestinatario_5_id());
							this.setNomeEsercente(p.getDestinatario_5_nome());
							numcapiovini =  rs.getString( "destinatario_5_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_5_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_6_id()) || (orgId == -999 && orgId==p.getDestinatario_6_id() && nomeOrgId.equals(p.getDestinatario_6_nome())))
						{
							this.setEsercente(p.getDestinatario_6_id());
							this.setNomeEsercente(p.getDestinatario_6_nome());
							numcapiovini =  rs.getString( "destinatario_6_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_6_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_7_id()) || (orgId == -999 && orgId==p.getDestinatario_7_id() && nomeOrgId.equals(p.getDestinatario_7_nome())))
						{
							this.setEsercente(p.getDestinatario_7_id());
							this.setNomeEsercente(p.getDestinatario_7_nome());
							numcapiovini =  rs.getString( "destinatario_7_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_7_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_8_id()) || (orgId == -999 && orgId==p.getDestinatario_8_id() && nomeOrgId.equals(p.getDestinatario_8_nome())))
						{
							this.setEsercente(p.getDestinatario_8_id());
							this.setNomeEsercente(p.getDestinatario_8_nome());
							numcapiovini =  rs.getString( "destinatario_8_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_8_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_9_id()) || (orgId == -999 && orgId==p.getDestinatario_9_id() && nomeOrgId.equals(p.getDestinatario_9_nome())))
						{
							this.setEsercente(p.getDestinatario_9_id());
							this.setNomeEsercente(p.getDestinatario_9_nome());
							numcapiovini =  rs.getString( "destinatario_9_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_9_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_10_id()) || (orgId == -999 && orgId==p.getDestinatario_10_id() && nomeOrgId.equals(p.getDestinatario_10_nome())))
						{
							this.setEsercente(p.getDestinatario_10_id());
							this.setNomeEsercente(p.getDestinatario_10_nome());
							numcapiovini =  rs.getString( "destinatario_10_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_10_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_11_id()) || (orgId == -999 && orgId==p.getDestinatario_11_id() && nomeOrgId.equals(p.getDestinatario_11_nome())))
						{
							this.setEsercente(p.getDestinatario_11_id());
							this.setNomeEsercente(p.getDestinatario_11_nome());
							numcapiovini =  rs.getString( "destinatario_11_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_11_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_12_id()) || (orgId == -999 && orgId==p.getDestinatario_12_id() && nomeOrgId.equals(p.getDestinatario_12_nome())))
						{
							this.setEsercente(p.getDestinatario_12_id());
							this.setNomeEsercente(p.getDestinatario_12_nome());
							numcapiovini =  rs.getString( "destinatario_12_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_12_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_13_id()) || (orgId == -999 && orgId==p.getDestinatario_13_id() && nomeOrgId.equals(p.getDestinatario_13_nome())))
						{
							this.setEsercente(p.getDestinatario_13_id());
							this.setNomeEsercente(p.getDestinatario_13_nome());
							numcapiovini =  rs.getString( "destinatario_13_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_13_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_14_id()) || (orgId == -999 && orgId==p.getDestinatario_14_id() && nomeOrgId.equals(p.getDestinatario_14_nome())))
						{
							this.setEsercente(p.getDestinatario_14_id());
							this.setNomeEsercente(p.getDestinatario_14_nome());
							numcapiovini =  rs.getString( "destinatario_14_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_14_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_15_id()) || (orgId == -999 && orgId==p.getDestinatario_15_id() && nomeOrgId.equals(p.getDestinatario_15_nome())))
						{
							this.setEsercente(p.getDestinatario_15_id());
							this.setNomeEsercente(p.getDestinatario_15_nome());
							numcapiovini =  rs.getString( "destinatario_15_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_15_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_16_id()) || (orgId == -999 && orgId==p.getDestinatario_16_id() && nomeOrgId.equals(p.getDestinatario_16_nome())))
						{
							this.setEsercente(p.getDestinatario_16_id());
							this.setNomeEsercente(p.getDestinatario_16_nome());
							numcapiovini =  rs.getString( "destinatario_16_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_16_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_17_id()) || (orgId == -999 && orgId==p.getDestinatario_17_id() && nomeOrgId.equals(p.getDestinatario_17_nome())))
						{
							this.setEsercente(p.getDestinatario_17_id());
							this.setNomeEsercente(p.getDestinatario_17_nome());
							numcapiovini =  rs.getString( "destinatario_17_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_17_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_18_id()) || (orgId == -999 && orgId==p.getDestinatario_18_id() && nomeOrgId.equals(p.getDestinatario_18_nome())))
						{
							this.setEsercente(p.getDestinatario_18_id());
							this.setNomeEsercente(p.getDestinatario_18_nome());
							numcapiovini =  rs.getString( "destinatario_18_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_18_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_19_id()) || (orgId == -999 && orgId==p.getDestinatario_19_id() && nomeOrgId.equals(p.getDestinatario_19_nome())))
						{
							this.setEsercente(p.getDestinatario_19_id());
							this.setNomeEsercente(p.getDestinatario_19_nome());
							numcapiovini =  rs.getString( "destinatario_19_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_19_num_capi_caprini");
						}
						else if( (orgId > 0 && orgId==p.getDestinatario_20_id()) || (orgId == -999 && orgId==p.getDestinatario_20_id() && nomeOrgId.equals(p.getDestinatario_20_nome())))
						{
							this.setEsercente(p.getDestinatario_20_id());
							this.setNomeEsercente(p.getDestinatario_20_nome());
							numcapiovini =  rs.getString( "destinatario_20_num_capi_ovini");
							numcapicaprini =  rs.getString( "destinatario_20_num_capi_caprini");
						}
						
					
//						numcapiovini =  rs.getString( "num_capi_ovini_macellati");
//						numcapicaprini =  rs.getString( "num_capi_caprini_macellati");
						categoria = "";//rs.getString( "categoria" );
						mod4= rs.getString( "mod4" );
						esito=rs.getString( "esito" );
						veterinari_1=rs.getString( "veterinari_1" );
						veterinari_2=rs.getString( "veterinari_2" );
						veterinari_3=rs.getString( "veterinari_3" );
						veterinari_1=(veterinari_1!=null && !veterinari_1.equals("null")?(veterinari_1):(""));
						veterinari_2=(veterinari_2!=null && !veterinari_2.equals("null")?(veterinari_2):(""));
						veterinari_3=(veterinari_3!=null && !veterinari_3.equals("null")?(veterinari_3):(""));
						veterinari=veterinari+veterinari_1;
						if(!veterinari.equals("") && !veterinari_2.equals(""))
							veterinari=veterinari+"<br/>";
						veterinari=veterinari+veterinari_2;
						if(!veterinari.equals("") && !veterinari_3.equals(""))
							veterinari=veterinari+"<br/>";
						veterinari=veterinari+veterinari_3;
						String[] valori = {mac_progressivo, partita, seduta, categoria_macellazione, numcapiovini, numcapicaprini, categoria, mod4, veterinari, esito};
						listaElementi.put(String.valueOf(i), valori);
						GenericBean gb = GenericBean.loadByIdentificativo(partita, db, configTipo);
//						PartitaSeduta p = (PartitaSeduta)gb;
					
						
						this.setCdPartita(partita);
						listaGB.add(gb);
						i++;
					}
				}
				else {
					while (rs.next())
					{
					String mac_progressivo="", partita ="", seduta="",numcapiovini ="", numcapicaprini="", categoria="", mod4="", esito="", veterinari="",veterinari_1 ="",veterinari_2 ="",veterinari_3 ="", categoria_macellazione="";
					int id = -1;
					mac_progressivo = rs.getString( "mac_progressivo" );
					partita = rs.getString( "partita" );
					categoria_macellazione = (rs.getBoolean( "specie_suina" )) ? "SUINI" : "OVICAPRINI";
					seduta = rs.getString( "seduta" );
					id = rs.getInt("id");
					
					GenericBean gb = null;
					Partita p = null;
					if(seduta==null || seduta.equals("") || seduta.equals("null"))
					{	
						gb = GenericBean.loadByIdIdMacello(id, idMacello, db, configTipo);
						p = (Partita)gb;
					}
					else
					{
						p = PartitaSeduta.loadByNumero(seduta, db, configTipo);
					}
					this.setProprietario(p.getProprietario());
					if( (orgId > 0 && orgId==p.getDestinatario_1_id()) || (orgId == -999 && orgId==p.getDestinatario_1_id() && nomeOrgId.equals(p.getDestinatario_1_nome())))
					{
						this.setEsercente(p.getDestinatario_1_id());
						this.setNomeEsercente(p.getDestinatario_1_nome());
						numcapiovini =  rs.getString( "destinatario_1_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_1_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_2_id()) || (orgId == -999 && orgId==p.getDestinatario_2_id() && nomeOrgId.equals(p.getDestinatario_2_nome())))
					{
						this.setEsercente(p.getDestinatario_2_id());
						this.setNomeEsercente(p.getDestinatario_2_nome());
						numcapiovini =  rs.getString( "destinatario_2_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_2_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_3_id()) || (orgId == -999 && orgId==p.getDestinatario_3_id() && nomeOrgId.equals(p.getDestinatario_3_nome())))
					{
						this.setEsercente(p.getDestinatario_3_id());
						this.setNomeEsercente(p.getDestinatario_3_nome());
						numcapiovini =  rs.getString( "destinatario_3_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_3_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_4_id()) || (orgId == -999 && orgId==p.getDestinatario_4_id() && nomeOrgId.equals(p.getDestinatario_4_nome())))
					{
						this.setEsercente(p.getDestinatario_4_id());
						this.setNomeEsercente(p.getDestinatario_4_nome());
						numcapiovini =  rs.getString( "destinatario_4_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_4_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_5_id()) || (orgId == -999 && orgId==p.getDestinatario_5_id() && nomeOrgId.equals(p.getDestinatario_5_nome())))
					{
						this.setEsercente(p.getDestinatario_5_id());
						this.setNomeEsercente(p.getDestinatario_5_nome());
						numcapiovini =  rs.getString( "destinatario_5_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_5_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_6_id()) || (orgId == -999 && orgId==p.getDestinatario_6_id() && nomeOrgId.equals(p.getDestinatario_6_nome())))
					{
						this.setEsercente(p.getDestinatario_6_id());
						this.setNomeEsercente(p.getDestinatario_6_nome());
						numcapiovini =  rs.getString( "destinatario_6_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_6_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_7_id()) || (orgId == -999 && orgId==p.getDestinatario_7_id() && nomeOrgId.equals(p.getDestinatario_7_nome())))
					{
						this.setEsercente(p.getDestinatario_7_id());
						this.setNomeEsercente(p.getDestinatario_7_nome());
						numcapiovini =  rs.getString( "destinatario_7_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_7_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_8_id()) || (orgId == -999 && orgId==p.getDestinatario_8_id() && nomeOrgId.equals(p.getDestinatario_8_nome())))
					{
						this.setEsercente(p.getDestinatario_8_id());
						this.setNomeEsercente(p.getDestinatario_8_nome());
						numcapiovini =  rs.getString( "destinatario_8_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_8_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_9_id()) || (orgId == -999 && orgId==p.getDestinatario_9_id() && nomeOrgId.equals(p.getDestinatario_9_nome())))
					{
						this.setEsercente(p.getDestinatario_9_id());
						this.setNomeEsercente(p.getDestinatario_9_nome());
						numcapiovini =  rs.getString( "destinatario_9_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_9_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_10_id()) || (orgId == -999 && orgId==p.getDestinatario_10_id() && nomeOrgId.equals(p.getDestinatario_10_nome())))
					{
						this.setEsercente(p.getDestinatario_10_id());
						this.setNomeEsercente(p.getDestinatario_10_nome());
						numcapiovini =  rs.getString( "destinatario_10_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_10_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_11_id()) || (orgId == -999 && orgId==p.getDestinatario_11_id() && nomeOrgId.equals(p.getDestinatario_11_nome())))
					{
						this.setEsercente(p.getDestinatario_11_id());
						this.setNomeEsercente(p.getDestinatario_11_nome());
						numcapiovini =  rs.getString( "destinatario_11_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_11_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_12_id()) || (orgId == -999 && orgId==p.getDestinatario_12_id() && nomeOrgId.equals(p.getDestinatario_12_nome())))
					{
						this.setEsercente(p.getDestinatario_12_id());
						this.setNomeEsercente(p.getDestinatario_12_nome());
						numcapiovini =  rs.getString( "destinatario_12_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_12_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_13_id()) || (orgId == -999 && orgId==p.getDestinatario_13_id() && nomeOrgId.equals(p.getDestinatario_13_nome())))
					{
						this.setEsercente(p.getDestinatario_13_id());
						this.setNomeEsercente(p.getDestinatario_13_nome());
						numcapiovini =  rs.getString( "destinatario_13_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_13_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_14_id()) || (orgId == -999 && orgId==p.getDestinatario_14_id() && nomeOrgId.equals(p.getDestinatario_14_nome())))
					{
						this.setEsercente(p.getDestinatario_14_id());
						this.setNomeEsercente(p.getDestinatario_14_nome());
						numcapiovini =  rs.getString( "destinatario_14_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_14_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_15_id()) || (orgId == -999 && orgId==p.getDestinatario_15_id() && nomeOrgId.equals(p.getDestinatario_15_nome())))
					{
						this.setEsercente(p.getDestinatario_15_id());
						this.setNomeEsercente(p.getDestinatario_15_nome());
						numcapiovini =  rs.getString( "destinatario_15_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_15_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_16_id()) || (orgId == -999 && orgId==p.getDestinatario_16_id() && nomeOrgId.equals(p.getDestinatario_16_nome())))
					{
						this.setEsercente(p.getDestinatario_16_id());
						this.setNomeEsercente(p.getDestinatario_16_nome());
						numcapiovini =  rs.getString( "destinatario_16_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_16_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_17_id()) || (orgId == -999 && orgId==p.getDestinatario_17_id() && nomeOrgId.equals(p.getDestinatario_17_nome())))
					{
						this.setEsercente(p.getDestinatario_17_id());
						this.setNomeEsercente(p.getDestinatario_17_nome());
						numcapiovini =  rs.getString( "destinatario_17_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_17_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_18_id()) || (orgId == -999 && orgId==p.getDestinatario_18_id() && nomeOrgId.equals(p.getDestinatario_18_nome())))
					{
						this.setEsercente(p.getDestinatario_18_id());
						this.setNomeEsercente(p.getDestinatario_18_nome());
						numcapiovini =  rs.getString( "destinatario_18_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_18_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_19_id()) || (orgId == -999 && orgId==p.getDestinatario_19_id() && nomeOrgId.equals(p.getDestinatario_19_nome())))
					{
						this.setEsercente(p.getDestinatario_19_id());
						this.setNomeEsercente(p.getDestinatario_19_nome());
						numcapiovini =  rs.getString( "destinatario_19_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_19_num_capi_caprini");
					}
					else if( (orgId > 0 && orgId==p.getDestinatario_20_id()) || (orgId == -999 && orgId==p.getDestinatario_20_id() && nomeOrgId.equals(p.getDestinatario_20_nome())))
					{
						this.setEsercente(p.getDestinatario_20_id());
						this.setNomeEsercente(p.getDestinatario_20_nome());
						numcapiovini =  rs.getString( "destinatario_20_num_capi_ovini");
						numcapicaprini =  rs.getString( "destinatario_20_num_capi_caprini");
					}
					
					
//					numcapiovini =  rs.getString( "num_capi_ovini_macellati");
//					numcapicaprini =  rs.getString( "num_capi_caprini_macellati");
					categoria = "";//rs.getString( "categoria" );
					mod4= rs.getString( "mod4" );
					esito=rs.getString( "esito" );
					veterinari_1=rs.getString( "veterinari_1" );
					veterinari_2=rs.getString( "veterinari_2" );
					veterinari_3=rs.getString( "veterinari_3" );
					veterinari_1=(veterinari_1!=null && !veterinari_1.equals("null")?(veterinari_1):(""));
					veterinari_2=(veterinari_2!=null && !veterinari_2.equals("null")?(veterinari_2):(""));
					veterinari_3=(veterinari_3!=null && !veterinari_3.equals("null")?(veterinari_3):(""));
					veterinari=veterinari+veterinari_1;
					if(!veterinari.equals("") && !veterinari_2.equals(""))
						veterinari=veterinari+"<br/>";
					veterinari=veterinari+veterinari_2;
					if(!veterinari.equals("") && !veterinari_3.equals(""))
						veterinari=veterinari+"<br/>";
					veterinari=veterinari+veterinari_3;
					String[] valori = {mac_progressivo, partita, seduta, categoria_macellazione, numcapiovini, numcapicaprini, categoria, mod4,veterinari, esito};
					listaElementi.put(String.valueOf(i), valori);
					
					
					if(seduta==null || seduta.equals("") || seduta.equals("null"))
					{
						listaGB.add(gb);
					}
					else
					{
						listaGB.add(p);
					}
					i++;
				}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void buildRecord2(ResultSet rs){
		try {
				setAnno(rs.getInt( "anno" ));
				setProgressivoMacellazione(rs.getInt( "progressivo" ));
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getProgressivoMacellazione() {
		return progressivoMacellazione;
	}
	public void setProgressivoMacellazione(int progressivoMacellazione) {
		this.progressivoMacellazione = progressivoMacellazione;
	}
	
	private boolean trovaColonna(ResultSet rs, String column)
	{
	  try
	  {
	    rs.findColumn(column);
	    return true;
	  } catch (SQLException sqlex)
	  {
	   
	  }
	  return false;
	}
	
	public void gestisciChiudiArt17(Connection db){
		if (idSeduta>0)
			chiudiArt17Sedute(db);
		else
			chiudiArt17(db);
	}
	
	public void chiudiArt17(Connection db){
		PreparedStatement stat = null;
		
		for(GenericBean gb : listaGB){
				
			Object o;
			try {
				o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(gb);
				String identificativo = (String)ReflectionUtil.invocaMetodo(o, configTipo.getNomeMetodoIdentificativo(), null);
				boolean art17 = (boolean)ReflectionUtil.invocaMetodo(o, "isArticolo17", null);
					 if (!art17){
						 String suffissoSeduta = "";
						 String nomeIdTabella = configTipo.getNomeCampoIdentificativoTabella();
						 if(o instanceof PartitaSeduta)
						 {
							 suffissoSeduta = "_sedute ";
							 identificativo = (String)ReflectionUtil.invocaMetodo(o, "getNumero", null);
							 nomeIdTabella = "numero";
						 }
						 
						 stat = db.prepareStatement( "UPDATE "+ configTipo.getNomeTabella() + suffissoSeduta + " SET articolo17 = true WHERE "+ nomeIdTabella +" = ? and trashed_date is null " );
						 stat.setString(1, identificativo);
						 stat.executeUpdate();
					 }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
	}
		public void chiudiArt17Sedute(Connection db){
			PreparedStatement stat = null;
			try {
				stat = db.prepareStatement( "UPDATE "+ configTipo.getNomeTabella() + "_sedute SET articolo17 = true WHERE id = ? and articolo17=false and trashed_date is null " );
			
			stat.setInt(1, idSeduta);
			stat.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
//		}
//		
//		
//		
//		if (configTipo.getIdTipo()==1){
//		 for(Capo c : listaCapi){
//			 try {
//				 if (!c.isArticolo17()){
//					 stat = db.prepareStatement( "UPDATE m_capi SET articolo17 = true WHERE cd_matricola = ? and trashed_date is null " );
//					 stat.setString(1, c.getCd_matricola());
//					 stat.executeUpdate();
//				 }
//			 } catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		 }
//		}
//		else if (configTipo.getIdTipo()==2){
//			 for(Partita p : listaPartite){
//				 try {
//					 if (!p.isArticolo17()){
//						 stat = db.prepareStatement( "UPDATE m_partite SET articolo17 = true WHERE cd_partita = ? and trashed_date is null " );
//						 stat.setString(1, p.getCd_partita());
//						 stat.executeUpdate();
//					 }
//				 } catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			 }
//			}
	
	
	private void store(Connection db) throws SQLException{
		calcolaCodiceIdentificativo( db );
		
		String sql = "INSERT INTO m_art17( id_macello, id_esercente, anno,  progressivo, data_modello, data_prima_generazione, data_ultima_generazione, "+
		"utente_prima_generazione , utente_ultima_generazione,  num_capi_prima_generazione,  num_capi_ultima_generazione, num_generazioni, nome_esercente ";
	
	    sql += " ) VALUES (?, ?, ?, ?, ?,now(), now(), ?, ?, ?, ?, ?, ?)";
	    
	    PreparedStatement stat = db.prepareStatement( sql );
	    int i =0;
	    stat.setInt(++i, idMacello);
	    stat.setInt(++i, esercente);
	    stat.setInt(++i, anno);
	    stat.setInt(++i, progressivoMacellazione);
	    stat.setTimestamp(++i, data);
	    stat.setInt(++i, idUtente);
	    stat.setInt(++i, idUtente);
	    stat.setInt(++i, listaGB.size());
	    stat.setInt(++i, listaGB.size());
	    stat.setInt(++i, 1);
	    stat.setString(++i, nomeEsercente);
	  
	    stat.execute();
	    stat.close();
	}
	private void calcolaCodiceIdentificativo( Connection db ) throws SQLException
	{
		int anno_corrente = Integer.parseInt( (new SimpleDateFormat("yyyy")).format( new java.util.Date() ) );
		String sql = "select coalesce( max( progressivo ), 0 ) + 1 as num from m_art17 where " +
				"id_macello = ? and anno = ?";
		PreparedStatement stat = db.prepareStatement( sql );
		stat.setInt( 1, idMacello );
		stat.setInt( 2, anno_corrente );
		ResultSet res = stat.executeQuery();
		
		if( res.next() )
		{
			this.progressivoMacellazione = res.getInt( "num" );
			this.anno = anno_corrente;
		}
		
		stat.close();
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	
}