package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ServiceEntities.PortCallMessage;
import ServiceEntities.TimeType;

public class ViewPCM extends AppCompatActivity {
    UserLocalStorage userLocalStore;
    //private String[] lv_arr = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pcm);
        Intent intent = getIntent();

        //ArrayList<String> stringList = intent.getStringArrayListExtra("portcalls");

        ArrayList<PortCallMessage> portCallList = UserLocalStorage.getMessageBrokerMap().get(TimeType.ESTIMATED.getText()).getQueue();

        ArrayList<String> stringList = new ArrayList<>();

        for(PortCallMessage pcm : portCallList){
            stringList.add(pcm.toXml());
        }



        // Get a handle to the list view
        ListView lv = (ListView) findViewById(R.id.listView);


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        lv.setAdapter(itemsAdapter);








        /*
        // Get the Intent that started this activity and extract the string

//        HttpUrlConnectionPortCDM portCdmCon = new HttpUrlConnectionPortCDM();
        TextView textView = (TextView) findViewById(R.id.textView2);
//        String wrTest = portCdmCon.getLatestPortCalls(4);
//        String wrTest = portCdmCon.pollQueueTest();
        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue("bcc50867-eada-4aa5-9727-8165e5e92a7b");
//        MessageBrokerQueue msgBrokerQueue = new MessageBrokerQueue();
//        msgBrokerQueue.createUnfilteredQueue();
        msgBrokerQueue.pollQueue();
        String wrTest = msgBrokerQueue.toString();
//        String wrTest = msgBrokerQueue.toString();

//        String vesselInfo = msgBrokerQueue.getQueue().get(0).getVessel().toString();

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


