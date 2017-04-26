package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import RESTServices.MessageBrokerQueue;

public class ViewPCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);
        // Get the Intent that started this activity and extract the string

//        HttpUrlConnectionPortCDM portCdmCon = new HttpUrlConnectionPortCDM();
        TextView textView = (TextView) findViewById(R.id.textView2);
//        String wrTest = portCdmCon.getLatestPortCalls(4);
//        String wrTest = portCdmCon.pollQueueTest();
        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue("507fecd5-e35a-4461-ab01-57cfb22f8ca7");
//        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue();
//        msgBrokerQueue.createUnfilteredQueue();
        msgBrokerQueue.pollQueue();
//        String wrTest = msgBrokerQueue.toString();
        String wrTest = msgBrokerQueue.toString();
        textView.setText(wrTest);

        /*ArrivalLocation arrLoc = new ArrivalLocation(null, new Location(null, new Position(0,0, "Gothenburg"), LocationType.TRAFFIC_AREA));
        LocationState locState = new LocationState("VESSEL", "2017-04-26T19:00:00.000Z", "ESTIMATED", arrLoc, null);
        PortCallMessage pcm = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                                                  "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                  "MaxJavaTest",
                                                    locState);
        System.out.println(pcm.toXml());
        AMSS amss = new AMSS(pcm);
        amss.submitStateUpdate();*/





    }

}


