package com.example.musicstoredemo.controller;

import com.example.musicstoredemo.model.access.Endpoint;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import com.example.musicstoredemo.service.MusicStoreService;
import com.example.musicstoredemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

    ObjectMapper mapper;
    ObjectWriter ow;

    @BeforeAll
    public void setUp() {
        mapper = new ObjectMapper();
        ow = mapper.writer().withDefaultPrettyPrinter();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGuitarCatalogSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/guitars")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getGuitarCatalog("euro", "none");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_GUITAR_CATALOGUE);
    }

    @Test
    public void testGetAccessoryCatalogSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/accessories")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getAccessoriesCatalog("euro", "none");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_ACCESSORIES_CATALOGUE);
    }

    @Test
    public void testGetGuitarCatalogByBrandSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/guitars/gibson")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getGuitarCatalogByBrand("gibson", "euro", "none");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_GUITAR_CATALOGUE_BY_BRAND);
    }

    @Test
    public void testGetAccessoryCatalogByBrandSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/accessories/gibson")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getAccessoriesCatalogByBrand("gibson", "euro", "none");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_ACCESSORIES_CATALOGUE_BY_BRAND);
    }

    @Test
    public void testGetGuitarByIdSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/guitar")
                .param("id", "1")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getGuitarById(1, "euro");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_GUITAR_BY_ID);
    }

    @Test
    public void testGetAccessoryByIdSuccessfully() throws Exception {
        mockMvc.perform(get("/catalog/accessory")
                .param("id", "1")
                .header("access-token", "clientaccesstoken"))
                .andExpect(status().isOk());

        verify(musicStoreServiceMock, times(1)).getAccessoryById(1, "euro");
        verify(userServiceMock, times(1)).validateAccess("clientaccesstoken", Endpoint.GET_ACCESSORY_BY_ID);
    }

    @Test
    public void testRevertCatalog() throws Exception {
        mockMvc.perform(post("/catalog/revert")
                .header("access-token", "adminaccesstoken"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).validateAccess("adminaccesstoken", Endpoint.POST_REVERT_CATALOGUE);
        verify(musicStoreServiceMock, times(1)).revertCatalog();
    }

    @Test
    public void testAddGuitar() throws Exception {
        mockMvc.perform(post("/catalog/guitar").contentType(APPLICATION_JSON_VALUE)
                .content(ow.writeValueAsString(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299)))
                .header("access-token", "adminaccesstoken"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).validateAccess("adminaccesstoken", Endpoint.POST_ADD_GUITAR);
        verify(musicStoreServiceMock, times(1)).addGuitar(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299));
    }

    @Test
    public void testAddAccessory() throws Exception {
        mockMvc.perform(post("/catalog/accessory").contentType(APPLICATION_JSON_VALUE)
                .content(ow.writeValueAsString(new Accessory(1, "Gibson", "strings", 12.99)))
                .header("access-token", "adminaccesstoken"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).validateAccess("adminaccesstoken", Endpoint.POST_ADD_ACCESSORY);
        verify(musicStoreServiceMock, times(1)).addAccessory(new Accessory(1, "Gibson", "strings", 12.99));
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

    @Test
    public void testGetGuitarByIdWithMissingHeader() throws Exception {
        String response = mockMvc.perform(get("/catalog/guitar")
                .param("id", "1"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    @Test
    public void testGetAccessoryByIdWithMissingHeader() throws Exception {
        String response = mockMvc.perform(get("/catalog/accessory")
                .param("id", "1"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    @Test
    public void testRevertCatalogWithMissingHeader() throws Exception {
        String response = mockMvc.perform(post("/catalog/revert"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    @Test
    public void testAddGuitarWithMissingHeader() throws Exception {
        String response = mockMvc.perform(post("/catalog/guitar").contentType(APPLICATION_JSON_VALUE)
                .content(ow.writeValueAsString(new Guitar(1, "Gibson", "electric", "Les Paul Custom", 6299))))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertTrue(response.contains("Required request header 'access-token' for method parameter type String is not present"));
        verifyNoInteractions(musicStoreServiceMock);
        verifyNoInteractions(userServiceMock);
    }

    @Test
    public void testAddAccessoryWithMissingHeader() throws Exception {
        String response = mockMvc.perform(post("/catalog/accessory").contentType(APPLICATION_JSON_VALUE)
                .content(ow.writeValueAsString(new Accessory(1, "Gibson", "strings", 12.99))))
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

}
