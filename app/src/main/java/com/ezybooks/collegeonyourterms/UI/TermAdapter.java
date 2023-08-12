package com.ezybooks.collegeonyourterms.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.entities.Term;

import java.util.List;

/**This class sets up the adapter for the terms list. Extends RecyclerView.*/
public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    /**This inner class sets the view holder. Extends RecyclerView.*/
    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        /**This method sets the item view to the text view item.
         * @param itemView */
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView=itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(new View.OnClickListener() {
                /**This method sets the on click listener for items in the terms list and sets up the information to
                 * be sent to the term details screen.
                 * @param v */
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term currentTerm = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termId", currentTerm.getTermID());
                    intent.putExtra("title", currentTerm.getTermTitle());
                    intent.putExtra("startDate", currentTerm.getStartDate());
                    intent.putExtra("endDate", currentTerm.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private final Context context;
    private final LayoutInflater mInflater;

    /**This method inflates the adapter.
     * @param context */
    public TermAdapter(Context context){;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**This overridden method creates the view holder and returns the term view holder from the adapter.
     * @param parent
     * @param viewType */
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    /**This overridden method populates the list with the term titles.
     * @param holder
     * @param position */
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null){
            Term currentTerm = mTerms.get(position);
            String name = currentTerm.getTermTitle();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No term name");
        }
    }

    /**This overridden method returns the size of mTerms.
     * @return mTerms.size()*/
    @Override
    public int getItemCount() {
        if(mTerms != null) {
            return mTerms.size();
        }
        else return 0;
    }

    /**This method sets the terms to a list.
     * @param terms */
    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }


}
