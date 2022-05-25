<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<style type="text/css">
#Copia ed adattamento del css contenuto in css > vam > homePage
#content
{
margin:0 auto;
width:899px;
}
#content p
{
font:normal 12px/18px Arial, Helvetica, sans-serif;
padding:10px;
color:#333333;
}
#content_right
{
margin:0 auto;
width:860px;
padding:5px;
}
#content h3
{
font:bold 12px/20px Arial, Helvetica, sans-serif;
color:#607B35;
}
#content_row1
{
margin:0 auto;
width:670px;
height:175px;
background:url('images/homePage/pets_clinic_08.gif') no-repeat 0 0;
padding-left:250px
}
#content_row2
{
margin:0 auto;
width:625px;
}

</style>

<div id="content">
  <table>
    <tr>
      <td >
		<div id="content_right">
        <table >
        	<tr>
        		<td>
        			<strong>
        				<font color="red">
        					<c:if test="${fn:length(trasferimentiDaAccettare) == 1}">
        						C'&egrave; un trasferimento in ingresso da accettare<br/>
        					</c:if>
        					<c:if test="${fn:length(trasferimentiDaAccettare) > 1}">
        						Ci sono ${fn:length(trasferimentiDaAccettare)} trasferimenti in ingresso da accettare<br/>
        					</c:if>
        					<c:if test="${fn:length(trasferimentiRientranti) == 1}">
        						<!--  E' rientrato un animale nell'ultima settimana-->
        					</c:if>
        					<c:if test="${fn:length(trasferimentiRientranti) > 1}">
        						<!-- Sono rientrati ${fn:length(trasferimentiRientranti)} animali nell'ultima settimana -->
        					</c:if>
        				</font>
        			</strong>
        		</td>
        	</tr>
          <tr>
            <td><h3>VAM</h3>
            
              <div id="content_row1">
                <p>
	                <b>VAM</b> (Veterinary Activity Management) è un sistema per la gestione delle principali funzionalità 
	                relative ad ospedali ed ambulatori veterinari pubblici della regione campania. 
	                In particolare il sistema permette ad una struttura di gestire:
					<br>
					<font color="red"><b>- L'accettazione e la presenza degli animali presso le strutture pubbliche</b></font>
					<br>
					<font color="red"><b>- Il percorso clinico e chirurgico di ogni animale (cane, gatto e sinantropo) dall'accettazione, alla cartella clinica, fino alle dimissioni;</b></font>
					<br>
					<font color="red"><b>- Le attivita' della struttura stessa (agenda, magazzino, etc.).</b></font>
					<br>
					<us:can f="AMMINISTRAZIONE" og="MAIN" r="w" sf="CAMBIO UTENTE">
					<center><table cellpadding="2" cellspacing="2"  border="1">
					<c:if test="${utente.ruolo=='17'}">						
						<tr><td>Username: <input type="text" name="username" id="username" style="text-align: center" /></td></tr>
						<tr><td><center><input type="button" value="Cambia Utente" onclick="location.href='login.CambioUtente.us?username='+document.getElementById('username').value"/></center></td></tr>
					</c:if>	
						<tr><td>Codice fiscale: <input type="text" name="cf_spid" id="cf_spid" style="text-align: center" /></td></tr>
						<tr><td><center><input type="button" value="Cambia Utente" onclick="location.href='login.Logout.us?cf_spid='+document.getElementById('cf_spid').value"/></center></td></tr>
						</table></center>
					</us:can>
					
                </p>
                </div>
                <br/>
                <br/>
                <div>
                	<p>
                		Scarica il modulo di errata corrige <a href="./errata corrige.doc">QUI</a>
                	</p>
                </div>
                
                <h3>Note di Rilascio</h3>
                
                <strong>06/03/2017</strong>
                	<ul>
                		<li>Gestione flusso necroscopia diviso per richiesta ed esito;</li>
					</ul>
                	
                <strong>10/05/2016</strong>
                	<ul>
                		<li>III Livello Registro Tumori Animale;
						<li>Piano di monitoraggio Fauna Selvatica</li>
                	</ul>
                <strong>01/03/2016</strong>
                	<ul>
                		<li>Ristrutturazione gestione necrosocpie per separazione richiesta da effettivo esame;
						<li>Automatizzazione generazione numero riferimento mittente per sala settoria</li>
                	</ul>
                 <strong>17/12/2015</strong>
                	<ul>
                		<li>Manutenzione Evolutiva Gestione Esame Istopatologico e Necroscopie;</li>
                	</ul>
                <strong>09/12/2015</strong>
                	<ul>
                		<li>Modifiche Registrazioni Accettazione e Trasporto Spoglie;</li>
                	</ul>
                <strong>13/10/2015</strong>
                	<ul>
                		<li>Anagrafe cetacei spiaggiati e animali provenienti dallo zoo;</li>
                	</ul>
                <strong>28/09/2015</strong>
                	<ul>
                		<li>Accettazioni Multiple;</li>
                	</ul>
                <strong>08/04/2015</strong>
                	<ul>
                		<li>Inserimento Nuovo Proprietario da Registrazione di Adozione;</li>
                	</ul>
                <strong>01/04/2015</strong>
                	<ul>
                		<li>Inserimento 'Prelievo Campioni Leishmania' da CC;</li>
                	</ul>
                	
                <strong>18/03/2015</strong>
                	<ul>
                		<li>Ricattura automatica in accettazioni per animali randagi/reimmessi;</li>
						<li>Gestione esito leishmaniosi in cc;</li>
                	</ul>
                	
                <strong>31/07/2014</strong>
                	<ul>
                		<li>Aggiunta nuova motivazione in fase di accettazione: "Prelievo campioni leishmania";</li>
						<li>Eliminata informazione sul luogo decesso per dimissioni effettuate con motivazione decesso;</li>
						<li>Aggiunto collegamento al dettaglio della necroscopia dalla pagina di dettaglio esito istopatologico, anche quando visualizzato dalla sezione "Registro tumori";</li>
						<li>Aggiunte al dettaglio istopatologico le informazioni riguardanti Peso, Habitat e Alimentazione dell'animale;</li>
						<li>Aggiunta nuova motivazione in fase di dimissione per animali randagi ritrovati da altra asl e precedentemente in canile: "Restituzione a canile origine";</li>
						<li>Liberalizzate dal dominio asl le seguenti motivazioni in fase di accettazione: Ricattura, Ritrovamento, Ritrovamento (Smarrimento non denunciato);</li>
                	</ul>
                
                <strong>02/07/2014</strong>
                	<ul>
                		<li>Comunicazione in Bdu dimissione per 'Trasferimento a canile' e 'Ritorno a proprietario';</li>
						<li>Aggiunta Anamnesi, EOG ed EOP in 'Stampa CC';</li>
						<li>Storicizzazione modifiche CC chiuse;</li>
						<li>Aggiunta campo Tipologia nella lista CC;</li>
						<li>Aggiunta dati ritrovamento in tutte le stampe;</li>
						<li>Redirect ad accettazione su tasto 'Chiusura CC' se chiusura non possibile per mancanza dati smaltimento</li>
                	</ul>
                	
                <strong>06/05/2014</strong>
                	<ul>
						<li>Nuova stampa CC;</li>
                		<li>Nuova stampa verbale di prelievo;</li>
                		<li>Stampa richiesta multipla istopatologici;</li>
                		<li>Completamento della gestione documentale su tutte le altre stampe presenti nell'applicativo;</li>
                		<li>Gestione registrazione di 'Rinnovo Passaporto';</li>
                		<li>Aggiunta campo valore per gli esiti del necroscopico;</li>
                		<li>Upload immagini in fase di inserimento necroscopico</li>
                	</ul>
                	
                <strong>01/04/2014</strong>
                	<ul>
                		<li>Attività esterne per deceduti senza microchip;</li>
                		<li>Gestione CC chiuse;</li>
                		<li>Gestione delle necroscopie non effettuabili;</li>
                		<li>Gestione doppio microchip;</li>
                		<li>Aggiunta anomalie ad organi presenti nella necroscopia;</li>
                		<li>Uniformazione dei dati mostrati per il proprietario rispetto a Bdu;</li>
                		<li>Gestione data esito per necroscopia</li>
                	</ul>
                	
               	<strong>24/03/2014</strong>
               	<ul>
               		<li>Aggiunta reg. di Adozione fuori asl in accettazione e dimissioni cc;</li>
               		<li>Accesso in accettazione per utenti Università;</li>
               		<li>Gestione campi 'Inserito' e 'Modificato' nel dettaglio CC, esami CC e dimissioni;</li>
               		<li>Adattamento 'Stampa certificato decesso' per i sinantropi;</li>
               		<li>Aggiunta di famiglia e genere in anagrafe sinantropo deceduto non anagrafe;</li>
					<li>Aggiunta organi in necroscopia;</li>
					<li>Possibilità di aggiungere richieste istopatologiche su cc ricevute per effettuarne necroscopie;</li>
					<li>Lista 'Gestione Istopatologico' filtrata per proprio laboratorio;</li>
					<li>Adattamento scheda necroscopica per sinantropi mammiferi;</li>
					<li>Inserimento smaltimento carcassa nell'accettazione di destinazione del trasferimento;</li>
					<li>Aggiunta 'Gestione Istopatologico' per utenti Asl al fine di avere evidenza della ricezione di esiti;</li>
					<li>Correzioni varie su verbale di prelievo;</li>
					<li>Miglioramenti prestazionali generali</li>
               	</ul>
               
                   <strong>06/03/2014</strong>
               	<ul>
               		<li>Comunicazione con Bdu:abilitazione pulsante 'Inserimento registrazioni completato' finchè non viene diversamente trasmesso da BDU</li>
               	</ul>
                   <strong>11/02/2014</strong>
               	<ul>
               		<li>Quadro patologico prevalente nel modulo necroscopia</li>
               	</ul>
                   <strong>04/02/2014</strong>
               	<ul>
               		<li>Disabilitazione delle registrazioni senza animale(Smarrimento e furto): tali operazioni vanno effettuate esclusivamente in BDU</li>
               	</ul>
				<strong>20/01/2014</strong>
               	<ul>
               		<li>Gestione registrazione 'Prelievo DNA'</li>
               	</ul>                
               	<strong>07/01/2014</strong>
               	<ul>
               		<li>Anagrafe sinantropi;
               			<ol>
               				- modifica lista genere/famiglia
               			</ol>
               			<ol>
               				- eliminazione campi specie, mantello e taglia
               			</ol>
               			<ol>
               				- sostituzione campo 'data nascita' con et&agrave;
               			</ol>
               			<ol>
               				- tra i detentori la voce 'Presidio Ospedaliero Veterinario' è stata sostituita da 'CRAS presso POV - Asl Napoli 1'
               			</ol>
               			<ol>
               				- il termina Cattura è stato sostituito da 'Rinvenimento' e 'Re-Immissione da Rilascio
               			</ol>
               			<ol>
               				- gestione codice ISPRA
               			</ol>
               			<ol>
               				- le registrazioni di Decesso e Rilascio effettuate in Vam vengono riportate automaticamente nella banca Sinantropi
               			</ol>
               			<ol>
               				- adattamento organi EOP, EOG ed esame necroscopico
               			</ol>
               			<ol>
               				- adattamento strutture di ricovero e destinazione delle dimissioni
               			</ol>
               			<ol>
               				- aggiunto nella home, in 'Gestisci informazioni', anche la voce 'Lesioni da arma da fuoco'
               			</ol>
               			
               		</li>
               		<li>Inserimento un alert in aggiunta accettazione per conferma data inserita;</li>
               		<li>Aggiunta delle voci 'Polizia provinciale', 'Polizia di Stato' e 'Guardia di Finanza' e rimozione di 'Pubblica sicurezza' alla lista dei richiedenti ;</li>
               		<li>Inserita voce 'Terapie in degenza' in fase di trasferimento per effetturare trasferimenti a strutture propria asl con motivazione di solo ricovero;</li>
               		<!--  Gestione documentale in stampa cartella clinica;-->
               		<li>Uniformazione delle operazioni di richiesta istopatologico e necroscopia;
               			<ol>- Aggiunta in istopatologico del laboratorio di destinazione (IZSM o UNIV)</ol>
               			<ol>- Generazione trasferimento presso clinica esterna in caso di come laboratorio esterno per il necroscopico</ol>
               			<ol>- Sostituzione del 'Numero accettazione' del necroscopico con 'Numero riferimento mittente'</ol>
						<ol>- Qualora vengano richiesti più esami istopatologici insieme alla necroscopia, il 'Numero riferimento mittente' inserito la prima volta viene riportato uguale nelle maschere successive</ol> 
               		</li>
               		<li>Data selezioabile per il trasferimento in ingresso;</li>
               		<li>Inserimento registrazioni in BDR: il sistema riporta direttamente alla pagina BDR della reg interessata;</li>
               		<li>Visualizzazione della registrazione inserita in BDR(es.: sterilizzazione);</li>
               		<li>Aggiunta dati ritrovamento nelle registrazioni di decesso</li>
               	</ul>
               	<strong>25/10/2013</strong>
               	<ul>
               		<li>Gestione accettazioni pregresse: le accettazioni con data precedente a quella più recente saranno impedite.</li>
               	</ul>
               	
               	<strong>13/08/2013</strong>
               	<ul>
               		<li>Gestione piani IUV in accettazione.</li>
               	</ul>
               
               	<strong>15/07/2013</strong>
               	<ul>
               		<li>Creazione tasto 'Salva e continua' per inserire esami istopatologici in serie;</li>
					<li>Inserita obbligatorietà sui dati del ritrovamento per animale randagio che sono stati inseriti nel Certificato di Decesso;</li>
               		<li>Inserito num.accettazione del necroscopico nella stampa di richiesta dell'istopatologico;</li>
               		<li>Inseriti nel referto necroscopico i flag sterilizzato e randagio;</li>
               		<li>Trasferimento: la data è selezionabile dall'utente;</li>
               		<li>In presenza di CC con registrazione dell'esame necroscopico, la CC potrà essere chiusa solo a seguito di smaltimento;</li>
					<li>Inserito controllo per cui lo smaltimento deve essere sempre successivo alla nescroscopia;</li>
					<li>In caso di smaltimento della carcassa l'accettazione relativa non può essere modificata o eliminata;</li>
					<li>Data necroscopia e data istologico devono essere precedenti alla data smaltimento.</li>
               	</ul>
               
               	<strong>24/06/2013</strong>
               	<ul>
               		<li>Gestione operatori multipli sull'esame necroscopico;</li>
               		<li>Gestione operatori multipli in 'Altro Intervento' del modulo Chirurgia;</li>
               		<li>Nuova impostazione della pagina di accettazione: non viene mostrata direttamente tutta la lista che causava rallentamenti al sistema;</li>
               		<li>Potenziamento della stabilità del sistema ed ottimizzazione della gestione degli errori;</li>
               	</ul>
               	
               	<strong>06/06/2013</strong>
               	<ul>
               		<li>Lo Smaltimento carcassa è passato dalla cc necroscopica alla pagina di dettaglio accettazione;</li>
               		<li>Nella cc necroscopica è stato predisposto il tasto 'Chiusura CC';</li>
               		<li>Inserita la Stampa del Certificato di Decesso nell'accettazione di animale deceduto e nella cc necroscopica;</li>
               		<li>Nuova gestione delle dimissioni per decesso;</li>
               		<li>Come sottovoci di 'Stato di nutrizione' della necroscopia sono state inserite quelle presenti nell'EOG;</li>
               		<li>Se l'animale è deceduto senza mc e randagio, i dati sul ritrovamento si inseriscono in fase di accettazione, anzichè in nescroscopia come accadeva in precedenza;</li>
               		<li>In fase di inserimento accettazione sono state inserite le informazioni sul proprietario;</li>
               		<li>Inserita la probabile causa del decesso nell'anagrafe animale deceduto senza mc;</li>
               		<li>In accettazione inseriti i campi Telefono e Residenza del richiedente, se è Privato;</li>
               		<li>Ottimizzazione della comunicazione con Canina;</li>
               		<li>Aggiunti tutti i dati anagrafici dell'animale nel referto necroscopico e nella stampa dell'accettazione;</li>
               		<li>Inibito l'inserimento della sterilizzazione, nel modulo chirurgia, per i sinantropi;</li>
               		<li>Inseriti i controlli sulle date nel modulo Dimissioni e Smaltimento Carcassa;</li>
					<li>Potenziamento della stabilità del sistema;</li>
               	</ul>
               	
               	<strong>23/05/2013</strong>
               	<ul>
               		<li>Creata la funzionalità di stampa della CC;</li>
               		<li>Creata la funzionalità di stampa del trasferimento;</li>
               		<li>Nell'esame necroscopico è stato reso possibile deselezionare le voci relative a "Fenomeni tanatologici" e "Cause morte finale";</li>
               		<li>Inserito un controllo nell'esame necroscopico: la data deve essere maggiore/uguale alla data del decesso;</li>
               		<li>Nell'esame necroscopico è stata inserita la riga mancante relativa al mantello;</li>
               		<li>Corretta la visualizzazione dei dati della colonia;</li>
               		<li>Eliminata la gestione della taglia per i gatti;</li>
               		<li>Aggiornati i numeri delle CC generate in seguito all'azzeramento del progressivo avvenuto il 23/04;</li>
               	</ul>
               	
               	<strong>14/05/2013</strong>
               	<ul>
               		<li>Inserito il riepilogo della CC in ogni sezione della stessa;</li>
               		<li>Inserito il riferimento al microchip nelle pagine in cui si apre la BDR in modalità interattiva;</li>
               		<li>Inserito nella sezione Diagnosi della CC i dati relativi al ricovero;</li>
               	</ul>
               	
               	<strong>02/05/2013</strong>
               	<ul>
               		<li>Creato il modulo Segnalazioni anche per gli utenti che non sono referenti Asl;</li>
               	</ul>
                </td>
          </tr>
        </table>
      </div>
</td>
</tr>
</table>
</div>