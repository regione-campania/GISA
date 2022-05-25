/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.controller.base;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TaherT
 */
public class DataClass {

    List<Technology> techList = new ArrayList<Technology>();

    public List<Technology> getTechList() {
        if(techList.isEmpty()){            
            techList.add(new Technology(1, 0,"Java"));
            techList.add(new Technology(0, 1,"J2SE"));
            techList.add(new Technology(0, 1,"J2EE"));
            techList.add(new Technology(0, 1,"J2ME"));
            techList.add(new Technology(1, 0,".NET"));
            techList.add(new Technology(0, 1,"Sliverlight"));
            techList.add(new Technology(1, 0,"PHP"));
            techList.add(new Technology(0, 1,"Zend"));
            techList.add(new Technology(0, 1,"Cake"));
        }
        return techList;
    }
    

    public void setTechList(List<Technology> techList) {
        this.techList = techList;
    }

    public class Technology {

        private int Id;
        private int pId;
        private String techName;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getpId() {
            return pId;
        }

        public void setpId(int pId) {
            this.pId = pId;
        }

        public String getTechName() {
            return techName;
        }

        public void setTechName(String techName) {
            this.techName = techName;
        }

        public Technology(int Id, int pId, String techName) {
            this.Id = Id;
            this.pId = pId;
            this.techName = techName;
        }
    }
}
