package com.example.androidposlinkdemo;

import android.app.Application;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pax.us.ecr.compat.POSLinkDependencyCompat;
import com.paxus.pay.contract.device.ICameraScanner;
import com.paxus.pay.contract.device.ISerialPort;
import com.paxus.pay.contract.device.exception.PortException;

import java.util.List;

public class App extends Application implements POSLinkDependencyCompat.POSLinkProcessStrategy {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("APP on create.");
        POSLinkDependencyCompat.setProcessStrategy(this);
        System.out.println("APP on create done.");
    }


    @Nullable
    @Override
    public Bundle onProcessLegacyCommand(String cmd, ContentValues request) {
        System.out.println("0..");
        return null;
    }

    @Override
    public void onStorePeteCommTypeForIdle(String type) {
        System.out.println("1..");
    }

    @Override
    public void onRebootApproved(@Nullable String launchHostPackage) {
        System.out.println("2..");
    }

    @Override
    public ProviderInfo onSelectProviderToRunCmd(List<ResolveInfo> pkgInfoList) throws POSLinkDependencyCompat.SelectHostException {
        System.out.println("3..");
        return null;
    }

    @Override
    public Notification onBuildPeteServiceNotification(boolean exit) {
        System.out.println("4..");

        if (exit) {
            return ServiceNotificationSet.getInstance()
                    .removeContentText(1)
                    .buildNotification(this, getPackageName(), R.drawable.ic_launcher_background);
        } else {
            return ServiceNotificationSet.getInstance()
                    .addContentText(1, "PETE service is running")
                    .buildNotification(this, getPackageName(), R.drawable.ic_launcher_background);
        }
    }

    @Override
    public boolean isAppForeground(@Nullable String pkgNameCanRunCmd) {
        System.out.println("5..");
        return false;
    }

    @Override
    public void onStoreHostTransContext(String hostPkgToRunTrans, Context context, String source) {
        System.out.println("6..");
    }

    @Override
    public byte[] tDES(byte[] dataIn, byte[] keyIn, boolean encrypt) {
        System.out.println("7..");
        return new byte[0];
    }

    @Override
    public ISerialPort onInitSerialPortConnect(String format, boolean usb) throws PortException {
        System.out.println("8..");
        return null;
    }

    @Override
    public ICameraScanner onCreateCameraScanner(int idx, int timeoutMs) {
        System.out.println("9..");
        return null;
    }


}
