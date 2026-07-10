import sqlite3

conn = sqlite3.connect("../database/expense_manager.db")
cursor = conn.cursor()

print("=" * 40)
print("     Revature Expense Manager")
print("=" * 40)

username = input("Enter username: ")
password = input("Enter password: ")

cursor.execute(
    "SELECT * FROM users WHERE username = ? AND password = ?",
    (username, password)
)

user = cursor.fetchone()

if not user:
    print("Invalid username or password")
    conn.close()
    raise SystemExit

print(f"\nLogin successful! Welcome {username}")

while True:
    print("\n===== Employee Menu =====")
    print("1. Submit Expense")
    print("2. View My Expenses")
    print("3. Edit Pending Expense")
    print("4. Delete Pending Expense")
    print("5. Logout")

    choice = input("Choose an option: ")


    if choice == "1":
        amount = float(input("Enter expense amount: "))
        description = input("Enter description: ")
        date = input("Enter date (YYYY-MM-DD): ")

        cursor.execute(
            """
            INSERT INTO expenses (user_id, amount, description, date)
            VALUES (?, ?, ?, ?)
            """,
            (user[0], amount, description, date)
        )

        expense_id = cursor.lastrowid

        cursor.execute(
            """
            INSERT INTO approvals (expense_id, status)
            VALUES (?, 'pending')
            """,
            (expense_id,)
        )

        conn.commit()
        print("\nExpense submitted successfully!")

    elif choice == "2":

        cursor.execute(
            """
            SELECT e.id, e.amount, e.description, e.date, a.status
            FROM expenses e
            JOIN approvals a
            ON e.id = a.expense_id
            WHERE e.user_id = ?
            ORDER BY e.id
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

        expense_id = input("Enter Expense ID to edit: ")

        cursor.execute(
            "SELECT status FROM approvals WHERE expense_id = ?",
            (expense_id,)
        )

        status = cursor.fetchone()

        if status is None:
            print("Expense not found.")

        elif status[0] != "pending":
            print("Only pending expenses can be edited.")

        else:
            new_amount = float(input("Enter new amount: "))
            new_description = input("Enter new description: ")
            new_date = input("Enter new date (YYYY-MM-DD): ")

            cursor.execute(
                """
                UPDATE expenses
                SET amount=?, description=?, date=?
                WHERE id=? AND user_id=?
                """,
                (new_amount, new_description, new_date, expense_id, user[0])
            )

            conn.commit()
            print("Expense updated successfully!")

    elif choice == "4":

        expense_id = input("Enter Expense ID to delete: ")

        cursor.execute(
            "SELECT status FROM approvals WHERE expense_id=?",
            (expense_id,)
        )

        status = cursor.fetchone()

        if status is None:
            print("Expense not found.")

        elif status[0] != "pending":
            print("Only pending expenses can be deleted.")

        else:
            cursor.execute(
                "DELETE FROM approvals WHERE expense_id=?",
                (expense_id,)
            )
            cursor.execute(
                "DELETE FROM expenses WHERE id=? AND user_id=?",
                (expense_id, user[0])
            )

            conn.commit()
            print("Expense deleted successfully!")

    elif choice == "5":
        print("Goodbye!")
        break

    else:
        print("Invalid option selected.")

conn.close()
