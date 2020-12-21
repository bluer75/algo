package alg;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Create flight itinerary for given flights and starting point. It has to use
 * all flights or return null. If more flight itineraries are possible use the
 * first one (alphabetically).
 */
public class FlightItinerary {
    public static String find(String start, String[][] flights) {
        Set<Integer> available = IntStream.range(0, flights.length).boxed().collect(Collectors.toSet());
        Deque<Integer> selected = new LinkedList<>();
        List<String> itinerary = new LinkedList<>();
        find(flights, start, available, selected, itinerary);
        return itinerary.stream().min(String::compareTo).orElse(null);
    }

    private static void find(String[][] flights, String start, Set<Integer> available, Deque<Integer> selected,
            List<String> itinerary) {
        if (available.isEmpty()) {
            // base case
            itinerary.add(selected.stream().reduce(flights[selected.getFirst()][0], (i, f) -> i + " " + flights[f][1],
                    (i1, i2) -> i1 + " " + i2));
            return;
        }
        for (int i : available) {
            if (isValid(flights, start, selected.peekLast(), i)) {
                // make copy of available flights
                available = new HashSet<>(available);
                // select that flight
                selected.addLast(i);
                available.remove(i);
                // find next one
                find(flights, null, available, selected, itinerary);
                // un-select that flight
                selected.removeLast();
                available.add(i);
            }
        }
    }

    private static boolean isValid(String[][] flights, String start, Integer last, Integer next) {
        // if this is the first flight it has to begin at given start
        if (start != null) {
            return flights[next][0].equals(start);
        }
        // destination of last flight has to be the same as source of next flight
        return flights[last][1] == flights[next][0];
    }

    public static void main(String... args) {
        // source, destination
        String[][] flights = { { "A", "B" }, { "A", "C" }, { "B", "C" }, { "C", "A" } };
        System.out.println(find("A", flights));
    }
}
