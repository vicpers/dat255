package HTTPRequest;

import android.widget.TextView;

import com.example.maxedman.walewale.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;


public class HttpUrlConnectionTester {

    public String test(){
        return "Hello World MAX!";
    }


    public String xmlTester(){
        String returnXml = "";
        try {
            returnXml = doGet();
        } catch (IOException e) {

            e.printStackTrace();
//			System.out.println("BREAK");
//			System.out.println(e.toString());
//			System.out.println(e.getMessage());
        }
        return returnXml;
    }

	private String doGet() throws IOException {

		//TODO Enter your base64 encoded Username:Password
		//String ipAdress = "http://192.168.100.101";
		String ipAdress = "http://dev.portcdm.eu";
		String port = "8080";
		String service = "/dmp/port_calls";
		
		String url = ipAdress + ":" + port + service;
		url += "?count=1";
		//System.out.println(url);
		
		String apiKey = "dhc";
		String username = "viktoria";
		String password = "vik123";

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

        return totLine;

	}

}
