/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class LineaProduttivaList extends Vector implements SyncableList {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.StabilimentoList.class);
	protected PagedListInfo pagedListInfo = null;
	private int idCategoria ;
	private int idMacrocategoria ;
	private int idStabilimento ;
	private String tipoSelezione ;
	private Integer[] idLineaProduttiva ;
	private Boolean enabledMacrocategoria ;
	private int id ;
	private int idNorma ;
	private boolean pregresse = false;





	public int getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(int idNorma) {
		this.idNorma = idNorma;
	}

	public boolean isEnabledMacrocategoria() {
		return enabledMacrocategoria;
	}

	public void setEnabledMacrocategoria(boolean enabledMacrocategoria) {
		this.enabledMacrocategoria = enabledMacrocategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoSelezione() {
		return tipoSelezione;
	}

	public void setTipoSelezione(String tipoSelezione) {
		this.tipoSelezione = tipoSelezione;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}


	public void setIdCategoria(String idCategoria) {
		if(idCategoria != null && !idCategoria.equals(""))
			setIdCategoria(Integer.parseInt(idCategoria));
	}

	public void setIdMacrocategoria(String idMacrocategoria) {
		if(idMacrocategoria != null && !idMacrocategoria.equals(""))
			setIdMacrocategoria(Integer.parseInt(idMacrocategoria));
	}


	public int getIdMacrocategoria() {
		return idMacrocategoria;
	}

	public void setIdMacrocategoria(int idMacrocategoria) {
		this.idMacrocategoria = idMacrocategoria;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}



	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub

	}







	protected void createFilter(Connection db, StringBuffer sqlFilter) 
	{
		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}

		if (idNorma >0 )
		{
			sqlFilter.append(" and l.id_norma = "+idNorma+" ");
		}
		if (enabledMacrocategoria!=null)
		{
			sqlFilter.append(" and l.enabled = "+enabledMacrocategoria+" ");
		}
		if (idCategoria>0)
			sqlFilter.append(" and l.id_aggregazione = ? ");
		if (idMacrocategoria >0)
			sqlFilter.append(" and l.id_macroarea = ? ");
		if(idStabilimento>0)
			sqlFilter.append(" and id_stabilimento = ? ");
		if (id>0)
			sqlFilter.append(" and rslp.id = ? ");

		if (idLineaProduttiva != null && idLineaProduttiva.length>0)
		{
			sqlFilter.append("AND  rslp.id_linea_produttiva in ( ");
			for (int i = 0 ; i<idLineaProduttiva.length-1 ; i++)
			{
				if(! idLineaProduttiva[i].equals("-1"))
					sqlFilter.append(idLineaProduttiva[i]+",");
			}
			if(! idLineaProduttiva[idLineaProduttiva.length-1].equals("-1"))
				sqlFilter.append(idLineaProduttiva[idLineaProduttiva.length-1]+") ");
			else
				sqlFilter.append(") ");


		}



	}
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		if (idCategoria>0)
			pst.setInt(++i, idCategoria) ;
		if (idMacrocategoria>0)
			pst.setInt(++i, idMacrocategoria) ;
		if(idStabilimento>0)
			pst.setInt(++i, idStabilimento) ;
		if (id>0)
			pst.setInt(++i, id) ;
		return i;
	}


	public LineaProduttiva getObject(ResultSet rs) throws SQLException {

		LineaProduttiva lp = new LineaProduttiva(rs) ;
		return lp ;

	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	} 




	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		String sqlConta = "select count (*) as recordcount " ;

		String sqlSele = 	"select  distinct rslp.scia_sospensione,rslp.data_sospensione_volontaria ,rslp.codice_ufficiale_esistente,l.id_lookup_configurazione_validazione as id_tipo_linea_produttiva, rslp.numero_registrazione,rslp.id_linea_produttiva as id_linea_attivita, l.path_descrizione as descrizione_linea_attivita, rslp.data_fine,rslp.data_inizio,rslp.stato,rslp.primario,rslp.tipo_attivita_produttiva," +
				"l.id_nuova_linea_attivita as id,rslp.id as id_rel_stab_lp,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria, rslp.codice_nazionale, "+
				"l.codice as codice,l.id_norma,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita, rslp.id_vecchia_linea, rslp.id_tipo_vecchio_operatore, l.id_lookup_tipo_linee_mobili, flag.* , rslp.tipo_carattere as tipo_carattere " +
				(!pregresse ? ", l.consenti_upload_file, l.consenti_valori_multipli" : "") +
				", (select count(*)>0 from linee_mobili_html_fields f where f.enabled and f.id_linea = l.id_nuova_linea_attivita ) as exist_campi_estesi ";
		String sqlFrom = " from " + (!pregresse ? "ml8" : "opu")+"_linee_attivita_nuove_materializzata l "+ 
				" join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita and rslp.enabled=true " +
				" left join master_list_flag_linee_attivita flag on flag.id_linea = l.id_nuova_linea_attivita "+
				"where 1=1 " ;

		sqlCount.append(sqlConta + sqlFrom);
		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
				pagedListInfo.setItemsPerPage(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("l.id_macroarea,l.id_aggregazione,l.id_attivita");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY rslp.numero_registrazione, l.id_macroarea,l.id_aggregazione,l.id_attivita");
		}

		//Need to build a base SQL statement for returning records

		sqlSelect.append(sqlSele);
		sqlSelect.append(sqlFrom);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	} 


	public ResultSet queryListSearch(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		String sqlConta = "select count (*) as recordcount " ;

		String sqlSele = 	"select  distinct  l.id_tipo_linea_produttiva," +
				"l.id_nuova_linea_attivita as id, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice as codice,l.id_norma,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita, l.id_lookup_tipo_linee_mobili  " ;

		String sqlFrom = " from  " + (!pregresse ? "ml8" : "opu")+"_linee_attivita_nuove l "+ 
				"where 1=1 " ;

		sqlCount.append(sqlConta + sqlFrom);
		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
				pagedListInfo.setItemsPerPage(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("l.id_macroarea,l.id_aggregazione,l.id_attivita");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY l.id_macroarea,l.id_aggregazione,l.id_attivita");
		}

		//Need to build a base SQL statement for returning records

		sqlSelect.append(sqlSele);
		sqlSelect.append(sqlFrom);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	} 

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {

			LineaProduttiva thisIter = this.getObject(rs);
			this.add(thisIter);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//  buildResources(db);
	}


	public void buildListSearch(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListSearch(db, pst);
		while (rs.next()) {

			LineaProduttiva thisIter = this.getObject(rs);
			this.add(thisIter);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//  buildResources(db);
	}



	public ResultSet queryListLp(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		String sqlConta = "select count (*) as recordcount " ;

		String sqlSele = 	"select  distinct l.id_tipo_linea_produttiva, rslp.data_inizio,rslp.stato,rslp.primario,rslp.tipo_attivita_produttiva," +
				"l.id_nuova_linea_attivita as id,-1 as id_rel_stab_lp,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice as codice,l.id_norma,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita, l.id_lookup_tipo_linee_mobili  " ;

		String sqlFrom = " from  " + (!pregresse ? "ml8" : "opu")+"_linee_attivita_nuove l "+ 
				"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita " +
				"where 1=1 " ;

		sqlCount.append(sqlConta + sqlFrom);
		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
				pagedListInfo.setItemsPerPage(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("l.id_macroarea,l.id_aggregazione,l.id_attivita");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY l.id_macroarea,l.id_aggregazione,l.id_attivita");
		}

		//Need to build a base SQL statement for returning records

		sqlSelect.append(sqlSele);
		sqlSelect.append(sqlFrom);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	} 
	public void buildListLp(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListLp(db, pst);
		while (rs.next()) {

			LineaProduttiva thisIter = this.getObject(rs);

			this.add(thisIter);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//  buildResources(db);
	}


	public ArrayList<HashMap<String, Object>> getHashmapListaLineeProduttive() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{

		Iterator<LineaProduttiva> itLinee = this.iterator();
		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaLinee = new ArrayList<HashMap<String,Object>>();

		while (itLinee.hasNext())
		{
			LineaProduttiva thisLinea = itLinee.next();
			HashMap<String, Object> dettagliolinea = new HashMap<String, Object>();

			Field[] campi = thisLinea.getClass().getDeclaredFields();
			Method[] metodi = thisLinea.getClass().getMethods();
			for (int i = 0 ; i <campi.length; i++)
			{
				String nomeCampo = campi[i].getName();

				for (int j=0; j<metodi.length; j++ )
				{



					if(  !nomeCampo.equalsIgnoreCase("infoStab") && ! nomeCampo.equalsIgnoreCase("NORMA_STABILIMENTI_852")  &&  ! nomeCampo.equalsIgnoreCase("NORMA_AZIENDE_AGRICOLE"))
					{
						if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
						{

							dettagliolinea.put(nomeCampo, metodi[j].invoke(thisLinea));

						}

					}
				}

				listaLinee.add(dettagliolinea);

			}

		}


		return listaLinee ;

	}

	public boolean isPregresse() {
		return pregresse;
	}

	public void setPregresse(boolean pregresse) {
		this.pregresse = pregresse;
	}




}
