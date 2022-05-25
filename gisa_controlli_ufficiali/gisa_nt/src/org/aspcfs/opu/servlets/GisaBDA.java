/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.opu.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.buffer.base.Comune;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.suap.actions.InterfValidazioneRichieste;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apicolture.actions.CfUtil;

/**
 * Servlet implementation class GisaBDA
 */
public class GisaBDA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NOMATCH_COMUNE_IMPIANTO = "Impresa presente in GISA, ma il comune selezionato per lo stabilimento non corrisponde all'impresa o non sono definiti il tipo di impresa o di attivita' fissa/mobile.";
	private static final String NOMATCH_NUMERO_REGISTRAZIONE = "Numero registrazione non trovato, non coerente con la linea scelta o non abilitato ad inserimento dati estesi.";
	private static final String NOMATCH_STATO_ATTIVO = "Impresa presente IN GISA, ma non e' nello stato attivo";
	private static final String NO_MATCH_LINEA_ATTIVITA = "All'impresa non risultano associate linee produttive conformi con il tipo di registrazione selezionato.";
	private static final String RICHIESTA_GIA_PRESENTE = "Richiesta di iscrizione gia Presente";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	Logger logger = Logger.getLogger(GisaBDA.class);
	
	
	public GisaBDA() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection db = null ;
		ConnectionPool cp = null ;
		try
		{

			
			cp = (ConnectionPool)request.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection();	
			int i = 0 ;

			
			String insert = "INSERT INTO log_user_reg( nome, cognome, codice_fiscale, "
					+ "sesso, pec, comune_residenza, provincia_residenza, cap_residenza, indirizzo_residenza, telefono,ip,user_agent,data_richiesta)VALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,current_timestamp);" ;


			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String codice_fiscale = request.getParameter("cf");
			String sesso = request.getParameter("sesso");
			String pec = request.getParameter("pec");
			String comune_residenza = request.getParameter("citta");
			String provincia_residenza = request.getParameter("provincia");
			String cap_residenza = request.getParameter("cap");
			String indirizzo_residenza = request.getParameter("indirizzo");
			String telefono = request.getParameter("telefono");
			String ip = request.getRemoteAddr();
			String tipoIscrizione = request.getParameter("tipoIscrizione");
			String userAgent = request.getHeader("User-Agent");
			int idComuneRes = -1 ;
			String siglaProvRes = "" ;





			if (	nome.trim().equalsIgnoreCase("") || 
					cognome.trim().equalsIgnoreCase("") || 
					codice_fiscale.trim().equalsIgnoreCase("") || 
					sesso.trim().equalsIgnoreCase("") || 
					pec.trim().equalsIgnoreCase("") || 
					telefono.trim().equalsIgnoreCase("") || 
					comune_residenza.trim().equalsIgnoreCase("") || 
					provincia_residenza.trim().equalsIgnoreCase("") || 
					cap_residenza.trim().equalsIgnoreCase("") || 
					indirizzo_residenza.trim().equalsIgnoreCase("") 

					)
			{
				request.setAttribute("Esito", "1");
				request.setAttribute("Msg", "Tutti i Campi sono Obbligatori");
			}
			else
			{
				String controlloCF=CfUtil.extractCodiceFiscale(codice_fiscale);
				if(!controlloCF.equalsIgnoreCase(""))
				{
					request.setAttribute("Esito", "1");
					request.setAttribute("Msg", controlloCF);
				}
				else
				{

					PreparedStatement pstC = db.prepareStatement("select comuni1.id,p.cod_provincia from comuni1 join lookup_province p on p.code=comuni1.cod_provincia::int where nome ilike ?");
					pstC.setString(1, comune_residenza);
					ResultSet rsC =  pstC.executeQuery();
					if(rsC.next())
					{
						idComuneRes = rsC.getInt("id");
						siglaProvRes = rsC.getString("cod_provincia");


						String verificaIscrizione = "select * from log_user_reg where UPPER(codice_fiscale)=?";
						PreparedStatement pst2 = db.prepareStatement(verificaIscrizione);
						pst2.setString(1, (codice_fiscale!=null ? codice_fiscale.toUpperCase() : codice_fiscale));
						ResultSet rs = pst2.executeQuery();
						if (!rs.next())
						{



							PreparedStatement pst = db.prepareStatement(insert);
							pst.setString(++i,nome);
							pst.setString(++i,cognome);
							pst.setString(++i,codice_fiscale);
							pst.setString(++i,sesso);
							pst.setString(++i,pec);
							pst.setString(++i,comune_residenza);
							pst.setString(++i,provincia_residenza);
							pst.setString(++i,cap_residenza);
							pst.setString(++i,indirizzo_residenza);
							pst.setString(++i,telefono);
							pst.setString(++i,ip);
							pst.setString(++i,userAgent);

							pst.execute();


							SoggettoFisico soggetto = new SoggettoFisico();
							soggetto.setNome(nome);
							soggetto.setCognome(cognome);
							soggetto.setCodFiscale(codice_fiscale);
							soggetto.setSesso(sesso);
							soggetto.setEmail(pec);
							soggetto.setEnteredBy(291);
							soggetto.setModifiedBy(291);
							soggetto.setTelefono1(telefono);
							soggetto.setTelefono2(telefono);

							Indirizzo ind = new Indirizzo();
							ind.setProvincia(provincia_residenza);
							ind.setCap(cap_residenza);
							ind.setVia(indirizzo_residenza);
							ind.setProvincia(siglaProvRes);
							ind.setComune(idComuneRes);

							soggetto.setIndirizzo(ind);
							soggetto.insert(db, request.getServletContext());


							String messageHD = "GISABDA \n , La persona "+nome+" "+cognome +" ha effettuato una richiesta di iscrizione alla banca dati apistica regionale.\nDi seguito i suoi dati :";
							
							
							messageHD+="\n Tipo Iscrizione Richiesto :"+tipoIscrizione;
							messageHD+="\n Nome :"+nome;
							messageHD+="\n Cognome :"+cognome;
							messageHD+="\n Cf / P.Iva :"+codice_fiscale;
							messageHD+="\n Pec :"+pec;
							messageHD+="\n Comune Residenza :"+comune_residenza;
							messageHD+="\n Provincia Residenza :"+provincia_residenza;
							messageHD+="\n Cap Residenza :"+cap_residenza;
							messageHD+="\n Indirizzo Residenza :"+indirizzo_residenza;
							messageHD+="\n Telefono :"+telefono;
							
							
							
							messageHD+="\n\n";


							String messageUser = "Gentile Utente, la sua richiesta di iscrizione e' andata a buon fine.\n Il Nostro servizio di Help Desk prendera' in carico la sua richiesta.";

							
						
							
							sendMailPec(request,messageHD,"####BDAPI RICHIESTA ISCRIZIONE###",getPref( request,"EMAILADDRESS"));
							sendMailPec(request,messageUser,"####BDAPI RICHIESTA ISCRIZIONE###",pec);

							
							request.setAttribute("Esito", "0");
							request.setAttribute("Msg", "iscrizione Avvenuta con Successo");
						}
						else
						{
							request.setAttribute("Esito", "1");
							request.setAttribute("Msg", "Richiesta di iscrizione gia Presente");

						}
					}
					else
					{
						
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", "Comune non corrispondente in banca dati");
						
					}
				}
			}


		}
		catch(SQLException e)	
		{
			System.out.println("Errore nella chiamata della servlet "+e.getMessage());
			request.setAttribute("Esito", "1");
			request.setAttribute("Msg", "Errore Generico del Server contattare l'hel desk "+e.getMessage());
					}

		finally
		{
			try {
				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("/gisa_nt/gisaBDA.jsp?esito="+request.getAttribute("Esito")+"&msg="+request.getAttribute("Msg"));
		

	}

	
	
	
	public void registrazioneApi(HttpServletRequest request, HttpServletResponse response, Connection db) throws SQLException
	{
		int i = 0 ;

		String insert = "INSERT INTO log_user_reg( nome, cognome, codice_fiscale, "
				+ "sesso, pec, comune_residenza, provincia_residenza, cap_residenza, indirizzo_residenza, telefono,ip,user_agent,data_richiesta)VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,current_timestamp);" ;

		String tipoIscrizione = request.getParameter("tipoIscrizione");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codice_fiscale = request.getParameter("cf");
		String sesso = request.getParameter("sesso");
		String pec = request.getParameter("pec");
		String comune_residenza = request.getParameter("citta");
		String provincia_residenza = request.getParameter("provincia");
		String cap_residenza = request.getParameter("cap");
		String indirizzo_residenza = request.getParameter("indirizzo");
		String telefono = request.getParameter("telefono");
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");
		int idComuneRes = -1 ;
		String siglaProvRes = "" ;





		if (	nome.trim().equalsIgnoreCase("") || 
				cognome.trim().equalsIgnoreCase("") || 
				codice_fiscale.trim().equalsIgnoreCase("") || 
				sesso.trim().equalsIgnoreCase("") || 
				pec.trim().equalsIgnoreCase("") || 
				telefono.trim().equalsIgnoreCase("") || 
				comune_residenza.trim().equalsIgnoreCase("") || 
				provincia_residenza.trim().equalsIgnoreCase("") || 
				cap_residenza.trim().equalsIgnoreCase("") || 
				indirizzo_residenza.trim().equalsIgnoreCase("") 

				)
		{
			request.setAttribute("Esito", "1");
			request.setAttribute("Msg", "Tutti i Campi sono Obbligatori");
		}
		else
		{
			String controlloCF=CfUtil.extractCodiceFiscale(codice_fiscale);
			if(!controlloCF.equalsIgnoreCase(""))
			{
				request.setAttribute("Esito", "1");
				request.setAttribute("Msg", controlloCF);
			}
			else
			{

				PreparedStatement pstC = db.prepareStatement("select comuni1.id,p.cod_provincia from comuni1 join lookup_province p on p.code=comuni1.cod_provincia::int where istat = ?");
				pstC.setString(1, comune_residenza);
				ResultSet rsC =  pstC.executeQuery();
				if(rsC.next())
				{
					idComuneRes = rsC.getInt("id");
					siglaProvRes = rsC.getString("cod_provincia");


					String verificaIscrizione = "select * from log_user_reg where UPPER(codice_fiscale)=?";
					PreparedStatement pst2 = db.prepareStatement(verificaIscrizione);
					pst2.setString(1, (codice_fiscale!=null ? codice_fiscale.toUpperCase() : codice_fiscale));
					ResultSet rs = pst2.executeQuery();
					if (!rs.next())
					{



						PreparedStatement pst = db.prepareStatement(insert);
						pst.setString(++i,nome);
						pst.setString(++i,cognome);
						pst.setString(++i,codice_fiscale);
						pst.setString(++i,sesso);
						pst.setString(++i,pec);
						pst.setString(++i,comune_residenza);
						pst.setString(++i,provincia_residenza);
						pst.setString(++i,cap_residenza);
						pst.setString(++i,indirizzo_residenza);
						pst.setString(++i,telefono);
						pst.setString(++i,ip);
						pst.setString(++i,userAgent);

						pst.execute();


						SoggettoFisico soggetto = new SoggettoFisico();
						soggetto.setNome(nome);
						soggetto.setCognome(cognome);
						soggetto.setCodFiscale(codice_fiscale);
						soggetto.setSesso(sesso);
						soggetto.setEmail(pec);
						soggetto.setEnteredBy(291);
						soggetto.setModifiedBy(291);
						soggetto.setTelefono1(telefono);
						soggetto.setTelefono2(telefono);

						Indirizzo ind = new Indirizzo();
						ind.setProvincia(provincia_residenza);
						ind.setCap(cap_residenza);
						ind.setVia(indirizzo_residenza);
						ind.setProvincia(siglaProvRes);
						ind.setComune(idComuneRes);

						soggetto.setIndirizzo(ind);
						soggetto.insert(db, request.getServletContext());


						String messageHD = "GISABDA \n , La persona "+nome+" "+cognome +" ha effettuato una richiesta di iscrizione alla banca dati apistica regionale.\nDi seguito i suoi dati :";
						
						messageHD+="\n Tipo Iscrizione Richiesto :"+tipoIscrizione;
						messageHD+="\n Nome :"+nome;
						messageHD+="\n Cognome :"+cognome;
						messageHD+="\n Cf :"+codice_fiscale;
						messageHD+="\n Pec :"+pec;
						messageHD+="\n Comune Residenza :"+comune_residenza;
						messageHD+="\n Provincia Residenza :"+provincia_residenza;
						messageHD+="\n Cap Residenza :"+cap_residenza;
						messageHD+="\n Indirizzo Residenza :"+indirizzo_residenza;
						messageHD+="\n Telefono :"+telefono;
						messageHD+="\n\n";


						String messageUser = "Gentile Utente, la sua richiesta di iscrizione e' andata a buon fine.\n Il Nostro servizio di Help Desk prendera' in carico la sua richiesta.";

						sendMailPec(request,messageHD,"####BDAPI RICHIESTA ISCRIZIONE###","gisahelpdesk@usmail.it" );
						sendMailPec(request,messageUser,"####BDAPI RICHIESTA ISCRIZIONE###",pec);

						
						request.setAttribute("Esito", "0");
						request.setAttribute("Msg", "iscrizione Avvenuta con Successo");
					}
					else
					{
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", "Richiesta di iscrizione gia Presente");

					}
				}
				else
				{
					
					request.setAttribute("Esito", "1");
					request.setAttribute("Msg", "Comune non corrispondente in banca dati");
					
				}
			}
		}
	}
	
	
	
	
	private void registrazioneTarghe(HttpServletRequest request, HttpServletResponse response, Connection db) throws SQLException {
		String insert = "INSERT INTO log_user_reg(id_tipo_iscrizione,numero_registrazione ,nome, cognome, codice_fiscale, "
				+ "sesso, pec, comune_residenza, provincia_residenza, cap_residenza, indirizzo_residenza, telefono,ip,user_agent,data_richiesta)VALUES ( ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,current_timestamp);" ;

		
		System.out.println(">>>>>>>>>>CHIAMATO METODO REGISTRAZIONE TRASPORTI/DISTRIBUTORI/PRODOTTI");
		//int comuneStabilimento = Integer.parseInt(request.getParameter("comune_stabilimento"));
		String istatComuneStabilimento = request.getParameter("comune_stabilimento"); //questo e' l'istat in realta' (a me serve fare alcuni controlli usando l'id comune associato)
		int tipoIscrizione = Integer.parseInt(request.getParameter("tipoIscrizione"));
		System.out.println(">>>>>>>>>>SI TRATTA DI ID TIPO REGISTRAZIONE: "+tipoIscrizione);
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codice_fiscale = request.getParameter("cf");
		String sesso = request.getParameter("sesso");
		String pec = request.getParameter("pec");
//		String comune_residenza = request.getParameter("citta");
		String provincia_residenza = request.getParameter("provincia");
		String cap_residenza = request.getParameter("cap");
		String indirizzo_residenza = request.getParameter("indirizzo");
		String telefono = request.getParameter("telefono");
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");
		int idComuneRes = -1 ;
		String siglaProvRes = "" ;
		String codiceRegistrazione = request.getParameter("codice_registrazione");
		
		System.out.println("parametri acquisiti");
		
		int i = 0 ;



		
		if (	nome.trim().equalsIgnoreCase("") || 
				cognome.trim().equalsIgnoreCase("") || 
				codice_fiscale.trim().equalsIgnoreCase("") || 
				sesso.trim().equalsIgnoreCase("") || 
				pec.trim().equalsIgnoreCase("") || 
				telefono.trim().equalsIgnoreCase("") || 
//				comune_residenza.trim().equalsIgnoreCase("") || 
				provincia_residenza.trim().equalsIgnoreCase("") || 
				cap_residenza.trim().equalsIgnoreCase("") || 
				indirizzo_residenza.trim().equalsIgnoreCase("") || 
				codiceRegistrazione.trim().equalsIgnoreCase("")

				)
		{
			request.setAttribute("Esito", "1");
			request.setAttribute("Msg", "Tutti i Campi sono Obbligatori");
			System.out.println(">>>>>>>>>>Errore: Almeno un campo non fornito");
			return;
		}
		else
		{
			String controlloCF=CfUtil.extractCodiceFiscale(codice_fiscale);
			if(!controlloCF.equalsIgnoreCase(""))
			{
				request.setAttribute("Esito", "1");
				request.setAttribute("Msg", controlloCF);
				System.out.println(">>>>>>>>>>Errore: CF NON VALIDO");
				return;
			}
			else
			{

				System.out.println("Inizio validazione vincoli");
				
//				PreparedStatement pstC = db.prepareStatement("select comuni1.id,p.cod_provincia from comuni1 join lookup_province p on p.code=comuni1.cod_provincia::int where istat = ?");
//				pstC.setString(1, comune_residenza);
//				System.out.println("query: -->"+pstC);
//				ResultSet rsC =  pstC.executeQuery();
//				if(!rsC.next())
//				{
//					request.setAttribute("Esito", "1");
//					request.setAttribute("Msg", "Comune (residenza) non esistente in banca dati");
//					System.out.println(">>>>>>>>>>Errore: Comune (residenza) non esistente in banca dati");
//					return;
//				}
//				else
//				{
//					System.out.println("Comune (RESIDENZA) esistente in banca dati,  proseguo...");
//					idComuneRes = rsC.getInt("id");
//					siglaProvRes = rsC.getString("cod_provincia");

					//traduco l'istat anche per il comune stabilimento
					PreparedStatement pst0 = db.prepareStatement("select id, nome from comuni1 where istat = ?");
					
					pst0.setString(1,istatComuneStabilimento);
					System.out.println(pst0 +" ");
					ResultSet rs0 = pst0.executeQuery();
					if(!rs0.next())
					{
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", "Comune (stabilimento) non esistente in banca dati");
						System.out.println(">>>>>>>>>>Errore: Comune (stabilimento) non esistente in banca dati");
						return;
					}
					
					String nomeComuneStabilimento = rs0.getString("nome");
					Integer idComuneStabilimento =  rs0.getInt("id"); //lo utilizzeremo in seguito di nuovo
					System.out.println("Trovata corrispondenza anche per comune stabilimento, proseguo...");
					
					//se si tratta di gestori trasporti possono avere al massimo un tipo di linea per impianto, quindi non puo' esistere duplicato sul numero di registrazione
					//mentre se si tratta di un gestore degli altri 2 tipi (prodotti/distributori) allora si puo' richiedere una linea aggiuntiv
					 
					String verificaIscrizione0 = "select * from log_user_reg where numero_registrazione = ?";  
					PreparedStatement pst2 = db.prepareStatement(verificaIscrizione0);
					pst2.setString(1, codiceRegistrazione );
					ResultSet rs = pst2.executeQuery();
					if(rs.next() && (rs.getInt("id_tipo_iscrizione") ==  MappingTipiGestoriLineeMobili.GESTORE_TRASPORTI || tipoIscrizione ==  MappingTipiGestoriLineeMobili.GESTORE_TRASPORTI || rs.getInt("id_tipo_iscrizione") == tipoIscrizione ))
					{	//se gia' esiste quello stabilimento, e ha associato un tipo linea trasporti, oppure la richiesta che arriva e' tipo trasporti oppure quella che gia' esiste e' linea di tipo uguale a quello richiesto
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", RICHIESTA_GIA_PRESENTE );
						System.out.println(">>>>>>>>>>Errore: Richiesta iscrizione gia' presente");
						return;
					}
					else
					{

						System.out.println("Richiesta NON gia' presente, proseguo...");
						pst0 = db.prepareStatement("select * from opu_operatori_denormalizzati_view"+
								" where codice_registrazione = ? and id_lookup_tipo_linee_mobili = ? ");
						pst0.setString(1, codiceRegistrazione );
						pst0.setInt(2, tipoIscrizione);
						System.out.println(">>>>>>>>>>Verifico coerenza: "+pst0.toString());

						rs0 = pst0.executeQuery();
						if(!rs0.next())
						{
							request.setAttribute("Esito", "1");
							request.setAttribute("Msg", this.NOMATCH_NUMERO_REGISTRAZIONE  );
							System.out.println(">>>>>>>>>>Errore: Numero registrazione non trovato");
							return;
						}
						System.out.println("Num registrazione trovato in banca dati,  proseguo...");
						rs0.close();
						pst0.close();
						
						pst0 = db.prepareStatement("select * from opu_operatori_denormalizzati_view where codice_registrazione = ? and id_stato = 0",ResultSet.TYPE_SCROLL_SENSITIVE, 
		                        ResultSet.CONCUR_UPDATABLE);
						pst0.setString(1, codiceRegistrazione);
						rs0 = pst0.executeQuery();
						if(!rs0.next())
						{
							request.setAttribute("Esito", "1");
							request.setAttribute("Msg", this.NOMATCH_STATO_ATTIVO);
							System.out.println(">>>>>>>>>>Errore: Non esistono entry in stato attivo");
							return;
						}
						else
						{
							System.out.println("stato attivo,  proseguo...");
							rs0.beforeFirst();
							while(rs0.next() && rs0.getInt("id_comune_richiesta") != (idComuneStabilimento))
							{
								//niente
							}
							if(rs0.isAfterLast()) //non ha fatto match neanche una volta
							{
								request.setAttribute("Esito", "1");
								request.setAttribute("Msg", NOMATCH_COMUNE_IMPIANTO);
								System.out.println(">>>>>>>>>>Errore: Non e' stato trovato match col comune");
								return;
							}
							System.out.println("Comune matchato per la richiesa,  proseguo...");
							//altrimenti esiste il comune ...quindi controllo che il tipo linee...
							rs0.beforeFirst();
							while(rs0.next() && (rs0.getInt("id_comune_richiesta") != (idComuneStabilimento) || rs0.getInt("id_lookup_tipo_linee_mobili") != tipoIscrizione ) )
							{
								//niente
							}
							if(rs0.isAfterLast())
							{
								request.setAttribute("Esito", "1");
								request.setAttribute("Msg", this.NO_MATCH_LINEA_ATTIVITA);
								System.out.println(">>>>>>>>>>Errore: Non esiste una corrispondenza con la tipologia delle linee mobili");
								return;
							}
						}
						
						rs0.close();
						pst0.close();
						System.out.println("vincoli superati con successo, proseguo...");
						//arrivati qui abbiamo almeno un entry con quel codice registrazione, quel comune stabilimento, stato attivo
						String tipoGestoreString = null;
						switch(tipoIscrizione)
						{
							case MappingTipiGestoriLineeMobili.GESTORE_TRASPORTI :
								tipoGestoreString="GESTORE TRASPORTI";
								break;
							case  MappingTipiGestoriLineeMobili.GESTORE_DISTRIBUTORI:
								tipoGestoreString = "GESTORE DISTRIBUTORI";
								break;
							case MappingTipiGestoriLineeMobili.GESTORE_PRODOTTI:
								tipoGestoreString = "GESTORE PRODOTTI";
								break;
						}
						
						
						PreparedStatement pst = db.prepareStatement(insert);
						pst.setInt(++i,tipoIscrizione);
						pst.setString(++i,codiceRegistrazione);
						pst.setString(++i,nome);
						pst.setString(++i,cognome);
						pst.setString(++i,codice_fiscale);
						pst.setString(++i,sesso);
						pst.setString(++i,pec);
						
//						pst.setString(++i,comune_residenza); //come comune residenza va settato quello dello stab
						pst.setString(++i,istatComuneStabilimento);
						
						pst.setString(++i,provincia_residenza);
						pst.setString(++i,cap_residenza);
						pst.setString(++i,indirizzo_residenza);
						pst.setString(++i,telefono);
						pst.setString(++i,ip);
						pst.setString(++i,userAgent);
//						System.out.println("CONTROLLO CORRETTEZZA CAMPI PT 5");
						System.out.println(">>>>>>>>>>Controlli vincoli superati: inserisco in log_user_reg la richiesta (per "+tipoGestoreString+" ) con il codice registrazione "+codiceRegistrazione);
						pst.execute();
						System.out.println(">>>>>>>>>>Inserimento effettuato con successo!");
						
						SoggettoFisico soggetto = new SoggettoFisico();
						
						
						
						soggetto.setNome(nome);
						soggetto.setCognome(cognome);
						soggetto.setCodFiscale(codice_fiscale);
						soggetto.setSesso(sesso);
						soggetto.setEmail(pec);
						soggetto.setEnteredBy(291);
						soggetto.setModifiedBy(291);
						soggetto.setCodiceRegistrazione(codiceRegistrazione);
//						soggetto.setTipoIscrizione(tipoIscrizione); //non necessario
						
						Indirizzo ind = new Indirizzo();
						ind.setProvincia(provincia_residenza);
						ind.setCap(cap_residenza);
						ind.setVia(indirizzo_residenza);
						ind.setProvincia(siglaProvRes);
						ind.setComune(idComuneRes);

						soggetto.setIndirizzo(ind);
						soggetto.insert(db, request.getServletContext());


						String messageHD = "GISABDA \n , La persona "+nome+" "+cognome +" ha effettuato una richiesta di iscrizione alla banca dati regionale di gestione dei propri trasporti/distributori/prodotti.\nDi seguito i suoi dati :";
						
						
						messageHD+="\n-Tipo Iscrizione Richiesto : "+tipoGestoreString;
						messageHD+="\n-Nome : "+nome;
						messageHD+="\n-Cognome : "+cognome;
						messageHD+="\n-Cf : "+codice_fiscale;
						messageHD+="\n-Codice Registrazione : "+codiceRegistrazione;
						messageHD+="\n-Comune Stabilimento : " + nomeComuneStabilimento;
						messageHD+="\n-Pec :"+pec;
//						messageHD+="\n-Comune Residenza :"+comune_residenza;
						messageHD+="\n-Provincia Residenza :"+provincia_residenza;
						messageHD+="\n-Cap Residenza :"+cap_residenza;
						messageHD+="\n-Indirizzo Residenza :"+indirizzo_residenza;
						messageHD+="\n-Telefono :"+telefono;
						messageHD+="\n\n";
						
						System.out.println("MESSAGGIO PER ADMIN:----------------------------------------------------");
						System.out.println(messageHD);
						

						String messageUser = "Gentile Utente, la sua richiesta di iscrizione e' andata a buon fine.\n Il Nostro servizio di Help Desk prendera' in carico la sua richiesta.\nDi seguito la stampa riepilogativa dei dati";
						messageUser+="\n-Tipo Iscrizione Richiesto : "+tipoGestoreString;
						messageUser+="\n-Nome : "+nome;
						messageUser+="\n-Cognome : "+cognome;
						messageUser+="\n-Cf : "+codice_fiscale;
						messageUser+="\n-Codice Registrazione : "+codiceRegistrazione;
						messageUser+="\n-Comune Stabilimento : " + nomeComuneStabilimento;
						messageUser+="\n-Pec :"+pec;
//						messageUser+="\n-Comune Residenza :"+comune_residenza;
						messageUser+="\n-Provincia Residenza :"+provincia_residenza;
						messageUser+="\n-Cap Residenza :"+cap_residenza;
						messageUser+="\n-Indirizzo Residenza :"+indirizzo_residenza;
						messageUser+="\n-Telefono :"+telefono;
						messageUser+="\n\n"; 
						
						System.out.println("\nMESSAGGIO PER L'UTENTE-----------------------------------------\n");
						System.out.println(messageUser);
						sendMailPec(request,messageHD,"####RICHIESTA ISCRIZIONE GESTIONE PROPRI AUTOMEZZI/DISTRIBUTORI/PRODOTTI###","gisahelpdesk@usmail.it");
						sendMailPec(request,messageUser,"####BDAPI RICHIESTA ISCRIZIONE GESTIONE PROPRI AUTOMEZZI/DISTRIBUTORI/PRODOTTI###",pec);

						
						request.setAttribute("Esito", "0");
						request.setAttribute("Msg", "iscrizione Avvenuta con Successo");
					}
					
//				}
					
					
					
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	private void registrazioneAcque(HttpServletRequest request, HttpServletResponse response, Connection db) throws SQLException {
		String insert = "INSERT INTO log_user_reg(id_tipo_iscrizione,nome, cognome, codice_fiscale, "
				+ "pec, comune_residenza, telefono,ip,user_agent,data_richiesta)VALUES (?, ?, ?, ?, ?, ?,?,?,?, current_timestamp);" ;

		
		System.out.println(">>>>>>>>>>CHIAMATO METODO REGISTRAZIONE ACQUE DI RETE");
		String istatComuneStabilimento = request.getParameter("comune_stabilimento"); //questo e' l'istat in realta' (a me serve fare alcuni controlli usando l'id comune associato)
		String tipoIscrizione = request.getParameter("tipoIscrizione");
		System.out.println(">>>>>>>>>>SI TRATTA DI TIPO ISCRIZIONE: "+tipoIscrizione);
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codice_fiscale = request.getParameter("cf");
		String pec = request.getParameter("pec");
		String telefono = request.getParameter("telefono");
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");
		int idComuneRes = -1 ;
		
		System.out.println("parametri acquisiti");
		
		int i = 0 ;



		
		if (	nome.trim().equalsIgnoreCase("") || 
				cognome.trim().equalsIgnoreCase("") || 
				codice_fiscale.trim().equalsIgnoreCase("") || 
				pec.trim().equalsIgnoreCase("") || 
				telefono.trim().equalsIgnoreCase("") 

				)
		{
			request.setAttribute("Esito", "1");
			request.setAttribute("Msg", "Tutti i Campi sono Obbligatori");
			System.out.println(">>>>>>>>>>Errore: Almeno un campo non fornito");
			return;
		}
		else
		{
		
			
			
			
			
			
			
			int tipoGestoreInt= -1;
			ResultSet rs3 = db.prepareStatement("select id from gestori_acque_gestori where nome ilike '"+tipoIscrizione.replaceAll("'", "''")+"'").executeQuery();
			if (rs3.next())
				tipoGestoreInt = rs3.getInt("id");
			
				System.out.println("Inizio validazione vincoli");
		
					PreparedStatement pst0 = db.prepareStatement("select id, nome from comuni1 where istat = ?");
					
					pst0.setString(1,istatComuneStabilimento);
					System.out.println(pst0 +" ");
					ResultSet rs0 = pst0.executeQuery();
					if(!rs0.next())
					{
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", "Comune (stabilimento) non esistente in banca dati");
						System.out.println(">>>>>>>>>>Errore: Comune (stabilimento) non esistente in banca dati");
						return;
					}
					
					String nomeComuneStabilimento = rs0.getString("nome");
					Integer idComuneStabilimento =  rs0.getInt("id"); //lo utilizzeremo in seguito di nuovo
					System.out.println("Trovata corrispondenza anche per comune stabilimento, proseguo...");
					
					
					String verificaIscrizione = "select * from log_user_reg where id_tipo_iscrizione = ? and comune_residenza = ? ";
					PreparedStatement pst2 = db.prepareStatement(verificaIscrizione);
					pst2.setInt(1,tipoGestoreInt);
					pst2.setString(2,nomeComuneStabilimento);
					ResultSet rs = pst2.executeQuery();
					if (!rs.next())
					{
						
						PreparedStatement pst = db.prepareStatement(insert);
						pst.setInt(++i,tipoGestoreInt);
						pst.setString(++i,nome);
						pst.setString(++i,cognome);
						pst.setString(++i,codice_fiscale);
						pst.setString(++i,pec);
						
//						pst.setString(++i,comune_residenza); //come comune residenza va settato quello dello stab
						pst.setString(++i,nomeComuneStabilimento);
						
						
						pst.setString(++i,telefono);
						pst.setString(++i,ip);
						pst.setString(++i,userAgent);
//						System.out.println("CONTROLLO CORRETTEZZA CAMPI PT 5");
						System.out.println(">>>>>>>>>>Controlli vincoli superati: inserisco in log_user_reg la richiesta (per "+nome+ " " + cognome +" ) con il codice fiscale "+codice_fiscale);
						pst.execute();
						System.out.println(">>>>>>>>>>Inserimento effettuato con successo!");
						
						SoggettoFisico soggetto = new SoggettoFisico();
						
						
						
						soggetto.setNome(nome);
						soggetto.setCognome(cognome);
						soggetto.setCodFiscale(codice_fiscale);
						soggetto.setEmail(pec);
						soggetto.setEnteredBy(291);
						soggetto.setModifiedBy(291);
//						soggetto.setTipoIscrizione(tipoIscrizione); //non necessario
						Indirizzo ind = new Indirizzo();
						ind.setComune(idComuneRes);
						soggetto.setIndirizzo(ind);
						soggetto.insert(db, request.getServletContext());

						String messageHD = "GISAACQUE \n , La persona "+nome+" "+cognome +" ha effettuato una richiesta di iscrizione ai gestori acque di rete.\nDi seguito i suoi dati :";
						
						
						messageHD+="\n-Ente gestore : "+tipoIscrizione;
						messageHD+="\n-Nome : "+nome;
						messageHD+="\n-Cognome : "+cognome;
						messageHD+="\n-Cf : "+codice_fiscale;
						messageHD+="\n-Comune : " + nomeComuneStabilimento;
						messageHD+="\n-Pec :"+pec;
//						messageHD+="\n-Telefono :"+telefono;
						messageHD+="\n\n";
						
						System.out.println("MESSAGGIO PER ADMIN:----------------------------------------------------");
						System.out.println(messageHD);
						

						String messageUser = "Gentile Utente, la sua richiesta di iscrizione e' andata a buon fine.\n Il Nostro servizio di Help Desk prendera' in carico la sua richiesta.\nDi seguito la stampa riepilogativa dei dati";
						messageUser+="\n-Ente gestore : "+tipoIscrizione;
						messageUser+="\n-Nome : "+nome;
						messageUser+="\n-Cognome : "+cognome;
						messageUser+="\n-Cf : "+codice_fiscale;
						messageUser+="\n-Comune : " + nomeComuneStabilimento;
						messageUser+="\n-Pec :"+pec;
		
						messageUser+="\n-Telefono :"+telefono;
						messageUser+="\n\n"; 
						
						System.out.println("\nMESSAGGIO PER L'UTENTE-----------------------------------------\n");
						System.out.println(messageUser);
						sendMailPec(request,messageHD,"####RICHIESTA ISCRIZIONE GESTORE ACQUE DI RETE###","gisahelpdesk@usmail.it");
						sendMailPec(request,messageUser,"####GISAACQUE RICHIESTA ISCRIZIONE GESTORE ACQUE DI RETE###",pec);

						
						request.setAttribute("Esito", "0");
						request.setAttribute("Msg", "iscrizione Avvenuta con Successo");
					}
					else
					{
						request.setAttribute("Esito", "1");
						request.setAttribute("Msg", "Richiesta di iscrizione gia Presente");
			
					}
			}	
		
			
		}
		

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection db = null ;
		ConnectionPool cp = null ;
		String destinazioneRitorno = null;
		
		
		try
		{
			
			
			ApplicationPrefs applicationPrefs = (ApplicationPrefs) request.getServletContext().getAttribute(
					"applicationPrefs");
			
			ApplicationPrefs prefs = (ApplicationPrefs) request.getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			ce.setDriver(ceDriver);

			cp = (ConnectionPool)request.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection(ce,null);	
			
		
			System.out.println("RICHIESTA UNA REGISTRAZIONE : PER \"AUTOMEZZI/DISTRIBUTORI/PRODOTTI\" oppure per  \"API\" oppure per \"ACQUE\"");
			
			if(request.getParameter("provenienza") != null && request.getParameter("provenienza").equalsIgnoreCase("targhe"))
			{
				
				System.out.println("RICHIESTA PER AUTOMEZZI/DISTRIBUTORI/PRODOTTI......");
				destinazioneRitorno = "/gisa_nt/gisaBDA_targhe.jsp";
				registrazioneTarghe(request,response,db);
//				request.getServletContext().getRequestDispatcher("/testDirottamento.jsp").forward(request,response);
//				return ;
			}
			else if(request.getParameter("provenienza") != null && request.getParameter("provenienza").equalsIgnoreCase("acque"))
			{
				
				System.out.println("RICHIESTA PER ACQUE DI RETE......");
				destinazioneRitorno = "/gisa_nt/gisaBDA_acque.jsp";
				registrazioneAcque(request,response,db);
//				request.getServletContext().getRequestDispatcher("/testDirottamento.jsp").forward(request,response);
//				return ;
			}
			else //apiario
			{
				System.out.println("RICHIESTA PER APIARI......");
				destinazioneRitorno = "/gisa_nt/gisaBDA.jsp";
				registrazioneApi(request, response, db);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Errore nella chiamata della servlet "+e.getMessage());
			request.setAttribute("Esito", "1");
			request.setAttribute("Msg", "Errore Generico del Server contattare l'hel desk "/*e.getMessage()*/);
			e.printStackTrace();
		}

		finally
		{
			try {
				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		response.sendRedirect(destinazioneRitorno+"?esito="+request.getAttribute("Esito")+"&msg="+request.getAttribute("Msg"));
		
		
		
	
		
		

	}

	

	public String getPref(HttpServletRequest req, String param) {
		ApplicationPrefs prefs = (ApplicationPrefs) req.getServletContext().getAttribute( "applicationPrefs");
		if (prefs != null) {
			return prefs.get(param);
		} else {
			return null;
		}
	}

	public  void sendMailPec(HttpServletRequest req,String testo,String object,String toDest)
	{

		
		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable","true");
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		
		PecMailSender sender = new PecMailSender(configs,"M3023707", "US9560031.1a");
		try {
			sender.sendMail(object,testo,"gisasuap@cert.izsmportici.it", toDest, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			logger.error("MAIL NON INVIATA IN FASE DI REGISTRAZIONE UTENTE DA SITO www.gisacampania.it : " + e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("MAIL NON INVIATA IN FASE DI REGISTRAZIONE UTENTE DA SITO www.gisacampania.it : " + e.getMessage());
			e.printStackTrace();
		}
		
		// initialize the StringBuffer object within the try/catch loop
//		StringBuffer sb = new StringBuffer(testo);
//		Mail mail = new Mail();
//		mail.setHost(getPref( req,"MAILSERVER"));
//		mail.setFrom(getPref( req,"EMAILADDRESS"));
//		mail.setUser(getPref( req,"EMAILADDRESS"));
//		mail.setPass(getPref( req,"MAILPASSWORD"));
//		mail.setPort(Integer.parseInt(getPref( req,"PORTSERVER")));
//		if (toDest!=null && "".equals(toDest))
//			mail.setTo(getPref( req,"EMAILADDRESS"));
//		else
//			mail.setTo(toDest);
//		mail.setSogg(object);
//		mail.setTesto(testo);
//		mail.sendMail();



	}




}
