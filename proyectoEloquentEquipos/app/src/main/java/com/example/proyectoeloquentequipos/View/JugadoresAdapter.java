package com.example.proyectoeloquentequipos.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoeloquentequipos.EditJugador;
import com.example.proyectoeloquentequipos.MainActivity;
import com.example.proyectoeloquentequipos.Model.Data.Jugadores;
import com.example.proyectoeloquentequipos.Model.Repository;
import com.example.proyectoeloquentequipos.Plantilla;
import com.example.proyectoeloquentequipos.R;
import com.example.proyectoeloquentequipos.SingleJugador;

import java.util.List;

public class JugadoresAdapter extends RecyclerView.Adapter <JugadoresAdapter.ItemHolder>{

    private LayoutInflater inflater;
    private List<Jugadores> jugadoresList;
    public MainViewModel viewModel;
    private Context miContexto;
    private static final String ID = "Jugadores.ID";
    private static final String IDEQUIPO = "Jugadores.IDEQUIPO";
    private static final String STORAGE = "/upload/";
    private long idEquipo;

    public JugadoresAdapter(Context context, long id) {
        inflater=LayoutInflater.from(context);
        miContexto = context;
        idEquipo = id;
    }

    @NonNull
    @Override
    public JugadoresAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.item_jugadores,parent,false);
        return new JugadoresAdapter.ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadoresAdapter.ItemHolder holder, int position) {
        if(jugadoresList !=null){
            final int posicion = position;
            final Jugadores current = jugadoresList.get(position);
            if(current.getIdequipo() == idEquipo){
                holder.tvNombre.setText(current.getNombre());
                holder.tvApellidos.setText(current.getApellidos());

                Repository repository = new Repository();
                Glide.with(miContexto)
                        .load(repository.getUrl()+STORAGE+current.getFoto())
                        .override(500, 500)// prueba de escalado
                        .into(holder.ivImagen);

                holder.cl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(miContexto, SingleJugador.class);
                        intent.putExtra(ID, current.getId());
                        miContexto.startActivity(intent);
                    }
                });

                viewModel = Plantilla.viewModel;
                holder.btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(miContexto, EditJugador.class);
                        intent.putExtra(ID, current.getId());
                        intent.putExtra(IDEQUIPO, idEquipo);
                        miContexto.startActivity(intent);
                    }
                });

                holder.btBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(miContexto);
                        builder.setMessage(R.string.dialogoMensaje)
                                .setTitle(R.string.dialogoTitulo);
                        builder.setPositiveButton(R.string.confirmacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                viewModel.deleteJugador(current.getId());
                                notifyItemRemoved(posicion);
                                jugadoresList.remove(posicion);
                            }
                        });
                        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }else{
                holder.cv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        int elements=0;
        if(jugadoresList !=null){
            elements= jugadoresList.size();
        }
        return elements;
    }

    public void setData(List<Jugadores> jugadoresList){
        this.jugadoresList = jugadoresList;
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombre, tvApellidos;
        private final Button btBorrar, btEditar;
        private ConstraintLayout cl;
        private CardView cv;
        private ImageView ivImagen;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvApellidos=itemView.findViewById(R.id.tvApellidos);
            btBorrar=itemView.findViewById(R.id.btBorrar);
            btEditar=itemView.findViewById(R.id.btEditar);
            cl = itemView.findViewById(R.id.cl);
            cv = itemView.findViewById(R.id.cvContainer);
            ivImagen = itemView.findViewById(R.id.ivImagen);
        }
    }
}
