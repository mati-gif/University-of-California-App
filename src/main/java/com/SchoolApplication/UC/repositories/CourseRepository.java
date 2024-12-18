package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//    boolean existsById(Long id);
//    Course findCourseById(Long id);

    int countByNameSubjectAndCourseIdAndScheduleId(String nameSubject);
}
