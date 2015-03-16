package com.kemble.planesenseora;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


public class OraMainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ora);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_ora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ora, container, false);


            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(getResources().openRawResource(R.raw.ora_items));

                ArrayList<OraItem> oraItems = new ArrayList<OraItem>();
//                XPathFactory xPathFactory = XPathFactory.newInstance();
//                XPath xp = xPathFactory.newXPath();
//
//                XPathExpression exprOraItems = xp.compile("/risk-factors/ora-items");
//                XPathExpression exprCategories = xp.compile("/risk-factors/categories");

                NodeList nlOraItems = document.getElementsByTagName("OraItem");
                NodeList nlCategories = document.getElementsByTagName("category-item");

                for (int n = 0; n < nlOraItems.getLength(); n++){
                    OraItem oraItem = new OraItem();
                    oraItem.RiskFactor = nlOraItems.item(n).getAttributes().getNamedItem("name").getNodeValue();
                    NodeList kids = nlOraItems.item(n).getChildNodes();
                    for(int k = 0; k < kids.getLength(); k++) {

                        switch (kids.item(k).getNodeName()) {
                            case "category":
                                String category = getCategory(nlCategories, kids.item(k).getTextContent());
                                if(category != null){
                                    oraItem.Category = category;
                                } else {
                                    oraItem.Category = "no category";
                                }
                            case "RiskFactorGreen":
                                oraItem.RiskFactorGreen = kids.item(k).getTextContent();
                                break;
                            case "RiskFactorYellow":
                                oraItem.RiskFactorYellow = kids.item(k).getTextContent();
                                break;
                            case "RiskFactorOrange":
                                oraItem.RiskFactorOrange = kids.item(k).getTextContent();
                                break;
                            case "RiskFactorRed":
                                oraItem.RiskFactorRed = kids.item(k).getTextContent();
                                break;
                        }
                    }
                    oraItems.add(oraItem);
                }

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }

            return rootView;
        }

        private static String getCategory(NodeList nlCategories, String categoryLevel) {
            for(int i = 0; i < nlCategories.getLength(); i++){
                Node n = nlCategories.item(i).getAttributes().getNamedItem("level");
                if(n != null){
                    String catAttr = n.getNodeValue();
                    if(catAttr.equalsIgnoreCase(categoryLevel)){
                        return nlCategories.item(i).getTextContent();
                    }
                }
            }
            return null;
        }
    }
}
