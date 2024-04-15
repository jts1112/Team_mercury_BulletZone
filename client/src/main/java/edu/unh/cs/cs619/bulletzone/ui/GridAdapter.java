package edu.unh.cs.cs619.bulletzone.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;

@EBean
public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private GridCell[][] gridData;
    private Context context;

    public GridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
        final View row;
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.field_item, parent, false);
            row = inflater.inflate(R.layout.field_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.gridImageView);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        int colX = position % gridData[0].length;
        int rowY = position / gridData[0].length;
        GridCell cell = gridData[rowY][colX];

        // Set terrain image
        holder.imageView.setImageResource(cell.getTerrainResourceID());

        // Set entity image on top of terrain
        if (cell.getEntityResourceID() != 0) {
            holder.imageView.setImageResource(cell.getEntityResourceID());
        }

        holder.imageView.setRotation(cell.getEntityRotation());

        row.setId(position);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ClientActivity) {
                    Toast.makeText(context, "Clicked" + row.getId() + "!!",
                            Toast.LENGTH_SHORT).show();
                    ((ClientActivity) context).onGridItemTapped(colX, rowY);
                }
            }
        });

        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
