package es.project.adriapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private static final int CODE1 =1 ;
    private static final int CODE2 = 2;
    private ListView lista;
    private List<Aplicacion> listaAplicacion = new ArrayList<>();
    private AdaptadorAplicaciones adapter;
    private Integer id;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(HomePage.this, AddEditActivity.class);
                startActivityForResult(i, CODE1);
            }
        });

        lista=(ListView)findViewById(R.id.lista);

        cargarDatos();
        adapter = new AdaptadorAplicaciones(this);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        LayoutInflater inflate = getLayoutInflater();
        View item = inflate.inflate(R.layout.header, null);
        menu.setHeaderView(item);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        id = info.position;
        switch (item.getItemId())
        {
            case R.id.opcDel:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.deleteDialogTitle));


                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listaAplicacion.remove(info.position);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomePage.this, getString(R.string.NotDeleted), Toast.LENGTH_SHORT);
                    }
                });
               AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.opcEdit:
                Aplicacion app = listaAplicacion.get(info.position);
                Intent i = new Intent(HomePage.this, AddEditActivity.class);
                i.putExtra("aplicacion", app);
                startActivityForResult(i, CODE2);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_del_all) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.tituloDialogoAll));

            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listaAplicacion.clear();
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
        else if (id == R.id.action_logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.deleteSession));
            sp = getSharedPreferences("datos",MODE_PRIVATE);

            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sp.getAll().clear();
                    finish();
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CODE1:
                if (resultCode == RESULT_OK) {
                    Aplicacion app = (Aplicacion) data.getSerializableExtra("aplicacion");
                    listaAplicacion.add(app);
                    app.setId(listaAplicacion.size()+1);
                    adapter.notifyDataSetChanged();

                }
                break;
            case CODE2:
                if (resultCode == RESULT_OK)
                {
                    Aplicacion app = (Aplicacion) data.getSerializableExtra("aplicacion");
                    listaAplicacion.set(id , app);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void cargarDatos() {
    listaAplicacion.add(new Aplicacion(1,getString(R.string.RunningApp),0.0,112,getString(R.string.RunningAppDesc), 1));
    listaAplicacion.add(new Aplicacion(2,getString(R.string.RecipesApp),1.0,80,getString(R.string.RecipesAppDesc), 3));
    listaAplicacion.add(new Aplicacion(3,getString(R.string.MesaggesApp),0.5,39,getString(R.string.MessaggesAppDesc), 2));
    listaAplicacion.add(new Aplicacion(4,getString(R.string.WeatherApp),0.99,84,getString(R.string.WeatherDesc), 3));
    listaAplicacion.add(new Aplicacion(5,getString(R.string.SyncPcApp),0.99,178,getString(R.string.SyncPcDesc), 3));
    listaAplicacion.add(new Aplicacion(6,getString(R.string.SaveThePizza),1.0,80,getString(R.string.SaveThePizzaDesc), 0));
        listaAplicacion.add(new Aplicacion(7,getString(R.string.RunningApp),0.0,112,getString(R.string.RunningAppDesc), 1));
        listaAplicacion.add(new Aplicacion(8,getString(R.string.RecipesApp),1.0,80,getString(R.string.RecipesAppDesc), 3));
        listaAplicacion.add(new Aplicacion(9,getString(R.string.MesaggesApp),0.5,39,getString(R.string.MessaggesAppDesc), 2));
        listaAplicacion.add(new Aplicacion(10,getString(R.string.WeatherApp),0.99,84,getString(R.string.WeatherDesc), 3));
        listaAplicacion.add(new Aplicacion(11,getString(R.string.SyncPcApp),0.99,178,getString(R.string.SyncPcDesc), 3));
        listaAplicacion.add(new Aplicacion(12,getString(R.string.SaveThePizza),1.0,80,getString(R.string.SaveThePizzaDesc), 0));
    }



    /* Types
    * 0 Games
    * 1 Health and fitness
    * 2 Social
    * 3 Files
    * */
    class AdaptadorAplicaciones extends ArrayAdapter{

        Activity context;

        public AdaptadorAplicaciones(Activity context) {
            super(context, R.layout.row, listaAplicacion);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;

            ViewHolder holder;
            if (item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.row, null);
                holder = new ViewHolder();
                holder.txtNombre =(TextView)item.findViewById(R.id.txtNombre);
                holder.txtPrecio =(TextView)item.findViewById(R.id.txtPrecio);
                holder.txtPeso =(TextView)item.findViewById(R.id.txtPeso);
                holder.txtDesc =(TextView)item.findViewById(R.id.txtDesc);
                holder.imagenApp =(ImageView)item.findViewById(R.id.imagenApp);
                item.setTag(holder);
            }

            else{
                holder = (ViewHolder) item.getTag();
            }
            holder.txtNombre.setText(listaAplicacion.get(position).getNombre());
            holder.txtPrecio.setText(String.valueOf(listaAplicacion.get(position).getPrecio()));
            holder.txtPeso.setText(String.valueOf(listaAplicacion.get(position).getPeso()));
            holder.txtDesc.setText(listaAplicacion.get(position).getDescripcion());


                switch (listaAplicacion.get(position).getTipo()) {
                    case 0:
                        holder.imagenApp.setBackgroundResource(R.mipmap.ic_games);
                        break;
                    case 1:
                        holder.imagenApp.setBackgroundResource(R.mipmap.ic_fitness);
                        break;
                    case 2:
                        holder.imagenApp.setBackgroundResource(R.mipmap.ic_social);
                        break;
                    case 3:
                        holder.imagenApp.setBackgroundResource(R.mipmap.ic_file);
                        break;
                }
            return item;
        }
    }

    static class ViewHolder {
        TextView txtNombre ;
        TextView txtPrecio ;
        TextView txtPeso ;
        TextView txtDesc ;
        ImageView imagenApp;
    }
}



