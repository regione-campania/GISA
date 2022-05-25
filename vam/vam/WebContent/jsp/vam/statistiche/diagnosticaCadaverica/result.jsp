<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<style type="text/css">
.result {
margin: -1px auto;
width: 200px;
height: 124px;
border: 1px solid brown;
border-radius: 0 0 0 0;
color: brown;
}
.titleDiv{
	text-align: center;
	padding: 10px;
	font-size: 12px;
	color: brown;
}
.numberDiv{
	text-align: center;
	font-size: 50px;
	margin-top: 22px;
}
.animaliLabel{
	text-align: center;
	font-size: 30px;
	margin-top: 36px;
}

.resultList {
	height: 124px;
	//width: 100%;
	margin: 0 auto;
	position: relative;
	//overflow: hidden;
}

.resultList li {
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
	float: left;
	width: 200px;
	margin: 0 0 0 0px;
	/*background-color: #2B477D;*/
	border: 0;/*solid 1px #415F9D;*/
	position: relative;
	z-index: 1;
	list-style: none;
}
</style>

<h2>STATISTICHE</h2>
<h4 class="titolopagina" style="text-align: center;">Diagnostica cadaverica degli animali senza padrone deceduti per cause naturali (${anno })</h4>
<ul class="resultList" style="height: 40px;">
	<li>&nbsp;</li>
	<li>
		<div class="titleDiv">RANDAGI MORTI</div>
	</li>
	<li>
		<div class="titleDiv">SOTTOPOSTI A NECROSCOPIA</div>
	</li>
</ul>
<ul class="resultList">
	<li>
		<div class="result" style="border-right: 0px; border-top-left-radius: 8px;border-bottom: 0px">
			<div class="animaliLabel" ><i>CANI</i></div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${caniMorti }</div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${caniEsaminati }</div>
		</div>
	</li>
	<li>
		<div class="result" style="border-radius: 0px 8px 0px 0px; background-color: brown; color: white;" >
			<div class="numberDiv">${caniPercentuale }</div>
		</div>
	</li>
</ul>
<ul class="resultList">
	<li>
		<div class="result" style="border-right: 0px;border-bottom: 0px">
			<div class="animaliLabel" ><i>GATTI</i></div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${gattiMorti }</div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${gattiEsaminati }</div>
		</div>
	</li>
	<li>
		<div class="result" style="background-color: brown; color: white;border-bottom: 0px;border-top: 1px solid white;" >
			<div class="numberDiv">${gattiPercentuale }</div>
		</div>
	</li>
</ul>
<ul class="resultList">
	<li>
		<div class="result" style="border-right: 0px;border-bottom: 0px">
			<div class="animaliLabel" ><i>SINANTROPI</i></div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${sinantropiMorti }</div>
		</div>
	</li>
	<li>
		<div class="result" style="border-left: 0px; border-right: 0px;border-bottom: 0px" >
			<div class="numberDiv" >${sinantropiEsaminati }</div>
		</div>
	</li>
	<li>
		<div class="result" style="background-color: brown; color: white;border-bottom: 0px;border-top: 1px solid white;" >
			<div class="numberDiv">${sinantropiPercentuale }</div>
		</div>
	</li>
</ul>
<ul class="resultList">
	<li>
		<div class="result" style="background-color: whiteSmoke;color: grey;border-right: 0px; border-radius: 0px 0 0 8px;">
			<div class="animaliLabel" ><i>TOTALE</i></div>
		</div>
	</li>
	<li>
		<div class="result" style="background-color: whiteSmoke;color: grey;border-left: 0px; border-right: 0px;" >
			<div class="numberDiv" >${totMorti }</div>
		</div>
	</li>
	<li>
		<div class="result" style="background-color: whiteSmoke;color: grey;border-left: 0px; border-right: 0px;" >
			<div class="numberDiv" >${totEsaminati }</div>
		</div>
	</li>
	<li>
		<div class="result" style="color: whiteSmoke;background-color: gray; border-radius: 0 0 8px 0;border-top: 1px solid white;border-left: 1px solid transparent;" >
			<div class="numberDiv">${totPercentuale }</div>
		</div>
	</li>
</ul>
