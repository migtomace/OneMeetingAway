package com.example.lmnop.onemeetingawaymap1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.Map;

class OwnIconRendered extends DefaultClusterRenderer<MyItem> {



    public OwnIconRendered(Context context, GoogleMap map,
                           ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {

//        InputStream is = context.getResources().openRawResource(R.drawable.my_image);
//        mBitmap = BitmapFactory.decodeStream(is);
//
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.aalogo);
        int dstWidth =120;
        int dstHeight = 120;

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true)));
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }


}