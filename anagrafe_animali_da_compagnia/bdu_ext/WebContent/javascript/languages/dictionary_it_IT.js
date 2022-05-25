/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
if (!FrameworkI18N) {
  var FrameworkI18N = {
    message:
    {
      "file.required":                  "- Il file � richiesto\r\n",
      "organization.required":          "- Il nome dell'organizzazione � richiesta\r\n",
      "specify.alert.description":      "- Prego specificare una descrizione per l'avviso\r\n",
      "data.list.required":             "- La Data elencata � richiesta\r\n",
      "serial.number.required":         "- Il numero seriale � richiesto\r\n",
      "PortalRole.required":            "- Il ruolo del portale � richiesto\r\n",
      "specify.blank.records":          "- I Campi vuoti non possono essere salvati\r\n",
      "name.required":                  "- Il nome � un campo richiesto.\r\n",
      "Form.not.submitted":             "Il modulo non pu� essere inviato.          \r\nPer favore verifica i seguenti:\r\n\r\n",
      "subject.required":               "- Un oggetot � richiesto\r\n",
      "URL.invalid":                    "- L'URL inserito non � valido.  Accertati che non ci siano caratteri non validi\r\n",
      "commission.entered.invalid":     "- La commissione inserita non � valida\r\n",
      "select.CommunicationType":       "Prego selezionare un Tipo di Comunicazione",
      "low.estimate":                   "- Le stime inferiori non possono essere maggiori delle stime superiori\r\n",
      "check.phone":                    "- Almeno un numero di telefono inserito non � valido.  ccertati che non ci siano caratteri non validi e di aver inserito il prefisso\r\n",
      "File.not.submitted":             "Il file non pu� essere inviato.          \r\nPer favore verifica i seguenti elementi:\r\n\r\n",
      "are.you.sure":                   "Sei sicuro?",
      "check.url":                      "- L'URL inserito non � valido.  Accertati che non ci siano caratteri non validi\r\n",
      "specify.type":                   "- Prego specificare un tipo\r\n",
      "sure.select.account":            "- Accertati di aver selezionato un account.\r\n",
      "delete.preference":              "Sei sicuro di voler cancellare le preferenze",
      "specify.alert.type":             "- Prego selezionare un tipo di avviso\r\n",
      "Fileinfo.not.submitted":         "Le informazioni sul file non possono essere inviate.          \r\nPer favore verifica i seguenti elementi:\r\n\r\n",
      "check.email":                    "- Almeno un indirizzo email inserito non � valido.  Accertati che non ci siano caratteri non validi\r\n",
      "check.form":                     "Il modulo non pu� essere salvato, per favore verifica i seguenti:\r\n\r\n",
      "check.send.email":               "Il messaggio non pu� essere inviato, per favore verifica i seguenti:\r\n\r\n",
      "no.create.relationship":         "Non poso creare la relazione, per favore verifica i seguenti:\r\n\r\n",
      "provider.needs.relationship":    "- Un provider deve essere selezionato per creare la relazione\r\n",
      "Name.required":                  "- Il nome � richiesto\r\n",
      "AlertDate.before.today":         "La data di avviso � anteriore alla data odierna\r\n",
      "Subject.required":               "- L'oggetto � richiesto\r\n",
      "relationshiptype.needs.selected":"- Deve essere selezionato un tipo di relazione\r\n",
      "Filename.required":              "- Il nome del file � richiesto\r\n",
      "specify.alert.date":             "- Prego specificare una data di avviso\r\n",
      "specify.opportunity.type":       "- Prego selezionare un tipo di opportunit� (account o contatto)\r\n",
      "check.ticket.issue.entered":     "- Controlla che il problema sia inserito\r\n",
      "check.ticket.contact.entered":   "- Controlla che il contatto sia selezionato\r\n",
      "title.required":                 "- Il titolo � un campo richiesto\r\n",
      "description.required":           "- La descrizione � un campo richiesto\r\n",
      "startdate.required":             "- La data di inizio � un campo richiesto\r\n",
      "Documents.team.role":            "(Ruolo)",
      "Documents.team.dept":            "(Dip)",
      "check.quote.email.entered":      "- Controlla che l'indirizzo email sia inserito\r\n",
      "check.quote.phone.entered":      "- Controlla che un numeor di telefono valido sia inserito\r\n",
      "check.quotes.notes.empty":       "Per favore inserisci le note che devono essere salvate.",
      "check.quote.product.name":       "- Prego specificare il nome del prodotto\r\n",
      "none.selected":                  "Non hai selezionato",
      "check.priceamount.blank":        "- Il prezzo non pu� essere nullo\r\n",
      "check.quote.extendedprice.blank":"- Il prezzo esteso non pu� essere nullo.\r\n",
      "check.quote.quantity.blank":     "- La quantit� non pu� essere nulla.\r\n",
      "check.quote.quantity.invalid":   "- La quantit� non � un numero valido.\r\n",
      "check.quote.extendedprice.invalid":"Il prezzo esteso non � un prezzo valido.\n",
      "check.quote.estdeldate.invalid": "La data di consegna prevista non � una data valida.\n",
      "check.quote.valid.entries":      "\nPer favore inserisci valori corretti per continuare",
      "program.error.conditions":       "Errore di programma. La posizione/modulo deve essere specificato per le condizioni",
      "program.error.remarks":          "Errore di programma. La posizione/modulo deve essere specificato per i commenti",
      "check.product.filename":         "Prego specificare il file che deve essere caricato",
      "image.thumbnail":                ": Immagine di anteprima",
      "image.small":                    ": Immagine piccola",
      "image.large":                    ": Immagine grande",
      "check.name":                     "- Prego specificare il nome\r\n",
      "label.all":                      "Tuitti",
      "check.number.invalid":           "- Prego inserire un numero valido\r\n",
      "check.product.quantity":         "Per favore indica la quantit� di almeno un prodotto",
      "check.product.categoryName":     "- Prego specificare il nome della categoria\r\n",
      "check.product.code":             "- Il codice � richiesto\r\n",
      "check.product.category":         "- La catgoria � richiesta\r\n",
      "verify.quote.newversion":        "Sei sicuro di voler creare una nuova versione di quesot commento?",
      "check.activity.folder.items":    "Il modulo della cartella attivit� non pu� essere inviato.          \r\nPer favore verifica i seguenti elementi:\r\n\r\n",
      "check.indentlevel.between":      "- Il livello di indentazione deve essere tra  0 e ",
      "check.number.loefield":          "- Nei campi LOE sono permessi solo numeri\r\n",
      "user.cannotbechanged.reason":    "Il ruolo dell'utente non pu� essere cambiato.\r\nMotivi possibili:\r\n- Ci potrebbe essere almeno un (1) altro utente con un ruolo di capo progetto\r\n- Tu devi essere in un ruolo che permetta la modifica dei ruoli degli utenti",
      "button.pleasewait":              "Prego attendere...",
      "check.subject":                  "- L'oggeto � un campo richiesto\r\n",
      "check.intro":                    "- Intro � un campo richiesto\r\n",
      "check.message":                  "Questo messaggio non pu� essere inviato.          \r\nPer favore verifica i seguenti elementi:\r\n\r\n",
      "check.message.required":         "- Il messaggo � un campo richiesto\r\n",
      "check.forum.name":               "- Il nome del Forum � un campo richiesto\r\n",
      "select.product":                 "- Seleziona un prodotto\r\n",
      "quote.notmodifiable":            "Il commento non pu� essere modificato.\nPer favore copialo o creane una nuova versione.",
      "check.ticket.resolution.atclose":"- La soluzione deve essere compilata quando chiudi una scheda\r\n",
      "check.servicecontract.number":   "- Il Numero di Contratto di Servizio � richesto\r\n",
      "check.init.contract.date":       "- La Data del Contratto Iniziale � richiesta\r\n",
      "check.response.time":            "- Il Tempo di Risposta � richiesto\r\n",
      "check.telephone.service":        "- Il Servizio Telefonico � richiesto\r\n",
      "check.onsite.service":           "- Il Servizio sul Sito � richiesto\r\n",
      "check.email.service":            "- Il Servizio Email � richiesto\r\n",
      "check.criteria":                 "- I Criteri sono richiesti\r\n",
      "confirm.delete.folder":          "Sei sicuro di voler cancellare questa cartella e tutti i dati associati che posso essere inseriti?",
      "confirm.delete.group":           "Sei sicuro di voler cancellare il gruppo, i campi in questo gruppo, e ogni dato associato inserito nei moduli di registrazione?",
      "confirm.delete.field":           "Sei sicuro di voler cancellare  il campo, e ogni dato associato inserito nei moduli di registrazione?",
      "caution.nothingtoremove":        "Nulla da eliminare",
      "caution.itemneedstobe.selected": "Ogni elemento deve essere selezionato prima di poter essere eliminato",
      "caution.category.disabled":      "La categoria selezionata � gi� disabilitata",
      "button.addR":                    "Agiungi >",
      "caution.category.exists":        "Una Categoria con quella descrizione esiste gi�",
      "caution.doublequotes.notallowed":"Le doppi virgoletto non sono ammesse nella descrizione",
      "caution.item.needed":            "Un elemento deve essere selezionato",
      "caution.singleitem.needed":      "Un singolo elemento deve essere selezionato",
      "caution.category.enabled":       "La Categoria � gi� abilitata",
      "button.updateR":                 "Aggiorna >",
      "check.username":                 "- Controlla che un Nome Utente sia inserito\r\n",
      "check.bothpasswords.match":      "- Controlla che entrambe le password siano inserite corretamente\r\n",
      "check.role.selected":            "- Controlla che un ruolo sia selezionato\r\n",
      "alert.noentriestoactivate":      "Nessuna elemento da attivare",
      "confirm.looseChanges":           "Perderai tutte le modifiche fatte alla bozza. Procedo ?",
      "check.campaign.name":            "- Il Nome della Compagnia � richiesto\r\n",
      "option.none":                    "--Nessuno--",
      "check.group.name":               "- Il Nome del Gruppo � richiesto\r\n",
      "check.campaign.criteria":        "I Criteri non possono essere processati, per favore verifica i seguenti:\r\n\r\n",
      "caution.selectfiletodownload":   "Prego selezionare un file da scaricare",
      "caution.provideanswer.survey":   "Prego fornire una risposta per ogni elemento del sondaggio.\r\n\r\n",
      "label.activate":                 "Attiva",
      "caution.provideanswer.required": "Prego fornire una risposta per ogni elemento richiesto del sondaggio.\r\n\r\n",
      "check.reminder":                 "- Controlla che il promemoria sia inserito correttamente\r\n",
      "check.alertdate.beforetoday":    "Data di avviso antecedente alla data odierna\r\n",
      "check.street.address":           "- L'indirizzo � richiesto\r\n",
      "check.city.name":                "- Il nome della citt� � richiesto\r\n",
      "check.state.name":               "- Il nome dello stato � richiesto\r\n",
      "check.country.name":             "- Il nome del Paese � richiesto\r\n",
      "confirm.delete.contact.address": "Sei sicuro di voler cancellare l'indirizzo del contatto selezionato?",
      "check.email.address":            "- L'indirizzo Email � richiesto\r\n",
      "check.valid.email.address":      "- Prego inserire un indirizzo Email valido\r\n",
      "check.phonenumber":              "- Il numero di telefono � richiesto\r\n",
      "check.valid.number":             "- Prego inserire un numero valido\r\n",
      "alert.user.role.notchangable":   "Qusto ruolo dell'utente non pu� essere cambiato.\r\nMotivi possibili:\r\n- Ci potrebbe essere almeno un (1) altro utente con un ruolo di capo progetto\r\n- Tu devi essere in un ruolo che permetta la modifica dei ruoli degli utenti.",
      "description.required.resubmit":  "Una descrizione � richiesta, per favore verifica quindi prova ad inviare nuovamente l'informazione.",
      "select.user.toreassign":         "- L'utente al quale riassegnare deve essere selezionato",
      "alert.reassignments":            "Le riassegnazioni non possono essere fatte, per favore verifica i seguenti:\r\n\r\n",
      "select.hours":                   "- Le Ore sono richieste\r\n",
      "check.hours.invalid":            "- Ore non valide\r\n",
      "select.reason":                  "- La motivazione � richiesta\r\n",
      "check.user.twologins.one":       "Questa applicazione permette ad un utente di essere collegato una sola volta\n e sembra che tu sia gi� collegato\n dal seguente indirizzo internet: ",
      "check.user.twologins.two":       ".\n\n Questo messaggio pu� anche apparire se non ti sei precedentemente scollegato\n e stai semplicemente provando a collegarti di nuovo dallo stesso browser.\n\n Scegli OK per conuare il collegamento.\n Scegli ANNULLA per ritornare alla maschera di collegamento.",
      "specify.contact":                "- Prego specificare un contatto\r\n",
      "check.length.wholenumber":       "- Controlla che la lunghezza sia un numero intero\r\n",
      "check.single.description":       "- E' permessa solo una singola descrizione\r\n",
      "check.module":                   "Il Modulo � richiesto\n",
      "check.targetdirectory":          "- La Cartella di destinazione � un campo richiesto\r\n",
      "check.firstname":                "- Il Nome � un campo richiesto\r\n",
      "check.lastname":                 "- Il Cognome � un campo richiesto\r\n",
      "check.company":                  "- La Compagnia � un campo richiesto\r\n",
      "check.emailaddress":             "- L'indirizzo Email � un campo richiesto\r\n",
      "check.emailaddress.invalid":     "- L'indirizzo Email non � valido.  Controlla che non ci siano caratteri non validi\r\n",
      "check.username":                 "- Il nome utente � un campo richiesto\r\n",
      "select.language":                "Prego selezionare una lingua per continuare",
      "check.profile":                  "- Il Profilo � un campo richiesto\r\n",
      "check.organization":             "- L'Organizzazione � un campo richiesto\r\n",
      "check.proxyhost":                "- Il Proxy host  � un campo richiesto quando proxy � selezionato\r\n",
      "check.proxyport.number":         "- La porta del Proxy deve essere un numero\r\n",
      "check.upgradesystem":            "Sei sicuro che vuoi aggiornare il sistema adesso?",
      "check.password":                 "- La Password � un campo richiesto\r\n",
      "check.licensekey":               "Inserisci la chiave di licenza per continuare",
      "check.registration.option":      "Prego seelezionare un'opzione di registrazione per continuare",
      "status.change.requirement":      "Lo stato pu� essere cambiato solo dall'utente al quale il compito � assegnato",
      "cannot.delete.task.reason":      "Non posso cancellare il compito selezinato perch� � stato gi� assegnato ad un altro utente.",
      "check.descriptionofservice":     "- La Descrizione del Servizio � un campo richiesto\r\n",
      "check.allitems.part.one":        "- Controlla tutti gli elementi nella riga ",
      "check.allitems.part.two":        " siano compilati\r\n",
      "check.issue.required":           "- Il problema � richiesto\r\n",
      "select.account.first":           "Devi prima selezionare un Account",
      "no.permission.addemployees":     "Non hai i permessi per aggiungere impiegati",
      "no.permission.addcontacts":      "Non hai i permessi per aggiungere contatti",
      "label.anyone":                   "Chiunque",
      "check.alertdate":                "- La Data di Avviso � obbligatoria se � selezionato Richiedi Monitoraggio\r\n",
      "check.followup.description":     "- La Descrizione del Monitoraggio � obbligatoria se � selezionato Richiedi Monitoraggio\r\n",
      "alertdate.onlyif.followup":      "- La Data di Avviso � richiesta solo se � selezionato Richiedi monitoraggio\r\n",
      "followupdesc.onlyif.followup":   "- La Descrizione del Monitoraggio � richiesta solo se � selezionato Richiedi monitoraggio\r\n",
      "select.onerecipient":            "- Seleziona almeno un destinatario\r\n",
      "check.subject":                  "- Inserisci un oggetto\r\n",
      "check.message":                  "- Inserisci un messaggio\r\n",
      "check.description":              "- Controlla che la desrizione sia inserita\r\n",
      "select.one.emailaddress":        "- Seleziona almeno un indirizzo email\r\n",
      "specify.youremailaddress":       "- Prego specificare il tuo indirizzo email\r\n",
      "select.organization.and.contact":"Prego selezionare un'Organizzazione e un Contatto",
      "select.organization.first":      "Prego selezionare prima un'Organizzazione",
      "confirm.delete.item":            "Sei sicuro di voler cancelare questo elemento?",
      "quote.submitted.withoutaction":  "Commento inserito senza aver effettuato nessuna azione.",
      "no.adjustment":                  "Nessun adattamento",
      "label.none":                     "Nulla",
      "label.show":                     "Mostra",
      "label.hide":                     "Nascondi",
      "label.any":                      "Ognuno",
      "specify.contact":                "- Prego specificare un contatto\r\n",
      "webcal.message":                 "Vedere il tuo calendario Concourse Suite Community Edition offline richiede un'applicazione desktop che supporti iCalendar. Queste applicazioni comprendono Mozilla Sunbird, Apple iCal, e probabilmente altri.   \r\n\r\nQuesta caratteristica pu� non funzionare sul tuo sistema.",
      "cannot.assign":                  "Il capo non pu� essere assegnato. Prego saltare il capo corrente",
      "confirm.delete.lead":            "Sei sicuro di voler cancellare questo capo?",
      "check.postalcode":               "- Prego inserire un Codice Postale valido\r\n",
      "check.phone.ext":                "- Prego inserire un interno telefonico valido\r\n",
      "provide.optionvalue":            "Devi fornire un valore per la nuova opzione",
      "label.entryalreadyexists":       "Il valore inserito esiste gi�",
      "label.nothingtorename":          "Niente da rinominare",
      "label.contact.lastName":          "Cognome",
      "label.contact.firstName":          "nome",
      "label.account.name":              "Nome dell'Account",
      "label.account.number":          "Numero dell'Account",
      "label.account.sc.serialnumber":              "Numero Serial",
      "label.account.sc.number":          "Numeor del Contratto di Servizio",
      "select.torename":                "Un elemento deve essere selzionato prima che possa essere rinominato",
      "required.oneiteminlist":         "Devi avere almeno un elemento in questa lista.",
      "check.textmessage":              "- Almeno un indirizzo di un messaggio di testo non � valido.  Accertati che non ci siano caratteri non validi\r\n",
      "confirm.quotesubmit":            "Sei sicuro di voler inviare questo commento perch� venga revisionato dal contatto selezinato?",
      "confirm.quoteproductclone":      "Sei sicuro di voler copiare il commento selezionato?",
      "label.news.required":            "- Controlla che l'etichetta delle novit� sia inserita\r\n",
      "label.discussion.required":            "- Controlla che l'etichetta delle discussioni sia inserita\r\n",
      "label.documents.required":            "- Controlla che l'etichetta dei documenti sia inserita\r\n",
      "label.lists.required":            "- Controlla che l'etichetta delle liste sia inserita\r\n",
      "label.plan.required":            "- Controlla che l'etichetta dei progetti sia inserita\r\n",
      "label.tickets.required":            "- Controlla che l'etichetta degli interventi sia inserita\r\n",
      "label.accounts.required":            "- Controlla che l'etichetta degli account sia inserita\r\n",
      "label.team.required":            "- Controlla che l'etichetta del team sia inserita\r\n",
      "label.details.required":            "- Controlla che l'etichetta dei dettagli sia inserita\r\n",
      "check.valid.input":              "Prego inserire un input valido",
      "check.valid.date":               "Prego inserire una data valida",
      "check.avoided.text":             "Prego inserire un input valido. Evita [*^|]"
    }
  }
}
