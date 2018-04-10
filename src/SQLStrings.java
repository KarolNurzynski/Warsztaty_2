
public class SQLStrings {
	
	String sql1 = "create table user_group(id int(11) auto_increment, name varchar(255), primary key(id)) character set utf8;";
	String sql2 = "create table users (id bigint(20) auto_increment, username varchar(255), email varchar(255) unique, password varchar(245), user_group_id int(11), primary key(id), foreign key(user_group_id) references user_group(id)) character set utf8;";
	String sql3 = "create table exercise (id int(11) auto_increment, title varchar(255), description text, primary key(id)) character set utf8;";
	String sql4 = "create table solution(id int(11) auto_increment, created datetime, updated datetime, description text, exercise_id int(11), users_id bigint(20), primary key(id), foreign key(exercise_id) references exercise(id), foreign key(users_id) references users(id)) character set utf8;";
	String sql5 = "";
	
			

}
