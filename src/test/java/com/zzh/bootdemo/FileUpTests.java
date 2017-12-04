package com.zzh.bootdemo;

import com.zzh.bootdemo.exception.StorageFileNotFoundException;
import com.zzh.bootdemo.service.StorageService;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileUpApp.class)
@AutoConfigureMockMvc
@Ignore
public class FileUpTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private StorageService storageService;

    @Test
    public void shouldListAllFile() throws Exception {
        given(this.storageService.loadAll()).willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
        this.mvc.perform(get("/fileup/")).andExpect(status().isOk())
                .andExpect(model().attribute("files", Matchers.contains("http://localhost/fileup/files/first.txt", "http://localhost/fileup/files/second.txt")));
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "text.txt", "text/plain", "Love boot".getBytes());
        this.mvc.perform(fileUpload("/fileup/").file(multipartFile))
                .andExpect(status().isFound()).andExpect(header().string("Location", "/fileup/"));
        then(this.storageService).should().store(multipartFile);
    }

    @Test
    public void should404WhenMissingFile()throws Exception {
        given(this.storageService.loadAsResource("test.txt"))
                .willThrow(StorageFileNotFoundException.class);
        this.mvc.perform(get("/fileup/files/test.txt")).andExpect(status().isNotFound());
    }

}
