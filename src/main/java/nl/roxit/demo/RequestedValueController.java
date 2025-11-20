package nl.roxit.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/requested-values")
public class RequestedValueController {

    private final RequestedValueRepository repository;

    public RequestedValueController(RequestedValueRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> body) {
        Object nameObj = body.get("name");
        Object raw = body.get("requested_value");
        if (raw == null || raw.toString().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", (Object) "requested_value is required"));
        }
        String name = nameObj != null ? nameObj.toString() : "default";
        String requestedValue = raw.toString();
        LocalDateTime now = LocalDateTime.now();

        // Find max version for the name
        List<RequestedValueEntity> existing = repository.findByNameOrderByVersionDesc(name);
        int newVersion = existing.isEmpty() ? 1 : existing.get(0).getVersion() + 1;

        RequestedValueEntity entity = new RequestedValueEntity(name, requestedValue, newVersion, true, now, now);
        RequestedValueEntity saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", (Object) saved.getId()));
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
                        case "name":
                            value = entity.getName();
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
                        case "version":
                            value = entity.getVersion();
                            break;
                        case "active":
                            value = entity.isActive();
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

    @GetMapping("/active/{name}")
    public ResponseEntity<Map<String, Object>> getActive(@PathVariable String name) {
        return repository.findByNameAndActiveTrue(name)
                .map(entity -> ResponseEntity.ok(Collections.singletonMap("requested_value", (Object) entity.getRequestedValue())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "No active version found")));
    }

    @PutMapping("/{name}")
    @Transactional
    public ResponseEntity<Map<String, Object>> update(@PathVariable String name, @RequestBody Map<String, Object> body) {
        Object raw = body.get("requested_value");
        if (raw == null || raw.toString().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", (Object) "requested_value is required"));
        }
        String requestedValue = raw.toString();
        LocalDateTime now = LocalDateTime.now();

        // Deactivate current active
        repository.findByNameAndActiveTrue(name).ifPresent(entity -> {
            entity.setActive(false);
            repository.save(entity);
        });

        // Find max version
        List<RequestedValueEntity> existing = repository.findByNameOrderByVersionDesc(name);
        int newVersion = existing.isEmpty() ? 1 : existing.get(0).getVersion() + 1;

        RequestedValueEntity newEntity = new RequestedValueEntity(name, requestedValue, newVersion, true, now, now);
        RequestedValueEntity saved = repository.save(newEntity);
        return ResponseEntity.ok(Collections.singletonMap("id", (Object) saved.getId()));
    }

    @GetMapping("/{name}/versions")
    public ResponseEntity<Page<Map<String, Object>>> getVersions(@PathVariable String name,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestedValueEntity> entities = repository.findByNameOrderByVersionDesc(name, pageable);
        Page<Map<String, Object>> result = entities.map(entity -> Map.of(
                "id", (Object) entity.getId(),
                "version", (Object) entity.getVersion(),
                "requested_value", (Object) entity.getRequestedValue(),
                "active", (Object) entity.isActive(),
                "created", (Object) entity.getCreated(),
                "last_retrieved", (Object) entity.getLastRetrieved()
        ));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{name}/versions/{version}/activate")
    @Transactional
    public ResponseEntity<Map<String, Object>> activateVersion(@PathVariable String name, @PathVariable int version) {
        Optional<RequestedValueEntity> target = repository.findByNameAndVersion(name, version);
        if (target.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Version not found"));
        }

        // Deactivate current active
        repository.findByNameAndActiveTrue(name).ifPresent(entity -> {
            entity.setActive(false);
            repository.save(entity);
        });

        // Activate target
        RequestedValueEntity entity = target.get();
        entity.setActive(true);
        entity.setLastRetrieved(LocalDateTime.now());
        repository.save(entity);

        return ResponseEntity.ok(Collections.singletonMap("message", "Version activated"));
    }
}
