package com.example.lnthe54.miniproject.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.lnthe54.miniproject.config.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author lnthe54 on 9/19/2018
 * @project MiniProject
 */
public class DownloadFile extends AsyncTask<String, Integer, String> {
    private static final String TAG = "DownloadFile";
    private onCallBack callBack;
    private Context context;

    private String fileName;
    private File folder;

    public DownloadFile(Context context, onCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        folder = new File(Environment.getExternalStorageDirectory(), Config.NAME_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        callBack.initProgressDialog(context);
    }

    @Override
    protected String doInBackground(String... link) {
        try {

            URL url = new URL(link[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();
            InputStream input = connection.getInputStream();

            fileName = link[0].substring(link[0].lastIndexOf('/') + 1, link[0].length());

            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()
                    + "/" + Config.NAME_FOLDER + "/" + fileName);

            byte[] data = new byte[4096];
            long total = 0;
            int count = input.read(data);

            while (count != -1) {
                total += count;
                publishProgress((int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
                count = input.read(data);
            }

            input.close();
            output.close();

            return "Downloaded at: " + Config.NAME_FOLDER + fileName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Something went wrong";
    }

    protected void onProgressUpdate(Integer... progress) {
        callBack.setProgress(progress);
    }

    @Override
    protected void onPostExecute(String path) {
        callBack.dismissProgressDialog();
    }

    public interface onCallBack {

        void initProgressDialog(Context context);

        void setProgress(Integer[] progress);

        void dismissProgressDialog();
    }
}
