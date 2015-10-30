package com.example.simpleui;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ggm on 10/12/15.
 */
public class Utils {


    public static void writeFile(Context context, String fileName, String content) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];

            fis.read(buffer, 0, buffer.length);
            fis.close();

            return new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    //將圖檔的路徑轉成uri的格式 -> file://XXXX/XXX.XXX
    public static Uri getPhotoUri() {
        //取得sdcard圖片資料匣的路徑
        //getExternalStoragePublicDirectory:取得SDCARD的目錄
        //DIRECTORY_PICTURES:圖片資料匣

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //如果不存在就建立
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        //sdcard圖片資料匣的路徑/simpleui_photo.png
        File file = new File(dir, "simpleui_photo.png");
        //轉成uri格式
        return Uri.fromFile(file);
    }
    //將uri的圖讀出來放到ByteArray
    public static byte[] uriToBytes(Context context,Uri uri){
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while ((len=is.read(buffer))!=-1){
                baos.write(buffer);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static byte[] urlToBytes(String urlString){
        try {
            URL url=new URL(urlString);
            URLConnection connection=url.openConnection();
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while ((len=is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    //利用GEO網站將地址轉成坐標
    //https://maps.googleapis.com/maps/api/geocode/json?address= 加地址
    //GEO網站回傳JSONARRAY字串
    public static String getGEOUrl(String address) {
        try {
            //要將中文地址轉用Ucode碼
            address= URLEncoder.encode(address,"utf-8");
            return "https://maps.googleapis.com/maps/api/geocode/json?address=" + address;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //利用GEO網站將地址轉成坐標
    //解析GEO網站回傳的JSONARRAY字串
    public static String getLatLngFromJSON(String jsonString){
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONObject locaion=object.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");
            double lat=locaion.getDouble("lat");
            double lng=locaion.getDouble("lng");
            Log.d("Debug",lat+","+lng);
            return lat+","+lng;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    //透過maps.googleapis.com回傳回坐標位置的圖片
    public static String getStaticMapUrl(String center,String zoom,String size){
        return String.format("https://maps.googleapis.com/maps/api/staticmap?center=%s&zoom=%s&size=%s",center,zoom,size);
    }

}
