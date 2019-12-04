using System.Data.SQLite;
using System.IO;
using System;

namespace Database 
{
    public class Database
    {
        public SQLiteConnection connection;
        public const string MOVE_LEFT = "moveLeft";
        public const string MOVE_RIGHT = "moveRight";
        public const string CROUCH = "crouch";
        public const string JUMP = "jump";
        public const string PUNCH = "punch";
        public const string HIGH_KICK = "highKick";
        public const string LOW_KICK = "lowKick";

        /// <summary>
        /// Default constructor for the database. 
        /// If a database does not exist, it builds one
        /// </summary>
        public Database()
        {
            // Establish connection to the database
            connection = new SQLiteConnection("Data Source=database.sqlite3");

            // Create new database file if not found
            if (!File.Exists("./database.sqlite3"))
            {
                // Create database file
                SQLiteConnection.CreateFile("database.sqlite3");

                // Create LoginInformation table
                string query = @"
CREATE TABLE UserInformation 
( 
	username TEXT NOT NULL UNIQUE, 
    password TEXT NOT NULL,
	moveLeft TEXT DEFAULT 'a',
	moveRight TEXT DEFAULT 'd',
    crouch TEXT DEFAULT 's', 
	jump TEXT DEFAULT 'w', 
	block TEXT DEFAULT 'o',
    punch TEXT DEFAULT 'p',
	highKick TEXT DEFAULT 'k', 
	lowKick TEXT DEFAULT 'l', 

    PRIMARY KEY(username)
)
";
                using (SQLiteCommand command = new SQLiteCommand(query, connection))
                {
                    command.Connection.Open();
                    command.ExecuteNonQuery();
                    command.Connection.Close();
                }
            }
        }

        /// <summary>
        /// Adds a new user entry to the database with the given username and password. 
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public bool AddNewUser(string username, string password)
        {
            bool succeeded = false;
            int i = 0;

            // Insert entry into the LoginInformation and UserKeybinding tables
            string insertQuery = "INSERT INTO UserInformation ('username', 'password') VALUES (@username, @password)";
            string checkQuery = "SELECT 1 FROM UserInformation WHERE username = @username"; 
            using (SQLiteCommand command = new SQLiteCommand(string.Empty, connection))
            {
                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", password);
                command.Connection.Open();
                command.CommandText = checkQuery;
                using(SQLiteDataReader reader = command.ExecuteReader())
                {
                    while(reader.Read())
                    {
                        i = int.Parse(reader[0].ToString());
                    }
                }
                if(i == 0)
                {
                    command.CommandText = insertQuery;
                    succeeded = true;
                    command.ExecuteNonQuery();
                }
                command.Connection.Close();
            }
            return succeeded;
        }

        /// <summary>
        /// Removes a user from the database with the given username.
        /// </summary>
        /// <param name="username"></param>
        public void RemoveUser(string username)
        {
            string query = "DELETE FROM UserInformation WHERE username= @username";
            using (SQLiteCommand command = new SQLiteCommand(query, connection))
            {
                command.Parameters.AddWithValue("@username", username);
                command.Connection.Open();
                command.ExecuteNonQuery();
                command.Connection.Close();
            }
            Console.WriteLine("User " + username + " removed.");
        }

        /// <summary>
        /// Changes the key binding for a given command for the given user.
        /// </summary>
        /// <param name="username"></param>
        /// <param name="command"></param>
        /// <param name="key"></param>
        public void SetKeyBinding(string username, string command, string key)
        {
            string query = "UPDATE UserInformation SET " + command + " = @key WHERE username = @username";
            using (SQLiteCommand sqlCommand = new SQLiteCommand(query, connection))
            {
                sqlCommand.Parameters.AddWithValue("@key", key);
                sqlCommand.Parameters.AddWithValue("@username", username);
                sqlCommand.Connection.Open();
                sqlCommand.ExecuteNonQuery();
                sqlCommand.Connection.Close();
            }                
            Console.WriteLine(command + " key for user " + username + " set to " + key);
        }

        /// <summary>
        /// Returns the key binding for a given command for the given user.
        /// </summary>
        /// <param name="username"></param>
        /// <param name="command"></param>
        /// <returns></returns>
        public string GetKeyBinding(string username, string command)
        {
            string key = string.Empty; ;
            string query = @"SELECT " + command + " FROM UserInformation WHERE username = @username";
            using (SQLiteCommand sqlCommand = new SQLiteCommand(query, connection))
            {
                sqlCommand.Parameters.AddWithValue("@username",username);
                sqlCommand.Connection.Open();
                using (SQLiteDataReader reader = sqlCommand.ExecuteReader())
                {
                    while(reader.Read())
                    {
                        key = reader.GetString(0);
                    }            
                }
                sqlCommand.Connection.Close();
            }

            Console.WriteLine(command + " key binding for " + username + " is " + key);
            return key;
        }

        /// <summary>
        /// Changes the password for the given user.
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        public void SetPassword(string username, string password)
        {
            string query = @"
UPDATE UserInformation 
SET password = @password 
WHERE username = @username
;
";
            using (SQLiteCommand sqlCommand = new SQLiteCommand(query, connection))
            {
                sqlCommand.Parameters.AddWithValue("@username", username);
                sqlCommand.Parameters.AddWithValue("@password", password);
                sqlCommand.Connection.Open();
                sqlCommand.ExecuteNonQuery();
                sqlCommand.Connection.Close();

            }
            Console.WriteLine("Password for " + username + " updated.");
        }

        /// <summary>
        /// Returns the key binding for a given command for the given user.
        /// </summary>
        /// <param name="username"></param>
        /// <returns></returns>
        private string GetPassword(string username)
        {
            string password;
            string query = @"
SELECT password 
FROM UserInformation 
WHERE username = @username
;
";
            using (SQLiteCommand sqlCommand = new SQLiteCommand(query, connection))
            {                
                sqlCommand.Parameters.AddWithValue("@username", username);
                sqlCommand.Connection.Open();
                using(SQLiteDataReader reader = sqlCommand.ExecuteReader())
                {
                    reader.Read();
                    password = reader.GetString(0);

                }
                sqlCommand.Connection.Close();
            }
            Console.WriteLine(username + "'s password retrieved");
            return password;
        }

         /// <summary>
        /// Checks to see if the submitted username/password combination matches the one stored in the database.
        /// </summary>
        /// <param name="submittedUsername"></param>
        /// <param name="submittedPassword"></param>
        /// <returns></returns>
        public bool VerifyPassword(string submittedUsername, string submittedPassword)
        {
            string storedPassword = GetPassword(submittedUsername);
            return storedPassword == submittedPassword ? true : false;
        }
    }
}
