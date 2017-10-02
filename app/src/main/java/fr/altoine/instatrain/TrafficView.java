package fr.altoine.instatrain;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * TrafficIconView - InstaTrain
 * Created by soulierantoine on 27/09/2017
 */
public final class TrafficIconView extends ConstraintLayout {
    final ImageView mIconTraffic;
    final ImageView mImageWorks;
    final ImageView mTrafficFrame;

    public TrafficIconView(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.traffic_list_item, this);
        mIconTraffic = (ImageView) findViewById(R.id.iv_icon);
        mImageWorks = (ImageView) findViewById(R.id.iv_works);
        mTrafficFrame = (ImageView) findViewById(R.id.iv_frame);
    }

    public TrafficIconView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.traffic_list_item, this);
        mIconTraffic = (ImageView) findViewById(R.id.iv_icon);
        mImageWorks = (ImageView) findViewById(R.id.iv_works);
        mTrafficFrame = (ImageView) findViewById(R.id.iv_frame);
    }

    public TrafficIconView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.traffic_list_item, this);
        mIconTraffic = (ImageView) findViewById(R.id.iv_icon);
        mImageWorks = (ImageView) findViewById(R.id.iv_works);
        mTrafficFrame = (ImageView) findViewById(R.id.iv_frame);
    }

    public void displayAlertFrame() {
        mTrafficFrame.setImageResource(R.drawable.alert_shape);
    }

    public void displayCritiqueFrame() {
        mTrafficFrame.setImageResource(R.drawable.alert_shape);
    }

    public void displayWorkIcon() {
        mImageWorks.setVisibility(VISIBLE);
    }

    public void hideWorkIcon() {
        mImageWorks.setVisibility(GONE);
    }
}
