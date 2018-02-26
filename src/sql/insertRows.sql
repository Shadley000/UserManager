INSERT INTO role_type (nname , description) VALUES ("Administrator",	"total control");
INSERT INTO role_type (nname , description) VALUES ("Super User",		"all user functions");
INSERT INTO role_type (nname , description) VALUES ("User",				"normal functions");
INSERT INTO role_type (nname , description) VALUES ("Guest",			"limited access");
INSERT INTO role_type (nname , description) VALUES ("Other",			"user defined");

INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("shadley000",	"password1",			"Stephen", 	"Hadley",	"StephenJHadley@gmail.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("Installation",	"asdk134jhalj7542",		"System", 	"User",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("A6Alarms",		"asdkjhaa6087lj435",	"System", 	"User",	" ");

INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("guest",			"password",			" ", 	" ",	"noreply@gmail.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("blackrhino",	"blackrhino123",	" ", 	" ",	"blackrhino_electsupt@dodi.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("blacklion",		"blacklion123",		" ",	" ",	"blacklion_electsupt@dodi.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("blackhawk",		"blackhawk123",		" ",	" ",	"blackhawk_electsupt@dodi.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("blackhornet",	"blackhornet123",	" ",	" ",	"blackhornet_electsupt@dodi.com");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("diamond",		"diamond123",		" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("hess",			"hess123",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("acmedrilling",	"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("bigoil",		"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("ship1",			"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("ship2",			"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("player1",		"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("player2",		"password",			" ",	" ",	" ");
INSERT INTO users (login, user_password, first_name, last_name, email) VALUES ("player3",		"password",			" ",	" ",	" ");

INSERT INTO application (nname) VALUES ("User Manager");
INSERT INTO application (nname) VALUES ("A6 Alarm Manager");
INSERT INTO application (nname) VALUES ("Galactic Warlord");

-- generate normal roles for user_manager and galactic warlord
INSERT INTO role (application_id, role_type_id, role_type_name, nname) 	select a.application_id, t.role_type_id, t.nname,t.nname	from application a, role_type t where a.nname = "User Manager" 	   and t.nname <> "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname) 	select a.application_id, t.role_type_id, t.nname,t.nname	from application a, role_type t where a.nname = "Galactic Warlord" and t.nname <> "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname) 	select a.application_id, t.role_type_id, t.nname,t.nname 	from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Administrator";        

-- generate custom roles for A6
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "BlackRhino", 	'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "BlackLion", 		'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "BlackHawk", 		'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "BlackHornet", 	'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "Ship 1", 		'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "Ship 2", 		'Ship'   		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "Diamond", 		'Contractor'   	from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "Hess", 			'Operator'		from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "ACME Drilling", 	'Contractor'    from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";        
INSERT INTO role (application_id, role_type_id, role_type_name, nname, ud1) 	select a.application_id, t.role_type_id, t.nname, "Big Oil", 		'Operator'    	from application a, role_type t where a.nname = "A6 Alarm Manager" AND t.nname = "Other";
					
-- gives me all role to everything
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND r.nname = "Administrator"	AND u.login = "shadley000";
          
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "BlackRhino"		AND u.login = "blackrhino";   
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "BlackLion"		AND u.login = "blacklion";  
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "BlackHawk"		AND u.login = "blackhawk";  
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "BlackHornet"	AND u.login = "blackhornet";  
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "Ship 1"			AND u.login = "ship1"; 
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "Ship 2"			AND u.login = "ship2";    
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "Diamond"		AND u.login = "diamond"; 
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "Hess"			AND u.login = "hess";
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "ACME Drilling"	AND u.login = "acmedrilling";
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "A6 Alarm Manager" AND r.nname = "Big Oil"		AND u.login = "bigoil";       

INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "Galactic Warlord" AND r.nname = "Guest"			AND u.login = "player1";    
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "Galactic Warlord" AND r.nname = "User"			AND u.login = "player2";   
INSERT INTO role_to_users (application_id, role_id, users_id) select a.application_id, r.role_id, u.users_id from application a, role r, users u where	a.application_id = r.application_id	AND a.nname = "Galactic Warlord" AND r.nname = "Super User"		AND u.login = "player3";   