package fr.altoine.instatrain;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;

import fr.altoine.instatrain.models.Traffic;

/**
 * TrafficIconView - InstaTrain
 * Created by soulierantoine on 27/09/2017
 */
public final class TrafficView extends ConstraintLayout {
    final ImageView mIconTraffic;
    final ImageView mImageWorks;
    final ImageView mTrafficFrame;

    public TrafficView(Context context, ImageView iconTraffic, ImageView imageWorks, ImageView trafficFrame) {
        super(context);
        mIconTraffic = iconTraffic;
        mImageWorks = imageWorks;
        mTrafficFrame = trafficFrame;
    }

    public TrafficView(Context context, AttributeSet attrs, ImageView iconTraffic, ImageView imageWorks, ImageView trafficFrame) {
        super(context, attrs);
        mIconTraffic = iconTraffic;
        mImageWorks = imageWorks;
        mTrafficFrame = trafficFrame;
    }

    public TrafficView(Context context, AttributeSet attrs, int defStyleAttr, ImageView iconTraffic, ImageView imageWorks, ImageView trafficFrame) {
        super(context, attrs, defStyleAttr);
        mIconTraffic = iconTraffic;
        mImageWorks = imageWorks;
        mTrafficFrame = trafficFrame;
    }

    public void displayAlertFrame() {
         mTrafficFrame.setImageResource(R.drawable.alert_shape);
    }

    public void displayCritiqueFrame() {
         mTrafficFrame.setImageResource(R.drawable.critique_shape);
    }

    public void displayWorkIcon() {
        mImageWorks.setVisibility(VISIBLE);
    }

    public void setIconLine(Traffic traffic) {
        if (traffic instanceof Traffic.MetroTraffic) {
            switch (traffic.getLine()) {
                case "1":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne1);
                    break;
                case "2":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne2);
                    break;
                case "3":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne3);
                    break;
                case "3B":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne3b);
                    break;
                case "4":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne4);
                    break;
                case "5":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne5);
                    break;
                case "6":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne6);
                    break;
                case "7":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne7);
                    break;
                case "7B":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne7b);
                    break;
                case "8":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne8);
                    break;
                case "9":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne9);
                    break;
                case "10":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne10);
                    break;
                case "11":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne11);
                    break;
                case "12":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne12);
                    break;
                case "13":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne13);
                    break;
                case "14":
                    mIconTraffic.setImageResource(R.drawable.ic_metro_ligne14);
                    break;
                default:
                    break;
            }
        } else if (traffic instanceof Traffic.RerTraffic) {
            switch (traffic.getLine()) {
                case "A":
                    mIconTraffic.setImageResource(R.drawable.ic_rer_ligne_a);
                    break;
                case "B":
                    mIconTraffic.setImageResource(R.drawable.ic_rer_ligne_b);
                    break;
                case "C":
                    mIconTraffic.setImageResource(R.drawable.ic_rer_ligne_c);
                    break;
                case "D":
                    mIconTraffic.setImageResource(R.drawable.ic_rer_ligne_d);
                    break;
                case "E":
                    mIconTraffic.setImageResource(R.drawable.ic_rer_ligne_e);
                    break;
                default:
                    break;
            }
        } else if (traffic instanceof Traffic.TramwayTraffic) {
            switch (traffic.getLine()) {
                case "1":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne1);
                    break;
                case "2":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne2);
                    break;
                case "3A":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne3a);
                    break;
                case "3B":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne3b);
                    break;
                case "5":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne5);
                    break;
                case "6":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne6);
                    break;
                case "7":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne7);
                    break;
                case "8":
                    mIconTraffic.setImageResource(R.drawable.ic_tram_ligne8);
                    break;
                default:
                    break;
            }
        }
    }
}
