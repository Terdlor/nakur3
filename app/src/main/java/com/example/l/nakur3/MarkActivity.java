package com.example.l.nakur3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.l.nakur3.database.HelperFactory;
import com.example.l.nakur3.database.Log;
import com.example.l.nakur3.database.Mark;
import com.example.l.nakur3.database.MarkDAO;

import java.util.*;

/**
 * Created by L on 15.08.2017.
 */
public class MarkActivity extends AppCompatActivity {

    ListView listMark;
    Button btAddMark;
    List<Mark> liMark;
    MarkDAO markDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        inizView(this);
        setListView();
    }

    //noooooo
    AdapterView.OnItemClickListener onClickItemListMark(final Context context){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Mark mark = liMark.get(position);
                 Log.showMassage(context, mark.getName());
            }
        };
    }

    View.OnClickListener onClickAddMark(Context context){
        return (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void inizView(Context context){
        try {
            HelperFactory.setHelper(getApplicationContext());

            btAddMark = (Button) findViewById(R.id.btAddMark);
            btAddMark.setOnClickListener(onClickAddMark(context));
            listMark = (ListView) findViewById(R.id.listMark);

            markDao = HelperFactory.getHelper().getMarkDAO();
        } catch (Exception e){
            Log.showError(this, e);
        }
    }

    void setLiMark(){
        try {
            if (liMark != null) {
                liMark.clear();
            } else {
                liMark = new ArrayList<>();
            }

            for (Mark mark : markDao.getAllMark()){
                liMark.add(mark);
            }
        } catch (Exception e){
            Log.showError(this, e);
        }
    }

    void setListView(){
try {
    setLiMark();

    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    HashMap<String, Object> map;

    for (Mark mark : liMark) {
        map = new HashMap<>();
        map.put("ID", String.valueOf(mark.getId()));
        map.put("NAME", mark.getName());
        map.put("SYN_DATE", String.valueOf(mark.getAddDate()));
        String status = mark.getStatus();
        int image;
        map.put("STATUS", status);
        if (status.equals("N")) {
            image = R.drawable.v_green;
        } else {
            image = R.drawable.x_red;
        }
        map.put("IMAGE", image);
        arrayList.add(map);
    }

    SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.single_row,
            new String[]{"ID", "NAME", "SYN_DATE", "IMAGE"},
            new int[]{R.id.id_row, R.id.name_row, R.id.date_row, R.id.imbtStatus});
    listMark.setAdapter(adapter);
    listMark.setOnItemClickListener(onClickItemListMark(this));

} catch (Exception e){
    Log.showError(this, e);
}
    }

    @Override
    public void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
