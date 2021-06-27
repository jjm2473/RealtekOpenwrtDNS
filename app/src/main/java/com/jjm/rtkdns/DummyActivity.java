package com.jjm.rtkdns;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DummyActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, R.string.started, Toast.LENGTH_LONG).show();

        this.finish();
    }
}
