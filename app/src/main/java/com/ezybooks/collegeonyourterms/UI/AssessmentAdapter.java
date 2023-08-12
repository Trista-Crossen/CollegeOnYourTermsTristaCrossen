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
import com.ezybooks.collegeonyourterms.entities.Assessment;

import java.util.List;

/**This class is used to populate the assessment lists found on the course details screen. Extends RecyclerView to show the list.*/
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    /**This inner class sets up the view holder for the recycler view to show the associated assessment list for courses.*/
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        /**This method finds the assessment item view so that it can be populated.
         * @param itemView */
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView10);

            /**This method sets the on click listener for list items in the item view.*/
            itemView.setOnClickListener(new View.OnClickListener() {
                /**This method sends the data from the item that is clicked to the assessment detail screen.
                 * @param view */
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment currentAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", currentAssessment.getAssessmentId());
                    intent.putExtra("courseId", currentAssessment.getCourseId());
                    intent.putExtra("title", currentAssessment.getTitle());
                    intent.putExtra("startDate", currentAssessment.getStartDate());
                    intent.putExtra("endDate", currentAssessment.getEndDate());
                    intent.putExtra("type", currentAssessment.getType());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    /**This method inflates the assessment adapter so that it shows in the layout.
     * @param context */
    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**This overridden method creates the assessment view holder and returns the inflated item view.
     * @param parent
     * @param viewType
     * @return AssessmentViewHolder(itemView)*/
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    /**This overridden method checks in the assessments list has data or not, and uses that information to write
     * the appropriate string to the assessment item view.
     * @param holder
     * @param position */
    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position){
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getTitle();
            holder.assessmentItemView.setText(name);
        }
        else{
            holder.assessmentItemView.setText("No assessments at this time");
        }
    }

    /**This method sets the list of assessments.
     * @param assessments */
    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    /**This method returns the number of items in mAssessments.
     * @return mAssessments.size()*/
    public int getItemCount() {
        if(mAssessments != null) return  mAssessments.size();
        else return 0;
    }
}
