package com.example.drawerlayout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.google.firebase.inappmessaging.internal.Logging.TAG;

public class ListviewFragment extends Fragment {

    TextView no, nama, umur, pb, bb, status;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper db;
    Cursor cursor;
    ListView listView;
    ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.list_view);
        db = new DatabaseHelper(getContext());
        sqLiteDatabase = db.getReadableDatabase();
        cursor = db.getAllData();
        listAdapter = new ListAdapter(getContext(),R.layout.row_layout);
        listView.setAdapter(listAdapter);

        no = (TextView)view.findViewById(R.id.no);
        nama = (TextView)view.findViewById(R.id.nama);
        umur = (TextView)view.findViewById(R.id.umur);
        pb = (TextView)view.findViewById(R.id.pb);
        bb = (TextView)view.findViewById(R.id.bb);
        status = (TextView)view.findViewById(R.id.status);

        if (cursor.moveToFirst()) {

            do {
                String no, nama, umur, pb, bb, status;
                no = cursor.getString(0);
                nama = cursor.getString(2);
                umur = cursor.getString(3);
                pb = cursor.getString(6);
                bb = cursor.getString(7);
                status = cursor.getString(8);
                DataProvider dataProvider = new DataProvider(no, nama, umur, pb, bb, status);
                listAdapter.add(dataProvider);
                listAdapter.notifyDataSetChanged();

            }while (cursor.moveToNext());

        }
        Log.d(TAG, "onCreateView: "+cursor.getCount());

        return view;
    }
}
