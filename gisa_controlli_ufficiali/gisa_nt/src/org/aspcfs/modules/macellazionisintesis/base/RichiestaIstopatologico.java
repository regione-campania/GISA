/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionisintesis.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;



import org.aspcfs.modules.macellazionisintesis.base.Organi;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class RichiestaIstopatologico extends GenericBean {

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "richiesta_istopatologico";
	private static final String nome_tabella2 = "richiesta_istopatologico_classificazione_esito";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;

	@Column(columnName = "identificativo_richiesta", columnType = STRING, table = nome_tabella)
	private String identificativoRichiesta = "";

	private ArrayList<Organi> lista_organi_richiesta = new ArrayList<Organi>();

	@Column(columnName = "id_capo", columnType = INT, table = nome_tabella)
	private int idCapo = -1;

	@Column(columnName = "id_partita", columnType = INT, table = nome_tabella)
	private int idPartita = -1;

	@Column(columnName = "data_prelievo", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataPrelievo;

	@Column(columnName = "id_destinatario_richiesta", columnType = INT, table = nome_tabella)
	private int idDestinatarioRichiesta = -1;

	@Column(columnName = "ragione_sociale_allevamento_provenienza_capo", columnType = STRING, table = nome_tabella)
	private String ragioneSocialeAllevamentoProvenienzaCapo = "";

	@Column(columnName = "telefono_allevamento_provenienza", columnType = STRING, table = nome_tabella)
	private String telefonoAllevamentoProvenienza = "";

	@Column(columnName = "codice_allevamento_provenienza", columnType = STRING, table = nome_tabella)
	private String codiceAllevamentoProvenienza = "";

	@Column(columnName = "id_asl_provenienza_capo", columnType = INT, table = nome_tabella)
	private int idAslProvenienzaCapo = -1;

	@Column(columnName = "note_richiesta", columnType = STRING, table = nome_tabella)
	private String noteRichiesta = "";

	@Column(columnName = "nominativo_veterinario_prelevatore", columnType = STRING, table = nome_tabella)
	private String nominativoVeterinarioPrelevatore = "";

	@Column(columnName = "data_inserimento", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataInserimento;

	@Column(columnName = "data_modifica", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataModifica;

	@Column(columnName = "utente_inserimento", columnType = INT, table = nome_tabella)
	private int utenteInserimento = -1;

	@Column(columnName = "utente_modifica", columnType = INT, table = nome_tabella)
	private int utenteModifica = -1;

	@Column(columnName = "note_internal_use_only", columnType = STRING, table = nome_tabella)
	private String noteInternalUseOnly = "";
	
	@Column(columnName = "id_habitat_animale", columnType = INT, table = nome_tabella)
	private int idHabitatAnimale = -1;
	
	@Column(columnName = "id_alimentazione_animale", columnType = INT, table = nome_tabella)
	private int idAlimentazioneAnimale = -1;

	
	@Column(columnName = "peso_animale", columnType = DOUBLE, table = nome_tabella)
	private double pesoAnimale = -1;
	
	@Column(columnName = "data_esito", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataEsito;
	
	@Column(columnName = "id_esito", columnType = INT, table = nome_tabella)
	private int idEsito = -1;
	
	@Column(columnName = "descrizione_esito", columnType = STRING, table = nome_tabella)
	private String descrizioneEsito = "";
	
	
	@Column(columnName = "codice_sigla_esito", columnType = STRING, table = nome_tabella)
	private String codiceSiglaEsito = "";
	
	
	
//	@Column(columnName = "id_classificazione", columnType = INT, table = nome_tabella2)
//	private int idClassificazione = -1;
//	
//	@Column(columnName = "id_valore_classificazione", columnType = INT, table = nome_tabella2)
//	private int idValoreClassificazione = -1;
	
	@Column(columnName = "denominazione_asl", columnType = STRING, table = nome_tabella)
	private String denominazioneAsl = "";
	
	
	@Column(columnName = "flag_esito_tumorale", columnType = BOOLEAN, table = nome_tabella)
	private boolean flagEsitoTumorale = false;

	
	private Capo capo = new Capo();
	
	public RichiestaIstopatologico() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// public String getNominativo_veterinario_prelevatore() {
	// return nominativo_veterinario_prelevatore;
	// }
	//
	// public void setNominativo_veterinario_prelevatore(String
	// nominativo_veterinario_prelevatore) {
	// this.nominativo_veterinario_prelevatore =
	// nominativo_veterinario_prelevatore;
	// }



	public ArrayList<Organi> getLista_organi_richiesta() {
		return lista_organi_richiesta;
	}

	public void setLista_organi_richiesta(ArrayList<Organi> lista_organi_richiesta) {
		this.lista_organi_richiesta = lista_organi_richiesta;
	}

	public int getIdCapo() {
		return idCapo;
	}

	public void setIdCapo(int idCapo) {
		this.idCapo = idCapo;
	}

	public void setIdCapo(String idCapo) {
		this.idCapo = Integer.parseInt(idCapo);
	}

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}

	public void setIdPartita(String idPartita) {
		this.idPartita = Integer.parseInt(idPartita);
	}

	public Timestamp getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Timestamp dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}
	
	public void setDataPrelievo(String dataPrelievo) {
		this.dataPrelievo = DatabaseUtils
				.parseDateToTimestamp(dataPrelievo);
	}

	public int getIdDestinatarioRichiesta() {
		return idDestinatarioRichiesta;
	}

	public void setIdDestinatarioRichiesta(int idDestinatarioRichiesta) {
		this.idDestinatarioRichiesta = idDestinatarioRichiesta;
	}

	public void setIdDestinatarioRichiesta(String idDestinatarioRichiesta) {
		this.idDestinatarioRichiesta = Integer.parseInt(idDestinatarioRichiesta);
	}

	public String getRagioneSocialeAllevamentoProvenienzaCapo() {
		return ragioneSocialeAllevamentoProvenienzaCapo;
	}

	public void setRagioneSocialeAllevamentoProvenienzaCapo(String ragioneSocialeAllevamentoProvenienzaCapo) {
		this.ragioneSocialeAllevamentoProvenienzaCapo = ragioneSocialeAllevamentoProvenienzaCapo;
	}

	public String getTelefonoAllevamentoProvenienza() {
		return telefonoAllevamentoProvenienza;
	}

	public void setTelefonoAllevamentoProvenienza(String telefonoAllevamentoProvenienza) {
		this.telefonoAllevamentoProvenienza = telefonoAllevamentoProvenienza;
	}

	public String getCodiceAllevamentoProvenienza() {
		return codiceAllevamentoProvenienza;
	}

	public void setCodiceAllevamentoProvenienza(String codiceAllevamentoProvenienza) {
		this.codiceAllevamentoProvenienza = codiceAllevamentoProvenienza;
	}

	public int getIdAslProvenienzaCapo() {
		return idAslProvenienzaCapo;
	}

	public void setIdAslProvenienzaCapo(int idAslProvenienzaCapo) {
		this.idAslProvenienzaCapo = idAslProvenienzaCapo;
	}

	public void setIdAslProvenienzaCapo(String idAslProvenienzaCapo) {
		this.idAslProvenienzaCapo = Integer.parseInt(idAslProvenienzaCapo);
	}

	public String getNoteRichiesta() {
		return noteRichiesta;
	}

	public void setNoteRichiesta(String noteRichiesta) {
		this.noteRichiesta = noteRichiesta;
	}

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(int utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public void setUtenteInserimento(String utenteInserimento) {
		this.utenteInserimento = Integer.parseInt(utenteInserimento);
	}

	public int getUtenteModifica() {
		return utenteModifica;
	}

	public void setUtenteModifica(int utenteModifica) {
		this.utenteModifica = utenteModifica;
	}

	public void setUtenteModifica(String utenteModifica) {
		this.utenteModifica = Integer.parseInt(utenteModifica);
	}

	public String getNoteInternalUseOnly() {
		return noteInternalUseOnly;
	}

	public void setNoteInternalUseOnly(String noteInternalUseOnly) {
		this.noteInternalUseOnly = noteInternalUseOnly;
	}
	
	
	

	public String getIdentificativoRichiesta() {
		return identificativoRichiesta;
	}

	public void setIdentificativoRichiesta(String identificativoRichiesta) {
		this.identificativoRichiesta = identificativoRichiesta;
	}

	public int getIdHabitatAnimale() {
		return idHabitatAnimale;
	}

	public void setIdHabitatAnimale(int idHabitatAnimale) {
		this.idHabitatAnimale = idHabitatAnimale;
	}
	
	public void setIdHabitatAnimale(String idHabitatAnimale) {
		this.idHabitatAnimale =Integer.parseInt(idHabitatAnimale);
	}

	public int getIdAlimentazioneAnimale() {
		return idAlimentazioneAnimale;
	}

	public void setIdAlimentazioneAnimale(int idAlimentazioneAnimale) {
		this.idAlimentazioneAnimale = idAlimentazioneAnimale;
	}
	
	
	public void setIdAlimentazioneAnimale(String idAlimentazioneAnimale) {
		this.idAlimentazioneAnimale = Integer.parseInt(idAlimentazioneAnimale);
	}
	
	
	

	public double getPesoAnimale() {
		return pesoAnimale;
	}

	public void setPesoAnimale(double pesoAnimale) {
		this.pesoAnimale = pesoAnimale;
	}
	
	
	public void setPesoAnimale(String pesoAnimale) {
		this.pesoAnimale = Double.valueOf(pesoAnimale);
	}
	
	
	

	public Capo getCapo() {
		return capo;
	}

	public void setCapo(Capo capo) {
		this.capo = capo;
	}
	
	
	

	public int getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(int idEsito) {
		this.idEsito = idEsito;
	}
	
	public void setIdEsito(String idEsito) {
		this.idEsito = Integer.valueOf(idEsito);
	}
	
	
	

	public Timestamp getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(Timestamp dataEsito) {
		this.dataEsito = dataEsito;
	}
	
	
	public void setDataEsito(String dataEsito) {
		this.dataEsito = DatabaseUtils
				.parseDateToTimestamp(dataEsito);
	}
	
	

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}
	
	
	

	public String getCodiceSiglaEsito() {
		return codiceSiglaEsito;
	}

	public void setCodiceSiglaEsito(String codiceSiglaEsito) {
		this.codiceSiglaEsito = codiceSiglaEsito;
	}
	
	
	
	
	

//	public int getIdClassificazione() {
//		return idClassificazione;
//	}
//
//	public void setIdClassificazione(int idClassificazione) {
//		this.idClassificazione = idClassificazione;
//	}
//	
//	public void setIdClassificazione(String idClassificazione) {
//	if (idClassificazione != null)	
//		this.idClassificazione = Integer.valueOf(idClassificazione);
//	}
//
//
//	public int getIdValoreClassificazione() {
//		return idValoreClassificazione;
//	}
//
//	public void setIdValoreClassificazione(int idValoreClassificazione) {
//		this.idValoreClassificazione = idValoreClassificazione;
//	}
//
//	public void setIdValoreClassificazione(String idValoreClassificazione) {
//		if (idValoreClassificazione != null)
//			this.idValoreClassificazione = Integer.valueOf(idValoreClassificazione);
//	}
	
	
	

	public boolean isFlagEsitoTumorale() {
		return flagEsitoTumorale;
	}

	public void setFlagEsitoTumorale(boolean flagEsitoTumorale) {
		this.flagEsitoTumorale = flagEsitoTumorale;
	}

	public String getDenominazioneAsl() {
		return denominazioneAsl;
	}

	public void setDenominazioneAsl(String denominazioneAsl) {
		this.denominazioneAsl = denominazioneAsl;
	}

	public RichiestaIstopatologico store(Connection db,ActionContext context) throws Exception

	{
		try {

			this.dataInserimento = new Timestamp(System.currentTimeMillis());
			this.dataModifica = this.dataInserimento;
			
			
			
		      id =  DatabaseUtils.getNextSeq(db,context, "richiesta_istopatologico","id");

			String identificativo_isto = "ISTO_G_"+id;
			
			this.setIdentificativoRichiesta(identificativo_isto);

			String sql = "INSERT INTO richiesta_istopatologico( ";

			// Field[] f = this.getClass().getDeclaredFields();
			// String[] campi = new
			// String[RichiestaIstopatologico.class.getFields().length];
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : RichiestaIstopatologico.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()) )
					campi.put(column.columnName(), column.columnType());
				}
			}

			Method[] m = this.getClass().getMethods();
			Vector<Method> v = new Vector<Method>();
			Vector<Integer> v2 = new Vector<Integer>();
			boolean firstField = true;

			Iterator it = campi.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();

				for (int j = 0; j < m.length; j++) {
					String met = m[j].getName();
					if (
//					!field.equalsIgnoreCase("id")
//							&&
					(met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
									+ field.replaceAll("_", "")))) {
						v.add(m[j]);
						v2.add((Integer) pairs.getValue());
						sql += (firstField) ? ("") : (",");
						firstField = false;
						sql += " " + field;
					}
				}

			}

			sql += " ) VALUES (";
			firstField = true;

			for (int i = 0; i < v.size(); i++) {
				{
					sql += (firstField) ? ("") : (",");
					sql += " ?";
					firstField = false;
				}
			}

			sql += " )";

			PreparedStatement stat = db.prepareStatement(sql);

			for (int i = 1; i <= v.size(); i++) {
				Object o = v.elementAt(i - 1).invoke(this);

				if (o == null) {
					stat.setNull(i, v2.elementAt(i - 1));
				} else {
					switch (parseType(o.getClass())) {
					case INT:
						stat.setInt(i, (Integer) o);
						break;
					case STRING:
						String s = (String) o;
						s = s.replaceAll("u13", " ");
						s = s.replaceAll("u10", " ");
						s = s.replaceAll("\\r", " ");
						s = s.replaceAll("\\n", " ");
						
						stat.setString(i, s);
						break;
					case BOOLEAN:
						stat.setBoolean(i, (Boolean) o);
						break;
					case TIMESTAMP:
						stat.setTimestamp(i, (Timestamp) o);
						break;
					case DATE:
						stat.setDate(i, (Date) o);
						break;
					case FLOAT:
						stat.setFloat(i, (Float) o);
						break;
					case DOUBLE:
						stat.setDouble(i, (Double) o);
						break;
					}
				}
			}
			
			
			stat.execute();
			stat.close();
			
			
			
			
			//Inserimento organi
			
			Iterator i = lista_organi_richiesta.iterator();
			int h = 1;
			
			while (i.hasNext()){
				int s = 0;
				Organi thisOrgano  = (Organi) i.next();
				String insert_organo = "Update m_lcso_organi set "
						+"istopatologico_topografia = ?, istopatologico_interessamento_altri_organi = ?, istopatologico_id_richiesta = ?, identificativo_campione_organo = ? "
           	+" where id = ?";
				PreparedStatement pst = db.prepareStatement(insert_organo);
				
				pst.setString(++s, thisOrgano.getIstopatologico_topografia());
				pst.setString(++s, thisOrgano.getIstopatologico_interessamento_altri_organi());
				pst.setInt(++s, this.getId());
				pst.setString(++s, identificativo_isto+"__"+h);
				pst.setInt(++s, thisOrgano.getId());				
				pst.execute();
				
				h++;
			}
			
			return this;
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}

	}

	public String getNominativoVeterinarioPrelevatore() {
		return nominativoVeterinarioPrelevatore;
	}

	public void setNominativoVeterinarioPrelevatore(String nominativoVeterinarioPrelevatore) {
		this.nominativoVeterinarioPrelevatore = nominativoVeterinarioPrelevatore;
	}

	protected static int parseType(Class<?> type) {
		int ret = -1;

		String name = type.getSimpleName();

		if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("integer")) {
			ret = INT;
		} else if (name.equalsIgnoreCase("string")) {
			ret = STRING;
		} else if (name.equalsIgnoreCase("double")) {
			ret = DOUBLE;
		} else if (name.equalsIgnoreCase("float")) {
			ret = FLOAT;
		} else if (name.equalsIgnoreCase("timestamp")) {
			ret = TIMESTAMP;
		} else if (name.equalsIgnoreCase("date")) {
			ret = DATE;
		} else if (name.equalsIgnoreCase("boolean")) {
			ret = BOOLEAN;
		}

		return ret;
	}
	
	
	public static RichiestaIstopatologico load(int id, Connection db )
	{

		RichiestaIstopatologico				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		boolean closeConnection = false;
		
		if( id > 0 )
		{
			try
			{
			
				if (db == null){
					db = GestoreConnessioni.getConnection();
					closeConnection = true;
				}
				stat	= db.prepareStatement( "SELECT * FROM richiesta_istopatologico r left join richiesta_istopatologico_classificazione_esito c on "
						+ "(r.id = c.id_richiesta_istopatologico)  WHERE r.id = ? and r.trashed_date IS NULL" );
				stat.setInt( 1, id );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
				}
				
				//Recupero dettagli Capo
				if (ret != null && ret.idCapo > 0){
					ret.setCapo(Capo.load(String.valueOf(ret.getIdCapo()), db));
				}
				
				//Recupero organi interessati
				if (ret != null)
					ret.setLista_organi_richiesta(Organi.loadOrganiByIdistopatologico(ret.getId(), db));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}
					
					if( stat != null )
					{
						stat.close();
						stat = null;
					}
					
					if (closeConnection){
						GestoreConnessioni.freeConnection(db);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		

	
	
	return ret;
	
	}
	
	
	private static RichiestaIstopatologico loadResultSet( ResultSet res, Connection db ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			RichiestaIstopatologico ret = new RichiestaIstopatologico();
			
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : RichiestaIstopatologico.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					//if (column.table() == null || (nome_tabella).equals(column.table()) )
						campi.put(column.columnName(), column.columnType());
				}
			}
		
			Method[]	m = ret.getClass().getMethods();
			
			Iterator it = campi.entrySet().iterator();
			while (it.hasNext()) {
				
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();
				Method getter	= null;
		    	 Method setter	= null;
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
								+ field.replaceAll("_", ""))) 
		             {
		                  getter = m[j];
		             }
		             else if(met.equalsIgnoreCase("SET" + field.replaceAll("_", "")) )
		             {
		            	 if (!(( (Integer) pairs.getValue() )  == INT && m[j].getParameterTypes() [0] == String.class) && 
		            			 !(( (Integer) pairs.getValue() )  == TIMESTAMP && m[j].getParameterTypes() [0] == String.class) &&
		            			 !(( (Integer) pairs.getValue() )  == DOUBLE && m[j].getParameterTypes() [0] == String.class))
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (field != null) )
		         {
		        	 Object o = null;
		             
		             switch ( ( (Integer) pairs.getValue() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
			
		
		
			return ret;
		}
	
	
	public RichiestaIstopatologico update(Connection db) throws Exception{
		
		try {

			this.dataModifica = this.dataInserimento;
			

			String sql = "UPDATE richiesta_istopatologico set ";
		//	String sql2 = "INSERT INTO " +nome_tabella2 + " (id_richiesta_istopatologico  ";

			// Field[] f = this.getClass().getDeclaredFields();
			// String[] campi = new
			// String[RichiestaIstopatologico.class.getFields().length];
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
		//	HashMap<String, Integer> campiClassificazione = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : RichiestaIstopatologico.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()) )
						campi.put(column.columnName(), column.columnType());
//					else if ((nome_tabella2).equals(column.table()))
//						campiClassificazione.put(column.columnName(), column.columnType());
				}
			}

			Method[] m = this.getClass().getMethods();
			Vector<Method> v = new Vector<Method>();
			Vector<Integer> v2 = new Vector<Integer>();
			boolean firstField = true;

			Iterator it = campi.entrySet().iterator();
			int len = campi.size();

			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();

				for (int j = 0; j < m.length; j++) {
					String met = m[j].getName();
					if (
//					!field.equalsIgnoreCase("id")
//							&&
					(met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
									+ field.replaceAll("_", "")))) {
						v.add(m[j]);
						v2.add((Integer) pairs.getValue());
						sql += (firstField) ? ("") : (",");
						firstField = false;
						sql += " " + field + "= ?";
					}
				}

			}

		//	sql += " ) ";
			//firstField = true;

//			for (int i = 0; i < v.size(); i++) {
//				{
//					sql += (firstField) ? ("") : (",");
//					sql += " ?";
//					firstField = false;
//				}
//			}
//
			sql += " where id =  " + this.getId();

			PreparedStatement stat = db.prepareStatement(sql);

			for (int i = 1; i <= v.size(); i++) {
				Object o = v.elementAt(i - 1).invoke(this);

				if (o == null) {
					stat.setNull(i, v2.elementAt(i - 1));
				} else {
					switch (parseType(o.getClass())) {
					case INT:
						stat.setInt(i, (Integer) o);
						break;
					case STRING:
						String s = (String) o;
						s = s.replaceAll("u13", " ");
						s = s.replaceAll("u10", " ");
						s = s.replaceAll("\\r", " ");
						s = s.replaceAll("\\n", " ");
					
						stat.setString(i, s);
						break;
					case BOOLEAN:
						stat.setBoolean(i, (Boolean) o);
						break;
					case TIMESTAMP:
						stat.setTimestamp(i, (Timestamp) o);
						break;
					case DATE:
						stat.setDate(i, (Date) o);
						break;
					case FLOAT:
						stat.setFloat(i, (Float) o);
						break;
					case DOUBLE:
						stat.setDouble(i, (Double) o);
						break;
					}
				}
			}
			
			
			stat.execute();
			stat.close();
	
			
			
			
			//Inserimento organi
					
					Iterator i = lista_organi_richiesta.iterator();
					
					while (i.hasNext()){

						int s = 0;
						Organi thisOrgano  = (Organi) i.next();
						thisOrgano.update(db);
						String insert_organo_classificazione = "INSERT INTO " +nome_tabella2 + " (id_richiesta_istopatologico, id_organo , id_esito_istopatologico, id_classificazione,  id_valore_classificazione ) values (?, ?, ?, ?, ?) ";
						PreparedStatement pst = db.prepareStatement(insert_organo_classificazione);
						
						pst.setInt(++s, this.getId());
						pst.setInt(++s, thisOrgano.getId());
						pst.setInt(++s, thisOrgano.getId_esito_istopatologico());
						pst.setInt(++s, thisOrgano.getId_classificazione_istopatologico());
						pst.setInt(++s, thisOrgano.getId_valore_classificazione_istopatologico());			
						pst.execute();
					
					}
			
			
			
			
			
			return this;
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}
		
	}
	
	
	public void caricaEsitoIstopatologico(ActionContext context){
		
	//	this.setIdEsito(context.getRequest().getParameter("idEsito"));
		this.setDataEsito(context.getRequest().getParameter("dataEsito"));
		this.setDescrizioneEsito(context.getRequest().getParameter("descrizioneEsito"));
		this.setCodiceSiglaEsito(context.getRequest().getParameter("codiceSiglaInterno"));
		this.setIdEsito(1); //SERVE A DIRE CHE E' STATO INSERITO UN ESITO
//		this.setIdClassificazione(context.getRequest().getParameter("idClassificazione"));
//		this.setIdValoreClassificazione(context.getRequest().getParameter("idValoreClassificazione"));
		
		Iterator i = lista_organi_richiesta.iterator();
		while (i.hasNext()){
			Organi thisOrgano = (Organi) i.next();
			thisOrgano.setId_esito_istopatologico(context.getRequest().getParameter("idEsito_"+thisOrgano.getId()));
			thisOrgano.setId_classificazione_istopatologico(context.getRequest().getParameter("idClassificazione_"+thisOrgano.getId()));
			thisOrgano.setId_valore_classificazione_istopatologico(context.getRequest().getParameter("idValoreClassificazione_"+thisOrgano.getId()));
			
			if (thisOrgano.getId_esito_istopatologico() == 1) //almeno uno tumorale, setto tumorale l'esito dell'istopatologico per importarlo correttamente nel registro tumori
				this.setFlagEsitoTumorale(true);
		}
	}

}
