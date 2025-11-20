-- Initial schema for RequestedValueEntity with versioning
CREATE TABLE requested_value_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    requested_value VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    active BOOLEAN NOT NULL,
    created TIMESTAMP,
    last_retrieved TIMESTAMP
);

CREATE INDEX idx_requested_value_entity_name ON requested_value_entity (name);
CREATE INDEX idx_requested_value_entity_name_active ON requested_value_entity (name, active);