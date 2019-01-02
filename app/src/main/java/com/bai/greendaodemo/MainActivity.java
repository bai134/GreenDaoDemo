package com.bai.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bai.greendaodemo.application.GDApplication;
import com.bai.greendaodemo.db.bean.Text;
import com.bai.greendaodemo.db.bean.TextClass;
import com.bai.greendaodemo.db.gen.TextClassDao;
import com.bai.greendaodemo.db.gen.TextDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_code;
    private Button bu_select;

    private TextDao textDao;
    private List<Text> text;
    private List<TextClass> textClasses;

    private TextClass textClass;
    private TextClassDao textClassDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDao = GDApplication.getInstance().getMaindaoSession().getTextDao();
        textClassDao = GDApplication.getInstance().getMaindaoSession().getTextClassDao();
        initData();
        text = textDao.queryBuilder().where(TextDao.Properties.ID.eq(0)).list();
        tv_name = findViewById(R.id.tv_name);
        tv_code = findViewById(R.id.tv_code);
        bu_select = findViewById(R.id.bu_select);
        bu_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textClasses = textClassDao.queryBuilder().where(TextClassDao.Properties.CODE.eq("001")).list();
                tv_code.setText(String.valueOf(textClasses.size()));
            }
        });
        tv_name.setText(text.get(0).getNAME());

    }

    private void initData() {
        textClass = new TextClass();
        textClass.setCODE("001");
        textClass.setNAME("一班");
        textClass.setNUMBER("53");
        textClass.setOTHER1("other1");
        textClassDao.insert(textClass);
    }
}
