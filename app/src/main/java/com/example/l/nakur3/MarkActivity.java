package com.example.l.nakur3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
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

    View.OnClickListener onClickAddMark(final Context context){
        return (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Добавление");
                // создаем view из dialog.xml
                View view1 = (LinearLayout) getLayoutInflater()
                        .inflate(R.layout.dialog_mark, null);
                // устанавливаем ее, как содержимое тела диалога
                adb.setView(view1);

                final EditText markInput = (EditText) view1.findViewById(R.id.eNameMark);

                adb
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Вводим текст и отображаем в строке ввода на основном экране:
                                        try {
                                            Date cur_date = new Date();
                                            Mark mark = new Mark();
                                            mark.setName(markInput.getText().toString());
                                            mark.setAddDate(new Date());
                                            mark.setStatus("N");
                                            mark.setSynDate(null);
                                            mark.setNote("Добавлено с смартфона");
                                            markDao.create(mark);

                                        } catch (Exception e){
                                            Log.showError(context, e);
                                        }

                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                adb.show();
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
