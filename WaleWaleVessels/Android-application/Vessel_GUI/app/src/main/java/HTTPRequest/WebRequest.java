package HTTPRequest;

/**
 * Created by maxedman on 2017-04-21.
 */


import android.util.Log;

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
    //static String response = "";
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
            StringBuilder sb = new StringBuilder(urladdress);
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (first){
                    sb.append("?");
                    first = false;
                } else
                    sb.append("&?");
                sb.append(param.getKey());
                sb.append("=");
                sb.append(param.getValue());
            }
            urladdress = sb.toString();
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

            if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = null;
                StringBuilder sb = new StringBuilder();
                try {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    if (br != null) {
                        while ((line = br.readLine()) != null) {
                             sb.append(line);
                        }
                    }
                    response += sb.toString();

                } catch (Exception e){
                    Log.e("Exception - WebRequest", e.toString());
                    response = "" + reqresponseCode;
                } finally {
                    // Closes down the reader and builder.
                    if (br != null)
                        br.close();
                }

            } else {
                Log.e("WebRequestHTTPConn", "Not OK!");
                Log.e("ResponseCode", "" + reqresponseCode);
                Log.e("URL", urladdress);
                response = "WebRequestHTTPConnection is not OK!" +
                            "\nResponseCode: " + reqresponseCode +
                            "\nURL: " + urladdress;
            }
        } catch (IOException e) {
            Log.e("Exception", e.toString());
            response = e.toString();
        }
        return response;
    }

    public static String makeWebServicePost(String urladdress, HashMap<String, String> headers, HashMap<String, String> params, String body){
        String response = "";
        try {
            URL url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = null;
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

                StringBuilder sb = new StringBuilder(response);
                int reqresponseCode = conn.getResponseCode();
                if (reqresponseCode == HttpsURLConnection.HTTP_CREATED) {
                    String line;
                    try {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (Exception e) {Log.e("Exception", e.toString());}
                } else if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    try{
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (Exception e){Log.e("Exception", e.toString());};

                } else if (reqresponseCode == HttpsURLConnection.HTTP_ACCEPTED) {
                    String line;
                    try{
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (Exception e){Log.e("Exception", e.toString());};

                } else {
                    Log.e("ResponseCode", "" + reqresponseCode);
                    Log.e("URL", urladdress);
                    Log.e("BODY", body);
                    Log.e("HEADERS", headers.toString());
                    String line;
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.e("RESPONSE: ", response);
                }
                // Converts the StringBuilder to a String
                response = sb.toString();
            } finally {
                if (br != null)
                    br.close();
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


