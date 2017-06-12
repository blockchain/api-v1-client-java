package info.blockchain.api.statistics;

import com.google.gson.JsonObject;

public class Point {

    private float x;
    private float y;

    public Point (JsonObject pointJson) {
        this(
                pointJson.has("x") ? pointJson.get("x").getAsFloat() : 0.0F,
                pointJson.has("y") ? pointJson.get("y").getAsFloat() : 0.0F
        );
    }

    public Point (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
