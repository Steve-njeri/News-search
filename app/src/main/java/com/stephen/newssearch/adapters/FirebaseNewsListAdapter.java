package com.stephen.newssearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.stephen.newssearch.R;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.util.ItemTouchHelperAdapter;
import com.stephen.newssearch.util.OnStartDragListener;

public class FirebaseNewsListAdapter extends FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;


    public FirebaseNewsListAdapter(FirebaseRecyclerOptions<Article> options,
                                         DatabaseReference ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    @NonNull
    @Override
    public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag, parent, false);
        return new FirebaseNewsViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
