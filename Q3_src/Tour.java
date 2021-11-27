import java.util.ArrayList;
import java.util.List;

public class Tour {
    List<City> cities;
    List<Double> lengths = new ArrayList<>();
    Double totalLength = 0.0;

    public Tour(List<City>cities) {
        this.cities = new ArrayList<>(cities);
        List<City>copy = new ArrayList<>(cities);
        City current = copy.remove(0);
        while(copy.size()>0) {
            City next = copy.remove(0);
            this.lengths.add(current.distanceTo(next));
            current = next;
        }
        this.lengths.add(current.distanceTo(this.cities.get(0)));
        for(Double len : lengths)
            this.totalLength += len;
    }

}
