package com.example.maxedman.walewale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import HTTPRequest.HttpUrlConnectionTester;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    printXml();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void printXml(){

        HttpUrlConnectionTester apiTester = new HttpUrlConnectionTester();
        //String testXML = apiTester.xmlTester();
        //System.out.println(apiTester.test());
        TextView myXmlView = (TextView)findViewById(R.id.xmlMessage);
        myXmlView.setText(apiTester.test());
        //TextView myXmlView = (TextView)findViewById(R.id.xmlMessage);
        //myXmlView.setText(testXML);
    }

    public void testSetText(){

        TextView myXmlView = (TextView)findViewById(R.id.xmlMessage);
        myXmlView.setText("Hello World!");
    }


}
