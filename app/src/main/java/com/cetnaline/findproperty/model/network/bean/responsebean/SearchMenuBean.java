package com.cetnaline.findproperty.model.network.bean.responsebean;

import com.cetnaline.findproperty.model.database.entity.DropMenu;

import java.util.List;

public class SearchMenuBean {
    private String Name;
    private List<DropMenu> SearchDataItemList;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<DropMenu> getSearchDataItemList() {
        return SearchDataItemList;
    }

    public void setSearchDataItemList(List<DropMenu> searchDataItemList) {
        SearchDataItemList = searchDataItemList;
    }
}
