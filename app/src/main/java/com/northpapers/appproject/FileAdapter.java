package com.northpapers.appproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Baboria on 4/23/2017.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {
    ArrayList<FileModel> files_list;

    String heading;
    String description;
    String newImageReference;
    String fileReference;

    IFileAdapter iFileAdapter;

    Context context;


    public FileAdapter(ArrayList<FileModel> files_list, Context context){
        this.files_list=files_list;
        iFileAdapter=(IFileAdapter) context;
        this.context=context;


    }


    @Override
    public FileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_adapter_view, parent, false);


        return new MyViewHolder(itemView);





    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final FileModel fileModel = files_list.get(position);

        holder.txt_heading.setText(fileModel.getHeading());
        holder.txt_new_ref.setText(fileModel.getNewImageReference());
        holder.txt_file_ref.setText(fileModel.getFileReference());
        holder.txt_description.setText(fileModel.getDescription());

      holder.btn_view.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            iFileAdapter.onItemClick(fileModel);
          }
      });


    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileModel.getNewImageReference());


       Glide.with(context)
               .using(new FirebaseImageLoader())
               .load(storageReference)
               .into(holder.newImage);










    }


    @Override
    public int getItemCount() {
        return files_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_heading, txt_description, txt_file_ref,txt_new_ref;

        public Button btn_view;
        ImageView newImage;
        LinearLayout linearLayout;



        public MyViewHolder(View itemView) {
            super(itemView);


            txt_heading=(TextView)itemView.findViewById(R.id.txt_file_heading);
            txt_description=(TextView)itemView.findViewById(R.id.txt_file_details);
            txt_file_ref=(TextView)itemView.findViewById(R.id.txt_file_ref);
            txt_new_ref=(TextView)itemView.findViewById(R.id.txt_new_ref);
            btn_view=(Button)itemView.findViewById(R.id.button_file_view);
            newImage=(ImageView)itemView.findViewById(R.id.img_new);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear_layout_first);





        }
    }
}
