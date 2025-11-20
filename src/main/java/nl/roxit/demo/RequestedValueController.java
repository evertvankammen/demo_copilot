package nl.roxit.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/requested-values")
public class RequestedValueController {

    private final RequestedValueRepository repository;

    public RequestedValueController(RequestedValueRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}/{field}")
    public ResponseEntity<Map<String, Object>> getField(@PathVariable Long id, @PathVariable String field) {
        return repository.findById(id)
                .map(entity -> {
                    Object value;
                    switch (field) {
                        case "id":
                            value = entity.getId();
                            break;
                        case "requested_value":
                            value = entity.getRequestedValue();
                            break;
                        case "created":
                            value = entity.getCreated();
                            break;
                        case "last_retrieved":
                            value = entity.getLastRetrieved();
                            break;
                        default:
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(Collections.singletonMap("error", (Object) "Invalid field requested"));
                    }
                    return ResponseEntity.ok(Collections.singletonMap(field, value));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Entity not found")));
    }
}
