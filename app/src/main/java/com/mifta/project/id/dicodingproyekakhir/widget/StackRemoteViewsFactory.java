package com.mifta.project.id.dicodingproyekakhir.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.database.MoviesHelper;
import com.mifta.project.id.dicodingproyekakhir.database.TvShowHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;
    private TvShowHelper tvShowHelper;
    private MoviesHelper moviesHelper;
    private ArrayList<MoviesItems> list;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        moviesHelper = MoviesHelper.getInstance(context);
        tvShowHelper = TvShowHelper.getInstance(context);
    }

    @Override
    public void onDataSetChanged() {
        moviesHelper.open();
        tvShowHelper.open();
        list = moviesHelper.getAllMovies();
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (Exception e) {
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }

        list = tvShowHelper.getAllTv();
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (Exception e) {
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        moviesHelper.close();
        tvShowHelper.close();
    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, widgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
