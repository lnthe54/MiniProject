package com.example.lnthe54.miniproject.model;

import android.os.AsyncTask;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author lnthe54 on 9/7/2018
 * @project MiniProject
 */
public class NewsAsync extends AsyncTask<String, Void, ArrayList<News>> {
    private XMLParserCallBack callBack;

    public NewsAsync(XMLParserCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
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
    }

    public interface XMLParserCallBack {
        void onParseResult(ArrayList<News> listNews);
    }
}
