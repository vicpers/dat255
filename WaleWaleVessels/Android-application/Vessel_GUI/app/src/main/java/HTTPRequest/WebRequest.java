package HTTPRequest;

/**
 * Created by maxedman on 2017-04-21.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
     *                  2 = POST
     * @param headers - http request headers. e.g login
     * @param params - http request params, depending on the service. Not working atm. Send null
     */
    public static String makeWebServiceCall(String urladdress, int requestmethod, HashMap<String, String> headers, HashMap<String, String> params) {
        URL url;
        String response = "";
//         Adds parameters to the urladdress string for GETRequests
        boolean first = true;
        if ((requestmethod == GETRequest) && (params != null)){
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
            if (requestmethod == POSTRequest) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            } else if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            }
//TODO Implement the correct way of adding params for POSTRequests. For now it has to be included in the URL-adress string.
            /*if (params != null) {
                OutputStream ostream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(ostream, "UTF-8"));
                StringBuilder requestresult = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first) {
                        first = false;
                        requestresult.append("?");
                    }
                    else {
                        requestresult.append("&?");
                    }
                    requestresult.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    requestresult.append("=");
                    requestresult.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

                }
                writer.write(requestresult.toString());

                writer.flush();
                writer.close();
                ostream.close();
            }*/
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
        } catch (Exception e) {
            e.printStackTrace();
            response = e.toString();
        }
        return response;
    }

}

