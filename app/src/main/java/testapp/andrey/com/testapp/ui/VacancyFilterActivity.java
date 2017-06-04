package testapp.andrey.com.testapp.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import testapp.andrey.com.testapp.R;
import testapp.andrey.com.testapp.models.OnVacanciesReceivedListener;
import testapp.andrey.com.testapp.models.VacancyManager;

public class VacancyFilterActivity extends AppCompatActivity implements View.OnClickListener, OnVacanciesReceivedListener{
    private ProgressBar progressBar;
    private Button searchButton;
    private EditText keywordEditText;
    private EditText salaryEditText;
    private EditText limitEditText;
    private Button otherParamsButton;
    private Spinner periodSpinner;
    private RelativeLayout otherParamsLayout;

    private VacancyManager vacancyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_filter);

        initUI();

        vacancyManager = VacancyManager.getInstance(getApplicationContext());
        vacancyManager.setVacanciesListener(this);
    }

    private void initUI() {
        setTitle(R.string.vacancy_search);
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        keywordEditText = (EditText) findViewById(R.id.keyword_edit_text);
        salaryEditText = (EditText) findViewById(R.id.min_salary_edit_text);
        limitEditText = (EditText) findViewById(R.id.limity_edit_text);
        otherParamsLayout = (RelativeLayout) findViewById(R.id.other_params_layout);
        otherParamsButton = (Button) findViewById(R.id.other_button);
        otherParamsButton.setOnClickListener(this);
        periodSpinner = (Spinner) findViewById(R.id.period_spinner);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                searchButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                vacancyManager.setupFilter(keywordEditText.getText().toString(),
                        salaryEditText.getText().toString(),
                        periodSpinner.getSelectedItemPosition(),
                        limitEditText.getText().toString());
                vacancyManager.requestAllVacancies();
                break;
            case R.id.other_button:
                int visiblity = otherParamsLayout.getVisibility();
                if (visiblity == View.VISIBLE){
                    otherParamsLayout.setVisibility(View.GONE);
                } else if (visiblity == View.GONE) {
                    otherParamsLayout.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onVacanciesReceived() {
        progressBar.setVisibility(View.INVISIBLE);
        searchButton.setEnabled(true);
        Intent intent = new Intent(this, VacancyListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onVacanciesRequestError() {
        progressBar.setVisibility(View.INVISIBLE);
        searchButton.setEnabled(true);
        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
    }
}
