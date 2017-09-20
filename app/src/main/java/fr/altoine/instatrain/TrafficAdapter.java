package fr.altoine.instatrain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;

import fr.altoine.instatrain.net.ResponseTraffic;
import fr.altoine.instatrain.utils.Constants;

/**
 * TrafficAdapter - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public class TrafficAdapter extends SectionedRecyclerViewAdapter<TrafficAdapter.TrafficViewHolder> {
    private Context mContext;
    private ResponseTraffic mResponseTraffic;

    public TrafficAdapter(Context context, ResponseTraffic responseTraffic) {
        mContext = context;
        mResponseTraffic = responseTraffic;
    }

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
            default:
                layoutRes = R.layout.traffic_list_item;
                break;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(layoutRes, parent, false);
        return new TrafficViewHolder(item);
    }

    @Override
    public int getSectionCount() {
        return Constants.TYPE_OF_TRANSPORTS;
    }

    @Override
    public int getItemCount(int section) {
        if (section == 0)
            return mResponseTraffic.getResult().getMetros().size();
        if (section == 1)
            return mResponseTraffic.getResult().getRers().size();
        if (section == 2)
            return mResponseTraffic.getResult().getTramways().size();

        return 0;
    }

    @Override
    public void onBindHeaderViewHolder(TrafficViewHolder holder, int section, boolean expanded) {
        // TODO: change for strings from resource
        if (section == 0)
            holder.mSectionTitle.setText("Metros");
        if (section == 1)
            holder.mSectionTitle.setText("Rers");
        if (section == 2)
            holder.mSectionTitle.setText("Tramways");
    }

    @Override
    public void onBindFooterViewHolder(TrafficViewHolder holder, int section) {}

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int section, int relativePosition, int absolutePosition) {

        if (mResponseTraffic.getResult().getMetros().size() > 0 && section == 0) {
            displayTrafficMetros(holder, relativePosition);
        }

        if (mResponseTraffic.getResult().getRers().size() > 0 && section == 1) {
            displayTrafficRers(holder, relativePosition);
        }
        if (mResponseTraffic.getResult().getTramways().size() > 0 && section == 2) {
            displayTrafficTramways(holder, relativePosition);
        }
    }

    static class TrafficViewHolder extends SectionedViewHolder {
        ImageView mIconTraffic;
        final TextView mStateTraffic;
        final TextView mSectionTitle;

        TrafficViewHolder(View itemView) {
            super(itemView);
            mIconTraffic = (ImageView) itemView.findViewById(R.id.iv_icon);
            mStateTraffic = (TextView) itemView.findViewById(R.id.tv_state);
            mSectionTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }


    private void displayTrafficMetros(TrafficViewHolder holder, int position) {
        ResponseTraffic.Result.Metro currentMetro = mResponseTraffic.getResult().getMetros().get(position);
        holder.mStateTraffic.setText(currentMetro.getTitle());

        switch (currentMetro.getLine()) {
            case "1":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne1, null)
                );
                break;
            case "2":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne2, null)
                );
                break;
            case "3":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne3, null)
                );
                break;
            case "3B":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne3b, null)
                );
                break;
            case "4":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne4, null)
                );
                break;
            case "5":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne5, null)
                );
                break;
            case "6":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne6, null)
                );
                break;
            case "7":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne7, null)
                );
                break;
            case "7B":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne7b, null)
                );
                break;
            case "8":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne8, null)
                );
                break;
            case "9":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne9, null)
                );
                break;
            case "10":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne10, null)
                );
                break;
            case "11":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne11, null)
                );
                break;
            case "12":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne12, null)
                );
                break;
            case "13":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne13, null)
                );
                break;
            case "14":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_metro_ligne14, null)
                );
                break;
            default:
                break;
        }
    }

    private void displayTrafficRers(TrafficViewHolder holder, int position) {
        ResponseTraffic.Result.Rer currentRer = mResponseTraffic.getResult().getRers().get(position);
        holder.mStateTraffic.setText(currentRer.getTitle());


        switch (currentRer.getLine()) {
            case "A":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_a, null)
                );
                break;
            case "B":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_b, null)
                );
                break;
            case "C":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_c, null)
                );
                break;
            case "D":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_d, null)
                );
                break;
            case "E":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_e, null)
                );
                break;
            default:
                break;
        }
    }

    private void displayTrafficTramways(TrafficViewHolder holder, int position) {
        ResponseTraffic.Result.Tramway currentTram = mResponseTraffic.getResult().getTramways().get(position);
        holder.mStateTraffic.setText(currentTram.getTitle());

        switch (currentTram.getLine()) {
            case "1":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne1, null)
                );
                break;
            case "2":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne2, null)
                );
                break;
            case "3A":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne3a, null)
                );
                break;
            case "3B":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne3b, null)
                );
                break;
            case "5":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne5, null)
                );
                break;
            case "6":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne6, null)
                );
                break;
            case "7":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne7, null)
                );
                break;
            case "8":
                holder.mIconTraffic.setImageDrawable(
                        ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_tram_ligne8, null)
                );
                break;
            default:
                break;
        }
    }
}
