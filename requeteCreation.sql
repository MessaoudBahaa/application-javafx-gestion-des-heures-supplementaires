

//////////////////////////////// TABLE des heures supplementaire travaille ///////////////////////////
drop TABLE IF EXISTS drh_test.lignSup;
create table lignSup (
	idEmp int(11) not null,
    idUser int(11) not null,
    dateS date not null,
    createdAt timestamp DEFAULT  CURRENT_TIMESTAMP ,
    updatedAt TIMESTAMP DEFAULT  CURRENT_TIMESTAMP on update now(),
    H50 int(11) default 0,
    H75 int(11) default 0,
    H100 int(11) default 0,
    HRepos int(11) default 0,
    HTot int(11) default 0 ,
    HTotM double default 0 ,
	primary key (idEmp,dateS),
	foreign key (idEmp) references employe(Matricule) on delete cascade on update cascade,
	foreign key (idUser) references user(id) on delete cascade on update cascade
);
DROP TRIGGER IF EXISTS defaultdatecurrentsup;
DELIMITER $$
CREATE TRIGGER `defaultdatecurrentsup` BEFORE INSERT ON `lignsup`
 FOR EACH ROW begin if (NEW.dateS='2000-01-01') then set NEW.dateS = DATE_FORMAT(now(),'%y-%m-%d');
 end if;
END $$
DELIMITER ;

drop trigger if EXISTS calculeTotalI ;
delimiter $$
create trigger  `calculeTotalI` before insert on `lignsup`
for each row BEGIN
  set NEW.HTot = NEW.H50+ NEW.H75+ NEW.H100;
  set NEW.HTotM = (NEW.H50*1.5)+(NEW.H75*1.75)+(NEW.H100*2)+ NEW.HRepos;
end $$
delimiter ;
drop trigger if EXISTS calculeTotalU ;
delimiter $$
create trigger  `calculeTotalU` before update on `lignsup`
for each row BEGIN
  set NEW.HTot = NEW.H50+ NEW.H75+ NEW.H100;
  set NEW.HTotM = (NEW.H50*1.5)+(NEW.H75*1.75)+(NEW.H100*2)+ NEW.HRepos;
end $$
delimiter ;


/////////////////////////////////////////////  Table des heures de recuperation ///////////////////////
drop TABLE IF EXISTS drh_test.lignRec;
create table lignRec (
	idEmp int(11) not null,
    idUser int(11) not null,
    dateR DATE not null,
    createdAt timestamp DEFAULT  CURRENT_TIMESTAMP ,
    updatedAt TIMESTAMP DEFAULT  CURRENT_TIMESTAMP on update now() ,
    du int(11) default 8 ,
    au int(11) default 16,
    HTot int(11) default 8,
	primary key (idEmp,dateR),
	foreign key (idEmp) references employe(Matricule) on delete cascade on update cascade,
	foreign key (idUser) references user(id) on delete cascade on update cascade
);
DROP TRIGGER IF EXISTS defaultdatecurrentrec;
DELIMITER $$
CREATE TRIGGER `defaultdatecurrentrec` BEFORE INSERT ON `lignRec`
 FOR EACH ROW begin if (NEW.dateR='2000-01-01') then set NEW.dateR = DATE_FORMAT(now(),'%y-%m-%d');
 end if;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS calculeHTotal;
DELIMITER $$
CREATE TRIGGER `calculeHTotal` BEFORE INSERT ON `lignRec`
for each row BEGIN
  if (new.du < new.au) then
  set new.htot= new.au-new.du;
  else
  set new.du = 8;
  set new.au=16;
  set new.htot= 8;
  end if ;
END $$
DELIMITER ;

///////////////////////////////////// table des heures supplementaires payee /////////////////////////////////
drop TABLE IF EXISTS drh_test.lignHPay;
create table lignHPay (
	idEmp int(11) not null,
    idUser int(11) not null,
    dateHP DATE not null,
    createdAt timestamp DEFAULT  CURRENT_TIMESTAMP ,
    updatedAt TIMESTAMP DEFAULT  CURRENT_TIMESTAMP on update now(),
    H50 int(11) default 0,
    H75 int(11) default 0,
    H100 int(11) default 0,
    HTot int(11) default 0,
    HTotM double default 0,
	primary key (idEmp,dateHP),
	foreign key (idEmp) references employe(Matricule) on delete cascade on update cascade,
	foreign key (idUser) references user(id) on delete cascade on update cascade
);



drop trigger if EXISTS  defaultdate;
DELIMITER $$
CREATE TRIGGER `defaultdate` BEFORE INSERT ON `lignHPay`
 FOR EACH ROW begin if (NEW.dateHP='2000-01-01') then set NEW.dateHP = DATE_FORMAT(now(),'%y-%m-01');
 ELSE
    set new.dateHP = DATE_FORMAT(new.dateHP,'%y-%m-01');
 end if;
END $$
DELIMITER ;

drop trigger if EXISTS calculeTotalPI ;
delimiter $$
create trigger  `calculeTotalPI` before insert on `lignHPay`
for each row BEGIN
  set NEW.HTot = NEW.H50+ NEW.H75+ NEW.H100;
  set NEW.HTotM = (NEW.H50*1.5)+(NEW.H75*1.75)+(NEW.H100*2);
end $$
delimiter ;

drop trigger if EXISTS calculeTotalPU ;
delimiter $$
create trigger  `calculeTotalPU` before update on `lignHPay`
for each row BEGIN
  set NEW.HTot = NEW.H50+ NEW.H75+ NEW.H100;
  set NEW.HTotM = (NEW.H50*1.5)+(NEW.H75*1.75)+(NEW.H100*2);
end $$
delimiter ;

/////////////////////////////////////////////////////////////////////////////////////////////

drop trigger if EXISTS calculeTotal ;
delimiter $$
create trigger  `calculeTotal` before insert on `lignHPay`
for each row BEGIN
  set NEW.HTot = NEW.H50+ NEW.H75+ NEW.H100;
  set NEW.HTotM = (NEW.H50*1.5)+

end $$
delimiter ;


CREATE TRIGGER `defaultdate` BEFORE INSERT ON `lignrec`
 FOR EACH ROW SET NEW.dateR = DATE_FORMAT(now(),'%y-%m-01');