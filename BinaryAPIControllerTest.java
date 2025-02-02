package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }
	@Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }
    // New test case 1: Addition with an empty operand
    @Test
    public void addWithEmptyOperand() throws Exception {
        // Simulates the addition where one operand is empty
        this.mvc.perform(get("/add").param("operand1", "111").param("operand2", ""))
                .andExpect(status().isOk()) // Verifies that the API responds successfully
                .andExpect(content().string("111")); // Checks that the result is equal to operand1 (operand2 is treated as 0)
    }

    // New test case 2: Addition with single bit operands
    @Test
    public void addWithSingleBitOperands() throws Exception {
        // Test addition where both operands are single-bit binary numbers
        this.mvc.perform(get("/add").param("operand1", "1").param("operand2", "1"))
                .andExpect(status().isOk()) // Ensures the API responds successfully
                .andExpect(content().string("10")); // Verifies correct addition result
    }

    // New test case 3: Large binary numbers addition in JSON API
    @Test
    public void addLargeBinaryJSON() throws Exception {
        // Tests the JSON addition endpoint with large binary inputs
        this.mvc.perform(get("/add_json").param("operand1", "1010101010101010").param("operand2", "1111111111111111"))
                .andExpect(status().isOk()) // Ensures the API responds successfully
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1010101010101010L)) // Confirms operand1 is correctly returned
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1111111111111111L)) // Confirms operand2 is correctly returned
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11010101010101001L)) // Confirms the addition result is accurate
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add")); // Verifies the operation type
    }
    @Test
    public void orOperation() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "1010").param("operand2", "1100"))
                .andExpect(status().isOk())
                .andExpect(content().string("1110")); // Verifies correct OR result
    }

    @Test
    public void orOperationJSON() throws Exception {
        this.mvc.perform(get("/or_json").param("operand1", "1010").param("operand2", "1100"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1110))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or")); // Verifies operator is OR
    }
    @Test
    public void andOperation() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1010").param("operand2", "1100"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000")); // Verifies correct AND result
    }

    @Test
    public void andOperationJSON() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "1010").param("operand2", "1100"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and")); // Verifies operator is AND
    }

    @Test
    public void multiplyOperation() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "101").param("operand2", "11"))
                .andExpect(status().isOk())
                .andExpect(content().string("1111")); // Verifies correct multiplication result
    }

    @Test
    public void multiplyOperationJSON() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "101").param("operand2", "11"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply")); // Verifies operator is multiply
    }

}