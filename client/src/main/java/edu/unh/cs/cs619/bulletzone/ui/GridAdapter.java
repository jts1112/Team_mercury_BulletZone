package edu.unh.cs.cs619.bulletzone.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.R;

@EBean
public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private GridCell[][] gridData;

    private int[][] Terrains;
    private GridCellImageMapper mapper;

    public GridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mapper = new GridCellImageMapper();
    }

    public void setGridData(GridCell[][] gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gridData != null ? gridData.length * gridData[0].length : 0;
    }

    @Override
    public Object getItem(int position) {
        int row = position / gridData[0].length;
        int col = position % gridData[0].length;
        return gridData[row][col];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.field_item, parent, false);
            holder = new ViewHolder();
            // Assuming you have an ImageView with id 'gridImageView'
            holder.imageView = convertView.findViewById(R.id.gridImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int row = position / gridData[0].length;
        int col = position % gridData[0].length;

        GridCell cell = gridData[row][col];

        // Set the image resource to the ImageView
        holder.imageView.setImageResource(cell.getResourceID());
        holder.imageView.setRotation(cell.getRotation());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
