package testapp.andrey.com.testapp.models;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import testapp.andrey.com.testapp.Utils;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Класс - хранилище данных
 */
public class VacancyManager {
    public HashMap<Integer, Vacancy> vacancies = new HashMap<>();

    private final String LOG_TAG = VacancyManager.class.getName();
    private static VacancyManager instance;
    private RequestQueue vacancyRequestQueue;
    private OnVacanciesReceivedListener onVacanciesReceivedListener;
    private Filter filter = new Filter();

    public static synchronized VacancyManager getInstance(Context context) {
        if (instance == null) {
            instance = new VacancyManager(context);
        }
        return instance;
    }

    private VacancyManager(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB
        Network network = new BasicNetwork(new HurlStack());
        vacancyRequestQueue = new RequestQueue(cache, network);
        vacancyRequestQueue.start();
    }

    public void setVacanciesListener(OnVacanciesReceivedListener listener) {
        onVacanciesReceivedListener = listener;
    }

    public void setupFilter(String keyword, String minSalary, int periodId, String limit) {
        filter.setup(keyword, minSalary, periodId, limit);
    }

    public void requestAllVacancies() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, filter.getUrl(), null, vacancyListener, vacancyErrorListener);

        vacancyRequestQueue.add(jsObjRequest);
    }

    private void onVacanciesReceived() {
        if (onVacanciesReceivedListener != null) {
            onVacanciesReceivedListener.onVacanciesReceived();
        }
    }

    private void onVacanciesRequestError() {
        if (onVacanciesReceivedListener != null) {
            onVacanciesReceivedListener.onVacanciesRequestError();
        }
    }

    private Response.Listener<JSONObject> vacancyListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            vacancies.clear();
            try {
                JSONArray jsonVacancies = response.getJSONArray("vacancies");
                for (int i = 0; i < jsonVacancies.length(); i++) {
                    Vacancy vacancy = parseVacancy(jsonVacancies.getJSONObject(i));
                    if (vacancy != null) {
                        vacancies.put(vacancy.id, vacancy);
                    }
                }
                onVacanciesReceived();
            } catch (JSONException e) {
                e.printStackTrace();
                onVacanciesRequestError();
            }
        }
    };

    private Response.ErrorListener vacancyErrorListener = new Response.ErrorListener()  {
        @Override
        public void onErrorResponse(VolleyError error) {
            onVacanciesRequestError();
        }
    };

    private Vacancy parseVacancy(JSONObject jsonVacancy) throws JSONException {
        Date addDate = null;
        Date modDate = null;
        try {
            addDate = Utils.toDate(jsonVacancy.getString("add_date"));
            modDate = Utils.toDate(jsonVacancy.getString("mod_date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int id = jsonVacancy.getInt("id");
        String url = jsonVacancy.getString("url");
        String position = jsonVacancy.getString("header");
        Company company = parceCompany(jsonVacancy.getJSONObject("company"));
        Contact contact = parceContact(jsonVacancy.getJSONObject("contact"));
        String description = jsonVacancy.getString("description");
        int minSalary = jsonVacancy.getInt("salary_min");
        int maxSalary = jsonVacancy.getInt("salary_max");
        int minSalaryRub = jsonVacancy.getInt("salary_min_rub");
        int maxSalaryRub = jsonVacancy.getInt("salary_max_rub");
        String salary = jsonVacancy.getString("salary");
        String salaryFormatted = jsonVacancy.getString("salary_formatted");

        int bonus = jsonVacancy.getInt("bonus");
        String requirements = jsonVacancy.getString("requirements_short");
        String paymentTypeAlias = jsonVacancy.getString("payment_type");

        String education = !jsonVacancy.isNull("education") ?
                jsonVacancy.getJSONObject("education").getString("title") : "-";
        String schedule = !jsonVacancy.isNull("schedule") ?
                jsonVacancy.getJSONObject("schedule").getString("title") : "-";
        String experience = !jsonVacancy.isNull("experience_length") ?
                jsonVacancy.getJSONObject("experience_length").getString("title") : "-";
        String workingType = !jsonVacancy.isNull("working_type") ?
                jsonVacancy.getJSONObject("working_type").getString("title") : "-";

        List<String> rubrics = new ArrayList<>();
        JSONArray rubricsArray = jsonVacancy.getJSONArray("rubrics").getJSONObject(0).getJSONArray("specialities");
        for (int i = 0; i < rubricsArray.length(); i++) {
            rubrics.add(rubricsArray.getJSONObject(i).getString("title"));
        }

        return new Vacancy(id, url,
                position,
                company, contact,
                description,
                minSalary, maxSalary,
                maxSalaryRub, minSalaryRub,
                salary, salaryFormatted,
                bonus,
                addDate, modDate,
                workingType,
                education,
                requirements,
                paymentTypeAlias,
                rubrics,
                schedule,
                experience);
    }

    private Company parceCompany(JSONObject jsonCompany) throws JSONException {
        String logoUrl = !jsonCompany.isNull("logo") ?
                jsonCompany.getJSONObject("logo").getString("url") : "";
        String title = !jsonCompany.isNull("title") ?
                jsonCompany.getString("title") : "-";
        String employeesCountAlias = !jsonCompany.isNull("employees") ?
                jsonCompany.getString("employees") : "-";
        return new Company(logoUrl, title, employeesCountAlias);
    }

    private Contact parceContact(JSONObject jsonCompany) throws JSONException {
        String city = !jsonCompany.isNull("city") ?
                jsonCompany.getJSONObject("city").getString("title") : null;
        String address = jsonCompany.getString("address");
        String addressDesc = jsonCompany.getString("address_description");
        String street = jsonCompany.getString("street");
        String building = jsonCompany.getString("building");
        String name = jsonCompany.getString("name");
        String firstName = jsonCompany.getString("firstname");
        String lastName = jsonCompany.getString("lastname");
        String url = jsonCompany.getString("url");
        String email = jsonCompany.getString("email");

        return new Contact(
                city, address, addressDesc,
                street, building,
                name, firstName, lastName,
                url, email);
    }
}
