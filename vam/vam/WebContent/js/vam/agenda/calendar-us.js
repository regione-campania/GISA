/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
$(document).ready(function() {
	

   var $calendar = $('#calendar');  
   var id = 10;

   $calendar.weekCalendar({	   
      timeslotsPerHour : 4,
      allowCalEventOverlap : true,
      overlapEventsSeparate: true,
      firstDayOfWeek : 1,
      businessHours :{start: 8, end: 20, limitDisplay: true },
      daysToShow : 7,
      height : function($calendar) {
         return $(window).height() - $("h1").outerHeight() - 1;
      },
      eventRender : function(calEvent, $event) {
         if (calEvent.end.getTime() < new Date().getTime()) {
            $event.css("backgroundColor", "#aaa");
            $event.find(".wc-time").css({
               "backgroundColor" : "#999",
               "border" : "1px solid #888"
            });
         }
      },
      draggable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      resizable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      eventNew : function(calEvent, $event) {
         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         var titleField = $dialogContent.find("input[name='title']");
         var bodyField = $dialogContent.find("textarea[name='body']");


         $dialogContent.dialog({
            modal: true,
            title: "Nuovo Evento",
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
               $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               Prenota : function() {
                  calEvent.id = id;
                  id++;
                  calEvent.start = new Date(startField.val());
                  calEvent.end = new Date(endField.val());
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();

                  $calendar.weekCalendar("removeUnsavedEvents");
                  $calendar.weekCalendar("updateEvent", calEvent);                  
                  document.form.action="vam.agenda.AddBooking.us";
                  document.form.submit();
                  $dialogContent.dialog("close");
               },
               Annulla : function() {
                  $dialogContent.dialog("close");
               }
            }
         }).show();

         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

      },
      eventDrop : function(calEvent, $event) {
    	  
    	  document.form.action="vam.agenda.EditBooking.us?id="+calEvent.id+"&&start="+calEvent.start+"&&end="+calEvent.end+"&&title="+calEvent.title+"&&body="+calEvent.body;
          document.form.submit();
      },
      eventResize : function(calEvent, $event) {
    	 
          document.form.action="vam.agenda.EditBooking.us?id="+calEvent.id+"&&start="+calEvent.start+"&&end="+calEvent.end+"&&title="+calEvent.title+"&&body="+calEvent.body;
          document.form.submit();
      },
      eventClick : function(calEvent, $event) {

         if (calEvent.readOnly) {
            return;
         }

         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         var titleField = $dialogContent.find("input[name='title']").val(calEvent.title);
         var bodyField = $dialogContent.find("textarea[name='body']");         
                       
         
         bodyField.val(calEvent.body);

         $dialogContent.dialog({
            modal: true,
            title: "Prenotazione - " + calEvent.title,
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
               $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               Modifica : function() {

                  calEvent.start = new Date(startField.val());
                  calEvent.end = new Date(endField.val());
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();

                  $calendar.weekCalendar("updateEvent", calEvent);
                  
                  document.form.action="vam.agenda.EditBooking.us?id="+calEvent.id;
                  document.form.submit();
                  
                  $dialogContent.dialog("close");
               },
               "Cancella" : function() {
                  $calendar.weekCalendar("removeEvent", calEvent.id);
                  
                  document.form.action="vam.agenda.DeleteBooking.us?id="+calEvent.id;
                  document.form.submit();
                  
                  $dialogContent.dialog("close");
               },
               Annulla : function() {
                  $dialogContent.dialog("close");
               }
            }
         }).show();

         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
         $(window).resize().resize(); //fixes a bug in modal overlay size ??

      },
      eventMouseover : function(calEvent, $event) {
      },
      eventMouseout : function(calEvent, $event) {
      },
      noEvents : function() {

      },        
      data: function(start, end, callback) {    	 
		$.getJSON("vam.agenda.ListBookings.us?x=" + (new Date()).getTime(), {
    	  start: start.getTime(),
    	  end: end.getTime()
    	  }, function(result) {    		     		  
    		  callback(result);    	  
    	  });
    }     
   });

   function resetForm($dialogContent) {
      $dialogContent.find("input").val("");
      $dialogContent.find("textarea").val("");
   }
   
   
   
   function getEventData2() {
	      var year = new Date().getFullYear();
	      var month = new Date().getMonth();
	      var day = new Date().getDate();

	      return {
	         events : [
	            {
	               "id":1,
	               "start": new Date(year, month, day, 12),
	               "end": new Date(year, month, day, 13, 30),
	               "title":"Lunch with Mike",
	               "body":"Test11"
	            },
	            {
	               "id":2,
	               "start": new Date(year, month, day, 14),
	               "end": new Date(year, month, day, 14, 45),
	               "title":"Dev Meeting"
	            },
	            {
	               "id":3,
	               "start": new Date(year, month, day + 1, 17),
	               "end": new Date(year, month, day + 1, 17, 45),
	               "title":"Hair cut"
	            },
	            {
	               "id":4,
	               "start": new Date(year, month, day - 1, 8),
	               "end": new Date(year, month, day - 1, 9, 30),
	               "title":"Team breakfast"
	            },
	            {
	               "id":5,
	               "start": new Date(year, month, day + 1, 14),
	               "end": new Date(year, month, day + 1, 15),
	               "title":"Product showcase"
	            },
	            {
	               "id":6,
	               "start": new Date(year, month, day, 10),
	               "end": new Date(year, month, day, 11),
	               "title":"I'm read-only",
	               readOnly : true
	            },
	            {
	                "id":7,
	                "start": new Date(year, month, day +2, 14),
	                "end": new Date(year, month, day +2, 15),
	                "title":"Test Prenotazione Sala"              
	             }

	         ]
	      };
	   }

   /*
    * Sets up the start and end time fields in the calendar event
    * form for editing based on the calendar event being edited
    */
   function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

      for (var i = 0; i < timeslotTimes.length; i++) {
         var startTime = timeslotTimes[i].start;
         var endTime = timeslotTimes[i].end;
         var startSelected = "";
         if (startTime.getTime() === calEvent.start.getTime()) {
            startSelected = "selected=\"selected\"";
         }
         var endSelected = "";
         if (endTime.getTime() === calEvent.end.getTime()) {
            endSelected = "selected=\"selected\"";
         }
         $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
         $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

      }
      $endTimeOptions = $endTimeField.find("option");
      $startTimeField.trigger("change");
   }

   var $endTimeField = $("select[name='end']");
   var $endTimeOptions = $endTimeField.find("option");

   //reduces the end time options to be only after the start time options.
   $("select[name='start']").change(function() {
      var startTime = $(this).find(":selected").val();
      var currentEndTime = $endTimeField.find("option:selected").val();
      $endTimeField.html(
            $endTimeOptions.filter(function() {
               return startTime < $(this).val();
            })
            );

      var endTimeSelected = false;
      $endTimeField.find("option").each(function() {
         if ($(this).val() === currentEndTime) {
            $(this).attr("selected", "selected");
            endTimeSelected = true;
            return false;
         }
      });

      if (!endTimeSelected) {
         //automatically select an end date 2 slots away.
         $endTimeField.find("option:eq(1)").attr("selected", "selected");
      }

   });


   var $about = $("#about");

   $("#about_button").click(function() {
      $about.dialog({
         title: "About this calendar demo",
         width: 600,
         close: function() {
            $about.dialog("destroy");
            $about.hide();
         },
         buttons: {
            close : function() {
               $about.dialog("close");
            }
         }
      }).show();
   });


});