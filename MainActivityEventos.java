package com.example.listadeprodutos;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.listadeprodutos.database.EventoDAO;
public class MainActivity extends AppCompatActivity {
    private ListView lista_Eventos;
    private ArrayAdapter<Evento> adapterEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        lista_Eventos = findViewById(R.id.lista_Eventos);
        onClickListenerListView();
        onClickHoldListenerlistview();  //-----APAGAR ITEM DA LISTA------
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        adapterEvento = new ArrayAdapter<Evento>(MainActivity.this,android.R.layout.simple_list_item_1,eventoDAO.listar());
        lista_Eventos.setAdapter(adapterEvento);
    }

    public void onClickHoldListenerlistview(){
         lista_Eventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoclicado =  adapterEvento.getItem(position);
                exibirConfirmacao(eventoclicado);
                return true;
            }
        });
    }

    public void exibirConfirmacao(final Evento evento) {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity.this);
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setTitle("Excluir");
        msgBox.setMessage("Excluir evento da lista?");
        msgBox.setPositiveButton("EXCLUIR",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventoDAO eventoDAO = new EventoDAO(getBaseContext());
                eventoDAO.deletar(evento);
                adapterEvento.remove(evento);
            }
        });
        msgBox.setCancelable(true);
        AlertDialog alert = msgBox.create();
        alert.show();
    }

    public void onClickListenerListView(){
        lista_Eventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoclicado = adapterEvento.getItem(position);
                Intent intent = new Intent(MainActivity.this,CadastroDeItem.class);
                intent.putExtra("editarEvento",eventoclicado);
                startActivity(intent);
            }
        });
    }
    public void onClickNovoEvento(View v){
        Intent intent = new Intent(MainActivity.this, CadastroDeItem.class);
        startActivity(intent);
    }

}
