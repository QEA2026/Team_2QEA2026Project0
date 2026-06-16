import sqlite3

username = input("Enter username: ")
password = input("Enter password: ")

conn = sqlite3.connect("../database/expense_manager.db")
cursor = conn.cursor()

cursor.execute(
    "SELECT * FROM users WHERE username = ? AND password = ?",
    (username, password)
)

user = cursor.fetchone()

if user:

    print("\nLogin successful!")
    print(f"Welcome {username}")

    print("\n===== Employee Menu =====")
    print("1. Submit Expense")
    print("2. View My Expenses")
    print("3. Logout")

    choice = input("Choose an option: ")

    if choice == "1":

        amount = float(input("Enter expense amount: "))
        description = input("Enter description: ")
        date = input("Enter date (YYYY-MM-DD): ")

        cursor.execute(
            """
            INSERT INTO expenses
            (user_id, amount, description, date)
            VALUES (?, ?, ?, ?)
            """,
            (user[0], amount, description, date)
        )

        expense_id = cursor.lastrowid

        cursor.execute(
            """
            INSERT INTO approvals
            (expense_id, status)
            VALUES (?, 'pending')
            """,
            (expense_id,)
        )

        conn.commit()

        print("\nExpense submitted successfully!")

    elif choice == "2":

        cursor.execute(
            """
            SELECT e.id,
                   e.amount,
                   e.description,
                   e.date,
                   a.status
            FROM expenses e
            JOIN approvals a
            ON e.id = a.expense_id
            WHERE e.user_id = ?
            """,
            (user[0],)
        )

        expenses = cursor.fetchall()

        if not expenses:
            print("\nNo expenses found.")

        else:

            print("\n===== My Expenses =====")

            for expense in expenses:

                print(f"""
Expense ID: {expense[0]}
Amount: ${expense[1]}
Description: {expense[2]}
Date: {expense[3]}
Status: {expense[4]}
-----------------------
""")

    elif choice == "3":

        print("Goodbye!")

    else:

        print("Invalid option selected.")

else:

    print("Invalid username or password")

conn.close()