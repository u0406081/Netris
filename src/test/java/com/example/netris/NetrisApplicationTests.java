package com.example.netris;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class NetrisApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        ResultActions isOk = this.mockMvc.perform(get("/aggregateCamers")).andExpect(status().isOk());
        isOk.andExpect(content().string(containsString("rtsp://127.0.0.1/1")));
        isOk.andExpect(content().string(containsString("rtsp://127.0.0.1/2")));
        isOk.andExpect(content().string(containsString("rtsp://127.0.0.1/3")));
        isOk.andExpect(content().string(containsString("rtsp://127.0.0.1/20")));
    }
}
