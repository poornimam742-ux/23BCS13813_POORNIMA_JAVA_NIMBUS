import java.util.*;

class Student {
    String name;
    double marks;

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentFilter {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Riya", 88),
            new Student("Karan", 72),
            new Student("Meena", 95),
            new Student("Arjun", 65)
        );

        System.out.println("Students scoring above 75 (sorted by marks):");
        students.stream()
                .filter(st -> st.marks > 75)
                .sorted((a, b) -> Double.compare(a.marks, b.marks))
                .map(st -> st.name)
                .forEach(n -> System.out.println(n));
    }
}
