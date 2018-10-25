package com.inwandou.wandou.functions.video.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;

import java.io.InputStream;

/**
 * 描述:
 * <p>
 * Created by and on 2018/10/24.
 */
public class VideoGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height, @NonNull Options options) {
        return null;
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }
}
