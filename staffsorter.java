import java.util.*;

class StaffMember {
    String fullName;
    int years;
    double wage;

    public StaffMember(String fullName, int years, double wage) {
        this.fullName = fullName;
        this.years = years;
        this.wage = wage;
    }

    @Override
    public String toString() {
        return fullName + " | Years: " + years + " | Wage: " + wage;
    }
}

public class StaffSorter {
    public static void main(String[] args) {
        List<StaffMember> staffList = new ArrayList<>();
        staffList.add(new StaffMember("Arjun", 30, 50000));
        staffList.add(new StaffMember("Bhavna", 25, 60000));
        staffList.add(new StaffMember("Chirag", 28, 55000));
        staffList.add(new StaffMember("Deepa", 35, 45000));

        // Sort by full name alphabetically
        staffList.sort((s1, s2) -> s1.fullName.compareTo(s2.fullName));
        System.out.println("Arranged by Full Name:");
        staffList.forEach(System.out::println);

        // Sort by years ascending
        staffList.sort(Comparator.comparingInt(s -> s.years));
        System.out.println("\nArranged by Years:");
        staffList.forEach(System.out::println);

        // Sort by wage descending
        staffList.sort((s1, s2) -> Double.compare(s2.wage, s1.wage));
        System.out.println("\nArranged by Wage (High to Low):");
        staffList.forEach(System.out::println);
    }
}
