package com.sarangchurch.follower.docs;

import com.sarangchurch.follower.HelloController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloController.class)
class HelloControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("문서화 Hello World")
    @Test
    @WithMockUser(roles = "ADMIN")
    void hello() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andDo(print())
                .andDo(document("hello"));
    }
}
