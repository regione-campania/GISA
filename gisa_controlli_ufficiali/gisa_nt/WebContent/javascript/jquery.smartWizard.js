/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * SmartWizard 3.3.1 plugin
 * jQuery Wizard control Plugin
 * by Dipu
 *
 * Refactored and extended:
 * https://github.com/mstratman/jQuery-Smart-Wizard
 *
 * Original URLs:
 * http://www.techlaboratory.net
 * http://tech-laboratory.blogspot.com
 */

function SmartWizard(target, options) {
	loadModalWindow();
    this.target       = target;
    this.options      = options;
    this.curStepIdx   = options.selected;
    this.steps        = $(target).children("ul").children("li").children("a"); // Get all anchors
    this.contentWidth = 0;
    this.msgBox = $('<div class="msgBox"><div class="content"></div><a href="#" class="close">X</a></div>');
    this.elmStepContainer = $('<div></div>').addClass("stepContainer");
    this.loader = $('<div>Loading</div>').addClass("loader");
    this.buttons = {
        next : $('<a>'+options.labelNext+'</a>').attr("href","#").addClass("buttonNext"),
        previous : $('<a>'+options.labelPrevious+'</a>').attr("href","#").addClass("buttonPrevious"),
//       finish  : $('<a>'+options.labelFinish+'</a>').attr("href","#").addClass("buttonFinish")
        
    };
    
//    this.buttons1 = {
//            next : $('<a>'+options.labelNext+'</a>').attr("href","#").addClass("buttonNextSU"),
//            previous : $('<a>'+options.labelPrevious+'</a>').attr("href","#").addClass("buttonPreviousSU"),
////           finish  : $('<a>'+options.labelFinish+'</a>').attr("href","#").addClass("buttonFinish")
//            
//        };

    /*
     * Private functions
     */

    var _init = function($this) {
        var elmActionBar = $('<div></div>').addClass("actionBar");
     //   var elmActionBarSu = $('<div></div>').addClass("actionBarSU");
        elmActionBar.append($this.msgBox);
        $('.close',$this.msgBox).click(function() {
            $this.msgBox.fadeOut("normal");
            return false;
        });

        var allDivs = $this.target.children('div');
        // CHeck if ul with steps has been added by user, if not add them
        if($this.target.children('ul').length == 0 ){
            var ul = $("<ul/>");
            target.prepend(ul)

            // for each div create a li
            allDivs.each(function(i,e){
                var title = $(e).first().children(".StepTitle").text();
                var s = $(e).attr("id")
                // if referenced div has no id, add one.
                if (s==undefined){
                    s = "step-"+(i+1)
                    $(e).attr("id",s);
                }
                var span = $("<span/>").addClass("stepDesc").text(title);
                var li = $("<li></li>").append($("<a></a>").attr("href", "#" + s).append($("<label></label>").addClass("stepNumber").text(i + 1)).append(span));
                ul.append(li);
            });
            // (re)initialise the steps property
            $this.steps = $(target).children("ul").children("li").children("a"); // Get all anchors
        }
        $this.target.children('ul').addClass("anchor");
        allDivs.addClass("content");

        // highlight steps with errors
        if($this.options.errorSteps && $this.options.errorSteps.length>0){
            $.each($this.options.errorSteps, function(i, n){
                $this.setError({ stepnum: n, iserror:true });
            });
        }

    //    elmActionBarSu.append($this.buttons1.next).append($this.buttons1.previous);
       // elmActionBarSU.append($this.buttons1.previous);
    //    $this.target.append(elmActionBarSu);
//        $this.target.append($this.buttons1.next);
//        $this.target.append($this.buttons1.previous);
   //     $this.target.append('</br></br>');
        $this.elmStepContainer.append(allDivs);
        elmActionBar.append($this.loader);
        $this.target.append($this.elmStepContainer);

        if ($this.options.includeFinishButton){
            elmActionBar.append($this.buttons.finish)
        }

        elmActionBar.append($this.buttons.next)
            .append($this.buttons.previous);
        $this.target.append(elmActionBar);
        this.contentWidth = $this.elmStepContainer.width();

        $($this.buttons.next).click(function() {
            $this.goForward();
            return false;
        });
        $($this.buttons.previous).click(function() {
            $this.goBackward();
            return false;
        });
        
//        $($this.buttons1.next).click(function() {
//            $this.goForward();
//            return false;
//        });
//        $($this.buttons1.previous).click(function() {
//            $this.goBackward();
//            return false;
//        });
        
        $($this.buttons.finish).click(function() {
           /* if(!$(this).hasClass('buttonDisabled')){
                if($.isFunction($this.options.onFinish)) {
                    var context = { fromStep: $this.curStepIdx + 1 };
                    if(!$this.options.onFinish.call(this,$($this.steps), context)){
                        return false;
                    }
                }else{
                    var frm = $this.target.parents('form');
                    if(frm && frm.length){
                        frm.submit();
                    }
                }
            }*/
        	alert('to do');
            //return false;
        });

        $($this.steps).bind("click", function(e){
        	
       
        
            if($this.steps.index(this) == $this.curStepIdx){
                return false;
            }
            var nextStepIdx = $this.steps.index(this);
            var isDone = $this.steps.eq(nextStepIdx).attr("isDone") - 0;
            if(isDone == 1){
                _loadContent($this, nextStepIdx);
            }
            
           
            return false;
        });

        // Enable keyboard navigation
        if($this.options.keyNavigation){
            $(document).keyup(function(e){
                if(e.which==39){ // Right Arrow
                    $this.goForward();
                }else if(e.which==37){ // Left Arrow
                    $this.goBackward();
                }
            });
        }
        //  Prepare the steps
        _prepareSteps($this);
        // Show the first slected step
        _loadContent($this, $this.curStepIdx);
    };

    var _prepareSteps = function($this) {
        if(! $this.options.enableAllSteps){
            $($this.steps, $this.target).removeClass("selected").removeClass("done").addClass("disabled");
            $($this.steps, $this.target).attr("isDone",0);
        }else{
            $($this.steps, $this.target).removeClass("selected").removeClass("disabled").addClass("done");
            $($this.steps, $this.target).attr("isDone",1);
        }

        $($this.steps, $this.target).each(function(i){
        	
        	//PRIMA DI NASCONDERE APPLICO FIXED HEADER
        	//alert(i);
        	$('#table'+i).fixedHeaderTable({
    	    	id:  i,
       	 		divHeadPaddingLeft:  $this.options.divHeadPaddingLeft,
        		divBodyPaddingLeft:   $this.options.divBodyPaddingLeft,
        		fixedTypeNumber:  $this.options.fixedTypeNumber
    			
    	    }); 
        	
        	
            $($(this).attr("href").replace(/^.+#/, '#'), $this.target).hide();
            $(this).attr("rel",i+1);
        });
    };

    var _step = function ($this, selStep) {
        return $(
            $(selStep, $this.target).attr("href").replace(/^.+#/, '#'),
            $this.target
        );
    };

    var _loadContent = function($this, stepIdx) {
        var selStep = $this.steps.eq(stepIdx);
        var ajaxurl = $this.options.contentURL;
        var ajaxurl_data = $this.options.contentURLData;
        var hasContent = selStep.data('hasContent');
        var stepNum = stepIdx+1;
        if (ajaxurl && ajaxurl.length>0) {
        	alert('if');
            if ($this.options.contentCache && hasContent) {
                _showStep($this, stepIdx);
            } else {
                var ajax_args = {
                    url: ajaxurl,
                    type: $this.options.ajaxType,
                    data: ({step_number : stepNum}),
                    dataType: "text",
                    beforeSend: function(){
                        $this.loader.show();
                    },
                    error: function(){
                        $this.loader.hide();
                    },
                    success: function(res){
                        $this.loader.hide();
                        if(res && res.length>0){
                            selStep.data('hasContent',true);
                            _step($this, selStep).html(res);
                            _showStep($this, stepIdx);
                        }
                    }
                };
                if (ajaxurl_data) {
                    ajax_args = $.extend(ajax_args, ajaxurl_data(stepNum));
                }
                $.ajax(ajax_args);
            }
        }else{
        //	alert('else');
            _showStep($this,stepIdx);
        }
    };

    var _showStep = function($this, stepIdx) {
        var selStep = $this.steps.eq(stepIdx);
        var curStep = $this.steps.eq($this.curStepIdx);
        if(stepIdx != $this.curStepIdx){
            if($.isFunction($this.options.onLeaveStep)) {
                var context = { fromStep: $this.curStepIdx+1, toStep: stepIdx+1 };
                if (! $this.options.onLeaveStep.call($this,$(curStep), context)){
                    return false;
                }
            }
        }
        $this.elmStepContainer.height(_step($this, selStep).outerHeight());
        var prevCurStepIdx = $this.curStepIdx;
        $this.curStepIdx =  stepIdx;
        if ($this.options.transitionEffect == 'slide'){
            _step($this, curStep).slideUp("fast",function(e){
                _step($this, selStep).slideDown("fast");
                _setupStep($this,curStep,selStep);
            });
        } else if ($this.options.transitionEffect == 'fade'){
            _step($this, curStep).fadeOut("fast",function(e){
                _step($this, selStep).fadeIn("fast");
                _setupStep($this,curStep,selStep);
            });
        } else if ($this.options.transitionEffect == 'slideleft'){
            var nextElmLeft = 0;
            var nextElmLeft1 = null;
            var nextElmLeft = null;
            var curElementLeft = 0;
            if(stepIdx > prevCurStepIdx){
                nextElmLeft1 = $this.elmStepContainer.width() + 10;
                nextElmLeft2 = 0;
                curElementLeft = 0 - _step($this, curStep).outerWidth();
            } else {
                nextElmLeft1 = 0 - _step($this, selStep).outerWidth() + 20;
                nextElmLeft2 = 0;
                curElementLeft = 10 + _step($this, curStep).outerWidth();
            }
            if (stepIdx == prevCurStepIdx) {
                nextElmLeft1 = $($(selStep, $this.target).attr("href"), $this.target).outerWidth() + 20;
                nextElmLeft2 = 0;
                curElementLeft = 0 - $($(curStep, $this.target).attr("href"), $this.target).outerWidth();
            } else {
                $($(curStep, $this.target).attr("href"), $this.target).animate({left:curElementLeft},"fast",function(e){
                    $($(curStep, $this.target).attr("href"), $this.target).hide();
                });
            }

            _step($this, selStep).css("left",nextElmLeft1).show().animate({left:nextElmLeft2},"fast",function(e){
                _setupStep($this,curStep,selStep);
            });
        } else {
            _step($this, curStep).hide();
            _step($this, selStep).show();
            _setupStep($this,curStep,selStep);
        }
        return true;
    };

    var _setupStep = function($this, curStep, selStep) {
        $(curStep, $this.target).removeClass("selected");
        $(curStep, $this.target).addClass("done");

        $(selStep, $this.target).removeClass("disabled");
        $(selStep, $this.target).removeClass("done");
        $(selStep, $this.target).addClass("selected");

        $(selStep, $this.target).attr("isDone",1);

        _adjustButton($this);

        if($.isFunction($this.options.onShowStep)) {
            var context = { fromStep: parseInt($(curStep).attr('rel')), toStep: parseInt($(selStep).attr('rel')) };
            if(! $this.options.onShowStep.call(this,$(selStep),context)){
                return false;
            }
        }
        if ($this.options.noForwardJumping) {
            // +2 == +1 (for index to step num) +1 (for next step)
            for (var i = $this.curStepIdx + 2; i <= $this.steps.length; i++) {
                $this.disableStep(i);
            }
        }
    };

    var _adjustButton = function($this) {
        if (! $this.options.cycleSteps){
            if (0 >= $this.curStepIdx) {
                $($this.buttons.previous).addClass("buttonDisabled");
//                $($this.buttons1.previous).addClass("buttonDisabled");
                if ($this.options.hideButtonsOnDisabled) {
                    $($this.buttons.previous).hide();
//                    $($this.buttons1.previous).hide();
                }
            }else{
                $($this.buttons.previous).removeClass("buttonDisabled");
//                $($this.buttons1.previous).removeClass("buttonDisabled");
                if ($this.options.hideButtonsOnDisabled) {
                    $($this.buttons.previous).show();
//                    $($this.buttons1.previous).show();
                }
            }
            if (($this.steps.length-1) <= $this.curStepIdx){
                $($this.buttons.next).addClass("buttonDisabled");
//                $($this.buttons1.next).addClass("buttonDisabled");
                if ($this.options.hideButtonsOnDisabled) {
                    $($this.buttons.next).hide();
//                    $($this.buttons1.next).hide();
                }
            }else{
                $($this.buttons.next).removeClass("buttonDisabled");
//                $($this.buttons1.next).removeClass("buttonDisabled");
                if ($this.options.hideButtonsOnDisabled) {
                    $($this.buttons.next).show();
//                    $($this.buttons1.next).show();
                }
            }
        }
        // Finish Button
        $this.enableFinish($this.options.enableFinishButton);
    };

    /*
     * Public methods
     */

    SmartWizard.prototype.goForward = function(){
    	//alert('forw');
        var nextStepIdx = this.curStepIdx + 1;
        if (this.steps.length <= nextStepIdx){
            if (! this.options.cycleSteps){
                return false;
            }
            nextStepIdx = 0;
        }
        _loadContent(this, nextStepIdx);
//        $('#aa'+nextStepIdx).fixedHeaderTable({
//        	 id:  nextStepIdx
//    		
//        }); 
//   
//        var $tableBodyCell = $('#fht-tbody'+nextStepIdx+' table tbody tr:first td');
//	    var $headerCell = $('#fht-thead'+nextStepIdx+' thead tr:last th');
//	    var $headerCell1 = $('#fht-thead'+nextStepIdx+' thead tr:nth-child(2) th');
//	    
//	    var arr = [];
//
//	    $headerCell1.each(function() {
//	        var $this=$(this);
//	        var colWidth = $this.width();
//	      //  alert(colWidth);
//	        arr.push(colWidth);
//	        //alert(arr);
//	    });
//
//	  
//	    $tableBodyCell.each(function(index){
//	        
//	    	if (index == 0 || index == 1)
//	    		$headerCell1.eq(index).width($(this).width());
//	    		
//	        if (index > 1 && index < $headerCell.length )
//	        	$headerCell.eq(index-2).width($(this).width());
//	        
//	     if (index == $headerCell.length ){
//	    	// alert(arr[$headerCell1.length-1] + '   ' + $(this).width());
//	        	//alert('==');
//	    if (arr[$headerCell1.length-1] > $(this).width()){
//	    //	alert('head >');
//	        	$headerCell1.eq($headerCell1.length).width($(this).width());
//	    }
//	    else {$(this).width(arr[$headerCell1.length]);
//	 //   alert('head <');
//	    }
//	    }
//	        
//	    });
	    
	  //  tableBodyCell1.deleteTHead();
        $('#aa'+nextStepIdx).show();
    };

    SmartWizard.prototype.goBackward = function(){
//    	alert('goB');
//    	alert(this.curStepIdx);
    	
        var nextStepIdx = this.curStepIdx-1;
        if (0 > nextStepIdx){
            if (! this.options.cycleSteps){
                return false;
            }
            nextStepIdx = this.steps.length - 1;
        }
        _loadContent(this, nextStepIdx);
//        alert(nextStepIdx);
//        $('#aa'+nextStepIdx).fixedHeaderTable({
//       	 id:  nextStepIdx
//   		
//      }); 
        
//        var $tableBodyCell = $('#fht-tbody'+nextStepIdx+' table tbody tr:first td');
//	    var $headerCell = $('#fht-thead'+nextStepIdx+' thead tr:last th');
//	    var $headerCell1 = $('#fht-thead'+nextStepIdx+' thead tr:nth-child(2) th');
//	    
//	    var arr = [];
//
//	    $headerCell1.each(function() {
//	        var $this=$(this);
//	        var colWidth = $this.width();
//	      //  alert(colWidth);
//	        arr.push(colWidth);
//	        //alert(arr);
//	    });
//
//	  
//	    $tableBodyCell.each(function(index){
//	        
//	    	if (index == 0 || index == 1)
//	    		$headerCell1.eq(index).width($(this).width());
//	    		
//	        if (index > 1 && index < $headerCell.length )
//	        	$headerCell.eq(index-2).width($(this).width());
//	        
//	     if (index == $headerCell.length ){
//	    	// alert(arr[$headerCell1.length-1] + '   ' + $(this).width());
//	        	//alert('==');
//	    if (arr[$headerCell1.length-1] > $(this).width()){
//	    //	alert('head >');
//	        	$headerCell1.eq($headerCell1.length).width($(this).width());
//	    }
//	    else {$(this).width(arr[$headerCell1.length]);
//	 //   alert('head <');
//	    }
//	    }
//	        
//	    });
    };

    SmartWizard.prototype.goToStep = function(stepNum){
        var stepIdx = stepNum - 1;
        if (stepIdx >= 0 && stepIdx < this.steps.length) {
            _loadContent(this, stepIdx);
        }
    };
    SmartWizard.prototype.enableStep = function(stepNum) {
        var stepIdx = stepNum - 1;
        if (stepIdx == this.curStepIdx || stepIdx < 0 || stepIdx >= this.steps.length) {
            return false;
        }
        var step = this.steps.eq(stepIdx);
        $(step, this.target).attr("isDone",1);
        $(step, this.target).removeClass("disabled").removeClass("selected").addClass("done");
    }
    SmartWizard.prototype.disableStep = function(stepNum) {
        var stepIdx = stepNum - 1;
        if (stepIdx == this.curStepIdx || stepIdx < 0 || stepIdx >= this.steps.length) {
            return false;
        }
        var step = this.steps.eq(stepIdx);
        $(step, this.target).attr("isDone",0);
        $(step, this.target).removeClass("done").removeClass("selected").addClass("disabled");
    }
    SmartWizard.prototype.currentStep = function() {
        return this.curStepIdx + 1;
    }

    SmartWizard.prototype.showMessage = function (msg) {
        $('.content', this.msgBox).html(msg);
        this.msgBox.show();
    }

    SmartWizard.prototype.enableFinish = function (enable) {
        // Controll status of finish button dynamically
        // just call this with status you want
        this.options.enableFinishButton = enable;
        if (this.options.includeFinishButton){
            if (!this.steps.hasClass('disabled') || this.options.enableFinishButton){
                $(this.buttons.finish).removeClass("buttonDisabled");
                if (this.options.hideButtonsOnDisabled) {
                    $(this.buttons.finish).show();
                }
            }else{
                $(this.buttons.finish).addClass("buttonDisabled");
                if (this.options.hideButtonsOnDisabled) {
                    $(this.buttons.finish).hide();
                }
            }
        }
        return this.options.enableFinishButton;
    }

    SmartWizard.prototype.hideMessage = function () {
        this.msgBox.fadeOut("normal");
    }
    SmartWizard.prototype.showError = function(stepnum) {
        this.setError(stepnum, true);
    }
    SmartWizard.prototype.hideError = function(stepnum) {
        this.setError(stepnum, false);
    }
    SmartWizard.prototype.setError = function(stepnum,iserror) {
        if (typeof stepnum == "object") {
            iserror = stepnum.iserror;
            stepnum = stepnum.stepnum;
        }

        if (iserror){
            $(this.steps.eq(stepnum-1), this.target).addClass('error')
        }else{
            $(this.steps.eq(stepnum-1), this.target).removeClass("error");
        }
    }

    SmartWizard.prototype.fixHeight = function(){
        var height = 0;

        var selStep = this.steps.eq(this.curStepIdx);
        var stepContainer = _step(this, selStep);
        stepContainer.children().each(function() {
            if($(this).is(':visible')) {
                 height += $(this).outerHeight();
            }
        });

        // These values (5 and 20) are experimentally chosen.
        stepContainer.height(height + 5);
        this.elmStepContainer.height(height + 20);
    }

    _init(this);
    
   // alert('unload');
    
//    $('#aa'+this.curStepIdx).fixedHeaderTable({
//    	id:  this.curStepIdx
//		
//    }); 
//    
//  
//    var $tableBodyCell = $('#fht-tbody'+this.curStepIdx+' table tbody tr:first td');
//    
//    var $headerCell = $('#fht-thead'+this.curStepIdx+' thead tr:last th');
//    var $headerCell1 = $('#fht-thead'+this.curStepIdx+' thead tr:nth-child(2) th');
//
//    
//
//    $tableBodyCell.each(function(index){
//    	if (index == 0 || index == 1)
//    		$headerCell1.eq(index).width($(this).width());
//    		
//        if (index > 1 && index < $headerCell.length )
//        	$headerCell.eq(index-2).width($(this).width());
//        
//        if (index == $headerCell.length ){
//        	alert('==');
//        	$headerCell1.eq($headerCell1.length).width($(this).width());
//        }
//    });
    loadModalWindowUnlock();
   
};



(function($){

    $.fn.smartWizard = function(method) {
        var args = arguments;
        var rv = undefined;
        var allObjs = this.each(function() {
            var wiz = $(this).data('smartWizard');
            if (typeof method == 'object' || ! method || ! wiz) {
                var options = $.extend({}, $.fn.smartWizard.defaults, method || {});
                if (! wiz) {
                    wiz = new SmartWizard($(this), options);
                    $(this).data('smartWizard', wiz);
                }
            } else {
                if (typeof SmartWizard.prototype[method] == "function") {
                    rv = SmartWizard.prototype[method].apply(wiz, Array.prototype.slice.call(args, 1));
                    return rv;
                } else {
                    $.error('Method ' + method + ' does not exist on jQuery.smartWizard');
                }
            }
        });
        if (rv === undefined) {
            return allObjs;
        } else {
            return rv;
        }
    };

// Default Properties and Events
    $.fn.smartWizard.defaults = {
        selected: 0,  // Selected Step, 0 = first step
        keyNavigation: true, // Enable/Disable key navigation(left and right keys are used if enabled)
        enableAllSteps: false,
        transitionEffect: 'fade', // Effect on navigation, none/fade/slide/slideleft
        contentURL:null, // content url, Enables Ajax content loading
        contentCache:true, // cache step contents, if false content is fetched always from ajax url
        cycleSteps: false, // cycle step navigation
        enableFinishButton: false, // make finish button enabled always
        hideButtonsOnDisabled: false, // when the previous/next/finish buttons are disabled, hide them instead?
        errorSteps:[],    // Array Steps with errors
        labelNext:'>>',
        labelPrevious:'<<',
        labelFinish:'Salva',
        noForwardJumping: false,
        ajaxType: "POST",
        onLeaveStep: null, // triggers when leaving a step
        onShowStep: null,  // triggers when showing a step
        onFinish: null,  // triggers when Finish button is clicked
        includeFinishButton : true   // Add the finish button
    };
    
    
    

})(jQuery);
