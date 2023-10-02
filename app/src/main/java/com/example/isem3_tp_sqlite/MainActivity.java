package com.example.isem3_tp_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btnRegister;
    Button btnConnexion;
    EditText txtLogin;
    EditText txtMp;
    TextView txtMessage;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("mabase",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(userID INT,login VARCHAR, mp VARCHAR);");
        SQLiteStatement s = db.compileStatement("select count(*) from users;");
        long count = s.simpleQueryForLong();
        if (count==0) {
            db.execSQL("insert into users values (1,'admin','123456');");
            db.execSQL("insert into users values (2,'toto','mptoto');");

            txtLogin = (EditText) findViewById(R.id.txtLogin);
            txtMp = (EditText) findViewById(R.id.txtMp);
            txtMessage = (TextView) findViewById(R.id.txtMessage);
            btnConnexion = (Button) findViewById(R.id.btnConnexion);
            btnConnexion.setOnClickListener(new View.OnClickListener() {
// clear the text boxes
                @Override
                public void onClick(View v) {
                    String loginStr = txtLogin.getText().toString();
                    String mpStr = txtMp.getText().toString();
//récupérer le mp de la base de données
                    Cursor a= db.rawQuery("select mp from users where login ='" + loginStr +"'", null);
                            a.moveToFirst();

                    String mp= a.getString(0);
                    if (mpStr.equals(mp)) {
                        txtMessage.setText("Succès" + ' ' +
                                loginStr.toString());
                        Intent intent = new
                                Intent(MainActivity.this,WelcomeActivity.class);
                        startActivity(intent);
                    } else {
                        txtLogin.setText("");
                        txtMp.setText("");
                        txtMessage.setText("Echec de connexion");
                    }
                }
            });

        }
        btnRegister = (ImageButton) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            // clear the text boxes
            @Override
            public void onClick(View v) {
                Intent i = new
                        Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}