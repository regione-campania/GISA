import {TableExport} from 'tableexport';

export class Utils {



    static showSpinner(show: boolean, text?: string) {
        if (show){
            document.getElementById("spinner")!.style.display = "block";
            if(text)
                document.getElementById("spinner-label")!.innerHTML = text!.toString();
        }else{
            document.getElementById("spinner")!.style.display = "none";
            document.getElementById("spinner-label")!.innerHTML = "";
        }
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
        console.log("r");
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

    static tableExportXlsx(table: HTMLTableElement, options?: { filename?: string, predicate?: (el: HTMLTableRowElement) => boolean, skipColumns?: Array<number> }){
        Utils.showSpinner(true, 'Creazione report in corso');
        let t = document.createElement('table')
        t.classList.add("table")
        t.classList.add("table-bordered")
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
            //+ '.xls'
        }

        var istance = new TableExport(t, {
            headers: true,                              // (Boolean), display table headers (th or td elements) in the <thead>, (default: true)
            footers: true,                              // (Boolean), display table footers (th or td elements) in the <tfoot>, (default: false)
            formats: ['xlsx'],            // (String[]), filetype(s) for the export, (default: ['xlsx', 'csv', 'txt'])
            filename: filename,                             // (id, String), filename for the downloaded file, (default: 'id')
            bootstrap: true,                           // (Boolean), style buttons using bootstrap, (default: true)
            position: 'bottom',                         // (top, bottom), position of the caption element relative to table, (default: 'bottom')
            //ignoreRows: null,                           // (Number, Number[]), row indices to exclude from the exported file(s) (default: null)
            //ignoreCols: null,                           // (Number, Number[]), column indices to exclude from the exported file(s) (default: null)
            trimWhitespace: true,                       // (Boolean), remove all leading/trailing newlines, spaces, and tabs from cell text in the exported file(s) (default: false)
        });

        var exportData:any = istance.getExportData();
        var key = Object.keys(exportData)[0];
        istance.export2file(exportData[key].xlsx.data, exportData[key].xlsx.mimeType, exportData[key].xlsx.filename, '.xlsx');
        Utils.showSpinner(false);



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
                        tempCell.classList.add('tableexport-string')
                    })
                }
            })
        }
    }

    static trimData(obj: any) {
        if (obj && typeof obj === "object") {
            Object.keys(obj).map(key => {
              if (typeof obj[key] === "object") {
                this.trimData(obj[key]);
              } else if (typeof obj[key] === "string") {
                obj[key] = obj[key].trim();
              }
            });
          }
          return obj;
    }
}