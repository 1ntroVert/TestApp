package testapp.andrey.com.testapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import testapp.andrey.com.testapp.R;
import testapp.andrey.com.testapp.models.Vacancy;
import testapp.andrey.com.testapp.models.VacancyManager;

public class VacancyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView vacanciesListView;

    private VacancyPreviewAdapter vacancyPreviewAdapter;
    private VacancyManager vacancyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_list);

        vacancyManager = VacancyManager.getInstance(getApplicationContext());

        initUI();
    }

    private void initUI() {
        setTitle(getString(R.string.vacancy_list_label) + " " + Integer.toString(vacancyManager.vacancies.size()));

        vacanciesListView = (ListView) findViewById(R.id.vacancies_list_view);
        vacanciesListView.setOnItemClickListener(this);
        vacancyPreviewAdapter = new VacancyPreviewAdapter(this, R.layout.vacancy_preview,
                new ArrayList<>(vacancyManager.vacancies.values()));
        vacanciesListView.setAdapter(vacancyPreviewAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() != R.id.vacancies_list_view) {
            return;
        }
        Vacancy vacancy = vacancyPreviewAdapter.getItem(position);
        Intent intent = new Intent(this, VacancyActivity.class);
        intent.putExtra("vacancy_id", vacancy.id);
        startActivity(intent);
    }
}
