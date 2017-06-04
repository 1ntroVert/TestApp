package testapp.andrey.com.testapp.models;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Класс, отвечающий за формирование URL для запроса
 */
public class Filter {
    private int minSalary = -1;
    private int limit = -1;
    private String q = "";
    private String period = "";

    public String getUrl() {
        String request = "https://api.zp.ru/v1/vacancies?";
        if (!q.equals("")) {
            request += "q=" + q + "&";
        }
        if (!period.equals("")) {
            request += "period=" + period + "&";
        }
        if (minSalary != -1) {
            request += "salary=" + Integer.toString(minSalary) + "&";
        }
        if (limit != -1) {
            request += "limit=" + Integer.toString(limit) + "&";
        }
        return request;
    }

    public void setup(String keyword, String minSalary, int periodId, String limit) {
        q = keyword;
        this.minSalary = !minSalary.equals("") ? Integer.parseInt(minSalary) : -1;
        switch (periodId) {
            case 1:
                period = "today";
                break;
            case 2:
                period = "three";
                break;
            case 3:
                period = "week";
                break;
            case 4:
                period = "month";
                break;
            default:
                period = "";
                break;
        }
        this.limit = !limit.equals("") ? Integer.parseInt(limit) : -1;
    }
}
