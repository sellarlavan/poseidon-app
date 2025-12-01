package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    void testLoginFailure() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void testUnauthorizedAccessRedirectsToLogin() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testUnauthorizedAccesAdminPages() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAuthorizedAdminPages() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());
    }

}
