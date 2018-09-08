package com.example.lnthe54.miniproject.model;

import android.util.Log;

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
    private static String TAG = "NewsXMLParser";

    private boolean allow_item = false;
    private boolean allow_link = false;
    private boolean allow_desc = false;
    private boolean allow_title = false;
    private boolean allow_pubDate = false;

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

            case Config.ITEM: {
                if (!allow_item) {
                    allow_item = true;
                    break;
                }
                listNew.add(news);
                news = null;
                break;
            }

            case Config.DESCRIPTION: {
                if (!allow_desc) {
                    allow_desc = true;
                    break;
                }
                String src = "img src=\"//";
                int index = value.indexOf(src) + src.length();
                String link = value.substring(index);
                String img = link.substring(0, link.indexOf("\""));
                img = "https://" + img;
                news.setImage(img);

                String desc = "</font></b></font><br><font size=\"-1\">";
                index = value.indexOf(desc) + desc.length();
                String s = value.substring(index);
                String description = s.substring(0, s.indexOf("</font>"));
                Log.d(TAG, "desc: " + description);
                news.setDesc(description);
                break;
            }

            case Config.TITLE: {
                if (!allow_title) {
                    allow_title = true;
                    break;
                }

                news.setTitle(value);
                break;
            }

            case Config.LINK: {
                if (!allow_link) {
                    allow_link = true;
                    break;
                }

                news.setLink(value);
                break;
            }

            case Config.PUB_DATE: {
                if (!allow_pubDate) {
                    allow_pubDate = true;
                    break;
                }
                news.setPubDate(value);
                break;
            }
        }
    }

    public ArrayList<News> getListNew() {
        return listNew;
    }
}
