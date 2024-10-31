package com.SchoolApplication.UC;

import com.SchoolApplication.UC.models.*;
import com.SchoolApplication.UC.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class UcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcApplication.class, args);
	}

	@Bean
//un  bean es simplemente una clase Java normal, escrita para seguir algunas reglas importantes. Pedimos que se ejecute esto primero.
	public CommandLineRunner initData(UsuariooRepository usuariooRepository,
									  StudentRepository studentRepository,
									  TeacherRepository teacherRepository,
									  CurseRepository curseRepository,
									  ScheduleRepository scheduleRepository,
									  CurseScheduleRepository curseScheduleRepository,
									  StudentCurseRepository studentCurseRepository,
									  TeacherCurseRepository teacherCurseRepository,
									  AttendanceRepository attendanceRepository) { // Define un bean de tipo CommandLineRunner que recibe un StudentRepository como parámetro.
		// initData toma una instancia de StudentRepository como parámetro y utiliza este repositorio para guardar algunos clientes en la base de datos al iniciar la aplicación.
		//Es un singleton que se ejecutan una vez cada vez que se inicia la aplicación.

		return (args) -> {

			// Create students
			Student student1 = new Student ("Melba","Morel","M.morel@me.com","123");
			Student student2 = new Student("Chloe", "O'Brian", "c.obrian@me.com", "456");

			studentRepository.save(student1);
			studentRepository.save(student2);


			// Create teachers
			Teacher teacher1 = new Teacher("Miguel", "Hernandez", "m.hernandez@me.com", "789");
			Teacher teacher2 = new Teacher("Gonzalo", "Gonzalez", "g.gonzalez@me.com", "987");

			teacherRepository.save(teacher1);
			teacherRepository.save(teacher2);

			// Create Curses

			Curse curse1 = new Curse("Math", "Junior or 3rd year", 20);
			Curse curse2 = new Curse("physics", "Senior or 4th year", 30);
			Curse curse3 = new Curse("Chemistry", "Senior or 4th year", 10);

			curseRepository.save(curse1);
			curseRepository.save(curse2);
			curseRepository.save(curse3);

			//Create Schedule
			Schedule schedule1 = new Schedule(Shift.MORNING, Arrays.asList("Monday and Wednesday" , "Tuesday and Thursday"), Arrays.asList("8:00 a 10:00", "10:00 a 12:00"));
			Schedule schedule2 = new Schedule(Shift.AFTERNOON, Arrays.asList("Monday and Friday" , "Tuesday and Thursday"), Arrays.asList("14:00 a 16:00", "16:00 a 18:00"));
			Schedule schedule3 = new Schedule(Shift.NIGHT, Arrays.asList("Tuesday and Friday" , "Wednesday and Thursday"), Arrays.asList("20:00 a 22:00", "22:00 a 24:00"));


			scheduleRepository.save(schedule1);
			scheduleRepository.save(schedule2);
			scheduleRepository.save(schedule3);


/////--------------------- Crear CurseSchedule y asociar los Horarios al estudiante Melba    --------------//


// ↓↓↓↓↓↓↓  Curse1 junto con schedule1, schedule2 y schedule3 ↓↓↓↓↓↓↓
//          ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯         //

			CurseSchedule curseSchedule1 = new CurseSchedule("Monday and Wednesday", "8:00 a 10:00");
			curse1.addCurseSchedule(curseSchedule1);
			schedule1.addCurseSchedule(curseSchedule1);

			curseScheduleRepository.save(curseSchedule1);


			CurseSchedule curseSchedule2 = new CurseSchedule("Monday and Friday", "14:00 a 16:00");
			curse1.addCurseSchedule(curseSchedule2);
			schedule2.addCurseSchedule(curseSchedule2);

			curseScheduleRepository.save(curseSchedule2);


			CurseSchedule curseSchedule3 = new CurseSchedule("Tuesday and Friday", "20:00 a 22:00");
			curse1.addCurseSchedule(curseSchedule3);
			schedule3.addCurseSchedule(curseSchedule3);

			curseScheduleRepository.save(curseSchedule3);

// ↓↓↓↓↓↓↓  Curse2 junto con schedule1, schedule2 y schedule3 ↓↓↓↓↓↓↓
//          ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯         //
			CurseSchedule curseSchedule4 = new CurseSchedule("Monday and Thursday", "9:00 a 11:00");
			curse2.addCurseSchedule(curseSchedule4);
			schedule1.addCurseSchedule(curseSchedule4);

			curseScheduleRepository.save(curseSchedule4);

			CurseSchedule curseSchedule5 = new CurseSchedule("Wednesday and Friday", "15:00 a 17:00");
			curse2.addCurseSchedule(curseSchedule5);
			schedule2.addCurseSchedule(curseSchedule5);

			curseScheduleRepository.save(curseSchedule5);

			CurseSchedule curseSchedule6 = new CurseSchedule("Tuesday and Thursday", "21:00 a 23:00");
			curse2.addCurseSchedule(curseSchedule6);
			schedule3.addCurseSchedule(curseSchedule6);

			curseScheduleRepository.save(curseSchedule6);
//--------------------------------------------------------------------------------



/////--------------------- Crear StudentCurse y asociar los Cursos al estudiante Melba y Chloe     --------------//


			LocalDateTime now = LocalDateTime.now(); //Declaras una variable llamada now de tipo LocalDateTime
			System.out.println(now);


			// ↓↓↓↓↓↓↓  Student1 junto con curse1, curse2 y curse3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			StudentCurse studentCurse1 = new StudentCurse(now, Status.APPROVED);
			student1.addStudentCurse(studentCurse1);
			curse1.addStudentCurse(studentCurse1);

			studentCurseRepository.save(studentCurse1);


			StudentCurse studentCurse2 = new StudentCurse(now, Status.APPROVED);
			student1.addStudentCurse(studentCurse2);
			curse2.addStudentCurse(studentCurse2);

			studentCurseRepository.save(studentCurse2);


			StudentCurse studentCurse3 = new StudentCurse(now, Status.APPROVED);
			student1.addStudentCurse(studentCurse3);
			curse3.addStudentCurse(studentCurse3);

			studentCurseRepository.save(studentCurse3);


			// ↓↓↓↓↓↓↓  Student2 junto con curse1, curse2 y curse3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			StudentCurse studentCurse4 = new StudentCurse(now, Status.APPROVED);
			student2.addStudentCurse(studentCurse4);
			curse1.addStudentCurse(studentCurse4);

			studentCurseRepository.save(studentCurse4);


			StudentCurse studentCurse5 = new StudentCurse(now, Status.APPROVED);
			student2.addStudentCurse(studentCurse5);
			curse2.addStudentCurse(studentCurse5);

			studentCurseRepository.save(studentCurse5);


			StudentCurse studentCurse6 = new StudentCurse(now, Status.APPROVED);
			student2.addStudentCurse(studentCurse6);
			curse3.addStudentCurse(studentCurse6);

			studentCurseRepository.save(studentCurse6);

/////--------------------- Crear TeacherCurse y asociar los Cursos al Profesor Miguel y Gonzalo     --------------//




			// ↓↓↓↓↓↓↓  Teacher1 junto con curse1, curse2 y curse3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //
			TeacherCurse teacherCurse1 = new TeacherCurse(now,RoleTeacher.MAIN);
			teacher1.addTeacherCurse(teacherCurse1);
			curse1.addTeacherCurse(teacherCurse1);


			teacherCurseRepository.save(teacherCurse1);


			TeacherCurse teacherCurse2 = new TeacherCurse(now,RoleTeacher.ASSISTANT);
			teacher1.addTeacherCurse(teacherCurse2);
			curse2.addTeacherCurse(teacherCurse2);


			teacherCurseRepository.save(teacherCurse2);



			TeacherCurse teacherCurse3 = new TeacherCurse(now,RoleTeacher.COLLABORATOR);
			teacher1.addTeacherCurse(teacherCurse3);
			curse3.addTeacherCurse(teacherCurse3);


			teacherCurseRepository.save(teacherCurse3);

			// ↓↓↓↓↓↓↓  Teacher2 junto con curse1, curse2 y curse3 ↓↓↓↓↓↓↓
//          		 	¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯            //

			TeacherCurse teacherCurse4 = new TeacherCurse(now,RoleTeacher.MAIN);
			teacher2.addTeacherCurse(teacherCurse4);
			curse1.addTeacherCurse(teacherCurse4);


			teacherCurseRepository.save(teacherCurse4);


			TeacherCurse teacherCurse5 = new TeacherCurse(now,RoleTeacher.ASSISTANT);
			teacher2.addTeacherCurse(teacherCurse5);
			curse2.addTeacherCurse(teacherCurse5);


			teacherCurseRepository.save(teacherCurse5);


			TeacherCurse teacherCurse6 = new TeacherCurse(now,RoleTeacher.COLLABORATOR);
			teacher2.addTeacherCurse(teacherCurse6);
			curse3.addTeacherCurse(teacherCurse6);


			teacherCurseRepository.save(teacherCurse6);


/////--------------------- Añadiendo la asistencia a un StudentCurse    y a un TeacherCurse     --------------//

			// Añadiendo la asistencia a un StudentCurse
			Attendance attendance1 = new Attendance(now, StatusAttendance.PRESENT);
			studentCurse1.addAttendance(attendance1);
			attendanceRepository.save(attendance1);
			studentCurseRepository.save(studentCurse1);

			Attendance attendance2 = new Attendance(now, StatusAttendance.ABSENT);
			studentCurse1.addAttendance(attendance2);
			attendanceRepository.save(attendance2);
			studentCurseRepository.save(studentCurse1);

			Attendance attendance3 = new Attendance(now, StatusAttendance.PRESENT);
			studentCurse1.addAttendance(attendance3);
			attendanceRepository.save(attendance3);
			studentCurseRepository.save(studentCurse1);

			Attendance attendance4 = new Attendance(now, StatusAttendance.PRESENT);
			studentCurse2.addAttendance(attendance4);
			attendanceRepository.save(attendance4);
			studentCurseRepository.save(studentCurse2);

			Attendance attendance5 = new Attendance(now, StatusAttendance.LATE);
			studentCurse2.addAttendance(attendance5);
			attendanceRepository.save(attendance5);
			studentCurseRepository.save(studentCurse2);

			Attendance attendance6 = new Attendance(now, StatusAttendance.LATE);
			studentCurse2.addAttendance(attendance6);
			attendanceRepository.save(attendance6);
			studentCurseRepository.save(studentCurse2);



		};
	}

}
