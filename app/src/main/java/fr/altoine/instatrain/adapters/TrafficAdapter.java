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
import fr.altoine.instatrain.net.ResponseTraffic;
import fr.altoine.instatrain.utils.Constants;

/**
 * TrafficAdapter - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public class TrafficAdapter extends SectionedRecyclerViewAdapter<TrafficAdapter.TrafficViewHolder> {
    private Context mContext;
    private ResponseTraffic mResponseTraffic;
    private final TrafficAdapterOnClickHandler mClickHandler;

    public TrafficAdapter(Context context, ResponseTraffic responseTraffic, TrafficAdapterOnClickHandler clickHandler) {
        mContext = context;
        mResponseTraffic = responseTraffic;
        mClickHandler = clickHandler;
    }

    public interface TrafficAdapterOnClickHandler {
        void onClick(ResponseTraffic.Result.Transports transport);
    }

    // TODO: avoid using notifyDataSetChanged() : compare the current response and the previous and simply apply necessary changes
    public void reloadResponse(ResponseTraffic responseTraffic) {
        mResponseTraffic = responseTraffic;
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
        if (section == Constants.Section.METROS) {
            int metrosListSize = mResponseTraffic.getResult().getMetros().size();
            return (metrosListSize != 0) ? metrosListSize : 0;
        }
        if (section == Constants.Section.RERS) {
            int rersListSize = mResponseTraffic.getResult().getRers().size();
            return (rersListSize!= 0) ? rersListSize : 0;
        }
        if (section == Constants.Section.TRAMWAYS) {
            int tramwaysListSize = mResponseTraffic.getResult().getTramways().size();
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
        ResponseTraffic.Result.Transports transports = mResponseTraffic.getResult().getTransports().get(position);


        switch (transports.getSlug()) {
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

        if (mResponseTraffic.getResult().getMetros().size() > 0 && section == Constants.Section.METROS)
            trafficView.setIconLine(mResponseTraffic.getResult().getMetros().get(relativePosition));

        if (mResponseTraffic.getResult().getRers().size() > 0 && section == Constants.Section.RERS)
            trafficView.setIconLine(mResponseTraffic.getResult().getRers().get(relativePosition));

        if (mResponseTraffic.getResult().getTramways().size() > 0 && section == Constants.Section.TRAMWAYS)
            trafficView.setIconLine(mResponseTraffic.getResult().getTramways().get(relativePosition));
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
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                switch (section) {
                    case Constants.Section.METROS:
                        mClickHandler.onClick(mResponseTraffic.getResult().getMetros().get(relativePos));
                        break;
                    case Constants.Section.RERS:
                        mClickHandler.onClick(mResponseTraffic.getResult().getRers().get(relativePos));
                        break;
                    case Constants.Section.TRAMWAYS:
                        mClickHandler.onClick(mResponseTraffic.getResult().getTramways().get(relativePos));
                        break;
                    default:
                        break;
                }
            }
        }

        TrafficViewHolder(View view) {
            super(view);
            mIconTraffic = (ImageView) view.findViewById(R.id.iv_icon);
            mSectionTitle = (TextView) view.findViewById(R.id.tv_title);
            mTrafficFrame = (ImageView) view.findViewById(R.id.iv_frame);
            mImageWorks = (ImageView) view.findViewById(R.id.iv_works);

            view.setOnClickListener(this);
        }
    }
}
