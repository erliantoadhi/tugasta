package com.example.drawerlayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText noSeri, nmBayi, umur, nmIbu, noHp, tb, bb, gizi;
    DatabaseHelper db;
    private ProgressDialog pg;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StringBuffer bf;
    protected String pingg;
    protected String beratt;
    protected String serii;

    private boolean isFirstOpen = true;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    public String getPingg() {
        return pingg;
    }

    public void setPingg(String pingg) {
        this.pingg = pingg;
    }

    public String getBeratt() {
        return beratt;
    }

    public void setBeratt(String beratt) {
        this.beratt = beratt;
    }

    public String getSerii() {
        return serii;
    }

    public void setSerii(String serii) {
        this.serii = serii;
    }

    private void getFirebase() {
        try {
            System.out.println("cari data");

            //==================================================================================SERI
            DatabaseReference seri = database.getReference("seri");
            seri.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (isFirstOpen) {
                        isFirstOpen = false;
                    } else {
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("onDataChanged", "newData no seri = " + value);
                        noSeri.setText(value);
                        setSerii(value);

                        ArrayList<String> listnoSeri = db.getAllNoSeri();

                        if (db.getAllNoSeri().contains(value)) {
                            try {
                                String nama = db.getNamaBayi(value);
                                String umurbayi = db.getUmurBayi(value);
                                String ibu = db.getIbuBayi(value);
                                String nohp = db.getNoHP(value);

                                nmBayi.setText(nama);
                                umur.setText(umurbayi);
                                nmIbu.setText(ibu);
                                noHp.setText(nohp);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Log.d("dbhelper", "db set Error : " + e.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

            //==================================================================================PING
            DatabaseReference ping = database.getReference("ping");
            ping.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    tb.setText(value);
                    tb.setEnabled(false);
                    setPingg(value);
                    hitStatus();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

            //=============================================================================TIMBANGAN
            DatabaseReference timbangan = database.getReference("timbangan");
            timbangan.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Double berat = Double.parseDouble(value)/1000;
                    bb.setText(""+berat);
                    bb.setEnabled(false);
                    setBeratt(""+berat);
                    hitStatus();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

        } catch (Exception e) {
            System.out.println("Tidak Ada Data");
        }

    }

    private boolean cek() {
        boolean cek = true;
        if (TextUtils.isEmpty(noSeri.getText())) {
            noSeri.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(nmBayi.getText())) {
            nmBayi.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(umur.getText())) {
            umur.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(nmIbu.getText())) {
            nmIbu.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(noHp.getText())) {
            noHp.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(tb.getText())) {
            tb.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(bb.getText())) {
            bb.setError("Tidak boleh kosong");
            cek = false;
        }
        if (TextUtils.isEmpty(gizi.getText())) {
            gizi.setError("Tidak boleh kosong");
            cek = false;
        }

        return cek;
    }

    //==================================================================================popup Simpan
    public void popup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup);

        TextView nama = dialog.findViewById(R.id.tv_popup_namabayi);
        TextView detail = dialog.findViewById(R.id.tv_popup_detail);
        nama.setText(nmBayi.getText());
        detail.setText(
                "Anak dari        *" + nmIbu.getText() + "\n" +
                        "Berumur         *" + umur.getText() + " Bulan\n" +
                        "Tinggi Badan *" + tb.getText() + " cm\n" +
                        "Berat Badan   *" + bb.getText() + " kg\n" +
                        "Gizi                   *" + gizi.getText()
        );

        Button simpan = dialog.findViewById(R.id.popup_save);
        Button cancel = dialog.findViewById(R.id.popup_cancel);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pg.setMessage("Mohon Tunggu");
                    pg.show();
                    if (db.isi(
                            Long.parseLong(String.valueOf(noSeri.getText())),
                            nmBayi.getText().toString(),
                            Integer.parseInt(String.valueOf(umur.getText())),
                            nmIbu.getText().toString(),
                            noHp.getText().toString(),
                            Integer.parseInt(String.valueOf(tb.getText())),
                            Float.parseFloat(String.valueOf(bb.getText())),
                            gizi.getText().toString()
                    )) {
                        pg.dismiss();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        sendWa();
                        reset();
                    } else {
                        pg.dismiss();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    pg.dismiss();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void reset() {
        noSeri.setText("");
        nmBayi.setText("");
        nmIbu.setText("");
        umur.setText("");
        noHp.setText("");
        tb.setText("");
        bb.setText("");
        gizi.setText("");
    }

    private void sendWa() {
        try {
            String semuapesan =
                    "Nama Bayi: "+ nmBayi.getText().toString() + "\n"+
                            "Umur Bayi(bln): "+ umur.getText().toString() + "\n"+
                            "Nama Ibu: "+ nmIbu.getText().toString() + "\n"+
                            "No. HP: "+ noHp.getText().toString() + "\n"+
                            "Tinggi Badan(cm): "+ tb.getText().toString() + "\n"+
                            "Berat Badan(kg): "+ bb.getText().toString() +"\n"+
                            "Status: "+ gizi.getText().toString();

            Intent whatsAppIntent = new Intent("android.intent.action.MAIN");
            whatsAppIntent.setAction(Intent.ACTION_VIEW);
            whatsAppIntent.setPackage("com.whatsapp");
            String url = "https://api.whatsapp.com/send?phone=" + noHp.getText().toString() + "&text=" + semuapesan;
            Log.d("sendwa", "no hp = " + noHp);
            whatsAppIntent.setData(Uri.parse(url));
            startActivity(whatsAppIntent);

            } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Whatsapp not installed.", Toast.LENGTH_SHORT).show();
            }
        }

    private int find(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) return i;
        }
        return -1;
    }

    private void hitStatus() {
        double p=Double.parseDouble(getPingg());
        double b=Double.parseDouble(getBeratt());
        String tempUmur = (umur.getText()).toString();
        int u = 0;

        if(!TextUtils.isEmpty(tempUmur)){
            u = Integer.parseInt(tempUmur);
        }
        System.out.println(tempUmur+" umurmu");
        double imt = b / (p * p * 0.0001);
        String gizi="---";

        int[] umur = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        double[] ambangBawah = { 11, 12.3, 13.6, 14.2, 14.4, 14.6, 14.6, 14.7, 14.6, 14.6, 14.5, 14.4, 14.3 };
        double[] ambangAtas  = { 16.4, 17.9, 19.5, 20.1, 20.4, 20.6, 20.6, 20.6, 20.5, 20.4, 20.2, 20.1, 19.9 };

        int index = this.find(umur, u);
        if (index == -1) {
            gizi="Umur tidak didukung";
        } else if(imt > ambangBawah[index] && imt < ambangAtas[index]){
            gizi="Normal";
        } else if(imt < ambangBawah[index] + 0.1){
            gizi="Kurus";
        } else if(imt > ambangAtas[index] - 0.1){
            gizi="Gemuk";
        }
        this.gizi.setText(gizi);
        System.out.println(getPingg() + "\t" + getBeratt());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        pg = new ProgressDialog(getActivity());
        db = new DatabaseHelper(getContext());
        noSeri = view.findViewById(R.id.edt_noSeri);
        nmBayi = view.findViewById(R.id.edt_namaBayi);
        umur = view.findViewById(R.id.edt_umur);
        nmIbu = view.findViewById(R.id.edt_namaIbu);
        noHp = view.findViewById(R.id.edt_noTelp);
        tb = view.findViewById(R.id.edt_tb);
        bb = view.findViewById(R.id.edt_bb);
        gizi = view.findViewById(R.id.edt_gizi);

        setPingg("0");
        setBeratt("0");
        hitStatus();
        gizi.setText("-");
        view.findViewById(R.id.bt_save).setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != "") {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.getWindow();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        getFirebase();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                if (cek()) {
                    popup();
                } else {
                    Toast.makeText(getActivity(), "Mohon Lengkapi Data", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.default_activity_button:
                break;
        }
    }
}
