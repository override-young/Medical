package cn.allen.medical.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.allen.medical.R;
import cn.allen.medical.entry.SysltjEntity;

public class TjAdapter extends RecyclerView.Adapter {

    private List<SysltjEntity> list;
    public TjAdapter() {
    }
    public void setData(List<SysltjEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.count_item_layout, viewGroup, false);
        v.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ObjectHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ObjectHolder holder = (ObjectHolder) viewHolder;
        holder.bind(list.get(i),i);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ObjectHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView name,units,spec,quantity;
        private View v;
        public ObjectHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.count_item_name);
            units = itemView.findViewById(R.id.count_item_units);
            spec = itemView.findViewById(R.id.count_item_spec);
            quantity = itemView.findViewById(R.id.count_item_quantity);
            v = itemView.findViewById(R.id.count_item_line);
        }
        public void bind(SysltjEntity menu,int index){
            if(menu!=null){
                v.setVisibility(index==0?View.GONE:View.VISIBLE);
                name.setText("测试");
                units.setText("测试");
                spec.setText("测试");
                quantity.setText("测试");
            }
        }
    }
}
