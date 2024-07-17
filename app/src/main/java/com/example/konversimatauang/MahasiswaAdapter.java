package com.example.konversimatauang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MahasiswaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();

    public MahasiswaAdapter(Context context) {
        this.context = context;
    }

    public void setMahasiswaList(ArrayList<Mahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @Override
    public int getCount() {
        return mahasiswaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mahasiswaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // or return a unique identifier for the item at position
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Mahasiswa mahasiswa = (Mahasiswa) getItem(position);
        viewHolder.bind(mahasiswa);

        return convertView;
    }

    private static class ViewHolder {
        private TextView txtNim;
        private TextView txtName;

        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txt_nama);
            txtNim = view.findViewById(R.id.txt_nim);
        }

        void bind(Mahasiswa mahasiswa) {
            txtName.setText(mahasiswa.getNama());
            txtNim.setText(mahasiswa.getNim());
        }
    }
}
