package com.example.musicstoredemo.controller;

import com.example.musicstoredemo.model.access.Endpoint;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import com.example.musicstoredemo.service.MusicStoreService;
import com.example.musicstoredemo.service.UserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@WebMvcTest(MusicStoreController.class)
public class MusicStoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private MusicStoreController musicStoreController;

    @MockBean
    private MusicStoreService musicStoreServiceMock;

    @MockBean
    private UserService userServiceMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGuitarCatalogSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getGuitarCatalog(any(String.class), any(String.class)))
                .thenReturn(new ArrayList<Guitar>(Collections.singletonList(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299))));

        List<Guitar> response = musicStoreServiceMock.getGuitarCatalog("euro", "none");
        verify(musicStoreServiceMock, times(1)).getGuitarCatalog("euro", "none");
        verifyGuitarCatalogResponse(response.get(0));
    }

    @Test
    public void testGetAccessoryCatalogSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getAccessoriesCatalog(any(String.class), any(String.class)))
                .thenReturn(new ArrayList<Accessory>(Collections.singletonList(new Accessory(1, "Gibson", "strings", 12.99))));

        List<Accessory> response = musicStoreServiceMock.getAccessoriesCatalog("euro", "none");
        verify(musicStoreServiceMock, times(1)).getAccessoriesCatalog("euro", "none");
        verifyAccessoryCatalogResponse(response.get(0));
    }

    @Test
    public void testGetGuitarCatalogByBrandSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getGuitarCatalogByBrand(any(String.class), any(String.class), any(String.class)))
                .thenReturn(new ArrayList<Guitar>(Collections.singletonList(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299))));

        List<Guitar> response = musicStoreServiceMock.getGuitarCatalogByBrand("Gibson", "euro", "none");
        verify(musicStoreServiceMock, times(1)).getGuitarCatalogByBrand("Gibson", "euro", "none");
        verifyGuitarCatalogResponse(response.get(0));
    }

    @Test
    public void testGetAccessoryCatalogByBrandSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getAccessoriesCatalogByBrand(any(String.class), any(String.class), any(String.class)))
                .thenReturn(new ArrayList<Accessory>(Collections.singletonList(new Accessory(1, "Gibson", "strings", 12.99))));

        List<Accessory> response = musicStoreServiceMock.getAccessoriesCatalogByBrand("Gibson", "euro", "none");
        verify(musicStoreServiceMock, times(1)).getAccessoriesCatalogByBrand("Gibson", "euro", "none");
        verifyAccessoryCatalogResponse(response.get(0));
    }

    @Test
    public void testGetGuitarByIdSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getGuitarById(any(long.class), any(String.class)))
                .thenReturn(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299));

        Guitar response = musicStoreServiceMock.getGuitarById(1, "Gibson");
        verify(musicStoreServiceMock, times(1)).getGuitarById(1, "Gibson");
        verifyGuitarCatalogResponse(response);
    }

    @Test
    public void testGetAccessoryByIdSuccessfully() {
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        when(musicStoreServiceMock.getAccessoryById(any(long.class), any(String.class)))
                .thenReturn(new Accessory(1, "Gibson", "strings", 12.99));

        Accessory response = musicStoreServiceMock.getAccessoryById(1, "Gibson");
        verify(musicStoreServiceMock, times(1)).getAccessoryById(1, "Gibson");
        verifyAccessoryCatalogResponse(response);
    }

    //@Test
    public void testAddGuitar() throws Exception {
        String content = getContentFromFile("request/guitar.json");
        doNothing().when(userServiceMock).validateAccess(any(String.class), any(Endpoint.class));
        mockMvc.perform(post("/catalog/guitar").content(content))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).addGuitar(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299));
    }

    @Test
    public void testGetGuitarCatalogWithMissingHeader() throws Exception {
        String response = mockMvc.perform(get("/catalog/guitars"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    @Test
    public void testGetAccessoriesCatalogWithMissingHeader() throws Exception {
        String response = mockMvc.perform(get("/catalog/accessories"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    private void verifyGuitarCatalogResponse(Guitar guitar) {
        assertEquals(1, guitar.getId());
        assertEquals("Gibson", guitar.getBrand());
        assertEquals("electric", guitar.getType());
        assertEquals("Les Paul Custom", guitar.getModel());
        assertEquals(6299, guitar.getPrice());
    }

    private void verifyAccessoryCatalogResponse(Accessory accessory) {
        assertEquals(1, accessory.getId());
        assertEquals("Gibson", accessory.getBrand());
        assertEquals("strings", accessory.getType());
        assertEquals(12.99, accessory.getPrice());
    }

    private String getContentFromFile(String path) {
        try{
            InputStream stream = new ClassPathResource(path).getInputStream();
            return IOUtils.toString(stream, Charset.defaultCharset());
        } catch (IOException e){
            throw new RuntimeException("Could not read test file path");
        }
    }

}
