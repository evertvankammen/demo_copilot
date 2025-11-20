package nl.roxit.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestedValueController.class)
class RequestedValueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestedValueRepository repository;

    @Test
    void getRequestedValue_ShouldReturnField_WhenEntityExists() throws Exception {
        Long id = 1L;
        String value = "some-value";
        RequestedValueEntity entity = new RequestedValueEntity(value, LocalDateTime.now(), LocalDateTime.now());
        // Reflection to set ID since it's private and generated
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);

        given(repository.findById(id)).willReturn(Optional.of(entity));

        mockMvc.perform(get("/api/requested-values/{id}/requested_value", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requested_value").value(value));
    }

    @Test
    void getCreated_ShouldReturnField_WhenEntityExists() throws Exception {
        Long id = 1L;
        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 12, 0);
        RequestedValueEntity entity = new RequestedValueEntity("val", created, LocalDateTime.now());
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);

        given(repository.findById(id)).willReturn(Optional.of(entity));

        mockMvc.perform(get("/api/requested-values/{id}/created", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created").exists());
    }

    @Test
    void getLastRetrieved_ShouldReturnField_WhenEntityExists() throws Exception {
        Long id = 1L;
        LocalDateTime lastRetrieved = LocalDateTime.of(2023, 1, 2, 12, 0);
        RequestedValueEntity entity = new RequestedValueEntity("val", LocalDateTime.now(), lastRetrieved);
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);

        given(repository.findById(id)).willReturn(Optional.of(entity));

        mockMvc.perform(get("/api/requested-values/{id}/last_retrieved", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.last_retrieved").exists());
    }

    @Test
    void getId_ShouldReturnField_WhenEntityExists() throws Exception {
        Long id = 1L;
        RequestedValueEntity entity = new RequestedValueEntity("val", LocalDateTime.now(), LocalDateTime.now());
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);

        given(repository.findById(id)).willReturn(Optional.of(entity));

        mockMvc.perform(get("/api/requested-values/{id}/id", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getField_ShouldReturn404_WhenEntityDoesNotExist() throws Exception {
        Long id = 99L;
        given(repository.findById(id)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/requested-values/{id}/requested_value", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Entity not found"));
    }

    @Test
    void getField_ShouldReturn400_WhenFieldIsInvalid() throws Exception {
        Long id = 1L;
        RequestedValueEntity entity = new RequestedValueEntity("val", LocalDateTime.now(), LocalDateTime.now());
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, id);

        given(repository.findById(id)).willReturn(Optional.of(entity));

        mockMvc.perform(get("/api/requested-values/{id}/invalid_field", id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid field requested"));
    }

    @Test
    void create_ShouldReturnId_WhenPayloadValid() throws Exception {
        RequestedValueEntity entity = new RequestedValueEntity("new-val", LocalDateTime.now(), LocalDateTime.now());
        java.lang.reflect.Field idField = RequestedValueEntity.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, 10L);

        when(repository.save(any(RequestedValueEntity.class))).thenReturn(entity);

        mockMvc.perform(post("/api/requested-values")
                .contentType("application/json")
                .content("{\n  \"requested_value\": \"new-val\"\n}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    void create_ShouldReturn400_WhenMissingValue() throws Exception {
        mockMvc.perform(post("/api/requested-values")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("requested_value is required"));
    }
}
