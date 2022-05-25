<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
session_start();
require_once("common/config.php");

if (!isset($_SESSION['id_asl'])) {
   	session_unset();     // unset $_SESSION variable for the run-time 
  	session_destroy();   // destroy session data in storage
	echo '<script type="text/javascript">alert("Sessione scaduta, rieseguire il login!")</script>';
	header("location: /");
	exit;	
}/*else{

	$_SESSION['LAST_ACTIVITY'] = time(); // update last activity time stamp
	$_SESSION["id_asl"] = $id_asl;
}*/


?>
<style>
	input[type=checkbox][disabled] {
		opacity: 0.3;
	}

	input[type=radio][disabled] {
		opacity: 0.3;
	}

	.upperLeftButton {
		background-image: url(resources/up.png);
		background-repeat: no-repeat;
		background-position: center left 0px;
		height: 20px;
		width: 20px;
		text-align: right;
	}

	.ag-theme-balham .ag-ltr .ag-cell {
		border-right: 1px solid #aaa;
	}

	.grid line {
		stroke: lightgrey;
		stroke-opacity: 0.7;
		shape-rendering: crispEdges;
		z-index: -1;
	}

	.grid path {
		stroke-width: 0;
	}

	div.tooltip {
		overflow: auto;
		position: absolute;
		text-align: center;
		/*width: 100px;					
	height: 40px;*/
		padding: 2px;
		id: tooltip;
		font: 15px Arial;
		background: lightsteelblue;
		border: 0px;
		border-radius: 8px;
		pointer-events: none;
	}

	html,
	body {
		margin: 0;
		padding: 0;
		height: 100%;
	}

	.ag-header-cell-label .ag-header-cell-text {
		white-space: normal !important;
	}

	.cell-bold {
		background-color: rgb(245, 247, 247);
		cursor: copy;
		color: rgba(0, 0, 0, 0.54);
	}


	.cell-center {
		text-align: center;
	}

	.cell-bold-pointer {
		background-color: rgb(245, 247, 247);
		cursor: pointer;
		font-weight: bold;
		text-align: right;
	}

	.test-container {
		height: 100%;
		display: flex;
		flex-direction: column;
	}

	.test-header {
		padding: 4px;
		font-family: Verdana, Geneva, Tahoma, sans-serif;
		font-size: 13px;
	}

	tree {
		overflow-y: auto;
		overflow-x: hidden;
		position: fixed;
		top: 20px;
		left: 780px;
		width: 500px;
		height: 280px;
		padding: 10px;
		opacity: 1;
		z-index: -999;
		background: white;
		border: 2px solid lightGrey;
		border-radius: 6px;
		opacity: 0;
		-moz-box-shadow: 0 0 25px #333333;
		-webkit-box-shadow: 0 0 25px #333333;
		box-shadow: 0 0 25px #333333;
	}

	legend {
		overflow-y: auto;
		overflow-x: hidden;
		position: fixed;
		top: 20px;
		left: 780px;
		width: 500px;
		height: 280px;
		padding: 10px;
		opacity: 1;
		z-index: -999;
		background: white;
		border: 2px solid lightGrey;
		border-radius: 6px;
		opacity: 0;
	}

	.right-div {
		display: flex;
		justify-content: flex-end;
	}

	.ag-header-group-cell-label {
		justify-content: center;
	}

	label {
		font-family: Arial;
		font-size: 14px;
	}
</style>
<html>

<head>

	<title>Reportistica avanzata</title>

	<link rel="shortcut icon" href="resources/icon.png" type="image/x-icon" />

	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />


	<script src="js/ag-grid-community.min.js"></script>
	<script src="js/jquery.min.js"></script>
	<script src="js/d3.v4.min.js"></script>
	<script src="js/costruisciQuery_new3.js?v6"></script>
	<script src="js/costruisciQuery_dettaglio2.js?v8"></script>
	<script src="js/customPinnedRowRenderer.js"></script>
	<script src="js/draw.js?3"></script>


	<script type="text/javascript" src="js/moment.min.js"></script>
	<script type="text/javascript" src="js/daterangepicker.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/daterangepicker.css" />

	<link rel="stylesheet" href="css/ag-grid.css">
	<link rel="stylesheet" href="css/ag-theme-balham.css">
	<link rel="stylesheet" href="css/loader.css">

</head>

<body>
	<div id="loader"></div>

	<button type="button" style="width: 150px; height: 25px;" id="seleziona">Seleziona Data</button> <label id="dim3"> </label>
	<br>
	<button type="button" style="width: 150px; height: 25px;" id="seleziona2">Seleziona Linea</button> <label id="dim4"> </label>
	<br>
	<label id="path1"></label>
	<br>
	<label id="path2"></label>
	<br>
	<select id="norme" onchange="query()">
		<option value="null">Tutte le norme</option>		
	</select>
	<hr>

	<button type="button" style="width: 150px; height: 25px;" id="dim2Up" class="upperLeftButton">Dim2Up</button>
	<button type="button" style="width: 150px; height: 25px;" id="dim1Up" class="upperLeftButton">Dim1Up</button>
	<button type="button" style="width: 150px; height: 25px;" id="inverti">Inverti righe/colonne</button>


	<select id="combo">
		<option value="strutture/piani" selected>Piano/Struttura</option>
		<option value="strutture/date">Data/Struttura</option>
		<option value="piani/date">Data/Piano</option>
		<!-- <option value="linee/piani">Linea/Piano</option>
		<option value="linee/strutture">Linea/Struttura</option>
		<option value="linee/date">Linea/Data</option> -->
	</select>

	<select id="tipoConteggio">
		<option value="conteggio">CU/Campioni eseguiti</option>
		<option value="ups">UPS</option>
		<option value="uba">UBA</option>
	</select>

	<input type="checkbox" value="eseguiti" id="eseguiti" checked> <label for="eseguiti">Eseguiti</label>
	<input type="checkbox" value="target" id="target"> <label for="programmati">Programmati</label>
	<input type="checkbox" value="perc" id="perc"> <label for="perc">Percentuale</label>

	<input type="checkbox" value="cumul" id="cumul" disabled> <label for="cumul">Cumulata</label>


	<div class="right-div">
		<button type="button" onclick="window.open('resources/legenda.html', '', 'width=800,height=700 resizable, toolbar=no,menubar=no,scrollbars=1, location')">Legenda</button>
		<button type="button" onClick="exportToExcel()">Excel</button>
		<button type="button" onClick="dettaglioToExcel()">Dettaglio</button>
		<button id="report" type="button" onclick="window.open('estrazioni/index.php', '', 'width=700,height=350 resizable, scrollbars=1, location')">Estrazioni</button>
		<button id="completa" type="button" onClick="exportRendicontazioneCompleta()" style="opacity: 1">Rendicontazione</button>
		<button type="button" onclick="apriAltreRend()">Altre rendicontazioni</button>
	</div>
	<div class="right-div">
		<div id="altreRend" style="display: none">
			<button id= "iuv" type="button" onClick="document.location.href = 'iuv/iuv.php'">IUV</button>
			<button id= "tse" type="button" onClick="document.location.href = 'tse/'">TSE</button>
		</div>
	</div>

	<div id="myGrid" style="width: 100%;" class="ag-theme-balham"></div>

	<div class="right-div">
		<input type="radio" name="radio" value="Eseguiti" checked> <label for="eseguitiPlot">Eseguiti</label><br>
		<input type="radio" name="radio" value="Percentuale" id="radioPerc"> <label for="percPlot">Percentuale</label> <br>
		<button type="button" style="width: 120px; height: 25px;" id="grafico">Nascondi grafico</button>
		<button type="button" style="width: 120px; height: 25px; white-space: nowrap; text-align: center;" id="legend">Nascondi legenda</button>
		<button type="button" style="width: 120px; height: 25px;" id="selectAll">Seleziona righe</button>
		<button type="button" style="width: 150px; height: 25px;" id="deselectAll">Deseleziona righe</button>
		<select id="graph">
			<option value="plot">Grafico</option>
			<option value="barre" selected>Barre</option>
			<option value="torta">Torta</option>

		</select>
	</div>

	<label id="path1"></label>
	<br>
	<label id="path2"></label>

	<div id="hidden_form_container" style="display:none;"></div>


	<script type="text/javascript" charset="utf-8">

		var anno_php = <?php echo $_CONFIG["ra_year"]?>;
		console.log("ANNO CONFIG:" + anno_php);

		$.ajax({
			type: "POST",
			url: "session_count.php",
			dataType: "text",
			async: true,
			success: function(risposta) {
				console.log("ajax sessioni: " + risposta);
				if (parseInt(risposta) >=2 ) { // 2 perchè una query in corso dell allinamento, seconda la qiery stessa
						//alert("Allineamento dati in corso! Potrebbero esserci disservizi");
						//throw "allineamento";
					//	window.location.href = "http://srv.gisacampania.it/gisa_nt/MyCFS.do?command=Home&Message=";
				}

			},
			error: function(risposta) {
				console.log("Errore ajax sessione");
			}
		})


		$.ajax({
			type: "POST",
			url: "whoami.php",
			dataType: "text",
			async: false,
			success: function(risposta) {
				if (risposta.includes("UFFICIALE")) {
					console = {};
					console.log = function() {};
					console.warn = function() {};
					console.error = function() {};
					console.info = function() {};
				}

			},
			error: function(risposta) {
				console.warn("Errore ambiente");
			}
		})

		$.ajax({
			type: "GET",
			url: "get_norme.php",
			dataType: "json",
			async: false,
			success: function(response) {
				var $select = $('#norme');
				response=response['norma'];
				console.log(response);
				response.forEach(function(norma){
					$select.append('<option value=' + norma['id_norma'] + '>' + norma['norma'] + '</option>'); // return empty
				})

			},
			error: function(risposta){
				console.warn("Errore norme");
			}
		})



		SESSION_ID_ASL = parseInt('<?php echo $_SESSION["id_asl"]; ?>');
		console.log("SESSION ID ASL: " + SESSION_ID_ASL);
		ID_ASL = null;
		if (SESSION_ID_ASL == -1) {
			SESSION_ID_ASL = 8;
			ID_ASL = 8;
		} else {
			ID_ASL = SESSION_ID_ASL - 200;
			d3.select("button#report").remove();
			d3.select("button#iuv").remove();
			d3.select("button#completa").remove();
			//strutture[4] = [ID_ASL];
		}
		console.log("ID ASL: " + ID_ASL);


		tree = d3.select('body').append('tree')
			.attr('class', 'tree')
			.attr('id', 'tree')
			.style("left", window.innerWidth - 750 + "px");

		tree2 = d3.select('body').append('tree')
			.attr('class', 'tree')
			.attr('id', 'tree2')
			.style("left", window.innerWidth - 650 + "px");

		$('tree#tree2').load("tree_linee.php");

		function apriAltreRend(){
			var content = document.getElementById("altreRend");
			if (content.style.display === "block") {
				content.style.display = "none";
			} else {
				content.style.display = "block";
			}
		}

		function exportToExcel() {
			var d1 = d3.select("#path1").text();
			var d2 = d3.select("#path2").text();
			var _d3 = d3.select("#dim3").text();
			var d4 = d3.select("#dim4").text();

			console.log('sheet.php?q=' + q)
			window.open('sheet.php?q=' + q + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4);
		}

		function exportRendicontazioneCompleta() {
			alert("Verranno scaricati più file, accertarsi che il browser accetti i pop-up da questo sito");
			var y = ANNO_GLOB;
			if(ANNO_GLOB == null)
				y = anno_php;
			console.log("sheet_rendicontazione_asl.php?anno="+y);
			d3.json("sheet_rendicontazione_asl.php?anno="+y, function(error, data) {
				if (error)
					throw error;
				console.log(data)

				
				var _d3 = "01-01-"+y+"/31-12-"+y;
				var d4 = "";
				var d1 = data.descr_asl;
				data.piani.forEach(function(d){
					var d2 = d.descr;
					var q = d.query.replace(/\\/g, '');
					var filename = "Rendicontazione_"+y+"_"+d2.replace("/", "_")+".xls";
					//console.log(q);
					window.open('sheet_rendicontazione_asl3.php?q=' + q + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4 + "&filename=" + filename);
				})
			})

			//console.log('sheet.php?q=' + q)
			//window.open('sheet.php?q=' + q + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4);
		}

		function dettaglioToExcel() {
			var d1 = d3.select("#path1").text();
			var d2 = d3.select("#path2").text();
			var _d3 = d3.select("#dim3").text();
			var d4 = d3.select("#dim4").text();

			//d1 = d2 = _d3 = d4 = "-";
			//console.log('sheet_dettaglio.php?q='+q)
			var qd = costruisciQuery_dettaglio();
			console.log(qd);


			var theForm, newInput1, newInput2, newInput3, newInput4, newInput5;
			// Start by creating a <form>
			theForm = document.createElement('form');
			theForm.action = 'sheet_dettaglio.php';
			theForm.method = 'post';
			// Next create the <input>s in the form and give them names and values
			newInput1 = document.createElement('input');
			newInput2 = document.createElement('input');
			newInput3 = document.createElement('input');
			newInput4 = document.createElement('input');
			newInput5 = document.createElement('input');

			newInput1.type = 'hidden';
			newInput1.name = 'q';
			newInput1.value = qd;
			theForm.appendChild(newInput1);

			newInput2.type = 'hidden';
			newInput2.name = 'd1';
			newInput2.value = d1;
			theForm.appendChild(newInput2);

			newInput3.type = 'hidden';
			newInput3.name = 'd2';
			newInput3.value = d2;
			theForm.appendChild(newInput3);

			newInput4.type = 'hidden';
			newInput4.name = 'd3';
			newInput4.value = _d3;
			theForm.appendChild(newInput4);

			newInput5.type = 'hidden';
			newInput5.name = 'd4';
			newInput5.value = d4;
			theForm.appendChild(newInput5);
			// ...and it to the DOM...
			document.getElementById('hidden_form_container').appendChild(theForm);
			// ...and submit it
			theForm.submit();

			//console.log('sheet_dettaglio.php?q=' + qd + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4);
			///////////////window.open('sheet_dettaglio.php?q=' + qd + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4);
			//window.open('sheet_dettaglio.php?d1=' + d1 + "&d2=" + d2 + "&d3=" + _d3 + "&d4=" + d4);

		}

		dataStart = null;
		dataEnd = null;
		globalTipoGraf = "Eseguiti";
		globalStileGraf = "barre";
		tipoConteggio_glob = "conteggio";
		_tipo = '';
		showLegend = 1;

		gridMaxHeight = 600;

		eseguitiFlag = true;
		targetFlag = false;
		percFlag = false;
		cumulFlag = false;

		year = anno_php;

		dim3 = d3.select("#dim3")
			.style("font-size", "19px")
			.style("font-family", "sans-serif")
			.style("white-space", "nowrap")
			.text(dataStart + "/" + dataEnd)

		dim4 = d3.select("#dim4")
			.style("font-size", "19px")
			.style("font-family", "sans-serif")
			.style("white-space", "nowrap")
			.text('Tutte le linee')

		div = d3.select("body").append("div")
			.attr("class", "tooltip")
			.attr("id", "tooltip")
			.style("opacity", 0)
			.style("z-index", 9999);

		//[ [LIVELLO]	[SELECT]	[ID]	[WHERE] ]  Query dimensioni

		strutture = [
			['ASL', 'UOC', 'UOS'],
			//['descr_struttura', 'descr_struttura', 'descr_struttura', 'descr_struttura'],
			['a.descrizione_breve', 'a.descrizione_breve', 'a.descrizione_breve', 'a.descrizione_breve'],
			//['id_struttura', 'id_struttura', 'id_struttura'], //as id_c
			['a.id', 'a.id', 'a.id', 'a.id'],
			['1=1', 'id_padre_uoc = ', 'id_padre_uos = '], //where
			[ID_ASL], //root id
			["Tutte le UO"]
		];

		piani = [
			['SEZIONI', 'TIPO', 'PIANO/ATTIVITA', 'INDICATORI'],
			//['descr_piano', 'descr_piano', 'descr_piano', 'descr_piano'],
			['p.descrizione', 'p.descrizione', "upper(p.alias||' '||p.descrizione)", "upper(p.alias||' '||p.descrizione)"],
			//['id_piano', 'id_piano', 'id_piano', 'id_piano'],
			['p.id', 'p.id', 'p.id', 'p.id'],
			['1=1', 'id_padre_piano_o_attivita = ', 'id_padre_piano = ', ' id_padre_indicatore = '],
			[-1], //root id
			["Tutti i piano/attivita"]

		];

		date = [
			['TRIMESTRI', 'MESI', 'GIORNI'],
			['data_inizio_controllo_q', 'data_inizio_controllo_m||\'-\'||data_inizio_controllo_m_text', 'data_inizio_controllo_d::text'], //as riga
			['data_inizio_controllo_q', 'data_inizio_controllo_m||\'-\'||data_inizio_controllo_m_text', 'data_inizio_controllo_d'], //as id_r
			['\'Trim \'::text || btrim(to_char(date_part(\'quarter\'::text,  data_inizio_controllo::timestamp without time zone)::integer, \'RN\'::text))', " date_part('month'::text, data_inizio_controllo::timestamp without time zone)::integer||\'-\'||to_char(to_timestamp(date_part('month'::text, data_inizio_controllo::timestamp without time zone)::text, 'MM'::text), 'TMMonth'::text)", ' data_inizio_controllo_m = '],
			//['data_inizio_controllo_q', 'data_inizio_controllo_m', 'data_inizio_controllo_d'], //as riga
			//['data_inizio_controllo_q', 'data_inizio_controllo_m', 'data_inizio_controllo_d'], //as id_r
			//['data_inizio_controllo_q', 'data_inizio_controllo_m||\'-\'||data_inizio_controllo_m_text', 'data_inizio_controllo_d'], //as id_r
			['1=1'], //root id
			[''],
			["'", "'", "'", "'"]
		];

		linee = [
			['MACROAREE', 'AGGREGAZIONI', 'ATTIVITA'],
			['vm.descrizione', 'vm.descrizione', 'vm.descrizione'],
			['vm.id', 'vm.id', 'vm.id'],
			[''],
			[-1], //root id
			["Tutte le linee"]
		]

		abilitaPicker();

		dim1 = piani; // prima dimensione colonne
		dim2 = strutture; //seconda dimensione righe

		dim3 = date;
		dim4 = linee; //quarta dimensione

		ind1 = 0;
		ind2 = 0;
		ind3 = 0;
		ind4 = 0;

		idPath1 = [];
		idPath2 = [];
		idPath4 = [];

		textPath2 = [dim2[5]];
		textPath1 = [dim1[5]];
		textPath4 = '';

		aggiornaPath();

		wDatePath = [];
		textDatePath = [year];

		var eGridDiv = document.querySelector('#myGrid');
		var gridOptions = null;

		id_c_new = -1;
		id_r_new = 8;

		ind_start2 = 0;
		id_r_new = ID_ASL;
		if (ID_ASL != 8) {
			ind2 = 1;
			ind_start2 = 1;
		}
		ind_start1 = 0;

		id_3_new = null;
		id_4_new = -1;

		w_date = null;

		id_p = null;
		id_s = null;
		id_l = null;

		freezeClic = false;
		treeOpen = false;
		firstTime = true;

		data_glob = null;
		cols = null;
		rows = null;
		q = null;
		id_norma = "null";
		function query() {
			freezeClic = true;
			d3.select("div#loader").style("display", "");

			colonna = dim1[1][ind1];
			id_c = dim1[2][ind1];

			riga = dim2[1][ind2];
			id_r = dim2[2][ind2];

			console.log("id_c = " + id_c_new);
			console.log("id_r = " + id_r_new);
			console.log("id_3 = " + id_3_new);
			console.log("id_4 = " + id_4_new);
			console.log("w_date = " + w_date);

			id_norma = d3.select("#norme").node().value; 
			console.log(id_norma);

			q = costruisciQuery_new3();
			console.log(q);

			var data = null;
			SESSION_ID = '<?php echo session_id() ?>';
			d3.json("query.php?q=" + q + "&session=" + SESSION_ID + "&id=" + ID_ASL, function(error, data) {
				console.log("Richiedo query per sessione " + SESSION_ID + " per id_asl " + ID_ASL);
				if (error)
					throw error;
				try {
					data = data["strutt"];
				} catch {
					data = [];
				}
				data_glob = data;
				console.log(data);
				if (gridOptions != null)
					gridOptions.api.destroy();
				drawTable(data);
			})
		};

		function drawTable(d) {

			document.getElementById("dim1Up").disabled = !(ind1 > ind_start1);
			document.getElementById("dim2Up").disabled = !(ind2 > ind_start2);

			var child = [];
			if (eseguitiFlag)
				child.push({
					headerName: "Eseguiti",
					field: "totaleriga",
					type: "numericColumn",
					width: 100,
					valueFormatter: formatNumber,
					pinned: true,
					cellClass: 'cell-bold-pointer'
				});
			if (targetFlag)
				child.push({
					headerName: "Programmati",
					field: "totaletarget",
					type: "numericColumn",
					width: 100,
					valueFormatter: formatNumber,
					pinned: true,
					cellClass: 'cell-bold-pointer'
				});
			if (percFlag)
				child.push({
					headerName: "",
					field: "mediaperc",
					type: "numericColumn",
					width: 70,
					valueFormatter: formatPerc,
					pinned: true,
					cellClass: 'cell-bold-pointer'
				});

			rowData = [];

			columnDefs = [{
				headerName: "",
				field: "righe",
				pinned: true,
				suppressMovable: true,
				width: 200,
				cellClass: 'cell-bold',
				tooltip: (params) => params.value,
				pinnedRowCellRenderer: 'customPinnedRowRenderer',
				pinnedRowCellRendererParams: {
					style: {
						'color': 'blue'
					}
				}
			}, {
				headerName: "TOTALE " + dim2[0][ind2].toUpperCase(),
				field: "totale",
				pinned: true,
				suppressMovable: true,
				cellClass: 'cell-bold-pointer',
				width: 100,
				valueFormatter: formatNumber,
				cellStyle: {
					'font-weight': 'bold',
					'text-align': 'right'
				},
				children: child
			}];

			cols = [];
			colsIds = [];
			for (i = 0; i < d.length; i++) {
				cols[i] = d[i].colonna;
				colsIds[i] = d[i].id_c
			}
			cols = cols.filter(onlyUnique);
			colsIds = colsIds.filter(onlyUnique);

			if (dim1[2][ind1] == 'data_inizio_controllo_d') {
				for (var i = 0; i < cols.length; i++) {
					cols[i] = (cols[i]).pad(2);
				}
			}

			var list = [];
			for (var j = 0; j < cols.length; j++) {
				list.push({
					'col': cols[j],
					'id': colsIds[j]
				});
			}
			if (dim1[2][ind1] == 'data_inizio_controllo_d') {
				console.log("ORDINOOOOOOO")
				list.sort(function(a, b) {
					return ((a.col < b.col) ? -1 : ((a.col == b.col) ? 0 : 1));
				});
			}
			for (var k = 0; k < list.length; k++) {
				cols[k] = list[k].col;
				colsIds[k] = list[k].id;
			}

			//console.log(cols);
			//console.log(colsIds);

			if (dim1[2][ind1] == 'data_inizio_controllo_d') {
				for (var i = 0; i < cols.length; i++) {
					if (cols[i].charAt(0) == "0")
						cols[i] = cols[i].slice(1);
				}
				console.log(cols)
			}
			/*else if(dim1[2][ind1]  == 'data_inizio_controllo_m||\'-\'||data_inizio_controllo_m_text' ){
			console.log("UNPAD");
			for(var i=0; i<cols.length; i++){
			cols[i] = cols[i].slice(2);
			}
			console.log(cols)
			}*/

			rows = [];
			for (i = 0; i < d.length; i++) {
				rows[i] = d[i].riga;
			}
			rows = rows.filter(onlyUnique);
			console.log(rows);

			var noCumul = $.extend(true, [], d); //deep copy d

			if (cumulFlag) {
				if (dim1 == date) {
					var cumDim = rows;
				} else if (dim2 == date) {
					var cumDim = cols;
				}
				for (var i = 0; i < cumDim.length; i++) {
					console.log("Calcolo cumulata per " + cumDim[i])

					//var a = [];
					var cumul = 0
					var cumulPerc = 0;
					for (var j = 0; j < d.length; j++) {
						if (cumDim[i] == d[j].riga || cumDim[i] == d[j].colonna) {
							d[j]['valore' + _tipo] = parseInt(d[j]['valore' + _tipo]) + parseInt(cumul);
							cumul = parseInt(d[j]['valore' + _tipo])

							d[j]['perc' + _tipo] = parseFloat(d[j]['perc' + _tipo]) + parseFloat(cumulPerc);
							cumulPerc = parseFloat(d[j]['perc' + _tipo])
						}
					}
				}

			}

			console.log(noCumul);

			totali = new Array(cols.length).fill(0);
			targ = new Array(cols.length).fill(0);
			percentuale = new Array(cols.length).fill(0);

			sommaTotali = 0;
			sommaTarget = 0;

			r = {};
			r["righe"] = "TOTALE " + dim1[0][ind1].toUpperCase();
			for (i = 0; i < cols.length; i++) {
				for (j = 0; j < noCumul.length; j++) {
					if (noCumul[j].colonna == cols[i]) {
						totali[i] += parseInt(noCumul[j]['valore' + _tipo]);
						targ[i] += parseInt(noCumul[j]['target' + _tipo]);
						percentuale[i] += parseFloat(noCumul[j]['perc' + _tipo]);
					}
				}
				sommaTotali += totali[i];
				sommaTarget += targ[i];
				r[cols[i] + "_ES"] = totali[i];
				//if (dim1 != date)
				r[cols[i] + "_TA"] = targ[i];
				//else
				//	r[cols[i]+"_TA"] = target[i]/cols.length;

				//r[cols[i] + "_PE"] = percentuale[i] / rows.length;
				if (targ[i] == 0)
					r[cols[i] + "_PE"] = 0;
				else
					r[cols[i] + "_PE"] = 100 / targ[i] * totali[i];

			}
			r["totaleriga"] = sommaTotali;
			if (dim1 == date)
				r["totaletarget"] = sommaTarget / cols.length;
			else if (dim2 == date)
				r["totaletarget"] = sommaTarget / rows.length;
			else
				r["totaletarget"] = sommaTarget;

			if (sommaTarget == 0)
				r["mediaperc"] = 0;
			else
				r["mediaperc"] = 100 / sommaTarget * sommaTotali;
			console.log(totali)
			//rowData.push(r);
			r_totali = r;
			console.log(r_totali)
			console.log(r)

			var totalW = (window.innerWidth);
			var colW = 120;
			var targetW = 0;
			var percW = 0;
			if (targetFlag)
				targetW = 100;
			if (percFlag)
				percW = 70;

			var offset = 50
			if (cols.length == 1)
				offset = 150
			if (cols.length == 2)
				offset = 100

			if ((cols.length * (colW + percW + targetW) + ((colW + percW + targetW)) + 250 < totalW))
				colW = ((totalW - (250 + percW + targetW)) / cols.length) - ((percW + targetW)) - offset;

			for (i = 0; i < cols.length; i++) {
				var child = [];
				if (eseguitiFlag)
					child.push({
						headerName: "Eseguiti",
						field: cols[i] + "_ES",
						type: "numericColumn",
						width: colW,
						headerTooltip: cols[i],
						colId: colsIds[i],
						valueFormatter: formatNumber
					});
				if (targetFlag)
					child.push({
						headerName: "Programmati",
						field: cols[i] + "_TA",
						type: "numericColumn",
						width: targetW,
						headerTooltip: cols[i],
						colId: colsIds[i],
						valueFormatter: formatNumber
					});
				if (percFlag)
					child.push({
						headerName: " ",
						field: cols[i] + "_PE",
						type: "numericColumn",
						width: percW,
						headerTooltip: cols[i],
						colId: colsIds[i],
						valueFormatter: formatPerc
					});

				columnDefs.push({
					headerName: cols[i],
					field: cols[i],
					suppressMovable: true,
					width: 100,
					headerTooltip: cols[i],
					colId: colsIds[i],
					cellStyle: {
						textAlign: 'center'
					},
					children: child
					/*[{headerName: "Eseguiti", field: cols[i]+"_ES", type: "numericColumn",width: 100, headerTooltip: cols[i], colId: colsIds[i], valueFormatter: formatNumber},{headerName: "Target", field: cols[i]+"_TA", type: "numericColumn",width: 100, headerTooltip: cols[i], colId: colsIds[i], valueFormatter: formatNumber},{headerName: "Perc", field: cols[i]+"_PE", type: "numericColumn",width: 75, headerTooltip: cols[i], colId: colsIds[i], valueFormatter: formatPerc}
					]*/
				})
			}
			console.log(columnDefs)

			if (dim2[2][ind2] == 'data_inizio_controllo_d') {
				for (var i = 0; i < rows.length; i++) {
					rows[i] = (rows[i]).pad(2);
				}
			}

			var list = [];
			for (var j = 0; j < rows.length; j++) {
				list.push({
					'col': rows[j]
				});
			}

			if (dim2[2][ind2] == 'data_inizio_controllo_d') {
				console.log("ORDINOOOOO")
				list.sort(function(a, b) {
					return ((a.col < b.col) ? -1 : ((a.col == b.col) ? 0 : 1));
				});
			}
			for (var k = 0; k < list.length; k++) {
				rows[k] = list[k].col;
			}

			if (dim2[2][ind2] == 'data_inizio_controllo_d') {
				for (var i = 0; i < rows.length; i++) {
					if (rows[i].charAt(0) == "0")
						rows[i] = rows[i].slice(1);
				}
				console.log(rows)
			}
			/*else if(dim2[2][ind2]  == 'data_inizio_controllo_m||\'-\'||data_inizio_controllo_m_text' ){
			console.log("UNPAD");
			for(var i=0; i<cols.length; i++){
			rows[i] = rows[i].slice(2);
			}
			console.log(rows)
			}*/

			data_glob = d;

			if (dim1 == linee || dim2 == linee) {
				d.map(a => a['target' + _tipo] = 0);
				d.map(a => a['perc' + _tipo] = 0);
			}

			if (globalStileGraf == 'plot')
				drawLines(rows, d, cols);
			else if (globalStileGraf == 'barre')
				drawBar(rows, d, cols);
			else if (globalStileGraf == 'torta')
				drawPie(rows, d, cols);

			for (i = 0; i < rows.length; i++) {
				r = {};
				tot = 0;
				totTarg = 0;
				r["righe"] = rows[i]
				for (j = 0; j < d.length; j++) {
					if (d[j].riga == rows[i]) {
						r[d[j].colonna + "_ES"] = d[j]['valore' + _tipo];
						r[d[j].colonna + "_TA"] = parseInt(d[j]['target' + _tipo]);
						r[d[j].colonna + "_PE"] = d[j]['perc' + _tipo];
						r["rowId"] = d[j].id_r;
						tot += parseInt(noCumul[j]['valore' + _tipo]);
						totTarg += parseInt(d[j]['target' + _tipo]);
					}
				}
				r["totaleriga"] = tot;
				if (dim1 == date)
					r["totaletarget"] = totTarg // / cols.length;
				else if (dim2 == date)
					r["totaletarget"] = totTarg // / rows.length;
				else
					r["totaletarget"] = totTarg

				if (totTarg == 0)
					r["mediaperc"] = 0;
				else
					r["mediaperc"] = 100 / totTarg * tot;
				rowData.push(r);
			}
			console.log(rowData)

			gridOptions = {
				defaultColDef: {
					sortable: true,
					resizable: true
				},
				columnDefs: columnDefs,
				rowData: rowData,
				headerHeight: 30,
				groupHeaderHeight: 75,
				domLayout: 'autoHeight',
				rowSelection: 'multiple',
				rowMultiSelectWithClick: true,
				onSelectionChanged: onSelectionChanged,
				onCellClicked(event) {
					console.log(event)
					if (event.rowIndex == 0 && event.column.colId != 'totaleriga' && ind1 < dim1[0].length - 1 && event.rowPinned == 'top' && event.colDef.field != "righe") {
						if (dim1 != date) {
							console.log("ID colonna = " + event.colDef.colId)
							//id_c_old = id_c_new;
							idPath1.push(id_c_new);
							textPath1.push(event.colDef.headerTooltip);
							id_c_new = event.colDef.colId;
							//idPath1.push(event.colDef.colId);
						} else {
							wDatePath.push(w_date);
							textDatePath.push(event.colDef.headerTooltip);
							apic = dim1[6][ind1] //eventuali apici
							if (dim1[3][ind1] != "1=1")
								w_date = dim1[3][ind1] + " = " + apic + event.colDef.colId + apic;
							else
								w_date = dim1[3][ind1];
						}
						ind1 += 1;
						query();
						d3.select("button#dim1Up")
							.text(dim1[0][ind1]);
					}
					if ((event.column.colId == 'totaleriga' || event.column.colId == 'totaletarget' || event.column.colId == 'mediaperc') && ind2 < dim2[0].length - 1 && event.rowPinned != 'top' && event.colDef.field != "righe") {
						if (dim2 != date) {
							console.log("ID riga = " + event.data.rowId)
							//id_r_old = id_r_new;
							idPath2.push(id_r_new);
							textPath2.push(event.data.righe);
							id_r_new = event.data.rowId;
						} else {
							wDatePath.push(w_date);
							textDatePath.push(event.data.righe);
							apic = dim2[6][ind2] //eventuali apici
							if (dim2[3][ind2] != "1=1")
								w_date = dim2[3][ind2] + " = " + apic + event.data.righe + apic;
							else
								w_date = dim2[3][ind2];
						}
						ind2 += 1;
						query();
						d3.select("button#dim2Up")
							.text(dim2[0][ind2]);
					}
					aggiornaPath();
				},
				components: {
					customPinnedRowRenderer: CustomPinnedRowRenderer
				},
				localeText: {
					noRowsToShow: 'Nessun CU associato alla query o gli indicatori selezionati non sono rendicontabili'
				}
			};

			gridOptions.getRowStyle = function(params) {
				if (params.node.rowPinned) {
					return {
						background: 'rgb(245, 247, 247)',
						'font-weight': 'bold',
						'cursor': 'pointer'
					};
				}
			}

			new agGrid.Grid(eGridDiv, gridOptions);

			topRow = createData(r_totali);
			gridOptions.api.setPinnedTopRowData(topRow);

			gridOptions.api.selectAll();

			gridOptions.api.setGridAutoHeight(true)

			//console.log(document.getElementById('myGrid').clientHeight)
			//console.log(gridOptions.api)

			if (document.getElementById('myGrid').clientHeight > gridMaxHeight) {
				gridOptions.api.setDomLayout(null);
				document.getElementById('myGrid').setAttribute("style", "height:" + gridMaxHeight + "");
			} else {
				//console.log($("[role=grid]").clientHeight)
				var newH = $("[role=grid]").clientHeight;
				document.getElementById('myGrid').setAttribute("style", "height:" + newH + "");

			}

			firstTime = false;
			d3.select("div#loader").style("display", "none");
			freezeClic = false;
		}

		function formatNumber(number) {
			if (!isNaN(number.value))
				return Math.floor(number.value).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
			return 0;
		}

		function formatPerc(number) {
			if (!isNaN(number.value))
				return number.value.toFixed(1) + "%";
			return 0;
		}

		function onlyUnique(value, index, self) {
			return self.indexOf(value) === index;

		}

		function onSelectionChanged(event) {
			if (!firstTime) {
				selectedRows = gridOptions.api.getSelectedRows().map(a => a.righe);
				d3.selectAll("#pie").remove();
				d3.selectAll("#plot").remove();
				d3.selectAll("#bars").remove();
				d3.selectAll("#container").remove();
				d3.selectAll("tooltip").remove();
				if (selectedRows.length > 0) {
					if (globalStileGraf == 'plot')
						drawLines(selectedRows, data_glob, cols);
					else if (globalStileGraf == 'barre')
						drawBar(selectedRows, data_glob, cols);
					else if (globalStileGraf == 'torta')
						drawPie(selectedRows, data_glob, cols);
				} else
					d3.select("svg#plot").attr("opacity", 0)
			}
		}

		d3.select("button#dim1Up")
			.text(dim1[0][ind1])
			.on("click", function(d) {
				if (dim1 != date) {
					id_c_new = idPath1.pop();
					textPath1.pop();
				} else {
					w_date = wDatePath.pop();
					textDatePath.pop();
				}
				ind1 -= 1;
				query();
				d3.select(this)
					.text(dim1[0][ind1]);
				aggiornaPath();
			})

		d3.select("button#dim2Up")
			.text(dim2[0][ind2])
			.on("click", function(d) {
				if (dim2 != date) {
					id_r_new = idPath2.pop();
					textPath2.pop();
				} else {
					w_date = wDatePath.pop();
					textDatePath.pop();
				}
				ind2 -= 1;
				query();
				d3.select(this)
					.text(dim2[0][ind2]);
				aggiornaPath();
			})

		d3.select("button#inverti")
			.on("click", function(d) {
				t = dim1;
				dim1 = dim2;
				dim2 = t;

				t = ind1
				ind1 = ind2;
				ind2 = t;

				t = ind_start1
				ind_start1 = ind_start2;
				ind_start2 = t;

				t = id_r_new;
				id_r_new = id_c_new;
				id_c_new = t;

				t = idPath1;
				idPath1 = idPath2;
				idPath2 = t;

				t = textPath1;
				textPath1 = textPath2;
				textPath2 = t;

				d3.select("button#dim1Up")
					.text(dim1[0][ind1])

				d3.select("button#dim2Up")
					.text(dim2[0][ind2])
				aggiornaPath();
				query();
			})

		d3.select("button#seleziona")
			.on("click", function(d) {
				if (treeOpen && (dim3 != date && dim4 != date)) { //chiudo
					d3.select("tree#tree").transition().duration(200).style("transform", "scale(0.5)").style("opacity", 0).style("z-index", -999)
					treeOpen = false;
				} else if (dim3 != date && dim4 != date) { //apro
					d3.select("tree#tree").transition().duration(200).style("transform", "scale(1)").style("opacity", 1).style("z-index", 999)
					treeOpen2 = true;
				}
			})

		treeOpen2 = false;
		d3.select("button#seleziona2")
			.on("click", function(d) {
				if (treeOpen2) { //chiudo
					d3.select("tree#tree2").transition().duration(200).style("transform", "scale(0.5)").style("opacity", 0).style("z-index", -999)
					treeOpen2 = false;
				} else { //apro
					d3.select("tree#tree2").transition().duration(200).style("transform", "scale(1)").style("opacity", 1).style("z-index", 999)
					treeOpen2 = true;
				}
			})

		d3.select("button#grafico")
			.on("click", function(d) {
				if (d3.select("svg#plot").attr("opacity") == 0) {
					d3.select("svg#plot").transition().duration(200).attr("opacity", 1)
					d3.select("button#grafico").text("Nascondi grafico");
				} else {
					d3.select("svg#plot").transition().duration(200).attr("opacity", 0)
					d3.select("button#grafico").text("Mostra grafico");
				}
			})

		d3.select("button#legend")
			.on("click", function(d) {
				if (d3.select("g#legend").attr("opacity") == 0) {
					d3.select("g#legend").transition().duration(200).attr("opacity", 1)
					d3.select("button#legend").text("Nascondi legenda");
					showLegend = 1;
				} else {
					d3.select("g#legend").transition().duration(200).attr("opacity", 0)
					d3.select("button#legend").text("Mostra legenda");
					showLegend = 0;
				}
			})

		d3.select("button#selectAll")
			.on("click", function(d) {
				gridOptions.api.selectAll();
			})

		d3.select("button#deselectAll")
			.on("click", function(d) {
				gridOptions.api.deselectAll();
			})

		d3.select("select#graph")
			.on("change", function(d) {
				globalStileGraf = d3.select(this)._groups[0][0].value;
				console.log(globalStileGraf);
				d3.selectAll("#pie").remove();
				d3.selectAll("#plot").remove();
				d3.selectAll("#bars").remove();
				d3.selectAll("tooltip").remove();
				if (globalStileGraf == 'plot')
					drawLines(rows, data_glob, cols);
				else if (globalStileGraf == 'barre')
					drawBar(rows, data_glob, cols);
				else if (globalStileGraf == 'torta')
					drawPie(rows, data_glob, cols);
			});

		d3.select("select#tipoConteggio")
			.on("change", function(d) {
				tipoConteggio_glob = d3.select(this)._groups[0][0].value;
				console.log(tipoConteggio_glob);

				_tipo = "";
				if (tipoConteggio_glob == 'ups')
					_tipo = '_ups';
				if (tipoConteggio_glob == 'uba')
					_tipo = '_uba';

				query();
			});

		d3.select("select#combo")
			.on("change", function(d) {
				idPath1 = [];
				idPath2 = [];
				textPath4 = '';
				id_c_new = 8;
				id_r_new = -1;
				id_l_new = -1;
				id_s = null;
				id_p = null;
				id_l = null;
				w_date = null;
				ind1 = 0;
				ind2 = 0;
				s = d3.select(this)._groups[0][0].value;
				s = s.split("/");
				dim1 = eval(s[1]);
				dim2 = eval(s[0]);
				aggiornaBottoni();

				console.log("dim1:" + dim1);
				console.log("dim2:" + dim2);
				d3.selectAll('svg').remove();
				id_c_new = dim1[4][0];
				id_r_new = dim2[4][0];
				id_3_new = dim3[4][0];
				id_4_new = dim4[4][0];


				if (dim1 == strutture) {
					id_c_new = ID_ASL;
					if (ID_ASL != 8) {
						ind_start1 = 1;
						ind_start2 = 0;
						ind1 = 1;
					}
				} else if (dim2 == strutture) {
					id_r_new = ID_ASL;
					if (ID_ASL != 8) {
						ind_start1 = 0;
						ind_start2 = 1;
						ind2 = 1;
					}
				} else if (dim3 == strutture) {
					id_3_new = ID_ASL;
					ind_start1 = 0;
					ind_start2 = 0;
				} else if (dim4 == strutture) {
					id_4_new = ID_ASL;
					ind_start1 = 0;
					ind_start2 = 0;
				}

				console.log("id_c = " + id_c_new);
				console.log("id_r = " + id_r_new);
				console.log("id_3 = " + id_3_new);
				console.log("id_4 = " + id_4_new);
				textPath2 = [dim2[5]];
				textPath1 = [dim1[5]];
				wDatePath = [year];
				d3.select("#dim3").text(dim3[5]);
				d3.select("#dim4").text(dim4[5]);
				aggiornaPath();

				if (dim1 == date || dim2 == date) {
					d3.select("input#cumul").property("disabled", false);
				} else {
					d3.select("input#cumul").property("disabled", true);
					d3.select("input#cumul").property("checked", false);
					cumulFlag = false;
				}

				if (dim1 == linee || dim2 == linee) {
					console.log("disattivo linee");
					d3.select("input#perc").property("disabled", true);
					d3.select("input#perc").property("checked", false);

					d3.select("input#target").property("disabled", true);
					d3.select("input#target").property("checked", false);

					percFlag = false;
					targetFlag = false;

					d3.selectAll("input[name='radio']")
						.property("checked", function(d) {
							if (this.value == "Eseguiti")
								return true;
							if (this.value == "Percentuale")
								return false;
						})

					document.getElementById("radioPerc").disabled = true;
					globalTipoGraf = 'Eseguiti';

				} else {
					d3.select("input#perc").property("disabled", false);
					d3.select("input#target").property("disabled", false);
					document.getElementById("radioPerc").disabled = false;
				}

				d3.select("button#dim1Up")
					.text(dim1[0][0]);
				d3.select("button#dim2Up")
					.text(dim2[0][0]);

				query();

			})

		function aggiornaPath() {
			d3.select('label#path1').text('');
			d3.select('label#path2').text('');
			var p1 = "";
			var p2 = "";
			if (dim1 != date) {
				for (var i = 0; i < textPath1.length; i++) {
					p1 = p1 + " > " + textPath1[i];
				}
			} else {
				for (var i = 0; i < textDatePath.length; i++) {
					p1 = p1 + " > " + textDatePath[i];
				}
			}

			if (dim2 != date) {
				for (var i = 0; i < textPath2.length; i++) {
					p2 = p2 + " > " + textPath2[i];
				}
			} else {
				for (var i = 0; i < textDatePath.length; i++) {
					p2 = p2 + " > " + textDatePath[i];
				}
			}
			d3.select('label#path1').text(p1);
			d3.select('label#path2').text(p2);

			/*var _path1 = document.getElementById('path1')
			var text = p1;
			text = text.split('>');
			_path1.textContent = '';
			for(var i = 0, l = text.length; i < l; i++) {
			var span = document.createElement('span');
			span.textContent = '>' + text[i];
			span.id = idPath1[i];
			_path1.appendChild(span);
			}

			_path1.addEventListener('click', function(e) {
			_path1.removeChild(e.target);
			}, false);


			var _path2 = document.getElementById('path2')
			text = _path2.textContent;
			_path2.textContent = '';
			for(var i = 0, l = text.length; i < l; i++) {
			var span = document.createElement('span');
			span.textContent = text.charAt(i);
			_path2.appendChild(span);
			}

			_path2.addEventListener('click', function(e) {
			_path2.removeChild(e.target);
			}, false);*/
		}

		query();

		function createData(row) {
			result = [];
			result.push(row)
			return result
		}

		document.addEventListener("click", freezeClicFn, true);

		function freezeClicFn(e) {
			console.log(e);
			if (freezeClic) {
				e.stopPropagation();
				e.preventDefault();
			} else {
				if (!document.getElementById('tree').contains(e.target)) {
					d3.select("tree#tree").transition().duration(200).style("transform", "scale(0.5)").style("opacity", 0).style("z-index", -999);
					treeOpen = false;
					d3.selectAll("div#tooltip").remove();
				}
				if (!document.getElementById('tree2').contains(e.target)) {
					d3.select("tree#tree2").transition().duration(200).style("transform", "scale(0.5)").style("opacity", 0).style("z-index", -999);
					treeOpen2 = false;
					d3.selectAll("div#tooltip").remove();
				}
			}
		}

		ANNO_GLOB = null;

		function abilitaPicker() {
			year = anno_php;

			let _ranges = Object();
			for(let y=2019; y<=year; y++){
				_ranges[y] = [moment(y + '-01-01'), moment(y + '-12-31')];
			}
			_ranges['Trim I'] = [moment(year + '-01-01'), moment(year + '-03-31')],
			_ranges['Trim II'] = [moment(year + '-04-01'), moment(year + '-06-30')],
			_ranges['Trim III'] = [moment(year + '-07-01'), moment(year + '-09-30')],
			_ranges['Trim IV'] = [moment(year + '-10-01'), moment(year + '-12-31')]
			
			ANNO_GLOB = year;

			date[5][0] = year;

			dataStart = year + '-01-01';
			dataEnd = year + '-12-31';

			d3.select("#dim3").text(dataStart + "/" + dataEnd);

			var sel2 = ''
				if (selezionaDate2 = true)
					sel2 = '2';
				$('button[id="seleziona"]').daterangepicker({
					opens: 'right',
					"startDate": "01/01/" + year,
					"endDate": "12/31/" + year,
					"alwaysShowCalendars": true,
					ranges: _ranges,
				"locale": {
					"format": "MM/DD/YYYY",
					"separator": " - ",
					"applyLabel": "Applica",
					"cancelLabel": "Annulla",
					"customRangeLabel": "Personalizzato",
					"daysOfWeek": [
						"Do",
						"Lu",
						"Ma",
						"Me",
						"Gi",
						"Ve",
						"Sa"
					],
					"monthNames": [
						"Gennaio",
						"Febbraio",
						"Marzo",
						"Aprile",
						"Maggio",
						"Giugno",
						"Luglio",
						"Agosto",
						"Settembre",
						"Ottobre",
						"Novembre",
						"Dicembre"
					],
					"firstDay": 1
				},
			}, function(start, end, label) {
				dataStart = start.format('YYYY-MM-DD');
				dataEnd = end.format('YYYY-MM-DD');

				if (start.format('YYYY') != end.format('YYYY')) {
					alert('Il range deve appartenere a allo stesso anno')
					//return;
				} else {
					w_date = "data_inizio_controllo between '" + dataStart + "' and '" + dataEnd + "'";
					d3.select("#dim3").text(dataStart + "/" + dataEnd);

					if (start.format('YYYY') != ANNO_GLOB) {
						console.log("Anno cambiato!");
						id_c_new = dim1[4][0];
						id_r_new = dim2[4][0];
						id_3_new = dim3[4][0];
						id_4_new = dim4[4][0];
						colonna = dim1[1][0];
						riga = dim2[1][0];
						d3.select("button#dim1Up")
							.text(dim1[0][ind1]);
						d3.select("button#dim2Up")
							.text(dim2[0][ind1]);
					}
					ANNO_GLOB = start.format('YYYY')
					query();
				}
			});
		}

		function disabilitaPicker() {
			$(".daterangepicker").remove();
			w_date = null;
		}

		String.prototype.pad = function(size) {
			var s = String(this);
			while (s.length < (size || 2)) {
				s = "0" + s;
			}
			return s;
		}

		//draw.js

		var sc = d3.scaleOrdinal(d3.schemeCategory10);
		colors = [sc(0), sc(1), sc(2), sc(3), sc(4), sc(5), sc(6), sc(7), sc(8), sc(9), "#63b598", "#ce7d78", "#ea9e70", "#a48a9e", "#c6e1e8", "#648177", "#0d5ac1", "#f205e6", "#1c0365", "#14a9ad", "#4ca2f9", "#a4e43f", "#d298e2", "#6119d0", "#d2737d", "#c0a43c", "#f2510e", "#651be6", "#79806e", "#61da5e", "#cd2f00", "#9348af", "#01ac53", "#c5a4fb", "#996635", "#b11573", "#4bb473", "#75d89e", "#2f3f94", "#2f7b99", "#da967d", "#34891f", "#b0d87b", "#ca4751", "#7e50a8", "#c4d647", "#e0eeb8", "#11dec1", "#289812", "#566ca0", "#ffdbe1", "#2f1179", "#935b6d", "#916988", "#513d98", "#aead3a", "#9e6d71", "#4b5bdc", "#0cd36d", "#250662", "#cb5bea", "#228916", "#ac3e1b", "#df514a", "#539397", "#880977", "#f697c1", "#ba96ce", "#679c9d", "#c6c42c", "#5d2c52", "#48b41b", "#e1cf3b", "#5be4f0", "#57c4d8", "#a4d17a", "#225b8", "#be608b", "#96b00c", "#088baf", "#f158bf", "#e145ba", "#ee91e3", "#05d371", "#5426e0", "#4834d0", "#802234", "#6749e8", "#0971f0", "#8fb413", "#b2b4f0", "#c3c89d", "#c9a941", "#41d158", "#fb21a3", "#51aed9", "#5bb32d", "#807fb", "#21538e", "#89d534", "#d36647", "#7fb411", "#0023b8", "#3b8c2a", "#986b53", "#f50422", "#983f7a", "#ea24a3", "#79352c", "#521250", "#c79ed2", "#d6dd92", "#e33e52", "#b2be57", "#fa06ec", "#1bb699", "#6b2e5f", "#64820f", "#1c271", "#21538e", "#89d534", "#d36647", "#7fb411", "#0023b8", "#3b8c2a", "#986b53", "#f50422", "#983f7a", "#ea24a3", "#79352c", "#521250", "#c79ed2", "#d6dd92", "#e33e52", "#b2be57", "#fa06ec", "#1bb699", "#6b2e5f", "#64820f", "#1c271", "#9cb64a", "#996c48", "#9ab9b7", "#06e052", "#e3a481", "#0eb621", "#fc458e", "#b2db15", "#aa226d", "#792ed8", "#73872a", "#520d3a", "#cefcb8", "#a5b3d9", "#7d1d85", "#c4fd57", "#f1ae16", "#8fe22a", "#ef6e3c", "#243eeb", "#1dc18", "#dd93fd", "#3f8473", "#e7dbce", "#421f79", "#7a3d93", "#635f6d", "#93f2d7", "#9b5c2a", "#15b9ee", "#0f5997", "#409188", "#911e20", "#1350ce", "#10e5b1", "#fff4d7", "#cb2582", "#ce00be", "#32d5d6", "#17232", "#608572", "#c79bc2", "#00f87c", "#77772a", "#6995ba", "#fc6b57", "#f07815", "#8fd883", "#060e27", "#96e591", "#21d52e", "#d00043", "#b47162", "#1ec227", "#4f0f6f", "#1d1d58", "#947002", "#bde052", "#e08c56", "#28fcfd", "#bb09b", "#36486a", "#d02e29", "#1ae6db", "#3e464c", "#a84a8f", "#911e7e", "#3f16d9", "#0f525f", "#ac7c0a", "#b4c086", "#c9d730", "#30cc49", "#3d6751", "#fb4c03", "#640fc1", "#62c03e", "#d3493a", "#88aa0b", "#406df9", "#615af0", "#4be47", "#2a3434", "#4a543f", "#79bca0", "#a8b8d4", "#00efd4", "#7ad236", "#7260d8", "#1deaa7", "#06f43a", "#823c59", "#e3d94c", "#dc1c06", "#f53b2a", "#b46238", "#2dfff6", "#a82b89", "#1a8011", "#436a9f", "#1a806a", "#4cf09d", "#c188a2", "#67eb4b", "#b308d3", "#fc7e41", "#af3101", "#ff065", "#71b1f4", "#a2f8a5", "#e23dd0", "#d3486d", "#00f7f9", "#474893", "#3cec35", "#1c65cb", "#5d1d0c", "#2d7d2a", "#ff3420", "#5cdd87", "#a259a4", "#e4ac44", "#1bede6", "#8798a4", "#d7790f", "#b2c24f", "#de73c2", "#d70a9c", "#25b67", "#88e9b8", "#c2b0e2", "#86e98f", "#ae90e2", "#1a806b", "#436a9e", "#0ec0ff", "#f812b3", "#b17fc9", "#8d6c2f", "#d3277a", "#2ca1ae", "#9685eb", "#8a96c6", "#dba2e6", "#76fc1b", "#608fa4", "#20f6ba", "#07d7f6", "#dce77a", "#77ecca"]

		function dragmove(d) {
			d3.select(this)
				.style("top", ((d3.event.sourceEvent.pageY) - this.offsetHeight / 2) + "px")
				.style("left", ((d3.event.sourceEvent.pageX) - this.offsetWidth / 2) + "px")
		}

		var drag = d3.drag()
			.on("drag", dragmove);

		d3.selectAll("#dim3").call(drag);

		d3.selectAll("input[name='radio']").on("change", function() {
			d3.selectAll("#pie").remove();
			d3.selectAll("#plot").remove();
			d3.selectAll("#bars").remove();
			d3.selectAll("tooltip").remove();
			globalTipoGraf = this.value;
			selectedRows = gridOptions.api.getSelectedRows().map(a => a.righe);
			if (selectedRows.length > 0) {
				if (globalStileGraf == 'plot')
					drawLines(rows, data_glob, cols);
				else if (globalStileGraf == 'barre')
					drawBar(rows, data_glob, cols);
				else if (globalStileGraf == 'torta')
					drawPie(rows, data_glob, cols);
			}
		});

		d3.selectAll("input[type='checkbox']").on("change", function() {
			console.log(this.value)
			if (this.value == 'eseguiti')
				eseguitiFlag = !eseguitiFlag;

			if (this.value == 'target')
				targetFlag = !targetFlag;

			if (this.value == 'perc')
				percFlag = !percFlag;

			if (this.value == 'cumul')
				cumulFlag = !cumulFlag;

			//drawTable(data_glob);
			query();
		});

		function apriMappa() {
			if (dim1 == piani)
				d1 = 'piani';
			else if (dim1 == strutture)
				d1 = 'strutture';
			else if (dim1 == date)
				d1 = 'date';
			else if (dim1 == linee)
				d1 = 'linee';

			if (dim2 == piani)
				d2 = 'piani';
			else if (dim2 == strutture)
				d2 = 'strutture';
			else if (dim2 == date)
				d2 = 'date';
			else if (dim2 == linee)
				d2 = 'linee';

			if (dim3 == piani)
				_d3 = 'piani';
			else if (dim3 == strutture)
				_d3 = 'strutture';
			else if (dim3 == date)
				_d3 = 'date';
			else if (dim3 == linee)
				_d3 = 'linee';

			if (dim4 == piani)
				d4 = 'piani';
			else if (dim4 == strutture)
				d4 = 'strutture';
			else if (dim4 == date)
				d4 = 'date';
			else if (dim4 == linee)
				d4 = 'linee';

			window.open("../map/index.html?id_3_new=" + id_3_new + "&id_4_new=" + id_4_new + "&id_c_new=" + id_c_new + "&id_r_new=" + id_r_new + "&w_date=" + w_date + "&dim1=" + d1 + "&dim2=" + d2 + "&dim3=" + _d3 + "&dim4=" + d4);
		}

		function aggiornaBottoni() {

			var dimensioni_glob = [date, piani, strutture, linee];
			var dimensioniSelezione = dimensioni_glob;
			console.log(dimensioniSelezione);

			tree.remove();
			tree2.remove();

			tree = d3.select('body').append('tree')
				.attr('class', 'tree')
				.attr('id', 'tree')
				.style("left", window.innerWidth - 750 + "px");

			tree2 = d3.select('body').append('tree')
				.attr('class', 'tree')
				.attr('id', 'tree2')
				.style("left", window.innerWidth - 650 + "px");

			dimensioniSelezione.splice(dimensioniSelezione.indexOf(dim1), 1);
			dimensioniSelezione.splice(dimensioniSelezione.indexOf(dim2), 1);
			disabilitaPicker();

			//console.log(dimensioniSelezione);

			for (var i = 0; i < dimensioniSelezione.length; i++) {

				if (dimensioniSelezione[i] == date) {
					if (i == 0) {
						d3.select("button#seleziona").text('Seleziona date')
						dim3 = date;
					} else {
						d3.select("button#seleziona2").text('Seleziona date')
						dim4 = date;
					}
					abilitaPicker();
				} else if (dimensioniSelezione[i] == piani) {
					if (i == 0) {
						d3.select("button#seleziona").text('Seleziona piano')
						dim3 = piani;
						$('#tree').load("tree_piani.php");
					} else {
						d3.select("button#seleziona2").text('Seleziona piano')
						dim4 = piani;
						$('#tree2').load("tree_piani.php");
					}
				} else if (dimensioniSelezione[i] == strutture) {
					if (i == 0) {
						d3.select("button#seleziona").text('Seleziona UO')
						dim3 = strutture;
						$('#tree').load("tree_asl.php");
					} else {
						d3.select("button#seleziona2").text('Seleziona UO')
						dim4 = strutture;
						$('#tree2').load("tree_asl.php");
					}
				} else if (dimensioniSelezione[i] == linee) {
					if (i == 0) {
						d3.select("button#seleziona").text('Seleziona linea')
						dim3 = linee;
						$('#tree').load("tree_linee.php");
					} else {
						d3.select("button#seleziona2").text('Seleziona linea')
						dim4 = linee;
						$('#tree2').load("tree_linee.php");
					}
				}
			}
			console.log("DIMENSIONI 3 E 4: ");
			console.log(dim3);
			console.log(dim4);

			treeOpen = false;
			treeOpen2 = false;
		}
	</script>
</body>

</html>