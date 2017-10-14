package fr.altoine.instatrain.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.ItemCoord;
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;

import fr.altoine.instatrain.R;
import fr.altoine.instatrain.TrafficView;
import fr.altoine.instatrain.models.Traffic;
import fr.altoine.instatrain.net.ResponseApi;
import fr.altoine.instatrain.utils.Constants;

/**
 * TrafficAdapter - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public class TrafficAdapter extends SectionedRecyclerViewAdapter<TrafficAdapter.TrafficViewHolder> {
    private Context mContext;
    private ResponseApi mResponseApi;
    private final TrafficAdapterOnClickHandler mClickHandler;

    public TrafficAdapter(Context context, ResponseApi responseTraffic, TrafficAdapterOnClickHandler clickHandler) {
        mContext = context;
        mResponseApi = responseTraffic;
        mClickHandler = clickHandler;
    }

    public interface TrafficAdapterOnClickHandler {
        void onClick(Traffic traffic);
    }

    // TODO: avoid using notifyDataSetChanged() : compare the current response and the previous and simply apply necessary changes
    public void reloadResponse(ResponseApi responseTraffic) {
        mResponseApi = responseTraffic;
        notifyDataSetChanged();
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layoutRes = R.layout.traffic_list_header;
                break;
            case VIEW_TYPE_ITEM:
                layoutRes = R.layout.traffic_list_item;
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new TrafficViewHolder(view);
    }

    @Override
    public int getSectionCount() {
        return Constants.TYPE_OF_TRANSPORTS;
    }

    @Override
    public int getItemCount(int section) {
        Traffic traffics = mResponseApi.getResult().getTraffics();

        if (section == Constants.Section.METROS) {
            int metrosListSize = traffics.getMetroTraffics().size();
            return (metrosListSize != 0) ? metrosListSize : 0;
        }
        if (section == Constants.Section.RERS) {
            int rersListSize = traffics.getRerTraffics().size();
            return (rersListSize!= 0) ? rersListSize : 0;
        }
        if (section == Constants.Section.TRAMWAYS) {
            int tramwaysListSize = traffics.getTramwayTraffics().size();
            return (tramwaysListSize != 0) ? tramwaysListSize : 0;
        }

        return 0;
    }

    @Override
    public void onBindHeaderViewHolder(TrafficViewHolder holder, int section, boolean expanded) {
        // TODO: change for strings from resource
        if (section == Constants.Section.METROS)
            holder.mSectionTitle.setText("Metros");
        if (section == Constants.Section.RERS)
            holder.mSectionTitle.setText("Rers");
        if (section == Constants.Section.TRAMWAYS)
            holder.mSectionTitle.setText("Tramways");
    }

    @Override
    public void onBindFooterViewHolder(TrafficViewHolder holder, int section) {}

    /*
     * Only enters here for non-header views.
     */
    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int section, int relativePosition, int absolutePosition) {
        // TODO: temporary solution, apparently it'd be more efficient to get the XML that's been inflated rather than use an utility class
         TrafficView trafficView = new TrafficView(mContext, holder.mIconTraffic, holder.mImageWorks, holder.mTrafficFrame);

        /*
         * There's two lists : one with all the transports juxtaposed one besides the others, and another with the sections name.
         * We take the absolution position, subtract the section number, and then subtract 1, which is the section name.
        */
        int position = absolutePosition - section - 1;
        Traffic resultTraffic = mResponseApi.getResult().getTraffics();
        Traffic currentTraffics = resultTraffic.getAllTraffic().get(position);

        switch (currentTraffics.getSlug()) {
            case Constants.Slug.NORMAL_TRAV:
                trafficView.displayWorkIcon();
                break;
            case Constants.Slug.ALERTE:
                trafficView.displayAlertFrame();
                break;
            case Constants.Slug.CRITIQUE:
                trafficView.displayCritiqueFrame();
                break;
            default:
                break;
        }

        if (resultTraffic.getMetroTraffics().size() > 0 && section == Constants.Section.METROS)
            trafficView.setIconLine(resultTraffic.getMetroTraffics().get(relativePosition));

        if (resultTraffic.getRerTraffics().size() > 0 && section == Constants.Section.RERS)
            trafficView.setIconLine(resultTraffic.getRerTraffics().get(relativePosition));

        if (resultTraffic.getTramwayTraffics().size() > 0 && section == Constants.Section.TRAMWAYS)
            trafficView.setIconLine(resultTraffic.getTramwayTraffics().get(relativePosition));
    }


    /**
     * No need to be static, this inner class will only be used in this adapter.
     * See: https://stackoverflow.com/a/31302613
     */
    class TrafficViewHolder extends SectionedViewHolder implements View.OnClickListener {
        final TextView mSectionTitle;
        final ImageView mImageWorks;
        final ImageView mIconTraffic;
        final ImageView mTrafficFrame;

        @Override
        public void onClick(View v) {
            if (isHeader() || isFooter())
                return;

            ItemCoord position = getRelativePosition();
            int section = position.section();
            int relativePos = position.relativePos();
            Traffic traffics = mResponseApi.getResult().getTraffics();
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                switch (section) {
                    case Constants.Section.METROS:
                        mClickHandler.onClick(traffics.getMetroTraffics().get(relativePos));
                        break;
                    case Constants.Section.RERS:
                        mClickHandler.onClick(traffics.getRerTraffics().get(relativePos));
                        break;
                    case Constants.Section.TRAMWAYS:
                        mClickHandler.onClick(traffics.getTramwayTraffics().get(relativePos));
                        break;
                    default:
                        break;
                }
            }
        }

        TrafficViewHolder(View view) {
            super(view);
            mIconTraffic  = view.findViewById(R.id.iv_icon);
            mSectionTitle = view.findViewById(R.id.tv_title);
            mTrafficFrame = view.findViewById(R.id.iv_frame);
            mImageWorks   = view.findViewById(R.id.iv_works);

            view.setOnClickListener(this);
        }
    }
}
