import java.util.List;
import java.util.Random;

public class City {
    double x;
    double y;

    public City() {
        Random r = new Random();
        this.x = r.nextDouble();
        this.y = r.nextDouble();
    }

    public City(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(City c) {
        double xDif = Math.abs(this.x - c.x);
        double yDif = Math.abs(this.y - c.y);
        double xSqr = Math.pow(xDif,2);
        double ySqr = Math.pow(yDif,2);
        return Math.sqrt(xSqr+ySqr);
    }


}
