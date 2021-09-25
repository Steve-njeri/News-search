package com.stephen.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.stephen.newssearch.R;

import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.ui.NewsDetailActivity;
import com.stephen.newssearch.util.ItemTouchHelperAdapter;
import com.stephen.newssearch.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;


public class FirebaseNewsListAdapter extends FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Article> mArticles = new ArrayList<>();


    public FirebaseNewsListAdapter(FirebaseRecyclerOptions<Article> options,
                                         Query ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                mArticles.add(dataSnapshot.getValue(Article.class));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseNewsViewHolder, int position, @NonNull Article article) {
        firebaseNewsViewHolder.bindNews(article);
        firebaseNewsViewHolder.mNewsImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(firebaseNewsViewHolder);
                }
                return false;
            }
        });

        firebaseNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("position", firebaseNewsViewHolder.getAdapterPosition());
                intent.putExtra("articles", Parcels.wrap(mArticles));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag, parent, false);
        return new FirebaseNewsViewHolder(view);
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mArticles, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }


    @Override
    public void onItemDismiss(int position) {
        mArticles.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Article article : mArticles) {
            int index = mArticles.indexOf(article);
            DatabaseReference mReference = getRef(index);
            article.setIndex(Integer.toString(index));
            mReference.setValue(article);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }
}
