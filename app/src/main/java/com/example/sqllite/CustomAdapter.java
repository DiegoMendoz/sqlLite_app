package com.example.sqllite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;

public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
private Context context;
Button delete_button;

private ArrayList contactos_id;
    private ArrayList contactos_nombre;
    private ArrayList contactos_apellido;
    private ArrayList contactos_telefono;
    private ArrayList contactos_domicilio;
    private ArrayList contactos_correo_electronico;
    private ArrayList contactos_edad;

CustomAdapter(Context context, ArrayList contactos_id, ArrayList contactos_apellido, ArrayList contactos_nombre, ArrayList contactos_telefono,ArrayList contactos_correo_electronico){
        this.context = context;
        this.contactos_id=contactos_id;
         this.contactos_nombre=contactos_nombre;
         this.contactos_apellido=contactos_apellido;
         this.contactos_telefono=contactos_telefono;



    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View view =  inflater.inflate(R.layout.my_row,parent,false);

       return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.contactos_id_txt.setText(String.valueOf(contactos_id.get(position)));
        holder.contactos_id_txt.setText(String.valueOf(contactos_id.get(position)));
        holder.contactos_nombre_txt.setText(String.valueOf(contactos_nombre.get(position)));
        holder.contactos_apellidos_txt.setText(String.valueOf(contactos_apellido.get(position)));
        holder.contactos_telefono_txt.setText(String.valueOf(contactos_telefono.get(position)));


    }


    @Override
    public int getItemCount() {
        return contactos_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView contactos_id_txt,contactos_nombre_txt,contactos_apellidos_txt,contactos_telefono_txt,contactos_correo_electronico_txt;
        Button delete_button;
         public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactos_id_txt=itemView.findViewById(R.id.contactos_id_txt);
            contactos_nombre_txt=itemView.findViewById(R.id.contactos_nombre_txt);
            contactos_apellidos_txt=itemView.findViewById(R.id.contactos_apellidos_txt);
            contactos_telefono_txt=itemView.findViewById(R.id.contactos_telefono_txt);
             delete_button=itemView.findViewById(R.id.delete_button);
             delete_button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
                     builder.setTitle("Confirmación")
                             .setMessage("¿Desea eliminar el dato de " + contactos_nombre.get(getAdapterPosition()) +" "+ contactos_apellido.get(getAdapterPosition()) + " con ID " + contactos_id.get(getAdapterPosition()) + "?")
                             .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {
                                     int position = getAdapterPosition();
                                     int contactId = Integer.parseInt(String.valueOf(contactos_id.get(position)));
                                     CapaBaseDatos myDB = new CapaBaseDatos(context);
                                     myDB.deleteContacto(contactId);

                                     // Elimina el elemento eliminado de las listas utilizadas por el adaptador
                                     contactos_id.remove(position);
                                     contactos_nombre.remove(position);
                                     contactos_apellido.remove(position);
                                     contactos_telefono.remove(position);
                                     notifyDataSetChanged();
                                 }
                             })
                             .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {
                                     dialogInterface.dismiss();
                                 }
                             })
                             .show();
                 }
             });

        }
    }
    void confirmDialog(){

    }
}
