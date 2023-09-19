import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents;
    }

    public boolean hasAvailableSlots() {
        return registeredStudents < capacity;
    }

    public void registerStudent() {
        registeredStudents++;
    }

    public void dropStudent() {
        registeredStudents--;
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.registerStudent();
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.dropStudent();
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        List<Course> courseDatabase = new ArrayList<>();
        courseDatabase.add(new Course("ECC701", "Microwave Engineering", "High-Frequency Circuits", 30, "Mon/Wed 10:00 AM"));
        courseDatabase.add(new Course("ECC702", "Mobile Communication System", "Wireless Network Technology", 25, "Tue/Thu 9:30 AM"));
        courseDatabase.add(new Course("ECCDLO 7013", "Cloud Computing and Security", "Virtualization and Data Protection", 20, "Mon/Wed/Fri 11:00 AM"));
        courseDatabase.add(new Course("ECCILO 7013", "Management Information System", "Business Data Management", 35, "Tue/Thu 2:00 PM"));
        courseDatabase.add(new Course("ECC011", "Programming Languages", "Software Development Fundamentals", 28, "Wed/Fri 1:30 PM"));

        List<Student> studentDatabase = new ArrayList<>();
        studentDatabase.add(new Student(12345, "Rohit Sharma"));
        studentDatabase.add(new Student(67890, "Virat Kohli"));

        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("1. Course Listing");
            System.out.println("2. Student Registration");
            System.out.println("3. Course Removal");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Available Courses:");
                    for (int i = 0; i < courseDatabase.size(); i++) {
                        Course course = courseDatabase.get(i);
                        System.out.println((i + 1) + ". " + course.getTitle() + " (" + course.getCourseCode() + ")");
                        System.out.println("   Description: " + course.getDescription());
                        System.out.println("   Schedule: " + course.getSchedule());
                        System.out.println("   Available Slots: " + course.getAvailableSlots());
                        System.out.println(); // Add an empty line for separation
                    }
                    break;
                case 2:
                    System.out.println("Student Registration");
                    System.out.print("Enter student ID: ");
                    int studentID = scanner.nextInt();

                    // Check if the student already exists
                    boolean studentExists = false;
                    Student existingStudent = null;
                    for (Student s : studentDatabase) {
                        if (s.getStudentID() == studentID) {
                            studentExists = true;
                            existingStudent = s;
                            break;
                        }
                    }

                    if (studentExists) {
                        System.out.println("Student with ID " + studentID + " already exists.");
                        System.out.print("Do you want to add courses to an existing student? (yes/no): ");
                        String addCoursesResponse = scanner.next().toLowerCase();

                        if (addCoursesResponse.equals("yes")) {
                            System.out.println("Available Courses:");
                            for (int i = 0; i < courseDatabase.size(); i++) {
                                Course course = courseDatabase.get(i);
                                System.out.println((i + 1) + ". " + course.getTitle() + " (" + course.getCourseCode() + ")");
                            }

                            System.out.print("Enter the number of the course to register for: ");
                            int courseNumber = scanner.nextInt();

                            if (courseNumber >= 1 && courseNumber <= courseDatabase.size()) {
                                Course selectedCourse = courseDatabase.get(courseNumber - 1);
                                if (selectedCourse.hasAvailableSlots()) {
                                    existingStudent.registerCourse(selectedCourse);
                                    System.out.println("Course registration successful.");
                                } else {
                                    System.out.println("Course is already full.");
                                }
                            } else {
                                System.out.println("Invalid course number.");
                            }
                        }
                    } else {
                        // Student doesn't exist, create a new student
                        System.out.print("Enter student name: ");
                        String studentName = scanner.next();
                        Student newStudent = new Student(studentID, studentName);
                        studentDatabase.add(newStudent);

                        System.out.println("Student added successfully.");

                        System.out.println("Available Courses:");
                        for (int i = 0; i < courseDatabase.size(); i++) {
                            Course course = courseDatabase.get(i);
                            System.out.println((i + 1) + ". " + course.getTitle() + " (" + course.getCourseCode() + ")");
                        }

                        System.out.print("Enter the number of the course to register for: ");
                        int courseNumber = scanner.nextInt();

                        if (courseNumber >= 1 && courseNumber <= courseDatabase.size()) {
                            Course selectedCourse = courseDatabase.get(courseNumber - 1);
                            if (selectedCourse.hasAvailableSlots()) {
                                newStudent.registerCourse(selectedCourse);
                                System.out.println("Course registration successful.");
                            } else {
                                System.out.println("Course is already full.");
                            }
                        } else {
                            System.out.println("Invalid course number.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Course Removal");
                    System.out.print("Enter student ID: ");
                    int studentIDToRemoveCourse = scanner.nextInt();

                    Student studentToRemove = null;
                    for (Student s : studentDatabase) {
                        if (s.getStudentID() == studentIDToRemoveCourse) {
                            studentToRemove = s;
                            break;
                        }
                    }

                    if (studentToRemove == null) {
                        System.out.println("Student with ID " + studentIDToRemoveCourse + " not found.");
                    } else {
                        List<Course> registeredCourses = studentToRemove.getRegisteredCourses();

                        if (registeredCourses.isEmpty()) {
                            System.out.println("Student has no registered courses.");
                        } else {
                            System.out.println("Registered Courses:");
                            for (int i = 0; i < registeredCourses.size(); i++) {
                                Course course = registeredCourses.get(i);
                                System.out.println((i + 1) + ". " + course.getTitle() + " (" + course.getCourseCode() + ")");
                            }

                            System.out.print("Enter the number of the course to remove: ");
                            int courseNumberToRemove = scanner.nextInt();

                            if (courseNumberToRemove >= 1 && courseNumberToRemove <= registeredCourses.size()) {
                                Course courseToRemove = registeredCourses.get(courseNumberToRemove - 1);
                                studentToRemove.dropCourse(courseToRemove);
                                System.out.println("Course removal successful.");
                            } else {
                                System.out.println("Invalid course number.");
                            }
                        }
                    }
                    break;

                case 4:
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }

        System.out.println("Thank you for using the Course Registration System!");
    }
}
