package com.example.journal_de_bord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class DefiAdapter extends ArrayAdapter<ItemDefi> {

    public DefiAdapter(Context context, List<ItemDefi> itemDefis) {
        super(context, 0, itemDefis);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_defi,parent, false);
        }

        ItemDefiViewHolder viewHolder = (ItemDefiViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ItemDefiViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.titre = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }

        ItemDefi defi = getItem(position);
        viewHolder.date.setText(defi.getDate().toString());
        viewHolder.titre.setText(defi.getTitre());

        return convertView;
    }

    private class ItemDefiViewHolder{
        public TextView date;
        public TextView titre;

    }
}
