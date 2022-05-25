/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.XLSUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

public class Stabilimento extends Operatore {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.Stabilimento.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	    
	    
	
	private Indirizzo sedeOperativa =new Indirizzo();
	private LineaProduttivaList listaLineeProduttive  = new  LineaProduttivaList() ;
	private int idStabilimento ;
	private Timestamp entered;
	private Timestamp modified;
	private int enteredBy ;
	private int modifiedBy ;
	private int idAsl ;
	private boolean flagFuoriRegione = false;
	private boolean flagModificaResidenzaFuoriAslInCorso = false;
	
	public int id_linea_produttiva = -1;
	
	public int getId_linea_produttiva() {
		return id_linea_produttiva;
	}

	public void setId_linea_produttiva(int id_linea_produttiva) {
		this.id_linea_produttiva = id_linea_produttiva;
	}

	
	
	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}
	
	public void setFlagFuoriRegione(
			String flagInRegione) {
		this.flagFuoriRegione = (("NO").equalsIgnoreCase(flagInRegione));
	}
	
	



	public boolean isFlagModificaResidenzaFuoriAslInCorso() {
		return flagModificaResidenzaFuoriAslInCorso;
	}

	public void setFlagModificaResidenzaFuoriAslInCorso(
			boolean flagModificaResidenzaFuoriAslInCorso) {
		this.flagModificaResidenzaFuoriAslInCorso = flagModificaResidenzaFuoriAslInCorso;
	}





	private SoggettoFisico rappLegale = new SoggettoFisico();



	public Stabilimento(){
		
		rappLegale = new SoggettoFisico();
    	sedeOperativa = new Indirizzo() ;
		
	}
	
	public Stabilimento(ResultSet rs) throws SQLException{
		this.buildRecordStabilimento(rs);
	}

	public Stabilimento(Connection db, int idOperatore) throws SQLException, IndirizzoNotFoundException{

		super.queryRecordOperatore(db, idOperatore);
		
		
	}
	
	public Stabilimento(Connection db, int idOperatore,int idStabilimento) throws SQLException, IndirizzoNotFoundException{

		super.queryRecordOperatore(db, idOperatore);
		queryRecordStabilimento(db,idStabilimento);
		
		
	}

	public void queryRecordStabilimento(Connection db , int idStabilimento) throws SQLException, IndirizzoNotFoundException
	{
		if (idStabilimento == -1){
			throw new SQLException("Invalid Account");
		}
		
		PreparedStatement pst = db.prepareStatement("Select * from opu_stabilimento o where o.id = ?");
		pst.setInt(1, idStabilimento);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	    	buildRecordStabilimento(rs);
	    	rappLegale = new SoggettoFisico(db,rs.getInt("id_soggetto_fisico"));
	    	sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
	    	listaLineeProduttive.setIdStabilimento(idStabilimento);
		    listaLineeProduttive.buildListStabilimento(db);
	    }

	    if (idStabilimento == -1) {
	      throw new SQLException(Constants.NOT_FOUND_ERROR);
	    }
	    
	    
	    
	    
	    
	    
	    
	    rs.close();
	    pst.close();
	    buildRappresentante(db);
	    rs.close();
	    pst.close();
		
	}

	
	
	public void setSedeOperativa(Sede sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public SoggettoFisico getRappLegale() {
		return rappLegale;
	}

	public void setRappLegale(SoggettoFisico rappLegale) {
		this.rappLegale = rappLegale;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		if (idAsl!= null && !idAsl.equals(""))
			this.idAsl = Integer.parseInt(idAsl);
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
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

	public LineaProduttivaList getListaLineeProduttive() {
		return listaLineeProduttive;
	}

	public void setListaLineeProduttive(LineaProduttivaList listaLineeProduttive) {
		this.listaLineeProduttive = listaLineeProduttive;
	}




	

	public Indirizzo getSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(Indirizzo sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public boolean insert (Connection db, boolean insertLinee) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;


			this.idStabilimento =DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq");
			sql.append("INSERT INTO opu_stabilimento (");

			
			if (idAsl > 0) {
				sql.append("id_asl, ");
			}
			
			if (idStabilimento > -1) {
				sql.append("id, ");
			}

			if (enteredBy > -1){
				sql.append("entered_by,");
			}

			if (modifiedBy > -1){
				sql.append("modified_by,");
			}
			if (super.getIdOperatore() > -1){
				sql.append("id_operatore,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("id_soggetto_fisico,");
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("id_indirizzo,");
				
			}
			
			if (flagFuoriRegione){
				sql.append("flag_fuori_regione,");
			}
			
			sql.append("entered,modified)");
			sql.append("VALUES (");

			if (idAsl > 0) {
				sql.append("?, ");
			}
			
			if (idStabilimento > -1) {
				sql.append("?,");
			}

			if (enteredBy > -1){
				sql.append("?,");
			}


			if (modifiedBy > -1){
				sql.append("?,");
			}
			if (super.getIdOperatore() > -1){
				sql.append("?,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("?,");
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("?,");
				
			}
			
			
			if (flagFuoriRegione){
				sql.append("?,");
			}


			sql.append("current_date,current_date)");


			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			if (idAsl > 0) {
				pst.setInt(++i,idAsl);
			}
			
			if (idStabilimento > -1) {
				pst.setInt(++i,idStabilimento);
			}

			if (enteredBy > -1){
				pst.setInt(++i, this.enteredBy);
			}


			if (modifiedBy > -1){
				pst.setInt(++i, this.modifiedBy);
			}
			if (super.getIdOperatore() > -1){
				pst.setInt(++i, super.getIdOperatore());
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				pst.setInt(++i, rappLegale.getIdSoggetto());
				
			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				pst.setInt(++i, sedeOperativa.getIdIndirizzo());
				
			}
			
			if (flagFuoriRegione){
				pst.setBoolean(++i, flagFuoriRegione); // di default è false
			}

			pst.execute();
			pst.close();

			this.idStabilimento = DatabaseUtils.getCurrVal(db, "opu_stabilimento_id_seq", idStabilimento);

			
			if (insertLinee){
			
			LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
			Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
			while(itLp.hasNext()){

				LineaProduttiva temp = itLp.next();
				this.aggiungiLineaProduttiva(db, temp);
			}
			}
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
		}
		return true;
	}

	
	public LineaProduttiva aggiungiLineaProduttiva (Connection db , LineaProduttiva lp)
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			int i = 0 ;
			sql.append("INSERT INTO opu_relazione_stabilimento_linee_produttive (id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato,telefono1,telefono2,mail1,mail2,fax, autorizzazione) values (?,?,?,?,?,?,?,?,?,?, ?) ");
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idStabilimento) ;
			pst.setInt(++i, lp.getIdRelazioneAttivita()); //PRIMA pst.setInt(++i, lp.getId());
			
			pst.setTimestamp(++i, lp.getDataInizio());
			pst.setTimestamp(++i, lp.getDataFine());
			pst.setInt(++i, lp.getStato());
			
			if (lp.getIdRelazioneAttivita()== LineaProduttiva.idAggregazioneColonia){
				pst.setString(++i, rappLegale.getTelefono1());
				pst.setString(++i, rappLegale.getTelefono2());
				pst.setString(++i, rappLegale.getEmail());
				pst.setString(++i, rappLegale.getEmail());
				pst.setString(++i, rappLegale.getFax());}
			else
			{
				pst.setString(++i, lp.getTelefono1());
				pst.setString(++i, lp.getTelefono2());
				pst.setString(++i, lp.getMail1());
				pst.setString(++i, lp.getMail2());
				pst.setString(++i, lp.getFax());
			}
			
			
			pst.setString(++i, lp.getAutorizzazione());
			
			
			pst.execute();
			
			lp.setId(DatabaseUtils.getCurrVal(db, "opu_relazione_stabilimento_linee_produttive_id_seq",
					1));
			
			//Salvo le informazioni pertinenti la linea operativa
			lp.insert(db);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
			
		}
		return lp ;
		
	}

	
	
  protected void buildRecordStabilimento(ResultSet rs) throws SQLException {
		  
		  this.setIdStabilimento(rs.getInt("id"));
		  this.setEnteredBy(rs.getInt("entered_by"));
		  this.setModifiedBy(rs.getInt("modified_by"));
		  this.setIdOperatore(rs.getInt("id_operatore"));
		  this.setIdAsl(rs.getInt("id_asl"));
		  this.setFlagFuoriRegione(rs.getBoolean("flag_fuori_regione"));
		  sedeOperativa.setIdIndirizzo(rs.getInt("id_indirizzo"));
		  rappLegale.setIdSoggetto(rs.getInt("id_soggetto_fisico"));
		  flagModificaResidenzaFuoriAslInCorso = rs.getBoolean("flag_modifica_residenza_fuori_asl_in_corso");
		//  buildSede(rs);
		//  buildRappresentanteLegale(rs);

		  
		  
	  }




	public boolean checkAslStabilimentoUtente(UserBean user) {
		return (this.getIdAsl() == user.getSiteId());
	}
	
	public boolean checkAslStabilimentoUtenteOrRoleHd(UserBean user) {
		return (this.getIdAsl() == user.getSiteId()
				|| user.getRoleId() == new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD1")) || user.getRoleId() == new Integer(
				ApplicationProperties.getProperty("ID_RUOLO_HD2")));
	}

public int updateSedeOperativa (Connection db) throws SQLException{

	//System.out.println("INIZIO UPDATE SEDE OPERATIVA");
	int resultCount = 0;
	try {
	PreparedStatement pst = null;
	StringBuffer sql = new StringBuffer();
	sql.append("UPDATE opu_stabilimento SET ");
	sql.append("id_indirizzo = ?,  id_asl = ?, flag_fuori_regione = ?, modified=now(), modified_by=?  where id= ? ");
	
	int i = 0;
	pst = db.prepareStatement(sql.toString());
	
	pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
	pst.setInt(++i, this.getIdAsl());
	pst.setBoolean(++i, this.isFlagFuoriRegione());
	pst.setInt(++i, this.modifiedBy);
	pst.setInt(++i, this.getIdStabilimento());
	resultCount = pst.executeUpdate();
	
	pst.close();
	
	}catch (SQLException e) {
		throw new SQLException(e.getMessage());
	} finally {
		
	}
		return resultCount ;
	}
	
	public int updateResponsabile (Connection db, int newResponsabile) throws SQLException 
	{
		StringBuffer sql = new StringBuffer();
		  int resultCount = -1;
		modifiedBy = enteredBy;
			//}
		//this.idStabilimento =DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq");
		int i=0;
		sql.append("Update opu_stabilimento set id_soggetto_fisico = ? where id= ?");
	
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql.toString());
		
		pst.setInt(++i, newResponsabile);
		pst.setInt(++i, this.getIdStabilimento());
		resultCount = pst.executeUpdate();
		pst.close();
		 }catch (SQLException e) {
				throw new SQLException(e.getMessage());
			    } finally {
			    }

		return resultCount ;
	}
	
	
	public int setModificaResidenzaFuoriAslInCorso(Connection db, boolean flag) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("update opu_stabilimento set flag_modifica_residenza_fuori_asl_in_corso = ? where id = ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		int i = 0;
		pst.setBoolean(++i, flag);
		pst.setInt(++i, this.idStabilimento);
		
		int resultCount = pst.executeUpdate();
		pst.close();
		
		return resultCount;
		
	}
	
	public static double calcolaOccupazioneStabilimento(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException
	{
		double ret = 0.0;
		
		ResultSet rs = null;
		PreparedStatement pst = null;

		pst = con.prepareStatement(" select * from canili_occupazione " +
								  "       where id_rel_stab_lp = ? "  );
		
			pst.setInt(1, idRelazioneAttivitaLineaProduttiva);

			rs = pst.executeQuery();
			
			if(rs.next())
			{
				ret = rs.getDouble("sum");
			}
			
			return ret;
	}
	
	public static int calcolaNumeroCaniVivi(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException
	{
		int ret = 0;
		
		ResultSet rs = null;
		PreparedStatement pst = null;

		pst = con.prepareStatement(" select * from canili_occupazione " +
								  "       where id_rel_stab_lp = ? "  );
		
			pst.setInt(1, idRelazioneAttivitaLineaProduttiva);

			rs = pst.executeQuery();
			
			if(rs.next())
			{
				ret = rs.getInt("numero_cani_vivi");
			}
			
			return ret;
	}
	
	public static ArrayList<String> caniliBloccatiManualmente(int asl, Connection con) throws SQLException
	{
		ArrayList<String> ret =  new ArrayList<>();
		
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		String select = "select ragione_sociale, data_sospensione,* from opu_operatori_denormalizzati_view  o join blocco_sblocco_canile b "
				+ "on o.id_rel_stab_lp=b.id_canile where (  (b.bloccato=true and b.trashed=false and data_riattivazione is null ) or (data_operazione is not null and "
				+ "data_riattivazione>now() and trashed = false)) and b.trashed_date is null ";
		if(asl>0) 
			select += " and id_asl = ? " ;
		pst = con.prepareStatement(select);
		if(asl>0) 
			pst.setInt(1, asl);
		rs = pst.executeQuery();
		while(rs.next()){
			ret.add(rs.getString("ragione_sociale"));
					//+" ["+rs.getString("data_sospensione").substring(0,10)+"]");
		}
		return ret;
	}

	
	
	
	public static ArrayList<String> caniliOccupati(int asl, Connection con) throws SQLException
	{
		ArrayList<String> ret =  new ArrayList<>();
		
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		String select = " select * from canili_occupazione where ";
		
		if(asl>0)
			select += " code = ? and " ;
		select += " (indice >= 100 or numero_cani_vivi >= ? ) ";
		//select +=  " group by o.id_rel_stab_lp, asl.description, o.ragione_sociale, c.mq_disponibili ";

		pst = con.prepareStatement(select);
		
		int i = 1;
		if(asl>0)
		{
			pst.setInt(i, asl);
			i++;
		}
		pst.setInt(i, Integer.parseInt(ApplicationProperties.getProperty("limite_numero_cani_vivi")));

		rs = pst.executeQuery();
		
		while(rs.next())
		{
			ret.add(rs.getString("ragione_sociale"));
		}
		
		return ret;
	}
	
	
	public static boolean checkOccupazione(int idRelazioneAttivitaLineaProduttiva, int idTagliaDaAggiungere, String dataNascita) throws SQLException 
	{
		boolean ok = true;
		Connection con = null;

		try 
		{
			
		if (idRelazioneAttivitaLineaProduttiva > 0 && dataNascita!=null && !dataNascita.equals(""))	
		{

			con = GestoreConnessioni.getConnection();
			
			Operatore canile = new Operatore();
			canile.queryRecordOperatorebyIdLineaProduttiva(con, idRelazioneAttivitaLineaProduttiva);
			Stabilimento stab = (Stabilimento) canile.getListaStabilimenti().get(0);
			LineaProduttiva lpp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			
			java.sql.Timestamp dataNascitaTimestamp = DatabaseUtils.parseDateToTimestamp(dataNascita);
			
			ResultSet rs = null;
			PreparedStatement pst = null;

			pst = con.prepareStatement(" select * from public_functions.checkcanilelibero(?,?,?,?,?) " );
			pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
			pst.setDouble(2, idTagliaDaAggiungere);
			pst.setInt(3, lpp.getIdRelazioneAttivita());
			pst.setInt(4, Integer.valueOf((ApplicationProperties.getProperty("soglia"))));
			pst.setTimestamp(5, dataNascitaTimestamp);
			
			rs = pst.executeQuery();
			rs.next();
			ok = rs.getBoolean(1);
				
			return ok;
		}
		else
		{
			return ok;
		}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			GestoreConnessioni.freeConnection(con);
		}
		return ok;
	}
	
	
	public static void generaXlsCaniInCanile(String path, ServletContext cc){
		 Connection		db		= null;
		 RowSetDynaClass	rsdc	= null;
		 try{
		    	db = GestoreConnessioni.getConnectionNotLogged(cc);
		    	SimpleDateFormat sdf= new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		    	
		    	
		    	String fileName="Estrazione_Totale_Cani_in_canile_"+ sdf.format(new Date())+".xls";
		    	
		    	//int			asl			= Integer.parseInt(context.getRequest().getParameter("aslRif"));
				
		    	String sql="Select * from totali_canili ";
			 
				PreparedStatement 	stat	= db.prepareStatement( sql );
				//esecuzione della query
		    	ResultSet			rs		= stat.executeQuery();

		    	rsdc	= new RowSetDynaClass(rs);
		    	
		    	StringBuffer sbf = new StringBuffer();
		    	sbf.append( "<table border=1>");
		    	
		    	sbf = XLSUtils.dynamicHeaderSb(rs, sbf);
		    	
		    	
		    	List<DynaBean> l = rsdc.getRows();
			 	

		    	//stampa i nomi delle colonne
		    	
		 	
		    	for( int i = 0; i < l.size(); i++ )
		    	{
		    		//Stampa ogni riga sul file
		    		sbf = XLSUtils.dynamicRowSb(l.get(i), rs, sbf);
		    	}
		    	sbf.append( "</table>".getBytes() );
		    	//svuota lo standard di output
		    	//sout.flush();
	            
		    	

	
                BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(path+fileName)));
                
               
               
                //write contents of StringBuffer to a file
                bwr.write(sbf.toString());
               
                //flush the stream
                bwr.flush();
               
                //close the stream
                bwr.close();
               
            
//		    	HttpServletResponse res = context.getResponse();
//		    	res.setContentType( "application/xls" );
//		    	res.setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
//		    	ServletOutputStream sout = res.getOutputStream();
		
		    	
		 	
		       	
		 
		    	
		 }
		 catch(Exception e){
			 
		 
		 }
		 finally{
				GestoreConnessioni.closeConnectionNotLogged(db);
				System.gc();
			
		 }
	}
	
	


}
