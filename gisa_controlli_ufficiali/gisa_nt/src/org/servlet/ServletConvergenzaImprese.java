/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ServletConvergenzaImprese
 */
@WebServlet("/ServletConvergenzaImprese")
public class ServletConvergenzaImprese extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletConvergenzaImprese() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		Connection db = null;
		ConnectionPool cp = null ;

		
		String namePool = "ConnectionPool";
		
		if (request.getParameter("pool")!=null)
			namePool=request.getParameter("pool");
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

			cp = (ConnectionPool)request.getServletContext().getAttribute(namePool);
			db = cp.getConnection(ce,null);
			String URLDB = db.getMetaData().getURL();

			StringBuffer OUTPUT = new StringBuffer();
			OUTPUT.append("<h1>STRINGA DI CONNESSIONE DB "+URLDB+"</h1>");
			
			

			try
			{
				int scelta = Integer.parseInt(request.getParameter("scelta"));
				request.setAttribute("Scelta", scelta);
				switch(scelta)
				{
				case 1 :
				{	

					scelta1(db, OUTPUT);
					break;
				}
				case 2 :
				{	

					scelta2(db, OUTPUT);
					break;
				}
				case 3 :
				{	

					scelta3(db, OUTPUT);
					break;
				}
				case 4 :
				{	

					scelta4(db, OUTPUT);
					break;
				}
				default:
				{
				
					
					OUTPUT.append("<font color='red'>SCELTA NON VALIDA [1 - 2- 3]</font>");
					request.setAttribute("Result", OUTPUT);
					break;
				}
				}
				request.setAttribute("Result", OUTPUT);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/convergenzaImprese.jsp");
			    dispatcher.forward(request, response);

			

			}
			catch(Exception e)
			{
				
				OUTPUT.append("<font color='red'>Indicare il tipo di \"Scelta\" [1 - 2- 3]</font>");
				request.setAttribute("Result", OUTPUT);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/convergenzaImprese.jsp");
			    dispatcher.forward(request, response);
			}

		}
		catch(Exception e)
		{

		}
		finally
		{
			cp.free(db,null);
		}


	}


	private void scelta1(Connection myConnection,StringBuffer OUTPUT)
	{

		try {
			long startTime = System.currentTimeMillis();
			// System.out.pritln("<br><br>Start Time::" +
			// startTime+"<br><br>");
			PreparedStatement myPreparedStatement = null;
			ResultSet myResultSet = null;

			String ragione_sociale = "";
			int id = 0;
			String tipo = "";
			String nome = "";
			String cognome = "";
			String cf = "";
			int id_ind = 0;
			String partita_iva = "";
			String entered = "";
			int id_madre = 0;
			int esito_confronto = 0;
			String confronto = "";
			String comune_sede_leg = "" ;
			String indirizzo_sede_leg= "" ;
			int toponimo= -1 ;
			String civico= "" ;
			String cap= "" ;
			int idStab = 0;

			String selectPI = "select o.id, o.ragione_sociale, o.partita_iva, l.description,to_char(o.entered, 'dd/mm/yyyy') as entered,of.nome,of.cognome,of.codice_fiscale,o.id_indirizzo, "
					+ "comuni1.nome as comune_sede_leg,ind.via as indirizzo_sede_leg,ind.toponimo ,ind.civico,ind.cap,st.id as id_stab "
					+ "from opu_operatore o left join opu_rel_operatore_soggetto_fisico os on (os.enabled and o.id=os.id_operatore) left join opu_soggetto_fisico of on of.id=os.id_soggetto_fisico  "
					+ "left join lookup_opu_tipo_impresa l on o.tipo_impresa=l.code "
					+ "left join opu_indirizzo ind on  ind.id = o.id_indirizzo "
					+ "left join comuni1 on comuni1.id = ind.comune "+
					"left join opu_stabilimento st on st.id_operatore = o.id and st.trashed_date is null " 
					+ "where o.trashed_date is null and  o.partita_iva<>'' and o.partita_iva "
					+ "is not null and o.partita_iva in (select partita_iva from opu_operatore where trashed_date is null and partita_iva<>'' and partita_iva is not null group by partita_iva having count(partita_iva)>1 order by partita_iva desc) "
//					+ "group by "+
//"o.id, o.ragione_sociale, o.partita_iva, l.description,to_char(o.entered, 'dd/mm/yyyy') ,"+
//"of.nome,of.cognome,of.codice_fiscale,o.id_indirizzo,comuni1.nome ,ind.via ,ind.toponimo ,ind.civico,ind.cap "
+ "order by o.partita_iva,o.id desc;";
				

			String LISTA_ID = "";
			myPreparedStatement = myConnection.prepareStatement(selectPI);
			myResultSet = myPreparedStatement.executeQuery();

			String queryConfrontoImpresa = "select id_esito from public_functions.confronto_impresa( ? , ? );";
			PreparedStatement pstConfrontoImpresa = null;
			ResultSet rsConfrentoImpresa = null;
			int i = 1;
			OUTPUT.append("<table>");
			int progressivoTab = 0;
			boolean primaRigaTabella = false;
			int prog =0;
			while (myResultSet.next()) {
				primaRigaTabella = false;
				prog ++ ;
				if (!partita_iva.equals(myResultSet.getString("partita_iva"))) {
					prog = 0 ;
					primaRigaTabella = true;
					progressivoTab ++;
					OUTPUT.append("</table><br>");
					OUTPUT.append("<br><b>" + i++ + ") PARTITA IVA: " + myResultSet.getString("partita_iva") + "</b><br><table border='1'>");
					OUTPUT.append("<tr><td><b> ID OPU_OPERATORE </b></td><td><b> PARTITA IVA </b></td><td><b> RAGIONE SOCIALE </b></td><td><b> TIPO IMPRESA </b></td><td><b> DATA INSER. </b></td><td><b> NOME RAPP. </b></td><td><b> COGNOME RAPP. </b></td><td><b> CF RAPP. </b></td><td><b> ID IND. SEDE LEGALE </b></td><td><b> CONFRONTO </b></td><td>COM.IND.TOP.CIV.CAP</td><td>TOT.STABILIMENTI(ATTIVI E NON)</td><td><b>SEL. IMPRESA DA TENERE</b></td><td><b>LISTA IMPR. DA RAGGRUPP.</b></td><td><b>SEL. INDIRIZZO SEDE LEGALE DA TENERE</b></td><td>ELIMINABILE</td><td><b>ESEGUI CONVERGENZA</b></td></tr>");
					id_madre = myResultSet.getInt("id");
					LISTA_ID = "";
				}
				partita_iva = myResultSet.getString("partita_iva");
				ragione_sociale = myResultSet.getString("ragione_sociale");
				id = myResultSet.getInt("id");
				tipo = myResultSet.getString("description");
				nome = myResultSet.getString("nome");
				cognome = myResultSet.getString("cognome");
				cf = myResultSet.getString("codice_fiscale");
				entered = myResultSet.getString("entered");
				id_ind = myResultSet.getInt("id_indirizzo");
				comune_sede_leg = myResultSet.getString("comune_sede_leg");
						indirizzo_sede_leg= myResultSet.getString("indirizzo_sede_leg");
						toponimo = myResultSet.getInt("toponimo");
						civico= myResultSet.getString("civico");
						cap= myResultSet.getString("cap");
						idStab= myResultSet.getInt("id_stab");
						
				LISTA_ID += "" + id + "&&&";
				// Query confronto
				pstConfrontoImpresa = myConnection.prepareStatement(queryConfrontoImpresa);
				pstConfrontoImpresa.setInt(1, id_madre);
				pstConfrontoImpresa.setInt(2, id);
				rsConfrentoImpresa = pstConfrontoImpresa.executeQuery();
				rsConfrentoImpresa.next();
				esito_confronto = rsConfrentoImpresa.getInt("id_esito");
				if (id_madre == id)
					confronto = " - ";
				else if (esito_confronto == 2)
					confronto = " UGUALE ";
				else
					confronto = " SIMILE ";

				String styleElimnaRed = "style=\"background-color: red\"";
				if (idStab>0)
				{
					styleElimnaRed="";

				}
					
				OUTPUT.append("<tr "+styleElimnaRed+" ><td>" + id + "</td><td>" + partita_iva + "</td><td>" + ragione_sociale
						+ "</td><td>" + tipo + "</td><td>" + entered + "</td><td>" + nome + "</td><td>" + cognome
						+ "</td><td>" + cf + "</td><td>" + id_ind + "</td><td>" + confronto + "</td>"
								+ "<td>"+comune_sede_leg + " | "+indirizzo_sede_leg+ " | "+toponimo+" | "+civico + " | "+cap+"</td>"
								+ "<td>"+idStab+"</td>");
				
				
				if(idStab>0)
				{
					OUTPUT.append("<td><input type = \"radio\" value =\""+id+"\" name=\"radio_"+progressivoTab+"\" ></td>"+
						"<td><input type = \"checkbox\" onclick=\"checkPremuto("+id+", "+idStab+",this)\"  value =\""+id+"\" name = \"check_"+progressivoTab+"\" ></td>"
						+ "<td><input type = \"radio\" value =\""+id_ind+"\" name=\"radioInd_"+progressivoTab+"\" ></td>"
);
					OUTPUT.append("<td>&nbsp</td>");

				}
				else
				{
					OUTPUT.append("<td><input type = \"radio\" value =\""+id+"\" name=\"radio_"+idStab+"\" ></td>"+
							"<td><input type = \"checkbox\"  onclick=\"checkPremuto("+id+", "+idStab+",this)\"  value =\""+id+"\" name = \"check_"+progressivoTab+"\" ></td>"
							+ "<td><input type = \"radio\" value =\""+id_ind+"\" name=\"radioInd_"+progressivoTab+"\" ></td>");
							
					OUTPUT.append("<td ><input type = \"button\" value =\"Elimina Impresa\" onclick=\"if(confirm('Sicuro Di Voler Eliminare Impresa con ID "+id+"')==true){eliminaImpreseSenzaStabilimenti("+id+");}\" ></td>");

				}
				
				
				if (primaRigaTabella==true)
				{
						OUTPUT.append("<td rowspan=\"3\"><input type =\"button\" value = \"convergi\" onclick=\"eseguiConvergenza('radio_"+progressivoTab+"','radioInd_"+progressivoTab+"','check_"+progressivoTab+"',this)\"></td>");
						OUTPUT.append("<td rowspan=\"3\"><input type =\"button\" value = \"convergenza Avanzata\" onclick=\"intercettaBtnConvImpresa('"+partita_iva+"')\"></td>");

				}
				OUTPUT.append("</tr>");
			}

			OUTPUT.append("</table>");
			System.out.println(OUTPUT);

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}


	}


	private void scelta2(Connection myConnection,StringBuffer OUTPUT)
	{
		try {
			long startTime = System.currentTimeMillis();
			// System.out.pritln("<br><br>Start Time::" +
			// startTime+"<br><br>");
			PreparedStatement myPreparedStatement = null;
			ResultSet myResultSet = null;

			int id=0;
			String entered="";
			int id_operatore=0;
			int id_indirizzo=0;
			int riferimento_org_id=0;
			String denominazione="";
			String codice_ufficiale_esistente="";


			String selectPI = 
			"select id,to_char(entered,'dd/mm/yyyy') as entered,id_operatore,id_indirizzo,riferimento_org_id,denominazione,codice_ufficiale_esistente,numero_registrazione from opu_stabilimento  where trashed_date is null and codice_ufficiale_esistente in "
			+ "(select codice_ufficiale_esistente from opu_stabilimento  where trashed_date is null  and codice_ufficiale_esistente  is not "
			+ "null and codice_ufficiale_esistente<>'' group by codice_ufficiale_esistente having count(codice_ufficiale_esistente)>1) "
			+ "order by codice_ufficiale_esistente desc;";
			myPreparedStatement = myConnection.prepareStatement(selectPI);
			myResultSet = myPreparedStatement.executeQuery();

			int i = 1;
			System.out.println ("<table>");
			while (myResultSet.next()) {

				if (!codice_ufficiale_esistente.equals(myResultSet.getString("codice_ufficiale_esistente"))) {
					OUTPUT.append("</table><br><b>" + i++ + ") CODICE UFFICIALE ESISTENTE: " + myResultSet.getString("codice_ufficiale_esistente") + "</b><br><table border='1'>");
					OUTPUT.append("<tr><td><b> ID OPU_STABILIMENTO </b></td><td><b> COD. UFF. ESIST. </b></td><td><b> DATA INS. </b></td><td><b> ID OPERATORE </b></td><td><b> DENOMINAZIONE </b></td><td><b> RIF. ORG_ID </b></td><td><b> ID INDIRIZZO </b></td></tr>");
				}

				id = myResultSet.getInt("id");
				entered = myResultSet.getString("entered");
				id_operatore = myResultSet.getInt("id_operatore");
				id_indirizzo = myResultSet.getInt("id_indirizzo");
				riferimento_org_id = myResultSet.getInt("riferimento_org_id");
				denominazione = myResultSet.getString("denominazione");
				codice_ufficiale_esistente = myResultSet.getString("codice_ufficiale_esistente");

				OUTPUT.append("<tr><td>" + id + "</td><td>" + codice_ufficiale_esistente + "</td><td>" + entered
						+ "</td><td>" + id_operatore + "</td><td>" + denominazione + "</td><td>" + riferimento_org_id + "</td><td>" + id_indirizzo
						+ "</td></tr>");
			}

			OUTPUT.append("</table>");
			myPreparedStatement.close();
			myResultSet.close();
		}catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}


	
	private void scelta3(Connection myConnection,StringBuffer OUTPUT)
	{
		
		int id_stab_pre=0;
		int ind_colore=0;
		String [] colori = new String[5];
		colori[0]="<tr bgcolor='#7FFF00'>";
		colori[1]="<tr bgcolor='#C0C0C0'>";
		colori[2]="<tr bgcolor='#87CEEB'>";
		colori[3]="<tr bgcolor='#FFFF00'>";
		colori[4]="<tr bgcolor='#ADD8E6'>";
		String current_tr="";
		try {
			long startTime = System.currentTimeMillis();
			// System.out.pritln("<br><br>Start Time::" +
			// startTime+"<br><br>");
			PreparedStatement myPreparedStatement = null;
			ResultSet myResultSet = null;
		
			int id_stabilimento = 0;
			int id_operatore = 0;
			int id_indirizzo_stabilimento = 0;
			int id_linea = 0;
			
			int codice_comune=0;
			String comune="";
			int toponimo=0;
			String via="";
			String civico="";
			String provincia="";
			
			
			String op_and_IDindirizzo="&&&";

			String selectPI = "select os.id as id_stabilimento,os.id_operatore,os.id_indirizzo as id_indirizzo_stabilimento,oi.comune as codice_comune,oi.comune_testo as comune,oi.toponimo,oi.via, oi.civico,  oi.provincia, osl.id as id_linea "
					+"from opu_stabilimento os join opu_relazione_stabilimento_linee_produttive osl on os.id=osl.id_stabilimento  "
					+"join opu_indirizzo oi on os.id_indirizzo=oi.id  "
					+"where enabled and trashed_date is null and id_operatore in ("
					+"						  select id_operatore from ("
					+"						   (select * from opu_stabilimento where trashed_date is null and id_operatore in (select id_operatore from opu_stabilimento where trashed_date is null group by id_operatore having count(id_operatore)>1 order by id_operatore desc))"
					+"						   EXCEPT "
					+"						   (select * from opu_stabilimento where trashed_date is null and codice_ufficiale_esistente in (select codice_ufficiale_esistente from opu_stabilimento  where trashed_date is null  and codice_ufficiale_esistente  is not "
					+"						   null and codice_ufficiale_esistente<>'' group by codice_ufficiale_esistente having count(codice_ufficiale_esistente)>1) ) "
					+"						   order by id_operatore,id  desc"
					+"						   ) as opu_stab where trashed_date is null group by id_operatore,id_indirizzo having count(*)>1"
					+"						 ) order by os.id_operatore,os.id desc;";

			myPreparedStatement = myConnection.prepareStatement(selectPI);
			myResultSet = myPreparedStatement.executeQuery();

			
			int i = 1;
			
			OUTPUT.append("<h3><b>SELEZIONE DEGLI STABILIMENTI A CUI<br> PER ID_OPERATORE E"
		  +"ID_INDIRIZZO_STABILIMENTO UGUALI CORRISPONDONO PIU' STABILIMENTI</b></h3><br><br>");
	
			
			OUTPUT.append("<table>");
			
			
			int ind_record=0;
			boolean stab_change=false;
			
			while (myResultSet.next()) {

				if (!op_and_IDindirizzo.equals(myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("id_indirizzo_stabilimento"))) {
					OUTPUT.append("</table>");
					
					if(ind_record>1 && stab_change){
//						System.out.println(OUTPUT);
						i++;	
					}
					
					OUTPUT.append("<br><br><b>" + i + ") COPPIA OPERATORE - ID_INDIRIZZO_STABILIMENTO: " + myResultSet.getString("id_operatore") + " - "+myResultSet.getString("id_indirizzo_stabilimento")+"</b><br><table border='1'>");
					OUTPUT.append("<tr><td><b> ID OPU_OPERATORE </b></td><td><b> ID_IND_STAB </b></td><td><b> ID_STABILIMENTO </b></td><td><b> CODICE COMUNE </b></td><td><b> COMUNE </b></td><td><b> TOPONIMO </b></td><td><b> VIA </b></td><td><b> CIVICO </b></td><td><b> PROVINCIA </b></td><td><b> ID_LINEA_ATT. </b></td></tr>");
					ind_record=0;
					stab_change=false;
					ind_colore=0;
					id_stab_pre=myResultSet.getInt("id_stabilimento");
				}
				op_and_IDindirizzo=myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("id_indirizzo_stabilimento");

				id_operatore=myResultSet.getInt("id_operatore");
				codice_comune=myResultSet.getInt("codice_comune");
				comune=myResultSet.getString("comune");
				id_stabilimento=myResultSet.getInt("id_stabilimento");
				id_indirizzo_stabilimento=myResultSet.getInt("id_indirizzo_stabilimento");
				toponimo=myResultSet.getInt("toponimo");
				via=(myResultSet.getString("via")!=null ? myResultSet.getString("via").toUpperCase() : "");
				civico=myResultSet.getString("civico");
				provincia=myResultSet.getString("provincia");
				id_linea = myResultSet.getInt("id_linea");

				ind_record++;
				
				if(id_stab_pre!=id_stabilimento){
					stab_change=true;
					ind_colore++;
					current_tr=colori[ind_colore%5];
					id_stab_pre=id_stabilimento;
				}else{
					current_tr=colori[ind_colore%5];
				}
				
				OUTPUT.append(current_tr+"<td>" + id_operatore + "</td><td>" + id_indirizzo_stabilimento + "</td><td>" + id_stabilimento
						+ "</td><td>" + codice_comune + "</td><td>" + comune + "</td><td>" + toponimo + "</td><td>" + via
						+ "</td><td>" + civico + "</td><td>" + provincia + "</td><td>" + id_linea + "</td></tr>");
				}

			OUTPUT.append("</table>");
			myPreparedStatement.close();
			myResultSet.close();
			myConnection.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}
	/*private void scelta3(Connection myConnection,StringBuffer OUTPUT)
	{


		try {
			int id_stab_pre=0;
			int ind_colore=0;
			String [] colori = new String[5];
			colori[0]="<tr bgcolor='#7FFF00'>";
			colori[1]="<tr bgcolor='#C0C0C0'>";
			colori[2]="<tr bgcolor='#87CEEB'>";
			colori[3]="<tr bgcolor='#FFFF00'>";
			colori[4]="<tr bgcolor='#ADD8E6'>";
			String current_tr="";

			long startTime = System.currentTimeMillis();
			// System.out.pritln("<br><br>Start Time::" +
			// startTime+"<br><br>");
			PreparedStatement myPreparedStatement = null;
			ResultSet myResultSet = null;
			

			int id_stabilimento = 0;
			int id_operatore = 0;
			int id_indirizzo_stabilimento = 0;
			int id_linea = 0;
			
			int codice_comune=0;
			String comune="";
			int toponimo=0;
			String via="";
			String civico="";
			String provincia="";
			
			
			String op_and_IDindirizzo="&&&";

			String selectPI = "select os.id as id_stabilimento,os.id_operatore,os.id_indirizzo as id_indirizzo_stabilimento,oi.comune as codice_comune,oi.comune_testo as comune,oi.toponimo,oi.via, oi.civico,  oi.provincia, osl.id as id_linea "
					+"from opu_stabilimento os join opu_relazione_stabilimento_linee_produttive osl on os.id=osl.id_stabilimento  and osl.enabled "
					+"join opu_indirizzo oi on os.id_indirizzo=oi.id  "
					+"where trashed_date is null and id_operatore in ("
					+"						  select id_operatore from ("
					+"						   (select * from opu_stabilimento where trashed_date is null and id_operatore in (select id_operatore from opu_stabilimento where trashed_date is null group by id_operatore having count(id_operatore)>1 order by id_operatore desc))"
					+"						   EXCEPT "
					+"						   (select * from opu_stabilimento where trashed_date is null and codice_ufficiale_esistente in (select codice_ufficiale_esistente from opu_stabilimento  where trashed_date is null  and codice_ufficiale_esistente  is not "
					+"						   null and codice_ufficiale_esistente<>'' group by codice_ufficiale_esistente having count(codice_ufficiale_esistente)>1) ) "
					+"						   order by id_operatore,id  desc"
					+"						   ) as opu_stab where trashed_date is null group by id_operatore,id_indirizzo having count(*)>1"
					+"						 ) order by os.id_operatore,oi.id desc;";

			myPreparedStatement = myConnection.prepareStatement(selectPI);
			myResultSet = myPreparedStatement.executeQuery();

			
			int i = 1;
			
			OUTPUT.append("<h3><b>SELEZIONE DEGLI STABILIMENTI A CUI<br> PER ID_OPERATORE E"
		  +"ID_INDIRIZZO_STABILIMENTO UGUALI CORRISPONDONO PIU' STABILIMENTI</b></h3><br><br>");
	
			
			OUTPUT.append("<table>");
			int progressivoTab = 0;
			boolean primaRigaTabella = false;
			int ind_record = 0 ;
			boolean stab_change = false;
			
			while (myResultSet.next()) {
				primaRigaTabella = false;
				if (!op_and_IDindirizzo.equals(myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("id_indirizzo_stabilimento"))) {
					OUTPUT.append("</table>");
					progressivoTab ++ ;
					if(ind_record>1 && stab_change){
						primaRigaTabella = true;
							
						i++;	
					}
					
					OUTPUT.append("<br><br><b>" + i + ") COPPIA OPERATORE - ID_INDIRIZZO_STABILIMENTO: " + myResultSet.getString("id_operatore") + " - "+myResultSet.getString("id_indirizzo_stabilimento")+"</b><br><table border='1'>");
					OUTPUT.append("<tr><td><b> ID OPU_OPERATORE </b></td><td><b> ID_IND_STAB </b></td><td><b> ID_STABILIMENTO </b></td><td><b> CODICE COMUNE </b></td><td><b> COMUNE </b></td><td><b> TOPONIMO </b></td><td><b> VIA </b></td><td><b> CIVICO </b></td><td><b> PROVINCIA </b></td><td><b> ID_LINEA_ATT. </b></td>"
							+ "<td><b>SEL. STABILIMENTO DA TENERE</b></td><td><b>LISTA STAB. DA RAGGRUPP.</b></td><td><b>SEL. INDIRIZZO STAB DA TENERE</b></td><td><b>ESEGUI CONVERGENZA</b></td></tr>");
							
					ind_record=0;
					stab_change=false;
					ind_colore=0;
					id_stab_pre=myResultSet.getInt("id_stabilimento");
				}
			
		
				op_and_IDindirizzo=myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("id_indirizzo_stabilimento");

				id_operatore=myResultSet.getInt("id_operatore");
				codice_comune=myResultSet.getInt("codice_comune");
				comune=myResultSet.getString("comune");
				id_stabilimento=myResultSet.getInt("id_stabilimento");
				id_indirizzo_stabilimento=myResultSet.getInt("id_indirizzo_stabilimento");
				toponimo=myResultSet.getInt("toponimo");
				via=(myResultSet.getString("via")!=null ? myResultSet.getString("via").toUpperCase() : "");
				civico=myResultSet.getString("civico");
				provincia=myResultSet.getString("provincia");
				id_linea = myResultSet.getInt("id_linea");
				ind_record++;
				if(id_stab_pre!=id_stabilimento){
					stab_change=true;
					ind_colore++;
					current_tr=colori[ind_colore%5];
					id_stab_pre=id_stabilimento;
				}else{
					current_tr=colori[ind_colore%5];
				}
				
				OUTPUT.append(current_tr+"<td>" + id_operatore + "</td><td>" + id_indirizzo_stabilimento + "</td><td>" + id_stabilimento
						+ "</td><td>" + codice_comune + "</td><td>" + comune + "</td><td>" + toponimo + "</td><td>" + via
						+ "</td><td>" + civico + "</td><td>" + provincia + "</td><td>" + id_linea + "</td><");
				
				
				
				OUTPUT.append("<td><input type = \"radio\" value =\""+id_stabilimento+"\" name=\"radio_"+progressivoTab+"\" ></td>"+
						"<td><input type = \"checkbox\" onclick=\"checkPremuto("+id_operatore+", "+id_stabilimento+",this)\"  value =\""+id_stabilimento+"\" name = \"check_"+progressivoTab+"\" ></td>"
						+ "<td><input type = \"radio\" value =\""+id_indirizzo_stabilimento+"\" name=\"radioInd_"+progressivoTab+"\" ></td>"
);
					
				if (primaRigaTabella==true)
				{
						OUTPUT.append("<td rowspan=\"3\"><input type =\"button\" value = \"convergi\" onclick=\"eseguiConvergenzaStabilimento('radio_"+progressivoTab+"','radioInd_"+progressivoTab+"','check_"+progressivoTab+"',this)\"></td>");
//						OUTPUT.append("<td rowspan=\"3\"><input type =\"button\" value = \"convergenza Avanzata\" onclick=\"intercettaBtnConvImpresa('"+partita_iva+"')\"></td>");

				}
				OUTPUT.append("</tr>");
				
				}

			OUTPUT.append("</table>");
			myPreparedStatement.close();
			myResultSet.close();
			myConnection.close();
		
		} catch (SQLException ex) {
			OUTPUT.append("<font color='red'>Attenzione sie verificato un errore : "+ ex.getMessage()+"</font>");
			
		}
	}*/

	
	private void scelta4(Connection myConnection,StringBuffer OUTPUT)
	{
	try {
		long startTime = System.currentTimeMillis();
		// System.out.pritln("<br><br>Start Time::" +
		// startTime+"<br><br>");
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		int id_madre=0;
		String confronto = "";

		int id_operatore=0;
		int codice_comune=0;
		String comune="";
		int id_stabilimento=0;
		int id_indirizzo=0;
		int toponimo=0;
		String via="";
		String civico="";
		String provincia="";
		
		String op_and_comune="&&&";
		String via_madre="";
		
		String selectPI = "select id_operatore,oi.comune as codice_comune,oi.comune_testo as comune,os.id as id_stabilimento,id_indirizzo,  toponimo, oi.via, civico,  provincia"
				+" from opu_stabilimento os join opu_indirizzo oi on os.id_indirizzo=oi.id where trashed_date is null and id_operatore in"
				+"("
				+"select id_operatore from opu_stabilimento os join opu_indirizzo oi on os.id_indirizzo=oi.id where trashed_date is null and id_operatore in"
				+"("
				+"(select id_operatore from opu_stabilimento where trashed_date is null  group by id_operatore having count(id_operatore)>1 EXCEPT"
				+"(select id_operatore from opu_stabilimento where trashed_date is null and id_operatore in ("
				+"   select id_operatore from ("
				+"      (select * from opu_stabilimento where trashed_date is null and id_operatore in (select id_operatore from opu_stabilimento where trashed_date is null group by id_operatore having count(id_operatore)>1 order by id_operatore desc))"
				+"      EXCEPT "
				+"      (select * from opu_stabilimento where trashed_date is null and codice_ufficiale_esistente in (select codice_ufficiale_esistente from opu_stabilimento  where trashed_date is null  and codice_ufficiale_esistente  is not "
				+"      null and codice_ufficiale_esistente<>'' group by codice_ufficiale_esistente having count(codice_ufficiale_esistente)>1) ) "
				+"      order by id_operatore,id  desc"
				+"      ) as opu_stab where trashed_date is null group by id_operatore,id_indirizzo having count(*)>1"
				+"   )"
				+"   ))"
				+"EXCEPT"
				+"(select id_operatore from opu_stabilimento where trashed_date is null and codice_ufficiale_esistente in (select codice_ufficiale_esistente from opu_stabilimento  where trashed_date is null  and codice_ufficiale_esistente  is not "
				+"      null and codice_ufficiale_esistente<>'' group by codice_ufficiale_esistente having count(codice_ufficiale_esistente)>1) "
				+" )) "
				+"group by id_operatore, comune having count(*)>1 order by id_operatore desc "
				+")order by id_operatore,os.id,oi.comune desc ;";
		myPreparedStatement = myConnection.prepareStatement(selectPI);
		myResultSet = myPreparedStatement.executeQuery();

		int i = 1;
		
		OUTPUT.append("<h3><b>SELEZIONE DEGLI STABILIMENTI CHE<br> PER ID_OPERATORE E"
	  +"CODICE_COMUNE UGUALE HANNO LA VIA 'SIMILE' (LEVENSHTEIN <= 5)<br>"
	  +"NB: RISULTATO AL NETTO DEI PRECEDENTI 2 CASI</b></h3><br><br>");

		
		OUTPUT.append("<table>");
		
		while (myResultSet.next()) {

			if (!op_and_comune.equals(myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("codice_comune"))) {
				OUTPUT.append("</table><br><br><b>" + i++ + ") COPPIA OPERATORE - COMUNE: " + myResultSet.getString("id_operatore") + " - "+myResultSet.getString("codice_comune")+"</b><br><table border='1'>");
				OUTPUT.append("<tr><td><b> ID OPU_OPERATORE </b></td><td><b> CODICE COMUNE </b></td><td><b> COMUNE </b></td><td><b> ID_STABILIMENTO </b></td><td><b> ID_INDIRIZZO </b></td><td><b> TOPONIMO </b></td><td><b> VIA </b></td><td><b> CIVICO </b></td><td><b> PROVINCIA </b></td><td><b> LEVENSHTEIN VIA </b></td></tr>");
				id_madre = myResultSet.getInt("id_stabilimento");
				via_madre=(myResultSet.getString("via")!=null ? myResultSet.getString("via").toUpperCase() : "");
			}
			op_and_comune=myResultSet.getString("id_operatore")+"&&&"+myResultSet.getString("codice_comune");

			id_operatore=myResultSet.getInt("id_operatore");
			codice_comune=myResultSet.getInt("codice_comune");
			comune=myResultSet.getString("comune");
			id_stabilimento=myResultSet.getInt("id_stabilimento");
			id_indirizzo=myResultSet.getInt("id_indirizzo");
			toponimo=myResultSet.getInt("toponimo");
			via=(myResultSet.getString("via")!=null ? myResultSet.getString("via").toUpperCase() : "");
			civico=myResultSet.getString("civico");
			provincia=myResultSet.getString("provincia");
			
			if (id_madre == id_stabilimento)
				confronto = " - ";
			else if (getLevenshteinDistance(via_madre,via) <= 5)
				confronto = ""+getLevenshteinDistance(via_madre,via);
			
			// Stampo solo se la differenza tra i 2 indirizzi e' <= 5, altrimenti non c'e' bisogno di stampare
			if (getLevenshteinDistance(via_madre,via) <= 5){
				OUTPUT.append("<tr><td>" + id_operatore + "</td><td>" + codice_comune + "</td><td>" + comune
					+ "</td><td>" + id_stabilimento + "</td><td>" + id_indirizzo + "</td><td>" + toponimo + "</td><td>" + via
					+ "</td><td>" + civico + "</td><td>" + provincia + "</td><td>" + confronto + "</td></tr>");
			}
		}

		OUTPUT.append("</table>");
		myPreparedStatement.close();
		myResultSet.close();
		myConnection.close();
	} catch (SQLException ex) {
		OUTPUT.append("SQLException: " + ex.getMessage());
		OUTPUT.append("SQLState: " + ex.getSQLState());
		OUTPUT.append("VendorError: " + ex.getErrorCode());
	}
	}
	
	
	
	private static int getLevenshteinDistance(String s, String t) {
	      if (s == null || t == null) {
	          throw new IllegalArgumentException("Strings must not be null");
	      }
	      int n = s.length(); // length of s
	      int m = t.length(); // length of t

	      if (n == 0) {
	          return m;
	      } else if (m == 0) {
	          return n;
	      }

	      if (n > m) {
	          // swap the input strings to consume less memory
	          String tmp = s;
	          s = t;
	          t = tmp;
	          n = m;
	          m = t.length();
	      }

	      int p[] = new int[n+1]; //'previous' cost array, horizontally
	      int d[] = new int[n+1]; // cost array, horizontally
	      int _d[]; //placeholder to assist in swapping p and d

	      // indexes into strings s and t
	      int i; // iterates through s
	      int j; // iterates through t

	      char t_j; // jth character of t

	      int cost; // cost

	      for (i = 0; i<=n; i++) {
	          p[i] = i;
	      }

	      for (j = 1; j<=m; j++) {
	          t_j = t.charAt(j-1);
	          d[0] = j;

	          for (i=1; i<=n; i++) {
	              cost = s.charAt(i-1)==t_j ? 0 : 1;
	              // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
	              d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
	          }

	          // copy current distance counts to 'previous row' distance counts
	          _d = p;
	          p = d;
	          d = _d;
	      }

	      // our last action in the above loop was to switch d and p, so p now 
	      // actually has the most recent cost counts
	      return p[n];
	  }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
