package com.example.teoriinformasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teoriinformasi.Adapter.AdapterList;
import com.example.teoriinformasi.Adapter.Request;
import com.example.teoriinformasi.SharedPreferences.SharePreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText symbol, binary;
    private TextView jumlah, panjang, delete, prefix, uniquely;
    private Button simpan;
    SharePreferences sessions;
    private DatabaseReference mDatabase;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    ArrayList<Request> list;
    AdapterList adapterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        symbol = findViewById(R.id.masukkansymbol);
        binary = findViewById(R.id.masukkanbinary);
        jumlah = findViewById(R.id.jumlah);
        simpan = findViewById(R.id.butonsimpan);
        panjang = findViewById(R.id.pilihpanjang);
        delete = findViewById(R.id.delete);
        prefix = findViewById(R.id.pilihprefix);
        uniquely = findViewById(R.id.pilihuniquely);
        sessions = new SharePreferences(MainActivity.this.getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler= (RecyclerView) findViewById(R.id.recycle);
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        list = new ArrayList<Request>();

        Simpan();
        daa();
        panjang();
        deletee();
        prefix();
        uniquely();
    }
    private void Simpan(){
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lengths = binary.length();
                jumlah.setText(String.valueOf(lengths));
                jumlah.getText().toString();
                double bawah = Math.pow(2, lengths);
                double atas = 1/bawah;
                String d = String.valueOf(atas);
                String a = symbol.getText().toString();
                String b = binary.getText().toString();
                String c =  jumlah.getText().toString();
               dataupload(new Request(a, b, c, d));
               symbol.setText("");
               binary.setText("");
               jumlah.setText("");
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void dataupload(Request request){
        mDatabase.child("Data").child(symbol.getText().toString()).setValue(request).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
    private void daa(){

        mDatabase.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Request request = dataSnapshot1.getValue(Request.class);
                    list.add(request);
                }
                adapterList = new AdapterList(MainActivity.this,list);
                mRecycler.setAdapter(adapterList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void panjang(){
    panjang.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mDatabase.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double total = 0.0;
                Double count = 0.0;
                Double averange = 0.0;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Request request = dataSnapshot1.getValue(Request.class);
                    Double jumlah = Double.valueOf(request.getJumlah());
                    total = (total + jumlah);
                    count = count + 1;
                    averange = total/count;
                    list.add(request);

                }
                adapterList = new AdapterList(MainActivity.this,list);
                mRecycler.setAdapter(adapterList);
                Log.d("TAG", averange +"");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Hasil");
                alertDialogBuilder.setMessage("Jumlah Codeword yang dihasilkan adalah " + " " + total + "."+" "+ "Sedangkan rata-rata panjang Codeword yang dihasilkan adalah" + " " + averange)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent i = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            });
        }
        });
    }
    private void deletee(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Data").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
    private void prefix(){
        prefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Data").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Double total = 0.0;
                        String output = null;
                        String output1 = null;
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Request request = dataSnapshot1.getValue(Request.class);
                            Double jumlah = Double.valueOf(request.getKraft());
                            total = (total + jumlah);
                            if (total <= 1){
                                output = "'Prefix Free Codes'";
                                output1 = "dan"+"'Uniquely Decodable'";
                            }else {
                                output = "'No Prefix Codes'";
                                output1 = "";
                            }
                            list.add(request);

                        }
                        adapterList = new AdapterList(MainActivity.this,list);
                        mRecycler.setAdapter(adapterList);
                        Log.d("TAG", total +"");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Hasil");
                        alertDialogBuilder.setMessage("Hasil jumlah perhitungan adalah " + " " + total + "."+" "+ "Sehingga codeword tersebut adalah" + " " + output+ " "+output1)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void uniquely(){
        uniquely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Data").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Double total = 0.0;
                        String output = null;
                        String output1 = null;
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Request request = dataSnapshot1.getValue(Request.class);
                            Double jumlah = Double.valueOf(request.getKraft());
                            total = (total + jumlah);
                            if (total <= 1){
                                output = "'Uniquely Decodable'";
                            }else {
                                output = "'No Uniquely Decodable'";
                            }
                            list.add(request);

                        }
                        adapterList = new AdapterList(MainActivity.this,list);
                        mRecycler.setAdapter(adapterList);
                        Log.d("TAG", total +"");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Hasil");
                        alertDialogBuilder.setMessage("Hasil jumlah perhitungan adalah " + " " + total + "."+" "+ "Sehingga codeword tersebut adalah" + " " + output)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
