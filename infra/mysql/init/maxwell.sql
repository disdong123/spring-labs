CREATE USER 'maxwell'@'%' IDENTIFIED BY 'maxwell';

GRANT ALL ON maxwell.* TO 'maxwell'@'%';

GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE ON *.* TO 'maxwell'@'%';