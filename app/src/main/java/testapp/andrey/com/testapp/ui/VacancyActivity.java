package testapp.andrey.com.testapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import testapp.andrey.com.testapp.R;
import testapp.andrey.com.testapp.Utils;
import testapp.andrey.com.testapp.models.Vacancy;
import testapp.andrey.com.testapp.models.VacancyManager;

public class VacancyActivity extends AppCompatActivity {
    private TextView dateTextView;
    private TextView positionTextView;
    private TextView salaryTextView;
    private TextView vacancyInfoTextView;
    private TextView companyInfoTextView;
    private TextView descTextView;
    private ImageView companyLogoImageView;

    private VacancyManager vacancyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        vacancyManager = VacancyManager.getInstance(getApplicationContext());

        initUI();
    }

    private void initUI() {
        Bundle extras = getIntent().getExtras();
        int vacancyId = extras.getInt("vacancy_id");
        Vacancy currentVacancy = vacancyManager.vacancies.get(vacancyId);

        setTitle(currentVacancy.position);

        dateTextView = (TextView) findViewById(R.id.date_text_view);
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        salaryTextView = (TextView) findViewById(R.id.salary_text_view);
        vacancyInfoTextView = (TextView) findViewById(R.id.vacancy_info_text_view);
        companyInfoTextView = (TextView) findViewById(R.id.company_info_text_view);
        descTextView = (TextView) findViewById(R.id.desc_text_view);
        companyLogoImageView = (ImageView) findViewById(R.id.logo);

        String date = "";
        if (currentVacancy.addDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentVacancy.addDate);
            date += getString(R.string.add_date) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " +
                    cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        }
        if (currentVacancy.modDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentVacancy.modDate);
            date += "\n" + getString(R.string.mod_date) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " +
                    cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        }
        dateTextView.setText(date);

        positionTextView.setText(currentVacancy.position);

        salaryTextView.setText(getString(R.string.salary) + ": " +
                currentVacancy.minSalaryRub + " - " + currentVacancy.maxSalaryRub + " т.р.");

        String vacancyInfo = "";
        vacancyInfo += getString(R.string.city) + " " + currentVacancy.contact.city;
        vacancyInfo += "\n" + getString(R.string.requirements) + " " + currentVacancy.requirements;
        vacancyInfo += "\n" + getString(R.string.workingType) + " " + currentVacancy.workingType;
        vacancyInfoTextView.setText(vacancyInfo);

        String companyInfo = currentVacancy.company.title + "\n" + currentVacancy.contact.address;
        companyInfoTextView.setText(companyInfo);

        descTextView.setText(Html.fromHtml(currentVacancy.description));

        new Utils.DownloadLogoTask(this, companyLogoImageView).execute(currentVacancy.company.logoUrl);
    }
}
