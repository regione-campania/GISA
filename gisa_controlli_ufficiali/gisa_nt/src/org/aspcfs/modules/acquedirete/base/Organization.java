/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.acquedirete.base.OrganizationEmailAddressList;
import org.aspcfs.modules.acquedirete.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.acquedirete.base.OrganizationAddress;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.acquedirete.base.OrganizationAddressList;
import org.aspcfs.modules.acquedirete.base.OrganizationList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.daffodilwoods.daffodildb.server.sql99.dml.contextuallytypedrowvalueconstructor;
import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.booleanfactor;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

/**
 * @author Unlimited
 *
 */
public class Organization extends GenericBean {

	private static final long serialVersionUID = 7127566929908851547L;

	private static Logger logger = Logger.getLogger(org.aspcfs.modules.acquedirete.base.Organization.class);

	private static java.util.logging.Logger log = java.util.logging.Logger.getLogger("MainLogger");


	//protected double YTD = 0;
	private Vector comuni=new Vector();
	protected PagedListInfo pagedListInfo = null; 

	private OrganizationList elenco_op_controllati = new OrganizationList();

	private OrganizationAddressList addressList = new OrganizationAddressList();
	private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();

	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
	private int siteId = -1;
	private int enteredBy = -1;
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private int modifiedBy = -1;
	private java.sql.Timestamp trashedDate = null;
	private int orgId ;
	private String ip_entered;
	private String ip_modified;
	private String accountName;
	private String name;
	private String asl;
	private int tipo_struttura = -1;
	private int categoriaRischio=-1;
	private int categoria_precedente=-1;
	private String ente_gestore ;
	private int stato ;
	private boolean selezionato = false ;
	

	public boolean isSelezionato() {
		return selezionato;
	}

	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}

	public void setStato(String stato)
	{
		if (!stato.equals(""))
		{
			this.stato = Integer.parseInt(stato);
		}
	}

	public String getEnte_gestore() {
		return ente_gestore;
	}

	public void setEnte_gestore(String ente_gestore) {
		this.ente_gestore = ente_gestore;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	private int orgIdC = -1;
	private int tipologia = -1;
	private int accountSize = -1;
	private String account_number = null;


	/**
	 *  Gets the id attribute of the Organization object
	 *
	 * @return    The id value
	 */
	public int getId() {
		return orgId;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int t){
		this.tipologia = t;
	}

	public String getIp_entered() {
		return ip_entered;
	}

	public void setIp_entered(String ip_entered) {
		this.ip_entered = ip_entered;
	}

	public String getIp_modified() {
		return ip_modified;
	}
	public void setIp_modified(String ip_modified) {
		this.ip_modified = ip_modified;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String name) {
		this.accountName = name;
	}

	public void setAccountSize(String tmp) {
		this.accountSize = Integer.parseInt(tmp);
	}

	public int getAccountSize() {
		return accountSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return account_number;
	}

	public void setAccountNumber(String an) {
		this.account_number = an;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getAsl () {
		return asl;
	}

	public int getOrgIdC() {
		return orgIdC;
	}

	public void setOrgIdC(int orgIdC) {
		this.orgIdC = orgIdC;
	}

	public int getTipo_struttura() {
		return tipo_struttura;
	}

	public void setTipo_struttura(int tipo) {
		this.tipo_struttura = tipo;
	}

	public Integer getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(String categoriaRischio) {
		this.categoriaRischio = Integer.parseInt(categoriaRischio);
	}
	public void setCategoriaRischio(Integer categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public Integer getCategoriaPrecedente() {
		return categoria_precedente;
	}

	public void setCategoriaPrecedente(String categoriaRischio) {
		this.categoria_precedente = Integer.parseInt(categoriaRischio);
	}
	public void setCategoriaPrecedente(Integer categoriaRischio) {
		this.categoria_precedente = categoriaRischio;
	}



	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public int getSiteId() {
		return siteId;
	}

	public Timestamp getTrashedDate() {
		return trashedDate;
	}
	/**
	 *  Constructor for the Organization object, creates an empty Organization
	 *
	 * @since    1.0
	 */
	public Organization() { }


	/**
	 *  Constructor for the Organization object
	 *
	 * @param  rs                Description of Parameter
	 * @exception  SQLException  Description of the Exception
	 * @throws  SQLException     Description of the Exception
	 * @throws  SQLException     Description of Exception
	 */
	public Organization(ResultSet rs) throws SQLException {
		buildRecord(rs);
		addressList.setOrgId(this.getOrgId());
	}


	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void setComuni (Connection db,int idAsl) throws SQLException {

		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		//sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");
		//if(codeUser==-1){
		sql.append("select comune from comuni ");
		if (idAsl>0)
			sql.append(" where codiceistatasl::int ="+idAsl);
	 		sql.append(" order by comune;");
		/*}else{
		    sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
		    }*/
		st = db.createStatement();
		rs = st.executeQuery(sql.toString());

		while (rs.next()) {
			comuni.add(rs.getString("comune"));
		}
		rs.close();
		st.close();

	}
	public void setRequestItems(ActionContext context) {

		addressList = new OrganizationAddressList(context.getRequest());

	}

	public Vector getComuni () throws SQLException {

		return comuni;

	}

	public OrganizationAddressList getAddressList() {
		return addressList;
	}

	public boolean isTrashed() {
		return (trashedDate != null);
	}




	public Organization(Connection db, int orgId) throws SQLException {

		if (orgId == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.* " +
						"FROM organization o " +
				"WHERE  o.org_id = ? " );
		pst.setInt(1, orgId);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, logger);
		if (rs.next()) {
			addressList.setOrgId(orgId);
			addressList.buildList(db);
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (orgId == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setOrgId(rs.getInt("org_id"));
		name = rs.getString("name");
		account_number = rs.getString("account_number");
		
		if (name==null || "".equals(name))
			name = account_number;
		
		siteId = rs.getInt("site_id");
		tipo_struttura = rs.getInt("tipo_struttura");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		trashedDate = rs.getTimestamp("trashed_date");
		this.ente_gestore = rs.getString("banca");
		this.stato = rs.getInt("cessato");
	} 

	public String generaNumeroRegistrazione(Connection db,String comune, String identificativo) 
	{
		String selnext = "select nextval('acquedirete_registrazione_seq')";
		String numero = "" ;
		try {
			db.setAutoCommit(false);

			if (comune != null && ! "".equals(comune))
			{
				String selIstati = "select codiceistatcomune from comuni where comune ilike ?";
				PreparedStatement pst = db.prepareStatement(selnext);
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					numero = rs.getInt(1) + numero;

				pst = db.prepareStatement(selIstati);
				pst.setString(1, comune);
				rs = pst.executeQuery();
				if (rs.next())
					numero = rs.getString(1) + identificativo + numero;
			}
			db.commit();
			db.setAutoCommit(true);
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				db.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				return "" ;
		}
		return numero ;
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */
	public int update(Connection db,ActionContext context) throws SQLException {
		int i = -1;
		boolean doCommit = false;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			i = this.update(db, false);

			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());

				//Solo se la provincia viene selezionata allora avviene il salvataggio       
				if(thisAddress.getCity()!=null ) {
					thisAddress.process(
							db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
				}

			}

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return i;
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @param  override       Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */
	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE organization " +
				"SET name = ?,banca = ? ,cessato = ? , ip_modified = ? , ");

		if (!override) {
			sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
		}

		

		if (tipo_struttura != -1) {
			sql.append("tipo_struttura = ?, ");
		}

		sql.append(
				"modifiedby = ?" );
		sql.append(" WHERE org_id = ? ");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, name);
		pst.setString(++i, ente_gestore);
		pst.setInt(++i, stato);

		pst.setString(++i, ip_modified);

		

		if (tipo_struttura != -1) {
			pst.setInt(++i, tipo_struttura);
		}

		pst.setInt(++i, this.getModifiedBy());
		pst.setInt(++i, this.getOrgId());
		resultCount = pst.executeUpdate();
		pst.close();



		return resultCount;

	}


	public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
		int count = 0;

		boolean commit = true;

		try {
			commit = db.getAutoCommit();
			if (commit) {
				db.setAutoCommit(false);
			}
			StringBuffer sql = new StringBuffer();
			sql.append(
					"UPDATE organization " +
							"SET trashed_date = ?, " +
							"modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
							"modifiedby = ? " +
					"WHERE org_id = ? ");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			if (toTrash) {
				DatabaseUtils.setTimestamp(
						pst, ++i, new Timestamp(System.currentTimeMillis()));
			} else {
				DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
			}
			pst.setInt(++i, tmpUserId);
			pst.setInt(++i, this.getId());
			count = pst.executeUpdate();
			pst.close();

			if (commit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}



	public int selectAsl(Connection db, String comune) throws SQLException {

		StringBuffer sql = new StringBuffer();
		int i=0, asl_code = 0 ;
		sql.append("SELECT codiceistatasl as asl from comuni where comune ilike ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(++i, comune.toLowerCase().replaceAll(" ", "%"));
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			asl_code = rs.getInt("asl");
		}
		return asl_code;
	}


	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			orgId = DatabaseUtils.getNextSeq(db, context,"organization","org_id");
			//idFarmacia = DatabaseUtils.getNextSeq(db, "farmacia_id_seq");
			sql.append(
					"INSERT INTO organization (org_id, name, site_id,banca,cessato, ");

			if (entered != null) {
				sql.append("entered, ");
			}

			if (modified != null) {
				sql.append("modified, ");
			}

			if (account_number != null) {
				sql.append("account_number, ");
			}

			if (tipo_struttura != -1) {
				sql.append("tipo_struttura, ");
			}

			sql.append("enteredby, modifiedby, trashed_date, pregresso,tipologia,ip_entered,ip_modified ");

			sql.append(")");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			if (account_number != null) {
				sql.append("?, ");
			}

			if (tipo_struttura != -1) {
				sql.append("?, ");
			}

			sql.append("?, ?, ?)");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, orgId);
			pst.setString(++i, name);
			pst.setInt(++i, siteId);
			pst.setString(++i, ente_gestore);
			pst.setInt(++i, stato);

			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}

			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}

			if (account_number != null) {
				pst.setString(++i, account_number);
			}

			if (tipo_struttura != -1) {
				pst.setInt(++i, tipo_struttura);
			}

			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			pst.setBoolean(++i, false);
			pst.setInt(++i, 14);
			pst.setString(++i, ip_entered);
			pst.setString(++i, ip_modified);
			pst.execute();
			pst.close();

			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {

				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());


				if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) {
					thisAddress.process(
							db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
				}
			}


			//this.update(db, true);
			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  tmpOrgId       Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int organizationSiteId = -1;
		String sqlSelect =
				"SELECT site_id " +
						"FROM organization " +
						"WHERE org_id = ? ";
		int i = 0;
		pst = db.prepareStatement(sqlSelect);
		pst.setInt(++i, tmpOrgId);
		rs = pst.executeQuery();
		if (rs.next()) {
			organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
		}
		rs.close();
		pst.close();
		return organizationSiteId;
	}

	public boolean logicdelete(Connection db)
			throws SQLException {
		if (orgId == -1) {
			throw new SQLException("Org ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			PreparedStatement pst = db
					.prepareStatement("update organization set trashed_date = current_date ,modified=current_date , modifiedby = ? WHERE org_id = ?");
			pst.setInt(1, this.getModifiedBy());
			pst.setInt(2,orgId);
			pst.execute();
			pst.close();



		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}



}




