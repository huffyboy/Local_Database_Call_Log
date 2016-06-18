package com.example.huff6.localdatabasecalllog;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //LocalSingleton localConnection;
    DatabaseHelper myDb;
    EditText textName, textPhone, textStartTime, textEndTime, textDuration, textNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //localConnection = new LocalSingleton(this);
        myDb = new DatabaseHelper(this);

        textName      = (EditText) findViewById(R.id.editText_name);
        textPhone     = (EditText) findViewById(R.id.editText_phone);
        textStartTime = (EditText) findViewById(R.id.editText_startTime);
        textEndTime   = (EditText) findViewById(R.id.editText_endTime);
        textDuration  = (EditText) findViewById(R.id.editText_duration);
        textNotes     = (EditText) findViewById(R.id.editText_notes);
    }

    public void onClickAddData(View v) {
        //boolean isInserted = myDb.insertData("Dummy", "123");
        //boolean isInserted = localConnection.insertRow(
        boolean isInserted = myDb.insertData(
                textName.getText().toString(),
                textPhone.getText().toString(),
                textStartTime.getText().toString(),
                textEndTime.getText().toString(),
                textDuration.getText().toString(),
                textNotes.getText().toString()
        );

        if (isInserted) {
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickViewData(View v) {
        //Cursor resultCursor = localConnection.getAllRows();
        Cursor resultCursor = myDb.getAllData();
        System.out.println("Before get count");
        if (resultCursor.getCount() == 0) {
            System.out.println("empty list");
            showMessage("Error", "Nothing found");
            return;
        }

        // ELSE //////
        System.out.println("before string builder");
        System.out.println("COUNT IS " + resultCursor.getCount());
        StringBuilder buffer = new StringBuilder();
        while (resultCursor.moveToNext()) {
            System.out.println("inside loop");
            buffer.append("Id       : " ).append(resultCursor.getString(0)).append("\n");
            buffer.append("Name     : " ).append(resultCursor.getString(1)).append("\n");
            buffer.append("Number   : " ).append(resultCursor.getString(2)).append("\n");
            buffer.append("S Time   : " ).append(resultCursor.getString(3)).append("\n");
            buffer.append("E Time   : " ).append(resultCursor.getString(4)).append("\n");
            buffer.append("Duration : " ).append(resultCursor.getString(5)).append("\n");
            buffer.append("Notes    : " ).append(resultCursor.getString(6)).append("\n\n");
        }

        System.out.println("out of loop");

        // show all data
        showMessage("Data", buffer.toString());
    }
    public void onClickUpdateQueue(View v) {
        // DO NOTHING
    }

    public void onClickSyncDatabase(View v) {
        //localConnection.clearContents();
        myDb.deleteAllData();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();
    }
}
