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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoeloquentequipos.EditEquipo;
import com.example.proyectoeloquentequipos.MainActivity;
import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Repository;
import com.example.proyectoeloquentequipos.R;
import com.example.proyectoeloquentequipos.SingleEquipo;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter <EquipoAdapter.ItemHolder>{

    private LayoutInflater inflater;
    private List<Equipo> equipoList;
    public MainViewModel viewModel;
    private Context miContexto;
    private static final String ID = "Equipo.ID";
    private static final String STORAGE = "/upload/";

    public EquipoAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        miContexto = context;
    }

    @NonNull
    @Override
    public EquipoAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.item_equipo,parent,false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoAdapter.ItemHolder holder, int position) {
        if(equipoList !=null){
            final int posicion = position;
            final Equipo current = equipoList.get(position);
            holder.tvNombre.setText(current.getNombre());
            holder.tvCiudad.setText(current.getCiudad());

            Repository repository = new Repository();
            Glide.with(miContexto)
                    .load(repository.getUrl()+STORAGE+current.getEscudo())
                    .override(500, 500)// prueba de escalado
                    .into(holder.ivImagen);

            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(miContexto, SingleEquipo.class);
                    intent.putExtra(ID, current.getId());
                    miContexto.startActivity(intent);
                }
            });

            viewModel = MainActivity.viewModel;
            holder.btEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(miContexto, EditEquipo.class);
                    intent.putExtra(ID, current.getId());
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
                            viewModel.deleteEquipo(current.getId());
                            notifyItemRemoved(posicion);
                            equipoList.remove(posicion);
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
        }
    }

    @Override
    public int getItemCount() {
        int elements=0;
        if(equipoList !=null){
            elements= equipoList.size();
        }
        return elements;
    }

    public void setData(List<Equipo> equipoList){
        this.equipoList = equipoList;
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombre, tvCiudad;
        private final Button btBorrar, btEditar;
        private ConstraintLayout cl;
        private ImageView ivImagen;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvCiudad=itemView.findViewById(R.id.etCiudad);
            btBorrar=itemView.findViewById(R.id.btBorrar);
            btEditar=itemView.findViewById(R.id.btEditar);
            cl = itemView.findViewById(R.id.cl);
            ivImagen = itemView.findViewById(R.id.ivImagen);
        }
    }
}
