package com.bai.greendaodemo.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "STUDENT",createInDb = false)
public class Text {
    @Id
    @Property(nameInDb = "ID")
    private Long ID;

//    @Property(nameInDb = "CODE")
//    private String CODE;

    @Property(nameInDb = "NAME")
    private String NAME;

    @Property(nameInDb = "AGE")
    private int AGE;

    @Property(nameInDb = "OTHER1")
    private String OTHER1;

    @Property(nameInDb = "OTHER2")
    private String OTHER2;

    @Generated(hash = 1654227205)
    public Text(Long ID, String NAME, int AGE, String OTHER1, String OTHER2) {
        this.ID = ID;
        this.NAME = NAME;
        this.AGE = AGE;
        this.OTHER1 = OTHER1;
        this.OTHER2 = OTHER2;
    }

    @Generated(hash = 1870502663)
    public Text() {
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

    public int getAGE() {
        return this.AGE;
    }

    public void setAGE(int AGE) {
        this.AGE = AGE;
    }

    public String getOTHER1() {
        return this.OTHER1;
    }

    public void setOTHER1(String OTHER1) {
        this.OTHER1 = OTHER1;
    }

    public String getOTHER2() {
        return this.OTHER2;
    }

    public void setOTHER2(String OTHER2) {
        this.OTHER2 = OTHER2;
    }



    

    

    
}
