import java.util.ArrayList;
import java.util.List;

public class Stats {
    List<Double> lengths;

    public Stats(List<Double>lengths) {
        this.lengths = new ArrayList<>(lengths);
    }

    public double lengthSum() {
        double length = 0;
        for(Double len : this.lengths)
            length += len;
        return length;
    }

    public double mean(){
        return this.lengthSum()/(this.lengths.size());
    }

    public double min(){
        double min = Double.MAX_VALUE;
        for(Double len : this.lengths)
            if(len < min) min = len;
        return min;
    }

    public double max(){
        double max = Double.MIN_VALUE;
        for(Double len : this.lengths)
            if(len > max) max = len;
        return max;
    }

    public double stdv(){
        // STRDV = sqrt([sum(item-mean)^2]/n)
        List<Double> meanDif = new ArrayList<>();
        Double mean = this.mean();
        for(Double len : this.lengths)
            meanDif.add(len-mean);
        double sum = 0;
        for(Double dif : meanDif)
            sum += Math.pow(dif, 2);
        return Math.sqrt(sum/this.lengths.size());
    }
    
    public void display() {
        System.out.println("Min        : "+this.min());
        System.out.println("Max        : "+this.max());
        System.out.println("Mean       : "+this.mean());
        System.out.println("SDeviation : "+this.stdv());
        System.out.println();
    }
}
