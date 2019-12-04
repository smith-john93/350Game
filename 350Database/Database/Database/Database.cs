using System.Data.SQLite;
using System.IO;
using System;

namespace DatabaseTesting
{
    class Database
    {
        public SQLiteConnection connection;
        public const string MOVE_LEFT = "moveLeft";
        public const string MOVE_RIGHT = "moveRight";
        public const string CROUCH = "crouch";
        public const string JUMP = "jump";
        public const string PUNCH = "punch";
        public const string HIGH_KICK = "highKick";
        public const string LOW_KICK = "lowKick";

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
                string query = "CREATE TABLE \"UserInformation\" ( \"username\" TEXT NOT NULL UNIQUE, " +
                    "\"password\" TEXT NOT NULL, \"moveLeft\" TEXT DEFAULT 'a', \"moveRight\" TEXT DEFAULT 'd', " +
                    "\"crouch\" TEXT DEFAULT 's', \"jump\" TEXT DEFAULT 'w', \"block\" TEXT DEFAULT 'o', " +
                    "\"punch\" TEXT DEFAULT 'p', \"highKick\" TEXT DEFAULT 'k', \"lowKick\" TEXT DEFAULT 'l', " +
                    "PRIMARY KEY(\"username\"))";
                SQLiteCommand command = new SQLiteCommand(query, connection);
                OpenConnection();
                command.ExecuteNonQuery();
                CloseConnection();
            }
        }

        private void OpenConnection()
        {
            if (connection.State != System.Data.ConnectionState.Open)
            {
                connection.Open();
            }
        }
        private void CloseConnection()
        {
            if (connection.State != System.Data.ConnectionState.Closed)
            {
                connection.Close();
            }
        }

        /*
        * void AddNewUser(string username, string password)
        * 
        *   Adds a new user entry to the database with the given username and password. Must catch
        * System.Data.SQLite.SQLiteException to handle the event of a username already being used in the database.
        *   
        * @param username: username to be added to the database
        * @param password: password for given user
        */
        public bool AddNewUser(string username, string password)
        {
            bool succeeded = true;

            try
            {
                // Insert entry into the LoginInformation and UserKeybinding tables
                string query = "INSERT INTO UserInformation ('username', 'password') VALUES (@username, @password)";
                SQLiteCommand command = new SQLiteCommand(query, connection);
                OpenConnection();
                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", password);
                command.ExecuteNonQuery();
                CloseConnection();
            }
            catch (System.Data.SQLite.SQLiteException e)
            {
                Console.WriteLine("Username \"" + username + "\" already exists.");
                succeeded = false;
            }

            Console.WriteLine("User " + username + " created.");
            return succeeded;
        }

        /*
        * void RemoveUser(string username, string password)
        * 
        *   Removes a user from the database with the given username.
        *   
        * @param username: username to be removed from the database
        */
        public void RemoveUser(string username)
        {
            string query = "DELETE FROM UserInformation WHERE username='" + username + "'";
            SQLiteCommand command = new SQLiteCommand(query, connection);
            OpenConnection();
            command.ExecuteNonQuery();
            CloseConnection();

            Console.WriteLine("User " + username + " removed.");
        }

        /*
        * void SetKeyBinding(string username, string command, string key)
        * 
        *   Changes the key binding for a given command for the given user.
        *   
        * @param username: user to have the key binding changed
        * @param command: command key binding to be changed
        * @param key: key that the key binding is to be changed to
        */
        public void SetKeyBinding(string username, string command, string key)
        {

            string query = "UPDATE UserInformation SET " + command + " = '" + key + "' WHERE username = '" + username + "'";
            SQLiteCommand sqlCommand = new SQLiteCommand(query, connection);
            OpenConnection();
            sqlCommand.ExecuteNonQuery();
            CloseConnection();
            Console.WriteLine(command + " key for user " + username + " set to " + key);
        }

        /*
        * void GetKeyBinding(string username, string command)
        * 
        *   Returns the key binding for a given command for the given user.
        *   
        * @param username: user key binding to be retrieved
        * @param command: command key binding to be retrieved
        *
        * Return: string (current key bindings for given command)
        */
        public string GetKeyBinding(string username, string command)
        {
            string query = "SELECT " + command + " FROM UserInformation WHERE username = '" + username + "'";
            SQLiteCommand sqlCommand = new SQLiteCommand(query, connection);
            OpenConnection();
            SQLiteDataReader reader = sqlCommand.ExecuteReader();
            reader.Read();
            string key = reader.GetString(0);
            reader.Close();
            CloseConnection();

            Console.WriteLine(command + " key binding for " + username + " is " + key);
            return key;
        }

        /*
        * void SetPassword(string username, string password)
        * 
        *   Changes the password for the given user.
        *   
        * @param username: user to have the key binding changed
        * @param password: new password
        */
        public void SetPassword(string username, string password)
        {
            string query = "UPDATE UserInformation SET password = '" + password + "' WHERE username = '" + username + "'";
            SQLiteCommand sqlCommand = new SQLiteCommand(query, connection);
            sqlCommand.Parameters.AddWithValue("@username", username);
            sqlCommand.Parameters.AddWithValue("@password", password);
            OpenConnection();
            sqlCommand.ExecuteNonQuery();
            CloseConnection();

            Console.WriteLine("Password for " + username + " updated.");
        }

        /*
        * void GetKeyBinding(string username, string command)
        * 
        *   Returns the key binding for a given command for the given user.
        *   
        * @param username: user key binding to be retrieved
        * @param command: command key binding to be retrieved
        *
        * Return: string (current key bindings for given command)
        */
        private string GetPassword(string username)
        {
            string query = "SELECT password FROM UserInformation WHERE username = '" + username + "'";
            SQLiteCommand sqlCommand = new SQLiteCommand(query, connection);
            OpenConnection();
            SQLiteDataReader reader = sqlCommand.ExecuteReader();
            reader.Read();
            string password = reader.GetString(0);
            reader.Close();
            CloseConnection();

            Console.WriteLine(username + "'s password retrieved");
            return password;
        }

        /* 
        * bool VerifyPassword(string submittedUsername, string submittedPassword)
        * 
        *   Checks to see if the submitted username/password combination matches the one
        * stored in the database.
        * 
        * @param submittedUsername: username
        * @param submittedPassword: password
        * 
        * Return true if passwords match, false if they do not match.
        */

        public bool VerifyPassword(string submittedUsername, string submittedPassword)
        {
            string storedPassword = GetPassword(submittedUsername);
            return storedPassword == submittedPassword ? true : false;
        }
    }
}