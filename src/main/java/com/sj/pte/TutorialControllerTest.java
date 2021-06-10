//package com.sj.pte;/**
// * Created by TUTEHUB on 2021/6/4 1:17 PM.
// * Copyright © 2021 TUTEHUB. All rights reserved.
// * ------------------------
// * Non-disclosure Terms
// * -------------------------
// * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
// * Developer dph agrees with above terms.
// * Technique Support: jobyme88.com
// * Contact: noreply@fengcaoculture.com
// */
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
///**
// * @description
// */
//@WebMvcTest(TutorialController.class)
//public class TutorialControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @MockBean
//    private TutorialRepository tutorialRepository;
//
//    protected MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(this.context)
//                .build();
//    }
//
//
//    @Test
//    public void testGetAllTutorials() throws Exception {
//        when(tutorialRepository.findAll())
//                .thenReturn(List.of(new Tutorial("1", "title", "des", false)));
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/tutorials"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("des"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].published").value(false));
//    }
//}
