package com.example.pr_17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbHelp;
    EditText anEd;
    EditText nameEd;
    EditText sizeEd;
    EditText weigEd;

    Button btnAdd,btnRead,btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelp = new DBHelper(this);

        anEd = (EditText)findViewById(R.id.ed_animal);
        nameEd = (EditText)findViewById(R.id.ed_name);
        sizeEd = (EditText)findViewById(R.id.ed_size);
        weigEd = (EditText)findViewById(R.id.ed_weight);

        btnAdd=findViewById(R.id.btn_add);
        btnRead=findViewById(R.id.btn_show);
        btnClear=findViewById(R.id.btn_clear);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        SQLiteDatabase db = dbHelp.getWritableDatabase();


        if(view.getId()==R.id.btn_add) {
                ContentValues cv = new ContentValues();

                // получаемданныеизполейввода
                String animal = anEd.getText().toString();
                String name = nameEd.getText().toString();
                int size = Integer.parseInt(sizeEd.getText().toString());
                int weight = Integer.parseInt(weigEd.getText().toString());



                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("animal", animal);
                cv.put("name", name);
                cv.put("size", size);
                cv.put("weight", weight);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d("tag", "row inserted, ID = " + rowID);

        }

        if(view.getId()==R.id.btn_show) {
            Log.d("rag", "--- Rows in mytable: ---");
            // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.query("mytable", null, null, null, null, null, null);

            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("id");
                int animalColIndex = c.getColumnIndex("animal");
                int nameColIndex = c.getColumnIndex("name");
                int sizelColIndex = c.getColumnIndex("size");
                int weightColIndex = c.getColumnIndex("weight");

                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("tag",
                            "ID = " + c.getInt(idColIndex) +
                                    ", animal = " + c.getString(animalColIndex) +
                                    ", name = " + c.getString(nameColIndex) +
                                    ", size = " + c.getString(sizelColIndex) +
                                    ", weight = " + c.getString(weightColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("tag", "0 rows");
            c.close();

        }

        if(view.getId()==R.id.btn_clear) {
            Log.d("tag", "--- Clear mytable: ---");
            // удаляемвсезаписи
            int clearCount = db.delete("mytable", null, null);
            Log.d("tag", "deleted rows count = " + clearCount);

        }

        dbHelp.close();
    }



}
