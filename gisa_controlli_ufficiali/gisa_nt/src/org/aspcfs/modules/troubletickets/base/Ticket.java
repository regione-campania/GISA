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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webdav.utils.ICalendar;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.UserGroupList;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.bloccocu.base.EventoBloccoCu;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.campioni.base.TicketList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.TicketEventList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.HashMap;

/**
 *  Represents a Ticket
 *
 * @author     chris
 * @created    November 8, 2001
 * @version    $Id: Ticket.java 14021 2006-01-25 15:15:37 -0500 (Wed, 25 Jan
 *      2006) partha@darkhorseventures.com $
 */
public class Ticket extends GenericBean {

	public static final int TIPO_NON_CONFORMITA_A_CARICO =8 ;
	public static final int TIPO_NON_CONFORMITA_NON_A_CARICO =10 ;
	
	
	
	public static final int TIPO_ARCHIVIATI 					= 1011 		;
	public static final int TIPO_IMPRESE 					= 1 		;
	public static final int TIPO_ALLEVAMENTI 				= 2 		;
	public static final int TIPO_ALLEVAMENTI_SIMILOPU 				= 222 		;

	public static final int TIPO_STABILIMENTI 				= 3 		;
	public static final int TIPO_OPERATORI_FR 				= 22 		;
	public static final int TIPO_PERATORI_PRIV				= 13 		;
	public static final int TIPO_FARMACOSORVEGLIANZA		= 151 		;
	public static final int TIPO_COLONIE 					= 16		;
	public static final int TIPO_OPU					=999		;
	public static final int TIPO_OPU_RICHIESTE					=1001		;
	public static final int TIPO_STABILIMENTI_ARCHIVIATI					=1999		;
	public static final int TIPO_SINTESIS					=2000		;
	public static final int TIPO_NOSCIA						= 2001;


	public static final int TIPO_API					=1000		;
	public static final int TIPO_API_ATTIVITA					=1002		;
	private int idDistributore ;
	public static final int TIPO_ABUSIVI					= 4 		;
	public static final int TIPO_SOA						= 97 		;
	public static final int TIPO_OSM						= 800 		;
	public static final int TIPO_OSM_REG					= 801 		;
	public static final int TIPO_TRASPORTI					= 9 		;
	public static final int TIPO_CANILI						= 10 		;
	public static final int TIPO_ACQUE_DI_RETE 			    = 14 		;
	public static final int TIPO_OPERATORI_COMMERCIALI		= 20 		;
	public static final int TIPO_CANIPADRONALI				= 255 		;
	public static final int TIPO_PUNTI_DI_SBARCO			= 5 		;
	public static final int TIPO_ZONECONTROLLO			= 15 		;
	public static final int TIPO_IMBARCAZIONE			= 17 		;

	public static final int TIPO_AZIENDE_AGRICOLE			= 7 		;
	public static final int TIPO_RIPRODUZIONE_ANIMALE		= 8 		;
	public static final int LABORATORI_HACCP				= 152 		;
	public static final int OPERATORI_SPERIM_ANIMALI		= 850 		;
	public static final int PARAFARMACIE					= 802 		;
	public static final int TIPO_STRUTTURA_ASL				= 6		;
	public static final int TIPO_OPERATORI_NON_ALTROVE		= 12	;
	public static final int TIPO_OPERATORI_MOLLUSCHIBIVALVI = 201	;
	public static final int TIPO_NUOVA_ANAGRAFICA					=3000		;

	
	public static final int ALT_ORGANIZATION 					= 1 		;
	public static final int ALT_OPU 					= 2 		;
	public static final int ALT_APIARI 					= 3 		;
	public static final int ALT_OPU_RICHIESTE 					= 4 		;
	public static final int ALT_SINTESIS 					= 6 		;
	public static final int ALT_ANAGRAFICA_IMPRESE				= 7 		;
	public static final int ALT_ANAGRAFICA_STABILIMENTI					= 8 		;

	
	private boolean flag_posticipato;
	private boolean flag_campione_non_conforme;
	
	
	
	
	
	
	public boolean isFlag_posticipato() {
		return flag_posticipato;
	}

	public void setFlag_posticipato(boolean flag_posticipato) {
		this.flag_posticipato = flag_posticipato;
	}

	public boolean isFlag_campione_non_conforme() {
		return flag_campione_non_conforme;
	}

	public void setFlag_campione_non_conforme(boolean flag_campione_non_conforme) {
		this.flag_campione_non_conforme = flag_campione_non_conforme;
	}

	public int getIdDistributore() {
		return idDistributore;
	}

	public void setIdDistributore(int idDistributore) {
		this.idDistributore = idDistributore;
	}

	protected int idApiario ;

	private String ipEntered ;
	private String ipModified ;
	;
	public boolean checkMatriciCanili = false;
	public String descrizioneTipoMatriciCanili = "";
	public int tipoMatriciCanili = -1;
	/**
	 * 
	 */
	
	public boolean flagBloccoCu ;
	public boolean flagBloccoNonConformita ;
	public boolean flagBloccoNonConformitaContoTerzi ;
	public boolean flagPregresso ;
	private static final long serialVersionUID = 7992684443554149703L;
	private boolean animaliNonAlimentari = false;
	private int animaliNonAlimentariCombo;
	protected String errorMessage = "";
	protected int id = -1;
	protected int orgId = -1;
	protected java.sql.Timestamp contractStartDate = null;
	protected java.sql.Timestamp contractEndDate = null;
	protected int assetId = -1;
	protected String assetSerialNumber = null;
	protected int assetManufacturerCode = -1;
	protected int assetVendorCode = -1;
	protected String assetModelVersion = null;
	protected String assetLocation = null;
	protected int assetOnsiteResponseModel = -1;
	protected int contactId = -1;
	protected java.sql.Timestamp assignedDate = null;
	protected java.sql.Timestamp dataFineControllo = null;
	protected java.sql.Timestamp followupDate = null;
	protected String problem = "";
	//R.M
	protected String tipo_esame = "";
	protected String location = null;
	protected String location_new = null;
	protected String comment = "";
	protected java.sql.Timestamp estimatedResolutionDate = null;
	protected String estimatedResolutionDateTimeZone = null;
	protected String cause = null;
	protected String solution = "";
	
	protected java.sql.Timestamp resolutionDate = null;
	protected int enteredBy = -1;
	protected int modifiedBy = -1;
	protected int resolvedBy = -1;
	protected java.sql.Timestamp entered = null;
	protected java.sql.Timestamp modified = null;
	protected java.sql.Timestamp closed = null;
	protected int statusId = -1; 
	protected String assignedDateTimeZone = null;
	protected String followupDateTimeZone = null;
	protected String resolutionDateTimeZone = null;
	protected java.sql.Timestamp trashedDate = null;
	protected java.sql.Timestamp dataChiusura = null;
	protected int causeId = -1;
	protected Timestamp dataSintomi;
	protected Timestamp dataPasto ;
	protected String dataSintomiTimeZone = null;
	protected String dataPastoTimeZone = null;
	protected int tipologia_operatore ;
	protected String motivo_cancellazione_allerta ;
	private String inserita_da ;
	private String modificata_da ;
	private String permission_ticket ;
	private String url_checklist ;
	protected String dataddtTimeZone = null;

	
	protected boolean chiusura_attesa_esito ;				// se settati a truce indica che il controllo si trova in uno stato di 
	// chiusura temporaneo e non e' possibile modificarlo e 
	// modificare le nc si puo solo inserire l'esito per campioni e tamponi
	protected Timestamp data_chiusura_attesa_esito ;

	protected String noteBlocco ;
	
	public int getIdApiario() {
		return idApiario;
	}

	public void setIdApiario(int idApiario) {
		this.idApiario = idApiario;
	}

	/**
	 * togliere il controllo su asl
	 * @return
	 */
	public boolean isflagBloccoCu() {
		
		return flagBloccoCu;
	}

	protected void setflagBloccoCu(boolean flagBloccoCu) {
		this.flagBloccoCu = flagBloccoCu;
	}
	
	public boolean isflagPregresso() {
		return flagPregresso;
	}

	protected void setflagPregresso(boolean flagPregresso) {
		this.flagPregresso = flagPregresso;
	}
	
	
	  public boolean isFlagBloccoNonConformita() {
		return flagBloccoNonConformita;
	}

	public void setFlagBloccoNonConformita(boolean flagBloccoNonConformita) {
		this.flagBloccoNonConformita = flagBloccoNonConformita;
	}

	
	
	
	public boolean isFlagBloccoNonConformitaContoTerzi() {
		return flagBloccoNonConformitaContoTerzi;
	}

	public void setFlagBloccoNonConformitaContoTerzi(boolean flagBloccoNonConformitaContoTerzi) {
		this.flagBloccoNonConformitaContoTerzi = flagBloccoNonConformitaContoTerzi;
	}

	public void setFlagBloccoNonConformitaContoTerzi(Connection db,int idControllo) throws SQLException
	{
		boolean flagBloccoNc = false ;
		String sql ="select * from controlli_verfica_blocco_non_conformita_carico_terzi(?)";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rs = pst.executeQuery();
		if ( rs.next())
			flagBloccoNonConformitaContoTerzi=rs.getBoolean(1);
		
		
	}
	
	public void setFlagBloccoNonConformita(Connection db,int idControllo) throws SQLException
	{
		boolean flagBloccoNc = false ;
		String sql ="select * from controlli_verfica_blocco_non_conformita(?)";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rs = pst.executeQuery();
		if ( rs.next())
			flagBloccoNonConformita=rs.getBoolean(1);
		
		
	}
	public  String getPrefissoAction(String actionName)
		{
			
//			String uri = request.getRequestURI();
//			String action = uri.substring(0, uri.lastIndexOf('/'));
			String action = "" ;
			int idNorma = -1 ;
			if (actionName.contains("AziendeAgricoleOpuStab"))
				idNorma = LineaProduttiva.NORMA_AZIENDE_AGRICOLE;
			else
				if (actionName.contains("Stabilimenti852") || actionName.contains("Distributori852"))
					idNorma = LineaProduttiva.NORMA_STABILIMENTI_852;
			else
				if (actionName.contains("suap.actions.AccountVigilanza"))
						idNorma = LineaProduttiva.NORMA_OPU_RICHIESTE;
			else
				if (actionName.contains("sintesis.actions.AccountVigilanza"))
						idNorma = LineaProduttiva.NORMA_SINTESIS;
			else
				if (actionName.contains("apicolture.actions.AccountVigilanza"))
						idNorma = LineaProduttiva.NORMA_APICOLTURA;
			switch(idNorma)
			{
			case 1 : 
			{
				
					action = "Stabilimenti852" ;
				
				break ;
			}
			case 7 : 
				
			{
				action = "OpuStab" ;
				break ;
			}
			case 1007 : 
				
			{
				action = "GisaSuapStab" ;
				break ;
			}
			case 2000 : 
				
			{
				action = "StabilimentoSintesisAction" ;
				break ;
			}
			case 17 : 
				
			{
				action = "ApicolturaApiari" ;
				break ;
			}
			default : 
			{
				action = "OpuStab" ;
				break ;
			}
			}
			return action ;
		}
	  
	public void controlloBloccoCu(Connection db,int idcontrollo)
	{
		try
		{
			Timestamp dataControllo =null;
			PreparedStatement pst = db.prepareStatement("select assigned_date from ticket where ticketid = ? ");
			pst.setInt(1,idcontrollo);
			ResultSet rs =pst.executeQuery();
			if (rs.next())
				dataControllo = rs.getTimestamp(1);
			
			if (dataControllo!=null){
			Calendar cal=GregorianCalendar.getInstance();
		    cal.setTime(new Date (dataControllo.getTime()));
			int annoControllo =  cal.get(Calendar.YEAR);
			
			Timestamp data_attuale_time = new Timestamp(System.currentTimeMillis());
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno_corrente = calCorrente.get(Calendar.YEAR);
			
			Calendar calCorrente2 = GregorianCalendar.getInstance();
			Timestamp data_attuale_time2= new Timestamp(System.currentTimeMillis());
			calCorrente2.setTime(new Timestamp(data_attuale_time2.getTime()));
			int anno_corrente2 = calCorrente2.get(Calendar.YEAR);
			
			//Controllo dell'anno n-1
			
			if (idcontrollo<10000000)
				if (annoControllo != anno_corrente )
				{
					
					this.flagBloccoCu = true ;
					this.flagPregresso = true;
					if (annoControllo>anno_corrente)
						noteBlocco = ApplicationProperties.getProperty("BLOCCO4_MESSAGGIO");
					else
					noteBlocco = ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO");
				}
			
			
		
//				else
//				{
//				this.flagBloccoCu = true ;
//				this.flagPregresso = true;
//				noteBlocco = ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO");
//				}
			
			
			EventoBloccoCu blocco = new EventoBloccoCu();
			blocco.queryRecord(db);
			
//			if (annoControllo == anno_corrente2 && blocco.getData_sblocco()!= null && dataControllo.compareTo(blocco.getData_sblocco())<=0)
//			{
//				this.flagBloccoCu = true ; 
//				noteBlocco = ApplicationProperties.getProperty("BLOCCO2_MESSAGGIO")+" dal "+blocco.getData_bloccoString()+ " al "+blocco.getData_sbloccoString()+ " "+ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO");
//			}
			
			String tipoBlocco = blocco.getTipoBlocco();
			if (annoControllo == anno_corrente2 && blocco.getData_sblocco()!= null 
					&& 
					 ( (tipoBlocco.equalsIgnoreCase("IN")  && dataControllo.compareTo(blocco.getData_sblocco())<=0 ) && (idcontrollo<10000000) )
					
					)
			{
				
				this.flagBloccoCu = true ;
				//messaggio3 = "CONTROLLO NON ACCETTATO! PRESENTE BLOCCO AL "+blocco.getData_sblocco();
				noteBlocco = ApplicationProperties.getProperty("BLOCCO2_MESSAGGIO")+" dal "+blocco.getData_bloccoString()+ " al "+blocco.getData_sbloccoString()+" "+
				 ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO");
			}
			else
			{
				if (annoControllo == anno_corrente2 && blocco.getData_sblocco()!= null 
						&& 
						 ( (tipoBlocco.equalsIgnoreCase("OUT")  && dataControllo.compareTo(blocco.getData_sblocco())>0 ) )
						&& (idcontrollo<10000000)
						)
				{
					
					this.flagBloccoCu = true ;
					//messaggio3 = "CONTROLLO NON ACCETTATO! PRESENTE BLOCCO AL "+blocco.getData_sblocco();
					noteBlocco = ApplicationProperties.getProperty("BLOCCO3_MESSAGGIO")+" dal "+blocco.getData_bloccoString()+ " al "+blocco.getData_sbloccoString()+" "+
					 ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO");
				}
				
			}
			
			
		} }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	

	public String getNoteBlocco() {
		return noteBlocco;
	}

	public void setNoteBlocco(String noteBlocco) {
		this.noteBlocco = noteBlocco;
	}



	protected boolean controllo_chiudibile ;			// se settato a true il controllo puo essere chiuso in maniera normale
// se settato a false il controllo puo essere chiuso in maniera temporanea
// poiche' e in attesa di esito di campioni e/o tamponi
	protected String microchip;
	
	private int idOpuOperatore ;
	

	public int getIdOpuOperatore() {
		return idOpuOperatore;
	}

	public void setIdOpuOperatore(int idOpuOperatore) {
		this.idOpuOperatore = idOpuOperatore;
	}
	
	public String getUrl_checklist() {
		return url_checklist;
	}

	public void setUrl_checklist(String url_checklist) {
		this.url_checklist = url_checklist;
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
public boolean isChiusura_attesa_esito() {
	return chiusura_attesa_esito;
}


public boolean isControllo_chiudibile() {
	return controllo_chiudibile;
}


/**
 *  Sets the assignedDateTimeZone attribute of the Ticket object
 *
 * @param  tmp  The new assignedDateTimeZone value
 */
public void setAssignedDateTimeZone(String tmp) {
	this.assignedDateTimeZone = tmp;
}

public void setDataDdtTimeZone(String tmp) {
	this.dataddtTimeZone = tmp;
}

public void setControllo_chiudibile(boolean controllo_chiudibile) {
	this.controllo_chiudibile = controllo_chiudibile;
}


public void setChiusura_attesa_esito(boolean chiusura_attesa_esito) {
	this.chiusura_attesa_esito = chiusura_attesa_esito;
}


public Timestamp getData_chiusura_attesa_esito() {
	return data_chiusura_attesa_esito;
}




public void setData_chiusura_attesa_esito(Timestamp data_chiusura_attesa_esito) {
	this.data_chiusura_attesa_esito = data_chiusura_attesa_esito;
}

	
	public String getPermission_ticket() {
		return permission_ticket;
	}


	public void setPermission_ticket(String permission_ticket) {
		this.permission_ticket = permission_ticket;
	}


	public String getTipo_esame() {
		return tipo_esame;
	}

	public void setTipo_esame(String tipo_esame) {
		this.tipo_esame = tipo_esame;
	}





	public boolean isCheckMatriciCanili() {
		return checkMatriciCanili;
	}


	public void setCheckMatriciCanili(boolean checkMatriciCanili) {
		this.checkMatriciCanili = checkMatriciCanili;
	}


	public String getDescrizioneTipoMatriciCanili() {
		return descrizioneTipoMatriciCanili;
	}


	public void setDescrizioneTipoMatriciCanili(String descrizioneTipoMatriciCanili) {
		this.descrizioneTipoMatriciCanili = descrizioneTipoMatriciCanili;
	}


	public int getTipoMatriciCanili() {
		return tipoMatriciCanili;
	}


	public void setTipoMatriciCanili(int tipoMatriciCanili) {
		this.tipoMatriciCanili = tipoMatriciCanili;
	}


	public String getIpEntered() {
		return ipEntered;
	}


	public void setIpEntered(String ipEntered) {
		this.ipEntered = ipEntered;
	}


	public String getIpModified() {
		return ipModified;
	}


	public void setIpModified(String ipModified) {
		this.ipModified = ipModified;
	}
public void setUo(String uo){}
public void setUo(String[] uo){}

	public String getInserita_da() {
		return inserita_da;
	}


	public void setInserita_da(String inserita_da) {
		this.inserita_da = inserita_da;
	}


	public String getModificata_da() {
		return modificata_da;
	}


	public void setModificata_da(String modificata_da) {
		this.modificata_da = modificata_da;
	}


	public String getMotivo_cancellazione_allerta() {
		return motivo_cancellazione_allerta;
	}


	public void setMotivo_cancellazione_allerta(String motivo_cancellazione_allerta) {
		this.motivo_cancellazione_allerta = motivo_cancellazione_allerta;
	}


	public int getTipologia_operatore() {
		return tipologia_operatore;
	}


	public void setTipologia_operatore(int tipologia_operatore) {
		this.tipologia_operatore = tipologia_operatore;
	}


	public String getDataSintomiTimeZone() {
		return dataSintomiTimeZone;
	}


	public void setDataSintomiTimeZone(String dataSintomiTimeZone) {
		this.dataSintomiTimeZone = dataSintomiTimeZone;
	}


	public String getDataPastoTimeZone() {
		return dataPastoTimeZone;
	}


	public void setDataPastoTimeZone(String dataPastoTimeZone) {
		this.dataPastoTimeZone = dataPastoTimeZone;
	}





	public java.sql.Timestamp getFollowupDate() {
		return followupDate;
	}


	public void setFollowupDate(String followupDate) {

		this.followupDate = DateUtils.parseDateStringNew(followupDate, "dd/MM/yyyy");
		this.followupDateTimeZone=followupDate;
	}


	public String getFollowupDateTimeZone() {
		return followupDateTimeZone;
	}


	public void setFollowupDateTimeZone(String followupDateTimeZone) {
		this.followupDateTimeZone = followupDateTimeZone;
	}



	public java.sql.Timestamp getDataPasto() {
		return dataPasto;
	}


	public void setDataPasto(java.sql.Timestamp dataPasto) {
		this.dataPasto = dataPasto;
	}


	public java.sql.Timestamp getDataSintomi() {
		return dataSintomi;
	}


	public void setDataSintomi(java.sql.Timestamp dataSintomi) {
		this.dataSintomi = dataSintomi;
	}


	protected int resolutionId = -1;
	protected int stateId = -1;
	protected int siteId = -1;

	//Related descriptions
	protected String companyName = "";
	protected String resolvedByDeptName = "";
	protected int orgSiteId = -1;

	
	protected FileItemList files = new FileItemList();

	protected SystemStatus systemStatus = null;
	

	/**
	 *  Constructor for the Ticket object, creates an empty Ticket
	 *
	 * @since    1.0
	 */
	public Ticket() { }


	/**
	 *  Constructor for the Ticket object
	 *
	 * @param  rs                Description of Parameter
	 * @exception  SQLException  Description of the Exception
	 * @throws  SQLException     Description of the Exception
	 * @throws  SQLException     Description of Exception
	 */
	public Ticket(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db                Description of Parameter
	 * @param  id                Description of Parameter
	 * @exception  SQLException  Description of the Exception
	 * @throws  SQLException     Description of the Exception
	 * @throws  SQLException     Description of Exception
	 */
	public Ticket(Connection db, int id) throws SQLException {
		queryRecord(db, id);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  id             Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		PreparedStatement pst = db.prepareStatement(
				"SELECT t.*, " +
				"o.name AS orgname, " +
				"o.enabled AS orgenabled, " +
				"o.site_id AS orgsiteid, " +
				
				"a.serial_number AS serialnumber, " +
				"a.manufacturer_code AS assetmanufacturercode, " +
				"a.vendor_code AS assetvendorcode, " +
				"a.model_version AS modelversion, " +
				"a.location AS assetlocation, " +
				"a.onsite_service_model AS assetonsiteservicemodel " +
				
				
				"FROM ticket t " +
				"LEFT JOIN organization o ON (t.org_id = o.org_id) " +
				
				"LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
				
		"WHERE t.ticketid = ? AND tipo_richiesta IS NULL ");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		
		
	}





	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  systemStatus   Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void buildFiles(Connection db) throws SQLException {
		files.clear();
		files.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
		files.setLinkItemId(this.getId());
		files.buildList(db);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  id             Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public boolean checkContactRecord(Connection db, int id) throws SQLException {
		boolean contactFound = false;
		if (id != -1) {
			PreparedStatement pst = db.prepareStatement(
					"SELECT contact_id from contact c " +
			"WHERE c.contact_id = ? ");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				contactFound = true;
			}
			rs.close();
			pst.close();
		}
		return contactFound;
	}


	/**
	 *  Gets the resolvedByDeptName attribute of the Ticket object
	 *
	 * @return    The resolvedByDeptName value
	 */
	public String getResolvedByDeptName() {
		return resolvedByDeptName;
	}


	/**
	 *  Sets the resolvedByDeptName attribute of the Ticket object
	 *
	 * @param  tmp  The new resolvedByDeptName value
	 */
	public void setResolvedByDeptName(String tmp) {
		this.resolvedByDeptName = tmp;
	}


	/**
	 *  Sets the productId attribute of the Ticket object
	 *
	 * @param  tmp  The new productId value
	 */
	


	


	/**
	 *  Sets the systemStatus attribute of the Ticket object
	 *
	 * @param  tmp  The new systemStatus value
	 */
	public void setSystemStatus(SystemStatus tmp) {
		this.systemStatus = tmp;
	}


	


	public int getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	/**
	 *  Gets the systemStatus attribute of the Ticket object
	 *
	 * @return    The systemStatus value
	 */
	public SystemStatus getSystemStatus() {
		return systemStatus;
	}


	/**
	 *  Gets the productId attribute of the Ticket object
	 *
	 * @return    The productId value
	 */
	


	/**
	 *  Sets the closed attribute of the Ticket object
	 *
	 * @param  closed  The new closed value
	 */
	public void setClosed(java.sql.Timestamp closed) {
		this.closed = closed;
	}


	/**
	 *  Sets the closed attribute of the Ticket object
	 *
	 * @param  tmp  The new closed value
	 */
	public void setClosed(String tmp) {
		this.closed = DateUtils.parseTimestampString(tmp);
	}


	/**
	 *  Sets the expectation attribute of the Ticket object
	 *
	 * @param  tmp  The new expectation value
	 */
	




	


	/**
	 *  Sets the statusId attribute of the Ticket object
	 *
	 * @param  tmp  The new statusId value
	 */
	public void setStatusId(int tmp) {
		this.statusId = tmp;
	}


	/**
	 *  Sets the statusId attribute of the Ticket object
	 *
	 * @param  tmp  The new statusId value
	 */
	public void setStatusId(String tmp) {
		this.statusId = Integer.parseInt(tmp);
	}


	/**
	 *  Gets the statusId attribute of the Ticket object
	 *
	 * @return    The statusId value
	 */
	public int getStatusId() {
		return statusId;
	}






	/**
	 *  Sets the resolutionDateTimeZone attribute of the Ticket object
	 *
	 * @param  tmp  The new resolutionDateTimeZone value
	 */
	public void setResolutionDateTimeZone(String tmp) {
		this.resolutionDateTimeZone = tmp;
	}


	/**
	 *  Gets the assignedDateTimeZone attribute of the Ticket object
	 *
	 * @return    The assignedDateTimeZone value
	 */
	public String getAssignedDateTimeZone() {
		return assignedDateTimeZone;
	}



	/**
	 *  Gets the resolutionDateTimeZone attribute of the Ticket object
	 *
	 * @return    The resolutionDateTimeZone value
	 */
	public String getResolutionDateTimeZone() {
		return resolutionDateTimeZone;
	}


	/**
	 *  Sets the trashedDate attribute of the Ticket object
	 *
	 * @param  tmp  The new trashedDate value
	 */
	public void setTrashedDate(java.sql.Timestamp tmp) {
		this.trashedDate = tmp;
	}


	/**
	 *  Sets the trashedDate attribute of the Ticket object
	 *
	 * @param  tmp  The new trashedDate value
	 */
	public void setTrashedDate(String tmp) {
		this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
	}
	
	
	
	public void setDataChiusura(java.sql.Timestamp tmp) {
		this.dataChiusura = tmp;
	}


	/**
	 *  Sets the trashedDate attribute of the Ticket object
	 *
	 * @param  tmp  The new trashedDate value
	 */
	public void setDataChiusura(String tmp) {
		this.dataChiusura = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 *  Gets the trashedDate attribute of the Ticket object
	 *
	 * @return    The statusId value
	 */
	public java.sql.Timestamp getTrashedDate() {
		return trashedDate;
	}
	
	public java.sql.Timestamp getDataChiusura() {
		return dataChiusura;
	}


	/**
	 *  Gets the trashed attribute of the Ticket object
	 *
	 * @return    The trashed value
	 */
	public boolean isTrashed() {
		return (trashedDate != null);
	}


	/**
	 *  Gets the orgSiteId attribute of the Ticket object
	 *
	 * @return    The orgSiteId value
	 */
	public int getOrgSiteId() {
		return orgSiteId;
	}


	


	/**
	 *  Sets the orgSiteId attribute of the Ticket object
	 *
	 * @param  tmp  The new orgSiteId value
	 */
	public void setOrgSiteId(int tmp) {
		this.orgSiteId = tmp;
	}


	/**
	 *  Sets the orgSiteId attribute of the Ticket object
	 *
	 * @param  tmp  The new orgSiteId value
	 */
	public void setOrgSiteId(String tmp) {
		this.orgSiteId = Integer.parseInt(tmp);
	}

	public void setIdApiario(String idApiario) {
		this.idApiario = Integer.parseInt(idApiario);
	}
	
	/**
	 *  Sets the Newticketlogentry attribute of the Ticket object
	 *
	 * @param  newticketlogentry  The new Newticketlogentry value
	 */
	public void setNewticketlogentry(String newticketlogentry) {
		this.comment = newticketlogentry;
	}


	


	/**
	 *  Sets the assignedDate attribute of the Ticket object
	 *
	 * @param  tmp  The new assignedDate value
	 */
	public void setAssignedDate(java.sql.Timestamp tmp) {
		this.assignedDate = tmp;
	}


	/**
	 *  Sets the assignedDate attribute of the Ticket object
	 *
	 * @param  tmp  The new assignedDate value
	 */
	public void setAssignedDate(String tmp) {
		this.assignedDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}




	/**
	 *  Sets the entered attribute of the Ticket object
	 *
	 * @param  tmp  The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the modified attribute of the Ticket object
	 *
	 * @param  tmp  The new modified value
	 */
	public void setModified(java.sql.Timestamp tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the entered attribute of the Ticket object
	 *
	 * @param  tmp  The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DateUtils.parseTimestampString(tmp);
	}


	/**
	 *  Sets the modified attribute of the Ticket object
	 *
	 * @param  tmp  The new modified value
	 */
	public void setModified(String tmp) {
		this.modified = DateUtils.parseTimestampString(tmp);
	}




	


	/**
	 *  Sets the ErrorMessage attribute of the Ticket object
	 *
	 * @param  tmp  The new ErrorMessage value
	 */
	public void setErrorMessage(String tmp) {
		this.errorMessage = tmp;
	}



	/**
	 *  Sets the files attribute of the Ticket object
	 *
	 * @param  tmp  The new files value
	 */
	public void setFiles(FileItemList tmp) {
		this.files = tmp;
	}


	/**
	 *  Sets the Id attribute of the Ticket object
	 *
	 * @param  tmp  The new Id value
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the Id attribute of the Ticket object
	 *
	 * @param  tmp  The new Id value
	 */
	public void setId(String tmp) {
		this.setId(Integer.parseInt(tmp));
	}


	/**
	 *  Sets the CompanyName attribute of the Ticket object
	 *
	 * @param  companyName  The new CompanyName value
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	/**
	 *  Sets the OrgId attribute of the Ticket object
	 *
	 * @param  tmp  The new OrgId value
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 *  Sets the OrgId attribute of the Ticket object
	 *
	 * @param  tmp  The new OrgId value
	 */
	public void setOrgId(String tmp) {
		this.orgId = Integer.parseInt(tmp);
	}


	

	/**
	 *  Sets the contractStartDate attribute of the Ticket object
	 *
	 * @param  tmp  The new contractStartDate value
	 */
	public void setContractStartDate(java.sql.Timestamp tmp) {
		this.contractStartDate = tmp;
	}


	/**
	 *  Sets the contractStartDate attribute of the Ticket object
	 *
	 * @param  tmp  The new contractStartDate value
	 */
	public void setContractStartDate(String tmp) {
		this.contractStartDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}


	/**
	 *  Sets the contractEndDate attribute of the Ticket object
	 *
	 * @param  tmp  The new contractEndDate value
	 */
	public void setContractEndDate(java.sql.Timestamp tmp) {
		this.contractEndDate = tmp;
	}


	/**
	 *  Sets the contractEndDate attribute of the Ticket object
	 *
	 * @param  tmp  The new contractEndDate value
	 */
	public void setContractEndDate(String tmp) {
		this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}


	


	/**
	 *  Sets the assetId attribute of the Ticket object
	 *
	 * @param  tmp  The new assetId value
	 */
	public void setAssetId(int tmp) {
		this.assetId = tmp;
	}


	/**
	 *  Sets the assetId attribute of the Ticket object
	 *
	 * @param  tmp  The new assetId value
	 */
	public void setAssetId(String tmp) {
		this.assetId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the assetSerialNumber attribute of the Ticket object
	 *
	 * @param  tmp  The new assetSerialNumber value
	 */
	public void setAssetSerialNumber(String tmp) {
		this.assetSerialNumber = tmp;
	}


	/**
	 *  Sets the assetManufacturerCode attribute of the Ticket object
	 *
	 * @param  tmp  The new assetManufacturerCode value
	 */
	public void setAssetManufacturerCode(int tmp) {
		this.assetManufacturerCode = tmp;
	}


	/**
	 *  Sets the assetManufacturerCode attribute of the Ticket object
	 *
	 * @param  tmp  The new assetManufacturerCode value
	 */
	public void setAssetManufacturerCode(String tmp) {
		this.assetManufacturerCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the assetVendorCode attribute of the Ticket object
	 *
	 * @param  tmp  The new assetVendorCode value
	 */
	public void setAssetVendorCode(int tmp) {
		this.assetVendorCode = tmp;
	}


	/**
	 *  Sets the assetVendorCode attribute of the Ticket object
	 *
	 * @param  tmp  The new assetVendorCode value
	 */
	public void setAssetVendorCode(String tmp) {
		this.assetVendorCode = Integer.parseInt(tmp);
	}



	/**
	 *  Sets the modelVersion attribute of the Ticket object
	 *
	 * @param  tmp  The new modelVersion value
	 */
	public void setAssetModelVersion(String tmp) {
		this.assetModelVersion = tmp;
	}


	/**
	 *  Sets the location attribute of the Ticket object
	 *
	 * @param  tmp  The new location value
	 */
	public void setAssetLocation(String tmp) {
		this.assetLocation = tmp;
	}


	/**
	 *  Sets the assetOnsiteResponseModel attribute of the Ticket object
	 *
	 * @param  tmp  The new assetOnsiteResponseModel value
	 */
	public void setAssetOnsiteResponseModel(int tmp) {
		this.assetOnsiteResponseModel = tmp;
	}


	/**
	 *  Sets the assetOnsiteResponseModel attribute of the Ticket object
	 *
	 * @param  tmp  The new assetOnsiteResponseModel value
	 */
	public void setAssetOnsiteResponseModel(String tmp) {
		this.assetOnsiteResponseModel = Integer.parseInt(tmp);
	}


		
	
	


	


	/**
	 *  Gets the paddedTicketId attribute of the Ticket object
	 *
	 * @return    The paddedTicketId value
	 */
	public String getPaddedTicketId() {
		
			return getPaddedId();
		
	}


	/**
	 *  Sets the ContactId attribute of the Ticket object
	 *
	 * @param  tmp  The new ContactId value
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
	}


	


	/**
	 *  Sets the ContactId attribute of the Ticket object
	 *
	 * @param  tmp  The new ContactId value
	 */
	public void setContactId(String tmp) {
		this.contactId = Integer.parseInt(tmp);
	}


	


	/**
	 *  Sets the Problem attribute of the Ticket object
	 *
	 * @param  tmp  The new Problem value
	 */
	public void setProblem(String tmp) {
		this.problem = tmp;
	}


	/**
	 *  Sets the location attribute of the Ticket object
	 *
	 * @param  tmp  The new location value
	 */
	public void setLocation(String tmp) {
		this.location = tmp;
	}


	/**
	 *  Sets the Comment attribute of the Ticket object
	 *
	 * @param  tmp  The new Comment value
	 */
	public void setComment(String tmp) {
		this.comment = tmp;
	}


	/**
	 *  Sets the estimatedResolutionDate attribute of the Ticket object
	 *
	 * @param  tmp  The new estimatedResolutionDate value
	 */
	public void setEstimatedResolutionDate(java.sql.Timestamp tmp) {
		this.estimatedResolutionDate = tmp;
	}


	/**
	 *  Sets the estimatedResolutionDate attribute of the Ticket object
	 *
	 * @param  tmp  The new estimatedResolutionDate value
	 */
	public void setEstimatedResolutionDate(String tmp) {
		this.estimatedResolutionDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}


	/**
	 *  Sets the estimatedResolutionDateTimeZone attribute of the Ticket object
	 *
	 * @param  tmp  The new estimatedResolutionDateTimeZone value
	 */
	public void setEstimatedResolutionDateTimeZone(String tmp) {
		this.estimatedResolutionDateTimeZone = tmp;
	}


	/**
	 *  Sets the cause attribute of the Ticket object
	 *
	 * @param  tmp  The new cause value
	 */
	public void setCause(String tmp) {
		this.cause = tmp;
	}


	/**
	 *  Sets the Solution attribute of the Ticket object
	 *
	 * @param  tmp  The new Solution value
	 */
	public void setSolution(String tmp) {
		this.solution = tmp;
	}



	


	/**
	 *  Sets the resolutionDate attribute of the Ticket object
	 *
	 * @param  tmp  The new resolutionDate value
	 */
	public void setResolutionDate(java.sql.Timestamp tmp) {
		this.resolutionDate = tmp;
	}


	/**
	 *  Sets the resolutionDate attribute of the Ticket object
	 *
	 * @param  tmp  The new resolutionDate value
	 */
	public void setResolutionDate(String tmp) {
		this.resolutionDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}


	

	/**
	 *  Sets the EnteredBy attribute of the Ticket object
	 *
	 * @param  tmp  The new EnteredBy value
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Ticket object
	 *
	 * @param  tmp  The new EnteredBy value
	 */
	public void setEnteredBy(String tmp) {
		this.enteredBy = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the ModifiedBy attribute of the Ticket object
	 *
	 * @param  tmp  The new ModifiedBy value
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Ticket object
	 *
	 * @param  tmp  The new ModifiedBy value
	 */
	public void setModifiedBy(String tmp) {
		this.modifiedBy = Integer.parseInt(tmp);
	}


	

	/**
	 *  Gets the closed attribute of the Ticket object
	 *
	 * @return    The closed value
	 */
	public java.sql.Timestamp getClosed() {
		return closed;
	}


	/**
	 *  Gets the closedString attribute of the Ticket object
	 *
	 * @return    The closedString value
	 */
	public String getClosedString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
					closed);
		} catch (NullPointerException e) {
		}
		return tmp;
	}


	/**
	 *  Gets the closed attribute of the Ticket object
	 *
	 * @return    The closed value
	 */
	public boolean isClosed() {
		return closed != null;
	}


	


	

	/**
	 *  Gets the paddedId attribute of the Ticket object
	 *
	 * @return    The paddedId value
	 */
	public String getPaddedId() {
		String padded = (String.valueOf(this.getId()));
		while (padded.length() < 6) {
			padded = "0" + padded;
		}
		return padded;
	}


	public String getPaddedIdControlloUfficiale(int idControllo) {
		String padded = (String.valueOf(idControllo));
		while (padded.length() < 6) {
			padded = "0" + padded;
		}
		return padded;
	}
	
	public String getPaddedIdControlloUfficiale(String idControllo) {
		String padded = idControllo;
		while (padded.length() < 6) {
			padded = "0" + padded;
		}
		return padded;
	}


	/**
	 *  Gets the entered attribute of the Ticket object
	 *
	 * @return    The entered value
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}


	/**
	 *  Gets the modified attribute of the Ticket object
	 *
	 * @return    The modified value
	 */
	public java.sql.Timestamp getModified() {
		return modified;
	}


	/**
	 *  Gets the modifiedString attribute of the Ticket object
	 *
	 * @return    The modifiedString value
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
	 *  Gets the enteredString attribute of the Ticket object
	 *
	 * @return    The enteredString value
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
	 *  Gets the enteredString attribute of the Ticket object
	 *
	 * @param  dateStyle  Description of the Parameter
	 * @param  timeStyle  Description of the Parameter
	 * @return            The enteredString value
	 */
	public String getEnteredString(int dateStyle, int timeStyle) {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(dateStyle, timeStyle).format(
					entered);
		} catch (NullPointerException e) {
		}
		return tmp;
	}


	/**
	 *  Gets the modifiedDateTimeString attribute of the Ticket object
	 *
	 * @return    The modifiedDateTimeString value
	 */
	public String getModifiedDateTimeString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
					modified);
		} catch (NullPointerException e) {
		}
		return tmp;
	}



	


	/**
	 *  Gets the escalationLevelName attribute of the Ticket object
	 *
	 * @return    The escalationLevelName value
	 */
	


	/**
	 *  Gets the Newticketlogentry attribute of the Ticket object
	 *
	 * @return    The Newticketlogentry value
	 */
	public String getNewticketlogentry() {
		return comment;
	}


	


	/**
	 *  Gets the assignedDate attribute of the Ticket object
	 *
	 * @return    The assignedDate value
	 */
	public java.sql.Timestamp getAssignedDate() {
		return assignedDate;
	}


	/**
	 *  Gets the CloseIt attribute of the Ticket object
	 *
	 * @return    The CloseIt value
	 */
	


	/**
	 *  Gets the files attribute of the Ticket object
	 *
	 * @return    The files value
	 */
	public FileItemList getFiles() {
		return files;
	}



	/**
	 *  Gets the CompanyName attribute of the Ticket object
	 *
	 * @return    The CompanyName value
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 *  Gets the ErrorMessage attribute of the Ticket object
	 *
	 * @return    The ErrorMessage value
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 *  Gets the Id attribute of the Ticket object
	 *
	 * @return    The Id value
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the OrgId attribute of the Ticket object
	 *
	 * @return    The OrgId value
	 */
	public int getOrgId() {
		return orgId;
	}


	

	

	/**
	 *  Gets the contractStartDate attribute of the Ticket object
	 *
	 * @return    The contractStartDate value
	 */
	public java.sql.Timestamp getContractStartDate() {
		return contractStartDate;
	}


	/**
	 *  Gets the contractEndDate attribute of the Ticket object
	 *
	 * @return    The contractEndDate value
	 */
	public java.sql.Timestamp getContractEndDate() {
		return contractEndDate;
	}


	


	/**
	 *  Gets the assetId attribute of the Ticket object
	 *
	 * @return    The assetId value
	 */
	public int getAssetId() {
		return assetId;
	}


	/**
	 *  Gets the assetSerialNumber attribute of the Ticket object
	 *
	 * @return    The assetSerialNumber value
	 */
	public String getAssetSerialNumber() {
		return assetSerialNumber;
	}


	/**
	 *  Gets the assetManufacturerCode attribute of the Ticket object
	 *
	 * @return    The assetManufacturerCode value
	 */
	public int getAssetManufacturerCode() {
		return assetManufacturerCode;
	}


	/**
	 *  Gets the assetVendorCode attribute of the Ticket object
	 *
	 * @return    The assetVendorCode value
	 */
	public int getAssetVendorCode() {
		return assetVendorCode;
	}


	/**
	 *  Gets the modelVersion attribute of the Ticket object
	 *
	 * @return    The modelVersion value
	 */
	public String getAssetModelVersion() {
		return assetModelVersion;
	}


	/**
	 *  Gets the location attribute of the Ticket object
	 *
	 * @return    The location value
	 */
	public String getAssetLocation() {
		return assetLocation;
	}


	/**
	 *  Gets the assetOnsiteResponseModel attribute of the Ticket object
	 *
	 * @return    The assetOnsiteResponseModel value
	 */
	public int getAssetOnsiteResponseModel() {
		return assetOnsiteResponseModel;
	}


	/**
	 *  Gets the ContactId attribute of the Ticket object
	 *
	 * @return    The ContactId value
	 */
	public int getContactId() {
		return contactId;
	}


	/**
	 *  Gets the Problem attribute of the Ticket object
	 *
	 * @return    The Problem value
	 */
	public String getProblem() {
		return problem;
	}


	/**
	 *  Gets the location attribute of the Ticket object
	 *
	 * @return    The location value
	 */
	public String getLocation() {
		return location;
	}
	
	public String getLocationNew() {
		return location_new;
	}



	/**
	 *  Gets the problemHeader attribute of the Ticket object
	 *
	 * @return    The problemHeader value
	 */
	public String getProblemHeader() {
		if (problem.trim().length() > 100) {
			return (problem.substring(0, 100) + "...");
		} else {
			return getProblem();
		}
	}


	


	/**
	 *  Gets the Comment attribute of the Ticket object
	 *
	 * @return    The Comment value
	 */
	public String getComment() {
		return comment;
	}


	/**
	 *  Gets the estimatedResolutionDate attribute of the Ticket object
	 *
	 * @return    The estimatedResolutionDate value
	 */
	public java.sql.Timestamp getEstimatedResolutionDate() {
		return estimatedResolutionDate;
	}


	/**
	 *  Gets the estimatedResolutionDateTimeZone attribute of the Ticket object
	 *
	 * @return    The estimatedResolutionDateTimeZone value
	 */
	public String getEstimatedResolutionDateTimeZone() {
		return estimatedResolutionDateTimeZone;
	}


	/**
	 *  Gets the cause attribute of the Ticket object
	 *
	 * @return    The cause value
	 */
	public String getCause() {
		return cause;
	}


	/**
	 *  Gets the Solution attribute of the Ticket object
	 *
	 * @return    The Solution value
	 */
	public String getSolution() {
		return solution;
	}







	/**
	 *  Gets the resolutionDate attribute of the Ticket object
	 *
	 * @return    The resolutionDate value
	 */
	public java.sql.Timestamp getResolutionDate() {
		return resolutionDate;
	}


	/**
	 *  Gets the EnteredBy attribute of the Ticket object
	 *
	 * @return    The EnteredBy value
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the ModifiedBy attribute of the Ticket object
	 *
	 * @return    The ModifiedBy value
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	

	/**
	 *  Gets the causeId attribute of the Ticket object
	 *
	 * @return    The causeId value
	 */
	public int getCauseId() {
		return causeId;
	}


	/**
	 *  Sets the causeId attribute of the Ticket object
	 *
	 * @param  tmp  The new causeId value
	 */
	public void setCauseId(int tmp) {
		this.causeId = tmp;
	}


	/**
	 *  Sets the causeId attribute of the Ticket object
	 *
	 * @param  tmp  The new causeId value
	 */
	public void setCauseId(String tmp) {
		this.causeId = Integer.parseInt(tmp);
	}


	/**
	 *  Gets the resolutionId attribute of the Ticket object
	 *
	 * @return    The resolutionId value
	 */
	public int getResolutionId() {
		return resolutionId;
	}


	/**
	 *  Sets the resolutionId attribute of the Ticket object
	 *
	 * @param  tmp  The new resolutionId value
	 */
	public void setResolutionId(int tmp) {
		this.resolutionId = tmp;
	}


	/**
	 *  Sets the resolutionId attribute of the Ticket object
	 *
	 * @param  tmp  The new resolutionId value
	 */
	public void setResolutionId(String tmp) {
		this.resolutionId = Integer.parseInt(tmp);
	}


	


	/**
	 *  Sets the defectId attribute of the Ticket object
	 *
	 * @param  tmp  The new defectId value
	 */
	


	/**
	 *  Gets the stateId attribute of the Ticket object
	 *
	 * @return    The stateId value
	 */
	public int getStateId() {
		return stateId;
	}


	/**
	 *  Sets the stateId attribute of the Ticket object
	 *
	 * @param  tmp  The new stateId value
	 */
	public void setStateId(int tmp) {
		this.stateId = tmp;
	}


	/**
	 *  Sets the stateId attribute of the Ticket object
	 *
	 * @param  tmp  The new stateId value
	 */
	public void setStateId(String tmp) {
		this.stateId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the siteId attribute of the Ticket object
	 *
	 * @param  tmp  The new siteId value
	 */
	public void setSiteId(int tmp) {
		this.siteId = tmp;
	}


	/**
	 *  Sets the siteId attribute of the Ticket object
	 *
	 * @param  tmp  The new siteId value
	 */
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}


	/**
	 *  Gets the siteId attribute of the Ticket object
	 *
	 * @return    The siteId value
	 */
	public int getSiteId() {
		return siteId;
	}


	/**
	 *  Description of the Method
	


	/**
	 *  Inserts this ticket into the database, and populates this Id. Inserts
	 *  required fields, then calls update to finish record entry
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */
	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);
			
			sql.append(
					"INSERT INTO ticket (contact_id, problem, pri_code, " +
					"department_code, cat_code, scode, org_id, link_contract_id, " +
					"link_asset_id, expectation, product_id, customer_product_id, " +
					"key_count, status_id, trashed_date, user_group_id, cause_id, " +
					"resolution_id, defect_id, escalation_level, resolvable, " +
			"resolvedby, resolvedby_department_code, state_id, site_id, ");
			if (id > -1) {
				sql.append("ticketid, ");
			}
			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}
			sql.append("enteredBy, modifiedBy ) ");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sql.append("?, ?, ?, ");
			if (id > -1) {
				sql.append("?,");
			}
			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}
			sql.append("?, ?) ");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());
		
				pst.setNull(++i, java.sql.Types.INTEGER);
			
			
				pst.setNull(++i, java.sql.Types.INTEGER);
			
			
				pst.setNull(++i, java.sql.Types.INTEGER);
			
				pst.setNull(++i, java.sql.Types.INTEGER);
			
			DatabaseUtils.setInt(pst, ++i, orgId);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, assetId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, statusId);
			DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, causeId);
			DatabaseUtils.setInt(pst, ++i, resolutionId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.BOOLEAN);
			
				pst.setNull(++i, java.sql.Types.INTEGER);
			
			
				pst.setNull(++i, java.sql.Types.INTEGER);
			
			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			if (id > -1) {
				pst.setInt(++i, id);
			}
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}
			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.execute();
			pst.close();
			//Update the rest of the fields
			this.update(db, true);
			
			
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
	 *  Update this ticket in the database
	 *
	 * @param  db             Description of Parameter
	 * @param  override       Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws SQLException 
	 * @throws  SQLException  Description of Exception
	 */
	public int update(Connection db, boolean override) throws SQLException  {
		
		int resultCount = 0;
		try
		{

			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();
			sql.append(
					"UPDATE ticket " +
					"SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
					"pri_code = ?, scode = ?, " +
					"cat_code = ?, assigned_to = ?, " +
					"subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
					"source_code = ?, contact_id = ?, problem = ?, " +
			"status_id = ?, trashed_date = ?, site_id = ? , ip_modified='"+ipModified+"',");
			//    if (!override) {
			sql.append(
					"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
			//    }

			if (dataSintomi != null)
			{
				sql.append( " data_sintomi = ?, data_sintomi_timezone = ?,");
			}

			if (assignedDate != null)
			{
				sql.append( " assigned_date = ?, assigned_date_timezone = ?,");
			}
			if (location != null)
			{
				sql.append( "location = ?,");
			}
			if (location_new != null)
			{
				sql.append( "location_new = ?,");
			}
			if (cause != null)
			{
				sql.append( "cause = ?,");
			}

			//    if (this.getCloseIt()) {
			//      sql.append("closed = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
			//    } else {
			//      if (closed != null) {
			//        sql.append("closed = ?, ");
			//      }
			//    }


			sql.append(
					"solution = ?,  " +
					"est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
					" expectation = ?, product_id = ?, customer_product_id = ?, " +
					"user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
					"escalation_level = ?" +
			"WHERE ticketid = ? ");


			int i = 0;
			

			pst = db.prepareStatement(sql.toString());
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, this.getAssetId());
			pst.setNull(++i, java.sql.Types.INTEGER);

			pst.setNull(++i, java.sql.Types.INTEGER);

			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());
			DatabaseUtils.setInt(pst, ++i, this.getStatusId());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			//    if (!override) {
				pst.setInt(++i, this.getModifiedBy());
				//    }

				if (dataSintomi != null)
				{

					DatabaseUtils.setTimestamp(pst, ++i, dataSintomi);
					pst.setString(++i, this.dataSintomiTimeZone);

				}
				if (assignedDate != null)
				{
					DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
					pst.setString(++i, this.assignedDateTimeZone);
				}
				if (location != null)
				{
					pst.setString(++i, location);
				}
				if (location_new != null)
				{
					pst.setString(++i, location_new);
				}
				if (cause != null)
				{
					pst.setString(++i, cause);
				}
				//    if (!this.getCloseIt() && closed != null) {
				//      pst.setTimestamp(++i, closed);
				//    }

				pst.setString(++i, this.getSolution());

				DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
				pst.setString(++i, estimatedResolutionDateTimeZone);
				DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
				pst.setString(++i, this.resolutionDateTimeZone);

				pst.setNull(++i, java.sql.Types.INTEGER);
				pst.setNull(++i, java.sql.Types.INTEGER);
				pst.setNull(++i, java.sql.Types.INTEGER);
				pst.setNull(++i, java.sql.Types.INTEGER);

				DatabaseUtils.setInt(pst, ++i, causeId);
				DatabaseUtils.setInt(pst, ++i, resolutionId);
				pst.setNull(++i, java.sql.Types.INTEGER);
				DatabaseUtils.setInt(pst, ++i, this.getStateId());
				pst.setNull(++i, java.sql.Types.INTEGER);

				pst.setInt(++i, id);
 
				
				resultCount = pst.executeUpdate();
				pst.close();
		}catch(SQLException e)
		{
			e.printStackTrace();  
		}

		return resultCount;
	}




	


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  toTrash        Description of the Parameter
	 * @param  tmpUserId      Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {

		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE ticket " +
				"SET trashed_date = ?, " +
				"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
				"user_group_id = ? , " +
				"modifiedby = ? " +
		"WHERE ticketid = ? ");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		if (toTrash) {
			DatabaseUtils.setTimestamp(
					pst, ++i, new Timestamp(System.currentTimeMillis()));
		} else {
			DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
		}
		DatabaseUtils.setInt(pst, ++i, -1);
		DatabaseUtils.setInt(pst, ++i, tmpUserId);
		pst.setInt(++i, this.id);
		resultCount = pst.executeUpdate();
		pst.close();

		

		return (resultCount == 1);
	}


	public boolean updateStatusAllerte(Connection db, boolean toTrash, int tmpUserId) throws SQLException {

		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE ticket " +
				"SET trashed_date = ?, " +
				"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
				"modifiedby = ?,motivo_cancellazione_allerta = ? " +
		"WHERE ticketid = ? ");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		if (toTrash) {
			DatabaseUtils.setTimestamp(
					pst, ++i, new Timestamp(System.currentTimeMillis()));
		} else {
			DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
		}

		DatabaseUtils.setInt(pst, ++i, tmpUserId);
		pst.setString(++i, motivo_cancellazione_allerta);
		pst.setInt(++i, this.id);
		resultCount = pst.executeUpdate();
		pst.close();

		// Trash the tasks related to the ticket
		
		return (resultCount == 1);
	}


	


	/**
	 *  Reopens a ticket so that it can be modified again
	 *
	 * @param  db             Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public int reopen(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
				"UPDATE ticket " +
				"SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", modifiedby = ? " +
						"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			//Update the ticket log
			
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public DependencyList processDependencies(Connection db) throws SQLException {
		DependencyList dependencyList = new DependencyList();
		

		return dependencyList;
	}

	
	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @param  baseFilePath   Description of the Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */
	public boolean delete(Connection db, String baseFilePath) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			//delete any related action list items

			//Delete any documents
			FileItemList fileList = new FileItemList();
			fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
			fileList.setLinkItemId(this.getId());
			fileList.buildList(db);
			fileList.delete(db, getFileLibraryPath(baseFilePath, "tickets"));
			fileList = null;

			


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


	public static void aggiornaPunteggio(Connection db, int idNononformita) throws SQLException
	{

		String selselectIdCu = "select id_controllo_ufficiale,provvedimenti_prescrittivi from ticket where ticketid = ?";
		String update = "update ticket set punteggio = (select sum (punteggio) from ticket where id_controllo_ufficiale= ? and tipologia in (2,7,8,16)) where ticketid = ?";
		PreparedStatement pst = db.prepareStatement(selselectIdCu);
		pst.setInt(1, idNononformita);
		ResultSet rs = pst.executeQuery();
		String idCU = "";
		int tipo_campione = -1 ;
		if(rs.next())
		{
			idCU = rs.getString(1);
			tipo_campione = rs.getInt("provvedimenti_prescrittivi");
			
		}
		String padd = idCU;
		int id_cu = -1;
		if(idCU!=null)
			while(idCU.startsWith("0"))
			{
				idCU = idCU.substring(1);
			}
		if(padd!= null && !padd.equals("") && tipo_campione != 5)
		{
			id_cu = Integer.parseInt(padd);
			pst =db.prepareStatement(update);

			pst.setString(1, padd);
			pst.setInt(2, id_cu);
			pst.execute();
		}
	}

	public static void updateDataAccettazione(Connection db, String codiceAccettazione, Timestamp dataAccettazione, int idCampione) throws SQLException
	{

		String update = "update ticket set cause = ?, data_accettazione= ? where ticketid = ?";
		PreparedStatement pst = db.prepareStatement(update);
		pst.setString(1, codiceAccettazione);
		pst.setTimestamp(2, dataAccettazione);
		pst.setInt(3, idCampione);

		pst.execute();
	}
	
	public static void updateInfoEsito(Connection db, String codiceAccettazione, Timestamp dataAccettazione, String noteEsitoEsame, Timestamp dataRisultato, int idCampione) throws SQLException
	{

		String update = "update ticket set cause = ?, data_accettazione= ?, note_esito_esame = ?, data_risultato = ?, esito_informazioni_laboratorio_chiuso = true, esito_campione_chiuso = true where ticketid = ?";
		PreparedStatement pst = db.prepareStatement(update);
		pst.setString(1, codiceAccettazione);
		pst.setTimestamp(2, dataAccettazione);
		pst.setString(3, noteEsitoEsame);
		pst.setTimestamp(4, dataRisultato);
		pst.setInt(5, idCampione);
		pst.execute();
	}
	
	public static void updateInfoEsitoCampioneChiuso(Connection db, int idCampione, boolean informazioniLaboratorio) throws SQLException
	{
		String update = "update ticket set esito_informazioni_laboratorio_chiuso = ? where ticketid = ?";
		PreparedStatement pst = db.prepareStatement(update);
		pst.setBoolean(1, informazioniLaboratorio);
		pst.setInt(2, idCampione);
		pst.execute();
	}
	
	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 */

	public int update(Connection db) throws SQLException {
		int i = -1;
		try {

			i = this.update(db, false);

			aggiornaPunteggio(db,this.getId());  

		} catch (SQLException e) {
			e.printStackTrace();
			db.rollback();
			throw new SQLException(e.getMessage());
		} 
		return i;
	}


	public int updateEsito(Connection db) throws SQLException {
		int i = -1;
		try {
			db.setAutoCommit(false);
			i = this.update(db, true);

			aggiornaPunteggio(db,this.getId());  
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return i;
	}


	

	/**
	 *  Description of the Method
	 *
	 * @param  rs             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		//ticket table
		this.setId(rs.getInt("ticketid"));
		orgId = DatabaseUtils.getInt(rs, "org_id");
		contactId = DatabaseUtils.getInt(rs, "contact_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		
		motivo_cancellazione_allerta = rs.getString("motivo_cancellazione_allerta");
		
		solution = rs.getString("solution");
		location = rs.getString("location");
		assignedDate = rs.getTimestamp("assigned_date");
		dataSintomi = rs.getTimestamp("data_sintomi");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		resolutionDate = rs.getTimestamp("resolution_date");
		cause = rs.getString("cause");
		assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		
		estimatedResolutionDateTimeZone = rs.getString(
		"est_resolution_date_timezone");
		assignedDateTimeZone = rs.getString("assigned_date_timezone");
		dataSintomiTimeZone = rs.getString("data_sintomi_timezone");
		resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		statusId = DatabaseUtils.getInt(rs, "status_id");
		trashedDate = rs.getTimestamp("trashed_date");
		causeId = DatabaseUtils.getInt(rs, "cause_id");
		
		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		//organization table
		companyName = rs.getString("orgname");
		orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");

		

		//asset table
		assetSerialNumber = rs.getString("serialnumber");
		assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
		assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
		assetModelVersion = rs.getString("modelversion");
		assetLocation = rs.getString("assetlocation");
		
		altId = DatabaseUtils.getInt(rs, "alt_id");
		
		resolvedBy =  DatabaseUtils.getInt(rs, "resolvedby");

	}




	/**
	 *  Description of the Method
	 *
	 * @return    Description of the Return Value
	 */
	public boolean hasFiles() {
		return (files != null && files.size() > 0);
	}


	/**
	 *  Gets the properties that are TimeZone sensitive for auto-populating
	 *
	 * @return    The timeZoneParams value
	 */
	public static ArrayList getTimeZoneParams() {
		ArrayList thisList = new ArrayList();
		thisList.add("assignedDate");
		thisList.add("estimatedResolutionDate");
		thisList.add("resolutionDate");
		thisList.add("contractStartDate");
		thisList.add("contractEndDate");
		return thisList;
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  projectId      Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void insertProjectLink(Connection db, int projectId) throws SQLException {
		String sql = "INSERT INTO ticketlink_project " +
		"(ticket_id, project_id) " +
		"VALUES (?, ?) ";
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(++i, this.getId());
		pst.setInt(++i, projectId);
		pst.execute();
		pst.close();
	}


	/**
	 *  Each ticket in a project has its own unique count
	 *
	 * @param  db             Description of the Parameter
	 * @param  projectId      Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void updateProjectTicketCount(Connection db, int projectId) throws SQLException {
		Exception errorMessage = null;
		boolean autoCommit = db.getAutoCommit();
		try {
			if (autoCommit) {
				db.setAutoCommit(false);
			}
			int i = 0;
			// Lock the row with the new value
			PreparedStatement pst = db.prepareStatement(
					"UPDATE project_ticket_count " +
					"SET key_count = key_count + 1 " +
			"WHERE project_id = ? ");
			pst.setInt(++i, projectId);
			pst.execute();
			pst.close();
			// Retrieve the new value
			i = 0;
			pst = db.prepareStatement(
					"SELECT key_count " +
					"FROM project_ticket_count " +
			"WHERE project_id = ? ");
			pst.setInt(++i, projectId);
			ResultSet rs = pst.executeQuery();
			
			rs.close();
			pst.close();
			if (autoCommit) {
				db.commit();
			}
		} catch (Exception e) {
			errorMessage = e;
			if (autoCommit) {
				db.rollback();
			}
		} finally {
			if (autoCommit) {
				db.setAutoCommit(true);
			}
		}
		if (errorMessage != null) {
			throw new SQLException(errorMessage.getMessage());
		}
	}

	
	
	public String getContainer()
	{
		String url = "";
		switch(tipologia_operatore)
		{
		case TIPO_IMPRESE :
		{
			url = "accounts";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			url = "imbarcazioni";
			break ;
		}
		case TIPO_STRUTTURA_ASL :
		{
			url = "asl";
			break ;
		}
		case TIPO_ALLEVAMENTI : 
		{
			url = "allevamenti";
			break ;
		}
		case TIPO_ALLEVAMENTI_SIMILOPU : 
		{
			url = "aziendezootecniche";
			break ;
		}
		
		case TIPO_STABILIMENTI : 
		{
			url = "stabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			url = "operatoriregione";
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			url = "operatoriprivati";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			url = "farmacosorveglianza";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			url = "abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			url = "soa";
			break ;
		}
		case TIPO_OSM : 
		{
			url = "osm";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			url = "osmregistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			url = "trasportoanimali";
			break ;
		}
		case TIPO_CANILI : 
		{
			url = "canili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			url = "colonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			url = "operatori_commerciali";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			url = "operatorinonaltrove";
			break ;
		}
		
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			url = "molluschibivalvi";
			break ;
		}
		case LABORATORI_HACCP : 
		{
			url = "laboratorihaccp";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO : 
		{
			url = "punti_di_sbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO : 
		{
			url = "zonecontrollo";
			break ;
		}
		case TIPO_ACQUE_DI_RETE : 
		{
			url = "acquedirete";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE: 
		{
			url = "aziendeagricole";
			break ;
		}
		case TIPO_RIPRODUZIONE_ANIMALE: 
		{
			url = "riproduzioneanimale";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			url = "canipadronali";
			break ;
		}
		case TIPO_OPU : 
		{
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "aziendeagricoleopu";
			break ;
		}
		case TIPO_OPU_RICHIESTE : 
		{
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			break ;
		}
		case TIPO_API : 
		{
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "apiari";
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			url = "osa";
			break ;
		}
		case PARAFARMACIE : 
		{
			url = "parafarmacie";
			break ;
		}
		case TIPO_NUOVA_ANAGRAFICA: 
		{
			url = "gestioneanagrafica";
			break ;
		}


		}

		return url;


	}

	/**
	 *  Each ticket in a project has its own unique count
	 *
	 * @param  tz       Description of the Parameter
	 * @param  created  Description of the Parameter
	 * @param  type     Description of the Parameter
	 * @return          Description of the Return Value
	 */
	
	
	public void setPermission()
	{
		
		switch(tipologia_operatore)
		{
		case TIPO_IMPRESE :
		{
			permission_ticket = "accounts-accounts";
			break ;
		}
		case TIPO_OPU : 
		{
			permission_ticket = "opu";
			break ;
		}
		case TIPO_OPU_RICHIESTE : 
		{
			permission_ticket = "opu";
			break ;
		}
		case TIPO_API : 
		{
			permission_ticket = "apicoltura";
			break ;
		}
		
		case TIPO_STRUTTURA_ASL :
		{
			permission_ticket = "oia-oia"; 
			break ;
		}
		case TIPO_ALLEVAMENTI : 
		{
			permission_ticket = "allevamenti-allevamenti";
			break ;
		}
		case TIPO_ALLEVAMENTI_SIMILOPU : 
		{
			permission_ticket = "allevamenti-allevamenti";
			break ;
		}
		case TIPO_STABILIMENTI : 
		{
			permission_ticket = "stabilimenti-stabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			permission_ticket = "operatoriregione-operatoriregione";
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			permission_ticket = "operatoriprivati-operatoriprivati";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			permission_ticket = "farmacie-farmacie";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			permission_ticket = "abusivismi-abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			permission_ticket = "soa-soa";
			break ;
		}
		case TIPO_OSM : 
		{
			permission_ticket = "osm-osm";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			permission_ticket = "osmregistrati-osmregistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			permission_ticket = "trasporti-trasporti";
			break ;
		}
		case TIPO_CANILI : 
		{
			permission_ticket = "canili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			permission_ticket = "colonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			permission_ticket = "operatori-commerciali";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO: 
		{
			permission_ticket = "punti_di_sbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO: 
		{
			permission_ticket = "zonecontrollo";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE: 
		{
			permission_ticket = "aziendeagricole";
			break ;
		}
		case TIPO_ACQUE_DI_RETE: 
		{
			permission_ticket = "acquedirete";
			break ;
		}
		case TIPO_RIPRODUZIONE_ANIMALE: 
		{
			permission_ticket = "riproduzioneanimale";
			break ;
		}
		case LABORATORI_HACCP: 
		{
			permission_ticket = "laboratorihaccp-laboratorihaccp";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			permission_ticket = "operatorinonaltrove-operatorinonaltrove";
			break ;
		}
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			permission_ticket = "molluschibivalvi";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			permission_ticket = "imbarcazioni-imbarcazioni";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			permission_ticket = "canipadronali";
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			permission_ticket = "osa-osa";
			break ;
		}
		case PARAFARMACIE : 
		{
			permission_ticket = "parafarmacie-parafarmacie";
			break ;
		}
		case TIPO_NUOVA_ANAGRAFICA : 
		{
			permission_ticket = "gestioneanagrafica-gestioneanagrafica";
			break ;
		}
		default :
		{
			permission_ticket="opu";
			break;
		}
		
		}
		
	}
	
	
	private String action ;
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String actionName)
	{
		action = actionName ;
		
	}
	/**
	 * Ritorna la parte iniziale del url
	 * a seconda della tipologia dell'operatore
	 * @return
	 */
	
	public String getURlDettaglio(int tipologia_operatore,String action)
	{
		String url = "";
		switch(tipologia_operatore)
		{
		
		case TIPO_ARCHIVIATI :
		{
			url = "RicercaArchiviati";
			break ;
		}
		case TIPO_IMPRESE :
		{
			url = "Account";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			url = "Imbarcazioni";
			break ;
		}
		case TIPO_STRUTTURA_ASL :
		{
			url = "Oia";
			break ;
		}
		case TIPO_ALLEVAMENTI : 
		{
			url = "Allevamenti";
			break ;
		}
		case TIPO_ALLEVAMENTI_SIMILOPU : 
		{
			url = "AziendeZootecniche";
			break ;
		}
		case TIPO_STABILIMENTI : 
		{
			url = "Stabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			if (this.getIdDistributore()>0)
				url = "Distributori";
			else
			url = "OperatoriFuoriRegione";
			
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			url = "Operatoriprivati";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			url = "Farmacie";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			url = "Abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			url = "Soa";
			break ;
		}
		case TIPO_OSM : 
		{
			url = "Osm";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			url = "OsmRegistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			url = "Trasporti";
			break ;
		}
		case TIPO_CANILI : 
		{
			url = "Canili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			url = "Colonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			url = "OperatoriCommerciali";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			url = "OpnonAltrove";
			break ;
		}
		
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			url = "MolluschiBivalvi";
			break ;
		}
		case LABORATORI_HACCP : 
		{
			url = "LabHaccp";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO : 
		{
			url = "PuntiSbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO : 
		{
			url = "ZoneControllo";
			break ;
		}
		case TIPO_ACQUE_DI_RETE : 
		{
			url = "AcqueRete";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE: 
		{
			url = "AziendeAgricole";
			break ;
		}
		case TIPO_RIPRODUZIONE_ANIMALE: 
		{
			url = "RiproduzioneAnimale";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			url = "CaniPadronali";
			break ;
		}
		case TIPO_OPU : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			else
			url = "OpuStab";
			
			
			break ;
		}
		case TIPO_OPU_RICHIESTE : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			else
			url = "GisaSuapStab";
			
			
			break ;
		}
		case TIPO_API : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaApiari";
			
			break ;
		}
		case TIPO_API_ATTIVITA : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaAttivita";
			
			break ;
		}
		case TIPO_STABILIMENTI_ARCHIVIATI : 
		{
			url = "RicercaArchiviati";
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			url = "OSAnimali";
			break ;
		}
		case TIPO_NUOVA_ANAGRAFICA : 
		{
			url = "GestioneAnagrafica";
			break ;
		}
		case PARAFARMACIE : 
		{
			url = "Parafarmacie";
			break ;
		}
		case TIPO_SINTESIS : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			else
			url = "StabilimentoSintesisAction";
			
			break ;
		}
		}

		return url;


	}
	
	public String getURlDettaglio()
	{
		String url = "";
		switch(tipologia_operatore)
		{
		case TIPO_IMPRESE :
		{
			url = "Account";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			url = "Imbarcazioni";
			break ;
		}
		case TIPO_STRUTTURA_ASL :
		{
			url = "Oia";
			break ;
		}
		case TIPO_ALLEVAMENTI : 
		{
			url = "Allevamenti";
			break ;
		}
		case TIPO_STABILIMENTI : 
		{
			url = "Stabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			if (this.getIdDistributore()>0)
				url = "Distributori";
			else
			url = "OperatoriFuoriRegione";
			
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			url = "Operatoriprivati";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			url = "Farmacie";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			url = "Abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			url = "Soa";
			break ;
		}
		case TIPO_OSM : 
		{
			url = "Osm";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			url = "OsmRegistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			url = "Trasporti";
			break ;
		}
		case TIPO_CANILI : 
		{
			url = "Canili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			url = "Colonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			url = "OperatoriCommerciali";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			url = "OpnonAltrove";
			break ;
		}
		case TIPO_API_ATTIVITA : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaAttivita";
			
			break ;
		}
		
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			url = "MolluschiBivalvi";
			break ;
		}
		case LABORATORI_HACCP : 
		{
			url = "LabHaccp";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO : 
		{
			url = "PuntiSbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO : 
		{
			url = "ZoneControllo";
			break ;
		}
		case TIPO_ACQUE_DI_RETE : 
		{
			url = "AcqueRete";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE: 
		{
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			else
			url = "AziendeAgricole";
			break ;
		}
		case TIPO_RIPRODUZIONE_ANIMALE: 
		{
			url = "RiproduzioneAnimale";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			url = "CaniPadronali";
			break ;
		}
		case TIPO_OPU : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			else
			url = "OpuStab";
			
			
			break ;
		}
		case TIPO_OPU_RICHIESTE : 
		{
			if (action != null && !"".equals(action)  && !"null".equals(action))
				url = getPrefissoAction(action);
			else
			url = "GisaSuapStab";
			
			
			break ;
		}
		case TIPO_API: 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaApiari";
			
			
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			url = "OSAnimali";
			break ;
		}
		case PARAFARMACIE : 
		{
			url = "Parafarmacie";
			break ;
		}
		case TIPO_NUOVA_ANAGRAFICA : 
		{
			url = "GestioneAnagrafica";
			break ;
		}
		
		case TIPO_SINTESIS : 
		{
			if (action != null && !"".equals(action)  && !"null".equals(action))
				url = getPrefissoAction(action);
			else
			url = "StabilimentoSintesisAction";
			
			
			break ;
		}

		}

		return url;


	}
	
	public String getURlDettaglioanagrafica()
	{
		String url = "";
		switch(tipologia_operatore)
		{
		
		case TIPO_IMPRESE :
		{
			url = "Accounts";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			url = "Imbarcazioni";
			break ;
		}
		case TIPO_STRUTTURA_ASL :
		{
			url = "Oia";
			break ;
		}
		case TIPO_ALLEVAMENTI : 
		{
			url = "Allevamenti";
			break ;
		}
		
		case TIPO_ALLEVAMENTI_SIMILOPU : 
		{
			url = "AziendeZootecniche";
			break ;
		}
		case TIPO_STABILIMENTI : 
		{
			url = "Stabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			url = "OperatoriFuoriRegione";
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			url = "Operatoriprivati";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			url = "Farmacosorveglianza";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			url = "Abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			url = "Soa";
			break ;
		}
		case TIPO_OSM : 
		{
			url = "Osm";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			url = "OsmRegistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			url = "TrasportoAnimali";
			break ;
		}
		case TIPO_CANILI : 
		{
			url = "Canili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			url = "Colonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			url = "OperatoriCommerciali";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			url = "OpnonAltrove";
			break ;
		}
		
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			url = "MolluschiBivalvi";
			break ;
		}
		case LABORATORI_HACCP : 
		{
			//url = "LabHaccp";
			url = "LaboratoriHACCP";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO : 
		{
			url = "PuntiSbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO : 
		{
			url = "ZoneControllo";
			break ;
		}
		case TIPO_ACQUE_DI_RETE : 
		{
			url = "AcqueRete";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE: 
		{
			url = "AziendeAgricole";
			break ;
		}
		case TIPO_RIPRODUZIONE_ANIMALE: 
		{
			url = "RiproduzioneAnimale";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			url = "CaniPadronali";
			break ;
		}
		case TIPO_NUOVA_ANAGRAFICA : 
		{
			url = "GestioneAnagraficaAction";
			break ;
		}
		case TIPO_OPU : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "OpuStab";
			
			
			break ;
		}
		case TIPO_OPU_RICHIESTE : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "GisaSuapStab";
			
			
			break ;
		}
		case TIPO_API : 
		{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaApiari";
			
			
			break ;
		}
		case TIPO_API_ATTIVITA :{
			
			
			if (action != null && !"".equals(action))
				url = getPrefissoAction(action);
			url = "ApicolturaAttivita";
			
			
			break ;
		}
		
		case TIPO_STABILIMENTI_ARCHIVIATI : 
		{
			url = "RicercaArchiviati";
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			url = "OsAnimali";
			break ;
		}
		case PARAFARMACIE : 
		{
			url = "Parafarmacie";
			break ;
		}
 
		
		case TIPO_NOSCIA:
		{
			url = "MainAnagraficaNoScia";
			break;
		}
		case TIPO_SINTESIS : 
		{
			url = "StabilimentoSintesisAction";
			break ;
		}
 

		}
		
		 
		
		return url;


	}

	public void setActionChecklist()
	{
		switch(tipologia_operatore)
		{
		case TIPO_IMPRESE :
		{
			url_checklist = "CheckListImprese";
			break ;
		}
		
		case TIPO_ALLEVAMENTI : 
		{
			url_checklist = "CheckListAllevamenti";
			break ;
		}
		case TIPO_STABILIMENTI : 
		{
			url_checklist = "CheckListStabilimenti";
			break ;
		}
		case TIPO_OPERATORI_FR : 
		{
			url_checklist = "ChecklistOperatoriFr";
			break ;
		}
		case TIPO_PERATORI_PRIV : 
		{
			url_checklist = "ChecklistOperatoriPr";
			break ;
		}
		case TIPO_FARMACOSORVEGLIANZA : 
		{
			url_checklist = "CheckListFarmacosorveglianza";
			break ;
		}
		case TIPO_ABUSIVI : 
		{
			url_checklist = "Abusivismi";
			break ;
		}
		case TIPO_SOA : 
		{
			url_checklist = "CheckListSoa";
			break ;
		}
		case TIPO_OSM : 
		{
			url_checklist = "ChecklistOsmRiconosciuti";
			break ;
		}
		case TIPO_OSM_REG : 
		{
			url_checklist = "ChecklistOsmRegistrati";
			break ;
		}
		case TIPO_TRASPORTI : 
		{
			url_checklist = "CheckListTrasporto";
			break ;
		}
		case TIPO_OPERATORI_NON_ALTROVE: 
		{
			url_checklist = "CheckListOpnonAltrove";
			break ;
		}
		case TIPO_OPERATORI_MOLLUSCHIBIVALVI :
		{
			url_checklist = "CheckListMolluschiBivalvi";
			break ;
		}
		case TIPO_CANILI : 
		{
			url_checklist = "CheckListCanili";
			break ;
		}
		case TIPO_COLONIE : 
		{
			url_checklist = "CheckListColonie";
			break ;
		}
		case TIPO_OPERATORI_COMMERCIALI : 
		{
			url_checklist = "CheckListOperatoriCommerciali";
			break ;
		}
		case TIPO_PUNTI_DI_SBARCO : 
		{
			url_checklist = "CheckListPuntiSbarco";
			break ;
		}
		case TIPO_ZONECONTROLLO : 
		{
			url_checklist = "CheckListZoneControllo";
			break ;
		}
		case TIPO_AZIENDE_AGRICOLE : 
		{
			url_checklist = "CheckListAziendeAgricole";
			break ;
		}
		case TIPO_API : 
		{
			url_checklist = "CheckListApicolturaApiari";
			break ;
		}
		case TIPO_ACQUE_DI_RETE : 
		{
			url_checklist = "CheckListAcqueReteAgricole";
			break ;
		}
		case TIPO_IMBARCAZIONE: 
		{
			url_checklist = "CheckListImbarcazioni";
			break ;
		}
		case LABORATORI_HACCP : 
		{
			url_checklist = "CheckListLabHaccp";
			break ;
		}
		case TIPO_CANIPADRONALI : 
		{
			url_checklist = "CheckListCaniPadronali";
			break ;
		}
		case OPERATORI_SPERIM_ANIMALI : 
		{
			url_checklist = "CheckListOSAnimali";
			break ;
		}
		case PARAFARMACIE : 
		{
			url_checklist = "CheckListParafarmacie";
			break ;
		}
	}
	}
	
public boolean logicdelete(Connection db, String baseFilePath)
	throws SQLException {
if (this.getId() == -1) {
	throw new SQLException("Ticket ID not specified.");
}
boolean commit = db.getAutoCommit();
try {
		PreparedStatement pst = db
			.prepareStatement("update ticket set trashed_date = current_date ,modified=current_date , modifiedby = ? WHERE ticketid = ?");
	pst.setInt(1, this.getModifiedBy());
	pst.setInt(2, this.getId());
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
	/**
	 *  Gets the projectIdByTicket attribute of the Ticket object
	 *
	 * @param  db                Description of the Parameter
	 * @return                   The projectIdByTicket value
	 * @exception  SQLException  Description of the Exception
	 */
	public int getProjectIdByTicket(Connection db) throws SQLException {
		int result = -1;
		PreparedStatement pst = db.prepareStatement("SELECT project_id FROM ticketlink_project WHERE ticket_id = ? ");
		pst.setInt(1, this.getId());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			result = DatabaseUtils.getInt(rs, "project_id");
		}
		rs.close();
		pst.close();
		return result;
	}

	public boolean isAnimaliNonAlimentari() {
		return animaliNonAlimentari;
	}

	public void setAnimaliNonAlimentari(boolean animaliNonAlimentari) {
		this.animaliNonAlimentari = animaliNonAlimentari;
	}

	public int getAnimaliNonAlimentariCombo() {
		return animaliNonAlimentariCombo;
	}

	public void setAnimaliNonAlimentariCombo(int animaliNonAlimentariCombo) {
		this.animaliNonAlimentariCombo = animaliNonAlimentariCombo;
	}
	 private int tipologiaNonConformita ;
		public int getTipologiaNonConformita() {
			return tipologiaNonConformita;
		}

		public void setTipologiaNonConformita(int tipologiaNonConformita) {
			this.tipologiaNonConformita = tipologiaNonConformita;
		}

		public String getLocation_new() {
			return location_new;
		}

		public void setLocation_new(String location_new) {
			this.location_new = location_new;
		}

		public java.sql.Timestamp getDataFineControllo() {
			return dataFineControllo;
		}

		public void setDataFineControllo(java.sql.Timestamp dataFineControllo) {
			this.dataFineControllo = dataFineControllo;
		}
	
}

