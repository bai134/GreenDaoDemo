package com.bai.greendaodemo.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "TEXTCLASS")
public class TextClass {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;
    @Property(nameInDb = "NAME")
    private String NAME;
    @Property(nameInDb = "CODE")
    private String CODE;
    @Property(nameInDb = "NUMBER")
    private String NUMBER;
    @Property(nameInDb = "OTHER1")
    private String OTHER1;
    @Generated(hash = 1309608612)
    public TextClass(Long ID, String NAME, String CODE, String NUMBER,
            String OTHER1) {
        this.ID = ID;
        this.NAME = NAME;
        this.CODE = CODE;
        this.NUMBER = NUMBER;
        this.OTHER1 = OTHER1;
    }
    @Generated(hash = 735233632)
    public TextClass() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getNAME() {
        return this.NAME;
    }
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    public String getCODE() {
        return this.CODE;
    }
    public void setCODE(String CODE) {
        this.CODE = CODE;
    }
    public String getNUMBER() {
        return this.NUMBER;
    }
    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }
    public String getOTHER1() {
        return this.OTHER1;
    }
    public void setOTHER1(String OTHER1) {
        this.OTHER1 = OTHER1;
    }

}
