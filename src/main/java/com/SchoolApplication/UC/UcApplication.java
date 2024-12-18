package com.SchoolApplication.UC;

import com.SchoolApplication.UC.models.*;
import com.SchoolApplication.UC.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class UcApplication {

	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(UcApplication.class, args);
	}

	@Bean
//un  bean es simplemente una clase Java normal, escrita para seguir algunas reglas importantes. Pedimos que se ejecute esto primero.
	public CommandLineRunner initData(UsuariooRepository usuariooRepository,
									  StudentRepository studentRepository,
									  TeacherRepository teacherRepository,
									  CourseRepository courseRepository,
									  ScheduleRepository scheduleRepository,
									  CourseScheduleRepository courseScheduleRepository,
									  StudentCourseRepository studentCourseRepository,
									  TeacherCourseRepository teacherCourseRepository,
									  AttendanceRepository attendanceRepository,
									  EnrollmentRepository enrollmentRepository) { // Define un bean de tipo CommandLineRunner que recibe un StudentRepository como parámetro.
		// initData toma una instancia de StudentRepository como parámetro y utiliza este repositorio para guardar algunos clientes en la base de datos al iniciar la aplicación.
		//Es un singleton que se ejecutan una vez cada vez que se inicia la aplicación.

		return (args) -> {

			// Create students
			Student student1 = new Student ("Melba","Morel","M.morel@me.com",passwordEncoder.encode("123"));
			Student student2 = new Student("Chloe", "O'Brian", "c.obrian@me.com", passwordEncoder.encode("456"));

			studentRepository.save(student1);
			studentRepository.save(student2);


			// Create teachers
			Teacher teacher1 = new Teacher("Miguel", "Hernandez", "m.hernandez@education.com", passwordEncoder.encode("789"));
			Teacher teacher2 = new Teacher("Gonzalo", "Gonzalez", "g.gonzalez@edu.com", passwordEncoder.encode("987"));

			teacherRepository.save(teacher1);
			teacherRepository.save(teacher2);

			// Create Courses

			Course course1 = new Course("Math", "Junior or 3rd year", 20);
			Course course2 = new Course("physics", "Senior or 4th year", 30);
			Course course3 = new Course("Chemistry", "Senior or 4th year", 10);

			courseRepository.save(course1);
			courseRepository.save(course2);
			courseRepository.save(course3);

			//Create Schedule
			Schedule schedule1 = new Schedule(Shift.MORNING, Arrays.asList("Monday and Wednesday" , "Tuesday and Thursday"), Arrays.asList("8:00 a 10:00", "10:00 a 12:00"));
			Schedule schedule2 = new Schedule(Shift.AFTERNOON, Arrays.asList("Monday and Friday" , "Tuesday and Thursday"), Arrays.asList("14:00 a 16:00", "16:00 a 18:00"));
			Schedule schedule3 = new Schedule(Shift.NIGHT, Arrays.asList("Tuesday and Friday" , "Wednesday and Thursday"), Arrays.asList("20:00 a 22:00", "22:00 a 24:00"));


			scheduleRepository.save(schedule1);
			scheduleRepository.save(schedule2);
			scheduleRepository.save(schedule3);


/////--------------------- Crear CourseSchedule y asociar los Horarios al estudiante Melba    --------------//


// ↓↓↓↓↓↓↓  Course1 junto con schedule1, schedule2 y schedule3 ↓↓↓↓↓↓↓
//          ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯         //

			//course 1 : =>=>=>=> MATH
			CourseSchedule courseSchedule1 = new CourseSchedule("Monday and Wednesday", "8:00 a 10:00");
			course1.addCourseSchedule(courseSchedule1);
			schedule1.addCourseSchedule(courseSchedule1);

			courseScheduleRepository.save(courseSchedule1);


			CourseSchedule courseSchedule2 = new CourseSchedule("Monday and Friday", "14:00 a 16:00");
			course1.addCourseSchedule(courseSchedule2);
			schedule2.addCourseSchedule(courseSchedule2);

			courseScheduleRepository.save(courseSchedule2);


			CourseSchedule courseSchedule3 = new CourseSchedule("Tuesday and Friday", "20:00 a 22:00");
			course1.addCourseSchedule(courseSchedule3);
			schedule3.addCourseSchedule(courseSchedule3);

			courseScheduleRepository.save(courseSchedule3);

// ↓↓↓↓↓↓↓  Course2 junto con schedule1, schedule2 y schedule3 ↓↓↓↓↓↓↓
//          ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯         //

			//course 2 : =>=>=>=> PHYSICS
			CourseSchedule courseSchedule4 = new CourseSchedule("Tuesday and Thursday", "10:00 a 12:00");
			course2.addCourseSchedule(courseSchedule4);
			schedule1.addCourseSchedule(courseSchedule4);

			courseScheduleRepository.save(courseSchedule4);

			CourseSchedule courseSchedule5 = new CourseSchedule("Tuesday and Thursday", "16:00 a 18:00");
			course2.addCourseSchedule(courseSchedule5);
			schedule2.addCourseSchedule(courseSchedule5);

			courseScheduleRepository.save(courseSchedule5);


			//course 3 : =>=>=>=> CHIMISTRY
			CourseSchedule courseSchedule6 = new CourseSchedule("Tuesday and Friday", "20:00 a 22:00");
			course3.addCourseSchedule(courseSchedule6);
			schedule3.addCourseSchedule(courseSchedule6);

			courseScheduleRepository.save(courseSchedule6);
//--------------------------------------------------------------------------------



/////--------------------- Crear StudentCourse y asociar los Cursos al estudiante Melba y Chloe     --------------//


			LocalDateTime now = LocalDateTime.now(); //Declaras una variable llamada now de tipo LocalDateTime
			System.out.println(now);


			// ↓↓↓↓↓↓↓  Student1 junto con course1, course2 y course3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			StudentCourse studentCourse1 = new StudentCourse(now, Status.APPROVED);
			student1.addStudentCourse(studentCourse1);
			course1.addStudentCourse(studentCourse1);

			studentCourseRepository.save(studentCourse1);


			StudentCourse studentCourse2 = new StudentCourse(now, Status.APPROVED);
			student1.addStudentCourse(studentCourse2);
			course2.addStudentCourse(studentCourse2);

			studentCourseRepository.save(studentCourse2);


//			StudentCourse studentCourse3 = new StudentCourse(now, Status.APPROVED);
//			student1.addStudentCourse(studentCourse3);
//			course3.addStudentCourse(studentCourse3);
//
//			studentCourseRepository.save(studentCourse3);


			// ↓↓↓↓↓↓↓  Student2 junto con course1, course2 y course3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			StudentCourse studentCourse4 = new StudentCourse(now, Status.APPROVED);
			student2.addStudentCourse(studentCourse4);
			course1.addStudentCourse(studentCourse4);

			studentCourseRepository.save(studentCourse4);


			StudentCourse studentCourse5 = new StudentCourse(now, Status.APPROVED);
			student2.addStudentCourse(studentCourse5);
			course2.addStudentCourse(studentCourse5);

			studentCourseRepository.save(studentCourse5);


			StudentCourse studentCourse6 = new StudentCourse(now, Status.APPROVED);
			student2.addStudentCourse(studentCourse6);
			course3.addStudentCourse(studentCourse6);

			studentCourseRepository.save(studentCourse6);

/////--------------------- Crear Enrollment y asociar a los StudentCourse y a los CourseSchedule     --------------//

			//Para Melba:
			Enrollment enrollment1 = new Enrollment("active");

			studentCourse1.addEnrollment(enrollment1);
			courseSchedule1.addEnrollment(enrollment1);

			enrollmentRepository.save(enrollment1);

			Enrollment enrollment2 = new Enrollment("active");

			studentCourse2.addEnrollment(enrollment2);
			courseSchedule5.addEnrollment(enrollment2);


			enrollmentRepository.save(enrollment2);

			Enrollment enrollment3 = new Enrollment("active");

			studentCourse6.addEnrollment(enrollment3);
			courseSchedule6.addEnrollment(enrollment3);

			enrollmentRepository.save(enrollment3);


			//Para Chloe: asociando a Chloe con Fisica y con el turnno de la tarde  de 16 a 18 los Tuesday and Thursday
			Enrollment enrollment4 = new Enrollment("active ES ESTEEEE");
			studentCourse5.addEnrollment(enrollment4);
			courseSchedule5.addEnrollment(enrollment4);

			enrollmentRepository.save(enrollment4);

			//Para Chloe: asociando a Chloe con Matematicas y con el turnno de la tarde  de 14 a 16 los Monday and Friday
			Enrollment enrollment5 = new Enrollment("active Funcionaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			studentCourse4.addEnrollment(enrollment5);
			courseSchedule2.addEnrollment(enrollment5);

			enrollmentRepository.save(enrollment5);



/////--------------------- Crear TeacherCourse y asociar los Cursos al Profesor Miguel y Gonzalo     --------------//




			// ↓↓↓↓↓↓↓  Teacher1 junto con course1, course2 y course3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //
			TeacherCourse teacherCourse1 = new TeacherCourse(now,RoleTeacher.MAIN);
			teacher1.addTeacherCourse(teacherCourse1);
			course1.addTeacherCourse(teacherCourse1);


			teacherCourseRepository.save(teacherCourse1);


			TeacherCourse teacherCourse2 = new TeacherCourse(now,RoleTeacher.ASSISTANT);
			teacher1.addTeacherCourse(teacherCourse2);
			course2.addTeacherCourse(teacherCourse2);


			teacherCourseRepository.save(teacherCourse2);



			TeacherCourse teacherCourse3 = new TeacherCourse(now,RoleTeacher.COLLABORATOR);
			teacher1.addTeacherCourse(teacherCourse3);
			course3.addTeacherCourse(teacherCourse3);


			teacherCourseRepository.save(teacherCourse3);

			// ↓↓↓↓↓↓↓  Teacher2 junto con course1, course2 y course3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			TeacherCourse teacherCourse4 = new TeacherCourse(now,RoleTeacher.ASSISTANT);
			teacher2.addTeacherCourse(teacherCourse4);
			course1.addTeacherCourse(teacherCourse4);


			teacherCourseRepository.save(teacherCourse4);


			TeacherCourse teacherCourse5 = new TeacherCourse(now,RoleTeacher.MAIN);
			teacher2.addTeacherCourse(teacherCourse5);
			course2.addTeacherCourse(teacherCourse5);


			teacherCourseRepository.save(teacherCourse5);


			TeacherCourse teacherCourse6 = new TeacherCourse(now,RoleTeacher.MAIN);
			teacher2.addTeacherCourse(teacherCourse6);
			course3.addTeacherCourse(teacherCourse6);


			teacherCourseRepository.save(teacherCourse6);


/////--------------------- Añadiendo la asistencia a un StudentCourse    y a un TeacherCourse     --------------//

			// Añadiendo la asistencia a un StudentCourse
			Attendance attendance1 = new Attendance(now, StatusAttendance.PRESENT);
			studentCourse1.addAttendance(attendance1);
			attendanceRepository.save(attendance1);
			studentCourseRepository.save(studentCourse1);

			Attendance attendance2 = new Attendance(now, StatusAttendance.ABSENT);
			studentCourse1.addAttendance(attendance2);
			attendanceRepository.save(attendance2);
			studentCourseRepository.save(studentCourse1);

			Attendance attendance3 = new Attendance(now, StatusAttendance.PRESENT);
			studentCourse1.addAttendance(attendance3);
			attendanceRepository.save(attendance3);
			studentCourseRepository.save(studentCourse1);

			Attendance attendance4 = new Attendance(now, StatusAttendance.PRESENT);
			studentCourse2.addAttendance(attendance4);
			attendanceRepository.save(attendance4);
			studentCourseRepository.save(studentCourse2);

			Attendance attendance5 = new Attendance(now, StatusAttendance.LATE);
			studentCourse2.addAttendance(attendance5);
			attendanceRepository.save(attendance5);
			studentCourseRepository.save(studentCourse2);

			Attendance attendance6 = new Attendance(now, StatusAttendance.LATE);
			studentCourse2.addAttendance(attendance6);
			attendanceRepository.save(attendance6);
			studentCourseRepository.save(studentCourse2);



		};
	}

}
