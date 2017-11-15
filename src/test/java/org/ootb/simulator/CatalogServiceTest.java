package org.ootb.simulator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jiazhao on 10/26/17.
 */
public class CatalogServiceTest extends BaseTest{

    private String serviceURL = "http://ws-catalogservice-hdp.vdev.gid.gap.com/ConsolidatedCatalogProvider/resources/catalog/v7/skus?market=US&channel=stores&storeId=05955&brand=ON&storeProductDataFromHBase=true&clientId=Snapsell&effectiveDate=2017-10-16%2023:08:00%20-0700&barCode=113140900021";
    private String data_local = "";

    @Before
    public void setup() throws Exception{
        commands = new String[]{"curl","-H","Content-Type:application/json","-X","GET","-d","",""};
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testGetProductFromHBase() throws Exception{
        serviceURL = "http://ws-catalogservice-hdp.e2e2.gid.gap.com/ConsolidatedCatalogProvider/resources/catalog/v7/skus?market=US&channel=stores&storeId=05955&brand=ON&storeProductDataFromHBase=true&clientId=Snapsell&effectiveDate=2017-10-25%2023:08:00%20-0700&id=1417000010000";
        data_local = "";
        commands[6] = data_local;
        commands[7] = serviceURL;
        exec();
    }

    @Test
    public void testUseHttpURLConnection() throws  Exception{
        serviceURL = "http://ws-catalogservice-hdp.e2e2.gid.gap.com/ConsolidatedCatalogProvider/resources/catalog/v7/skus?market=US&channel=stores&storeId=05955&brand=ON&storeProductDataFromHBase=true&clientId=Snapsell&effectiveDate=2017-10-25%2023:08:00%20-0700&id=1417000010000";
        URL url = new URL(serviceURL);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept","application/json");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while((line = reader.readLine())!=null){
            sb.append(line);
        }

        String result = sb.toString();
        System.out.println(result);
    }
}
