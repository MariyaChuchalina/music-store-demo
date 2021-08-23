package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.model.catalog.Caretaker;
import com.example.musicstoredemo.model.catalog.Catalog;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import com.example.musicstoredemo.model.sort.BrandSorter;
import com.example.musicstoredemo.model.sort.PriceAscendingSorter;
import com.example.musicstoredemo.model.sort.PriceDescendingSorter;
import com.example.musicstoredemo.model.sort.SortContext;
import com.example.musicstoredemo.model.sort.TypeSorter;
import com.example.musicstoredemo.repository.AccessoryRepository;
import com.example.musicstoredemo.repository.GuitarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class MusicStoreServiceTest {

    @InjectMocks
    private MusicStoreService musicStoreService;

    @Mock
    private GuitarRepository guitarRepositoryMock;

    @Mock
    private AccessoryRepository accessoryRepositoryMock;

    private Catalog catalog;
    private Caretaker caretaker;

    private List<Guitar> updatedGuitarCatalog;
    private List<Accessory> updatedAccessoriesCatalog;

    private SortContext sortContext;

    @BeforeEach
    public void setUp() {
        catalog = Mockito.spy(Catalog.getInstance());
        catalog.setGuitarList(populateGuitarCatalog());
        catalog.setAccessoryList(populateAccessoriesCatalog());
        caretaker = Mockito.spy(new Caretaker());
        updatedGuitarCatalog = Mockito.spy(new ArrayList<Guitar>());
        updatedAccessoriesCatalog = Mockito.spy(new ArrayList<Accessory>());
        sortContext = new SortContext();

        Whitebox.setInternalState(musicStoreService, "catalog", Catalog.getInstance());
        Whitebox.setInternalState(musicStoreService, "updatedGuitarCatalog", updatedGuitarCatalog);
        Whitebox.setInternalState(musicStoreService, "updatedAccessoriesCatalog", updatedAccessoriesCatalog);
        Whitebox.setInternalState(caretaker, "catalogHistory", new Stack<>());
        Whitebox.setInternalState(musicStoreService, "caretaker", caretaker);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGuitarCatalogSortByAscendingPrice() {
        sortContext.setSorter(new PriceAscendingSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("euro", "ascending");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals(250.00, guitarList.get(0).getPrice());
        assertEquals(2479.00, guitarList.get(1).getPrice());
        assertEquals(6299, guitarList.get(2).getPrice());
    }

    @Test
    public void testGetAccessoriesCatalogSortByAscendingPrice() {
        sortContext.setSorter(new PriceAscendingSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("euro", "ascending");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals(7.49, accessoryList.get(0).getPrice());
        assertEquals(12.99, accessoryList.get(1).getPrice());
        assertEquals(24.99, accessoryList.get(2).getPrice());
    }

    @Test
    public void testGetGuitarCatalogSortByDescendingPrice() {
        sortContext.setSorter(new PriceDescendingSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("euro", "descending");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals(6299, guitarList.get(0).getPrice());
        assertEquals(2479.00, guitarList.get(1).getPrice());
        assertEquals(250.00, guitarList.get(2).getPrice());
    }

    @Test
    public void testGetAccessoriesCatalogSortByDescendingPrice() {
        sortContext.setSorter(new PriceDescendingSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("euro", "descending");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals(24.99, accessoryList.get(0).getPrice());
        assertEquals(12.99, accessoryList.get(1).getPrice());
        assertEquals(7.49, accessoryList.get(2).getPrice());
    }

    @Test
    public void testGetGuitarCatalogSortByBrand() {
        sortContext.setSorter(new BrandSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("euro", "brand");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals("Fender", guitarList.get(0).getBrand());
        assertEquals("Gibson", guitarList.get(1).getBrand());
        assertEquals("Gibson", guitarList.get(2).getBrand());
    }

    @Test
    public void testGetAccessoriesCatalogSortByBrand() {
        sortContext.setSorter(new BrandSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("euro", "brand");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals("Dunlop", accessoryList.get(0).getBrand());
        assertEquals("Gibson", accessoryList.get(1).getBrand());
        assertEquals("Martin", accessoryList.get(2).getBrand());
    }

    @Test
    public void testGetGuitarCatalogSortByType() {
        sortContext.setSorter(new TypeSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("euro", "type");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals("acoustic", guitarList.get(0).getType());
        assertEquals("electric", guitarList.get(1).getType());
        assertEquals("electric-acoustic", guitarList.get(2).getType());
    }

    @Test
    public void testGetAccessoriesCatalogSortByType() {
        sortContext.setSorter(new TypeSorter());
        Whitebox.setInternalState(musicStoreService, "sortContext", sortContext);
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("euro", "type");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals("strap", accessoryList.get(0).getType());
        assertEquals("strings", accessoryList.get(1).getType());
        assertEquals("strings", accessoryList.get(2).getType());
    }

    @Test
    public void testGetGuitarCatalogInDollars() {
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("dollar", "none");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals(2900.43, guitarList.get(0).getPrice());
        assertEquals(7369.83, guitarList.get(1).getPrice());
        assertEquals(292.5, guitarList.get(2).getPrice());
    }

    @Test
    public void testGetAccessoriesCatalogInDollars() {
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("dollar", "none");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals(15.2, accessoryList.get(0).getPrice());
        assertEquals(29.24, accessoryList.get(1).getPrice());
        assertEquals(8.76, accessoryList.get(2).getPrice());
    }

    @Test
    public void testGetGuitarCatalogInPounds() {
        List<Guitar> guitarList = musicStoreService.getGuitarCatalog("pound", "none");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        assertEquals(2131.94, guitarList.get(0).getPrice());
        assertEquals(5417.14, guitarList.get(1).getPrice());
        assertEquals(215.0, guitarList.get(2).getPrice());
    }

    @Test
    public void testGetAccessoriesCatalogInPounds() {
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalog("pound", "none");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        assertEquals(11.17, accessoryList.get(0).getPrice());
        assertEquals(21.49, accessoryList.get(1).getPrice());
        assertEquals(6.44, accessoryList.get(2).getPrice());
    }

    @Test
    public void testGetGuitarCatalogByBrand() {
        List<Guitar> guitarList = musicStoreService.getGuitarCatalogByBrand("gibson", "euro", "none");

        verify(catalog).getGuitarList();
        verify(updatedGuitarCatalog, times(3)).add(any(Guitar.class));
        guitarList.forEach(g -> {
            assertEquals("Gibson", g.getBrand());
        });
    }

    @Test
    public void testGetAccessoriesCatalogByBrand() {
        List<Accessory> accessoryList = musicStoreService.getAccessoriesCatalogByBrand("gibson", "euro", "none");

        verify(catalog).getAccessoryList();
        verify(updatedAccessoriesCatalog, times(3)).add(any(Accessory.class));
        accessoryList.forEach(a -> {
            assertEquals("Gibson", a.getBrand());
        });
    }

    @Test
    public void testGetGuitarCatalogWithInvalidBrand() {
        try {
            musicStoreService.getGuitarCatalogByBrand("aaa", "euro", "none");
        } catch (ItemNotFoundException infe) {
            assertEquals("Invalid brand requested", infe.getMessage());
        }
    }

    @Test
    public void testGetAccessoriesCatalogWithInvalidBrand() {
        try {
            musicStoreService.getAccessoriesCatalogByBrand("aaa", "euro", "none");
        } catch (ItemNotFoundException infe) {
            assertEquals("Invalid brand requested", infe.getMessage());
        }
    }

    @Test
    public void testGetGuitarInDollars() {
        Guitar guitar = musicStoreService.getGuitarById(1, "dollar");

        verify(catalog).getGuitarList();
        assertEquals(2900.43, guitar.getPrice());
    }

    @Test
    public void testGetGuitarInPounds() {
        Guitar guitar = musicStoreService.getGuitarById(1, "pound");

        verify(catalog).getGuitarList();
        assertEquals(2131.94, guitar.getPrice());
    }

    @Test
    public void testGetAccessoryInDollars() {
        Accessory accessory = musicStoreService.getAccessoryById(1, "dollar");

        verify(catalog).getAccessoryList();
        assertEquals(15.2, accessory.getPrice());
    }

    @Test
    public void testGetAccessoryInPounds() {
        Accessory accessory = musicStoreService.getAccessoryById(1, "pound");

        verify(catalog).getAccessoryList();
        assertEquals(11.17, accessory.getPrice());
    }

    @Test
    public void testAddGuitar() {
        when(guitarRepositoryMock.findAll()).thenReturn(populateGuitarCatalog());

        musicStoreService.addGuitar(new Guitar(4, "Gibson", "acoustic", "J-45", 2420.00));

        verify(guitarRepositoryMock, times(2)).findAll();
        verify(guitarRepositoryMock, times(1)).save(any(Guitar.class));
        verify(caretaker).save(any(Catalog.class));
    }

    @Test
    public void testAddAccessory() {
        when(accessoryRepositoryMock.findAll()).thenReturn(populateAccessoriesCatalog());

        musicStoreService.addAccessory(new Accessory(4, "Gibson", "strings", 18.99));

        verify(accessoryRepositoryMock, times(2)).findAll();
        verify(accessoryRepositoryMock, times(1)).save(any(Accessory.class));
        verify(caretaker).save(any(Catalog.class));
    }

    @Test
    public void testAddExistingGuitar() {
        when(guitarRepositoryMock.findAll()).thenReturn(populateGuitarCatalog());

        musicStoreService.addGuitar(new Guitar(1, "Gibson", "acoustic", "J-45", 2479.00));

        verify(guitarRepositoryMock, times(2)).findAll();
        verify(guitarRepositoryMock, never()).save(any(Guitar.class));
    }

    @Test
    public void testAddExistingAccessory() {
        when(accessoryRepositoryMock.findAll()).thenReturn(populateAccessoriesCatalog());

        musicStoreService.addAccessory(new Accessory(1, "Gibson", "strings", 12.99));

        verify(accessoryRepositoryMock, times(2)).findAll();
        verify(accessoryRepositoryMock, never()).save(any(Accessory.class));
    }

    @Test
    public void testGetGuitar() {
        musicStoreService.getGuitarById(1, "euro");

        verify(catalog).getGuitarList();
    }

    @Test
    public void testGetAccessory() {
        musicStoreService.getAccessoryById(1, "euro");

        verify(catalog).getAccessoryList();
    }

    @Test
    public void testGetNonExistingGuitar() {
        try {
            musicStoreService.getGuitarById(999, "euro");
        } catch (ItemNotFoundException infe) {
            assertEquals("No guitar with id: 999 found", infe.getMessage());
            verify(catalog).getGuitarList();
        }
    }

    @Test
    public void testGetNonExistingAccessory() {
        try {
            musicStoreService.getAccessoryById(999, "euro");
        } catch (ItemNotFoundException infe) {
            assertEquals("No accessory with id: 999 found", infe.getMessage());
            verify(catalog).getAccessoryList();
        }
    }

    @Test
    public void testRevertCatalog() {
        musicStoreService.revertCatalog();

        verify(caretaker).revert(any(Catalog.class));
    }

    private List<Guitar> populateGuitarCatalog() {
        List<Guitar> guitarList = new ArrayList<Guitar>();
        guitarList.add(new Guitar(1, "Gibson", "acoustic", "J-45", 2479.00));
        guitarList.add(new Guitar(2, "Gibson", "electric", "Les Paul Custom", 6299));
        guitarList.add(new Guitar(3, "Fender", "electric-acoustic", "T-Bucket", 250.00));
        return guitarList;
    }

    private List<Accessory> populateAccessoriesCatalog() {
        List<Accessory> accessoryList = new ArrayList<Accessory>();
        accessoryList.add(new Accessory(1, "Gibson", "strings", 12.99));
        accessoryList.add(new Accessory(2, "Dunlop", "strap", 24.99));
        accessoryList.add(new Accessory(3, "Martin", "strings", 7.49));
        return accessoryList;
    }
}
