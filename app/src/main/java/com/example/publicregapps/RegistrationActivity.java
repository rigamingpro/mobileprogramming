package com.example.publicregapps;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.text.InputType;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class RegistrationActivity extends AppCompatActivity {
    EditText editTextNIK, editTextNama, editTextTempatLahir,editTextTanggalLahir, editTextAlamat, editTextEmail, editTextTelepon;
    RadioGroup radioGroupJenisKelamin;
    Button btnSubmit;
    RadioButton radioButtonLaki, radioButtonPerempuan;
    Calendar calendar;
    Spinner spinnerCountryCode;
    ArrayAdapter<CharSequence> adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextNIK = findViewById(R.id.editTextNIK);
        editTextNama = findViewById(R.id.editTextNama);
        radioGroupJenisKelamin = findViewById(R.id.radioGroupJenisKelamin);
        editTextTanggalLahir = findViewById(R.id.editTextTanggalLahir);
        editTextTempatLahir = findViewById(R.id.editTextTempatLahir);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelepon = findViewById(R.id.editTextTelepon);
        spinnerCountryCode = findViewById(R.id.spinnerCountryCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Menggunakan array adapter untuk memuat kode negara dari resources
        adapter = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan data dari input fields
                String nik = editTextNIK.getText().toString();
                String nama = editTextNama.getText().toString();
                int selectedId = radioGroupJenisKelamin.getCheckedRadioButtonId();
                String jenisKelamin = "";
                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    jenisKelamin = radioButton.getText().toString();
                }
                String tempatLahir = editTextTempatLahir.getText().toString();
                String tanggalLahir = editTextTanggalLahir.getText().toString();
                String alamat = editTextAlamat.getText().toString();
                String email = editTextEmail.getText().toString();
                String telepon = editTextTelepon.getText().toString();
                // Ambil kode negara dari spinner
                String countryCode = spinnerCountryCode.getSelectedItem().toString();

                // Tambahkan kode negara ke nomor telepon jika belum ada
                if (!telepon.startsWith("+")) {
                    telepon = countryCode.split(" ")[0] + telepon;
                }

                // Validasi kolom kosong
                if (TextUtils.isEmpty(nik) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(tempatLahir) || TextUtils.isEmpty(tanggalLahir)
                        || TextUtils.isEmpty(alamat) || TextUtils.isEmpty(email) || TextUtils.isEmpty(telepon)) {
                    Toast.makeText(RegistrationActivity.this, "Isi semua kolom yang diperlukan", Toast.LENGTH_SHORT).show();
                    highlightEmptyFields(); // Method untuk menyorot kolom yang kosong
                    return;
                }



                // Kirim data ke halaman Detail Penduduk
                Intent intent = new Intent(RegistrationActivity.this, DetailActivity.class);
                intent.putExtra("NIK", nik);
                intent.putExtra("Nama", nama);
                intent.putExtra("JenisKelamin", jenisKelamin);
                intent.putExtra("TempatLahir", tempatLahir);
                intent.putExtra("TanggalLahir", tanggalLahir);
                intent.putExtra("Alamat", alamat);
                intent.putExtra("Email", email);
                intent.putExtra("Telepon", telepon);
                startActivity(intent);
            }
        });

        // Hanya memperbolehkan input angka pada editTextNIK
        editTextNIK.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextTelepon.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Inisialisasi DatePickerDialog saat editTextTempatTanggalLahir diklik
        editTextTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(RegistrationActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextTanggalLahir.setText(sdf.format(calendar.getTime()));
    }

    private void highlightEmptyFields() {
        if (TextUtils.isEmpty(editTextNIK.getText())) {
            editTextNIK.setHint("NIK (Isi diperlukan)");
            editTextNIK.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextNama.getText())) {
            editTextNama.setHint("Nama (Isi diperlukan)");
            editTextNama.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextTempatLahir.getText())) {
            editTextTempatLahir.setHint("Tempat Lahir (Isi diperlukan)");
            editTextTempatLahir.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextTanggalLahir.getText())) {
            editTextTanggalLahir.setHint("Tanggal Lahir (Isi diperlukan)");
            editTextTanggalLahir.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextAlamat.getText())) {
            editTextAlamat.setHint("Alamat (Isi diperlukan)");
            editTextAlamat.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextEmail.getText())) {
            editTextEmail.setHint("Email (Isi diperlukan)");
            editTextEmail.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (TextUtils.isEmpty(editTextTelepon.getText())) {
            editTextTelepon.setHint("Telepon (Isi diperlukan)");
            editTextTelepon.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }
}
