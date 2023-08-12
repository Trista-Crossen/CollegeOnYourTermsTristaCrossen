package com.ezybooks.collegeonyourterms.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezybooks.collegeonyourterms.R;
import com.ezybooks.collegeonyourterms.entities.CourseNote;

import java.util.List;

/**This class sets up the adapter for the note list to be shown on the course details screen. Extends RecylerView*/
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    /**This method inflates the list.
     * @param context */
    public NoteAdapter( Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**This inner class gives the TextView a name. Extends RecyclerView.*/
    class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView noteItemView;
        /**This method sets the item view to the proper text view.
         * @param itemView */
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView12);
        }
    }
    private List<CourseNote> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    /**This overridden method inflates the list items and returns them to the view holder.
     * @param parent
     * @param viewType */
    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    /**This overridden method binds the items to the view holder and sets the texts on the item views.
     * @param holder
     * @param position */
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(mNotes != null){
            CourseNote current = mNotes.get(position);
            String note = current.getNoteString();
            holder.noteItemView.setText(note);
        }
        else {
            holder.noteItemView.setText("No notes at this time.");
        }
    }

    /**This method sets the list of notes.
     * @param notes */
    public void setNotes(List<CourseNote> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    /**This method returns the size of mNotes.
     * @return mNotes.size()*/
    @Override
    public int getItemCount() {
        if(mNotes != null) return mNotes.size();
         else return 0;
    }
}
