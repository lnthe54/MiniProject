package com.example.lnthe54.miniproject.model;

import com.example.lnthe54.miniproject.config.Config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/7/2018
 * @project MiniProject
 */
public class NewsXMLParser extends DefaultHandler {
    private ArrayList<News> listNew = new ArrayList<>();
    private News news;
    private String value;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals(Config.ITEM)) {
            news = new News();
        }
        value = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value += new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (news == null) {
            return;
        }

        switch (qName) {
            case Config.TITLE: {
                news.setTitle(value);
                break;
            }
            case Config.DESCRIPTION: {
//                String src = "<img src=\n";
//                int index = value.indexOf(src);
//                String desc = value.substring(index + src.length());
//                index = desc.indexOf("\" alt=");
//                value = value.substring(index);
//                String img = desc.substring(0, index);
//                Log.d(TAG, "endElement: " + img);
////                news.setImage(img);
//
//                String br = "<font size=\\\"-1\\\">";
//                int index = value.indexOf(br) + br.length();
//                String desc = value.substring(index);
//                Log.d(TAG, "endElement: "+desc);
//                news.setDesc(desc);
                break;
            }
            case Config.PUB_DATE: {
                news.setPubDate(value);
                break;
            }
            case Config.LINK: {
                news.setLink(value);
                break;
            }
            case Config.ITEM: {
                listNew.add(news);
                break;
            }
        }
    }

    public ArrayList<News> getListNew() {
        return listNew;
    }
}
