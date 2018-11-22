package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "centanet_drop_menu"
)
public class DropMenu {
    private Integer type;
    private Integer ID;
    private String Value;
    private String Text;
    private String Name;
    private String Para;
    private String key;

    @Generated(hash = 95796022)
    public DropMenu(Integer type, Integer ID, String Value, String Text,
            String Name, String Para, String key) {
        this.type = type;
        this.ID = ID;
        this.Value = Value;
        this.Text = Text;
        this.Name = Name;
        this.Para = Para;
        this.key = key;
    }

    @Generated(hash = 692340790)
    public DropMenu() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPara() {
        return Para;
    }

    public void setPara(String para) {
        Para = para;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
