package com.example.juliagustafsson.vessel_gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.UUID;

import RESTServices.AMSS;
import ServiceEntities.ArrivalLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;

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
//        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue("19531434-b926-4a2a-aae0-949f8bec9442");
//        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue();
//        msgBrokerQueue.createUnfilteredQueue();
//        msgBrokerQueue.pollQueue();
//        String wrTest = msgBrokerQueue.toString();
//        String wrTest = msgBrokerQueue.toString();
//        textView.setText(wrTest);

        ArrivalLocation arrLoc = new ArrivalLocation(null, new Location(null, new Position(0,0, "Gothenburg"), LocationType.TRAFFIC_AREA));
        LocationState locState = new LocationState("VESSEL", "2017-04-26T19:00:00.000Z", "ESTIMATED", arrLoc, null);
        PortCallMessage pcm = new PortCallMessage("urn:mrn:stm:vessel:IMO:9501368",
                                                  "urn:mrn:stm:portcdm:message:" + UUID.randomUUID().toString(),
                                                  "MaxJavaTest",
                                                    locState);
        System.out.println(pcm.toXml());
        AMSS amss = new AMSS(pcm);
        amss.submitStateUpdate();





    }

}


