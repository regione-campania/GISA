/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var options;
$(document).ready(function() {
 options = {
        beforeSend : function() {
                $("#progressbox").show();
                // clear everything
                $("#progressbar").width('0%');
                $("#message").empty();
                $("#percent").html("0%");
        },
        uploadProgress : function(event, position, total, percentComplete) {
                $("#progressbar").width(percentComplete + '%');
                $("#percent").html(percentComplete + '%');

                // change message text to red after 50%
                if (percentComplete > 50) {
                $("#message").html("<font color='red'>File Upload is in progress</font>");
                }
        },
        success : function() {
                $("#progressbar").width('100%');
                $("#percent").html('100%');
        },
        complete : function(response) {
        $("#message").html("<font color='blue'>Your file has been uploaded!</font>");
        },
        error : function() {
        $("#message").html("<font color='red'> ERROR: unable to upload files</font>");
        }
};

});