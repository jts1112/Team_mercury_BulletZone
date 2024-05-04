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
import edu.unh.cs.cs619.bulletzone.replay.GameReplayManager;

@EBean
public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private GridCell[][] gridData;
    private GridCell[][][] gridData3d;
    private Context context;

    private int selectedPosition = -1;
    private int flagplaced = -1; // 0 if flag button hasnt beent pressed or if timer completed. if pressed its 1.

    public GridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setGridData(GridCell[][] gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    public void setGridData3d(GridCell[][][] gridData) {
        this.gridData3d = gridData;
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


        int rowY = position / gridData[0].length;
        int colX = position % gridData[0].length;
        GridCell cell = gridData[rowY][colX];

        // Set terrain image
        holder.imageView.setImageResource(cell.getTerrainResourceID());

        // Set entity image on top of terrain
        if (cell.getEntityResourceID() != 0) {
            holder.imageView.setImageResource(cell.getEntityResourceID());
        }

        holder.imageView.setRotation(cell.getEntityRotation());

        // add flag into the gridveiw . but also need to check if should add flag.
        if (flagplaced == position) {
            holder.imageView.setImageResource(R.drawable.flag1);
        }

        row.setId(position);

        // TODO my implementation
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ClientActivity) {
                    Toast.makeText(context, "Clicked" + row.getId() + "!!",
                            Toast.LENGTH_SHORT).show();
                    ((ClientActivity) context).onGridItemTapped(colX, rowY);
                    selectedPosition = row.getId();
                }
                Log.d("Cell Clicked", "onClick: ");
            }
        });

        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    public int getSelectedPosition(){
        return this.selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition){
        this.selectedPosition = selectedPosition;
    }

    /**
     * Set the flag placed value. 1 if it has been placed and zero if unplaced.
     * @param placed
     */
    public void  setFlagplaced(int placed) {
        this.flagplaced = placed;
    }



}
