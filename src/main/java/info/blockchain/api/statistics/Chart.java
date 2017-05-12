package info.blockchain.api.statistics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Chart {

    private String status;
    private String name;
    private String unit;
    private String period;
    private String description;
    private List<Point> values;

    public Chart (JsonObject chartJson) {
        this(
                chartJson.has("status") ? chartJson.get("status").getAsString() : "",
                chartJson.has("name") ? chartJson.get("name").getAsString() : "",
                chartJson.has("unit") ? chartJson.get("unit").getAsString() : "",
                chartJson.has("period") ? chartJson.get("period").getAsString() : "",
                chartJson.has("description") ? chartJson.get("description").getAsString() : "",
                null
        );
        values = getPoints(chartJson);
    }

    public Chart (String status, String name, String unit, String period, String description, List<Point> values) {
        this.status = status;
        this.name = name;
        this.unit = unit;
        this.period = period;
        this.description = description;
        this.values = values;
    }

    private List<Point> getPoints(JsonObject chartJson) {
        List<Point> points = new ArrayList<Point>();
        for (JsonElement pointElement : chartJson.getAsJsonArray()) {
            JsonObject pointJson = pointElement.getAsJsonObject();
            points.add(new Point(pointJson));
        }

        return points;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public List<Point> getValues() {
        return values;
    }
}
