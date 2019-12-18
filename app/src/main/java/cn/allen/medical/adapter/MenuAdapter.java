package cn.allen.medical.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allen.frame.tools.OnAllenItemClickListener;
import allen.frame.widget.BadgeView;
import cn.allen.medical.R;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;

public class MenuAdapter extends RecyclerView.Adapter {

    private List<MeMenu> list;
    private boolean isGrid = false;
    public MenuAdapter(boolean isGrid) {
        this.isGrid = isGrid;
    }
    public void setData(List<MeMenu> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if(isGrid){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.grid_memu_item_layout, viewGroup, false);
        }else{
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_memu_item_layout, viewGroup, false);
        }
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ObjectHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ObjectHolder holder = (ObjectHolder) viewHolder;
        holder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ObjectHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView name;
        private AppCompatImageView icon;
        private View lay,panel;
        private BadgeView badge;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            icon = itemView.findViewById(R.id.item_icon);
            lay = itemView.findViewById(R.id.item_layout);
            panel = itemView.findViewById(R.id.item_icon_panel);
            badge = new BadgeView(itemView.getContext());
            badge.setTargetView(panel);
            badge.setBadgeCount(0);
            badge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
            badge.setHideOnNull(true);
        }
        public void bind(MeMenu menu){
            if(menu!=null){
                icon.setImageResource(MenuEnum.getResId(menu.getCode()));
                name.setText(menu.getText());
                badge.setBadgeCount(menu.getCount());
                lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener!=null){
                            listener.onItemClick(menu);
                        }
                    }
                });
            }
        }
    }
    private OnAllenItemClickListener listener;
    public void setItemClickListener(OnAllenItemClickListener listener){
        this.listener = listener;
    }
}
