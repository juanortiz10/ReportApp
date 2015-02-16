package com.example.juan.apportaofficial;

import android.annotation.TargetApi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Reportes extends android.support.v4.app.Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private EditText etNombre,etReferencia, etDescripcion,etFecha,etPersona;
    private Button btnCargar, btnEnviar;
    private Uri output;
    private File file;
    private String addressString = "No address found",lat="lat",lon="lon",foto,spnSelection;
    private Spinner spn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reportes, null);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etNombre = (EditText) v.findViewById(R.id.etNombre);
        etReferencia = (EditText) v.findViewById(R.id.etReferencia);
        etDescripcion = (EditText) v.findViewById(R.id.etDescripcion);
        etFecha = (EditText) v.findViewById(R.id.etFecha);
        etPersona=(EditText)v.findViewById(R.id.etPersona);

        spn=(Spinner)v.findViewById(R.id.spn);
        spn.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        R.array.spn_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        btnCargar = (Button) v.findViewById(R.id.btnCargar);
        btnEnviar = (Button) v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
        btnCargar.setOnClickListener(this);

        LocationManager locationManager;
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateWithNewLocation(location);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCargar:
                if (!etNombre.getText().toString().trim().equalsIgnoreCase("")) {
                    getCamera();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Agregaste Nombre a la foto", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnEnviar:
                if (areFull() == true) {
                    InputStream is = null;
                    String nombre = etPersona.getText().toString();
                    String refe = etReferencia.getText().toString();
                    String desc = etDescripcion.getText().toString();
                    String fec = etFecha.getText().toString();

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("name", nombre));
                    nameValuePairs.add(new BasicNameValuePair("dire", addressString));
                    nameValuePairs.add(new BasicNameValuePair("refe", refe));
                    nameValuePairs.add(new BasicNameValuePair("des", desc));
                    nameValuePairs.add(new BasicNameValuePair("fec", fec));
                    nameValuePairs.add(new BasicNameValuePair("lat", lat));
                    nameValuePairs.add(new BasicNameValuePair("lon", lon));
                    nameValuePairs.add(new BasicNameValuePair("tipo", spnSelection));
                    try {
                        if (file.exists()) new ServerUpdate().execute();
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://reportapp.org/connection/connection.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Toast.makeText(getActivity().getApplicationContext(), "Reporte agregado correctamente", Toast.LENGTH_LONG).show();
                        Intent act = new Intent();
                        act.setClass(getActivity(), Success.class);
                        getActivity().startActivity(act);
                    } catch (Exception ex) {
                        Log.e("Client Protocol", "Log_Tag");
                        ex.printStackTrace();
                    }
                    break;
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Verifica que hayas llenado todos los campos",Toast.LENGTH_SHORT).show();
                }
        }
    }

    //This method is going to invoke the CAMERA from the device
    private void getCamera() {
        foto = Environment.getExternalStorageDirectory() + "/" + etNombre.getText().toString().trim() + ".jpg";
        file = new File(foto);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        output = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver cr = getActivity().getContentResolver();
        Bitmap bit = null;
        try {
            bit = android.provider.MediaStore.Images.Media.getBitmap(cr, output);
            int rotate = 0;
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void uploadFoto(String imag) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httpPost = new HttpPost("http://reportapp.org/connection/FileUpload.php");
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody foto = new FileBody(file, "imag.jpeg");
        mpEntity.addPart("fotoUp", foto);
        httpPost.setEntity(mpEntity);
        try {
            httpClient.execute(httpPost);
            httpClient.getConnectionManager().shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         spn.setSelection(i);
         spnSelection = (String) spn.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ServerUpdate extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... strings) {
            uploadFoto(foto);
            if(onInsert())
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "Error al subir la imagen", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Espera un momento...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
    }

    private boolean onInsert(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient=new DefaultHttpClient();
        httpPost=new HttpPost("http://reportapp.org/connection/InsertImagen.php");
        nameValuePairs=new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("imagen",etNombre.getText().toString().trim()+".jpg"));

        try{
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }


    private void updateWithNewLocation(Location location) {
        String latLongString = "UBicacion Desconocida";

        DecimalFormat df = new DecimalFormat("##.00");
        if (location != null) {
            double lati = location.getLatitude();
            double lngi = location.getLongitude();
            lat= String.valueOf(lati);
            lon= String.valueOf(lngi);
            latLongString = "Lat:" + df.format(lati) + "\nLong:" + df.format(lngi);
            Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(lati, lngi, 1);
                if (addresses.size() == 1) {
                    addressString = "";
                    Address address = addresses.get(0);
                    addressString = addressString + address.getAddressLine(0) + "\n";
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressString = addressString + address.getAddressLine(i) + "\n";
                    }
                    addressString = addressString + address.getCountryName() + "\n";
                }
            } catch (IOException ioe) {
                Log.e("Geocoder IOException exception: ", ioe.getMessage());
            }
        }
    }

    private boolean areFull(){
        boolean request = false;
        if (etNombre.getText().toString().trim().equals("") ||etReferencia.getText().toString().trim().equals("") || etDescripcion.getText().toString().trim().equals("") || etFecha.getText().toString().trim().equals("") ||etPersona.getText().toString().trim().equals("")){
        }else{
            request=true;
        }
        return request;
    }
}





