package nl.roxit.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RequestedValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestedValue;
    private LocalDateTime created;
    private LocalDateTime lastRetrieved;

    public RequestedValueEntity() {}

    public RequestedValueEntity(String requestedValue, LocalDateTime created, LocalDateTime lastRetrieved) {
        this.requestedValue = requestedValue;
        this.created = created;
        this.lastRetrieved = lastRetrieved;
    }

    public Long getId() { return id; }
    public String getRequestedValue() { return requestedValue; }
    public void setRequestedValue(String requestedValue) { this.requestedValue = requestedValue; }
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    public LocalDateTime getLastRetrieved() { return lastRetrieved; }
    public void setLastRetrieved(LocalDateTime lastRetrieved) { this.lastRetrieved = lastRetrieved; }
}
