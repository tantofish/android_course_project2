package com.tantofish.androidcourseproject2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tantofish.androidcourseproject2.R;
import com.tantofish.androidcourseproject2.models.SearchFilter;

/**
 * Created by yutu on 8/8/15.
 */
public class PreferenceDialog extends DialogFragment {


    private Context mContext;

    private String[] imageSizeOptions;
    private String[] imageColorOptions;
    private String[] imageTypeOptions;

    private Spinner spSize;
    private Spinner spColor;
    private Spinner spType;
    private EditText etSite;

    private ArrayAdapter aaSize;
    private ArrayAdapter aaColor;
    private ArrayAdapter aaType;

    private SearchFilter sFilter;

    public PreferenceDialog() {
        sFilter = new SearchFilter();
        imageSizeOptions = new String[]{"default", "small", "medium", "large", "extra-large"};
        imageColorOptions = new String[]{"default", "black", "white", "red", "green", "blue", "yellow", "brown", "gray", "orange", "pink", "purple", "teal"};
        imageTypeOptions = new String[]{"default", "face", "photo", "clipart", "lineart"};
    }

    public static PreferenceDialog newInstance(Context context) {
        PreferenceDialog frag = new PreferenceDialog();
        frag.setContext(context);
        //Bundle args = new Bundle();
        //args.putString(key, value);
        //frag.setArguments(args);
        return frag;
    }

    public void setContext(Context context){
        this.mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preference, container);


        spSize  = (Spinner)  view.findViewById(R.id.spSize);
        spColor = (Spinner)  view.findViewById(R.id.spColor);
        spType  = (Spinner)  view.findViewById(R.id.spType);
        etSite  = (EditText) view.findViewById(R.id.etSite);

        aaSize  = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, imageSizeOptions);
        aaColor = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, imageColorOptions);
        aaType  = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, imageTypeOptions);

        aaSize.setDropDownViewResource(android.R.layout.simple_list_item_1);
        aaColor.setDropDownViewResource(android.R.layout.simple_list_item_1);
        aaType.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spSize.setAdapter(aaSize);
        spColor.setAdapter(aaColor);
        spType.setAdapter(aaType);

        getDialog().setTitle("Searching Preference");
        return view;
    }

    public SearchFilter getSearchFilter(){
        if(spColor == null) return sFilter;

        sFilter.setImageColor(spColor.getSelectedItem().toString());
        sFilter.setImageSize(spSize.getSelectedItem().toString());
        sFilter.setImageType(spType.getSelectedItem().toString());
        sFilter.setSearchSite(etSite.getText().toString());

        return this.sFilter;
    }
}
