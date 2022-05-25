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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.oreilly.servlet.MultipartRequest;

public class Indirizzo extends GenericBean {

	public static final int TIPO_SEDE_LEGALE = 1 ;
	public static final int TIPO_SEDE_OPERATIVA = 5 ;
	public static final int TIPO_SEDE_MOBILE = 7 ;



	private int idIndirizzo = -1;
	private String cap;
	private int comune = -1;
	private String descrizioneComune ;
	private String provincia;
	private String via;
	private String nazione;
	private double latitudine;
	private double longitudine;
	private int enteredBy = -1;
	private int modifiedBy = -1;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private int idProvincia = -1 ;
	private int tipologiaSede = -1;
	private String descrizione_provincia;
	private int idAsl =-1;
	private String descrizioneAsl ="";
	private Timestamp modified;
	private String comuneTesto ;
	private String descrizioneToponimo ;
	private String toponimo ;  
	private String civico;




	public String getDescrizioneToponimo() {
		return descrizioneToponimo;
	}

	public void setDescrizioneToponimo(String descrizioneToponimo) {
		this.descrizioneToponimo = descrizioneToponimo;
	}

	public String getToponimo() {
		 if(toponimo!=null) return toponimo ; else return "";
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}

	public String getCivico() {
		if(civico!=null)
		return civico;
		return "";
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getComuneTesto() {
		if(comuneTesto!=null)
			return comuneTesto.trim();
		return "" ;
	}

	public void setComuneTesto(String comuneTesto) {
		this.comuneTesto = comuneTesto;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	public Indirizzo(){

	}

	public void queryRecord(Connection db , String comune , String indirizzo) throws SQLException
	{
		String sql = "select * from public.suap_dbi_cerca_indirizzo_per_comune_indirizzo(?,?)";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, comune);
		pst.setString(2, indirizzo);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.buildRecord(rs);
	}

	public void setInfoComune(String comune,Connection db) throws SQLException
	{

		PreparedStatement pst = db.prepareStatement("select  id,nome from comuni1 where nome ilike ? ");
		pst.setString(1, comune);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.setComune(rs.getInt(1));
			this.setDescrizioneComune(rs.getString(2));
		}
	}


	
	public Indirizzo(Connection db, int idIndirizzo) throws SQLException{
		/*	if (idIndirizzo == -1){
				throw new SQLException("Invalid Indirizzo");
			}*/

		PreparedStatement pst = db.prepareStatement("select * from public.suap_dbi_cerca_indirizzo_per_id(?)");// and (comuni1.nome!='n.d' or comune_testo!='n.d')");

		pst.setInt(1, idIndirizzo);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			this.idAsl = rs.getInt("code");
			this.descrizioneAsl = rs.getString("description");
		}
		rs.close();
		pst.close();
	}


	public String getIstatComune(Connection db) throws SQLException
	{
		PreparedStatement pst= db.prepareStatement("select istat from comuni1 where id = ?");
		pst.setInt(1, comune);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			return rs.getString(1);
		return "" ;
	}

	private int opu_evita_indirizzi_duplicati(Connection db ) throws SQLException
	{
		int idIndirizzo = -1 ;

		String sql = "select * from public.suap_dbi_verifica_esistenza_indirizzo(?, ?,? ,? ,? , ?)" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, this.comune);
			pst.setString(2, this.provincia);
			pst.setString(3, this.via);
			if(toponimo!=null && !toponimo.equals("") && !toponimo.equals("unefined"))
				pst.setInt(4, Integer.parseInt(toponimo));
			else
				pst.setObject(4, null);
			pst.setString(5, this.civico);
			pst.setString(6, this.cap);
			
			System.out.println("#######################OPERATORE ACTION SUAP #################################### INSERIMENTO DI UN NUVO INDIRIZZO EVITA DUPLICATI QUERY "+pst.toString());

			ResultSet rs = pst.executeQuery();
			
			if (rs.next())
				idIndirizzo = rs.getInt(1);


		}
		catch(SQLException e)
		{
			throw e ;
		}
		return idIndirizzo ;

	}


	public Indirizzo(HttpServletRequest request,Connection db,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");
		LookupList nazioniList= new LookupList(db,"lookup_nazioni");
		this.setNazione(nazioniList.getSelectedValue(Integer.parseInt(request.getParameter("nazioneSedeLegale"))));

		if ("106".equals(request.getParameter("nazioneSedeLegale")))
		{

			if ((String)request.getParameter("searchcodeIdprovincia") != null){
				if (! "".equals(request.getParameter("searchcodeIdprovincia"))  && new Integer ((String)request.getParameter("searchcodeIdprovincia")) > 0)
				{
					this.setProvincia(request.getParameter("searchcodeIdprovincia"));
					this.setIdProvincia(Integer.parseInt(request.getParameter("searchcodeIdprovincia")));
					this.setDescrizione_provincia(request.getParameter("searchcodeIdprovinciainput"));
				}

			}else if ((String)request.getParameter("searchcodeIdprovinciaAsl") != null){
				this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAsl"));
			}
			this.setComune(request.getParameter("searchcodeIdComune"));
			if(request.getParameter("searchcodeIdComuneinput")!=null && !request.getParameter("searchcodeIdComuneinput").equals(""))
			{
				this.setDescrizioneComune(request.getParameter("searchcodeIdComuneinput"));
			}
			else if(request.getParameter("codeIdComune")!=null && !request.getParameter("codeIdComune").equals(""))
			{
				this.setDescrizioneComune(request.getParameter("codeIdComune"));
			}
			this.setVia(request.getParameter("viainput"));


		}
		else
		{
			this.setComuneTesto(request.getParameter("searchcodeIdComuneinput"));
			this.setComune(-1);
			this.setVia(request.getParameter("viainput"));

		}
		this.setToponimo(request.getParameter("toponimoSedeLegale"));
		this.setCivico(request.getParameter("civicoSedeLegale"));

		this.setCap(request.getParameter("cap"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);

		this.insert(db,context);


	}

	public Indirizzo(HttpServletRequest request,MultipartRequest multiPart,LookupList nazioniList,Connection db,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		this.setNazione(nazioniList.getSelectedValue(Integer.parseInt(multiPart.getParameter("nazioneSedeLegale"))));

		if ("106".equals(multiPart.getParameter("nazioneSedeLegale")))
		{
			
			if(multiPart.getParameter("searchcodeIdprovinciaSigla")!=null && !"".equals(multiPart.getParameter("searchcodeIdprovinciaSigla")))
			{
				Provincia p = new Provincia().getProvinciaBySigla(db, multiPart.getParameter("searchcodeIdprovinciaSigla"));
				this.setProvincia(p.getCodice()+"");
				this.setIdProvincia(p.getCodice());
				this.setDescrizione_provincia(p.getDescrizione());
			} 
			else if ((String)multiPart.getParameter("searchcodeIdprovincia") != null)
			{
				if (! "".equals(multiPart.getParameter("searchcodeIdprovincia"))  && new Integer ((String)multiPart.getParameter("searchcodeIdprovincia")) > 0)
				{
					this.setProvincia(multiPart.getParameter("searchcodeIdprovincia"));
					this.setIdProvincia(Integer.parseInt(multiPart.getParameter("searchcodeIdprovincia")));
					this.setDescrizione_provincia(multiPart.getParameter("searchcodeIdprovinciainput"));
				}
			}
			else if ((String)multiPart.getParameter("searchcodeIdprovinciaAsl") != null){
				this.setProvincia((String)multiPart.getParameter("searchcodeIdprovinciaAsl"));
			}
			if(multiPart.getParameter("searchcodeIdComune")!=null && !multiPart.getParameter("searchcodeIdComune").equals(""))
				this.setComune(multiPart.getParameter("searchcodeIdComune"));
			else
				this.setComune(multiPart.getParameter("searchcodeIdComuneId"));
			this.setDescrizioneComune(multiPart.getParameter("searchcodeIdComuneinput"));
			
			if(multiPart.getParameter("searchcodeIdComuneinput")!=null && !multiPart.getParameter("searchcodeIdComuneinput").equals(""))
			{
				this.setDescrizioneComune(multiPart.getParameter("searchcodeIdComuneinput"));
			}
			else if(multiPart.getParameter("codeIdComune")!=null && !multiPart.getParameter("codeIdComune").equals(""))
			{
				this.setDescrizioneComune(multiPart.getParameter("codeIdComune"));
			}
			
			
			this.setVia(multiPart.getParameter("viainput"));


		}
		else
		{
			this.setComuneTesto(multiPart.getParameter("searchcodeIdComuneinput"));
			this.setComune(-1);
			this.setVia(multiPart.getParameter("viainput"));

		}
		if(multiPart.getParameter("toponimoSedeLegale")!=null && !multiPart.getParameter("toponimoSedeLegale").equals(""))
			this.setToponimo(multiPart.getParameter("toponimoSedeLegale"));
		else
			this.setToponimo(multiPart.getParameter("toponimoSedeLegaleId"));
		
		this.setCivico(multiPart.getParameter("civicoSedeLegale"));

		this.setCap(multiPart.getParameter("cap"));
		
		if (this.cap == null || this.cap.equals(""))
			this.setCap(multiPart.getParameter("presso"));
		
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);

		this.insert(db,context);


	}

	public Indirizzo(HttpServletRequest request,LookupList nazioniList,Connection db,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		int idNazione = -1;
		if(request.getParameter("nazioneSedeLegale")!=null && !request.getParameter("nazioneSedeLegale").equals(""))
			idNazione = Integer.parseInt(request.getParameter("nazioneSedeLegale"));
		else if(request.getParameter("nazioneSedeLegaleId")!=null && !request.getParameter("nazioneSedeLegaleId").equals(""))
			idNazione = Integer.parseInt(request.getParameter("nazioneSedeLegaleId"));
		this.setNazione(nazioniList.getSelectedValue(idNazione));

		if (106==idNazione)
		{

			if ((String)request.getParameter("searchcodeIdprovincia") != null){
				if (! "".equals(request.getParameter("searchcodeIdprovincia"))  && new Integer ((String)request.getParameter("searchcodeIdprovincia")) > 0)
				{
					this.setProvincia(request.getParameter("searchcodeIdprovincia"));
					this.setIdProvincia(Integer.parseInt(request.getParameter("searchcodeIdprovincia")));
					this.setDescrizione_provincia(request.getParameter("searchcodeIdprovinciainput"));
				}

			}else if ((String)request.getParameter("searchcodeIdprovinciaAsl") != null){
				this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAsl"));
			}
			else if(request.getParameter("searchcodeIdprovinciaSigla")!=null && !request.getParameter("searchcodeIdprovinciaSigla").equals(""))
			{
				Provincia p = new Provincia().getProvinciaBySigla(db, request.getParameter("searchcodeIdprovinciaSigla"));
				this.setProvincia(p.getCodice()+"");
				this.setIdProvincia(p.getCodice());
				this.setDescrizione_provincia(p.getDescrizione());
			}
			if(request.getParameter("searchcodeIdComune")!=null && !request.getParameter("searchcodeIdComune").equals("") )
			{
				this.setComune(request.getParameter("searchcodeIdComune"));
				this.setDescrizioneComune(request.getParameter("searchcodeIdComuneinput"));
			}
			else
			{
				this.setComune(request.getParameter("searchcodeIdComuneId"));
				this.setDescrizioneComune(request.getParameter("searchcodeIdComuneinput"));
			}
			
			this.setVia(request.getParameter("viainput"));


		}
		else
		{
			this.setComuneTesto(request.getParameter("searchcodeIdComuneinput"));
			this.setComune(-1);
			this.setVia(request.getParameter("viainput"));

		}
		
		if(request.getParameter("toponimoSedeLegale")!=null)
			this.setToponimo(request.getParameter("toponimoSedeLegale"));
		else
			this.setToponimo(request.getParameter("toponimoSedeLegaleId"));
		
		this.setCivico(request.getParameter("civicoSedeLegale"));

		this.setCap(request.getParameter("presso"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);

		this.insert(db,context);


	}

	public void getSedeOperativaSuap(HttpServletRequest request,LookupList nazioniList,Connection db,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		this.setNazione("Italia");



		if ((String)request.getParameter("searchcodeIdprovinciaStab") != null){
			if (! "".equals(request.getParameter("searchcodeIdprovinciaStab"))  && new Integer ((String)request.getParameter("searchcodeIdprovinciaStab")) > 0)
			{
				this.setProvincia(request.getParameter("searchcodeIdprovinciaStab"));
				this.setIdProvincia(Integer.parseInt(request.getParameter("searchcodeIdprovinciaStab")));
				this.setDescrizione_provincia(request.getParameter("searchcodeIdprovinciaStabinput"));
			}

		}
		else if(request.getParameter("searchcodeIdprovinciaStabSigla") != null && !((String)request.getParameter("searchcodeIdprovinciaStabSigla")).equals(""))
		{
			Provincia p = new Provincia().getProvinciaBySigla(db, request.getParameter("searchcodeIdprovinciaStabSigla"));
			this.setProvincia(p.getCodice()+"");
			this.setIdProvincia(p.getCodice());
			this.setDescrizione_provincia(p.getDescrizione());
		}
		else if ((String)request.getParameter("searchcodeIdprovinciaAslStab") != null){
			this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAslStab"));
		}
		if(request.getParameter("searchcodeIdComuneStab")!=null && !"-1".equals(request.getParameter("searchcodeIdComuneStab")))
		{
			this.setComune(request.getParameter("searchcodeIdComuneStab"));
			this.setDescrizioneComune(request.getParameter("searchcodeIdComuneStabinput"));
		}
		else if(request.getParameter("idComuneSedeOperativa")!=null && !"-1".equals(request.getParameter("idComuneSedeOperativa")))
		{
			this.setComune(request.getParameter("idComuneSedeOperativa"));
			this.setDescrizioneComune(request.getParameter("searchcodeIdComuneStabinput"));
		}
		
		this.setVia(request.getParameter("viaStabinput"));

		if(request.getParameter("toponimoSedeOperativa")!=null)
			this.setToponimo(request.getParameter("toponimoSedeOperativa"));
		else
			this.setToponimo(request.getParameter("toponimoSedeOperativaId"));
		
		this.setCivico(request.getParameter("civicoSedeOperativa"));


		this.setCap(request.getParameter("capStab"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);


		if (this.getComune()<=0 &&  ( this.getVia() == null || this.getVia().equals("") ))
			this.setIdIndirizzo(-1);
		else
			this.insert(db,context);


	}

	
	
	
	
	public void getSedeOperativaSuap(HttpServletRequest request, MultipartRequest multi,LookupList nazioniList,Connection db,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		this.setNazione("Italia");



		if ((String)multi.getParameter("searchcodeIdprovinciaStab") != null){
			if (! "".equals(multi.getParameter("searchcodeIdprovinciaStab"))  && new Integer ((String)multi.getParameter("searchcodeIdprovinciaStab")) > 0)
			{
				this.setProvincia(multi.getParameter("searchcodeIdprovinciaStab"));
				this.setIdProvincia(Integer.parseInt(multi.getParameter("searchcodeIdprovinciaStab")));
				this.setDescrizione_provincia(multi.getParameter("searchcodeIdprovinciaStabinput"));
			}

		}else if ((String)multi.getParameter("searchcodeIdprovinciaAslStab") != null){
			this.setProvincia((String)multi.getParameter("searchcodeIdprovinciaAslStab"));
		}
		if(multi.getParameter("searchcodeIdComuneStab")!=null && !"-1".equals(multi.getParameter("searchcodeIdComuneStab")))
		{
			this.setComune(multi.getParameter("searchcodeIdComuneStab"));
			this.setDescrizioneComune(multi.getParameter("searchcodeIdComuneStabinput"));
		}
		else if(multi.getParameter("idComuneSedeOperativa")!=null && !"-1".equals(multi.getParameter("idComuneSedeOperativa")))
		{
			this.setComune(multi.getParameter("idComuneSedeOperativa"));
			this.setDescrizioneComune(multi.getParameter("searchcodeIdComuneStabinput"));
		}
		else
		{
			this.setIdIndirizzo(-1);
		}
		
		this.setVia(multi.getParameter("viaStabinput"));

		if(multi.getParameter("toponimoSedeOperativa")!=null && !multi.getParameter("toponimoSedeOperativa").equals(""))
			this.setToponimo(multi.getParameter("toponimoSedeOperativa"));
		else
			this.setToponimo(multi.getParameter("toponimoSedeOperativaId"));
		
		this.setCivico(multi.getParameter("civicoSedeOperativa"));


		this.setCap(multi.getParameter("capStab"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);


		if (this.getComune()<=0 &&  ( this.getVia() == null || this.getVia().equals("") ))
			this.setIdIndirizzo(-1);
		else
			this.insert(db,context);


	}


	public Indirizzo(HttpServletRequest request,Connection db,boolean flagDia,ActionContext context) throws SQLException{

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		if ((String)request.getParameter("searchcodeIdprovinciaSL") != null){
			if (new Integer ((String)request.getParameter("searchcodeIdprovinciaSL")) > -1)
				this.setProvincia(request.getParameter("searchcodeIdprovinciaSL"));
			else
				this.setProvincia(request.getParameter("searchcodeIdprovinciaTestoSL"));
		}else if ((String)request.getParameter("searchcodeIdprovinciaAsSLl") != null){
			this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAslSL"));
		}
		this.setComune(request.getParameter("searchcodeIdComuneSL"));
		this.setVia(request.getParameter("viaTestoSL"));
		this.setLatitudine(request.getParameter("latitudineSL"));
		this.setLongitudine(request.getParameter("longitudineSL"));
		this.setCap(request.getParameter("capSL"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);
		if (this.getComune()<=0 &&  ( this.getVia() == null || this.getVia().equals("") ))
			this.setIdIndirizzo(-1);
		else
			this.insert(db,context);


	}




	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}

	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}

	public String getDescrizione_provincia() {
		return (descrizione_provincia!=null) ? descrizione_provincia.trim() : "";
	}

	public void setDescrizione_provincia(String descrizione_provincia) {
		this.descrizione_provincia = descrizione_provincia;
	}

	public String getDescrizioneComune() {
		return (descrizioneComune!=null) ? descrizioneComune.trim() : "";
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public int getIdIndirizzo() {
		return idIndirizzo;
	}


	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}



	public String getCap() {
		return (cap!=null) ? cap.trim() : "";
	}


	public void setCap(String cap) {
		this.cap = (cap!=null) ? cap.trim() : "";
	}



	public int getComune() {

		return comune;
	}


	public void setComune(String comune) {
		if (comune != null && !comune.equals("") && !comune.equals("undefined") && !comune.equals("null"))
			this.comune = new Integer(comune).intValue();
	}




	public void setComune(int idComune){
		this.comune =idComune;
	}




	public String getProvincia() {
		return (provincia!=null) ? provincia.trim() : "" ;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getVia() {
		return (via!=null) ? via.trim() : "" ;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getNazione() {
		if(nazione!=null)
			return nazione.trim();
		return "";
	}





	public void setNazione(String nazione) {
		this.nazione = nazione;
	}






	public double getLatitudine() {
		return latitudine;
	}





	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}





	public double getLongitudine() {
		return longitudine;
	}





	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}



	public void setLatitudine(String latitude) {
		try {
			this.latitudine = Double.parseDouble(latitude.replace(',', '.'));
		} catch (Exception e) {
			this.latitudine = 0;
		}
	}


	public void setLongitudine(String longitude) {
		try {
			this.longitudine = Double.parseDouble(longitude.replace(',', '.'));
		} catch (Exception e) {
			this.longitudine = 0;
		}
	}





	public int getTipologiaSede() {
		return tipologiaSede;
	}





	public void setTipologiaSede(int tipologiaSede) {
		this.tipologiaSede = tipologiaSede;
	}


	public void setTipologiaSede(String tipologiaSede) {
		this.tipologiaSede = new Integer(tipologiaSede).intValue();
	}


	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getIpEnteredBy() {
		return ipEnteredBy;
	}


	public void setIpEnteredBy(String ipEnteredBy) {
		this.ipEnteredBy = ipEnteredBy;
	}


	public String getIpModifiedBy() {
		return ipModifiedBy;
	}


	public void setIpModifiedBy(String ipModifiedBy) {
		this.ipModifiedBy = ipModifiedBy;
	}




	public boolean insert(Connection db,ActionContext context) throws SQLException{
		StringBuffer sql = new StringBuffer();
		try{

			//Schiatta in import impresa indivisuale perchè non viene passata la sede legale e quindi cap null, andando a recuperare da comuni1 il valore 80100 per Napoli, schiatta per il vincolo check_cap_na
			//Poichè per l'impresa individuale la sede legale non sarà usata, pensiamo che si possa mettere tranquillamente il cap null
			//if (cap==null || cap.trim().equals(""))
				//cap = ComuniAnagrafica.getCap(db, this.comune);
			System.out.println("#######################OPERATORE ACTION SUAP #################################### INSERIMENTO DI UN NUVO INDIRIZZO EVITA DUPLICATI ");

			idIndirizzo = this.opu_evita_indirizzi_duplicati(db);
			
			System.out.println("#######################OPERATORE ACTION SUAP ####################################  FINE INSERIMENTO DI UN NUVO INDIRIZZO EVITA DUPLICATI ");

			if (idIndirizzo>0)
				return true ;
			
			
			if (idIndirizzo <=0 )
			{
				//Controllare se c'e' gia' soggetto fisico, se no inserirlo
				idIndirizzo = DatabaseUtils.getNextSeq(db, "opu_indirizzo_id_seq");

				sql.append("INSERT INTO opu_indirizzo (toponimo,civico,");

				if (idIndirizzo > -1)
					sql.append("id,");

				sql.append("via, cap, comune, provincia, nazione");


				sql.append(", latitudine");



				sql.append(", longitudine");

				sql.append(", comune_testo");


				sql.append(")");

				sql.append("VALUES (?,?,?,?,?,?,?,?");


				if (idIndirizzo > -1) {
					sql.append(",?");
				}


				sql.append(", ?");



				sql.append(", ?");



				sql.append(")");

				int i = 0;

				PreparedStatement pst = db.prepareStatement(sql.toString());

				if (toponimo!=null && !toponimo.equals(""))
					pst.setInt(++i, Integer.parseInt(toponimo));
				else
					pst.setObject(++i, null);


				pst.setString(++i, civico);
				if (idIndirizzo > -1) {
					pst.setInt(++i, idIndirizzo);
				}


				pst.setString(++i, this.via);
				pst.setString(++i, this.cap);
				if(comune!=0)
					pst.setInt(++i, this.comune);
				else
					pst.setInt(++i, -1);
				pst.setString(++i, this.provincia);
				pst.setString(++i, this.nazione);
				pst.setDouble(++i, this.latitudine);
				pst.setDouble(++i, this.longitudine);
				pst.setString(++i, this.comuneTesto);


				pst.execute();
				pst.close();

			}
			else
			{
				idIndirizzo=-1;
			}
			//	JOptionPane.showMessageDialog(null,pst.toString()+"\nINSERT INTO opu_indirizzo SET via="+this.via+" COMUNE= "+this.comune+" PROVINCIA= "+this.provincia+" CAP= "+this.cap+" ID="+idIndirizzo+"\n Stringhe: Provincia: "+this.descrizione_provincia+" Comune: "+this.descrizioneComune);



		}catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}







	public Indirizzo (ResultSet rs) throws SQLException {

		buildRecord(rs);

	}




	protected void buildRecord(ResultSet rs) throws SQLException {

		this.descrizione_provincia = rs.getString("descrizione_provincia");
		this.idIndirizzo = rs.getInt("id");
		this.comune = rs.getInt("comune");
		if(rs.getString("cod_provincia")!=null)
			this.idProvincia = Integer.parseInt(rs.getString("cod_provincia"));
		this.provincia = rs.getString("provincia");
		this.cap = rs.getString("cap");
		if (cap!=null)
			cap=cap.trim();
		this.descrizioneComune = rs.getString("descrizione_comune");
		this.via = rs.getString("via");
		this.nazione = rs.getString("nazione");
		this.latitudine = rs.getDouble("latitudine");
		this.longitudine = rs.getDouble("longitudine");
		try{
			this.tipologiaSede = rs.getInt("tipologia_sede");
		}catch (org.postgresql.util.PSQLException e){

		}
		try{
			this.comuneTesto = rs.getString("comune_testo");
		}catch (org.postgresql.util.PSQLException e){

		}
		toponimo=rs.getString("toponimo");
		civico=rs.getString("civico");

		try{
			this.descrizioneToponimo = rs.getString("descrizione_toponimo");
		}
		catch (org.postgresql.util.PSQLException e){
			e.printStackTrace();
		}

	}



	public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0 ; i <campi.length; i++)
		{
			String nomeCampo = campi[i].getName();

			for (int j=0; j<metodi.length; j++ )
			{
				if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
				{
					if(nomeCampo.equalsIgnoreCase("via"))
					{
						//map.put("descrizione", metodi[j].invoke(this));
						map.put("descrizionevia", metodi[j].invoke(this));
						map.put("value", metodi[j].invoke(this));
						map.put("label", metodi[j].invoke(this));
					}
					else{
						if(nomeCampo.equalsIgnoreCase("idIndirizzo"))
						{
							//map.put("codice", metodi[j].invoke(this));
							map.put("codicevia", metodi[j].invoke(this));
							map.put("idindirizzo", metodi[j].invoke(this));
						}
						else
						{
							map.put(nomeCampo, (""+metodi[j].invoke(this)).trim());
						}
					}
				}

			}

		}

		return map ;

	}	





	public HashMap<String, Object> getHashmapIndirizzo() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0 ; i <campi.length; i++)
		{
			String nomeCampo = campi[i].getName();

			for (int j=0; j<metodi.length; j++ )
			{
				if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
				{

					//map.put("descrizione", metodi[j].invoke(this));
					map.put(nomeCampo,new String (""+metodi[j].invoke(this)+""));



				}

			}

		}

		return map ;

	}	



	public String toString()
	{
		String descrizione = "" ;

		if(via!=null)
			descrizione = via;
		if (cap != null)
			descrizione += ", " + cap;
		if (descrizioneComune!=null)
			descrizione += " " + descrizioneComune ;
		if(descrizione_provincia!=null )
			descrizione+=" , "+descrizione_provincia ;

		return descrizione ;
	}

	//IN CASO DI MODIFICA INDIRIZZO, CREA NUOVA ENTRY PER TENERE TRACCIA DEL CAMBIO
	public boolean insertModificaIndirizzo(Connection db, int operatoreId, int oldIndirizzoId, int newIndirizzoId, int userId, int tipoSede) throws SQLException{
		StringBuffer sql = new StringBuffer();
		java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		java.sql.Timestamp data_date = new Timestamp(timeNow.getTime());
		try{

			sql.append("INSERT INTO opu_indirizzo_history (id_operatore, id_vecchio_indirizzo, id_nuovo_indirizzo, utente_modifica, data_modifica, tipo_sede");
			sql.append(")");

			sql.append("VALUES (?,?,?, ?, ?, ?");
			sql.append(")");

			int i = 0;

			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, operatoreId);
			pst.setInt(++i, oldIndirizzoId);
			pst.setInt(++i, newIndirizzoId);
			pst.setInt(++i, userId);
			pst.setTimestamp(++i, data_date);
			pst.setInt(++i, tipoSede); //1: LEGALE 2: OPERATIVA 3: RESPONSABILE


			pst.execute();
			pst.close();



		}catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public void setIdComuneFromdescrizione(Connection db,String descrizioneComune) throws SQLException
	{
		String sql = "select comuni1.id , lp.code as idProvincia,lp.description as provincia,comuni1.nome from comuni1 join lookup_province lp on lp.code = comuni1.cod_provincia::int where comuni1.nome ilike ?";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, descrizioneComune);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
		{
			this.comune = rs.getInt(1);
			this.idProvincia=rs.getInt(2);
			this.descrizione_provincia=rs.getString(3);	
			this.descrizioneComune=rs.getString(4);
		}

	}

	public int compareTo(Indirizzo otherIndirizzo) {

		String nazione = otherIndirizzo.getNazione();
		String provincia = otherIndirizzo.getProvincia()+"";
		String comune = otherIndirizzo.getDescrizioneComune();
		String comuneTesto = otherIndirizzo.getComuneTesto();
		String via = otherIndirizzo.getVia();

		if ( 
			
				(this.getDescrizioneComune()!=null && this.getDescrizioneComune().equalsIgnoreCase(comune) || this.getComuneTesto().equalsIgnoreCase(comuneTesto)) &&

				this.getVia() != null && this.getVia().equalsIgnoreCase(via) &&
				(this.getCivico() != null && this.getCivico().equalsIgnoreCase(otherIndirizzo.getCivico())) &&
				(this.getToponimo().equalsIgnoreCase(otherIndirizzo.getToponimo()))
				)
		{
			return 0;
		}


		return 1;



	}
	
	
	public int compareTo(Indirizzo thisInd,Indirizzo otherIndirizzo) {

		String nazione = otherIndirizzo.getNazione();
		String provincia = otherIndirizzo.getDescrizione_provincia()+"";
		String comune = otherIndirizzo.getDescrizioneComune();
		String comuneTesto = otherIndirizzo.getComuneTesto();
		String via = otherIndirizzo.getVia();

		if ( (thisInd.getNazione()!= null && thisInd.getNazione().equalsIgnoreCase(nazione)) 		&&
				(thisInd.getDescrizione_provincia() != null && thisInd.getDescrizione_provincia().equalsIgnoreCase(provincia))		&&
				(thisInd.getDescrizioneComune()!=null && thisInd.getDescrizioneComune().equalsIgnoreCase(comune) || thisInd.getComuneTesto().equalsIgnoreCase(comuneTesto)) &&

				thisInd.getVia() != null && thisInd.getVia().trim().equalsIgnoreCase(via.trim()) 
				)
		{
			return 0;
		}


		return 1;



	}


	public void updateCoordinate(Connection db) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("update opu_indirizzo set latitudine = ? , longitudine = ? where id = ?");
		pst.setDouble(1, latitudine);
		pst.setDouble(2, longitudine);
		pst.setInt(3, idIndirizzo);
		pst.execute();
	}


}



