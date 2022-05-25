/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
export class Utils {

    static showSpinner(show: boolean) {
        if (show)
            document.getElementById("spinner")!.style.display = "block";
        else
            document.getElementById("spinner")!.style.display = "none";
    }

    static min(args: any[]) {
        if (!args || args.length === 0)
            return undefined
        let min = args[0]
        if (args.length <= 1)
            return min
        args.forEach((el: any) => {
            if (el < min)
                min = el
        })
        return min
    }

    static max(args: any[]) {
        if (!args || args.length === 0)
            return undefined
        let max = args[0]
        if (args.length <= 1)
            return max
        args.forEach((el: any) => {
            if (el > max)
                max = el
        })
        return max
    }

    static sort(args: any[]) {
        let temp = []
        let m
        const len = args.length
        for (let i = 0; i < len; i++) {
            m = Utils.min(args)
            temp.push(m)
            args.splice(args.indexOf(m), 1)
        }
        return temp
    }

    static download(data: any, fileName?: string) {
      //data = data.slice(0, data.size, "text/xml") //forzo text per forzare download e non apertura
      const url = URL.createObjectURL(data)
      const a = document.createElement('a')
      a.href = url
      a.download = fileName ?? ''
      a.click()
      setTimeout(function(){
        a.remove()
        URL.revokeObjectURL(url)
      }, 100)
    }

    static exportHTML(html: HTMLElement | string, mimeType: string = 'text/plain', fileName?: string) {
        const encodedData = encodeURI(typeof html === 'object' ? html.outerHTML : html)
        const a = document.createElement('a')
        a.href = `data:${mimeType}, ${encodedData}`
        a.download = fileName ?? 'export.txt'
        a.click()
        setTimeout(() => { a.remove() }, 0)
    }

    static exportTable(table: HTMLTableElement, options?: { filename?: string, predicate?: (el: HTMLTableRowElement) => boolean, skipColumns?: Array<number> }) {
        let t = document.createElement('table')
        let tHead, tBody, tFoot: HTMLTableSectionElement

        if (table.tHead) {
            tHead = t.createTHead()
            extractSection(table.tHead, tHead)
            let temp: any
            tHead.querySelectorAll('td').forEach(td => {
                temp = document.createElement('th')
                temp.innerText = td.innerText
                temp.style.border = '1px solid black'
                td.replaceWith(temp)
            })
        }
        if (table.tBodies[0]) {
            tBody = t.createTBody()
            extractSection(table.tBodies[0], tBody)
        }
        if (table.tFoot) {
            tFoot = t.createTFoot()
            extractSection(table.tFoot, tFoot)
        }

        let filename = options?.filename
        if(!filename) {
            const now = new Date()
            filename = 'report-'
            + (now.getDate() < 10 ? '0' + now.getDate() : now.getDate()) + '_'
            + (now.getMonth() < 10 ? '0' + (now.getMonth()+1) : (now.getMonth()+1)) + '_'
            + now.getFullYear() + '-'
            + now.getHours() + '_'
            + now.getMinutes()
            + '.xls'
        }

        this.exportHTML(t, 'application/vnd.ms-excel', filename)

        //helper
        function extractSection(source: HTMLTableSectionElement, target: HTMLTableSectionElement) {
            let tempRow: HTMLTableRowElement
            let tempCell: HTMLTableCellElement
            Array.from(source.rows).forEach(r => {
                if (options?.predicate && !options.predicate(r))
                    return
                tempRow = target.insertRow()
                if (r.cells) {
                    Array.from(r.cells).forEach(c => {
                        if (options?.skipColumns?.includes(c.cellIndex))
                            return
                        tempCell = tempRow.insertCell()
                        tempCell.innerText = c.innerText
                        tempCell.style.border = '1px solid black'
                    })
                }
            })
        }
    }

    static fromDateToLocaleDate(d: Date) {
        var dd = String(d.getDate()).padStart(2, '0');
        var mm = String(d.getMonth() + 1).padStart(2, '0');
        var yyyy = d.getFullYear();

        return yyyy + "-" + dd + "-" + mm;
    }

    static fromItalianDateString(s: string, separator: string = '-') {
        let i, j, d, m, y, hh, mm
        const radix = 10
        i = 0
        d = parseInt(s.substring(i, j = s.indexOf(separator, i)), radix); i = ++j;
        m = parseInt(s.substring(i, j = s.indexOf(separator, i)), radix); i = ++j;
        y = parseInt(s.substring(i), radix)
        /* old
        y = parseInt(s.substring(i, j = s.indexOf(' ', i)), radix); i = ++j;
        hh = parseInt(s.substring(i, j = s.indexOf(':', i)), radix); i = ++j;
        mm = parseInt(s.substring(i), radix)
        */
        hh = 0
        mm = 0
        return new Date(y, m - 1, d, hh, mm)
    }

    static fromTimeStringToLocaleData(s: string) {
        if (s == null)
            return null;
        let dateString = s.split(" ")[0]; //isolo data da orario
        let splitted = dateString.split("-");
        return `${splitted[2]}-${splitted[1]}-${splitted[0]}`;
    }

    static fromTimeStringToLocaleTime(s: string) {
        if (s == null)
            return null;
        let dateString = s.split(" "); //isolo data da orario
        let splitted = dateString[0].split("-");
        return `${splitted[2]}-${splitted[1]}-${splitted[0]} ${dateString[1]}`;
    }

    static fromISOTimeToLocaleTime(s: string) {
        if (s == null)
            return null;
        let dateString = s.split("T"); //isolo data da orario
        let splitted = dateString[0].split("-");
        dateString[1] = dateString[1].split(".")[0] //rimuovo millisecondi da orario;
        return `${splitted[2]}-${splitted[1]}-${splitted[0]} ${dateString[1]}`;
    }

    static fromISOTimeToLocaleDate(s: string) {
        if (s == null)
            return null;
        let dateString = s.split("T"); //isolo data da orario
        let splitted = dateString[0].split("-");
        return `${splitted[2]}-${splitted[1]}-${splitted[0]}`;
    }

    static getLocation(){
        return new Promise((res, rej) => {
            navigator.geolocation.getCurrentPosition(res, rej);
        });
    }
}