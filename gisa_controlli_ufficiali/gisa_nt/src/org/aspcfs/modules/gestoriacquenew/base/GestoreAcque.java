/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestoreAcque {
	
	public HashMap<String,Integer> getComuniAccettatiPerPP ()
	{
		return this.comuniAccettatiPerPP;
	}
	public void setcomuniAccettatiPerPP(HashMap<String,Integer> cc)
	{
		this.comuniAccettatiPerPP = cc;
	}
	
	public ArrayList<AnagraficaGestore> getAnagraficheDelGestore() {
		return anagraficheDelGestore;
	}
	public void setAnagraficheDelGestore(ArrayList<AnagraficaGestore> anagraficheDelGestore) {
		this.anagraficheDelGestore = anagraficheDelGestore;
	}
	
	public ArrayList<PuntoPrelievo> getPuntiPrelievo(){return this.puntiPrelievo;}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAltId() {
		return alt_id;
	}
	public void setAltId(int alt_id) {
		this.alt_id = alt_id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeUtenteAssociato() {
		return nomeUtenteAssociato;
	}
	public void setNomeUtenteAssociato(String nomeUtenteAssociato) {
		this.nomeUtenteAssociato = nomeUtenteAssociato;
	}

	
	private int id;
	private int alt_id;
	private String nome;
	private HashMap<String,Integer> comuniAccettatiPerPP = new HashMap<String,Integer>(); /*[k : nome comune , v : idcomune ] su lookup comuni1 */
	private ArrayList<AnagraficaGestore> anagraficheDelGestore = new ArrayList<AnagraficaGestore>();  /*ad uno stesso gestore possono essere associate piu' di un'anagrafica (indirizzo, comune, telefono,asl etc) */
	public ArrayList<PuntoPrelievo> puntiPrelievo = new ArrayList<PuntoPrelievo>();
	public int maxIndexChunkSize;
	private String nomeUtenteAssociato;
	
	
	public GestoreAcque() { }
	
	public static PuntoPrelievo searchPuntoRaccoltaById(Connection conn,int idGestore,int idPuntoPrelievo,boolean searchCI ) throws Exception
	{
		return PuntoPrelievo.searchByIdGestore(conn, idGestore, idPuntoPrelievo,searchCI);
	}
	
	
	public GestoreAcque(String nomeGestore, Connection conn,int indiceChunkPuntiRaccolta,boolean searchCI) throws GestoreNotFoundException,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			pst = conn.prepareStatement("select * from gestori_acque_gestori gest where lower(gest.nome) = lower(?) ");
			pst.setString(1, nomeGestore);
			rs = pst.executeQuery();
			if(rs.next() == false)
				throw new GestoreNotFoundException();
			
			build(rs,conn,indiceChunkPuntiRaccolta,searchCI);
		}
		catch(GestoreNotFoundException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	}
	
	public static ArrayList<GestoreAcque> getAllGestoriConUtenteAssociato(Connection conn, int indiceChunkPuntiRaccolta,boolean searchCI) throws  Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<GestoreAcque> toRet = new ArrayList<GestoreAcque>();
		try
		{
			
			pst = conn.prepareStatement("select gest.nome as nome_gestore, gest.*,ug.*,acc.* from gestori_acque_gestori gest left join users_to_gestori_acque ug on gest.id = ug.id_gestore_acque_anag "
					+ "left join access acc on acc.user_id = ug.user_id ");
			rs = pst.executeQuery();
			while(rs.next())
			{
				String nome = rs.getString("nome_gestore");
				GestoreAcque toAdd = new GestoreAcque(nome, conn, indiceChunkPuntiRaccolta, searchCI);
				String nomeUtenteAssociato = rs.getString("username");
				toAdd.setNomeUtenteAssociato(nomeUtenteAssociato);
				toRet.add(toAdd);
			}
			
			 return toRet;
		}
		 
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	
		
	}
	
	public static ArrayList<GestoreAcque> getAllGestori(Connection conn, int indiceChunkPuntiRaccolta,boolean searchCI) throws  Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<GestoreAcque> toRet = new ArrayList<GestoreAcque>();
		
		try
		{
			pst = conn.prepareStatement("select gest.nome as nome_gestore, gest.* from gestori_acque_gestori gest where gest.enabled = true "
					+ " "
					+ " order by gest.nome ");
			rs = pst.executeQuery();
			while(rs.next())
			{
				String nome = rs.getString("nome_gestore");
				GestoreAcque toAdd = new GestoreAcque(nome, conn, indiceChunkPuntiRaccolta, searchCI);
				toRet.add(toAdd);
			}
			
			 return toRet;
		}
		 
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	
		
	}
	
	
	
	public GestoreAcque(int userId, Connection conn,int indiceChunkPuntiRaccolta,boolean searchCI) throws GestoreNotFoundException,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			pst = conn.prepareStatement("select * from gestori_acque_gestori gest join gestori_acque_anag anag on anag.id_gestore = gest.id join  users_to_gestori_acque users on users.id_gestore_acque_anag = gest.id where users.user_id = ? ");
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if(rs.next() == false)
				throw new GestoreNotFoundException();
			
			build(rs,conn,indiceChunkPuntiRaccolta,searchCI);
		}
		catch(GestoreNotFoundException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	}
	
	public GestoreAcque(int idGestore, Connection conn,int indiceChunkPuntiRaccolta) throws GestoreNotFoundException,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			pst = conn.prepareStatement("select * from gestori_acque_gestori gest join gestori_acque_anag anag on anag.id_gestore = gest.id where gest.id = ? ");
			pst.setInt(1, idGestore);
			rs = pst.executeQuery();
			if(rs.next() == false)
				throw new GestoreNotFoundException();
			
			build(rs,conn,indiceChunkPuntiRaccolta,false);
		}
		catch(GestoreNotFoundException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	}
	
	public GestoreAcque(int idGestore, Connection conn) throws GestoreNotFoundException,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			pst = conn.prepareStatement(" select * from gestori_acque_gestori gest where gest.id = ? ");
			pst.setInt(1, idGestore);
			rs = pst.executeQuery();
			if(rs.next())
				build(rs,conn);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}	
		}
	}
	
	
	
	 
	public void build(ResultSet rs , Connection db, int indiceChunkPuntiPrelievo,boolean searchCI ) /*searchCI dice se cercare anche i controlli */
	{
		try{setAltId(rs.getInt("alt_id"));}catch(Exception ex){ex.printStackTrace();}
		try{setId(rs.getInt("id"));}catch(Exception ex){ex.printStackTrace();}
		try{setNome(rs.getString("nome"));}catch(Exception ex){ex.printStackTrace();}
		 
		/*per le varie anagrafiche che puo' avere questo gestore */
		
		try
		{
			setAnagraficheDelGestore(AnagraficaGestore.getAnagraficheDiGestore(getId(), db));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		/*per i comuni accettati (i comuni per i quali si puo' mettere pp per il gestore )*/
		/*PreparedStatement pst0 = null;
		ResultSet rs0= null;
		try
		{
			getComuniAccettatiPerPP().clear();
			pst0 = db.prepareStatement("select * from comuni_accettati_per_gestoriacque where id_gestore = ?");
			pst0.setInt(1, getId());
			rs0 = pst0.executeQuery();
			while(rs0.next())
			{
				int idComuneAccettato = rs0.getInt("id_comune");
				String nomeComuneAccettato = ControlloInterno.getDescFromLookupCode(db, "comuni1", "id", "nome", idComuneAccettato, "");
				getComuniAccettatiPerPP().put(nomeComuneAccettato,rs0.getInt("id_comune"));
			}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst0.close();} catch(Exception ex){}
			try{rs0.close();} catch(Exception ex){}
		}
		*/
		try
		{
			puntiPrelievo = PuntoPrelievo.searchChunkPerGestore(db,getId(), indiceChunkPuntiPrelievo,searchCI);
			maxIndexChunkSize = -1 + (int)Math.ceil(PuntoPrelievo.searchAllPerGestore(db, getId(),searchCI).size() / (float)PuntoPrelievo.CHUNK_SIZE);
		}catch(Exception ex){ex.printStackTrace();}

		 
	}
	
	public void build(ResultSet rs , Connection db) /*searchCI dice se cercare anche i controlli */
	{
		try{setAltId(rs.getInt("alt_id"));}catch(Exception ex){ex.printStackTrace();}
		try{setId(rs.getInt("id"));}catch(Exception ex){ex.printStackTrace();}
		try{setNome(rs.getString("nome"));}catch(Exception ex){ex.printStackTrace();}
		 
		/*per le varie anagrafiche che puo' avere questo gestore */
		
		try
		{
			setAnagraficheDelGestore(AnagraficaGestore.getAnagraficheDiGestore(getId(), db));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	/*necessita che i punti prelievo figli siano valorizzati con desc asl, desc via, desc tipologia, e tutti i dati del punto di prelievo */
	/* e che siano valorizzati i dati del gestore */
	/*Ritorna msg esito */
	public String insert(Connection db) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer("");
		try
		{
			pst = db.prepareStatement("insert into gestori_acque_gestori(nome) values(?) returning id");
			pst.setString(1, getNome().toUpperCase());
			pst.execute();
			rs = pst.getResultSet();
			rs.next();
			setId(rs.getInt(1));

			/*per le varie anagrafiche associate a questo gestore*/
			for(AnagraficaGestore anag : anagraficheDelGestore)
			{
				anag.setIdGestore(getId());
				anag.insert(db);
			}
			
			/*per i comuni accettati 
			for(String descComune : comuniAccettatiPerPP.keySet())
			{
				PreparedStatement pst0 = null;
				try
				{
					int idComune = comuniAccettatiPerPP.get(descComune);
					pst0 = db.prepareStatement("insert into comuni_accettati_per_gestoriacque(id_gestore, id_comune) values (?,?) ");
					pst0.setInt(1, getId());
					pst0.setInt(2, idComune);
					pst0.executeUpdate();
				}
				catch(Exception ex)
				{
				 
					throw ex;
				}
				finally
				{
					try{pst0.close();} catch(Exception ex){}
				}
				
			 
			} */
			
			sb.append("<br> <font color='green'> OPERAZIONE INSERIMENTO PER GESTORE \""+getNome()+"\" COMPLETATA CON SUCCESSO</font>");
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			sb.append("<br> <font color='red'> OPERAZIONE PER GESTORE \""+getNome()+"\" FALLITA</font>");
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		return sb.toString();
	}
	
	
	
	
	/*aggiunge tutte le altre informazioni per il gestore, dato il nome */
	/*public String update(Connection db) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer("");
		try
		{
			pst = db.prepareStatement("update gestori_acque_anag set idasl = ?, idcomune = ? , provincia = ?, telefono = ?, domicilio_digitale = ?, indirizzo = ? where"
					+ " nome = ?");
			int u = 0; 
			pst.setInt(++u, getIdAsl());
			pst.setInt(++u, getIdComune());
			pst.setString(++u, getProvincia());
			pst.setString(++u, getTelefono());
			pst.setString(++u, getDomicilioDigitale());
			pst.setString(++u, getIndirizzo());
			pst.setString(++u, getNome()); 
			int updated = pst.executeUpdate();
			
			if(updated == 1)
			{
				sb.append("<br> <font color='green'> OPERAZIONE AGGIORNAMENTO PER GESTORE \""+getNome()+"\" COMPLETATA CON SUCCESSO</font>");
			}
			else
			{
				sb.append("<br> <font color='green'> OPERAZIONE AGGIORNAMENTO PER GESTORE \""+getNome()+"\" FALLITA, NESSUN GESTORE AGGIORNATO/font>");
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

			sb.append("<br> <font color='green'> OPERAZIONE AGGIORNAMENTO PER GESTORE \""+getNome()+"\" FALLITA, NESSUN GESTORE AGGIORNATO/font>");
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		return sb.toString();
	}*/
	
}
