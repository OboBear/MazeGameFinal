package game.ourmaze.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import game.ourmaze.R;
import game.ourmaze.Tool;
import game.ourmaze.equipment.ToolBean;

/**
 * @author obo
 * @date 2019/1/20
 */
public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolIconViewHolder> {

    List<ToolBean> toolBeans = new ArrayList<>();

    public ToolAdapter(List<ToolBean> toolBeans) {
        this.toolBeans = toolBeans;
    }

    @Override
    public ToolIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToolIconViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tool_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(ToolIconViewHolder holder, int position) {
        holder.bindData(toolBeans.get(position), position);
    }

    @Override
    public int getItemCount() {
        return toolBeans.size();
    }

    static class ToolIconViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_tool_icon;
        TextView tv_tool_count;

        public ToolIconViewHolder(View itemView) {
            super(itemView);
            iv_tool_icon = itemView.findViewById(R.id.iv_tool_icon);
            tv_tool_count = itemView.findViewById(R.id.tv_tool_count);
        }

        public void bindData(ToolBean toolBean, int position) {
            tv_tool_count.setText(toolBean.name + "X" + toolBean.count);
            if (toolBean.count > 0) {
                iv_tool_icon.setImageResource(toolBean.iconResource);
                tv_tool_count.setTextColor(Color.YELLOW);
            } else {
                iv_tool_icon.setImageResource(toolBean.iconResourceEmp);
                tv_tool_count.setTextColor(Color.parseColor("#999999"));
            }
            itemView.setOnClickListener(v->{
                if (toolBean.count > 0) {
                    Tool.use_tool(position, toolBean);
                }
            });
        }
    }
}
