package es.project.adriapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPass;
    private EditText edtName;
    private Button btnSend;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("datos", MODE_PRIVATE);


        edtPass = (EditText)findViewById(R.id.edtPass);
        edtName = (EditText)findViewById(R.id.edtName);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        if (sp.contains("user"))
        {
            edtName.setText(sp.getString("user",""));
            edtPass.setText(sp.getString("password",""));
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", edtName.getText().toString());
        editor.putString("password", edtPass.getText().toString());
        editor.commit();
        Intent i = new Intent(MainActivity.this, HomePage.class);
        edtPass.setText("");
        edtName.setText("");
        startActivity(i);
    }
}
