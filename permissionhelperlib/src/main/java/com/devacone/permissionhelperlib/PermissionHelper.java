package com.devacone.permissionhelperlib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marzellamega on 3/21/16.
 */
public class PermissionHelper {

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private AppCompatActivity context;

    private AlertDialog dialog;

    private boolean isDestroyed = false;

    private final List<String> permissionsList = new ArrayList<>();

    public PermissionHelper(AppCompatActivity context) {
        this.context = context;
    }

    public void RequestAllPermission() {

//         start permission request for android M
        List<String> permissionsNeeded = new ArrayList<>();

        try {
            PackageInfo info = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (!addPermission(permissionsList, p)) {
                        permissionsNeeded.add(p.substring(p.lastIndexOf(".") + 1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You have to allow permission to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                if (dialog == null) {
                    dialog = showYesNoDialog(message, context,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            positiveButtonResponse();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            negativeButtonResponse();
                                            break;
                                    }
                                }
                            });
                } else {
                    dialog.show();
                }
                return;
            }
            ActivityCompat.requestPermissions(context, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            context.onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, new String[]{}, new int[]{PackageManager.PERMISSION_GRANTED});
        }
    }

    private void negativeButtonResponse() {
        positiveButtonResponse();
    }

    private void positiveButtonResponse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission))
                return false;
        }
        return true;
    }

    private AlertDialog showYesNoDialog(String message, Context context, DialogInterface.OnClickListener listener) {
        return showYesNoDialog(message, context, listener, "Yes", "No");
    }

    private AlertDialog showYesNoDialog(String message, Context context, DialogInterface.OnClickListener listener
            , String yesOption, String noOption) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(yesOption, listener)
                .setNegativeButton(noOption, listener);
        return builder.show();
    }

}
