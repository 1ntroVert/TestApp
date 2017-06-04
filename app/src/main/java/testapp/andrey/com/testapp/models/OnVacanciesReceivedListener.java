package testapp.andrey.com.testapp.models;

import java.util.List;

import testapp.andrey.com.testapp.models.Vacancy;

/**
 * Created by Андрей on 04.06.2017.
 */

public interface OnVacanciesReceivedListener {
    void onVacanciesReceived();
    void onVacanciesRequestError();
}
