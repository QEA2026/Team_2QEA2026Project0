import sqlite3

conn = sqlite3.connect("../database/expense_manager.db")

cursor = conn.cursor()

cursor.execute("DROP TABLE IF EXISTS approvals")
cursor.execute("DROP TABLE IF EXISTS expenses")
cursor.execute("DROP TABLE IF EXISTS users")

cursor.execute("""
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE,
    password TEXT,
    role TEXT
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    amount REAL,
    description TEXT,
    date TEXT
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS approvals (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    expense_id INTEGER UNIQUE,
    status TEXT DEFAULT 'pending',
    reviewer INTEGER,
    comment TEXT,
    review_date TEXT,
    FOREIGN KEY (expense_id) REFERENCES expenses(id),
    FOREIGN KEY (reviewer) REFERENCES users(id)
)
""")

cursor.execute("""
INSERT OR IGNORE INTO users
(username, password, role)
VALUES
('employee1', 'password123', 'employee')
""")

cursor.execute("""
INSERT OR IGNORE INTO users
(username, password, role)
VALUES
('manager1', 'admin123', 'manager')
""")

cursor.executemany("""
INSERT OR IGNORE INTO expenses (user_id, amount, description, date)
VALUES (?, ?, ?, ?)
""", [
    (1, 45.75, "Lunch with client", "2026-06-20"),
    (2, 120.50, "Office supplies", "2026-06-21"),
])

cursor.executemany("""
INSERT OR IGNORE INTO approvals (expense_id)
VALUES (?)
""", [
    (1,),
    (2,),
])

conn.commit()
conn.close()

print("Database created successfully!")