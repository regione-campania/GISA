<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" type="text/css" href="../css/jqcontextmenu.css" />

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>

<script type="text/javascript" src="../javascript/jqcontextmenu.js">

/***********************************************
* jQuery Context Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for this script and 100s more
***********************************************/

</script>

<script type="text/javascript">

//Usage: $(elementselector).addcontextmenu('id_of_context_menu_on_page')
//To apply context menu to entire document, use: $(document).addcontextmenu('id_of_context_menu_on_page')

jQuery(document).ready(function($){
	$('a.mylinks').addcontextmenu('contextmenu1') //apply context menu to links with class="mylinks"
})


jQuery(document).ready(function($){
	$('img').addcontextmenu('contextmenu2') //apply context menu to all images on the page
})

</script>

<p><a class="mylinks" href="http://www.dynamicdrive.com">Dynamic Drive</a></p>
<p><img src="http://img339.imageshack.us/img339/2488/redleaveslarge.jpg" /></p>


<!--HTML for Context Menu 1-->
<ul id="contextmenu1" class="jqcontextmenu">
<li><a href="#">Item 1a</a></li>
<li><a href="#">Item 2a</a></li>
<li><a href="#">Item Folder 3a</a>
	<ul>
	<li><a href="#">Sub Item 3.1a</a></li>
	<li><a href="#">Sub Item 3.2a</a></li>
	<li><a href="#">Sub Item 3.3a</a></li>
	<li><a href="#">Sub Item 3.4a</a></li>
	</ul>
</li>
<li><a href="#">Item 4a</a></li>
<li><a href="#">Item Folder 5a</a>
	<ul>
	<li><a href="#">Sub Item 5.1a</a></li>
	<li><a href="#">Item Folder 5.2a</a>
		<ul>
		<li><a href="#">Sub Item 5.2.1a</a></li>
		<li><a href="#">Sub Item 5.2.2a</a></li>
		<li><a href="#">Sub Item 5.2.3a</a></li>
		<li><a href="#">Sub Item 5.2.4a</a></li>
		</ul>
	</li>
	</ul>
</li>
<li><a href="#">Item 6a</a></li>
</ul>


<!--HTML for Context Menu 2-->
<ul id="contextmenu2" class="jqcontextmenu">
<li><a href="#">Item 1a</a></li>
<li><a href="#">Item 2a</a></li>
<li><a href="#">Item 1a</a></li>
<li><a href="#">Item 2a</a></li>
</ul>