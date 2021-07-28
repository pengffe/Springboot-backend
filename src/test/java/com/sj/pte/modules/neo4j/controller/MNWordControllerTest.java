package com.sj.pte.modules.neo4j.controller;/**
 * Created by TUTEHUB on 2021-07-28 12:20.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.neo4j.entity.MNWord;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

/**
 * @descrption
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MNWordControllerTest {

    @Autowired
    WebApplicationContext context;
    private MockMvc mockMvc;
    @Before
    public void initMockMvc() {
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void saveWordTest() throws Exception{
        String wordTitle = "persevere";
        String saveApi = "/dic/" + wordTitle;

        String contentAsString = this.mockMvc
                .perform(MockMvcRequestBuilders.post(saveApi))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(17))
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void saveVocabularyTest() throws Exception{
        String wordTitle = "persist";
        String saveApi = "/dic/";


        MNWord word = new MNWord(1L, "persist", "persist", "坚持；固执", new ArrayList<>(), new HashSet<>());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestString = objectMapper.writeValueAsString(word);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post(saveApi)
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

    }

    @Test
    public void createRelationTest() throws Exception{
        List<String> startNodes = new ArrayList<>();
        startNodes.add("persevere");
        startNodes.add("per");
        String end = "perseveringly";

        for (String node: startNodes
             ) {
            String api = "/dic/" + node + "/" + end;
            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post(api));
            if (node.equals("persist")){
                perform.andExpect(MockMvcResultMatchers.status().isOk());
            }
            else {
                perform.andExpect(MockMvcResultMatchers.status().isNotFound());
            }
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void getWordTest() throws Exception {
        List<String> startNodes = new ArrayList<>();
        startNodes.add("persist");
        startNodes.add("origin");
        startNodes.add("friend");
        startNodes.add("FF");

        for (String node: startNodes
        ) {
            String api = "/dic/" + node;

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get(api));
            if (!node.equals("FF")){
                perform.andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(node));
            }
            else {
                perform.andExpect(MockMvcResultMatchers.status().isNotFound());
            }
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void getParentTest() throws Exception {
        List<String> startNodes = new ArrayList<>();
        startNodes.add("persistence");
        startNodes.add("original");
        startNodes.add("FF");


        for (String node: startNodes
        ) {
            String api = "/dic/parent/" + node;

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get(api));
            if (!node.equals("FF")){
                perform.andExpect(MockMvcResultMatchers.status().isOk());
            }
            else {
                perform.andExpect(MockMvcResultMatchers.status().isNotFound());
            }
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void getParentsTest() throws Exception {
        List<String> startNodes = new ArrayList<>();
        startNodes.add("perseveringly");
        startNodes.add("originally");
        startNodes.add("FF");


        for (String node: startNodes
        ) {
            String api = "/dic/parent/" + node;

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get(api));
            if (!node.equals("FF")){
                perform.andExpect(MockMvcResultMatchers.status().isOk());
            }
            else {
                perform.andExpect(MockMvcResultMatchers.status().isNotFound());
            }
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void getChildrenTest() throws Exception {
        List<String> startNodes = new ArrayList<>();
        startNodes.add("persist");
        startNodes.add("origin");
        startNodes.add("friend");
        startNodes.add("FF");


        for (String node: startNodes
        ) {
            String api = "/dic/children/" + node;

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get(api));
            if (!node.equals("FF")){
                perform.andExpect(MockMvcResultMatchers.status().isOk());
            }
            else {
                perform.andExpect(MockMvcResultMatchers.status().isNotFound());
            }
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void deleteRelationTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("FF", "friend");
        map.put("friend", "origin");
        map.put("origin", "persist");
        map.put("persist", "friend");

        Set<String> strings = map.keySet();
        for (String key: strings
             ) {
            System.out.println(key);
            String api = "/dic/" + key + "/" + map.get(key);

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.delete(api));
            perform.andExpect(MockMvcResultMatchers.status().isOk());
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void deleteNodeAndRelationTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("FF", "friend");
        map.put("OO", "origin");
        map.put("PP", "persist");

        Set<String> strings = map.keySet();
        for (String key: strings
        ) {
            System.out.println(key);
            String api = "/dic/node/" + map.get(key) + "/" + key;

            ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.delete(api));
            perform.andExpect(MockMvcResultMatchers.status().isOk());
            System.out.println(perform.andReturn().getResponse().getContentAsString());
        }
    }

    @Test
    public void deleteNodeTest(){

    }
}
