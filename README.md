# MPermissionHelper
Permission Helper For android M

Description:

if user has android Marshmallow version then, this library get all permission from your manifests file.
find the permission which not granted yet, and ask to user runtime.

Usage :

create the helper class and pass the activity as parameter and request all permission like so:
```
PermissionHelper permissionHelper = new PermissionHelper(this);
```

handle onRequestPermissionsResult on that activity:
```
@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionHelper.REQUEST_CODE_ASK_PERMISSIONS) {
            //todo do something when permission granted
        }
    }
```

Todo :
ask single permission
