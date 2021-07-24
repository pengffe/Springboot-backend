package com.sj.pte.modules.question.controller;/**
 * Created by TUTEHUB on 2021-06-04 16:58.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.question.bean.MNDI;
import com.sj.pte.modules.question.bean.MNRA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption 测试题库controller
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class QuestionControllerTest {

    @Autowired
    WebApplicationContext context;
    private MockMvc mockMvc;
    @Before
    public void initMockMvc() {
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void updateRS() throws Exception{

    }


    @Test
    public void testController() throws Exception{
        List<String> questionTypeList = new ArrayList<String>() {
            {
                add("asq");
//                add("fib");
//                add("hiw");
//                add("mcm");
//                add("mcs");
//                add("ra");
//                add("rfib");
//                add("rl");
//                add("rmcm");
//                add("rmcs");
//                add("ro");
//                add("rs");
//                add("rwfib");
//                add("smw");
//                add("sst");
//                add("we");
//                add("wfd");
            }
        };

        String type;
        String questionId = "";

        String saveUrl;
        String getAllUrl;
        String getOneUrl;
        String updateFrequencyUrl;
        String updateUpdateUrl;
        String deleteUrl;
        for (String item : questionTypeList
        ) {
            type = item;
            questionId = item + "-1";

            saveUrl = "/question/" + type + "/1";
            getAllUrl = "/question/" + type;
            getOneUrl = saveUrl;
            updateFrequencyUrl = "/" + type + "/" + type + "-1" + "/i/1";
            updateUpdateUrl = "/" + type + "/" + type + "-1" + "/b/true";
            deleteUrl = saveUrl;

//            testSave(saveUrl, questionId);
            testGetAll(getAllUrl, questionId);
//            testGetOne(getOneUrl,questionId);
//            testUpdateFrequency(updateFrequencyUrl);
//            testUpdateUpdated(updateUpdateUrl);
//            testDelete(deleteUrl);
        }
    }

    public void testSave(String saveUrl, String questionId) throws Exception{
        System.out.println("Test save url: " + saveUrl);
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.post(saveUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(17))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").value(questionId))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST save: " + contentAsString);
        System.out.println();
    }

    public void testGetAll(String getAllUrl, String questionId) throws Exception{
        System.out.println("Test get all url: " + getAllUrl);
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.get(getAllUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionId").value(questionId))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST get all: " + contentAsString);
        System.out.println();
    }

    public void testGetOne(String getOneUrl, String questionId) throws Exception{
        System.out.println("Test get one url: " + getOneUrl);
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.get(getOneUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").value(questionId))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST get one: " + contentAsString);
        System.out.println();
    }

    public void testUpdateFrequency(String updateFrequencyUrl) throws Exception{
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.put(updateFrequencyUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedCount").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST update frequency: " + contentAsString);
        System.out.println();
    }

    public void testUpdateUpdated(String updateUpdateUrl) throws Exception{
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.put(updateUpdateUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedCount").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST update isUpdated: " + contentAsString);
        System.out.println();
    }

    public void testDelete(String deleteUrl) throws Exception{
        System.out.println("Test delete url: " + deleteUrl);
        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.delete(deleteUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedCount").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("TEST delete: " + contentAsString);
        System.out.println();
    }
}
