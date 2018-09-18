package com.mediamonks.testkiosk;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_text)
    TextView _mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        checkStartKioskMode();

        setImmersiveMode();
        _mainText.setOnClickListener(v -> setImmersiveMode());
    }

    private void checkStartKioskMode() {
        DevicePolicyManager manager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (manager == null) return;

        if (manager.isDeviceOwnerApp(getApplicationContext().getPackageName())) {
            manager.setLockTaskPackages(DeviceOwnerReceiver.getComponentName(getApplicationContext()), new String[]{getPackageName()});

            startLockTask();
        }
    }

    private void setImmersiveMode() {
        _mainText.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }
}
