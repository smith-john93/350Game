using System;

namespace DatabaseTesting
{
    class Test
    {
        static void Main(string[] args)
        {
            Database database = new Database();

            // Add Users
            try
            {
                database.AddNewUser("PasswordIsTACO", "TACO");
            }
            catch (System.Data.SQLite.SQLiteException e)
            {
                Console.WriteLine("Username already exists.");
            }
            try
            {
                database.AddNewUser("Tim", "Password");
            }
            catch (System.Data.SQLite.SQLiteException e)
            {
                Console.WriteLine("Username already exists.");
            }

            // Update Key Binding
            Console.WriteLine(database.GetKeyBinding("Tim", Database.JUMP));
            database.SetKeyBinding("Tim", Database.JUMP, "z");
            Console.WriteLine(database.GetKeyBinding("Tim", Database.JUMP));

            // Update Password
            Console.WriteLine(database.GetPassword("PasswordIsTACO"));
            database.SetPassword("PasswordIsTACO", "NotTACO");
            Console.WriteLine(database.GetPassword("PasswordIsTACO"));

            // Remove User
            database.RemoveUser("PasswordIsTACO");

        }
    }
}
