package com.example.course.domain.student.controller

import com.example.course.domain.student.service.StudentService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [StudentController::class])
class StudentControllerTest {

    @MockitoBean
    lateinit var studentService: StudentService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun 학생_회원가입_성공() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "200012345",
                "password": "12345678",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().isOk)
    }

    @Test
    fun 학생_로그인_성공() {
        //given
        val request = """
            {
                "studentNumber": "200012345",
                "password": "12345678"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect { status().isOk }
    }

    @Test
    fun 회원가입_시_이름이_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": null,
                "studentNumber": "200012345",
                "password": "12345678",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_이름이_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "",
                "studentNumber": "200012345",
                "password": "12345678",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_학번이_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": null,
                "password": "12345678",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_학번이_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "",
                "password": "12345678",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_비밀번호가_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "200012345",
                "password": null,
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_비밀번호가_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "200012345",
                "password": "",
                "departmentName": "Computer Science"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_학과명이_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "200012345",
                "password": "12345678",
                "departmentName": null
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)

        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 회원가입_시_학과명이_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentName": "이름",
                "studentNumber": "200012345",
                "password": "12345678",
                "departmentName": ""
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().is4xxClientError)
    }

    @Test
    fun 로그인_시_학번이_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentNumber": null,
                "password": "12345678"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect { status().is4xxClientError }
    }

    @Test
    fun 로그인_시_학번이_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentNumber": "",
                "password": "12345678"
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect { status().is4xxClientError }
    }

    @Test
    fun 로그인_시_비밀번호가_null인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentNumber": "202512345",
                "password": null
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect { status().is4xxClientError }
    }

    @Test
    fun 로그인_시_비밀번호가_공백인_경우_예외_발생() {
        //given
        val request = """
            {
                "studentNumber": "202512345",
                "password": ""
            }
        """.trimIndent()

        // when & then
        mockMvc.perform(
            post("/students/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect { status().is4xxClientError }
    }
}