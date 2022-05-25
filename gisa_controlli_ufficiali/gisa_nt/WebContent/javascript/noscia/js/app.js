/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
'use strict';
(function () {
  // Load syntax highlighting
  hljs.initHighlightingOnLoad();

  // Add support notice to all examples
  window.addEventListener('DOMContentLoaded', addSupportNotice, false);

  function addSupportNotice() {
    // The "Example" heading
    var headings = document.querySelectorAll('h2');
    var foundExampleHeading;
    for (var i = 0; i < headings.length; ++i) {
      if (headings[i].textContent.trim().match(/^Examples?$/)) {
        foundExampleHeading = true;
        break;
      }
    }
    if (!foundExampleHeading) {
      return;
    }

    // The #browser_and_AT_support link
    var supportLink = document.querySelector('a[href$="#browser_and_AT_support"]');
    if (!supportLink) {
      return;
    }

    // Get the right relative URL to the root aria-practices page
    var urlPrefix = supportLink.getAttribute('href').split('#')[0];

    // Expected outcome '../js/app.js' OR '../../js/app.js'
    var scriptSource = document.querySelector('[src$="app.js"]').getAttribute('src');
    // Replace 'app.js' part with 'notice.html'

    var fetchSource = scriptSource.replace('app.js', './notice.html');
    //fetch('https://raw.githack.com/w3c/aria-practices/1228-support-notice/examples/js/notice.html')
    fetch(fetchSource)
    .then(function(response) {
      // Return notice.html as text
      return response.text();
    })
    .then(function(html) {
      // Parse response as text/html
      var parser = new DOMParser();
      return parser.parseFromString(html, "text/html");
    })
    .then(function(doc) {
      // Get the details element from the parsed response
      var noticeElement = doc.querySelector('details');
      // Rewrite links with the right urlPrefix
      var links = doc.querySelectorAll('a[href^="/#"]');
      for (var i = 0; i < links.length; ++i) {
        links[i].pathname = urlPrefix;
      }
      // Insert the support notice before the page's h1
      var heading = document.querySelector('h1');
      heading.parentNode.insertBefore(noticeElement, heading.nextSibling);
    })
  }
}());
