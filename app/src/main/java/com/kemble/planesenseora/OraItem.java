package com.kemble.planesenseora;

import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Pete on 3/14/2015.
 */
public class OraItem {

    public String RiskFactor;
    public String Category;
    public int RiskFactorPointScale;
    public String RiskFactorGreen;
    public String RiskFactorYellow;
    public String RiskFactorOrange;
    public String RiskFactorRed;

    public String getVal(){
        return null;
    }

    public class Category {
        public String Level;
        public String Name;
    }
}

