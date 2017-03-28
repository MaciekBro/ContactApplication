package com.example.rent.contactapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        requestPermision();

    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,     //nie robimy komunikatu, dlaczego chcemy te uprawnienia
//                    Manifest.permission.READ_CONTACTS)) {
//
//            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

//            }
        } else {
            loadContact();
        }
    }

    private void loadContact() {

        Cursor contactsCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);

        Toast.makeText(this, "Załadowano kontakty: " + contactsCursor.getCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContact();
                } else {
                    Toast.makeText(this, "Brak uprawnień do wykonania operacji..", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}
