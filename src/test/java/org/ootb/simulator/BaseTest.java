package org.ootb.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by jiazhao on 10/3/17.
 */
public class BaseTest {

    protected String[] commands;
    protected String txnUUID = "";

    protected String exec() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(commands);
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while((line = reader.readLine())!=null){
            sb.append(line + "\n");
        }

        String result = sb.toString();
        System.out.println(result);
        return result;
    }

    protected void fetchTxnUUID(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String,String> hashMap = new HashMap();
        hashMap = objectMapper.readValue(json,hashMap.getClass());
        if(hashMap != null) {
            txnUUID = hashMap.get("txnUUID");
        }
    }
}
