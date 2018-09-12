package com.example.lnthe54.miniproject.model;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lnthe54.miniproject.R;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author lnthe54 on 9/7/2018
 * @project MiniProject
 */
public class NewsAsync extends AsyncTask<String, Void, ArrayList<News>> {
    private XMLParserCallBack callBack;
    private Context context;

    public NewsAsync(Context context, XMLParserCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callBack.initDialog(context, R.style.Custom);
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            NewsXMLParser xmlParser = new NewsXMLParser();
            String link = strings[0];

            parser.parse(link, xmlParser);

            return xmlParser.getListNew();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        callBack.onParseResult(news);
        callBack.dismissDialog();
    }

    public interface XMLParserCallBack {
        void onParseResult(ArrayList<News> listNews);

        void initDialog(Context context, int style);

        void dismissDialog();
    }
}
