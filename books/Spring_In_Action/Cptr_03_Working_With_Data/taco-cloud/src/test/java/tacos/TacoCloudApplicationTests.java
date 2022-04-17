package tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class TacoCloudApplicationTests {
	
	@Autowired
	private MockMvc mockMvc; // injects MockMvc
	
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/"))	// performs GET /
			.andExpect(status().isOk()) 	// expects HTTP 200
			.andExpect(view().name("home")) 	// expects home view
			.andExpect(content().string(containsString("Welcome to ..."))); 	// expects Welcome to ... 
	}

	@Test
	void contextLoads() {
	}

}
