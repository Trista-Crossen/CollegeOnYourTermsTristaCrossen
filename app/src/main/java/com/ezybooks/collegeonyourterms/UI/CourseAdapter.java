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
import com.ezybooks.collegeonyourterms.entities.Course;

import java.util.List;

/**This class sets up the adapter for the course list on the term details screen. Extends RecyclerView.*/
public class CourseAdapter  extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    /**This inner class sets the text views for the items on the course list. Extends RecyclerView.*/
    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;
        private final TextView courseItemView2;

        /**This method identifies the text view elements and tells the program which data to send where.
         * @param itemView */
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView9);
            courseItemView2 = itemView.findViewById(R.id.textView10);

            itemView.setOnClickListener(new View.OnClickListener() {
                /**This overridden method sends the course information from the list to the course details screen when an item is clicked.
                 * @param v */
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course currentCourse = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseId", currentCourse.getCourseId());
                    intent.putExtra("termId", currentCourse.getTermId());
                    intent.putExtra("title", currentCourse.getCourseTitle());
                    intent.putExtra("startDate", currentCourse.getStartDate());
                    intent.putExtra("endDate", currentCourse.getEndDate());
                    intent.putExtra("instructorName", currentCourse.getInstructorName());
                    intent.putExtra("instructorPhone", currentCourse.getInstructorPhone());
                    intent.putExtra("instructorEmail", currentCourse.getInstructorEmail());
                    intent.putExtra("status", currentCourse.getStatus());
                    intent.putExtra("courseNote", currentCourse.getNote());

                    context.startActivity(intent);
                }
            });

        }

    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    /**This method inflates the list on the layout.
     * @param context */
    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**This overridden method creates the view holder and uses the inflater to show the list items.
     * @param viewType
     * @param parent
     * @return CourseViewHolder(itemView)*/
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    /**This method binds the information to the view holder.
     * @param position
     * @param holder */
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course currentCourse = mCourses.get(position);
            String title = currentCourse.getCourseTitle();
            int termID = currentCourse.getTermId();
            holder.courseItemView.setText(title);
            holder.courseItemView2.setText(Integer.toString(termID));
        } else {
            holder.courseItemView.setText("No course title");
            holder.courseItemView2.setText("No term ID");
        }
    }

    /**This method sets the courses to the list on the screen.
     * @param courses*/
    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    /**This method returns the size of mCourses.
     * @return mCourses.size()*/
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }
}

