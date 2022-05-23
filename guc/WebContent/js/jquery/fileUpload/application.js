/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * jQuery File Upload Plugin JS Example 6.0
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload();

    if (window.location.hostname === 'blueimp.github.com') {
        // Demo settings:
        $('#fileupload').prop(
            'action',
            '//jquery-file-upload.appspot.com'
        );
        $('#fileupload').fileupload('option', {
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
    } else {
        $('#fileupload').fileupload('option', {
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Load existing files:
        $.getJSON($('#fileupload').prop('action') 
        		+ '?idAccettazione=' + $('#fileupload').prop('idAccettazione').value
        		+ '&idClass=' + $('#fileupload').prop('idClass').value
        		+ '&classRef=' + $('#fileupload').prop('classRef').value
        		+ '&x=' + ( new Date() ).getTime(), function (files) {
            var fu = $('#fileupload').data('fileupload'),
                template;
            fu._adjustMaxNumberOfFiles(-files.length);
            template = fu._renderDownload(files)
                .appendTo($('#fileupload .files'));
            // Force reflow:
            fu._reflow = fu._transition && template.length &&
                template[0].offsetWidth;
            template.addClass('in');
        });
    }

    // Enable iframe cross-domain access via redirect page:
    var redirectPage = window.location.href.replace(
        /\/[^\/]*$/,
        '/result.html?%s'
    );
    $('#fileupload').bind('fileuploadsend', function (e, data) {
        if (data.dataType.substr(0, 6) === 'iframe') {
            var target = $('<a/>').prop('href', data.url)[0];
            if (window.location.host !== target.host) {
                data.formData.push({
                    name: 'redirect',
                    value: redirectPage
                });
            }
        }
    });

    // Open download dialogs via iframes,
    // to prevent aborting current uploads:
    $('#fileupload .files').delegate(
        'a:not([rel^=gallery])',
        'click',
        function (e) {
            e.preventDefault();
            $('<iframe style="display:none;"></iframe>')
                .prop('src', this.href)
                .appendTo(document.body);
        }
    );

    // Initialize the Bootstrap Image Gallery plugin:
    $('#fileupload .files').imagegallery();

});
