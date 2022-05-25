/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
if (!FrameworkI18N) {
  var FrameworkI18N = {
    message:
    {
      "file.required":                  "- Datei wird benouml;tigt\r\n",
      "organization.required":          "- Bitte geben Sie fuuml;r die Organisation einen Namen ein\r\n",
      "specify.alert.description":      "- Bitte geben Sie eine Alarmbeschreibung ein\r\n",
      "data.list.required":             "- Bitte geben Sie ein Datum ein\r\n",
      "serial.number.required":         "- Bitte geben Sie die Seriennummer ein\r\n",
      "PortalRole.required":            "- Portal Rolle wird benouml;tigt\r\n",
      "specify.blank.records":          "- Leere Datensauml;tze kouml;nnen nicht gespeichert werden\r\n",
      "name.required":                  "- Name ist ein Pflichtfeld.\r\n",
      "Form.not.submitted":             "Das Formular konnte nicht angenommen werden.          \r\nBitte beachten Sie Folgendes:\r\n\r\n",
      "subject.required":               "- Bitte geben Sie einen Betreff ein\r\n",
      "URL.invalid":                    "- Die angegebene URL ist unguuml;ltig.  Bitte žberpruuml;fen Sie diese nochmal\r\n",
      "commission.entered.invalid":     "- Die eingebene Kommission ist unguuml;ltig\r\n",
      "select.CommunicationType":       "Bitte wauml;hlen Sie einen Kommunikationstyp aus",
      "low.estimate":                   "- Der niedrigste Erwartungswert kann nicht houml;her als der houml;chste Erwartungswert sein\r\n",
      "check.phone":                    "- Mindestens eine eingegebene Telefonnummer ist unguuml;ltig. Bitte uuml;berpruuml;fen Sie diese auf unguuml;ltige Zeichen\r\n",
      "File.not.submitted":             "Die Datei konnte nicht versandt werden.          \r\nBitte beachten Sie Folgendes:\r\n\r\n",
      "are.you.sure":                   "Sind Sie sicher?",
      "check.url":                      "- Die angegebene URL ist unguuml;ltig.  Bitte uuml;berpruuml;fen Sie diese nochmal\r\n",
      "specify.type":                   "- Bitte geben Sie einen Typ an\r\n",
      "sure.select.account":            "- Vergewissern Sie sich, dass Sie einen Kunden ausgewauml;hlt haben.\r\n",
      "delete.preference":              "Sind Sie sicher, Sie wollen die Prauml;ferenz louml;schen?",
      "specify.alert.type":             "- Bitte geben Sie einen Alarmtyp an\r\n",
      "Fileinfo.not.submitted":         "Die Dateiinformation konnte nicht angenommen werden.          \r\nBitte beachten Sie Folgendes:\r\n\r\n",
      "check.email":                    "- Mindestens eine eingegebene Emailadresse ist unguuml;ltig. Bitte uuml;berpruuml;fen Sie diese auf unguuml;ltige Zeichen\r\n",
      "check.form":                     "Das Formular konnte nicht angenommen werden, bitte beachten Sie Folgendes:\r\n\r\n",
      "check.send.email":               "The message could not be sent, please check the following:\r\n\r\n",
      "no.create.relationship":         "Die Relation konnte nicht aufgebaut werden, bitte uuml;berpruuml;fen Sie Folgendes:\r\n\r\n",
      "provider.needs.relationship":    "- Ein Provider muss ausgewauml;hlt werden um die Relation aufzubauen\r\n",
      "Name.required":                  "- Ein Name muss eingegeben werden\r\n",
      "AlertDate.before.today":         "Das Datum fuuml;r den Alarm liegt vor dem heutigen Datum\r\n",
      "Subject.required":               "- Ein Betreff muss angegeben werden\r\n",
      "relationshiptype.needs.selected":"- Ein Relationstyp muss ausgewauml;hlt werden.\r\n",
      "Filename.required":              "- Ein Dateiname muss angegeben werden\r\n",
      "specify.alert.date":             "- Bitte geben Sie ein Alarmdatum ein.\r\n",
      "specify.opportunity.type":       "- Bitten wauml;hlen Sie den Typ der Geschauml;ftsmouml;glichkeit an (Kunde oder Kontakt)\r\n",
      "check.ticket.issue.entered":     "- Uuml;berpruuml;fen Sie die Eingabe\r\n",
      "check.ticket.contact.entered":   "- Uuml;berpruuml;fen Sie, ob ein Kontakt ausgewauml;hlt wurde.\r\n",
      "title.required":                 "- Bitte geben Sie einen Titel ein\r\n",
      "description.required":           "- Bitte geben Sie eine Beschreibung ein\r\n",
      "startdate.required":             "- Bitte geben Sie ein Startdatum ein\r\n",
      "Documents.team.role":            "(Rolle)",
      "Documents.team.dept":            "(Abtl.)",
      "check.quote.email.entered":      "- Uuml;berpruuml;fen Sie die eingegebene Emailadresse\r\n",
      "check.quote.phone.entered":      "- Bitte geben Sie die eingegebene Telefonnummer\r\n",
      "check.quotes.notes.empty":       "Bitte geben Sie die Anmerkungen, die gespeichert werden soll, ein.",
      "check.quote.product.name":       "- Bitte geben Sie einen Produktnamen ein\r\n",
      "none.selected":                  "Nichts ausgewauml;hlt",
      "check.priceamount.blank":        "- The price can not be blank\r\n",
      "check.quote.extendedprice.blank":"Bitte geben Sie einen Preis ein\n",
      "check.quote.quantity.blank":     "Bitte geben Sie eine Menge ein\n",
      "check.quote.quantity.invalid":     "Die Menge ist keine guuml;ltige Zahl\n",
      "check.quote.extendedprice.invalid":"Der angegebene Preis ist kein guuml;ltiger Preis\n",
      "check.quote.estdeldate.invalid": "Das geschauml;tzte Lieferdatum ist kein guuml;ltiges Datum.\n",
      "check.quote.valid.entries":      "\nBitte geben Sie guuml;ltige Werte ein",
      "program.error.conditions":       "Programmfehler: Das Ort-Modul muss fuuml;r die Bedingungen definiert sein.",
      "program.error.remarks":          "Programmfehler: Das Ort-Modul muss fuuml;r die Anmerkungen definiert sein.",
      "check.product.filename":         "Bitte geben Sie die Datei an, die hochgeladen werden soll.",
      "image.thumbnail":                ": Miniaturbild",
      "image.small":                    ": Kleines Bild",
      "image.large":                    ": Gro&szlig;es Bild",
      "check.name":                     "- Bitte geben Sie den Namen an\r\n",
      "label.all":                      "Alle",
      "check.number.invalid":           "Bitte geben Sie eine guuml;ltige Zahl ein",
      "check.product.quantity":         "Bitte geben Sie mindestens eine Produktmenge ein",
      "check.product.categoryName":     "- Bitte geben Sie den Kategorienamen an\r\n",
      "check.product.code":             "- Bitte geben Sie den Produktcode ein\r\n",
      "check.product.category":         "- Bitte geben Sie die Kategorie ein\r\n",
      "verify.quote.newversion":        "Sind Sie sicher, Sie wollen eine neue Version dieses Angebotes erstellen?",
      "check.activity.folder.items":    "Das Aktivitauml;tenverzeichnis konnte nicht abgeschickt werden.\r\nBitte verifzieren Sie Folgendes:\r\n\r\n",
      "check.indentlevel.between":      "- Das Indent level muss zwischen 0 und ",
      "check.number.loefield":          "- Nur Zahlen sind im LOE Feld erlaubt\r\n",
      "user.cannotbechanged.reason":    "Diese Anwenderrolle kann nicht geauml;ndert werden.\r\nMouml;gliche Gruuml;nde:\r\n- Es gibt mindestens einen anderen Anwender mit der Projektleitungsrolle\r\n- Ihre Rolle muss das Auml;ndern von anderen Anwenderrollen erlauben.",
      "button.pleasewait":              "Bitte warten...",
      "check.subject":                  "- Bitte geben Sie einen Betreff ein\r\n",
      "check.intro":                    "- Bitte geben Sie einen Intro ein\r\n",
      "check.message":                  "Die Nachricht konnte nicht versandt werden.\r\nBitte verifzieren Sie Folgendes:\r\n\r\n",
      "check.message.required":         "- Bitte geben Sie eine Nachricht ein\r\n",
      "check.forum.name":               "- Bitte geben Sie ein Forum ein\r\n",
      "select.product":                 "- Wauml;hlen Sie bitte ein Produkt aus\r\n",
      "quote.notmodifiable":            "Das Angebot kann nicht geauml;ndert werden.\nBitte wauml;hlen Sie entweder Klonen oder Neu erstellen.",
      "check.ticket.resolution.atclose":"- Wenn Sie den Beleg schliesen wollen, muuml;ssen Sie die Auflouml;sung eintragen\r\n",
      "check.servicecontract.number":   "- Bitte geben Sie eine Servicevertragsnummer ein\r\n",
      "check.init.contract.date":       "- Bitte geben Sie ein Anfangsdatum fuuml;r den Vertrag ein\r\n",
      "check.response.time":            "- Bitte geben Sie eine Antwortszeit ein\r\n",
      "check.telephone.service":        "- Bitte geben Sie den Telefonservice ein\r\n",
      "check.onsite.service":           "- Bitte geben Sie den Onsite Service ein\r\n",
      "check.email.service":            "- Bitte geben Sie den Emailservice ein\r\n",
      "check.criteria":                 "- Bitte geben Sie die Kriterien ein\r\n",
      "confirm.delete.folder":          "Sind Sie sicher, Sie wollen dieses Verzeichnis und alle zugehouml;rigen eingegebenen Daten louml;schen?",
      "confirm.delete.group":           "Sind Sie sicher, Sie wollen diese Gruppe, alle Felder und alle zugehouml;rigen eingegebenen Daten louml;schen?",
      "confirm.delete.field":           "Sind Sie sicher, Sie wollen das Feld und alle zugehouml;rigen eingegebenen Daten louml;schen??",
      "caution.nothingtoremove":        "Nichts zu louml;schen",
      "caution.itemneedstobe.selected": "Zuerst muuml;ssen Sie ein Thema auswauml;hlen, um es louml;schen zu kouml;nnen",
      "caution.category.disabled":      "Die ausgewauml;hlte Kategorie ist schon deaktiviert.",
      "button.addR":                    "Hinzufuuml;gen >",
      "caution.category.exists":        "Die Kategorie mit dieser Beschreibung existiert bereits.",
      "caution.doublequotes.notallowed":"Doppelhochkommatas sind in der Beschreibung nicht erlaubt.",
      "caution.item.needed":            "Sie muuml;ssen ein Thema auswauml;hlen",
      "caution.singleitem.needed":      "A single item needs to be selected",
      "caution.category.enabled":       "Die ausgewauml;hlte Kategorie ist schon aktiviert",
      "button.updateR":                 "Aktualisieren >",
      "check.username":                 "- Bitte geben Sie einen Anwendernamen ein\r\n",
      "check.bothpasswords.match":      "- Uuml;berpruuml;fen Sie, ob beide Passworte richtig eingegeben wurden.\r\n",
      "check.role.selected":            "- Uuml;berpruuml;fen Sie, ob eine Rolle ausgewauml;hlt wurde.\r\n",
      "alert.noentriestoactivate":      "Keine Eintrauml;ge zum Aktivieren",
      "confirm.looseChanges":           "Sie werden alle Auml;nderungen des Entwurfs verlieren. Weiter?",
      "check.campaign.name":            "- Bitte geben Sie einen Kampagnennamen ein\r\n",
      "option.none":                    "--keine--",
      "check.group.name":               "- Bitte geben Sie einen Gruppennamen ein\r\n",
      "check.campaign.criteria":        "Die Kriterien konnten nicht verarbeitet werden, bitte uuml;berpruuml;fen Sie Folgendes:\r\n\r\n",
      "caution.selectfiletodownload":   "Bitte wauml;hlen Sie eine Datei zum Herunterladen aus",
      "caution.provideanswer.survey":   "Bitte geben Sie zu jeder Frage eine Antwort ein.\r\n\r\n",
      "label.activate":                 "Aktivieren",
      "caution.provideanswer.required": "Bitte geben Sie zu jeder Frage eine Antwort ein.\r\n\r\n",
      "check.reminder":                 "- Uuml;berpruuml;fen Sie, ob die Erinnerung korrekt eingegeben wurde.\r\n",
      "check.alertdate.beforetoday":    "Das Alarmdatum liegt vor dem heutigen Datum.\r\n",
      "check.street.address":           "- Bitte geben Sie einen Strassennamen ein\r\n",
      "check.city.name":                "- Bitte geben Sie einen Stadtnamen ein\r\n",
      "check.state.name":               "- Bitte geben Sie ein Bundesland ein\r\n",
      "check.country.name":             "- Bitte geben Sie ein Land ein\r\n",
      "confirm.delete.contact.address": "Sind Sie sicher Sie wollen die ausgewauml;hlte Adresse louml;schen?",
      "check.email.address":            "- Bitte geben Sie eine Emailadresse ein\r\n",
      "check.valid.email.address":      "- Bitte geben Sie eine guuml;ltige Emailadresse ein\r\n",
      "check.phonenumber":              "- Bitte geben Sie eine Telefonnummer ein\r\n",
      "check.valid.number":             "- Bitte geben Sie eine guuml;ltige Zahl ein\r\n",
      "alert.user.role.notchangable":   "Die Anwenderrolle kann nicht geauml;ndert werden.\r\nMouml;gliche Gruuml;nde:\r\n- Es gibt mindestens einen anderen Anwender mit einem Projekt mit Rolle Erstkontakt\r\n- Ihre Rolle muss das Auml;ndern von anderen Anwenderrollen erlauben.",
      "description.required.resubmit":  "Bitte geben Sie eine Beschreibung ein und senden die Information nochmal ab.",
      "select.user.toreassign":         "- Ein Anwender fuuml;r die erneute Zuweisung muss erst ausgewauml;hlt werden.",
      "alert.reassignments":            "Eine Wiederzuweisung konnte nicht durchgefuuml;hrt werden, bitte uuml;berpruuml;fen Sie Folgendes:\r\n\r\n",
      "select.hours":                   "- Bitte geben Sie Stunden ein\\r\n",
      "check.hours.invalid":            "- Bitte geben Sie eine guuml;ltige Zahl fuuml;r Stunden ein\r\n",
      "select.reason":                  "- Bitte geben Sie einen Grund ein\r\n",
      "check.user.twologins.one":       "Die Anwendung erlaubt jedem Anwender sich nur einmal anzumelden\n und es sieht so aus, als ob Sie bereits im System angemeldet sind von folgender Adresse aus: ",
      "check.user.twologins.two":       ".\n\n Diese Nachricht erscheint, wenn Sie sich zuvor nicht vom System abgemeldet haben\n und Sie sich nochmal anmelden wollen.\n\n Wauml;hlen Sie OK aus, um zum Login zu gelangen.\n Wauml;hlen Sie ABBRECHEN aus, um zum vorherigen Login Bildschirm zuruuml;ckzukommen.",
      "specify.contact":                "- Bitte wauml;hlen Sie einen Kontakt aus\r\n",
      "check.length.wholenumber":       "- Uuml;berpruuml;fen Sie ob die Lauml;nge eine ganze Zahl ist.\r\n",
      "check.single.description":       "- Nur eine Beschreibung ist erlaubt.\r\n",
      "check.module":                   "Bitte geben Sie ein Modul ein\\n",
      "check.targetdirectory":          "- Bitte tragen Sie ein Zielverzeichnis ein\r\n",
      "check.firstname":                "- Bitte tragen Sie den Vornamen ein\r\n",
      "check.lastname":                 "- Bitte tragen Sie den Nachnamen ein\r\n",
      "check.company":                  "- Bitte tragen Sie die Firma ein\r\n",
      "check.emailaddress":             "- Bitte tragen Sie die Emailadresse\r\n",
      "check.emailaddress.invalid":     "- Die Emailadresse ist unguuml;ltig. Uuml;berpruuml;fen Sie diese bitte auch unguuml;ltige Zeichen\r\n",
      "check.username":                 "- Bitte tragen Sie einen Anwendernamen ein\r\n",
      "select.language":                "Bitte wauml;hlen Sie eine Sprache aus",
      "check.profile":                  "- Bitte tragen Sie ein Profil ein\r\n",
      "check.organization":             "- Bitte tragen Sie die Organisation ein\r\n",
      "check.proxyhost":                "- Bitte tragen Sie einen Proxyserver ein, falls Sie das Feld fuuml;r Proxyserver angekreuzt haben.\r\n",
      "check.proxyport.number":         "- Der Proxyport ist eine Zahl\r\n",
      "check.upgradesystem":            "Sind Sie sicher dass Sie das System nun upgraden wollen?",
      "check.password":                 "- Bitte tragen Sie ein Passwort ein\r\n",
      "check.licensekey":               "Bitte tragen Sie die Lizenznummer ein",
      "check.registration.option":      "Bitte tragen Sie eine Registrierungsoption ein",
      "status.change.requirement":      " Der Status kann nur geauml;ndert werden vom Anwender, dem diese Aufgabe zugewiesen ist.",
      "cannot.delete.task.reason":      "Die ausgewauml;hlte Tauml;tigkeit kann nicht gelouml;scht, werden da Sie einem anderen Anwender zugewiesen wurde.",
      "check.descriptionofservice":     "- Bitte tragen Sie die Beschreibung des Service ein\r\n",
      "check.allitems.part.one":        "- Bitte uuml;berpruuml;fen Sie, ob alle Themen ",
      "check.allitems.part.two":        "in der Spalte eingetragen sind.\r\n",
      "check.issue.required":           "- Bitte tragen Sie ein Thema ein\r\n",
      "select.account.first":           "Zuerst muuml;ssen Sie ein Konto auswauml;hlen",
      "no.permission.addemployees":     "Sie haben nicht die Erlaubnis Angestellte hinzuzufuuml;gen",
      "no.permission.addcontacts":      "Sie haben nicht die Erlaubnis Kontakte hinzuzufuuml;gen",
      "label.anyone":                   "Jeder",
      "check.alertdate":                "- Ein Alarmdatum wird benouml;tigt, wenn die Verfolgung aktiviert ist\r\n",
      "check.followup.description":     "- Eine Beschreibung der Verfolgung ist notwendig, wenn die Verfolgung aktiviert ist\r\n",
      "alertdate.onlyif.followup":      "- Ein Alarmdatum wird nur benouml;tigt, wenn die Verfolgung aktiviert ist\r\n",
      "followupdesc.onlyif.followup":   "- Eine Beschreibung der Verfolgung ist nur notwendig, wenn die Verfolgung aktiviert ist\r\n",
      "select.onerecipient":            "- Bitte wauml;hlen Sie mindestens einen Empfauml;nger aus.\r\n",
      "check.subject":                  "- Geben Sie einen Betreff ein\r\n",
      "check.message":                  "- Geben Sie unten die Nachricht ein\r\n",
      "check.description":              "- Uuml;berpruuml;fen Sie, ob die Beschreibung eingegeben wurde.\r\n",
      "select.one.emailaddress":        "- Bitte wauml;hlen Sie zumindest eine Emailadresse aus\r\n",
      "specify.youremailaddress":       "- Bitte geben Sie Ihre Emailadresse an\r\n",
      "select.organization.and.contact":"Bitte wauml;hlen Sie eine Organisation und einen Kontakt aus",
      "select.organization.first":      "Wauml;hlen Sie zuerst eine Organisation aus",
      "confirm.delete.item":            "Sind Sie sicher Sie wollen dies louml;schen?",
      "quote.submitted.withoutaction":  "Das Angebot wurde angenommen ohne jede weitere Aktion.",
      "no.adjustment":                  "Keine Anpassung",
      "label.none":                     "Keine",
      "label.show":                     "Anzeigen",
      "label.hide":                     "Ausblenden",
      "label.any":                      "Jeder",
      "specify.contact":                "- Bitte geben Sie einen Kontakt an\r\n",
      "webcal.message":                 "Wenn Sie Ihren Concourse Suite Community Edition Kalender offline sehen wollen, benouml;tigen Sie einen iCalendar kompatible Anwendung, wie z.B. Mozilla Sunbird, Apple iCal, and andere.\r\n\r\nDiese Funktion ist eventuell auf Ihrem PC nicht verfuuml;gbar.",
      "cannot.assign":                  "Der Erstkontakt konnte nicht zugeordent werden, bitte uuml;berspringen Sie den aktuellen Erstkontakt",
      "confirm.delete.lead":            "Sind Sie sicher, dass Sie diesen Erstkontakt louml;schen wollen?",
      "check.postalcode":               "- Bitte geben Sie eine guuml;ltige Postleitzahl ein\r\n",
      "check.phone.ext":                "- Bitte geben Sie eine guuml;ltige Telefonnummer ein\r\n",
      "provide.optionvalue":            "You must provide a value for the new option",
      "label.entryalreadyexists":       "Entry already exists",
      "label.nothingtorename":          "Nothing to rename",
      "label.contact.lastName":          "Last Name",
      "label.contact.firstName":          "First Name",
      "label.account.name":              "Account Name",
      "label.account.number":          "Account Number",
      "label.account.sc.serialnumber":              "Serial Number",
      "label.account.sc.number":          "Service Contract Number",
      "select.torename":                "An item needs to be selected before it can be renamed",
      "required.oneiteminlist":         "You must have at least one item in this list.",
      "check.textmessage":              "- At least one entered text message address is invalid.  Make sure there are no invalid characters\r\n",
      "confirm.quotesubmit":            "Are you sure you want to submit this quote for review by the selected contact?",
      "confirm.quoteproductclone":      "Are you sure you want to clone the selected quote item?",
      "label.news.required":            "- Check that the news label is entered\r\n",
      "label.discussion.required":            "- Check that the discussion label is entered\r\n",
      "label.documents.required":            "- Check that the documents label is entered\r\n",
      "label.lists.required":            "- Check that the lists label is entered\r\n",
      "label.plan.required":            "- Check that the plan label is entered\r\n",
      "label.tickets.required":            "- Check that the tickets label is entered\r\n",
      "label.accounts.required":            "- Check that the accounts label is entered\r\n",
      "label.team.required":            "- Check that the team label is entered\r\n",
      "label.details.required":            "- Check that the details label is entered\r\n",
      "check.valid.input":              "Please enter a valid input",
      "check.valid.date":               "Please enter a valid date",
      "check.avoided.text":             "Please enter a valid input. Avoid [*^|]"
    }
  }
}