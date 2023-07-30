package com.example.deltaproject2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class base extends ArrayAdapter<Data.Scores> {

    public base(Context context, List<Data.Scores> scoresList) {
        super(context, 0, scoresList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.listing, parent, false);
        }
        Data.Scores score = getItem(position);
        TextView txtName = itemView.findViewById(R.id.name1);
        TextView txtScore = itemView.findViewById(R.id.name2);
        if (score != null) {
            txtName.setText(score.getname2());
            txtScore.setText(String.valueOf(score.getScore()));
        }

        return itemView;
    }
}
