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
//$id_asl = -1;

if($utente == null)
	$utente = "sviluppo";

if($readonly == null)
	$readonly = 0;

if ($id_asl == null)
	$id_asl = -999;	
$id_asl = $id_asl - 200;


?>


<!DOCTYPE html>
<meta charset="utf-8">
<style>


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
    height:30px;
}

.node_l rect {
  cursor: pointer;
  fill: #fff;
  fill-opacity: 0.5;
  stroke: #3182bd;
  stroke-width: 1.5px;
}

.node_l text {
  font: 10px sans-serif;
  pointer-events: none;
}

input.input-box, textarea { 
	background: #E8E8E8	; 
	  pointer-events: none;
	  font-size:11px;
-moz-user-select: none; -webkit-user-select: none; -ms-user-select:none; user-select:none;-o-user-select:none;
}


.link {
  fill: none;
  opacity: 0;
  stroke: #9ecae1;
  stroke-width: 1.5px;
}
	
body {
	overflow-x: hidden;
	}
	



</style>


<body>
<script src="js/d3.v4.min.js"></script>
<script>



function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
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

//var id_stuttura_glob = getUrlParam("id", 8);
SESSION_ID_ASL = parseInt('<?php echo $id_asl; ?>');
console.log("SESSION ID ASL: " + SESSION_ID_ASL);

if (window.location.href.indexOf("131.1.255.94") > -1)
	SESSION_ID_ASL = -201;

/*if (SESSION_ID_ASL == -1199) {
	alert("SESSIONE SCADUTA! RIESEGUIRE LOGIN")
	throw new Error("SESSIONE SCADUTA! RIESEGUIRE LOGIN!");
}*/

var id_stuttura_glob = null;
if (SESSION_ID_ASL != -1199) {
	id_stuttura_glob = getUrlParam("id", SESSION_ID_ASL);
}

if (id_stuttura_glob == -201) {
	id_stuttura_glob = 8;
}
console.log("ID STRUTTURA:  " + id_stuttura_glob);

var read_only =  <?php echo $readonly; ?> ;
console.log("READONLY: " + read_only);

var utente = '<?php echo $utente; ?>';

if (read_only == 1)
	read_only = true;
else
	read_only = false;
console.log("READONLY: " + read_only);

var once = true;
var once_text = true;
var once_rect = true;
var locked = false;

var valori, targets, ups, uba;

var clicked = null;
var old_clicked_l;

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
root_l;

var diagonal = d3.linkHorizontal()
	.x(function (d) {
		return d.y;
	})
	.y(function (d) {
		return d.x;
	});

var svg;
if(dim3 == linee){
	console.log("linee dim3");
	svg = d3.select("#tree").append("svg")
		.attr("id", "linee")
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
}else if(dim4 == linee){
	console.log("linee dim4");
	svg = d3.select("#tree2").append("svg")		
		.attr("id", "linee")
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
}

var div = d3.select("body").append("div")
	.attr("class", "tooltip")
	.style("opacity", 0);

d3.json("tree_linee_json.php", function (error, data_l) {
	if (error)
		throw error;
	//_data = data[0].get_struttura_piani_tree;
	//_data = _data.substring(1, _data.length-1);
	//_data = '{ "name": "flare", "children": [ ' + _data + '] }'
	//console.log(JSON.parse(data))
	//console.log(data);
	var _json = JSON.parse(data_l);
	root_l = d3.hierarchy(_json);
	root_l.x0 = 0;
	root_l.y0 = 0;

//	console.log(root_l)
	var nodes = root_l.descendants();
//	console.log("nodi")
//	console.log(nodes);

	for (i = 1; i < nodes.length; i++) {
		nodes[i]._children = nodes[i].children;
		nodes[i].children = null;
	}
	update_l(root_l);
});

function update_l(source) {
	
	var nodes = root_l.descendants();
	
	var height = Math.max(500, nodes.length * barHeight + margin.top + margin.bottom);

	d3.select("svg#linee").transition()
	.duration(duration)
	.attr("height", height);

	console.log("SELF")
	console.log(self.frameElement);
	d3.select(self.frameElement).transition()
	.duration(duration)
	.style("height", height + "px");

	// Compute the "layout". TODO https://github.com/d3/d3-hierarchy/issues/67
	var index = -1;
	root_l.eachBefore(function (n) {
		n.x = ++index * barHeight;
		n.y = n.depth * 10;
	});
	// Update the nodes�
	var node = svg.selectAll(".node_l")
		.data(nodes, function (d) {
			return d.id /*|| (d.id = ++i)*/;
		});

	var nodeEnter = node.enter().append("g")
		.attr("class", "node_l")
		.attr("transform", function (d) {
			return "translate(" + source.y0 + "," + source.x0 + ")";
		})
		.style("opacity", 0)

		// Enter any new nodes at the parent's previous position.


	var lineaRect = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("height", barHeight)
		.attr("width", barWidth)
		.attr("id_linea", function (d) {
			return d.data.id;
		})
		.attr("nome", function (d) {
			return d.data.descrizione;
		})
		.on("click", click)
		.on("mouseover", function (d) {
			if (d.data.codice.length >= 90) {
				div.transition()
				.style("opacity", .9);

				div.html(d.data.codice)
				.style("left", (d3.event.pageX) + "px")
				.style("top", (d3.event.pageY - 28) + "px");
			}
		})
		.on("mouseout", function (d) {
			div.transition()
			.style("opacity", 0);
		});

	nodeEnter.append("text")
	.attr("dy", 3.5)
	.attr("dx", 0.5)
	.style("overflow", "hidden")
	.style("font-size", "9px")
	.attr("id_linea", function (d) {
		return d.data.id;
	})
	.text(function (d) {
		if (d.data.descrizione.length < 82)
			return d.data.descrizione.toUpperCase();
		return d.data.descrizione.substring(0, 82).toUpperCase() + '...';
	})
	.attr("id", function (d) {
		return "linea" + d.data.id;
	})

	// Transition nodes to their new position.
	nodeEnter.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1);

	node.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1)
	.select("rect")

	// Transition exiting nodes to the parent's new position.
	node.exit().transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + source.y + "," + source.x + ")";
	})
	.style("opacity", 0)
	.remove();

	// Stash the old positions for transition.
	root_l.each(function (d) {
		d.x0 = d.x;
		d.y0 = d.y;
	});
	d3.select("div#loader").style("display", "none");

}

// Toggle children on click.
function click(d) {

	if (typeof old_clicked_l != "undefined")
		old_clicked_l.style("fill", old_color_l);

	if (typeof d.data.children != "undefined") {
		if (d.children) {
			d._children = d.children;
			d.children = null;
		} else {
			d.children = d._children;
			d._children = null;
		}
		update_l(d);
	}

	old_clicked_l = d3.select(this);
	old_color_l = d3.select(this).style("fill");

	d3.select(this).style("fill", "blue");

	linea = d3.select(this).attr("id_linea");
	console.log("Hai cliccato la linea: " + d3.select(this).attr("id_linea"));
	console.log(d3.select(this));
	//dim3.text(d3.select(this).attr("struttura"));

	if (dim3 == linee){
		id_3_new = linea;
		d3.select("#dim3").text(d3.select(this).attr("nome"));
	}else if (dim4 == linee){
		id_4_new = linea;
		d3.select("#dim4").text(d3.select(this).attr("nome"));
	}

	query();
}

var once = false;

function color(d) {
	return d._children ? "orange" : d.children ? "orange" : "yellow";
}

function getAmbiente() {
	const Http = new XMLHttpRequest();
	const url = '../ambiente.php';
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

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}


</script>



