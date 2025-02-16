package com.reactive.liquibase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("student_courses")
public class StudentCourse {
    @Id
    private Long id;
    private Long studentId;
    private Long courseId;
}
