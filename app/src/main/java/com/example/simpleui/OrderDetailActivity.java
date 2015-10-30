package com.example.simpleui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView addressTextView;
    private WebView webView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        addressTextView = (TextView) findViewById(R.id.address);
        webView= (WebView) findViewById(R.id.webView);
        imageView= (ImageView) findViewById(R.id.imageView);
        String store_Info=getIntent().getStringExtra("store_info");
        //店名,地址
        final String address=store_Info.split(",")[1];
        Log.d("debug", "address=" + address);
        addressTextView.setText(address);

        GeoCodingTask task=new GeoCodingTask();
        task.execute(address);
    }
    //AsyncTask<傳進去的值,進度值,回傳值>
    //在背景作業
    //使用方式:
    //GeoCodingTask task=new GeoCodingTask();
    //task.execute(傳進去的值1,傳進去的值2,傳進去的值3);
    //分別對應params[0],params[1],params[2]

    private class GeoCodingTask extends AsyncTask<String,Integer,String>{
        //對應第一個參數，傳進去的值
        //params是Array
        //String doInBackground中的String對應第三個參數:回傳值
        @Override
         protected String doInBackground(String... params) {
            String url=Utils.getGEOUrl(params[0]);
            String json =new String(Utils.urlToBytes(url));
            String latLng=Utils.getLatLngFromJSON(json);
            return latLng;
        }
//        //對應第二個參數，進度值
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//        }
        //對應第三個參數，回傳值
        @Override
        protected void onPostExecute(String latLng) {
            //執行完後將值放到TextView
            addressTextView.setText(latLng);
            String staticMapUrl=Utils.getStaticMapUrl(latLng,"17","300x600");
            webView.loadUrl(staticMapUrl);
            StaticMapTask task=new StaticMapTask();
            task.execute(latLng);

        }



    }
    private class StaticMapTask extends AsyncTask<String,Integer,byte[]>{
        @Override
        protected byte[] doInBackground(String... params) {
            String staticMapUrl=Utils.getStaticMapUrl(params[0],"17","300x600");
            return Utils.urlToBytes(staticMapUrl);
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            Bitmap bm= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageView.setImageBitmap(bm);
        }
    }

}
