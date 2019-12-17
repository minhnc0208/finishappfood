package com.duanmot.myapplication.Adapter;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duanmot.myapplication.R;
import com.duanmot.myapplication.model.Comment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Comment extends RecyclerView.Adapter<Adapter_Comment.CommentViewHolder> {

    Context context;
    List<Comment> dsComment;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceComments;
    DatabaseReference databaseReferenceMonAn;



    public Adapter_Comment(Context context, List<Comment> dsComment) {
        this.context = context;
        this.dsComment = dsComment;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_user;
        TextView tv_name, tv_content, tv_date;
        LinearLayout llItemComment;

        public CommentViewHolder(final View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.comment_user_img);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_date = itemView.findViewById(R.id.comment_date);
            llItemComment = itemView.findViewById(R.id.llItemComment);

        }
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        CommentViewHolder commentViewHolder = new CommentViewHolder(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabase.getReference().child("Users");
        databaseReferenceComments = firebaseDatabase.getReference().child("Comments");
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");

        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {

        final Comment comment = dsComment.get(position);
        holder.tv_name.setText(comment.getUname());
        holder.tv_content.setText(comment.getUcomment());
        Glide.with(context).load(comment.getUimg()).into(holder.img_user);
        holder.tv_date.setText(comment.getTime());


    }

    @Override
    public int getItemCount() {
        return dsComment.size();
    }

}


//        holder.tv_date.setText((Integer) comment.getTimestamp());
//        holder.tv_name.setText(dsComment.get(position).getUname());
//        holder.tv_date.setText(timestampToString((Long)dsComment.get(position).getTimestamp()));