<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<style>


#policyPassword ul.helper-text li.valid {
  color: #1fd34a;
}
#policyPassword.valid input {
  border: 2px solid #1fd34a;
}


</style>

<script>
(function(){
	var password = document.querySelector('.password');
	
	var helperText = {
		charLength: document.querySelector('.helper-text .length'),
		lowercase: document.querySelector('.helper-text .lowercase'),
		uppercase: document.querySelector('.helper-text .uppercase'),
		special: document.querySelector('.helper-text .special')
	};
	
	var pattern = {
		charLength: function() {
			if( password.value.length >= 10 &&  password.value.length<=15 ) {
				return true;
			}
		},
		lowercase: function() {
			var regex = /^(?=.*[a-z]).+$/; // Lowercase character pattern

			if( regex.test(password.value) ) {
				return true;
			}
		},
		uppercase: function() {
			var regex = /^(?=.*[A-Z]).+$/; // Uppercase character pattern

			if( regex.test(password.value) ) {
				return true;
			}
		},
		special: function() {
			var regex = /^(?=.*[0-9_\W]).+$/; // Special character or number pattern

			if( regex.test(password.value) ) {
				return true;
			}
		}   
	};
	
	// Listen for keyup action on password field
  password.addEventListener('keyup', function (){
		// Check that password is a minimum of 8 characters
		patternTest( pattern.charLength(), helperText.charLength );
		
		// Check that password contains a lowercase letter		
		patternTest( pattern.lowercase(), helperText.lowercase );
		
		// Check that password contains an uppercase letter
		patternTest( pattern.uppercase(), helperText.uppercase );
		
		// Check that password contains a number or special character
		patternTest( pattern.special(), helperText.special );
    
    // Check that all requirements are fulfilled
    if( hasClass(helperText.charLength, 'valid') &&
			  hasClass(helperText.lowercase, 'valid') && 
			 	hasClass(helperText.uppercase, 'valid') && 
			  hasClass(helperText.special, 'valid')
		) {
			addClass(password.parentElement, 'valid');
    }
    else {
      removeClass(password.parentElement, 'valid');
    }
	});
	
	function patternTest(pattern, response) {
		if(pattern) {
      addClass(response, 'valid');
    }
    else {
      removeClass(response, 'valid');
    }
	}
	
	function addClass(el, className) {
		if (el.classList) {
			el.classList.add(className);
		}
		else {
			el.className += ' ' + className;
		}
	}
	
	function removeClass(el, className) {
		if (el.classList)
				el.classList.remove(className);
			else
				el.className = el.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
	}
	
	function hasClass(el, className) {
		if (el.classList) {
			console.log(el.classList);
			return el.classList.contains(className);	
		}
		else {
			new RegExp('(^| )' + className + '( |$)', 'gi').test(el.className);	
		}
	}
	
})();


</script>