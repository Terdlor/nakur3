package com.example.l.nakur3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.l.nakur3.database.*;

import java.util.*;

/**
 * Created by L on 15.08.2017.
 */
public class FlavorActivity extends AppCompatActivity {

    ListView listFlavor;
    Button btAddFlavor;
    Mark cur_mark;
    List<Flavor> liFlavor;
    MarkDAO markDao;
    FlavorDAO flavorDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        inizView(this);
        setListView(this);
    }

    View.OnClickListener onClickAddFlavor(final Context context){
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
                markInput.setInputType(InputType.TYPE_NULL);
                adb
                        .setCancelable(true)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Вводим текст и отображаем в строке ввода на основном экране:
                                        try {
                                            Date cur_date = new Date();
                                            Flavor flavor = new Flavor();
                                            flavor.setName(markInput.getText().toString());
                                            flavor.setAddDate(new Date());
                                            flavor.setStatus("N");
                                            flavor.setSynDate(null);
                                            flavor.setNote("Добавлено с смартфона");
                                            cur_mark.addFlavorDB(flavor);

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

    View.OnClickListener onClickEditMark(final Context context, final int pos){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Flavor mark = liFlavor.get(pos);//  markDao.getMarkById(position);
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Изменение");
                    // создаем view из dialog.xml
                    View view1 = (LinearLayout) getLayoutInflater()
                            .inflate(R.layout.dialog_mark, null);
                    // устанавливаем ее, как содержимое тела диалога
                    adb.setView(view1);

                    final EditText markInputName = (EditText) view1.findViewById(R.id.eNameMark);
                    markInputName.setText(mark.getName());
                    //markInputName.setInputType(InputType.TYPE_NULL);
                    final EditText markInputNote = (EditText) view1.findViewById(R.id.eNoteMark);
                    markInputNote.setText(mark.getNote());
                   //markInputNote.setInputType(InputType.TYPE_NULL);
                    final TextView dates = (TextView) view1.findViewById(R.id.tDateMark);
                    //dates.setText(mark.getSynDate().toString());
                    adb.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                mark.setName(markInputName.getText().toString());
                                mark.setNote(markInputNote.getText().toString());
                                mark.setStatus("E");
                                markDao.update(cur_mark);
                                //flavorDao.update(mark);
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
                    final Flavor mark =liFlavor.get(pos);// markDao.getMarkById(position);
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Подтверждение");
                    adb.setMessage("Удалить "+mark.getName()+"?");
                    adb.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mark.setStatus("D");
                                flavorDao.update(mark);
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

            cur_mark = HelperFactory.getHelper().getMarkDAO().getMarkById(getIntent().getExtras().getInt("id"));

            btAddFlavor = (Button) findViewById(R.id.btAddMark);
            btAddFlavor.setOnClickListener(onClickAddFlavor(context));
            listFlavor = (ListView) findViewById(R.id.listMark);


        } catch (Exception e){
            Log.showError(this, e);
        }
    }

    void setListView(final Context context) {
        try {
            liFlavor = cur_mark.getFlavorListDB();

            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
            HashMap<String, Object> map;

            for (Flavor mark : liFlavor) {
                map = new HashMap<>();
                map.put("ID", String.valueOf(mark.getId()));
                map.put("NAME", mark.getName());
                map.put("SYN_DATE", Log.getDate(mark.getAddDate()));
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

                    LinearLayout llsr = (LinearLayout) layout.findViewById(R.id.llsin_row);
                    llsr.setOnClickListener(onClickEditMark(context, position));
                    llsr.setOnLongClickListener(onLongClickDeleteMark(context, position));

                    return layout;
                }
            };
            listFlavor.setAdapter(adapter);

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
