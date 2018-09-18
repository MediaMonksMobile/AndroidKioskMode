# AndroidKioskMode
Sample app for activating Kiosk Mode on Android

In order to make an app device owner, follow these steps:
 * install the app; it will not be device owner
 * make sure you have developer options enabled, and have debug access to the device
 * run the following terminal command:
		``adb shell dpm set-device-owner {package/path-to-DeviceOwnerReceiver}``
 * example for the sample app:
		``adb shell dpm set-device-owner com.mediamonks.testkiosk.debug/com.mediamonks.testkiosk.DeviceOwnerReceiver``
 * restart the app; it will now be a device owner

The following application parts are mandatory to allow an app to start in Kiosk Mode  
 * a class extending android.app.admin.DeviceAdminReceiver, see DeviceOwnerReceiver
 * an xml describing the policy components; see /app/src/main/res/xml/device_owner_receiver.xml
 * an entry for the receiver in the AndroidManifest class, as follows::
    ``<receiver  
        android:name=".DeviceOwnerReceiver"  
        android:description="@string/app_name"  
        android:label="@string/app_name"  
        android:permission="android.permission.BIND_DEVICE_ADMIN"  
        android:exported="true"  
        >  
        <meta-data  
            android:name="android.app.device_admin"  
            android:resource="@xml/device_owner_receiver"/>  
        <intent-filter>  
            <action android:name="android.app.action.DEVICE_ADMIN_ENABLED"/>  
            <action android:name="android.app.action.PROFILE_PROVISIONING_COMPLETE"/>  
            <action android:name="android.intent.action.BOOT_COMPLETED"/>  
            <action android:name="android.app.action.PROFILE_OWNER_CHANGED"/>  
            <action android:name="android.app.action.DEVICE_OWNER_CHANGED"/>  
        </intent-filter>  
    </receiver>``
 * The following code in the MainActivity:
    private void checkStartKioskMode() {
        DevicePolicyManager manager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (manager == null) return;

        if (manager.isDeviceOwnerApp(getApplicationContext().getPackageName())) {
            manager.setLockTaskPackages(DeviceOwnerReceiver.getComponentName(getApplicationContext()), new String[]{getPackageName()});

            startLockTask();
        }
    }
	