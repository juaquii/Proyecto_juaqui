package com.github.juaquii.Test;

import com.github.juaquii.Model.Connection.ConnectionProperties;
import com.github.juaquii.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","proyecto_futbol","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
