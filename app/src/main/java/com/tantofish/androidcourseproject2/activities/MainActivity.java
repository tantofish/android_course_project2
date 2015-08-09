package com.tantofish.androidcourseproject2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tantofish.androidcourseproject2.R;
import com.tantofish.androidcourseproject2.adapters.ImageResultsAdapter;
import com.tantofish.androidcourseproject2.fragments.PreferenceDialog;
import com.tantofish.androidcourseproject2.interfaces.EndlessScrollListener;
import com.tantofish.androidcourseproject2.models.ImageResult;
import com.tantofish.androidcourseproject2.models.SearchFilter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private EditText etQuery;
    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private FragmentManager fm;
    private PreferenceDialog preferenceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupActionbar();
        setupFragments();

        //showEditDialog();
        // Create the data source
        imageResults = new ArrayList<ImageResult>();
        // Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        // Link the adapter to the adapterview(gridview)
        gvResults.setAdapter(aImageResults);
    }

    private void setupFragments() {
        fm = getSupportFragmentManager();
        preferenceDialog = PreferenceDialog.newInstance(this);
    }

    private void setupActionbar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.custom_actionbar, null);
        getSupportActionBar().setCustomView(actionbarLayout);
        View abView = getSupportActionBar().getCustomView();

        etQuery = (EditText) abView.findViewById(R.id.etQuery);

    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                // Creating an intent
                Intent i = new Intent(MainActivity.this, ImageDisplayActivity.class);
                // Get the image result to display
                ImageResult result = imageResults.get(position);
                // Pass image result into the intent
                i.putExtra("result", result);
                // launch the new activity
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                searchMore((page - 1) * 8);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onSettingClick(View view) {
        preferenceDialog.show(fm, "preference_fragment");
        SearchFilter mSF = preferenceDialog.getSearchFilter();
        Log.d("DEBUG", "imageColor: " + mSF.getImageColor());
        Log.d("DEBUG", "imageSize: " + mSF.getImageSize());
        Log.d("DEBUG", "imageType: " + mSF.getImageType());
        Log.d("DEBUG", "SearchSite: " + mSF.getSearchSite());
    }

    private String getSearchUrl() {
        return getSearchUrl(0);
    }

    private String getSearchUrl(int start) {
        String imageColor = preferenceDialog.getSearchFilter().getImageColor();
        String imageSize = preferenceDialog.getSearchFilter().getImageSize();
        String imageType = preferenceDialog.getSearchFilter().getImageType();
        String searchSite = preferenceDialog.getSearchFilter().getSearchSite();
        String query = etQuery.getText().toString();

        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8"
                + "&imgsz=" + imageSize
                + "&imgcolor=" + imageColor
                + "&imgtype=" + imageType
                + "&as_sitesearch=" + searchSite
                + "&start=" + start;

        Log.d("DEBUG", "Search URL: " + searchUrl);

        return searchUrl;
    }

    private void searchMore(int start) {
        AsyncHttpClient client = new AsyncHttpClient();

        String searchUrl = getSearchUrl(start);

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();   // clear previous search results
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideKeyBoard();
                Log.d("DEBUG", "Search Result: " + imageResults.toString());
            }
        });
    }
    public void onImageSearch(View v) {

        AsyncHttpClient client = new AsyncHttpClient();

        String searchUrl = getSearchUrl();

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();   // clear previous search results
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideKeyBoard();
                Log.d("DEBUG", "Search Result: " + imageResults.toString());
            }
        });
    }

    private void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
