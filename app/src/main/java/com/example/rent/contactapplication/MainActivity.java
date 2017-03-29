package com.example.rent.contactapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ContactsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermision();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ContactsAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        //loader, byt w stylu nukleusa do ładowania danych!

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

    private void loadContact() {            //contentprovidery opieraja sie na  URI

        getSupportLoaderManager().initLoader(1, null, this);


//        Cursor contactsCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
//        Toast.makeText(this, "Załadowano kontakty: " + contactsCursor.getCount(), Toast.LENGTH_SHORT).show();
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

    //Loader callback!

    //tutaj mowimy jakiego rodzaju to bedzie loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        return new CursorLoader(this, Uri.parse(ContactsContract.Contacts.CONTENT_URI+"//2"),null,null,null,null);
    }

    //tutaj przychodzą wyniki
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(this, "Loading finished!", Toast.LENGTH_SHORT).show();
        adapter.setCursor(data);
    }

    //mozemy tutaj czyscic , raczej sie go nie używa
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setCursor(null);
    }
}
