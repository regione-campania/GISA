<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
session_start();
$id_asl = $_SESSION['id_asl'];
$readonly = $_SESSION['readonly'];
$utente = $_SESSION['id_user'];
require_once("common/config.php");
//$id_asl = -1;

if ($utente == null)
	$utente = "sviluppo";

if ($readonly == null)
	$readonly = 0;

if ($id_asl == null)
	$id_asl = -999;
$id_asl = $id_asl - 200;


?>


<!DOCTYPE html>
<meta charset="utf-8">
<style>

	footer {
		position: fixed;
		text-align: center;
		padding: 3px;
		background-color: #e0e0e0;
		color: grey;
		bottom: 0;
		z-index: -99999;
		width: 100%;
	}
	/* Center the loader */
	#loader {
		position: absolute;
		left: 50%;
		top: 50%;
		z-index: 1;
		width: 150px;
		height: 150px;
		margin: -75px 0 0 -75px;
		border: 16px solid #f3f3f3;
		border-radius: 50%;
		border-top: 16px solid #3498db;
		width: 120px;
		height: 120px;
		-webkit-animation: spin 2s linear infinite;
		animation: spin 2s linear infinite;
	}

	@-webkit-keyframes spin {
		0% {
			-webkit-transform: rotate(0deg);
		}

		100% {
			-webkit-transform: rotate(360deg);
		}
	}

	@keyframes spin {
		0% {
			transform: rotate(0deg);
		}

		100% {
			transform: rotate(360deg);
		}
	}

	/* Add animation to "page content" */
	.animate-bottom {
		position: relative;
		-webkit-animation-name: animatebottom;
		-webkit-animation-duration: 1s;
		animation-name: animatebottom;
		animation-duration: 1s
	}

	@-webkit-keyframes animatebottom {
		from {
			bottom: -100px;
			opacity: 0
		}

		to {
			bottom: 0px;
			opacity: 1
		}
	}

	@keyframes animatebottom {
		from {
			bottom: -100px;
			opacity: 0
		}

		to {
			bottom: 0;
			opacity: 1
		}
	}


	input {
		text-align: right;
	}

	div.tooltip {
		overflow: auto;
		position: absolute;
		text-align: center;
		/*width: 100px;					
        height: 40px;*/
		padding: 2px;
		id: tooltip;
		font: 12px sans-serif;
		background: lightsteelblue;
		border: 0px;
		border-radius: 8px;
		pointer-events: none;
		z-index: 100
	}

	.bigText {
		height: 30px;
	}

	.node rect {
		cursor: pointer;
		fill: #fff;
		fill-opacity: 0.5;
		stroke: #3182bd;
		stroke-width: 1.5px;
	}

	.node text {
		font: 10px sans-serif;
		pointer-events: none;
	}

	input.input-box,
	textarea {
		background: #E8E8E8;
		pointer-events: none;
		font-size: 11px;
		-moz-user-select: none;
		-webkit-user-select: none;
		-ms-user-select: none;
		user-select: none;
		-o-user-select: none;
	}


	.link {
		fill: none;
		opacity: 0;
		stroke: #9ecae1;
		stroke-width: 1.5px;
	}

	valori1 {
		scrollbar-y-position: left;
		overflow-y: auto;
		overflow-x: hidden;
		height: 290px;


		position: fixed;
		top: 100px;
		left: 800px;
		width: 700px;
		--- height: auto;
		padding: 10px;
		background-color: white;
		opacity: 0;
	}

	valori1 p {
		opacity: 1;
		margin: 0;
		font-family: sans-serif;
		font-size: 15px;
		line-height: 16px;
	}

	selezione {
		position: fixed;
		z-index: 999;
		top: -50px;
		left: 800px;
		font-family: sans-serif;
		font-size: 20px;
		width: 730px;
		height: auto;
		padding: 10px;
		opacity: 1;
		--border: 2px solid #3182bd;
		--border-radius: 5px;
	}


	borsello {
		overflow-y: auto;
		overflow-x: hidden;
		position: fixed;
		top: 20px;
		left: 780px;
		width: 730px;
		height: 280px;
		padding: 10px;
		opacity: 1;
		--border: 2px solid #3182bd;
		--border-radius: 5px;
	}

	borsello p {
		opacity: 1;
		margin: 0;
		font-family: sans-serif;
		font-size: 15px;
		line-height: 16px;
		height: 18px;
	}

	body {
		overflow-x: hidden;
	}

	button.logout {
		z-index: 999;
		position: fixed;
		top: 6px;
		left: 1200px;
	}

	button.export {
		z-index: 999;
		position: fixed;
		top: 6px;
		left: 1120px;
	}

	button.manuale {
		z-index: 999;
		position: fixed;
		top: 6px;
		left: 1040px;
	}

	button.strutt {
		z-index: 999;
		position: absolute;
		top: 70px;
		left: 8px;
	}
</style>

<head>
	<title>Calcolo UPS-UBA</title>
</head>

<body>
	<script src="https://d3js.org/d3.v4.min.js"></script>
	<script src="js/sidebar.js"></script>

	<link rel="stylesheet" type="text/css" href="css/sidebar.css" />

	<span style="font-size:20px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>
	<div id="mySidenav" class="sidenav">
		<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		<a id="matrixRef" href="tree.php">Matrix</a>
		<a id="modRef" href="dpat/?1">Modello 4</a>
	</div>

	<select id="anno">
		<!--<option id="2019" value="2019"> 2019 </option>
<option id="2020" value="2020"> 2020 </option>-->
	</select>

	<footer>
      <p>Versione del codice sorgente reperibile nel repository per riuso al link:<br>
      <a href="https://github.com/gisa-riuso-temp/matrix_cmd">https://github.com/gisa-riuso-temp/matrix_cmd</a></p>
    </footer>

	<script>
		var anno_php = <?php echo $_CONFIG["matrix_year"] ?>;
		//alert(anno_php);
		console.log("ANNO CONFIG:" + anno_php);

		for (let y = 2019; y <= anno_php; y++) {
			d3.select("select#anno").append("option").attr("id", y.toString()).attr("value", y.toString()).text(y);
		}

		ANNO_GLOB = null;

		d3.select("#anno").on("change", function(d) {
			ANNO_GLOB = this.value;
			var id = getUrlParam("id_struttura", null);
			if (id == null)
				window.location.href = "tree.php?anno=" + ANNO_GLOB;
			else
				window.location.href = "tree.php?id=" + id + "anno=" + ANNO_GLOB;
		})

		function getUrlVars() {
			var vars = {};
			var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
				vars[key] = value;
			});
			return vars;
		}

		function getUrlParam(parameter, defaultvalue) {
			var urlparameter = defaultvalue;
			if (window.location.href.indexOf(parameter) > -1) {
				urlparameter = getUrlVars()[parameter];
			}
			return urlparameter;
		}

		ANNO_GLOB = getUrlParam("anno", anno_php);

		d3.select("#anno").property("value", ANNO_GLOB);

		console.log("ANNO:" + ANNO_GLOB);

		//var id_stuttura_glob = getUrlParam("id", 8);
		SESSION_ID_ASL = parseInt('<?php echo $id_asl; ?>');
		console.log("SESSION ID ASL: " + SESSION_ID_ASL);

		//window.location.href = 'manutenzione.html';
		/*
		var ss = SESSION_ID_ASL+200;
		if(ss != -1)
			window.location.href = '/dpat/?id_asl='+ss;
		else 
			window.location.href = '/dpat/?id_asl=null';
		*/
		if (SESSION_ID_ASL != -201)
			document.getElementById("modRef").href = "/dpat/?id_asl=" + (200 + parseInt(SESSION_ID_ASL));


		//if(window.location.href.indexOf("131.1.255.94") > -1) 
		//  SESSION_ID_ASL = -201;


		if (SESSION_ID_ASL == -1199) {
			alert("SESSIONE SCADUTA! RIESEGUIRE LOGIN")
			//throw new Error("SESSIONE SCADUTA! RIESEGUIRE LOGIN!");
		}

		var id_stuttura_glob = null;
		if (SESSION_ID_ASL != -1199) {
			id_stuttura_glob = getUrlParam("id", SESSION_ID_ASL);
		}

		if (id_stuttura_glob == -201) {
			id_stuttura_glob = 8;
		}


		console.log("ID STRUTTURA:  " + id_stuttura_glob);

		var read_only = <?php echo $readonly; ?>;
		console.log("READONLY: " + read_only);

		var utente = '<?php echo $utente; ?>';


		if (read_only == 1)
			read_only = true;
		else
			read_only = false;

		//read_only = false;

		console.log("READONLY: " + read_only);

		var once = true;
		var once_text = true;
		var once_rect = true;
		var locked = false;


		//id_stuttura_glob = 576;
		data_strutt_glob = null;
		data_piani_glob = null;
		f_uba_glob = null;
		f_ups_glob = null;
		title_glob = null;
		livello_glob = null;

		var valori, targets, ups, uba;

		var data_n = [];
		var data_r;

		var id_piano_glob = null;
		var stutture_length_glob = null;
		var target_glob = null;
		var clicked = null;
		var old_clicked;
		var pianiRect;


		var margin = {
				top: 40,
				right: 20,
				bottom: 30,
				left: 0
			},
			width = 800,
			barHeight = 20,
			barWidth = (width - margin.left - margin.right) * 0.58
		tagetNodeW = 48;

		var i = 0,
			duration = 400,
			root,
			node,
			nodeEnter;

		var diagonal = d3.linkHorizontal()
			.x(function(d) {
				return d.y;
			})
			.y(function(d) {
				return d.x;
			});

		var ambiente = d3.select("body").append("div")
			.style("font-size", "10px")
			.style("font-family", "sans-serif")
			.style("white-space", "nowrap");

		getAmbiente();


		var ASL = d3.select("body").append("div")
			.style("font-size", "20px")
			.style("font-family", "sans-serif")
			.style("white-space", "nowrap")


		var logout = d3.select("body")
			.append("button")
			.attr("class", "logout")
			.text("Logout")
			.on("click", function(d) {
				window.location = "logout.php";
			});

		var manuale = d3.select("body")
			.append("button")
			.attr("class", "manuale")
			.text("Manuale")
			.on("click", function(d) {
				window.open("manuale.php");
			});

		var exp = d3.select("body")
			.append("button")
			.attr("class", "export")
			.text("Esporta")
			.on("click", function(d) {
				console.log("sheet.php?id=" + id_stuttura_glob + "&strutt=" + title_glob.replace(/ /g, "_") + "&anno=" + ANNO_GLOB);
				window.open("sheet.php?id=" + id_stuttura_glob + "&strutt=" + title_glob.replace(/ /g, "_") + "&anno=" + ANNO_GLOB);
			});

		var top_strutt = d3.select("body")
			.append("button")
			.attr("class", "strutt")
			.text("Struttura superiore")
			.on("click", function(d) {
				window.open("tree.php?id=" + parent_glob + "&anno=" + ANNO_GLOB);
			});


		var svg = d3.select("body").append("svg")
			.attr("width", width) // + margin.left + margin.right)
			.append("g")
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		d3.select("#borsello").select("div#cons").attr("transform", "translate(30, 30)");

		var div = d3.select("body").append("div")
			.attr("class", "tooltip")
			.style("opacity", 0);

		console.log("struttura_parent_json.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB);
		d3.json("struttura_parent_json.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB, function(error, data) {
			if (error) throw error;
			data = data["strutt"];
			console.log(data);
			dpar = " - DPAR";
			if (data[0].descr == 'Regione Campania')
				data[0].descr = data[0].descr + dpar;
			ASL.text(data[0].descr);
			title_glob = data[0].descr;
			parent_glob = data[0].parent;
			livello_glob = data[0].livello;
			document.title = document.title + " - " + data[0].descr;
			if (data[0].livello == 0 && SESSION_ID_ASL == -201) {
				top_strutt.remove();
			} else if (data[0].livello == 1 && SESSION_ID_ASL != -201) {
				top_strutt.remove();
			} else if (data[0].livello == 2) {
				d3.selectAll("#b1").remove();
			}
		});

		console.log("tree_json_2019.php?&id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB);
		d3.json("tree_json_2019.php?&id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB, function(error, data) {
			if (error) throw error;
			//_data = data[0].get_struttura_piani_tree;
			//_data = _data.substring(1, _data.length-1);
			//_data = '{ "name": "flare", "children": [ ' + _data + '] }'
			//console.log(JSON.parse(data))
			//console.log(data);
			_json = JSON.parse(data);
			data_piani_glob = _json;
			root = d3.hierarchy(_json);
			root.x0 = 0;
			root.y0 = 0;

			console.log(root)
			nodes = root.descendants();
			console.log("nodi")
			console.log(nodes);

			for (i = 1; i < nodes.length; i++) {
				nodes[i]._children = nodes[i].children;
				nodes[i].children = null;
			}
			update(root);
			updateTree();
		});

		function drawStrutture() {
			console.log("strutture_json_2019.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB);
			d3.json("strutture_json_2019.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB, function(error, data) {
				if (error) throw error;
				data_strutt_glob = data["strutt"];
				console.log(data_strutt_glob)
				updateStrutture(data["strutt"]);
			});
		}
		drawStrutture();

		function drawBorsellino() {
			console.log("borsellino_json_2019.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB);

			d3.json("borsellino_json_2019.php?id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB, function(error, data) {
				if (error) throw error;
				data_borsellino_glob = data["borsellino"];
				console.log(data_borsellino_glob)
				updateBorsellino(data_borsellino_glob);
			});
		}
		drawBorsellino();

		function updateTree() {
			d3.json("newvalues_json_2019.php?&id_struttura=" + id_stuttura_glob + "&anno=" + ANNO_GLOB, function(error, data) {
				if (error) throw error;
				data_n = data["piano"];
				data_piani_glob = data_n;
				console.log(data_n);
				//setTimeout(function(d){newValues()}, 2000)
				//d3.select("div#loader").style("display", "none");
				newValues()
			})

		}

		offsetRectPerTesto = 25

		function update(source) {
			// Compute the flattened node list.

			// console.log(data_n.length)
			nodes = root.descendants();
			/*if (data_n.length){
				
				nodes= data_n;
				root = data_r;
				}*/
			//console.log("nodi")
			//console.log(nodes)
			//console.log(data_n)

			var height = Math.max(500, nodes.length * barHeight + margin.top + margin.bottom);

			d3.select("svg").transition()
				.duration(duration)
				.attr("height", height);

			d3.select(self.frameElement).transition()
				.duration(duration)
				.style("height", height + "px");

			// Compute the "layout". TODO https://github.com/d3/d3-hierarchy/issues/67
			var index = -1;
			root.eachBefore(function(n) {
				n.x = ++index * barHeight;
				n.y = n.depth * 10;
			});

			if (once_text) {

				console.log("LIVELLO " + livello_glob);
				var parent = '';
				var child = 'ASL';
				if (livello_glob == 1) {
					parent = "DPAR";
					child = "ASL";
				} else if (livello_glob == 2) {
					parent = "UOC";
					child = "UOS";
				}

				var prog = "N.campioni\ne\nattività\nprogrammati\n" + parent;
				var distr = "N.campioni\ne\nattività\ndistribuiti\n" + child;
				var prog_ups = "UPS per\ncampioni\ne attività\nprogrammati\n" + parent;
				var prog_uba = "UBA per\ncampioni\ne attività\nprogrammati\n" + parent;
				var distr_ups = "UPS per\ncampioni\ne attività\ndistributi\n" + child;
				var distr_uba = "UBA per\ncampioni\ne attività\ndistribuiti\n" + child;

				if (livello_glob == 0) {
					prog = "";
					prog_ups = "";
					prog_uba = "";
					distr = "N.campioni\ne\nattività\nprogrammati\nDPAR";
					distr_ups = "UPS per\ncampioni\ne attività\nprogrammati\nDPAR";
					distr_uba = "UBA per\ncampioni\ne attività\nprogrammati\nDPAR";
				}
				tar_val = svg.append("text")
					.text(prog)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + (tagetNodeW / 2))
					.attr("y", -30);

				val_lab = svg.append("text")
					.text(distr)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + tagetNodeW * 3 + (tagetNodeW / 2))
					.attr("y", -30);

				/*lab_ups = svg.append("text")
					.text("UPS")
					.style("font-family", "sans-serif")
					.style("font-size", "11px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.attr("x", barWidth + tagetNodeW * 2 + (tagetNodeW / 2))
					.attr("y", -20);

				lab_uba = svg.append("text")
					.text("UBA")
					.style("font-family", "sans-serif")
					.style("font-size", "11px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.attr("x", barWidth + tagetNodeW * 3 + (tagetNodeW / 2))
					.attr("y", -20);*/

				lab_ups = svg.append("text")
					.text(distr_ups)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + tagetNodeW * 4 + (tagetNodeW / 2))
					.attr("y", -30);

				lab_uba = svg.append("text")
					.text(distr_uba)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + tagetNodeW * 5 + (tagetNodeW / 2))
					.attr("y", -30);


				/*lab_ups_target = svg.append("text")
					.text("UPS")
					.style("font-family", "sans-serif")
					.style("font-size", "11px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.attr("x", barWidth + tagetNodeW * 4 + (tagetNodeW / 2))
					.attr("y", -20);

				lab_uba_target = svg.append("text")
					.text("UBA")
					.style("font-family", "sans-serif")
					.style("font-size", "11px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.attr("x", barWidth + tagetNodeW * 5 + (tagetNodeW / 2))
					.attr("y", -20);*/

				lab_ups_target = svg.append("text")
					.text(prog_ups)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + tagetNodeW + (tagetNodeW / 2))
					.attr("y", -30);

				lab_uba_target = svg.append("text")
					.text(prog_uba)
					.style("font-family", "sans-serif")
					.style("font-size", "8px")
					.style("font-weight", "bold")
					.style("text-anchor", "middle")
					.style("white-space", "pre-wrap")
					.style("font-stretch", "semi-condensed")
					.attr("x", barWidth + tagetNodeW * 2 + (tagetNodeW / 2))
					.attr("y", -30);

				once_text = false;
			}

			// Update the nodes�
			node = svg.selectAll(".node")
				.data(nodes, function(d) {
					return d.id /*|| (d.id = ++i)*/ ;
				});



			nodeEnter = node.enter().append("g")
				.attr("class", "node")
				.attr("transform", function(d) {
					return "translate(" + source.y0 + "," + source.x0 + ")";
				})
				.style("opacity", 0)


			// Enter any new nodes at the parent's previous position.


			pianiRect = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("height", barHeight)
				.attr("width", barWidth)
				.style("fill", function(d) {
					if (d.data.color != null)
						return d.data.color
					return "";
				})
				.attr("id", function(d) {
					return "pianoRect" + d.data.id_piano;
				})
				.attr("id_piano", function(d) {
					return d.data.id_piano;
				})
				.attr("fattore_uba", function(d) {
					return d.data.fattore_uba_reale;
				})
				.attr("fattore_ups", function(d) {
					return d.data.fattore_ups_reale;
				})
				.attr("target", function(d) {
					return d.data.target;
				})
				.attr("livello", function(d) {
					return d.data.level;
				})
				//.attr("z-index", function(d){return 5 - d.data.level;})
				.on("click", click)
				.on("mouseover", function(d) {
					if (d.data.name.length >= 90) {
						div.transition()
							.style("opacity", .9);

						div.html(d.data.name)
							.style("left", (d3.event.pageX) + "px")
							.style("top", (d3.event.pageY - 28) + "px");
					}
				})
				.on("mouseout", function(d) {
					div.transition()
						.style("opacity", 0);
				});

			targetNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", "white")

			valoreNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth + tagetNodeW * 3)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", function(d) {
					if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 1)
						return "#ffcc00";
					else if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 0)
						return "#00CC00";
					else if (Math.round(d.data.valore) > Math.round(d.data.target))
						return " #ffcc00";
					return "#C80000";
				})
				.attr("id", function(d) {
					return "valoreNode" + d.data.id_piano;
				})

			upsNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth + tagetNodeW * 4)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", "white")

			ubaNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth + tagetNodeW * 5)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", "white")


			upsTargetNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth + tagetNodeW)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", "white")

			ubaTargetNode = nodeEnter.append("rect")
				.attr("y", -barHeight / 2)
				.attr("x", barWidth + tagetNodeW * 2)
				.attr("height", barHeight)
				.attr("width", tagetNodeW)
				.style("fill", "white")

			nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", 0.5)
				.style("overflow", "hidden")
				.style("font-size", "9px")
				.attr("id_piano", function(d) {
					return d.data.id_piano;
				})
				.text(function(d) {
					if (d.data.level == 0)
						return "TOTALE";
					/*if(d.data.level == 1)
						return d.data.path.split("-").pop();
					if(d.data.level == 2)
						return d.data.path.split("-").pop();*/
					if (d.data.name.length < 82)
						return d.data.name.toUpperCase();
					return d.data.name.substring(0, 82).toUpperCase() + '...';
				})
				.attr("id", function(d) {
					return "nomePiano" + d.data.id_piano;
				})

			//.text(function(d) { return d.data.name + ' - TARGET: ' + d.data.target + ' - VALORE: ' + d.data.valore; });

			valori = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + tagetNodeW * 3 + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.style("font-weight", "bold")
				.attr("id", function(d) {
					return "valore" + d.data.id_piano;
				})
			/* .text(function(d) {
			return numberWithCommas(Math.round(d.data.valore)); 
		});*/

			targets = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.attr("id", function(d) {
					return "target" + d.data.id_piano;
				})
				.text(function(d) {
					return numberWithCommas(Math.round(d.data.target));
				});

			ups = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + tagetNodeW * 4 + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.attr("id", function(d) {
					return "ups_impegnati" + d.data.id_piano;
				})
			//  .text(function(d) { return numberWithCommas(Math.round(d.data.ups_impegnati)); });

			uba = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + +tagetNodeW * 5 + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.attr("id", function(d) {
					return "uba_impegnati" + d.data.id_piano;
				})
			// .text(function(d) { return numberWithCommas(Math.round(d.data.uba_impegnati)); });


			ups_target = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + tagetNodeW + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.attr("id", function(d) {
					return "ups_target" + d.data.id_piano;
				})
			//  .text(function(d) { return numberWithCommas(Math.round(d.data.ups_target)); });

			uba_target = nodeEnter.append("text")
				.attr("dy", 3.5)
				.attr("dx", barWidth + +tagetNodeW * 2 + (tagetNodeW / 2) + tagetNodeW / 2 - 1)
				.style("text-anchor", "end")
				.style("font-size", "9px")
				.attr("id", function(d) {
					return "uba_target" + d.data.id_piano;
				})
			//  .text(function(d) { return numberWithCommas(Math.round(d.data.uba_target)); });



			// Transition nodes to their new position.
			nodeEnter.transition()
				.duration(duration)
				.attr("transform", function(d) {
					return "translate(" + d.y + "," + (d.x + offsetRectPerTesto) + ")";
				})
				.style("opacity", 1);

			node.transition()
				.duration(duration)
				.attr("transform", function(d) {
					return "translate(" + d.y + "," + (d.x + offsetRectPerTesto) + ")";
				})
				.style("opacity", 1)
				.select("rect")
				.style("fill", function(d) {
					if (d.data.color != null)
						return d.data.color
					return "yellow";
				});

			// Transition exiting nodes to the parent's new position.
			node.exit().transition()
				.duration(duration)
				.attr("transform", function(d) {
					return "translate(" + source.y + "," + (d.x + offsetRectPerTesto) + ")";
				})
				.style("opacity", 0)
				.remove();

			// Stash the old positions for transition.
			root.each(function(d) {
				d.x0 = d.x;
				d.y0 = d.y;
			});
			d3.select("div#loader").style("display", "none");

			/*if(data_n.length){
	  valori.text(function(d){
	   console.log(d3.select(this)._groups[0][0].__data__.data);
		id = d3.select(this)._groups[0][0].__data__.data.count;
		return data_n[id].data.valore
		})*/

			if (data_n.length) { //uso nuovi valori se aggiornati
				// setTimeout(function(d){newValues()}, 500)
				newValues();
			}

			//data_n = [];

		}


		function newValues() {
			//console.log("Nuovi valori trovati")
			data_n.forEach(function(d) {


				if ((Math.round(d.valore) == Math.round(d.target) && d.case == 1) || Math.round(d.valore) > Math.round(d.target))
					col = "#ffcc00";
				else if (Math.round(d.valore) == Math.round(d.target) && d.case == 0)
					col = "#00CC00";
				else
					col = "#C80000";

				d3.selectAll("#valoreNode" + d.id_piano)
					.style("fill", col)
				/*d3.select("#valoreNode"+d.id_piano)
				.style("fill", function(d){
					if(d.id_piano == id_piano_glob)
						console.log(id_piano_glob+" "+d.valore+ " " + d.target);
					if (Math.round(d.valore) == Math.round(d.target))
						return "#00CC00";
					return "#C80000";
				});*/

				//console.log(d3.select("#valore"+d.id_piano)).

				d3.selectAll("#valore" + d.id_piano)
					.text(numberWithCommas(Math.round(d.valore)))

				d3.selectAll("#ups_impegnati" + d.id_piano).text(numberWithCommas(Math.round(d.ups_impegnati)))
				d3.selectAll("#uba_impegnati" + d.id_piano).text(numberWithCommas(Math.round(d.uba_impegnati)))
				d3.selectAll("#ups_target" + d.id_piano).text(numberWithCommas(Math.round(d.ups_target)))
				d3.selectAll("#uba_target" + d.id_piano).text(numberWithCommas(Math.round(d.uba_target)))

			})
			//console.log("AGGIORNATO")
		}

		// Toggle children on click.
		function click(d) {

			if (typeof old_clicked != "undefined")
				old_clicked.style("fill", old_color);

			if (typeof d.data.children != "undefined") {
				if (d.children) {
					d._children = d.children;
					d.children = null;
				} else {
					d.children = d._children;
					d._children = null;
				}
				update(d);
			}


			old_clicked = d3.select(this);
			old_color = d3.select(this).style("fill");

			d3.select(this).style("fill", "blue");

			piano = d3.select(this).attr("id_piano");
			console.log("Hai cliccato il piano: " + d3.select(this).attr("id_piano"));

			f_uba_glob = d3.select(this).attr("fattore_uba");
			f_ups_glob = d3.select(this).attr("fattore_ups");
			target_glob = d3.select(this).attr("target");

			if (f_uba_glob == "")
				f_uba_glob = 0;

			if (f_ups_glob == "")
				f_ups_glob = 0;

			console.log("con fattori ups " + f_ups_glob + ' uba ' + f_uba_glob);

			//-----------------------------------
			dd = []; //array distinct asl/nuclei
			for (var i = 0; i < data_strutt_glob.length; i++) {
				if (data_strutt_glob[i].id_piano == piano)
					dd.push(data_strutt_glob[i]);
			}

			console.log("strutture");
			console.log(dd);

			console.log("dd len: " + dd.length);
			stutture_length_glob = dd.length;


			for (i = 0; i < dd.length; i++) {

				var inputs = document.getElementById("input".concat(i + 1));

				//	if (inputs.type == "text"){
				//		inputs.value = "";
				//	  }


				var ii = document.getElementById("input".concat(i + 1));
				ii.setAttribute("value", "");

				//document.getElementById("input".concat(i+1)).blur()

				d3.select("#valori1").select("#campo".concat(i + 1))
					.text(dd[i].descr)
					.style("font-size", "12px")
					.attr("id_struttura", dd[i].id)
					.on("mouseover", function(d) {
						div.transition()
							.style("opacity", 1);

						div.html(d3.select(this).text())
							.style("left", (d3.event.pageX - 150) + "px")
							.style("top", (d3.event.pageY - 28) + "px");
					})
					.on("mouseout", function(d) {
						div.transition()
							.style("opacity", 0);
					});
				d3.select("#valori1").select("#pcampo".concat(i + 1))
					.attr("id_struttura", dd[i].id)
					.style("opacity", 1);
				//d3.select("#valori1").select("#input".concat(i+1))
				//.attr("value", dd[i].target);
				inputs.value = dd[i].target;
				d3.select("#valori1").select("#input".concat(i + 1))
					.attr("id_struttura", dd[i].id);
				d3.select("#valori1").select("#ups".concat(i + 1))
					.attr("value", Math.round(dd[i].target * f_ups_glob * 100) / 100);
				d3.select("#valori1").select("#uba".concat(i + 1))
					.attr("value", Math.round(dd[i].target * f_uba_glob * 100) / 100);

				/*d3.select("#borsello").select("#ups".concat(i+1))
					.attr("value", dd[i].ups_totali);
				d3.select("#borsello").select("#uba".concat(i+1))
					.attr("value", dd[i].uba_totali);*/
			}
			for (i = dd.length; i < 40; i++) {
				d3.select("#valori1").select("#pcampo".concat(i + 1))
					.remove()
				//.style("opacity", 0);
			}
			//-------------------------------------
			//for (var i=0; i<data_strutt_glob.length; i++){
			//	if(data_strutt_glob[i].id_piano == piano)
			//		d.append(data_strutt_glob[i]);
			// }
			//console.log(d.data);


			// d3.select("#selezione").select("#ppiano")
			//.style("white-space","nowrap")
			//	.text(piano) //+ " id " + d.data.id_piano);
			id_piano_glob = d.data.id_piano;



			if (typeof d.data.children != "undefined") {

				d3.select("#selezione").select("#ppiano")
					.text("Selezionare sottopiano/attivita'".toUpperCase())


				d3.select("#valori1")
					.transition().duration(100)
					.style("transform", "scale(0.5)")
					.style("opacity", 0)

			} else if (d.data.level == 4) {
				for (i = 0; i < 40; i++) {
					d3.select("input#input".concat(i)).style("background", "white");
					if (read_only) {
						d3.select("#valori1").select("#pcampo".concat(i + 1)).select("#input".concat(i + 1))
							.attr("readonly", true)
					}
				}
				max = 200;
				d3.select("#selezione").select("#ppiano")
					.text("Selezionato: " + d.data.name.toUpperCase().substr(0, max - 1) + (d.data.name.length > max ? '.....' : ''))
				d3.select("#valori1")
					.transition().duration(200)
					.style("transform", "scale(0.5)")
					.style("opacity", 0)
				d3.select("#valori1")
					.transition().delay(500).duration(500)
					.style("transform", "scale(1)")
					.style("opacity", 1)


			}
			//newValues();
			//updateTree();
		}

		function updateBorsellino(data) {
			for (i = 0; i < data.length; i++) {

				d3.select("#borsello").select("#campo".concat(i + 1))
					.text(data[i].descr)
					.style("font-size", "12px")
					.attr("id_struttura", data[i].id_struttura)
					.on("mouseover", function(d) {
						div.transition()
							.style("opacity", 1);

						div.html(d3.select(this).text())
							.style("left", (d3.event.pageX - 150) + "px")
							.style("top", (d3.event.pageY - 28) + "px");
					})
					.on("mouseout", function(d) {
						div.transition()
							.style("opacity", 0);
					});
				d3.select("#borsello").select("#pborsello".concat(i + 1))
					.style("white-space", "nowrap")
					.attr("id_struttura", data[i].id_struttura);

				d3.select("#borsello").select("#pborsello".concat(i + 1)).select("input#b1")
					.attr("id_struttura", data[i].id_struttura);

				d3.select("#borsello").select("#ups".concat(i + 1))
					.attr("value", numberWithCommas(Math.round(data[i].disponibili_ups - data[i].impegnati_ups)));
				d3.select("#borsello").select("#uba".concat(i + 1))
					.attr("value", numberWithCommas(Math.round(data[i].disponibili_uba - data[i].impegnati_uba)));
				d3.select("#borsello").select("#ups_cons".concat(i + 1))
					.attr("value", numberWithCommas(Math.round(data[i].impegnati_ups)));
				d3.select("#borsello").select("#uba_cons".concat(i + 1))
					.attr("value", numberWithCommas(Math.round(data[i].impegnati_uba)));
			}
			for (i = data.length; i < 40; i++) {
				d3.select("#borsello").select("#pborsello".concat(i + 1))
					.remove()
			}

			if (21 * data.length + 150 > 320) {
				d3.select("valori1")
					.style("top", 360 + "px");
				d3.select("selezione")
					.style("top", 320 + "px");
			} else {

				d3.select("valori1")
					.style("top", 21 * data.length + 150 + "px");
				d3.select("selezione")
					.style("top", 21 * data.length + 100 + "px");
			}
		}


		var once = false;

		function updateStrutture(data) {


			if (once) {
				for (i = 0; i < 40; i++) {
					d3.select("#valori1").select("#pcampo".concat(i + 1))
						.style("opacity", 0);
				}
				console.log(data);
				console.log("prima for");
				d = []; //array distinct asl/nuclei
				id_struttura = [];
				for (i = 0; i < data.length; i++) {
					d[i] = data[i].descr;
					id_struttura[i] = data[i].id;
				}
				console.log("dopo for");
				d = d.filter(onlyUnique);
				console.log("dopo unique");
				//ds = id_struttura.filter(onlyUnique);
				//console.log("dopo filter");
				stutture_length_glob = d.length;

				once = false;
			}


			d3.select("div#loader").style("display", "none");

		}

		function color(d) {
			return d._children ? "orange" : d.children ? "orange" : "yellow";
		}

		function getAmbiente() {
			const Http = new XMLHttpRequest();
			const url = 'ambiente.php';
			console.log(url);
			Http.open("GET", url);
			Http.send();
			Http.onreadystatechange = (e) => {
				if (Http.readyState == 4 && Http.status == 200) {
					console.log(Http.responseText);
					ambiente
						.text(Http.responseText)
						.style("color", "red")
						.style("font-weight", "bold")
				}
			}
		}

		function onlyUnique(value, index, self) {
			return self.indexOf(value) === index;
		}


		d3.select("body").on('keydown', function() {
			console.log(d3.event)

			if (d3.event.srcElement.id.startsWith("input") && (d3.event.keyCode != 16 && d3.event.keyCode != 17) && !read_only) {
				console.log(d3.event.srcElement)
				d3.select("input#" + d3.event.srcElement.id).style("background", "#ff6666");
			}

			if (d3.event.keyCode == 13 && d3.event.srcElement.id.startsWith("input")) {

				cell = "input#" + d3.event.srcElement.id;

				d3.select("div#loader").style("display", "");
				if (id_piano_glob == null) {
					alert("Selezionare prima il piano/attivita'");
					d3.select("div#loader").style("display", "none");
					return;
				}
				valore = d3.event.srcElement.value;
				if (!/^\d+$/.test(valore)) {
					alert("Inserire soli numeri");
					d3.select("div#loader").style("display", "none");
					return;
				}

				sum_valori = 0;
				for (i = 0; i < stutture_length_glob; i++) {
					//sum_valori = sum_valori +  parseInt(d3.select("#valori1").select("#input".concat(i+1))
					//.attr("value"));
					var inputs = document.getElementById("input".concat(i + 1));
					var val = inputs.value;
					if (val != "")
						sum_valori = sum_valori + parseInt(val);
					console.log(inputs.value)
				}


				console.log("sum: " + sum_valori);
				if (sum_valori > target_glob && id_stuttura_glob != 8) {
					if (!confirm('I valori inseriti superano il target consentito! Confermare?')) {
						//alert("Il valori inseriti superano il target consentito");
						d3.select("div#loader").style("display", "none");
						return;
					}
				}

				console.log(clicked);
				id_piano = id_piano_glob;
				try {
					id_struttura = d3.event.originalTarget.attributes.id_struttura.value; //Firefox
				} catch {
					id_struttura = d3.event.path[1].attributes.id_struttura.value; //Chrome
				}
				id_struttura_p = id_stuttura_glob;
				console.log("VALORE inserito: " + d3.event.srcElement.value + " su piano " + id_piano + " , su struttura " + id_struttura + ' , padre ' + id_struttura_p);
				console.log(" f_uba " + f_uba_glob + " f_ups " + f_ups_glob);

				//var uba = f_uba_glob * valore;
				//var ups = f_ups_glob * valore;

				uba = f_uba_glob;
				ups = f_ups_glob;
				i = d3.event.srcElement.id.match(/\d+/g).map(Number);
				try {
					document.getElementById("input".concat(parseInt(i) + 1)).focus()
				} catch {}
				const Http = new XMLHttpRequest();
				const url = 'upsert_new.php?piano=' + id_piano + '&struttura=' + id_struttura + '&valore=' + valore + '&utente=' + utente;
				console.log(url);
				Http.open("GET", url);
				Http.send();
				Http.onreadystatechange = (e) => {
					if (Http.readyState == 4 && Http.status == 200) {
						d3.select(cell).style("background", "white");
						var response = Http.responseText;
						console.log(response);
						d3.select("#log").attr("value", response);
						drawBorsellino();

						d3.select("#valori1").select("#ups".concat(i))
							.attr("value", Math.round(valore * f_ups_glob * 100) / 100)
						d3.select("#valori1").select("#uba".concat(i))
							.attr("value", Math.round(valore * f_uba_glob * 100) / 100)

						drawStrutture();
						updateTree();
					}
					//clicked.text(function(d) { return d.data.name + ' - TARGET: ' + d.data.target + ' - VALORE: ' + valore; }); 
				}
			}
		});

		function numberWithCommas(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
		}


		function isKeyPressed(event) {

			if (event.shiftKey) {
				id_s = d3.select(event.target).attr("id_struttura");
				window.open("tree.php?id=" + id_s)
			} else if (event.ctrlKey)
				window.open("tree.php?id=" + parent_glob)
		}


		function enterStuttura(event) {

			id_s = d3.select(event.target).attr("id_struttura");
			window.open("tree.php?id=" + id_s + "&anno=" + ANNO_GLOB);
		}
	</script>

	<div id="loader"></div>




	<borsello id="borsello" class="borsello">
		<fieldset style="border: 2px solid #3182bd" style="overflow-y: auto">

			<strong>
				<p> <label id="disp" style="margin-left: 50px">Disponibili</label> <label id="cons" style="margin-left: 55px">Consumati</label></p>
			</strong>
			<strong>
				<p> <label id="disp" style="margin-left: 50px">UPS </label> <label id="disp" style="margin-left: 25px">UBA </label>
			</strong>
			<strong> <label id="disp" style="margin-left: 45px">UPS </label> <label id="disp" style="margin-left: 25px">UBA </label>

				<p id="pborsello1"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups1" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba1" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons1" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons1" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo1">
					</span>
				</p>
				<p id="pborsello2"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups2" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba2" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons2" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons2" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo2">
					</span>
				</p>
				<p id="pborsello3"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups3" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba3" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons3" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons3" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo3">
					</span>
				</p>
				<p id="pborsello4"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups4" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba4" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons4" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons4" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo4">
					</span>
				</p>
				<p id="pborsello5"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups5" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba5" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons5" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons5" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo5">
					</span>
				</p>
				<p id="pborsello6"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups6" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba6" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons6" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons6" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo6">
					</span>
				</p>
				<p id="pborsello7"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups7" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba7" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons7" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons7" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo7">
					</span>
				</p>
				<p id="pborsello8"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups8" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba8" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons8" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons8" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo8">
					</span>
				</p>
				<p id="pborsello9"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups9" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba9" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons9" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons9" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo9">
					</span>
				</p>
				<p id="pborsello10"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups10" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba10" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons10" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons10" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo10">
					</span>
				</p>
				<p id="pborsello11"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups11" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba11" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons11" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons11" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo11">
					</span>
				</p>
				<p id="pborsello12"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups12" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba12" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons12" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons12" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo12">
					</span>
				</p>
				<p id="pborsello13"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups13" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba13" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons13" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons13" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo13">
					</span>
				</p>
				<p id="pborsello14"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups14" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba14" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons14" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons14" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo14">
					</span>
				</p>
				<p id="pborsello15"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups15" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba15" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons15" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons15" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo15">
					</span>
				</p>
				<p id="pborsello16"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups16" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba16" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons16" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons16" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo16">
					</span>
				</p>
				<p id="pborsello17"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups17" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba17" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons17" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons17" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo17">
					</span>
				</p>
				<p id="pborsello18"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups18" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba18" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons18" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons18" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo18">
					</span>
				</p>
				<p id="pborsello19"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups19" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba19" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons19" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons19" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo19">
					</span>
				</p>
				<p id="pborsello20"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"><input id="ups20" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba20" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons20" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons20" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo20">
					</span>
				</p>

				<!-- 20 --------------------- -->

				<p id="pborsello21"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups21" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba21" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons21" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons21" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo21">
					</span>
				</p>
				<p id="pborsello22"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups22" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba22" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons22" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons22" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo22">
					</span>
				</p>
				<p id="pborsello23"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups23" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba23" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons23" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons23" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo23">
					</span>
				</p>
				<p id="pborsello24"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups24" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba24" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons24" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons24" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo24">
					</span>
				</p>
				<p id="pborsello25"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups25" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba25" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons25" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons25" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo25">
					</span>
				</p>
				<p id="pborsello26"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups26" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba26" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons26" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons26" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo26">
					</span>
				</p>
				<p id="pborsello27"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups27" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba27" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons27" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons27" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo27">
					</span>
				</p>
				<p id="pborsello28"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups28" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba28" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons28" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons28" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo28">
					</span>
				</p>
				<p id="pborsello29"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups29" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba29" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons29" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons29" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo29">
					</span>
				</p>
				<p id="pborsello30"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups30" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba30" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons30" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons30" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo30">
					</span>
				</p>
				<p id="pborsello31"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups31" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba31" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons31" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons31" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo31">
					</span>
				</p>
				<p id="pborsello32"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups32" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba32" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons32" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons32" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo32">
					</span>
				</p>
				<p id="pborsello33"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups33" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba33" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons33" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons33" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo33">
					</span>
				</p>
				<p id="pborsello34"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups34" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba34" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons34" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons34" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo34">
					</span>
				</p>
				<p id="pborsello35"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups35" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba35" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons35" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons35" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo35">
					</span>
				</p>
				<p id="pborsello36"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups36" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba36" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons36" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons36" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo36">
					</span>
				</p>
				<p id="pborsello37"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups37" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba37" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons37" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons37" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo37">
					</span>
				</p>
				<p id="pborsello38"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups38" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba38" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons38" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons38" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo38">
					</span>
				</p>
				<p id="pborsello39"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups39" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba39" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons39" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons39" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo39">
					</span>
				</p>
				<p id="pborsello40"> <input type="button" value=">" id="b1" onclick="enterStuttura(event)" title="Clicca per entrare in sottostruttura"> <input id="ups40" type="text" name="Campo" value="" size="6" readonly class="input-box"> <input id="uba40" type="text" name="Campo" value="" size="6" readonly class="input-box">
					<input id="ups_cons40" type="text" name="Campo" value="" size="6" readonly class="input-box" style="margin-left: 10px;"> <input id="uba_cons40" type="text" name="Campo" value="" size="6" readonly class="input-box"> <span id="campo40">
					</span>
				</p>


	</borsello>


	<selezione class="selezione" id="selezione"> <strong>
			<p id="ppiano"> SELEZIONARE SOTTOPIANO/ATTIVITA' <span id="piano"> </span> </p>
		</strong>
	</selezione>

	<valori1 id="valori1" class="valori1">
		<strong>
			<p>
				<labelno id="disp">Distribuiti</labelno>
				<LABELno id="ups" style="margin-left: 15px;">UPS</labelno>
				<LABELno id="uba" style="margin-left: 15px;"> UBA</labelno>
			</p>
		</strong>
		<p id="pcampo1"> <input id="input1" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups1" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba1" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo1"> </span> </p>
		<p id="pcampo2"> <input id="input2" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups2" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba2" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo2"> </span> </p>
		<p id="pcampo3"> <input id="input3" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups3" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba3" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo3"> </span> </p>
		<p id="pcampo4"> <input id="input4" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups4" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba4" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo4"> </span> </p>
		<p id="pcampo5"> <input id="input5" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups5" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba5" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo5"> </span> </p>
		<p id="pcampo6"> <input id="input6" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups6" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba6" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo6"> </span> </p>
		<p id="pcampo7"> <input id="input7" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups7" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba7" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo7"> </span> </p>
		<p id="pcampo8"> <input id="input8" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups8" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba8" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo8"> </span></p>
		<p id="pcampo9"> <input id="input9" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups9" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba9" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo9"> </span></p>
		<p id="pcampo10"> <input id="input10" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups10" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba10" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo10"> </span></p>
		<p id="pcampo11"> <input id="input11" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups11" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba11" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo11"> </span> </p>
		<p id="pcampo12"> <input id="input12" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups12" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba12" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo12"> </span></p>
		<p id="pcampo13"> <input id="input13" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups13" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba13" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo13"> </span></p>
		<p id="pcampo14"> <input id="input14" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups14" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba14" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo14"> </span></p>
		<p id="pcampo15"> <input id="input15" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups15" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba15" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo15"> </span></p>
		<p id="pcampo16"> <input id="input16" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups16" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba16" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo16"> </span></p>
		<p id="pcampo17"> <input id="input17" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups17" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba17" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo17"> </span></p>
		<p id="pcampo18"> <input id="input18" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups18" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba18" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo18"> </span></p>
		<p id="pcampo19"> <input id="input19" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups19" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba19" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo19"> </span></p>
		<p id="pcampo20"> <input id="input20" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups20" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba20" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo20"> </span></p>

		<!-- 20----------------- -->
		<p id="pcampo21"> <input id="input21" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups21" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba21" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo21"> </span> </p>
		<p id="pcampo22"> <input id="input22" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups22" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba22" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo22"> </span> </p>
		<p id="pcampo23"> <input id="input23" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups23" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba23" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo23"> </span> </p>
		<p id="pcampo24"> <input id="input24" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups24" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba24" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo24"> </span> </p>
		<p id="pcampo25"> <input id="input25" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups25" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba25" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo25"> </span> </p>
		<p id="pcampo26"> <input id="input26" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups26" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba26" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo26"> </span> </p>
		<p id="pcampo27"> <input id="input27" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups27" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba27" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo27"> </span> </p>
		<p id="pcampo28"> <input id="input28" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups28" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba28" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo28"> </span></p>
		<p id="pcampo29"> <input id="input29" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups29" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba29" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo29"> </span></p>
		<p id="pcampo30"> <input id="input30" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups30" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba30" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo30"> </span></p>
		<p id="pcampo31"> <input id="input31" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups31" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba31" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo31"> </span> </p>
		<p id="pcampo32"> <input id="input32" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups32" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba32" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo32"> </span></p>
		<p id="pcampo33"> <input id="input33" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups33" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba33" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo33"> </span></p>
		<p id="pcampo34"> <input id="input34" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups34" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba34" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo34"> </span></p>
		<p id="pcampo35"> <input id="input35" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups35" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba35" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo35"> </span></p>
		<p id="pcampo36"> <input id="input36" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups36" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba36" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo36"> </span></p>
		<p id="pcampo37"> <input id="input37" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups37" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba37" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo37"> </span></p>
		<p id="pcampo38"> <input id="input38" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups38" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba38" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo38"> </span></p>
		<p id="pcampo39"> <input id="input39" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups39" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba39" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo39"> </span></p>
		<p id="pcampo40"> <input id="input40" type="text" name="Campo" value="" size="5" style="margin-left: 10px;" onmousedown="isKeyPressed(event)"> <input id="ups40" type="text" name="Campo" value="" size="5" readonly class="input-box" style="margin-left: 20px;"> <input id="uba40" type="text" name="Campo" value="" size="5" readonly class="input-box"> <span id="campo40"> </span></p>


		<hr>
		<p> <b> Inserire valore <i>Distribuiti</i> e premere Invio per ogni cella </b> </p>
		<hr>
		<p>	
			Per sapere il carico di lavoro sia in termini di campioni che UPS di ciascuna UOC si dovrebbero distribuire nel DPAT tutte le attività assegnate dal DPAR tal quali quindi consultare la tabella riepilogo e vedere il valore negativo ottenuto nel campo disponibili UPS. 
			<br>Il documento disponibile in matrix non conosce a priori come il Dipartimento distribuirà la programmazione regionale fra le diverse UOC/UOSD motivo per cui non stima di default nei calcoli disponibili le esigenze in UPS delle singole UOC/UOSD ma solo del Dipartimento intero.
		<p>
		<!--<p> LOG: <input id="log" size="80" class="bigText" readonly class="input-box"> </p> -->
	</valori1>