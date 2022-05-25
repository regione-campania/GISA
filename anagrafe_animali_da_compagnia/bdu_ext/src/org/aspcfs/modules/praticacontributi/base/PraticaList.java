/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.praticacontributi.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.aspcfs.controller.ApplicationPrefs;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.PagedListInfo;

public class PraticaList extends ArrayList{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8082236127864769721L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.praticacontributi.base.Pratica.class);
	private transient static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MainLogger");
	private List<Pratica> listaPratiche;

	
	//id delle pratiche
	private int id = -1;
	private int numeroDecretoPratica     			= 0;	//numero del decreto della pratica
    private int asl	= -1;
    private int idTipologiaPratica	= -1;
    private java.sql.Timestamp dataDecreto 			= null; 
    private java.sql.Timestamp dataInizio  			= null;
    private java.sql.Timestamp dataFine    			= null;
	private PagedListInfo pagedListInfo    			= null;
	private int comune 				   			    = -1;
    private int statoP                              = -1; 
	
    public PraticaList() {  	
		this.listaPratiche= new ArrayList();
	}
	public void setNumeroDecretoPratica(int numeroDecretoPratica) {
		this.numeroDecretoPratica = numeroDecretoPratica;
	}
	public void setNumeroDecretoPratica(String numeroDecretoPratica) {		
		this.numeroDecretoPratica = Integer.parseInt(numeroDecretoPratica);
	}
	
	public int getNumeroDecretoPratica() {
		return numeroDecretoPratica;
	}
      
	public void setdataDecretoPratica(Timestamp dataDecreto) {
		this.dataDecreto = dataDecreto;
	}
	public void setdataDecretoPratica(String dataDecreto) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsedDate;
		java.sql.Timestamp timestamp = null;
		try {
			parsedDate = dateFormat.parse(dataDecreto);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			this.dataDecreto= timestamp ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Timestamp getdataDecretoPratica() {
		return dataDecreto;
	}
      
    public List<Pratica> getListaPratiche() {
		return listaPratiche;
    }
	
    public PraticaList(Connection db) throws SQLException { }

    public void setListaPratiche(List<Pratica> listaPratiche) {
		this.listaPratiche = listaPratiche;
	}
	
	public List<Pratica> getLista (Connection db, int asl)throws SQLException {
	
		ResultSet rs;
		String sql="";
		
		try {
		
			sql="SELECT asl,data_decreto,descrizione,numero_decreto,data_inizio_sterilizzazione,data_fine_sterilizzazione,id, data_chiusura_pratica from pratiche_contributi where trashed_date is null  "  ;

			PreparedStatement ps = db.prepareStatement(sql.toString());
			rs = DatabaseUtils.executeQuery(db, ps);
			
			Pratica pratica;
			while (rs.next()) {
			
				pratica=new Pratica();
				pratica.setIdAslPratica(rs.getInt(1));
				pratica.setDataDecreto(rs.getTimestamp(2));
				pratica.setOggettoPratica(rs.getString(3));
				pratica.setNumeroDecretoPratica(rs.getInt(4));
				pratica.setDataInizioSterilizzazione(rs.getTimestamp(5));
				pratica.setDataFineSterilizzazione(rs.getTimestamp(6));
				pratica.setId(rs.getInt(7));
				pratica.setData_chiusura_pratica(rs.getTimestamp(8));
				listaPratiche.add(pratica);
			
			}

			rs.close();
			if (ps != null) {
				ps.close();
			}
		}
		catch (SQLException e) {
				db.rollback();
				throw new SQLException(e.getMessage());
		}
		this.setListaPratiche(listaPratiche);
		return listaPratiche;
	}	
	
	//viene invocata dal dwr  nella maschera di inserimento di un cane
	public ArrayList<PraticaDWR> getListDataPadronali(String data,int asl){
		
		Connection conn = null;
		
/*		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/

		ArrayList<PraticaDWR> elencoPratiche =null;
		
		try{
			
			//Class.forName(driver).newInstance();
			conn = GestoreConnessioni.getConnection();
		//	conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			
			String query="Select id, asl, descrizione, numero_decreto, data_decreto, "+
						 "       numero_totale_cani_catturati , numero_totale_cani_padronali , " +
						 "		 data_inizio_sterilizzazione,  data_fine_sterilizzazione, " +
						 "		 numero_restante_cani_catturati, numero_restante_cani_padronali "+
						 "       FROM pratiche_contributi where trashed_date is null and to_date('"+data+"' ,'dd/mm/yyyy')" +
						 "		 between data_inizio_sterilizzazione and data_fine_sterilizzazione and asl= ? and numero_restante_cani_padronali>0 ";
			
			PreparedStatement st = conn.prepareStatement( query );
			st.setInt(1, asl);
		
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			ResultSet rs = st.executeQuery();
				
				
				while(rs.next()){
					pratica_tmp= new PraticaDWR();
					pratica_tmp.setId(rs.getInt("id"));
					int i=pratica_tmp.getId();
					pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
					pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
					pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
					pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
					pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
					pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
					pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
					pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
					pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
					pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
					elencoPratiche.add(pratica_tmp);
					
					String query2="Select comune from pratiche_contributi_comuni where id_pratica=?";
					
					PreparedStatement st2 = conn.prepareStatement( query2 );
					st2.setInt(1, i);
					ResultSet rs2 = st2.executeQuery();
					ArrayList<String> comuni= new ArrayList<String>();
					while(rs2.next()){
						comuni.add(rs2.getString("comune"));
					}
					pratica_tmp.setElenco_comuni(comuni);
							
				}
			//	return elencoPratiche ;	
		}
		catch(Exception e){
			 logger.severe("[CANINA] - EXCEPTION nel metodo getListDataPadronali della classe PraticaList");
			e.printStackTrace();
		}
		finally{
			if( conn != null )
			{
				//	conn.close();
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return elencoPratiche;
		
		
	}

	//viene invocata dal dwr  nella maschera di inserimento di un cane
	public ArrayList<PraticaDWR> getListDataCatturati(String data,int asl){
		
		Connection conn = null;
		
/*		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		
		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/

		ArrayList<PraticaDWR> elencoPratiche =null;
		
		try{
			conn = GestoreConnessioni.getConnection();
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			
			String query="Select id, " +
						 "		 asl," +
						 "		 descrizione, " +
						 "		 numero_decreto, " +
						 "		 data_decreto, " +
						 "		 numero_totale_cani_catturati , " +
						 "       numero_totale_cani_padronali , " +
						 "		 data_inizio_sterilizzazione," +
						 "		 data_fine_sterilizzazione,	" +
						 "		 numero_restante_cani_catturati, "+
						 "       numero_restante_cani_padronali "+
						 "       FROM pratiche_contributi " +
						 "       where trashed_date is null and to_date('"+data+"' ,'dd/mm/yyyy')" +
						 "		 between data_inizio_sterilizzazione and data_fine_sterilizzazione and asl= ? ";
		
//			if (){
	//			query=query+ "and numero_restante_cani_padronali>0 " ;
	//		}
			
			PreparedStatement st = conn.prepareStatement( query );
			st.setInt(1, asl);
		
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			ResultSet rs = st.executeQuery();
				
				
				while(rs.next()){
					pratica_tmp= new PraticaDWR();
					pratica_tmp.setId(rs.getInt("id"));
					int i=pratica_tmp.getId();
					pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
					pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
					pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
					pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
					pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
					pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
					pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
					pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
					pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
					pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
					elencoPratiche.add(pratica_tmp);
					
					String query2="Select comune from pratiche_contributi_comuni where id_pratica=?";
					
					PreparedStatement st2 = conn.prepareStatement( query2 );
					st2.setInt(1, i);
					ResultSet rs2 = st2.executeQuery();
					ArrayList<String> comuni= new ArrayList<String>();
					while(rs2.next()){
						comuni.add(rs2.getString("comune"));
					}
					pratica_tmp.setElenco_comuni(comuni);
							
				}
		//		return elencoPratiche ;	
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListDataCatturati della classe PraticaList");
			e.printStackTrace();
			//return "SystemError";
		}
		finally{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return elencoPratiche;
		
	}
	

	//viene invocata dal dwr  nella maschera di inserimento di un cane
	public ArrayList<PraticaDWR> getListData(String data,int asl, int idCanile, int idSpecie){
		
		Connection conn = null;
/*		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		
		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/

		ArrayList<PraticaDWR> elencoPratiche =null;
		
		try{
			
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			conn = GestoreConnessioni.getConnection();
			String query="Select id, " +
						 "       asl, " +
						 "       descrizione, " +
						 "		 numero_decreto, "+
						 "		 data_decreto, "+
						 "       numero_totale_cani_catturati , " +
						 "       numero_totale_cani_padronali , " +
						 "		 data_inizio_sterilizzazione," +
						 "		 data_fine_sterilizzazione,			  " +
						 "       numero_restante_cani_catturati, "+
						 "       numero_restante_cani_padronali , "+
						 "       numero_restante_gatti_catturati, "+
						 "       numero_restante_gatti_padronali "+
						 "       FROM pratiche_contributi " +
						 "       where trashed_date is null and to_date('"+data+"' ,'dd/mm/yyyy')" +
						 "		 between data_inizio_sterilizzazione and data_fine_sterilizzazione and asl= ?  and data_chiusura_pratica is null "; 
			
			if (idSpecie == Cane.idSpecie){
				query += "and ( numero_restante_cani_catturati>0  or numero_restante_cani_padronali>0)";
			}else if (idSpecie == Gatto.idSpecie){
				query += "and ( numero_restante_gatti_catturati>0  or numero_restante_gatti_padronali>0)";
			}
			
			PreparedStatement st = conn.prepareStatement( query );
			st.setInt(1, asl);
		
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			
			ResultSet rs = st.executeQuery();
				
			
			System.out.println("Query pratica 1: "+st.toString());
				
				while(rs.next()){
					pratica_tmp= new PraticaDWR();
					pratica_tmp.setId(rs.getInt("id"));
					int i=pratica_tmp.getId();
					pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
					pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
					pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
					pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
					pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
					pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
					pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
					pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
					pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
					pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
					pratica_tmp.setGatti_restanti_catturati(rs.getInt("numero_restante_gatti_catturati"));
					pratica_tmp.setGatti_restanti_padronali(rs.getInt("numero_restante_gatti_padronali"));
					elencoPratiche.add(pratica_tmp);
					
					//String query2 = "Select comune from pratiche_contributi_comuni where id_pratica= ? ";		
					String query2 = "Select comuni1.nome from pratiche_contributi_comuni left join comuni1 on (comune = comuni1.id) where id_pratica= ?";
					PreparedStatement st2 = conn.prepareStatement( query2 );
					
					st2.setInt(1, i);
					
					ResultSet rs2 = st2.executeQuery();
				
					
					System.out.println("Query pratica 2: "+st2.toString());
					
					
					ArrayList<String> comuni = new ArrayList<String>();
					ArrayList<String> canili = new ArrayList<String>();

					while(rs2.next()){
						comuni.add(rs2.getString("nome"));
					}
					
					if(idCanile != 0){
						
						String query3 = "Select canile from pratiche_contributi_canili where id_pratica= ?" ; // and org_id_canile = ?";
						PreparedStatement st3 = conn.prepareStatement( query3);
						//Adding union  per la gestione dei canili
						st3.setInt(1, i);
						//st3.setInt(2, idCanile);
						ResultSet rs3 = st3.executeQuery();
						System.out.println("Query pratica 3: "+st3.toString());
						//Aggiunta
						while(rs3.next()){
							canili.add(rs3.getString("canile"));
						}
						
						
					}
					
					
					pratica_tmp.setElenco_canili(canili);
					pratica_tmp.setElenco_comuni(comuni);
					
							
				}
				
			//	return elencoPratiche ;	
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListData della classe PraticaList");
			e.printStackTrace();
		}
		finally{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return elencoPratiche;
		
		
	}
	
	
	
	
public ArrayList<PraticaDWR> getListDataLP(String data,String sesso, String comune, String idVeterinario){
		
		Connection conn = null;

		ArrayList<PraticaDWR> elencoPratiche =null;
		
		try{
			
			conn = GestoreConnessioni.getConnection();
			String query="Select p.id, " +
						 "       p.asl, " +
						 "       p.descrizione, " +
						 "		 p.numero_decreto, "+
						 "		 p.data_decreto, "+
						 "       numero_totale_cani_maschi , " +
						 "       numero_totale_cani_femmina , " +
						 "		 data_inizio_sterilizzazione," +
						 "		 data_fine_sterilizzazione,			  " +
						 "       numero_restante_cani_maschi, "+
						 "       numero_restante_cani_femmina  "+
						 "       FROM pratiche_contributi p ";
			
			
			if(comune!=null && !comune.equals("") && !comune.equals("null"))
				query+= "       join pratiche_contributi_comuni p_c on p_c.comune = ? and p_c.id_pratica =  p.id ";
			
						 
					query+=	"       join pratiche_contributi_veterinari p_v on p_v.veterinario = ? and p_v.id_pratica =  p.id " +
							"       where trashed_date is null and (to_date('"+data+"' ,'dd/mm/yyyy')" +
							"		between data_inizio_sterilizzazione and data_fine_sterilizzazione and data_chiusura_pratica is null  or ? = '')"; 
			
			if (sesso.equalsIgnoreCase("m"))
			{
				query += " and numero_restante_cani_maschi>0 ";
			}
			else if (sesso.equalsIgnoreCase("f")){
				query += " and numero_restante_cani_femmina>0 ";
			}
			
			
			int j =1;
			PreparedStatement st = conn.prepareStatement( query );
			if(comune!=null && !comune.equals("") && !comune.equals("null"))
				st.setInt(j++, Integer.parseInt(comune));
			st.setInt(j++, Integer.parseInt(idVeterinario));
			st.setString(j++, data);
		
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			
			ResultSet rs = st.executeQuery();
				
			
			System.out.println("Query pratica 1: "+st.toString());
				
				while(rs.next())
				{
					pratica_tmp= new PraticaDWR();
					pratica_tmp.setId(rs.getInt("id"));
					int i=pratica_tmp.getId();
					pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
					pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
					pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
					pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
					pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
					pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
					pratica_tmp.setCani_restanti_maschi(rs.getInt("numero_restante_cani_maschi"));
					pratica_tmp.setCani_restanti_femmina(rs.getInt("numero_restante_cani_femmina"));
					pratica_tmp.setTotale_cani_maschi(rs.getInt("numero_totale_cani_maschi"));
					pratica_tmp.setTotale_cani_femmina(rs.getInt("numero_totale_cani_femmina"));
					
					
					String query2 = "Select comuni1.nome from pratiche_contributi_comuni left join comuni1 on (comune = comuni1.id) where id_pratica= ?";
					PreparedStatement st2 = conn.prepareStatement( query2 );
					
					st2.setInt(1, i);
					
					ResultSet rs2 = st2.executeQuery();
				
					
					System.out.println("Query pratica 2: "+st2.toString());
					
					
					ArrayList<String> comuni = new ArrayList<String>();

					while(rs2.next()){
						comuni.add(rs2.getString("nome"));
					}
					
					pratica_tmp.setElenco_comuni(comuni);
					
					elencoPratiche.add(pratica_tmp);
				}
				
			//	return elencoPratiche ;	
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListData della classe PraticaList");
			e.printStackTrace();
		}
		finally{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return elencoPratiche;
		
		
	}
	
public ArrayList<PraticaDWR> getListDataCattura(String data,int asl,boolean cattura, String comune_proprietario, String comune_cattura,int propId, int idDetentore, int tipoDetentore){
		
		Connection conn = null;
		
/*		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
		
		
		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/

		ArrayList<PraticaDWR> elencoPratiche =null;
		
		try{
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			conn = GestoreConnessioni.getConnection();
			
			  
			String query="Select id, " +
						 "       asl, " +
						 "       descrizione, " +
						 "		 numero_decreto, "+
						 "		 data_decreto, "+
						 "       numero_totale_cani_catturati , " +
						 "       numero_totale_cani_padronali , " +
						 "		 data_inizio_sterilizzazione," +
						 "		 data_fine_sterilizzazione,			  " +
						 "       numero_restante_cani_catturati, "+
						 "       numero_restante_cani_padronali "+
						 "       FROM pratiche_contributi " +
						 "       where trashed_date is null and to_date('"+data+"' ,'dd/mm/yyyy')" +
						 "		 between data_inizio_sterilizzazione and data_fine_sterilizzazione and asl= ? and data_chiusura_pratica is null  ";
			
			if (cattura==true && propId == 19){
				query=query + " and numero_restante_cani_catturati > 0";
				
			}
			if (cattura==false && propId != 19){
				query=query + " and numero_restante_cani_padronali > 0";
				
			}
	
			PreparedStatement st = conn.prepareStatement( query );
			st.setInt(1, asl);
			
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			ResultSet rs = st.executeQuery();
			
				
				while(rs.next()){
					pratica_tmp= new PraticaDWR();
					pratica_tmp.setId(rs.getInt("id"));
					int i=pratica_tmp.getId();
					pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
					pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
					pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
					pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
					pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
					pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
					pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
					pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
					pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
					pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
					

					String query2="Select comune from pratiche_contributi_comuni where id_pratica=? ";
					
					PreparedStatement st2 = conn.prepareStatement( query2 );
					st2.setInt(1, i);
					ResultSet rs2 = st2.executeQuery();
					
					ArrayList<String> comuni= new ArrayList<String>();
					ArrayList<String> canili = new ArrayList<String>();

					while(rs2.next()){
						comuni.add(rs2.getString("comune"));
					}
					
					
					/*if (comune_proprietario!=null){
						if(comuni.contains(comune_proprietario)){
							elencoPratiche.add(pratica_tmp);
						}
					}
					else if (comune_cattura!=null){
						if(comuni.contains(comune_cattura)){
							elencoPratiche.add(pratica_tmp);
						}
					}*/
					if(idDetentore != 0 && tipoDetentore == 11){
						
						String query3 = "Select canile from pratiche_contributi_canili where id_pratica= ? and org_id_canile = ?";
						PreparedStatement st3 = conn.prepareStatement( query3);
						//Adding union  per la gestione dei canili
						st3.setInt(1, i);
						st3.setInt(2, idDetentore);
						ResultSet rs3 = st3.executeQuery();
						System.out.println("Query pratica 3: "+st3.toString());
						//Aggiunta
						while(rs3.next()){
							canili.add(rs3.getString("canile"));
							elencoPratiche.add(pratica_tmp);
							pratica_tmp.setElenco_canili(canili);
						}
						
						
						
					}
					
					if (comune_proprietario!=null || comune_cattura != null){
						if(comuni.contains(comune_proprietario) || comuni.contains(comune_cattura)){
							elencoPratiche.add(pratica_tmp);
							pratica_tmp.setElenco_comuni(comuni);
					}
				}
					
					
		
				}
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListDataCattura della classe PraticaList");
			e.printStackTrace();
		}
		finally{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		
		return elencoPratiche;
		
		
	}




public ArrayList<PraticaDWR> getListReport(int chiusura,int asl){
	Connection conn = null;
/*	
	ApplicationPrefs prefs = ApplicationPrefs.application_prefs;
	
	
	String driver	= prefs.get("GATEKEEPER.DRIVER");
	String url		= prefs.get("GATEKEEPER.URL");
	String user		= prefs.get("GATEKEEPER.USER");
	String password	= prefs.get("GATEKEEPER.PASSWORD");*/

	ArrayList<PraticaDWR> elencoPratiche =null;
	
	try{
		
		conn = GestoreConnessioni.getConnection();
		String query="Select id, " +
					 "       asl, " +
					 "       descrizione, " +
					 "		 numero_decreto, "+
					 "		 data_decreto, "+
					 "       numero_totale_cani_catturati , " +
					 "       numero_totale_cani_padronali , " +
					 "		 data_inizio_sterilizzazione," +
					 "		 data_fine_sterilizzazione,			  " +
					 "       numero_restante_cani_catturati, "+
					 "       numero_restante_cani_padronali "+
					 "       FROM pratiche_contributi " +
					 "       where asl= ? "; 
		if (chiusura==1){
			query= query + " and data_chiusura_pratica is null";
		}
		if (chiusura==2){
			query= query + " and data_chiusura_pratica is not null";
		}
		
		query = query + " and trashed_date is not null";
		
		PreparedStatement st = conn.prepareStatement( query );
		
		st.setInt(1, asl);
		
		elencoPratiche = new ArrayList<PraticaDWR>();
		PraticaDWR pratica_tmp= null;
		ResultSet rs = st.executeQuery();
			
			
			while(rs.next()){
				pratica_tmp= new PraticaDWR();
				pratica_tmp.setId(rs.getInt("id"));
				int i=pratica_tmp.getId();
				pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
				pratica_tmp.setOggettoPratica(rs.getString("descrizione"));
				pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
				pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
				pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
				pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
				pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
				pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
				pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
				pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
				elencoPratiche.add(pratica_tmp);
				
				String query2="Select comune from pratiche_contributi_comuni where id_pratica=?";
				
				PreparedStatement st2 = conn.prepareStatement( query2 );
				st2.setInt(1, i);
				ResultSet rs2 = st2.executeQuery();
				ArrayList<String> comuni= new ArrayList<String>();
				while(rs2.next()){
					comuni.add(rs2.getString("comune"));
				}
				pratica_tmp.setElenco_comuni(comuni);
						
			}
		//	return elencoPratiche ;	
	}
	catch(Exception e){
		logger.severe("[CANINA] - EXCEPTION nel metodo getListReport della classe PraticaList");
		e.printStackTrace();
	}
	finally{
		if( conn != null )
		{
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}
	}
	
	return elencoPratiche;
	
	
}

	public int getAslRif() {
			return asl;
	}	
		  
	public void setAslRif(int asl) {
			this.asl = asl;
	}
		  
	public void setAslRif(String tmp) {
		  this.asl = Integer.parseInt(tmp);
	}
	
	public int getIdTipologiaPratica() {
		return idTipologiaPratica;
	}	
	  
	public void setIdTipologiaPratica(int idTipologiaPratica) {
		this.idTipologiaPratica = idTipologiaPratica;
	}
	  
	public void setIdTipologiaPratica(String idTipologiaPratica) {
	  this.idTipologiaPratica = Integer.parseInt(idTipologiaPratica);
}
		
	public void setId(int tmp) {
	      this.id = tmp;
	}
	
	
	public int getId() {
	      return this.id;
	}
	
    public void setId(String tmp) {
	    this.id = Integer.parseInt(tmp);
	}

	public String formatta(Timestamp d) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data_dec = "";
		if (d!=null){
			java.util.Date data = new Date( d.getTime() );
			data_dec=sdf.format(data);
		}
		return data_dec;
	}

	public ArrayList<PraticaDWR> getListPraticheTotale(int asl){
		Connection conn = null;
/*		
		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;

		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/
	
		ArrayList<PraticaDWR> elencoPratiche =null;
		try{
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			conn = GestoreConnessioni.getConnection();
			String query="Select id, " +
			 "       asl, " +
			 "       descrizione, " +
			 "       numero_totale_cani_catturati , " +
			 "  	 numero_totale_gatti_catturati , " +
			 "		 data_inizio_sterilizzazione," +
			 "		 data_fine_sterilizzazione,			  " +
			 "		 numero_totale_cani_padronali ," +
			 "       numero_restante_cani_catturati, "+
			 "       numero_restante_cani_padronali, "+
			 "		 numero_totale_gatti_padronali " +
			 "       FROM pratiche_contributi " +
			 "       where asl= ? and trashed_date is null";

			PreparedStatement st = conn.prepareStatement( query );
			st.setInt(1, asl);
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				pratica_tmp= new PraticaDWR();
				pratica_tmp.setTotale_cani_catturati(rs.getInt("numero_totale_cani_catturati"));
				pratica_tmp.setTotale_cani_padronali(rs.getInt("numero_totale_cani_padronali"));
				pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
				pratica_tmp.setId(rs.getInt("id"));
				int i=pratica_tmp.getId();
				pratica_tmp.setCani_restanti_catturati(rs.getInt("numero_restante_cani_catturati"));
				pratica_tmp.setCani_restanti_padronali(rs.getInt("numero_restante_cani_padronali"));
				pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
				pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
				elencoPratiche.add(pratica_tmp);
				
				String query2="Select comune from pratiche_contributi_comuni where id_pratica=?";
				
				PreparedStatement st2 = conn.prepareStatement( query2 );
				st2.setInt(1, i);
				ResultSet rs2 = st2.executeQuery();
				ArrayList<String> comuni= new ArrayList<String>();
				while(rs2.next()){
					comuni.add(rs2.getString("comune"));
				}
				pratica_tmp.setElenco_comuni(comuni);
						
			}
			//return elencoPratiche ;	
		
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListPraticheTotale della classe PraticaList");
			e.printStackTrace();
		}
		finally{
			if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}
		}
		return elencoPratiche;
		
	
	}
	
	
	
	/**
	   *  Sets the pagedListInfo attribute of the AssetList object
	   *
	   * @param  tmp  The new pagedListInfo value
	   */
	  public void setPagedListInfo(PagedListInfo tmp) {
	    this.pagedListInfo  = tmp;
	  }


	  /**
	   *  Gets the pagedListInfo attribute of the AssetList object
	   *
	   * @return    The pagedListInfo value
	   */
	  public PagedListInfo getPagedListInfo() {
	    return pagedListInfo;
	  }
	  private int parentId = -1;
	  public void setParentId(String tmp) {
		    this.parentId = Integer.parseInt(tmp);
		  }
	  
	  
	  


		  /**
		   *  Sets the parentId attribute of the AssetList object
		   *
		   * @param  tmp  The new parentId value
		   */
		  public void setParentId(int tmp) {
		    this.parentId = tmp;
		  }

	  
	  /**
	   *  Gets the parentId attribute of the AssetList object
	   *
	   * @return    The parentId value
	   */
	  public int getParentId() {
	    return parentId;
	  }

	
	  public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) {
		    	Pratica thisAsset = this.getObject(rs);
		    	thisAsset.popolaComuni(db, thisAsset.getId());
		    	this.add(thisAsset);
		    }
		    
		    
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		    if (buildCompleteHierarchy) {
		      Iterator iter = (Iterator) this.iterator();
		      while (iter.hasNext()) {
		    	  Pratica asset = (Pratica) iter.next();
		        asset.setBuildCompleteHierarchy(true);
		        asset.buildCompleteHierarchy(db);
		        if (asset.getChildList() != null) {
		          this.addAll(asset.getChildList());
		        }
		      }
		    }
		  }
	  
	  
	  public Pratica getObject(ResultSet rs) throws SQLException {
		  Pratica thisAsset = new Pratica(rs);
		    return thisAsset;
		  }
		  
	  private boolean buildCompleteHierarchy = false;

	  /**
	   *  Gets the buildCompleteHierarchy attribute of the AssetList object
	   *
	   * @return    The buildCompleteHierarchy value
	   */
	  public boolean getBuildCompleteHierarchy() {
	    return buildCompleteHierarchy;
	  }


	  /**
	   *  Sets the buildCompleteHierarchy attribute of the AssetList object
	   *
	   * @param  tmp  The new buildCompleteHierarchy value
	   */
	  public void setBuildCompleteHierarchy(boolean tmp) {
	    this.buildCompleteHierarchy = tmp;
	  }


	  /**
	   *  Sets the buildCompleteHierarchy attribute of the AssetList object
	   *
	   * @param  tmp  The new buildCompleteHierarchy value
	   */
	  public void setBuildCompleteHierarchy(String tmp) {
	    this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
	  }

	  
	  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		    ResultSet rs = null;
		    int items = -1;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records
		    sqlCount.append(
		        "SELECT COUNT(DISTINCT (p.ID)) AS recordcount " +
		        "FROM pratiche_contributi p  left join lookup_asl_rif l on l.code=p.asl left join pratiche_contributi_comuni s on s.id_pratica=p.id " +
	        "WHERE p.id > -1 and trashed_date is null ");
		   
		 	    
		    
		    createFilter(sqlFilter, db);
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
		      items = prepareFilter(pst);
		      rs = pst.executeQuery();
			    
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		      //Determine column to sort by
		      if ( sortNumeroDecretoPratica) {
		    	  pagedListInfo.setDefaultSort("p.numero_decreto ", null);
		      }else{
		    	  pagedListInfo.setDefaultSort("p.entered desc ", null);
		      }
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		    } else if( sortNumeroDecretoPratica ) {
		    	sqlOrder.append(" ORDER BY p.numero_decreto ");
		    } else {
		    	sqlOrder.append(" ORDER BY p.entered desc ");}
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {  
		   		sqlSelect.append("SELECT "); 
		    }
		    sqlSelect.append(
		       " p.id, p.asl, p.descrizione, p.numero_decreto, p.data_decreto, " +
		       " p.entered,    p.enteredby, p.modified, p.modifiedby, " +
		       " p.numero_totale_cani_catturati, p.numero_totale_gatti_catturati, p.data_inizio_sterilizzazione, data_fine_sterilizzazione, " +
		       " p.numero_totale_cani_maschi, p.numero_totale_cani_femmina, " +
		       " p.numero_totale_cani_padronali, p.numero_totale_gatti_padronali, " +
		       " p.numero_restante_cani_padronali, numero_restante_gatti_padronali," +
		       " p.numero_restante_cani_maschi, numero_restante_cani_femmina," +
		       " p.numero_restante_cani_catturati, numero_restante_gatti_catturati,l.description, p.data_chiusura_pratica, p.id_tipologia_pratica FROM pratiche_contributi p left join lookup_asl_rif l on l.code=p.asl left join pratiche_contributi_comuni s on s.id_pratica=p.id left join pratiche_contributi_canili c on c.id_pratica=p.id " +
		        "WHERE p.id > -1 ");
		    String sqlGroupBy=" group by p.id, p.asl, descrizione, numero_decreto, data_decreto, p.entered,    p.enteredby, p.modified, p.modifiedby, numero_totale_cani_catturati," +
		    		"  numero_totale_gatti_catturati, data_inizio_sterilizzazione, data_fine_sterilizzazione," +
		    		"  numero_totale_cani_padronali, numero_totale_gatti_padronali," +
		    		"  numero_restante_cani_padronali, numero_restante_gatti_padronali," +
		    		" numero_restante_cani_catturati, numero_restante_gatti_catturati,l.description, p.data_chiusura_pratica, p.id_tipologia_pratica ";

		    pst = db.prepareStatement(
		        sqlSelect.toString() + sqlFilter.toString() + sqlGroupBy + sqlOrder.toString());
		    
		    items = prepareFilter(pst);

		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    rs = pst.executeQuery();
		    
		    System.out.println("Query ricerca pratica contributi: " + pst.toString());
		     
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    return rs;
		  }

	  
	  
	  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
		 
		  if (sqlFilter == null) {
		    sqlFilter = new StringBuffer();
		  }

		  if ( asl > -1) 
		  {
			//Flusso 251: modifiche del 03/08 - INIZIO
			sqlFilter.append(" AND ( (p.id_tipologia_pratica in (1,2) and p.asl = ?) OR (p.id_tipologia_pratica = 3 and s.asl = ?) ) ");
			//Flusso 251: modifiche del 03/08 - FINE
		  }
		  
		  if ( idTipologiaPratica > 0) 
		  {
			  if ( idTipologiaPratica == Pratica.idPraticaCanile) 
				  sqlFilter.append(" AND p.id_tipologia_pratica = ? ");
			  else 
				  sqlFilter.append(" AND p.id_tipologia_pratica in (?,?) ");
			  
		  }
		  //Flusso 251 rollback
		  //else
			  //sqlFilter.append(" AND p.id_tipologia_pratica <> 3 ");

		  if (numeroDecretoPratica >0){
			  sqlFilter.append(" AND p.numero_decreto = ? ");
		  }
			
		  if (dataDecreto!=null){
			  sqlFilter.append(" AND p.data_decreto = ? ");
		  }
		
		  if (dataInizio!=null){
			  sqlFilter.append(" AND p.data_inizio_sterilizzazione = ? ");
		  }
		
		  if (dataFine!=null){
			  sqlFilter.append(" AND p.data_fine_sterilizzazione = ? ");
		  }
		  
		  if (comune > -1){
			  sqlFilter.append(" AND comune =  ? ");
		  }
		  if( statoP==1 )
		    {
		    	sqlFilter.append("AND p.data_chiusura_pratica is  not null ");
		    }
		    else
		    if (statoP==2){
		    	sqlFilter.append("AND p.data_chiusura_pratica is  null ");
		    }
		    else
		    if( statoP==3 ){
		    	//sqlFilter.append(" ");
		    }
		  
	  }
	  
	  private int prepareFilter(PreparedStatement pst) throws SQLException {
		  int i = 0;
		  if (asl > -1 ) {
		    	pst.setInt(++i, asl);
		    	pst.setInt(++i, asl);
		      }
		  
		  
		  
		  if ( idTipologiaPratica == Pratica.idPraticaCanile) 
			  pst.setInt(++i, Pratica.idPraticaCanile);
		  else 
		  {
			  pst.setInt(++i, Pratica.idPraticaComune);
		      pst.setInt(++i, Pratica.idPraticaLP);
		  }

		  if (numeroDecretoPratica > 0 ) {
		    	pst.setInt(++i, numeroDecretoPratica);
		      }
		  if (dataDecreto!=null){
			  pst.setTimestamp(++i, dataDecreto);
		  }
		  if (dataInizio!=null){
			  pst.setTimestamp(++i, dataInizio);
		  }
		  if (dataFine!=null){
			  pst.setTimestamp(++i, dataFine);
		  }
		  if (comune > -1){
			  pst.setInt(++i, comune );
		  }
		 
		  return i;
	  }  
	  
	  
	  private boolean sortNumeroDecretoPratica = false;
	  
	  

	  public boolean isNumeroDecretoPratica() {
	  	return sortNumeroDecretoPratica;
	  }

	  public void setSortNumeroDecretoPratica(boolean sortNumeroDecretoPratica) {
	  	this.sortNumeroDecretoPratica = sortNumeroDecretoPratica;
	  }

	public void setDataInizio(java.sql.Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}


	public void setDataInizio(String dataInizio) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsedDate;
		java.sql.Timestamp timestamp = null;
		try {
			parsedDate = dateFormat.parse(dataInizio);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			this.dataInizio= timestamp ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<PraticaDWR> getListPratiche(Connection conn){
		//Connection conn = null;
/*		
		ApplicationPrefs prefs = ApplicationPrefs.application_prefs;

		String driver	= prefs.get("GATEKEEPER.DRIVER");
		String url		= prefs.get("GATEKEEPER.URL");
		String user		= prefs.get("GATEKEEPER.USER");
		String password	= prefs.get("GATEKEEPER.PASSWORD");*/
	
		ArrayList<PraticaDWR> elencoPratiche =null;
		try{
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection( url + "?user=" + user + "&password=" + password);
			//conn = GestoreConnessioni.getConnection();
			String query="Select id, " +
			 "       numero_decreto " +	
			 "       FROM pratiche_contributi ";

			PreparedStatement st = conn.prepareStatement( query );
			//st.setInt(1, asl);
			elencoPratiche = new ArrayList<PraticaDWR>();
			PraticaDWR pratica_tmp= null;
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				pratica_tmp= new PraticaDWR();
				pratica_tmp.setId(rs.getInt("id"));
				pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
				elencoPratiche.add(pratica_tmp);	
						
			}
			//return elencoPratiche ;	
		
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListPraticheTotale della classe PraticaList");
			e.printStackTrace();
		}
		finally{
		/*	if( conn != null )
			{
				GestoreConnessioni.freeConnection(conn);
				conn = null;
			}*/
		}
		return elencoPratiche;
		
	
	}
	
	public java.sql.Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataFine(java.sql.Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	public void setDataFine(String dataFine) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsedDate;
		java.sql.Timestamp timestamp = null;
		try {
			parsedDate = dateFormat.parse(dataFine);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			this.dataFine= timestamp ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public java.sql.Timestamp getDataFine() {
		return dataFine;
	}

	public void setComune(String comune) {
		this.comune = new Integer(comune).intValue();
	}

	public int getComune() {
		return comune;
	}
	
	public int getStatoP() {
			return statoP;
	}
	  
	public void setStatoP(String b)	{
			int tmp= Integer.parseInt(b);
			this.statoP  = tmp;
	}

	public void setStatoP(int b)	{
		this.statoP  = b;
	}
}