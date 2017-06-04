package testapp.andrey.com.testapp.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import testapp.andrey.com.testapp.R;
import testapp.andrey.com.testapp.models.Vacancy;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Адаптер для отображения списка вакансий
 */
public class VacancyPreviewAdapter extends ArrayAdapter {
    private final List<Vacancy> vacancies;
    private final Context context;
    private final int resId;

    public VacancyPreviewAdapter(@NonNull Context context, @LayoutRes int resId, List<Vacancy> vacancies) {
        super(context, resId);
        this.vacancies = vacancies;
        this.context = context;
        this.resId = resId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(resId, null, true);
        TextView positionTextView = (TextView) viewRow.findViewById(R.id.position_text_view);
        positionTextView.setText(vacancies.get(i).position);
        TextView companyAndPlaceTextView = (TextView) viewRow.findViewById(R.id.company_and_place_text_view);
        companyAndPlaceTextView.setText(vacancies.get(i).company.title);
        TextView salaryTextView = (TextView) viewRow.findViewById(R.id.salary_text_view);
        salaryTextView.setText(context.getString(R.string.salary) + ": " +
                vacancies.get(i).minSalaryRub + " - " + vacancies.get(i).maxSalaryRub + " т.р.");
        TextView addDateTextView = (TextView) viewRow.findViewById(R.id.add_date_text_view);
        if (vacancies.get(i).addDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(vacancies.get(i).addDate);
            String addDate = cal.get(Calendar.DAY_OF_MONTH) + " " +
                    cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            addDateTextView.setText(addDate);
        }
        return viewRow;
    }

    @Override
    public int getCount() {
        return vacancies.size();
    }

    @Override
    public Vacancy getItem(int position) {
        return vacancies.get(position);
    }
}
