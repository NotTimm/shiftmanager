from flask import Flask, jsonify, request
from flask_mysqldb import MySQL
from functools import wraps
from werkzeug.security import generate_password_hash, check_password_hash
import jwt
from datetime import datetime, timedelta
import sys
import os
from dotenv import load_dotenv

load_dotenv();

app = Flask(__name__)
app.config['MYSQL_HOST'] = '127.0.0.1'
app.config['MYSQL_PORT'] = int(os.getenv('SQLPORT'))
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = os.getenv('SQLPASS')
app.config['MYSQL_DB'] = 'cs7319'
app.config['SECRET_KEY'] = os.getenv('SECRETKEY')

mysql = MySQL(app)

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.form['token']
        if not token:
            return 'Missing AUTH'
        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
        except:
            return 'Invalid AUTH'
        return f(*args, **kwargs)
    return decorated

@app.before_first_request
def before_first_request():
    try:
        print(mysql.connection.ping(), file=sys.stderr)
        print("connection successful", file=sys.stderr)
    except Exception as e:
        print("error connecting to server", e, file=sys.stderr)
    cur = mysql.connection.cursor();
    for i in range(15):
        date = (datetime.now() + timedelta(days=i)).date()
        dayOfWeek = date.strftime('%A')
        cur.execute("SELECT * FROM schedule WHERE date=%s", [date])
        if cur.fetchone() is None:
            print("added to db: ",date, dayOfWeek)
            cur.execute("INSERT INTO schedule (date, dayOfWeek, nurse1, nurse2, nurse3, nurse4, nurse5, nurse6, nurse7, nurse8, nurse9, nurse10) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", ([date], [dayOfWeek],["*"],["*"],["*"],["*"],["*"],["*"],["*"],["*"],["*"],["*"]))    
    mysql.connection.commit()
    cur.close()
    return 'Current Schedule Built'

@app.route('/newuser', methods=['POST'])
def new_user():
    name = request.form['fullName']
    workid = request.form['workID']
    phone = request.form['phoneNumber']
    email = request.form['email']
    password = request.form['password']
    if name == "" or email == "" or password == "":
        return 'Required Fields'
    print(name,workid,phone,email,password)
    cur = mysql.connection.cursor()
    check = cur.execute("SELECT * FROM users WHERE email=%s", [email])
    if check > 0:
        return 'Username already exists!'
    else:
        password_hash = generate_password_hash(password)
        cur.execute("INSERT INTO users(fullName, workID, phoneNumber, email, password) VALUES (%s, %s, %s, %s, %s)", (name, workid, phone, email, password_hash))
        mysql.connection.commit()
        cur.close()
        return 'User added'
    
@app.route('/login', methods=['POST'])
def login():
    email = request.form['email']
    password = request.form['password']
    cur = mysql.connection.cursor()
    result = cur.execute("SELECT * FROM users WHERE email=%s", [email])
    if result > 0:
        data = cur.fetchone()
        password_hash = generate_password_hash(password);
        if check_password_hash(password_hash, password):
            token = jwt.encode({"email": email, "exp": (datetime.now() + timedelta(minutes=30)).timestamp()}, app.config['SECRET_KEY'])
            return token
        else:
            return 'Invalid password!'
    else:
        return 'Invalid email!'
    
@app.route('/getshifts', methods=['POST'])
@token_required
def get_shifts():
    cur = mysql.connection.cursor()
    s = []
    for i in range(15):
        today = (datetime.now() + timedelta(days=i)).date()
        s.append(today)
    cur.execute("SELECT * FROM schedule WHERE date in (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)", ([s[0]],[s[1]],[s[2]],[s[3]],[s[4]],[s[5]],[s[6]],[s[7]],[s[8]],[s[9]],[s[10]],[s[11]],[s[12]],[s[13]],[s[14]]))
    rows = cur.fetchall()
    cur.close()
    return jsonify(rows)

if __name__ == '__main__':
    app.run(port=8080)