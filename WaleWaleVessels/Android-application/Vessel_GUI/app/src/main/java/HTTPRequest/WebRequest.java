package HTTPRequest;

/**
 * Created by maxedman on 2017-04-21.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WebRequest {
    static String response = null;
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;

    /**
     * Making web service call
     *
     * @url - url to make web request
     * @requestmethod - http request method
     */
    public static String makeWebServiceCall(String url, int requestmethod) {
        return makeWebServiceCall(url, requestmethod, null, null);
    }

    /**
     * Making simple web service call
     *
     * @param urladdress- url to make web request
     * @param requestmethod - http request method
     *                  1 = GET
     *                  2 = POST - NOT SUPPORTED
     * @param headers - http request headers. e.g login
     * @param params - http request params, depending on the service. Not working atm. Send null
     *
     */
    public static String makeWebServiceCall(String urladdress, int requestmethod, HashMap<String, String> headers, HashMap<String, String> params) {
        URL url;
        String response = "";
//         Adds parameters to the urladdress string for GETRequests
        boolean first = true;
        if ((params != null) && (requestmethod == 1)){
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (first){
                    urladdress += "?" + param.getKey() + "=" + param.getValue();
                    first = false;
                } else {
                    urladdress += "&?" + param.getKey() + "=" + param.getValue();
                }
            }
        }
        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);
            if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int reqresponseCode = conn.getResponseCode();
            System.out.println(reqresponseCode);

            if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                System.out.println("WebRequestHTTPConnection is not OK!");
                System.out.println("ResponseCode: " + reqresponseCode);
                System.out.println("URL: " + urladdress);
                response = "WebRequestHTTPConnection is not OK!" +
                            "\nResponseCode: " + reqresponseCode +
                            "\nURL: " + urladdress;
            }
            if (requestmethod == 2)
                response = reqresponseCode + "";
        } catch (Exception e) {
            e.printStackTrace();
            response = e.toString();
        }
        return response;
    }



    public static String makeWebServicePost(String urladdress, HashMap<String, String> headers, HashMap<String, String> params, String body){

        try {
            URL url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try {
                if (headers != null) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        conn.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(body);
                writer.flush();
                writer.close();
                os.close();

                int reqresponseCode = conn.getResponseCode();
                if (reqresponseCode == HttpsURLConnection.HTTP_CREATED) {
                    String line;
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } catch (Exception e) {
                        response = "" + reqresponseCode;
                    }
                } else if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    try{
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    } catch (Exception e){
                        response = "" + reqresponseCode;
                    }
                } else {
//                    System.out.println("WebRequestHTTPConnection is not OK!");
                    System.out.println("ResponseCode: " + reqresponseCode);
                    System.out.println("URL: " + urladdress);
                    System.out.println("BODY: " + body);
                    System.out.println("HEADERS: " + headers);
                    /*response = "WebRequestHTTPConnection is not OK!" +
                            "\nResponseCode: " + reqresponseCode +
                            "\nURL: " + urladdress;*/
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    System.out.println("RESPONSE: " + response);
                }
            } finally {
                conn.disconnect();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}


