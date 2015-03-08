package com.example.juan.apportaofficial;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


public class MyCall extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new callTask().execute();
    }

    class callTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            Bundle bundle = getIntent().getExtras();
            Intent intent = new Intent(Intent.ACTION_CALL);
            String uri = "tel:"+bundle.getString("tel");
            intent.setData(Uri.parse(uri));
            startActivity(intent);
            return null;
        }
        @Override
        protected void onPostExecute(Object result) {
            Intent intent1=new Intent(MyCall.this,Page1.class);
            startActivity(intent1);
        }

    }
}
