package com.example.l.nakur3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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
        setListView(this);
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

                                            setListView(context);
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

    View.OnClickListener onClickSendMark(final Context context, final int pos){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.showMassage(context, String.valueOf(pos));
            }
        };
    }

    View.OnClickListener onClickEditMark(final Context context, final int pos){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Mark mark = liMark.get(pos);//  markDao.getMarkById(position);
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Изменение");
                    // создаем view из dialog.xml
                    View view1 = (LinearLayout) getLayoutInflater()
                            .inflate(R.layout.dialog_mark, null);
                    // устанавливаем ее, как содержимое тела диалога
                    adb.setView(view1);

                    final EditText markInput = (EditText) view1.findViewById(R.id.eNameMark);
                    markInput.setText(mark.getName());
                    final TextView dates = (TextView) view1.findViewById(R.id.tDateMark);
                    //dates.setText(mark.getSynDate().toString());
                    adb.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                mark.setName(markInput.getText().toString());
                                mark.setStatus("E");
                                markDao.update(mark);
                                setListView(context);
                            }catch (Exception e){
                                Log.showError(context, e);
                            }
                        }
                    });
                    adb.setNegativeButton("Отмена", null);
                    adb.setCancelable(true);
                    adb.create().show();
                }catch (Exception e){
                    Log.showError(context, e);
                }
            }
        };
    }

    View.OnLongClickListener onLongClickDeleteMark (final Context context, final int pos){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    final Mark mark =liMark.get(pos);// markDao.getMarkById(position);
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Подтверждение");
                    adb.setMessage("Удалить "+mark.getName()+"?");
                    adb.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mark.setStatus("D");
                                markDao.update(mark);
                                setListView(context);
                            } catch (Exception e){
                                Log.showError(context, e);
                            }
                        }
                    });
                    adb.setNegativeButton("Отмена", null);
                    adb.setCancelable(true);
                    adb.create().show();
                } catch (Exception e){
                    Log.showError(context, e);
                }
                return  true;
            }
        };
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

    void setListView(final Context context) {
            try {
                liMark = new ArrayList<>(markDao.getAllMark());

                ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
                HashMap<String, Object> map;

                for (Mark mark : liMark) {
                    map = new HashMap<>();
                    map.put("ID", String.valueOf(mark.getId()));
                    map.put("NAME", mark.getName());
                    map.put("SYN_DATE", String.valueOf(mark.getAddDate()));
                    String status = mark.getStatus();
                    map.put("STATUS", status);
                    switch (status) {
                        case "N":
                            map.put("IMAGE", R.drawable.plus_green);
                            break;
                        case "E":
                            map.put("IMAGE", R.drawable.arr_yell);
                            break;
                        case "D":
                            map.put("IMAGE", R.drawable.x_red);
                            break;
                        case "C":
                            map.put("IMAGE", R.drawable.v_green);
                            break;
                        default:
                            map.put("IMAGE", R.drawable.xz_blue);
                            break;
                    }
                    arrayList.add(map);
                }

            SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.single_row,
                    new String[]{"ID", "NAME", "SYN_DATE", "IMAGE"},
                    new int[]{R.id.id_row, R.id.name_row, R.id.date_row, R.id.imbtStatus})
                {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        final ViewGroup layout = (ViewGroup) super.getView(position, convertView, parent);
                        ImageButton im = (ImageButton) layout.findViewById(R.id.imbtStatus);
                        im.setOnClickListener(onClickSendMark(context, position));

                        LinearLayout llsr = (LinearLayout) layout.findViewById(R.id.llsin_row);
                        llsr.setOnClickListener(onClickEditMark(context, position));
                        llsr.setOnLongClickListener(onLongClickDeleteMark(context, position));

                        return layout;
                    }
                };
                listMark.setAdapter(adapter);

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
