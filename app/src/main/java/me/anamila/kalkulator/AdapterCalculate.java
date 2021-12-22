package me.anamila.kalkulator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCalculate extends RecyclerView.Adapter<AdapterCalculate.CalcViewHolder> {
    Context context;
    ArrayList<String>calculateList;

    public AdapterCalculate(ArrayList<String>calculateList){
        this.calculateList = calculateList;
    }

    @NonNull
    @Override
    public AdapterCalculate.CalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        CalcViewHolder holder = new CalcViewHolder(inflater.inflate(R.layout.item_record,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCalculate.CalcViewHolder holder, @SuppressLint("Recyclerview") int position) {
        holder.historyRecord.setText(calculateList.get(position));
        holder.linLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),calculateList.get(position)+" Removed", Toast.LENGTH_SHORT).show();
                calculateList.remove(position);
                notifyItemRemoved(position);
                MainActivity.historyCalc = calculateList;
                MainActivity.strListHistoryCalc = MainActivity.gson.toJson(MainActivity.historyCalc);
                MainActivity.pref.edit().putString(context.getString(R.string.calcHistory_key),MainActivity.strListHistoryCalc).apply();
                MainActivity.record.setAdapter(new AdapterCalculate(calculateList));
                MainActivity.record.setLayoutManager(new LinearLayoutManager(context));

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return calculateList.size();
    }

    public class CalcViewHolder extends RecyclerView.ViewHolder{
        public TextView historyRecord;
        public LinearLayout linLayout;

        public CalcViewHolder(@NonNull View itemView) {
            super(itemView);
            historyRecord = itemView.findViewById(R.id.tvHistoryrecord);
            linLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
