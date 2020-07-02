package com.example.teoriinformasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teoriinformasi.R;
import com.example.teoriinformasi.SharedPreferences.SharePreferences;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ListViewHolder> {
   Context context;
   ArrayList<Request>requests;
   SharePreferences sessions;
   private DatabaseReference mDatabase;

   public AdapterList(Context c, ArrayList<Request> p){
       context = c;
       requests = p;
   }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.symbol.setText(requests.get(position).getSymbol());
        holder.binary.setText(requests.get(position).getBinary());
        holder.jumlah.setText(requests.get(position).getJumlah());
        holder.kraft.setText(requests.get(position).getKraft());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        TextView symbol, binary, jumlah, kraft;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            symbol = (TextView) itemView.findViewById(R.id.isymbol);
            binary = (TextView) itemView.findViewById(R.id.ibinary);
            jumlah = (TextView) itemView.findViewById(R.id.ijumlah);
            kraft = (TextView) itemView.findViewById(R.id.ikraft);
        }
    }
}
