/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
const template = `
<div id="container-filtro">
    <h4 id="intestazione-filtro">Filtro</h4>
    <form id="filtro">
        <div class="campo-filtro" id="campo-anno">
            <label for="anno">Anno</label>
            <select id="anno" name="anno">
                <option value="all" selected></option>
            </select>
        </div>
        <div class="campo-filtro" id="campo-richiesta">
            <label for="richiesta">Richiesta</label>
            <input id="richiesta" name="richiesta" placeholder="Cerca nel testo della richiesta">
        </div>
        <div class="campo-filtro">
            <label for="priorita">Priorità</label>
            <select id="priorita" name="priorita">
                <option value="all" selected></option>
                <option value="nd">NON DEFINITA</option>
                <option value="bassa">BASSA</option>
                <option value="media">MEDIA</option>
                <option value="alta">ALTA</option>
            </select>
        </div>
        <div class="campo-filtro">
            <label for="stato">Stato</label>
            <select id="stato" name="stato">
                <option value="all" selected></option>
                <option value="aperta">APERTA</option>
                <option value="consegnata">CONSEGNATA</option>
                <option value="standby">STANDBY</option>
                <option value="annullata">ANNULLATA</option>
            </select>
        </div>
        <div class="campo-filtro">
            <label for="moduli-presenti">Moduli presenti</label>
            <fieldset id="moduli-presenti">
                <dhv:permission name="devdoc-mod-a-view">
                    <input type="checkbox" name="mod-a">A
                </dhv:permission>
                <dhv:permission name="devdoc-mod-b-view">
                    <input type="checkbox" name="mod-b">B
                </dhv:permission>
                <dhv:permission name="devdoc-mod-ch-view">
                    <input type="checkbox" name="mod-ch">CH
                </dhv:permission>
                <dhv:permission name="devdoc-mod-c-view">
                    <input type="checkbox" name="mod-c">C
                </dhv:permission>
                <dhv:permission name="devdoc-mod-d-view">
                    <input type="checkbox" name="mod-d">D
                </dhv:permission>
            </fieldset>
        </div>
        <div class="campo-filtro">
            <label for="ordinamento">Ordina per</label>
            <select id="ordinamento" name="ordinamento">
                <option value="data-inserimento" selected>DATA INSERIMENTO</option>
                <option value="data-utima-modifica">DATA ULTIMA MODIFICA</option>
                <option value="id-richiesta">ID RICHIESTA</option>
            </select>
            <fieldset id="modalita-ordinamento" class="radio-group">
                <label for="piu-recente">Più recente</label>
                <input type="radio" id="piu-recente" name="modalita-ordinamento" value="desc" checked>
                <label for="meno-recente">Meno recente</label>
                <input type="radio" id="meno-recente" name="modalita-ordinamento" value="asc">
            </fieldset>
        </div>
        <div class="campo-filtro container-bottone">
            <button type="submit" class="bottone-filtro" id="applica-filtro" title="Applica">
                <i class="fas fa-search"></i>
                <span>CERCA</span>
            </button>
        </div>
        <div class="campo-filtro container-bottone">
            <button class="bottone-filtro" id="resetta-filtro" title="Resetta">
                <i class="fas fa-undo"></i>
                <span>RESET</span>
            </button>
        </div>
    </form>
</div>
`
const rigaNessunRisultato = {
    template: `<div id="riga-nessun-risultato">Nessun elemento corrisponde ai parametri di ricerca.</div>`,
    add: function() {
        let t = document.getElementById('riga-nessun-risultato')
        if(!t) table.insertAdjacentHTML('afterend', this.template)
    },
    remove: function() {
        let t = document.getElementById('riga-nessun-risultato')
        if(t) t.remove()
    }
}

//helpers
//extends strings capabilities, return true if this string contains str as substring or false otherwise
String.prototype.find = function (str) {
    if (typeof str !== 'string')
        return false
    if (this.toLowerCase().trim().includes(str.toLowerCase().trim()))
        return true
    return false
}

function extractDate(str) {
    let dateString = str.trim()
    let i = 0
    let d = dateString.substring(i, dateString.indexOf('/', i))
    i = dateString.indexOf('/', i) + 1
    let m = dateString.substring(i, dateString.indexOf('/', i))
    i = dateString.indexOf('/', i) + 1
    let y = dateString.substring(i, dateString.indexOf(' ', i))
    i = dateString.indexOf(' ', i) + 1
    let hours = dateString.substring(i, dateString.indexOf(':', i))
    i = dateString.indexOf(':', i) + 1
    let minutes = dateString.substring(i)
    return new Date(`${y}-${m}-${d}T${hours}:${minutes}:00`)
}

function getTable() {
    return context.getElementById('tabellaFlussi')
}

function restoreTable() {
    table.replaceWith(tableBackup.table)
    table = getTable()
    tableBackup.rows.forEach(row => table.append(row))
}

function loadYears() {
    let years = []
    let temp
    tableBackup.rows.forEach(row => {
        temp = row.cells[indiciColonne["anno"]].innerText
        if(isNaN(temp) || years.includes(temp))
            return
        years.push(temp)
    })
    years.sort((a,b) => parseInt(b, 10) - parseInt(a, 10))
    let selectField = document.getElementById('anno')
    years.forEach(y => {
        temp = `<option value="${y}">${y}</option>`
        selectField.insertAdjacentHTML('beforeend', temp)
    })
}

//filter logic
const context = document
let table = getTable()
const tableBackup = { table: table.cloneNode(), rows: Array.from(table.rows)}

let indiciColonne = {}
Array.from(table.rows[0].cells).forEach((th, index) => {
    indiciColonne[th.innerText.toLowerCase()] = index
})

function Filter() {
    table.insertAdjacentHTML('beforebegin', template)
    loadYears()
    this.container = context.getElementById('container-filtro')
    this.form = this.container.querySelector('#filtro')
    this.form.addEventListener('submit', event => {
        event.preventDefault()
        event.stopPropagation()
    })
    this.fields = {
        anno: this.form.elements.namedItem('anno'),
        richiesta: this.form.elements.namedItem('richiesta'),
        priorita: this.form.elements.namedItem('priorita'),
        stato: this.form.elements.namedItem('stato'),
        includiModA: this.form.elements.namedItem('mod-a'),
        includiModB: this.form.elements.namedItem('mod-b'),
        includiModCH: this.form.elements.namedItem('mod-ch'),
        includiModC: this.form.elements.namedItem('mod-c'),
        includiModD: this.form.elements.namedItem('mod-d'),
        ordinamento: this.form.elements.namedItem('ordinamento'),
        modOrdinamento: this.form.elements.namedItem('modalita-ordinamento')
    }
    this.buttons = {
        applyButton: this.form.elements.namedItem('applica-filtro'),
        resetButton: this.form.elements.namedItem('resetta-filtro')
    }
    this.buttons.applyButton.addEventListener('click', () => this.apply())
    this.buttons.resetButton.addEventListener('click', () => this.reset())
}

Filter.prototype.apply = function () {
    restoreTable()
    let areAllRowsHidden = true
    Array.from(table.rows).forEach((tr, index) => {
        if (index === 0) //salta la prima riga (header della tabella)
            return
        let isRowVisible = true
        const annoValue = this.fields.anno.value
        if (isRowVisible && annoValue !== 'all') {
            let tdAnno = tr.cells[indiciColonne["anno"]]
            if (!(tdAnno.innerText.find(annoValue)))
                isRowVisible = false
        }
        const richiestaValue = this.fields.richiesta.value.trim()
        if (isRowVisible && richiestaValue !== '') {
            let tdRichiesta = tr.cells[indiciColonne["richiesta"]]
            if (!(tdRichiesta.innerText.find(richiestaValue)))
                isRowVisible = false
        }
        const prioritaValue = this.fields.priorita.value
        if (isRowVisible && prioritaValue !== 'all') {
            let tdPriorita = tr.cells[indiciColonne["priorita'"]]
            switch (prioritaValue) {
                case 'nd': if (!(tdPriorita.innerText.find('non definita'))) isRowVisible = false; break;
                case 'bassa': if (!(tdPriorita.innerText.find('bassa'))) isRowVisible = false; break;
                case 'media': if (!(tdPriorita.innerText.find('media'))) isRowVisible = false; break;
                case 'alta': if (!(tdPriorita.innerText.find('alta'))) isRowVisible = false; break;
                default: break;
            }
        }
        const statoValue = this.fields.stato.value
        if (isRowVisible && statoValue !== 'all') {
            let tdStato = tr.cells[indiciColonne["stato"]]
            switch (statoValue) {
                case 'aperta': if (!(tdStato.innerText.find('aperta'))) isRowVisible = false; break;
                case 'consegnata': if (!(tdStato.innerText.find('consegnata'))) isRowVisible = false; break;
                case 'standby': if (!(tdStato.innerText.find('standby'))) isRowVisible = false; break;
                case 'annullata': if (!(tdStato.innerText.find('annullata'))) isRowVisible = false; break;
                default: break;
            }
        }
        const isModuloAChecked = this.fields.includiModA.checked
        if (isRowVisible && isModuloAChecked) {
            let tdModA = tr.cells[indiciColonne["modulo a"]]
            if (!(tdModA.innerText.find('visualizza')))
                isRowVisible = false
        }
        const isModuloBChecked = this.fields.includiModB.checked
        if (isRowVisible && isModuloBChecked) {
            let tdModB = tr.cells[indiciColonne["modulo b"]]
            if (!(tdModB.innerText.find('visualizza')))
                isRowVisible = false
        }
        const isModuloCHChecked = this.fields.includiModCH.checked
        if (isRowVisible && isModuloCHChecked) {
            let tdModCH = tr.cells[indiciColonne["modulo ch"]]
            if (!(tdModCH.innerText.find('visualizza')))
                isRowVisible = false
        }
        const isModuloCChecked = this.fields.includiModC.checked
        if (isRowVisible && isModuloCChecked) {
            let tdModC = tr.cells[indiciColonne["modulo c"]]
            if (!(tdModC.innerText.find('visualizza')))
                isRowVisible = false
        }
        const isModuloDChecked = this.fields.includiModD.checked
        if (isRowVisible && isModuloDChecked) {
            let tdModD = tr.cells[indiciColonne["modulo d"]]
            if (!(tdModD.innerText.find('visualizza')))
                isRowVisible = false
        }

        if (isRowVisible) {
            tr.hidden = false
            areAllRowsHidden = false
        }  
        else
            tr.hidden = true
    })
    //re-order
    if(areAllRowsHidden) {
        rigaNessunRisultato.add()
    }
    else {
        rigaNessunRisultato.remove()
        this.sortValues()
    }
    
}

Filter.prototype.reset = function () {
    this.form.reset()
    restoreTable()
    this.showAll()
    rigaNessunRisultato.remove()
}

Filter.prototype.showAll = function () {
    Array.from(table.rows).forEach(tr => {
        tr.hidden = false
    })
}

Filter.prototype.sortValues = function () {
    const sortField = this.fields.ordinamento.value
    const sortOrder = this.fields.modOrdinamento.value
    let tempTable = tableBackup.table.cloneNode()
    const tableHeader = tableBackup.rows[0]
    let rowsArray = Array.from(tableBackup.rows).slice(1)

    switch(sortField) {
        case 'data-inserimento': sortByDataInserimento(sortOrder); break;
        case 'data-utima-modifica': sortByDataUltimaModifica(sortOrder); break;
        case 'id-richiesta': sortByIdRichiesta(sortOrder); break;
        default: break;
    }

    function sortByDataInserimento(order) {
        if (order === 'desc') {
            restoreTable()
        }
        else {
            tempTable.appendChild(tableHeader)
            rowsArray.reverse().forEach(row => tempTable.appendChild(row))
            table.replaceWith(tempTable)
        }
    }

    function sortByDataUltimaModifica(order) {
        tempTable.appendChild(tableHeader)
        //sort by asc
        rowsArray.sort((a,b) => {
            let dataA = extractDate(a.cells[indiciColonne["ultima modifica"]].innerText)
            let dataB = extractDate(b.cells[indiciColonne["ultima modifica"]].innerText)
            return dataA - dataB
        })
        if(order === 'desc')
            rowsArray.reverse()
        rowsArray.forEach(row => tempTable.appendChild(row))
        table.replaceWith(tempTable)
    }

    function sortByIdRichiesta(order) {
        tempTable.appendChild(tableHeader)
        //sort by asc
        rowsArray.sort((a,b) => {
            let idA = parseInt(a.cells[indiciColonne["id"]].innerText)
            let idB = parseInt(b.cells[indiciColonne["id"]].innerText)
            return idA - idB
        })
        if(order === 'desc')
            rowsArray.reverse()
        rowsArray.forEach(row => tempTable.appendChild(row))
        table.replaceWith(tempTable)
    }
}

const filter = new Filter()