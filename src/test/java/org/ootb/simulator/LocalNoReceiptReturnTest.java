package org.ootb.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by jiazhao on 10/3/17.
 */
public class LocalNoReceiptReturnTest extends BaseTest{

    private String url_dev = "";
    private String url_local = "";
    private String data_dev = "";
    private String data_local = "";
    private String command = "";
//    private String[] commands ;
//    private String txnUUID = "";
    private String baseUrl = "http://localhost:8080/orders/";

    @Before
    public void setup() throws Exception{
        commands = new String[]{"curl","-H","Content-Type:application/json","-X","POST","-d","",""};
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testLocalNoReceiptReturn() throws IOException, InterruptedException {
        blindReturn();
        addProduct();
        getTax();
        issueMRC();
        printRecepit();
        submit();
    }

    private void blindReturn() throws IOException, InterruptedException {
        url_local = baseUrl + "returns/blindreturn";
        data_local = "{\"storeNumber\":\"05955\",\"registerNumber\":\"050\",\"brandName\":\"Athleta\",\"brandAbbreviation\":\"AT\",\"zipcode\":\"94403\"," +
                "\"serialNumber\":\"testDevice-serial-no\",\"lasDeviceId\":\"3c651925-d54c-4798-9fb8-c8855ae30554\",\"localDate\":\"2017-10-04T11:08:00.030-0700\"," +
                "\"appName\":\"error-500\",\"brandCode\":\"10\",\"city\":\"SAN MATEO\",\"phoneNumber\":\"(650) 638-0199\",\"localeCode\":\"en_US\",\"marketCode\":" +
                "\"US\",\"storeName\":\"Some Name\",\"stateCode\":\"CA\",\"address\":\"49 WEST HILLSDALE BLVD.\",\"cashierId\":\"1580344\"}";
        commands[6] = data_local;
        commands[7] = url_local;
        String result = exec();
        fetchTxnUUID(result);
    }

    private void addProduct() throws IOException, InterruptedException {
        url_local = baseUrl + "returns/blindreturn/" + txnUUID + "/product";
        data_local = "{\"scanCode\":\"160738100002\",\"store\":{\"storeNumber\":\"05955\",\"registerNumber\":\"050\",\"brandName\":\"Athleta\",\"brandAbbreviation\":" +
                "\"AT\",\"zipcode\":\"94403\",\"serialNumber\":\"testDevice-serial-no\",\"lasDeviceId\":\"3c651925-d54c-4798-9fb8-c8855ae30554\",\"localDate\":" +
                "\"2017-08-30T11:08:00.030-0700\",\"appName\":\"error-500\",\"brandCode\":\"10\",\"city\":\"SAN MATEO\",\"phoneNumber\":\"(650) 638-0199\"," +
                "\"localeCode\":\"en_US\",\"marketCode\":\"US\",\"storeName\":\"Some Name\",\"stateCode\":\"CA\",\"address\":\"49 WEST HILLSDALE BLVD.\"," +
                "\"cashierId\":\"1580344\"}}";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void getTax() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/tax";
        data_local = "{\"maxProductSequence\":1,\"txnUUID\":\"1506578880030059550501449427177\",\"status\":\"NEW\",\"taxCalcMode\":\"AUTO\",\"totalUnits\":1," +
                "\"totalDiscounts\":0,\"preTaxTotal\":35,\"taxTotal\":0,\"taxRate\":0,\"total\":35,\"header\":{\"transactionId\":null,\"datasourceId\":null," +
                "\"salesDate\":\"2016-07-28\",\"transactionType\":\"SalesTransaction\"},\"orderLookupDetails\":[],\"returnPayments\":[]," +
                "\"alternateTenderTypes\":[],\"hasTenderBasedDiscounts\":false,\"products\":[{\"sequence\":0,\"userDefinedId\":0,\"styleCode\":\"612346\"," +
                "\"styleColorNumber\":\"612346002\",\"taxRate\":null,\"taxCharged\":null,\"sellingDivisionId\":\"WHS\",\"qualifyingPromos\":[]," +
                "\"overriddenPrice\":null,\"type\":\"Merch\",\"posCode\":\"260833\",\"sku\":\"6123460020037\",\"sizeCode\":\"0036\",\"taxCode\":\"C2\"," +
                "\"upc\":\"126083311047\",\"finalPrice\":35,\"merchandiseType\":\"Merch\",\"selected\":null,\"price\":\"35\",\"isPriceOverride\":null," +
                "\"originalPrice\":35,\"departmentNumber\":\"304\",\"description\":\"Boys College Team Graphic Tees\",\"discounts\":[],\"quantity\":1," +
                "\"promotionalPrice\":35,\"priceTypeId\":\"1\",\"colorCode\":\"081\",\"isTaxable\":null,\"lineTotalTax\":null,\"code\":null," +
                "\"priceAdjustmentInfo\":{\"priceAdjustmentIndicator\":false,\"mappedDetails\":{}},\"onlineItem\":false}],\"discounts\":[{\"code\":" +
                "\"FNF2015\",\"status\":\"Pending\",\"scannedCode\":\"FNF2015\",\"promotionCode\":null,\"promotionId\":null,\"appliedStatus\":null," +
                "\"promotionType\":null,\"promotionDescription\":null,\"awardId\":null,\"qualifyingTenders\":null,\"membershipId\":null," +
                "\"membershipToken\":null}],\"payments\":[],\"invalidDiscounts\":[],\"multipleProducts\":[],\"priceAdjustTransaction\":false}";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void issueMRC() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/issueMRC";
        data_local = "{\"giftCardNumber\":\"6003877903252141\",\"refundAmount\":\"25.00\",\"approvalCode\":\"\"}";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void printRecepit() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/receipt/print";
        data_local = "";
        commands[4] = "GET";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void submit() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/submit";
        data_local = "";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }
}
