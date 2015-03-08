package com.example.juan.apportaofficial;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapter.MyBaseAdapter;
import adapter.Postal;

public class Emergencias  extends Fragment {
    private String addressString = "No address found",direccion="";
    ListView lv;
    Context context;
    String municipio;
    int[] prgmImages;
    String [] prgmNameList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.emergencias, null);
        context=getActivity().getApplicationContext();

        LocationManager locationManager;
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateWithNewLocation(location);

        asynk as=new asynk();
        as.execute();
        lv=(ListView)v.findViewById(R.id.listView);
        return v;
    }

    public void setAdapter(){
        lv.setAdapter(new MyBaseAdapter(this, prgmNameList,prgmImages));
    }

    private void updateWithNewLocation(Location location) {
        DecimalFormat df = new DecimalFormat("##.00");
        if (location != null) {
            double lati = location.getLatitude();
            double lngi = location.getLongitude();
            Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(lati, lngi, 1);
                if (addresses.size() == 1) {
                    addressString = "";
                    Address address = addresses.get(0);
                       addressString = addressString + address.getPostalCode();
                       direccion=addressString;
                    System.out.println(direccion);
                }
            }catch (IOException ioe) {
                Log.e("Geocoder IOException exception: ", ioe.getMessage());
            }
        }
    }

    class asynk extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                verificaNull();
                int code=Integer.parseInt(direccion);
                Postal postal=new Postal();
                municipio=postal.getCodes(code);
                checkPostal();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void verificaNull(){
        if (direccion.trim().equals("null")) {
            direccion = "64000";
        }
    }


    private void checkPostal(){
        String loc=municipio;
        switch (loc.trim()){

            case "Monterrey":
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                                , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                        prgmNameList= new String[]{"(81)8375-1212 ", "(81)8125-9494 ", "(81)8305-0900", "(81)8340-2113"
                                , "(81)8342-0053", "(81)8375-4909", "(81)8311-0033"};
                        System.out.println("Estoy en mty");
                        setAdapter();
                    }
                });

                break;

            case "Juarez":
                getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"(81)8375-1212 ", "1771-2060 ", "1771-2050", "(81)8340-2113"
                        , "1771-2060", "1878-0434", "(81)8311-0033"};
                setAdapter();
                    }
                });
                break;

            case "Guadalupe":
                getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"(81)8375-1212 ", "8030-6000 ", "8135-5900", "(81)8340-2113"
                        , "4040-0021", "8030-6185", "4040-9080"};
                setAdapter();
                            }
                        });
                break;

            case "San Nicolas":
                getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"(81)8375-1212 ", "(81)8125-9494 ", "8353-3649", "(81)8340-2113"
                        , "8383-9568", "8330-3344", "8158-1351"};
                setAdapter();
                    }
                  });
                break;

            case "Escobedo":
             getActivity().runOnUiThread(new Runnable() {
             @Override
             public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"065", "(81)8125-9494 ", "8353-3649", "(81)8340-2113"
                        , "8383-9568", "8397-2911", "8158-1351"};
               setAdapter();
                        }
              });
                break;

            case "Apodaca":
              getActivity().runOnUiThread(new Runnable() {
               @Override
                public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"(81)8375-1212 ", "(81)8125-9494 ", "(81)8305-0900", "(81)8340-2113"
                        , "(81)8342-0053", "(81)8375-4909", "(81)8311-0033"};
                setAdapter();
               }
              });
                break;

            default:
            getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prgmImages= new int[]{R.drawable.roja, R.drawable.alarm, R.drawable.police, R.drawable.ange
                        , R.drawable.bomberos, R.drawable.proteccion, R.drawable.verde};
                prgmNameList= new String[]{"(81)8375-1212 ", "(81)8125-9494 ", "(81)8305-0900", "(81)8340-2113"
                        , "(81)8342-0053", "(81)8375-4909", "(81)8311-0033"};
                setAdapter();
                }
          });
                break;
        }
    }
}
