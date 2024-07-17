package com.example.konversimatauang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_edit_nim, edt_edit_nama;
    private Button btn_update;
    private DatabaseReference database;

    public static final String EXTRA_MAHASISWA = "extra_mahasiswa";
    public static final int ALERT_DIALOG_CLOSE = 10;
    public static final int ALERT_DIALOG_DELETE = 20;

    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = FirebaseDatabase.getInstance().getReference();

        edt_edit_nim = findViewById(R.id.edt_edit_nim);
        edt_edit_nama = findViewById(R.id.edt_edit_nama);
        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        mahasiswa = getIntent().getParcelableExtra(EXTRA_MAHASISWA);

        if (mahasiswa != null) {
            edt_edit_nim.setText(mahasiswa.getNim());
            edt_edit_nama.setText(mahasiswa.getNama());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateMahasiswa();
        }
    }

    private void updateMahasiswa() {
        String nim = edt_edit_nim.getText().toString().trim();
        String nama = edt_edit_nama.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edt_edit_nama.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            edt_edit_nim.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields) {
            Toast.makeText(UpdateActivity.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            mahasiswa.setNim(nim);
            mahasiswa.setNama(nama);
            mahasiswa.setPhoto("");

            DatabaseReference dbMahasiswa = database.child("mahasiswa").child(mahasiswa.getId());
            dbMahasiswa.setValue(mahasiswa);

            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void kembalihome(View view) {
        Intent moveIntent = new Intent(UpdateActivity.this, to_do_list.class);
        startActivity(moveIntent);
        finish();
    }

    public void deleteData(View view) {
        showAlertDialog(ALERT_DIALOG_DELETE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //case R.id.action_delete:
            //    showAlertDialog(ALERT_DIALOG_DELETE);
            //break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(final int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;

        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isDialogClose) {
                    finish();
                } else {
                    DatabaseReference dbMahasiswa = database.child("mahasiswa").child(mahasiswa.getId());
                    dbMahasiswa.removeValue();

                    Toast.makeText(UpdateActivity.this, "Deleting data...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
