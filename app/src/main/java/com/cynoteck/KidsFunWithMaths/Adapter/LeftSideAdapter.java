package com.cynoteck.KidsFunWithMaths.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.KidsFunWithMaths.Models.LeftSideModel;
import com.cynoteck.KidsFunWithMaths.R;

public class LeftSideAdapter extends RecyclerView.Adapter<LeftSideAdapter.MyViewHolder> {
    private LeftSideModel[] countModels;
    int size;
    String itemsName;

    public LeftSideAdapter(LeftSideModel[] leftSideModels, int size, String itemsName) {
        this.countModels = leftSideModels;
        this.size = size;
        this.itemsName = itemsName;
    }

    @NonNull
    @Override
    public LeftSideAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.left_side_list, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHolder(listItem);
        return (LeftSideAdapter.MyViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeftSideAdapter.MyViewHolder holder, int position) {

        if (itemsName.equals("basketball")) {
            holder.imageView.setImageResource(countModels[0].getImgId());
        }else  if (itemsName.equals("banana")) {
            holder.imageView.setImageResource(countModels[1].getImgId());
        }else if (itemsName.equals("brookle")) {
            holder.imageView.setImageResource(countModels[2].getImgId());
        }else  if (itemsName.equals("icecream")) {
            holder.imageView.setImageResource(countModels[3].getImgId());
        }else if (itemsName.equals("brookle")) {
            holder.imageView.setImageResource(countModels[4].getImgId());
        }else  if (itemsName.equals("football")) {
            holder.imageView.setImageResource(countModels[5].getImgId());
        }else if (itemsName.equals("orange")) {
            holder.imageView.setImageResource(countModels[6].getImgId());
        }else  if (itemsName.equals("pineapple")) {
            holder.imageView.setImageResource(countModels[7].getImgId());
        }else if (itemsName.equals("tamatoo")) {
            holder.imageView.setImageResource(countModels[8].getImgId());
        }else  if (itemsName.equals("cherry")) {
            holder.imageView.setImageResource(countModels[9].getImgId());
        }else if (itemsName.equals("car")) {
            holder.imageView.setImageResource(countModels[10].getImgId());
        }else  if (itemsName.equals("corn")) {
            holder.imageView.setImageResource(countModels[11].getImgId());
        }else if (itemsName.equals("bringl")) {
            holder.imageView.setImageResource(countModels[12].getImgId());
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.compreLeftSideIV);

        }
    }
}
