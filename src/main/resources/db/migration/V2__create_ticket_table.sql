CREATE TABLE ticket_index_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  owner_open_id varchar(100) NOT NULL,
  assignee_open_id varchar(100) DEFAULT NULL,
  assignee_role varchar(20),
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  sum_duration BIGINT NOT NULL, PRIMARY KEY(ID)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE ticket_tbl(
  ID               int NOT NULL AUTO_INCREMENT,
  ticket_index_id  int NOT NULL,
  create_time      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
UPDATE CURRENT_TIMESTAMP,
event varchar(255)NOT NULL,
event_status varchar(20)NOT NULL,
duration BIGINT NOT NULL,
PRIMARY KEY(ID),
FOREIGN KEY(ticket_index_id)REFERENCES ticket_index_tbl(ID)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
