/* Copyright (C) 2013  Egon Willighagen <egonw@users.sf.net>
 *
 * PinDroid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * PinDroid is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PinDroid; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */
package com.github.egonw.isotopes;

import java.io.IOException;

import org.openscience.cdk.config.Isotopes;
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
			editText.setText("Unrecognized element symbol: " + elementSymbol);
			return;
		} else if (elementSymbol.length() == 1) {
			elementSymbol = elementSymbol.toUpperCase();
		} else if (elementSymbol.length() > 1) {
			elementSymbol = elementSymbol.substring(0, 1).toUpperCase() +
				elementSymbol.substring(1).toLowerCase();
		}
		Isotopes factory = null;
		try {
			factory = Isotopes.getInstance();
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
