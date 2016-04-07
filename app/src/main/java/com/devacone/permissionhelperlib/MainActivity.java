package com.devacone.permissionhelperlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devacone.permissionlibrary.R;

public class MainActivity extends AppCompatActivity {

    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionHelper = new PermissionHelper(this);

        // call this method to request all permission needed.
        // just put your needed permission on manifests like usual
        permissionHelper.RequestAllPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionHelper.REQUEST_CODE_ASK_PERMISSIONS) {
            //todo do something when permission granted
        }
    }
}
