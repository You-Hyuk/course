package com.example.course.domain.student.service

import com.example.course.domain.student.dao.DepartmentRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.PostStudentRequest
import com.example.course.domain.student.entity.Department
import com.example.course.domain.student.entity.Student
import com.example.course.domain.student.exception.DepartmentNotFoundException
import com.example.course.domain.student.exception.DuplicateStudentException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StudentServiceTest {

    @Mock
    lateinit var studentRepository: StudentRepository

    @Mock
    lateinit var departmentRepository: DepartmentRepository

    @InjectMocks
    lateinit var studentService: StudentService

    @Test
    fun `정상적인 학생 회원가입 성공`() {
        // given
        val request = PostStudentRequest(
            studentName = "홍길동",
            studentNumber = "200012345",
            password = "password123",
            departmentName = "Computer Science"
        )

        val department = Department(id = 1L, name = "Computer Science")

        given(departmentRepository.findDepartmentByName("Computer Science"))
            .willReturn(department)

        // when
        studentService.generateNewStudent(request)

        // then
        verify(studentRepository).save(any(Student::class.java))
    }

    @Test
    fun 이미_존재하는_학번으로_회원가입_하는_경우_예외발생() {
        // given
        val request = PostStudentRequest(
            studentName = "홍길동",
            studentNumber = "200012345",
            password = "password123",
            departmentName = "Computer Science"
        )
        val department = Department(id = 1L, name = "Computer Science")

        given(departmentRepository.findDepartmentByName("Computer Science"))
            .willReturn(department)

        given(studentRepository.existsByNumber("200012345"))
            .willReturn(true)

        // when & then
        assertThrows<DuplicateStudentException> {
            studentService.generateNewStudent(request)
        }
        verify(studentRepository, never()).save(any(Student::class.java))
    }


    @Test
    fun 존재하지_않는_학과를_입력한_경우_예외발생() {
        // given
        val request = PostStudentRequest(
            studentName = "홍길동",
            studentNumber = "200012345",
            password = "password123",
            departmentName = "department"
        )

        given(departmentRepository.findDepartmentByName("department"))
            .willReturn(null)

        // when & then
        assertThrows<DepartmentNotFoundException> {
            studentService.generateNewStudent(request)
        }

        verify(studentRepository, never()).save(any(Student::class.java))
    }
}