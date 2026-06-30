package com.saadoun.e_learning.aspect;

import com.saadoun.e_learning.dto.StudentDto;
import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.model.Enrollment;
import com.saadoun.e_learning.model.Log;
import com.saadoun.e_learning.model.enums.ActionType;
import com.saadoun.e_learning.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLoggingAspect.class);

    private final LogRepository logRepository;

    @AfterReturning(
            pointcut = "execution(* com.saadoun.e_learning.service.StudentService.createStudent(..))",
            returning = "student"
    )
    public void logStudentCreate(StudentDto student) {
        saveLog(student.getId(), "Student", student.getName());
    }

    @AfterReturning(
            pointcut = "execution(* com.saadoun.e_learning.service.CourseService.save(..))",
            returning = "course"
    )
    public void logCourseCreate(Course course) {
        saveLog(course.getId(), "Course", course.getTitle());
    }

    @AfterReturning(
            pointcut = "execution(* com.saadoun.e_learning.service.StudentService.enroll(..))",
            returning = "enrollment"
    )
    public void logEnrollmentCreate(Enrollment enrollment) {
        Long studentId = enrollment.getStudent() != null ? enrollment.getStudent().getId() : null;
        Long courseId = enrollment.getCourse() != null ? enrollment.getCourse().getId() : null;
        String details = "studentId=" + studentId + ", courseId=" + courseId;
        saveLog(null, "Enrollment", details);
    }

    private void saveLog(Long modelId, String entityName, String details) {
        Log auditLog = Log.builder()
                .model_id(modelId)
                .name(entityName + " created: " + details)
                .type(ActionType.CREATE)
                .build();

        logRepository.save(auditLog);
        LOGGER.info(auditLog.getName());
    }
}