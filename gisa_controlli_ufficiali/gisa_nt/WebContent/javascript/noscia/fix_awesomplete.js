/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//avoid Redeclaration Error
if(!window.isAwesompleteInitialized)
	window.isAwesompleteInitialized = false

function initAwesomplete(...args) {
	if(isAwesompleteInitialized)
		return
	//default config
	let inputId = 'comune_nascita_rapp_legaleLabel'
	let hiddenId = 'comune_nascita_rapp_legale'
	let options =  {
		list: cArray,
		maxItems: 20,
		autoFirst: true,
		replace: function(item) {
			input.value = item.label
			hidden.value = item.value
		},
		filter: Awesomplete.FILTER_STARTSWITH
	}
	//looking for new configuration
	args.forEach(arg => {
		if(typeof arg === 'object') {
			if('inputId' in arg)
				inputId = arg.inputId
			if('hiddenId' in arg)
				hiddenId = arg.hiddenId
			if('options' in arg)
				options = arg.options
		}
	})

	//initialization
	var input = document.getElementById(inputId)
	var hidden = document.getElementById(hiddenId)
	var awesomplete = new Awesomplete(input, options)
	
	isAwesompleteInitialized = true
}