package com.example.drawerlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView NO, NAMA, UMUR, PB, BB, STATUS;
    }

    @Override
    public void add(@Nullable Object object) {
//        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        View row = convertView;
        LayoutHandler layoutHandler;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.NO = (TextView)convertView.findViewById(R.id.text_no);
            layoutHandler.NAMA = (TextView)convertView.findViewById(R.id.text_nama_bayi);
            layoutHandler.UMUR = (TextView)convertView.findViewById(R.id.text_umur);
            layoutHandler.PB = (TextView)convertView.findViewById(R.id.text_pb);
            layoutHandler.BB = (TextView)convertView.findViewById(R.id.text_bb);
            layoutHandler.STATUS = (TextView)convertView.findViewById(R.id.text_status);
            convertView.setTag(layoutHandler);
//            convertView = layoutInflater.inflate()
        } else {
            layoutHandler = (LayoutHandler)convertView.getTag();
        }

        DataProvider dataProvider = (DataProvider)this.getItem(position);
        layoutHandler.NO.setText(dataProvider.getNo());
        layoutHandler.NAMA.setText(dataProvider.getNama());
        layoutHandler.UMUR.setText(dataProvider.getUmur());
        layoutHandler.PB.setText(dataProvider.getPb());
        layoutHandler.BB.setText(dataProvider.getBb());
        layoutHandler.STATUS.setText(dataProvider.getStatus());

        return convertView;

    }
}
