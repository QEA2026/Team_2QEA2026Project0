import sqlite3

conn = sqlite3.connect("../database/expense_manager.db")

cursor = conn.cursor()

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
    expense_id INTEGER,
    status TEXT DEFAULT 'pending',
    reviewer INTEGER,
    comment TEXT,
    review_date TEXT
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

conn.commit()
conn.close()

print("Database created successfully!")