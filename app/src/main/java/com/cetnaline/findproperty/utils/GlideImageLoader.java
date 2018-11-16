package com.cetnaline.findproperty.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cetnaline.findproperty.R;

public class GlideImageLoader {

    private static RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.image_loading)
            .error(R.mipmap.image_load_err)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

    public static void loadWithUrl(Context context, String url, ImageView target) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    public static void loadWithUrl(Context context, String url,RequestOptions options, ImageView target) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    public static void loadWithUrl(Context context, String url,int width, int height, ImageView target) {
        options.override(width, height);     //Target.SIZE_ORIGINAL 加载为原始尺寸
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }
}
