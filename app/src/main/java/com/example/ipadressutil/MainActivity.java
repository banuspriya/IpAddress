package com.example.ipadressutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity {


    EditText ip,subnet;
    TextView result;
    Button networkAddress, cidrNotation;
    String cidr,iptext,subnettext ,networkAddressText;
    String binaryValue;
    private String cidrtext,networkAddressTextComputed;
    char[] ipBinary = new char[32];
    char[] subnetBinary = new char[32];
    int [] networkBinary = new int [32];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = (EditText)findViewById(R.id.Ip);
        subnet= (EditText) findViewById(R.id.Subnet);
        result = (TextView) findViewById(R.id.result);
        networkAddress = (Button) findViewById(R.id.NetworkAddress);
        cidrNotation = (Button) findViewById(R.id.CIDR);
        iptext = ip.getText().toString();
        subnettext = subnet.getText().toString();

        cidrNotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cidrtext = Computecidr(iptext,subnettext);
                result.setText("cidr " + cidr);
            }
        });

        networkAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkAddressText = computeNetworkAdd(iptext ,subnettext);
                result.setText("NetworkAdd " + networkAddressText);
            }
        });
    }
//compute the NetworkAddress  with the given ip address & subnet address

    private String computeNetworkAdd(String iptext, String subnettext) {
        String ipString = ComputeBinary(iptext);
        ipBinary= ipString.toCharArray();
        String subnetString = ComputeBinary(subnettext);
        subnetBinary= subnetString.toCharArray();
        for (int i =0 ; i< ipBinary.length; i++ )
        {
            networkBinary[i] = (ipBinary[i] - 48) & (subnetBinary[i]- 48);
        }

        for (int j = 0 ; j <4 ;j++)
        {
            String num = "";
            for( int k = 0 ; k<8 && k <networkBinary.length; k ++)
            {
                num+=networkBinary[k];
            }
            networkAddressTextComputed += Integer.parseInt(num,2);
           if(j<3)
            networkAddressTextComputed+= ".";
        }
        return networkAddressTextComputed;
    }
//compute the Cidr  with the given ip address & subnet address

    private String Computecidr(String ip , String subnet) {
       String subnetBinary = ComputeBinary(subnet);
       int count = 0 ;
       for(int i = 0 ;i <subnetBinary.length();i++)
       {
           if(subnetBinary.charAt(i)==1)
           {
               count++;
           }

           else
           {
               cidr =ip + "/" + count ;
           }
       }
        return cidr;
    }

//compute the binary equivalent of the given address
    private String ComputeBinary (String ip)
    {
        String[] arr = ip.split("\\.");
         binaryValue = "";
        for (String str : arr) {
            String s = Integer.toBinaryString(Integer.parseInt(str));
            if (s.length() < 8) {
                int diff = 8-s.length();
                for (int i = 0; i < diff; i++) {
                    s = "0" + s;
                }
            }
            binaryValue += s;
        }
        return binaryValue;

    }
}