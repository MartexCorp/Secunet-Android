package com.app.martex.healthapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Martex on 2/12/2018.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    public List<Object> recordListrecycler;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, hospital, hash;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tv_date);
            hospital = (TextView) view.findViewById(R.id.tv_hospital);
            hash = (TextView) view.findViewById(R.id.tv_hash);
        }
    }


    public RecordAdapter(Context context, List<Object> recordAdapter) {
        this.recordListrecycler = recordAdapter;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Record record = (Record) recordListrecycler.get(position);
        holder.date.setText(record.getDate());
        holder.hospital.setText(record.getHospital());
        holder.hash.setText(record.getHash());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = position;
                Intent i = new Intent(v.getContext(),Results.class);
                i.putExtra("position",pos);
                i.putExtra("date",record.getDate());
                i.putExtra("data",((searchActivity)context).getPass_medical_data());
                v.getContext().startActivity(i);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = position+1;
                Toast.makeText(context, "Item : "+ pos, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        return recordListrecycler.size();
    }


}
