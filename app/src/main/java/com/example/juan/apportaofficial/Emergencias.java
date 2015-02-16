package com.example.juan.apportaofficial;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Emergencias  extends Fragment implements AdapterView.OnItemClickListener {
    private String addressString = "No address found",direccion="";
    private ListView listViewE;
    private ArrayAdapter<String>adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.emergencias, null);
        listViewE=(ListView)v.findViewById(R.id.listViewE);
        listViewE.setOnItemClickListener(this);
        LocationManager locationManager;
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateWithNewLocation(location);
        showNumbers();
        return v;
    }

    private void updateWithNewLocation(Location location) {
        String latLongString = "Ubicacion Desconocida";

        DecimalFormat df = new DecimalFormat("##.00");
        if (location != null) {
            double lati = location.getLatitude();
            double lngi = location.getLongitude();
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
                    direccion=addressString;
                }
                Toast.makeText(getActivity().getApplicationContext(),"Ubicacion Actual:\n"+direccion,Toast.LENGTH_SHORT).show();
            } catch (IOException ioe) {
                Log.e("Geocoder IOException exception: ", ioe.getMessage());
            }
        }
    }

    private void showNumbers(){
        switch (direccion.trim()){
            case "Monterrey, N.L.":
                String[] numerosAtencion = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion);
                listViewE.setAdapter(adapter);
                break;
            case "Guadalupe, N.L.":
                System.out.println("Estoy en gpe");
                String[] numerosAtencion1 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion1);
                listViewE.setAdapter(adapter);
                break;
            case "San Nicolas de los Garza, N.L.":
                String[] numerosAtencion2 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion2);
                listViewE.setAdapter(adapter);
                break;
            case "Escobedo, N.L.":
                String[] numerosAtencion3 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion3);
                listViewE.setAdapter(adapter);
                break;
            case "Apodaca, N.L.":
                String[] numerosAtencion4 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion4);
                listViewE.setAdapter(adapter);
                break;
            case "Juarez,N.L.":
                String[] numerosAtencion5 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion5);
                listViewE.setAdapter(adapter);
                break;
            default:
                String[] numerosAtencion6 = new String[] {"ATENCION CIUDADANA","01 800 161 2422", "01 800 161 CIAC", "(81)8345-4545",
                        "(81)8130-6523","(81)8130-6557","(81)8130-6558","CRUZ ROJA MONTERREY","(81)8375-1212","EMERGENCIAS","065",
                        "CRUZ VERDE MONTERREY", "(81)8311-003","(81)8311-0014","(81)8311-0449","VIALIDAD Y TRANSITO ","060","(81)8305-0900",
                        "POLICIA PREVENTIVA MPAL","(81)8125-9494","(81)8125-9400","POLICIA MINISTERIAL",
                        "(81)8151-6001","CENTRO ESTATAL DE EMERGENCIAS","066", "01-800-712-4580 (Lada sin costo)",
                        "BOMBEROS DE MONTERREY", "(81)8342-0053", "(81)8342-0054","(81)8342-0055","PROTECCION CIVIL","(81)8375-4909",
                        "ANGELES VERDES","(81)8340-2113"};
                adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numerosAtencion6);
                listViewE.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        try{
            String number = "tel:" +adapterView.getItemAtPosition(i).toString().trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        }catch(Exception ex){
            Toast.makeText(getActivity().getApplicationContext(),"Selecciona un numero",Toast.LENGTH_SHORT).show();
        }
    }
}
