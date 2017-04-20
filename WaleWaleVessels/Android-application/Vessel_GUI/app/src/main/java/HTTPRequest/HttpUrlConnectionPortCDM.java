package HTTPRequest;

import android.widget.TextView;

import com.example.juliagustafsson.vessel_gui.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;


public class HttpUrlConnectionPortCDM {

	public static void main (String[] args){
		HttpUrlConnectionPortCDM foo = new HttpUrlConnectionPortCDM();
		System.out.println(foo.xmlTester());
	}

    public String xmlTester(){

        try {
            return doGet();
        } catch (IOException e) {

            e.printStackTrace();
//			System.out.println("BREAK");
//			System.out.println(e.toString());
//			System.out.println(e.getMessage());
        }
        return null;
    }

	private String doGet() throws IOException {

		//String ipAdress = "http://walewale.portcdm";
		String ipAdress = "http://dev.portcdm.eu";
		String port = "8080";
		String service = "/dmp/port_calls";
		
		String url = ipAdress + ":" + port + service;
		url += "?count=1";
		//System.out.println(url);
		

		String username = "viktoria";
		String password = "vik123";
        String apiKey = "dhc";
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
        String totLine = "";

	    while ((inputLine = inputReader.readLine()) != null) {
            totLine += inputLine;
	    }

        xmlStream.close();
        //System.out.println(inputLine instanceof String);
        return totLine;
	}

}
