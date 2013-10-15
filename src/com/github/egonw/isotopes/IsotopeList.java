package com.github.egonw.isotopes;

import java.io.IOException;

import org.openscience.cdk.config.BODRIsotopes;
import org.openscience.cdk.interfaces.IIsotope;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class IsotopeList extends Activity {

	@SuppressLint({ "NewApi", "DefaultLocale" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_isotope_list);

		Intent intent = getIntent();
		String elementSymbol = intent.getStringExtra(MainActivity.ELEMENT_SYMBOL);
		TextView editText = (TextView) findViewById(R.id.output);
		editText.setTextSize(25);
		if (elementSymbol.length() > 3) {
			editText.setText("Unlikely element: " + elementSymbol);
			return;
		} else if (elementSymbol.length() == 1) {
			elementSymbol = elementSymbol.toUpperCase();
		} else if (elementSymbol.length() > 1) {
			elementSymbol = elementSymbol.substring(0, 1).toUpperCase() +
				elementSymbol.substring(1).toLowerCase();
		}
		BODRIsotopes factory = null;
		try {
			factory = BODRIsotopes.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String isotopeList = "";
		if (factory != null) {
			IIsotope[] isotopes = factory.getIsotopes(elementSymbol);
			if (isotopes.length == 0) {
				isotopeList = "No isotopes found for " + elementSymbol + ".";
			}
			for (IIsotope isotope : isotopes) {
				isotopeList += isotope.getMassNumber() +
					isotope.getSymbol() + ": ";
				if (isotope.getExactMass() != null)
					isotopeList += isotope.getExactMass();
				if (isotope.getNaturalAbundance() != null) {
					isotopeList += ", abundance: " + isotope.getNaturalAbundance() + "\n";
				}
			}
		}
		editText.setText(isotopeList);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
