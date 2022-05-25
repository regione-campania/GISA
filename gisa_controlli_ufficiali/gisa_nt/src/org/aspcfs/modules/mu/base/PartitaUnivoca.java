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
import java.util.HashMap;
import java.util.Vector;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;



public class PartitaUnivoca extends GenericBean{



private int id = -1;
private int idUtenteInserimento;
private int idMacello = -1;
private String codiceAziendaProvenienza=null;
private String numeroPartita=null;
private String proprietarioAnimali=null;
private String codiceAziendaNascita=null;
private boolean vincoloSanitario=false;
private String motivoVincoloSanitario=null;
private String mod4=null;
private Timestamp dataMod4=null;
private int macellazioneDifferita=-1;
private boolean infoCatenaAlimentare=false;
private Timestamp dataArrivoMacello=null;
private boolean dataArrivoMacelloDichiarata = false;
private String mezzoTipo=null;
private String mezzoTarga=null;
private boolean trasportoSuperiore = false;
private String veterinario1 =null;
private String veterinario2 =null;
private String veterinario3 =null;
private ArrayList<CapoUnivoco> listaCapi = new ArrayList<CapoUnivoco>();
private HashMap<String, Integer> listaCapiNumeri = new HashMap<String, Integer>();
private ArrayList<CapoUnivoco> listaCapiMacellabili = new ArrayList<CapoUnivoco>();
private HashMap<String, Integer> listaCapiMacellabiliNumeri = new HashMap<String, Integer>();

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
	if (idMacello!=null && !idMacello.equals("null") && !idMacello.equals(""))
	this.idMacello = Integer.parseInt(idMacello);
}

public String getCodiceAziendaProvenienza() {
		return codiceAziendaProvenienza;
	}

	public void setCodiceAziendaProvenienza(String codiceAziendaProvenienza) {
		this.codiceAziendaProvenienza = codiceAziendaProvenienza;
	}

	public String getNumeroPartita() {
		return numeroPartita;
	}

	public void setNumeroPartita(String numeroPartita) {
		this.numeroPartita = numeroPartita;
	}

	public String getProprietarioAnimali() {
		return proprietarioAnimali;
	}

	public void setProprietarioAnimali(String proprietarioAnimali) {
		this.proprietarioAnimali = proprietarioAnimali;
	}

	public String getCodiceAziendaNascita() {
		return codiceAziendaNascita;
	}

	public void setCodiceAziendaNascita(String codiceAziendaNascita) {
		this.codiceAziendaNascita = codiceAziendaNascita;
	}

	public boolean isVincoloSanitario() {
		return vincoloSanitario;
	}

	public void setVincoloSanitario(boolean vincoloSanitario) {
		this.vincoloSanitario = vincoloSanitario;
	}
	public void setVincoloSanitario(String vincoloSanitario) {
		if (vincoloSanitario!=null && vincoloSanitario.equals("on"))
			this.vincoloSanitario = true;
	}

	public String getMotivoVincoloSanitario() {
		return motivoVincoloSanitario;
	}

	public void setMotivoVincoloSanitario(String motivoVincoloSanitario) {
		this.motivoVincoloSanitario = motivoVincoloSanitario;
	}

	public String getMod4() {
		return mod4;
	}

	public void setMod4(String mod4) {
		this.mod4 = mod4;
	}

	public Timestamp getDataMod4() {
		return dataMod4;
	}

	public void setDataMod4(Timestamp dataMod4) {
		this.dataMod4 = dataMod4;
	}
	
	public void setDataMod4(String dataMod4) {
		this.dataMod4 =  DateUtils.parseDateStringNew(dataMod4, "dd/MM/yyyy");
	}

	public int getMacellazioneDifferita() {
		return macellazioneDifferita;
	}

	public void setMacellazioneDifferita(int macellazioneDifferita) {
		this.macellazioneDifferita = macellazioneDifferita;
	}
	public void setMacellazioneDifferita(String macellazioneDifferita) {
		if (macellazioneDifferita!=null && !macellazioneDifferita.equals("null") && !macellazioneDifferita.equals(""))
			this.macellazioneDifferita = Integer.parseInt(macellazioneDifferita);
	}

	public boolean isInfoCatenaAlimentare() {
		return infoCatenaAlimentare;
	}

	public void setInfoCatenaAlimentare(boolean infoCatenaAlimentare) {
		this.infoCatenaAlimentare = infoCatenaAlimentare;
	}
	
	public void setInfoCatenaAlimentare(String infoCatenaAlimentare) {
	if (infoCatenaAlimentare!=null && infoCatenaAlimentare.equals("on"))
		this.infoCatenaAlimentare = true;
	}
	
	public Timestamp getDataArrivoMacello() {
		return dataArrivoMacello;
	}

	public void setDataArrivoMacello(Timestamp dataArrivoMacello) {
		this.dataArrivoMacello = dataArrivoMacello;
	}

	public void setDataArrivoMacello(String dataArrivoMacello) {
		this.dataArrivoMacello =  DateUtils.parseDateStringNew(dataArrivoMacello, "dd/MM/yyyy");
	}
	
	public boolean isDataArrivoMacelloDichiarata() {
		return dataArrivoMacelloDichiarata;
	}

	public void setDataArrivoMacelloDichiarata(boolean dataArrivoMacelloDichiarata) {
		this.dataArrivoMacelloDichiarata = dataArrivoMacelloDichiarata;
	}
	
	public void setDataArrivoMacelloDichiarata(String dataArrivoMacelloDichiarata) {
		if (dataArrivoMacelloDichiarata!=null && dataArrivoMacelloDichiarata.equals("on"))
			this.dataArrivoMacelloDichiarata = true;
		}

	public String getMezzoTipo() {
		return mezzoTipo;
	}

	public void setMezzoTipo(String mezzoTipo) {
		this.mezzoTipo = mezzoTipo;
	}

	public String getMezzoTarga() {
		return mezzoTarga;
	}

	public void setMezzoTarga(String mezzoTarga) {
		this.mezzoTarga = mezzoTarga;
	}

	public boolean isTrasportoSuperiore() {
		return trasportoSuperiore;
	}

	public void setTrasportoSuperiore(boolean trasportoSuperiore) {
		this.trasportoSuperiore = trasportoSuperiore;
	}
	
	public void setTrasportoSuperiore(String trasportoSuperiore) {
		if (trasportoSuperiore!=null && trasportoSuperiore.equals("on"))
			this.trasportoSuperiore = true;
	}

	public String getVeterinario1() {
		return veterinario1;
	}

	public void setVeterinario1(String veterinario1) {
		this.veterinario1 = veterinario1;
	}

	public String getVeterinario2() {
		return veterinario2;
	}

	public void setVeterinario2(String veterinario2) {
		this.veterinario2 = veterinario2;
	}

	public String getVeterinario3() {
		return veterinario3;
	}

	public void setVeterinario3(String veterinario3) {
		this.veterinario3 = veterinario3;
	}
	
	
	public ArrayList<CapoUnivoco> getListaCapi() {
		return listaCapi;
	}

	public void setListaCapi(ArrayList<CapoUnivoco> listaCapi) {
		this.listaCapi = listaCapi;
	}

	public HashMap<String, Integer> getListaCapiNumeri() {
		return listaCapiNumeri;
	}

	public void setListaCapiNumeri(HashMap<String, Integer> listaCapiNumeri) {
		this.listaCapiNumeri = listaCapiNumeri;
	}

	public static int nextId(Connection db) {
		
		PreparedStatement stat = null;
		ResultSet res = null;

		int max = 1;

		String sql = "SELECT max(id) as max FROM mu_partite";
		try {
			stat = db.prepareStatement(sql);

			res = stat.executeQuery();

			while (res.next()) {
				max = res.getInt("max") + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return max;
	}
	
	public PartitaUnivoca(){
		
		
	}
	
	public PartitaUnivoca(ActionContext context){
		setIdMacello(context.getRequest().getParameter("idMacello"));
		setNumeroPartita(context.getRequest().getParameter("numero_partita"));
		setCodiceAziendaProvenienza(context.getRequest().getParameter("codiceAziendaProvenienza"));
		setProprietarioAnimali(context.getRequest().getParameter("proprietarioAnimali"));
		setCodiceAziendaNascita(context.getRequest().getParameter("codiceAziendaNascita"));
		setVincoloSanitario(context.getRequest().getParameter("vincoloSanitario"));
		setMotivoVincoloSanitario(context.getRequest().getParameter("vincoloSanitarioMotivo"));
		setMod4(context.getRequest().getParameter("mod4"));
		setDataMod4(context.getRequest().getParameter("dataMod4"));
		setMacellazioneDifferita(context.getRequest().getParameter("macellazioneDifferita"));
		setInfoCatenaAlimentare(context.getRequest().getParameter("infoCatenaAlimentare"));
		setDataArrivoMacello(context.getRequest().getParameter("dataArrivoMacello"));
		setDataArrivoMacelloDichiarata(context.getRequest().getParameter("dataArrivoMacelloDichiarata"));
		setMezzoTipo(context.getRequest().getParameter("mezzoTipo"));
		setMezzoTarga(context.getRequest().getParameter("mezzoTarga"));
		setTrasportoSuperiore(context.getRequest().getParameter("trasportoSuperiore"));
		setVeterinario1(context.getRequest().getParameter("veterinario1"));
		setVeterinario2(context.getRequest().getParameter("veterinario2"));
		setVeterinario3(context.getRequest().getParameter("veterinario3"));
		
	}
	
	public PartitaUnivoca(ResultSet rs) throws SQLException{
		setIdMacello(rs.getInt("id_macello"));
		setNumeroPartita(rs.getString("numero"));
		setCodiceAziendaProvenienza(rs.getString("codice_azienda_provenienza"));
		setProprietarioAnimali(rs.getString("proprietario_animali"));
		setCodiceAziendaNascita(rs.getString("codice_azienda_nascita"));
		setVincoloSanitario(rs.getBoolean("vincolo_sanitario"));
		setMotivoVincoloSanitario(rs.getString("motivo_vincolo_sanitario"));
		setMod4(rs.getString("mod4"));
		setDataMod4(rs.getTimestamp("data_mod4"));
		setMacellazioneDifferita(rs.getInt("macellazione_differita"));
		setInfoCatenaAlimentare(rs.getBoolean("info_catena_alimentare"));
		setDataArrivoMacello(rs.getTimestamp("data_arrivo_macello"));
		setDataArrivoMacelloDichiarata(rs.getBoolean("data_arrivo_macello_dichiarata"));
		setMezzoTipo(rs.getString("mezzo_tipo"));
		setMezzoTarga(rs.getString("mezzo_targa"));
		setTrasportoSuperiore(rs.getBoolean("trasporto_superiore"));
		setVeterinario1(rs.getString("veterinario1"));
		setVeterinario2(rs.getString("veterinario2"));
		setVeterinario3(rs.getString("veterinario3"));
		setId(rs.getInt("id"));
		
	}
	
	public boolean insert(Connection db) throws SQLException{
		
		 int nextSeq = -1;
		 String nexCode = "select nextval('mu_partite_id_seq')";
		  try
		  {
			  PreparedStatement pst = db.prepareStatement(nexCode);
			  ResultSet rs = pst.executeQuery();
			  if (rs.next())
			  {
				  nextSeq=rs.getInt(1);
			  }
			  if(nextSeq>-1)
				  setId(nextSeq);
		  }
		  catch(SQLException e){
			  throw e ;  
		  }
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO mu_partite (entered, id ");
		
		if (idUtenteInserimento>0)
			sql.append(", enteredby");
		if (idMacello>0)
			sql.append(", id_macello");
		if (codiceAziendaProvenienza!=null)
			sql.append(", codice_azienda_provenienza");
		if (numeroPartita!=null)
			sql.append(", numero");
		if (proprietarioAnimali!=null)
			sql.append(", proprietario_animali");
		if (codiceAziendaNascita!=null)
			sql.append(", codice_azienda_nascita");
		if (vincoloSanitario)
			sql.append(", vincolo_sanitario");
		if (motivoVincoloSanitario!=null)
			sql.append(", motivo_vincolo_sanitario");
		if (mod4!=null)
			sql.append(", mod4");
		if (dataMod4!=null)
			sql.append(", data_mod4");
		if (infoCatenaAlimentare)
			sql.append(", info_catena_alimentare");
		if (macellazioneDifferita>0)
			sql.append(", macellazione_differita");
		if (dataArrivoMacello!=null)
			sql.append(", data_arrivo_macello");
		if (dataArrivoMacelloDichiarata)
			sql.append(", data_arrivo_macello_dichiarata");
		if (mezzoTipo!=null)
			sql.append(", mezzo_tipo");
		if (mezzoTarga!=null)
			sql.append(", mezzo_targa");
		if (trasportoSuperiore)
			sql.append(", trasporto_superiore");
		if (veterinario1!=null)
			sql.append(", veterinario1");
		if (veterinario2!=null)
			sql.append(", veterinario2");
		if (veterinario3!=null)
			sql.append(", veterinario3");
		sql.append(")");
		
		sql.append (" VALUES (now(), ? ");
		
		if (idUtenteInserimento>0)
			sql.append(",?");
		if (idMacello>0)
			sql.append(", ?");
		if (codiceAziendaProvenienza!=null)
			sql.append(", ?");
		if (numeroPartita!=null)
			sql.append(", ?");
		if (proprietarioAnimali!=null)
			sql.append(", ?");
		if (codiceAziendaNascita!=null)
			sql.append(", ?");
		if (vincoloSanitario)
			sql.append(", ?");
		if (motivoVincoloSanitario!=null)
			sql.append(", ?");
		if (mod4!=null)
			sql.append(", ?");
		if (dataMod4!=null)
			sql.append(", ?");
		if (infoCatenaAlimentare)
			sql.append(", ?");
		if (macellazioneDifferita>0)
			sql.append(", ?");
		if (dataArrivoMacello!=null)
			sql.append(", ?");
		if (dataArrivoMacelloDichiarata)
			sql.append(", ?");
		if (mezzoTipo!=null)
			sql.append(", ?");
		if (mezzoTarga!=null)
			sql.append(", ?");
		if (trasportoSuperiore)
			sql.append(", ?");
		if (veterinario1!=null)
			sql.append(", ?");
		if (veterinario2!=null)
			sql.append(", ?");
		if (veterinario3!=null)
			sql.append(", ?");
		
		sql.append(")");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());
		
		pst.setInt(++i, id);
		
		if (idUtenteInserimento>0)
			pst.setInt(++i, idUtenteInserimento);
		if (idMacello>0)
			pst.setInt(++i, idMacello);
		if (codiceAziendaProvenienza!=null)
			pst.setString(++i, codiceAziendaProvenienza);
		if (numeroPartita!=null)
			pst.setString(++i, numeroPartita);
		if (proprietarioAnimali!=null)
			pst.setString(++i, proprietarioAnimali);
		if (codiceAziendaNascita!=null)
			pst.setString(++i, codiceAziendaNascita);
		if (vincoloSanitario)
			pst.setBoolean (++i, vincoloSanitario);
		if (motivoVincoloSanitario!=null)
			pst.setString(++i, motivoVincoloSanitario);
		if (mod4!=null)
			pst.setString(++i, mod4);
		if (dataMod4!=null)
			pst.setTimestamp(++i, dataMod4);
		if (infoCatenaAlimentare)
			pst.setBoolean (++i, infoCatenaAlimentare);
		if (macellazioneDifferita>0)
			pst.setInt (++i, macellazioneDifferita);
		if (dataArrivoMacello!=null)
			pst.setTimestamp(++i, dataArrivoMacello);
		if (dataArrivoMacelloDichiarata)
			pst.setBoolean (++i, dataArrivoMacelloDichiarata);
		if (mezzoTipo!=null)
			pst.setString(++i, mezzoTipo);
		if (mezzoTarga!=null)
			pst.setString(++i, mezzoTarga);
		if (trasportoSuperiore)
			pst.setBoolean (++i, trasportoSuperiore);
		if (veterinario1!=null)
			pst.setString(++i, veterinario1);
		if (veterinario2!=null)
			pst.setString(++i, veterinario2);
		if (veterinario3!=null)
			pst.setString(++i, veterinario3);
		
		if (id>0)
			pst.execute();
		
		pst.close();
		return true;
	}

	public PartitaUnivoca(Connection db, int idPartita){
		
		 String sql = "select * from mu_partite where id = ?";
		  
			  PreparedStatement pst;
			try {
				pst = db.prepareStatement(sql);
			
			  pst.setInt(1, idPartita);
			  
			  ResultSet rs = pst.executeQuery();
			  if (rs.next())
			  {
				  buildRecord(rs, db);
			  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void buildRecord(ResultSet rs, Connection db) throws SQLException{
		setIdMacello(rs.getInt("id_macello"));
		setNumeroPartita(rs.getString("numero"));
		setCodiceAziendaProvenienza(rs.getString("codice_azienda_provenienza"));
		setProprietarioAnimali(rs.getString("proprietario_animali"));
		setCodiceAziendaNascita(rs.getString("codice_azienda_nascita"));
		setVincoloSanitario(rs.getBoolean("vincolo_sanitario"));
		setMotivoVincoloSanitario(rs.getString("motivo_vincolo_sanitario"));
		setMod4(rs.getString("mod4"));
		setDataMod4(rs.getTimestamp("data_mod4"));
		setMacellazioneDifferita(rs.getInt("macellazione_differita"));
		setInfoCatenaAlimentare(rs.getBoolean("info_catena_alimentare"));
		setDataArrivoMacello(rs.getTimestamp("data_arrivo_macello"));
		setDataArrivoMacelloDichiarata(rs.getBoolean("data_arrivo_macello_dichiarata"));
		setMezzoTipo(rs.getString("mezzo_tipo"));
		setMezzoTarga(rs.getString("mezzo_targa"));
		setTrasportoSuperiore(rs.getBoolean("trasporto_superiore"));
		setVeterinario1(rs.getString("veterinario1"));
		setVeterinario2(rs.getString("veterinario2"));
		setVeterinario3(rs.getString("veterinario3"));
		setId(rs.getInt("id"));
		//settaListaCapiNumeri(db);
		settaListaCapi(db);
		settaListaCapiMacellabili(db);
		
	}
	
	
	public void buildRecord(ResultSet rs) throws SQLException{
		setIdMacello(rs.getInt("id_macello"));
		setNumeroPartita(rs.getString("numero"));
		setCodiceAziendaProvenienza(rs.getString("codice_azienda_provenienza"));
		setProprietarioAnimali(rs.getString("proprietario_animali"));
		setCodiceAziendaNascita(rs.getString("codice_azienda_nascita"));
		setVincoloSanitario(rs.getBoolean("vincolo_sanitario"));
		setMotivoVincoloSanitario(rs.getString("motivo_vincolo_sanitario"));
		setMod4(rs.getString("mod4"));
		setDataMod4(rs.getTimestamp("data_mod4"));
		setMacellazioneDifferita(rs.getInt("macellazione_differita"));
		setInfoCatenaAlimentare(rs.getBoolean("info_catena_alimentare"));
		setDataArrivoMacello(rs.getTimestamp("data_arrivo_macello"));
		setDataArrivoMacelloDichiarata(rs.getBoolean("data_arrivo_macello_dichiarata"));
		setMezzoTipo(rs.getString("mezzo_tipo"));
		setMezzoTarga(rs.getString("mezzo_targa"));
		setTrasportoSuperiore(rs.getBoolean("trasporto_superiore"));
		setVeterinario1(rs.getString("veterinario1"));
		setVeterinario2(rs.getString("veterinario2"));
		setVeterinario3(rs.getString("veterinario3"));
		setId(rs.getInt("id"));

		
	}
	
	private void settaListaCapi(Connection db){
		 String sql = "select * from mu_capi where id_partita = ? order by specie asc";
		  
		  PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
		
		  pst.setInt(1, id);
		  
		  ResultSet rs = pst.executeQuery();
		  while (rs.next())
		  {
			 CapoUnivoco capo = new CapoUnivoco (rs);
			 capo.setNumeroPartita(this.numeroPartita);
			 capo.setSpecieCapoNome();
			 listaCapi.add(capo);
			 int num = 0;
			 
			 try {
				 if (capo.isFlagArrivatoDeceduto())
					 num = listaCapiNumeri.get(capo.getSpecieCapoNome()+"_deceduti");
				 else 
					 num = listaCapiNumeri.get(capo.getSpecieCapoNome());
			 }
			 catch (Exception e) {}
			 
		
			 num++;
		     if (capo.isFlagArrivatoDeceduto()) 
		    	 listaCapiNumeri.put(capo.getSpecieCapoNome()+"_deceduti", num);
		     else
		    	 listaCapiNumeri.put(capo.getSpecieCapoNome(), num);
		  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void settaListaCapiMacellabili(Connection db){
		 String sql = "select * from mu_capi where id_partita = ? and id_seduta is null and flag_arrivato_deceduto is false order by specie asc";
		  
		  PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql); 
		
		  pst.setInt(1, id);
		  
		  ResultSet rs = pst.executeQuery();
		  while (rs.next())
		  {
			 CapoUnivoco capo = new CapoUnivoco (rs);
			 capo.setNumeroPartita(this.numeroPartita);
			 capo.setSpecieCapoNome();
			 listaCapiMacellabili.add(capo);
			 int num = 0;
			 
			 try {
				 num = listaCapiMacellabiliNumeri.get(capo.getSpecieCapoNome());
			 }
			 catch (Exception e) {}
			 
			 num++;
			 listaCapiMacellabiliNumeri.put(capo.getSpecieCapoNome(), num);
		  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private void settaListaCapiNumeri(Connection db){
//		 String sql = "select specie.description as specie, count(id) as numero from mu_capi c left join lookup_specie_mu specie on specie.code = c.specie where c.id_partita = ? group by specie.description order by specie";
//		  
//		  PreparedStatement pst;
//		try {
//			pst = db.prepareStatement(sql);
//		
//		  pst.setInt(1, id);
//		  
//		  ResultSet rs = pst.executeQuery();
//		  while (rs.next())
//		  {
//			String specie = rs.getString("specie");
//			int numero = rs.getInt("numero");
//			 listaCapiNumeri.put(specie, numero);
//		  }
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public Vector ricercaPartita(Connection db){
		ResultSet rs = null;
		Vector partiteList = new Vector();
		PreparedStatement pst;
		try {
			
			String query = "select * from mu_partite where id>0 ";
			//query = query.substring(0, query.indexOf("order by"));
			
			if (numeroPartita!=null && !numeroPartita.equals(""))
				query+=" and numero ilike ? ";
			
			if (mod4!=null && !mod4.equals(""))
				query+=" and mod4 ilike ? ";
			
			if (idMacello>0)
				query+=" and id_macello =  ? ";
			
			query+=" order by data_arrivo_macello DESC ";
			
			pst = db.prepareStatement(query);
			int i = 0;
			
			if (numeroPartita!=null && !numeroPartita.equals(""))
				pst.setString(++i, "%"+numeroPartita+"%");
			
			if (mod4!=null && !mod4.equals(""))
				pst.setString(++i, "%"+mod4+"%");
			
			if (idMacello>0)
				pst.setInt(++i, idMacello);
			
			rs = DatabaseUtils.executeQuery(db, pst); 
			 while (rs.next()){
					 PartitaUnivoca partita = new PartitaUnivoca(rs);
					 partita.settaListaCapi(db);
					 partita.settaListaCapiMacellabili(db);
					// partita.settaListaCapiNumeri(db);
					 partiteList.add(partita);
				 }
		rs.close();
		pst.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return partiteList;
	}

	public ArrayList<CapoUnivoco> getListaCapiMacellabili() {
		return listaCapiMacellabili;
	}

	public void setListaCapiMacellabili(ArrayList<CapoUnivoco> listaCapiMacellabili) {
		this.listaCapiMacellabili = listaCapiMacellabili;
	}

	public HashMap<String, Integer> getListaCapiMacellabiliNumeri() {
		return listaCapiMacellabiliNumeri;
	}

	public void setListaCapiMacellabiliNumeri(HashMap<String, Integer> listaCapiMacellabiliNumeri) {
		this.listaCapiMacellabiliNumeri = listaCapiMacellabiliNumeri;
	}
}
