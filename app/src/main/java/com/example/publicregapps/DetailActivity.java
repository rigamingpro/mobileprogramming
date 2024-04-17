package com.example.publicregapps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView textViewNIK, textViewNama, textViewJenisKelamin, textViewTempatLahir, textViewTanggalLahir, textViewAlamat, textViewEmail, textViewTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewNIK = findViewById(R.id.textViewNIK);
        textViewNama = findViewById(R.id.textViewNama);
        textViewJenisKelamin = findViewById(R.id.textViewJenisKelamin);
        textViewTempatLahir = findViewById(R.id.textViewTempatLahir);
        textViewTanggalLahir = findViewById(R.id.textViewTanggalLahir);
        textViewAlamat = findViewById(R.id.textViewAlamat);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewTelepon = findViewById(R.id.textViewTelepon);

        // Mengambil data dari intent
        String nik = getIntent().getStringExtra("NIK");
        String nama = getIntent().getStringExtra("Nama");
        String jenisKelamin = getIntent().getStringExtra("JenisKelamin");
        String tempatLahir = getIntent().getStringExtra("TempatLahir");
        String tanggalLahir = getIntent().getStringExtra("TanggalLahir");
        String alamat = getIntent().getStringExtra("Alamat");
        String email = getIntent().getStringExtra("Email");
        String telepon = getIntent().getStringExtra("Telepon");

        // Menampilkan data pada TextView
        textViewNIK.setText(nik);
        textViewNama.setText(nama);
        textViewJenisKelamin.setText(jenisKelamin);
        textViewTempatLahir.setText(tempatLahir);

        // Ubah format tanggal lahir
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            Date date = inputFormat.parse(tanggalLahir);
            String formattedDate = outputFormat.format(date);
            textViewTanggalLahir.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            textViewTanggalLahir.setText(tanggalLahir);
        }

        textViewAlamat.setText(alamat);
        textViewEmail.setText(email);
        textViewTelepon.setText(telepon);

        // Menambahkan event listener untuk textViewEmail
        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka aplikasi email untuk menulis email ke alamat yang ditampilkan
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + email));
                startActivity(intent);
            }
        });

        // Menambahkan event listener untuk textViewTelepon
        textViewTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka aplikasi untuk melakukan panggilan ke nomor yang ditampilkan
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telepon));
                startActivity(intent);
            }
        });
    }
}
