package nl.roxit.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(columnList = "name"), @Index(columnList = "name, active")})
public class RequestedValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String requestedValue;
    private LocalDateTime created;
    private LocalDateTime lastRetrieved;

    @Column(nullable = false)
    private int version;

    @Column(nullable = false)
    private boolean active;

    public RequestedValueEntity() {}

    public RequestedValueEntity(String requestedValue, LocalDateTime created, LocalDateTime lastRetrieved) {
        this.requestedValue = requestedValue;
        this.created = created;
        this.lastRetrieved = lastRetrieved;
    }

    public RequestedValueEntity(String name, String requestedValue, int version, boolean active, LocalDateTime created, LocalDateTime lastRetrieved) {
        this.name = name;
        this.requestedValue = requestedValue;
        this.version = version;
        this.active = active;
        this.created = created;
        this.lastRetrieved = lastRetrieved;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRequestedValue() { return requestedValue; }
    public void setRequestedValue(String requestedValue) { this.requestedValue = requestedValue; }
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    public LocalDateTime getLastRetrieved() { return lastRetrieved; }
    public void setLastRetrieved(LocalDateTime lastRetrieved) { this.lastRetrieved = lastRetrieved; }
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
