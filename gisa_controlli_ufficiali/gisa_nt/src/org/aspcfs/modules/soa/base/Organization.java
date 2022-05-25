/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.soa.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.soa.base.Organization;
import org.aspcfs.modules.soa.base.OrganizationAddress;
import org.aspcfs.modules.soa.base.OrganizationEmailAddress;
import org.aspcfs.modules.soa.base.OrganizationPhoneNumber;
import org.aspcfs.modules.soa.base.SottoAttivita;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

public class Organization extends GenericBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
	
	public static final int STATO_IMPIANTO_IN_DOMANDA = 3 ;
	
	public static final int STATO_IMPIANTO_INOLTRO_RIC_DEFINITIVO = 12 ;
	public static final int STATO_IMPIANTO_RICHIESTA_RIC_DEFINITIVO = 11 ;
	public static final int STATO_IMPIANTO_RICHIESTA_REVOCA= 10 ;
	public static final int STATO_IMPIANTO_PROROGA_RIC_CONDIZIONATO = 9 ;
	public static final int STATO_IMPIANTO_IN_RICONOSCIUTO_CONDIZIONATO = 5 ;
	
	public static final int STATO_IMPIANTO_AUTORIZZATO = 0 ;
	public static final int STATO_IMPIANTO_REVOCATO = 1 ;
  public static final int TIPO_SOA_RICONOSCIUTI = 1;
  public static final int TIPO_SOA_REGISTRATI = 2;
  protected double YTD = 0;
  private int categoriaRischio=-1;
  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
  private int accountSize = -1;
  private boolean directBill = false;
  private String accountSizeName = null;
  private int listSalutation = -1;
  private int segmentList = -1;
  private int siteId = -1;
  private int stageId = -1;
  private String stageName = null; 
  private String siteClient = null;
  public int segmentId = -1;
  private int subSegmentId = -1;
  private String industryName = null;
  private boolean minerOnly = false;
  private int enteredBy = -1;
  private int tipologia = -1;
  private String specie_allev = null;
  private String orientamento_prod = null;
  private String tipologia_strutt = null;
  private String numero_capi = null;
  private String codice1 = null;
  private String codice2 = null;
  private String codice3 = null;
  private String codice4 = null;
  private String codice5 = null;
  private String codice6 = null;
  private String codice7 = null;
  private String codice8 = null;
  private String codice9 = null;
  private String codice10 = null;
  private String tipo_soa = null;
  private String categoria = null;
  private String codiceImpianto = null;
  private int tipoSoa ;
  private int impianto = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp contractEndDate = null;
  private java.sql.Timestamp date1 = null;
  private java.sql.Timestamp date2 = null;
  
  private java.sql.Timestamp alertDate = null;
  private String alertText = "";
  private Vector comuni=new Vector();
  private int modifiedBy = -1;
  private boolean enabled = true;
  private int employees = 0;
  private double revenue = 0;
  private String ticker = "";
  private String accountNumber = "";
  private int owner = -1;
  private int duplicateId = -1;
  private int importId = -1;
  private int statusId = -1;
  private Timestamp trashedDate = null;
  private String alertDateTimeZone = null;
  private String contractEndDateTimeZone = null;
  private int source = -1;
  private int statoLab = -1;
  private int rating = -1;
  private double potential = 0;
  private String city = null;
  private String state = null;
  private String postalCode = null;
  private String county = null;
  private String numAut = null;
  private OrganizationAddressList addressList = new OrganizationAddressList();
  private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
  private OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";

  private LookupList types = new LookupList();
  private ArrayList typeList = null;

  private boolean contactDelete = false;
  private boolean revenueDelete = false;
  private boolean documentDelete = false;

  //By default, insert a primary contact record if contact information is available.
  //Account contact import disables this automatic insertion when importing

  private boolean hasEnabledOwnerAccount = true;

  //contact as account stuff
  private String nameSalutation = null;
  private String nameFirst = null;
  private String nameMiddle = null;
  private String nameLast = null;
  private String nameSuffix = null;

  private boolean hasOpportunities = false;
  private boolean hasPortalUsers = false;
  private boolean isIndividual = false;

  private boolean forceDelete = false;

  private String dunsType = null;
  private String dunsNumber = null;
  private String businessNameTwo = null;
  private int sicCode = -1;
  private int yearStarted = -1;
  private String sicDescription = null;
  
  	// campi nuovi - progetto STI
	private String partitaIva = null;
	private String codiceFiscale = null;
	private String abi = null;
	private String cab = null;
	private String cin = null;
	private String banca = null;
	private String contoCorrente = null;
	private String nomeCorrentista = null;
	private String codiceFiscaleCorrentista = null;
	private int livelloRischio = -1;
	private int livelloRischioFinale = -1;
	/*dati aggiunti da d.dauria per gestire il riferimento */
	private String codiceFiscaleRappresentante = null;
	private String nomeRappresentante = null;
	private String cognomeRappresentante = null;
	private String emailRappresentante = null;
	private String ritiReligiosi="";
	private java.util.Date prossimoControllo= null;
	private int    titoloRappresentante = -1;
	private String telefonoRappresentante = null;
	private java.sql.Timestamp dataNascitaRappresentante =null;
	private String luogoNascitaRappresentante = "";
	private String tipoAutorizzazzione="";
	private int progressivo_soa=-1;
	private int imballata=-1;
	//private boolean osa=false;
	// fine campi nuovi - progetto STI
	private Timestamp dataScadenzaNumero ;
	//livello di rischio provvisorio e finale: campo ricavato da audit
	private String fax = null;
	private String codiceAteco ;
	private String city_legale_rapp ;
	private String address_legale_rapp ;
	private String prov_legale_rapp;
	private Timestamp date3 ;
	private Timestamp data_assegnazione_approval_number;
	private int idControlloDocumentale ; 
	private int statoIstruttoria ;
	private String tipo_stab = null;
	private java.sql.Timestamp dataPresentazione = null;
	private Timestamp data_prossimo_controllo ;
	private int categoria_precedente=-1;
	
	public ArrayList<SottoAttivita> elencoSottoattivita = new ArrayList<SottoAttivita>();

	
	public void setSottoattivitaList(ArrayList<SottoAttivita>  lista)
	{
		this.elencoSottoattivita = lista ;
	}
	public Timestamp getDataScadenzaNumero() {
		return dataScadenzaNumero;
	}
	
	public int getTipoSoa() {
		return tipoSoa;
	}
	public void setTipoSoa(int tipoSoa) {
		this.tipoSoa = tipoSoa;
	}
	public Vector getComuni()
	{
		return comuni ; 
	}
	public ArrayList<SottoAttivita> getSottoattivitaList()
	{
		return elencoSottoattivita;
	}
private boolean pregresso ;
	
	
	
	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}
	
	public void checkPregresso(Connection db) throws SQLException {
		if (this.getOwner() == -1) {
			throw new SQLException("ID not specified for lookup.");
		}

		PreparedStatement pst = db.prepareStatement(
				"SELECT * " +
				"FROM stabilimenti_dati_mancanti " +
		"WHERE org_id = ? ");
		pst.setInt(1, this.getOrgId());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			pregresso = true ;
		} 
		else
		{
			pregresso = false ;
		}
		
	}
	
	
	
	public void updatePregresso(Connection db) throws SQLException
	{
		
		
		String sql = "";
		if (name != null && ! name.equals(""))
		{
			sql += "update organization set name = ? where org_id="+orgId+";" ;
		}
		
		if (partitaIva != null && ! partitaIva.equals(""))
		{
			sql += "update organization set partita_iva = ? where org_id="+orgId+";" ;
		}
		
		if (codiceFiscale != null && ! codiceFiscale.equals(""))
		{
			sql += "update organization set codice_fiscale = ? where org_id="+orgId+";" ;
		}
		
		if (nomeRappresentante != null && ! nomeRappresentante.equals(""))
		{
			sql += "update organization set nome_rappresentante = ? where org_id="+orgId+";" ;
		}
		if (cognomeRappresentante != null && ! cognomeRappresentante.equals(""))
		{
			sql += "update organization set cognome_rappresentante = ? where org_id="+orgId+";" ;
		}
		if (codiceFiscaleRappresentante != null && ! codiceFiscaleRappresentante.equals(""))
		{
			sql += "update organization set codice_fiscale_rappresentante = ? where org_id="+orgId+";" ;
		}
		if (dataNascitaRappresentante != null)
		{
			sql += "update organization set data_nascita_rappresentante = ? where org_id="+orgId+";" ;
		}
		
		PreparedStatement pst = db.prepareStatement(sql);
		int i = 0 ;
		if (name != null && ! name.equals(""))
		{
			pst.setString(++i, name);
			
		}
		
		if (partitaIva != null && ! partitaIva.equals(""))
		{
			pst.setString(++i, partitaIva);		
			
		}
		
		if (codiceFiscale != null && ! codiceFiscale.equals(""))
		{
			pst.setString(++i, codiceFiscale);		
		}
		
		if (nomeRappresentante != null && ! nomeRappresentante.equals(""))
		{
			pst.setString(++i, nomeRappresentante);		
		}
		if (cognomeRappresentante != null && ! cognomeRappresentante.equals(""))
		{
			pst.setString(++i, cognomeRappresentante);		
		}
		if (codiceFiscaleRappresentante != null && ! codiceFiscaleRappresentante.equals(""))
		{
			pst.setString(++i, codiceFiscaleRappresentante);		
		}
		if (dataNascitaRappresentante != null)
		{
			pst.setTimestamp(++i, dataNascitaRappresentante);		
		}
		pst.execute();
		
		
		
		
		
	}
	
	
	public static LookupList getCategorieSoa(Connection db,int tipoOsm)
	{
		LookupList lookup_categorie_soa = new LookupList();
		LookupElement elm = null ;
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			pst = db.prepareStatement("select * from lookup_categoria_soa where tipo_soa = ? and enabled = true order by description");
			pst.setInt(1, tipoOsm);
			rs = pst.executeQuery() ;
			while (rs.next())
			{
				int code = rs.getInt("code");
				String description = rs.getString("description");
				elm = new LookupElement();
				elm.setCode(code);
				elm.setDescription(description);
				lookup_categorie_soa.add(elm);
				
			}
			
		}
		catch(SQLException e )
		{
			
		}
		
		
		return lookup_categorie_soa ;
	}
	
	public void setSottoattivitaItem(ActionContext context,Connection db) throws SQLException, ParseException
	{
		LookupList lookup_impianto = new LookupList(db,"lookup_impianto_soa");
		lookup_impianto.addItem(-1, "-SELEZIONA -");
		context.getRequest().setAttribute("impianto", lookup_impianto);

		LookupList lookup_categoria = new LookupList(db,"lookup_categoria_soa");
		lookup_categoria.addItem(-1, "-SELEZIONA -");
		context.getRequest().setAttribute("categoria", lookup_categoria);

		LookupList statoLab = new LookupList(db,"lookup_stato_lab");
		statoLab.addItem(-1, "-SELEZIONA -");
		context.getRequest().setAttribute("statoLab", statoLab);

		LookupList tipoAutorizzazzione = new LookupList(db,"lookup_sottoattivita_tipoautorizzazione");
		tipoAutorizzazzione.addItem(-1, "-SELEZIONA -");
		context.getRequest().setAttribute("tipoAutorizzazzione", tipoAutorizzazzione);


		Date curr = new Date();
		Timestamp now =new Timestamp(curr.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		ArrayList<SottoAttivita> listaTemp = this.elencoSottoattivita ;
		int i = this.getSottoattivitaList().size()+1 ;
		while(context.getParameter("impianto_"+i)!=null)
		{
			SottoAttivita sa = new SottoAttivita();
			sa.setAttivita					( lookup_impianto.getSelectedValue(Integer.parseInt(context.getParameter("impianto_"+i))) );
			sa.setCodice_impianto			( Integer.parseInt(context.getParameter("impianto_"+i)) );
			sa.setCodice_sezione			( Integer.parseInt(context.getParameter("categoria_"+i)) );
			if(context.getParameter("id_"+i)!=null)
				sa.setId							(Integer.parseInt(context.getParameter("id_"+i)) );
			if(context.getParameter("dateI_"+i)!=null && !"".equals(context.getParameter("dateI_"+i)))
				sa.setData_inizio_attivita(new Timestamp(sdf.parse(context.getParameter("dateI_"+i)).getTime()) );
			if(context.getParameter("dateF_"+i)!=null && !"".equals(context.getParameter("dateF_"+i)))
				sa.setData_fine_attivita(new Timestamp(sdf.parse(context.getParameter("dateF_"+i)).getTime()) );

			if(context.getParameter("dateIS_"+i)!=null && !"".equals(context.getParameter("dateIS_"+i)))
				sa.setData_inizio_sospensione(new Timestamp(sdf.parse(context.getParameter("dateIS_"+i)).getTime()) );
			
			sa.setStato_attivita(Integer.parseInt(context.getParameter("statoLab_"+i)) );
			sa.setDescrizione_stato_attivita( statoLab.getSelectedValue(Integer.parseInt(context.getParameter("statoLab_"+i)))  );
			sa.setEnabled					( true );
			sa.setEntered_by				( user.getSiteId() );
			sa.setEntered					( now );
			
		
			if (context.getParameter("non_imballata_"+i)!=null)
			{
				sa.setNon_imballata					( 1 );
			}
			
			
			
			sa.setModified					( now );
			sa.setModified_by				( user.getSiteId() );
			if (context.getParameter("riti_religiosi_"+i)!=null)
			{
				sa.setRiti_religiosi(true);
			}
			
			if (context.getParameter("imballata_"+i)!=null)
			{
				sa.setImballata(1);
			}
			
			//sa.setId_classificazione(Integer.parseInt(context.getParameter("classificazione_"+i)));
			String[] prodottiSel =context.getRequest().getParameterValues("prodotti_"+i);
			ArrayList<Integer> listaProdotti = new ArrayList<Integer>();
			if(prodottiSel!=null)
			for(String s : prodottiSel)
			{
				listaProdotti.add(Integer.parseInt(s));
				
			}
			sa.setListaProdotti(listaProdotti);
			
			int stato = -1 ;
			//sa.setStato_attivita			( stato );
			SottoAttivita satemp = null ;
			boolean categoriasezioneesistente = false ;
			for (SottoAttivita ss : listaTemp)
			{
				if(ss.getCodice_sezione()==sa.getCodice_sezione())
				{
					categoriasezioneesistente = true ;
					satemp = ss ;
					break;
				}
				
			}
			if(categoriasezioneesistente == true)
			{
				sa.setTipo_autorizzazione(satemp.getTipo_autorizzazione()); 
			}
			else
			
				sa.setTipo_autorizzazione		(2 );
			i++;
			elencoSottoattivita.add(sa);
		}


	}
	
	public boolean insert_stabilimento(Connection db,ActionContext context,int tipoSoa) 
	{


		modified = new Timestamp(new java.sql.Date(System.currentTimeMillis()).getTime());
		entered = new Timestamp(new java.sql.Date(System.currentTimeMillis()).getTime());

		String sql = "INSERT INTO organization (org_id,name,employees,entered,enteredby,modified," +
		"modifiedby,enabled,direct_bill,site_id,partita_iva,date2,prossimo_controllo," +
		"tipologia,impianto,stato_lab,numaut,codice_impianto,tipo_aut,categoria_rischio,stato_istruttoria,date1,date3,namemiddle,city_legale_rapp,address_legale_rapp" +
		",prov_legale_rapp,codice_fiscale_rappresentante,nome_rappresentante,cognome_rappresentante,email_rappresentante,telefono_rappresentante,data_nascita_rappresentante,luogo_nascita_rappresentante,fax,tipo_soa)" +
		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String sql_esl = "select org_id from organization where site_id = ? and partita_iva=? and tipologia = 97 and trashed_date is null and tipo_soa =?";
		try
		{

			PreparedStatement pst1 = db.prepareStatement(sql_esl);
			pst1.setInt(1,siteId);
			pst1.setString(2, partitaIva);
			pst1.setString(3, tipoSoa+"");

			ResultSet rs = pst1.executeQuery();

			//if (rs.next())
				//return false ;
			orgId = DatabaseUtils.getNextSeq(db,context, "organization","org_id");
			db.setAutoCommit(false);
			enabled=true;

			this.setOrgId(orgId);
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, orgId);
			pst.setString(2, name);
			pst.setInt(3, employees);
			pst.setTimestamp(4,  new Timestamp(System.currentTimeMillis()));
			pst.setInt(5, enteredBy);
			pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pst.setInt(7, modifiedBy);
			pst.setBoolean(8, enabled);
			pst.setBoolean(9, directBill);
			pst.setInt(10, siteId);
			pst.setString(11, partitaIva);
			pst.setTimestamp(12, date2);
			if ((date2 != null)) 
			{
				Timestamp prossimo_controllo = new Timestamp(date2.getTime())  ;
				prossimo_controllo.setDate(prossimo_controllo.getDate()+30);
				pst.setTimestamp(13, prossimo_controllo);
			}
			else
			{
				pst.setTimestamp(13, null);
			}

			pst.setInt(14, tipologia);
			if(getSottoattivitaList().size()>0)
				pst.setInt(15, getSottoattivitaList().get(0).getCodice_impianto());
			else
				pst.setInt(15,impianto);
			pst.setInt(16, statoLab);
			pst.setString(17, numAut);
			pst.setInt(18, impianto);
			pst.setString(19, tipoAutorizzazzione);
			pst.setInt(20, categoriaRischio);
			pst.setInt(21, statoIstruttoria);
			pst.setTimestamp(22, date1);
			pst.setTimestamp(23, date3);
			pst.setString(24, nameMiddle);
			pst.setString(25, city_legale_rapp);
			pst.setString(26, address_legale_rapp);
			pst.setString(27, prov_legale_rapp);
			pst.setString(28,codiceFiscaleRappresentante );
			pst.setString(29,nomeRappresentante );
			pst.setString(30,cognomeRappresentante );
			pst.setString(31,emailRappresentante );
			pst.setString(32,telefonoRappresentante );
			pst.setTimestamp(33,dataNascitaRappresentante );
			pst.setString(34,luogoNascitaRappresentante );
			pst.setString(35,fax );
			pst.setInt(36,tipoSoa );

			pst.execute();
			
			int id = DatabaseUtils.getNextSeqInt(db, context,"quesiti_controllo_documentale_stabilimenti","id");

			PreparedStatement pst_controllo_ins = db.prepareStatement("insert into quesiti_controllo_documentale_stabilimenti (id,id_stabilimento) values  (?,?)");
			pst_controllo_ins.setInt(1,id);
			pst_controllo_ins.setInt(2,orgId);

			pst_controllo_ins.execute();
			


			PreparedStatement pst_controllo_doc = db.prepareStatement("insert into quesiti_risposte_controllo_documentale (id_quesito,id_quesiti_controllo_documentale_stabilimenti,risposta_asl,risposta_stap)  (" +
					"select id,?," +
					"case when competenza_asl = true then false "+
					"else " +
					"true " +
					"end as risp" +
					"" +
			",false from quesiti_controllo_documentale where enabled = true )");
			pst_controllo_doc.setInt(1,id);
			pst_controllo_doc.execute();
			
			
			
			
			//Insert the addresses if there are any
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisAddress.process(
						db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
			}

			Iterator saiterator = getSottoattivitaList().iterator();
			while (saiterator.hasNext()) {
				SottoAttivita thisSa = (SottoAttivita) saiterator.next();
				thisSa.setId_soa(orgId);
				thisSa.setTipo_autorizzazione(2);
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisSa.store(db,context);
			}
			db.commit(); 
			db.setAutoCommit(true);
			return true;

		}
		catch(Exception e)
		{
			try {
				db.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return false ;
	}
	
	public java.sql.Timestamp getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(java.sql.Timestamp dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}

	public Timestamp getData_prossimo_controllo() {
		return data_prossimo_controllo;
	}

	public void setData_prossimo_controllo(Timestamp data_prossimo_controllo) {
		this.data_prossimo_controllo = data_prossimo_controllo;
	}

	public int getCategoria_precedente() {
		return categoria_precedente;
	}

	public void setCategoria_precedente(int categoria_precedente) {
		this.categoria_precedente = categoria_precedente;
	}

	public void setProssimoControllo(java.util.Date prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}

	public String getTipo_stab() {
		return tipo_stab;
	}

	public void setTipo_stab(String tipo_stab) {
		this.tipo_stab = tipo_stab;
	}

	public int getStatoIstruttoria() {
		return statoIstruttoria;
	}

	
	private boolean contolloDocumentale ;
	
	
	public boolean isContolloDocumentale() {
		return contolloDocumentale;
	}
	public void setContolloDocumentale(boolean contolloDocumentale) {
		this.contolloDocumentale = contolloDocumentale;
	}
	public void setStatoIstruttoria(int statoIstruttoria) {
		this.statoIstruttoria = statoIstruttoria;
	}

	public Timestamp getData_assegnazione_approval_number() {
		return data_assegnazione_approval_number;
	}
	
	public String getData_assegnazione_approval_number_asString() {
		
		String dataAssegnazione = "" ;
		if(data_assegnazione_approval_number != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataAssegnazione= sdf.format(new Date(data_assegnazione_approval_number.getTime()));
			
		}
		return dataAssegnazione ;
		
	}


	public void setData_assegnazione_approval_number(
			String data_assegnazione_approval_number) throws ParseException {
		if(data_assegnazione_approval_number != null && ! "".equals(data_assegnazione_approval_number))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_approval_number = new Timestamp(sdf.parse(data_assegnazione_approval_number).getTime());
			
		}
		
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCity_legale_rapp() {
		return city_legale_rapp;
	}

	public void setCity_legale_rapp(String city_legale_rapp) {
		this.city_legale_rapp = city_legale_rapp;
	}

	public String getAddress_legale_rapp() {
		return address_legale_rapp;
	}

	public void setAddress_legale_rapp(String address_legale_rapp) {
		this.address_legale_rapp = address_legale_rapp;
	}

	public String getProv_legale_rapp() {
		return prov_legale_rapp;
	}

	public void setProv_legale_rapp(String prov_legale_rapp) {
		this.prov_legale_rapp = prov_legale_rapp;
	}

	public Timestamp getDate3() {
		return date3;
	}

	public void setDate3(Timestamp date3) {
		this.date3 = date3;
	}

	public int getIdControlloDocumentale() {
		return idControlloDocumentale;
	}

	public void setIdControlloDocumentale(int idControlloDocumentale) {
		this.idControlloDocumentale = idControlloDocumentale;
	}

	public String getTipoAutorizzazzione() {
		return tipoAutorizzazzione;
	}

	public void setTipoAutorizzazzione(String tipoAutorizzazzione) {
		this.tipoAutorizzazzione = tipoAutorizzazzione;
	}

	public int getProgressivo_soa() {
		return progressivo_soa;
	}

	public void setProgressivo_soa(int progressivo_soa) {
		this.progressivo_soa = progressivo_soa;
	}

	public int getImballata() {
		return imballata;
	}

	public void setImballata(int imballata) {
		this.imballata = imballata;
	}

	
	
	public String getRitiReligiosi() {
		return ritiReligiosi;
	}

	public void setRitiReligiosi(String ritiReligiosi) {
		this.ritiReligiosi = ritiReligiosi;
	}

	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}

	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
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
	
	 public java.util.Date getProssimoControllo(){
		 return this.prossimoControllo;
		 	  
	 }
	
	public void updateCategoriaRischio(Connection db, int categoriaR, int orgid) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_rischio = ? where org_id = ?");
		pst.setInt(1, categoriaR);
		pst.setInt(2, orgid);
		pst.execute();
		
	}
	public String getEmailRappresentante() {
		return emailRappresentante;
	}

	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}

	public java.sql.Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(String tmp) {
	    this.dataNascitaRappresentante = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }

	public void setDataNascitaRappresentante(java.sql.Timestamp tmp) {
		this.dataNascitaRappresentante = tmp;
	}
	public String getLuogoNascitaRappresentante() {
		return luogoNascitaRappresentante;
	}

	public void setLuogoNascitaRappresentante(String luogoRappresentante) {
		this.luogoNascitaRappresentante = luogoRappresentante;
	}

	public int getTitoloRappresentante() {
		return titoloRappresentante;
	}

	public void setTitoloRappresentante(int titoloRappresentante) {
		this.titoloRappresentante = titoloRappresentante;
	}

	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}

	public void setTelefonoRappresentante(String telefonoRappresentante) {
		this.telefonoRappresentante = telefonoRappresentante;
	}

	public int getLivelloRischio() {
		return livelloRischio;
	}
	
	public void setLivelloRischio(int tmp) {
		this.livelloRischio = tmp;
	}
	
	private Timestamp dataAudit = null;
	public Timestamp getDataAudit() {
		return dataAudit;
	}

	public void setDataAudit(Timestamp tmp) {
		this.dataAudit = tmp;
	}
	
	public int getLivelloRischioFinale() {
		return livelloRischioFinale;
	}

	public void setLivelloRischioFinale(int tmp) {
		this.livelloRischioFinale = tmp;
	}
	
	
  	// campi nuovi - metodi get e set
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getCodiceImpianto() {
		return codiceImpianto;
	}

	public void setCodiceImpianto(String codiceImpianto) {
		this.codiceImpianto = codiceImpianto;
	}
	
	public int getImpianto() {
		return impianto;
	}

	public void setImpianto(int impianto) {
		this.impianto = impianto;
	}
	
	public String getSpecieAllev() {
		return specie_allev;
	}

	public void setSpecieAllev(String specie_allev) {
		this.specie_allev = specie_allev;
	}
	
	public String getTipoStab() {
		return tipo_soa;
	}

	public void setTipoStab(String tipo_soa) {
		this.tipo_soa = tipo_soa;
	}
	
	public String getNumAut() {
		return numAut;
	}

	/*public void setNumAut(int numAut) {
		this.numAut = numAut;
	}*/
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public void setNumAut(String numAut) {
		this.numAut = numAut;
	}
	
	public String getOrientamentoProd() {
		return orientamento_prod;
	}

	public void setOrientamentoProd(String orientamento_prod) {
		this.orientamento_prod = orientamento_prod;
	}
	
	public String getNumeroCapi() {
		return numero_capi;
	}

	public void setNumeroCapi(String numero_capi) {
		this.numero_capi = numero_capi;
	}
	
	public String getTipologiaStrutt() {
		return tipologia_strutt;
	}

	public void setTipologiaStrutt(String tipologia_strutt) {
		this.tipologia_strutt = tipologia_strutt;
	}
	
	// campi nuovi carmela- metodi get e set
	public int getTipologia() {
		return tipologia;
	}
	
	public String getCodice1() {
		return codice1;
	}

	public void setCodice1(String codice1) {
		this.codice1 = codice1;
			}
	
	public String getCodice2() {
		return codice2;
	}

	public void setCodice2(String codice2) {
		this.codice2 = codice2;
	}
	
	public String getCodice3() {
		return codice3;
	}

	public void setCodice3(String codice3) {
		this.codice3 = codice3;
	}
	
	public String getCodice4() {
		return codice4;
	}

	public void setCodice4(String codice4) {
		this.codice4 = codice4;
	}
	
	public String getCodice5() {
		return codice5;
	}

	public void setCodice5(String codice5) {
		this.codice5 = codice5;
	}
	
	public String getCodice6() {
		return codice6;
	}

	public void setCodice6(String codice6) {
		this.codice6 = codice6;
	}
	
	public String getCodice7() {
		return codice7;
	}

	public void setCodice7(String codice7) {
		this.codice7 = codice7;
	}
	
	public String getCodice8() {
		return codice8;
	}

	public void setCodice8(String codice8) {
		this.codice8 = codice8;
	}
	
	public String getCodice9() {
		return codice9;
	}

	public void setCodice9(String codice9) {
		this.codice9 = codice9;
	}
	
	public String getCodice10() {
		return codice10;
	}
	
	public void setCodice10(String codice10) {
		this.codice10 = codice10;
	}


	public void setTpologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public String getCodiceFiscaleCorrentista() {
		return codiceFiscaleCorrentista;
	}

	public void setCodiceFiscaleCorrentista(String codiceFiscaleCorrentista) {
		this.codiceFiscaleCorrentista = codiceFiscaleCorrentista;
	}

	public String getAbi() {
		return abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}

	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getContoCorrente() {
		return contoCorrente;
	}

	public void setContoCorrente(String contoCorrente) {
		this.contoCorrente = contoCorrente;
	}
	//fine campi nuovi - metodi get e set

	
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
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  org_id            Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Organization(Connection db, int org_id) throws SQLException {
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.*,qrcd.id as id_controllo_documentale, " +
        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county " +
        
        "FROM organization o " +
		"LEFT JOIN quesiti_controllo_documentale_stabilimenti qrcd on (o.org_id = qrcd.id_stabilimento) " +
        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
        "WHERE o.org_id = ? " );
    pst.setInt(1, org_id);

    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
      setDataScadenzaNumero(db);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    
    //if this is an individual account, populate the primary contact record
  
   
    addressList.setOrgId(this.getOrgId());
    addressList.buildList(db);
    this.livelloRischioFinale = getLivelloRischioFinale(db);
    this.dataAudit = getDataAudit(db);
  }

  public static Organization loadbyProgressivo(int progressivoStabilimento, Connection db) throws SQLException
	{

	  Organization		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		if(! db.isClosed())
		{

			String sql = "SELECT org_id FROM organization WHERE progressivo_stabilimento = ? AND enabled AND trashed_date IS NULL";

			try
			{
				stat = db.prepareStatement( sql );

				stat.setInt( 1, progressivoStabilimento );
				res = stat.executeQuery();
				if( res.next() )
				{
					ret = new Organization( db, res.getInt( "org_id" ) );
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{


			}

			return ret;
		}
		return null;
	}
  public static Organization load(String approvalNumber, Connection db)
  {
	  Organization		ret		= null;
	  PreparedStatement	stat	= null;
	  ResultSet			res		= null;
	  
	  String sql = "SELECT org_id FROM organization WHERE numAut = ? AND enabled AND trashed_date IS NULL";
	  
	  try
	  {
		stat = db.prepareStatement( sql );
		
		stat.setString( 1, approvalNumber );
		res = stat.executeQuery();
		if( res.next() )
		{
			ret = new Organization( db, res.getInt( "org_id" ) );
		}
	  }
	  catch (SQLException e)
	  {
		e.printStackTrace();
	  }
	  
	  return ret;
  }

  public static Organization loadVecchio(String ragioneSociale,String ragioneSocialePrec, String approvalNumber, Connection db)
  {
	  Organization		ret		= null;
	  PreparedStatement	stat	= null;
	  ResultSet			res		= null;
	  
	  String sql = "SELECT org_id FROM organization WHERE name = ? AND numAut = ? AND enabled AND trashed_date IS NULL";
	  
	  try
	  {
		stat = db.prepareStatement( sql );
		stat.setString( 1, ragioneSociale );
		stat.setString( 2, approvalNumber );
		res = stat.executeQuery();
		if( res.next() )
		{
			ret = new Organization( db, res.getInt( "org_id" ) );
		}else{
			if(ragioneSocialePrec!=null){
			stat = db.prepareStatement( sql );
			stat.setString( 1, ragioneSocialePrec );
			stat.setString( 2, approvalNumber );
			res = stat.executeQuery();
			if( res.next() )
			{
				ret = new Organization( db, res.getInt( "org_id" ) );
			}
			
		}}
	  }
	  catch (SQLException e)
	  {
		e.printStackTrace();
	  }
	  
	  return ret;
  }

/**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  criteriaString  The new typeList value
   */
  public void setTypeList(String[] criteriaString) {
    if (criteriaString != null) {
      String[] params = criteriaString;
      typeList = new ArrayList(Arrays.asList(params));
    } else {
      typeList = new ArrayList();
    }
  }


  /**
   *  Sets the ContactDelete attribute of the Organization object
   *
   * @param  tmp  The new ContactDelete value
   */
  public void setContactDelete(boolean tmp) {
    this.contactDelete = tmp;
  }


  /**
   *  Gets the approved attribute of the Contact object
   *
   * @return    The approved value
   */
  public boolean isApproved() {
    return (statusId == Import.PROCESSED_UNAPPROVED ? false : true);
  }

  /**
   *  Sets the RevenueDelete attribute of the Organization object
   *
   * @param  tmp  The new RevenueDelete value
   */
  public void setRevenueDelete(boolean tmp) {
    this.revenueDelete = tmp;
  }


  /**
   *  Sets the DocumentDelete attribute of the Organization object
   *
   * @param  tmp  The new DocumentDelete value
   */
  public void setDocumentDelete(boolean tmp) {
    this.documentDelete = tmp;
  }


  /**
   *  Sets the EnteredByName attribute of the Organization object
   *
   * @param  enteredByName  The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the ModifiedByName attribute of the Organization object
   *
   * @param  modifiedByName  The new ModifiedByName value
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the alertDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new alertDateTimeZone value
   */
  public void setAlertDateTimeZone(String tmp) {
    this.alertDateTimeZone = tmp;
  }


  /**
   *  Sets the contractEndDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new contractEndDateTimeZone value
   */
  public void setContractEndDateTimeZone(String tmp) {
    this.contractEndDateTimeZone = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setStatoLab(int tmp) {
    this.statoLab = tmp;
  }

  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(String tmp) {
    this.source = Integer.parseInt(tmp);
  }
  
  

  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setStatoLab(String tmp) {
    this.statoLab = Integer.parseInt(tmp);
  }
  
  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setImpianto(String tmp) {
    this.impianto = Integer.parseInt(tmp);
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(int tmp) {
    this.rating = tmp;
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(String tmp) {
    this.rating = Integer.parseInt(tmp);
  }


  /**
   *  Sets the potential attribute of the Organization object
   *
   * @param  tmp  The new potential value
   */
  public void setPotential(double tmp) {
    this.potential = tmp;
  }


  /**
   *  Gets the alertDateTimeZone attribute of the Organization object
   *
   * @return    The alertDateTimeZone value
   */
  public String getAlertDateTimeZone() {
    return alertDateTimeZone;
  }


  /**
   *  Gets the contractEndDateTimeZone attribute of the Organization object
   *
   * @return    The contractEndDateTimeZone value
   */
  public String getContractEndDateTimeZone() {
    return contractEndDateTimeZone;
  }


  /**
   *  Gets the source attribute of the Organization object
   *
   * @return    The source value
   */
  public int getSource() {
    return source;
  }
  

  /**
   *  Gets the rating attribute of the Organization object
   *
   * @return    The rating value
   */
  public int getRating() {
    return rating;
  }

  
  /**
   *  Gets the rating attribute of the Organization object
   *
   * @return    The rating value
   */
  public int getStatoLab() {
    return statoLab;
  }

  /**
   *  Gets the potential attribute of the Organization object
   *
   * @return    The potential value
   */
  public double getPotential() {
    return potential;
  }


  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the statusId attribute of the Organization object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the OwnerName attribute of the Organization object
   *
   * @param  ownerName  The new OwnerName value
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Sets the ErrorMessage attribute of the Organization object
   *
   * @param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the types attribute of the Organization object
   *
   * @param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the importId attribute of the Organization object
   *
   * @return    The importId value
   */
  public int getImportId() {
    return importId;
  }




  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   * @param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertText attribute of the Organization object
   *
   * @param  tmp  The new alertText value
   */
  public void setAlertText(String tmp) {
    this.alertText = tmp;
  }


  /**
   *  Sets the Modified attribute of the Organization object
   *
   * @param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Organization object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Organization object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  contractEndDate  The new ContractEndDate value
   */
  public void setContractEndDate(java.sql.Timestamp contractEndDate) {
    this.contractEndDate = contractEndDate;
  }
  public void setDate1(java.sql.Timestamp val) {
	    this.date1 = val;
  }
  public void setDate2(java.sql.Timestamp val) {
	    this.date2 = val;
  }

  /**
   *  Sets the YTD attribute of the Organization object
   *
   * @param  YTD  The new YTD value
   */
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  tmp  The new ContractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }
  public void setDate1(String tmp) {
	    this.date1 = DatabaseUtils.parseDateToTimestamp(tmp);
  }
  public void setDate2(String tmp) {
	    this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
}

  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the Employees attribute of the Organization object
   *
   * @param  employees  The new Employees value
   */
  public void setEmployees(String employees) {
    try {
      this.employees = Integer.parseInt(employees);
    } catch (Exception e) {
      this.employees = 0;
    }
  }


  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   * @param  duplicateId  The new DuplicateId value
   */
  public void setDuplicateId(int duplicateId) {
    this.duplicateId = duplicateId;
  }


  /**
   *  Sets the orgId attribute of the Organization object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
    addressList.setOrgId(tmp);
    phoneNumberList.setOrgId(tmp);
    emailAddressList.setOrgId(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   * @param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Sets the OrgId attribute of the Organization object
   *
   * @param  tmp  The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Revenue attribute of the Organization object
   *
   * @param  revenue  The new Revenue value
   */
  public void setRevenue(String revenue) {
    this.revenue = Double.parseDouble(revenue);
  }


  /**
   *  Sets the revenue attribute of the Organization object
   *
   * @param  tmp  The new revenue value
   */
  public void setRevenue(double tmp) {
    this.revenue = tmp;
  }


  /**
   *  Sets the Name attribute of the Organization object
   *
   * @param  tmp  The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Url attribute of the Organization object
   *
   * @param  tmp  The new Url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Ticker attribute of the Organization object
   *
   * @param  ticker  The new Ticker value
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }


  /**
   *  Sets the LastModified attribute of the Organization object
   *
   * @param  tmp  The new LastModified value
   */
  public void setLastModified(String tmp) {
    this.lastModified = tmp;
  }


  /**
   *  Sets the Notes attribute of the Organization object
   *
   * @param  tmp  The new Notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Industry attribute of the Organization object
   *
   * @param  tmp  The new Industry value
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }


  /**
   *  Sets the account size attribute of the Organization object
   *
   * @param  tmp  The new account size value
   */
  public void setAccountSize(String tmp) {
    this.accountSize = Integer.parseInt(tmp);
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(int tmp) {
    this.listSalutation = tmp;
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(String tmp) {
    this.listSalutation = Integer.parseInt(tmp);
  }


  /**
   *  Sets the segmentList attribute of the Organization object
   *
   * @param  segmentList  The new segmentList value
   */
  public void setSegmentList(int segmentList) {
    this.segmentList = segmentList;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the siteId attribute of the Organization object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }
  
  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  stageId  The new siteId value
   */
  public void setStageId(int stageId) {
    this.stageId = stageId;
  }


  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  tmp  The new stageId value
   */
  public void setStageId(String tmp) {
    this.stageId = Integer.parseInt(tmp);
  }

  /**
   *  Gets the stageId attribute of the Organization object
   *
   * @return    The stageId value
   */
  
  public int getStageId() {
    return stageId;
  }  

  /**
   *  Gets the stageName attribute of the Organization object
   *
   * @return    The stageName value
   */
  
  public String getStageName() {
    return stageName;
  }

  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentList(String tmp) {
    this.segmentList = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteClient attribute of the Organization object
   *
   * @param  siteClient  The new siteClient value
   */
  public void setSiteClient(String siteClient) {
    this.siteClient = siteClient;
  }


  /**
   *  Gets the siteClient attribute of the Organization object
   *
   * @return    The siteClient value
   */
  public String getSiteClient() {
    return siteClient;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  segmentId  The new segmentId value
   */
  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }


  /**
   *  Gets the segmentId attribute of the Organization object
   *
   * @return    The segmentId value
   */
  public int getSegmentId() {
    return segmentId;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentId(String tmp) {
    this.segmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  tmp  The new subSegmentId value
   */
  public void setSubSegmentId(String tmp) {
    this.subSegmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  SubSegmentId  The new subSegmentId value
   */
  public void setSubSegmentId(int SubSegmentId) {
    this.subSegmentId = SubSegmentId;
  }


  /**
   *  Gets the subSegmentId attribute of the Organization object
   *
   * @return    The subSegmentId value
   */
  public int getSubSegmentId() {
    return subSegmentId;
  }


  /**
   *  Gets the segmentList attribute of the Organization object
   *
   * @return    The segmentList value
   */
  public int getSegmentList() {
    return segmentList;
  }


  /**
   *  Gets the listSalutation attribute of the Organization object
   *
   * @return    The listSalutation value
   */
  public int getListSalutation() {
    return listSalutation;
  }


  /**
   *  Sets the industry attribute of the Organization object
   *
   * @param  tmp  The new industry value
   */
  public void setIndustry(int tmp) {
    industry = tmp;
  }


  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(boolean tmp) {
    this.directBill = tmp;
  }


  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(String tmp) {
    this.directBill = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the AddressList attribute of the Organization object
   *
   * @param  tmp  The new AddressList value
   */
  public void setAddressList(OrganizationAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the PhoneNumberList attribute of the Organization object
   *
   * @param  tmp  The new PhoneNumberList value
   */
  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   * @param  tmp  The new EmailAddressList value
   */
  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   * @param  tmp  The new Enteredby value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Organization object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(String tmp) {
    enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   * @param  context  The new requestItems value
   * @since           1.15
   */
  public void setRequestItems(ActionContext context) {
    phoneNumberList = new OrganizationPhoneNumberList(context);
    addressList = new OrganizationAddressList(context.getRequest());
    emailAddressList = new OrganizationEmailAddressList(context.getRequest());
  }


  /**
   *  Gets the ContactDelete attribute of the Organization object
   *
   * @return    The ContactDelete value
   */
  public boolean getContactDelete() {
    return contactDelete;
  }


  /**
   *  Gets the RevenueDelete attribute of the Organization object
   *
   * @return    The RevenueDelete value
   */
  public boolean getRevenueDelete() {
    return revenueDelete;
  }


  /**
   *  Gets the hasOpportunities attribute of the Organization object
   *
   * @return    The hasOpportunities value
   */
  public boolean getHasOpportunities() {
    return hasOpportunities;
  }


  /**
   *  Sets the hasOpportunities attribute of the Organization object
   *
   * @param  hasOpportunities  The new hasOpportunities value
   */
  public void setHasOpportunities(boolean hasOpportunities) {
    this.hasOpportunities = hasOpportunities;
  }


  /**
   *  Sets the hasPortalUsers attribute of the Organization object
   *
   * @param  tmp  The new hasPortalUsers value
   */
  public void setHasPortalUsers(boolean tmp) {
    this.hasPortalUsers = tmp;
  }


  /**
   *  Gets the hasPortalUsers attribute of the Organization object
   *
   * @return    The hasPortalUsers value
   */
  public boolean getHasPortalUsers() {
    return hasPortalUsers;
  }


  /**
   *  Gets the DocumentDelete attribute of the Organization object
   *
   * @return    The DocumentDelete value
   */
  public boolean getDocumentDelete() {
    return documentDelete;
  }


  /**
   *  Gets the YTD attribute of the Organization object
   *
   * @return    The YTD value
   */
  public double getYTD() {
    return YTD;
  }


  /**
   *  Gets the YTDValue attribute of the Organization object
   *
   * @return    The YTDValue value
   */
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }


  /**
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the typeList attribute of the Organization object
   *
   * @return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the alertDate attribute of the Organization object
   *
   * @return    The alertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Entered attribute of the Organization object
   *
   * @return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Organization object
   *
   * @return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   * @return    The ModifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredString attribute of the Organization object
   *
   * @return    The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the nameSalutation attribute of the Organization object
   *
   * @return    The nameSalutation value
   */
  public String getNameSalutation() {
    return nameSalutation;
  }


  /**
   *  Gets the nameFirst attribute of the Organization object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Organization object
   *
   * @return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the nameLast attribute of the Organization object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameSuffix attribute of the Organization object
   *
   * @return    The nameSuffix value
   */
  public String getNameSuffix() {
    return nameSuffix;
  }


  /**
   *  Sets the nameSalutation attribute of the Organization object
   *
   * @param  tmp  The new nameSalutation value
   */
  public void setNameSalutation(String tmp) {
    this.nameSalutation = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Organization object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Organization object
   *
   * @param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Organization object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameSuffix attribute of the Organization object
   *
   * @param  tmp  The new nameSuffix value
   */
  public void setNameSuffix(String tmp) {
    this.nameSuffix = tmp;
  }


  /**
   *  Gets the ContractEndDate attribute of the Organization object
   *
   * @return    The ContractEndDate value
   */
  public java.sql.Timestamp getContractEndDate() {
    return contractEndDate;
  }
  public java.sql.Timestamp getDate1() {
	    return date1;
  }
  public java.sql.Timestamp getDate2() {
	    return date2;
}
  /**
   *  Gets the ContractEndDateString attribute of the Organization object
   *
   * @return    The ContractEndDateString value
   */
  public String getContractEndDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public String getDate1String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date1);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
  }
  public String getDate2String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date2);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
}

  /**
   *  Gets the contractEndDateBufferedString attribute of the Organization
   *  object
   *
   * @return    The contractEndDateBufferedString value
   */
  public String getContractEndDateBufferedString() {
    String tmp = "None";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public String getDate1BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date1);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }
  public String getDate2BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date2);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }

  /**
   *  Gets the asValuesArray attribute of the Organization object
   *
   * @return    The asValuesArray value
   */
  public String[] getAsValuesArray() {
    String[] temp = new String[5];
    temp[0] = this.getName();
    temp[1] = this.getUrl();
    temp[2] = this.getOwnerName();
    temp[3] = this.getEnteredByName();
    temp[4] = this.getModifiedByName();

    return temp;
  }


  /**
   *  Gets the alertDateString attribute of the Organization object
   *
   * @return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";

    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDetails attribute of the Organization object
   *
   * @return    The alertDetails value
   */
  public String getAlertDetails() {
    StringBuffer out = new StringBuffer();

    if (alertDate == null) {
      return out.toString();
    } else {
      out.append(getAlertText() + " - " + getAlertDateString());
      return out.toString().trim();
    }
  }


  /**
   *  Gets the DuplicateId attribute of the Organization object
   *
   * @return    The DuplicateId value
   */
  public int getDuplicateId() {
    return duplicateId;
  }


  /**
   *  Gets the OwnerName attribute of the Organization object
   *
   * @return    The OwnerName value
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the EnteredByName attribute of the Organization object
   *
   * @return    The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the Organization object
   *
   * @return    The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the Owner attribute of the Organization object
   *
   * @return    The Owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the OwnerId attribute of the Organization object
   *
   * @return    The OwnerId value
   */
  public int getOwnerId() {
    return owner;
  }


  /**
   *  Gets the AccountNumber attribute of the Organization object
   *
   * @return    The AccountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Gets the Ticker attribute of the Organization object
   *
   * @return    The Ticker value
   */
  public String getTicker() {
    return ticker;
  }


  /**
   *  Gets the Revenue attribute of the Organization object
   *
   * @return    The Revenue value
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   *  Gets the Employees attribute of the Organization object
   *
   * @return    The Employees value
   */
  public int getEmployees() {
    return employees;
  }


  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   * @return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the id attribute of the Organization object
   *
   * @return    The id value
   */
  public int getId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   * @return    The Name value
   */
  public String getName() {
    if (name != null && name.trim().length() > 0) {
      return name;
    }
    return this.getNameLastFirstMiddle();
  }


  /**
   *  Gets the accountNameOnly attribute of the Organization object
   *
   * @return    The accountNameOnly value
   */
  public String getAccountNameOnly() {
    return name;
  }


  /**
   *  Gets the Url attribute of the Organization object
   *
   * @return    The Url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the urlString attribute of the Organization object when a url or link
   *  needs to be displayed
   *
   * @return    The urlString value
   */
  public String getUrlString() {
    if (url != null) {
      if (url.indexOf("://") == -1) {
        return "http://" + url;
      }
    }
    return url;
  }


  /**
   *  Gets the LastModified attribute of the Organization object
   *
   * @return    The LastModified value
   */
  public String getLastModified() {
    return lastModified;
  }


  /**
   *  Gets the Notes attribute of the Organization object
   *
   * @return    The Notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Industry attribute of the Organization object
   *
   * @return    The Industry value
   */
  public int getIndustry() {
    return industry;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object adding account
   *  size to the ad account form
   *
   * @return    The Account size value
   */
  public int getAccountSize() {
    return accountSize;
  }


  /**
   *  Gets the IndustryName attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getAccountSizeName() {
    return accountSizeName;
  }


  /**
   *  Gets the phone number attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The PhoneNumber value
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Gets the EmailAddress attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The EmailAddress value
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the Address attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The Address value
   */
  public Address getAddress(String thisType) {
    return getAddressList().getAddress(thisType);
  }


  /**
   *  Gets the primaryEmailAddress attribute of the Organization object
   *
   * @return    The primaryEmailAddress value
   */
  public String getPrimaryEmailAddress() {
    return emailAddressList.getPrimaryEmailAddress();
  }


  /**
   *  Gets the primaryPhoneNumber attribute of the Organization object
   *
   * @return    The primaryPhoneNumber value
   */
  public String getPrimaryPhoneNumber() {
    return phoneNumberList.getPrimaryPhoneNumber();
  }


  /**
   *  Gets the primaryAddress attribute of the Organization object
   *
   * @return    The primaryAddress value
   */
  public Address getPrimaryAddress() {
    return getAddressList().getPrimaryAddress();
  }


  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   * @return    The Enteredby value
   */
  public int getEnteredby() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Miner_only attribute of the Organization object
   *
   * @return    The Miner_only value
   */
  public boolean getMiner_only() {
    return minerOnly;
  }


  /**
   *  Gets the MinerOnly attribute of the Organization object
   *
   * @return    The MinerOnly value
   */
  public boolean getMinerOnly() {
    return minerOnly;
  }


  /**
   *  Gets the DirectBill attribute of the Organization object
   *
   * @return    The DirectBill value
   */
  public boolean getDirectBill() {
    return directBill;
  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(boolean tmp) {
    this.forceDelete = tmp;
  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(String tmp) {
    this.forceDelete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the forceDelete attribute of the Organization object
   *
   * @return    The forceDelete value
   */
  public boolean getForceDelete() {
    return forceDelete;
  }


  /**
   * Gets the city attribute of the Organization object
   *
   * @return The city value
   */
  public String getCity() {
    return city;
  }


  /**
   * Sets the city attribute of the Organization object
   *
   * @param tmp The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }
 
  /**
   * Sets the state attribute of the Organization object
   *
   * @param tmp The new state value
   */
	 public void setState(String tmp) {
	 	this.state = tmp;
	 }


	/**
   * Sets the postalCode attribute of the Organization object
   *
   * @param tmp The new postalCode value
   */
	 public void setPostalCode(String tmp) {
	 	this.postalCode = tmp;
	 }
	 
	 
  /**
   * Gets the postalCode attribute of the Organization object
   *
   * @return The postalCode value
   */
	 public String getPostalCode() {
	 	return postalCode;
	 }


  /**
   * Sets the county attribute of the Organization object
   *
   * @param tmp The new county value
   */
	 public void setCounty(String tmp) {
	 	this.county = tmp;
	 }


  /**
   * Gets the state attribute of the Organization object
   *
   * @return The state value
   */
	 public String getState() {
	 	return state;
	 }


  /**
   * Gets the county attribute of the Organization object
   *
   * @return The county value
   */
	 public String getCounty() {
	 	return county;
	 }



  /**
   *  Gets the trashed attribute of the Organization object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Gets the AddressList attribute of the Organization object
   *
   * @return    The AddressList value
   */
  public OrganizationAddressList getAddressList() {
    return addressList;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Organization object
   *
   * @return    The PhoneNumberList value
   */
  public OrganizationPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   * @return    The EmailAddressList value
   */
  public OrganizationEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Gets the isIndividual attribute of the Organization object
   *
   * @return    The isIndividual value
   */
  public boolean getIsIndividual() {
    return isIndividual;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(boolean tmp) {
    this.isIndividual = tmp;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(String tmp) {
    this.isIndividual = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * @return the businessNameTwo
   */
  public String getBusinessNameTwo() {
    return businessNameTwo;
  }


  /**
   * @return the dunsNumber
   */
  public String getDunsNumber() {
    return dunsNumber;
  }


  /**
   * @return the dunsType
   */
  public String getDunsType() {
    return dunsType;
  }


  /**
   * @return the sicCode
   */
  public int getSicCode() {
    return sicCode;
  }

	public String getSicDescription() {
		return sicDescription;
	}

  /**
   * @return the yearStarted
   */
  public int getYearStarted() {
    return yearStarted;
  }


  /**
   * @param businessNameTwo the businessNameTwo to set
   */
  public void setBusinessNameTwo(String businessNameTwo) {
    this.businessNameTwo = businessNameTwo;
  }


  /**
   * @param dunsNumber the dunsNumber to set
   */
  public void setDunsNumber(String dunsNumber) {
    this.dunsNumber = dunsNumber;
  }


  /**
   * @param dunsType the dunsType to set
   */
  public void setDunsType(String dunsType) {
    this.dunsType = dunsType;
  }


  /**
   * @param employees the employees to set
   */
  public void setEmployees(int employees) {
    this.employees = employees;
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(int sicCode) {
    this.sicCode = sicCode;
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(String sicCode) {
    this.sicCode = Integer.parseInt(sicCode);
  }

	public void setSicDescription(String tmp) {
		this.sicDescription = tmp;
	}

  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(int yearStarted) {
    this.yearStarted = yearStarted;
  }


  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(String yearStarted) {
    this.yearStarted = Integer.parseInt(yearStarted);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  year           Description of Parameter
   * @param  type           Description of Parameter
   * @param  ownerId        Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildRevenueYTD(Connection db, int year, int type, int ownerId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT SUM(rv.amount) AS s " +
        "FROM revenue rv " +
        "WHERE rv.org_id = ? AND rv.year = ? AND rv.owner = ? ");
    if (type > 0) {
      sql.append("AND rv.type = ? ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, orgId);
    pst.setInt(++i, year);
    pst.setInt(++i, ownerId);
    if (type > 0) {
      pst.setInt(++i, type);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setYTD(rs.getDouble("s"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  sets the items in the type list to the the lookup list 'types'
   *  Organization object
   *
   * @param  db             The new typeListToTypes value
   * @throws  SQLException  Description of the Exception
   */
  


  
  
  public void setDataScadenzaNumero(Connection db) {
		
		try
		{
			PreparedStatement pst = db.prepareStatement("select min(data_inizio_attivita) from soa_sottoattivita where id_soa = ?");
			pst.setInt(1, orgId);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				this.dataScadenzaNumero = rs.getTimestamp(1);
			}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  type_id        Description of Parameter
   * @param  level          Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (orgId == -1) {
      throw new SQLException("No Organization ID Specified");
    }
    String sql =
        "INSERT INTO soa_type_levels " +
        "(org_id, type_id, " + DatabaseUtils.addQuotes(db, "level") + ") " +
        "VALUES (?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getOrgId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM account_type_levels WHERE org_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization set enabled = ? " +
        "WHERE org_id = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, orgId);

    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization SET enabled = ? " +
        "WHERE org_id = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, orgId);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    int resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 1) {
      success = true;
    }
    return success;
  }

  
  
  
  public int checkIfExistsUpload(Connection db,String approval,String categoria,int impianto) throws SQLException {
	   
	 int statoLab=-1;
	  PreparedStatement pst = null;
	    ResultSet rs = null;
	    String sqlSelect =
	        "SELECT stato_lab " +
	        "FROM organization " +
	        "WHERE numaut= ? and categoria=? and impianto=? and enabled=?";
	    int i = 0;
	    pst = db.prepareStatement(sqlSelect);
	    pst.setString(1, approval);
	    pst.setString(2, categoria);
	    pst.setInt(3,impianto);
	    pst.setBoolean(4,true);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	    	statoLab=rs.getInt("stato_lab");
	    
	    
	    } 
	      rs.close();
	      pst.close();
	      return statoLab;
	    
	  }
  
  
  public int getIdImpianto(Connection db,String codiceImpianto) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    String sqlSelect =
	        "SELECT  code " +
	        "FROM lookup_impianto_soa " +
	        "WHERE description= ? and enabled=?";
	    int idImpianto = 0;
	    pst = db.prepareStatement(sqlSelect);
	    pst.setString(1, codiceImpianto);
	    pst.setBoolean(2, true);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	      idImpianto=rs.getInt("code");
	      rs.close();
	      pst.close();
	    }
	    return idImpianto;
	  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  checkName      Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sqlSelect =
        "SELECT name, org_id " +
        "FROM organization " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(organization.name) = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, checkName.toLowerCase());
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setDuplicateId(rs.getInt("org_id"));
      rs.close();
      pst.close();
      return true;
    } else {
      rs.close();
      pst.close();
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  lookupName     Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  siteId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String lookupName, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.name) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, lookupName.toLowerCase());
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  first             Description of the Parameter
   * @param  middle            Description of the Parameter
   * @param  last              Description of the Parameter
   * @param  importId          Description of the Parameter
   * @param  siteId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String first, String middle, String last, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.namefirst) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namemiddle) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namelast) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, (first != null ? first.toLowerCase() : (String) null));
    pst.setString(++i, (middle != null ? middle.toLowerCase() : (String) null));
    pst.setString(++i, (last != null ? last.toLowerCase() : (String) null));
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
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
/**
 * Metodo che disabilita uno soa esistente  
 * @param db
 * @param approval
 * @param categoria
 * @param impianto
 */
  public void disabilitaSoa(Connection db,String approval,String categoria,int impianto){
	  PreparedStatement pst = null;
	 
	    int organizationSiteId = -1;
	    String sqlSelect =
	        "update organization set enabled =? where numaut= ? and categoria=? and impianto=?";
	   
	    
	    try {
	    	pst = db.prepareStatement(sqlSelect);
			pst.setBoolean(1, false);
			  pst.setString(2, approval);
			    pst.setString(3, categoria);
			    pst.setInt(4,impianto);
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	   
	  
	  
	  
	  
  }
  

  /**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of the Exception
	 */
	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			if (stageId==-1){
				LookupList stageList = new LookupList();
				stageList.tableName = "lookup_stabilimenti_stage";
				stageList.setShowDisabledFlag(false);
				stageList.buildList(db);
				if (stageList.getFirstEnabledElement()>0){
					stageId = stageList.getFirstEnabledElement();
				}
			}

			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			orgId = DatabaseUtils.getNextSeqInt(db, context,"organization","org_id");

			sql.append(
					"INSERT INTO organization (name, industry_temp_code, url, " +
					"miner_only, owner, duplicate_id, notes, employees, revenue, " +
					"ticker_symbol, account_number, namesalutation, namefirst, namelast, " +
					"namemiddle, trashed_date, segment_id,  direct_bill, account_size,  " +
					"sub_segment_id, site_id, source, rating, potential, " +
			"duns_type, duns_number, business_name_two, year_started, sic_code, sic_description, ");
			if (! "".equals(codiceAteco))
			{
				sql.append("codice_ateco ,") ;
			}

			if (orgId > -1) {
				sql.append("org_id, ");
			}
			if (entered != null) {
				sql.append("entered, ");
			}
			if (statusId > -1) {
				sql.append("status_id, ");
			}
			if (stageId > -1) {
				sql.append("stage_id, ");
			}
			if (importId > -1) {
				sql.append("import_id, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}

			//campi nuovi
			if (partitaIva != null) {
				sql.append("partita_iva, ");
			}
			if (codiceFiscale != null) {
				sql.append("codice_fiscale, ");
			}
			if (abi != null) {
				sql.append("abi, ");
			}
			if (cab != null) {
				sql.append("cab, ");
			}
			if (cin != null) {
				sql.append("cin, ");
			}
			if (banca != null) {
				sql.append("banca, ");
			}
			if (contoCorrente != null) {
				sql.append("conto_corrente, ");
			}
			if (nomeCorrentista != null) {
				sql.append("nome_correntista, ");
			}
			if (codiceFiscaleCorrentista != null) {
				sql.append("cf_correntista, ");
			}
			// fine campi nuovi

			sql.append("enteredBy, modifiedBy, tipologia, specie_allev");

			if(orientamento_prod != null){
				sql.append(", orientamento_prod");
			}
			if(tipologia_strutt != null){
				sql.append(", tipologia_strutt");
			}

			if (numero_capi != null) {
				sql.append(", numero_capi");
			}
			if (codice1 != "") {
				sql.append(", codice1");
			}
			if (codice2 != "") {
				sql.append(", codice2");
			}
			if (codice3 != "") {
				sql.append(", codice3");
			}
			if (codice4 != "") {
				sql.append(", codice4");
			}
			if (codice5 != "") {
				sql.append(", codice5");
			}
			if (codice6 != "") {
				sql.append(", codice6");
			}
			if (codice7 != "") {
				sql.append(", codice7");
			}
			if (codice8 != "") {
				sql.append(", codice8");
			}
			if (codice9 != "") {
				sql.append(", codice9");
			}
			if (codice10 != null) {
				sql.append(", codice10");
			}
			if (tipo_stab != null){
				sql.append(", tipo_stab");
			}
			if (categoria != null) {
				sql.append(", categoria");
			}

			sql.append(", impianto, stato_lab");

			if (numAut != null) {
				sql.append(", numaut");
			}
			if (codiceImpianto != null) {
				sql.append(", codice_impianto");
			}
			if (titoloRappresentante > -1 ) {
				sql.append(", titolo_rappresentante ");
			}
			if (codiceFiscaleRappresentante != "") {
				sql.append(", codice_fiscale_rappresentante ");
			}
			if (nomeRappresentante != "") {
				sql.append(", nome_rappresentante ");
			}
			if (cognomeRappresentante != "") {
				sql.append(", cognome_rappresentante ");
			}
			if (emailRappresentante != "") {
				sql.append(", email_rappresentante ");
			}
			if (telefonoRappresentante != "") {
				sql.append(", telefono_rappresentante ");
			}
			if (dataNascitaRappresentante != null) {
				if(!dataNascitaRappresentante.equals(""))
					sql.append(", data_nascita_rappresentante ");
			}
			if (!luogoNascitaRappresentante.equals("")) {
				sql.append(", luogo_nascita_rappresentante ");
			}
			if (tipoAutorizzazzione!= "") {
				sql.append(", tipo_aut ");
			}
			if (imballata >-1) {
				sql.append(", imballata ");
			}
			if (ritiReligiosi != "") {
				sql.append(", riti_religiosi, categoria_rischio ");
			}


			sql.append(")");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
			sql.append("?,?,?,?,?,?,");

			if (! "".equals(codiceAteco))
			{
				sql.append("?,");
			}
			if (orgId > -1) {
				sql.append("?,");
			}
			if (entered != null) {
				sql.append("?, ");
			}
			if (statusId > -1) {
				sql.append("?, ");
			}
			if (stageId > -1) {
				sql.append("?, ");
			}
			if (importId > -1) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			//campi nuovi
			if (partitaIva != null) {
				sql.append("?, ");
			}

			if (codiceFiscale != null) {
				sql.append("?, ");
			}
			if (abi != null) {
				sql.append("?, ");
			}
			if (cab != null) {
				sql.append("?, ");
			}
			if (cin != null) {
				sql.append("?, ");
			}
			if (banca != null) {
				sql.append("?, ");
			}
			if (contoCorrente != null) {
				sql.append("?, ");
			}
			if (nomeCorrentista != null) {
				sql.append("?, ");
			}
			if (codiceFiscaleCorrentista != null) {
				sql.append("?, ");
			}
			//fine campi nuovi

			sql.append("?,?,3,?");
			/* if (specie_allev != null) {
        sql.append("?,");
    }*/
			if (orientamento_prod != null) {
				sql.append(", ?");
			}
			if (tipologia_strutt != null) {
				sql.append(", ?");
			}

			if (numero_capi != null) {
				sql.append(", ?");
			} 
			if (codice1 != "") {
				sql.append(", ?");
			}
			if (codice2 != "") {
				sql.append(", ?");
			}
			if (codice3 != "") {
				sql.append(", ?");
			}
			if (codice4 != "") {
				sql.append(", ?");
			}
			if (codice5 != "") {
				sql.append(", ?");
			}
			if (codice6 != "") {
				sql.append(", ?");
			}
			if (codice7 != "") {
				sql.append(", ?");
			}
			if (codice8 != "") {
				sql.append(", ?");
			}
			if (codice9 != "") {
				sql.append(", ?");
			}
			if (codice10 != null) {
				sql.append(", ?");
			}
			if(tipo_stab != null){
				sql.append(", ?");
			}
			if (categoria != null) {
				sql.append(", ?");
			}

			sql.append(", ?, ?");

			if (numAut != null) {
				sql.append(", ?");
			}

			if (codiceImpianto != null) {
				sql.append(", ?");
			}

			//aggiunti da d.dauria
			if (titoloRappresentante > -1) {
				sql.append(", ? ");
			}
			if (codiceFiscaleRappresentante != "") {
				sql.append(", ? ");
			}
			if (nomeRappresentante != "") {
				sql.append(", ? ");
			}
			if (cognomeRappresentante != "") {
				sql.append(", ? ");
			}
			if (emailRappresentante != "") {
				sql.append(", ? ");
			}
			if (telefonoRappresentante != "") {
				sql.append(", ? ");
			}
			if (dataNascitaRappresentante != null) {
				if(!dataNascitaRappresentante.equals(""))
					sql.append(", ? ");
			}
			if (!luogoNascitaRappresentante.equals("")) {
				sql.append(", ? ");
			}

			if (tipoAutorizzazzione!= "") {
				sql.append(", ? ");
			}
			if (imballata >-1) {
				sql.append(", ? ");
			}
			if (ritiReligiosi != "") {
				sql.append(", ?, ? ");
			}


			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, this.getName());
			pst.setInt(++i, this.getIndustry());
			pst.setString(++i, this.getUrl());
			pst.setBoolean(++i, this.getMinerOnly());
			DatabaseUtils.setInt(pst, ++i, this.getOwner());
			pst.setInt(++i, this.getDuplicateId());
			pst.setString(++i, this.getNotes());
			DatabaseUtils.setInt(pst, ++i, this.getEmployees());
			pst.setDouble(++i, this.getRevenue());
			pst.setString(++i, this.getTicker());
			pst.setString(++i, this.getAccountNumber());
			pst.setString(++i, this.getNameSalutation());
			pst.setString(++i, this.getNameFirst());
			pst.setString(++i, this.getNameLast());
			pst.setString(++i, this.getNameMiddle());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			DatabaseUtils.setInt(pst, ++i, this.getSegmentId());
			pst.setBoolean(++i, this.getDirectBill());
			DatabaseUtils.setInt(pst, ++i, this.getAccountSize());
			DatabaseUtils.setInt(pst, ++i, this.getSubSegmentId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			DatabaseUtils.setInt(pst, ++i, this.getSource());
			DatabaseUtils.setInt(pst, ++i, this.getRating());
			pst.setDouble(++i, this.getPotential());
			pst.setString(++i, this.getDunsType());
			pst.setString(++i, this.getDunsNumber());
			pst.setString(++i, this.getBusinessNameTwo());
			DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
			DatabaseUtils.setInt(pst, ++i, this.getSicCode());
			pst.setString(++i, this.getSicDescription());

			if (! "".equals(codiceAteco))
			{
				pst.setString(++i,codiceAteco);
			}
			if (orgId > -1) {
				pst.setInt(++i, orgId);
			}
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (statusId > -1) {
				pst.setInt(++i, this.getStatusId());
			}
			if (stageId > -1) {
				pst.setInt(++i, this.getStageId());
			}
			if (importId > -1) {
				pst.setInt(++i, this.getImportId());
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}

			//campi nuovi
			if (partitaIva != null) {
				pst.setString(++i, this.getPartitaIva());
			}
			if (codiceFiscale != null) {
				pst.setString(++i, this.getCodiceFiscale());
			}
			if (abi != null) {
				pst.setString(++i, this.getAbi());
			}
			if (cab != null) {
				pst.setString(++i, this.getCab());
			}
			if (cin != null) {
				pst.setString(++i, this.getCin());
			}
			if (banca != null) {
				pst.setString(++i, this.getBanca());
			}
			if (contoCorrente != null) {
				pst.setString(++i, this.getContoCorrente());
			}
			if (nomeCorrentista != null) {
				pst.setString(++i, this.getNomeCorrentista());
			}
			if (codiceFiscaleCorrentista != null) {
				pst.setString(++i, this.getCodiceFiscaleCorrentista());
			}
			//fine campi nuovi

			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			//campi stabilimenti
			pst.setString(++i, this.getSpecieAllev());
			if (orientamento_prod != null) {
				pst.setString(++i, this.getOrientamentoProd());
			}
			if (tipologia_strutt != null) {
				pst.setString(++i, this.getTipologiaStrutt());
			}

			if (numero_capi != null) {
				pst.setString(++i, this.getNumeroCapi());
			}

			if (codice1 != "") {
				pst.setString(++i, this.getCodice1());
			}
			if (codice2 != "") {
				pst.setString(++i, this.getCodice2());
			}
			if (codice3 != "") {
				pst.setString(++i, this.getCodice3());
			}
			if (codice4 != "") {
				pst.setString(++i, this.getCodice4());
			}
			if (codice5 != "") {
				pst.setString(++i, this.getCodice5());
			}
			if (codice6 != "") {
				pst.setString(++i, this.getCodice6());
			}
			if (codice7 != "") {
				pst.setString(++i, this.getCodice7());
			}
			if (codice8 != "") {
				pst.setString(++i, this.getCodice8());
			}
			if (codice9 != "") {
				pst.setString(++i, this.getCodice9());
			}
			if (codice10 != null) {
				pst.setString(++i, this.getCodice10());
			}
			if (tipo_stab != null) {
				pst.setString(++i, this.getTipoStab());
			}
			if (categoria != null) {
				pst.setString(++i, this.getCategoria());
			}
			DatabaseUtils.setInt(pst, ++i, this.getImpianto());
			DatabaseUtils.setInt(pst, ++i, this.getStatoLab());

			if (numAut != null) {
				pst.setString(++i, this.getNumAut());
			}

			if (codiceImpianto != null) {
				pst.setString(++i, this.getCodiceImpianto());
			}

			if (titoloRappresentante > -1) {
				pst.setInt(++i, this.getTitoloRappresentante());
			}
			if (codiceFiscaleRappresentante != "") {
				pst.setString(++i, this.getCodiceFiscaleRappresentante());
			}
			if (nomeRappresentante != "") {
				pst.setString(++i, this.getNomeRappresentante());
			}
			if (cognomeRappresentante != "") {
				pst.setString(++i, this.getCognomeRappresentante());
			}
			if (emailRappresentante != "") {
				pst.setString(++i, this.getEmailRappresentante());
			}
			if (telefonoRappresentante != "") {
				pst.setString(++i, this.getTelefonoRappresentante());
			}
			if (dataNascitaRappresentante != null) {
				if(!dataNascitaRappresentante.equals(""))
					pst.setTimestamp(++i, this.getDataNascitaRappresentante());
			}
			if (luogoNascitaRappresentante != "") {
				pst.setString(++i, this.getLuogoNascitaRappresentante());
			}

			if (tipoAutorizzazzione!= "") {
				pst.setString(++i, this.getTipoAutorizzazzione());
			}
			if (imballata >-1) {
				pst.setInt(++i, this.getImballata());
			}
			if (ritiReligiosi != "") {
				pst.setString(++i, this.getRitiReligiosi());
			}
			pst.setInt(++i, this.getCategoriaRischio());
			pst.execute();

			pst.close();



	
			//Insert the phone numbers if there are any
			Iterator iphone = phoneNumberList.iterator();
			while (iphone.hasNext()) {
				OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
				//thisPhoneNumber.insert(db, this.getOrgId(), this.getEnteredBy());
				thisPhoneNumber.process(
						db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
			}

			//Insert the addresses if there are any
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisAddress.process(
						db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
			}

			//Insert the email addresses if there are any
			Iterator iemail = emailAddressList.iterator();
			while (iemail.hasNext()) {
				OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
				//thisEmailAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisEmailAddress.process(
						db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
			}

			this.update(db, true);
			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;
	}
	
	
	public void completaPratica(Connection db) throws SQLException
	{
		try
		{
			db.prepareStatement("update organization set stato_istruttoria = 0 where org_id = "+this.getOrgId()).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}

	}
	
	public void cambiaStatoImpianto(int idStato,int idImpianto,Connection db) throws SQLException
	{
		try
		{
			
			String sql = " update soa_sottoattivita set stato_attivita = "+idStato + ", descrizione_stato_attivita =(select description from lookup_stato_lab_impianti where code = "+idStato+")  " ;
			if (idStato ==STATO_IMPIANTO_AUTORIZZATO || idStato ==STATO_IMPIANTO_REVOCATO)
				sql+= " ,tipo_autorizzazione=1 ";
				
			sql+= " where id = "+idImpianto;
			
			db.prepareStatement(sql).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}

	}
	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public int update(Connection db,ActionContext context) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		int i = -1;
		boolean doCommit = false;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			i = this.update(db, false);
			//Process the phone numbers if there are any
			Iterator iphone = phoneNumberList.iterator();
		
			//Insert the addresses if there are any
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				thisAddress.process(
						db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy(),context);
			}

Iterator saiterator = getSottoattivitaList().iterator();
			
			db.prepareStatement("delete from soa_sottoattivita where id_soa ="+this.orgId).execute();
			db.prepareStatement("delete from lista_prodotti_soa where id_soa ="+this.orgId).execute();
			
			while (saiterator.hasNext()) {
				SottoAttivita thisSa = (SottoAttivita) saiterator.next();
				thisSa.setId_soa(orgId);
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisSa.store(db,context);
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
	
	
	public void cambiaStatoIstruttoria(int idStato,Connection db) throws SQLException
	{
		try
		{
			db.prepareStatement("update organization set stato_istruttoria = "+idStato + " where org_id = "+this.getOrgId()).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}
		
	}
	
	
	public void revocaStabilimento(Connection db) throws SQLException
	{
		try
		{
			db.prepareStatement("update organization set stato_lab = 1 where org_id = "+this.getOrgId()).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}
		
	}

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  newOwner       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
 * @throws InvocationTargetException 
 * @throws IllegalAccessException 
 * @throws IllegalArgumentException 
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    int result = -1;
    this.setOwner(newOwner);

    if (result == -1) {
      return false;
    }

    return true;
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
        "SET name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " +
        "employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, ");

    if (owner > -1) {
      sql.append("owner = ?, ");
    }

    sql.append(
        "duplicate_id = ?, contract_end = ?, date1 = ?, date2 = ?, contract_end_timezone = ?, " +
        "alertdate = ?, alertdate_timezone=?, alert = ?, namesalutation = ?, namefirst = ?, " +
        "namemiddle = ?, namelast = ?, trashed_date = ?, segment_id = ?, " +
        "direct_bill = ?, account_size = ?, site_id = ?, sub_segment_id = ?, " +
        "source = ?, rating = ?, potential = ?, " +
        "duns_type = ?, duns_number = ?, business_name_two = ?, year_started = ?, sic_code = ?, sic_description = ?, ");
    sql.append("stage_id = ? ");
    
    //campi nuovi
	sql.append(", partita_iva = ?, codice_fiscale = ?, abi = ?, cab = ?, cin = ?, banca = ?, conto_corrente = ?, nome_correntista = ?, cf_correntista = ?, " +
			"specie_allev = ?, codice1 = ?, codice2 = ?, codice3 = ?, codice4 = ?, codice5 = ?, codice6 = ?, codice7 = ?, codice8 = ?, codice9 = ?, codice10 = ?, " +
			"tipo_stab = ?, categoria = ?, impianto = ?, stato_lab = ?, numaut = ?, codice_impianto = ?,titolo_rappresentante = ?, " +
			"codice_fiscale_rappresentante = ?, nome_rappresentante = ?, cognome_rappresentante = ?, email_rappresentante = ?, telefono_rappresentante = ?,  " +
			"data_nascita_rappresentante =?, luogo_nascita_rappresentante = ?,progressivo_stabilimento = ?");
	//fine campi nuovi

    sql.append("WHERE org_id = ? ");
   

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, industry);
    pst.setString(++i, url);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, employees);
    pst.setDouble(++i, revenue);
    pst.setString(++i, ticker);
    pst.setString(++i, accountNumber);
    if (owner > -1) {
      pst.setInt(++i, this.getOwner());
    }
    pst.setInt(++i, this.getDuplicateId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractEndDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate1());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate2());
    pst.setString(++i, this.getContractEndDateTimeZone());
    DatabaseUtils.setTimestamp(pst, ++i, this.getAlertDate());
    pst.setString(++i, this.getAlertDateTimeZone());
    pst.setString(++i, alertText);
    pst.setString(++i, this.getNameSalutation());
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameMiddle);
    pst.setString(++i, nameLast);
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    DatabaseUtils.setInt(pst, ++i, segmentId);
    pst.setBoolean(++i, directBill);
    DatabaseUtils.setInt(pst, ++i, accountSize);
    DatabaseUtils.setInt(pst, ++i, siteId);
    DatabaseUtils.setInt(pst, ++i, subSegmentId);
    DatabaseUtils.setInt(pst, ++i, this.getSource());
    DatabaseUtils.setInt(pst, ++i, this.getRating());
    pst.setDouble(++i, this.getPotential());
    pst.setString(++i, this.getDunsType());
    pst.setString(++i, this.getDunsNumber());
    pst.setString(++i, this.getBusinessNameTwo());
    DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
    DatabaseUtils.setInt(pst, ++i, this.getSicCode());
    pst.setString(++i, this.getSicDescription());
    DatabaseUtils.setInt(pst, ++i, stageId);
    
    //campi nuovi
	pst.setString(++i, this.getPartitaIva());
    pst.setString(++i, this.getCodiceFiscale());
  	pst.setString(++i, this.getAbi());
  	pst.setString(++i, this.getCab());
  	pst.setString(++i, this.getCin());
  	pst.setString(++i, this.getBanca());
  	pst.setString(++i, this.getContoCorrente());  
  	pst.setString(++i, this.getNomeCorrentista());   
  	pst.setString(++i, this.getCodiceFiscaleCorrentista()); 
  	pst.setString(++i, specie_allev);
  	pst.setString(++i, this.getCodice1()); 
  	pst.setString(++i, this.getCodice2()); 
  	pst.setString(++i, this.getCodice3()); 
  	pst.setString(++i, this.getCodice4()); 
  	pst.setString(++i, this.getCodice5()); 
  	pst.setString(++i, this.getCodice6()); 
  	pst.setString(++i, this.getCodice7()); 
  	pst.setString(++i, this.getCodice8()); 
  	pst.setString(++i, this.getCodice9()); 
  	pst.setString(++i, this.getCodice10()); 
  	pst.setString(++i, this.getTipoStab());
  	pst.setString(++i, this.getCategoria()); 
    DatabaseUtils.setInt(pst, ++i, impianto);
    DatabaseUtils.setInt(pst, ++i, statoLab);
  	pst.setString(++i, this.getNumAut()); 
  	pst.setString(++i, this.getCodiceImpianto()); 
    //fine campi nuovi    
  	DatabaseUtils.setInt(pst, ++i, this.getTitoloRappresentante());
  	pst.setString(++i, this.getCodiceFiscaleRappresentante()); 
  	pst.setString(++i, this.getNomeRappresentante());
  	pst.setString(++i, this.getCognomeRappresentante());
  	pst.setString(++i, this.getEmailRappresentante()); 
	pst.setString(++i, this.getTelefonoRappresentante()); 
	DatabaseUtils.setTimestamp(pst, ++i, this.getDataNascitaRappresentante());
	pst.setString(++i, this.getLuogoNascitaRappresentante()); 
	pst.setInt(++i, progressivo_soa);
    
    pst.setInt(++i, orgId);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
 	  
    resultCount = pst.executeUpdate();
    pst.close();

    // When an account name gets updated,
    // the stored org_name in contact needs to be updated
    pst = db.prepareStatement(
        "UPDATE contact " +
        "SET org_name = ? " +
        "WHERE org_id = ? " +
        "AND org_name NOT LIKE ? ");
    pst.setString(1, name);
    pst.setInt(2, orgId);
    pst.setString(3, name);
    pst.executeUpdate();
    pst.close();

    //Remove all account types, add new list
    if (!this.getMinerOnly() && typeList != null) {
      resetType(db);
      int lvlcount = 0;
      for (int k = 0; k < typeList.size(); k++) {
        String val = (String) typeList.get(k);
        if (val != null && !(val.equals("")) && !(val.equals("-1"))) {
          int type_id = Integer.parseInt((String) typeList.get(k));
          lvlcount++;
          insertType(db, type_id, lvlcount);
        } else {
          lvlcount--;
        }
      }
    }
    return resultCount;
  }


  /**
   *  Renames the organization running this database
   *
   * @param  db             Description of the Parameter
   * @param  newName        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void renameMyCompany(Connection db, String newName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization " +
        "SET name = ? " +
        "WHERE org_id = 0 ");
    pst.setString(1, newName);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseFilePath   Description of Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      Statement st = db.createStatement();
      st.executeUpdate(
          "update organization set trashed_date = current_date WHERE org_id = " + this.getOrgId());
     
      st.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
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


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void deleteMinerOnly(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("The Organization could not be found.");
    }
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM news WHERE org_id = " + this.getOrgId());
      st.close();
      
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE org_id = ? " +
          "AND miner_only = ? ");
      pst.setInt(1, this.getOrgId());
      pst.setBoolean(2, true);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
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
  }


  /**
   *  Approves all records for a specific import
   *
   * @param  db             Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  status         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int updateImportStatus(Connection db, int importId, int status) throws SQLException {
    int count = 0;
    boolean commit = true;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      String sql = "UPDATE organization " +
          "SET status_id = ? " +
          "WHERE import_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, status);
      pst.setInt(++i, importId);
      count = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
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
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  toTrash        Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
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



  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  thisImportId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization_emailaddress " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_emailaddress.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_phone " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_phone.org_id)");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_address " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_address.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE import_id = ?");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
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


  /**
   *  Checks to see if the this account has any associated contacts
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean hasContacts(Connection db) throws SQLException {
    int recordCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as recordcount " +
        "FROM contact " +
        "WHERE org_id = ? ");
    pst.setInt(1, this.getOrgId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("recordCount");
    }
    rs.close();
    pst.close();
    if (recordCount > 0) {
      return true;
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public String toString() {
    StringBuffer out = new StringBuffer();
    out.append("=================================================\r\n");
    out.append("Organization Name: " + this.getName() + "\r\n");
    out.append("ID: " + this.getOrgId() + "\r\n");
    return out.toString();
  }


  /**
	 *  Description of the Method
	 *
	 * @param  rs             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		//organization table
		dataPresentazione = rs.getTimestamp("datapresentazione");
		idControlloDocumentale = rs.getInt("id_controllo_documentale");
		prov_legale_rapp = rs.getString("prov_legale_rapp");
		address_legale_rapp = rs.getString("address_legale_rapp");
		dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
		city_legale_rapp = rs.getString("city_legale_rapp");
		fax = rs.getString("fax");
		tipoSoa = rs.getInt("tipo_soa");
		statoIstruttoria = rs.getInt("stato_istruttoria") ;
		categoriaRischio=rs.getInt("categoria_rischio");
		data_assegnazione_approval_number = rs.getTimestamp("data_assegnazione_approval_number");
		//codiceAteco = rs.getString("codice_ateco");
		categoria_precedente=rs.getInt("categoria_precedente");
		this.setOrgId(rs.getInt("org_id"));
		name = rs.getString("name");
		accountNumber = rs.getString("account_number");
		revenue = rs.getDouble("revenue");
		notes = rs.getString("notes");
		data_prossimo_controllo = rs.getTimestamp("prossimo_controllo");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		enabled = rs.getBoolean("enabled");
		contractEndDate = rs.getTimestamp("contract_end");
		date1 = rs.getTimestamp("date1");
		date2 = rs.getTimestamp("date2");
		alertDate = rs.getTimestamp("alertdate");
		alertText = rs.getString("alert");
		nameSalutation = rs.getString("namesalutation");
		alertDateTimeZone = rs.getString("alertdate_timezone");
		contractEndDateTimeZone = rs.getString("contract_end_timezone");
		trashedDate = rs.getTimestamp("trashed_date");
		directBill = rs.getBoolean("direct_bill");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		statoIstruttoria = rs.getInt("stato_istruttoria");
		city=rs.getString("o_city");
		state=rs.getString("o_state");
		postalCode=rs.getString("o_postalcode");
		partitaIva = rs.getString("partita_iva");
		codiceFiscale = rs.getString("codice_fiscale");
		banca = rs.getString("banca");
		contoCorrente = rs.getString("conto_corrente");
		nomeCorrentista = rs.getString("nome_correntista");
		codiceFiscaleCorrentista = rs.getString("cf_correntista");
		specie_allev = rs.getString("specie_allev");
		orientamento_prod = rs.getString("orientamento_prod");
		tipologia_strutt = rs.getString("tipologia_strutt");
		tipologia=rs.getInt("tipologia");
		numero_capi = rs.getString("numero_capi");
		tipo_stab = rs.getString("tipo_stab");
		categoria = rs.getString("categoria");
		impianto = DatabaseUtils.getInt(rs, "impianto");
		statoLab = DatabaseUtils.getInt(rs, "stato_lab");
		numAut = rs.getString("numaut");
		codiceImpianto = rs.getString("codice_impianto");
		//fine campi nuovi
		titoloRappresentante = rs.getInt("titolo_rappresentante");
		codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
		nomeRappresentante = rs.getString("nome_rappresentante");
		cognomeRappresentante = rs.getString("cognome_rappresentante");
		nomeRappresentante = rs.getString("nome_rappresentante");
		emailRappresentante = rs.getString("email_rappresentante");
		telefonoRappresentante = rs.getString("telefono_rappresentante");
		dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
		luogoNascitaRappresentante = rs.getString("luogo_nascita_rappresentante");

	}

	public void completaIstruttoria(Connection db) throws SQLException
	{
		try
		{
			String update = "update organization set stato_istruttoria = ? ,notes=?, namemiddle = ?, date1= ? ,date3 =? where org_id = ? ";
			PreparedStatement pst = db.prepareStatement(update);
			pst.setInt(1, statoIstruttoria);
			pst.setString(2, notes);
			pst.setString(3, nameMiddle);
			pst.setTimestamp(4, date1);
			pst.setTimestamp(5, date1);
			pst.setInt(6, orgId);
			pst.execute();
		}
		catch(SQLException e)
		{
			throw e ;
		}
	}


	public void attribuisciApprovalNumbrer(Connection db) throws SQLException
	{
		try
		{
			String update = "update organization set numaut = ? , stato_istruttoria = ? , data_assegnazione_approval_number = ? where org_id = ? ";
			PreparedStatement pst = db.prepareStatement(update);
			pst.setString(1, numAut);
			pst.setInt(2, statoIstruttoria);
			pst.setTimestamp(3, data_assegnazione_approval_number);
			pst.setInt(4, orgId);
			pst.execute();
		}
		catch(SQLException e)
		{
			throw e ;
		}
	}

	public void salvaDefinitiva(Connection db) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		try
		{
			String update = "update organization set stato_istruttoria = ? ,stato_lab = 0  where org_id = ? ";
			PreparedStatement pst = db.prepareStatement(update);

			pst.setInt(1, statoIstruttoria);
			pst.setInt(2, orgId);
			pst.execute();

			Iterator saiterator = getSottoattivitaList().iterator();
			while (saiterator.hasNext()) {
				SottoAttivita thisSa = (SottoAttivita) saiterator.next();
				thisSa.setId_soa(orgId);
				thisSa.setTipo_autorizzazione(1);
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisSa.update(db);
			}
		}
		catch(SQLException e)
		{
			throw e ;
		}
	}


  /**
   *  Gets the nameLastFirstMiddle attribute of the Organization object
   *
   * @return    The nameLastFirstMiddle value
   */
  public String getNameLastFirstMiddle() {
    StringBuffer out = new StringBuffer();
    if (nameLast != null && nameLast.trim().length() > 0) {
      out.append(nameLast);
    }
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      if (nameLast.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }
    if (nameMiddle != null && nameMiddle.trim().length() > 0) {
      if (nameMiddle.length() > 0) {
        out.append(" ");
      }
      out.append(nameMiddle);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }


  /**
   *  Gets the properties that are TimeZone sensitive for a Call
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("alertDate");
    thisList.add("contractEndDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the Organization class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("revenue");
    thisList.add("potential");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int countEmployees(Connection db, int id) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement("SELECT employees FROM organization WHERE org_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("employees");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  employeeCount     Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateEmployeeCount(Connection db, int id, int employeeCount) throws SQLException {
    StringBuffer sql = new StringBuffer();
    if (employeeCount != -1) {
      sql.append("UPDATE organization SET employees = ? WHERE org_id = ? AND employees <> ? ");
    } else {
      sql.append("UPDATE organization SET employees = ? WHERE org_id = ? AND employees IS NOT NULL ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, 1, employeeCount);
    pst.setInt(2, id);
    if (employeeCount != -1) {
      pst.setInt(3, employeeCount);
    }
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  typeId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean hasType(int typeId) {
    if (types != null) {
      Iterator i = types.iterator();
      while (i.hasNext()) {
        LookupElement element = (LookupElement) i.next();
        if (element.getCode() == typeId) {
          return true;
        }
      }
    }
    return false;
  }


  public void updateStatoLab(int idStato,Connection db) throws SQLException
	{
		try
		{
			db.prepareStatement("update organization set stato_lab = "+idStato + " where org_id = "+this.getOrgId()).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}

	}

  public int getLivelloRischio(Connection db) throws SQLException {
	    int livelloRischio = -1;
	    PreparedStatement pst = db.prepareStatement("SELECT livello_rischio FROM audit WHERE org_id = ? ORDER BY data_1 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      livelloRischio = rs.getInt("livello_rischio");
	    }
	    rs.close();
	    pst.close();
	    return livelloRischio;
}
  
  public int getIdImpianto(String attivita,Connection db) throws SQLException {
	    int livelloRischio = -1;
	    PreparedStatement pst = db.prepareStatement("SELECT code FROM  lookup_impianto_soa WHERE description=?");
	    pst.setString(1, attivita);
	    ResultSet rs = pst.executeQuery();
	   int idImpianto=-1;
	    if (rs.next()) {
	      idImpianto = rs.getInt("code");
	    }
	    rs.close();
	    pst.close();
	    return idImpianto;
}
  
  
  public int getLivelloRischioFinale(Connection db) throws SQLException {
	    int livelloRischio = -1;
	    PreparedStatement pst = db.prepareStatement("SELECT livello_rischio_finale FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      livelloRischio = rs.getInt("livello_rischio_finale");
	    }
	    rs.close();
	    pst.close();
	    return livelloRischio;
}

public Timestamp getDataAudit(Connection db) throws SQLException {
	  	Timestamp data = null;
	    PreparedStatement pst = db.prepareStatement("SELECT data_2 FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      data = rs.getTimestamp("data_2");
	    }
	    rs.close();
	    pst.close();
	    return data;
}

public void setComuni (Connection db, int codeUser) throws SQLException {

	Statement st = null;
	ResultSet rs = null;
	StringBuffer sql = new StringBuffer();

	//sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");

	sql.append("select comune from comuni where codiceistatasl in (select codiceistat from lookup_site_id where 1=1");

	if(codeUser!=-1 && codeUser!=0)
		sql.append("  and code= "+ codeUser + ")");
	else
		sql.append(")");
	//sql.append("select comune from comuni");

	st = db.createStatement();
	rs = st.executeQuery(sql.toString());

	while (rs.next()) {
		comuni.add(rs.getString("comune"));
	}
	rs.close();
	st.close();

}
public int updateVoltura(Connection db, int org_id) throws SQLException{
	
	int result = 0;
	PreparedStatement pst=db.prepareStatement("UPDATE organization set name = ?, datapresentazione=?, banca = ?, partita_iva = ?, codice_fiscale_rappresentante = ?, nome_rappresentante = ?, cognome_rappresentante = ?, email_rappresentante = ?, telefono_rappresentante = ?, data_nascita_rappresentante =?, luogo_nascita_rappresentante = ?, fax = ? where org_id = ?");
	pst.setString(1, this.getName());
	DatabaseUtils.setTimestamp(pst, 2, this.getDataPresentazione());
	pst.setString(3, this.getBanca());
	pst.setString(4, this.getPartitaIva());
	pst.setString(5, this.getCodiceFiscaleRappresentante());
	pst.setString(6, this.getNomeRappresentante());
	pst.setString(7, this.getCognomeRappresentante());
	pst.setString(8, this.getEmailRappresentante());
	pst.setString(9, this.getTelefonoRappresentante());
	DatabaseUtils.setTimestamp(pst, 10, this.getDataNascitaRappresentante());
	pst.setString(11, this.getLuogoNascitaRappresentante());
	pst.setString(12, this.getFax());
	pst.setInt(13, org_id);
	result = pst.executeUpdate();
	return result;
}

public String getIdVoltura(Connection db,int org_id){
	
	String idVoltura= null;
	try{
		
		String sql="select max(ticketid) as ticketid from ticket where org_id=? and tipologia = 4 and trashed_Date is null";
		java.sql.PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, org_id);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			
			idVoltura = rs.getString("ticketid");
			
		}
		pst.close();
		rs.close();
	}catch(Exception e){
		
		e.printStackTrace();
	}
	return idVoltura;
	
	
}
  
}

