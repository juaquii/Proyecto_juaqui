package com.github.juaquii.Test;

import com.github.juaquii.Model.Connection.ConnectionProperties;
import com.github.juaquii.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
