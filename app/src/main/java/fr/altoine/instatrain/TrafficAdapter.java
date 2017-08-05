package fr.altoine.instatrain;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TrafficAdapter - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder> {
    private Context mContext;

    public TrafficAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.traffic_list_item, parent, false);
        return new TrafficViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int position) {
        holder.mIconTraffic.setImageDrawable(
                ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_rer_ligne_a, null)
        );

        holder.mStateTraffic.setText("Metros normal");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class TrafficViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconTraffic;
        private TextView mStateTraffic;

        public TrafficViewHolder(View itemView) {
            super(itemView);
            mIconTraffic = (ImageView) itemView.findViewById(R.id.iv_icon);
            mStateTraffic = (TextView) itemView.findViewById(R.id.tv_state);
        }
    }
}
