package com.tantofish.androidcourseproject2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tantofish.androidcourseproject2.R;
import com.tantofish.androidcourseproject2.models.ImageResult;

import java.util.List;

/**
 * Created by yutu on 8/6/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult>{

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvSize = (TextView) convertView.findViewById(R.id.tvSize);
        // clear out the image from last time
        ivImage.setImageResource(0);
        // populate title and remote download image url
        tvTitle.setText(Html.fromHtml(imageInfo.title));

        tvSize.setText(imageInfo.width + "x" + imageInfo.height);
        //Transformation trans = new CropSquareTransformation();

        Picasso.with(getContext())
                .load(imageInfo.thumbUrl)
        //        .transform(trans)
                .placeholder(R.drawable.placeholder)
                .into(ivImage);

        return convertView;
    }

    public class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
}
