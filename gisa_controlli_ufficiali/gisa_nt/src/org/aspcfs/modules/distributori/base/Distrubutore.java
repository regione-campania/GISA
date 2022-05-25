/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.distributori.base;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.InvalidFieldException;
import org.aspcfs.utils.InvalidFileException;
import org.aspcfs.utils.InvioMassivoDistributori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Distrubutore implements Serializable {
	String nomeImpresa="";
	private OrganizationAddress organizationAddress=new OrganizationAddress();
	private String dataInst ;
	int tipoOperatoreOpu ; 
	private String tipo_operatore ;
	private String descrizioneErrore ;
	private int idLineaAttivita;
	private String descrizioneAlimentoDistribuito;
	private int idStabilimento;
	
	
	
	public int getIdStabilimento() {
		return idStabilimento;
	}


	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}


	public String getDescrizioneAlimentoDistribuito() {
		return descrizioneAlimentoDistribuito;
	}


	public void setDescrizioneAlimentoDistribuito(String descrizioneAlimentoDistribuito) {
		this.descrizioneAlimentoDistribuito = descrizioneAlimentoDistribuito;
	}


	public int getIdLineaAttivita() {
		return idLineaAttivita;
	}


	public void setIdLineaAttivita(int idLineaAttivita) {
		this.idLineaAttivita = idLineaAttivita;
	}


	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}


	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}


	public String getTipo_operatore() {
		return tipo_operatore;
	}


	public void setTipo_operatore(String tipo_operatore) {
		this.tipo_operatore = tipo_operatore;
	}


	public String getDataInst() {
		return dataInst;
	}

	
	public int getTipoOperatoreOpu() {
		return tipoOperatoreOpu;
	}


	public void setTipoOperatoreOpu(int tipoOperatoreOpu) {
		this.tipoOperatoreOpu = tipoOperatoreOpu;
	}


	public void setDataInst(String dataInst) {
		this.dataInst = dataInst;
	}

	public String getNomeImpresa() {
	return nomeImpresa;
}
	private String aslMacchinettaDesc="";
	public String getAslMacchinettaDesc() {
		return aslMacchinettaDesc;
	}

	public void setAslMacchinettaDesc(String aslMacchinettaDesc) {
		this.aslMacchinettaDesc = aslMacchinettaDesc;
	}
	private int aslMacchinetta=-1;
	public int getAslMacchinetta() {
		return aslMacchinetta;
	}

	public void setAslMacchinetta(int aslMacchinetta) {
		this.aslMacchinetta = aslMacchinetta;
	}

	public Distrubutore(){}
	
public void setNomeImpresa(String nomeImpresa) {
	this.nomeImpresa = nomeImpresa;
}

String asl="";
public String getAsl() {
	if(asl!=null && !"".equals(asl))
	return asl;
	return "-";
}
public void setAsl(String aslimpresa) {
	this.asl = aslimpresa;
}

String ubicazione="";
private int org_id=-1;
	public int getOrg_id() {
	return org_id;
}
public void setOrg_id(int org_id) {
	this.org_id = org_id;
}
	private int id=-1;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String matricola;
	private String comune;
	private String indirizzo;
	private String cap;
	private String provincia;
	private String latitudine;
	private String longitudine;
	private int alimentoDistribuito;
	private String descrizioneTipoAlimenti="";
	private String note;
	private int idRelStabLp;
	private Date data;
	
	public int getIdRelStabLp() {
		return idRelStabLp;
	}


	public void setIdRelStabLp(int idRelStabLp) {
		this.idRelStabLp = idRelStabLp;
	}


	public String getMatricola() {
		return matricola;
	}
	public String getUbicazione() {
		return ubicazione;
	}
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}
	public String getDescrizioneTipoAlimenti() {
		return descrizioneTipoAlimenti;
	}
	public void setDescrizioneTipoAlimenti(String descrizioneTipoAlimenti) {
		this.descrizioneTipoAlimenti = descrizioneTipoAlimenti;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public int getAlimentoDistribuito() {
		return alimentoDistribuito;
	}
	public void setAlimentoDistribuito(int alimentoDistribuito) {
		this.alimentoDistribuito = alimentoDistribuito;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
	public static Map<String, Distrubutore> getPresidentsByUniqueIds(int orgid,Connection db){
		Map<String, Distrubutore> m=new HashMap<String, Distrubutore>();
		
		
		try{
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement("select * from distributori_automatici where org_id=?");
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				int id=rs.getInt("id");
				String matricola=rs.getString("matricola");
				String comune=rs.getString("comune");
				String provincia=rs.getString("provincia");
				String latitudine=rs.getString("latitudine");
				String longitudine=rs.getString("longitudine");
				String cap=rs.getString("cap");
				String note=rs.getString("note");
				String indirizzo=rs.getString("indirizzo");
				Date data=rs.getDate("data");
				String description=rs.getString("description");
				int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
				String ubicazione=rs.getString("ubicazione");
				Distrubutore dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
				dist.setId(id);
				m.put(matricola, dist);
				
			}
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return m;
	}
	
	public Distrubutore loadDistributoreOpu(int orgid,int id,Connection db){
		
		  ResultSet rs1=null;
		Distrubutore dist=null;
		try{
		
			
			PreparedStatement pst= db.prepareStatement("select * from distributori_automatici,lookup_tipo_distributore  where alimenti_distribuiti=code and  id_stabilimento=? and id="+id);
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				
				OrganizationAddress add=new OrganizationAddress();




				id=rs.getInt("id");
				String matricola=rs.getString("matricola");
				String comune=rs.getString("comune");

				String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune+"'";
				PreparedStatement pst1=db.prepareStatement(sql);
				rs1=pst1.executeQuery();
				int idAslAppartenenzaComune=16;
				if(rs1.next()){

					idAslAppartenenzaComune=rs1.getInt(1);
				}

				String provincia=rs.getString("provincia");
				
				String cap=rs.getString("cap");
				String note=rs.getString("note");
				String indirizzo=rs.getString("indirizzo");
				Date data=rs.getDate("data");
				String description=rs.getString("description");
				int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
				String ubicazione=rs.getString("ubicazione");
				dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
				dist.setId(id);
				dist.setAslMacchinetta(idAslAppartenenzaComune);
				dist.setDescrizioneTipoAlimenti(description);
				

				add.setStreetAddressLine1(indirizzo);
				add.setCity(comune);
				add.setState(provincia);
				add.setOtherState(provincia);
				add.setZip(cap);
				dist.setOrganizationAddress(add);

			}
			
			
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				if(rs1!=null)
				rs1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dist;
	}
	
	
	public Distrubutore loadDistributore(int orgid,int id,Connection db){
		
		  ResultSet rs1=null;
		Distrubutore dist=null;
		try{
		
			
			PreparedStatement pst= db.prepareStatement("select * from distributori_automatici,lookup_tipo_distributore  where alimenti_distribuiti=code and  org_id=? and id="+id);
			
			pst.setInt(1, orgid);
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				
				OrganizationAddress add=new OrganizationAddress();




				id=rs.getInt("id");
				String matricola=rs.getString("matricola");
				String comune=rs.getString("comune");

				String sql="select code from lookup_site_id ,comuni where lookup_site_id.codiceistat=comuni.codiceistatasl and comuni.comune ilike'"+comune+"'";
				PreparedStatement pst1=db.prepareStatement(sql);
				rs1=pst1.executeQuery();
				int idAslAppartenenzaComune=16;
				if(rs1.next()){

					idAslAppartenenzaComune=rs1.getInt(1);
				}

				String provincia=rs.getString("provincia");
				
				String cap=rs.getString("cap");
				String note=rs.getString("note");
				String indirizzo=rs.getString("indirizzo");
				Date data=rs.getDate("data");
				String description=rs.getString("description");
				int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
				String ubicazione=rs.getString("ubicazione");
				dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
				dist.setId(id);
				dist.setAslMacchinetta(idAslAppartenenzaComune);
				dist.setDescrizioneTipoAlimenti(description);
				

				add.setStreetAddressLine1(indirizzo);
				add.setCity(comune);
				add.setState(provincia);
				add.setOtherState(provincia);
				add.setZip(cap);
				dist.setOrganizationAddress(add);

			}
			
			
			
			
			
			
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				if(rs1!=null)
				rs1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dist;
	}
	
	
	
	public OrganizationAddress getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(OrganizationAddress organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public void updateDistributore(Connection db, int org_id,int id){
		
		try{
			java.sql.Date d=null;
			if(data!=null){
				d=new java.sql.Date(data.getTime());
				
			}
			
String sql="update distributori_automatici set matricola=? , data=? , comune=?, provincia=?,indirizzo=?, cap=?, latitudine=?, longitudine=? , note=?, alimenti_distribuiti=?,ubicazione=? where matricola=? and org_id=? and id="+id;
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, matricola);
				pst.setDate(2, d);
				pst.setString(3, comune);
				pst.setString(4, provincia);
				pst.setString(5, indirizzo);
				pst.setString(6, cap);
				pst.setString(7, latitudine);
				pst.setString(8, longitudine);
				pst.setString(9, note);
				pst.setInt(10, alimentoDistribuito);
				pst.setString(11, ubicazione);
				pst.setString(12, matricola);
				pst.setInt(13, org_id);
			
				pst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	public int checkIfExist(Connection db,String matricola,int org_id) {
		
		int id=-1;
		try{
			
			String sql="select id from distributori_automatici where matricola=? and org_id=? ";
			
			java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
				pst.setString(1, matricola);
				pst.setInt(2, org_id);
				ResultSet rs=pst.executeQuery();
				
				
				if(rs.next()){
					id=rs.getInt("id");
				
				}
			
		}catch(Exception e){
		
			e.printStackTrace();
			
		}
		return id;
		
	}
	
	


public String controllaEsistenzaDistributoreInNuovaAnagrafica(Connection db) throws InvalidFieldException {
	
	String matricolaOut="";
		
		String sql="select imp.*,rslp.id_stabilimento from opu_stabilimento_mobile_distributori imp join opu_relazione_stabilimento_linee_produttive rslp on rslp.id = id_rel_stab_linea where matricola ilike ? ";
		
		try {
		java.sql.PreparedStatement pst=(java.sql.PreparedStatement)
		db.prepareStatement(sql);
		pst.setString(1, matricola.trim());
		ResultSet rs=pst.executeQuery();
	
			if(rs.next()){
				matricolaOut=rs.getString("matricola");
				String dataInizio = rs.getString("data_installazione");
				this.id =rs.getInt("indice");
//				this.idStabilimento=rs.getInt("id_stabilimento");
				
				if (dataInst!=null && dataInst.length()<9){
						String textDate = dataInst;
				        Date actualDate = null;
				  
				        SimpleDateFormat yy = new SimpleDateFormat( "dd/MM/yy" );
				        SimpleDateFormat yyyy = new SimpleDateFormat( "dd/MM/yyyy" );
				  
				        try {
				            actualDate = yy.parse( textDate );
				        }
				        catch ( ParseException pe ) {
				            System.out.println( pe.toString() );
				        }
				        dataInst = yyyy.format( actualDate ).toString();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Timestamp timeDataInizioPresente= new Timestamp(sdf.parse(dataInizio).getTime());
				Timestamp timeDataInizio= new Timestamp(sdf.parse(dataInst).getTime());
				if(timeDataInizio.before(timeDataInizioPresente))
					throw new InvalidFieldException("Data Inizio precedente alla data Attuale");
				
				if(idStabilimento!=rs.getInt("id_stabilimento"))
					throw new InvalidFieldException("KO-DISTRIBUTORE ASSOCIATO A ALTRO STABILIMENTO");

				
					
				
				}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (InvalidFieldException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
	
	return matricolaOut;
	
}
	
	
	
	
	
	

	public Distrubutore (String matricola , String comune,String indirizzo,String cap,String provincia,String latituidine,String longitudine,String note , Date data ,int alimentiDistribuiti,String ubicazione){
		
		this.setAlimentoDistribuito(alimentiDistribuiti);
		this.setNote(note);
		this.setLatitudine(longitudine);
		this.setLongitudine(longitudine);
		this.setData(data);
		this.setIndirizzo(indirizzo);
		this.setMatricola(matricola);
		this.setCap(cap);
		this.setComune(comune);
		this.setProvincia(provincia);
		this.setUbicazione(ubicazione);
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (data!=null)
			{
				dataInst = sdf.format(data);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public Distrubutore (String matricola , String dataInizio,String dettagliUbicazione,String indirizzo,String comune,String cap , int tipoAlimentoDitribuito) throws InvalidFieldException{
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		
		Timestamp dataInizioTimestamp;
		try {
			dataInizioTimestamp = new Timestamp(sdf.parse(dataInizio).getTime());
		} catch (ParseException e) {
		}

		this.setMatricola(matricola);
		this.setDataInst(dataInizio);
		this.setUbicazione(dettagliUbicazione);
		this.setIndirizzo(indirizzo);
		this.setCap(cap);
		this.setComune(comune);
		this.setAlimentoDistribuito(tipoAlimentoDitribuito);
		
		
	}
	
	
	public void controllavalidataCampi(Connection db) throws InvalidFieldException, SQLException
	{
		
		if(idLineaAttivita<=0)
		{
			throw new InvalidFieldException("Linea Attivita Non Valida");
		}
		else
		{
			String sql = "select descrizione from master_list_suap where id ="+idLineaAttivita;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				descrizioneTipoAlimenti=rs.getString("descrizione");
		}
		if(matricola==null || "".equalsIgnoreCase(matricola.trim()))
		{
			
			throw new InvalidFieldException("Matricola non valida");
		}
		if(ubicazione==null || "".equalsIgnoreCase(ubicazione.trim()))
		{
			throw new InvalidFieldException("Ubicazione non valida");
		}
		if(comune==null || "".equalsIgnoreCase(comune.trim()))
		{
			throw new InvalidFieldException("Comune non valido");
		}
		else
		{
			/*Verifica Esistenza comune con ritorni di asl e provincia*/
			
			String verificaComune = "select lp.description as provincia,c.nome as comune , asl.description as asl from comuni1  c join lookup_province lp on lp.code = c.cod_provincia::int join lookup_site_id asl on asl.code = c.codiceistatasl::int where nome ilike ?";
			PreparedStatement pst  = db.prepareStatement(verificaComune);
			pst.setString(1, comune.trim());
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				provincia = rs.getString("provincia");
				asl = rs.getString("asl");
			}
			else
				throw new InvalidFieldException("Comune non valido - non trovato in Anagrafica");
			
		}
		if(indirizzo==null || "".equalsIgnoreCase(indirizzo.trim()))
		{
			throw new InvalidFieldException("Indirizzo non valido");
		}
		
		
		
	}
	
	
	
	
public void update(Connection db,int org_id ) {
		
		try{
		
			String sql="UPDATE  DISTRIBUTORI_AUTOMATICI SET DATA=?,COMUNE=?,PROVINCIA=?,INDIRIZZO=?,CAP=?,LATITUDINE=?,LONGITUDINE=?,ALIMENTI_DISTRIBUITI=?,NOTE=? WHERE  and MATRICOLA=? and id="+id;
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			java.sql.Date d=null;
			if(data!=null){
				d=new java.sql.Date(data.getTime());
			}
			Timestamp t=new Timestamp(d.getTime());
			DatabaseUtils.setTimestamp(pst, 1, t);
			pst.setString(2, comune);
			pst.setString(3,provincia );
			pst.setString(4, indirizzo);
			pst.setString(5, cap);
			pst.setString(6, latitudine);
			pst.setString(7, longitudine);
			pst.setInt(8, alimentoDistribuito);
			pst.setString(9, note);
			pst.setInt(10, org_id);	
			pst.setString(12, matricola);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	

	
	public void insert(Connection db,int org_id ) {
		
		try{
		
			 id = DatabaseUtils.getNextSeqTipo(db, "distributori_id_seq");
			
			String sql="INSERT INTO DISTRIBUTORI_AUTOMATICI (ID,MATRICOLA,DATA,COMUNE,PROVINCIA,INDIRIZZO,CAP,LATITUDINE,LONGITUDINE,ALIMENTI_DISTRIBUITI,NOTE,ORG_ID,ubicazione) VALUES "+
			" ("+id+",?,?,?,?,?,?,?,?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			pst.setString(1, matricola);
			java.sql.Date d=null;
			if(data!=null){
				d=new java.sql.Date(data.getTime());
			}
			if(d!=null){
			Timestamp t=new Timestamp(d.getTime());
			DatabaseUtils.setTimestamp(pst, 2, t);}
			else{
				
				pst.setDate(2,null);
			}
			pst.setString(3, comune);
			pst.setString(4,provincia );
			pst.setString(5, indirizzo);
			pst.setString(6, cap);
			pst.setString(7, latitudine);
			pst.setString(8, longitudine);
			pst.setInt(9, alimentoDistribuito);
			pst.setString(10, note);
			pst.setInt(11, org_id);	
			pst.setString(12, ubicazione);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	} 
	
	
	
public void insertNuovaAnagrafica(Connection db,int org_id ) {
		
		try{
		
			 id = DatabaseUtils.getNextSeqTipo(db, "distributori_id_seq");
			
			String sql="INSERT INTO DISTRIBUTORI_AUTOMATICI (ID,MATRICOLA,DATA,COMUNE,PROVINCIA,INDIRIZZO,CAP,LATITUDINE,LONGITUDINE,ALIMENTI_DISTRIBUITI,NOTE,ORG_ID,ubicazione) VALUES "+
			" ("+id+",?,?,?,?,?,?,?,?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			pst.setString(1, matricola);
			java.sql.Date d=null;
			if(data!=null){
				d=new java.sql.Date(data.getTime());
			}
			if(d!=null){
			Timestamp t=new Timestamp(d.getTime());
			DatabaseUtils.setTimestamp(pst, 2, t);}
			else{
				
				pst.setDate(2,null);
			}
			pst.setString(3, comune);
			pst.setString(4,provincia );
			pst.setString(5, indirizzo);
			pst.setString(6, cap);
			pst.setString(7, latitudine);
			pst.setString(8, longitudine);
			pst.setInt(9, alimentoDistribuito);
			pst.setString(10, note);
			pst.setInt(11, org_id);	
			pst.setString(12, ubicazione);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	
	public void insertopu(Connection db,int org_id ) {
		
		try{
		
			 id = DatabaseUtils.getNextSeqTipo(db, "distributori_id_seq");
			
			String sql="INSERT INTO DISTRIBUTORI_AUTOMATICI (ID,MATRICOLA,DATA,COMUNE,PROVINCIA,INDIRIZZO,CAP,LATITUDINE,LONGITUDINE,ALIMENTI_DISTRIBUITI,NOTE,id_stabilimento,ubicazione) VALUES "+
			" ("+id+",?,?,?,?,?,?,?,?,?,?,?,?)";
			java.sql.PreparedStatement pst= db.prepareStatement(sql);
			pst.setString(1, matricola);
			java.sql.Date d=null;
			if(data!=null){
				d=new java.sql.Date(data.getTime());
			}
			if(d!=null){
			Timestamp t=new Timestamp(d.getTime());
			DatabaseUtils.setTimestamp(pst, 2, t);}
			else{
				
				pst.setDate(2,null);
			}
			pst.setString(3, comune);
			pst.setString(4,provincia );
			pst.setString(5, indirizzo);
			pst.setString(6, cap);
			pst.setString(7, latitudine);
			pst.setString(8, longitudine);
			pst.setInt(9, alimentoDistribuito);
			pst.setString(10, note);
			pst.setInt(11, org_id);	
			pst.setString(12, ubicazione);	
			pst.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		
		
	}
	
	
	public static void deleteDistributore(String matricola,int org_id,Connection db){
		
		try{
			String del="delete from  distributori_automatici where matricola=? and org_id="+org_id;
			
			java.sql.PreparedStatement pst=db.prepareStatement(del);
			
			
			pst.setString(1, matricola);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
public static void deleteDistributoreOpu(String matricola,int org_id,Connection db){
		
		try{
			String del="delete from  distributori_automatici where matricola=? and id_stabilimento="+org_id;
			
			java.sql.PreparedStatement pst=db.prepareStatement(del);
			
			
			pst.setString(1, matricola);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
