import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Collections.swap;

public class TSP {
    final static Double THRESHOLD = 1E-10;
    List<City> cities = new ArrayList<>();

    public TSP(int city) {
        for(int i=0; i<city; i++)
            cities.add(new City());
    }

    public static List<TSP> massGenerator(int city, int tsp) {
        List<TSP>instances = new ArrayList<>();
        for(int i=0; i<tsp; i++)
            instances.add(new TSP(city));
        return instances;
    }

    public Tour bruteForce() {
        List<City>path = new ArrayList<>(this.cities);
        Tour shortest = new Tour(path);
        int n = this.cities.size();
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = 0;
        int i=0;
        while(i < n) {
            if (indices[i] < i) {
                Collections.swap(path, i % 2 == 0 ?  0: indices[i], i);
                Tour newTour = new Tour(path);
                if(newTour.totalLength < shortest.totalLength)
                    shortest = newTour;
                indices[i]++;
                i = 0;
            }
            else {
                indices[i] = 0;
                i++;
            }
        }
        return shortest;
    }

    public Tour randomized() {
        Random r = new Random();
        List<City>toGo = new ArrayList<>(this.cities);
        List<City>path = new ArrayList<>();
        while(toGo.size() > 0)
            path.add(toGo.remove(r.nextInt(toGo.size())));
        return new Tour(path);
    }

    // pseudocode from wikipedia
    /*
    procedure 2optSwap(route, i, k) {
        1. take route[0] to route[i-1] and add them in order to new_route
        2. take route[i] to route[k] and add them in reverse order to new_route
        3. take route[k+1] to end and add them in order to new_route
        return new_route;
    }
    repeat until no improvement is made {
        start_again:
        best_distance = calculateTotalDistance(existing_route)
        for (i = 1; i <= number of nodes eligible to be swapped - 1; i++) {
            for (k = i + 1; k <= number of nodes eligible to be swapped; k++) {
                new_route = 2optSwap(existing_route, i, k)
                new_distance = calculateTotalDistance(new_route)
                if (new_distance < best_distance) {
                    existing_route = new_route
                    best_distance = new_distance
                    goto start_again
                }
            }
        }
    }
    */
    public List<City> twoOptSwap(List<City>path, int i, int k) {
        List<City>newPath = new ArrayList<>();
        for(int j=0; j<i; j++)
            newPath.add(path.get(j));
        for(int j=k; j>=i; j--)
            newPath.add(path.get(j));
        for(int j=k+1; j<path.size(); j++)
            newPath.add(path.get(j));
        return newPath;
    }

    public Tour hillClimbing() {
        Tour baseTour = randomized();
        boolean changeMade = true;
        while(changeMade) {
            changeMade = false;
            Double minDistance = baseTour.totalLength;
            for(int i=1; i<= baseTour.cities.size()-1; i++) {
                for(int k=i; k<=baseTour.cities.size()-1; k++) {
                    Tour newTour = new Tour(twoOptSwap(baseTour.cities,i, k));
                    Double newDistance = newTour.totalLength;
                    if(newDistance < minDistance) {
                        baseTour = newTour;
                        minDistance = newDistance;
                        changeMade = true;
                    }
                }
            }
        }
        return baseTour;
    }

    public static void main(String[]args) {

        List<TSP>tsp = massGenerator(7, 100);

        List<Double>bruteSol = new ArrayList<>();
        List<Double>randmSol = new ArrayList<>();
        List<Double>hillCSol = new ArrayList<>();

        int randomFoundOptimal = 0;
        int hillFoundOptimal = 0;
        for(TSP p : tsp) {
            Tour brute = p.bruteForce();
            Tour randm = p.randomized();
            Tour hillC = p.hillClimbing();
            if(Math.abs(brute.totalLength - randm.totalLength) < THRESHOLD)
                randomFoundOptimal++;
            if(Math.abs(brute.totalLength - hillC.totalLength) < THRESHOLD)
                hillFoundOptimal++;
            bruteSol.add(brute.totalLength);
            randmSol.add(randm.totalLength);
            hillCSol.add(hillC.totalLength);
        }

        Stats bruteStats = new Stats(bruteSol);
        Stats randmStats = new Stats(randmSol);
        Stats hillCStats = new Stats(hillCSol);

        System.out.println("Brute Search  - 7 cities");
        bruteStats.display();
        System.out.println("Random Paths  - 7 cities");
        System.out.println("Optimal    : "+randomFoundOptimal);
        randmStats.display();
        System.out.println("Hill Climbing - 7 cities");
        System.out.println("Optimal    : "+hillFoundOptimal);
        hillCStats.display();

        List<TSP>tsp100 = massGenerator(100, 100);

        List<Double>bruteSol100 = new ArrayList<>();
        List<Double>randmSol100 = new ArrayList<>();
        List<Double>hillCSol100 = new ArrayList<>();

        int randomFoundOptimal100 = 0;
        int hillFoundOptimal100 = 0;
        for(TSP p : tsp100) {
            Tour brute100 = p.bruteForce();
            Tour randm100 = p.randomized();
            Tour hillC100 = p.hillClimbing();
            if(Math.abs(brute100.totalLength - randm100.totalLength) < THRESHOLD)
                randomFoundOptimal100++;
            if(Math.abs(brute100.totalLength - hillC100.totalLength) < THRESHOLD)
                hillFoundOptimal100++;
            bruteSol100.add(brute100.totalLength);
            randmSol100.add(randm100.totalLength);
            hillCSol100.add(hillC100.totalLength);
        }

        Stats brute100Stats = new Stats(bruteSol100);
        Stats randm100Stats = new Stats(randmSol100);
        Stats hillC100Stats = new Stats(hillCSol100);

        System.out.println("Brute Search  - 100 cities");
        brute100Stats.display();
        System.out.println("Random Paths  - 100 cities");
        System.out.println("Optimal    : "+randomFoundOptimal100);
        randm100Stats.display();
        System.out.println("Hill Climbing - 100 cities");
        System.out.println("Optimal    : "+hillFoundOptimal100);
        hillC100Stats.display();

    }
}
