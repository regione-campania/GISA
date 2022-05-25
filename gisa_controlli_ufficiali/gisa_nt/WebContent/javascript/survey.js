/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function popItems(qid,items){
    popURL('CampaignManagerSurvey.do?command=ViewItems&questionid='+qid+'&items='+items,'Item_List','540','250','yes','yes');
}

function processButton(formName, typeSelected){
  if(typeSelected == 4){
    document.forms[formName].itemsButton.disabled = false;
  }else{
    document.forms[formName].itemsButton.disabled = true;
  }
}

function editQuestion(questionid){
  document.forms['survey'].questionid.value = questionid;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=Modify&auto-populate=true&pg=2' ;
  document.forms['survey'].submit();
}

function delQuestion(questionid){
  if (confirm('Are you sure?')) {
  document.forms['survey'].questionid.value = questionid ;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=DeleteQuestion&pg=1' ;
  document.forms['survey'].submit();
  }
}

function moveQuestion(questionid,direction){
  document.forms['survey'].questionid.value = questionid;
  document.forms['survey'].action = 'CampaignManagerSurvey.do?command=MoveQuestion&direction=' + direction + '&pg=1' ;
  document.forms['survey'].submit();
}


function setActionSubmit(actionURL){
  document.forms['survey'].action = actionURL;
  document.forms['survey'].submit();
}


