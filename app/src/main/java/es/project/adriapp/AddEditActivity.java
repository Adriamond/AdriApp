package es.project.adriapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEditActivity extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtPrecio;
    private EditText edtPeso;
    private EditText edtDesc;
    private Button btnAdd;
    private Spinner listaTipos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        edtPeso = (EditText) findViewById(R.id.edtPeso);
        edtDesc = (EditText) findViewById(R.id.edtDesc);
        listaTipos = (Spinner) findViewById(R.id.listaTipos);
        btnAdd = (Button) findViewById(R.id.btnAdd  );

        String[] tipos = {getString(R.string.Games), getString(R.string.HealthAndFitness), getString(R.string.Social), getString(R.string.Files)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        listaTipos.setAdapter(adapter);


        final Aplicacion app = (Aplicacion) getIntent().getSerializableExtra("aplicacion");

        if (app!=null)
        {
            edtNombre.setText(String.valueOf(app.getNombre()));
            edtDesc.setText(String.valueOf(app.getDescripcion()));
            edtPrecio.setText(String.valueOf(app.getPrecio()));
            edtPeso.setText(String.valueOf(app.getPeso()));
            listaTipos.setSelection(app.getTipo());
            btnAdd.setText(getResources().getString(R.string.edit));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Aplicacion a = new Aplicacion(null, edtNombre.getText().toString(),
                        Double.parseDouble(edtPrecio.getText().toString()),
                        Integer.parseInt(edtPeso.getText().toString()),
                        edtDesc.getText().toString(),
                        listaTipos.getSelectedItemPosition());

                if (app != null)
                {
                    a.setId(a.getId());
                }
                    Intent i = new Intent();
                i.putExtra("aplicacion",a);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
