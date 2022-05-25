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

.node_p rect {
  cursor: pointer;
  fill: #fff;
  fill-opacity: 0.5;
  stroke: #3182bd;
  stroke-width: 1.5px;
}

.node_p text {
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

if (window.location.href.indexOf("http://131.1.255.94") > -1)
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

var read_only =  <?php echo $readonly; ?>;
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

title_glob = null;

var valori, targets, ups, uba;

var clicked = null;
var old_clicked_p;
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
root_p;

var diagonal = d3.linkHorizontal()
	.x(function (d) {
		return d.y;
	})
	.y(function (d) {
		return d.x;
	});

if (dim3 == piani) {
	console.log("piano dim3");
	var svg_p = d3.select("#tree").append("svg")
		.attr("id","piani")
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
} else if (dim4 == piani) {
	console.log("piano dim4");
	var svg_p = d3.select("#tree2").append("svg")
		.attr("id","piani")	
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
}

var div = d3.select("body").append("div")
	.attr("class", "tooltip")
	.style("opacity", 0);

d3.json("../tree_json_2019.php?id_struttura=" + 8 + "&anno="+ANNO_GLOB, function (error, data_p) {
	if (error)
		throw error;

	var _json = JSON.parse(data_p);
	root_p = d3.hierarchy(_json);
	root_p.x0 = 0;
	root_p.y0 = 0;

	var nodes_p = root_p.descendants();

	
	console.log(nodes_p);
	for (i = 1; i < nodes_p.length; i++) {
		nodes_p[i]._children = nodes_p[i].children;
		nodes_p[i].children = null;
	}
	update_p(root_p);
});

function update_p(source) {

	var nodes_p = root_p.descendants();

	var height = Math.max(500, nodes_p.length * barHeight + margin.top + margin.bottom);

	d3.select("svg#piani").transition()
	.duration(duration)
	.attr("height", height);

	
	console.log("SELF")
	console.log(self.frameElement);
	d3.select(self.frameElement).transition()
	.duration(duration)
	.style("height", height + "px");

	// Compute the "layout". TODO https://github.com/d3/d3-hierarchy/issues/67
	var index = -1;
	root_p.eachBefore(function (n) {
		n.x = ++index * barHeight;
		n.y = n.depth * 10;
	});

	// Update the nodes�
	var node_p = svg_p.selectAll(".node_p")
		.data(nodes_p, function (d) {
			return d.id /*|| (d.id = ++i)*/;
		});

	var nodeEnter_p = node_p.enter().append("g")
		.attr("class", "node_p")
		.attr("transform", function (d) {
			return "translate(" + source.y0 + "," + source.x0 + ")";
		})
		.style("opacity", 0)

		// Enter any new nodes at the parent's previous position.

	nodeEnter_p.append("rect")
		.attr("y", -barHeight / 2)
		.attr("height", barHeight)
		.attr("width", barWidth)
		.style("fill", function (d) {
			if (d.data.color != null)
				return d.data.color
				return "";
		})
		.attr("id_piano", function (d) {
			return d.data.id_piano;
		})
		.attr("nome", function (d) {
			return d.data.path;
		})
		.on("click", click_p)
		.on("mouseover", function (d) {
			if (d.data.name.length >= 90) {
				div.transition()
				.style("opacity", .9);

				div.html(d.data.name)
				.style("left", (d3.event.pageX) + "px")
				.style("top", (d3.event.pageY - 28) + "px");
			}
		})
		.on("mouseout", function (d) {
			div.transition()
			.style("opacity", 0);
		});

	nodeEnter_p.append("text")
	.attr("dy", 3.5)
	.attr("dx", 0.5)
	.style("overflow", "hidden")
	.style("font-size", "9px")
	.attr("id_piano", function (d) {
		return d.data.id_piano;
	})
	.text(function (d) {
		if (d.data.level == 0)
			return "TOTALE";
		if (d.data.name.length < 82)
			return d.data.name.toUpperCase();
		return d.data.name.substring(0, 82).toUpperCase() + '...';
	})

	//.text(function(d) { return d.data.name + ' - TARGET: ' + d.data.target + ' - VALORE: ' + d.data.valore; });


	// Transition nodes to their new position.
	nodeEnter_p.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1);

	node_p.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1)
	.select("rect")
	.style("fill", function (d) {
		if (d.data.color != null)
			return d.data.color
			return "yellow";
	});

	// Transition exiting nodes to the parent's new position.
	node_p.exit().transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + source.y + "," + source.x + ")";
	})
	.style("opacity", 0)
	.remove();

	// Stash the old positions for transition.
	root_p.each(function (d) {
		d.x0 = d.x;
		d.y0 = d.y;
	});

}

// Toggle children on click.
function click_p(d) {

	if (typeof old_clicked_p != "undefined")
		old_clicked_p.style("fill", old_color_p);

	if (typeof d.data.children != "undefined") {
		if (d.children) {
			d._children = d.children;
			d.children = null;
		} else {
			d.children = d._children;
			d._children = null;
		}
		update_p(d);
	}

	old_clicked_p = d3.select(this);
	old_color_p = d3.select(this).style("fill");

	d3.select(this).style("fill", "blue");

	piano = d3.select(this).attr("id_piano");
	console.log("Hai cliccato il piano: " + d3.select(this).attr("id_piano"));
	id_p = piano;
	// dim3.text(d3.select(this).attr("piano"))

	if (dim3 == piani){
		id_3_new = piano;
		d3.select("#dim3").text(d3.select(this).attr("nome"));
	}else if (dim4 == piani){
		id_4_new = piano;
		d3.select("#dim4").text(d3.select(this).attr("nome"));
	}

	query();

}

var once = false;

function color(d) {
	return d._children ? "orange" : d.children ? "orange" : "yellow";
}

function onlyUnique(value, index, self) {
	return self.indexOf(value) === index;
}

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
</script>





