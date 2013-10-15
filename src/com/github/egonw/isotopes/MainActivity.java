package com.github.egonw.isotopes;

import java.io.IOException;

import org.openscience.cdk.config.BODRIsotopes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String ELEMENT_SYMBOL = "com.github.egonw.isotopes.ELEMENT_SYMBOL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // better to be slow at start up then when returning results
		try {
			BODRIsotopes.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void listIsotopes(View view) {
    	Intent intent = new Intent(this, IsotopeList.class);
    	EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(ELEMENT_SYMBOL, message);
        startActivity(intent);
    }
}
