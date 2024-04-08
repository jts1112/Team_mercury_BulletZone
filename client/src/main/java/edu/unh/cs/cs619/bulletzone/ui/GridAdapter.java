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
    private int[][] gridData;
    private GridCellImageMapper mapper;

    public GridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mapper = new GridCellImageMapper(context);
    }

    public void setGridData(int[][] gridData) {
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
        Log.d("Adapter", "getView called");
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.field_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.gridImageView); // Assuming you have an ImageView with id 'gridImageView'
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int row = position / 16;
        int col = position % 16;

        int cellValue = gridData[row][col];

        // TODO: somehow have getview not call image mapper for speed
        // Get the image resource for the cell value
        int imageResource = mapper.getImageResourceForCell(cellValue);

        // Set the image resource to the ImageView
        holder.imageView.setImageResource(imageResource);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
