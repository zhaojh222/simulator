package org.ootb.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
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
public class LocalChaseTest extends BaseTest{

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
        url_dev  = "https://api-devshowcase.selling.gapinc.dev/orders/";
        data_dev = "{\"scanCode\":\"1207580010000\",\"store\":{\"storeNumber\":\"05955\",\"registerNumber\":\"002\",\"brandName\":\"Athleta\",\"brandCode\":\"10\"," +
                "\"brandAbbreviation\":\"Athleta\",\"zipcode\":\"94115\",\"serialNumber\":null,\"lasDeviceId\":null,\"marketCode\":\"US\",\"localeCode\":\"en_US\"," +
                "\"localDate\":\"2017-10-04T13:07:22.352-0700\",\"appName\":\"SNAP_SELL\",\"stateCode\":\"NY\",\"cashierId\":\"1580344\",\"city\":\"NEW YORK\"," +
                "\"address\":\"610 AVENUE OF THE AMERICAS\",\"phoneNumber\":\"(212) 645-0663\"}}";

        commands = new String[]{"curl","-H","Content-Type:application/json","-X","POST","-d","",""};
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testSaleWithSubmit() throws IOException, InterruptedException {
        createNewOrder();
        addProduct();
        applyDiscount();
        getTax();
        addPayment();
        printRecepit();
        emailRecepit();
        receiptEJournals();
        submit();
    }

    @Test
    public void testSaleWithCancel() throws IOException, InterruptedException {
        createNewOrder();
        addProduct();
        applyDiscount();
        getTax();
//        addPayment();
        cancel();
    }

//    @Test
    public void createNewOrder() throws IOException, InterruptedException {
        url_local = baseUrl;
        data_local = "{\"scanCode\":\"1417000010000\",\"store\":{\"storeNumber\":\"05955\",\"registerNumber\":\"050\",\"brandName\":\"Old Navy\",\"brandAbbreviation\": \"ON\",\"zipcode\":\"94403\"," +
                "\"sellingDivisionId\":\"Old Navy\",\"serialNumber\":\"testDevice-serial-no\",\"lasDeviceId\":\"3c651925-d54c-4798-9fb8-c8855ae30554\",\"localDate\":" +
                "\"2017-10-25T23:08:00.030-0700\",\"appName\":\"Snap_Sell\",\"brandCode\":\"10\",\"city\":\"SAN MATEO\",\"phoneNumber\":\"(650) 638-0199\",\"localeCode\":" +
                "\"en_US\",\"marketCode\":\"US\",\"storeName\":\"Some Name\",\"stateCode\":\"CA\",\"address\":\"49 WEST HILLSDALE BLVD.\",\"cashierId\":\"1580344\"," +
                "\"storeProductDataFromHBase\":\"YES\",\"receiptConfig\":{\"footer\":\"my footer\"}}}";
        commands[6] = data_local;
        commands[7] = url_local;
        String result = exec();
        fetchTxnUUID(result);
    }

    private void addProduct() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID;
        data_local = "{\"scanCode\":\"FNF2015\",\"store\":{\"storeNumber\":\"05955\",\"registerNumber\":\"050\",\"brandName\":\"Old Navy\",\"zipcode\":\"94403\"," +
                "\"sellingDivisionId\":\"Old Navy\",\"serialNumber\":\"testDevice-serial-no\",\"lasDeviceId\":\"3c651925-d54c-4798-9fb8-c8855ae30554\",\"localDate\":" +
                "\"2016-08-01T23:08:00.030-0700\",\"appName\":\"Snap_Sell\",\"brandCode\":\"10\",\"city\":\"SAN MATEO\",\"phoneNumber\":\"(650) 638-0199\"," +
                "\"localeCode\":\"en_US\",\"marketCode\":\"US\",\"storeName\":\"Some Name\",\"stateCode\":\"CA\",\"address\":\"49 WEST HILLSDALE BLVD.\"," +
                "\"cashierId\":\"1580344\",\"storeProductDataFromHBase\":\"YES\",\"receiptConfig\":{\"footer\":\"my footer\"}}}";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void applyDiscount() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/discounts/apply";
        data_local = "{\"maxProductSequence\":1,\"txnUUID\":\"1504850880030059550501114396370\",\"status\":\"NEW\",\"taxCalcMode\":\"AUTO\",\"totalUnits\":1," +
                "\"totalDiscounts\":0,\"preTaxTotal\":35,\"taxTotal\":0,\"taxRate\":0,\"total\":35,\"header\":{\"transactionId\":null,\"datasourceId\":null," +
                "\"salesDate\":\"2016-08-01\",\"transactionType\":\"SalesTransaction\"},\"orderLookupDetails\":[],\"returnPayments\":[],\"alternateTenderTypes\":[]," +
                "\"hasTenderBasedDiscounts\":false,\"products\":[{\"sequence\":0,\"userDefinedId\":0,\"styleCode\":\"612346\",\"styleColorNumber\":\"612346002\"," +
                "\"taxRate\":null,\"taxCharged\":null,\"sellingDivisionId\":\"WHS\",\"qualifyingPromos\":[],\"overriddenPrice\":null,\"type\":\"Merch\",\"posCode\":" +
                "\"260833\",\"sku\":\"6123460020037\",\"sizeCode\":\"0036\",\"taxCode\":\"C2\",\"upc\":\"126083311047\",\"finalPrice\":35,\"merchandiseType\":" +
                "\"Merch\",\"selected\":null,\"price\":\"35\",\"isPriceOverride\":null,\"originalPrice\":35,\"departmentNumber\":\"304\",\"description\":" +
                "\"Boys College Team Graphic Tees\",\"discounts\":[],\"quantity\":1,\"promotionalPrice\":35,\"priceTypeId\":\"1\",\"colorCode\":\"081\"," +
                "\"isTaxable\":null,\"lineTotalTax\":null,\"code\":null,\"priceAdjustmentInfo\":{\"priceAdjustmentIndicator\":false,\"mappedDetails\":{}}," +
                "\"onlineItem\":false,\"finalSale\":false}],\"discounts\":[{\"code\":\"FNF2015\",\"status\":\"Pending\",\"scannedCode\":\"FNF2015\"," +
                "\"promotionCode\":null,\"promotionId\":null,\"appliedStatus\":null,\"promotionType\":null,\"promotionDescription\":null,\"awardId\":null," +
                "\"qualifyingTenders\":null,\"membershipId\":null,\"membershipToken\":null}],\"payments\":[],\"invalidDiscounts\":[],\"multipleProducts\":[]," +
                "\"priceAdjustTransaction\":false}";
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

    private void addPayment() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/payment";
        data_local = "{\"tenderType\":\"MRC\",\"id\":\"1\",\"platform\":\"IOS\",\"processedAmount\":21.99,\"requestedAmount\":21.99,\"encryptedData\":" +
                "\"Q2xvlmVDSEpiYTrNe7wQQACNHyLMUo9jHS4Fu6ubGHQAbLKHjzHlHgMxZqdv7gYBSfuz+j0koAMjK56e6d+QjrLicu2NwWeFVvCrrv9N8A9LN8z+1NiUBkyL" +
                "YoaBcakWk1leR59NNAH2yLft4/T4Zfobq5BztqT0QJ3UaOXIJi9ylXGqGGIicQQPm2Jq/lkSzqwyc6+8mtAQ37kfOKMezilW+gFptLLEsN9bNi9YfWQGY57jALE7" +
                "jptUFl8NnrawDRd3l/XHgXUh74UM59P+N+nQeq0+LtFSQM7OQo1e6nI4SpGXoCgNlTR/2YFi4+Vy2/VC0IZ/T/g/qW2A8nWAXw==\",\"publicKeyHash\":" +
                "\"08a33bb56673fd8d3d298c29b18e663a5d2321c82e5009554c364c4af11ac691041254ae6ba6d7f019804434b5e2e0c3451e4aaa591e97812bbaddcc000ceffa\"," +
                "\"encryptionVersion\":\"v2\",\"cardNumber\":\"XXXXXXXXXXXX0119\",\"cardBrand\":\"MRC\",\"resultCode\":\"1[49]\",\"transactionId\":\"0831\"," +
                "\"referenceNumber\":\"00000002\",\"approvalCode\":\"083414\",\"date\":\"16/06/29\",\"cardExpirationDate\":\"1222\",\"time\":\"15:32:26\"," +
                "\"isApproved\":true,\"transmissionNumber\":\"0000\",\"bankSequenceNumber\":\"000041\",\"entryMethod\":\"MagSwipe\",\"pinDisclaimerTextRequired\":" +
                "\"false\",\"aid\":null,\"tvr\":null,\"tsi\":null,\"name\":\"VISA ACQUIRER TEST CARD 01\"}";
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

    private void cancel() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/cancel";
        data_local = "";
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

    private void emailRecepit() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/receipt/email";
        data_local = "{\"emailAddress\":\"zhaojh221@gmail.com\"}";
        commands[4] = "POST";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

    private void receiptEJournals() throws IOException, InterruptedException {
        url_local = baseUrl + txnUUID + "/receipt/ejJournal/email";
        data_local = "";
        commands[4] = "POST";
        commands[6] = data_local;
        commands[7] = url_local;
        exec();
    }

}
