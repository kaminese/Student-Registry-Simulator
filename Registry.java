// Name: MY CHI DUONG
// Student Number: 500641750

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Registry 
{
   private TreeMap<String, Student> students = new TreeMap<String, Student>();
   private TreeMap<String, ActiveCourse> courses = new TreeMap<String, ActiveCourse>();

   
   public Registry() throws FileNotFoundException, NoSuchElementException
   { // new file:
		File newfile = new File("students.txt");
		Scanner scanner = new Scanner(newfile);
	
	   while (scanner.hasNextLine()) {
		   String name = scanner.next();
		   String studentid = scanner.next();
		   Student NewStudent = new Student(name, studentid);
		   students.put(studentid, NewStudent);
	   }
	   scanner.close();
	   
	   ArrayList<Student> list = new ArrayList<Student>();

	   // Add some active courses with students
	   String courseName = "Computer Science II";
	   String courseCode = "CPS209";
	   String descr = "Learn how to write complex programs!";
	   String format = "3Lec 2Lab";
	   courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));

	   // CPS511
	   list.clear();
	   courseName = "Computer Graphics";
	   courseCode = "CPS511";
	   descr = "Learn how to write cool graphics programs";
	   format = "3Lec";
	   courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"F2020",list));
	  
	   // CPS643
	   list.clear();
	   courseName = "Virtual Reality";
	   courseCode = "CPS643";
	   descr = "Learn how to write extremely cool virtual reality programs";
	   format = "3Lec 2Lab";
	   courses.put(courseCode, new ActiveCourse(courseName, courseCode, descr, format, "W2020", list)); 

		// CPS706
		list.clear();
		courseName = "Computer Networks";
		courseCode = "CPS706";
		descr = "Learn about Computer Networking";
		format = "3Lec 1Lab";
		courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));

		// CPS616
		list.clear();
		courseName = "Algorithms";
		courseCode = "CPS616";
		descr = "Learn about Algorithms";
		format = "3Lec 1Lab";
		courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));
   }

   // contains key id, setstring = courses.keysets, for string code = 
   public boolean addNewStudent(String name, String id)
   {
	   Student newStudent = new Student(name, id);
	   if (findStudent(id) != null) return false; {
		students.put(id, newStudent);
	   }
	   return true;
   }
   
   public boolean removeStudent(String studentId)
   {
	   if (students.containsKey(studentId)) {
		   students.remove(studentId);
		   
		   return true;
	   }
	   return false;
   }
   
   public void printAllStudents()
   {
	   for (String key : students.keySet()) {
		System.out.println("ID: " + key + " Name: " + students.get(key).getName());
	   }
   }
   
   private Student findStudent(String id)
   {
		Set<String> studentkeys = students.keySet();
		for (String tempsid : studentkeys) {
			if (id.equals(tempsid)) {
				return students.get(id);
			}
		}
	return null;
   }
   
   // returns the treemap courses
   public TreeMap<String,ActiveCourse> getMap() {
		return courses;
   } 

   
   private ActiveCourse findCourse(String code)
   {
	for (String key : courses.keySet()) {
		if (key.equals(code)) {
			return courses.get(code);
		}
	}
	return null;
   }
   
   public void addCourse(String studentId, String courseCode)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   if (s.takenCourse(courseCode)) return;
	   
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   if (ac.enrolled(studentId)) return;
			   
	   ac.students.add(s);
	   s.addCourse(ac.getName(),ac.getCode(),ac.getCourseDescription(),ac.getFormat(),ac.getSemester(),0);
   }
   

   public void dropCourse(String studentId, String courseCode)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.remove(studentId);
	   s.removeActiveCourse(courseCode);
   }
   
   public void printActiveCourses()
   {
	for (String key : courses.keySet()) {
		System.out.println(courses.get(key).getDescription());
		}
   }
   
   public void printClassList(String courseCode)
   {
		for (String key : courses.keySet()) {
			if (key.equals(courseCode)) {
				ActiveCourse ac = courses.get(key);
				if (ac.getCode().equalsIgnoreCase(courseCode)) {
					ac.printClassList();
				}
			}
		}
	}
 
   public void sortCourseByName(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.sortByName();
   }
   
   public void sortCourseById(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.sortById();	   
   }
   
   public void printGrades(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.printGrades();
   }
   
   public void printStudentCourses(String studentId)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   s.printActiveCourses();
   }
   
   public void printStudentTranscript(String studentId)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   s.printTranscript();
   }
   
   public void setFinalGrade(String courseCode, String studentId, double grade)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   s.setGrade(courseCode, grade);
	   
   }
}
