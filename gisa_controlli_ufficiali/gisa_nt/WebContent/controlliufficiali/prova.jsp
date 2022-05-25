<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<a href="#" style="display:none;visibility:hidden;" 
   onclick="return false" ID="dummyLink" runat="server">dummyasdasda</a>

   <ajaxToolkit:ModalPopupExtender ID="MyMPE" runat="server"
    TargetControlID="dummyLink"
    PopupControlID="EditorPanel"
    BackgroundCssClass="modalBackground" 
    DropShadow="true" 
    BehaviourID="MyMPE"
    OkControlID="OkButton"
    CancelControlID="CancelButton"> 
</ajaxToolkit:ModalPopupExtender>
   <script>
   $find('MyMPE').show();
   
   </script>