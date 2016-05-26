package com.padeoe.testnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by padeoe on 2016/5/23.
 */
public class ScanResultReceiver extends BroadcastReceiver {
    WifiManager wifiManager= (WifiManager)App.getAppContext().getSystemService(Context.WIFI_SERVICE);
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("已接收到扫描结果");
        List<ScanResult> scanResults= wifiManager.getScanResults();
        for(ScanResult s:scanResults){
            System.out.println(s.toString());
        }
        //wifi结果分析完毕，无论是否找到有效wifi都关闭扫描结果的接收
        //  WiFiScanner.endScan();
    }
}
