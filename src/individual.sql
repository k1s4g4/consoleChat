

create database individual;


create table individual.user(
				  user_id int(15) not null auto_increment primary key,
				  username varchar(20) not null,
				  pass varchar(60) not null,
				  role int(1),
                  default_password boolean,
                  deleted boolean
				  );
                    

                    
create table individual.user_message(
						 message_id int(15) not null primary key auto_increment,
						 text varchar(500),
                         subject varchar(30),
                         date_time timestamp DEFAULT CURRENT_TIMESTAMP,
						 sender_id int(15) not null,
                         receiver_id int(15) not null,
                         receiver_has_read boolean,
                         receiver_deleted boolean,
                         sender_deleted boolean,
                         constraint message_fk_sender foreign key (sender_id) references user(user_id),
                         constraint message_fk_receiver foreign key (receiver_id) references user(user_id)
						 );
                    

INSERT INTO `user` VALUES (1,'vasilis','M0nyW9e28eJmZOA9cGTxVQ==',0,1,1),(2,'test','FrFpKtf82LF6XGDqKzDA/w==',0,1,1),(3,'test1','X9zvOLn5H7Wgx/ZcBt4yGQ==',0,1,1),(4,'asdf','U5bOXZIjVbv2ygGmiwKkEg==',0,1,1),(5,'test','FrFpKtf82LF6XGDqKzDA/w==',0,0,0),(6,'test1','X9zvOLn5H7Wgx/ZcBt4yGQ==',1,0,0),(7,'test3','bGuqepuTETOeZOWAD/L0IQ==',2,0,0);
            
INSERT INTO `user_message` VALUES (1,'dsWkI6ZFrBIXxkpy8VsfKCLETI3wHiDEfs/e5dPgfTKw0uaXEOKd3vHY09WaPlNG','geia sou test','2018-11-19 00:59:02',6,5,1,0,0),(2,'UCmFGOYrQP/W+YK2waW8BkEFXr39i7U4lZtEosZDBOAUaUNlHU+5qdI0AJurX8fG','geia sou test1','2018-11-19 00:59:51',5,6,1,0,0),(3,'r/4UjofhCdOjdsdRljyuFg==','geia sou ksana test','2018-11-19 01:00:12',6,5,0,0,0),(4,'OhSr2fnKe1L+/ahpO9O0UuNbIa4u1l18jwFt2v/FEQc=','asdf;lkjasdf','2018-11-19 01:13:29',6,5,1,0,0),(5,'fZ37jjE4rrFqvKxB9+IFTplv5sUe3XSdKPewLy6jS/h1liLrDl8nrOgAE4mnPc5/7Mo8ZxiLagKACpHhgzPoZw==','testasdf','2018-11-19 01:13:46',6,5,1,0,0),(6,'Ig34DhMKh3+sYK+DbCEeCFq4fFN04FU19a86zbGP8Yi9HfEKg40QRwT3tl+AzQb7TrlqbOOWXGRpAXKxdPo1xM26ccNtdtMwVqKT6NqMRbI=',' ti fash re file?','2018-11-19 01:24:12',5,7,1,0,0),(7,'FyL996jVoySaRHf0juTaQzeCfJ3MxBDBFdJHaNDZoD7uYDr00/cAwVGU3r5sNF0l','den nomizw','2018-11-19 01:30:34',7,5,0,0,0),(8,'FvK01PcEXwgnSsiqYMfzBsv3TBIiAlBXoy3dwDT5GiLj9rS/b8UF1Jmmd3xBGV+YDeldeLecwEv8v2sbrwIwtQ==','asdfasdf','2018-11-19 01:29:59',5,7,1,0,0),(9,'YJ+Lg
