package HTTPRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;




public class HttpUrlConnectionPortCDM {

//    public static void main(String[] args){
    public String requestTester(String wrResponse){

        WebRequest wr = new WebRequest();

        /*

        String ipAdress = "http://dev.portcdm.eu";
        String port = "8080";
        String service = "/dmp/port_calls";
        String url = ipAdress + ":" + port + service;
        url += "?count=1";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("count", "1");

        HashMap<String, String> headers = new HashMap<String, String>();
        String username = "viktoria";
        String password = "vik123";
        String apiKey = "eeee";

        headers.put("Accept", "application/json");
        headers.put("X-PortCDM-UserId", username);
        headers.put("X-PortCDM-Password", password);
        headers.put("X-PortCDM-APIKey", apiKey);

        String wrResponse = wr.makeWebServiceCall(url, 1, headers, null);
        System.out.println(wrResponse);
*/
        String jsonString = "";
        try {
            JSONArray jsonArr = new JSONArray(wrResponse);
            jsonString = "Mjemje";
        } catch (JSONException e1){
            jsonString = e1.toString();
            //jsonString = "Hello!";
        }

        return jsonString;
        // Calling method for parsing JSon-string
        /*ArrayList<HashMap<String, Object>> testPcm = wr.parseJson(wrResponse);
        for (HashMap<String, Object> singlePcm : testPcm) {
            singlePcm.toString();
        }*/
    }

/*    public static void main(String[] args){

        String json = "{\"MyResponse\":[{\"foo\":\"fooStr\",\"bar\":\"barStr\"}]}";
        // Calling method for parsing JSon-string
        WebRequest wr = new WebRequest();
        ArrayList<HashMap<String, Object>> testPcm = wr.parseJson(json);
        for (HashMap<String, Object> singlePcm : testPcm) {
            singlePcm.toString();
        }
    }*/

    public ArrayList<String> getPcm(int x){

        try {
            return getLatestPCM(x);
        } catch (IOException e) {

            e.printStackTrace();
//			System.out.println("BREAK");
//			System.out.println(e.toString());
//			System.out.println(e.getMessage());
        }
        return null;
    }

    private ArrayList<String> getLatestPCM(int x) throws IOException {

        //String ipAdress = "http://walewale.portcdm";
        String ipAdress = "http://dev.portcdm.eu";
        String port = "8080";
        String service = "/dmp/port_calls";

        String url = ipAdress + ":" + port + service;
        url += "?count=" + x;

        String username = "viktoria";
        String password = "vik123";
        String apiKey = "eeee";
        //String username = "porter";
        //String password = "porter";

        URL requestURL = new URL(url);

        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("X-PortCDM-UserId", username);
        con.setRequestProperty("X-PortCDM-Password", password);
        con.setRequestProperty("X-PortCDM-APIKey", apiKey);

        int responseCode = con.getResponseCode();
        //System.out.println(responseCode);

        InputStream xmlStream = con.getInputStream();

        // prints all the rows returned
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(xmlStream));
        String inputLine = "";
        ArrayList<String> dataArray = new ArrayList<String>();

        while ((inputLine = inputReader.readLine()) != null) {
            dataArray.add(inputLine);
        }

        xmlStream.close();
        con.disconnect();

        return dataArray;

    }

}
